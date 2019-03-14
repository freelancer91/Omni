package omni.impl.seq;
import omni.api.OmniCollection;
import omni.util.OmniArray;
import omni.api.OmniList;
import omni.api.OmniStack;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.Comparator;
import omni.util.ArrCopy;
import omni.util.ByteSortUtil;
import omni.impl.CheckedCollection;
import java.util.NoSuchElementException;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;
import omni.util.TypeUtil;
import java.util.ConcurrentModificationException;
import omni.function.ByteUnaryOperator;
import omni.function.ByteComparator;
import omni.function.BytePredicate;
import omni.function.ByteConsumer;
import omni.util.ToStringUtil;
import omni.util.BitSetUtil;
import omni.impl.AbstractByteItr;
public abstract class ByteArrSeq implements OmniCollection.OfByte
{
  transient int size;
  transient byte[] arr;
  private ByteArrSeq()
  {
    super();
  }
  private ByteArrSeq(int initialCapacity)
  {
    super();
    switch(initialCapacity)
    { 
    default:
      this.arr=new byte[initialCapacity];
      return;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfByte.DEFAULT_ARR;
    case 0:
    }
  }
  private ByteArrSeq(int size,byte[] arr)
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
      if(size<=(OmniArray.MAX_ARR_SIZE/6)){
        (buffer=new char[size*6])[size=uncheckedToString(size,buffer)]=']';
      }else{
        final ToStringUtil.OmniStringBuilder builder;
        uncheckedToString(size,builder=new ToStringUtil.OmniStringBuilder(1,new char[size*5]));
        (buffer=builder.buffer)[size=builder.size]=']';
      }
      buffer[0]='[';
      return new String(buffer,0,size+1);
    }
    return "[]";
  }
  abstract int uncheckedHashCode(int size);
  abstract int uncheckedToString(int size,char[] buffer);
  abstract void uncheckedToString(int size,ToStringUtil.OmniStringBuilder builder);
  @Override
  public boolean contains(boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return OmniArray.OfByte.uncheckedcontains(this.arr,0,size-1,TypeUtil.castToByte(val));
    }
    return false;
  }
  @Override
  public boolean contains(int val)
  {
    if(val==(byte)val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfByte.uncheckedcontains(this.arr,0,size-1,(val));
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
      final byte v;
      if((v=(byte)val)==val)
      {
        return OmniArray.OfByte.uncheckedcontains(this.arr,0,size-1,v);
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
      final byte v;
      if(val==(v=(byte)val))
      {
        return OmniArray.OfByte.uncheckedcontains(this.arr,0,size-1,v);
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
      final byte v;
      if(val==(v=(byte)val))
      {
        return OmniArray.OfByte.uncheckedcontains(this.arr,0,size-1,v);
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
      if(val instanceof Byte)
      {
        return OmniArray.OfByte.uncheckedcontains(this.arr,0,size-1,(byte)(val));
      }
    }
    return false;
  }
  @Override
  public boolean contains(byte val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfByte.uncheckedcontains(this.arr,0,size-1,(val));
      }
    }
    return false;
  }
  @Override
  public boolean contains(char val)
  {
    if(val<=Byte.MAX_VALUE)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfByte.uncheckedcontains(this.arr,0,size-1,(val));
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
      return this.uncheckedremoveVal(size,TypeUtil.castToByte(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(int val)
  {
    if(val==(byte)val)
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
      final byte v;
      if((v=(byte)val)==val)
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
      final byte v;
      if(val==(v=(byte)val))
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
      final byte v;
      if(val==(v=(byte)val))
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
      if(val instanceof Byte)
      {
        return this.uncheckedremoveVal(size,(byte)(val));
      }
    }
    return false;
  }
  @Override
  public boolean removeVal(byte val)
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
  public boolean removeVal(char val)
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
  abstract boolean uncheckedremoveVal(int size,int val);
  abstract void uncheckedForEach(int size,ByteConsumer action);
  @Override
  public void forEach(ByteConsumer action)
  {
    final int size;
    if((size=this.size)!=0)
    {
      uncheckedForEach(size,action);
    }
  }
  @Override
  public void forEach(Consumer<? super Byte> action)
  {
    final int size;
    if((size=this.size)!=0)
    {
      uncheckedForEach(size,action::accept);
    }
  }
  private void uncheckedAppend(int size,byte val)
  {
    byte[] arr;
    if((arr=this.arr).length==size)
    {
      ArrCopy.uncheckedCopy(arr,0,arr=new byte[OmniArray.growBy50Pct(size)],0,size);
      this.arr=arr;
    }
    arr[size]=val;
    this.size=size+1;
  }
  private void uncheckedInit(byte val)
  {
    byte[] arr;
    if((arr=this.arr)==null)
    {
      this.arr=new byte[]{val};
    }
    else
    {
      if(arr==OmniArray.OfByte.DEFAULT_ARR)
      {
        this.arr=arr=new byte[OmniArray.DEFAULT_ARR_SEQ_CAP];
      }
      arr[0]=val;
    }
    this.size=1;
  }
  private void uncheckedInsert(int index,int size,byte val)
  {
    final int tailDist;
    if((tailDist=size-index)==0)
    {
      uncheckedAppend(size,val);
    }
    else
    {
      byte[] arr;
      if((arr=this.arr).length==size)
      {
        final byte[] tmp;
        ArrCopy.semicheckedCopy(arr,0,tmp=new byte[OmniArray.growBy50Pct(size)],0,index);
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
  public void push(byte val)
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
  public boolean add(byte val)
  {
    push(val);
    return true;
  }
  @Override
  public boolean add(Byte val)
  {
    push((byte)val);
    return true;
  }
  @Override
  public boolean add(boolean val)
  {
    push((byte)TypeUtil.castToByte(val));
    return true;
  }
  abstract void uncheckedCopyInto(byte[] dst,int length);
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
  abstract void uncheckedCopyInto(Object[] dst,int length);
  abstract void uncheckedCopyInto(Byte[] dst,int length);
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
  abstract void uncheckedCopyInto(short[] dst,int length);
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
  public
    static class UncheckedStack
      extends ByteArrSeq
      implements OmniStack.OfByte,Cloneable
  {
    public UncheckedStack()
    {
      super();
    }
    public UncheckedStack(int initialCapacity)
    {
      super(initialCapacity);
    }
    private UncheckedStack(int size,byte[] arr)
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
      final byte[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new byte[size],0,size);
      }
      else
      {
        copy=OmniArray.OfByte.DEFAULT_ARR;
      }
      return new UncheckedStack(size,copy);
    }
    int uncheckedToString(int size,char[] buffer)
    {
      return OmniArray.OfByte.descendingToString(this.arr,0,size-1,buffer,1);
    }
    void uncheckedToString(int size,ToStringUtil.OmniStringBuilder builder)
    {
      OmniArray.OfByte.descendingToString(this.arr,0,size-1,builder);
    }
    @Override
    int uncheckedHashCode(int size)
    {
      return OmniArray.OfByte.descendingSeqHashCode(this.arr,0,size-1);
    }
    @Override
    public int search(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfByte.uncheckedsearch(this.arr,size,TypeUtil.castToByte(val));
      }
      return -1;
    }
    @Override
    public int search(int val)
    {
      if(val==(byte)val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfByte.uncheckedsearch(this.arr,size,(val));
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
        final byte v;
        if((v=(byte)val)==val)
        {
          return OmniArray.OfByte.uncheckedsearch(this.arr,size,v);
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
        final byte v;
        if(val==(v=(byte)val))
        {
          return OmniArray.OfByte.uncheckedsearch(this.arr,size,v);
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
        final byte v;
        if(val==(v=(byte)val))
        {
          return OmniArray.OfByte.uncheckedsearch(this.arr,size,v);
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
        if(val instanceof Byte)
        {
          return OmniArray.OfByte.uncheckedsearch(this.arr,size,(byte)(val));
        }
      }
      return -1;
    }
    @Override
    public int search(byte val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfByte.uncheckedsearch(this.arr,size,(val));
        }
      }
      return -1;
    }
    @Override
    public int search(char val)
    {
      if(val<=Byte.MAX_VALUE)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfByte.uncheckedsearch(this.arr,size,(val));
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
          OmniArray.OfByte.removeIndexAndPullDown(arr,index,size);
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
    void uncheckedForEach(int size,ByteConsumer action)
    {
      {
        OmniArray.OfByte.descendingForEach(this.arr,0,size-1,action);
      }
    }
    private static class Itr
      extends AbstractByteItr
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
      public byte nextByte()
      {
        return (byte)parent.arr[cursor--];
      }
      @Override
      public void remove()
      {
        final UncheckedStack root;
        OmniArray.OfByte.removeIndexAndPullDown((root=this.parent).arr,this.cursor,--root.size);
      }
      @Override
      public void forEachRemaining(ByteConsumer action)
      {
        final int cursor;
        if((cursor=this.cursor)>=0)
        {
          OmniArray.OfByte.descendingForEach(parent.arr,0,cursor,action);
          this.cursor=-1;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Byte> action)
      {
        final int cursor;
        if((cursor=this.cursor)>=0)
        {
          OmniArray.OfByte.descendingForEach(parent.arr,0,cursor,action::accept);
          this.cursor=-1;
        }
      }
    }
    @Override
    public OmniIterator.OfByte iterator()
    {
      return new Itr(this);
    }
    @Override
    public void push(Byte val)
    {
      push((byte)val);
    }
    @Override
    void uncheckedCopyInto(byte[] dst,int length)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(Object[] dst,int length)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(Byte[] dst,int length)
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
    void uncheckedCopyInto(short[] dst,int length)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override
    public Byte pop()
    {
      return popByte();
    }
    @Override
    public byte popByte()
    {
      return (byte)arr[--this.size];
    }
  }
  public
    static class UncheckedList
      extends ByteArrSeq
      implements ByteListDefault,Cloneable
  {
    public UncheckedList()
    {
      super();
    }
    public UncheckedList(int initialCapacity)
    {
      super(initialCapacity);
    }
    private UncheckedList(int size,byte[] arr)
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
      final byte[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new byte[size],0,size);
      }
      else
      {
        copy=OmniArray.OfByte.DEFAULT_ARR;
      }
      return new UncheckedList(size,copy);
    }
    int uncheckedToString(int size,char[] buffer)
    {
      return OmniArray.OfByte.ascendingToString(this.arr,0,size-1,buffer,1);
    }
    void uncheckedToString(int size,ToStringUtil.OmniStringBuilder builder)
    {
      OmniArray.OfByte.ascendingToString(this.arr,0,size-1,builder);
    }
    @Override
    int uncheckedHashCode(int size)
    {
      return OmniArray.OfByte.ascendingSeqHashCode(this.arr,0,size-1);
    }
    @Override
    public int indexOf(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfByte.uncheckedindexOf(this.arr,size,TypeUtil.castToByte(val));
      }
      return -1;
    }
    @Override
    public int indexOf(int val)
    {
      if(val==(byte)val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfByte.uncheckedindexOf(this.arr,size,(val));
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
        final byte v;
        if((v=(byte)val)==val)
        {
          return OmniArray.OfByte.uncheckedindexOf(this.arr,size,v);
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
        final byte v;
        if(val==(v=(byte)val))
        {
          return OmniArray.OfByte.uncheckedindexOf(this.arr,size,v);
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
        final byte v;
        if(val==(v=(byte)val))
        {
          return OmniArray.OfByte.uncheckedindexOf(this.arr,size,v);
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
        if(val instanceof Byte)
        {
          return OmniArray.OfByte.uncheckedindexOf(this.arr,size,(byte)(val));
        }
      }
      return -1;
    }
    @Override
    public int indexOf(byte val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfByte.uncheckedindexOf(this.arr,size,(val));
        }
      }
      return -1;
    }
    @Override
    public int indexOf(char val)
    {
      if(val<=Byte.MAX_VALUE)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfByte.uncheckedindexOf(this.arr,size,(val));
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
        return OmniArray.OfByte.uncheckedlastIndexOf(this.arr,size,TypeUtil.castToByte(val));
      }
      return -1;
    }
    @Override
    public int lastIndexOf(int val)
    {
      if(val==(byte)val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfByte.uncheckedlastIndexOf(this.arr,size,(val));
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
        final byte v;
        if((v=(byte)val)==val)
        {
          return OmniArray.OfByte.uncheckedlastIndexOf(this.arr,size,v);
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
        final byte v;
        if(val==(v=(byte)val))
        {
          return OmniArray.OfByte.uncheckedlastIndexOf(this.arr,size,v);
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
        final byte v;
        if(val==(v=(byte)val))
        {
          return OmniArray.OfByte.uncheckedlastIndexOf(this.arr,size,v);
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
          return OmniArray.OfByte.uncheckedlastIndexOf(this.arr,size,(byte)(val));
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(byte val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfByte.uncheckedlastIndexOf(this.arr,size,(val));
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(char val)
    {
      if(val<=Byte.MAX_VALUE)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfByte.uncheckedlastIndexOf(this.arr,size,(val));
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
          OmniArray.OfByte.removeIndexAndPullDown(arr,index,--size);
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
    void uncheckedForEach(int size,ByteConsumer action)
    {
      {
        OmniArray.OfByte.ascendingForEach(this.arr,0,size-1,action);
      }
    }
    private static class Itr
      extends AbstractByteItr
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
      public byte nextByte()
      {
        return (byte)parent.arr[cursor++];
      }
      @Override
      public void remove()
      {
        final UncheckedList root;
        OmniArray.OfByte.removeIndexAndPullDown((root=this.parent).arr,--this.cursor,--root.size);
      }
      @Override
      public void forEachRemaining(ByteConsumer action)
      {
        final int cursor,bound;
        final UncheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size))
        {
          OmniArray.OfByte.ascendingForEach(parent.arr,cursor,bound-1,action);
          this.cursor=bound;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Byte> action)
      {
        final int cursor,bound;
        final UncheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size))
        {
          OmniArray.OfByte.ascendingForEach(parent.arr,cursor,bound-1,action::accept);
          this.cursor=bound;
        }
      }
    }
    @Override
    public OmniIterator.OfByte iterator()
    {
      return new Itr(this);
    }
    private static class ListItr extends Itr implements OmniListIterator.OfByte
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
      public byte nextByte()
      {
        int lastRet;
        this.lastRet=lastRet=this.cursor++;
        return (byte)parent.arr[lastRet];
      }
      @Override
      public void remove()
      {
        final UncheckedList root;
        final int lastRet;
        OmniArray.OfByte.removeIndexAndPullDown((root=this.parent).arr,lastRet=this.lastRet,--root.size);
        this.cursor=lastRet;
      }
      @Override
      public void forEachRemaining(ByteConsumer action)
      {
        int cursor;
        final int bound;
        final UncheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size))
        {
          OmniArray.OfByte.ascendingForEach(parent.arr,cursor,cursor=bound-1,action);
          this.lastRet=cursor;
          this.cursor=bound;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Byte> action)
      {
        final int cursor,bound;
        final UncheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size))
        {
          OmniArray.OfByte.ascendingForEach(parent.arr,cursor,bound-1,action::accept);
          this.cursor=bound;
        }
      }
      @Override
      public byte previousByte()
      {
        final int lastRet;
        this.lastRet=lastRet=--this.cursor;
        return (byte)parent.arr[lastRet];
      }
      @Override
      public void set(byte val)
      {
        parent.arr[this.lastRet]=val;
      }
      @Override
      public void add(byte val)
      {
        final UncheckedList root;
        final int rootSize;
        if((rootSize=(root=this.parent).size)!=0)
        {
          ((ByteArrSeq)root).uncheckedInsert(this.cursor++,rootSize,val);
        }
        else
        {
          ((ByteArrSeq)root).uncheckedInit(val);
          ++this.cursor;
        }
      }
    }
    @Override
    public OmniListIterator.OfByte listIterator()
    {
      return new ListItr(this);
    }
    @Override
    public OmniListIterator.OfByte listIterator(int index)
    {
      return new ListItr(this,index);
    }
    @Override
    public void add(int index,byte val)
    {
      int size;
      if((size=this.size)!=0){
        ((ByteArrSeq)this).uncheckedInsert(index,size,val);
      }else{
        ((ByteArrSeq)this).uncheckedInit(val);
      }
    }
    @Override
    void uncheckedCopyInto(byte[] dst,int length)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(Object[] dst,int length)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override
    void uncheckedCopyInto(Byte[] dst,int length)
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
    void uncheckedCopyInto(short[] dst,int length)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override
    public byte getByte(int index)
    {
      return (byte)this.arr[index];
    }
    @Override
    public void put(int index,byte val)
    {
      this.arr[index]=val;
    }
    @Override
    public byte set(int index,byte val)
    {
      final byte[] arr;
      final var ret=(byte)(arr=this.arr)[index];
      arr[index]=val;
      return ret;
    }
    @Override
    public OmniList.OfByte subList(int fromIndex,int toIndex)
    {
      return new UncheckedSubList(this,fromIndex,toIndex-fromIndex);
    }
  }
  public
    static class UncheckedSubList
      implements ByteListDefault,Cloneable
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
      final byte[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(root.arr,rootOffset,copy=new byte[size],0,size);
      }
      else
      {
        copy=OmniArray.OfByte.DEFAULT_ARR;
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
          if(size<=(OmniArray.MAX_ARR_SIZE/6)){
            (buffer=new char[size*6])[size=OmniArray.OfByte.ascendingToString(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,buffer,1)]=']';
          }else{
            final ToStringUtil.OmniStringBuilder builder;
            OmniArray.OfByte.ascendingToString(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,builder=new ToStringUtil.OmniStringBuilder(1,new char[size*5]));
            (buffer=builder.buffer)[size=builder.size]=']';
          }
          buffer[0]='[';
          return new String(buffer,0,size+1);
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
        return OmniArray.OfByte.ascendingSeqHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
        (root=this.root).size=OmniArray.OfByte.removeRangeAndPullDown(root.arr,this.rootOffset,root.size,size);
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
        return OmniArray.OfByte.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,TypeUtil.castToByte(val));
      }
      return false;
    }
    @Override
    public boolean contains(int val)
    {
      if(val==(byte)val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          return OmniArray.OfByte.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(val));
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
        final byte v;
        if((v=(byte)val)==val)
        {
          final int rootOffset;
          return OmniArray.OfByte.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,v);
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
        final byte v;
        if(val==(v=(byte)val))
        {
          final int rootOffset;
          return OmniArray.OfByte.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,v);
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
        final byte v;
        if(val==(v=(byte)val))
        {
          final int rootOffset;
          return OmniArray.OfByte.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,v);
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
        if(val instanceof Byte)
        {
          final int rootOffset;
          return OmniArray.OfByte.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(byte)(val));
        }
      }
      return false;
    }
    @Override
    public boolean contains(byte val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          return OmniArray.OfByte.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(val));
        }
      }
      return false;
    }
    @Override
    public boolean contains(char val)
    {
      if(val<=Byte.MAX_VALUE)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          return OmniArray.OfByte.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(val));
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
        return this.uncheckedremoveVal(size,TypeUtil.castToByte(val));
      }
      return false;
    }
    @Override
    public boolean removeVal(int val)
    {
      if(val==(byte)val)
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
        final byte v;
        if((v=(byte)val)==val)
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
        final byte v;
        if(val==(v=(byte)val))
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
        final byte v;
        if(val==(v=(byte)val))
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
        if(val instanceof Byte)
        {
          return this.uncheckedremoveVal(size,(byte)(val));
        }
      }
      return false;
    }
    @Override
    public boolean removeVal(byte val)
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
    public boolean removeVal(char val)
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
    public int indexOf(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfByte.uncheckedindexOf(root.arr,this.rootOffset,size,TypeUtil.castToByte(val));
      }
      return -1;
    }
    @Override
    public int indexOf(int val)
    {
      if(val==(byte)val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfByte.uncheckedindexOf(root.arr,this.rootOffset,size,(val));
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
        final byte v;
        if((v=(byte)val)==val)
        {
          return OmniArray.OfByte.uncheckedindexOf(root.arr,this.rootOffset,size,v);
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
        final byte v;
        if(val==(v=(byte)val))
        {
          return OmniArray.OfByte.uncheckedindexOf(root.arr,this.rootOffset,size,v);
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
        final byte v;
        if(val==(v=(byte)val))
        {
          return OmniArray.OfByte.uncheckedindexOf(root.arr,this.rootOffset,size,v);
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
        if(val instanceof Byte)
        {
          return OmniArray.OfByte.uncheckedindexOf(root.arr,this.rootOffset,size,(byte)(val));
        }
      }
      return -1;
    }
    @Override
    public int indexOf(byte val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfByte.uncheckedindexOf(root.arr,this.rootOffset,size,(val));
        }
      }
      return -1;
    }
    @Override
    public int indexOf(char val)
    {
      if(val<=Byte.MAX_VALUE)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfByte.uncheckedindexOf(root.arr,this.rootOffset,size,(val));
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
        return OmniArray.OfByte.uncheckedlastIndexOf(root.arr,this.rootOffset,size,TypeUtil.castToByte(val));
      }
      return -1;
    }
    @Override
    public int lastIndexOf(int val)
    {
      if(val==(byte)val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfByte.uncheckedlastIndexOf(root.arr,this.rootOffset,size,(val));
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
        final byte v;
        if((v=(byte)val)==val)
        {
          return OmniArray.OfByte.uncheckedlastIndexOf(root.arr,this.rootOffset,size,v);
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
        final byte v;
        if(val==(v=(byte)val))
        {
          return OmniArray.OfByte.uncheckedlastIndexOf(root.arr,this.rootOffset,size,v);
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
        final byte v;
        if(val==(v=(byte)val))
        {
          return OmniArray.OfByte.uncheckedlastIndexOf(root.arr,this.rootOffset,size,v);
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
          return OmniArray.OfByte.uncheckedlastIndexOf(root.arr,this.rootOffset,size,(byte)(val));
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(byte val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfByte.uncheckedlastIndexOf(root.arr,this.rootOffset,size,(val));
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(char val)
    {
      if(val<=Byte.MAX_VALUE)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfByte.uncheckedlastIndexOf(root.arr,this.rootOffset,size,(val));
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
          OmniArray.OfByte.removeIndexAndPullDown(arr,index,--root.size);
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
    public void forEach(ByteConsumer action)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          OmniArray.OfByte.ascendingForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,action);
        }
      }
    }
    @Override
    public void forEach(Consumer<? super Byte> action)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          OmniArray.OfByte.ascendingForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,action::accept);
        }
      }
    }
    private static class Itr
      extends AbstractByteItr
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
      public byte nextByte()
      {
        return (byte)parent.root.arr[cursor++];
      }
      @Override
      public void remove()
      {
        UncheckedSubList parent;
        final UncheckedList root;
        OmniArray.OfByte.removeIndexAndPullDown((root=(parent=this.parent).root).arr,--this.cursor,--root.size);
        do{
          --parent.size;
        }while((parent=parent.parent)!=null);
      }
      @Override
      public void forEachRemaining(ByteConsumer action)
      {
        final int cursor,bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
        {
          OmniArray.OfByte.ascendingForEach(parent.root.arr,cursor,bound-1,action);
          this.cursor=bound;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Byte> action)
      {
        final int cursor,bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
        {
          OmniArray.OfByte.ascendingForEach(parent.root.arr,cursor,bound-1,action::accept);
          this.cursor=bound;
        }
      }
    }
    @Override
    public OmniIterator.OfByte iterator()
    {
      return new Itr(this);
    }
    private static class ListItr extends Itr implements OmniListIterator.OfByte
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
      public byte nextByte()
      {
        int lastRet;
        this.lastRet=lastRet=this.cursor++;
        return (byte)parent.root.arr[lastRet];
      }
      @Override
      public void remove()
      {
        UncheckedSubList parent;
        final UncheckedList root;
        final int lastRet;
        OmniArray.OfByte.removeIndexAndPullDown((root=(parent=this.parent).root).arr,lastRet=this.lastRet,--root.size);
        this.cursor=lastRet;
        do{
          --parent.size;
        }while((parent=parent.parent)!=null);
      }
      @Override
      public void forEachRemaining(ByteConsumer action)
      {
        int cursor;
        final int bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
        {
          OmniArray.OfByte.ascendingForEach(parent.root.arr,cursor,cursor=bound-1,action);
          this.lastRet=cursor;
          this.cursor=bound;
        }
      }
      @Override
      public void forEachRemaining(Consumer<? super Byte> action)
      {
        final int cursor,bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
        {
          OmniArray.OfByte.ascendingForEach(parent.root.arr,cursor,bound-1,action::accept);
          this.cursor=bound;
        }
      }
      @Override
      public byte previousByte()
      {
        final int lastRet;
        this.lastRet=lastRet=--this.cursor;
        return (byte)parent.root.arr[lastRet];
      }
      @Override
      public void set(byte val)
      {
        parent.root.arr[this.lastRet]=val;
      }
      @Override
      public void add(byte val)
      {
        final UncheckedList root;
        final int rootSize;
        UncheckedSubList parent;
        if((rootSize=(root=(parent=this.parent).root).size)!=0)
        {
          ((ByteArrSeq)root).uncheckedInsert(this.cursor++,rootSize,val);
        }
        else
        {
          ((ByteArrSeq)root).uncheckedInit(val);
          ++this.cursor;
        }
        do{
          ++parent.size;
        }while((parent=parent.parent)!=null);
      }
    }
    @Override
    public OmniListIterator.OfByte listIterator()
    {
      return new ListItr(this);
    }
    @Override
    public OmniListIterator.OfByte listIterator(int index)
    {
      return new ListItr(this,index+this.rootOffset);
    }
    @Override
    public boolean add(Byte val){
      return add((byte)val);
    }
    @Override
    public boolean add(boolean val){
      return add((byte)TypeUtil.castToByte(val));
    }
    @Override
    public boolean add(byte val)
    {
      for(var curr=parent;curr!=null;++curr.size,curr=curr.parent){}
      final UncheckedList root;
      final int rootSize;
      if((rootSize=(root=this.root).size)!=0){
        ((ByteArrSeq)root).uncheckedInsert(this.rootOffset+(this.size++),rootSize,val);
      }else{
        ((ByteArrSeq)root).uncheckedInit(val);
        ++this.size;
      }
      return true;
    }
    @Override
    public void add(int index,byte val)
    {
      for(var curr=parent;curr!=null;++curr.size,curr=curr.parent){}
      ++this.size;
      final UncheckedList root;
      final int rootSize;
      if((rootSize=(root=this.root).size)!=0){
        ((ByteArrSeq)root).uncheckedInsert(this.rootOffset+index,rootSize,val);
      }else{
        ((ByteArrSeq)root).uncheckedInit(val);
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
    public byte[] toByteArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final byte[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new byte[size],0,size);
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
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new Byte[size],0,size);
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
    public short[] toShortArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final short[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new short[size],0,size);
        return dst;
      }
      return OmniArray.OfShort.DEFAULT_ARR;
    }
    @Override
    public byte getByte(int index)
    {
      return (byte)root.arr[index+this.rootOffset];
    }
    @Override
    public void put(int index,byte val)
    {
      root.arr[index+this.rootOffset]=val;
    }
    @Override
    public byte set(int index,byte val)
    {
      final byte[] arr;
      final var ret=(byte)(arr=root.arr)[index+=this.rootOffset];
      arr[index]=val;
      return ret;
    }
    @Override
    public OmniList.OfByte subList(int fromIndex,int toIndex)
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
    private CheckedStack(int size,byte[] arr)
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
      final byte[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new byte[size],0,size);
      }
      else
      {
        copy=OmniArray.OfByte.DEFAULT_ARR;
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
          OmniArray.OfByte.removeIndexAndPullDown(arr,index,size);
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
    void uncheckedForEach(int size,ByteConsumer action)
    {
      int modCount=this.modCount;
      try
      {
        OmniArray.OfByte.descendingForEach(this.arr,0,size-1,action);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    private static class Itr
      extends AbstractByteItr
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
      public byte nextByte()
      {
        final CheckedStack root;
        CheckedCollection.checkModCount(modCount,(root=this.parent).modCount);
        final int cursor;
        if((cursor=this.cursor)>=0)
        {
          this.lastRet=cursor;
          this.cursor=cursor-1;
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
          int modCount;
          final CheckedStack root;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.parent).modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          OmniArray.OfByte.removeIndexAndPullDown(root.arr,lastRet,--root.size);
          this.cursor=lastRet;
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override
      public void forEachRemaining(ByteConsumer action)
      {
        final int cursor;
        if((cursor=this.cursor)>=0)
        {
          final int modCount=this.modCount;
          final var parent=this.parent;
          try
          {
            OmniArray.OfByte.descendingForEach(parent.arr,0,cursor,action);
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
      public void forEachRemaining(Consumer<? super Byte> action)
      {
        final int cursor;
        if((cursor=this.cursor)>=0)
        {
          final int modCount=this.modCount;
          final var parent=this.parent;
          try
          {
            OmniArray.OfByte.descendingForEach(parent.arr,0,cursor,action::accept);
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
    public OmniIterator.OfByte iterator()
    {
      return new Itr(this);
    }
    @Override
    public void push(byte val)
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
    public byte popByte()
    {
      int size;
      if((size=this.size)!=0)
      {
        ++this.modCount;
        final var ret=(byte)arr[--size];
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
    private CheckedList(int size,byte[] arr)
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
      final byte[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new byte[size],0,size);
      }
      else
      {
        copy=OmniArray.OfByte.DEFAULT_ARR;
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
          OmniArray.OfByte.removeIndexAndPullDown(arr,index,--size);
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
    void uncheckedForEach(int size,ByteConsumer action)
    {
      int modCount=this.modCount;
      try
      {
        OmniArray.OfByte.ascendingForEach(this.arr,0,size-1,action);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    private static class Itr
      extends AbstractByteItr
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
      public byte nextByte()
      {
        final CheckedList root;
        CheckedCollection.checkModCount(modCount,(root=this.parent).modCount);
        final int cursor;
        if((cursor=this.cursor)<root.size)
        {
          this.lastRet=cursor;
          this.cursor=cursor+1;
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
          int modCount;
          final CheckedList root;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.parent).modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          OmniArray.OfByte.removeIndexAndPullDown(root.arr,lastRet,--root.size);
          this.cursor=lastRet;
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override
      public void forEachRemaining(ByteConsumer action)
      {
        int cursor;
        final int bound;
        final CheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size))
        {
          final int modCount=this.modCount;
          try
          {
            OmniArray.OfByte.ascendingForEach(parent.arr,cursor,cursor=bound-1,action);
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
      public void forEachRemaining(Consumer<? super Byte> action)
      {
        int cursor;
        final int bound;
        final CheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size))
        {
          final int modCount=this.modCount;
          try
          {
            OmniArray.OfByte.ascendingForEach(parent.arr,cursor,cursor=bound-1,action::accept);
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
    public OmniIterator.OfByte iterator()
    {
      return new Itr(this);
    }
    private static class ListItr extends Itr implements OmniListIterator.OfByte
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
      public byte previousByte()
      {
        final CheckedList root;
        int cursor;
        CheckedCollection.checkModCount(modCount,(root=this.parent).modCount);
        if((cursor=this.cursor)>0)
        {
          this.lastRet=--cursor;
          this.cursor=cursor;
          return (byte)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override
      public void set(byte val)
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
      public void add(byte val)
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
          ((ByteArrSeq)root).uncheckedInsert(this.cursor++,rootSize,val);
        }
        else
        {
          ((ByteArrSeq)root).uncheckedInit(val);
          ++this.cursor;
        }
      }
    }
    @Override
    public OmniListIterator.OfByte listIterator()
    {
      return new ListItr(this);
    }
    @Override
    public OmniListIterator.OfByte listIterator(int index)
    {
      if(index<0 || index>this.size)
      {
        throw new IndexOutOfBoundsException("index="+index+"; size="+this.size);
      }
      return new ListItr(this,index);
    }
    @Override
    public void push(byte val)
    {
      ++this.modCount;
      super.push(val);
    }
    @Override
    public void add(int index,byte val)
    {
      int size;
      if((size=this.size)<index || index<0){
        throw new IndexOutOfBoundsException("index="+index+"; size="+size);
      }
      ++this.modCount;
      if(size!=0){
        ((ByteArrSeq)this).uncheckedInsert(index,size,val);
      }else{
        ((ByteArrSeq)this).uncheckedInit(val);
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
    public byte getByte(int index)
    {
      if(index<0 || index>=this.size)
      {
        throw new IndexOutOfBoundsException("index="+index+"; size="+this.size);
      }
      return (byte)this.arr[index];
    }
    @Override
    public void put(int index,byte val)
    {
      if(index<0 || index>=this.size)
      {
        throw new IndexOutOfBoundsException("index="+index+"; size="+this.size);
      }
      this.arr[index]=val;
    }
    @Override
    public byte set(int index,byte val)
    {
      if(index<0 || index>=this.size)
      {
        throw new IndexOutOfBoundsException("index="+index+"; size="+this.size);
      }
      final byte[] arr;
      final var ret=(byte)(arr=this.arr)[index];
      arr[index]=val;
      return ret;
    }
    @Override
    public OmniList.OfByte subList(int fromIndex,int toIndex)
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
      implements ByteListDefault,Cloneable
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
      final byte[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(root.arr,rootOffset,copy=new byte[size],0,size);
      }
      else
      {
        copy=OmniArray.OfByte.DEFAULT_ARR;
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
          if(size<=(OmniArray.MAX_ARR_SIZE/6)){
            (buffer=new char[size*6])[size=OmniArray.OfByte.ascendingToString(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,buffer,1)]=']';
          }else{
            final ToStringUtil.OmniStringBuilder builder;
            OmniArray.OfByte.ascendingToString(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,builder=new ToStringUtil.OmniStringBuilder(1,new char[size*5]));
            (buffer=builder.buffer)[size=builder.size]=']';
          }
          buffer[0]='[';
          return new String(buffer,0,size+1);
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
        return OmniArray.OfByte.ascendingSeqHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
        root.size=OmniArray.OfByte.removeRangeAndPullDown(root.arr,this.rootOffset,root.size,size);
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
          return OmniArray.OfByte.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,TypeUtil.castToByte(val));
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
      if(val==(byte)val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfByte.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(val));
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
        final byte v;
        if((v=(byte)val)==val)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfByte.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,v);
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
        final byte v;
        if(val==(v=(byte)val))
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfByte.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,v);
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
        final byte v;
        if(val==(v=(byte)val))
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfByte.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,v);
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
        if(val instanceof Byte)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfByte.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(byte)(val));
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
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfByte.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(val));
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
      if(val<=Byte.MAX_VALUE)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfByte.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(val));
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
        return this.uncheckedremoveVal(size,TypeUtil.castToByte(val));
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean removeVal(int val)
    {
      if(val==(byte)val)
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
        final byte v;
        if((v=(byte)val)==val)
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
    public boolean removeVal(double val)
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
    public boolean remove(Object val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val instanceof Byte)
        {
          return this.uncheckedremoveVal(size,(byte)(val));
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean removeVal(byte val)
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
    public boolean removeVal(char val)
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
    public int indexOf(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          return OmniArray.OfByte.uncheckedindexOf(root.arr,this.rootOffset,size,TypeUtil.castToByte(val));
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
      if(val==(byte)val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfByte.uncheckedindexOf(root.arr,this.rootOffset,size,(val));
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
        final byte v;
        if((v=(byte)val)==val)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfByte.uncheckedindexOf(root.arr,this.rootOffset,size,v);
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
        final byte v;
        if(val==(v=(byte)val))
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfByte.uncheckedindexOf(root.arr,this.rootOffset,size,v);
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
        final byte v;
        if(val==(v=(byte)val))
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfByte.uncheckedindexOf(root.arr,this.rootOffset,size,v);
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
        if(val instanceof Byte)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfByte.uncheckedindexOf(root.arr,this.rootOffset,size,(byte)(val));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int indexOf(byte val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfByte.uncheckedindexOf(root.arr,this.rootOffset,size,(val));
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
      if(val<=Byte.MAX_VALUE)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfByte.uncheckedindexOf(root.arr,this.rootOffset,size,(val));
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
          return OmniArray.OfByte.uncheckedlastIndexOf(root.arr,this.rootOffset,size,TypeUtil.castToByte(val));
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
      if(val==(byte)val)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfByte.uncheckedlastIndexOf(root.arr,this.rootOffset,size,(val));
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
        final byte v;
        if((v=(byte)val)==val)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfByte.uncheckedlastIndexOf(root.arr,this.rootOffset,size,v);
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
        final byte v;
        if(val==(v=(byte)val))
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfByte.uncheckedlastIndexOf(root.arr,this.rootOffset,size,v);
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
        final byte v;
        if(val==(v=(byte)val))
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfByte.uncheckedlastIndexOf(root.arr,this.rootOffset,size,v);
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
        if(val instanceof Byte)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfByte.uncheckedlastIndexOf(root.arr,this.rootOffset,size,(byte)(val));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int lastIndexOf(byte val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfByte.uncheckedlastIndexOf(root.arr,this.rootOffset,size,(val));
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
      if(val<=Byte.MAX_VALUE)
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfByte.uncheckedlastIndexOf(root.arr,this.rootOffset,size,(val));
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
          OmniArray.OfByte.removeIndexAndPullDown(arr,index,--root.size);
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
    public void forEach(ByteConsumer action)
    {
      int modCount=this.modCount;
      final var root=this.root;
      try
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          OmniArray.OfByte.ascendingForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,action);
        }
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override
    public void forEach(Consumer<? super Byte> action)
    {
      int modCount=this.modCount;
      final var root=this.root;
      try
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          OmniArray.OfByte.ascendingForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,action::accept);
        }
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    private static class Itr
      extends AbstractByteItr
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
      public byte nextByte()
      {
        final CheckedList root;
        final CheckedSubList parent;
        CheckedCollection.checkModCount(modCount,(root=(parent=this.parent).root).modCount);
        final int cursor;
        if((cursor=this.cursor)<(parent.rootOffset+parent.size))
        {
          this.lastRet=cursor;
          this.cursor=cursor+1;
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
          OmniArray.OfByte.removeIndexAndPullDown(root.arr,lastRet,--root.size);
          this.cursor=lastRet;
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override
      public void forEachRemaining(ByteConsumer action)
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
            OmniArray.OfByte.ascendingForEach(root.arr,cursor,cursor=bound-1,action);
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
      public void forEachRemaining(Consumer<? super Byte> action)
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
            OmniArray.OfByte.ascendingForEach(root.arr,cursor,cursor=bound-1,action::accept);
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
    public OmniIterator.OfByte iterator()
    {
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new Itr(this);
    }
    private static class ListItr extends Itr implements OmniListIterator.OfByte
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
      public byte previousByte()
      {
        final CheckedList root;
        int cursor;
        final CheckedSubList parent;
        CheckedCollection.checkModCount(modCount,(root=(parent=this.parent).root).modCount);
        if((cursor=this.cursor)>parent.rootOffset)
        {
          this.lastRet=--cursor;
          this.cursor=cursor;
          return (byte)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override
      public void set(byte val)
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
      public void add(byte val)
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
          ((ByteArrSeq)root).uncheckedInsert(this.cursor++,rootSize,val);
        }
        else
        {
          ((ByteArrSeq)root).uncheckedInit(val);
          ++this.cursor;
        }
      }
    }
    @Override
    public OmniListIterator.OfByte listIterator()
    {
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new ListItr(this);
    }
    @Override
    public OmniListIterator.OfByte listIterator(int index)
    {
      CheckedCollection.checkModCount(modCount,root.modCount);
      if(index<0 || index>this.size)
      {
        throw new IndexOutOfBoundsException("index="+index+"; size="+this.size);
      }
      return new ListItr(this,index+this.rootOffset);
    }
    @Override
    public boolean add(Byte val){
      return add((byte)val);
    }
    @Override
    public boolean add(boolean val){
      return add((byte)TypeUtil.castToByte(val));
    }
    @Override
    public boolean add(byte val)
    {
      final CheckedList root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,++curr.size,curr=curr.parent){}
      if((modCount=root.size)!=0){
        ((ByteArrSeq)root).uncheckedInsert(this.rootOffset+(this.size++),modCount,val);
      }else{
        ((ByteArrSeq)root).uncheckedInit(val);
        ++this.size;
      }
      return true;
    }
    @Override
    public void add(int index,byte val)
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
        ((ByteArrSeq)root).uncheckedInsert(this.rootOffset+index,modCount,val);
      }else{
        ((ByteArrSeq)root).uncheckedInit(val);
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
    public byte[] toByteArray()
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0)
      {
        final byte[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new byte[size],0,size);
        return dst;
      }
      return OmniArray.OfByte.DEFAULT_ARR;
    }
    @Override
    public Byte[] toArray()
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0)
      {
        final Byte[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new Byte[size],0,size);
        return dst;
      }
      return OmniArray.OfByte.DEFAULT_BOXED_ARR;
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
    public short[] toShortArray()
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0)
      {
        final short[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new short[size],0,size);
        return dst;
      }
      return OmniArray.OfShort.DEFAULT_ARR;
    }
    @Override
    public byte getByte(int index)
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      if(index<0 || index>=this.size)
      {
        throw new IndexOutOfBoundsException("index="+index+"; size="+this.size);
      }
      return (byte)root.arr[index+this.rootOffset];
    }
    @Override
    public void put(int index,byte val)
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
    public byte set(int index,byte val)
    {
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      if(index<0 || index>=this.size)
      {
        throw new IndexOutOfBoundsException("index="+index+"; size="+this.size);
      }
      final byte[] arr;
      final var ret=(byte)(arr=root.arr)[index+=this.rootOffset];
      arr[index]=val;
      return ret;
    }
    @Override
    public OmniList.OfByte subList(int fromIndex,int toIndex)
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
