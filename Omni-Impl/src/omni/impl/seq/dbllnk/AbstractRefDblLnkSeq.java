package omni.impl.seq.dbllnk;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import omni.api.OmniList;
import omni.impl.seq.AbstractRefList;
import omni.util.OmniPred;
abstract class AbstractRefDblLnkSeq<E>extends AbstractRefList<E> implements OmniList.OfRef<E>{
  static <E> void joinNodes(Node<E> before,Node<E> after){
    after.prev=before;
    before.next=after;
  }
  @Override public boolean contains(boolean val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedAnyMatches(head,tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean contains(Boolean val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedAnyMatches(head,tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean contains(byte val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedAnyMatches(head,tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean contains(Byte val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedAnyMatches(head,tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean contains(char val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedAnyMatches(head,tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean contains(Character val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedAnyMatches(head,tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean contains(double val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedAnyMatches(head,tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean contains(Double val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedAnyMatches(head,tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean contains(float val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedAnyMatches(head,tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean contains(Float val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedAnyMatches(head,tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean contains(int val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedAnyMatches(head,tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean contains(Integer val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedAnyMatches(head,tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean contains(long val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedAnyMatches(head,tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean contains(Long val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedAnyMatches(head,tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean contains(Object val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedAnyMatches(head,tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean contains(short val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedAnyMatches(head,tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean contains(Short val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedAnyMatches(head,tail,OmniPred.OfRef.getEqualsPred(val));
  }
  final void finalRemoveLast(Node<E> oldTail){
    if(oldTail==head){
      this.head=null;
      this.tail=null;
    }else{
      this.tail=oldTail=oldTail.prev;
      oldTail.next=null;
    }
  }
  final void finalRemoveLast(Node<E> removeThis,Node<E> next){
    if(removeThis==head){
      this.head=next;
      next.prev=null;
    }else{
      joinNodes(removeThis.prev,next);
    }
  }
  final void finalRemoveFirst(Node<E> oldHead){
    if(oldHead==tail){
      this.head=null;
      this.tail=null;
    }else{
      this.head=oldHead=oldHead.next;
      oldHead.prev=null;
    }
  }
  final void finalRemoveFirst(Node<E> prev,Node<E> removeThis){
    if(removeThis==tail){
      this.tail=prev;
      prev.next=null;
    }else{
      joinNodes(prev,removeThis.next);
    }
  }
  static boolean uncheckedAnyMatches(Node<?> begin,Node<?> end,Predicate<Object> pred){
    while(!pred.test(begin.val)){
      if(begin==end){ return false; }
      begin=begin.next;
    }
    return true;
  }
  static <DST,SRC extends DST> void uncheckedCopyIntoArray(Node<SRC> src,int srcLength,DST[] dst,int dstOffset){
    for(;;){
      dst[dstOffset]=src.val;
      if(--srcLength==0){ return; }
      src=src.next;
      ++dstOffset;
    }
  }
  static <E> void uncheckedForEachForward(Node<E> begin,Node<E> end,Consumer<? super E> action){
    for(;;){
      action.accept(begin.val);
      if(begin==end){ return; }
      begin=begin.next;
    }
  }
  static int uncheckedForwardHashCode(Node<?> begin,Node<?> end){
    int hash=31+Objects.hashCode(begin.val);
    while(begin!=end){
      hash=hash*31+Objects.hashCode((begin=begin.next).val);
    }
    return hash;
  }
  static void uncheckedForwardToString(Node<?> begin,Node<?> end,StringBuilder builder){
    for(builder.append(begin.val);begin!=end;builder.append(',').append(' ').append((begin=begin.next).val)){}
  }
  static <E> Node<E> uncheckedIterateForward(Node<E> curr,int dist){
    do{
      curr=curr.next;
    }while(--dist!=0);
    return curr;
  }
  static <E> Node<E> uncheckedIterateReverse(Node<E> curr,int dist){
    do{
      curr=curr.prev;
    }while(--dist!=0);
    return curr;
  }
  static <E> void uncheckedReplaceAll(Node<E> begin,Node<E> end,Function<? super E,? extends E> operator){
    for(;;){
      begin.val=operator.apply(begin.val);
      if(begin==end){ return; }
      begin=begin.next;
    }
  }
  static <E> void uncheckedReverseSort(Node<E> begin,int size,Node<E> end){
    // TODO
  }
  static int uncheckedSearch(Node<?> head,Predicate<Object> pred){
    int index=1;
    while(!pred.test(head.val)){
      if((head=head.next)==null){ return -1; }
      ++index;
    }
    return index;
  }
  static <E> void uncheckedSort(Node<E> begin,int size,Node<E> end){
    // TODO
  }
  static <E> void uncheckedSort(Node<E> begin,int size,Node<E> end,Comparator<? super E> sorter){
    // TODO
  }
  transient Node<E> head;
  transient Node<E> tail;
  AbstractRefDblLnkSeq(){}
  AbstractRefDblLnkSeq(Node<E> headAndTail){
    super(1);
    this.head=headAndTail;
    this.tail=headAndTail;
  }
  AbstractRefDblLnkSeq(Node<E> head,int size,Node<E> tail){
    super(size);
    this.head=head;
    this.tail=tail;
  }
  public void addFirst(E val){
    Node<E> head;
    if((head=this.head)!=null){
      uncheckedPrepend(head,val);
    }else{
      uncheckedInit(val);
    }
  }
  public void addLast(E val){
    Node<E> tail;
    if((tail=this.tail)!=null){
      uncheckedAppend(tail,val);
    }else{
      uncheckedInit(val);
    }
  }
  @Override public int indexOf(boolean val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedFirstIndexOf(head,tail,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  @Override public int indexOf(Boolean val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedFirstIndexOf(head,tail,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  @Override public int indexOf(byte val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedFirstIndexOf(head,tail,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  @Override public int indexOf(Byte val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedFirstIndexOf(head,tail,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  @Override public int indexOf(char val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedFirstIndexOf(head,tail,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  @Override public int indexOf(Character val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedFirstIndexOf(head,tail,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  @Override public int indexOf(double val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedFirstIndexOf(head,tail,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  @Override public int indexOf(Double val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedFirstIndexOf(head,tail,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  @Override public int indexOf(float val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedFirstIndexOf(head,tail,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  @Override public int indexOf(Float val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedFirstIndexOf(head,tail,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  @Override public int indexOf(int val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedFirstIndexOf(head,tail,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  @Override public int indexOf(Integer val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedFirstIndexOf(head,tail,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  @Override public int indexOf(long val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedFirstIndexOf(head,tail,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  @Override public int indexOf(Long val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedFirstIndexOf(head,tail,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  @Override public int indexOf(short val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedFirstIndexOf(head,tail,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  @Override public int indexOf(Short val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedFirstIndexOf(head,tail,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  public boolean offer(E val){
    Node<E> head;
    if((head=this.head)!=null){
      uncheckedPrepend(head,val);
    }else{
      uncheckedInit(val);
    }
    return true;
  }
  public boolean offerFirst(E val){
    Node<E> head;
    if((head=this.head)!=null){
      uncheckedPrepend(head,val);
    }else{
      uncheckedInit(val);
    }
    return true;
  }
  public boolean offerLast(E val){
    Node<E> tail;
    if((tail=this.tail)!=null){
      uncheckedAppend(tail,val);
    }else{
      uncheckedInit(val);
    }
    return true;
  }
  public E peek(){
    Node<E> head;
    if((head=this.head)!=null){ return head.val; }
    return null;
  }
  public E peekFirst(){
    Node<E> head;
    if((head=this.head)!=null){ return head.val; }
    return null;
  }
  public E peekLast(){
    Node<E> tail;
    if((tail=this.tail)!=null){ return tail.val; }
    return null;
  }
  public E poll(){
    Node<E> head;
    if((head=this.head)!=null){
      final var ret=head.val;
      uncheckedRemoveFirst(head);
      return ret;
    }
    return null;
  }
  public E pollFirst(){
    Node<E> head;
    if((head=this.head)!=null){
      final var ret=head.val;
      uncheckedRemoveFirst(head);
      return ret;
    }
    return null;
  }
  public E pollLast(){
    Node<E> tail;
    if((tail=this.tail)!=null){
      final var ret=tail.val;
      uncheckedRemoveLast(tail);
      return ret;
    }
    return null;
  }
  public void push(E val){
    Node<E> head;
    if((head=this.head)!=null){
      uncheckedPrepend(head,val);
    }else{
      uncheckedInit(val);
    }
  }
  public boolean removeFirstOccurrence(boolean val){
    return removeVal(val);
  }
  public boolean removeFirstOccurrence(Boolean val){
    return removeVal(val);
  }
  public boolean removeFirstOccurrence(byte val){
    return removeVal(val);
  }
  public boolean removeFirstOccurrence(Byte val){
    return removeVal(val);
  }
  public boolean removeFirstOccurrence(char val){
    return removeVal(val);
  }
  public boolean removeFirstOccurrence(Character val){
    return removeVal(val);
  }
  public boolean removeFirstOccurrence(double val){
    return removeVal(val);
  }
  public boolean removeFirstOccurrence(Double val){
    return removeVal(val);
  }
  public boolean removeFirstOccurrence(float val){
    return removeVal(val);
  }
  public boolean removeFirstOccurrence(Float val){
    return removeVal(val);
  }
  public boolean removeFirstOccurrence(int val){
    return removeVal(val);
  }
  public boolean removeFirstOccurrence(Integer val){
    return removeVal(val);
  }
  public boolean removeFirstOccurrence(long val){
    return removeVal(val);
  }
  public boolean removeFirstOccurrence(Long val){
    return removeVal(val);
  }
  public boolean removeFirstOccurrence(Object val){
    return remove(val);
  }
  public boolean removeFirstOccurrence(short val){
    return removeVal(val);
  }
  public boolean removeFirstOccurrence(Short val){
    return removeVal(val);
  }
  public int search(boolean val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  public int search(Boolean val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  public int search(byte val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  public int search(Byte val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  public int search(char val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  public int search(Character val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  public int search(double val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  public int search(Double val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  public int search(float val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  public int search(Float val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  public int search(int val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  public int search(Integer val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  public int search(long val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  public int search(Long val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  public int search(short val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  public int search(Short val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  final Node<E> getReadableNode(int headDist,int tailDist){
    Node<E> node;
    if(headDist>=tailDist){
      node=this.tail;
      if(tailDist!=1){
        node=uncheckedIterateReverse(node,tailDist-1);
      }
    }else{
      node=this.head;
      if(headDist!=0){
        node=uncheckedIterateForward(node,headDist);
      }
    }
    return node;
  }
  final Node<E> getWritableNode(int headDist,int tailDist){
    if(tailDist==0){ return null; }
    Node<E> node;
    if(headDist>=tailDist){
      node=this.tail;
      if(tailDist!=1){
        node=uncheckedIterateReverse(node,tailDist-1);
      }
    }else{
      node=this.head;
      if(headDist!=0){
        node=uncheckedIterateForward(node,headDist);
      }
    }
    return node;
  }
  abstract void uncheckedAppend(Node<E> oldTail,E val);
  abstract void uncheckedClear();
  abstract void uncheckedInit(E val);
  abstract void uncheckedPrepend(Node<E> oldHead,E val);
  abstract void uncheckedRemoveFirst(Node<E> oldHead);
  abstract void uncheckedRemoveLast(Node<E> oldTail);
  static int uncheckedFirstIndexOf(Node<?> head,Node<?> tail,Predicate<Object> pred){
    int index=0;
    while(!pred.test(head.val)){
      if(head==tail){ return -1; }
      ++index;
      head=head.next;
    }
    return index;
  }
  static class Node<E>{
    transient Node<E> prev;
    transient E val;
    transient Node<E> next;
    Node(E val){
      this.val=val;
    }
    Node(E val,Node<E> next){
      this.val=val;
      joinNodes(this,next);
    }
    Node(Node<E> prev,E val){
      joinNodes(prev,this);
      this.val=val;
    }
    Node(Node<E> prev,E val,Node<E> next){
      joinNodes(prev,this);
      this.val=val;
      joinNodes(this,next);
    }
  }
}
