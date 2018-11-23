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
public class UncheckedList<E> extends AbstractSeq.Unchecked<E> implements OmniList.OfRef<E>
{
  UncheckedList()
  {
    super();
  }
  UncheckedList(int capacity)
  {
    super(capacity);
  }
  UncheckedList(int size,Object[] arr)
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
    return new UncheckedList<E>(size,arr);
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
      SortUtil.uncheckedsort(arr,0,size-1,sorter);
    }
  }
  @Override
  public void replaceAll(UnaryOperator<E> operator)
  {
    final int size;
    if((size=this.size)!=0)
    {
      ArrSeqUtil.uncheckedReplaceAll(arr,0,size,operator);
    }
  }
  @Override
  public void put(int index,E val)
  {
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
  public E remove(int index)
  {
    final Object[] arr;
    @SuppressWarnings("unchecked")
    final var removed=(E)(arr=this.arr)[index];
    ArrSeqUtil.eraseIndexHelper(arr,index,--size);
    return removed;
  }
  @SuppressWarnings("unchecked")
  @Override
  public E get(int index)
  {
    return (E)arr[index];
  }
  @Override
  public E set(int index,E val)
  {
    final Object[] arr;
    @SuppressWarnings("unchecked")
    final var oldVal=(E)(arr=this.arr)[index];
    arr[index]=val;
    return oldVal;
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
    ArrSeqUtil.eraseIndexHelper(arr,index,size);
    this.size=size;
    return true;
  }
  @Override
  boolean uncheckedremoveValNonNull(int size,Object nonNull)
  {
    final var arr=this.arr;
    int index;
    for(index=0,--size;!nonNull.equals(arr[index]);++index)
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
}
