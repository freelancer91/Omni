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
import omni.util.SortUtil;
import omni.util.HashUtils;
import omni.impl.seq.AbstractFloatList;
import omni.impl.AbstractFloatItr;
import omni.util.BitSetUtils;
import omni.util.TypeUtil;
import omni.function.FloatComparator;
import omni.function.FloatUnaryOperator;
import omni.function.FloatConsumer;
import omni.function.FloatPredicate;
public abstract class FloatArrSeq extends AbstractFloatList implements OmniCollection.OfFloat
{
  private static void eraseIndexHelper(float[] arr,int index,int newSize)
  {
    ArrCopy.semicheckedCopy(arr,index+1,arr,index,newSize-index);
  }
  static boolean uncheckedcontains0(float[] arr,int offset,int bound)
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
  static boolean uncheckedcontainsBits(float[] arr,int offset,int bound,int bits)
  {
    for(;bits!=Float.floatToRawIntBits(arr[offset]);)
    {
      if(++offset==bound)
      {
        return false;
      }
    }
    return true;
  }
  static boolean uncheckedcontainsNaN(float[] arr,int offset,int bound)
  {
    for(;!Float.isNaN(arr[offset]);)
    {
      if(++offset==bound)
      {
        return false;
      }
    }
    return true;
  }
  private static int uncheckedindexOf0(float[] arr,int offset,int bound)
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
  private static int uncheckedindexOfBits(float[] arr,int offset,int bound,int bits)
  {
    int index;
    for(index=offset;bits!=Float.floatToRawIntBits(arr[index]);)
    {
      if(++index==bound)
      {
        return -1;
      }
    }
    return index-offset;
  }
  private static int uncheckedindexOfNaN(float[] arr,int offset,int bound)
  {
    int index;
    for(index=offset;!Float.isNaN(arr[index]);)
    {
      if(++index==bound)
      {
        return -1;
      }
    }
    return index-offset;
  }
  private static int uncheckedindexOf0(float[] arr,int bound)
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
  private static int uncheckedindexOfBits(float[] arr,int bound,int bits)
  {
    int index;
    for(index=0;bits!=Float.floatToRawIntBits(arr[index]);)
    {
      if(++index==bound)
      {
        return -1;
      }
    }
    return index;
  }
  private static int uncheckedindexOfNaN(float[] arr,int bound)
  {
    int index;
    for(index=0;!Float.isNaN(arr[index]);)
    {
      if(++index==bound)
      {
        return -1;
      }
    }
    return index;
  }
  private static int uncheckedlastIndexOf0(float[] arr,int offset,int bound)
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
  private static int uncheckedlastIndexOfBits(float[] arr,int offset,int bound,int bits)
  {
    for(;bits!=Float.floatToRawIntBits(arr[--bound]);)
    {
      if(bound==offset)
      {
        return -1;
      }
    }
    return bound-offset;
  }
  private static int uncheckedlastIndexOfNaN(float[] arr,int offset,int bound)
  {
    for(;!Float.isNaN(arr[--bound]);)
    {
      if(bound==offset)
      {
        return -1;
      }
    }
    return bound-offset;
  }
  private static int uncheckedlastIndexOf0(float[] arr,int bound)
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
  private static int uncheckedlastIndexOfBits(float[] arr,int bound,int bits)
  {
    for(;bits!=Float.floatToRawIntBits(arr[--bound]);)
    {
      if(bound==0)
      {
        return -1;
      }
    }
    return bound;
  }
  private static int uncheckedlastIndexOfNaN(float[] arr,int bound)
  {
    for(;!Float.isNaN(arr[--bound]);)
    {
      if(bound==0)
      {
        return -1;
      }
    }
    return bound;
  }
  private static int uncheckedsearch0(float[] arr,int bound)
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
  private static int uncheckedsearchBits(float[] arr,int bound,int bits)
  {
    int index;
    for(index=bound-1;bits!=Float.floatToRawIntBits(arr[index]);--index)
    {
      if(index==0)
      {
        return -1;
      }
    }
    return bound-index;
  }
  private static int uncheckedsearchNaN(float[] arr,int bound)
  {
    int index;
    for(index=bound-1;!Float.isNaN(arr[index]);--index)
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
  (float[] arr
  ,int offset
  ,int bound,float val)
  {
    if(val==val)
    {
      return uncheckedcontainsBits(arr
      ,0
      ,bound,Float.floatToRawIntBits(val));
    }
    return uncheckedcontainsNaN(arr
    ,0
    ,bound);
  }
  private static
  int
  uncheckedsearch
  (float[] arr
  ,int bound,float val)
  {
    if(val==val)
    {
      return uncheckedsearchBits(arr
      ,bound,Float.floatToRawIntBits(val));
    }
    return uncheckedsearchNaN(arr
    ,bound);
  }
  private static
  int
  uncheckedindexOf
  (float[] arr
  ,int bound,float val)
  {
    if(val==val)
    {
      return uncheckedindexOfBits(arr
      ,bound,Float.floatToRawIntBits(val));
    }
    return uncheckedindexOfNaN(arr
    ,bound);
  }
  private static
  int
  uncheckedlastIndexOf
  (float[] arr
  ,int bound,float val)
  {
    if(val==val)
    {
      return uncheckedlastIndexOfBits(arr
      ,bound,Float.floatToRawIntBits(val));
    }
    return uncheckedlastIndexOfNaN(arr
    ,bound);
  }
  private static
  boolean
  uncheckedcontainsRawInt
  (float[] arr
  ,int offset
  ,int bound,float val)
  {
    if(val!=0)
    {
      return uncheckedcontainsBits(arr
      ,0
      ,bound,Float.floatToRawIntBits(val));
    }
    return uncheckedcontains0(arr
    ,0
    ,bound);
  }
  private static
  int
  uncheckedsearchRawInt
  (float[] arr
  ,int bound,float val)
  {
    if(val!=0)
    {
      return uncheckedsearchBits(arr
      ,bound,Float.floatToRawIntBits(val));
    }
    return uncheckedsearch0(arr
    ,bound);
  }
  private static
  int
  uncheckedindexOfRawInt
  (float[] arr
  ,int bound,float val)
  {
    if(val!=0)
    {
      return uncheckedindexOfBits(arr
      ,bound,Float.floatToRawIntBits(val));
    }
    return uncheckedindexOf0(arr
    ,bound);
  }
  private static
  int
  uncheckedlastIndexOfRawInt
  (float[] arr
  ,int bound,float val)
  {
    if(val!=0)
    {
      return uncheckedlastIndexOfBits(arr
      ,bound,Float.floatToRawIntBits(val));
    }
    return uncheckedlastIndexOf0(arr
    ,bound);
  }
  private static  void uncheckedreplaceAll(float[] arr,int offset,int bound,FloatUnaryOperator operator)
  {
    do
    {
      arr[offset]=operator.applyAsFloat((float)arr[offset]);
    }
    while(++offset!=bound);
  }
  static  void uncheckedForwardForEach(float[] arr,int offset,int bound,FloatConsumer action)
  {
    do
    {
      action.accept((float)arr[offset]);
    }
    while(++offset!=bound);
  }
  static  void uncheckedReverseForEach(float[] arr,int offset,int bound,FloatConsumer action)
  {
    do
    {
      action.accept((float)arr[--bound]);
    }
    while(bound!=offset);
  }
  private static int forwardHashCode(float[] arr,int offset,int bound)
  {
    int hash=31+HashUtils.hashFloat(arr[offset]);
    while(++offset!=bound)
    {
      hash=hash*31+HashUtils.hashFloat(arr[offset]);
    }
    return hash;
  }
  private static int reverseHashCode(float[] arr,int offset,int bound)
  {
    int hash=31+HashUtils.hashFloat(arr[offset]);
    while(bound!=offset)
    {
      hash=hash*31+HashUtils.hashFloat(arr[offset]);
    }
    return hash;
  }
  private static void forwardToString(float[] arr,int offset,int bound,StringBuilder builder)
  {
    for(builder.append(arr[offset]);++offset!=bound;builder.append(',').append(' ').append(arr[offset]));
  }
  private static void reverseToString(float[] arr,int offset,int bound,StringBuilder builder)
  {
    for(builder.append(arr[--bound]);bound!=offset;builder.append(',').append(' ').append(arr[--bound]));
  }
  static  int pullSurvivorsUp(float[] arr,int srcBegin,int srcEnd,FloatPredicate filter)
  {
    int dstOffset=srcBegin;
    while(srcBegin!=srcEnd)
    {
      final float v;
      if(!filter.test((float)(v=arr[--srcBegin])))
      {
        arr[dstOffset--]=v;
      }
    }
    return dstOffset;
  }
  static  int pullSurvivorsDown(float[] arr,int srcBegin,int srcEnd,FloatPredicate filter)
  {
    int dstOffset=srcBegin;
    while(srcBegin!=srcEnd)
    {
      final float v;
      if(!filter.test((float)(v=arr[++srcBegin])))
      {
        arr[dstOffset++]=v;
      }
    }
    return dstOffset;
  }
  static  int markSurvivorsAndPullUp(CheckedCollection.AbstractModCountChecker modCountChecker,float[] arr,int srcOffset,int srcBound,FloatPredicate filter)
  {
    int dstOffset=srcOffset;
    for(;;)
    {
      if(--srcOffset==srcBound)
      {
        modCountChecker.checkModCount();
        break;
      }
      final float v;
      if(!filter.test((float)(v=arr[srcOffset])))
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
  static  int markSurvivorsAndPullDown(CheckedCollection.AbstractModCountChecker modCountChecker,float[] arr,int srcOffset,int srcBound,FloatPredicate filter)
  {
    int dstOffset=srcOffset;
    for(;;)
    {
      if(++srcOffset==srcBound)
      {
        modCountChecker.checkModCount();
        break;
      }
      final float v;
      if(!filter.test((float)(v=arr[srcOffset])))
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
  static  int markSurvivorsReverse(float[] arr,long[] survivorSet,int offset,int bound,FloatPredicate filter)
  {
    for(int numSurvivors=0,wordOffset=survivorSet.length-1;;)
    {
      long word=0L,marker=1L<<63;
      do
      {
        if(!filter.test((float)arr[offset]))
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
  static  int markSurvivors(float[] arr,long[] survivorSet,int offset,int bound,FloatPredicate filter)
  {
    for(int numSurvivors=0,wordOffset=0;;)
    {
      long word=0L,marker=1L;
      do
      {
        if(!filter.test((float)arr[offset]))
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
  static int pullSurvivorsUp(float[] arr,long[] survivorSet,int dstOffset,int srcOffset,int numSurvivors)
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
  static int pullSurvivorsDown(float[] arr,long[] survivorSet,int dstOffset,int srcOffset,int numSurvivors)
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
      return;
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
  @Override
  public boolean contains(final Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Float)
      {
        return FloatArrSeq.uncheckedcontains(arr,0,size,(float)(val));
      }
    }
    return false;
  }
  public int indexOf(final Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Float)
      {
        return FloatArrSeq.uncheckedindexOf(arr,size,(float)(val));
      }
    }
    return -1;
  }
  public int lastIndexOf(final Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Float)
      {
        return FloatArrSeq.uncheckedlastIndexOf(arr,size,(float)(val));
      }
    }
    return -1;
  }
  public int search(final Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Float)
      {
        return FloatArrSeq.uncheckedsearch(arr,size,(float)(val));
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
        return FloatArrSeq.uncheckedcontainsBits(arr,0,size,TypeUtil.FLT_TRUE_BITS);
      }
      return FloatArrSeq.uncheckedcontains0(arr,0,size);
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
        if(TypeUtil.checkCastToFloat(val))
        {
          return FloatArrSeq.uncheckedcontainsBits(arr,0,size,Float.floatToRawIntBits(val));
        }
      }
      else
      {
        return FloatArrSeq.uncheckedcontains0(arr,0,size);
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
        if(TypeUtil.checkCastToFloat(val))
        {
          return FloatArrSeq.uncheckedcontainsBits(arr,0,size,Float.floatToRawIntBits(val));
        }
      }
      else
      {
        return FloatArrSeq.uncheckedcontains0(arr,0,size);
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
      return FloatArrSeq.uncheckedcontains(arr,0,size,(val));
    }
    return false;
  }
  @Override
  public boolean contains(final double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final float v;
      if(val==(v=(float)val))
      {
        return FloatArrSeq.uncheckedcontainsBits(arr,0,size,Float.floatToRawIntBits(v));
      }
      else if(v!=v)
      {
        return FloatArrSeq.uncheckedcontainsNaN(arr,0,size);
      }
    }
    return false;
  }
  @Override
  protected boolean containsRawInt(final int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return FloatArrSeq.uncheckedcontainsRawInt(arr,0,size,val);
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
  @Override
  public void forEach(final FloatConsumer action)
  {
    final int size;
    if((size=this.size)!=0)
    {
      uncheckedForEach(size,action);
    }
  }
  @Override
  public void forEach(final Consumer<? super Float> action)
  {
    final int size;
    if((size=this.size)!=0)
    {
      uncheckedForEach(size,action::accept);
    }
  }
  @Override
  public boolean removeIf(final FloatPredicate filter)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedRemoveIf(size,filter);
    }
    return false;
  }
  @Override
  public boolean removeIf(final Predicate<? super Float> filter)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedRemoveIf(size,filter::test);
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
        return FloatArrSeq.uncheckedindexOfBits(arr,size,TypeUtil.FLT_TRUE_BITS);
      }
      return FloatArrSeq.uncheckedindexOf0(arr,size);
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
        if(TypeUtil.checkCastToFloat(val))
        {
          return FloatArrSeq.uncheckedindexOfBits(arr,size,Float.floatToRawIntBits(val));
        }
      }
      else
      {
        return FloatArrSeq.uncheckedindexOf0(arr,size);
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
        if(TypeUtil.checkCastToFloat(val))
        {
          return FloatArrSeq.uncheckedindexOfBits(arr,size,Float.floatToRawIntBits(val));
        }
      }
      else
      {
        return FloatArrSeq.uncheckedindexOf0(arr,size);
      }
    }
    return -1;
  }
  public int indexOf(final float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return FloatArrSeq.uncheckedindexOf(arr,size,(val));
    }
    return -1;
  }
  public int indexOf(final double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final float v;
      if(val==(v=(float)val))
      {
        return FloatArrSeq.uncheckedindexOfBits(arr,size,Float.floatToRawIntBits(v));
      }
      else if(v!=v)
      {
        return FloatArrSeq.uncheckedindexOfNaN(arr,size);
      }
    }
    return -1;
  }
  public int indexOf(final char val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return FloatArrSeq.uncheckedindexOfRawInt(arr,size,val);
      }
    }
    return -1;
  }
  public int indexOf(final short val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return FloatArrSeq.uncheckedindexOfRawInt(arr,size,val);
      }
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
        return FloatArrSeq.uncheckedlastIndexOfBits(arr,size,TypeUtil.FLT_TRUE_BITS);
      }
      return FloatArrSeq.uncheckedlastIndexOf0(arr,size);
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
        if(TypeUtil.checkCastToFloat(val))
        {
          return FloatArrSeq.uncheckedlastIndexOfBits(arr,size,Float.floatToRawIntBits(val));
        }
      }
      else
      {
        return FloatArrSeq.uncheckedlastIndexOf0(arr,size);
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
        if(TypeUtil.checkCastToFloat(val))
        {
          return FloatArrSeq.uncheckedlastIndexOfBits(arr,size,Float.floatToRawIntBits(val));
        }
      }
      else
      {
        return FloatArrSeq.uncheckedlastIndexOf0(arr,size);
      }
    }
    return -1;
  }
  public int lastIndexOf(final float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return FloatArrSeq.uncheckedlastIndexOf(arr,size,(val));
    }
    return -1;
  }
  public int lastIndexOf(final double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final float v;
      if(val==(v=(float)val))
      {
        return FloatArrSeq.uncheckedlastIndexOfBits(arr,size,Float.floatToRawIntBits(v));
      }
      else if(v!=v)
      {
        return FloatArrSeq.uncheckedlastIndexOfNaN(arr,size);
      }
    }
    return -1;
  }
  public int lastIndexOf(final char val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return FloatArrSeq.uncheckedlastIndexOfRawInt(arr,size,val);
      }
    }
    return -1;
  }
  public int lastIndexOf(final short val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return FloatArrSeq.uncheckedlastIndexOfRawInt(arr,size,val);
      }
    }
    return -1;
  }
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
  public Float peek()
  {
    final int size;
    if((size=this.size)!=0)
    {
      return (arr[size-1]);
    }
    return null;
  }
  abstract void uncheckedCopyInto(final Float[] dst,final int length);
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
        return this.uncheckedremoveVal(size,(float)(val));
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
        return this.uncheckedremoveValBits(size,TypeUtil.FLT_TRUE_BITS);
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
        if(TypeUtil.checkCastToFloat(val))
        {
          return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
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
        if(TypeUtil.checkCastToFloat(val))
        {
          return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
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
      return this.uncheckedremoveVal(size,(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(final double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final float v;
      if(val==(v=(float)val))
      {
        return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(v));
      }
      else if(v!=v)
      {
        return this.uncheckedremoveValNaN(size);
      }
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
        return FloatArrSeq.uncheckedsearchBits(arr,size,TypeUtil.FLT_TRUE_BITS);
      }
      return FloatArrSeq.uncheckedsearch0(arr,size);
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
        if(TypeUtil.checkCastToFloat(val))
        {
          return FloatArrSeq.uncheckedsearchBits(arr,size,Float.floatToRawIntBits(val));
        }
      }
      else
      {
        return FloatArrSeq.uncheckedsearch0(arr,size);
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
        if(TypeUtil.checkCastToFloat(val))
        {
          return FloatArrSeq.uncheckedsearchBits(arr,size,Float.floatToRawIntBits(val));
        }
      }
      else
      {
        return FloatArrSeq.uncheckedsearch0(arr,size);
      }
    }
    return -1;
  }
  public int search(final float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return FloatArrSeq.uncheckedsearch(arr,size,(val));
    }
    return -1;
  }
  public int search(final double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final float v;
      if(val==(v=(float)val))
      {
        return FloatArrSeq.uncheckedsearchBits(arr,size,Float.floatToRawIntBits(v));
      }
      else if(v!=v)
      {
        return FloatArrSeq.uncheckedsearchNaN(arr,size);
      }
    }
    return -1;
  }
  public int search(final char val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return FloatArrSeq.uncheckedsearchRawInt(arr,size,val);
      }
    }
    return -1;
  }
  public int search(final short val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return FloatArrSeq.uncheckedsearchRawInt(arr,size,val);
      }
    }
    return -1;
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
  public Float[] toArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final Float[] dst;
      uncheckedCopyInto(dst=new Float[size],size);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_BOXED_ARR;
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
  abstract boolean uncheckedremoveValBits(final int size,final int bits);
  abstract boolean uncheckedremoveValNaN(final int size);
  private boolean uncheckedremoveVal(final int size,final float val)
  {
    if(val==(val))
    {
      return uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
    }
    return uncheckedremoveValNaN(size);
  }
  abstract void uncheckedForEach(final int size,final FloatConsumer action);
  abstract int uncheckedHashCode(final int size);
  abstract boolean uncheckedRemoveIf(final int size,final FloatPredicate filter);
  abstract void uncheckedToString(final int size,final StringBuilder builder);
  private int finalizeSubListBatchRemove(final float[] arr,final int newBound,final int oldBound)
  {
    final int newRootSize,numRemoved;
    size=newRootSize=size-(numRemoved=oldBound-newBound);
    ArrCopy.semicheckedCopy(arr,oldBound,arr,newBound,newRootSize-newBound);
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
    size=1;
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
  public static abstract class Checked extends FloatArrSeq
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
    private Checked(final int size,final float[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean add(final float val)
    {
      ++modCount;
      super.push(val);
      return true;
    }  
    @Override
    public void add(final int index,final float val)
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
    public float getFloat(final int index)
    {
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      return (float)arr[index];
    }
    @Override
    public float popFloat()
    {
      final int size;
      if((size=this.size)!=0)
      {
        ++modCount;
        return (float)super.uncheckedPop(size-1);
      }
      throw new NoSuchElementException();
    }
    @Override
    public void push(final float val)
    {
      ++modCount;
      super.push(val);
    }
    @Override
    public float removeFloatAt(final int index)
    {
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ++modCount;
      final float[] arr;
      final var removed=(float)(arr=this.arr)[index];
      eraseIndexHelper(arr,index,--size);
      this.size=size;
      return removed;
    }
    @Override
    public float set(final int index,final float val)
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
    boolean uncheckedRemoveIf(int size,final FloatPredicate filter)
    {
      int srcOffset;
      final int modCount=this.modCount;
      try
      {
        srcOffset=0;
        final var arr=this.arr;
        for(;;)
        {
          if(filter.test((float)arr[srcOffset]))
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
  public static class CheckedList extends Checked implements OmniList.OfFloat
  {
    public CheckedList()
    {
      super();
    }
    public CheckedList(final int capacity)
    {
      super(capacity);
    }
    public CheckedList(final int size,final float[] arr)
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
      return new CheckedList(size,arr);
    }
    @Override
    public boolean equals(final Object val)
    {
      //TODO implements equals method
      return false;
    }
    @Override
    public OmniIterator.OfFloat iterator()
    {
      return new BidirectionalItr(this);
    }
    @Override
    public OmniListIterator.OfFloat listIterator()
    {
      return new BidirectionalItr(this);
    }
    @Override
    public OmniListIterator.OfFloat listIterator(final int index)
    {
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,this.size);
      return new BidirectionalItr(this,index);
    }
    @Override
    public void put(final int index,final float val)
    {
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      arr[index]=val;
    }
    @Override
    public void replaceAll(final FloatUnaryOperator operator)
    {
      final int size;
      if(0!=((size=this.size)))
      {
        int modCount=this.modCount;
        try
        {
          FloatArrSeq.uncheckedreplaceAll(arr,0,size,operator);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        this.modCount=modCount+1;
      }
    }
    @Override
    public void sort(final FloatComparator sorter)
    {
      final int size;
      if(1<((size=this.size)))
      {
        int modCount=this.modCount;
        try
        {
          SortUtil.uncheckedcomparatorSort(arr,0,size-1,sorter);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        this.modCount=modCount+1;
      }
    }
    @Override
    public void replaceAll(final UnaryOperator<Float> operator)
    {
      final int size;
      if(0!=((size=this.size)))
      {
        int modCount=this.modCount;
        try
        {
          FloatArrSeq.uncheckedreplaceAll(arr,0,size,operator::apply);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        this.modCount=modCount+1;
      }
    }
    @Override
    public void sort(final Comparator<? super Float> sorter)
    {
      final int size;
      if(1<((size=this.size)))
      {
        int modCount=this.modCount;
        try
        {
          SortUtil.uncheckedcomparatorSort(arr,0,size-1,sorter::compare);
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
         SortUtil.uncheckedreverseSort(arr,0,size-1);
         ++this.modCount;
      }
    }
    public void sort()
    {
      final int size;
      if((size=this.size)>1)
      {
         SortUtil.uncheckedsort(arr,0,size-1);
         ++this.modCount;
      }
    }
    @Override
    public OmniList.OfFloat subList(final int fromIndex,final int toIndex)
    {
      CheckedCollection.checkSubListRange(fromIndex,toIndex,this.size);
      return new SubList(this,fromIndex,toIndex-fromIndex);
    }
    @Override
    void uncheckedCopyInto(final float[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Object[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Float[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final double[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedForEach(final int size,final FloatConsumer action)
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
    boolean uncheckedremoveValBits(int size,final int bits)
    {
      final var arr=this.arr;int index;
      for(index=0,--size;bits!=Float.floatToRawIntBits(arr[index]);++index)
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
      for(index=0,--size;!Float.isNaN(arr[index]);++index)
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
    private static class BidirectionalItr implements OmniListIterator.OfFloat
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
      public void add(final float val)
      {
        final Checked root;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        final int rootSize,cursor=this.cursor;
        if((rootSize=root.size)!=0)
        {
          ((FloatArrSeq)root).uncheckedInsert(cursor,val,rootSize);
        }
        else
        {
          ((FloatArrSeq)root).uncheckedInit(val);
        }
        root.modCount=++modCount;
        this.modCount=modCount;
        this.cursor=cursor+1;
        this.lastRet=-1;
      }
      @Override
      public void forEachRemaining(final FloatConsumer action)
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
      public void forEachRemaining(final Consumer<? super Float> action)
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
      public float nextFloat()
      {
        final Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        final int cursor;
        if((cursor=this.cursor)!=root.size)
        {
          lastRet=cursor;
          this.cursor=cursor+1;
          return (float)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override
      public int nextIndex()
      {
        return cursor;
      }
      @Override
      public float previousFloat()
      {
        final Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        int cursor;
        if((cursor=this.cursor)!=0)
        {
          lastRet=--cursor;
          this.cursor=cursor;
          return (float)root.arr[cursor];
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
      public void set(final float val)
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
    private static class SubList extends AbstractFloatList implements OmniList.OfFloat
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
      public boolean add(final float val)
      {
        final Checked root;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        final int rootSize;
        final int size=this.size;
        if((rootSize=root.size)!=0)
        {
          ((FloatArrSeq)root).uncheckedInsert(rootOffset+size,val,rootSize);
        }
        else
        {
          ((FloatArrSeq)root).uncheckedInit(val);
        }
        root.modCount=++modCount;
        this.modCount=modCount;
        bubbleUpIncrementSize(parent);
        this.size=size+1;
        return true;
      }
      @Override
      public void add(final int index,final float val)
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
          ((FloatArrSeq)root).uncheckedInsert(rootOffset+index,val,rootSize);
        }
        else
        {
          ((FloatArrSeq)root).uncheckedInit(val);
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
          final float[] arr;
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
        final float[] arr;
        final int size;
        if((size=this.size)!=0)
        {
          ArrCopy.uncheckedCopy(root.arr,rootOffset,arr=new float[size],0,size);
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
          if(val instanceof Float)
          {
            return uncheckedcontains(size,(float)(val));
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
          if(val instanceof Float)
          {
            return uncheckedindexOf(size,(float)(val));
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
          if(val instanceof Float)
          {
            return uncheckedlastIndexOf(size,(float)(val));
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
            return this.uncheckedcontainsBits(size,TypeUtil.FLT_TRUE_BITS);
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
            if(TypeUtil.checkCastToFloat(val))
            {
              return this.uncheckedcontainsBits(size,Float.floatToRawIntBits(val));
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
            if(TypeUtil.checkCastToFloat(val))
            {
              return this.uncheckedcontainsBits(size,Float.floatToRawIntBits(val));
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
          return this.uncheckedcontains(size,(val));
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
          final float v;
          if(val==(v=(float)val))
          {
            return this.uncheckedcontainsBits(size,Float.floatToRawIntBits(v));
          }
          else if(v!=v)
          {
            return this.uncheckedcontainsNaN(size);
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      protected boolean containsRawInt(final int val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val!=0)
          {
            return uncheckedcontainsBits(size,Float.floatToRawIntBits(val));
          }
          return uncheckedcontains0(size);
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
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
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      private int uncheckedindexOfRawInt(final int size,final int val)
      {
        if(0!=(val))
        {
          return uncheckedindexOfBits(size,Float.floatToRawIntBits(val));
        }
        return uncheckedindexOf0(size);
      }
      private int uncheckedlastIndexOfRawInt(final int size,final int val)
      {
        if(0!=(val))
        {
          return uncheckedlastIndexOfBits(size,Float.floatToRawIntBits(val));
        }
        return uncheckedlastIndexOf0(size);
      }
      @Override
      public boolean equals(final Object val)
      {
        //TODO implements equals method
        return false;
      }
      @Override
      public void forEach(final FloatConsumer action)
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
      public void forEach(final Consumer<? super Float> action)
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
      public float getFloat(final int index)
      {
        final var root=checkModCountAndGetRoot();
        CheckedCollection.checkLo(index);
        CheckedCollection.checkReadHi(index,this.size);
        return (float)root.arr[index+rootOffset];
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
            return this.uncheckedindexOfBits(size,TypeUtil.FLT_TRUE_BITS);
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
            if(TypeUtil.checkCastToFloat(val))
            {
              return this.uncheckedindexOfBits(size,Float.floatToRawIntBits(val));
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
            if(TypeUtil.checkCastToFloat(val))
            {
              return this.uncheckedindexOfBits(size,Float.floatToRawIntBits(val));
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
          return this.uncheckedindexOf(size,(val));
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
          final float v;
          if(val==(v=(float)val))
          {
            return this.uncheckedindexOfBits(size,Float.floatToRawIntBits(v));
          }
          else if(v!=v)
          {
            return this.uncheckedindexOfNaN(size);
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int indexOf(final char val)
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedindexOfRawInt(size,val);
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int indexOf(final short val)
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedindexOfRawInt(size,val);
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
          if(val)
          {
            return this.uncheckedlastIndexOfBits(size,TypeUtil.FLT_TRUE_BITS);
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
            if(TypeUtil.checkCastToFloat(val))
            {
              return this.uncheckedlastIndexOfBits(size,Float.floatToRawIntBits(val));
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
            if(TypeUtil.checkCastToFloat(val))
            {
              return this.uncheckedlastIndexOfBits(size,Float.floatToRawIntBits(val));
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
          return this.uncheckedlastIndexOf(size,(val));
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
          final float v;
          if(val==(v=(float)val))
          {
            return this.uncheckedlastIndexOfBits(size,Float.floatToRawIntBits(v));
          }
          else if(v!=v)
          {
            return this.uncheckedlastIndexOfNaN(size);
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int lastIndexOf(final char val)
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedlastIndexOfRawInt(size,val);
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int lastIndexOf(final short val)
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedlastIndexOfRawInt(size,val);
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
      public OmniIterator.OfFloat iterator()
      {
        final int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
        return new BidirectionalItr(this,modCount);
      }
      @Override
      public OmniListIterator.OfFloat listIterator()
      {
        final int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
        return new BidirectionalItr(this,modCount);
      }
      @Override
      public OmniListIterator.OfFloat listIterator(final int index)
      {
        final int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
        CheckedCollection.checkLo(index);
        CheckedCollection.checkWriteHi(index,size);
        return new BidirectionalItr(this,modCount,index+rootOffset);
      }
      @Override
      public void put(final int index,final float val)
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
          if(val instanceof Float)
          {
            return uncheckedremoveVal(size,(float)(val));
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
            return this.uncheckedremoveValBits(size,TypeUtil.FLT_TRUE_BITS);
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
            if(TypeUtil.checkCastToFloat(val))
            {
              return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
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
            if(TypeUtil.checkCastToFloat(val))
            {
              return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
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
          return this.uncheckedremoveVal(size,(val));
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
          final float v;
          if(val==(v=(float)val))
          {
            return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(v));
          }
          else if(v!=v)
          {
            return this.uncheckedremoveValNaN(size);
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public float removeFloatAt(int index)
      {
        int modCount;
        final Checked root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        CheckedCollection.checkLo(index);
        final int size;
        CheckedCollection.checkReadHi(index,size=this.size);
        final float[] arr;
        final var removed=(float)(arr=root.arr)[index+=rootOffset];
        eraseIndexHelper(arr,index,--root.size);
        root.modCount=++modCount;
        bubbleUpDecrementSize(parent);
        this.modCount=modCount;
        this.size=size-1;
        return removed;
      }
      @Override
      public boolean removeIf(final FloatPredicate filter)
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
      public void replaceAll(final FloatUnaryOperator operator)
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
      public void sort(final FloatComparator sorter)
      {
        final var root=this.root;
        final int size;
        if(1<(size=this.size))
        {
          int modCount=this.modCount;
          try
          {
            final int rootOffset;SortUtil.uncheckedcomparatorSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter);
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
          SortUtil.uncheckedreverseSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
          SortUtil.uncheckedsort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
              root.modCount=++modCount;
              bubbleUpIncrementModCount(parent);
              this.modCount=modCount;
        }
      }
      @Override
      public boolean removeIf(final Predicate<? super Float> filter)
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
      public void replaceAll(final UnaryOperator<Float> operator)
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
      public void sort(final Comparator<? super Float> sorter)
      {
        final var root=this.root;
        final int size;
        if(1<(size=this.size))
        {
          int modCount=this.modCount;
          try
          {
            final int rootOffset;SortUtil.uncheckedcomparatorSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter::compare);
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
      public float set(int index,final float val)
      {
        final var root=checkModCountAndGetRoot();
        CheckedCollection.checkLo(index);
        CheckedCollection.checkReadHi(index,this.size);
        final float[] arr;
        final var oldVal=(float)(arr=root.arr)[index+=rootOffset];
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
      public OmniList.OfFloat subList(final int fromIndex,final int toIndex)
      {
        final var root=checkModCountAndGetRoot();
        CheckedCollection.checkSubListRange(fromIndex,toIndex,this.size);
        return new SubList(root,this,this.rootOffset+fromIndex,toIndex-fromIndex);
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
      public Float[] toArray()
      {
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0)
        {
          final Float[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new Float[size],0,size);
          return dst;
        }
        return OmniArray.OfFloat.DEFAULT_BOXED_ARR;
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
      private boolean uncheckedcontains(final int size,final float val)
      {
        if(val==(val))
        {
          return uncheckedcontainsBits(size,Float.floatToRawIntBits(val));
        }
        return uncheckedcontainsNaN(size);
      }
      private int uncheckedindexOf(final int size,final float val)
      {
        if(val==(val))
        {
          return uncheckedindexOfBits(size,Float.floatToRawIntBits(val));
        }
        return uncheckedindexOfNaN(size);
      }
      private int uncheckedlastIndexOf(final int size,final float val)
      {
        if(val==(val))
        {
          return uncheckedlastIndexOfBits(size,Float.floatToRawIntBits(val));
        }
        return uncheckedlastIndexOfNaN(size);
      }
      private boolean uncheckedremoveVal(final int size,final float val)
      {
        if(val==(val))
        {
          return uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
        }
        return uncheckedremoveValNaN(size);
      }
      private boolean uncheckedcontainsBits(final int size,final int bits)
      { 
        final int rootOffset;
        return FloatArrSeq.uncheckedcontainsBits(checkModCountAndGetRoot().arr,rootOffset=this.rootOffset,rootOffset+size,bits);
      }
      private boolean uncheckedcontains0(final int size)
      { 
        final int rootOffset;
        return FloatArrSeq.uncheckedcontains0(checkModCountAndGetRoot().arr,rootOffset=this.rootOffset,rootOffset+size);
      }
      private boolean uncheckedcontainsNaN(final int size)
      { 
        final int rootOffset;
        return FloatArrSeq.uncheckedcontainsNaN(checkModCountAndGetRoot().arr,rootOffset=this.rootOffset,rootOffset+size);
      }
      private int uncheckedindexOfBits(final int size,final int bits)
      { 
        final int rootOffset;
        return FloatArrSeq.uncheckedindexOfBits(checkModCountAndGetRoot().arr,rootOffset=this.rootOffset,rootOffset+size,bits);
      }
      private int uncheckedindexOf0(final int size)
      { 
        final int rootOffset;
        return FloatArrSeq.uncheckedindexOf0(checkModCountAndGetRoot().arr,rootOffset=this.rootOffset,rootOffset+size);
      }
      private int uncheckedindexOfNaN(final int size)
      { 
        final int rootOffset;
        return FloatArrSeq.uncheckedindexOfNaN(checkModCountAndGetRoot().arr,rootOffset=this.rootOffset,rootOffset+size);
      }
      private int uncheckedlastIndexOfBits(final int size,final int bits)
      { 
        final int rootOffset;
        return FloatArrSeq.uncheckedlastIndexOfBits(checkModCountAndGetRoot().arr,rootOffset=this.rootOffset,rootOffset+size,bits);
      }
      private int uncheckedlastIndexOf0(final int size)
      { 
        final int rootOffset;
        return FloatArrSeq.uncheckedlastIndexOf0(checkModCountAndGetRoot().arr,rootOffset=this.rootOffset,rootOffset+size);
      }
      private int uncheckedlastIndexOfNaN(final int size)
      { 
        final int rootOffset;
        return FloatArrSeq.uncheckedlastIndexOfNaN(checkModCountAndGetRoot().arr,rootOffset=this.rootOffset,rootOffset+size);
      }
      private boolean uncheckedremoveValBits(int size,final int bits)
      {
        int modCount;
        final Checked root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        final var arr=root.arr;int index;final int bound=(index=this.rootOffset)+(--size);
        for(;bits!=Float.floatToRawIntBits(arr[index]);)
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
        for(;Float.isNaN(arr[index]);)
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
      private boolean uncheckedRemoveIf(int size,final FloatPredicate filter)
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
            if(filter.test((float)arr[srcOffset]))
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
          this.size=size-(size=((FloatArrSeq)root).finalizeSubListBatchRemove(arr,dstOffset,srcBound));
          bubbleUpDecrementSize(parent,size);
        }
        catch(final RuntimeException e)
        {
          throw CheckedCollection.checkModCount(modCount,root.modCount,e);
        }
        return true;
      }
      private static class BidirectionalItr implements OmniListIterator.OfFloat
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
        public void add(final float val)
        {
          final SubList parent;
          final Checked root;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
          final int rootSize;
          final int cursor=this.cursor;
          if((rootSize=root.size)!=0)
          {
            ((FloatArrSeq)root).uncheckedInsert(cursor,val,rootSize);
          }
          else
          {
            ((FloatArrSeq)root).uncheckedInit(val);
          }
          bubbleUpIncrementSize(parent.parent);
          parent.modCount=++modCount;
          root.modCount=modCount;
          ++parent.size;
          this.cursor=cursor+1;
          this.lastRet=-1;
        }
        @Override
        public void forEachRemaining(final FloatConsumer action)
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
        public void forEachRemaining(final Consumer<? super Float> action)
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
        public float nextFloat()
        {
          final Checked root;
          final SubList parent;
          CheckedCollection.checkModCount(modCount,(root=(parent=this.parent).root).modCount);
          final int cursor;
          if((cursor=this.cursor)!=parent.rootOffset+parent.size)
          {
            lastRet=cursor;
            this.cursor=cursor+1;
            return (float)root.arr[cursor];
          }
          throw new NoSuchElementException();
        }
        @Override
        public int nextIndex()
        {
          return cursor-parent.rootOffset;
        }
        @Override
        public float previousFloat()
        {
          final Checked root;
          final SubList  parent;
          CheckedCollection.checkModCount(modCount,(root=(parent=this.parent).root).modCount);
          int cursor;
          if((cursor=this.cursor)!=parent.rootOffset)
          {
            lastRet=--cursor;
            this.cursor=cursor;
            return (float)root.arr[cursor];
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
        public void set(final float val)
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
  public static class CheckedStack extends Checked implements OmniStack.OfFloat
  {
    public CheckedStack()
    {
      super();
    }
    public CheckedStack(final int capacity)
    {
      super(capacity);
    }
    public CheckedStack(final int size,final float[] arr)
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
      return new CheckedStack(size,arr);
    }
    @Override
    public boolean equals(final Object val)
    {
      //TODO implements equals method
      return false;
    }
    @Override
    public OmniIterator.OfFloat iterator()
    {
      return new CheckedDescendingItr(this);
    }
        @Override
        public float pollFloat()
        {
          final int size;
          if((size=this.size)!=0)
          {
            ++modCount;
            return (float)(((FloatArrSeq)this).uncheckedPop(size-1));
          }
          return Float.NaN;
        }
        @Override
        public Float poll()
        {
          final int size;
          if((size=this.size)!=0)
          {
            ++modCount;
            return (Float)(((FloatArrSeq)this).uncheckedPop(size-1));
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
            return (double)(((FloatArrSeq)this).uncheckedPop(size-1));
          }
          return Double.NaN;
        }
    @Override
    void uncheckedCopyInto(final float[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Object[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Float[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final double[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedForEach(final int size,final FloatConsumer action)
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
    boolean uncheckedremoveValBits(int size,final int bits)
    {
      final var arr=this.arr;int index;
      for(index=--size;bits!=Float.floatToRawIntBits(arr[index]);--index)
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
      for(index=--size;!Float.isNaN(arr[index]);--index)
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
    private static class CheckedDescendingItr extends AbstractFloatItr implements OmniIterator.OfFloat
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
      public void forEachRemaining(FloatConsumer action)
      {
        final int cursor;
        if((cursor=this.cursor)!=0)
        {
          uncheckedForEachRemaining(cursor,action);
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Float> action)
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
      public float nextFloat()
      {
        final CheckedStack root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        int cursor;
        if((cursor=this.cursor)!=0)
        {
          lastRet=--cursor;
          this.cursor=cursor;
          return (float)root.arr[cursor];
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
      private void uncheckedForEachRemaining(int cursor,FloatConsumer action)
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
    @Override
    boolean uncheckedRemoveIf(int size,final FloatPredicate filter)
    {
      int srcOffset;
      srcOffset=0;
      while(!filter.test((float)arr[srcOffset]))
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
  public static class UncheckedList extends Unchecked implements OmniList.OfFloat
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
    @Override
    public OmniIterator.OfFloat iterator()
    {
      return new AscendingItr(this);
    }
    @Override
    public OmniListIterator.OfFloat listIterator()
    {
      return new BidirectionalItr(this);
    }
    @Override
    public OmniListIterator.OfFloat listIterator(final int index)
    {
      return new BidirectionalItr(this,index);
    }
    @Override
    public void put(final int index,final float val)
    {
      arr[index]=val;
    }
    @Override
    public void replaceAll(final FloatUnaryOperator operator)
    {
      final int size;
      if(0!=((size=this.size)))
      {
        FloatArrSeq.uncheckedreplaceAll(arr,0,size,operator);
      }
    }
    @Override
    public void reverseSort()
    {
      final int size;
      if(1<((size=this.size)))
      {
        SortUtil.uncheckedreverseSort(arr,0,size-1);
      }
    }
    @Override
    public void sort()
    {
      final int size;
      if(1<((size=this.size)))
      {
        SortUtil.uncheckedsort(arr,0,size-1);
      }
    }
    @Override
    public void sort(final FloatComparator sorter)
    {
      final int size;
      if(1<((size=this.size)))
      {
        SortUtil.uncheckedcomparatorSort(arr,0,size-1,sorter);
      }
    }
    @Override
    public void replaceAll(final UnaryOperator<Float> operator)
    {
      final int size;
      if(0!=((size=this.size)))
      {
        FloatArrSeq.uncheckedreplaceAll(arr,0,size,operator::apply);
      }
    }
    @Override
    public void sort(final Comparator<? super Float> sorter)
    {
      final int size;
      if(1<((size=this.size)))
      {
        SortUtil.uncheckedcomparatorSort(arr,0,size-1,sorter::compare);
      }
    }
    @Override
    public OmniList.OfFloat subList(final int fromIndex,final int toIndex)
    {
      return new SubList(this,fromIndex,toIndex-fromIndex);
    }
    @Override
    void uncheckedCopyInto(final float[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Object[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Float[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final double[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedForEach(final int size,final FloatConsumer action)
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
    boolean uncheckedremoveValBits(int size,final int bits)
    {
      final var arr=this.arr;int index;
      for(index=--size;bits!=Float.floatToRawIntBits(arr[index]);--index)
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
      for(index=--size;!Float.isNaN(arr[index]);--index)
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
    private static class AscendingItr extends AbstractFloatItr implements OmniIterator.OfFloat
    {
      private transient final UncheckedList root;
      private transient int cursor;
      private AscendingItr(UncheckedList root)
      {
        this.root=root;
      }
      @Override
      public void forEachRemaining(FloatConsumer action)
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
      public void forEachRemaining(Consumer<? super Float> action)
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
      public float nextFloat()
      {
        return (float)root.arr[cursor++];
      }
      @Override
      public void remove()
      {
        final UncheckedList root;
        eraseIndexHelper((root=this.root).arr,--cursor,--root.size);
      }
    }
    private static class BidirectionalItr implements OmniListIterator.OfFloat
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
      public void add(final float val)
      {
        final FloatArrSeq root;
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
      public void forEachRemaining(FloatConsumer action)
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
      public void forEachRemaining(Consumer<? super Float> action)
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
      public float nextFloat()
      {
        final int lastRet;
        this.lastRet=lastRet=cursor++;
        return (float)root.arr[lastRet];
      }
      @Override
      public int nextIndex()
      {
        return cursor;
      }
      @Override
      public float previousFloat()
      {
        final int lastRet;
        this.lastRet=lastRet=--cursor;
        return (float)root.arr[lastRet];
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
      public void set(float val)
      {
        root.arr[lastRet]=val;
      }
    }
    private static class SubList extends AbstractFloatList implements OmniList.OfFloat
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
      public boolean add(final float val)
      {
        final FloatArrSeq root;
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
      public void add(final int index,final float val)
      {
        final FloatArrSeq root;
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
          final float[] arr;
          ArrCopy.semicheckedCopy(arr=root.arr,size+(size=rootOffset),arr,size,newRootSize-size);
        }
      }
      @Override
      public Object clone()
      {
        final float[] arr;
        final int size;
        if((size=this.size)!=0)
        {
          ArrCopy.uncheckedCopy(root.arr,rootOffset,arr=new float[size],0,size);
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
          if(val instanceof Float)
          {
            return uncheckedcontains(size,(float)(val));
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
          if(val instanceof Float)
          {
            return uncheckedindexOf(size,(float)(val));
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
          if(val instanceof Float)
          {
            return uncheckedlastIndexOf(size,(float)(val));
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
            return this.uncheckedcontainsBits(size,TypeUtil.FLT_TRUE_BITS);
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
            if(TypeUtil.checkCastToFloat(val))
            {
              return this.uncheckedcontainsBits(size,Float.floatToRawIntBits(val));
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
            if(TypeUtil.checkCastToFloat(val))
            {
              return this.uncheckedcontainsBits(size,Float.floatToRawIntBits(val));
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
          return this.uncheckedcontains(size,(val));
        }
        return false;
      }
      @Override
      public boolean contains(final double val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final float v;
          if(val==(v=(float)val))
          {
            return this.uncheckedcontainsBits(size,Float.floatToRawIntBits(v));
          }
          else if(v!=v)
          {
            return this.uncheckedcontainsNaN(size);
          }
        }
        return false;
      }
      @Override
      protected boolean containsRawInt(int val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          return uncheckedcontainsRawInt(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
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
      public void forEach(FloatConsumer action)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          uncheckedForwardForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size,action);
        }
      }
      @Override
      public void forEach(Consumer<? super Float> action)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          uncheckedForwardForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size,action::accept);
        }
      }
      @Override
      public float getFloat(int index)
      {
        return (float)root.arr[index+rootOffset];
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
            return this.uncheckedindexOfBits(size,TypeUtil.FLT_TRUE_BITS);
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
            if(TypeUtil.checkCastToFloat(val))
            {
              return this.uncheckedindexOfBits(size,Float.floatToRawIntBits(val));
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
            if(TypeUtil.checkCastToFloat(val))
            {
              return this.uncheckedindexOfBits(size,Float.floatToRawIntBits(val));
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
          return this.uncheckedindexOf(size,(val));
        }
        return -1;
      }
      @Override
      public int indexOf(final double val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final float v;
          if(val==(v=(float)val))
          {
            return this.uncheckedindexOfBits(size,Float.floatToRawIntBits(v));
          }
          else if(v!=v)
          {
            return this.uncheckedindexOfNaN(size);
          }
        }
        return -1;
      }
      @Override
      public int indexOf(final char val)
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedindexOfRawInt(size,val);
          }
        }
        return -1;
      }
      @Override
      public int indexOf(final short val)
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedindexOfRawInt(size,val);
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
          if(val)
          {
            return this.uncheckedlastIndexOfBits(size,TypeUtil.FLT_TRUE_BITS);
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
            if(TypeUtil.checkCastToFloat(val))
            {
              return this.uncheckedlastIndexOfBits(size,Float.floatToRawIntBits(val));
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
            if(TypeUtil.checkCastToFloat(val))
            {
              return this.uncheckedlastIndexOfBits(size,Float.floatToRawIntBits(val));
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
          return this.uncheckedlastIndexOf(size,(val));
        }
        return -1;
      }
      @Override
      public int lastIndexOf(final double val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final float v;
          if(val==(v=(float)val))
          {
            return this.uncheckedlastIndexOfBits(size,Float.floatToRawIntBits(v));
          }
          else if(v!=v)
          {
            return this.uncheckedlastIndexOfNaN(size);
          }
        }
        return -1;
      }
      @Override
      public int lastIndexOf(final char val)
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedlastIndexOfRawInt(size,val);
          }
        }
        return -1;
      }
      @Override
      public int lastIndexOf(final short val)
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedlastIndexOfRawInt(size,val);
          }
        }
        return -1;
      }
      @Override
      public OmniIterator.OfFloat iterator()
      {
        return new AscendingItr(this);
      }
      @Override
      public OmniListIterator.OfFloat listIterator()
      {
        return new BidirectionalItr(this);
      }
      @Override
      public OmniListIterator.OfFloat listIterator(int index)
      {
        return new BidirectionalItr(this,index+rootOffset);
      }
      @Override
      public void put(int index,float val)
      {
        root.arr[index+rootOffset]=val;
      }     
      @Override
      public boolean remove(Object val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val instanceof Float)
          {
            return uncheckedremoveVal(size,(float)(val));
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
            return this.uncheckedremoveValBits(size,TypeUtil.FLT_TRUE_BITS);
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
            if(TypeUtil.checkCastToFloat(val))
            {
              return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
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
            if(TypeUtil.checkCastToFloat(val))
            {
              return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
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
          return this.uncheckedremoveVal(size,(val));
        }
        return false;
      }
      @Override
      public boolean removeVal(final double val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final float v;
          if(val==(v=(float)val))
          {
            return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(v));
          }
          else if(v!=v)
          {
            return this.uncheckedremoveValNaN(size);
          }
        }
        return false;
      }
      @Override
      protected boolean removeValRawInt(int val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return uncheckedremoveValRawInt(size,val);
        }
        return false;
      }
      private int uncheckedindexOfRawInt(final int size,final int val)
      {
        if(0!=(val))
        {
          return uncheckedindexOfBits(size,Float.floatToRawIntBits(val));
        }
        return uncheckedindexOf0(size);
      }
      private int uncheckedlastIndexOfRawInt(final int size,final int val)
      {
        if(0!=(val))
        {
          return uncheckedlastIndexOfBits(size,Float.floatToRawIntBits(val));
        }
        return uncheckedlastIndexOf0(size);
      }
      @Override
      public float removeFloatAt(int index)
      {
        final Unchecked root;
        final float[] arr;
        final var removed=(float)(arr=(root=this.root).arr)[index+=rootOffset];
        eraseIndexHelper(arr,index,--root.size);
        bubbleUpDecrementSize(parent);
        --size;
        return removed;
      }
      @Override
      public boolean removeIf(final FloatPredicate filter)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return uncheckedRemoveIf(size,filter);
        }
        return false;
      }
      @Override
      public void replaceAll(final FloatUnaryOperator operator)
      {
        final int size;
        if(0!=((size=this.size)))
        {
          final int rootOffset;
          FloatArrSeq.uncheckedreplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);
        }
      }
      @Override
      public void reverseSort()
      {
        final int size;
        if(1<((size=this.size)))
        {
          final int rootOffset;
          SortUtil.uncheckedreverseSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
        }
      }
      @Override
      public void sort()
      {
        final int size;
        if(1<((size=this.size)))
        {
          final int rootOffset;
          SortUtil.uncheckedsort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
        }
      }
      @Override
      public void sort(final FloatComparator sorter)
      {
        final int size;
        if(1<((size=this.size)))
        {
          final int rootOffset;
          SortUtil.uncheckedcomparatorSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter);
        }
      }
      @Override
      public void replaceAll(final UnaryOperator<Float> operator)
      {
        final int size;
        if(0!=((size=this.size)))
        {
          final int rootOffset;
          FloatArrSeq.uncheckedreplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator::apply);
        }
      }
      @Override
      public void sort(final Comparator<? super Float> sorter)
      {
        final int size;
        if(1<((size=this.size)))
        {
          final int rootOffset;
          SortUtil.uncheckedcomparatorSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter::compare);
        }
      }
      @Override
      public boolean removeIf(final Predicate<? super Float> filter)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return uncheckedRemoveIf(size,filter::test);
        }
        return false;
      }
      @Override
      public float set(int index,float val)
      {
        final float[] arr;
        final var oldVal=(float)(arr=root.arr)[index+=rootOffset];
        arr[index]=val;
        return oldVal;
      }
      @Override
      public OmniList.OfFloat subList(int fromIndex,int toIndex)
      {
        return new SubList(root,this,this.rootOffset+fromIndex,toIndex-fromIndex);
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
      public Float[] toArray()
      {
        final int size;
        if((size=this.size)!=0)
        {
          final Float[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new Float[size],0,size);
          return dst;
        }
        return OmniArray.OfFloat.DEFAULT_BOXED_ARR;
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
      private boolean uncheckedcontainsBits(int size,int bits)
      { 
        final int rootOffset;
        return FloatArrSeq.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size,bits);
      }
      private boolean uncheckedcontainsNaN(int size)
      { 
        final int rootOffset;
        return FloatArrSeq.uncheckedcontainsNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }
      private boolean uncheckedcontains0(int size)
      { 
        final int rootOffset;
        return FloatArrSeq.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }
      private boolean uncheckedcontains(final int size,final float val)
      {
        if(val==(val))
        {
          return uncheckedcontainsBits(size,Float.floatToRawIntBits(val));
        }
        return uncheckedcontainsNaN(size);
      }
      private int uncheckedindexOfBits(int size,int bits)
      { 
        final int rootOffset;
        return FloatArrSeq.uncheckedindexOfBits(root.arr,rootOffset=this.rootOffset,rootOffset+size,bits);
      }
      private int uncheckedindexOfNaN(int size)
      { 
        final int rootOffset;
        return FloatArrSeq.uncheckedindexOfNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }
      private int uncheckedindexOf0(int size)
      { 
        final int rootOffset;
        return FloatArrSeq.uncheckedindexOf0(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }
      private int uncheckedindexOf(final int size,final float val)
      {
        if(val==(val))
        {
          return uncheckedindexOfBits(size,Float.floatToRawIntBits(val));
        }
        return uncheckedindexOfNaN(size);
      }
      private int uncheckedlastIndexOfBits(int size,int bits)
      { 
        final int rootOffset;
        return FloatArrSeq.uncheckedlastIndexOfBits(root.arr,rootOffset=this.rootOffset,rootOffset+size,bits);
      }
      private int uncheckedlastIndexOfNaN(int size)
      { 
        final int rootOffset;
        return FloatArrSeq.uncheckedlastIndexOfNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }
      private int uncheckedlastIndexOf0(int size)
      { 
        final int rootOffset;
        return FloatArrSeq.uncheckedlastIndexOf0(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }
      private int uncheckedlastIndexOf(final int size,final float val)
      {
        if(val==(val))
        {
          return uncheckedlastIndexOfBits(size,Float.floatToRawIntBits(val));
        }
        return uncheckedlastIndexOfNaN(size);
      }
      private boolean uncheckedRemoveIf(int size,final FloatPredicate filter)
      {
        final Unchecked root;
        int srcOffset;
        final int srcBound=(srcOffset=rootOffset)+size;
        final var arr=(root=this.root).arr;
        while(!filter.test((float)arr[srcOffset]))
        {
          if(++srcOffset==srcBound)
          {
            return false;
          }
        }
        this.size=size-(size=((FloatArrSeq)root).finalizeSubListBatchRemove(arr,pullSurvivorsDown(arr,srcOffset,srcBound-1,filter),srcBound));
        bubbleUpDecrementSize(parent,size);
        return true;
      }
      private boolean uncheckedremoveVal(final int size,final float val)
      {
        if(val==(val))
        {
          return uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
        }
        return uncheckedremoveValNaN(size);
      }
      private boolean uncheckedremoveValRawInt(final int size,final int val)
      {
        if(0!=(val))
        {
          return uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
        }
        return uncheckedremoveVal0(size);
      }
      private boolean uncheckedremoveValBits(int size,int bits)
      {
        final Unchecked root;final var arr=(root=this.root).arr;int index;final int bound=(index=this.rootOffset)+(--size);
        for(;bits!=Float.floatToRawIntBits(arr[index]);)
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
        for(;!Float.isNaN(arr[index]);)
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
      private static class AscendingItr extends AbstractFloatItr implements OmniIterator.OfFloat
      {
        private transient final SubList parent;
        private transient int cursor;
        private AscendingItr(SubList parent)
        {
           this.parent=parent;
           this.cursor=parent.rootOffset;
        }
        @Override
        public void forEachRemaining(FloatConsumer action)
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
        public void forEachRemaining(Consumer<? super Float> action)
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
        public float nextFloat()
        {
          return (float)parent.root.arr[cursor++];
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
      private static class BidirectionalItr implements OmniListIterator.OfFloat
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
        public void add(final float val)
        {
          final SubList parent;
          final FloatArrSeq root;
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
        public void forEachRemaining(FloatConsumer action)
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
        public void forEachRemaining(Consumer<? super Float> action)
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
        public float nextFloat()
        {
          final int lastRet;
          this.lastRet=lastRet=cursor++;
          return (float)parent.root.arr[lastRet];
        }
        @Override
        public int nextIndex()
        {
          return cursor-parent.rootOffset;
        }
        @Override
        public float previousFloat()
        {
          final int lastRet;
          this.lastRet=lastRet=--cursor;
          return (float)parent.root.arr[lastRet];
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
        public void set(float val)
        {
          parent.root.arr[lastRet]=val;
        }
      }
    }
  }
  public static class UncheckedStack extends Unchecked implements OmniStack.OfFloat
  {
    public UncheckedStack()
    {
      super();
    }
    public UncheckedStack(final int capacity)
    {
      super(capacity);
    }
    public UncheckedStack(final int size,final float[] arr)
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
      return new UncheckedStack(size,arr);
    }
    @Override
    public boolean equals(final Object val)
    {
      //TODO implements equals method
      return false;
    }
    @Override
    public OmniIterator.OfFloat iterator()
    {
      return new UncheckedDescendingItr(this);
    }
        @Override
        public float pollFloat()
        {
          final int size;
          if((size=this.size)!=0)
          {
            return (float)(((FloatArrSeq)this).uncheckedPop(size-1));
          }
          return Float.NaN;
        }
        @Override
        public Float poll()
        {
          final int size;
          if((size=this.size)!=0)
          {
            return (Float)(((FloatArrSeq)this).uncheckedPop(size-1));
          }
          return null;
        }
        @Override
        public double pollDouble()
        {
          final int size;
          if((size=this.size)!=0)
          {
            return (double)(((FloatArrSeq)this).uncheckedPop(size-1));
          }
          return Double.NaN;
        }
    @Override
    void uncheckedCopyInto(final float[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Object[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Float[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final double[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedForEach(final int size,final FloatConsumer action)
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
    boolean uncheckedremoveValBits(int size,final int bits)
    {
      final var arr=this.arr;int index;
      for(index=--size;bits!=Float.floatToRawIntBits(arr[index]);--index)
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
      for(index=--size;!Float.isNaN(arr[index]);--index)
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
    private static class UncheckedDescendingItr extends AbstractFloatItr implements OmniIterator.OfFloat
    {
      private transient final UncheckedStack root;
      private transient int cursor;
      private UncheckedDescendingItr(UncheckedStack root)
      {
        this.root=root;
        cursor=root.size;
      }
      @Override
      public void forEachRemaining(FloatConsumer action)
      {
        final int cursor;
        if((cursor=this.cursor)!=0)
        {
          uncheckedReverseForEach(root.arr,0,cursor,action);
          this.cursor=0;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Float> action)
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
      public float nextFloat()
      {
        return (float)root.arr[--cursor];
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
