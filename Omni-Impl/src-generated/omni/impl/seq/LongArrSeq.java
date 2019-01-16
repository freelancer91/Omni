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
import omni.impl.seq.AbstractLongList;
import omni.impl.AbstractLongItr;
import omni.util.BitSetUtils;
import omni.util.TypeUtil;
import omni.function.LongComparator;
import java.util.function.LongUnaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
public abstract class LongArrSeq extends AbstractLongList implements OmniCollection.OfLong
{
  private static void eraseIndexHelper(long[] arr,int index,int newSize)
  {
    ArrCopy.semicheckedCopy(arr,index+1,arr,index,newSize-index);
  }
  static boolean uncheckedcontains(long[] arr,int offset,int bound,long val)
  {
    for(;val!=(arr[offset]);)
    {
      if(++offset==bound)
      {
        return false;
      }
    }
    return true;
  }
  private static int uncheckedindexOf(long[] arr,int offset,int bound,long val)
  {
    int index;
    for(index=offset;val!=(arr[index]);)
    {
      if(++index==bound)
      {
        return -1;
      }
    }
    return index-offset;
  }
  private static int uncheckedindexOf(long[] arr,int bound,long val)
  {
    int index;
    for(index=0;val!=(arr[index]);)
    {
      if(++index==bound)
      {
        return -1;
      }
    }
    return index;
  }
  private static int uncheckedlastIndexOf(long[] arr,int offset,int bound,long val)
  {
    for(;val!=(arr[--bound]);)
    {
      if(bound==offset)
      {
        return -1;
      }
    }
    return bound-offset;
  }
  private static int uncheckedlastIndexOf(long[] arr,int bound,long val)
  {
    for(;val!=(arr[--bound]);)
    {
      if(bound==0)
      {
        return -1;
      }
    }
    return bound;
  }
  private static int uncheckedsearch(long[] arr,int bound,long val)
  {
    int index;
    for(index=bound-1;val!=(arr[index]);--index)
    {
      if(index==0)
      {
        return -1;
      }
    }
    return bound-index;
  }
  private static  void uncheckedreplaceAll(long[] arr,int offset,int bound,LongUnaryOperator operator)
  {
    do
    {
      arr[offset]=operator.applyAsLong((long)arr[offset]);
    }
    while(++offset!=bound);
  }
  static  void uncheckedForwardForEach(long[] arr,int offset,int bound,LongConsumer action)
  {
    do
    {
      action.accept((long)arr[offset]);
    }
    while(++offset!=bound);
  }
  static  void uncheckedReverseForEach(long[] arr,int offset,int bound,LongConsumer action)
  {
    do
    {
      action.accept((long)arr[--bound]);
    }
    while(bound!=offset);
  }
  private static int forwardHashCode(long[] arr,int offset,int bound)
  {
    int hash=31+Long.hashCode(arr[offset]);
    while(++offset!=bound)
    {
      hash=hash*31+Long.hashCode(arr[offset]);
    }
    return hash;
  }
  private static int reverseHashCode(long[] arr,int offset,int bound)
  {
    int hash=31+Long.hashCode(arr[offset]);
    while(bound!=offset)
    {
      hash=hash*31+Long.hashCode(arr[offset]);
    }
    return hash;
  }
  private static void forwardToString(long[] arr,int offset,int bound,StringBuilder builder)
  {
    for(builder.append(arr[offset]);++offset!=bound;builder.append(',').append(' ').append(arr[offset]));
  }
  private static void reverseToString(long[] arr,int offset,int bound,StringBuilder builder)
  {
    for(builder.append(arr[--bound]);bound!=offset;builder.append(',').append(' ').append(arr[--bound]));
  }
  static  int pullSurvivorsUp(long[] arr,int srcBegin,int srcEnd,LongPredicate filter)
  {
    int dstOffset=srcBegin;
    while(srcBegin!=srcEnd)
    {
      final long v;
      if(!filter.test((long)(v=arr[--srcBegin])))
      {
        arr[dstOffset--]=v;
      }
    }
    return dstOffset;
  }
  static  int pullSurvivorsDown(long[] arr,int srcBegin,int srcEnd,LongPredicate filter)
  {
    int dstOffset=srcBegin;
    while(srcBegin!=srcEnd)
    {
      final long v;
      if(!filter.test((long)(v=arr[++srcBegin])))
      {
        arr[dstOffset++]=v;
      }
    }
    return dstOffset;
  }
  static  int markSurvivorsAndPullUp(CheckedCollection.AbstractModCountChecker modCountChecker,long[] arr,int srcOffset,int srcBound,LongPredicate filter)
  {
    int dstOffset=srcOffset;
    for(;;)
    {
      if(--srcOffset==srcBound)
      {
        modCountChecker.checkModCount();
        break;
      }
      final long v;
      if(!filter.test((long)(v=arr[srcOffset])))
      {
        int numSurvivors;
        if((numSurvivors=(--srcOffset)-srcBound)!=0)
        {
          final long[] survivors;
          numSurvivors=markSurvivorsReverse(arr,survivors=BitSetUtils.getBitSet(numSurvivors),srcOffset,srcBound,filter);
          modCountChecker.checkModCount();
          arr[dstOffset--]=v;
          if(numSurvivors!=0)
          {
            dstOffset=pullSurvivorsUp(arr,survivors,dstOffset,srcOffset,numSurvivors);
          }
        }
        else
        {
          modCountChecker.checkModCount();
          arr[dstOffset--]=v;
        }
        break;
      }
    }
    return dstOffset;
  }
  static  int markSurvivorsAndPullDown(CheckedCollection.AbstractModCountChecker modCountChecker,long[] arr,int srcOffset,int srcBound,LongPredicate filter)
  {
    int dstOffset=srcOffset;
    for(;;)
    {
      if(++srcOffset==srcBound)
      {
        modCountChecker.checkModCount();
        break;
      }
      final long v;
      if(!filter.test((long)(v=arr[srcOffset])))
      {
        int numSurvivors;
        if((numSurvivors=srcBound-++srcOffset)!=0)
        {
          final long[] survivors;
          numSurvivors=markSurvivors(arr,survivors=BitSetUtils.getBitSet(numSurvivors),srcOffset,srcBound,filter);
          modCountChecker.checkModCount();
          arr[dstOffset++]=v;
          if(numSurvivors!=0)
          {
            dstOffset=pullSurvivorsDown(arr,survivors,dstOffset,srcOffset,numSurvivors);
          }
        }
        else
        {
          modCountChecker.checkModCount();
          arr[dstOffset++]=v;
        }
        break;
      }
    }
    return dstOffset;
  }
  static  int markSurvivorsReverse(long[] arr,long[] survivorSet,int offset,int bound,LongPredicate filter)
  {
    for(int numSurvivors=0,wordOffset=survivorSet.length-1;;)
    {
      long word=0L,marker=1L<<63;
      do
      {
        if(!filter.test((long)arr[offset]))
        {
          ++numSurvivors;
          word|=marker;
        }
        if(--offset==bound)
        {
          survivorSet[wordOffset]=word;
          return numSurvivors;
        }
      }
      while((marker>>>=1)!=0);
      survivorSet[wordOffset--]=word;
    }
  }
  static  int markSurvivors(long[] arr,long[] survivorSet,int offset,int bound,LongPredicate filter)
  {
    for(int numSurvivors=0,wordOffset=0;;)
    {
      long word=0L,marker=1L;
      do
      {
        if(!filter.test((long)arr[offset]))
        {
          ++numSurvivors;
          word|=marker;
        }
        if(++offset==bound)
        {
          survivorSet[wordOffset]=word;
          return numSurvivors;
        }
      }
      while((marker<<=1)!=0);
      survivorSet[wordOffset++]=word;
    }
  }
  static int pullSurvivorsUp(long[] arr,long[] survivorSet,int dstOffset,int srcOffset,int numSurvivors)
  {
    for(int survivorSetOffset=survivorSet.length-1;;--survivorSetOffset,srcOffset-=64)
    {
      long survivorWord;
      int runLength;
      if((runLength=Long.numberOfLeadingZeros(survivorWord=survivorSet[survivorSetOffset]))!=64)
      {
        int wordSrcOffset=srcOffset;
        do
        {
          ArrCopy.uncheckedCopy(arr,(wordSrcOffset-=(runLength+(runLength=Long.numberOfLeadingZeros(~(survivorWord<<=runLength))))),arr,dstOffset-=runLength,runLength);
          if((numSurvivors-=runLength)==0)
          {
            return dstOffset;
          }
        }
        while((runLength=Long.numberOfLeadingZeros(survivorWord<<=runLength))!=64);
      }
    }
  }
  static int pullSurvivorsDown(long[] arr,long[] survivorSet,int dstOffset,int srcOffset,int numSurvivors)
  {
    for(int survivorSetOffset=0;;++survivorSetOffset,srcOffset+=64)
    {
      long survivorWord;
      int runLength;
      if((runLength=Long.numberOfTrailingZeros(survivorWord=survivorSet[survivorSetOffset]))!=64)
      {
        int wordSrcOffset=srcOffset;
        do
        {
          ArrCopy.uncheckedCopy(arr,wordSrcOffset+=runLength,arr,dstOffset,runLength=Long.numberOfTrailingZeros(~(survivorWord>>>=runLength)));
          dstOffset+=runLength;
          if((numSurvivors-=runLength)==0)
          {
            return dstOffset;
          }
          wordSrcOffset+=runLength;
        }
        while((runLength=Long.numberOfTrailingZeros(survivorWord>>>=runLength))!=64);
      }
    }
  }
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
      return;
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
  @Override
  public boolean contains(final Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Long)
      {
        return LongArrSeq.uncheckedcontains(arr,0,size,(long)(val));
      }
    }
    return false;
  }
  public int indexOf(final Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Long)
      {
        return LongArrSeq.uncheckedindexOf(arr,size,(long)(val));
      }
    }
    return -1;
  }
  public int lastIndexOf(final Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Long)
      {
        return LongArrSeq.uncheckedlastIndexOf(arr,size,(long)(val));
      }
    }
    return -1;
  }
  public int search(final Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Long)
      {
        return LongArrSeq.uncheckedsearch(arr,size,(long)(val));
      }
    }
    return -1;
  }
  @Override
  public boolean contains(final boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return LongArrSeq.uncheckedcontains(arr,0,size,TypeUtil.castToLong(val));
    }
    return false;
  }
  @Override
  public boolean contains(final int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      {
        return LongArrSeq.uncheckedcontains(arr,0,size,(val));
      }
    }
    return false;
  }
  @Override
  public boolean contains(final long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return LongArrSeq.uncheckedcontains(arr,0,size,(val));
    }
    return false;
  }
  @Override
  public boolean contains(final float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final long v;
      if(TypeUtil.floatEquals(val,v=(long)val))
      {
        return LongArrSeq.uncheckedcontains(arr,0,size,v);
      }
    }
    return false;
  }
  @Override
  public boolean contains(final double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final long v;
      if(val==(v=(long)val))
      {
        return LongArrSeq.uncheckedcontains(arr,0,size,v);
      }
    }
    return false;
  }
  @Override
  public void forEach(final LongConsumer action)
  {
    final int size;
    if((size=this.size)!=0)
    {
      uncheckedForEach(size,action);
    }
  }
  @Override
  public void forEach(final Consumer<? super Long> action)
  {
    final int size;
    if((size=this.size)!=0)
    {
      uncheckedForEach(size,action::accept);
    }
  }
  @Override
  public boolean removeIf(final LongPredicate filter)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedRemoveIf(size,filter);
    }
    return false;
  }
  @Override
  public boolean removeIf(final Predicate<? super Long> filter)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedRemoveIf(size,filter::test);
    }
    return false;
  }
  public Long pop()
  {
    return popLong();
  }
  public void push(final Long val)
  {
    push((long)val);
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
  public int indexOf(final boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return LongArrSeq.uncheckedindexOf(arr,size,TypeUtil.castToLong(val));
    }
    return -1;
  }
  public int indexOf(final int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      {
        return LongArrSeq.uncheckedindexOf(arr,size,(val));
      }
    }
    return -1;
  }
  public int indexOf(final long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return LongArrSeq.uncheckedindexOf(arr,size,(val));
    }
    return -1;
  }
  public int indexOf(final float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final long v;
      if(TypeUtil.floatEquals(val,v=(long)val))
      {
        return LongArrSeq.uncheckedindexOf(arr,size,v);
      }
    }
    return -1;
  }
  public int indexOf(final double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final long v;
      if(val==(v=(long)val))
      {
        return LongArrSeq.uncheckedindexOf(arr,size,v);
      }
    }
    return -1;
  }
  public int lastIndexOf(final boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return LongArrSeq.uncheckedlastIndexOf(arr,size,TypeUtil.castToLong(val));
    }
    return -1;
  }
  public int lastIndexOf(final int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      {
        return LongArrSeq.uncheckedlastIndexOf(arr,size,(val));
      }
    }
    return -1;
  }
  public int lastIndexOf(final long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return LongArrSeq.uncheckedlastIndexOf(arr,size,(val));
    }
    return -1;
  }
  public int lastIndexOf(final float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final long v;
      if(TypeUtil.floatEquals(val,v=(long)val))
      {
        return LongArrSeq.uncheckedlastIndexOf(arr,size,v);
      }
    }
    return -1;
  }
  public int lastIndexOf(final double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final long v;
      if(val==(v=(long)val))
      {
        return LongArrSeq.uncheckedlastIndexOf(arr,size,v);
      }
    }
    return -1;
  }
  public long peekLong()
  {
    final int size;
    if((size=this.size)!=0)
    {
      return (long)(arr[size-1]);
    }
    return Long.MIN_VALUE;
  }
  abstract void uncheckedCopyInto(final long[] dst,final int length);
  public Long peek()
  {
    final int size;
    if((size=this.size)!=0)
    {
      return (arr[size-1]);
    }
    return null;
  }
  abstract void uncheckedCopyInto(final Long[] dst,final int length);
  abstract void uncheckedCopyInto(final Object[] dst,final int length);
  public double peekDouble()
  {
    final int size;
    if((size=this.size)!=0)
    {
      return (double)(arr[size-1]);
    }
    return Double.NaN;
  }
  abstract void uncheckedCopyInto(final double[] dst,final int length);
  public float peekFloat()
  {
    final int size;
    if((size=this.size)!=0)
    {
      return (float)(arr[size-1]);
    }
    return Float.NaN;
  }
  abstract void uncheckedCopyInto(final float[] dst,final int length);
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
        return this.uncheckedremoveVal(size,(long)(val));
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(final boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,TypeUtil.castToLong(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(final int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      {
        return this.uncheckedremoveVal(size,(val));
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(final long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(final float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final long v;
      if(TypeUtil.floatEquals(val,v=(long)val))
      {
        return this.uncheckedremoveVal(size,v);
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(final double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final long v;
      if(val==(v=(long)val))
      {
        return this.uncheckedremoveVal(size,v);
      }
    }
    return false;
  }
  public int search(final boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return LongArrSeq.uncheckedsearch(arr,size,TypeUtil.castToLong(val));
    }
    return -1;
  }
  public int search(final int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      {
        return LongArrSeq.uncheckedsearch(arr,size,(val));
      }
    }
    return -1;
  }
  public int search(final long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return LongArrSeq.uncheckedsearch(arr,size,(val));
    }
    return -1;
  }
  public int search(final float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final long v;
      if(TypeUtil.floatEquals(val,v=(long)val))
      {
        return LongArrSeq.uncheckedsearch(arr,size,v);
      }
    }
    return -1;
  }
  public int search(final double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final long v;
      if(val==(v=(long)val))
      {
        return LongArrSeq.uncheckedsearch(arr,size,v);
      }
    }
    return -1;
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
  public long[] toLongArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final long[] dst;
      uncheckedCopyInto(dst=new long[size],size);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override
  public Long[] toArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final Long[] dst;
      uncheckedCopyInto(dst=new Long[size],size);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_BOXED_ARR;
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
  @Override
  public float[] toFloatArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final float[] dst;
      uncheckedCopyInto(dst=new float[size],size);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
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
  @Override
  public String toString()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final StringBuilder builder;
      uncheckedToString(size,builder=new StringBuilder("["));
      return builder.append(']').toString();
    }
    return "[]";
  }
  abstract boolean uncheckedremoveVal(final int size,final long val);
  abstract void uncheckedForEach(final int size,final LongConsumer action);
  abstract int uncheckedHashCode(final int size);
  abstract boolean uncheckedRemoveIf(final int size,final LongPredicate filter);
  abstract void uncheckedToString(final int size,final StringBuilder builder);
  private int finalizeSubListBatchRemove(final long[] arr,final int newBound,final int oldBound)
  {
    final int newRootSize,numRemoved;
    size=newRootSize=size-(numRemoved=oldBound-newBound);
    ArrCopy.semicheckedCopy(arr,oldBound,arr,newBound,newRootSize-newBound);
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
    size=1;
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
  public static abstract class Checked extends LongArrSeq
  {
    private class ModCountChecker extends CheckedCollection.AbstractModCountChecker
    {
      public ModCountChecker(int expectedModCount)
      {
        super(expectedModCount);
      }
      @Override protected int getActualModCount()
      {
        return modCount;
      }
    }
    transient int modCount;
    private Checked()
    {
      super();
    }
    private Checked(final int capacity)
    {
      super(capacity);
    }
    private Checked(final int size,final long[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean add(final long val)
    {
      ++modCount;
      super.push(val);
      return true;
    }  
    @Override
    public void add(final int index,final long val)
    {
      CheckedCollection.checkLo(index);
      final int size;
      CheckedCollection.checkWriteHi(index,size=this.size);
      ++modCount;
      if(size!=0)
      {
        super.uncheckedInsert(index,val,size);
      }
      else
      {
        super.uncheckedInit(val);
      }
    }
    @Override
    public void clear()
    {
      if(size!=0)
      {
        ++modCount;
        this.size=0;
      }
    }
    @Override
    public long getLong(final int index)
    {
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      return (long)arr[index];
    }
    @Override
    public long popLong()
    {
      final int size;
      if((size=this.size)!=0)
      {
        ++modCount;
        return (long)super.uncheckedPop(size-1);
      }
      throw new NoSuchElementException();
    }
    @Override
    public void push(final long val)
    {
      ++modCount;
      super.push(val);
    }
    @Override
    public long removeLongAt(final int index)
    {
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ++modCount;
      final long[] arr;
      final var removed=(long)(arr=this.arr)[index];
      eraseIndexHelper(arr,index,--size);
      this.size=size;
      return removed;
    }
    @Override
    public long set(final int index,final long val)
    {
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      return super.set(index,val);
    }
    @Override
    public <T> T[] toArray(final IntFunction<T[]> arrConstructor)
    {
      return super.toArray(size->
      {
        int modCount=this.modCount;
        try
        {
          return arrConstructor.apply(size);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      });
    }
    @Override
    boolean uncheckedRemoveIf(int size,final LongPredicate filter)
    {
      int srcOffset;
      final int modCount=this.modCount;
      try
      {
        srcOffset=0;
        final var arr=this.arr;
        for(;;)
        {
          if(filter.test((long)arr[srcOffset]))
          {
            break;
          }
          if(++srcOffset==size)
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
            return false;
          }
        }
        int dstOffset=markSurvivorsAndPullDown(new ModCountChecker(modCount),arr,srcOffset,size,filter);
        this.modCount=modCount+1;
        this.size=dstOffset;
      }
      catch(final RuntimeException e)
      {
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
      return true;
    }
  }
  public static class CheckedList extends Checked implements OmniList.OfLong
  {
    public CheckedList()
    {
      super();
    }
    public CheckedList(final int capacity)
    {
      super(capacity);
    }
    public CheckedList(final int size,final long[] arr)
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
      return new CheckedList(size,arr);
    }
    @Override
    public boolean equals(final Object val)
    {
      //TODO implements equals method
      return false;
    }
    @Override
    public OmniIterator.OfLong iterator()
    {
      return new BidirectionalItr(this);
    }
    @Override
    public OmniListIterator.OfLong listIterator()
    {
      return new BidirectionalItr(this);
    }
    @Override
    public OmniListIterator.OfLong listIterator(final int index)
    {
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,this.size);
      return new BidirectionalItr(this,index);
    }
    @Override
    public void put(final int index,final long val)
    {
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      arr[index]=val;
    }
    @Override
    public void replaceAll(final LongUnaryOperator operator)
    {
      final int size;
      if(0!=((size=this.size)))
      {
        int modCount=this.modCount;
        try
        {
          LongArrSeq.uncheckedreplaceAll(arr,0,size,operator);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        this.modCount=modCount+1;
      }
    }
    @Override
    public void sort(final LongComparator sorter)
    {
      final int size;
      if(1<((size=this.size)))
      {
        int modCount=this.modCount;
        try
        {
          LongSortUtil.uncheckedcomparatorSort(arr,0,size-1,sorter);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        this.modCount=modCount+1;
      }
    }
    @Override
    public void replaceAll(final UnaryOperator<Long> operator)
    {
      final int size;
      if(0!=((size=this.size)))
      {
        int modCount=this.modCount;
        try
        {
          LongArrSeq.uncheckedreplaceAll(arr,0,size,operator::apply);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        this.modCount=modCount+1;
      }
    }
    @Override
    public void sort(final Comparator<? super Long> sorter)
    {
      final int size;
      if(1<((size=this.size)))
      {
        int modCount=this.modCount;
        try
        {
          LongSortUtil.uncheckedcomparatorSort(arr,0,size-1,sorter::compare);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        this.modCount=modCount+1;
      }
    }
    public void reverseSort()
    {
      final int size;
      if((size=this.size)>1)
      {
         LongSortUtil.uncheckedreverseSort(arr,0,size-1);
         ++this.modCount;
      }
    }
    public void sort()
    {
      final int size;
      if((size=this.size)>1)
      {
         LongSortUtil.uncheckedsort(arr,0,size-1);
         ++this.modCount;
      }
    }
    @Override
    public OmniList.OfLong subList(final int fromIndex,final int toIndex)
    {
      CheckedCollection.checkSubListRange(fromIndex,toIndex,this.size);
      return new SubList(this,fromIndex,toIndex-fromIndex);
    }
    @Override
    void uncheckedCopyInto(final long[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Object[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Long[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final double[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final float[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedForEach(final int size,final LongConsumer action)
    {
      int modCount=this.modCount;
      try
      {
        uncheckedForwardForEach(arr,0,size,action);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override
    int uncheckedHashCode(final int size)
    {
      return forwardHashCode(arr,0,size);
    }
    @Override
    boolean uncheckedremoveVal(int size,final long val)
    {
      final var arr=this.arr;int index;
      for(index=0,--size;val!=(arr[index]);++index)
      {
        if(index==size)
        {
          return false;
        }
      }
      ++this.modCount;
      eraseIndexHelper(arr,index,size);
      this.size=size;
      return true;
    }
    @Override
    void uncheckedToString(final int size,final StringBuilder builder)
    {
      forwardToString(arr,0,size,builder);
    }
    private static class BidirectionalItr implements OmniListIterator.OfLong
    {
      private transient int lastRet=-1;
      private transient final Checked root;
      private transient int cursor;
      private transient int modCount;
      private BidirectionalItr(final Checked root)
      {
        this.root=root;
        this.modCount=root.modCount;
      }
      private BidirectionalItr(final Checked root,final int cursor)
      {
        this.root=root;
        this.modCount=root.modCount;
        this.cursor=cursor;
      }
      @Override
      public void add(final long val)
      {
        final Checked root;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        final int rootSize,cursor=this.cursor;
        if((rootSize=root.size)!=0)
        {
          ((LongArrSeq)root).uncheckedInsert(cursor,val,rootSize);
        }
        else
        {
          ((LongArrSeq)root).uncheckedInit(val);
        }
        root.modCount=++modCount;
        this.modCount=modCount;
        this.cursor=cursor+1;
        this.lastRet=-1;
      }
      @Override
      public void forEachRemaining(final LongConsumer action)
      {
        final int cursor,bound;
        final Checked root;
        if((cursor=this.cursor)!=(bound=(root=this.root).size))
        {
          int modCount=this.modCount;
          try
          {
            uncheckedForwardForEach(root.arr,cursor,bound,action);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      @Override
      public void forEachRemaining(final Consumer<? super Long> action)
      {
        final int cursor,bound;
        final Checked root;
        if((cursor=this.cursor)!=(bound=(root=this.root).size))
        {
          int modCount=this.modCount;
          try
          {
            uncheckedForwardForEach(root.arr,cursor,bound,action::accept);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      @Override
      public boolean hasNext()
      {
        return cursor!=root.size;
      }
      @Override
      public boolean hasPrevious()
      {
        return cursor!=0;
      }
      @Override
      public long nextLong()
      {
        final Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        final int cursor;
        if((cursor=this.cursor)!=root.size)
        {
          lastRet=cursor;
          this.cursor=cursor+1;
          return (long)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override
      public int nextIndex()
      {
        return cursor;
      }
      @Override
      public long previousLong()
      {
        final Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        int cursor;
        if((cursor=this.cursor)!=0)
        {
          lastRet=--cursor;
          this.cursor=cursor;
          return (long)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override
      public int previousIndex()
      {
        return cursor-1;
      }
      @Override
      public void remove()
      {
        final int lastRet;
        if((lastRet=this.lastRet)!=-1)
        {
          final Checked root;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
          eraseIndexHelper(root.arr,lastRet,--root.size);
          root.modCount=++modCount;
          this.modCount=modCount;
          cursor=lastRet;
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override
      public void set(final long val)
      {
        final int lastRet;
        if((lastRet=this.lastRet)!=-1)
        {
          final Checked root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.arr[lastRet]=val;
          return;
        }
        throw new IllegalStateException();
      }
    }
    private static class SubList extends AbstractLongList implements OmniList.OfLong
    {
      transient final Checked root;
      transient final SubList parent;
      transient final int rootOffset;
      transient int modCount;
      private static  void bubbleUpDecrementSize(SubList parent)
      {
        while(parent!=null)
        {
          --parent.size;++parent.modCount;
          parent=parent.parent;
        }
      }
      private static  void bubbleUpDecrementSize(SubList parent,final int numToRemove)
      {
        while(parent!=null)
        {
          parent.size-=numToRemove;++parent.modCount;
          parent=parent.parent;
        }
      }
      private static  void bubbleUpIncrementModCount(SubList parent)
      {
        while(parent!=null)
        {
          ++parent.modCount;
          parent=parent.parent;
        }
      }
      private static  void bubbleUpIncrementSize(SubList parent)
      {
        while(parent!=null)
        {
          ++parent.size;++parent.modCount;
          parent=parent.parent;
        }
      }
      private SubList(final Checked root,final int rootOffset,final int size)
      {
        super(size);
        this.root=root;
        this.parent=null;
        this.rootOffset=rootOffset;
        this.modCount=root.modCount;
      }
      private SubList(final Checked root,final SubList parent,final int rootOffset,final int size)
      {
        super(size);
        this.root=root;
        this.parent=parent;
        this.rootOffset=rootOffset;
        this.modCount=root.modCount;
      }
      @Override
      public boolean add(final long val)
      {
        final Checked root;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        final int rootSize;
        final int size=this.size;
        if((rootSize=root.size)!=0)
        {
          ((LongArrSeq)root).uncheckedInsert(rootOffset+size,val,rootSize);
        }
        else
        {
          ((LongArrSeq)root).uncheckedInit(val);
        }
        root.modCount=++modCount;
        this.modCount=modCount;
        bubbleUpIncrementSize(parent);
        this.size=size+1;
        return true;
      }
      @Override
      public void add(final int index,final long val)
      {
        final Checked root;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        CheckedCollection.checkLo(index);
        final int size;
        CheckedCollection.checkWriteHi(index,size=this.size);
        final int rootSize;
        if((rootSize=root.size)!=0)
        {
          ((LongArrSeq)root).uncheckedInsert(rootOffset+index,val,rootSize);
        }
        else
        {
          ((LongArrSeq)root).uncheckedInit(val);
        }
        root.modCount=++modCount;
        this.modCount=modCount;
        bubbleUpIncrementSize(parent);
        this.size=size+1;
      }
      @Override
      public void clear()
      {
        final var root=this.root;
        final int modCount=this.modCount;
        final int size;
        if((size=this.size)!=0)
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
          this.modCount=modCount+1;
          this.size=0;
          bubbleUpDecrementSize(parent,size);
          final int newRootSize;
          root.size=newRootSize=root.size-size;
          final long[] arr;
          final int rootOffset;
          ArrCopy.semicheckedCopy(arr=root.arr,(rootOffset=this.rootOffset)+size,arr,rootOffset,newRootSize-rootOffset);
        }
        else
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      @Override
      public Object clone()
      {
        final var root=checkModCountAndGetRoot();
        final long[] arr;
        final int size;
        if((size=this.size)!=0)
        {
          ArrCopy.uncheckedCopy(root.arr,rootOffset,arr=new long[size],0,size);
        }
        else
        {
          arr=null;
        }
        return new CheckedList(size,arr);
      }
      @Override
      public boolean contains(final Object val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val instanceof Long)
          {
            return uncheckedcontains(size,(long)(val));
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public int indexOf(final Object val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val instanceof Long)
          {
            return uncheckedindexOf(size,(long)(val));
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int lastIndexOf(final Object val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val instanceof Long)
          {
            return uncheckedlastIndexOf(size,(long)(val));
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public boolean contains(final boolean val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedcontains(size,TypeUtil.castToLong(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean contains(final int val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          {
            return this.uncheckedcontains(size,(val));
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean contains(final long val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedcontains(size,(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean contains(final float val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final long v;
          if(TypeUtil.floatEquals(val,v=(long)val))
          {
            return this.uncheckedcontains(size,v);
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean contains(final double val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final long v;
          if(val==(v=(long)val))
          {
            return this.uncheckedcontains(size,v);
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean equals(final Object val)
      {
        //TODO implements equals method
        return false;
      }
      @Override
      public void forEach(final LongConsumer action)
      {
        final var root=this.root;
        int modCount=this.modCount;
        try
        {
          final int size;if((size=this.size)!=0){final int rootOffset;uncheckedForwardForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size,action);}
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      @Override
      public void forEach(final Consumer<? super Long> action)
      {
        final var root=this.root;
        int modCount=this.modCount;
        try
        {
          final int size;if((size=this.size)!=0){final int rootOffset;uncheckedForwardForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size,action::accept);}
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      @Override
      public long getLong(final int index)
      {
        final var root=checkModCountAndGetRoot();
        CheckedCollection.checkLo(index);
        CheckedCollection.checkReadHi(index,this.size);
        return (long)root.arr[index+rootOffset];
      }    
      @Override
      public int hashCode()
      {
        final var root=checkModCountAndGetRoot();
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int rootOffset;
            return forwardHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size);
          }
          return 1;
        }
      }
      @Override
      public int indexOf(final boolean val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedindexOf(size,TypeUtil.castToLong(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int indexOf(final int val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          {
            return this.uncheckedindexOf(size,(val));
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int indexOf(final long val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedindexOf(size,(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int indexOf(final float val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final long v;
          if(TypeUtil.floatEquals(val,v=(long)val))
          {
            return this.uncheckedindexOf(size,v);
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int indexOf(final double val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final long v;
          if(val==(v=(long)val))
          {
            return this.uncheckedindexOf(size,v);
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int lastIndexOf(final boolean val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedlastIndexOf(size,TypeUtil.castToLong(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int lastIndexOf(final int val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          {
            return this.uncheckedlastIndexOf(size,(val));
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int lastIndexOf(final long val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedlastIndexOf(size,(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int lastIndexOf(final float val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final long v;
          if(TypeUtil.floatEquals(val,v=(long)val))
          {
            return this.uncheckedlastIndexOf(size,v);
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int lastIndexOf(final double val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final long v;
          if(val==(v=(long)val))
          {
            return this.uncheckedlastIndexOf(size,v);
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public boolean isEmpty()
      {
        CheckedCollection.checkModCount(this.modCount,root.modCount);
        return size==0;
      }
      @Override
      public OmniIterator.OfLong iterator()
      {
        final int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
        return new BidirectionalItr(this,modCount);
      }
      @Override
      public OmniListIterator.OfLong listIterator()
      {
        final int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
        return new BidirectionalItr(this,modCount);
      }
      @Override
      public OmniListIterator.OfLong listIterator(final int index)
      {
        final int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
        CheckedCollection.checkLo(index);
        CheckedCollection.checkWriteHi(index,size);
        return new BidirectionalItr(this,modCount,index+rootOffset);
      }
      @Override
      public void put(final int index,final long val)
      {
        final var root=checkModCountAndGetRoot();
        CheckedCollection.checkLo(index);
        CheckedCollection.checkReadHi(index,this.size);
        root.arr[index+rootOffset]=val;
      }
      @Override
      public boolean remove(final Object val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val instanceof Long)
          {
            return uncheckedremoveVal(size,(long)(val));
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final boolean val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,TypeUtil.castToLong(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final int val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          {
            return this.uncheckedremoveVal(size,(val));
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final long val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final float val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final long v;
          if(TypeUtil.floatEquals(val,v=(long)val))
          {
            return this.uncheckedremoveVal(size,v);
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final double val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final long v;
          if(val==(v=(long)val))
          {
            return this.uncheckedremoveVal(size,v);
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public long removeLongAt(int index)
      {
        int modCount;
        final Checked root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        CheckedCollection.checkLo(index);
        final int size;
        CheckedCollection.checkReadHi(index,size=this.size);
        final long[] arr;
        final var removed=(long)(arr=root.arr)[index+=rootOffset];
        eraseIndexHelper(arr,index,--root.size);
        root.modCount=++modCount;
        bubbleUpDecrementSize(parent);
        this.modCount=modCount;
        this.size=size-1;
        return removed;
      }
      @Override
      public boolean removeIf(final LongPredicate filter)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return uncheckedRemoveIf(size,filter);
        }
        CheckedCollection.checkModCount(this.modCount,root.modCount);
        return false;
      }
      @Override
      public void replaceAll(final LongUnaryOperator operator)
      {
        final var root=this.root;
        final int size;
        if(0!=(size=this.size))
        {
          int modCount=this.modCount;
          try
          {
            final int rootOffset;uncheckedreplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
              root.modCount=++modCount;
              bubbleUpIncrementModCount(parent);
              this.modCount=modCount;
        }
        else
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      @Override
      public void sort(final LongComparator sorter)
      {
        final var root=this.root;
        final int size;
        if(1<(size=this.size))
        {
          int modCount=this.modCount;
          try
          {
            final int rootOffset;LongSortUtil.uncheckedcomparatorSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
              root.modCount=++modCount;
              bubbleUpIncrementModCount(parent);
              this.modCount=modCount;
        }
        else
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      @Override
      public void reverseSort()
      {
        int modCount;
        final Checked root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        final int size;
        if((size=this.size)>1)
        {
          final int rootOffset;
          LongSortUtil.uncheckedreverseSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
              root.modCount=++modCount;
              bubbleUpIncrementModCount(parent);
              this.modCount=modCount;
        }
      }
      @Override
      public void sort()
      {
        int modCount;
        final Checked root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        final int size;
        if((size=this.size)>1)
        {
          final int rootOffset;
          LongSortUtil.uncheckedsort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
              root.modCount=++modCount;
              bubbleUpIncrementModCount(parent);
              this.modCount=modCount;
        }
      }
      @Override
      public boolean removeIf(final Predicate<? super Long> filter)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return uncheckedRemoveIf(size,filter::test);
        }
        CheckedCollection.checkModCount(this.modCount,root.modCount);
        return false;
      }
      @Override
      public void replaceAll(final UnaryOperator<Long> operator)
      {
        final var root=this.root;
        final int size;
        if(0!=(size=this.size))
        {
          int modCount=this.modCount;
          try
          {
            final int rootOffset;uncheckedreplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator::apply);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
              root.modCount=++modCount;
              bubbleUpIncrementModCount(parent);
              this.modCount=modCount;
        }
        else
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      @Override
      public void sort(final Comparator<? super Long> sorter)
      {
        final var root=this.root;
        final int size;
        if(1<(size=this.size))
        {
          int modCount=this.modCount;
          try
          {
            final int rootOffset;LongSortUtil.uncheckedcomparatorSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter::compare);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
              root.modCount=++modCount;
              bubbleUpIncrementModCount(parent);
              this.modCount=modCount;
        }
        else
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      @Override
      public long set(int index,final long val)
      {
        final var root=checkModCountAndGetRoot();
        CheckedCollection.checkLo(index);
        CheckedCollection.checkReadHi(index,this.size);
        final long[] arr;
        final var oldVal=(long)(arr=root.arr)[index+=rootOffset];
        arr[index]=val;
        return oldVal;
      }
      @Override
      public int size()
      {
        CheckedCollection.checkModCount(this.modCount,root.modCount);
        return size;
      }
      @Override
      public OmniList.OfLong subList(final int fromIndex,final int toIndex)
      {
        final var root=checkModCountAndGetRoot();
        CheckedCollection.checkSubListRange(fromIndex,toIndex,this.size);
        return new SubList(root,this,this.rootOffset+fromIndex,toIndex-fromIndex);
      }
      @Override
      public long[] toLongArray()
      {
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0)
        {
          final long[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new long[size],0,size);
          return dst;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
      }
      @Override
      public Long[] toArray()
      {
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0)
        {
          final Long[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new Long[size],0,size);
          return dst;
        }
        return OmniArray.OfLong.DEFAULT_BOXED_ARR;
      }
      @Override
      public double[] toDoubleArray()
      {
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0)
        {
          final double[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new double[size],0,size);
          return dst;
        }
        return OmniArray.OfDouble.DEFAULT_ARR;
      }
      @Override
      public float[] toFloatArray()
      {
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0)
        {
          final float[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new float[size],0,size);
          return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
      }
      @Override
      public <T> T[] toArray(final IntFunction<T[]> arrConstructor)
      {
        final var root=this.root;
        final int size;
        final T[] dst;
        int modCount=this.modCount;
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
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst,0,size);
        }
        return dst;
      }
      @Override
      public <T> T[] toArray(T[] arr)
      {
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0)
        {
          ArrCopy.uncheckedCopy(root.arr,rootOffset,arr=OmniArray.uncheckedArrResize(size,arr),0,size);
        }
        else if(arr.length!=0)
        {
          arr[0]=null;
        }
        return arr;
      }
      @Override
      public String toString()
      {
        final var root=checkModCountAndGetRoot();
        {
          final int size;
          if((size=this.size)!=0)
          {
            final StringBuilder builder;
            final int rootOffset;
            forwardToString(root.arr,rootOffset=this.rootOffset,rootOffset+size,builder=new StringBuilder("["));
            return builder.append(']').toString();
          }
          return "[]";
        }
      }
      private boolean uncheckedcontains(final int size,final long val)
      { 
        final int rootOffset;
        return LongArrSeq.uncheckedcontains(checkModCountAndGetRoot().arr,rootOffset=this.rootOffset,rootOffset+size,val);
      }
      private int uncheckedindexOf(final int size,final long val)
      { 
        final int rootOffset;
        return LongArrSeq.uncheckedindexOf(checkModCountAndGetRoot().arr,rootOffset=this.rootOffset,rootOffset+size,val);
      }
      private int uncheckedlastIndexOf(final int size,final long val)
      { 
        final int rootOffset;
        return LongArrSeq.uncheckedlastIndexOf(checkModCountAndGetRoot().arr,rootOffset=this.rootOffset,rootOffset+size,val);
      }
      private boolean uncheckedremoveVal(int size,final long val)
      {
        int modCount;
        final Checked root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        final var arr=root.arr;int index;final int bound=(index=this.rootOffset)+(--size);
        for(;val!=(arr[index]);)
        {
          if(++index==bound)
          {
            return false;
          }
        }
        root.modCount=++modCount;
        bubbleUpDecrementSize(parent);
        this.modCount=modCount;
        eraseIndexHelper(arr,index,--root.size);
        this.size=size;
        return true;
      }
      private Checked checkModCountAndGetRoot()
      {
        final Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        return root;
      }
      private boolean uncheckedRemoveIf(int size,final LongPredicate filter)
      {
        final Checked root=this.root;
        int modCount=this.modCount;
        int srcOffset;
        final int srcBound=(srcOffset=rootOffset)+size;
        int dstOffset;
        try
        {
          final var arr=root.arr;
          for(;;)
          {
            if(filter.test((long)arr[srcOffset]))
            {
              break;
            }
            if(++srcOffset==srcBound)
            {
              CheckedCollection.checkModCount(modCount,root.modCount);
              return false;
            }
          }
          dstOffset=markSurvivorsAndPullDown(root.new ModCountChecker(modCount),arr,srcOffset,srcBound,filter);
          root.modCount=++modCount;
          this.modCount=modCount;
          this.size=size-(size=((LongArrSeq)root).finalizeSubListBatchRemove(arr,dstOffset,srcBound));
          bubbleUpDecrementSize(parent,size);
        }
        catch(final RuntimeException e)
        {
          throw CheckedCollection.checkModCount(modCount,root.modCount,e);
        }
        return true;
      }
      private static class BidirectionalItr implements OmniListIterator.OfLong
      {
        private transient final SubList parent;
        private transient int cursor;
        private transient int lastRet=-1;
        private transient int modCount;
        private BidirectionalItr(final SubList parent,final int modCount)
        {
          this.parent=parent;
          this.cursor=parent.rootOffset;
          this.modCount=modCount;
        }
        private BidirectionalItr(final SubList parent,final int modCount,final int cursor)
        {
          this.parent=parent;
          this.cursor=cursor;
          this.modCount=modCount;
        }
        @Override
        public void add(final long val)
        {
          final SubList parent;
          final Checked root;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
          final int rootSize;
          final int cursor=this.cursor;
          if((rootSize=root.size)!=0)
          {
            ((LongArrSeq)root).uncheckedInsert(cursor,val,rootSize);
          }
          else
          {
            ((LongArrSeq)root).uncheckedInit(val);
          }
          bubbleUpIncrementSize(parent.parent);
          parent.modCount=++modCount;
          root.modCount=modCount;
          ++parent.size;
          this.cursor=cursor+1;
          this.lastRet=-1;
        }
        @Override
        public void forEachRemaining(final LongConsumer action)
        {
          final int cursor,bound;
          final SubList parent;
          if((cursor=this.cursor)!=(bound=(parent=this.parent).rootOffset+parent.size))
          {
            final var root=parent.root;
            int modCount=this.modCount;
            try
            {
              uncheckedForwardForEach(root.arr,cursor,bound,action);
            }
            finally
            {
              CheckedCollection.checkModCount(modCount,root.modCount);
            }
            this.cursor=bound;
            lastRet=bound-1;
          }
        }
        @Override
        public void forEachRemaining(final Consumer<? super Long> action)
        {
          final int cursor,bound;
          final SubList parent;
          if((cursor=this.cursor)!=(bound=(parent=this.parent).rootOffset+parent.size))
          {
            final var root=parent.root;
            int modCount=this.modCount;
            try
            {
              uncheckedForwardForEach(root.arr,cursor,bound,action::accept);
            }
            finally
            {
              CheckedCollection.checkModCount(modCount,root.modCount);
            }
            this.cursor=bound;
            lastRet=bound-1;
          }
        }
        @Override
        public boolean hasNext()
        {
          final SubList parent;
          return cursor!=(parent=this.parent).rootOffset+parent.size;
        }
        @Override
        public boolean hasPrevious()
        {
          return cursor!=parent.rootOffset;
        }
        @Override
        public long nextLong()
        {
          final Checked root;
          final SubList parent;
          CheckedCollection.checkModCount(modCount,(root=(parent=this.parent).root).modCount);
          final int cursor;
          if((cursor=this.cursor)!=parent.rootOffset+parent.size)
          {
            lastRet=cursor;
            this.cursor=cursor+1;
            return (long)root.arr[cursor];
          }
          throw new NoSuchElementException();
        }
        @Override
        public int nextIndex()
        {
          return cursor-parent.rootOffset;
        }
        @Override
        public long previousLong()
        {
          final Checked root;
          final SubList  parent;
          CheckedCollection.checkModCount(modCount,(root=(parent=this.parent).root).modCount);
          int cursor;
          if((cursor=this.cursor)!=parent.rootOffset)
          {
            lastRet=--cursor;
            this.cursor=cursor;
            return (long)root.arr[cursor];
          }
          throw new NoSuchElementException();
        }
        @Override
        public int previousIndex()
        {
          return cursor-parent.rootOffset-1;
        }
        @Override
        public void remove()
        {
          final int lastRet;
          if((lastRet=this.lastRet)!=-1)
          {
            final Checked root;
            final SubList  parent;
            int modCount;
            CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
            eraseIndexHelper(root.arr,lastRet,--root.size);
            bubbleUpDecrementSize(parent.parent);
            --parent.size;
            root.modCount=++modCount;
            parent.modCount=modCount;
            this.modCount=modCount;
            cursor=lastRet;
            this.lastRet=-1;
            return;
          }
          throw new IllegalStateException();
        }
        @Override
        public void set(final long val)
        {
          final int lastRet;
          if((lastRet=this.lastRet)!=-1)
          {
            final Checked root;
            CheckedCollection.checkModCount(modCount,(root=parent.root).modCount);
            root.arr[lastRet]=val;
            return;
          }
          throw new IllegalStateException();
        }
      }
    }
  }
  public static class CheckedStack extends Checked implements OmniStack.OfLong
  {
    public CheckedStack()
    {
      super();
    }
    public CheckedStack(final int capacity)
    {
      super(capacity);
    }
    public CheckedStack(final int size,final long[] arr)
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
      return new CheckedStack(size,arr);
    }
    @Override
    public boolean equals(final Object val)
    {
      //TODO implements equals method
      return false;
    }
    @Override
    public OmniIterator.OfLong iterator()
    {
      return new CheckedDescendingItr(this);
    }
        @Override
        public long pollLong()
        {
          final int size;
          if((size=this.size)!=0)
          {
            ++modCount;
            return (long)(((LongArrSeq)this).uncheckedPop(size-1));
          }
          return Long.MIN_VALUE;
        }
        @Override
        public Long poll()
        {
          final int size;
          if((size=this.size)!=0)
          {
            ++modCount;
            return (Long)(((LongArrSeq)this).uncheckedPop(size-1));
          }
          return null;
        }
        @Override
        public double pollDouble()
        {
          final int size;
          if((size=this.size)!=0)
          {
            ++modCount;
            return (double)(((LongArrSeq)this).uncheckedPop(size-1));
          }
          return Double.NaN;
        }
        @Override
        public float pollFloat()
        {
          final int size;
          if((size=this.size)!=0)
          {
            ++modCount;
            return (float)(((LongArrSeq)this).uncheckedPop(size-1));
          }
          return Float.NaN;
        }
    @Override
    void uncheckedCopyInto(final long[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Object[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Long[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final double[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final float[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedForEach(final int size,final LongConsumer action)
    {
      int modCount=this.modCount;
      try
      {
        uncheckedReverseForEach(arr,0,size,action);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override
    int uncheckedHashCode(final int size)
    {
      return reverseHashCode(arr,0,size);
    }
    @Override
    boolean uncheckedremoveVal(int size,final long val)
    {
      final var arr=this.arr;int index;
      for(index=--size;val!=(arr[index]);--index)
      {
        if(index==0)
        {
          return false;
        }
      }
      ++this.modCount;
      eraseIndexHelper(arr,index,size);
      this.size=size;
      return true;
    }
    @Override
    void uncheckedToString(final int size,final StringBuilder builder)
    {
      reverseToString(arr,0,size,builder);
    }
    private static class CheckedDescendingItr extends AbstractLongItr implements OmniIterator.OfLong
    {
      private transient final CheckedStack root;
      private transient int cursor;
      private transient int lastRet=-1;
      private transient int modCount;
      private CheckedDescendingItr(CheckedStack root)
      {
        this.root=root;
        cursor=root.size;
        modCount=root.modCount;
      }
      @Override
      public void forEachRemaining(LongConsumer action)
      {
        final int cursor;
        if((cursor=this.cursor)!=0)
        {
          uncheckedForEachRemaining(cursor,action);
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Long> action)
      {
        final int cursor;
        if((cursor=this.cursor)!=0)
        {
          uncheckedForEachRemaining(cursor,action::accept);
        }
      }
      @Override
      public boolean hasNext()
      {
        return cursor!=0;
      }
      @Override
      public long nextLong()
      {
        final CheckedStack root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        int cursor;
        if((cursor=this.cursor)!=0)
        {
          lastRet=--cursor;
          this.cursor=cursor;
          return (long)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override
      public void remove()
      {
        final int lastRet;
        if((lastRet=this.lastRet)!=-1)
        {
          final CheckedStack root;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
          eraseIndexHelper(root.arr,lastRet,--root.size);
          root.modCount=++modCount;
          this.modCount=modCount;
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      private void uncheckedForEachRemaining(int cursor,LongConsumer action)
      {
        final var root=this.root;
        int modCount=this.modCount;
        try
        {
          uncheckedReverseForEach(root.arr,0,cursor,action);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
        this.cursor=0;
        lastRet=0;
      }
    }
  }
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
    @Override
    boolean uncheckedRemoveIf(int size,final LongPredicate filter)
    {
      int srcOffset;
      srcOffset=0;
      while(!filter.test((long)arr[srcOffset]))
      {
        if(++srcOffset==size)
        {
          return false;
        }
      }
      this.size=pullSurvivorsDown(arr,srcOffset,size-1,filter);
      return true;
    }
  }
  public static class UncheckedList extends Unchecked implements OmniList.OfLong
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
    @Override
    public OmniIterator.OfLong iterator()
    {
      return new AscendingItr(this);
    }
    @Override
    public OmniListIterator.OfLong listIterator()
    {
      return new BidirectionalItr(this);
    }
    @Override
    public OmniListIterator.OfLong listIterator(final int index)
    {
      return new BidirectionalItr(this,index);
    }
    @Override
    public void put(final int index,final long val)
    {
      arr[index]=val;
    }
    @Override
    public void replaceAll(final LongUnaryOperator operator)
    {
      final int size;
      if(0!=((size=this.size)))
      {
        LongArrSeq.uncheckedreplaceAll(arr,0,size,operator);
      }
    }
    @Override
    public void reverseSort()
    {
      final int size;
      if(1<((size=this.size)))
      {
        LongSortUtil.uncheckedreverseSort(arr,0,size-1);
      }
    }
    @Override
    public void sort()
    {
      final int size;
      if(1<((size=this.size)))
      {
        LongSortUtil.uncheckedsort(arr,0,size-1);
      }
    }
    @Override
    public void sort(final LongComparator sorter)
    {
      final int size;
      if(1<((size=this.size)))
      {
        LongSortUtil.uncheckedcomparatorSort(arr,0,size-1,sorter);
      }
    }
    @Override
    public void replaceAll(final UnaryOperator<Long> operator)
    {
      final int size;
      if(0!=((size=this.size)))
      {
        LongArrSeq.uncheckedreplaceAll(arr,0,size,operator::apply);
      }
    }
    @Override
    public void sort(final Comparator<? super Long> sorter)
    {
      final int size;
      if(1<((size=this.size)))
      {
        LongSortUtil.uncheckedcomparatorSort(arr,0,size-1,sorter::compare);
      }
    }
    @Override
    public OmniList.OfLong subList(final int fromIndex,final int toIndex)
    {
      return new SubList(this,fromIndex,toIndex-fromIndex);
    }
    @Override
    void uncheckedCopyInto(final long[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Object[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Long[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final double[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final float[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedForEach(final int size,final LongConsumer action)
    {
      uncheckedForwardForEach(arr,0,size,action);
    }
    @Override
    int uncheckedHashCode(final int size)
    {
      return forwardHashCode(arr,0,size);
    }
    @Override
    boolean uncheckedremoveVal(int size,final long val)
    {
      final var arr=this.arr;int index;
      for(index=--size;val!=(arr[index]);--index)
      {
        if(index==0)
        {
          return false;
        }
      }
      eraseIndexHelper(arr,index,size);
      this.size=size;
      return true;
    }
    @Override
    void uncheckedToString(final int size,final StringBuilder builder)
    {
      forwardToString(arr,0,size,builder);
    }
    private static class AscendingItr extends AbstractLongItr implements OmniIterator.OfLong
    {
      private transient final UncheckedList root;
      private transient int cursor;
      private AscendingItr(UncheckedList root)
      {
        this.root=root;
      }
      @Override
      public void forEachRemaining(LongConsumer action)
      {
        final UncheckedList root;
        final int cursor,bound;
        if((cursor=this.cursor)!=(bound=(root=this.root).size))
        {
          uncheckedForwardForEach(root.arr,cursor,bound,action);
          this.cursor=bound;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Long> action)
      {
        final UncheckedList root;
        final int cursor,bound;
        if((cursor=this.cursor)!=(bound=(root=this.root).size))
        {
          uncheckedForwardForEach(root.arr,cursor,bound,action::accept);
          this.cursor=bound;
        }
      }
      @Override
      public boolean hasNext()
      {
        return cursor!=root.size;
      }
      @Override
      public long nextLong()
      {
        return (long)root.arr[cursor++];
      }
      @Override
      public void remove()
      {
        final UncheckedList root;
        eraseIndexHelper((root=this.root).arr,--cursor,--root.size);
      }
    }
    private static class BidirectionalItr implements OmniListIterator.OfLong
    {
      private transient final UncheckedList root;
      private transient int cursor;
      private transient int lastRet;
      private BidirectionalItr(final UncheckedList root)
      {
        this.root=root;
      }
      private BidirectionalItr(final UncheckedList root,final int cursor)
      {
        this.root=root;
        this.cursor=cursor;
      }
      @Override
      public void add(final long val)
      {
        final LongArrSeq root;
        final int rootSize,cursor=this.cursor;
        if((rootSize=(root=this.root).size)!=0)
        {
          root.uncheckedInsert(cursor,val,rootSize);
        }
        else
        {
          root.uncheckedInit(val);
        }
        this.cursor=cursor+1;
      }
      @Override
      public void forEachRemaining(LongConsumer action)
      {
        final int cursor,bound;
        final Unchecked root;
        if((cursor=this.cursor)!=(bound=(root=this.root).size))
        {
          uncheckedForwardForEach(root.arr,cursor,bound,action);
          this.cursor=bound;
          lastRet=bound-1;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Long> action)
      {
        final int cursor,bound;
        final Unchecked root;
        if((cursor=this.cursor)!=(bound=(root=this.root).size))
        {
          uncheckedForwardForEach(root.arr,cursor,bound,action::accept);
          this.cursor=bound;
          lastRet=bound-1;
        }
      }
      @Override
      public boolean hasNext()
      {
         return cursor!=root.size;
      }
      @Override
      public boolean hasPrevious()
      {
        return cursor!=0;
      }
      @Override
      public long nextLong()
      {
        final int lastRet;
        this.lastRet=lastRet=cursor++;
        return (long)root.arr[lastRet];
      }
      @Override
      public int nextIndex()
      {
        return cursor;
      }
      @Override
      public long previousLong()
      {
        final int lastRet;
        this.lastRet=lastRet=--cursor;
        return (long)root.arr[lastRet];
      }
      @Override
      public int previousIndex()
      {
        return cursor-1;
      }
      @Override
      public void remove()
      {
        final UncheckedList root;
        final int lastRet;
        eraseIndexHelper((root=this.root).arr,lastRet=this.lastRet,--root.size);
        cursor=lastRet;
      }
      @Override
      public void set(long val)
      {
        root.arr[lastRet]=val;
      }
    }
    private static class SubList extends AbstractLongList implements OmniList.OfLong
    {
      private static  void bubbleUpDecrementSize(SubList parent)
      {
        while(parent!=null)
        {
          --parent.size;
          parent=parent.parent;
        }
      }
      private static  void bubbleUpDecrementSize(SubList parent,final int numToRemove)
      {
        while(parent!=null)
        {
          parent.size-=numToRemove;
          parent=parent.parent;
        }
      }
      private static  void bubbleUpIncrementSize(SubList parent)
      {
        while(parent!=null)
        {
          ++parent.size;
          parent=parent.parent;
        }
      }
      transient final Unchecked root;
      transient final SubList parent;
      transient final int rootOffset;
      private SubList(final Unchecked root,final int rootOffset,final int size)
      {
        super(size);
        this.root=root;
        this.parent=null;
        this.rootOffset=rootOffset;
      }
      private SubList(final Unchecked root,final SubList parent,final int rootOffset,final int size)
      {
        super(size);
        this.root=root;
        this.parent=parent;
        this.rootOffset=rootOffset;
      }
      @Override
      public boolean add(final long val)
      {
        final LongArrSeq root;
        final int rootSize;
        final int size=this.size;
        if((rootSize=(root=this.root).size)!=0)
        {
          root.uncheckedInsert(size+rootOffset,val,rootSize);
        }
        else
        {
          root.uncheckedInit(val);
        }
        this.size=size+1;
        bubbleUpIncrementSize(parent);
        return true;
      }
      @Override
      public void add(final int index,final long val)
      {
        final LongArrSeq root;
        final int rootSize;
        if((rootSize=(root=this.root).size)!=0)
        {
          root.uncheckedInsert(index+rootOffset,val,rootSize);
        }
        else
        {
          root.uncheckedInit(val);
        }
        ++size;
        bubbleUpIncrementSize(parent);
      }
      @Override
      public void clear()
      {
        int size;
        if((size=this.size)!=0)
        {
          this.size=0;
          bubbleUpDecrementSize(parent,size);
          final int newRootSize;
          final Unchecked root;
          (root=this.root).size=newRootSize=root.size-size;
          final long[] arr;
          ArrCopy.semicheckedCopy(arr=root.arr,size+(size=rootOffset),arr,size,newRootSize-size);
        }
      }
      @Override
      public Object clone()
      {
        final long[] arr;
        final int size;
        if((size=this.size)!=0)
        {
          ArrCopy.uncheckedCopy(root.arr,rootOffset,arr=new long[size],0,size);
        }
        else
        {
          arr=null;
        }
        return new UncheckedList(size,arr);
      }
      @Override
      public boolean contains(Object val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val instanceof Long)
          {
            return uncheckedcontains(size,(long)(val));
          }
        }
        return false;
      }
      @Override
      public int indexOf(Object val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val instanceof Long)
          {
            return uncheckedindexOf(size,(long)(val));
          }
        }
        return -1;
      }
      @Override
      public int lastIndexOf(Object val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val instanceof Long)
          {
            return uncheckedlastIndexOf(size,(long)(val));
          }
        }
        return -1;
      }
      @Override
      public boolean contains(final boolean val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedcontains(size,TypeUtil.castToLong(val));
        }
        return false;
      }
      @Override
      public boolean contains(final int val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          {
            return this.uncheckedcontains(size,(val));
          }
        }
        return false;
      }
      @Override
      public boolean contains(final long val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedcontains(size,(val));
        }
        return false;
      }
      @Override
      public boolean contains(final float val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final long v;
          if(TypeUtil.floatEquals(val,v=(long)val))
          {
            return this.uncheckedcontains(size,v);
          }
        }
        return false;
      }
      @Override
      public boolean contains(final double val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final long v;
          if(val==(v=(long)val))
          {
            return this.uncheckedcontains(size,v);
          }
        }
        return false;
      }
      @Override
      public boolean equals(Object val)
      {
        //TODO implements equals method
        return false;
      }
      @Override
      public void forEach(LongConsumer action)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          uncheckedForwardForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size,action);
        }
      }
      @Override
      public void forEach(Consumer<? super Long> action)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          uncheckedForwardForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size,action::accept);
        }
      }
      @Override
      public long getLong(int index)
      {
        return (long)root.arr[index+rootOffset];
      }
      @Override
      public int hashCode()
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          return forwardHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }
        return 1;
      }
      @Override
      public int indexOf(final boolean val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedindexOf(size,TypeUtil.castToLong(val));
        }
        return -1;
      }
      @Override
      public int indexOf(final int val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          {
            return this.uncheckedindexOf(size,(val));
          }
        }
        return -1;
      }
      @Override
      public int indexOf(final long val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedindexOf(size,(val));
        }
        return -1;
      }
      @Override
      public int indexOf(final float val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final long v;
          if(TypeUtil.floatEquals(val,v=(long)val))
          {
            return this.uncheckedindexOf(size,v);
          }
        }
        return -1;
      }
      @Override
      public int indexOf(final double val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final long v;
          if(val==(v=(long)val))
          {
            return this.uncheckedindexOf(size,v);
          }
        }
        return -1;
      }
      @Override
      public int lastIndexOf(final boolean val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedlastIndexOf(size,TypeUtil.castToLong(val));
        }
        return -1;
      }
      @Override
      public int lastIndexOf(final int val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          {
            return this.uncheckedlastIndexOf(size,(val));
          }
        }
        return -1;
      }
      @Override
      public int lastIndexOf(final long val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedlastIndexOf(size,(val));
        }
        return -1;
      }
      @Override
      public int lastIndexOf(final float val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final long v;
          if(TypeUtil.floatEquals(val,v=(long)val))
          {
            return this.uncheckedlastIndexOf(size,v);
          }
        }
        return -1;
      }
      @Override
      public int lastIndexOf(final double val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final long v;
          if(val==(v=(long)val))
          {
            return this.uncheckedlastIndexOf(size,v);
          }
        }
        return -1;
      }
      @Override
      public OmniIterator.OfLong iterator()
      {
        return new AscendingItr(this);
      }
      @Override
      public OmniListIterator.OfLong listIterator()
      {
        return new BidirectionalItr(this);
      }
      @Override
      public OmniListIterator.OfLong listIterator(int index)
      {
        return new BidirectionalItr(this,index+rootOffset);
      }
      @Override
      public void put(int index,long val)
      {
        root.arr[index+rootOffset]=val;
      }     
      @Override
      public boolean remove(Object val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val instanceof Long)
          {
            return uncheckedremoveVal(size,(long)(val));
          }
        }
        return false;
      }
      @Override
      public boolean removeVal(final boolean val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,TypeUtil.castToLong(val));
        }
        return false;
      }
      @Override
      public boolean removeVal(final int val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          {
            return this.uncheckedremoveVal(size,(val));
          }
        }
        return false;
      }
      @Override
      public boolean removeVal(final long val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,(val));
        }
        return false;
      }
      @Override
      public boolean removeVal(final float val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final long v;
          if(TypeUtil.floatEquals(val,v=(long)val))
          {
            return this.uncheckedremoveVal(size,v);
          }
        }
        return false;
      }
      @Override
      public boolean removeVal(final double val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final long v;
          if(val==(v=(long)val))
          {
            return this.uncheckedremoveVal(size,v);
          }
        }
        return false;
      }
      @Override
      public long removeLongAt(int index)
      {
        final Unchecked root;
        final long[] arr;
        final var removed=(long)(arr=(root=this.root).arr)[index+=rootOffset];
        eraseIndexHelper(arr,index,--root.size);
        bubbleUpDecrementSize(parent);
        --size;
        return removed;
      }
      @Override
      public boolean removeIf(final LongPredicate filter)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return uncheckedRemoveIf(size,filter);
        }
        return false;
      }
      @Override
      public void replaceAll(final LongUnaryOperator operator)
      {
        final int size;
        if(0!=((size=this.size)))
        {
          final int rootOffset;
          LongArrSeq.uncheckedreplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);
        }
      }
      @Override
      public void reverseSort()
      {
        final int size;
        if(1<((size=this.size)))
        {
          final int rootOffset;
          LongSortUtil.uncheckedreverseSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
        }
      }
      @Override
      public void sort()
      {
        final int size;
        if(1<((size=this.size)))
        {
          final int rootOffset;
          LongSortUtil.uncheckedsort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
        }
      }
      @Override
      public void sort(final LongComparator sorter)
      {
        final int size;
        if(1<((size=this.size)))
        {
          final int rootOffset;
          LongSortUtil.uncheckedcomparatorSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter);
        }
      }
      @Override
      public void replaceAll(final UnaryOperator<Long> operator)
      {
        final int size;
        if(0!=((size=this.size)))
        {
          final int rootOffset;
          LongArrSeq.uncheckedreplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator::apply);
        }
      }
      @Override
      public void sort(final Comparator<? super Long> sorter)
      {
        final int size;
        if(1<((size=this.size)))
        {
          final int rootOffset;
          LongSortUtil.uncheckedcomparatorSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter::compare);
        }
      }
      @Override
      public boolean removeIf(final Predicate<? super Long> filter)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return uncheckedRemoveIf(size,filter::test);
        }
        return false;
      }
      @Override
      public long set(int index,long val)
      {
        final long[] arr;
        final var oldVal=(long)(arr=root.arr)[index+=rootOffset];
        arr[index]=val;
        return oldVal;
      }
      @Override
      public OmniList.OfLong subList(int fromIndex,int toIndex)
      {
        return new SubList(root,this,this.rootOffset+fromIndex,toIndex-fromIndex);
      }
      @Override
      public long[] toLongArray()
      {
        final int size;
        if((size=this.size)!=0)
        {
          final long[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new long[size],0,size);
          return dst;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
      }
      @Override
      public Long[] toArray()
      {
        final int size;
        if((size=this.size)!=0)
        {
          final Long[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new Long[size],0,size);
          return dst;
        }
        return OmniArray.OfLong.DEFAULT_BOXED_ARR;
      }
      @Override
      public double[] toDoubleArray()
      {
        final int size;
        if((size=this.size)!=0)
        {
          final double[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new double[size],0,size);
          return dst;
        }
        return OmniArray.OfDouble.DEFAULT_ARR;
      }
      @Override
      public float[] toFloatArray()
      {
        final int size;
        if((size=this.size)!=0)
        {
          final float[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new float[size],0,size);
          return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
      }
      @Override
      public <T> T[] toArray(IntFunction<T[]> arrConstructor)
      {
        final int size;
        final T[] dst=arrConstructor.apply(size=this.size);
        if(size!=0)
        {
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst,0,size);
        }
        return dst;
      }
      @Override
      public <T> T[] toArray(T[] arr)
      {
        final int size;
        if((size=this.size)!=0)
        {
          ArrCopy.uncheckedCopy(root.arr,rootOffset,arr=OmniArray.uncheckedArrResize(size,arr),0,size);
        }
        else if(arr.length!=0)
        {
          arr[0]=null;
        }
        return arr;
      }
      @Override
      public String toString()
      {
        final int size;
        if((size=this.size)!=0)
        {
          final StringBuilder builder;
          final int rootOffset;
          forwardToString(root.arr,rootOffset=this.rootOffset,rootOffset+size,builder=new StringBuilder("["));
          return builder.append(']').toString();
        }
        return "[]";
      }
      private boolean uncheckedcontains(int size,long val)
      { 
        final int rootOffset;
        return LongArrSeq.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
      }
      private int uncheckedindexOf(int size,long val)
      { 
        final int rootOffset;
        return LongArrSeq.uncheckedindexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
      }
      private int uncheckedlastIndexOf(int size,long val)
      { 
        final int rootOffset;
        return LongArrSeq.uncheckedlastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
      }
      private boolean uncheckedRemoveIf(int size,final LongPredicate filter)
      {
        final Unchecked root;
        int srcOffset;
        final int srcBound=(srcOffset=rootOffset)+size;
        final var arr=(root=this.root).arr;
        while(!filter.test((long)arr[srcOffset]))
        {
          if(++srcOffset==srcBound)
          {
            return false;
          }
        }
        this.size=size-(size=((LongArrSeq)root).finalizeSubListBatchRemove(arr,pullSurvivorsDown(arr,srcOffset,srcBound-1,filter),srcBound));
        bubbleUpDecrementSize(parent,size);
        return true;
      }
      private boolean uncheckedremoveVal(int size,long val)
      {
        final Unchecked root;final var arr=(root=this.root).arr;int index;final int bound=(index=this.rootOffset)+(--size);
        for(;val!=(arr[index]);)
        {
          if(++index==bound)
          {
            return false;
          }
        }
        eraseIndexHelper(arr,index,--root.size);
        bubbleUpDecrementSize(parent);
        this.size=size;
        return true;
      }
      private static class AscendingItr extends AbstractLongItr implements OmniIterator.OfLong
      {
        private transient final SubList parent;
        private transient int cursor;
        private AscendingItr(SubList parent)
        {
           this.parent=parent;
           this.cursor=parent.rootOffset;
        }
        @Override
        public void forEachRemaining(LongConsumer action)
        {
          final int cursor,bound;
          final SubList parent;
          if((cursor=this.cursor)!=(bound=(parent=this.parent).rootOffset+parent.size))
          {
            uncheckedForwardForEach(parent.root.arr,cursor,bound,action);
            this.cursor=bound;
          }
        }
        @Override
        public void forEachRemaining(Consumer<? super Long> action)
        {
          final int cursor,bound;
          final SubList parent;
          if((cursor=this.cursor)!=(bound=(parent=this.parent).rootOffset+parent.size))
          {
            uncheckedForwardForEach(parent.root.arr,cursor,bound,action::accept);
            this.cursor=bound;
          }
        }
        @Override
        public boolean hasNext()
        {
          final SubList parent;
          return cursor!=(parent=this.parent).rootOffset+parent.size;
        }
        @Override
        public long nextLong()
        {
          return (long)parent.root.arr[cursor++];
        }
        @Override
        public void remove()
        {
          final SubList parent;
          final Unchecked root;
          eraseIndexHelper((root=(parent=this.parent).root).arr,--cursor,--root.size);
          bubbleUpDecrementSize(parent.parent);
          --parent.size;
        }
      }
      private static class BidirectionalItr implements OmniListIterator.OfLong
      {
        private transient final SubList parent;
        private transient int cursor;
        private transient int lastRet;
        private BidirectionalItr(final SubList parent)
        {
          this.parent=parent;
          this.cursor=parent.rootOffset;
        }
        private BidirectionalItr(final SubList parent,final int cursor)
        {
          this.parent=parent;
          this.cursor=cursor;
        }
        @Override
        public void add(final long val)
        {
          final SubList parent;
          final LongArrSeq root;
          final int rootSize,cursor=this.cursor;
          if((rootSize=(root=(parent=this.parent).root).size)!=0)
          {
            root.uncheckedInsert(cursor,val,rootSize);
          }
          else
          {
            root.uncheckedInit(val);
          }
          bubbleUpIncrementSize(parent.parent);
          ++parent.size;
          this.cursor=cursor+1;
        }
        @Override
        public void forEachRemaining(LongConsumer action)
        {
          final int cursor,bound;
          final SubList parent;
          if((cursor=this.cursor)!=(bound=(parent=this.parent).rootOffset+parent.size))
          {
            uncheckedForwardForEach(parent.root.arr,cursor,bound,action);
            this.cursor=bound;
            lastRet=bound-1;
          }
        }
        @Override
        public void forEachRemaining(Consumer<? super Long> action)
        {
          final int cursor,bound;
          final SubList parent;
          if((cursor=this.cursor)!=(bound=(parent=this.parent).rootOffset+parent.size))
          {
            uncheckedForwardForEach(parent.root.arr,cursor,bound,action::accept);
            this.cursor=bound;
            lastRet=bound-1;
          }
        }
        @Override
        public boolean hasNext()
        {
          final SubList parent;
          return cursor!=(parent=this.parent).rootOffset+parent.size;
        }
        @Override
        public boolean hasPrevious()
        {
          return cursor!=parent.rootOffset;
        }
        @Override
        public long nextLong()
        {
          final int lastRet;
          this.lastRet=lastRet=cursor++;
          return (long)parent.root.arr[lastRet];
        }
        @Override
        public int nextIndex()
        {
          return cursor-parent.rootOffset;
        }
        @Override
        public long previousLong()
        {
          final int lastRet;
          this.lastRet=lastRet=--cursor;
          return (long)parent.root.arr[lastRet];
        }
        @Override
        public int previousIndex()
        {
          return cursor-parent.rootOffset-1;
        }
        @Override
        public void remove()
        {
          final SubList parent;
          final Unchecked root;
          final int lastRet;
          eraseIndexHelper((root=(parent=this.parent).root).arr,lastRet=this.lastRet,--root.size);
          bubbleUpDecrementSize(parent.parent);
          --parent.size;
          cursor=lastRet;
        }
        @Override
        public void set(long val)
        {
          parent.root.arr[lastRet]=val;
        }
      }
    }
  }
  public static class UncheckedStack extends Unchecked implements OmniStack.OfLong
  {
    public UncheckedStack()
    {
      super();
    }
    public UncheckedStack(final int capacity)
    {
      super(capacity);
    }
    public UncheckedStack(final int size,final long[] arr)
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
      return new UncheckedStack(size,arr);
    }
    @Override
    public boolean equals(final Object val)
    {
      //TODO implements equals method
      return false;
    }
    @Override
    public OmniIterator.OfLong iterator()
    {
      return new UncheckedDescendingItr(this);
    }
        @Override
        public long pollLong()
        {
          final int size;
          if((size=this.size)!=0)
          {
            return (long)(((LongArrSeq)this).uncheckedPop(size-1));
          }
          return Long.MIN_VALUE;
        }
        @Override
        public Long poll()
        {
          final int size;
          if((size=this.size)!=0)
          {
            return (Long)(((LongArrSeq)this).uncheckedPop(size-1));
          }
          return null;
        }
        @Override
        public double pollDouble()
        {
          final int size;
          if((size=this.size)!=0)
          {
            return (double)(((LongArrSeq)this).uncheckedPop(size-1));
          }
          return Double.NaN;
        }
        @Override
        public float pollFloat()
        {
          final int size;
          if((size=this.size)!=0)
          {
            return (float)(((LongArrSeq)this).uncheckedPop(size-1));
          }
          return Float.NaN;
        }
    @Override
    void uncheckedCopyInto(final long[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Object[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Long[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final double[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final float[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedForEach(final int size,final LongConsumer action)
    {
      uncheckedReverseForEach(arr,0,size,action);
    }
    @Override
    int uncheckedHashCode(final int size)
    {
      return reverseHashCode(arr,0,size);
    }
    @Override
    boolean uncheckedremoveVal(int size,final long val)
    {
      final var arr=this.arr;int index;
      for(index=--size;val!=(arr[index]);--index)
      {
        if(index==0)
        {
          return false;
        }
      }
      eraseIndexHelper(arr,index,size);
      this.size=size;
      return true;
    }
    @Override
    void uncheckedToString(final int size,final StringBuilder builder)
    {
      reverseToString(arr,0,size,builder);
    }
    private static class UncheckedDescendingItr extends AbstractLongItr implements OmniIterator.OfLong
    {
      private transient final UncheckedStack root;
      private transient int cursor;
      private UncheckedDescendingItr(UncheckedStack root)
      {
        this.root=root;
        cursor=root.size;
      }
      @Override
      public void forEachRemaining(LongConsumer action)
      {
        final int cursor;
        if((cursor=this.cursor)!=0)
        {
          uncheckedReverseForEach(root.arr,0,cursor,action);
          this.cursor=0;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Long> action)
      {
        final int cursor;
        if((cursor=this.cursor)!=0)
        {
          uncheckedReverseForEach(root.arr,0,cursor,action::accept);
          this.cursor=0;
        }
      }
      @Override
      public boolean hasNext()
      {
        return cursor!=0;
      }
      @Override
      public long nextLong()
      {
        return (long)root.arr[--cursor];
      }
      @Override
      public void remove()
      {
        final UncheckedStack root;
        eraseIndexHelper((root=this.root).arr,cursor,--root.size);
      }
    }
  }
}
