package omni.impl.seq.arr.ofref;
import omni.impl.CheckedCollection;
import omni.impl.seq.AbstractRefList;
import omni.impl.seq.arr.ArrSeqUtil;
import omni.util.OmniArray;
import omni.util.ArrCopy;
import java.util.function.IntFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.Objects;
import omni.util.OmniPred;
import omni.util.BitSetUtils;
abstract class AbstractSeq<E> extends AbstractRefList<E>
{
  transient Object[] arr;
  private AbstractSeq()
  {
    super();
    this.arr=OmniArray.OfRef.DEFAULT_ARR;
  }
  private AbstractSeq(int capacity)
  {
    super();
    switch(capacity){
    default:
      this.arr=new Object[capacity];
      return;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfRef.DEFAULT_ARR;
    case 0:
    }
  }
  private AbstractSeq(int size,Object[] arr)
  {
    super(size);
    this.arr=arr;
  }
  public boolean contains(final Boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedcontains(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  public boolean contains(final Byte val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedcontains(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  public boolean contains(final Character val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedcontains(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  public boolean contains(final Short val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedcontains(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  public boolean contains(final Integer val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedcontains(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  public boolean contains(final Long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedcontains(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  public boolean contains(final Float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedcontains(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  public boolean contains(final Double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedcontains(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  public int indexOf(final Boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedindexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int indexOf(final Byte val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedindexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int indexOf(final Character val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedindexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int indexOf(final Short val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedindexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int indexOf(final Integer val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedindexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int indexOf(final Long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedindexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int indexOf(final Float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedindexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int indexOf(final Double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedindexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final Boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedsearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final Byte val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedsearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final Character val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedsearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final Short val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedsearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final Integer val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedsearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final Long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedsearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final Float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedsearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final Double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedsearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public boolean removeVal(final Boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  public boolean removeVal(final Byte val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  public boolean removeVal(final Character val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  public boolean removeVal(final Short val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  public boolean removeVal(final Integer val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  public boolean removeVal(final Long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  public boolean removeVal(final Float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  public boolean removeVal(final Double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  public boolean remove(Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=null)
      {
        return this.uncheckedremoveValNonNull(size,val);
      }
      return this.uncheckedremoveVal(size,Objects::isNull);
    }
    return false;
  }
  public boolean contains(Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      {
        return ArrSeqUtil.uncheckedcontains(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  public int indexOf(Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      {
        return ArrSeqUtil.uncheckedindexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  public int search(Object val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      {
        return ArrSeqUtil.uncheckedsearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  public boolean contains(boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedcontains(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  public boolean removeVal(boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  public int indexOf(boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedindexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedsearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public boolean contains(final byte val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedcontains(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  public boolean removeVal(final byte val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  public int indexOf(final byte val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedindexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(final byte val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedsearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public boolean contains(char val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return ArrSeqUtil.uncheckedcontains(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  public boolean removeVal(char val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  public int indexOf(char val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return ArrSeqUtil.uncheckedindexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
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
        return ArrSeqUtil.uncheckedsearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  public boolean contains(short val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return ArrSeqUtil.uncheckedcontains(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  public boolean removeVal(short val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  public int indexOf(short val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return ArrSeqUtil.uncheckedindexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
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
        return ArrSeqUtil.uncheckedsearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  public boolean contains(int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      {
        return ArrSeqUtil.uncheckedcontains(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  public boolean removeVal(int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      {
        return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  public int indexOf(int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      {
        return ArrSeqUtil.uncheckedindexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  public int search(int val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      {
        return ArrSeqUtil.uncheckedsearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return -1;
  }
  public boolean contains(long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedcontains(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  public boolean removeVal(long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  public int indexOf(long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedindexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedsearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public boolean contains(float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedcontains(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  public boolean removeVal(float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  public int indexOf(float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedindexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedsearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public boolean contains(double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedcontains(arr,0,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  public boolean removeVal(double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  public int indexOf(double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedindexOf(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public int search(double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return ArrSeqUtil.uncheckedsearch(arr,size,OmniPred.OfRef.getEqualsPred(val));
    }
    return -1;
  }
  public void forEach(Consumer<? super E> action)
  {
    final int size;
    if((size=this.size)!=0)
    {
      uncheckedForEach(size,action);
    }
  }
  abstract void uncheckedForEach(int size,Consumer<? super E> action);
  public boolean removeIf(Predicate<? super E> filter)
  {
    final int size;
    return (size=this.size)!=0 && uncheckedRemoveIf(size,filter);
  }
  abstract boolean uncheckedRemoveIf(int size,Predicate<? super E> filter);
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
  @SuppressWarnings("unchecked")
  public E peek()
  {
    final int size;
    if((size=this.size)!=0)
    {
      return (E)(arr[size-1]);
    }
    return null;
  }
  public void push(E val)
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
  private Object[] growInsert(Object[] arr,int index,int size){
    if(arr.length==size)
    {
      ArrCopy.semicheckedCopy(arr,0,arr=new Object[OmniArray.growBy50Pct(size)],0,index);
      this.arr=arr;
    }
    return arr;
  }
  private void uncheckedAppend(E val,int size){
    Object[] arr;
    if((arr=this.arr).length==size)
    {
      ArrCopy.uncheckedCopy(arr,0,arr=new Object[OmniArray.growBy50Pct(size)],0,size);
    }
    arr[size]=val;
    this.size=size+1;
  }
  private void uncheckedInit(E val){
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
    size=1;
  }
  private void uncheckedInsert(int index,E val,int size){
    final int tailDist;
    if((tailDist=size-index)==0){
      uncheckedAppend(val,size);
    }else{
      Object[] arr;
      ArrCopy.uncheckedCopy(arr=this.arr,index,arr=growInsert(arr,index,size),index+1,tailDist);
      arr[index]=val;
      this.size=size+1;
    }
  }
  abstract boolean uncheckedremoveVal(int size,Predicate<Object> pred);
  abstract boolean uncheckedremoveValNonNull(int size,Object nonNull);
  public Object[] toArray()
  {
    final int size;
    if((size=this.size)!=0)
    {
      final Object[] dst;
      uncheckedCopyInto(dst=new Object[size],size);
      return dst;
    }
    return OmniArray.OfRef.DEFAULT_ARR;
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
  abstract void uncheckedCopyInto(Object[] dst,int length);
  @Override
  protected int uncheckedlastIndexOf(int size,Predicate<Object> pred){
    return ArrSeqUtil.uncheckedlastIndexOf(arr,size,pred);
  }
  @Override
  protected int uncheckedlastIndexOfNonNull(int size,Object nonNull){
    return ArrSeqUtil.uncheckedlastIndexOf(arr,size,nonNull::equals);
  }
  E uncheckedPop(int newSize)
  {
    this.size=newSize;
    final Object[] arr;
    @SuppressWarnings("unchecked") final var popped=(E)(arr=this.arr)[newSize];
    arr[newSize]=null;
    return popped;
  }
  private int finalizeSubListBatchRemove(Object[] arr,int newBound,int oldBound){
    final int newRootSize,numRemoved;
    final int rootSize;
    size=newRootSize=(rootSize=size)-(numRemoved=oldBound-newBound);
    ArrCopy.semicheckedCopy(arr,oldBound,arr,newBound,newRootSize-newBound);
    OmniArray.OfRef.nullifyRange(arr,newRootSize,rootSize-1);
    return numRemoved;
  }
  static abstract class Checked<E> extends AbstractSeq<E>
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
    Checked(int size,Object[] arr)
    {
      super(size,arr);
    }
    public boolean add(E val)
    {
      ++modCount;
      super.push(val);
      return true;
    }
    public void add(int index,E val)
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
      final int size;
      if((size=this.size)!=0)
      {
        OmniArray.OfRef.nullifyRange(arr,0,size-1);
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
    public boolean contains(Object val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final var arr=this.arr;
        if(val!=null)
        {
          final int modCount=this.modCount;
          try
          {
            return ArrSeqUtil.uncheckedcontains(arr,0,size,val::equals);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
        return ArrSeqUtil.uncheckedcontains(arr,0,size,Objects::isNull);
      }
      return false;
    }
    @SuppressWarnings("unchecked")
    @Override
    boolean uncheckedRemoveIf(int size,Predicate<? super E> filter)
    {
      int srcOffset;
      final int modCount=this.modCount;
      try
      {
        srcOffset=0;
        final var arr=this.arr;
        for(;;)
        {
          if(filter.test((E)arr[srcOffset]))
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
          final Object v;
          if(!filter.test((E)(v=arr[srcOffset])))
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
        OmniArray.OfRef.nullifyRange(arr,dstOffset,--size);
        this.size=size;
      }
      catch(RuntimeException e)
      {
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
      return true;
    }
    static abstract class AbstractBidirectionalItr<E>
    {
      transient int lastRet=-1;
      transient final Checked<E> root;
      transient int cursor;
      transient int modCount;
      AbstractBidirectionalItr(Checked<E> root)
      {
        this.root=root;
        this.modCount=root.modCount;
      }
      AbstractBidirectionalItr(Checked<E> root,int cursor)
      {
        this.root=root;
        this.modCount=root.modCount;
        this.cursor=cursor;
      }
      public void add(E val)
      {
        final Checked<E> root;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        final int rootSize,cursor=this.cursor;
        if((rootSize=root.size)!=0)
        {
          ((AbstractSeq<E>)root).uncheckedInsert(cursor,val,rootSize);
        }
        else
        {
          ((AbstractSeq<E>)root).uncheckedInit(val);
        }
        root.modCount=++modCount;
        this.modCount=modCount;
        this.cursor=cursor+1;
        this.lastRet=-1;
      }
    }
    static abstract class AbstractSubList<E> extends AbstractRefList<E>
    {
      private static <E> void bubbleUpDecrementSize(AbstractSubList<E> parent,int numToRemove)
      {
        while(parent!=null){
          parent.size-=numToRemove;
          ++parent.modCount;
          parent=parent.parent;
        }
      }
      private static <E> void bubbleUpIncrementSize(AbstractSubList<E> parent){
        while(parent!=null){
          ++parent.size;
          ++parent.modCount;
          parent=parent.parent;
        }
      }
      transient final Checked<E> root;
      transient final AbstractSubList<E> parent;
      transient final int rootOffset;
      transient int modCount;
      AbstractSubList(Checked<E> root,AbstractSubList<E> parent,int rootOffset,int size,int modCount)
      {
        super(size);
        this.root=root;
        this.parent=parent;
        this.rootOffset=rootOffset;
        this.modCount=modCount;
      }
      AbstractSubList(Checked<E> root,int rootOffset,int size)
      {
        super(size);
        this.root=root;
        this.parent=null;
        this.rootOffset=rootOffset;
        this.modCount=root.modCount;
      }
      @Override
      protected int uncheckedlastIndexOf(int size,Predicate<Object> pred)
      {
        Checked<E> root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        int rootOffset;
        return ArrSeqUtil.uncheckedlastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,pred);
      }
      @Override
      protected int uncheckedlastIndexOfNonNull(int size,Object nonNull)
      {
        final int modCount=this.modCount;
        final var root=this.root;
        try
        {
          int rootOffset;
          return ArrSeqUtil.uncheckedlastIndexOf(root.arr,rootOffset=this.rootOffset,rootOffset+size,
              nonNull::equals);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      public boolean add(E val)
      {
        final Checked<E> root;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        final int rootSize;
        final int size=this.size;
        if((rootSize=root.size)!=0)
        {
          ((AbstractSeq<E>)root).uncheckedInsert(rootOffset+size,val,rootSize);
        }
        else
        {
          ((AbstractSeq<E>)root).uncheckedInit(val);
        }
        root.modCount=++modCount;
        this.modCount=modCount;
        bubbleUpIncrementSize(parent);
        this.size=size+1;
        return true;
      }
      public void add(int index,E val)
      {
        final Checked<E> root;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        CheckedCollection.checkLo(index);
        final int size;
        CheckedCollection.checkWriteHi(index,size=this.size);
        final int rootSize;
        if((rootSize=root.size)!=0)
        {
          ((AbstractSeq<E>)root).uncheckedInsert(rootOffset+index,val,rootSize);
        }
        else
        {
          ((AbstractSeq<E>)root).uncheckedInit(val);
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
          final int oldRootSize;
          root.size=newRootSize=(oldRootSize=root.size)-size;
          final Object[] arr;
          final int rootOffset;
          ArrCopy.semicheckedCopy(arr=root.arr,(rootOffset=this.rootOffset)+size,arr,rootOffset,newRootSize-rootOffset);
          OmniArray.OfRef.nullifyRange(arr,newRootSize,oldRootSize-1);
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
      public boolean removeIf(Predicate<? super E> filter)
      {
        final int size;
        if((size=this.size)!=0)
        {
          return uncheckedRemoveIf(size,filter);
        }
        CheckedCollection.checkModCount(this.modCount,root.modCount);
        return false;
      }
      @SuppressWarnings("unchecked")
      private boolean uncheckedRemoveIf(int size,Predicate<? super E> filter)
      {
        final Checked<E> root=this.root;
        int modCount=this.modCount;
        int srcOffset;
        final int srcBound=(srcOffset=rootOffset)+size;
        int dstOffset;
        try
        {
          final var arr=root.arr;
          for(;;)
          {
            if(filter.test((E)arr[srcOffset]))
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
            final Object v;
            if(!filter.test((E)(v=arr[srcOffset])))
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
          this.size=size-(size=((AbstractSeq<E>)root).finalizeSubListBatchRemove(arr,dstOffset,srcBound));
          bubbleUpDecrementSize(parent,size);
        }
        catch(RuntimeException e)
        {
          throw CheckedCollection.checkModCount(modCount,root.modCount,e);
        }
        return true;
      }
      static abstract class AbstractBidirectionalItr<E>
      {
        transient final AbstractSubList<E> parent;
        transient int cursor;
        transient int lastRet=-1;
        transient int modCount;
        AbstractBidirectionalItr(AbstractSubList<E> parent,int modCount)
        {
          this.parent=parent;
          this.cursor=parent.rootOffset;
          this.modCount=modCount;
        }
        AbstractBidirectionalItr(AbstractSubList<E> parent,int modCount,int cursor){
          this.parent=parent;
          this.cursor=cursor;
          this.modCount=modCount;
        }
        public void add(E val)
        {
          final AbstractSubList<E> parent;
          final Checked<E> root;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
          final int rootSize;
          final int cursor=this.cursor;
          if((rootSize=root.size)!=0){
            ((AbstractSeq<E>)root).uncheckedInsert(cursor,val,rootSize);
          }else{
            ((AbstractSeq<E>)root).uncheckedInit(val);
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
  static abstract class Unchecked<E> extends AbstractSeq<E>
  {
    Unchecked(){
      super();
    }
    Unchecked(int capacity){
      super(capacity);
    }
    Unchecked(int size,Object[] arr){
      super(size,arr);
    }
    public boolean add(E val)
    {
      super.push(val);
      return true;
    }
    public void add(int index,E val)
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
    @Override
    boolean uncheckedRemoveIf(int size,Predicate<? super E> filter)
    {
      int srcOffset;
      srcOffset=0;
      while(!filter.test((E)arr[srcOffset]))
      {
        if(++srcOffset==size)
        {
          return false;
        }
      }
      OmniArray.OfRef.nullifyRange(arr,srcOffset=ArrSeqUtil.pullSurvivorsDown(arr,srcOffset,--size,filter),size);
      this.size=srcOffset;
      return true;
    }
    static abstract class AbstractBidirectionalItr<E>{
      transient final Unchecked<E> root;
      transient int cursor;
      transient int lastRet;
      AbstractBidirectionalItr(Unchecked<E> root){
        this.root=root;
      }
      AbstractBidirectionalItr(Unchecked<E> root,int cursor){
        this.root=root;
        this.cursor=cursor;
      }
      public void add(E val){
        final AbstractSeq<E> root;
        final int rootSize,cursor=this.cursor;
        if((rootSize=(root=this.root).size)!=0){
          root.uncheckedInsert(cursor,val,rootSize);
        }else{
          root.uncheckedInit(val);
        }
        this.cursor=cursor+1;
      }
    }
    static abstract class AbstractSubList<E> extends AbstractRefList<E>
    {
      private static <E> void bubbleUpDecrementSize(AbstractSubList<E> parent,int numToRemove)
      {
        while(parent!=null)
        {
          parent.size-=numToRemove;
          parent=parent.parent;
        }
      }
      private static <E> void bubbleUpIncrementSize(AbstractSubList<E> parent)
      {
        while(parent!=null){
          ++parent.size;
          parent=parent.parent;
        }
      }
      static <E> void bubbleUpDecrementSize(AbstractSubList<E> parent){
        while(parent!=null){
          --parent.size;
          parent=parent.parent;
        }
      }
      transient final Unchecked<E> root;
      transient final AbstractSubList<E> parent;
      transient final int rootOffset;
      AbstractSubList(Unchecked<E> root,AbstractSubList<E> parent,int rootOffset,int size)
      {
        super(size);
        this.root=root;
        this.parent=parent;
        this.rootOffset=rootOffset;
      }
      AbstractSubList(Unchecked<E> root,int rootOffset,int size)
      {
        super(size);
        this.root=root;
        this.parent=null;
        this.rootOffset=rootOffset;
      }
      public boolean add(E val)
      {
        final AbstractSeq<E> root;
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
      public void add(int index,E val){
        final AbstractSeq<E> root;
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
          final Unchecked<E> root;
          final int oldRootSize;
          (root=this.root).size=newRootSize=(oldRootSize=root.size)-size;
          final Object[] arr;
          ArrCopy.semicheckedCopy(arr=root.arr,size+(size=rootOffset),arr,size,newRootSize-size);
          OmniArray.OfRef.nullifyRange(arr,newRootSize,oldRootSize-1);
        }
      }
      public boolean removeIf(Predicate<? super E> filter)
      {
        final int size;
        return (size=this.size)!=0 && uncheckedRemoveIf(size,filter);
      }
      @SuppressWarnings("unchecked")
      private boolean uncheckedRemoveIf(int size,Predicate<? super E> filter)
      {
        final Unchecked<E> root;
        int srcOffset;
        int srcBound=(srcOffset=rootOffset)+size;
        final var arr=(root=this.root).arr;
        while(!filter.test((E)arr[srcOffset]))
        {
          if(++srcOffset==srcBound)
          {
            return false;
          }
        }
        this.size=size-(size=((AbstractSeq<E>)root).finalizeSubListBatchRemove(arr,ArrSeqUtil.pullSurvivorsDown(arr,srcOffset,srcBound-1,filter),srcBound));
        bubbleUpDecrementSize(parent,size);
        return true;
      }
      static abstract class AbstractBidirectionalItr<E>
      {
        transient final AbstractSubList<E> parent;
        transient int cursor;
        transient int lastRet;
        AbstractBidirectionalItr(AbstractSubList<E> parent)
        {
          this.parent=parent;
          this.cursor=parent.rootOffset;
        }
        AbstractBidirectionalItr(AbstractSubList<E> parent,int cursor)
        {
          this.parent=parent;
          this.cursor=cursor;
        }
        public void add(E val)
        {
          final AbstractSubList<E> parent;
          final AbstractSeq<E> root;
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
