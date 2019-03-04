package omni.impl.seq;
import omni.api.OmniCollection;
import omni.util.OmniArray;
import omni.api.OmniList;
import omni.api.OmniStack;
import java.util.function.Predicate;
import java.util.function.Consumer;
import omni.function.FloatPredicate;
import omni.function.FloatConsumer;
import java.util.Comparator;
import omni.function.FloatComparator;
import omni.util.ToStringUtil;
import omni.util.ArrCopy;
import omni.util.FloatSortUtil;
import omni.impl.CheckedCollection;
import omni.util.TypeUtil;
import java.util.NoSuchElementException;
import omni.util.HashUtil;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import java.util.function.IntFunction;
public abstract class FloatArrSeq implements OmniCollection.OfFloat
{
  transient int size;
  transient float[] arr;
  private FloatArrSeq()
  {
    super();
  }
  private FloatArrSeq(int initialCapacity)
  {
    super();
    switch(initialCapacity)
    { 
    default:
      this.arr=new float[initialCapacity];
      return;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfFloat.DEFAULT_ARR;
    case 0:
    }
  }
  private FloatArrSeq(int size,float[] arr)
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
  public static class UncheckedListImpl extends FloatArrSeq implements OmniList.OfFloat
  {
    private static int uncheckedAbsoluteIndexOfBits(float[] arr,int offset,int bound
    ,int val
    )
    {
      for(;;)
      {
        if(
        val==Float.floatToRawIntBits(arr[offset])
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
    private static int uncheckedAbsoluteLastIndexOfBits(float[] arr,int offset,int bound
    ,int val
    )
    {
      for(;;)
      {
        if(
        val==Float.floatToRawIntBits(arr[--bound])
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
    private static int uncheckedRelativeIndexOfBits(float[] arr,int offset,int bound
    ,int val
    )
    {
      for(int i=offset;;)
      {
        if(
        val==Float.floatToRawIntBits(arr[i])
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
    private static int uncheckedRelativeLastIndexOfBits(float[] arr,int offset,int bound
    ,int val
    )
    {
      for(;;)
      {
        if(
        val==Float.floatToRawIntBits(arr[--bound])
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
    private static int uncheckedAbsoluteIndexOf0(float[] arr,int offset,int bound
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
    private static int uncheckedAbsoluteLastIndexOf0(float[] arr,int offset,int bound
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
    private static int uncheckedRelativeIndexOf0(float[] arr,int offset,int bound
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
    private static int uncheckedRelativeLastIndexOf0(float[] arr,int offset,int bound
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
    private static int uncheckedAbsoluteIndexOfNaN(float[] arr,int offset,int bound
    )
    {
      for(;;)
      {
        if(
        Float.isNaN(arr[offset])
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
    private static int uncheckedAbsoluteLastIndexOfNaN(float[] arr,int offset,int bound
    )
    {
      for(;;)
      {
        if(
        Float.isNaN(arr[--bound])
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
    private static int uncheckedRelativeIndexOfNaN(float[] arr,int offset,int bound
    )
    {
      for(int i=offset;;)
      {
        if(
        Float.isNaN(arr[i])
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
    private static int uncheckedRelativeLastIndexOfNaN(float[] arr,int offset,int bound
    )
    {
      for(;;)
      {
        if(
        Float.isNaN(arr[--bound])
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
    ,int val
    )
    {
      final var arr=this.arr;
      for(int i=0;;)
      {
        if(
        val==Float.floatToRawIntBits(arr[i])
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
        Float.isNaN(arr[i])
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
    static int uncheckedToString(float[] arr,int begin,int end,char[] buffer)
    {
      int bufferOffset;
      for(bufferOffset=ToStringUtil.getStringFloat(arr[begin],buffer,1);begin!=end;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringFloat(arr[++begin],buffer,bufferOffset)){}
      return bufferOffset;
    }
    static void uncheckedToString(float[] arr,int begin,int end,ToStringUtil.OmniStringBuilder builder)
    {
      for(builder.uncheckedAppendFloat(arr[begin]);begin!=end;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendFloat(arr[++begin])){}
    }
    static  void uncheckedForEach(float[] arr,int begin,int end,FloatConsumer action)
    {
      for(;;++begin)
      {
        action.accept((float)arr[begin]);
        if(begin==end)
        {
          return;
        }
      }
    }
    static int uncheckedHashCode(float[] arr,int begin,int end)
    {
      int hash=31+HashUtil.hashFloat(arr[begin]);
      while(begin!=end)
      {
        hash=hash*31+HashUtil.hashFloat(arr[++begin]);
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
    private UncheckedListImpl(int size,float[] arr)
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
        final char[] buffer;
        if(size<=(OmniArray.MAX_ARR_SIZE/((17)<<1)))
        {
          (buffer=new char[size*(17)])[size=uncheckedToString(arr,0,size-1,buffer)]=']';
          buffer[0]='[';
          return new String(buffer,0,size+1);
        }
        else
        {
          final ToStringUtil.OmniStringBuilder builder;
          uncheckedToString(arr,0,size-1,builder=new ToStringUtil.OmniStringBuilder(1,new char[size<=(int)(OmniArray.MAX_ARR_SIZE/11)?(size*11):OmniArray.MAX_ARR_SIZE]));
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
        final float[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new float[size],0,size);
        return new UncheckedListImpl(size,copy);
      }
      return new UncheckedListImpl();
    }
    @Override
    public void forEach(FloatConsumer action)
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
    public void forEach(Consumer<? super Float> action)
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
    public boolean removeIf(FloatPredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Float> filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(float val)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(Float val)
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
          return uncheckedRemoveValBits(size,TypeUtil.FLT_TRUE_BITS);
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
          if(TypeUtil.checkCastToFloat(val))
          {
            return uncheckedRemoveValBits(size,Float.floatToRawIntBits(val));
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
          if(TypeUtil.checkCastToFloat(val))
          {
            return uncheckedRemoveValBits(size,Float.floatToRawIntBits(val));
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
          return uncheckedRemoveValBits(size,Float.floatToRawIntBits(val));
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
        final float v;
        if((v=(float)val)==val)
        {
          return uncheckedRemoveValBits(size,Float.floatToRawIntBits(v));
        }
        else if(v!=v)
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
        if(val instanceof Float)
        {
          final float v;
          if((v=(float)val)==v)
          {
            return uncheckedRemoveValBits(size,Float.floatToRawIntBits(v));
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
          return OmniArray.OfFloat.uncheckedcontainsBits(this.arr,0,size-1,TypeUtil.FLT_TRUE_BITS);
        }
        return OmniArray.OfFloat.uncheckedcontains0(this.arr,0,size-1);
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
          if(TypeUtil.checkCastToFloat(val))
          {
            return OmniArray.OfFloat.uncheckedcontainsBits(this.arr,0,size-1,Float.floatToRawIntBits(val));
          }
        }
        else
        {
          return OmniArray.OfFloat.uncheckedcontains0(this.arr,0,size-1);
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
          if(TypeUtil.checkCastToFloat(val))
          {
            return OmniArray.OfFloat.uncheckedcontainsBits(this.arr,0,size-1,Float.floatToRawIntBits(val));
          }
        }
        else
        {
          return OmniArray.OfFloat.uncheckedcontains0(this.arr,0,size-1);
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
          return OmniArray.OfFloat.uncheckedcontainsBits(this.arr,0,size-1,Float.floatToRawIntBits(val));
        }
        return OmniArray.OfFloat.uncheckedcontainsNaN(this.arr,0,size-1);
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
        final float v;
        if((v=(float)val)==val)
        {
          return OmniArray.OfFloat.uncheckedcontainsBits(this.arr,0,size-1,Float.floatToRawIntBits(v));
        }
        else if(v!=v)
        {
          return OmniArray.OfFloat.uncheckedcontainsNaN(this.arr,0,size-1);
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
        if(val instanceof Float)
        {
          final float v;
          if((v=(float)val)==v)
          {
            return OmniArray.OfFloat.uncheckedcontainsBits(this.arr,0,size-1,Float.floatToRawIntBits(v));
          }
          return OmniArray.OfFloat.uncheckedcontainsNaN(this.arr,0,size-1);
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
          return uncheckedAbsoluteIndexOfBits(this.arr,0,size,TypeUtil.FLT_TRUE_BITS);
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
          if(TypeUtil.checkCastToFloat(val))
          {
            return uncheckedAbsoluteIndexOfBits(this.arr,0,size,Float.floatToRawIntBits(val));
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
          if(TypeUtil.checkCastToFloat(val))
          {
            return uncheckedAbsoluteIndexOfBits(this.arr,0,size,Float.floatToRawIntBits(val));
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
          return uncheckedAbsoluteIndexOfBits(this.arr,0,size,Float.floatToRawIntBits(val));
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
        final float v;
        if((v=(float)val)==val)
        {
          return uncheckedAbsoluteIndexOfBits(this.arr,0,size,Float.floatToRawIntBits(v));
        }
        else if(v!=v)
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
        if(val instanceof Float)
        {
          final float v;
          if((v=(float)val)==v)
          {
            return uncheckedAbsoluteIndexOfBits(this.arr,0,size,Float.floatToRawIntBits(v));
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
          return uncheckedAbsoluteLastIndexOfBits(this.arr,0,size,TypeUtil.FLT_TRUE_BITS);
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
          if(TypeUtil.checkCastToFloat(val))
          {
            return uncheckedAbsoluteLastIndexOfBits(this.arr,0,size,Float.floatToRawIntBits(val));
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
          if(TypeUtil.checkCastToFloat(val))
          {
            return uncheckedAbsoluteLastIndexOfBits(this.arr,0,size,Float.floatToRawIntBits(val));
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
          return uncheckedAbsoluteLastIndexOfBits(this.arr,0,size,Float.floatToRawIntBits(val));
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
        final float v;
        if((v=(float)val)==val)
        {
          return uncheckedAbsoluteLastIndexOfBits(this.arr,0,size,Float.floatToRawIntBits(v));
        }
        else if(v!=v)
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
        if(val instanceof Float)
        {
          final float v;
          if((v=(float)val)==v)
          {
            return uncheckedAbsoluteLastIndexOfBits(this.arr,0,size,Float.floatToRawIntBits(v));
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
    public void put(int index,float val)
    {
      arr[index]=val;
    }
    @Override
    public float getFloat(int index)
    {
      return (float)arr[index];
    }
    @Override
    public Float get(int index)
    {
      return getFloat(index);
    }
    @Override
    public void add(int index,float val)
    {
      //TODO
    }
    @Override
    public void add(int index,Float val)
    {
      //TODO
    }
    @Override
    public float removeFloatAt(int index)
    {
      final float[] arr;
      float ret=(float)(arr=this.arr)[index];
      ArrCopy.semicheckedSelfCopy(arr,index+1,index,(--size)-index);
      return ret;
    }
    @Override
    public OmniListIterator.OfFloat listIterator()
    {
      //TODO
      return null;
    }
    @Override
    public OmniListIterator.OfFloat listIterator(int index)
    {
      //TODO
      return null;
    }
    @Override
    public OmniList.OfFloat subList(int fromIndex,int toIndex)
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
          FloatSortUtil.uncheckedDescendingSort(arr,0,size);
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
          FloatSortUtil.uncheckedAscendingSort(arr,0,size);
        }
      }
    }
    @Override
    public OmniIterator.OfFloat iterator()
    {
      //TODO
      return null;
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
    public Float[] toArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final Float[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new Float[size],0,size);
        return copy;
      }
      return OmniArray.OfFloat.DEFAULT_BOXED_ARR;
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
  }
  public static class UncheckedStackImpl extends FloatArrSeq implements OmniStack.OfFloat
  {
    private int uncheckedSearchBits(int bound
    ,int val
    )
    {
      final var arr=this.arr;
      for(int index=bound-1;;)
      {
        if(
        val==Float.floatToRawIntBits(arr[index])
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
        Float.isNaN(arr[index])
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
    ,int val
    )
    {
      final var arr=this.arr;
      for(int i=--size;;--i)
      {
        if(
        val==Float.floatToRawIntBits(arr[i])
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
        Float.isNaN(arr[i])
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
    static int uncheckedToString(float[] arr,int begin,int end,char[] buffer)
    {
      int bufferOffset;
      for(bufferOffset=ToStringUtil.getStringFloat(arr[end],buffer,1);begin!=end;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringFloat(arr[--end],buffer,bufferOffset)){}
      return bufferOffset;
    }
    static void uncheckedToString(float[] arr,int begin,int end,ToStringUtil.OmniStringBuilder builder)
    {
      for(builder.uncheckedAppendFloat(arr[end]);begin!=end;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendFloat(arr[--end])){}
    }
    static  void uncheckedForEach(float[] arr,int begin,int end,FloatConsumer action)
    {
      for(;;--end)
      {
        action.accept((float)arr[end]);
        if(begin==end)
        {
          return;
        }
      }
    }
    static int uncheckedHashCode(float[] arr,int begin,int end)
    {
      int hash=31+HashUtil.hashFloat(arr[end]);
      while(begin!=end)
      {
        hash=hash*31+HashUtil.hashFloat(arr[--end]);
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
    private UncheckedStackImpl(int size,float[] arr)
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
        final char[] buffer;
        if(size<=(OmniArray.MAX_ARR_SIZE/((17)<<1)))
        {
          (buffer=new char[size*(17)])[size=uncheckedToString(arr,0,size-1,buffer)]=']';
          buffer[0]='[';
          return new String(buffer,0,size+1);
        }
        else
        {
          final ToStringUtil.OmniStringBuilder builder;
          uncheckedToString(arr,0,size-1,builder=new ToStringUtil.OmniStringBuilder(1,new char[size<=(int)(OmniArray.MAX_ARR_SIZE/11)?(size*11):OmniArray.MAX_ARR_SIZE]));
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
        final float[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new float[size],0,size);
        return new UncheckedStackImpl(size,copy);
      }
      return new UncheckedStackImpl();
    }
    @Override
    public void forEach(FloatConsumer action)
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
    public void forEach(Consumer<? super Float> action)
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
    public boolean removeIf(FloatPredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Float> filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(float val)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(Float val)
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
          return uncheckedRemoveValBits(size,TypeUtil.FLT_TRUE_BITS);
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
          if(TypeUtil.checkCastToFloat(val))
          {
            return uncheckedRemoveValBits(size,Float.floatToRawIntBits(val));
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
          if(TypeUtil.checkCastToFloat(val))
          {
            return uncheckedRemoveValBits(size,Float.floatToRawIntBits(val));
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
          return uncheckedRemoveValBits(size,Float.floatToRawIntBits(val));
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
        final float v;
        if((v=(float)val)==val)
        {
          return uncheckedRemoveValBits(size,Float.floatToRawIntBits(v));
        }
        else if(v!=v)
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
        if(val instanceof Float)
        {
          final float v;
          if((v=(float)val)==v)
          {
            return uncheckedRemoveValBits(size,Float.floatToRawIntBits(v));
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
          return OmniArray.OfFloat.uncheckedcontainsBits(this.arr,0,size-1,TypeUtil.FLT_TRUE_BITS);
        }
        return OmniArray.OfFloat.uncheckedcontains0(this.arr,0,size-1);
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
          if(TypeUtil.checkCastToFloat(val))
          {
            return OmniArray.OfFloat.uncheckedcontainsBits(this.arr,0,size-1,Float.floatToRawIntBits(val));
          }
        }
        else
        {
          return OmniArray.OfFloat.uncheckedcontains0(this.arr,0,size-1);
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
          if(TypeUtil.checkCastToFloat(val))
          {
            return OmniArray.OfFloat.uncheckedcontainsBits(this.arr,0,size-1,Float.floatToRawIntBits(val));
          }
        }
        else
        {
          return OmniArray.OfFloat.uncheckedcontains0(this.arr,0,size-1);
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
          return OmniArray.OfFloat.uncheckedcontainsBits(this.arr,0,size-1,Float.floatToRawIntBits(val));
        }
        return OmniArray.OfFloat.uncheckedcontainsNaN(this.arr,0,size-1);
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
        final float v;
        if((v=(float)val)==val)
        {
          return OmniArray.OfFloat.uncheckedcontainsBits(this.arr,0,size-1,Float.floatToRawIntBits(v));
        }
        else if(v!=v)
        {
          return OmniArray.OfFloat.uncheckedcontainsNaN(this.arr,0,size-1);
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
        if(val instanceof Float)
        {
          final float v;
          if((v=(float)val)==v)
          {
            return OmniArray.OfFloat.uncheckedcontainsBits(this.arr,0,size-1,Float.floatToRawIntBits(v));
          }
          return OmniArray.OfFloat.uncheckedcontainsNaN(this.arr,0,size-1);
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
    public OmniIterator.OfFloat iterator()
    {
      //TODO
      return null;
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
    public Float[] toArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final Float[] copy;
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new Float[size],0,size);
        return copy;
      }
      return OmniArray.OfFloat.DEFAULT_BOXED_ARR;
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
    public
    int
    search(boolean val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val)
        {
          return uncheckedSearchBits(size,TypeUtil.FLT_TRUE_BITS);
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
          if(TypeUtil.checkCastToFloat(val))
          {
            return uncheckedSearchBits(size,Float.floatToRawIntBits(val));
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
          if(TypeUtil.checkCastToFloat(val))
          {
            return uncheckedSearchBits(size,Float.floatToRawIntBits(val));
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
          return uncheckedSearchBits(size,Float.floatToRawIntBits(val));
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
        final float v;
        if((v=(float)val)==val)
        {
          return uncheckedSearchBits(size,Float.floatToRawIntBits(v));
        }
        else if(v!=v)
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
        if(val instanceof Float)
        {
          final float v;
          if((v=(float)val)==v)
          {
            return uncheckedSearchBits(size,Float.floatToRawIntBits(v));
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
    public float popFloat()
    {
      int size;
      if((size=this.size)!=0)
      {
        float ret=(float)arr[--size];
        this.size=size;
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override
    public Float pop()
    {
      return popFloat();
    }
    @Override
    public void push(float val)
    {
      //TODO
    }
    @Override
    public void push(Float val)
    {
      //TODO
    }
    @Override
    public float peekFloat()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (float)((float)arr[size-1]);
      }
      return Float.NaN;
    }
    @Override
    public float pollFloat()
    {
      int size;
      if((size=this.size)!=0)
      {
        float ret=(float)((float)arr[--size]);
        this.size=size;
        return ret;
      }
      return Float.NaN;
    }
    @Override
    public Float poll()
    {
      int size;
      if((size=this.size)!=0)
      {
        Float ret=(Float)((float)arr[--size]);
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
        double ret=(double)((float)arr[--size]);
        this.size=size;
        return ret;
      }
      return Double.NaN;
    }
    @Override
    public Float peek()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (Float)((float)arr[size-1]);
      }
      return null;
    }
    @Override
    public double peekDouble()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (double)((float)arr[size-1]);
      }
      return Double.NaN;
    }
  }
  public static class CheckedListImpl extends FloatArrSeq implements OmniList.OfFloat
  {
    private static int uncheckedAbsoluteIndexOfBits(float[] arr,int offset,int bound
    ,int val
    )
    {
      for(;;)
      {
        if(
        val==Float.floatToRawIntBits(arr[offset])
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
    private static int uncheckedAbsoluteLastIndexOfBits(float[] arr,int offset,int bound
    ,int val
    )
    {
      for(;;)
      {
        if(
        val==Float.floatToRawIntBits(arr[--bound])
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
    private static int uncheckedRelativeIndexOfBits(float[] arr,int offset,int bound
    ,int val
    )
    {
      for(int i=offset;;)
      {
        if(
        val==Float.floatToRawIntBits(arr[i])
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
    private static int uncheckedRelativeLastIndexOfBits(float[] arr,int offset,int bound
    ,int val
    )
    {
      for(;;)
      {
        if(
        val==Float.floatToRawIntBits(arr[--bound])
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
    private static int uncheckedAbsoluteIndexOf0(float[] arr,int offset,int bound
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
    private static int uncheckedAbsoluteLastIndexOf0(float[] arr,int offset,int bound
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
    private static int uncheckedRelativeIndexOf0(float[] arr,int offset,int bound
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
    private static int uncheckedRelativeLastIndexOf0(float[] arr,int offset,int bound
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
    private static int uncheckedAbsoluteIndexOfNaN(float[] arr,int offset,int bound
    )
    {
      for(;;)
      {
        if(
        Float.isNaN(arr[offset])
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
    private static int uncheckedAbsoluteLastIndexOfNaN(float[] arr,int offset,int bound
    )
    {
      for(;;)
      {
        if(
        Float.isNaN(arr[--bound])
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
    private static int uncheckedRelativeIndexOfNaN(float[] arr,int offset,int bound
    )
    {
      for(int i=offset;;)
      {
        if(
        Float.isNaN(arr[i])
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
    private static int uncheckedRelativeLastIndexOfNaN(float[] arr,int offset,int bound
    )
    {
      for(;;)
      {
        if(
        Float.isNaN(arr[--bound])
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
    ,int val
    )
    {
      int modCount=this.modCount;
      final var arr=this.arr;
      for(int i=0;;)
      {
        if(
        val==Float.floatToRawIntBits(arr[i])
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
        Float.isNaN(arr[i])
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
    static int uncheckedToString(float[] arr,int begin,int end,char[] buffer)
    {
      int bufferOffset;
      for(bufferOffset=ToStringUtil.getStringFloat(arr[begin],buffer,1);begin!=end;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringFloat(arr[++begin],buffer,bufferOffset)){}
      return bufferOffset;
    }
    static void uncheckedToString(float[] arr,int begin,int end,ToStringUtil.OmniStringBuilder builder)
    {
      for(builder.uncheckedAppendFloat(arr[begin]);begin!=end;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendFloat(arr[++begin])){}
    }
    static  void uncheckedForEach(float[] arr,int begin,int end,FloatConsumer action)
    {
      for(;;++begin)
      {
        action.accept((float)arr[begin]);
        if(begin==end)
        {
          return;
        }
      }
    }
    static int uncheckedHashCode(float[] arr,int begin,int end)
    {
      int hash=31+HashUtil.hashFloat(arr[begin]);
      while(begin!=end)
      {
        hash=hash*31+HashUtil.hashFloat(arr[++begin]);
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
    private CheckedListImpl(int size,float[] arr)
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
        final char[] buffer;
        if(size<=(OmniArray.MAX_ARR_SIZE/((17)<<1)))
        {
          (buffer=new char[size*(17)])[size=uncheckedToString(arr,0,size-1,buffer)]=']';
          buffer[0]='[';
          return new String(buffer,0,size+1);
        }
        else
        {
          final ToStringUtil.OmniStringBuilder builder;
          uncheckedToString(arr,0,size-1,builder=new ToStringUtil.OmniStringBuilder(1,new char[size<=(int)(OmniArray.MAX_ARR_SIZE/11)?(size*11):OmniArray.MAX_ARR_SIZE]));
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
        final float[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new float[size],0,size);
        return new CheckedListImpl(size,copy);
      }
      return new CheckedListImpl();
    }
    @Override
    public void forEach(FloatConsumer action)
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
    public void forEach(Consumer<? super Float> action)
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
    public boolean removeIf(FloatPredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Float> filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(float val)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(Float val)
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
          return uncheckedRemoveValBits(size,TypeUtil.FLT_TRUE_BITS);
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
          if(TypeUtil.checkCastToFloat(val))
          {
            return uncheckedRemoveValBits(size,Float.floatToRawIntBits(val));
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
          if(TypeUtil.checkCastToFloat(val))
          {
            return uncheckedRemoveValBits(size,Float.floatToRawIntBits(val));
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
          return uncheckedRemoveValBits(size,Float.floatToRawIntBits(val));
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
        final float v;
        if((v=(float)val)==val)
        {
          return uncheckedRemoveValBits(size,Float.floatToRawIntBits(v));
        }
        else if(v!=v)
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
        if(val instanceof Float)
        {
          final float v;
          if((v=(float)val)==v)
          {
            return uncheckedRemoveValBits(size,Float.floatToRawIntBits(v));
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
          return OmniArray.OfFloat.uncheckedcontainsBits(this.arr,0,size-1,TypeUtil.FLT_TRUE_BITS);
        }
        return OmniArray.OfFloat.uncheckedcontains0(this.arr,0,size-1);
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
          if(TypeUtil.checkCastToFloat(val))
          {
            return OmniArray.OfFloat.uncheckedcontainsBits(this.arr,0,size-1,Float.floatToRawIntBits(val));
          }
        }
        else
        {
          return OmniArray.OfFloat.uncheckedcontains0(this.arr,0,size-1);
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
          if(TypeUtil.checkCastToFloat(val))
          {
            return OmniArray.OfFloat.uncheckedcontainsBits(this.arr,0,size-1,Float.floatToRawIntBits(val));
          }
        }
        else
        {
          return OmniArray.OfFloat.uncheckedcontains0(this.arr,0,size-1);
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
          return OmniArray.OfFloat.uncheckedcontainsBits(this.arr,0,size-1,Float.floatToRawIntBits(val));
        }
        return OmniArray.OfFloat.uncheckedcontainsNaN(this.arr,0,size-1);
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
        final float v;
        if((v=(float)val)==val)
        {
          return OmniArray.OfFloat.uncheckedcontainsBits(this.arr,0,size-1,Float.floatToRawIntBits(v));
        }
        else if(v!=v)
        {
          return OmniArray.OfFloat.uncheckedcontainsNaN(this.arr,0,size-1);
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
        if(val instanceof Float)
        {
          final float v;
          if((v=(float)val)==v)
          {
            return OmniArray.OfFloat.uncheckedcontainsBits(this.arr,0,size-1,Float.floatToRawIntBits(v));
          }
          return OmniArray.OfFloat.uncheckedcontainsNaN(this.arr,0,size-1);
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
          return uncheckedAbsoluteIndexOfBits(this.arr,0,size,TypeUtil.FLT_TRUE_BITS);
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
          if(TypeUtil.checkCastToFloat(val))
          {
            return uncheckedAbsoluteIndexOfBits(this.arr,0,size,Float.floatToRawIntBits(val));
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
          if(TypeUtil.checkCastToFloat(val))
          {
            return uncheckedAbsoluteIndexOfBits(this.arr,0,size,Float.floatToRawIntBits(val));
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
          return uncheckedAbsoluteIndexOfBits(this.arr,0,size,Float.floatToRawIntBits(val));
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
        final float v;
        if((v=(float)val)==val)
        {
          return uncheckedAbsoluteIndexOfBits(this.arr,0,size,Float.floatToRawIntBits(v));
        }
        else if(v!=v)
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
        if(val instanceof Float)
        {
          final float v;
          if((v=(float)val)==v)
          {
            return uncheckedAbsoluteIndexOfBits(this.arr,0,size,Float.floatToRawIntBits(v));
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
          return uncheckedAbsoluteLastIndexOfBits(this.arr,0,size,TypeUtil.FLT_TRUE_BITS);
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
          if(TypeUtil.checkCastToFloat(val))
          {
            return uncheckedAbsoluteLastIndexOfBits(this.arr,0,size,Float.floatToRawIntBits(val));
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
          if(TypeUtil.checkCastToFloat(val))
          {
            return uncheckedAbsoluteLastIndexOfBits(this.arr,0,size,Float.floatToRawIntBits(val));
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
          return uncheckedAbsoluteLastIndexOfBits(this.arr,0,size,Float.floatToRawIntBits(val));
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
        final float v;
        if((v=(float)val)==val)
        {
          return uncheckedAbsoluteLastIndexOfBits(this.arr,0,size,Float.floatToRawIntBits(v));
        }
        else if(v!=v)
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
        if(val instanceof Float)
        {
          final float v;
          if((v=(float)val)==v)
          {
            return uncheckedAbsoluteLastIndexOfBits(this.arr,0,size,Float.floatToRawIntBits(v));
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
    public void put(int index,float val)
    {
      if(index<0 || index>=this.size)
      {
        throw new IndexOutOfBoundsException("index="+index+"; size="+this.size);
      }
      arr[index]=val;
    }
    @Override
    public float getFloat(int index)
    {
      if(index<0 || index>=this.size)
      {
        throw new IndexOutOfBoundsException("index="+index+"; size="+this.size);
      }
      return (float)arr[index];
    }
    @Override
    public Float get(int index)
    {
      return getFloat(index);
    }
    @Override
    public void add(int index,float val)
    {
      //TODO
    }
    @Override
    public void add(int index,Float val)
    {
      //TODO
    }
    @Override
    public float removeFloatAt(int index)
    {
      int size;
      if(index<0 || index>=(size=this.size))
      {
        throw new IndexOutOfBoundsException("index = "+index+"; size="+this.size);
      }
      ++this.modCount;
      final float[] arr;
      float ret=(float)(arr=this.arr)[index];
      ArrCopy.semicheckedSelfCopy(arr,index+1,index,(--size)-index);
      this.size=size;
      return ret;
    }
    @Override
    public OmniListIterator.OfFloat listIterator()
    {
      //TODO
      return null;
    }
    @Override
    public OmniListIterator.OfFloat listIterator(int index)
    {
      //TODO
      return null;
    }
    @Override
    public OmniList.OfFloat subList(int fromIndex,int toIndex)
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
          FloatSortUtil.uncheckedDescendingSort(arr,0,size);
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
          FloatSortUtil.uncheckedAscendingSort(arr,0,size);
        }
      }
    }
    @Override
    public OmniIterator.OfFloat iterator()
    {
      //TODO
      return null;
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
    public Float[] toArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final Float[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new Float[size],0,size);
        return copy;
      }
      return OmniArray.OfFloat.DEFAULT_BOXED_ARR;
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
  }
  public static class CheckedStackImpl extends FloatArrSeq implements OmniStack.OfFloat
  {
    private int uncheckedSearchBits(int bound
    ,int val
    )
    {
      final var arr=this.arr;
      for(int index=bound-1;;)
      {
        if(
        val==Float.floatToRawIntBits(arr[index])
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
        Float.isNaN(arr[index])
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
    ,int val
    )
    {
      int modCount=this.modCount;
      final var arr=this.arr;
      for(int i=--size;;--i)
      {
        if(
        val==Float.floatToRawIntBits(arr[i])
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
        Float.isNaN(arr[i])
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
    static int uncheckedToString(float[] arr,int begin,int end,char[] buffer)
    {
      int bufferOffset;
      for(bufferOffset=ToStringUtil.getStringFloat(arr[end],buffer,1);begin!=end;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringFloat(arr[--end],buffer,bufferOffset)){}
      return bufferOffset;
    }
    static void uncheckedToString(float[] arr,int begin,int end,ToStringUtil.OmniStringBuilder builder)
    {
      for(builder.uncheckedAppendFloat(arr[end]);begin!=end;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendFloat(arr[--end])){}
    }
    static  void uncheckedForEach(float[] arr,int begin,int end,FloatConsumer action)
    {
      for(;;--end)
      {
        action.accept((float)arr[end]);
        if(begin==end)
        {
          return;
        }
      }
    }
    static int uncheckedHashCode(float[] arr,int begin,int end)
    {
      int hash=31+HashUtil.hashFloat(arr[end]);
      while(begin!=end)
      {
        hash=hash*31+HashUtil.hashFloat(arr[--end]);
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
    private CheckedStackImpl(int size,float[] arr)
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
        final char[] buffer;
        if(size<=(OmniArray.MAX_ARR_SIZE/((17)<<1)))
        {
          (buffer=new char[size*(17)])[size=uncheckedToString(arr,0,size-1,buffer)]=']';
          buffer[0]='[';
          return new String(buffer,0,size+1);
        }
        else
        {
          final ToStringUtil.OmniStringBuilder builder;
          uncheckedToString(arr,0,size-1,builder=new ToStringUtil.OmniStringBuilder(1,new char[size<=(int)(OmniArray.MAX_ARR_SIZE/11)?(size*11):OmniArray.MAX_ARR_SIZE]));
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
        final float[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new float[size],0,size);
        return new CheckedStackImpl(size,copy);
      }
      return new CheckedStackImpl();
    }
    @Override
    public void forEach(FloatConsumer action)
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
    public void forEach(Consumer<? super Float> action)
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
    public boolean removeIf(FloatPredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Float> filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(float val)
    {
      //TODO
      return false;
    }
    @Override
    public boolean add(Float val)
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
          return uncheckedRemoveValBits(size,TypeUtil.FLT_TRUE_BITS);
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
          if(TypeUtil.checkCastToFloat(val))
          {
            return uncheckedRemoveValBits(size,Float.floatToRawIntBits(val));
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
          if(TypeUtil.checkCastToFloat(val))
          {
            return uncheckedRemoveValBits(size,Float.floatToRawIntBits(val));
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
          return uncheckedRemoveValBits(size,Float.floatToRawIntBits(val));
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
        final float v;
        if((v=(float)val)==val)
        {
          return uncheckedRemoveValBits(size,Float.floatToRawIntBits(v));
        }
        else if(v!=v)
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
        if(val instanceof Float)
        {
          final float v;
          if((v=(float)val)==v)
          {
            return uncheckedRemoveValBits(size,Float.floatToRawIntBits(v));
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
          return OmniArray.OfFloat.uncheckedcontainsBits(this.arr,0,size-1,TypeUtil.FLT_TRUE_BITS);
        }
        return OmniArray.OfFloat.uncheckedcontains0(this.arr,0,size-1);
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
          if(TypeUtil.checkCastToFloat(val))
          {
            return OmniArray.OfFloat.uncheckedcontainsBits(this.arr,0,size-1,Float.floatToRawIntBits(val));
          }
        }
        else
        {
          return OmniArray.OfFloat.uncheckedcontains0(this.arr,0,size-1);
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
          if(TypeUtil.checkCastToFloat(val))
          {
            return OmniArray.OfFloat.uncheckedcontainsBits(this.arr,0,size-1,Float.floatToRawIntBits(val));
          }
        }
        else
        {
          return OmniArray.OfFloat.uncheckedcontains0(this.arr,0,size-1);
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
          return OmniArray.OfFloat.uncheckedcontainsBits(this.arr,0,size-1,Float.floatToRawIntBits(val));
        }
        return OmniArray.OfFloat.uncheckedcontainsNaN(this.arr,0,size-1);
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
        final float v;
        if((v=(float)val)==val)
        {
          return OmniArray.OfFloat.uncheckedcontainsBits(this.arr,0,size-1,Float.floatToRawIntBits(v));
        }
        else if(v!=v)
        {
          return OmniArray.OfFloat.uncheckedcontainsNaN(this.arr,0,size-1);
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
        if(val instanceof Float)
        {
          final float v;
          if((v=(float)val)==v)
          {
            return OmniArray.OfFloat.uncheckedcontainsBits(this.arr,0,size-1,Float.floatToRawIntBits(v));
          }
          return OmniArray.OfFloat.uncheckedcontainsNaN(this.arr,0,size-1);
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
    public OmniIterator.OfFloat iterator()
    {
      //TODO
      return null;
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
    public Float[] toArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final Float[] copy;
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new Float[size],0,size);
        return copy;
      }
      return OmniArray.OfFloat.DEFAULT_BOXED_ARR;
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
    public
    int
    search(boolean val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val)
        {
          return uncheckedSearchBits(size,TypeUtil.FLT_TRUE_BITS);
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
          if(TypeUtil.checkCastToFloat(val))
          {
            return uncheckedSearchBits(size,Float.floatToRawIntBits(val));
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
          if(TypeUtil.checkCastToFloat(val))
          {
            return uncheckedSearchBits(size,Float.floatToRawIntBits(val));
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
          return uncheckedSearchBits(size,Float.floatToRawIntBits(val));
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
        final float v;
        if((v=(float)val)==val)
        {
          return uncheckedSearchBits(size,Float.floatToRawIntBits(v));
        }
        else if(v!=v)
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
        if(val instanceof Float)
        {
          final float v;
          if((v=(float)val)==v)
          {
            return uncheckedSearchBits(size,Float.floatToRawIntBits(v));
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
    public float popFloat()
    {
      int size;
      if((size=this.size)!=0)
      {
        float ret=(float)arr[--size];
        ++this.modCount;
        this.size=size;
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override
    public Float pop()
    {
      return popFloat();
    }
    @Override
    public void push(float val)
    {
      //TODO
    }
    @Override
    public void push(Float val)
    {
      //TODO
    }
    @Override
    public float peekFloat()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (float)((float)arr[size-1]);
      }
      return Float.NaN;
    }
    @Override
    public float pollFloat()
    {
      int size;
      if((size=this.size)!=0)
      {
        float ret=(float)((float)arr[--size]);
        ++this.modCount;
        this.size=size;
        return ret;
      }
      return Float.NaN;
    }
    @Override
    public Float poll()
    {
      int size;
      if((size=this.size)!=0)
      {
        Float ret=(Float)((float)arr[--size]);
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
        double ret=(double)((float)arr[--size]);
        ++this.modCount;
        this.size=size;
        return ret;
      }
      return Double.NaN;
    }
    @Override
    public Float peek()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (Float)((float)arr[size-1]);
      }
      return null;
    }
    @Override
    public double peekDouble()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (double)((float)arr[size-1]);
      }
      return Double.NaN;
    }
  }
}
