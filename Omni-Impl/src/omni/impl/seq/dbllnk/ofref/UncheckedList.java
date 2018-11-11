package omni.impl.seq.dbllnk.ofref;
import java.util.function.Predicate;
import omni.api.OmniDeque;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.util.OmniPred;
public class UncheckedList<E>extends AbstractSeq.Unchecked<E> implements OmniDeque.OfRef<E>{
  static <E> int collapseBodyHelper(Node<E> prev,Node<E> next,Predicate<? super E> filter){
    int numRemoved=0;
    for(Node<E> curr;(curr=prev.next)!=next;prev=curr){
      if(filter.test(curr.val)){
        do{
          ++numRemoved;
          if((curr=curr.next)==next){
            joinNodes(prev,next);
            return numRemoved;
          }
        }while(filter.test(curr.val));
        joinNodes(prev,curr);
      }
    }
    return numRemoved;
  }
  UncheckedList(){
    super();
  }
  UncheckedList(Node<E> onlyNode){
    super(onlyNode);
  }
  UncheckedList(Node<E> head,int size,Node<E> tail){
    super(head,size,tail);
  }
  @Override public boolean add(E val){
    super.addLast(val);
    return true;
  }
  @Override public void add(int index,E val){
    int size;
    if((size=this.size)!=0){
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
  @Override public OmniIterator.OfRef<E> descendingIterator(){
    return new UncheckedDescendingItr<>(this);
  }
  @Override public E element(){
    return head.val;
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public E getFirst(){
    return head.val;
  }
  @Override public E getLast(){
    return tail.val;
  }
  @Override public OmniIterator.OfRef<E> iterator(){
    return new UncheckedAscendingItr<>(this);
  }
  @Override public OmniListIterator.OfRef<E> listIterator(){
    return new UncheckedBidirectionalItr<>(this);
  }
  @Override public OmniListIterator.OfRef<E> listIterator(int index){
    return new UncheckedBidirectionalItr<>(this,super.getItrNode(index,size-index),index);
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
    Node<E> head;
    if((head=this.head)!=null){
      staticEraseHead(this,head);
      --size;
      return head.val;
    }
    return null;
  }
  @Override public E pollFirst(){
    Node<E> head;
    if((head=this.head)!=null){
      staticEraseHead(this,head);
      --size;
      return head.val;
    }
    return null;
  }
  @Override public E pollLast(){
    Node<E> tail;
    if((tail=this.tail)!=null){
      staticEraseTail(this,tail);
      --size;
      return tail.val;
    }
    return null;
  }
  @Override public E pop(){
    Node<E> head;
    staticEraseHead(this,head=this.head);
    --size;
    return head.val;
  }
  @Override public void push(E val){
    super.addFirst(val);
  }
  @Override public E remove(){
    Node<E> head;
    staticEraseHead(this,head=this.head);
    --size;
    return head.val;
  }
  @Override public E remove(int index){
    Node<E> node;
    int size;
    if((size=--this.size)!=0){
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
    return node.val;
  }
  @Override public E removeFirst(){
    Node<E> head;
    staticEraseHead(this,head=this.head);
    --size;
    return head.val;
  }
  @Override public E removeLast(){
    Node<E> tail;
    staticEraseTail(this,tail=this.tail);
    --size;
    return tail.val;
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
    final Node<E> tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeLastOccurrence(short val){
    final Node<E> tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeLastOccurrence(Short val){
    final Node<E> tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public OmniList.OfRef<E> subList(int fromIndex,int toIndex){
    int subListSize;
    final int tailDist=size-toIndex;
    switch(subListSize=toIndex-fromIndex){
    default:
      return getDefaultSubList(fromIndex,subListSize,tailDist);
    case 1:
      return getSubList1(fromIndex,tailDist);
    case 0:
      return getEmptySubList(fromIndex,tailDist);
    }
  }
  @Override boolean collapseBody(Node<E> head,Node<E> tail,Predicate<? super E> filter){
    Node<E> prev;
    while((head=(prev=head).next)!=tail){
      if(filter.test(head.val)){
        int numRemoved;
        for(numRemoved=1;(head=head.next)!=tail;++numRemoved){
          if(!filter.test(head.val)){
            numRemoved+=collapseBodyHelper(head,tail,filter);
            break;
          }
        }
        joinNodes(prev,head);
        size-=numRemoved;
        return true;
      }
    }
    return false;
  }
  @Override void collapseTail(final Node<E> head,Node<E> tail,Predicate<? super E> filter){
    int numRemoved;
    for(numRemoved=1;(tail=tail.prev)!=head;++numRemoved){
      if(!filter.test(tail.val)){
        numRemoved+=collapseBodyHelper(head,tail,filter);
        break;
      }
    }
    staticSetTail(this,tail);
    size-=numRemoved;
  }
  @Override void findNewHead(Node<E> head,Predicate<? super E> filter){
    final Node<E> tail;
    if((tail=this.tail)!=head){
      if(filter.test(tail.val)){
        collapseHeadAndTail(head,tail,filter);
      }else{
        collapseHead(head.next,tail,filter);
      }
    }else{
      super.clear();
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
    --size;
    return true;
  }
  @Override boolean uncheckedRemoveFirstNonNull(Node<E> curr,Object nonNull){
    final var tail=this.tail;
    if(nonNull.equals(curr.val)){
      if(curr==tail){
        staticInit(this,null);
      }else{
        staticEraseHead(this,curr);
      }
    }else{
      Node<E> prev;
      do{
        if(curr==tail){ return false; }
      }while(!nonNull.equals((curr=(prev=curr).next).val));
      if(curr==tail){
        staticSetTail(this,prev);
      }else{
        joinNodes(prev,curr.next);
      }
    }
    --size;
    return true;
  }
  private void collapseHead(Node<E> headCandidate,Node<E> tail,Predicate<? super E> filter){
    int numRemoved;
    for(numRemoved=1;headCandidate!=tail;++numRemoved,headCandidate=headCandidate.next){
      if(!filter.test(headCandidate.val)){
        numRemoved+=collapseBodyHelper(headCandidate,tail,filter);
        break;
      }
    }
    staticSetHead(this,headCandidate);
    size-=numRemoved;
  }
  private void collapseHeadAndTail(Node<E> head,Node<E> tail,Predicate<? super E> filter){
    for(int numRemoved=2;(head=head.next)!=tail;++numRemoved){
      if(!filter.test(head.val)){
        while((tail=tail.prev)!=head){
          if(!filter.test(tail.val)){
            numRemoved+=collapseBodyHelper(head,tail,filter);
            break;
          }
          ++numRemoved;
        }
        staticSetHead(this,head);
        staticSetTail(this,tail);
        size-=numRemoved;
        return;
      }
    }
    super.clear();
  }
  private OmniList.OfRef<E> getDefaultSubList(int headDist,int subListSize,int tailDist){
    final Node<E> subListHead=head;
    Node<E> subListTail=tail;
    if(tailDist==0){
      if(headDist==0){ return new UncheckedSubList<>(this,null,subListHead,subListSize,subListTail); }
      return new UncheckedSubList.Suffix<>(this,null,headDist<=subListSize?uncheckedIterateForward(subListHead,headDist)
          :uncheckedIterateReverse(subListTail,subListSize),subListSize,subListTail);
    }
    subListTail=tailDist<=subListSize?uncheckedIterateReverse(subListTail,tailDist)
        :uncheckedIterateForward(subListHead,subListSize);
    if(headDist==0){ return new UncheckedSubList.Prefix<>(this,null,subListHead,subListSize,subListTail); }
    return new UncheckedSubList.Body<>(this,null,headDist<=subListSize?uncheckedIterateForward(subListHead,headDist)
        :uncheckedIterateReverse(subListTail,subListSize),subListSize,subListTail,headDist);
  }
  private OmniList.OfRef<E> getEmptySubList(int headDist,int tailDist){
    if(tailDist==0){
      if(headDist==0){ return new UncheckedSubList<>(this,null); }
      return new UncheckedSubList.Suffix<>(this,null);
    }else if(headDist==0){ return new UncheckedSubList.Prefix<>(this,null); }
    return new UncheckedSubList.Body<>(this,null,headDist);
  }
  private OmniList.OfRef<E> getSubList1(int headDist,int tailDist){
    if(tailDist==0){
      if(headDist==0){ return new UncheckedSubList<>(this,null,head); }
      return new UncheckedSubList.Suffix<>(this,null,tail);
    }else if(headDist==0){ return new UncheckedSubList.Prefix<>(this,null,head); }
    return new UncheckedSubList.Body<>(this,null,staticGetNode(this,headDist,tailDist),headDist);
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
    --size;
    return true;
  }
}
