package omni.impl.seq;
import omni.api.OmniCollection;
import omni.util.OmniArray;
import omni.api.OmniList;
import omni.api.OmniStack;
import java.util.function.Predicate;
import java.util.function.Consumer;
import omni.function.ShortPredicate;
import omni.function.ShortConsumer;
import java.util.Comparator;
import omni.function.ShortComparator;
import omni.util.ToStringUtil;
import omni.util.ArrCopy;
import omni.util.ShortSortUtil;
import omni.impl.CheckedCollection;
import omni.util.TypeUtil;
import java.util.NoSuchElementException;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import java.util.function.IntFunction;
public abstract class ShortArrSeq implements OmniCollection.OfShort
{
  transient int size;
  transient short[] arr;
  private ShortArrSeq()
  {
    super();
  }
  private ShortArrSeq(int initialCapacity)
  {
    super();
    switch(initialCapacity)
    { 
    default:
      this.arr=new short[initialCapacity];
      return;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfShort.DEFAULT_ARR;
    case 0:
    }
  }
  private ShortArrSeq(int size,short[] arr)
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
  public static class UncheckedListImpl extends ShortArrSeq implements OmniList.OfShort
  {
    private static int uncheckedAbsoluteIndexOf (short[] arr,int offset,int bound
    ,int val
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
    private static int uncheckedAbsoluteLastIndexOf (short[] arr,int offset,int bound
    ,int val
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
    private static int uncheckedRelativeIndexOf (short[] arr,int offset,int bound
    ,int val
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
    private static int uncheckedRelativeLastIndexOf (short[] arr,int offset,int bound
    ,int val
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
    ,int val
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
    static int uncheckedToString(short[] arr,int begin,int end,char[] buffer)
    {
      int bufferOffset;
      for(bufferOffset=ToStringUtil.getStringShort(arr[begin],buffer,1);begin!=end;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringShort(arr[++begin],buffer,bufferOffset)){}
      return bufferOffset;
    }
    static void uncheckedToString(short[] arr,int begin,int end,ToStringUtil.OmniStringBuilder builder)
    {
      for(builder.uncheckedAppendShort(arr[begin]);begin!=end;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendShort(arr[++begin])){}
    }
    static  void uncheckedForEach(short[] arr,int begin,int end,ShortConsumer action)
    {
      for(;;++begin)
      {
        action.accept((short)arr[begin]);
        if(begin==end)
        {
          return;
        }
      }
    }
    static int uncheckedHashCode(short[] arr,int begin,int end)
    {
      int hash=31+(arr[begin]);
      while(begin!=end)
      {
        hash=hash*31+(arr[++begin]);
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
    private UncheckedListImpl(int size,short[] arr)
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
        if(size<=(OmniArray.MAX_ARR_SIZE/((8)<<1)))
        {
          (buffer=new char[size*(8)])[size=uncheckedToString(arr,0,size-1,buffer)]=']';
          buffer[0]='[';
          return new String(buffer,0,size+1);
        }
        else
        {
          final ToStringUtil.OmniStringBuilder builder;
          uncheckedToString(arr,0,size-1,builder=new ToStringUtil.OmniStringBuilder(1,new char[size<=(int)(OmniArray.MAX_ARR_SIZE/5.5f)?(size*5)+(size>>>1):OmniArray.MAX_ARR_SIZE]));
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
        final short[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new short[size],0,size);
        return new UncheckedListImpl(size,copy);
      }
      return new UncheckedListImpl();
    }
    @Override
    public void forEach(ShortConsumer action)
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
    public void forEach(Consumer<? super Short> action)
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
    public boolean removeIf(ShortPredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Short> filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(short val)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(Short val)
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
        return uncheckedRemoveVal(size,(short)TypeUtil.castToByte(val));
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
        if(val==(short)val)
        {
          return uncheckedRemoveVal(size,val);
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
        final short v;
        if(val==(v=(short)val))
        {
          return uncheckedRemoveVal(size,v);
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
        final short v;
        if(val==(v=(short)val))
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
        final short v;
        if((v=(short)val)==val)
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
        if(val instanceof Short)
        {
          {
            return uncheckedRemoveVal(size,(short)(val));
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
        return OmniArray.OfShort.uncheckedcontains(this.arr,0,size-1,(short)TypeUtil.castToByte(val));
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
        if(val==(short)val)
        {
          return OmniArray.OfShort.uncheckedcontains(this.arr,0,size-1,val);
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
        final short v;
        if(val==(v=(short)val))
        {
          return OmniArray.OfShort.uncheckedcontains(this.arr,0,size-1,v);
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
        final short v;
        if(val==(v=(short)val))
        {
          return OmniArray.OfShort.uncheckedcontains(this.arr,0,size-1,v);
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
        final short v;
        if((v=(short)val)==val)
        {
          return OmniArray.OfShort.uncheckedcontains(this.arr,0,size-1,v);
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
        if(val instanceof Short)
        {
          {
            return OmniArray.OfShort.uncheckedcontains(this.arr,0,size-1,(short)(val));
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
        return uncheckedAbsoluteIndexOf(this.arr,0,size,(short)TypeUtil.castToByte(val));
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
        if(val==(short)val)
        {
          return uncheckedAbsoluteIndexOf(this.arr,0,size,val);
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
        final short v;
        if(val==(v=(short)val))
        {
          return uncheckedAbsoluteIndexOf(this.arr,0,size,v);
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
        final short v;
        if(val==(v=(short)val))
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
        final short v;
        if((v=(short)val)==val)
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
        if(val instanceof Short)
        {
          {
            return uncheckedAbsoluteIndexOf(this.arr,0,size,(short)(val));
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
        return uncheckedAbsoluteLastIndexOf(this.arr,0,size,(short)TypeUtil.castToByte(val));
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
        if(val==(short)val)
        {
          return uncheckedAbsoluteLastIndexOf(this.arr,0,size,val);
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
        final short v;
        if(val==(v=(short)val))
        {
          return uncheckedAbsoluteLastIndexOf(this.arr,0,size,v);
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
        final short v;
        if(val==(v=(short)val))
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
        final short v;
        if((v=(short)val)==val)
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
        if(val instanceof Short)
        {
          {
            return uncheckedAbsoluteLastIndexOf(this.arr,0,size,(short)(val));
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
    public void put(int index,short val)
    {
      arr[index]=val;
    }
    @Override
    public short getShort(int index)
    {
      return (short)arr[index];
    }
    @Override
    public Short get(int index)
    {
      return getShort(index);
    }
    @Override
    public void add(int index,short val)
    {
      //TODO
    }
    @Override
    public void add(int index,Short val)
    {
      //TODO
    }
    @Override
    public short removeShortAt(int index)
    {
      final short[] arr;
      short ret=(short)(arr=this.arr)[index];
      ArrCopy.semicheckedSelfCopy(arr,index+1,index,(--size)-index);
      return ret;
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
    public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        {
          ShortSortUtil.uncheckedDescendingSort(arr,0,size);
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
          ShortSortUtil.uncheckedAscendingSort(arr,0,size);
        }
      }
    }
    @Override
    public OmniIterator.OfShort iterator()
    {
      //TODO
      return null;
    }
    @Override
    public short[] toShortArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new short[size],0,size);
        return copy;
      }
      return OmniArray.OfShort.DEFAULT_ARR;
    }
    @Override
    public Short[] toArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final Short[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new Short[size],0,size);
        return copy;
      }
      return OmniArray.OfShort.DEFAULT_BOXED_ARR;
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
    public int[] toIntArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final int[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new int[size],0,size);
        return copy;
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
  }
  public static class UncheckedStackImpl extends ShortArrSeq implements OmniStack.OfShort
  {
    private int uncheckedSearch (int bound
    ,int val
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
    ,int val
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
    static int uncheckedToString(short[] arr,int begin,int end,char[] buffer)
    {
      int bufferOffset;
      for(bufferOffset=ToStringUtil.getStringShort(arr[end],buffer,1);begin!=end;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringShort(arr[--end],buffer,bufferOffset)){}
      return bufferOffset;
    }
    static void uncheckedToString(short[] arr,int begin,int end,ToStringUtil.OmniStringBuilder builder)
    {
      for(builder.uncheckedAppendShort(arr[end]);begin!=end;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendShort(arr[--end])){}
    }
    static  void uncheckedForEach(short[] arr,int begin,int end,ShortConsumer action)
    {
      for(;;--end)
      {
        action.accept((short)arr[end]);
        if(begin==end)
        {
          return;
        }
      }
    }
    static int uncheckedHashCode(short[] arr,int begin,int end)
    {
      int hash=31+(arr[end]);
      while(begin!=end)
      {
        hash=hash*31+(arr[--end]);
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
    private UncheckedStackImpl(int size,short[] arr)
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
        if(size<=(OmniArray.MAX_ARR_SIZE/((8)<<1)))
        {
          (buffer=new char[size*(8)])[size=uncheckedToString(arr,0,size-1,buffer)]=']';
          buffer[0]='[';
          return new String(buffer,0,size+1);
        }
        else
        {
          final ToStringUtil.OmniStringBuilder builder;
          uncheckedToString(arr,0,size-1,builder=new ToStringUtil.OmniStringBuilder(1,new char[size<=(int)(OmniArray.MAX_ARR_SIZE/5.5f)?(size*5)+(size>>>1):OmniArray.MAX_ARR_SIZE]));
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
        final short[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new short[size],0,size);
        return new UncheckedStackImpl(size,copy);
      }
      return new UncheckedStackImpl();
    }
    @Override
    public void forEach(ShortConsumer action)
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
    public void forEach(Consumer<? super Short> action)
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
    public boolean removeIf(ShortPredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Short> filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(short val)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(Short val)
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
        return uncheckedRemoveVal(size,(short)TypeUtil.castToByte(val));
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
        if(val==(short)val)
        {
          return uncheckedRemoveVal(size,val);
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
        final short v;
        if(val==(v=(short)val))
        {
          return uncheckedRemoveVal(size,v);
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
        final short v;
        if(val==(v=(short)val))
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
        final short v;
        if((v=(short)val)==val)
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
        if(val instanceof Short)
        {
          {
            return uncheckedRemoveVal(size,(short)(val));
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
        return OmniArray.OfShort.uncheckedcontains(this.arr,0,size-1,(short)TypeUtil.castToByte(val));
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
        if(val==(short)val)
        {
          return OmniArray.OfShort.uncheckedcontains(this.arr,0,size-1,val);
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
        final short v;
        if(val==(v=(short)val))
        {
          return OmniArray.OfShort.uncheckedcontains(this.arr,0,size-1,v);
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
        final short v;
        if(val==(v=(short)val))
        {
          return OmniArray.OfShort.uncheckedcontains(this.arr,0,size-1,v);
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
        final short v;
        if((v=(short)val)==val)
        {
          return OmniArray.OfShort.uncheckedcontains(this.arr,0,size-1,v);
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
        if(val instanceof Short)
        {
          {
            return OmniArray.OfShort.uncheckedcontains(this.arr,0,size-1,(short)(val));
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
    public OmniIterator.OfShort iterator()
    {
      //TODO
      return null;
    }
    @Override
    public short[] toShortArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short[] copy;
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new short[size],0,size);
        return copy;
      }
      return OmniArray.OfShort.DEFAULT_ARR;
    }
    @Override
    public Short[] toArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final Short[] copy;
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new Short[size],0,size);
        return copy;
      }
      return OmniArray.OfShort.DEFAULT_BOXED_ARR;
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
    public int[] toIntArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final int[] copy;
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new int[size],0,size);
        return copy;
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
    @Override
    public
    int
    search(boolean val)
    {
      int size;
      if((size=this.size)!=0)
      {
        return uncheckedSearch(size,(short)TypeUtil.castToByte(val));
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
        if(val==(short)val)
        {
          return uncheckedSearch(size,val);
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
        final short v;
        if(val==(v=(short)val))
        {
          return uncheckedSearch(size,v);
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
        final short v;
        if(val==(v=(short)val))
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
        final short v;
        if((v=(short)val)==val)
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
        if(val instanceof Short)
        {
          {
            return uncheckedSearch(size,(short)(val));
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
    public short popShort()
    {
      int size;
      if((size=this.size)!=0)
      {
        short ret=(short)arr[--size];
        this.size=size;
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override
    public Short pop()
    {
      return popShort();
    }
    @Override
    public void push(short val)
    {
      //TODO
    }
    @Override
    public void push(Short val)
    {
      //TODO
    }
    @Override
    public short peekShort()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (short)((short)arr[size-1]);
      }
      return Short.MIN_VALUE;
    }
    @Override
    public short pollShort()
    {
      int size;
      if((size=this.size)!=0)
      {
        short ret=(short)((short)arr[--size]);
        this.size=size;
        return ret;
      }
      return Short.MIN_VALUE;
    }
    @Override
    public Short poll()
    {
      int size;
      if((size=this.size)!=0)
      {
        Short ret=(Short)((short)arr[--size]);
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
        double ret=(double)((short)arr[--size]);
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
        float ret=(float)((short)arr[--size]);
        this.size=size;
        return ret;
      }
      return Float.NaN;
    }
    @Override
    public long pollLong()
    {
      int size;
      if((size=this.size)!=0)
      {
        long ret=(long)((short)arr[--size]);
        this.size=size;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override
    public int pollInt()
    {
      int size;
      if((size=this.size)!=0)
      {
        int ret=(int)((short)arr[--size]);
        this.size=size;
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override
    public Short peek()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (Short)((short)arr[size-1]);
      }
      return null;
    }
    @Override
    public double peekDouble()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (double)((short)arr[size-1]);
      }
      return Double.NaN;
    }
    @Override
    public float peekFloat()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (float)((short)arr[size-1]);
      }
      return Float.NaN;
    }
    @Override
    public long peekLong()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (long)((short)arr[size-1]);
      }
      return Long.MIN_VALUE;
    }
    @Override
    public int peekInt()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (int)((short)arr[size-1]);
      }
      return Integer.MIN_VALUE;
    }
  }
  public static class CheckedListImpl extends ShortArrSeq implements OmniList.OfShort
  {
    private static int uncheckedAbsoluteIndexOf (short[] arr,int offset,int bound
    ,int val
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
    private static int uncheckedAbsoluteLastIndexOf (short[] arr,int offset,int bound
    ,int val
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
    private static int uncheckedRelativeIndexOf (short[] arr,int offset,int bound
    ,int val
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
    private static int uncheckedRelativeLastIndexOf (short[] arr,int offset,int bound
    ,int val
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
    ,int val
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
    static int uncheckedToString(short[] arr,int begin,int end,char[] buffer)
    {
      int bufferOffset;
      for(bufferOffset=ToStringUtil.getStringShort(arr[begin],buffer,1);begin!=end;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringShort(arr[++begin],buffer,bufferOffset)){}
      return bufferOffset;
    }
    static void uncheckedToString(short[] arr,int begin,int end,ToStringUtil.OmniStringBuilder builder)
    {
      for(builder.uncheckedAppendShort(arr[begin]);begin!=end;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendShort(arr[++begin])){}
    }
    static  void uncheckedForEach(short[] arr,int begin,int end,ShortConsumer action)
    {
      for(;;++begin)
      {
        action.accept((short)arr[begin]);
        if(begin==end)
        {
          return;
        }
      }
    }
    static int uncheckedHashCode(short[] arr,int begin,int end)
    {
      int hash=31+(arr[begin]);
      while(begin!=end)
      {
        hash=hash*31+(arr[++begin]);
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
    private CheckedListImpl(int size,short[] arr)
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
        if(size<=(OmniArray.MAX_ARR_SIZE/((8)<<1)))
        {
          (buffer=new char[size*(8)])[size=uncheckedToString(arr,0,size-1,buffer)]=']';
          buffer[0]='[';
          return new String(buffer,0,size+1);
        }
        else
        {
          final ToStringUtil.OmniStringBuilder builder;
          uncheckedToString(arr,0,size-1,builder=new ToStringUtil.OmniStringBuilder(1,new char[size<=(int)(OmniArray.MAX_ARR_SIZE/5.5f)?(size*5)+(size>>>1):OmniArray.MAX_ARR_SIZE]));
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
        final short[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new short[size],0,size);
        return new CheckedListImpl(size,copy);
      }
      return new CheckedListImpl();
    }
    @Override
    public void forEach(ShortConsumer action)
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
    public void forEach(Consumer<? super Short> action)
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
    public boolean removeIf(ShortPredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Short> filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(short val)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(Short val)
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
        return uncheckedRemoveVal(size,(short)TypeUtil.castToByte(val));
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
        if(val==(short)val)
        {
          return uncheckedRemoveVal(size,val);
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
        final short v;
        if(val==(v=(short)val))
        {
          return uncheckedRemoveVal(size,v);
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
        final short v;
        if(val==(v=(short)val))
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
        final short v;
        if((v=(short)val)==val)
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
        if(val instanceof Short)
        {
          {
            return uncheckedRemoveVal(size,(short)(val));
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
        return OmniArray.OfShort.uncheckedcontains(this.arr,0,size-1,(short)TypeUtil.castToByte(val));
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
        if(val==(short)val)
        {
          return OmniArray.OfShort.uncheckedcontains(this.arr,0,size-1,val);
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
        final short v;
        if(val==(v=(short)val))
        {
          return OmniArray.OfShort.uncheckedcontains(this.arr,0,size-1,v);
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
        final short v;
        if(val==(v=(short)val))
        {
          return OmniArray.OfShort.uncheckedcontains(this.arr,0,size-1,v);
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
        final short v;
        if((v=(short)val)==val)
        {
          return OmniArray.OfShort.uncheckedcontains(this.arr,0,size-1,v);
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
        if(val instanceof Short)
        {
          {
            return OmniArray.OfShort.uncheckedcontains(this.arr,0,size-1,(short)(val));
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
        return uncheckedAbsoluteIndexOf(this.arr,0,size,(short)TypeUtil.castToByte(val));
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
        if(val==(short)val)
        {
          return uncheckedAbsoluteIndexOf(this.arr,0,size,val);
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
        final short v;
        if(val==(v=(short)val))
        {
          return uncheckedAbsoluteIndexOf(this.arr,0,size,v);
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
        final short v;
        if(val==(v=(short)val))
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
        final short v;
        if((v=(short)val)==val)
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
        if(val instanceof Short)
        {
          int modCount=this.modCount;
          try
          {
            return uncheckedAbsoluteIndexOf(this.arr,0,size,(short)(val));
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
        return uncheckedAbsoluteLastIndexOf(this.arr,0,size,(short)TypeUtil.castToByte(val));
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
        if(val==(short)val)
        {
          return uncheckedAbsoluteLastIndexOf(this.arr,0,size,val);
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
        final short v;
        if(val==(v=(short)val))
        {
          return uncheckedAbsoluteLastIndexOf(this.arr,0,size,v);
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
        final short v;
        if(val==(v=(short)val))
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
        final short v;
        if((v=(short)val)==val)
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
        if(val instanceof Short)
        {
          int modCount=this.modCount;
          try
          {
            return uncheckedAbsoluteLastIndexOf(this.arr,0,size,(short)(val));
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
    public void put(int index,short val)
    {
      if(index<0 || index>=this.size)
      {
        throw new IndexOutOfBoundsException("index="+index+"; size="+this.size);
      }
      arr[index]=val;
    }
    @Override
    public short getShort(int index)
    {
      if(index<0 || index>=this.size)
      {
        throw new IndexOutOfBoundsException("index="+index+"; size="+this.size);
      }
      return (short)arr[index];
    }
    @Override
    public Short get(int index)
    {
      return getShort(index);
    }
    @Override
    public void add(int index,short val)
    {
      //TODO
    }
    @Override
    public void add(int index,Short val)
    {
      //TODO
    }
    @Override
    public short removeShortAt(int index)
    {
      int size;
      if(index<0 || index>=(size=this.size))
      {
        throw new IndexOutOfBoundsException("index = "+index+"; size="+this.size);
      }
      ++this.modCount;
      final short[] arr;
      short ret=(short)(arr=this.arr)[index];
      ArrCopy.semicheckedSelfCopy(arr,index+1,index,(--size)-index);
      this.size=size;
      return ret;
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
    public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        {
          ShortSortUtil.uncheckedDescendingSort(arr,0,size);
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
          ShortSortUtil.uncheckedAscendingSort(arr,0,size);
        }
      }
    }
    @Override
    public OmniIterator.OfShort iterator()
    {
      //TODO
      return null;
    }
    @Override
    public short[] toShortArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new short[size],0,size);
        return copy;
      }
      return OmniArray.OfShort.DEFAULT_ARR;
    }
    @Override
    public Short[] toArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final Short[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new Short[size],0,size);
        return copy;
      }
      return OmniArray.OfShort.DEFAULT_BOXED_ARR;
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
    public int[] toIntArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final int[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new int[size],0,size);
        return copy;
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
  }
  public static class CheckedStackImpl extends ShortArrSeq implements OmniStack.OfShort
  {
    private int uncheckedSearch (int bound
    ,int val
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
    ,int val
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
    static int uncheckedToString(short[] arr,int begin,int end,char[] buffer)
    {
      int bufferOffset;
      for(bufferOffset=ToStringUtil.getStringShort(arr[end],buffer,1);begin!=end;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringShort(arr[--end],buffer,bufferOffset)){}
      return bufferOffset;
    }
    static void uncheckedToString(short[] arr,int begin,int end,ToStringUtil.OmniStringBuilder builder)
    {
      for(builder.uncheckedAppendShort(arr[end]);begin!=end;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendShort(arr[--end])){}
    }
    static  void uncheckedForEach(short[] arr,int begin,int end,ShortConsumer action)
    {
      for(;;--end)
      {
        action.accept((short)arr[end]);
        if(begin==end)
        {
          return;
        }
      }
    }
    static int uncheckedHashCode(short[] arr,int begin,int end)
    {
      int hash=31+(arr[end]);
      while(begin!=end)
      {
        hash=hash*31+(arr[--end]);
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
    private CheckedStackImpl(int size,short[] arr)
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
        if(size<=(OmniArray.MAX_ARR_SIZE/((8)<<1)))
        {
          (buffer=new char[size*(8)])[size=uncheckedToString(arr,0,size-1,buffer)]=']';
          buffer[0]='[';
          return new String(buffer,0,size+1);
        }
        else
        {
          final ToStringUtil.OmniStringBuilder builder;
          uncheckedToString(arr,0,size-1,builder=new ToStringUtil.OmniStringBuilder(1,new char[size<=(int)(OmniArray.MAX_ARR_SIZE/5.5f)?(size*5)+(size>>>1):OmniArray.MAX_ARR_SIZE]));
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
        final short[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new short[size],0,size);
        return new CheckedStackImpl(size,copy);
      }
      return new CheckedStackImpl();
    }
    @Override
    public void forEach(ShortConsumer action)
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
    public void forEach(Consumer<? super Short> action)
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
    public boolean removeIf(ShortPredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Short> filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(short val)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(Short val)
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
        return uncheckedRemoveVal(size,(short)TypeUtil.castToByte(val));
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
        if(val==(short)val)
        {
          return uncheckedRemoveVal(size,val);
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
        final short v;
        if(val==(v=(short)val))
        {
          return uncheckedRemoveVal(size,v);
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
        final short v;
        if(val==(v=(short)val))
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
        final short v;
        if((v=(short)val)==val)
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
        if(val instanceof Short)
        {
          {
            return uncheckedRemoveVal(size,(short)(val));
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
        return OmniArray.OfShort.uncheckedcontains(this.arr,0,size-1,(short)TypeUtil.castToByte(val));
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
        if(val==(short)val)
        {
          return OmniArray.OfShort.uncheckedcontains(this.arr,0,size-1,val);
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
        final short v;
        if(val==(v=(short)val))
        {
          return OmniArray.OfShort.uncheckedcontains(this.arr,0,size-1,v);
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
        final short v;
        if(val==(v=(short)val))
        {
          return OmniArray.OfShort.uncheckedcontains(this.arr,0,size-1,v);
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
        final short v;
        if((v=(short)val)==val)
        {
          return OmniArray.OfShort.uncheckedcontains(this.arr,0,size-1,v);
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
        if(val instanceof Short)
        {
          {
            return OmniArray.OfShort.uncheckedcontains(this.arr,0,size-1,(short)(val));
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
    public OmniIterator.OfShort iterator()
    {
      //TODO
      return null;
    }
    @Override
    public short[] toShortArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short[] copy;
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new short[size],0,size);
        return copy;
      }
      return OmniArray.OfShort.DEFAULT_ARR;
    }
    @Override
    public Short[] toArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final Short[] copy;
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new Short[size],0,size);
        return copy;
      }
      return OmniArray.OfShort.DEFAULT_BOXED_ARR;
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
    public int[] toIntArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final int[] copy;
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new int[size],0,size);
        return copy;
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
    @Override
    public
    int
    search(boolean val)
    {
      int size;
      if((size=this.size)!=0)
      {
        return uncheckedSearch(size,(short)TypeUtil.castToByte(val));
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
        if(val==(short)val)
        {
          return uncheckedSearch(size,val);
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
        final short v;
        if(val==(v=(short)val))
        {
          return uncheckedSearch(size,v);
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
        final short v;
        if(val==(v=(short)val))
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
        final short v;
        if((v=(short)val)==val)
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
        if(val instanceof Short)
        {
          {
            return uncheckedSearch(size,(short)(val));
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
    public short popShort()
    {
      int size;
      if((size=this.size)!=0)
      {
        short ret=(short)arr[--size];
        ++this.modCount;
        this.size=size;
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override
    public Short pop()
    {
      return popShort();
    }
    @Override
    public void push(short val)
    {
      //TODO
    }
    @Override
    public void push(Short val)
    {
      //TODO
    }
    @Override
    public short peekShort()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (short)((short)arr[size-1]);
      }
      return Short.MIN_VALUE;
    }
    @Override
    public short pollShort()
    {
      int size;
      if((size=this.size)!=0)
      {
        short ret=(short)((short)arr[--size]);
        ++this.modCount;
        this.size=size;
        return ret;
      }
      return Short.MIN_VALUE;
    }
    @Override
    public Short poll()
    {
      int size;
      if((size=this.size)!=0)
      {
        Short ret=(Short)((short)arr[--size]);
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
        double ret=(double)((short)arr[--size]);
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
        float ret=(float)((short)arr[--size]);
        ++this.modCount;
        this.size=size;
        return ret;
      }
      return Float.NaN;
    }
    @Override
    public long pollLong()
    {
      int size;
      if((size=this.size)!=0)
      {
        long ret=(long)((short)arr[--size]);
        ++this.modCount;
        this.size=size;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override
    public int pollInt()
    {
      int size;
      if((size=this.size)!=0)
      {
        int ret=(int)((short)arr[--size]);
        ++this.modCount;
        this.size=size;
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override
    public Short peek()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (Short)((short)arr[size-1]);
      }
      return null;
    }
    @Override
    public double peekDouble()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (double)((short)arr[size-1]);
      }
      return Double.NaN;
    }
    @Override
    public float peekFloat()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (float)((short)arr[size-1]);
      }
      return Float.NaN;
    }
    @Override
    public long peekLong()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (long)((short)arr[size-1]);
      }
      return Long.MIN_VALUE;
    }
    @Override
    public int peekInt()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (int)((short)arr[size-1]);
      }
      return Integer.MIN_VALUE;
    }
  }
}
