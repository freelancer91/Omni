package omni.impl.seq.arr.ofboolean;
import omni.api.OmniStack;
import omni.api.OmniIterator;
import omni.util.ArrCopy;
import omni.impl.seq.arr.ArrSeqUtil;
import omni.function.BooleanConsumer;
import omni.util.TypeUtil;
import java.util.NoSuchElementException;
import omni.impl.CheckedCollection;
public class CheckedStack extends AbstractSeq.Checked implements OmniStack.OfBoolean
{
  CheckedStack()
  {
    super();
  }
  CheckedStack(int capacity)
  {
    super(capacity);
  }
  private CheckedStack(int size,boolean[] arr)
  {
    super(size,arr);
  }
  @Override
  public Object clone()
  {
    final boolean[] arr;
    final int size;
    if((size=this.size)!=0)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr=new boolean[size],0,size);
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
  public OmniIterator.OfBoolean iterator()
  {
    //TODO
    return null;
  }
  @Override
  public Boolean poll()
  {
    final int size;
    if((size=this.size)!=0)
    {
      ++modCount;
      return (Boolean)(super.uncheckedPop(size-1));
    }
    return null;
  }
  @Override
  public Boolean pop()
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
  void uncheckedCopyInto(Boolean[] arr,int size)
  {
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
  @Override
  void uncheckedForEach(int size,BooleanConsumer action)
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
  public void push(Boolean val)
  {
    ++modCount;
    super.push((boolean)val);
  }
  @Override
  public boolean popBoolean()
  {
    final int size;
    if((size=this.size)!=0)
    {
      ++modCount;
      return (boolean)super.uncheckedPop(size-1);
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
      return TypeUtil.castToLong(super.uncheckedPop(size-1));
    }
    return Long.MIN_VALUE;
  }
  @Override
  void uncheckedCopyInto(long[] arr,int size)
  {
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
   @Override
   boolean uncheckedremoveVal(int size,boolean val)
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
      return (int)TypeUtil.castToByte(super.uncheckedPop(size-1));
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
      return (short)TypeUtil.castToByte(super.uncheckedPop(size-1));
    }
    return Short.MIN_VALUE;
  }
  @Override
  void uncheckedCopyInto(short[] arr,int size)
  {
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
  @Override
  public byte pollByte()
  {
    final int size;
    if((size=this.size)!=0)
    {
      ++modCount;
      return TypeUtil.castToByte(super.uncheckedPop(size-1));
    }
    return Byte.MIN_VALUE;
  }
  @Override
  void uncheckedCopyInto(byte[] arr,int size)
  {
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
  @Override
  public boolean pollBoolean()
  {
    final int size;
    if((size=this.size)!=0)
    {
      ++modCount;
      return (boolean)(super.uncheckedPop(size-1));
    }
    return false;
  }
  @Override
  void uncheckedCopyInto(boolean[] arr,int size)
  {
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
  @Override
  public char pollChar()
  {
    final int size;
    if((size=this.size)!=0)
    {
      ++modCount;
      return TypeUtil.castToChar(super.uncheckedPop(size-1));
    }
    return Character.MIN_VALUE;
  }
  @Override
  void uncheckedCopyInto(char[] arr,int size)
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
      return TypeUtil.castToDouble(super.uncheckedPop(size-1));
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
      return TypeUtil.castToFloat(super.uncheckedPop(size-1));
    }
    return Float.NaN;
  }
  @Override
  void uncheckedCopyInto(float[] arr,int size)
  {
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
}
