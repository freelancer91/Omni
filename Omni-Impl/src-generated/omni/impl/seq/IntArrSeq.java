package omni.impl.seq;
import omni.api.OmniCollection;
import omni.util.OmniArray;
import omni.api.OmniList;
import omni.api.OmniStack;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.function.IntPredicate;
import java.util.function.IntConsumer;
import java.util.Comparator;
import java.util.function.IntBinaryOperator;
import omni.util.ToStringUtil;
import omni.util.ArrCopy;
import omni.util.IntSortUtil;
import omni.impl.CheckedCollection;
import omni.util.TypeUtil;
import java.util.NoSuchElementException;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import java.util.function.IntFunction;
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
  public int size()
  {
    return this.size;
  }
  @Override
  public boolean isEmpty()
  {
    return this.size==0;
  }
  @Override
  public void clear()
  {
    this.size=0;
  }
  public static class UncheckedListImpl extends IntArrSeq implements OmniList.OfInt
  {
    static int uncheckedToString(int[] arr,int begin,int end,char[] buffer)
    {
      int bufferOffset;
      for(bufferOffset=ToStringUtil.getStringInt(arr[begin],buffer,1);begin!=end;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringInt(arr[++begin],buffer,bufferOffset)){}
      return bufferOffset;
    }
    static void uncheckedToString(int[] arr,int begin,int end,ToStringUtil.OmniStringBuilder builder)
    {
      for(builder.uncheckedAppendInt(arr[begin]);begin!=end;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendInt(arr[++begin])){}
    }
    static  void uncheckedForEach(int[] arr,int begin,int end,IntConsumer action)
    {
      for(;;++begin)
      {
        action.accept((int)arr[begin]);
        if(begin==end)
        {
          return;
        }
      }
    }
    static int uncheckedHashCode(int[] arr,int begin,int end)
    {
      int hash=31+(arr[begin]);
      while(begin!=end)
      {
        hash=hash*31+(arr[++begin]);
      }
      return hash;
    }
    public UncheckedListImpl()
    {
      super();
    }
    public UncheckedListImpl(int initialCapacity)
    {
      super(initialCapacity);
    }
    private UncheckedListImpl(int size,int[] arr)
    {
      super(size,arr);
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
          (buffer=new char[size*(13)])[size=uncheckedToString(arr,0,size-1,buffer)]=']';
          buffer[0]='[';
          return new String(buffer,0,size+1);
        }
        else
        {
          final ToStringUtil.OmniStringBuilder builder;
          uncheckedToString(arr,0,size-1,builder=new ToStringUtil.OmniStringBuilder(1,new char[size<=(int)(OmniArray.MAX_ARR_SIZE>>>3)?(size<<3):OmniArray.MAX_ARR_SIZE]));
          builder.uncheckedAppendChar(']');
          (buffer=builder.buffer)[0]='[';
          return new String(buffer,0,builder.size);
        }
      }
      return "[]";
    }
    @Override
    public int hashCode()
    {
      final int size;
      if((size=this.size)!=0)
      {
        {
          return uncheckedHashCode(arr,0,size-1);
        }
      }
      return 1;
    }
    @Override
    public <T> T[] toArray(T[] arr)
    {
      //TODO
      return null;
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor)
    {
      //TODO
      return null;
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
      final int size;
      if((size=this.size)!=0)
      {
        final int[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new int[size],0,size);
        return new UncheckedListImpl(size,copy);
      }
      return new UncheckedListImpl();
    }
    @Override
    public void forEach(IntConsumer action)
    {
      final int size;
      if((size=this.size)!=0)
      {
        {
          uncheckedForEach(arr,0,size-1,action);
        }
      }
    }
    @Override
    public void forEach(Consumer<? super Integer> action)
    {
      final int size;
      if((size=this.size)!=0)
      {
        {
          uncheckedForEach(arr,0,size-1,action::accept);
        }
      }
    }
    @Override
    public boolean removeIf(IntPredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Integer> filter)
    {
      //TODO
      return false;
    }
   @Override
   public
   boolean
   contains(boolean val)
   {
     final int size;
     if((size=this.size)!=0)
     {
       return OmniArray.OfInt.uncheckedcontains(this.arr,0,size-1,(int)TypeUtil.castToByte(val));
     }
     return false;
   }
   @Override
   public
   boolean
   contains(int val)
   {
     final int size;
     if((size=this.size)!=0)
     {
       return OmniArray.OfInt.uncheckedcontains(this.arr,0,size-1,(val));
     }
     return false;
   }
   @Override
   public
   boolean
   contains(long val)
   {
     final int size;
     if((size=this.size)!=0)
     {
       final int v;
       if(val==(v=(int)val))
       {
         return OmniArray.OfInt.uncheckedcontains(this.arr,0,size-1,v);
       }
     }
     return false;
   }
   @Override
   public
   boolean
   contains(float val)
   {
     final int size;
     if((size=this.size)!=0)
     {
       final int v;
       if((double)val==(double)(v=(int)val))
       {
         return OmniArray.OfInt.uncheckedcontains(this.arr,0,size-1,v);
       }
     }
     return false;
   }
   @Override
   public
   boolean
   contains(double val)
   {
     final int size;
     if((size=this.size)!=0)
     {
       final int v;
       if((v=(int)val)==val)
       {
         return OmniArray.OfInt.uncheckedcontains(this.arr,0,size-1,v);
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
     final int size;
     if((size=this.size)!=0)
     {
       if(val instanceof Integer)
       {
         return OmniArray.OfInt.uncheckedcontains(this.arr,0,size-1,(int)(val));
       }
     }
     return false;
   }
    @Override
    public boolean add(int val)
    {
      //TODO
      return false;
    }
    @Override
    public void add(int index,int val)
    {
      //TODO
    }
    @Override
    public int removeIntAt(int index)
    {
      final int[] arr;
      int ret=(int)(arr=this.arr)[index];
      ArrCopy.semicheckedSelfCopy(arr,index+1,index,(--size)-index);
      this.size=size;
      return ret;
    }
    @Override
    public OmniListIterator.OfInt listIterator()
    {
      //TODO
      return null;
    }
    @Override
    public OmniListIterator.OfInt listIterator(int index)
    {
      //TODO
      return null;
    }
    @Override
    public OmniList.OfInt subList(int fromIndex,int toIndex)
    {
      //TODO
      return null;
    }
    @Override
    public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        {
          IntSortUtil.uncheckedDescendingSort(arr,0,size);
        }
      }
    }
    @Override
    public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        {
          IntSortUtil.uncheckedAscendingSort(arr,0,size);
        }
      }
    }
    @Override
    public OmniIterator.OfInt iterator()
    {
      //TODO
      return null;
    }
    @Override
    public int[] toIntArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final int[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new int[size],0,size);
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
        ArrCopy.uncheckedCopy(arr,0,copy=new Integer[size],0,size);
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
        ArrCopy.uncheckedCopy(arr,0,copy=new double[size],0,size);
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
        ArrCopy.uncheckedCopy(arr,0,copy=new float[size],0,size);
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
        ArrCopy.uncheckedCopy(arr,0,copy=new long[size],0,size);
        return copy;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
  }
  public static class UncheckedStackImpl extends IntArrSeq implements OmniStack.OfInt
  {
    static int uncheckedToString(int[] arr,int begin,int end,char[] buffer)
    {
      int bufferOffset;
      for(bufferOffset=ToStringUtil.getStringInt(arr[end],buffer,1);begin!=end;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringInt(arr[--end],buffer,bufferOffset)){}
      return bufferOffset;
    }
    static void uncheckedToString(int[] arr,int begin,int end,ToStringUtil.OmniStringBuilder builder)
    {
      for(builder.uncheckedAppendInt(arr[end]);begin!=end;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendInt(arr[--end])){}
    }
    static  void uncheckedForEach(int[] arr,int begin,int end,IntConsumer action)
    {
      for(;;--end)
      {
        action.accept((int)arr[end]);
        if(begin==end)
        {
          return;
        }
      }
    }
    static int uncheckedHashCode(int[] arr,int begin,int end)
    {
      int hash=31+(arr[end]);
      while(begin!=end)
      {
        hash=hash*31+(arr[--end]);
      }
      return hash;
    }
    public UncheckedStackImpl()
    {
      super();
    }
    public UncheckedStackImpl(int initialCapacity)
    {
      super(initialCapacity);
    }
    private UncheckedStackImpl(int size,int[] arr)
    {
      super(size,arr);
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
          (buffer=new char[size*(13)])[size=uncheckedToString(arr,0,size-1,buffer)]=']';
          buffer[0]='[';
          return new String(buffer,0,size+1);
        }
        else
        {
          final ToStringUtil.OmniStringBuilder builder;
          uncheckedToString(arr,0,size-1,builder=new ToStringUtil.OmniStringBuilder(1,new char[size<=(int)(OmniArray.MAX_ARR_SIZE>>>3)?(size<<3):OmniArray.MAX_ARR_SIZE]));
          builder.uncheckedAppendChar(']');
          (buffer=builder.buffer)[0]='[';
          return new String(buffer,0,builder.size);
        }
      }
      return "[]";
    }
    @Override
    public int hashCode()
    {
      final int size;
      if((size=this.size)!=0)
      {
        {
          return uncheckedHashCode(arr,0,size-1);
        }
      }
      return 1;
    }
    @Override
    public <T> T[] toArray(T[] arr)
    {
      //TODO
      return null;
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor)
    {
      //TODO
      return null;
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
      final int size;
      if((size=this.size)!=0)
      {
        final int[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new int[size],0,size);
        return new UncheckedStackImpl(size,copy);
      }
      return new UncheckedStackImpl();
    }
    @Override
    public void forEach(IntConsumer action)
    {
      final int size;
      if((size=this.size)!=0)
      {
        {
          uncheckedForEach(arr,0,size-1,action);
        }
      }
    }
    @Override
    public void forEach(Consumer<? super Integer> action)
    {
      final int size;
      if((size=this.size)!=0)
      {
        {
          uncheckedForEach(arr,0,size-1,action::accept);
        }
      }
    }
    @Override
    public boolean removeIf(IntPredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Integer> filter)
    {
      //TODO
      return false;
    }
   @Override
   public
   boolean
   contains(boolean val)
   {
     final int size;
     if((size=this.size)!=0)
     {
       return OmniArray.OfInt.uncheckedcontains(this.arr,0,size-1,(int)TypeUtil.castToByte(val));
     }
     return false;
   }
   @Override
   public
   boolean
   contains(int val)
   {
     final int size;
     if((size=this.size)!=0)
     {
       return OmniArray.OfInt.uncheckedcontains(this.arr,0,size-1,(val));
     }
     return false;
   }
   @Override
   public
   boolean
   contains(long val)
   {
     final int size;
     if((size=this.size)!=0)
     {
       final int v;
       if(val==(v=(int)val))
       {
         return OmniArray.OfInt.uncheckedcontains(this.arr,0,size-1,v);
       }
     }
     return false;
   }
   @Override
   public
   boolean
   contains(float val)
   {
     final int size;
     if((size=this.size)!=0)
     {
       final int v;
       if((double)val==(double)(v=(int)val))
       {
         return OmniArray.OfInt.uncheckedcontains(this.arr,0,size-1,v);
       }
     }
     return false;
   }
   @Override
   public
   boolean
   contains(double val)
   {
     final int size;
     if((size=this.size)!=0)
     {
       final int v;
       if((v=(int)val)==val)
       {
         return OmniArray.OfInt.uncheckedcontains(this.arr,0,size-1,v);
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
     final int size;
     if((size=this.size)!=0)
     {
       if(val instanceof Integer)
       {
         return OmniArray.OfInt.uncheckedcontains(this.arr,0,size-1,(int)(val));
       }
     }
     return false;
   }
    @Override
    public OmniIterator.OfInt iterator()
    {
      //TODO
      return null;
    }
    @Override
    public int[] toIntArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final int[] copy;
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new int[size],0,size);
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
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new Integer[size],0,size);
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
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new double[size],0,size);
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
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new float[size],0,size);
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
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new long[size],0,size);
        return copy;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override
    public int popInt()
    {
      int size;
      if((size=this.size)!=0)
      {
        int ret=(int)arr[--size];
        this.size=size;
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override
    public Integer pop()
    {
      return popInt();
    }
    @Override
    public void push(int val)
    {
      //TODO
    }
    @Override
    public void push(Integer val)
    {
      //TODO
    }
    @Override
    public int peekInt()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (int)((int)arr[size-1]);
      }
      return Integer.MIN_VALUE;
    }
    @Override
    public int pollInt()
    {
      int size;
      if((size=this.size)!=0)
      {
        int ret=(int)((int)arr[--size]);
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
        Integer ret=(Integer)((int)arr[--size]);
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
        double ret=(double)((int)arr[--size]);
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
        float ret=(float)((int)arr[--size]);
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
        long ret=(long)((int)arr[--size]);
        this.size=size;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override
    public Integer peek()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (Integer)((int)arr[size-1]);
      }
      return null;
    }
    @Override
    public double peekDouble()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (double)((int)arr[size-1]);
      }
      return Double.NaN;
    }
    @Override
    public float peekFloat()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (float)((int)arr[size-1]);
      }
      return Float.NaN;
    }
    @Override
    public long peekLong()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (long)((int)arr[size-1]);
      }
      return Long.MIN_VALUE;
    }
  }
  public static class CheckedListImpl extends IntArrSeq implements OmniList.OfInt
  {
    static int uncheckedToString(int[] arr,int begin,int end,char[] buffer)
    {
      int bufferOffset;
      for(bufferOffset=ToStringUtil.getStringInt(arr[begin],buffer,1);begin!=end;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringInt(arr[++begin],buffer,bufferOffset)){}
      return bufferOffset;
    }
    static void uncheckedToString(int[] arr,int begin,int end,ToStringUtil.OmniStringBuilder builder)
    {
      for(builder.uncheckedAppendInt(arr[begin]);begin!=end;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendInt(arr[++begin])){}
    }
    static  void uncheckedForEach(int[] arr,int begin,int end,IntConsumer action)
    {
      for(;;++begin)
      {
        action.accept((int)arr[begin]);
        if(begin==end)
        {
          return;
        }
      }
    }
    static int uncheckedHashCode(int[] arr,int begin,int end)
    {
      int hash=31+(arr[begin]);
      while(begin!=end)
      {
        hash=hash*31+(arr[++begin]);
      }
      return hash;
    }
    transient int modCount;
    public CheckedListImpl()
    {
      super();
    }
    public CheckedListImpl(int initialCapacity)
    {
      super(initialCapacity);
    }
    private CheckedListImpl(int size,int[] arr)
    {
      super(size,arr);
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
          (buffer=new char[size*(13)])[size=uncheckedToString(arr,0,size-1,buffer)]=']';
          buffer[0]='[';
          return new String(buffer,0,size+1);
        }
        else
        {
          final ToStringUtil.OmniStringBuilder builder;
          uncheckedToString(arr,0,size-1,builder=new ToStringUtil.OmniStringBuilder(1,new char[size<=(int)(OmniArray.MAX_ARR_SIZE>>>3)?(size<<3):OmniArray.MAX_ARR_SIZE]));
          builder.uncheckedAppendChar(']');
          (buffer=builder.buffer)[0]='[';
          return new String(buffer,0,builder.size);
        }
      }
      return "[]";
    }
    @Override
    public int hashCode()
    {
      final int size;
      if((size=this.size)!=0)
      {
        {
          return uncheckedHashCode(arr,0,size-1);
        }
      }
      return 1;
    }
    @Override
    public <T> T[] toArray(T[] arr)
    {
      //TODO
      return null;
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor)
    {
      //TODO
      return null;
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
      final int size;
      if((size=this.size)!=0)
      {
        final int[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new int[size],0,size);
        return new CheckedListImpl(size,copy);
      }
      return new CheckedListImpl();
    }
    @Override
    public void forEach(IntConsumer action)
    {
      final int size;
      if((size=this.size)!=0)
      {
        int modCount=this.modCount;
        try
        {
          uncheckedForEach(arr,0,size-1,action);
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
      final int size;
      if((size=this.size)!=0)
      {
        int modCount=this.modCount;
        try
        {
          uncheckedForEach(arr,0,size-1,action::accept);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override
    public boolean removeIf(IntPredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Integer> filter)
    {
      //TODO
      return false;
    }
   @Override
   public
   boolean
   contains(boolean val)
   {
     final int size;
     if((size=this.size)!=0)
     {
       return OmniArray.OfInt.uncheckedcontains(this.arr,0,size-1,(int)TypeUtil.castToByte(val));
     }
     return false;
   }
   @Override
   public
   boolean
   contains(int val)
   {
     final int size;
     if((size=this.size)!=0)
     {
       return OmniArray.OfInt.uncheckedcontains(this.arr,0,size-1,(val));
     }
     return false;
   }
   @Override
   public
   boolean
   contains(long val)
   {
     final int size;
     if((size=this.size)!=0)
     {
       final int v;
       if(val==(v=(int)val))
       {
         return OmniArray.OfInt.uncheckedcontains(this.arr,0,size-1,v);
       }
     }
     return false;
   }
   @Override
   public
   boolean
   contains(float val)
   {
     final int size;
     if((size=this.size)!=0)
     {
       final int v;
       if((double)val==(double)(v=(int)val))
       {
         return OmniArray.OfInt.uncheckedcontains(this.arr,0,size-1,v);
       }
     }
     return false;
   }
   @Override
   public
   boolean
   contains(double val)
   {
     final int size;
     if((size=this.size)!=0)
     {
       final int v;
       if((v=(int)val)==val)
       {
         return OmniArray.OfInt.uncheckedcontains(this.arr,0,size-1,v);
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
     final int size;
     if((size=this.size)!=0)
     {
       int modCount=this.modCount;
       if(val instanceof Integer)
       {
         return OmniArray.OfInt.uncheckedcontains(this.arr,0,size-1,(int)(val));
       }
     }
     CheckedCollection.checkModCount(modCount,this.modCount);
     return false;
   }
    @Override
    public boolean add(int val)
    {
      //TODO
      return false;
    }
    @Override
    public void add(int index,int val)
    {
      //TODO
    }
    @Override
    public int removeIntAt(int index)
    {
      int size;
      if(index<0 || index>=(size=this.size))
      {
        throw new IndexOutOfBoundsException("index = "+index+"; size="+this.size);
      }
      ++this.modCount;
      final int[] arr;
      int ret=(int)(arr=this.arr)[index];
      ArrCopy.semicheckedSelfCopy(arr,index+1,index,(--size)-index);
      this.size=size;
      return ret;
    }
    @Override
    public OmniListIterator.OfInt listIterator()
    {
      //TODO
      return null;
    }
    @Override
    public OmniListIterator.OfInt listIterator(int index)
    {
      //TODO
      return null;
    }
    @Override
    public OmniList.OfInt subList(int fromIndex,int toIndex)
    {
      //TODO
      return null;
    }
    @Override
    public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        {
          IntSortUtil.uncheckedDescendingSort(arr,0,size);
        }
      }
    }
    @Override
    public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)>1)
      {
        {
          IntSortUtil.uncheckedAscendingSort(arr,0,size);
        }
      }
    }
    @Override
    public OmniIterator.OfInt iterator()
    {
      //TODO
      return null;
    }
    @Override
    public int[] toIntArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final int[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new int[size],0,size);
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
        ArrCopy.uncheckedCopy(arr,0,copy=new Integer[size],0,size);
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
        ArrCopy.uncheckedCopy(arr,0,copy=new double[size],0,size);
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
        ArrCopy.uncheckedCopy(arr,0,copy=new float[size],0,size);
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
        ArrCopy.uncheckedCopy(arr,0,copy=new long[size],0,size);
        return copy;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
  }
  public static class CheckedStackImpl extends IntArrSeq implements OmniStack.OfInt
  {
    static int uncheckedToString(int[] arr,int begin,int end,char[] buffer)
    {
      int bufferOffset;
      for(bufferOffset=ToStringUtil.getStringInt(arr[end],buffer,1);begin!=end;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',bufferOffset=ToStringUtil.getStringInt(arr[--end],buffer,bufferOffset)){}
      return bufferOffset;
    }
    static void uncheckedToString(int[] arr,int begin,int end,ToStringUtil.OmniStringBuilder builder)
    {
      for(builder.uncheckedAppendInt(arr[end]);begin!=end;builder.uncheckedAppendCommaAndSpace(),builder.uncheckedAppendInt(arr[--end])){}
    }
    static  void uncheckedForEach(int[] arr,int begin,int end,IntConsumer action)
    {
      for(;;--end)
      {
        action.accept((int)arr[end]);
        if(begin==end)
        {
          return;
        }
      }
    }
    static int uncheckedHashCode(int[] arr,int begin,int end)
    {
      int hash=31+(arr[end]);
      while(begin!=end)
      {
        hash=hash*31+(arr[--end]);
      }
      return hash;
    }
    transient int modCount;
    public CheckedStackImpl()
    {
      super();
    }
    public CheckedStackImpl(int initialCapacity)
    {
      super(initialCapacity);
    }
    private CheckedStackImpl(int size,int[] arr)
    {
      super(size,arr);
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
          (buffer=new char[size*(13)])[size=uncheckedToString(arr,0,size-1,buffer)]=']';
          buffer[0]='[';
          return new String(buffer,0,size+1);
        }
        else
        {
          final ToStringUtil.OmniStringBuilder builder;
          uncheckedToString(arr,0,size-1,builder=new ToStringUtil.OmniStringBuilder(1,new char[size<=(int)(OmniArray.MAX_ARR_SIZE>>>3)?(size<<3):OmniArray.MAX_ARR_SIZE]));
          builder.uncheckedAppendChar(']');
          (buffer=builder.buffer)[0]='[';
          return new String(buffer,0,builder.size);
        }
      }
      return "[]";
    }
    @Override
    public int hashCode()
    {
      final int size;
      if((size=this.size)!=0)
      {
        {
          return uncheckedHashCode(arr,0,size-1);
        }
      }
      return 1;
    }
    @Override
    public <T> T[] toArray(T[] arr)
    {
      //TODO
      return null;
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor)
    {
      //TODO
      return null;
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
      final int size;
      if((size=this.size)!=0)
      {
        final int[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new int[size],0,size);
        return new CheckedStackImpl(size,copy);
      }
      return new CheckedStackImpl();
    }
    @Override
    public void forEach(IntConsumer action)
    {
      final int size;
      if((size=this.size)!=0)
      {
        int modCount=this.modCount;
        try
        {
          uncheckedForEach(arr,0,size-1,action);
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
      final int size;
      if((size=this.size)!=0)
      {
        int modCount=this.modCount;
        try
        {
          uncheckedForEach(arr,0,size-1,action::accept);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override
    public boolean removeIf(IntPredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Integer> filter)
    {
      //TODO
      return false;
    }
   @Override
   public
   boolean
   contains(boolean val)
   {
     final int size;
     if((size=this.size)!=0)
     {
       return OmniArray.OfInt.uncheckedcontains(this.arr,0,size-1,(int)TypeUtil.castToByte(val));
     }
     return false;
   }
   @Override
   public
   boolean
   contains(int val)
   {
     final int size;
     if((size=this.size)!=0)
     {
       return OmniArray.OfInt.uncheckedcontains(this.arr,0,size-1,(val));
     }
     return false;
   }
   @Override
   public
   boolean
   contains(long val)
   {
     final int size;
     if((size=this.size)!=0)
     {
       final int v;
       if(val==(v=(int)val))
       {
         return OmniArray.OfInt.uncheckedcontains(this.arr,0,size-1,v);
       }
     }
     return false;
   }
   @Override
   public
   boolean
   contains(float val)
   {
     final int size;
     if((size=this.size)!=0)
     {
       final int v;
       if((double)val==(double)(v=(int)val))
       {
         return OmniArray.OfInt.uncheckedcontains(this.arr,0,size-1,v);
       }
     }
     return false;
   }
   @Override
   public
   boolean
   contains(double val)
   {
     final int size;
     if((size=this.size)!=0)
     {
       final int v;
       if((v=(int)val)==val)
       {
         return OmniArray.OfInt.uncheckedcontains(this.arr,0,size-1,v);
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
     final int size;
     if((size=this.size)!=0)
     {
       int modCount=this.modCount;
       if(val instanceof Integer)
       {
         return OmniArray.OfInt.uncheckedcontains(this.arr,0,size-1,(int)(val));
       }
     }
     CheckedCollection.checkModCount(modCount,this.modCount);
     return false;
   }
    @Override
    public OmniIterator.OfInt iterator()
    {
      //TODO
      return null;
    }
    @Override
    public int[] toIntArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final int[] copy;
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new int[size],0,size);
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
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new Integer[size],0,size);
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
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new double[size],0,size);
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
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new float[size],0,size);
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
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new long[size],0,size);
        return copy;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override
    public int popInt()
    {
      int size;
      if((size=this.size)!=0)
      {
        int ret=(int)arr[--size];
        ++this.modCount;
        this.size=size;
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override
    public Integer pop()
    {
      return popInt();
    }
    @Override
    public void push(int val)
    {
      //TODO
    }
    @Override
    public void push(Integer val)
    {
      //TODO
    }
    @Override
    public int peekInt()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (int)((int)arr[size-1]);
      }
      return Integer.MIN_VALUE;
    }
    @Override
    public int pollInt()
    {
      int size;
      if((size=this.size)!=0)
      {
        int ret=(int)((int)arr[--size]);
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
        Integer ret=(Integer)((int)arr[--size]);
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
        double ret=(double)((int)arr[--size]);
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
        float ret=(float)((int)arr[--size]);
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
        long ret=(long)((int)arr[--size]);
        ++this.modCount;
        this.size=size;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override
    public Integer peek()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (Integer)((int)arr[size-1]);
      }
      return null;
    }
    @Override
    public double peekDouble()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (double)((int)arr[size-1]);
      }
      return Double.NaN;
    }
    @Override
    public float peekFloat()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (float)((int)arr[size-1]);
      }
      return Float.NaN;
    }
    @Override
    public long peekLong()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (long)((int)arr[size-1]);
      }
      return Long.MIN_VALUE;
    }
  }
}
