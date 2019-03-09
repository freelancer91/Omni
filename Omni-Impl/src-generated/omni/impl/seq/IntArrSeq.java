package omni.impl.seq;
import omni.api.OmniCollection;
import omni.util.OmniArray;
import omni.api.OmniList;
import omni.api.OmniStack;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.Comparator;
import omni.util.ArrCopy;
import omni.util.IntSortUtil;
import omni.impl.CheckedCollection;
import java.util.NoSuchElementException;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;
import omni.util.TypeUtil;
import java.util.ConcurrentModificationException;
import java.util.function.IntUnaryOperator;
import java.util.function.IntBinaryOperator;
import java.util.function.IntPredicate;
import java.util.function.IntConsumer;
import omni.util.ToStringUtil;
import omni.util.BitSetUtil;
public abstract class IntArrSeq implements OmniCollection.OfInt
{
  transient int size;
  transient int[] arr;
  private IntArrSeq()
  {
    super();
  }
  private IntArrSeq(int initialCapacity)
  {
    super();
    switch(initialCapacity)
    { 
    default:
      this.arr=new int[initialCapacity];
      return;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfInt.DEFAULT_ARR;
    case 0:
    }
  }
  private IntArrSeq(int size,int[] arr)
  {
    super();
    this.size=size;
    this.arr=arr;
  }
  @Override
  public void clear()
  {
    this.size=0;
  }
  @Override
  public boolean isEmpty()
  {
    return this.size==0;
  }
  @Override
  public int size()
  {
    return this.size;
  }
  @Override
  public boolean removeIf(IntPredicate filter)
  {
    int size;
    if((size=this.size)!=0)
    {
      int newBound;
      if(size!=(newBound=doRemoveIf(this.arr,0,size,filter)))
      {
        this.size=newBound;
        return true;
      }
    }
    return false;
  }
  @Override
  public boolean removeIf(Predicate<? super Integer> filter)
  {
    int size;
    if((size=this.size)!=0)
    {
      int newBound;
      if(size!=(newBound=doRemoveIf(this.arr,0,size,filter::test)))
      {
        this.size=newBound;
        return true;
      }
    }
    return false;
  }
  static void uncheckedForEachAscending(int[] arr,int offset,int bound,IntConsumer action)
  {
    do
    {
      action.accept((int)arr[offset]);
    }
    while(++offset!=bound);
  }
  static void uncheckedForEachDescending(int[] arr,int offset,int bound,IntConsumer action)
  {
    do
    {
      action.accept((int)arr[--bound]);
    }
    while(bound!=offset);
  }
  private static int doRemoveIf(int[] arr,int srcOffset,int srcBound,IntPredicate filter)
  {
    do
    {
      if(filter.test((int)arr[srcOffset]))
      {
        return pullSurvivorsDown(arr,srcOffset,srcBound,filter);
      }
    }
    while(++srcOffset!=srcBound);
    return srcBound;
  }
    private static  int pullSurvivorsDown(int[] arr,int srcOffset,int srcBound,IntPredicate filter)
    {
      int dstOffset=srcOffset;
      while(++srcOffset!=srcBound)
      {
        final int v;
        if(!filter.test((int)(v=arr[srcOffset])))
        {
          arr[dstOffset++]=v;
        }
      }
      return dstOffset;
    }
    private static int doRemoveIf(int[] arr,int srcOffset,int srcBound,IntPredicate filter,CheckedCollection.AbstractModCountChecker modCountChecker)
    {
      try
      {
        do
        {
          if(filter.test((int)arr[srcOffset]))
          {
            int dstOffset=srcOffset;
            while(++srcOffset!=srcBound)
            {
              final int v;
              if(!filter.test(v=arr[srcOffset]))
              {
                int newDstOffset=markAndPullDown(arr,srcOffset+1,srcBound,filter,dstOffset+1,modCountChecker);
                arr[dstOffset]=v;
                return newDstOffset;
              }
            }
            modCountChecker.checkModCount();
            return dstOffset;
          }
        }
        while(++srcOffset!=srcBound);
        modCountChecker.checkModCount();
        return srcBound;
      }
      catch(final ConcurrentModificationException e)
      {
        throw e;
      }
      catch(final RuntimeException e)
      {
        throw modCountChecker.handleException(e);
      }
    }
    private static int markAndPullDown(int[] arr,int srcOffset,int srcBound,IntPredicate filter,int dstOffset,CheckedCollection.AbstractModCountChecker modCountChecker)
    {
      int nRemaining;
      if((nRemaining=(srcBound-srcOffset))!=0)
      {
        if(nRemaining>64)
        {
          long[] survivorSet;
          int numSurvivors=BitSetUtil.markSurvivors(arr,srcOffset,srcBound,filter,survivorSet=BitSetUtil.getBitSet(nRemaining));
          modCountChecker.checkModCount();
          if(numSurvivors!=0)
          {
            if(numSurvivors==nRemaining)
            {
              System.arraycopy(arr,srcOffset,arr,dstOffset,numSurvivors);
              dstOffset+=numSurvivors;
            }
            else
            {
              BitSetUtil.pullSurvivorsDown(arr,srcOffset,dstOffset,dstOffset+=numSurvivors,survivorSet);
            }
          }
        }
        else
        {
          long word=BitSetUtil.markSurvivors(arr,srcOffset,srcBound,filter);
          modCountChecker.checkModCount();
          int numSurvivors;
          if((numSurvivors=Long.bitCount(word))!=0)
          {
            if(numSurvivors==nRemaining)
            {
              ArrCopy.uncheckedCopy(arr,srcOffset,arr,dstOffset,numSurvivors);
              dstOffset+=numSurvivors;
            }
            else
            {
              BitSetUtil.pullSurvivorsDown(arr,srcOffset,dstOffset,dstOffset+=numSurvivors,word);
            }
          }
        }
      }
      else
      {
        modCountChecker.checkModCount();
      }
      return dstOffset;
    }
  @Override
  public boolean add(boolean val)
  {
    push((int)TypeUtil.castToByte(val));
    return true;
  }
  @Override
  public boolean add(int val)
  {
    push(val);
    return true;
  }
  @Override
  public boolean add(Integer val)
  {
    push((int)val);
    return true;
  }
  @Override
  public int hashCode()
  {
    int size;
    if((size=this.size)!=0)
    {
      return uncheckedHashCode(size);
    }
    return 1;
  }
  @Override
  public String toString()
  {
    int size;
    if((size=this.size)!=0)
    {
      if(size>(Integer.MAX_VALUE/3))
      {
        throw new OutOfMemoryError();
      }
      final char[] buffer;
      if(size<=(OmniArray.MAX_ARR_SIZE/((13)<<1)))
      {
        (buffer=new char[size*(13)])[size=uncheckedToString(size,buffer)]=']';
        buffer[0]='[';
        return new String(buffer,0,size+1);
      }
      else
      {
        final ToStringUtil.OmniStringBuilder builder;
        uncheckedToString(size,builder=new ToStringUtil.OmniStringBuilder(1,new char[size<=(int)(OmniArray.MAX_ARR_SIZE>>>3)?(size<<3):OmniArray.MAX_ARR_SIZE]));
        builder.uncheckedAppendChar(']');
        (buffer=builder.buffer)[0]='[';
        return new String(buffer,0,builder.size);
      }
    }
    return "[]";
  }
  @Override
  public <T> T[] toArray(T[] dst)
  {
    int size;
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
  private boolean uncheckedcontains (int size
  ,int val
  )
  {
    for(final var arr=this.arr;;)
    {
      if(
      val==arr[--size]
      )
      {
        return true;
      }
      if(size==0)
      {
        return false;
      }
    }
  }
  abstract boolean uncheckedremoveVal(int size,int val);
  @Override
  public
  boolean
  removeVal(boolean val)
  {
    int size;
    if((size=this.size)!=0)
    {
      return uncheckedremoveVal(size,(int)TypeUtil.castToByte(val));
    }
    return false;
  }
  @Override
  public
  boolean
  removeVal(int val)
  {
    int size;
    if((size=this.size)!=0)
    {
      return uncheckedremoveVal(size,(val));
    }
    return false;
  }
  @Override
  public
  boolean
  removeVal(long val)
  {
    int size;
    if((size=this.size)!=0)
    {
      final int v;
      if(val==(v=(int)val))
      {
        return uncheckedremoveVal(size,v);
      }
    }
    return false;
  }
  @Override
  public
  boolean
  removeVal(float val)
  {
    int size;
    if((size=this.size)!=0)
    {
      final int v;
      if((double)val==(double)(v=(int)val))
      {
        return uncheckedremoveVal(size,v);
      }
    }
    return false;
  }
  @Override
  public
  boolean
  removeVal(double val)
  {
    int size;
    if((size=this.size)!=0)
    {
      final int v;
      if((v=(int)val)==val)
      {
        return uncheckedremoveVal(size,v);
      }
    }
    return false;
  }
  @Override
  public
  boolean
  remove
  (Object val)
  {
    int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Integer)
      {
        return uncheckedremoveVal(size,(int)(val));
      }
    }
    return false;
  }
  @Override
  public
  boolean
  removeVal(byte val)
  {
    {
      int size;
      if((size=this.size)!=0)
      {
        return uncheckedremoveVal(size,(val));
      }
    }
    return false;
  }
  @Override
  public
  boolean
  removeVal(char val)
  {
    {
      int size;
      if((size=this.size)!=0)
      {
        return uncheckedremoveVal(size,(val));
      }
    }
    return false;
  }
  @Override
  public
  boolean
  contains(boolean val)
  {
    int size;
    if((size=this.size)!=0)
    {
      return uncheckedcontains(size,(int)TypeUtil.castToByte(val));
    }
    return false;
  }
  @Override
  public
  boolean
  contains(int val)
  {
    int size;
    if((size=this.size)!=0)
    {
      return uncheckedcontains(size,(val));
    }
    return false;
  }
  @Override
  public
  boolean
  contains(long val)
  {
    int size;
    if((size=this.size)!=0)
    {
      final int v;
      if(val==(v=(int)val))
      {
        return uncheckedcontains(size,v);
      }
    }
    return false;
  }
  @Override
  public
  boolean
  contains(float val)
  {
    int size;
    if((size=this.size)!=0)
    {
      final int v;
      if((double)val==(double)(v=(int)val))
      {
        return uncheckedcontains(size,v);
      }
    }
    return false;
  }
  @Override
  public
  boolean
  contains(double val)
  {
    int size;
    if((size=this.size)!=0)
    {
      final int v;
      if((v=(int)val)==val)
      {
        return uncheckedcontains(size,v);
      }
    }
    return false;
  }
  @Override
  public
  boolean
  contains
  (Object val)
  {
    int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Integer)
      {
        return uncheckedcontains(size,(int)(val));
      }
    }
    return false;
  }
  @Override
  public
  boolean
  contains(byte val)
  {
    {
      int size;
      if((size=this.size)!=0)
      {
        return uncheckedcontains(size,(val));
      }
    }
    return false;
  }
  @Override
  public
  boolean
  contains(char val)
  {
    {
      int size;
      if((size=this.size)!=0)
      {
        return uncheckedcontains(size,(val));
      }
    }
    return false;
  }
  abstract void uncheckedCopyInto(Object[] dst,int size);
  abstract void uncheckedCopyInto(int[] dst,int size);
  @Override
  public int[] toIntArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final int[] copy;
      uncheckedCopyInto(copy=new int[size],size);
      return copy;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  abstract void uncheckedCopyInto(Integer[] dst,int size);
  @Override
  public Integer[] toArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final Integer[] copy;
      uncheckedCopyInto(copy=new Integer[size],size);
      return copy;
    }
    return OmniArray.OfInt.DEFAULT_BOXED_ARR;
  }
  abstract void uncheckedCopyInto(double[] dst,int size);
  @Override
  public double[] toDoubleArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final double[] copy;
      uncheckedCopyInto(copy=new double[size],size);
      return copy;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  abstract void uncheckedCopyInto(float[] dst,int size);
  @Override
  public float[] toFloatArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final float[] copy;
      uncheckedCopyInto(copy=new float[size],size);
      return copy;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  abstract void uncheckedCopyInto(long[] dst,int size);
  @Override
  public long[] toLongArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final long[] copy;
      uncheckedCopyInto(copy=new long[size],size);
      return copy;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  abstract int uncheckedHashCode(int size);
  abstract int uncheckedToString(int size,char[] buffer);
  abstract void uncheckedToString(int size,ToStringUtil.OmniStringBuilder builder);
  abstract void uncheckedForEach(int size,IntConsumer action);
  private void uncheckedInit(int val)
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
  private void uncheckedAppend(int size,int val)
  {
    int[] arr;
    if((arr=this.arr).length==size)
    {
      ArrCopy.uncheckedCopy(arr,0,arr=new int[OmniArray.growBy50Pct(size)],0,size);
      this.arr=arr;
    }
    arr[size]=val;
    this.size=size+1;
  }
  public void push(int val)
  {
    int size;
    if((size=this.size)!=0)
    {
      uncheckedAppend(size,val);
    }
    else
    {
      uncheckedInit(val);
    }
  }
  private void uncheckedInsert(int size,int index,int val)
  {
    int tailDist;
    if((tailDist=size-index)==0)
    {
      uncheckedAppend(size,val);
    }
    else
    {
      int[] arr;
      if((arr=this.arr).length==size)
      {
        int[] newArr;
        ArrCopy.semicheckedCopy(arr,0,newArr=new int[OmniArray.growBy50Pct(size)],0,index);
        ArrCopy.uncheckedCopy(arr,index,newArr,index+1,tailDist);
        this.arr=arr=newArr;
      }
      else
      {
        ArrCopy.uncheckedCopy(arr,index,arr,index+1,tailDist);
      }
      arr[index]=val;
      this.size=size+1;
    }
  }
  public void push(Integer val)
  {
    push((int)val);
  }
  private int uncheckedremoveAtIndex(int index,int size)
  {
    final int[] arr;
    final int ret=(int)(arr=this.arr)[index];
    ArrCopy.semicheckedSelfCopy(arr,index+1,index,(--size)-index);
    this.size=size;
    return ret;
  }
  public
  static class UncheckedStack
    extends IntArrSeq
    implements OmniStack.OfInt
  {
    public UncheckedStack()
    {
      super();
    }
    public UncheckedStack(int initialCapacity)
    {
      super(initialCapacity);
    }
    private UncheckedStack(int size,int[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implement equals(Object) method
      return false;
    }
    @Override
    public Object clone()
    {
      final int[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new int[size],0,size);
      }
      else
      {
        copy=OmniArray.OfInt.DEFAULT_ARR;
      }
      return new UncheckedStack(size,copy);
    }
    @Override
    public OmniIterator.OfInt iterator()
    {
      return new Itr(this);
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor)
    {
      final int size;
      T[] dst=arrConstructor.apply(size=this.size);
      if(size!=0)
      {
        ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,size);
      }
      return dst;
    }
    @Override
    void uncheckedCopyInto(int[] dst,int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(Integer[] dst,int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(double[] dst,int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(float[] dst,int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(long[] dst,int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,size);
    }
    private static class Itr
      extends AbstractItr
    {
      transient final UncheckedStack parent;
      transient int cursor;
      private Itr(UncheckedStack parent)
      {
        this.parent=parent;
        this.cursor=parent.size;
      }
      @Override
      public int nextInt()
      {
            return (int)parent.arr[this.cursor--];
      }
      @Override
      public void forEachRemaining(IntConsumer action)
      {
        int cursor;
        if((cursor=this.cursor)>0)
        {
          {
            uncheckedForEachDescending(this.parent.arr,0,cursor,action);
          }
          this.cursor=0;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Integer> action)
      {
        int cursor;
        if((cursor=this.cursor)>0)
        {
          {
            uncheckedForEachDescending(this.parent.arr,0,cursor,action::accept);
          }
          this.cursor=0;
        }
      }
      @Override
      public boolean hasNext()
      {
        return this.cursor>0;
      }
      @Override
      public void remove()
      {
        final UncheckedStack parent;
        int cursor;
        ArrCopy.semicheckedSelfCopy((parent=this.parent).arr,(cursor=this.cursor)+1,cursor,(--parent.size)-cursor);
      }
    }
    @Override
    boolean
    uncheckedremoveVal (int size
    ,int val
    )
    {
      final var arr=this.arr;
      for(int i=--size;;)
      {
        if(
        val==arr[i]
        )
        {
          ArrCopy.semicheckedSelfCopy(arr,i+1,i,size-i);
          this.size=size;
          return true;
        }
        if(i==0)
        {
          return false;
        }
      }
    }
     @Override
     public void forEach(IntConsumer action)
     {
       int size;
       if((size=this.size)!=0)
       {
         {
           uncheckedForEachDescending(arr,0,size,action);
         }
       }
     }
    @Override
    public void forEach(Consumer<? super Integer> action)
    {
      int size;
      if((size=this.size)!=0)
      {
        {
          uncheckedForEachDescending(arr,0,size,action::accept);
        }
      }
    }
    @Override
    public int popInt()
    {
      return (int)arr[--this.size];
    }
    @Override
    public int pollInt()
    {
      int size;
      if((size=this.size)!=0)
      {
        final int ret=(int)((int)arr[--size]);
        this.size=size;
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override
    public Integer poll()
    {
      int size;
      if((size=this.size)!=0)
      {
        final Integer ret=(Integer)((int)arr[--size]);
        this.size=size;
        return ret;
      }
      return null;
    }
    @Override
    public double pollDouble()
    {
      int size;
      if((size=this.size)!=0)
      {
        final double ret=(double)((int)arr[--size]);
        this.size=size;
        return ret;
      }
      return Double.NaN;
    }
    @Override
    public float pollFloat()
    {
      int size;
      if((size=this.size)!=0)
      {
        final float ret=(float)((int)arr[--size]);
        this.size=size;
        return ret;
      }
      return Float.NaN;
    }
    @Override
    public long pollLong()
    {
      int size;
      if((size=this.size)!=0)
      {
        final long ret=(long)((int)arr[--size]);
        this.size=size;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override
    public Integer pop()
    {
      return popInt();
    }
    @Override
    public
    int
    search(boolean val)
    {
      int size;
      if((size=this.size)!=0)
      {
        return uncheckedsearch(size,(int)TypeUtil.castToByte(val));
      }
      return -1;
    }
    @Override
    public
    int
    search(int val)
    {
      int size;
      if((size=this.size)!=0)
      {
        return uncheckedsearch(size,(val));
      }
      return -1;
    }
    @Override
    public
    int
    search(long val)
    {
      int size;
      if((size=this.size)!=0)
      {
        final int v;
        if(val==(v=(int)val))
        {
          return uncheckedsearch(size,v);
        }
      }
      return -1;
    }
    @Override
    public
    int
    search(float val)
    {
      int size;
      if((size=this.size)!=0)
      {
        final int v;
        if((double)val==(double)(v=(int)val))
        {
          return uncheckedsearch(size,v);
        }
      }
      return -1;
    }
    @Override
    public
    int
    search(double val)
    {
      int size;
      if((size=this.size)!=0)
      {
        final int v;
        if((v=(int)val)==val)
        {
          return uncheckedsearch(size,v);
        }
      }
      return -1;
    }
    @Override
    public
    int
    search
    (Object val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Integer)
        {
          return uncheckedsearch(size,(int)(val));
        }
      }
      return -1;
    }
    public int peekInt()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (int)((int)arr[size-1]);
      }
      return Integer.MIN_VALUE;
    }
    public Integer peek()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (Integer)((int)arr[size-1]);
      }
      return null;
    }
    public double peekDouble()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (double)((int)arr[size-1]);
      }
      return Double.NaN;
    }
    public float peekFloat()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (float)((int)arr[size-1]);
      }
      return Float.NaN;
    }
    public long peekLong()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (long)((int)arr[size-1]);
      }
      return Long.MIN_VALUE;
    }
    private int uncheckedsearch (int size
    ,int val
    )
    {
      final var arr=this.arr;
      for(int i=size-1;;--i)
      {
        if(
        val==arr[i]
        )
        {
          return size-i;
        }
        if(i==0)
        {
          return -1;
        }
      }
    }
    @Override
    void uncheckedForEach(int size,IntConsumer action)
    {
      {
        uncheckedForEachDescending(this.arr,0,size,action);
      }
    }
    @Override
    void uncheckedCopyInto(Object[] dst,int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,size);
    }
    @Override
    int uncheckedHashCode(int size)
    {
      final int[] arr;
      int hash;
      for(hash=31+((arr=this.arr)[--size]);size!=0;hash=hash*31+(arr[--size])){}
      return hash;
    }
    @Override
    int uncheckedToString(int size,char[] buffer)
    {
      final int[] arr;
      int bufferOffset;
      for(bufferOffset=ToStringUtil.getStringInt((arr=this.arr)[--size],buffer,1);size!=0;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringInt(arr[--size],buffer,bufferOffset)){}
      return bufferOffset;
    }
    @Override
    void uncheckedToString(int size,ToStringUtil.OmniStringBuilder builder)
    {
      final int[] arr;
      for(builder.uncheckedAppendInt((arr=this.arr)[--size]);size!=0;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendInt(arr[--size])){}
    }
  }
  public
  static class CheckedStack
    extends UncheckedStack
  {
    transient int modCount;
    public CheckedStack()
    {
      super();
    }
    public CheckedStack(int initialCapacity)
    {
      super(initialCapacity);
    }
    private CheckedStack(int size,int[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implement equals(Object) method
      return false;
    }
    @Override
    public Object clone()
    {
      final int[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new int[size],0,size);
      }
      else
      {
        copy=OmniArray.OfInt.DEFAULT_ARR;
      }
      return new CheckedStack(size,copy);
    }
    @Override
    public OmniIterator.OfInt iterator()
    {
      return new Itr(this);
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor)
    {
      return super.toArray((arrSize)->
      {
        int modCount=this.modCount;
        try
        {
          return arrConstructor.apply(arrSize);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      });
    }
    private static class Itr
      extends AbstractItr
    {
      transient final CheckedStack parent;
      transient int cursor;
      transient int modCount;
      transient int lastRet;
      private Itr(CheckedStack parent)
      {
        this.parent=parent;
        this.cursor=parent.size;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      @Override
      public int nextInt()
      {
            final int cursor;
            final CheckedStack root;
            CheckedCollection.checkModCount(this.modCount,(root=this.parent).modCount);
            if((cursor=this.cursor)<0)
            {
              throw new NoSuchElementException();
            }
            this.cursor=cursor-1;
            this.lastRet=cursor;
            return (int)root.arr[cursor];
      }
      @Override
      public void forEachRemaining(IntConsumer action)
      {
        int cursor;
        if((cursor=this.cursor)>0)
        {
          final CheckedStack root=this.parent;
          int modCount=this.modCount;
          try
          {
            uncheckedForEachDescending(root.arr,0,cursor,action);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
          this.lastRet=0;
          this.cursor=0;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Integer> action)
      {
        int cursor;
        if((cursor=this.cursor)>0)
        {
          final CheckedStack root=this.parent;
          int modCount=this.modCount;
          try
          {
            uncheckedForEachDescending(root.arr,0,cursor,action::accept);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
          this.lastRet=0;
          this.cursor=0;
        }
      }
      @Override
      public boolean hasNext()
      {
        return this.cursor>0;
      }
      @Override
      public void remove()
      {
        int cursor;
        if((cursor=this.lastRet)==-1)
        {
          throw new IllegalStateException();
        }
        int modCount;
        final CheckedStack root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.parent).modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        ArrCopy.semicheckedSelfCopy(root.arr,cursor+1,cursor,(--root.size)-cursor);
        this.cursor=cursor;
        this.lastRet=-1;
      }
    }
    @Override
    boolean
    uncheckedremoveVal (int size
    ,int val
    )
    {
      final var arr=this.arr;
      for(int i=--size;;)
      {
        if(
        val==arr[i]
        )
        {
          ++this.modCount;
          ArrCopy.semicheckedSelfCopy(arr,i+1,i,size-i);
          this.size=size;
          return true;
        }
        if(i==0)
        {
          return false;
        }
      }
    }
     @Override
     public void forEach(IntConsumer action)
     {
       int size;
       if((size=this.size)!=0)
       {
         int modCount=this.modCount;
         try
         {
           uncheckedForEachDescending(arr,0,size,action);
         }
         finally
         {
           CheckedCollection.checkModCount(modCount,this.modCount);
         }
       }
     }
    @Override
    public void forEach(Consumer<? super Integer> action)
    {
      int size;
      if((size=this.size)!=0)
      {
        int modCount=this.modCount;
        try
        {
          uncheckedForEachDescending(arr,0,size,action::accept);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override
    public int popInt()
    {
      int size;
      if((size=this.size)!=0)
      {
        ++this.modCount;
        final int ret=(int)arr[--size];
        this.size=size;
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override
    public int pollInt()
    {
      int size;
      if((size=this.size)!=0)
      {
        final int ret=(int)((int)arr[--size]);
        ++this.modCount;
        this.size=size;
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override
    public Integer poll()
    {
      int size;
      if((size=this.size)!=0)
      {
        final Integer ret=(Integer)((int)arr[--size]);
        ++this.modCount;
        this.size=size;
        return ret;
      }
      return null;
    }
    @Override
    public double pollDouble()
    {
      int size;
      if((size=this.size)!=0)
      {
        final double ret=(double)((int)arr[--size]);
        ++this.modCount;
        this.size=size;
        return ret;
      }
      return Double.NaN;
    }
    @Override
    public float pollFloat()
    {
      int size;
      if((size=this.size)!=0)
      {
        final float ret=(float)((int)arr[--size]);
        ++this.modCount;
        this.size=size;
        return ret;
      }
      return Float.NaN;
    }
    @Override
    public long pollLong()
    {
      int size;
      if((size=this.size)!=0)
      {
        final long ret=(long)((int)arr[--size]);
        ++this.modCount;
        this.size=size;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override
    void uncheckedForEach(int size,IntConsumer action)
    {
      int modCount=this.modCount;
      try
      {
        uncheckedForEachDescending(this.arr,0,size,action);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override
    public boolean removeIf(IntPredicate filter)
    {
      int size;
      if((size=this.size)!=0)
      {
        int newBound;
        int modCount;
        if(size!=(newBound=doRemoveIf(this.arr,0,size,filter,new CheckedCollection.AbstractModCountChecker(modCount=this.modCount){
          @Override
          protected int getActualModCount(){
            return CheckedStack.this.modCount;
          }
        })))
        {
          this.modCount=modCount+1;
          this.size=newBound;
          return true;
        }
      }
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Integer> filter)
    {
      int size;
      if((size=this.size)!=0)
      {
        int newBound;
        int modCount;
        if(size!=(newBound=doRemoveIf(this.arr,0,size,filter::test,new CheckedCollection.AbstractModCountChecker(modCount=this.modCount){
          @Override
          protected int getActualModCount(){
            return CheckedStack.this.modCount;
          }
        })))
        {
          this.modCount=modCount+1;
          this.size=newBound;
          return true;
        }
      }
      return false;
    }
    public void push(int val)
    {
      ++this.modCount;
      super.push(val);
    }
    @Override
    public void clear()
    {
      if(this.size!=0)
      {
        ++this.modCount;
        this.size=0;
      }
    }
  }
  public
  static class UncheckedList
    extends IntArrSeq
    implements ListDefault
  {
    public UncheckedList()
    {
      super();
    }
    public UncheckedList(int initialCapacity)
    {
      super(initialCapacity);
    }
    private UncheckedList(int size,int[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implement equals(Object) method
      return false;
    }
    @Override
    public Object clone()
    {
      final int[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new int[size],0,size);
      }
      else
      {
        copy=OmniArray.OfInt.DEFAULT_ARR;
      }
      return new UncheckedList(size,copy);
    }
    @Override
    public OmniIterator.OfInt iterator()
    {
      return new Itr(this);
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor)
    {
      final int size;
      T[] dst=arrConstructor.apply(size=this.size);
      if(size!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,dst,0,size);
      }
      return dst;
    }
    @Override
    void uncheckedCopyInto(int[] dst,int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(Integer[] dst,int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(double[] dst,int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(float[] dst,int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(long[] dst,int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,size);
    }
    private static class Itr
      extends AbstractItr
    {
      transient final UncheckedList parent;
      transient int cursor;
      private Itr(UncheckedList parent)
      {
        this.parent=parent;
      }
      private Itr(UncheckedList parent,int cursor)
      {
        this.parent=parent;
        this.cursor=cursor;
      }
      @Override
      public int nextInt()
      {
            return (int)parent.arr[this.cursor++];
      }
      @Override
      public void forEachRemaining(IntConsumer action)
      {
        int cursor;
        int bound;
        final UncheckedList root;
        if((cursor=this.cursor)<(bound=(root=this.parent).size))
        {
          {
            uncheckedForEachAscending(root.arr,cursor,bound,action);
          }
          this.cursor=bound;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Integer> action)
      {
        int cursor;
        int bound;
        final UncheckedList root;
        if((cursor=this.cursor)<(bound=(root=this.parent).size))
        {
          {
            uncheckedForEachAscending(root.arr,cursor,bound,action::accept);
          }
          this.cursor=bound;
        }
      }
      @Override
      public boolean hasNext()
      {
        return this.cursor<parent.size;
      }
      @Override
      public void remove()
      {
        final UncheckedList parent;
        int cursor;
        ArrCopy.semicheckedSelfCopy((parent=this.parent).arr,cursor=this.cursor,--cursor,(--parent.size)-cursor);
        this.cursor=cursor;
      }
    }
    @Override
    boolean
    uncheckedremoveVal (int size
    ,int val
    )
    {
      final var arr=this.arr;
      for(int i=0;;)
      {
        if(
        val==arr[i]
        )
        {
          ArrCopy.semicheckedSelfCopy(arr,i+1,i,(--size)-i);
          this.size=size;
          return true;
        }
        if(++i==size)
        {
          return false;
        }
      }
    }
     @Override
     public void forEach(IntConsumer action)
     {
       int size;
       if((size=this.size)!=0)
       {
         {
           uncheckedForEachAscending(arr,0,size,action);
         }
       }
     }
    @Override
    public void forEach(Consumer<? super Integer> action)
    {
      int size;
      if((size=this.size)!=0)
      {
        {
          uncheckedForEachAscending(arr,0,size,action::accept);
        }
      }
    }
    private void uncheckedAdd(int index,int val,int size)
    {
      if(size!=0)
      {
        super.uncheckedInsert(size,index,val);
      }
      else
      {
        super.uncheckedInit(val);
      }
    }
    /*
    private void sublistForEach(int rootOffset,int size,IntConsumer action)
    {
      final var arr=this.arr;
      for(size+=rootOffset;;)
      {
        action.accept((int)arr[rootOffset]);
        if(++rootOffset==size)
        {
          return;
        }
      }
    }
    */
    private void uncheckedReplaceAll(int offset,int bound,IntUnaryOperator operator)
    {
      final var arr=this.arr;
      for(;;)
      {
        arr[offset]=operator.applyAsInt((int)arr[offset]);
        if(++offset==bound)
        {
          return;
        }
      }
    }
    private boolean sublistcontains (int rootOffset,int size
    ,int val
    )
    {
      final var arr=this.arr;
      for(size+=rootOffset;;)
      {
        if(
        val==arr[rootOffset]
        )
        {
          return true;
        }
        if(++rootOffset==size)
        {
          return false;
        }
      }
    }
    private int sublistindexOf (int rootOffset,int size
    ,int val
    )
    {
      final var arr=this.arr;
      int i;
      for(size+=(i=rootOffset);;)
      {
        if(
        val==arr[i]
        )
        {
          return i-rootOffset;
        }
        if(++i==size)
        {
          return -1;
        }
      }
    }
    private int uncheckedindexOf (int size
    ,int val
    )
    {
      final var arr=this.arr;
      for(int i=0;;)
      {
        if(
        val==arr[i]
        )
        {
          return i;
        }
        if(++i==size)
        {
          return -1;
        }
      }
    }
    private int sublistlastIndexOf (int rootOffset,int size
    ,int val
    )
    {
      final var arr=this.arr;
      for(size+=(rootOffset-1);;--size)
      {
        if(
        val==arr[size]
        )
        {
          return size-rootOffset;
        }
        if(size==rootOffset)
        {
          return -1;
        }
      }
    }
    private int uncheckedlastIndexOf (int size
    ,int val
    )
    {
      final var arr=this.arr;
      for(;;)
      {
        if(
        val==arr[--size]
        )
        {
          return size;
        }
        if(size==0)
        {
          return -1;
        }
      }
    }
    @Override
    void uncheckedForEach(int size,IntConsumer action)
    {
      {
        uncheckedForEachAscending(this.arr,0,size,action);
      }
    }
    @Override
    void uncheckedCopyInto(Object[] dst,int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,size);
    }
    @Override
    int uncheckedHashCode(int size)
    {
      final int[] arr;
      int hash;
      int i;
      for(hash=31+((arr=this.arr)[i=0]);++i!=size;hash=hash*31+(arr[i])){}
      return hash;
    }
    @Override
    int uncheckedToString(int size,char[] buffer)
    {
      final int[] arr;
      int bufferOffset;
      int i;
      for(bufferOffset=ToStringUtil.getStringInt((arr=this.arr)[i=0],buffer,1);++i!=size;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringInt(arr[i],buffer,bufferOffset)){}
      return bufferOffset;
    }
    @Override
    void uncheckedToString(int size,ToStringUtil.OmniStringBuilder builder)
    {
      final int[] arr;
      int i;
      for(builder.uncheckedAppendInt((arr=this.arr)[i=0]);++i!=size;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendInt(arr[i])){}
    }
     private static class LstItr
       extends Itr implements OmniListIterator.OfInt
     {
       transient int lastRet;
       private LstItr(UncheckedList parent)
       {
         super(parent);
         this.lastRet=-1;
       }
       private LstItr(UncheckedList parent,int cursor)
       {
         super(parent);
         this.lastRet=-1;
       }
       @Override
       public int nextInt()
       {
             final int ret;
             this.lastRet=ret=this.cursor++;
             return (int)parent.arr[ret];
       }
       @Override
       public void forEachRemaining(IntConsumer action)
       {
         int cursor;
         int bound;
         final UncheckedList root;
         if((cursor=this.cursor)<(bound=(root=this.parent).size))
         {
           {
             uncheckedForEachAscending(root.arr,cursor,bound,action);
           }
           this.lastRet=bound-1;
           this.cursor=bound;
         }
       }
       @Override
       public void forEachRemaining(Consumer<? super Integer> action)
       {
         int cursor;
         int bound;
         final UncheckedList root;
         if((cursor=this.cursor)<(bound=(root=this.parent).size))
         {
           {
             uncheckedForEachAscending(root.arr,cursor,bound,action::accept);
           }
           this.lastRet=bound-1;
           this.cursor=bound;
         }
       }
       @Override
       public void remove()
       {
         final UncheckedList parent;
         int cursor;
         ArrCopy.semicheckedSelfCopy((parent=this.parent).arr,(cursor=this.lastRet)+1,cursor,(--parent.size)-cursor);
         this.cursor=cursor;
         this.lastRet=-1;
       }
       @Override
       public boolean hasPrevious()
       {
         return this.cursor>0;
       }
       @Override
       public int nextIndex()
       {
         return this.cursor;
       }
       @Override
       public int previousIndex()
       {
         return this.cursor-1;
       }
       @Override
       public int previousInt()
       {
             final int ret;
             this.lastRet=ret=--this.cursor;
             return (int)parent.arr[ret];
       }
       @Override
       public void add(int val)
       {
         //TODO implement LstItr.add(int)
       }
       @Override
       public void set(int val)
       {
         //TODO implement LstItr.set(int)
       }
     }
    @Override
    public OmniList.OfInt subList(int fromIndex,int toIndex)
    {
      return new UncheckedSubList(this,fromIndex,toIndex-fromIndex);
    }
    @Override
    public OmniListIterator.OfInt listIterator()
    {
      return new LstItr(this);
    }
    @Override
    public OmniListIterator.OfInt listIterator(int index)
    {
      return new LstItr(this,index);
    }
    @Override
    public void sort(IntBinaryOperator sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        {
          if(sorter==null)
          {
            IntSortUtil.uncheckedAscendingSort(this.arr,0,size);
          }
          else
          {
            IntSortUtil.uncheckedStableSort(this.arr,0,size,sorter);
          }
        }
      }
    }
    @Override
    public void sort(Comparator<? super Integer> sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        {
          if(sorter==null)
          {
            IntSortUtil.uncheckedAscendingSort(this.arr,0,size);
          }
          else
          {
            IntSortUtil.uncheckedStableSort(this.arr,0,size,sorter::compare);
          }
        }
      }
    }
    @Override
    public void unstableSort(IntBinaryOperator sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        {
          if(sorter==null)
          {
            IntSortUtil.uncheckedAscendingSort(this.arr,0,size);
          }
          else
          {
            IntSortUtil.uncheckedUnstableSort(this.arr,0,size,sorter);
          }
        }
      }
    }
    @Override
    public void stableAscendingSort()
    {
      int size;
      if((size=this.size)>1)
      {
        {
          IntSortUtil.uncheckedAscendingSort(this.arr,0,size);
        }
      }
    }
    @Override
    public void stableDescendingSort()
    {
      int size;
      if((size=this.size)>1)
      {
        {
          IntSortUtil.uncheckedDescendingSort(this.arr,0,size);
        }
      }
    }
    @Override
    public void replaceAll(IntUnaryOperator operator)
    {
      int size;
      if((size=this.size)!=0)
      {
        {
          ((UncheckedList)this).uncheckedReplaceAll(0,size,operator);
        }
      }
    }
    @Override
    public void replaceAll(UnaryOperator<Integer> operator)
    {
      int size;
      if((size=this.size)!=0)
      {
        {
          ((UncheckedList)this).uncheckedReplaceAll(0,size,operator::apply);
        }
      }
    }
    @Override
    public void add(int index,int val)
    {
      ((UncheckedList)this).uncheckedAdd(index,val,size);
    }
    @Override
    public int getInt(int index)
    {
      return (int)arr[index];
    }
    @Override
    public void put(int index,int val)
    {
      arr[index]=val;
    }
    @Override
    public int set(int index,int val)
    {
      final int[] arr;
      final int ret=(int)(arr=this.arr)[index];
      arr[index]=val;
      return ret;
    }
    @Override
    public int removeIntAt(int index)
    {
      return ((IntArrSeq)this).uncheckedremoveAtIndex(index,size);
    }
    //TODO implement AND and OR in the template switches to avoid writing this twice
    @Override
    public
    int
    indexOf(boolean val)
    {
      int size;
      if((size=this.size)!=0)
      {
        return uncheckedindexOf(size,(int)TypeUtil.castToByte(val));
      }
      return -1;
    }
    @Override
    public
    int
    indexOf(int val)
    {
      int size;
      if((size=this.size)!=0)
      {
        return uncheckedindexOf(size,(val));
      }
      return -1;
    }
    @Override
    public
    int
    indexOf(long val)
    {
      int size;
      if((size=this.size)!=0)
      {
        final int v;
        if(val==(v=(int)val))
        {
          return uncheckedindexOf(size,v);
        }
      }
      return -1;
    }
    @Override
    public
    int
    indexOf(float val)
    {
      int size;
      if((size=this.size)!=0)
      {
        final int v;
        if((double)val==(double)(v=(int)val))
        {
          return uncheckedindexOf(size,v);
        }
      }
      return -1;
    }
    @Override
    public
    int
    indexOf(double val)
    {
      int size;
      if((size=this.size)!=0)
      {
        final int v;
        if((v=(int)val)==val)
        {
          return uncheckedindexOf(size,v);
        }
      }
      return -1;
    }
    @Override
    public
    int
    indexOf
    (Object val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Integer)
        {
          return uncheckedindexOf(size,(int)(val));
        }
      }
      return -1;
    }
    //TODO implement AND and OR in the template switches to avoid writing this twice
    @Override
    public
    int
    lastIndexOf(boolean val)
    {
      int size;
      if((size=this.size)!=0)
      {
        return uncheckedlastIndexOf(size,(int)TypeUtil.castToByte(val));
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf(int val)
    {
      int size;
      if((size=this.size)!=0)
      {
        return uncheckedlastIndexOf(size,(val));
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf(long val)
    {
      int size;
      if((size=this.size)!=0)
      {
        final int v;
        if(val==(v=(int)val))
        {
          return uncheckedlastIndexOf(size,v);
        }
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf(float val)
    {
      int size;
      if((size=this.size)!=0)
      {
        final int v;
        if((double)val==(double)(v=(int)val))
        {
          return uncheckedlastIndexOf(size,v);
        }
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf(double val)
    {
      int size;
      if((size=this.size)!=0)
      {
        final int v;
        if((v=(int)val)==val)
        {
          return uncheckedlastIndexOf(size,v);
        }
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf
    (Object val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Integer)
        {
          return uncheckedlastIndexOf(size,(int)(val));
        }
      }
      return -1;
    }
  }
  public
  static class CheckedList
    extends UncheckedList
  {
    transient int modCount;
    public CheckedList()
    {
      super();
    }
    public CheckedList(int initialCapacity)
    {
      super(initialCapacity);
    }
    private CheckedList(int size,int[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implement equals(Object) method
      return false;
    }
    @Override
    public Object clone()
    {
      final int[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new int[size],0,size);
      }
      else
      {
        copy=OmniArray.OfInt.DEFAULT_ARR;
      }
      return new CheckedList(size,copy);
    }
    @Override
    public OmniIterator.OfInt iterator()
    {
      return new Itr(this);
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor)
    {
      return super.toArray((arrSize)->
      {
        int modCount=this.modCount;
        try
        {
          return arrConstructor.apply(arrSize);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      });
    }
    private static class Itr
      extends AbstractItr
    {
      transient final CheckedList parent;
      transient int cursor;
      transient int modCount;
      transient int lastRet;
      private Itr(CheckedList parent)
      {
        this.parent=parent;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      private Itr(CheckedList parent,int cursor)
      {
        this.parent=parent;
        this.cursor=cursor;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      @Override
      public int nextInt()
      {
            final int cursor;
            final CheckedList root;
            CheckedCollection.checkModCount(this.modCount,(root=this.parent).modCount);
            if((cursor=this.cursor)>=root.size)
            {
               throw new NoSuchElementException();
            }
            this.cursor=cursor+1;
            this.lastRet=cursor;
            return (int)root.arr[cursor];
      }
      @Override
      public void forEachRemaining(IntConsumer action)
      {
        int cursor;
        int bound;
        final CheckedList root;
        if((cursor=this.cursor)<(bound=(root=this.parent).size))
        {
          int modCount=this.modCount;
          try
          {
            uncheckedForEachAscending(root.arr,cursor,bound,action);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
          this.lastRet=bound-1;
          this.cursor=bound;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Integer> action)
      {
        int cursor;
        int bound;
        final CheckedList root;
        if((cursor=this.cursor)<(bound=(root=this.parent).size))
        {
          int modCount=this.modCount;
          try
          {
            uncheckedForEachAscending(root.arr,cursor,bound,action::accept);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
          this.lastRet=bound-1;
          this.cursor=bound;
        }
      }
      @Override
      public boolean hasNext()
      {
        return this.cursor<parent.size;
      }
      @Override
      public void remove()
      {
        int cursor;
        if((cursor=this.lastRet)==-1)
        {
          throw new IllegalStateException();
        }
        int modCount;
        final CheckedList root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.parent).modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        ArrCopy.semicheckedSelfCopy(root.arr,cursor+1,cursor,(--root.size)-cursor);
        this.cursor=cursor;
        this.lastRet=-1;
      }
    }
    @Override
    boolean
    uncheckedremoveVal (int size
    ,int val
    )
    {
      final var arr=this.arr;
      for(int i=0;;)
      {
        if(
        val==arr[i]
        )
        {
          ++this.modCount;
          ArrCopy.semicheckedSelfCopy(arr,i+1,i,(--size)-i);
          this.size=size;
          return true;
        }
        if(++i==size)
        {
          return false;
        }
      }
    }
     @Override
     public void forEach(IntConsumer action)
     {
       int size;
       if((size=this.size)!=0)
       {
         int modCount=this.modCount;
         try
         {
           uncheckedForEachAscending(arr,0,size,action);
         }
         finally
         {
           CheckedCollection.checkModCount(modCount,this.modCount);
         }
       }
     }
    @Override
    public void forEach(Consumer<? super Integer> action)
    {
      int size;
      if((size=this.size)!=0)
      {
        int modCount=this.modCount;
        try
        {
          uncheckedForEachAscending(arr,0,size,action::accept);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override
    void uncheckedForEach(int size,IntConsumer action)
    {
      int modCount=this.modCount;
      try
      {
        uncheckedForEachAscending(this.arr,0,size,action);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override
    public boolean removeIf(IntPredicate filter)
    {
      int size;
      if((size=this.size)!=0)
      {
        int newBound;
        int modCount;
        if(size!=(newBound=doRemoveIf(this.arr,0,size,filter,new CheckedCollection.AbstractModCountChecker(modCount=this.modCount){
          @Override
          protected int getActualModCount(){
            return CheckedList.this.modCount;
          }
        })))
        {
          this.modCount=modCount+1;
          this.size=newBound;
          return true;
        }
      }
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Integer> filter)
    {
      int size;
      if((size=this.size)!=0)
      {
        int newBound;
        int modCount;
        if(size!=(newBound=doRemoveIf(this.arr,0,size,filter::test,new CheckedCollection.AbstractModCountChecker(modCount=this.modCount){
          @Override
          protected int getActualModCount(){
            return CheckedList.this.modCount;
          }
        })))
        {
          this.modCount=modCount+1;
          this.size=newBound;
          return true;
        }
      }
      return false;
    }
    public void push(int val)
    {
      ++this.modCount;
      super.push(val);
    }
    @Override
    public void clear()
    {
      if(this.size!=0)
      {
        ++this.modCount;
        this.size=0;
      }
    }
     private static class LstItr
       extends Itr implements OmniListIterator.OfInt
     {
       private LstItr(CheckedList parent)
       {
         super(parent);
       }
       private LstItr(CheckedList parent,int cursor)
       {
         super(parent);
       }
       @Override
       public boolean hasPrevious()
       {
         return this.cursor>0;
       }
       @Override
       public int nextIndex()
       {
         return this.cursor;
       }
       @Override
       public int previousIndex()
       {
         return this.cursor-1;
       }
       @Override
       public int previousInt()
       {
             int cursor;
             final CheckedList root;
             CheckedCollection.checkModCount(this.modCount,(root=this.parent).modCount);
             if((cursor=this.cursor)<=0)
             {
                throw new NoSuchElementException();
             }
             this.cursor=--cursor;
             this.lastRet=cursor;
             return (int)root.arr[cursor];
       }
       @Override
       public void add(int val)
       {
         //TODO implement LstItr.add(int)
       }
       @Override
       public void set(int val)
       {
         //TODO implement LstItr.set(int)
       }
     }
    @Override
    public OmniList.OfInt subList(int fromIndex,int toIndex)
    {
      int span;
      if((span=toIndex-fromIndex)<0|| fromIndex<0 ||  toIndex>this.size)
      {
        throw new InvalidReadIndexException("fromIndex="+fromIndex+"; toIndex="+toIndex+"; size="+this.size);
      }
      return new CheckedSubList(this,fromIndex,span,modCount);
    }
    @Override
    public OmniListIterator.OfInt listIterator()
    {
      return new LstItr(this);
    }
    @Override
    public OmniListIterator.OfInt listIterator(int index)
    {
      if(index<0 || size<index)
      {
        throw new InvalidWriteIndexException(index,size);
      }
      return new LstItr(this,index);
    }
    @Override
    public void sort(IntBinaryOperator sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        {
          if(sorter==null)
          {
            IntSortUtil.uncheckedAscendingSort(this.arr,0,size);
          }
          else
          {
            IntSortUtil.uncheckedStableSort(this.arr,0,size,sorter);
          }
          ++this.modCount;
        }
      }
    }
    @Override
    public void sort(Comparator<? super Integer> sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        {
          if(sorter==null)
          {
            IntSortUtil.uncheckedAscendingSort(this.arr,0,size);
          }
          else
          {
            IntSortUtil.uncheckedStableSort(this.arr,0,size,sorter::compare);
          }
          ++this.modCount;
        }
      }
    }
    @Override
    public void unstableSort(IntBinaryOperator sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        {
          if(sorter==null)
          {
            IntSortUtil.uncheckedAscendingSort(this.arr,0,size);
          }
          else
          {
            IntSortUtil.uncheckedUnstableSort(this.arr,0,size,sorter);
          }
          ++this.modCount;
        }
      }
    }
    @Override
    public void stableAscendingSort()
    {
      int size;
      if((size=this.size)>1)
      {
        {
          IntSortUtil.uncheckedAscendingSort(this.arr,0,size);
          ++this.modCount;
        }
      }
    }
    @Override
    public void stableDescendingSort()
    {
      int size;
      if((size=this.size)>1)
      {
        {
          IntSortUtil.uncheckedDescendingSort(this.arr,0,size);
          ++this.modCount;
        }
      }
    }
    @Override
    public void replaceAll(IntUnaryOperator operator)
    {
      int size;
      if((size=this.size)!=0)
      {
        int modCount=this.modCount;
        try
        {
          ((UncheckedList)this).uncheckedReplaceAll(0,size,operator);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override
    public void replaceAll(UnaryOperator<Integer> operator)
    {
      int size;
      if((size=this.size)!=0)
      {
        int modCount=this.modCount;
        try
        {
          ((UncheckedList)this).uncheckedReplaceAll(0,size,operator::apply);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override
    public void add(int index,int val)
    {
      int size;
      if((size=this.size)<index || index<0)
      {
        throw new InvalidWriteIndexException(index,size);
      }
      ++this.modCount;
      ((UncheckedList)this).uncheckedAdd(index,val,size);
    }
    @Override
    public int getInt(int index)
    {
      if(index<0 || index>=size)
      {
        throw new InvalidReadIndexException(index,size);
      }
      return (int)arr[index];
    }
    @Override
    public void put(int index,int val)
    {
      if(index<0 || index>=size)
      {
        throw new InvalidWriteIndexException(index,size);
      }
      arr[index]=val;
    }
    @Override
    public int set(int index,int val)
    {
      if(index<0 || index>=size)
      {
        throw new InvalidReadIndexException(index,size);
      }
      final int[] arr;
      final int ret=(int)(arr=this.arr)[index];
      arr[index]=val;
      return ret;
    }
    @Override
    public int removeIntAt(int index)
    {
      int size;
      if((size=this.size)<=index || index<0)
      {
        throw new InvalidWriteIndexException(index,size);
      }
      ++this.modCount;
      return ((IntArrSeq)this).uncheckedremoveAtIndex(index,size);
    }
  }
  private
  static class UncheckedSubList
    implements SubListDefault
  {
    transient final UncheckedList root;
    transient final UncheckedSubList parent;
    transient final int rootOffset;
    transient int size;
    private UncheckedSubList(UncheckedList root,int rootOffset,int size
    )
    {
      this.root=root;
      this.parent=null;
      this.rootOffset=rootOffset;
      this.size=size;
    }
    private UncheckedSubList(UncheckedSubList parent,int rootOffset,int size
    )
    {
      this.root=parent.root;
      this.parent=parent;
      this.rootOffset=rootOffset;
      this.size=size;
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implement equals(Object) method
      return false;
    }
    @Override
    public Object clone()
    {
      final int[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new int[size],0,size);
      }
      else
      {
        copy=OmniArray.OfInt.DEFAULT_ARR;
      }
      return new UncheckedList(size,copy);
    }
    @Override
    public OmniIterator.OfInt iterator()
    {
      return new Itr(this);
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor)
    {
      final int size;
      final T[] dst;
      {
        dst=arrConstructor.apply(size=this.size);
      }
      if(size!=0)
      {
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst,0,size);
      }
      return dst;
    }
    @Override
    public int[] toIntArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final int[] copy;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new int[size],0,size);
        return copy;
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
    @Override
    public Integer[] toArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final Integer[] copy;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new Integer[size],0,size);
        return copy;
      }
      return OmniArray.OfInt.DEFAULT_BOXED_ARR;
    }
    @Override
    public double[] toDoubleArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final double[] copy;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new double[size],0,size);
        return copy;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override
    public float[] toFloatArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final float[] copy;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new float[size],0,size);
        return copy;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override
    public long[] toLongArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final long[] copy;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new long[size],0,size);
        return copy;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    private static class Itr
      extends AbstractItr
    {
      transient final UncheckedSubList parent;
      transient int cursor;
      private Itr(UncheckedSubList parent)
      {
        this.parent=parent;
        this.cursor=parent.rootOffset;
      }
      private Itr(UncheckedSubList parent,int cursor)
      {
        this.parent=parent;
        this.cursor=cursor+parent.rootOffset;
      }
      @Override
      public int nextInt()
      {
            return (int)parent.root.arr[this.cursor++];
      }
      @Override
      public void forEachRemaining(IntConsumer action)
      {
        int cursor;
        int bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
        {
          {
            uncheckedForEachAscending(parent.root.arr,cursor,bound,action);
          }
          this.cursor=bound;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Integer> action)
      {
        int cursor;
        int bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
        {
          {
            uncheckedForEachAscending(parent.root.arr,cursor,bound,action::accept);
          }
          this.cursor=bound;
        }
      }
      @Override
      public boolean hasNext()
      {
        final UncheckedSubList parent;
        return this.cursor<(parent=this.parent).rootOffset+parent.size;
      }
      @Override
      public void remove()
      {
        UncheckedSubList parent;
        final UncheckedList root;
        int cursor;
        ArrCopy.semicheckedSelfCopy((root=(parent=this.parent).root).arr,cursor=this.cursor,--cursor,(--root.size)-cursor);
        do{
          --parent.size;
        }while((parent=parent.parent)!=null);
        this.cursor=cursor;
      }
    }
    private boolean
    uncheckedremoveVal (int size
    ,int val
    )
    {
      final UncheckedList root;
      final var arr=(root=this.root).arr;
      {
        for(int i=this.rootOffset,bound=i+size;;)
        {
          if(
          val==arr[i]
          )
          {
            for(var curr=parent;curr!=null;--curr.size,curr=curr.parent){}
            this.size=size-1;
            ArrCopy.semicheckedSelfCopy(arr,i+1,i,(--root.size)-i);
            return true;
          }
          if(++i==bound)
          {
            return false;
          }
        }
      }
    }
     @Override
     public void forEach(IntConsumer action)
     {
       int size;
       if((size=this.size)!=0)
       {
         {
           int rootOffset;
           uncheckedForEachAscending(root.arr,rootOffset=this.rootOffset,rootOffset+size,action);
         }
       }
     }
    @Override
    public void forEach(Consumer<? super Integer> action)
    {
      int size;
      if((size=this.size)!=0)
      {
        {
          int rootOffset;
          uncheckedForEachAscending(root.arr,rootOffset=this.rootOffset,rootOffset+size,action::accept);
        }
      }
    }
    @Override
    public boolean removeIf(IntPredicate filter)
    {
      int size;
      if((size=this.size)!=0)
      {
        int newBound;
        final int[] arr;
        final UncheckedList root;
        int rootOffset,rootBound,numRemoved;
        if((numRemoved=(rootBound=(rootOffset=this.rootOffset)+size)-(newBound=doRemoveIf(arr=(root=this.root).arr,rootOffset,rootBound,filter)))!=0)
        {
          for(var curr=parent;curr!=null;curr.size-=numRemoved,curr=curr.parent){}
          int rootSize;
          ArrCopy.semicheckedSelfCopy(arr,rootBound,newBound,(rootSize=root.size)-rootBound);
          root.size=rootSize-numRemoved;
          this.size=size-numRemoved;
          return true;
        }
      }
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Integer> filter)
    {
      int size;
      if((size=this.size)!=0)
      {
        int newBound;
        final int[] arr;
        final UncheckedList root;
        int rootOffset,rootBound,numRemoved;
        if((numRemoved=(rootBound=(rootOffset=this.rootOffset)+size)-(newBound=doRemoveIf(arr=(root=this.root).arr,rootOffset,rootBound,filter::test)))!=0)
        {
          for(var curr=parent;curr!=null;curr.size-=numRemoved,curr=curr.parent){}
          int rootSize;
          ArrCopy.semicheckedSelfCopy(arr,rootBound,newBound,(rootSize=root.size)-rootBound);
          root.size=rootSize-numRemoved;
          this.size=size-numRemoved;
          return true;
        }
      }
      return false;
    }
    @Override
    public
    boolean
    contains(boolean val)
    {
      int size;
      if((size=this.size)!=0)
      {
        return ((UncheckedList)root).sublistcontains(this.rootOffset,size,(int)TypeUtil.castToByte(val));
      }
      return false;
    }
    @Override
    public
    boolean
    contains(int val)
    {
      int size;
      if((size=this.size)!=0)
      {
        return ((UncheckedList)root).sublistcontains(this.rootOffset,size,(val));
      }
      return false;
    }
    @Override
    public
    boolean
    contains(long val)
    {
      int size;
      if((size=this.size)!=0)
      {
        final int v;
        if(val==(v=(int)val))
        {
          return ((UncheckedList)root).sublistcontains(this.rootOffset,size,v);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    contains(float val)
    {
      int size;
      if((size=this.size)!=0)
      {
        final int v;
        if((double)val==(double)(v=(int)val))
        {
          return ((UncheckedList)root).sublistcontains(this.rootOffset,size,v);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    contains(double val)
    {
      int size;
      if((size=this.size)!=0)
      {
        final int v;
        if((v=(int)val)==val)
        {
          return ((UncheckedList)root).sublistcontains(this.rootOffset,size,v);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    contains
    (Object val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Integer)
        {
          return ((UncheckedList)root).sublistcontains(this.rootOffset,size,(int)(val));
        }
      }
      return false;
    }
    @Override
    public
    boolean
    contains(byte val)
    {
      {
        int size;
        if((size=this.size)!=0)
        {
          return ((UncheckedList)root).sublistcontains(this.rootOffset,size,(val));
        }
      }
      return false;
    }
    @Override
    public
    boolean
    contains(char val)
    {
      {
        int size;
        if((size=this.size)!=0)
        {
          return ((UncheckedList)root).sublistcontains(this.rootOffset,size,(val));
        }
      }
      return false;
    }
    @Override
    public int hashCode()
    {
      int size;
      if((size=this.size)!=0)
      {
        final int[] arr;
        int hash;
        int i;
        for(hash=31+((arr=root.arr)[i=this.rootOffset]),size+=i;++i!=size;hash=hash*31+(arr[i])){}
        return hash;
      }
      return 1;
    }
    @Override
    public boolean isEmpty()
    {
      return this.size==0;
    }
    @Override
    public
    boolean
    removeVal(boolean val)
    {
      int size;
      if((size=this.size)!=0)
      {
        return uncheckedremoveVal(size,(int)TypeUtil.castToByte(val));
      }
      return false;
    }
    @Override
    public
    boolean
    removeVal(int val)
    {
      int size;
      if((size=this.size)!=0)
      {
        return uncheckedremoveVal(size,(val));
      }
      return false;
    }
    @Override
    public
    boolean
    removeVal(long val)
    {
      int size;
      if((size=this.size)!=0)
      {
        final int v;
        if(val==(v=(int)val))
        {
          return uncheckedremoveVal(size,v);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    removeVal(float val)
    {
      int size;
      if((size=this.size)!=0)
      {
        final int v;
        if((double)val==(double)(v=(int)val))
        {
          return uncheckedremoveVal(size,v);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    removeVal(double val)
    {
      int size;
      if((size=this.size)!=0)
      {
        final int v;
        if((v=(int)val)==val)
        {
          return uncheckedremoveVal(size,v);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    remove
    (Object val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Integer)
        {
          return uncheckedremoveVal(size,(int)(val));
        }
      }
      return false;
    }
    @Override
    public
    boolean
    removeVal(byte val)
    {
      {
        int size;
        if((size=this.size)!=0)
        {
          return uncheckedremoveVal(size,(val));
        }
      }
      return false;
    }
    @Override
    public
    boolean
    removeVal(char val)
    {
      {
        int size;
        if((size=this.size)!=0)
        {
          return uncheckedremoveVal(size,(val));
        }
      }
      return false;
    }
    @Override
    public int size()
    {
      return this.size;
    }
    @Override
    public <T> T[] toArray(T[] dst)
    {
      int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=OmniArray.uncheckedArrResize(size,dst),0,size);
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
      int size;
      if((size=this.size)!=0)
      {
        if(size>(Integer.MAX_VALUE/3))
        {
          throw new OutOfMemoryError();
        }
        final char[] buffer;
        if(size<=(OmniArray.MAX_ARR_SIZE/((13)<<1)))
        {
          final int[] arr;
          int bufferOffset,i;
          for(bufferOffset=ToStringUtil.getStringInt((arr=root.arr)[i=this.rootOffset],buffer=new char[size*(13)],1),size+=i;++i!=size;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringInt(arr[i],buffer,bufferOffset)){}
          buffer[bufferOffset]=']';
          buffer[0]='[';
          return new String(buffer,0,bufferOffset+1);
        }
        else
        {
          final ToStringUtil.OmniStringBuilder builder;
          final int[] arr;
          int i;
          for((builder=new ToStringUtil.OmniStringBuilder(1,new char[size<=(int)(OmniArray.MAX_ARR_SIZE>>>3)?(size<<3):OmniArray.MAX_ARR_SIZE])).uncheckedAppendInt((arr=root.arr)[i=this.rootOffset]),size+=i;++i!=size;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendInt(arr[i])){}
          builder.uncheckedAppendChar(']');
          (buffer=builder.buffer)[0]='[';
          return new String(buffer,0,builder.size);
        }
      }
      return "[]";
    }
    @Override
    public boolean add(int val)
    {
      int rootSize;
      final UncheckedList root;
      if((rootSize=(root=this.root).size)!=0)
      {
        for(var curr=parent;curr!=null;++curr.size,curr=curr.parent){}
        ((IntArrSeq)root).uncheckedInsert(rootSize,this.rootOffset,val);
        ++this.size;
      }
      else
      {
        for(var curr=parent;curr!=null;curr.size=1,curr=curr.parent){}
        ((IntArrSeq)root).uncheckedInit(val);
        this.size=1;
      }
      return true;
    }
    @Override
    public void clear()
    {
      int size;
      if((size=this.size)!=0)
      {
        for(var curr=parent;curr!=null;parent.size-=size){}
        final UncheckedList root;
        int rootOffset;
        ArrCopy.semicheckedSelfCopy((root=this.root).arr,(rootOffset=this.rootOffset)+size,rootOffset,(root.size-=size)-rootOffset);
        this.size=0;
      }
    }
     private static class LstItr
       extends Itr implements OmniListIterator.OfInt
     {
       transient int lastRet;
       private LstItr(UncheckedSubList parent)
       {
         super(parent);
         this.lastRet=-1;
       }
       private LstItr(UncheckedSubList parent,int cursor)
       {
         super(parent);
         this.lastRet=-1;
       }
       @Override
       public int nextInt()
       {
             final int ret;
             this.lastRet=ret=this.cursor++;
             return (int)parent.root.arr[ret];
       }
       @Override
       public void forEachRemaining(IntConsumer action)
       {
         int cursor;
         int bound;
         final UncheckedSubList parent;
         if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
         {
           {
             uncheckedForEachAscending(parent.root.arr,cursor,bound,action);
           }
           this.lastRet=bound-1;
           this.cursor=bound;
         }
       }
       @Override
       public void forEachRemaining(Consumer<? super Integer> action)
       {
         int cursor;
         int bound;
         final UncheckedSubList parent;
         if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
         {
           {
             uncheckedForEachAscending(parent.root.arr,cursor,bound,action::accept);
           }
           this.lastRet=bound-1;
           this.cursor=bound;
         }
       }
       @Override
       public void remove()
       {
         UncheckedSubList parent;
         final UncheckedList root;
         int cursor;
         ArrCopy.semicheckedSelfCopy((root=(parent=this.parent).root).arr,(cursor=this.lastRet)+1,cursor,(--root.size)-cursor);
         do{
           --parent.size;
         }while((parent=parent.parent)!=null);
         this.cursor=cursor;
         this.lastRet=-1;
       }
       @Override
       public boolean hasPrevious()
       {
         return this.cursor>parent.rootOffset;
       }
       @Override
       public int nextIndex()
       {
         return this.cursor-parent.rootOffset;
       }
       @Override
       public int previousIndex()
       {
         return this.cursor-parent.rootOffset-1;
       }
       @Override
       public int previousInt()
       {
             final int ret;
             this.lastRet=ret=--this.cursor;
             return (int)parent.root.arr[ret];
       }
       @Override
       public void add(int val)
       {
         //TODO implement LstItr.add(int)
       }
       @Override
       public void set(int val)
       {
         //TODO implement LstItr.set(int)
       }
     }
    @Override
    public OmniList.OfInt subList(int fromIndex,int toIndex)
    {
      return new UncheckedSubList(this,this.rootOffset+fromIndex,toIndex-fromIndex);
    }
    @Override
    public OmniListIterator.OfInt listIterator()
    {
      return new LstItr(this);
    }
    @Override
    public OmniListIterator.OfInt listIterator(int index)
    {
      return new LstItr(this,index+this.rootOffset);
    }
    @Override
    public void sort(IntBinaryOperator sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        {
          if(sorter==null)
          {
            final int rootOffset;
            IntSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
          }
          else
          {
            final int rootOffset;
            IntSortUtil.uncheckedStableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter);
          }
        }
      }
    }
    @Override
    public void sort(Comparator<? super Integer> sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        {
          if(sorter==null)
          {
            final int rootOffset;
            IntSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
          }
          else
          {
            final int rootOffset;
            IntSortUtil.uncheckedStableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter::compare);
          }
        }
      }
    }
    @Override
    public void unstableSort(IntBinaryOperator sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        {
          if(sorter==null)
          {
            final int rootOffset;
            IntSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
          }
          else
          {
            final int rootOffset;
            IntSortUtil.uncheckedUnstableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter);
          }
        }
      }
    }
    @Override
    public void stableAscendingSort()
    {
      int size;
      if((size=this.size)>1)
      {
        {
          final int rootOffset;
          IntSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }
      }
    }
    @Override
    public void stableDescendingSort()
    {
      int size;
      if((size=this.size)>1)
      {
        {
          final int rootOffset;
          IntSortUtil.uncheckedDescendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }
      }
    }
    @Override
    public void replaceAll(IntUnaryOperator operator)
    {
      int size;
      if((size=this.size)!=0)
      {
        {
          final int rootOffset;
          ((UncheckedList)root).uncheckedReplaceAll(rootOffset=this.rootOffset,rootOffset+size,operator);
        }
      }
    }
    @Override
    public void replaceAll(UnaryOperator<Integer> operator)
    {
      int size;
      if((size=this.size)!=0)
      {
        {
          final int rootOffset;
          ((UncheckedList)root).uncheckedReplaceAll(rootOffset=this.rootOffset,rootOffset+size,operator::apply);
        }
      }
    }
    @Override
    public void add(int index,int val)
    {
      int rootSize;
      final UncheckedList root;
      if((rootSize=(root=this.root).size)!=0)
      {
        for(var curr=parent;curr!=null;++curr.size,curr=curr.parent){}
        ((IntArrSeq)root).uncheckedInsert(rootSize,index+this.rootOffset,val);
        ++this.size;
      }
      else
      {
        for(var curr=parent;curr!=null;curr.size=1,curr=curr.parent){}
        ((IntArrSeq)root).uncheckedInit(val);
        this.size=1;
      }
    }
    @Override
    public int getInt(int index)
    {
      return (int)root.arr[index+this.rootOffset];
    }
    @Override
    public void put(int index,int val)
    {
      root.arr[index+this.rootOffset]=val;
    }
    @Override
    public int set(int index,int val)
    {
      final int[] arr;
      final int ret=(int)(arr=root.arr)[index+=this.rootOffset];
      arr[index]=val;
      return ret;
    }
    @Override
    public int removeIntAt(int index)
    {
      for(var curr=parent;curr!=null;--curr.size,curr=curr.parent){}
      --this.size;
      final IntArrSeq root;
      return (root=this.root).uncheckedremoveAtIndex(index+this.rootOffset,root.size);
    }
    //TODO implement AND and OR in the template switches to avoid writing this twice
    @Override
    public
    int
    indexOf(boolean val)
    {
      int size;
      if((size=this.size)!=0)
      {
        return ((UncheckedList)root).sublistindexOf(this.rootOffset,size,(int)TypeUtil.castToByte(val));
      }
      return -1;
    }
    @Override
    public
    int
    indexOf(int val)
    {
      int size;
      if((size=this.size)!=0)
      {
        return ((UncheckedList)root).sublistindexOf(this.rootOffset,size,(val));
      }
      return -1;
    }
    @Override
    public
    int
    indexOf(long val)
    {
      int size;
      if((size=this.size)!=0)
      {
        final int v;
        if(val==(v=(int)val))
        {
          return ((UncheckedList)root).sublistindexOf(this.rootOffset,size,v);
        }
      }
      return -1;
    }
    @Override
    public
    int
    indexOf(float val)
    {
      int size;
      if((size=this.size)!=0)
      {
        final int v;
        if((double)val==(double)(v=(int)val))
        {
          return ((UncheckedList)root).sublistindexOf(this.rootOffset,size,v);
        }
      }
      return -1;
    }
    @Override
    public
    int
    indexOf(double val)
    {
      int size;
      if((size=this.size)!=0)
      {
        final int v;
        if((v=(int)val)==val)
        {
          return ((UncheckedList)root).sublistindexOf(this.rootOffset,size,v);
        }
      }
      return -1;
    }
    @Override
    public
    int
    indexOf
    (Object val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Integer)
        {
          return ((UncheckedList)root).sublistindexOf(this.rootOffset,size,(int)(val));
        }
      }
      return -1;
    }
    //TODO implement AND and OR in the template switches to avoid writing this twice
    @Override
    public
    int
    lastIndexOf(boolean val)
    {
      int size;
      if((size=this.size)!=0)
      {
        return ((UncheckedList)root).sublistlastIndexOf(this.rootOffset,size,(int)TypeUtil.castToByte(val));
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf(int val)
    {
      int size;
      if((size=this.size)!=0)
      {
        return ((UncheckedList)root).sublistlastIndexOf(this.rootOffset,size,(val));
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf(long val)
    {
      int size;
      if((size=this.size)!=0)
      {
        final int v;
        if(val==(v=(int)val))
        {
          return ((UncheckedList)root).sublistlastIndexOf(this.rootOffset,size,v);
        }
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf(float val)
    {
      int size;
      if((size=this.size)!=0)
      {
        final int v;
        if((double)val==(double)(v=(int)val))
        {
          return ((UncheckedList)root).sublistlastIndexOf(this.rootOffset,size,v);
        }
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf(double val)
    {
      int size;
      if((size=this.size)!=0)
      {
        final int v;
        if((v=(int)val)==val)
        {
          return ((UncheckedList)root).sublistlastIndexOf(this.rootOffset,size,v);
        }
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf
    (Object val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Integer)
        {
          return ((UncheckedList)root).sublistlastIndexOf(this.rootOffset,size,(int)(val));
        }
      }
      return -1;
    }
  }
  private
  static class CheckedSubList
    implements SubListDefault
  {
    transient final CheckedList root;
    transient final CheckedSubList parent;
    transient final int rootOffset;
    transient int size;
    transient int modCount;
    private CheckedSubList(CheckedList root,int rootOffset,int size
      ,int modCount
    )
    {
      this.modCount=modCount;
      this.root=root;
      this.parent=null;
      this.rootOffset=rootOffset;
      this.size=size;
    }
    private CheckedSubList(CheckedSubList parent,int rootOffset,int size
      ,int modCount
    )
    {
      this.modCount=modCount;
      this.root=parent.root;
      this.parent=parent;
      this.rootOffset=rootOffset;
      this.size=size;
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implement equals(Object) method
      return false;
    }
    @Override
    public Object clone()
    {
      final int[] copy;
      final int size;
      final var root=checkModCountAndGetRoot();
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new int[size],0,size);
      }
      else
      {
        copy=OmniArray.OfInt.DEFAULT_ARR;
      }
      return new CheckedList(size,copy);
    }
    @Override
    public OmniIterator.OfInt iterator()
    {
      CheckedCollection.checkModCount(this.modCount,root.modCount);
      return new Itr(this);
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor)
    {
      final int size;
      final T[] dst;
      int modCount=this.modCount;
      final var root=this.root;
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
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst,0,size);
      }
      return dst;
    }
    @Override
    public int[] toIntArray()
    {
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0)
      {
        final int[] copy;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new int[size],0,size);
        return copy;
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
    @Override
    public Integer[] toArray()
    {
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0)
      {
        final Integer[] copy;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new Integer[size],0,size);
        return copy;
      }
      return OmniArray.OfInt.DEFAULT_BOXED_ARR;
    }
    @Override
    public double[] toDoubleArray()
    {
      final var root=checkModCountAndGetRoot();
      final int size;
      if((size=this.size)!=0)
      {
        final double[] copy;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new double[size],0,size);
        return copy;
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
        final float[] copy;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new float[size],0,size);
        return copy;
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
        final long[] copy;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new long[size],0,size);
        return copy;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    private static class Itr
      extends AbstractItr
    {
      transient final CheckedSubList parent;
      transient int cursor;
      transient int modCount;
      transient int lastRet;
      private Itr(CheckedSubList parent)
      {
        this.parent=parent;
        this.cursor=parent.rootOffset;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      private Itr(CheckedSubList parent,int cursor)
      {
        this.parent=parent;
        this.cursor=cursor+parent.rootOffset;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      @Override
      public int nextInt()
      {
            final int cursor;
            final CheckedList root;
            final CheckedSubList parent;
            CheckedCollection.checkModCount(this.modCount,(root=(parent=this.parent).root).modCount);
            if((cursor=this.cursor)>=parent.rootOffset+parent.size)
            {
               throw new NoSuchElementException();
            }
            this.cursor=cursor+1;
            this.lastRet=cursor;
            return (int)root.arr[cursor];
      }
      @Override
      public void forEachRemaining(IntConsumer action)
      {
        int cursor;
        int bound;
        final CheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
        {
          final CheckedList root=parent.root;
          int modCount=this.modCount;
          try
          {
            uncheckedForEachAscending(root.arr,cursor,bound,action);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
          this.lastRet=bound-1;
          this.cursor=bound;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Integer> action)
      {
        int cursor;
        int bound;
        final CheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
        {
          final CheckedList root=parent.root;
          int modCount=this.modCount;
          try
          {
            uncheckedForEachAscending(root.arr,cursor,bound,action::accept);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
          this.lastRet=bound-1;
          this.cursor=bound;
        }
      }
      @Override
      public boolean hasNext()
      {
        final CheckedSubList parent;
        return this.cursor<(parent=this.parent).rootOffset+parent.size;
      }
      @Override
      public void remove()
      {
        int cursor;
        if((cursor=this.lastRet)==-1)
        {
          throw new IllegalStateException();
        }
        int modCount;
        CheckedSubList parent;
        final CheckedList root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
        root.modCount=++modCount;
        do{
          parent.modCount=modCount;
          --parent.size;
        }while((parent=parent.parent)!=null);
        this.modCount=modCount;
        ArrCopy.semicheckedSelfCopy(root.arr,cursor+1,cursor,(--root.size)-cursor);
        this.cursor=cursor;
        this.lastRet=-1;
      }
    }
    private boolean
    uncheckedremoveVal (int size
    ,int val
    )
    {
      final CheckedList root;
      final var arr=(root=this.root).arr;
      int modCount=this.modCount;
      try
      {
        for(int i=this.rootOffset,bound=i+size;;)
        {
          if(
          val==arr[i]
          )
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
            root.modCount=++modCount;
            this.modCount=modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,--curr.size,curr=curr.parent){}
            this.size=size-1;
            ArrCopy.semicheckedSelfCopy(arr,i+1,i,(--root.size)-i);
            return true;
          }
          if(++i==bound)
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
          }
        }
      }
      catch(ConcurrentModificationException e)
      {
        throw e;
      }
      catch(RuntimeException e)
      {
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
    }
     @Override
     public void forEach(IntConsumer action)
     {
       int size;
       if((size=this.size)!=0)
       {
         final var root=this.root;
         int modCount=this.modCount;
         try
         {
           int rootOffset;
           uncheckedForEachAscending(root.arr,rootOffset=this.rootOffset,rootOffset+size,action);
         }
         finally
         {
           CheckedCollection.checkModCount(modCount,root.modCount);
         }
       }
       else
       {
         CheckedCollection.checkModCount(modCount,root.modCount);
       }
     }
    @Override
    public void forEach(Consumer<? super Integer> action)
    {
      int size;
      if((size=this.size)!=0)
      {
        final var root=this.root;
        int modCount=this.modCount;
        try
        {
          int rootOffset;
          uncheckedForEachAscending(root.arr,rootOffset=this.rootOffset,rootOffset+size,action::accept);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      else
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override
    public boolean removeIf(IntPredicate filter)
    {
      int size;
      if((size=this.size)!=0)
      {
        int newBound;
        final int[] arr;
        final CheckedList root;
        int rootOffset,rootBound,numRemoved;
        int modCount;
        if((numRemoved=(rootBound=(rootOffset=this.rootOffset)+size)-(newBound=doRemoveIf(arr=(root=this.root).arr,rootOffset,rootBound,filter,new CheckedCollection.AbstractModCountChecker(modCount=this.modCount){
          @Override
          protected int getActualModCount(){
            return root.modCount;
          }
        })))!=0)
        {
          root.modCount=++modCount;
          this.modCount=modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr.size-=numRemoved,curr=curr.parent){}
          int rootSize;
          ArrCopy.semicheckedSelfCopy(arr,rootBound,newBound,(rootSize=root.size)-rootBound);
          root.size=rootSize-numRemoved;
          this.size=size-numRemoved;
          return true;
        }
      }
      else
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Integer> filter)
    {
      int size;
      if((size=this.size)!=0)
      {
        int newBound;
        final int[] arr;
        final CheckedList root;
        int rootOffset,rootBound,numRemoved;
        int modCount;
        if((numRemoved=(rootBound=(rootOffset=this.rootOffset)+size)-(newBound=doRemoveIf(arr=(root=this.root).arr,rootOffset,rootBound,filter::test,new CheckedCollection.AbstractModCountChecker(modCount=this.modCount){
          @Override
          protected int getActualModCount(){
            return root.modCount;
          }
        })))!=0)
        {
          root.modCount=++modCount;
          this.modCount=modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr.size-=numRemoved,curr=curr.parent){}
          int rootSize;
          ArrCopy.semicheckedSelfCopy(arr,rootBound,newBound,(rootSize=root.size)-rootBound);
          root.size=rootSize-numRemoved;
          this.size=size-numRemoved;
          return true;
        }
      }
      else
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      return false;
    }
    @Override
    public
    boolean
    contains(boolean val)
    {
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0)
      {
        return ((UncheckedList)root).sublistcontains(this.rootOffset,size,(int)TypeUtil.castToByte(val));
      }
      return false;
    }
    @Override
    public
    boolean
    contains(int val)
    {
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0)
      {
        return ((UncheckedList)root).sublistcontains(this.rootOffset,size,(val));
      }
      return false;
    }
    @Override
    public
    boolean
    contains(long val)
    {
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0)
      {
        final int v;
        if(val==(v=(int)val))
        {
          return ((UncheckedList)root).sublistcontains(this.rootOffset,size,v);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    contains(float val)
    {
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0)
      {
        final int v;
        if((double)val==(double)(v=(int)val))
        {
          return ((UncheckedList)root).sublistcontains(this.rootOffset,size,v);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    contains(double val)
    {
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0)
      {
        final int v;
        if((v=(int)val)==val)
        {
          return ((UncheckedList)root).sublistcontains(this.rootOffset,size,v);
        }
      }
      return false;
    }
    @Override
    public
    boolean
    contains
    (Object val)
    {
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Integer)
        {
          return ((UncheckedList)root).sublistcontains(this.rootOffset,size,(int)(val));
        }
      }
      return false;
    }
    @Override
    public
    boolean
    contains(byte val)
    {
      final var root=checkModCountAndGetRoot();
      {
        int size;
        if((size=this.size)!=0)
        {
          return ((UncheckedList)root).sublistcontains(this.rootOffset,size,(val));
        }
      }
      return false;
    }
    @Override
    public
    boolean
    contains(char val)
    {
      final var root=checkModCountAndGetRoot();
      {
        int size;
        if((size=this.size)!=0)
        {
          return ((UncheckedList)root).sublistcontains(this.rootOffset,size,(val));
        }
      }
      return false;
    }
    @Override
    public int hashCode()
    {
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0)
      {
        final int[] arr;
        int hash;
        int i;
        for(hash=31+((arr=root.arr)[i=this.rootOffset]),size+=i;++i!=size;hash=hash*31+(arr[i])){}
        return hash;
      }
      return 1;
    }
    @Override
    public boolean isEmpty()
    {
      CheckedCollection.checkModCount(this.modCount,root.modCount);
      return this.size==0;
    }
    @Override
    public
    boolean
    removeVal(boolean val)
    {
      int size;
      if((size=this.size)!=0)
      {
        return uncheckedremoveVal(size,(int)TypeUtil.castToByte(val));
      }
      CheckedCollection.checkModCount(this.modCount,root.modCount);
      return false;
    }
    @Override
    public
    boolean
    removeVal(int val)
    {
      int size;
      if((size=this.size)!=0)
      {
        return uncheckedremoveVal(size,(val));
      }
      CheckedCollection.checkModCount(this.modCount,root.modCount);
      return false;
    }
    @Override
    public
    boolean
    removeVal(long val)
    {
      int size;
      if((size=this.size)!=0)
      {
        final int v;
        if(val==(v=(int)val))
        {
          return uncheckedremoveVal(size,v);
        }
      }
      CheckedCollection.checkModCount(this.modCount,root.modCount);
      return false;
    }
    @Override
    public
    boolean
    removeVal(float val)
    {
      int size;
      if((size=this.size)!=0)
      {
        final int v;
        if((double)val==(double)(v=(int)val))
        {
          return uncheckedremoveVal(size,v);
        }
      }
      CheckedCollection.checkModCount(this.modCount,root.modCount);
      return false;
    }
    @Override
    public
    boolean
    removeVal(double val)
    {
      int size;
      if((size=this.size)!=0)
      {
        final int v;
        if((v=(int)val)==val)
        {
          return uncheckedremoveVal(size,v);
        }
      }
      CheckedCollection.checkModCount(this.modCount,root.modCount);
      return false;
    }
    @Override
    public
    boolean
    remove
    (Object val)
    {
      int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Integer)
        {
          return uncheckedremoveVal(size,(int)(val));
        }
      }
      CheckedCollection.checkModCount(this.modCount,root.modCount);
      return false;
    }
    @Override
    public
    boolean
    removeVal(byte val)
    {
      {
        int size;
        if((size=this.size)!=0)
        {
          return uncheckedremoveVal(size,(val));
        }
      }
      CheckedCollection.checkModCount(this.modCount,root.modCount);
      return false;
    }
    @Override
    public
    boolean
    removeVal(char val)
    {
      {
        int size;
        if((size=this.size)!=0)
        {
          return uncheckedremoveVal(size,(val));
        }
      }
      CheckedCollection.checkModCount(this.modCount,root.modCount);
      return false;
    }
    @Override
    public int size()
    {
      CheckedCollection.checkModCount(this.modCount,root.modCount);
      return this.size;
    }
    @Override
    public <T> T[] toArray(T[] dst)
    {
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=OmniArray.uncheckedArrResize(size,dst),0,size);
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
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0)
      {
        if(size>(Integer.MAX_VALUE/3))
        {
          throw new OutOfMemoryError();
        }
        final char[] buffer;
        if(size<=(OmniArray.MAX_ARR_SIZE/((13)<<1)))
        {
          final int[] arr;
          int bufferOffset,i;
          for(bufferOffset=ToStringUtil.getStringInt((arr=root.arr)[i=this.rootOffset],buffer=new char[size*(13)],1),size+=i;++i!=size;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringInt(arr[i],buffer,bufferOffset)){}
          buffer[bufferOffset]=']';
          buffer[0]='[';
          return new String(buffer,0,bufferOffset+1);
        }
        else
        {
          final ToStringUtil.OmniStringBuilder builder;
          final int[] arr;
          int i;
          for((builder=new ToStringUtil.OmniStringBuilder(1,new char[size<=(int)(OmniArray.MAX_ARR_SIZE>>>3)?(size<<3):OmniArray.MAX_ARR_SIZE])).uncheckedAppendInt((arr=root.arr)[i=this.rootOffset]),size+=i;++i!=size;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendInt(arr[i])){}
          builder.uncheckedAppendChar(']');
          (buffer=builder.buffer)[0]='[';
          return new String(buffer,0,builder.size);
        }
      }
      return "[]";
    }
    @Override
    public boolean add(int val)
    {
      int rootSize;
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      this.modCount=modCount;
      if((rootSize=root.size)!=0)
      {
        for(var curr=parent;curr!=null;curr.modCount=modCount,++curr.size,curr=curr.parent){}
        ((IntArrSeq)root).uncheckedInsert(rootSize,this.rootOffset,val);
        ++this.size;
      }
      else
      {
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr.size=1,curr=curr.parent){}
        ((IntArrSeq)root).uncheckedInit(val);
        this.size=1;
      }
      return true;
    }
    private IntArrSeq checkModCountAndGetRoot()
    {
      final CheckedList root;
      CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
      return root;
    }
    @Override
    public void clear()
    {
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      int size;
      if((size=this.size)!=0)
      {
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;parent.modCount=modCount,parent.size-=size){}
        int rootOffset;
        ArrCopy.semicheckedSelfCopy(root.arr,(rootOffset=this.rootOffset)+size,rootOffset,(root.size-=size)-rootOffset);
        this.size=0;
      }
    }
     private static class LstItr
       extends Itr implements OmniListIterator.OfInt
     {
       private LstItr(CheckedSubList parent)
       {
         super(parent);
       }
       private LstItr(CheckedSubList parent,int cursor)
       {
         super(parent);
       }
       @Override
       public boolean hasPrevious()
       {
         return this.cursor>parent.rootOffset;
       }
       @Override
       public int nextIndex()
       {
         return this.cursor-parent.rootOffset;
       }
       @Override
       public int previousIndex()
       {
         return this.cursor-parent.rootOffset-1;
       }
       @Override
       public int previousInt()
       {
             int cursor;
             final CheckedList root;
             final CheckedSubList parent;
             CheckedCollection.checkModCount(this.modCount,(root=(parent=this.parent).root).modCount);
             if((cursor=this.cursor)<=parent.rootOffset)
             {
                throw new NoSuchElementException();
             }
             this.cursor=--cursor;
             this.lastRet=cursor;
             return (int)root.arr[cursor];
       }
       @Override
       public void add(int val)
       {
         //TODO implement LstItr.add(int)
       }
       @Override
       public void set(int val)
       {
         //TODO implement LstItr.set(int)
       }
     }
    @Override
    public OmniList.OfInt subList(int fromIndex,int toIndex)
    {
      final int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,root.modCount);
      int span;
      if((span=toIndex-fromIndex)<0|| fromIndex<0 ||  toIndex>this.size)
      {
        throw new InvalidReadIndexException("fromIndex="+fromIndex+"; toIndex="+toIndex+"; size="+this.size);
      }
      return new CheckedSubList(this,this.rootOffset+fromIndex,span,modCount);
    }
    @Override
    public OmniListIterator.OfInt listIterator()
    {
      CheckedCollection.checkModCount(this.modCount,root.modCount);
      return new LstItr(this);
    }
    @Override
    public OmniListIterator.OfInt listIterator(int index)
    {
      CheckedCollection.checkModCount(this.modCount,root.modCount);
      if(index<0 || size<index)
      {
        throw new InvalidWriteIndexException(index,size);
      }
      return new LstItr(this,index+this.rootOffset);
    }
    @Override
    public void sort(IntBinaryOperator sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        int modCount=this.modCount;
        final var root=this.root;
        try
        {
          if(sorter==null)
          {
            final int rootOffset;
            IntSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
          }
          else
          {
            final int rootOffset;
            IntSortUtil.uncheckedStableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter);
          }
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        }
      }
      else
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override
    public void sort(Comparator<? super Integer> sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        int modCount=this.modCount;
        final var root=this.root;
        try
        {
          if(sorter==null)
          {
            final int rootOffset;
            IntSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
          }
          else
          {
            final int rootOffset;
            IntSortUtil.uncheckedStableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter::compare);
          }
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        }
      }
      else
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override
    public void unstableSort(IntBinaryOperator sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        int modCount=this.modCount;
        final var root=this.root;
        try
        {
          if(sorter==null)
          {
            final int rootOffset;
            IntSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
          }
          else
          {
            final int rootOffset;
            IntSortUtil.uncheckedUnstableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter);
          }
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        }
      }
      else
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override
    public void stableAscendingSort()
    {
      int size;
      if((size=this.size)>1)
      {
        int modCount=this.modCount;
        final var root=this.root;
        try
        {
          final int rootOffset;
          IntSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        }
      }
      else
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override
    public void stableDescendingSort()
    {
      int size;
      if((size=this.size)>1)
      {
        int modCount=this.modCount;
        final var root=this.root;
        try
        {
          final int rootOffset;
          IntSortUtil.uncheckedDescendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        }
      }
      else
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override
    public void replaceAll(IntUnaryOperator operator)
    {
      int size;
      if((size=this.size)!=0)
      {
        int modCount=this.modCount;
        final var root=this.root;
        try
        {
          final int rootOffset;
          ((UncheckedList)root).uncheckedReplaceAll(rootOffset=this.rootOffset,rootOffset+size,operator);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        }
      }
      else
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override
    public void replaceAll(UnaryOperator<Integer> operator)
    {
      int size;
      if((size=this.size)!=0)
      {
        int modCount=this.modCount;
        final var root=this.root;
        try
        {
          final int rootOffset;
          ((UncheckedList)root).uncheckedReplaceAll(rootOffset=this.rootOffset,rootOffset+size,operator::apply);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        }
      }
      else
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override
    public void add(int index,int val)
    {
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      int size;
      if((size=this.size)<index || index<0)
      {
        throw new InvalidWriteIndexException(index,size);
      }
      root.modCount=++modCount;
      this.modCount=modCount;
      int rootSize;
      if((rootSize=root.size)!=0)
      {
        for(var curr=parent;curr!=null;curr.modCount=modCount,++curr.size,curr=curr.parent){}
        ((IntArrSeq)root).uncheckedInsert(rootSize,index+this.rootOffset,val);
        ++this.size;
      }
      else
      {
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr.size=1,curr=curr.parent){}
        ((IntArrSeq)root).uncheckedInit(val);
        this.size=1;
      }
    }
    @Override
    public int getInt(int index)
    {
      final var root=checkModCountAndGetRoot();
      if(index<0 || index>=size)
      {
        throw new InvalidReadIndexException(index,size);
      }
      return (int)root.arr[index+this.rootOffset];
    }
    @Override
    public void put(int index,int val)
    {
      final var root=checkModCountAndGetRoot();
      if(index<0 || index>=size)
      {
        throw new InvalidWriteIndexException(index,size);
      }
      root.arr[index+this.rootOffset]=val;
    }
    @Override
    public int set(int index,int val)
    {
      final var root=checkModCountAndGetRoot();
      if(index<0 || index>=size)
      {
        throw new InvalidReadIndexException(index,size);
      }
      final int[] arr;
      final int ret=(int)(arr=root.arr)[index+=this.rootOffset];
      arr[index]=val;
      return ret;
    }
    @Override
    public int removeIntAt(int index)
    {
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      int size;
      if((size=this.size)<=index || index<0)
      {
        throw new InvalidWriteIndexException(index,size);
      }
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,--curr.size,curr=curr.parent){}
      this.size=size-1;
      return ((IntArrSeq)root).uncheckedremoveAtIndex(index+this.rootOffset,root.size);
    }
    //TODO implement AND and OR in the template switches to avoid writing this twice
    @Override
    public
    int
    indexOf(boolean val)
    {
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0)
      {
        return ((UncheckedList)root).sublistindexOf(this.rootOffset,size,(int)TypeUtil.castToByte(val));
      }
      return -1;
    }
    @Override
    public
    int
    indexOf(int val)
    {
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0)
      {
        return ((UncheckedList)root).sublistindexOf(this.rootOffset,size,(val));
      }
      return -1;
    }
    @Override
    public
    int
    indexOf(long val)
    {
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0)
      {
        final int v;
        if(val==(v=(int)val))
        {
          return ((UncheckedList)root).sublistindexOf(this.rootOffset,size,v);
        }
      }
      return -1;
    }
    @Override
    public
    int
    indexOf(float val)
    {
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0)
      {
        final int v;
        if((double)val==(double)(v=(int)val))
        {
          return ((UncheckedList)root).sublistindexOf(this.rootOffset,size,v);
        }
      }
      return -1;
    }
    @Override
    public
    int
    indexOf(double val)
    {
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0)
      {
        final int v;
        if((v=(int)val)==val)
        {
          return ((UncheckedList)root).sublistindexOf(this.rootOffset,size,v);
        }
      }
      return -1;
    }
    @Override
    public
    int
    indexOf
    (Object val)
    {
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Integer)
        {
          return ((UncheckedList)root).sublistindexOf(this.rootOffset,size,(int)(val));
        }
      }
      return -1;
    }
    //TODO implement AND and OR in the template switches to avoid writing this twice
    @Override
    public
    int
    lastIndexOf(boolean val)
    {
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0)
      {
        return ((UncheckedList)root).sublistlastIndexOf(this.rootOffset,size,(int)TypeUtil.castToByte(val));
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf(int val)
    {
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0)
      {
        return ((UncheckedList)root).sublistlastIndexOf(this.rootOffset,size,(val));
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf(long val)
    {
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0)
      {
        final int v;
        if(val==(v=(int)val))
        {
          return ((UncheckedList)root).sublistlastIndexOf(this.rootOffset,size,v);
        }
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf(float val)
    {
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0)
      {
        final int v;
        if((double)val==(double)(v=(int)val))
        {
          return ((UncheckedList)root).sublistlastIndexOf(this.rootOffset,size,v);
        }
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf(double val)
    {
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0)
      {
        final int v;
        if((v=(int)val)==val)
        {
          return ((UncheckedList)root).sublistlastIndexOf(this.rootOffset,size,v);
        }
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf
    (Object val)
    {
      final var root=checkModCountAndGetRoot();
      int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Integer)
        {
          return ((UncheckedList)root).sublistlastIndexOf(this.rootOffset,size,(int)(val));
        }
      }
      return -1;
    }
  }
  private static abstract class AbstractItr implements OmniIterator.OfInt
  {
    @Override
    public Integer next()
    {
      return (Integer)(nextInt());
    }
    @Override
    public double nextDouble()
    {
      return (double)(nextInt());
    }
    @Override
    public float nextFloat()
    {
      return (float)(nextInt());
    }
    @Override
    public long nextLong()
    {
      return (long)(nextInt());
    }
  }
  private static abstract interface ListDefault extends OmniList.OfInt
  {
    @Override
    public default void add(int index,Integer val)
    {
      add(index,(int)val);
    }
    @Override
    public default Integer get(int index)
    {
      return getInt(index);
    }
    @Override
    public default Integer set(int index,Integer val)
    {
      return set(index,(int)val);
    }
    @Override
    public default  Integer remove(int index)
    {
      return removeIntAt(index);
    }
  }
  private static abstract interface SubListDefault extends ListDefault
  {
    @Override
    public default boolean add(Integer val)
    {
      return this.add((int)val);
    }
    @Override
    public default boolean add(boolean val)
    {
      return this.add((int)TypeUtil.castToByte(val));
    }
  }
}
