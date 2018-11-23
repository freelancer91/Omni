package omni.impl.seq.arr.offloat;
import omni.impl.CheckedCollection;
import omni.impl.seq.AbstractFloatList;
import omni.impl.seq.arr.ArrSeqUtil;
import omni.util.OmniArray;
import omni.util.ArrCopy;
import java.util.function.IntFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import omni.function.FloatConsumer;
import omni.function.FloatPredicate;
import omni.util.TypeUtil;
import omni.util.BitSetUtils;
abstract class AbstractSeq extends AbstractFloatList
{
  transient float[] arr;
  private AbstractSeq()
  {
    super();
    this.arr=OmniArray.OfFloat.DEFAULT_ARR;
  }
  private AbstractSeq(int capacity)
  {
    super();
    switch(capacity){
    default:
      this.arr=new float[capacity];
      return;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfFloat.DEFAULT_ARR;
    case 0:
    }
  }
  private AbstractSeq(int size,float[] arr)
  {
    super(size);
    this.arr=arr;
  }
  @Override
  public float getFloat(int index)
  {
    return arr[index];
  }
  @Override
  public float set(int index,float val)
  {
    final float[] arr;
    final var oldVal=(arr=this.arr)[index];
    arr[index]=val;
    return oldVal;
  }
  public boolean remove(Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Float)
      {
        return this.uncheckedremoveVal(size,(float)(val));
      }
    }
    return false;
  }
  public boolean contains(Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Float)
      {
        return ArrSeqUtil.uncheckedcontains(arr,0,size,(float)(val));
      }
    }
    return false;
  }
  public int indexOf(Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Float)
      {
        return ArrSeqUtil.uncheckedindexOf(arr,size,(float)(val));
      }
    }
    return -1;
  }
  public int search(Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val instanceof Float)
      {
        return ArrSeqUtil.uncheckedsearch(arr,size,(float)(val));
      }
    }
    return -1;
  }
  public boolean contains(boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val)
      {
        return ArrSeqUtil.uncheckedcontainsBits(arr,0,size,TypeUtil.FLT_TRUE_BITS);
      }
      return ArrSeqUtil.uncheckedcontains0(arr,0,size);
    }
    return false;
  }
  public boolean removeVal(boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val)
      {
        return this.uncheckedremoveValBits(size,TypeUtil.FLT_TRUE_BITS);
      }
      return this.uncheckedremoveVal0(size);
    }
    return false;
  }
  public int indexOf(boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val)
      {
        return ArrSeqUtil.uncheckedindexOfBits(arr,size,TypeUtil.FLT_TRUE_BITS);
      }
      return ArrSeqUtil.uncheckedindexOf0(arr,size);
    }
    return -1;
  }
  public int search(boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val)
      {
        return ArrSeqUtil.uncheckedsearchBits(arr,size,TypeUtil.FLT_TRUE_BITS);
      }
      return ArrSeqUtil.uncheckedsearch0(arr,size);
    }
    return -1;
  }
  public int indexOf(char val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return ArrSeqUtil.uncheckedindexOfRawInt(arr,size,val);
      }
    }
    return -1;
  }
  public int search(char val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return ArrSeqUtil.uncheckedsearchRawInt(arr,size,val);
      }
    }
    return -1;
  }
  public int indexOf(short val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return ArrSeqUtil.uncheckedindexOfRawInt(arr,size,val);
      }
    }
    return -1;
  }
  public int search(short val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return ArrSeqUtil.uncheckedsearchRawInt(arr,size,val);
      }
    }
    return -1;
  }
  public boolean contains(int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=0)
      {
        if(TypeUtil.checkCastToFloat(val))
        {
          return ArrSeqUtil.uncheckedcontainsBits(arr,0,size,Float.floatToRawIntBits(val));
        }
      }
      else
      {
        return ArrSeqUtil.uncheckedcontains0(arr,0,size);
      }
    }
    return false;
  }
  public boolean removeVal(int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=0)
      {
        if(TypeUtil.checkCastToFloat(val))
        {
          return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
        }
      }
      else
      {
        return this.uncheckedremoveVal0(size);
      }
    }
    return false;
  }
  public int indexOf(int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=0)
      {
        if(TypeUtil.checkCastToFloat(val))
        {
          return ArrSeqUtil.uncheckedindexOfBits(arr,size,Float.floatToRawIntBits(val));
        }
      }
      else
      {
        return ArrSeqUtil.uncheckedindexOf0(arr,size);
      }
    }
    return -1;
  }
  public int search(int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=0)
      {
        if(TypeUtil.checkCastToFloat(val))
        {
          return ArrSeqUtil.uncheckedsearchBits(arr,size,Float.floatToRawIntBits(val));
        }
      }
      else
      {
        return ArrSeqUtil.uncheckedsearch0(arr,size);
      }
    }
    return -1;
  }
  public boolean contains(long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=0)
      {
        if(TypeUtil.checkCastToFloat(val))
        {
          return ArrSeqUtil.uncheckedcontainsBits(arr,0,size,Float.floatToRawIntBits(val));
        }
      }
      else
      {
         return ArrSeqUtil.uncheckedcontains0(arr,0,size);
      }
    }
    return false;
  }
  public boolean removeVal(long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=0)
      {
        if(TypeUtil.checkCastToFloat(val))
        {
          return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
        }
      }
      else
      {
         return this.uncheckedremoveVal0(size);
      }
    }
    return false;
  }
  public int indexOf(long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=0)
      {
        if(TypeUtil.checkCastToFloat(val))
        {
          return ArrSeqUtil.uncheckedindexOfBits(arr,size,Float.floatToRawIntBits(val));
        }
      }
      else
      {
         return ArrSeqUtil.uncheckedindexOf0(arr,size);
      }
    }
    return -1;
  }
  public int search(long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=0)
      {
        if(TypeUtil.checkCastToFloat(val))
        {
          return ArrSeqUtil.uncheckedsearchBits(arr,size,Float.floatToRawIntBits(val));
        }
      }
      else
      {
         return ArrSeqUtil.uncheckedsearch0(arr,size);
      }
    }
    return -1;
  }
  public boolean contains(float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedcontains(arr,0,size,(val));
    }
    return false;
  }
  public boolean removeVal(float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,(val));
    }
    return false;
  }
  public int indexOf(float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedindexOf(arr,size,(val));
    }
    return -1;
  }
  public int search(float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedsearch(arr,size,(val));
    }
    return -1;
  }
  public boolean contains(double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final float v;
      if(val==(v=(float)val))
      {
        return ArrSeqUtil.uncheckedcontainsBits(arr,0,size,Float.floatToRawIntBits(v));
      }
      else if(v!=v)
      {
        return ArrSeqUtil.uncheckedcontainsNaN(arr,0,size);
      }
    }
    return false;
  }
  public boolean removeVal(double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final float v;
      if(val==(v=(float)val))
      {
        return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(v));
      }
      else if(v!=v)
      {
        return this.uncheckedremoveValNaN(size);
      }
    }
    return false;
  }
  public int indexOf(double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final float v;
      if(val==(v=(float)val))
      {
        return ArrSeqUtil.uncheckedindexOfBits(arr,size,Float.floatToRawIntBits(v));
      }
      else if(v!=v)
      {
        return ArrSeqUtil.uncheckedindexOfNaN(arr,size);
      }
    }
    return -1;
  }
  public int search(double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      final float v;
      if(val==(v=(float)val))
      {
        return ArrSeqUtil.uncheckedsearchBits(arr,size,Float.floatToRawIntBits(v));
      }
      else if(v!=v)
      {
        return ArrSeqUtil.uncheckedsearchNaN(arr,size);
      }
    }
    return -1;
  }
  public void forEach(FloatConsumer action)
  {
    final int size;
    if((size=this.size)!=0)
    {
      uncheckedForEach(size,action);
    }
  }
  abstract void uncheckedForEach(int size,FloatConsumer action);
  public boolean removeIf(FloatPredicate filter)
  {
    final int size;
    return (size=this.size)!=0 && uncheckedRemoveIf(size,filter);
  }
  abstract boolean uncheckedRemoveIf(int size,FloatPredicate filter);
  public void forEach(Consumer<? super Float> action)
  {
    final int size;
    if((size=this.size)!=0)
    {
      uncheckedForEach(size,action::accept);
    }
  }
  public boolean removeIf(Predicate<? super Float> filter)
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
  public Float peek()
  {
    final int size;
    if((size=this.size)!=0)
    {
      return (Float)(arr[size-1]);
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
  public void push(float val)
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
  private float[] growInsert(float[] arr,int index,int size){
    if(arr.length==size)
    {
      ArrCopy.semicheckedCopy(arr,0,arr=new float[OmniArray.growBy50Pct(size)],0,index);
      this.arr=arr;
    }
    return arr;
  }
  private void uncheckedAppend(float val,int size){
    float[] arr;
    if((arr=this.arr).length==size)
    {
      ArrCopy.uncheckedCopy(arr,0,arr=new float[OmniArray.growBy50Pct(size)],0,size);
    }
    arr[size]=val;
    this.size=size+1;
  }
  private void uncheckedInit(float val){
    float[] arr;
    if((arr=this.arr)==OmniArray.OfFloat.DEFAULT_ARR)
    {
      this.arr=arr=new float[OmniArray.DEFAULT_ARR_SEQ_CAP];
    }
    else if(arr==null)
    {
      this.arr=arr=new float[1];
    }
    arr[0]=val;
    size=1;
  }
  private void uncheckedInsert(int index,float val,int size){
    final int tailDist;
    if((tailDist=size-index)==0){
      uncheckedAppend(val,size);
    }else{
      float[] arr;
      ArrCopy.uncheckedCopy(arr=this.arr,index,arr=growInsert(arr,index,size),index+1,tailDist);
      arr[index]=val;
      this.size=size+1;
    }
  }
  abstract boolean uncheckedremoveVal0(int size);
  abstract boolean uncheckedremoveValBits(int size,int bits);
  abstract boolean uncheckedremoveValNaN(int size);
  private boolean uncheckedremoveVal(int size,float val)
  {
    if(val==val)
    {
      return uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
    }
    return uncheckedremoveValNaN(size);
  }
  @Override
  protected boolean containsRawInt(int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedcontainsRawInt(arr,0,size,val);
    }
    return false;
  }
  @Override
  protected boolean removeRawInt(int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
       if(val!=0)
       {
         return uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
       }
       return uncheckedremoveVal0(size);
    }
    return false;
  }
  public Float[] toArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final Float[] dst;
      uncheckedCopyInto(dst=new Float[size],size);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_BOXED_ARR;
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
  abstract void uncheckedCopyInto(Float[] dst,int length);
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
  @Override
  protected int uncheckedlastIndexOf0(int size)
  {
    return ArrSeqUtil.uncheckedlastIndexOf0(arr,size);
  }
  @Override
  protected int uncheckedlastIndexOfBits(int size,int bits)
  {
    return ArrSeqUtil.uncheckedlastIndexOfBits(arr,size,bits);
  }
  @Override
  protected int uncheckedlastIndexOfNaN(int size)
  {
    return ArrSeqUtil.uncheckedlastIndexOfNaN(arr,size);
  }
  float uncheckedPop(int newSize)
  {
    this.size=newSize;
    return arr[newSize];
  }
  private int finalizeSubListBatchRemove(float[] arr,int newBound,int oldBound){
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
    Checked(int size,float[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean add(float val)
    {
      ++modCount;
      super.push(val);
      return true;
    }
    @Override
    public float removeFloatAt(int index)
    {
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ++modCount;
      final float[] arr;
      final var removed=(float)(arr=this.arr)[index];
      ArrSeqUtil.eraseIndexHelper(arr,index,--size);
      this.size=size;
      return removed;
    }
    @Override
    public void add(int index,float val)
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
    boolean uncheckedRemoveIf(int size,FloatPredicate filter)
    {
      int srcOffset;
      final int modCount=this.modCount;
      try
      {
        srcOffset=0;
        final var arr=this.arr;
        for(;;)
        {
          if(filter.test((float)arr[srcOffset]))
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
          final float v;
          if(!filter.test((float)(v=arr[srcOffset])))
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
      public void add(float val)
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
    static abstract class AbstractSubList extends AbstractFloatList
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
      protected int uncheckedlastIndexOf0(int size){
        Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        final int rootOffset;
        return ArrSeqUtil.uncheckedlastIndexOf0(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }
      @Override
      protected int uncheckedlastIndexOfBits(int size,int bits){
        Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        final int rootOffset;
        return ArrSeqUtil.uncheckedlastIndexOfBits(root.arr,rootOffset=this.rootOffset,rootOffset+size,bits);
      }
      @Override
      protected int uncheckedlastIndexOfNaN(int size){
        Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        final int rootOffset;
        return ArrSeqUtil.uncheckedlastIndexOfNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }
      @Override
      public boolean add(float val)
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
      public void add(int index,float val)
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
          final float[] arr;
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
      public boolean removeIf(FloatPredicate filter)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return uncheckedRemoveIf(size,filter);
        }
        CheckedCollection.checkModCount(this.modCount,root.modCount);
        return false;
      }
      public boolean removeIf(Predicate<? super Float> filter)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return uncheckedRemoveIf(size,filter::test);
        }
        CheckedCollection.checkModCount(this.modCount,root.modCount);
        return false;
      }
      private boolean uncheckedRemoveIf(int size,FloatPredicate filter)
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
            if(filter.test((float)arr[srcOffset]))
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
            final float v;
            if(!filter.test((float)(v=arr[srcOffset])))
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
        public void add(float val)
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
    Unchecked(int size,float[] arr){
      super(size,arr);
    }
    @Override
    public boolean add(float val)
    {
      super.push(val);
      return true;
    }
    @Override
    public void add(int index,float val)
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
    public float removeFloatAt(int index)
    {
      final float[] arr;
      final var removed=(float)(arr=this.arr)[index];
      ArrSeqUtil.eraseIndexHelper(arr,index,--size);
      return removed;
    }
    @Override
    boolean uncheckedRemoveIf(int size,FloatPredicate filter)
    {
      int srcOffset;
      srcOffset=0;
      while(!filter.test((float)arr[srcOffset]))
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
      public void add(float val){
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
    static abstract class AbstractSubList extends AbstractFloatList
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
      public boolean add(float val)
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
      public void add(int index,float val){
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
          final float[] arr;
          ArrCopy.semicheckedCopy(arr=root.arr,size+(size=rootOffset),arr,size,newRootSize-size);
        }
      }
      public boolean removeIf(FloatPredicate filter)
      {
        final int size;
        return (size=this.size)!=0 && uncheckedRemoveIf(size,filter);
      }
      public boolean removeIf(Predicate<? super Float> filter)
      {
        final int size;
        return (size=this.size)!=0 && uncheckedRemoveIf(size,filter::test);
      }
      private boolean uncheckedRemoveIf(int size,FloatPredicate filter)
      {
        final Unchecked root;
        int srcOffset;
        int srcBound=(srcOffset=rootOffset)+size;
        final var arr=(root=this.root).arr;
        while(!filter.test((float)arr[srcOffset]))
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
        public void add(float val)
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
