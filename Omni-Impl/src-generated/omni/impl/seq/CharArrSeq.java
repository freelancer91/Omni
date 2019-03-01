package omni.impl.seq;
import omni.api.OmniCollection;
import omni.util.OmniArray;
import omni.api.OmniList;
import omni.api.OmniStack;
import java.util.function.Predicate;
import java.util.function.Consumer;
import omni.function.CharPredicate;
import omni.function.CharConsumer;
import java.util.Comparator;
import omni.function.CharComparator;
import omni.util.ToStringUtil;
import omni.util.ArrCopy;
import omni.util.CharSortUtil;
import omni.impl.CheckedCollection;
import omni.util.TypeUtil;
import java.util.NoSuchElementException;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import java.util.function.IntFunction;
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
  public static class UncheckedListImpl extends CharArrSeq implements OmniList.OfChar
  {
    static void uncheckedToString(char[] arr,int begin,int end,char[] buffer)
    {
      int bufferOffset;
      for(buffer[bufferOffset=1]=arr[begin];begin!=end;buffer[++bufferOffset]=',',buffer[++bufferOffset]=' ',buffer[++bufferOffset]=arr[++begin]){}
    }
    static  void uncheckedForEach(char[] arr,int begin,int end,CharConsumer action)
    {
      for(;;++begin)
      {
        action.accept((char)arr[begin]);
        if(begin==end)
        {
          return;
        }
      }
    }
    static int uncheckedHashCode(char[] arr,int begin,int end)
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
    private UncheckedListImpl(int size,char[] arr)
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
        uncheckedToString(arr,0,size-1,buffer=new char[size*=3]);
        buffer[size-1]=']';
        buffer[0]='[';
        return new String(buffer,0,size);
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
        final char[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new char[size],0,size);
        return new UncheckedListImpl(size,copy);
      }
      return new UncheckedListImpl();
    }
    @Override
    public void forEach(CharConsumer action)
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
    public void forEach(Consumer<? super Character> action)
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
    public boolean removeIf(CharPredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Character> filter)
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
       return OmniArray.OfChar.uncheckedcontains(this.arr,0,size-1,TypeUtil.castToChar(val));
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
       if(val==(char)val)
       {
         return OmniArray.OfChar.uncheckedcontains(this.arr,0,size-1,val);
       }
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
       final char v;
       if(val==(v=(char)val))
       {
         return OmniArray.OfChar.uncheckedcontains(this.arr,0,size-1,v);
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
       final char v;
       if(val==(v=(char)val))
       {
         return OmniArray.OfChar.uncheckedcontains(this.arr,0,size-1,v);
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
       final char v;
       if((v=(char)val)==val)
       {
         return OmniArray.OfChar.uncheckedcontains(this.arr,0,size-1,v);
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
       if(val instanceof Character)
       {
         return OmniArray.OfChar.uncheckedcontains(this.arr,0,size-1,(char)(val));
       }
     }
     return false;
   }
    @Override
    public boolean add(char val)
    {
      //TODO
      return false;
    }
    @Override
    public void add(int index,char val)
    {
      //TODO
    }
    @Override
    public char removeCharAt(int index)
    {
      final char[] arr;
      char ret=(char)(arr=this.arr)[index];
      ArrCopy.semicheckedSelfCopy(arr,index+1,index,(--size)-index);
      this.size=size;
      return ret;
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
    public OmniList.OfChar subList(int fromIndex,int toIndex)
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
          CharSortUtil.uncheckedDescendingSort(arr,0,size);
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
          CharSortUtil.uncheckedAscendingSort(arr,0,size);
        }
      }
    }
    @Override
    public OmniIterator.OfChar iterator()
    {
      //TODO
      return null;
    }
    @Override
    public char[] toCharArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new char[size],0,size);
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
        ArrCopy.uncheckedCopy(arr,0,copy=new Character[size],0,size);
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
  }
  public static class UncheckedStackImpl extends CharArrSeq implements OmniStack.OfChar
  {
    static void uncheckedToString(char[] arr,int begin,int end,char[] buffer)
    {
      int bufferOffset;
      for(buffer[bufferOffset=1]=arr[end];begin!=end;buffer[++bufferOffset]=',',buffer[++bufferOffset]=' ',buffer[++bufferOffset]=arr[--end]){}
    }
    static  void uncheckedForEach(char[] arr,int begin,int end,CharConsumer action)
    {
      for(;;--end)
      {
        action.accept((char)arr[end]);
        if(begin==end)
        {
          return;
        }
      }
    }
    static int uncheckedHashCode(char[] arr,int begin,int end)
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
    private UncheckedStackImpl(int size,char[] arr)
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
        uncheckedToString(arr,0,size-1,buffer=new char[size*=3]);
        buffer[size-1]=']';
        buffer[0]='[';
        return new String(buffer,0,size);
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
        final char[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new char[size],0,size);
        return new UncheckedStackImpl(size,copy);
      }
      return new UncheckedStackImpl();
    }
    @Override
    public void forEach(CharConsumer action)
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
    public void forEach(Consumer<? super Character> action)
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
    public boolean removeIf(CharPredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Character> filter)
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
       return OmniArray.OfChar.uncheckedcontains(this.arr,0,size-1,TypeUtil.castToChar(val));
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
       if(val==(char)val)
       {
         return OmniArray.OfChar.uncheckedcontains(this.arr,0,size-1,val);
       }
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
       final char v;
       if(val==(v=(char)val))
       {
         return OmniArray.OfChar.uncheckedcontains(this.arr,0,size-1,v);
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
       final char v;
       if(val==(v=(char)val))
       {
         return OmniArray.OfChar.uncheckedcontains(this.arr,0,size-1,v);
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
       final char v;
       if((v=(char)val)==val)
       {
         return OmniArray.OfChar.uncheckedcontains(this.arr,0,size-1,v);
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
       if(val instanceof Character)
       {
         return OmniArray.OfChar.uncheckedcontains(this.arr,0,size-1,(char)(val));
       }
     }
     return false;
   }
    @Override
    public OmniIterator.OfChar iterator()
    {
      //TODO
      return null;
    }
    @Override
    public char[] toCharArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char[] copy;
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new char[size],0,size);
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
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new Character[size],0,size);
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
    public char popChar()
    {
      int size;
      if((size=this.size)!=0)
      {
        char ret=(char)arr[--size];
        this.size=size;
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override
    public Character pop()
    {
      return popChar();
    }
    @Override
    public void push(char val)
    {
      //TODO
    }
    @Override
    public void push(Character val)
    {
      //TODO
    }
    @Override
    public char peekChar()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (char)((char)arr[size-1]);
      }
      return Character.MIN_VALUE;
    }
    @Override
    public char pollChar()
    {
      int size;
      if((size=this.size)!=0)
      {
        char ret=(char)((char)arr[--size]);
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
        Character ret=(Character)((char)arr[--size]);
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
        double ret=(double)((char)arr[--size]);
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
        float ret=(float)((char)arr[--size]);
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
        long ret=(long)((char)arr[--size]);
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
        int ret=(int)((char)arr[--size]);
        this.size=size;
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override
    public Character peek()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (Character)((char)arr[size-1]);
      }
      return null;
    }
    @Override
    public double peekDouble()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (double)((char)arr[size-1]);
      }
      return Double.NaN;
    }
    @Override
    public float peekFloat()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (float)((char)arr[size-1]);
      }
      return Float.NaN;
    }
    @Override
    public long peekLong()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (long)((char)arr[size-1]);
      }
      return Long.MIN_VALUE;
    }
    @Override
    public int peekInt()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (int)((char)arr[size-1]);
      }
      return Integer.MIN_VALUE;
    }
  }
  public static class CheckedListImpl extends CharArrSeq implements OmniList.OfChar
  {
    static void uncheckedToString(char[] arr,int begin,int end,char[] buffer)
    {
      int bufferOffset;
      for(buffer[bufferOffset=1]=arr[begin];begin!=end;buffer[++bufferOffset]=',',buffer[++bufferOffset]=' ',buffer[++bufferOffset]=arr[++begin]){}
    }
    static  void uncheckedForEach(char[] arr,int begin,int end,CharConsumer action)
    {
      for(;;++begin)
      {
        action.accept((char)arr[begin]);
        if(begin==end)
        {
          return;
        }
      }
    }
    static int uncheckedHashCode(char[] arr,int begin,int end)
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
    private CheckedListImpl(int size,char[] arr)
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
        uncheckedToString(arr,0,size-1,buffer=new char[size*=3]);
        buffer[size-1]=']';
        buffer[0]='[';
        return new String(buffer,0,size);
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
        final char[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new char[size],0,size);
        return new CheckedListImpl(size,copy);
      }
      return new CheckedListImpl();
    }
    @Override
    public void forEach(CharConsumer action)
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
    public void forEach(Consumer<? super Character> action)
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
    public boolean removeIf(CharPredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Character> filter)
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
       return OmniArray.OfChar.uncheckedcontains(this.arr,0,size-1,TypeUtil.castToChar(val));
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
       if(val==(char)val)
       {
         return OmniArray.OfChar.uncheckedcontains(this.arr,0,size-1,val);
       }
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
       final char v;
       if(val==(v=(char)val))
       {
         return OmniArray.OfChar.uncheckedcontains(this.arr,0,size-1,v);
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
       final char v;
       if(val==(v=(char)val))
       {
         return OmniArray.OfChar.uncheckedcontains(this.arr,0,size-1,v);
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
       final char v;
       if((v=(char)val)==val)
       {
         return OmniArray.OfChar.uncheckedcontains(this.arr,0,size-1,v);
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
       if(val instanceof Character)
       {
         return OmniArray.OfChar.uncheckedcontains(this.arr,0,size-1,(char)(val));
       }
     }
     CheckedCollection.checkModCount(modCount,this.modCount);
     return false;
   }
    @Override
    public boolean add(char val)
    {
      //TODO
      return false;
    }
    @Override
    public void add(int index,char val)
    {
      //TODO
    }
    @Override
    public char removeCharAt(int index)
    {
      int size;
      if(index<0 || index>=(size=this.size))
      {
        throw new IndexOutOfBoundsException("index = "+index+"; size="+this.size);
      }
      ++this.modCount;
      final char[] arr;
      char ret=(char)(arr=this.arr)[index];
      ArrCopy.semicheckedSelfCopy(arr,index+1,index,(--size)-index);
      this.size=size;
      return ret;
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
    public OmniList.OfChar subList(int fromIndex,int toIndex)
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
          CharSortUtil.uncheckedDescendingSort(arr,0,size);
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
          CharSortUtil.uncheckedAscendingSort(arr,0,size);
        }
      }
    }
    @Override
    public OmniIterator.OfChar iterator()
    {
      //TODO
      return null;
    }
    @Override
    public char[] toCharArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new char[size],0,size);
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
        ArrCopy.uncheckedCopy(arr,0,copy=new Character[size],0,size);
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
  }
  public static class CheckedStackImpl extends CharArrSeq implements OmniStack.OfChar
  {
    static void uncheckedToString(char[] arr,int begin,int end,char[] buffer)
    {
      int bufferOffset;
      for(buffer[bufferOffset=1]=arr[end];begin!=end;buffer[++bufferOffset]=',',buffer[++bufferOffset]=' ',buffer[++bufferOffset]=arr[--end]){}
    }
    static  void uncheckedForEach(char[] arr,int begin,int end,CharConsumer action)
    {
      for(;;--end)
      {
        action.accept((char)arr[end]);
        if(begin==end)
        {
          return;
        }
      }
    }
    static int uncheckedHashCode(char[] arr,int begin,int end)
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
    private CheckedStackImpl(int size,char[] arr)
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
        uncheckedToString(arr,0,size-1,buffer=new char[size*=3]);
        buffer[size-1]=']';
        buffer[0]='[';
        return new String(buffer,0,size);
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
        final char[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new char[size],0,size);
        return new CheckedStackImpl(size,copy);
      }
      return new CheckedStackImpl();
    }
    @Override
    public void forEach(CharConsumer action)
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
    public void forEach(Consumer<? super Character> action)
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
    public boolean removeIf(CharPredicate filter)
    {
      //TODO
      return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Character> filter)
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
       return OmniArray.OfChar.uncheckedcontains(this.arr,0,size-1,TypeUtil.castToChar(val));
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
       if(val==(char)val)
       {
         return OmniArray.OfChar.uncheckedcontains(this.arr,0,size-1,val);
       }
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
       final char v;
       if(val==(v=(char)val))
       {
         return OmniArray.OfChar.uncheckedcontains(this.arr,0,size-1,v);
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
       final char v;
       if(val==(v=(char)val))
       {
         return OmniArray.OfChar.uncheckedcontains(this.arr,0,size-1,v);
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
       final char v;
       if((v=(char)val)==val)
       {
         return OmniArray.OfChar.uncheckedcontains(this.arr,0,size-1,v);
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
       if(val instanceof Character)
       {
         return OmniArray.OfChar.uncheckedcontains(this.arr,0,size-1,(char)(val));
       }
     }
     CheckedCollection.checkModCount(modCount,this.modCount);
     return false;
   }
    @Override
    public OmniIterator.OfChar iterator()
    {
      //TODO
      return null;
    }
    @Override
    public char[] toCharArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char[] copy;
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new char[size],0,size);
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
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new Character[size],0,size);
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
    public char popChar()
    {
      int size;
      if((size=this.size)!=0)
      {
        char ret=(char)arr[--size];
        ++this.modCount;
        this.size=size;
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override
    public Character pop()
    {
      return popChar();
    }
    @Override
    public void push(char val)
    {
      //TODO
    }
    @Override
    public void push(Character val)
    {
      //TODO
    }
    @Override
    public char peekChar()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (char)((char)arr[size-1]);
      }
      return Character.MIN_VALUE;
    }
    @Override
    public char pollChar()
    {
      int size;
      if((size=this.size)!=0)
      {
        char ret=(char)((char)arr[--size]);
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
        Character ret=(Character)((char)arr[--size]);
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
        double ret=(double)((char)arr[--size]);
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
        float ret=(float)((char)arr[--size]);
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
        long ret=(long)((char)arr[--size]);
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
        int ret=(int)((char)arr[--size]);
        ++this.modCount;
        this.size=size;
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override
    public Character peek()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (Character)((char)arr[size-1]);
      }
      return null;
    }
    @Override
    public double peekDouble()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (double)((char)arr[size-1]);
      }
      return Double.NaN;
    }
    @Override
    public float peekFloat()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (float)((char)arr[size-1]);
      }
      return Float.NaN;
    }
    @Override
    public long peekLong()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (long)((char)arr[size-1]);
      }
      return Long.MIN_VALUE;
    }
    @Override
    public int peekInt()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (int)((char)arr[size-1]);
      }
      return Integer.MIN_VALUE;
    }
  }
}
