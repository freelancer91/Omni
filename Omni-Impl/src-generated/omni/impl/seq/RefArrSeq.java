package omni.impl.seq;
import omni.api.OmniCollection;
import omni.util.OmniArray;
import omni.api.OmniList;
import omni.api.OmniStack;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.Comparator;
import omni.util.ArrCopy;
import omni.util.RefSortUtil;
import java.util.Objects;
import omni.impl.CheckedCollection;
import java.util.NoSuchElementException;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import java.util.function.IntFunction;
import omni.util.OmniPred;
public abstract class RefArrSeq<E> implements OmniCollection.OfRef<E>
{
  transient int size;
  transient Object[] arr;
  private RefArrSeq()
  {
    super();
  }
  private RefArrSeq(int initialCapacity)
  {
    super();
    switch(initialCapacity)
    { 
    default:
      this.arr=new Object[initialCapacity];
      return;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfRef.DEFAULT_ARR;
    case 0:
    }
  }
  private RefArrSeq(int size,Object[] arr)
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
    int size;
    if((size=this.size)!=0)
    {
      this.size=0;
      OmniArray.OfRef.nullifyRange(arr,0,size-1);
    }
  }
  public static class UncheckedListImpl<E> extends RefArrSeq<E> implements OmniList.OfRef<E>
  {
    static void uncheckedToString(Object[] arr,int begin,int end,StringBuilder builder)
    {
      for(builder.append(arr[begin]);begin!=end;builder.append(',').append(' ').append(arr[++begin])){}
    }
    @SuppressWarnings("unchecked")
    static <E> void uncheckedForEach(Object[] arr,int begin,int end,Consumer<? super E> action)
    {
      for(;;++begin)
      {
        action.accept((E)arr[begin]);
        if(begin==end)
        {
          return;
        }
      }
    }
    static int uncheckedHashCode(Object[] arr,int begin,int end)
    {
      int hash=31+Objects.hashCode(arr[begin]);
      while(begin!=end)
      {
        hash=hash*31+Objects.hashCode(arr[++begin]);
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
    private UncheckedListImpl(int size,Object[] arr)
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
        final StringBuilder builder;
        {
          uncheckedToString(arr,0,size-1,builder=new StringBuilder("["));
        }
        return builder.append(']').toString();
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
        final Object[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new Object[size],0,size);
        return new UncheckedListImpl<E>(size,copy);
      }
      return new UncheckedListImpl<E>();
    }
    @SuppressWarnings("unchecked")
    @Override
    public void forEach(Consumer<? super E> action)
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
    public boolean removeIf(Predicate<? super E> filter)
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
       return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
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
       return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
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
       return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
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
       return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
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
       return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
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
       {
         return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
       }
     }
     return false;
   }
    @Override
    public boolean add(E val)
    {
      //TODO
      return false;
    }
    @Override
    public void add(int index,E val)
    {
      //TODO
    }
    @Override
    public E remove(int index)
    {
      final Object[] arr;
      @SuppressWarnings("unchecked")
      E ret=(E)(arr=this.arr)[index];
      ArrCopy.semicheckedSelfCopy(arr,index+1,index,(--size)-index);
      arr[size]=null;
      this.size=size;
      return ret;
    }
    @Override
    public OmniListIterator.OfRef<E> listIterator()
    {
      //TODO
      return null;
    }
    @Override
    public OmniListIterator.OfRef<E> listIterator(int index)
    {
      //TODO
      return null;
    }
    @Override
    public OmniList.OfRef<E> subList(int fromIndex,int toIndex)
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
          RefSortUtil.uncheckedStableDescendingSort(arr,0,size);
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
          RefSortUtil.uncheckedStableAscendingSort(arr,0,size);
        }
      }
    }
    @Override
    public OmniIterator.OfRef<E> iterator()
    {
      //TODO
      return null;
    }
    @Override
    public Object[] toArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final Object[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new Object[size],0,size);
        return copy;
      }
      return OmniArray.OfRef.DEFAULT_ARR;
    }
  }
  public static class UncheckedStackImpl<E> extends RefArrSeq<E> implements OmniStack.OfRef<E>
  {
    static void uncheckedToString(Object[] arr,int begin,int end,StringBuilder builder)
    {
      for(builder.append(arr[end]);begin!=end;builder.append(',').append(' ').append(arr[--end])){}
    }
    @SuppressWarnings("unchecked")
    static <E> void uncheckedForEach(Object[] arr,int begin,int end,Consumer<? super E> action)
    {
      for(;;--end)
      {
        action.accept((E)arr[end]);
        if(begin==end)
        {
          return;
        }
      }
    }
    static int uncheckedHashCode(Object[] arr,int begin,int end)
    {
      int hash=31+Objects.hashCode(arr[end]);
      while(begin!=end)
      {
        hash=hash*31+Objects.hashCode(arr[--end]);
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
    private UncheckedStackImpl(int size,Object[] arr)
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
        final StringBuilder builder;
        {
          uncheckedToString(arr,0,size-1,builder=new StringBuilder("["));
        }
        return builder.append(']').toString();
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
        final Object[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new Object[size],0,size);
        return new UncheckedStackImpl<E>(size,copy);
      }
      return new UncheckedStackImpl<E>();
    }
    @SuppressWarnings("unchecked")
    @Override
    public void forEach(Consumer<? super E> action)
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
    public boolean removeIf(Predicate<? super E> filter)
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
       return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
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
       return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
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
       return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
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
       return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
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
       return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
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
       {
         return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
       }
     }
     return false;
   }
    @Override
    public OmniIterator.OfRef<E> iterator()
    {
      //TODO
      return null;
    }
    @Override
    public Object[] toArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final Object[] copy;
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new Object[size],0,size);
        return copy;
      }
      return OmniArray.OfRef.DEFAULT_ARR;
    }
    @Override
    public E pop()
    {
      int size;
      if((size=this.size)!=0)
      {
        @SuppressWarnings("unchecked")
        E ret=(E)arr[--size];
        arr[size]=null;
        this.size=size;
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override
    public void push(E val)
    {
      //TODO
    }
    @SuppressWarnings("unchecked")
    @Override
    public E peek()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (E)((E)arr[size-1]);
      }
      return null;
    }
    @Override
    public E poll()
    {
      int size;
      if((size=this.size)!=0)
      {
        E ret=(E)((E)arr[--size]);
        arr[size]=null;
        this.size=size;
        return ret;
      }
      return null;
    }
  }
  public static class CheckedListImpl<E> extends RefArrSeq<E> implements OmniList.OfRef<E>
  {
    static void uncheckedToString(Object[] arr,int begin,int end,StringBuilder builder)
    {
      for(builder.append(arr[begin]);begin!=end;builder.append(',').append(' ').append(arr[++begin])){}
    }
    @SuppressWarnings("unchecked")
    static <E> void uncheckedForEach(Object[] arr,int begin,int end,Consumer<? super E> action)
    {
      for(;;++begin)
      {
        action.accept((E)arr[begin]);
        if(begin==end)
        {
          return;
        }
      }
    }
    static int uncheckedHashCode(Object[] arr,int begin,int end)
    {
      int hash=31+Objects.hashCode(arr[begin]);
      while(begin!=end)
      {
        hash=hash*31+Objects.hashCode(arr[++begin]);
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
    private CheckedListImpl(int size,Object[] arr)
    {
      super(size,arr);
    }
    @Override
    public void clear()
    {
      final int size;
      if((size=this.size)!=0)
      {
        ++this.modCount;
        this.size=0;
        OmniArray.OfRef.nullifyRange(arr,0,size-1);
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
        final StringBuilder builder;
        int modCount=this.modCount;
        try
        {
          uncheckedToString(arr,0,size-1,builder=new StringBuilder("["));
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        return builder.append(']').toString();
      }
      return "[]";
    }
    @Override
    public int hashCode()
    {
      final int size;
      if((size=this.size)!=0)
      {
        int modCount=this.modCount;
        try
        {
          return uncheckedHashCode(arr,0,size-1);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
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
        final Object[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new Object[size],0,size);
        return new CheckedListImpl<E>(size,copy);
      }
      return new CheckedListImpl<E>();
    }
    @SuppressWarnings("unchecked")
    @Override
    public void forEach(Consumer<? super E> action)
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
    public boolean removeIf(Predicate<? super E> filter)
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
       return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
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
       return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
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
       return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
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
       return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
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
       return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
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
       {
         return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
       }
     }
     CheckedCollection.checkModCount(modCount,this.modCount);
     return false;
   }
    @Override
    public boolean add(E val)
    {
      //TODO
      return false;
    }
    @Override
    public void add(int index,E val)
    {
      //TODO
    }
    @Override
    public E remove(int index)
    {
      int size;
      if(index<0 || index>=(size=this.size))
      {
        throw new IndexOutOfBoundsException("index = "+index+"; size="+this.size);
      }
      ++this.modCount;
      final Object[] arr;
      @SuppressWarnings("unchecked")
      E ret=(E)(arr=this.arr)[index];
      ArrCopy.semicheckedSelfCopy(arr,index+1,index,(--size)-index);
      arr[size]=null;
      this.size=size;
      return ret;
    }
    @Override
    public OmniListIterator.OfRef<E> listIterator()
    {
      //TODO
      return null;
    }
    @Override
    public OmniListIterator.OfRef<E> listIterator(int index)
    {
      //TODO
      return null;
    }
    @Override
    public OmniList.OfRef<E> subList(int fromIndex,int toIndex)
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
        final int modCount=this.modCount;
        try
        {
          RefSortUtil.uncheckedStableDescendingSort(arr,0,size);
        }
        //TODO do we need to catch exceptions in the checked version?
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
      final int size;
      if((size=this.size)>1)
      {
        final int modCount=this.modCount;
        try
        {
          RefSortUtil.uncheckedStableAscendingSort(arr,0,size);
        }
        //TODO do we need to catch exceptions in the checked version?
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override
    public OmniIterator.OfRef<E> iterator()
    {
      //TODO
      return null;
    }
    @Override
    public Object[] toArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final Object[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new Object[size],0,size);
        return copy;
      }
      return OmniArray.OfRef.DEFAULT_ARR;
    }
  }
  public static class CheckedStackImpl<E> extends RefArrSeq<E> implements OmniStack.OfRef<E>
  {
    static void uncheckedToString(Object[] arr,int begin,int end,StringBuilder builder)
    {
      for(builder.append(arr[end]);begin!=end;builder.append(',').append(' ').append(arr[--end])){}
    }
    @SuppressWarnings("unchecked")
    static <E> void uncheckedForEach(Object[] arr,int begin,int end,Consumer<? super E> action)
    {
      for(;;--end)
      {
        action.accept((E)arr[end]);
        if(begin==end)
        {
          return;
        }
      }
    }
    static int uncheckedHashCode(Object[] arr,int begin,int end)
    {
      int hash=31+Objects.hashCode(arr[end]);
      while(begin!=end)
      {
        hash=hash*31+Objects.hashCode(arr[--end]);
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
    private CheckedStackImpl(int size,Object[] arr)
    {
      super(size,arr);
    }
    @Override
    public void clear()
    {
      final int size;
      if((size=this.size)!=0)
      {
        ++this.modCount;
        this.size=0;
        OmniArray.OfRef.nullifyRange(arr,0,size-1);
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
        final StringBuilder builder;
        int modCount=this.modCount;
        try
        {
          uncheckedToString(arr,0,size-1,builder=new StringBuilder("["));
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        return builder.append(']').toString();
      }
      return "[]";
    }
    @Override
    public int hashCode()
    {
      final int size;
      if((size=this.size)!=0)
      {
        int modCount=this.modCount;
        try
        {
          return uncheckedHashCode(arr,0,size-1);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
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
        final Object[] copy;
        ArrCopy.uncheckedCopy(arr,0,copy=new Object[size],0,size);
        return new CheckedStackImpl<E>(size,copy);
      }
      return new CheckedStackImpl<E>();
    }
    @SuppressWarnings("unchecked")
    @Override
    public void forEach(Consumer<? super E> action)
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
    public boolean removeIf(Predicate<? super E> filter)
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
       return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
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
       return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
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
       return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
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
       return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
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
       return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
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
       {
         return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
       }
     }
     CheckedCollection.checkModCount(modCount,this.modCount);
     return false;
   }
    @Override
    public OmniIterator.OfRef<E> iterator()
    {
      //TODO
      return null;
    }
    @Override
    public Object[] toArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final Object[] copy;
        ArrCopy.uncheckedReverseCopy(arr,0,copy=new Object[size],0,size);
        return copy;
      }
      return OmniArray.OfRef.DEFAULT_ARR;
    }
    @Override
    public E pop()
    {
      int size;
      if((size=this.size)!=0)
      {
        @SuppressWarnings("unchecked")
        E ret=(E)arr[--size];
        ++this.modCount;
        arr[size]=null;
        this.size=size;
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override
    public void push(E val)
    {
      //TODO
    }
    @SuppressWarnings("unchecked")
    @Override
    public E peek()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (E)((E)arr[size-1]);
      }
      return null;
    }
    @Override
    public E poll()
    {
      int size;
      if((size=this.size)!=0)
      {
        E ret=(E)((E)arr[--size]);
        ++this.modCount;
        arr[size]=null;
        this.size=size;
        return ret;
      }
      return null;
    }
  }
}
