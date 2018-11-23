package omni.impl.seq.dbllnk.ofdouble;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.function.DoubleUnaryOperator;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import omni.function.DoubleComparator;
import omni.impl.CheckedCollection;
import omni.util.BitSetUtils;
import omni.util.TypeUtil;
public class CheckedList extends AbstractSeq.Checked{
    static int collapseBodyHelper(CheckedCollection.AbstractModCountChecker modCountChecker,Node prev,int numLeft,
            Node next,DoublePredicate filter){
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
    static int markSurvivors(Node begin,int numLeft,long[] survivorSet,DoublePredicate filter){
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
    public boolean add(double val){
        super.addLast(val);
        return true;
    }
    @Override
    public boolean add(Double val){
        super.addLast((double)val);
        return true;
    }
    @Override
    public void add(int index,double val){
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
    public void addFirst(Double val){
        super.addFirst((double)val);
    }
    @Override
    public void addLast(Double val){
        super.addLast((double)val);
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
    public double doubleElement(){
        return super.getFirstDouble();
    }
    @Override
    public Double element(){
        return super.getFirstDouble();
    }
    @Override
    public boolean equals(Object val){
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public void forEach(Consumer<? super Double> action){
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
    public void forEach(DoubleConsumer action){
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
    public double getDouble(int index){
        CheckedCollection.checkLo(index);
        int size;
        CheckedCollection.checkReadHi(index,size=this.size);
        return staticGetNode(this,index,size-index).val;
    }
    @Override
    public Double getFirst(){
        return super.getFirstDouble();
    }
    @Override
    public Double getLast(){
        return super.getLastDouble();
    }
    @Override
    public boolean offer(double val){
        super.addLast(val);
        return true;
    }
    @Override
    public boolean offer(Double val){
        super.addLast((double)val);
        return true;
    }
    @Override
    public boolean offerFirst(double val){
        super.addFirst(val);
        return true;
    }
    @Override
    public boolean offerFirst(Double val){
        super.addFirst((double)val);
        return true;
    }
    @Override
    public boolean offerLast(double val){
        super.addLast(val);
        return true;
    }
    @Override
    public boolean offerLast(Double val){
        super.addLast((double)val);
        return true;
    }
    @Override
    public Double pop(){
        return super.removeFirstDouble();
    }
    @Override
    public double popDouble(){
        return super.removeFirstDouble();
    }
    @Override
    public void push(double val){
        super.addFirst(val);
    }
    @Override
    public void push(Double val){
        super.addFirst((double)val);
    }
    @Override
    public void put(int index,double val){
        CheckedCollection.checkLo(index);
        int size;
        CheckedCollection.checkReadHi(index,size=this.size);
        staticGetNode(this,index,size-index).val=val;
    }
    @Override
    public Double remove(){
        return super.removeFirstDouble();
    }
    @Override
    public double removeDouble(){
        return super.removeFirstDouble();
    }
    @Override
    public double removeDoubleAt(int index){
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
    public Double removeFirst(){
        return super.removeFirstDouble();
    }
    @Override
    public boolean removeIf(DoublePredicate filter){
        Node head;
        return (head=this.head)!=null&&uncheckedRemoveIf(head,filter);
    }
    @Override
    public boolean removeIf(Predicate<? super Double> filter){
        Node head;
        return (head=this.head)!=null&&uncheckedRemoveIf(head,filter::test);
    }
    @Override
    public Double removeLast(){
        return super.removeLastDouble();
    }
    @Override
    public void replaceAll(DoubleUnaryOperator operator){
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
    public void replaceAll(UnaryOperator<Double> operator){
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
    public double set(int index,double val){
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
    public void sort(Comparator<? super Double> sorter){
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
    public void sort(DoubleComparator sorter){
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
    private boolean collapseBody(int modCount,Node prev,Node next,DoublePredicate filter){
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
    private void collapseHead(int modCount,Node headCandidate,Node oldTail,DoublePredicate filter){
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
    private void collapseHeadAndTail(int modCount,Node headCandidate,Node tailCandidate,DoublePredicate filter){
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
    private void collapseTail(int modCount,Node oldHead,Node tailCandidate,DoublePredicate filter){
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
    private boolean uncheckedRemoveIf(Node head,DoublePredicate filter){
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
    public Double poll(){
        return super.pollFirst();
    }
    @Override
    public double pollDouble(){
        return super.pollFirstDouble();
    }
    @Override
    public Double pollLast(){
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
    public boolean add(boolean val){
        super.addLast(TypeUtil.castToDouble(val));
        return true;
    }
    @Override
    public boolean add(char val){
        super.addLast((double)val);
        return true;
    }
    @Override
    public boolean add(float val){
        super.addLast((double)val);
        return true;
    }
    @Override
    public boolean add(int val){
        super.addLast((double)val);
        return true;
    }
    @Override
    public boolean add(long val){
        super.addLast((double)val);
        return true;
    }
    @Override
    public boolean add(short val){
        super.addLast((double)val);
        return true;
    }
    @Override
    public omni.api.OmniIterator.OfDouble descendingIterator(){
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public omni.api.OmniIterator.OfDouble iterator(){
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public omni.api.OmniListIterator.OfDouble listIterator(){
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public omni.api.OmniListIterator.OfDouble listIterator(int index){
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public boolean removeLastOccurrence(boolean val){
        final Node tail;
        if((tail=this.tail)!=null){
            if(val){
                return uncheckedRemoveLastDblBits(tail,TypeUtil.DBL_TRUE_BITS);
            }
            return uncheckedRemoveLastDbl0(tail);
        }
        return false;
    }
    @Override
    public boolean removeLastOccurrence(double val){
        final Node tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,val);
    }
    @Override
    public boolean removeLastOccurrence(float val){
        final Node tail;
        if((tail=this.tail)!=null){
            if(val==val){
                return uncheckedRemoveLastDblBits(tail,Double.doubleToRawLongBits(val));
            }
            return uncheckedRemoveLastDblNaN(tail);
        }
        return false;
    }
    @Override
    public boolean removeLastOccurrence(int val){
        final Node tail;
        if((tail=this.tail)!=null){
            if(val!=0){
                return uncheckedRemoveLastDblBits(tail,Double.doubleToRawLongBits(val));
            }
            return uncheckedRemoveLastDbl0(tail);
        }
        return false;
    }
    @Override
    public boolean removeLastOccurrence(long val){
        final Node tail;
        if((tail=this.tail)!=null){
            if(val!=0){
                return TypeUtil.checkCastToDouble(val)
                        &&uncheckedRemoveLastDblBits(tail,Double.doubleToRawLongBits(val));
            }
            return uncheckedRemoveLastDbl0(tail);
        }
        return false;
    }
    @Override
    public boolean removeLastOccurrence(Object val){
        final Node tail;
        return (tail=this.tail)!=null&&val instanceof Double&&uncheckedRemoveLastMatch(tail,(double)val);
    }
    @Override
    public omni.api.OmniList.OfDouble subList(int fromIndex,int toIndex){
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    boolean uncheckedRemoveFirstDbl0(Node curr){
        final var tail=this.tail;
        if(0==curr.val){
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
            }while((curr=(prev=curr).next).val!=0);
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
    @Override
    boolean uncheckedRemoveFirstDblBits(Node curr,long dblBits){
        final var tail=this.tail;
        if(dblBits==Double.doubleToRawLongBits(curr.val)){
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
            }while(Double.doubleToRawLongBits((curr=(prev=curr).next).val)!=dblBits);
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
    @Override
    boolean uncheckedRemoveFirstDblNaN(Node curr){
        final var tail=this.tail;
        if(Double.isNaN(curr.val)){
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
            }while(!Double.isNaN((curr=(prev=curr).next).val));
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
    private boolean uncheckedRemoveLastMatch(Node curr,double val){
        if(val==val){
            return uncheckedRemoveLastDblBits(curr,Double.doubleToRawLongBits(val));
        }
        return uncheckedRemoveLastDblNaN(curr);
    }
    private boolean uncheckedRemoveLastDblBits(Node curr,long dblBits){
        final var head=this.head;
        if(Double.doubleToRawLongBits(curr.val)==dblBits){
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
            }while(Double.doubleToRawLongBits((curr=(next=curr).prev).val)!=dblBits);
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
    private boolean uncheckedRemoveLastDbl0(Node curr){
        final var head=this.head;
        if(curr.val==0){
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
            }while((curr=(next=curr).prev).val!=0);
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
    private boolean uncheckedRemoveLastDblNaN(Node curr){
        final var head=this.head;
        if(Double.isNaN(curr.val)){
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
            }while(!Double.isNaN((curr=(next=curr).prev).val));
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
