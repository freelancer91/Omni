package omni.impl.seq.arr.ofshort;
import omni.api.OmniList;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import omni.util.ArrCopy;
import omni.util.SortUtil;
import omni.impl.seq.arr.ArrSeqUtil;
import omni.function.ShortConsumer;
import omni.function.ShortUnaryOperator;
import java.util.Comparator;
import java.util.function.UnaryOperator;
import omni.function.ShortComparator;
import omni.impl.CheckedCollection;
public class CheckedList extends AbstractSeq.Checked implements OmniList.OfShort
{
  CheckedList()
  {
    super();
  }
  CheckedList(int capacity)
  {
    super(capacity);
  }
  CheckedList(int size,short[] arr)
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
    return new CheckedList(size,arr);
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
  public OmniListIterator.OfShort listIterator()
  {
    //TODO
    return null;
  }
  @Override
  public OmniListIterator.OfShort listIterator(int index)
  {
    //TODO
    return null;
  }
  @Override
  public OmniList.OfShort subList(int fromIndex,int toIndex)
  {
    //TODO
    return null;
  }
  @Override
  public void sort(ShortComparator sorter)
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
  public void replaceAll(ShortUnaryOperator operator)
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
  public void put(int index,short val)
  {
    CheckedCollection.checkLo(index);
    CheckedCollection.checkReadHi(index,this.size);
    arr[index]=val;
  }
  @Override
  void uncheckedCopyInto(Short[] arr,int size)
  {
    ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
  }
  @Override
  void uncheckedForEach(int size,ShortConsumer action)
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
  public short getShort(int index)
  {
    CheckedCollection.checkLo(index);
    CheckedCollection.checkReadHi(index,this.size);
    return (short)arr[index];
  }
  @Override
  public short set(int index,short val)
  {
    CheckedCollection.checkLo(index);
    CheckedCollection.checkReadHi(index,this.size);
    final short[] arr;
    final var oldVal=(short)(arr=this.arr)[index];
    arr[index]=val;
    return oldVal;
  }
  @Override
  public void replaceAll(UnaryOperator<Short> operator)
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
  public void sort(Comparator<? super Short> sorter)
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
  void uncheckedCopyInto(long[] arr,int size)
  {
    ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
  }
  @Override
  boolean uncheckedremoveVal(int size,int val)
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
    ++modCount;
    ArrSeqUtil.eraseIndexHelper(arr,index,size);
    this.size=size;
    return true;
  }
  @Override
  void uncheckedCopyInto(int[] arr,int size)
  {
    ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
  }
  @Override
  void uncheckedCopyInto(short[] arr,int size)
  {
    ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
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
