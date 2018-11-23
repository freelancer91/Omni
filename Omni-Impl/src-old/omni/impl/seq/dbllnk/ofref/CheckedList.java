package omni.impl.seq.dbllnk.ofref;
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
public class CheckedList<E>extends AbstractSeq.Checked<E>{
  static <E> int collapseBodyHelper(CheckedCollection.AbstractModCountChecker modCountChecker,Node<E> prev,int numLeft,
      Node<E> next,Predicate<? super E> filter){
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
            if(numLeft!=0&&(numSurvivors
                =markSurvivors(before=before.next,numLeft,survivorSet=BitSetUtils.getBitSet(numLeft),filter))!=0){
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
  static <E> int markSurvivors(Node<E> begin,int numLeft,long[] survivorSet,Predicate<? super E> filter){
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
  static <E> Node<E> retainSurvivors(Node<E> lastKnownSurvivor,Node<E> curr,int numSurvivors,long[] survivorSet){
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
  CheckedList(Node<E> onlyNode){
    super(onlyNode);
  }
  CheckedList(Node<E> head,int size,Node<E> tail){
    super(head,size,tail);
  }
  @Override public boolean add(E val){
    super.addLast(val);
    return true;
  }
  @Override public void add(int index,E val){
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
  @Override public void clear(){
    if(size!=0){
      ++modCount;
      super.clear();
    }
  }
  @Override public Object clone(){
    Node<E> newHead,newTail;
    int size;
    if((size=this.size)!=0){
      Node<E> oldHead,oldTail;
      for(newHead=new Node<>((oldHead=head).val),newTail=newHead,oldTail=tail;oldHead!=oldTail;
          newTail=new Node<>(newTail,(oldHead=oldHead.next).val)){}
    }else{
      newHead=null;
      newTail=null;
    }
    return new CheckedList<>(newHead,size,newTail);
  }
  @Override public OmniIterator.OfRef<E> descendingIterator(){
    return new CheckedDescendingItr<>(this);
  }
  @Override public E element(){
    return super.getFirst();
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public void forEach(Consumer<? super E> action){
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
  @Override public E get(int index){
    CheckedCollection.checkLo(index);
    int size;
    CheckedCollection.checkReadHi(index,size=this.size);
    return staticGetNode(this,index,size-index).val;
  }
  @Override public E getLast(){
    Node<E> tail;
    if((tail=this.tail)!=null){ return tail.val; }
    throw new NoSuchElementException();
  }
  @Override public int hashCode(){
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
  @Override public OmniIterator.OfRef<E> iterator(){
    return new CheckedAscendingItr<>(this);
  }
  @Override public OmniListIterator.OfRef<E> listIterator(){
    return new CheckedBidirectionalItr<>(this);
  }
  @Override public OmniListIterator.OfRef<E> listIterator(int index){
    CheckedCollection.checkLo(index);
    int size;
    CheckedCollection.checkWriteHi(index,size=this.size);
    return new CheckedBidirectionalItr<>(this,super.getItrNode(index,size-index),index);
  }
  @Override public boolean offer(E val){
    super.addLast(val);
    return true;
  }
  @Override public boolean offerFirst(E val){
    super.addFirst(val);
    return true;
  }
  @Override public boolean offerLast(E val){
    super.addLast(val);
    return true;
  }
  @Override public E poll(){
    return super.pollFirst();
  }
  @Override public E pollLast(){
    Node<E> tail;
    if((tail=this.tail)!=null){
      ++modCount;
      --size;
      staticEraseTail(this,tail);
      return tail.val;
    }
    return null;
  }
  @Override public E pop(){
    return super.removeFirst();
  }
  @Override public void push(E val){
    super.addFirst(val);
  }
  @Override public void put(int index,E val){
    CheckedCollection.checkLo(index);
    int size;
    CheckedCollection.checkReadHi(index,size=this.size);
    staticGetNode(this,index,size-index).val=val;
  }
  @Override public E remove(){
    return super.removeFirst();
  }
  @Override public E remove(int index){
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
  @Override public boolean removeIf(Predicate<? super E> filter){
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
  @Override public E removeLast(){
    Node<E> tail;
    if((tail=this.tail)!=null){
      ++modCount;
      --size;
      staticEraseTail(this,tail);
      return tail.val;
    }
    throw new NoSuchElementException();
  }
  @Override public boolean removeLastOccurrence(boolean val){
    final Node<E> tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeLastOccurrence(Boolean val){
    final Node<E> tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeLastOccurrence(byte val){
    final Node<E> tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeLastOccurrence(Byte val){
    final Node<E> tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeLastOccurrence(char val){
    final Node<E> tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeLastOccurrence(Character val){
    final Node<E> tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeLastOccurrence(double val){
    final Node<E> tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeLastOccurrence(Double val){
    final Node<E> tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeLastOccurrence(float val){
    final Node<E> tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeLastOccurrence(Float val){
    final Node<E> tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeLastOccurrence(int val){
    final Node<E> tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeLastOccurrence(Integer val){
    final Node<E> tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeLastOccurrence(long val){
    final Node<E> tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeLastOccurrence(Long val){
    final Node<E> tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeLastOccurrence(Object val){
    Node<E> tail;
    if((tail=this.tail)!=null){
      if(val!=null){ return uncheckedRemoveLastNonNull(tail,val); }
      return uncheckedRemoveLastMatch(tail,Objects::isNull);
    }
    return false;
  }
  @Override public boolean removeLastOccurrence(short val){
    final Node<E> tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeLastOccurrence(Short val){
    final Node<E> tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public void replaceAll(UnaryOperator<E> operator){
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
  @Override public void reverseSort(){
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
  @Override public E set(int index,E val){
    CheckedCollection.checkLo(index);
    int size;
    CheckedCollection.checkReadHi(index,size=this.size);
    Node<E> node;
    final var oldVal=(node=staticGetNode(this,index,size-index)).val;
    node.val=val;
    return oldVal;
  }
  @Override public void sort(){
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
  @Override public void sort(Comparator<? super E> sorter){
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
  @Override public OmniList.OfRef<E> subList(int fromIndex,int toIndex){
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
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    return super.toArray(size->{
      final int modCount=this.modCount;
      try{
        return arrConstructor.apply(size);
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    });
  }
  @Override public String toString(){
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
  @Override protected int uncheckedLastIndexOfNonNull(int size,Object nonNull){
    final int modCount=this.modCount;
    try{
      return super.uncheckedLastIndexOfNonNull(size,nonNull);
    }finally{
      CheckedCollection.checkModCount(modCount,this.modCount);
    }
  }
  @Override boolean uncheckedRemoveFirstMatch(Node<E> curr,Predicate<Object> pred){
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
        if(curr==tail){ return false; }
      }while(!pred.test((curr=(prev=curr).next).val));
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
  @Override boolean uncheckedRemoveFirstNonNull(Node<E> curr,Object nonNull){
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
            if((numLeft-=numConsumed)!=0&&(numLeft
                =markSurvivors(before=before.next,numLeft,survivorSet=BitSetUtils.getBitSet(numLeft),filter))!=0){
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
            size=(oldSize-=numConsumed)+2
                -collapseBodyHelper(new ModCountChecker(modCount),headCandidate,oldSize,tailCandidate,filter);
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
      if(headDist==0){ return new CheckedSubList<>(this,null,subListHead,subListSize,subListTail); }
      return new CheckedSubList.Suffix<>(this,null,headDist<=subListSize?uncheckedIterateForward(subListHead,headDist)
          :uncheckedIterateReverse(subListTail,subListSize),subListSize,subListTail);
    }
    subListTail=tailDist<=subListSize?uncheckedIterateReverse(subListTail,tailDist)
        :uncheckedIterateForward(subListHead,subListSize);
    if(headDist==0){ return new CheckedSubList.Prefix<>(this,null,subListHead,subListSize,subListTail); }
    return new CheckedSubList.Body<>(this,null,headDist<=subListSize?uncheckedIterateForward(subListHead,headDist)
        :uncheckedIterateReverse(subListTail,subListSize),subListSize,subListTail,headDist);
  }
  private OmniList.OfRef<E> getEmptySubList(int headDist,int tailDist){
    if(tailDist==0){
      if(headDist==0){ return new CheckedSubList<>(this,null); }
      return new CheckedSubList.Suffix<>(this,null);
    }else if(headDist==0){ return new CheckedSubList.Prefix<>(this,null); }
    return new CheckedSubList.Body<>(this,null,headDist);
  }
  private OmniList.OfRef<E> getSubList1(int headDist,int tailDist){
    if(tailDist==0){
      if(headDist==0){ return new CheckedSubList<>(this,null,head); }
      return new CheckedSubList.Suffix<>(this,null,tail);
    }else if(headDist==0){ return new CheckedSubList.Prefix<>(this,null,head); }
    return new CheckedSubList.Body<>(this,null,staticGetNode(this,headDist,tailDist),headDist);
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
        if(curr==head){ return false; }
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
}
