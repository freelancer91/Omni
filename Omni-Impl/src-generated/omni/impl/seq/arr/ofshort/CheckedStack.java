package omni.impl.seq.arr.ofshort;
import omni.api.OmniStack;
import omni.api.OmniIterator;
import omni.util.ArrCopy;
import omni.impl.seq.arr.ArrSeqUtil;
import omni.function.ShortConsumer;
import java.util.NoSuchElementException;
import omni.impl.CheckedCollection;
public class CheckedStack extends AbstractSeq.Checked implements OmniStack.OfShort
{
  CheckedStack()
  {
    super();
  }
  CheckedStack(int capacity)
  {
    super(capacity);
  }
  private CheckedStack(int size,short[] arr)
  {
    super(size,arr);
  }
  @Override
  public Object clone()
  {
    final short[] arr;
    final int size;
    if((size=this.size)!=0)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr=new short[size],0,size);
    }
    else
    {
      arr=null;
    }
    return new CheckedStack(size,arr);
  }
  @Override
  public boolean equals(Object val)
  {
    //TODO
    return false;
  }
  @Override
  public OmniIterator.OfShort iterator()
  {
    //TODO
    return null;
  }
  @Override
  public Short poll()
  {
    final int size;
    if((size=this.size)!=0)
    {
      ++modCount;
      return (Short)(super.uncheckedPop(size-1));
    }
    return null;
  }
  @Override
  public Short pop()
  {
    final int size;
    if((size=this.size)!=0)
    {
      ++modCount;
      return super.uncheckedPop(size-1);
    }
    throw new NoSuchElementException();
  }
  @Override
  void uncheckedCopyInto(Short[] arr,int size)
  {
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
  @Override
  void uncheckedForEach(int size,ShortConsumer action)
  {
    final int modCount=this.modCount;
    try
    {
      ArrSeqUtil.uncheckedReverseForEach(arr,0,size,action);
    }
    finally
    {
      CheckedCollection.checkModCount(modCount,this.modCount);
    }
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
  public void push(Short val)
  {
    ++modCount;
    super.push((short)val);
  }
  @Override
  public short popShort()
  {
    final int size;
    if((size=this.size)!=0)
    {
      ++modCount;
      return (short)super.uncheckedPop(size-1);
    }
    throw new NoSuchElementException();
  }
  @Override
  void uncheckedCopyInto(Object[] arr,int size)
  {
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
  @Override
  public long pollLong()
  {
    final int size;
    if((size=this.size)!=0)
    {
      ++modCount;
      return (long)(super.uncheckedPop(size-1));
    }
    return Long.MIN_VALUE;
  }
  @Override
  void uncheckedCopyInto(long[] arr,int size)
  {
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
   @Override
   boolean uncheckedremoveVal(int size,int val)
   {
     final var arr=this.arr;
     int index;
     for(index=--size;val!=(arr[index]);--index)
     {
       if(index==0)
       {
         return false;
       }
     }
     ++modCount;
     ArrSeqUtil.eraseIndexHelper(arr,index,size);
     this.size=size;
     return true;
   }
  @Override
  public int pollInt()
  {
    final int size;
    if((size=this.size)!=0)
    {
      ++modCount;
      return (int)(super.uncheckedPop(size-1));
    }
    return Integer.MIN_VALUE;
  }
  @Override
  void uncheckedCopyInto(int[] arr,int size)
  {
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
  @Override
  public short pollShort()
  {
    final int size;
    if((size=this.size)!=0)
    {
      ++modCount;
      return (short)(super.uncheckedPop(size-1));
    }
    return Short.MIN_VALUE;
  }
  @Override
  void uncheckedCopyInto(short[] arr,int size)
  {
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
  @Override
  public double pollDouble()
  {
    final int size;
    if((size=this.size)!=0)
    {
      ++modCount;
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
      ++modCount;
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
