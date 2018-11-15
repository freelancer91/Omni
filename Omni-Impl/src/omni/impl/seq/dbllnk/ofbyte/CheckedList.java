package omni.impl.seq.dbllnk.ofbyte;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import omni.api.OmniList;
import omni.function.ByteComparator;
import omni.function.ByteConsumer;
import omni.function.BytePredicate;
import omni.function.ByteUnaryOperator;
import omni.impl.CheckedCollection;
import omni.util.BitSetUtils;
import omni.util.TypeUtil;
public class CheckedList extends AbstractSeq.Checked{
    static int collapseBodyHelper(CheckedCollection.AbstractModCountChecker modCountChecker,Node prev,int numLeft,
            Node next,BytePredicate filter){
        int numRemoved=0;
        for(Node before;;prev=before){
            if(numLeft==0){
                modCountChecker.checkModCount();
                break;
            }
            --numLeft;
            if(filter.test((before=prev.next).val)){
                ++numRemoved;
                for(Node after;;next=after){
                    if(numLeft==0){
                        modCountChecker.checkModCount();
                        break;
                    }
                    --numLeft;
                    if(filter.test((after=next.prev).val)){
                        ++numRemoved;
                        long[] survivorSet;
                        int numSurvivors;
                        if(numLeft!=0&&(numSurvivors=markSurvivors(before=before.next,numLeft,
                                survivorSet=BitSetUtils.getBitSet(numLeft),filter))!=0){
                            modCountChecker.checkModCount();
                            numRemoved+=numLeft-numSurvivors;
                            prev=retainSurvivors(prev,before,numLeft,survivorSet);
                        }else{
                            numRemoved+=numLeft;
                            modCountChecker.checkModCount();
                        }
                        break;
                    }
                }
                joinNodes(prev,next);
                break;
            }
        }
        return numRemoved;
    }
    static int markSurvivors(Node begin,int numLeft,long[] survivorSet,BytePredicate filter){
        long survivorWord;
        for(int survivorOffset=0,numSurvivors=0;;survivorSet[survivorOffset++]=survivorWord){
            survivorWord=0L;
            long marker=1L;
            do{
                if(!filter.test(begin.val)){
                    survivorWord|=marker;
                    ++numSurvivors;
                }
                if(--numLeft==0){
                    survivorSet[survivorOffset]=survivorWord;
                    return numSurvivors;
                }
                begin=begin.next;
            }while((marker<<=1)!=0);
        }
    }
    static Node retainSurvivors(Node lastKnownSurvivor,Node curr,int numSurvivors,long[] survivorSet){
        for(int survivorOffset=0;;++survivorOffset){
            long survivorWord;
            int runLength;
            curr=iterateForward(curr,runLength=Long.numberOfTrailingZeros(survivorWord=survivorSet[survivorOffset]));
            if(runLength!=64){
                joinNodes(lastKnownSurvivor,lastKnownSurvivor=curr);
                runLength=Long.numberOfTrailingZeros(~(survivorWord>>>=runLength));
                do{
                    lastKnownSurvivor=uncheckedIterateForward(lastKnownSurvivor,runLength);
                    if((numSurvivors-=runLength)==0){
                        return lastKnownSurvivor;
                    }else if(runLength==64){
                        survivorWord=survivorSet[survivorOffset++];
                    }
                }while((runLength=Long.numberOfTrailingZeros(~(survivorWord>>>=runLength)))!=0);
                curr=lastKnownSurvivor;
            }
        }
    }
    CheckedList(){
        super();
    }
    CheckedList(Node onlyNode){
        super(onlyNode);
    }
    CheckedList(Node head,int size,Node tail){
        super(head,size,tail);
    }
    @Override
    public boolean add(byte val){
        super.addLast(val);
        return true;
    }
    @Override
    public boolean add(Byte val){
        super.addLast((byte)val);
        return true;
    }
    @Override
    public void add(int index,byte val){
        CheckedCollection.checkLo(index);
        int size;
        CheckedCollection.checkWriteHi(index,size=this.size);
        ++modCount;
        if(size!=0){
            if(index==0){
                head=new Node(val,head);
            }else{
                int tailDist;
                if((tailDist=size-index)==0){
                    tail=new Node(tail,val);
                }else{
                    staticInsertNode(this,index,val,tailDist);
                }
            }
        }else{
            staticInit(this,new Node(val));
        }
        this.size=size+1;
    }
    @Override
    public void addFirst(Byte val){
        super.addFirst((byte)val);
    }
    @Override
    public void addLast(Byte val){
        super.addLast((byte)val);
    }
    @Override
    public void clear(){
        if(size!=0){
            ++modCount;
            super.clear();
        }
    }
    @Override
    public Object clone(){
        Node newHead,newTail;
        int size;
        if((size=this.size)!=0){
            Node oldHead,oldTail;
            for(newHead=new Node((oldHead=head).val),newTail=newHead,oldTail=tail;oldHead!=oldTail;newTail=new Node(
                    newTail,(oldHead=oldHead.next).val)){}
        }else{
            newHead=null;
            newTail=null;
        }
        return new CheckedList(newHead,size,newTail);
    }
    @Override
    public byte byteElement(){
        return super.getFirstByte();
    }
    @Override
    public Byte element(){
        return super.getFirstByte();
    }
    @Override
    public boolean equals(Object val){
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public void forEach(Consumer<? super Byte> action){
        Node head;
        if((head=this.head)!=null){
            final int modCount=this.modCount;
            try{
                uncheckedForEachForward(head,tail,action::accept);
            }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
            }
        }
    }
    @Override
    public void forEach(ByteConsumer action){
        Node head;
        if((head=this.head)!=null){
            final int modCount=this.modCount;
            try{
                uncheckedForEachForward(head,tail,action);
            }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
            }
        }
    }
    @Override
    public byte getByte(int index){
        CheckedCollection.checkLo(index);
        int size;
        CheckedCollection.checkReadHi(index,size=this.size);
        return staticGetNode(this,index,size-index).val;
    }
    @Override
    public Byte getFirst(){
        return super.getFirstByte();
    }
    @Override
    public Byte getLast(){
        return super.getLastByte();
    }
    @Override
    public boolean offer(byte val){
        super.addLast(val);
        return true;
    }
    @Override
    public boolean offer(Byte val){
        super.addLast((byte)val);
        return true;
    }
    @Override
    public boolean offerFirst(byte val){
        super.addFirst(val);
        return true;
    }
    @Override
    public boolean offerFirst(Byte val){
        super.addFirst((byte)val);
        return true;
    }
    @Override
    public boolean offerLast(byte val){
        super.addLast(val);
        return true;
    }
    @Override
    public boolean offerLast(Byte val){
        super.addLast((byte)val);
        return true;
    }
    @Override
    public Byte pop(){
        return super.removeFirstByte();
    }
    @Override
    public byte popByte(){
        return super.removeFirstByte();
    }
    @Override
    public void push(byte val){
        super.addFirst(val);
    }
    @Override
    public void push(Byte val){
        super.addFirst((byte)val);
    }
    @Override
    public void put(int index,byte val){
        CheckedCollection.checkLo(index);
        int size;
        CheckedCollection.checkReadHi(index,size=this.size);
        staticGetNode(this,index,size-index).val=val;
    }
    @Override
    public Byte remove(){
        return super.removeFirstByte();
    }
    @Override
    public byte removeByte(){
        return super.removeFirstByte();
    }
    @Override
    public byte removeByteAt(int index){
        CheckedCollection.checkLo(index);
        int size;
        CheckedCollection.checkReadHi(index,size=this.size);
        ++modCount;
        Node node;
        if(--size!=0){
            if(index==0){
                staticEraseHead(this,node=head);
            }else{
                int tailDist;
                if((tailDist=size-index)==0){
                    staticEraseTail(this,node=tail);
                }else{
                    node=staticExtractNode(this,index,tailDist);
                }
            }
        }else{
            node=head;
            staticInit(this,null);
        }
        this.size=size;
        return node.val;
    }
    @Override
    public Byte removeFirst(){
        return super.removeFirstByte();
    }
    @Override
    public boolean removeIf(BytePredicate filter){
        Node head;
        return (head=this.head)!=null&&uncheckedRemoveIf(head,filter);
    }
    @Override
    public boolean removeIf(Predicate<? super Byte> filter){
        Node head;
        return (head=this.head)!=null&&uncheckedRemoveIf(head,filter::test);
    }
    @Override
    public Byte removeLast(){
        return super.removeLastByte();
    }
    @Override
    public void replaceAll(ByteUnaryOperator operator){
        Node head;
        if((head=this.head)!=null){
            final int modCount=this.modCount;
            try{
                uncheckedReplaceAll(head,tail,operator);
            }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
            }
            this.modCount=modCount+1;
        }
    }
    @Override
    public void replaceAll(UnaryOperator<Byte> operator){
        Node head;
        if((head=this.head)!=null){
            final int modCount=this.modCount;
            try{
                uncheckedReplaceAll(head,tail,operator::apply);
            }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
            }
            this.modCount=modCount+1;
        }
    }
    @Override
    public void reverseSort(){
        int size;
        if((size=this.size)>1){
            uncheckedSort(head,size,tail);
            ++modCount;
        }
    }
    @Override
    public byte set(int index,byte val){
        CheckedCollection.checkLo(index);
        int size;
        CheckedCollection.checkReadHi(index,size=this.size);
        Node node;
        final var oldVal=(node=staticGetNode(this,index,size-index)).val;
        node.val=val;
        return oldVal;
    }
    @Override
    public void sort(){
        int size;
        if((size=this.size)>1){
            uncheckedSort(head,size,tail);
            ++modCount;
        }
    }
    @Override
    public void sort(Comparator<? super Byte> sorter){
        int size;
        if((size=this.size)>1){
            final int modCount=this.modCount;
            try{
                uncheckedSort(head,size,tail,sorter::compare);
            }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
            }
            this.modCount=modCount+1;
        }
    }
    @Override
    public void sort(ByteComparator sorter){
        int size;
        if((size=this.size)>1){
            final int modCount=this.modCount;
            try{
                uncheckedSort(head,size,tail,sorter);
            }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
            }
            this.modCount=modCount+1;
        }
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        return super.toArray(size->{
            final int modCount=this.modCount;
            try{
                return arrConstructor.apply(size);
            }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
            }
        });
    }
    private boolean collapseBody(int modCount,Node prev,Node next,BytePredicate filter){
        int numLeft=size;
        int numConsumed=2;
        for(Node before;numConsumed!=numLeft;prev=before){
            ++numConsumed;
            if(filter.test((before=prev.next).val)){
                int newSize=numConsumed-1;
                for(Node after;;next=after,++newSize){
                    if(numConsumed==numLeft){
                        CheckedCollection.checkModCount(modCount,this.modCount);
                        break;
                    }
                    ++numConsumed;
                    if(filter.test((after=next.prev).val)){
                        long[] survivorSet;
                        if((numLeft-=numConsumed)!=0&&(numLeft=markSurvivors(before=before.next,numLeft,
                                survivorSet=BitSetUtils.getBitSet(numLeft),filter))!=0){
                            CheckedCollection.checkModCount(modCount,this.modCount);
                            newSize+=numLeft;
                            prev=retainSurvivors(prev,before,numLeft,survivorSet);
                        }else{
                            CheckedCollection.checkModCount(modCount,this.modCount);
                        }
                        break;
                    }
                }
                joinNodes(prev,next);
                this.modCount=modCount+1;
                size=newSize;
                return true;
            }
        }
        CheckedCollection.checkModCount(modCount,this.modCount);
        return false;
    }
    private void collapseHead(int modCount,Node headCandidate,Node oldTail,BytePredicate filter){
        for(int oldSize=size,numConsumed=2;;headCandidate=headCandidate.next){
            if(numConsumed==oldSize){
                CheckedCollection.checkModCount(modCount,this.modCount);
                size=1;
                break;
            }
            ++numConsumed;
            if(!filter.test(headCandidate.val)){
                size=(oldSize-=numConsumed)+1
                        -collapseBodyHelper(new ModCountChecker(modCount),headCandidate,oldSize,oldTail,filter);
                break;
            }
        }
        staticSetHead(this,headCandidate);
    }
    private void collapseHeadAndTail(int modCount,Node headCandidate,Node tailCandidate,BytePredicate filter){
        int oldSize=size;
        int numConsumed=2;
        for(;;){
            if(numConsumed==oldSize){
                CheckedCollection.checkModCount(modCount,this.modCount);
                staticClear(this);
                return;
            }
            ++numConsumed;
            if(!filter.test((headCandidate=headCandidate.next).val)){
                for(;;){
                    if(numConsumed==oldSize){
                        CheckedCollection.checkModCount(modCount,this.modCount);
                        size=1;
                        tailCandidate=headCandidate;
                        break;
                    }
                    ++numConsumed;
                    if(!filter.test((tailCandidate=tailCandidate.prev).val)){
                        size=(oldSize-=numConsumed)+2-collapseBodyHelper(new ModCountChecker(modCount),headCandidate,
                                oldSize,tailCandidate,filter);
                        break;
                    }
                }
                staticSetTail(this,tailCandidate);
                staticSetHead(this,headCandidate);
                return;
            }
        }
    }
    private void collapseTail(int modCount,Node oldHead,Node tailCandidate,BytePredicate filter){
        for(int oldSize=size,numConsumed=2;;tailCandidate=tailCandidate.prev){
            if(numConsumed==oldSize){
                CheckedCollection.checkModCount(modCount,this.modCount);
                size=1;
                break;
            }
            ++numConsumed;
            if(!filter.test(tailCandidate.val)){
                size=(oldSize-=numConsumed)+1
                        -collapseBodyHelper(new ModCountChecker(modCount),oldHead,oldSize,tailCandidate,filter);
                break;
            }
        }
        staticSetTail(this,tailCandidate);
    }
    private boolean uncheckedRemoveIf(Node head,BytePredicate filter){
        final int modCount=this.modCount;
        try{
            final Node tail=this.tail;
            if(filter.test(head.val)){
                if(head==tail){
                    CheckedCollection.checkModCount(modCount,this.modCount);
                    super.clear();
                }else if(filter.test(tail.val)){
                    collapseHeadAndTail(modCount,head,tail,filter);
                }else{
                    collapseHead(modCount,head.next,tail,filter);
                }
                this.modCount=modCount+1;
                return true;
            }else if(head!=tail){
                if(filter.test(tail.val)){
                    collapseTail(modCount,head,tail.prev,filter);
                    this.modCount=modCount+1;
                    return true;
                }
                return collapseBody(modCount,head,tail,filter);
            }
        }catch(final RuntimeException e){
            throw CheckedCollection.checkModCount(modCount,this.modCount,e);
        }
        CheckedCollection.checkModCount(modCount,this.modCount);
        return false;
    }
    @Override
    public Byte poll(){
        return super.pollFirst();
    }
    @Override
    public double pollDouble(){
        return super.pollFirstDouble();
    }
    @Override
    public float pollFloat(){
        return super.pollFirstFloat();
    }
    @Override
    public long pollLong(){
        return super.pollFirstLong();
    }
    @Override
    public int pollInt(){
        return super.pollFirstInt();
    }
    @Override
    public short pollShort(){
        return super.pollFirstShort();
    }
    @Override
    public byte pollByte(){
        return super.pollFirstByte();
    }
    @Override
    public Byte pollLast(){
        Node tail;
        if((tail=this.tail)!=null){
            ++modCount;
            --size;
            staticEraseTail(this,tail);
            return tail.val;
        }
        return null;
    }
    @Override
    public double pollLastDouble(){
        Node tail;
        if((tail=this.tail)!=null){
            ++modCount;
            --size;
            staticEraseTail(this,tail);
            return tail.val;
        }
        return Double.NaN;
    }
    @Override
    public float pollLastFloat(){
        Node tail;
        if((tail=this.tail)!=null){
            ++modCount;
            --size;
            staticEraseTail(this,tail);
            return tail.val;
        }
        return Float.NaN;
    }
    @Override
    public long pollLastLong(){
        Node tail;
        if((tail=this.tail)!=null){
            ++modCount;
            --size;
            staticEraseTail(this,tail);
            return tail.val;
        }
        return Long.MIN_VALUE;
    }
    @Override
    public int pollLastInt(){
        Node tail;
        if((tail=this.tail)!=null){
            ++modCount;
            --size;
            staticEraseTail(this,tail);
            return tail.val;
        }
        return Integer.MIN_VALUE;
    }
    @Override
    public short pollLastShort(){
        Node tail;
        if((tail=this.tail)!=null){
            ++modCount;
            --size;
            staticEraseTail(this,tail);
            return tail.val;
        }
        return Short.MIN_VALUE;
    }
    @Override
    public byte pollLastByte(){
        Node tail;
        if((tail=this.tail)!=null){
            ++modCount;
            --size;
            staticEraseTail(this,tail);
            return tail.val;
        }
        return Byte.MIN_VALUE;
    }
    @Override
    public omni.api.OmniIterator.OfByte descendingIterator(){
        // TODO Auto-generated method stub
        return null;
    }
    private boolean uncheckedRemoveLastMatch(Node curr,int val){
        final var head=this.head;
        if(curr.val==val){
            if(curr==head){
                staticInit(this,null);
            }else{
                staticEraseTail(this,curr);
            }
        }else{
            Node next;
            do{
                if(curr==head){
                    return false;
                }
            }while((curr=(next=curr).prev).val!=val);
            if(curr==head){
                staticSetHead(this,next);
            }else{
                joinNodes(curr.prev,next);
            }
        }
        ++modCount;
        --size;
        return true;
    }
    @Override
    public boolean removeLastOccurrence(boolean val){
        final Node tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,TypeUtil.castToByte(val));
    }
    @Override
    public boolean removeLastOccurrence(byte val){
        final Node tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,val);
    }
    @Override
    public boolean removeLastOccurrence(char val){
        final Node tail;
        return val<=Byte.MAX_VALUE&&(tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,val);
    }
    @Override
    public boolean removeLastOccurrence(double val){
        final Node tail;
        int v;
        return (tail=this.tail)!=null&&val==(v=(byte)val)&&uncheckedRemoveLastMatch(tail,v);
    }
    @Override
    public boolean removeLastOccurrence(float val){
        final Node tail;
        int v;
        return (tail=this.tail)!=null&&val==(v=(byte)val)&&uncheckedRemoveLastMatch(tail,v);
    }
    @Override
    public boolean removeLastOccurrence(int val){
        final Node tail;
        return (tail=this.tail)!=null&&val==(byte)val&&uncheckedRemoveLastMatch(tail,val);
    }
    @Override
    public boolean removeLastOccurrence(long val){
        final Node tail;
        int v;
        return (tail=this.tail)!=null&&val==(v=(byte)val)&&uncheckedRemoveLastMatch(tail,v);
    }
    @Override
    public boolean removeLastOccurrence(Object val){
        final Node tail;
        return (tail=this.tail)!=null&&val instanceof Byte&&uncheckedRemoveLastMatch(tail,(byte)val);
    }
    @Override
    public boolean add(boolean val){
        super.addLast(TypeUtil.castToByte(val));
        return true;
    }
    @Override
    public omni.api.OmniIterator.OfByte iterator(){
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public omni.api.OmniListIterator.OfByte listIterator(){
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public omni.api.OmniListIterator.OfByte listIterator(int index){
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public OmniList.OfByte subList(int fromIndex,int toIndex){
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    boolean uncheckedRemoveFirstMatch(Node curr,int val){
        final var tail=this.tail;
        if(val==curr.val){
            if(curr==tail){
                staticInit(this,null);
            }else{
                staticEraseHead(this,curr);
            }
        }else{
            Node prev;
            do{
                if(curr==tail){
                    return false;
                }
            }while((curr=(prev=curr).next).val!=val);
            if(curr==tail){
                staticSetTail(this,prev);
            }else{
                joinNodes(prev,curr.next);
            }
        }
        ++modCount;
        --size;
        return true;
    }
}
