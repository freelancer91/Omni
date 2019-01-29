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
import omni.impl.seq.AbstractSeq;
import omni.util.BitSetUtil;
import java.util.Objects;
import omni.util.OmniPred;
public abstract class RefArrSeq<E> extends AbstractSeq implements OmniCollection.OfRef<E>
{
  private static void eraseIndexHelper(Object[] arr,int index,int newSize)
  {
    ArrCopy.semicheckedCopy(arr,index+1,arr,index,newSize-index);
    arr[newSize]=null;
  }
  static boolean uncheckedcontains(Object[] arr,int offset,int bound,Predicate<Object> pred)
  {
    for(;!pred.test(arr[offset]);)
    {
      if(++offset==bound)
      {
        return false;
      }
    }
    return true;
  }
  private static int uncheckedindexOf(Object[] arr,int offset,int bound,Predicate<Object> pred)
  {
    int index;
    for(index=offset;!pred.test(arr[index]);)
    {
      if(++index==bound)
      {
        return -1;
      }
    }
    return index-offset;
  }
  private static int uncheckedindexOf(Object[] arr,int bound,Predicate<Object> pred)
  {
    int index;
    for(index=0;!pred.test(arr[index]);)
    {
      if(++index==bound)
      {
        return -1;
      }
    }
    return index;
  }
  private static int uncheckedlastIndexOf(Object[] arr,int offset,int bound,Predicate<Object> pred)
  {
    for(;!pred.test(arr[--bound]);)
    {
      if(bound==offset)
      {
        return -1;
      }
    }
    return bound-offset;
  }
  private static int uncheckedlastIndexOf(Object[] arr,int bound,Predicate<Object> pred)
  {
    for(;!pred.test(arr[--bound]);)
    {
      if(bound==0)
      {
        return -1;
      }
    }
    return bound;
  }
  private static int uncheckedsearch(Object[] arr,int bound,Predicate<Object> pred)
  {
    int index;
    for(index=bound-1;!pred.test(arr[index]);--index)
    {
      if(index==0)
      {
        return -1;
      }
    }
    return bound-index;
  }
  @SuppressWarnings("unchecked")
  private static <E> void uncheckedreplaceAll(Object[] arr,int offset,int bound,UnaryOperator<E> operator)
  {
    do
    {
      arr[offset]=operator.apply((E)arr[offset]);
    }
    while(++offset!=bound);
  }
  @SuppressWarnings("unchecked")
  static <E> void uncheckedForwardForEach(Object[] arr,int offset,int bound,Consumer<? super E> action)
  {
    do
    {
      action.accept((E)arr[offset]);
    }
    while(++offset!=bound);
  }
  @SuppressWarnings("unchecked")
  static <E> void uncheckedReverseForEach(Object[] arr,int offset,int bound,Consumer<? super E> action)
  {
    do
    {
      action.accept((E)arr[--bound]);
    }
    while(bound!=offset);
  }
  private static int forwardHashCode(Object[] arr,int offset,int bound)
  {
    int hash=31+Objects.hashCode(arr[offset]);
    while(++offset!=bound)
    {
      hash=hash*31+Objects.hashCode(arr[offset]);
    }
    return hash;
  }
  private static int reverseHashCode(Object[] arr,int offset,int bound)
  {
    int hash=31+Objects.hashCode(arr[offset]);
    while(bound!=offset)
    {
      hash=hash*31+Objects.hashCode(arr[offset]);
    }
    return hash;
  }
  private static void forwardToString(Object[] arr,int offset,int bound,StringBuilder builder)
  {
    for(builder.append(arr[offset]);++offset!=bound;builder.append(',').append(' ').append(arr[offset]));
  }
  private static void reverseToString(Object[] arr,int offset,int bound,StringBuilder builder)
  {
    for(builder.append(arr[--bound]);bound!=offset;builder.append(',').append(' ').append(arr[--bound]));
  }
  @SuppressWarnings("unchecked")
  static <E> int pullSurvivorsUp(Object[] arr,int srcBegin,int srcEnd,Predicate<? super E> filter)
  {
    int dstOffset=srcBegin;
    while(srcBegin!=srcEnd)
    {
      final Object v;
      if(!filter.test((E)(v=arr[--srcBegin])))
      {
        arr[dstOffset--]=v;
      }
    }
    return dstOffset;
  }
  @SuppressWarnings("unchecked")
  static <E> int pullSurvivorsDown(Object[] arr,int srcBegin,int srcEnd,Predicate<? super E> filter)
  {
    int dstOffset=srcBegin;
    while(srcBegin!=srcEnd)
    {
      final Object v;
      if(!filter.test((E)(v=arr[++srcBegin])))
      {
        arr[dstOffset++]=v;
      }
    }
    return dstOffset;
  }
  @SuppressWarnings("unchecked")
  static <E> int markSurvivorsAndPullUp(CheckedCollection.AbstractModCountChecker modCountChecker,Object[] arr,int srcOffset,int srcBound,Predicate<? super E> filter)
  {
    int dstOffset=srcOffset;
    for(;;)
    {
      if(--srcOffset==srcBound)
      {
        modCountChecker.checkModCount();
        break;
      }
      final Object v;
      if(!filter.test((E)(v=arr[srcOffset])))
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
  @SuppressWarnings("unchecked")
  static <E> int markSurvivorsAndPullDown(CheckedCollection.AbstractModCountChecker modCountChecker,Object[] arr,int srcOffset,int srcBound,Predicate<? super E> filter)
  {
    int dstOffset=srcOffset;
    for(;;)
    {
      if(++srcOffset==srcBound)
      {
        modCountChecker.checkModCount();
        break;
      }
      final Object v;
      if(!filter.test((E)(v=arr[srcOffset])))
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
  @SuppressWarnings("unchecked")
  static <E> int markSurvivorsReverse(Object[] arr,long[] survivorSet,int offset,int bound,Predicate<? super E> filter)
  {
    for(int numSurvivors=0,wordOffset=survivorSet.length-1;;)
    {
      long word=0L,marker=1L<<63;
      do
      {
        if(!filter.test((E)arr[offset]))
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
  @SuppressWarnings("unchecked")
  static <E> int markSurvivors(Object[] arr,long[] survivorSet,int offset,int bound,Predicate<? super E> filter)
  {
    for(int numSurvivors=0,wordOffset=0;;)
    {
      long word=0L,marker=1L;
      do
      {
        if(!filter.test((E)arr[offset]))
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
  static int pullSurvivorsUp(Object[] arr,long[] survivorSet,int dstOffset,int srcOffset,int numSurvivors)
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
  static int pullSurvivorsDown(Object[] arr,long[] survivorSet,int dstOffset,int srcOffset,int numSurvivors)
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
  transient Object[] arr;
  private RefArrSeq()
  {
    super();
    this.arr=OmniArray.OfRef.DEFAULT_ARR;
  }
  private RefArrSeq(final int capacity)
  {
    super();
    switch(capacity)
    {
    default:
      this.arr=new Object[capacity];
      return;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfRef.DEFAULT_ARR;
    case 0:
    }
  }
  private RefArrSeq(final int size,final Object[] arr)
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
      {
        return RefArrSeq.uncheckedcontains(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  public int indexOf(final Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      {
        return RefArrSeq.uncheckedindexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  public int lastIndexOf(final Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      {
        return RefArrSeq.uncheckedlastIndexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  public int search(final Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      {
        return RefArrSeq.uncheckedsearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
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
      return RefArrSeq.uncheckedcontains(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
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
        return RefArrSeq.uncheckedcontains(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
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
      return RefArrSeq.uncheckedcontains(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedcontains(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedcontains(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final byte val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedcontains(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final char val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return RefArrSeq.uncheckedcontains(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override
  public boolean contains(final short val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return RefArrSeq.uncheckedcontains(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override
  public boolean contains(final Boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedcontains(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final Byte val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedcontains(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final Character val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedcontains(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final Short val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedcontains(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final Integer val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedcontains(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final Long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedcontains(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final Float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedcontains(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(final Double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedcontains(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public void forEach(final Consumer<? super E> action)
  {
    final int size;
    if((size=this.size)!=0)
    {
      uncheckedForEach(size,action);
    }
  }
  @Override
  public boolean removeIf(final Predicate<? super E> filter)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return uncheckedRemoveIf(size,filter);
    }
    return false;
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
      return RefArrSeq.uncheckedindexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int indexOf(final int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      {
        return RefArrSeq.uncheckedindexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  public int indexOf(final long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedindexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int indexOf(final float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedindexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int indexOf(final double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedindexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int indexOf(final byte val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedindexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int indexOf(final char val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return RefArrSeq.uncheckedindexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
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
        return RefArrSeq.uncheckedindexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  public int indexOf(final Boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedindexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int indexOf(final Byte val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedindexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int indexOf(final Character val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedindexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int indexOf(final Short val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedindexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int indexOf(final Integer val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedindexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int indexOf(final Long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedindexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int indexOf(final Float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedindexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int indexOf(final Double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedindexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int lastIndexOf(final boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedlastIndexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int lastIndexOf(final int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      {
        return RefArrSeq.uncheckedlastIndexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  public int lastIndexOf(final long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedlastIndexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int lastIndexOf(final float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedlastIndexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int lastIndexOf(final double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedlastIndexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int lastIndexOf(final byte val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedlastIndexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int lastIndexOf(final char val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return RefArrSeq.uncheckedlastIndexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
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
        return RefArrSeq.uncheckedlastIndexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  public int lastIndexOf(final Boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedlastIndexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int lastIndexOf(final Byte val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedlastIndexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int lastIndexOf(final Character val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedlastIndexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int lastIndexOf(final Short val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedlastIndexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int lastIndexOf(final Integer val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedlastIndexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int lastIndexOf(final Long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedlastIndexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int lastIndexOf(final Float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedlastIndexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int lastIndexOf(final Double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedlastIndexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  @SuppressWarnings("unchecked")
  public E peek()
  {
    final int size;
    if((size=this.size)!=0)
    {
      return (E)(arr[size-1]);
    }
    return null;
  }
  abstract void uncheckedCopyInto(final Object[] dst,final int length);
  public E pop()
  {
    return uncheckedPop(size-1);
  }
  public void push(final E val)
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
      if(val!=null)
      {
        return this.uncheckedremoveValNonNull(size,val);
      }
      return this.uncheckedremoveVal(size,Objects::isNull);
    }
    return false;
  }
  @Override
  public boolean removeVal(final boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
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
        return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
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
      return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(final float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(final double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(final byte val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(final char val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(final short val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(final Boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(final Byte val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(final Character val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(final Short val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(final Integer val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(final Long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(final Float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(final Double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  public int search(final boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedsearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      {
        return RefArrSeq.uncheckedsearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  public int search(final long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedsearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedsearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedsearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final byte val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedsearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final char val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return RefArrSeq.uncheckedsearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
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
        return RefArrSeq.uncheckedsearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  public int search(final Boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedsearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final Byte val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedsearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final Character val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedsearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final Short val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedsearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final Integer val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedsearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final Long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedsearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final Float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedsearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final Double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return RefArrSeq.uncheckedsearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public E set(final int index,final E val)
  {
    final Object[] arr;
    @SuppressWarnings("unchecked")
    final var oldVal=(E)(arr=this.arr)[index];
    arr[index]=val;
    return oldVal;
  }
  @Override
  public Object[] toArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final Object[] dst;
      uncheckedCopyInto(dst=new Object[size],size);
      return dst;
    }
    return OmniArray.OfRef.DEFAULT_ARR;
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
  abstract boolean uncheckedremoveVal(final int size,final Predicate<Object> pred);
  abstract boolean uncheckedremoveValNonNull(final int size,final Object nonNull);
  abstract void uncheckedForEach(final int size,final Consumer<? super E> action);
  abstract int uncheckedHashCode(final int size);
  abstract boolean uncheckedRemoveIf(final int size,final Predicate<? super E> filter);
  abstract void uncheckedToString(final int size,final StringBuilder builder);
  private int finalizeSubListBatchRemove(final Object[] arr,final int newBound,final int oldBound)
  {
    final int newRootSize,numRemoved;
    final int rootSize;
    size=newRootSize=(rootSize=size)-(numRemoved=oldBound-newBound);
    ArrCopy.semicheckedCopy(arr,oldBound,arr,newBound,newRootSize-newBound);
    OmniArray.OfRef.nullifyRange(arr,newRootSize,rootSize-1);
    return numRemoved;
  }
  private Object[] growInsert(Object[] arr,final int index,final int size)
  {
    if(arr.length==size)
    {
      ArrCopy.semicheckedCopy(arr,0,arr=new Object[OmniArray.growBy50Pct(size)],0,index);
      this.arr=arr;
    }
    return arr;
  }
  private void uncheckedAppend(final E val,final int size)
  {
    Object[] arr;
    if((arr=this.arr).length==size)
    {
      ArrCopy.uncheckedCopy(arr,0,arr=new Object[OmniArray.growBy50Pct(size)],0,size);
    }
    arr[size]=val;
    this.size=size+1;
  }
  private void uncheckedInit(final E val)
  {
    Object[] arr;
    if((arr=this.arr)==OmniArray.OfRef.DEFAULT_ARR)
    {
      this.arr=arr=new Object[OmniArray.DEFAULT_ARR_SEQ_CAP];
    }
    else if(arr==null)
    {
      this.arr=arr=new Object[1];
    }
    arr[0]=val;
    size=1;
  }
  private void uncheckedInsert(final int index,final E val,final int size)
  {
    final int tailDist;
    if((tailDist=size-index)==0)
    {
      uncheckedAppend(val,size);
    }
    else
    {
      Object[] arr;
      ArrCopy.uncheckedCopy(arr=this.arr,index,arr=growInsert(arr,index,size),index+1,tailDist);
      arr[index]=val;
      this.size=size+1;
    }
  }
  private E uncheckedPop(final int newSize)
  {
    this.size=newSize;
    final Object[] arr;
    @SuppressWarnings("unchecked") final var popped=(E)(arr=this.arr)[newSize];
    arr[newSize]=null;
    return popped;
  }
  public static abstract class Checked<E> extends RefArrSeq<E>
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
    private Checked(final int size,final Object[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean add(final E val)
    {
      ++modCount;
      super.push(val);
      return true;
    }  
    public void add(final int index,final E val)
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
      final int size;
      if((size=this.size)!=0)
      {
        OmniArray.OfRef.nullifyRange(arr,0,size-1);
        ++modCount;
        this.size=0;
      }
    }
    @Override
    public boolean contains(final Object val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final var arr=this.arr;
        if(val!=null)
        {
          int modCount=this.modCount;
          try
          {
            return uncheckedcontains(arr,0,size,val::equals);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
        return RefArrSeq.uncheckedcontains(arr,0,size,Objects::isNull);
      }
      return false;
    }
    @Override
    public int indexOf(final Object val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final var arr=this.arr;
        if(val!=null)
        {
          int modCount=this.modCount;
          try
          {
            return uncheckedindexOf(arr,size,val::equals);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
        return RefArrSeq.uncheckedindexOf(arr,size,Objects::isNull);
      }
      return -1;
    }
    @Override
    public int lastIndexOf(final Object val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final var arr=this.arr;
        if(val!=null)
        {
          int modCount=this.modCount;
          try
          {
            return uncheckedlastIndexOf(arr,size,val::equals);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
        return RefArrSeq.uncheckedlastIndexOf(arr,size,Objects::isNull);
      }
      return -1;
    }
    @Override
    public int search(final Object val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final var arr=this.arr;
        if(val!=null)
        {
          int modCount=this.modCount;
          try
          {
            return uncheckedsearch(arr,size,val::equals);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
        return RefArrSeq.uncheckedsearch(arr,size,Objects::isNull);
      }
      return -1;
    }
    @SuppressWarnings("unchecked")
    public E get(final int index)
    {
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      return (E)arr[index];
    }
    @Override
    public E pop()
    {
      final int size;
      if((size=this.size)!=0)
      {
        ++modCount;
        return (E)super.uncheckedPop(size-1);
      }
      throw new NoSuchElementException();
    }
    @Override
    public void push(final E val)
    {
      ++modCount;
      super.push(val);
    }
    public E remove(final int index)
    {
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ++modCount;
      final Object[] arr;
      @SuppressWarnings("unchecked")
      final var removed=(E)(arr=this.arr)[index];
      eraseIndexHelper(arr,index,--size);
      this.size=size;
      return removed;
    }
    @Override
    public E set(final int index,final E val)
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
    @SuppressWarnings("unchecked")
    @Override
    boolean uncheckedRemoveIf(int size,final Predicate<? super E> filter)
    {
      int srcOffset;
      final int modCount=this.modCount;
      try
      {
        srcOffset=0;
        final var arr=this.arr;
        for(;;)
        {
          if(filter.test((E)arr[srcOffset]))
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
        OmniArray.OfRef.nullifyRange(arr,dstOffset,size-1);
        this.size=dstOffset;
      }
      catch(final RuntimeException e)
      {
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
      return true;
    }
  }
  public static class CheckedList<E> extends Checked<E> implements OmniList.OfRef<E>
  {
    public CheckedList()
    {
      super();
    }
    public CheckedList(final int capacity)
    {
      super(capacity);
    }
    public CheckedList(final int size,final Object[] arr)
    {
      super(size,arr);
    }
    @Override
    public Object clone()
    {
      final Object[] arr;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,arr=new Object[size],0,size);
      }
      else
      {
        arr=null;
      }
      return new CheckedList<E>(size,arr);
    }
    @Override
    public boolean equals(final Object val)
    {
      //TODO implements equals method
      return false;
    }
    @Override
    public OmniIterator.OfRef<E> iterator()
    {
      return new BidirectionalItr<E>(this);
    }
    @Override
    public OmniListIterator.OfRef<E> listIterator()
    {
      return new BidirectionalItr<E>(this);
    }
    @Override
    public OmniListIterator.OfRef<E> listIterator(final int index)
    {
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,this.size);
      return new BidirectionalItr<E>(this,index);
    }
    @Override
    public void put(final int index,final E val)
    {
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      arr[index]=val;
    }
    @Override
    public void replaceAll(final UnaryOperator<E> operator)
    {
      final int size;
      if(0!=((size=this.size)))
      {
        int modCount=this.modCount;
        try
        {
          RefArrSeq.uncheckedreplaceAll(arr,0,size,operator);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        this.modCount=modCount+1;
      }
    }
    @Override
    public void sort(final Comparator<? super E> sorter)
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
    public void reverseSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        int modCount=this.modCount;
        try
        {
          SortUtil.uncheckedreverseSort(arr,0,size-1);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        this.modCount=modCount+1;
      }
    }
    public void sort()
    {
      final int size;
      if((size=this.size)>1)
      {
        int modCount=this.modCount;
        try
        {
          SortUtil.uncheckedsort(arr,0,size-1);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        this.modCount=modCount+1;
      }
    }
    @Override
    public OmniList.OfRef<E> subList(final int fromIndex,final int toIndex)
    {
      CheckedCollection.checkSubListRange(fromIndex,toIndex,this.size);
      return new SubList<E>(this,fromIndex,toIndex-fromIndex);
    }
    @Override
    void uncheckedCopyInto(final Object[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedForEach(final int size,final Consumer<? super E> action)
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
      int modCount=this.modCount;
      try
      {
        return forwardHashCode(arr,0,size);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override
    boolean uncheckedremoveVal(int size,final Predicate<Object> pred)
    {
      final var arr=this.arr;int index;
      for(index=0,--size;!pred.test(arr[index]);++index)
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
    boolean uncheckedremoveValNonNull(int size,final Object nonNull)
    {
      final var arr=this.arr;
      int index;
      int modCount=this.modCount;
      try
      {
        for(index=0,--size;!nonNull.equals(arr[index]);++index)
        {
          if(index==size)
          {
            return false;
          }
        }
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
      ++modCount;
      eraseIndexHelper(arr,index,size);
      this.size=size;
      return true;
    }
    @Override
    void uncheckedToString(final int size,final StringBuilder builder)
    {
      int modCount=this.modCount;
      try
      {
        forwardToString(arr,0,size,builder);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    private static class BidirectionalItr<E> implements OmniListIterator.OfRef<E>
    {
      private transient int lastRet=-1;
      private transient final Checked<E> root;
      private transient int cursor;
      private transient int modCount;
      private BidirectionalItr(final Checked<E> root)
      {
        this.root=root;
        this.modCount=root.modCount;
      }
      private BidirectionalItr(final Checked<E> root,final int cursor)
      {
        this.root=root;
        this.modCount=root.modCount;
        this.cursor=cursor;
      }
      @Override
      public void add(final E val)
      {
        final Checked<E> root;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        final int rootSize,cursor=this.cursor;
        if((rootSize=root.size)!=0)
        {
          ((RefArrSeq<E>)root).uncheckedInsert(cursor,val,rootSize);
        }
        else
        {
          ((RefArrSeq<E>)root).uncheckedInit(val);
        }
        root.modCount=++modCount;
        this.modCount=modCount;
        this.cursor=cursor+1;
        this.lastRet=-1;
      }
      @Override
      public void forEachRemaining(final Consumer<? super E> action)
      {
        final int cursor,bound;
        final Checked<E> root;
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
      public boolean hasNext()
      {
        return cursor!=root.size;
      }
      @Override
      public boolean hasPrevious()
      {
        return cursor!=0;
      }
      @SuppressWarnings("unchecked")
      @Override
      public E next()
      {
        final Checked<E> root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        final int cursor;
        if((cursor=this.cursor)!=root.size)
        {
          lastRet=cursor;
          this.cursor=cursor+1;
          return (E)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override
      public int nextIndex()
      {
        return cursor;
      }
      @SuppressWarnings("unchecked")
      @Override
      public E previous()
      {
        final Checked<E> root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        int cursor;
        if((cursor=this.cursor)!=0)
        {
          lastRet=--cursor;
          this.cursor=cursor;
          return (E)root.arr[cursor];
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
          final Checked<E> root;
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
      public void set(final E val)
      {
        final int lastRet;
        if((lastRet=this.lastRet)!=-1)
        {
          final Checked<E> root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          root.arr[lastRet]=val;
          return;
        }
        throw new IllegalStateException();
      }
    }
    private static class SubList<E> extends AbstractSeq implements OmniList.OfRef<E>
    {
      transient final Checked<E> root;
      transient final SubList<E> parent;
      transient final int rootOffset;
      transient int modCount;
      private static <E> void bubbleUpDecrementSize(SubList<E> parent)
      {
        while(parent!=null)
        {
          --parent.size;++parent.modCount;
          parent=parent.parent;
        }
      }
      private static <E> void bubbleUpDecrementSize(SubList<E> parent,final int numToRemove)
      {
        while(parent!=null)
        {
          parent.size-=numToRemove;++parent.modCount;
          parent=parent.parent;
        }
      }
      private static <E> void bubbleUpIncrementModCount(SubList<E> parent)
      {
        while(parent!=null)
        {
          ++parent.modCount;
          parent=parent.parent;
        }
      }
      private static <E> void bubbleUpIncrementSize(SubList<E> parent)
      {
        while(parent!=null)
        {
          ++parent.size;++parent.modCount;
          parent=parent.parent;
        }
      }
      private SubList(final Checked<E> root,final int rootOffset,final int size)
      {
        super(size);
        this.root=root;
        this.parent=null;
        this.rootOffset=rootOffset;
        this.modCount=root.modCount;
      }
      private SubList(final Checked<E> root,final SubList<E> parent,final int rootOffset,final int size)
      {
        super(size);
        this.root=root;
        this.parent=parent;
        this.rootOffset=rootOffset;
        this.modCount=root.modCount;
      }
      @Override
      public boolean add(final E val)
      {
        final Checked<E> root;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        final int rootSize;
        final int size=this.size;
        if((rootSize=root.size)!=0)
        {
          ((RefArrSeq<E>)root).uncheckedInsert(rootOffset+size,val,rootSize);
        }
        else
        {
          ((RefArrSeq<E>)root).uncheckedInit(val);
        }
        root.modCount=++modCount;
        this.modCount=modCount;
        bubbleUpIncrementSize(parent);
        this.size=size+1;
        return true;
      }
      @Override
      public void add(final int index,final E val)
      {
        final Checked<E> root;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        CheckedCollection.checkLo(index);
        final int size;
        CheckedCollection.checkWriteHi(index,size=this.size);
        final int rootSize;
        if((rootSize=root.size)!=0)
        {
          ((RefArrSeq<E>)root).uncheckedInsert(rootOffset+index,val,rootSize);
        }
        else
        {
          ((RefArrSeq<E>)root).uncheckedInit(val);
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
          final int oldRootSize;
          root.size=newRootSize=(oldRootSize=root.size)-size;
          final Object[] arr;
          final int rootOffset;
          ArrCopy.semicheckedCopy(arr=root.arr,(rootOffset=this.rootOffset)+size,arr,rootOffset,newRootSize-rootOffset);
          OmniArray.OfRef.nullifyRange(arr,newRootSize,oldRootSize-1);
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
        final Object[] arr;
        final int size;
        if((size=this.size)!=0)
        {
          ArrCopy.uncheckedCopy(root.arr,rootOffset,arr=new Object[size],0,size);
        }
        else
        {
          arr=null;
        }
        return new CheckedList<E>(size,arr);
      }
      @Override
      public boolean contains(final Object val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val!=null)
          {
            return uncheckedcontainsNonNull(size,val);
          }
          return uncheckedcontains(size,Objects::isNull);
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
          if(val!=null)
          {
            return uncheckedindexOfNonNull(size,val);
          }
          return uncheckedindexOf(size,Objects::isNull);
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
          if(val!=null)
          {
            return uncheckedlastIndexOfNonNull(size,val);
          }
          return uncheckedlastIndexOf(size,Objects::isNull);
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
          return this.uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
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
            return this.uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
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
          return this.uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
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
          return this.uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
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
          return this.uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
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
          return this.uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean contains(final char val)
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean contains(final short val)
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean contains(final Boolean val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean contains(final Byte val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean contains(final Character val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean contains(final Short val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean contains(final Integer val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean contains(final Long val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean contains(final Float val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean contains(final Double val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
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
      public void forEach(final Consumer<? super E> action)
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
      @SuppressWarnings("unchecked")
      @Override
      public E get(final int index)
      {
        final var root=checkModCountAndGetRoot();
        CheckedCollection.checkLo(index);
        CheckedCollection.checkReadHi(index,this.size);
        return (E)root.arr[index+rootOffset];
      }    
      @Override
      public int hashCode()
      {
        final int modCount=this.modCount;
        final var root=this.root;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int rootOffset;
            return forwardHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size);
          }
          return 1;
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      @Override
      public int indexOf(final boolean val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
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
            return this.uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
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
          return this.uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
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
          return this.uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
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
          return this.uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
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
          return this.uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
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
            return this.uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
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
            return this.uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int indexOf(final Boolean val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int indexOf(final Byte val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int indexOf(final Character val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int indexOf(final Short val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int indexOf(final Integer val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int indexOf(final Long val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int indexOf(final Float val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int indexOf(final Double val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
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
          return this.uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
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
            return this.uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
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
          return this.uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
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
          return this.uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
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
          return this.uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
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
          return this.uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
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
            return this.uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
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
            return this.uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int lastIndexOf(final Boolean val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int lastIndexOf(final Byte val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int lastIndexOf(final Character val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int lastIndexOf(final Short val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int lastIndexOf(final Integer val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int lastIndexOf(final Long val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int lastIndexOf(final Float val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return -1;
      }
      @Override
      public int lastIndexOf(final Double val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
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
      public OmniIterator.OfRef<E> iterator()
      {
        final int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
        return new BidirectionalItr<E>(this,modCount);
      }
      @Override
      public OmniListIterator.OfRef<E> listIterator()
      {
        final int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
        return new BidirectionalItr<E>(this,modCount);
      }
      @Override
      public OmniListIterator.OfRef<E> listIterator(final int index)
      {
        final int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
        CheckedCollection.checkLo(index);
        CheckedCollection.checkWriteHi(index,size);
        return new BidirectionalItr<E>(this,modCount,index+rootOffset);
      }
      @Override
      public void put(final int index,final E val)
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
          if(val!=null)
          {
            return uncheckedremoveValNonNull(size,val);
          }
          return uncheckedremoveVal(size,Objects::isNull);
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
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
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
            return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
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
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
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
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
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
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
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
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final char val)
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final short val)
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
          }
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final Boolean val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final Byte val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final Character val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final Short val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final Integer val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final Long val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final Float val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public boolean removeVal(final Double val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
        }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override
      public E remove(int index)
      {
        int modCount;
        final Checked<E> root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        CheckedCollection.checkLo(index);
        final int size;
        CheckedCollection.checkReadHi(index,size=this.size);
        final Object[] arr;
        @SuppressWarnings("unchecked")
        final var removed=(E)(arr=root.arr)[index+=rootOffset];
        eraseIndexHelper(arr,index,--root.size);
        root.modCount=++modCount;
        bubbleUpDecrementSize(parent);
        this.modCount=modCount;
        this.size=size-1;
        return removed;
      }
      @Override
      public boolean removeIf(final Predicate<? super E> filter)
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
      public void replaceAll(final UnaryOperator<E> operator)
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
      public void sort(final Comparator<? super E> sorter)
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
        final var root=this.root;
        final int size;
        if(1<(size=this.size))
        {
          int modCount=this.modCount;
          try
          {
            final int rootOffset;SortUtil.uncheckedreverseSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
      public void sort()
      {
        final var root=this.root;
        final int size;
        if(1<(size=this.size))
        {
          int modCount=this.modCount;
          try
          {
            final int rootOffset;SortUtil.uncheckedsort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
      public E set(int index,final E val)
      {
        final var root=checkModCountAndGetRoot();
        CheckedCollection.checkLo(index);
        CheckedCollection.checkReadHi(index,this.size);
        final Object[] arr;
        @SuppressWarnings("unchecked")
        final var oldVal=(E)(arr=root.arr)[index+=rootOffset];
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
      public OmniList.OfRef<E> subList(final int fromIndex,final int toIndex)
      {
        final var root=checkModCountAndGetRoot();
        CheckedCollection.checkSubListRange(fromIndex,toIndex,this.size);
        return new SubList<E>(root,this,this.rootOffset+fromIndex,toIndex-fromIndex);
      }
      @Override
      public Object[] toArray()
      {
        final var root=checkModCountAndGetRoot();
        final int size;
        if((size=this.size)!=0)
        {
          final Object[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new Object[size],0,size);
          return dst;
        }
        return OmniArray.OfRef.DEFAULT_ARR;
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
        final int modCount=this.modCount;
        final var root=this.root;
        try
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
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      private int uncheckedindexOf(final int size,final Predicate<Object> pred)
      { 
        final int rootOffset;
        return RefArrSeq.uncheckedindexOf(checkModCountAndGetRoot().arr,rootOffset=this.rootOffset,rootOffset+size,pred);
      }
      private int uncheckedlastIndexOf(final int size,final Predicate<Object> pred)
      { 
        final int rootOffset;
        return RefArrSeq.uncheckedlastIndexOf(checkModCountAndGetRoot().arr,rootOffset=this.rootOffset,rootOffset+size,pred);
      }
      private boolean uncheckedcontains(final int size,final Predicate<Object> pred)
      { 
        final int rootOffset;
        return RefArrSeq.uncheckedcontains(checkModCountAndGetRoot().arr,rootOffset=this.rootOffset,rootOffset+size,pred);
      }
      private boolean uncheckedcontainsNonNull(final int size,final Object nonNull)
      {
        final var root=this.root;
        int modCount=this.modCount;
        try
        {
          final int rootOffset;return RefArrSeq.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size,nonNull::equals);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      private int uncheckedindexOfNonNull(final int size,final Object nonNull)
      {
        final var root=this.root;
        int modCount=this.modCount;
        try
        {
          final int rootOffset;return RefArrSeq.uncheckedindexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,nonNull::equals);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      private int uncheckedlastIndexOfNonNull(final int size,final Object nonNull)
      {
        final var root=this.root;
        int modCount=this.modCount;
        try
        {
          final int rootOffset;return RefArrSeq.uncheckedlastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,nonNull::equals);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      private boolean uncheckedremoveVal(int size,final Predicate<Object> pred)
      {
        int modCount;
        final Checked<E> root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        final var arr=root.arr;int index;final int bound=(index=this.rootOffset)+(--size);
        for(;!pred.test(arr[index]);)
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
      private boolean uncheckedremoveValNonNull(int size,final Object nonNull)
      {
        final Checked<E> root;
        final var arr=(root=this.root).arr;
        int index;
        int modCount=this.modCount;
        try
        {
          final int bound=(index=this.rootOffset)+(--size);
          for(;!nonNull.equals(arr[index]);)
          {
            if(++index==bound)
            {
              return false;
            }
          }
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
        root.modCount=++modCount;
        bubbleUpDecrementSize(parent);
        this.modCount=modCount;
        eraseIndexHelper(arr,index,--root.size);
        this.size=size;
        return true;
      }
      private Checked<E> checkModCountAndGetRoot()
      {
        final Checked<E> root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        return root;
      }
      @SuppressWarnings("unchecked")
      private boolean uncheckedRemoveIf(int size,final Predicate<? super E> filter)
      {
        final Checked<E> root=this.root;
        int modCount=this.modCount;
        int srcOffset;
        final int srcBound=(srcOffset=rootOffset)+size;
        int dstOffset;
        try
        {
          final var arr=root.arr;
          for(;;)
          {
            if(filter.test((E)arr[srcOffset]))
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
          this.size=size-(size=((RefArrSeq<E>)root).finalizeSubListBatchRemove(arr,dstOffset,srcBound));
          bubbleUpDecrementSize(parent,size);
        }
        catch(final RuntimeException e)
        {
          throw CheckedCollection.checkModCount(modCount,root.modCount,e);
        }
        return true;
      }
      private static class BidirectionalItr<E> implements OmniListIterator.OfRef<E>
      {
        private transient final SubList<E> parent;
        private transient int cursor;
        private transient int lastRet=-1;
        private transient int modCount;
        private BidirectionalItr(final SubList<E> parent,final int modCount)
        {
          this.parent=parent;
          this.cursor=parent.rootOffset;
          this.modCount=modCount;
        }
        private BidirectionalItr(final SubList<E> parent,final int modCount,final int cursor)
        {
          this.parent=parent;
          this.cursor=cursor;
          this.modCount=modCount;
        }
        @Override
        public void add(final E val)
        {
          final SubList<E> parent;
          final Checked<E> root;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
          final int rootSize;
          final int cursor=this.cursor;
          if((rootSize=root.size)!=0)
          {
            ((RefArrSeq<E>)root).uncheckedInsert(cursor,val,rootSize);
          }
          else
          {
            ((RefArrSeq<E>)root).uncheckedInit(val);
          }
          bubbleUpIncrementSize(parent.parent);
          parent.modCount=++modCount;
          root.modCount=modCount;
          ++parent.size;
          this.cursor=cursor+1;
          this.lastRet=-1;
        }
        @Override
        public void forEachRemaining(final Consumer<? super E> action)
        {
          final int cursor,bound;
          final SubList<E> parent;
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
        public boolean hasNext()
        {
          final SubList<E> parent;
          return cursor!=(parent=this.parent).rootOffset+parent.size;
        }
        @Override
        public boolean hasPrevious()
        {
          return cursor!=parent.rootOffset;
        }
        @SuppressWarnings("unchecked")
        @Override
        public E next()
        {
          final Checked<E> root;
          final SubList<E> parent;
          CheckedCollection.checkModCount(modCount,(root=(parent=this.parent).root).modCount);
          final int cursor;
          if((cursor=this.cursor)!=parent.rootOffset+parent.size)
          {
            lastRet=cursor;
            this.cursor=cursor+1;
            return (E)root.arr[cursor];
          }
          throw new NoSuchElementException();
        }
        @Override
        public int nextIndex()
        {
          return cursor-parent.rootOffset;
        }
        @SuppressWarnings("unchecked")
        @Override
        public E previous()
        {
          final Checked<E> root;
          final SubList<E>  parent;
          CheckedCollection.checkModCount(modCount,(root=(parent=this.parent).root).modCount);
          int cursor;
          if((cursor=this.cursor)!=parent.rootOffset)
          {
            lastRet=--cursor;
            this.cursor=cursor;
            return (E)root.arr[cursor];
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
            final Checked<E> root;
            final SubList<E>  parent;
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
        public void set(final E val)
        {
          final int lastRet;
          if((lastRet=this.lastRet)!=-1)
          {
            final Checked<E> root;
            CheckedCollection.checkModCount(modCount,(root=parent.root).modCount);
            root.arr[lastRet]=val;
            return;
          }
          throw new IllegalStateException();
        }
      }
    }
  }
  public static class CheckedStack<E> extends Checked<E> implements OmniStack.OfRef<E>
  {
    public CheckedStack()
    {
      super();
    }
    public CheckedStack(final int capacity)
    {
      super(capacity);
    }
    public CheckedStack(final int size,final Object[] arr)
    {
      super(size,arr);
    }
    @Override
    public Object clone()
    {
      final Object[] arr;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,arr=new Object[size],0,size);
      }
      else
      {
        arr=null;
      }
      return new CheckedStack<E>(size,arr);
    }
    @Override
    public boolean equals(final Object val)
    {
      //TODO implements equals method
      return false;
    }
    @Override
    public OmniIterator.OfRef<E> iterator()
    {
      return new CheckedDescendingItr<E>(this);
    }
        @Override
        public E poll()
        {
          final int size;
          if((size=this.size)!=0)
          {
            ++modCount;
            return (E)(((RefArrSeq<E>)this).uncheckedPop(size-1));
          }
          return null;
        }
    @Override
    void uncheckedCopyInto(final Object[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedForEach(final int size,final Consumer<? super E> action)
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
      int modCount=this.modCount;
      try
      {
        return reverseHashCode(arr,0,size);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override
    boolean uncheckedremoveVal(int size,final Predicate<Object> pred)
    {
      final var arr=this.arr;int index;
      for(index=--size;!pred.test(arr[index]);--index)
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
    boolean uncheckedremoveValNonNull(int size,final Object nonNull)
    {
      final var arr=this.arr;
      int index;
      int modCount=this.modCount;
      try
      {
        for(index=--size;!nonNull.equals(arr[index]);--index)
        {
          if(index==0)
          {
            return false;
          }
        }
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
      this.modCount=modCount+1;
      eraseIndexHelper(arr,index,size);
      this.size=size;
      return true;
    }
    @Override
    void uncheckedToString(final int size,final StringBuilder builder)
    {
      int modCount=this.modCount;
      try
      {
        reverseToString(arr,0,size,builder);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    private static class CheckedDescendingItr<E> implements OmniIterator.OfRef<E>
    {
      private transient final CheckedStack<E> root;
      private transient int cursor;
      private transient int lastRet=-1;
      private transient int modCount;
      private CheckedDescendingItr(CheckedStack<E> root)
      {
        this.root=root;
        cursor=root.size;
        modCount=root.modCount;
      }
      @Override
      public void forEachRemaining(Consumer<? super E> action)
      {
        final int cursor;
        if((cursor=this.cursor)!=0)
        {
          uncheckedForEachRemaining(cursor,action);
        }
      }
      @Override
      public boolean hasNext()
      {
        return cursor!=0;
      }
      @SuppressWarnings("unchecked")
      @Override
      public E next()
      {
        final CheckedStack<E> root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        int cursor;
        if((cursor=this.cursor)!=0)
        {
          lastRet=--cursor;
          this.cursor=cursor;
          return (E)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override
      public void remove()
      {
        final int lastRet;
        if((lastRet=this.lastRet)!=-1)
        {
          final CheckedStack<E> root;
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
      private void uncheckedForEachRemaining(int cursor,Consumer<? super E> action)
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
  public static abstract class Unchecked<E> extends RefArrSeq<E>
  {
    private Unchecked()
    {
      super();
    }
    private Unchecked(final int capacity)
    {
      super(capacity);
    }
    private Unchecked(final int size,final Object[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean add(final E val)
    {
      super.push(val);
      return true;
    }
    public void add(final int index,final E val)
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
    public void clear()
    {
      final int size;
      if((size=this.size)!=0)
      {
        OmniArray.OfRef.nullifyRange(arr,0,size-1);
        this.size=0;
      }
    }
    @SuppressWarnings("unchecked")
    public E get(final int index)
    {
      return (E)arr[index];
    }
    public E remove(final int index)
    {
      final Object[] arr;
      @SuppressWarnings("unchecked")
      final var removed=(E)(arr=this.arr)[index];
      eraseIndexHelper(arr,index,--size);
      return removed;
    }
    @SuppressWarnings("unchecked")
    @Override
    boolean uncheckedRemoveIf(int size,final Predicate<? super E> filter)
    {
      int srcOffset;
      srcOffset=0;
      while(!filter.test((E)arr[srcOffset]))
      {
        if(++srcOffset==size)
        {
          return false;
        }
      }
      OmniArray.OfRef.nullifyRange(arr,srcOffset=pullSurvivorsDown(arr,srcOffset,--size,filter),size);
      this.size=srcOffset;
      return true;
    }
  }
  public static class UncheckedList<E> extends Unchecked<E> implements OmniList.OfRef<E>
  {
    public UncheckedList()
    {
      super();
    }
    public UncheckedList(final int capacity)
    {
      super(capacity);
    }
    public UncheckedList(final int size,final Object[] arr)
    {
      super(size,arr);
    }
    @Override
    public Object clone()
    {
      final Object[] arr;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,arr=new Object[size],0,size);
      }
      else
      {
        arr=null;
      }
      return new UncheckedList<E>(size,arr);
    }
    @Override
    public boolean equals(final Object val)
    {
      //TODO implements equals method
      return false;
    }
    @Override
    public OmniIterator.OfRef<E> iterator()
    {
      return new AscendingItr<E>(this);
    }
    @Override
    public OmniListIterator.OfRef<E> listIterator()
    {
      return new BidirectionalItr<E>(this);
    }
    @Override
    public OmniListIterator.OfRef<E> listIterator(final int index)
    {
      return new BidirectionalItr<E>(this,index);
    }
    @Override
    public void put(final int index,final E val)
    {
      arr[index]=val;
    }
    @Override
    public void replaceAll(final UnaryOperator<E> operator)
    {
      final int size;
      if(0!=((size=this.size)))
      {
        RefArrSeq.uncheckedreplaceAll(arr,0,size,operator);
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
    public void sort(final Comparator<? super E> sorter)
    {
      final int size;
      if(1<((size=this.size)))
      {
        SortUtil.uncheckedcomparatorSort(arr,0,size-1,sorter);
      }
    }
    @Override
    public OmniList.OfRef<E> subList(final int fromIndex,final int toIndex)
    {
      return new SubList<E>(this,fromIndex,toIndex-fromIndex);
    }
    @Override
    void uncheckedCopyInto(final Object[] arr,final int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedForEach(final int size,final Consumer<? super E> action)
    {
      uncheckedForwardForEach(arr,0,size,action);
    }
    @Override
    int uncheckedHashCode(final int size)
    {
      return forwardHashCode(arr,0,size);
    }
    @Override
    boolean uncheckedremoveVal(int size,final Predicate<Object> pred)
    {
      final var arr=this.arr;int index;
      for(index=0,--size;!pred.test(arr[index]);++index)
      {
        if(index==size)
        {
          return false;
        }
      }
      eraseIndexHelper(arr,index,size);
      this.size=size;
      return true;
    }
    @Override
    boolean uncheckedremoveValNonNull(int size,final Object nonNull)
    {
      final var arr=this.arr;int index;
      for(index=--size;!nonNull.equals(arr[index]);--index)
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
    private static class AscendingItr<E> implements OmniIterator.OfRef<E>
    {
      private transient final UncheckedList<E> root;
      private transient int cursor;
      private AscendingItr(UncheckedList<E> root)
      {
        this.root=root;
      }
      @Override
      public void forEachRemaining(Consumer<? super E> action)
      {
        final UncheckedList<E> root;
        final int cursor,bound;
        if((cursor=this.cursor)!=(bound=(root=this.root).size))
        {
          uncheckedForwardForEach(root.arr,cursor,bound,action);
          this.cursor=bound;
        }
      }
      @Override
      public boolean hasNext()
      {
        return cursor!=root.size;
      }
      @SuppressWarnings("unchecked")
      @Override
      public E next()
      {
        return (E)root.arr[cursor++];
      }
      @Override
      public void remove()
      {
        final UncheckedList<E> root;
        eraseIndexHelper((root=this.root).arr,--cursor,--root.size);
      }
    }
    private static class BidirectionalItr<E> implements OmniListIterator.OfRef<E>
    {
      private transient final UncheckedList<E> root;
      private transient int cursor;
      private transient int lastRet;
      private BidirectionalItr(final UncheckedList<E> root)
      {
        this.root=root;
      }
      private BidirectionalItr(final UncheckedList<E> root,final int cursor)
      {
        this.root=root;
        this.cursor=cursor;
      }
      @Override
      public void add(final E val)
      {
        final RefArrSeq<E> root;
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
      public void forEachRemaining(Consumer<? super E> action)
      {
        final int cursor,bound;
        final Unchecked<E> root;
        if((cursor=this.cursor)!=(bound=(root=this.root).size))
        {
          uncheckedForwardForEach(root.arr,cursor,bound,action);
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
      @SuppressWarnings("unchecked")
      @Override
      public E next()
      {
        final int lastRet;
        this.lastRet=lastRet=cursor++;
        return (E)root.arr[lastRet];
      }
      @Override
      public int nextIndex()
      {
        return cursor;
      }
      @SuppressWarnings("unchecked")
      @Override
      public E previous()
      {
        final int lastRet;
        this.lastRet=lastRet=--cursor;
        return (E)root.arr[lastRet];
      }
      @Override
      public int previousIndex()
      {
        return cursor-1;
      }
      @Override
      public void remove()
      {
        final UncheckedList<E> root;
        final int lastRet;
        eraseIndexHelper((root=this.root).arr,lastRet=this.lastRet,--root.size);
        cursor=lastRet;
      }
      @Override
      public void set(E val)
      {
        root.arr[lastRet]=val;
      }
    }
    private static class SubList<E> extends AbstractSeq implements OmniList.OfRef<E>
    {
      private static <E> void bubbleUpDecrementSize(SubList<E> parent)
      {
        while(parent!=null)
        {
          --parent.size;
          parent=parent.parent;
        }
      }
      private static <E> void bubbleUpDecrementSize(SubList<E> parent,final int numToRemove)
      {
        while(parent!=null)
        {
          parent.size-=numToRemove;
          parent=parent.parent;
        }
      }
      private static <E> void bubbleUpIncrementSize(SubList<E> parent)
      {
        while(parent!=null)
        {
          ++parent.size;
          parent=parent.parent;
        }
      }
      transient final Unchecked<E> root;
      transient final SubList<E> parent;
      transient final int rootOffset;
      private SubList(final Unchecked<E> root,final int rootOffset,final int size)
      {
        super(size);
        this.root=root;
        this.parent=null;
        this.rootOffset=rootOffset;
      }
      private SubList(final Unchecked<E> root,final SubList<E> parent,final int rootOffset,final int size)
      {
        super(size);
        this.root=root;
        this.parent=parent;
        this.rootOffset=rootOffset;
      }
      @Override
      public boolean add(final E val)
      {
        final RefArrSeq<E> root;
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
      public void add(final int index,final E val)
      {
        final RefArrSeq<E> root;
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
          final Unchecked<E> root;
          final int oldRootSize;
          (root=this.root).size=newRootSize=(oldRootSize=root.size)-size;
          final Object[] arr;
          ArrCopy.semicheckedCopy(arr=root.arr,size+(size=rootOffset),arr,size,newRootSize-size);
          OmniArray.OfRef.nullifyRange(arr,newRootSize,oldRootSize-1);
        }
      }
      @Override
      public Object clone()
      {
        final Object[] arr;
        final int size;
        if((size=this.size)!=0)
        {
          ArrCopy.uncheckedCopy(root.arr,rootOffset,arr=new Object[size],0,size);
        }
        else
        {
          arr=null;
        }
        return new UncheckedList<E>(size,arr);
      }
      @Override
      public boolean contains(Object val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          {
            return uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
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
          {
            return uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
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
          {
            return uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
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
          return this.uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
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
            return this.uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
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
          return this.uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
      }
      @Override
      public boolean contains(final float val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
      }
      @Override
      public boolean contains(final double val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
      }
      @Override
      public boolean contains(final byte val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
      }
      @Override
      public boolean contains(final char val)
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
          }
        }
        return false;
      }
      @Override
      public boolean contains(final short val)
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
          }
        }
        return false;
      }
      @Override
      public boolean contains(final Boolean val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
      }
      @Override
      public boolean contains(final Byte val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
      }
      @Override
      public boolean contains(final Character val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
      }
      @Override
      public boolean contains(final Short val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
      }
      @Override
      public boolean contains(final Integer val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
      }
      @Override
      public boolean contains(final Long val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
      }
      @Override
      public boolean contains(final Float val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
      }
      @Override
      public boolean contains(final Double val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedcontains(size,OmniPred.OfRef.getEqualsPred(val));
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
      public void forEach(Consumer<? super E> action)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          uncheckedForwardForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size,action);
        }
      }
      @SuppressWarnings("unchecked")
      @Override
      public E get(int index)
      {
        return (E)root.arr[index+rootOffset];
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
          return this.uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
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
            return this.uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
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
          return this.uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
      }
      @Override
      public int indexOf(final float val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
      }
      @Override
      public int indexOf(final double val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
      }
      @Override
      public int indexOf(final byte val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
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
            return this.uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
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
            return this.uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
          }
        }
        return -1;
      }
      @Override
      public int indexOf(final Boolean val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
      }
      @Override
      public int indexOf(final Byte val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
      }
      @Override
      public int indexOf(final Character val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
      }
      @Override
      public int indexOf(final Short val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
      }
      @Override
      public int indexOf(final Integer val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
      }
      @Override
      public int indexOf(final Long val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
      }
      @Override
      public int indexOf(final Float val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
      }
      @Override
      public int indexOf(final Double val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedindexOf(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
      }
      @Override
      public int lastIndexOf(final boolean val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
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
            return this.uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
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
          return this.uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
      }
      @Override
      public int lastIndexOf(final float val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
      }
      @Override
      public int lastIndexOf(final double val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
      }
      @Override
      public int lastIndexOf(final byte val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
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
            return this.uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
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
            return this.uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
          }
        }
        return -1;
      }
      @Override
      public int lastIndexOf(final Boolean val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
      }
      @Override
      public int lastIndexOf(final Byte val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
      }
      @Override
      public int lastIndexOf(final Character val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
      }
      @Override
      public int lastIndexOf(final Short val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
      }
      @Override
      public int lastIndexOf(final Integer val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
      }
      @Override
      public int lastIndexOf(final Long val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
      }
      @Override
      public int lastIndexOf(final Float val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
      }
      @Override
      public int lastIndexOf(final Double val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedlastIndexOf(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return -1;
      }
      @Override
      public OmniIterator.OfRef<E> iterator()
      {
        return new AscendingItr<E>(this);
      }
      @Override
      public OmniListIterator.OfRef<E> listIterator()
      {
        return new BidirectionalItr<E>(this);
      }
      @Override
      public OmniListIterator.OfRef<E> listIterator(int index)
      {
        return new BidirectionalItr<E>(this,index+rootOffset);
      }
      @Override
      public void put(int index,E val)
      {
        root.arr[index+rootOffset]=val;
      }     
      @Override
      public boolean remove(Object val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          {
            return uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
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
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
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
            return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
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
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
      }
      @Override
      public boolean removeVal(final float val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
      }
      @Override
      public boolean removeVal(final double val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
      }
      @Override
      public boolean removeVal(final byte val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
      }
      @Override
      public boolean removeVal(final char val)
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
          }
        }
        return false;
      }
      @Override
      public boolean removeVal(final short val)
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
          }
        }
        return false;
      }
      @Override
      public boolean removeVal(final Boolean val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
      }
      @Override
      public boolean removeVal(final Byte val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
      }
      @Override
      public boolean removeVal(final Character val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
      }
      @Override
      public boolean removeVal(final Short val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
      }
      @Override
      public boolean removeVal(final Integer val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
      }
      @Override
      public boolean removeVal(final Long val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
      }
      @Override
      public boolean removeVal(final Float val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
      }
      @Override
      public boolean removeVal(final Double val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
        }
        return false;
      }
      @Override
      public E remove(int index)
      {
        final Unchecked<E> root;
        final Object[] arr;
        @SuppressWarnings("unchecked")
        final var removed=(E)(arr=(root=this.root).arr)[index+=rootOffset];
        eraseIndexHelper(arr,index,--root.size);
        bubbleUpDecrementSize(parent);
        --size;
        return removed;
      }
      @Override
      public boolean removeIf(final Predicate<? super E> filter)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return uncheckedRemoveIf(size,filter);
        }
        return false;
      }
      @Override
      public void replaceAll(final UnaryOperator<E> operator)
      {
        final int size;
        if(0!=((size=this.size)))
        {
          final int rootOffset;
          RefArrSeq.uncheckedreplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);
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
      public void sort(final Comparator<? super E> sorter)
      {
        final int size;
        if(1<((size=this.size)))
        {
          final int rootOffset;
          SortUtil.uncheckedcomparatorSort(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,sorter);
        }
      }
      @Override
      public E set(int index,E val)
      {
        final Object[] arr;
        @SuppressWarnings("unchecked")
        final var oldVal=(E)(arr=root.arr)[index+=rootOffset];
        arr[index]=val;
        return oldVal;
      }
      @Override
      public OmniList.OfRef<E> subList(int fromIndex,int toIndex)
      {
        return new SubList<E>(root,this,this.rootOffset+fromIndex,toIndex-fromIndex);
      }
      @Override
      public Object[] toArray()
      {
        final int size;
        if((size=this.size)!=0)
        {
          final Object[] dst;
          ArrCopy.uncheckedCopy(root.arr,rootOffset,dst=new Object[size],0,size);
          return dst;
        }
        return OmniArray.OfRef.DEFAULT_ARR;
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
      private boolean uncheckedcontains(int size,Predicate<Object> pred)
      { 
        final int rootOffset;
        return RefArrSeq.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size,pred);
      }
      private int uncheckedindexOf(int size,Predicate<Object> pred)
      { 
        final int rootOffset;
        return RefArrSeq.uncheckedindexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,pred);
      }
      private int uncheckedlastIndexOf(int size,Predicate<Object> pred)
      { 
        final int rootOffset;
        return RefArrSeq.uncheckedlastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,pred);
      }
      @SuppressWarnings("unchecked")
      private boolean uncheckedRemoveIf(int size,final Predicate<? super E> filter)
      {
        final Unchecked<E> root;
        int srcOffset;
        final int srcBound=(srcOffset=rootOffset)+size;
        final var arr=(root=this.root).arr;
        while(!filter.test((E)arr[srcOffset]))
        {
          if(++srcOffset==srcBound)
          {
            return false;
          }
        }
        this.size=size-(size=((RefArrSeq<E>)root).finalizeSubListBatchRemove(arr,pullSurvivorsDown(arr,srcOffset,srcBound-1,filter),srcBound));
        bubbleUpDecrementSize(parent,size);
        return true;
      }
      private boolean uncheckedremoveVal(int size,Predicate<Object> pred)
      {
        final Unchecked<E> root;final var arr=(root=this.root).arr;int index;final int bound=(index=this.rootOffset)+(--size);
        for(;!pred.test(arr[index]);)
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
      private static class AscendingItr<E> implements OmniIterator.OfRef<E>
      {
        private transient final SubList<E> parent;
        private transient int cursor;
        private AscendingItr(SubList<E> parent)
        {
           this.parent=parent;
           this.cursor=parent.rootOffset;
        }
        @Override
        public void forEachRemaining(Consumer<? super E> action)
        {
          final int cursor,bound;
          final SubList<E> parent;
          if((cursor=this.cursor)!=(bound=(parent=this.parent).rootOffset+parent.size))
          {
            uncheckedForwardForEach(parent.root.arr,cursor,bound,action);
            this.cursor=bound;
          }
        }
        @Override
        public boolean hasNext()
        {
          final SubList<E> parent;
          return cursor!=(parent=this.parent).rootOffset+parent.size;
        }
        @SuppressWarnings("unchecked")
        @Override
        public E next()
        {
          return (E)parent.root.arr[cursor++];
        }
        @Override
        public void remove()
        {
          final SubList<E> parent;
          final Unchecked<E> root;
          eraseIndexHelper((root=(parent=this.parent).root).arr,--cursor,--root.size);
          bubbleUpDecrementSize(parent.parent);
          --parent.size;
        }
      }
      private static class BidirectionalItr<E> implements OmniListIterator.OfRef<E>
      {
        private transient final SubList<E> parent;
        private transient int cursor;
        private transient int lastRet;
        private BidirectionalItr(final SubList<E> parent)
        {
          this.parent=parent;
          this.cursor=parent.rootOffset;
        }
        private BidirectionalItr(final SubList<E> parent,final int cursor)
        {
          this.parent=parent;
          this.cursor=cursor;
        }
        @Override
        public void add(final E val)
        {
          final SubList<E> parent;
          final RefArrSeq<E> root;
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
        public void forEachRemaining(Consumer<? super E> action)
        {
          final int cursor,bound;
          final SubList<E> parent;
          if((cursor=this.cursor)!=(bound=(parent=this.parent).rootOffset+parent.size))
          {
            uncheckedForwardForEach(parent.root.arr,cursor,bound,action);
            this.cursor=bound;
            lastRet=bound-1;
          }
        }
        @Override
        public boolean hasNext()
        {
          final SubList<E> parent;
          return cursor!=(parent=this.parent).rootOffset+parent.size;
        }
        @Override
        public boolean hasPrevious()
        {
          return cursor!=parent.rootOffset;
        }
        @SuppressWarnings("unchecked")
        @Override
        public E next()
        {
          final int lastRet;
          this.lastRet=lastRet=cursor++;
          return (E)parent.root.arr[lastRet];
        }
        @Override
        public int nextIndex()
        {
          return cursor-parent.rootOffset;
        }
        @SuppressWarnings("unchecked")
        @Override
        public E previous()
        {
          final int lastRet;
          this.lastRet=lastRet=--cursor;
          return (E)parent.root.arr[lastRet];
        }
        @Override
        public int previousIndex()
        {
          return cursor-parent.rootOffset-1;
        }
        @Override
        public void remove()
        {
          final SubList<E> parent;
          final Unchecked<E> root;
          final int lastRet;
          eraseIndexHelper((root=(parent=this.parent).root).arr,lastRet=this.lastRet,--root.size);
          bubbleUpDecrementSize(parent.parent);
          --parent.size;
          cursor=lastRet;
        }
        @Override
        public void set(E val)
        {
          parent.root.arr[lastRet]=val;
        }
      }
    }
  }
  public static class UncheckedStack<E> extends Unchecked<E> implements OmniStack.OfRef<E>
  {
    public UncheckedStack()
    {
      super();
    }
    public UncheckedStack(final int capacity)
    {
      super(capacity);
    }
    public UncheckedStack(final int size,final Object[] arr)
    {
      super(size,arr);
    }
    @Override
    public Object clone()
    {
      final Object[] arr;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,arr=new Object[size],0,size);
      }
      else
      {
        arr=null;
      }
      return new UncheckedStack<E>(size,arr);
    }
    @Override
    public boolean equals(final Object val)
    {
      //TODO implements equals method
      return false;
    }
    @Override
    public OmniIterator.OfRef<E> iterator()
    {
      return new UncheckedDescendingItr<E>(this);
    }
        @Override
        public E poll()
        {
          final int size;
          if((size=this.size)!=0)
          {
            return (E)(((RefArrSeq<E>)this).uncheckedPop(size-1));
          }
          return null;
        }
    @Override
    void uncheckedCopyInto(final Object[] arr,final int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,arr,0,size);
    }
    @Override
    void uncheckedForEach(final int size,final Consumer<? super E> action)
    {
      uncheckedReverseForEach(arr,0,size,action);
    }
    @Override
    int uncheckedHashCode(final int size)
    {
      return reverseHashCode(arr,0,size);
    }
    @Override
    boolean uncheckedremoveVal(int size,final Predicate<Object> pred)
    {
      final var arr=this.arr;int index;
      for(index=--size;!pred.test(arr[index]);--index)
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
    boolean uncheckedremoveValNonNull(int size,final Object nonNull)
    {
      final var arr=this.arr;int index;
      for(index=--size;!nonNull.equals(arr[index]);--index)
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
    private static class UncheckedDescendingItr<E> implements OmniIterator.OfRef<E>
    {
      private transient final UncheckedStack<E> root;
      private transient int cursor;
      private UncheckedDescendingItr(UncheckedStack<E> root)
      {
        this.root=root;
        cursor=root.size;
      }
      @Override
      public void forEachRemaining(Consumer<? super E> action)
      {
        final int cursor;
        if((cursor=this.cursor)!=0)
        {
          uncheckedReverseForEach(root.arr,0,cursor,action);
          this.cursor=0;
        }
      }
      @Override
      public boolean hasNext()
      {
        return cursor!=0;
      }
      @SuppressWarnings("unchecked")
      @Override
      public E next()
      {
        return (E)root.arr[--cursor];
      }
      @Override
      public void remove()
      {
        final UncheckedStack<E> root;
        eraseIndexHelper((root=this.root).arr,cursor,--root.size);
      }
    }
  }
}
