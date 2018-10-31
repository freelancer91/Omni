package omni.impl.seq.dbllnk;
import java.util.function.Consumer;
import java.util.function.Predicate;
import omni.api.OmniDeque;
import omni.api.OmniIterator;
import omni.util.OmniPred;
public class UncheckedRefDblLnkList<E>extends AbstractUncheckedRefDblLnkSeq<E>
    implements OmniDeque.OfRef<E>{
  UncheckedRefDblLnkList(){
    super();
  }
  UncheckedRefDblLnkList(Node<E> head,int size,Node<E> tail){
    super(head,size,tail);
  }
  UncheckedRefDblLnkList(Node<E> headAndTail){
    super(headAndTail);
  }
  private boolean uncheckedRemoveLastMatch(Node<E> tail,Predicate<? super E> pred){
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
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeLastOccurrence(short val){
    Node<E> tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public boolean removeLastOccurrence(Short val){
    Node<E> tail;
    return (tail=this.tail)!=null&&uncheckedRemoveLastMatch(tail,OmniPred.OfRef.getEqualsPred(val));
  }
  @Override public int search(Object val){
    Node<E> head;
    if((head=this.head)!=null){ return uncheckedSearch(head,OmniPred.OfRef.getEqualsPred(val)); }
    return -1;
  }
  @Override public E element(){
    return head.val;
  }
  @Override public E remove(){
    Node<E> head;
    final var ret=(head=this.head).val;
    if(--size==0){
      this.head=null;
      tail=null;
    }else{
      this.head=head=head.next;
      head.prev=null;
    }
    return ret;
  }
  @Override public E pop(){
    Node<E> head;
    final var ret=(head=this.head).val;
    if(--size==0){
      this.head=null;
      tail=null;
    }else{
      this.head=head=head.next;
      head.prev=null;
    }
    return ret;
  }
  static class DescendingItr<E> implements OmniIterator.OfRef<E>{
    transient final AbstractUncheckedRefDblLnkSeq<E> root;
    transient Node<E> cursor;
    DescendingItr(AbstractUncheckedRefDblLnkSeq<E> root){
      this.root=root;
      this.cursor=root.tail;
    }
    @Override public boolean hasNext(){
      return this.cursor!=null;
    }
    @Override public E next(){
      Node<E> cursor;
      final var ret=(cursor=this.cursor).val;
      this.cursor=cursor.prev;
      return ret;
    }
    @Override public void forEachRemaining(Consumer<? super E> action){
      Node<E> cursor;
      if((cursor=this.cursor)!=null){
        do{
          action.accept(cursor.val);
        }while((cursor=cursor.prev)!=null);
        this.cursor=null;
      }
    }
    @Override public void remove(){
      final var root=this.root;
      Node<E> cursor;
      if((cursor=this.cursor)==null){
        if((cursor=root.head)==root.tail){
          root.head=null;
          root.tail=null;
        }else{
          root.head=cursor=cursor.next;
          cursor.prev=null;
        }
      }else{
        joinNodes(cursor,cursor.next.next);
      }
      --root.size;
    }
  }
  @Override public omni.api.OmniIterator.OfRef<E> descendingIterator(){
    return new DescendingItr<>(this);
  }
  @Override public E getFirst(){
    return head.val;
  }
  @Override public E getLast(){
    return tail.val;
  }
  @Override public E removeFirst(){
    Node<E> head;
    final var ret=(head=this.head).val;
    if(--size==0){
      this.head=null;
      tail=null;
    }else{
      this.head=head=head.next;
      head.prev=null;
    }
    return ret;
  }
  @Override public E removeLast(){
    Node<E> tail;
    final var ret=(tail=this.tail).val;
    if(--size==0){
      head=null;
      this.tail=null;
    }else{
      this.tail=tail=tail.prev;
      tail.next=null;
    }
    return ret;
  }
}