package omni.impl.seq;
import omni.api.OmniCollection;
import omni.util.OmniArray;
import omni.api.OmniList;
import omni.api.OmniStack;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.Comparator;
import omni.util.ArrCopy;
import omni.util.ByteSortUtil;
import omni.impl.CheckedCollection;
import java.util.NoSuchElementException;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;
import omni.util.TypeUtil;
import omni.function.ByteUnaryOperator;
import omni.function.ByteComparator;
import omni.function.BytePredicate;
import omni.function.ByteConsumer;
import omni.util.ToStringUtil;
public abstract class ByteArrSeq implements OmniCollection.OfByte
{
  transient int size;
  transient byte[] arr;
  private ByteArrSeq()
  {
    super();
  }
  private ByteArrSeq(int initialCapacity)
  {
    super();
    switch(initialCapacity)
    { 
    default:
      this.arr=new byte[initialCapacity];
      return;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfByte.DEFAULT_ARR;
    case 0:
    }
  }
  private ByteArrSeq(int size,byte[] arr)
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
    push(TypeUtil.castToByte(val));
    return true;
  }
  @Override
  public boolean add(byte val)
  {
    push(val);
    return true;
  }
  @Override
  public boolean add(Byte val)
  {
    push((byte)val);
    return true;
  }
  @Override
  public void forEach(ByteConsumer action)
  {
    int size;
    if((size=this.size)!=0)
    {
      uncheckedForEach(size,action);
    }
  }
  @Override
  public void forEach(Consumer<? super Byte> action)
  {
    int size;
    if((size=this.size)!=0)
    {
      uncheckedForEach(size,action::accept);
    }
  }
  @Override
  public boolean removeIf(BytePredicate filter)
  {
    int size;
    if((size=this.size)!=0)
    {
      return uncheckedRemoveIf(size,filter);
    }
    return false;
  }
  @Override
  public boolean removeIf(Predicate<? super Byte> filter)
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
      if(size>(Integer.MAX_VALUE/3))
      {
        throw new OutOfMemoryError();
      }
      final char[] buffer;
      if(size<=(OmniArray.MAX_ARR_SIZE/((6)<<1)))
      {
        (buffer=new char[size*(6)])[size=uncheckedToString(size,buffer)]=']';
        buffer[0]='[';
        return new String(buffer,0,size+1);
      }
      else
      {
        final ToStringUtil.OmniStringBuilder builder;
        uncheckedToString(size,builder=new ToStringUtil.OmniStringBuilder(1,new char[size<=(int)(OmniArray.MAX_ARR_SIZE/5)?(size*5):OmniArray.MAX_ARR_SIZE]));
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
  ,int val
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
  abstract boolean uncheckedremoveVal(int size,int val);
  @Override
  public
  boolean
  removeVal(boolean val)
  {
    int size;
    if((size=this.size)!=0)
    {
      return uncheckedremoveVal(size,TypeUtil.castToByte(val));
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
      if(val==(byte)val)
      {
        return uncheckedremoveVal(size,val);
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
      final byte v;
      if(val==(v=(byte)val))
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
      final byte v;
      if(val==(v=(byte)val))
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
      final byte v;
      if((v=(byte)val)==val)
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
      if(val instanceof Byte)
      {
        return uncheckedremoveVal(size,(byte)(val));
      }
    }
    return false;
  }
  @Override
  public
  boolean
  removeVal(byte val)
  {
    {
      int size;
      if((size=this.size)!=0)
      {
        return uncheckedremoveVal(size,(val));
      }
    }
    return false;
  }
  @Override
  public
  boolean
  removeVal(char val)
  {
    if(val<=Byte.MAX_VALUE)
    {
      int size;
      if((size=this.size)!=0)
      {
        return uncheckedremoveVal(size,(val));
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
      return uncheckedcontains(size,TypeUtil.castToByte(val));
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
      if(val==(byte)val)
      {
        return uncheckedcontains(size,val);
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
      final byte v;
      if(val==(v=(byte)val))
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
      final byte v;
      if(val==(v=(byte)val))
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
      final byte v;
      if((v=(byte)val)==val)
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
      if(val instanceof Byte)
      {
        return uncheckedcontains(size,(byte)(val));
      }
    }
    return false;
  }
  @Override
  public
  boolean
  contains(byte val)
  {
    {
      int size;
      if((size=this.size)!=0)
      {
        return uncheckedcontains(size,(val));
      }
    }
    return false;
  }
  @Override
  public
  boolean
  contains(char val)
  {
    if(val<=Byte.MAX_VALUE)
    {
      int size;
      if((size=this.size)!=0)
      {
        return uncheckedcontains(size,(val));
      }
    }
    return false;
  }
  abstract void uncheckedCopyInto(Object[] dst,int size);
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
  abstract void uncheckedCopyInto(Byte[] dst,int size);
  @Override
  public Byte[] toArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final Byte[] copy;
      uncheckedCopyInto(copy=new Byte[size],size);
      return copy;
    }
    return OmniArray.OfByte.DEFAULT_BOXED_ARR;
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
  abstract int uncheckedHashCode(int size);
  abstract int uncheckedToString(int size,char[] buffer);
  abstract void uncheckedToString(int size,ToStringUtil.OmniStringBuilder builder);
  abstract void uncheckedForEach(int size,ByteConsumer action);
  abstract boolean uncheckedRemoveIf(int size,BytePredicate filter);
  private void uncheckedInit(byte val)
  {
    byte[] arr;
    if((arr=this.arr)==OmniArray.OfByte.DEFAULT_ARR)
    {
      this.arr=arr=new byte[OmniArray.DEFAULT_ARR_SEQ_CAP];
    }
    else if(arr==null)
    {
      this.arr=arr=new byte[1];
    }
    arr[0]=val;
    this.size=1;
  }
  private void uncheckedAppend(int size,byte val)
  {
    byte[] arr;
    if((arr=this.arr).length==size)
    {
      ArrCopy.uncheckedCopy(arr,0,arr=new byte[OmniArray.growBy50Pct(size)],0,size);
      this.arr=arr;
    }
    arr[size]=val;
    this.size=size+1;
  }
  public void push(byte val)
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
  private void uncheckedInsert(int size,int index,byte val)
  {
    int tailDist;
    if((tailDist=size-index)==0)
    {
      uncheckedAppend(size,val);
    }
    else
    {
      byte[] arr;
      if((arr=this.arr).length==size)
      {
        byte[] newArr;
        ArrCopy.semicheckedCopy(arr,0,newArr=new byte[OmniArray.growBy50Pct(size)],0,index);
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
  public void push(Byte val)
  {
    push((byte)val);
  }
  private byte uncheckedremoveAtIndex(int index,int size)
  {
    final byte[] arr;
    final byte ret=(byte)(arr=this.arr)[index];
    ArrCopy.semicheckedSelfCopy(arr,index+1,index,(--size)-index);
    this.size=size;
    return ret;
  }
  public static class UncheckedArrStack extends ByteArrSeq implements OmniStack.OfByte
  {
    public UncheckedArrStack()
    {
      super();
    }
    public UncheckedArrStack(int initialCapacity)
    {
      super(initialCapacity);
    }
    public UncheckedArrStack(int size,byte[] arr)
    {
      super(size,arr);
    }
    @Override
    public byte popByte()
    {
      return (byte)arr[--this.size];
    }
    @Override
    public Byte pop()
    {
      return popByte();
    }
    @Override
    public Object clone()
    {
      byte[] copy;
      int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new byte[size],0,size);
      }
      else
      {
        copy=OmniArray.OfByte.DEFAULT_ARR;
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
        return uncheckedsearch(size,TypeUtil.castToByte(val));
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
        if(val==(byte)val)
        {
          return uncheckedsearch(size,val);
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
        final byte v;
        if(val==(v=(byte)val))
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
        final byte v;
        if(val==(v=(byte)val))
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
        final byte v;
        if((v=(byte)val)==val)
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
        if(val instanceof Byte)
        {
          return uncheckedsearch(size,(byte)(val));
        }
      }
      return -1;
    }
    @Override
    public
    int
    search(char val)
    {
      if(val<=Byte.MAX_VALUE)
      {
        int size;
        if((size=this.size)!=0)
        {
          return uncheckedsearch(size,(val));
        }
      }
      return -1;
    }
    @Override
    public
    int
    search(byte val)
    {
      {
        int size;
        if((size=this.size)!=0)
        {
          return uncheckedsearch(size,(val));
        }
      }
      return -1;
    }
    @Override
    public byte pollByte()
    {
      int size;
      if((size=this.size)!=0)
      {
        final byte ret=(byte)((byte)arr[--size]);
        this.size=size;
        return ret;
      }
      return Byte.MIN_VALUE;
    }
    @Override
    public Byte poll()
    {
      int size;
      if((size=this.size)!=0)
      {
        final Byte ret=(Byte)((byte)arr[--size]);
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
        final double ret=(double)((byte)arr[--size]);
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
        final float ret=(float)((byte)arr[--size]);
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
        final long ret=(long)((byte)arr[--size]);
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
        final int ret=(int)((byte)arr[--size]);
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
        final short ret=(short)((byte)arr[--size]);
        this.size=size;
        return ret;
      }
      return Short.MIN_VALUE;
    }
    public byte peekByte()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (byte)((byte)arr[size-1]);
      }
      return Byte.MIN_VALUE;
    }
    public Byte peek()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (Byte)((byte)arr[size-1]);
      }
      return null;
    }
    public double peekDouble()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (double)((byte)arr[size-1]);
      }
      return Double.NaN;
    }
    public float peekFloat()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (float)((byte)arr[size-1]);
      }
      return Float.NaN;
    }
    public long peekLong()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (long)((byte)arr[size-1]);
      }
      return Long.MIN_VALUE;
    }
    public int peekInt()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (int)((byte)arr[size-1]);
      }
      return Integer.MIN_VALUE;
    }
    public short peekShort()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (short)((byte)arr[size-1]);
      }
      return Short.MIN_VALUE;
    }
    @Override
    void uncheckedCopyInto(byte[] dst,int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(Byte[] dst,int size)
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
    void uncheckedCopyInto(Object[] dst,int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,size);
    }
    @Override
    boolean
    uncheckedremoveVal (int size
    ,int val
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
    ,int val
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
      final byte[] arr;
      int hash;
      for(hash=31+((arr=this.arr)[--size]);size!=0;hash=hash*31+(arr[--size])){}
      return hash;
    }
    @Override
    int uncheckedToString(int size,char[] buffer)
    {
      final byte[] arr;
      int bufferOffset;
      for(bufferOffset=ToStringUtil.getStringShort((arr=this.arr)[--size],buffer,1);size!=0;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringShort(arr[--size],buffer,bufferOffset)){}
      return bufferOffset;
    }
    @Override
    void uncheckedToString(int size,ToStringUtil.OmniStringBuilder builder)
    {
      final byte[] arr;
      for(builder.uncheckedAppendShort((arr=this.arr)[--size]);size!=0;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendShort(arr[--size])){}
    }
    @Override
    void uncheckedForEach(int size,ByteConsumer action)
    {
      final var arr=this.arr;
      for(;;)
      {
        action.accept((byte)arr[--size]);
        if(size==0)
        {
          return;
        }
      }
    }
    @Override
    boolean uncheckedRemoveIf(int size,BytePredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public OmniIterator.OfByte iterator()
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
    public CheckedArrStack(int size,byte[] arr)
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
    public byte popByte()
    {
      int size;
      if((size=this.size)!=0)
      {
        ++this.modCount;
        final byte ret=(byte)arr[--size];
        this.size=size;
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override
    public Object clone()
    {
      byte[] copy;
      int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new byte[size],0,size);
      }
      else
      {
        copy=OmniArray.OfByte.DEFAULT_ARR;
      }
      return new CheckedArrStack(size,copy);
    }
    @Override
    public byte pollByte()
    {
      int size;
      if((size=this.size)!=0)
      {
        final byte ret=(byte)((byte)arr[--size]);
        ++this.modCount;
        this.size=size;
        return ret;
      }
      return Byte.MIN_VALUE;
    }
    @Override
    public Byte poll()
    {
      int size;
      if((size=this.size)!=0)
      {
        final Byte ret=(Byte)((byte)arr[--size]);
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
        final double ret=(double)((byte)arr[--size]);
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
        final float ret=(float)((byte)arr[--size]);
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
        final long ret=(long)((byte)arr[--size]);
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
        final int ret=(int)((byte)arr[--size]);
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
        final short ret=(short)((byte)arr[--size]);
        ++this.modCount;
        this.size=size;
        return ret;
      }
      return Short.MIN_VALUE;
    }
    @Override
    boolean
    uncheckedremoveVal (int size
    ,int val
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
    void uncheckedForEach(int size,ByteConsumer action)
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
    boolean uncheckedRemoveIf(int size,BytePredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public void push(byte val)
    {
      ++this.modCount;
      super.push(val);
    }
    @Override
    public OmniIterator.OfByte iterator()
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
  private static class UncheckedArrList extends ByteArrSeq implements OmniList.OfByte
  {
    private static class SubListImpl implements OmniList.OfByte
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
        final byte[] copy;
        int size;
        if((size=this.size)!=0)
        {
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new byte[size],0,size);
        }
        else
        {
          copy=OmniArray.OfByte.DEFAULT_ARR;
        }
        return new UncheckedArrList(size,copy);
      }
      @Override
      public boolean add(byte val)
      {
        int rootSize;
        final UncheckedArrList root;
        if((rootSize=(root=this.root).size)!=0)
        {
          for(var curr=parent;curr!=null;++parent.size){}
          ((ByteArrSeq)root).uncheckedInsert(rootSize,this.rootOffset,val);
          ++this.size;
        }
        else
        {
          for(var curr=parent;curr!=null;parent.size=1){}
          ((ByteArrSeq)root).uncheckedInit(val);
          this.size=1;
        }
        return true;
      }
      @Override
      public void add(int index,byte val)
      {
        int rootSize;
        final UncheckedArrList root;
        if((rootSize=(root=this.root).size)!=0)
        {
          for(var curr=parent;curr!=null;++parent.size){}
          ((ByteArrSeq)root).uncheckedInsert(rootSize,index+this.rootOffset,val);
          ++this.size;
        }
        else
        {
          for(var curr=parent;curr!=null;parent.size=1){}
          ((ByteArrSeq)root).uncheckedInit(val);
          this.size=1;
        }
      }
      @Override
      public boolean add(Byte val)
      {
        return add((byte)val);
      }
      @Override
      public void add(int index,Byte val)
      {
        add(index,(byte)val);
      }
      @Override
      public void forEach(ByteConsumer action)
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
      public void forEach(Consumer<? super Byte> action)
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
      public boolean removeIf(BytePredicate filter)
      {
        //TODO
        return false;
      }
      @Override
      public boolean removeIf(Predicate<? super Byte> action)
      {
        //TODO
        return false;
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
      public Byte[] toArray()
      {
        final int size;
        if((size=this.size)!=0)
        {
          final Byte[] copy;
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new Byte[size],0,size);
          return copy;
        }
        return OmniArray.OfByte.DEFAULT_BOXED_ARR;
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
      public void put(int index,byte val)
      {
        root.arr[index+this.rootOffset]=val;
      }
      @Override
      public byte getByte(int index)
      {
        return (byte)root.arr[index+this.rootOffset];
      }
      @Override
      public Byte get(int index)
      {
        return getByte(index);
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
      public OmniIterator.OfByte iterator()
      {
        //TODO
        return null;
      }
      @Override
      public OmniListIterator.OfByte listIterator()
      {
        //TODO
        return null;
      }
      @Override
      public OmniListIterator.OfByte listIterator(int index)
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
          return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,TypeUtil.castToByte(val));
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
          if(val==(byte)val)
          {
            return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,val);
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
          final byte v;
          if(val==(v=(byte)val))
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
          final byte v;
          if(val==(v=(byte)val))
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
          final byte v;
          if((v=(byte)val)==val)
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
          if(val instanceof Byte)
          {
            return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,(byte)(val));
          }
        }
        return false;
      }
      @Override
      public
      boolean
      contains(byte val)
      {
        {
          int size;
          if((size=this.size)!=0)
          {
            return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,(val));
          }
        }
        return false;
      }
      @Override
      public
      boolean
      contains(char val)
      {
        if(val<=Byte.MAX_VALUE)
        {
          int size;
          if((size=this.size)!=0)
          {
            return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,(val));
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
          return uncheckedremoveVal(size,TypeUtil.castToByte(val));
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
          if(val==(byte)val)
          {
            return uncheckedremoveVal(size,val);
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
          final byte v;
          if(val==(v=(byte)val))
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
          final byte v;
          if(val==(v=(byte)val))
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
          final byte v;
          if((v=(byte)val)==val)
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
          if(val instanceof Byte)
          {
            return uncheckedremoveVal(size,(byte)(val));
          }
        }
        return false;
      }
      @Override
      public
      boolean
      removeVal(byte val)
      {
        {
          int size;
          if((size=this.size)!=0)
          {
            return uncheckedremoveVal(size,(val));
          }
        }
        return false;
      }
      @Override
      public
      boolean
      removeVal(char val)
      {
        if(val<=Byte.MAX_VALUE)
        {
          int size;
          if((size=this.size)!=0)
          {
            return uncheckedremoveVal(size,(val));
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
          return ((UncheckedArrList)root).sublistindexOf(this.rootOffset,size,TypeUtil.castToByte(val));
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
          if(val==(byte)val)
          {
            return ((UncheckedArrList)root).sublistindexOf(this.rootOffset,size,val);
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
          final byte v;
          if(val==(v=(byte)val))
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
          final byte v;
          if(val==(v=(byte)val))
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
          final byte v;
          if((v=(byte)val)==val)
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
          if(val instanceof Byte)
          {
            return ((UncheckedArrList)root).sublistindexOf(this.rootOffset,size,(byte)(val));
          }
        }
        return -1;
      }
      @Override
      public
      int
      indexOf(char val)
      {
        if(val<=Byte.MAX_VALUE)
        {
          int size;
          if((size=this.size)!=0)
          {
            return ((UncheckedArrList)root).sublistindexOf(this.rootOffset,size,(val));
          }
        }
        return -1;
      }
      @Override
      public
      int
      indexOf(byte val)
      {
        {
          int size;
          if((size=this.size)!=0)
          {
            return ((UncheckedArrList)root).sublistindexOf(this.rootOffset,size,(val));
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
          return ((UncheckedArrList)root).sublistlastIndexOf(this.rootOffset,size,TypeUtil.castToByte(val));
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
          if(val==(byte)val)
          {
            return ((UncheckedArrList)root).sublistlastIndexOf(this.rootOffset,size,val);
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
          final byte v;
          if(val==(v=(byte)val))
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
          final byte v;
          if(val==(v=(byte)val))
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
          final byte v;
          if((v=(byte)val)==val)
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
          if(val instanceof Byte)
          {
            return ((UncheckedArrList)root).sublistlastIndexOf(this.rootOffset,size,(byte)(val));
          }
        }
        return -1;
      }
      @Override
      public
      int
      lastIndexOf(char val)
      {
        if(val<=Byte.MAX_VALUE)
        {
          int size;
          if((size=this.size)!=0)
          {
            return ((UncheckedArrList)root).sublistlastIndexOf(this.rootOffset,size,(val));
          }
        }
        return -1;
      }
      @Override
      public
      int
      lastIndexOf(byte val)
      {
        {
          int size;
          if((size=this.size)!=0)
          {
            return ((UncheckedArrList)root).sublistlastIndexOf(this.rootOffset,size,(val));
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
          if(size>(Integer.MAX_VALUE/3))
          {
            throw new OutOfMemoryError();
          }
          final char[] buffer;
          if(size<=(OmniArray.MAX_ARR_SIZE/((6)<<1)))
          {
            final byte[] arr;
            int bufferOffset,i;
            for(bufferOffset=ToStringUtil.getStringShort((arr=root.arr)[i=this.rootOffset],buffer=new char[size*(6)],1),size+=i;++i!=size;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringShort(arr[i],buffer,bufferOffset)){}
            buffer[bufferOffset]=']';
            buffer[0]='[';
            return new String(buffer,0,bufferOffset+1);
          }
          else
          {
            final ToStringUtil.OmniStringBuilder builder;
            final byte[] arr;
            int i;
            for((builder=new ToStringUtil.OmniStringBuilder(1,new char[size<=(int)(OmniArray.MAX_ARR_SIZE/5)?(size*5):OmniArray.MAX_ARR_SIZE])).uncheckedAppendShort((arr=root.arr)[i=this.rootOffset]),size+=i;++i!=size;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendShort(arr[i])){}
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
          final byte[] arr;
          int hash;
          int i;
          for(hash=31+((arr=root.arr)[i=this.rootOffset]),size+=i;++i!=size;hash=hash*31+(arr[i])){}
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
        ,int val
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
    public UncheckedArrList(int size,byte[] arr)
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
    public byte getByte(int index)
    {
      return (byte)arr[index];
    }
    @Override
    public Byte get(int index)
    {
      return getByte(index);
    }
    @Override
    public void put(int index,byte val)
    {
      arr[index]=val;
    }
    @Override
    public Object clone()
    {
      byte[] copy;
      int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new byte[size],0,size);
      }
      else
      {
        copy=OmniArray.OfByte.DEFAULT_ARR;
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
        return uncheckedindexOf(size,TypeUtil.castToByte(val));
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
        if(val==(byte)val)
        {
          return uncheckedindexOf(size,val);
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
        final byte v;
        if(val==(v=(byte)val))
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
        final byte v;
        if(val==(v=(byte)val))
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
        final byte v;
        if((v=(byte)val)==val)
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
        if(val instanceof Byte)
        {
          return uncheckedindexOf(size,(byte)(val));
        }
      }
      return -1;
    }
    @Override
    public
    int
    indexOf(char val)
    {
      if(val<=Byte.MAX_VALUE)
      {
        int size;
        if((size=this.size)!=0)
        {
          return uncheckedindexOf(size,(val));
        }
      }
      return -1;
    }
    @Override
    public
    int
    indexOf(byte val)
    {
      {
        int size;
        if((size=this.size)!=0)
        {
          return uncheckedindexOf(size,(val));
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
        return uncheckedlastIndexOf(size,TypeUtil.castToByte(val));
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
        if(val==(byte)val)
        {
          return uncheckedlastIndexOf(size,val);
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
        final byte v;
        if(val==(v=(byte)val))
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
        final byte v;
        if(val==(v=(byte)val))
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
        final byte v;
        if((v=(byte)val)==val)
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
        if(val instanceof Byte)
        {
          return uncheckedlastIndexOf(size,(byte)(val));
        }
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf(char val)
    {
      if(val<=Byte.MAX_VALUE)
      {
        int size;
        if((size=this.size)!=0)
        {
          return uncheckedlastIndexOf(size,(val));
        }
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf(byte val)
    {
      {
        int size;
        if((size=this.size)!=0)
        {
          return uncheckedlastIndexOf(size,(val));
        }
      }
      return -1;
    }
    @Override
    void uncheckedCopyInto(byte[] dst,int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(Byte[] dst,int size)
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
    void uncheckedCopyInto(Object[] dst,int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,size);
    }
    private boolean sublistcontains (int rootOffset,int size
    ,int val
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
    ,int val
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
    ,int val
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
    ,int val
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
    ,int val
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
    ,int val
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
      final byte[] arr;
      int hash,i;
      for(hash=31+((arr=this.arr)[i=0]);++i!=size;hash=hash*31+(arr[i])){}
      return hash;
    }
    @Override
    int uncheckedToString(int size,char[] buffer)
    {
      final byte[] arr;
      int bufferOffset,i;
      for(bufferOffset=ToStringUtil.getStringShort((arr=this.arr)[i=0],buffer,1);++i!=size;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringShort(arr[i],buffer,bufferOffset)){}
      return bufferOffset;
    }
    @Override
    void uncheckedToString(int size,ToStringUtil.OmniStringBuilder builder)
    {
      final byte[] arr;
      int i;
      for(builder.uncheckedAppendShort((arr=this.arr)[i=0]);++i!=size;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendShort(arr[i])){}
    }
    private void sublistForEach(int rootOffset,int size,ByteConsumer action)
    {
      final var arr=this.arr;
      for(size+=rootOffset;;)
      {
        action.accept((byte)arr[rootOffset]);
        if(++rootOffset==size)
        {
          return;
        }
      }
    }
    @Override
    void uncheckedForEach(int size,ByteConsumer action)
    {
      final var arr=this.arr;
      for(int i=0;;)
      {
        action.accept((byte)arr[i]);
        if(++i==size)
        {
          return;
        }
      }
    }
    @Override
    boolean uncheckedRemoveIf(int size,BytePredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public OmniIterator.OfByte iterator()
    {
      //TODO
      return null;
    }
    @Override
    public OmniListIterator.OfByte listIterator()
    {
      //TODO
      return null;
    }
    @Override
    public OmniListIterator.OfByte listIterator(int index)
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
    public void add(int index,byte val)
    {
      uncheckedAdd(index,val,this.size);
    }
    @Override
    public void add(int index,Byte val)
    {
      add(index,(byte)val);
    }
    private void uncheckedAdd(int index,byte val,int size)
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
    public Byte remove(int index)
    {
      return removeByteAt(index);
    }
    @Override
    public byte removeByteAt(int index)
    {
      return super.uncheckedremoveAtIndex(index,this.size);
    }
    @Override
    public void replaceAll(ByteUnaryOperator operator)
    {
      int size;
      if((size=this.size)!=0)
      {
        uncheckedReplaceAll(size,operator);
      }
    }
    @Override
    public void replaceAll(UnaryOperator<Byte> operator)
    {
      int size;
      if((size=this.size)!=0)
      {
        uncheckedReplaceAll(size,operator::apply);
      }
    }
    private void uncheckedReplaceAll(int size,ByteUnaryOperator operator)
    {
      final var arr=this.arr;
      for(int i=0;;)
      {
        arr[i]=operator.applyAsByte((byte)arr[i]);
        if(++i==size)
        {
          return;
        }
      }
    }
    @Override
    public byte set(int index,byte val)
    {
      final byte[] arr;
      final byte ret=(byte)(arr=this.arr)[index];
      arr[index]=val;
      return ret;
    }
    @Override
    public Byte set(int index,Byte val)
    {
      return set(index,(byte)val);
    }
    @Override
    public void sort(ByteComparator sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        if(sorter==null)
        {
          ByteSortUtil.uncheckedAscendingSort(this.arr,0,size);
        }
        else
        {
          ByteSortUtil.uncheckedStableSort(this.arr,0,size,sorter);
        }
      }
    }
    @Override
    public void sort(Comparator<? super Byte> sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        if(sorter==null)
        {
          ByteSortUtil.uncheckedAscendingSort(this.arr,0,size);
        }
        else
        {
          ByteSortUtil.uncheckedStableSort(this.arr,0,size,sorter::compare);
        }
      }
    }
    @Override
    public void stableAscendingSort()
    {
      int size;
      if((size=this.size)>1)
      {
        ByteSortUtil.uncheckedAscendingSort(this.arr,0,size);
      }
    }
    @Override
    public void stableDescendingSort()
    {
      int size;
      if((size=this.size)>1)
      {
        ByteSortUtil.uncheckedDescendingSort(this.arr,0,size);
      }
    }
    @Override
    public OmniList.OfByte subList(int fromIndex,int toIndex)
    {
      //TODO
      return null;
    }
    @Override
    public void unstableSort(ByteComparator sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        if(sorter==null)
        {
          ByteSortUtil.uncheckedAscendingSort(this.arr,0,size);
        }
        else
        {
          ByteSortUtil.uncheckedUnstableSort(this.arr,0,size,sorter);
        }
      }
    }
  }
  public static class CheckedArrList extends UncheckedArrList
  {
    private static class SubListImpl implements OmniList.OfByte
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
        final byte[] copy;
        int size;
        if((size=this.size)!=0)
        {
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new byte[size],0,size);
        }
        else
        {
          copy=OmniArray.OfByte.DEFAULT_ARR;
        }
        return new CheckedArrList(size,copy);
      }
      @Override
      public boolean add(byte val)
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
          ((ByteArrSeq)root).uncheckedInsert(rootSize,this.rootOffset,val);
          ++this.size;
        }
        else
        {
          for(var curr=parent;curr!=null;parent.modCount=modCount,parent.size=1){}
          ((ByteArrSeq)root).uncheckedInit(val);
          this.size=1;
        }
        return true;
      }
      @Override
      public void add(int index,byte val)
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
          ((ByteArrSeq)root).uncheckedInsert(rootSize,index+this.rootOffset,val);
          ++this.size;
        }
        else
        {
          for(var curr=parent;curr!=null;parent.modCount=modCount,parent.size=1){}
          ((ByteArrSeq)root).uncheckedInit(val);
          this.size=1;
        }
      }
      @Override
      public boolean add(Byte val)
      {
        return add((byte)val);
      }
      @Override
      public void add(int index,Byte val)
      {
        add(index,(byte)val);
      }
      @Override
      public void forEach(ByteConsumer action)
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
      public void forEach(Consumer<? super Byte> action)
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
      public boolean removeIf(BytePredicate filter)
      {
        //TODO
        return false;
      }
      @Override
      public boolean removeIf(Predicate<? super Byte> action)
      {
        //TODO
        return false;
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
      public Byte[] toArray()
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        final int size;
        if((size=this.size)!=0)
        {
          final Byte[] copy;
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new Byte[size],0,size);
          return copy;
        }
        return OmniArray.OfByte.DEFAULT_BOXED_ARR;
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
      public void put(int index,byte val)
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
      public byte getByte(int index)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        if(index<0 || index>=size)
        {
          throw new InvalidReadIndexException(index,size);
        }
        return (byte)root.arr[index+this.rootOffset];
      }
      @Override
      public Byte get(int index)
      {
        return getByte(index);
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
      public OmniIterator.OfByte iterator()
      {
        //TODO
        return null;
      }
      @Override
      public OmniListIterator.OfByte listIterator()
      {
        //TODO
        return null;
      }
      @Override
      public OmniListIterator.OfByte listIterator(int index)
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
          return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,TypeUtil.castToByte(val));
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
          if(val==(byte)val)
          {
            return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,val);
          }
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
          final byte v;
          if(val==(v=(byte)val))
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
          final byte v;
          if(val==(v=(byte)val))
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
          final byte v;
          if((v=(byte)val)==val)
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
          if(val instanceof Byte)
          {
            return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,(byte)(val));
          }
        }
        return false;
      }
      @Override
      public
      boolean
      contains(byte val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        {
          int size;
          if((size=this.size)!=0)
          {
            return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,(val));
          }
        }
        return false;
      }
      @Override
      public
      boolean
      contains(char val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        if(val<=Byte.MAX_VALUE)
        {
          int size;
          if((size=this.size)!=0)
          {
            return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,(val));
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
          return uncheckedremoveVal(size,TypeUtil.castToByte(val));
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
          if(val==(byte)val)
          {
            return uncheckedremoveVal(size,val);
          }
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
          final byte v;
          if(val==(v=(byte)val))
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
          final byte v;
          if(val==(v=(byte)val))
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
          final byte v;
          if((v=(byte)val)==val)
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
          if(val instanceof Byte)
          {
            return uncheckedremoveVal(size,(byte)(val));
          }
        }
        CheckedCollection.checkModCount(this.modCount,root.modCount);
        return false;
      }
      @Override
      public
      boolean
      removeVal(byte val)
      {
        {
          int size;
          if((size=this.size)!=0)
          {
            return uncheckedremoveVal(size,(val));
          }
        }
        CheckedCollection.checkModCount(this.modCount,root.modCount);
        return false;
      }
      @Override
      public
      boolean
      removeVal(char val)
      {
        if(val<=Byte.MAX_VALUE)
        {
          int size;
          if((size=this.size)!=0)
          {
            return uncheckedremoveVal(size,(val));
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
          return ((UncheckedArrList)root).sublistindexOf(this.rootOffset,size,TypeUtil.castToByte(val));
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
          if(val==(byte)val)
          {
            return ((UncheckedArrList)root).sublistindexOf(this.rootOffset,size,val);
          }
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
          final byte v;
          if(val==(v=(byte)val))
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
          final byte v;
          if(val==(v=(byte)val))
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
          final byte v;
          if((v=(byte)val)==val)
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
          if(val instanceof Byte)
          {
            return ((UncheckedArrList)root).sublistindexOf(this.rootOffset,size,(byte)(val));
          }
        }
        return -1;
      }
      @Override
      public
      int
      indexOf(char val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        if(val<=Byte.MAX_VALUE)
        {
          int size;
          if((size=this.size)!=0)
          {
            return ((UncheckedArrList)root).sublistindexOf(this.rootOffset,size,(val));
          }
        }
        return -1;
      }
      @Override
      public
      int
      indexOf(byte val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        {
          int size;
          if((size=this.size)!=0)
          {
            return ((UncheckedArrList)root).sublistindexOf(this.rootOffset,size,(val));
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
          return ((UncheckedArrList)root).sublistlastIndexOf(this.rootOffset,size,TypeUtil.castToByte(val));
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
          if(val==(byte)val)
          {
            return ((UncheckedArrList)root).sublistlastIndexOf(this.rootOffset,size,val);
          }
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
          final byte v;
          if(val==(v=(byte)val))
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
          final byte v;
          if(val==(v=(byte)val))
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
          final byte v;
          if((v=(byte)val)==val)
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
          if(val instanceof Byte)
          {
            return ((UncheckedArrList)root).sublistlastIndexOf(this.rootOffset,size,(byte)(val));
          }
        }
        return -1;
      }
      @Override
      public
      int
      lastIndexOf(char val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        if(val<=Byte.MAX_VALUE)
        {
          int size;
          if((size=this.size)!=0)
          {
            return ((UncheckedArrList)root).sublistlastIndexOf(this.rootOffset,size,(val));
          }
        }
        return -1;
      }
      @Override
      public
      int
      lastIndexOf(byte val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        {
          int size;
          if((size=this.size)!=0)
          {
            return ((UncheckedArrList)root).sublistlastIndexOf(this.rootOffset,size,(val));
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
          if(size>(Integer.MAX_VALUE/3))
          {
            throw new OutOfMemoryError();
          }
          final char[] buffer;
          if(size<=(OmniArray.MAX_ARR_SIZE/((6)<<1)))
          {
            final byte[] arr;
            int bufferOffset,i;
            for(bufferOffset=ToStringUtil.getStringShort((arr=root.arr)[i=this.rootOffset],buffer=new char[size*(6)],1),size+=i;++i!=size;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringShort(arr[i],buffer,bufferOffset)){}
            buffer[bufferOffset]=']';
            buffer[0]='[';
            return new String(buffer,0,bufferOffset+1);
          }
          else
          {
            final ToStringUtil.OmniStringBuilder builder;
            final byte[] arr;
            int i;
            for((builder=new ToStringUtil.OmniStringBuilder(1,new char[size<=(int)(OmniArray.MAX_ARR_SIZE/5)?(size*5):OmniArray.MAX_ARR_SIZE])).uncheckedAppendShort((arr=root.arr)[i=this.rootOffset]),size+=i;++i!=size;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendShort(arr[i])){}
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
          final byte[] arr;
          int hash;
          int i;
          for(hash=31+((arr=root.arr)[i=this.rootOffset]),size+=i;++i!=size;hash=hash*31+(arr[i])){}
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
        ,int val
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
    public CheckedArrList(int size,byte[] arr)
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
      byte[] copy;
      int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new byte[size],0,size);
      }
      else
      {
        copy=OmniArray.OfByte.DEFAULT_ARR;
      }
      return new CheckedArrList(size,copy);
    }
    @Override
    public void push(byte val)
    {
      ++this.modCount;
      super.push(val);
    }
    @Override
    public void put(int index,byte val)
    {
      if(index>=0 && index<size)
      {
        super.put(index,val);
        return;
      }
      throw new InvalidWriteIndexException(index,size);
    }
    @Override
    public byte set(int index,byte val)
    {
      if(index>=0 && index<size)
      {
        return super.set(index,val);
      }
      throw new InvalidReadIndexException(index,size);
    }
    @Override
    public byte removeByteAt(int index)
    {
      int size;
      if((size=this.size)>index && index>=0)
      {
        ++this.modCount;
        return ((ByteArrSeq)this).uncheckedremoveAtIndex(index,size);
      }
      throw new InvalidWriteIndexException(index,size);
    }
    @Override
    public void unstableSort(ByteComparator sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        if(sorter==null)
        {
          ByteSortUtil.uncheckedAscendingSort(this.arr,0,size);
          ++this.modCount;
        }
        else
        {
          int modCount=this.modCount;
          try
          {
            ByteSortUtil.uncheckedUnstableSort(this.arr,0,size,sorter);
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
    public void replaceAll(UnaryOperator<Byte> operator)
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
    public void replaceAll(ByteUnaryOperator operator)
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
        ByteSortUtil.uncheckedAscendingSort(this.arr,0,size);
        ++this.modCount;
      }
    }
    @Override
    public void stableDescendingSort()
    {
      int size;
      if((size=this.size)>1)
      {
        ByteSortUtil.uncheckedDescendingSort(this.arr,0,size);
        ++this.modCount;
      }
    }
    @Override
    public OmniList.OfByte subList(int fromIndex,int toIndex)
    {
      //TODO
      return null;
    }
    @Override
    public void sort(ByteComparator sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        if(sorter==null)
        {
          ByteSortUtil.uncheckedAscendingSort(this.arr,0,size);
          ++this.modCount;
        }
        else
        {
          int modCount=this.modCount;
          try
          {
            ByteSortUtil.uncheckedStableSort(this.arr,0,size,sorter);
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
    public void sort(Comparator<? super Byte> sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        if(sorter==null)
        {
          ByteSortUtil.uncheckedAscendingSort(this.arr,0,size);
          ++this.modCount;
        }
        else
        {
          int modCount=this.modCount;
          try
          {
            ByteSortUtil.uncheckedStableSort(this.arr,0,size,sorter::compare);
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
    ,int val
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
    void uncheckedForEach(int size,ByteConsumer action)
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
    boolean uncheckedRemoveIf(int size,BytePredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public OmniIterator.OfByte iterator()
    {
      //TODO
      return null;
    }
    @Override
    public OmniListIterator.OfByte listIterator()
    {
      //TODO
      return null;
    }
    @Override
    public OmniListIterator.OfByte listIterator(int index)
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
    public byte getByte(int index)
    {
      if(index>=0 && index<size)
      {
        return super.getByte(index);
      }
      throw new InvalidReadIndexException(index,size);
    }
    @Override
    public void add(int index,byte val)
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
