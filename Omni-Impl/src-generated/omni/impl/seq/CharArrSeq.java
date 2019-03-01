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
import omni.util.CharSortUtil;
import omni.util.ToStringUtil;
import omni.impl.seq.AbstractCharList;
import omni.impl.AbstractCharItr;
import omni.util.BitSetUtil;
import omni.util.TypeUtil;
import omni.function.CharComparator;
import omni.function.CharUnaryOperator;
import omni.function.CharConsumer;
import omni.function.CharPredicate;
public abstract class CharArrSeq extends AbstractCharList implements OmniCollection.OfChar
{
  private static void eraseIndexHelper(char[] arr,int index,int newSize)
  {
    ArrCopy.semicheckedSelfCopy(arr,index+1,index,newSize-index);
  }
  static  void uncheckedReplaceAll(char[] arr,int offset,int bound,CharUnaryOperator operator)
  {
    do
    {
      arr[offset]=operator.applyAsChar((char)arr[offset]);
    }
    while(++offset!=bound);
  }
  static  void uncheckedAscendingForEach(char[] arr,int begin,int end,CharConsumer action)
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
  static  void uncheckedDescendingForEach(char[] arr,int begin,int end,CharConsumer action)
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
  static int ascendingHashCode(char[] arr,int begin,int end)
  {
    int hash=31+(arr[begin]);
    while(begin!=end)
    {
      hash=hash*31+(arr[++begin]);
    }
    return hash;
  }
  static int descendingHashCode(char[] arr,int begin,int end)
  {
    int hash=31+(arr[end]);
    while(begin!=end)
    {
      hash=hash*31+(arr[--end]);
    }
    return hash;
  }
  static int ascendingToString(char[] arr,int begin,int end,char[] buffer,int bufferOffset)
  {
    for(buffer[bufferOffset++]=arr[begin];begin!=end;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',buffer[bufferOffset++]=arr[++begin]){}
    return bufferOffset;
  }
  static int descendingToString(char[] arr,int begin,int end,char[] buffer,int bufferOffset)
  {
    for(buffer[bufferOffset++]=arr[end];begin!=end;buffer[bufferOffset]=',',buffer[++bufferOffset]=' ',buffer[bufferOffset++]=arr[--end]){}
    return bufferOffset;
  }
    static boolean uncheckedcontains (char[] arr,int begin,int end
    ,int val
    )
    {
      while(
      val!=(arr[begin])
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
    static int uncheckedsearch (char[] arr,int end
    ,int val
    )
    {
      int index=end;
      while(
      val!=(arr[index])
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
    static int uncheckedindexOf (char[] arr,int bound
    ,int val
    )
    {
      int index=0;
      while(
      val!=(arr[index])
      )
      {
        if(++index==bound)
        {
          return -1;
        }
      }
      return index;
    }
    static int uncheckedlastIndexOf (char[] arr,int bound
    ,int val
    )
    {
      while(
      val!=(arr[--bound])
      )
      {
        if(bound==0)
        {
          return -1;
        }
      }
      return bound;
    }
    static int uncheckedindexOf (char[] arr,int offset,int bound
    ,int val
    )
    {
      int index=offset;
      while(
      val!=(arr[index])
      )
      {
        if(++index==bound)
        {
          return -1;
        }
      }
      return index-offset;
    }
    static int uncheckedlastIndexOf (char[] arr,int offset,int bound
    ,int val
    )
    {
      while(
      val!=(arr[--bound])
      )
      {
        if(bound==offset)
        {
          return -1;
        }
      }
      return bound-offset;
    }
  //TODO mark/pull survivors up/down
  transient char[] arr;
  private CharArrSeq()
  {
    super();
    this.arr=OmniArray.OfChar.DEFAULT_ARR;
  }
  private CharArrSeq(final int capacity)
  {
    super();
    switch(capacity)
    {
    default:
      this.arr=new char[capacity];
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfChar.DEFAULT_ARR;
    case 0:
    }
  }
  private CharArrSeq(final int size,final char[] arr)
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
  abstract void uncheckedToString(int size,char[] buffer);
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
      buffer[0]='[';
      buffer[size-1]=']';
      return new String(buffer,0,size);
    }
    return "[]";
  }
  abstract void uncheckedForEach(final int size,final CharConsumer action);
  //TODO forEach methods
  //TODO removeIf methods
  //TODO peek methods
  //TODO uncheckedCopyInto methods
  //TODO contains methods
  //TODO indexOf methods
  //TODO lastIndexOf methods
  //TODO search methods
   public Character pop()
   {
     return popChar();
   }
   public void push(final Character val)
   {
     push((char)val);
   }
  public char popChar()
  {
    return uncheckedPop(size-1);
  }
  public void push(final char val)
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
      if(val instanceof Character)
      {
        return this.uncheckedRemoveVal(size,(char)(val));
      }
    }
    return false;
  }
  @Override
  public char set(final int index,final char val)
  {
    final char[] arr;
    final var oldVal=(char)(arr=this.arr)[index];
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
  abstract boolean uncheckedRemoveVal(final int size,final int val);
  abstract boolean uncheckedRemoveIf(final int size,final CharPredicate filter);
  private int finalizeSubListBatchRemove(final char[] arr,final int newBound,final int oldBound)
  {
    final int newRootSize,numRemoved;
    size=newRootSize=size-(numRemoved=oldBound-newBound);
    ArrCopy.semicheckedSelfCopy(arr,oldBound,newBound,newRootSize-newBound);
    return numRemoved;
  }
  private char[] growInsert(char[] arr,final int index,final int size)
  {
    if(arr.length==size)
    {
      ArrCopy.semicheckedCopy(arr,0,arr=new char[OmniArray.growBy50Pct(size)],0,index);
      this.arr=arr;
    }
    return arr;
  }
  private void uncheckedAppend(final char val,final int size)
  {
    char[] arr;
    if((arr=this.arr).length==size)
    {
      ArrCopy.uncheckedCopy(arr,0,arr=new char[OmniArray.growBy50Pct(size)],0,size);
    }
    arr[size]=val;
    this.size=size+1;
  }
  private void uncheckedInit(final char val)
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
  private void uncheckedInsert(final int index,final char val,final int size)
  {
    final int tailDist;
    if((tailDist=size-index)==0)
    {
      uncheckedAppend(val,size);
    }
    else
    {
      char[] arr;
      ArrCopy.uncheckedCopy(arr=this.arr,index,arr=growInsert(arr,index,size),index+1,tailDist);
      arr[index]=val;
      this.size=size+1;
    }
  }
  private char uncheckedPop(final int newSize)
  {
    this.size=newSize;
    return arr[newSize];
  }
  //TODO toArray methods
  //TODO removeVal methods
  public static abstract class Unchecked extends CharArrSeq
  {
    private Unchecked()
    {
      super();
    }
    private Unchecked(final int capacity)
    {
      super(capacity);
    }
    private Unchecked(final int size,final char[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean add(final char val)
    {
      super.push(val);
      return true;
    }
    @Override
    public void add(final int index,final char val)
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
    public char getChar(final int index)
    {
      return (char)arr[index];
    }
    @Override
    public char removeCharAt(final int index)
    {
      final char[] arr;
      final var removed=(char)(arr=this.arr)[index];
      eraseIndexHelper(arr,index,--size);
      return removed;
    }
    //TODO uncheckedRemoveIf
  }
  public static class UncheckedList extends Unchecked
  {
    public UncheckedList()
    {
      super();
    }
    public UncheckedList(final int capacity)
    {
      super(capacity);
    }
    public UncheckedList(final int size,final char[] arr)
    {
      super(size,arr);
    }
    @Override
    public Object clone()
    {
      final char[] arr;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,arr=new char[size],0,size);
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
  }
  //TODO UncheckedStack
  //TODO Checked
  //TODO CheckedList
  //TODO CheckedStack
}
