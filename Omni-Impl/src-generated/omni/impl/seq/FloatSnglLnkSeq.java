package omni.impl.seq;
import omni.api.OmniCollection;
import omni.util.ToStringUtil;
import omni.util.OmniArray;
import java.util.function.Consumer;
import omni.function.FloatConsumer;
import omni.util.HashUtil;
import omni.util.TypeUtil;
public abstract class FloatSnglLnkSeq implements OmniCollection.OfFloat,Cloneable{
  static class Node{
    transient float val;
    transient Node next;
    Node(float val){
      this.val=val;
    }
    Node(float val,Node next){
      this.val=val;
      this.next=next;
    }
    private int uncheckedHashCode(){
      int hash=31+HashUtil.hashFloat(val);
      for(var curr=next;curr!=null;curr=curr.next){
        hash=(hash*31)+HashUtil.hashFloat(curr.val);
      }
      return hash;
    }
    private int uncheckedToString(byte[] buffer){
      int bufferOffset=1;
      for(var curr=this;;buffer[bufferOffset]=(byte)',',buffer[++bufferOffset]=(byte)' ',++bufferOffset){
        bufferOffset=ToStringUtil.getStringFloat(curr.val,buffer,bufferOffset);
        if((curr=curr.next)==null){
          return bufferOffset;
        }
      }
    }
    private void uncheckedToString(ToStringUtil.OmniStringBuilderByte builder){
      //TODO
      for(var curr=this;;builder.uncheckedAppendCommaAndSpace()){
        builder.uncheckedAppendFloat(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedForEach(FloatConsumer action){
      for(var curr=this;;){
        action.accept(curr.val);
        if((curr=curr.next)==null){
          return;
        }
      }
    }
    private void uncheckedCopyInto(float[] dst){
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
    private void uncheckedCopyInto(Float[] dst){
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
  }
  transient int size;
  transient Node head;
  private FloatSnglLnkSeq(){
  }
  private FloatSnglLnkSeq(int size,Node head){
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
      if((size=this.size)<=(OmniArray.MAX_ARR_SIZE/17)){(buffer=new byte[size*17])
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
  @Override public void forEach(FloatConsumer action){
    final Node head;
    if((head=this.head)!=null){
      head.uncheckedForEach(action);
    }
  }
  @Override public void forEach(Consumer<? super Float> action){
    final Node head;
    if((head=this.head)!=null){
      head.uncheckedForEach(action::accept);
    }
  }
  public void push(float val){
    this.head=new Node(val,this.head);
    ++this.size;
  }
  @Override public boolean add(float val){
    push(val);
    return true;
  }
  @Override
  public boolean add(Float val)
  {
    push((float)val);
    return true;
  }
  @Override
  public boolean add(boolean val)
  {
    push((float)TypeUtil.castToFloat(val));
    return true;
  }
  @Override
  public boolean add(int val)
  {
    push((float)val);
    return true;
  }
  @Override
  public boolean add(char val)
  {
    push((float)val);
    return true;
  }
  @Override
  public boolean add(short val)
  {
    push((float)val);
    return true;
  }
  @Override
  public boolean add(long val)
  {
    push((float)val);
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
  @Override public float[] toFloatArray(){
    final Node head;
    if((head=this.head)!=null){
      final float[] dst;
      head.uncheckedCopyInto(dst=new float[this.size]);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override public Float[] toArray(){
    final Node head;
    if((head=this.head)!=null){
      final Float[] dst;
      head.uncheckedCopyInto(dst=new Float[this.size]);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_BOXED_ARR;
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
  //TODO contains
  //TODO removeVal
  //TODO removeIf methods
  //TODO etc
}
