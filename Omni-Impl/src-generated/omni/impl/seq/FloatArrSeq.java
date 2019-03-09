package omni.impl.seq;
import omni.api.OmniCollection;
import omni.util.OmniArray;
import omni.api.OmniList;
import omni.api.OmniStack;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.Comparator;
import omni.util.ArrCopy;
import omni.util.FloatSortUtil;
import omni.impl.CheckedCollection;
import java.util.NoSuchElementException;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;
import omni.util.TypeUtil;
import java.util.ConcurrentModificationException;
import omni.function.FloatUnaryOperator;
import omni.function.FloatComparator;
import omni.function.FloatPredicate;
import omni.function.FloatConsumer;
import omni.util.ToStringUtil;
import omni.util.HashUtil;
import omni.util.BitSetUtil;
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
  public boolean removeIf(FloatPredicate filter)
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
  public boolean removeIf(Predicate<? super Float> filter)
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
  static void uncheckedForEachAscending(float[] arr,int offset,int bound,FloatConsumer action)
  {
    do
    {
      action.accept((float)arr[offset]);
    }
    while(++offset!=bound);
  }
  static void uncheckedForEachDescending(float[] arr,int offset,int bound,FloatConsumer action)
  {
    do
    {
      action.accept((float)arr[--bound]);
    }
    while(bound!=offset);
  }
  private static int doRemoveIf(float[] arr,int srcOffset,int srcBound,FloatPredicate filter)
  {
    do
    {
      if(filter.test((float)arr[srcOffset]))
      {
        return pullSurvivorsDown(arr,srcOffset,srcBound,filter);
      }
    }
    while(++srcOffset!=srcBound);
    return srcBound;
  }
    private static  int pullSurvivorsDown(float[] arr,int srcOffset,int srcBound,FloatPredicate filter)
    {
      int dstOffset=srcOffset;
      while(++srcOffset!=srcBound)
      {
        final float v;
        if(!filter.test((float)(v=arr[srcOffset])))
        {
          arr[dstOffset++]=v;
        }
      }
      return dstOffset;
    }
    private static int doRemoveIf(float[] arr,int srcOffset,int srcBound,FloatPredicate filter,CheckedCollection.AbstractModCountChecker modCountChecker)
    {
      try
      {
        do
        {
          if(filter.test((float)arr[srcOffset]))
          {
            int dstOffset=srcOffset;
            while(++srcOffset!=srcBound)
            {
              final float v;
              if(!filter.test(v=arr[srcOffset]))
              {
                int newDstOffset=markAndPullDown(arr,srcOffset+1,srcBound,filter,dstOffset+1,modCountChecker);
                arr[dstOffset]=v;
                return newDstOffset;
              }
            }
            modCountChecker.checkModCount();
            return dstOffset;
          }
        }
        while(++srcOffset!=srcBound);
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
    private static int markAndPullDown(float[] arr,int srcOffset,int srcBound,FloatPredicate filter,int dstOffset,CheckedCollection.AbstractModCountChecker modCountChecker)
    {
      int nRemaining;
      if((nRemaining=(srcBound-srcOffset))!=0)
      {
        if(nRemaining>64)
        {
          long[] survivorSet;
          int numSurvivors=BitSetUtil.markSurvivors(arr,srcOffset,srcBound,filter,survivorSet=BitSetUtil.getBitSet(nRemaining));
          modCountChecker.checkModCount();
          if(numSurvivors!=0)
          {
            if(numSurvivors==nRemaining)
            {
              System.arraycopy(arr,srcOffset,arr,dstOffset,numSurvivors);
              dstOffset+=numSurvivors;
            }
            else
            {
              BitSetUtil.pullSurvivorsDown(arr,srcOffset,dstOffset,dstOffset+=numSurvivors,survivorSet);
            }
          }
        }
        else
        {
          long word=BitSetUtil.markSurvivors(arr,srcOffset,srcBound,filter);
          modCountChecker.checkModCount();
          int numSurvivors;
          if((numSurvivors=Long.bitCount(word))!=0)
          {
            if(numSurvivors==nRemaining)
            {
              ArrCopy.uncheckedCopy(arr,srcOffset,arr,dstOffset,numSurvivors);
              dstOffset+=numSurvivors;
            }
            else
            {
              BitSetUtil.pullSurvivorsDown(arr,srcOffset,dstOffset,dstOffset+=numSurvivors,word);
            }
          }
        }
      }
      else
      {
        modCountChecker.checkModCount();
      }
      return dstOffset;
    }
  @Override
  public boolean add(boolean val)
  {
    push(TypeUtil.castToFloat(val));
    return true;
  }
  @Override
  public boolean add(int val)
  {
    push((float)val);
    return true;
  }
  @Override
  public boolean add(char val)
  {
    push((float)val);
    return true;
  }
  @Override
  public boolean add(long val)
  {
    push((float)val);
    return true;
  }
  @Override
  public boolean add(short val)
  {
    push((float)val);
    return true;
  }
  @Override
  public boolean add(float val)
  {
    push(val);
    return true;
  }
  @Override
  public boolean add(Float val)
  {
    push((float)val);
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
      if(size>(Integer.MAX_VALUE/5))
      {
        throw new OutOfMemoryError();
      }
      final char[] buffer;
      if(size<=(OmniArray.MAX_ARR_SIZE/((17)<<1)))
      {
        (buffer=new char[size*(17)])[size=uncheckedToString(size,buffer)]=']';
        buffer[0]='[';
        return new String(buffer,0,size+1);
      }
      else
      {
        final ToStringUtil.OmniStringBuilder builder;
        uncheckedToString(size,builder=new ToStringUtil.OmniStringBuilder(1,new char[size<=(int)(OmniArray.MAX_ARR_SIZE/11)?(size*11):OmniArray.MAX_ARR_SIZE]));
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
  private boolean uncheckedcontainsBits(int size
  ,int bits
  )
  {
    for(final var arr=this.arr;;)
    {
      if(
      bits==Float.floatToRawIntBits(arr[--size])
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
  private boolean uncheckedcontains0(int size
  )
  {
    for(final var arr=this.arr;;)
    {
      if(
      0==arr[--size]
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
  private boolean uncheckedcontainsNaN(int size
  )
  {
    for(final var arr=this.arr;;)
    {
      if(
      Float.isNaN(arr[--size])
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
  abstract boolean uncheckedremoveValBits(int size,int bits);
  abstract boolean uncheckedremoveVal0(int size);
  abstract boolean uncheckedremoveValNaN(int size);
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
        return uncheckedremoveValBits(size,TypeUtil.FLT_TRUE_BITS);
      }
      return uncheckedremoveVal0(size);
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
          return uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
        }
      }
      else
      {
        return uncheckedremoveVal0(size);
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
          return uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
        }
      }
      else
      {
        return uncheckedremoveVal0(size);
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
        return uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
      }
      return uncheckedremoveValNaN(size);
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
        return uncheckedremoveValBits(size,Float.floatToRawIntBits(v));
      }
      else if(v!=v)
      {
        return uncheckedremoveValNaN(size);
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
          return uncheckedremoveValBits(size,Float.floatToRawIntBits(v));
        }
        return uncheckedremoveValNaN(size);
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
        if(val!=0)
        {
          return uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
        }
        return uncheckedremoveVal0(size);
      }
    }
    return false;
  }
  @Override
  public
  boolean
  removeVal(char val)
  {
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val!=0)
        {
          return uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
        }
        return uncheckedremoveVal0(size);
      }
    }
    return false;
  }
  @Override
  public
  boolean
  removeVal(short val)
  {
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val!=0)
        {
          return uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
        }
        return uncheckedremoveVal0(size);
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
      if(val)
      {
        return uncheckedcontainsBits(size,TypeUtil.FLT_TRUE_BITS);
      }
      return uncheckedcontains0(size);
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
          return uncheckedcontainsBits(size,Float.floatToRawIntBits(val));
        }
      }
      else
      {
        return uncheckedcontains0(size);
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
          return uncheckedcontainsBits(size,Float.floatToRawIntBits(val));
        }
      }
      else
      {
        return uncheckedcontains0(size);
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
        return uncheckedcontainsBits(size,Float.floatToRawIntBits(val));
      }
      return uncheckedcontainsNaN(size);
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
        return uncheckedcontainsBits(size,Float.floatToRawIntBits(v));
      }
      else if(v!=v)
      {
        return uncheckedcontainsNaN(size);
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
          return uncheckedcontainsBits(size,Float.floatToRawIntBits(v));
        }
        return uncheckedcontainsNaN(size);
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
        if(val!=0)
        {
          return uncheckedcontainsBits(size,Float.floatToRawIntBits(val));
        }
        return uncheckedcontains0(size);
      }
    }
    return false;
  }
  @Override
  public
  boolean
  contains(char val)
  {
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val!=0)
        {
          return uncheckedcontainsBits(size,Float.floatToRawIntBits(val));
        }
        return uncheckedcontains0(size);
      }
    }
    return false;
  }
  @Override
  public
  boolean
  contains(short val)
  {
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val!=0)
        {
          return uncheckedcontainsBits(size,Float.floatToRawIntBits(val));
        }
        return uncheckedcontains0(size);
      }
    }
    return false;
  }
  abstract void uncheckedCopyInto(Object[] dst,int size);
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
  abstract void uncheckedCopyInto(Float[] dst,int size);
  @Override
  public Float[] toArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final Float[] copy;
      uncheckedCopyInto(copy=new Float[size],size);
      return copy;
    }
    return OmniArray.OfFloat.DEFAULT_BOXED_ARR;
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
  abstract int uncheckedHashCode(int size);
  abstract int uncheckedToString(int size,char[] buffer);
  abstract void uncheckedToString(int size,ToStringUtil.OmniStringBuilder builder);
  abstract void uncheckedForEach(int size,FloatConsumer action);
  private void uncheckedInit(float val)
  {
    float[] arr;
    if((arr=this.arr)==OmniArray.OfFloat.DEFAULT_ARR)
    {
      this.arr=arr=new float[OmniArray.DEFAULT_ARR_SEQ_CAP];
    }
    else if(arr==null)
    {
      this.arr=arr=new float[1];
    }
    arr[0]=val;
    this.size=1;
  }
  private void uncheckedAppend(int size,float val)
  {
    float[] arr;
    if((arr=this.arr).length==size)
    {
      ArrCopy.uncheckedCopy(arr,0,arr=new float[OmniArray.growBy50Pct(size)],0,size);
      this.arr=arr;
    }
    arr[size]=val;
    this.size=size+1;
  }
  public void push(float val)
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
  private void uncheckedInsert(int size,int index,float val)
  {
    int tailDist;
    if((tailDist=size-index)==0)
    {
      uncheckedAppend(size,val);
    }
    else
    {
      float[] arr;
      if((arr=this.arr).length==size)
      {
        float[] newArr;
        ArrCopy.semicheckedCopy(arr,0,newArr=new float[OmniArray.growBy50Pct(size)],0,index);
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
  public void push(Float val)
  {
    push((float)val);
  }
  private float uncheckedremoveAtIndex(int index,int size)
  {
    final float[] arr;
    final float ret=(float)(arr=this.arr)[index];
    ArrCopy.semicheckedSelfCopy(arr,index+1,index,(--size)-index);
    this.size=size;
    return ret;
  }
  public
  static class UncheckedStack
    extends FloatArrSeq
    implements OmniStack.OfFloat
  {
    public UncheckedStack()
    {
      super();
    }
    public UncheckedStack(int initialCapacity)
    {
      super(initialCapacity);
    }
    private UncheckedStack(int size,float[] arr)
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
      final float[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new float[size],0,size);
      }
      else
      {
        copy=OmniArray.OfFloat.DEFAULT_ARR;
      }
      return new UncheckedStack(size,copy);
    }
    @Override
    public OmniIterator.OfFloat iterator()
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
    void uncheckedCopyInto(float[] dst,int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(Float[] dst,int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(double[] dst,int size)
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
      public float nextFloat()
      {
            return (float)parent.arr[this.cursor--];
      }
      @Override
      public void forEachRemaining(FloatConsumer action)
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
      public void forEachRemaining(Consumer<? super Float> action)
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
    uncheckedremoveValBits(int size
    ,int bits
    )
    {
      final var arr=this.arr;
      for(int i=--size;;)
      {
        if(
        bits==Float.floatToRawIntBits(arr[i])
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
    boolean
    uncheckedremoveVal0(int size
    )
    {
      final var arr=this.arr;
      for(int i=--size;;)
      {
        if(
        0==arr[i]
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
    boolean
    uncheckedremoveValNaN(int size
    )
    {
      final var arr=this.arr;
      for(int i=--size;;)
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
     @Override
     public void forEach(FloatConsumer action)
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
    public void forEach(Consumer<? super Float> action)
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
    public float popFloat()
    {
      return (float)arr[--this.size];
    }
    @Override
    public float pollFloat()
    {
      int size;
      if((size=this.size)!=0)
      {
        final float ret=(float)((float)arr[--size]);
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
        final Float ret=(Float)((float)arr[--size]);
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
        final double ret=(double)((float)arr[--size]);
        this.size=size;
        return ret;
      }
      return Double.NaN;
    }
    @Override
    public Float pop()
    {
      return popFloat();
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
          return uncheckedsearchBits(size,TypeUtil.FLT_TRUE_BITS);
        }
        return uncheckedsearch0(size);
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
            return uncheckedsearchBits(size,Float.floatToRawIntBits(val));
          }
        }
        else
        {
          return uncheckedsearch0(size);
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
            return uncheckedsearchBits(size,Float.floatToRawIntBits(val));
          }
        }
        else
        {
          return uncheckedsearch0(size);
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
          return uncheckedsearchBits(size,Float.floatToRawIntBits(val));
        }
        return uncheckedsearchNaN(size);
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
          return uncheckedsearchBits(size,Float.floatToRawIntBits(v));
        }
        else if(v!=v)
        {
          return uncheckedsearchNaN(size);
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
            return uncheckedsearchBits(size,Float.floatToRawIntBits(v));
          }
          return uncheckedsearchNaN(size);
        }
      }
      return -1;
    }
    @Override
    public
    int
    search(char val)
    {
      {
        int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            return uncheckedsearchBits(size,Float.floatToRawIntBits(val));
          }
          return uncheckedsearch0(size);
        }
      }
      return -1;
    }
    @Override
    public
    int
    search(short val)
    {
      {
        int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            return uncheckedsearchBits(size,Float.floatToRawIntBits(val));
          }
          return uncheckedsearch0(size);
        }
      }
      return -1;
    }
    public float peekFloat()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (float)((float)arr[size-1]);
      }
      return Float.NaN;
    }
    public Float peek()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (Float)((float)arr[size-1]);
      }
      return null;
    }
    public double peekDouble()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (double)((float)arr[size-1]);
      }
      return Double.NaN;
    }
    private int uncheckedsearchBits(int size
    ,int bits
    )
    {
      final var arr=this.arr;
      for(int i=size-1;;--i)
      {
        if(
        bits==Float.floatToRawIntBits(arr[i])
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
    private int uncheckedsearch0(int size
    )
    {
      final var arr=this.arr;
      for(int i=size-1;;--i)
      {
        if(
        0==arr[i]
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
    private int uncheckedsearchNaN(int size
    )
    {
      final var arr=this.arr;
      for(int i=size-1;;--i)
      {
        if(
        Float.isNaN(arr[i])
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
    void uncheckedForEach(int size,FloatConsumer action)
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
      final float[] arr;
      int hash;
      for(hash=31+HashUtil.hashFloat((arr=this.arr)[--size]);size!=0;hash=hash*31+HashUtil.hashFloat(arr[--size])){}
      return hash;
    }
    @Override
    int uncheckedToString(int size,char[] buffer)
    {
      final float[] arr;
      int bufferOffset;
      for(bufferOffset=ToStringUtil.getStringFloat((arr=this.arr)[--size],buffer,1);size!=0;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringFloat(arr[--size],buffer,bufferOffset)){}
      return bufferOffset;
    }
    @Override
    void uncheckedToString(int size,ToStringUtil.OmniStringBuilder builder)
    {
      final float[] arr;
      for(builder.uncheckedAppendFloat((arr=this.arr)[--size]);size!=0;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendFloat(arr[--size])){}
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
    private CheckedStack(int size,float[] arr)
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
      final float[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new float[size],0,size);
      }
      else
      {
        copy=OmniArray.OfFloat.DEFAULT_ARR;
      }
      return new CheckedStack(size,copy);
    }
    @Override
    public OmniIterator.OfFloat iterator()
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
      public float nextFloat()
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
            return (float)root.arr[cursor];
      }
      @Override
      public void forEachRemaining(FloatConsumer action)
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
      public void forEachRemaining(Consumer<? super Float> action)
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
    uncheckedremoveValBits(int size
    ,int bits
    )
    {
      final var arr=this.arr;
      for(int i=--size;;)
      {
        if(
        bits==Float.floatToRawIntBits(arr[i])
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
    boolean
    uncheckedremoveVal0(int size
    )
    {
      final var arr=this.arr;
      for(int i=--size;;)
      {
        if(
        0==arr[i]
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
    boolean
    uncheckedremoveValNaN(int size
    )
    {
      final var arr=this.arr;
      for(int i=--size;;)
      {
        if(
        Float.isNaN(arr[i])
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
     public void forEach(FloatConsumer action)
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
    public void forEach(Consumer<? super Float> action)
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
    public float popFloat()
    {
      int size;
      if((size=this.size)!=0)
      {
        ++this.modCount;
        final float ret=(float)arr[--size];
        this.size=size;
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override
    public float pollFloat()
    {
      int size;
      if((size=this.size)!=0)
      {
        final float ret=(float)((float)arr[--size]);
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
        final Float ret=(Float)((float)arr[--size]);
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
        final double ret=(double)((float)arr[--size]);
        ++this.modCount;
        this.size=size;
        return ret;
      }
      return Double.NaN;
    }
    @Override
    void uncheckedForEach(int size,FloatConsumer action)
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
    public boolean removeIf(FloatPredicate filter)
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
    public boolean removeIf(Predicate<? super Float> filter)
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
    public void push(float val)
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
    extends FloatArrSeq
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
    private UncheckedList(int size,float[] arr)
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
      final float[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new float[size],0,size);
      }
      else
      {
        copy=OmniArray.OfFloat.DEFAULT_ARR;
      }
      return new UncheckedList(size,copy);
    }
    @Override
    public OmniIterator.OfFloat iterator()
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
    void uncheckedCopyInto(float[] dst,int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(Float[] dst,int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(double[] dst,int size)
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
      public float nextFloat()
      {
            return (float)parent.arr[this.cursor++];
      }
      @Override
      public void forEachRemaining(FloatConsumer action)
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
      public void forEachRemaining(Consumer<? super Float> action)
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
    uncheckedremoveValBits(int size
    ,int bits
    )
    {
      final var arr=this.arr;
      for(int i=0;;)
      {
        if(
        bits==Float.floatToRawIntBits(arr[i])
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
    boolean
    uncheckedremoveVal0(int size
    )
    {
      final var arr=this.arr;
      for(int i=0;;)
      {
        if(
        0==arr[i]
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
    boolean
    uncheckedremoveValNaN(int size
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
     @Override
     public void forEach(FloatConsumer action)
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
    public void forEach(Consumer<? super Float> action)
    {
      int size;
      if((size=this.size)!=0)
      {
        {
          uncheckedForEachAscending(arr,0,size,action::accept);
        }
      }
    }
    private void uncheckedAdd(int index,float val,int size)
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
    private void sublistForEach(int rootOffset,int size,FloatConsumer action)
    {
      final var arr=this.arr;
      for(size+=rootOffset;;)
      {
        action.accept((float)arr[rootOffset]);
        if(++rootOffset==size)
        {
          return;
        }
      }
    }
    */
    private void uncheckedReplaceAll(int offset,int bound,FloatUnaryOperator operator)
    {
      final var arr=this.arr;
      for(;;)
      {
        arr[offset]=operator.applyAsFloat((float)arr[offset]);
        if(++offset==bound)
        {
          return;
        }
      }
    }
    private boolean sublistcontainsBits(int rootOffset,int size
    ,int bits
    )
    {
      final var arr=this.arr;
      for(size+=rootOffset;;)
      {
        if(
        bits==Float.floatToRawIntBits(arr[rootOffset])
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
    private boolean sublistcontains0(int rootOffset,int size
    )
    {
      final var arr=this.arr;
      for(size+=rootOffset;;)
      {
        if(
        0==arr[rootOffset]
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
    private boolean sublistcontainsNaN(int rootOffset,int size
    )
    {
      final var arr=this.arr;
      for(size+=rootOffset;;)
      {
        if(
        Float.isNaN(arr[rootOffset])
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
    private int sublistindexOfBits(int rootOffset,int size
    ,int bits
    )
    {
      final var arr=this.arr;
      int i;
      for(size+=(i=rootOffset);;)
      {
        if(
        bits==Float.floatToRawIntBits(arr[i])
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
    private int uncheckedindexOfBits(int size
    ,int bits
    )
    {
      final var arr=this.arr;
      for(int i=0;;)
      {
        if(
        bits==Float.floatToRawIntBits(arr[i])
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
    private int sublistindexOf0(int rootOffset,int size
    )
    {
      final var arr=this.arr;
      int i;
      for(size+=(i=rootOffset);;)
      {
        if(
        0==arr[i]
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
    private int uncheckedindexOf0(int size
    )
    {
      final var arr=this.arr;
      for(int i=0;;)
      {
        if(
        0==arr[i]
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
    private int sublistindexOfNaN(int rootOffset,int size
    )
    {
      final var arr=this.arr;
      int i;
      for(size+=(i=rootOffset);;)
      {
        if(
        Float.isNaN(arr[i])
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
    private int uncheckedindexOfNaN(int size
    )
    {
      final var arr=this.arr;
      for(int i=0;;)
      {
        if(
        Float.isNaN(arr[i])
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
    private int sublistlastIndexOfBits(int rootOffset,int size
    ,int bits
    )
    {
      final var arr=this.arr;
      for(size+=(rootOffset-1);;--size)
      {
        if(
        bits==Float.floatToRawIntBits(arr[size])
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
    private int uncheckedlastIndexOfBits(int size
    ,int bits
    )
    {
      final var arr=this.arr;
      for(;;)
      {
        if(
        bits==Float.floatToRawIntBits(arr[--size])
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
    private int sublistlastIndexOf0(int rootOffset,int size
    )
    {
      final var arr=this.arr;
      for(size+=(rootOffset-1);;--size)
      {
        if(
        0==arr[size]
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
    private int uncheckedlastIndexOf0(int size
    )
    {
      final var arr=this.arr;
      for(;;)
      {
        if(
        0==arr[--size]
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
    private int sublistlastIndexOfNaN(int rootOffset,int size
    )
    {
      final var arr=this.arr;
      for(size+=(rootOffset-1);;--size)
      {
        if(
        Float.isNaN(arr[size])
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
    private int uncheckedlastIndexOfNaN(int size
    )
    {
      final var arr=this.arr;
      for(;;)
      {
        if(
        Float.isNaN(arr[--size])
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
    void uncheckedForEach(int size,FloatConsumer action)
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
      final float[] arr;
      int hash;
      int i;
      for(hash=31+HashUtil.hashFloat((arr=this.arr)[i=0]);++i!=size;hash=hash*31+HashUtil.hashFloat(arr[i])){}
      return hash;
    }
    @Override
    int uncheckedToString(int size,char[] buffer)
    {
      final float[] arr;
      int bufferOffset;
      int i;
      for(bufferOffset=ToStringUtil.getStringFloat((arr=this.arr)[i=0],buffer,1);++i!=size;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringFloat(arr[i],buffer,bufferOffset)){}
      return bufferOffset;
    }
    @Override
    void uncheckedToString(int size,ToStringUtil.OmniStringBuilder builder)
    {
      final float[] arr;
      int i;
      for(builder.uncheckedAppendFloat((arr=this.arr)[i=0]);++i!=size;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendFloat(arr[i])){}
    }
     private static class LstItr
       extends Itr implements OmniListIterator.OfFloat
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
       public float nextFloat()
       {
             final int ret;
             this.lastRet=ret=this.cursor++;
             return (float)parent.arr[ret];
       }
       @Override
       public void forEachRemaining(FloatConsumer action)
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
       public void forEachRemaining(Consumer<? super Float> action)
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
       public float previousFloat()
       {
             final int ret;
             this.lastRet=ret=--this.cursor;
             return (float)parent.arr[ret];
       }
       @Override
       public void add(float val)
       {
         //TODO implement LstItr.add(float)
       }
       @Override
       public void set(float val)
       {
         //TODO implement LstItr.set(float)
       }
     }
    @Override
    public OmniList.OfFloat subList(int fromIndex,int toIndex)
    {
      return new UncheckedSubList(this,fromIndex,toIndex-fromIndex);
    }
    @Override
    public OmniListIterator.OfFloat listIterator()
    {
      return new LstItr(this);
    }
    @Override
    public OmniListIterator.OfFloat listIterator(int index)
    {
      return new LstItr(this,index);
    }
    @Override
    public void sort(FloatComparator sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        {
          if(sorter==null)
          {
            FloatSortUtil.uncheckedAscendingSort(this.arr,0,size);
          }
          else
          {
            FloatSortUtil.uncheckedStableSort(this.arr,0,size,sorter);
          }
        }
      }
    }
    @Override
    public void sort(Comparator<? super Float> sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        {
          if(sorter==null)
          {
            FloatSortUtil.uncheckedAscendingSort(this.arr,0,size);
          }
          else
          {
            FloatSortUtil.uncheckedStableSort(this.arr,0,size,sorter::compare);
          }
        }
      }
    }
    @Override
    public void unstableSort(FloatComparator sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        {
          if(sorter==null)
          {
            FloatSortUtil.uncheckedAscendingSort(this.arr,0,size);
          }
          else
          {
            FloatSortUtil.uncheckedUnstableSort(this.arr,0,size,sorter);
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
          FloatSortUtil.uncheckedAscendingSort(this.arr,0,size);
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
          FloatSortUtil.uncheckedDescendingSort(this.arr,0,size);
        }
      }
    }
    @Override
    public void replaceAll(FloatUnaryOperator operator)
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
    public void replaceAll(UnaryOperator<Float> operator)
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
    public void add(int index,float val)
    {
      ((UncheckedList)this).uncheckedAdd(index,val,size);
    }
    @Override
    public float getFloat(int index)
    {
      return (float)arr[index];
    }
    @Override
    public void put(int index,float val)
    {
      arr[index]=val;
    }
    @Override
    public float set(int index,float val)
    {
      final float[] arr;
      final float ret=(float)(arr=this.arr)[index];
      arr[index]=val;
      return ret;
    }
    @Override
    public float removeFloatAt(int index)
    {
      return ((FloatArrSeq)this).uncheckedremoveAtIndex(index,size);
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
        if(val)
        {
          return uncheckedindexOfBits(size,TypeUtil.FLT_TRUE_BITS);
        }
        return uncheckedindexOf0(size);
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
            return uncheckedindexOfBits(size,Float.floatToRawIntBits(val));
          }
        }
        else
        {
          return uncheckedindexOf0(size);
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
            return uncheckedindexOfBits(size,Float.floatToRawIntBits(val));
          }
        }
        else
        {
          return uncheckedindexOf0(size);
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
          return uncheckedindexOfBits(size,Float.floatToRawIntBits(val));
        }
        return uncheckedindexOfNaN(size);
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
          return uncheckedindexOfBits(size,Float.floatToRawIntBits(v));
        }
        else if(v!=v)
        {
          return uncheckedindexOfNaN(size);
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
            return uncheckedindexOfBits(size,Float.floatToRawIntBits(v));
          }
          return uncheckedindexOfNaN(size);
        }
      }
      return -1;
    }
    @Override
    public
    int
    indexOf(char val)
    {
      {
        int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            return uncheckedindexOfBits(size,Float.floatToRawIntBits(val));
          }
          return uncheckedindexOf0(size);
        }
      }
      return -1;
    }
    @Override
    public
    int
    indexOf(short val)
    {
      {
        int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            return uncheckedindexOfBits(size,Float.floatToRawIntBits(val));
          }
          return uncheckedindexOf0(size);
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
        if(val)
        {
          return uncheckedlastIndexOfBits(size,TypeUtil.FLT_TRUE_BITS);
        }
        return uncheckedlastIndexOf0(size);
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
            return uncheckedlastIndexOfBits(size,Float.floatToRawIntBits(val));
          }
        }
        else
        {
          return uncheckedlastIndexOf0(size);
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
            return uncheckedlastIndexOfBits(size,Float.floatToRawIntBits(val));
          }
        }
        else
        {
          return uncheckedlastIndexOf0(size);
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
          return uncheckedlastIndexOfBits(size,Float.floatToRawIntBits(val));
        }
        return uncheckedlastIndexOfNaN(size);
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
          return uncheckedlastIndexOfBits(size,Float.floatToRawIntBits(v));
        }
        else if(v!=v)
        {
          return uncheckedlastIndexOfNaN(size);
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
            return uncheckedlastIndexOfBits(size,Float.floatToRawIntBits(v));
          }
          return uncheckedlastIndexOfNaN(size);
        }
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf(char val)
    {
      {
        int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            return uncheckedlastIndexOfBits(size,Float.floatToRawIntBits(val));
          }
          return uncheckedlastIndexOf0(size);
        }
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf(short val)
    {
      {
        int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            return uncheckedlastIndexOfBits(size,Float.floatToRawIntBits(val));
          }
          return uncheckedlastIndexOf0(size);
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
    private CheckedList(int size,float[] arr)
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
      final float[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new float[size],0,size);
      }
      else
      {
        copy=OmniArray.OfFloat.DEFAULT_ARR;
      }
      return new CheckedList(size,copy);
    }
    @Override
    public OmniIterator.OfFloat iterator()
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
      public float nextFloat()
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
            return (float)root.arr[cursor];
      }
      @Override
      public void forEachRemaining(FloatConsumer action)
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
      public void forEachRemaining(Consumer<? super Float> action)
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
    uncheckedremoveValBits(int size
    ,int bits
    )
    {
      final var arr=this.arr;
      for(int i=0;;)
      {
        if(
        bits==Float.floatToRawIntBits(arr[i])
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
    boolean
    uncheckedremoveVal0(int size
    )
    {
      final var arr=this.arr;
      for(int i=0;;)
      {
        if(
        0==arr[i]
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
    boolean
    uncheckedremoveValNaN(int size
    )
    {
      final var arr=this.arr;
      for(int i=0;;)
      {
        if(
        Float.isNaN(arr[i])
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
     public void forEach(FloatConsumer action)
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
    public void forEach(Consumer<? super Float> action)
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
    void uncheckedForEach(int size,FloatConsumer action)
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
    public boolean removeIf(FloatPredicate filter)
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
    public boolean removeIf(Predicate<? super Float> filter)
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
    public void push(float val)
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
       extends Itr implements OmniListIterator.OfFloat
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
       public float previousFloat()
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
             return (float)root.arr[cursor];
       }
       @Override
       public void add(float val)
       {
         //TODO implement LstItr.add(float)
       }
       @Override
       public void set(float val)
       {
         //TODO implement LstItr.set(float)
       }
     }
    @Override
    public OmniList.OfFloat subList(int fromIndex,int toIndex)
    {
      int span;
      if((span=toIndex-fromIndex)<0|| fromIndex<0 ||  toIndex>this.size)
      {
        throw new InvalidReadIndexException("fromIndex="+fromIndex+"; toIndex="+toIndex+"; size="+this.size);
      }
      return new CheckedSubList(this,fromIndex,span,modCount);
    }
    @Override
    public OmniListIterator.OfFloat listIterator()
    {
      return new LstItr(this);
    }
    @Override
    public OmniListIterator.OfFloat listIterator(int index)
    {
      if(index<0 || size<index)
      {
        throw new InvalidWriteIndexException(index,size);
      }
      return new LstItr(this,index);
    }
    @Override
    public void sort(FloatComparator sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        {
          if(sorter==null)
          {
            FloatSortUtil.uncheckedAscendingSort(this.arr,0,size);
          }
          else
          {
            FloatSortUtil.uncheckedStableSort(this.arr,0,size,sorter);
          }
          ++this.modCount;
        }
      }
    }
    @Override
    public void sort(Comparator<? super Float> sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        {
          if(sorter==null)
          {
            FloatSortUtil.uncheckedAscendingSort(this.arr,0,size);
          }
          else
          {
            FloatSortUtil.uncheckedStableSort(this.arr,0,size,sorter::compare);
          }
          ++this.modCount;
        }
      }
    }
    @Override
    public void unstableSort(FloatComparator sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        {
          if(sorter==null)
          {
            FloatSortUtil.uncheckedAscendingSort(this.arr,0,size);
          }
          else
          {
            FloatSortUtil.uncheckedUnstableSort(this.arr,0,size,sorter);
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
          FloatSortUtil.uncheckedAscendingSort(this.arr,0,size);
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
          FloatSortUtil.uncheckedDescendingSort(this.arr,0,size);
          ++this.modCount;
        }
      }
    }
    @Override
    public void replaceAll(FloatUnaryOperator operator)
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
    public void replaceAll(UnaryOperator<Float> operator)
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
    public void add(int index,float val)
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
    public float getFloat(int index)
    {
      if(index<0 || index>=size)
      {
        throw new InvalidReadIndexException(index,size);
      }
      return (float)arr[index];
    }
    @Override
    public void put(int index,float val)
    {
      if(index<0 || index>=size)
      {
        throw new InvalidWriteIndexException(index,size);
      }
      arr[index]=val;
    }
    @Override
    public float set(int index,float val)
    {
      if(index<0 || index>=size)
      {
        throw new InvalidReadIndexException(index,size);
      }
      final float[] arr;
      final float ret=(float)(arr=this.arr)[index];
      arr[index]=val;
      return ret;
    }
    @Override
    public float removeFloatAt(int index)
    {
      int size;
      if((size=this.size)<=index || index<0)
      {
        throw new InvalidWriteIndexException(index,size);
      }
      ++this.modCount;
      return ((FloatArrSeq)this).uncheckedremoveAtIndex(index,size);
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
      final float[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new float[size],0,size);
      }
      else
      {
        copy=OmniArray.OfFloat.DEFAULT_ARR;
      }
      return new UncheckedList(size,copy);
    }
    @Override
    public OmniIterator.OfFloat iterator()
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
    public Float[] toArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final Float[] copy;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new Float[size],0,size);
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
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new double[size],0,size);
        return copy;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
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
      public float nextFloat()
      {
            return (float)parent.root.arr[this.cursor++];
      }
      @Override
      public void forEachRemaining(FloatConsumer action)
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
      public void forEachRemaining(Consumer<? super Float> action)
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
    uncheckedremoveValBits(int size
    ,int bits
    )
    {
      final UncheckedList root;
      final var arr=(root=this.root).arr;
      {
        for(int i=this.rootOffset,bound=i+size;;)
        {
          if(
          bits==Float.floatToRawIntBits(arr[i])
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
    private boolean
    uncheckedremoveVal0(int size
    )
    {
      final UncheckedList root;
      final var arr=(root=this.root).arr;
      {
        for(int i=this.rootOffset,bound=i+size;;)
        {
          if(
          0==arr[i]
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
    private boolean
    uncheckedremoveValNaN(int size
    )
    {
      final UncheckedList root;
      final var arr=(root=this.root).arr;
      {
        for(int i=this.rootOffset,bound=i+size;;)
        {
          if(
          Float.isNaN(arr[i])
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
     public void forEach(FloatConsumer action)
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
    public void forEach(Consumer<? super Float> action)
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
    public boolean removeIf(FloatPredicate filter)
    {
      int size;
      if((size=this.size)!=0)
      {
        int newBound;
        final float[] arr;
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
    public boolean removeIf(Predicate<? super Float> filter)
    {
      int size;
      if((size=this.size)!=0)
      {
        int newBound;
        final float[] arr;
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
        if(val)
        {
          return ((UncheckedList)root).sublistcontainsBits(this.rootOffset,size,TypeUtil.FLT_TRUE_BITS);
        }
        return ((UncheckedList)root).sublistcontains0(this.rootOffset,size);
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
            return ((UncheckedList)root).sublistcontainsBits(this.rootOffset,size,Float.floatToRawIntBits(val));
          }
        }
        else
        {
          return ((UncheckedList)root).sublistcontains0(this.rootOffset,size);
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
            return ((UncheckedList)root).sublistcontainsBits(this.rootOffset,size,Float.floatToRawIntBits(val));
          }
        }
        else
        {
          return ((UncheckedList)root).sublistcontains0(this.rootOffset,size);
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
          return ((UncheckedList)root).sublistcontainsBits(this.rootOffset,size,Float.floatToRawIntBits(val));
        }
        return ((UncheckedList)root).sublistcontainsNaN(this.rootOffset,size);
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
          return ((UncheckedList)root).sublistcontainsBits(this.rootOffset,size,Float.floatToRawIntBits(v));
        }
        else if(v!=v)
        {
          return ((UncheckedList)root).sublistcontainsNaN(this.rootOffset,size);
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
            return ((UncheckedList)root).sublistcontainsBits(this.rootOffset,size,Float.floatToRawIntBits(v));
          }
          return ((UncheckedList)root).sublistcontainsNaN(this.rootOffset,size);
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
          if(val!=0)
          {
            return ((UncheckedList)root).sublistcontainsBits(this.rootOffset,size,Float.floatToRawIntBits(val));
          }
          return ((UncheckedList)root).sublistcontains0(this.rootOffset,size);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    contains(char val)
    {
      {
        int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            return ((UncheckedList)root).sublistcontainsBits(this.rootOffset,size,Float.floatToRawIntBits(val));
          }
          return ((UncheckedList)root).sublistcontains0(this.rootOffset,size);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    contains(short val)
    {
      {
        int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            return ((UncheckedList)root).sublistcontainsBits(this.rootOffset,size,Float.floatToRawIntBits(val));
          }
          return ((UncheckedList)root).sublistcontains0(this.rootOffset,size);
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
        final float[] arr;
        int hash;
        int i;
        for(hash=31+HashUtil.hashFloat((arr=root.arr)[i=this.rootOffset]),size+=i;++i!=size;hash=hash*31+HashUtil.hashFloat(arr[i])){}
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
        if(val)
        {
          return uncheckedremoveValBits(size,TypeUtil.FLT_TRUE_BITS);
        }
        return uncheckedremoveVal0(size);
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
            return uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
          }
        }
        else
        {
          return uncheckedremoveVal0(size);
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
            return uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
          }
        }
        else
        {
          return uncheckedremoveVal0(size);
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
          return uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
        }
        return uncheckedremoveValNaN(size);
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
          return uncheckedremoveValBits(size,Float.floatToRawIntBits(v));
        }
        else if(v!=v)
        {
          return uncheckedremoveValNaN(size);
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
            return uncheckedremoveValBits(size,Float.floatToRawIntBits(v));
          }
          return uncheckedremoveValNaN(size);
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
          if(val!=0)
          {
            return uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
          }
          return uncheckedremoveVal0(size);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    removeVal(char val)
    {
      {
        int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            return uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
          }
          return uncheckedremoveVal0(size);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    removeVal(short val)
    {
      {
        int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            return uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
          }
          return uncheckedremoveVal0(size);
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
        if(size>(Integer.MAX_VALUE/5))
        {
          throw new OutOfMemoryError();
        }
        final char[] buffer;
        if(size<=(OmniArray.MAX_ARR_SIZE/((17)<<1)))
        {
          final float[] arr;
          int bufferOffset,i;
          for(bufferOffset=ToStringUtil.getStringFloat((arr=root.arr)[i=this.rootOffset],buffer=new char[size*(17)],1),size+=i;++i!=size;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringFloat(arr[i],buffer,bufferOffset)){}
          buffer[bufferOffset]=']';
          buffer[0]='[';
          return new String(buffer,0,bufferOffset+1);
        }
        else
        {
          final ToStringUtil.OmniStringBuilder builder;
          final float[] arr;
          int i;
          for((builder=new ToStringUtil.OmniStringBuilder(1,new char[size<=(int)(OmniArray.MAX_ARR_SIZE/11)?(size*11):OmniArray.MAX_ARR_SIZE])).uncheckedAppendFloat((arr=root.arr)[i=this.rootOffset]),size+=i;++i!=size;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendFloat(arr[i])){}
          builder.uncheckedAppendChar(']');
          (buffer=builder.buffer)[0]='[';
          return new String(buffer,0,builder.size);
        }
      }
      return "[]";
    }
    @Override
    public boolean add(float val)
    {
      int rootSize;
      final UncheckedList root;
      if((rootSize=(root=this.root).size)!=0)
      {
        for(var curr=parent;curr!=null;++curr.size,curr=curr.parent){}
        ((FloatArrSeq)root).uncheckedInsert(rootSize,this.rootOffset,val);
        ++this.size;
      }
      else
      {
        for(var curr=parent;curr!=null;curr.size=1,curr=curr.parent){}
        ((FloatArrSeq)root).uncheckedInit(val);
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
       extends Itr implements OmniListIterator.OfFloat
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
       public float nextFloat()
       {
             final int ret;
             this.lastRet=ret=this.cursor++;
             return (float)parent.root.arr[ret];
       }
       @Override
       public void forEachRemaining(FloatConsumer action)
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
       public void forEachRemaining(Consumer<? super Float> action)
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
       public float previousFloat()
       {
             final int ret;
             this.lastRet=ret=--this.cursor;
             return (float)parent.root.arr[ret];
       }
       @Override
       public void add(float val)
       {
         //TODO implement LstItr.add(float)
       }
       @Override
       public void set(float val)
       {
         //TODO implement LstItr.set(float)
       }
     }
    @Override
    public OmniList.OfFloat subList(int fromIndex,int toIndex)
    {
      return new UncheckedSubList(this,this.rootOffset+fromIndex,toIndex-fromIndex);
    }
    @Override
    public OmniListIterator.OfFloat listIterator()
    {
      return new LstItr(this);
    }
    @Override
    public OmniListIterator.OfFloat listIterator(int index)
    {
      return new LstItr(this,index+this.rootOffset);
    }
    @Override
    public void sort(FloatComparator sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        {
          if(sorter==null)
          {
            final int rootOffset;
            FloatSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
          }
          else
          {
            final int rootOffset;
            FloatSortUtil.uncheckedStableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter);
          }
        }
      }
    }
    @Override
    public void sort(Comparator<? super Float> sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        {
          if(sorter==null)
          {
            final int rootOffset;
            FloatSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
          }
          else
          {
            final int rootOffset;
            FloatSortUtil.uncheckedStableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter::compare);
          }
        }
      }
    }
    @Override
    public void unstableSort(FloatComparator sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        {
          if(sorter==null)
          {
            final int rootOffset;
            FloatSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
          }
          else
          {
            final int rootOffset;
            FloatSortUtil.uncheckedUnstableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter);
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
          FloatSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
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
          FloatSortUtil.uncheckedDescendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }
      }
    }
    @Override
    public void replaceAll(FloatUnaryOperator operator)
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
    public void replaceAll(UnaryOperator<Float> operator)
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
    public void add(int index,float val)
    {
      int rootSize;
      final UncheckedList root;
      if((rootSize=(root=this.root).size)!=0)
      {
        for(var curr=parent;curr!=null;++curr.size,curr=curr.parent){}
        ((FloatArrSeq)root).uncheckedInsert(rootSize,index+this.rootOffset,val);
        ++this.size;
      }
      else
      {
        for(var curr=parent;curr!=null;curr.size=1,curr=curr.parent){}
        ((FloatArrSeq)root).uncheckedInit(val);
        this.size=1;
      }
    }
    @Override
    public float getFloat(int index)
    {
      return (float)root.arr[index+this.rootOffset];
    }
    @Override
    public void put(int index,float val)
    {
      root.arr[index+this.rootOffset]=val;
    }
    @Override
    public float set(int index,float val)
    {
      final float[] arr;
      final float ret=(float)(arr=root.arr)[index+=this.rootOffset];
      arr[index]=val;
      return ret;
    }
    @Override
    public float removeFloatAt(int index)
    {
      for(var curr=parent;curr!=null;--curr.size,curr=curr.parent){}
      --this.size;
      final FloatArrSeq root;
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
        if(val)
        {
          return ((UncheckedList)root).sublistindexOfBits(this.rootOffset,size,TypeUtil.FLT_TRUE_BITS);
        }
        return ((UncheckedList)root).sublistindexOf0(this.rootOffset,size);
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
            return ((UncheckedList)root).sublistindexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
          }
        }
        else
        {
          return ((UncheckedList)root).sublistindexOf0(this.rootOffset,size);
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
            return ((UncheckedList)root).sublistindexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
          }
        }
        else
        {
          return ((UncheckedList)root).sublistindexOf0(this.rootOffset,size);
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
          return ((UncheckedList)root).sublistindexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
        }
        return ((UncheckedList)root).sublistindexOfNaN(this.rootOffset,size);
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
          return ((UncheckedList)root).sublistindexOfBits(this.rootOffset,size,Float.floatToRawIntBits(v));
        }
        else if(v!=v)
        {
          return ((UncheckedList)root).sublistindexOfNaN(this.rootOffset,size);
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
            return ((UncheckedList)root).sublistindexOfBits(this.rootOffset,size,Float.floatToRawIntBits(v));
          }
          return ((UncheckedList)root).sublistindexOfNaN(this.rootOffset,size);
        }
      }
      return -1;
    }
    @Override
    public
    int
    indexOf(char val)
    {
      {
        int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            return ((UncheckedList)root).sublistindexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
          }
          return ((UncheckedList)root).sublistindexOf0(this.rootOffset,size);
        }
      }
      return -1;
    }
    @Override
    public
    int
    indexOf(short val)
    {
      {
        int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            return ((UncheckedList)root).sublistindexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
          }
          return ((UncheckedList)root).sublistindexOf0(this.rootOffset,size);
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
        if(val)
        {
          return ((UncheckedList)root).sublistlastIndexOfBits(this.rootOffset,size,TypeUtil.FLT_TRUE_BITS);
        }
        return ((UncheckedList)root).sublistlastIndexOf0(this.rootOffset,size);
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
            return ((UncheckedList)root).sublistlastIndexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
          }
        }
        else
        {
          return ((UncheckedList)root).sublistlastIndexOf0(this.rootOffset,size);
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
            return ((UncheckedList)root).sublistlastIndexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
          }
        }
        else
        {
          return ((UncheckedList)root).sublistlastIndexOf0(this.rootOffset,size);
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
          return ((UncheckedList)root).sublistlastIndexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
        }
        return ((UncheckedList)root).sublistlastIndexOfNaN(this.rootOffset,size);
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
          return ((UncheckedList)root).sublistlastIndexOfBits(this.rootOffset,size,Float.floatToRawIntBits(v));
        }
        else if(v!=v)
        {
          return ((UncheckedList)root).sublistlastIndexOfNaN(this.rootOffset,size);
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
            return ((UncheckedList)root).sublistlastIndexOfBits(this.rootOffset,size,Float.floatToRawIntBits(v));
          }
          return ((UncheckedList)root).sublistlastIndexOfNaN(this.rootOffset,size);
        }
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf(char val)
    {
      {
        int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            return ((UncheckedList)root).sublistlastIndexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
          }
          return ((UncheckedList)root).sublistlastIndexOf0(this.rootOffset,size);
        }
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf(short val)
    {
      {
        int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            return ((UncheckedList)root).sublistlastIndexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
          }
          return ((UncheckedList)root).sublistlastIndexOf0(this.rootOffset,size);
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
      final float[] copy;
      final int size;
      final var root=checkModCountAndGetRoot();
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new float[size],0,size);
      }
      else
      {
        copy=OmniArray.OfFloat.DEFAULT_ARR;
      }
      return new CheckedList(size,copy);
    }
    @Override
    public OmniIterator.OfFloat iterator()
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
    public Float[] toArray()
    {
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0)
      {
        final Float[] copy;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new Float[size],0,size);
        return copy;
      }
      return OmniArray.OfFloat.DEFAULT_BOXED_ARR;
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
      public float nextFloat()
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
            return (float)root.arr[cursor];
      }
      @Override
      public void forEachRemaining(FloatConsumer action)
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
      public void forEachRemaining(Consumer<? super Float> action)
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
    uncheckedremoveValBits(int size
    ,int bits
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
          bits==Float.floatToRawIntBits(arr[i])
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
    private boolean
    uncheckedremoveVal0(int size
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
          0==arr[i]
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
    private boolean
    uncheckedremoveValNaN(int size
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
          Float.isNaN(arr[i])
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
     public void forEach(FloatConsumer action)
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
    public void forEach(Consumer<? super Float> action)
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
    public boolean removeIf(FloatPredicate filter)
    {
      int size;
      if((size=this.size)!=0)
      {
        int newBound;
        final float[] arr;
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
    public boolean removeIf(Predicate<? super Float> filter)
    {
      int size;
      if((size=this.size)!=0)
      {
        int newBound;
        final float[] arr;
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
        if(val)
        {
          return ((UncheckedList)root).sublistcontainsBits(this.rootOffset,size,TypeUtil.FLT_TRUE_BITS);
        }
        return ((UncheckedList)root).sublistcontains0(this.rootOffset,size);
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
        if(val!=0)
        {
          if(TypeUtil.checkCastToFloat(val))
          {
            return ((UncheckedList)root).sublistcontainsBits(this.rootOffset,size,Float.floatToRawIntBits(val));
          }
        }
        else
        {
          return ((UncheckedList)root).sublistcontains0(this.rootOffset,size);
        }
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
        if(val!=0)
        {
          if(TypeUtil.checkCastToFloat(val))
          {
            return ((UncheckedList)root).sublistcontainsBits(this.rootOffset,size,Float.floatToRawIntBits(val));
          }
        }
        else
        {
          return ((UncheckedList)root).sublistcontains0(this.rootOffset,size);
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
        if(val==val)
        {
          return ((UncheckedList)root).sublistcontainsBits(this.rootOffset,size,Float.floatToRawIntBits(val));
        }
        return ((UncheckedList)root).sublistcontainsNaN(this.rootOffset,size);
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
        final float v;
        if((v=(float)val)==val)
        {
          return ((UncheckedList)root).sublistcontainsBits(this.rootOffset,size,Float.floatToRawIntBits(v));
        }
        else if(v!=v)
        {
          return ((UncheckedList)root).sublistcontainsNaN(this.rootOffset,size);
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
        if(val instanceof Float)
        {
          final float v;
          if((v=(float)val)==v)
          {
            return ((UncheckedList)root).sublistcontainsBits(this.rootOffset,size,Float.floatToRawIntBits(v));
          }
          return ((UncheckedList)root).sublistcontainsNaN(this.rootOffset,size);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    contains(byte val)
    {
      final var root=checkModCountAndGetRoot();
      {
        int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            return ((UncheckedList)root).sublistcontainsBits(this.rootOffset,size,Float.floatToRawIntBits(val));
          }
          return ((UncheckedList)root).sublistcontains0(this.rootOffset,size);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    contains(char val)
    {
      final var root=checkModCountAndGetRoot();
      {
        int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            return ((UncheckedList)root).sublistcontainsBits(this.rootOffset,size,Float.floatToRawIntBits(val));
          }
          return ((UncheckedList)root).sublistcontains0(this.rootOffset,size);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    contains(short val)
    {
      final var root=checkModCountAndGetRoot();
      {
        int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            return ((UncheckedList)root).sublistcontainsBits(this.rootOffset,size,Float.floatToRawIntBits(val));
          }
          return ((UncheckedList)root).sublistcontains0(this.rootOffset,size);
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
        final float[] arr;
        int hash;
        int i;
        for(hash=31+HashUtil.hashFloat((arr=root.arr)[i=this.rootOffset]),size+=i;++i!=size;hash=hash*31+HashUtil.hashFloat(arr[i])){}
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
        if(val)
        {
          return uncheckedremoveValBits(size,TypeUtil.FLT_TRUE_BITS);
        }
        return uncheckedremoveVal0(size);
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
        if(val!=0)
        {
          if(TypeUtil.checkCastToFloat(val))
          {
            return uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
          }
        }
        else
        {
          return uncheckedremoveVal0(size);
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
        if(val!=0)
        {
          if(TypeUtil.checkCastToFloat(val))
          {
            return uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
          }
        }
        else
        {
          return uncheckedremoveVal0(size);
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
        if(val==val)
        {
          return uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
        }
        return uncheckedremoveValNaN(size);
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
        final float v;
        if((v=(float)val)==val)
        {
          return uncheckedremoveValBits(size,Float.floatToRawIntBits(v));
        }
        else if(v!=v)
        {
          return uncheckedremoveValNaN(size);
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
        if(val instanceof Float)
        {
          final float v;
          if((v=(float)val)==v)
          {
            return uncheckedremoveValBits(size,Float.floatToRawIntBits(v));
          }
          return uncheckedremoveValNaN(size);
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
          if(val!=0)
          {
            return uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
          }
          return uncheckedremoveVal0(size);
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
      {
        int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            return uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
          }
          return uncheckedremoveVal0(size);
        }
      }
      CheckedCollection.checkModCount(this.modCount,root.modCount);
      return false;
    }
    @Override
    public
    boolean
    removeVal(short val)
    {
      {
        int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            return uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
          }
          return uncheckedremoveVal0(size);
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
        if(size>(Integer.MAX_VALUE/5))
        {
          throw new OutOfMemoryError();
        }
        final char[] buffer;
        if(size<=(OmniArray.MAX_ARR_SIZE/((17)<<1)))
        {
          final float[] arr;
          int bufferOffset,i;
          for(bufferOffset=ToStringUtil.getStringFloat((arr=root.arr)[i=this.rootOffset],buffer=new char[size*(17)],1),size+=i;++i!=size;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringFloat(arr[i],buffer,bufferOffset)){}
          buffer[bufferOffset]=']';
          buffer[0]='[';
          return new String(buffer,0,bufferOffset+1);
        }
        else
        {
          final ToStringUtil.OmniStringBuilder builder;
          final float[] arr;
          int i;
          for((builder=new ToStringUtil.OmniStringBuilder(1,new char[size<=(int)(OmniArray.MAX_ARR_SIZE/11)?(size*11):OmniArray.MAX_ARR_SIZE])).uncheckedAppendFloat((arr=root.arr)[i=this.rootOffset]),size+=i;++i!=size;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendFloat(arr[i])){}
          builder.uncheckedAppendChar(']');
          (buffer=builder.buffer)[0]='[';
          return new String(buffer,0,builder.size);
        }
      }
      return "[]";
    }
    @Override
    public boolean add(float val)
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
        ((FloatArrSeq)root).uncheckedInsert(rootSize,this.rootOffset,val);
        ++this.size;
      }
      else
      {
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr.size=1,curr=curr.parent){}
        ((FloatArrSeq)root).uncheckedInit(val);
        this.size=1;
      }
      return true;
    }
    private FloatArrSeq checkModCountAndGetRoot()
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
       extends Itr implements OmniListIterator.OfFloat
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
       public float previousFloat()
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
             return (float)root.arr[cursor];
       }
       @Override
       public void add(float val)
       {
         //TODO implement LstItr.add(float)
       }
       @Override
       public void set(float val)
       {
         //TODO implement LstItr.set(float)
       }
     }
    @Override
    public OmniList.OfFloat subList(int fromIndex,int toIndex)
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
    public OmniListIterator.OfFloat listIterator()
    {
      CheckedCollection.checkModCount(this.modCount,root.modCount);
      return new LstItr(this);
    }
    @Override
    public OmniListIterator.OfFloat listIterator(int index)
    {
      CheckedCollection.checkModCount(this.modCount,root.modCount);
      if(index<0 || size<index)
      {
        throw new InvalidWriteIndexException(index,size);
      }
      return new LstItr(this,index+this.rootOffset);
    }
    @Override
    public void sort(FloatComparator sorter)
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
            FloatSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
          }
          else
          {
            final int rootOffset;
            FloatSortUtil.uncheckedStableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter);
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
    public void sort(Comparator<? super Float> sorter)
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
            FloatSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
          }
          else
          {
            final int rootOffset;
            FloatSortUtil.uncheckedStableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter::compare);
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
    public void unstableSort(FloatComparator sorter)
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
            FloatSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
          }
          else
          {
            final int rootOffset;
            FloatSortUtil.uncheckedUnstableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter);
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
          FloatSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
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
          FloatSortUtil.uncheckedDescendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
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
    public void replaceAll(FloatUnaryOperator operator)
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
    public void replaceAll(UnaryOperator<Float> operator)
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
    public void add(int index,float val)
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
        ((FloatArrSeq)root).uncheckedInsert(rootSize,index+this.rootOffset,val);
        ++this.size;
      }
      else
      {
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr.size=1,curr=curr.parent){}
        ((FloatArrSeq)root).uncheckedInit(val);
        this.size=1;
      }
    }
    @Override
    public float getFloat(int index)
    {
      final var root=checkModCountAndGetRoot();
      if(index<0 || index>=size)
      {
        throw new InvalidReadIndexException(index,size);
      }
      return (float)root.arr[index+this.rootOffset];
    }
    @Override
    public void put(int index,float val)
    {
      final var root=checkModCountAndGetRoot();
      if(index<0 || index>=size)
      {
        throw new InvalidWriteIndexException(index,size);
      }
      root.arr[index+this.rootOffset]=val;
    }
    @Override
    public float set(int index,float val)
    {
      final var root=checkModCountAndGetRoot();
      if(index<0 || index>=size)
      {
        throw new InvalidReadIndexException(index,size);
      }
      final float[] arr;
      final float ret=(float)(arr=root.arr)[index+=this.rootOffset];
      arr[index]=val;
      return ret;
    }
    @Override
    public float removeFloatAt(int index)
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
      return ((FloatArrSeq)root).uncheckedremoveAtIndex(index+this.rootOffset,root.size);
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
        if(val)
        {
          return ((UncheckedList)root).sublistindexOfBits(this.rootOffset,size,TypeUtil.FLT_TRUE_BITS);
        }
        return ((UncheckedList)root).sublistindexOf0(this.rootOffset,size);
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
        if(val!=0)
        {
          if(TypeUtil.checkCastToFloat(val))
          {
            return ((UncheckedList)root).sublistindexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
          }
        }
        else
        {
          return ((UncheckedList)root).sublistindexOf0(this.rootOffset,size);
        }
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
        if(val!=0)
        {
          if(TypeUtil.checkCastToFloat(val))
          {
            return ((UncheckedList)root).sublistindexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
          }
        }
        else
        {
          return ((UncheckedList)root).sublistindexOf0(this.rootOffset,size);
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
        if(val==val)
        {
          return ((UncheckedList)root).sublistindexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
        }
        return ((UncheckedList)root).sublistindexOfNaN(this.rootOffset,size);
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
        final float v;
        if((v=(float)val)==val)
        {
          return ((UncheckedList)root).sublistindexOfBits(this.rootOffset,size,Float.floatToRawIntBits(v));
        }
        else if(v!=v)
        {
          return ((UncheckedList)root).sublistindexOfNaN(this.rootOffset,size);
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
        if(val instanceof Float)
        {
          final float v;
          if((v=(float)val)==v)
          {
            return ((UncheckedList)root).sublistindexOfBits(this.rootOffset,size,Float.floatToRawIntBits(v));
          }
          return ((UncheckedList)root).sublistindexOfNaN(this.rootOffset,size);
        }
      }
      return -1;
    }
    @Override
    public
    int
    indexOf(char val)
    {
      final var root=checkModCountAndGetRoot();
      {
        int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            return ((UncheckedList)root).sublistindexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
          }
          return ((UncheckedList)root).sublistindexOf0(this.rootOffset,size);
        }
      }
      return -1;
    }
    @Override
    public
    int
    indexOf(short val)
    {
      final var root=checkModCountAndGetRoot();
      {
        int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            return ((UncheckedList)root).sublistindexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
          }
          return ((UncheckedList)root).sublistindexOf0(this.rootOffset,size);
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
        if(val)
        {
          return ((UncheckedList)root).sublistlastIndexOfBits(this.rootOffset,size,TypeUtil.FLT_TRUE_BITS);
        }
        return ((UncheckedList)root).sublistlastIndexOf0(this.rootOffset,size);
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
        if(val!=0)
        {
          if(TypeUtil.checkCastToFloat(val))
          {
            return ((UncheckedList)root).sublistlastIndexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
          }
        }
        else
        {
          return ((UncheckedList)root).sublistlastIndexOf0(this.rootOffset,size);
        }
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
        if(val!=0)
        {
          if(TypeUtil.checkCastToFloat(val))
          {
            return ((UncheckedList)root).sublistlastIndexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
          }
        }
        else
        {
          return ((UncheckedList)root).sublistlastIndexOf0(this.rootOffset,size);
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
        if(val==val)
        {
          return ((UncheckedList)root).sublistlastIndexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
        }
        return ((UncheckedList)root).sublistlastIndexOfNaN(this.rootOffset,size);
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
        final float v;
        if((v=(float)val)==val)
        {
          return ((UncheckedList)root).sublistlastIndexOfBits(this.rootOffset,size,Float.floatToRawIntBits(v));
        }
        else if(v!=v)
        {
          return ((UncheckedList)root).sublistlastIndexOfNaN(this.rootOffset,size);
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
        if(val instanceof Float)
        {
          final float v;
          if((v=(float)val)==v)
          {
            return ((UncheckedList)root).sublistlastIndexOfBits(this.rootOffset,size,Float.floatToRawIntBits(v));
          }
          return ((UncheckedList)root).sublistlastIndexOfNaN(this.rootOffset,size);
        }
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf(char val)
    {
      final var root=checkModCountAndGetRoot();
      {
        int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            return ((UncheckedList)root).sublistlastIndexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
          }
          return ((UncheckedList)root).sublistlastIndexOf0(this.rootOffset,size);
        }
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf(short val)
    {
      final var root=checkModCountAndGetRoot();
      {
        int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            return ((UncheckedList)root).sublistlastIndexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
          }
          return ((UncheckedList)root).sublistlastIndexOf0(this.rootOffset,size);
        }
      }
      return -1;
    }
  }
  private static abstract class AbstractItr implements OmniIterator.OfFloat
  {
    @Override
    public Float next()
    {
      return (Float)(nextFloat());
    }
    @Override
    public double nextDouble()
    {
      return (double)(nextFloat());
    }
  }
  private static abstract interface ListDefault extends OmniList.OfFloat
  {
    @Override
    public default void add(int index,Float val)
    {
      add(index,(float)val);
    }
    @Override
    public default Float get(int index)
    {
      return getFloat(index);
    }
    @Override
    public default Float set(int index,Float val)
    {
      return set(index,(float)val);
    }
    @Override
    public default  Float remove(int index)
    {
      return removeFloatAt(index);
    }
  }
  private static abstract interface SubListDefault extends ListDefault
  {
    @Override
    public default boolean add(Float val)
    {
      return this.add((float)val);
    }
    @Override
    public default boolean add(boolean val)
    {
      return this.add(TypeUtil.castToFloat(val));
    }
    @Override
    public default boolean add(int val)
    {
      return this.add((float)val);
    }
    @Override
    public default boolean add(char val)
    {
      return this.add((float)val);
    }
    @Override
    public default boolean add(short val)
    {
      return this.add((float)val);
    }
    @Override
    public default boolean add(long val)
    {
      return this.add((float)val);
    }
  }
}
