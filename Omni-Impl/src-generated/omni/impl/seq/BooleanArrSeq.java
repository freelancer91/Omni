package omni.impl.seq;
import omni.api.OmniCollection;
import omni.util.OmniArray;
import omni.api.OmniList;
import omni.api.OmniStack;
import java.util.function.Predicate;
import java.util.function.Consumer;
import omni.function.BooleanPredicate;
import omni.function.BooleanConsumer;
import java.util.Comparator;
import omni.function.BooleanComparator;
import omni.util.ToStringUtil;
import omni.util.ArrCopy;
import omni.util.BooleanSortUtil;
import omni.impl.CheckedCollection;
import omni.util.TypeUtil;
import java.util.NoSuchElementException;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import java.util.function.IntFunction;
public abstract class BooleanArrSeq implements OmniCollection.OfBoolean
{
  transient int size;
  transient boolean[] arr;
  private BooleanArrSeq()
  {
    super();
  }
  private BooleanArrSeq(int initialCapacity)
  {
    super();
    switch(initialCapacity)
    { 
    default:
      this.arr=new boolean[initialCapacity];
      return;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfBoolean.DEFAULT_ARR;
    case 0:
    }
  }
  private BooleanArrSeq(int size,boolean[] arr)
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
  public static class UncheckedListImpl extends BooleanArrSeq implements OmniList.OfBoolean
  {
    private static int uncheckedAbsoluteIndexOf (boolean[] arr,int offset,int bound
    ,boolean val
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
    private static int uncheckedAbsoluteLastIndexOf (boolean[] arr,int offset,int bound
    ,boolean val
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
    private static int uncheckedRelativeIndexOf (boolean[] arr,int offset,int bound
    ,boolean val
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
    private static int uncheckedRelativeLastIndexOf (boolean[] arr,int offset,int bound
    ,boolean val
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
    ,boolean val
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
    static int uncheckedToString(boolean[] arr,int begin,int end,char[] buffer)
    {
      int bufferOffset;
      for(bufferOffset=ToStringUtil.getStringBoolean(arr[begin],buffer,1);begin!=end;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringBoolean(arr[++begin],buffer,bufferOffset)){}
      return bufferOffset;
    }
    static void uncheckedToString(boolean[] arr,int begin,int end,ToStringUtil.OmniStringBuilder builder)
    {
      for(builder.uncheckedAppendBoolean(arr[begin]);begin!=end;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendBoolean(arr[++begin])){}
    }
    static  void uncheckedForEach(boolean[] arr,int begin,int end,BooleanConsumer action)
    {
      for(;;++begin)
      {
        action.accept((boolean)arr[begin]);
        if(begin==end)
        {
          return;
        }
      }
    }
    static int uncheckedHashCode(boolean[] arr,int begin,int end)
    {
      int hash=31+Boolean.hashCode(arr[begin]);
      while(begin!=end)
      {
        hash=hash*31+Boolean.hashCode(arr[++begin]);
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
    private UncheckedListImpl(int size,boolean[] arr)
    {
      super(size,arr);
    }
    @Override
    public String toString()
    {
      int size;
      if((size=this.size)!=0)
      {
        if(size>(Integer.MAX_VALUE/6))
        {
          throw new OutOfMemoryError();
        }
        final char[] buffer;
        if(size<=(OmniArray.MAX_ARR_SIZE/((7)<<1)))
        {
          (buffer=new char[size*(7)])[size=uncheckedToString(arr,0,size-1,buffer)]=']';
          buffer[0]='[';
          return new String(buffer,0,size+1);
        }
        else
        {
          final ToStringUtil.OmniStringBuilder builder;
          uncheckedToString(arr,0,size-1,builder=new ToStringUtil.OmniStringBuilder(1,new char[size<=(int)(OmniArray.MAX_ARR_SIZE/6.5f)?(size*6)+(size>>>1):OmniArray.MAX_ARR_SIZE]));
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
        final boolean[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new boolean[size],0,size);
        return new UncheckedListImpl(size,copy);
      }
      return new UncheckedListImpl();
    }
    @Override
    public void forEach(BooleanConsumer action)
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
    public void forEach(Consumer<? super Boolean> action)
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
    public boolean removeIf(BooleanPredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Boolean> filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(boolean val)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(Boolean val)
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
        return uncheckedRemoveVal(size,(val));
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
        final boolean v;
        switch(val)
        {
          default:
        return false;
          case 0:
            v=false;
            break;
          case 1:
            v=true;
        }
        return uncheckedRemoveVal(size,v);
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
        final boolean v;
        if(val==0L)
        {
          v=false;
        }
        else if(val==1L)
        {
          v=true;
        }
        else
        {
          return false;
        }
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
        final boolean v;
        switch(Float.floatToRawIntBits(val))
        {
          default:
            return false;
          case 0:
          case Integer.MIN_VALUE:
            v=false;
            break;
          case 1:
            v=true;
        }
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
        final boolean v;
        final long bits;
        if((bits=Double.doubleToRawLongBits(val))==0L || bits==Long.MIN_VALUE)
        {
          v=false;
        }
        else if(bits==1)
        {
          v=true;
        }
        else
        {
          return false;
        }
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
        if(val instanceof Boolean)
        {
          {
            return uncheckedRemoveVal(size,(boolean)(val));
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
        return OmniArray.OfBoolean.uncheckedcontains(this.arr,0,size-1,(val));
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
        final boolean v;
        switch(val)
        {
          default:
        return false;
          case 0:
            v=false;
            break;
          case 1:
            v=true;
        }
        return OmniArray.OfBoolean.uncheckedcontains(this.arr,0,size-1,v);
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
        final boolean v;
        if(val==0L)
        {
          v=false;
        }
        else if(val==1L)
        {
          v=true;
        }
        else
        {
          return false;
        }
        {
          return OmniArray.OfBoolean.uncheckedcontains(this.arr,0,size-1,v);
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
        final boolean v;
        switch(Float.floatToRawIntBits(val))
        {
          default:
            return false;
          case 0:
          case Integer.MIN_VALUE:
            v=false;
            break;
          case 1:
            v=true;
        }
        {
          return OmniArray.OfBoolean.uncheckedcontains(this.arr,0,size-1,v);
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
        final boolean v;
        final long bits;
        if((bits=Double.doubleToRawLongBits(val))==0L || bits==Long.MIN_VALUE)
        {
          v=false;
        }
        else if(bits==1)
        {
          v=true;
        }
        else
        {
          return false;
        }
        {
          return OmniArray.OfBoolean.uncheckedcontains(this.arr,0,size-1,v);
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
        if(val instanceof Boolean)
        {
          {
            return OmniArray.OfBoolean.uncheckedcontains(this.arr,0,size-1,(boolean)(val));
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
        return uncheckedAbsoluteIndexOf(this.arr,0,size,(val));
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
        final boolean v;
        switch(val)
        {
          default:
        return -1;
          case 0:
            v=false;
            break;
          case 1:
            v=true;
        }
        return uncheckedAbsoluteIndexOf(this.arr,0,size,v);
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
        final boolean v;
        if(val==0L)
        {
          v=false;
        }
        else if(val==1L)
        {
          v=true;
        }
        else
        {
          return -1;
        }
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
        final boolean v;
        switch(Float.floatToRawIntBits(val))
        {
          default:
            return -1;
          case 0:
          case Integer.MIN_VALUE:
            v=false;
            break;
          case 1:
            v=true;
        }
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
        final boolean v;
        final long bits;
        if((bits=Double.doubleToRawLongBits(val))==0L || bits==Long.MIN_VALUE)
        {
          v=false;
        }
        else if(bits==1)
        {
          v=true;
        }
        else
        {
          return -1;
        }
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
        if(val instanceof Boolean)
        {
          {
            return uncheckedAbsoluteIndexOf(this.arr,0,size,(boolean)(val));
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
        return uncheckedAbsoluteLastIndexOf(this.arr,0,size,(val));
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
        final boolean v;
        switch(val)
        {
          default:
        return -1;
          case 0:
            v=false;
            break;
          case 1:
            v=true;
        }
        return uncheckedAbsoluteLastIndexOf(this.arr,0,size,v);
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
        final boolean v;
        if(val==0L)
        {
          v=false;
        }
        else if(val==1L)
        {
          v=true;
        }
        else
        {
          return -1;
        }
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
        final boolean v;
        switch(Float.floatToRawIntBits(val))
        {
          default:
            return -1;
          case 0:
          case Integer.MIN_VALUE:
            v=false;
            break;
          case 1:
            v=true;
        }
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
        final boolean v;
        final long bits;
        if((bits=Double.doubleToRawLongBits(val))==0L || bits==Long.MIN_VALUE)
        {
          v=false;
        }
        else if(bits==1)
        {
          v=true;
        }
        else
        {
          return -1;
        }
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
        if(val instanceof Boolean)
        {
          {
            return uncheckedAbsoluteLastIndexOf(this.arr,0,size,(boolean)(val));
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
    public void put(int index,boolean val)
    {
      arr[index]=val;
    }
    @Override
    public boolean getBoolean(int index)
    {
      return (boolean)arr[index];
    }
    @Override
    public Boolean get(int index)
    {
      return getBoolean(index);
    }
    @Override
    public void add(int index,boolean val)
    {
      //TODO
    }
    @Override
    public void add(int index,Boolean val)
    {
      //TODO
    }
    @Override
    public boolean removeBooleanAt(int index)
    {
      final boolean[] arr;
      boolean ret=(boolean)(arr=this.arr)[index];
      ArrCopy.semicheckedSelfCopy(arr,index+1,index,(--size)-index);
      return ret;
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
    public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        {
          BooleanSortUtil.uncheckedDescendingSort(arr,0,size);
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
          BooleanSortUtil.uncheckedAscendingSort(arr,0,size);
        }
      }
    }
    @Override
    public OmniIterator.OfBoolean iterator()
    {
      //TODO
      return null;
    }
    @Override
    public boolean[] toBooleanArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final boolean[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new boolean[size],0,size);
        return copy;
      }
      return OmniArray.OfBoolean.DEFAULT_ARR;
    }
    @Override
    public Boolean[] toArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final Boolean[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new Boolean[size],0,size);
        return copy;
      }
      return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
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
    public char[] toCharArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new char[size],0,size);
        return copy;
      }
      return OmniArray.OfChar.DEFAULT_ARR;
    }
    @Override
    public byte[] toByteArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final byte[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new byte[size],0,size);
        return copy;
      }
      return OmniArray.OfByte.DEFAULT_ARR;
    }
  }
  public static class UncheckedStackImpl extends BooleanArrSeq implements OmniStack.OfBoolean
  {
    private int uncheckedSearch (int bound
    ,boolean val
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
    ,boolean val
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
    static int uncheckedToString(boolean[] arr,int begin,int end,char[] buffer)
    {
      int bufferOffset;
      for(bufferOffset=ToStringUtil.getStringBoolean(arr[end],buffer,1);begin!=end;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringBoolean(arr[--end],buffer,bufferOffset)){}
      return bufferOffset;
    }
    static void uncheckedToString(boolean[] arr,int begin,int end,ToStringUtil.OmniStringBuilder builder)
    {
      for(builder.uncheckedAppendBoolean(arr[end]);begin!=end;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendBoolean(arr[--end])){}
    }
    static  void uncheckedForEach(boolean[] arr,int begin,int end,BooleanConsumer action)
    {
      for(;;--end)
      {
        action.accept((boolean)arr[end]);
        if(begin==end)
        {
          return;
        }
      }
    }
    static int uncheckedHashCode(boolean[] arr,int begin,int end)
    {
      int hash=31+Boolean.hashCode(arr[end]);
      while(begin!=end)
      {
        hash=hash*31+Boolean.hashCode(arr[--end]);
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
    private UncheckedStackImpl(int size,boolean[] arr)
    {
      super(size,arr);
    }
    @Override
    public String toString()
    {
      int size;
      if((size=this.size)!=0)
      {
        if(size>(Integer.MAX_VALUE/6))
        {
          throw new OutOfMemoryError();
        }
        final char[] buffer;
        if(size<=(OmniArray.MAX_ARR_SIZE/((7)<<1)))
        {
          (buffer=new char[size*(7)])[size=uncheckedToString(arr,0,size-1,buffer)]=']';
          buffer[0]='[';
          return new String(buffer,0,size+1);
        }
        else
        {
          final ToStringUtil.OmniStringBuilder builder;
          uncheckedToString(arr,0,size-1,builder=new ToStringUtil.OmniStringBuilder(1,new char[size<=(int)(OmniArray.MAX_ARR_SIZE/6.5f)?(size*6)+(size>>>1):OmniArray.MAX_ARR_SIZE]));
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
        final boolean[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new boolean[size],0,size);
        return new UncheckedStackImpl(size,copy);
      }
      return new UncheckedStackImpl();
    }
    @Override
    public void forEach(BooleanConsumer action)
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
    public void forEach(Consumer<? super Boolean> action)
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
    public boolean removeIf(BooleanPredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Boolean> filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(boolean val)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(Boolean val)
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
        return uncheckedRemoveVal(size,(val));
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
        final boolean v;
        switch(val)
        {
          default:
        return false;
          case 0:
            v=false;
            break;
          case 1:
            v=true;
        }
        return uncheckedRemoveVal(size,v);
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
        final boolean v;
        if(val==0L)
        {
          v=false;
        }
        else if(val==1L)
        {
          v=true;
        }
        else
        {
          return false;
        }
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
        final boolean v;
        switch(Float.floatToRawIntBits(val))
        {
          default:
            return false;
          case 0:
          case Integer.MIN_VALUE:
            v=false;
            break;
          case 1:
            v=true;
        }
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
        final boolean v;
        final long bits;
        if((bits=Double.doubleToRawLongBits(val))==0L || bits==Long.MIN_VALUE)
        {
          v=false;
        }
        else if(bits==1)
        {
          v=true;
        }
        else
        {
          return false;
        }
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
        if(val instanceof Boolean)
        {
          {
            return uncheckedRemoveVal(size,(boolean)(val));
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
        return OmniArray.OfBoolean.uncheckedcontains(this.arr,0,size-1,(val));
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
        final boolean v;
        switch(val)
        {
          default:
        return false;
          case 0:
            v=false;
            break;
          case 1:
            v=true;
        }
        return OmniArray.OfBoolean.uncheckedcontains(this.arr,0,size-1,v);
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
        final boolean v;
        if(val==0L)
        {
          v=false;
        }
        else if(val==1L)
        {
          v=true;
        }
        else
        {
          return false;
        }
        {
          return OmniArray.OfBoolean.uncheckedcontains(this.arr,0,size-1,v);
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
        final boolean v;
        switch(Float.floatToRawIntBits(val))
        {
          default:
            return false;
          case 0:
          case Integer.MIN_VALUE:
            v=false;
            break;
          case 1:
            v=true;
        }
        {
          return OmniArray.OfBoolean.uncheckedcontains(this.arr,0,size-1,v);
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
        final boolean v;
        final long bits;
        if((bits=Double.doubleToRawLongBits(val))==0L || bits==Long.MIN_VALUE)
        {
          v=false;
        }
        else if(bits==1)
        {
          v=true;
        }
        else
        {
          return false;
        }
        {
          return OmniArray.OfBoolean.uncheckedcontains(this.arr,0,size-1,v);
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
        if(val instanceof Boolean)
        {
          {
            return OmniArray.OfBoolean.uncheckedcontains(this.arr,0,size-1,(boolean)(val));
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
    public OmniIterator.OfBoolean iterator()
    {
      //TODO
      return null;
    }
    @Override
    public boolean[] toBooleanArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final boolean[] copy;
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new boolean[size],0,size);
        return copy;
      }
      return OmniArray.OfBoolean.DEFAULT_ARR;
    }
    @Override
    public Boolean[] toArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final Boolean[] copy;
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new Boolean[size],0,size);
        return copy;
      }
      return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
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
    public char[] toCharArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char[] copy;
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new char[size],0,size);
        return copy;
      }
      return OmniArray.OfChar.DEFAULT_ARR;
    }
    @Override
    public byte[] toByteArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final byte[] copy;
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new byte[size],0,size);
        return copy;
      }
      return OmniArray.OfByte.DEFAULT_ARR;
    }
    @Override
    public
    int
    search(boolean val)
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
    search(int val)
    {
      int size;
      if((size=this.size)!=0)
      {
        final boolean v;
        switch(val)
        {
          default:
        return -1;
          case 0:
            v=false;
            break;
          case 1:
            v=true;
        }
        return uncheckedSearch(size,v);
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
        final boolean v;
        if(val==0L)
        {
          v=false;
        }
        else if(val==1L)
        {
          v=true;
        }
        else
        {
          return -1;
        }
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
        final boolean v;
        switch(Float.floatToRawIntBits(val))
        {
          default:
            return -1;
          case 0:
          case Integer.MIN_VALUE:
            v=false;
            break;
          case 1:
            v=true;
        }
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
        final boolean v;
        final long bits;
        if((bits=Double.doubleToRawLongBits(val))==0L || bits==Long.MIN_VALUE)
        {
          v=false;
        }
        else if(bits==1)
        {
          v=true;
        }
        else
        {
          return -1;
        }
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
        if(val instanceof Boolean)
        {
          {
            return uncheckedSearch(size,(boolean)(val));
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
    public boolean popBoolean()
    {
      int size;
      if((size=this.size)!=0)
      {
        boolean ret=(boolean)arr[--size];
        this.size=size;
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override
    public Boolean pop()
    {
      return popBoolean();
    }
    @Override
    public void push(boolean val)
    {
      //TODO
    }
    @Override
    public void push(Boolean val)
    {
      //TODO
    }
    @Override
    public boolean peekBoolean()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (boolean)((boolean)arr[size-1]);
      }
      return false;
    }
    @Override
    public boolean pollBoolean()
    {
      int size;
      if((size=this.size)!=0)
      {
        boolean ret=(boolean)((boolean)arr[--size]);
        this.size=size;
        return ret;
      }
      return false;
    }
    @Override
    public Boolean poll()
    {
      int size;
      if((size=this.size)!=0)
      {
        Boolean ret=(Boolean)((boolean)arr[--size]);
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
        double ret=TypeUtil.castToDouble((boolean)arr[--size]);
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
        float ret=TypeUtil.castToFloat((boolean)arr[--size]);
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
        long ret=TypeUtil.castToLong((boolean)arr[--size]);
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
        int ret=(int)TypeUtil.castToByte((boolean)arr[--size]);
        this.size=size;
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override
    public short pollShort()
    {
      int size;
      if((size=this.size)!=0)
      {
        short ret=(short)TypeUtil.castToByte((boolean)arr[--size]);
        this.size=size;
        return ret;
      }
      return Short.MIN_VALUE;
    }
    @Override
    public char pollChar()
    {
      int size;
      if((size=this.size)!=0)
      {
        char ret=TypeUtil.castToChar((boolean)arr[--size]);
        this.size=size;
        return ret;
      }
      return Character.MIN_VALUE;
    }
    @Override
    public byte pollByte()
    {
      int size;
      if((size=this.size)!=0)
      {
        byte ret=TypeUtil.castToByte((boolean)arr[--size]);
        this.size=size;
        return ret;
      }
      return Byte.MIN_VALUE;
    }
    @Override
    public Boolean peek()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (Boolean)((boolean)arr[size-1]);
      }
      return null;
    }
    @Override
    public double peekDouble()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return TypeUtil.castToDouble((boolean)arr[size-1]);
      }
      return Double.NaN;
    }
    @Override
    public float peekFloat()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return TypeUtil.castToFloat((boolean)arr[size-1]);
      }
      return Float.NaN;
    }
    @Override
    public long peekLong()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return TypeUtil.castToLong((boolean)arr[size-1]);
      }
      return Long.MIN_VALUE;
    }
    @Override
    public int peekInt()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (int)TypeUtil.castToByte((boolean)arr[size-1]);
      }
      return Integer.MIN_VALUE;
    }
    @Override
    public short peekShort()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (short)TypeUtil.castToByte((boolean)arr[size-1]);
      }
      return Short.MIN_VALUE;
    }
    @Override
    public char peekChar()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return TypeUtil.castToChar((boolean)arr[size-1]);
      }
      return Character.MIN_VALUE;
    }
    @Override
    public byte peekByte()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return TypeUtil.castToByte((boolean)arr[size-1]);
      }
      return Byte.MIN_VALUE;
    }
  }
  public static class CheckedListImpl extends BooleanArrSeq implements OmniList.OfBoolean
  {
    private static int uncheckedAbsoluteIndexOf (boolean[] arr,int offset,int bound
    ,boolean val
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
    private static int uncheckedAbsoluteLastIndexOf (boolean[] arr,int offset,int bound
    ,boolean val
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
    private static int uncheckedRelativeIndexOf (boolean[] arr,int offset,int bound
    ,boolean val
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
    private static int uncheckedRelativeLastIndexOf (boolean[] arr,int offset,int bound
    ,boolean val
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
    ,boolean val
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
    static int uncheckedToString(boolean[] arr,int begin,int end,char[] buffer)
    {
      int bufferOffset;
      for(bufferOffset=ToStringUtil.getStringBoolean(arr[begin],buffer,1);begin!=end;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringBoolean(arr[++begin],buffer,bufferOffset)){}
      return bufferOffset;
    }
    static void uncheckedToString(boolean[] arr,int begin,int end,ToStringUtil.OmniStringBuilder builder)
    {
      for(builder.uncheckedAppendBoolean(arr[begin]);begin!=end;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendBoolean(arr[++begin])){}
    }
    static  void uncheckedForEach(boolean[] arr,int begin,int end,BooleanConsumer action)
    {
      for(;;++begin)
      {
        action.accept((boolean)arr[begin]);
        if(begin==end)
        {
          return;
        }
      }
    }
    static int uncheckedHashCode(boolean[] arr,int begin,int end)
    {
      int hash=31+Boolean.hashCode(arr[begin]);
      while(begin!=end)
      {
        hash=hash*31+Boolean.hashCode(arr[++begin]);
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
    private CheckedListImpl(int size,boolean[] arr)
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
        if(size>(Integer.MAX_VALUE/6))
        {
          throw new OutOfMemoryError();
        }
        final char[] buffer;
        if(size<=(OmniArray.MAX_ARR_SIZE/((7)<<1)))
        {
          (buffer=new char[size*(7)])[size=uncheckedToString(arr,0,size-1,buffer)]=']';
          buffer[0]='[';
          return new String(buffer,0,size+1);
        }
        else
        {
          final ToStringUtil.OmniStringBuilder builder;
          uncheckedToString(arr,0,size-1,builder=new ToStringUtil.OmniStringBuilder(1,new char[size<=(int)(OmniArray.MAX_ARR_SIZE/6.5f)?(size*6)+(size>>>1):OmniArray.MAX_ARR_SIZE]));
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
        final boolean[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new boolean[size],0,size);
        return new CheckedListImpl(size,copy);
      }
      return new CheckedListImpl();
    }
    @Override
    public void forEach(BooleanConsumer action)
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
    public void forEach(Consumer<? super Boolean> action)
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
    public boolean removeIf(BooleanPredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Boolean> filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(boolean val)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(Boolean val)
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
        return uncheckedRemoveVal(size,(val));
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
        final boolean v;
        switch(val)
        {
          default:
        return false;
          case 0:
            v=false;
            break;
          case 1:
            v=true;
        }
        return uncheckedRemoveVal(size,v);
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
        final boolean v;
        if(val==0L)
        {
          v=false;
        }
        else if(val==1L)
        {
          v=true;
        }
        else
        {
          return false;
        }
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
        final boolean v;
        switch(Float.floatToRawIntBits(val))
        {
          default:
            return false;
          case 0:
          case Integer.MIN_VALUE:
            v=false;
            break;
          case 1:
            v=true;
        }
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
        final boolean v;
        final long bits;
        if((bits=Double.doubleToRawLongBits(val))==0L || bits==Long.MIN_VALUE)
        {
          v=false;
        }
        else if(bits==1)
        {
          v=true;
        }
        else
        {
          return false;
        }
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
        if(val instanceof Boolean)
        {
          {
            return uncheckedRemoveVal(size,(boolean)(val));
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
        return OmniArray.OfBoolean.uncheckedcontains(this.arr,0,size-1,(val));
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
        final boolean v;
        switch(val)
        {
          default:
        return false;
          case 0:
            v=false;
            break;
          case 1:
            v=true;
        }
        return OmniArray.OfBoolean.uncheckedcontains(this.arr,0,size-1,v);
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
        final boolean v;
        if(val==0L)
        {
          v=false;
        }
        else if(val==1L)
        {
          v=true;
        }
        else
        {
          return false;
        }
        {
          return OmniArray.OfBoolean.uncheckedcontains(this.arr,0,size-1,v);
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
        final boolean v;
        switch(Float.floatToRawIntBits(val))
        {
          default:
            return false;
          case 0:
          case Integer.MIN_VALUE:
            v=false;
            break;
          case 1:
            v=true;
        }
        {
          return OmniArray.OfBoolean.uncheckedcontains(this.arr,0,size-1,v);
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
        final boolean v;
        final long bits;
        if((bits=Double.doubleToRawLongBits(val))==0L || bits==Long.MIN_VALUE)
        {
          v=false;
        }
        else if(bits==1)
        {
          v=true;
        }
        else
        {
          return false;
        }
        {
          return OmniArray.OfBoolean.uncheckedcontains(this.arr,0,size-1,v);
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
        if(val instanceof Boolean)
        {
          {
            return OmniArray.OfBoolean.uncheckedcontains(this.arr,0,size-1,(boolean)(val));
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
        return uncheckedAbsoluteIndexOf(this.arr,0,size,(val));
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
        final boolean v;
        switch(val)
        {
          default:
        return -1;
          case 0:
            v=false;
            break;
          case 1:
            v=true;
        }
        return uncheckedAbsoluteIndexOf(this.arr,0,size,v);
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
        final boolean v;
        if(val==0L)
        {
          v=false;
        }
        else if(val==1L)
        {
          v=true;
        }
        else
        {
          return -1;
        }
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
        final boolean v;
        switch(Float.floatToRawIntBits(val))
        {
          default:
            return -1;
          case 0:
          case Integer.MIN_VALUE:
            v=false;
            break;
          case 1:
            v=true;
        }
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
        final boolean v;
        final long bits;
        if((bits=Double.doubleToRawLongBits(val))==0L || bits==Long.MIN_VALUE)
        {
          v=false;
        }
        else if(bits==1)
        {
          v=true;
        }
        else
        {
          return -1;
        }
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
        if(val instanceof Boolean)
        {
          int modCount=this.modCount;
          try
          {
            return uncheckedAbsoluteIndexOf(this.arr,0,size,(boolean)(val));
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
        return uncheckedAbsoluteLastIndexOf(this.arr,0,size,(val));
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
        final boolean v;
        switch(val)
        {
          default:
        return -1;
          case 0:
            v=false;
            break;
          case 1:
            v=true;
        }
        return uncheckedAbsoluteLastIndexOf(this.arr,0,size,v);
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
        final boolean v;
        if(val==0L)
        {
          v=false;
        }
        else if(val==1L)
        {
          v=true;
        }
        else
        {
          return -1;
        }
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
        final boolean v;
        switch(Float.floatToRawIntBits(val))
        {
          default:
            return -1;
          case 0:
          case Integer.MIN_VALUE:
            v=false;
            break;
          case 1:
            v=true;
        }
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
        final boolean v;
        final long bits;
        if((bits=Double.doubleToRawLongBits(val))==0L || bits==Long.MIN_VALUE)
        {
          v=false;
        }
        else if(bits==1)
        {
          v=true;
        }
        else
        {
          return -1;
        }
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
        if(val instanceof Boolean)
        {
          int modCount=this.modCount;
          try
          {
            return uncheckedAbsoluteLastIndexOf(this.arr,0,size,(boolean)(val));
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
    public void put(int index,boolean val)
    {
      if(index<0 || index>=this.size)
      {
        throw new IndexOutOfBoundsException("index="+index+"; size="+this.size);
      }
      arr[index]=val;
    }
    @Override
    public boolean getBoolean(int index)
    {
      if(index<0 || index>=this.size)
      {
        throw new IndexOutOfBoundsException("index="+index+"; size="+this.size);
      }
      return (boolean)arr[index];
    }
    @Override
    public Boolean get(int index)
    {
      return getBoolean(index);
    }
    @Override
    public void add(int index,boolean val)
    {
      //TODO
    }
    @Override
    public void add(int index,Boolean val)
    {
      //TODO
    }
    @Override
    public boolean removeBooleanAt(int index)
    {
      int size;
      if(index<0 || index>=(size=this.size))
      {
        throw new IndexOutOfBoundsException("index = "+index+"; size="+this.size);
      }
      ++this.modCount;
      final boolean[] arr;
      boolean ret=(boolean)(arr=this.arr)[index];
      ArrCopy.semicheckedSelfCopy(arr,index+1,index,(--size)-index);
      this.size=size;
      return ret;
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
    public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        {
          BooleanSortUtil.uncheckedDescendingSort(arr,0,size);
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
          BooleanSortUtil.uncheckedAscendingSort(arr,0,size);
        }
      }
    }
    @Override
    public OmniIterator.OfBoolean iterator()
    {
      //TODO
      return null;
    }
    @Override
    public boolean[] toBooleanArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final boolean[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new boolean[size],0,size);
        return copy;
      }
      return OmniArray.OfBoolean.DEFAULT_ARR;
    }
    @Override
    public Boolean[] toArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final Boolean[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new Boolean[size],0,size);
        return copy;
      }
      return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
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
    public char[] toCharArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new char[size],0,size);
        return copy;
      }
      return OmniArray.OfChar.DEFAULT_ARR;
    }
    @Override
    public byte[] toByteArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final byte[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new byte[size],0,size);
        return copy;
      }
      return OmniArray.OfByte.DEFAULT_ARR;
    }
  }
  public static class CheckedStackImpl extends BooleanArrSeq implements OmniStack.OfBoolean
  {
    private int uncheckedSearch (int bound
    ,boolean val
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
    ,boolean val
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
    static int uncheckedToString(boolean[] arr,int begin,int end,char[] buffer)
    {
      int bufferOffset;
      for(bufferOffset=ToStringUtil.getStringBoolean(arr[end],buffer,1);begin!=end;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringBoolean(arr[--end],buffer,bufferOffset)){}
      return bufferOffset;
    }
    static void uncheckedToString(boolean[] arr,int begin,int end,ToStringUtil.OmniStringBuilder builder)
    {
      for(builder.uncheckedAppendBoolean(arr[end]);begin!=end;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendBoolean(arr[--end])){}
    }
    static  void uncheckedForEach(boolean[] arr,int begin,int end,BooleanConsumer action)
    {
      for(;;--end)
      {
        action.accept((boolean)arr[end]);
        if(begin==end)
        {
          return;
        }
      }
    }
    static int uncheckedHashCode(boolean[] arr,int begin,int end)
    {
      int hash=31+Boolean.hashCode(arr[end]);
      while(begin!=end)
      {
        hash=hash*31+Boolean.hashCode(arr[--end]);
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
    private CheckedStackImpl(int size,boolean[] arr)
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
        if(size>(Integer.MAX_VALUE/6))
        {
          throw new OutOfMemoryError();
        }
        final char[] buffer;
        if(size<=(OmniArray.MAX_ARR_SIZE/((7)<<1)))
        {
          (buffer=new char[size*(7)])[size=uncheckedToString(arr,0,size-1,buffer)]=']';
          buffer[0]='[';
          return new String(buffer,0,size+1);
        }
        else
        {
          final ToStringUtil.OmniStringBuilder builder;
          uncheckedToString(arr,0,size-1,builder=new ToStringUtil.OmniStringBuilder(1,new char[size<=(int)(OmniArray.MAX_ARR_SIZE/6.5f)?(size*6)+(size>>>1):OmniArray.MAX_ARR_SIZE]));
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
        final boolean[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new boolean[size],0,size);
        return new CheckedStackImpl(size,copy);
      }
      return new CheckedStackImpl();
    }
    @Override
    public void forEach(BooleanConsumer action)
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
    public void forEach(Consumer<? super Boolean> action)
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
    public boolean removeIf(BooleanPredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Boolean> filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(boolean val)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(Boolean val)
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
        return uncheckedRemoveVal(size,(val));
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
        final boolean v;
        switch(val)
        {
          default:
        return false;
          case 0:
            v=false;
            break;
          case 1:
            v=true;
        }
        return uncheckedRemoveVal(size,v);
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
        final boolean v;
        if(val==0L)
        {
          v=false;
        }
        else if(val==1L)
        {
          v=true;
        }
        else
        {
          return false;
        }
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
        final boolean v;
        switch(Float.floatToRawIntBits(val))
        {
          default:
            return false;
          case 0:
          case Integer.MIN_VALUE:
            v=false;
            break;
          case 1:
            v=true;
        }
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
        final boolean v;
        final long bits;
        if((bits=Double.doubleToRawLongBits(val))==0L || bits==Long.MIN_VALUE)
        {
          v=false;
        }
        else if(bits==1)
        {
          v=true;
        }
        else
        {
          return false;
        }
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
        if(val instanceof Boolean)
        {
          {
            return uncheckedRemoveVal(size,(boolean)(val));
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
        return OmniArray.OfBoolean.uncheckedcontains(this.arr,0,size-1,(val));
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
        final boolean v;
        switch(val)
        {
          default:
        return false;
          case 0:
            v=false;
            break;
          case 1:
            v=true;
        }
        return OmniArray.OfBoolean.uncheckedcontains(this.arr,0,size-1,v);
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
        final boolean v;
        if(val==0L)
        {
          v=false;
        }
        else if(val==1L)
        {
          v=true;
        }
        else
        {
          return false;
        }
        {
          return OmniArray.OfBoolean.uncheckedcontains(this.arr,0,size-1,v);
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
        final boolean v;
        switch(Float.floatToRawIntBits(val))
        {
          default:
            return false;
          case 0:
          case Integer.MIN_VALUE:
            v=false;
            break;
          case 1:
            v=true;
        }
        {
          return OmniArray.OfBoolean.uncheckedcontains(this.arr,0,size-1,v);
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
        final boolean v;
        final long bits;
        if((bits=Double.doubleToRawLongBits(val))==0L || bits==Long.MIN_VALUE)
        {
          v=false;
        }
        else if(bits==1)
        {
          v=true;
        }
        else
        {
          return false;
        }
        {
          return OmniArray.OfBoolean.uncheckedcontains(this.arr,0,size-1,v);
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
        if(val instanceof Boolean)
        {
          {
            return OmniArray.OfBoolean.uncheckedcontains(this.arr,0,size-1,(boolean)(val));
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
    public OmniIterator.OfBoolean iterator()
    {
      //TODO
      return null;
    }
    @Override
    public boolean[] toBooleanArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final boolean[] copy;
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new boolean[size],0,size);
        return copy;
      }
      return OmniArray.OfBoolean.DEFAULT_ARR;
    }
    @Override
    public Boolean[] toArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final Boolean[] copy;
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new Boolean[size],0,size);
        return copy;
      }
      return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
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
    public char[] toCharArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char[] copy;
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new char[size],0,size);
        return copy;
      }
      return OmniArray.OfChar.DEFAULT_ARR;
    }
    @Override
    public byte[] toByteArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final byte[] copy;
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new byte[size],0,size);
        return copy;
      }
      return OmniArray.OfByte.DEFAULT_ARR;
    }
    @Override
    public
    int
    search(boolean val)
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
    search(int val)
    {
      int size;
      if((size=this.size)!=0)
      {
        final boolean v;
        switch(val)
        {
          default:
        return -1;
          case 0:
            v=false;
            break;
          case 1:
            v=true;
        }
        return uncheckedSearch(size,v);
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
        final boolean v;
        if(val==0L)
        {
          v=false;
        }
        else if(val==1L)
        {
          v=true;
        }
        else
        {
          return -1;
        }
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
        final boolean v;
        switch(Float.floatToRawIntBits(val))
        {
          default:
            return -1;
          case 0:
          case Integer.MIN_VALUE:
            v=false;
            break;
          case 1:
            v=true;
        }
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
        final boolean v;
        final long bits;
        if((bits=Double.doubleToRawLongBits(val))==0L || bits==Long.MIN_VALUE)
        {
          v=false;
        }
        else if(bits==1)
        {
          v=true;
        }
        else
        {
          return -1;
        }
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
        if(val instanceof Boolean)
        {
          {
            return uncheckedSearch(size,(boolean)(val));
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
    public boolean popBoolean()
    {
      int size;
      if((size=this.size)!=0)
      {
        boolean ret=(boolean)arr[--size];
        ++this.modCount;
        this.size=size;
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override
    public Boolean pop()
    {
      return popBoolean();
    }
    @Override
    public void push(boolean val)
    {
      //TODO
    }
    @Override
    public void push(Boolean val)
    {
      //TODO
    }
    @Override
    public boolean peekBoolean()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (boolean)((boolean)arr[size-1]);
      }
      return false;
    }
    @Override
    public boolean pollBoolean()
    {
      int size;
      if((size=this.size)!=0)
      {
        boolean ret=(boolean)((boolean)arr[--size]);
        ++this.modCount;
        this.size=size;
        return ret;
      }
      return false;
    }
    @Override
    public Boolean poll()
    {
      int size;
      if((size=this.size)!=0)
      {
        Boolean ret=(Boolean)((boolean)arr[--size]);
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
        double ret=TypeUtil.castToDouble((boolean)arr[--size]);
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
        float ret=TypeUtil.castToFloat((boolean)arr[--size]);
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
        long ret=TypeUtil.castToLong((boolean)arr[--size]);
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
        int ret=(int)TypeUtil.castToByte((boolean)arr[--size]);
        ++this.modCount;
        this.size=size;
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override
    public short pollShort()
    {
      int size;
      if((size=this.size)!=0)
      {
        short ret=(short)TypeUtil.castToByte((boolean)arr[--size]);
        ++this.modCount;
        this.size=size;
        return ret;
      }
      return Short.MIN_VALUE;
    }
    @Override
    public char pollChar()
    {
      int size;
      if((size=this.size)!=0)
      {
        char ret=TypeUtil.castToChar((boolean)arr[--size]);
        ++this.modCount;
        this.size=size;
        return ret;
      }
      return Character.MIN_VALUE;
    }
    @Override
    public byte pollByte()
    {
      int size;
      if((size=this.size)!=0)
      {
        byte ret=TypeUtil.castToByte((boolean)arr[--size]);
        ++this.modCount;
        this.size=size;
        return ret;
      }
      return Byte.MIN_VALUE;
    }
    @Override
    public Boolean peek()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (Boolean)((boolean)arr[size-1]);
      }
      return null;
    }
    @Override
    public double peekDouble()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return TypeUtil.castToDouble((boolean)arr[size-1]);
      }
      return Double.NaN;
    }
    @Override
    public float peekFloat()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return TypeUtil.castToFloat((boolean)arr[size-1]);
      }
      return Float.NaN;
    }
    @Override
    public long peekLong()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return TypeUtil.castToLong((boolean)arr[size-1]);
      }
      return Long.MIN_VALUE;
    }
    @Override
    public int peekInt()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (int)TypeUtil.castToByte((boolean)arr[size-1]);
      }
      return Integer.MIN_VALUE;
    }
    @Override
    public short peekShort()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (short)TypeUtil.castToByte((boolean)arr[size-1]);
      }
      return Short.MIN_VALUE;
    }
    @Override
    public char peekChar()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return TypeUtil.castToChar((boolean)arr[size-1]);
      }
      return Character.MIN_VALUE;
    }
    @Override
    public byte peekByte()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return TypeUtil.castToByte((boolean)arr[size-1]);
      }
      return Byte.MIN_VALUE;
    }
  }
}
