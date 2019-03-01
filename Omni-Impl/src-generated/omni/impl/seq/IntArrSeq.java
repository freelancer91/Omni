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
import omni.util.IntSortUtil;
import omni.util.ToStringUtil;
import omni.impl.seq.AbstractIntList;
import omni.impl.AbstractIntItr;
import omni.util.BitSetUtil;
import omni.util.TypeUtil;
import java.util.function.IntBinaryOperator;
import java.util.function.IntUnaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
public abstract class IntArrSeq extends AbstractIntList implements OmniCollection.OfInt
{
  private static void eraseIndexHelper(int[] arr,int index,int newSize)
  {
    ArrCopy.semicheckedSelfCopy(arr,index+1,index,newSize-index);
  }
  static  void uncheckedReplaceAll(int[] arr,int offset,int bound,IntUnaryOperator operator)
  {
    do
    {
      arr[offset]=operator.applyAsInt((int)arr[offset]);
    }
    while(++offset!=bound);
  }
  static  void uncheckedAscendingForEach(int[] arr,int begin,int end,IntConsumer action)
  {
    for(;;++begin)
    {
      action.accept((int)arr[begin]);
      if(begin==end)
      {
        return;
      }
    }
  }
  static  void uncheckedDescendingForEach(int[] arr,int begin,int end,IntConsumer action)
  {
    for(;;--end)
    {
      action.accept((int)arr[end]);
      if(begin==end)
      {
        return;
      }
    }
  }
  static int ascendingHashCode(int[] arr,int begin,int end)
  {
    int hash=31+(arr[begin]);
    while(begin!=end)
    {
      hash=hash*31+(arr[++begin]);
    }
    return hash;
  }
  static int descendingHashCode(int[] arr,int begin,int end)
  {
    int hash=31+(arr[end]);
    while(begin!=end)
    {
      hash=hash*31+(arr[--end]);
    }
    return hash;
  }
  static int ascendingToString(int[] arr,int begin,int end,char[] buffer,int bufferOffset)
  {
    for(bufferOffset=ToStringUtil.getStringInt(arr[begin],buffer,bufferOffset);begin!=end;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringInt(arr[++begin],buffer,++bufferOffset)){}
    return bufferOffset;
  }
  static int descendingToString(int[] arr,int begin,int end,char[] buffer,int bufferOffset)
  {
    for(bufferOffset=ToStringUtil.getStringInt(arr[end],buffer,bufferOffset);begin!=end;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringInt(arr[--end],buffer,++bufferOffset)){}
    return bufferOffset;
  }
  static void ascendingToString(int[] arr,int begin,int end,ToStringUtil.OmniStringBuilder builder)
  {
    for(builder.uncheckedAppendInt(arr[begin]);begin!=end;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendInt(arr[++begin])){} 
  }
  static void descendingToString(int[] arr,int begin,int end,ToStringUtil.OmniStringBuilder builder)
  {
    for(builder.uncheckedAppendInt(arr[end]);begin!=end;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendInt(arr[--end])){} 
  }
    static boolean uncheckedcontains (int[] arr,int begin,int end
    ,int val
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
    static int uncheckedsearch (int[] arr,int end
    ,int val
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
    static int uncheckedindexOf (int[] arr,int bound
    ,int val
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
    static int uncheckedlastIndexOf (int[] arr,int bound
    ,int val
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
    static int uncheckedindexOf (int[] arr,int offset,int bound
    ,int val
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
    static int uncheckedlastIndexOf (int[] arr,int offset,int bound
    ,int val
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
  //TODO mark/pull survivors up/down
  transient int[] arr;
  private IntArrSeq()
  {
    super();
    this.arr=OmniArray.OfInt.DEFAULT_ARR;
  }
  private IntArrSeq(final int capacity)
  {
    super();
    switch(capacity)
    {
    default:
      this.arr=new int[capacity];
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfInt.DEFAULT_ARR;
    case 0:
    }
  }
  private IntArrSeq(final int size,final int[] arr)
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
  abstract int uncheckedToString(int size,char[] buffer);
  abstract void uncheckedToString(int size,ToStringUtil.OmniStringBuilder builder);
  @Override
  public String toString()
  {
    int size;
    if((size=this.size)!=0)
    {
      final char[] buffer;
      if(size<=(OmniArray.MAX_ARR_SIZE/26))
      {
        buffer[size=uncheckedToString(size,buffer=new char[size*13])]=']';
      }
      else
      {
        if(size>(Integer.MAX_VALUE/3))
        {
          throw new OutOfMemoryError();
        }
        final ToStringUtil.OmniStringBuilder builder;
        uncheckedToString(size,builder=new ToStringUtil.OmniStringBuilder(1,new char[size<=(OmniArray.MAX_ARR_SIZE>>>3)?(size<<3):(OmniArray.MAX_ARR_SIZE)]));
        (buffer=builder.buffer)[size=builder.size]=']';
      }
      buffer[0]='[';
      return new String(buffer,0,size+1);
    }
    return "[]";
  }
  abstract void uncheckedForEach(final int size,final IntConsumer action);
  //TODO forEach methods
  //TODO removeIf methods
  //TODO peek methods
  //TODO uncheckedCopyInto methods
  //TODO contains methods
  //TODO indexOf methods
  //TODO lastIndexOf methods
  //TODO search methods
   public Integer pop()
   {
     return popInt();
   }
   public void push(final Integer val)
   {
     push((int)val);
   }
  public int popInt()
  {
    return uncheckedPop(size-1);
  }
  public void push(final int val)
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
      if(val instanceof Integer)
      {
        return this.uncheckedRemoveVal(size,(int)(val));
      }
    }
    return false;
  }
  @Override
  public int set(final int index,final int val)
  {
    final int[] arr;
    final var oldVal=(int)(arr=this.arr)[index];
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
  abstract boolean uncheckedRemoveVal(final int size,final int val);
  abstract boolean uncheckedRemoveIf(final int size,final IntPredicate filter);
  private int finalizeSubListBatchRemove(final int[] arr,final int newBound,final int oldBound)
  {
    final int newRootSize,numRemoved;
    size=newRootSize=size-(numRemoved=oldBound-newBound);
    ArrCopy.semicheckedSelfCopy(arr,oldBound,newBound,newRootSize-newBound);
    return numRemoved;
  }
  private int[] growInsert(int[] arr,final int index,final int size)
  {
    if(arr.length==size)
    {
      ArrCopy.semicheckedCopy(arr,0,arr=new int[OmniArray.growBy50Pct(size)],0,index);
      this.arr=arr;
    }
    return arr;
  }
  private void uncheckedAppend(final int val,final int size)
  {
    int[] arr;
    if((arr=this.arr).length==size)
    {
      ArrCopy.uncheckedCopy(arr,0,arr=new int[OmniArray.growBy50Pct(size)],0,size);
    }
    arr[size]=val;
    this.size=size+1;
  }
  private void uncheckedInit(final int val)
  {
    int[] arr;
    if((arr=this.arr)==OmniArray.OfInt.DEFAULT_ARR)
    {
      this.arr=arr=new int[OmniArray.DEFAULT_ARR_SEQ_CAP];
    }
    else if(arr==null)
    {
      this.arr=arr=new int[1];
    }
    arr[0]=val;
    this.size=1;
  }
  private void uncheckedInsert(final int index,final int val,final int size)
  {
    final int tailDist;
    if((tailDist=size-index)==0)
    {
      uncheckedAppend(val,size);
    }
    else
    {
      int[] arr;
      ArrCopy.uncheckedCopy(arr=this.arr,index,arr=growInsert(arr,index,size),index+1,tailDist);
      arr[index]=val;
      this.size=size+1;
    }
  }
  private int uncheckedPop(final int newSize)
  {
    this.size=newSize;
    return arr[newSize];
  }
  //TODO toArray methods
  //TODO removeVal methods
  public static abstract class Unchecked extends IntArrSeq
  {
    private Unchecked()
    {
      super();
    }
    private Unchecked(final int capacity)
    {
      super(capacity);
    }
    private Unchecked(final int size,final int[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean add(final int val)
    {
      super.push(val);
      return true;
    }
    @Override
    public void add(final int index,final int val)
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
    public int getInt(final int index)
    {
      return (int)arr[index];
    }
    @Override
    public int removeIntAt(final int index)
    {
      final int[] arr;
      final var removed=(int)(arr=this.arr)[index];
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
    public UncheckedList(final int size,final int[] arr)
    {
      super(size,arr);
    }
    @Override
    public Object clone()
    {
      final int[] arr;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,arr=new int[size],0,size);
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
