package omni.impl.seq.arr.offloat;
import omni.api.OmniStack;
import omni.api.OmniIterator;
import omni.util.ArrCopy;
import omni.impl.seq.arr.ArrSeqUtil;
import omni.function.FloatConsumer;
public class UncheckedStack extends AbstractSeq.Unchecked implements OmniStack.OfFloat
{
  UncheckedStack()
  {
    super();
  }
  UncheckedStack(int capacity)
  {
    super(capacity);
  }
  private UncheckedStack(int size,float[] arr)
  {
    super(size,arr);
  }
  @Override
  public Object clone()
  {
    final float[] arr;
    final int size;
    if((size=this.size)!=0)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr=new float[size],0,size);
    }
    else
    {
      arr=null;
    }
    return new UncheckedStack(size,arr);
  }
  @Override
  public boolean equals(Object val)
  {
    //TODO
    return false;
  }
  @Override
  public OmniIterator.OfFloat iterator()
  {
    //TODO
    return null;
  }
  @Override
  public Float poll()
  {
    final int size;
    if((size=this.size)!=0)
    {
      return (Float)(super.uncheckedPop(size-1));
    }
    return null;
  }
  @Override
  public Float pop()
  {
    return super.uncheckedPop(size-1);
  }
  @Override
  void uncheckedCopyInto(Float[] arr,int size)
  {
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
  @Override
  void uncheckedForEach(int size,FloatConsumer action)
  {
    ArrSeqUtil.uncheckedReverseForEach(arr,0,size,action);
  }
  @Override
  int uncheckedHashCode(int size)
  {
    return ArrSeqUtil.reverseHashCode(arr,0,size);
  }
  @Override
  void uncheckedToString(int size,StringBuilder builder)
  {
    ArrSeqUtil.reverseToString(arr,0,size,builder);
  }
  @Override
  void uncheckedCopyInto(Object[] arr,int size)
  {
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
  @Override
  public void push(Float val)
  {
    super.push((float)val);
  }
  @Override
  public float popFloat()
  {
    return super.uncheckedPop(size-1);
  }
  @Override
  boolean uncheckedremoveVal0(int size)
  {
    final var arr=this.arr;
    int index;
    for(index=--size;0!=(arr[index]);--index)
    {
      if(index==0)
      {
        return false;
      }
    }
    ArrSeqUtil.eraseIndexHelper(arr,index,size);
    this.size=size;
    return true;
  }
  @Override
  boolean uncheckedremoveValBits(int size,int bits)
  {
    final var arr=this.arr;
    int index;
    for(index=--size;bits!=Float.floatToRawIntBits(arr[index]);--index)
    {
      if(index==0)
      {
        return false;
      }
    }
    ArrSeqUtil.eraseIndexHelper(arr,index,size);
    this.size=size;
    return true;
  }
  @Override
  boolean uncheckedremoveValNaN(int size)
  {
    final var arr=this.arr;
    int index;
    for(index=--size;!Float.isNaN(arr[index]);--index)
    {
      if(index==0)
      {
        return false;
      }
    }
    ArrSeqUtil.eraseIndexHelper(arr,index,size);
    this.size=size;
    return true;
  }
  @Override
  public double pollDouble()
  {
    final int size;
    if((size=this.size)!=0)
    {
      return (double)(super.uncheckedPop(size-1));
    }
    return Double.NaN;
  }
  @Override
  void uncheckedCopyInto(double[] arr,int size)
  {
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
  @Override
  public float pollFloat()
  {
    final int size;
    if((size=this.size)!=0)
    {
      return (float)(super.uncheckedPop(size-1));
    }
    return Float.NaN;
  }
  @Override
  void uncheckedCopyInto(float[] arr,int size)
  {
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
}
