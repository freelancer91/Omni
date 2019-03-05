package omni.impl.seq;
import omni.api.OmniCollection;
import omni.util.OmniArray;
import omni.api.OmniList;
import omni.api.OmniStack;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.Comparator;
import omni.util.ArrCopy;
import omni.util.DoubleSortUtil;
import omni.impl.CheckedCollection;
import java.util.NoSuchElementException;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;
import omni.util.TypeUtil;
import java.util.function.DoubleUnaryOperator;
import omni.function.DoubleComparator;
import java.util.function.DoublePredicate;
import java.util.function.DoubleConsumer;
import omni.util.HashUtil;
public abstract class DoubleArrSeq implements OmniCollection.OfDouble
{
  transient int size;
  transient double[] arr;
  private DoubleArrSeq()
  {
    super();
  }
  private DoubleArrSeq(int initialCapacity)
  {
    super();
    switch(initialCapacity)
    { 
    default:
      this.arr=new double[initialCapacity];
      return;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfDouble.DEFAULT_ARR;
    case 0:
    }
  }
  private DoubleArrSeq(int size,double[] arr)
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
    push(TypeUtil.castToDouble(val));
    return true;
  }
  @Override
  public boolean add(int val)
  {
    push((double)val);
    return true;
  }
  @Override
  public boolean add(char val)
  {
    push((double)val);
    return true;
  }
  @Override
  public boolean add(long val)
  {
    push((double)val);
    return true;
  }
  @Override
  public boolean add(short val)
  {
    push((double)val);
    return true;
  }
  @Override
  public boolean add(float val)
  {
    push((double)val);
    return true;
  }
  @Override
  public boolean add(double val)
  {
    push(val);
    return true;
  }
  @Override
  public boolean add(Double val)
  {
    push((double)val);
    return true;
  }
  @Override
  public void forEach(DoubleConsumer action)
  {
    int size;
    if((size=this.size)!=0)
    {
      uncheckedForEach(size,action);
    }
  }
  @Override
  public void forEach(Consumer<? super Double> action)
  {
    int size;
    if((size=this.size)!=0)
    {
      uncheckedForEach(size,action::accept);
    }
  }
  @Override
  public boolean removeIf(DoublePredicate filter)
  {
    int size;
    if((size=this.size)!=0)
    {
      return uncheckedRemoveIf(size,filter);
    }
    return false;
  }
  @Override
  public boolean removeIf(Predicate<? super Double> filter)
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
      final StringBuilder builder;
      uncheckedToString(size,builder=new StringBuilder("["));
      return builder.append(']').toString();
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
  ,long bits
  )
  {
    for(final var arr=this.arr;;)
    {
      if(
      bits==Double.doubleToRawLongBits(arr[--size])
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
      Double.isNaN(arr[--size])
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
  abstract boolean uncheckedremoveValBits(int size,long bits);
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
        return uncheckedremoveValBits(size,TypeUtil.DBL_TRUE_BITS);
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
        {
          return uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
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
        if(TypeUtil.checkCastToDouble(val))
        {
          return uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
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
        return uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
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
      if(val==val)
      {
        return uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
      }
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
      if(val instanceof Double)
      {
        final double v;
        if((v=(double)val)==v)
        {
          return uncheckedremoveValBits(size,Double.doubleToRawLongBits(v));
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
          return uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
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
          return uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
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
          return uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
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
        return uncheckedcontainsBits(size,TypeUtil.DBL_TRUE_BITS);
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
        {
          return uncheckedcontainsBits(size,Double.doubleToRawLongBits(val));
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
        if(TypeUtil.checkCastToDouble(val))
        {
          return uncheckedcontainsBits(size,Double.doubleToRawLongBits(val));
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
        return uncheckedcontainsBits(size,Double.doubleToRawLongBits(val));
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
      if(val==val)
      {
        return uncheckedcontainsBits(size,Double.doubleToRawLongBits(val));
      }
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
      if(val instanceof Double)
      {
        final double v;
        if((v=(double)val)==v)
        {
          return uncheckedcontainsBits(size,Double.doubleToRawLongBits(v));
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
          return uncheckedcontainsBits(size,Double.doubleToRawLongBits(val));
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
          return uncheckedcontainsBits(size,Double.doubleToRawLongBits(val));
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
          return uncheckedcontainsBits(size,Double.doubleToRawLongBits(val));
        }
        return uncheckedcontains0(size);
      }
    }
    return false;
  }
  abstract void uncheckedCopyInto(Object[] dst,int size);
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
  abstract void uncheckedCopyInto(Double[] dst,int size);
  @Override
  public Double[] toArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final Double[] copy;
      uncheckedCopyInto(copy=new Double[size],size);
      return copy;
    }
    return OmniArray.OfDouble.DEFAULT_BOXED_ARR;
  }
  abstract int uncheckedHashCode(int size);
  abstract void uncheckedToString(int size,StringBuilder builder);
  abstract void uncheckedForEach(int size,DoubleConsumer action);
  abstract boolean uncheckedRemoveIf(int size,DoublePredicate filter);
  private void uncheckedInit(double val)
  {
    double[] arr;
    if((arr=this.arr)==OmniArray.OfDouble.DEFAULT_ARR)
    {
      this.arr=arr=new double[OmniArray.DEFAULT_ARR_SEQ_CAP];
    }
    else if(arr==null)
    {
      this.arr=arr=new double[1];
    }
    arr[0]=val;
    this.size=1;
  }
  private void uncheckedAppend(int size,double val)
  {
    double[] arr;
    if((arr=this.arr).length==size)
    {
      ArrCopy.uncheckedCopy(arr,0,arr=new double[OmniArray.growBy50Pct(size)],0,size);
      this.arr=arr;
    }
    arr[size]=val;
    this.size=size+1;
  }
  public void push(double val)
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
  private void uncheckedInsert(int size,int index,double val)
  {
    int tailDist;
    if((tailDist=size-index)==0)
    {
      uncheckedAppend(size,val);
    }
    else
    {
      double[] arr;
      if((arr=this.arr).length==size)
      {
        double[] newArr;
        ArrCopy.semicheckedCopy(arr,0,newArr=new double[OmniArray.growBy50Pct(size)],0,index);
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
  public void push(Double val)
  {
    push((double)val);
  }
  private double uncheckedremoveAtIndex(int index,int size)
  {
    final double[] arr;
    final double ret=(double)(arr=this.arr)[index];
    ArrCopy.semicheckedSelfCopy(arr,index+1,index,(--size)-index);
    this.size=size;
    return ret;
  }
  public static class UncheckedArrStack extends DoubleArrSeq implements OmniStack.OfDouble
  {
    public UncheckedArrStack()
    {
      super();
    }
    public UncheckedArrStack(int initialCapacity)
    {
      super(initialCapacity);
    }
    public UncheckedArrStack(int size,double[] arr)
    {
      super(size,arr);
    }
    @Override
    public double popDouble()
    {
      return (double)arr[--this.size];
    }
    @Override
    public Double pop()
    {
      return popDouble();
    }
    @Override
    public Object clone()
    {
      double[] copy;
      int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new double[size],0,size);
      }
      else
      {
        copy=OmniArray.OfDouble.DEFAULT_ARR;
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
          return uncheckedsearchBits(size,TypeUtil.DBL_TRUE_BITS);
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
          {
            return uncheckedsearchBits(size,Double.doubleToRawLongBits(val));
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
          if(TypeUtil.checkCastToDouble(val))
          {
            return uncheckedsearchBits(size,Double.doubleToRawLongBits(val));
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
          return uncheckedsearchBits(size,Double.doubleToRawLongBits(val));
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
        if(val==val)
        {
          return uncheckedsearchBits(size,Double.doubleToRawLongBits(val));
        }
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
        if(val instanceof Double)
        {
          final double v;
          if((v=(double)val)==v)
          {
            return uncheckedsearchBits(size,Double.doubleToRawLongBits(v));
          }
          return uncheckedsearchNaN(size);
        }
      }
      return -1;
    }
    @Override
    public double pollDouble()
    {
      int size;
      if((size=this.size)!=0)
      {
        final double ret=(double)((double)arr[--size]);
        this.size=size;
        return ret;
      }
      return Double.NaN;
    }
    @Override
    public Double poll()
    {
      int size;
      if((size=this.size)!=0)
      {
        final Double ret=(Double)((double)arr[--size]);
        this.size=size;
        return ret;
      }
      return null;
    }
    public double peekDouble()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (double)((double)arr[size-1]);
      }
      return Double.NaN;
    }
    public Double peek()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (Double)((double)arr[size-1]);
      }
      return null;
    }
    @Override
    void uncheckedCopyInto(double[] dst,int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(Double[] dst,int size)
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
    ,long bits
    )
    {
      final var arr=this.arr;
      for(int i=--size;;)
      {
        if(
        bits==Double.doubleToRawLongBits(arr[i])
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
        Double.isNaN(arr[i])
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
    ,long bits
    )
    {
      final var arr=this.arr;
      for(int i=size-1;;--i)
      {
        if(
        bits==Double.doubleToRawLongBits(arr[i])
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
        Double.isNaN(arr[i])
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
      final double[] arr;
      int hash;
      for(hash=31+HashUtil.hashDouble((arr=this.arr)[--size]);size!=0;hash=hash*31+HashUtil.hashDouble(arr[--size])){}
      return hash;
    }
    @Override
    void uncheckedToString(int size,StringBuilder builder)
    {
      final double[] arr;
      for(builder.append((arr=this.arr)[--size]);size!=0;builder.append(',').append(' ').append(arr[--size])){}
    }
    @Override
    void uncheckedForEach(int size,DoubleConsumer action)
    {
      final var arr=this.arr;
      for(;;)
      {
        action.accept((double)arr[--size]);
        if(size==0)
        {
          return;
        }
      }
    }
    @Override
    boolean uncheckedRemoveIf(int size,DoublePredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public OmniIterator.OfDouble iterator()
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
    public CheckedArrStack(int size,double[] arr)
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
    public double popDouble()
    {
      int size;
      if((size=this.size)!=0)
      {
        ++this.modCount;
        final double ret=(double)arr[--size];
        this.size=size;
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override
    public Object clone()
    {
      double[] copy;
      int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new double[size],0,size);
      }
      else
      {
        copy=OmniArray.OfDouble.DEFAULT_ARR;
      }
      return new CheckedArrStack(size,copy);
    }
    @Override
    public double pollDouble()
    {
      int size;
      if((size=this.size)!=0)
      {
        final double ret=(double)((double)arr[--size]);
        ++this.modCount;
        this.size=size;
        return ret;
      }
      return Double.NaN;
    }
    @Override
    public Double poll()
    {
      int size;
      if((size=this.size)!=0)
      {
        final Double ret=(Double)((double)arr[--size]);
        ++this.modCount;
        this.size=size;
        return ret;
      }
      return null;
    }
    @Override
    boolean
    uncheckedremoveValBits(int size
    ,long bits
    )
    {
      final var arr=this.arr;
      for(int i=--size;;)
      {
        if(
        bits==Double.doubleToRawLongBits(arr[i])
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
        Double.isNaN(arr[i])
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
    void uncheckedForEach(int size,DoubleConsumer action)
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
    boolean uncheckedRemoveIf(int size,DoublePredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public void push(double val)
    {
      ++this.modCount;
      super.push(val);
    }
    @Override
    public OmniIterator.OfDouble iterator()
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
  private static class UncheckedArrList extends DoubleArrSeq implements OmniList.OfDouble
  {
    private static class SubListImpl implements OmniList.OfDouble
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
        final double[] copy;
        int size;
        if((size=this.size)!=0)
        {
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new double[size],0,size);
        }
        else
        {
          copy=OmniArray.OfDouble.DEFAULT_ARR;
        }
        return new UncheckedArrList(size,copy);
      }
      @Override
      public boolean add(double val)
      {
        int rootSize;
        final UncheckedArrList root;
        if((rootSize=(root=this.root).size)!=0)
        {
          for(var curr=parent;curr!=null;++parent.size){}
          ((DoubleArrSeq)root).uncheckedInsert(rootSize,this.rootOffset,val);
          ++this.size;
        }
        else
        {
          for(var curr=parent;curr!=null;parent.size=1){}
          ((DoubleArrSeq)root).uncheckedInit(val);
          this.size=1;
        }
        return true;
      }
      @Override
      public void add(int index,double val)
      {
        int rootSize;
        final UncheckedArrList root;
        if((rootSize=(root=this.root).size)!=0)
        {
          for(var curr=parent;curr!=null;++parent.size){}
          ((DoubleArrSeq)root).uncheckedInsert(rootSize,index+this.rootOffset,val);
          ++this.size;
        }
        else
        {
          for(var curr=parent;curr!=null;parent.size=1){}
          ((DoubleArrSeq)root).uncheckedInit(val);
          this.size=1;
        }
      }
      @Override
      public boolean add(Double val)
      {
        return add((double)val);
      }
      @Override
      public void add(int index,Double val)
      {
        add(index,(double)val);
      }
      @Override
      public void forEach(DoubleConsumer action)
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
      public void forEach(Consumer<? super Double> action)
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
      public boolean removeIf(DoublePredicate filter)
      {
        //TODO
        return false;
      }
      @Override
      public boolean removeIf(Predicate<? super Double> action)
      {
        //TODO
        return false;
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
      public Double[] toArray()
      {
        final int size;
        if((size=this.size)!=0)
        {
          final Double[] copy;
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new Double[size],0,size);
          return copy;
        }
        return OmniArray.OfDouble.DEFAULT_BOXED_ARR;
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
      public void put(int index,double val)
      {
        root.arr[index+this.rootOffset]=val;
      }
      @Override
      public double getDouble(int index)
      {
        return (double)root.arr[index+this.rootOffset];
      }
      @Override
      public Double get(int index)
      {
        return getDouble(index);
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
      public OmniIterator.OfDouble iterator()
      {
        //TODO
        return null;
      }
      @Override
      public OmniListIterator.OfDouble listIterator()
      {
        //TODO
        return null;
      }
      @Override
      public OmniListIterator.OfDouble listIterator(int index)
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
            return ((UncheckedArrList)root).sublistcontainsBits(this.rootOffset,size,TypeUtil.DBL_TRUE_BITS);
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
            {
              return ((UncheckedArrList)root).sublistcontainsBits(this.rootOffset,size,Double.doubleToRawLongBits(val));
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
            if(TypeUtil.checkCastToDouble(val))
            {
              return ((UncheckedArrList)root).sublistcontainsBits(this.rootOffset,size,Double.doubleToRawLongBits(val));
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
            return ((UncheckedArrList)root).sublistcontainsBits(this.rootOffset,size,Double.doubleToRawLongBits(val));
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
          if(val==val)
          {
            return ((UncheckedArrList)root).sublistcontainsBits(this.rootOffset,size,Double.doubleToRawLongBits(val));
          }
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
          if(val instanceof Double)
          {
            final double v;
            if((v=(double)val)==v)
            {
              return ((UncheckedArrList)root).sublistcontainsBits(this.rootOffset,size,Double.doubleToRawLongBits(v));
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
              return ((UncheckedArrList)root).sublistcontainsBits(this.rootOffset,size,Double.doubleToRawLongBits(val));
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
              return ((UncheckedArrList)root).sublistcontainsBits(this.rootOffset,size,Double.doubleToRawLongBits(val));
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
              return ((UncheckedArrList)root).sublistcontainsBits(this.rootOffset,size,Double.doubleToRawLongBits(val));
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
            return uncheckedremoveValBits(size,TypeUtil.DBL_TRUE_BITS);
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
            {
              return uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
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
            if(TypeUtil.checkCastToDouble(val))
            {
              return uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
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
            return uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
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
          if(val==val)
          {
            return uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
          }
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
          if(val instanceof Double)
          {
            final double v;
            if((v=(double)val)==v)
            {
              return uncheckedremoveValBits(size,Double.doubleToRawLongBits(v));
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
              return uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
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
              return uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
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
              return uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
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
            return ((UncheckedArrList)root).sublistindexOfBits(this.rootOffset,size,TypeUtil.DBL_TRUE_BITS);
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
            {
              return ((UncheckedArrList)root).sublistindexOfBits(this.rootOffset,size,Double.doubleToRawLongBits(val));
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
            if(TypeUtil.checkCastToDouble(val))
            {
              return ((UncheckedArrList)root).sublistindexOfBits(this.rootOffset,size,Double.doubleToRawLongBits(val));
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
            return ((UncheckedArrList)root).sublistindexOfBits(this.rootOffset,size,Double.doubleToRawLongBits(val));
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
          if(val==val)
          {
            return ((UncheckedArrList)root).sublistindexOfBits(this.rootOffset,size,Double.doubleToRawLongBits(val));
          }
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
          if(val instanceof Double)
          {
            final double v;
            if((v=(double)val)==v)
            {
              return ((UncheckedArrList)root).sublistindexOfBits(this.rootOffset,size,Double.doubleToRawLongBits(v));
            }
            return ((UncheckedArrList)root).sublistindexOfNaN(this.rootOffset,size);
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
            return ((UncheckedArrList)root).sublistlastIndexOfBits(this.rootOffset,size,TypeUtil.DBL_TRUE_BITS);
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
            {
              return ((UncheckedArrList)root).sublistlastIndexOfBits(this.rootOffset,size,Double.doubleToRawLongBits(val));
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
            if(TypeUtil.checkCastToDouble(val))
            {
              return ((UncheckedArrList)root).sublistlastIndexOfBits(this.rootOffset,size,Double.doubleToRawLongBits(val));
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
            return ((UncheckedArrList)root).sublistlastIndexOfBits(this.rootOffset,size,Double.doubleToRawLongBits(val));
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
          if(val==val)
          {
            return ((UncheckedArrList)root).sublistlastIndexOfBits(this.rootOffset,size,Double.doubleToRawLongBits(val));
          }
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
          if(val instanceof Double)
          {
            final double v;
            if((v=(double)val)==v)
            {
              return ((UncheckedArrList)root).sublistlastIndexOfBits(this.rootOffset,size,Double.doubleToRawLongBits(v));
            }
            return ((UncheckedArrList)root).sublistlastIndexOfNaN(this.rootOffset,size);
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
          final StringBuilder builder;
          final double[] arr;
          int i;
          for(builder=new StringBuilder("[").append((arr=root.arr)[i=this.rootOffset]),size+=i;++i!=size;builder.append(',').append(' ').append(arr[i])){}
          return builder.append(']').toString();
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
          final double[] arr;
          int hash;
          int i;
          for(hash=31+HashUtil.hashDouble((arr=root.arr)[i=this.rootOffset]),size+=i;++i!=size;hash=hash*31+HashUtil.hashDouble(arr[i])){}
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
        ,long bits
        )
        {
          final UncheckedArrList root;
          final var arr=(root=this.root).arr;
          {
            for(int i=this.rootOffset,bound=i+size;;)
            {
              if(
              bits==Double.doubleToRawLongBits(arr[i])
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
              Double.isNaN(arr[i])
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
    public UncheckedArrList(int size,double[] arr)
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
    public double getDouble(int index)
    {
      return (double)arr[index];
    }
    @Override
    public Double get(int index)
    {
      return getDouble(index);
    }
    @Override
    public void put(int index,double val)
    {
      arr[index]=val;
    }
    @Override
    public Object clone()
    {
      double[] copy;
      int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new double[size],0,size);
      }
      else
      {
        copy=OmniArray.OfDouble.DEFAULT_ARR;
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
          return uncheckedindexOfBits(size,TypeUtil.DBL_TRUE_BITS);
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
          {
            return uncheckedindexOfBits(size,Double.doubleToRawLongBits(val));
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
          if(TypeUtil.checkCastToDouble(val))
          {
            return uncheckedindexOfBits(size,Double.doubleToRawLongBits(val));
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
          return uncheckedindexOfBits(size,Double.doubleToRawLongBits(val));
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
        if(val==val)
        {
          return uncheckedindexOfBits(size,Double.doubleToRawLongBits(val));
        }
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
        if(val instanceof Double)
        {
          final double v;
          if((v=(double)val)==v)
          {
            return uncheckedindexOfBits(size,Double.doubleToRawLongBits(v));
          }
          return uncheckedindexOfNaN(size);
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
          return uncheckedlastIndexOfBits(size,TypeUtil.DBL_TRUE_BITS);
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
          {
            return uncheckedlastIndexOfBits(size,Double.doubleToRawLongBits(val));
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
          if(TypeUtil.checkCastToDouble(val))
          {
            return uncheckedlastIndexOfBits(size,Double.doubleToRawLongBits(val));
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
          return uncheckedlastIndexOfBits(size,Double.doubleToRawLongBits(val));
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
        if(val==val)
        {
          return uncheckedlastIndexOfBits(size,Double.doubleToRawLongBits(val));
        }
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
        if(val instanceof Double)
        {
          final double v;
          if((v=(double)val)==v)
          {
            return uncheckedlastIndexOfBits(size,Double.doubleToRawLongBits(v));
          }
          return uncheckedlastIndexOfNaN(size);
        }
      }
      return -1;
    }
    @Override
    void uncheckedCopyInto(double[] dst,int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(Double[] dst,int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(Object[] dst,int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,size);
    }
    private boolean sublistcontainsBits(int rootOffset,int size
    ,long bits
    )
    {
      final var arr=this.arr;
      for(size+=rootOffset;;)
      {
        if(
        bits==Double.doubleToRawLongBits(arr[rootOffset])
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
        Double.isNaN(arr[rootOffset])
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
    ,long bits
    )
    {
      final var arr=this.arr;
      for(int i=0;;)
      {
        if(
        bits==Double.doubleToRawLongBits(arr[i])
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
        Double.isNaN(arr[i])
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
    ,long bits
    )
    {
      final var arr=this.arr;
      int i;
      for(size+=(i=rootOffset);;)
      {
        if(
        bits==Double.doubleToRawLongBits(arr[i])
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
    ,long bits
    )
    {
      final var arr=this.arr;
      for(int i=0;;)
      {
        if(
        bits==Double.doubleToRawLongBits(arr[i])
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
        Double.isNaN(arr[i])
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
        Double.isNaN(arr[i])
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
    ,long bits
    )
    {
      final var arr=this.arr;
      for(size+=(rootOffset-1);;--size)
      {
        if(
        bits==Double.doubleToRawLongBits(arr[size])
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
    ,long bits
    )
    {
      final var arr=this.arr;
      for(;;)
      {
        if(
        bits==Double.doubleToRawLongBits(arr[--size])
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
        Double.isNaN(arr[size])
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
        Double.isNaN(arr[--size])
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
      final double[] arr;
      int hash,i;
      for(hash=31+HashUtil.hashDouble((arr=this.arr)[i=0]);++i!=size;hash=hash*31+HashUtil.hashDouble(arr[i])){}
      return hash;
    }
    @Override
    void uncheckedToString(int size,StringBuilder builder)
    {
      final double[] arr;
      int i;
      for(builder.append((arr=this.arr)[i=0]);++i!=size;builder.append(',').append(' ').append(arr[i])){}
    }
    private void sublistForEach(int rootOffset,int size,DoubleConsumer action)
    {
      final var arr=this.arr;
      for(size+=rootOffset;;)
      {
        action.accept((double)arr[rootOffset]);
        if(++rootOffset==size)
        {
          return;
        }
      }
    }
    @Override
    void uncheckedForEach(int size,DoubleConsumer action)
    {
      final var arr=this.arr;
      for(int i=0;;)
      {
        action.accept((double)arr[i]);
        if(++i==size)
        {
          return;
        }
      }
    }
    @Override
    boolean uncheckedRemoveIf(int size,DoublePredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public OmniIterator.OfDouble iterator()
    {
      //TODO
      return null;
    }
    @Override
    public OmniListIterator.OfDouble listIterator()
    {
      //TODO
      return null;
    }
    @Override
    public OmniListIterator.OfDouble listIterator(int index)
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
    public void add(int index,double val)
    {
      uncheckedAdd(index,val,this.size);
    }
    @Override
    public void add(int index,Double val)
    {
      add(index,(double)val);
    }
    private void uncheckedAdd(int index,double val,int size)
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
    public Double remove(int index)
    {
      return removeDoubleAt(index);
    }
    @Override
    public double removeDoubleAt(int index)
    {
      return super.uncheckedremoveAtIndex(index,this.size);
    }
    @Override
    public void replaceAll(DoubleUnaryOperator operator)
    {
      int size;
      if((size=this.size)!=0)
      {
        uncheckedReplaceAll(size,operator);
      }
    }
    @Override
    public void replaceAll(UnaryOperator<Double> operator)
    {
      int size;
      if((size=this.size)!=0)
      {
        uncheckedReplaceAll(size,operator::apply);
      }
    }
    private void uncheckedReplaceAll(int size,DoubleUnaryOperator operator)
    {
      final var arr=this.arr;
      for(int i=0;;)
      {
        arr[i]=operator.applyAsDouble((double)arr[i]);
        if(++i==size)
        {
          return;
        }
      }
    }
    @Override
    public double set(int index,double val)
    {
      final double[] arr;
      final double ret=(double)(arr=this.arr)[index];
      arr[index]=val;
      return ret;
    }
    @Override
    public Double set(int index,Double val)
    {
      return set(index,(double)val);
    }
    @Override
    public void sort(DoubleComparator sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        if(sorter==null)
        {
          DoubleSortUtil.uncheckedAscendingSort(this.arr,0,size);
        }
        else
        {
          DoubleSortUtil.uncheckedStableSort(this.arr,0,size,sorter);
        }
      }
    }
    @Override
    public void sort(Comparator<? super Double> sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        if(sorter==null)
        {
          DoubleSortUtil.uncheckedAscendingSort(this.arr,0,size);
        }
        else
        {
          DoubleSortUtil.uncheckedStableSort(this.arr,0,size,sorter::compare);
        }
      }
    }
    @Override
    public void stableAscendingSort()
    {
      int size;
      if((size=this.size)>1)
      {
        DoubleSortUtil.uncheckedAscendingSort(this.arr,0,size);
      }
    }
    @Override
    public void stableDescendingSort()
    {
      int size;
      if((size=this.size)>1)
      {
        DoubleSortUtil.uncheckedDescendingSort(this.arr,0,size);
      }
    }
    @Override
    public OmniList.OfDouble subList(int fromIndex,int toIndex)
    {
      //TODO
      return null;
    }
    @Override
    public void unstableSort(DoubleComparator sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        if(sorter==null)
        {
          DoubleSortUtil.uncheckedAscendingSort(this.arr,0,size);
        }
        else
        {
          DoubleSortUtil.uncheckedUnstableSort(this.arr,0,size,sorter);
        }
      }
    }
  }
  public static class CheckedArrList extends UncheckedArrList
  {
    private static class SubListImpl implements OmniList.OfDouble
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
        final double[] copy;
        int size;
        if((size=this.size)!=0)
        {
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new double[size],0,size);
        }
        else
        {
          copy=OmniArray.OfDouble.DEFAULT_ARR;
        }
        return new CheckedArrList(size,copy);
      }
      @Override
      public boolean add(double val)
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
          ((DoubleArrSeq)root).uncheckedInsert(rootSize,this.rootOffset,val);
          ++this.size;
        }
        else
        {
          for(var curr=parent;curr!=null;parent.modCount=modCount,parent.size=1){}
          ((DoubleArrSeq)root).uncheckedInit(val);
          this.size=1;
        }
        return true;
      }
      @Override
      public void add(int index,double val)
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
          ((DoubleArrSeq)root).uncheckedInsert(rootSize,index+this.rootOffset,val);
          ++this.size;
        }
        else
        {
          for(var curr=parent;curr!=null;parent.modCount=modCount,parent.size=1){}
          ((DoubleArrSeq)root).uncheckedInit(val);
          this.size=1;
        }
      }
      @Override
      public boolean add(Double val)
      {
        return add((double)val);
      }
      @Override
      public void add(int index,Double val)
      {
        add(index,(double)val);
      }
      @Override
      public void forEach(DoubleConsumer action)
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
      public void forEach(Consumer<? super Double> action)
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
      public boolean removeIf(DoublePredicate filter)
      {
        //TODO
        return false;
      }
      @Override
      public boolean removeIf(Predicate<? super Double> action)
      {
        //TODO
        return false;
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
      public Double[] toArray()
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        final int size;
        if((size=this.size)!=0)
        {
          final Double[] copy;
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new Double[size],0,size);
          return copy;
        }
        return OmniArray.OfDouble.DEFAULT_BOXED_ARR;
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
      public void put(int index,double val)
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
      public double getDouble(int index)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        if(index<0 || index>=size)
        {
          throw new InvalidReadIndexException(index,size);
        }
        return (double)root.arr[index+this.rootOffset];
      }
      @Override
      public Double get(int index)
      {
        return getDouble(index);
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
      public OmniIterator.OfDouble iterator()
      {
        //TODO
        return null;
      }
      @Override
      public OmniListIterator.OfDouble listIterator()
      {
        //TODO
        return null;
      }
      @Override
      public OmniListIterator.OfDouble listIterator(int index)
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
            return ((UncheckedArrList)root).sublistcontainsBits(this.rootOffset,size,TypeUtil.DBL_TRUE_BITS);
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
            {
              return ((UncheckedArrList)root).sublistcontainsBits(this.rootOffset,size,Double.doubleToRawLongBits(val));
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
            if(TypeUtil.checkCastToDouble(val))
            {
              return ((UncheckedArrList)root).sublistcontainsBits(this.rootOffset,size,Double.doubleToRawLongBits(val));
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
            return ((UncheckedArrList)root).sublistcontainsBits(this.rootOffset,size,Double.doubleToRawLongBits(val));
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
          if(val==val)
          {
            return ((UncheckedArrList)root).sublistcontainsBits(this.rootOffset,size,Double.doubleToRawLongBits(val));
          }
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
          if(val instanceof Double)
          {
            final double v;
            if((v=(double)val)==v)
            {
              return ((UncheckedArrList)root).sublistcontainsBits(this.rootOffset,size,Double.doubleToRawLongBits(v));
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
              return ((UncheckedArrList)root).sublistcontainsBits(this.rootOffset,size,Double.doubleToRawLongBits(val));
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
              return ((UncheckedArrList)root).sublistcontainsBits(this.rootOffset,size,Double.doubleToRawLongBits(val));
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
              return ((UncheckedArrList)root).sublistcontainsBits(this.rootOffset,size,Double.doubleToRawLongBits(val));
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
            return uncheckedremoveValBits(size,TypeUtil.DBL_TRUE_BITS);
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
            {
              return uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
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
            if(TypeUtil.checkCastToDouble(val))
            {
              return uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
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
            return uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
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
          if(val==val)
          {
            return uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
          }
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
          if(val instanceof Double)
          {
            final double v;
            if((v=(double)val)==v)
            {
              return uncheckedremoveValBits(size,Double.doubleToRawLongBits(v));
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
              return uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
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
              return uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
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
              return uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
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
            return ((UncheckedArrList)root).sublistindexOfBits(this.rootOffset,size,TypeUtil.DBL_TRUE_BITS);
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
            {
              return ((UncheckedArrList)root).sublistindexOfBits(this.rootOffset,size,Double.doubleToRawLongBits(val));
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
            if(TypeUtil.checkCastToDouble(val))
            {
              return ((UncheckedArrList)root).sublistindexOfBits(this.rootOffset,size,Double.doubleToRawLongBits(val));
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
            return ((UncheckedArrList)root).sublistindexOfBits(this.rootOffset,size,Double.doubleToRawLongBits(val));
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
          if(val==val)
          {
            return ((UncheckedArrList)root).sublistindexOfBits(this.rootOffset,size,Double.doubleToRawLongBits(val));
          }
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
          if(val instanceof Double)
          {
            final double v;
            if((v=(double)val)==v)
            {
              return ((UncheckedArrList)root).sublistindexOfBits(this.rootOffset,size,Double.doubleToRawLongBits(v));
            }
            return ((UncheckedArrList)root).sublistindexOfNaN(this.rootOffset,size);
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
            return ((UncheckedArrList)root).sublistlastIndexOfBits(this.rootOffset,size,TypeUtil.DBL_TRUE_BITS);
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
            {
              return ((UncheckedArrList)root).sublistlastIndexOfBits(this.rootOffset,size,Double.doubleToRawLongBits(val));
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
            if(TypeUtil.checkCastToDouble(val))
            {
              return ((UncheckedArrList)root).sublistlastIndexOfBits(this.rootOffset,size,Double.doubleToRawLongBits(val));
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
            return ((UncheckedArrList)root).sublistlastIndexOfBits(this.rootOffset,size,Double.doubleToRawLongBits(val));
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
          if(val==val)
          {
            return ((UncheckedArrList)root).sublistlastIndexOfBits(this.rootOffset,size,Double.doubleToRawLongBits(val));
          }
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
          if(val instanceof Double)
          {
            final double v;
            if((v=(double)val)==v)
            {
              return ((UncheckedArrList)root).sublistlastIndexOfBits(this.rootOffset,size,Double.doubleToRawLongBits(v));
            }
            return ((UncheckedArrList)root).sublistlastIndexOfNaN(this.rootOffset,size);
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
          final StringBuilder builder;
          final double[] arr;
          int i;
          for(builder=new StringBuilder("[").append((arr=root.arr)[i=this.rootOffset]),size+=i;++i!=size;builder.append(',').append(' ').append(arr[i])){}
          return builder.append(']').toString();
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
          final double[] arr;
          int hash;
          int i;
          for(hash=31+HashUtil.hashDouble((arr=root.arr)[i=this.rootOffset]),size+=i;++i!=size;hash=hash*31+HashUtil.hashDouble(arr[i])){}
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
        ,long bits
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
              bits==Double.doubleToRawLongBits(arr[i])
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
              Double.isNaN(arr[i])
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
    public CheckedArrList(int size,double[] arr)
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
      double[] copy;
      int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new double[size],0,size);
      }
      else
      {
        copy=OmniArray.OfDouble.DEFAULT_ARR;
      }
      return new CheckedArrList(size,copy);
    }
    @Override
    public void push(double val)
    {
      ++this.modCount;
      super.push(val);
    }
    @Override
    public void put(int index,double val)
    {
      if(index>=0 && index<size)
      {
        super.put(index,val);
        return;
      }
      throw new InvalidWriteIndexException(index,size);
    }
    @Override
    public double set(int index,double val)
    {
      if(index>=0 && index<size)
      {
        return super.set(index,val);
      }
      throw new InvalidReadIndexException(index,size);
    }
    @Override
    public double removeDoubleAt(int index)
    {
      int size;
      if((size=this.size)>index && index>=0)
      {
        ++this.modCount;
        return ((DoubleArrSeq)this).uncheckedremoveAtIndex(index,size);
      }
      throw new InvalidWriteIndexException(index,size);
    }
    @Override
    public void unstableSort(DoubleComparator sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        if(sorter==null)
        {
          DoubleSortUtil.uncheckedAscendingSort(this.arr,0,size);
          ++this.modCount;
        }
        else
        {
          int modCount=this.modCount;
          try
          {
            DoubleSortUtil.uncheckedUnstableSort(this.arr,0,size,sorter);
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
    public void replaceAll(UnaryOperator<Double> operator)
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
    public void replaceAll(DoubleUnaryOperator operator)
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
        DoubleSortUtil.uncheckedAscendingSort(this.arr,0,size);
        ++this.modCount;
      }
    }
    @Override
    public void stableDescendingSort()
    {
      int size;
      if((size=this.size)>1)
      {
        DoubleSortUtil.uncheckedDescendingSort(this.arr,0,size);
        ++this.modCount;
      }
    }
    @Override
    public OmniList.OfDouble subList(int fromIndex,int toIndex)
    {
      //TODO
      return null;
    }
    @Override
    public void sort(DoubleComparator sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        if(sorter==null)
        {
          DoubleSortUtil.uncheckedAscendingSort(this.arr,0,size);
          ++this.modCount;
        }
        else
        {
          int modCount=this.modCount;
          try
          {
            DoubleSortUtil.uncheckedStableSort(this.arr,0,size,sorter);
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
    public void sort(Comparator<? super Double> sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        if(sorter==null)
        {
          DoubleSortUtil.uncheckedAscendingSort(this.arr,0,size);
          ++this.modCount;
        }
        else
        {
          int modCount=this.modCount;
          try
          {
            DoubleSortUtil.uncheckedStableSort(this.arr,0,size,sorter::compare);
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
    ,long bits
    )
    {
      final var arr=this.arr;
      for(int i=0;;)
      {
        if(
        bits==Double.doubleToRawLongBits(arr[i])
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
        Double.isNaN(arr[i])
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
    void uncheckedForEach(int size,DoubleConsumer action)
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
    boolean uncheckedRemoveIf(int size,DoublePredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public OmniIterator.OfDouble iterator()
    {
      //TODO
      return null;
    }
    @Override
    public OmniListIterator.OfDouble listIterator()
    {
      //TODO
      return null;
    }
    @Override
    public OmniListIterator.OfDouble listIterator(int index)
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
    public double getDouble(int index)
    {
      if(index>=0 && index<size)
      {
        return super.getDouble(index);
      }
      throw new InvalidReadIndexException(index,size);
    }
    @Override
    public void add(int index,double val)
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
