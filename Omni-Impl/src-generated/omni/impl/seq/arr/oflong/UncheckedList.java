package omni.impl.seq.arr.oflong;
import omni.api.OmniList;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import omni.util.ArrCopy;
import omni.util.SortUtil;
import omni.impl.seq.arr.ArrSeqUtil;
import java.util.function.LongConsumer;
import java.util.function.LongUnaryOperator;
import java.util.Comparator;
import java.util.function.UnaryOperator;
import omni.function.LongComparator;
public class UncheckedList extends AbstractSeq.Unchecked implements OmniList.OfLong
{
  UncheckedList()
  {
    super();
  }
  UncheckedList(int capacity)
  {
    super(capacity);
  }
  UncheckedList(int size,long[] arr)
  {
    super(size,arr);
  }
  @Override
  public Object clone()
  {
    final long[] arr;
    final int size;
    if((size=this.size)!=0)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr=new long[size],0,size);
    }
    else
    {
      arr=null;
    }
    return new UncheckedList(size,arr);
  }
  @Override
  public boolean equals(Object val)
  {
    //TODO
    return false;
  }
  @Override
  public OmniIterator.OfLong iterator()
  {
    //TODO
    return null;
  }
  @Override
  public OmniListIterator.OfLong listIterator()
  {
    //TODO
    return null;
  }
  @Override
  public OmniListIterator.OfLong listIterator(int index)
  {
    //TODO
    return null;
  }
  @Override
  public OmniList.OfLong subList(int fromIndex,int toIndex)
  {
    //TODO
    return null;
  }
  @Override
  public void sort(LongComparator sorter)
  {
    final int size;
    if((size=this.size)>1)
    {
      SortUtil.uncheckedsort(arr,0,size-1,sorter);
    }
  }
  @Override
  public void replaceAll(LongUnaryOperator operator)
  {
    final int size;
    if((size=this.size)!=0)
    {
      ArrSeqUtil.uncheckedReplaceAll(arr,0,size,operator);
    }
  }
  @Override
  public void put(int index,long val)
  {
    arr[index]=val;
  }
  @Override
  void uncheckedCopyInto(Long[] arr,int size)
  {
    ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
  }
  @Override
  void uncheckedForEach(int size,LongConsumer action)
  {
    ArrSeqUtil.uncheckedForwardForEach(arr,0,size,action);
  }
  @Override
  int uncheckedHashCode(int size)
  {
    return ArrSeqUtil.forwardHashCode(arr,0,size);
  }
  @Override
  void uncheckedToString(int size,StringBuilder builder)
  {
    ArrSeqUtil.forwardToString(arr,0,size,builder);
  }
  @Override
  public void sort()
  {
    final int size;
    if((size=this.size)>1)
    {
      SortUtil.uncheckedsort(arr,0,size-1);
    }
  }
  @Override
  public void reverseSort()
  {
    final int size;
    if((size=this.size)>1)
    {
      SortUtil.uncheckedreverseSort(arr,0,size-1);
    }
  }
  @Override
  public void replaceAll(UnaryOperator<Long> operator)
  {
    final int size;
    if((size=this.size)!=0)
    {
      ArrSeqUtil.uncheckedReplaceAll(arr,0,size,operator::apply);
    }
  }
  @Override
  public void sort(Comparator<? super Long> sorter)
  {
    final int size;
    if((size=this.size)>1)
    {
      SortUtil.uncheckedsort(arr,0,size-1,sorter::compare);
    }
  }
  @Override
  void uncheckedCopyInto(long[] arr,int size)
  {
    ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
  }
  @Override
  boolean uncheckedremoveVal(int size,long val)
  {
    final var arr=this.arr;
    int index;
    for(index=0,--size;val!=(arr[index]);++index)
    {
      if(index==size)
      {
        return false;
      }
    }
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
  @Override
  void uncheckedCopyInto(float[] arr,int size)
  {
    ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
  }
}
