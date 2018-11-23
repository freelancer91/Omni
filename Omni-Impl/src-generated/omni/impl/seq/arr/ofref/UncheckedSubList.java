package omni.impl.seq.arr.ofref;
import omni.api.OmniList;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import omni.util.ArrCopy;
import omni.util.SortUtil;
import omni.util.OmniArray;
import omni.impl.seq.arr.ArrSeqUtil;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.Comparator;
import java.util.function.UnaryOperator;
import java.util.function.IntFunction;
import omni.util.OmniPred;
class UncheckedSubList<E> extends AbstractSeq.Unchecked.AbstractSubList<E> implements OmniList.OfRef<E>
{
  //TODO user macros to clean up the template
  UncheckedSubList(AbstractSeq.Unchecked<E> root,AbstractSeq.Unchecked.AbstractSubList<E> parent,int rootOffset,int size)
  {
    super(root,parent,rootOffset,size);
  }
  UncheckedSubList(AbstractSeq.Unchecked<E> root,int rootOffset,int size)
  {
    super(root,rootOffset,size);
  }
  @Override
  public Object clone()
  {
    final Object[] arr;
    final int size;
    if((size=this.size)!=0)
    {
      ArrCopy.uncheckedCopy(root.arr,rootOffset,arr=new Object[size],0,size);
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
    return new UncheckedSubList<E>(root,this,this.rootOffset+fromIndex,toIndex-fromIndex);
  }
  @Override
  public void sort(Comparator<? super E> sorter)
  {
    final int size;
    if((size=this.size)>1)
    {
      final int rootOffset;
      SortUtil.uncheckedsort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter);
    }
  }
  @Override
  public void replaceAll(UnaryOperator<E> operator)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final int rootOffset;
      ArrSeqUtil.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);
    }
  }
  @Override
  public void put(int index,E val)
  {
    root.arr[index+rootOffset]=val;
  }
  @Override
  public E set(int index,E val)
  {
    final Object[] arr;
    @SuppressWarnings("unchecked")
    final var oldVal=(E)(arr=root.arr)[index+=rootOffset];
    arr[index]=val;
    return oldVal;
  }
  @SuppressWarnings("unchecked")
  @Override
  public E get(int index)
  {
    return (E)root.arr[index+rootOffset];
  }
  @Override
  public void sort()
  {
    final int size;
    if((size=this.size)>1)
    {
      final int rootOffset;
      SortUtil.uncheckedsort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
    }
  }
  @Override
  public void reverseSort()
  {
    final int size;
    if((size=this.size)>1)
    {
      final int rootOffset;
      SortUtil.uncheckedreverseSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
    }
  }
  @Override
  public E remove(int index)
  {
    final AbstractSeq.Unchecked<E> root;
    final Object[] arr;
    @SuppressWarnings("unchecked")
    final var removed=(E)(arr=(root=this.root).arr)[index+=rootOffset];
    ArrSeqUtil.eraseIndexHelper(arr,index,--root.size);
    bubbleUpDecrementSize(parent);
    --size;
    return removed;
  }
  @Override
  public String toString()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final StringBuilder builder;
      final int rootOffset;
      ArrSeqUtil.forwardToString(root.arr,rootOffset=this.rootOffset,rootOffset+size,builder=new StringBuilder("["));
      return builder.append(']').toString();
    }
    return "[]";
  }
  @Override
  public int hashCode()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final int rootOffset;
      return ArrSeqUtil.forwardHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size);
    }
    return 1;
  }
  @Override
  public void forEach(Consumer<? super E> action)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final int rootOffset;
      ArrSeqUtil.uncheckedForwardForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size,action);
    }
  }
  @Override
  public Object[] toArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final Object[] dst;
      ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new Object[size],0,size);
      return dst;
    }
    return OmniArray.OfRef.DEFAULT_ARR;
  }
  @Override
  public <T> T[] toArray(IntFunction<T[]> arrConstructor)
  {
    final int size;
    final T[] dst=arrConstructor.apply(size=this.size);
    if(size!=0)
    {
      ArrCopy.uncheckedCopy(root.arr,rootOffset,dst,0,size);
    }
    return dst;
  }
  @Override
  public <T> T[] toArray(T[] arr)
  {
    final int size;
    if((size=this.size)!=0)
    {
      ArrCopy.uncheckedCopy(root.arr,rootOffset,arr=OmniArray.uncheckedArrResize(size,arr),0,size);
    }
    else if(arr.length!=0)
    {
      arr[0]=null;
    }
    return arr;
  }
  private boolean uncheckedcontains(int size,Predicate<Object> pred)
  {
    final int rootOffset;
    return ArrSeqUtil.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size,pred);
  }
  private int uncheckedindexOf(int size,Predicate<Object> pred)
  {
    final int rootOffset;
    return ArrSeqUtil.uncheckedindexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,pred);
  }
  private boolean uncheckedremoveVal(int size,Predicate<Object> pred)
  {
    final AbstractSeq.Unchecked<E> root;
    final var arr=(root=this.root).arr;
    int index;
    final int bound=(index=this.rootOffset)+(--size);
    while(!pred.test(arr[index]))
    {
      if(++index==bound)
      {
        return false;
      }
    }
    ArrSeqUtil.eraseIndexHelper(arr,index,--root.size);
    bubbleUpDecrementSize(parent);
    this.size=size;
    return true;
  }
  @Override
  public boolean remove(Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      {
        return uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override
  public boolean contains(Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      {
        return uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override
  public int indexOf(Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      {
        return uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  @Override
  public boolean contains(boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public int indexOf(boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public boolean contains(byte val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(byte val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public int indexOf(byte val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public boolean contains(char val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  public boolean removeVal(char val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  public int indexOf(char val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  @Override
  public boolean contains(short val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(short val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override
  public int indexOf(short val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  @Override
  public boolean contains(int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      {
        return uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      {
        return uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override
  public int indexOf(int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      {
        return uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  public boolean contains(long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  public boolean removeVal(long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  public int indexOf(long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public boolean contains(float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  public boolean removeVal(float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  public int indexOf(float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public boolean contains(double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  public boolean removeVal(double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  public int indexOf(double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public boolean contains(Boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(Byte val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(Character val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(Short val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(Integer val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(Long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(Float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(Double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(Boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(Byte val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(Character val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(Short val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(Integer val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(Long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(Float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(Double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public int indexOf(Boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int indexOf(Byte val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int indexOf(Character val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int indexOf(Short val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int indexOf(Integer val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int indexOf(Long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int indexOf(Float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  public int indexOf(Double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @Override
  protected int uncheckedlastIndexOf(int size,Predicate<Object> pred)
  {
    final int rootOffset;
    return ArrSeqUtil.uncheckedlastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,pred);
  }
  @Override
  protected int uncheckedlastIndexOfNonNull(int size,Object nonNull)
  {
    final int rootOffset;
    return ArrSeqUtil.uncheckedlastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,nonNull::equals);
  }
}
