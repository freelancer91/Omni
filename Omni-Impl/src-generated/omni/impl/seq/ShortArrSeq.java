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
import omni.util.ShortSortUtil;
import omni.util.ToStringUtil;
import omni.impl.seq.AbstractShortList;
import omni.impl.AbstractShortItr;
import omni.util.BitSetUtil;
import omni.util.TypeUtil;
import omni.function.ShortComparator;
import omni.function.ShortUnaryOperator;
import omni.function.ShortConsumer;
import omni.function.ShortPredicate;
public abstract class ShortArrSeq extends AbstractShortList implements OmniCollection.OfShort
{
  private static void eraseIndexHelper(short[] arr,int index,int newSize)
  {
    ArrCopy.semicheckedSelfCopy(arr,index+1,index,newSize-index);
  }
  static  void uncheckedReplaceAll(short[] arr,int offset,int bound,ShortUnaryOperator operator)
  {
    do
    {
      arr[offset]=operator.applyAsShort((short)arr[offset]);
    }
    while(++offset!=bound);
  }
  static  void uncheckedAscendingForEach(short[] arr,int begin,int end,ShortConsumer action)
  {
    for(;;++begin)
    {
      action.accept((short)arr[begin]);
      if(begin==end)
      {
        return;
      }
    }
  }
  static  void uncheckedDescendingForEach(short[] arr,int begin,int end,ShortConsumer action)
  {
    for(;;--end)
    {
      action.accept((short)arr[end]);
      if(begin==end)
      {
        return;
      }
    }
  }
  static int ascendingHashCode(short[] arr,int begin,int end)
  {
    int hash=31+(arr[begin]);
    while(begin!=end)
    {
      hash=hash*31+(arr[++begin]);
    }
    return hash;
  }
  static int descendingHashCode(short[] arr,int begin,int end)
  {
    int hash=31+(arr[end]);
    while(begin!=end)
    {
      hash=hash*31+(arr[--end]);
    }
    return hash;
  }
  static int ascendingToString(short[] arr,int begin,int end,char[] buffer,int bufferOffset)
  {
    for(bufferOffset=ToStringUtil.getStringShort(arr[begin],buffer,bufferOffset);begin!=end;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringShort(arr[++begin],buffer,++bufferOffset)){}
    return bufferOffset;
  }
  static int descendingToString(short[] arr,int begin,int end,char[] buffer,int bufferOffset)
  {
    for(bufferOffset=ToStringUtil.getStringShort(arr[end],buffer,bufferOffset);begin!=end;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringShort(arr[--end],buffer,++bufferOffset)){}
    return bufferOffset;
  }
  static void ascendingToString(short[] arr,int begin,int end,ToStringUtil.OmniStringBuilder builder)
  {
    for(builder.uncheckedAppendShort(arr[begin]);begin!=end;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendShort(arr[++begin])){} 
  }
  static void descendingToString(short[] arr,int begin,int end,ToStringUtil.OmniStringBuilder builder)
  {
    for(builder.uncheckedAppendShort(arr[end]);begin!=end;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendShort(arr[--end])){} 
  }
    static boolean uncheckedcontains (short[] arr,int begin,int end
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
    static int uncheckedsearch (short[] arr,int end
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
    static int uncheckedindexOf (short[] arr,int bound
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
    static int uncheckedlastIndexOf (short[] arr,int bound
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
    static int uncheckedindexOf (short[] arr,int offset,int bound
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
    static int uncheckedlastIndexOf (short[] arr,int offset,int bound
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
  transient short[] arr;
  private ShortArrSeq()
  {
    super();
    this.arr=OmniArray.OfShort.DEFAULT_ARR;
  }
  private ShortArrSeq(final int capacity)
  {
    super();
    switch(capacity)
    {
    default:
      this.arr=new short[capacity];
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfShort.DEFAULT_ARR;
    case 0:
    }
  }
  private ShortArrSeq(final int size,final short[] arr)
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
      if(size<=(OmniArray.MAX_ARR_SIZE>>>4))
      {
        buffer[size=uncheckedToString(size,buffer=new char[size<<3])]=']';
      }
      else
      {
        if(size>(Integer.MAX_VALUE/3))
        {
          throw new OutOfMemoryError();
        }
        final ToStringUtil.OmniStringBuilder builder;
        uncheckedToString(size,builder=new ToStringUtil.OmniStringBuilder(1,new char[size<=(int)((OmniArray.MAX_ARR_SIZE/5.5f))?((size*5)+(size>>1)):(OmniArray.MAX_ARR_SIZE)]));
        (buffer=builder.buffer)[size=builder.size]=']';
      }
      buffer[0]='[';
      return new String(buffer,0,size+1);
    }
    return "[]";
  }
  abstract void uncheckedForEach(final int size,final ShortConsumer action);
  //TODO forEach methods
  //TODO removeIf methods
  //TODO peek methods
  //TODO uncheckedCopyInto methods
  //TODO contains methods
  //TODO indexOf methods
  //TODO lastIndexOf methods
  //TODO search methods
   public Short pop()
   {
     return popShort();
   }
   public void push(final Short val)
   {
     push((short)val);
   }
  public short popShort()
  {
    return uncheckedPop(size-1);
  }
  public void push(final short val)
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
      if(val instanceof Short)
      {
        return this.uncheckedRemoveVal(size,(short)(val));
      }
    }
    return false;
  }
  @Override
  public short set(final int index,final short val)
  {
    final short[] arr;
    final var oldVal=(short)(arr=this.arr)[index];
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
  abstract boolean uncheckedRemoveIf(final int size,final ShortPredicate filter);
  private int finalizeSubListBatchRemove(final short[] arr,final int newBound,final int oldBound)
  {
    final int newRootSize,numRemoved;
    size=newRootSize=size-(numRemoved=oldBound-newBound);
    ArrCopy.semicheckedSelfCopy(arr,oldBound,newBound,newRootSize-newBound);
    return numRemoved;
  }
  private short[] growInsert(short[] arr,final int index,final int size)
  {
    if(arr.length==size)
    {
      ArrCopy.semicheckedCopy(arr,0,arr=new short[OmniArray.growBy50Pct(size)],0,index);
      this.arr=arr;
    }
    return arr;
  }
  private void uncheckedAppend(final short val,final int size)
  {
    short[] arr;
    if((arr=this.arr).length==size)
    {
      ArrCopy.uncheckedCopy(arr,0,arr=new short[OmniArray.growBy50Pct(size)],0,size);
    }
    arr[size]=val;
    this.size=size+1;
  }
  private void uncheckedInit(final short val)
  {
    short[] arr;
    if((arr=this.arr)==OmniArray.OfShort.DEFAULT_ARR)
    {
      this.arr=arr=new short[OmniArray.DEFAULT_ARR_SEQ_CAP];
    }
    else if(arr==null)
    {
      this.arr=arr=new short[1];
    }
    arr[0]=val;
    this.size=1;
  }
  private void uncheckedInsert(final int index,final short val,final int size)
  {
    final int tailDist;
    if((tailDist=size-index)==0)
    {
      uncheckedAppend(val,size);
    }
    else
    {
      short[] arr;
      ArrCopy.uncheckedCopy(arr=this.arr,index,arr=growInsert(arr,index,size),index+1,tailDist);
      arr[index]=val;
      this.size=size+1;
    }
  }
  private short uncheckedPop(final int newSize)
  {
    this.size=newSize;
    return arr[newSize];
  }
  //TODO toArray methods
  //TODO removeVal methods
  public static abstract class Unchecked extends ShortArrSeq
  {
    private Unchecked()
    {
      super();
    }
    private Unchecked(final int capacity)
    {
      super(capacity);
    }
    private Unchecked(final int size,final short[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean add(final short val)
    {
      super.push(val);
      return true;
    }
    @Override
    public void add(final int index,final short val)
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
    public short getShort(final int index)
    {
      return (short)arr[index];
    }
    @Override
    public short removeShortAt(final int index)
    {
      final short[] arr;
      final var removed=(short)(arr=this.arr)[index];
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
    public UncheckedList(final int size,final short[] arr)
    {
      super(size,arr);
    }
    @Override
    public Object clone()
    {
      final short[] arr;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,arr=new short[size],0,size);
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
