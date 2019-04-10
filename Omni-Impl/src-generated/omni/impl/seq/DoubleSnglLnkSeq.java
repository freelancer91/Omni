package omni.impl.seq;
import omni.api.OmniCollection;
import omni.util.OmniArray;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import omni.util.HashUtil;
import omni.util.TypeUtil;
public abstract class DoubleSnglLnkSeq implements OmniCollection.OfDouble,Cloneable{
  static class Node{
    transient double val;
    transient Node next;
    Node(double val){
      this.val=val;
    }
    Node(double val,Node next){
      this.val=val;
      this.next=next;
    }
    private int uncheckedHashCode(){
      int hash=31+HashUtil.hashDouble(val);
      for(var curr=next;curr!=null;curr=curr.next){
        hash=(hash*31)+HashUtil.hashDouble(curr.val);
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
    private void uncheckedForEach(DoubleConsumer action){
      for(var curr=this;;){
        action.accept(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedCopyInto(double[] dst){
      int dstOffset=0;
      for(var curr=this;;++dstOffset){
        dst[dstOffset]=(curr.val);
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
    private void uncheckedCopyInto(Double[] dst){
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
  transient Node head;
  private DoubleSnglLnkSeq(){
  }
  private DoubleSnglLnkSeq(int size,Node head){
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
    final Node head;
    if((head=this.head)!=null){
      return head.uncheckedHashCode();
    }
    return 1;
  }
  @Override public String toString(){
    final Node head;
    if((head=this.head)!=null){
      final StringBuilder builder=new StringBuilder("[");
      head.uncheckedToString(builder);
      return builder.append(']').toString();
    }
    return "[]";
  }
  @Override public void forEach(DoubleConsumer action){
    final Node head;
    if((head=this.head)!=null){
      head.uncheckedForEach(action);
    }
  }
  @Override public void forEach(Consumer<? super Double> action){
    final Node head;
    if((head=this.head)!=null){
      head.uncheckedForEach(action::accept);
    }
  }
  public void push(double val){
    this.head=new Node(val,this.head);
    ++this.size;
  }
  @Override public boolean add(double val){
    push(val);
    return true;
  }
  @Override
  public boolean add(Double val)
  {
    push((double)val);
    return true;
  }
  @Override
  public boolean add(boolean val)
  {
    push((double)TypeUtil.castToDouble(val));
    return true;
  }
  @Override
  public boolean add(int val)
  {
    push((double)val);
    return true;
  }
  @Override
  public boolean add(char val)
  {
    push((double)val);
    return true;
  }
  @Override
  public boolean add(short val)
  {
    push((double)val);
    return true;
  }
  @Override
  public boolean add(long val)
  {
    push((double)val);
    return true;
  }
  @Override
  public boolean add(float val)
  {
    push((double)val);
    return true;
  }
  @Override public <T> T[] toArray(T[] arr){
    final Node head;
    if((head=this.head)!=null){
      head.uncheckedCopyInto(arr=OmniArray.uncheckedArrResize(size,arr));
    }else{
      arr[0]=null;
    }
    return arr;
  }
  @Override public double[] toDoubleArray(){
    final Node head;
    if((head=this.head)!=null){
      final double[] dst;
      head.uncheckedCopyInto(dst=new double[this.size]);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public Double[] toArray(){
    final Node head;
    if((head=this.head)!=null){
      final Double[] dst;
      head.uncheckedCopyInto(dst=new Double[this.size]);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_BOXED_ARR;
  }
  //TODO contains
  //TODO removeVal
  //TODO removeIf methods
  //TODO etc
}
