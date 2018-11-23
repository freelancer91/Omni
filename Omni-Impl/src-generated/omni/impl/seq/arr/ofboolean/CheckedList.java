package omni.impl.seq.arr.ofboolean;
import omni.api.OmniList;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import omni.util.ArrCopy;
import omni.util.SortUtil;
import omni.impl.seq.arr.ArrSeqUtil;
import omni.function.BooleanConsumer;
import omni.function.BooleanPredicate;
import java.util.Comparator;
import java.util.function.UnaryOperator;
import omni.function.BooleanComparator;
import omni.impl.CheckedCollection;
public class CheckedList extends AbstractSeq.Checked implements OmniList.OfBoolean
{
  CheckedList()
  {
    super();
  }
  CheckedList(int capacity)
  {
    super(capacity);
  }
  CheckedList(int size,boolean[] arr)
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
    return new CheckedList(size,arr);
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
  public OmniListIterator.OfBoolean listIterator()
  {
    //TODO
    return null;
  }
  @Override
  public OmniListIterator.OfBoolean listIterator(int index)
  {
    //TODO
    return null;
  }
  @Override
  public OmniList.OfBoolean subList(int fromIndex,int toIndex)
  {
    //TODO
    return null;
  }
  @Override
  public void sort(BooleanComparator sorter)
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
  public void replaceAll(BooleanPredicate operator)
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
  public void put(int index,boolean val)
  {
    CheckedCollection.checkLo(index);
    CheckedCollection.checkReadHi(index,this.size);
    arr[index]=val;
  }
  @Override
  void uncheckedCopyInto(Boolean[] arr,int size)
  {
    ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
  }
  @Override
  void uncheckedForEach(int size,BooleanConsumer action)
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
  public boolean getBoolean(int index)
  {
    CheckedCollection.checkLo(index);
    CheckedCollection.checkReadHi(index,this.size);
    return (boolean)arr[index];
  }
  @Override
  public boolean set(int index,boolean val)
  {
    CheckedCollection.checkLo(index);
    CheckedCollection.checkReadHi(index,this.size);
    final boolean[] arr;
    final var oldVal=(boolean)(arr=this.arr)[index];
    arr[index]=val;
    return oldVal;
  }
  @Override
  public void replaceAll(UnaryOperator<Boolean> operator)
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
  public void sort(Comparator<? super Boolean> sorter)
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
  boolean uncheckedremoveVal(int size,boolean val)
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
  void uncheckedCopyInto(byte[] arr,int size)
  {
    ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
  }
  @Override
  void uncheckedCopyInto(boolean[] arr,int size)
  {
    ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
  }
  @Override
  void uncheckedCopyInto(char[] arr,int size)
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
