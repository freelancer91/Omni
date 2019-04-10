package omni.impl.seq;
import omni.api.OmniCollection;
import omni.util.OmniArray;
import java.util.function.Consumer;
import java.util.Objects;
public abstract class RefSnglLnkSeq<E> implements OmniCollection.OfRef<E>,Cloneable{
  static class Node<E>{
    transient E val;
    transient Node<E> next;
    Node(E val){
      this.val=val;
    }
    Node(E val,Node<E> next){
      this.val=val;
      this.next=next;
    }
    private int uncheckedHashCode(){
      int hash=31+Objects.hashCode(val);
      for(var curr=next;curr!=null;curr=curr.next){
        hash=(hash*31)+Objects.hashCode(curr.val);
      }
      return hash;
    }
    private void uncheckedToString(StringBuilder builder){
      for(var curr=this;;builder.append(',').append(' ')){
        builder.append(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedForEach(Consumer<? super E> action){
      for(var curr=this;;){
        action.accept(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedCopyInto(Object[] dst){
      int dstOffset=0;
      for(var curr=this;;++dstOffset){
        dst[dstOffset]=(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
  }
  transient int size;
  transient Node<E> head;
  private RefSnglLnkSeq(){
  }
  private RefSnglLnkSeq(int size,Node<E> head){
    this.size=size;
    this.head=head;
  }
  @Override public abstract Object clone();
  @Override public int size(){
    return this.size;
  }
  @Override public boolean isEmpty(){
    return this.size==0;
  }
  @Override public void clear(){
    this.head=null;
    this.size=0;
  }
  @Override public int hashCode(){
    final Node<E> head;
    if((head=this.head)!=null){
      return head.uncheckedHashCode();
    }
    return 1;
  }
  @Override public String toString(){
    final Node<E> head;
    if((head=this.head)!=null){
      final StringBuilder builder=new StringBuilder("[");
      head.uncheckedToString(builder);
      return builder.append(']').toString();
    }
    return "[]";
  }
  @Override public void forEach(Consumer<? super E> action){
    final Node<E> head;
    if((head=this.head)!=null){
      head.uncheckedForEach(action);
    }
  }
  public void push(E val){
    this.head=new Node<E>(val,this.head);
    ++this.size;
  }
  @Override public boolean add(E val){
    push(val);
    return true;
  }
  @Override public <T> T[] toArray(T[] arr){
    final Node<E> head;
    if((head=this.head)!=null){
      head.uncheckedCopyInto(arr=OmniArray.uncheckedArrResize(size,arr));
    }else{
      arr[0]=null;
    }
    return arr;
  }
  @Override public Object[] toArray(){
    final Node<E> head;
    if((head=this.head)!=null){
      final Object[] dst;
      head.uncheckedCopyInto(dst=new Object[this.size]);
      return dst;
    }
    return OmniArray.OfRef.DEFAULT_ARR;
  }
  //TODO contains
  //TODO removeVal
  //TODO removeIf methods
  //TODO etc
}
