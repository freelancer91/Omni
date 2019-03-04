package omni.impl.seq;
import omni.api.OmniCollection;
import omni.util.OmniArray;
import omni.api.OmniList;
import omni.api.OmniStack;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.function.DoublePredicate;
import java.util.function.DoubleConsumer;
import java.util.Comparator;
import omni.function.DoubleComparator;
import omni.util.ArrCopy;
import omni.util.DoubleSortUtil;
import omni.impl.CheckedCollection;
import omni.util.TypeUtil;
import java.util.NoSuchElementException;
import omni.util.HashUtil;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import java.util.function.IntFunction;
public abstract class DoubleArrSeq implements OmniCollection.OfDouble
{
  transient int size;
  transient double[] arr;
  private DoubleArrSeq()
  {
    super();
  }
  private DoubleArrSeq(int initialCapacity)
  {
    super();
    switch(initialCapacity)
    { 
    default:
      this.arr=new double[initialCapacity];
      return;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfDouble.DEFAULT_ARR;
    case 0:
    }
  }
  private DoubleArrSeq(int size,double[] arr)
  {
    super();
    this.size=size;
    this.arr=arr;
  }
  @Override
  public int size()
  {
    return this.size;
  }
  @Override
  public boolean isEmpty()
  {
    return this.size==0;
  }
  @Override
  public void clear()
  {
    this.size=0;
  }
  public static class UncheckedListImpl extends DoubleArrSeq implements OmniList.OfDouble
  {
    private static int uncheckedAbsoluteIndexOfBits(double[] arr,int offset,int bound
    ,long val
    )
    {
      for(;;)
      {
        if(
        val==Double.doubleToRawLongBits(arr[offset])
        )
        {
          return offset;
        }
        if(++offset==bound)
        {
          return -1;
        }
      }
    }
    private static int uncheckedAbsoluteLastIndexOfBits(double[] arr,int offset,int bound
    ,long val
    )
    {
      for(;;)
      {
        if(
        val==Double.doubleToRawLongBits(arr[--bound])
        )
        {
          return bound;
        }
        if(offset==bound)
        {
          return -1;
        }
      }
    }
    /*
    private static int uncheckedRelativeIndexOfBits(double[] arr,int offset,int bound
    ,long val
    )
    {
      for(int i=offset;;)
      {
        if(
        val==Double.doubleToRawLongBits(arr[i])
        )
        {
          return i-offset;
        }
        if(++i==bound)
        {
          return -1;
        }
      }
    }
    private static int uncheckedRelativeLastIndexOfBits(double[] arr,int offset,int bound
    ,long val
    )
    {
      for(;;)
      {
        if(
        val==Double.doubleToRawLongBits(arr[--bound])
        )
        {
          return bound-offset;
        }
        if(offset==bound)
        {
          return -1;
        }
      }
    }
    */
    private static int uncheckedAbsoluteIndexOf0(double[] arr,int offset,int bound
    )
    {
      for(;;)
      {
        if(
        0==(arr[offset])
        )
        {
          return offset;
        }
        if(++offset==bound)
        {
          return -1;
        }
      }
    }
    private static int uncheckedAbsoluteLastIndexOf0(double[] arr,int offset,int bound
    )
    {
      for(;;)
      {
        if(
        0==(arr[--bound])
        )
        {
          return bound;
        }
        if(offset==bound)
        {
          return -1;
        }
      }
    }
    /*
    private static int uncheckedRelativeIndexOf0(double[] arr,int offset,int bound
    )
    {
      for(int i=offset;;)
      {
        if(
        0==(arr[i])
        )
        {
          return i-offset;
        }
        if(++i==bound)
        {
          return -1;
        }
      }
    }
    private static int uncheckedRelativeLastIndexOf0(double[] arr,int offset,int bound
    )
    {
      for(;;)
      {
        if(
        0==(arr[--bound])
        )
        {
          return bound-offset;
        }
        if(offset==bound)
        {
          return -1;
        }
      }
    }
    */
    private static int uncheckedAbsoluteIndexOfNaN(double[] arr,int offset,int bound
    )
    {
      for(;;)
      {
        if(
        Double.isNaN(arr[offset])
        )
        {
          return offset;
        }
        if(++offset==bound)
        {
          return -1;
        }
      }
    }
    private static int uncheckedAbsoluteLastIndexOfNaN(double[] arr,int offset,int bound
    )
    {
      for(;;)
      {
        if(
        Double.isNaN(arr[--bound])
        )
        {
          return bound;
        }
        if(offset==bound)
        {
          return -1;
        }
      }
    }
    /*
    private static int uncheckedRelativeIndexOfNaN(double[] arr,int offset,int bound
    )
    {
      for(int i=offset;;)
      {
        if(
        Double.isNaN(arr[i])
        )
        {
          return i-offset;
        }
        if(++i==bound)
        {
          return -1;
        }
      }
    }
    private static int uncheckedRelativeLastIndexOfNaN(double[] arr,int offset,int bound
    )
    {
      for(;;)
      {
        if(
        Double.isNaN(arr[--bound])
        )
        {
          return bound-offset;
        }
        if(offset==bound)
        {
          return -1;
        }
      }
    }
    */
    private boolean uncheckedRemoveValBits(int size
    ,long val
    )
    {
      final var arr=this.arr;
      for(int i=0;;)
      {
        if(
        val==Double.doubleToRawLongBits(arr[i])
        )
        {
          ArrCopy.semicheckedSelfCopy(arr,i+1,i,(--size)-i);
          this.size=size;
          return true;
        }
        if(++i==size)
        {
          return false;
        }
      }
    }
    private boolean uncheckedRemoveVal0(int size
    )
    {
      final var arr=this.arr;
      for(int i=0;;)
      {
        if(
        0==(arr[i])
        )
        {
          ArrCopy.semicheckedSelfCopy(arr,i+1,i,(--size)-i);
          this.size=size;
          return true;
        }
        if(++i==size)
        {
          return false;
        }
      }
    }
    private boolean uncheckedRemoveValNaN(int size
    )
    {
      final var arr=this.arr;
      for(int i=0;;)
      {
        if(
        Double.isNaN(arr[i])
        )
        {
          ArrCopy.semicheckedSelfCopy(arr,i+1,i,(--size)-i);
          this.size=size;
          return true;
        }
        if(++i==size)
        {
          return false;
        }
      }
    }
    static void uncheckedToString(double[] arr,int begin,int end,StringBuilder builder)
    {
      for(builder.append(arr[begin]);begin!=end;builder.append(',').append(' ').append(arr[++begin])){}
    }
    static  void uncheckedForEach(double[] arr,int begin,int end,DoubleConsumer action)
    {
      for(;;++begin)
      {
        action.accept((double)arr[begin]);
        if(begin==end)
        {
          return;
        }
      }
    }
    static int uncheckedHashCode(double[] arr,int begin,int end)
    {
      int hash=31+HashUtil.hashDouble(arr[begin]);
      while(begin!=end)
      {
        hash=hash*31+HashUtil.hashDouble(arr[++begin]);
      }
      return hash;
    }
    public UncheckedListImpl()
    {
      super();
    }
    public UncheckedListImpl(int initialCapacity)
    {
      super(initialCapacity);
    }
    private UncheckedListImpl(int size,double[] arr)
    {
      super(size,arr);
    }
    @Override
    public String toString()
    {
      int size;
      if((size=this.size)!=0)
      {
        if(size>(Integer.MAX_VALUE/5))
        {
          throw new OutOfMemoryError();
        }
        final StringBuilder builder;
        {
          uncheckedToString(arr,0,size-1,builder=new StringBuilder("["));
        }
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
        {
          return uncheckedHashCode(arr,0,size-1);
        }
      }
      return 1;
    }
    @Override
    public <T> T[] toArray(T[] dst)
    {
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,dst=OmniArray.uncheckedArrResize(size,dst),0,size);
      }
      else if(dst.length!=0)
      {
        dst[0]=null;
      }
      return dst;
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor)
    {
      final int size;
      T[] dst;
        dst=arrConstructor.apply(size=this.size);
      if(size!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,dst,0,size);
      }
      return dst;
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO
      return false;
    }
    @Override
    public Object clone()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final double[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new double[size],0,size);
        return new UncheckedListImpl(size,copy);
      }
      return new UncheckedListImpl();
    }
    @Override
    public void forEach(DoubleConsumer action)
    {
      final int size;
      if((size=this.size)!=0)
      {
        {
          uncheckedForEach(arr,0,size-1,action);
        }
      }
    }
    @Override
    public void forEach(Consumer<? super Double> action)
    {
      final int size;
      if((size=this.size)!=0)
      {
        {
          uncheckedForEach(arr,0,size-1,action::accept);
        }
      }
    }
    @Override
    public boolean removeIf(DoublePredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Double> filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(double val)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(Double val)
    {
      //TODO
      return false;
    }
    @Override
    public
    boolean
    removeVal(boolean val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val)
        {
          return uncheckedRemoveValBits(size,TypeUtil.DBL_TRUE_BITS);
        }
        return uncheckedRemoveVal0(size);
      }
      return false;
    }
    @Override
    public
    boolean
    removeVal(int val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val!=0)
        {
          {
            return uncheckedRemoveValBits(size,Double.doubleToRawLongBits(val));
          }
        }
        else
        {
          return uncheckedRemoveVal0(size);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    removeVal(long val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val!=0)
        {
          if(TypeUtil.checkCastToDouble(val))
          {
            return uncheckedRemoveValBits(size,Double.doubleToRawLongBits(val));
          }
        }
        else
        {
          return uncheckedRemoveVal0(size);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    removeVal(float val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val==val)
        {
          return uncheckedRemoveValBits(size,Double.doubleToRawLongBits(val));
        }
        return uncheckedRemoveValNaN(size);
      }
      return false;
    }
    @Override
    public
    boolean
    removeVal(double val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val==val)
        {
          return uncheckedRemoveValBits(size,Double.doubleToRawLongBits(val));
        }
        {
          return uncheckedRemoveValNaN(size);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    remove
    (Object val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Double)
        {
          final double v;
          if((v=(double)val)==v)
          {
            return uncheckedRemoveValBits(size,Double.doubleToRawLongBits(v));
          }
          return uncheckedRemoveValNaN(size);
        }
      }
      return false;
    }
    //#IFSWITCH removeVal==contains,removeVal
    //  #IF OfRef,OfByte
    //#MACRO QueryByte()
    //  #ENDIF
    //#ENDIF
    @Override
    public
    boolean
    contains(boolean val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val)
        {
          return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,TypeUtil.DBL_TRUE_BITS);
        }
        return OmniArray.OfDouble.uncheckedcontains0(this.arr,0,size-1);
      }
      return false;
    }
    @Override
    public
    boolean
    contains(int val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val!=0)
        {
          {
            return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,Double.doubleToRawLongBits(val));
          }
        }
        else
        {
          return OmniArray.OfDouble.uncheckedcontains0(this.arr,0,size-1);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    contains(long val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val!=0)
        {
          if(TypeUtil.checkCastToDouble(val))
          {
            return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,Double.doubleToRawLongBits(val));
          }
        }
        else
        {
          return OmniArray.OfDouble.uncheckedcontains0(this.arr,0,size-1);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    contains(float val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val==val)
        {
          return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,Double.doubleToRawLongBits(val));
        }
        return OmniArray.OfDouble.uncheckedcontainsNaN(this.arr,0,size-1);
      }
      return false;
    }
    @Override
    public
    boolean
    contains(double val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val==val)
        {
          return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,Double.doubleToRawLongBits(val));
        }
        {
          return OmniArray.OfDouble.uncheckedcontainsNaN(this.arr,0,size-1);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    contains
    (Object val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Double)
        {
          final double v;
          if((v=(double)val)==v)
          {
            return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,Double.doubleToRawLongBits(v));
          }
          return OmniArray.OfDouble.uncheckedcontainsNaN(this.arr,0,size-1);
        }
      }
      return false;
    }
    //#IFSWITCH contains==contains,removeVal
    //  #IF OfRef,OfByte
    //#MACRO QueryByte()
    //  #ENDIF
    //#ENDIF
    @Override
    public
    int
    indexOf(boolean val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val)
        {
          return uncheckedAbsoluteIndexOfBits(this.arr,0,size,TypeUtil.DBL_TRUE_BITS);
        }
        return uncheckedAbsoluteIndexOf0(this.arr,0,size);
      }
      return -1;
    }
    @Override
    public
    int
    indexOf(int val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val!=0)
        {
          {
            return uncheckedAbsoluteIndexOfBits(this.arr,0,size,Double.doubleToRawLongBits(val));
          }
        }
        else
        {
          return uncheckedAbsoluteIndexOf0(this.arr,0,size);
        }
      }
      return -1;
    }
    @Override
    public
    int
    indexOf(long val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val!=0)
        {
          if(TypeUtil.checkCastToDouble(val))
          {
            return uncheckedAbsoluteIndexOfBits(this.arr,0,size,Double.doubleToRawLongBits(val));
          }
        }
        else
        {
          return uncheckedAbsoluteIndexOf0(this.arr,0,size);
        }
      }
      return -1;
    }
    @Override
    public
    int
    indexOf(float val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val==val)
        {
          return uncheckedAbsoluteIndexOfBits(this.arr,0,size,Double.doubleToRawLongBits(val));
        }
        return uncheckedAbsoluteIndexOfNaN(this.arr,0,size);
      }
      return -1;
    }
    @Override
    public
    int
    indexOf(double val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val==val)
        {
          return uncheckedAbsoluteIndexOfBits(this.arr,0,size,Double.doubleToRawLongBits(val));
        }
        {
          return uncheckedAbsoluteIndexOfNaN(this.arr,0,size);
        }
      }
      return -1;
    }
    @Override
    public
    int
    indexOf
    (Object val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Double)
        {
          final double v;
          if((v=(double)val)==v)
          {
            return uncheckedAbsoluteIndexOfBits(this.arr,0,size,Double.doubleToRawLongBits(v));
          }
          return uncheckedAbsoluteIndexOfNaN(this.arr,0,size);
        }
      }
      return -1;
    }
    //#IFSWITCH indexOf==contains,removeVal
    //  #IF OfRef,OfByte
    //#MACRO QueryByte()
    //  #ENDIF
    //#ENDIF
    @Override
    public
    int
    lastIndexOf(boolean val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val)
        {
          return uncheckedAbsoluteLastIndexOfBits(this.arr,0,size,TypeUtil.DBL_TRUE_BITS);
        }
        return uncheckedAbsoluteLastIndexOf0(this.arr,0,size);
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf(int val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val!=0)
        {
          {
            return uncheckedAbsoluteLastIndexOfBits(this.arr,0,size,Double.doubleToRawLongBits(val));
          }
        }
        else
        {
          return uncheckedAbsoluteLastIndexOf0(this.arr,0,size);
        }
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf(long val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val!=0)
        {
          if(TypeUtil.checkCastToDouble(val))
          {
            return uncheckedAbsoluteLastIndexOfBits(this.arr,0,size,Double.doubleToRawLongBits(val));
          }
        }
        else
        {
          return uncheckedAbsoluteLastIndexOf0(this.arr,0,size);
        }
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf(float val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val==val)
        {
          return uncheckedAbsoluteLastIndexOfBits(this.arr,0,size,Double.doubleToRawLongBits(val));
        }
        return uncheckedAbsoluteLastIndexOfNaN(this.arr,0,size);
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf(double val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val==val)
        {
          return uncheckedAbsoluteLastIndexOfBits(this.arr,0,size,Double.doubleToRawLongBits(val));
        }
        {
          return uncheckedAbsoluteLastIndexOfNaN(this.arr,0,size);
        }
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf
    (Object val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Double)
        {
          final double v;
          if((v=(double)val)==v)
          {
            return uncheckedAbsoluteLastIndexOfBits(this.arr,0,size,Double.doubleToRawLongBits(v));
          }
          return uncheckedAbsoluteLastIndexOfNaN(this.arr,0,size);
        }
      }
      return -1;
    }
    //#IFSWITCH lastIndexOf==contains,removeVal
    //  #IF OfRef,OfByte
    //#MACRO QueryByte()
    //  #ENDIF
    //#ENDIF
    @Override
    public void put(int index,double val)
    {
      arr[index]=val;
    }
    @Override
    public double getDouble(int index)
    {
      return (double)arr[index];
    }
    @Override
    public Double get(int index)
    {
      return getDouble(index);
    }
    @Override
    public void add(int index,double val)
    {
      //TODO
    }
    @Override
    public void add(int index,Double val)
    {
      //TODO
    }
    @Override
    public double removeDoubleAt(int index)
    {
      final double[] arr;
      double ret=(double)(arr=this.arr)[index];
      ArrCopy.semicheckedSelfCopy(arr,index+1,index,(--size)-index);
      return ret;
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
    public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        {
          DoubleSortUtil.uncheckedDescendingSort(arr,0,size);
        }
      }
    }
    @Override
    public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        {
          DoubleSortUtil.uncheckedAscendingSort(arr,0,size);
        }
      }
    }
    @Override
    public OmniIterator.OfDouble iterator()
    {
      //TODO
      return null;
    }
    @Override
    public double[] toDoubleArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final double[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new double[size],0,size);
        return copy;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override
    public Double[] toArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final Double[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new Double[size],0,size);
        return copy;
      }
      return OmniArray.OfDouble.DEFAULT_BOXED_ARR;
    }
  }
  public static class UncheckedStackImpl extends DoubleArrSeq implements OmniStack.OfDouble
  {
    private int uncheckedSearchBits(int bound
    ,long val
    )
    {
      final var arr=this.arr;
      for(int index=bound-1;;)
      {
        if(
        val==Double.doubleToRawLongBits(arr[index])
        )
        {
          return bound-index;
        }
        if(index==0)
        {
          return -1;
        }
      }
    }
    private int uncheckedSearch0(int bound
    )
    {
      final var arr=this.arr;
      for(int index=bound-1;;)
      {
        if(
        0==(arr[index])
        )
        {
          return bound-index;
        }
        if(index==0)
        {
          return -1;
        }
      }
    }
    private int uncheckedSearchNaN(int bound
    )
    {
      final var arr=this.arr;
      for(int index=bound-1;;)
      {
        if(
        Double.isNaN(arr[index])
        )
        {
          return bound-index;
        }
        if(index==0)
        {
          return -1;
        }
      }
    }
    private boolean uncheckedRemoveValBits(int size
    ,long val
    )
    {
      final var arr=this.arr;
      for(int i=--size;;--i)
      {
        if(
        val==Double.doubleToRawLongBits(arr[i])
        )
        {
          ArrCopy.semicheckedSelfCopy(arr,i+1,i,size-i);
          this.size=size;
          return true;
        }
        if(i==0)
        {
          return false;
        }
      }
    }
    private boolean uncheckedRemoveVal0(int size
    )
    {
      final var arr=this.arr;
      for(int i=--size;;--i)
      {
        if(
        0==(arr[i])
        )
        {
          ArrCopy.semicheckedSelfCopy(arr,i+1,i,size-i);
          this.size=size;
          return true;
        }
        if(i==0)
        {
          return false;
        }
      }
    }
    private boolean uncheckedRemoveValNaN(int size
    )
    {
      final var arr=this.arr;
      for(int i=--size;;--i)
      {
        if(
        Double.isNaN(arr[i])
        )
        {
          ArrCopy.semicheckedSelfCopy(arr,i+1,i,size-i);
          this.size=size;
          return true;
        }
        if(i==0)
        {
          return false;
        }
      }
    }
    static void uncheckedToString(double[] arr,int begin,int end,StringBuilder builder)
    {
      for(builder.append(arr[end]);begin!=end;builder.append(',').append(' ').append(arr[--end])){}
    }
    static  void uncheckedForEach(double[] arr,int begin,int end,DoubleConsumer action)
    {
      for(;;--end)
      {
        action.accept((double)arr[end]);
        if(begin==end)
        {
          return;
        }
      }
    }
    static int uncheckedHashCode(double[] arr,int begin,int end)
    {
      int hash=31+HashUtil.hashDouble(arr[end]);
      while(begin!=end)
      {
        hash=hash*31+HashUtil.hashDouble(arr[--end]);
      }
      return hash;
    }
    public UncheckedStackImpl()
    {
      super();
    }
    public UncheckedStackImpl(int initialCapacity)
    {
      super(initialCapacity);
    }
    private UncheckedStackImpl(int size,double[] arr)
    {
      super(size,arr);
    }
    @Override
    public String toString()
    {
      int size;
      if((size=this.size)!=0)
      {
        if(size>(Integer.MAX_VALUE/5))
        {
          throw new OutOfMemoryError();
        }
        final StringBuilder builder;
        {
          uncheckedToString(arr,0,size-1,builder=new StringBuilder("["));
        }
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
        {
          return uncheckedHashCode(arr,0,size-1);
        }
      }
      return 1;
    }
    @Override
    public <T> T[] toArray(T[] dst)
    {
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedReverseCopy(this.arr,0,dst=OmniArray.uncheckedArrResize(size,dst),0,size);
      }
      else if(dst.length!=0)
      {
        dst[0]=null;
      }
      return dst;
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor)
    {
      final int size;
      T[] dst;
        dst=arrConstructor.apply(size=this.size);
      if(size!=0)
      {
        ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,size);
      }
      return dst;
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO
      return false;
    }
    @Override
    public Object clone()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final double[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new double[size],0,size);
        return new UncheckedStackImpl(size,copy);
      }
      return new UncheckedStackImpl();
    }
    @Override
    public void forEach(DoubleConsumer action)
    {
      final int size;
      if((size=this.size)!=0)
      {
        {
          uncheckedForEach(arr,0,size-1,action);
        }
      }
    }
    @Override
    public void forEach(Consumer<? super Double> action)
    {
      final int size;
      if((size=this.size)!=0)
      {
        {
          uncheckedForEach(arr,0,size-1,action::accept);
        }
      }
    }
    @Override
    public boolean removeIf(DoublePredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Double> filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(double val)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(Double val)
    {
      //TODO
      return false;
    }
    @Override
    public
    boolean
    removeVal(boolean val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val)
        {
          return uncheckedRemoveValBits(size,TypeUtil.DBL_TRUE_BITS);
        }
        return uncheckedRemoveVal0(size);
      }
      return false;
    }
    @Override
    public
    boolean
    removeVal(int val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val!=0)
        {
          {
            return uncheckedRemoveValBits(size,Double.doubleToRawLongBits(val));
          }
        }
        else
        {
          return uncheckedRemoveVal0(size);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    removeVal(long val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val!=0)
        {
          if(TypeUtil.checkCastToDouble(val))
          {
            return uncheckedRemoveValBits(size,Double.doubleToRawLongBits(val));
          }
        }
        else
        {
          return uncheckedRemoveVal0(size);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    removeVal(float val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val==val)
        {
          return uncheckedRemoveValBits(size,Double.doubleToRawLongBits(val));
        }
        return uncheckedRemoveValNaN(size);
      }
      return false;
    }
    @Override
    public
    boolean
    removeVal(double val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val==val)
        {
          return uncheckedRemoveValBits(size,Double.doubleToRawLongBits(val));
        }
        {
          return uncheckedRemoveValNaN(size);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    remove
    (Object val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Double)
        {
          final double v;
          if((v=(double)val)==v)
          {
            return uncheckedRemoveValBits(size,Double.doubleToRawLongBits(v));
          }
          return uncheckedRemoveValNaN(size);
        }
      }
      return false;
    }
    //#IFSWITCH removeVal==contains,removeVal
    //  #IF OfRef,OfByte
    //#MACRO QueryByte()
    //  #ENDIF
    //#ENDIF
    @Override
    public
    boolean
    contains(boolean val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val)
        {
          return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,TypeUtil.DBL_TRUE_BITS);
        }
        return OmniArray.OfDouble.uncheckedcontains0(this.arr,0,size-1);
      }
      return false;
    }
    @Override
    public
    boolean
    contains(int val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val!=0)
        {
          {
            return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,Double.doubleToRawLongBits(val));
          }
        }
        else
        {
          return OmniArray.OfDouble.uncheckedcontains0(this.arr,0,size-1);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    contains(long val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val!=0)
        {
          if(TypeUtil.checkCastToDouble(val))
          {
            return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,Double.doubleToRawLongBits(val));
          }
        }
        else
        {
          return OmniArray.OfDouble.uncheckedcontains0(this.arr,0,size-1);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    contains(float val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val==val)
        {
          return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,Double.doubleToRawLongBits(val));
        }
        return OmniArray.OfDouble.uncheckedcontainsNaN(this.arr,0,size-1);
      }
      return false;
    }
    @Override
    public
    boolean
    contains(double val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val==val)
        {
          return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,Double.doubleToRawLongBits(val));
        }
        {
          return OmniArray.OfDouble.uncheckedcontainsNaN(this.arr,0,size-1);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    contains
    (Object val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Double)
        {
          final double v;
          if((v=(double)val)==v)
          {
            return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,Double.doubleToRawLongBits(v));
          }
          return OmniArray.OfDouble.uncheckedcontainsNaN(this.arr,0,size-1);
        }
      }
      return false;
    }
    //#IFSWITCH contains==contains,removeVal
    //  #IF OfRef,OfByte
    //#MACRO QueryByte()
    //  #ENDIF
    //#ENDIF
    @Override
    public OmniIterator.OfDouble iterator()
    {
      //TODO
      return null;
    }
    @Override
    public double[] toDoubleArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final double[] copy;
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new double[size],0,size);
        return copy;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override
    public Double[] toArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final Double[] copy;
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new Double[size],0,size);
        return copy;
      }
      return OmniArray.OfDouble.DEFAULT_BOXED_ARR;
    }
    @Override
    public
    int
    search(boolean val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val)
        {
          return uncheckedSearchBits(size,TypeUtil.DBL_TRUE_BITS);
        }
        return uncheckedSearch0(size);
      }
      return -1;
    }
    @Override
    public
    int
    search(int val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val!=0)
        {
          {
            return uncheckedSearchBits(size,Double.doubleToRawLongBits(val));
          }
        }
        else
        {
          return uncheckedSearch0(size);
        }
      }
      return -1;
    }
    @Override
    public
    int
    search(long val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val!=0)
        {
          if(TypeUtil.checkCastToDouble(val))
          {
            return uncheckedSearchBits(size,Double.doubleToRawLongBits(val));
          }
        }
        else
        {
          return uncheckedSearch0(size);
        }
      }
      return -1;
    }
    @Override
    public
    int
    search(float val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val==val)
        {
          return uncheckedSearchBits(size,Double.doubleToRawLongBits(val));
        }
        return uncheckedSearchNaN(size);
      }
      return -1;
    }
    @Override
    public
    int
    search(double val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val==val)
        {
          return uncheckedSearchBits(size,Double.doubleToRawLongBits(val));
        }
        {
          return uncheckedSearchNaN(size);
        }
      }
      return -1;
    }
    @Override
    public
    int
    search
    (Object val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Double)
        {
          final double v;
          if((v=(double)val)==v)
          {
            return uncheckedSearchBits(size,Double.doubleToRawLongBits(v));
          }
          return uncheckedSearchNaN(size);
        }
      }
      return -1;
    }
    //#IFSWITCH search==contains,removeVal
    //  #IF OfRef,OfByte
    //#MACRO QueryByte()
    //  #ENDIF
    //#ENDIF
    @Override
    public double popDouble()
    {
      int size;
      if((size=this.size)!=0)
      {
        double ret=(double)arr[--size];
        this.size=size;
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override
    public Double pop()
    {
      return popDouble();
    }
    @Override
    public void push(double val)
    {
      //TODO
    }
    @Override
    public void push(Double val)
    {
      //TODO
    }
    @Override
    public double peekDouble()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (double)((double)arr[size-1]);
      }
      return Double.NaN;
    }
    @Override
    public double pollDouble()
    {
      int size;
      if((size=this.size)!=0)
      {
        double ret=(double)((double)arr[--size]);
        this.size=size;
        return ret;
      }
      return Double.NaN;
    }
    @Override
    public Double poll()
    {
      int size;
      if((size=this.size)!=0)
      {
        Double ret=(Double)((double)arr[--size]);
        this.size=size;
        return ret;
      }
      return null;
    }
    @Override
    public Double peek()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (Double)((double)arr[size-1]);
      }
      return null;
    }
  }
  public static class CheckedListImpl extends DoubleArrSeq implements OmniList.OfDouble
  {
    private static int uncheckedAbsoluteIndexOfBits(double[] arr,int offset,int bound
    ,long val
    )
    {
      for(;;)
      {
        if(
        val==Double.doubleToRawLongBits(arr[offset])
        )
        {
          return offset;
        }
        if(++offset==bound)
        {
          return -1;
        }
      }
    }
    private static int uncheckedAbsoluteLastIndexOfBits(double[] arr,int offset,int bound
    ,long val
    )
    {
      for(;;)
      {
        if(
        val==Double.doubleToRawLongBits(arr[--bound])
        )
        {
          return bound;
        }
        if(offset==bound)
        {
          return -1;
        }
      }
    }
    /*
    private static int uncheckedRelativeIndexOfBits(double[] arr,int offset,int bound
    ,long val
    )
    {
      for(int i=offset;;)
      {
        if(
        val==Double.doubleToRawLongBits(arr[i])
        )
        {
          return i-offset;
        }
        if(++i==bound)
        {
          return -1;
        }
      }
    }
    private static int uncheckedRelativeLastIndexOfBits(double[] arr,int offset,int bound
    ,long val
    )
    {
      for(;;)
      {
        if(
        val==Double.doubleToRawLongBits(arr[--bound])
        )
        {
          return bound-offset;
        }
        if(offset==bound)
        {
          return -1;
        }
      }
    }
    */
    private static int uncheckedAbsoluteIndexOf0(double[] arr,int offset,int bound
    )
    {
      for(;;)
      {
        if(
        0==(arr[offset])
        )
        {
          return offset;
        }
        if(++offset==bound)
        {
          return -1;
        }
      }
    }
    private static int uncheckedAbsoluteLastIndexOf0(double[] arr,int offset,int bound
    )
    {
      for(;;)
      {
        if(
        0==(arr[--bound])
        )
        {
          return bound;
        }
        if(offset==bound)
        {
          return -1;
        }
      }
    }
    /*
    private static int uncheckedRelativeIndexOf0(double[] arr,int offset,int bound
    )
    {
      for(int i=offset;;)
      {
        if(
        0==(arr[i])
        )
        {
          return i-offset;
        }
        if(++i==bound)
        {
          return -1;
        }
      }
    }
    private static int uncheckedRelativeLastIndexOf0(double[] arr,int offset,int bound
    )
    {
      for(;;)
      {
        if(
        0==(arr[--bound])
        )
        {
          return bound-offset;
        }
        if(offset==bound)
        {
          return -1;
        }
      }
    }
    */
    private static int uncheckedAbsoluteIndexOfNaN(double[] arr,int offset,int bound
    )
    {
      for(;;)
      {
        if(
        Double.isNaN(arr[offset])
        )
        {
          return offset;
        }
        if(++offset==bound)
        {
          return -1;
        }
      }
    }
    private static int uncheckedAbsoluteLastIndexOfNaN(double[] arr,int offset,int bound
    )
    {
      for(;;)
      {
        if(
        Double.isNaN(arr[--bound])
        )
        {
          return bound;
        }
        if(offset==bound)
        {
          return -1;
        }
      }
    }
    /*
    private static int uncheckedRelativeIndexOfNaN(double[] arr,int offset,int bound
    )
    {
      for(int i=offset;;)
      {
        if(
        Double.isNaN(arr[i])
        )
        {
          return i-offset;
        }
        if(++i==bound)
        {
          return -1;
        }
      }
    }
    private static int uncheckedRelativeLastIndexOfNaN(double[] arr,int offset,int bound
    )
    {
      for(;;)
      {
        if(
        Double.isNaN(arr[--bound])
        )
        {
          return bound-offset;
        }
        if(offset==bound)
        {
          return -1;
        }
      }
    }
    */
    private boolean uncheckedRemoveValBits(int size
    ,long val
    )
    {
      int modCount=this.modCount;
      final var arr=this.arr;
      for(int i=0;;)
      {
        if(
        val==Double.doubleToRawLongBits(arr[i])
        )
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
          ArrCopy.semicheckedSelfCopy(arr,i+1,i,(--size)-i);
          this.size=size;
          return true;
        }
        if(++i==size)
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          return false;
        }
      }
    }
    private boolean uncheckedRemoveVal0(int size
    )
    {
      int modCount=this.modCount;
      final var arr=this.arr;
      for(int i=0;;)
      {
        if(
        0==(arr[i])
        )
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
          ArrCopy.semicheckedSelfCopy(arr,i+1,i,(--size)-i);
          this.size=size;
          return true;
        }
        if(++i==size)
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          return false;
        }
      }
    }
    private boolean uncheckedRemoveValNaN(int size
    )
    {
      int modCount=this.modCount;
      final var arr=this.arr;
      for(int i=0;;)
      {
        if(
        Double.isNaN(arr[i])
        )
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
          ArrCopy.semicheckedSelfCopy(arr,i+1,i,(--size)-i);
          this.size=size;
          return true;
        }
        if(++i==size)
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          return false;
        }
      }
    }
    static void uncheckedToString(double[] arr,int begin,int end,StringBuilder builder)
    {
      for(builder.append(arr[begin]);begin!=end;builder.append(',').append(' ').append(arr[++begin])){}
    }
    static  void uncheckedForEach(double[] arr,int begin,int end,DoubleConsumer action)
    {
      for(;;++begin)
      {
        action.accept((double)arr[begin]);
        if(begin==end)
        {
          return;
        }
      }
    }
    static int uncheckedHashCode(double[] arr,int begin,int end)
    {
      int hash=31+HashUtil.hashDouble(arr[begin]);
      while(begin!=end)
      {
        hash=hash*31+HashUtil.hashDouble(arr[++begin]);
      }
      return hash;
    }
    transient int modCount;
    public CheckedListImpl()
    {
      super();
    }
    public CheckedListImpl(int initialCapacity)
    {
      super(initialCapacity);
    }
    private CheckedListImpl(int size,double[] arr)
    {
      super(size,arr);
    }
    @Override
    public void clear()
    {
      if(this.size!=0)
      {
        ++this.modCount;
        this.size=0;
      }
    }
    @Override
    public String toString()
    {
      int size;
      if((size=this.size)!=0)
      {
        if(size>(Integer.MAX_VALUE/5))
        {
          throw new OutOfMemoryError();
        }
        final StringBuilder builder;
        {
          uncheckedToString(arr,0,size-1,builder=new StringBuilder("["));
        }
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
        {
          return uncheckedHashCode(arr,0,size-1);
        }
      }
      return 1;
    }
    @Override
    public <T> T[] toArray(T[] dst)
    {
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,dst=OmniArray.uncheckedArrResize(size,dst),0,size);
      }
      else if(dst.length!=0)
      {
        dst[0]=null;
      }
      return dst;
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor)
    {
      final int size;
      T[] dst;
      int modCount=this.modCount;
      try
      {
        dst=arrConstructor.apply(size=this.size);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
      if(size!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,dst,0,size);
      }
      return dst;
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO
      return false;
    }
    @Override
    public Object clone()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final double[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new double[size],0,size);
        return new CheckedListImpl(size,copy);
      }
      return new CheckedListImpl();
    }
    @Override
    public void forEach(DoubleConsumer action)
    {
      final int size;
      if((size=this.size)!=0)
      {
        int modCount=this.modCount;
        try
        {
          uncheckedForEach(arr,0,size-1,action);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override
    public void forEach(Consumer<? super Double> action)
    {
      final int size;
      if((size=this.size)!=0)
      {
        int modCount=this.modCount;
        try
        {
          uncheckedForEach(arr,0,size-1,action::accept);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override
    public boolean removeIf(DoublePredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Double> filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(double val)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(Double val)
    {
      //TODO
      return false;
    }
    @Override
    public
    boolean
    removeVal(boolean val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val)
        {
          return uncheckedRemoveValBits(size,TypeUtil.DBL_TRUE_BITS);
        }
        return uncheckedRemoveVal0(size);
      }
      return false;
    }
    @Override
    public
    boolean
    removeVal(int val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val!=0)
        {
          {
            return uncheckedRemoveValBits(size,Double.doubleToRawLongBits(val));
          }
        }
        else
        {
          return uncheckedRemoveVal0(size);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    removeVal(long val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val!=0)
        {
          if(TypeUtil.checkCastToDouble(val))
          {
            return uncheckedRemoveValBits(size,Double.doubleToRawLongBits(val));
          }
        }
        else
        {
          return uncheckedRemoveVal0(size);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    removeVal(float val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val==val)
        {
          return uncheckedRemoveValBits(size,Double.doubleToRawLongBits(val));
        }
        return uncheckedRemoveValNaN(size);
      }
      return false;
    }
    @Override
    public
    boolean
    removeVal(double val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val==val)
        {
          return uncheckedRemoveValBits(size,Double.doubleToRawLongBits(val));
        }
        {
          return uncheckedRemoveValNaN(size);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    remove
    (Object val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Double)
        {
          final double v;
          if((v=(double)val)==v)
          {
            return uncheckedRemoveValBits(size,Double.doubleToRawLongBits(v));
          }
          return uncheckedRemoveValNaN(size);
        }
      }
      return false;
    }
    //#IFSWITCH removeVal==contains,removeVal
    //  #IF OfRef,OfByte
    //#MACRO QueryByte()
    //  #ENDIF
    //#ENDIF
    @Override
    public
    boolean
    contains(boolean val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val)
        {
          return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,TypeUtil.DBL_TRUE_BITS);
        }
        return OmniArray.OfDouble.uncheckedcontains0(this.arr,0,size-1);
      }
      return false;
    }
    @Override
    public
    boolean
    contains(int val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val!=0)
        {
          {
            return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,Double.doubleToRawLongBits(val));
          }
        }
        else
        {
          return OmniArray.OfDouble.uncheckedcontains0(this.arr,0,size-1);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    contains(long val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val!=0)
        {
          if(TypeUtil.checkCastToDouble(val))
          {
            return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,Double.doubleToRawLongBits(val));
          }
        }
        else
        {
          return OmniArray.OfDouble.uncheckedcontains0(this.arr,0,size-1);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    contains(float val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val==val)
        {
          return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,Double.doubleToRawLongBits(val));
        }
        return OmniArray.OfDouble.uncheckedcontainsNaN(this.arr,0,size-1);
      }
      return false;
    }
    @Override
    public
    boolean
    contains(double val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val==val)
        {
          return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,Double.doubleToRawLongBits(val));
        }
        {
          return OmniArray.OfDouble.uncheckedcontainsNaN(this.arr,0,size-1);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    contains
    (Object val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Double)
        {
          final double v;
          if((v=(double)val)==v)
          {
            return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,Double.doubleToRawLongBits(v));
          }
          return OmniArray.OfDouble.uncheckedcontainsNaN(this.arr,0,size-1);
        }
      }
      return false;
    }
    //#IFSWITCH contains==contains,removeVal
    //  #IF OfRef,OfByte
    //#MACRO QueryByte()
    //  #ENDIF
    //#ENDIF
    @Override
    public
    int
    indexOf(boolean val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val)
        {
          return uncheckedAbsoluteIndexOfBits(this.arr,0,size,TypeUtil.DBL_TRUE_BITS);
        }
        return uncheckedAbsoluteIndexOf0(this.arr,0,size);
      }
      return -1;
    }
    @Override
    public
    int
    indexOf(int val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val!=0)
        {
          {
            return uncheckedAbsoluteIndexOfBits(this.arr,0,size,Double.doubleToRawLongBits(val));
          }
        }
        else
        {
          return uncheckedAbsoluteIndexOf0(this.arr,0,size);
        }
      }
      return -1;
    }
    @Override
    public
    int
    indexOf(long val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val!=0)
        {
          if(TypeUtil.checkCastToDouble(val))
          {
            return uncheckedAbsoluteIndexOfBits(this.arr,0,size,Double.doubleToRawLongBits(val));
          }
        }
        else
        {
          return uncheckedAbsoluteIndexOf0(this.arr,0,size);
        }
      }
      return -1;
    }
    @Override
    public
    int
    indexOf(float val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val==val)
        {
          return uncheckedAbsoluteIndexOfBits(this.arr,0,size,Double.doubleToRawLongBits(val));
        }
        return uncheckedAbsoluteIndexOfNaN(this.arr,0,size);
      }
      return -1;
    }
    @Override
    public
    int
    indexOf(double val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val==val)
        {
          return uncheckedAbsoluteIndexOfBits(this.arr,0,size,Double.doubleToRawLongBits(val));
        }
        {
          return uncheckedAbsoluteIndexOfNaN(this.arr,0,size);
        }
      }
      return -1;
    }
    @Override
    public
    int
    indexOf
    (Object val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Double)
        {
          final double v;
          if((v=(double)val)==v)
          {
            return uncheckedAbsoluteIndexOfBits(this.arr,0,size,Double.doubleToRawLongBits(v));
          }
          return uncheckedAbsoluteIndexOfNaN(this.arr,0,size);
        }
      }
      return -1;
    }
    //#IFSWITCH indexOf==contains,removeVal
    //  #IF OfRef,OfByte
    //#MACRO QueryByte()
    //  #ENDIF
    //#ENDIF
    @Override
    public
    int
    lastIndexOf(boolean val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val)
        {
          return uncheckedAbsoluteLastIndexOfBits(this.arr,0,size,TypeUtil.DBL_TRUE_BITS);
        }
        return uncheckedAbsoluteLastIndexOf0(this.arr,0,size);
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf(int val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val!=0)
        {
          {
            return uncheckedAbsoluteLastIndexOfBits(this.arr,0,size,Double.doubleToRawLongBits(val));
          }
        }
        else
        {
          return uncheckedAbsoluteLastIndexOf0(this.arr,0,size);
        }
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf(long val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val!=0)
        {
          if(TypeUtil.checkCastToDouble(val))
          {
            return uncheckedAbsoluteLastIndexOfBits(this.arr,0,size,Double.doubleToRawLongBits(val));
          }
        }
        else
        {
          return uncheckedAbsoluteLastIndexOf0(this.arr,0,size);
        }
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf(float val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val==val)
        {
          return uncheckedAbsoluteLastIndexOfBits(this.arr,0,size,Double.doubleToRawLongBits(val));
        }
        return uncheckedAbsoluteLastIndexOfNaN(this.arr,0,size);
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf(double val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val==val)
        {
          return uncheckedAbsoluteLastIndexOfBits(this.arr,0,size,Double.doubleToRawLongBits(val));
        }
        {
          return uncheckedAbsoluteLastIndexOfNaN(this.arr,0,size);
        }
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf
    (Object val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Double)
        {
          final double v;
          if((v=(double)val)==v)
          {
            return uncheckedAbsoluteLastIndexOfBits(this.arr,0,size,Double.doubleToRawLongBits(v));
          }
          return uncheckedAbsoluteLastIndexOfNaN(this.arr,0,size);
        }
      }
      return -1;
    }
    //#IFSWITCH lastIndexOf==contains,removeVal
    //  #IF OfRef,OfByte
    //#MACRO QueryByte()
    //  #ENDIF
    //#ENDIF
    @Override
    public void put(int index,double val)
    {
      if(index<0 || index>=this.size)
      {
        throw new IndexOutOfBoundsException("index="+index+"; size="+this.size);
      }
      arr[index]=val;
    }
    @Override
    public double getDouble(int index)
    {
      if(index<0 || index>=this.size)
      {
        throw new IndexOutOfBoundsException("index="+index+"; size="+this.size);
      }
      return (double)arr[index];
    }
    @Override
    public Double get(int index)
    {
      return getDouble(index);
    }
    @Override
    public void add(int index,double val)
    {
      //TODO
    }
    @Override
    public void add(int index,Double val)
    {
      //TODO
    }
    @Override
    public double removeDoubleAt(int index)
    {
      int size;
      if(index<0 || index>=(size=this.size))
      {
        throw new IndexOutOfBoundsException("index = "+index+"; size="+this.size);
      }
      ++this.modCount;
      final double[] arr;
      double ret=(double)(arr=this.arr)[index];
      ArrCopy.semicheckedSelfCopy(arr,index+1,index,(--size)-index);
      this.size=size;
      return ret;
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
    public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        {
          DoubleSortUtil.uncheckedDescendingSort(arr,0,size);
        }
      }
    }
    @Override
    public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        {
          DoubleSortUtil.uncheckedAscendingSort(arr,0,size);
        }
      }
    }
    @Override
    public OmniIterator.OfDouble iterator()
    {
      //TODO
      return null;
    }
    @Override
    public double[] toDoubleArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final double[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new double[size],0,size);
        return copy;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override
    public Double[] toArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final Double[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new Double[size],0,size);
        return copy;
      }
      return OmniArray.OfDouble.DEFAULT_BOXED_ARR;
    }
  }
  public static class CheckedStackImpl extends DoubleArrSeq implements OmniStack.OfDouble
  {
    private int uncheckedSearchBits(int bound
    ,long val
    )
    {
      final var arr=this.arr;
      for(int index=bound-1;;)
      {
        if(
        val==Double.doubleToRawLongBits(arr[index])
        )
        {
          return bound-index;
        }
        if(index==0)
        {
          return -1;
        }
      }
    }
    private int uncheckedSearch0(int bound
    )
    {
      final var arr=this.arr;
      for(int index=bound-1;;)
      {
        if(
        0==(arr[index])
        )
        {
          return bound-index;
        }
        if(index==0)
        {
          return -1;
        }
      }
    }
    private int uncheckedSearchNaN(int bound
    )
    {
      final var arr=this.arr;
      for(int index=bound-1;;)
      {
        if(
        Double.isNaN(arr[index])
        )
        {
          return bound-index;
        }
        if(index==0)
        {
          return -1;
        }
      }
    }
    private boolean uncheckedRemoveValBits(int size
    ,long val
    )
    {
      int modCount=this.modCount;
      final var arr=this.arr;
      for(int i=--size;;--i)
      {
        if(
        val==Double.doubleToRawLongBits(arr[i])
        )
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
          ArrCopy.semicheckedSelfCopy(arr,i+1,i,size-i);
          this.size=size;
          return true;
        }
        if(i==0)
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          return false;
        }
      }
    }
    private boolean uncheckedRemoveVal0(int size
    )
    {
      int modCount=this.modCount;
      final var arr=this.arr;
      for(int i=--size;;--i)
      {
        if(
        0==(arr[i])
        )
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
          ArrCopy.semicheckedSelfCopy(arr,i+1,i,size-i);
          this.size=size;
          return true;
        }
        if(i==0)
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          return false;
        }
      }
    }
    private boolean uncheckedRemoveValNaN(int size
    )
    {
      int modCount=this.modCount;
      final var arr=this.arr;
      for(int i=--size;;--i)
      {
        if(
        Double.isNaN(arr[i])
        )
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
          ArrCopy.semicheckedSelfCopy(arr,i+1,i,size-i);
          this.size=size;
          return true;
        }
        if(i==0)
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          return false;
        }
      }
    }
    static void uncheckedToString(double[] arr,int begin,int end,StringBuilder builder)
    {
      for(builder.append(arr[end]);begin!=end;builder.append(',').append(' ').append(arr[--end])){}
    }
    static  void uncheckedForEach(double[] arr,int begin,int end,DoubleConsumer action)
    {
      for(;;--end)
      {
        action.accept((double)arr[end]);
        if(begin==end)
        {
          return;
        }
      }
    }
    static int uncheckedHashCode(double[] arr,int begin,int end)
    {
      int hash=31+HashUtil.hashDouble(arr[end]);
      while(begin!=end)
      {
        hash=hash*31+HashUtil.hashDouble(arr[--end]);
      }
      return hash;
    }
    transient int modCount;
    public CheckedStackImpl()
    {
      super();
    }
    public CheckedStackImpl(int initialCapacity)
    {
      super(initialCapacity);
    }
    private CheckedStackImpl(int size,double[] arr)
    {
      super(size,arr);
    }
    @Override
    public void clear()
    {
      if(this.size!=0)
      {
        ++this.modCount;
        this.size=0;
      }
    }
    @Override
    public String toString()
    {
      int size;
      if((size=this.size)!=0)
      {
        if(size>(Integer.MAX_VALUE/5))
        {
          throw new OutOfMemoryError();
        }
        final StringBuilder builder;
        {
          uncheckedToString(arr,0,size-1,builder=new StringBuilder("["));
        }
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
        {
          return uncheckedHashCode(arr,0,size-1);
        }
      }
      return 1;
    }
    @Override
    public <T> T[] toArray(T[] dst)
    {
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedReverseCopy(this.arr,0,dst=OmniArray.uncheckedArrResize(size,dst),0,size);
      }
      else if(dst.length!=0)
      {
        dst[0]=null;
      }
      return dst;
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor)
    {
      final int size;
      T[] dst;
      int modCount=this.modCount;
      try
      {
        dst=arrConstructor.apply(size=this.size);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
      if(size!=0)
      {
        ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,size);
      }
      return dst;
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO
      return false;
    }
    @Override
    public Object clone()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final double[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new double[size],0,size);
        return new CheckedStackImpl(size,copy);
      }
      return new CheckedStackImpl();
    }
    @Override
    public void forEach(DoubleConsumer action)
    {
      final int size;
      if((size=this.size)!=0)
      {
        int modCount=this.modCount;
        try
        {
          uncheckedForEach(arr,0,size-1,action);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override
    public void forEach(Consumer<? super Double> action)
    {
      final int size;
      if((size=this.size)!=0)
      {
        int modCount=this.modCount;
        try
        {
          uncheckedForEach(arr,0,size-1,action::accept);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override
    public boolean removeIf(DoublePredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Double> filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(double val)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(Double val)
    {
      //TODO
      return false;
    }
    @Override
    public
    boolean
    removeVal(boolean val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val)
        {
          return uncheckedRemoveValBits(size,TypeUtil.DBL_TRUE_BITS);
        }
        return uncheckedRemoveVal0(size);
      }
      return false;
    }
    @Override
    public
    boolean
    removeVal(int val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val!=0)
        {
          {
            return uncheckedRemoveValBits(size,Double.doubleToRawLongBits(val));
          }
        }
        else
        {
          return uncheckedRemoveVal0(size);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    removeVal(long val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val!=0)
        {
          if(TypeUtil.checkCastToDouble(val))
          {
            return uncheckedRemoveValBits(size,Double.doubleToRawLongBits(val));
          }
        }
        else
        {
          return uncheckedRemoveVal0(size);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    removeVal(float val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val==val)
        {
          return uncheckedRemoveValBits(size,Double.doubleToRawLongBits(val));
        }
        return uncheckedRemoveValNaN(size);
      }
      return false;
    }
    @Override
    public
    boolean
    removeVal(double val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val==val)
        {
          return uncheckedRemoveValBits(size,Double.doubleToRawLongBits(val));
        }
        {
          return uncheckedRemoveValNaN(size);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    remove
    (Object val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Double)
        {
          final double v;
          if((v=(double)val)==v)
          {
            return uncheckedRemoveValBits(size,Double.doubleToRawLongBits(v));
          }
          return uncheckedRemoveValNaN(size);
        }
      }
      return false;
    }
    //#IFSWITCH removeVal==contains,removeVal
    //  #IF OfRef,OfByte
    //#MACRO QueryByte()
    //  #ENDIF
    //#ENDIF
    @Override
    public
    boolean
    contains(boolean val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val)
        {
          return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,TypeUtil.DBL_TRUE_BITS);
        }
        return OmniArray.OfDouble.uncheckedcontains0(this.arr,0,size-1);
      }
      return false;
    }
    @Override
    public
    boolean
    contains(int val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val!=0)
        {
          {
            return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,Double.doubleToRawLongBits(val));
          }
        }
        else
        {
          return OmniArray.OfDouble.uncheckedcontains0(this.arr,0,size-1);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    contains(long val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val!=0)
        {
          if(TypeUtil.checkCastToDouble(val))
          {
            return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,Double.doubleToRawLongBits(val));
          }
        }
        else
        {
          return OmniArray.OfDouble.uncheckedcontains0(this.arr,0,size-1);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    contains(float val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val==val)
        {
          return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,Double.doubleToRawLongBits(val));
        }
        return OmniArray.OfDouble.uncheckedcontainsNaN(this.arr,0,size-1);
      }
      return false;
    }
    @Override
    public
    boolean
    contains(double val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val==val)
        {
          return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,Double.doubleToRawLongBits(val));
        }
        {
          return OmniArray.OfDouble.uncheckedcontainsNaN(this.arr,0,size-1);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    contains
    (Object val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Double)
        {
          final double v;
          if((v=(double)val)==v)
          {
            return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,Double.doubleToRawLongBits(v));
          }
          return OmniArray.OfDouble.uncheckedcontainsNaN(this.arr,0,size-1);
        }
      }
      return false;
    }
    //#IFSWITCH contains==contains,removeVal
    //  #IF OfRef,OfByte
    //#MACRO QueryByte()
    //  #ENDIF
    //#ENDIF
    @Override
    public OmniIterator.OfDouble iterator()
    {
      //TODO
      return null;
    }
    @Override
    public double[] toDoubleArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final double[] copy;
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new double[size],0,size);
        return copy;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override
    public Double[] toArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final Double[] copy;
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new Double[size],0,size);
        return copy;
      }
      return OmniArray.OfDouble.DEFAULT_BOXED_ARR;
    }
    @Override
    public
    int
    search(boolean val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val)
        {
          return uncheckedSearchBits(size,TypeUtil.DBL_TRUE_BITS);
        }
        return uncheckedSearch0(size);
      }
      return -1;
    }
    @Override
    public
    int
    search(int val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val!=0)
        {
          {
            return uncheckedSearchBits(size,Double.doubleToRawLongBits(val));
          }
        }
        else
        {
          return uncheckedSearch0(size);
        }
      }
      return -1;
    }
    @Override
    public
    int
    search(long val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val!=0)
        {
          if(TypeUtil.checkCastToDouble(val))
          {
            return uncheckedSearchBits(size,Double.doubleToRawLongBits(val));
          }
        }
        else
        {
          return uncheckedSearch0(size);
        }
      }
      return -1;
    }
    @Override
    public
    int
    search(float val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val==val)
        {
          return uncheckedSearchBits(size,Double.doubleToRawLongBits(val));
        }
        return uncheckedSearchNaN(size);
      }
      return -1;
    }
    @Override
    public
    int
    search(double val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val==val)
        {
          return uncheckedSearchBits(size,Double.doubleToRawLongBits(val));
        }
        {
          return uncheckedSearchNaN(size);
        }
      }
      return -1;
    }
    @Override
    public
    int
    search
    (Object val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Double)
        {
          final double v;
          if((v=(double)val)==v)
          {
            return uncheckedSearchBits(size,Double.doubleToRawLongBits(v));
          }
          return uncheckedSearchNaN(size);
        }
      }
      return -1;
    }
    //#IFSWITCH search==contains,removeVal
    //  #IF OfRef,OfByte
    //#MACRO QueryByte()
    //  #ENDIF
    //#ENDIF
    @Override
    public double popDouble()
    {
      int size;
      if((size=this.size)!=0)
      {
        double ret=(double)arr[--size];
        ++this.modCount;
        this.size=size;
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override
    public Double pop()
    {
      return popDouble();
    }
    @Override
    public void push(double val)
    {
      //TODO
    }
    @Override
    public void push(Double val)
    {
      //TODO
    }
    @Override
    public double peekDouble()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (double)((double)arr[size-1]);
      }
      return Double.NaN;
    }
    @Override
    public double pollDouble()
    {
      int size;
      if((size=this.size)!=0)
      {
        double ret=(double)((double)arr[--size]);
        ++this.modCount;
        this.size=size;
        return ret;
      }
      return Double.NaN;
    }
    @Override
    public Double poll()
    {
      int size;
      if((size=this.size)!=0)
      {
        Double ret=(Double)((double)arr[--size]);
        ++this.modCount;
        this.size=size;
        return ret;
      }
      return null;
    }
    @Override
    public Double peek()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (Double)((double)arr[size-1]);
      }
      return null;
    }
  }
}
