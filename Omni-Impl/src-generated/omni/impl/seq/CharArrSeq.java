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
import java.util.ConcurrentModificationException;
import omni.function.CharUnaryOperator;
import omni.function.CharComparator;
import omni.function.CharPredicate;
import omni.function.CharConsumer;
import omni.util.BitSetUtil;
import omni.impl.AbstractCharItr;
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
  abstract int uncheckedHashCode(int size);
  abstract void uncheckedToString(int size,char[] buffer);
  @Override
  public boolean contains(boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return OmniArray.OfChar.uncheckedcontains(this.arr,0,size-1,TypeUtil.castToChar(val));
    }
    return false;
  }
  @Override
  public boolean contains(int val)
  {
    if(val==(char)val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfChar.uncheckedcontains(this.arr,0,size-1,(val));
      }
    }
    return false;
  }
  @Override
  public boolean contains(long val)
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
  public boolean contains(float val)
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
  public boolean contains(double val)
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
  public boolean contains(Object val)
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
  public boolean contains(byte val)
  {
    if(val>=0)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfChar.uncheckedcontains(this.arr,0,size-1,(val));
      }
    }
    return false;
  }
  @Override
  public boolean contains(char val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfChar.uncheckedcontains(this.arr,0,size-1,(val));
      }
    }
    return false;
  }
  @Override
  public boolean contains(short val)
  {
    if(val>=0)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfChar.uncheckedcontains(this.arr,0,size-1,(val));
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,TypeUtil.castToChar(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(int val)
  {
    if(val==(char)val)
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
  public boolean removeVal(long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final char v;
      if((v=(char)val)==val)
      {
        return this.uncheckedremoveVal(size,v);
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final char v;
      if(val==(v=(char)val))
      {
        return this.uncheckedremoveVal(size,v);
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final char v;
      if(val==(v=(char)val))
      {
        return this.uncheckedremoveVal(size,v);
      }
    }
    return false;
  }
  @Override
  public boolean remove(Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Character)
      {
        return this.uncheckedremoveVal(size,(char)(val));
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(byte val)
  {
    if(val>=0)
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
  public boolean removeVal(char val)
  {
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
  public boolean removeVal(short val)
  {
    if(val>=0)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return this.uncheckedremoveVal(size,(val));
      }
    }
    return false;
  }
  abstract boolean uncheckedremoveVal(int size,int val);
  abstract void uncheckedForEach(int size,CharConsumer action);
  @Override
  public void forEach(CharConsumer action)
  {
    final int size;
    if((size=this.size)!=0)
    {
      uncheckedForEach(size,action);
    }
  }
  @Override
  public void forEach(Consumer<? super Character> action)
  {
    final int size;
    if((size=this.size)!=0)
    {
      uncheckedForEach(size,action::accept);
    }
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
  private void uncheckedInit(char val)
  {
    char[] arr;
    if((arr=this.arr)==null)
    {
      this.arr=new char[]{val};
    }
    else
    {
      if(arr==OmniArray.OfChar.DEFAULT_ARR)
      {
        this.arr=arr=new char[OmniArray.DEFAULT_ARR_SEQ_CAP];
      }
      arr[0]=val;
    }
    this.size=1;
  }
  private void uncheckedInsert(int index,int size,char val)
  {
    final int tailDist;
    if((tailDist=size-index)==0)
    {
      uncheckedAppend(size,val);
    }
    else
    {
      char[] arr;
      if((arr=this.arr).length==size)
      {
        final char[] tmp;
        ArrCopy.semicheckedCopy(arr,0,tmp=new char[OmniArray.growBy50Pct(size)],0,index);
        ArrCopy.uncheckedCopy(arr,index,tmp,index+1,tailDist);
        this.arr=arr=tmp;
      }
      else
      {
        ArrCopy.uncheckedCopy(arr,index,arr,index+1,tailDist);
      }
      arr[index]=val;
      this.size=size+1;
    }
  }
  public void push(char val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      uncheckedAppend(size,val);
    }
    else
    {
      uncheckedInit(val);
    }
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
  public boolean add(boolean val)
  {
    push((char)TypeUtil.castToChar(val));
    return true;
  }
  abstract void uncheckedCopyInto(char[] dst,int length);
  @Override
  public <T> T[] toArray(T[] arr)
  {
    final int size;
    if((size=this.size)!=0)
    {
      uncheckedCopyInto(arr=OmniArray.uncheckedArrResize(size,arr),size);
    }
    else if(arr.length!=0)
    {
      arr[0]=null;
    }
    return arr;
  }
  @Override
  public <T> T[] toArray(IntFunction<T[]> arrConstructor)
  {
    final int size;
    T[] dst=arrConstructor.apply(size=this.size);
    if(size!=0)
    {
      uncheckedCopyInto(dst,size);
    }
    return dst;
  }
  @Override
  public char[] toCharArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final char[] dst;
      uncheckedCopyInto(dst=new char[size],size);
      return dst;
    }
    return OmniArray.OfChar.DEFAULT_ARR;
  }
  abstract void uncheckedCopyInto(Object[] dst,int length);
  abstract void uncheckedCopyInto(Character[] dst,int length);
  @Override
  public Character[] toArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final Character[] dst;
      uncheckedCopyInto(dst=new Character[size],size);
      return dst;
    }
    return OmniArray.OfChar.DEFAULT_BOXED_ARR;
  }
  abstract void uncheckedCopyInto(double[] dst,int length);
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
  abstract void uncheckedCopyInto(float[] dst,int length);
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
  abstract void uncheckedCopyInto(long[] dst,int length);
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
  abstract void uncheckedCopyInto(int[] dst,int length);
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
  public
    static class UncheckedStack
      extends CharArrSeq
      implements OmniStack.OfChar,Cloneable
  {
    public UncheckedStack()
    {
      super();
    }
    public UncheckedStack(int initialCapacity)
    {
      super(initialCapacity);
    }
    private UncheckedStack(int size,char[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implements equals method for UncheckedStack
      return false;
    }
    @Override
    public Object clone()
    {
      final char[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new char[size],0,size);
      }
      else
      {
        copy=OmniArray.OfChar.DEFAULT_ARR;
      }
      return new UncheckedStack(size,copy);
    }
    void uncheckedToString(int size,char[] buffer)
    {
      OmniArray.OfChar.descendingToString(this.arr,0,size-1,buffer,1);
    }
    @Override
    int uncheckedHashCode(int size)
    {
      return OmniArray.OfChar.descendingSeqHashCode(this.arr,0,size-1);
    }
    @Override
    public int search(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfChar.uncheckedsearch(this.arr,size,TypeUtil.castToChar(val));
      }
      return -1;
    }
    @Override
    public int search(int val)
    {
      if(val==(char)val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfChar.uncheckedsearch(this.arr,size,(val));
        }
      }
      return -1;
    }
    @Override
    public int search(long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char v;
        if((v=(char)val)==val)
        {
          return OmniArray.OfChar.uncheckedsearch(this.arr,size,v);
        }
      }
      return -1;
    }
    @Override
    public int search(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char v;
        if(val==(v=(char)val))
        {
          return OmniArray.OfChar.uncheckedsearch(this.arr,size,v);
        }
      }
      return -1;
    }
    @Override
    public int search(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char v;
        if(val==(v=(char)val))
        {
          return OmniArray.OfChar.uncheckedsearch(this.arr,size,v);
        }
      }
      return -1;
    }
    @Override
    public int search(Object val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Character)
        {
          return OmniArray.OfChar.uncheckedsearch(this.arr,size,(char)(val));
        }
      }
      return -1;
    }
    @Override
    public int search(char val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfChar.uncheckedsearch(this.arr,size,(val));
        }
      }
      return -1;
    }
    @Override
    public int search(short val)
    {
      if(val>=0)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfChar.uncheckedsearch(this.arr,size,(val));
        }
      }
      return -1;
    }
    @Override
    boolean uncheckedremoveVal (int size
    ,int val
    )
    {
      final var arr=this.arr;
      for(int index=--size;;--index)
      {
        if(
        val==arr[index]
        )
        {
          OmniArray.OfChar.removeIndexAndPullDown(arr,index,size);
          this.size=size;
          return true;
        }
        if(index==0)
        {
          return false;
        }
      }
    }
    @Override
    void uncheckedForEach(int size,CharConsumer action)
    {
      {
        OmniArray.OfChar.descendingForEach(this.arr,0,size-1,action);
      }
    }
    private static class Itr
      extends AbstractCharItr
    {
      transient final UncheckedStack parent;
      transient int cursor;
      private Itr(UncheckedStack parent)
      {
        this.parent=parent;
        this.cursor=parent.size-1;
      }
      private Itr(UncheckedStack parent,int cursor)
      {
        this.parent=parent;
        this.cursor=cursor;
      }
      @Override
      public boolean hasNext()
      {
        return this.cursor>=0;
      }
      @Override
      public char nextChar()
      {
        return (char)parent.arr[cursor--];
      }
      @Override
      public void remove()
      {
        final UncheckedStack root;
        OmniArray.OfChar.removeIndexAndPullDown((root=this.parent).arr,this.cursor,--root.size);
      }
      @Override
      public void forEachRemaining(CharConsumer action)
      {
        final int cursor;
        if((cursor=this.cursor)>=0)
        {
          OmniArray.OfChar.descendingForEach(parent.arr,0,cursor,action);
          this.cursor=-1;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Character> action)
      {
        final int cursor;
        if((cursor=this.cursor)>=0)
        {
          OmniArray.OfChar.descendingForEach(parent.arr,0,cursor,action::accept);
          this.cursor=-1;
        }
      }
    }
    @Override
    public OmniIterator.OfChar iterator()
    {
      return new Itr(this);
    }
    @Override
    public void push(Character val)
    {
      push((char)val);
    }
    @Override
    void uncheckedCopyInto(char[] dst,int length)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(Object[] dst,int length)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(Character[] dst,int length)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(double[] dst,int length)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(float[] dst,int length)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(long[] dst,int length)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(int[] dst,int length)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override
    public Character pop()
    {
      return popChar();
    }
    @Override
    public char popChar()
    {
      return (char)arr[--this.size];
    }
  }
  public
    static class UncheckedList
      extends CharArrSeq
      implements CharListDefault,Cloneable
  {
    public UncheckedList()
    {
      super();
    }
    public UncheckedList(int initialCapacity)
    {
      super(initialCapacity);
    }
    private UncheckedList(int size,char[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implements equals method for UncheckedList
      return false;
    }
    @Override
    public Object clone()
    {
      final char[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new char[size],0,size);
      }
      else
      {
        copy=OmniArray.OfChar.DEFAULT_ARR;
      }
      return new UncheckedList(size,copy);
    }
    void uncheckedToString(int size,char[] buffer)
    {
      OmniArray.OfChar.ascendingToString(this.arr,0,size-1,buffer,1);
    }
    @Override
    int uncheckedHashCode(int size)
    {
      return OmniArray.OfChar.ascendingSeqHashCode(this.arr,0,size-1);
    }
    @Override
    public int indexOf(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfChar.uncheckedindexOf(this.arr,size,TypeUtil.castToChar(val));
      }
      return -1;
    }
    @Override
    public int indexOf(int val)
    {
      if(val==(char)val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfChar.uncheckedindexOf(this.arr,size,(val));
        }
      }
      return -1;
    }
    @Override
    public int indexOf(long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char v;
        if((v=(char)val)==val)
        {
          return OmniArray.OfChar.uncheckedindexOf(this.arr,size,v);
        }
      }
      return -1;
    }
    @Override
    public int indexOf(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char v;
        if(val==(v=(char)val))
        {
          return OmniArray.OfChar.uncheckedindexOf(this.arr,size,v);
        }
      }
      return -1;
    }
    @Override
    public int indexOf(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char v;
        if(val==(v=(char)val))
        {
          return OmniArray.OfChar.uncheckedindexOf(this.arr,size,v);
        }
      }
      return -1;
    }
    @Override
    public int indexOf(Object val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Character)
        {
          return OmniArray.OfChar.uncheckedindexOf(this.arr,size,(char)(val));
        }
      }
      return -1;
    }
    @Override
    public int indexOf(char val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfChar.uncheckedindexOf(this.arr,size,(val));
        }
      }
      return -1;
    }
    @Override
    public int indexOf(short val)
    {
      if(val>=0)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfChar.uncheckedindexOf(this.arr,size,(val));
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfChar.uncheckedlastIndexOf(this.arr,size,TypeUtil.castToChar(val));
      }
      return -1;
    }
    @Override
    public int lastIndexOf(int val)
    {
      if(val==(char)val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfChar.uncheckedlastIndexOf(this.arr,size,(val));
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char v;
        if((v=(char)val)==val)
        {
          return OmniArray.OfChar.uncheckedlastIndexOf(this.arr,size,v);
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char v;
        if(val==(v=(char)val))
        {
          return OmniArray.OfChar.uncheckedlastIndexOf(this.arr,size,v);
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char v;
        if(val==(v=(char)val))
        {
          return OmniArray.OfChar.uncheckedlastIndexOf(this.arr,size,v);
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
        if(val instanceof Character)
        {
          return OmniArray.OfChar.uncheckedlastIndexOf(this.arr,size,(char)(val));
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(char val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfChar.uncheckedlastIndexOf(this.arr,size,(val));
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(short val)
    {
      if(val>=0)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfChar.uncheckedlastIndexOf(this.arr,size,(val));
        }
      }
      return -1;
    }
    @Override
    boolean uncheckedremoveVal (int size
    ,int val
    )
    {
      final var arr=this.arr;
      for(int index=0;;)
      {
        if(
        val==arr[index]
        )
        {
          OmniArray.OfChar.removeIndexAndPullDown(arr,index,--size);
          this.size=size;
          return true;
        }
        if(++index==size)
        {
          return false;
        }
      }
    }
    @Override
    void uncheckedForEach(int size,CharConsumer action)
    {
      {
        OmniArray.OfChar.ascendingForEach(this.arr,0,size-1,action);
      }
    }
    private static class Itr
      extends AbstractCharItr
    {
      transient final UncheckedList parent;
      transient int cursor;
      private Itr(UncheckedList parent)
      {
        this.parent=parent;
        this.cursor=0;
      }
      private Itr(UncheckedList parent,int cursor)
      {
        this.parent=parent;
        this.cursor=cursor;
      }
      @Override
      public boolean hasNext()
      {
        return this.cursor<parent.size;
      }
      @Override
      public char nextChar()
      {
        return (char)parent.arr[cursor++];
      }
      @Override
      public void remove()
      {
        final UncheckedList root;
        OmniArray.OfChar.removeIndexAndPullDown((root=this.parent).arr,--this.cursor,--root.size);
      }
      @Override
      public void forEachRemaining(CharConsumer action)
      {
        final int cursor,bound;
        final UncheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size))
        {
          OmniArray.OfChar.ascendingForEach(parent.arr,cursor,bound-1,action);
          this.cursor=bound;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Character> action)
      {
        final int cursor,bound;
        final UncheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size))
        {
          OmniArray.OfChar.ascendingForEach(parent.arr,cursor,bound-1,action::accept);
          this.cursor=bound;
        }
      }
    }
    @Override
    public OmniIterator.OfChar iterator()
    {
      return new Itr(this);
    }
    private static class ListItr extends Itr implements OmniListIterator.OfChar
    {
      transient int lastRet;
      private ListItr(UncheckedList parent)
      {
        super(parent);
        this.lastRet=-1;
      }
      private ListItr(UncheckedList parent,int cursor)
      {
        super(parent,cursor);
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
      public char nextChar()
      {
        int lastRet;
        this.lastRet=lastRet=this.cursor++;
        return (char)parent.arr[lastRet];
      }
      @Override
      public void remove()
      {
        final UncheckedList root;
        final int lastRet;
        OmniArray.OfChar.removeIndexAndPullDown((root=this.parent).arr,lastRet=this.lastRet,--root.size);
        this.cursor=lastRet;
      }
      @Override
      public void forEachRemaining(CharConsumer action)
      {
        int cursor;
        final int bound;
        final UncheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size))
        {
          OmniArray.OfChar.ascendingForEach(parent.arr,cursor,cursor=bound-1,action);
          this.lastRet=cursor;
          this.cursor=bound;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Character> action)
      {
        final int cursor,bound;
        final UncheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size))
        {
          OmniArray.OfChar.ascendingForEach(parent.arr,cursor,bound-1,action::accept);
          this.cursor=bound;
        }
      }
      @Override
      public char previousChar()
      {
        final int lastRet;
        this.lastRet=lastRet=--this.cursor;
        return (char)parent.arr[lastRet];
      }
      @Override
      public void set(char val)
      {
        parent.arr[this.lastRet]=val;
      }
      @Override
      public void add(char val)
      {
        final UncheckedList root;
        final int rootSize;
        if((rootSize=(root=this.parent).size)!=0)
        {
          ((CharArrSeq)root).uncheckedInsert(this.cursor++,rootSize,val);
        }
        else
        {
          ((CharArrSeq)root).uncheckedInit(val);
          ++this.cursor;
        }
      }
    }
    @Override
    public OmniListIterator.OfChar listIterator()
    {
      return new ListItr(this);
    }
    @Override
    public OmniListIterator.OfChar listIterator(int index)
    {
      return new ListItr(this,index);
    }
    @Override
    public void add(int index,char val)
    {
      int size;
      if((size=this.size)!=0){
        ((CharArrSeq)this).uncheckedInsert(index,size,val);
      }else{
        ((CharArrSeq)this).uncheckedInit(val);
      }
    }
    @Override
    void uncheckedCopyInto(char[] dst,int length)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(Object[] dst,int length)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(Character[] dst,int length)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(double[] dst,int length)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(float[] dst,int length)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(long[] dst,int length)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(int[] dst,int length)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override
    public char getChar(int index)
    {
      return (char)this.arr[index];
    }
    @Override
    public void put(int index,char val)
    {
      this.arr[index]=val;
    }
    @Override
    public char set(int index,char val)
    {
      final char[] arr;
      final var ret=(char)(arr=this.arr)[index];
      arr[index]=val;
      return ret;
    }
    @Override
    public OmniList.OfChar subList(int fromIndex,int toIndex)
    {
      return new UncheckedSubList(this,fromIndex,toIndex-fromIndex);
    }
  }
  public
    static class UncheckedSubList
      implements CharListDefault,Cloneable
  {
    transient final int rootOffset;
    transient int size;
    transient final UncheckedList root;
    transient final UncheckedSubList parent;
    private UncheckedSubList(UncheckedList root,int rootOffset,int size)
    {
      super();
      this.root=root;
      this.parent=null;
      this.rootOffset=rootOffset;
      this.size=size;
    }
    private UncheckedSubList(UncheckedSubList parent,int rootOffset,int size)
    {
      super();
      this.root=parent.root;
      this.parent=parent;
      this.rootOffset=rootOffset;
      this.size=size;
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implements equals method for UncheckedSubList
      return false;
    }
    @Override
    public Object clone()
    {
      final char[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(root.arr,rootOffset,copy=new char[size],0,size);
      }
      else
      {
        copy=OmniArray.OfChar.DEFAULT_ARR;
      }
      return new UncheckedList(size,copy);
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
          final int rootOffset;
          final char[] buffer;
          OmniArray.OfChar.ascendingToString(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,buffer=new char[size*=3],1);
          buffer[0]='[';
          buffer[size-1]=']';
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
        final int rootOffset;
        return OmniArray.OfChar.ascendingSeqHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
    @Override
    public void clear()
    {
      final int size;
      if((size=this.size)!=0)
      {
        for(var curr=parent;curr!=null;curr.size-=size,curr=curr.parent){}
        final UncheckedList root;
        (root=this.root).size=OmniArray.OfChar.removeRangeAndPullDown(root.arr,this.rootOffset,root.size,size);
        this.size=0;
      }
    }
    @Override
    public boolean contains(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final int rootOffset;
        return OmniArray.OfChar.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,TypeUtil.castToChar(val));
      }
      return false;
    }
    @Override
    public boolean contains(int val)
    {
      if(val==(char)val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          return OmniArray.OfChar.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(val));
        }
      }
      return false;
    }
    @Override
    public boolean contains(long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char v;
        if((v=(char)val)==val)
        {
          final int rootOffset;
          return OmniArray.OfChar.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,v);
        }
      }
      return false;
    }
    @Override
    public boolean contains(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char v;
        if(val==(v=(char)val))
        {
          final int rootOffset;
          return OmniArray.OfChar.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,v);
        }
      }
      return false;
    }
    @Override
    public boolean contains(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char v;
        if(val==(v=(char)val))
        {
          final int rootOffset;
          return OmniArray.OfChar.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,v);
        }
      }
      return false;
    }
    @Override
    public boolean contains(Object val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Character)
        {
          final int rootOffset;
          return OmniArray.OfChar.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(char)(val));
        }
      }
      return false;
    }
    @Override
    public boolean contains(byte val)
    {
      if(val>=0)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          return OmniArray.OfChar.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(val));
        }
      }
      return false;
    }
    @Override
    public boolean contains(char val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          return OmniArray.OfChar.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(val));
        }
      }
      return false;
    }
    @Override
    public boolean contains(short val)
    {
      if(val>=0)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          return OmniArray.OfChar.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(val));
        }
      }
      return false;
    }
    @Override
    public boolean removeVal(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return this.uncheckedremoveVal(size,TypeUtil.castToChar(val));
      }
      return false;
    }
    @Override
    public boolean removeVal(int val)
    {
      if(val==(char)val)
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
    public boolean removeVal(long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char v;
        if((v=(char)val)==val)
        {
          return this.uncheckedremoveVal(size,v);
        }
      }
      return false;
    }
    @Override
    public boolean removeVal(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char v;
        if(val==(v=(char)val))
        {
          return this.uncheckedremoveVal(size,v);
        }
      }
      return false;
    }
    @Override
    public boolean removeVal(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char v;
        if(val==(v=(char)val))
        {
          return this.uncheckedremoveVal(size,v);
        }
      }
      return false;
    }
    @Override
    public boolean remove(Object val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Character)
        {
          return this.uncheckedremoveVal(size,(char)(val));
        }
      }
      return false;
    }
    @Override
    public boolean removeVal(byte val)
    {
      if(val>=0)
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
    public boolean removeVal(char val)
    {
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
    public boolean removeVal(short val)
    {
      if(val>=0)
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
    public int indexOf(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfChar.uncheckedindexOf(root.arr,this.rootOffset,size,TypeUtil.castToChar(val));
      }
      return -1;
    }
    @Override
    public int indexOf(int val)
    {
      if(val==(char)val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfChar.uncheckedindexOf(root.arr,this.rootOffset,size,(val));
        }
      }
      return -1;
    }
    @Override
    public int indexOf(long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char v;
        if((v=(char)val)==val)
        {
          return OmniArray.OfChar.uncheckedindexOf(root.arr,this.rootOffset,size,v);
        }
      }
      return -1;
    }
    @Override
    public int indexOf(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char v;
        if(val==(v=(char)val))
        {
          return OmniArray.OfChar.uncheckedindexOf(root.arr,this.rootOffset,size,v);
        }
      }
      return -1;
    }
    @Override
    public int indexOf(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char v;
        if(val==(v=(char)val))
        {
          return OmniArray.OfChar.uncheckedindexOf(root.arr,this.rootOffset,size,v);
        }
      }
      return -1;
    }
    @Override
    public int indexOf(Object val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Character)
        {
          return OmniArray.OfChar.uncheckedindexOf(root.arr,this.rootOffset,size,(char)(val));
        }
      }
      return -1;
    }
    @Override
    public int indexOf(char val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfChar.uncheckedindexOf(root.arr,this.rootOffset,size,(val));
        }
      }
      return -1;
    }
    @Override
    public int indexOf(short val)
    {
      if(val>=0)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfChar.uncheckedindexOf(root.arr,this.rootOffset,size,(val));
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfChar.uncheckedlastIndexOf(root.arr,this.rootOffset,size,TypeUtil.castToChar(val));
      }
      return -1;
    }
    @Override
    public int lastIndexOf(int val)
    {
      if(val==(char)val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfChar.uncheckedlastIndexOf(root.arr,this.rootOffset,size,(val));
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char v;
        if((v=(char)val)==val)
        {
          return OmniArray.OfChar.uncheckedlastIndexOf(root.arr,this.rootOffset,size,v);
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char v;
        if(val==(v=(char)val))
        {
          return OmniArray.OfChar.uncheckedlastIndexOf(root.arr,this.rootOffset,size,v);
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char v;
        if(val==(v=(char)val))
        {
          return OmniArray.OfChar.uncheckedlastIndexOf(root.arr,this.rootOffset,size,v);
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
        if(val instanceof Character)
        {
          return OmniArray.OfChar.uncheckedlastIndexOf(root.arr,this.rootOffset,size,(char)(val));
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(char val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfChar.uncheckedlastIndexOf(root.arr,this.rootOffset,size,(val));
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(short val)
    {
      if(val>=0)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfChar.uncheckedlastIndexOf(root.arr,this.rootOffset,size,(val));
        }
      }
      return -1;
    }
    private boolean uncheckedremoveVal (int size
    ,int val
    )
    {
      final UncheckedList root;
      final var arr=(root=this.root).arr;
      for(int index=this.rootOffset,bound=index+(--size);;++index)
      {
        if(
        val==arr[index]
        )
        {
          for(var curr=parent;curr!=null;--curr.size,curr=curr.parent){}
          OmniArray.OfChar.removeIndexAndPullDown(arr,index,--root.size);
          this.size=size;
          return true;
        }
        if(index==bound)
        {
          return false;
        }
      }
    }
    @Override
    public void forEach(CharConsumer action)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          OmniArray.OfChar.ascendingForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,action);
        }
      }
    }
    @Override
    public void forEach(Consumer<? super Character> action)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          OmniArray.OfChar.ascendingForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,action::accept);
        }
      }
    }
    private static class Itr
      extends AbstractCharItr
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
        this.cursor=cursor;
      }
      @Override
      public boolean hasNext()
      {
        final UncheckedSubList parent;
        return this.cursor<(parent=this.parent).rootOffset+parent.size;
      }
      @Override
      public char nextChar()
      {
        return (char)parent.root.arr[cursor++];
      }
      @Override
      public void remove()
      {
        UncheckedSubList parent;
        final UncheckedList root;
        OmniArray.OfChar.removeIndexAndPullDown((root=(parent=this.parent).root).arr,--this.cursor,--root.size);
        do{
          --parent.size;
        }while((parent=parent.parent)!=null);
      }
      @Override
      public void forEachRemaining(CharConsumer action)
      {
        final int cursor,bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
        {
          OmniArray.OfChar.ascendingForEach(parent.root.arr,cursor,bound-1,action);
          this.cursor=bound;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Character> action)
      {
        final int cursor,bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
        {
          OmniArray.OfChar.ascendingForEach(parent.root.arr,cursor,bound-1,action::accept);
          this.cursor=bound;
        }
      }
    }
    @Override
    public OmniIterator.OfChar iterator()
    {
      return new Itr(this);
    }
    private static class ListItr extends Itr implements OmniListIterator.OfChar
    {
      transient int lastRet;
      private ListItr(UncheckedSubList parent)
      {
        super(parent);
        this.lastRet=-1;
      }
      private ListItr(UncheckedSubList parent,int cursor)
      {
        super(parent,cursor);
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
      public char nextChar()
      {
        int lastRet;
        this.lastRet=lastRet=this.cursor++;
        return (char)parent.root.arr[lastRet];
      }
      @Override
      public void remove()
      {
        UncheckedSubList parent;
        final UncheckedList root;
        final int lastRet;
        OmniArray.OfChar.removeIndexAndPullDown((root=(parent=this.parent).root).arr,lastRet=this.lastRet,--root.size);
        this.cursor=lastRet;
        do{
          --parent.size;
        }while((parent=parent.parent)!=null);
      }
      @Override
      public void forEachRemaining(CharConsumer action)
      {
        int cursor;
        final int bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
        {
          OmniArray.OfChar.ascendingForEach(parent.root.arr,cursor,cursor=bound-1,action);
          this.lastRet=cursor;
          this.cursor=bound;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Character> action)
      {
        final int cursor,bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
        {
          OmniArray.OfChar.ascendingForEach(parent.root.arr,cursor,bound-1,action::accept);
          this.cursor=bound;
        }
      }
      @Override
      public char previousChar()
      {
        final int lastRet;
        this.lastRet=lastRet=--this.cursor;
        return (char)parent.root.arr[lastRet];
      }
      @Override
      public void set(char val)
      {
        parent.root.arr[this.lastRet]=val;
      }
      @Override
      public void add(char val)
      {
        final UncheckedList root;
        final int rootSize;
        UncheckedSubList parent;
        if((rootSize=(root=(parent=this.parent).root).size)!=0)
        {
          ((CharArrSeq)root).uncheckedInsert(this.cursor++,rootSize,val);
        }
        else
        {
          ((CharArrSeq)root).uncheckedInit(val);
          ++this.cursor;
        }
        do{
          ++parent.size;
        }while((parent=parent.parent)!=null);
      }
    }
    @Override
    public OmniListIterator.OfChar listIterator()
    {
      return new ListItr(this);
    }
    @Override
    public OmniListIterator.OfChar listIterator(int index)
    {
      return new ListItr(this,index+this.rootOffset);
    }
    @Override
    public boolean add(Character val){
      return add((char)val);
    }
    @Override
    public boolean add(boolean val){
      return add((char)TypeUtil.castToChar(val));
    }
    @Override
    public boolean add(char val)
    {
      for(var curr=parent;curr!=null;++curr.size,curr=curr.parent){}
      final UncheckedList root;
      final int rootSize;
      if((rootSize=(root=this.root).size)!=0){
        ((CharArrSeq)root).uncheckedInsert(this.rootOffset+(this.size++),rootSize,val);
      }else{
        ((CharArrSeq)root).uncheckedInit(val);
        ++this.size;
      }
      return true;
    }
    @Override
    public void add(int index,char val)
    {
      for(var curr=parent;curr!=null;++curr.size,curr=curr.parent){}
      ++this.size;
      final UncheckedList root;
      final int rootSize;
      if((rootSize=(root=this.root).size)!=0){
        ((CharArrSeq)root).uncheckedInsert(this.rootOffset+index,rootSize,val);
      }else{
        ((CharArrSeq)root).uncheckedInit(val);
      }
    }
    @Override
    public <T> T[] toArray(T[] arr)
    {
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,arr=OmniArray.uncheckedArrResize(size,arr),0,size);
      }
      else if(arr.length!=0)
      {
        arr[0]=null;
      }
      return arr;
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
    public char[] toCharArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new char[size],0,size);
        return dst;
      }
      return OmniArray.OfChar.DEFAULT_ARR;
    }
    @Override
    public Character[] toArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final Character[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new Character[size],0,size);
        return dst;
      }
      return OmniArray.OfChar.DEFAULT_BOXED_ARR;
    }
    @Override
    public double[] toDoubleArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final double[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new double[size],0,size);
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
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new float[size],0,size);
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
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new long[size],0,size);
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
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new int[size],0,size);
        return dst;
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
    @Override
    public char getChar(int index)
    {
      return (char)root.arr[index+this.rootOffset];
    }
    @Override
    public void put(int index,char val)
    {
      root.arr[index+this.rootOffset]=val;
    }
    @Override
    public char set(int index,char val)
    {
      final char[] arr;
      final var ret=(char)(arr=root.arr)[index+=this.rootOffset];
      arr[index]=val;
      return ret;
    }
    @Override
    public OmniList.OfChar subList(int fromIndex,int toIndex)
    {
      return new UncheckedSubList(this,this.rootOffset+fromIndex,toIndex-fromIndex);
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
    private CheckedStack(int size,char[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implements equals method for CheckedStack
      return false;
    }
    @Override
    public Object clone()
    {
      final char[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new char[size],0,size);
      }
      else
      {
        copy=OmniArray.OfChar.DEFAULT_ARR;
      }
      return new CheckedStack(size,copy);
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
    boolean uncheckedremoveVal (int size
    ,int val
    )
    {
      final var arr=this.arr;
      for(int index=--size;;--index)
      {
        if(
        val==arr[index]
        )
        {
          ++this.modCount;
          OmniArray.OfChar.removeIndexAndPullDown(arr,index,size);
          this.size=size;
          return true;
        }
        if(index==0)
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
        OmniArray.OfChar.descendingForEach(this.arr,0,size-1,action);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    private static class Itr
      extends AbstractCharItr
    {
      transient final CheckedStack parent;
      transient int cursor;
      transient int lastRet;
      transient int modCount;
      private Itr(CheckedStack parent)
      {
        this.parent=parent;
        this.cursor=parent.size-1;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      private Itr(CheckedStack parent,int cursor)
      {
        this.parent=parent;
        this.cursor=cursor;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      @Override
      public boolean hasNext()
      {
        return this.cursor>=0;
      }
      @Override
      public char nextChar()
      {
        final CheckedStack root;
        CheckedCollection.checkModCount(modCount,(root=this.parent).modCount);
        final int cursor;
        if((cursor=this.cursor)>=0)
        {
          this.lastRet=cursor;
          this.cursor=cursor-1;
            return (char)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override
      public void remove()
      {
        final int lastRet;
        if((lastRet=this.lastRet)!=-1)
        {
          int modCount;
          final CheckedStack root;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.parent).modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          OmniArray.OfChar.removeIndexAndPullDown(root.arr,lastRet,--root.size);
          this.cursor=lastRet;
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override
      public void forEachRemaining(CharConsumer action)
      {
        final int cursor;
        if((cursor=this.cursor)>=0)
        {
          final int modCount=this.modCount;
          final var parent=this.parent;
          try
          {
            OmniArray.OfChar.descendingForEach(parent.arr,0,cursor,action);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,parent.modCount);
          }
          this.cursor=-1;
          this.lastRet=0;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Character> action)
      {
        final int cursor;
        if((cursor=this.cursor)>=0)
        {
          final int modCount=this.modCount;
          final var parent=this.parent;
          try
          {
            OmniArray.OfChar.descendingForEach(parent.arr,0,cursor,action::accept);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,parent.modCount);
          }
          this.cursor=-1;
          this.lastRet=0;
        }
      }
    }
    @Override
    public OmniIterator.OfChar iterator()
    {
      return new Itr(this);
    }
    @Override
    public void push(char val)
    {
      ++this.modCount;
      super.push(val);
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor)
    {
      return super.toArray(arrSize->{
        final int modCount=this.modCount;
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
    public char popChar()
    {
      int size;
      if((size=this.size)!=0)
      {
        ++this.modCount;
        final var ret=(char)arr[--size];
        this.size=size;
        return ret;
      }
      throw new NoSuchElementException();
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
    private CheckedList(int size,char[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implements equals method for CheckedList
      return false;
    }
    @Override
    public Object clone()
    {
      final char[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new char[size],0,size);
      }
      else
      {
        copy=OmniArray.OfChar.DEFAULT_ARR;
      }
      return new CheckedList(size,copy);
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
    boolean uncheckedremoveVal (int size
    ,int val
    )
    {
      final var arr=this.arr;
      for(int index=0;;)
      {
        if(
        val==arr[index]
        )
        {
          OmniArray.OfChar.removeIndexAndPullDown(arr,index,--size);
          this.size=size;
          return true;
        }
        if(++index==size)
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
        OmniArray.OfChar.ascendingForEach(this.arr,0,size-1,action);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    private static class Itr
      extends AbstractCharItr
    {
      transient final CheckedList parent;
      transient int cursor;
      transient int lastRet;
      transient int modCount;
      private Itr(CheckedList parent)
      {
        this.parent=parent;
        this.cursor=0;
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
      public boolean hasNext()
      {
        return this.cursor<parent.size;
      }
      @Override
      public char nextChar()
      {
        final CheckedList root;
        CheckedCollection.checkModCount(modCount,(root=this.parent).modCount);
        final int cursor;
        if((cursor=this.cursor)<root.size)
        {
          this.lastRet=cursor;
          this.cursor=cursor+1;
            return (char)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override
      public void remove()
      {
        final int lastRet;
        if((lastRet=this.lastRet)!=-1)
        {
          int modCount;
          final CheckedList root;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.parent).modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          OmniArray.OfChar.removeIndexAndPullDown(root.arr,lastRet,--root.size);
          this.cursor=lastRet;
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override
      public void forEachRemaining(CharConsumer action)
      {
        int cursor;
        final int bound;
        final CheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size))
        {
          final int modCount=this.modCount;
          try
          {
            OmniArray.OfChar.ascendingForEach(parent.arr,cursor,cursor=bound-1,action);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,parent.modCount);
          }
          this.cursor=bound;
          this.lastRet=cursor;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Character> action)
      {
        int cursor;
        final int bound;
        final CheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size))
        {
          final int modCount=this.modCount;
          try
          {
            OmniArray.OfChar.ascendingForEach(parent.arr,cursor,cursor=bound-1,action::accept);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,parent.modCount);
          }
          this.cursor=bound;
          this.lastRet=cursor;
        }
      }
    }
    @Override
    public OmniIterator.OfChar iterator()
    {
      return new Itr(this);
    }
    private static class ListItr extends Itr implements OmniListIterator.OfChar
    {
      private ListItr(CheckedList parent)
      {
        super(parent);
      }
      private ListItr(CheckedList parent,int cursor)
      {
        super(parent,cursor);
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
      public char previousChar()
      {
        final CheckedList root;
        int cursor;
        CheckedCollection.checkModCount(modCount,(root=this.parent).modCount);
        if((cursor=this.cursor)>0)
        {
          this.lastRet=--cursor;
          this.cursor=cursor;
          return (char)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override
      public void set(char val)
      {
        final int lastRet;
        if((lastRet=this.lastRet)!=-1)
        {
          final CheckedList root;
          CheckedCollection.checkModCount(modCount,(root=this.parent).modCount);
          root.arr[lastRet]=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override
      public void add(char val)
      {
        int modCount;
        final CheckedList root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.parent).modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        this.lastRet=-1;
        final int rootSize;
        if((rootSize=root.size)!=0)
        {
          ((CharArrSeq)root).uncheckedInsert(this.cursor++,rootSize,val);
        }
        else
        {
          ((CharArrSeq)root).uncheckedInit(val);
          ++this.cursor;
        }
      }
    }
    @Override
    public OmniListIterator.OfChar listIterator()
    {
      return new ListItr(this);
    }
    @Override
    public OmniListIterator.OfChar listIterator(int index)
    {
      if(index<0 || index>this.size)
      {
        throw new IndexOutOfBoundsException("index="+index+"; size="+this.size);
      }
      return new ListItr(this,index);
    }
    @Override
    public void push(char val)
    {
      ++this.modCount;
      super.push(val);
    }
    @Override
    public void add(int index,char val)
    {
      int size;
      if((size=this.size)<index || index<0){
        throw new IndexOutOfBoundsException("index="+index+"; size="+size);
      }
      ++this.modCount;
      if(size!=0){
        ((CharArrSeq)this).uncheckedInsert(index,size,val);
      }else{
        ((CharArrSeq)this).uncheckedInit(val);
      }
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor)
    {
      return super.toArray(arrSize->{
        final int modCount=this.modCount;
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
      if(index<0 || index>=this.size)
      {
        throw new IndexOutOfBoundsException("index="+index+"; size="+this.size);
      }
      return (char)this.arr[index];
    }
    @Override
    public void put(int index,char val)
    {
      if(index<0 || index>=this.size)
      {
        throw new IndexOutOfBoundsException("index="+index+"; size="+this.size);
      }
      this.arr[index]=val;
    }
    @Override
    public char set(int index,char val)
    {
      if(index<0 || index>=this.size)
      {
        throw new IndexOutOfBoundsException("index="+index+"; size="+this.size);
      }
      final char[] arr;
      final var ret=(char)(arr=this.arr)[index];
      arr[index]=val;
      return ret;
    }
    @Override
    public OmniList.OfChar subList(int fromIndex,int toIndex)
    {
      final int subListSize;
      if((subListSize=toIndex-fromIndex)<0 || fromIndex<0 || this.size<toIndex)
      {
        throw new IndexOutOfBoundsException("fromIndex="+fromIndex+"; toIndex="+toIndex+"; size="+this.size);
      }
      return new CheckedSubList(this,fromIndex,subListSize);
    }
  }
  private
    static class CheckedSubList
      implements CharListDefault,Cloneable
  {
    transient int modCount;
    transient final int rootOffset;
    transient int size;
    transient final CheckedList root;
    transient final CheckedSubList parent;
    private CheckedSubList(CheckedList root,int rootOffset,int size)
    {
      super();
      this.root=root;
      this.parent=null;
      this.rootOffset=rootOffset;
      this.size=size;
      this.modCount=root.modCount;
    }
    private CheckedSubList(CheckedSubList parent,int rootOffset,int size)
    {
      super();
      this.root=parent.root;
      this.parent=parent;
      this.rootOffset=rootOffset;
      this.size=size;
      this.modCount=parent.modCount;
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implements equals method for CheckedSubList
      return false;
    }
    @Override
    public Object clone()
    {
      final CheckedList root;
      CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
      final char[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(root.arr,rootOffset,copy=new char[size],0,size);
      }
      else
      {
        copy=OmniArray.OfChar.DEFAULT_ARR;
      }
      return new CheckedList(size,copy);
    }
    @Override
    public String toString()
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      int size;
      if((size=this.size)!=0)
      {
        if(size>(Integer.MAX_VALUE/3))
        {
          throw new OutOfMemoryError();
        }
          final int rootOffset;
          final char[] buffer;
          OmniArray.OfChar.ascendingToString(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,buffer=new char[size*=3],1);
          buffer[0]='[';
          buffer[size-1]=']';
          return new String(buffer,0,size);
      }
      return "[]";
    }
    @Override
    public int hashCode()
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0)
      {
        final int rootOffset;
        return OmniArray.OfChar.ascendingSeqHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
      }
      return 1;
    }
    @Override
    public int size()
    {
      CheckedCollection.checkModCount(modCount,root.modCount);
      return this.size;
    }
    @Override
    public boolean isEmpty()
    {
      CheckedCollection.checkModCount(modCount,root.modCount);
      return this.size==0;
    }
    @Override
    public void clear()
    {
      final CheckedList root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0)
      {
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr.size-=size,curr=curr.parent){}
        root.size=OmniArray.OfChar.removeRangeAndPullDown(root.arr,this.rootOffset,root.size,size);
        this.size=0;
      }
    }
    @Override
    public boolean contains(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          final int rootOffset;
          return OmniArray.OfChar.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,TypeUtil.castToChar(val));
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean contains(int val)
    {
      if(val==(char)val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfChar.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(val));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean contains(long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char v;
        if((v=(char)val)==val)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfChar.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,v);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean contains(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char v;
        if(val==(v=(char)val))
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfChar.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,v);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean contains(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char v;
        if(val==(v=(char)val))
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfChar.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,v);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean contains(Object val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Character)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfChar.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(char)(val));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean contains(byte val)
    {
      if(val>=0)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfChar.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(val));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean contains(char val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfChar.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(val));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean contains(short val)
    {
      if(val>=0)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfChar.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(val));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean removeVal(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return this.uncheckedremoveVal(size,TypeUtil.castToChar(val));
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean removeVal(int val)
    {
      if(val==(char)val)
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
    public boolean removeVal(long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char v;
        if((v=(char)val)==val)
        {
          return this.uncheckedremoveVal(size,v);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean removeVal(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char v;
        if(val==(v=(char)val))
        {
          return this.uncheckedremoveVal(size,v);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean removeVal(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char v;
        if(val==(v=(char)val))
        {
          return this.uncheckedremoveVal(size,v);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean remove(Object val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Character)
        {
          return this.uncheckedremoveVal(size,(char)(val));
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean removeVal(byte val)
    {
      if(val>=0)
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
    public boolean removeVal(char val)
    {
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
    public boolean removeVal(short val)
    {
      if(val>=0)
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
    public int indexOf(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          return OmniArray.OfChar.uncheckedindexOf(root.arr,this.rootOffset,size,TypeUtil.castToChar(val));
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int indexOf(int val)
    {
      if(val==(char)val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfChar.uncheckedindexOf(root.arr,this.rootOffset,size,(val));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int indexOf(long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char v;
        if((v=(char)val)==val)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfChar.uncheckedindexOf(root.arr,this.rootOffset,size,v);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int indexOf(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char v;
        if(val==(v=(char)val))
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfChar.uncheckedindexOf(root.arr,this.rootOffset,size,v);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int indexOf(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char v;
        if(val==(v=(char)val))
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfChar.uncheckedindexOf(root.arr,this.rootOffset,size,v);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int indexOf(Object val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Character)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfChar.uncheckedindexOf(root.arr,this.rootOffset,size,(char)(val));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int indexOf(char val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfChar.uncheckedindexOf(root.arr,this.rootOffset,size,(val));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int indexOf(short val)
    {
      if(val>=0)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfChar.uncheckedindexOf(root.arr,this.rootOffset,size,(val));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int lastIndexOf(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          return OmniArray.OfChar.uncheckedlastIndexOf(root.arr,this.rootOffset,size,TypeUtil.castToChar(val));
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int lastIndexOf(int val)
    {
      if(val==(char)val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfChar.uncheckedlastIndexOf(root.arr,this.rootOffset,size,(val));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int lastIndexOf(long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char v;
        if((v=(char)val)==val)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfChar.uncheckedlastIndexOf(root.arr,this.rootOffset,size,v);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int lastIndexOf(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char v;
        if(val==(v=(char)val))
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfChar.uncheckedlastIndexOf(root.arr,this.rootOffset,size,v);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int lastIndexOf(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final char v;
        if(val==(v=(char)val))
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfChar.uncheckedlastIndexOf(root.arr,this.rootOffset,size,v);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int lastIndexOf(Object val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Character)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfChar.uncheckedlastIndexOf(root.arr,this.rootOffset,size,(char)(val));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int lastIndexOf(char val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfChar.uncheckedlastIndexOf(root.arr,this.rootOffset,size,(val));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int lastIndexOf(short val)
    {
      if(val>=0)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfChar.uncheckedlastIndexOf(root.arr,this.rootOffset,size,(val));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    private boolean uncheckedremoveVal (int size
    ,int val
    )
    {
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      final var arr=root.arr;
      for(int index=this.rootOffset,bound=index+(--size);;++index)
      {
        if(
        val==arr[index]
        )
        {
          root.modCount=++modCount;
          this.modCount=modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,--curr.size,curr=curr.parent){}
          OmniArray.OfChar.removeIndexAndPullDown(arr,index,--root.size);
          this.size=size;
          return true;
        }
        if(index==bound)
        {
          return false;
        }
      }
    }
    @Override
    public void forEach(CharConsumer action)
    {
      int modCount=this.modCount;
      final var root=this.root;
      try
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          OmniArray.OfChar.ascendingForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,action);
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
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          OmniArray.OfChar.ascendingForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,action::accept);
        }
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    private static class Itr
      extends AbstractCharItr
    {
      transient final CheckedSubList parent;
      transient int cursor;
      transient int lastRet;
      transient int modCount;
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
        this.cursor=cursor;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      @Override
      public boolean hasNext()
      {
        final CheckedSubList parent;
        return this.cursor<(parent=this.parent).rootOffset+parent.size;
      }
      @Override
      public char nextChar()
      {
        final CheckedList root;
        final CheckedSubList parent;
        CheckedCollection.checkModCount(modCount,(root=(parent=this.parent).root).modCount);
        final int cursor;
        if((cursor=this.cursor)<(parent.rootOffset+parent.size))
        {
          this.lastRet=cursor;
          this.cursor=cursor+1;
            return (char)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override
      public void remove()
      {
        final int lastRet;
        if((lastRet=this.lastRet)!=-1)
        {
          int modCount;
          final CheckedList root;
          CheckedSubList parent;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
          root.modCount=++modCount;
          do{
            parent.modCount=modCount;
            --parent.size;
          }while((parent=parent.parent)!=null);
          this.modCount=modCount;
          OmniArray.OfChar.removeIndexAndPullDown(root.arr,lastRet,--root.size);
          this.cursor=lastRet;
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override
      public void forEachRemaining(CharConsumer action)
      {
        int cursor;
        final int bound;
        final CheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
        {
          final int modCount=this.modCount;
          final var root=parent.root;
          try
          {
            OmniArray.OfChar.ascendingForEach(root.arr,cursor,cursor=bound-1,action);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
          this.cursor=bound;
          this.lastRet=cursor;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Character> action)
      {
        int cursor;
        final int bound;
        final CheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
        {
          final int modCount=this.modCount;
          final var root=parent.root;
          try
          {
            OmniArray.OfChar.ascendingForEach(root.arr,cursor,cursor=bound-1,action::accept);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
          this.cursor=bound;
          this.lastRet=cursor;
        }
      }
    }
    @Override
    public OmniIterator.OfChar iterator()
    {
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new Itr(this);
    }
    private static class ListItr extends Itr implements OmniListIterator.OfChar
    {
      private ListItr(CheckedSubList parent)
      {
        super(parent);
      }
      private ListItr(CheckedSubList parent,int cursor)
      {
        super(parent,cursor);
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
      public char previousChar()
      {
        final CheckedList root;
        int cursor;
        final CheckedSubList parent;
        CheckedCollection.checkModCount(modCount,(root=(parent=this.parent).root).modCount);
        if((cursor=this.cursor)>parent.rootOffset)
        {
          this.lastRet=--cursor;
          this.cursor=cursor;
          return (char)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override
      public void set(char val)
      {
        final int lastRet;
        if((lastRet=this.lastRet)!=-1)
        {
          final CheckedList root;
          CheckedCollection.checkModCount(modCount,(root=this.parent.root).modCount);
          root.arr[lastRet]=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override
      public void add(char val)
      {
        int modCount;
        final CheckedList root;
        CheckedSubList parent;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
        root.modCount=++modCount;
        do{
          parent.modCount=modCount;
          ++parent.size;
        }while((parent=parent.parent)!=null);
        this.modCount=modCount;
        this.lastRet=-1;
        final int rootSize;
        if((rootSize=root.size)!=0)
        {
          ((CharArrSeq)root).uncheckedInsert(this.cursor++,rootSize,val);
        }
        else
        {
          ((CharArrSeq)root).uncheckedInit(val);
          ++this.cursor;
        }
      }
    }
    @Override
    public OmniListIterator.OfChar listIterator()
    {
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new ListItr(this);
    }
    @Override
    public OmniListIterator.OfChar listIterator(int index)
    {
      CheckedCollection.checkModCount(modCount,root.modCount);
      if(index<0 || index>this.size)
      {
        throw new IndexOutOfBoundsException("index="+index+"; size="+this.size);
      }
      return new ListItr(this,index+this.rootOffset);
    }
    @Override
    public boolean add(Character val){
      return add((char)val);
    }
    @Override
    public boolean add(boolean val){
      return add((char)TypeUtil.castToChar(val));
    }
    @Override
    public boolean add(char val)
    {
      final CheckedList root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,++curr.size,curr=curr.parent){}
      if((modCount=root.size)!=0){
        ((CharArrSeq)root).uncheckedInsert(this.rootOffset+(this.size++),modCount,val);
      }else{
        ((CharArrSeq)root).uncheckedInit(val);
        ++this.size;
      }
      return true;
    }
    @Override
    public void add(int index,char val)
    {
      final CheckedList root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      int size;
      if((size=this.size)<index||index<0){
        throw new IndexOutOfBoundsException("index="+index+"; size="+size);
      }
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,++curr.size,curr=curr.parent){}
      this.size=size+1;
      if((modCount=root.size)!=0){
        ((CharArrSeq)root).uncheckedInsert(this.rootOffset+index,modCount,val);
      }else{
        ((CharArrSeq)root).uncheckedInit(val);
      }
    }
    @Override
    public <T> T[] toArray(T[] arr)
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,arr=OmniArray.uncheckedArrResize(size,arr),0,size);
      }
      else if(arr.length!=0)
      {
        arr[0]=null;
      }
      return arr;
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor)
    {
      final int size;
      final T[] dst;
      final CheckedList root;
      int modCount=this.modCount;
      try
      {
        dst=arrConstructor.apply(size=this.size);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      }
      if(size!=0)
      {
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst,0,size);
      }
      return dst;
    }
    @Override
    public char[] toCharArray()
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0)
      {
        final char[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new char[size],0,size);
        return dst;
      }
      return OmniArray.OfChar.DEFAULT_ARR;
    }
    @Override
    public Character[] toArray()
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0)
      {
        final Character[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new Character[size],0,size);
        return dst;
      }
      return OmniArray.OfChar.DEFAULT_BOXED_ARR;
    }
    @Override
    public double[] toDoubleArray()
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0)
      {
        final double[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new double[size],0,size);
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override
    public float[] toFloatArray()
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0)
      {
        final float[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new float[size],0,size);
        return dst;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override
    public long[] toLongArray()
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0)
      {
        final long[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new long[size],0,size);
        return dst;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override
    public int[] toIntArray()
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0)
      {
        final int[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new int[size],0,size);
        return dst;
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
    @Override
    public char getChar(int index)
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      if(index<0 || index>=this.size)
      {
        throw new IndexOutOfBoundsException("index="+index+"; size="+this.size);
      }
      return (char)root.arr[index+this.rootOffset];
    }
    @Override
    public void put(int index,char val)
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      if(index<0 || index>=this.size)
      {
        throw new IndexOutOfBoundsException("index="+index+"; size="+this.size);
      }
      root.arr[index+this.rootOffset]=val;
    }
    @Override
    public char set(int index,char val)
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      if(index<0 || index>=this.size)
      {
        throw new IndexOutOfBoundsException("index="+index+"; size="+this.size);
      }
      final char[] arr;
      final var ret=(char)(arr=root.arr)[index+=this.rootOffset];
      arr[index]=val;
      return ret;
    }
    @Override
    public OmniList.OfChar subList(int fromIndex,int toIndex)
    {
      CheckedCollection.checkModCount(modCount,root.modCount);
      final int subListSize;
      if((subListSize=toIndex-fromIndex)<0 || fromIndex<0 || this.size<toIndex)
      {
        throw new IndexOutOfBoundsException("fromIndex="+fromIndex+"; toIndex="+toIndex+"; size="+this.size);
      }
      return new CheckedSubList(this,this.rootOffset+fromIndex,subListSize);
    }
  }
}
