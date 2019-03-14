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
import java.util.ConcurrentModificationException;
import java.util.function.DoubleUnaryOperator;
import omni.function.DoubleComparator;
import java.util.function.DoublePredicate;
import java.util.function.DoubleConsumer;
import omni.util.HashUtil;
import omni.util.BitSetUtil;
import omni.impl.AbstractDoubleItr;
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
      if(size>(Integer.MAX_VALUE/5))
      {
        throw new OutOfMemoryError();
      }
      final StringBuilder builder=new StringBuilder("[");
      uncheckedToString(size,builder);
      return builder.append(']').toString();
    }
    return "[]";
  }
  abstract int uncheckedHashCode(int size);
  abstract void uncheckedToString(int size,StringBuilder builder);
  @Override
  public boolean contains(boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val)
      {
        return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,TypeUtil.DBL_TRUE_BITS);
      }
      return OmniArray.OfDouble.uncheckedcontains0(this.arr,0,size-1);
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
        if(val!=0)
        {
          {
            return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,Double.doubleToRawLongBits(val));
          }
        }
        else
        {
          return OmniArray.OfDouble.uncheckedcontains0(this.arr,0,size-1);
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
      if(val!=0)
      {
        if(TypeUtil.checkCastToDouble(val))
        {
          return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,Double.doubleToRawLongBits(val));
        }
      }
      else
      {
        return OmniArray.OfDouble.uncheckedcontains0(this.arr,0,size-1);
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
      if(val==val)
      {
        return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,Double.doubleToRawLongBits(val));
      }
      return OmniArray.OfDouble.uncheckedcontainsNaN(this.arr,0,size-1);
    }
    return false;
  }
  @Override
  public boolean contains(double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val==val)
      {
        return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,Double.doubleToRawLongBits(val));
      }
      return OmniArray.OfDouble.uncheckedcontainsNaN(this.arr,0,size-1);
    }
    return false;
  }
  @Override
  public boolean contains(Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Double)
      {
        final double v;
        if((v=(double)val)==v)
        {
          return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,Double.doubleToRawLongBits(v));
        }
        return OmniArray.OfDouble.uncheckedcontainsNaN(this.arr,0,size-1);
      }
    }
    return false;
  }
  @Override
  public boolean contains(byte val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=0)
        {
          return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,Double.doubleToRawLongBits(val));
        }
        return OmniArray.OfDouble.uncheckedcontains0(this.arr,0,size-1);
      }
    }
    return false;
  }
  @Override
  public boolean contains(char val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=0)
        {
          return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,Double.doubleToRawLongBits(val));
        }
        return OmniArray.OfDouble.uncheckedcontains0(this.arr,0,size-1);
      }
    }
    return false;
  }
  @Override
  public boolean contains(short val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=0)
        {
          return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,Double.doubleToRawLongBits(val));
        }
        return OmniArray.OfDouble.uncheckedcontains0(this.arr,0,size-1);
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
      if(val)
      {
        return this.uncheckedremoveValBits(size,TypeUtil.DBL_TRUE_BITS);
      }
      return this.uncheckedremoveVal0(size);
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
        if(val!=0)
        {
          {
            return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
          }
        }
        else
        {
          return this.uncheckedremoveVal0(size);
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
      if(val!=0)
      {
        if(TypeUtil.checkCastToDouble(val))
        {
          return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
        }
      }
      else
      {
        return this.uncheckedremoveVal0(size);
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
      if(val==val)
      {
        return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
      }
      return this.uncheckedremoveValNaN(size);
    }
    return false;
  }
  @Override
  public boolean removeVal(double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val==val)
      {
        return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
      }
      return this.uncheckedremoveValNaN(size);
    }
    return false;
  }
  @Override
  public boolean remove(Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Double)
      {
        final double v;
        if((v=(double)val)==v)
        {
          return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(v));
        }
        return this.uncheckedremoveValNaN(size);
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(byte val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=0)
        {
          return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
        }
        return this.uncheckedremoveVal0(size);
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(char val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=0)
        {
          return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
        }
        return this.uncheckedremoveVal0(size);
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(short val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=0)
        {
          return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
        }
        return this.uncheckedremoveVal0(size);
      }
    }
    return false;
  }
  abstract boolean uncheckedremoveValBits(int size,long bits);
  abstract boolean uncheckedremoveVal0(int size);
  abstract boolean uncheckedremoveValNaN(int size);
  abstract void uncheckedForEach(int size,DoubleConsumer action);
  @Override
  public void forEach(DoubleConsumer action)
  {
    final int size;
    if((size=this.size)!=0)
    {
      uncheckedForEach(size,action);
    }
  }
  @Override
  public void forEach(Consumer<? super Double> action)
  {
    final int size;
    if((size=this.size)!=0)
    {
      uncheckedForEach(size,action::accept);
    }
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
  private void uncheckedInit(double val)
  {
    double[] arr;
    if((arr=this.arr)==null)
    {
      this.arr=new double[]{val};
    }
    else
    {
      if(arr==OmniArray.OfDouble.DEFAULT_ARR)
      {
        this.arr=arr=new double[OmniArray.DEFAULT_ARR_SEQ_CAP];
      }
      arr[0]=val;
    }
    this.size=1;
  }
  private void uncheckedInsert(int index,int size,double val)
  {
    final int tailDist;
    if((tailDist=size-index)==0)
    {
      uncheckedAppend(size,val);
    }
    else
    {
      double[] arr;
      if((arr=this.arr).length==size)
      {
        final double[] tmp;
        ArrCopy.semicheckedCopy(arr,0,tmp=new double[OmniArray.growBy50Pct(size)],0,index);
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
  public void push(double val)
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
  public boolean add(boolean val)
  {
    push((double)TypeUtil.castToDouble(val));
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
  public boolean add(short val)
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
  public boolean add(float val)
  {
    push((double)val);
    return true;
  }
  abstract void uncheckedCopyInto(double[] dst,int length);
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
  abstract void uncheckedCopyInto(Object[] dst,int length);
  abstract void uncheckedCopyInto(Double[] dst,int length);
  @Override
  public Double[] toArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final Double[] dst;
      uncheckedCopyInto(dst=new Double[size],size);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_BOXED_ARR;
  }
  public
    static class UncheckedStack
      extends DoubleArrSeq
      implements OmniStack.OfDouble,Cloneable
  {
    public UncheckedStack()
    {
      super();
    }
    public UncheckedStack(int initialCapacity)
    {
      super(initialCapacity);
    }
    private UncheckedStack(int size,double[] arr)
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
      final double[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new double[size],0,size);
      }
      else
      {
        copy=OmniArray.OfDouble.DEFAULT_ARR;
      }
      return new UncheckedStack(size,copy);
    }
    @Override
    void uncheckedToString(int size,StringBuilder builder)
    {
      OmniArray.OfDouble.descendingToString(this.arr,0,size-1,builder);
    }
    @Override
    int uncheckedHashCode(int size)
    {
      return OmniArray.OfDouble.descendingSeqHashCode(this.arr,0,size-1);
    }
    @Override
    public int search(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val)
        {
          return OmniArray.OfDouble.uncheckedsearchBits(this.arr,size,TypeUtil.DBL_TRUE_BITS);
        }
        return OmniArray.OfDouble.uncheckedsearch0(this.arr,size);
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
          if(val!=0)
          {
            {
              return OmniArray.OfDouble.uncheckedsearchBits(this.arr,size,Double.doubleToRawLongBits(val));
            }
          }
          else
          {
            return OmniArray.OfDouble.uncheckedsearch0(this.arr,size);
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
        if(val!=0)
        {
          if(TypeUtil.checkCastToDouble(val))
          {
            return OmniArray.OfDouble.uncheckedsearchBits(this.arr,size,Double.doubleToRawLongBits(val));
          }
        }
        else
        {
          return OmniArray.OfDouble.uncheckedsearch0(this.arr,size);
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
        if(val==val)
        {
          return OmniArray.OfDouble.uncheckedsearchBits(this.arr,size,Double.doubleToRawLongBits(val));
        }
        return OmniArray.OfDouble.uncheckedsearchNaN(this.arr,size);
      }
      return -1;
    }
    @Override
    public int search(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val==val)
        {
          return OmniArray.OfDouble.uncheckedsearchBits(this.arr,size,Double.doubleToRawLongBits(val));
        }
        return OmniArray.OfDouble.uncheckedsearchNaN(this.arr,size);
      }
      return -1;
    }
    @Override
    public int search(Object val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Double)
        {
          final double v;
          if((v=(double)val)==v)
          {
            return OmniArray.OfDouble.uncheckedsearchBits(this.arr,size,Double.doubleToRawLongBits(v));
          }
          return OmniArray.OfDouble.uncheckedsearchNaN(this.arr,size);
        }
      }
      return -1;
    }
    @Override
    boolean uncheckedremoveValBits(int size
    ,long bits
    )
    {
      final var arr=this.arr;
      for(int index=--size;;--index)
      {
        if(
        bits==Double.doubleToRawLongBits(arr[index])
        )
        {
          OmniArray.OfDouble.removeIndexAndPullDown(arr,index,size);
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
    boolean uncheckedremoveValNaN(int size
    )
    {
      final var arr=this.arr;
      for(int index=--size;;--index)
      {
        if(
        Double.isNaN(arr[index])
        )
        {
          OmniArray.OfDouble.removeIndexAndPullDown(arr,index,size);
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
    boolean uncheckedremoveVal0(int size
    )
    {
      final var arr=this.arr;
      for(int index=--size;;--index)
      {
        if(
        arr[index]==0
        )
        {
          OmniArray.OfDouble.removeIndexAndPullDown(arr,index,size);
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
    void uncheckedForEach(int size,DoubleConsumer action)
    {
      {
        OmniArray.OfDouble.descendingForEach(this.arr,0,size-1,action);
      }
    }
    private static class Itr
      extends AbstractDoubleItr
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
      public double nextDouble()
      {
        return (double)parent.arr[cursor--];
      }
      @Override
      public void remove()
      {
        final UncheckedStack root;
        OmniArray.OfDouble.removeIndexAndPullDown((root=this.parent).arr,this.cursor,--root.size);
      }
      @Override
      public void forEachRemaining(DoubleConsumer action)
      {
        final int cursor;
        if((cursor=this.cursor)>=0)
        {
          OmniArray.OfDouble.descendingForEach(parent.arr,0,cursor,action);
          this.cursor=-1;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Double> action)
      {
        final int cursor;
        if((cursor=this.cursor)>=0)
        {
          OmniArray.OfDouble.descendingForEach(parent.arr,0,cursor,action::accept);
          this.cursor=-1;
        }
      }
    }
    @Override
    public OmniIterator.OfDouble iterator()
    {
      return new Itr(this);
    }
    @Override
    public void push(Double val)
    {
      push((double)val);
    }
    @Override
    void uncheckedCopyInto(double[] dst,int length)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(Object[] dst,int length)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(Double[] dst,int length)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override
    public Double pop()
    {
      return popDouble();
    }
    @Override
    public double popDouble()
    {
      return (double)arr[--this.size];
    }
  }
  public
    static class UncheckedList
      extends DoubleArrSeq
      implements DoubleListDefault,Cloneable
  {
    public UncheckedList()
    {
      super();
    }
    public UncheckedList(int initialCapacity)
    {
      super(initialCapacity);
    }
    private UncheckedList(int size,double[] arr)
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
      final double[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new double[size],0,size);
      }
      else
      {
        copy=OmniArray.OfDouble.DEFAULT_ARR;
      }
      return new UncheckedList(size,copy);
    }
    @Override
    void uncheckedToString(int size,StringBuilder builder)
    {
      OmniArray.OfDouble.ascendingToString(this.arr,0,size-1,builder);
    }
    @Override
    int uncheckedHashCode(int size)
    {
      return OmniArray.OfDouble.ascendingSeqHashCode(this.arr,0,size-1);
    }
    @Override
    public int indexOf(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val)
        {
          return OmniArray.OfDouble.uncheckedindexOfBits(this.arr,size,TypeUtil.DBL_TRUE_BITS);
        }
        return OmniArray.OfDouble.uncheckedindexOf0(this.arr,size);
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
          if(val!=0)
          {
            {
              return OmniArray.OfDouble.uncheckedindexOfBits(this.arr,size,Double.doubleToRawLongBits(val));
            }
          }
          else
          {
            return OmniArray.OfDouble.uncheckedindexOf0(this.arr,size);
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
        if(val!=0)
        {
          if(TypeUtil.checkCastToDouble(val))
          {
            return OmniArray.OfDouble.uncheckedindexOfBits(this.arr,size,Double.doubleToRawLongBits(val));
          }
        }
        else
        {
          return OmniArray.OfDouble.uncheckedindexOf0(this.arr,size);
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
        if(val==val)
        {
          return OmniArray.OfDouble.uncheckedindexOfBits(this.arr,size,Double.doubleToRawLongBits(val));
        }
        return OmniArray.OfDouble.uncheckedindexOfNaN(this.arr,size);
      }
      return -1;
    }
    @Override
    public int indexOf(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val==val)
        {
          return OmniArray.OfDouble.uncheckedindexOfBits(this.arr,size,Double.doubleToRawLongBits(val));
        }
        return OmniArray.OfDouble.uncheckedindexOfNaN(this.arr,size);
      }
      return -1;
    }
    @Override
    public int indexOf(Object val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Double)
        {
          final double v;
          if((v=(double)val)==v)
          {
            return OmniArray.OfDouble.uncheckedindexOfBits(this.arr,size,Double.doubleToRawLongBits(v));
          }
          return OmniArray.OfDouble.uncheckedindexOfNaN(this.arr,size);
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
        if(val)
        {
          return OmniArray.OfDouble.uncheckedlastIndexOfBits(this.arr,size,TypeUtil.DBL_TRUE_BITS);
        }
        return OmniArray.OfDouble.uncheckedlastIndexOf0(this.arr,size);
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
          if(val!=0)
          {
            {
              return OmniArray.OfDouble.uncheckedlastIndexOfBits(this.arr,size,Double.doubleToRawLongBits(val));
            }
          }
          else
          {
            return OmniArray.OfDouble.uncheckedlastIndexOf0(this.arr,size);
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
        if(val!=0)
        {
          if(TypeUtil.checkCastToDouble(val))
          {
            return OmniArray.OfDouble.uncheckedlastIndexOfBits(this.arr,size,Double.doubleToRawLongBits(val));
          }
        }
        else
        {
          return OmniArray.OfDouble.uncheckedlastIndexOf0(this.arr,size);
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
        if(val==val)
        {
          return OmniArray.OfDouble.uncheckedlastIndexOfBits(this.arr,size,Double.doubleToRawLongBits(val));
        }
        return OmniArray.OfDouble.uncheckedlastIndexOfNaN(this.arr,size);
      }
      return -1;
    }
    @Override
    public int lastIndexOf(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val==val)
        {
          return OmniArray.OfDouble.uncheckedlastIndexOfBits(this.arr,size,Double.doubleToRawLongBits(val));
        }
        return OmniArray.OfDouble.uncheckedlastIndexOfNaN(this.arr,size);
      }
      return -1;
    }
    @Override
    public int lastIndexOf(Object val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Double)
        {
          final double v;
          if((v=(double)val)==v)
          {
            return OmniArray.OfDouble.uncheckedlastIndexOfBits(this.arr,size,Double.doubleToRawLongBits(v));
          }
          return OmniArray.OfDouble.uncheckedlastIndexOfNaN(this.arr,size);
        }
      }
      return -1;
    }
    @Override
    boolean uncheckedremoveValBits(int size
    ,long bits
    )
    {
      final var arr=this.arr;
      for(int index=0;;)
      {
        if(
        bits==Double.doubleToRawLongBits(arr[index])
        )
        {
          OmniArray.OfDouble.removeIndexAndPullDown(arr,index,--size);
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
    boolean uncheckedremoveValNaN(int size
    )
    {
      final var arr=this.arr;
      for(int index=0;;)
      {
        if(
        Double.isNaN(arr[index])
        )
        {
          OmniArray.OfDouble.removeIndexAndPullDown(arr,index,--size);
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
    boolean uncheckedremoveVal0(int size
    )
    {
      final var arr=this.arr;
      for(int index=0;;)
      {
        if(
        arr[index]==0
        )
        {
          OmniArray.OfDouble.removeIndexAndPullDown(arr,index,--size);
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
    void uncheckedForEach(int size,DoubleConsumer action)
    {
      {
        OmniArray.OfDouble.ascendingForEach(this.arr,0,size-1,action);
      }
    }
    private static class Itr
      extends AbstractDoubleItr
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
      public double nextDouble()
      {
        return (double)parent.arr[cursor++];
      }
      @Override
      public void remove()
      {
        final UncheckedList root;
        OmniArray.OfDouble.removeIndexAndPullDown((root=this.parent).arr,--this.cursor,--root.size);
      }
      @Override
      public void forEachRemaining(DoubleConsumer action)
      {
        final int cursor,bound;
        final UncheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size))
        {
          OmniArray.OfDouble.ascendingForEach(parent.arr,cursor,bound-1,action);
          this.cursor=bound;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Double> action)
      {
        final int cursor,bound;
        final UncheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size))
        {
          OmniArray.OfDouble.ascendingForEach(parent.arr,cursor,bound-1,action::accept);
          this.cursor=bound;
        }
      }
    }
    @Override
    public OmniIterator.OfDouble iterator()
    {
      return new Itr(this);
    }
    private static class ListItr extends Itr implements OmniListIterator.OfDouble
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
      public double nextDouble()
      {
        int lastRet;
        this.lastRet=lastRet=this.cursor++;
        return (double)parent.arr[lastRet];
      }
      @Override
      public void remove()
      {
        final UncheckedList root;
        final int lastRet;
        OmniArray.OfDouble.removeIndexAndPullDown((root=this.parent).arr,lastRet=this.lastRet,--root.size);
        this.cursor=lastRet;
      }
      @Override
      public void forEachRemaining(DoubleConsumer action)
      {
        int cursor;
        final int bound;
        final UncheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size))
        {
          OmniArray.OfDouble.ascendingForEach(parent.arr,cursor,cursor=bound-1,action);
          this.lastRet=cursor;
          this.cursor=bound;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Double> action)
      {
        final int cursor,bound;
        final UncheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size))
        {
          OmniArray.OfDouble.ascendingForEach(parent.arr,cursor,bound-1,action::accept);
          this.cursor=bound;
        }
      }
      @Override
      public double previousDouble()
      {
        final int lastRet;
        this.lastRet=lastRet=--this.cursor;
        return (double)parent.arr[lastRet];
      }
      @Override
      public void set(double val)
      {
        parent.arr[this.lastRet]=val;
      }
      @Override
      public void add(double val)
      {
        final UncheckedList root;
        final int rootSize;
        if((rootSize=(root=this.parent).size)!=0)
        {
          ((DoubleArrSeq)root).uncheckedInsert(this.cursor++,rootSize,val);
        }
        else
        {
          ((DoubleArrSeq)root).uncheckedInit(val);
          ++this.cursor;
        }
      }
    }
    @Override
    public OmniListIterator.OfDouble listIterator()
    {
      return new ListItr(this);
    }
    @Override
    public OmniListIterator.OfDouble listIterator(int index)
    {
      return new ListItr(this,index);
    }
    @Override
    public void add(int index,double val)
    {
      int size;
      if((size=this.size)!=0){
        ((DoubleArrSeq)this).uncheckedInsert(index,size,val);
      }else{
        ((DoubleArrSeq)this).uncheckedInit(val);
      }
    }
    @Override
    void uncheckedCopyInto(double[] dst,int length)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(Object[] dst,int length)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(Double[] dst,int length)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override
    public double getDouble(int index)
    {
      return (double)this.arr[index];
    }
    @Override
    public void put(int index,double val)
    {
      this.arr[index]=val;
    }
    @Override
    public double set(int index,double val)
    {
      final double[] arr;
      final var ret=(double)(arr=this.arr)[index];
      arr[index]=val;
      return ret;
    }
    @Override
    public OmniList.OfDouble subList(int fromIndex,int toIndex)
    {
      return new UncheckedSubList(this,fromIndex,toIndex-fromIndex);
    }
  }
  public
    static class UncheckedSubList
      implements DoubleListDefault,Cloneable
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
      final double[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(root.arr,rootOffset,copy=new double[size],0,size);
      }
      else
      {
        copy=OmniArray.OfDouble.DEFAULT_ARR;
      }
      return new UncheckedList(size,copy);
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
          final int rootOffset;
          final StringBuilder builder;
          OmniArray.OfDouble.ascendingToString(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,builder=new StringBuilder("["));
          return builder.append(']').toString();
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
        return OmniArray.OfDouble.ascendingSeqHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
        (root=this.root).size=OmniArray.OfDouble.removeRangeAndPullDown(root.arr,this.rootOffset,root.size,size);
        this.size=0;
      }
    }
    @Override
    public boolean contains(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val)
        {
          final int rootOffset;
          return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,TypeUtil.DBL_TRUE_BITS);
        }
        final int rootOffset;
        return OmniArray.OfDouble.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
          if(val!=0)
          {
            {
              final int rootOffset;
              return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Double.doubleToRawLongBits(val));
            }
          }
          else
          {
            final int rootOffset;
            return OmniArray.OfDouble.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
        if(val!=0)
        {
          if(TypeUtil.checkCastToDouble(val))
          {
            final int rootOffset;
            return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Double.doubleToRawLongBits(val));
          }
        }
        else
        {
          final int rootOffset;
          return OmniArray.OfDouble.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
        if(val==val)
        {
          final int rootOffset;
          return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Double.doubleToRawLongBits(val));
        }
        final int rootOffset;
        return OmniArray.OfDouble.uncheckedcontainsNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
      }
      return false;
    }
    @Override
    public boolean contains(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val==val)
        {
          final int rootOffset;
          return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Double.doubleToRawLongBits(val));
        }
        final int rootOffset;
        return OmniArray.OfDouble.uncheckedcontainsNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
      }
      return false;
    }
    @Override
    public boolean contains(Object val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Double)
        {
          final double v;
          if((v=(double)val)==v)
          {
            final int rootOffset;
            return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Double.doubleToRawLongBits(v));
          }
          final int rootOffset;
          return OmniArray.OfDouble.uncheckedcontainsNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
        }
      }
      return false;
    }
    @Override
    public boolean contains(byte val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            final int rootOffset;
            return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Double.doubleToRawLongBits(val));
          }
          final int rootOffset;
          return OmniArray.OfDouble.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
        }
      }
      return false;
    }
    @Override
    public boolean contains(char val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            final int rootOffset;
            return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Double.doubleToRawLongBits(val));
          }
          final int rootOffset;
          return OmniArray.OfDouble.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
        }
      }
      return false;
    }
    @Override
    public boolean contains(short val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            final int rootOffset;
            return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Double.doubleToRawLongBits(val));
          }
          final int rootOffset;
          return OmniArray.OfDouble.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
        if(val)
        {
          return this.uncheckedremoveValBits(size,TypeUtil.DBL_TRUE_BITS);
        }
        return this.uncheckedremoveVal0(size);
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
          if(val!=0)
          {
            {
              return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
            }
          }
          else
          {
            return this.uncheckedremoveVal0(size);
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
        if(val!=0)
        {
          if(TypeUtil.checkCastToDouble(val))
          {
            return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
          }
        }
        else
        {
          return this.uncheckedremoveVal0(size);
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
        if(val==val)
        {
          return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
        }
        return this.uncheckedremoveValNaN(size);
      }
      return false;
    }
    @Override
    public boolean removeVal(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val==val)
        {
          return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
        }
        return this.uncheckedremoveValNaN(size);
      }
      return false;
    }
    @Override
    public boolean remove(Object val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Double)
        {
          final double v;
          if((v=(double)val)==v)
          {
            return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(v));
          }
          return this.uncheckedremoveValNaN(size);
        }
      }
      return false;
    }
    @Override
    public boolean removeVal(byte val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
          }
          return this.uncheckedremoveVal0(size);
        }
      }
      return false;
    }
    @Override
    public boolean removeVal(char val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
          }
          return this.uncheckedremoveVal0(size);
        }
      }
      return false;
    }
    @Override
    public boolean removeVal(short val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
          }
          return this.uncheckedremoveVal0(size);
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
        if(val)
        {
          return OmniArray.OfDouble.uncheckedindexOfBits(root.arr,this.rootOffset,size,TypeUtil.DBL_TRUE_BITS);
        }
        return OmniArray.OfDouble.uncheckedindexOf0(root.arr,this.rootOffset,size);
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
          if(val!=0)
          {
            {
              return OmniArray.OfDouble.uncheckedindexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(val));
            }
          }
          else
          {
            return OmniArray.OfDouble.uncheckedindexOf0(root.arr,this.rootOffset,size);
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
        if(val!=0)
        {
          if(TypeUtil.checkCastToDouble(val))
          {
            return OmniArray.OfDouble.uncheckedindexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(val));
          }
        }
        else
        {
          return OmniArray.OfDouble.uncheckedindexOf0(root.arr,this.rootOffset,size);
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
        if(val==val)
        {
          return OmniArray.OfDouble.uncheckedindexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(val));
        }
        return OmniArray.OfDouble.uncheckedindexOfNaN(root.arr,this.rootOffset,size);
      }
      return -1;
    }
    @Override
    public int indexOf(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val==val)
        {
          return OmniArray.OfDouble.uncheckedindexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(val));
        }
        return OmniArray.OfDouble.uncheckedindexOfNaN(root.arr,this.rootOffset,size);
      }
      return -1;
    }
    @Override
    public int indexOf(Object val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Double)
        {
          final double v;
          if((v=(double)val)==v)
          {
            return OmniArray.OfDouble.uncheckedindexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(v));
          }
          return OmniArray.OfDouble.uncheckedindexOfNaN(root.arr,this.rootOffset,size);
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
        if(val)
        {
          return OmniArray.OfDouble.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,TypeUtil.DBL_TRUE_BITS);
        }
        return OmniArray.OfDouble.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
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
          if(val!=0)
          {
            {
              return OmniArray.OfDouble.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(val));
            }
          }
          else
          {
            return OmniArray.OfDouble.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
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
        if(val!=0)
        {
          if(TypeUtil.checkCastToDouble(val))
          {
            return OmniArray.OfDouble.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(val));
          }
        }
        else
        {
          return OmniArray.OfDouble.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
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
        if(val==val)
        {
          return OmniArray.OfDouble.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(val));
        }
        return OmniArray.OfDouble.uncheckedlastIndexOfNaN(root.arr,this.rootOffset,size);
      }
      return -1;
    }
    @Override
    public int lastIndexOf(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val==val)
        {
          return OmniArray.OfDouble.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(val));
        }
        return OmniArray.OfDouble.uncheckedlastIndexOfNaN(root.arr,this.rootOffset,size);
      }
      return -1;
    }
    @Override
    public int lastIndexOf(Object val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Double)
        {
          final double v;
          if((v=(double)val)==v)
          {
            return OmniArray.OfDouble.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(v));
          }
          return OmniArray.OfDouble.uncheckedlastIndexOfNaN(root.arr,this.rootOffset,size);
        }
      }
      return -1;
    }
    private boolean uncheckedremoveValBits(int size
    ,long bits
    )
    {
      final UncheckedList root;
      final var arr=(root=this.root).arr;
      for(int index=this.rootOffset,bound=index+(--size);;++index)
      {
        if(
        bits==Double.doubleToRawLongBits(arr[index])
        )
        {
          for(var curr=parent;curr!=null;--curr.size,curr=curr.parent){}
          OmniArray.OfDouble.removeIndexAndPullDown(arr,index,--root.size);
          this.size=size;
          return true;
        }
        if(index==bound)
        {
          return false;
        }
      }
    }
    private boolean uncheckedremoveValNaN(int size
    )
    {
      final UncheckedList root;
      final var arr=(root=this.root).arr;
      for(int index=this.rootOffset,bound=index+(--size);;++index)
      {
        if(
        Double.isNaN(arr[index])
        )
        {
          for(var curr=parent;curr!=null;--curr.size,curr=curr.parent){}
          OmniArray.OfDouble.removeIndexAndPullDown(arr,index,--root.size);
          this.size=size;
          return true;
        }
        if(index==bound)
        {
          return false;
        }
      }
    }
    private boolean uncheckedremoveVal0(int size
    )
    {
      final UncheckedList root;
      final var arr=(root=this.root).arr;
      for(int index=this.rootOffset,bound=index+(--size);;++index)
      {
        if(
        arr[index]==0
        )
        {
          for(var curr=parent;curr!=null;--curr.size,curr=curr.parent){}
          OmniArray.OfDouble.removeIndexAndPullDown(arr,index,--root.size);
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
    public void forEach(DoubleConsumer action)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          OmniArray.OfDouble.ascendingForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,action);
        }
      }
    }
    @Override
    public void forEach(Consumer<? super Double> action)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          OmniArray.OfDouble.ascendingForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,action::accept);
        }
      }
    }
    private static class Itr
      extends AbstractDoubleItr
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
      public double nextDouble()
      {
        return (double)parent.root.arr[cursor++];
      }
      @Override
      public void remove()
      {
        UncheckedSubList parent;
        final UncheckedList root;
        OmniArray.OfDouble.removeIndexAndPullDown((root=(parent=this.parent).root).arr,--this.cursor,--root.size);
        do{
          --parent.size;
        }while((parent=parent.parent)!=null);
      }
      @Override
      public void forEachRemaining(DoubleConsumer action)
      {
        final int cursor,bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
        {
          OmniArray.OfDouble.ascendingForEach(parent.root.arr,cursor,bound-1,action);
          this.cursor=bound;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Double> action)
      {
        final int cursor,bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
        {
          OmniArray.OfDouble.ascendingForEach(parent.root.arr,cursor,bound-1,action::accept);
          this.cursor=bound;
        }
      }
    }
    @Override
    public OmniIterator.OfDouble iterator()
    {
      return new Itr(this);
    }
    private static class ListItr extends Itr implements OmniListIterator.OfDouble
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
      public double nextDouble()
      {
        int lastRet;
        this.lastRet=lastRet=this.cursor++;
        return (double)parent.root.arr[lastRet];
      }
      @Override
      public void remove()
      {
        UncheckedSubList parent;
        final UncheckedList root;
        final int lastRet;
        OmniArray.OfDouble.removeIndexAndPullDown((root=(parent=this.parent).root).arr,lastRet=this.lastRet,--root.size);
        this.cursor=lastRet;
        do{
          --parent.size;
        }while((parent=parent.parent)!=null);
      }
      @Override
      public void forEachRemaining(DoubleConsumer action)
      {
        int cursor;
        final int bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
        {
          OmniArray.OfDouble.ascendingForEach(parent.root.arr,cursor,cursor=bound-1,action);
          this.lastRet=cursor;
          this.cursor=bound;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Double> action)
      {
        final int cursor,bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
        {
          OmniArray.OfDouble.ascendingForEach(parent.root.arr,cursor,bound-1,action::accept);
          this.cursor=bound;
        }
      }
      @Override
      public double previousDouble()
      {
        final int lastRet;
        this.lastRet=lastRet=--this.cursor;
        return (double)parent.root.arr[lastRet];
      }
      @Override
      public void set(double val)
      {
        parent.root.arr[this.lastRet]=val;
      }
      @Override
      public void add(double val)
      {
        final UncheckedList root;
        final int rootSize;
        UncheckedSubList parent;
        if((rootSize=(root=(parent=this.parent).root).size)!=0)
        {
          ((DoubleArrSeq)root).uncheckedInsert(this.cursor++,rootSize,val);
        }
        else
        {
          ((DoubleArrSeq)root).uncheckedInit(val);
          ++this.cursor;
        }
        do{
          ++parent.size;
        }while((parent=parent.parent)!=null);
      }
    }
    @Override
    public OmniListIterator.OfDouble listIterator()
    {
      return new ListItr(this);
    }
    @Override
    public OmniListIterator.OfDouble listIterator(int index)
    {
      return new ListItr(this,index+this.rootOffset);
    }
    @Override
    public boolean add(Double val){
      return add((double)val);
    }
    @Override
    public boolean add(boolean val){
      return add((double)TypeUtil.castToDouble(val));
    }
    @Override
    public boolean add(int val)
    {
      return add((double)val);
    }
    @Override
    public boolean add(char val)
    {
      return add((double)val);
    }
    @Override
    public boolean add(short val)
    {
      return add((double)val);
    }
    @Override
    public boolean add(long val)
    {
      return add((double)val);
    }
    @Override
    public boolean add(float val)
    {
      return add((double)val);
    }
    @Override
    public boolean add(double val)
    {
      for(var curr=parent;curr!=null;++curr.size,curr=curr.parent){}
      final UncheckedList root;
      final int rootSize;
      if((rootSize=(root=this.root).size)!=0){
        ((DoubleArrSeq)root).uncheckedInsert(this.rootOffset+(this.size++),rootSize,val);
      }else{
        ((DoubleArrSeq)root).uncheckedInit(val);
        ++this.size;
      }
      return true;
    }
    @Override
    public void add(int index,double val)
    {
      for(var curr=parent;curr!=null;++curr.size,curr=curr.parent){}
      ++this.size;
      final UncheckedList root;
      final int rootSize;
      if((rootSize=(root=this.root).size)!=0){
        ((DoubleArrSeq)root).uncheckedInsert(this.rootOffset+index,rootSize,val);
      }else{
        ((DoubleArrSeq)root).uncheckedInit(val);
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
    public Double[] toArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final Double[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new Double[size],0,size);
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_BOXED_ARR;
    }
    @Override
    public double getDouble(int index)
    {
      return (double)root.arr[index+this.rootOffset];
    }
    @Override
    public void put(int index,double val)
    {
      root.arr[index+this.rootOffset]=val;
    }
    @Override
    public double set(int index,double val)
    {
      final double[] arr;
      final var ret=(double)(arr=root.arr)[index+=this.rootOffset];
      arr[index]=val;
      return ret;
    }
    @Override
    public OmniList.OfDouble subList(int fromIndex,int toIndex)
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
    private CheckedStack(int size,double[] arr)
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
      final double[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new double[size],0,size);
      }
      else
      {
        copy=OmniArray.OfDouble.DEFAULT_ARR;
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
    boolean uncheckedremoveValBits(int size
    ,long bits
    )
    {
      final var arr=this.arr;
      for(int index=--size;;--index)
      {
        if(
        bits==Double.doubleToRawLongBits(arr[index])
        )
        {
          ++this.modCount;
          OmniArray.OfDouble.removeIndexAndPullDown(arr,index,size);
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
    boolean uncheckedremoveValNaN(int size
    )
    {
      final var arr=this.arr;
      for(int index=--size;;--index)
      {
        if(
        Double.isNaN(arr[index])
        )
        {
          ++this.modCount;
          OmniArray.OfDouble.removeIndexAndPullDown(arr,index,size);
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
    boolean uncheckedremoveVal0(int size
    )
    {
      final var arr=this.arr;
      for(int index=--size;;--index)
      {
        if(
        arr[index]==0
        )
        {
          ++this.modCount;
          OmniArray.OfDouble.removeIndexAndPullDown(arr,index,size);
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
    void uncheckedForEach(int size,DoubleConsumer action)
    {
      int modCount=this.modCount;
      try
      {
        OmniArray.OfDouble.descendingForEach(this.arr,0,size-1,action);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    private static class Itr
      extends AbstractDoubleItr
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
      public double nextDouble()
      {
        final CheckedStack root;
        CheckedCollection.checkModCount(modCount,(root=this.parent).modCount);
        final int cursor;
        if((cursor=this.cursor)>=0)
        {
          this.lastRet=cursor;
          this.cursor=cursor-1;
            return (double)root.arr[cursor];
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
          OmniArray.OfDouble.removeIndexAndPullDown(root.arr,lastRet,--root.size);
          this.cursor=lastRet;
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override
      public void forEachRemaining(DoubleConsumer action)
      {
        final int cursor;
        if((cursor=this.cursor)>=0)
        {
          final int modCount=this.modCount;
          final var parent=this.parent;
          try
          {
            OmniArray.OfDouble.descendingForEach(parent.arr,0,cursor,action);
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
      public void forEachRemaining(Consumer<? super Double> action)
      {
        final int cursor;
        if((cursor=this.cursor)>=0)
        {
          final int modCount=this.modCount;
          final var parent=this.parent;
          try
          {
            OmniArray.OfDouble.descendingForEach(parent.arr,0,cursor,action::accept);
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
    public OmniIterator.OfDouble iterator()
    {
      return new Itr(this);
    }
    @Override
    public void push(double val)
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
    public double popDouble()
    {
      int size;
      if((size=this.size)!=0)
      {
        ++this.modCount;
        final var ret=(double)arr[--size];
        this.size=size;
        return ret;
      }
      throw new NoSuchElementException();
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
    private CheckedList(int size,double[] arr)
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
      final double[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new double[size],0,size);
      }
      else
      {
        copy=OmniArray.OfDouble.DEFAULT_ARR;
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
    boolean uncheckedremoveValBits(int size
    ,long bits
    )
    {
      final var arr=this.arr;
      for(int index=0;;)
      {
        if(
        bits==Double.doubleToRawLongBits(arr[index])
        )
        {
          OmniArray.OfDouble.removeIndexAndPullDown(arr,index,--size);
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
    boolean uncheckedremoveValNaN(int size
    )
    {
      final var arr=this.arr;
      for(int index=0;;)
      {
        if(
        Double.isNaN(arr[index])
        )
        {
          OmniArray.OfDouble.removeIndexAndPullDown(arr,index,--size);
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
    boolean uncheckedremoveVal0(int size
    )
    {
      final var arr=this.arr;
      for(int index=0;;)
      {
        if(
        arr[index]==0
        )
        {
          OmniArray.OfDouble.removeIndexAndPullDown(arr,index,--size);
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
    void uncheckedForEach(int size,DoubleConsumer action)
    {
      int modCount=this.modCount;
      try
      {
        OmniArray.OfDouble.ascendingForEach(this.arr,0,size-1,action);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    private static class Itr
      extends AbstractDoubleItr
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
      public double nextDouble()
      {
        final CheckedList root;
        CheckedCollection.checkModCount(modCount,(root=this.parent).modCount);
        final int cursor;
        if((cursor=this.cursor)<root.size)
        {
          this.lastRet=cursor;
          this.cursor=cursor+1;
            return (double)root.arr[cursor];
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
          OmniArray.OfDouble.removeIndexAndPullDown(root.arr,lastRet,--root.size);
          this.cursor=lastRet;
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override
      public void forEachRemaining(DoubleConsumer action)
      {
        int cursor;
        final int bound;
        final CheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size))
        {
          final int modCount=this.modCount;
          try
          {
            OmniArray.OfDouble.ascendingForEach(parent.arr,cursor,cursor=bound-1,action);
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
      public void forEachRemaining(Consumer<? super Double> action)
      {
        int cursor;
        final int bound;
        final CheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size))
        {
          final int modCount=this.modCount;
          try
          {
            OmniArray.OfDouble.ascendingForEach(parent.arr,cursor,cursor=bound-1,action::accept);
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
    public OmniIterator.OfDouble iterator()
    {
      return new Itr(this);
    }
    private static class ListItr extends Itr implements OmniListIterator.OfDouble
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
      public double previousDouble()
      {
        final CheckedList root;
        int cursor;
        CheckedCollection.checkModCount(modCount,(root=this.parent).modCount);
        if((cursor=this.cursor)>0)
        {
          this.lastRet=--cursor;
          this.cursor=cursor;
          return (double)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override
      public void set(double val)
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
      public void add(double val)
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
          ((DoubleArrSeq)root).uncheckedInsert(this.cursor++,rootSize,val);
        }
        else
        {
          ((DoubleArrSeq)root).uncheckedInit(val);
          ++this.cursor;
        }
      }
    }
    @Override
    public OmniListIterator.OfDouble listIterator()
    {
      return new ListItr(this);
    }
    @Override
    public OmniListIterator.OfDouble listIterator(int index)
    {
      if(index<0 || index>this.size)
      {
        throw new IndexOutOfBoundsException("index="+index+"; size="+this.size);
      }
      return new ListItr(this,index);
    }
    @Override
    public void push(double val)
    {
      ++this.modCount;
      super.push(val);
    }
    @Override
    public void add(int index,double val)
    {
      int size;
      if((size=this.size)<index || index<0){
        throw new IndexOutOfBoundsException("index="+index+"; size="+size);
      }
      ++this.modCount;
      if(size!=0){
        ((DoubleArrSeq)this).uncheckedInsert(index,size,val);
      }else{
        ((DoubleArrSeq)this).uncheckedInit(val);
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
    public double getDouble(int index)
    {
      if(index<0 || index>=this.size)
      {
        throw new IndexOutOfBoundsException("index="+index+"; size="+this.size);
      }
      return (double)this.arr[index];
    }
    @Override
    public void put(int index,double val)
    {
      if(index<0 || index>=this.size)
      {
        throw new IndexOutOfBoundsException("index="+index+"; size="+this.size);
      }
      this.arr[index]=val;
    }
    @Override
    public double set(int index,double val)
    {
      if(index<0 || index>=this.size)
      {
        throw new IndexOutOfBoundsException("index="+index+"; size="+this.size);
      }
      final double[] arr;
      final var ret=(double)(arr=this.arr)[index];
      arr[index]=val;
      return ret;
    }
    @Override
    public OmniList.OfDouble subList(int fromIndex,int toIndex)
    {
      final int subListSize;
      if((subListSize=toIndex-fromIndex)<0 || fromIndex<0 || this.size<toIndex)
      {
        throw new IndexOutOfBoundsException("fromIndex="+fromIndex+"; toIndex="+toIndex+"; size="+this.size);
      }
      return new CheckedSubList(this,fromIndex,subListSize);
    }
  }
  private
    static class CheckedSubList
      implements DoubleListDefault,Cloneable
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
      final double[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(root.arr,rootOffset,copy=new double[size],0,size);
      }
      else
      {
        copy=OmniArray.OfDouble.DEFAULT_ARR;
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
        if(size>(Integer.MAX_VALUE/5))
        {
          throw new OutOfMemoryError();
        }
          final int rootOffset;
          final StringBuilder builder;
          OmniArray.OfDouble.ascendingToString(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,builder=new StringBuilder("["));
          return builder.append(']').toString();
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
        return OmniArray.OfDouble.ascendingSeqHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
        root.size=OmniArray.OfDouble.removeRangeAndPullDown(root.arr,this.rootOffset,root.size,size);
        this.size=0;
      }
    }
    @Override
    public boolean contains(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,TypeUtil.DBL_TRUE_BITS);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          final int rootOffset;
          return OmniArray.OfDouble.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
          if(val!=0)
          {
            {
              final int modCount=this.modCount;
              final var root=this.root;
              try{
                final int rootOffset;
                return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Double.doubleToRawLongBits(val));
              }finally{
                CheckedCollection.checkModCount(modCount,root.modCount);
              }
            }
          }
          else
          {
            final int modCount=this.modCount;
            final var root=this.root;
            try{
              final int rootOffset;
              return OmniArray.OfDouble.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
        if(val!=0)
        {
          if(TypeUtil.checkCastToDouble(val))
          {
            final int modCount=this.modCount;
            final var root=this.root;
            try{
              final int rootOffset;
              return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Double.doubleToRawLongBits(val));
            }finally{
              CheckedCollection.checkModCount(modCount,root.modCount);
            }
          }
        }
        else
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfDouble.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
        if(val==val)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Double.doubleToRawLongBits(val));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          final int rootOffset;
          return OmniArray.OfDouble.uncheckedcontainsNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
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
        if(val==val)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Double.doubleToRawLongBits(val));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          final int rootOffset;
          return OmniArray.OfDouble.uncheckedcontainsNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
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
        if(val instanceof Double)
        {
          final double v;
          if((v=(double)val)==v)
          {
            final int modCount=this.modCount;
            final var root=this.root;
            try{
              final int rootOffset;
              return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Double.doubleToRawLongBits(v));
            }finally{
              CheckedCollection.checkModCount(modCount,root.modCount);
            }
          }
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfDouble.uncheckedcontainsNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean contains(byte val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            final int modCount=this.modCount;
            final var root=this.root;
            try{
              final int rootOffset;
              return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Double.doubleToRawLongBits(val));
            }finally{
              CheckedCollection.checkModCount(modCount,root.modCount);
            }
          }
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfDouble.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean contains(char val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            final int modCount=this.modCount;
            final var root=this.root;
            try{
              final int rootOffset;
              return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Double.doubleToRawLongBits(val));
            }finally{
              CheckedCollection.checkModCount(modCount,root.modCount);
            }
          }
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfDouble.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean contains(short val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            final int modCount=this.modCount;
            final var root=this.root;
            try{
              final int rootOffset;
              return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Double.doubleToRawLongBits(val));
            }finally{
              CheckedCollection.checkModCount(modCount,root.modCount);
            }
          }
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfDouble.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
        if(val)
        {
          return this.uncheckedremoveValBits(size,TypeUtil.DBL_TRUE_BITS);
        }
        return this.uncheckedremoveVal0(size);
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
          if(val!=0)
          {
            {
              return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
            }
          }
          else
          {
            return this.uncheckedremoveVal0(size);
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
        if(val!=0)
        {
          if(TypeUtil.checkCastToDouble(val))
          {
            return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
          }
        }
        else
        {
          return this.uncheckedremoveVal0(size);
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
        if(val==val)
        {
          return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
        }
        return this.uncheckedremoveValNaN(size);
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
        if(val==val)
        {
          return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
        }
        return this.uncheckedremoveValNaN(size);
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
        if(val instanceof Double)
        {
          final double v;
          if((v=(double)val)==v)
          {
            return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(v));
          }
          return this.uncheckedremoveValNaN(size);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean removeVal(byte val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
          }
          return this.uncheckedremoveVal0(size);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean removeVal(char val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
          }
          return this.uncheckedremoveVal0(size);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean removeVal(short val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
          }
          return this.uncheckedremoveVal0(size);
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
        if(val)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfDouble.uncheckedindexOfBits(root.arr,this.rootOffset,size,TypeUtil.DBL_TRUE_BITS);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          return OmniArray.OfDouble.uncheckedindexOf0(root.arr,this.rootOffset,size);
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
          if(val!=0)
          {
            {
              final int modCount=this.modCount;
              final var root=this.root;
              try{
                return OmniArray.OfDouble.uncheckedindexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(val));
              }finally{
                CheckedCollection.checkModCount(modCount,root.modCount);
              }
            }
          }
          else
          {
            final int modCount=this.modCount;
            final var root=this.root;
            try{
              return OmniArray.OfDouble.uncheckedindexOf0(root.arr,this.rootOffset,size);
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
        if(val!=0)
        {
          if(TypeUtil.checkCastToDouble(val))
          {
            final int modCount=this.modCount;
            final var root=this.root;
            try{
              return OmniArray.OfDouble.uncheckedindexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(val));
            }finally{
              CheckedCollection.checkModCount(modCount,root.modCount);
            }
          }
        }
        else
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfDouble.uncheckedindexOf0(root.arr,this.rootOffset,size);
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
        if(val==val)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfDouble.uncheckedindexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(val));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          return OmniArray.OfDouble.uncheckedindexOfNaN(root.arr,this.rootOffset,size);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
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
        if(val==val)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfDouble.uncheckedindexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(val));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          return OmniArray.OfDouble.uncheckedindexOfNaN(root.arr,this.rootOffset,size);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
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
        if(val instanceof Double)
        {
          final double v;
          if((v=(double)val)==v)
          {
            final int modCount=this.modCount;
            final var root=this.root;
            try{
              return OmniArray.OfDouble.uncheckedindexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(v));
            }finally{
              CheckedCollection.checkModCount(modCount,root.modCount);
            }
          }
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfDouble.uncheckedindexOfNaN(root.arr,this.rootOffset,size);
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
        if(val)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfDouble.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,TypeUtil.DBL_TRUE_BITS);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          return OmniArray.OfDouble.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
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
          if(val!=0)
          {
            {
              final int modCount=this.modCount;
              final var root=this.root;
              try{
                return OmniArray.OfDouble.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(val));
              }finally{
                CheckedCollection.checkModCount(modCount,root.modCount);
              }
            }
          }
          else
          {
            final int modCount=this.modCount;
            final var root=this.root;
            try{
              return OmniArray.OfDouble.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
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
        if(val!=0)
        {
          if(TypeUtil.checkCastToDouble(val))
          {
            final int modCount=this.modCount;
            final var root=this.root;
            try{
              return OmniArray.OfDouble.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(val));
            }finally{
              CheckedCollection.checkModCount(modCount,root.modCount);
            }
          }
        }
        else
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfDouble.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
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
        if(val==val)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfDouble.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(val));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          return OmniArray.OfDouble.uncheckedlastIndexOfNaN(root.arr,this.rootOffset,size);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
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
        if(val==val)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfDouble.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(val));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          return OmniArray.OfDouble.uncheckedlastIndexOfNaN(root.arr,this.rootOffset,size);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
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
        if(val instanceof Double)
        {
          final double v;
          if((v=(double)val)==v)
          {
            final int modCount=this.modCount;
            final var root=this.root;
            try{
              return OmniArray.OfDouble.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(v));
            }finally{
              CheckedCollection.checkModCount(modCount,root.modCount);
            }
          }
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfDouble.uncheckedlastIndexOfNaN(root.arr,this.rootOffset,size);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    private boolean uncheckedremoveValBits(int size
    ,long bits
    )
    {
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      final var arr=root.arr;
      for(int index=this.rootOffset,bound=index+(--size);;++index)
      {
        if(
        bits==Double.doubleToRawLongBits(arr[index])
        )
        {
          root.modCount=++modCount;
          this.modCount=modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,--curr.size,curr=curr.parent){}
          OmniArray.OfDouble.removeIndexAndPullDown(arr,index,--root.size);
          this.size=size;
          return true;
        }
        if(index==bound)
        {
          return false;
        }
      }
    }
    private boolean uncheckedremoveValNaN(int size
    )
    {
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      final var arr=root.arr;
      for(int index=this.rootOffset,bound=index+(--size);;++index)
      {
        if(
        Double.isNaN(arr[index])
        )
        {
          root.modCount=++modCount;
          this.modCount=modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,--curr.size,curr=curr.parent){}
          OmniArray.OfDouble.removeIndexAndPullDown(arr,index,--root.size);
          this.size=size;
          return true;
        }
        if(index==bound)
        {
          return false;
        }
      }
    }
    private boolean uncheckedremoveVal0(int size
    )
    {
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      final var arr=root.arr;
      for(int index=this.rootOffset,bound=index+(--size);;++index)
      {
        if(
        arr[index]==0
        )
        {
          root.modCount=++modCount;
          this.modCount=modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,--curr.size,curr=curr.parent){}
          OmniArray.OfDouble.removeIndexAndPullDown(arr,index,--root.size);
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
    public void forEach(DoubleConsumer action)
    {
      int modCount=this.modCount;
      final var root=this.root;
      try
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          OmniArray.OfDouble.ascendingForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,action);
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
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          OmniArray.OfDouble.ascendingForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,action::accept);
        }
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    private static class Itr
      extends AbstractDoubleItr
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
      public double nextDouble()
      {
        final CheckedList root;
        final CheckedSubList parent;
        CheckedCollection.checkModCount(modCount,(root=(parent=this.parent).root).modCount);
        final int cursor;
        if((cursor=this.cursor)<(parent.rootOffset+parent.size))
        {
          this.lastRet=cursor;
          this.cursor=cursor+1;
            return (double)root.arr[cursor];
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
          OmniArray.OfDouble.removeIndexAndPullDown(root.arr,lastRet,--root.size);
          this.cursor=lastRet;
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override
      public void forEachRemaining(DoubleConsumer action)
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
            OmniArray.OfDouble.ascendingForEach(root.arr,cursor,cursor=bound-1,action);
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
      public void forEachRemaining(Consumer<? super Double> action)
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
            OmniArray.OfDouble.ascendingForEach(root.arr,cursor,cursor=bound-1,action::accept);
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
    public OmniIterator.OfDouble iterator()
    {
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new Itr(this);
    }
    private static class ListItr extends Itr implements OmniListIterator.OfDouble
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
      public double previousDouble()
      {
        final CheckedList root;
        int cursor;
        final CheckedSubList parent;
        CheckedCollection.checkModCount(modCount,(root=(parent=this.parent).root).modCount);
        if((cursor=this.cursor)>parent.rootOffset)
        {
          this.lastRet=--cursor;
          this.cursor=cursor;
          return (double)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override
      public void set(double val)
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
      public void add(double val)
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
          ((DoubleArrSeq)root).uncheckedInsert(this.cursor++,rootSize,val);
        }
        else
        {
          ((DoubleArrSeq)root).uncheckedInit(val);
          ++this.cursor;
        }
      }
    }
    @Override
    public OmniListIterator.OfDouble listIterator()
    {
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new ListItr(this);
    }
    @Override
    public OmniListIterator.OfDouble listIterator(int index)
    {
      CheckedCollection.checkModCount(modCount,root.modCount);
      if(index<0 || index>this.size)
      {
        throw new IndexOutOfBoundsException("index="+index+"; size="+this.size);
      }
      return new ListItr(this,index+this.rootOffset);
    }
    @Override
    public boolean add(Double val){
      return add((double)val);
    }
    @Override
    public boolean add(boolean val){
      return add((double)TypeUtil.castToDouble(val));
    }
    @Override
    public boolean add(int val)
    {
      return add((double)val);
    }
    @Override
    public boolean add(char val)
    {
      return add((double)val);
    }
    @Override
    public boolean add(short val)
    {
      return add((double)val);
    }
    @Override
    public boolean add(long val)
    {
      return add((double)val);
    }
    @Override
    public boolean add(float val)
    {
      return add((double)val);
    }
    @Override
    public boolean add(double val)
    {
      final CheckedList root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,++curr.size,curr=curr.parent){}
      if((modCount=root.size)!=0){
        ((DoubleArrSeq)root).uncheckedInsert(this.rootOffset+(this.size++),modCount,val);
      }else{
        ((DoubleArrSeq)root).uncheckedInit(val);
        ++this.size;
      }
      return true;
    }
    @Override
    public void add(int index,double val)
    {
      final CheckedList root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      int size;
      if((size=this.size)<index||index<0){
        throw new IndexOutOfBoundsException("index="+index+"; size="+size);
      }
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,++curr.size,curr=curr.parent){}
      this.size=size+1;
      if((modCount=root.size)!=0){
        ((DoubleArrSeq)root).uncheckedInsert(this.rootOffset+index,modCount,val);
      }else{
        ((DoubleArrSeq)root).uncheckedInit(val);
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
    public Double[] toArray()
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0)
      {
        final Double[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new Double[size],0,size);
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_BOXED_ARR;
    }
    @Override
    public double getDouble(int index)
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      if(index<0 || index>=this.size)
      {
        throw new IndexOutOfBoundsException("index="+index+"; size="+this.size);
      }
      return (double)root.arr[index+this.rootOffset];
    }
    @Override
    public void put(int index,double val)
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      if(index<0 || index>=this.size)
      {
        throw new IndexOutOfBoundsException("index="+index+"; size="+this.size);
      }
      root.arr[index+this.rootOffset]=val;
    }
    @Override
    public double set(int index,double val)
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      if(index<0 || index>=this.size)
      {
        throw new IndexOutOfBoundsException("index="+index+"; size="+this.size);
      }
      final double[] arr;
      final var ret=(double)(arr=root.arr)[index+=this.rootOffset];
      arr[index]=val;
      return ret;
    }
    @Override
    public OmniList.OfDouble subList(int fromIndex,int toIndex)
    {
      CheckedCollection.checkModCount(modCount,root.modCount);
      final int subListSize;
      if((subListSize=toIndex-fromIndex)<0 || fromIndex<0 || this.size<toIndex)
      {
        throw new IndexOutOfBoundsException("fromIndex="+fromIndex+"; toIndex="+toIndex+"; size="+this.size);
      }
      return new CheckedSubList(this,this.rootOffset+fromIndex,subListSize);
    }
  }
}
