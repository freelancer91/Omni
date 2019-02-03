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
import omni.impl.seq.AbstractByteList;
import omni.impl.AbstractByteItr;
import omni.util.BitSetUtil;
import omni.util.TypeUtil;
import omni.function.ByteComparator;
import omni.function.ByteUnaryOperator;
import omni.function.ByteConsumer;
import omni.function.BytePredicate;
public abstract class ByteArrSeq extends AbstractByteList implements OmniCollection.OfByte
{
  private static void eraseIndexHelper(byte[] arr,int index,int newSize)
  {
    ArrCopy.semicheckedCopy(arr,index+1,arr,index,newSize-index);
  }
  static boolean uncheckedcontains(byte[] arr,int offset,int bound,int val)
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
  private static int uncheckedindexOf(byte[] arr,int offset,int bound,int val)
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
  private static int uncheckedindexOf(byte[] arr,int bound,int val)
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
  private static int uncheckedlastIndexOf(byte[] arr,int offset,int bound,int val)
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
  private static int uncheckedlastIndexOf(byte[] arr,int bound,int val)
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
  private static int uncheckedsearch(byte[] arr,int bound,int val)
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
  private static  void uncheckedreplaceAll(byte[] arr,int offset,int bound,ByteUnaryOperator operator)
  {
    do
    {
      arr[offset]=operator.applyAsByte((byte)arr[offset]);
    }
    while(++offset!=bound);
  }
  static  void uncheckedForwardForEach(byte[] arr,int offset,int bound,ByteConsumer action)
  {
    do
    {
      action.accept((byte)arr[offset]);
    }
    while(++offset!=bound);
  }
  static  void uncheckedReverseForEach(byte[] arr,int offset,int bound,ByteConsumer action)
  {
    do
    {
      action.accept((byte)arr[--bound]);
    }
    while(bound!=offset);
  }
  private static int forwardHashCode(byte[] arr,int offset,int bound)
  {
    int hash=31+(arr[offset]);
    while(++offset!=bound)
    {
      hash=hash*31+(arr[offset]);
    }
    return hash;
  }
  private static int reverseHashCode(byte[] arr,int offset,int bound)
  {
    int hash=31+(arr[offset]);
    while(bound!=offset)
    {
      hash=hash*31+(arr[offset]);
    }
    return hash;
  }
  private static void forwardToString(byte[] arr,int offset,int bound,StringBuilder builder)
  {
    for(builder.append(arr[offset]);++offset!=bound;builder.append(',').append(' ').append(arr[offset]));
  }
  private static void reverseToString(byte[] arr,int offset,int bound,StringBuilder builder)
  {
    for(builder.append(arr[--bound]);bound!=offset;builder.append(',').append(' ').append(arr[--bound]));
  }
  static  int pullSurvivorsUp(byte[] arr,int srcBegin,int srcEnd,BytePredicate filter)
  {
    int dstOffset=srcBegin;
    while(srcBegin!=srcEnd)
    {
      final byte v;
      if(!filter.test((byte)(v=arr[--srcBegin])))
      {
        arr[dstOffset--]=v;
      }
    }
    return dstOffset;
  }
  static  int pullSurvivorsDown(byte[] arr,int srcBegin,int srcEnd,BytePredicate filter)
  {
    int dstOffset=srcBegin;
    while(srcBegin!=srcEnd)
    {
      final byte v;
      if(!filter.test((byte)(v=arr[++srcBegin])))
      {
        arr[dstOffset++]=v;
      }
    }
    return dstOffset;
  }
  static  int markSurvivorsAndPullUp(CheckedCollection.AbstractModCountChecker modCountChecker,byte[] arr,int srcOffset,int srcBound,BytePredicate filter)
  {
    int dstOffset=srcOffset;
    for(;;)
    {
      if(--srcOffset==srcBound)
      {
        modCountChecker.checkModCount();
        break;
      }
      final byte v;
      if(!filter.test((byte)(v=arr[srcOffset])))
      {
        int numSurvivors;
        if((numSurvivors=(--srcOffset)-srcBound)!=0)
        {
          final long[] survivors;
          numSurvivors=markSurvivorsReverse(arr,survivors=BitSetUtil.getBitSet(numSurvivors),srcOffset,srcBound,filter);
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
  static  int markSurvivorsAndPullDown(CheckedCollection.AbstractModCountChecker modCountChecker,byte[] arr,int srcOffset,int srcBound,BytePredicate filter)
  {
    int dstOffset=srcOffset;
    for(;;)
    {
      if(++srcOffset==srcBound)
      {
        modCountChecker.checkModCount();
        break;
      }
      final byte v;
      if(!filter.test((byte)(v=arr[srcOffset])))
      {
        int numSurvivors;
        if((numSurvivors=srcBound-++srcOffset)!=0)
        {
          final long[] survivors;
          numSurvivors=markSurvivors(arr,survivors=BitSetUtil.getBitSet(numSurvivors),srcOffset,srcBound,filter);
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
  static  int markSurvivorsReverse(byte[] arr,long[] survivorSet,int offset,int bound,BytePredicate filter)
  {
    for(int numSurvivors=0,wordOffset=survivorSet.length-1;;)
    {
      long word=0L,marker=1L<<63;
      do
      {
        if(!filter.test((byte)arr[offset]))
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
  static  int markSurvivors(byte[] arr,long[] survivorSet,int offset,int bound,BytePredicate filter)
  {
    for(int numSurvivors=0,wordOffset=0;;)
    {
      long word=0L,marker=1L;
      do
      {
        if(!filter.test((byte)arr[offset]))
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
  static int pullSurvivorsUp(byte[] arr,long[] survivorSet,int dstOffset,int srcOffset,int numSurvivors)
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
  static int pullSurvivorsDown(byte[] arr,long[] survivorSet,int dstOffset,int srcOffset,int numSurvivors)
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
  transient byte[] arr;
  private ByteArrSeq()
  {
    super();
    this.arr=OmniArray.OfByte.DEFAULT_ARR;
  }
  private ByteArrSeq(final int capacity)
  {
    super();
    switch(capacity)
    {
    default:
      this.arr=new byte[capacity];
      return;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfByte.DEFAULT_ARR;
    case 0:
    }
  }
  private ByteArrSeq(final int size,final byte[] arr)
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
      if(val instanceof Byte)
      {
        return ByteArrSeq.uncheckedcontains(arr,0,size,(byte)(val));
      }
    }
    return false;
  }
  public int indexOf(final Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Byte)
      {
        return ByteArrSeq.uncheckedindexOf(arr,size,(byte)(val));
      }
    }
    return -1;
  }
  public int lastIndexOf(final Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Byte)
      {
        return ByteArrSeq.uncheckedlastIndexOf(arr,size,(byte)(val));
      }
    }
    return -1;
  }
  public int search(final Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Byte)
      {
        return ByteArrSeq.uncheckedsearch(arr,size,(byte)(val));
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
      return ByteArrSeq.uncheckedcontains(arr,0,size,TypeUtil.castToByte(val));
    }
    return false;
  }
  @Override
  public boolean contains(final int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val==(byte)val)
      {
        return ByteArrSeq.uncheckedcontains(arr,0,size,(val));
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
      final byte v;
      if(val==(v=(byte)val))
      {
        return ByteArrSeq.uncheckedcontains(arr,0,size,v);
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
      final byte v;
      if(val==(v=(byte)val))
      {
        return ByteArrSeq.uncheckedcontains(arr,0,size,v);
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
      final byte v;
      if(val==(v=(byte)val))
      {
        return ByteArrSeq.uncheckedcontains(arr,0,size,v);
      }
    }
    return false;
  }
  @Override
  public boolean contains(final byte val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ByteArrSeq.uncheckedcontains(arr,0,size,(val));
    }
    return false;
  }
  @Override
  public boolean contains(final char val)
  {
    if(val<=Byte.MAX_VALUE)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return ByteArrSeq.uncheckedcontains(arr,0,size,(val));
      }
    }
    return false;
  }
  @Override
  public void forEach(final ByteConsumer action)
  {
    final int size;
    if((size=this.size)!=0)
    {
      uncheckedForEach(size,action);
    }
  }
  @Override
  public void forEach(final Consumer<? super Byte> action)
  {
    final int size;
    if((size=this.size)!=0)
    {
      uncheckedForEach(size,action::accept);
    }
  }
  @Override
  public boolean removeIf(final BytePredicate filter)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedRemoveIf(size,filter);
    }
    return false;
  }
  @Override
  public boolean removeIf(final Predicate<? super Byte> filter)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedRemoveIf(size,filter::test);
    }
    return false;
  }
  public Byte pop()
  {
    return popByte();
  }
  public void push(final Byte val)
  {
    push((byte)val);
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
      return ByteArrSeq.uncheckedindexOf(arr,size,TypeUtil.castToByte(val));
    }
    return -1;
  }
  public int indexOf(final int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val==(byte)val)
      {
        return ByteArrSeq.uncheckedindexOf(arr,size,(val));
      }
    }
    return -1;
  }
  public int indexOf(final long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final byte v;
      if(val==(v=(byte)val))
      {
        return ByteArrSeq.uncheckedindexOf(arr,size,v);
      }
    }
    return -1;
  }
  public int indexOf(final float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final byte v;
      if(val==(v=(byte)val))
      {
        return ByteArrSeq.uncheckedindexOf(arr,size,v);
      }
    }
    return -1;
  }
  public int indexOf(final double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final byte v;
      if(val==(v=(byte)val))
      {
        return ByteArrSeq.uncheckedindexOf(arr,size,v);
      }
    }
    return -1;
  }
  public int indexOf(final byte val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ByteArrSeq.uncheckedindexOf(arr,size,(val));
    }
    return -1;
  }
  public int indexOf(final char val)
  {
    if(val<=Byte.MAX_VALUE)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return ByteArrSeq.uncheckedindexOf(arr,size,(val));
      }
    }
    return -1;
  }
  public int lastIndexOf(final boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ByteArrSeq.uncheckedlastIndexOf(arr,size,TypeUtil.castToByte(val));
    }
    return -1;
  }
  public int lastIndexOf(final int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val==(byte)val)
      {
        return ByteArrSeq.uncheckedlastIndexOf(arr,size,(val));
      }
    }
    return -1;
  }
  public int lastIndexOf(final long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final byte v;
      if(val==(v=(byte)val))
      {
        return ByteArrSeq.uncheckedlastIndexOf(arr,size,v);
      }
    }
    return -1;
  }
  public int lastIndexOf(final float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final byte v;
      if(val==(v=(byte)val))
      {
        return ByteArrSeq.uncheckedlastIndexOf(arr,size,v);
      }
    }
    return -1;
  }
  public int lastIndexOf(final double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final byte v;
      if(val==(v=(byte)val))
      {
        return ByteArrSeq.uncheckedlastIndexOf(arr,size,v);
      }
    }
    return -1;
  }
  public int lastIndexOf(final byte val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ByteArrSeq.uncheckedlastIndexOf(arr,size,(val));
    }
    return -1;
  }
  public int lastIndexOf(final char val)
  {
    if(val<=Byte.MAX_VALUE)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return ByteArrSeq.uncheckedlastIndexOf(arr,size,(val));
      }
    }
    return -1;
  }
  public byte peekByte()
  {
    final int size;
    if((size=this.size)!=0)
    {
      return (byte)(arr[size-1]);
    }
    return Byte.MIN_VALUE;
  }
  abstract void uncheckedCopyInto(final byte[] dst,final int length);
  public Byte peek()
  {
    final int size;
    if((size=this.size)!=0)
    {
      return (arr[size-1]);
    }
    return null;
  }
  abstract void uncheckedCopyInto(final Byte[] dst,final int length);
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
  public int peekInt()
  {
    final int size;
    if((size=this.size)!=0)
    {
      return (int)(arr[size-1]);
    }
    return Integer.MIN_VALUE;
  }
  abstract void uncheckedCopyInto(final int[] dst,final int length);
  public short peekShort()
  {
    final int size;
    if((size=this.size)!=0)
    {
      return (short)(arr[size-1]);
    }
    return Short.MIN_VALUE;
  }
  abstract void uncheckedCopyInto(final short[] dst,final int length);
  public byte popByte()
  {
    return uncheckedPop(size-1);
  }
  public void push(final byte val)
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
      if(val instanceof Byte)
      {
        return this.uncheckedremoveVal(size,(byte)(val));
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
      return this.uncheckedremoveVal(size,TypeUtil.castToByte(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(final int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val==(byte)val)
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
      final byte v;
      if(val==(v=(byte)val))
      {
        return this.uncheckedremoveVal(size,v);
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
      final byte v;
      if(val==(v=(byte)val))
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
      final byte v;
      if(val==(v=(byte)val))
      {
        return this.uncheckedremoveVal(size,v);
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(final byte val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(final char val)
  {
    if(val<=Byte.MAX_VALUE)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return this.uncheckedremoveVal(size,(val));
      }
    }
    return false;
  }
  public int search(final boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ByteArrSeq.uncheckedsearch(arr,size,TypeUtil.castToByte(val));
    }
    return -1;
  }
  public int search(final int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val==(byte)val)
      {
        return ByteArrSeq.uncheckedsearch(arr,size,(val));
      }
    }
    return -1;
  }
  public int search(final long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final byte v;
      if(val==(v=(byte)val))
      {
        return ByteArrSeq.uncheckedsearch(arr,size,v);
      }
    }
    return -1;
  }
  public int search(final float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final byte v;
      if(val==(v=(byte)val))
      {
        return ByteArrSeq.uncheckedsearch(arr,size,v);
      }
    }
    return -1;
  }
  public int search(final double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final byte v;
      if(val==(v=(byte)val))
      {
        return ByteArrSeq.uncheckedsearch(arr,size,v);
      }
    }
    return -1;
  }
  public int search(final byte val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ByteArrSeq.uncheckedsearch(arr,size,(val));
    }
    return -1;
  }
  public int search(final char val)
  {
    if(val<=Byte.MAX_VALUE)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return ByteArrSeq.uncheckedsearch(arr,size,(val));
      }
    }
    return -1;
  }
  @Override
  public byte set(final int index,final byte val)
  {
    final byte[] arr;
    final var oldVal=(byte)(arr=this.arr)[index];
    arr[index]=val;
    return oldVal;
  }
  @Override
  public byte[] toByteArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final byte[] dst;
      uncheckedCopyInto(dst=new byte[size],size);
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_ARR;
  }
  @Override
  public Byte[] toArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final Byte[] dst;
      uncheckedCopyInto(dst=new Byte[size],size);
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_BOXED_ARR;
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
  public int[] toIntArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final int[] dst;
      uncheckedCopyInto(dst=new int[size],size);
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  @Override
  public short[] toShortArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final short[] dst;
      uncheckedCopyInto(dst=new short[size],size);
      return dst;
    }
    return OmniArray.OfShort.DEFAULT_ARR;
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
  abstract boolean uncheckedremoveVal(final int size,final int val);
  abstract void uncheckedForEach(final int size,final ByteConsumer action);
  abstract int uncheckedHashCode(final int size);
  abstract boolean uncheckedRemoveIf(final int size,final BytePredicate filter);
  abstract void uncheckedToString(final int size,final StringBuilder builder);
  private int finalizeSubListBatchRemove(final byte[] arr,final int newBound,final int oldBound)
  {
    final int newRootSize,numRemoved;
    size=newRootSize=size-(numRemoved=oldBound-newBound);
    ArrCopy.semicheckedCopy(arr,oldBound,arr,newBound,newRootSize-newBound);
    return numRemoved;
  }
  private byte[] growInsert(byte[] arr,final int index,final int size)
  {
    if(arr.length==size)
    {
      ArrCopy.semicheckedCopy(arr,0,arr=new byte[OmniArray.growBy50Pct(size)],0,index);
      this.arr=arr;
    }
    return arr;
  }
  private void uncheckedAppend(final byte val,final int size)
  {
    byte[] arr;
    if((arr=this.arr).length==size)
    {
      ArrCopy.uncheckedCopy(arr,0,arr=new byte[OmniArray.growBy50Pct(size)],0,size);
    }
    arr[size]=val;
    this.size=size+1;
  }
  private void uncheckedInit(final byte val)
  {
    byte[] arr;
    if((arr=this.arr)==OmniArray.OfByte.DEFAULT_ARR)
    {
      this.arr=arr=new byte[OmniArray.DEFAULT_ARR_SEQ_CAP];
    }
    else if(arr==null)
    {
      this.arr=arr=new byte[1];
    }
    arr[0]=val;
    size=1;
  }
  private void uncheckedInsert(final int index,final byte val,final int size)
  {
    final int tailDist;
    if((tailDist=size-index)==0)
    {
      uncheckedAppend(val,size);
    }
    else
    {
      byte[] arr;
      ArrCopy.uncheckedCopy(arr=this.arr,index,arr=growInsert(arr,index,size),index+1,tailDist);
      arr[index]=val;
      this.size=size+1;
    }
  }
  private byte uncheckedPop(final int newSize)
  {
    this.size=newSize;
    return arr[newSize];
  }
  public static abstract class Checked extends ByteArrSeq
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
    private Checked(final int size,final byte[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean add(final byte val)
    {
      ++modCount;
      super.push(val);
      return true;
    }  
    @Override
    public void add(final int index,final byte val)
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
    public byte getByte(final int index)
    {
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      return (byte)arr[index];
    }
    @Override
    public byte popByte()
    {
      final int size;
      if((size=this.size)!=0)
      {
        ++modCount;
        return (byte)super.uncheckedPop(size-1);
      }
      throw new NoSuchElementException();
    }
    @Override
    public void push(final byte val)
    {
      ++modCount;
      super.push(val);
    }
    @Override
    public byte removeByteAt(final int index)
    {
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ++modCount;
      final byte[] arr;
      final var removed=(byte)(arr=this.arr)[index];
      eraseIndexHelper(arr,index,--size);
      this.size=size;
      return removed;
    }
    @Override
    public byte set(final int index,final byte val)
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
    boolean uncheckedRemoveIf(int size,final BytePredicate filter)
    {
      int srcOffset;
      final int modCount=this.modCount;
      try
      {
        srcOffset=0;
        final var arr=this.arr;
        for(;;)
        {
          if(filter.test((byte)arr[srcOffset]))
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
  public static class CheckedList extends Checked implements OmniList.OfByte
  {
    public CheckedList()
    {
      super();
    }
    public CheckedList(final int capacity)
    {
      super(capacity);
    }
    public CheckedList(final int size,final byte[] arr)
    {
      super(size,arr);
    }
    @Override
    public Object clone()
    {
      final byte[] arr;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,arr=new byte[size],0,size);
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
    public OmniIterator.OfByte iterator()
    {
      return new BidirectionalItr(this);
    }
    @Override
    public OmniListIterator.OfByte listIterator()
    {
      return new BidirectionalItr(this);
    }
    @Override
    public OmniListIterator.OfByte listIterator(final int index)
    {
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,this.size);
      return new BidirectionalItr(this,index);
    }
    @Override
    public void put(final int index,final byte val)
    {
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      arr[index]=val;
    }
    @Override
    public void replaceAll(final ByteUnaryOperator operator)
    {
      final int size;
      if(0!=((size=this.size)))
      {
        int modCount=this.modCount;
        try
        {
          ByteArrSeq.uncheckedreplaceAll(arr,0,size,operator);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        this.modCount=modCount+1;
      }
    }
    @Override
    public void sort(final ByteComparator sorter)
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
    public void replaceAll(final UnaryOperator<Byte> operator)
    {
      final int size;
      if(0!=((size=this.size)))
      {
        int modCount=this.modCount;
        try
        {
          ByteArrSeq.uncheckedreplaceAll(arr,0,size,operator::apply);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        this.modCount=modCount+1;
      }
    }
    @Override
    public void sort(final Comparator<? super Byte> sorter)
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
    public OmniList.OfByte subList(final int fromIndex,final int toIndex)
    {
      CheckedCollection.checkSubListRange(fromIndex,toIndex,this.size);
      return new SubList(this,fromIndex,toIndex-fromIndex);
    }
    @Override
    void uncheckedCopyInto(final byte[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Object[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Byte[] arr,final int size)
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
    void uncheckedCopyInto(final long[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final int[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final short[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedForEach(final int size,final ByteConsumer action)
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
    boolean uncheckedremoveVal(int size,final int val)
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
    private static class BidirectionalItr implements OmniListIterator.OfByte
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
      public void add(final byte val)
      {
        final Checked root;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        final int rootSize,cursor=this.cursor;
        if((rootSize=root.size)!=0)
        {
          ((ByteArrSeq)root).uncheckedInsert(cursor,val,rootSize);
        }
        else
        {
          ((ByteArrSeq)root).uncheckedInit(val);
        }
        root.modCount=++modCount;
        this.modCount=modCount;
        this.cursor=cursor+1;
        this.lastRet=-1;
      }
      @Override
      public void forEachRemaining(final ByteConsumer action)
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
      public void forEachRemaining(final Consumer<? super Byte> action)
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
      public byte nextByte()
      {
        final Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        final int cursor;
        if((cursor=this.cursor)!=root.size)
        {
          lastRet=cursor;
          this.cursor=cursor+1;
          return (byte)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override
      public int nextIndex()
      {
        return cursor;
      }
      @Override
      public byte previousByte()
      {
        final Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        int cursor;
        if((cursor=this.cursor)!=0)
        {
          lastRet=--cursor;
          this.cursor=cursor;
          return (byte)root.arr[cursor];
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
      public void set(final byte val)
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
    private static class SubList extends AbstractByteList implements OmniList.OfByte
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
      public boolean add(final byte val)
      {
        final Checked root;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        final int rootSize;
        final int size=this.size;
        if((rootSize=root.size)!=0)
        {
          ((ByteArrSeq)root).uncheckedInsert(rootOffset+size,val,rootSize);
        }
        else
        {
          ((ByteArrSeq)root).uncheckedInit(val);
        }
        root.modCount=++modCount;
        this.modCount=modCount;
        bubbleUpIncrementSize(parent);
        this.size=size+1;
        return true;
      }
      @Override
      public void add(final int index,final byte val)
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
          ((ByteArrSeq)root).uncheckedInsert(rootOffset+index,val,rootSize);
        }
        else
        {
          ((ByteArrSeq)root).uncheckedInit(val);
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
          final byte[] arr;
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
        final byte[] arr;
        final int size;
        if((size=this.size)!=0)
        {
          ArrCopy.uncheckedCopy(root.arr,rootOffset,arr=new byte[size],0,size);
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
          if(val instanceof Byte)
          {
            return uncheckedcontains(size,(byte)(val));
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
          if(val instanceof Byte)
          {
            return uncheckedindexOf(size,(byte)(val));
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
          if(val instanceof Byte)
          {
            return uncheckedlastIndexOf(size,(byte)(val));
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
          return this.uncheckedcontains(size,TypeUtil.castToByte(val));
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
          if(val==(byte)val)
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
          final byte v;
          if(val==(v=(byte)val))
          {
            return this.uncheckedcontains(size,v);
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
          final byte v;
          if(val==(v=(byte)val))
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
          final byte v;
          if(val==(v=(byte)val))
          {
            return this.uncheckedcontains(size,v);
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean contains(final byte val)
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
      public boolean contains(final char val)
      {
        if(val<=Byte.MAX_VALUE)
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedcontains(size,(val));
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
      public void forEach(final ByteConsumer action)
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
      public void forEach(final Consumer<? super Byte> action)
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
      public byte getByte(final int index)
      {
        final var root=checkModCountAndGetRoot();
        CheckedCollection.checkLo(index);
        CheckedCollection.checkReadHi(index,this.size);
        return (byte)root.arr[index+rootOffset];
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
          return this.uncheckedindexOf(size,TypeUtil.castToByte(val));
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
          if(val==(byte)val)
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
          final byte v;
          if(val==(v=(byte)val))
          {
            return this.uncheckedindexOf(size,v);
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
          final byte v;
          if(val==(v=(byte)val))
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
          final byte v;
          if(val==(v=(byte)val))
          {
            return this.uncheckedindexOf(size,v);
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int indexOf(final byte val)
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
      public int indexOf(final char val)
      {
        if(val<=Byte.MAX_VALUE)
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedindexOf(size,(val));
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
          return this.uncheckedlastIndexOf(size,TypeUtil.castToByte(val));
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
          if(val==(byte)val)
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
          final byte v;
          if(val==(v=(byte)val))
          {
            return this.uncheckedlastIndexOf(size,v);
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
          final byte v;
          if(val==(v=(byte)val))
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
          final byte v;
          if(val==(v=(byte)val))
          {
            return this.uncheckedlastIndexOf(size,v);
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int lastIndexOf(final byte val)
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
      public int lastIndexOf(final char val)
      {
        if(val<=Byte.MAX_VALUE)
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedlastIndexOf(size,(val));
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
      public OmniIterator.OfByte iterator()
      {
        final int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
        return new BidirectionalItr(this,modCount);
      }
      @Override
      public OmniListIterator.OfByte listIterator()
      {
        final int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
        return new BidirectionalItr(this,modCount);
      }
      @Override
      public OmniListIterator.OfByte listIterator(final int index)
      {
        final int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
        CheckedCollection.checkLo(index);
        CheckedCollection.checkWriteHi(index,size);
        return new BidirectionalItr(this,modCount,index+rootOffset);
      }
      @Override
      public void put(final int index,final byte val)
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
          if(val instanceof Byte)
          {
            return uncheckedremoveVal(size,(byte)(val));
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
          return this.uncheckedremoveVal(size,TypeUtil.castToByte(val));
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
          if(val==(byte)val)
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
          final byte v;
          if(val==(v=(byte)val))
          {
            return this.uncheckedremoveVal(size,v);
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
          final byte v;
          if(val==(v=(byte)val))
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
          final byte v;
          if(val==(v=(byte)val))
          {
            return this.uncheckedremoveVal(size,v);
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final byte val)
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
      public boolean removeVal(final char val)
      {
        if(val<=Byte.MAX_VALUE)
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedremoveVal(size,(val));
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public byte removeByteAt(int index)
      {
        int modCount;
        final Checked root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        CheckedCollection.checkLo(index);
        final int size;
        CheckedCollection.checkReadHi(index,size=this.size);
        final byte[] arr;
        final var removed=(byte)(arr=root.arr)[index+=rootOffset];
        eraseIndexHelper(arr,index,--root.size);
        root.modCount=++modCount;
        bubbleUpDecrementSize(parent);
        this.modCount=modCount;
        this.size=size-1;
        return removed;
      }
      @Override
      public boolean removeIf(final BytePredicate filter)
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
      public void replaceAll(final ByteUnaryOperator operator)
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
      public void sort(final ByteComparator sorter)
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
      public boolean removeIf(final Predicate<? super Byte> filter)
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
      public void replaceAll(final UnaryOperator<Byte> operator)
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
      public void sort(final Comparator<? super Byte> sorter)
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
      public byte set(int index,final byte val)
      {
        final var root=checkModCountAndGetRoot();
        CheckedCollection.checkLo(index);
        CheckedCollection.checkReadHi(index,this.size);
        final byte[] arr;
        final var oldVal=(byte)(arr=root.arr)[index+=rootOffset];
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
      public OmniList.OfByte subList(final int fromIndex,final int toIndex)
      {
        final var root=checkModCountAndGetRoot();
        CheckedCollection.checkSubListRange(fromIndex,toIndex,this.size);
        return new SubList(root,this,this.rootOffset+fromIndex,toIndex-fromIndex);
      }
      @Override
      public byte[] toByteArray()
      {
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0)
        {
          final byte[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new byte[size],0,size);
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_ARR;
      }
      @Override
      public Byte[] toArray()
      {
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0)
        {
          final Byte[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new Byte[size],0,size);
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_BOXED_ARR;
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
      public int[] toIntArray()
      {
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0)
        {
          final int[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new int[size],0,size);
          return dst;
        }
        return OmniArray.OfInt.DEFAULT_ARR;
      }
      @Override
      public short[] toShortArray()
      {
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0)
        {
          final short[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new short[size],0,size);
          return dst;
        }
        return OmniArray.OfShort.DEFAULT_ARR;
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
      private boolean uncheckedcontains(final int size,final int val)
      { 
        final int rootOffset;
        return ByteArrSeq.uncheckedcontains(checkModCountAndGetRoot().arr,rootOffset=this.rootOffset,rootOffset+size,val);
      }
      private int uncheckedindexOf(final int size,final int val)
      { 
        final int rootOffset;
        return ByteArrSeq.uncheckedindexOf(checkModCountAndGetRoot().arr,rootOffset=this.rootOffset,rootOffset+size,val);
      }
      private int uncheckedlastIndexOf(final int size,final int val)
      { 
        final int rootOffset;
        return ByteArrSeq.uncheckedlastIndexOf(checkModCountAndGetRoot().arr,rootOffset=this.rootOffset,rootOffset+size,val);
      }
      private boolean uncheckedremoveVal(int size,final int val)
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
      private boolean uncheckedRemoveIf(int size,final BytePredicate filter)
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
            if(filter.test((byte)arr[srcOffset]))
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
          this.size=size-(size=((ByteArrSeq)root).finalizeSubListBatchRemove(arr,dstOffset,srcBound));
          bubbleUpDecrementSize(parent,size);
        }
        catch(final RuntimeException e)
        {
          throw CheckedCollection.checkModCount(modCount,root.modCount,e);
        }
        return true;
      }
      private static class BidirectionalItr implements OmniListIterator.OfByte
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
        public void add(final byte val)
        {
          final SubList parent;
          final Checked root;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
          final int rootSize;
          final int cursor=this.cursor;
          if((rootSize=root.size)!=0)
          {
            ((ByteArrSeq)root).uncheckedInsert(cursor,val,rootSize);
          }
          else
          {
            ((ByteArrSeq)root).uncheckedInit(val);
          }
          bubbleUpIncrementSize(parent.parent);
          parent.modCount=++modCount;
          root.modCount=modCount;
          ++parent.size;
          this.cursor=cursor+1;
          this.lastRet=-1;
        }
        @Override
        public void forEachRemaining(final ByteConsumer action)
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
        public void forEachRemaining(final Consumer<? super Byte> action)
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
        public byte nextByte()
        {
          final Checked root;
          final SubList parent;
          CheckedCollection.checkModCount(modCount,(root=(parent=this.parent).root).modCount);
          final int cursor;
          if((cursor=this.cursor)!=parent.rootOffset+parent.size)
          {
            lastRet=cursor;
            this.cursor=cursor+1;
            return (byte)root.arr[cursor];
          }
          throw new NoSuchElementException();
        }
        @Override
        public int nextIndex()
        {
          return cursor-parent.rootOffset;
        }
        @Override
        public byte previousByte()
        {
          final Checked root;
          final SubList  parent;
          CheckedCollection.checkModCount(modCount,(root=(parent=this.parent).root).modCount);
          int cursor;
          if((cursor=this.cursor)!=parent.rootOffset)
          {
            lastRet=--cursor;
            this.cursor=cursor;
            return (byte)root.arr[cursor];
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
        public void set(final byte val)
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
  public static class CheckedStack extends Checked implements OmniStack.OfByte
  {
    public CheckedStack()
    {
      super();
    }
    public CheckedStack(final int capacity)
    {
      super(capacity);
    }
    public CheckedStack(final int size,final byte[] arr)
    {
      super(size,arr);
    }
    @Override
    public Object clone()
    {
      final byte[] arr;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,arr=new byte[size],0,size);
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
    public OmniIterator.OfByte iterator()
    {
      return new CheckedDescendingItr(this);
    }
        @Override
        public byte pollByte()
        {
          final int size;
          if((size=this.size)!=0)
          {
            ++modCount;
            return (byte)(((ByteArrSeq)this).uncheckedPop(size-1));
          }
          return Byte.MIN_VALUE;
        }
        @Override
        public Byte poll()
        {
          final int size;
          if((size=this.size)!=0)
          {
            ++modCount;
            return (Byte)(((ByteArrSeq)this).uncheckedPop(size-1));
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
            return (double)(((ByteArrSeq)this).uncheckedPop(size-1));
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
            return (float)(((ByteArrSeq)this).uncheckedPop(size-1));
          }
          return Float.NaN;
        }
        @Override
        public long pollLong()
        {
          final int size;
          if((size=this.size)!=0)
          {
            ++modCount;
            return (long)(((ByteArrSeq)this).uncheckedPop(size-1));
          }
          return Long.MIN_VALUE;
        }
        @Override
        public int pollInt()
        {
          final int size;
          if((size=this.size)!=0)
          {
            ++modCount;
            return (int)(((ByteArrSeq)this).uncheckedPop(size-1));
          }
          return Integer.MIN_VALUE;
        }
        @Override
        public short pollShort()
        {
          final int size;
          if((size=this.size)!=0)
          {
            ++modCount;
            return (short)(((ByteArrSeq)this).uncheckedPop(size-1));
          }
          return Short.MIN_VALUE;
        }
    @Override
    void uncheckedCopyInto(final byte[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Object[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Byte[] arr,final int size)
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
    void uncheckedCopyInto(final long[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final int[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final short[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedForEach(final int size,final ByteConsumer action)
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
    boolean uncheckedremoveVal(int size,final int val)
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
    private static class CheckedDescendingItr extends AbstractByteItr implements OmniIterator.OfByte
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
      public void forEachRemaining(ByteConsumer action)
      {
        final int cursor;
        if((cursor=this.cursor)!=0)
        {
          uncheckedForEachRemaining(cursor,action);
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Byte> action)
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
      public byte nextByte()
      {
        final CheckedStack root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        int cursor;
        if((cursor=this.cursor)!=0)
        {
          lastRet=--cursor;
          this.cursor=cursor;
          return (byte)root.arr[cursor];
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
      private void uncheckedForEachRemaining(int cursor,ByteConsumer action)
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
  public static abstract class Unchecked extends ByteArrSeq
  {
    private Unchecked()
    {
      super();
    }
    private Unchecked(final int capacity)
    {
      super(capacity);
    }
    private Unchecked(final int size,final byte[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean add(final byte val)
    {
      super.push(val);
      return true;
    }
    @Override
    public void add(final int index,final byte val)
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
    public byte getByte(final int index)
    {
      return (byte)arr[index];
    }
    @Override
    public byte removeByteAt(final int index)
    {
      final byte[] arr;
      final var removed=(byte)(arr=this.arr)[index];
      eraseIndexHelper(arr,index,--size);
      return removed;
    }
    @Override
    boolean uncheckedRemoveIf(int size,final BytePredicate filter)
    {
      int srcOffset;
      srcOffset=0;
      while(!filter.test((byte)arr[srcOffset]))
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
  public static class UncheckedList extends Unchecked implements OmniList.OfByte
  {
    public UncheckedList()
    {
      super();
    }
    public UncheckedList(final int capacity)
    {
      super(capacity);
    }
    public UncheckedList(final int size,final byte[] arr)
    {
      super(size,arr);
    }
    @Override
    public Object clone()
    {
      final byte[] arr;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,arr=new byte[size],0,size);
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
    public OmniIterator.OfByte iterator()
    {
      return new AscendingItr(this);
    }
    @Override
    public OmniListIterator.OfByte listIterator()
    {
      return new BidirectionalItr(this);
    }
    @Override
    public OmniListIterator.OfByte listIterator(final int index)
    {
      return new BidirectionalItr(this,index);
    }
    @Override
    public void put(final int index,final byte val)
    {
      arr[index]=val;
    }
    @Override
    public void replaceAll(final ByteUnaryOperator operator)
    {
      final int size;
      if(0!=((size=this.size)))
      {
        ByteArrSeq.uncheckedreplaceAll(arr,0,size,operator);
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
    public void sort(final ByteComparator sorter)
    {
      final int size;
      if(1<((size=this.size)))
      {
        SortUtil.uncheckedcomparatorSort(arr,0,size-1,sorter);
      }
    }
    @Override
    public void replaceAll(final UnaryOperator<Byte> operator)
    {
      final int size;
      if(0!=((size=this.size)))
      {
        ByteArrSeq.uncheckedreplaceAll(arr,0,size,operator::apply);
      }
    }
    @Override
    public void sort(final Comparator<? super Byte> sorter)
    {
      final int size;
      if(1<((size=this.size)))
      {
        SortUtil.uncheckedcomparatorSort(arr,0,size-1,sorter::compare);
      }
    }
    @Override
    public OmniList.OfByte subList(final int fromIndex,final int toIndex)
    {
      return new SubList(this,fromIndex,toIndex-fromIndex);
    }
    @Override
    void uncheckedCopyInto(final byte[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Object[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Byte[] arr,final int size)
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
    void uncheckedCopyInto(final long[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final int[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final short[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedForEach(final int size,final ByteConsumer action)
    {
      uncheckedForwardForEach(arr,0,size,action);
    }
    @Override
    int uncheckedHashCode(final int size)
    {
      return forwardHashCode(arr,0,size);
    }
    @Override
    boolean uncheckedremoveVal(int size,final int val)
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
    private static class AscendingItr extends AbstractByteItr implements OmniIterator.OfByte
    {
      private transient final UncheckedList root;
      private transient int cursor;
      private AscendingItr(UncheckedList root)
      {
        this.root=root;
      }
      @Override
      public void forEachRemaining(ByteConsumer action)
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
      public void forEachRemaining(Consumer<? super Byte> action)
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
      public byte nextByte()
      {
        return (byte)root.arr[cursor++];
      }
      @Override
      public void remove()
      {
        final UncheckedList root;
        eraseIndexHelper((root=this.root).arr,--cursor,--root.size);
      }
    }
    private static class BidirectionalItr implements OmniListIterator.OfByte
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
      public void add(final byte val)
      {
        final ByteArrSeq root;
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
      public void forEachRemaining(ByteConsumer action)
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
      public void forEachRemaining(Consumer<? super Byte> action)
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
      public byte nextByte()
      {
        final int lastRet;
        this.lastRet=lastRet=cursor++;
        return (byte)root.arr[lastRet];
      }
      @Override
      public int nextIndex()
      {
        return cursor;
      }
      @Override
      public byte previousByte()
      {
        final int lastRet;
        this.lastRet=lastRet=--cursor;
        return (byte)root.arr[lastRet];
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
      public void set(byte val)
      {
        root.arr[lastRet]=val;
      }
    }
    private static class SubList extends AbstractByteList implements OmniList.OfByte
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
      public boolean add(final byte val)
      {
        final ByteArrSeq root;
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
      public void add(final int index,final byte val)
      {
        final ByteArrSeq root;
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
          final byte[] arr;
          ArrCopy.semicheckedCopy(arr=root.arr,size+(size=rootOffset),arr,size,newRootSize-size);
        }
      }
      @Override
      public Object clone()
      {
        final byte[] arr;
        final int size;
        if((size=this.size)!=0)
        {
          ArrCopy.uncheckedCopy(root.arr,rootOffset,arr=new byte[size],0,size);
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
          if(val instanceof Byte)
          {
            return uncheckedcontains(size,(byte)(val));
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
          if(val instanceof Byte)
          {
            return uncheckedindexOf(size,(byte)(val));
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
          if(val instanceof Byte)
          {
            return uncheckedlastIndexOf(size,(byte)(val));
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
          return this.uncheckedcontains(size,TypeUtil.castToByte(val));
        }
        return false;
      }
      @Override
      public boolean contains(final int val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val==(byte)val)
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
          final byte v;
          if(val==(v=(byte)val))
          {
            return this.uncheckedcontains(size,v);
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
          final byte v;
          if(val==(v=(byte)val))
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
          final byte v;
          if(val==(v=(byte)val))
          {
            return this.uncheckedcontains(size,v);
          }
        }
        return false;
      }
      @Override
      public boolean contains(final byte val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedcontains(size,(val));
        }
        return false;
      }
      @Override
      public boolean contains(final char val)
      {
        if(val<=Byte.MAX_VALUE)
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedcontains(size,(val));
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
      public void forEach(ByteConsumer action)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          uncheckedForwardForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size,action);
        }
      }
      @Override
      public void forEach(Consumer<? super Byte> action)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          uncheckedForwardForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size,action::accept);
        }
      }
      @Override
      public byte getByte(int index)
      {
        return (byte)root.arr[index+rootOffset];
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
          return this.uncheckedindexOf(size,TypeUtil.castToByte(val));
        }
        return -1;
      }
      @Override
      public int indexOf(final int val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val==(byte)val)
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
          final byte v;
          if(val==(v=(byte)val))
          {
            return this.uncheckedindexOf(size,v);
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
          final byte v;
          if(val==(v=(byte)val))
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
          final byte v;
          if(val==(v=(byte)val))
          {
            return this.uncheckedindexOf(size,v);
          }
        }
        return -1;
      }
      @Override
      public int indexOf(final byte val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedindexOf(size,(val));
        }
        return -1;
      }
      @Override
      public int indexOf(final char val)
      {
        if(val<=Byte.MAX_VALUE)
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedindexOf(size,(val));
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
          return this.uncheckedlastIndexOf(size,TypeUtil.castToByte(val));
        }
        return -1;
      }
      @Override
      public int lastIndexOf(final int val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val==(byte)val)
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
          final byte v;
          if(val==(v=(byte)val))
          {
            return this.uncheckedlastIndexOf(size,v);
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
          final byte v;
          if(val==(v=(byte)val))
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
          final byte v;
          if(val==(v=(byte)val))
          {
            return this.uncheckedlastIndexOf(size,v);
          }
        }
        return -1;
      }
      @Override
      public int lastIndexOf(final byte val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedlastIndexOf(size,(val));
        }
        return -1;
      }
      @Override
      public int lastIndexOf(final char val)
      {
        if(val<=Byte.MAX_VALUE)
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedlastIndexOf(size,(val));
          }
        }
        return -1;
      }
      @Override
      public OmniIterator.OfByte iterator()
      {
        return new AscendingItr(this);
      }
      @Override
      public OmniListIterator.OfByte listIterator()
      {
        return new BidirectionalItr(this);
      }
      @Override
      public OmniListIterator.OfByte listIterator(int index)
      {
        return new BidirectionalItr(this,index+rootOffset);
      }
      @Override
      public void put(int index,byte val)
      {
        root.arr[index+rootOffset]=val;
      }     
      @Override
      public boolean remove(Object val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val instanceof Byte)
          {
            return uncheckedremoveVal(size,(byte)(val));
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
          return this.uncheckedremoveVal(size,TypeUtil.castToByte(val));
        }
        return false;
      }
      @Override
      public boolean removeVal(final int val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val==(byte)val)
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
          final byte v;
          if(val==(v=(byte)val))
          {
            return this.uncheckedremoveVal(size,v);
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
          final byte v;
          if(val==(v=(byte)val))
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
          final byte v;
          if(val==(v=(byte)val))
          {
            return this.uncheckedremoveVal(size,v);
          }
        }
        return false;
      }
      @Override
      public boolean removeVal(final byte val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,(val));
        }
        return false;
      }
      @Override
      public boolean removeVal(final char val)
      {
        if(val<=Byte.MAX_VALUE)
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedremoveVal(size,(val));
          }
        }
        return false;
      }
      @Override
      public byte removeByteAt(int index)
      {
        final Unchecked root;
        final byte[] arr;
        final var removed=(byte)(arr=(root=this.root).arr)[index+=rootOffset];
        eraseIndexHelper(arr,index,--root.size);
        bubbleUpDecrementSize(parent);
        --size;
        return removed;
      }
      @Override
      public boolean removeIf(final BytePredicate filter)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return uncheckedRemoveIf(size,filter);
        }
        return false;
      }
      @Override
      public void replaceAll(final ByteUnaryOperator operator)
      {
        final int size;
        if(0!=((size=this.size)))
        {
          final int rootOffset;
          ByteArrSeq.uncheckedreplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);
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
      public void sort(final ByteComparator sorter)
      {
        final int size;
        if(1<((size=this.size)))
        {
          final int rootOffset;
          SortUtil.uncheckedcomparatorSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter);
        }
      }
      @Override
      public void replaceAll(final UnaryOperator<Byte> operator)
      {
        final int size;
        if(0!=((size=this.size)))
        {
          final int rootOffset;
          ByteArrSeq.uncheckedreplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator::apply);
        }
      }
      @Override
      public void sort(final Comparator<? super Byte> sorter)
      {
        final int size;
        if(1<((size=this.size)))
        {
          final int rootOffset;
          SortUtil.uncheckedcomparatorSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter::compare);
        }
      }
      @Override
      public boolean removeIf(final Predicate<? super Byte> filter)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return uncheckedRemoveIf(size,filter::test);
        }
        return false;
      }
      @Override
      public byte set(int index,byte val)
      {
        final byte[] arr;
        final var oldVal=(byte)(arr=root.arr)[index+=rootOffset];
        arr[index]=val;
        return oldVal;
      }
      @Override
      public OmniList.OfByte subList(int fromIndex,int toIndex)
      {
        return new SubList(root,this,this.rootOffset+fromIndex,toIndex-fromIndex);
      }
      @Override
      public byte[] toByteArray()
      {
        final int size;
        if((size=this.size)!=0)
        {
          final byte[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new byte[size],0,size);
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_ARR;
      }
      @Override
      public Byte[] toArray()
      {
        final int size;
        if((size=this.size)!=0)
        {
          final Byte[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new Byte[size],0,size);
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_BOXED_ARR;
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
      public int[] toIntArray()
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new int[size],0,size);
          return dst;
        }
        return OmniArray.OfInt.DEFAULT_ARR;
      }
      @Override
      public short[] toShortArray()
      {
        final int size;
        if((size=this.size)!=0)
        {
          final short[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new short[size],0,size);
          return dst;
        }
        return OmniArray.OfShort.DEFAULT_ARR;
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
      private boolean uncheckedcontains(int size,int val)
      { 
        final int rootOffset;
        return ByteArrSeq.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
      }
      private int uncheckedindexOf(int size,int val)
      { 
        final int rootOffset;
        return ByteArrSeq.uncheckedindexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
      }
      private int uncheckedlastIndexOf(int size,int val)
      { 
        final int rootOffset;
        return ByteArrSeq.uncheckedlastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
      }
      private boolean uncheckedRemoveIf(int size,final BytePredicate filter)
      {
        final Unchecked root;
        int srcOffset;
        final int srcBound=(srcOffset=rootOffset)+size;
        final var arr=(root=this.root).arr;
        while(!filter.test((byte)arr[srcOffset]))
        {
          if(++srcOffset==srcBound)
          {
            return false;
          }
        }
        this.size=size-(size=((ByteArrSeq)root).finalizeSubListBatchRemove(arr,pullSurvivorsDown(arr,srcOffset,srcBound-1,filter),srcBound));
        bubbleUpDecrementSize(parent,size);
        return true;
      }
      private boolean uncheckedremoveVal(int size,int val)
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
      private static class AscendingItr extends AbstractByteItr implements OmniIterator.OfByte
      {
        private transient final SubList parent;
        private transient int cursor;
        private AscendingItr(SubList parent)
        {
           this.parent=parent;
           this.cursor=parent.rootOffset;
        }
        @Override
        public void forEachRemaining(ByteConsumer action)
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
        public void forEachRemaining(Consumer<? super Byte> action)
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
        public byte nextByte()
        {
          return (byte)parent.root.arr[cursor++];
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
      private static class BidirectionalItr implements OmniListIterator.OfByte
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
        public void add(final byte val)
        {
          final SubList parent;
          final ByteArrSeq root;
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
        public void forEachRemaining(ByteConsumer action)
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
        public void forEachRemaining(Consumer<? super Byte> action)
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
        public byte nextByte()
        {
          final int lastRet;
          this.lastRet=lastRet=cursor++;
          return (byte)parent.root.arr[lastRet];
        }
        @Override
        public int nextIndex()
        {
          return cursor-parent.rootOffset;
        }
        @Override
        public byte previousByte()
        {
          final int lastRet;
          this.lastRet=lastRet=--cursor;
          return (byte)parent.root.arr[lastRet];
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
        public void set(byte val)
        {
          parent.root.arr[lastRet]=val;
        }
      }
    }
  }
  public static class UncheckedStack extends Unchecked implements OmniStack.OfByte
  {
    public UncheckedStack()
    {
      super();
    }
    public UncheckedStack(final int capacity)
    {
      super(capacity);
    }
    public UncheckedStack(final int size,final byte[] arr)
    {
      super(size,arr);
    }
    @Override
    public Object clone()
    {
      final byte[] arr;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,arr=new byte[size],0,size);
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
    public OmniIterator.OfByte iterator()
    {
      return new UncheckedDescendingItr(this);
    }
        @Override
        public byte pollByte()
        {
          final int size;
          if((size=this.size)!=0)
          {
            return (byte)(((ByteArrSeq)this).uncheckedPop(size-1));
          }
          return Byte.MIN_VALUE;
        }
        @Override
        public Byte poll()
        {
          final int size;
          if((size=this.size)!=0)
          {
            return (Byte)(((ByteArrSeq)this).uncheckedPop(size-1));
          }
          return null;
        }
        @Override
        public double pollDouble()
        {
          final int size;
          if((size=this.size)!=0)
          {
            return (double)(((ByteArrSeq)this).uncheckedPop(size-1));
          }
          return Double.NaN;
        }
        @Override
        public float pollFloat()
        {
          final int size;
          if((size=this.size)!=0)
          {
            return (float)(((ByteArrSeq)this).uncheckedPop(size-1));
          }
          return Float.NaN;
        }
        @Override
        public long pollLong()
        {
          final int size;
          if((size=this.size)!=0)
          {
            return (long)(((ByteArrSeq)this).uncheckedPop(size-1));
          }
          return Long.MIN_VALUE;
        }
        @Override
        public int pollInt()
        {
          final int size;
          if((size=this.size)!=0)
          {
            return (int)(((ByteArrSeq)this).uncheckedPop(size-1));
          }
          return Integer.MIN_VALUE;
        }
        @Override
        public short pollShort()
        {
          final int size;
          if((size=this.size)!=0)
          {
            return (short)(((ByteArrSeq)this).uncheckedPop(size-1));
          }
          return Short.MIN_VALUE;
        }
    @Override
    void uncheckedCopyInto(final byte[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Object[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final Byte[] arr,final int size)
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
    void uncheckedCopyInto(final long[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final int[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedCopyInto(final short[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedForEach(final int size,final ByteConsumer action)
    {
      uncheckedReverseForEach(arr,0,size,action);
    }
    @Override
    int uncheckedHashCode(final int size)
    {
      return reverseHashCode(arr,0,size);
    }
    @Override
    boolean uncheckedremoveVal(int size,final int val)
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
    private static class UncheckedDescendingItr extends AbstractByteItr implements OmniIterator.OfByte
    {
      private transient final UncheckedStack root;
      private transient int cursor;
      private UncheckedDescendingItr(UncheckedStack root)
      {
        this.root=root;
        cursor=root.size;
      }
      @Override
      public void forEachRemaining(ByteConsumer action)
      {
        final int cursor;
        if((cursor=this.cursor)!=0)
        {
          uncheckedReverseForEach(root.arr,0,cursor,action);
          this.cursor=0;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Byte> action)
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
      public byte nextByte()
      {
        return (byte)root.arr[--cursor];
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
