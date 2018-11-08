package omni.impl.seq.dbllnk;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.impl.CheckedCollection;
import omni.util.BitSetUtils;
import omni.util.OmniPred;
public class CheckedRefDblLnkList<E>extends AbstractRefDblLnkSeq.Checked<E>{
    private static <E> int collapseBodyHelper(CheckedCollection.AbstractModCountChecker modCountChecker,Node<E> prev,
            int numLeft,Node<E> next,Predicate<? super E> filter){
        int numRemoved=0;
        for(Node<E> before;;prev=before){
            if(numLeft==0){
                modCountChecker.checkModCount();
                break;
            }
            --numLeft;
            if(filter.test((before=prev.next).val)){
                ++numRemoved;
                for(Node<E> after;;next=after){
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
    private static <E> int markSurvivors(Node<E> begin,int numLeft,long[] survivorSet,Predicate<? super E> filter){
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
    private static <E> Node<E> retainSurvivors(Node<E> lastKnownSurvivor,Node<E> curr,int numSurvivors,
            long[] survivorSet){
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
    CheckedRefDblLnkList(){
        super();
    }
    CheckedRefDblLnkList(Node<E> onlyNode){
        super(onlyNode);
    }
    CheckedRefDblLnkList(Node<E> head,int size,Node<E> tail){
        super(head,size,tail);
    }
    @Override
    public boolean add(E val){
        super.addLast(val);
        return true;
    }
    @Override
    boolean uncheckedRemoveFirstMatch(Node<E> curr,Predicate<Object> pred){
        final var tail=this.tail;
        if(pred.test(curr.val)){
            if(curr==tail){
                staticInit(this,null);
            }else{
                staticEraseHead(this,curr);
            }
        }else{
            Node<E> prev;
            do{
                if(curr==tail){
                    return false;
                }
            }while(!pred.test((curr=(prev=curr).next).val));
            if(curr==tail){
                staticSetTail(this,prev);
            }else{
                joinNodes(prev,curr.next);
            }
        }
        ++this.modCount;
        --size;
        return true;
    }
    @Override
    boolean uncheckedRemoveFirstNonNull(Node<E> curr,Object nonNull){
        final int modCount=this.modCount;
        try{
            final var tail=this.tail;
            if(nonNull.equals(curr.val)){
                CheckedCollection.checkModCount(modCount,this.modCount);
                if(curr==tail){
                    staticInit(this,null);
                }else{
                    staticEraseHead(this,curr);
                }
            }else{
                Node<E> prev;
                do{
                    if(curr==tail){
                        CheckedCollection.checkModCount(modCount,this.modCount);
                        return false;
                    }
                }while(!nonNull.equals((curr=(prev=curr).next).val));
                CheckedCollection.checkModCount(modCount,this.modCount);
                if(curr==tail){
                    staticSetTail(this,prev);
                }else{
                    joinNodes(prev,curr.next);
                }
            }
        }catch(final RuntimeException e){
            throw CheckedCollection.checkModCount(modCount,this.modCount,e);
        }
        this.modCount=modCount+1;
        --size;
        return true;
    }
    @Override
    public E set(int index,E val){
        CheckedCollection.checkLo(index);
        int size;
        CheckedCollection.checkReadHi(index,size=this.size);
        Node<E> node;
        final var oldVal=(node=staticGetNode(this,index,size-index)).val;
        node.val=val;
        return oldVal;
    }
    @Override
    public void put(int index,E val){
        CheckedCollection.checkLo(index);
        int size;
        CheckedCollection.checkReadHi(index,size=this.size);
        staticGetNode(this,index,size-index).val=val;
    }
    @Override
    public E get(int index){
        CheckedCollection.checkLo(index);
        int size;
        CheckedCollection.checkReadHi(index,size=this.size);
        return staticGetNode(this,index,size-index).val;
    }
    @Override
    public void add(int index,E val){
        CheckedCollection.checkLo(index);
        int size;
        CheckedCollection.checkWriteHi(index,size=this.size);
        ++modCount;
        if(size!=0){
            if(index==0){
                head=new Node<>(val,head);
            }else{
                int tailDist;
                if((tailDist=size-index)==0){
                    tail=new Node<>(tail,val);
                }else{
                    staticInsertNode(this,index,val,tailDist);
                }
            }
        }else{
            staticInit(this,new Node<>(val));
        }
        this.size=size+1;
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
        Node<E> newHead,newTail;
        int size;
        if((size=this.size)!=0){
            Node<E> oldHead,oldTail;
            for(newHead=new Node<>((oldHead=head).val),newTail=newHead,oldTail=tail;oldHead!=oldTail;newTail=new Node<>(
                    newTail,(oldHead=oldHead.next).val)){}
        }else{
            newHead=null;
            newTail=null;
        }
        return new CheckedRefDblLnkList<>(newHead,size,newTail);
    }
    @Override
    public OmniIterator.OfRef<E> descendingIterator(){
        return new DescendingItr<>(this);
    }
    @Override
    public E element(){
        return super.getFirst();
    }
    @Override
    public boolean equals(Object val){
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public void forEach(Consumer<? super E> action){
        Node<E> head;
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
    public E getLast(){
        Node<E> tail;
        if((tail=this.tail)!=null){
            return tail.val;
        }
        throw new NoSuchElementException();
    }
    @Override
    public int hashCode(){
        Node<E> head;
        if((head=this.head)!=null){
            final int modCount=this.modCount;
            try{
                return uncheckedForwardHashCode(head,tail);
            }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
            }
        }
        return -1;
    }
    @Override
    public OmniIterator.OfRef<E> iterator(){
        return new AscendingItr<>(this);
    }
    @Override
    public OmniListIterator.OfRef<E> listIterator(){
        return new BidirectionalItr<>(this);
    }
    @Override
    public OmniListIterator.OfRef<E> listIterator(int index){
        CheckedCollection.checkLo(index);
        int size;
        CheckedCollection.checkWriteHi(index,size=this.size);
        return new BidirectionalItr<>(this,super.getItrNode(index,size-index),index);
    }
    @Override
    public boolean offer(E val){
        super.addLast(val);
        return true;
    }
    @Override
    public boolean offerFirst(E val){
        super.addFirst(val);
        return true;
    }
    @Override
    public boolean offerLast(E val){
        super.addLast(val);
        return true;
    }
    @Override
    public E poll(){
        return super.pollFirst();
    }
    @Override
    public E pollLast(){
        Node<E> tail;
        if((tail=this.tail)!=null){
            ++modCount;
            --size;
            staticEraseTail(this,tail);
            return tail.val;
        }
        return null;
    }
    @Override
    public E pop(){
        return super.removeFirst();
    }
    @Override
    public void push(E val){
        super.addFirst(val);
    }
    @Override
    public E remove(){
        return super.removeFirst();
    }
    @Override
    public E remove(int index){
        CheckedCollection.checkLo(index);
        int size;
        CheckedCollection.checkReadHi(index,size=this.size);
        ++modCount;
        Node<E> node;
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
    public boolean removeIf(Predicate<? super E> filter){
        Node<E> head;
        if((head=this.head)!=null){
            final int modCount=this.modCount;
            try{
                final Node<E> tail=this.tail;
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
        }
        return false;
    }
    @Override
    public E removeLast(){
        Node<E> tail;
        if((tail=this.tail)!=null){
            ++modCount;
            --size;
            staticEraseTail(this,tail);
            return tail.val;
        }
        throw new NoSuchElementException();
    }
    @Override
    public boolean removeLastOccurrence(boolean val){
        final Node<E> tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeLastOccurrence(Boolean val){
        final Node<E> tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeLastOccurrence(byte val){
        final Node<E> tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeLastOccurrence(Byte val){
        final Node<E> tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeLastOccurrence(char val){
        final Node<E> tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeLastOccurrence(Character val){
        final Node<E> tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeLastOccurrence(double val){
        final Node<E> tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeLastOccurrence(Double val){
        final Node<E> tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeLastOccurrence(float val){
        final Node<E> tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeLastOccurrence(Float val){
        final Node<E> tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeLastOccurrence(int val){
        final Node<E> tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeLastOccurrence(Integer val){
        final Node<E> tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeLastOccurrence(long val){
        final Node<E> tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeLastOccurrence(Long val){
        final Node<E> tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeLastOccurrence(Object val){
        Node<E> tail;
        if((tail=this.tail)!=null){
            if(val!=null){
                return uncheckedRemoveLastNonNull(tail,val);
            }
            return uncheckedRemoveLastMatch(tail,Objects::isNull);
        }
        return false;
    }
    @Override
    public boolean removeLastOccurrence(short val){
        final Node<E> tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public boolean removeLastOccurrence(Short val){
        final Node<E> tail;
        return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
    }
    @Override
    public void replaceAll(UnaryOperator<E> operator){
        Node<E> head;
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
    public void reverseSort(){
        int size;
        if((size=this.size)>1){
            final int modCount=this.modCount;
            try{
                uncheckedSort(head,size,tail,Comparator.reverseOrder());
            }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
            }
            this.modCount=modCount+1;
        }
    }
    @Override
    public void sort(){
        int size;
        if((size=this.size)>1){
            final int modCount=this.modCount;
            try{
                uncheckedSort(head,size,tail,Comparator.naturalOrder());
            }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
            }
            this.modCount=modCount+1;
        }
    }
    @Override
    public void sort(Comparator<? super E> sorter){
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
    public OmniList.OfRef<E> subList(int fromIndex,int toIndex){
        int size;
        CheckedCollection.CheckSubListRange(fromIndex,toIndex,size=this.size);
        size-=toIndex;
        int subListSize;
        switch(subListSize=toIndex-fromIndex){
        default:
            return getDefaultSubList(fromIndex,subListSize,size);
        case 1:
            return getSubList1(fromIndex,size);
        case 0:
            return getEmptySubList(fromIndex,size);
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
    @Override
    public String toString(){
        Node<E> head;
        if((head=this.head)!=null){
            final StringBuilder builder=new StringBuilder("[");
            final int modCount=this.modCount;
            try{
                uncheckedForwardToString(head,tail,builder);
            }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
            }
            return builder.append(']').toString();
        }
        return "[]";
    }
    @Override
    protected int uncheckedLastIndexOfNonNull(int size,Object nonNull){
        final int modCount=this.modCount;
        try{
            return super.uncheckedLastIndexOfNonNull(size,nonNull);
        }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
        }
    }
    private boolean collapseBody(int modCount,Node<E> prev,Node<E> next,Predicate<? super E> filter){
        int numLeft=size;
        int numConsumed=2;
        for(Node<E> before;numConsumed!=numLeft;prev=before){
            ++numConsumed;
            if(filter.test((before=prev.next).val)){
                int newSize=numConsumed-1;
                for(Node<E> after;;next=after,++newSize){
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
    private void collapseHead(int modCount,Node<E> headCandidate,Node<E> oldTail,Predicate<? super E> filter){
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
    private void collapseHeadAndTail(int modCount,Node<E> headCandidate,Node<E> tailCandidate,
            Predicate<? super E> filter){
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
    private void collapseTail(int modCount,Node<E> oldHead,Node<E> tailCandidate,Predicate<? super E> filter){
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
    private OmniList.OfRef<E> getDefaultSubList(int headDist,int subListSize,int tailDist){
        final Node<E> subListHead=head;
        Node<E> subListTail=tail;
        if(tailDist==0){
            if(headDist==0){
                return new RootSubList<>(this,null,subListHead,subListSize,subListTail);
            }
            return new SuffixSubList<>(this,null,headDist<=subListSize?uncheckedIterateForward(subListHead,headDist)
                    :uncheckedIterateReverse(subListTail,subListSize),subListSize,subListTail);
        }
        subListTail=tailDist<=subListSize?uncheckedIterateReverse(subListTail,tailDist)
                :uncheckedIterateForward(subListHead,subListSize);
        if(headDist==0){
            return new PrefixSubList<>(this,null,subListHead,subListSize,subListTail);
        }
        return new BodySubList<>(this,null,headDist<=subListSize?uncheckedIterateForward(subListHead,headDist)
                :uncheckedIterateReverse(subListTail,subListSize),subListSize,subListTail,headDist);
    }
    private OmniList.OfRef<E> getEmptySubList(int headDist,int tailDist){
        if(tailDist==0){
            if(headDist==0){
                return new RootSubList<>(this,null);
            }
            return new SuffixSubList<>(this,null);
        }else if(headDist==0){
            return new PrefixSubList<>(this,null);
        }
        return new BodySubList<>(this,null,headDist);
    }
    private OmniList.OfRef<E> getSubList1(int headDist,int tailDist){
        if(tailDist==0){
            if(headDist==0){
                return new RootSubList<>(this,null,head);
            }
            return new SuffixSubList<>(this,null,tail);
        }else if(headDist==0){
            return new PrefixSubList<>(this,null,head);
        }
        return new BodySubList<>(this,null,staticGetNode(this,headDist,tailDist),headDist);
    }
    private boolean uncheckedRemoveLastMatch(Node<E> curr,Predicate<Object> pred){
        final var head=this.head;
        if(pred.test(curr.val)){
            if(curr==head){
                staticInit(this,null);
            }else{
                staticEraseTail(this,curr);
            }
        }else{
            Node<E> next;
            do{
                if(curr==head){
                    return false;
                }
            }while(!pred.test((curr=(next=curr).prev).val));
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
    private boolean uncheckedRemoveLastNonNull(Node<E> curr,Object nonNull){
        final int modCount=this.modCount;
        try{
            final var head=this.head;
            if(nonNull.equals(curr.val)){
                CheckedCollection.checkModCount(modCount,this.modCount);
                if(curr==head){
                    staticInit(this,null);
                }else{
                    staticEraseTail(this,curr);
                }
            }else{
                Node<E> next;
                do{
                    if(curr==head){
                        CheckedCollection.checkModCount(modCount,this.modCount);
                        return false;
                    }
                }while(!nonNull.equals((curr=(next=curr).prev).val));
                CheckedCollection.checkModCount(modCount,this.modCount);
                if(curr==head){
                    staticSetHead(this,next);
                }else{
                    joinNodes(curr.prev,next);
                }
            }
        }catch(final RuntimeException e){
            throw CheckedCollection.checkModCount(modCount,this.modCount,e);
        }
        this.modCount=modCount+1;
        --size;
        return true;
    }
    private static class AscendingItr<E> implements OmniIterator.OfRef<E>{
        transient final Checked<E> root;
        transient Node<E> cursor;
        transient Node<E> lastRet;
        transient int modCount;
        private AscendingItr(Checked<E> root){
            this.root=root;
            this.cursor=root.head;
            this.modCount=root.modCount;
        }
        private AscendingItr(Checked<E> root,Node<E> cursor){
            this.root=root;
            this.cursor=cursor;
            this.modCount=root.modCount;
        }
        @Override
        public void forEachRemaining(Consumer<? super E> action){
            Node<E> cursor;
            if((cursor=this.cursor)!=null){
                final int modCount=this.modCount;
                final var root=this.root;
                try{
                    cursor=uncheckedForEach(cursor,root,action);
                }finally{
                    CheckedCollection.checkModCount(modCount,root.modCount);
                }
                this.cursor=null;
                this.lastRet=cursor;
            }
        }
        @Override
        public boolean hasNext(){
            return cursor!=null;
        }
        @Override
        public E next(){
            CheckedCollection.checkModCount(modCount,root.modCount);
            Node<E> cursor;
            if((cursor=this.cursor)!=null){
                this.cursor=iterate(cursor);
                this.lastRet=cursor;
                return cursor.val;
            }
            throw new NoSuchElementException();
        }
        @Override
        public void remove(){
            Node<E> lastRet;
            if((lastRet=this.lastRet)!=null){
                int modCount;
                Checked<E> root;
                CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
                root.modCount=++modCount;
                this.modCount=modCount;
                uncheckedRemove(lastRet,root);
                this.lastRet=null;
                return;
            }
            throw new IllegalStateException();
        }
        Node<E> iterate(Node<E> cursor){
            return cursor.next;
        }
        Node<E> uncheckedForEach(Node<E> cursor,Checked<E> root,Consumer<? super E> action){
            uncheckedForEachForward(cursor,cursor=root.tail,action);
            return cursor;
        }
        void uncheckedRemove(Node<E> lastRet,Checked<E> root){
            if(--root.size==0){
                staticInit(root,null);
            }else{
                if(lastRet==root.head){
                    staticSetHead(root,cursor);
                }else if(lastRet==root.tail){
                    staticEraseTail(root,lastRet);
                }else{
                    joinNodes(lastRet.prev,cursor);
                }
            }
        }
    }
    private static class BidirectionalItr<E>extends AscendingItr<E> implements OmniListIterator.OfRef<E>{
        private transient int nextIndex;
        private BidirectionalItr(Checked<E> root){
            super(root);
        }
        private BidirectionalItr(Checked<E> root,Node<E> cursor,int index){
            super(root,cursor);
            this.nextIndex=index;
        }
        @Override
        public void add(E val){
            int modCount;
            Checked<E> root;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            this.modCount=modCount;
            if(++root.size!=1){
                Node<E> cursor;
                if((cursor=this.cursor)==null){
                    root.tail=new Node<>(root.tail,val);
                }else if(cursor==root.head){
                    root.head=new Node<>(val,cursor);
                }else{
                    new Node<>(cursor.prev,val,cursor);
                }
            }else{
                staticInit(root,new Node<>(val));
            }
            ++nextIndex;
            lastRet=null;
        }
        @Override
        public void forEachRemaining(Consumer<? super E> action){
            Node<E> cursor;
            if((cursor=this.cursor)!=null){
                final int modCount=this.modCount;
                final var root=this.root;
                try{
                    uncheckedForEachForward(cursor,cursor=root.tail,action);
                }finally{
                    CheckedCollection.checkModCount(modCount,root.modCount);
                }
                this.nextIndex=root.size;
                this.cursor=null;
                lastRet=cursor;
            }
        }
        @Override
        public boolean hasPrevious(){
            return this.nextIndex!=0;
        }
        @Override
        public E next(){
            CheckedCollection.checkModCount(modCount,root.modCount);
            Node<E> cursor;
            if((cursor=this.cursor)!=null){
                ++nextIndex;
                this.cursor=cursor.next;
                lastRet=cursor;
                return cursor.val;
            }
            throw new NoSuchElementException();
        }
        @Override
        public int nextIndex(){
            return this.nextIndex;
        }
        @Override
        public E previous(){
            CheckedCollection.checkModCount(modCount,root.modCount);
            int nextIndex;
            if((nextIndex=this.nextIndex)!=0){
                this.nextIndex=nextIndex-1;
                Node<E> cursor;
                this.cursor=cursor=this.cursor.prev;
                lastRet=cursor;
                return cursor.val;
            }
            throw new NoSuchElementException();
        }
        @Override
        public int previousIndex(){
            return this.nextIndex-1;
        }
        @Override
        public void set(E val){
            Node<E> lastRet;
            if((lastRet=this.lastRet)!=null){
                CheckedCollection.checkModCount(modCount,root.modCount);
                lastRet.val=val;
                return;
            }
            throw new IllegalStateException();
        }
        @Override
        void uncheckedRemove(Node<E> lastRet,Checked<E> root){
            if(--root.size!=0){
                Node<E> cursor;
                if(lastRet!=(cursor=this.cursor)){
                    --nextIndex;
                    if(lastRet==root.head){
                        staticSetHead(root,cursor);
                    }else if(lastRet==root.tail){
                        staticEraseTail(root,lastRet);
                    }else{
                        joinNodes(lastRet.prev,cursor);
                    }
                }else{
                    if(lastRet==root.head){
                        staticEraseHead(root,lastRet);
                    }else if(lastRet==root.tail){
                        staticEraseTail(root,lastRet);
                    }else{
                        joinNodes(lastRet.prev,lastRet.next);
                    }
                }
            }else{
                staticInit(root,null);
                if(lastRet!=cursor){
                    --nextIndex;
                }
            }
        }
    }
    private static class BodySubList<E>extends PrefixSubList<E>{
        transient final int parentOffset;
        private BodySubList(Checked<E> root,RootSubList<E> parent,int parentOffset){
            super(root,parent);
            this.parentOffset=parentOffset;
        }
        private BodySubList(Checked<E> root,RootSubList<E> parent,Node<E> onlyNode,int parentOffset){
            super(root,parent,onlyNode);
            this.parentOffset=parentOffset;
        }
        private BodySubList(Checked<E> root,RootSubList<E> parent,Node<E> head,int size,Node<E> tail,int parentOffset){
            super(root,parent,head,size,tail);
            this.parentOffset=parentOffset;
        }
        @Override
        public void clear(){
            Checked<E> root;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            int size;
            if((size=this.size)!=0){
                root.modCount=++modCount;
                bubbleUpClear(modCount,size,head,tail);
                root.size-=size;
            }
        }
        @Override
        public OmniList.OfRef<E> subList(int fromIndex,int toIndex){
            Checked<E> root;
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            int size;
            CheckedCollection.CheckSubListRange(fromIndex,toIndex,size=this.size);
            int subListSize;
            switch(subListSize=toIndex-fromIndex){
            default:
                return getDefaultSubList(root,fromIndex,subListSize,size-toIndex);
            case 1:
                return getSubList1(root,fromIndex,size-toIndex);
            case 0:
                return getEmptySubList(root,fromIndex);
            }
        }
        @Override
        void ascItrRemove(RootSubList.AscendingItr<E> itr){
            Node<E> lastRet;
            if((lastRet=itr.lastRet)!=null){
                int modCount;
                Checked<E> root;
                CheckedCollection.checkModCount(modCount=itr.modCount,(root=this.root).modCount);
                root.modCount=++modCount;
                uncheckedItrRemove(lastRet,modCount);
                itr.modCount=modCount;
                --root.size;
                return;
            }
            throw new IllegalStateException();
        }
        @Override
        void bidirectItrRemove(RootSubList.BidirectionalItr<E> itr){
            Node<E> lastRet;
            if((lastRet=itr.lastRet)!=null){
                int modCount;
                Checked<E> root;
                CheckedCollection.checkModCount(modCount=itr.modCount,(root=this.root).modCount);
                root.modCount=++modCount;
                uncheckedItrRemove(lastRet,modCount);
                if(lastRet!=itr.cursor){
                    --itr.nextIndex;
                }
                itr.modCount=modCount;
                --root.size;
                return;
            }
            throw new IllegalStateException();
        }
        @Override
        void findNewHead(int modCount,Node<E> curr,Predicate<? super E> filter){
            final Node<E> tail;
            if(curr==(tail=this.tail)){
                Checked<E> root;
                CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
                bubbleUpClear(++modCount,1,head,tail);
                root.modCount=modCount;
                --root.size;
            }else if(filter.test(tail.val)){
                collapseHeadAndTail(modCount,curr,tail,filter);
            }else{
                ((RootSubList<E>)this).suffixCollapseHead(modCount,curr,tail,filter);
            }
        }
        @Override
        int getParentOffset(){
            return this.parentOffset;
        }
        @Override
        void initHelper(Checked<E> root,E val,int modCount){
            final Node<E> newNode;
            ((RootSubList<E>)this).privateInit(newNode=new Node<>(val),modCount);
            RootSubList<E> parent,curr;
            if((parent=(curr=this).parent)!=null){
                do{
                    int parentSize;
                    if((parentSize=parent.size)!=0){
                        Node<E> before,after;
                        int headDist,tailDist;
                        if((headDist=curr.getParentOffset())==0){
                            parent.bubbleUpSuffixPushHead(modCount,newNode,after=parent.head);
                            before=after.prev;
                        }else if((tailDist=parentSize-headDist)==0){
                            parent.bubbleUpPrefixPushTail(modCount,newNode,before=parent.tail);
                            after=before.next;
                        }else{
                            if(headDist<=tailDist){
                                after=(before=iterateForward(root.head,headDist-1)).next;
                            }else{
                                before=(after=iterateReverse(root.tail,tailDist-1)).prev;
                            }
                            parent.bubbleUpIncrementSize(modCount);
                        }
                        joinNodes(before,newNode);
                        joinNodes(newNode,after);
                        return;
                    }
                    parent=(curr=parent).parent;
                    curr.privateInit(newNode,modCount);
                    curr.size=1;
                }while(parent!=null);
            }
            subSeqInsertHelper(root,newNode,curr.getParentOffset());
        }
        @Override
        void prependHelper(E val,int modCount){
            this.modCount=modCount;
            Node<E> newNode,oldHead;
            head=newNode=new Node<>((oldHead=head).prev,val,oldHead);
            RootSubList<E> parent;
            if((parent=this.parent)!=null){
                parent.bubbleUpSuffixPushHead(modCount,newNode,oldHead);
            }
        }
        @Override
        void removeFirstHelper(int modCount,Node<E> curr){
            Checked<E> root;
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            if(curr==tail){
                bubbleUpClear(modCount,1,head,curr);
            }else{
                ((RootSubList<E>)this).bubbleUpSuffixEraseHead(modCount,curr,curr.next);
            }
            --root.size;
        }
        @Override
        Node<E> uncheckedExtractHead(int newSize){
            Checked<E> root;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            Node<E> oldHead;
            ((RootSubList<E>)this).bubbleUpSuffixEraseHead(modCount,oldHead=head,oldHead.next);
            --root.size;
            return oldHead;
        }
        @Override
        Node<E> uncheckedExtractLastNode(){
            final Node<E> lastNode;
            final Checked<E> root;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            bubbleUpClear(modCount,1,lastNode=head,lastNode);
            --root.size;
            return lastNode;
        }
        private void bubbleUpClear(int modCount,int size,Node<E> head,Node<E> tail){
            final Node<E> prev=head.prev,next=tail.next;
            for(RootSubList<E> curr=this;;){
                curr.modCount=modCount;
                staticClear(curr);
                if((curr=curr.parent)!=null){
                    if(curr.head!=head){
                        curr.bubbleUpPrefixCollapseTail(modCount,size,tail,prev);
                        break;
                    }else if(curr.tail!=tail){
                        curr.modCount=modCount;
                        curr.size-=size;
                        curr.head=next;
                        if((curr=curr.parent)!=null){
                            curr.bubbleUpSuffixCollapseHead(modCount,size,head,next);
                        }
                        break;
                    }
                    continue;
                }
                break;
            }
            joinNodes(prev,next);
        }
        private void bubbleUpCollapseHeadAndTail(int newModCount,Node<E> oldHead,Node<E> oldTail,Node<E> newHead,
                Node<E> newTail,int numRemoved){
            final int newSize=size-numRemoved;
            for(RootSubList<E> curr=this;;){
                curr.privateCollapseHeadAndTail(newModCount,newSize,newHead,newTail);
                if((curr=curr.parent)==null){
                    break;
                }
                if(curr.head!=oldHead){
                    curr.bubbleUpPrefixCollapseTail(newModCount,numRemoved,oldTail,newTail);
                    break;
                }else if(curr.tail!=oldTail){
                    curr.modCount=newModCount;
                    curr.size-=numRemoved;
                    curr.head=newHead;
                    if((curr=curr.parent)!=null){
                        curr.bubbleUpSuffixCollapseHead(newModCount,numRemoved,oldHead,newHead);
                    }
                    break;
                }
            }
            joinNodes(oldHead.prev,newHead);
            joinNodes(newTail,oldTail.next);
        }
        private void collapseHeadAndTail(int modCount,final Node<E> head,final Node<E> tail,
                final Predicate<? super E> filter){
            final int oldSize=size;
            int numConsumed=2;
            var headCandidate=head;
            final var root=this.root;
            for(;;){
                if(numConsumed==oldSize){
                    CheckedCollection.checkModCount(modCount,root.modCount);
                    bubbleUpClear(++modCount,oldSize,head,tail);
                    break;
                }
                ++numConsumed;
                if(!filter.test((headCandidate=headCandidate.next).val)){
                    var tailCandidate=tail;
                    for(;;){
                        if(numConsumed==oldSize){
                            CheckedCollection.checkModCount(modCount,root.modCount);
                            --numConsumed;
                            break;
                        }
                        ++numConsumed;
                        if(!filter.test((tailCandidate=tailCandidate.prev).val)){
                            numConsumed-=2-collapseBodyHelper(root.new ModCountChecker(modCount),headCandidate,
                                    oldSize-numConsumed,tailCandidate,filter);
                            break;
                        }
                    }
                    bubbleUpCollapseHeadAndTail(++modCount,head,tail,headCandidate,tailCandidate,numConsumed);
                    break;
                }
            }
            root.modCount=modCount;
            root.size-=numConsumed;
        }
        private OmniList.OfRef<E> getDefaultSubList(Checked<E> root,int headDist,int subListSize,int tailDist){
            Node<E> subListHead,subListTail;
            if(headDist<=tailDist){
                subListHead=iterateForward(head,headDist);
                subListTail=tailDist<subListSize?iterateReverse(tail,tailDist)
                        :uncheckedIterateForward(subListHead,subListSize);
            }else{
                subListTail=iterateReverse(tail,tailDist);
                subListHead=headDist<subListSize?iterateForward(head,headDist)
                        :uncheckedIterateReverse(subListTail,subListSize);
            }
            return new BodySubList<>(root,this,subListHead,subListSize,subListTail,headDist);
        }
        private OmniList.OfRef<E> getEmptySubList(Checked<E> root,int headDist){
            return new BodySubList<>(root,this,headDist);
        }
        private OmniList.OfRef<E> getSubList1(Checked<E> root,int headDist,int tailDist){
            return new BodySubList<>(root,this,staticGetNode(this,headDist,tailDist),headDist);
        }
        private void uncheckedItrRemove(Node<E> lastRet,int modCount){
            if(lastRet==head){
                if(lastRet==tail){
                    bubbleUpClear(modCount,1,lastRet,lastRet);
                }else{
                    ((RootSubList<E>)this).bubbleUpSuffixEraseHead(modCount,lastRet,lastRet.next);
                }
            }else{
                if(lastRet==tail){
                    super.bubbleUpPrefixEraseTail(modCount,lastRet,lastRet.prev);
                }else{
                    ((RootSubList<E>)this).bubbleUpDecrementSize(modCount);
                    joinNodes(lastRet.prev,lastRet.next);
                }
            }
        }
    }
    private static class DescendingItr<E>extends AscendingItr<E>{
        private DescendingItr(Checked<E> root){
            super(root,root.tail);
        }
        @Override
        Node<E> iterate(Node<E> cursor){
            return cursor.prev;
        }
        @Override
        Node<E> uncheckedForEach(Node<E> cursor,Checked<E> root,Consumer<? super E> action){
            uncheckedForEachReverse(cursor,action);
            return root.head;
        }
        @Override
        void uncheckedRemove(Node<E> lastRet,Checked<E> root){
            if(--root.size==0){
                staticInit(root,null);
            }else{
                if(lastRet==root.head){
                    staticEraseHead(root,lastRet);
                }else if(lastRet==root.tail){
                    staticSetTail(root,cursor);
                }else{
                    joinNodes(cursor,lastRet.next);
                }
            }
        }
    }
    private static class PrefixSubList<E>extends RootSubList<E>{
        private PrefixSubList(Checked<E> root,RootSubList<E> parent){
            super(root,parent);
        }
        private PrefixSubList(Checked<E> root,RootSubList<E> parent,Node<E> onlyNode){
            super(root,parent,onlyNode);
        }
        private PrefixSubList(Checked<E> root,RootSubList<E> parent,Node<E> head,int size,Node<E> tail){
            super(root,parent,head,size,tail);
        }
        @Override
        public void clear(){
            int modCount;
            Checked<E> root;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            int size;
            if((size=this.size)!=0){
                root.modCount=++modCount;
                Node<E> tmp;
                bubbleUpClear(modCount,size,tmp=tail,tmp=tmp.next);
                staticSetHead(root,tmp);
                root.size-=size;
            }
        }
        @Override
        public OmniList.OfRef<E> subList(int fromIndex,int toIndex){
            Checked<E> root;
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            int size;
            CheckedCollection.CheckSubListRange(fromIndex,toIndex,size=this.size);
            int subListSize;
            switch(subListSize=toIndex-fromIndex){
            default:
                return getDefaultSubList(root,fromIndex,subListSize,size-toIndex);
            case 1:
                return getSubList1(root,fromIndex,size-toIndex);
            case 0:
                return getEmptySubList(root,fromIndex);
            }
        }
        @Override
        void appendHelper(E val,int modCount){
            this.modCount=modCount;
            Node<E> newNode,oldTail;
            tail=newNode=new Node<>(oldTail=tail,val,oldTail.next);
            RootSubList<E> parent;
            if((parent=this.parent)!=null){
                parent.bubbleUpPrefixPushTail(modCount,newNode,oldTail);
            }
        }
        @Override
        void ascItrRemove(RootSubList.AscendingItr<E> itr){
            Node<E> lastRet;
            if((lastRet=itr.lastRet)!=null){
                int modCount;
                Checked<E> root;
                CheckedCollection.checkModCount(modCount=itr.modCount,(root=this.root).modCount);
                root.modCount=++modCount;
                uncheckedItrRemove(lastRet,modCount,root);
                itr.modCount=modCount;
                --root.size;
                return;
            }
            throw new IllegalStateException();
        }
        @Override
        void bidirectItrRemove(RootSubList.BidirectionalItr<E> itr){
            Node<E> lastRet;
            if((lastRet=itr.lastRet)!=null){
                int modCount;
                Checked<E> root;
                CheckedCollection.checkModCount(modCount=itr.modCount,(root=this.root).modCount);
                root.modCount=++modCount;
                uncheckedItrRemove(lastRet,modCount,root);
                if(lastRet!=itr.cursor){
                    --itr.nextIndex;
                }
                itr.modCount=modCount;
                --root.size;
                return;
            }
            throw new IllegalStateException();
        }
        @Override
        void findNewHead(int modCount,Node<E> curr,Predicate<? super E> filter){
            final Node<E> tail;
            if(curr==(tail=this.tail)){
                Checked<E> root;
                CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
                root.modCount=++modCount;
                bubbleUpClear(modCount,1,curr,curr=tail.next);
                staticSetHead(root,curr);
                --root.size;
            }else if(filter.test(tail.val)){
                collapseHeadAndTail(modCount,curr,tail,filter);
            }else{
                super.rootCollapseHead(modCount,curr.next,tail,filter);
            }
        }
        @Override
        void initHelper(Checked<E> root,E val,int modCount){
            final Node<E> after,newNode=new Node<>(val);
            for(RootSubList<E> curr=this;;curr.size=1){
                curr.privateInit(newNode,modCount);
                if((curr=curr.parent)==null){
                    after=root.head;
                    break;
                }else if(curr.size!=0){
                    after=curr.head;
                    curr.bubbleUpRootPushHead(modCount,newNode);
                    break;
                }
            }
            joinNodes(newNode,after);
        }
        @Override
        final boolean removeIfHelper(int modCount,Node<E> curr,Predicate<? super E> filter){
            final Node<E> tail;
            if(curr!=(tail=this.tail)){
                if(filter.test(tail.val)){
                    prefixCollapseTail(modCount,curr,tail,filter);
                    return true;
                }
                return super.collapseBody(modCount,curr,tail,filter);
            }
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
        }
        @Override
        Node<E> uncheckedExtractLastNode(){
            Checked<E> root;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            final Node<E> next,lastNode;
            staticSetHead(root,next=(lastNode=tail).next);
            bubbleUpClear(modCount,1,lastNode,next);
            --root.size;
            return lastNode;
        }
        @Override
        final Node<E> uncheckedExtractTail(int newSize){
            Checked<E> root;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            Node<E> oldTail;
            bubbleUpPrefixEraseTail(modCount,oldTail=tail,oldTail.prev);
            --root.size;
            return oldTail;
        }
        @Override
        final boolean uncheckedRemoveFirstMatchHelper(int modCount,Node<E> curr,Predicate<Object> pred){
            final var root=this.root;
            final var tail=this.tail;
            Node<E> prev;
            do{
                if(curr==tail){
                    CheckedCollection.checkModCount(modCount,root.modCount);
                    return false;
                }
            }while(!pred.test((curr=(prev=curr).next).val));
            CheckedCollection.checkModCount(modCount,root.modCount);
            root.modCount=++modCount;
            --root.size;
            if(curr==tail){
                bubbleUpPrefixEraseTail(modCount,curr,prev);
            }else{
                super.bubbleUpDecrementSize(modCount);
                joinNodes(prev,curr.next);
            }
            return true;
        }
        private void bubbleUpClear(int modCount,int size,Node<E> tail,final Node<E> next){
            for(RootSubList<E> curr=this;;){
                curr.modCount=modCount;
                staticClear(curr);
                if((curr=curr.parent)==null){
                    break;
                }else if(curr.tail!=tail){
                    curr.bubbleUpRootCollapseHead(modCount,size,next);
                    break;
                }
            }
        }
        private void bubbleUpCollapseHeadAndTail(int newModCount,Node<E> oldTail,Node<E> newHead,Node<E> newTail,
                int numRemoved){
            final int newSize=size-numRemoved;
            for(RootSubList<E> curr=this;;){
                curr.privateCollapseHeadAndTail(newModCount,newSize,newHead,newTail);
                if((curr=curr.parent)==null){
                    break;
                }
                if(curr.tail!=oldTail){
                    curr.bubbleUpRootCollapseHead(newModCount,numRemoved,newHead);
                    break;
                }
            }
            joinNodes(newTail,oldTail.next);
        }
        private void bubbleUpPrefixEraseTail(int newModCount,Node<E> oldTail,Node<E> newTail){
            RootSubList<E> curr=this;
            do{
                curr.modCount=newModCount;
                --curr.size;
                curr.tail=newTail;
                if((curr=curr.parent)==null){
                    return;
                }
            }while(curr.tail==oldTail);
            curr.bubbleUpDecrementSize(newModCount);
            joinNodes(newTail,oldTail.next);
        }
        private void collapseHeadAndTail(int modCount,Node<E> headCandidate,Node<E> tail,Predicate<? super E> filter){
            final int oldSize=size;
            int numConsumed=2;
            final var root=this.root;
            for(;;){
                if(numConsumed==oldSize){
                    CheckedCollection.checkModCount(modCount,root.modCount);
                    bubbleUpClear(++modCount,oldSize,tail,headCandidate=tail.next);
                    break;
                }
                ++numConsumed;
                if(!filter.test((headCandidate=headCandidate.next).val)){
                    var tailCandidate=tail;
                    for(;;){
                        if(numConsumed==oldSize){
                            CheckedCollection.checkModCount(modCount,root.modCount);
                            --numConsumed;
                            break;
                        }
                        ++numConsumed;
                        if(!filter.test((tailCandidate=tailCandidate.prev).val)){
                            numConsumed-=2-collapseBodyHelper(root.new ModCountChecker(modCount),headCandidate,
                                    oldSize-numConsumed,tailCandidate,filter);
                            break;
                        }
                    }
                    bubbleUpCollapseHeadAndTail(++modCount,tail,headCandidate,tailCandidate,numConsumed);
                    break;
                }
            }
            root.modCount=modCount;
            staticSetHead(root,headCandidate);
            root.size-=numConsumed;
        }
        private OmniList.OfRef<E> getDefaultSubList(Checked<E> root,int headDist,int subListSize,int tailDist){
            Node<E> subListHead=head,subListTail=tail;
            if(headDist==0){
                return new PrefixSubList<>(root,this,subListHead,subListSize,
                        tailDist<=subListSize?iterateReverse(subListTail,tailDist)
                                :uncheckedIterateForward(subListHead,subListSize));
            }
            if(headDist<=tailDist){
                subListHead=uncheckedIterateForward(subListHead,headDist);
                subListTail=tailDist<=subListSize?iterateReverse(subListTail,tailDist)
                        :uncheckedIterateForward(subListHead,subListSize);
            }else{
                subListTail=iterateReverse(subListTail,tailDist);
                subListHead=headDist<=subListSize?uncheckedIterateForward(subListHead,headDist)
                        :uncheckedIterateReverse(subListTail,subListSize);
            }
            return new BodySubList<>(root,this,subListHead,subListSize,subListTail,headDist);
        }
        private OmniList.OfRef<E> getEmptySubList(Checked<E> root,int headDist){
            if(headDist==0){
                return new PrefixSubList<>(root,this);
            }
            return new BodySubList<>(root,this,headDist);
        }
        private OmniList.OfRef<E> getSubList1(Checked<E> root,int headDist,int tailDist){
            if(headDist==0){
                return new PrefixSubList<>(root,this,head);
            }
            return new BodySubList<>(root,this,
                    tailDist<headDist?iterateReverse(tail,tailDist):uncheckedIterateForward(head,headDist),headDist);
        }
        private void prefixCollapseTail(int modCount,Node<E> oldHead,Node<E> oldTail,Predicate<? super E> filter){
            final int oldSize=size;
            int numConsumed=2;
            final var root=this.root;
            var tailCandidate=oldTail.prev;
            for(;;tailCandidate=tailCandidate.prev){
                if(numConsumed==oldSize){
                    CheckedCollection.checkModCount(modCount,root.modCount);
                    --numConsumed;
                    break;
                }
                ++numConsumed;
                if(!filter.test(tailCandidate.val)){
                    numConsumed-=2-collapseBodyHelper(root.new ModCountChecker(modCount),oldHead,oldSize-numConsumed,
                            tailCandidate,filter);
                    break;
                }
            }
            root.modCount=++modCount;
            super.bubbleUpPrefixCollapseTail(modCount,numConsumed,oldTail,tailCandidate);
            joinNodes(tailCandidate,oldTail.next);
            root.size-=numConsumed;
        }
        private void uncheckedItrRemove(Node<E> lastRet,int modCount,Checked<E> root){
            if(lastRet==head){
                if(lastRet==tail){
                    bubbleUpClear(modCount,1,lastRet,lastRet=lastRet.next);
                }else{
                    super.bubbleUpRootEraseHead(modCount,lastRet=lastRet.next);
                }
                staticSetHead(root,lastRet);
            }else{
                if(lastRet==tail){
                    bubbleUpPrefixEraseTail(modCount,lastRet,lastRet.prev);
                }else{
                    super.bubbleUpDecrementSize(modCount);
                    joinNodes(lastRet.prev,lastRet.next);
                }
            }
        }
    }
    private static class RootSubList<E>extends  AbstractRefDblLnkSeq<E>{
        transient final RootSubList<E> parent;
        transient final Checked<E> root;
        transient int modCount;
        private RootSubList(Checked<E> root,RootSubList<E> parent){
            super();
            this.root=root;
            this.parent=parent;
        }
        private RootSubList(Checked<E> root,RootSubList<E> parent,Node<E> onlyNode){
            super(onlyNode);
            this.root=root;
            this.parent=parent;
        }
        private RootSubList(Checked<E> root,RootSubList<E> parent,Node<E> head,int size,Node<E> tail){
            super(head,size,tail);
            this.root=root;
            this.parent=parent;
        }
        @Override
        public final boolean add(E val){
            int modCount;
            Checked<E> root;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            if(++size!=1){
                appendHelper(val,modCount);
            }else{
                initHelper(root,val,modCount);
            }
            ++root.size;
            return true;
        }
        @Override
        public E get(int index){
            CheckedCollection.checkModCount(modCount,root.modCount);
            CheckedCollection.checkLo(index);
            int size;
            CheckedCollection.checkReadHi(index,size=this.size);
            return staticGetNode(this,index,size-index).val;
        }
        @Override
        public void put(int index,E val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            CheckedCollection.checkLo(index);
            int size;
            CheckedCollection.checkReadHi(index,size=this.size);
            staticGetNode(this,index,size-index).val=val;
        }
        @Override
        public E set(int index,E val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            CheckedCollection.checkLo(index);
            int size;
            CheckedCollection.checkReadHi(index,size=this.size);
            Node<E> node;
            final var oldVal=(node=staticGetNode(this,index,size-index)).val;
            node.val=val;
            return oldVal;
        }
        @Override
        public final void add(int index,E val){
            int modCount;
            Checked<E> root;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            CheckedCollection.checkLo(index);
            int size;
            CheckedCollection.checkWriteHi(index,size=this.size);
            root.modCount=++modCount;
            if(size!=0){
                if(index==0){
                    prependHelper(val,modCount);
                }else{
                    int tailDist;
                    if((tailDist=size-index)==0){
                        appendHelper(val,modCount);
                    }else{
                        Node<E> before,after;
                        if(index<=tailDist){
                            after=(before=iterateForward(head,index-1)).next;
                        }else{
                            before=(after=iterateReverse(tail,tailDist-1)).prev;
                        }
                        insertHelper(before,val,after,modCount);
                    }
                }
            }else{
                initHelper(root,val,modCount);
            }
            ++root.size;
            this.size=size+1;
        }
        @Override
        public void clear(){
            int modCount;
            Checked<E> root;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            if(size!=0){
                root.modCount=++modCount;
                bubbleUpClear(modCount);
                staticClear(root);
            }
        }
        @Override
        public final Object clone(){
            CheckedCollection.checkModCount(modCount,root.modCount);
            Node<E> newHead,newTail;
            int size;
            if((size=this.size)!=0){
                Node<E> oldHead,oldTail;
                for(newHead=new Node<>(
                        (oldHead=head).val),newTail=newHead,oldTail=tail;oldHead!=oldTail;newTail=new Node<>(newTail,
                                (oldHead=oldHead.next).val)){}
            }else{
                newHead=null;
                newTail=null;
            }
            return new CheckedRefDblLnkList<>(newHead,size,newTail);
        }
        @Override
        public final boolean contains(boolean val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return super.contains(val);
        }
        @Override
        public final boolean contains(Boolean val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return super.contains(val);
        }
        @Override
        public final boolean contains(byte val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return super.contains(val);
        }
        @Override
        public final boolean contains(Byte val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return super.contains(val);
        }
        @Override
        public final boolean contains(char val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return super.contains(val);
        }
        @Override
        public final boolean contains(Character val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return super.contains(val);
        }
        @Override
        public final boolean contains(double val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return super.contains(val);
        }
        @Override
        public final boolean contains(Double val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return super.contains(val);
        }
        @Override
        public final boolean contains(float val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return super.contains(val);
        }
        @Override
        public final boolean contains(Float val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return super.contains(val);
        }
        @Override
        public final boolean contains(int val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return super.contains(val);
        }
        @Override
        public final boolean contains(Integer val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return super.contains(val);
        }
        @Override
        public final boolean contains(long val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return super.contains(val);
        }
        @Override
        public final boolean contains(Long val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return super.contains(val);
        }
        @Override
        public final boolean contains(Object val){
            final int modCount=this.modCount;
            try{
                return super.contains(val);
            }finally{
                CheckedCollection.checkModCount(modCount,root.modCount);
            }
        }
        @Override
        public final boolean contains(short val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return super.contains(val);
        }
        @Override
        public final boolean contains(Short val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return super.contains(val);
        }
        @Override
        public boolean equals(Object val){
            // TODO Auto-generated method stub
            return false;
        }
        @Override
        public final void forEach(Consumer<? super E> action){
            final int modCount=this.modCount;
            try{
                super.forEach(action);
            }finally{
                CheckedCollection.checkModCount(modCount,root.modCount);
            }
        }
        @Override
        public final int hashCode(){
            final int modCount=this.modCount;
            try{
                return super.hashCode();
            }finally{
                CheckedCollection.checkModCount(modCount,root.modCount);
            }
        }
        @Override
        public final int indexOf(boolean val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return super.indexOf(val);
        }
        @Override
        public final int indexOf(Boolean val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return super.indexOf(val);
        }
        @Override
        public final int indexOf(byte val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return super.indexOf(val);
        }
        @Override
        public final int indexOf(Byte val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return super.indexOf(val);
        }
        @Override
        public final int indexOf(char val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return super.indexOf(val);
        }
        @Override
        public final int indexOf(Character val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return super.indexOf(val);
        }
        @Override
        public final int indexOf(double val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return super.indexOf(val);
        }
        @Override
        public final int indexOf(Double val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return super.indexOf(val);
        }
        @Override
        public final int indexOf(float val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return super.indexOf(val);
        }
        @Override
        public final int indexOf(Float val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return super.indexOf(val);
        }
        @Override
        public final int indexOf(int val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return super.indexOf(val);
        }
        @Override
        public final int indexOf(Integer val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return super.indexOf(val);
        }
        @Override
        public final int indexOf(long val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return super.indexOf(val);
        }
        @Override
        public final int indexOf(Long val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return super.indexOf(val);
        }
        @Override
        public final int indexOf(Object val){
            final int modCount=this.modCount;
            try{
                return super.indexOf(val);
            }finally{
                CheckedCollection.checkModCount(modCount,root.modCount);
            }
        }
        @Override
        public final int indexOf(short val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return super.indexOf(val);
        }
        @Override
        public final int indexOf(Short val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return super.indexOf(val);
        }
        @Override
        public final boolean isEmpty(){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return size==0;
        }
        @Override
        public final OmniIterator.OfRef<E> iterator(){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return new AscendingItr<>(this);
        }
        @Override
        public final int lastIndexOf(boolean val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            final int size;
            if((size=this.size)!=0){
                return super.uncheckedLastIndexOfMatch(size,OmniPred.OfRef.getEqualsPred(val));
            }
            return -1;
        }
        @Override
        public final int lastIndexOf(Boolean val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            final int size;
            if((size=this.size)!=0){
                return super.uncheckedLastIndexOfMatch(size,OmniPred.OfRef.getEqualsPred(val));
            }
            return -1;
        }
        @Override
        public final int lastIndexOf(byte val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            final int size;
            if((size=this.size)!=0){
                return super.uncheckedLastIndexOfMatch(size,OmniPred.OfRef.getEqualsPred(val));
            }
            return -1;
        }
        @Override
        public final int lastIndexOf(Byte val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            final int size;
            if((size=this.size)!=0){
                return super.uncheckedLastIndexOfMatch(size,OmniPred.OfRef.getEqualsPred(val));
            }
            return -1;
        }
        @Override
        public final int lastIndexOf(char val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            final int size;
            if((size=this.size)!=0){
                return super.uncheckedLastIndexOfMatch(size,OmniPred.OfRef.getEqualsPred(val));
            }
            return -1;
        }
        @Override
        public final int lastIndexOf(Character val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            final int size;
            if((size=this.size)!=0){
                return super.uncheckedLastIndexOfMatch(size,OmniPred.OfRef.getEqualsPred(val));
            }
            return -1;
        }
        @Override
        public final int lastIndexOf(double val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            final int size;
            if((size=this.size)!=0){
                return super.uncheckedLastIndexOfMatch(size,OmniPred.OfRef.getEqualsPred(val));
            }
            return -1;
        }
        @Override
        public final int lastIndexOf(Double val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            final int size;
            if((size=this.size)!=0){
                return super.uncheckedLastIndexOfMatch(size,OmniPred.OfRef.getEqualsPred(val));
            }
            return -1;
        }
        @Override
        public final int lastIndexOf(float val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            final int size;
            if((size=this.size)!=0){
                return super.uncheckedLastIndexOfMatch(size,OmniPred.OfRef.getEqualsPred(val));
            }
            return -1;
        }
        @Override
        public final int lastIndexOf(Float val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            final int size;
            if((size=this.size)!=0){
                return super.uncheckedLastIndexOfMatch(size,OmniPred.OfRef.getEqualsPred(val));
            }
            return -1;
        }
        @Override
        public final int lastIndexOf(int val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            final int size;
            if((size=this.size)!=0){
                return super.uncheckedLastIndexOfMatch(size,OmniPred.OfRef.getEqualsPred(val));
            }
            return -1;
        }
        @Override
        public final int lastIndexOf(Integer val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            final int size;
            if((size=this.size)!=0){
                return super.uncheckedLastIndexOfMatch(size,OmniPred.OfRef.getEqualsPred(val));
            }
            return -1;
        }
        @Override
        public final int lastIndexOf(long val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            final int size;
            if((size=this.size)!=0){
                return super.uncheckedLastIndexOfMatch(size,OmniPred.OfRef.getEqualsPred(val));
            }
            return -1;
        }
        @Override
        public final int lastIndexOf(Long val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            final int size;
            if((size=this.size)!=0){
                return super.uncheckedLastIndexOfMatch(size,OmniPred.OfRef.getEqualsPred(val));
            }
            return -1;
        }
        @Override
        public final int lastIndexOf(Object val){
            final int modCount=this.modCount;
            try{
                final int size;
                if((size=this.size)!=0){
                    if(val!=null){
                        return super.uncheckedLastIndexOfNonNull(size,val);
                    }
                    return super.uncheckedLastIndexOfMatch(size,Objects::isNull);
                }
            }finally{
                CheckedCollection.checkModCount(modCount,root.modCount);
            }
            return -1;
        }
        @Override
        public final int lastIndexOf(short val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            final int size;
            if((size=this.size)!=0){
                return super.uncheckedLastIndexOfMatch(size,OmniPred.OfRef.getEqualsPred(val));
            }
            return -1;
        }
        @Override
        public final int lastIndexOf(Short val){
            CheckedCollection.checkModCount(modCount,root.modCount);
            final int size;
            if((size=this.size)!=0){
                return super.uncheckedLastIndexOfMatch(size,OmniPred.OfRef.getEqualsPred(val));
            }
            return -1;
        }
        @Override
        public final OmniListIterator.OfRef<E> listIterator(){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return new BidirectionalItr<>(this);
        }
        @Override
        public final OmniListIterator.OfRef<E> listIterator(int index){
            CheckedCollection.checkModCount(modCount,root.modCount);
            CheckedCollection.checkLo(index);
            int size;
            CheckedCollection.checkWriteHi(index,size=this.size);
            return new BidirectionalItr<>(this,super.getItrNode(index,size-index),index);
        }
        @Override
        public final E remove(int index){
            CheckedCollection.checkLo(index);
            int size;
            CheckedCollection.checkReadHi(index,size=this.size);
            Node<E> node;
            if(--size!=0){
                if(index==0){
                    node=uncheckedExtractHead(size);
                }else{
                    int tailDist;
                    if((tailDist=size-index)==0){
                        node=uncheckedExtractTail(size);
                    }else{
                        int modCount;
                        Checked<E> root;
                        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
                        root.modCount=++modCount;
                        this.modCount=modCount;
                        RootSubList<E> parent;
                        if((parent=this.parent)!=null){
                            parent.bubbleUpDecrementSize(modCount);
                        }
                        --root.size;
                        node=staticExtractNode(this,index,tailDist);
                    }
                }
            }else{
                node=uncheckedExtractLastNode();
            }
            this.size=size;
            return node.val;
        }
        @Override
        public final boolean remove(Object val){
            Node<E> head;
            if((head=this.head)!=null){
                if(val!=null){
                    return uncheckedRemoveFirstNonNull(head,val);
                }
                return uncheckedRemoveFirstMatch(head,Objects::isNull);
            }
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
        }
        @Override
        public final boolean removeIf(Predicate<? super E> filter){
            final int modCount=this.modCount;
            Node<E> head;
            if((head=this.head)!=null){
                try{
                    if(filter.test(head.val)){
                        findNewHead(modCount,head,filter);
                        return true;
                    }
                    return removeIfHelper(modCount,head,filter);
                }catch(final RuntimeException e){
                    throw CheckedCollection.checkModCount(modCount,root.modCount,e);
                }
            }
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
        }
        @Override
        public final boolean removeVal(boolean val){
            Node<E> head;
            if((head=this.head)!=null){
                return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
            }
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
        }
        @Override
        public final boolean removeVal(Boolean val){
            Node<E> head;
            if((head=this.head)!=null){
                return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
            }
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
        }
        @Override
        public final boolean removeVal(byte val){
            Node<E> head;
            if((head=this.head)!=null){
                return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
            }
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
        }
        @Override
        public final boolean removeVal(Byte val){
            Node<E> head;
            if((head=this.head)!=null){
                return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
            }
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
        }
        @Override
        public final boolean removeVal(char val){
            Node<E> head;
            if((head=this.head)!=null){
                return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
            }
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
        }
        @Override
        public final boolean removeVal(Character val){
            Node<E> head;
            if((head=this.head)!=null){
                return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
            }
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
        }
        @Override
        public final boolean removeVal(double val){
            Node<E> head;
            if((head=this.head)!=null){
                return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
            }
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
        }
        @Override
        public final boolean removeVal(Double val){
            Node<E> head;
            if((head=this.head)!=null){
                return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
            }
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
        }
        @Override
        public final boolean removeVal(float val){
            Node<E> head;
            if((head=this.head)!=null){
                return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
            }
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
        }
        @Override
        public final boolean removeVal(Float val){
            Node<E> head;
            if((head=this.head)!=null){
                return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
            }
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
        }
        @Override
        public final boolean removeVal(int val){
            Node<E> head;
            if((head=this.head)!=null){
                return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
            }
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
        }
        @Override
        public final boolean removeVal(Integer val){
            Node<E> head;
            if((head=this.head)!=null){
                return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
            }
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
        }
        @Override
        public final boolean removeVal(long val){
            Node<E> head;
            if((head=this.head)!=null){
                return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
            }
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
        }
        @Override
        public final boolean removeVal(Long val){
            Node<E> head;
            if((head=this.head)!=null){
                return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
            }
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
        }
        @Override
        public final boolean removeVal(short val){
            Node<E> head;
            if((head=this.head)!=null){
                return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
            }
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
        }
        @Override
        public final boolean removeVal(Short val){
            Node<E> head;
            if((head=this.head)!=null){
                return uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
            }
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
        }
        @Override
        public final void replaceAll(UnaryOperator<E> operator){
            int modCount=this.modCount;
            Checked<E> root;
            try{
                Node<E> head;
                if((head=this.head)==null){
                    return;
                }
                uncheckedReplaceAll(head,tail,operator);
            }finally{
                CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            }
            root.modCount=++modCount;
            bubbleUpSetModCount(modCount);
        }
        @Override
        public final void reverseSort(){
            int modCount=this.modCount;
            Checked<E> root;
            try{
                int size;
                if((size=this.size)<2){
                    return;
                }
                uncheckedSort(head,size,tail,Comparator.reverseOrder());
            }finally{
                CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            }
            root.modCount=++modCount;
            bubbleUpSetModCount(modCount);
        }
        @Override
        public final int size(){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return size;
        }
        @Override
        public final void sort(){
            int modCount=this.modCount;
            Checked<E> root;
            try{
                int size;
                if((size=this.size)<2){
                    return;
                }
                uncheckedSort(head,size,tail,Comparator.naturalOrder());
            }finally{
                CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            }
            root.modCount=++modCount;
            bubbleUpSetModCount(modCount);
        }
        @Override
        public final void sort(Comparator<? super E> sorter){
            int modCount=this.modCount;
            Checked<E> root;
            try{
                int size;
                if((size=this.size)<2){
                    return;
                }
                uncheckedSort(head,size,tail,sorter);
            }finally{
                CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            }
            root.modCount=++modCount;
            bubbleUpSetModCount(modCount);
        }
        @Override
        public OmniList.OfRef<E> subList(int fromIndex,int toIndex){
            Checked<E> root;
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            int size;
            CheckedCollection.CheckSubListRange(fromIndex,toIndex,size=this.size);
            final int tailDist=size-toIndex;
            int subListSize;
            switch(subListSize=toIndex-fromIndex){
            default:
                return getDefaultSubList(root,fromIndex,subListSize,tailDist);
            case 1:
                return getSubList1(root,fromIndex,tailDist);
            case 0:
                return getEmptySubList(root,fromIndex,tailDist);
            }
        }
        @Override
        public final Object[] toArray(){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return super.toArray();
        }
        @Override
        public final <T> T[] toArray(IntFunction<T[]> arrConstructor){
            return super.toArray(size->{
                final int modCount=this.modCount;
                try{
                    return arrConstructor.apply(size);
                }finally{
                    CheckedCollection.checkModCount(modCount,root.modCount);
                }
            });
        }
        @Override
        public final <T> T[] toArray(T[] dst){
            final int modCount=this.modCount;
            try{
                return super.toArray(dst);
            }finally{
                CheckedCollection.checkModCount(modCount,root.modCount);
            }
        }
        @Override
        public final String toString(){
            final int modCount=this.modCount;
            try{
                return super.toString();
            }finally{
                CheckedCollection.checkModCount(modCount,root.modCount);
            }
        }
        void appendHelper(E val,int modCount){
            this.modCount=modCount;
            Node<E> newNode;
            tail=newNode=new Node<>(tail,val);
            RootSubList<E> parent;
            if((parent=this.parent)!=null){
                parent.bubbleUpRootPushTail(modCount,newNode);
            }
        }
        void ascItrRemove(AscendingItr<E> itr){
            Node<E> lastRet;
            if((lastRet=itr.lastRet)!=null){
                int modCount;
                Checked<E> root;
                CheckedCollection.checkModCount(modCount=itr.modCount,(root=this.root).modCount);
                root.modCount=++modCount;
                uncheckedItrRemove(lastRet,modCount,root);
                itr.modCount=modCount;
                --root.size;
                return;
            }
            throw new IllegalStateException();
        }
        void bidirectItrRemove(BidirectionalItr<E> itr){
            Node<E> lastRet;
            if((lastRet=itr.lastRet)!=null){
                int modCount;
                Checked<E> root;
                CheckedCollection.checkModCount(modCount=itr.modCount,(root=this.root).modCount);
                root.modCount=++modCount;
                uncheckedItrRemove(lastRet,modCount,root);
                if(lastRet!=itr.cursor){
                    --itr.nextIndex;
                }
                itr.modCount=modCount;
                --root.size;
                return;
            }
            throw new IllegalStateException();
        }
        void findNewHead(int modCount,Node<E> curr,Predicate<? super E> filter){
            final Node<E> tail;
            if(curr==(tail=this.tail)){
                Checked<E> root;
                CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
                root.modCount=++modCount;
                bubbleUpClear(modCount);
                staticClear(root);
            }else if(filter.test(tail.val)){
                collapseHeadAndTail(modCount,curr,tail,filter);
            }else{
                rootCollapseHead(modCount,curr.next,tail,filter);
            }
        }
        int getParentOffset(){
            return 0;
        }
        void initHelper(Checked<E> root,E val,int modCount){
            final var newNode=new Node<>(val);
            for(var curr=this;;curr.size=1){
                curr.privateInit(newNode,modCount);
                if((curr=curr.parent)==null){
                    staticInit(root,newNode);
                    return;
                }
            }
        }
        void prependHelper(E val,int modCount){
            this.modCount=modCount;
            Node<E> newNode;
            head=newNode=new Node<>(val,head);
            RootSubList<E> parent;
            if((parent=this.parent)!=null){
                parent.bubbleUpRootPushHead(modCount,newNode);
            }
        }
        void removeFirstHelper(int modCount,Node<E> curr){
            Checked<E> root;
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            if(curr==tail){
                bubbleUpClear(modCount);
                staticClear(root);
            }else{
                bubbleUpRootEraseHead(modCount,curr=curr.next);
                staticSetHead(root,curr);
                --root.size;
            }
        }
        boolean removeIfHelper(int modCount,Node<E> curr,Predicate<? super E> filter){
            final Node<E> tail;
            if(curr!=(tail=this.tail)){
                if(filter.test(tail.val)){
                    rootCollapseTail(modCount,curr,tail.prev,filter);
                    return true;
                }
                return collapseBody(modCount,curr,tail,filter);
            }
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
        }
        Node<E> uncheckedExtractHead(int newSize){
            Checked<E> root;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            Node<E> oldHead,newHead;
            bubbleUpRootEraseHead(modCount,newHead=(oldHead=head).next);
            staticSetHead(root,newHead);
            root.size=newSize;
            return oldHead;
        }
        Node<E> uncheckedExtractLastNode(){
            int modCount;
            Checked<E> root;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            final var lastNode=head;
            bubbleUpClear(modCount);
            staticClear(root);
            return lastNode;
        }
        Node<E> uncheckedExtractTail(int newSize){
            Checked<E> root;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            Node<E> oldTail,newTail;
            bubbleUpRootEraseTail(modCount,newTail=(oldTail=tail).prev);
            staticSetTail(root,newTail);
            root.size=newSize;
            return oldTail;
        }
        @Override
        final boolean uncheckedRemoveFirstMatch(Node<E> head,Predicate<Object> pred){
            final int modCount=this.modCount;
            try{
                if(pred.test(head.val)){
                    removeFirstHelper(modCount,head);
                    return true;
                }
                return uncheckedRemoveFirstMatchHelper(modCount,head,pred);
            }catch(final RuntimeException e){
                throw CheckedCollection.checkModCount(modCount,root.modCount,e);
            }
        }
        boolean uncheckedRemoveFirstMatchHelper(int modCount,Node<E> curr,Predicate<Object> pred){
            final var root=this.root;
            final var tail=this.tail;
            Node<E> prev;
            do{
                if(curr==tail){
                    CheckedCollection.checkModCount(modCount,root.modCount);
                    return false;
                }
            }while(!pred.test((curr=(prev=curr).next).val));
            CheckedCollection.checkModCount(modCount,root.modCount);
            root.modCount=++modCount;
            --root.size;
            if(curr==tail){
                bubbleUpRootEraseTail(modCount,prev);
                staticSetTail(root,prev);
            }else{
                bubbleUpDecrementSize(modCount);
                joinNodes(prev,curr.next);
            }
            return true;
        }
        @Override
        final boolean uncheckedRemoveFirstNonNull(Node<E> head,Object nonNull){
            final int modCount=this.modCount;
            try{
                if(nonNull.equals(head.val)){
                    removeFirstHelper(modCount,head);
                    return true;
                }
                return uncheckedRemoveFirstMatchHelper(modCount,head,nonNull::equals);
            }catch(final RuntimeException e){
                throw CheckedCollection.checkModCount(modCount,root.modCount,e);
            }
        }
        private void bubbleUpClear(int modCount){
            var curr=this;
            do{
                curr.modCount=modCount;
                staticClear(curr);
            }while((curr=curr.parent)!=null);
        }
        private void bubbleUpCollapseHeadAndTail(int newModCount,Node<E> newHead,Node<E> newTail,int newSize){
            var curr=this;
            do{
                curr.privateCollapseHeadAndTail(newModCount,newSize,newHead,newTail);
            }while((curr=curr.parent)!=null);
        }
        private void bubbleUpDecrementSize(int newModCount){
            var curr=this;
            do{
                curr.modCount=newModCount;
                --curr.size;
            }while((curr=curr.parent)!=null);
        }
        private void bubbleUpDecrementSize(int newModCount,int numRemoved){
            var curr=this;
            do{
                curr.modCount=newModCount;
                curr.size-=numRemoved;
            }while((curr=curr.parent)!=null);
        }
        private void bubbleUpIncrementSize(int newModCount){
            var curr=this;
            do{
                curr.modCount=newModCount;
                ++curr.size;
            }while((curr=curr.parent)!=null);
        }
        private void bubbleUpPrefixCollapseTail(int newModCount,int numRemoved,Node<E> oldTail,Node<E> newTail){
            var curr=this;
            while(curr.tail==oldTail){
                curr.modCount=newModCount;
                curr.size-=numRemoved;
                curr.tail=newTail;
                if((curr=curr.parent)==null){
                    return;
                }
            }
            curr.bubbleUpDecrementSize(newModCount,numRemoved);
        }
        private void bubbleUpPrefixPushTail(int newModCount,Node<E> newTail,Node<E> oldTail){
            var curr=this;
            while(curr.tail==oldTail){
                curr.modCount=newModCount;
                ++curr.size;
                curr.tail=newTail;
                if((curr=curr.parent)==null){
                    return;
                }
            }
            curr.bubbleUpIncrementSize(newModCount);
        }
        private void bubbleUpRootCollapseHead(int newModCount,int numRemoved,Node<E> newHead){
            var curr=this;
            do{
                curr.modCount=newModCount;
                curr.size-=numRemoved;
                curr.head=newHead;
            }while((curr=curr.parent)!=null);
        }
        private void bubbleUpRootCollapseTail(int newModCount,int numRemoved,Node<E> newTail){
            var curr=this;
            do{
                curr.modCount=newModCount;
                curr.size-=numRemoved;
                curr.tail=newTail;
            }while((curr=curr.parent)!=null);
        }
        private void bubbleUpRootEraseHead(int newModCount,Node<E> newHead){
            var curr=this;
            do{
                curr.modCount=newModCount;
                --curr.size;
                curr.head=newHead;
            }while((curr=curr.parent)!=null);
        }
        private void bubbleUpRootEraseTail(int newModCount,Node<E> newTail){
            var curr=this;
            do{
                curr.modCount=newModCount;
                --curr.size;
                curr.tail=newTail;
            }while((curr=curr.parent)!=null);
        }
        private void bubbleUpRootPushHead(int newModCount,Node<E> newHead){
            var curr=this;
            do{
                curr.modCount=newModCount;
                ++curr.size;
                curr.head=newHead;
            }while((curr=curr.parent)!=null);
        }
        private void bubbleUpRootPushTail(int newModCount,Node<E> newTail){
            var curr=this;
            do{
                curr.modCount=newModCount;
                ++curr.size;
                curr.tail=newTail;
            }while((curr=curr.parent)!=null);
        }
        private void bubbleUpSetModCount(int newModCount){
            var curr=this;
            do{
                curr.modCount=newModCount;
            }while((curr=curr.parent)!=null);
        }
        private void bubbleUpSuffixCollapseHead(int newModCount,int numRemoved,Node<E> oldHead,Node<E> newHead){
            var curr=this;
            while(curr.head==oldHead){
                curr.modCount=newModCount;
                curr.size-=numRemoved;
                curr.head=newHead;
                if((curr=curr.parent)==null){
                    return;
                }
            }
            curr.bubbleUpDecrementSize(newModCount,numRemoved);
        }
        private void bubbleUpSuffixEraseHead(int newModCount,Node<E> oldHead,Node<E> newHead){
            var curr=this;
            do{
                curr.modCount=newModCount;
                --curr.size;
                curr.head=newHead;
                if((curr=curr.parent)==null){
                    return;
                }
            }while(curr.head==oldHead);
            curr.bubbleUpDecrementSize(newModCount);
            joinNodes(oldHead.prev,newHead);
        }
        private void bubbleUpSuffixPushHead(int newModCount,Node<E> newHead,Node<E> oldHead){
            var curr=this;
            while(curr.head==oldHead){
                curr.modCount=newModCount;
                ++curr.size;
                curr.head=newHead;
                if((curr=curr.parent)==null){
                    return;
                }
            }
            curr.bubbleUpIncrementSize(newModCount);
        }
        private boolean collapseBody(int modCount,Node<E> prev,Node<E> next,Predicate<? super E> filter){
            final int oldSize=size;
            int numConsumed=2;
            final var root=this.root;
            for(Node<E> before;numConsumed!=oldSize;prev=before){
                ++numConsumed;
                if(filter.test((before=prev.next).val)){
                    int numRemoved=1;
                    for(Node<E> after;;next=after){
                        if(numConsumed==oldSize){
                            CheckedCollection.checkModCount(modCount,root.modCount);
                            break;
                        }
                        ++numConsumed;
                        if(filter.test((after=prev.next).val)){
                            ++numRemoved;
                            long[] survivorSet;
                            int numLeft,numSurvivors;
                            if((numLeft=oldSize-numConsumed)!=0&&(numSurvivors=markSurvivors(before=before.next,numLeft,
                                    survivorSet=BitSetUtils.getBitSet(numLeft),filter))!=0){
                                CheckedCollection.checkModCount(modCount,root.modCount);
                                numRemoved+=numLeft-numSurvivors;
                                prev=retainSurvivors(prev,before,numSurvivors,survivorSet);
                            }else{
                                CheckedCollection.checkModCount(modCount,root.modCount);
                                numRemoved+=oldSize;
                            }
                            break;
                        }
                    }
                    joinNodes(prev,next);
                    root.modCount=++modCount;
                    bubbleUpDecrementSize(modCount,numRemoved);
                    root.size-=numRemoved;
                    return true;
                }
            }
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
        }
        private void collapseHeadAndTail(int modCount,Node<E> headCandidate,Node<E> tailCandidate,
                final Predicate<? super E> filter){
            int size=this.size;
            int numConsumed=2;
            final var root=this.root;
            for(;;){
                if(numConsumed==size){
                    CheckedCollection.checkModCount(modCount,root.modCount);
                    bubbleUpClear(++modCount);
                    staticClear(root);
                    break;
                }
                ++numConsumed;
                if(!filter.test((headCandidate=headCandidate.next).val)){
                    for(;;){
                        if(numConsumed==size){
                            CheckedCollection.checkModCount(modCount,root.modCount);
                            size=1;
                            break;
                        }
                        ++numConsumed;
                        if(!filter.test((tailCandidate=tailCandidate.prev).val)){
                            size-=numConsumed-2-collapseBodyHelper(root.new ModCountChecker(modCount),headCandidate,
                                    size-numConsumed,tailCandidate,filter);
                            break;
                        }
                    }
                    bubbleUpCollapseHeadAndTail(++modCount,headCandidate,tailCandidate,size);
                    staticSetHead(root,headCandidate);
                    staticSetTail(root,tailCandidate);
                    root.size=size;
                    break;
                }
            }
            root.modCount=modCount;
        }
        private OmniList.OfRef<E> getDefaultSubList(Checked<E> root,int headDist,int subListSize,int tailDist){
            final Node<E> subListHead=head;
            Node<E> subListTail=tail;
            if(tailDist==0){
                if(headDist==0){
                    return new RootSubList<>(root,this,subListHead,subListSize,subListTail);
                }
                return new SuffixSubList<>(root,this,headDist<=subListSize?uncheckedIterateForward(subListHead,headDist)
                        :uncheckedIterateReverse(subListTail,subListSize),subListSize,subListTail);
            }
            subListTail=tailDist<=subListSize?uncheckedIterateReverse(subListTail,tailDist)
                    :uncheckedIterateForward(subListHead,subListSize);
            if(headDist==0){
                return new PrefixSubList<>(root,this,subListHead,subListSize,subListTail);
            }
            return new BodySubList<>(root,this,headDist<=subListSize?uncheckedIterateForward(subListHead,headDist)
                    :uncheckedIterateReverse(subListTail,subListSize),subListSize,subListTail,headDist);
        }
        private OmniList.OfRef<E> getEmptySubList(Checked<E> root,int headDist,int tailDist){
            if(tailDist==0){
                if(headDist==0){
                    return new RootSubList<>(root,this);
                }
                return new SuffixSubList<>(root,this);
            }else if(headDist==0){
                return new PrefixSubList<>(root,this);
            }
            return new BodySubList<>(root,this,headDist);
        }
        private OmniList.OfRef<E> getSubList1(Checked<E> root,int headDist,int tailDist){
            if(tailDist==0){
                if(headDist==0){
                    return new RootSubList<>(root,this,head);
                }
                return new SuffixSubList<>(root,this,tail);
            }else if(headDist==0){
                return new PrefixSubList<>(root,this,head);
            }
            return new BodySubList<>(root,this,staticGetNode(this,headDist,tailDist),headDist);
        }
        private void insertHelper(Node<E> before,E val,Node<E> after,int modCount){
            new Node<>(before,val,after);
            this.modCount=modCount;
            RootSubList<E> parent;
            if((parent=this.parent)!=null){
                parent.bubbleUpIncrementSize(modCount);
            }
        }
        private void privateCollapseHeadAndTail(int modCount,int size,Node<E> head,Node<E> tail){
            this.modCount=modCount;
            this.size=size;
            this.head=head;
            this.tail=tail;
        }
        private void privateInit(Node<E> node,int modCount){
            this.modCount=modCount;
            staticInit(this,node);
        }
        private void rootCollapseHead(int modCount,Node<E> headCandidate,final Node<E> oldTail,
                Predicate<? super E> filter){
            final int oldSize=size;
            int numConsumed=2;
            final var root=this.root;
            for(;;headCandidate=headCandidate.next){
                if(numConsumed==oldSize){
                    CheckedCollection.checkModCount(modCount,root.modCount);
                    --numConsumed;
                    break;
                }
                ++numConsumed;
                if(!filter.test(headCandidate.val)){
                    numConsumed-=2-collapseBodyHelper(root.new ModCountChecker(modCount),headCandidate,
                            oldSize-numConsumed,oldTail,filter);
                    break;
                }
            }
            root.modCount=++modCount;
            bubbleUpRootCollapseHead(modCount,numConsumed,headCandidate);
            staticSetHead(root,headCandidate);
            root.size-=numConsumed;
        }
        private void rootCollapseTail(int modCount,final Node<E> oldHead,Node<E> tailCandidate,
                Predicate<? super E> filter){
            final int oldSize=size;
            int numConsumed=2;
            final var root=this.root;
            for(;;tailCandidate=tailCandidate.prev){
                if(numConsumed==oldSize){
                    CheckedCollection.checkModCount(modCount,root.modCount);
                    --numConsumed;
                    break;
                }
                ++numConsumed;
                if(!filter.test(tailCandidate.val)){
                    numConsumed-=2-collapseBodyHelper(root.new ModCountChecker(modCount),oldHead,oldSize-numConsumed,
                            tailCandidate,filter);
                    break;
                }
            }
            root.modCount=++modCount;
            bubbleUpRootCollapseTail(modCount,numConsumed,tailCandidate);
            staticSetTail(root,tailCandidate);
            root.size-=numConsumed;
        }
        private void suffixCollapseHead(int modCount,Node<E> oldHead,Node<E> oldTail,Predicate<? super E> filter){
            final int oldSize=size;
            int numConsumed=2;
            final var root=this.root;
            var headCandidate=oldHead.next;
            for(;;headCandidate=headCandidate.next){
                if(numConsumed==oldSize){
                    CheckedCollection.checkModCount(modCount,root.modCount);
                    --numConsumed;
                    break;
                }
                ++numConsumed;
                if(!filter.test(headCandidate.val)){
                    numConsumed-=2-collapseBodyHelper(root.new ModCountChecker(modCount),headCandidate,
                            oldSize-numConsumed,oldTail,filter);
                    break;
                }
            }
            root.modCount=++modCount;
            bubbleUpSuffixCollapseHead(modCount,numConsumed,oldHead,headCandidate);
            joinNodes(oldHead.prev,headCandidate);
            root.size-=numConsumed;
        }
        private void uncheckedItrRemove(Node<E> lastRet,int modCount,Checked<E> root){
            if(lastRet==tail){
                if(lastRet==head){
                    bubbleUpClear(modCount);
                    staticInit(root,null);
                }else{
                    bubbleUpRootEraseTail(modCount,lastRet=lastRet.prev);
                    staticSetTail(root,lastRet);
                }
            }else{
                if(lastRet==head){
                    bubbleUpRootEraseHead(modCount,lastRet=lastRet.next);
                    staticSetHead(root,lastRet);
                }else{
                    bubbleUpDecrementSize(modCount);
                    joinNodes(lastRet.prev,lastRet.next);
                }
            }
        }
        private static class AscendingItr<E> implements OmniIterator.OfRef<E>{
            transient final RootSubList<E> parent;
            transient Node<E> cursor;
            transient Node<E> lastRet;
            transient int modCount;
            private AscendingItr(RootSubList<E> parent){
                this.parent=parent;
                this.cursor=parent.head;
                this.modCount=parent.modCount;
            }
            private AscendingItr(RootSubList<E> parent,Node<E> cursor){
                this.parent=parent;
                this.cursor=cursor;
                this.modCount=parent.modCount;
            }
            @Override
            public void forEachRemaining(Consumer<? super E> action){
                Node<E> cursor;
                if((cursor=this.cursor)!=null){
                    final int modCount=this.modCount;
                    final var parent=this.parent;
                    try{
                        uncheckedForEachForward(cursor,cursor=parent.tail,action);
                    }finally{
                        CheckedCollection.checkModCount(modCount,parent.root.modCount);
                    }
                    this.cursor=null;
                    this.lastRet=cursor;
                }
            }
            @Override
            public boolean hasNext(){
                return this.cursor!=null;
            }
            @Override
            public E next(){
                RootSubList<E> parent;
                CheckedCollection.checkModCount(modCount,(parent=this.parent).root.modCount);
                Node<E> cursor;
                if((cursor=this.cursor)!=null){
                    if(cursor==parent.tail){
                        this.cursor=null;
                    }else{
                        this.cursor=cursor.next;
                    }
                    this.lastRet=cursor;
                    return cursor.val;
                }
                throw new NoSuchElementException();
            }
            @Override
            public void remove(){
                parent.ascItrRemove(this);
                this.lastRet=null;
            }
        }
        private static class BidirectionalItr<E>extends AscendingItr<E> implements OmniListIterator.OfRef<E>{
            transient int nextIndex;
            private BidirectionalItr(RootSubList<E> parent){
                super(parent);
            }
            private BidirectionalItr(RootSubList<E> parent,Node<E> cursor,int nextIndex){
                super(parent,cursor);
                this.nextIndex=nextIndex;
            }
            @Override
            public void add(E val){
                int modCount;
                Checked<E> root;
                RootSubList<E> parent;
                CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
                root.modCount=++modCount;
                final int nextIndex=this.nextIndex++;
                int size;
                if((size=parent.size++)!=0){
                    if(nextIndex==0){
                        parent.prependHelper(val,modCount);
                    }else if(nextIndex==size){
                        parent.appendHelper(val,modCount);
                    }else{
                        Node<E> cursor;
                        parent.insertHelper((cursor=this.cursor).prev,val,cursor,modCount);
                    }
                }else{
                    parent.initHelper(root,val,modCount);
                }
                ++root.size;
                this.modCount=modCount;
                lastRet=null;
            }
            @Override
            public void forEachRemaining(Consumer<? super E> action){
                Node<E> cursor;
                if((cursor=this.cursor)!=null){
                    final int modCount=this.modCount;
                    final var parent=this.parent;
                    try{
                        uncheckedForEachForward(cursor,cursor=parent.tail,action);
                    }finally{
                        CheckedCollection.checkModCount(modCount,parent.root.modCount);
                    }
                    this.nextIndex=parent.size;
                    this.cursor=null;
                    lastRet=cursor;
                }
            }
            @Override
            public boolean hasPrevious(){
                return this.nextIndex!=0;
            }
            @Override
            public E next(){
                RootSubList<E> parent;
                CheckedCollection.checkModCount(modCount,(parent=this.parent).root.modCount);
                Node<E> cursor;
                if((cursor=this.cursor)!=null){
                    if(cursor==parent.tail){
                        this.cursor=null;
                    }else{
                        this.cursor=cursor.next;
                    }
                    ++nextIndex;
                    lastRet=cursor;
                    return cursor.val;
                }
                throw new NoSuchElementException();
            }
            @Override
            public int nextIndex(){
                return this.nextIndex;
            }
            @Override
            public E previous(){
                CheckedCollection.checkModCount(modCount,parent.root.modCount);
                int nextIndex;
                if((nextIndex=this.nextIndex)!=0){
                    this.nextIndex=nextIndex-1;
                    Node<E> cursor;
                    this.cursor=cursor=this.cursor.prev;
                    lastRet=cursor;
                    return cursor.val;
                }
                throw new NoSuchElementException();
            }
            @Override
            public int previousIndex(){
                return this.nextIndex-1;
            }
            @Override
            public void remove(){
                parent.bidirectItrRemove(this);
                lastRet=null;
            }
            @Override
            public void set(E val){
                lastRet.val=val;
            }
        }
    }
    private static class SuffixSubList<E>extends RootSubList<E>{
        private SuffixSubList(Checked<E> root,RootSubList<E> parent){
            super(root,parent);
        }
        private SuffixSubList(Checked<E> root,RootSubList<E> parent,Node<E> onlyNode){
            super(root,parent,onlyNode);
        }
        private SuffixSubList(Checked<E> root,RootSubList<E> parent,Node<E> head,int size,Node<E> tail){
            super(root,parent,head,size,tail);
        }
        @Override
        public void clear(){
            int modCount;
            Checked<E> root;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            int size;
            if((size=this.size)!=0){
                root.modCount=++modCount;
                Node<E> tmp;
                bubbleUpClear(modCount,size,tmp=head,tmp=tmp.prev);
                staticSetTail(root,tmp);
                root.size-=size;
            }
        }
        @Override
        public OmniList.OfRef<E> subList(int fromIndex,int toIndex){
            Checked<E> root;
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            int size;
            CheckedCollection.CheckSubListRange(fromIndex,toIndex,size=this.size);
            final int tailDist=size-toIndex;
            int subListSize;
            switch(subListSize=toIndex-fromIndex){
            default:
                return getDefaultSubList(root,fromIndex,subListSize,tailDist);
            case 1:
                return getSubList1(root,fromIndex,tailDist);
            case 0:
                return getEmptySubList(root,fromIndex,tailDist);
            }
        }
        @Override
        void ascItrRemove(RootSubList.AscendingItr<E> itr){
            Node<E> lastRet;
            if((lastRet=itr.lastRet)!=null){
                int modCount;
                Checked<E> root;
                CheckedCollection.checkModCount(modCount=itr.modCount,(root=this.root).modCount);
                root.modCount=++modCount;
                uncheckedItrRemove(lastRet,modCount,root);
                itr.modCount=modCount;
                --root.size;
                return;
            }
            throw new IllegalStateException();
        }
        @Override
        void bidirectItrRemove(RootSubList.BidirectionalItr<E> itr){
            Node<E> lastRet;
            if((lastRet=itr.lastRet)!=null){
                int modCount;
                Checked<E> root;
                CheckedCollection.checkModCount(modCount=itr.modCount,(root=this.root).modCount);
                root.modCount=++modCount;
                uncheckedItrRemove(lastRet,modCount,root);
                if(lastRet!=itr.cursor){
                    --itr.nextIndex;
                }
                itr.modCount=modCount;
                --root.size;
                return;
            }
            throw new IllegalStateException();
        }
        @Override
        void findNewHead(int modCount,Node<E> curr,Predicate<? super E> filter){
            final Node<E> tail;
            if(curr==(tail=this.tail)){
                Checked<E> root;
                CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
                root.modCount=++modCount;
                bubbleUpClear(modCount,1,curr,curr=curr.prev);
                staticSetTail(root,curr);
                --root.size;
            }else if(filter.test(tail.val)){
                collapseHeadAndTail(modCount,curr,tail,filter);
            }else{
                super.suffixCollapseHead(modCount,curr,tail,filter);
            }
        }
        @Override
        void initHelper(Checked<E> root,E val,int modCount){
            final Node<E> before,newNode=new Node<>(val);
            for(RootSubList<E> curr=this;;curr.size=1){
                curr.privateInit(newNode,modCount);
                if((curr=curr.parent)==null){
                    before=root.tail;
                    break;
                }else if(curr.size!=0){
                    before=curr.tail;
                    curr.bubbleUpRootPushTail(modCount,newNode);
                    break;
                }
            }
            joinNodes(before,newNode);
        }
        @Override
        void prependHelper(E val,int modCount){
            this.modCount=modCount;
            Node<E> newNode,oldHead;
            head=newNode=new Node<>((oldHead=head).prev,val,oldHead);
            RootSubList<E> parent;
            if((parent=this.parent)!=null){
                parent.bubbleUpSuffixPushHead(modCount,newNode,oldHead);
            }
        }
        @Override
        void removeFirstHelper(int modCount,Node<E> curr){
            Checked<E> root;
            CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            if(curr==tail){
                bubbleUpClear(modCount,1,curr,curr=curr.prev);
                staticSetTail(root,curr);
            }else{
                super.bubbleUpSuffixEraseHead(modCount,curr,curr.next);
            }
            --root.size;
        }
        @Override
        Node<E> uncheckedExtractHead(int newSize){
            Checked<E> root;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            Node<E> oldHead;
            super.bubbleUpSuffixEraseHead(modCount,oldHead=head,oldHead.next);
            --root.size;
            return oldHead;
        }
        @Override
        Node<E> uncheckedExtractLastNode(){
            int modCount;
            Checked<E> root;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
            root.modCount=++modCount;
            final Node<E> prev,lastNode;
            staticSetTail(root,prev=(lastNode=head).prev);
            bubbleUpClear(modCount,1,lastNode,prev);
            --root.size;
            return lastNode;
        }
        private void bubbleUpClear(int modCount,int size,Node<E> head,final Node<E> prev){
            for(RootSubList<E> curr=this;;){
                curr.modCount=modCount;
                staticClear(curr);
                if((curr=curr.parent)==null){
                    break;
                }else if(curr.head!=head){
                    curr.bubbleUpRootCollapseTail(modCount,size,prev);
                    break;
                }
            }
        }
        private void bubbleUpCollapseHeadAndTail(int newModCount,Node<E> oldHead,Node<E> newHead,Node<E> newTail,
                int numRemoved){
            final int newSize=size-numRemoved;
            for(RootSubList<E> curr=this;;){
                curr.privateCollapseHeadAndTail(newModCount,newSize,newHead,newTail);
                if((curr=curr.parent)==null){
                    break;
                }
                if(curr.head!=oldHead){
                    curr.bubbleUpRootCollapseTail(newModCount,numRemoved,newTail);
                    break;
                }
            }
            joinNodes(oldHead.prev,newHead);
        }
        private void collapseHeadAndTail(int modCount,Node<E> head,Node<E> tailCandidate,Predicate<? super E> filter){
            final int oldSize=size;
            int numConsumed=2;
            final var root=this.root;
            for(;;){
                if(numConsumed==oldSize){
                    CheckedCollection.checkModCount(modCount,root.modCount);
                    bubbleUpClear(++modCount,oldSize,head,tailCandidate=head.prev);
                    break;
                }
                ++numConsumed;
                if(!filter.test((tailCandidate=tailCandidate.prev).val)){
                    var headCandidate=head;
                    for(;;){
                        if(numConsumed==oldSize){
                            CheckedCollection.checkModCount(modCount,root.modCount);
                            --numConsumed;
                            break;
                        }
                        ++numConsumed;
                        if(!filter.test((headCandidate=headCandidate.next).val)){
                            numConsumed-=2-+collapseBodyHelper(root.new ModCountChecker(modCount),headCandidate,
                                    oldSize-numConsumed,tailCandidate,filter);
                            break;
                        }
                    }
                    bubbleUpCollapseHeadAndTail(++modCount,head,headCandidate,tailCandidate,numConsumed);
                    break;
                }
            }
            root.modCount=modCount;
            staticSetTail(root,tailCandidate);
            root.size-=numConsumed;
        }
        private OmniList.OfRef<E> getDefaultSubList(Checked<E> root,int headDist,int subListSize,int tailDist){
            Node<E> subListHead=head,subListTail=tail;
            if(tailDist==0){
                return new SuffixSubList<>(root,this,headDist<=subListSize?iterateForward(subListHead,headDist)
                        :uncheckedIterateReverse(subListTail,subListSize));
            }
            if(tailDist<=headDist){
                subListTail=uncheckedIterateReverse(subListTail,tailDist);
                subListHead=headDist<=subListSize?iterateForward(subListHead,headDist)
                        :uncheckedIterateReverse(subListTail,subListSize);
            }else{
                subListHead=iterateForward(subListHead,headDist);
                subListTail=tailDist<=subListSize?uncheckedIterateReverse(subListTail,tailDist)
                        :uncheckedIterateForward(subListHead,subListSize);
            }
            return new BodySubList<>(root,this,subListHead,subListSize,subListTail,headDist);
        }
        private OmniList.OfRef<E> getEmptySubList(Checked<E> root,int headDist,int tailDist){
            if(tailDist==0){
                return new PrefixSubList<>(root,this);
            }
            return new BodySubList<>(root,this,headDist);
        }
        private OmniList.OfRef<E> getSubList1(Checked<E> root,int headDist,int tailDist){
            if(tailDist==0){
                return new SuffixSubList<>(root,this,tail);
            }
            return new BodySubList<>(root,this,staticGetNode(this,headDist,tailDist),headDist);
        }
        private void uncheckedItrRemove(Node<E> lastRet,int modCount,Checked<E> root){
            if(lastRet==tail){
                if(lastRet==head){
                    bubbleUpClear(modCount,1,lastRet,lastRet=lastRet.prev);
                }else{
                    super.bubbleUpRootEraseTail(modCount,lastRet=lastRet.prev);
                }
                staticSetTail(root,lastRet);
            }else{
                if(lastRet==head){
                    super.bubbleUpSuffixEraseHead(modCount,lastRet,lastRet.next);
                }else{
                    super.bubbleUpDecrementSize(modCount);
                    joinNodes(lastRet.prev,lastRet.next);
                }
            }
        }
    }
}
