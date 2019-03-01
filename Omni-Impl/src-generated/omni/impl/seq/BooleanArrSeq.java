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
import omni.util.BooleanSortUtil;
import omni.util.ToStringUtil;
import omni.impl.seq.AbstractBooleanList;
import omni.impl.AbstractBooleanItr;
import omni.util.TypeUtil;
import omni.function.BooleanComparator;
import omni.function.BooleanConsumer;
import omni.function.BooleanPredicate;
public abstract class BooleanArrSeq extends AbstractBooleanList implements OmniCollection.OfBoolean
{
  private static void eraseIndexHelper(boolean[] arr,int index,int newSize)
  {
    ArrCopy.semicheckedSelfCopy(arr,index+1,index,newSize-index);
  }
  static  void uncheckedReplaceAll(boolean[] arr,int offset,int bound,BooleanPredicate operator)
  {
    do
    {
      arr[offset]=operator.test((boolean)arr[offset]);
    }
    while(++offset!=bound);
  }
  static  void uncheckedAscendingForEach(boolean[] arr,int begin,int end,BooleanConsumer action)
  {
    for(;;++begin)
    {
      action.accept((boolean)arr[begin]);
      if(begin==end)
      {
        return;
      }
    }
  }
  static  void uncheckedDescendingForEach(boolean[] arr,int begin,int end,BooleanConsumer action)
  {
    for(;;--end)
    {
      action.accept((boolean)arr[end]);
      if(begin==end)
      {
        return;
      }
    }
  }
  static int ascendingHashCode(boolean[] arr,int begin,int end)
  {
    int hash=31+Boolean.hashCode(arr[begin]);
    while(begin!=end)
    {
      hash=hash*31+Boolean.hashCode(arr[++begin]);
    }
    return hash;
  }
  static int descendingHashCode(boolean[] arr,int begin,int end)
  {
    int hash=31+Boolean.hashCode(arr[end]);
    while(begin!=end)
    {
      hash=hash*31+Boolean.hashCode(arr[--end]);
    }
    return hash;
  }
  static int ascendingToString(boolean[] arr,int begin,int end,char[] buffer,int bufferOffset)
  {
    for(bufferOffset=ToStringUtil.getStringBoolean(arr[begin],buffer,bufferOffset);begin!=end;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringBoolean(arr[++begin],buffer,++bufferOffset)){}
    return bufferOffset;
  }
  static int descendingToString(boolean[] arr,int begin,int end,char[] buffer,int bufferOffset)
  {
    for(bufferOffset=ToStringUtil.getStringBoolean(arr[end],buffer,bufferOffset);begin!=end;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringBoolean(arr[--end],buffer,++bufferOffset)){}
    return bufferOffset;
  }
  static void ascendingToString(boolean[] arr,int begin,int end,ToStringUtil.OmniStringBuilder builder)
  {
    for(builder.uncheckedAppend(arr[begin]);begin!=end;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppend(arr[++begin])){}
  }
  static void descendingToString(boolean[] arr,int begin,int end,ToStringUtil.OmniStringBuilder builder)
  {
    for(builder.uncheckedAppend(arr[end]);begin!=end;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppend(arr[--end])){}
  }
    static boolean uncheckedcontains (boolean[] arr,int begin,int end
    ,boolean val
    )
    {
      while(
      val^(arr[begin])
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
    static int uncheckedsearch (boolean[] arr,int end
    ,boolean val
    )
    {
      int index=end;
      while(
      val^(arr[index])
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
    static int uncheckedindexOf (boolean[] arr,int bound
    ,boolean val
    )
    {
      int index=0;
      while(
      val^(arr[index])
      )
      {
        if(++index==bound)
        {
          return -1;
        }
      }
      return index;
    }
    static int uncheckedlastIndexOf (boolean[] arr,int bound
    ,boolean val
    )
    {
      while(
      val^(arr[--bound])
      )
      {
        if(bound==0)
        {
          return -1;
        }
      }
      return bound;
    }
    static int uncheckedindexOf (boolean[] arr,int offset,int bound
    ,boolean val
    )
    {
      int index=offset;
      while(
      val^(arr[index])
      )
      {
        if(++index==bound)
        {
          return -1;
        }
      }
      return index-offset;
    }
    static int uncheckedlastIndexOf (boolean[] arr,int offset,int bound
    ,boolean val
    )
    {
      while(
      val^(arr[--bound])
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
  transient boolean[] arr;
  private BooleanArrSeq()
  {
    super();
    this.arr=OmniArray.OfBoolean.DEFAULT_ARR;
  }
  private BooleanArrSeq(final int capacity)
  {
    super();
    switch(capacity)
    {
    default:
      this.arr=new boolean[capacity];
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfBoolean.DEFAULT_ARR;
    case 0:
    }
  }
  private BooleanArrSeq(final int size,final boolean[] arr)
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
      if(size<=((OmniArray.MAX_ARR_SIZE)/14))
      {
        buffer[size=uncheckedToString(size,buffer=new char[size*7])]=']';
      }
      else
      {
        if(size>(Integer.MAX_VALUE/6))
        {
          throw new OutOfMemoryError();
        }
        final ToStringUtil.OmniStringBuilder builder;
        uncheckedToString(size,builder=new ToStringUtil.OmniStringBuilder(1,new char[size<=(int)((OmniArray.MAX_ARR_SIZE/6.5f))?((size*6)+(size>>1)):(OmniArray.MAX_ARR_SIZE)]));
        (buffer=builder.buffer)[size=builder.size]=']';
      }
      buffer[0]='[';
      return new String(buffer,0,size+1);
    }
    return "[]";
  }
  abstract void uncheckedForEach(final int size,final BooleanConsumer action);
  //TODO forEach methods
  //TODO removeIf methods
  //TODO peek methods
  //TODO uncheckedCopyInto methods
  //TODO contains methods
  //TODO indexOf methods
  //TODO lastIndexOf methods
  //TODO search methods
   public Boolean pop()
   {
     return popBoolean();
   }
   public void push(final Boolean val)
   {
     push((boolean)val);
   }
  public boolean popBoolean()
  {
    return uncheckedPop(size-1);
  }
  public void push(final boolean val)
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
      if(val instanceof Boolean)
      {
        return this.uncheckedRemoveVal(size,(boolean)(val));
      }
    }
    return false;
  }
  @Override
  public boolean set(final int index,final boolean val)
  {
    final boolean[] arr;
    final var oldVal=(boolean)(arr=this.arr)[index];
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
  abstract boolean uncheckedRemoveVal(final int size,final boolean val);
  abstract boolean uncheckedRemoveIf(final int size,final BooleanPredicate filter);
  private int finalizeSubListBatchRemove(final boolean[] arr,final int newBound,final int oldBound)
  {
    final int newRootSize,numRemoved;
    size=newRootSize=size-(numRemoved=oldBound-newBound);
    ArrCopy.semicheckedSelfCopy(arr,oldBound,newBound,newRootSize-newBound);
    return numRemoved;
  }
  private boolean[] growInsert(boolean[] arr,final int index,final int size)
  {
    if(arr.length==size)
    {
      ArrCopy.semicheckedCopy(arr,0,arr=new boolean[OmniArray.growBy50Pct(size)],0,index);
      this.arr=arr;
    }
    return arr;
  }
  private void uncheckedAppend(final boolean val,final int size)
  {
    boolean[] arr;
    if((arr=this.arr).length==size)
    {
      ArrCopy.uncheckedCopy(arr,0,arr=new boolean[OmniArray.growBy50Pct(size)],0,size);
    }
    arr[size]=val;
    this.size=size+1;
  }
  private void uncheckedInit(final boolean val)
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
  private void uncheckedInsert(final int index,final boolean val,final int size)
  {
    final int tailDist;
    if((tailDist=size-index)==0)
    {
      uncheckedAppend(val,size);
    }
    else
    {
      boolean[] arr;
      ArrCopy.uncheckedCopy(arr=this.arr,index,arr=growInsert(arr,index,size),index+1,tailDist);
      arr[index]=val;
      this.size=size+1;
    }
  }
  private boolean uncheckedPop(final int newSize)
  {
    this.size=newSize;
    return arr[newSize];
  }
  //TODO toArray methods
  //TODO removeVal methods
  public static abstract class Unchecked extends BooleanArrSeq
  {
    private Unchecked()
    {
      super();
    }
    private Unchecked(final int capacity)
    {
      super(capacity);
    }
    private Unchecked(final int size,final boolean[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean add(final boolean val)
    {
      super.push(val);
      return true;
    }
    @Override
    public void add(final int index,final boolean val)
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
    public boolean getBoolean(final int index)
    {
      return (boolean)arr[index];
    }
    @Override
    public boolean removeBooleanAt(final int index)
    {
      final boolean[] arr;
      final var removed=(boolean)(arr=this.arr)[index];
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
    public UncheckedList(final int size,final boolean[] arr)
    {
      super(size,arr);
    }
    @Override
    public Object clone()
    {
      final boolean[] arr;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,arr=new boolean[size],0,size);
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
