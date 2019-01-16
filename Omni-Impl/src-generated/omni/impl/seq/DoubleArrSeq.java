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
import omni.util.HashUtils;
import omni.impl.seq.AbstractDoubleList;
import omni.impl.AbstractDoubleItr;
import omni.util.BitSetUtils;
import omni.util.TypeUtil;
import omni.function.DoubleComparator;
import java.util.function.DoubleUnaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
public abstract class DoubleArrSeq extends AbstractDoubleList implements OmniCollection.OfDouble
{
  private static void eraseIndexHelper(double[] arr,int index,int newSize)
  {
    ArrCopy.semicheckedCopy(arr,index+1,arr,index,newSize-index);
  }
  static boolean uncheckedcontains0(double[] arr,int offset,int bound)
  {
    for(;0!=(arr[offset]);)
    {
      if(++offset==bound)
      {
        return false;
      }
    }
    return true;
  }
  static boolean uncheckedcontainsBits(double[] arr,int offset,int bound,long bits)
  {
    for(;bits!=Double.doubleToRawLongBits(arr[offset]);)
    {
      if(++offset==bound)
      {
        return false;
      }
    }
    return true;
  }
  static boolean uncheckedcontainsNaN(double[] arr,int offset,int bound)
  {
    for(;!Double.isNaN(arr[offset]);)
    {
      if(++offset==bound)
      {
        return false;
      }
    }
    return true;
  }
  private static int uncheckedindexOf0(double[] arr,int offset,int bound)
  {
    int index;
    for(index=offset;0!=(arr[index]);)
    {
      if(++index==bound)
      {
        return -1;
      }
    }
    return index-offset;
  }
  private static int uncheckedindexOfBits(double[] arr,int offset,int bound,long bits)
  {
    int index;
    for(index=offset;bits!=Double.doubleToRawLongBits(arr[index]);)
    {
      if(++index==bound)
      {
        return -1;
      }
    }
    return index-offset;
  }
  private static int uncheckedindexOfNaN(double[] arr,int offset,int bound)
  {
    int index;
    for(index=offset;!Double.isNaN(arr[index]);)
    {
      if(++index==bound)
      {
        return -1;
      }
    }
    return index-offset;
  }
  private static int uncheckedindexOf0(double[] arr,int bound)
  {
    int index;
    for(index=0;0!=(arr[index]);)
    {
      if(++index==bound)
      {
        return -1;
      }
    }
    return index;
  }
  private static int uncheckedindexOfBits(double[] arr,int bound,long bits)
  {
    int index;
    for(index=0;bits!=Double.doubleToRawLongBits(arr[index]);)
    {
      if(++index==bound)
      {
        return -1;
      }
    }
    return index;
  }
  private static int uncheckedindexOfNaN(double[] arr,int bound)
  {
    int index;
    for(index=0;!Double.isNaN(arr[index]);)
    {
      if(++index==bound)
      {
        return -1;
      }
    }
    return index;
  }
  private static int uncheckedlastIndexOf0(double[] arr,int offset,int bound)
  {
    for(;0!=(arr[--bound]);)
    {
      if(bound==offset)
      {
        return -1;
      }
    }
    return bound-offset;
  }
  private static int uncheckedlastIndexOfBits(double[] arr,int offset,int bound,long bits)
  {
    for(;bits!=Double.doubleToRawLongBits(arr[--bound]);)
    {
      if(bound==offset)
      {
        return -1;
      }
    }
    return bound-offset;
  }
  private static int uncheckedlastIndexOfNaN(double[] arr,int offset,int bound)
  {
    for(;!Double.isNaN(arr[--bound]);)
    {
      if(bound==offset)
      {
        return -1;
      }
    }
    return bound-offset;
  }
  private static int uncheckedlastIndexOf0(double[] arr,int bound)
  {
    for(;0!=(arr[--bound]);)
    {
      if(bound==0)
      {
        return -1;
      }
    }
    return bound;
  }
  private static int uncheckedlastIndexOfBits(double[] arr,int bound,long bits)
  {
    for(;bits!=Double.doubleToRawLongBits(arr[--bound]);)
    {
      if(bound==0)
      {
        return -1;
      }
    }
    return bound;
  }
  private static int uncheckedlastIndexOfNaN(double[] arr,int bound)
  {
    for(;!Double.isNaN(arr[--bound]);)
    {
      if(bound==0)
      {
        return -1;
      }
    }
    return bound;
  }
  private static int uncheckedsearch0(double[] arr,int bound)
  {
    int index;
    for(index=bound-1;0!=(arr[index]);--index)
    {
      if(index==0)
      {
        return -1;
      }
    }
    return bound-index;
  }
  private static int uncheckedsearchBits(double[] arr,int bound,long bits)
  {
    int index;
    for(index=bound-1;bits!=Double.doubleToRawLongBits(arr[index]);--index)
    {
      if(index==0)
      {
        return -1;
      }
    }
    return bound-index;
  }
  private static int uncheckedsearchNaN(double[] arr,int bound)
  {
    int index;
    for(index=bound-1;!Double.isNaN(arr[index]);--index)
    {
      if(index==0)
      {
        return -1;
      }
    }
    return bound-index;
  }
  private static
  boolean
  uncheckedcontains
  (double[] arr
  ,int offset
  ,int bound,double val)
  {
    if(val==val)
    {
      return uncheckedcontainsBits(arr
      ,0
      ,bound,Double.doubleToRawLongBits(val));
    }
    return uncheckedcontainsNaN(arr
    ,0
    ,bound);
  }
  private static
  int
  uncheckedsearch
  (double[] arr
  ,int bound,double val)
  {
    if(val==val)
    {
      return uncheckedsearchBits(arr
      ,bound,Double.doubleToRawLongBits(val));
    }
    return uncheckedsearchNaN(arr
    ,bound);
  }
  private static
  int
  uncheckedindexOf
  (double[] arr
  ,int bound,double val)
  {
    if(val==val)
    {
      return uncheckedindexOfBits(arr
      ,bound,Double.doubleToRawLongBits(val));
    }
    return uncheckedindexOfNaN(arr
    ,bound);
  }
  private static
  int
  uncheckedlastIndexOf
  (double[] arr
  ,int bound,double val)
  {
    if(val==val)
    {
      return uncheckedlastIndexOfBits(arr
      ,bound,Double.doubleToRawLongBits(val));
    }
    return uncheckedlastIndexOfNaN(arr
    ,bound);
  }
  private static  void uncheckedreplaceAll(double[] arr,int offset,int bound,DoubleUnaryOperator operator)
  {
    do
    {
      arr[offset]=operator.applyAsDouble((double)arr[offset]);
    }
    while(++offset!=bound);
  }
  static  void uncheckedForwardForEach(double[] arr,int offset,int bound,DoubleConsumer action)
  {
    do
    {
      action.accept((double)arr[offset]);
    }
    while(++offset!=bound);
  }
  static  void uncheckedReverseForEach(double[] arr,int offset,int bound,DoubleConsumer action)
  {
    do
    {
      action.accept((double)arr[--bound]);
    }
    while(bound!=offset);
  }
  private static int forwardHashCode(double[] arr,int offset,int bound)
  {
    int hash=31+HashUtils.hashDouble(arr[offset]);
    while(++offset!=bound)
    {
      hash=hash*31+HashUtils.hashDouble(arr[offset]);
    }
    return hash;
  }
  private static int reverseHashCode(double[] arr,int offset,int bound)
  {
    int hash=31+HashUtils.hashDouble(arr[offset]);
    while(bound!=offset)
    {
      hash=hash*31+HashUtils.hashDouble(arr[offset]);
    }
    return hash;
  }
  private static void forwardToString(double[] arr,int offset,int bound,StringBuilder builder)
  {
    for(builder.append(arr[offset]);++offset!=bound;builder.append(',').append(' ').append(arr[offset]));
  }
  private static void reverseToString(double[] arr,int offset,int bound,StringBuilder builder)
  {
    for(builder.append(arr[--bound]);bound!=offset;builder.append(',').append(' ').append(arr[--bound]));
  }
  static  int pullSurvivorsUp(double[] arr,int srcBegin,int srcEnd,DoublePredicate filter)
  {
    int dstOffset=srcBegin;
    while(srcBegin!=srcEnd)
    {
      final double v;
      if(!filter.test((double)(v=arr[--srcBegin])))
      {
        arr[dstOffset--]=v;
      }
    }
    return dstOffset;
  }
  static  int pullSurvivorsDown(double[] arr,int srcBegin,int srcEnd,DoublePredicate filter)
  {
    int dstOffset=srcBegin;
    while(srcBegin!=srcEnd)
    {
      final double v;
      if(!filter.test((double)(v=arr[++srcBegin])))
      {
        arr[dstOffset++]=v;
      }
    }
    return dstOffset;
  }
  static  int markSurvivorsAndPullUp(CheckedCollection.AbstractModCountChecker modCountChecker,double[] arr,int srcOffset,int srcBound,DoublePredicate filter)
  {
    int dstOffset=srcOffset;
    for(;;)
    {
      if(--srcOffset==srcBound)
      {
        modCountChecker.checkModCount();
        break;
      }
      final double v;
      if(!filter.test((double)(v=arr[srcOffset])))
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
  static  int markSurvivorsAndPullDown(CheckedCollection.AbstractModCountChecker modCountChecker,double[] arr,int srcOffset,int srcBound,DoublePredicate filter)
  {
    int dstOffset=srcOffset;
    for(;;)
    {
      if(++srcOffset==srcBound)
      {
        modCountChecker.checkModCount();
        break;
      }
      final double v;
      if(!filter.test((double)(v=arr[srcOffset])))
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
  static  int markSurvivorsReverse(double[] arr,long[] survivorSet,int offset,int bound,DoublePredicate filter)
  {
    for(int numSurvivors=0,wordOffset=survivorSet.length-1;;)
    {
      long word=0L,marker=1L<<63;
      do
      {
        if(!filter.test((double)arr[offset]))
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
  static  int markSurvivors(double[] arr,long[] survivorSet,int offset,int bound,DoublePredicate filter)
  {
    for(int numSurvivors=0,wordOffset=0;;)
    {
      long word=0L,marker=1L;
      do
      {
        if(!filter.test((double)arr[offset]))
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
  static int pullSurvivorsUp(double[] arr,long[] survivorSet,int dstOffset,int srcOffset,int numSurvivors)
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
  static int pullSurvivorsDown(double[] arr,long[] survivorSet,int dstOffset,int srcOffset,int numSurvivors)
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
      return;
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
  @Override
  public boolean contains(final Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Double)
      {
        return DoubleArrSeq.uncheckedcontains(arr,0,size,(double)(val));
      }
    }
    return false;
  }
  public int indexOf(final Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Double)
      {
        return DoubleArrSeq.uncheckedindexOf(arr,size,(double)(val));
      }
    }
    return -1;
  }
  public int lastIndexOf(final Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Double)
      {
        return DoubleArrSeq.uncheckedlastIndexOf(arr,size,(double)(val));
      }
    }
    return -1;
  }
  public int search(final Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Double)
      {
        return DoubleArrSeq.uncheckedsearch(arr,size,(double)(val));
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
      if(val)
      {
        return DoubleArrSeq.uncheckedcontainsBits(arr,0,size,TypeUtil.DBL_TRUE_BITS);
      }
      return DoubleArrSeq.uncheckedcontains0(arr,0,size);
    }
    return false;
  }
  @Override
  public boolean contains(final int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=0)
      {
        {
          return DoubleArrSeq.uncheckedcontainsBits(arr,0,size,Double.doubleToRawLongBits(val));
        }
      }
      else
      {
        return DoubleArrSeq.uncheckedcontains0(arr,0,size);
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
      if(val!=0)
      {
        if(TypeUtil.checkCastToDouble(val))
        {
          return DoubleArrSeq.uncheckedcontainsBits(arr,0,size,Double.doubleToRawLongBits(val));
        }
      }
      else
      {
        return DoubleArrSeq.uncheckedcontains0(arr,0,size);
      }
    }
    return false;
  }
  @Override
  public boolean contains(final float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val==val)
      {
        return DoubleArrSeq.uncheckedcontainsBits(arr,0,size,Double.doubleToRawLongBits(val));
      }
      return DoubleArrSeq.uncheckedcontainsNaN(arr,0,size);
    }
    return false;
  }
  @Override
  public boolean contains(final double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return DoubleArrSeq.uncheckedcontains(arr,0,size,(val));
    }
    return false;
  }
  @Override
  public void forEach(final DoubleConsumer action)
  {
    final int size;
    if((size=this.size)!=0)
    {
      uncheckedForEach(size,action);
    }
  }
  @Override
  public void forEach(final Consumer<? super Double> action)
  {
    final int size;
    if((size=this.size)!=0)
    {
      uncheckedForEach(size,action::accept);
    }
  }
  @Override
  public boolean removeIf(final DoublePredicate filter)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedRemoveIf(size,filter);
    }
    return false;
  }
  @Override
  public boolean removeIf(final Predicate<? super Double> filter)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedRemoveIf(size,filter::test);
    }
    return false;
  }
  public Double pop()
  {
    return popDouble();
  }
  public void push(final Double val)
  {
    push((double)val);
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
      if(val)
      {
        return DoubleArrSeq.uncheckedindexOfBits(arr,size,TypeUtil.DBL_TRUE_BITS);
      }
      return DoubleArrSeq.uncheckedindexOf0(arr,size);
    }
    return -1;
  }
  public int indexOf(final int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=0)
      {
        {
          return DoubleArrSeq.uncheckedindexOfBits(arr,size,Double.doubleToRawLongBits(val));
        }
      }
      else
      {
        return DoubleArrSeq.uncheckedindexOf0(arr,size);
      }
    }
    return -1;
  }
  public int indexOf(final long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=0)
      {
        if(TypeUtil.checkCastToDouble(val))
        {
          return DoubleArrSeq.uncheckedindexOfBits(arr,size,Double.doubleToRawLongBits(val));
        }
      }
      else
      {
        return DoubleArrSeq.uncheckedindexOf0(arr,size);
      }
    }
    return -1;
  }
  public int indexOf(final float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val==val)
      {
        return DoubleArrSeq.uncheckedindexOfBits(arr,size,Double.doubleToRawLongBits(val));
      }
      return DoubleArrSeq.uncheckedindexOfNaN(arr,size);
    }
    return -1;
  }
  public int indexOf(final double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return DoubleArrSeq.uncheckedindexOf(arr,size,(val));
    }
    return -1;
  }
  public int lastIndexOf(final boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val)
      {
        return DoubleArrSeq.uncheckedlastIndexOfBits(arr,size,TypeUtil.DBL_TRUE_BITS);
      }
      return DoubleArrSeq.uncheckedlastIndexOf0(arr,size);
    }
    return -1;
  }
  public int lastIndexOf(final int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=0)
      {
        {
          return DoubleArrSeq.uncheckedlastIndexOfBits(arr,size,Double.doubleToRawLongBits(val));
        }
      }
      else
      {
        return DoubleArrSeq.uncheckedlastIndexOf0(arr,size);
      }
    }
    return -1;
  }
  public int lastIndexOf(final long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=0)
      {
        if(TypeUtil.checkCastToDouble(val))
        {
          return DoubleArrSeq.uncheckedlastIndexOfBits(arr,size,Double.doubleToRawLongBits(val));
        }
      }
      else
      {
        return DoubleArrSeq.uncheckedlastIndexOf0(arr,size);
      }
    }
    return -1;
  }
  public int lastIndexOf(final float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val==val)
      {
        return DoubleArrSeq.uncheckedlastIndexOfBits(arr,size,Double.doubleToRawLongBits(val));
      }
      return DoubleArrSeq.uncheckedlastIndexOfNaN(arr,size);
    }
    return -1;
  }
  public int lastIndexOf(final double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return DoubleArrSeq.uncheckedlastIndexOf(arr,size,(val));
    }
    return -1;
  }
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
  public Double peek()
  {
    final int size;
    if((size=this.size)!=0)
    {
      return (arr[size-1]);
    }
    return null;
  }
  abstract void uncheckedCopyInto(final Double[] dst,final int length);
  abstract void uncheckedCopyInto(final Object[] dst,final int length);
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
        return this.uncheckedremoveVal(size,(double)(val));
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
      if(val)
      {
        return this.uncheckedremoveValBits(size,TypeUtil.DBL_TRUE_BITS);
      }
      return this.uncheckedremoveVal0(size);
    }
    return false;
  }
  @Override
  public boolean removeVal(final int val)
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
    return false;
  }
  @Override
  public boolean removeVal(final long val)
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
  public boolean removeVal(final float val)
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
  public boolean removeVal(final double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,(val));
    }
    return false;
  }
  public int search(final boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val)
      {
        return DoubleArrSeq.uncheckedsearchBits(arr,size,TypeUtil.DBL_TRUE_BITS);
      }
      return DoubleArrSeq.uncheckedsearch0(arr,size);
    }
    return -1;
  }
  public int search(final int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=0)
      {
        {
          return DoubleArrSeq.uncheckedsearchBits(arr,size,Double.doubleToRawLongBits(val));
        }
      }
      else
      {
        return DoubleArrSeq.uncheckedsearch0(arr,size);
      }
    }
    return -1;
  }
  public int search(final long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=0)
      {
        if(TypeUtil.checkCastToDouble(val))
        {
          return DoubleArrSeq.uncheckedsearchBits(arr,size,Double.doubleToRawLongBits(val));
        }
      }
      else
      {
        return DoubleArrSeq.uncheckedsearch0(arr,size);
      }
    }
    return -1;
  }
  public int search(final float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val==val)
      {
        return DoubleArrSeq.uncheckedsearchBits(arr,size,Double.doubleToRawLongBits(val));
      }
      return DoubleArrSeq.uncheckedsearchNaN(arr,size);
    }
    return -1;
  }
  public int search(final double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return DoubleArrSeq.uncheckedsearch(arr,size,(val));
    }
    return -1;
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
  abstract boolean uncheckedremoveVal0(final int size);
  abstract boolean uncheckedremoveValBits(final int size,final long bits);
  abstract boolean uncheckedremoveValNaN(final int size);
  private boolean uncheckedremoveVal(final int size,final double val)
  {
    if(val==(val))
    {
      return uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
    }
    return uncheckedremoveValNaN(size);
  }
  abstract void uncheckedForEach(final int size,final DoubleConsumer action);
  abstract int uncheckedHashCode(final int size);
  abstract boolean uncheckedRemoveIf(final int size,final DoublePredicate filter);
  abstract void uncheckedToString(final int size,final StringBuilder builder);
  private int finalizeSubListBatchRemove(final double[] arr,final int newBound,final int oldBound)
  {
    final int newRootSize,numRemoved;
    size=newRootSize=size-(numRemoved=oldBound-newBound);
    ArrCopy.semicheckedCopy(arr,oldBound,arr,newBound,newRootSize-newBound);
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
    size=1;
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
  public static abstract class Checked extends DoubleArrSeq
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
    private Checked(final int size,final double[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean add(final double val)
    {
      ++modCount;
      super.push(val);
      return true;
    }  
    @Override
    public void add(final int index,final double val)
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
    public double getDouble(final int index)
    {
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      return (double)arr[index];
    }
    @Override
    public double popDouble()
    {
      final int size;
      if((size=this.size)!=0)
      {
        ++modCount;
        return (double)super.uncheckedPop(size-1);
      }
      throw new NoSuchElementException();
    }
    @Override
    public void push(final double val)
    {
      ++modCount;
      super.push(val);
    }
    @Override
    public double removeDoubleAt(final int index)
    {
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ++modCount;
      final double[] arr;
      final var removed=(double)(arr=this.arr)[index];
      eraseIndexHelper(arr,index,--size);
      this.size=size;
      return removed;
    }
    @Override
    public double set(final int index,final double val)
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
    boolean uncheckedRemoveIf(int size,final DoublePredicate filter)
    {
      int srcOffset;
      final int modCount=this.modCount;
      try
      {
        srcOffset=0;
        final var arr=this.arr;
        for(;;)
        {
          if(filter.test((double)arr[srcOffset]))
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
  public static class CheckedList extends Checked implements OmniList.OfDouble
  {
    public CheckedList()
    {
      super();
    }
    public CheckedList(final int capacity)
    {
      super(capacity);
    }
    public CheckedList(final int size,final double[] arr)
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
      return new CheckedList(size,arr);
    }
    @Override
    public boolean equals(final Object val)
    {
      //TODO implements equals method
      return false;
    }
    @Override
    public OmniIterator.OfDouble iterator()
    {
      return new BidirectionalItr(this);
    }
    @Override
    public OmniListIterator.OfDouble listIterator()
    {
      return new BidirectionalItr(this);
    }
    @Override
    public OmniListIterator.OfDouble listIterator(final int index)
    {
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,this.size);
      return new BidirectionalItr(this,index);
    }
    @Override
    public void put(final int index,final double val)
    {
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      arr[index]=val;
    }
    @Override
    public void replaceAll(final DoubleUnaryOperator operator)
    {
      final int size;
      if(0!=((size=this.size)))
      {
        int modCount=this.modCount;
        try
        {
          DoubleArrSeq.uncheckedreplaceAll(arr,0,size,operator);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        this.modCount=modCount+1;
      }
    }
    @Override
    public void sort(final DoubleComparator sorter)
    {
      final int size;
      if(1<((size=this.size)))
      {
        int modCount=this.modCount;
        try
        {
          DoubleSortUtil.uncheckedcomparatorSort(arr,0,size-1,sorter);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        this.modCount=modCount+1;
      }
    }
    @Override
    public void replaceAll(final UnaryOperator<Double> operator)
    {
      final int size;
      if(0!=((size=this.size)))
      {
        int modCount=this.modCount;
        try
        {
          DoubleArrSeq.uncheckedreplaceAll(arr,0,size,operator::apply);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        this.modCount=modCount+1;
      }
    }
    @Override
    public void sort(final Comparator<? super Double> sorter)
    {
      final int size;
      if(1<((size=this.size)))
      {
        int modCount=this.modCount;
        try
        {
          DoubleSortUtil.uncheckedcomparatorSort(arr,0,size-1,sorter::compare);
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
         DoubleSortUtil.uncheckedreverseSort(arr,0,size-1);
         ++this.modCount;
      }
    }
    public void sort()
    {
      final int size;
      if((size=this.size)>1)
      {
         DoubleSortUtil.uncheckedsort(arr,0,size-1);
         ++this.modCount;
      }
    }
    @Override
    public OmniList.OfDouble subList(final int fromIndex,final int toIndex)
    {
      CheckedCollection.checkSubListRange(fromIndex,toIndex,this.size);
      return new SubList(this,fromIndex,toIndex-fromIndex);
    }
    @Override
    void uncheckedCopyInto(final double[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Object[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Double[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedForEach(final int size,final DoubleConsumer action)
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
    boolean uncheckedremoveVal0(int size)
    {
      final var arr=this.arr;int index;
      for(index=0,--size;0!=(arr[index]);++index)
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
    boolean uncheckedremoveValBits(int size,final long bits)
    {
      final var arr=this.arr;int index;
      for(index=0,--size;bits!=Double.doubleToRawLongBits(arr[index]);++index)
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
    boolean uncheckedremoveValNaN(int size)
    {
      final var arr=this.arr;int index;
      for(index=0,--size;!Double.isNaN(arr[index]);++index)
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
    private static class BidirectionalItr implements OmniListIterator.OfDouble
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
      public void add(final double val)
      {
        final Checked root;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        final int rootSize,cursor=this.cursor;
        if((rootSize=root.size)!=0)
        {
          ((DoubleArrSeq)root).uncheckedInsert(cursor,val,rootSize);
        }
        else
        {
          ((DoubleArrSeq)root).uncheckedInit(val);
        }
        root.modCount=++modCount;
        this.modCount=modCount;
        this.cursor=cursor+1;
        this.lastRet=-1;
      }
      @Override
      public void forEachRemaining(final DoubleConsumer action)
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
      public void forEachRemaining(final Consumer<? super Double> action)
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
      public double nextDouble()
      {
        final Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        final int cursor;
        if((cursor=this.cursor)!=root.size)
        {
          lastRet=cursor;
          this.cursor=cursor+1;
          return (double)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override
      public int nextIndex()
      {
        return cursor;
      }
      @Override
      public double previousDouble()
      {
        final Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        int cursor;
        if((cursor=this.cursor)!=0)
        {
          lastRet=--cursor;
          this.cursor=cursor;
          return (double)root.arr[cursor];
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
      public void set(final double val)
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
    private static class SubList extends AbstractDoubleList implements OmniList.OfDouble
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
      public boolean add(final double val)
      {
        final Checked root;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        final int rootSize;
        final int size=this.size;
        if((rootSize=root.size)!=0)
        {
          ((DoubleArrSeq)root).uncheckedInsert(rootOffset+size,val,rootSize);
        }
        else
        {
          ((DoubleArrSeq)root).uncheckedInit(val);
        }
        root.modCount=++modCount;
        this.modCount=modCount;
        bubbleUpIncrementSize(parent);
        this.size=size+1;
        return true;
      }
      @Override
      public void add(final int index,final double val)
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
          ((DoubleArrSeq)root).uncheckedInsert(rootOffset+index,val,rootSize);
        }
        else
        {
          ((DoubleArrSeq)root).uncheckedInit(val);
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
          final double[] arr;
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
        final double[] arr;
        final int size;
        if((size=this.size)!=0)
        {
          ArrCopy.uncheckedCopy(root.arr,rootOffset,arr=new double[size],0,size);
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
          if(val instanceof Double)
          {
            return uncheckedcontains(size,(double)(val));
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
          if(val instanceof Double)
          {
            return uncheckedindexOf(size,(double)(val));
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
          if(val instanceof Double)
          {
            return uncheckedlastIndexOf(size,(double)(val));
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
          if(val)
          {
            return this.uncheckedcontainsBits(size,TypeUtil.DBL_TRUE_BITS);
          }
          return this.uncheckedcontains0(size);
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
          if(val!=0)
          {
            {
              return this.uncheckedcontainsBits(size,Double.doubleToRawLongBits(val));
            }
          }
          else
          {
            return this.uncheckedcontains0(size);
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
          if(val!=0)
          {
            if(TypeUtil.checkCastToDouble(val))
            {
              return this.uncheckedcontainsBits(size,Double.doubleToRawLongBits(val));
            }
          }
          else
          {
            return this.uncheckedcontains0(size);
          }
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
          if(val==val)
          {
            return this.uncheckedcontainsBits(size,Double.doubleToRawLongBits(val));
          }
          return this.uncheckedcontainsNaN(size);
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
          return this.uncheckedcontains(size,(val));
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
      public void forEach(final DoubleConsumer action)
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
      public void forEach(final Consumer<? super Double> action)
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
      public double getDouble(final int index)
      {
        final var root=checkModCountAndGetRoot();
        CheckedCollection.checkLo(index);
        CheckedCollection.checkReadHi(index,this.size);
        return (double)root.arr[index+rootOffset];
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
          if(val)
          {
            return this.uncheckedindexOfBits(size,TypeUtil.DBL_TRUE_BITS);
          }
          return this.uncheckedindexOf0(size);
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
          if(val!=0)
          {
            {
              return this.uncheckedindexOfBits(size,Double.doubleToRawLongBits(val));
            }
          }
          else
          {
            return this.uncheckedindexOf0(size);
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
          if(val!=0)
          {
            if(TypeUtil.checkCastToDouble(val))
            {
              return this.uncheckedindexOfBits(size,Double.doubleToRawLongBits(val));
            }
          }
          else
          {
            return this.uncheckedindexOf0(size);
          }
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
          if(val==val)
          {
            return this.uncheckedindexOfBits(size,Double.doubleToRawLongBits(val));
          }
          return this.uncheckedindexOfNaN(size);
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
          return this.uncheckedindexOf(size,(val));
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
          if(val)
          {
            return this.uncheckedlastIndexOfBits(size,TypeUtil.DBL_TRUE_BITS);
          }
          return this.uncheckedlastIndexOf0(size);
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
          if(val!=0)
          {
            {
              return this.uncheckedlastIndexOfBits(size,Double.doubleToRawLongBits(val));
            }
          }
          else
          {
            return this.uncheckedlastIndexOf0(size);
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
          if(val!=0)
          {
            if(TypeUtil.checkCastToDouble(val))
            {
              return this.uncheckedlastIndexOfBits(size,Double.doubleToRawLongBits(val));
            }
          }
          else
          {
            return this.uncheckedlastIndexOf0(size);
          }
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
          if(val==val)
          {
            return this.uncheckedlastIndexOfBits(size,Double.doubleToRawLongBits(val));
          }
          return this.uncheckedlastIndexOfNaN(size);
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
          return this.uncheckedlastIndexOf(size,(val));
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
      public OmniIterator.OfDouble iterator()
      {
        final int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
        return new BidirectionalItr(this,modCount);
      }
      @Override
      public OmniListIterator.OfDouble listIterator()
      {
        final int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
        return new BidirectionalItr(this,modCount);
      }
      @Override
      public OmniListIterator.OfDouble listIterator(final int index)
      {
        final int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
        CheckedCollection.checkLo(index);
        CheckedCollection.checkWriteHi(index,size);
        return new BidirectionalItr(this,modCount,index+rootOffset);
      }
      @Override
      public void put(final int index,final double val)
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
          if(val instanceof Double)
          {
            return uncheckedremoveVal(size,(double)(val));
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
      public boolean removeVal(final int val)
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
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final long val)
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
      public boolean removeVal(final float val)
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
      public boolean removeVal(final double val)
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
      public double removeDoubleAt(int index)
      {
        int modCount;
        final Checked root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        CheckedCollection.checkLo(index);
        final int size;
        CheckedCollection.checkReadHi(index,size=this.size);
        final double[] arr;
        final var removed=(double)(arr=root.arr)[index+=rootOffset];
        eraseIndexHelper(arr,index,--root.size);
        root.modCount=++modCount;
        bubbleUpDecrementSize(parent);
        this.modCount=modCount;
        this.size=size-1;
        return removed;
      }
      @Override
      public boolean removeIf(final DoublePredicate filter)
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
      public void replaceAll(final DoubleUnaryOperator operator)
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
      public void sort(final DoubleComparator sorter)
      {
        final var root=this.root;
        final int size;
        if(1<(size=this.size))
        {
          int modCount=this.modCount;
          try
          {
            final int rootOffset;DoubleSortUtil.uncheckedcomparatorSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter);
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
          DoubleSortUtil.uncheckedreverseSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
          DoubleSortUtil.uncheckedsort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
              root.modCount=++modCount;
              bubbleUpIncrementModCount(parent);
              this.modCount=modCount;
        }
      }
      @Override
      public boolean removeIf(final Predicate<? super Double> filter)
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
      public void replaceAll(final UnaryOperator<Double> operator)
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
      public void sort(final Comparator<? super Double> sorter)
      {
        final var root=this.root;
        final int size;
        if(1<(size=this.size))
        {
          int modCount=this.modCount;
          try
          {
            final int rootOffset;DoubleSortUtil.uncheckedcomparatorSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter::compare);
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
      public double set(int index,final double val)
      {
        final var root=checkModCountAndGetRoot();
        CheckedCollection.checkLo(index);
        CheckedCollection.checkReadHi(index,this.size);
        final double[] arr;
        final var oldVal=(double)(arr=root.arr)[index+=rootOffset];
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
      public OmniList.OfDouble subList(final int fromIndex,final int toIndex)
      {
        final var root=checkModCountAndGetRoot();
        CheckedCollection.checkSubListRange(fromIndex,toIndex,this.size);
        return new SubList(root,this,this.rootOffset+fromIndex,toIndex-fromIndex);
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
      public Double[] toArray()
      {
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0)
        {
          final Double[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new Double[size],0,size);
          return dst;
        }
        return OmniArray.OfDouble.DEFAULT_BOXED_ARR;
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
      private boolean uncheckedcontains(final int size,final double val)
      {
        if(val==(val))
        {
          return uncheckedcontainsBits(size,Double.doubleToRawLongBits(val));
        }
        return uncheckedcontainsNaN(size);
      }
      private int uncheckedindexOf(final int size,final double val)
      {
        if(val==(val))
        {
          return uncheckedindexOfBits(size,Double.doubleToRawLongBits(val));
        }
        return uncheckedindexOfNaN(size);
      }
      private int uncheckedlastIndexOf(final int size,final double val)
      {
        if(val==(val))
        {
          return uncheckedlastIndexOfBits(size,Double.doubleToRawLongBits(val));
        }
        return uncheckedlastIndexOfNaN(size);
      }
      private boolean uncheckedremoveVal(final int size,final double val)
      {
        if(val==(val))
        {
          return uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
        }
        return uncheckedremoveValNaN(size);
      }
      private boolean uncheckedcontainsBits(final int size,final long bits)
      { 
        final int rootOffset;
        return DoubleArrSeq.uncheckedcontainsBits(checkModCountAndGetRoot().arr,rootOffset=this.rootOffset,rootOffset+size,bits);
      }
      private boolean uncheckedcontains0(final int size)
      { 
        final int rootOffset;
        return DoubleArrSeq.uncheckedcontains0(checkModCountAndGetRoot().arr,rootOffset=this.rootOffset,rootOffset+size);
      }
      private boolean uncheckedcontainsNaN(final int size)
      { 
        final int rootOffset;
        return DoubleArrSeq.uncheckedcontainsNaN(checkModCountAndGetRoot().arr,rootOffset=this.rootOffset,rootOffset+size);
      }
      private int uncheckedindexOfBits(final int size,final long bits)
      { 
        final int rootOffset;
        return DoubleArrSeq.uncheckedindexOfBits(checkModCountAndGetRoot().arr,rootOffset=this.rootOffset,rootOffset+size,bits);
      }
      private int uncheckedindexOf0(final int size)
      { 
        final int rootOffset;
        return DoubleArrSeq.uncheckedindexOf0(checkModCountAndGetRoot().arr,rootOffset=this.rootOffset,rootOffset+size);
      }
      private int uncheckedindexOfNaN(final int size)
      { 
        final int rootOffset;
        return DoubleArrSeq.uncheckedindexOfNaN(checkModCountAndGetRoot().arr,rootOffset=this.rootOffset,rootOffset+size);
      }
      private int uncheckedlastIndexOfBits(final int size,final long bits)
      { 
        final int rootOffset;
        return DoubleArrSeq.uncheckedlastIndexOfBits(checkModCountAndGetRoot().arr,rootOffset=this.rootOffset,rootOffset+size,bits);
      }
      private int uncheckedlastIndexOf0(final int size)
      { 
        final int rootOffset;
        return DoubleArrSeq.uncheckedlastIndexOf0(checkModCountAndGetRoot().arr,rootOffset=this.rootOffset,rootOffset+size);
      }
      private int uncheckedlastIndexOfNaN(final int size)
      { 
        final int rootOffset;
        return DoubleArrSeq.uncheckedlastIndexOfNaN(checkModCountAndGetRoot().arr,rootOffset=this.rootOffset,rootOffset+size);
      }
      private boolean uncheckedremoveValBits(int size,final long bits)
      {
        int modCount;
        final Checked root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        final var arr=root.arr;int index;final int bound=(index=this.rootOffset)+(--size);
        for(;bits!=Double.doubleToRawLongBits(arr[index]);)
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
      private boolean uncheckedremoveVal0(int size)
      {
        int modCount;
        final Checked root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        final var arr=root.arr;int index;final int bound=(index=this.rootOffset)+(--size);
        for(;0!=(arr[index]);)
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
      private boolean uncheckedremoveValNaN(int size)
      {
        int modCount;
        final Checked root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        final var arr=root.arr;int index;final int bound=(index=this.rootOffset)+(--size);
        for(;Double.isNaN(arr[index]);)
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
      private boolean uncheckedRemoveIf(int size,final DoublePredicate filter)
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
            if(filter.test((double)arr[srcOffset]))
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
          this.size=size-(size=((DoubleArrSeq)root).finalizeSubListBatchRemove(arr,dstOffset,srcBound));
          bubbleUpDecrementSize(parent,size);
        }
        catch(final RuntimeException e)
        {
          throw CheckedCollection.checkModCount(modCount,root.modCount,e);
        }
        return true;
      }
      private static class BidirectionalItr implements OmniListIterator.OfDouble
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
        public void add(final double val)
        {
          final SubList parent;
          final Checked root;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
          final int rootSize;
          final int cursor=this.cursor;
          if((rootSize=root.size)!=0)
          {
            ((DoubleArrSeq)root).uncheckedInsert(cursor,val,rootSize);
          }
          else
          {
            ((DoubleArrSeq)root).uncheckedInit(val);
          }
          bubbleUpIncrementSize(parent.parent);
          parent.modCount=++modCount;
          root.modCount=modCount;
          ++parent.size;
          this.cursor=cursor+1;
          this.lastRet=-1;
        }
        @Override
        public void forEachRemaining(final DoubleConsumer action)
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
        public void forEachRemaining(final Consumer<? super Double> action)
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
        public double nextDouble()
        {
          final Checked root;
          final SubList parent;
          CheckedCollection.checkModCount(modCount,(root=(parent=this.parent).root).modCount);
          final int cursor;
          if((cursor=this.cursor)!=parent.rootOffset+parent.size)
          {
            lastRet=cursor;
            this.cursor=cursor+1;
            return (double)root.arr[cursor];
          }
          throw new NoSuchElementException();
        }
        @Override
        public int nextIndex()
        {
          return cursor-parent.rootOffset;
        }
        @Override
        public double previousDouble()
        {
          final Checked root;
          final SubList  parent;
          CheckedCollection.checkModCount(modCount,(root=(parent=this.parent).root).modCount);
          int cursor;
          if((cursor=this.cursor)!=parent.rootOffset)
          {
            lastRet=--cursor;
            this.cursor=cursor;
            return (double)root.arr[cursor];
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
        public void set(final double val)
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
  public static class CheckedStack extends Checked implements OmniStack.OfDouble
  {
    public CheckedStack()
    {
      super();
    }
    public CheckedStack(final int capacity)
    {
      super(capacity);
    }
    public CheckedStack(final int size,final double[] arr)
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
      return new CheckedStack(size,arr);
    }
    @Override
    public boolean equals(final Object val)
    {
      //TODO implements equals method
      return false;
    }
    @Override
    public OmniIterator.OfDouble iterator()
    {
      return new CheckedDescendingItr(this);
    }
        @Override
        public double pollDouble()
        {
          final int size;
          if((size=this.size)!=0)
          {
            ++modCount;
            return (double)(((DoubleArrSeq)this).uncheckedPop(size-1));
          }
          return Double.NaN;
        }
        @Override
        public Double poll()
        {
          final int size;
          if((size=this.size)!=0)
          {
            ++modCount;
            return (Double)(((DoubleArrSeq)this).uncheckedPop(size-1));
          }
          return null;
        }
    @Override
    void uncheckedCopyInto(final double[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Object[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Double[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedForEach(final int size,final DoubleConsumer action)
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
    boolean uncheckedremoveVal0(int size)
    {
      final var arr=this.arr;int index;
      for(index=--size;0!=(arr[index]);--index)
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
    boolean uncheckedremoveValBits(int size,final long bits)
    {
      final var arr=this.arr;int index;
      for(index=--size;bits!=Double.doubleToRawLongBits(arr[index]);--index)
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
    boolean uncheckedremoveValNaN(int size)
    {
      final var arr=this.arr;int index;
      for(index=--size;!Double.isNaN(arr[index]);--index)
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
    private static class CheckedDescendingItr extends AbstractDoubleItr implements OmniIterator.OfDouble
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
      public void forEachRemaining(DoubleConsumer action)
      {
        final int cursor;
        if((cursor=this.cursor)!=0)
        {
          uncheckedForEachRemaining(cursor,action);
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Double> action)
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
      public double nextDouble()
      {
        final CheckedStack root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        int cursor;
        if((cursor=this.cursor)!=0)
        {
          lastRet=--cursor;
          this.cursor=cursor;
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
      private void uncheckedForEachRemaining(int cursor,DoubleConsumer action)
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
    @Override
    boolean uncheckedRemoveIf(int size,final DoublePredicate filter)
    {
      int srcOffset;
      srcOffset=0;
      while(!filter.test((double)arr[srcOffset]))
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
  public static class UncheckedList extends Unchecked implements OmniList.OfDouble
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
    @Override
    public OmniIterator.OfDouble iterator()
    {
      return new AscendingItr(this);
    }
    @Override
    public OmniListIterator.OfDouble listIterator()
    {
      return new BidirectionalItr(this);
    }
    @Override
    public OmniListIterator.OfDouble listIterator(final int index)
    {
      return new BidirectionalItr(this,index);
    }
    @Override
    public void put(final int index,final double val)
    {
      arr[index]=val;
    }
    @Override
    public void replaceAll(final DoubleUnaryOperator operator)
    {
      final int size;
      if(0!=((size=this.size)))
      {
        DoubleArrSeq.uncheckedreplaceAll(arr,0,size,operator);
      }
    }
    @Override
    public void reverseSort()
    {
      final int size;
      if(1<((size=this.size)))
      {
        DoubleSortUtil.uncheckedreverseSort(arr,0,size-1);
      }
    }
    @Override
    public void sort()
    {
      final int size;
      if(1<((size=this.size)))
      {
        DoubleSortUtil.uncheckedsort(arr,0,size-1);
      }
    }
    @Override
    public void sort(final DoubleComparator sorter)
    {
      final int size;
      if(1<((size=this.size)))
      {
        DoubleSortUtil.uncheckedcomparatorSort(arr,0,size-1,sorter);
      }
    }
    @Override
    public void replaceAll(final UnaryOperator<Double> operator)
    {
      final int size;
      if(0!=((size=this.size)))
      {
        DoubleArrSeq.uncheckedreplaceAll(arr,0,size,operator::apply);
      }
    }
    @Override
    public void sort(final Comparator<? super Double> sorter)
    {
      final int size;
      if(1<((size=this.size)))
      {
        DoubleSortUtil.uncheckedcomparatorSort(arr,0,size-1,sorter::compare);
      }
    }
    @Override
    public OmniList.OfDouble subList(final int fromIndex,final int toIndex)
    {
      return new SubList(this,fromIndex,toIndex-fromIndex);
    }
    @Override
    void uncheckedCopyInto(final double[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Object[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Double[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedForEach(final int size,final DoubleConsumer action)
    {
      uncheckedForwardForEach(arr,0,size,action);
    }
    @Override
    int uncheckedHashCode(final int size)
    {
      return forwardHashCode(arr,0,size);
    }
    @Override
    boolean uncheckedremoveVal0(int size)
    {
      final var arr=this.arr;int index;
      for(index=--size;0!=(arr[index]);--index)
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
    boolean uncheckedremoveValBits(int size,final long bits)
    {
      final var arr=this.arr;int index;
      for(index=--size;bits!=Double.doubleToRawLongBits(arr[index]);--index)
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
    boolean uncheckedremoveValNaN(int size)
    {
      final var arr=this.arr;int index;
      for(index=--size;!Double.isNaN(arr[index]);--index)
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
    private static class AscendingItr extends AbstractDoubleItr implements OmniIterator.OfDouble
    {
      private transient final UncheckedList root;
      private transient int cursor;
      private AscendingItr(UncheckedList root)
      {
        this.root=root;
      }
      @Override
      public void forEachRemaining(DoubleConsumer action)
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
      public void forEachRemaining(Consumer<? super Double> action)
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
      public double nextDouble()
      {
        return (double)root.arr[cursor++];
      }
      @Override
      public void remove()
      {
        final UncheckedList root;
        eraseIndexHelper((root=this.root).arr,--cursor,--root.size);
      }
    }
    private static class BidirectionalItr implements OmniListIterator.OfDouble
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
      public void add(final double val)
      {
        final DoubleArrSeq root;
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
      public void forEachRemaining(DoubleConsumer action)
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
      public void forEachRemaining(Consumer<? super Double> action)
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
      public double nextDouble()
      {
        final int lastRet;
        this.lastRet=lastRet=cursor++;
        return (double)root.arr[lastRet];
      }
      @Override
      public int nextIndex()
      {
        return cursor;
      }
      @Override
      public double previousDouble()
      {
        final int lastRet;
        this.lastRet=lastRet=--cursor;
        return (double)root.arr[lastRet];
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
      public void set(double val)
      {
        root.arr[lastRet]=val;
      }
    }
    private static class SubList extends AbstractDoubleList implements OmniList.OfDouble
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
      public boolean add(final double val)
      {
        final DoubleArrSeq root;
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
      public void add(final int index,final double val)
      {
        final DoubleArrSeq root;
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
          final double[] arr;
          ArrCopy.semicheckedCopy(arr=root.arr,size+(size=rootOffset),arr,size,newRootSize-size);
        }
      }
      @Override
      public Object clone()
      {
        final double[] arr;
        final int size;
        if((size=this.size)!=0)
        {
          ArrCopy.uncheckedCopy(root.arr,rootOffset,arr=new double[size],0,size);
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
          if(val instanceof Double)
          {
            return uncheckedcontains(size,(double)(val));
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
          if(val instanceof Double)
          {
            return uncheckedindexOf(size,(double)(val));
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
          if(val instanceof Double)
          {
            return uncheckedlastIndexOf(size,(double)(val));
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
          if(val)
          {
            return this.uncheckedcontainsBits(size,TypeUtil.DBL_TRUE_BITS);
          }
          return this.uncheckedcontains0(size);
        }
        return false;
      }
      @Override
      public boolean contains(final int val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            {
              return this.uncheckedcontainsBits(size,Double.doubleToRawLongBits(val));
            }
          }
          else
          {
            return this.uncheckedcontains0(size);
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
          if(val!=0)
          {
            if(TypeUtil.checkCastToDouble(val))
            {
              return this.uncheckedcontainsBits(size,Double.doubleToRawLongBits(val));
            }
          }
          else
          {
            return this.uncheckedcontains0(size);
          }
        }
        return false;
      }
      @Override
      public boolean contains(final float val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val==val)
          {
            return this.uncheckedcontainsBits(size,Double.doubleToRawLongBits(val));
          }
          return this.uncheckedcontainsNaN(size);
        }
        return false;
      }
      @Override
      public boolean contains(final double val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedcontains(size,(val));
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
      public void forEach(DoubleConsumer action)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          uncheckedForwardForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size,action);
        }
      }
      @Override
      public void forEach(Consumer<? super Double> action)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          uncheckedForwardForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size,action::accept);
        }
      }
      @Override
      public double getDouble(int index)
      {
        return (double)root.arr[index+rootOffset];
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
          if(val)
          {
            return this.uncheckedindexOfBits(size,TypeUtil.DBL_TRUE_BITS);
          }
          return this.uncheckedindexOf0(size);
        }
        return -1;
      }
      @Override
      public int indexOf(final int val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            {
              return this.uncheckedindexOfBits(size,Double.doubleToRawLongBits(val));
            }
          }
          else
          {
            return this.uncheckedindexOf0(size);
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
          if(val!=0)
          {
            if(TypeUtil.checkCastToDouble(val))
            {
              return this.uncheckedindexOfBits(size,Double.doubleToRawLongBits(val));
            }
          }
          else
          {
            return this.uncheckedindexOf0(size);
          }
        }
        return -1;
      }
      @Override
      public int indexOf(final float val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val==val)
          {
            return this.uncheckedindexOfBits(size,Double.doubleToRawLongBits(val));
          }
          return this.uncheckedindexOfNaN(size);
        }
        return -1;
      }
      @Override
      public int indexOf(final double val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedindexOf(size,(val));
        }
        return -1;
      }
      @Override
      public int lastIndexOf(final boolean val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val)
          {
            return this.uncheckedlastIndexOfBits(size,TypeUtil.DBL_TRUE_BITS);
          }
          return this.uncheckedlastIndexOf0(size);
        }
        return -1;
      }
      @Override
      public int lastIndexOf(final int val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            {
              return this.uncheckedlastIndexOfBits(size,Double.doubleToRawLongBits(val));
            }
          }
          else
          {
            return this.uncheckedlastIndexOf0(size);
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
          if(val!=0)
          {
            if(TypeUtil.checkCastToDouble(val))
            {
              return this.uncheckedlastIndexOfBits(size,Double.doubleToRawLongBits(val));
            }
          }
          else
          {
            return this.uncheckedlastIndexOf0(size);
          }
        }
        return -1;
      }
      @Override
      public int lastIndexOf(final float val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val==val)
          {
            return this.uncheckedlastIndexOfBits(size,Double.doubleToRawLongBits(val));
          }
          return this.uncheckedlastIndexOfNaN(size);
        }
        return -1;
      }
      @Override
      public int lastIndexOf(final double val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedlastIndexOf(size,(val));
        }
        return -1;
      }
      @Override
      public OmniIterator.OfDouble iterator()
      {
        return new AscendingItr(this);
      }
      @Override
      public OmniListIterator.OfDouble listIterator()
      {
        return new BidirectionalItr(this);
      }
      @Override
      public OmniListIterator.OfDouble listIterator(int index)
      {
        return new BidirectionalItr(this,index+rootOffset);
      }
      @Override
      public void put(int index,double val)
      {
        root.arr[index+rootOffset]=val;
      }     
      @Override
      public boolean remove(Object val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val instanceof Double)
          {
            return uncheckedremoveVal(size,(double)(val));
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
          if(val)
          {
            return this.uncheckedremoveValBits(size,TypeUtil.DBL_TRUE_BITS);
          }
          return this.uncheckedremoveVal0(size);
        }
        return false;
      }
      @Override
      public boolean removeVal(final int val)
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
        return false;
      }
      @Override
      public boolean removeVal(final long val)
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
      public boolean removeVal(final float val)
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
      public boolean removeVal(final double val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,(val));
        }
        return false;
      }
      @Override
      public double removeDoubleAt(int index)
      {
        final Unchecked root;
        final double[] arr;
        final var removed=(double)(arr=(root=this.root).arr)[index+=rootOffset];
        eraseIndexHelper(arr,index,--root.size);
        bubbleUpDecrementSize(parent);
        --size;
        return removed;
      }
      @Override
      public boolean removeIf(final DoublePredicate filter)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return uncheckedRemoveIf(size,filter);
        }
        return false;
      }
      @Override
      public void replaceAll(final DoubleUnaryOperator operator)
      {
        final int size;
        if(0!=((size=this.size)))
        {
          final int rootOffset;
          DoubleArrSeq.uncheckedreplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);
        }
      }
      @Override
      public void reverseSort()
      {
        final int size;
        if(1<((size=this.size)))
        {
          final int rootOffset;
          DoubleSortUtil.uncheckedreverseSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
        }
      }
      @Override
      public void sort()
      {
        final int size;
        if(1<((size=this.size)))
        {
          final int rootOffset;
          DoubleSortUtil.uncheckedsort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
        }
      }
      @Override
      public void sort(final DoubleComparator sorter)
      {
        final int size;
        if(1<((size=this.size)))
        {
          final int rootOffset;
          DoubleSortUtil.uncheckedcomparatorSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter);
        }
      }
      @Override
      public void replaceAll(final UnaryOperator<Double> operator)
      {
        final int size;
        if(0!=((size=this.size)))
        {
          final int rootOffset;
          DoubleArrSeq.uncheckedreplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator::apply);
        }
      }
      @Override
      public void sort(final Comparator<? super Double> sorter)
      {
        final int size;
        if(1<((size=this.size)))
        {
          final int rootOffset;
          DoubleSortUtil.uncheckedcomparatorSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter::compare);
        }
      }
      @Override
      public boolean removeIf(final Predicate<? super Double> filter)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return uncheckedRemoveIf(size,filter::test);
        }
        return false;
      }
      @Override
      public double set(int index,double val)
      {
        final double[] arr;
        final var oldVal=(double)(arr=root.arr)[index+=rootOffset];
        arr[index]=val;
        return oldVal;
      }
      @Override
      public OmniList.OfDouble subList(int fromIndex,int toIndex)
      {
        return new SubList(root,this,this.rootOffset+fromIndex,toIndex-fromIndex);
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
      public Double[] toArray()
      {
        final int size;
        if((size=this.size)!=0)
        {
          final Double[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new Double[size],0,size);
          return dst;
        }
        return OmniArray.OfDouble.DEFAULT_BOXED_ARR;
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
      private boolean uncheckedcontainsBits(int size,long bits)
      { 
        final int rootOffset;
        return DoubleArrSeq.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size,bits);
      }
      private boolean uncheckedcontainsNaN(int size)
      { 
        final int rootOffset;
        return DoubleArrSeq.uncheckedcontainsNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }
      private boolean uncheckedcontains0(int size)
      { 
        final int rootOffset;
        return DoubleArrSeq.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }
      private boolean uncheckedcontains(final int size,final double val)
      {
        if(val==(val))
        {
          return uncheckedcontainsBits(size,Double.doubleToRawLongBits(val));
        }
        return uncheckedcontainsNaN(size);
      }
      private int uncheckedindexOfBits(int size,long bits)
      { 
        final int rootOffset;
        return DoubleArrSeq.uncheckedindexOfBits(root.arr,rootOffset=this.rootOffset,rootOffset+size,bits);
      }
      private int uncheckedindexOfNaN(int size)
      { 
        final int rootOffset;
        return DoubleArrSeq.uncheckedindexOfNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }
      private int uncheckedindexOf0(int size)
      { 
        final int rootOffset;
        return DoubleArrSeq.uncheckedindexOf0(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }
      private int uncheckedindexOf(final int size,final double val)
      {
        if(val==(val))
        {
          return uncheckedindexOfBits(size,Double.doubleToRawLongBits(val));
        }
        return uncheckedindexOfNaN(size);
      }
      private int uncheckedlastIndexOfBits(int size,long bits)
      { 
        final int rootOffset;
        return DoubleArrSeq.uncheckedlastIndexOfBits(root.arr,rootOffset=this.rootOffset,rootOffset+size,bits);
      }
      private int uncheckedlastIndexOfNaN(int size)
      { 
        final int rootOffset;
        return DoubleArrSeq.uncheckedlastIndexOfNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }
      private int uncheckedlastIndexOf0(int size)
      { 
        final int rootOffset;
        return DoubleArrSeq.uncheckedlastIndexOf0(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }
      private int uncheckedlastIndexOf(final int size,final double val)
      {
        if(val==(val))
        {
          return uncheckedlastIndexOfBits(size,Double.doubleToRawLongBits(val));
        }
        return uncheckedlastIndexOfNaN(size);
      }
      private boolean uncheckedRemoveIf(int size,final DoublePredicate filter)
      {
        final Unchecked root;
        int srcOffset;
        final int srcBound=(srcOffset=rootOffset)+size;
        final var arr=(root=this.root).arr;
        while(!filter.test((double)arr[srcOffset]))
        {
          if(++srcOffset==srcBound)
          {
            return false;
          }
        }
        this.size=size-(size=((DoubleArrSeq)root).finalizeSubListBatchRemove(arr,pullSurvivorsDown(arr,srcOffset,srcBound-1,filter),srcBound));
        bubbleUpDecrementSize(parent,size);
        return true;
      }
      private boolean uncheckedremoveVal(final int size,final double val)
      {
        if(val==(val))
        {
          return uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
        }
        return uncheckedremoveValNaN(size);
      }
      private boolean uncheckedremoveValBits(int size,long bits)
      {
        final Unchecked root;final var arr=(root=this.root).arr;int index;final int bound=(index=this.rootOffset)+(--size);
        for(;bits!=Double.doubleToRawLongBits(arr[index]);)
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
      private boolean uncheckedremoveVal0(int size)
      {
        final Unchecked root;final var arr=(root=this.root).arr;int index;final int bound=(index=this.rootOffset)+(--size);
        for(;0!=(arr[index]);)
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
      private boolean uncheckedremoveValNaN(int size)
      {
        final Unchecked root;final var arr=(root=this.root).arr;int index;final int bound=(index=this.rootOffset)+(--size);
        for(;!Double.isNaN(arr[index]);)
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
      private static class AscendingItr extends AbstractDoubleItr implements OmniIterator.OfDouble
      {
        private transient final SubList parent;
        private transient int cursor;
        private AscendingItr(SubList parent)
        {
           this.parent=parent;
           this.cursor=parent.rootOffset;
        }
        @Override
        public void forEachRemaining(DoubleConsumer action)
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
        public void forEachRemaining(Consumer<? super Double> action)
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
        public double nextDouble()
        {
          return (double)parent.root.arr[cursor++];
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
      private static class BidirectionalItr implements OmniListIterator.OfDouble
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
        public void add(final double val)
        {
          final SubList parent;
          final DoubleArrSeq root;
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
        public void forEachRemaining(DoubleConsumer action)
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
        public void forEachRemaining(Consumer<? super Double> action)
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
        public double nextDouble()
        {
          final int lastRet;
          this.lastRet=lastRet=cursor++;
          return (double)parent.root.arr[lastRet];
        }
        @Override
        public int nextIndex()
        {
          return cursor-parent.rootOffset;
        }
        @Override
        public double previousDouble()
        {
          final int lastRet;
          this.lastRet=lastRet=--cursor;
          return (double)parent.root.arr[lastRet];
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
        public void set(double val)
        {
          parent.root.arr[lastRet]=val;
        }
      }
    }
  }
  public static class UncheckedStack extends Unchecked implements OmniStack.OfDouble
  {
    public UncheckedStack()
    {
      super();
    }
    public UncheckedStack(final int capacity)
    {
      super(capacity);
    }
    public UncheckedStack(final int size,final double[] arr)
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
      return new UncheckedStack(size,arr);
    }
    @Override
    public boolean equals(final Object val)
    {
      //TODO implements equals method
      return false;
    }
    @Override
    public OmniIterator.OfDouble iterator()
    {
      return new UncheckedDescendingItr(this);
    }
        @Override
        public double pollDouble()
        {
          final int size;
          if((size=this.size)!=0)
          {
            return (double)(((DoubleArrSeq)this).uncheckedPop(size-1));
          }
          return Double.NaN;
        }
        @Override
        public Double poll()
        {
          final int size;
          if((size=this.size)!=0)
          {
            return (Double)(((DoubleArrSeq)this).uncheckedPop(size-1));
          }
          return null;
        }
    @Override
    void uncheckedCopyInto(final double[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Object[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Double[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedForEach(final int size,final DoubleConsumer action)
    {
      uncheckedReverseForEach(arr,0,size,action);
    }
    @Override
    int uncheckedHashCode(final int size)
    {
      return reverseHashCode(arr,0,size);
    }
    @Override
    boolean uncheckedremoveVal0(int size)
    {
      final var arr=this.arr;int index;
      for(index=--size;0!=(arr[index]);--index)
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
    boolean uncheckedremoveValBits(int size,final long bits)
    {
      final var arr=this.arr;int index;
      for(index=--size;bits!=Double.doubleToRawLongBits(arr[index]);--index)
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
    boolean uncheckedremoveValNaN(int size)
    {
      final var arr=this.arr;int index;
      for(index=--size;!Double.isNaN(arr[index]);--index)
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
    private static class UncheckedDescendingItr extends AbstractDoubleItr implements OmniIterator.OfDouble
    {
      private transient final UncheckedStack root;
      private transient int cursor;
      private UncheckedDescendingItr(UncheckedStack root)
      {
        this.root=root;
        cursor=root.size;
      }
      @Override
      public void forEachRemaining(DoubleConsumer action)
      {
        final int cursor;
        if((cursor=this.cursor)!=0)
        {
          uncheckedReverseForEach(root.arr,0,cursor,action);
          this.cursor=0;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Double> action)
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
      public double nextDouble()
      {
        return (double)root.arr[--cursor];
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
