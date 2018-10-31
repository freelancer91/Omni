package omni.impl.seq.dbllnk;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import omni.api.OmniDeque;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.impl.CheckedCollection;
import omni.util.OmniPred;
public class CheckedRefDblLnkList<E>extends AbstractCheckedRefDblLnkSeq<E> implements OmniDeque.OfRef<E>{
  transient int modCount;
  CheckedRefDblLnkList(){
    super();
  }
  CheckedRefDblLnkList(Node<E> head,int size,Node<E> tail){
    super(head,size,tail);
  }
  CheckedRefDblLnkList(Node<E> headAndTail){
    super(headAndTail);
  }
  private boolean uncheckedRemoveLastNonNull(Node<E> tail,Object nonNull){
    final int modCount=this.modCount;
    Node<E> next;
    try{
      if(nonNull.equals(tail.val)){
        super.finalRemoveLast(tail);
      }else{
        final var head=this.head;
        do{
          if(tail==head){ return false; }
        }while(!nonNull.equals((tail=(next=tail).prev).val));
        super.finalRemoveLast(tail,next);
      }
    }finally{
      CheckedCollection.checkModCount(modCount,this.modCount);
    }
    this.modCount=modCount+1;
    --size;
    return true;
  }
  private boolean uncheckedRemoveLastMatch(Node<E> tail,Predicate<Object> pred){
    if(pred.test(tail.val)){
      super.finalRemoveLast(tail);
    }else{
      final var head=this.head;
      Node<E> next;
      do{
        if(tail==head){ return false; }
      }while(!pred.test((tail=(next=tail).prev).val));
      super.finalRemoveLast(tail,next);
    }
    ++modCount;
    --size;
    return true;
  }
  @Override public boolean removeLastOccurrence(boolean val){
    Node<E> tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeLastOccurrence(Boolean val){
    Node<E> tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeLastOccurrence(byte val){
    Node<E> tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeLastOccurrence(Byte val){
    Node<E> tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeLastOccurrence(char val){
    Node<E> tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeLastOccurrence(Character val){
    Node<E> tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeLastOccurrence(double val){
    Node<E> tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeLastOccurrence(Double val){
    Node<E> tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeLastOccurrence(float val){
    Node<E> tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeLastOccurrence(Float val){
    Node<E> tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeLastOccurrence(int val){
    Node<E> tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeLastOccurrence(Integer val){
    Node<E> tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeLastOccurrence(long val){
    Node<E> tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeLastOccurrence(Long val){
    Node<E> tail;
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
    Node<E> tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeLastOccurrence(Short val){
    Node<E> tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
  }
  private boolean uncheckedRemoveFirstNonNull(Node<E> head,Object nonNull){
    final int modCount=this.modCount;
    Node<E> prev;
    try{
      if(nonNull.equals(head.val)){
        super.finalRemoveFirst(head);
      }else{
        final var tail=this.tail;
        do{
          if(head==tail){ return false; }
        }while(!nonNull.equals((head=(prev=head).next).val));
        super.finalRemoveFirst(prev,head);
      }
    }finally{
      CheckedCollection.checkModCount(modCount,this.modCount);
    }
    this.modCount=modCount+1;
    --size;
    return true;
  }
  private boolean uncheckedRemoveFirstMatch(Node<E> head,Predicate<Object> pred){
    if(pred.test(head.val)){
      super.finalRemoveFirst(head);
    }else{
      final var tail=this.tail;
      Node<E> prev;
      do{
        if(head==tail){ return false; }
      }while(!pred.test((head=(prev=head).next).val));
      super.finalRemoveFirst(prev,head);
    }
    ++modCount;
    --size;
    return true;
  }
  @Override public boolean remove(Object val){
    Node<E> head;
    if((head=this.head)!=null){
      if(val!=null){ return uncheckedRemoveFirstNonNull(head,val); }
      return uncheckedRemoveFirstMatch(head,Objects::isNull);
    }
    return false;
  }
  @Override public boolean removeVal(boolean val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(Boolean val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(byte val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(Byte val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(char val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(Character val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(double val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(Double val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(float val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(Float val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(int val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(Integer val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(long val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(Long val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(short val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeVal(Short val){
    Node<E> head;
    return (head=this.head)!=null&&uncheckedRemoveFirstMatch(head,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public Object[] toArray(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public <T> T[] toArray(T[] dst){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public int search(Object val){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public E element(){
    Node<E> head;
    if((head=this.head)!=null){ return head.val; }
    throw new NoSuchElementException();
  }
  @Override public E remove(){
    Node<E> head;
    if((head=this.head)!=null){
      ++this.modCount;
      final var ret=head.val;
      super.finalRemoveFirst(head);
      --size;
      return ret;
    }
    throw new NoSuchElementException();
  }
  @Override public boolean add(E val){
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
  @Override public OmniIterator.OfRef<E> iterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public boolean removeIf(Predicate<? super E> filter){
    // TODO Auto-generated method stub
    return false;
  }
  @Override public E pop(){
    Node<E> head;
    if((head=this.head)!=null){
      ++this.modCount;
      final var ret=head.val;
      super.finalRemoveFirst(head);
      --size;
      return ret;
    }
    throw new NoSuchElementException();
  }
  @Override public void add(int index,E val){
    // TODO Auto-generated method stub
  }
  @Override public E get(int index){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public OmniListIterator.OfRef<E> listIterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public OmniListIterator.OfRef<E> listIterator(int index){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public void put(int index,E val){
    // TODO Auto-generated method stub
  }
  @Override public E remove(int index){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public void replaceAll(UnaryOperator<E> operator){
    // TODO Auto-generated method stub
  }
  @Override public E set(int index,E val){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public void sort(Comparator<? super E> sorter){
    // TODO Auto-generated method stub
  }
  @Override public OmniList.OfRef<E> subList(int fromIndex,int toIndex){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public int indexOf(Object val){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public void reverseSort(){
    // TODO Auto-generated method stub
  }
  @Override public void sort(){
    // TODO Auto-generated method stub
  }
  @Override public OmniIterator.OfRef<E> descendingIterator(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public E getFirst(){
    Node<E> head;
    if((head=this.head)!=null){ return head.val; }
    throw new NoSuchElementException();
  }
  @Override public E getLast(){
    Node<E> tail;
    if((tail=this.tail)!=null){ return tail.val; }
    throw new NoSuchElementException();
  }
  @Override public E removeFirst(){
    Node<E> head;
    if((head=this.head)!=null){
      ++this.modCount;
      final var ret=head.val;
      super.finalRemoveFirst(head);
      --size;
      return ret;
    }
    throw new NoSuchElementException();
  }
  @Override public E removeLast(){
    Node<E> tail;
    if((tail=this.tail)!=null){
      ++this.modCount;
      final var ret=tail.val;
      super.finalRemoveLast(tail);
      --size;
      return ret;
    }
    throw new NoSuchElementException();
  }
  @Override void uncheckedAppend(Node<E> oldTail,E val){
    // TODO Auto-generated method stub
  }
  @Override void uncheckedClear(){
    // TODO Auto-generated method stub
  }
  @Override void uncheckedInit(E val){
    // TODO Auto-generated method stub
  }
  @Override void uncheckedPrepend(Node<E> oldHead,E val){
    // TODO Auto-generated method stub
  }
  @Override void uncheckedRemoveFirst(Node<E> oldHead){
    // TODO Auto-generated method stub
  }
  @Override void uncheckedRemoveLast(Node<E> oldTail){}
  @Override protected int uncheckedLastIndexOfNonNull(int size,Object nonNull){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override protected int uncheckedLastIndexOfMatch(int size,Predicate<Object> pred){
    // TODO Auto-generated method stub
    return 0;
  }
  @Override public Object clone(){
    // TODO Auto-generated method stub
    return null;
  }
  @Override public boolean equals(Object val){
    // TODO Auto-generated method stub
    return false;
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
    return 1;
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
}
