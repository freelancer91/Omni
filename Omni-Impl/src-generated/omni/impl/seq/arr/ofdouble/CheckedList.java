package omni.impl.seq.arr.ofdouble;
import omni.api.OmniList;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import omni.util.ArrCopy;
import omni.util.SortUtil;
import omni.impl.seq.arr.ArrSeqUtil;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleUnaryOperator;
import java.util.Comparator;
import java.util.function.UnaryOperator;
import omni.function.DoubleComparator;
import omni.impl.CheckedCollection;
public class CheckedList extends AbstractSeq.Checked implements OmniList.OfDouble
{
  CheckedList()
  {
    super();
  }
  CheckedList(int capacity)
  {
    super(capacity);
  }
  CheckedList(int size,double[] arr)
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
    return new CheckedList(size,arr);
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
  public OmniListIterator.OfDouble listIterator()
  {
    //TODO
    return null;
  }
  @Override
  public OmniListIterator.OfDouble listIterator(int index)
  {
    //TODO
    return null;
  }
  @Override
  public OmniList.OfDouble subList(int fromIndex,int toIndex)
  {
    //TODO
    return null;
  }
  @Override
  public void sort(DoubleComparator sorter)
  {
    final int size;
    if((size=this.size)>1)
    {
      final int modCount=this.modCount;
      try
      {
        SortUtil.uncheckedsort(arr,0,size-1,sorter);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
      this.modCount=modCount+1;
    }
  }
  @Override
  public void replaceAll(DoubleUnaryOperator operator)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final int modCount=this.modCount;
      try
      {
        ArrSeqUtil.uncheckedReplaceAll(arr,0,size,operator);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
      this.modCount=modCount+1;
    }
  }
  @Override
  public void put(int index,double val)
  {
    CheckedCollection.checkLo(index);
    CheckedCollection.checkReadHi(index,this.size);
    arr[index]=val;
  }
  @Override
  void uncheckedCopyInto(Double[] arr,int size)
  {
    ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
  }
  @Override
  void uncheckedForEach(int size,DoubleConsumer action)
  {
    final int modCount=this.modCount;
    try
    {
      ArrSeqUtil.uncheckedForwardForEach(arr,0,size,action);
    }
    finally
    {
      CheckedCollection.checkModCount(modCount,this.modCount);
    }
  }
  @Override
  int uncheckedHashCode(int size)
  {
    final int modCount=this.modCount;
    try
    {
      return ArrSeqUtil.forwardHashCode(arr,0,size);
    }
    finally
    {
      CheckedCollection.checkModCount(modCount,this.modCount);
    }
  }
  @Override
  void uncheckedToString(int size,StringBuilder builder)
  {
    final int modCount=this.modCount;
    try
    {
      ArrSeqUtil.forwardToString(arr,0,size,builder);
    }
    finally
    {
      CheckedCollection.checkModCount(modCount,this.modCount);
    }
  }
  @Override
  public void sort()
  {
    final int size;
    if((size=this.size)>1)
    {
      {
        SortUtil.uncheckedsort(arr,0,size-1);
      }
      ++this.modCount;
    }
  }
  @Override
  public void reverseSort()
  {
    final int size;
    if((size=this.size)>1)
    {
      {
        SortUtil.uncheckedreverseSort(arr,0,size-1);
      }
      ++this.modCount;
    }
  }
  @Override
  public double getDouble(int index)
  {
    CheckedCollection.checkLo(index);
    CheckedCollection.checkReadHi(index,this.size);
    return (double)arr[index];
  }
  @Override
  public double set(int index,double val)
  {
    CheckedCollection.checkLo(index);
    CheckedCollection.checkReadHi(index,this.size);
    final double[] arr;
    final var oldVal=(double)(arr=this.arr)[index];
    arr[index]=val;
    return oldVal;
  }
  @Override
  public void replaceAll(UnaryOperator<Double> operator)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final int modCount=this.modCount;
      try
      {
        ArrSeqUtil.uncheckedReplaceAll(arr,0,size,operator::apply);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
      this.modCount=modCount+1;
    }
  }
  @Override
  public void sort(Comparator<? super Double> sorter)
  {
    final int size;
    if((size=this.size)>1)
    {
      final int modCount=this.modCount;
      try
      {
        SortUtil.uncheckedsort(arr,0,size-1,sorter::compare);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
      this.modCount=modCount+1;
    }
  }
  @Override
  boolean uncheckedremoveVal0(int size)
  {
    final var arr=this.arr;
    int index;
    for(index=0,--size;0!=(arr[index]);++index)
    {
      if(index==size)
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
    for(index=0,--size;bits!=Double.doubleToRawLongBits(arr[index]);++index)
    {
      if(index==size)
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
    for(index=0,--size;!Double.isNaN(arr[index]);++index)
    {
      if(index==size)
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
  void uncheckedCopyInto(Object[] arr,int size)
  {
    ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
  }
  @Override
  void uncheckedCopyInto(double[] arr,int size)
  {
    ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
  }
}
