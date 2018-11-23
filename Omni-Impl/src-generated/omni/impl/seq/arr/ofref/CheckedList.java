package omni.impl.seq.arr.ofref;
import omni.api.OmniList;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import omni.util.ArrCopy;
import omni.util.SortUtil;
import omni.impl.seq.arr.ArrSeqUtil;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.Comparator;
import java.util.function.UnaryOperator;
import omni.impl.CheckedCollection;
public class CheckedList<E> extends AbstractSeq.Checked<E> implements OmniList.OfRef<E>
{
  CheckedList()
  {
    super();
  }
  CheckedList(int capacity)
  {
    super(capacity);
  }
  CheckedList(int size,Object[] arr)
  {
    super(size,arr);
  }
  @Override
  public Object clone()
  {
    final Object[] arr;
    final int size;
    if((size=this.size)!=0)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr=new Object[size],0,size);
    }
    else
    {
      arr=null;
    }
    return new CheckedList<E>(size,arr);
  }
  @Override
  public boolean equals(Object val)
  {
    //TODO
    return false;
  }
  @Override
  public OmniIterator.OfRef<E> iterator()
  {
    //TODO
    return null;
  }
  @Override
  public OmniListIterator.OfRef<E> listIterator()
  {
    //TODO
    return null;
  }
  @Override
  public OmniListIterator.OfRef<E> listIterator(int index)
  {
    //TODO
    return null;
  }
  @Override
  public OmniList.OfRef<E> subList(int fromIndex,int toIndex)
  {
    //TODO
    return null;
  }
  @Override
  public void sort(Comparator<? super E> sorter)
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
  public void replaceAll(UnaryOperator<E> operator)
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
  public void put(int index,E val)
  {
    CheckedCollection.checkLo(index);
    CheckedCollection.checkReadHi(index,this.size);
    arr[index]=val;
  }
  @Override
  void uncheckedCopyInto(Object[] arr,int size)
  {
    ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
  }
  @Override
  void uncheckedForEach(int size,Consumer<? super E> action)
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
      final int modCount=this.modCount;
      try
      {
        SortUtil.uncheckedsort(arr,0,size-1);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
      this.modCount=modCount+1;
    }
  }
  @Override
  public void reverseSort()
  {
    final int size;
    if((size=this.size)>1)
    {
      final int modCount=this.modCount;
      try
      {
        SortUtil.uncheckedreverseSort(arr,0,size-1);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
      this.modCount=modCount+1;
    }
  }
  @SuppressWarnings("unchecked")
  @Override
  public E get(int index)
  {
    CheckedCollection.checkLo(index);
    CheckedCollection.checkReadHi(index,this.size);
    return (E)arr[index];
  }
  @Override
  public E set(int index,E val)
  {
    CheckedCollection.checkLo(index);
    CheckedCollection.checkReadHi(index,this.size);
    final Object[] arr;
    @SuppressWarnings("unchecked")
    final var oldVal=(E)(arr=this.arr)[index];
    arr[index]=val;
    return oldVal;
  }
  @Override
  public E remove(int index)
  {
    CheckedCollection.checkLo(index);
    int size;
    CheckedCollection.checkReadHi(index,size=this.size);
    final Object[] arr;
    @SuppressWarnings("unchecked")
    final var removed=(E)(arr=this.arr)[index];
    ArrSeqUtil.eraseIndexHelper(arr,index,--size);
    this.size=size;
    return removed;
  }
  @Override
  boolean uncheckedremoveVal(int size,Predicate<Object> pred)
  {
    final var arr=this.arr;
    int index;
    for(index=0,--size;!pred.test(arr[index]);++index)
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
  boolean uncheckedremoveValNonNull(int size,Object nonNull){
    final var arr=this.arr;
    int modCount=this.modCount;
    int index;
    try
    {
      for(index=0,--size;!nonNull.equals(arr[index]);++index){
        if(index==size){ return false; }
      }
    }
    finally
    {
      CheckedCollection.checkModCount(modCount,this.modCount);
    }
    ++modCount;
    ArrSeqUtil.eraseIndexHelper(arr,index,size);
    this.size=size;
    return true;
  }
  @Override
  protected int uncheckedlastIndexOfNonNull(int size,Object nonNull){
    final int modCount=this.modCount;
    try
    {
      return ArrSeqUtil.uncheckedlastIndexOf(arr,size,nonNull::equals);
    }
    finally
    {
      CheckedCollection.checkModCount(modCount,this.modCount);
    }
  }
}
