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
import omni.impl.CheckedCollection;
import java.util.NoSuchElementException;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;
import java.util.ConcurrentModificationException;
import omni.util.BitSetUtil;
import omni.util.OmniPred;
public abstract class RefArrSeq<E> implements OmniCollection.OfRef<E>
{
  transient int size;
  transient Object[] arr;
  private RefArrSeq()
  {
    super();
    this.arr=OmniArray.OfRef.DEFAULT_ARR;
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
      OmniArray.OfRef.nullifyRange(this.arr,size-1,0);
    }
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
      final StringBuilder builder=new StringBuilder("[");
      uncheckedToString(size,builder);
      return builder.append(']').toString();
    }
    return "[]";
  }
  abstract int uncheckedHashCode(int size);
  abstract void uncheckedToString(int size,StringBuilder builder);
  @Override
  public boolean contains(boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(int val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
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
      return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean contains(Object val)
  {
    final int size;
    if((size=this.size)!=0){
      if(val!=null){
        return OmniArray.OfRef.uncheckedcontainsNonNull(this.arr,0,size-1,val);
      }
      return OmniArray.OfRef.uncheckedcontainsNull(this.arr,0,size-1);
  //#ELSE
  //    if(val instanceof E)
  //    {
  //  #IF OfDouble,OfFloat
  //      final Object v;
  //      if((v=(Object)val)==v)
  //      {
  //        #MACRO ReturnUncheckedQueryBits($convertToBits$(v))
  //      }
  //      #MACRO ReturnUncheckedQueryNaN()
  //  #ELSE
  //      #MACRO ReturnUncheckedQuery(OmniPred.OfRef.getEqualsPred(val))
  //  #ENDIF
  //    }
  //#ENDIF
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
        return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
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
        return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override
  public boolean contains(short val)
  {
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override
  public boolean contains(Boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=null)
      {
        return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred((boolean)(val)));
      }
      return OmniArray.OfRef.uncheckedcontainsNull(this.arr,0,size-1);
    }
    return false;
  }
  @Override
  public boolean contains(Byte val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=null)
      {
        return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred((byte)(val)));
      }
      return OmniArray.OfRef.uncheckedcontainsNull(this.arr,0,size-1);
    }
    return false;
  }
  @Override
  public boolean contains(Character val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=null)
      {
        return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred((char)(val)));
      }
      return OmniArray.OfRef.uncheckedcontainsNull(this.arr,0,size-1);
    }
    return false;
  }
  @Override
  public boolean contains(Short val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=null)
      {
        return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred((short)(val)));
      }
      return OmniArray.OfRef.uncheckedcontainsNull(this.arr,0,size-1);
    }
    return false;
  }
  @Override
  public boolean contains(Integer val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=null)
      {
        return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred((int)(val)));
      }
      return OmniArray.OfRef.uncheckedcontainsNull(this.arr,0,size-1);
    }
    return false;
  }
  @Override
  public boolean contains(Long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=null)
      {
        return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred((long)(val)));
      }
      return OmniArray.OfRef.uncheckedcontainsNull(this.arr,0,size-1);
    }
    return false;
  }
  @Override
  public boolean contains(Float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=null)
      {
        return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred((float)(val)));
      }
      return OmniArray.OfRef.uncheckedcontainsNull(this.arr,0,size-1);
    }
    return false;
  }
  @Override
  public boolean contains(Double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=null)
      {
        return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred((double)(val)));
      }
      return OmniArray.OfRef.uncheckedcontainsNull(this.arr,0,size-1);
    }
    return false;
  }
  @Override
  public boolean removeVal(boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(int val)
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
  @Override
  public boolean removeVal(long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean removeVal(double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
    }
    return false;
  }
  @Override
  public boolean remove(Object val)
  {
    final int size;
    if((size=this.size)!=0){
      if(val!=null){
        return this.uncheckedremoveValNonNull(size,val);
      }
      return this.uncheckedremoveValNull(size);
  //#ELSE
  //    if(val instanceof E)
  //    {
  //  #IF OfDouble,OfFloat
  //      final Object v;
  //      if((v=(Object)val)==v)
  //      {
  //        #MACRO ReturnUncheckedQueryBits($convertToBits$(v))
  //      }
  //      #MACRO ReturnUncheckedQueryNaN()
  //  #ELSE
  //      #MACRO ReturnUncheckedQuery(OmniPred.OfRef.getEqualsPred(val))
  //  #ENDIF
  //    }
  //#ENDIF
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
        return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
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
        return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
      }
    }
    return false;
  }
  @Override
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
  @Override
  public boolean removeVal(Boolean val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=null)
      {
        return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((boolean)(val)));
      }
      return this.uncheckedremoveValNull(size);
    }
    return false;
  }
  @Override
  public boolean removeVal(Byte val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=null)
      {
        return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((byte)(val)));
      }
      return this.uncheckedremoveValNull(size);
    }
    return false;
  }
  @Override
  public boolean removeVal(Character val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=null)
      {
        return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((char)(val)));
      }
      return this.uncheckedremoveValNull(size);
    }
    return false;
  }
  @Override
  public boolean removeVal(Short val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=null)
      {
        return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((short)(val)));
      }
      return this.uncheckedremoveValNull(size);
    }
    return false;
  }
  @Override
  public boolean removeVal(Integer val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=null)
      {
        return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((int)(val)));
      }
      return this.uncheckedremoveValNull(size);
    }
    return false;
  }
  @Override
  public boolean removeVal(Long val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=null)
      {
        return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((long)(val)));
      }
      return this.uncheckedremoveValNull(size);
    }
    return false;
  }
  @Override
  public boolean removeVal(Float val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=null)
      {
        return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((float)(val)));
      }
      return this.uncheckedremoveValNull(size);
    }
    return false;
  }
  @Override
  public boolean removeVal(Double val)
  {
    final int size;
    if((size=this.size)!=0)
    {
      if(val!=null)
      {
        return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((double)(val)));
      }
      return this.uncheckedremoveValNull(size);
    }
    return false;
  }
  abstract boolean uncheckedremoveValNonNull(int size,Object nonNull);
  abstract boolean uncheckedremoveValNull(int size);
  abstract boolean uncheckedremoveVal(int size,Predicate<Object> pred);
  abstract void uncheckedForEach(int size,Consumer<? super E> action);
  @Override
  public void forEach(Consumer<? super E> action)
  {
    final int size;
    if((size=this.size)!=0)
    {
      uncheckedForEach(size,action);
    }
  }
  private void uncheckedAppend(int size,E val)
  {
    Object[] arr;
    if((arr=this.arr).length==size)
    {
      ArrCopy.uncheckedCopy(arr,0,arr=new Object[OmniArray.growBy50Pct(size)],0,size);
      this.arr=arr;
    }
    arr[size]=val;
    this.size=size+1;
  }
  private void uncheckedInit(E val)
  {
    Object[] arr;
    if((arr=this.arr)==null)
    {
      this.arr=new Object[]{val};
    }
    else
    {
      if(arr==OmniArray.OfRef.DEFAULT_ARR)
      {
        this.arr=arr=new Object[OmniArray.DEFAULT_ARR_SEQ_CAP];
      }
      arr[0]=val;
    }
    this.size=1;
  }
  public void push(E val)
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
  public boolean add(E val)
  {
    push(val);
    return true;
  }
  abstract void uncheckedCopyInto(Object[] dst,int length);
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
  boolean uncheckedRemoveIf(int size,Predicate<? super E> filter)
  {
    final int numRemoved;
    final Object[] arr;
    if((numRemoved=uncheckedRemoveIfImpl(arr=this.arr,0,size,filter))!=0){
      OmniArray.OfRef.nullifyRange(arr,size-1,size-=numRemoved);
      this.size=size;
      return true;
    }
    return false;
  }
  @Override
  public boolean removeIf(Predicate<? super E> filter)
  {
    final int size;
    return (size=this.size)!=0 && uncheckedRemoveIf(size,filter);
  }
  @SuppressWarnings("unchecked")
  private static <E> int pullSurvivorsDown(Object[] arr,int srcOffset,int srcBound,int dstOffset,Predicate<? super E> filter)
  {
    while(++srcOffset!=srcBound)
    {
      final Object v;
      if(!filter.test((E)(v=arr[srcOffset])))
      {
        arr[dstOffset++]=v;
      }
    }
    return srcBound-dstOffset;
  }
  @SuppressWarnings("unchecked")
  private static <E> int uncheckedRemoveIfImpl(Object[] arr,int srcOffset,int srcBound,Predicate<? super E> filter)
  {
    do{
      if(filter.test((E)arr[srcOffset])){
        return pullSurvivorsDown(arr,srcOffset,srcBound,srcOffset,filter);
      }
    }while(++srcOffset!=srcBound);
    return 0;
  }
  @SuppressWarnings("unchecked")
  private static <E> int uncheckedRemoveIfImpl(Object[] arr,int srcOffset,int srcBound,Predicate<? super E> filter,CheckedCollection.AbstractModCountChecker modCountChecker)
  {
    do
    {
      if(filter.test((E)arr[srcOffset]))
      {
        int dstOffset=srcOffset;
        outer:for(;;)
        {
          if(++srcOffset==srcBound)
          {
            modCountChecker.checkModCount();
            break outer;
          }
          Object before;
          if(!filter.test((E)(before=arr[srcOffset])))
          {
            for(int i=srcBound-1;;--i)
            {
              if(i==srcOffset)
              {
                modCountChecker.checkModCount();
                arr[dstOffset++]=before;
                break outer;
              }
              Object after;
              if(!filter.test((E)(after=arr[i])))
              {
                int n;
                if((n=i-(++srcOffset))!=0)
                {
                  if(n>64)
                  {
                    long[] survivorSet;
                    int numSurvivors=BitSetUtil.markSurvivors(arr,srcOffset,i,filter,survivorSet=BitSetUtil.getBitSet(n));
                    modCountChecker.checkModCount();
                    if(numSurvivors!=0)
                    {
                      if(numSurvivors==n)
                      {
                        ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset-1,numSurvivors+=2);
                        dstOffset+=numSurvivors;
                      }
                      else
                      {
                        arr[dstOffset]=before;
                        BitSetUtil.pullSurvivorsDown(arr,srcOffset,++dstOffset,dstOffset+=numSurvivors,survivorSet);
                        arr[dstOffset++]=after;
                      }
                      break outer;
                    }
                  }
                  else
                  {
                    long survivorWord=BitSetUtil.markSurvivors(arr,srcOffset,i,filter);
                    modCountChecker.checkModCount();
                    int numSurvivors;
                    if((numSurvivors=Long.bitCount(survivorWord))!=0)
                    {
                      if(numSurvivors==n)
                      {
                        ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset-1,numSurvivors+=2);
                        dstOffset+=numSurvivors;
                      }
                      else
                      {
                        arr[dstOffset]=before;
                        BitSetUtil.pullSurvivorsDown(arr,srcOffset,++dstOffset,dstOffset+=numSurvivors,survivorWord);
                        arr[dstOffset++]=after;
                      }
                      break outer;
                    }
                  }
                }
                else
                {
                  modCountChecker.checkModCount();
                }
                arr[dstOffset++]=before;
                arr[dstOffset++]=after;
                break outer;
              }
            }
          }
        }
        return srcBound-dstOffset;
      }
    }
    while(++srcOffset!=srcBound);
    return 0;
  }
  public
    static class UncheckedStack<E>
      extends RefArrSeq<E>
      implements OmniStack.OfRef<E>,Cloneable
  {
    public UncheckedStack()
    {
      super();
    }
    public UncheckedStack(int initialCapacity)
    {
      super(initialCapacity);
    }
    UncheckedStack(int size,Object[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implements equals method for UncheckedStack<E>
      return false;
    }
    @Override
    public Object clone()
    {
      final Object[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new Object[size],0,size);
      }
      else
      {
        copy=OmniArray.OfRef.DEFAULT_ARR;
      }
      return new UncheckedStack<E>(size,copy);
    }
    @Override
    void uncheckedToString(int size,StringBuilder builder)
    {
      OmniArray.OfRef.descendingToString(this.arr,0,size-1,builder);
    }
    @Override
    int uncheckedHashCode(int size)
    {
      return OmniArray.OfRef.descendingSeqHashCode(this.arr,0,size-1);
    }
    @Override
    public int search(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfRef.uncheckedsearch(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
      }
      return -1;
    }
    @Override
    public int search(int val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfRef.uncheckedsearch(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
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
        return OmniArray.OfRef.uncheckedsearch(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
      }
      return -1;
    }
    @Override
    public int search(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfRef.uncheckedsearch(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
      }
      return -1;
    }
    @Override
    public int search(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfRef.uncheckedsearch(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
      }
      return -1;
    }
    @Override
    public int search(Object val)
    {
      final int size;
      if((size=this.size)!=0){
        if(val!=null){
          return OmniArray.OfRef.uncheckedsearchNonNull(this.arr,size,val);
        }
        return OmniArray.OfRef.uncheckedsearchNull(this.arr,size);
    //#ELSE
    //    if(val instanceof E)
    //    {
    //  #IF OfDouble,OfFloat
    //      final Object v;
    //      if((v=(Object)val)==v)
    //      {
    //        #MACRO ReturnUncheckedQueryBits($convertToBits$(v))
    //      }
    //      #MACRO ReturnUncheckedQueryNaN()
    //  #ELSE
    //      #MACRO ReturnUncheckedQuery(OmniPred.OfRef.getEqualsPred(val))
    //  #ENDIF
    //    }
    //#ENDIF
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
          return OmniArray.OfRef.uncheckedsearch(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
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
          return OmniArray.OfRef.uncheckedsearch(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
      }
      return -1;
    }
    @Override
    public int search(short val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfRef.uncheckedsearch(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
      }
      return -1;
    }
    @Override
    public int search(Boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedsearch(this.arr,size,OmniPred.OfRef.getEqualsPred((boolean)(val)));
        }
        return OmniArray.OfRef.uncheckedsearchNull(this.arr,size);
      }
      return -1;
    }
    @Override
    public int search(Byte val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedsearch(this.arr,size,OmniPred.OfRef.getEqualsPred((byte)(val)));
        }
        return OmniArray.OfRef.uncheckedsearchNull(this.arr,size);
      }
      return -1;
    }
    @Override
    public int search(Character val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedsearch(this.arr,size,OmniPred.OfRef.getEqualsPred((char)(val)));
        }
        return OmniArray.OfRef.uncheckedsearchNull(this.arr,size);
      }
      return -1;
    }
    @Override
    public int search(Short val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedsearch(this.arr,size,OmniPred.OfRef.getEqualsPred((short)(val)));
        }
        return OmniArray.OfRef.uncheckedsearchNull(this.arr,size);
      }
      return -1;
    }
    @Override
    public int search(Integer val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedsearch(this.arr,size,OmniPred.OfRef.getEqualsPred((int)(val)));
        }
        return OmniArray.OfRef.uncheckedsearchNull(this.arr,size);
      }
      return -1;
    }
    @Override
    public int search(Long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedsearch(this.arr,size,OmniPred.OfRef.getEqualsPred((long)(val)));
        }
        return OmniArray.OfRef.uncheckedsearchNull(this.arr,size);
      }
      return -1;
    }
    @Override
    public int search(Float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedsearch(this.arr,size,OmniPred.OfRef.getEqualsPred((float)(val)));
        }
        return OmniArray.OfRef.uncheckedsearchNull(this.arr,size);
      }
      return -1;
    }
    @Override
    public int search(Double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedsearch(this.arr,size,OmniPred.OfRef.getEqualsPred((double)(val)));
        }
        return OmniArray.OfRef.uncheckedsearchNull(this.arr,size);
      }
      return -1;
    }
    @Override
    boolean uncheckedremoveValNonNull(int size,Object nonNull)
    {
      {
        final var arr=this.arr;
        for(int index=--size;;--index)
        {
          if(nonNull.equals(arr[index]))
          {
            OmniArray.OfRef.removeIndexAndPullDown(arr,index,size);
            this.size=size;
            return true;
          }
          if(index==0)
          {
            return false;
          }
        }
      }
    }
    @Override
    boolean uncheckedremoveValNull(int size
    )
    {
      final var arr=this.arr;
      for(int index=--size;;--index)
      {
        if(
        arr[index]==null
        )
        {
          OmniArray.OfRef.removeIndexAndPullDown(arr,index,size);
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
    boolean uncheckedremoveVal (int size
    ,Predicate<Object> pred
    )
    {
      final var arr=this.arr;
      for(int index=--size;;--index)
      {
        if(
        pred.test(arr[index])
        )
        {
          OmniArray.OfRef.removeIndexAndPullDown(arr,index,size);
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
    void uncheckedForEach(int size,Consumer<? super E> action)
    {
      {
        OmniArray.OfRef.descendingForEach(this.arr,0,size-1,action);
      }
    }
    private static class Itr<E>
      implements OmniIterator.OfRef<E>
    {
      transient final UncheckedStack<E> parent;
      transient int cursor;
      private Itr(UncheckedStack<E> parent)
      {
        this.parent=parent;
        this.cursor=parent.size;
      }
      private Itr(UncheckedStack<E> parent,int cursor)
      {
        this.parent=parent;
        this.cursor=cursor;
      }
      @Override
      public boolean hasNext()
      {
        return this.cursor>0;
      }
      @SuppressWarnings("unchecked")
      @Override
      public E next()
      {
        return (E)parent.arr[--cursor];
      }
      @Override
      public void remove()
      {
        final UncheckedStack<E> root;
        OmniArray.OfRef.removeIndexAndPullDown((root=this.parent).arr,this.cursor,--root.size);
      }
      @Override
      public void forEachRemaining(Consumer<? super E> action)
      {
        final int cursor;
        if((cursor=this.cursor)>0)
        {
          OmniArray.OfRef.descendingForEach(parent.arr,0,cursor-1,action);
          this.cursor=0;
        }
      }
    }
    @Override
    public OmniIterator.OfRef<E> iterator()
    {
      return new Itr<E>(this);
    }
    @Override
    void uncheckedCopyInto(Object[] dst,int length)
    {
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @SuppressWarnings("unchecked")
    @Override
    public E pop()
    {
      final Object[] arr;
      final int size;
      final var ret=(E)(arr=this.arr)[size=--this.size];
      arr[size]=null;
      return ret;
    }
    @SuppressWarnings("unchecked")
    @Override
    public E poll()
    {
      int size;
      if((size=this.size)!=0)
      {
        final var ret=(E)(arr[--size]);
        arr[size]=null;
        this.size=size;
        return ret;
      }
      return null;
    }
    @SuppressWarnings("unchecked")
    @Override
    public E peek()
    {
      final int size;
      if((size=this.size)!=0)
      {
        return (E)(arr[size-1]);
      }
      return null;
    }
  }
  public
    static class UncheckedList<E>
      extends RefArrSeq<E>
      implements OmniList.OfRef<E>,Cloneable
  {
    public UncheckedList()
    {
      super();
    }
    public UncheckedList(int initialCapacity)
    {
      super(initialCapacity);
    }
    UncheckedList(int size,Object[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implements equals method for UncheckedList<E>
      return false;
    }
    @Override
    public Object clone()
    {
      final Object[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new Object[size],0,size);
      }
      else
      {
        copy=OmniArray.OfRef.DEFAULT_ARR;
      }
      return new UncheckedList<E>(size,copy);
    }
    @Override
    void uncheckedToString(int size,StringBuilder builder)
    {
      OmniArray.OfRef.ascendingToString(this.arr,0,size-1,builder);
    }
    @Override
    int uncheckedHashCode(int size)
    {
      return OmniArray.OfRef.ascendingSeqHashCode(this.arr,0,size-1);
    }
    @Override
    public int indexOf(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfRef.uncheckedindexOf(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
      }
      return -1;
    }
    @Override
    public int indexOf(int val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfRef.uncheckedindexOf(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
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
        return OmniArray.OfRef.uncheckedindexOf(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
      }
      return -1;
    }
    @Override
    public int indexOf(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfRef.uncheckedindexOf(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
      }
      return -1;
    }
    @Override
    public int indexOf(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfRef.uncheckedindexOf(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
      }
      return -1;
    }
    @Override
    public int indexOf(Object val)
    {
      final int size;
      if((size=this.size)!=0){
        if(val!=null){
          return OmniArray.OfRef.uncheckedindexOfNonNull(this.arr,size,val);
        }
        return OmniArray.OfRef.uncheckedindexOfNull(this.arr,size);
    //#ELSE
    //    if(val instanceof E)
    //    {
    //  #IF OfDouble,OfFloat
    //      final Object v;
    //      if((v=(Object)val)==v)
    //      {
    //        #MACRO ReturnUncheckedQueryBits($convertToBits$(v))
    //      }
    //      #MACRO ReturnUncheckedQueryNaN()
    //  #ELSE
    //      #MACRO ReturnUncheckedQuery(OmniPred.OfRef.getEqualsPred(val))
    //  #ENDIF
    //    }
    //#ENDIF
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
          return OmniArray.OfRef.uncheckedindexOf(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
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
          return OmniArray.OfRef.uncheckedindexOf(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
      }
      return -1;
    }
    @Override
    public int indexOf(short val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfRef.uncheckedindexOf(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
      }
      return -1;
    }
    @Override
    public int indexOf(Boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedindexOf(this.arr,size,OmniPred.OfRef.getEqualsPred((boolean)(val)));
        }
        return OmniArray.OfRef.uncheckedindexOfNull(this.arr,size);
      }
      return -1;
    }
    @Override
    public int indexOf(Byte val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedindexOf(this.arr,size,OmniPred.OfRef.getEqualsPred((byte)(val)));
        }
        return OmniArray.OfRef.uncheckedindexOfNull(this.arr,size);
      }
      return -1;
    }
    @Override
    public int indexOf(Character val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedindexOf(this.arr,size,OmniPred.OfRef.getEqualsPred((char)(val)));
        }
        return OmniArray.OfRef.uncheckedindexOfNull(this.arr,size);
      }
      return -1;
    }
    @Override
    public int indexOf(Short val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedindexOf(this.arr,size,OmniPred.OfRef.getEqualsPred((short)(val)));
        }
        return OmniArray.OfRef.uncheckedindexOfNull(this.arr,size);
      }
      return -1;
    }
    @Override
    public int indexOf(Integer val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedindexOf(this.arr,size,OmniPred.OfRef.getEqualsPred((int)(val)));
        }
        return OmniArray.OfRef.uncheckedindexOfNull(this.arr,size);
      }
      return -1;
    }
    @Override
    public int indexOf(Long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedindexOf(this.arr,size,OmniPred.OfRef.getEqualsPred((long)(val)));
        }
        return OmniArray.OfRef.uncheckedindexOfNull(this.arr,size);
      }
      return -1;
    }
    @Override
    public int indexOf(Float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedindexOf(this.arr,size,OmniPred.OfRef.getEqualsPred((float)(val)));
        }
        return OmniArray.OfRef.uncheckedindexOfNull(this.arr,size);
      }
      return -1;
    }
    @Override
    public int indexOf(Double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedindexOf(this.arr,size,OmniPred.OfRef.getEqualsPred((double)(val)));
        }
        return OmniArray.OfRef.uncheckedindexOfNull(this.arr,size);
      }
      return -1;
    }
    @Override
    public int lastIndexOf(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfRef.uncheckedlastIndexOf(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
      }
      return -1;
    }
    @Override
    public int lastIndexOf(int val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfRef.uncheckedlastIndexOf(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
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
        return OmniArray.OfRef.uncheckedlastIndexOf(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
      }
      return -1;
    }
    @Override
    public int lastIndexOf(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfRef.uncheckedlastIndexOf(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
      }
      return -1;
    }
    @Override
    public int lastIndexOf(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfRef.uncheckedlastIndexOf(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
      }
      return -1;
    }
    @Override
    public int lastIndexOf(Object val)
    {
      final int size;
      if((size=this.size)!=0){
        if(val!=null){
          return OmniArray.OfRef.uncheckedlastIndexOfNonNull(this.arr,size,val);
        }
        return OmniArray.OfRef.uncheckedlastIndexOfNull(this.arr,size);
    //#ELSE
    //    if(val instanceof E)
    //    {
    //  #IF OfDouble,OfFloat
    //      final Object v;
    //      if((v=(Object)val)==v)
    //      {
    //        #MACRO ReturnUncheckedQueryBits($convertToBits$(v))
    //      }
    //      #MACRO ReturnUncheckedQueryNaN()
    //  #ELSE
    //      #MACRO ReturnUncheckedQuery(OmniPred.OfRef.getEqualsPred(val))
    //  #ENDIF
    //    }
    //#ENDIF
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
          return OmniArray.OfRef.uncheckedlastIndexOf(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
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
          return OmniArray.OfRef.uncheckedlastIndexOf(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(short val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfRef.uncheckedlastIndexOf(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(Boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedlastIndexOf(this.arr,size,OmniPred.OfRef.getEqualsPred((boolean)(val)));
        }
        return OmniArray.OfRef.uncheckedlastIndexOfNull(this.arr,size);
      }
      return -1;
    }
    @Override
    public int lastIndexOf(Byte val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedlastIndexOf(this.arr,size,OmniPred.OfRef.getEqualsPred((byte)(val)));
        }
        return OmniArray.OfRef.uncheckedlastIndexOfNull(this.arr,size);
      }
      return -1;
    }
    @Override
    public int lastIndexOf(Character val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedlastIndexOf(this.arr,size,OmniPred.OfRef.getEqualsPred((char)(val)));
        }
        return OmniArray.OfRef.uncheckedlastIndexOfNull(this.arr,size);
      }
      return -1;
    }
    @Override
    public int lastIndexOf(Short val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedlastIndexOf(this.arr,size,OmniPred.OfRef.getEqualsPred((short)(val)));
        }
        return OmniArray.OfRef.uncheckedlastIndexOfNull(this.arr,size);
      }
      return -1;
    }
    @Override
    public int lastIndexOf(Integer val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedlastIndexOf(this.arr,size,OmniPred.OfRef.getEqualsPred((int)(val)));
        }
        return OmniArray.OfRef.uncheckedlastIndexOfNull(this.arr,size);
      }
      return -1;
    }
    @Override
    public int lastIndexOf(Long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedlastIndexOf(this.arr,size,OmniPred.OfRef.getEqualsPred((long)(val)));
        }
        return OmniArray.OfRef.uncheckedlastIndexOfNull(this.arr,size);
      }
      return -1;
    }
    @Override
    public int lastIndexOf(Float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedlastIndexOf(this.arr,size,OmniPred.OfRef.getEqualsPred((float)(val)));
        }
        return OmniArray.OfRef.uncheckedlastIndexOfNull(this.arr,size);
      }
      return -1;
    }
    @Override
    public int lastIndexOf(Double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedlastIndexOf(this.arr,size,OmniPred.OfRef.getEqualsPred((double)(val)));
        }
        return OmniArray.OfRef.uncheckedlastIndexOfNull(this.arr,size);
      }
      return -1;
    }
    @Override
    boolean uncheckedremoveValNonNull(int size,Object nonNull)
    {
      {
        final var arr=this.arr;
        for(int index=0;;)
        {
          if(nonNull.equals(arr[index]))
          {
            OmniArray.OfRef.removeIndexAndPullDown(arr,index,--size);
            this.size=size;
            return true;
          }
          if(++index==size)
          {
            return false;
          }
        }
      }
    }
    @Override
    boolean uncheckedremoveValNull(int size
    )
    {
      final var arr=this.arr;
      for(int index=0;;)
      {
        if(
        arr[index]==null
        )
        {
          OmniArray.OfRef.removeIndexAndPullDown(arr,index,--size);
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
    boolean uncheckedremoveVal (int size
    ,Predicate<Object> pred
    )
    {
      final var arr=this.arr;
      for(int index=0;;)
      {
        if(
        pred.test(arr[index])
        )
        {
          OmniArray.OfRef.removeIndexAndPullDown(arr,index,--size);
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
    void uncheckedForEach(int size,Consumer<? super E> action)
    {
      {
        OmniArray.OfRef.ascendingForEach(this.arr,0,size-1,action);
      }
    }
    private static class Itr<E>
      implements OmniIterator.OfRef<E>
    {
      transient final UncheckedList<E> parent;
      transient int cursor;
      private Itr(UncheckedList<E> parent)
      {
        this.parent=parent;
        this.cursor=0;
      }
      private Itr(UncheckedList<E> parent,int cursor)
      {
        this.parent=parent;
        this.cursor=cursor;
      }
      @Override
      public boolean hasNext()
      {
        return this.cursor<parent.size;
      }
      @SuppressWarnings("unchecked")
      @Override
      public E next()
      {
        return (E)parent.arr[cursor++];
      }
      @Override
      public void remove()
      {
        final UncheckedList<E> root;
        OmniArray.OfRef.removeIndexAndPullDown((root=this.parent).arr,--this.cursor,--root.size);
      }
      @Override
      public void forEachRemaining(Consumer<? super E> action)
      {
        final int cursor,bound;
        final UncheckedList<E> parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size))
        {
          OmniArray.OfRef.ascendingForEach(parent.arr,cursor,bound-1,action);
          this.cursor=bound;
        }
      }
    }
    @Override
    public OmniIterator.OfRef<E> iterator()
    {
      return new Itr<E>(this);
    }
    private static class ListItr<E> extends Itr<E> implements OmniListIterator.OfRef<E>
    {
      transient int lastRet;
      private ListItr(UncheckedList<E> parent)
      {
        super(parent);
        this.lastRet=-1;
      }
      private ListItr(UncheckedList<E> parent,int cursor)
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
      @SuppressWarnings("unchecked")
      @Override
      public E next()
      {
        int lastRet;
        this.lastRet=lastRet=this.cursor++;
        return (E)parent.arr[lastRet];
      }
      @Override
      public void remove()
      {
        final UncheckedList<E> root;
        final int lastRet;
        OmniArray.OfRef.removeIndexAndPullDown((root=this.parent).arr,lastRet=this.lastRet,--root.size);
        this.cursor=lastRet;
      }
      @Override
      public void forEachRemaining(Consumer<? super E> action)
      {
        int cursor;
        final int bound;
        final UncheckedList<E> parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size))
        {
          OmniArray.OfRef.ascendingForEach(parent.arr,cursor,cursor=bound-1,action);
          this.lastRet=cursor;
          this.cursor=bound;
        }
      }
      @SuppressWarnings("unchecked")
      @Override
      public E previous()
      {
        final int lastRet;
        this.lastRet=lastRet=--this.cursor;
        return (E)parent.arr[lastRet];
      }
      @Override
      public void set(E val)
      {
        parent.arr[this.lastRet]=val;
      }
      @Override
      public void add(E val)
      {
        final UncheckedList<E> root;
        final int rootSize;
        if((rootSize=(root=this.parent).size)!=0)
        {
          ((UncheckedList<E>)root).uncheckedInsert(this.cursor++,rootSize,val);
        }
        else
        {
          ((RefArrSeq<E>)root).uncheckedInit(val);
          ++this.cursor;
        }
      }
    }
    @Override
    public OmniListIterator.OfRef<E> listIterator()
    {
      return new ListItr<E>(this);
    }
    @Override
    public OmniListIterator.OfRef<E> listIterator(int index)
    {
      return new ListItr<E>(this,index);
    }
      private void uncheckedInsert(int index,int size,E val)
      {
        final int tailDist;
        if((tailDist=size-index)==0)
        {
          super.uncheckedAppend(size,val);
        }
        else
        {
          Object[] arr;
          if((arr=this.arr).length==size)
          {
            final Object[] tmp;
            ArrCopy.semicheckedCopy(arr,0,tmp=new Object[OmniArray.growBy50Pct(size)],0,index);
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
    @Override
    public void add(int index,E val)
    {
      final int size;
      if((size=this.size)!=0){
        ((UncheckedList<E>)this).uncheckedInsert(index,size,val);
      }else{
        ((RefArrSeq<E>)this).uncheckedInit(val);
      }
    }
    @Override
    void uncheckedCopyInto(Object[] dst,int length)
    {
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @SuppressWarnings("unchecked")
    @Override
    public E get(int index)
    {
      return (E)this.arr[index];
    }
    @Override
    public void put(int index,E val)
    {
      this.arr[index]=val;
    }
    @SuppressWarnings("unchecked")
    @Override
    public E set(int index,E val)
    {
      final Object[] arr;
      final var ret=(E)(arr=this.arr)[index];
      arr[index]=val;
      return ret;
    }
    @SuppressWarnings("unchecked")
    @Override
    public E remove(int index)
    {
      final Object[] arr;
      final var ret=(E)(arr=this.arr)[index];
      OmniArray.OfRef.removeIndexAndPullDown(arr,index,--size);
      return ret;
    }
    @Override
    public void replaceAll(UnaryOperator<E> operator)
    {
      final int size;
      if((size=this.size)!=0)
      {
        OmniArray.OfRef.uncheckedReplaceAll(this.arr,0,size,operator);
      }
    }
    @Override
    public void sort(Comparator<? super E> sorter)
    {
      final int size;
      if((size=this.size)>1)
      {
        if(sorter==null)
        {
          RefSortUtil.uncheckedStableAscendingSort(this.arr,0,size);
        }
        else
        {
          RefSortUtil.uncheckedStableSort(this.arr,0,size,sorter);
        }
      }
    }
    @Override
    public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)>1){
        RefSortUtil.uncheckedStableAscendingSort(this.arr,0,size);
      }
    }
    @Override
    public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1){
        RefSortUtil.uncheckedStableDescendingSort(this.arr,0,size);
      }
    }
    @Override
    public void unstableSort(Comparator<? super E> sorter)
    {
      final int size;
      if((size=this.size)>1)
      {
        if(sorter==null)
        {
          RefSortUtil.uncheckedUnstableAscendingSort(this.arr,0,size);
        }
        else
        {
          RefSortUtil.uncheckedUnstableSort(this.arr,0,size,sorter);
        }
      }
    }
    @Override
    public void unstableAscendingSort()
    {
      final int size;
      if((size=this.size)>1){
        RefSortUtil.uncheckedUnstableAscendingSort(this.arr,0,size);
      }
    }
    @Override
    public void unstableDescendingSort()
    {
      final int size;
      if((size=this.size)>1){
        RefSortUtil.uncheckedUnstableDescendingSort(this.arr,0,size);
      }
    }
    @Override
    public OmniList.OfRef<E> subList(int fromIndex,int toIndex)
    {
      return new UncheckedSubList<E>(this,fromIndex,toIndex-fromIndex);
    }
  }
  public
    static class UncheckedSubList<E>
      implements OmniList.OfRef<E>,Cloneable
  {
    transient final int rootOffset;
    transient int size;
    transient final UncheckedList<E> root;
    transient final UncheckedSubList<E> parent;
    private UncheckedSubList(UncheckedList<E> root,int rootOffset,int size)
    {
      super();
      this.root=root;
      this.parent=null;
      this.rootOffset=rootOffset;
      this.size=size;
    }
    private UncheckedSubList(UncheckedSubList<E> parent,int rootOffset,int size)
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
      //TODO implements equals method for UncheckedSubList<E>
      return false;
    }
    @Override
    public Object clone()
    {
      final Object[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(root.arr,rootOffset,copy=new Object[size],0,size);
      }
      else
      {
        copy=OmniArray.OfRef.DEFAULT_ARR;
      }
      return new UncheckedList<E>(size,copy);
    }
    @Override
    public String toString()
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          final StringBuilder builder;
          final int rootOffset;
          OmniArray.OfRef.ascendingToString(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,builder=new StringBuilder("["));
          return builder.append(']').toString();
        }
        return "[]";
      }
    }
    @Override
    public int hashCode()
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          return OmniArray.OfRef.ascendingSeqHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
        }
        return 1;
      }
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
        final UncheckedList<E> root;
        (root=this.root).size=OmniArray.OfRef.removeRangeAndPullDown(root.arr,this.rootOffset+size,root.size,size);
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
        return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean contains(int val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred(val));
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
        final int rootOffset;
        return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean contains(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final int rootOffset;
        return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean contains(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final int rootOffset;
        return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean contains(Object val)
    {
      final int size;
      if((size=this.size)!=0){
        if(val!=null){
          final int rootOffset;
          return OmniArray.OfRef.uncheckedcontainsNonNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,val);
        }
        final int rootOffset;
        return OmniArray.OfRef.uncheckedcontainsNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
    //#ELSE
    //    if(val instanceof E)
    //    {
    //  #IF OfDouble,OfFloat
    //      final Object v;
    //      if((v=(Object)val)==v)
    //      {
    //        #MACRO ReturnUncheckedQueryBits($convertToBits$(v))
    //      }
    //      #MACRO ReturnUncheckedQueryNaN()
    //  #ELSE
    //      #MACRO ReturnUncheckedQuery(OmniPred.OfRef.getEqualsPred(val))
    //  #ENDIF
    //    }
    //#ENDIF
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
          return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred(val));
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
          return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred(val));
        }
      }
      return false;
    }
    @Override
    public boolean contains(short val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred(val));
        }
      }
      return false;
    }
    @Override
    public boolean contains(Boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          final int rootOffset;
          return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred((boolean)(val)));
        }
        final int rootOffset;
        return OmniArray.OfRef.uncheckedcontainsNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
      }
      return false;
    }
    @Override
    public boolean contains(Byte val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          final int rootOffset;
          return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred((byte)(val)));
        }
        final int rootOffset;
        return OmniArray.OfRef.uncheckedcontainsNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
      }
      return false;
    }
    @Override
    public boolean contains(Character val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          final int rootOffset;
          return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred((char)(val)));
        }
        final int rootOffset;
        return OmniArray.OfRef.uncheckedcontainsNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
      }
      return false;
    }
    @Override
    public boolean contains(Short val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          final int rootOffset;
          return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred((short)(val)));
        }
        final int rootOffset;
        return OmniArray.OfRef.uncheckedcontainsNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
      }
      return false;
    }
    @Override
    public boolean contains(Integer val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          final int rootOffset;
          return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred((int)(val)));
        }
        final int rootOffset;
        return OmniArray.OfRef.uncheckedcontainsNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
      }
      return false;
    }
    @Override
    public boolean contains(Long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          final int rootOffset;
          return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred((long)(val)));
        }
        final int rootOffset;
        return OmniArray.OfRef.uncheckedcontainsNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
      }
      return false;
    }
    @Override
    public boolean contains(Float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          final int rootOffset;
          return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred((float)(val)));
        }
        final int rootOffset;
        return OmniArray.OfRef.uncheckedcontainsNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
      }
      return false;
    }
    @Override
    public boolean contains(Double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          final int rootOffset;
          return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred((double)(val)));
        }
        final int rootOffset;
        return OmniArray.OfRef.uncheckedcontainsNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
      }
      return false;
    }
    @Override
    public boolean removeVal(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeVal(int val)
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
    @Override
    public boolean removeVal(long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeVal(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean removeVal(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
      }
      return false;
    }
    @Override
    public boolean remove(Object val)
    {
      final int size;
      if((size=this.size)!=0){
        if(val!=null){
          return this.uncheckedremoveValNonNull(size,val);
        }
        return this.uncheckedremoveValNull(size);
    //#ELSE
    //    if(val instanceof E)
    //    {
    //  #IF OfDouble,OfFloat
    //      final Object v;
    //      if((v=(Object)val)==v)
    //      {
    //        #MACRO ReturnUncheckedQueryBits($convertToBits$(v))
    //      }
    //      #MACRO ReturnUncheckedQueryNaN()
    //  #ELSE
    //      #MACRO ReturnUncheckedQuery(OmniPred.OfRef.getEqualsPred(val))
    //  #ENDIF
    //    }
    //#ENDIF
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
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
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
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
        }
      }
      return false;
    }
    @Override
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
    @Override
    public boolean removeVal(Boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((boolean)(val)));
        }
        return this.uncheckedremoveValNull(size);
      }
      return false;
    }
    @Override
    public boolean removeVal(Byte val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((byte)(val)));
        }
        return this.uncheckedremoveValNull(size);
      }
      return false;
    }
    @Override
    public boolean removeVal(Character val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((char)(val)));
        }
        return this.uncheckedremoveValNull(size);
      }
      return false;
    }
    @Override
    public boolean removeVal(Short val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((short)(val)));
        }
        return this.uncheckedremoveValNull(size);
      }
      return false;
    }
    @Override
    public boolean removeVal(Integer val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((int)(val)));
        }
        return this.uncheckedremoveValNull(size);
      }
      return false;
    }
    @Override
    public boolean removeVal(Long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((long)(val)));
        }
        return this.uncheckedremoveValNull(size);
      }
      return false;
    }
    @Override
    public boolean removeVal(Float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((float)(val)));
        }
        return this.uncheckedremoveValNull(size);
      }
      return false;
    }
    @Override
    public boolean removeVal(Double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((double)(val)));
        }
        return this.uncheckedremoveValNull(size);
      }
      return false;
    }
    @Override
    public int indexOf(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
      }
      return -1;
    }
    @Override
    public int indexOf(int val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
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
        return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
      }
      return -1;
    }
    @Override
    public int indexOf(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
      }
      return -1;
    }
    @Override
    public int indexOf(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
      }
      return -1;
    }
    @Override
    public int indexOf(Object val)
    {
      final int size;
      if((size=this.size)!=0){
        if(val!=null){
          return OmniArray.OfRef.uncheckedindexOfNonNull(root.arr,this.rootOffset,size,val);
        }
        return OmniArray.OfRef.uncheckedindexOfNull(root.arr,this.rootOffset,size);
    //#ELSE
    //    if(val instanceof E)
    //    {
    //  #IF OfDouble,OfFloat
    //      final Object v;
    //      if((v=(Object)val)==v)
    //      {
    //        #MACRO ReturnUncheckedQueryBits($convertToBits$(v))
    //      }
    //      #MACRO ReturnUncheckedQueryNaN()
    //  #ELSE
    //      #MACRO ReturnUncheckedQuery(OmniPred.OfRef.getEqualsPred(val))
    //  #ENDIF
    //    }
    //#ENDIF
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
          return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
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
          return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
        }
      }
      return -1;
    }
    @Override
    public int indexOf(short val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
        }
      }
      return -1;
    }
    @Override
    public int indexOf(Boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((boolean)(val)));
        }
        return OmniArray.OfRef.uncheckedindexOfNull(root.arr,this.rootOffset,size);
      }
      return -1;
    }
    @Override
    public int indexOf(Byte val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((byte)(val)));
        }
        return OmniArray.OfRef.uncheckedindexOfNull(root.arr,this.rootOffset,size);
      }
      return -1;
    }
    @Override
    public int indexOf(Character val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((char)(val)));
        }
        return OmniArray.OfRef.uncheckedindexOfNull(root.arr,this.rootOffset,size);
      }
      return -1;
    }
    @Override
    public int indexOf(Short val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((short)(val)));
        }
        return OmniArray.OfRef.uncheckedindexOfNull(root.arr,this.rootOffset,size);
      }
      return -1;
    }
    @Override
    public int indexOf(Integer val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((int)(val)));
        }
        return OmniArray.OfRef.uncheckedindexOfNull(root.arr,this.rootOffset,size);
      }
      return -1;
    }
    @Override
    public int indexOf(Long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((long)(val)));
        }
        return OmniArray.OfRef.uncheckedindexOfNull(root.arr,this.rootOffset,size);
      }
      return -1;
    }
    @Override
    public int indexOf(Float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((float)(val)));
        }
        return OmniArray.OfRef.uncheckedindexOfNull(root.arr,this.rootOffset,size);
      }
      return -1;
    }
    @Override
    public int indexOf(Double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((double)(val)));
        }
        return OmniArray.OfRef.uncheckedindexOfNull(root.arr,this.rootOffset,size);
      }
      return -1;
    }
    @Override
    public int lastIndexOf(boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
      }
      return -1;
    }
    @Override
    public int lastIndexOf(int val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
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
        return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
      }
      return -1;
    }
    @Override
    public int lastIndexOf(float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
      }
      return -1;
    }
    @Override
    public int lastIndexOf(double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
      }
      return -1;
    }
    @Override
    public int lastIndexOf(Object val)
    {
      final int size;
      if((size=this.size)!=0){
        if(val!=null){
          return OmniArray.OfRef.uncheckedlastIndexOfNonNull(root.arr,this.rootOffset,size,val);
        }
        return OmniArray.OfRef.uncheckedlastIndexOfNull(root.arr,this.rootOffset,size);
    //#ELSE
    //    if(val instanceof E)
    //    {
    //  #IF OfDouble,OfFloat
    //      final Object v;
    //      if((v=(Object)val)==v)
    //      {
    //        #MACRO ReturnUncheckedQueryBits($convertToBits$(v))
    //      }
    //      #MACRO ReturnUncheckedQueryNaN()
    //  #ELSE
    //      #MACRO ReturnUncheckedQuery(OmniPred.OfRef.getEqualsPred(val))
    //  #ENDIF
    //    }
    //#ENDIF
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
          return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
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
          return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(short val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
        }
      }
      return -1;
    }
    @Override
    public int lastIndexOf(Boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((boolean)(val)));
        }
        return OmniArray.OfRef.uncheckedlastIndexOfNull(root.arr,this.rootOffset,size);
      }
      return -1;
    }
    @Override
    public int lastIndexOf(Byte val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((byte)(val)));
        }
        return OmniArray.OfRef.uncheckedlastIndexOfNull(root.arr,this.rootOffset,size);
      }
      return -1;
    }
    @Override
    public int lastIndexOf(Character val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((char)(val)));
        }
        return OmniArray.OfRef.uncheckedlastIndexOfNull(root.arr,this.rootOffset,size);
      }
      return -1;
    }
    @Override
    public int lastIndexOf(Short val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((short)(val)));
        }
        return OmniArray.OfRef.uncheckedlastIndexOfNull(root.arr,this.rootOffset,size);
      }
      return -1;
    }
    @Override
    public int lastIndexOf(Integer val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((int)(val)));
        }
        return OmniArray.OfRef.uncheckedlastIndexOfNull(root.arr,this.rootOffset,size);
      }
      return -1;
    }
    @Override
    public int lastIndexOf(Long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((long)(val)));
        }
        return OmniArray.OfRef.uncheckedlastIndexOfNull(root.arr,this.rootOffset,size);
      }
      return -1;
    }
    @Override
    public int lastIndexOf(Float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((float)(val)));
        }
        return OmniArray.OfRef.uncheckedlastIndexOfNull(root.arr,this.rootOffset,size);
      }
      return -1;
    }
    @Override
    public int lastIndexOf(Double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((double)(val)));
        }
        return OmniArray.OfRef.uncheckedlastIndexOfNull(root.arr,this.rootOffset,size);
      }
      return -1;
    }
    private boolean uncheckedremoveValNonNull(int size,Object nonNull)
    {
      final UncheckedList<E> root;
      final var arr=(root=this.root).arr;
      {
        for(int index=this.rootOffset,bound=index+(--size);;++index)
        {
          if(nonNull.equals(arr[index]))
          {
            for(var curr=parent;curr!=null;--curr.size,curr=curr.parent){}
            OmniArray.OfRef.removeIndexAndPullDown(arr,index,--root.size);
            this.size=size;
            return true;
          }
          if(index==bound)
          {
            return false;
          }
        }
      }
    }
    private boolean uncheckedremoveValNull(int size
    )
    {
      final UncheckedList<E> root;
      final var arr=(root=this.root).arr;
      for(int index=this.rootOffset,bound=index+(--size);;++index)
      {
        if(
        arr[index]==null
        )
        {
          for(var curr=parent;curr!=null;--curr.size,curr=curr.parent){}
          OmniArray.OfRef.removeIndexAndPullDown(arr,index,--root.size);
          this.size=size;
          return true;
        }
        if(index==bound)
        {
          return false;
        }
      }
    }
    private boolean uncheckedremoveVal (int size
    ,Predicate<Object> pred
    )
    {
      final UncheckedList<E> root;
      final var arr=(root=this.root).arr;
      for(int index=this.rootOffset,bound=index+(--size);;++index)
      {
        if(
        pred.test(arr[index])
        )
        {
          for(var curr=parent;curr!=null;--curr.size,curr=curr.parent){}
          OmniArray.OfRef.removeIndexAndPullDown(arr,index,--root.size);
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
    public void forEach(Consumer<? super E> action)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          OmniArray.OfRef.ascendingForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,action);
        }
      }
    }
    private static class Itr<E>
      implements OmniIterator.OfRef<E>
    {
      transient final UncheckedSubList<E> parent;
      transient int cursor;
      private Itr(UncheckedSubList<E> parent)
      {
        this.parent=parent;
        this.cursor=parent.rootOffset;
      }
      private Itr(UncheckedSubList<E> parent,int cursor)
      {
        this.parent=parent;
        this.cursor=cursor;
      }
      @Override
      public boolean hasNext()
      {
        final UncheckedSubList<E> parent;
        return this.cursor<(parent=this.parent).rootOffset+parent.size;
      }
      @SuppressWarnings("unchecked")
      @Override
      public E next()
      {
        return (E)parent.root.arr[cursor++];
      }
      @Override
      public void remove()
      {
        UncheckedSubList<E> parent;
        final UncheckedList<E> root;
        OmniArray.OfRef.removeIndexAndPullDown((root=(parent=this.parent).root).arr,--this.cursor,--root.size);
        do{
          --parent.size;
        }while((parent=parent.parent)!=null);
      }
      @Override
      public void forEachRemaining(Consumer<? super E> action)
      {
        final int cursor,bound;
        final UncheckedSubList<E> parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
        {
          OmniArray.OfRef.ascendingForEach(parent.root.arr,cursor,bound-1,action);
          this.cursor=bound;
        }
      }
    }
    @Override
    public OmniIterator.OfRef<E> iterator()
    {
      return new Itr<E>(this);
    }
    private static class ListItr<E> extends Itr<E> implements OmniListIterator.OfRef<E>
    {
      transient int lastRet;
      private ListItr(UncheckedSubList<E> parent)
      {
        super(parent);
        this.lastRet=-1;
      }
      private ListItr(UncheckedSubList<E> parent,int cursor)
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
      @SuppressWarnings("unchecked")
      @Override
      public E next()
      {
        int lastRet;
        this.lastRet=lastRet=this.cursor++;
        return (E)parent.root.arr[lastRet];
      }
      @Override
      public void remove()
      {
        UncheckedSubList<E> parent;
        final UncheckedList<E> root;
        final int lastRet;
        OmniArray.OfRef.removeIndexAndPullDown((root=(parent=this.parent).root).arr,lastRet=this.lastRet,--root.size);
        this.cursor=lastRet;
        do{
          --parent.size;
        }while((parent=parent.parent)!=null);
      }
      @Override
      public void forEachRemaining(Consumer<? super E> action)
      {
        int cursor;
        final int bound;
        final UncheckedSubList<E> parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
        {
          OmniArray.OfRef.ascendingForEach(parent.root.arr,cursor,cursor=bound-1,action);
          this.lastRet=cursor;
          this.cursor=bound;
        }
      }
      @SuppressWarnings("unchecked")
      @Override
      public E previous()
      {
        final int lastRet;
        this.lastRet=lastRet=--this.cursor;
        return (E)parent.root.arr[lastRet];
      }
      @Override
      public void set(E val)
      {
        parent.root.arr[this.lastRet]=val;
      }
      @Override
      public void add(E val)
      {
        final UncheckedList<E> root;
        final int rootSize;
        UncheckedSubList<E> parent;
        if((rootSize=(root=(parent=this.parent).root).size)!=0)
        {
          ((UncheckedList<E>)root).uncheckedInsert(this.cursor++,rootSize,val);
        }
        else
        {
          ((RefArrSeq<E>)root).uncheckedInit(val);
          ++this.cursor;
        }
        do{
          ++parent.size;
        }while((parent=parent.parent)!=null);
      }
    }
    @Override
    public OmniListIterator.OfRef<E> listIterator()
    {
      return new ListItr<E>(this);
    }
    @Override
    public OmniListIterator.OfRef<E> listIterator(int index)
    {
      return new ListItr<E>(this,index+this.rootOffset);
    }
    @Override
    public boolean add(E val)
    {
      for(var curr=parent;curr!=null;++curr.size,curr=curr.parent){}
      final UncheckedList<E> root;
      final int rootSize;
      if((rootSize=(root=this.root).size)!=0){
        ((UncheckedList<E>)root).uncheckedInsert(this.rootOffset+(this.size++),rootSize,val);
      }else{
        ((RefArrSeq<E>)root).uncheckedInit(val);
        ++this.size;
      }
      return true;
    }
    @Override
    public void add(int index,E val)
    {
      for(var curr=parent;curr!=null;++curr.size,curr=curr.parent){}
      ++this.size;
      final UncheckedList<E> root;
      final int rootSize;
      if((rootSize=(root=this.root).size)!=0){
        ((UncheckedList<E>)root).uncheckedInsert(this.rootOffset+index,rootSize,val);
      }else{
        ((RefArrSeq<E>)root).uncheckedInit(val);
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
    public Object[] toArray()
    {
      final int size;
      if((size=this.size)!=0)
      {
        final Object[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new Object[size],0,size);
        return dst;
      }
      return OmniArray.OfRef.DEFAULT_ARR;
    }
    @SuppressWarnings("unchecked")
    @Override
    public E get(int index)
    {
      return (E)root.arr[index+this.rootOffset];
    }
    @Override
    public void put(int index,E val)
    {
      root.arr[index+this.rootOffset]=val;
    }
    @SuppressWarnings("unchecked")
    @Override
    public E set(int index,E val)
    {
      final Object[] arr;
      final var ret=(E)(arr=root.arr)[index+=this.rootOffset];
      arr[index]=val;
      return ret;
    }
    @SuppressWarnings("unchecked")
    @Override
    public E remove(int index)
    {
      final Object[] arr;
      for(var curr=parent;curr!=null;--curr.size,curr=curr.parent){}
      final UncheckedList<E> root;
      final var ret=(E)(arr=(root=this.root).arr)[index+=this.rootOffset];
      OmniArray.OfRef.removeIndexAndPullDown(arr,index,--root.size);
      this.size=size-1;
      return ret;
    }
    @Override
    public boolean removeIf(Predicate<? super E> filter)
    {
      final int size;
      if((size=this.size)!=0)
      {
        {
          final Object[] arr;
          final int numRemoved;
          int rootOffset;
          final UncheckedList<E> root;
          if((numRemoved=uncheckedRemoveIfImpl(arr=(root=this.root).arr,rootOffset=this.rootOffset,rootOffset+=size,filter))!=0){
            for(var curr=parent;curr!=null;curr.size-=numRemoved,curr=curr.parent){}
            root.size=OmniArray.OfRef.removeRangeAndPullDown(arr,rootOffset,root.size,numRemoved);
            this.size=size-numRemoved;
            return true;
          }
        }
      }
      return false;
    }
    @Override
    public void replaceAll(UnaryOperator<E> operator)
    {
      final int size;
      if((size=this.size)!=0)
      {
        final int rootOffset;
        OmniArray.OfRef.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);  
      }
    }
    @Override
    public void sort(Comparator<? super E> sorter)
    {
      final int size;
      if((size=this.size)>1)
      {
        final int rootOffset;
        if(sorter==null)
        {
          RefSortUtil.uncheckedStableAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }
        else
        {
          RefSortUtil.uncheckedStableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter);
        }
      }
    }
    @Override
    public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)>1){
        final int rootOffset;
        RefSortUtil.uncheckedStableAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }
    }
    @Override
    public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1){
        final int rootOffset;
        RefSortUtil.uncheckedStableDescendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }
    }
    @Override
    public void unstableSort(Comparator<? super E> sorter)
    {
      final int size;
      if((size=this.size)>1)
      {
        final int rootOffset;
        if(sorter==null)
        {
          RefSortUtil.uncheckedUnstableAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }
        else
        {
          RefSortUtil.uncheckedUnstableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter);
        }
      }
    }
    @Override
    public void unstableAscendingSort()
    {
      final int size;
      if((size=this.size)>1){
        final int rootOffset;
        RefSortUtil.uncheckedUnstableAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }
    }
    @Override
    public void unstableDescendingSort()
    {
      final int size;
      if((size=this.size)>1){
        final int rootOffset;
        RefSortUtil.uncheckedUnstableDescendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }
    }
    @Override
    public OmniList.OfRef<E> subList(int fromIndex,int toIndex)
    {
      return new UncheckedSubList<E>(this,this.rootOffset+fromIndex,toIndex-fromIndex);
    }
  }
  public
    static class CheckedStack<E>
      extends UncheckedStack<E>
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
    CheckedStack(int size,Object[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implements equals method for CheckedStack<E>
      return false;
    }
    @Override
    public Object clone()
    {
      final Object[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new Object[size],0,size);
      }
      else
      {
        copy=OmniArray.OfRef.DEFAULT_ARR;
      }
      return new CheckedStack<E>(size,copy);
    }
    @Override
    void uncheckedToString(int size,StringBuilder builder)
    {
      final int modCount=this.modCount;
      try
      {
        super.uncheckedToString(size,builder);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override
    int uncheckedHashCode(int size)
    {
      final int modCount=this.modCount;
      try
      {
        return super.uncheckedHashCode(size);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override
    public void clear()
    {
      final int size;
      if((size=this.size)!=0)
      {
        ++this.modCount;
        this.size=0;
        OmniArray.OfRef.nullifyRange(this.arr,size-1,0);
      }
    }
    @Override
    public boolean contains(Object val)
    {
      final int size;
      if((size=this.size)!=0){
        if(val!=null){
          final int modCount=this.modCount;
          try{
            return OmniArray.OfRef.uncheckedcontainsNonNull(this.arr,0,size-1,val);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
        return OmniArray.OfRef.uncheckedcontainsNull(this.arr,0,size-1);
    //#ELSE
    //    if(val instanceof E)
    //    {
    //  #IF OfDouble,OfFloat
    //      final Object v;
    //      if((v=(Object)val)==v)
    //      {
    //        #MACRO ReturnUncheckedQueryBits($convertToBits$(v))
    //      }
    //      #MACRO ReturnUncheckedQueryNaN()
    //  #ELSE
    //      #MACRO ReturnUncheckedQuery(OmniPred.OfRef.getEqualsPred(val))
    //  #ENDIF
    //    }
    //#ENDIF
      }
      return false;
    }
    @Override
    public int search(Object val)
    {
      final int size;
      if((size=this.size)!=0){
        if(val!=null){
          final int modCount=this.modCount;
          try{
            return OmniArray.OfRef.uncheckedsearchNonNull(this.arr,size,val);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
        return OmniArray.OfRef.uncheckedsearchNull(this.arr,size);  
    //#ELSE
    //    if(val instanceof E)
    //    {
    //  #IF OfDouble,OfFloat
    //      final Object v;
    //      if((v=(Object)val)==v)
    //      {
    //        #MACRO ReturnUncheckedQueryBits($convertToBits$(v))
    //      }
    //      #MACRO ReturnUncheckedQueryNaN()
    //  #ELSE
    //      #MACRO ReturnUncheckedQuery(OmniPred.OfRef.getEqualsPred(val))
    //  #ENDIF
    //    }
    //#ENDIF
      }
      return -1;
    }
    @Override
    boolean uncheckedremoveValNonNull(int size,Object nonNull)
    {
      int modCount=this.modCount;
      try
      {
        final var arr=this.arr;
        for(int index=--size;;--index)
        {
          if(nonNull.equals(arr[index]))
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.modCount=modCount+1;
            OmniArray.OfRef.removeIndexAndPullDown(arr,index,size);
            this.size=size;
            return true;
          }
          if(index==0)
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
            return false;
          }
        }
      }
      catch(final ConcurrentModificationException e)
      {
        throw e;
      }
      catch(final RuntimeException e)
      {
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
    }
    @Override
    boolean uncheckedremoveValNull(int size
    )
    {
      final var arr=this.arr;
      for(int index=--size;;--index)
      {
        if(
        arr[index]==null
        )
        {
          ++this.modCount;
          OmniArray.OfRef.removeIndexAndPullDown(arr,index,size);
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
    boolean uncheckedremoveVal (int size
    ,Predicate<Object> pred
    )
    {
      final var arr=this.arr;
      for(int index=--size;;--index)
      {
        if(
        pred.test(arr[index])
        )
        {
          ++this.modCount;
          OmniArray.OfRef.removeIndexAndPullDown(arr,index,size);
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
    void uncheckedForEach(int size,Consumer<? super E> action)
    {
      int modCount=this.modCount;
      try
      {
        OmniArray.OfRef.descendingForEach(this.arr,0,size-1,action);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    private static class Itr<E>
      implements OmniIterator.OfRef<E>
    {
      transient final CheckedStack<E> parent;
      transient int cursor;
      transient int lastRet;
      transient int modCount;
      private Itr(CheckedStack<E> parent)
      {
        this.parent=parent;
        this.cursor=parent.size;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      private Itr(CheckedStack<E> parent,int cursor)
      {
        this.parent=parent;
        this.cursor=cursor;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      @Override
      public boolean hasNext()
      {
        return this.cursor>0;
      }
      @SuppressWarnings("unchecked")
      @Override
      public E next()
      {
        final CheckedStack<E> root;
        CheckedCollection.checkModCount(modCount,(root=this.parent).modCount);
        int cursor;
        if((cursor=this.cursor)>0)
        {
          this.lastRet=--cursor;
          this.cursor=cursor;
            return (E)root.arr[cursor];
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
          final CheckedStack<E> root;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.parent).modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          OmniArray.OfRef.removeIndexAndPullDown(root.arr,lastRet,--root.size);
          this.cursor=lastRet;
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override
      public void forEachRemaining(Consumer<? super E> action)
      {
        final int cursor;
        if((cursor=this.cursor)>0)
        {
          final int modCount=this.modCount;
          final var parent=this.parent;
          try
          {
            OmniArray.OfRef.descendingForEach(parent.arr,0,cursor-1,action);
          }
          finally
          {
            CheckedCollection.checkModCount(modCount,parent.modCount);
          }
          this.cursor=0;
          this.lastRet=0;
        }
      }
    }
    @Override
    public OmniIterator.OfRef<E> iterator()
    {
      return new Itr<E>(this);
    }
    @Override
    public void push(E val)
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
    @SuppressWarnings("unchecked")
    @Override
    public E pop()
    {
      int size;
      if((size=this.size)!=0)
      {
        ++this.modCount;
        final Object[] arr;
        final var ret=(E)(arr=this.arr)[--size];
        arr[size]=null;
        this.size=size;
        return ret;
      }
      throw new NoSuchElementException();
    }
    @SuppressWarnings("unchecked")
    @Override
    public E poll()
    {
      int size;
      if((size=this.size)!=0)
      {
        ++this.modCount;
        final var ret=(E)(arr[--size]);
        arr[size]=null;
        this.size=size;
        return ret;
      }
      return null;
    }
    @Override
    boolean uncheckedRemoveIf(int size,Predicate<? super E> filter)
    {
      final int modCount=this.modCount;
      try
      {
        final int numRemoved;
        final Object[] arr;
        if((numRemoved=uncheckedRemoveIfImpl(arr=this.arr
          ,0,size,filter,new CheckedCollection.AbstractModCountChecker(modCount){
            @Override protected int getActualModCount(){
            return CheckedStack.this.modCount;
            }
          }))
          !=0
          ){
          this.modCount=modCount+1;
          OmniArray.OfRef.nullifyRange(arr,size-1,size-=numRemoved);
          this.size=size;
          return true;
        }
      }
      catch(ConcurrentModificationException e)
      {
        throw e;
      }
      catch(RuntimeException e)
      {
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return false;
    }
  }
  public
    static class CheckedList<E>
      extends UncheckedList<E>
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
    CheckedList(int size,Object[] arr)
    {
      super(size,arr);
    }
    @Override
    public boolean equals(Object val)
    {
      //TODO implements equals method for CheckedList<E>
      return false;
    }
    @Override
    public Object clone()
    {
      final Object[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(this.arr,0,copy=new Object[size],0,size);
      }
      else
      {
        copy=OmniArray.OfRef.DEFAULT_ARR;
      }
      return new CheckedList<E>(size,copy);
    }
    @Override
    void uncheckedToString(int size,StringBuilder builder)
    {
      final int modCount=this.modCount;
      try
      {
        super.uncheckedToString(size,builder);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override
    int uncheckedHashCode(int size)
    {
      final int modCount=this.modCount;
      try
      {
        return super.uncheckedHashCode(size);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override
    public void clear()
    {
      final int size;
      if((size=this.size)!=0)
      {
        ++this.modCount;
        this.size=0;
        OmniArray.OfRef.nullifyRange(this.arr,size-1,0);
      }
    }
    @Override
    public boolean contains(Object val)
    {
      final int size;
      if((size=this.size)!=0){
        if(val!=null){
          final int modCount=this.modCount;
          try{
            return OmniArray.OfRef.uncheckedcontainsNonNull(this.arr,0,size-1,val);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
        return OmniArray.OfRef.uncheckedcontainsNull(this.arr,0,size-1);
    //#ELSE
    //    if(val instanceof E)
    //    {
    //  #IF OfDouble,OfFloat
    //      final Object v;
    //      if((v=(Object)val)==v)
    //      {
    //        #MACRO ReturnUncheckedQueryBits($convertToBits$(v))
    //      }
    //      #MACRO ReturnUncheckedQueryNaN()
    //  #ELSE
    //      #MACRO ReturnUncheckedQuery(OmniPred.OfRef.getEqualsPred(val))
    //  #ENDIF
    //    }
    //#ENDIF
      }
      return false;
    }
    @Override
    public int indexOf(Object val)
    {
      final int size;
      if((size=this.size)!=0){
        if(val!=null){
          final int modCount=this.modCount;
          try{
            return OmniArray.OfRef.uncheckedindexOfNonNull(this.arr,size,val);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
        return OmniArray.OfRef.uncheckedindexOfNull(this.arr,size);  
    //#ELSE
    //    if(val instanceof E)
    //    {
    //  #IF OfDouble,OfFloat
    //      final Object v;
    //      if((v=(Object)val)==v)
    //      {
    //        #MACRO ReturnUncheckedQueryBits($convertToBits$(v))
    //      }
    //      #MACRO ReturnUncheckedQueryNaN()
    //  #ELSE
    //      #MACRO ReturnUncheckedQuery(OmniPred.OfRef.getEqualsPred(val))
    //  #ENDIF
    //    }
    //#ENDIF
      }
      return -1;
    }
    @Override
    public int lastIndexOf(Object val)
    {
      final int size;
      if((size=this.size)!=0){
        if(val!=null){
          final int modCount=this.modCount;
          try{
            return OmniArray.OfRef.uncheckedlastIndexOfNonNull(this.arr,size,val);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
          }
        }
        return OmniArray.OfRef.uncheckedlastIndexOfNull(this.arr,size);  
    //#ELSE
    //    if(val instanceof E)
    //    {
    //  #IF OfDouble,OfFloat
    //      final Object v;
    //      if((v=(Object)val)==v)
    //      {
    //        #MACRO ReturnUncheckedQueryBits($convertToBits$(v))
    //      }
    //      #MACRO ReturnUncheckedQueryNaN()
    //  #ELSE
    //      #MACRO ReturnUncheckedQuery(OmniPred.OfRef.getEqualsPred(val))
    //  #ENDIF
    //    }
    //#ENDIF
      }
      return -1;
    }
    @Override
    boolean uncheckedremoveValNonNull(int size,Object nonNull)
    {
      int modCount=this.modCount;
      try
      {
        final var arr=this.arr;
        for(int index=0;;)
        {
          if(nonNull.equals(arr[index]))
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.modCount=modCount+1;
            OmniArray.OfRef.removeIndexAndPullDown(arr,index,--size);
            this.size=size;
            return true;
          }
          if(++index==size)
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
            return false;
          }
        }
      }
      catch(final ConcurrentModificationException e)
      {
        throw e;
      }
      catch(final RuntimeException e)
      {
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
    }
    @Override
    boolean uncheckedremoveValNull(int size
    )
    {
      final var arr=this.arr;
      for(int index=0;;)
      {
        if(
        arr[index]==null
        )
        {
          ++this.modCount;
          OmniArray.OfRef.removeIndexAndPullDown(arr,index,--size);
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
    boolean uncheckedremoveVal (int size
    ,Predicate<Object> pred
    )
    {
      final var arr=this.arr;
      for(int index=0;;)
      {
        if(
        pred.test(arr[index])
        )
        {
          ++this.modCount;
          OmniArray.OfRef.removeIndexAndPullDown(arr,index,--size);
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
    void uncheckedForEach(int size,Consumer<? super E> action)
    {
      int modCount=this.modCount;
      try
      {
        OmniArray.OfRef.ascendingForEach(this.arr,0,size-1,action);
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    private static class Itr<E>
      implements OmniIterator.OfRef<E>
    {
      transient final CheckedList<E> parent;
      transient int cursor;
      transient int lastRet;
      transient int modCount;
      private Itr(CheckedList<E> parent)
      {
        this.parent=parent;
        this.cursor=0;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      private Itr(CheckedList<E> parent,int cursor)
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
      @SuppressWarnings("unchecked")
      @Override
      public E next()
      {
        final CheckedList<E> root;
        CheckedCollection.checkModCount(modCount,(root=this.parent).modCount);
        final int cursor;
        if((cursor=this.cursor)<root.size)
        {
          this.lastRet=cursor;
          this.cursor=cursor+1;
            return (E)root.arr[cursor];
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
          final CheckedList<E> root;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.parent).modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          OmniArray.OfRef.removeIndexAndPullDown(root.arr,lastRet,--root.size);
          this.cursor=lastRet;
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override
      public void forEachRemaining(Consumer<? super E> action)
      {
        int cursor;
        final int bound;
        final CheckedList<E> parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size))
        {
          final int modCount=this.modCount;
          try
          {
            OmniArray.OfRef.ascendingForEach(parent.arr,cursor,cursor=bound-1,action);
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
    public OmniIterator.OfRef<E> iterator()
    {
      return new Itr<E>(this);
    }
    private static class ListItr<E> extends Itr<E> implements OmniListIterator.OfRef<E>
    {
      private ListItr(CheckedList<E> parent)
      {
        super(parent);
      }
      private ListItr(CheckedList<E> parent,int cursor)
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
      @SuppressWarnings("unchecked")
      @Override
      public E previous()
      {
        final CheckedList<E> root;
        int cursor;
        CheckedCollection.checkModCount(modCount,(root=this.parent).modCount);
        if((cursor=this.cursor)>0)
        {
          this.lastRet=--cursor;
          this.cursor=cursor;
          return (E)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override
      public void set(E val)
      {
        final int lastRet;
        if((lastRet=this.lastRet)!=-1)
        {
          final CheckedList<E> root;
          CheckedCollection.checkModCount(modCount,(root=this.parent).modCount);
          root.arr[lastRet]=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override
      public void add(E val)
      {
        int modCount;
        final CheckedList<E> root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.parent).modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        this.lastRet=-1;
        final int rootSize;
        if((rootSize=root.size)!=0)
        {
          ((UncheckedList<E>)root).uncheckedInsert(this.cursor++,rootSize,val);
        }
        else
        {
          ((RefArrSeq<E>)root).uncheckedInit(val);
          ++this.cursor;
        }
      }
    }
    @Override
    public OmniListIterator.OfRef<E> listIterator()
    {
      return new ListItr<E>(this);
    }
    @Override
    public OmniListIterator.OfRef<E> listIterator(int index)
    {
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,this.size);
      return new ListItr<E>(this,index);
    }
    @Override
    public void push(E val)
    {
      ++this.modCount;
      super.push(val);
    }
    @Override
    public void add(int index,E val)
    {
      final int size;
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,size=this.size);
      ++this.modCount;
      if(size!=0){
        ((UncheckedList<E>)this).uncheckedInsert(index,size,val);
      }else{
        ((RefArrSeq<E>)this).uncheckedInit(val);
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
    @SuppressWarnings("unchecked")
    @Override
    public E get(int index)
    {
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      return (E)this.arr[index];
    }
    @Override
    public void put(int index,E val)
    {
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      this.arr[index]=val;
    }
    @SuppressWarnings("unchecked")
    @Override
    public E set(int index,E val)
    {
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      final Object[] arr;
      final var ret=(E)(arr=this.arr)[index];
      arr[index]=val;
      return ret;
    }
    @SuppressWarnings("unchecked")
    @Override
    public E remove(int index)
    {
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      final Object[] arr;
      ++this.modCount;
      final var ret=(E)(arr=this.arr)[index];
      OmniArray.OfRef.removeIndexAndPullDown(arr,index,--size);
      this.size=size;
      return ret;
    }
    @Override
    boolean uncheckedRemoveIf(int size,Predicate<? super E> filter)
    {
      final int modCount=this.modCount;
      try
      {
        final int numRemoved;
        final Object[] arr;
        if((numRemoved=uncheckedRemoveIfImpl(arr=this.arr
          ,0,size,filter,new CheckedCollection.AbstractModCountChecker(modCount){
            @Override protected int getActualModCount(){
            return CheckedList.this.modCount;
            }
          }))
          !=0
          ){
          this.modCount=modCount+1;
          OmniArray.OfRef.nullifyRange(arr,size-1,size-=numRemoved);
          this.size=size;
          return true;
        }
      }
      catch(ConcurrentModificationException e)
      {
        throw e;
      }
      catch(RuntimeException e)
      {
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return false;
    }
    @Override
    public void replaceAll(UnaryOperator<E> operator)
    {
      final int size;
      if((size=this.size)!=0)
      {
        int modCount=this.modCount;
        try
        {
          OmniArray.OfRef.uncheckedReplaceAll(this.arr,0,size,operator);
        }
        finally
        {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        this.modCount=modCount+1;
      }
    }
    @Override
    public void sort(Comparator<? super E> sorter)
    {
      final int size;
      if((size=this.size)>1)
      {
        final int modCount=this.modCount;
        try
        {
          if(sorter==null)
          {
            RefSortUtil.uncheckedStableAscendingSort(this.arr,0,size);
          }
          else
          {
            RefSortUtil.uncheckedStableSort(this.arr,0,size,sorter);
          }
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
          throw CheckedCollection.checkModCount(modCount,this.modCount,new IllegalArgumentException("Comparison method violates its general contract!",e));
        }
        catch(RuntimeException e)
        {
          throw CheckedCollection.checkModCount(modCount,this.modCount,e);
        }
        CheckedCollection.checkModCount(modCount,this.modCount);
        this.modCount=modCount+1;
      }
    }
    @Override
    public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)>1){
        final int modCount=this.modCount;
        try
        {
          RefSortUtil.uncheckedStableAscendingSort(this.arr,0,size);
        }
        catch(ArrayIndexOutOfBoundsException e){
          throw CheckedCollection.checkModCount(modCount,this.modCount,new IllegalArgumentException("Comparison method violates its general contract!",e));
        }catch(RuntimeException e){
          throw CheckedCollection.checkModCount(modCount,this.modCount,e);
        }
        CheckedCollection.checkModCount(modCount,this.modCount);
        this.modCount=modCount+1;
      }
    }
    @Override
    public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1){
        final int modCount=this.modCount;
        try
        {
          RefSortUtil.uncheckedStableDescendingSort(this.arr,0,size);
        }
        catch(ArrayIndexOutOfBoundsException e){
          throw CheckedCollection.checkModCount(modCount,this.modCount,new IllegalArgumentException("Comparison method violates its general contract!",e));
        }catch(RuntimeException e){
          throw CheckedCollection.checkModCount(modCount,this.modCount,e);
        }
        CheckedCollection.checkModCount(modCount,this.modCount);
        this.modCount=modCount+1;
      }
    }
    @Override
    public void unstableSort(Comparator<? super E> sorter)
    {
      final int size;
      if((size=this.size)>1)
      {
        final int modCount=this.modCount;
        try
        {
          if(sorter==null)
          {
            RefSortUtil.uncheckedUnstableAscendingSort(this.arr,0,size);
          }
          else
          {
            RefSortUtil.uncheckedUnstableSort(this.arr,0,size,sorter);
          }
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
          throw CheckedCollection.checkModCount(modCount,this.modCount,new IllegalArgumentException("Comparison method violates its general contract!",e));
        }
        catch(RuntimeException e)
        {
          throw CheckedCollection.checkModCount(modCount,this.modCount,e);
        }
        CheckedCollection.checkModCount(modCount,this.modCount);
        this.modCount=modCount+1;
      }
    }
    @Override
    public void unstableAscendingSort()
    {
      final int size;
      if((size=this.size)>1){
        final int modCount=this.modCount;
        try
        {
          RefSortUtil.uncheckedUnstableAscendingSort(this.arr,0,size);
        }
        catch(ArrayIndexOutOfBoundsException e){
          throw CheckedCollection.checkModCount(modCount,this.modCount,new IllegalArgumentException("Comparison method violates its general contract!",e));
        }catch(RuntimeException e){
          throw CheckedCollection.checkModCount(modCount,this.modCount,e);
        }
        CheckedCollection.checkModCount(modCount,this.modCount);
        this.modCount=modCount+1;
      }
    }
    @Override
    public void unstableDescendingSort()
    {
      final int size;
      if((size=this.size)>1){
        final int modCount=this.modCount;
        try
        {
          RefSortUtil.uncheckedUnstableDescendingSort(this.arr,0,size);
        }
        catch(ArrayIndexOutOfBoundsException e){
          throw CheckedCollection.checkModCount(modCount,this.modCount,new IllegalArgumentException("Comparison method violates its general contract!",e));
        }catch(RuntimeException e){
          throw CheckedCollection.checkModCount(modCount,this.modCount,e);
        }
        CheckedCollection.checkModCount(modCount,this.modCount);
        this.modCount=modCount+1;
      }
    }
    @Override
    public OmniList.OfRef<E> subList(int fromIndex,int toIndex)
    {
      return new CheckedSubList<E>(this,fromIndex,CheckedCollection.checkSubListRange(fromIndex,toIndex,this.size));
    }
  }
  private
    static class CheckedSubList<E>
      implements OmniList.OfRef<E>,Cloneable
  {
    transient int modCount;
    transient final int rootOffset;
    transient int size;
    transient final CheckedList<E> root;
    transient final CheckedSubList<E> parent;
    private CheckedSubList(CheckedList<E> root,int rootOffset,int size)
    {
      super();
      this.root=root;
      this.parent=null;
      this.rootOffset=rootOffset;
      this.size=size;
      this.modCount=root.modCount;
    }
    private CheckedSubList(CheckedSubList<E> parent,int rootOffset,int size)
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
      //TODO implements equals method for CheckedSubList<E>
      return false;
    }
    @Override
    public Object clone()
    {
      final CheckedList<E> root;
      CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
      final Object[] copy;
      final int size;
      if((size=this.size)!=0)
      {
        ArrCopy.uncheckedCopy(root.arr,rootOffset,copy=new Object[size],0,size);
      }
      else
      {
        copy=OmniArray.OfRef.DEFAULT_ARR;
      }
      return new CheckedList<E>(size,copy);
    }
    @Override
    public String toString()
    {
      final int modCount=this.modCount;
      final var root=this.root;
      try
      {
        final int size;
        if((size=this.size)!=0)
        {
          final StringBuilder builder;
          final int rootOffset;
          OmniArray.OfRef.ascendingToString(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,builder=new StringBuilder("["));
          return builder.append(']').toString();
        }
        return "[]";
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override
    public int hashCode()
    {
      final int modCount=this.modCount;
      final var root=this.root;
      try
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          return OmniArray.OfRef.ascendingSeqHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
        }
        return 1;
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
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
      final CheckedList<E> root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0)
      {
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr.size-=size,curr=curr.parent){}
        root.size=OmniArray.OfRef.removeRangeAndPullDown(root.arr,this.rootOffset+size,root.size,size);
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
          return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred(val));
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
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred(val));
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
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          final int rootOffset;
          return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred(val));
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
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
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          final int rootOffset;
          return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred(val));
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
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
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          final int rootOffset;
          return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred(val));
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean contains(Object val)
    {
      final int size;
      if((size=this.size)!=0){
        if(val!=null){
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfRef.uncheckedcontainsNonNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,val);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          final int rootOffset;
          return OmniArray.OfRef.uncheckedcontainsNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
    //#ELSE
    //    if(val instanceof E)
    //    {
    //  #IF OfDouble,OfFloat
    //      final Object v;
    //      if((v=(Object)val)==v)
    //      {
    //        #MACRO ReturnUncheckedQueryBits($convertToBits$(v))
    //      }
    //      #MACRO ReturnUncheckedQueryNaN()
    //  #ELSE
    //      #MACRO ReturnUncheckedQuery(OmniPred.OfRef.getEqualsPred(val))
    //  #ENDIF
    //    }
    //#ENDIF
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
            return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred(val));
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
            return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred(val));
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
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred(val));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean contains(Boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred((boolean)(val)));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          final int rootOffset;
          return OmniArray.OfRef.uncheckedcontainsNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean contains(Byte val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred((byte)(val)));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          final int rootOffset;
          return OmniArray.OfRef.uncheckedcontainsNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean contains(Character val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred((char)(val)));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          final int rootOffset;
          return OmniArray.OfRef.uncheckedcontainsNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean contains(Short val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred((short)(val)));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          final int rootOffset;
          return OmniArray.OfRef.uncheckedcontainsNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean contains(Integer val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred((int)(val)));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          final int rootOffset;
          return OmniArray.OfRef.uncheckedcontainsNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean contains(Long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred((long)(val)));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          final int rootOffset;
          return OmniArray.OfRef.uncheckedcontainsNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean contains(Float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred((float)(val)));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          final int rootOffset;
          return OmniArray.OfRef.uncheckedcontainsNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean contains(Double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            final int rootOffset;
            return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred((double)(val)));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          final int rootOffset;
          return OmniArray.OfRef.uncheckedcontainsNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
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
        return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean removeVal(int val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
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
        return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
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
        return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
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
        return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean remove(Object val)
    {
      final int size;
      if((size=this.size)!=0){
        if(val!=null){
          return this.uncheckedremoveValNonNull(size,val);
        }
        return this.uncheckedremoveValNull(size);
    //#ELSE
    //    if(val instanceof E)
    //    {
    //  #IF OfDouble,OfFloat
    //      final Object v;
    //      if((v=(Object)val)==v)
    //      {
    //        #MACRO ReturnUncheckedQueryBits($convertToBits$(v))
    //      }
    //      #MACRO ReturnUncheckedQueryNaN()
    //  #ELSE
    //      #MACRO ReturnUncheckedQuery(OmniPred.OfRef.getEqualsPred(val))
    //  #ENDIF
    //    }
    //#ENDIF
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
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
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
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean removeVal(short val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean removeVal(Boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((boolean)(val)));
        }
        return this.uncheckedremoveValNull(size);
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean removeVal(Byte val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((byte)(val)));
        }
        return this.uncheckedremoveValNull(size);
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean removeVal(Character val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((char)(val)));
        }
        return this.uncheckedremoveValNull(size);
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean removeVal(Short val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((short)(val)));
        }
        return this.uncheckedremoveValNull(size);
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean removeVal(Integer val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((int)(val)));
        }
        return this.uncheckedremoveValNull(size);
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean removeVal(Long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((long)(val)));
        }
        return this.uncheckedremoveValNull(size);
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean removeVal(Float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((float)(val)));
        }
        return this.uncheckedremoveValNull(size);
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public boolean removeVal(Double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((double)(val)));
        }
        return this.uncheckedremoveValNull(size);
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
          return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
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
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
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
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
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
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
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
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int indexOf(Object val)
    {
      final int size;
      if((size=this.size)!=0){
        if(val!=null){
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfRef.uncheckedindexOfNonNull(root.arr,this.rootOffset,size,val);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          return OmniArray.OfRef.uncheckedindexOfNull(root.arr,this.rootOffset,size);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
    //#ELSE
    //    if(val instanceof E)
    //    {
    //  #IF OfDouble,OfFloat
    //      final Object v;
    //      if((v=(Object)val)==v)
    //      {
    //        #MACRO ReturnUncheckedQueryBits($convertToBits$(v))
    //      }
    //      #MACRO ReturnUncheckedQueryNaN()
    //  #ELSE
    //      #MACRO ReturnUncheckedQuery(OmniPred.OfRef.getEqualsPred(val))
    //  #ENDIF
    //    }
    //#ENDIF
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
            return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
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
            return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
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
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int indexOf(Boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((boolean)(val)));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          return OmniArray.OfRef.uncheckedindexOfNull(root.arr,this.rootOffset,size);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int indexOf(Byte val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((byte)(val)));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          return OmniArray.OfRef.uncheckedindexOfNull(root.arr,this.rootOffset,size);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int indexOf(Character val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((char)(val)));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          return OmniArray.OfRef.uncheckedindexOfNull(root.arr,this.rootOffset,size);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int indexOf(Short val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((short)(val)));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          return OmniArray.OfRef.uncheckedindexOfNull(root.arr,this.rootOffset,size);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int indexOf(Integer val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((int)(val)));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          return OmniArray.OfRef.uncheckedindexOfNull(root.arr,this.rootOffset,size);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int indexOf(Long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((long)(val)));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          return OmniArray.OfRef.uncheckedindexOfNull(root.arr,this.rootOffset,size);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int indexOf(Float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((float)(val)));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          return OmniArray.OfRef.uncheckedindexOfNull(root.arr,this.rootOffset,size);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int indexOf(Double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((double)(val)));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          return OmniArray.OfRef.uncheckedindexOfNull(root.arr,this.rootOffset,size);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
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
          return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
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
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
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
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
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
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
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
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int lastIndexOf(Object val)
    {
      final int size;
      if((size=this.size)!=0){
        if(val!=null){
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfRef.uncheckedlastIndexOfNonNull(root.arr,this.rootOffset,size,val);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          return OmniArray.OfRef.uncheckedlastIndexOfNull(root.arr,this.rootOffset,size);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
    //#ELSE
    //    if(val instanceof E)
    //    {
    //  #IF OfDouble,OfFloat
    //      final Object v;
    //      if((v=(Object)val)==v)
    //      {
    //        #MACRO ReturnUncheckedQueryBits($convertToBits$(v))
    //      }
    //      #MACRO ReturnUncheckedQueryNaN()
    //  #ELSE
    //      #MACRO ReturnUncheckedQuery(OmniPred.OfRef.getEqualsPred(val))
    //  #ENDIF
    //    }
    //#ENDIF
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
            return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
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
            return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
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
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int lastIndexOf(Boolean val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((boolean)(val)));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          return OmniArray.OfRef.uncheckedlastIndexOfNull(root.arr,this.rootOffset,size);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int lastIndexOf(Byte val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((byte)(val)));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          return OmniArray.OfRef.uncheckedlastIndexOfNull(root.arr,this.rootOffset,size);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int lastIndexOf(Character val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((char)(val)));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          return OmniArray.OfRef.uncheckedlastIndexOfNull(root.arr,this.rootOffset,size);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int lastIndexOf(Short val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((short)(val)));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          return OmniArray.OfRef.uncheckedlastIndexOfNull(root.arr,this.rootOffset,size);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int lastIndexOf(Integer val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((int)(val)));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          return OmniArray.OfRef.uncheckedlastIndexOfNull(root.arr,this.rootOffset,size);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int lastIndexOf(Long val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((long)(val)));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          return OmniArray.OfRef.uncheckedlastIndexOfNull(root.arr,this.rootOffset,size);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int lastIndexOf(Float val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((float)(val)));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          return OmniArray.OfRef.uncheckedlastIndexOfNull(root.arr,this.rootOffset,size);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    @Override
    public int lastIndexOf(Double val)
    {
      final int size;
      if((size=this.size)!=0)
      {
        if(val!=null)
        {
          final int modCount=this.modCount;
          final var root=this.root;
          try{
            return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((double)(val)));
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
        }
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          return OmniArray.OfRef.uncheckedlastIndexOfNull(root.arr,this.rootOffset,size);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return -1;
    }
    private boolean uncheckedremoveValNonNull(int size,Object nonNull)
    {
      int modCount=this.modCount;
      final CheckedList<E> root;
      final var arr=(root=this.root).arr;
      try
      {
        for(int index=this.rootOffset,bound=index+(--size);;++index)
        {
          if(nonNull.equals(arr[index]))
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
            root.modCount=++modCount;
            this.modCount=modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,--curr.size,curr=curr.parent){}
            OmniArray.OfRef.removeIndexAndPullDown(arr,index,--root.size);
            this.size=size;
            return true;
          }
          if(index==bound)
          {
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
          }
        }
      }
      catch(final ConcurrentModificationException e)
      {
        throw e;
      }
      catch(final RuntimeException e)
      {
        throw CheckedCollection.checkModCount(modCount,root.modCount,e);
      }
    }
    private boolean uncheckedremoveValNull(int size
    )
    {
      int modCount;
      final CheckedList<E> root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      final var arr=root.arr;
      for(int index=this.rootOffset,bound=index+(--size);;++index)
      {
        if(
        arr[index]==null
        )
        {
          root.modCount=++modCount;
          this.modCount=modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,--curr.size,curr=curr.parent){}
          OmniArray.OfRef.removeIndexAndPullDown(arr,index,--root.size);
          this.size=size;
          return true;
        }
        if(index==bound)
        {
          return false;
        }
      }
    }
    private boolean uncheckedremoveVal (int size
    ,Predicate<Object> pred
    )
    {
      int modCount;
      final CheckedList<E> root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      final var arr=root.arr;
      for(int index=this.rootOffset,bound=index+(--size);;++index)
      {
        if(
        pred.test(arr[index])
        )
        {
          root.modCount=++modCount;
          this.modCount=modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,--curr.size,curr=curr.parent){}
          OmniArray.OfRef.removeIndexAndPullDown(arr,index,--root.size);
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
    public void forEach(Consumer<? super E> action)
    {
      int modCount=this.modCount;
      final var root=this.root;
      try
      {
        final int size;
        if((size=this.size)!=0)
        {
          final int rootOffset;
          OmniArray.OfRef.ascendingForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,action);
        }
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    private static class Itr<E>
      implements OmniIterator.OfRef<E>
    {
      transient final CheckedSubList<E> parent;
      transient int cursor;
      transient int lastRet;
      transient int modCount;
      private Itr(CheckedSubList<E> parent)
      {
        this.parent=parent;
        this.cursor=parent.rootOffset;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      private Itr(CheckedSubList<E> parent,int cursor)
      {
        this.parent=parent;
        this.cursor=cursor;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      @Override
      public boolean hasNext()
      {
        final CheckedSubList<E> parent;
        return this.cursor<(parent=this.parent).rootOffset+parent.size;
      }
      @SuppressWarnings("unchecked")
      @Override
      public E next()
      {
        final CheckedList<E> root;
        final CheckedSubList<E> parent;
        CheckedCollection.checkModCount(modCount,(root=(parent=this.parent).root).modCount);
        final int cursor;
        if((cursor=this.cursor)<(parent.rootOffset+parent.size))
        {
          this.lastRet=cursor;
          this.cursor=cursor+1;
            return (E)root.arr[cursor];
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
          final CheckedList<E> root;
          CheckedSubList<E> parent;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
          root.modCount=++modCount;
          do{
            parent.modCount=modCount;
            --parent.size;
          }while((parent=parent.parent)!=null);
          this.modCount=modCount;
          OmniArray.OfRef.removeIndexAndPullDown(root.arr,lastRet,--root.size);
          this.cursor=lastRet;
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override
      public void forEachRemaining(Consumer<? super E> action)
      {
        int cursor;
        final int bound;
        final CheckedSubList<E> parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size))
        {
          final int modCount=this.modCount;
          final var root=parent.root;
          try
          {
            OmniArray.OfRef.ascendingForEach(root.arr,cursor,cursor=bound-1,action);
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
    public OmniIterator.OfRef<E> iterator()
    {
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new Itr<E>(this);
    }
    private static class ListItr<E> extends Itr<E> implements OmniListIterator.OfRef<E>
    {
      private ListItr(CheckedSubList<E> parent)
      {
        super(parent);
      }
      private ListItr(CheckedSubList<E> parent,int cursor)
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
      @SuppressWarnings("unchecked")
      @Override
      public E previous()
      {
        final CheckedList<E> root;
        int cursor;
        final CheckedSubList<E> parent;
        CheckedCollection.checkModCount(modCount,(root=(parent=this.parent).root).modCount);
        if((cursor=this.cursor)>parent.rootOffset)
        {
          this.lastRet=--cursor;
          this.cursor=cursor;
          return (E)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override
      public void set(E val)
      {
        final int lastRet;
        if((lastRet=this.lastRet)!=-1)
        {
          final CheckedList<E> root;
          CheckedCollection.checkModCount(modCount,(root=this.parent.root).modCount);
          root.arr[lastRet]=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override
      public void add(E val)
      {
        int modCount;
        final CheckedList<E> root;
        CheckedSubList<E> parent;
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
          ((UncheckedList<E>)root).uncheckedInsert(this.cursor++,rootSize,val);
        }
        else
        {
          ((RefArrSeq<E>)root).uncheckedInit(val);
          ++this.cursor;
        }
      }
    }
    @Override
    public OmniListIterator.OfRef<E> listIterator()
    {
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new ListItr<E>(this);
    }
    @Override
    public OmniListIterator.OfRef<E> listIterator(int index)
    {
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,this.size);
      return new ListItr<E>(this,index+this.rootOffset);
    }
    @Override
    public boolean add(E val)
    {
      final CheckedList<E> root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,++curr.size,curr=curr.parent){}
      if((modCount=root.size)!=0){
        ((UncheckedList<E>)root).uncheckedInsert(this.rootOffset+(this.size++),modCount,val);
      }else{
        ((RefArrSeq<E>)root).uncheckedInit(val);
        ++this.size;
      }
      return true;
    }
    @Override
    public void add(int index,E val)
    {
      final CheckedList<E> root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      final int size;
      CheckedCollection.checkWriteHi(index,size=this.size);
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,++curr.size,curr=curr.parent){}
      this.size=size+1;
      if((modCount=root.size)!=0){
        ((UncheckedList<E>)root).uncheckedInsert(this.rootOffset+index,modCount,val);
      }else{
        ((RefArrSeq<E>)root).uncheckedInit(val);
      }
    }
    @Override
    public <T> T[] toArray(T[] arr)
    {
      final CheckedList<E> root;
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
      final CheckedList<E> root;
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
    public Object[] toArray()
    {
      final CheckedList<E> root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0)
      {
        final Object[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new Object[size],0,size);
        return dst;
      }
      return OmniArray.OfRef.DEFAULT_ARR;
    }
    @SuppressWarnings("unchecked")
    @Override
    public E get(int index)
    {
      final CheckedList<E> root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      return (E)root.arr[index+this.rootOffset];
    }
    @Override
    public void put(int index,E val)
    {
      final CheckedList<E> root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      root.arr[index+this.rootOffset]=val;
    }
    @SuppressWarnings("unchecked")
    @Override
    public E set(int index,E val)
    {
      final CheckedList<E> root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      final Object[] arr;
      final var ret=(E)(arr=root.arr)[index+=this.rootOffset];
      arr[index]=val;
      return ret;
    }
    @SuppressWarnings("unchecked")
    @Override
    public E remove(int index)
    {
      int modCount;
      final CheckedList<E> root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      final Object[] arr;
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,--curr.size,curr=curr.parent){}
      final var ret=(E)(arr=root.arr)[index+=this.rootOffset];
      OmniArray.OfRef.removeIndexAndPullDown(arr,index,--root.size);
      this.size=size-1;
      return ret;
    }
    @Override
    public boolean removeIf(Predicate<? super E> filter)
    {
      int modCount=this.modCount;
      final var root=this.root;
      final int size;
      if((size=this.size)!=0)
      {
        try
        {
          final Object[] arr;
          final int numRemoved;
          int rootOffset;
          if((numRemoved=uncheckedRemoveIfImpl(arr=root.arr,rootOffset=this.rootOffset,rootOffset+=size,filter,new CheckedCollection.AbstractModCountChecker(modCount){
            @Override protected int getActualModCount(){
              return root.modCount;
            }
          }))!=0){
            root.modCount=++modCount;
            this.modCount=modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,curr.size-=numRemoved,curr=curr.parent){}
            root.size=OmniArray.OfRef.removeRangeAndPullDown(arr,rootOffset,root.size,numRemoved);
            this.size=size-numRemoved;
            return true;
          }
        }
        catch(ConcurrentModificationException e)
        {
          throw e;
        }
        catch(RuntimeException e)
        {
          throw CheckedCollection.checkModCount(modCount,root.modCount,e);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override
    public void replaceAll(UnaryOperator<E> operator)
    {
      int modCount=this.modCount;
      final var root=this.root;
      try
      {
        final int size;
        if((size=this.size)==0)
        {
          return;
        }
        final int rootOffset;
        OmniArray.OfRef.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);  
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
    }
    @Override
    public void sort(Comparator<? super E> sorter)
    {
      int modCount=this.modCount;
      final var root=this.root;
      try
      {
        final int size;
        if((size=this.size)<2)
        {
          return;
        }
        try
        {
          final int rootOffset;
          if(sorter==null)
          {
            RefSortUtil.uncheckedStableAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
          }
          else
          {
            {
              RefSortUtil.uncheckedStableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter);
            }
          }
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
          throw new IllegalArgumentException("Comparison method violates its general contract!",e);
        }
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
    }
    @Override
    public void stableAscendingSort()
    {
      int modCount=this.modCount;
      final var root=this.root;
      try{
        final int size;
        if((size=this.size)<2){
          return;
        }
        try{
          final int rootOffset;
          RefSortUtil.uncheckedStableAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }catch(ArrayIndexOutOfBoundsException e){
          throw CheckedCollection.checkModCount(modCount,root.modCount,new IllegalArgumentException("Comparison method violates its general contract!",e));
        }
      }
      finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}  
    }
    @Override
    public void stableDescendingSort()
    {
      int modCount=this.modCount;
      final var root=this.root;
      try{
        final int size;
        if((size=this.size)<2){
          return;
        }
        try{
          final int rootOffset;
          RefSortUtil.uncheckedStableDescendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }catch(ArrayIndexOutOfBoundsException e){
          throw CheckedCollection.checkModCount(modCount,root.modCount,new IllegalArgumentException("Comparison method violates its general contract!",e));
        }
      }
      finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}  
    }
    @Override
    public void unstableSort(Comparator<? super E> sorter)
    {
      int modCount=this.modCount;
      final var root=this.root;
      try
      {
        final int size;
        if((size=this.size)<2)
        {
          return;
        }
        try
        {
          final int rootOffset;
          if(sorter==null)
          {
            RefSortUtil.uncheckedUnstableAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
          }
          else
          {
            {
              RefSortUtil.uncheckedUnstableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter);
            }
          }
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
          throw new IllegalArgumentException("Comparison method violates its general contract!",e);
        }
      }
      finally
      {
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
    }
    @Override
    public void unstableAscendingSort()
    {
      int modCount=this.modCount;
      final var root=this.root;
      try{
        final int size;
        if((size=this.size)<2){
          return;
        }
        try{
          final int rootOffset;
          RefSortUtil.uncheckedUnstableAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }catch(ArrayIndexOutOfBoundsException e){
          throw CheckedCollection.checkModCount(modCount,root.modCount,new IllegalArgumentException("Comparison method violates its general contract!",e));
        }
      }
      finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}  
    }
    @Override
    public void unstableDescendingSort()
    {
      int modCount=this.modCount;
      final var root=this.root;
      try{
        final int size;
        if((size=this.size)<2){
          return;
        }
        try{
          final int rootOffset;
          RefSortUtil.uncheckedUnstableDescendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }catch(ArrayIndexOutOfBoundsException e){
          throw CheckedCollection.checkModCount(modCount,root.modCount,new IllegalArgumentException("Comparison method violates its general contract!",e));
        }
      }
      finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}  
    }
    @Override
    public OmniList.OfRef<E> subList(int fromIndex,int toIndex)
    {
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new CheckedSubList<E>(this,this.rootOffset+fromIndex,CheckedCollection.checkSubListRange(fromIndex,toIndex,this.size));
    }
  }
}
