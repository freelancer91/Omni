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
import java.util.ConcurrentModificationException;
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
  public boolean removeIf(BooleanPredicate filter)
  {
    int size;
    if((size=this.size)!=0)
    {
      int newBound;
      if(size!=(newBound=doRemoveIf(this.arr,0,size,filter)))
      {
        this.size=newBound;
        return true;
      }
    }
    return false;
  }
  @Override
  public boolean removeIf(Predicate<? super Boolean> filter)
  {
    int size;
    if((size=this.size)!=0)
    {
      int newBound;
      if(size!=(newBound=doRemoveIf(this.arr,0,size,filter::test)))
      {
        this.size=newBound;
        return true;
      }
    }
    return false;
  }
  static void uncheckedForEachAscending(boolean[] arr,int offset,int bound,BooleanConsumer action)
  {
    do
    {
      action.accept((boolean)arr[offset]);
    }
    while(++offset!=bound);
  }
  static void uncheckedForEachDescending(boolean[] arr,int offset,int bound,BooleanConsumer action)
  {
    do
    {
      action.accept((boolean)arr[--bound]);
    }
    while(bound!=offset);
  }
  private static int doRemoveIf(boolean[] arr,int srcOffset,int srcBound,BooleanPredicate filter)
  {
    boolean v;
    if(filter.test(v=arr[srcOffset]))
    {
      int dstOffset=srcOffset;
      while(++srcOffset!=srcBound)
      {
        if(arr[srcOffset]^v)
        {
          if(!filter.test(v=!v))
          {
            arr[dstOffset]=v;
            return pullSurvivorsDown(arr,srcOffset,srcBound,dstOffset+1,v);
          }
          break;
        }
      }
      return dstOffset;
    }
    else
    {
      while(++srcOffset!=srcBound)
      {
        if(arr[srcOffset]^v)
        {
          if(filter.test(!v))
          {
            return pullSurvivorsDown(arr,srcOffset,srcBound,srcOffset,v);
          }
          break;
        }
      }
      return srcBound;
    }
  }
    private static int pullSurvivorsDown(boolean[] arr,int srcOffset,int srcBound,int dstOffset,boolean retainThis)
    {
      while(++srcOffset!=srcBound)
      {
        if(arr[srcOffset]==retainThis)
        {
          arr[dstOffset++]=retainThis;
        }
      }
      return dstOffset;
    }
    private static int doRemoveIf(boolean[] arr,int srcOffset,int srcBound,BooleanPredicate filter,CheckedCollection.AbstractModCountChecker modCountChecker)
    {
      try
      {
        boolean v;
        if(filter.test(v=arr[srcOffset]))
        {
          int dstOffset=srcOffset;
          while(++srcOffset!=srcBound)
          {
            if(arr[srcOffset]^v)
            {
              if(!filter.test(v=!v))
              {
                modCountChecker.checkModCount();
                arr[dstOffset]=v;
                return pullSurvivorsDown(arr,srcOffset,srcBound,dstOffset+1,v);
              }
              break;
            }
          }
          modCountChecker.checkModCount();
          return dstOffset;
        }
        else
        {
          while(++srcOffset!=srcBound)
          {
            if(arr[srcOffset]^v)
            {
              if(!filter.test(!v))
              {
                break;
              }
              modCountChecker.checkModCount();
              return pullSurvivorsDown(arr,srcOffset,srcBound,srcOffset,v);
            }
          }
        }
        modCountChecker.checkModCount();
        return srcBound;
      }
      catch(final ConcurrentModificationException e)
      {
        throw e;
      }
      catch(final RuntimeException e)
      {
        throw modCountChecker.handleException(e);
      }
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
  public
  static class UncheckedStack
    extends BooleanArrSeq
    implements OmniStack.OfBoolean
  {
    public UncheckedStack()
    {
      super();
    }
    public UncheckedStack(int initialCapacity)
    {
      super(initialCapacity);
    }
    private UncheckedStack(int size,boolean[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implement equals(Object) method
      return false;
    }
    @Override
    public Object clone()
    {
      final boolean[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new boolean[size],0,size);
      }
      else
      {
        copy=OmniArray.OfBoolean.DEFAULT_ARR;
      }
      return new UncheckedStack(size,copy);
    }
    @Override
    public OmniIterator.OfBoolean iterator()
    {
      return new Itr(this);
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor)
    {
      final int size;
      T[] dst=arrConstructor.apply(size=this.size);
      if(size!=0)
      {
        ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,size);
      }
      return dst;
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
    private static class Itr
      extends AbstractItr
    {
      transient final UncheckedStack parent;
      transient int cursor;
      private Itr(UncheckedStack parent)
      {
        this.parent=parent;
        this.cursor=parent.size;
      }
      @Override
      public boolean nextBoolean()
      {
            return (boolean)parent.arr[this.cursor--];
      }
      @Override
      public void forEachRemaining(BooleanConsumer action)
      {
        int cursor;
        if((cursor=this.cursor)>0)
        {
          {
            uncheckedForEachDescending(this.parent.arr,0,cursor,action);
          }
          this.cursor=0;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Boolean> action)
      {
        int cursor;
        if((cursor=this.cursor)>0)
        {
          {
            uncheckedForEachDescending(this.parent.arr,0,cursor,action::accept);
          }
          this.cursor=0;
        }
      }
      @Override
      public boolean hasNext()
      {
        return this.cursor>0;
      }
      @Override
      public void remove()
      {
        final UncheckedStack parent;
        int cursor;
        ArrCopy.semicheckedSelfCopy((parent=this.parent).arr,(cursor=this.cursor)+1,cursor,(--parent.size)-cursor);
      }
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
     @Override
     public void forEach(BooleanConsumer action)
     {
       int size;
       if((size=this.size)!=0)
       {
         {
           uncheckedForEachDescending(arr,0,size,action);
         }
       }
     }
    @Override
    public void forEach(Consumer<? super Boolean> action)
    {
      int size;
      if((size=this.size)!=0)
      {
        {
          uncheckedForEachDescending(arr,0,size,action::accept);
        }
      }
    }
    @Override
    public boolean popBoolean()
    {
      return (boolean)arr[--this.size];
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
    @Override
    public Boolean pop()
    {
      return popBoolean();
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
    void uncheckedForEach(int size,BooleanConsumer action)
    {
      {
        uncheckedForEachDescending(this.arr,0,size,action);
      }
    }
    @Override
    void uncheckedCopyInto(Object[] dst,int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,size);
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
  }
  public
  static class CheckedStack
    extends UncheckedStack
  {
    transient int modCount;
    public CheckedStack()
    {
      super();
    }
    public CheckedStack(int initialCapacity)
    {
      super(initialCapacity);
    }
    private CheckedStack(int size,boolean[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implement equals(Object) method
      return false;
    }
    @Override
    public Object clone()
    {
      final boolean[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new boolean[size],0,size);
      }
      else
      {
        copy=OmniArray.OfBoolean.DEFAULT_ARR;
      }
      return new CheckedStack(size,copy);
    }
    @Override
    public OmniIterator.OfBoolean iterator()
    {
      return new Itr(this);
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
    private static class Itr
      extends AbstractItr
    {
      transient final CheckedStack parent;
      transient int cursor;
      transient int modCount;
      transient int lastRet;
      private Itr(CheckedStack parent)
      {
        this.parent=parent;
        this.cursor=parent.size;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      @Override
      public boolean nextBoolean()
      {
            final int cursor;
            final CheckedStack root;
            CheckedCollection.checkModCount(this.modCount,(root=this.parent).modCount);
            if((cursor=this.cursor)<0)
            {
              throw new NoSuchElementException();
            }
            this.cursor=cursor-1;
            this.lastRet=cursor;
            return (boolean)root.arr[cursor];
      }
      @Override
      public void forEachRemaining(BooleanConsumer action)
      {
        int cursor;
        if((cursor=this.cursor)>0)
        {
          final CheckedStack root=this.parent;
          int modCount=this.modCount;
          try
          {
            uncheckedForEachDescending(root.arr,0,cursor,action);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
          this.lastRet=0;
          this.cursor=0;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Boolean> action)
      {
        int cursor;
        if((cursor=this.cursor)>0)
        {
          final CheckedStack root=this.parent;
          int modCount=this.modCount;
          try
          {
            uncheckedForEachDescending(root.arr,0,cursor,action::accept);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
          this.lastRet=0;
          this.cursor=0;
        }
      }
      @Override
      public boolean hasNext()
      {
        return this.cursor>0;
      }
      @Override
      public void remove()
      {
        int cursor;
        if((cursor=this.lastRet)==-1)
        {
          throw new IllegalStateException();
        }
        int modCount;
        final CheckedStack root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.parent).modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        ArrCopy.semicheckedSelfCopy(root.arr,cursor+1,cursor,(--root.size)-cursor);
        this.cursor=cursor;
        this.lastRet=-1;
      }
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
     public void forEach(BooleanConsumer action)
     {
       int size;
       if((size=this.size)!=0)
       {
         int modCount=this.modCount;
         try
         {
           uncheckedForEachDescending(arr,0,size,action);
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
      int size;
      if((size=this.size)!=0)
      {
        int modCount=this.modCount;
        try
        {
          uncheckedForEachDescending(arr,0,size,action::accept);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
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
    void uncheckedForEach(int size,BooleanConsumer action)
    {
      int modCount=this.modCount;
      try
      {
        uncheckedForEachDescending(this.arr,0,size,action);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override
    public boolean removeIf(BooleanPredicate filter)
    {
      int size;
      if((size=this.size)!=0)
      {
        int newBound;
        int modCount;
        if(size!=(newBound=doRemoveIf(this.arr,0,size,filter,new CheckedCollection.AbstractModCountChecker(modCount=this.modCount){
          @Override
          protected int getActualModCount(){
            return CheckedStack.this.modCount;
          }
        })))
        {
          this.modCount=modCount+1;
          this.size=newBound;
          return true;
        }
      }
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Boolean> filter)
    {
      int size;
      if((size=this.size)!=0)
      {
        int newBound;
        int modCount;
        if(size!=(newBound=doRemoveIf(this.arr,0,size,filter::test,new CheckedCollection.AbstractModCountChecker(modCount=this.modCount){
          @Override
          protected int getActualModCount(){
            return CheckedStack.this.modCount;
          }
        })))
        {
          this.modCount=modCount+1;
          this.size=newBound;
          return true;
        }
      }
      return false;
    }
    public void push(boolean val)
    {
      ++this.modCount;
      super.push(val);
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
  }
  public
  static class UncheckedList
    extends BooleanArrSeq
    implements ListDefault
  {
    public UncheckedList()
    {
      super();
    }
    public UncheckedList(int initialCapacity)
    {
      super(initialCapacity);
    }
    private UncheckedList(int size,boolean[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implement equals(Object) method
      return false;
    }
    @Override
    public Object clone()
    {
      final boolean[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new boolean[size],0,size);
      }
      else
      {
        copy=OmniArray.OfBoolean.DEFAULT_ARR;
      }
      return new UncheckedList(size,copy);
    }
    @Override
    public OmniIterator.OfBoolean iterator()
    {
      return new Itr(this);
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor)
    {
      final int size;
      T[] dst=arrConstructor.apply(size=this.size);
      if(size!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,dst,0,size);
      }
      return dst;
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
    private static class Itr
      extends AbstractItr
    {
      transient final UncheckedList parent;
      transient int cursor;
      private Itr(UncheckedList parent)
      {
        this.parent=parent;
      }
      private Itr(UncheckedList parent,int cursor)
      {
        this.parent=parent;
        this.cursor=cursor;
      }
      @Override
      public boolean nextBoolean()
      {
            return (boolean)parent.arr[this.cursor++];
      }
      @Override
      public void forEachRemaining(BooleanConsumer action)
      {
        int cursor;
        int bound;
        final UncheckedList root;
        if((cursor=this.cursor)<(bound=(root=this.parent).size))
        {
          {
            uncheckedForEachAscending(root.arr,cursor,bound,action);
          }
          this.cursor=bound;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Boolean> action)
      {
        int cursor;
        int bound;
        final UncheckedList root;
        if((cursor=this.cursor)<(bound=(root=this.parent).size))
        {
          {
            uncheckedForEachAscending(root.arr,cursor,bound,action::accept);
          }
          this.cursor=bound;
        }
      }
      @Override
      public boolean hasNext()
      {
        return this.cursor<parent.size;
      }
      @Override
      public void remove()
      {
        final UncheckedList parent;
        int cursor;
        ArrCopy.semicheckedSelfCopy((parent=this.parent).arr,cursor=this.cursor,--cursor,(--parent.size)-cursor);
        this.cursor=cursor;
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
     @Override
     public void forEach(BooleanConsumer action)
     {
       int size;
       if((size=this.size)!=0)
       {
         {
           uncheckedForEachAscending(arr,0,size,action);
         }
       }
     }
    @Override
    public void forEach(Consumer<? super Boolean> action)
    {
      int size;
      if((size=this.size)!=0)
      {
        {
          uncheckedForEachAscending(arr,0,size,action::accept);
        }
      }
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
    /*
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
    */
    private void uncheckedReplaceAll(int offset,int bound,BooleanPredicate operator)
    {
      final var arr=this.arr;
      for(;;)
      {
        arr[offset]=operator.test((boolean)arr[offset]);
        if(++offset==bound)
        {
          return;
        }
      }
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
    void uncheckedForEach(int size,BooleanConsumer action)
    {
      {
        uncheckedForEachAscending(this.arr,0,size,action);
      }
    }
    @Override
    void uncheckedCopyInto(Object[] dst,int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,size);
    }
    @Override
    int uncheckedHashCode(int size)
    {
      final boolean[] arr;
      int hash;
      int i;
      for(hash=31+Boolean.hashCode((arr=this.arr)[i=0]);++i!=size;hash=hash*31+Boolean.hashCode(arr[i])){}
      return hash;
    }
    @Override
    int uncheckedToString(int size,char[] buffer)
    {
      final boolean[] arr;
      int bufferOffset;
      int i;
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
     private static class LstItr
       extends Itr implements OmniListIterator.OfBoolean
     {
       transient int lastRet;
       private LstItr(UncheckedList parent)
       {
         super(parent);
         this.lastRet=-1;
       }
       private LstItr(UncheckedList parent,int cursor)
       {
         super(parent);
         this.lastRet=-1;
       }
       @Override
       public boolean nextBoolean()
       {
             final int ret;
             this.lastRet=ret=this.cursor++;
             return (boolean)parent.arr[ret];
       }
       @Override
       public void forEachRemaining(BooleanConsumer action)
       {
         int cursor;
         int bound;
         final UncheckedList root;
         if((cursor=this.cursor)<(bound=(root=this.parent).size))
         {
           {
             uncheckedForEachAscending(root.arr,cursor,bound,action);
           }
           this.lastRet=bound-1;
           this.cursor=bound;
         }
       }
       @Override
       public void forEachRemaining(Consumer<? super Boolean> action)
       {
         int cursor;
         int bound;
         final UncheckedList root;
         if((cursor=this.cursor)<(bound=(root=this.parent).size))
         {
           {
             uncheckedForEachAscending(root.arr,cursor,bound,action::accept);
           }
           this.lastRet=bound-1;
           this.cursor=bound;
         }
       }
       @Override
       public void remove()
       {
         final UncheckedList parent;
         int cursor;
         ArrCopy.semicheckedSelfCopy((parent=this.parent).arr,(cursor=this.lastRet)+1,cursor,(--parent.size)-cursor);
         this.cursor=cursor;
         this.lastRet=-1;
       }
       @Override
       public boolean hasPrevious()
       {
         return this.cursor>0;
       }
       @Override
       public int nextIndex()
       {
         return this.cursor;
       }
       @Override
       public int previousIndex()
       {
         return this.cursor-1;
       }
       @Override
       public boolean previousBoolean()
       {
             final int ret;
             this.lastRet=ret=--this.cursor;
             return (boolean)parent.arr[ret];
       }
       @Override
       public void add(boolean val)
       {
         //TODO implement LstItr.add(boolean)
       }
       @Override
       public void set(boolean val)
       {
         //TODO implement LstItr.set(boolean)
       }
     }
    @Override
    public OmniList.OfBoolean subList(int fromIndex,int toIndex)
    {
      return new UncheckedSubList(this,fromIndex,toIndex-fromIndex);
    }
    @Override
    public OmniListIterator.OfBoolean listIterator()
    {
      return new LstItr(this);
    }
    @Override
    public OmniListIterator.OfBoolean listIterator(int index)
    {
      return new LstItr(this,index);
    }
    @Override
    public void sort(BooleanComparator sorter)
    {
      int size;
      if((size=this.size)>1)
      {
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
    }
    @Override
    public void sort(Comparator<? super Boolean> sorter)
    {
      int size;
      if((size=this.size)>1)
      {
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
    }
    @Override
    public void stableAscendingSort()
    {
      int size;
      if((size=this.size)>1)
      {
        {
          BooleanSortUtil.uncheckedAscendingSort(this.arr,0,size);
        }
      }
    }
    @Override
    public void stableDescendingSort()
    {
      int size;
      if((size=this.size)>1)
      {
        {
          BooleanSortUtil.uncheckedDescendingSort(this.arr,0,size);
        }
      }
    }
    @Override
    public void replaceAll(BooleanPredicate operator)
    {
      int size;
      if((size=this.size)!=0)
      {
        {
          ((UncheckedList)this).uncheckedReplaceAll(0,size,operator);
        }
      }
    }
    @Override
    public void replaceAll(UnaryOperator<Boolean> operator)
    {
      int size;
      if((size=this.size)!=0)
      {
        {
          ((UncheckedList)this).uncheckedReplaceAll(0,size,operator::apply);
        }
      }
    }
    @Override
    public void add(int index,boolean val)
    {
      ((UncheckedList)this).uncheckedAdd(index,val,size);
    }
    @Override
    public boolean getBoolean(int index)
    {
      return (boolean)arr[index];
    }
    @Override
    public void put(int index,boolean val)
    {
      arr[index]=val;
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
    public boolean removeBooleanAt(int index)
    {
      return ((BooleanArrSeq)this).uncheckedremoveAtIndex(index,size);
    }
    //TODO implement AND and OR in the template switches to avoid writing this twice
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
    //TODO implement AND and OR in the template switches to avoid writing this twice
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
  }
  public
  static class CheckedList
    extends UncheckedList
  {
    transient int modCount;
    public CheckedList()
    {
      super();
    }
    public CheckedList(int initialCapacity)
    {
      super(initialCapacity);
    }
    private CheckedList(int size,boolean[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implement equals(Object) method
      return false;
    }
    @Override
    public Object clone()
    {
      final boolean[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new boolean[size],0,size);
      }
      else
      {
        copy=OmniArray.OfBoolean.DEFAULT_ARR;
      }
      return new CheckedList(size,copy);
    }
    @Override
    public OmniIterator.OfBoolean iterator()
    {
      return new Itr(this);
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
    private static class Itr
      extends AbstractItr
    {
      transient final CheckedList parent;
      transient int cursor;
      transient int modCount;
      transient int lastRet;
      private Itr(CheckedList parent)
      {
        this.parent=parent;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      private Itr(CheckedList parent,int cursor)
      {
        this.parent=parent;
        this.cursor=cursor;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      @Override
      public boolean nextBoolean()
      {
            final int cursor;
            final CheckedList root;
            CheckedCollection.checkModCount(this.modCount,(root=this.parent).modCount);
            if((cursor=this.cursor)>=root.size)
            {
               throw new NoSuchElementException();
            }
            this.cursor=cursor+1;
            this.lastRet=cursor;
            return (boolean)root.arr[cursor];
      }
      @Override
      public void forEachRemaining(BooleanConsumer action)
      {
        int cursor;
        int bound;
        final CheckedList root;
        if((cursor=this.cursor)<(bound=(root=this.parent).size))
        {
          int modCount=this.modCount;
          try
          {
            uncheckedForEachAscending(root.arr,cursor,bound,action);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
          this.lastRet=bound-1;
          this.cursor=bound;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Boolean> action)
      {
        int cursor;
        int bound;
        final CheckedList root;
        if((cursor=this.cursor)<(bound=(root=this.parent).size))
        {
          int modCount=this.modCount;
          try
          {
            uncheckedForEachAscending(root.arr,cursor,bound,action::accept);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
          this.lastRet=bound-1;
          this.cursor=bound;
        }
      }
      @Override
      public boolean hasNext()
      {
        return this.cursor<parent.size;
      }
      @Override
      public void remove()
      {
        int cursor;
        if((cursor=this.lastRet)==-1)
        {
          throw new IllegalStateException();
        }
        int modCount;
        final CheckedList root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.parent).modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        ArrCopy.semicheckedSelfCopy(root.arr,cursor+1,cursor,(--root.size)-cursor);
        this.cursor=cursor;
        this.lastRet=-1;
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
     public void forEach(BooleanConsumer action)
     {
       int size;
       if((size=this.size)!=0)
       {
         int modCount=this.modCount;
         try
         {
           uncheckedForEachAscending(arr,0,size,action);
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
      int size;
      if((size=this.size)!=0)
      {
        int modCount=this.modCount;
        try
        {
          uncheckedForEachAscending(arr,0,size,action::accept);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override
    void uncheckedForEach(int size,BooleanConsumer action)
    {
      int modCount=this.modCount;
      try
      {
        uncheckedForEachAscending(this.arr,0,size,action);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override
    public boolean removeIf(BooleanPredicate filter)
    {
      int size;
      if((size=this.size)!=0)
      {
        int newBound;
        int modCount;
        if(size!=(newBound=doRemoveIf(this.arr,0,size,filter,new CheckedCollection.AbstractModCountChecker(modCount=this.modCount){
          @Override
          protected int getActualModCount(){
            return CheckedList.this.modCount;
          }
        })))
        {
          this.modCount=modCount+1;
          this.size=newBound;
          return true;
        }
      }
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Boolean> filter)
    {
      int size;
      if((size=this.size)!=0)
      {
        int newBound;
        int modCount;
        if(size!=(newBound=doRemoveIf(this.arr,0,size,filter::test,new CheckedCollection.AbstractModCountChecker(modCount=this.modCount){
          @Override
          protected int getActualModCount(){
            return CheckedList.this.modCount;
          }
        })))
        {
          this.modCount=modCount+1;
          this.size=newBound;
          return true;
        }
      }
      return false;
    }
    public void push(boolean val)
    {
      ++this.modCount;
      super.push(val);
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
     private static class LstItr
       extends Itr implements OmniListIterator.OfBoolean
     {
       private LstItr(CheckedList parent)
       {
         super(parent);
       }
       private LstItr(CheckedList parent,int cursor)
       {
         super(parent);
       }
       @Override
       public boolean hasPrevious()
       {
         return this.cursor>0;
       }
       @Override
       public int nextIndex()
       {
         return this.cursor;
       }
       @Override
       public int previousIndex()
       {
         return this.cursor-1;
       }
       @Override
       public boolean previousBoolean()
       {
             int cursor;
             final CheckedList root;
             CheckedCollection.checkModCount(this.modCount,(root=this.parent).modCount);
             if((cursor=this.cursor)<=0)
             {
                throw new NoSuchElementException();
             }
             this.cursor=--cursor;
             this.lastRet=cursor;
             return (boolean)root.arr[cursor];
       }
       @Override
       public void add(boolean val)
       {
         //TODO implement LstItr.add(boolean)
       }
       @Override
       public void set(boolean val)
       {
         //TODO implement LstItr.set(boolean)
       }
     }
    @Override
    public OmniList.OfBoolean subList(int fromIndex,int toIndex)
    {
      int span;
      if((span=toIndex-fromIndex)<0|| fromIndex<0 ||  toIndex>this.size)
      {
        throw new InvalidReadIndexException("fromIndex="+fromIndex+"; toIndex="+toIndex+"; size="+this.size);
      }
      return new CheckedSubList(this,fromIndex,span,modCount);
    }
    @Override
    public OmniListIterator.OfBoolean listIterator()
    {
      return new LstItr(this);
    }
    @Override
    public OmniListIterator.OfBoolean listIterator(int index)
    {
      if(index<0 || size<index)
      {
        throw new InvalidWriteIndexException(index,size);
      }
      return new LstItr(this,index);
    }
    @Override
    public void sort(BooleanComparator sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        {
          if(sorter==null)
          {
            BooleanSortUtil.uncheckedAscendingSort(this.arr,0,size);
          }
          else
          {
            BooleanSortUtil.uncheckedSort(this.arr,0,size,sorter);
          }
          ++this.modCount;
        }
      }
    }
    @Override
    public void sort(Comparator<? super Boolean> sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        {
          if(sorter==null)
          {
            BooleanSortUtil.uncheckedAscendingSort(this.arr,0,size);
          }
          else
          {
            BooleanSortUtil.uncheckedSort(this.arr,0,size,sorter::compare);
          }
          ++this.modCount;
        }
      }
    }
    @Override
    public void stableAscendingSort()
    {
      int size;
      if((size=this.size)>1)
      {
        {
          BooleanSortUtil.uncheckedAscendingSort(this.arr,0,size);
          ++this.modCount;
        }
      }
    }
    @Override
    public void stableDescendingSort()
    {
      int size;
      if((size=this.size)>1)
      {
        {
          BooleanSortUtil.uncheckedDescendingSort(this.arr,0,size);
          ++this.modCount;
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
          ((UncheckedList)this).uncheckedReplaceAll(0,size,operator);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
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
          ((UncheckedList)this).uncheckedReplaceAll(0,size,operator::apply);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override
    public void add(int index,boolean val)
    {
      int size;
      if((size=this.size)<index || index<0)
      {
        throw new InvalidWriteIndexException(index,size);
      }
      ++this.modCount;
      ((UncheckedList)this).uncheckedAdd(index,val,size);
    }
    @Override
    public boolean getBoolean(int index)
    {
      if(index<0 || index>=size)
      {
        throw new InvalidReadIndexException(index,size);
      }
      return (boolean)arr[index];
    }
    @Override
    public void put(int index,boolean val)
    {
      if(index<0 || index>=size)
      {
        throw new InvalidWriteIndexException(index,size);
      }
      arr[index]=val;
    }
    @Override
    public boolean set(int index,boolean val)
    {
      if(index<0 || index>=size)
      {
        throw new InvalidReadIndexException(index,size);
      }
      final boolean[] arr;
      final boolean ret=(boolean)(arr=this.arr)[index];
      arr[index]=val;
      return ret;
    }
    @Override
    public boolean removeBooleanAt(int index)
    {
      int size;
      if((size=this.size)<=index || index<0)
      {
        throw new InvalidWriteIndexException(index,size);
      }
      ++this.modCount;
      return ((BooleanArrSeq)this).uncheckedremoveAtIndex(index,size);
    }
  }
  private
  static class UncheckedSubList
    implements SubListDefault
  {
    transient final UncheckedList root;
    transient final UncheckedSubList parent;
    transient final int rootOffset;
    transient int size;
    private UncheckedSubList(UncheckedList root,int rootOffset,int size
    )
    {
      this.root=root;
      this.parent=null;
      this.rootOffset=rootOffset;
      this.size=size;
    }
    private UncheckedSubList(UncheckedSubList parent,int rootOffset,int size
    )
    {
      this.root=parent.root;
      this.parent=parent;
      this.rootOffset=rootOffset;
      this.size=size;
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implement equals(Object) method
      return false;
    }
    @Override
    public Object clone()
    {
      final boolean[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new boolean[size],0,size);
      }
      else
      {
        copy=OmniArray.OfBoolean.DEFAULT_ARR;
      }
      return new UncheckedList(size,copy);
    }
    @Override
    public OmniIterator.OfBoolean iterator()
    {
      return new Itr(this);
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor)
    {
      final int size;
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
    private static class Itr
      extends AbstractItr
    {
      transient final UncheckedSubList parent;
      transient int cursor;
      private Itr(UncheckedSubList parent)
      {
        this.parent=parent;
        this.cursor=parent.rootOffset;
      }
      private Itr(UncheckedSubList parent,int cursor)
      {
        this.parent=parent;
        this.cursor=cursor+parent.rootOffset;
      }
      @Override
      public boolean nextBoolean()
      {
            return (boolean)parent.root.arr[this.cursor++];
      }
      @Override
      public void forEachRemaining(BooleanConsumer action)
      {
        int cursor;
        int bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
        {
          {
            uncheckedForEachAscending(parent.root.arr,cursor,bound,action);
          }
          this.cursor=bound;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Boolean> action)
      {
        int cursor;
        int bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
        {
          {
            uncheckedForEachAscending(parent.root.arr,cursor,bound,action::accept);
          }
          this.cursor=bound;
        }
      }
      @Override
      public boolean hasNext()
      {
        final UncheckedSubList parent;
        return this.cursor<(parent=this.parent).rootOffset+parent.size;
      }
      @Override
      public void remove()
      {
        UncheckedSubList parent;
        final UncheckedList root;
        int cursor;
        ArrCopy.semicheckedSelfCopy((root=(parent=this.parent).root).arr,cursor=this.cursor,--cursor,(--root.size)-cursor);
        do{
          --parent.size;
        }while((parent=parent.parent)!=null);
        this.cursor=cursor;
      }
    }
    private boolean
    uncheckedremoveVal (int size
    ,boolean val
    )
    {
      final UncheckedList root;
      final var arr=(root=this.root).arr;
      {
        for(int i=this.rootOffset,bound=i+size;;)
        {
          if(
          val==arr[i]
          )
          {
            for(var curr=parent;curr!=null;--curr.size,curr=curr.parent){}
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
     @Override
     public void forEach(BooleanConsumer action)
     {
       int size;
       if((size=this.size)!=0)
       {
         {
           int rootOffset;
           uncheckedForEachAscending(root.arr,rootOffset=this.rootOffset,rootOffset+size,action);
         }
       }
     }
    @Override
    public void forEach(Consumer<? super Boolean> action)
    {
      int size;
      if((size=this.size)!=0)
      {
        {
          int rootOffset;
          uncheckedForEachAscending(root.arr,rootOffset=this.rootOffset,rootOffset+size,action::accept);
        }
      }
    }
    @Override
    public boolean removeIf(BooleanPredicate filter)
    {
      int size;
      if((size=this.size)!=0)
      {
        int newBound;
        final boolean[] arr;
        final UncheckedList root;
        int rootOffset,rootBound,numRemoved;
        if((numRemoved=(rootBound=(rootOffset=this.rootOffset)+size)-(newBound=doRemoveIf(arr=(root=this.root).arr,rootOffset,rootBound,filter)))!=0)
        {
          for(var curr=parent;curr!=null;curr.size-=numRemoved,curr=curr.parent){}
          int rootSize;
          ArrCopy.semicheckedSelfCopy(arr,rootBound,newBound,(rootSize=root.size)-rootBound);
          root.size=rootSize-numRemoved;
          this.size=size-numRemoved;
          return true;
        }
      }
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Boolean> filter)
    {
      int size;
      if((size=this.size)!=0)
      {
        int newBound;
        final boolean[] arr;
        final UncheckedList root;
        int rootOffset,rootBound,numRemoved;
        if((numRemoved=(rootBound=(rootOffset=this.rootOffset)+size)-(newBound=doRemoveIf(arr=(root=this.root).arr,rootOffset,rootBound,filter::test)))!=0)
        {
          for(var curr=parent;curr!=null;curr.size-=numRemoved,curr=curr.parent){}
          int rootSize;
          ArrCopy.semicheckedSelfCopy(arr,rootBound,newBound,(rootSize=root.size)-rootBound);
          root.size=rootSize-numRemoved;
          this.size=size-numRemoved;
          return true;
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
        return ((UncheckedList)root).sublistcontains(this.rootOffset,size,(val));
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
        return ((UncheckedList)root).sublistcontains(this.rootOffset,size,v);
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
          return ((UncheckedList)root).sublistcontains(this.rootOffset,size,v);
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
          return ((UncheckedList)root).sublistcontains(this.rootOffset,size,v);
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
          return ((UncheckedList)root).sublistcontains(this.rootOffset,size,v);
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
          return ((UncheckedList)root).sublistcontains(this.rootOffset,size,(boolean)(val));
        }
      }
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
    public boolean isEmpty()
    {
      return this.size==0;
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
    public int size()
    {
      return this.size;
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
    public boolean add(boolean val)
    {
      int rootSize;
      final UncheckedList root;
      if((rootSize=(root=this.root).size)!=0)
      {
        for(var curr=parent;curr!=null;++curr.size,curr=curr.parent){}
        ((BooleanArrSeq)root).uncheckedInsert(rootSize,this.rootOffset,val);
        ++this.size;
      }
      else
      {
        for(var curr=parent;curr!=null;curr.size=1,curr=curr.parent){}
        ((BooleanArrSeq)root).uncheckedInit(val);
        this.size=1;
      }
      return true;
    }
    @Override
    public void clear()
    {
      int size;
      if((size=this.size)!=0)
      {
        for(var curr=parent;curr!=null;parent.size-=size){}
        final UncheckedList root;
        int rootOffset;
        ArrCopy.semicheckedSelfCopy((root=this.root).arr,(rootOffset=this.rootOffset)+size,rootOffset,(root.size-=size)-rootOffset);
        this.size=0;
      }
    }
     private static class LstItr
       extends Itr implements OmniListIterator.OfBoolean
     {
       transient int lastRet;
       private LstItr(UncheckedSubList parent)
       {
         super(parent);
         this.lastRet=-1;
       }
       private LstItr(UncheckedSubList parent,int cursor)
       {
         super(parent);
         this.lastRet=-1;
       }
       @Override
       public boolean nextBoolean()
       {
             final int ret;
             this.lastRet=ret=this.cursor++;
             return (boolean)parent.root.arr[ret];
       }
       @Override
       public void forEachRemaining(BooleanConsumer action)
       {
         int cursor;
         int bound;
         final UncheckedSubList parent;
         if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
         {
           {
             uncheckedForEachAscending(parent.root.arr,cursor,bound,action);
           }
           this.lastRet=bound-1;
           this.cursor=bound;
         }
       }
       @Override
       public void forEachRemaining(Consumer<? super Boolean> action)
       {
         int cursor;
         int bound;
         final UncheckedSubList parent;
         if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
         {
           {
             uncheckedForEachAscending(parent.root.arr,cursor,bound,action::accept);
           }
           this.lastRet=bound-1;
           this.cursor=bound;
         }
       }
       @Override
       public void remove()
       {
         UncheckedSubList parent;
         final UncheckedList root;
         int cursor;
         ArrCopy.semicheckedSelfCopy((root=(parent=this.parent).root).arr,(cursor=this.lastRet)+1,cursor,(--root.size)-cursor);
         do{
           --parent.size;
         }while((parent=parent.parent)!=null);
         this.cursor=cursor;
         this.lastRet=-1;
       }
       @Override
       public boolean hasPrevious()
       {
         return this.cursor>parent.rootOffset;
       }
       @Override
       public int nextIndex()
       {
         return this.cursor-parent.rootOffset;
       }
       @Override
       public int previousIndex()
       {
         return this.cursor-parent.rootOffset-1;
       }
       @Override
       public boolean previousBoolean()
       {
             final int ret;
             this.lastRet=ret=--this.cursor;
             return (boolean)parent.root.arr[ret];
       }
       @Override
       public void add(boolean val)
       {
         //TODO implement LstItr.add(boolean)
       }
       @Override
       public void set(boolean val)
       {
         //TODO implement LstItr.set(boolean)
       }
     }
    @Override
    public OmniList.OfBoolean subList(int fromIndex,int toIndex)
    {
      return new UncheckedSubList(this,this.rootOffset+fromIndex,toIndex-fromIndex);
    }
    @Override
    public OmniListIterator.OfBoolean listIterator()
    {
      return new LstItr(this);
    }
    @Override
    public OmniListIterator.OfBoolean listIterator(int index)
    {
      return new LstItr(this,index+this.rootOffset);
    }
    @Override
    public void sort(BooleanComparator sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        {
          if(sorter==null)
          {
            final int rootOffset;
            BooleanSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
          }
          else
          {
            final int rootOffset;
            BooleanSortUtil.uncheckedSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter);
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
        {
          if(sorter==null)
          {
            final int rootOffset;
            BooleanSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
          }
          else
          {
            final int rootOffset;
            BooleanSortUtil.uncheckedSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter::compare);
          }
        }
      }
    }
    @Override
    public void stableAscendingSort()
    {
      int size;
      if((size=this.size)>1)
      {
        {
          final int rootOffset;
          BooleanSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }
      }
    }
    @Override
    public void stableDescendingSort()
    {
      int size;
      if((size=this.size)>1)
      {
        {
          final int rootOffset;
          BooleanSortUtil.uncheckedDescendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }
      }
    }
    @Override
    public void replaceAll(BooleanPredicate operator)
    {
      int size;
      if((size=this.size)!=0)
      {
        {
          final int rootOffset;
          ((UncheckedList)root).uncheckedReplaceAll(rootOffset=this.rootOffset,rootOffset+size,operator);
        }
      }
    }
    @Override
    public void replaceAll(UnaryOperator<Boolean> operator)
    {
      int size;
      if((size=this.size)!=0)
      {
        {
          final int rootOffset;
          ((UncheckedList)root).uncheckedReplaceAll(rootOffset=this.rootOffset,rootOffset+size,operator::apply);
        }
      }
    }
    @Override
    public void add(int index,boolean val)
    {
      int rootSize;
      final UncheckedList root;
      if((rootSize=(root=this.root).size)!=0)
      {
        for(var curr=parent;curr!=null;++curr.size,curr=curr.parent){}
        ((BooleanArrSeq)root).uncheckedInsert(rootSize,index+this.rootOffset,val);
        ++this.size;
      }
      else
      {
        for(var curr=parent;curr!=null;curr.size=1,curr=curr.parent){}
        ((BooleanArrSeq)root).uncheckedInit(val);
        this.size=1;
      }
    }
    @Override
    public boolean getBoolean(int index)
    {
      return (boolean)root.arr[index+this.rootOffset];
    }
    @Override
    public void put(int index,boolean val)
    {
      root.arr[index+this.rootOffset]=val;
    }
    @Override
    public boolean set(int index,boolean val)
    {
      final boolean[] arr;
      final boolean ret=(boolean)(arr=root.arr)[index+=this.rootOffset];
      arr[index]=val;
      return ret;
    }
    @Override
    public boolean removeBooleanAt(int index)
    {
      for(var curr=parent;curr!=null;--curr.size,curr=curr.parent){}
      --this.size;
      final BooleanArrSeq root;
      return (root=this.root).uncheckedremoveAtIndex(index+this.rootOffset,root.size);
    }
    //TODO implement AND and OR in the template switches to avoid writing this twice
    @Override
    public
    int
    indexOf(boolean val)
    {
      int size;
      if((size=this.size)!=0)
      {
        return ((UncheckedList)root).sublistindexOf(this.rootOffset,size,(val));
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
        return ((UncheckedList)root).sublistindexOf(this.rootOffset,size,v);
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
          return ((UncheckedList)root).sublistindexOf(this.rootOffset,size,v);
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
          return ((UncheckedList)root).sublistindexOf(this.rootOffset,size,v);
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
          return ((UncheckedList)root).sublistindexOf(this.rootOffset,size,v);
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
          return ((UncheckedList)root).sublistindexOf(this.rootOffset,size,(boolean)(val));
        }
      }
      return -1;
    }
    //TODO implement AND and OR in the template switches to avoid writing this twice
    @Override
    public
    int
    lastIndexOf(boolean val)
    {
      int size;
      if((size=this.size)!=0)
      {
        return ((UncheckedList)root).sublistlastIndexOf(this.rootOffset,size,(val));
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
        return ((UncheckedList)root).sublistlastIndexOf(this.rootOffset,size,v);
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
          return ((UncheckedList)root).sublistlastIndexOf(this.rootOffset,size,v);
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
          return ((UncheckedList)root).sublistlastIndexOf(this.rootOffset,size,v);
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
          return ((UncheckedList)root).sublistlastIndexOf(this.rootOffset,size,v);
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
          return ((UncheckedList)root).sublistlastIndexOf(this.rootOffset,size,(boolean)(val));
        }
      }
      return -1;
    }
  }
  private
  static class CheckedSubList
    implements SubListDefault
  {
    transient final CheckedList root;
    transient final CheckedSubList parent;
    transient final int rootOffset;
    transient int size;
    transient int modCount;
    private CheckedSubList(CheckedList root,int rootOffset,int size
      ,int modCount
    )
    {
      this.modCount=modCount;
      this.root=root;
      this.parent=null;
      this.rootOffset=rootOffset;
      this.size=size;
    }
    private CheckedSubList(CheckedSubList parent,int rootOffset,int size
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
    public boolean equals(Object val)
    {
      //TODO implement equals(Object) method
      return false;
    }
    @Override
    public Object clone()
    {
      final boolean[] copy;
      final int size;
      final var root=checkModCountAndGetRoot();
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new boolean[size],0,size);
      }
      else
      {
        copy=OmniArray.OfBoolean.DEFAULT_ARR;
      }
      return new CheckedList(size,copy);
    }
    @Override
    public OmniIterator.OfBoolean iterator()
    {
      CheckedCollection.checkModCount(this.modCount,root.modCount);
      return new Itr(this);
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor)
    {
      final int size;
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
    public boolean[] toBooleanArray()
    {
      final var root=checkModCountAndGetRoot();
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
      final var root=checkModCountAndGetRoot();
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
      final var root=checkModCountAndGetRoot();
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
      final var root=checkModCountAndGetRoot();
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
      final var root=checkModCountAndGetRoot();
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
      final var root=checkModCountAndGetRoot();
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
      final var root=checkModCountAndGetRoot();
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
      final var root=checkModCountAndGetRoot();
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
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0)
      {
        final byte[] copy;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new byte[size],0,size);
        return copy;
      }
      return OmniArray.OfByte.DEFAULT_ARR;
    }
    private static class Itr
      extends AbstractItr
    {
      transient final CheckedSubList parent;
      transient int cursor;
      transient int modCount;
      transient int lastRet;
      private Itr(CheckedSubList parent)
      {
        this.parent=parent;
        this.cursor=parent.rootOffset;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      private Itr(CheckedSubList parent,int cursor)
      {
        this.parent=parent;
        this.cursor=cursor+parent.rootOffset;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      @Override
      public boolean nextBoolean()
      {
            final int cursor;
            final CheckedList root;
            final CheckedSubList parent;
            CheckedCollection.checkModCount(this.modCount,(root=(parent=this.parent).root).modCount);
            if((cursor=this.cursor)>=parent.rootOffset+parent.size)
            {
               throw new NoSuchElementException();
            }
            this.cursor=cursor+1;
            this.lastRet=cursor;
            return (boolean)root.arr[cursor];
      }
      @Override
      public void forEachRemaining(BooleanConsumer action)
      {
        int cursor;
        int bound;
        final CheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
        {
          final CheckedList root=parent.root;
          int modCount=this.modCount;
          try
          {
            uncheckedForEachAscending(root.arr,cursor,bound,action);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
          this.lastRet=bound-1;
          this.cursor=bound;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Boolean> action)
      {
        int cursor;
        int bound;
        final CheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
        {
          final CheckedList root=parent.root;
          int modCount=this.modCount;
          try
          {
            uncheckedForEachAscending(root.arr,cursor,bound,action::accept);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
          this.lastRet=bound-1;
          this.cursor=bound;
        }
      }
      @Override
      public boolean hasNext()
      {
        final CheckedSubList parent;
        return this.cursor<(parent=this.parent).rootOffset+parent.size;
      }
      @Override
      public void remove()
      {
        int cursor;
        if((cursor=this.lastRet)==-1)
        {
          throw new IllegalStateException();
        }
        int modCount;
        CheckedSubList parent;
        final CheckedList root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
        root.modCount=++modCount;
        do{
          parent.modCount=modCount;
          --parent.size;
        }while((parent=parent.parent)!=null);
        this.modCount=modCount;
        ArrCopy.semicheckedSelfCopy(root.arr,cursor+1,cursor,(--root.size)-cursor);
        this.cursor=cursor;
        this.lastRet=-1;
      }
    }
    private boolean
    uncheckedremoveVal (int size
    ,boolean val
    )
    {
      final CheckedList root;
      final var arr=(root=this.root).arr;
      int modCount=this.modCount;
      try
      {
        for(int i=this.rootOffset,bound=i+size;;)
        {
          if(
          val==arr[i]
          )
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
            root.modCount=++modCount;
            this.modCount=modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,--curr.size,curr=curr.parent){}
            this.size=size-1;
            ArrCopy.semicheckedSelfCopy(arr,i+1,i,(--root.size)-i);
            return true;
          }
          if(++i==bound)
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
          }
        }
      }
      catch(ConcurrentModificationException e)
      {
        throw e;
      }
      catch(RuntimeException e)
      {
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
    }
     @Override
     public void forEach(BooleanConsumer action)
     {
       int size;
       if((size=this.size)!=0)
       {
         final var root=this.root;
         int modCount=this.modCount;
         try
         {
           int rootOffset;
           uncheckedForEachAscending(root.arr,rootOffset=this.rootOffset,rootOffset+size,action);
         }
         finally
         {
           CheckedCollection.checkModCount(modCount,root.modCount);
         }
       }
       else
       {
         CheckedCollection.checkModCount(modCount,root.modCount);
       }
     }
    @Override
    public void forEach(Consumer<? super Boolean> action)
    {
      int size;
      if((size=this.size)!=0)
      {
        final var root=this.root;
        int modCount=this.modCount;
        try
        {
          int rootOffset;
          uncheckedForEachAscending(root.arr,rootOffset=this.rootOffset,rootOffset+size,action::accept);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      else
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override
    public boolean removeIf(BooleanPredicate filter)
    {
      int size;
      if((size=this.size)!=0)
      {
        int newBound;
        final boolean[] arr;
        final CheckedList root;
        int rootOffset,rootBound,numRemoved;
        int modCount;
        if((numRemoved=(rootBound=(rootOffset=this.rootOffset)+size)-(newBound=doRemoveIf(arr=(root=this.root).arr,rootOffset,rootBound,filter,new CheckedCollection.AbstractModCountChecker(modCount=this.modCount){
          @Override
          protected int getActualModCount(){
            return root.modCount;
          }
        })))!=0)
        {
          root.modCount=++modCount;
          this.modCount=modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr.size-=numRemoved,curr=curr.parent){}
          int rootSize;
          ArrCopy.semicheckedSelfCopy(arr,rootBound,newBound,(rootSize=root.size)-rootBound);
          root.size=rootSize-numRemoved;
          this.size=size-numRemoved;
          return true;
        }
      }
      else
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Boolean> filter)
    {
      int size;
      if((size=this.size)!=0)
      {
        int newBound;
        final boolean[] arr;
        final CheckedList root;
        int rootOffset,rootBound,numRemoved;
        int modCount;
        if((numRemoved=(rootBound=(rootOffset=this.rootOffset)+size)-(newBound=doRemoveIf(arr=(root=this.root).arr,rootOffset,rootBound,filter::test,new CheckedCollection.AbstractModCountChecker(modCount=this.modCount){
          @Override
          protected int getActualModCount(){
            return root.modCount;
          }
        })))!=0)
        {
          root.modCount=++modCount;
          this.modCount=modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr.size-=numRemoved,curr=curr.parent){}
          int rootSize;
          ArrCopy.semicheckedSelfCopy(arr,rootBound,newBound,(rootSize=root.size)-rootBound);
          root.size=rootSize-numRemoved;
          this.size=size-numRemoved;
          return true;
        }
      }
      else
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      return false;
    }
    @Override
    public
    boolean
    contains(boolean val)
    {
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0)
      {
        return ((UncheckedList)root).sublistcontains(this.rootOffset,size,(val));
      }
      return false;
    }
    @Override
    public
    boolean
    contains(int val)
    {
      final var root=checkModCountAndGetRoot();
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
        return ((UncheckedList)root).sublistcontains(this.rootOffset,size,v);
      }
      return false;
    }
    @Override
    public
    boolean
    contains(long val)
    {
      final var root=checkModCountAndGetRoot();
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
          return ((UncheckedList)root).sublistcontains(this.rootOffset,size,v);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    contains(float val)
    {
      final var root=checkModCountAndGetRoot();
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
          return ((UncheckedList)root).sublistcontains(this.rootOffset,size,v);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    contains(double val)
    {
      final var root=checkModCountAndGetRoot();
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
          return ((UncheckedList)root).sublistcontains(this.rootOffset,size,v);
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
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Boolean)
        {
          return ((UncheckedList)root).sublistcontains(this.rootOffset,size,(boolean)(val));
        }
      }
      return false;
    }
    @Override
    public int hashCode()
    {
      final var root=checkModCountAndGetRoot();
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
    public boolean isEmpty()
    {
      CheckedCollection.checkModCount(this.modCount,root.modCount);
      return this.size==0;
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
    public int size()
    {
      CheckedCollection.checkModCount(this.modCount,root.modCount);
      return this.size;
    }
    @Override
    public <T> T[] toArray(T[] dst)
    {
      final var root=checkModCountAndGetRoot();
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
    public String toString()
    {
      final var root=checkModCountAndGetRoot();
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
    public boolean add(boolean val)
    {
      int rootSize;
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      this.modCount=modCount;
      if((rootSize=root.size)!=0)
      {
        for(var curr=parent;curr!=null;curr.modCount=modCount,++curr.size,curr=curr.parent){}
        ((BooleanArrSeq)root).uncheckedInsert(rootSize,this.rootOffset,val);
        ++this.size;
      }
      else
      {
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr.size=1,curr=curr.parent){}
        ((BooleanArrSeq)root).uncheckedInit(val);
        this.size=1;
      }
      return true;
    }
    private BooleanArrSeq checkModCountAndGetRoot()
    {
      final CheckedList root;
      CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
      return root;
    }
    @Override
    public void clear()
    {
      int modCount;
      final CheckedList root;
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
     private static class LstItr
       extends Itr implements OmniListIterator.OfBoolean
     {
       private LstItr(CheckedSubList parent)
       {
         super(parent);
       }
       private LstItr(CheckedSubList parent,int cursor)
       {
         super(parent);
       }
       @Override
       public boolean hasPrevious()
       {
         return this.cursor>parent.rootOffset;
       }
       @Override
       public int nextIndex()
       {
         return this.cursor-parent.rootOffset;
       }
       @Override
       public int previousIndex()
       {
         return this.cursor-parent.rootOffset-1;
       }
       @Override
       public boolean previousBoolean()
       {
             int cursor;
             final CheckedList root;
             final CheckedSubList parent;
             CheckedCollection.checkModCount(this.modCount,(root=(parent=this.parent).root).modCount);
             if((cursor=this.cursor)<=parent.rootOffset)
             {
                throw new NoSuchElementException();
             }
             this.cursor=--cursor;
             this.lastRet=cursor;
             return (boolean)root.arr[cursor];
       }
       @Override
       public void add(boolean val)
       {
         //TODO implement LstItr.add(boolean)
       }
       @Override
       public void set(boolean val)
       {
         //TODO implement LstItr.set(boolean)
       }
     }
    @Override
    public OmniList.OfBoolean subList(int fromIndex,int toIndex)
    {
      final int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
      int span;
      if((span=toIndex-fromIndex)<0|| fromIndex<0 ||  toIndex>this.size)
      {
        throw new InvalidReadIndexException("fromIndex="+fromIndex+"; toIndex="+toIndex+"; size="+this.size);
      }
      return new CheckedSubList(this,this.rootOffset+fromIndex,span,modCount);
    }
    @Override
    public OmniListIterator.OfBoolean listIterator()
    {
      CheckedCollection.checkModCount(this.modCount,root.modCount);
      return new LstItr(this);
    }
    @Override
    public OmniListIterator.OfBoolean listIterator(int index)
    {
      CheckedCollection.checkModCount(this.modCount,root.modCount);
      if(index<0 || size<index)
      {
        throw new InvalidWriteIndexException(index,size);
      }
      return new LstItr(this,index+this.rootOffset);
    }
    @Override
    public void sort(BooleanComparator sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        int modCount=this.modCount;
        final var root=this.root;
        try
        {
          if(sorter==null)
          {
            final int rootOffset;
            BooleanSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
          }
          else
          {
            final int rootOffset;
            BooleanSortUtil.uncheckedSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter);
          }
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        }
      }
      else
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override
    public void sort(Comparator<? super Boolean> sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        int modCount=this.modCount;
        final var root=this.root;
        try
        {
          if(sorter==null)
          {
            final int rootOffset;
            BooleanSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
          }
          else
          {
            final int rootOffset;
            BooleanSortUtil.uncheckedSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter::compare);
          }
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        }
      }
      else
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override
    public void stableAscendingSort()
    {
      int size;
      if((size=this.size)>1)
      {
        int modCount=this.modCount;
        final var root=this.root;
        try
        {
          final int rootOffset;
          BooleanSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        }
      }
      else
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override
    public void stableDescendingSort()
    {
      int size;
      if((size=this.size)>1)
      {
        int modCount=this.modCount;
        final var root=this.root;
        try
        {
          final int rootOffset;
          BooleanSortUtil.uncheckedDescendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        }
      }
      else
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override
    public void replaceAll(BooleanPredicate operator)
    {
      int size;
      if((size=this.size)!=0)
      {
        int modCount=this.modCount;
        final var root=this.root;
        try
        {
          final int rootOffset;
          ((UncheckedList)root).uncheckedReplaceAll(rootOffset=this.rootOffset,rootOffset+size,operator);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        }
      }
      else
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override
    public void replaceAll(UnaryOperator<Boolean> operator)
    {
      int size;
      if((size=this.size)!=0)
      {
        int modCount=this.modCount;
        final var root=this.root;
        try
        {
          final int rootOffset;
          ((UncheckedList)root).uncheckedReplaceAll(rootOffset=this.rootOffset,rootOffset+size,operator::apply);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        }
      }
      else
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override
    public void add(int index,boolean val)
    {
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      int size;
      if((size=this.size)<index || index<0)
      {
        throw new InvalidWriteIndexException(index,size);
      }
      root.modCount=++modCount;
      this.modCount=modCount;
      int rootSize;
      if((rootSize=root.size)!=0)
      {
        for(var curr=parent;curr!=null;curr.modCount=modCount,++curr.size,curr=curr.parent){}
        ((BooleanArrSeq)root).uncheckedInsert(rootSize,index+this.rootOffset,val);
        ++this.size;
      }
      else
      {
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr.size=1,curr=curr.parent){}
        ((BooleanArrSeq)root).uncheckedInit(val);
        this.size=1;
      }
    }
    @Override
    public boolean getBoolean(int index)
    {
      final var root=checkModCountAndGetRoot();
      if(index<0 || index>=size)
      {
        throw new InvalidReadIndexException(index,size);
      }
      return (boolean)root.arr[index+this.rootOffset];
    }
    @Override
    public void put(int index,boolean val)
    {
      final var root=checkModCountAndGetRoot();
      if(index<0 || index>=size)
      {
        throw new InvalidWriteIndexException(index,size);
      }
      root.arr[index+this.rootOffset]=val;
    }
    @Override
    public boolean set(int index,boolean val)
    {
      final var root=checkModCountAndGetRoot();
      if(index<0 || index>=size)
      {
        throw new InvalidReadIndexException(index,size);
      }
      final boolean[] arr;
      final boolean ret=(boolean)(arr=root.arr)[index+=this.rootOffset];
      arr[index]=val;
      return ret;
    }
    @Override
    public boolean removeBooleanAt(int index)
    {
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      int size;
      if((size=this.size)<=index || index<0)
      {
        throw new InvalidWriteIndexException(index,size);
      }
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,--curr.size,curr=curr.parent){}
      this.size=size-1;
      return ((BooleanArrSeq)root).uncheckedremoveAtIndex(index+this.rootOffset,root.size);
    }
    //TODO implement AND and OR in the template switches to avoid writing this twice
    @Override
    public
    int
    indexOf(boolean val)
    {
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0)
      {
        return ((UncheckedList)root).sublistindexOf(this.rootOffset,size,(val));
      }
      return -1;
    }
    @Override
    public
    int
    indexOf(int val)
    {
      final var root=checkModCountAndGetRoot();
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
        return ((UncheckedList)root).sublistindexOf(this.rootOffset,size,v);
      }
      return -1;
    }
    @Override
    public
    int
    indexOf(long val)
    {
      final var root=checkModCountAndGetRoot();
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
          return ((UncheckedList)root).sublistindexOf(this.rootOffset,size,v);
        }
      }
      return -1;
    }
    @Override
    public
    int
    indexOf(float val)
    {
      final var root=checkModCountAndGetRoot();
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
          return ((UncheckedList)root).sublistindexOf(this.rootOffset,size,v);
        }
      }
      return -1;
    }
    @Override
    public
    int
    indexOf(double val)
    {
      final var root=checkModCountAndGetRoot();
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
          return ((UncheckedList)root).sublistindexOf(this.rootOffset,size,v);
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
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Boolean)
        {
          return ((UncheckedList)root).sublistindexOf(this.rootOffset,size,(boolean)(val));
        }
      }
      return -1;
    }
    //TODO implement AND and OR in the template switches to avoid writing this twice
    @Override
    public
    int
    lastIndexOf(boolean val)
    {
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0)
      {
        return ((UncheckedList)root).sublistlastIndexOf(this.rootOffset,size,(val));
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf(int val)
    {
      final var root=checkModCountAndGetRoot();
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
        return ((UncheckedList)root).sublistlastIndexOf(this.rootOffset,size,v);
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf(long val)
    {
      final var root=checkModCountAndGetRoot();
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
          return ((UncheckedList)root).sublistlastIndexOf(this.rootOffset,size,v);
        }
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf(float val)
    {
      final var root=checkModCountAndGetRoot();
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
          return ((UncheckedList)root).sublistlastIndexOf(this.rootOffset,size,v);
        }
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf(double val)
    {
      final var root=checkModCountAndGetRoot();
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
          return ((UncheckedList)root).sublistlastIndexOf(this.rootOffset,size,v);
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
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Boolean)
        {
          return ((UncheckedList)root).sublistlastIndexOf(this.rootOffset,size,(boolean)(val));
        }
      }
      return -1;
    }
  }
  private static abstract class AbstractItr implements OmniIterator.OfBoolean
  {
    @Override
    public Boolean next()
    {
      return (Boolean)(nextBoolean());
    }
    @Override
    public double nextDouble()
    {
      return TypeUtil.castToDouble(nextBoolean());
    }
    @Override
    public float nextFloat()
    {
      return TypeUtil.castToFloat(nextBoolean());
    }
    @Override
    public long nextLong()
    {
      return TypeUtil.castToLong(nextBoolean());
    }
    @Override
    public int nextInt()
    {
      return (int)TypeUtil.castToByte(nextBoolean());
    }
    @Override
    public short nextShort()
    {
      return (short)TypeUtil.castToByte(nextBoolean());
    }
    @Override
    public char nextChar()
    {
      return TypeUtil.castToChar(nextBoolean());
    }
    @Override
    public byte nextByte()
    {
      return TypeUtil.castToByte(nextBoolean());
    }
  }
  private static abstract interface ListDefault extends OmniList.OfBoolean
  {
    @Override
    public default void add(int index,Boolean val)
    {
      add(index,(boolean)val);
    }
    @Override
    public default Boolean get(int index)
    {
      return getBoolean(index);
    }
    @Override
    public default Boolean set(int index,Boolean val)
    {
      return set(index,(boolean)val);
    }
    @Override
    public default  Boolean remove(int index)
    {
      return removeBooleanAt(index);
    }
  }
  private static abstract interface SubListDefault extends ListDefault
  {
    @Override
    public default boolean add(Boolean val)
    {
      return this.add((boolean)val);
    }
  }
}
