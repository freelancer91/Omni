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
import omni.util.LongSortUtil;
import omni.util.ToStringUtil;
import omni.impl.seq.AbstractLongList;
import omni.impl.AbstractLongItr;
import omni.util.BitSetUtil;
import omni.util.TypeUtil;
import omni.function.LongComparator;
import java.util.function.LongUnaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
public abstract class LongArrSeq extends AbstractLongList implements OmniCollection.OfLong
{
  private static void eraseIndexHelper(long[] arr,int index,int newSize)
  {
    ArrCopy.semicheckedSelfCopy(arr,index+1,index,newSize-index);
  }
  static  void uncheckedReplaceAll(long[] arr,int offset,int bound,LongUnaryOperator operator)
  {
    do
    {
      arr[offset]=operator.applyAsLong((long)arr[offset]);
    }
    while(++offset!=bound);
  }
  static  void uncheckedAscendingForEach(long[] arr,int begin,int end,LongConsumer action)
  {
    for(;;++begin)
    {
      action.accept((long)arr[begin]);
      if(begin==end)
      {
        return;
      }
    }
  }
  static  void uncheckedDescendingForEach(long[] arr,int begin,int end,LongConsumer action)
  {
    for(;;--end)
    {
      action.accept((long)arr[end]);
      if(begin==end)
      {
        return;
      }
    }
  }
  static int ascendingHashCode(long[] arr,int begin,int end)
  {
    int hash=31+Long.hashCode(arr[begin]);
    while(begin!=end)
    {
      hash=hash*31+Long.hashCode(arr[++begin]);
    }
    return hash;
  }
  static int descendingHashCode(long[] arr,int begin,int end)
  {
    int hash=31+Long.hashCode(arr[end]);
    while(begin!=end)
    {
      hash=hash*31+Long.hashCode(arr[--end]);
    }
    return hash;
  }
  static int ascendingToString(long[] arr,int begin,int end,char[] buffer,int bufferOffset)
  {
    for(bufferOffset=ToStringUtil.getStringLong(arr[begin],buffer,bufferOffset);begin!=end;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringLong(arr[++begin],buffer,++bufferOffset)){}
    return bufferOffset;
  }
  static int descendingToString(long[] arr,int begin,int end,char[] buffer,int bufferOffset)
  {
    for(bufferOffset=ToStringUtil.getStringLong(arr[end],buffer,bufferOffset);begin!=end;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringLong(arr[--end],buffer,++bufferOffset)){}
    return bufferOffset;
  }
  static void ascendingToString(long[] arr,int begin,int end,ToStringUtil.OmniStringBuilder builder)
  {
    for(builder.uncheckedAppendLong(arr[begin]);begin!=end;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendLong(arr[++begin])){} 
  }
  static void descendingToString(long[] arr,int begin,int end,ToStringUtil.OmniStringBuilder builder)
  {
    for(builder.uncheckedAppendLong(arr[end]);begin!=end;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendLong(arr[--end])){} 
  }
    static boolean uncheckedcontains (long[] arr,int begin,int end
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
    static int uncheckedsearch (long[] arr,int end
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
    static int uncheckedindexOf (long[] arr,int bound
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
    static int uncheckedlastIndexOf (long[] arr,int bound
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
    static int uncheckedindexOf (long[] arr,int offset,int bound
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
    static int uncheckedlastIndexOf (long[] arr,int offset,int bound
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
  //TODO mark/pull survivors up/down
  transient long[] arr;
  private LongArrSeq()
  {
    super();
    this.arr=OmniArray.OfLong.DEFAULT_ARR;
  }
  private LongArrSeq(final int capacity)
  {
    super();
    switch(capacity)
    {
    default:
      this.arr=new long[capacity];
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfLong.DEFAULT_ARR;
    case 0:
    }
  }
  private LongArrSeq(final int size,final long[] arr)
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
      if(size<=(OmniArray.MAX_ARR_SIZE/44))
      {
        buffer[size=uncheckedToString(size,buffer=new char[size*22])]=']';
      }
      else
      {
        if(size>(Integer.MAX_VALUE/3))
        {
          throw new OutOfMemoryError();
        }
        final ToStringUtil.OmniStringBuilder builder;
        uncheckedToString(size,builder=new ToStringUtil.OmniStringBuilder(1,new char[size<=(int)((OmniArray.MAX_ARR_SIZE/12.5f))?((size*12)+(size>>1)):(OmniArray.MAX_ARR_SIZE)]));
        (buffer=builder.buffer)[size=builder.size]=']';
      }
      buffer[0]='[';
      return new String(buffer,0,size+1);
    }
    return "[]";
  }
  abstract void uncheckedForEach(final int size,final LongConsumer action);
  //TODO forEach methods
  //TODO removeIf methods
  //TODO peek methods
  //TODO uncheckedCopyInto methods
  //TODO contains methods
  //TODO indexOf methods
  //TODO lastIndexOf methods
  //TODO search methods
   public Long pop()
   {
     return popLong();
   }
   public void push(final Long val)
   {
     push((long)val);
   }
  public long popLong()
  {
    return uncheckedPop(size-1);
  }
  public void push(final long val)
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
      if(val instanceof Long)
      {
        return this.uncheckedRemoveVal(size,(long)(val));
      }
    }
    return false;
  }
  @Override
  public long set(final int index,final long val)
  {
    final long[] arr;
    final var oldVal=(long)(arr=this.arr)[index];
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
  abstract boolean uncheckedRemoveVal(final int size,final long val);
  abstract boolean uncheckedRemoveIf(final int size,final LongPredicate filter);
  private int finalizeSubListBatchRemove(final long[] arr,final int newBound,final int oldBound)
  {
    final int newRootSize,numRemoved;
    size=newRootSize=size-(numRemoved=oldBound-newBound);
    ArrCopy.semicheckedSelfCopy(arr,oldBound,newBound,newRootSize-newBound);
    return numRemoved;
  }
  private long[] growInsert(long[] arr,final int index,final int size)
  {
    if(arr.length==size)
    {
      ArrCopy.semicheckedCopy(arr,0,arr=new long[OmniArray.growBy50Pct(size)],0,index);
      this.arr=arr;
    }
    return arr;
  }
  private void uncheckedAppend(final long val,final int size)
  {
    long[] arr;
    if((arr=this.arr).length==size)
    {
      ArrCopy.uncheckedCopy(arr,0,arr=new long[OmniArray.growBy50Pct(size)],0,size);
    }
    arr[size]=val;
    this.size=size+1;
  }
  private void uncheckedInit(final long val)
  {
    long[] arr;
    if((arr=this.arr)==OmniArray.OfLong.DEFAULT_ARR)
    {
      this.arr=arr=new long[OmniArray.DEFAULT_ARR_SEQ_CAP];
    }
    else if(arr==null)
    {
      this.arr=arr=new long[1];
    }
    arr[0]=val;
    this.size=1;
  }
  private void uncheckedInsert(final int index,final long val,final int size)
  {
    final int tailDist;
    if((tailDist=size-index)==0)
    {
      uncheckedAppend(val,size);
    }
    else
    {
      long[] arr;
      ArrCopy.uncheckedCopy(arr=this.arr,index,arr=growInsert(arr,index,size),index+1,tailDist);
      arr[index]=val;
      this.size=size+1;
    }
  }
  private long uncheckedPop(final int newSize)
  {
    this.size=newSize;
    return arr[newSize];
  }
  //TODO toArray methods
  //TODO removeVal methods
  public static abstract class Unchecked extends LongArrSeq
  {
    private Unchecked()
    {
      super();
    }
    private Unchecked(final int capacity)
    {
      super(capacity);
    }
    private Unchecked(final int size,final long[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean add(final long val)
    {
      super.push(val);
      return true;
    }
    @Override
    public void add(final int index,final long val)
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
    public long getLong(final int index)
    {
      return (long)arr[index];
    }
    @Override
    public long removeLongAt(final int index)
    {
      final long[] arr;
      final var removed=(long)(arr=this.arr)[index];
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
    public UncheckedList(final int size,final long[] arr)
    {
      super(size,arr);
    }
    @Override
    public Object clone()
    {
      final long[] arr;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,arr=new long[size],0,size);
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
