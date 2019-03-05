package omni.impl.seq;
import omni.api.OmniCollection;
import omni.util.OmniArray;
import omni.api.OmniList;
import omni.api.OmniStack;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.Comparator;
import omni.util.ArrCopy;
import omni.util.BooleanSortUtil;
import omni.impl.CheckedCollection;
import java.util.NoSuchElementException;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;
import omni.util.TypeUtil;
import omni.function.BooleanComparator;
import omni.function.BooleanPredicate;
import omni.function.BooleanConsumer;
import omni.util.ToStringUtil;
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
  public void clear()
  {
    this.size=0;
  }
  @Override
  public boolean isEmpty()
  {
    return this.size==0;
  }
  @Override
  public int size()
  {
    return this.size;
  }
  @Override
  public boolean add(boolean val)
  {
    push(val);
    return true;
  }
  @Override
  public boolean add(Boolean val)
  {
    push((boolean)val);
    return true;
  }
  @Override
  public void forEach(BooleanConsumer action)
  {
    int size;
    if((size=this.size)!=0)
    {
      uncheckedForEach(size,action);
    }
  }
  @Override
  public void forEach(Consumer<? super Boolean> action)
  {
    int size;
    if((size=this.size)!=0)
    {
      uncheckedForEach(size,action::accept);
    }
  }
  @Override
  public boolean removeIf(BooleanPredicate filter)
  {
    int size;
    if((size=this.size)!=0)
    {
      return uncheckedRemoveIf(size,filter);
    }
    return false;
  }
  @Override
  public boolean removeIf(Predicate<? super Boolean> filter)
  {
    int size;
    if((size=this.size)!=0)
    {
      return uncheckedRemoveIf(size,filter::test);
    }
    return false;
  }
  @Override
  public int hashCode()
  {
    int size;
    if((size=this.size)!=0)
    {
      return uncheckedHashCode(size);
    }
    return 1;
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
        (buffer=new char[size*(7)])[size=uncheckedToString(size,buffer)]=']';
        buffer[0]='[';
        return new String(buffer,0,size+1);
      }
      else
      {
        final ToStringUtil.OmniStringBuilder builder;
        uncheckedToString(size,builder=new ToStringUtil.OmniStringBuilder(1,new char[size<=(int)(OmniArray.MAX_ARR_SIZE/6.5f)?(size*6)+(size>>>1):OmniArray.MAX_ARR_SIZE]));
        builder.uncheckedAppendChar(']');
        (buffer=builder.buffer)[0]='[';
        return new String(buffer,0,builder.size);
      }
    }
    return "[]";
  }
  @Override
  public <T> T[] toArray(T[] dst)
  {
    int size;
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
  private boolean uncheckedcontains (int size
  ,boolean val
  )
  {
    for(final var arr=this.arr;;)
    {
      if(
      val==arr[--size]
      )
      {
        return true;
      }
      if(size==0)
      {
        return false;
      }
    }
  }
  abstract boolean uncheckedremoveVal(int size,boolean val);
  @Override
  public
  boolean
  removeVal(boolean val)
  {
    int size;
    if((size=this.size)!=0)
    {
      return uncheckedremoveVal(size,(val));
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
      return uncheckedremoveVal(size,v);
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
        return uncheckedremoveVal(size,v);
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
        return uncheckedremoveVal(size,v);
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
        return uncheckedremoveVal(size,v);
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
        return uncheckedremoveVal(size,(boolean)(val));
      }
    }
    return false;
  }
  @Override
  public
  boolean
  contains(boolean val)
  {
    int size;
    if((size=this.size)!=0)
    {
      return uncheckedcontains(size,(val));
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
      return uncheckedcontains(size,v);
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
        return uncheckedcontains(size,v);
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
        return uncheckedcontains(size,v);
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
        return uncheckedcontains(size,v);
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
        return uncheckedcontains(size,(boolean)(val));
      }
    }
    return false;
  }
  abstract void uncheckedCopyInto(Object[] dst,int size);
  abstract void uncheckedCopyInto(boolean[] dst,int size);
  @Override
  public boolean[] toBooleanArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final boolean[] copy;
      uncheckedCopyInto(copy=new boolean[size],size);
      return copy;
    }
    return OmniArray.OfBoolean.DEFAULT_ARR;
  }
  abstract void uncheckedCopyInto(Boolean[] dst,int size);
  @Override
  public Boolean[] toArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final Boolean[] copy;
      uncheckedCopyInto(copy=new Boolean[size],size);
      return copy;
    }
    return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
  }
  abstract void uncheckedCopyInto(double[] dst,int size);
  @Override
  public double[] toDoubleArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final double[] copy;
      uncheckedCopyInto(copy=new double[size],size);
      return copy;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  abstract void uncheckedCopyInto(float[] dst,int size);
  @Override
  public float[] toFloatArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final float[] copy;
      uncheckedCopyInto(copy=new float[size],size);
      return copy;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  abstract void uncheckedCopyInto(long[] dst,int size);
  @Override
  public long[] toLongArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final long[] copy;
      uncheckedCopyInto(copy=new long[size],size);
      return copy;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  abstract void uncheckedCopyInto(int[] dst,int size);
  @Override
  public int[] toIntArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final int[] copy;
      uncheckedCopyInto(copy=new int[size],size);
      return copy;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  abstract void uncheckedCopyInto(short[] dst,int size);
  @Override
  public short[] toShortArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final short[] copy;
      uncheckedCopyInto(copy=new short[size],size);
      return copy;
    }
    return OmniArray.OfShort.DEFAULT_ARR;
  }
  abstract void uncheckedCopyInto(char[] dst,int size);
  @Override
  public char[] toCharArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final char[] copy;
      uncheckedCopyInto(copy=new char[size],size);
      return copy;
    }
    return OmniArray.OfChar.DEFAULT_ARR;
  }
  abstract void uncheckedCopyInto(byte[] dst,int size);
  @Override
  public byte[] toByteArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final byte[] copy;
      uncheckedCopyInto(copy=new byte[size],size);
      return copy;
    }
    return OmniArray.OfByte.DEFAULT_ARR;
  }
  abstract int uncheckedHashCode(int size);
  abstract int uncheckedToString(int size,char[] buffer);
  abstract void uncheckedToString(int size,ToStringUtil.OmniStringBuilder builder);
  abstract void uncheckedForEach(int size,BooleanConsumer action);
  abstract boolean uncheckedRemoveIf(int size,BooleanPredicate filter);
  private void uncheckedInit(boolean val)
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
    this.size=1;
  }
  private void uncheckedAppend(int size,boolean val)
  {
    boolean[] arr;
    if((arr=this.arr).length==size)
    {
      ArrCopy.uncheckedCopy(arr,0,arr=new boolean[OmniArray.growBy50Pct(size)],0,size);
      this.arr=arr;
    }
    arr[size]=val;
    this.size=size+1;
  }
  public void push(boolean val)
  {
    int size;
    if((size=this.size)!=0)
    {
      uncheckedAppend(size,val);
    }
    else
    {
      uncheckedInit(val);
    }
  }
  private void uncheckedInsert(int size,int index,boolean val)
  {
    int tailDist;
    if((tailDist=size-index)==0)
    {
      uncheckedAppend(size,val);
    }
    else
    {
      boolean[] arr;
      if((arr=this.arr).length==size)
      {
        boolean[] newArr;
        ArrCopy.semicheckedCopy(arr,0,newArr=new boolean[OmniArray.growBy50Pct(size)],0,index);
        ArrCopy.uncheckedCopy(arr,index,newArr,index+1,tailDist);
        this.arr=arr=newArr;
      }
      else
      {
        ArrCopy.uncheckedCopy(arr,index,arr,index+1,tailDist);
      }
      arr[index]=val;
      this.size=size+1;
    }
  }
  public void push(Boolean val)
  {
    push((boolean)val);
  }
  private boolean uncheckedremoveAtIndex(int index,int size)
  {
    final boolean[] arr;
    final boolean ret=(boolean)(arr=this.arr)[index];
    ArrCopy.semicheckedSelfCopy(arr,index+1,index,(--size)-index);
    this.size=size;
    return ret;
  }
  public static class UncheckedArrStack extends BooleanArrSeq implements OmniStack.OfBoolean
  {
    public UncheckedArrStack()
    {
      super();
    }
    public UncheckedArrStack(int initialCapacity)
    {
      super(initialCapacity);
    }
    public UncheckedArrStack(int size,boolean[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean popBoolean()
    {
      return (boolean)arr[--this.size];
    }
    @Override
    public Boolean pop()
    {
      return popBoolean();
    }
    @Override
    public Object clone()
    {
      boolean[] copy;
      int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new boolean[size],0,size);
      }
      else
      {
        copy=OmniArray.OfBoolean.DEFAULT_ARR;
      }
      return new UncheckedArrStack(size,copy);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO
      return false;
    }
    @Override
    public
    int
    search(boolean val)
    {
      int size;
      if((size=this.size)!=0)
      {
        return uncheckedsearch(size,(val));
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
        return uncheckedsearch(size,v);
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
          return uncheckedsearch(size,v);
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
          return uncheckedsearch(size,v);
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
          return uncheckedsearch(size,v);
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
          return uncheckedsearch(size,(boolean)(val));
        }
      }
      return -1;
    }
    @Override
    public boolean pollBoolean()
    {
      int size;
      if((size=this.size)!=0)
      {
        final boolean ret=(boolean)((boolean)arr[--size]);
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
        final Boolean ret=(Boolean)((boolean)arr[--size]);
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
        final double ret=TypeUtil.castToDouble((boolean)arr[--size]);
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
        final float ret=TypeUtil.castToFloat((boolean)arr[--size]);
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
        final long ret=TypeUtil.castToLong((boolean)arr[--size]);
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
        final int ret=(int)TypeUtil.castToByte((boolean)arr[--size]);
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
        final short ret=(short)TypeUtil.castToByte((boolean)arr[--size]);
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
        final char ret=TypeUtil.castToChar((boolean)arr[--size]);
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
        final byte ret=TypeUtil.castToByte((boolean)arr[--size]);
        this.size=size;
        return ret;
      }
      return Byte.MIN_VALUE;
    }
    public boolean peekBoolean()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (boolean)((boolean)arr[size-1]);
      }
      return false;
    }
    public Boolean peek()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (Boolean)((boolean)arr[size-1]);
      }
      return null;
    }
    public double peekDouble()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return TypeUtil.castToDouble((boolean)arr[size-1]);
      }
      return Double.NaN;
    }
    public float peekFloat()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return TypeUtil.castToFloat((boolean)arr[size-1]);
      }
      return Float.NaN;
    }
    public long peekLong()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return TypeUtil.castToLong((boolean)arr[size-1]);
      }
      return Long.MIN_VALUE;
    }
    public int peekInt()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (int)TypeUtil.castToByte((boolean)arr[size-1]);
      }
      return Integer.MIN_VALUE;
    }
    public short peekShort()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (short)TypeUtil.castToByte((boolean)arr[size-1]);
      }
      return Short.MIN_VALUE;
    }
    public char peekChar()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return TypeUtil.castToChar((boolean)arr[size-1]);
      }
      return Character.MIN_VALUE;
    }
    public byte peekByte()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return TypeUtil.castToByte((boolean)arr[size-1]);
      }
      return Byte.MIN_VALUE;
    }
    @Override
    void uncheckedCopyInto(boolean[] dst,int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(Boolean[] dst,int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(double[] dst,int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(float[] dst,int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(long[] dst,int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(int[] dst,int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(short[] dst,int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(char[] dst,int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(byte[] dst,int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(Object[] dst,int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,size);
    }
    @Override
    boolean
    uncheckedremoveVal (int size
    ,boolean val
    )
    {
      final var arr=this.arr;
      for(int i=--size;;)
      {
        if(
        val==arr[i]
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
    private int uncheckedsearch (int size
    ,boolean val
    )
    {
      final var arr=this.arr;
      for(int i=size-1;;--i)
      {
        if(
        val==arr[i]
        )
        {
          return size-i;
        }
        if(i==0)
        {
          return -1;
        }
      }
    }
    @Override
    int uncheckedHashCode(int size)
    {
      final boolean[] arr;
      int hash;
      for(hash=31+Boolean.hashCode((arr=this.arr)[--size]);size!=0;hash=hash*31+Boolean.hashCode(arr[--size])){}
      return hash;
    }
    @Override
    int uncheckedToString(int size,char[] buffer)
    {
      final boolean[] arr;
      int bufferOffset;
      for(bufferOffset=ToStringUtil.getStringBoolean((arr=this.arr)[--size],buffer,1);size!=0;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringBoolean(arr[--size],buffer,bufferOffset)){}
      return bufferOffset;
    }
    @Override
    void uncheckedToString(int size,ToStringUtil.OmniStringBuilder builder)
    {
      final boolean[] arr;
      for(builder.uncheckedAppendBoolean((arr=this.arr)[--size]);size!=0;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendBoolean(arr[--size])){}
    }
    @Override
    void uncheckedForEach(int size,BooleanConsumer action)
    {
      final var arr=this.arr;
      for(;;)
      {
        action.accept((boolean)arr[--size]);
        if(size==0)
        {
          return;
        }
      }
    }
    @Override
    boolean uncheckedRemoveIf(int size,BooleanPredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public OmniIterator.OfBoolean iterator()
    {
      //TODO
      return null;
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor)
    {
      int size;
      T[] dst=arrConstructor.apply(size=this.size);
      if(size!=0)
      {
        ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,size);
      }
      return dst;
    }
  }
  public static class CheckedArrStack extends UncheckedArrStack
  {
    transient int modCount;
    public CheckedArrStack()
    {
      super();
    }
    public CheckedArrStack(int initialCapacity)
    {
      super(initialCapacity);
    }
    public CheckedArrStack(int size,boolean[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO
      return false;
    }
    @Override
    public boolean popBoolean()
    {
      int size;
      if((size=this.size)!=0)
      {
        ++this.modCount;
        final boolean ret=(boolean)arr[--size];
        this.size=size;
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override
    public Object clone()
    {
      boolean[] copy;
      int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new boolean[size],0,size);
      }
      else
      {
        copy=OmniArray.OfBoolean.DEFAULT_ARR;
      }
      return new CheckedArrStack(size,copy);
    }
    @Override
    public boolean pollBoolean()
    {
      int size;
      if((size=this.size)!=0)
      {
        final boolean ret=(boolean)((boolean)arr[--size]);
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
        final Boolean ret=(Boolean)((boolean)arr[--size]);
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
        final double ret=TypeUtil.castToDouble((boolean)arr[--size]);
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
        final float ret=TypeUtil.castToFloat((boolean)arr[--size]);
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
        final long ret=TypeUtil.castToLong((boolean)arr[--size]);
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
        final int ret=(int)TypeUtil.castToByte((boolean)arr[--size]);
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
        final short ret=(short)TypeUtil.castToByte((boolean)arr[--size]);
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
        final char ret=TypeUtil.castToChar((boolean)arr[--size]);
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
        final byte ret=TypeUtil.castToByte((boolean)arr[--size]);
        ++this.modCount;
        this.size=size;
        return ret;
      }
      return Byte.MIN_VALUE;
    }
    @Override
    boolean
    uncheckedremoveVal (int size
    ,boolean val
    )
    {
      final var arr=this.arr;
      for(int i=--size;;)
      {
        if(
        val==arr[i]
        )
        {
          ++this.modCount;
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
    @Override
    void uncheckedForEach(int size,BooleanConsumer action)
    {
      int modCount=this.modCount;
      try
      {
        super.uncheckedForEach(size,action);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override
    boolean uncheckedRemoveIf(int size,BooleanPredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public void push(boolean val)
    {
      ++this.modCount;
      super.push(val);
    }
    @Override
    public OmniIterator.OfBoolean iterator()
    {
      //TODO
      return null;
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor)
    {
      return super.toArray((arrSize)->
      {
        int modCount=this.modCount;
        try
        {
          return arrConstructor.apply(arrSize);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      });
    }
  }
  private static class UncheckedArrList extends BooleanArrSeq implements OmniList.OfBoolean
  {
    private static class SubListImpl implements OmniList.OfBoolean
    {
      transient final UncheckedArrList root;
      transient final SubListImpl parent;
      transient final int rootOffset;
      transient int size;
      SubListImpl(UncheckedArrList root,int rootOffset,int size
      )
      {
        this.root=root;
        this.parent=null;
        this.rootOffset=rootOffset;
        this.size=size;
      }
      SubListImpl(SubListImpl parent,int rootOffset,int size
      )
      {
        this.root=parent.root;
        this.parent=parent;
        this.rootOffset=rootOffset;
        this.size=size;
      }
      @Override
      public Object clone()
      {
        final boolean[] copy;
        int size;
        if((size=this.size)!=0)
        {
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new boolean[size],0,size);
        }
        else
        {
          copy=OmniArray.OfBoolean.DEFAULT_ARR;
        }
        return new UncheckedArrList(size,copy);
      }
      @Override
      public boolean add(boolean val)
      {
        int rootSize;
        final UncheckedArrList root;
        if((rootSize=(root=this.root).size)!=0)
        {
          for(var curr=parent;curr!=null;++parent.size){}
          ((BooleanArrSeq)root).uncheckedInsert(rootSize,this.rootOffset,val);
          ++this.size;
        }
        else
        {
          for(var curr=parent;curr!=null;parent.size=1){}
          ((BooleanArrSeq)root).uncheckedInit(val);
          this.size=1;
        }
        return true;
      }
      @Override
      public void add(int index,boolean val)
      {
        int rootSize;
        final UncheckedArrList root;
        if((rootSize=(root=this.root).size)!=0)
        {
          for(var curr=parent;curr!=null;++parent.size){}
          ((BooleanArrSeq)root).uncheckedInsert(rootSize,index+this.rootOffset,val);
          ++this.size;
        }
        else
        {
          for(var curr=parent;curr!=null;parent.size=1){}
          ((BooleanArrSeq)root).uncheckedInit(val);
          this.size=1;
        }
      }
      @Override
      public boolean add(Boolean val)
      {
        return add((boolean)val);
      }
      @Override
      public void add(int index,Boolean val)
      {
        add(index,(boolean)val);
      }
      @Override
      public void forEach(BooleanConsumer action)
      {
        {
          int size;
          if((size=this.size)!=0)
          {
            ((UncheckedArrList)root).sublistForEach(this.rootOffset,size,action);
          }
        }
      }
      @Override
      public void forEach(Consumer<? super Boolean> action)
      {
        {
          int size;
          if((size=this.size)!=0)
          {
            ((UncheckedArrList)root).sublistForEach(this.rootOffset,size,action::accept);
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
      public boolean removeIf(Predicate<? super Boolean> action)
      {
        //TODO
        return false;
      }
      @Override
      public boolean[] toBooleanArray()
      {
        final int size;
        if((size=this.size)!=0)
        {
          final boolean[] copy;
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new boolean[size],0,size);
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
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new Boolean[size],0,size);
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
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new double[size],0,size);
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
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new float[size],0,size);
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
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new long[size],0,size);
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
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new int[size],0,size);
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
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new short[size],0,size);
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
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new char[size],0,size);
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
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new byte[size],0,size);
          return copy;
        }
        return OmniArray.OfByte.DEFAULT_ARR;
      }
      @Override
      public <T> T[] toArray(IntFunction<T[]> arrConstructor)
      {
        int size;
        final T[] dst;
        {
          dst=arrConstructor.apply(size=this.size);
        }
        if(size!=0)
        {
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst,0,size);
        }
        return dst;
      }
      @Override
      public void put(int index,boolean val)
      {
        root.arr[index+this.rootOffset]=val;
      }
      @Override
      public boolean getBoolean(int index)
      {
        return (boolean)root.arr[index+this.rootOffset];
      }
      @Override
      public Boolean get(int index)
      {
        return getBoolean(index);
      }
      @Override
      public <T> T[] toArray(T[] dst)
      {
        int size;
        if((size=this.size)!=0)
        {
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=OmniArray.uncheckedArrResize(size,dst),0,size);
        }
        else if(dst.length!=0)
        {
          dst[0]=null;
        }
        return dst;
      }
      @Override
      public void clear()
      {
        int size;
        if((size=this.size)!=0)
        {
          for(var curr=parent;curr!=null;parent.size-=size){}
          final UncheckedArrList root;
          int rootOffset;
          ArrCopy.semicheckedSelfCopy((root=this.root).arr,(rootOffset=this.rootOffset)+size,rootOffset,(root.size-=size)-rootOffset);
          this.size=0;
        }
      }
      @Override
      public OmniIterator.OfBoolean iterator()
      {
        //TODO
        return null;
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
      public
      boolean
      contains(boolean val)
      {
        int size;
        if((size=this.size)!=0)
        {
          return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,(val));
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
          return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,v);
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
            return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,v);
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
            return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,v);
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
            return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,v);
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
            return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,(boolean)(val));
          }
        }
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
          return uncheckedremoveVal(size,(val));
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
          return uncheckedremoveVal(size,v);
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
            return uncheckedremoveVal(size,v);
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
            return uncheckedremoveVal(size,v);
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
            return uncheckedremoveVal(size,v);
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
            return uncheckedremoveVal(size,(boolean)(val));
          }
        }
        return false;
      }
      @Override
      public
      int
      indexOf(boolean val)
      {
        int size;
        if((size=this.size)!=0)
        {
          return ((UncheckedArrList)root).sublistindexOf(this.rootOffset,size,(val));
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
          return ((UncheckedArrList)root).sublistindexOf(this.rootOffset,size,v);
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
            return ((UncheckedArrList)root).sublistindexOf(this.rootOffset,size,v);
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
            return ((UncheckedArrList)root).sublistindexOf(this.rootOffset,size,v);
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
            return ((UncheckedArrList)root).sublistindexOf(this.rootOffset,size,v);
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
            return ((UncheckedArrList)root).sublistindexOf(this.rootOffset,size,(boolean)(val));
          }
        }
        return -1;
      }
      @Override
      public
      int
      lastIndexOf(boolean val)
      {
        int size;
        if((size=this.size)!=0)
        {
          return ((UncheckedArrList)root).sublistlastIndexOf(this.rootOffset,size,(val));
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
          return ((UncheckedArrList)root).sublistlastIndexOf(this.rootOffset,size,v);
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
            return ((UncheckedArrList)root).sublistlastIndexOf(this.rootOffset,size,v);
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
            return ((UncheckedArrList)root).sublistlastIndexOf(this.rootOffset,size,v);
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
            return ((UncheckedArrList)root).sublistlastIndexOf(this.rootOffset,size,v);
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
            return ((UncheckedArrList)root).sublistlastIndexOf(this.rootOffset,size,(boolean)(val));
          }
        }
        return -1;
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
            final boolean[] arr;
            int bufferOffset,i;
            for(bufferOffset=ToStringUtil.getStringBoolean((arr=root.arr)[i=this.rootOffset],buffer=new char[size*(7)],1),size+=i;++i!=size;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringBoolean(arr[i],buffer,bufferOffset)){}
            buffer[bufferOffset]=']';
            buffer[0]='[';
            return new String(buffer,0,bufferOffset+1);
          }
          else
          {
            final ToStringUtil.OmniStringBuilder builder;
            final boolean[] arr;
            int i;
            for((builder=new ToStringUtil.OmniStringBuilder(1,new char[size<=(int)(OmniArray.MAX_ARR_SIZE/6.5f)?(size*6)+(size>>>1):OmniArray.MAX_ARR_SIZE])).uncheckedAppendBoolean((arr=root.arr)[i=this.rootOffset]),size+=i;++i!=size;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendBoolean(arr[i])){}
            builder.uncheckedAppendChar(']');
            (buffer=builder.buffer)[0]='[';
            return new String(buffer,0,builder.size);
          }
        }
        return "[]";
      }
      @Override
      public boolean equals(Object val)
      {
        //TODO
        return false;
      }
      @Override
      public int hashCode()
      {
        int size;
        if((size=this.size)!=0)
        {
          final boolean[] arr;
          int hash;
          int i;
          for(hash=31+Boolean.hashCode((arr=root.arr)[i=this.rootOffset]),size+=i;++i!=size;hash=hash*31+Boolean.hashCode(arr[i])){}
          return hash;
        }
        return 1;
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
        private boolean
        uncheckedremoveVal (int size
        ,boolean val
        )
        {
          final UncheckedArrList root;
          final var arr=(root=this.root).arr;
          {
            for(int i=this.rootOffset,bound=i+size;;)
            {
              if(
              val==arr[i]
              )
              {
                for(var curr=parent;curr!=null;--curr.size){}
                this.size=size-1;
                ArrCopy.semicheckedSelfCopy(arr,i+1,i,(--root.size)-i);
                return true;
              }
              if(++i==bound)
              {
                return false;
              }
            }
          }
        }
    }
    public UncheckedArrList()
    {
      super();
    }
    public UncheckedArrList(int initialCapacity)
    {
      super(initialCapacity);
    }
    public UncheckedArrList(int size,boolean[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO
      return false;
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
    public void put(int index,boolean val)
    {
      arr[index]=val;
    }
    @Override
    public Object clone()
    {
      boolean[] copy;
      int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new boolean[size],0,size);
      }
      else
      {
        copy=OmniArray.OfBoolean.DEFAULT_ARR;
      }
      return new UncheckedArrList(size,copy);
    }
    @Override
    public
    int
    indexOf(boolean val)
    {
      int size;
      if((size=this.size)!=0)
      {
        return uncheckedindexOf(size,(val));
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
        return uncheckedindexOf(size,v);
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
          return uncheckedindexOf(size,v);
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
          return uncheckedindexOf(size,v);
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
          return uncheckedindexOf(size,v);
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
          return uncheckedindexOf(size,(boolean)(val));
        }
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf(boolean val)
    {
      int size;
      if((size=this.size)!=0)
      {
        return uncheckedlastIndexOf(size,(val));
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
        return uncheckedlastIndexOf(size,v);
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
          return uncheckedlastIndexOf(size,v);
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
          return uncheckedlastIndexOf(size,v);
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
          return uncheckedlastIndexOf(size,v);
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
          return uncheckedlastIndexOf(size,(boolean)(val));
        }
      }
      return -1;
    }
    @Override
    void uncheckedCopyInto(boolean[] dst,int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(Boolean[] dst,int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(double[] dst,int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(float[] dst,int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(long[] dst,int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(int[] dst,int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(short[] dst,int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(char[] dst,int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(byte[] dst,int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(Object[] dst,int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,size);
    }
    private boolean sublistcontains (int rootOffset,int size
    ,boolean val
    )
    {
      final var arr=this.arr;
      for(size+=rootOffset;;)
      {
        if(
        val==arr[rootOffset]
        )
        {
          return true;
        }
        if(++rootOffset==size)
        {
          return false;
        }
      }
    }
    @Override
    boolean
    uncheckedremoveVal (int size
    ,boolean val
    )
    {
      final var arr=this.arr;
      for(int i=0;;)
      {
        if(
        val==arr[i]
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
    private int sublistindexOf (int rootOffset,int size
    ,boolean val
    )
    {
      final var arr=this.arr;
      int i;
      for(size+=(i=rootOffset);;)
      {
        if(
        val==arr[i]
        )
        {
          return i-rootOffset;
        }
        if(++i==size)
        {
          return -1;
        }
      }
    }
    private int uncheckedindexOf (int size
    ,boolean val
    )
    {
      final var arr=this.arr;
      for(int i=0;;)
      {
        if(
        val==arr[i]
        )
        {
          return i;
        }
        if(++i==size)
        {
          return -1;
        }
      }
    }
    private int sublistlastIndexOf (int rootOffset,int size
    ,boolean val
    )
    {
      final var arr=this.arr;
      for(size+=(rootOffset-1);;--size)
      {
        if(
        val==arr[size]
        )
        {
          return size-rootOffset;
        }
        if(size==rootOffset)
        {
          return -1;
        }
      }
    }
    private int uncheckedlastIndexOf (int size
    ,boolean val
    )
    {
      final var arr=this.arr;
      for(;;)
      {
        if(
        val==arr[--size]
        )
        {
          return size;
        }
        if(size==0)
        {
          return -1;
        }
      }
    }
    @Override
    int uncheckedHashCode(int size)
    {
      final boolean[] arr;
      int hash,i;
      for(hash=31+Boolean.hashCode((arr=this.arr)[i=0]);++i!=size;hash=hash*31+Boolean.hashCode(arr[i])){}
      return hash;
    }
    @Override
    int uncheckedToString(int size,char[] buffer)
    {
      final boolean[] arr;
      int bufferOffset,i;
      for(bufferOffset=ToStringUtil.getStringBoolean((arr=this.arr)[i=0],buffer,1);++i!=size;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringBoolean(arr[i],buffer,bufferOffset)){}
      return bufferOffset;
    }
    @Override
    void uncheckedToString(int size,ToStringUtil.OmniStringBuilder builder)
    {
      final boolean[] arr;
      int i;
      for(builder.uncheckedAppendBoolean((arr=this.arr)[i=0]);++i!=size;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendBoolean(arr[i])){}
    }
    private void sublistForEach(int rootOffset,int size,BooleanConsumer action)
    {
      final var arr=this.arr;
      for(size+=rootOffset;;)
      {
        action.accept((boolean)arr[rootOffset]);
        if(++rootOffset==size)
        {
          return;
        }
      }
    }
    @Override
    void uncheckedForEach(int size,BooleanConsumer action)
    {
      final var arr=this.arr;
      for(int i=0;;)
      {
        action.accept((boolean)arr[i]);
        if(++i==size)
        {
          return;
        }
      }
    }
    @Override
    boolean uncheckedRemoveIf(int size,BooleanPredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public OmniIterator.OfBoolean iterator()
    {
      //TODO
      return null;
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
    public <T> T[] toArray(IntFunction<T[]> arrConstructor)
    {
      int size;
      T[] dst=arrConstructor.apply(size=this.size);
      if(size!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,dst,0,size);
      }
      return dst;
    }
    @Override
    public void add(int index,boolean val)
    {
      uncheckedAdd(index,val,this.size);
    }
    @Override
    public void add(int index,Boolean val)
    {
      add(index,(boolean)val);
    }
    private void uncheckedAdd(int index,boolean val,int size)
    {
      if(size!=0)
      {
        super.uncheckedInsert(size,index,val);
      }
      else
      {
        super.uncheckedInit(val);
      }
    }
    @Override
    public Boolean remove(int index)
    {
      return removeBooleanAt(index);
    }
    @Override
    public boolean removeBooleanAt(int index)
    {
      return super.uncheckedremoveAtIndex(index,this.size);
    }
    @Override
    public void replaceAll(BooleanPredicate operator)
    {
      int size;
      if((size=this.size)!=0)
      {
        uncheckedReplaceAll(size,operator);
      }
    }
    @Override
    public void replaceAll(UnaryOperator<Boolean> operator)
    {
      int size;
      if((size=this.size)!=0)
      {
        uncheckedReplaceAll(size,operator::apply);
      }
    }
    private void uncheckedReplaceAll(int size,BooleanPredicate operator)
    {
      final var arr=this.arr;
      for(int i=0;;)
      {
        arr[i]=operator.test((boolean)arr[i]);
        if(++i==size)
        {
          return;
        }
      }
    }
    @Override
    public boolean set(int index,boolean val)
    {
      final boolean[] arr;
      final boolean ret=(boolean)(arr=this.arr)[index];
      arr[index]=val;
      return ret;
    }
    @Override
    public Boolean set(int index,Boolean val)
    {
      return set(index,(boolean)val);
    }
    @Override
    public void sort(BooleanComparator sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        if(sorter==null)
        {
          BooleanSortUtil.uncheckedAscendingSort(this.arr,0,size);
        }
        else
        {
          BooleanSortUtil.uncheckedSort(this.arr,0,size,sorter);
        }
      }
    }
    @Override
    public void sort(Comparator<? super Boolean> sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        if(sorter==null)
        {
          BooleanSortUtil.uncheckedAscendingSort(this.arr,0,size);
        }
        else
        {
          BooleanSortUtil.uncheckedSort(this.arr,0,size,sorter::compare);
        }
      }
    }
    @Override
    public void stableAscendingSort()
    {
      int size;
      if((size=this.size)>1)
      {
        BooleanSortUtil.uncheckedAscendingSort(this.arr,0,size);
      }
    }
    @Override
    public void stableDescendingSort()
    {
      int size;
      if((size=this.size)>1)
      {
        BooleanSortUtil.uncheckedDescendingSort(this.arr,0,size);
      }
    }
    @Override
    public OmniList.OfBoolean subList(int fromIndex,int toIndex)
    {
      //TODO
      return null;
    }
  }
  public static class CheckedArrList extends UncheckedArrList
  {
    private static class SubListImpl implements OmniList.OfBoolean
    {
      transient int modCount;
      transient final CheckedArrList root;
      transient final SubListImpl parent;
      transient final int rootOffset;
      transient int size;
      SubListImpl(CheckedArrList root,int rootOffset,int size
        ,int modCount
      )
      {
        this.modCount=modCount;
        this.root=root;
        this.parent=null;
        this.rootOffset=rootOffset;
        this.size=size;
      }
      SubListImpl(SubListImpl parent,int rootOffset,int size
        ,int modCount
      )
      {
        this.modCount=modCount;
        this.root=parent.root;
        this.parent=parent;
        this.rootOffset=rootOffset;
        this.size=size;
      }
      @Override
      public Object clone()
      {
        int modCount;
        final CheckedArrList root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        final boolean[] copy;
        int size;
        if((size=this.size)!=0)
        {
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new boolean[size],0,size);
        }
        else
        {
          copy=OmniArray.OfBoolean.DEFAULT_ARR;
        }
        return new CheckedArrList(size,copy);
      }
      @Override
      public boolean add(boolean val)
      {
        int rootSize;
        int modCount;
        final CheckedArrList root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        if((rootSize=root.size)!=0)
        {
          for(var curr=parent;curr!=null;parent.modCount=modCount,++parent.size){}
          ((BooleanArrSeq)root).uncheckedInsert(rootSize,this.rootOffset,val);
          ++this.size;
        }
        else
        {
          for(var curr=parent;curr!=null;parent.modCount=modCount,parent.size=1){}
          ((BooleanArrSeq)root).uncheckedInit(val);
          this.size=1;
        }
        return true;
      }
      @Override
      public void add(int index,boolean val)
      {
        int rootSize;
        int modCount;
        final CheckedArrList root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        int size;
        if((size=this.size)<index||index<0)
        {
          throw new InvalidReadIndexException(index,size);
        }
        root.modCount=++modCount;
        this.modCount=modCount;
        if((rootSize=root.size)!=0)
        {
          for(var curr=parent;curr!=null;parent.modCount=modCount,++parent.size){}
          ((BooleanArrSeq)root).uncheckedInsert(rootSize,index+this.rootOffset,val);
          ++this.size;
        }
        else
        {
          for(var curr=parent;curr!=null;parent.modCount=modCount,parent.size=1){}
          ((BooleanArrSeq)root).uncheckedInit(val);
          this.size=1;
        }
      }
      @Override
      public boolean add(Boolean val)
      {
        return add((boolean)val);
      }
      @Override
      public void add(int index,Boolean val)
      {
        add(index,(boolean)val);
      }
      @Override
      public void forEach(BooleanConsumer action)
      {
        int modCount=this.modCount;
        final var root=this.root;
        try
        {
          int size;
          if((size=this.size)!=0)
          {
            ((UncheckedArrList)root).sublistForEach(this.rootOffset,size,action);
          }
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      @Override
      public void forEach(Consumer<? super Boolean> action)
      {
        int modCount=this.modCount;
        final var root=this.root;
        try
        {
          int size;
          if((size=this.size)!=0)
          {
            ((UncheckedArrList)root).sublistForEach(this.rootOffset,size,action::accept);
          }
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      @Override
      public boolean removeIf(BooleanPredicate filter)
      {
        //TODO
        return false;
      }
      @Override
      public boolean removeIf(Predicate<? super Boolean> action)
      {
        //TODO
        return false;
      }
      @Override
      public boolean[] toBooleanArray()
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        final int size;
        if((size=this.size)!=0)
        {
          final boolean[] copy;
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new boolean[size],0,size);
          return copy;
        }
        return OmniArray.OfBoolean.DEFAULT_ARR;
      }
      @Override
      public Boolean[] toArray()
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        final int size;
        if((size=this.size)!=0)
        {
          final Boolean[] copy;
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new Boolean[size],0,size);
          return copy;
        }
        return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
      }
      @Override
      public double[] toDoubleArray()
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        final int size;
        if((size=this.size)!=0)
        {
          final double[] copy;
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new double[size],0,size);
          return copy;
        }
        return OmniArray.OfDouble.DEFAULT_ARR;
      }
      @Override
      public float[] toFloatArray()
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        final int size;
        if((size=this.size)!=0)
        {
          final float[] copy;
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new float[size],0,size);
          return copy;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
      }
      @Override
      public long[] toLongArray()
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        final int size;
        if((size=this.size)!=0)
        {
          final long[] copy;
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new long[size],0,size);
          return copy;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
      }
      @Override
      public int[] toIntArray()
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        final int size;
        if((size=this.size)!=0)
        {
          final int[] copy;
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new int[size],0,size);
          return copy;
        }
        return OmniArray.OfInt.DEFAULT_ARR;
      }
      @Override
      public short[] toShortArray()
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        final int size;
        if((size=this.size)!=0)
        {
          final short[] copy;
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new short[size],0,size);
          return copy;
        }
        return OmniArray.OfShort.DEFAULT_ARR;
      }
      @Override
      public char[] toCharArray()
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        final int size;
        if((size=this.size)!=0)
        {
          final char[] copy;
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new char[size],0,size);
          return copy;
        }
        return OmniArray.OfChar.DEFAULT_ARR;
      }
      @Override
      public byte[] toByteArray()
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        final int size;
        if((size=this.size)!=0)
        {
          final byte[] copy;
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new byte[size],0,size);
          return copy;
        }
        return OmniArray.OfByte.DEFAULT_ARR;
      }
      @Override
      public <T> T[] toArray(IntFunction<T[]> arrConstructor)
      {
        int size;
        final T[] dst;
        int modCount=this.modCount;
        final var root=this.root;
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
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst,0,size);
        }
        return dst;
      }
      @Override
      public void put(int index,boolean val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        if(index<0 || index>=size)
        {
          throw new InvalidReadIndexException(index,size);
        }
        root.arr[index+this.rootOffset]=val;
      }
      @Override
      public boolean getBoolean(int index)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        if(index<0 || index>=size)
        {
          throw new InvalidReadIndexException(index,size);
        }
        return (boolean)root.arr[index+this.rootOffset];
      }
      @Override
      public Boolean get(int index)
      {
        return getBoolean(index);
      }
      @Override
      public <T> T[] toArray(T[] dst)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        int size;
        if((size=this.size)!=0)
        {
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=OmniArray.uncheckedArrResize(size,dst),0,size);
        }
        else if(dst.length!=0)
        {
          dst[0]=null;
        }
        return dst;
      }
      @Override
      public void clear()
      {
        int modCount;
        final CheckedArrList root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        int size;
        if((size=this.size)!=0)
        {
          root.modCount=++modCount;
          this.modCount=modCount;
          for(var curr=parent;curr!=null;parent.modCount=modCount,parent.size-=size){}
          int rootOffset;
          ArrCopy.semicheckedSelfCopy(root.arr,(rootOffset=this.rootOffset)+size,rootOffset,(root.size-=size)-rootOffset);
          this.size=0;
        }
      }
      @Override
      public OmniIterator.OfBoolean iterator()
      {
        //TODO
        return null;
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
      public
      boolean
      contains(boolean val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        int size;
        if((size=this.size)!=0)
        {
          return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,(val));
        }
        return false;
      }
      @Override
      public
      boolean
      contains(int val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
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
          return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,v);
        }
        return false;
      }
      @Override
      public
      boolean
      contains(long val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
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
            return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,v);
          }
        }
        return false;
      }
      @Override
      public
      boolean
      contains(float val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
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
            return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,v);
          }
        }
        return false;
      }
      @Override
      public
      boolean
      contains(double val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
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
            return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,v);
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
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        int size;
        if((size=this.size)!=0)
        {
          if(val instanceof Boolean)
          {
            return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,(boolean)(val));
          }
        }
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
          return uncheckedremoveVal(size,(val));
        }
        CheckedCollection.checkModCount(this.modCount,root.modCount);
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
              CheckedCollection.checkModCount(this.modCount,root.modCount);
            return false;
            case 0:
              v=false;
              break;
            case 1:
              v=true;
          }
          return uncheckedremoveVal(size,v);
        }
        CheckedCollection.checkModCount(this.modCount,root.modCount);
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
            CheckedCollection.checkModCount(this.modCount,root.modCount);
            return false;
          }
          {
            return uncheckedremoveVal(size,v);
          }
        }
        CheckedCollection.checkModCount(this.modCount,root.modCount);
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
              CheckedCollection.checkModCount(this.modCount,root.modCount);
              return false;
            case 0:
            case Integer.MIN_VALUE:
              v=false;
              break;
            case 1:
              v=true;
          }
          {
            return uncheckedremoveVal(size,v);
          }
        }
        CheckedCollection.checkModCount(this.modCount,root.modCount);
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
            CheckedCollection.checkModCount(this.modCount,root.modCount);
            return false;
          }
          {
            return uncheckedremoveVal(size,v);
          }
        }
        CheckedCollection.checkModCount(this.modCount,root.modCount);
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
            return uncheckedremoveVal(size,(boolean)(val));
          }
        }
        CheckedCollection.checkModCount(this.modCount,root.modCount);
        return false;
      }
      @Override
      public
      int
      indexOf(boolean val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        int size;
        if((size=this.size)!=0)
        {
          return ((UncheckedArrList)root).sublistindexOf(this.rootOffset,size,(val));
        }
        return -1;
      }
      @Override
      public
      int
      indexOf(int val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
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
          return ((UncheckedArrList)root).sublistindexOf(this.rootOffset,size,v);
        }
        return -1;
      }
      @Override
      public
      int
      indexOf(long val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
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
            return ((UncheckedArrList)root).sublistindexOf(this.rootOffset,size,v);
          }
        }
        return -1;
      }
      @Override
      public
      int
      indexOf(float val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
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
            return ((UncheckedArrList)root).sublistindexOf(this.rootOffset,size,v);
          }
        }
        return -1;
      }
      @Override
      public
      int
      indexOf(double val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
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
            return ((UncheckedArrList)root).sublistindexOf(this.rootOffset,size,v);
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
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        int size;
        if((size=this.size)!=0)
        {
          if(val instanceof Boolean)
          {
            return ((UncheckedArrList)root).sublistindexOf(this.rootOffset,size,(boolean)(val));
          }
        }
        return -1;
      }
      @Override
      public
      int
      lastIndexOf(boolean val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        int size;
        if((size=this.size)!=0)
        {
          return ((UncheckedArrList)root).sublistlastIndexOf(this.rootOffset,size,(val));
        }
        return -1;
      }
      @Override
      public
      int
      lastIndexOf(int val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
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
          return ((UncheckedArrList)root).sublistlastIndexOf(this.rootOffset,size,v);
        }
        return -1;
      }
      @Override
      public
      int
      lastIndexOf(long val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
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
            return ((UncheckedArrList)root).sublistlastIndexOf(this.rootOffset,size,v);
          }
        }
        return -1;
      }
      @Override
      public
      int
      lastIndexOf(float val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
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
            return ((UncheckedArrList)root).sublistlastIndexOf(this.rootOffset,size,v);
          }
        }
        return -1;
      }
      @Override
      public
      int
      lastIndexOf(double val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
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
            return ((UncheckedArrList)root).sublistlastIndexOf(this.rootOffset,size,v);
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
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        int size;
        if((size=this.size)!=0)
        {
          if(val instanceof Boolean)
          {
            return ((UncheckedArrList)root).sublistlastIndexOf(this.rootOffset,size,(boolean)(val));
          }
        }
        return -1;
      }
      @Override
      public String toString()
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
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
            final boolean[] arr;
            int bufferOffset,i;
            for(bufferOffset=ToStringUtil.getStringBoolean((arr=root.arr)[i=this.rootOffset],buffer=new char[size*(7)],1),size+=i;++i!=size;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringBoolean(arr[i],buffer,bufferOffset)){}
            buffer[bufferOffset]=']';
            buffer[0]='[';
            return new String(buffer,0,bufferOffset+1);
          }
          else
          {
            final ToStringUtil.OmniStringBuilder builder;
            final boolean[] arr;
            int i;
            for((builder=new ToStringUtil.OmniStringBuilder(1,new char[size<=(int)(OmniArray.MAX_ARR_SIZE/6.5f)?(size*6)+(size>>>1):OmniArray.MAX_ARR_SIZE])).uncheckedAppendBoolean((arr=root.arr)[i=this.rootOffset]),size+=i;++i!=size;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendBoolean(arr[i])){}
            builder.uncheckedAppendChar(']');
            (buffer=builder.buffer)[0]='[';
            return new String(buffer,0,builder.size);
          }
        }
        return "[]";
      }
      @Override
      public boolean equals(Object val)
      {
        //TODO
        return false;
      }
      @Override
      public int hashCode()
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        int size;
        if((size=this.size)!=0)
        {
          final boolean[] arr;
          int hash;
          int i;
          for(hash=31+Boolean.hashCode((arr=root.arr)[i=this.rootOffset]),size+=i;++i!=size;hash=hash*31+Boolean.hashCode(arr[i])){}
          return hash;
        }
        return 1;
      }
      @Override
      public int size()
      {
        CheckedCollection.checkModCount(this.modCount,root.modCount);
        return this.size;
      }
      @Override
      public boolean isEmpty()
      {
        CheckedCollection.checkModCount(this.modCount,root.modCount);
        return this.size==0;
      }
        private boolean
        uncheckedremoveVal (int size
        ,boolean val
        )
        {
          final UncheckedArrList root;
          final var arr=(root=this.root).arr;
          {
            for(int i=this.rootOffset,bound=i+size;;)
            {
              if(
              val==arr[i]
              )
              {
                for(var curr=parent;curr!=null;--curr.size){}
                this.size=size-1;
                ArrCopy.semicheckedSelfCopy(arr,i+1,i,(--root.size)-i);
                return true;
              }
              if(++i==bound)
              {
                return false;
              }
            }
          }
        }
    }
    transient int modCount;
    public CheckedArrList()
    {
      super();
    }
    public CheckedArrList(int initialCapacity)
    {
      super(initialCapacity);
    }
    public CheckedArrList(int size,boolean[] arr)
    {
      super(size,arr);
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
      boolean[] copy;
      int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new boolean[size],0,size);
      }
      else
      {
        copy=OmniArray.OfBoolean.DEFAULT_ARR;
      }
      return new CheckedArrList(size,copy);
    }
    @Override
    public void push(boolean val)
    {
      ++this.modCount;
      super.push(val);
    }
    @Override
    public void put(int index,boolean val)
    {
      if(index>=0 && index<size)
      {
        super.put(index,val);
        return;
      }
      throw new InvalidWriteIndexException(index,size);
    }
    @Override
    public boolean set(int index,boolean val)
    {
      if(index>=0 && index<size)
      {
        return super.set(index,val);
      }
      throw new InvalidReadIndexException(index,size);
    }
    @Override
    public boolean removeBooleanAt(int index)
    {
      int size;
      if((size=this.size)>index && index>=0)
      {
        ++this.modCount;
        return ((BooleanArrSeq)this).uncheckedremoveAtIndex(index,size);
      }
      throw new InvalidWriteIndexException(index,size);
    }
    @Override
    public void replaceAll(UnaryOperator<Boolean> operator)
    {
      int size;
      if((size=this.size)!=0)
      {
        int modCount=this.modCount;
        try
        {
          super.uncheckedReplaceAll(size,operator::apply);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override
    public void replaceAll(BooleanPredicate operator)
    {
      int size;
      if((size=this.size)!=0)
      {
        int modCount=this.modCount;
        try
        {
          super.uncheckedReplaceAll(size,operator);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override
    public void stableAscendingSort()
    {
      int size;
      if((size=this.size)>1)
      {
        BooleanSortUtil.uncheckedAscendingSort(this.arr,0,size);
        ++this.modCount;
      }
    }
    @Override
    public void stableDescendingSort()
    {
      int size;
      if((size=this.size)>1)
      {
        BooleanSortUtil.uncheckedDescendingSort(this.arr,0,size);
        ++this.modCount;
      }
    }
    @Override
    public OmniList.OfBoolean subList(int fromIndex,int toIndex)
    {
      //TODO
      return null;
    }
    @Override
    public void sort(BooleanComparator sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        if(sorter==null)
        {
          BooleanSortUtil.uncheckedAscendingSort(this.arr,0,size);
          ++this.modCount;
        }
        else
        {
          int modCount=this.modCount;
          try
          {
            BooleanSortUtil.uncheckedSort(this.arr,0,size,sorter);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.modCount=modCount+1;
          }
        }
      }
    }
    @Override
    public void sort(Comparator<? super Boolean> sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        if(sorter==null)
        {
          BooleanSortUtil.uncheckedAscendingSort(this.arr,0,size);
          ++this.modCount;
        }
        else
        {
          int modCount=this.modCount;
          try
          {
            BooleanSortUtil.uncheckedSort(this.arr,0,size,sorter::compare);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.modCount=modCount+1;
          }
        }
      }
    }
    @Override
    boolean
    uncheckedremoveVal (int size
    ,boolean val
    )
    {
      final var arr=this.arr;
      for(int i=0;;)
      {
        if(
        val==arr[i]
        )
        {
          ++this.modCount;
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
    @Override
    void uncheckedForEach(int size,BooleanConsumer action)
    {
      int modCount=this.modCount;
      try
      {
        super.uncheckedForEach(size,action);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override
    boolean uncheckedRemoveIf(int size,BooleanPredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public OmniIterator.OfBoolean iterator()
    {
      //TODO
      return null;
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
    public <T> T[] toArray(IntFunction<T[]> arrConstructor)
    {
      return super.toArray((arrSize)->
      {
        int modCount=this.modCount;
        try
        {
          return arrConstructor.apply(arrSize);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      });
    }
    @Override
    public boolean getBoolean(int index)
    {
      if(index>=0 && index<size)
      {
        return super.getBoolean(index);
      }
      throw new InvalidReadIndexException(index,size);
    }
    @Override
    public void add(int index,boolean val)
    {
      int size;
      if(index<=(size=this.size)&& index>=0)
      {
        ++this.modCount;
        super.uncheckedAdd(index,val,size);
        return;
      }
      throw new InvalidWriteIndexException(index,size);
    }
  }
}
