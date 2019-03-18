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
import omni.impl.AbstractBooleanItr;
public abstract class BooleanArrSeq implements OmniCollection.OfBoolean
{
  transient int size;
  transient boolean[] arr;
  private BooleanArrSeq()
  {
    super();
    this.arr=OmniArray.OfBoolean.DEFAULT_ARR;
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
  @Override
  public String toString()
  {
    int size;
    if((size=this.size)!=0)
    {
      if(size>=(Integer.MAX_VALUE/6))
      {
        throw new OutOfMemoryError();
      }
      final byte[] buffer;
      if(size<=(OmniArray.MAX_ARR_SIZE/7)){(buffer=new byte[size*7])
        [size=uncheckedToString(size,buffer)]=(byte)']';
        buffer[0]=(byte)'[';
        return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
      }else{
        final ToStringUtil.OmniStringBuilderByte builder;
        uncheckedToString(size,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
        builder.uncheckedAppendChar((byte)']');
        buffer=builder.buffer;
        buffer[0]=(byte)'[';
        return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
      }
    }
    return "[]";
  }
  abstract int uncheckedHashCode(int size);
  abstract int uncheckedToString(int size,byte[] buffer);
  abstract void uncheckedToString(int size,ToStringUtil.OmniStringBuilderByte builder);
  @Override
  public boolean contains(boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return OmniArray.OfBoolean.uncheckedcontains(this.arr,0,size-1,(val));
    }
    return false;
  }
  @Override
  public boolean contains(int val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        returnFalse:for(;;)
        {
          final boolean v;
          switch(val)
          {
          default:
            break returnFalse;
          case 0:
            v=false;
            break;
          case 1:
            v=true;
          }
          return OmniArray.OfBoolean.uncheckedcontains(this.arr,0,size-1,v);
        }
      }
    }
    return false;
  }
  @Override
  public boolean contains(long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      returnFalse:for(;;)
      {
        final boolean v;
        if(val==0L)
        {
          v=false;
        }else if(val==1L)
        {
          v=true;
        }
        else
        {
          break returnFalse;
        }
        return OmniArray.OfBoolean.uncheckedcontains(this.arr,0,size-1,v);
      }
    }
    return false;
  }
  @Override
  public boolean contains(float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      returnFalse:for(;;)
      {
        final boolean v;
        switch(Float.floatToRawIntBits(val))
        {
          default:
            break returnFalse;
          case 0:
          case Integer.MIN_VALUE:
            v=false;
            break;
          case TypeUtil.FLT_TRUE_BITS:
            v=true;
        }
        return OmniArray.OfBoolean.uncheckedcontains(this.arr,0,size-1,v);
      }
    }
    return false;
  }
  @Override
  public boolean contains(double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      returnFalse:for(;;)
      {
        final boolean v;
        long bits;
        if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE)
        {
          v=false;
        }
        else if(bits==TypeUtil.DBL_TRUE_BITS)
        {
          v=true;
        }
        else
        {
          break returnFalse;
        }
        return OmniArray.OfBoolean.uncheckedcontains(this.arr,0,size-1,v);
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
      if(val instanceof Boolean)
      {
        return OmniArray.OfBoolean.uncheckedcontains(this.arr,0,size-1,(boolean)(val));
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(int val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        returnFalse:for(;;)
        {
          final boolean v;
          switch(val)
          {
          default:
            break returnFalse;
          case 0:
            v=false;
            break;
          case 1:
            v=true;
          }
          return this.uncheckedremoveVal(size,v);
        }
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      returnFalse:for(;;)
      {
        final boolean v;
        if(val==0L)
        {
          v=false;
        }else if(val==1L)
        {
          v=true;
        }
        else
        {
          break returnFalse;
        }
        return this.uncheckedremoveVal(size,v);
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      returnFalse:for(;;)
      {
        final boolean v;
        switch(Float.floatToRawIntBits(val))
        {
          default:
            break returnFalse;
          case 0:
          case Integer.MIN_VALUE:
            v=false;
            break;
          case TypeUtil.FLT_TRUE_BITS:
            v=true;
        }
        return this.uncheckedremoveVal(size,v);
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      returnFalse:for(;;)
      {
        final boolean v;
        long bits;
        if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE)
        {
          v=false;
        }
        else if(bits==TypeUtil.DBL_TRUE_BITS)
        {
          v=true;
        }
        else
        {
          break returnFalse;
        }
        return this.uncheckedremoveVal(size,v);
      }
    }
    return false;
  }
  @Override
  public boolean remove(Object val)
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
  abstract boolean uncheckedremoveVal(int size,boolean val);
  abstract void uncheckedForEach(int size,BooleanConsumer action);
  @Override
  public void forEach(BooleanConsumer action)
  {
    final int size;
    if((size=this.size)!=0)
    {
      uncheckedForEach(size,action);
    }
  }
  @Override
  public void forEach(Consumer<? super Boolean> action)
  {
    final int size;
    if((size=this.size)!=0)
    {
      uncheckedForEach(size,action::accept);
    }
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
  private void uncheckedInit(boolean val)
  {
    boolean[] arr;
    if((arr=this.arr)==null)
    {
      this.arr=new boolean[]{val};
    }
    else
    {
      if(arr==OmniArray.OfBoolean.DEFAULT_ARR)
      {
        this.arr=arr=new boolean[OmniArray.DEFAULT_ARR_SEQ_CAP];
      }
      arr[0]=val;
    }
    this.size=1;
  }
  private void uncheckedInsert(int index,int size,boolean val)
  {
    final int tailDist;
    if((tailDist=size-index)==0)
    {
      uncheckedAppend(size,val);
    }
    else
    {
      boolean[] arr;
      if((arr=this.arr).length==size)
      {
        final boolean[] tmp;
        ArrCopy.semicheckedCopy(arr,0,tmp=new boolean[OmniArray.growBy50Pct(size)],0,index);
        ArrCopy.uncheckedCopy(arr,index,tmp,index+1,tailDist);
        this.arr=arr=tmp;
      }
      else
      {
        ArrCopy.uncheckedCopy(arr,index,arr,index+1,tailDist);
      }
      arr[index]=val;
      this.size=size+1;
    }
  }
  public void push(boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      uncheckedAppend(size,val);
    }
    else
    {
      uncheckedInit(val);
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
  abstract void uncheckedCopyInto(boolean[] dst,int length);
  @Override
  public <T> T[] toArray(T[] arr)
  {
    final int size;
    if((size=this.size)!=0)
    {
      uncheckedCopyInto(arr=OmniArray.uncheckedArrResize(size,arr),size);
    }
    else if(arr.length!=0)
    {
      arr[0]=null;
    }
    return arr;
  }
  @Override
  public <T> T[] toArray(IntFunction<T[]> arrConstructor)
  {
    final int size;
    T[] dst=arrConstructor.apply(size=this.size);
    if(size!=0)
    {
      uncheckedCopyInto(dst,size);
    }
    return dst;
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
  abstract void uncheckedCopyInto(Object[] dst,int length);
  abstract void uncheckedCopyInto(Boolean[] dst,int length);
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
  abstract void uncheckedCopyInto(double[] dst,int length);
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
  abstract void uncheckedCopyInto(float[] dst,int length);
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
  abstract void uncheckedCopyInto(long[] dst,int length);
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
  abstract void uncheckedCopyInto(int[] dst,int length);
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
  abstract void uncheckedCopyInto(short[] dst,int length);
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
  abstract void uncheckedCopyInto(byte[] dst,int length);
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
  abstract void uncheckedCopyInto(char[] dst,int length);
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
  abstract boolean uncheckedRemoveIf(int size,BooleanPredicate filter);
  @Override
  public boolean removeIf(BooleanPredicate filter)
  {
    final int size;
    return (size=this.size)!=0 && uncheckedRemoveIf(size,filter);
  }
  @Override
  public boolean removeIf(Predicate<? super Boolean> filter)
  {
    final int size;
    return (size=this.size)!=0 && uncheckedRemoveIf(size,filter::test);
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
    return srcBound-dstOffset;
  }
  private static int uncheckedRemoveIfImpl(boolean[] arr,int srcOffset,int srcBound,BooleanPredicate filter)
  {
    boolean v;
    if(filter.test(v=arr[srcOffset]))
    {
      final int dstOffset=srcOffset;
      while(++srcOffset!=srcBound)
      {
        if(arr[srcOffset]^v)
        {
          if(filter.test(v=!v))
          {
            break;
          }
          arr[dstOffset]=v;
          return pullSurvivorsDown(arr,srcOffset,srcBound,dstOffset+1,v);
        }
      }
      return srcBound-dstOffset;
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
      return 0;
    }
  }
  private static int uncheckedRemoveIfImpl(boolean[] arr,int srcOffset,int srcBound,BooleanPredicate filter,CheckedCollection.AbstractModCountChecker modCountChecker)
  {
    boolean v;
    if(filter.test(v=arr[srcOffset]))
    {
      final int dstOffset=srcOffset;
      while(++srcOffset!=srcBound)
      {
        if(arr[srcOffset]^v)
        {
          if(filter.test(v=!v))
          {
            break;
          }
          modCountChecker.checkModCount();
          arr[dstOffset]=v;
          return pullSurvivorsDown(arr,srcOffset,srcBound,dstOffset+1,v);
        }
      }
      modCountChecker.checkModCount();
      return srcBound-dstOffset;
    }
    else
    {
      while(++srcOffset!=srcBound)
      {
        if(arr[srcOffset]^v)
        {
          if(filter.test(!v))
          {
            modCountChecker.checkModCount();
            return pullSurvivorsDown(arr,srcOffset,srcBound,srcOffset,v);
          }
          break;
        }
      }
      return 0;
    }
  }
  public
    static class UncheckedStack
      extends BooleanArrSeq
      implements OmniStack.OfBoolean,Cloneable
  {
    public UncheckedStack()
    {
      super();
    }
    public UncheckedStack(int initialCapacity)
    {
      super(initialCapacity);
    }
    UncheckedStack(int size,boolean[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implements equals method for UncheckedStack
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
    int uncheckedToString(int size,byte[] buffer)
    {
      return OmniArray.OfBoolean.descendingToString(this.arr,0,size-1,buffer,1);
    }
    void uncheckedToString(int size,ToStringUtil.OmniStringBuilderByte builder)
    {
      OmniArray.OfBoolean.descendingToString(this.arr,0,size-1,builder);
    }
    @Override
    int uncheckedHashCode(int size)
    {
      return OmniArray.OfBoolean.descendingSeqHashCode(this.arr,0,size-1);
    }
    @Override
    public int search(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfBoolean.uncheckedsearch(this.arr,size,(val));
      }
      return -1;
    }
    @Override
    public int search(int val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          returnFalse:for(;;)
          {
            final boolean v;
            switch(val)
            {
            default:
              break returnFalse;
            case 0:
              v=false;
              break;
            case 1:
              v=true;
            }
            return OmniArray.OfBoolean.uncheckedsearch(this.arr,size,v);
          }
        }
      }
      return -1;
    }
    @Override
    public int search(long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        returnFalse:for(;;)
        {
          final boolean v;
          if(val==0L)
          {
            v=false;
          }else if(val==1L)
          {
            v=true;
          }
          else
          {
            break returnFalse;
          }
          return OmniArray.OfBoolean.uncheckedsearch(this.arr,size,v);
        }
      }
      return -1;
    }
    @Override
    public int search(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        returnFalse:for(;;)
        {
          final boolean v;
          switch(Float.floatToRawIntBits(val))
          {
            default:
              break returnFalse;
            case 0:
            case Integer.MIN_VALUE:
              v=false;
              break;
            case TypeUtil.FLT_TRUE_BITS:
              v=true;
          }
          return OmniArray.OfBoolean.uncheckedsearch(this.arr,size,v);
        }
      }
      return -1;
    }
    @Override
    public int search(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        returnFalse:for(;;)
        {
          final boolean v;
          long bits;
          if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE)
          {
            v=false;
          }
          else if(bits==TypeUtil.DBL_TRUE_BITS)
          {
            v=true;
          }
          else
          {
            break returnFalse;
          }
          return OmniArray.OfBoolean.uncheckedsearch(this.arr,size,v);
        }
      }
      return -1;
    }
    @Override
    public int search(Object val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Boolean)
        {
          return OmniArray.OfBoolean.uncheckedsearch(this.arr,size,(boolean)(val));
        }
      }
      return -1;
    }
    @Override
    boolean uncheckedremoveVal (int size
    ,boolean val
    )
    {
      final var arr=this.arr;
      for(int index=--size;;--index)
      {
        if(
        val==arr[index]
        )
        {
          OmniArray.OfBoolean.removeIndexAndPullDown(arr,index,size);
          this.size=size;
          return true;
        }
        if(index==0)
        {
          return false;
        }
      }
    }
    @Override
    void uncheckedForEach(int size,BooleanConsumer action)
    {
      {
        OmniArray.OfBoolean.descendingForEach(this.arr,0,size-1,action);
      }
    }
    private static class Itr
      extends AbstractBooleanItr
    {
      transient final UncheckedStack parent;
      transient int cursor;
      private Itr(UncheckedStack parent)
      {
        this.parent=parent;
        this.cursor=parent.size-1;
      }
      private Itr(UncheckedStack parent,int cursor)
      {
        this.parent=parent;
        this.cursor=cursor;
      }
      @Override
      public boolean hasNext()
      {
        return this.cursor>=0;
      }
      @Override
      public boolean nextBoolean()
      {
        return (boolean)parent.arr[cursor--];
      }
      @Override
      public void remove()
      {
        final UncheckedStack root;
        OmniArray.OfBoolean.removeIndexAndPullDown((root=this.parent).arr,this.cursor,--root.size);
      }
      @Override
      public void forEachRemaining(BooleanConsumer action)
      {
        final int cursor;
        if((cursor=this.cursor)>=0)
        {
          OmniArray.OfBoolean.descendingForEach(parent.arr,0,cursor,action);
          this.cursor=-1;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Boolean> action)
      {
        final int cursor;
        if((cursor=this.cursor)>=0)
        {
          OmniArray.OfBoolean.descendingForEach(parent.arr,0,cursor,action::accept);
          this.cursor=-1;
        }
      }
    }
    @Override
    public OmniIterator.OfBoolean iterator()
    {
      return new Itr(this);
    }
    @Override
    public void push(Boolean val)
    {
      push((boolean)val);
    }
    @Override
    void uncheckedCopyInto(boolean[] dst,int length)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(Object[] dst,int length)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(Boolean[] dst,int length)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(double[] dst,int length)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(float[] dst,int length)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(long[] dst,int length)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(int[] dst,int length)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(short[] dst,int length)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(byte[] dst,int length)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(char[] dst,int length)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override
    public Boolean pop()
    {
      return popBoolean();
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
        final var ret=(boolean)(arr[--size]);
        this.size=size;
        return ret;
      }
      return false;
    }
    @Override
    public boolean peekBoolean()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (boolean)(arr[size-1]);
      }
      return false;
    }
    @Override
    public Boolean poll()
    {
      int size;
      if((size=this.size)!=0)
      {
        final var ret=(Boolean)(arr[--size]);
        this.size=size;
        return ret;
      }
      return null;
    }
    @Override
    public Boolean peek()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (Boolean)(arr[size-1]);
      }
      return null;
    }
    @Override
    public double pollDouble()
    {
      int size;
      if((size=this.size)!=0)
      {
        final var ret=TypeUtil.castToDouble(arr[--size]);
        this.size=size;
        return ret;
      }
      return Double.NaN;
    }
    @Override
    public double peekDouble()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return TypeUtil.castToDouble(arr[size-1]);
      }
      return Double.NaN;
    }
    @Override
    public float pollFloat()
    {
      int size;
      if((size=this.size)!=0)
      {
        final var ret=TypeUtil.castToFloat(arr[--size]);
        this.size=size;
        return ret;
      }
      return Float.NaN;
    }
    @Override
    public float peekFloat()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return TypeUtil.castToFloat(arr[size-1]);
      }
      return Float.NaN;
    }
    @Override
    public long pollLong()
    {
      int size;
      if((size=this.size)!=0)
      {
        final var ret=TypeUtil.castToLong(arr[--size]);
        this.size=size;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override
    public long peekLong()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return TypeUtil.castToLong(arr[size-1]);
      }
      return Long.MIN_VALUE;
    }
    @Override
    public int pollInt()
    {
      int size;
      if((size=this.size)!=0)
      {
        final var ret=(int)TypeUtil.castToByte(arr[--size]);
        this.size=size;
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override
    public int peekInt()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (int)TypeUtil.castToByte(arr[size-1]);
      }
      return Integer.MIN_VALUE;
    }
    @Override
    public short pollShort()
    {
      int size;
      if((size=this.size)!=0)
      {
        final var ret=(short)TypeUtil.castToByte(arr[--size]);
        this.size=size;
        return ret;
      }
      return Short.MIN_VALUE;
    }
    @Override
    public short peekShort()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (short)TypeUtil.castToByte(arr[size-1]);
      }
      return Short.MIN_VALUE;
    }
    @Override
    public byte pollByte()
    {
      int size;
      if((size=this.size)!=0)
      {
        final var ret=TypeUtil.castToByte(arr[--size]);
        this.size=size;
        return ret;
      }
      return Byte.MIN_VALUE;
    }
    @Override
    public byte peekByte()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return TypeUtil.castToByte(arr[size-1]);
      }
      return Byte.MIN_VALUE;
    }
    @Override
    public char pollChar()
    {
      int size;
      if((size=this.size)!=0)
      {
        final var ret=TypeUtil.castToChar(arr[--size]);
        this.size=size;
        return ret;
      }
      return Character.MIN_VALUE;
    }
    @Override
    public char peekChar()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return TypeUtil.castToChar(arr[size-1]);
      }
      return Character.MIN_VALUE;
    }
    @Override
    boolean uncheckedRemoveIf(int size,BooleanPredicate filter)
    {
      if(size!=(size-=uncheckedRemoveIfImpl(this.arr,0,size,filter))){
        this.size=size;
        return true;
      }
      return false;
    }
  }
  public
    static class UncheckedList
      extends BooleanArrSeq
      implements BooleanListDefault,Cloneable
  {
    public UncheckedList()
    {
      super();
    }
    public UncheckedList(int initialCapacity)
    {
      super(initialCapacity);
    }
    UncheckedList(int size,boolean[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implements equals method for UncheckedList
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
    int uncheckedToString(int size,byte[] buffer)
    {
      return OmniArray.OfBoolean.ascendingToString(this.arr,0,size-1,buffer,1);
    }
    void uncheckedToString(int size,ToStringUtil.OmniStringBuilderByte builder)
    {
      OmniArray.OfBoolean.ascendingToString(this.arr,0,size-1,builder);
    }
    @Override
    int uncheckedHashCode(int size)
    {
      return OmniArray.OfBoolean.ascendingSeqHashCode(this.arr,0,size-1);
    }
    @Override
    public int indexOf(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfBoolean.uncheckedindexOf(this.arr,size,(val));
      }
      return -1;
    }
    @Override
    public int indexOf(int val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          returnFalse:for(;;)
          {
            final boolean v;
            switch(val)
            {
            default:
              break returnFalse;
            case 0:
              v=false;
              break;
            case 1:
              v=true;
            }
            return OmniArray.OfBoolean.uncheckedindexOf(this.arr,size,v);
          }
        }
      }
      return -1;
    }
    @Override
    public int indexOf(long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        returnFalse:for(;;)
        {
          final boolean v;
          if(val==0L)
          {
            v=false;
          }else if(val==1L)
          {
            v=true;
          }
          else
          {
            break returnFalse;
          }
          return OmniArray.OfBoolean.uncheckedindexOf(this.arr,size,v);
        }
      }
      return -1;
    }
    @Override
    public int indexOf(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        returnFalse:for(;;)
        {
          final boolean v;
          switch(Float.floatToRawIntBits(val))
          {
            default:
              break returnFalse;
            case 0:
            case Integer.MIN_VALUE:
              v=false;
              break;
            case TypeUtil.FLT_TRUE_BITS:
              v=true;
          }
          return OmniArray.OfBoolean.uncheckedindexOf(this.arr,size,v);
        }
      }
      return -1;
    }
    @Override
    public int indexOf(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        returnFalse:for(;;)
        {
          final boolean v;
          long bits;
          if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE)
          {
            v=false;
          }
          else if(bits==TypeUtil.DBL_TRUE_BITS)
          {
            v=true;
          }
          else
          {
            break returnFalse;
          }
          return OmniArray.OfBoolean.uncheckedindexOf(this.arr,size,v);
        }
      }
      return -1;
    }
    @Override
    public int indexOf(Object val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Boolean)
        {
          return OmniArray.OfBoolean.uncheckedindexOf(this.arr,size,(boolean)(val));
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfBoolean.uncheckedlastIndexOf(this.arr,size,(val));
      }
      return -1;
    }
    @Override
    public int lastIndexOf(int val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          returnFalse:for(;;)
          {
            final boolean v;
            switch(val)
            {
            default:
              break returnFalse;
            case 0:
              v=false;
              break;
            case 1:
              v=true;
            }
            return OmniArray.OfBoolean.uncheckedlastIndexOf(this.arr,size,v);
          }
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        returnFalse:for(;;)
        {
          final boolean v;
          if(val==0L)
          {
            v=false;
          }else if(val==1L)
          {
            v=true;
          }
          else
          {
            break returnFalse;
          }
          return OmniArray.OfBoolean.uncheckedlastIndexOf(this.arr,size,v);
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        returnFalse:for(;;)
        {
          final boolean v;
          switch(Float.floatToRawIntBits(val))
          {
            default:
              break returnFalse;
            case 0:
            case Integer.MIN_VALUE:
              v=false;
              break;
            case TypeUtil.FLT_TRUE_BITS:
              v=true;
          }
          return OmniArray.OfBoolean.uncheckedlastIndexOf(this.arr,size,v);
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        returnFalse:for(;;)
        {
          final boolean v;
          long bits;
          if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE)
          {
            v=false;
          }
          else if(bits==TypeUtil.DBL_TRUE_BITS)
          {
            v=true;
          }
          else
          {
            break returnFalse;
          }
          return OmniArray.OfBoolean.uncheckedlastIndexOf(this.arr,size,v);
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
          return OmniArray.OfBoolean.uncheckedlastIndexOf(this.arr,size,(boolean)(val));
        }
      }
      return -1;
    }
    @Override
    boolean uncheckedremoveVal (int size
    ,boolean val
    )
    {
      final var arr=this.arr;
      for(int index=0;;)
      {
        if(
        val==arr[index]
        )
        {
          OmniArray.OfBoolean.removeIndexAndPullDown(arr,index,--size);
          this.size=size;
          return true;
        }
        if(++index==size)
        {
          return false;
        }
      }
    }
    @Override
    void uncheckedForEach(int size,BooleanConsumer action)
    {
      {
        OmniArray.OfBoolean.ascendingForEach(this.arr,0,size-1,action);
      }
    }
    private static class Itr
      extends AbstractBooleanItr
    {
      transient final UncheckedList parent;
      transient int cursor;
      private Itr(UncheckedList parent)
      {
        this.parent=parent;
        this.cursor=0;
      }
      private Itr(UncheckedList parent,int cursor)
      {
        this.parent=parent;
        this.cursor=cursor;
      }
      @Override
      public boolean hasNext()
      {
        return this.cursor<parent.size;
      }
      @Override
      public boolean nextBoolean()
      {
        return (boolean)parent.arr[cursor++];
      }
      @Override
      public void remove()
      {
        final UncheckedList root;
        OmniArray.OfBoolean.removeIndexAndPullDown((root=this.parent).arr,--this.cursor,--root.size);
      }
      @Override
      public void forEachRemaining(BooleanConsumer action)
      {
        final int cursor,bound;
        final UncheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size))
        {
          OmniArray.OfBoolean.ascendingForEach(parent.arr,cursor,bound-1,action);
          this.cursor=bound;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Boolean> action)
      {
        final int cursor,bound;
        final UncheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size))
        {
          OmniArray.OfBoolean.ascendingForEach(parent.arr,cursor,bound-1,action::accept);
          this.cursor=bound;
        }
      }
    }
    @Override
    public OmniIterator.OfBoolean iterator()
    {
      return new Itr(this);
    }
    private static class ListItr extends Itr implements OmniListIterator.OfBoolean
    {
      transient int lastRet;
      private ListItr(UncheckedList parent)
      {
        super(parent);
        this.lastRet=-1;
      }
      private ListItr(UncheckedList parent,int cursor)
      {
        super(parent,cursor);
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
      public boolean nextBoolean()
      {
        int lastRet;
        this.lastRet=lastRet=this.cursor++;
        return (boolean)parent.arr[lastRet];
      }
      @Override
      public void remove()
      {
        final UncheckedList root;
        final int lastRet;
        OmniArray.OfBoolean.removeIndexAndPullDown((root=this.parent).arr,lastRet=this.lastRet,--root.size);
        this.cursor=lastRet;
      }
      @Override
      public void forEachRemaining(BooleanConsumer action)
      {
        int cursor;
        final int bound;
        final UncheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size))
        {
          OmniArray.OfBoolean.ascendingForEach(parent.arr,cursor,cursor=bound-1,action);
          this.lastRet=cursor;
          this.cursor=bound;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Boolean> action)
      {
        final int cursor,bound;
        final UncheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size))
        {
          OmniArray.OfBoolean.ascendingForEach(parent.arr,cursor,bound-1,action::accept);
          this.cursor=bound;
        }
      }
      @Override
      public boolean previousBoolean()
      {
        final int lastRet;
        this.lastRet=lastRet=--this.cursor;
        return (boolean)parent.arr[lastRet];
      }
      @Override
      public void set(boolean val)
      {
        parent.arr[this.lastRet]=val;
      }
      @Override
      public void add(boolean val)
      {
        final UncheckedList root;
        final int rootSize;
        if((rootSize=(root=this.parent).size)!=0)
        {
          ((BooleanArrSeq)root).uncheckedInsert(this.cursor++,rootSize,val);
        }
        else
        {
          ((BooleanArrSeq)root).uncheckedInit(val);
          ++this.cursor;
        }
      }
    }
    @Override
    public OmniListIterator.OfBoolean listIterator()
    {
      return new ListItr(this);
    }
    @Override
    public OmniListIterator.OfBoolean listIterator(int index)
    {
      return new ListItr(this,index);
    }
    @Override
    public void add(int index,boolean val)
    {
      final int size;
      if((size=this.size)!=0){
        ((BooleanArrSeq)this).uncheckedInsert(index,size,val);
      }else{
        ((BooleanArrSeq)this).uncheckedInit(val);
      }
    }
    @Override
    void uncheckedCopyInto(boolean[] dst,int length)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(Object[] dst,int length)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(Boolean[] dst,int length)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(double[] dst,int length)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(float[] dst,int length)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(long[] dst,int length)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(int[] dst,int length)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(short[] dst,int length)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(byte[] dst,int length)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(char[] dst,int length)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override
    public boolean getBoolean(int index)
    {
      return (boolean)this.arr[index];
    }
    @Override
    public void put(int index,boolean val)
    {
      this.arr[index]=val;
    }
    @Override
    public boolean set(int index,boolean val)
    {
      final boolean[] arr;
      final var ret=(boolean)(arr=this.arr)[index];
      arr[index]=val;
      return ret;
    }
    @Override
    public boolean removeBooleanAt(int index)
    {
      final boolean[] arr;
      final var ret=(boolean)(arr=this.arr)[index];
      OmniArray.OfBoolean.removeIndexAndPullDown(arr,index,--size);
      return ret;
    }
    @Override
    boolean uncheckedRemoveIf(int size,BooleanPredicate filter)
    {
      if(size!=(size-=uncheckedRemoveIfImpl(this.arr,0,size,filter))){
        this.size=size;
        return true;
      }
      return false;
    }
    @Override
    public void replaceAll(BooleanPredicate operator)
    {
      final int size;
      if((size=this.size)!=0)
      {
        OmniArray.OfBoolean.uncheckedReplaceAll(this.arr,0,size,operator);
      }
    }
    @Override
    public void sort(BooleanComparator sorter)
    {
      final int size;
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
    public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        BooleanSortUtil.uncheckedAscendingSort(this.arr,0,size);
      }
    }
    @Override
    public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        BooleanSortUtil.uncheckedDescendingSort(this.arr,0,size);
      }
    }
    @Override
    public void replaceAll(UnaryOperator<Boolean> operator)
    {
      final int size;
      if((size=this.size)!=0)
      {
        OmniArray.OfBoolean.uncheckedReplaceAll(this.arr,0,size,operator::apply);
      }
    }
    @Override
    public void sort(Comparator<? super Boolean> sorter)
    {
      final int size;
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
    public OmniList.OfBoolean subList(int fromIndex,int toIndex)
    {
      return new UncheckedSubList(this,fromIndex,toIndex-fromIndex);
    }
  }
  public
    static class UncheckedSubList
      implements BooleanSubListDefault,Cloneable
  {
    transient final int rootOffset;
    transient int size;
    transient final UncheckedList root;
    transient final UncheckedSubList parent;
    private UncheckedSubList(UncheckedList root,int rootOffset,int size)
    {
      super();
      this.root=root;
      this.parent=null;
      this.rootOffset=rootOffset;
      this.size=size;
    }
    private UncheckedSubList(UncheckedSubList parent,int rootOffset,int size)
    {
      super();
      this.root=parent.root;
      this.parent=parent;
      this.rootOffset=rootOffset;
      this.size=size;
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implements equals method for UncheckedSubList
      return false;
    }
    @Override
    public Object clone()
    {
      final boolean[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(root.arr,rootOffset,copy=new boolean[size],0,size);
      }
      else
      {
        copy=OmniArray.OfBoolean.DEFAULT_ARR;
      }
      return new UncheckedList(size,copy);
    }
    @Override
    public String toString()
    {
      int size;
      if((size=this.size)!=0)
      {
        if(size>=(Integer.MAX_VALUE/6))
        {
          throw new OutOfMemoryError();
        }
          final int rootOffset;
          final byte[] buffer;
          if(size<=(OmniArray.MAX_ARR_SIZE/7)){(buffer=new byte[size*7])
            [size=OmniArray.OfBoolean.ascendingToString(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,buffer,1)]=(byte)']';
            buffer[0]=(byte)'[';
            return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
          }else{
            final ToStringUtil.OmniStringBuilderByte builder;
            OmniArray.OfBoolean.ascendingToString(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
            builder.uncheckedAppendChar((byte)']');
            (buffer=builder.buffer)[0]=(byte)'[';
            return new String(buffer,0,size=builder.size,ToStringUtil.IOS8859CharSet);
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
        final int rootOffset;
        return OmniArray.OfBoolean.ascendingSeqHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
    @Override
    public void clear()
    {
      final int size;
      if((size=this.size)!=0)
      {
        for(var curr=parent;curr!=null;curr.size-=size,curr=curr.parent){}
        final UncheckedList root;
        (root=this.root).size=OmniArray.OfBoolean.removeRangeAndPullDown(root.arr,this.rootOffset+size,root.size,size);
        this.size=0;
      }
    }
    @Override
    public boolean contains(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final int rootOffset;
        return OmniArray.OfBoolean.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(val));
      }
      return false;
    }
    @Override
    public boolean contains(int val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          returnFalse:for(;;)
          {
            final boolean v;
            switch(val)
            {
            default:
              break returnFalse;
            case 0:
              v=false;
              break;
            case 1:
              v=true;
            }
            final int rootOffset;
            return OmniArray.OfBoolean.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,v);
          }
        }
      }
      return false;
    }
    @Override
    public boolean contains(long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        returnFalse:for(;;)
        {
          final boolean v;
          if(val==0L)
          {
            v=false;
          }else if(val==1L)
          {
            v=true;
          }
          else
          {
            break returnFalse;
          }
          final int rootOffset;
          return OmniArray.OfBoolean.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,v);
        }
      }
      return false;
    }
    @Override
    public boolean contains(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        returnFalse:for(;;)
        {
          final boolean v;
          switch(Float.floatToRawIntBits(val))
          {
            default:
              break returnFalse;
            case 0:
            case Integer.MIN_VALUE:
              v=false;
              break;
            case TypeUtil.FLT_TRUE_BITS:
              v=true;
          }
          final int rootOffset;
          return OmniArray.OfBoolean.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,v);
        }
      }
      return false;
    }
    @Override
    public boolean contains(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        returnFalse:for(;;)
        {
          final boolean v;
          long bits;
          if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE)
          {
            v=false;
          }
          else if(bits==TypeUtil.DBL_TRUE_BITS)
          {
            v=true;
          }
          else
          {
            break returnFalse;
          }
          final int rootOffset;
          return OmniArray.OfBoolean.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,v);
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
        if(val instanceof Boolean)
        {
          final int rootOffset;
          return OmniArray.OfBoolean.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(boolean)(val));
        }
      }
      return false;
    }
    @Override
    public boolean removeVal(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return this.uncheckedremoveVal(size,(val));
      }
      return false;
    }
    @Override
    public boolean removeVal(int val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          returnFalse:for(;;)
          {
            final boolean v;
            switch(val)
            {
            default:
              break returnFalse;
            case 0:
              v=false;
              break;
            case 1:
              v=true;
            }
            return this.uncheckedremoveVal(size,v);
          }
        }
      }
      return false;
    }
    @Override
    public boolean removeVal(long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        returnFalse:for(;;)
        {
          final boolean v;
          if(val==0L)
          {
            v=false;
          }else if(val==1L)
          {
            v=true;
          }
          else
          {
            break returnFalse;
          }
          return this.uncheckedremoveVal(size,v);
        }
      }
      return false;
    }
    @Override
    public boolean removeVal(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        returnFalse:for(;;)
        {
          final boolean v;
          switch(Float.floatToRawIntBits(val))
          {
            default:
              break returnFalse;
            case 0:
            case Integer.MIN_VALUE:
              v=false;
              break;
            case TypeUtil.FLT_TRUE_BITS:
              v=true;
          }
          return this.uncheckedremoveVal(size,v);
        }
      }
      return false;
    }
    @Override
    public boolean removeVal(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        returnFalse:for(;;)
        {
          final boolean v;
          long bits;
          if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE)
          {
            v=false;
          }
          else if(bits==TypeUtil.DBL_TRUE_BITS)
          {
            v=true;
          }
          else
          {
            break returnFalse;
          }
          return this.uncheckedremoveVal(size,v);
        }
      }
      return false;
    }
    @Override
    public boolean remove(Object val)
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
    public int indexOf(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfBoolean.uncheckedindexOf(root.arr,this.rootOffset,size,(val));
      }
      return -1;
    }
    @Override
    public int indexOf(int val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          returnFalse:for(;;)
          {
            final boolean v;
            switch(val)
            {
            default:
              break returnFalse;
            case 0:
              v=false;
              break;
            case 1:
              v=true;
            }
            return OmniArray.OfBoolean.uncheckedindexOf(root.arr,this.rootOffset,size,v);
          }
        }
      }
      return -1;
    }
    @Override
    public int indexOf(long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        returnFalse:for(;;)
        {
          final boolean v;
          if(val==0L)
          {
            v=false;
          }else if(val==1L)
          {
            v=true;
          }
          else
          {
            break returnFalse;
          }
          return OmniArray.OfBoolean.uncheckedindexOf(root.arr,this.rootOffset,size,v);
        }
      }
      return -1;
    }
    @Override
    public int indexOf(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        returnFalse:for(;;)
        {
          final boolean v;
          switch(Float.floatToRawIntBits(val))
          {
            default:
              break returnFalse;
            case 0:
            case Integer.MIN_VALUE:
              v=false;
              break;
            case TypeUtil.FLT_TRUE_BITS:
              v=true;
          }
          return OmniArray.OfBoolean.uncheckedindexOf(root.arr,this.rootOffset,size,v);
        }
      }
      return -1;
    }
    @Override
    public int indexOf(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        returnFalse:for(;;)
        {
          final boolean v;
          long bits;
          if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE)
          {
            v=false;
          }
          else if(bits==TypeUtil.DBL_TRUE_BITS)
          {
            v=true;
          }
          else
          {
            break returnFalse;
          }
          return OmniArray.OfBoolean.uncheckedindexOf(root.arr,this.rootOffset,size,v);
        }
      }
      return -1;
    }
    @Override
    public int indexOf(Object val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Boolean)
        {
          return OmniArray.OfBoolean.uncheckedindexOf(root.arr,this.rootOffset,size,(boolean)(val));
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfBoolean.uncheckedlastIndexOf(root.arr,this.rootOffset,size,(val));
      }
      return -1;
    }
    @Override
    public int lastIndexOf(int val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          returnFalse:for(;;)
          {
            final boolean v;
            switch(val)
            {
            default:
              break returnFalse;
            case 0:
              v=false;
              break;
            case 1:
              v=true;
            }
            return OmniArray.OfBoolean.uncheckedlastIndexOf(root.arr,this.rootOffset,size,v);
          }
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        returnFalse:for(;;)
        {
          final boolean v;
          if(val==0L)
          {
            v=false;
          }else if(val==1L)
          {
            v=true;
          }
          else
          {
            break returnFalse;
          }
          return OmniArray.OfBoolean.uncheckedlastIndexOf(root.arr,this.rootOffset,size,v);
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        returnFalse:for(;;)
        {
          final boolean v;
          switch(Float.floatToRawIntBits(val))
          {
            default:
              break returnFalse;
            case 0:
            case Integer.MIN_VALUE:
              v=false;
              break;
            case TypeUtil.FLT_TRUE_BITS:
              v=true;
          }
          return OmniArray.OfBoolean.uncheckedlastIndexOf(root.arr,this.rootOffset,size,v);
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        returnFalse:for(;;)
        {
          final boolean v;
          long bits;
          if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE)
          {
            v=false;
          }
          else if(bits==TypeUtil.DBL_TRUE_BITS)
          {
            v=true;
          }
          else
          {
            break returnFalse;
          }
          return OmniArray.OfBoolean.uncheckedlastIndexOf(root.arr,this.rootOffset,size,v);
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
          return OmniArray.OfBoolean.uncheckedlastIndexOf(root.arr,this.rootOffset,size,(boolean)(val));
        }
      }
      return -1;
    }
    private boolean uncheckedremoveVal (int size
    ,boolean val
    )
    {
      final UncheckedList root;
      final var arr=(root=this.root).arr;
      for(int index=this.rootOffset,bound=index+(--size);;++index)
      {
        if(
        val==arr[index]
        )
        {
          for(var curr=parent;curr!=null;--curr.size,curr=curr.parent){}
          OmniArray.OfBoolean.removeIndexAndPullDown(arr,index,--root.size);
          this.size=size;
          return true;
        }
        if(index==bound)
        {
          return false;
        }
      }
    }
    @Override
    public void forEach(BooleanConsumer action)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          OmniArray.OfBoolean.ascendingForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,action);
        }
      }
    }
    @Override
    public void forEach(Consumer<? super Boolean> action)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          OmniArray.OfBoolean.ascendingForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,action::accept);
        }
      }
    }
    private static class Itr
      extends AbstractBooleanItr
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
        this.cursor=cursor;
      }
      @Override
      public boolean hasNext()
      {
        final UncheckedSubList parent;
        return this.cursor<(parent=this.parent).rootOffset+parent.size;
      }
      @Override
      public boolean nextBoolean()
      {
        return (boolean)parent.root.arr[cursor++];
      }
      @Override
      public void remove()
      {
        UncheckedSubList parent;
        final UncheckedList root;
        OmniArray.OfBoolean.removeIndexAndPullDown((root=(parent=this.parent).root).arr,--this.cursor,--root.size);
        do{
          --parent.size;
        }while((parent=parent.parent)!=null);
      }
      @Override
      public void forEachRemaining(BooleanConsumer action)
      {
        final int cursor,bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
        {
          OmniArray.OfBoolean.ascendingForEach(parent.root.arr,cursor,bound-1,action);
          this.cursor=bound;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Boolean> action)
      {
        final int cursor,bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
        {
          OmniArray.OfBoolean.ascendingForEach(parent.root.arr,cursor,bound-1,action::accept);
          this.cursor=bound;
        }
      }
    }
    @Override
    public OmniIterator.OfBoolean iterator()
    {
      return new Itr(this);
    }
    private static class ListItr extends Itr implements OmniListIterator.OfBoolean
    {
      transient int lastRet;
      private ListItr(UncheckedSubList parent)
      {
        super(parent);
        this.lastRet=-1;
      }
      private ListItr(UncheckedSubList parent,int cursor)
      {
        super(parent,cursor);
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
      public boolean nextBoolean()
      {
        int lastRet;
        this.lastRet=lastRet=this.cursor++;
        return (boolean)parent.root.arr[lastRet];
      }
      @Override
      public void remove()
      {
        UncheckedSubList parent;
        final UncheckedList root;
        final int lastRet;
        OmniArray.OfBoolean.removeIndexAndPullDown((root=(parent=this.parent).root).arr,lastRet=this.lastRet,--root.size);
        this.cursor=lastRet;
        do{
          --parent.size;
        }while((parent=parent.parent)!=null);
      }
      @Override
      public void forEachRemaining(BooleanConsumer action)
      {
        int cursor;
        final int bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
        {
          OmniArray.OfBoolean.ascendingForEach(parent.root.arr,cursor,cursor=bound-1,action);
          this.lastRet=cursor;
          this.cursor=bound;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Boolean> action)
      {
        final int cursor,bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
        {
          OmniArray.OfBoolean.ascendingForEach(parent.root.arr,cursor,bound-1,action::accept);
          this.cursor=bound;
        }
      }
      @Override
      public boolean previousBoolean()
      {
        final int lastRet;
        this.lastRet=lastRet=--this.cursor;
        return (boolean)parent.root.arr[lastRet];
      }
      @Override
      public void set(boolean val)
      {
        parent.root.arr[this.lastRet]=val;
      }
      @Override
      public void add(boolean val)
      {
        final UncheckedList root;
        final int rootSize;
        UncheckedSubList parent;
        if((rootSize=(root=(parent=this.parent).root).size)!=0)
        {
          ((BooleanArrSeq)root).uncheckedInsert(this.cursor++,rootSize,val);
        }
        else
        {
          ((BooleanArrSeq)root).uncheckedInit(val);
          ++this.cursor;
        }
        do{
          ++parent.size;
        }while((parent=parent.parent)!=null);
      }
    }
    @Override
    public OmniListIterator.OfBoolean listIterator()
    {
      return new ListItr(this);
    }
    @Override
    public OmniListIterator.OfBoolean listIterator(int index)
    {
      return new ListItr(this,index+this.rootOffset);
    }
    @Override
    public boolean add(boolean val)
    {
      for(var curr=parent;curr!=null;++curr.size,curr=curr.parent){}
      final UncheckedList root;
      final int rootSize;
      if((rootSize=(root=this.root).size)!=0){
        ((BooleanArrSeq)root).uncheckedInsert(this.rootOffset+(this.size++),rootSize,val);
      }else{
        ((BooleanArrSeq)root).uncheckedInit(val);
        ++this.size;
      }
      return true;
    }
    @Override
    public void add(int index,boolean val)
    {
      for(var curr=parent;curr!=null;++curr.size,curr=curr.parent){}
      ++this.size;
      final UncheckedList root;
      final int rootSize;
      if((rootSize=(root=this.root).size)!=0){
        ((BooleanArrSeq)root).uncheckedInsert(this.rootOffset+index,rootSize,val);
      }else{
        ((BooleanArrSeq)root).uncheckedInit(val);
      }
    }
    @Override
    public <T> T[] toArray(T[] arr)
    {
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,arr=OmniArray.uncheckedArrResize(size,arr),0,size);
      }
      else if(arr.length!=0)
      {
        arr[0]=null;
      }
      return arr;
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
        final boolean[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new boolean[size],0,size);
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
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new Boolean[size],0,size);
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
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new double[size],0,size);
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
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new float[size],0,size);
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
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new long[size],0,size);
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
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new int[size],0,size);
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
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new short[size],0,size);
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
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new byte[size],0,size);
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
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new char[size],0,size);
        return dst;
      }
      return OmniArray.OfChar.DEFAULT_ARR;
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
      final var ret=(boolean)(arr=root.arr)[index+=this.rootOffset];
      arr[index]=val;
      return ret;
    }
    @Override
    public boolean removeBooleanAt(int index)
    {
      final boolean[] arr;
      for(var curr=parent;curr!=null;--curr.size,curr=curr.parent){}
      final UncheckedList root;
      final var ret=(boolean)(arr=(root=this.root).arr)[index+=this.rootOffset];
      OmniArray.OfBoolean.removeIndexAndPullDown(arr,index,--root.size);
      this.size=size-1;
      return ret;
    }
    @Override
    public boolean removeIf(BooleanPredicate filter)
    {
      final int size;
      if((size=this.size)!=0)
      {
        {
          final boolean[] arr;
          final int numRemoved;
          int rootOffset;
          final UncheckedList root;
          if((numRemoved=uncheckedRemoveIfImpl(arr=(root=this.root).arr,rootOffset=this.rootOffset,rootOffset+=size,filter))!=0){
            for(var curr=parent;curr!=null;curr.size-=numRemoved,curr=curr.parent){}
            root.size=OmniArray.OfBoolean.removeRangeAndPullDown(arr,rootOffset,root.size,numRemoved);
            this.size=size-numRemoved;
            return true;
          }
        }
      }
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Boolean> filter)
    {
      final int size;
      if((size=this.size)!=0)
      {
        {
          final boolean[] arr;
          final int numRemoved;
          int rootOffset;
          final UncheckedList root;
          if((numRemoved=uncheckedRemoveIfImpl(arr=(root=this.root).arr,rootOffset=this.rootOffset,rootOffset+=size,filter::test))!=0){
            for(var curr=parent;curr!=null;curr.size-=numRemoved,curr=curr.parent){}
            root.size=OmniArray.OfBoolean.removeRangeAndPullDown(arr,rootOffset,root.size,numRemoved);
            this.size=size-numRemoved;
            return true;
          }
        }
      }
      return false;
    }
    @Override
    public void replaceAll(BooleanPredicate operator)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final int rootOffset;
        OmniArray.OfBoolean.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);  
      }
    }
    @Override
    public void sort(BooleanComparator sorter)
    {
      final int size;
      if((size=this.size)>1)
      {
        final int rootOffset;
        if(sorter==null)
        {
          BooleanSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }
        else
        {
          BooleanSortUtil.uncheckedSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter);
        }
      }
    }
    @Override
    public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        final int rootOffset;
        BooleanSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }
    }
    @Override
    public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        final int rootOffset;
        BooleanSortUtil.uncheckedDescendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }
    }
    @Override
    public void replaceAll(UnaryOperator<Boolean> operator)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final int rootOffset;
        OmniArray.OfBoolean.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator::apply);  
      }
    }
    @Override
    public void sort(Comparator<? super Boolean> sorter)
    {
      final int size;
      if((size=this.size)>1)
      {
        final int rootOffset;
        if(sorter==null)
        {
          BooleanSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }
        else
        {
          BooleanSortUtil.uncheckedSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter::compare);
        }
      }
    }
    @Override
    public OmniList.OfBoolean subList(int fromIndex,int toIndex)
    {
      return new UncheckedSubList(this,this.rootOffset+fromIndex,toIndex-fromIndex);
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
    CheckedStack(int size,boolean[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implements equals method for CheckedStack
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
    public void clear()
    {
      if(this.size!=0)
      {
        ++this.modCount;
        this.size=0;
      }
    }
    @Override
    boolean uncheckedremoveVal (int size
    ,boolean val
    )
    {
      final var arr=this.arr;
      for(int index=--size;;--index)
      {
        if(
        val==arr[index]
        )
        {
          ++this.modCount;
          OmniArray.OfBoolean.removeIndexAndPullDown(arr,index,size);
          this.size=size;
          return true;
        }
        if(index==0)
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
        OmniArray.OfBoolean.descendingForEach(this.arr,0,size-1,action);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    private static class Itr
      extends AbstractBooleanItr
    {
      transient final CheckedStack parent;
      transient int cursor;
      transient int lastRet;
      transient int modCount;
      private Itr(CheckedStack parent)
      {
        this.parent=parent;
        this.cursor=parent.size-1;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      private Itr(CheckedStack parent,int cursor)
      {
        this.parent=parent;
        this.cursor=cursor;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      @Override
      public boolean hasNext()
      {
        return this.cursor>=0;
      }
      @Override
      public boolean nextBoolean()
      {
        final CheckedStack root;
        CheckedCollection.checkModCount(modCount,(root=this.parent).modCount);
        final int cursor;
        if((cursor=this.cursor)>=0)
        {
          this.lastRet=cursor;
          this.cursor=cursor-1;
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
          int modCount;
          final CheckedStack root;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.parent).modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          OmniArray.OfBoolean.removeIndexAndPullDown(root.arr,lastRet,--root.size);
          this.cursor=lastRet;
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override
      public void forEachRemaining(BooleanConsumer action)
      {
        final int cursor;
        if((cursor=this.cursor)>=0)
        {
          final int modCount=this.modCount;
          final var parent=this.parent;
          try
          {
            OmniArray.OfBoolean.descendingForEach(parent.arr,0,cursor,action);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,parent.modCount);
          }
          this.cursor=-1;
          this.lastRet=0;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Boolean> action)
      {
        final int cursor;
        if((cursor=this.cursor)>=0)
        {
          final int modCount=this.modCount;
          final var parent=this.parent;
          try
          {
            OmniArray.OfBoolean.descendingForEach(parent.arr,0,cursor,action::accept);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,parent.modCount);
          }
          this.cursor=-1;
          this.lastRet=0;
        }
      }
    }
    @Override
    public OmniIterator.OfBoolean iterator()
    {
      return new Itr(this);
    }
    @Override
    public void push(boolean val)
    {
      ++this.modCount;
      super.push(val);
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor)
    {
      return super.toArray(arrSize->{
        final int modCount=this.modCount;
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
    public boolean popBoolean()
    {
      int size;
      if((size=this.size)!=0)
      {
        ++this.modCount;
        final var ret=(boolean)arr[--size];
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
        ++this.modCount;
        final var ret=(boolean)(arr[--size]);
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
        ++this.modCount;
        final var ret=(Boolean)(arr[--size]);
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
        ++this.modCount;
        final var ret=TypeUtil.castToDouble(arr[--size]);
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
        ++this.modCount;
        final var ret=TypeUtil.castToFloat(arr[--size]);
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
        ++this.modCount;
        final var ret=TypeUtil.castToLong(arr[--size]);
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
        ++this.modCount;
        final var ret=(int)TypeUtil.castToByte(arr[--size]);
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
        ++this.modCount;
        final var ret=(short)TypeUtil.castToByte(arr[--size]);
        this.size=size;
        return ret;
      }
      return Short.MIN_VALUE;
    }
    @Override
    public byte pollByte()
    {
      int size;
      if((size=this.size)!=0)
      {
        ++this.modCount;
        final var ret=TypeUtil.castToByte(arr[--size]);
        this.size=size;
        return ret;
      }
      return Byte.MIN_VALUE;
    }
    @Override
    public char pollChar()
    {
      int size;
      if((size=this.size)!=0)
      {
        ++this.modCount;
        final var ret=TypeUtil.castToChar(arr[--size]);
        this.size=size;
        return ret;
      }
      return Character.MIN_VALUE;
    }
    @Override
    boolean uncheckedRemoveIf(int size,BooleanPredicate filter)
    {
      final int modCount;
      if(size!=(size-=uncheckedRemoveIfImpl(this.arr
        ,0,size,filter,new CheckedCollection.AbstractModCountChecker(modCount=this.modCount){
          @Override protected int getActualModCount(){
          return CheckedStack.this.modCount;
          }
        }))
        ){
        this.modCount=modCount+1;
        this.size=size;
        return true;
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return false;
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
    CheckedList(int size,boolean[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implements equals method for CheckedList
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
    public void clear()
    {
      if(this.size!=0)
      {
        ++this.modCount;
        this.size=0;
      }
    }
    @Override
    boolean uncheckedremoveVal (int size
    ,boolean val
    )
    {
      final var arr=this.arr;
      for(int index=0;;)
      {
        if(
        val==arr[index]
        )
        {
          OmniArray.OfBoolean.removeIndexAndPullDown(arr,index,--size);
          this.size=size;
          return true;
        }
        if(++index==size)
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
        OmniArray.OfBoolean.ascendingForEach(this.arr,0,size-1,action);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    private static class Itr
      extends AbstractBooleanItr
    {
      transient final CheckedList parent;
      transient int cursor;
      transient int lastRet;
      transient int modCount;
      private Itr(CheckedList parent)
      {
        this.parent=parent;
        this.cursor=0;
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
      public boolean hasNext()
      {
        return this.cursor<parent.size;
      }
      @Override
      public boolean nextBoolean()
      {
        final CheckedList root;
        CheckedCollection.checkModCount(modCount,(root=this.parent).modCount);
        final int cursor;
        if((cursor=this.cursor)<root.size)
        {
          this.lastRet=cursor;
          this.cursor=cursor+1;
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
          int modCount;
          final CheckedList root;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.parent).modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          OmniArray.OfBoolean.removeIndexAndPullDown(root.arr,lastRet,--root.size);
          this.cursor=lastRet;
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override
      public void forEachRemaining(BooleanConsumer action)
      {
        int cursor;
        final int bound;
        final CheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size))
        {
          final int modCount=this.modCount;
          try
          {
            OmniArray.OfBoolean.ascendingForEach(parent.arr,cursor,cursor=bound-1,action);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,parent.modCount);
          }
          this.cursor=bound;
          this.lastRet=cursor;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Boolean> action)
      {
        int cursor;
        final int bound;
        final CheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size))
        {
          final int modCount=this.modCount;
          try
          {
            OmniArray.OfBoolean.ascendingForEach(parent.arr,cursor,cursor=bound-1,action::accept);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,parent.modCount);
          }
          this.cursor=bound;
          this.lastRet=cursor;
        }
      }
    }
    @Override
    public OmniIterator.OfBoolean iterator()
    {
      return new Itr(this);
    }
    private static class ListItr extends Itr implements OmniListIterator.OfBoolean
    {
      private ListItr(CheckedList parent)
      {
        super(parent);
      }
      private ListItr(CheckedList parent,int cursor)
      {
        super(parent,cursor);
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
        final CheckedList root;
        int cursor;
        CheckedCollection.checkModCount(modCount,(root=this.parent).modCount);
        if((cursor=this.cursor)>0)
        {
          this.lastRet=--cursor;
          this.cursor=cursor;
          return (boolean)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override
      public void set(boolean val)
      {
        final int lastRet;
        if((lastRet=this.lastRet)!=-1)
        {
          final CheckedList root;
          CheckedCollection.checkModCount(modCount,(root=this.parent).modCount);
          root.arr[lastRet]=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override
      public void add(boolean val)
      {
        int modCount;
        final CheckedList root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.parent).modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        this.lastRet=-1;
        final int rootSize;
        if((rootSize=root.size)!=0)
        {
          ((BooleanArrSeq)root).uncheckedInsert(this.cursor++,rootSize,val);
        }
        else
        {
          ((BooleanArrSeq)root).uncheckedInit(val);
          ++this.cursor;
        }
      }
    }
    @Override
    public OmniListIterator.OfBoolean listIterator()
    {
      return new ListItr(this);
    }
    @Override
    public OmniListIterator.OfBoolean listIterator(int index)
    {
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,this.size);
      return new ListItr(this,index);
    }
    @Override
    public void push(boolean val)
    {
      ++this.modCount;
      super.push(val);
    }
    @Override
    public void add(int index,boolean val)
    {
      final int size;
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,size=this.size);
      ++this.modCount;
      if(size!=0){
        ((BooleanArrSeq)this).uncheckedInsert(index,size,val);
      }else{
        ((BooleanArrSeq)this).uncheckedInit(val);
      }
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor)
    {
      return super.toArray(arrSize->{
        final int modCount=this.modCount;
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
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      return (boolean)this.arr[index];
    }
    @Override
    public void put(int index,boolean val)
    {
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      this.arr[index]=val;
    }
    @Override
    public boolean set(int index,boolean val)
    {
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      final boolean[] arr;
      final var ret=(boolean)(arr=this.arr)[index];
      arr[index]=val;
      return ret;
    }
    @Override
    public boolean removeBooleanAt(int index)
    {
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      final boolean[] arr;
      ++this.modCount;
      final var ret=(boolean)(arr=this.arr)[index];
      OmniArray.OfBoolean.removeIndexAndPullDown(arr,index,--size);
      this.size=size;
      return ret;
    }
    @Override
    boolean uncheckedRemoveIf(int size,BooleanPredicate filter)
    {
      final int modCount;
      if(size!=(size-=uncheckedRemoveIfImpl(this.arr
        ,0,size,filter,new CheckedCollection.AbstractModCountChecker(modCount=this.modCount){
          @Override protected int getActualModCount(){
          return CheckedList.this.modCount;
          }
        }))
        ){
        this.modCount=modCount+1;
        this.size=size;
        return true;
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return false;
    }
    @Override
    public void replaceAll(BooleanPredicate operator)
    {
      final int size;
      if((size=this.size)!=0)
      {
        int modCount=this.modCount;
        try
        {
          OmniArray.OfBoolean.uncheckedReplaceAll(this.arr,0,size,operator);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        this.modCount=modCount+1;
      }
    }
    @Override
    public void sort(BooleanComparator sorter)
    {
      final int size;
      if((size=this.size)>1)
      {
        if(sorter==null)
        {
          BooleanSortUtil.uncheckedAscendingSort(this.arr,0,size);
          ++this.modCount;
        }
        else
        {
          final int modCount=this.modCount;
          try
          {
            BooleanSortUtil.uncheckedSort(this.arr,0,size,sorter);
          }
          catch(ArrayIndexOutOfBoundsException e)
          {
            throw CheckedCollection.checkModCount(modCount,this.modCount,e);
          }
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override
    public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        BooleanSortUtil.uncheckedAscendingSort(this.arr,0,size);
        ++this.modCount;
      }
    }
    @Override
    public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        BooleanSortUtil.uncheckedDescendingSort(this.arr,0,size);
        ++this.modCount;
      }
    }
    @Override
    public void replaceAll(UnaryOperator<Boolean> operator)
    {
      final int size;
      if((size=this.size)!=0)
      {
        int modCount=this.modCount;
        try
        {
          OmniArray.OfBoolean.uncheckedReplaceAll(this.arr,0,size,operator::apply);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        this.modCount=modCount+1;
      }
    }
    @Override
    public void sort(Comparator<? super Boolean> sorter)
    {
      final int size;
      if((size=this.size)>1)
      {
        if(sorter==null)
        {
          BooleanSortUtil.uncheckedAscendingSort(this.arr,0,size);
          ++this.modCount;
        }
        else
        {
          final int modCount=this.modCount;
          try
          {
            BooleanSortUtil.uncheckedSort(this.arr,0,size,sorter::compare);
          }
          catch(ArrayIndexOutOfBoundsException e)
          {
            throw CheckedCollection.checkModCount(modCount,this.modCount,e);
          }
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override
    public OmniList.OfBoolean subList(int fromIndex,int toIndex)
    {
      return new CheckedSubList(this,fromIndex,CheckedCollection.checkSubListRange(fromIndex,toIndex,this.size));
    }
  }
  private
    static class CheckedSubList
      implements BooleanSubListDefault,Cloneable
  {
    transient int modCount;
    transient final int rootOffset;
    transient int size;
    transient final CheckedList root;
    transient final CheckedSubList parent;
    private CheckedSubList(CheckedList root,int rootOffset,int size)
    {
      super();
      this.root=root;
      this.parent=null;
      this.rootOffset=rootOffset;
      this.size=size;
      this.modCount=root.modCount;
    }
    private CheckedSubList(CheckedSubList parent,int rootOffset,int size)
    {
      super();
      this.root=parent.root;
      this.parent=parent;
      this.rootOffset=rootOffset;
      this.size=size;
      this.modCount=parent.modCount;
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implements equals method for CheckedSubList
      return false;
    }
    @Override
    public Object clone()
    {
      final CheckedList root;
      CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
      final boolean[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(root.arr,rootOffset,copy=new boolean[size],0,size);
      }
      else
      {
        copy=OmniArray.OfBoolean.DEFAULT_ARR;
      }
      return new CheckedList(size,copy);
    }
    @Override
    public String toString()
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      int size;
      if((size=this.size)!=0)
      {
        if(size>=(Integer.MAX_VALUE/6))
        {
          throw new OutOfMemoryError();
        }
          final int rootOffset;
          final byte[] buffer;
          if(size<=(OmniArray.MAX_ARR_SIZE/7)){(buffer=new byte[size*7])
            [size=OmniArray.OfBoolean.ascendingToString(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,buffer,1)]=(byte)']';
            buffer[0]=(byte)'[';
            return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
          }else{
            final ToStringUtil.OmniStringBuilderByte builder;
            OmniArray.OfBoolean.ascendingToString(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
            builder.uncheckedAppendChar((byte)']');
            (buffer=builder.buffer)[0]=(byte)'[';
            return new String(buffer,0,size=builder.size,ToStringUtil.IOS8859CharSet);
          }
      }
      return "[]";
    }
    @Override
    public int hashCode()
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0)
      {
        final int rootOffset;
        return OmniArray.OfBoolean.ascendingSeqHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
      }
      return 1;
    }
    @Override
    public int size()
    {
      CheckedCollection.checkModCount(modCount,root.modCount);
      return this.size;
    }
    @Override
    public boolean isEmpty()
    {
      CheckedCollection.checkModCount(modCount,root.modCount);
      return this.size==0;
    }
    @Override
    public void clear()
    {
      final CheckedList root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0)
      {
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr.size-=size,curr=curr.parent){}
        root.size=OmniArray.OfBoolean.removeRangeAndPullDown(root.arr,this.rootOffset+size,root.size,size);
        this.size=0;
      }
    }
    @Override
    public boolean contains(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          final int rootOffset;
          return OmniArray.OfBoolean.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(val));
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean contains(int val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          returnFalse:for(;;)
          {
            final boolean v;
            switch(val)
            {
            default:
              break returnFalse;
            case 0:
              v=false;
              break;
            case 1:
              v=true;
            }
            final int modCount=this.modCount;
            final var root=this.root;
            try{
              final int rootOffset;
              return OmniArray.OfBoolean.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,v);
            }finally{
              CheckedCollection.checkModCount(modCount,root.modCount);
            }
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean contains(long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        returnFalse:for(;;)
        {
          final boolean v;
          if(val==0L)
          {
            v=false;
          }else if(val==1L)
          {
            v=true;
          }
          else
          {
            break returnFalse;
          }
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfBoolean.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,v);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean contains(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        returnFalse:for(;;)
        {
          final boolean v;
          switch(Float.floatToRawIntBits(val))
          {
            default:
              break returnFalse;
            case 0:
            case Integer.MIN_VALUE:
              v=false;
              break;
            case TypeUtil.FLT_TRUE_BITS:
              v=true;
          }
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfBoolean.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,v);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean contains(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        returnFalse:for(;;)
        {
          final boolean v;
          long bits;
          if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE)
          {
            v=false;
          }
          else if(bits==TypeUtil.DBL_TRUE_BITS)
          {
            v=true;
          }
          else
          {
            break returnFalse;
          }
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfBoolean.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,v);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean contains(Object val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Boolean)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfBoolean.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(boolean)(val));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean removeVal(boolean val)
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
    public boolean removeVal(int val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          returnFalse:for(;;)
          {
            final boolean v;
            switch(val)
            {
            default:
              break returnFalse;
            case 0:
              v=false;
              break;
            case 1:
              v=true;
            }
            return this.uncheckedremoveVal(size,v);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean removeVal(long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        returnFalse:for(;;)
        {
          final boolean v;
          if(val==0L)
          {
            v=false;
          }else if(val==1L)
          {
            v=true;
          }
          else
          {
            break returnFalse;
          }
          return this.uncheckedremoveVal(size,v);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean removeVal(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        returnFalse:for(;;)
        {
          final boolean v;
          switch(Float.floatToRawIntBits(val))
          {
            default:
              break returnFalse;
            case 0:
            case Integer.MIN_VALUE:
              v=false;
              break;
            case TypeUtil.FLT_TRUE_BITS:
              v=true;
          }
          return this.uncheckedremoveVal(size,v);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean removeVal(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        returnFalse:for(;;)
        {
          final boolean v;
          long bits;
          if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE)
          {
            v=false;
          }
          else if(bits==TypeUtil.DBL_TRUE_BITS)
          {
            v=true;
          }
          else
          {
            break returnFalse;
          }
          return this.uncheckedremoveVal(size,v);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean remove(Object val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Boolean)
        {
          return this.uncheckedremoveVal(size,(boolean)(val));
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public int indexOf(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          return OmniArray.OfBoolean.uncheckedindexOf(root.arr,this.rootOffset,size,(val));
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int indexOf(int val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          returnFalse:for(;;)
          {
            final boolean v;
            switch(val)
            {
            default:
              break returnFalse;
            case 0:
              v=false;
              break;
            case 1:
              v=true;
            }
            final int modCount=this.modCount;
            final var root=this.root;
            try{
              return OmniArray.OfBoolean.uncheckedindexOf(root.arr,this.rootOffset,size,v);
            }finally{
              CheckedCollection.checkModCount(modCount,root.modCount);
            }
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int indexOf(long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        returnFalse:for(;;)
        {
          final boolean v;
          if(val==0L)
          {
            v=false;
          }else if(val==1L)
          {
            v=true;
          }
          else
          {
            break returnFalse;
          }
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfBoolean.uncheckedindexOf(root.arr,this.rootOffset,size,v);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int indexOf(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        returnFalse:for(;;)
        {
          final boolean v;
          switch(Float.floatToRawIntBits(val))
          {
            default:
              break returnFalse;
            case 0:
            case Integer.MIN_VALUE:
              v=false;
              break;
            case TypeUtil.FLT_TRUE_BITS:
              v=true;
          }
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfBoolean.uncheckedindexOf(root.arr,this.rootOffset,size,v);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int indexOf(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        returnFalse:for(;;)
        {
          final boolean v;
          long bits;
          if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE)
          {
            v=false;
          }
          else if(bits==TypeUtil.DBL_TRUE_BITS)
          {
            v=true;
          }
          else
          {
            break returnFalse;
          }
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfBoolean.uncheckedindexOf(root.arr,this.rootOffset,size,v);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int indexOf(Object val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Boolean)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfBoolean.uncheckedindexOf(root.arr,this.rootOffset,size,(boolean)(val));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int lastIndexOf(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          return OmniArray.OfBoolean.uncheckedlastIndexOf(root.arr,this.rootOffset,size,(val));
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int lastIndexOf(int val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          returnFalse:for(;;)
          {
            final boolean v;
            switch(val)
            {
            default:
              break returnFalse;
            case 0:
              v=false;
              break;
            case 1:
              v=true;
            }
            final int modCount=this.modCount;
            final var root=this.root;
            try{
              return OmniArray.OfBoolean.uncheckedlastIndexOf(root.arr,this.rootOffset,size,v);
            }finally{
              CheckedCollection.checkModCount(modCount,root.modCount);
            }
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int lastIndexOf(long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        returnFalse:for(;;)
        {
          final boolean v;
          if(val==0L)
          {
            v=false;
          }else if(val==1L)
          {
            v=true;
          }
          else
          {
            break returnFalse;
          }
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfBoolean.uncheckedlastIndexOf(root.arr,this.rootOffset,size,v);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int lastIndexOf(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        returnFalse:for(;;)
        {
          final boolean v;
          switch(Float.floatToRawIntBits(val))
          {
            default:
              break returnFalse;
            case 0:
            case Integer.MIN_VALUE:
              v=false;
              break;
            case TypeUtil.FLT_TRUE_BITS:
              v=true;
          }
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfBoolean.uncheckedlastIndexOf(root.arr,this.rootOffset,size,v);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int lastIndexOf(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        returnFalse:for(;;)
        {
          final boolean v;
          long bits;
          if((bits=Double.doubleToRawLongBits(val))==0 || bits==Long.MIN_VALUE)
          {
            v=false;
          }
          else if(bits==TypeUtil.DBL_TRUE_BITS)
          {
            v=true;
          }
          else
          {
            break returnFalse;
          }
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfBoolean.uncheckedlastIndexOf(root.arr,this.rootOffset,size,v);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
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
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfBoolean.uncheckedlastIndexOf(root.arr,this.rootOffset,size,(boolean)(val));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    private boolean uncheckedremoveVal (int size
    ,boolean val
    )
    {
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      final var arr=root.arr;
      for(int index=this.rootOffset,bound=index+(--size);;++index)
      {
        if(
        val==arr[index]
        )
        {
          root.modCount=++modCount;
          this.modCount=modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,--curr.size,curr=curr.parent){}
          OmniArray.OfBoolean.removeIndexAndPullDown(arr,index,--root.size);
          this.size=size;
          return true;
        }
        if(index==bound)
        {
          return false;
        }
      }
    }
    @Override
    public void forEach(BooleanConsumer action)
    {
      int modCount=this.modCount;
      final var root=this.root;
      try
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          OmniArray.OfBoolean.ascendingForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,action);
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
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          OmniArray.OfBoolean.ascendingForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,action::accept);
        }
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    private static class Itr
      extends AbstractBooleanItr
    {
      transient final CheckedSubList parent;
      transient int cursor;
      transient int lastRet;
      transient int modCount;
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
        this.cursor=cursor;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      @Override
      public boolean hasNext()
      {
        final CheckedSubList parent;
        return this.cursor<(parent=this.parent).rootOffset+parent.size;
      }
      @Override
      public boolean nextBoolean()
      {
        final CheckedList root;
        final CheckedSubList parent;
        CheckedCollection.checkModCount(modCount,(root=(parent=this.parent).root).modCount);
        final int cursor;
        if((cursor=this.cursor)<(parent.rootOffset+parent.size))
        {
          this.lastRet=cursor;
          this.cursor=cursor+1;
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
          int modCount;
          final CheckedList root;
          CheckedSubList parent;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
          root.modCount=++modCount;
          do{
            parent.modCount=modCount;
            --parent.size;
          }while((parent=parent.parent)!=null);
          this.modCount=modCount;
          OmniArray.OfBoolean.removeIndexAndPullDown(root.arr,lastRet,--root.size);
          this.cursor=lastRet;
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override
      public void forEachRemaining(BooleanConsumer action)
      {
        int cursor;
        final int bound;
        final CheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
        {
          final int modCount=this.modCount;
          final var root=parent.root;
          try
          {
            OmniArray.OfBoolean.ascendingForEach(root.arr,cursor,cursor=bound-1,action);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
          this.cursor=bound;
          this.lastRet=cursor;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Boolean> action)
      {
        int cursor;
        final int bound;
        final CheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
        {
          final int modCount=this.modCount;
          final var root=parent.root;
          try
          {
            OmniArray.OfBoolean.ascendingForEach(root.arr,cursor,cursor=bound-1,action::accept);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
          this.cursor=bound;
          this.lastRet=cursor;
        }
      }
    }
    @Override
    public OmniIterator.OfBoolean iterator()
    {
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new Itr(this);
    }
    private static class ListItr extends Itr implements OmniListIterator.OfBoolean
    {
      private ListItr(CheckedSubList parent)
      {
        super(parent);
      }
      private ListItr(CheckedSubList parent,int cursor)
      {
        super(parent,cursor);
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
        final CheckedList root;
        int cursor;
        final CheckedSubList parent;
        CheckedCollection.checkModCount(modCount,(root=(parent=this.parent).root).modCount);
        if((cursor=this.cursor)>parent.rootOffset)
        {
          this.lastRet=--cursor;
          this.cursor=cursor;
          return (boolean)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override
      public void set(boolean val)
      {
        final int lastRet;
        if((lastRet=this.lastRet)!=-1)
        {
          final CheckedList root;
          CheckedCollection.checkModCount(modCount,(root=this.parent.root).modCount);
          root.arr[lastRet]=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override
      public void add(boolean val)
      {
        int modCount;
        final CheckedList root;
        CheckedSubList parent;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
        root.modCount=++modCount;
        do{
          parent.modCount=modCount;
          ++parent.size;
        }while((parent=parent.parent)!=null);
        this.modCount=modCount;
        this.lastRet=-1;
        final int rootSize;
        if((rootSize=root.size)!=0)
        {
          ((BooleanArrSeq)root).uncheckedInsert(this.cursor++,rootSize,val);
        }
        else
        {
          ((BooleanArrSeq)root).uncheckedInit(val);
          ++this.cursor;
        }
      }
    }
    @Override
    public OmniListIterator.OfBoolean listIterator()
    {
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new ListItr(this);
    }
    @Override
    public OmniListIterator.OfBoolean listIterator(int index)
    {
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,this.size);
      return new ListItr(this,index+this.rootOffset);
    }
    @Override
    public boolean add(boolean val)
    {
      final CheckedList root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,++curr.size,curr=curr.parent){}
      if((modCount=root.size)!=0){
        ((BooleanArrSeq)root).uncheckedInsert(this.rootOffset+(this.size++),modCount,val);
      }else{
        ((BooleanArrSeq)root).uncheckedInit(val);
        ++this.size;
      }
      return true;
    }
    @Override
    public void add(int index,boolean val)
    {
      final CheckedList root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      final int size;
      CheckedCollection.checkWriteHi(index,size=this.size);
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,++curr.size,curr=curr.parent){}
      this.size=size+1;
      if((modCount=root.size)!=0){
        ((BooleanArrSeq)root).uncheckedInsert(this.rootOffset+index,modCount,val);
      }else{
        ((BooleanArrSeq)root).uncheckedInit(val);
      }
    }
    @Override
    public <T> T[] toArray(T[] arr)
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,arr=OmniArray.uncheckedArrResize(size,arr),0,size);
      }
      else if(arr.length!=0)
      {
        arr[0]=null;
      }
      return arr;
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor)
    {
      final int size;
      final T[] dst;
      final CheckedList root;
      int modCount=this.modCount;
      try
      {
        dst=arrConstructor.apply(size=this.size);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
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
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0)
      {
        final boolean[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new boolean[size],0,size);
        return dst;
      }
      return OmniArray.OfBoolean.DEFAULT_ARR;
    }
    @Override
    public Boolean[] toArray()
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0)
      {
        final Boolean[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new Boolean[size],0,size);
        return dst;
      }
      return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
    }
    @Override
    public double[] toDoubleArray()
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0)
      {
        final double[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new double[size],0,size);
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override
    public float[] toFloatArray()
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0)
      {
        final float[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new float[size],0,size);
        return dst;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override
    public long[] toLongArray()
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0)
      {
        final long[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new long[size],0,size);
        return dst;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override
    public int[] toIntArray()
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0)
      {
        final int[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new int[size],0,size);
        return dst;
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
    @Override
    public short[] toShortArray()
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0)
      {
        final short[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new short[size],0,size);
        return dst;
      }
      return OmniArray.OfShort.DEFAULT_ARR;
    }
    @Override
    public byte[] toByteArray()
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0)
      {
        final byte[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new byte[size],0,size);
        return dst;
      }
      return OmniArray.OfByte.DEFAULT_ARR;
    }
    @Override
    public char[] toCharArray()
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0)
      {
        final char[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new char[size],0,size);
        return dst;
      }
      return OmniArray.OfChar.DEFAULT_ARR;
    }
    @Override
    public boolean getBoolean(int index)
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      return (boolean)root.arr[index+this.rootOffset];
    }
    @Override
    public void put(int index,boolean val)
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      root.arr[index+this.rootOffset]=val;
    }
    @Override
    public boolean set(int index,boolean val)
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      final boolean[] arr;
      final var ret=(boolean)(arr=root.arr)[index+=this.rootOffset];
      arr[index]=val;
      return ret;
    }
    @Override
    public boolean removeBooleanAt(int index)
    {
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      final boolean[] arr;
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,--curr.size,curr=curr.parent){}
      final var ret=(boolean)(arr=root.arr)[index+=this.rootOffset];
      OmniArray.OfBoolean.removeIndexAndPullDown(arr,index,--root.size);
      this.size=size-1;
      return ret;
    }
    @Override
    public boolean removeIf(BooleanPredicate filter)
    {
      int modCount=this.modCount;
      final var root=this.root;
      final int size;
      if((size=this.size)!=0)
      {
        try
        {
          final boolean[] arr;
          final int numRemoved;
          int rootOffset;
          if((numRemoved=uncheckedRemoveIfImpl(arr=root.arr,rootOffset=this.rootOffset,rootOffset+=size,filter,new CheckedCollection.AbstractModCountChecker(modCount){
            @Override protected int getActualModCount(){
              return root.modCount;
            }
          }))!=0){
            root.modCount=++modCount;
            this.modCount=modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,curr.size-=numRemoved,curr=curr.parent){}
            root.size=OmniArray.OfBoolean.removeRangeAndPullDown(arr,rootOffset,root.size,numRemoved);
            this.size=size-numRemoved;
            return true;
          }
        }
        catch(ConcurrentModificationException e)
        {
          throw e;
        }
        catch(RuntimeException e)
        {
          throw CheckedCollection.checkModCount(modCount,root.modCount,e);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Boolean> filter)
    {
      int modCount=this.modCount;
      final var root=this.root;
      final int size;
      if((size=this.size)!=0)
      {
        try
        {
          final boolean[] arr;
          final int numRemoved;
          int rootOffset;
          if((numRemoved=uncheckedRemoveIfImpl(arr=root.arr,rootOffset=this.rootOffset,rootOffset+=size,filter::test,new CheckedCollection.AbstractModCountChecker(modCount){
            @Override protected int getActualModCount(){
              return root.modCount;
            }
          }))!=0){
            root.modCount=++modCount;
            this.modCount=modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,curr.size-=numRemoved,curr=curr.parent){}
            root.size=OmniArray.OfBoolean.removeRangeAndPullDown(arr,rootOffset,root.size,numRemoved);
            this.size=size-numRemoved;
            return true;
          }
        }
        catch(ConcurrentModificationException e)
        {
          throw e;
        }
        catch(RuntimeException e)
        {
          throw CheckedCollection.checkModCount(modCount,root.modCount,e);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public void replaceAll(BooleanPredicate operator)
    {
      int modCount=this.modCount;
      final var root=this.root;
      try
      {
        final int size;
        if((size=this.size)==0)
        {
          return;
        }
        final int rootOffset;
        OmniArray.OfBoolean.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);  
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
    }
    @Override
    public void sort(BooleanComparator sorter)
    {
      int modCount=this.modCount;
      final var root=this.root;
      try
      {
        final int size;
        if((size=this.size)<2)
        {
          return;
        }
        final int rootOffset;
        if(sorter==null)
        {
          BooleanSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }
        else
        {
          try
          {
            BooleanSortUtil.uncheckedSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter);
          }
          catch(ArrayIndexOutOfBoundsException e)
          {
            throw new IllegalArgumentException("Comparison method violates its general contract!");
          }
        }
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
    }
    @Override
    public void stableAscendingSort()
    {
      int modCount=this.modCount;
      final var root=this.root;
      try
      {
        final int size;
        if((size=this.size)<2)
        {
          return;
        }
        final int rootOffset;
        BooleanSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
    }
    @Override
    public void stableDescendingSort()
    {
      int modCount=this.modCount;
      final var root=this.root;
      try
      {
        final int size;
        if((size=this.size)<2)
        {
          return;
        }
        final int rootOffset;
        BooleanSortUtil.uncheckedDescendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
    }
    @Override
    public void replaceAll(UnaryOperator<Boolean> operator)
    {
      int modCount=this.modCount;
      final var root=this.root;
      try
      {
        final int size;
        if((size=this.size)==0)
        {
          return;
        }
        final int rootOffset;
        OmniArray.OfBoolean.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator::apply);  
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
    }
    @Override
    public void sort(Comparator<? super Boolean> sorter)
    {
      int modCount=this.modCount;
      final var root=this.root;
      try
      {
        final int size;
        if((size=this.size)<2)
        {
          return;
        }
        final int rootOffset;
        if(sorter==null)
        {
          BooleanSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }
        else
        {
          try
          {
            BooleanSortUtil.uncheckedSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter::compare);
          }
          catch(ArrayIndexOutOfBoundsException e)
          {
            throw new IllegalArgumentException("Comparison method violates its general contract!");
          }
        }
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
    }
    @Override
    public OmniList.OfBoolean subList(int fromIndex,int toIndex)
    {
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new CheckedSubList(this,this.rootOffset+fromIndex,CheckedCollection.checkSubListRange(fromIndex,toIndex,this.size));
    }
  }
}
