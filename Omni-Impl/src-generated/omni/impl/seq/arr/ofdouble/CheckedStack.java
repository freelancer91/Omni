package omni.impl.seq.arr.ofdouble;
import omni.api.OmniStack;
import omni.api.OmniIterator;
import omni.util.ArrCopy;
import omni.impl.seq.arr.ArrSeqUtil;
import java.util.function.DoubleConsumer;
import java.util.NoSuchElementException;
import omni.impl.CheckedCollection;
public class CheckedStack extends AbstractSeq.Checked implements OmniStack.OfDouble
{
  CheckedStack()
  {
    super();
  }
  CheckedStack(int capacity)
  {
    super(capacity);
  }
  private CheckedStack(int size,double[] arr)
  {
    super(size,arr);
  }
  @Override
  public Object clone()
  {
    final double[] arr;
    final int size;
    if((size=this.size)!=0)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr=new double[size],0,size);
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
  public OmniIterator.OfDouble iterator()
  {
    //TODO
    return null;
  }
  @Override
  public Double poll()
  {
    final int size;
    if((size=this.size)!=0)
    {
      ++modCount;
      return (Double)(super.uncheckedPop(size-1));
    }
    return null;
  }
  @Override
  public Double pop()
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
  void uncheckedCopyInto(Double[] arr,int size)
  {
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
  }
  @Override
  void uncheckedForEach(int size,DoubleConsumer action)
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
  public void push(Double val)
  {
    ++modCount;
    super.push((double)val);
  }
  @Override
  public double popDouble()
  {
    final int size;
    if((size=this.size)!=0)
    {
      ++modCount;
      return (double)super.uncheckedPop(size-1);
    }
    throw new NoSuchElementException();
  }
  @Override
  void uncheckedCopyInto(Object[] arr,int size)
  {
    ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
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
     ++modCount;
     ArrSeqUtil.eraseIndexHelper(arr,index,size);
     this.size=size;
     return true;
   }
   @Override
   boolean uncheckedremoveValBits(int size,long bits)
   {
     final var arr=this.arr;
     int index;
     for(index=--size;bits!=Double.doubleToRawLongBits(arr[index]);--index)
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
   boolean uncheckedremoveValNaN(int size)
   {
     final var arr=this.arr;
     int index;
     for(index=--size;!Double.isNaN(arr[index]);--index)
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
}
