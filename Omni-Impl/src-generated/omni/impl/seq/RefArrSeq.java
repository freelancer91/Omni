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
import omni.util.RefSortUtil;
import omni.impl.seq.AbstractSeq;
import omni.util.BitSetUtil;
import java.util.Objects;
import omni.util.OmniPred;
public abstract class RefArrSeq<E> extends AbstractSeq implements OmniCollection.OfRef<E>
{
  private static void eraseIndexHelper(Object[] arr,int index,int newSize)
  {
    ArrCopy.semicheckedSelfCopy(arr,index+1,index,newSize-index);
    arr[newSize]=null;
  }
  @SuppressWarnings("unchecked")
  static <E> void uncheckedReplaceAll(Object[] arr,int offset,int bound,UnaryOperator<E> operator)
  {
    do
    {
      arr[offset]=operator.apply((E)arr[offset]);
    }
    while(++offset!=bound);
  }
  @SuppressWarnings("unchecked")
  static <E> void uncheckedAscendingForEach(Object[] arr,int begin,int end,Consumer<? super E> action)
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
  @SuppressWarnings("unchecked")
  static <E> void uncheckedDescendingForEach(Object[] arr,int begin,int end,Consumer<? super E> action)
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
  static int ascendingHashCode(Object[] arr,int begin,int end)
  {
    int hash=31+Objects.hashCode(arr[begin]);
    while(begin!=end)
    {
      hash=hash*31+Objects.hashCode(arr[++begin]);
    }
    return hash;
  }
  static int descendingHashCode(Object[] arr,int begin,int end)
  {
    int hash=31+Objects.hashCode(arr[end]);
    while(begin!=end)
    {
      hash=hash*31+Objects.hashCode(arr[--end]);
    }
    return hash;
  }
  static void ascendingToString(Object[] arr,int begin,int end,StringBuilder builder)
  {
    for(builder.append(arr[begin]);begin!=end;builder.append(',').append(' ').append(arr[++begin])){}
  }
  static void descendingToString(Object[] arr,int begin,int end,StringBuilder builder)
  {
    for(builder.append(arr[end]);begin!=end;builder.append(',').append(' ').append(arr[--end])){}
  }
    static boolean uncheckedcontainsNonNull(Object[] arr,int begin,int end
    ,Object val
    )
    {
      while(
      !val.equals(arr[begin])
      )
      {
        if(begin==end)
        {
          return false;
        }
        ++begin;
      }
      return true;
    }
    static int uncheckedsearchNonNull(Object[] arr,int end
    ,Object val
    )
    {
      int index=end;
      while(
      !val.equals(arr[index])
      )
      {
        if(index==0)
        {
          return -1;
        }
        --index;
      }
      return index-end+1;
    }
    static int uncheckedindexOfNonNull(Object[] arr,int bound
    ,Object val
    )
    {
      int index=0;
      while(
      !val.equals(arr[index])
      )
      {
        if(++index==bound)
        {
          return -1;
        }
      }
      return index;
    }
    static int uncheckedlastIndexOfNonNull(Object[] arr,int bound
    ,Object val
    )
    {
      while(
      !val.equals(arr[--bound])
      )
      {
        if(bound==0)
        {
          return -1;
        }
      }
      return bound;
    }
    static int uncheckedindexOfNonNull(Object[] arr,int offset,int bound
    ,Object val
    )
    {
      int index=offset;
      while(
      !val.equals(arr[index])
      )
      {
        if(++index==bound)
        {
          return -1;
        }
      }
      return index-offset;
    }
    static int uncheckedlastIndexOfNonNull(Object[] arr,int offset,int bound
    ,Object val
    )
    {
      while(
      !val.equals(arr[--bound])
      )
      {
        if(bound==offset)
        {
          return -1;
        }
      }
      return bound-offset;
    }
    static boolean uncheckedcontainsNull(Object[] arr,int begin,int end
    )
    {
      while(
      (arr[begin])!=null
      )
      {
        if(begin==end)
        {
          return false;
        }
        ++begin;
      }
      return true;
    }
    static int uncheckedsearchNull(Object[] arr,int end
    )
    {
      int index=end;
      while(
      (arr[index])!=null
      )
      {
        if(index==0)
        {
          return -1;
        }
        --index;
      }
      return index-end+1;
    }
    static int uncheckedindexOfNull(Object[] arr,int bound
    )
    {
      int index=0;
      while(
      (arr[index])!=null
      )
      {
        if(++index==bound)
        {
          return -1;
        }
      }
      return index;
    }
    static int uncheckedlastIndexOfNull(Object[] arr,int bound
    )
    {
      while(
      (arr[--bound])!=null
      )
      {
        if(bound==0)
        {
          return -1;
        }
      }
      return bound;
    }
    static int uncheckedindexOfNull(Object[] arr,int offset,int bound
    )
    {
      int index=offset;
      while(
      (arr[index])!=null
      )
      {
        if(++index==bound)
        {
          return -1;
        }
      }
      return index-offset;
    }
    static int uncheckedlastIndexOfNull(Object[] arr,int offset,int bound
    )
    {
      while(
      (arr[--bound])!=null
      )
      {
        if(bound==offset)
        {
          return -1;
        }
      }
      return bound-offset;
    }
  static boolean uncheckedcontains(Object[] arr,int begin,int end,Object val)
  {
    if(val!=null)
    {
      return uncheckedcontainsNonNull(arr,begin,end,val);
    }
    return uncheckedcontainsNull(arr,begin,end);
  }
  static int uncheckedsearch(Object[] arr,int end,Object val)
  {
    if(val!=null)
    {
      return uncheckedsearchNonNull(arr,end,val);
    }
    return uncheckedsearchNull(arr,end);
  }
  static int uncheckedindexOf(Object[] arr,int bound,Object val)
  {
    if(val!=null)
    {
      return uncheckedindexOfNonNull(arr,bound,val);
    }
    return uncheckedindexOfNull(arr,bound);
  }
  static int uncheckedlastIndexOf(Object[] arr,int bound,Object val)
  {
    if(val!=null)
    {
      return uncheckedlastIndexOfNonNull(arr,bound,val);
    }
    return uncheckedlastIndexOfNull(arr,bound);
  }
  static int uncheckedindexOf(Object[] arr,int offset,int bound,Object val)
  {
    if(val!=null)
    {
      return uncheckedindexOfNonNull(arr,offset,bound,val);
    }
    return uncheckedindexOfNull(arr,offset,bound);
  }
  static int uncheckedlastIndexOf(Object[] arr,int offset,int bound,Object val)
  {
    if(val!=null)
    {
      return uncheckedlastIndexOfNonNull(arr,offset,bound,val);
    }
    return uncheckedlastIndexOfNull(arr,offset,bound);
  }
  //TODO mark/pull survivors up/down
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
  abstract int uncheckedHashCode(int size);
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
  abstract void uncheckedToString(int size,StringBuilder builder);
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
      uncheckedToString(size,builder=new StringBuilder("["));
      return builder.append(']').toString();
    }
    return "[]";
  }
  abstract void uncheckedForEach(final int size,final Consumer<? super E> action);
  //TODO forEach methods
  //TODO removeIf methods
  //TODO peek methods
  //TODO uncheckedCopyInto methods
  //TODO contains methods
  //TODO indexOf methods
  //TODO lastIndexOf methods
  //TODO search methods
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
      return this.uncheckedremoveValNull(size);
    }
    return false;
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
  abstract boolean uncheckedremoveValNonNull(final int size,final Object nonNull);
  abstract boolean uncheckedremoveValNull(final int size);
  abstract boolean uncheckedRemoveIf(final int size,final Predicate<? super E> filter);
  private int finalizeSubListBatchRemove(final Object[] arr,final int newBound,final int oldBound)
  {
    final int newRootSize,numRemoved;
    final int rootSize;
    size=newRootSize=(rootSize=size)-(numRemoved=oldBound-newBound);
    ArrCopy.semicheckedSelfCopy(arr,oldBound,newBound,newRootSize-newBound);
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
    this.size=1;
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
  //TODO toArray methods
  //TODO removeVal methods
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
    //TODO uncheckedRemoveIf
  }
  public static class UncheckedList<E> extends Unchecked<E>
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
  }
  //TODO UncheckedStack
  //TODO Checked
  //TODO CheckedList
  //TODO CheckedStack
}
