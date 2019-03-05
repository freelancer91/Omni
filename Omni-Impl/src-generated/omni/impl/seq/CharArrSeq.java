package omni.impl.seq;
import omni.api.OmniCollection;
import omni.util.OmniArray;
import omni.api.OmniList;
import omni.api.OmniStack;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.Comparator;
import omni.util.ArrCopy;
import omni.util.CharSortUtil;
import omni.impl.CheckedCollection;
import java.util.NoSuchElementException;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;
import omni.util.TypeUtil;
import omni.function.CharUnaryOperator;
import omni.function.CharComparator;
import omni.function.CharPredicate;
import omni.function.CharConsumer;
public abstract class CharArrSeq implements OmniCollection.OfChar
{
  transient int size;
  transient char[] arr;
  private CharArrSeq()
  {
    super();
  }
  private CharArrSeq(int initialCapacity)
  {
    super();
    switch(initialCapacity)
    { 
    default:
      this.arr=new char[initialCapacity];
      return;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfChar.DEFAULT_ARR;
    case 0:
    }
  }
  private CharArrSeq(int size,char[] arr)
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
  public boolean add(boolean val)
  {
    push(TypeUtil.castToChar(val));
    return true;
  }
  @Override
  public boolean add(char val)
  {
    push(val);
    return true;
  }
  @Override
  public boolean add(Character val)
  {
    push((char)val);
    return true;
  }
  @Override
  public void forEach(CharConsumer action)
  {
    int size;
    if((size=this.size)!=0)
    {
      uncheckedForEach(size,action);
    }
  }
  @Override
  public void forEach(Consumer<? super Character> action)
  {
    int size;
    if((size=this.size)!=0)
    {
      uncheckedForEach(size,action::accept);
    }
  }
  @Override
  public boolean removeIf(CharPredicate filter)
  {
    int size;
    if((size=this.size)!=0)
    {
      return uncheckedRemoveIf(size,filter);
    }
    return false;
  }
  @Override
  public boolean removeIf(Predicate<? super Character> filter)
  {
    int size;
    if((size=this.size)!=0)
    {
      return uncheckedRemoveIf(size,filter::test);
    }
    return false;
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
      uncheckedToString(size,buffer=new char[size*=3]);
      buffer[size-1]=']';
      buffer[0]='[';
      return new String(buffer,0,size);
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
      return uncheckedremoveVal(size,TypeUtil.castToChar(val));
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
      if(val==(char)val)
      {
        return uncheckedremoveVal(size,val);
      }
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
      final char v;
      if(val==(v=(char)val))
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
      final char v;
      if(val==(v=(char)val))
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
      final char v;
      if((v=(char)val)==val)
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
      if(val instanceof Character)
      {
        return uncheckedremoveVal(size,(char)(val));
      }
    }
    return false;
  }
  @Override
  public
  boolean
  removeVal(byte val)
  {
    if(val>=0)
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
  removeVal(short val)
  {
    if(val>=0)
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
      return uncheckedcontains(size,TypeUtil.castToChar(val));
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
      if(val==(char)val)
      {
        return uncheckedcontains(size,val);
      }
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
      final char v;
      if(val==(v=(char)val))
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
      final char v;
      if(val==(v=(char)val))
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
      final char v;
      if((v=(char)val)==val)
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
      if(val instanceof Character)
      {
        return uncheckedcontains(size,(char)(val));
      }
    }
    return false;
  }
  @Override
  public
  boolean
  contains(byte val)
  {
    if(val>=0)
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
  @Override
  public
  boolean
  contains(short val)
  {
    if(val>=0)
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
  abstract void uncheckedCopyInto(char[] dst,int size);
  @Override
  public char[] toCharArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final char[] copy;
      uncheckedCopyInto(copy=new char[size],size);
      return copy;
    }
    return OmniArray.OfChar.DEFAULT_ARR;
  }
  abstract void uncheckedCopyInto(Character[] dst,int size);
  @Override
  public Character[] toArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final Character[] copy;
      uncheckedCopyInto(copy=new Character[size],size);
      return copy;
    }
    return OmniArray.OfChar.DEFAULT_BOXED_ARR;
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
  abstract int uncheckedHashCode(int size);
  abstract void uncheckedToString(int size,char[] buffer);
  abstract void uncheckedForEach(int size,CharConsumer action);
  abstract boolean uncheckedRemoveIf(int size,CharPredicate filter);
  private void uncheckedInit(char val)
  {
    char[] arr;
    if((arr=this.arr)==OmniArray.OfChar.DEFAULT_ARR)
    {
      this.arr=arr=new char[OmniArray.DEFAULT_ARR_SEQ_CAP];
    }
    else if(arr==null)
    {
      this.arr=arr=new char[1];
    }
    arr[0]=val;
    this.size=1;
  }
  private void uncheckedAppend(int size,char val)
  {
    char[] arr;
    if((arr=this.arr).length==size)
    {
      ArrCopy.uncheckedCopy(arr,0,arr=new char[OmniArray.growBy50Pct(size)],0,size);
      this.arr=arr;
    }
    arr[size]=val;
    this.size=size+1;
  }
  public void push(char val)
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
  private void uncheckedInsert(int size,int index,char val)
  {
    int tailDist;
    if((tailDist=size-index)==0)
    {
      uncheckedAppend(size,val);
    }
    else
    {
      char[] arr;
      if((arr=this.arr).length==size)
      {
        char[] newArr;
        ArrCopy.semicheckedCopy(arr,0,newArr=new char[OmniArray.growBy50Pct(size)],0,index);
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
  public void push(Character val)
  {
    push((char)val);
  }
  private char uncheckedremoveAtIndex(int index,int size)
  {
    final char[] arr;
    final char ret=(char)(arr=this.arr)[index];
    ArrCopy.semicheckedSelfCopy(arr,index+1,index,(--size)-index);
    this.size=size;
    return ret;
  }
  public static class UncheckedArrStack extends CharArrSeq implements OmniStack.OfChar
  {
    public UncheckedArrStack()
    {
      super();
    }
    public UncheckedArrStack(int initialCapacity)
    {
      super(initialCapacity);
    }
    public UncheckedArrStack(int size,char[] arr)
    {
      super(size,arr);
    }
    @Override
    public char popChar()
    {
      return (char)arr[--this.size];
    }
    @Override
    public Character pop()
    {
      return popChar();
    }
    @Override
    public Object clone()
    {
      char[] copy;
      int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new char[size],0,size);
      }
      else
      {
        copy=OmniArray.OfChar.DEFAULT_ARR;
      }
      return new UncheckedArrStack(size,copy);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO
      return false;
    }
    @Override
    public
    int
    search(boolean val)
    {
      int size;
      if((size=this.size)!=0)
      {
        return uncheckedsearch(size,TypeUtil.castToChar(val));
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
        if(val==(char)val)
        {
          return uncheckedsearch(size,val);
        }
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
        final char v;
        if(val==(v=(char)val))
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
        final char v;
        if(val==(v=(char)val))
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
        final char v;
        if((v=(char)val)==val)
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
        if(val instanceof Character)
        {
          return uncheckedsearch(size,(char)(val));
        }
      }
      return -1;
    }
    @Override
    public
    int
    search(char val)
    {
      {
        int size;
        if((size=this.size)!=0)
        {
          return uncheckedsearch(size,(val));
        }
      }
      return -1;
    }
    @Override
    public
    int
    search(short val)
    {
      if(val>=0)
      {
        int size;
        if((size=this.size)!=0)
        {
          return uncheckedsearch(size,(val));
        }
      }
      return -1;
    }
    @Override
    public char pollChar()
    {
      int size;
      if((size=this.size)!=0)
      {
        final char ret=(char)((char)arr[--size]);
        this.size=size;
        return ret;
      }
      return Character.MIN_VALUE;
    }
    @Override
    public Character poll()
    {
      int size;
      if((size=this.size)!=0)
      {
        final Character ret=(Character)((char)arr[--size]);
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
        final double ret=(double)((char)arr[--size]);
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
        final float ret=(float)((char)arr[--size]);
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
        final long ret=(long)((char)arr[--size]);
        this.size=size;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override
    public int pollInt()
    {
      int size;
      if((size=this.size)!=0)
      {
        final int ret=(int)((char)arr[--size]);
        this.size=size;
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    public char peekChar()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (char)((char)arr[size-1]);
      }
      return Character.MIN_VALUE;
    }
    public Character peek()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (Character)((char)arr[size-1]);
      }
      return null;
    }
    public double peekDouble()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (double)((char)arr[size-1]);
      }
      return Double.NaN;
    }
    public float peekFloat()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (float)((char)arr[size-1]);
      }
      return Float.NaN;
    }
    public long peekLong()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (long)((char)arr[size-1]);
      }
      return Long.MIN_VALUE;
    }
    public int peekInt()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (int)((char)arr[size-1]);
      }
      return Integer.MIN_VALUE;
    }
    @Override
    void uncheckedCopyInto(char[] dst,int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(Character[] dst,int size)
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
    @Override
    void uncheckedCopyInto(int[] dst,int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(Object[] dst,int size)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,size);
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
    int uncheckedHashCode(int size)
    {
      final char[] arr;
      int hash;
      for(hash=31+((arr=this.arr)[--size]);size!=0;hash=hash*31+(arr[--size])){}
      return hash;
    }
    @Override
    void uncheckedToString(int size,char[] buffer)
    {
      final char[] arr;
      int bufferOffset;
      for(buffer[bufferOffset=1]=(arr=this.arr)[--size];size!=0;buffer[++bufferOffset]=',',buffer[++bufferOffset]=' ',buffer[++bufferOffset]=arr[--size]){}
    }
    @Override
    void uncheckedForEach(int size,CharConsumer action)
    {
      final var arr=this.arr;
      for(;;)
      {
        action.accept((char)arr[--size]);
        if(size==0)
        {
          return;
        }
      }
    }
    @Override
    boolean uncheckedRemoveIf(int size,CharPredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public OmniIterator.OfChar iterator()
    {
      //TODO
      return null;
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor)
    {
      int size;
      T[] dst=arrConstructor.apply(size=this.size);
      if(size!=0)
      {
        ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,size);
      }
      return dst;
    }
  }
  public static class CheckedArrStack extends UncheckedArrStack
  {
    transient int modCount;
    public CheckedArrStack()
    {
      super();
    }
    public CheckedArrStack(int initialCapacity)
    {
      super(initialCapacity);
    }
    public CheckedArrStack(int size,char[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO
      return false;
    }
    @Override
    public char popChar()
    {
      int size;
      if((size=this.size)!=0)
      {
        ++this.modCount;
        final char ret=(char)arr[--size];
        this.size=size;
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override
    public Object clone()
    {
      char[] copy;
      int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new char[size],0,size);
      }
      else
      {
        copy=OmniArray.OfChar.DEFAULT_ARR;
      }
      return new CheckedArrStack(size,copy);
    }
    @Override
    public char pollChar()
    {
      int size;
      if((size=this.size)!=0)
      {
        final char ret=(char)((char)arr[--size]);
        ++this.modCount;
        this.size=size;
        return ret;
      }
      return Character.MIN_VALUE;
    }
    @Override
    public Character poll()
    {
      int size;
      if((size=this.size)!=0)
      {
        final Character ret=(Character)((char)arr[--size]);
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
        final double ret=(double)((char)arr[--size]);
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
        final float ret=(float)((char)arr[--size]);
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
        final long ret=(long)((char)arr[--size]);
        ++this.modCount;
        this.size=size;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override
    public int pollInt()
    {
      int size;
      if((size=this.size)!=0)
      {
        final int ret=(int)((char)arr[--size]);
        ++this.modCount;
        this.size=size;
        return ret;
      }
      return Integer.MIN_VALUE;
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
    void uncheckedForEach(int size,CharConsumer action)
    {
      int modCount=this.modCount;
      try
      {
        super.uncheckedForEach(size,action);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override
    boolean uncheckedRemoveIf(int size,CharPredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public void push(char val)
    {
      ++this.modCount;
      super.push(val);
    }
    @Override
    public OmniIterator.OfChar iterator()
    {
      //TODO
      return null;
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
  }
  private static class UncheckedArrList extends CharArrSeq implements OmniList.OfChar
  {
    private static class SubListImpl implements OmniList.OfChar
    {
      transient final UncheckedArrList root;
      transient final SubListImpl parent;
      transient final int rootOffset;
      transient int size;
      SubListImpl(UncheckedArrList root,int rootOffset,int size
      )
      {
        this.root=root;
        this.parent=null;
        this.rootOffset=rootOffset;
        this.size=size;
      }
      SubListImpl(SubListImpl parent,int rootOffset,int size
      )
      {
        this.root=parent.root;
        this.parent=parent;
        this.rootOffset=rootOffset;
        this.size=size;
      }
      @Override
      public Object clone()
      {
        final char[] copy;
        int size;
        if((size=this.size)!=0)
        {
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new char[size],0,size);
        }
        else
        {
          copy=OmniArray.OfChar.DEFAULT_ARR;
        }
        return new UncheckedArrList(size,copy);
      }
      @Override
      public boolean add(char val)
      {
        int rootSize;
        final UncheckedArrList root;
        if((rootSize=(root=this.root).size)!=0)
        {
          for(var curr=parent;curr!=null;++parent.size){}
          ((CharArrSeq)root).uncheckedInsert(rootSize,this.rootOffset,val);
          ++this.size;
        }
        else
        {
          for(var curr=parent;curr!=null;parent.size=1){}
          ((CharArrSeq)root).uncheckedInit(val);
          this.size=1;
        }
        return true;
      }
      @Override
      public void add(int index,char val)
      {
        int rootSize;
        final UncheckedArrList root;
        if((rootSize=(root=this.root).size)!=0)
        {
          for(var curr=parent;curr!=null;++parent.size){}
          ((CharArrSeq)root).uncheckedInsert(rootSize,index+this.rootOffset,val);
          ++this.size;
        }
        else
        {
          for(var curr=parent;curr!=null;parent.size=1){}
          ((CharArrSeq)root).uncheckedInit(val);
          this.size=1;
        }
      }
      @Override
      public boolean add(Character val)
      {
        return add((char)val);
      }
      @Override
      public void add(int index,Character val)
      {
        add(index,(char)val);
      }
      @Override
      public void forEach(CharConsumer action)
      {
        {
          int size;
          if((size=this.size)!=0)
          {
            ((UncheckedArrList)root).sublistForEach(this.rootOffset,size,action);
          }
        }
      }
      @Override
      public void forEach(Consumer<? super Character> action)
      {
        {
          int size;
          if((size=this.size)!=0)
          {
            ((UncheckedArrList)root).sublistForEach(this.rootOffset,size,action::accept);
          }
        }
      }
      @Override
      public boolean removeIf(CharPredicate filter)
      {
        //TODO
        return false;
      }
      @Override
      public boolean removeIf(Predicate<? super Character> action)
      {
        //TODO
        return false;
      }
      @Override
      public char[] toCharArray()
      {
        final int size;
        if((size=this.size)!=0)
        {
          final char[] copy;
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new char[size],0,size);
          return copy;
        }
        return OmniArray.OfChar.DEFAULT_ARR;
      }
      @Override
      public Character[] toArray()
      {
        final int size;
        if((size=this.size)!=0)
        {
          final Character[] copy;
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new Character[size],0,size);
          return copy;
        }
        return OmniArray.OfChar.DEFAULT_BOXED_ARR;
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
      public <T> T[] toArray(IntFunction<T[]> arrConstructor)
      {
        int size;
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
      public void put(int index,char val)
      {
        root.arr[index+this.rootOffset]=val;
      }
      @Override
      public char getChar(int index)
      {
        return (char)root.arr[index+this.rootOffset];
      }
      @Override
      public Character get(int index)
      {
        return getChar(index);
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
      public void clear()
      {
        int size;
        if((size=this.size)!=0)
        {
          for(var curr=parent;curr!=null;parent.size-=size){}
          final UncheckedArrList root;
          int rootOffset;
          ArrCopy.semicheckedSelfCopy((root=this.root).arr,(rootOffset=this.rootOffset)+size,rootOffset,(root.size-=size)-rootOffset);
          this.size=0;
        }
      }
      @Override
      public OmniIterator.OfChar iterator()
      {
        //TODO
        return null;
      }
      @Override
      public OmniListIterator.OfChar listIterator()
      {
        //TODO
        return null;
      }
      @Override
      public OmniListIterator.OfChar listIterator(int index)
      {
        //TODO
        return null;
      }
      @Override
      public
      boolean
      contains(boolean val)
      {
        int size;
        if((size=this.size)!=0)
        {
          return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,TypeUtil.castToChar(val));
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
          if(val==(char)val)
          {
            return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,val);
          }
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
          final char v;
          if(val==(v=(char)val))
          {
            return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,v);
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
          final char v;
          if(val==(v=(char)val))
          {
            return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,v);
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
          final char v;
          if((v=(char)val)==val)
          {
            return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,v);
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
          if(val instanceof Character)
          {
            return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,(char)(val));
          }
        }
        return false;
      }
      @Override
      public
      boolean
      contains(byte val)
      {
        if(val>=0)
        {
          int size;
          if((size=this.size)!=0)
          {
            return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,(val));
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
            return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,(val));
          }
        }
        return false;
      }
      @Override
      public
      boolean
      contains(short val)
      {
        if(val>=0)
        {
          int size;
          if((size=this.size)!=0)
          {
            return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,(val));
          }
        }
        return false;
      }
      @Override
      public
      boolean
      removeVal(boolean val)
      {
        int size;
        if((size=this.size)!=0)
        {
          return uncheckedremoveVal(size,TypeUtil.castToChar(val));
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
          if(val==(char)val)
          {
            return uncheckedremoveVal(size,val);
          }
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
          final char v;
          if(val==(v=(char)val))
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
          final char v;
          if(val==(v=(char)val))
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
          final char v;
          if((v=(char)val)==val)
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
          if(val instanceof Character)
          {
            return uncheckedremoveVal(size,(char)(val));
          }
        }
        return false;
      }
      @Override
      public
      boolean
      removeVal(byte val)
      {
        if(val>=0)
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
      removeVal(short val)
      {
        if(val>=0)
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
      int
      indexOf(boolean val)
      {
        int size;
        if((size=this.size)!=0)
        {
          return ((UncheckedArrList)root).sublistindexOf(this.rootOffset,size,TypeUtil.castToChar(val));
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
          if(val==(char)val)
          {
            return ((UncheckedArrList)root).sublistindexOf(this.rootOffset,size,val);
          }
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
          final char v;
          if(val==(v=(char)val))
          {
            return ((UncheckedArrList)root).sublistindexOf(this.rootOffset,size,v);
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
          final char v;
          if(val==(v=(char)val))
          {
            return ((UncheckedArrList)root).sublistindexOf(this.rootOffset,size,v);
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
          final char v;
          if((v=(char)val)==val)
          {
            return ((UncheckedArrList)root).sublistindexOf(this.rootOffset,size,v);
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
          if(val instanceof Character)
          {
            return ((UncheckedArrList)root).sublistindexOf(this.rootOffset,size,(char)(val));
          }
        }
        return -1;
      }
      @Override
      public
      int
      indexOf(char val)
      {
        {
          int size;
          if((size=this.size)!=0)
          {
            return ((UncheckedArrList)root).sublistindexOf(this.rootOffset,size,(val));
          }
        }
        return -1;
      }
      @Override
      public
      int
      indexOf(short val)
      {
        if(val>=0)
        {
          int size;
          if((size=this.size)!=0)
          {
            return ((UncheckedArrList)root).sublistindexOf(this.rootOffset,size,(val));
          }
        }
        return -1;
      }
      @Override
      public
      int
      lastIndexOf(boolean val)
      {
        int size;
        if((size=this.size)!=0)
        {
          return ((UncheckedArrList)root).sublistlastIndexOf(this.rootOffset,size,TypeUtil.castToChar(val));
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
          if(val==(char)val)
          {
            return ((UncheckedArrList)root).sublistlastIndexOf(this.rootOffset,size,val);
          }
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
          final char v;
          if(val==(v=(char)val))
          {
            return ((UncheckedArrList)root).sublistlastIndexOf(this.rootOffset,size,v);
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
          final char v;
          if(val==(v=(char)val))
          {
            return ((UncheckedArrList)root).sublistlastIndexOf(this.rootOffset,size,v);
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
          final char v;
          if((v=(char)val)==val)
          {
            return ((UncheckedArrList)root).sublistlastIndexOf(this.rootOffset,size,v);
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
          if(val instanceof Character)
          {
            return ((UncheckedArrList)root).sublistlastIndexOf(this.rootOffset,size,(char)(val));
          }
        }
        return -1;
      }
      @Override
      public
      int
      lastIndexOf(char val)
      {
        {
          int size;
          if((size=this.size)!=0)
          {
            return ((UncheckedArrList)root).sublistlastIndexOf(this.rootOffset,size,(val));
          }
        }
        return -1;
      }
      @Override
      public
      int
      lastIndexOf(short val)
      {
        if(val>=0)
        {
          int size;
          if((size=this.size)!=0)
          {
            return ((UncheckedArrList)root).sublistlastIndexOf(this.rootOffset,size,(val));
          }
        }
        return -1;
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
          final char[] arr;
          int bufferOffset,i;
          for((buffer=new char[size*3])[bufferOffset=1]=(arr=root.arr)[i=this.rootOffset],size+=i;++i!=size;buffer[++bufferOffset]=',',buffer[++bufferOffset]=' ',buffer[++bufferOffset]=arr[i]){}
          buffer[++bufferOffset]=']';
          buffer[0]='[';
          return new String(buffer,0,bufferOffset+1);
        }
        return "[]";
      }
      @Override
      public boolean equals(Object val)
      {
        //TODO
        return false;
      }
      @Override
      public int hashCode()
      {
        int size;
        if((size=this.size)!=0)
        {
          final char[] arr;
          int hash;
          int i;
          for(hash=31+((arr=root.arr)[i=this.rootOffset]),size+=i;++i!=size;hash=hash*31+(arr[i])){}
          return hash;
        }
        return 1;
      }
      @Override
      public int size()
      {
        return this.size;
      }
      @Override
      public boolean isEmpty()
      {
        return this.size==0;
      }
        private boolean
        uncheckedremoveVal (int size
        ,int val
        )
        {
          final UncheckedArrList root;
          final var arr=(root=this.root).arr;
          {
            for(int i=this.rootOffset,bound=i+size;;)
            {
              if(
              val==arr[i]
              )
              {
                for(var curr=parent;curr!=null;--curr.size){}
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
    }
    public UncheckedArrList()
    {
      super();
    }
    public UncheckedArrList(int initialCapacity)
    {
      super(initialCapacity);
    }
    public UncheckedArrList(int size,char[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO
      return false;
    }
    @Override
    public char getChar(int index)
    {
      return (char)arr[index];
    }
    @Override
    public Character get(int index)
    {
      return getChar(index);
    }
    @Override
    public void put(int index,char val)
    {
      arr[index]=val;
    }
    @Override
    public Object clone()
    {
      char[] copy;
      int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new char[size],0,size);
      }
      else
      {
        copy=OmniArray.OfChar.DEFAULT_ARR;
      }
      return new UncheckedArrList(size,copy);
    }
    @Override
    public
    int
    indexOf(boolean val)
    {
      int size;
      if((size=this.size)!=0)
      {
        return uncheckedindexOf(size,TypeUtil.castToChar(val));
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
        if(val==(char)val)
        {
          return uncheckedindexOf(size,val);
        }
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
        final char v;
        if(val==(v=(char)val))
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
        final char v;
        if(val==(v=(char)val))
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
        final char v;
        if((v=(char)val)==val)
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
        if(val instanceof Character)
        {
          return uncheckedindexOf(size,(char)(val));
        }
      }
      return -1;
    }
    @Override
    public
    int
    indexOf(char val)
    {
      {
        int size;
        if((size=this.size)!=0)
        {
          return uncheckedindexOf(size,(val));
        }
      }
      return -1;
    }
    @Override
    public
    int
    indexOf(short val)
    {
      if(val>=0)
      {
        int size;
        if((size=this.size)!=0)
        {
          return uncheckedindexOf(size,(val));
        }
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf(boolean val)
    {
      int size;
      if((size=this.size)!=0)
      {
        return uncheckedlastIndexOf(size,TypeUtil.castToChar(val));
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
        if(val==(char)val)
        {
          return uncheckedlastIndexOf(size,val);
        }
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
        final char v;
        if(val==(v=(char)val))
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
        final char v;
        if(val==(v=(char)val))
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
        final char v;
        if((v=(char)val)==val)
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
        if(val instanceof Character)
        {
          return uncheckedlastIndexOf(size,(char)(val));
        }
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf(char val)
    {
      {
        int size;
        if((size=this.size)!=0)
        {
          return uncheckedlastIndexOf(size,(val));
        }
      }
      return -1;
    }
    @Override
    public
    int
    lastIndexOf(short val)
    {
      if(val>=0)
      {
        int size;
        if((size=this.size)!=0)
        {
          return uncheckedlastIndexOf(size,(val));
        }
      }
      return -1;
    }
    @Override
    void uncheckedCopyInto(char[] dst,int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(Character[] dst,int size)
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
    @Override
    void uncheckedCopyInto(int[] dst,int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,size);
    }
    @Override
    void uncheckedCopyInto(Object[] dst,int size)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,size);
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
    int uncheckedHashCode(int size)
    {
      final char[] arr;
      int hash,i;
      for(hash=31+((arr=this.arr)[i=0]);++i!=size;hash=hash*31+(arr[i])){}
      return hash;
    }
    @Override
    void uncheckedToString(int size,char[] buffer)
    {
      final char[] arr;
      int bufferOffset,i;
      for(buffer[bufferOffset=1]=(arr=this.arr)[i=0];++i!=size;buffer[++bufferOffset]=',',buffer[++bufferOffset]=' ',buffer[++bufferOffset]=arr[i]){}
    }
    private void sublistForEach(int rootOffset,int size,CharConsumer action)
    {
      final var arr=this.arr;
      for(size+=rootOffset;;)
      {
        action.accept((char)arr[rootOffset]);
        if(++rootOffset==size)
        {
          return;
        }
      }
    }
    @Override
    void uncheckedForEach(int size,CharConsumer action)
    {
      final var arr=this.arr;
      for(int i=0;;)
      {
        action.accept((char)arr[i]);
        if(++i==size)
        {
          return;
        }
      }
    }
    @Override
    boolean uncheckedRemoveIf(int size,CharPredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public OmniIterator.OfChar iterator()
    {
      //TODO
      return null;
    }
    @Override
    public OmniListIterator.OfChar listIterator()
    {
      //TODO
      return null;
    }
    @Override
    public OmniListIterator.OfChar listIterator(int index)
    {
      //TODO
      return null;
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor)
    {
      int size;
      T[] dst=arrConstructor.apply(size=this.size);
      if(size!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,dst,0,size);
      }
      return dst;
    }
    @Override
    public void add(int index,char val)
    {
      uncheckedAdd(index,val,this.size);
    }
    @Override
    public void add(int index,Character val)
    {
      add(index,(char)val);
    }
    private void uncheckedAdd(int index,char val,int size)
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
    @Override
    public Character remove(int index)
    {
      return removeCharAt(index);
    }
    @Override
    public char removeCharAt(int index)
    {
      return super.uncheckedremoveAtIndex(index,this.size);
    }
    @Override
    public void replaceAll(CharUnaryOperator operator)
    {
      int size;
      if((size=this.size)!=0)
      {
        uncheckedReplaceAll(size,operator);
      }
    }
    @Override
    public void replaceAll(UnaryOperator<Character> operator)
    {
      int size;
      if((size=this.size)!=0)
      {
        uncheckedReplaceAll(size,operator::apply);
      }
    }
    private void uncheckedReplaceAll(int size,CharUnaryOperator operator)
    {
      final var arr=this.arr;
      for(int i=0;;)
      {
        arr[i]=operator.applyAsChar((char)arr[i]);
        if(++i==size)
        {
          return;
        }
      }
    }
    @Override
    public char set(int index,char val)
    {
      final char[] arr;
      final char ret=(char)(arr=this.arr)[index];
      arr[index]=val;
      return ret;
    }
    @Override
    public Character set(int index,Character val)
    {
      return set(index,(char)val);
    }
    @Override
    public void sort(CharComparator sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        if(sorter==null)
        {
          CharSortUtil.uncheckedAscendingSort(this.arr,0,size);
        }
        else
        {
          CharSortUtil.uncheckedStableSort(this.arr,0,size,sorter);
        }
      }
    }
    @Override
    public void sort(Comparator<? super Character> sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        if(sorter==null)
        {
          CharSortUtil.uncheckedAscendingSort(this.arr,0,size);
        }
        else
        {
          CharSortUtil.uncheckedStableSort(this.arr,0,size,sorter::compare);
        }
      }
    }
    @Override
    public void stableAscendingSort()
    {
      int size;
      if((size=this.size)>1)
      {
        CharSortUtil.uncheckedAscendingSort(this.arr,0,size);
      }
    }
    @Override
    public void stableDescendingSort()
    {
      int size;
      if((size=this.size)>1)
      {
        CharSortUtil.uncheckedDescendingSort(this.arr,0,size);
      }
    }
    @Override
    public OmniList.OfChar subList(int fromIndex,int toIndex)
    {
      //TODO
      return null;
    }
    @Override
    public void unstableSort(CharComparator sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        if(sorter==null)
        {
          CharSortUtil.uncheckedAscendingSort(this.arr,0,size);
        }
        else
        {
          CharSortUtil.uncheckedUnstableSort(this.arr,0,size,sorter);
        }
      }
    }
  }
  public static class CheckedArrList extends UncheckedArrList
  {
    private static class SubListImpl implements OmniList.OfChar
    {
      transient int modCount;
      transient final CheckedArrList root;
      transient final SubListImpl parent;
      transient final int rootOffset;
      transient int size;
      SubListImpl(CheckedArrList root,int rootOffset,int size
        ,int modCount
      )
      {
        this.modCount=modCount;
        this.root=root;
        this.parent=null;
        this.rootOffset=rootOffset;
        this.size=size;
      }
      SubListImpl(SubListImpl parent,int rootOffset,int size
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
      public Object clone()
      {
        int modCount;
        final CheckedArrList root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        final char[] copy;
        int size;
        if((size=this.size)!=0)
        {
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new char[size],0,size);
        }
        else
        {
          copy=OmniArray.OfChar.DEFAULT_ARR;
        }
        return new CheckedArrList(size,copy);
      }
      @Override
      public boolean add(char val)
      {
        int rootSize;
        int modCount;
        final CheckedArrList root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        if((rootSize=root.size)!=0)
        {
          for(var curr=parent;curr!=null;parent.modCount=modCount,++parent.size){}
          ((CharArrSeq)root).uncheckedInsert(rootSize,this.rootOffset,val);
          ++this.size;
        }
        else
        {
          for(var curr=parent;curr!=null;parent.modCount=modCount,parent.size=1){}
          ((CharArrSeq)root).uncheckedInit(val);
          this.size=1;
        }
        return true;
      }
      @Override
      public void add(int index,char val)
      {
        int rootSize;
        int modCount;
        final CheckedArrList root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        int size;
        if((size=this.size)<index||index<0)
        {
          throw new InvalidReadIndexException(index,size);
        }
        root.modCount=++modCount;
        this.modCount=modCount;
        if((rootSize=root.size)!=0)
        {
          for(var curr=parent;curr!=null;parent.modCount=modCount,++parent.size){}
          ((CharArrSeq)root).uncheckedInsert(rootSize,index+this.rootOffset,val);
          ++this.size;
        }
        else
        {
          for(var curr=parent;curr!=null;parent.modCount=modCount,parent.size=1){}
          ((CharArrSeq)root).uncheckedInit(val);
          this.size=1;
        }
      }
      @Override
      public boolean add(Character val)
      {
        return add((char)val);
      }
      @Override
      public void add(int index,Character val)
      {
        add(index,(char)val);
      }
      @Override
      public void forEach(CharConsumer action)
      {
        int modCount=this.modCount;
        final var root=this.root;
        try
        {
          int size;
          if((size=this.size)!=0)
          {
            ((UncheckedArrList)root).sublistForEach(this.rootOffset,size,action);
          }
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      @Override
      public void forEach(Consumer<? super Character> action)
      {
        int modCount=this.modCount;
        final var root=this.root;
        try
        {
          int size;
          if((size=this.size)!=0)
          {
            ((UncheckedArrList)root).sublistForEach(this.rootOffset,size,action::accept);
          }
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      @Override
      public boolean removeIf(CharPredicate filter)
      {
        //TODO
        return false;
      }
      @Override
      public boolean removeIf(Predicate<? super Character> action)
      {
        //TODO
        return false;
      }
      @Override
      public char[] toCharArray()
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        final int size;
        if((size=this.size)!=0)
        {
          final char[] copy;
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new char[size],0,size);
          return copy;
        }
        return OmniArray.OfChar.DEFAULT_ARR;
      }
      @Override
      public Character[] toArray()
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        final int size;
        if((size=this.size)!=0)
        {
          final Character[] copy;
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new Character[size],0,size);
          return copy;
        }
        return OmniArray.OfChar.DEFAULT_BOXED_ARR;
      }
      @Override
      public double[] toDoubleArray()
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
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
        final CheckedArrList root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
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
        final CheckedArrList root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        final int size;
        if((size=this.size)!=0)
        {
          final long[] copy;
          ArrCopy.uncheckedCopy(root.arr,this.rootOffset,copy=new long[size],0,size);
          return copy;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
      }
      @Override
      public int[] toIntArray()
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
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
      public <T> T[] toArray(IntFunction<T[]> arrConstructor)
      {
        int size;
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
      public void put(int index,char val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        if(index<0 || index>=size)
        {
          throw new InvalidReadIndexException(index,size);
        }
        root.arr[index+this.rootOffset]=val;
      }
      @Override
      public char getChar(int index)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        if(index<0 || index>=size)
        {
          throw new InvalidReadIndexException(index,size);
        }
        return (char)root.arr[index+this.rootOffset];
      }
      @Override
      public Character get(int index)
      {
        return getChar(index);
      }
      @Override
      public <T> T[] toArray(T[] dst)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
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
      public void clear()
      {
        int modCount;
        final CheckedArrList root;
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
      @Override
      public OmniIterator.OfChar iterator()
      {
        //TODO
        return null;
      }
      @Override
      public OmniListIterator.OfChar listIterator()
      {
        //TODO
        return null;
      }
      @Override
      public OmniListIterator.OfChar listIterator(int index)
      {
        //TODO
        return null;
      }
      @Override
      public
      boolean
      contains(boolean val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        int size;
        if((size=this.size)!=0)
        {
          return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,TypeUtil.castToChar(val));
        }
        return false;
      }
      @Override
      public
      boolean
      contains(int val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        int size;
        if((size=this.size)!=0)
        {
          if(val==(char)val)
          {
            return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,val);
          }
        }
        return false;
      }
      @Override
      public
      boolean
      contains(long val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        int size;
        if((size=this.size)!=0)
        {
          final char v;
          if(val==(v=(char)val))
          {
            return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,v);
          }
        }
        return false;
      }
      @Override
      public
      boolean
      contains(float val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        int size;
        if((size=this.size)!=0)
        {
          final char v;
          if(val==(v=(char)val))
          {
            return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,v);
          }
        }
        return false;
      }
      @Override
      public
      boolean
      contains(double val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        int size;
        if((size=this.size)!=0)
        {
          final char v;
          if((v=(char)val)==val)
          {
            return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,v);
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
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        int size;
        if((size=this.size)!=0)
        {
          if(val instanceof Character)
          {
            return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,(char)(val));
          }
        }
        return false;
      }
      @Override
      public
      boolean
      contains(byte val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        if(val>=0)
        {
          int size;
          if((size=this.size)!=0)
          {
            return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,(val));
          }
        }
        return false;
      }
      @Override
      public
      boolean
      contains(char val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        {
          int size;
          if((size=this.size)!=0)
          {
            return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,(val));
          }
        }
        return false;
      }
      @Override
      public
      boolean
      contains(short val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        if(val>=0)
        {
          int size;
          if((size=this.size)!=0)
          {
            return ((UncheckedArrList)root).sublistcontains(this.rootOffset,size,(val));
          }
        }
        return false;
      }
      @Override
      public
      boolean
      removeVal(boolean val)
      {
        int size;
        if((size=this.size)!=0)
        {
          return uncheckedremoveVal(size,TypeUtil.castToChar(val));
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
          if(val==(char)val)
          {
            return uncheckedremoveVal(size,val);
          }
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
          final char v;
          if(val==(v=(char)val))
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
          final char v;
          if(val==(v=(char)val))
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
          final char v;
          if((v=(char)val)==val)
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
          if(val instanceof Character)
          {
            return uncheckedremoveVal(size,(char)(val));
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
        if(val>=0)
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
      public
      boolean
      removeVal(short val)
      {
        if(val>=0)
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
      int
      indexOf(boolean val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        int size;
        if((size=this.size)!=0)
        {
          return ((UncheckedArrList)root).sublistindexOf(this.rootOffset,size,TypeUtil.castToChar(val));
        }
        return -1;
      }
      @Override
      public
      int
      indexOf(int val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        int size;
        if((size=this.size)!=0)
        {
          if(val==(char)val)
          {
            return ((UncheckedArrList)root).sublistindexOf(this.rootOffset,size,val);
          }
        }
        return -1;
      }
      @Override
      public
      int
      indexOf(long val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        int size;
        if((size=this.size)!=0)
        {
          final char v;
          if(val==(v=(char)val))
          {
            return ((UncheckedArrList)root).sublistindexOf(this.rootOffset,size,v);
          }
        }
        return -1;
      }
      @Override
      public
      int
      indexOf(float val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        int size;
        if((size=this.size)!=0)
        {
          final char v;
          if(val==(v=(char)val))
          {
            return ((UncheckedArrList)root).sublistindexOf(this.rootOffset,size,v);
          }
        }
        return -1;
      }
      @Override
      public
      int
      indexOf(double val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        int size;
        if((size=this.size)!=0)
        {
          final char v;
          if((v=(char)val)==val)
          {
            return ((UncheckedArrList)root).sublistindexOf(this.rootOffset,size,v);
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
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        int size;
        if((size=this.size)!=0)
        {
          if(val instanceof Character)
          {
            return ((UncheckedArrList)root).sublistindexOf(this.rootOffset,size,(char)(val));
          }
        }
        return -1;
      }
      @Override
      public
      int
      indexOf(char val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        {
          int size;
          if((size=this.size)!=0)
          {
            return ((UncheckedArrList)root).sublistindexOf(this.rootOffset,size,(val));
          }
        }
        return -1;
      }
      @Override
      public
      int
      indexOf(short val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        if(val>=0)
        {
          int size;
          if((size=this.size)!=0)
          {
            return ((UncheckedArrList)root).sublistindexOf(this.rootOffset,size,(val));
          }
        }
        return -1;
      }
      @Override
      public
      int
      lastIndexOf(boolean val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        int size;
        if((size=this.size)!=0)
        {
          return ((UncheckedArrList)root).sublistlastIndexOf(this.rootOffset,size,TypeUtil.castToChar(val));
        }
        return -1;
      }
      @Override
      public
      int
      lastIndexOf(int val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        int size;
        if((size=this.size)!=0)
        {
          if(val==(char)val)
          {
            return ((UncheckedArrList)root).sublistlastIndexOf(this.rootOffset,size,val);
          }
        }
        return -1;
      }
      @Override
      public
      int
      lastIndexOf(long val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        int size;
        if((size=this.size)!=0)
        {
          final char v;
          if(val==(v=(char)val))
          {
            return ((UncheckedArrList)root).sublistlastIndexOf(this.rootOffset,size,v);
          }
        }
        return -1;
      }
      @Override
      public
      int
      lastIndexOf(float val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        int size;
        if((size=this.size)!=0)
        {
          final char v;
          if(val==(v=(char)val))
          {
            return ((UncheckedArrList)root).sublistlastIndexOf(this.rootOffset,size,v);
          }
        }
        return -1;
      }
      @Override
      public
      int
      lastIndexOf(double val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        int size;
        if((size=this.size)!=0)
        {
          final char v;
          if((v=(char)val)==val)
          {
            return ((UncheckedArrList)root).sublistlastIndexOf(this.rootOffset,size,v);
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
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        int size;
        if((size=this.size)!=0)
        {
          if(val instanceof Character)
          {
            return ((UncheckedArrList)root).sublistlastIndexOf(this.rootOffset,size,(char)(val));
          }
        }
        return -1;
      }
      @Override
      public
      int
      lastIndexOf(char val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        {
          int size;
          if((size=this.size)!=0)
          {
            return ((UncheckedArrList)root).sublistlastIndexOf(this.rootOffset,size,(val));
          }
        }
        return -1;
      }
      @Override
      public
      int
      lastIndexOf(short val)
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        if(val>=0)
        {
          int size;
          if((size=this.size)!=0)
          {
            return ((UncheckedArrList)root).sublistlastIndexOf(this.rootOffset,size,(val));
          }
        }
        return -1;
      }
      @Override
      public String toString()
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        int size;
        if((size=this.size)!=0)
        {
          if(size>(Integer.MAX_VALUE/3))
          {
            throw new OutOfMemoryError();
          }
          final char[] buffer;
          final char[] arr;
          int bufferOffset,i;
          for((buffer=new char[size*3])[bufferOffset=1]=(arr=root.arr)[i=this.rootOffset],size+=i;++i!=size;buffer[++bufferOffset]=',',buffer[++bufferOffset]=' ',buffer[++bufferOffset]=arr[i]){}
          buffer[++bufferOffset]=']';
          buffer[0]='[';
          return new String(buffer,0,bufferOffset+1);
        }
        return "[]";
      }
      @Override
      public boolean equals(Object val)
      {
        //TODO
        return false;
      }
      @Override
      public int hashCode()
      {
        final CheckedArrList root;
        CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
        int size;
        if((size=this.size)!=0)
        {
          final char[] arr;
          int hash;
          int i;
          for(hash=31+((arr=root.arr)[i=this.rootOffset]),size+=i;++i!=size;hash=hash*31+(arr[i])){}
          return hash;
        }
        return 1;
      }
      @Override
      public int size()
      {
        CheckedCollection.checkModCount(this.modCount,root.modCount);
        return this.size;
      }
      @Override
      public boolean isEmpty()
      {
        CheckedCollection.checkModCount(this.modCount,root.modCount);
        return this.size==0;
      }
        private boolean
        uncheckedremoveVal (int size
        ,int val
        )
        {
          final UncheckedArrList root;
          final var arr=(root=this.root).arr;
          {
            for(int i=this.rootOffset,bound=i+size;;)
            {
              if(
              val==arr[i]
              )
              {
                for(var curr=parent;curr!=null;--curr.size){}
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
    }
    transient int modCount;
    public CheckedArrList()
    {
      super();
    }
    public CheckedArrList(int initialCapacity)
    {
      super(initialCapacity);
    }
    public CheckedArrList(int size,char[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO
      return false;
    }
    @Override
    public Object clone()
    {
      char[] copy;
      int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new char[size],0,size);
      }
      else
      {
        copy=OmniArray.OfChar.DEFAULT_ARR;
      }
      return new CheckedArrList(size,copy);
    }
    @Override
    public void push(char val)
    {
      ++this.modCount;
      super.push(val);
    }
    @Override
    public void put(int index,char val)
    {
      if(index>=0 && index<size)
      {
        super.put(index,val);
        return;
      }
      throw new InvalidWriteIndexException(index,size);
    }
    @Override
    public char set(int index,char val)
    {
      if(index>=0 && index<size)
      {
        return super.set(index,val);
      }
      throw new InvalidReadIndexException(index,size);
    }
    @Override
    public char removeCharAt(int index)
    {
      int size;
      if((size=this.size)>index && index>=0)
      {
        ++this.modCount;
        return ((CharArrSeq)this).uncheckedremoveAtIndex(index,size);
      }
      throw new InvalidWriteIndexException(index,size);
    }
    @Override
    public void unstableSort(CharComparator sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        if(sorter==null)
        {
          CharSortUtil.uncheckedAscendingSort(this.arr,0,size);
          ++this.modCount;
        }
        else
        {
          int modCount=this.modCount;
          try
          {
            CharSortUtil.uncheckedUnstableSort(this.arr,0,size,sorter);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.modCount=modCount+1;
          }
        }
      }
    }
    @Override
    public void replaceAll(UnaryOperator<Character> operator)
    {
      int size;
      if((size=this.size)!=0)
      {
        int modCount=this.modCount;
        try
        {
          super.uncheckedReplaceAll(size,operator::apply);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override
    public void replaceAll(CharUnaryOperator operator)
    {
      int size;
      if((size=this.size)!=0)
      {
        int modCount=this.modCount;
        try
        {
          super.uncheckedReplaceAll(size,operator);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override
    public void stableAscendingSort()
    {
      int size;
      if((size=this.size)>1)
      {
        CharSortUtil.uncheckedAscendingSort(this.arr,0,size);
        ++this.modCount;
      }
    }
    @Override
    public void stableDescendingSort()
    {
      int size;
      if((size=this.size)>1)
      {
        CharSortUtil.uncheckedDescendingSort(this.arr,0,size);
        ++this.modCount;
      }
    }
    @Override
    public OmniList.OfChar subList(int fromIndex,int toIndex)
    {
      //TODO
      return null;
    }
    @Override
    public void sort(CharComparator sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        if(sorter==null)
        {
          CharSortUtil.uncheckedAscendingSort(this.arr,0,size);
          ++this.modCount;
        }
        else
        {
          int modCount=this.modCount;
          try
          {
            CharSortUtil.uncheckedStableSort(this.arr,0,size,sorter);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.modCount=modCount+1;
          }
        }
      }
    }
    @Override
    public void sort(Comparator<? super Character> sorter)
    {
      int size;
      if((size=this.size)>1)
      {
        if(sorter==null)
        {
          CharSortUtil.uncheckedAscendingSort(this.arr,0,size);
          ++this.modCount;
        }
        else
        {
          int modCount=this.modCount;
          try
          {
            CharSortUtil.uncheckedStableSort(this.arr,0,size,sorter::compare);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.modCount=modCount+1;
          }
        }
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
    void uncheckedForEach(int size,CharConsumer action)
    {
      int modCount=this.modCount;
      try
      {
        super.uncheckedForEach(size,action);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override
    boolean uncheckedRemoveIf(int size,CharPredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public OmniIterator.OfChar iterator()
    {
      //TODO
      return null;
    }
    @Override
    public OmniListIterator.OfChar listIterator()
    {
      //TODO
      return null;
    }
    @Override
    public OmniListIterator.OfChar listIterator(int index)
    {
      //TODO
      return null;
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
    @Override
    public char getChar(int index)
    {
      if(index>=0 && index<size)
      {
        return super.getChar(index);
      }
      throw new InvalidReadIndexException(index,size);
    }
    @Override
    public void add(int index,char val)
    {
      int size;
      if(index<=(size=this.size)&& index>=0)
      {
        ++this.modCount;
        super.uncheckedAdd(index,val,size);
        return;
      }
      throw new InvalidWriteIndexException(index,size);
    }
  }
}
