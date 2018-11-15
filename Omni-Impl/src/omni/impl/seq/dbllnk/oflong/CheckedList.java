package omni.impl.seq.dbllnk.oflong;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
import java.util.function.LongUnaryOperator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import omni.api.OmniList;
import omni.function.LongComparator;
import omni.impl.CheckedCollection;
import omni.util.BitSetUtils;
import omni.util.TypeUtil;
public class CheckedList extends AbstractSeq.Checked{
    static int collapseBodyHelper(CheckedCollection.AbstractModCountChecker modCountChecker,Node prev,int numLeft,
            Node next,LongPredicate filter){
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
    static int markSurvivors(Node begin,int numLeft,long[] survivorSet,LongPredicate filter){
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
    public boolean add(boolean val){
        super.addLast(TypeUtil.castToLong(val));
        return true;
    }
    @Override
    public boolean add(int val){
        super.addLast((long)val);
        return true;
    }
    @Override
    public void add(int index,long val){
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
    public boolean add(long val){
        super.addLast(val);
        return true;
    }
    @Override
    public boolean add(Long val){
        super.addLast((long)val);
        return true;
    }
    @Override
    public void addFirst(Long val){
        super.addFirst((long)val);
    }
    @Override
    public void addLast(Long val){
        super.addLast((long)val);
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
    public omni.api.OmniIterator.OfLong descendingIterator(){
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public Long element(){
        return super.getFirstLong();
    }
    @Override
    public boolean equals(Object val){
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public void forEach(Consumer<? super Long> action){
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
    public void forEach(LongConsumer action){
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
    public Long getFirst(){
        return super.getFirstLong();
    }
    @Override
    public Long getLast(){
        return super.getLastLong();
    }
    @Override
    public long getLong(int index){
        CheckedCollection.checkLo(index);
        int size;
        CheckedCollection.checkReadHi(index,size=this.size);
        return staticGetNode(this,index,size-index).val;
    }
    @Override
    public omni.api.OmniIterator.OfLong iterator(){
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public omni.api.OmniListIterator.OfLong listIterator(){
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public omni.api.OmniListIterator.OfLong listIterator(int index){
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public long longElement(){
        return super.getFirstLong();
    }
    @Override
    public boolean offer(long val){
        super.addLast(val);
        return true;
    }
    @Override
    public boolean offer(Long val){
        super.addLast((long)val);
        return true;
    }
    @Override
    public boolean offerFirst(long val){
        super.addFirst(val);
        return true;
    }
    @Override
    public boolean offerFirst(Long val){
        super.addFirst((long)val);
        return true;
    }
    @Override
    public boolean offerLast(long val){
        super.addLast(val);
        return true;
    }
    @Override
    public boolean offerLast(Long val){
        super.addLast((long)val);
        return true;
    }
    @Override
    public Long poll(){
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
    public Long pollLast(){
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
    public long pollLong(){
        return super.pollFirstLong();
    }
    @Override
    public Long pop(){
        return super.removeFirstLong();
    }
    @Override
    public long popLong(){
        return super.removeFirstLong();
    }
    @Override
    public void push(long val){
        super.addFirst(val);
    }
    @Override
    public void push(Long val){
        super.addFirst((long)val);
    }
    @Override
    public void put(int index,long val){
        CheckedCollection.checkLo(index);
        int size;
        CheckedCollection.checkReadHi(index,size=this.size);
        staticGetNode(this,index,size-index).val=val;
    }
    @Override
    public Long remove(){
        return super.removeFirstLong();
    }
    @Override
    public Long removeFirst(){
        return super.removeFirstLong();
    }
    @Override
    public boolean removeIf(LongPredicate filter){
        Node head;
        return (head=this.head)!=null&&uncheckedRemoveIf(head,filter);
    }
    @Override
    public boolean removeIf(Predicate<? super Long> filter){
        Node head;
        return (head=this.head)!=null&&uncheckedRemoveIf(head,filter::test);
    }
    @Override
    public Long removeLast(){
        return super.removeLastLong();
    }
    @Override
    public boolean removeLastOccurrence(boolean val){
        final Node tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,TypeUtil.castToLong(val));
    }
    @Override
    public boolean removeLastOccurrence(double val){
        final Node tail;
        long v;
        return (tail=this.tail)!=null&&TypeUtil.doubleEquals(val,v=(long)val)&&uncheckedRemoveLastMatch(tail,v);
    }
    @Override
    public boolean removeLastOccurrence(float val){
        final Node tail;
        long v;
        return (tail=this.tail)!=null&&TypeUtil.floatEquals(val,v=(long)val)&&uncheckedRemoveLastMatch(tail,v);
    }
    @Override
    public boolean removeLastOccurrence(int val){
        final Node tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,val);
    }
    @Override
    public boolean removeLastOccurrence(long val){
        final Node tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,val);
    }
    @Override
    public boolean removeLastOccurrence(Object val){
        final Node tail;
        return (tail=this.tail)!=null&&val instanceof Long&&uncheckedRemoveLastMatch(tail,(long)val);
    }
    @Override
    public long removeLong(){
        return super.removeFirstLong();
    }
    @Override
    public long removeLongAt(int index){
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
    public void replaceAll(LongUnaryOperator operator){
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
    public void replaceAll(UnaryOperator<Long> operator){
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
    public long set(int index,long val){
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
    public void sort(Comparator<? super Long> sorter){
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
    public void sort(LongComparator sorter){
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
    public OmniList.OfLong subList(int fromIndex,int toIndex){
        // TODO Auto-generated method stub
        return null;
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
    @Override
    boolean uncheckedRemoveFirstMatch(Node curr,long val){
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
    private boolean collapseBody(int modCount,Node prev,Node next,LongPredicate filter){
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
    private void collapseHead(int modCount,Node headCandidate,Node oldTail,LongPredicate filter){
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
    private void collapseHeadAndTail(int modCount,Node headCandidate,Node tailCandidate,LongPredicate filter){
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
    private void collapseTail(int modCount,Node oldHead,Node tailCandidate,LongPredicate filter){
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
    private boolean uncheckedRemoveIf(Node head,LongPredicate filter){
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
    private boolean uncheckedRemoveLastMatch(Node curr,long val){
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
}
