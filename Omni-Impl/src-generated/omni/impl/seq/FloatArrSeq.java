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
import omni.util.FloatSortUtil;
import omni.util.ToStringUtil;
import omni.util.HashUtil;
import omni.impl.seq.AbstractFloatList;
import omni.impl.AbstractFloatItr;
import omni.util.BitSetUtil;
import omni.util.TypeUtil;
import omni.function.FloatComparator;
import omni.function.FloatUnaryOperator;
import omni.function.FloatConsumer;
import omni.function.FloatPredicate;
public abstract class FloatArrSeq extends AbstractFloatList implements OmniCollection.OfFloat
{
  private static void eraseIndexHelper(float[] arr,int index,int newSize)
  {
    ArrCopy.semicheckedSelfCopy(arr,index+1,index,newSize-index);
  }
  static  void uncheckedReplaceAll(float[] arr,int offset,int bound,FloatUnaryOperator operator)
  {
    do
    {
      arr[offset]=operator.applyAsFloat((float)arr[offset]);
    }
    while(++offset!=bound);
  }
  static  void uncheckedAscendingForEach(float[] arr,int begin,int end,FloatConsumer action)
  {
    for(;;++begin)
    {
      action.accept((float)arr[begin]);
      if(begin==end)
      {
        return;
      }
    }
  }
  static  void uncheckedDescendingForEach(float[] arr,int begin,int end,FloatConsumer action)
  {
    for(;;--end)
    {
      action.accept((float)arr[end]);
      if(begin==end)
      {
        return;
      }
    }
  }
  static int ascendingHashCode(float[] arr,int begin,int end)
  {
    int hash=31+HashUtil.hashFloat(arr[begin]);
    while(begin!=end)
    {
      hash=hash*31+HashUtil.hashFloat(arr[++begin]);
    }
    return hash;
  }
  static int descendingHashCode(float[] arr,int begin,int end)
  {
    int hash=31+HashUtil.hashFloat(arr[end]);
    while(begin!=end)
    {
      hash=hash*31+HashUtil.hashFloat(arr[--end]);
    }
    return hash;
  }
  static int ascendingToString(float[] arr,int begin,int end,char[] buffer,int bufferOffset)
  {
    for(bufferOffset=ToStringUtil.getStringFloat(arr[begin],buffer,bufferOffset);begin!=end;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringFloat(arr[++begin],buffer,++bufferOffset)){}
    return bufferOffset;
  }
  static int descendingToString(float[] arr,int begin,int end,char[] buffer,int bufferOffset)
  {
    for(bufferOffset=ToStringUtil.getStringFloat(arr[end],buffer,bufferOffset);begin!=end;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringFloat(arr[--end],buffer,++bufferOffset)){}
    return bufferOffset;
  }
  static void ascendingToString(float[] arr,int begin,int end,ToStringUtil.OmniStringBuilder builder)
  {
    for(builder.uncheckedAppendFloat(arr[begin]);begin!=end;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendFloat(arr[++begin])){} 
  }
  static void descendingToString(float[] arr,int begin,int end,ToStringUtil.OmniStringBuilder builder)
  {
    for(builder.uncheckedAppendFloat(arr[end]);begin!=end;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendFloat(arr[--end])){} 
  }
    static boolean uncheckedcontainsBits(float[] arr,int begin,int end
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
    static int uncheckedsearchBits(float[] arr,int end
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
    static int uncheckedindexOfBits(float[] arr,int bound
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
    static int uncheckedlastIndexOfBits(float[] arr,int bound
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
    static int uncheckedindexOfBits(float[] arr,int offset,int bound
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
    static int uncheckedlastIndexOfBits(float[] arr,int offset,int bound
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
    static boolean uncheckedcontainsNaN(float[] arr,int begin,int end
    )
    {
      while(
      !Float.isNaN(arr[begin])
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
    static int uncheckedsearchNaN(float[] arr,int end
    )
    {
      int index=end;
      while(
      !Float.isNaN(arr[index])
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
    static int uncheckedindexOfNaN(float[] arr,int bound
    )
    {
      int index=0;
      while(
      !Float.isNaN(arr[index])
      )
      {
        if(++index==bound)
        {
          return -1;
        }
      }
      return index;
    }
    static int uncheckedlastIndexOfNaN(float[] arr,int bound
    )
    {
      while(
      !Float.isNaN(arr[--bound])
      )
      {
        if(bound==0)
        {
          return -1;
        }
      }
      return bound;
    }
    static int uncheckedindexOfNaN(float[] arr,int offset,int bound
    )
    {
      int index=offset;
      while(
      !Float.isNaN(arr[index])
      )
      {
        if(++index==bound)
        {
          return -1;
        }
      }
      return index-offset;
    }
    static int uncheckedlastIndexOfNaN(float[] arr,int offset,int bound
    )
    {
      while(
      !Float.isNaN(arr[--bound])
      )
      {
        if(bound==offset)
        {
          return -1;
        }
      }
      return bound-offset;
    }
    static boolean uncheckedcontains0(float[] arr,int begin,int end
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
    static int uncheckedsearch0(float[] arr,int end
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
    static int uncheckedindexOf0(float[] arr,int bound
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
    static int uncheckedlastIndexOf0(float[] arr,int bound
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
    static int uncheckedindexOf0(float[] arr,int offset,int bound
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
    static int uncheckedlastIndexOf0(float[] arr,int offset,int bound
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
  transient float[] arr;
  private FloatArrSeq()
  {
    super();
    this.arr=OmniArray.OfFloat.DEFAULT_ARR;
  }
  private FloatArrSeq(final int capacity)
  {
    super();
    switch(capacity)
    {
    default:
      this.arr=new float[capacity];
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfFloat.DEFAULT_ARR;
    case 0:
    }
  }
  private FloatArrSeq(final int size,final float[] arr)
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
      if(size<=(OmniArray.MAX_ARR_SIZE/34))
      {
        buffer[size=uncheckedToString(size,buffer=new char[size*17])]=']';
      }
      else
      {
        if(size>(Integer.MAX_VALUE/5))
        {
          throw new OutOfMemoryError();
        }
        final ToStringUtil.OmniStringBuilder builder;
        uncheckedToString(size,builder=new ToStringUtil.OmniStringBuilder(1,new char[size<=(OmniArray.MAX_ARR_SIZE/11)?(size*11):(OmniArray.MAX_ARR_SIZE)]));
        (buffer=builder.buffer)[size=builder.size]=']';
      }
      buffer[0]='[';
      return new String(buffer,0,size+1);
    }
    return "[]";
  }
  abstract void uncheckedForEach(final int size,final FloatConsumer action);
  //TODO forEach methods
  //TODO removeIf methods
  //TODO peek methods
  //TODO uncheckedCopyInto methods
  //TODO contains methods
  //TODO indexOf methods
  //TODO lastIndexOf methods
  //TODO search methods
  @Override
  protected boolean containsRawInt(final int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=0)
      {
        return uncheckedcontainsBits(this.arr,0,size-1,Float.floatToRawIntBits(val));
      }
      return uncheckedcontains0(this.arr,0,size-1);
    }
    return false;
  }
  @Override
  protected boolean removeValRawInt(final int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=0)
      {
        return uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
      }
      return uncheckedremoveVal0(size);
    }
    return false;
  }
   public Float pop()
   {
     return popFloat();
   }
   public void push(final Float val)
   {
     push((float)val);
   }
  public float popFloat()
  {
    return uncheckedPop(size-1);
  }
  public void push(final float val)
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
      if(val instanceof Float)
      {
        return this.uncheckedRemoveVal(size,(float)(val));
      }
    }
    return false;
  }
  @Override
  public float set(final int index,final float val)
  {
    final float[] arr;
    final var oldVal=(float)(arr=this.arr)[index];
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
  abstract boolean uncheckedRemoveValBits(final int size,final int bits);
  abstract boolean uncheckedRemoveValNaN(final int size);
  abstract boolean uncheckedRemoveIf(final int size,final FloatPredicate filter);
  private int finalizeSubListBatchRemove(final float[] arr,final int newBound,final int oldBound)
  {
    final int newRootSize,numRemoved;
    size=newRootSize=size-(numRemoved=oldBound-newBound);
    ArrCopy.semicheckedSelfCopy(arr,oldBound,newBound,newRootSize-newBound);
    return numRemoved;
  }
  private float[] growInsert(float[] arr,final int index,final int size)
  {
    if(arr.length==size)
    {
      ArrCopy.semicheckedCopy(arr,0,arr=new float[OmniArray.growBy50Pct(size)],0,index);
      this.arr=arr;
    }
    return arr;
  }
  private void uncheckedAppend(final float val,final int size)
  {
    float[] arr;
    if((arr=this.arr).length==size)
    {
      ArrCopy.uncheckedCopy(arr,0,arr=new float[OmniArray.growBy50Pct(size)],0,size);
    }
    arr[size]=val;
    this.size=size+1;
  }
  private void uncheckedInit(final float val)
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
  private void uncheckedInsert(final int index,final float val,final int size)
  {
    final int tailDist;
    if((tailDist=size-index)==0)
    {
      uncheckedAppend(val,size);
    }
    else
    {
      float[] arr;
      ArrCopy.uncheckedCopy(arr=this.arr,index,arr=growInsert(arr,index,size),index+1,tailDist);
      arr[index]=val;
      this.size=size+1;
    }
  }
  private float uncheckedPop(final int newSize)
  {
    this.size=newSize;
    return arr[newSize];
  }
  //TODO toArray methods
  //TODO removeVal methods
  public static abstract class Unchecked extends FloatArrSeq
  {
    private Unchecked()
    {
      super();
    }
    private Unchecked(final int capacity)
    {
      super(capacity);
    }
    private Unchecked(final int size,final float[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean add(final float val)
    {
      super.push(val);
      return true;
    }
    @Override
    public void add(final int index,final float val)
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
    public float getFloat(final int index)
    {
      return (float)arr[index];
    }
    @Override
    public float removeFloatAt(final int index)
    {
      final float[] arr;
      final var removed=(float)(arr=this.arr)[index];
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
    public UncheckedList(final int size,final float[] arr)
    {
      super(size,arr);
    }
    @Override
    public Object clone()
    {
      final float[] arr;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,arr=new float[size],0,size);
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
