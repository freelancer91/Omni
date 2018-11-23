package omni.impl.seq.arr.ofdouble;
import omni.api.OmniList;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import omni.util.ArrCopy;
import omni.util.SortUtil;
import omni.util.OmniArray;
import omni.impl.seq.arr.ArrSeqUtil;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Consumer;
import java.util.Comparator;
import java.util.function.UnaryOperator;
import java.util.function.IntFunction;
import omni.function.DoubleComparator;
import omni.util.TypeUtil;
class UncheckedSubList extends AbstractSeq.Unchecked.AbstractSubList implements OmniList.OfDouble
{
  //TODO user macros to clean up the template
  UncheckedSubList(AbstractSeq.Unchecked root,AbstractSeq.Unchecked.AbstractSubList parent,int rootOffset,int size)
  {
    super(root,parent,rootOffset,size);
  }
  UncheckedSubList(AbstractSeq.Unchecked root,int rootOffset,int size)
  {
    super(root,rootOffset,size);
  }
  @Override
  public Object clone()
  {
    final double[] arr;
    final int size;
    if((size=this.size)!=0)
    {
      ArrCopy.uncheckedCopy(root.arr,rootOffset,arr=new double[size],0,size);
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
    return new UncheckedSubList(root,this,this.rootOffset+fromIndex,toIndex-fromIndex);
  }
  @Override
  public void sort(DoubleComparator sorter)
  {
    final int size;
    if((size=this.size)>1)
    {
      final int rootOffset;
      SortUtil.uncheckedsort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter);
    }
  }
  @Override
  public void replaceAll(DoubleUnaryOperator operator)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final int rootOffset;
      ArrSeqUtil.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);
    }
  }
  @Override
  public void put(int index,double val)
  {
    root.arr[index+rootOffset]=val;
  }
  @Override
  public double set(int index,double val)
  {
    final double[] arr;
    final var oldVal=(double)(arr=root.arr)[index+=rootOffset];
    arr[index]=val;
    return oldVal;
  }
  @Override
  public double getDouble(int index)
  {
    return (double)root.arr[index+rootOffset];
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
  public double removeDoubleAt(int index)
  {
    final AbstractSeq.Unchecked root;
    final double[] arr;
    final var removed=(double)(arr=(root=this.root).arr)[index+=rootOffset];
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
  public void forEach(DoubleConsumer action)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final int rootOffset;
      ArrSeqUtil.uncheckedForwardForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size,action);
    }
  }
  @Override
  public Double[] toArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final Double[] dst;
      ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new Double[size],0,size);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_BOXED_ARR;
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
  private boolean uncheckedcontainsBits(int size,long bits)
  {
    final int rootOffset;
    return ArrSeqUtil.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size,bits);
  }
  private int uncheckedindexOfBits(int size,long bits)
  {
    final int rootOffset;
    return ArrSeqUtil.uncheckedindexOfBits(root.arr,rootOffset=this.rootOffset,rootOffset+size,bits);
  }
  private boolean uncheckedcontainsNaN(int size)
  {
    final int rootOffset;
    return ArrSeqUtil.uncheckedcontainsNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size);
  }
  private int uncheckedindexOfNaN(int size)
  {
    final int rootOffset;
    return ArrSeqUtil.uncheckedindexOfNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size);
  }
  private boolean uncheckedcontains0(int size)
  {
    final int rootOffset;
    return ArrSeqUtil.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size);
  }
  private int uncheckedindexOf0(int size)
  {
    final int rootOffset;
    return ArrSeqUtil.uncheckedindexOf0(root.arr,rootOffset=this.rootOffset,rootOffset+size);
  }
  private boolean uncheckedcontains(int size,double val)
  {
    if(val==val)
    {
      return uncheckedcontainsBits(size,Double.doubleToRawLongBits(val));
    }
    return uncheckedcontainsNaN(size);
  }
  private boolean uncheckedremoveVal(int size,double val)
  {
    if(val==val)
    {
      return uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
    }
    return uncheckedremoveValNaN(size);
  }
  private int uncheckedindexOf(int size,double val)
  {
    if(val==val)
    {
      return uncheckedindexOfBits(size,Double.doubleToRawLongBits(val));
    }
    return uncheckedindexOfNaN(size);
  }
  private boolean uncheckedremoveValBits(int size,long bits)
  {
    final AbstractSeq.Unchecked root;
    final var arr=(root=this.root).arr;
    int index;
    final int bound=(index=this.rootOffset)+(--size);
    while(bits!=Double.doubleToRawLongBits(arr[index]))
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
  private boolean uncheckedremoveVal0(int size)
  {
    final AbstractSeq.Unchecked root;
    final var arr=(root=this.root).arr;
    int index;
    final int bound=(index=this.rootOffset)+(--size);
    while(0!=(arr[index]))
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
  private boolean uncheckedremoveValNaN(int size)
  {
    final AbstractSeq.Unchecked root;
    final var arr=(root=this.root).arr;
    int index;
    final int bound=(index=this.rootOffset)+(--size);
    while(!Double.isNaN(arr[index]))
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
      if(val instanceof Double)
      {
        return uncheckedremoveVal(size,(double)(val));
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
      if(val instanceof Double)
      {
        return uncheckedcontains(size,(double)(val));
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
      if(val instanceof Double)
      {
        return uncheckedindexOf(size,(double)(val));
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
      if(val)
      {
        return uncheckedcontainsBits(size,TypeUtil.DBL_TRUE_BITS);
      }
      return uncheckedcontains0(size);
    }
    return false;
  }
  @Override
  public boolean removeVal(boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val)
      {
        return uncheckedremoveValBits(size,TypeUtil.DBL_TRUE_BITS);
      }
      return uncheckedremoveVal0(size);
    }
    return false;
  }
  @Override
  public int indexOf(boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val)
      {
        return uncheckedindexOfBits(size,TypeUtil.DBL_TRUE_BITS);
      }
      return uncheckedindexOf0(size);
    }
    return -1;
  }
  @Override
  public boolean contains(int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=0)
      {
      }
      else
      {
        return uncheckedcontains0(size);
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
      if(val!=0)
      {
      }
      else
      {
        return uncheckedremoveVal0(size);
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
      if(val!=0)
      {
      }
      else
      {
        return uncheckedindexOf0(size);
      }
    }
    return -1;
  }
  public boolean contains(long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=0)
      {
        if(TypeUtil.checkCastToDouble(val))
        {
          return uncheckedcontainsBits(size,Double.doubleToRawLongBits(val));
        }
      }
      else
      {
         return uncheckedcontains0(size);
      }
    }
    return false;
  }
  public boolean removeVal(long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=0)
      {
        if(TypeUtil.checkCastToDouble(val))
        {
          return uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
        }
      }
      else
      {
         return uncheckedremoveVal0(size);
      }
    }
    return false;
  }
  public int indexOf(long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=0)
      {
        if(TypeUtil.checkCastToDouble(val))
        {
          return uncheckedindexOfBits(size,Double.doubleToRawLongBits(val));
        }
      }
      else
      {
         return uncheckedindexOf0(size);
      }
    }
    return -1;
  }
  public boolean contains(float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val==val)
      {
        return uncheckedcontainsBits(size,Double.doubleToRawLongBits(val));
      }
      return uncheckedcontainsNaN(size);  
    }
    return false;
  }
  public boolean removeVal(float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val==val)
      {
        return uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
      }
      return uncheckedremoveValNaN(size);  
    }
    return false;
  }
  public int indexOf(float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val==val)
      {
        return uncheckedindexOfBits(size,Double.doubleToRawLongBits(val));
      }
      return uncheckedindexOfNaN(size);  
    }
    return -1;
  }
  public boolean contains(double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedcontains(size,(val));
    }
    return false;
  }
  public boolean removeVal(double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedremoveVal(size,(val));
    }
    return false;
  }
  public int indexOf(double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedindexOf(size,(val));
    }
    return -1;
  }
  @Override
  protected int uncheckedlastIndexOf0(int size)
  {
    final int rootOffset;
    return ArrSeqUtil.uncheckedlastIndexOf0(root.arr,rootOffset=this.rootOffset,rootOffset+size);
  }
  @Override
  protected int uncheckedlastIndexOfBits(int size,long bits)
  {
    final int rootOffset;
    return ArrSeqUtil.uncheckedlastIndexOfBits(root.arr,rootOffset=this.rootOffset,rootOffset+size,bits);
  }
  @Override
  protected int uncheckedlastIndexOfNaN(int size)
  {
    final int rootOffset;
    return ArrSeqUtil.uncheckedlastIndexOfNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size);
  }
  @Override
  public void forEach(Consumer<? super Double> action)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final int rootOffset;
      ArrSeqUtil.uncheckedForwardForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size,action::accept);
    }
  }
  @Override
  public void sort(Comparator<? super Double> sorter)
  {
    final int size;
    if((size=this.size)>1)
    {
      final int rootOffset;
      SortUtil.uncheckedsort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter::compare);
    }
  }
  @Override
  public void replaceAll(UnaryOperator<Double> operator)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final int rootOffset;
      ArrSeqUtil.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator::apply);
    }
  }
  @Override
  public double[] toDoubleArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final double[] dst;
      ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new double[size],0,size);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
}
