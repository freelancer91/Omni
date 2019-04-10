package omni.impl.seq;
import omni.api.OmniCollection;
import omni.util.ToStringUtil;
import omni.util.OmniArray;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import omni.util.TypeUtil;
public abstract class LongSnglLnkSeq implements OmniCollection.OfLong,Cloneable{
  static class Node{
    transient long val;
    transient Node next;
    Node(long val){
      this.val=val;
    }
    Node(long val,Node next){
      this.val=val;
      this.next=next;
    }
    private int uncheckedHashCode(){
      int hash=31+Long.hashCode(val);
      for(var curr=next;curr!=null;curr=curr.next){
        hash=(hash*31)+Long.hashCode(curr.val);
      }
      return hash;
    }
    private int uncheckedToString(byte[] buffer){
      int bufferOffset=1;
      for(var curr=this;;buffer[bufferOffset]=(byte)',',buffer[++bufferOffset]=(byte)' ',++bufferOffset){
        bufferOffset=ToStringUtil.getStringLong(curr.val,buffer,bufferOffset);
        if((curr=curr.next)==null){
          return bufferOffset;
        }
      }
    }
    private void uncheckedToString(ToStringUtil.OmniStringBuilderByte builder){
      //TODO
      for(var curr=this;;builder.uncheckedAppendCommaAndSpace()){
        builder.uncheckedAppendLong(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedForEach(LongConsumer action){
      for(var curr=this;;){
        action.accept(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedCopyInto(long[] dst){
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
    private void uncheckedCopyInto(Long[] dst){
      int dstOffset=0;
      for(var curr=this;;++dstOffset){
        dst[dstOffset]=(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedCopyInto(double[] dst){
      int dstOffset=0;
      for(var curr=this;;++dstOffset){
        dst[dstOffset]=(double)(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedCopyInto(float[] dst){
      int dstOffset=0;
      for(var curr=this;;++dstOffset){
        dst[dstOffset]=(float)(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
  }
  transient int size;
  transient Node head;
  private LongSnglLnkSeq(){
  }
  private LongSnglLnkSeq(int size,Node head){
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
      int size;
      final byte[] buffer;
      if((size=this.size)<=(OmniArray.MAX_ARR_SIZE/22)){(buffer=new byte[size*22])
        [size=head.uncheckedToString(buffer)]=(byte)']';
        buffer[0]=(byte)'[';
        return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
      }else{
        final ToStringUtil.OmniStringBuilderByte builder;
        head.uncheckedToString(builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
        builder.uncheckedAppendChar((byte)']');
        buffer=builder.buffer;
        buffer[0]=(byte)'[';
        return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
      }
    }
    return "[]";
  }
  @Override public void forEach(LongConsumer action){
    final Node head;
    if((head=this.head)!=null){
      head.uncheckedForEach(action);
    }
  }
  @Override public void forEach(Consumer<? super Long> action){
    final Node head;
    if((head=this.head)!=null){
      head.uncheckedForEach(action::accept);
    }
  }
  public void push(long val){
    this.head=new Node(val,this.head);
    ++this.size;
  }
  @Override public boolean add(long val){
    push(val);
    return true;
  }
  @Override
  public boolean add(Long val)
  {
    push((long)val);
    return true;
  }
  @Override
  public boolean add(boolean val)
  {
    push((long)TypeUtil.castToLong(val));
    return true;
  }
  @Override
  public boolean add(int val)
  {
    push((long)val);
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
  @Override public long[] toLongArray(){
    final Node head;
    if((head=this.head)!=null){
      final long[] dst;
      head.uncheckedCopyInto(dst=new long[this.size]);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override public Long[] toArray(){
    final Node head;
    if((head=this.head)!=null){
      final Long[] dst;
      head.uncheckedCopyInto(dst=new Long[this.size]);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_BOXED_ARR;
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
  @Override public float[] toFloatArray(){
    final Node head;
    if((head=this.head)!=null){
      final float[] dst;
      head.uncheckedCopyInto(dst=new float[this.size]);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  //TODO contains
  //TODO removeVal
  //TODO removeIf methods
  //TODO etc
}
