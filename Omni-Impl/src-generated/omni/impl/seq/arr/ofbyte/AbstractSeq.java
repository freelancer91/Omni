package omni.impl.seq.arr.ofbyte;
import omni.impl.CheckedCollection;
import omni.impl.seq.AbstractByteList;
import omni.impl.seq.arr.ArrSeqUtil;
import omni.util.OmniArray;
import omni.util.ArrCopy;
import java.util.function.IntFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import omni.function.ByteConsumer;
import omni.function.BytePredicate;
import omni.util.TypeUtil;
import omni.util.BitSetUtils;
abstract class AbstractSeq extends AbstractByteList
{
  transient byte[] arr;
  private AbstractSeq()
  {
    super();
    this.arr=OmniArray.OfByte.DEFAULT_ARR;
  }
  private AbstractSeq(int capacity)
  {
    super();
    switch(capacity){
    default:
      this.arr=new byte[capacity];
      return;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfByte.DEFAULT_ARR;
    case 0:
    }
  }
  private AbstractSeq(int size,byte[] arr)
  {
    super(size);
    this.arr=arr;
  }
  @Override
  public byte getByte(int index)
  {
    return arr[index];
  }
  @Override
  public byte set(int index,byte val)
  {
    final byte[] arr;
    final var oldVal=(arr=this.arr)[index];
    arr[index]=val;
    return oldVal;
  }
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
  public boolean contains(Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Byte)
      {
        return ArrSeqUtil.uncheckedcontains(arr,0,size,(byte)(val));
      }
    }
    return false;
  }
  public int indexOf(Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Byte)
      {
        return ArrSeqUtil.uncheckedindexOf(arr,size,(byte)(val));
      }
    }
    return -1;
  }
  public int search(Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Byte)
      {
        return ArrSeqUtil.uncheckedsearch(arr,size,(byte)(val));
      }
    }
    return -1;
  }
  public boolean contains(boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedcontains(arr,0,size,TypeUtil.castToByte(val));
    }
    return false;
  }
  public boolean removeVal(boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,TypeUtil.castToByte(val));
    }
    return false;
  }
  public int indexOf(boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedindexOf(arr,size,TypeUtil.castToByte(val));
    }
    return -1;
  }
  public int search(boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedsearch(arr,size,TypeUtil.castToByte(val));
    }
    return -1;
  }
  public boolean contains(final byte val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedcontains(arr,0,size,(val));
    }
    return false;
  }
  public boolean removeVal(final byte val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,(val));
    }
    return false;
  }
  public int indexOf(final byte val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedindexOf(arr,size,(val));
    }
    return -1;
  }
  public int search(final byte val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedsearch(arr,size,(val));
    }
    return -1;
  }
  public boolean contains(char val)
  {
    if(val<=Byte.MAX_VALUE)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return ArrSeqUtil.uncheckedcontains(arr,0,size,(val));
      }
    }
    return false;
  }
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
  public int indexOf(char val)
  {
    if(val<=Byte.MAX_VALUE)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return ArrSeqUtil.uncheckedindexOf(arr,size,(val));
      }
    }
    return -1;
  }
  public int search(char val)
  {
    if(val<=Byte.MAX_VALUE)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return ArrSeqUtil.uncheckedsearch(arr,size,(val));
      }
    }
    return -1;
  }
  public boolean contains(int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val==(byte)val)
      {
        return ArrSeqUtil.uncheckedcontains(arr,0,size,(val));
      }
    }
    return false;
  }
  public boolean removeVal(int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val==(byte)val)
      {
        return this.uncheckedremoveVal(size,(val));
      }
    }
    return false;
  }
  public int indexOf(int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val==(byte)val)
      {
        return ArrSeqUtil.uncheckedindexOf(arr,size,(val));
      }
    }
    return -1;
  }
  public int search(int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val==(byte)val)
      {
        return ArrSeqUtil.uncheckedsearch(arr,size,(val));
      }
    }
    return -1;
  }
  public boolean contains(long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final byte v;
      if(val==(v=(byte)val))
      {
        return ArrSeqUtil.uncheckedcontains(arr,0,size,v);
      }
    }
    return false;
  }
  public boolean removeVal(long val)
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
  public int indexOf(long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final byte v;
      if(val==(v=(byte)val))
      {
        return ArrSeqUtil.uncheckedindexOf(arr,size,v);
      }
    }
    return -1;
  }
  public int search(long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final byte v;
      if(val==(v=(byte)val))
      {
        return ArrSeqUtil.uncheckedsearch(arr,size,v);
      }
    }
    return -1;
  }
  public boolean contains(float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final byte v;
      if(val==(v=(byte)val))
      {
        return ArrSeqUtil.uncheckedcontains(arr,0,size,v);
      }
    }
    return false;
  }
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
  public int indexOf(float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final byte v;
      if(val==(v=(byte)val))
      {
        return ArrSeqUtil.uncheckedindexOf(arr,size,v);
      }
    }
    return -1;
  }
  public int search(float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final byte v;
      if(val==(v=(byte)val))
      {
        return ArrSeqUtil.uncheckedsearch(arr,size,v);
      }
    }
    return -1;
  }
  public boolean contains(double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final byte v;
      if(val==(v=(byte)val))
      {
        return ArrSeqUtil.uncheckedcontains(arr,0,size,v);
      }
    }
    return false;
  }
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
  public int indexOf(double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final byte v;
      if(val==(v=(byte)val))
      {
        return ArrSeqUtil.uncheckedindexOf(arr,size,v);
      }
    }
    return -1;
  }
  public int search(double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final byte v;
      if(val==(v=(byte)val))
      {
        return ArrSeqUtil.uncheckedsearch(arr,size,v);
      }
    }
    return -1;
  }
  public void forEach(ByteConsumer action)
  {
    final int size;
    if((size=this.size)!=0)
    {
      uncheckedForEach(size,action);
    }
  }
  abstract void uncheckedForEach(int size,ByteConsumer action);
  public boolean removeIf(BytePredicate filter)
  {
    final int size;
    return (size=this.size)!=0 && uncheckedRemoveIf(size,filter);
  }
  abstract boolean uncheckedRemoveIf(int size,BytePredicate filter);
  public void forEach(Consumer<? super Byte> action)
  {
    final int size;
    if((size=this.size)!=0)
    {
      uncheckedForEach(size,action::accept);
    }
  }
  public boolean removeIf(Predicate<? super Byte> filter)
  {
    final int size;
    return (size=this.size)!=0 && uncheckedRemoveIf(size,filter::test);
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
  abstract int uncheckedHashCode(int size);  
  @Override
  public String toString()
  {
    final int size;
    if((size=this.size)!=0){
      final StringBuilder builder;
      uncheckedToString(size,builder=new StringBuilder("["));
      return builder.append(']').toString();
    }
    return "[]";
  }
  abstract void uncheckedToString(int size,StringBuilder builder);
  public Byte peek()
  {
    final int size;
    if((size=this.size)!=0)
    {
      return (Byte)(arr[size-1]);
    }
    return null;
  }
  public double peekDouble()
  {
    final int size;
    if((size=this.size)!=0)
    {
      return (double)(arr[size-1]);
    }
    return Double.NaN;
  }
  public float peekFloat()
  {
    final int size;
    if((size=this.size)!=0)
    {
      return (float)(arr[size-1]);
    }
    return Float.NaN;
  }
  public long peekLong()
  {
    final int size;
    if((size=this.size)!=0)
    {
      return (long)(arr[size-1]);
    }
    return Long.MIN_VALUE;
  }
  public int peekInt()
  {
    final int size;
    if((size=this.size)!=0)
    {
      return (int)(arr[size-1]);
    }
    return Integer.MIN_VALUE;
  }
  public short peekShort()
  {
    final int size;
    if((size=this.size)!=0)
    {
      return (short)(arr[size-1]);
    }
    return Short.MIN_VALUE;
  }
  public byte peekByte()
  {
    final int size;
    if((size=this.size)!=0)
    {
      return (byte)(arr[size-1]);
    }
    return Byte.MIN_VALUE;
  }
  public void push(byte val)
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
  private byte[] growInsert(byte[] arr,int index,int size){
    if(arr.length==size)
    {
      ArrCopy.semicheckedCopy(arr,0,arr=new byte[OmniArray.growBy50Pct(size)],0,index);
      this.arr=arr;
    }
    return arr;
  }
  private void uncheckedAppend(byte val,int size){
    byte[] arr;
    if((arr=this.arr).length==size)
    {
      ArrCopy.uncheckedCopy(arr,0,arr=new byte[OmniArray.growBy50Pct(size)],0,size);
    }
    arr[size]=val;
    this.size=size+1;
  }
  private void uncheckedInit(byte val){
    byte[] arr;
    if((arr=this.arr)==OmniArray.OfByte.DEFAULT_ARR)
    {
      this.arr=arr=new byte[OmniArray.DEFAULT_ARR_SEQ_CAP];
    }
    else if(arr==null)
    {
      this.arr=arr=new byte[1];
    }
    arr[0]=val;
    size=1;
  }
  private void uncheckedInsert(int index,byte val,int size){
    final int tailDist;
    if((tailDist=size-index)==0){
      uncheckedAppend(val,size);
    }else{
      byte[] arr;
      ArrCopy.uncheckedCopy(arr=this.arr,index,arr=growInsert(arr,index,size),index+1,tailDist);
      arr[index]=val;
      this.size=size+1;
    }
  }
  abstract boolean uncheckedremoveVal(int size,int val);
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
  public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    final T[] dst=arrConstructor.apply(size=this.size);
    if(size!=0){
      uncheckedCopyInto(dst,size);
    }
    return dst;
  }
  public <T> T[] toArray(T[] dst){
    final int size;
    if((size=this.size)!=0){
      uncheckedCopyInto(dst=OmniArray.uncheckedArrResize(size,dst),size);
    }else if(dst.length!=0){
      dst[0]=null;
    }
    return dst;
  }
  abstract void uncheckedCopyInto(Byte[] dst,int length);
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
  abstract void uncheckedCopyInto(Object[] dst,int length);
  abstract void uncheckedCopyInto(double[] dst,int size);
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
  abstract void uncheckedCopyInto(float[] dst,int size);
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
  abstract void uncheckedCopyInto(long[] dst,int size);
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
  abstract void uncheckedCopyInto(int[] dst,int size);
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
  abstract void uncheckedCopyInto(short[] dst,int size);
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
  abstract void uncheckedCopyInto(byte[] dst,int size);
  @Override
  protected int uncheckedlastIndexOf(int size,int val)
  {
    return ArrSeqUtil.uncheckedlastIndexOf(arr,size,val);
  }
  byte uncheckedPop(int newSize)
  {
    this.size=newSize;
    return arr[newSize];
  }
  private int finalizeSubListBatchRemove(byte[] arr,int newBound,int oldBound){
    final int newRootSize,numRemoved;
    size=newRootSize=size-(numRemoved=oldBound-newBound);
    ArrCopy.semicheckedCopy(arr,oldBound,arr,newBound,newRootSize-newBound);
    return numRemoved;
  }
  static abstract class Checked extends AbstractSeq
  {
    transient int modCount;
    Checked()
    {
      super();
    }
    Checked(int capacity)
    {
      super(capacity);
    }
    Checked(int size,byte[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean add(byte val)
    {
      ++modCount;
      super.push(val);
      return true;
    }
    @Override
    public byte removeByteAt(int index)
    {
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ++modCount;
      final byte[] arr;
      final var removed=(byte)(arr=this.arr)[index];
      ArrSeqUtil.eraseIndexHelper(arr,index,--size);
      this.size=size;
      return removed;
    }
    @Override
    public void add(int index,byte val)
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
      if(size!=0)
      {
        ++modCount;
        this.size=0;
      }
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      return super.toArray(size->{
        final int modCount=this.modCount;
        try{
          return arrConstructor.apply(size);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      });
    }
    @Override
    boolean uncheckedRemoveIf(int size,BytePredicate filter)
    {
      int srcOffset;
      final int modCount=this.modCount;
      try
      {
        srcOffset=0;
        final var arr=this.arr;
        for(;;)
        {
          if(filter.test((byte)arr[srcOffset]))
          {
            break;
          }
          if(++srcOffset==size)
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
            return false;
          }
        }
        int dstOffset=srcOffset;
        for(;;)
        {
          if(++srcOffset==size)
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
            break;
          }
          final byte v;
          if(!filter.test((byte)(v=arr[srcOffset])))
          {
            final long[] survivors;
            int numSurvivors;
            if((numSurvivors=size-++srcOffset)!=0)
            {
              numSurvivors=ArrSeqUtil.markSurvivors(arr,survivors=BitSetUtils.getBitSet(numSurvivors),srcOffset,size,filter);
              CheckedCollection.checkModCount(modCount,this.modCount);
              arr[dstOffset++]=v;
              if(numSurvivors!=0)
              {
                dstOffset=ArrSeqUtil.pullSurvivorsDown(arr,survivors,dstOffset,srcOffset,numSurvivors);
              }
            }
            else
            {
              CheckedCollection.checkModCount(modCount,this.modCount);
              arr[dstOffset++]=v;
            }
			break;
          }
        }
        this.size=size-1;
      }
      catch(RuntimeException e)
      {
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
      return true;
    }
    static abstract class AbstractBidirectionalItr
    {
      transient int lastRet=-1;
      transient final Checked root;
      transient int cursor;
      transient int modCount;
      AbstractBidirectionalItr(Checked root)
      {
        this.root=root;
        this.modCount=root.modCount;
      }
      AbstractBidirectionalItr(Checked root,int cursor)
      {
        this.root=root;
        this.modCount=root.modCount;
        this.cursor=cursor;
      }
      public void add(byte val)
      {
        final Checked root;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        final int rootSize,cursor=this.cursor;
        if((rootSize=root.size)!=0)
        {
          ((AbstractSeq)root).uncheckedInsert(cursor,val,rootSize);
        }
        else
        {
          ((AbstractSeq)root).uncheckedInit(val);
        }
        root.modCount=++modCount;
        this.modCount=modCount;
        this.cursor=cursor+1;
        this.lastRet=-1;
      }
    }
    static abstract class AbstractSubList extends AbstractByteList
    {
      private static  void bubbleUpDecrementSize(AbstractSubList parent,int numToRemove)
      {
        while(parent!=null){
          parent.size-=numToRemove;
          ++parent.modCount;
          parent=parent.parent;
        }
      }
      private static  void bubbleUpIncrementSize(AbstractSubList parent){
        while(parent!=null){
          ++parent.size;
          ++parent.modCount;
          parent=parent.parent;
        }
      }
      transient final Checked root;
      transient final AbstractSubList parent;
      transient final int rootOffset;
      transient int modCount;
      AbstractSubList(Checked root,AbstractSubList parent,int rootOffset,int size,int modCount)
      {
        super(size);
        this.root=root;
        this.parent=parent;
        this.rootOffset=rootOffset;
        this.modCount=modCount;
      }
      AbstractSubList(Checked root,int rootOffset,int size)
      {
        super(size);
        this.root=root;
        this.parent=null;
        this.rootOffset=rootOffset;
        this.modCount=root.modCount;
      }
      @Override
      protected int uncheckedlastIndexOf(int size,int val){
        Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        final int rootOffset;
        return ArrSeqUtil.uncheckedlastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
      }
      @Override
      public boolean add(byte val)
      {
        final Checked root;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        final int rootSize;
        final int size=this.size;
        if((rootSize=root.size)!=0)
        {
          ((AbstractSeq)root).uncheckedInsert(rootOffset+size,val,rootSize);
        }
        else
        {
          ((AbstractSeq)root).uncheckedInit(val);
        }
        root.modCount=++modCount;
        this.modCount=modCount;
        bubbleUpIncrementSize(parent);
        this.size=size+1;
        return true;
      }
      @Override
      public void add(int index,byte val)
      {
        final Checked root;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        CheckedCollection.checkLo(index);
        final int size;
        CheckedCollection.checkWriteHi(index,size=this.size);
        final int rootSize;
        if((rootSize=root.size)!=0)
        {
          ((AbstractSeq)root).uncheckedInsert(rootOffset+index,val,rootSize);
        }
        else
        {
          ((AbstractSeq)root).uncheckedInit(val);
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
          root.size=newRootSize=root.size-size;
          final byte[] arr;
          final int rootOffset;
          ArrCopy.semicheckedCopy(arr=root.arr,(rootOffset=this.rootOffset)+size,arr,rootOffset,newRootSize-rootOffset);
        }
        else
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      @Override
      public boolean isEmpty()
      {
        CheckedCollection.checkModCount(this.modCount,root.modCount);
        return size==0;
      }
      @Override
      public int size()
      {
        CheckedCollection.checkModCount(this.modCount,root.modCount);
        return size;
      }
      public boolean removeIf(BytePredicate filter)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return uncheckedRemoveIf(size,filter);
        }
        CheckedCollection.checkModCount(this.modCount,root.modCount);
        return false;
      }
      public boolean removeIf(Predicate<? super Byte> filter)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return uncheckedRemoveIf(size,filter::test);
        }
        CheckedCollection.checkModCount(this.modCount,root.modCount);
        return false;
      }
      private boolean uncheckedRemoveIf(int size,BytePredicate filter)
      {
        final Checked root=this.root;
        int modCount=this.modCount;
        int srcOffset;
        final int srcBound=(srcOffset=rootOffset)+size;
        int dstOffset;
        try
        {
          final var arr=root.arr;
          for(;;)
          {
            if(filter.test((byte)arr[srcOffset]))
            {
              break;
            }
            if(++srcOffset==srcBound)
            {
              CheckedCollection.checkModCount(modCount,root.modCount);
              return false;
            }
          }
          dstOffset=srcOffset;
          for(;;)
          {
            if(++srcOffset==srcBound)
            {
              CheckedCollection.checkModCount(modCount,root.modCount);
              break;
            }
            final byte v;
            if(!filter.test((byte)(v=arr[srcOffset])))
            {
              final long[] survivors;
              int numSurvivors;
              if((numSurvivors=size-++srcOffset)!=0)
              {
                numSurvivors=ArrSeqUtil.markSurvivors(arr,survivors=BitSetUtils.getBitSet(numSurvivors),srcOffset,srcBound,filter);
                CheckedCollection.checkModCount(modCount,root.modCount);
                arr[dstOffset++]=v;
                if(numSurvivors!=0)
                {
                  dstOffset=ArrSeqUtil.pullSurvivorsDown(arr,survivors,dstOffset,srcOffset,numSurvivors);
                }
              }
              else
              {
                CheckedCollection.checkModCount(modCount,root.modCount);
                arr[dstOffset++]=v;
              }
			  break;
            }
          }
          root.modCount=++modCount;
          this.modCount=modCount;
          this.size=size-(size=((AbstractSeq)root).finalizeSubListBatchRemove(arr,dstOffset,srcBound));
          bubbleUpDecrementSize(parent,size);
        }
        catch(RuntimeException e)
        {
          throw CheckedCollection.checkModCount(modCount,root.modCount,e);
        }
        return true;
      }
      static abstract class AbstractBidirectionalItr
      {
        transient final AbstractSubList parent;
        transient int cursor;
        transient int lastRet=-1;
        transient int modCount;
        AbstractBidirectionalItr(AbstractSubList parent,int modCount)
        {
          this.parent=parent;
          this.cursor=parent.rootOffset;
          this.modCount=modCount;
        }
        AbstractBidirectionalItr(AbstractSubList parent,int modCount,int cursor){
          this.parent=parent;
          this.cursor=cursor;
          this.modCount=modCount;
        }
        public void add(byte val)
        {
          final AbstractSubList parent;
          final Checked root;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
          final int rootSize;
          final int cursor=this.cursor;
          if((rootSize=root.size)!=0){
            ((AbstractSeq)root).uncheckedInsert(cursor,val,rootSize);
          }else{
            ((AbstractSeq)root).uncheckedInit(val);
          }
          bubbleUpIncrementSize(parent.parent);
          parent.modCount=++modCount;
          root.modCount=modCount;
          ++parent.size;
          this.cursor=cursor+1;
          this.lastRet=-1;
        }
      }
    }
  }
  static abstract class Unchecked extends AbstractSeq
  {
    Unchecked(){
      super();
    }
    Unchecked(int capacity){
      super(capacity);
    }
    Unchecked(int size,byte[] arr){
      super(size,arr);
    }
    @Override
    public boolean add(byte val)
    {
      super.push(val);
      return true;
    }
    @Override
    public void add(int index,byte val)
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
    public byte removeByteAt(int index)
    {
      final byte[] arr;
      final var removed=(byte)(arr=this.arr)[index];
      ArrSeqUtil.eraseIndexHelper(arr,index,--size);
      return removed;
    }
    @Override
    boolean uncheckedRemoveIf(int size,BytePredicate filter)
    {
      int srcOffset;
      srcOffset=0;
      while(!filter.test((byte)arr[srcOffset]))
      {
        if(++srcOffset==size)
        {
          return false;
        }
      }
      this.size=ArrSeqUtil.pullSurvivorsDown(arr,srcOffset,size-1,filter);
      return true;
    }
    static abstract class AbstractBidirectionalItr{
      transient final Unchecked root;
      transient int cursor;
      transient int lastRet;
      AbstractBidirectionalItr(Unchecked root){
        this.root=root;
      }
      AbstractBidirectionalItr(Unchecked root,int cursor){
        this.root=root;
        this.cursor=cursor;
      }
      public void add(byte val){
        final AbstractSeq root;
        final int rootSize,cursor=this.cursor;
        if((rootSize=(root=this.root).size)!=0){
          root.uncheckedInsert(cursor,val,rootSize);
        }else{
          root.uncheckedInit(val);
        }
        this.cursor=cursor+1;
      }
    }
    static abstract class AbstractSubList extends AbstractByteList
    {
      private static  void bubbleUpDecrementSize(AbstractSubList parent,int numToRemove)
      {
        while(parent!=null)
        {
          parent.size-=numToRemove;
          parent=parent.parent;
        }
      }
      private static  void bubbleUpIncrementSize(AbstractSubList parent)
      {
        while(parent!=null){
          ++parent.size;
          parent=parent.parent;
        }
      }
      static  void bubbleUpDecrementSize(AbstractSubList parent){
        while(parent!=null){
          --parent.size;
          parent=parent.parent;
        }
      }
      transient final Unchecked root;
      transient final AbstractSubList parent;
      transient final int rootOffset;
      AbstractSubList(Unchecked root,AbstractSubList parent,int rootOffset,int size)
      {
        super(size);
        this.root=root;
        this.parent=parent;
        this.rootOffset=rootOffset;
      }
      AbstractSubList(Unchecked root,int rootOffset,int size)
      {
        super(size);
        this.root=root;
        this.parent=null;
        this.rootOffset=rootOffset;
      }
      @Override
      public boolean add(byte val)
      {
        final AbstractSeq root;
        final int rootSize;
        final int size=this.size;
        if((rootSize=(root=this.root).size)!=0){
          root.uncheckedInsert(size+rootOffset,val,rootSize);
        }else{
          root.uncheckedInit(val);
        }
        this.size=size+1;
        bubbleUpIncrementSize(parent);
        return true;
      }
      @Override
      public void add(int index,byte val){
        final AbstractSeq root;
        final int rootSize;
        if((rootSize=(root=this.root).size)!=0){
          root.uncheckedInsert(index+rootOffset,val,rootSize);
        }else{
          root.uncheckedInit(val);
        }
        ++size;
        bubbleUpIncrementSize(parent);
      }
      @Override
      public void clear(){
        int size;
        if((size=this.size)!=0){
          this.size=0;
          bubbleUpDecrementSize(parent,size);
          final int newRootSize;
          final Unchecked root;
          (root=this.root).size=newRootSize=root.size-size;
          final byte[] arr;
          ArrCopy.semicheckedCopy(arr=root.arr,size+(size=rootOffset),arr,size,newRootSize-size);
        }
      }
      public boolean removeIf(BytePredicate filter)
      {
        final int size;
        return (size=this.size)!=0 && uncheckedRemoveIf(size,filter);
      }
      public boolean removeIf(Predicate<? super Byte> filter)
      {
        final int size;
        return (size=this.size)!=0 && uncheckedRemoveIf(size,filter::test);
      }
      private boolean uncheckedRemoveIf(int size,BytePredicate filter)
      {
        final Unchecked root;
        int srcOffset;
        int srcBound=(srcOffset=rootOffset)+size;
        final var arr=(root=this.root).arr;
        while(!filter.test((byte)arr[srcOffset]))
        {
          if(++srcOffset==srcBound)
          {
            return false;
          }
        }
        this.size=size-(size=((AbstractSeq)root).finalizeSubListBatchRemove(arr,ArrSeqUtil.pullSurvivorsDown(arr,srcOffset,srcBound-1,filter),srcBound));
        bubbleUpDecrementSize(parent,size);
        return true;
      }
      static abstract class AbstractBidirectionalItr
      {
        transient final AbstractSubList parent;
        transient int cursor;
        transient int lastRet;
        AbstractBidirectionalItr(AbstractSubList parent)
        {
          this.parent=parent;
          this.cursor=parent.rootOffset;
        }
        AbstractBidirectionalItr(AbstractSubList parent,int cursor)
        {
          this.parent=parent;
          this.cursor=cursor;
        }
        public void add(byte val)
        {
          final AbstractSubList parent;
          final AbstractSeq root;
          final int rootSize,cursor=this.cursor;
          if((rootSize=(root=(parent=this.parent).root).size)!=0){
            root.uncheckedInsert(cursor,val,rootSize);
          }else{
            root.uncheckedInit(val);
          }
          bubbleUpIncrementSize(parent.parent);
          ++parent.size;
          this.cursor=cursor+1;
        }
      }
    }
  }
}
