package omni.impl.seq;
import omni.api.OmniCollection;
import omni.util.ToStringUtil;
import omni.util.OmniArray;
import java.util.function.Consumer;
import omni.function.ByteConsumer;
import omni.util.TypeUtil;
public abstract class ByteSnglLnkSeq implements OmniCollection.OfByte,Cloneable{
  static class Node{
    transient byte val;
    transient Node next;
    Node(byte val){
      this.val=val;
    }
    Node(byte val,Node next){
      this.val=val;
      this.next=next;
    }
    private int uncheckedHashCode(){
      int hash=31+(val);
      for(var curr=next;curr!=null;curr=curr.next){
        hash=(hash*31)+(curr.val);
      }
      return hash;
    }
    private int uncheckedToString(byte[] buffer){
      int bufferOffset=1;
      for(var curr=this;;buffer[bufferOffset]=(byte)',',buffer[++bufferOffset]=(byte)' ',++bufferOffset){
        bufferOffset=ToStringUtil.getStringShort(curr.val,buffer,bufferOffset);
        if((curr=curr.next)==null){
          return bufferOffset;
        }
      }
    }
    private void uncheckedToString(ToStringUtil.OmniStringBuilderByte builder){
      //TODO
      for(var curr=this;;builder.uncheckedAppendCommaAndSpace()){
        builder.uncheckedAppendShort(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedForEach(ByteConsumer action){
      for(var curr=this;;){
        action.accept(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedCopyInto(byte[] dst){
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
    private void uncheckedCopyInto(Byte[] dst){
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
    private void uncheckedCopyInto(long[] dst){
      int dstOffset=0;
      for(var curr=this;;++dstOffset){
        dst[dstOffset]=(long)(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedCopyInto(int[] dst){
      int dstOffset=0;
      for(var curr=this;;++dstOffset){
        dst[dstOffset]=(int)(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedCopyInto(short[] dst){
      int dstOffset=0;
      for(var curr=this;;++dstOffset){
        dst[dstOffset]=(short)(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
  }
  transient int size;
  transient Node head;
  private ByteSnglLnkSeq(){
  }
  private ByteSnglLnkSeq(int size,Node head){
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
      if((size=this.size)<=(OmniArray.MAX_ARR_SIZE/6)){(buffer=new byte[size*6])
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
  @Override public void forEach(ByteConsumer action){
    final Node head;
    if((head=this.head)!=null){
      head.uncheckedForEach(action);
    }
  }
  @Override public void forEach(Consumer<? super Byte> action){
    final Node head;
    if((head=this.head)!=null){
      head.uncheckedForEach(action::accept);
    }
  }
  public void push(byte val){
    this.head=new Node(val,this.head);
    ++this.size;
  }
  @Override public boolean add(byte val){
    push(val);
    return true;
  }
  @Override
  public boolean add(Byte val)
  {
    push((byte)val);
    return true;
  }
  @Override
  public boolean add(boolean val)
  {
    push((byte)TypeUtil.castToByte(val));
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
  @Override public byte[] toByteArray(){
    final Node head;
    if((head=this.head)!=null){
      final byte[] dst;
      head.uncheckedCopyInto(dst=new byte[this.size]);
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_ARR;
  }
  @Override public Byte[] toArray(){
    final Node head;
    if((head=this.head)!=null){
      final Byte[] dst;
      head.uncheckedCopyInto(dst=new Byte[this.size]);
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_BOXED_ARR;
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
  @Override public long[] toLongArray(){
    final Node head;
    if((head=this.head)!=null){
      final long[] dst;
      head.uncheckedCopyInto(dst=new long[this.size]);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override public int[] toIntArray(){
    final Node head;
    if((head=this.head)!=null){
      final int[] dst;
      head.uncheckedCopyInto(dst=new int[this.size]);
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  @Override public short[] toShortArray(){
    final Node head;
    if((head=this.head)!=null){
      final short[] dst;
      head.uncheckedCopyInto(dst=new short[this.size]);
      return dst;
    }
    return OmniArray.OfShort.DEFAULT_ARR;
  }
  //TODO contains
  //TODO removeVal
  //TODO removeIf methods
  //TODO etc
}
