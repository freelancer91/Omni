package omni.impl.seq;
import omni.api.OmniCollection;
import omni.util.OmniArray;
import omni.api.OmniList;
import omni.api.OmniStack;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.function.LongPredicate;
import java.util.function.LongConsumer;
import java.util.Comparator;
import omni.function.LongComparator;
import omni.util.ToStringUtil;
import omni.util.ArrCopy;
import omni.util.LongSortUtil;
import omni.impl.CheckedCollection;
import omni.util.TypeUtil;
import java.util.NoSuchElementException;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import java.util.function.IntFunction;
public abstract class LongArrSeq implements OmniCollection.OfLong
{
  transient int size;
  transient long[] arr;
  private LongArrSeq()
  {
    super();
  }
  private LongArrSeq(int initialCapacity)
  {
    super();
    switch(initialCapacity)
    { 
    default:
      this.arr=new long[initialCapacity];
      return;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfLong.DEFAULT_ARR;
    case 0:
    }
  }
  private LongArrSeq(int size,long[] arr)
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
  public static class UncheckedListImpl extends LongArrSeq implements OmniList.OfLong
  {
    private static int uncheckedAbsoluteIndexOf (long[] arr,int offset,int bound
    ,long val
    )
    {
      for(;;)
      {
        if(
        val==(arr[offset])
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
    private static int uncheckedAbsoluteLastIndexOf (long[] arr,int offset,int bound
    ,long val
    )
    {
      for(;;)
      {
        if(
        val==(arr[--bound])
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
    private static int uncheckedRelativeIndexOf (long[] arr,int offset,int bound
    ,long val
    )
    {
      for(int i=offset;;)
      {
        if(
        val==(arr[i])
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
    private static int uncheckedRelativeLastIndexOf (long[] arr,int offset,int bound
    ,long val
    )
    {
      for(;;)
      {
        if(
        val==(arr[--bound])
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
    private boolean uncheckedRemoveVal (int size
    ,long val
    )
    {
      final var arr=this.arr;
      for(int i=0;;)
      {
        if(
        val==(arr[i])
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
    static int uncheckedToString(long[] arr,int begin,int end,char[] buffer)
    {
      int bufferOffset;
      for(bufferOffset=ToStringUtil.getStringLong(arr[begin],buffer,1);begin!=end;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringLong(arr[++begin],buffer,bufferOffset)){}
      return bufferOffset;
    }
    static void uncheckedToString(long[] arr,int begin,int end,ToStringUtil.OmniStringBuilder builder)
    {
      for(builder.uncheckedAppendLong(arr[begin]);begin!=end;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendLong(arr[++begin])){}
    }
    static  void uncheckedForEach(long[] arr,int begin,int end,LongConsumer action)
    {
      for(;;++begin)
      {
        action.accept((long)arr[begin]);
        if(begin==end)
        {
          return;
        }
      }
    }
    static int uncheckedHashCode(long[] arr,int begin,int end)
    {
      int hash=31+Long.hashCode(arr[begin]);
      while(begin!=end)
      {
        hash=hash*31+Long.hashCode(arr[++begin]);
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
    private UncheckedListImpl(int size,long[] arr)
    {
      super(size,arr);
    }
    @Override
    public String toString()
    {
      int size;
      if((size=this.size)!=0)
      {
        if(size>(Integer.MAX_VALUE/3))
        {
          throw new OutOfMemoryError();
        }
        final char[] buffer;
        if(size<=(OmniArray.MAX_ARR_SIZE/((22)<<1)))
        {
          (buffer=new char[size*(22)])[size=uncheckedToString(arr,0,size-1,buffer)]=']';
          buffer[0]='[';
          return new String(buffer,0,size+1);
        }
        else
        {
          final ToStringUtil.OmniStringBuilder builder;
          uncheckedToString(arr,0,size-1,builder=new ToStringUtil.OmniStringBuilder(1,new char[size<=(int)(OmniArray.MAX_ARR_SIZE/12.5f)?(size*12)+(size>>>1):OmniArray.MAX_ARR_SIZE]));
          builder.uncheckedAppendChar(']');
          (buffer=builder.buffer)[0]='[';
          return new String(buffer,0,builder.size);
        }
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
        final long[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new long[size],0,size);
        return new UncheckedListImpl(size,copy);
      }
      return new UncheckedListImpl();
    }
    @Override
    public void forEach(LongConsumer action)
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
    public void forEach(Consumer<? super Long> action)
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
    public boolean removeIf(LongPredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Long> filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(long val)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(Long val)
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
        return uncheckedRemoveVal(size,TypeUtil.castToLong(val));
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
        return uncheckedRemoveVal(size,(val));
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
        return uncheckedRemoveVal(size,(val));
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
        final long v;
        if(TypeUtil.floatEquals(val,v=(long)val))
        {
          return uncheckedRemoveVal(size,v);
        }
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
        final long v;
        if(TypeUtil.doubleEquals(val,v=(long)val))
        {
          return uncheckedRemoveVal(size,v);
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
        if(val instanceof Long)
        {
          {
            return uncheckedRemoveVal(size,(long)(val));
          }
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
        return OmniArray.OfLong.uncheckedcontains(this.arr,0,size-1,TypeUtil.castToLong(val));
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
        return OmniArray.OfLong.uncheckedcontains(this.arr,0,size-1,(val));
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
        return OmniArray.OfLong.uncheckedcontains(this.arr,0,size-1,(val));
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
        final long v;
        if(TypeUtil.floatEquals(val,v=(long)val))
        {
          return OmniArray.OfLong.uncheckedcontains(this.arr,0,size-1,v);
        }
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
        final long v;
        if(TypeUtil.doubleEquals(val,v=(long)val))
        {
          return OmniArray.OfLong.uncheckedcontains(this.arr,0,size-1,v);
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
        if(val instanceof Long)
        {
          {
            return OmniArray.OfLong.uncheckedcontains(this.arr,0,size-1,(long)(val));
          }
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
        return uncheckedAbsoluteIndexOf(this.arr,0,size,TypeUtil.castToLong(val));
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
        return uncheckedAbsoluteIndexOf(this.arr,0,size,(val));
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
        return uncheckedAbsoluteIndexOf(this.arr,0,size,(val));
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
        final long v;
        if(TypeUtil.floatEquals(val,v=(long)val))
        {
          return uncheckedAbsoluteIndexOf(this.arr,0,size,v);
        }
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
        final long v;
        if(TypeUtil.doubleEquals(val,v=(long)val))
        {
          return uncheckedAbsoluteIndexOf(this.arr,0,size,v);
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
        if(val instanceof Long)
        {
          {
            return uncheckedAbsoluteIndexOf(this.arr,0,size,(long)(val));
          }
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
        return uncheckedAbsoluteLastIndexOf(this.arr,0,size,TypeUtil.castToLong(val));
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
        return uncheckedAbsoluteLastIndexOf(this.arr,0,size,(val));
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
        return uncheckedAbsoluteLastIndexOf(this.arr,0,size,(val));
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
        final long v;
        if(TypeUtil.floatEquals(val,v=(long)val))
        {
          return uncheckedAbsoluteLastIndexOf(this.arr,0,size,v);
        }
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
        final long v;
        if(TypeUtil.doubleEquals(val,v=(long)val))
        {
          return uncheckedAbsoluteLastIndexOf(this.arr,0,size,v);
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
        if(val instanceof Long)
        {
          {
            return uncheckedAbsoluteLastIndexOf(this.arr,0,size,(long)(val));
          }
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
    public void put(int index,long val)
    {
      arr[index]=val;
    }
    @Override
    public long getLong(int index)
    {
      return (long)arr[index];
    }
    @Override
    public Long get(int index)
    {
      return getLong(index);
    }
    @Override
    public void add(int index,long val)
    {
      //TODO
    }
    @Override
    public void add(int index,Long val)
    {
      //TODO
    }
    @Override
    public long removeLongAt(int index)
    {
      final long[] arr;
      long ret=(long)(arr=this.arr)[index];
      ArrCopy.semicheckedSelfCopy(arr,index+1,index,(--size)-index);
      return ret;
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
    public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        {
          LongSortUtil.uncheckedDescendingSort(arr,0,size);
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
          LongSortUtil.uncheckedAscendingSort(arr,0,size);
        }
      }
    }
    @Override
    public OmniIterator.OfLong iterator()
    {
      //TODO
      return null;
    }
    @Override
    public long[] toLongArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final long[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new long[size],0,size);
        return copy;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override
    public Long[] toArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final Long[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new Long[size],0,size);
        return copy;
      }
      return OmniArray.OfLong.DEFAULT_BOXED_ARR;
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
    public float[] toFloatArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final float[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new float[size],0,size);
        return copy;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
  }
  public static class UncheckedStackImpl extends LongArrSeq implements OmniStack.OfLong
  {
    private int uncheckedSearch (int bound
    ,long val
    )
    {
      final var arr=this.arr;
      for(int index=bound-1;;)
      {
        if(
        val==(arr[index])
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
    private boolean uncheckedRemoveVal (int size
    ,long val
    )
    {
      final var arr=this.arr;
      for(int i=--size;;--i)
      {
        if(
        val==(arr[i])
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
    static int uncheckedToString(long[] arr,int begin,int end,char[] buffer)
    {
      int bufferOffset;
      for(bufferOffset=ToStringUtil.getStringLong(arr[end],buffer,1);begin!=end;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringLong(arr[--end],buffer,bufferOffset)){}
      return bufferOffset;
    }
    static void uncheckedToString(long[] arr,int begin,int end,ToStringUtil.OmniStringBuilder builder)
    {
      for(builder.uncheckedAppendLong(arr[end]);begin!=end;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendLong(arr[--end])){}
    }
    static  void uncheckedForEach(long[] arr,int begin,int end,LongConsumer action)
    {
      for(;;--end)
      {
        action.accept((long)arr[end]);
        if(begin==end)
        {
          return;
        }
      }
    }
    static int uncheckedHashCode(long[] arr,int begin,int end)
    {
      int hash=31+Long.hashCode(arr[end]);
      while(begin!=end)
      {
        hash=hash*31+Long.hashCode(arr[--end]);
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
    private UncheckedStackImpl(int size,long[] arr)
    {
      super(size,arr);
    }
    @Override
    public String toString()
    {
      int size;
      if((size=this.size)!=0)
      {
        if(size>(Integer.MAX_VALUE/3))
        {
          throw new OutOfMemoryError();
        }
        final char[] buffer;
        if(size<=(OmniArray.MAX_ARR_SIZE/((22)<<1)))
        {
          (buffer=new char[size*(22)])[size=uncheckedToString(arr,0,size-1,buffer)]=']';
          buffer[0]='[';
          return new String(buffer,0,size+1);
        }
        else
        {
          final ToStringUtil.OmniStringBuilder builder;
          uncheckedToString(arr,0,size-1,builder=new ToStringUtil.OmniStringBuilder(1,new char[size<=(int)(OmniArray.MAX_ARR_SIZE/12.5f)?(size*12)+(size>>>1):OmniArray.MAX_ARR_SIZE]));
          builder.uncheckedAppendChar(']');
          (buffer=builder.buffer)[0]='[';
          return new String(buffer,0,builder.size);
        }
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
        final long[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new long[size],0,size);
        return new UncheckedStackImpl(size,copy);
      }
      return new UncheckedStackImpl();
    }
    @Override
    public void forEach(LongConsumer action)
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
    public void forEach(Consumer<? super Long> action)
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
    public boolean removeIf(LongPredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Long> filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(long val)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(Long val)
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
        return uncheckedRemoveVal(size,TypeUtil.castToLong(val));
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
        return uncheckedRemoveVal(size,(val));
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
        return uncheckedRemoveVal(size,(val));
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
        final long v;
        if(TypeUtil.floatEquals(val,v=(long)val))
        {
          return uncheckedRemoveVal(size,v);
        }
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
        final long v;
        if(TypeUtil.doubleEquals(val,v=(long)val))
        {
          return uncheckedRemoveVal(size,v);
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
        if(val instanceof Long)
        {
          {
            return uncheckedRemoveVal(size,(long)(val));
          }
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
        return OmniArray.OfLong.uncheckedcontains(this.arr,0,size-1,TypeUtil.castToLong(val));
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
        return OmniArray.OfLong.uncheckedcontains(this.arr,0,size-1,(val));
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
        return OmniArray.OfLong.uncheckedcontains(this.arr,0,size-1,(val));
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
        final long v;
        if(TypeUtil.floatEquals(val,v=(long)val))
        {
          return OmniArray.OfLong.uncheckedcontains(this.arr,0,size-1,v);
        }
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
        final long v;
        if(TypeUtil.doubleEquals(val,v=(long)val))
        {
          return OmniArray.OfLong.uncheckedcontains(this.arr,0,size-1,v);
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
        if(val instanceof Long)
        {
          {
            return OmniArray.OfLong.uncheckedcontains(this.arr,0,size-1,(long)(val));
          }
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
    public OmniIterator.OfLong iterator()
    {
      //TODO
      return null;
    }
    @Override
    public long[] toLongArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final long[] copy;
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new long[size],0,size);
        return copy;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override
    public Long[] toArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final Long[] copy;
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new Long[size],0,size);
        return copy;
      }
      return OmniArray.OfLong.DEFAULT_BOXED_ARR;
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
    public float[] toFloatArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final float[] copy;
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new float[size],0,size);
        return copy;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override
    public
    int
    search(boolean val)
    {
      int size;
      if((size=this.size)!=0)
      {
        return uncheckedSearch(size,TypeUtil.castToLong(val));
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
        return uncheckedSearch(size,(val));
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
        return uncheckedSearch(size,(val));
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
        final long v;
        if(TypeUtil.floatEquals(val,v=(long)val))
        {
          return uncheckedSearch(size,v);
        }
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
        final long v;
        if(TypeUtil.doubleEquals(val,v=(long)val))
        {
          return uncheckedSearch(size,v);
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
        if(val instanceof Long)
        {
          {
            return uncheckedSearch(size,(long)(val));
          }
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
    public long popLong()
    {
      int size;
      if((size=this.size)!=0)
      {
        long ret=(long)arr[--size];
        this.size=size;
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override
    public Long pop()
    {
      return popLong();
    }
    @Override
    public void push(long val)
    {
      //TODO
    }
    @Override
    public void push(Long val)
    {
      //TODO
    }
    @Override
    public long peekLong()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (long)((long)arr[size-1]);
      }
      return Long.MIN_VALUE;
    }
    @Override
    public long pollLong()
    {
      int size;
      if((size=this.size)!=0)
      {
        long ret=(long)((long)arr[--size]);
        this.size=size;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override
    public Long poll()
    {
      int size;
      if((size=this.size)!=0)
      {
        Long ret=(Long)((long)arr[--size]);
        this.size=size;
        return ret;
      }
      return null;
    }
    @Override
    public double pollDouble()
    {
      int size;
      if((size=this.size)!=0)
      {
        double ret=(double)((long)arr[--size]);
        this.size=size;
        return ret;
      }
      return Double.NaN;
    }
    @Override
    public float pollFloat()
    {
      int size;
      if((size=this.size)!=0)
      {
        float ret=(float)((long)arr[--size]);
        this.size=size;
        return ret;
      }
      return Float.NaN;
    }
    @Override
    public Long peek()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (Long)((long)arr[size-1]);
      }
      return null;
    }
    @Override
    public double peekDouble()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (double)((long)arr[size-1]);
      }
      return Double.NaN;
    }
    @Override
    public float peekFloat()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (float)((long)arr[size-1]);
      }
      return Float.NaN;
    }
  }
  public static class CheckedListImpl extends LongArrSeq implements OmniList.OfLong
  {
    private static int uncheckedAbsoluteIndexOf (long[] arr,int offset,int bound
    ,long val
    )
    {
      for(;;)
      {
        if(
        val==(arr[offset])
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
    private static int uncheckedAbsoluteLastIndexOf (long[] arr,int offset,int bound
    ,long val
    )
    {
      for(;;)
      {
        if(
        val==(arr[--bound])
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
    private static int uncheckedRelativeIndexOf (long[] arr,int offset,int bound
    ,long val
    )
    {
      for(int i=offset;;)
      {
        if(
        val==(arr[i])
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
    private static int uncheckedRelativeLastIndexOf (long[] arr,int offset,int bound
    ,long val
    )
    {
      for(;;)
      {
        if(
        val==(arr[--bound])
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
    private boolean uncheckedRemoveVal (int size
    ,long val
    )
    {
      int modCount=this.modCount;
      final var arr=this.arr;
      for(int i=0;;)
      {
        if(
        val==(arr[i])
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
    static int uncheckedToString(long[] arr,int begin,int end,char[] buffer)
    {
      int bufferOffset;
      for(bufferOffset=ToStringUtil.getStringLong(arr[begin],buffer,1);begin!=end;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringLong(arr[++begin],buffer,bufferOffset)){}
      return bufferOffset;
    }
    static void uncheckedToString(long[] arr,int begin,int end,ToStringUtil.OmniStringBuilder builder)
    {
      for(builder.uncheckedAppendLong(arr[begin]);begin!=end;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendLong(arr[++begin])){}
    }
    static  void uncheckedForEach(long[] arr,int begin,int end,LongConsumer action)
    {
      for(;;++begin)
      {
        action.accept((long)arr[begin]);
        if(begin==end)
        {
          return;
        }
      }
    }
    static int uncheckedHashCode(long[] arr,int begin,int end)
    {
      int hash=31+Long.hashCode(arr[begin]);
      while(begin!=end)
      {
        hash=hash*31+Long.hashCode(arr[++begin]);
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
    private CheckedListImpl(int size,long[] arr)
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
        if(size>(Integer.MAX_VALUE/3))
        {
          throw new OutOfMemoryError();
        }
        final char[] buffer;
        if(size<=(OmniArray.MAX_ARR_SIZE/((22)<<1)))
        {
          (buffer=new char[size*(22)])[size=uncheckedToString(arr,0,size-1,buffer)]=']';
          buffer[0]='[';
          return new String(buffer,0,size+1);
        }
        else
        {
          final ToStringUtil.OmniStringBuilder builder;
          uncheckedToString(arr,0,size-1,builder=new ToStringUtil.OmniStringBuilder(1,new char[size<=(int)(OmniArray.MAX_ARR_SIZE/12.5f)?(size*12)+(size>>>1):OmniArray.MAX_ARR_SIZE]));
          builder.uncheckedAppendChar(']');
          (buffer=builder.buffer)[0]='[';
          return new String(buffer,0,builder.size);
        }
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
        final long[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new long[size],0,size);
        return new CheckedListImpl(size,copy);
      }
      return new CheckedListImpl();
    }
    @Override
    public void forEach(LongConsumer action)
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
    public void forEach(Consumer<? super Long> action)
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
    public boolean removeIf(LongPredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Long> filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(long val)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(Long val)
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
        return uncheckedRemoveVal(size,TypeUtil.castToLong(val));
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
        return uncheckedRemoveVal(size,(val));
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
        return uncheckedRemoveVal(size,(val));
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
        final long v;
        if(TypeUtil.floatEquals(val,v=(long)val))
        {
          return uncheckedRemoveVal(size,v);
        }
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
        final long v;
        if(TypeUtil.doubleEquals(val,v=(long)val))
        {
          return uncheckedRemoveVal(size,v);
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
        if(val instanceof Long)
        {
          {
            return uncheckedRemoveVal(size,(long)(val));
          }
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
        return OmniArray.OfLong.uncheckedcontains(this.arr,0,size-1,TypeUtil.castToLong(val));
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
        return OmniArray.OfLong.uncheckedcontains(this.arr,0,size-1,(val));
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
        return OmniArray.OfLong.uncheckedcontains(this.arr,0,size-1,(val));
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
        final long v;
        if(TypeUtil.floatEquals(val,v=(long)val))
        {
          return OmniArray.OfLong.uncheckedcontains(this.arr,0,size-1,v);
        }
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
        final long v;
        if(TypeUtil.doubleEquals(val,v=(long)val))
        {
          return OmniArray.OfLong.uncheckedcontains(this.arr,0,size-1,v);
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
        if(val instanceof Long)
        {
          {
            return OmniArray.OfLong.uncheckedcontains(this.arr,0,size-1,(long)(val));
          }
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
        return uncheckedAbsoluteIndexOf(this.arr,0,size,TypeUtil.castToLong(val));
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
        return uncheckedAbsoluteIndexOf(this.arr,0,size,(val));
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
        return uncheckedAbsoluteIndexOf(this.arr,0,size,(val));
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
        final long v;
        if(TypeUtil.floatEquals(val,v=(long)val))
        {
          return uncheckedAbsoluteIndexOf(this.arr,0,size,v);
        }
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
        final long v;
        if(TypeUtil.doubleEquals(val,v=(long)val))
        {
          return uncheckedAbsoluteIndexOf(this.arr,0,size,v);
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
        if(val instanceof Long)
        {
          int modCount=this.modCount;
          try
          {
            return uncheckedAbsoluteIndexOf(this.arr,0,size,(long)(val));
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
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
        return uncheckedAbsoluteLastIndexOf(this.arr,0,size,TypeUtil.castToLong(val));
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
        return uncheckedAbsoluteLastIndexOf(this.arr,0,size,(val));
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
        return uncheckedAbsoluteLastIndexOf(this.arr,0,size,(val));
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
        final long v;
        if(TypeUtil.floatEquals(val,v=(long)val))
        {
          return uncheckedAbsoluteLastIndexOf(this.arr,0,size,v);
        }
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
        final long v;
        if(TypeUtil.doubleEquals(val,v=(long)val))
        {
          return uncheckedAbsoluteLastIndexOf(this.arr,0,size,v);
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
        if(val instanceof Long)
        {
          int modCount=this.modCount;
          try
          {
            return uncheckedAbsoluteLastIndexOf(this.arr,0,size,(long)(val));
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
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
    public void put(int index,long val)
    {
      if(index<0 || index>=this.size)
      {
        throw new IndexOutOfBoundsException("index="+index+"; size="+this.size);
      }
      arr[index]=val;
    }
    @Override
    public long getLong(int index)
    {
      if(index<0 || index>=this.size)
      {
        throw new IndexOutOfBoundsException("index="+index+"; size="+this.size);
      }
      return (long)arr[index];
    }
    @Override
    public Long get(int index)
    {
      return getLong(index);
    }
    @Override
    public void add(int index,long val)
    {
      //TODO
    }
    @Override
    public void add(int index,Long val)
    {
      //TODO
    }
    @Override
    public long removeLongAt(int index)
    {
      int size;
      if(index<0 || index>=(size=this.size))
      {
        throw new IndexOutOfBoundsException("index = "+index+"; size="+this.size);
      }
      ++this.modCount;
      final long[] arr;
      long ret=(long)(arr=this.arr)[index];
      ArrCopy.semicheckedSelfCopy(arr,index+1,index,(--size)-index);
      this.size=size;
      return ret;
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
    public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        {
          LongSortUtil.uncheckedDescendingSort(arr,0,size);
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
          LongSortUtil.uncheckedAscendingSort(arr,0,size);
        }
      }
    }
    @Override
    public OmniIterator.OfLong iterator()
    {
      //TODO
      return null;
    }
    @Override
    public long[] toLongArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final long[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new long[size],0,size);
        return copy;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override
    public Long[] toArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final Long[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new Long[size],0,size);
        return copy;
      }
      return OmniArray.OfLong.DEFAULT_BOXED_ARR;
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
    public float[] toFloatArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final float[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new float[size],0,size);
        return copy;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
  }
  public static class CheckedStackImpl extends LongArrSeq implements OmniStack.OfLong
  {
    private int uncheckedSearch (int bound
    ,long val
    )
    {
      final var arr=this.arr;
      for(int index=bound-1;;)
      {
        if(
        val==(arr[index])
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
    private boolean uncheckedRemoveVal (int size
    ,long val
    )
    {
      int modCount=this.modCount;
      final var arr=this.arr;
      for(int i=--size;;--i)
      {
        if(
        val==(arr[i])
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
    static int uncheckedToString(long[] arr,int begin,int end,char[] buffer)
    {
      int bufferOffset;
      for(bufferOffset=ToStringUtil.getStringLong(arr[end],buffer,1);begin!=end;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringLong(arr[--end],buffer,bufferOffset)){}
      return bufferOffset;
    }
    static void uncheckedToString(long[] arr,int begin,int end,ToStringUtil.OmniStringBuilder builder)
    {
      for(builder.uncheckedAppendLong(arr[end]);begin!=end;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendLong(arr[--end])){}
    }
    static  void uncheckedForEach(long[] arr,int begin,int end,LongConsumer action)
    {
      for(;;--end)
      {
        action.accept((long)arr[end]);
        if(begin==end)
        {
          return;
        }
      }
    }
    static int uncheckedHashCode(long[] arr,int begin,int end)
    {
      int hash=31+Long.hashCode(arr[end]);
      while(begin!=end)
      {
        hash=hash*31+Long.hashCode(arr[--end]);
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
    private CheckedStackImpl(int size,long[] arr)
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
        if(size>(Integer.MAX_VALUE/3))
        {
          throw new OutOfMemoryError();
        }
        final char[] buffer;
        if(size<=(OmniArray.MAX_ARR_SIZE/((22)<<1)))
        {
          (buffer=new char[size*(22)])[size=uncheckedToString(arr,0,size-1,buffer)]=']';
          buffer[0]='[';
          return new String(buffer,0,size+1);
        }
        else
        {
          final ToStringUtil.OmniStringBuilder builder;
          uncheckedToString(arr,0,size-1,builder=new ToStringUtil.OmniStringBuilder(1,new char[size<=(int)(OmniArray.MAX_ARR_SIZE/12.5f)?(size*12)+(size>>>1):OmniArray.MAX_ARR_SIZE]));
          builder.uncheckedAppendChar(']');
          (buffer=builder.buffer)[0]='[';
          return new String(buffer,0,builder.size);
        }
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
        final long[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new long[size],0,size);
        return new CheckedStackImpl(size,copy);
      }
      return new CheckedStackImpl();
    }
    @Override
    public void forEach(LongConsumer action)
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
    public void forEach(Consumer<? super Long> action)
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
    public boolean removeIf(LongPredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Long> filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(long val)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(Long val)
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
        return uncheckedRemoveVal(size,TypeUtil.castToLong(val));
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
        return uncheckedRemoveVal(size,(val));
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
        return uncheckedRemoveVal(size,(val));
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
        final long v;
        if(TypeUtil.floatEquals(val,v=(long)val))
        {
          return uncheckedRemoveVal(size,v);
        }
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
        final long v;
        if(TypeUtil.doubleEquals(val,v=(long)val))
        {
          return uncheckedRemoveVal(size,v);
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
        if(val instanceof Long)
        {
          {
            return uncheckedRemoveVal(size,(long)(val));
          }
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
        return OmniArray.OfLong.uncheckedcontains(this.arr,0,size-1,TypeUtil.castToLong(val));
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
        return OmniArray.OfLong.uncheckedcontains(this.arr,0,size-1,(val));
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
        return OmniArray.OfLong.uncheckedcontains(this.arr,0,size-1,(val));
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
        final long v;
        if(TypeUtil.floatEquals(val,v=(long)val))
        {
          return OmniArray.OfLong.uncheckedcontains(this.arr,0,size-1,v);
        }
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
        final long v;
        if(TypeUtil.doubleEquals(val,v=(long)val))
        {
          return OmniArray.OfLong.uncheckedcontains(this.arr,0,size-1,v);
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
        if(val instanceof Long)
        {
          {
            return OmniArray.OfLong.uncheckedcontains(this.arr,0,size-1,(long)(val));
          }
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
    public OmniIterator.OfLong iterator()
    {
      //TODO
      return null;
    }
    @Override
    public long[] toLongArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final long[] copy;
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new long[size],0,size);
        return copy;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override
    public Long[] toArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final Long[] copy;
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new Long[size],0,size);
        return copy;
      }
      return OmniArray.OfLong.DEFAULT_BOXED_ARR;
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
    public float[] toFloatArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final float[] copy;
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new float[size],0,size);
        return copy;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override
    public
    int
    search(boolean val)
    {
      int size;
      if((size=this.size)!=0)
      {
        return uncheckedSearch(size,TypeUtil.castToLong(val));
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
        return uncheckedSearch(size,(val));
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
        return uncheckedSearch(size,(val));
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
        final long v;
        if(TypeUtil.floatEquals(val,v=(long)val))
        {
          return uncheckedSearch(size,v);
        }
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
        final long v;
        if(TypeUtil.doubleEquals(val,v=(long)val))
        {
          return uncheckedSearch(size,v);
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
        if(val instanceof Long)
        {
          {
            return uncheckedSearch(size,(long)(val));
          }
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
    public long popLong()
    {
      int size;
      if((size=this.size)!=0)
      {
        long ret=(long)arr[--size];
        ++this.modCount;
        this.size=size;
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override
    public Long pop()
    {
      return popLong();
    }
    @Override
    public void push(long val)
    {
      //TODO
    }
    @Override
    public void push(Long val)
    {
      //TODO
    }
    @Override
    public long peekLong()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (long)((long)arr[size-1]);
      }
      return Long.MIN_VALUE;
    }
    @Override
    public long pollLong()
    {
      int size;
      if((size=this.size)!=0)
      {
        long ret=(long)((long)arr[--size]);
        ++this.modCount;
        this.size=size;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override
    public Long poll()
    {
      int size;
      if((size=this.size)!=0)
      {
        Long ret=(Long)((long)arr[--size]);
        ++this.modCount;
        this.size=size;
        return ret;
      }
      return null;
    }
    @Override
    public double pollDouble()
    {
      int size;
      if((size=this.size)!=0)
      {
        double ret=(double)((long)arr[--size]);
        ++this.modCount;
        this.size=size;
        return ret;
      }
      return Double.NaN;
    }
    @Override
    public float pollFloat()
    {
      int size;
      if((size=this.size)!=0)
      {
        float ret=(float)((long)arr[--size]);
        ++this.modCount;
        this.size=size;
        return ret;
      }
      return Float.NaN;
    }
    @Override
    public Long peek()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (Long)((long)arr[size-1]);
      }
      return null;
    }
    @Override
    public double peekDouble()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (double)((long)arr[size-1]);
      }
      return Double.NaN;
    }
    @Override
    public float peekFloat()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (float)((long)arr[size-1]);
      }
      return Float.NaN;
    }
  }
}
