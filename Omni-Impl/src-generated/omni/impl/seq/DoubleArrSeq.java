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
import omni.util.DoubleSortUtil;
import omni.util.HashUtil;
import omni.impl.seq.AbstractDoubleList;
import omni.impl.AbstractDoubleItr;
import omni.util.BitSetUtil;
import omni.util.TypeUtil;
import omni.function.DoubleComparator;
import java.util.function.DoubleUnaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
public abstract class DoubleArrSeq extends AbstractDoubleList implements OmniCollection.OfDouble
{
  private static void eraseIndexHelper(double[] arr,int index,int newSize)
  {
    ArrCopy.semicheckedSelfCopy(arr,index+1,index,newSize-index);
  }
  static  void uncheckedReplaceAll(double[] arr,int offset,int bound,DoubleUnaryOperator operator)
  {
    do
    {
      arr[offset]=operator.applyAsDouble((double)arr[offset]);
    }
    while(++offset!=bound);
  }
  static  void uncheckedAscendingForEach(double[] arr,int begin,int end,DoubleConsumer action)
  {
    for(;;++begin)
    {
      action.accept((double)arr[begin]);
      if(begin==end)
      {
        return;
      }
    }
  }
  static  void uncheckedDescendingForEach(double[] arr,int begin,int end,DoubleConsumer action)
  {
    for(;;--end)
    {
      action.accept((double)arr[end]);
      if(begin==end)
      {
        return;
      }
    }
  }
  static int ascendingHashCode(double[] arr,int begin,int end)
  {
    int hash=31+HashUtil.hashDouble(arr[begin]);
    while(begin!=end)
    {
      hash=hash*31+HashUtil.hashDouble(arr[++begin]);
    }
    return hash;
  }
  static int descendingHashCode(double[] arr,int begin,int end)
  {
    int hash=31+HashUtil.hashDouble(arr[end]);
    while(begin!=end)
    {
      hash=hash*31+HashUtil.hashDouble(arr[--end]);
    }
    return hash;
  }
  static void ascendingToString(double[] arr,int begin,int end,StringBuilder builder)
  {
    for(builder.append(arr[begin]);begin!=end;builder.append(',').append(' ').append(arr[++begin])){}
  }
  static void descendingToString(double[] arr,int begin,int end,StringBuilder builder)
  {
    for(builder.append(arr[end]);begin!=end;builder.append(',').append(' ').append(arr[--end])){}
  }
    static boolean uncheckedcontainsBits(double[] arr,int begin,int end
    ,long val
    )
    {
      while(
      val!=(arr[begin])
      )
      {
        if(begin==end)
        {
          return false;
        }
        ++begin;
      }
      return true;
    }
    static int uncheckedsearchBits(double[] arr,int end
    ,long val
    )
    {
      int index=end;
      while(
      val!=(arr[index])
      )
      {
        if(index==0)
        {
          return -1;
        }
        --index;
      }
      return index-end+1;
    }
    static int uncheckedindexOfBits(double[] arr,int bound
    ,long val
    )
    {
      int index=0;
      while(
      val!=(arr[index])
      )
      {
        if(++index==bound)
        {
          return -1;
        }
      }
      return index;
    }
    static int uncheckedlastIndexOfBits(double[] arr,int bound
    ,long val
    )
    {
      while(
      val!=(arr[--bound])
      )
      {
        if(bound==0)
        {
          return -1;
        }
      }
      return bound;
    }
    static int uncheckedindexOfBits(double[] arr,int offset,int bound
    ,long val
    )
    {
      int index=offset;
      while(
      val!=(arr[index])
      )
      {
        if(++index==bound)
        {
          return -1;
        }
      }
      return index-offset;
    }
    static int uncheckedlastIndexOfBits(double[] arr,int offset,int bound
    ,long val
    )
    {
      while(
      val!=(arr[--bound])
      )
      {
        if(bound==offset)
        {
          return -1;
        }
      }
      return bound-offset;
    }
    static boolean uncheckedcontainsNaN(double[] arr,int begin,int end
    )
    {
      while(
      !Double.isNaN(arr[begin])
      )
      {
        if(begin==end)
        {
          return false;
        }
        ++begin;
      }
      return true;
    }
    static int uncheckedsearchNaN(double[] arr,int end
    )
    {
      int index=end;
      while(
      !Double.isNaN(arr[index])
      )
      {
        if(index==0)
        {
          return -1;
        }
        --index;
      }
      return index-end+1;
    }
    static int uncheckedindexOfNaN(double[] arr,int bound
    )
    {
      int index=0;
      while(
      !Double.isNaN(arr[index])
      )
      {
        if(++index==bound)
        {
          return -1;
        }
      }
      return index;
    }
    static int uncheckedlastIndexOfNaN(double[] arr,int bound
    )
    {
      while(
      !Double.isNaN(arr[--bound])
      )
      {
        if(bound==0)
        {
          return -1;
        }
      }
      return bound;
    }
    static int uncheckedindexOfNaN(double[] arr,int offset,int bound
    )
    {
      int index=offset;
      while(
      !Double.isNaN(arr[index])
      )
      {
        if(++index==bound)
        {
          return -1;
        }
      }
      return index-offset;
    }
    static int uncheckedlastIndexOfNaN(double[] arr,int offset,int bound
    )
    {
      while(
      !Double.isNaN(arr[--bound])
      )
      {
        if(bound==offset)
        {
          return -1;
        }
      }
      return bound-offset;
    }
    static boolean uncheckedcontains0(double[] arr,int begin,int end
    )
    {
      while(
      (arr[begin])!=0
      )
      {
        if(begin==end)
        {
          return false;
        }
        ++begin;
      }
      return true;
    }
    static int uncheckedsearch0(double[] arr,int end
    )
    {
      int index=end;
      while(
      (arr[index])!=0
      )
      {
        if(index==0)
        {
          return -1;
        }
        --index;
      }
      return index-end+1;
    }
    static int uncheckedindexOf0(double[] arr,int bound
    )
    {
      int index=0;
      while(
      (arr[index])!=0
      )
      {
        if(++index==bound)
        {
          return -1;
        }
      }
      return index;
    }
    static int uncheckedlastIndexOf0(double[] arr,int bound
    )
    {
      while(
      (arr[--bound])!=0
      )
      {
        if(bound==0)
        {
          return -1;
        }
      }
      return bound;
    }
    static int uncheckedindexOf0(double[] arr,int offset,int bound
    )
    {
      int index=offset;
      while(
      (arr[index])!=0
      )
      {
        if(++index==bound)
        {
          return -1;
        }
      }
      return index-offset;
    }
    static int uncheckedlastIndexOf0(double[] arr,int offset,int bound
    )
    {
      while(
      (arr[--bound])!=0
      )
      {
        if(bound==offset)
        {
          return -1;
        }
      }
      return bound-offset;
    }
  //TODO static query helper methods
  //TODO mark/pull survivors up/down
  transient double[] arr;
  private DoubleArrSeq()
  {
    super();
    this.arr=OmniArray.OfDouble.DEFAULT_ARR;
  }
  private DoubleArrSeq(final int capacity)
  {
    super();
    switch(capacity)
    {
    default:
      this.arr=new double[capacity];
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfDouble.DEFAULT_ARR;
    case 0:
    }
  }
  private DoubleArrSeq(final int size,final double[] arr)
  {
    super(size);
    this.arr=arr;
  }
  abstract int uncheckedHashCode(int size);
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
  abstract void uncheckedToString(int size,StringBuilder builder);
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
      final StringBuilder builder;
      uncheckedToString(size,builder=new StringBuilder("["));
      return builder.append(']').toString();
    }
    return "[]";
  }
  abstract void uncheckedForEach(final int size,final DoubleConsumer action);
  //TODO forEach methods
  //TODO removeIf methods
  //TODO peek methods
  //TODO uncheckedCopyInto methods
  //TODO contains methods
  //TODO indexOf methods
  //TODO lastIndexOf methods
  //TODO search methods
   public Double pop()
   {
     return popDouble();
   }
   public void push(final Double val)
   {
     push((double)val);
   }
  public double popDouble()
  {
    return uncheckedPop(size-1);
  }
  public void push(final double val)
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
      if(val instanceof Double)
      {
        return this.uncheckedRemoveVal(size,(double)(val));
      }
    }
    return false;
  }
  @Override
  public double set(final int index,final double val)
  {
    final double[] arr;
    final var oldVal=(double)(arr=this.arr)[index];
    arr[index]=val;
    return oldVal;
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
  abstract boolean uncheckedRemoveVal0(final int size);
  abstract boolean uncheckedRemoveValBits(final int size,final long bits);
  abstract boolean uncheckedRemoveValNaN(final int size);
  abstract boolean uncheckedRemoveIf(final int size,final DoublePredicate filter);
  private int finalizeSubListBatchRemove(final double[] arr,final int newBound,final int oldBound)
  {
    final int newRootSize,numRemoved;
    size=newRootSize=size-(numRemoved=oldBound-newBound);
    ArrCopy.semicheckedSelfCopy(arr,oldBound,newBound,newRootSize-newBound);
    return numRemoved;
  }
  private double[] growInsert(double[] arr,final int index,final int size)
  {
    if(arr.length==size)
    {
      ArrCopy.semicheckedCopy(arr,0,arr=new double[OmniArray.growBy50Pct(size)],0,index);
      this.arr=arr;
    }
    return arr;
  }
  private void uncheckedAppend(final double val,final int size)
  {
    double[] arr;
    if((arr=this.arr).length==size)
    {
      ArrCopy.uncheckedCopy(arr,0,arr=new double[OmniArray.growBy50Pct(size)],0,size);
    }
    arr[size]=val;
    this.size=size+1;
  }
  private void uncheckedInit(final double val)
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
  private void uncheckedInsert(final int index,final double val,final int size)
  {
    final int tailDist;
    if((tailDist=size-index)==0)
    {
      uncheckedAppend(val,size);
    }
    else
    {
      double[] arr;
      ArrCopy.uncheckedCopy(arr=this.arr,index,arr=growInsert(arr,index,size),index+1,tailDist);
      arr[index]=val;
      this.size=size+1;
    }
  }
  private double uncheckedPop(final int newSize)
  {
    this.size=newSize;
    return arr[newSize];
  }
  //TODO toArray methods
  //TODO removeVal methods
  public static abstract class Unchecked extends DoubleArrSeq
  {
    private Unchecked()
    {
      super();
    }
    private Unchecked(final int capacity)
    {
      super(capacity);
    }
    private Unchecked(final int size,final double[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean add(final double val)
    {
      super.push(val);
      return true;
    }
    @Override
    public void add(final int index,final double val)
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
    public double getDouble(final int index)
    {
      return (double)arr[index];
    }
    @Override
    public double removeDoubleAt(final int index)
    {
      final double[] arr;
      final var removed=(double)(arr=this.arr)[index];
      eraseIndexHelper(arr,index,--size);
      return removed;
    }
    //TODO uncheckedRemoveIf
  }
  public static class UncheckedList extends Unchecked
  {
    public UncheckedList()
    {
      super();
    }
    public UncheckedList(final int capacity)
    {
      super(capacity);
    }
    public UncheckedList(final int size,final double[] arr)
    {
      super(size,arr);
    }
    @Override
    public Object clone()
    {
      final double[] arr;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,arr=new double[size],0,size);
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
  }
  //TODO UncheckedStack
  //TODO Checked
  //TODO CheckedList
  //TODO CheckedStack
}
