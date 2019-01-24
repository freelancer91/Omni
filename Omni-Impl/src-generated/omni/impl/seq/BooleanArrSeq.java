package omni.impl.seq;
import java.util.function.IntFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.Comparator;
import java.util.NoSuchElementException;
import omni.api.OmniCollection;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.api.OmniListIterator;
import omni.impl.CheckedCollection;
import omni.util.ArrCopy;
import omni.util.OmniArray;
import omni.util.SortUtil;
import omni.impl.seq.AbstractBooleanList;
import omni.impl.AbstractBooleanItr;
import omni.util.TypeUtil;
import omni.function.BooleanComparator;
import omni.function.BooleanConsumer;
import omni.function.BooleanPredicate;
public abstract class BooleanArrSeq extends AbstractBooleanList implements OmniCollection.OfBoolean
{
  private static void eraseIndexHelper(boolean[] arr,int index,int newSize)
  {
    ArrCopy.semicheckedCopy(arr,index+1,arr,index,newSize-index);
  }
  static boolean uncheckedcontains(boolean[] arr,int offset,int bound,boolean val)
  {
    for(;val^(arr[offset]);)
    {
      if(++offset==bound)
      {
        return false;
      }
    }
    return true;
  }
  private static int uncheckedindexOf(boolean[] arr,int offset,int bound,boolean val)
  {
    int index;
    for(index=offset;val^(arr[index]);)
    {
      if(++index==bound)
      {
        return -1;
      }
    }
    return index-offset;
  }
  private static int uncheckedindexOf(boolean[] arr,int bound,boolean val)
  {
    int index;
    for(index=0;val^(arr[index]);)
    {
      if(++index==bound)
      {
        return -1;
      }
    }
    return index;
  }
  private static int uncheckedlastIndexOf(boolean[] arr,int offset,int bound,boolean val)
  {
    for(;val^(arr[--bound]);)
    {
      if(bound==offset)
      {
        return -1;
      }
    }
    return bound-offset;
  }
  private static int uncheckedlastIndexOf(boolean[] arr,int bound,boolean val)
  {
    for(;val^(arr[--bound]);)
    {
      if(bound==0)
      {
        return -1;
      }
    }
    return bound;
  }
  private static int uncheckedsearch(boolean[] arr,int bound,boolean val)
  {
    int index;
    for(index=bound-1;val^(arr[index]);--index)
    {
      if(index==0)
      {
        return -1;
      }
    }
    return bound-index;
  }
  private static  void uncheckedreplaceAll(boolean[] arr,int offset,int bound,BooleanPredicate operator)
  {
    do
    {
      arr[offset]=operator.test((boolean)arr[offset]);
    }
    while(++offset!=bound);
  }
  static  void uncheckedForwardForEach(boolean[] arr,int offset,int bound,BooleanConsumer action)
  {
    do
    {
      action.accept((boolean)arr[offset]);
    }
    while(++offset!=bound);
  }
  static  void uncheckedReverseForEach(boolean[] arr,int offset,int bound,BooleanConsumer action)
  {
    do
    {
      action.accept((boolean)arr[--bound]);
    }
    while(bound!=offset);
  }
  private static int forwardHashCode(boolean[] arr,int offset,int bound)
  {
    int hash=31+Boolean.hashCode(arr[offset]);
    while(++offset!=bound)
    {
      hash=hash*31+Boolean.hashCode(arr[offset]);
    }
    return hash;
  }
  private static int reverseHashCode(boolean[] arr,int offset,int bound)
  {
    int hash=31+Boolean.hashCode(arr[offset]);
    while(bound!=offset)
    {
      hash=hash*31+Boolean.hashCode(arr[offset]);
    }
    return hash;
  }
  private static void forwardToString(boolean[] arr,int offset,int bound,StringBuilder builder)
  {
    for(builder.append(arr[offset]);++offset!=bound;builder.append(',').append(' ').append(arr[offset]));
  }
  private static void reverseToString(boolean[] arr,int offset,int bound,StringBuilder builder)
  {
    for(builder.append(arr[--bound]);bound!=offset;builder.append(',').append(' ').append(arr[--bound]));
  }
  private static  int pullSurvivorsDown(boolean[] arr,int dstOffset,int srcOffset,int srcBound,boolean v)
  {
    for(;;)
    {
      arr[dstOffset++]=v;
      do
      {
        if(++srcOffset==srcBound)
        {
          return dstOffset;
        }
      }
      while(arr[srcOffset]^v);
    }
  }
  static  int pullSurvivorsUp(boolean[] arr,int srcOffset,int srcBound,boolean v)
  {
    for(int dstOffset=srcOffset;;arr[dstOffset--]=v)
    {
      do
      {
        if(--srcOffset==srcBound)
        {
          return dstOffset;
        }
      }
      while(arr[srcOffset]^v);
    }
  }
  static  int pullSurvivorsDown(boolean[] arr,int srcOffset,int srcBound,boolean v)
  {
    for(int dstOffset=srcOffset;;arr[dstOffset++]=v)
    {
      do
      {
        if(++srcOffset==srcBound)
        {
          return dstOffset;
        }
      }
      while(arr[srcOffset]^v);
    }
  }
  transient boolean[] arr;
  private BooleanArrSeq()
  {
    super();
    this.arr=OmniArray.OfBoolean.DEFAULT_ARR;
  }
  private BooleanArrSeq(final int capacity)
  {
    super();
    switch(capacity)
    {
    default:
      this.arr=new boolean[capacity];
      return;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfBoolean.DEFAULT_ARR;
    case 0:
    }
  }
  private BooleanArrSeq(final int size,final boolean[] arr)
  {
    super(size);
    this.arr=arr;
  }
  @Override
  public boolean contains(final Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Boolean)
      {
        return BooleanArrSeq.uncheckedcontains(arr,0,size,(boolean)(val));
      }
    }
    return false;
  }
  public int indexOf(final Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Boolean)
      {
        return BooleanArrSeq.uncheckedindexOf(arr,size,(boolean)(val));
      }
    }
    return -1;
  }
  public int lastIndexOf(final Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Boolean)
      {
        return BooleanArrSeq.uncheckedlastIndexOf(arr,size,(boolean)(val));
      }
    }
    return -1;
  }
  public int search(final Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Boolean)
      {
        return BooleanArrSeq.uncheckedsearch(arr,size,(boolean)(val));
      }
    }
    return -1;
  }
  @Override
  public boolean contains(final boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return BooleanArrSeq.uncheckedcontains(arr,0,size,(val));
    }
    return false;
  }
  @Override
  public boolean contains(final int val)
  {
    final int size;
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
      return BooleanArrSeq.uncheckedcontains(arr,0,size,v);
    }
    return false;
  }
  @Override
  public boolean contains(final long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final boolean v;
      if(val==0)
      {
        v=false;
      }
      else if(val==1)
      {
        v=true;
      }
      else
      {
        return false;
      }
      {
        return BooleanArrSeq.uncheckedcontains(arr,0,size,v);
      }
    }
    return false;
  }
  @Override
  public boolean contains(final float val)
  {
    final int size;
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
        case TypeUtil.FLT_TRUE_BITS:
          v=true;
      }
      {
        return BooleanArrSeq.uncheckedcontains(arr,0,size,v);
      }
    }
    return false;
  }
  @Override
  public boolean contains(final double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final boolean v;
      final long bits;
      if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE)
      {
        v=false;
      }
      else if(bits==TypeUtil.DBL_TRUE_BITS)
      {
        v=true;
      }
      else
      {
        return false;
      }
      {
        return BooleanArrSeq.uncheckedcontains(arr,0,size,v);
      }
    }
    return false;
  }
  @Override
  public void forEach(final BooleanConsumer action)
  {
    final int size;
    if((size=this.size)!=0)
    {
      uncheckedForEach(size,action);
    }
  }
  @Override
  public void forEach(final Consumer<? super Boolean> action)
  {
    final int size;
    if((size=this.size)!=0)
    {
      uncheckedForEach(size,action::accept);
    }
  }
  @Override
  public boolean removeIf(final BooleanPredicate filter)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedRemoveIf(size,filter);
    }
    return false;
  }
  @Override
  public boolean removeIf(final Predicate<? super Boolean> filter)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedRemoveIf(size,filter::test);
    }
    return false;
  }
  public Boolean pop()
  {
    return popBoolean();
  }
  public void push(final Boolean val)
  {
    push((boolean)val);
  }
  @Override
  public int hashCode()
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedHashCode(size);
    }
    return 1;
  }
  public int indexOf(final boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return BooleanArrSeq.uncheckedindexOf(arr,size,(val));
    }
    return -1;
  }
  public int indexOf(final int val)
  {
    final int size;
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
      return BooleanArrSeq.uncheckedindexOf(arr,size,v);
    }
    return -1;
  }
  public int indexOf(final long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final boolean v;
      if(val==0)
      {
        v=false;
      }
      else if(val==1)
      {
        v=true;
      }
      else
      {
        return -1;
      }
      {
        return BooleanArrSeq.uncheckedindexOf(arr,size,v);
      }
    }
    return -1;
  }
  public int indexOf(final float val)
  {
    final int size;
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
        case TypeUtil.FLT_TRUE_BITS:
          v=true;
      }
      {
        return BooleanArrSeq.uncheckedindexOf(arr,size,v);
      }
    }
    return -1;
  }
  public int indexOf(final double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final boolean v;
      final long bits;
      if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE)
      {
        v=false;
      }
      else if(bits==TypeUtil.DBL_TRUE_BITS)
      {
        v=true;
      }
      else
      {
        return -1;
      }
      {
        return BooleanArrSeq.uncheckedindexOf(arr,size,v);
      }
    }
    return -1;
  }
  public int lastIndexOf(final boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return BooleanArrSeq.uncheckedlastIndexOf(arr,size,(val));
    }
    return -1;
  }
  public int lastIndexOf(final int val)
  {
    final int size;
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
      return BooleanArrSeq.uncheckedlastIndexOf(arr,size,v);
    }
    return -1;
  }
  public int lastIndexOf(final long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final boolean v;
      if(val==0)
      {
        v=false;
      }
      else if(val==1)
      {
        v=true;
      }
      else
      {
        return -1;
      }
      {
        return BooleanArrSeq.uncheckedlastIndexOf(arr,size,v);
      }
    }
    return -1;
  }
  public int lastIndexOf(final float val)
  {
    final int size;
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
        case TypeUtil.FLT_TRUE_BITS:
          v=true;
      }
      {
        return BooleanArrSeq.uncheckedlastIndexOf(arr,size,v);
      }
    }
    return -1;
  }
  public int lastIndexOf(final double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final boolean v;
      final long bits;
      if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE)
      {
        v=false;
      }
      else if(bits==TypeUtil.DBL_TRUE_BITS)
      {
        v=true;
      }
      else
      {
        return -1;
      }
      {
        return BooleanArrSeq.uncheckedlastIndexOf(arr,size,v);
      }
    }
    return -1;
  }
  public boolean peekBoolean()
  {
    final int size;
    if((size=this.size)!=0)
    {
      return (boolean)(arr[size-1]);
    }
    return false;
  }
  abstract void uncheckedCopyInto(final boolean[] dst,final int length);
  public Boolean peek()
  {
    final int size;
    if((size=this.size)!=0)
    {
      return (arr[size-1]);
    }
    return null;
  }
  abstract void uncheckedCopyInto(final Boolean[] dst,final int length);
  abstract void uncheckedCopyInto(final Object[] dst,final int length);
  public double peekDouble()
  {
    final int size;
    if((size=this.size)!=0)
    {
      return TypeUtil.castToDouble(arr[size-1]);
    }
    return Double.NaN;
  }
  abstract void uncheckedCopyInto(final double[] dst,final int length);
  public float peekFloat()
  {
    final int size;
    if((size=this.size)!=0)
    {
      return TypeUtil.castToFloat(arr[size-1]);
    }
    return Float.NaN;
  }
  abstract void uncheckedCopyInto(final float[] dst,final int length);
  public long peekLong()
  {
    final int size;
    if((size=this.size)!=0)
    {
      return TypeUtil.castToLong(arr[size-1]);
    }
    return Long.MIN_VALUE;
  }
  abstract void uncheckedCopyInto(final long[] dst,final int length);
  public int peekInt()
  {
    final int size;
    if((size=this.size)!=0)
    {
      return (int)TypeUtil.castToByte(arr[size-1]);
    }
    return Integer.MIN_VALUE;
  }
  abstract void uncheckedCopyInto(final int[] dst,final int length);
  public short peekShort()
  {
    final int size;
    if((size=this.size)!=0)
    {
      return (short)TypeUtil.castToByte(arr[size-1]);
    }
    return Short.MIN_VALUE;
  }
  abstract void uncheckedCopyInto(final short[] dst,final int length);
  public byte peekByte()
  {
    final int size;
    if((size=this.size)!=0)
    {
      return TypeUtil.castToByte(arr[size-1]);
    }
    return Byte.MIN_VALUE;
  }
  abstract void uncheckedCopyInto(final byte[] dst,final int length);
  public char peekChar()
  {
    final int size;
    if((size=this.size)!=0)
    {
      return TypeUtil.castToChar(arr[size-1]);
    }
    return Character.MIN_VALUE;
  }
  abstract void uncheckedCopyInto(final char[] dst,final int length);
  public boolean popBoolean()
  {
    return uncheckedPop(size-1);
  }
  public void push(final boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      uncheckedAppend(val,size);
    }
    else
    {
      uncheckedInit(val);
    }
  }
  @Override
  public boolean remove(final Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Boolean)
      {
        return this.uncheckedremoveVal(size,(boolean)(val));
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(final boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(final int val)
  {
    final int size;
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
      return this.uncheckedremoveVal(size,v);
    }
    return false;
  }
  @Override
  public boolean removeVal(final long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final boolean v;
      if(val==0)
      {
        v=false;
      }
      else if(val==1)
      {
        v=true;
      }
      else
      {
        return false;
      }
      {
        return this.uncheckedremoveVal(size,v);
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(final float val)
  {
    final int size;
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
        case TypeUtil.FLT_TRUE_BITS:
          v=true;
      }
      {
        return this.uncheckedremoveVal(size,v);
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(final double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final boolean v;
      final long bits;
      if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE)
      {
        v=false;
      }
      else if(bits==TypeUtil.DBL_TRUE_BITS)
      {
        v=true;
      }
      else
      {
        return false;
      }
      {
        return this.uncheckedremoveVal(size,v);
      }
    }
    return false;
  }
  public int search(final boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return BooleanArrSeq.uncheckedsearch(arr,size,(val));
    }
    return -1;
  }
  public int search(final int val)
  {
    final int size;
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
      return BooleanArrSeq.uncheckedsearch(arr,size,v);
    }
    return -1;
  }
  public int search(final long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final boolean v;
      if(val==0)
      {
        v=false;
      }
      else if(val==1)
      {
        v=true;
      }
      else
      {
        return -1;
      }
      {
        return BooleanArrSeq.uncheckedsearch(arr,size,v);
      }
    }
    return -1;
  }
  public int search(final float val)
  {
    final int size;
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
        case TypeUtil.FLT_TRUE_BITS:
          v=true;
      }
      {
        return BooleanArrSeq.uncheckedsearch(arr,size,v);
      }
    }
    return -1;
  }
  public int search(final double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final boolean v;
      final long bits;
      if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE)
      {
        v=false;
      }
      else if(bits==TypeUtil.DBL_TRUE_BITS)
      {
        v=true;
      }
      else
      {
        return -1;
      }
      {
        return BooleanArrSeq.uncheckedsearch(arr,size,v);
      }
    }
    return -1;
  }
  @Override
  public boolean set(final int index,final boolean val)
  {
    final boolean[] arr;
    final var oldVal=(boolean)(arr=this.arr)[index];
    arr[index]=val;
    return oldVal;
  }
  @Override
  public boolean[] toBooleanArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final boolean[] dst;
      uncheckedCopyInto(dst=new boolean[size],size);
      return dst;
    }
    return OmniArray.OfBoolean.DEFAULT_ARR;
  }
  @Override
  public Boolean[] toArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final Boolean[] dst;
      uncheckedCopyInto(dst=new Boolean[size],size);
      return dst;
    }
    return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
  }
  @Override
  public double[] toDoubleArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final double[] dst;
      uncheckedCopyInto(dst=new double[size],size);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override
  public float[] toFloatArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final float[] dst;
      uncheckedCopyInto(dst=new float[size],size);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override
  public long[] toLongArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final long[] dst;
      uncheckedCopyInto(dst=new long[size],size);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override
  public int[] toIntArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final int[] dst;
      uncheckedCopyInto(dst=new int[size],size);
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  @Override
  public short[] toShortArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final short[] dst;
      uncheckedCopyInto(dst=new short[size],size);
      return dst;
    }
    return OmniArray.OfShort.DEFAULT_ARR;
  }
  @Override
  public byte[] toByteArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final byte[] dst;
      uncheckedCopyInto(dst=new byte[size],size);
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_ARR;
  }
  @Override
  public char[] toCharArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final char[] dst;
      uncheckedCopyInto(dst=new char[size],size);
      return dst;
    }
    return OmniArray.OfChar.DEFAULT_ARR;
  }
  @Override
  public <T> T[] toArray(final IntFunction<T[]> arrConstructor)
  {
    final int size;
    final T[] dst=arrConstructor.apply(size=this.size);
    if(size!=0)
    {
      uncheckedCopyInto(dst,size);
    }
    return dst;
  }
  @Override
  public <T> T[] toArray(T[] dst)
  {
    final int size;
    if((size=this.size)!=0)
    {
      uncheckedCopyInto(dst=OmniArray.uncheckedArrResize(size,dst),size);
    }
    else if(dst.length!=0)
    {
      dst[0]=null;
    }
    return dst;
  }
  @Override
  public String toString()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final StringBuilder builder;
      uncheckedToString(size,builder=new StringBuilder("["));
      return builder.append(']').toString();
    }
    return "[]";
  }
  abstract boolean uncheckedremoveVal(final int size,final boolean val);
  abstract void uncheckedForEach(final int size,final BooleanConsumer action);
  abstract int uncheckedHashCode(final int size);
  abstract boolean uncheckedRemoveIf(final int size,final BooleanPredicate filter);
  abstract void uncheckedToString(final int size,final StringBuilder builder);
  private int finalizeSubListBatchRemove(final boolean[] arr,final int newBound,final int oldBound)
  {
    final int newRootSize,numRemoved;
    size=newRootSize=size-(numRemoved=oldBound-newBound);
    ArrCopy.semicheckedCopy(arr,oldBound,arr,newBound,newRootSize-newBound);
    return numRemoved;
  }
  private boolean[] growInsert(boolean[] arr,final int index,final int size)
  {
    if(arr.length==size)
    {
      ArrCopy.semicheckedCopy(arr,0,arr=new boolean[OmniArray.growBy50Pct(size)],0,index);
      this.arr=arr;
    }
    return arr;
  }
  private void uncheckedAppend(final boolean val,final int size)
  {
    boolean[] arr;
    if((arr=this.arr).length==size)
    {
      ArrCopy.uncheckedCopy(arr,0,arr=new boolean[OmniArray.growBy50Pct(size)],0,size);
    }
    arr[size]=val;
    this.size=size+1;
  }
  private void uncheckedInit(final boolean val)
  {
    boolean[] arr;
    if((arr=this.arr)==OmniArray.OfBoolean.DEFAULT_ARR)
    {
      this.arr=arr=new boolean[OmniArray.DEFAULT_ARR_SEQ_CAP];
    }
    else if(arr==null)
    {
      this.arr=arr=new boolean[1];
    }
    arr[0]=val;
    size=1;
  }
  private void uncheckedInsert(final int index,final boolean val,final int size)
  {
    final int tailDist;
    if((tailDist=size-index)==0)
    {
      uncheckedAppend(val,size);
    }
    else
    {
      boolean[] arr;
      ArrCopy.uncheckedCopy(arr=this.arr,index,arr=growInsert(arr,index,size),index+1,tailDist);
      arr[index]=val;
      this.size=size+1;
    }
  }
  private boolean uncheckedPop(final int newSize)
  {
    this.size=newSize;
    return arr[newSize];
  }
  public static abstract class Checked extends BooleanArrSeq
  {
    transient int modCount;
    private Checked()
    {
      super();
    }
    private Checked(final int capacity)
    {
      super(capacity);
    }
    private Checked(final int size,final boolean[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean add(final boolean val)
    {
      ++modCount;
      super.push(val);
      return true;
    }  
    @Override
    public void add(final int index,final boolean val)
    {
      CheckedCollection.checkLo(index);
      final int size;
      CheckedCollection.checkWriteHi(index,size=this.size);
      ++modCount;
      if(size!=0)
      {
        super.uncheckedInsert(index,val,size);
      }
      else
      {
        super.uncheckedInit(val);
      }
    }
    @Override
    public void clear()
    {
      if(size!=0)
      {
        ++modCount;
        this.size=0;
      }
    }
    @Override
    public boolean getBoolean(final int index)
    {
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      return (boolean)arr[index];
    }
    @Override
    public boolean popBoolean()
    {
      final int size;
      if((size=this.size)!=0)
      {
        ++modCount;
        return (boolean)super.uncheckedPop(size-1);
      }
      throw new NoSuchElementException();
    }
    @Override
    public void push(final boolean val)
    {
      ++modCount;
      super.push(val);
    }
    @Override
    public boolean removeBooleanAt(final int index)
    {
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ++modCount;
      final boolean[] arr;
      final var removed=(boolean)(arr=this.arr)[index];
      eraseIndexHelper(arr,index,--size);
      this.size=size;
      return removed;
    }
    @Override
    public boolean set(final int index,final boolean val)
    {
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      return super.set(index,val);
    }
    @Override
    public <T> T[] toArray(final IntFunction<T[]> arrConstructor)
    {
      return super.toArray(size->
      {
        int modCount=this.modCount;
        try
        {
          return arrConstructor.apply(size);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      });
    }
    @Override
    boolean uncheckedRemoveIf(int size,final BooleanPredicate filter)
    {
      int srcOffset;
      final int modCount=this.modCount;
      try
      {
        final boolean[] arr;
        boolean v;
        if(filter.test(v=(arr=this.arr)[srcOffset=0]))
        {
          for(;;)
          {
            if(++srcOffset==size)
            {
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.size=0;
              break;
            }
            if(v^arr[srcOffset])
            {
              final boolean tmp=filter.test(v=!v);
              CheckedCollection.checkModCount(modCount,this.modCount);
              this.size=tmp?0:pullSurvivorsDown(arr,0,srcOffset,size,v);
              break;
            }
          }
        }
        else
        {
          for(;;)
          {
            if(++srcOffset==size)
            {
              CheckedCollection.checkModCount(modCount,this.modCount);
              return false;
            }
            if(v^arr[srcOffset])
            {
              final boolean tmp=filter.test(!v);
              CheckedCollection.checkModCount(modCount,this.modCount);
              if(!tmp)
              {
                return false;
              }
              this.size=pullSurvivorsDown(arr,srcOffset,size,v);
              break;
            }
          }
        }
      }
      catch(final RuntimeException e)
      {
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
      return true;
    }
  }
  public static class CheckedList extends Checked implements OmniList.OfBoolean
  {
    public CheckedList()
    {
      super();
    }
    public CheckedList(final int capacity)
    {
      super(capacity);
    }
    public CheckedList(final int size,final boolean[] arr)
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
    public boolean equals(final Object val)
    {
      //TODO implements equals method
      return false;
    }
    @Override
    public OmniIterator.OfBoolean iterator()
    {
      return new BidirectionalItr(this);
    }
    @Override
    public OmniListIterator.OfBoolean listIterator()
    {
      return new BidirectionalItr(this);
    }
    @Override
    public OmniListIterator.OfBoolean listIterator(final int index)
    {
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,this.size);
      return new BidirectionalItr(this,index);
    }
    @Override
    public void put(final int index,final boolean val)
    {
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      arr[index]=val;
    }
    @Override
    public void replaceAll(final BooleanPredicate operator)
    {
      final int size;
      if(0!=((size=this.size)))
      {
        int modCount=this.modCount;
        try
        {
          BooleanArrSeq.uncheckedreplaceAll(arr,0,size,operator);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        this.modCount=modCount+1;
      }
    }
    @Override
    public void sort(final BooleanComparator sorter)
    {
      final int size;
      if(1<((size=this.size)))
      {
        int modCount=this.modCount;
        try
        {
          SortUtil.uncheckedcomparatorSort(arr,0,size-1,sorter);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        this.modCount=modCount+1;
      }
    }
    @Override
    public void replaceAll(final UnaryOperator<Boolean> operator)
    {
      final int size;
      if(0!=((size=this.size)))
      {
        int modCount=this.modCount;
        try
        {
          BooleanArrSeq.uncheckedreplaceAll(arr,0,size,operator::apply);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        this.modCount=modCount+1;
      }
    }
    @Override
    public void sort(final Comparator<? super Boolean> sorter)
    {
      final int size;
      if(1<((size=this.size)))
      {
        int modCount=this.modCount;
        try
        {
          SortUtil.uncheckedcomparatorSort(arr,0,size-1,sorter::compare);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        this.modCount=modCount+1;
      }
    }
    public void reverseSort()
    {
      final int size;
      if((size=this.size)>1)
      {
         SortUtil.uncheckedreverseSort(arr,0,size-1);
         ++this.modCount;
      }
    }
    public void sort()
    {
      final int size;
      if((size=this.size)>1)
      {
         SortUtil.uncheckedsort(arr,0,size-1);
         ++this.modCount;
      }
    }
    @Override
    public OmniList.OfBoolean subList(final int fromIndex,final int toIndex)
    {
      CheckedCollection.checkSubListRange(fromIndex,toIndex,this.size);
      return new SubList(this,fromIndex,toIndex-fromIndex);
    }
    @Override
    void uncheckedCopyInto(final boolean[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Object[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Boolean[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final double[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final float[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final long[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final int[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final short[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final byte[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final char[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedForEach(final int size,final BooleanConsumer action)
    {
      int modCount=this.modCount;
      try
      {
        uncheckedForwardForEach(arr,0,size,action);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override
    int uncheckedHashCode(final int size)
    {
      return forwardHashCode(arr,0,size);
    }
    @Override
    boolean uncheckedremoveVal(int size,final boolean val)
    {
      final var arr=this.arr;int index;
      for(index=0,--size;val^(arr[index]);++index)
      {
        if(index==size)
        {
          return false;
        }
      }
      ++this.modCount;
      eraseIndexHelper(arr,index,size);
      this.size=size;
      return true;
    }
    @Override
    void uncheckedToString(final int size,final StringBuilder builder)
    {
      forwardToString(arr,0,size,builder);
    }
    private static class BidirectionalItr implements OmniListIterator.OfBoolean
    {
      private transient int lastRet=-1;
      private transient final Checked root;
      private transient int cursor;
      private transient int modCount;
      private BidirectionalItr(final Checked root)
      {
        this.root=root;
        this.modCount=root.modCount;
      }
      private BidirectionalItr(final Checked root,final int cursor)
      {
        this.root=root;
        this.modCount=root.modCount;
        this.cursor=cursor;
      }
      @Override
      public void add(final boolean val)
      {
        final Checked root;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        final int rootSize,cursor=this.cursor;
        if((rootSize=root.size)!=0)
        {
          ((BooleanArrSeq)root).uncheckedInsert(cursor,val,rootSize);
        }
        else
        {
          ((BooleanArrSeq)root).uncheckedInit(val);
        }
        root.modCount=++modCount;
        this.modCount=modCount;
        this.cursor=cursor+1;
        this.lastRet=-1;
      }
      @Override
      public void forEachRemaining(final BooleanConsumer action)
      {
        final int cursor,bound;
        final Checked root;
        if((cursor=this.cursor)!=(bound=(root=this.root).size))
        {
          int modCount=this.modCount;
          try
          {
            uncheckedForwardForEach(root.arr,cursor,bound,action);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      @Override
      public void forEachRemaining(final Consumer<? super Boolean> action)
      {
        final int cursor,bound;
        final Checked root;
        if((cursor=this.cursor)!=(bound=(root=this.root).size))
        {
          int modCount=this.modCount;
          try
          {
            uncheckedForwardForEach(root.arr,cursor,bound,action::accept);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      @Override
      public boolean hasNext()
      {
        return cursor!=root.size;
      }
      @Override
      public boolean hasPrevious()
      {
        return cursor!=0;
      }
      @Override
      public boolean nextBoolean()
      {
        final Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        final int cursor;
        if((cursor=this.cursor)!=root.size)
        {
          lastRet=cursor;
          this.cursor=cursor+1;
          return (boolean)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override
      public int nextIndex()
      {
        return cursor;
      }
      @Override
      public boolean previousBoolean()
      {
        final Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        int cursor;
        if((cursor=this.cursor)!=0)
        {
          lastRet=--cursor;
          this.cursor=cursor;
          return (boolean)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override
      public int previousIndex()
      {
        return cursor-1;
      }
      @Override
      public void remove()
      {
        final int lastRet;
        if((lastRet=this.lastRet)!=-1)
        {
          final Checked root;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
          eraseIndexHelper(root.arr,lastRet,--root.size);
          root.modCount=++modCount;
          this.modCount=modCount;
          cursor=lastRet;
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override
      public void set(final boolean val)
      {
        final int lastRet;
        if((lastRet=this.lastRet)!=-1)
        {
          final Checked root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.arr[lastRet]=val;
          return;
        }
        throw new IllegalStateException();
      }
    }
    private static class SubList extends AbstractBooleanList implements OmniList.OfBoolean
    {
      transient final Checked root;
      transient final SubList parent;
      transient final int rootOffset;
      transient int modCount;
      private static  void bubbleUpDecrementSize(SubList parent)
      {
        while(parent!=null)
        {
          --parent.size;++parent.modCount;
          parent=parent.parent;
        }
      }
      private static  void bubbleUpDecrementSize(SubList parent,final int numToRemove)
      {
        while(parent!=null)
        {
          parent.size-=numToRemove;++parent.modCount;
          parent=parent.parent;
        }
      }
      private static  void bubbleUpIncrementModCount(SubList parent)
      {
        while(parent!=null)
        {
          ++parent.modCount;
          parent=parent.parent;
        }
      }
      private static  void bubbleUpIncrementSize(SubList parent)
      {
        while(parent!=null)
        {
          ++parent.size;++parent.modCount;
          parent=parent.parent;
        }
      }
      private SubList(final Checked root,final int rootOffset,final int size)
      {
        super(size);
        this.root=root;
        this.parent=null;
        this.rootOffset=rootOffset;
        this.modCount=root.modCount;
      }
      private SubList(final Checked root,final SubList parent,final int rootOffset,final int size)
      {
        super(size);
        this.root=root;
        this.parent=parent;
        this.rootOffset=rootOffset;
        this.modCount=root.modCount;
      }
      @Override
      public boolean add(final boolean val)
      {
        final Checked root;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        final int rootSize;
        final int size=this.size;
        if((rootSize=root.size)!=0)
        {
          ((BooleanArrSeq)root).uncheckedInsert(rootOffset+size,val,rootSize);
        }
        else
        {
          ((BooleanArrSeq)root).uncheckedInit(val);
        }
        root.modCount=++modCount;
        this.modCount=modCount;
        bubbleUpIncrementSize(parent);
        this.size=size+1;
        return true;
      }
      @Override
      public void add(final int index,final boolean val)
      {
        final Checked root;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        CheckedCollection.checkLo(index);
        final int size;
        CheckedCollection.checkWriteHi(index,size=this.size);
        final int rootSize;
        if((rootSize=root.size)!=0)
        {
          ((BooleanArrSeq)root).uncheckedInsert(rootOffset+index,val,rootSize);
        }
        else
        {
          ((BooleanArrSeq)root).uncheckedInit(val);
        }
        root.modCount=++modCount;
        this.modCount=modCount;
        bubbleUpIncrementSize(parent);
        this.size=size+1;
      }
      @Override
      public void clear()
      {
        final var root=this.root;
        final int modCount=this.modCount;
        final int size;
        if((size=this.size)!=0)
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
          this.modCount=modCount+1;
          this.size=0;
          bubbleUpDecrementSize(parent,size);
          final int newRootSize;
          root.size=newRootSize=root.size-size;
          final boolean[] arr;
          final int rootOffset;
          ArrCopy.semicheckedCopy(arr=root.arr,(rootOffset=this.rootOffset)+size,arr,rootOffset,newRootSize-rootOffset);
        }
        else
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      @Override
      public Object clone()
      {
        final var root=checkModCountAndGetRoot();
        final boolean[] arr;
        final int size;
        if((size=this.size)!=0)
        {
          ArrCopy.uncheckedCopy(root.arr,rootOffset,arr=new boolean[size],0,size);
        }
        else
        {
          arr=null;
        }
        return new CheckedList(size,arr);
      }
      @Override
      public boolean contains(final Object val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val instanceof Boolean)
          {
            return uncheckedcontains(size,(boolean)(val));
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public int indexOf(final Object val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val instanceof Boolean)
          {
            return uncheckedindexOf(size,(boolean)(val));
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int lastIndexOf(final Object val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val instanceof Boolean)
          {
            return uncheckedlastIndexOf(size,(boolean)(val));
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public boolean contains(final boolean val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedcontains(size,(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean contains(final int val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final boolean v;
          switch(val)
          {
            default:
              CheckedCollection.checkModCount(modCount,root.modCount);
              return false;
            case 0:
              v=false;
              break;
            case 1:
              v=true;
          }
          return this.uncheckedcontains(size,v);
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean contains(final long val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final boolean v;
          if(val==0)
          {
            v=false;
          }
          else if(val==1)
          {
            v=true;
          }
          else
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
          }
          {
            return this.uncheckedcontains(size,v);
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean contains(final float val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final boolean v;
          switch(Float.floatToRawIntBits(val))
          {
            default:
              CheckedCollection.checkModCount(modCount,root.modCount);
              return false;
            case 0:
            case Integer.MIN_VALUE:
              v=false;
              break;
            case TypeUtil.FLT_TRUE_BITS:
              v=true;
          }
          {
            return this.uncheckedcontains(size,v);
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean contains(final double val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final boolean v;
          final long bits;
          if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE)
          {
            v=false;
          }
          else if(bits==TypeUtil.DBL_TRUE_BITS)
          {
            v=true;
          }
          else
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
          }
          {
            return this.uncheckedcontains(size,v);
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean equals(final Object val)
      {
        //TODO implements equals method
        return false;
      }
      @Override
      public void forEach(final BooleanConsumer action)
      {
        final var root=this.root;
        int modCount=this.modCount;
        try
        {
          final int size;if((size=this.size)!=0){final int rootOffset;uncheckedForwardForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size,action);}
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      @Override
      public void forEach(final Consumer<? super Boolean> action)
      {
        final var root=this.root;
        int modCount=this.modCount;
        try
        {
          final int size;if((size=this.size)!=0){final int rootOffset;uncheckedForwardForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size,action::accept);}
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      @Override
      public boolean getBoolean(final int index)
      {
        final var root=checkModCountAndGetRoot();
        CheckedCollection.checkLo(index);
        CheckedCollection.checkReadHi(index,this.size);
        return (boolean)root.arr[index+rootOffset];
      }    
      @Override
      public int hashCode()
      {
        final var root=checkModCountAndGetRoot();
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int rootOffset;
            return forwardHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size);
          }
          return 1;
        }
      }
      @Override
      public int indexOf(final boolean val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedindexOf(size,(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int indexOf(final int val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final boolean v;
          switch(val)
          {
            default:
              CheckedCollection.checkModCount(modCount,root.modCount);
              return -1;
            case 0:
              v=false;
              break;
            case 1:
              v=true;
          }
          return this.uncheckedindexOf(size,v);
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int indexOf(final long val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final boolean v;
          if(val==0)
          {
            v=false;
          }
          else if(val==1)
          {
            v=true;
          }
          else
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
            return -1;
          }
          {
            return this.uncheckedindexOf(size,v);
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int indexOf(final float val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final boolean v;
          switch(Float.floatToRawIntBits(val))
          {
            default:
              CheckedCollection.checkModCount(modCount,root.modCount);
              return -1;
            case 0:
            case Integer.MIN_VALUE:
              v=false;
              break;
            case TypeUtil.FLT_TRUE_BITS:
              v=true;
          }
          {
            return this.uncheckedindexOf(size,v);
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int indexOf(final double val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final boolean v;
          final long bits;
          if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE)
          {
            v=false;
          }
          else if(bits==TypeUtil.DBL_TRUE_BITS)
          {
            v=true;
          }
          else
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
            return -1;
          }
          {
            return this.uncheckedindexOf(size,v);
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int lastIndexOf(final boolean val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedlastIndexOf(size,(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int lastIndexOf(final int val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final boolean v;
          switch(val)
          {
            default:
              CheckedCollection.checkModCount(modCount,root.modCount);
              return -1;
            case 0:
              v=false;
              break;
            case 1:
              v=true;
          }
          return this.uncheckedlastIndexOf(size,v);
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int lastIndexOf(final long val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final boolean v;
          if(val==0)
          {
            v=false;
          }
          else if(val==1)
          {
            v=true;
          }
          else
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
            return -1;
          }
          {
            return this.uncheckedlastIndexOf(size,v);
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int lastIndexOf(final float val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final boolean v;
          switch(Float.floatToRawIntBits(val))
          {
            default:
              CheckedCollection.checkModCount(modCount,root.modCount);
              return -1;
            case 0:
            case Integer.MIN_VALUE:
              v=false;
              break;
            case TypeUtil.FLT_TRUE_BITS:
              v=true;
          }
          {
            return this.uncheckedlastIndexOf(size,v);
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int lastIndexOf(final double val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final boolean v;
          final long bits;
          if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE)
          {
            v=false;
          }
          else if(bits==TypeUtil.DBL_TRUE_BITS)
          {
            v=true;
          }
          else
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
            return -1;
          }
          {
            return this.uncheckedlastIndexOf(size,v);
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public boolean isEmpty()
      {
        CheckedCollection.checkModCount(this.modCount,root.modCount);
        return size==0;
      }
      @Override
      public OmniIterator.OfBoolean iterator()
      {
        final int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
        return new BidirectionalItr(this,modCount);
      }
      @Override
      public OmniListIterator.OfBoolean listIterator()
      {
        final int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
        return new BidirectionalItr(this,modCount);
      }
      @Override
      public OmniListIterator.OfBoolean listIterator(final int index)
      {
        final int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
        CheckedCollection.checkLo(index);
        CheckedCollection.checkWriteHi(index,size);
        return new BidirectionalItr(this,modCount,index+rootOffset);
      }
      @Override
      public void put(final int index,final boolean val)
      {
        final var root=checkModCountAndGetRoot();
        CheckedCollection.checkLo(index);
        CheckedCollection.checkReadHi(index,this.size);
        root.arr[index+rootOffset]=val;
      }
      @Override
      public boolean remove(final Object val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val instanceof Boolean)
          {
            return uncheckedremoveVal(size,(boolean)(val));
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final boolean val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final int val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final boolean v;
          switch(val)
          {
            default:
              CheckedCollection.checkModCount(modCount,root.modCount);
              return false;
            case 0:
              v=false;
              break;
            case 1:
              v=true;
          }
          return this.uncheckedremoveVal(size,v);
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final long val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final boolean v;
          if(val==0)
          {
            v=false;
          }
          else if(val==1)
          {
            v=true;
          }
          else
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
          }
          {
            return this.uncheckedremoveVal(size,v);
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final float val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final boolean v;
          switch(Float.floatToRawIntBits(val))
          {
            default:
              CheckedCollection.checkModCount(modCount,root.modCount);
              return false;
            case 0:
            case Integer.MIN_VALUE:
              v=false;
              break;
            case TypeUtil.FLT_TRUE_BITS:
              v=true;
          }
          {
            return this.uncheckedremoveVal(size,v);
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final double val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final boolean v;
          final long bits;
          if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE)
          {
            v=false;
          }
          else if(bits==TypeUtil.DBL_TRUE_BITS)
          {
            v=true;
          }
          else
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
          }
          {
            return this.uncheckedremoveVal(size,v);
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeBooleanAt(int index)
      {
        int modCount;
        final Checked root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        CheckedCollection.checkLo(index);
        final int size;
        CheckedCollection.checkReadHi(index,size=this.size);
        final boolean[] arr;
        final var removed=(boolean)(arr=root.arr)[index+=rootOffset];
        eraseIndexHelper(arr,index,--root.size);
        root.modCount=++modCount;
        bubbleUpDecrementSize(parent);
        this.modCount=modCount;
        this.size=size-1;
        return removed;
      }
      @Override
      public boolean removeIf(final BooleanPredicate filter)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return uncheckedRemoveIf(size,filter);
        }
        CheckedCollection.checkModCount(this.modCount,root.modCount);
        return false;
      }
      @Override
      public void replaceAll(final BooleanPredicate operator)
      {
        final var root=this.root;
        final int size;
        if(0!=(size=this.size))
        {
          int modCount=this.modCount;
          try
          {
            final int rootOffset;uncheckedreplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
              root.modCount=++modCount;
              bubbleUpIncrementModCount(parent);
              this.modCount=modCount;
        }
        else
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      @Override
      public void sort(final BooleanComparator sorter)
      {
        final var root=this.root;
        final int size;
        if(1<(size=this.size))
        {
          int modCount=this.modCount;
          try
          {
            final int rootOffset;SortUtil.uncheckedcomparatorSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
              root.modCount=++modCount;
              bubbleUpIncrementModCount(parent);
              this.modCount=modCount;
        }
        else
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      @Override
      public void reverseSort()
      {
        int modCount;
        final Checked root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        final int size;
        if((size=this.size)>1)
        {
          final int rootOffset;
          SortUtil.uncheckedreverseSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
              root.modCount=++modCount;
              bubbleUpIncrementModCount(parent);
              this.modCount=modCount;
        }
      }
      @Override
      public void sort()
      {
        int modCount;
        final Checked root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        final int size;
        if((size=this.size)>1)
        {
          final int rootOffset;
          SortUtil.uncheckedsort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
              root.modCount=++modCount;
              bubbleUpIncrementModCount(parent);
              this.modCount=modCount;
        }
      }
      @Override
      public boolean removeIf(final Predicate<? super Boolean> filter)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return uncheckedRemoveIf(size,filter::test);
        }
        CheckedCollection.checkModCount(this.modCount,root.modCount);
        return false;
      }
      @Override
      public void replaceAll(final UnaryOperator<Boolean> operator)
      {
        final var root=this.root;
        final int size;
        if(0!=(size=this.size))
        {
          int modCount=this.modCount;
          try
          {
            final int rootOffset;uncheckedreplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator::apply);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
              root.modCount=++modCount;
              bubbleUpIncrementModCount(parent);
              this.modCount=modCount;
        }
        else
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      @Override
      public void sort(final Comparator<? super Boolean> sorter)
      {
        final var root=this.root;
        final int size;
        if(1<(size=this.size))
        {
          int modCount=this.modCount;
          try
          {
            final int rootOffset;SortUtil.uncheckedcomparatorSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter::compare);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
              root.modCount=++modCount;
              bubbleUpIncrementModCount(parent);
              this.modCount=modCount;
        }
        else
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      @Override
      public boolean set(int index,final boolean val)
      {
        final var root=checkModCountAndGetRoot();
        CheckedCollection.checkLo(index);
        CheckedCollection.checkReadHi(index,this.size);
        final boolean[] arr;
        final var oldVal=(boolean)(arr=root.arr)[index+=rootOffset];
        arr[index]=val;
        return oldVal;
      }
      @Override
      public int size()
      {
        CheckedCollection.checkModCount(this.modCount,root.modCount);
        return size;
      }
      @Override
      public OmniList.OfBoolean subList(final int fromIndex,final int toIndex)
      {
        final var root=checkModCountAndGetRoot();
        CheckedCollection.checkSubListRange(fromIndex,toIndex,this.size);
        return new SubList(root,this,this.rootOffset+fromIndex,toIndex-fromIndex);
      }
      @Override
      public boolean[] toBooleanArray()
      {
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0)
        {
          final boolean[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new boolean[size],0,size);
          return dst;
        }
        return OmniArray.OfBoolean.DEFAULT_ARR;
      }
      @Override
      public Boolean[] toArray()
      {
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0)
        {
          final Boolean[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new Boolean[size],0,size);
          return dst;
        }
        return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
      }
      @Override
      public double[] toDoubleArray()
      {
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0)
        {
          final double[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new double[size],0,size);
          return dst;
        }
        return OmniArray.OfDouble.DEFAULT_ARR;
      }
      @Override
      public float[] toFloatArray()
      {
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0)
        {
          final float[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new float[size],0,size);
          return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
      }
      @Override
      public long[] toLongArray()
      {
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0)
        {
          final long[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new long[size],0,size);
          return dst;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
      }
      @Override
      public int[] toIntArray()
      {
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0)
        {
          final int[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new int[size],0,size);
          return dst;
        }
        return OmniArray.OfInt.DEFAULT_ARR;
      }
      @Override
      public short[] toShortArray()
      {
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0)
        {
          final short[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new short[size],0,size);
          return dst;
        }
        return OmniArray.OfShort.DEFAULT_ARR;
      }
      @Override
      public byte[] toByteArray()
      {
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0)
        {
          final byte[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new byte[size],0,size);
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_ARR;
      }
      @Override
      public char[] toCharArray()
      {
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0)
        {
          final char[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new char[size],0,size);
          return dst;
        }
        return OmniArray.OfChar.DEFAULT_ARR;
      }
      @Override
      public <T> T[] toArray(final IntFunction<T[]> arrConstructor)
      {
        final var root=this.root;
        final int size;
        final T[] dst;
        int modCount=this.modCount;
        try
        {
          dst=arrConstructor.apply(size=this.size);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
        if(size!=0)
        {
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst,0,size);
        }
        return dst;
      }
      @Override
      public <T> T[] toArray(T[] arr)
      {
        final var root=checkModCountAndGetRoot();
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
      @Override
      public String toString()
      {
        final var root=checkModCountAndGetRoot();
        {
          final int size;
          if((size=this.size)!=0)
          {
            final StringBuilder builder;
            final int rootOffset;
            forwardToString(root.arr,rootOffset=this.rootOffset,rootOffset+size,builder=new StringBuilder("["));
            return builder.append(']').toString();
          }
          return "[]";
        }
      }
      private boolean uncheckedcontains(final int size,final boolean val)
      { 
        final int rootOffset;
        return BooleanArrSeq.uncheckedcontains(checkModCountAndGetRoot().arr,rootOffset=this.rootOffset,rootOffset+size,val);
      }
      private int uncheckedindexOf(final int size,final boolean val)
      { 
        final int rootOffset;
        return BooleanArrSeq.uncheckedindexOf(checkModCountAndGetRoot().arr,rootOffset=this.rootOffset,rootOffset+size,val);
      }
      private int uncheckedlastIndexOf(final int size,final boolean val)
      { 
        final int rootOffset;
        return BooleanArrSeq.uncheckedlastIndexOf(checkModCountAndGetRoot().arr,rootOffset=this.rootOffset,rootOffset+size,val);
      }
      private boolean uncheckedremoveVal(int size,final boolean val)
      {
        int modCount;
        final Checked root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        final var arr=root.arr;int index;final int bound=(index=this.rootOffset)+(--size);
        for(;val^(arr[index]);)
        {
          if(++index==bound)
          {
            return false;
          }
        }
        root.modCount=++modCount;
        bubbleUpDecrementSize(parent);
        this.modCount=modCount;
        eraseIndexHelper(arr,index,--root.size);
        this.size=size;
        return true;
      }
      private Checked checkModCountAndGetRoot()
      {
        final Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        return root;
      }
      private boolean uncheckedRemoveIf(int size,final BooleanPredicate filter)
      {
        final Checked root=this.root;
        int modCount=this.modCount;
        int srcOffset;
        final int srcBound=(srcOffset=rootOffset)+size;
        int dstOffset;
        try
        {
          final boolean[] arr;
          boolean v;
          if(filter.test(v=(arr=root.arr)[srcOffset]))
          {
            dstOffset=srcOffset;
            for(;;)
            {
              if(++srcOffset==srcBound)
              {
                CheckedCollection.checkModCount(modCount,root.modCount);
                break;
              }
              if(v^arr[srcOffset])
              {
                final boolean tmp=filter.test(v=!v);
                CheckedCollection.checkModCount(modCount,root.modCount);
                if(!tmp)
                {
                  dstOffset=pullSurvivorsDown(arr,dstOffset,srcOffset,size,v);
                }
                break;
              }
            }
          }
          else
          {
            for(;;)
            {
              if(++srcOffset==srcBound)
              {
                CheckedCollection.checkModCount(modCount,root.modCount);
                return false;
              }
              if(v^arr[srcOffset])
              {
                final boolean tmp=filter.test(!v);
                CheckedCollection.checkModCount(modCount,root.modCount);
                if(!tmp)
                {
                  return false;
                }
                dstOffset=pullSurvivorsDown(arr,srcOffset,size,v);
                break;
              }
            }
          }
          root.modCount=++modCount;
          this.modCount=modCount;
          this.size=size-(size=((BooleanArrSeq)root).finalizeSubListBatchRemove(arr,dstOffset,srcBound));
          bubbleUpDecrementSize(parent,size);
        }
        catch(final RuntimeException e)
        {
          throw CheckedCollection.checkModCount(modCount,root.modCount,e);
        }
        return true;
      }
      private static class BidirectionalItr implements OmniListIterator.OfBoolean
      {
        private transient final SubList parent;
        private transient int cursor;
        private transient int lastRet=-1;
        private transient int modCount;
        private BidirectionalItr(final SubList parent,final int modCount)
        {
          this.parent=parent;
          this.cursor=parent.rootOffset;
          this.modCount=modCount;
        }
        private BidirectionalItr(final SubList parent,final int modCount,final int cursor)
        {
          this.parent=parent;
          this.cursor=cursor;
          this.modCount=modCount;
        }
        @Override
        public void add(final boolean val)
        {
          final SubList parent;
          final Checked root;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
          final int rootSize;
          final int cursor=this.cursor;
          if((rootSize=root.size)!=0)
          {
            ((BooleanArrSeq)root).uncheckedInsert(cursor,val,rootSize);
          }
          else
          {
            ((BooleanArrSeq)root).uncheckedInit(val);
          }
          bubbleUpIncrementSize(parent.parent);
          parent.modCount=++modCount;
          root.modCount=modCount;
          ++parent.size;
          this.cursor=cursor+1;
          this.lastRet=-1;
        }
        @Override
        public void forEachRemaining(final BooleanConsumer action)
        {
          final int cursor,bound;
          final SubList parent;
          if((cursor=this.cursor)!=(bound=(parent=this.parent).rootOffset+parent.size))
          {
            final var root=parent.root;
            int modCount=this.modCount;
            try
            {
              uncheckedForwardForEach(root.arr,cursor,bound,action);
            }
            finally
            {
              CheckedCollection.checkModCount(modCount,root.modCount);
            }
            this.cursor=bound;
            lastRet=bound-1;
          }
        }
        @Override
        public void forEachRemaining(final Consumer<? super Boolean> action)
        {
          final int cursor,bound;
          final SubList parent;
          if((cursor=this.cursor)!=(bound=(parent=this.parent).rootOffset+parent.size))
          {
            final var root=parent.root;
            int modCount=this.modCount;
            try
            {
              uncheckedForwardForEach(root.arr,cursor,bound,action::accept);
            }
            finally
            {
              CheckedCollection.checkModCount(modCount,root.modCount);
            }
            this.cursor=bound;
            lastRet=bound-1;
          }
        }
        @Override
        public boolean hasNext()
        {
          final SubList parent;
          return cursor!=(parent=this.parent).rootOffset+parent.size;
        }
        @Override
        public boolean hasPrevious()
        {
          return cursor!=parent.rootOffset;
        }
        @Override
        public boolean nextBoolean()
        {
          final Checked root;
          final SubList parent;
          CheckedCollection.checkModCount(modCount,(root=(parent=this.parent).root).modCount);
          final int cursor;
          if((cursor=this.cursor)!=parent.rootOffset+parent.size)
          {
            lastRet=cursor;
            this.cursor=cursor+1;
            return (boolean)root.arr[cursor];
          }
          throw new NoSuchElementException();
        }
        @Override
        public int nextIndex()
        {
          return cursor-parent.rootOffset;
        }
        @Override
        public boolean previousBoolean()
        {
          final Checked root;
          final SubList  parent;
          CheckedCollection.checkModCount(modCount,(root=(parent=this.parent).root).modCount);
          int cursor;
          if((cursor=this.cursor)!=parent.rootOffset)
          {
            lastRet=--cursor;
            this.cursor=cursor;
            return (boolean)root.arr[cursor];
          }
          throw new NoSuchElementException();
        }
        @Override
        public int previousIndex()
        {
          return cursor-parent.rootOffset-1;
        }
        @Override
        public void remove()
        {
          final int lastRet;
          if((lastRet=this.lastRet)!=-1)
          {
            final Checked root;
            final SubList  parent;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
            eraseIndexHelper(root.arr,lastRet,--root.size);
            bubbleUpDecrementSize(parent.parent);
            --parent.size;
            root.modCount=++modCount;
            parent.modCount=modCount;
            this.modCount=modCount;
            cursor=lastRet;
            this.lastRet=-1;
            return;
          }
          throw new IllegalStateException();
        }
        @Override
        public void set(final boolean val)
        {
          final int lastRet;
          if((lastRet=this.lastRet)!=-1)
          {
            final Checked root;
            CheckedCollection.checkModCount(modCount,(root=parent.root).modCount);
            root.arr[lastRet]=val;
            return;
          }
          throw new IllegalStateException();
        }
      }
    }
  }
  public static class CheckedStack extends Checked implements OmniStack.OfBoolean
  {
    public CheckedStack()
    {
      super();
    }
    public CheckedStack(final int capacity)
    {
      super(capacity);
    }
    public CheckedStack(final int size,final boolean[] arr)
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
      return new CheckedStack(size,arr);
    }
    @Override
    public boolean equals(final Object val)
    {
      //TODO implements equals method
      return false;
    }
    @Override
    public OmniIterator.OfBoolean iterator()
    {
      return new CheckedDescendingItr(this);
    }
        @Override
        public boolean pollBoolean()
        {
          final int size;
          if((size=this.size)!=0)
          {
            ++modCount;
            return (boolean)(((BooleanArrSeq)this).uncheckedPop(size-1));
          }
          return false;
        }
        @Override
        public Boolean poll()
        {
          final int size;
          if((size=this.size)!=0)
          {
            ++modCount;
            return (Boolean)(((BooleanArrSeq)this).uncheckedPop(size-1));
          }
          return null;
        }
        @Override
        public double pollDouble()
        {
          final int size;
          if((size=this.size)!=0)
          {
            ++modCount;
            return TypeUtil.castToDouble(((BooleanArrSeq)this).uncheckedPop(size-1));
          }
          return Double.NaN;
        }
        @Override
        public float pollFloat()
        {
          final int size;
          if((size=this.size)!=0)
          {
            ++modCount;
            return TypeUtil.castToFloat(((BooleanArrSeq)this).uncheckedPop(size-1));
          }
          return Float.NaN;
        }
        @Override
        public long pollLong()
        {
          final int size;
          if((size=this.size)!=0)
          {
            ++modCount;
            return TypeUtil.castToLong(((BooleanArrSeq)this).uncheckedPop(size-1));
          }
          return Long.MIN_VALUE;
        }
        @Override
        public int pollInt()
        {
          final int size;
          if((size=this.size)!=0)
          {
            ++modCount;
            return (int)TypeUtil.castToByte(((BooleanArrSeq)this).uncheckedPop(size-1));
          }
          return Integer.MIN_VALUE;
        }
        @Override
        public short pollShort()
        {
          final int size;
          if((size=this.size)!=0)
          {
            ++modCount;
            return (short)TypeUtil.castToByte(((BooleanArrSeq)this).uncheckedPop(size-1));
          }
          return Short.MIN_VALUE;
        }
        @Override
        public byte pollByte()
        {
          final int size;
          if((size=this.size)!=0)
          {
            ++modCount;
            return TypeUtil.castToByte(((BooleanArrSeq)this).uncheckedPop(size-1));
          }
          return Byte.MIN_VALUE;
        }
        @Override
        public char pollChar()
        {
          final int size;
          if((size=this.size)!=0)
          {
            ++modCount;
            return TypeUtil.castToChar(((BooleanArrSeq)this).uncheckedPop(size-1));
          }
          return Character.MIN_VALUE;
        }
    @Override
    void uncheckedCopyInto(final boolean[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Object[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Boolean[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final double[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final float[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final long[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final int[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final short[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final byte[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final char[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedForEach(final int size,final BooleanConsumer action)
    {
      int modCount=this.modCount;
      try
      {
        uncheckedReverseForEach(arr,0,size,action);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override
    int uncheckedHashCode(final int size)
    {
      return reverseHashCode(arr,0,size);
    }
    @Override
    boolean uncheckedremoveVal(int size,final boolean val)
    {
      final var arr=this.arr;int index;
      for(index=--size;val^(arr[index]);--index)
      {
        if(index==0)
        {
          return false;
        }
      }
      ++this.modCount;
      eraseIndexHelper(arr,index,size);
      this.size=size;
      return true;
    }
    @Override
    void uncheckedToString(final int size,final StringBuilder builder)
    {
      reverseToString(arr,0,size,builder);
    }
    private static class CheckedDescendingItr extends AbstractBooleanItr implements OmniIterator.OfBoolean
    {
      private transient final CheckedStack root;
      private transient int cursor;
      private transient int lastRet=-1;
      private transient int modCount;
      private CheckedDescendingItr(CheckedStack root)
      {
        this.root=root;
        cursor=root.size;
        modCount=root.modCount;
      }
      @Override
      public void forEachRemaining(BooleanConsumer action)
      {
        final int cursor;
        if((cursor=this.cursor)!=0)
        {
          uncheckedForEachRemaining(cursor,action);
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Boolean> action)
      {
        final int cursor;
        if((cursor=this.cursor)!=0)
        {
          uncheckedForEachRemaining(cursor,action::accept);
        }
      }
      @Override
      public boolean hasNext()
      {
        return cursor!=0;
      }
      @Override
      public boolean nextBoolean()
      {
        final CheckedStack root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        int cursor;
        if((cursor=this.cursor)!=0)
        {
          lastRet=--cursor;
          this.cursor=cursor;
          return (boolean)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override
      public void remove()
      {
        final int lastRet;
        if((lastRet=this.lastRet)!=-1)
        {
          final CheckedStack root;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
          eraseIndexHelper(root.arr,lastRet,--root.size);
          root.modCount=++modCount;
          this.modCount=modCount;
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      private void uncheckedForEachRemaining(int cursor,BooleanConsumer action)
      {
        final var root=this.root;
        int modCount=this.modCount;
        try
        {
          uncheckedReverseForEach(root.arr,0,cursor,action);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
        this.cursor=0;
        lastRet=0;
      }
    }
  }
  public static abstract class Unchecked extends BooleanArrSeq
  {
    private Unchecked()
    {
      super();
    }
    private Unchecked(final int capacity)
    {
      super(capacity);
    }
    private Unchecked(final int size,final boolean[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean add(final boolean val)
    {
      super.push(val);
      return true;
    }
    @Override
    public void add(final int index,final boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        super.uncheckedInsert(index,val,size);
      }
      else
      {
        super.uncheckedInit(val);
      }
    }
    @Override
    public boolean getBoolean(final int index)
    {
      return (boolean)arr[index];
    }
    @Override
    public boolean removeBooleanAt(final int index)
    {
      final boolean[] arr;
      final var removed=(boolean)(arr=this.arr)[index];
      eraseIndexHelper(arr,index,--size);
      return removed;
    }
    @Override
    boolean uncheckedRemoveIf(int size,final BooleanPredicate filter)
    {
      int srcOffset;
      boolean v;
      if(filter.test(v=arr[srcOffset=0]))
      {
        for(;;)
        {
          if(++srcOffset==size)
          {
            this.size=0;
            break;
          }
          if(v^arr[srcOffset])
          {
            this.size=filter.test(v=!v)?0:pullSurvivorsDown(arr,0,srcOffset,size,v);
            break;
          }
        }
      }
      else
      {
        for(;;)
        {
          if(++srcOffset==size)
          {
            return false;
          }
          if(v^arr[srcOffset])
          {
            if(!filter.test(!v))
            {
              return false;
            }
            this.size=pullSurvivorsDown(arr,srcOffset,size,v);
            break;
          }
        }
      }
      return true;
    }
  }
  public static class UncheckedList extends Unchecked implements OmniList.OfBoolean
  {
    public UncheckedList()
    {
      super();
    }
    public UncheckedList(final int capacity)
    {
      super(capacity);
    }
    public UncheckedList(final int size,final boolean[] arr)
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
      return new UncheckedList(size,arr);
    }
    @Override
    public boolean equals(final Object val)
    {
      //TODO implements equals method
      return false;
    }
    @Override
    public OmniIterator.OfBoolean iterator()
    {
      return new AscendingItr(this);
    }
    @Override
    public OmniListIterator.OfBoolean listIterator()
    {
      return new BidirectionalItr(this);
    }
    @Override
    public OmniListIterator.OfBoolean listIterator(final int index)
    {
      return new BidirectionalItr(this,index);
    }
    @Override
    public void put(final int index,final boolean val)
    {
      arr[index]=val;
    }
    @Override
    public void replaceAll(final BooleanPredicate operator)
    {
      final int size;
      if(0!=((size=this.size)))
      {
        BooleanArrSeq.uncheckedreplaceAll(arr,0,size,operator);
      }
    }
    @Override
    public void reverseSort()
    {
      final int size;
      if(1<((size=this.size)))
      {
        SortUtil.uncheckedreverseSort(arr,0,size-1);
      }
    }
    @Override
    public void sort()
    {
      final int size;
      if(1<((size=this.size)))
      {
        SortUtil.uncheckedsort(arr,0,size-1);
      }
    }
    @Override
    public void sort(final BooleanComparator sorter)
    {
      final int size;
      if(1<((size=this.size)))
      {
        SortUtil.uncheckedcomparatorSort(arr,0,size-1,sorter);
      }
    }
    @Override
    public void replaceAll(final UnaryOperator<Boolean> operator)
    {
      final int size;
      if(0!=((size=this.size)))
      {
        BooleanArrSeq.uncheckedreplaceAll(arr,0,size,operator::apply);
      }
    }
    @Override
    public void sort(final Comparator<? super Boolean> sorter)
    {
      final int size;
      if(1<((size=this.size)))
      {
        SortUtil.uncheckedcomparatorSort(arr,0,size-1,sorter::compare);
      }
    }
    @Override
    public OmniList.OfBoolean subList(final int fromIndex,final int toIndex)
    {
      return new SubList(this,fromIndex,toIndex-fromIndex);
    }
    @Override
    void uncheckedCopyInto(final boolean[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Object[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Boolean[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final double[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final float[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final long[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final int[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final short[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final byte[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final char[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedForEach(final int size,final BooleanConsumer action)
    {
      uncheckedForwardForEach(arr,0,size,action);
    }
    @Override
    int uncheckedHashCode(final int size)
    {
      return forwardHashCode(arr,0,size);
    }
    @Override
    boolean uncheckedremoveVal(int size,final boolean val)
    {
      final var arr=this.arr;int index;
      for(index=--size;val^(arr[index]);--index)
      {
        if(index==0)
        {
          return false;
        }
      }
      eraseIndexHelper(arr,index,size);
      this.size=size;
      return true;
    }
    @Override
    void uncheckedToString(final int size,final StringBuilder builder)
    {
      forwardToString(arr,0,size,builder);
    }
    private static class AscendingItr extends AbstractBooleanItr implements OmniIterator.OfBoolean
    {
      private transient final UncheckedList root;
      private transient int cursor;
      private AscendingItr(UncheckedList root)
      {
        this.root=root;
      }
      @Override
      public void forEachRemaining(BooleanConsumer action)
      {
        final UncheckedList root;
        final int cursor,bound;
        if((cursor=this.cursor)!=(bound=(root=this.root).size))
        {
          uncheckedForwardForEach(root.arr,cursor,bound,action);
          this.cursor=bound;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Boolean> action)
      {
        final UncheckedList root;
        final int cursor,bound;
        if((cursor=this.cursor)!=(bound=(root=this.root).size))
        {
          uncheckedForwardForEach(root.arr,cursor,bound,action::accept);
          this.cursor=bound;
        }
      }
      @Override
      public boolean hasNext()
      {
        return cursor!=root.size;
      }
      @Override
      public boolean nextBoolean()
      {
        return (boolean)root.arr[cursor++];
      }
      @Override
      public void remove()
      {
        final UncheckedList root;
        eraseIndexHelper((root=this.root).arr,--cursor,--root.size);
      }
    }
    private static class BidirectionalItr implements OmniListIterator.OfBoolean
    {
      private transient final UncheckedList root;
      private transient int cursor;
      private transient int lastRet;
      private BidirectionalItr(final UncheckedList root)
      {
        this.root=root;
      }
      private BidirectionalItr(final UncheckedList root,final int cursor)
      {
        this.root=root;
        this.cursor=cursor;
      }
      @Override
      public void add(final boolean val)
      {
        final BooleanArrSeq root;
        final int rootSize,cursor=this.cursor;
        if((rootSize=(root=this.root).size)!=0)
        {
          root.uncheckedInsert(cursor,val,rootSize);
        }
        else
        {
          root.uncheckedInit(val);
        }
        this.cursor=cursor+1;
      }
      @Override
      public void forEachRemaining(BooleanConsumer action)
      {
        final int cursor,bound;
        final Unchecked root;
        if((cursor=this.cursor)!=(bound=(root=this.root).size))
        {
          uncheckedForwardForEach(root.arr,cursor,bound,action);
          this.cursor=bound;
          lastRet=bound-1;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Boolean> action)
      {
        final int cursor,bound;
        final Unchecked root;
        if((cursor=this.cursor)!=(bound=(root=this.root).size))
        {
          uncheckedForwardForEach(root.arr,cursor,bound,action::accept);
          this.cursor=bound;
          lastRet=bound-1;
        }
      }
      @Override
      public boolean hasNext()
      {
         return cursor!=root.size;
      }
      @Override
      public boolean hasPrevious()
      {
        return cursor!=0;
      }
      @Override
      public boolean nextBoolean()
      {
        final int lastRet;
        this.lastRet=lastRet=cursor++;
        return (boolean)root.arr[lastRet];
      }
      @Override
      public int nextIndex()
      {
        return cursor;
      }
      @Override
      public boolean previousBoolean()
      {
        final int lastRet;
        this.lastRet=lastRet=--cursor;
        return (boolean)root.arr[lastRet];
      }
      @Override
      public int previousIndex()
      {
        return cursor-1;
      }
      @Override
      public void remove()
      {
        final UncheckedList root;
        final int lastRet;
        eraseIndexHelper((root=this.root).arr,lastRet=this.lastRet,--root.size);
        cursor=lastRet;
      }
      @Override
      public void set(boolean val)
      {
        root.arr[lastRet]=val;
      }
    }
    private static class SubList extends AbstractBooleanList implements OmniList.OfBoolean
    {
      private static  void bubbleUpDecrementSize(SubList parent)
      {
        while(parent!=null)
        {
          --parent.size;
          parent=parent.parent;
        }
      }
      private static  void bubbleUpDecrementSize(SubList parent,final int numToRemove)
      {
        while(parent!=null)
        {
          parent.size-=numToRemove;
          parent=parent.parent;
        }
      }
      private static  void bubbleUpIncrementSize(SubList parent)
      {
        while(parent!=null)
        {
          ++parent.size;
          parent=parent.parent;
        }
      }
      transient final Unchecked root;
      transient final SubList parent;
      transient final int rootOffset;
      private SubList(final Unchecked root,final int rootOffset,final int size)
      {
        super(size);
        this.root=root;
        this.parent=null;
        this.rootOffset=rootOffset;
      }
      private SubList(final Unchecked root,final SubList parent,final int rootOffset,final int size)
      {
        super(size);
        this.root=root;
        this.parent=parent;
        this.rootOffset=rootOffset;
      }
      @Override
      public boolean add(final boolean val)
      {
        final BooleanArrSeq root;
        final int rootSize;
        final int size=this.size;
        if((rootSize=(root=this.root).size)!=0)
        {
          root.uncheckedInsert(size+rootOffset,val,rootSize);
        }
        else
        {
          root.uncheckedInit(val);
        }
        this.size=size+1;
        bubbleUpIncrementSize(parent);
        return true;
      }
      @Override
      public void add(final int index,final boolean val)
      {
        final BooleanArrSeq root;
        final int rootSize;
        if((rootSize=(root=this.root).size)!=0)
        {
          root.uncheckedInsert(index+rootOffset,val,rootSize);
        }
        else
        {
          root.uncheckedInit(val);
        }
        ++size;
        bubbleUpIncrementSize(parent);
      }
      @Override
      public void clear()
      {
        int size;
        if((size=this.size)!=0)
        {
          this.size=0;
          bubbleUpDecrementSize(parent,size);
          final int newRootSize;
          final Unchecked root;
          (root=this.root).size=newRootSize=root.size-size;
          final boolean[] arr;
          ArrCopy.semicheckedCopy(arr=root.arr,size+(size=rootOffset),arr,size,newRootSize-size);
        }
      }
      @Override
      public Object clone()
      {
        final boolean[] arr;
        final int size;
        if((size=this.size)!=0)
        {
          ArrCopy.uncheckedCopy(root.arr,rootOffset,arr=new boolean[size],0,size);
        }
        else
        {
          arr=null;
        }
        return new UncheckedList(size,arr);
      }
      @Override
      public boolean contains(Object val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val instanceof Boolean)
          {
            return uncheckedcontains(size,(boolean)(val));
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
          if(val instanceof Boolean)
          {
            return uncheckedindexOf(size,(boolean)(val));
          }
        }
        return -1;
      }
      @Override
      public int lastIndexOf(Object val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val instanceof Boolean)
          {
            return uncheckedlastIndexOf(size,(boolean)(val));
          }
        }
        return -1;
      }
      @Override
      public boolean contains(final boolean val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedcontains(size,(val));
        }
        return false;
      }
      @Override
      public boolean contains(final int val)
      {
        final int size;
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
          return this.uncheckedcontains(size,v);
        }
        return false;
      }
      @Override
      public boolean contains(final long val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final boolean v;
          if(val==0)
          {
            v=false;
          }
          else if(val==1)
          {
            v=true;
          }
          else
          {
            return false;
          }
          {
            return this.uncheckedcontains(size,v);
          }
        }
        return false;
      }
      @Override
      public boolean contains(final float val)
      {
        final int size;
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
            case TypeUtil.FLT_TRUE_BITS:
              v=true;
          }
          {
            return this.uncheckedcontains(size,v);
          }
        }
        return false;
      }
      @Override
      public boolean contains(final double val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final boolean v;
          final long bits;
          if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE)
          {
            v=false;
          }
          else if(bits==TypeUtil.DBL_TRUE_BITS)
          {
            v=true;
          }
          else
          {
            return false;
          }
          {
            return this.uncheckedcontains(size,v);
          }
        }
        return false;
      }
      @Override
      public boolean equals(Object val)
      {
        //TODO implements equals method
        return false;
      }
      @Override
      public void forEach(BooleanConsumer action)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          uncheckedForwardForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size,action);
        }
      }
      @Override
      public void forEach(Consumer<? super Boolean> action)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          uncheckedForwardForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size,action::accept);
        }
      }
      @Override
      public boolean getBoolean(int index)
      {
        return (boolean)root.arr[index+rootOffset];
      }
      @Override
      public int hashCode()
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          return forwardHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }
        return 1;
      }
      @Override
      public int indexOf(final boolean val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedindexOf(size,(val));
        }
        return -1;
      }
      @Override
      public int indexOf(final int val)
      {
        final int size;
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
          return this.uncheckedindexOf(size,v);
        }
        return -1;
      }
      @Override
      public int indexOf(final long val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final boolean v;
          if(val==0)
          {
            v=false;
          }
          else if(val==1)
          {
            v=true;
          }
          else
          {
            return -1;
          }
          {
            return this.uncheckedindexOf(size,v);
          }
        }
        return -1;
      }
      @Override
      public int indexOf(final float val)
      {
        final int size;
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
            case TypeUtil.FLT_TRUE_BITS:
              v=true;
          }
          {
            return this.uncheckedindexOf(size,v);
          }
        }
        return -1;
      }
      @Override
      public int indexOf(final double val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final boolean v;
          final long bits;
          if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE)
          {
            v=false;
          }
          else if(bits==TypeUtil.DBL_TRUE_BITS)
          {
            v=true;
          }
          else
          {
            return -1;
          }
          {
            return this.uncheckedindexOf(size,v);
          }
        }
        return -1;
      }
      @Override
      public int lastIndexOf(final boolean val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedlastIndexOf(size,(val));
        }
        return -1;
      }
      @Override
      public int lastIndexOf(final int val)
      {
        final int size;
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
          return this.uncheckedlastIndexOf(size,v);
        }
        return -1;
      }
      @Override
      public int lastIndexOf(final long val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final boolean v;
          if(val==0)
          {
            v=false;
          }
          else if(val==1)
          {
            v=true;
          }
          else
          {
            return -1;
          }
          {
            return this.uncheckedlastIndexOf(size,v);
          }
        }
        return -1;
      }
      @Override
      public int lastIndexOf(final float val)
      {
        final int size;
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
            case TypeUtil.FLT_TRUE_BITS:
              v=true;
          }
          {
            return this.uncheckedlastIndexOf(size,v);
          }
        }
        return -1;
      }
      @Override
      public int lastIndexOf(final double val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final boolean v;
          final long bits;
          if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE)
          {
            v=false;
          }
          else if(bits==TypeUtil.DBL_TRUE_BITS)
          {
            v=true;
          }
          else
          {
            return -1;
          }
          {
            return this.uncheckedlastIndexOf(size,v);
          }
        }
        return -1;
      }
      @Override
      public OmniIterator.OfBoolean iterator()
      {
        return new AscendingItr(this);
      }
      @Override
      public OmniListIterator.OfBoolean listIterator()
      {
        return new BidirectionalItr(this);
      }
      @Override
      public OmniListIterator.OfBoolean listIterator(int index)
      {
        return new BidirectionalItr(this,index+rootOffset);
      }
      @Override
      public void put(int index,boolean val)
      {
        root.arr[index+rootOffset]=val;
      }     
      @Override
      public boolean remove(Object val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val instanceof Boolean)
          {
            return uncheckedremoveVal(size,(boolean)(val));
          }
        }
        return false;
      }
      @Override
      public boolean removeVal(final boolean val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,(val));
        }
        return false;
      }
      @Override
      public boolean removeVal(final int val)
      {
        final int size;
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
          return this.uncheckedremoveVal(size,v);
        }
        return false;
      }
      @Override
      public boolean removeVal(final long val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final boolean v;
          if(val==0)
          {
            v=false;
          }
          else if(val==1)
          {
            v=true;
          }
          else
          {
            return false;
          }
          {
            return this.uncheckedremoveVal(size,v);
          }
        }
        return false;
      }
      @Override
      public boolean removeVal(final float val)
      {
        final int size;
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
            case TypeUtil.FLT_TRUE_BITS:
              v=true;
          }
          {
            return this.uncheckedremoveVal(size,v);
          }
        }
        return false;
      }
      @Override
      public boolean removeVal(final double val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final boolean v;
          final long bits;
          if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE)
          {
            v=false;
          }
          else if(bits==TypeUtil.DBL_TRUE_BITS)
          {
            v=true;
          }
          else
          {
            return false;
          }
          {
            return this.uncheckedremoveVal(size,v);
          }
        }
        return false;
      }
      @Override
      public boolean removeBooleanAt(int index)
      {
        final Unchecked root;
        final boolean[] arr;
        final var removed=(boolean)(arr=(root=this.root).arr)[index+=rootOffset];
        eraseIndexHelper(arr,index,--root.size);
        bubbleUpDecrementSize(parent);
        --size;
        return removed;
      }
      @Override
      public boolean removeIf(final BooleanPredicate filter)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return uncheckedRemoveIf(size,filter);
        }
        return false;
      }
      @Override
      public void replaceAll(final BooleanPredicate operator)
      {
        final int size;
        if(0!=((size=this.size)))
        {
          final int rootOffset;
          BooleanArrSeq.uncheckedreplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);
        }
      }
      @Override
      public void reverseSort()
      {
        final int size;
        if(1<((size=this.size)))
        {
          final int rootOffset;
          SortUtil.uncheckedreverseSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
        }
      }
      @Override
      public void sort()
      {
        final int size;
        if(1<((size=this.size)))
        {
          final int rootOffset;
          SortUtil.uncheckedsort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
        }
      }
      @Override
      public void sort(final BooleanComparator sorter)
      {
        final int size;
        if(1<((size=this.size)))
        {
          final int rootOffset;
          SortUtil.uncheckedcomparatorSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter);
        }
      }
      @Override
      public void replaceAll(final UnaryOperator<Boolean> operator)
      {
        final int size;
        if(0!=((size=this.size)))
        {
          final int rootOffset;
          BooleanArrSeq.uncheckedreplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator::apply);
        }
      }
      @Override
      public void sort(final Comparator<? super Boolean> sorter)
      {
        final int size;
        if(1<((size=this.size)))
        {
          final int rootOffset;
          SortUtil.uncheckedcomparatorSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter::compare);
        }
      }
      @Override
      public boolean removeIf(final Predicate<? super Boolean> filter)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return uncheckedRemoveIf(size,filter::test);
        }
        return false;
      }
      @Override
      public boolean set(int index,boolean val)
      {
        final boolean[] arr;
        final var oldVal=(boolean)(arr=root.arr)[index+=rootOffset];
        arr[index]=val;
        return oldVal;
      }
      @Override
      public OmniList.OfBoolean subList(int fromIndex,int toIndex)
      {
        return new SubList(root,this,this.rootOffset+fromIndex,toIndex-fromIndex);
      }
      @Override
      public boolean[] toBooleanArray()
      {
        final int size;
        if((size=this.size)!=0)
        {
          final boolean[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new boolean[size],0,size);
          return dst;
        }
        return OmniArray.OfBoolean.DEFAULT_ARR;
      }
      @Override
      public Boolean[] toArray()
      {
        final int size;
        if((size=this.size)!=0)
        {
          final Boolean[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new Boolean[size],0,size);
          return dst;
        }
        return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
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
      @Override
      public float[] toFloatArray()
      {
        final int size;
        if((size=this.size)!=0)
        {
          final float[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new float[size],0,size);
          return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
      }
      @Override
      public long[] toLongArray()
      {
        final int size;
        if((size=this.size)!=0)
        {
          final long[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new long[size],0,size);
          return dst;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
      }
      @Override
      public int[] toIntArray()
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new int[size],0,size);
          return dst;
        }
        return OmniArray.OfInt.DEFAULT_ARR;
      }
      @Override
      public short[] toShortArray()
      {
        final int size;
        if((size=this.size)!=0)
        {
          final short[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new short[size],0,size);
          return dst;
        }
        return OmniArray.OfShort.DEFAULT_ARR;
      }
      @Override
      public byte[] toByteArray()
      {
        final int size;
        if((size=this.size)!=0)
        {
          final byte[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new byte[size],0,size);
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_ARR;
      }
      @Override
      public char[] toCharArray()
      {
        final int size;
        if((size=this.size)!=0)
        {
          final char[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new char[size],0,size);
          return dst;
        }
        return OmniArray.OfChar.DEFAULT_ARR;
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
      @Override
      public String toString()
      {
        final int size;
        if((size=this.size)!=0)
        {
          final StringBuilder builder;
          final int rootOffset;
          forwardToString(root.arr,rootOffset=this.rootOffset,rootOffset+size,builder=new StringBuilder("["));
          return builder.append(']').toString();
        }
        return "[]";
      }
      private boolean uncheckedcontains(int size,boolean val)
      { 
        final int rootOffset;
        return BooleanArrSeq.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
      }
      private int uncheckedindexOf(int size,boolean val)
      { 
        final int rootOffset;
        return BooleanArrSeq.uncheckedindexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
      }
      private int uncheckedlastIndexOf(int size,boolean val)
      { 
        final int rootOffset;
        return BooleanArrSeq.uncheckedlastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
      }
      private boolean uncheckedRemoveIf(int size,final BooleanPredicate filter)
      {
        final Unchecked root;
        int srcOffset;
        final int srcBound=(srcOffset=rootOffset)+size;
        int dstOffset;
        final boolean[] arr;
        boolean v;
        if(filter.test(v=(arr=(root=this.root).arr)[srcOffset]))
        {
          dstOffset=srcOffset;
          for(;;)
          {
            if(++srcOffset==srcBound)
            {
              break;
            }
            if(v^arr[srcOffset])
            {
              if(!filter.test(v=!v))
              {
                dstOffset=pullSurvivorsDown(arr,dstOffset,srcOffset,srcBound,v);
              }
              break;
            }
          }
        }
        else
        {
          for(;;)
          {
            if(++srcOffset==srcBound)
            {
              return false;
            }
            if(v^arr[srcOffset])
            {
              if(!filter.test(!v))
              {
                return false;
              }
              dstOffset=pullSurvivorsDown(arr,srcOffset,srcBound,v);
              break;
            }
          }
        }
        this.size=size-(size=((BooleanArrSeq)root).finalizeSubListBatchRemove(arr,dstOffset,srcBound));
        bubbleUpDecrementSize(parent,size);
        return true;
      }
      private boolean uncheckedremoveVal(int size,boolean val)
      {
        final Unchecked root;final var arr=(root=this.root).arr;int index;final int bound=(index=this.rootOffset)+(--size);
        for(;val^(arr[index]);)
        {
          if(++index==bound)
          {
            return false;
          }
        }
        eraseIndexHelper(arr,index,--root.size);
        bubbleUpDecrementSize(parent);
        this.size=size;
        return true;
      }
      private static class AscendingItr extends AbstractBooleanItr implements OmniIterator.OfBoolean
      {
        private transient final SubList parent;
        private transient int cursor;
        private AscendingItr(SubList parent)
        {
           this.parent=parent;
           this.cursor=parent.rootOffset;
        }
        @Override
        public void forEachRemaining(BooleanConsumer action)
        {
          final int cursor,bound;
          final SubList parent;
          if((cursor=this.cursor)!=(bound=(parent=this.parent).rootOffset+parent.size))
          {
            uncheckedForwardForEach(parent.root.arr,cursor,bound,action);
            this.cursor=bound;
          }
        }
        @Override
        public void forEachRemaining(Consumer<? super Boolean> action)
        {
          final int cursor,bound;
          final SubList parent;
          if((cursor=this.cursor)!=(bound=(parent=this.parent).rootOffset+parent.size))
          {
            uncheckedForwardForEach(parent.root.arr,cursor,bound,action::accept);
            this.cursor=bound;
          }
        }
        @Override
        public boolean hasNext()
        {
          final SubList parent;
          return cursor!=(parent=this.parent).rootOffset+parent.size;
        }
        @Override
        public boolean nextBoolean()
        {
          return (boolean)parent.root.arr[cursor++];
        }
        @Override
        public void remove()
        {
          final SubList parent;
          final Unchecked root;
          eraseIndexHelper((root=(parent=this.parent).root).arr,--cursor,--root.size);
          bubbleUpDecrementSize(parent.parent);
          --parent.size;
        }
      }
      private static class BidirectionalItr implements OmniListIterator.OfBoolean
      {
        private transient final SubList parent;
        private transient int cursor;
        private transient int lastRet;
        private BidirectionalItr(final SubList parent)
        {
          this.parent=parent;
          this.cursor=parent.rootOffset;
        }
        private BidirectionalItr(final SubList parent,final int cursor)
        {
          this.parent=parent;
          this.cursor=cursor;
        }
        @Override
        public void add(final boolean val)
        {
          final SubList parent;
          final BooleanArrSeq root;
          final int rootSize,cursor=this.cursor;
          if((rootSize=(root=(parent=this.parent).root).size)!=0)
          {
            root.uncheckedInsert(cursor,val,rootSize);
          }
          else
          {
            root.uncheckedInit(val);
          }
          bubbleUpIncrementSize(parent.parent);
          ++parent.size;
          this.cursor=cursor+1;
        }
        @Override
        public void forEachRemaining(BooleanConsumer action)
        {
          final int cursor,bound;
          final SubList parent;
          if((cursor=this.cursor)!=(bound=(parent=this.parent).rootOffset+parent.size))
          {
            uncheckedForwardForEach(parent.root.arr,cursor,bound,action);
            this.cursor=bound;
            lastRet=bound-1;
          }
        }
        @Override
        public void forEachRemaining(Consumer<? super Boolean> action)
        {
          final int cursor,bound;
          final SubList parent;
          if((cursor=this.cursor)!=(bound=(parent=this.parent).rootOffset+parent.size))
          {
            uncheckedForwardForEach(parent.root.arr,cursor,bound,action::accept);
            this.cursor=bound;
            lastRet=bound-1;
          }
        }
        @Override
        public boolean hasNext()
        {
          final SubList parent;
          return cursor!=(parent=this.parent).rootOffset+parent.size;
        }
        @Override
        public boolean hasPrevious()
        {
          return cursor!=parent.rootOffset;
        }
        @Override
        public boolean nextBoolean()
        {
          final int lastRet;
          this.lastRet=lastRet=cursor++;
          return (boolean)parent.root.arr[lastRet];
        }
        @Override
        public int nextIndex()
        {
          return cursor-parent.rootOffset;
        }
        @Override
        public boolean previousBoolean()
        {
          final int lastRet;
          this.lastRet=lastRet=--cursor;
          return (boolean)parent.root.arr[lastRet];
        }
        @Override
        public int previousIndex()
        {
          return cursor-parent.rootOffset-1;
        }
        @Override
        public void remove()
        {
          final SubList parent;
          final Unchecked root;
          final int lastRet;
          eraseIndexHelper((root=(parent=this.parent).root).arr,lastRet=this.lastRet,--root.size);
          bubbleUpDecrementSize(parent.parent);
          --parent.size;
          cursor=lastRet;
        }
        @Override
        public void set(boolean val)
        {
          parent.root.arr[lastRet]=val;
        }
      }
    }
  }
  public static class UncheckedStack extends Unchecked implements OmniStack.OfBoolean
  {
    public UncheckedStack()
    {
      super();
    }
    public UncheckedStack(final int capacity)
    {
      super(capacity);
    }
    public UncheckedStack(final int size,final boolean[] arr)
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
      return new UncheckedStack(size,arr);
    }
    @Override
    public boolean equals(final Object val)
    {
      //TODO implements equals method
      return false;
    }
    @Override
    public OmniIterator.OfBoolean iterator()
    {
      return new UncheckedDescendingItr(this);
    }
        @Override
        public boolean pollBoolean()
        {
          final int size;
          if((size=this.size)!=0)
          {
            return (boolean)(((BooleanArrSeq)this).uncheckedPop(size-1));
          }
          return false;
        }
        @Override
        public Boolean poll()
        {
          final int size;
          if((size=this.size)!=0)
          {
            return (Boolean)(((BooleanArrSeq)this).uncheckedPop(size-1));
          }
          return null;
        }
        @Override
        public double pollDouble()
        {
          final int size;
          if((size=this.size)!=0)
          {
            return TypeUtil.castToDouble(((BooleanArrSeq)this).uncheckedPop(size-1));
          }
          return Double.NaN;
        }
        @Override
        public float pollFloat()
        {
          final int size;
          if((size=this.size)!=0)
          {
            return TypeUtil.castToFloat(((BooleanArrSeq)this).uncheckedPop(size-1));
          }
          return Float.NaN;
        }
        @Override
        public long pollLong()
        {
          final int size;
          if((size=this.size)!=0)
          {
            return TypeUtil.castToLong(((BooleanArrSeq)this).uncheckedPop(size-1));
          }
          return Long.MIN_VALUE;
        }
        @Override
        public int pollInt()
        {
          final int size;
          if((size=this.size)!=0)
          {
            return (int)TypeUtil.castToByte(((BooleanArrSeq)this).uncheckedPop(size-1));
          }
          return Integer.MIN_VALUE;
        }
        @Override
        public short pollShort()
        {
          final int size;
          if((size=this.size)!=0)
          {
            return (short)TypeUtil.castToByte(((BooleanArrSeq)this).uncheckedPop(size-1));
          }
          return Short.MIN_VALUE;
        }
        @Override
        public byte pollByte()
        {
          final int size;
          if((size=this.size)!=0)
          {
            return TypeUtil.castToByte(((BooleanArrSeq)this).uncheckedPop(size-1));
          }
          return Byte.MIN_VALUE;
        }
        @Override
        public char pollChar()
        {
          final int size;
          if((size=this.size)!=0)
          {
            return TypeUtil.castToChar(((BooleanArrSeq)this).uncheckedPop(size-1));
          }
          return Character.MIN_VALUE;
        }
    @Override
    void uncheckedCopyInto(final boolean[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Object[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Boolean[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final double[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final float[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final long[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final int[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final short[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final byte[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final char[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedForEach(final int size,final BooleanConsumer action)
    {
      uncheckedReverseForEach(arr,0,size,action);
    }
    @Override
    int uncheckedHashCode(final int size)
    {
      return reverseHashCode(arr,0,size);
    }
    @Override
    boolean uncheckedremoveVal(int size,final boolean val)
    {
      final var arr=this.arr;int index;
      for(index=--size;val^(arr[index]);--index)
      {
        if(index==0)
        {
          return false;
        }
      }
      eraseIndexHelper(arr,index,size);
      this.size=size;
      return true;
    }
    @Override
    void uncheckedToString(final int size,final StringBuilder builder)
    {
      reverseToString(arr,0,size,builder);
    }
    private static class UncheckedDescendingItr extends AbstractBooleanItr implements OmniIterator.OfBoolean
    {
      private transient final UncheckedStack root;
      private transient int cursor;
      private UncheckedDescendingItr(UncheckedStack root)
      {
        this.root=root;
        cursor=root.size;
      }
      @Override
      public void forEachRemaining(BooleanConsumer action)
      {
        final int cursor;
        if((cursor=this.cursor)!=0)
        {
          uncheckedReverseForEach(root.arr,0,cursor,action);
          this.cursor=0;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Boolean> action)
      {
        final int cursor;
        if((cursor=this.cursor)!=0)
        {
          uncheckedReverseForEach(root.arr,0,cursor,action::accept);
          this.cursor=0;
        }
      }
      @Override
      public boolean hasNext()
      {
        return cursor!=0;
      }
      @Override
      public boolean nextBoolean()
      {
        return (boolean)root.arr[--cursor];
      }
      @Override
      public void remove()
      {
        final UncheckedStack root;
        eraseIndexHelper((root=this.root).arr,cursor,--root.size);
      }
    }
  }
}
