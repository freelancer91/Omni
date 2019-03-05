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
import omni.function.FloatUnaryOperator;
import omni.function.FloatComparator;
import omni.function.FloatPredicate;
import omni.function.FloatConsumer;
import omni.util.ToStringUtil;
import omni.util.HashUtil;
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
  public void forEach(FloatConsumer action)
  {
    int size;
    if((size=this.size)!=0)
    {
      uncheckedForEach(size,action);
    }
  }
  @Override
  public void forEach(Consumer<? super Float> action)
  {
    int size;
    if((size=this.size)!=0)
    {
      uncheckedForEach(size,action::accept);
    }
  }
  @Override
  public boolean removeIf(FloatPredicate filter)
  {
    int size;
    if((size=this.size)!=0)
    {
      return uncheckedRemoveIf(size,filter);
    }
    return false;
  }
  @Override
  public boolean removeIf(Predicate<? super Float> filter)
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
  abstract boolean uncheckedRemoveIf(int size,FloatPredicate filter);
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
  public static class UncheckedArrStack extends FloatArrSeq implements OmniStack.OfFloat
  {
    public UncheckedArrStack()
    {
      super();
    }
    public UncheckedArrStack(int initialCapacity)
    {
      super(initialCapacity);
    }
    public UncheckedArrStack(int size,float[] arr)
    {
      super(size,arr);
    }
    @Override
    public float popFloat()
    {
      return (float)arr[--this.size];
    }
    @Override
    public Float pop()
    {
      return popFloat();
    }
    @Override
    public Object clone()
    {
      float[] copy;
      int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new float[size],0,size);
      }
      else
      {
        copy=OmniArray.OfFloat.DEFAULT_ARR;
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
    @Override
    void uncheckedCopyInto(Object[] dst,int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,size);
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
    @Override
    void uncheckedForEach(int size,FloatConsumer action)
    {
      final var arr=this.arr;
      for(;;)
      {
        action.accept((float)arr[--size]);
        if(size==0)
        {
          return;
        }
      }
    }
    @Override
    boolean uncheckedRemoveIf(int size,FloatPredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public OmniIterator.OfFloat iterator()
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
    public CheckedArrStack(int size,float[] arr)
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
    public Object clone()
    {
      float[] copy;
      int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new float[size],0,size);
      }
      else
      {
        copy=OmniArray.OfFloat.DEFAULT_ARR;
      }
      return new CheckedArrStack(size,copy);
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
    void uncheckedForEach(int size,FloatConsumer action)
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
    boolean uncheckedRemoveIf(int size,FloatPredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public void push(float val)
    {
      ++this.modCount;
      super.push(val);
    }
    @Override
    public OmniIterator.OfFloat iterator()
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
  private static class UncheckedArrList extends FloatArrSeq implements OmniList.OfFloat
  {
    private static class SubListImpl implements OmniList.OfFloat
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
        final float[] copy;
        int size;
        if((size=this.size)!=0)
        {
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new float[size],0,size);
        }
        else
        {
          copy=OmniArray.OfFloat.DEFAULT_ARR;
        }
        return new UncheckedArrList(size,copy);
      }
      @Override
      public boolean add(float val)
      {
        int rootSize;
        final UncheckedArrList root;
        if((rootSize=(root=this.root).size)!=0)
        {
          for(var curr=parent;curr!=null;++parent.size){}
          ((FloatArrSeq)root).uncheckedInsert(rootSize,this.rootOffset,val);
          ++this.size;
        }
        else
        {
          for(var curr=parent;curr!=null;parent.size=1){}
          ((FloatArrSeq)root).uncheckedInit(val);
          this.size=1;
        }
        return true;
      }
      @Override
      public void add(int index,float val)
      {
        int rootSize;
        final UncheckedArrList root;
        if((rootSize=(root=this.root).size)!=0)
        {
          for(var curr=parent;curr!=null;++parent.size){}
          ((FloatArrSeq)root).uncheckedInsert(rootSize,index+this.rootOffset,val);
          ++this.size;
        }
        else
        {
          for(var curr=parent;curr!=null;parent.size=1){}
          ((FloatArrSeq)root).uncheckedInit(val);
          this.size=1;
        }
      }
      @Override
      public boolean add(Float val)
      {
        return add((float)val);
      }
      @Override
      public void add(int index,Float val)
      {
        add(index,(float)val);
      }
      @Override
      public void forEach(FloatConsumer action)
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
      public void forEach(Consumer<? super Float> action)
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
      public boolean removeIf(FloatPredicate filter)
      {
        //TODO
        return false;
      }
      @Override
      public boolean removeIf(Predicate<? super Float> action)
      {
        //TODO
        return false;
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
      public void put(int index,float val)
      {
        root.arr[index+this.rootOffset]=val;
      }
      @Override
      public float getFloat(int index)
      {
        return (float)root.arr[index+this.rootOffset];
      }
      @Override
      public Float get(int index)
      {
        return getFloat(index);
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
      public OmniIterator.OfFloat iterator()
      {
        //TODO
        return null;
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
      public
      boolean
      contains(boolean val)
      {
        int size;
        if((size=this.size)!=0)
        {
          if(val)
          {
            return ((UncheckedArrList)root).sublistcontainsBits(this.rootOffset,size,TypeUtil.FLT_TRUE_BITS);
          }
          return ((UncheckedArrList)root).sublistcontains0(this.rootOffset,size);
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
              return ((UncheckedArrList)root).sublistcontainsBits(this.rootOffset,size,Float.floatToRawIntBits(val));
            }
          }
          else
          {
            return ((UncheckedArrList)root).sublistcontains0(this.rootOffset,size);
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
              return ((UncheckedArrList)root).sublistcontainsBits(this.rootOffset,size,Float.floatToRawIntBits(val));
            }
          }
          else
          {
            return ((UncheckedArrList)root).sublistcontains0(this.rootOffset,size);
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
            return ((UncheckedArrList)root).sublistcontainsBits(this.rootOffset,size,Float.floatToRawIntBits(val));
          }
          return ((UncheckedArrList)root).sublistcontainsNaN(this.rootOffset,size);
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
            return ((UncheckedArrList)root).sublistcontainsBits(this.rootOffset,size,Float.floatToRawIntBits(v));
          }
          else if(v!=v)
          {
            return ((UncheckedArrList)root).sublistcontainsNaN(this.rootOffset,size);
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
              return ((UncheckedArrList)root).sublistcontainsBits(this.rootOffset,size,Float.floatToRawIntBits(v));
            }
            return ((UncheckedArrList)root).sublistcontainsNaN(this.rootOffset,size);
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
              return ((UncheckedArrList)root).sublistcontainsBits(this.rootOffset,size,Float.floatToRawIntBits(val));
            }
            return ((UncheckedArrList)root).sublistcontains0(this.rootOffset,size);
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
              return ((UncheckedArrList)root).sublistcontainsBits(this.rootOffset,size,Float.floatToRawIntBits(val));
            }
            return ((UncheckedArrList)root).sublistcontains0(this.rootOffset,size);
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
              return ((UncheckedArrList)root).sublistcontainsBits(this.rootOffset,size,Float.floatToRawIntBits(val));
            }
            return ((UncheckedArrList)root).sublistcontains0(this.rootOffset,size);
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
      int
      indexOf(boolean val)
      {
        int size;
        if((size=this.size)!=0)
        {
          if(val)
          {
            return ((UncheckedArrList)root).sublistindexOfBits(this.rootOffset,size,TypeUtil.FLT_TRUE_BITS);
          }
          return ((UncheckedArrList)root).sublistindexOf0(this.rootOffset,size);
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
              return ((UncheckedArrList)root).sublistindexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
            }
          }
          else
          {
            return ((UncheckedArrList)root).sublistindexOf0(this.rootOffset,size);
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
              return ((UncheckedArrList)root).sublistindexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
            }
          }
          else
          {
            return ((UncheckedArrList)root).sublistindexOf0(this.rootOffset,size);
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
            return ((UncheckedArrList)root).sublistindexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
          }
          return ((UncheckedArrList)root).sublistindexOfNaN(this.rootOffset,size);
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
            return ((UncheckedArrList)root).sublistindexOfBits(this.rootOffset,size,Float.floatToRawIntBits(v));
          }
          else if(v!=v)
          {
            return ((UncheckedArrList)root).sublistindexOfNaN(this.rootOffset,size);
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
              return ((UncheckedArrList)root).sublistindexOfBits(this.rootOffset,size,Float.floatToRawIntBits(v));
            }
            return ((UncheckedArrList)root).sublistindexOfNaN(this.rootOffset,size);
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
              return ((UncheckedArrList)root).sublistindexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
            }
            return ((UncheckedArrList)root).sublistindexOf0(this.rootOffset,size);
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
              return ((UncheckedArrList)root).sublistindexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
            }
            return ((UncheckedArrList)root).sublistindexOf0(this.rootOffset,size);
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
          if(val)
          {
            return ((UncheckedArrList)root).sublistlastIndexOfBits(this.rootOffset,size,TypeUtil.FLT_TRUE_BITS);
          }
          return ((UncheckedArrList)root).sublistlastIndexOf0(this.rootOffset,size);
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
              return ((UncheckedArrList)root).sublistlastIndexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
            }
          }
          else
          {
            return ((UncheckedArrList)root).sublistlastIndexOf0(this.rootOffset,size);
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
              return ((UncheckedArrList)root).sublistlastIndexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
            }
          }
          else
          {
            return ((UncheckedArrList)root).sublistlastIndexOf0(this.rootOffset,size);
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
            return ((UncheckedArrList)root).sublistlastIndexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
          }
          return ((UncheckedArrList)root).sublistlastIndexOfNaN(this.rootOffset,size);
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
            return ((UncheckedArrList)root).sublistlastIndexOfBits(this.rootOffset,size,Float.floatToRawIntBits(v));
          }
          else if(v!=v)
          {
            return ((UncheckedArrList)root).sublistlastIndexOfNaN(this.rootOffset,size);
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
              return ((UncheckedArrList)root).sublistlastIndexOfBits(this.rootOffset,size,Float.floatToRawIntBits(v));
            }
            return ((UncheckedArrList)root).sublistlastIndexOfNaN(this.rootOffset,size);
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
              return ((UncheckedArrList)root).sublistlastIndexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
            }
            return ((UncheckedArrList)root).sublistlastIndexOf0(this.rootOffset,size);
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
              return ((UncheckedArrList)root).sublistlastIndexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
            }
            return ((UncheckedArrList)root).sublistlastIndexOf0(this.rootOffset,size);
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
          final float[] arr;
          int hash;
          int i;
          for(hash=31+HashUtil.hashFloat((arr=root.arr)[i=this.rootOffset]),size+=i;++i!=size;hash=hash*31+HashUtil.hashFloat(arr[i])){}
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
        uncheckedremoveValBits(int size
        ,int bits
        )
        {
          final UncheckedArrList root;
          final var arr=(root=this.root).arr;
          {
            for(int i=this.rootOffset,bound=i+size;;)
            {
              if(
              bits==Float.floatToRawIntBits(arr[i])
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
        private boolean
        uncheckedremoveVal0(int size
        )
        {
          final UncheckedArrList root;
          final var arr=(root=this.root).arr;
          {
            for(int i=this.rootOffset,bound=i+size;;)
            {
              if(
              0==arr[i]
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
        private boolean
        uncheckedremoveValNaN(int size
        )
        {
          final UncheckedArrList root;
          final var arr=(root=this.root).arr;
          {
            for(int i=this.rootOffset,bound=i+size;;)
            {
              if(
              Float.isNaN(arr[i])
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
    public UncheckedArrList(int size,float[] arr)
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
    public void put(int index,float val)
    {
      arr[index]=val;
    }
    @Override
    public Object clone()
    {
      float[] copy;
      int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new float[size],0,size);
      }
      else
      {
        copy=OmniArray.OfFloat.DEFAULT_ARR;
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
    @Override
    void uncheckedCopyInto(Object[] dst,int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,size);
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
    int uncheckedHashCode(int size)
    {
      final float[] arr;
      int hash,i;
      for(hash=31+HashUtil.hashFloat((arr=this.arr)[i=0]);++i!=size;hash=hash*31+HashUtil.hashFloat(arr[i])){}
      return hash;
    }
    @Override
    int uncheckedToString(int size,char[] buffer)
    {
      final float[] arr;
      int bufferOffset,i;
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
    @Override
    void uncheckedForEach(int size,FloatConsumer action)
    {
      final var arr=this.arr;
      for(int i=0;;)
      {
        action.accept((float)arr[i]);
        if(++i==size)
        {
          return;
        }
      }
    }
    @Override
    boolean uncheckedRemoveIf(int size,FloatPredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public OmniIterator.OfFloat iterator()
    {
      //TODO
      return null;
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
    public void add(int index,float val)
    {
      uncheckedAdd(index,val,this.size);
    }
    @Override
    public void add(int index,Float val)
    {
      add(index,(float)val);
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
    @Override
    public Float remove(int index)
    {
      return removeFloatAt(index);
    }
    @Override
    public float removeFloatAt(int index)
    {
      return super.uncheckedremoveAtIndex(index,this.size);
    }
    @Override
    public void replaceAll(FloatUnaryOperator operator)
    {
      int size;
      if((size=this.size)!=0)
      {
        uncheckedReplaceAll(size,operator);
      }
    }
    @Override
    public void replaceAll(UnaryOperator<Float> operator)
    {
      int size;
      if((size=this.size)!=0)
      {
        uncheckedReplaceAll(size,operator::apply);
      }
    }
    private void uncheckedReplaceAll(int size,FloatUnaryOperator operator)
    {
      final var arr=this.arr;
      for(int i=0;;)
      {
        arr[i]=operator.applyAsFloat((float)arr[i]);
        if(++i==size)
        {
          return;
        }
      }
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
    public Float set(int index,Float val)
    {
      return set(index,(float)val);
    }
    @Override
    public void sort(FloatComparator sorter)
    {
      int size;
      if((size=this.size)>1)
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
    @Override
    public void sort(Comparator<? super Float> sorter)
    {
      int size;
      if((size=this.size)>1)
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
    @Override
    public void stableAscendingSort()
    {
      int size;
      if((size=this.size)>1)
      {
        FloatSortUtil.uncheckedAscendingSort(this.arr,0,size);
      }
    }
    @Override
    public void stableDescendingSort()
    {
      int size;
      if((size=this.size)>1)
      {
        FloatSortUtil.uncheckedDescendingSort(this.arr,0,size);
      }
    }
    @Override
    public OmniList.OfFloat subList(int fromIndex,int toIndex)
    {
      //TODO
      return null;
    }
    @Override
    public void unstableSort(FloatComparator sorter)
    {
      int size;
      if((size=this.size)>1)
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
  public static class CheckedArrList extends UncheckedArrList
  {
    private static class SubListImpl implements OmniList.OfFloat
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
        final float[] copy;
        int size;
        if((size=this.size)!=0)
        {
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new float[size],0,size);
        }
        else
        {
          copy=OmniArray.OfFloat.DEFAULT_ARR;
        }
        return new CheckedArrList(size,copy);
      }
      @Override
      public boolean add(float val)
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
          ((FloatArrSeq)root).uncheckedInsert(rootSize,this.rootOffset,val);
          ++this.size;
        }
        else
        {
          for(var curr=parent;curr!=null;parent.modCount=modCount,parent.size=1){}
          ((FloatArrSeq)root).uncheckedInit(val);
          this.size=1;
        }
        return true;
      }
      @Override
      public void add(int index,float val)
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
          ((FloatArrSeq)root).uncheckedInsert(rootSize,index+this.rootOffset,val);
          ++this.size;
        }
        else
        {
          for(var curr=parent;curr!=null;parent.modCount=modCount,parent.size=1){}
          ((FloatArrSeq)root).uncheckedInit(val);
          this.size=1;
        }
      }
      @Override
      public boolean add(Float val)
      {
        return add((float)val);
      }
      @Override
      public void add(int index,Float val)
      {
        add(index,(float)val);
      }
      @Override
      public void forEach(FloatConsumer action)
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
      public void forEach(Consumer<? super Float> action)
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
      public boolean removeIf(FloatPredicate filter)
      {
        //TODO
        return false;
      }
      @Override
      public boolean removeIf(Predicate<? super Float> action)
      {
        //TODO
        return false;
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
      public Float[] toArray()
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
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
      public void put(int index,float val)
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
      public float getFloat(int index)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        if(index<0 || index>=size)
        {
          throw new InvalidReadIndexException(index,size);
        }
        return (float)root.arr[index+this.rootOffset];
      }
      @Override
      public Float get(int index)
      {
        return getFloat(index);
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
      public OmniIterator.OfFloat iterator()
      {
        //TODO
        return null;
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
      public
      boolean
      contains(boolean val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        int size;
        if((size=this.size)!=0)
        {
          if(val)
          {
            return ((UncheckedArrList)root).sublistcontainsBits(this.rootOffset,size,TypeUtil.FLT_TRUE_BITS);
          }
          return ((UncheckedArrList)root).sublistcontains0(this.rootOffset,size);
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
          if(val!=0)
          {
            if(TypeUtil.checkCastToFloat(val))
            {
              return ((UncheckedArrList)root).sublistcontainsBits(this.rootOffset,size,Float.floatToRawIntBits(val));
            }
          }
          else
          {
            return ((UncheckedArrList)root).sublistcontains0(this.rootOffset,size);
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
          if(val!=0)
          {
            if(TypeUtil.checkCastToFloat(val))
            {
              return ((UncheckedArrList)root).sublistcontainsBits(this.rootOffset,size,Float.floatToRawIntBits(val));
            }
          }
          else
          {
            return ((UncheckedArrList)root).sublistcontains0(this.rootOffset,size);
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
          if(val==val)
          {
            return ((UncheckedArrList)root).sublistcontainsBits(this.rootOffset,size,Float.floatToRawIntBits(val));
          }
          return ((UncheckedArrList)root).sublistcontainsNaN(this.rootOffset,size);
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
          final float v;
          if((v=(float)val)==val)
          {
            return ((UncheckedArrList)root).sublistcontainsBits(this.rootOffset,size,Float.floatToRawIntBits(v));
          }
          else if(v!=v)
          {
            return ((UncheckedArrList)root).sublistcontainsNaN(this.rootOffset,size);
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
          if(val instanceof Float)
          {
            final float v;
            if((v=(float)val)==v)
            {
              return ((UncheckedArrList)root).sublistcontainsBits(this.rootOffset,size,Float.floatToRawIntBits(v));
            }
            return ((UncheckedArrList)root).sublistcontainsNaN(this.rootOffset,size);
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
            if(val!=0)
            {
              return ((UncheckedArrList)root).sublistcontainsBits(this.rootOffset,size,Float.floatToRawIntBits(val));
            }
            return ((UncheckedArrList)root).sublistcontains0(this.rootOffset,size);
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
        {
          int size;
          if((size=this.size)!=0)
          {
            if(val!=0)
            {
              return ((UncheckedArrList)root).sublistcontainsBits(this.rootOffset,size,Float.floatToRawIntBits(val));
            }
            return ((UncheckedArrList)root).sublistcontains0(this.rootOffset,size);
          }
        }
        return false;
      }
      @Override
      public
      boolean
      contains(short val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        {
          int size;
          if((size=this.size)!=0)
          {
            if(val!=0)
            {
              return ((UncheckedArrList)root).sublistcontainsBits(this.rootOffset,size,Float.floatToRawIntBits(val));
            }
            return ((UncheckedArrList)root).sublistcontains0(this.rootOffset,size);
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
      public
      int
      indexOf(boolean val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        int size;
        if((size=this.size)!=0)
        {
          if(val)
          {
            return ((UncheckedArrList)root).sublistindexOfBits(this.rootOffset,size,TypeUtil.FLT_TRUE_BITS);
          }
          return ((UncheckedArrList)root).sublistindexOf0(this.rootOffset,size);
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
          if(val!=0)
          {
            if(TypeUtil.checkCastToFloat(val))
            {
              return ((UncheckedArrList)root).sublistindexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
            }
          }
          else
          {
            return ((UncheckedArrList)root).sublistindexOf0(this.rootOffset,size);
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
          if(val!=0)
          {
            if(TypeUtil.checkCastToFloat(val))
            {
              return ((UncheckedArrList)root).sublistindexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
            }
          }
          else
          {
            return ((UncheckedArrList)root).sublistindexOf0(this.rootOffset,size);
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
          if(val==val)
          {
            return ((UncheckedArrList)root).sublistindexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
          }
          return ((UncheckedArrList)root).sublistindexOfNaN(this.rootOffset,size);
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
          final float v;
          if((v=(float)val)==val)
          {
            return ((UncheckedArrList)root).sublistindexOfBits(this.rootOffset,size,Float.floatToRawIntBits(v));
          }
          else if(v!=v)
          {
            return ((UncheckedArrList)root).sublistindexOfNaN(this.rootOffset,size);
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
          if(val instanceof Float)
          {
            final float v;
            if((v=(float)val)==v)
            {
              return ((UncheckedArrList)root).sublistindexOfBits(this.rootOffset,size,Float.floatToRawIntBits(v));
            }
            return ((UncheckedArrList)root).sublistindexOfNaN(this.rootOffset,size);
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
        {
          int size;
          if((size=this.size)!=0)
          {
            if(val!=0)
            {
              return ((UncheckedArrList)root).sublistindexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
            }
            return ((UncheckedArrList)root).sublistindexOf0(this.rootOffset,size);
          }
        }
        return -1;
      }
      @Override
      public
      int
      indexOf(short val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        {
          int size;
          if((size=this.size)!=0)
          {
            if(val!=0)
            {
              return ((UncheckedArrList)root).sublistindexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
            }
            return ((UncheckedArrList)root).sublistindexOf0(this.rootOffset,size);
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
          if(val)
          {
            return ((UncheckedArrList)root).sublistlastIndexOfBits(this.rootOffset,size,TypeUtil.FLT_TRUE_BITS);
          }
          return ((UncheckedArrList)root).sublistlastIndexOf0(this.rootOffset,size);
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
          if(val!=0)
          {
            if(TypeUtil.checkCastToFloat(val))
            {
              return ((UncheckedArrList)root).sublistlastIndexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
            }
          }
          else
          {
            return ((UncheckedArrList)root).sublistlastIndexOf0(this.rootOffset,size);
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
          if(val!=0)
          {
            if(TypeUtil.checkCastToFloat(val))
            {
              return ((UncheckedArrList)root).sublistlastIndexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
            }
          }
          else
          {
            return ((UncheckedArrList)root).sublistlastIndexOf0(this.rootOffset,size);
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
          if(val==val)
          {
            return ((UncheckedArrList)root).sublistlastIndexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
          }
          return ((UncheckedArrList)root).sublistlastIndexOfNaN(this.rootOffset,size);
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
          final float v;
          if((v=(float)val)==val)
          {
            return ((UncheckedArrList)root).sublistlastIndexOfBits(this.rootOffset,size,Float.floatToRawIntBits(v));
          }
          else if(v!=v)
          {
            return ((UncheckedArrList)root).sublistlastIndexOfNaN(this.rootOffset,size);
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
          if(val instanceof Float)
          {
            final float v;
            if((v=(float)val)==v)
            {
              return ((UncheckedArrList)root).sublistlastIndexOfBits(this.rootOffset,size,Float.floatToRawIntBits(v));
            }
            return ((UncheckedArrList)root).sublistlastIndexOfNaN(this.rootOffset,size);
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
        {
          int size;
          if((size=this.size)!=0)
          {
            if(val!=0)
            {
              return ((UncheckedArrList)root).sublistlastIndexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
            }
            return ((UncheckedArrList)root).sublistlastIndexOf0(this.rootOffset,size);
          }
        }
        return -1;
      }
      @Override
      public
      int
      lastIndexOf(short val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        {
          int size;
          if((size=this.size)!=0)
          {
            if(val!=0)
            {
              return ((UncheckedArrList)root).sublistlastIndexOfBits(this.rootOffset,size,Float.floatToRawIntBits(val));
            }
            return ((UncheckedArrList)root).sublistlastIndexOf0(this.rootOffset,size);
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
          final float[] arr;
          int hash;
          int i;
          for(hash=31+HashUtil.hashFloat((arr=root.arr)[i=this.rootOffset]),size+=i;++i!=size;hash=hash*31+HashUtil.hashFloat(arr[i])){}
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
        uncheckedremoveValBits(int size
        ,int bits
        )
        {
          int modCount=this.modCount;
          final CheckedArrList root;
          final var arr=(root=this.root).arr;
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
                for(var curr=parent;curr!=null;--curr.size,curr.modCount=modCount){}
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
          catch(RuntimeException e)
          {
            throw CheckedCollection.checkModCount(modCount,root.modCount,e);
          }
        }
        private boolean
        uncheckedremoveVal0(int size
        )
        {
          final UncheckedArrList root;
          final var arr=(root=this.root).arr;
          {
            for(int i=this.rootOffset,bound=i+size;;)
            {
              if(
              0==arr[i]
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
        private boolean
        uncheckedremoveValNaN(int size
        )
        {
          final UncheckedArrList root;
          final var arr=(root=this.root).arr;
          {
            for(int i=this.rootOffset,bound=i+size;;)
            {
              if(
              Float.isNaN(arr[i])
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
    public CheckedArrList(int size,float[] arr)
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
      float[] copy;
      int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new float[size],0,size);
      }
      else
      {
        copy=OmniArray.OfFloat.DEFAULT_ARR;
      }
      return new CheckedArrList(size,copy);
    }
    @Override
    public void push(float val)
    {
      ++this.modCount;
      super.push(val);
    }
    @Override
    public void put(int index,float val)
    {
      if(index>=0 && index<size)
      {
        super.put(index,val);
        return;
      }
      throw new InvalidWriteIndexException(index,size);
    }
    @Override
    public float set(int index,float val)
    {
      if(index>=0 && index<size)
      {
        return super.set(index,val);
      }
      throw new InvalidReadIndexException(index,size);
    }
    @Override
    public float removeFloatAt(int index)
    {
      int size;
      if((size=this.size)>index && index>=0)
      {
        ++this.modCount;
        return ((FloatArrSeq)this).uncheckedremoveAtIndex(index,size);
      }
      throw new InvalidWriteIndexException(index,size);
    }
    @Override
    public void unstableSort(FloatComparator sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        if(sorter==null)
        {
          FloatSortUtil.uncheckedAscendingSort(this.arr,0,size);
          ++this.modCount;
        }
        else
        {
          int modCount=this.modCount;
          try
          {
            FloatSortUtil.uncheckedUnstableSort(this.arr,0,size,sorter);
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
    public void replaceAll(UnaryOperator<Float> operator)
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
    public void replaceAll(FloatUnaryOperator operator)
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
        FloatSortUtil.uncheckedAscendingSort(this.arr,0,size);
        ++this.modCount;
      }
    }
    @Override
    public void stableDescendingSort()
    {
      int size;
      if((size=this.size)>1)
      {
        FloatSortUtil.uncheckedDescendingSort(this.arr,0,size);
        ++this.modCount;
      }
    }
    @Override
    public OmniList.OfFloat subList(int fromIndex,int toIndex)
    {
      //TODO
      return null;
    }
    @Override
    public void sort(FloatComparator sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        if(sorter==null)
        {
          FloatSortUtil.uncheckedAscendingSort(this.arr,0,size);
          ++this.modCount;
        }
        else
        {
          int modCount=this.modCount;
          try
          {
            FloatSortUtil.uncheckedStableSort(this.arr,0,size,sorter);
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
    public void sort(Comparator<? super Float> sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        if(sorter==null)
        {
          FloatSortUtil.uncheckedAscendingSort(this.arr,0,size);
          ++this.modCount;
        }
        else
        {
          int modCount=this.modCount;
          try
          {
            FloatSortUtil.uncheckedStableSort(this.arr,0,size,sorter::compare);
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
    void uncheckedForEach(int size,FloatConsumer action)
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
    boolean uncheckedRemoveIf(int size,FloatPredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public OmniIterator.OfFloat iterator()
    {
      //TODO
      return null;
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
    public float getFloat(int index)
    {
      if(index>=0 && index<size)
      {
        return super.getFloat(index);
      }
      throw new InvalidReadIndexException(index,size);
    }
    @Override
    public void add(int index,float val)
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
