package omni.impl.seq;
import java.util.List;
import java.util.Collection;
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
import omni.impl.AbstractOmniCollection;
import java.util.ListIterator;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;
import java.util.ConcurrentModificationException;
import java.util.Objects;
import omni.util.OmniPred;
import java.io.Externalizable;
import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectInput;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.RandomAccess;
public abstract class RefArrSeq<E> extends 
AbstractOmniCollection<E>
 implements OmniCollection.OfRef<E>,Externalizable,RandomAccess{
  //TODO refactor the template and/or optimize code generation to make sure that the code generation doesn't take forever
  private static final long serialVersionUID=1L;
  transient Object[] arr; 
  private RefArrSeq(){
    super();
    this.arr=OmniArray.OfRef.DEFAULT_ARR;
  }
  private RefArrSeq(Collection<? extends E> that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private RefArrSeq(OmniCollection.OfRef<? extends E> that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private RefArrSeq(int initialCapacity){
    super();
    switch(initialCapacity){ 
    default:
      this.arr=new Object[initialCapacity];
      return;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfRef.DEFAULT_ARR;
    case 0:
    }
  }
  private RefArrSeq(int size,Object[] arr){
    super(size);
    this.arr=arr;
  }
  @Override public void writeExternal(ObjectOutput out) throws IOException{
    int size;
    out.writeInt(size=this.size);
    if(size!=0)
    {
      OmniArray.OfRef.writeArray(arr,0,size-1,out);
    }
  }
  @Override public void readExternal(ObjectInput in) throws IOException
    ,ClassNotFoundException
  {
    int size;
    this.size=size=in.readInt();
    if(size!=0){
      Object[] arr;
      OmniArray.OfRef.readArray(arr=new Object[size],0,size-1,in);
      this.arr=arr;
    }else{
      this.arr=OmniArray.OfRef.DEFAULT_ARR;
    }
  }
  @SuppressWarnings("unchecked")
  public E pop(){
    final Object[] arr;
    final int size;
    final var ret=(E)(arr=this.arr)[size=--this.size];
    arr[size]=null;
    return ret;
  }
  @SuppressWarnings("unchecked")
  static <E> long markSurvivors(Object[] arr,int srcOffset,int srcBound,Predicate<? super E> filter){
    for(long word=0L,marker=1L;;marker<<=1){
      if(!filter.test((E)arr[srcOffset])){
        word|=marker;
      }
      if(++srcOffset==srcBound){
        return word;
      }
    }
  }
  @SuppressWarnings("unchecked")
  static <E> int markSurvivors(Object[] arr,int srcOffset,int srcBound,Predicate<? super E> filter,long[] survivorSet){
    for(int numSurvivors=0,wordOffset=0;;){
      long word=0L,marker=1L;
      do{
        if(!filter.test((E)arr[srcOffset])){
          word|=marker;
          ++numSurvivors;
        }
        if(++srcOffset==srcBound){
          survivorSet[wordOffset]=word;
          return numSurvivors;
        }
      }
      while((marker<<=1)!=0L);
      survivorSet[wordOffset++]=word;
    }
  }
  static void pullSurvivorsDown(Object[] arr,int srcOffset,int dstOffset,long word){
    int numTail0s=Long.numberOfTrailingZeros(word);
    do{
      ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset+=numTail0s,numTail0s=Long.numberOfTrailingZeros(~(word>>>=numTail0s)));
      srcOffset+=numTail0s;
      dstOffset+=numTail0s;
    }while((numTail0s=Long.numberOfTrailingZeros(word>>>=numTail0s))!=64);
  }
  static void pullSurvivorsDown(Object[] arr,int srcOffset,int dstOffset,int dstBound,long[] survivorSet){
    for(int wordOffset=0;;){
      long word=survivorSet[wordOffset];
      for(int s=srcOffset;;){
        int numTail0s;
        if((numTail0s=Long.numberOfTrailingZeros(word))==64){
          break;
        }
        ArrCopy.uncheckedSelfCopy(arr,dstOffset,s+=numTail0s,numTail0s=Long.numberOfTrailingZeros(~(word>>>=numTail0s)));
        if((dstOffset+=numTail0s)>=dstBound){
          return;
        }else if(numTail0s==64){
          break;
        }
        s+=numTail0s;
        word>>>=numTail0s;
      }
      ++wordOffset;
      srcOffset+=64;
    }
  }
  @Override public void clear(){
    int size;
    if((size=this.size)!=0){
      this.size=0;
      OmniArray.OfRef.nullifyRange(this.arr,size-1,0);
    }
  }
  @Override public String toString(){
    int size;
    if((size=this.size)!=0){
      final StringBuilder builder=new StringBuilder("[");
      uncheckedToString(size,builder);
      return builder.append(']').toString();
    }
    return "[]";
  }
  abstract void uncheckedToString(int size,StringBuilder builder);
  @Override public boolean contains(boolean val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(int val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(long val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(float val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(double val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(Object val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val!=null){
            return OmniArray.OfRef.uncheckedcontainsNonNull(this.arr,0,size-1,val);
          }
          return OmniArray.OfRef.uncheckedcontainsNull(this.arr,0,size-1);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(byte val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(char val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(short val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(Boolean val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val!=null){
            return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred((boolean)(val)));
          }
          return OmniArray.OfRef.uncheckedcontainsNull(this.arr,0,size-1);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(Byte val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val!=null){
            return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred((byte)(val)));
          }
          return OmniArray.OfRef.uncheckedcontainsNull(this.arr,0,size-1);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(Character val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val!=null){
            return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred((char)(val)));
          }
          return OmniArray.OfRef.uncheckedcontainsNull(this.arr,0,size-1);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(Short val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val!=null){
            return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred((short)(val)));
          }
          return OmniArray.OfRef.uncheckedcontainsNull(this.arr,0,size-1);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(Integer val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val!=null){
            return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred((int)(val)));
          }
          return OmniArray.OfRef.uncheckedcontainsNull(this.arr,0,size-1);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(Long val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val!=null){
            return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred((long)(val)));
          }
          return OmniArray.OfRef.uncheckedcontainsNull(this.arr,0,size-1);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(Float val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val!=null){
            return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred((float)(val)));
          }
          return OmniArray.OfRef.uncheckedcontainsNull(this.arr,0,size-1);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(Double val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val!=null){
            return OmniArray.OfRef.uncheckedcontains(this.arr,0,size-1,OmniPred.OfRef.getEqualsPred((double)(val)));
          }
          return OmniArray.OfRef.uncheckedcontainsNull(this.arr,0,size-1);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(boolean val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(int val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(long val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(float val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(double val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean remove(Object val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val!=null){
            return this.uncheckedremoveValNonNull(size,val);
          }
          return this.uncheckedremoveValNull(size);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(byte val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(char val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(short val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(Boolean val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val!=null){
            return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((boolean)(val)));
          }
          return this.uncheckedremoveValNull(size);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(Byte val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val!=null){
            return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((byte)(val)));
          }
          return this.uncheckedremoveValNull(size);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(Character val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val!=null){
            return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((char)(val)));
          }
          return this.uncheckedremoveValNull(size);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(Short val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val!=null){
            return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((short)(val)));
          }
          return this.uncheckedremoveValNull(size);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(Integer val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val!=null){
            return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((int)(val)));
          }
          return this.uncheckedremoveValNull(size);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(Long val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val!=null){
            return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((long)(val)));
          }
          return this.uncheckedremoveValNull(size);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(Float val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val!=null){
            return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((float)(val)));
          }
          return this.uncheckedremoveValNull(size);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(Double val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val!=null){
            return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((double)(val)));
          }
          return this.uncheckedremoveValNull(size);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  abstract boolean uncheckedremoveValNonNull(int size,Object nonNull);
  abstract boolean uncheckedremoveValNull(int size);
  abstract boolean uncheckedremoveVal(int size,Predicate<Object> pred);
  abstract void uncheckedForEach(int size,Consumer<? super E> action);
  @Override public void forEach(Consumer<? super E> action){
    final int size;
    if((size=this.size)!=0){
      uncheckedForEach(size,action);
    }
  }
  public void push(E val){
    final int size;
    if((size=this.size)!=0){
      uncheckedAppend(size,val);
    }else{
      uncheckedInit(val);
    }
  }
  @Override public boolean add(E val){
    push(val);
    return true;
  }
  abstract void uncheckedCopyInto(Object[] dst,int length);
  @Override public <T> T[] toArray(T[] arr){
    final int size;
    if((size=this.size)!=0){
      uncheckedCopyInto(arr=OmniArray.uncheckedArrResize(size,arr),size);
    }else if(arr.length!=0){
      arr[0]=null;
    }
    return arr;
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    T[] dst=arrConstructor.apply(size=this.size);
    if(size!=0){
      uncheckedCopyInto(dst,size);
    }
    return dst;
  }
  @Override public Object[] toArray(){
    final int size;
    if((size=this.size)!=0){
      final Object[] dst;
      uncheckedCopyInto(dst=new Object[size],size);
      return dst;
    }
    return OmniArray.OfRef.DEFAULT_ARR;
  }
  boolean uncheckedRemoveIf(int size,Predicate<? super E> filter){
    final int numRemoved;
    final Object[] arr;
    if((numRemoved=uncheckedRemoveIfImpl(arr=this.arr,0,size,filter))!=0){
      OmniArray.OfRef.nullifyRange(arr,size-1,size-=numRemoved);
      this.size=size;
      return true;
    }
    return false;
  }
  @Override public boolean removeIf(Predicate<? super E> filter){
    final int size;
    return (size=this.size)!=0 && uncheckedRemoveIf(size,filter);
  }
  private
  void uncheckedAppend(int size,E val){
    Object[] arr;
    if((arr=this.arr).length==size){
      ArrCopy.uncheckedCopy(arr,0,arr=new Object[OmniArray.growBy50Pct(size)],0,size);
      this.arr=arr;
    }
    arr[size]=val;
    this.size=size+1;
  }
  void uncheckedInit(E val){
    Object[] arr;
    if((arr=this.arr)==null){
      this.arr=new Object[]{val};
    }else{
      if(arr==OmniArray.OfRef.DEFAULT_ARR){
        this.arr=arr=new Object[OmniArray.DEFAULT_ARR_SEQ_CAP];
      }
      arr[0]=val;
    }
    this.size=1;
  }
  @SuppressWarnings("unchecked")
  private static <E> int pullSurvivorsDown(Object[] arr,int srcOffset,int srcBound,int dstOffset,Predicate<? super E> filter){
    while(++srcOffset!=srcBound){
      final Object v;
      if(!filter.test((E)(v=arr[srcOffset])))
      {
        arr[dstOffset++]=v;
      }
    }
    return srcBound-dstOffset;
  }
  @SuppressWarnings("unchecked")
  private static <E> int uncheckedRemoveIfImpl(Object[] arr,int srcOffset,int srcBound,Predicate<? super E> filter){
    do{
      if(filter.test((E)arr[srcOffset])){
        return pullSurvivorsDown(arr,srcOffset,srcBound,srcOffset,filter);
      }
    }while(++srcOffset!=srcBound);
    return 0;
  }
  @SuppressWarnings("unchecked")
  private static <E> int uncheckedRemoveIfImpl(Object[] arr,int srcOffset,int srcBound,Predicate<? super E> filter,CheckedCollection.AbstractModCountChecker modCountChecker){
    do{
      if(filter.test((E)arr[srcOffset])){
        int dstOffset=srcOffset;
        outer:for(;;){
          if(++srcOffset==srcBound){
            modCountChecker.checkModCount();
            break outer;
          }
          Object before;
          if(!filter.test((E)(before=arr[srcOffset]))){
            for(int i=srcBound-1;;--i){
              if(i==srcOffset){
                modCountChecker.checkModCount();
                arr[dstOffset++]=before;
                break outer;
              }
              Object after;
              if(!filter.test((E)(after=arr[i]))){
                int n;
                if((n=i-(++srcOffset))!=0){
                  if(n>64){
                    long[] survivorSet;
                    int numSurvivors=markSurvivors(arr,srcOffset,i,filter,survivorSet=new long[(n-1>>6)+1]);
                    modCountChecker.checkModCount();
                    if(numSurvivors!=0){
                      if(numSurvivors==n){
                        System.arraycopy(arr,srcOffset-1,arr,dstOffset,numSurvivors+=2);
                        dstOffset+=numSurvivors;
                      }else{
                        arr[dstOffset]=before;
                        pullSurvivorsDown(arr,srcOffset,++dstOffset,dstOffset+=numSurvivors,survivorSet);
                        arr[dstOffset++]=after;
                      }
                      break outer;
                    }
                  }else{
                    long survivorWord=markSurvivors(arr,srcOffset,i,filter);
                    modCountChecker.checkModCount();
                    int numSurvivors;
                    if((numSurvivors=Long.bitCount(survivorWord))!=0){
                      if(numSurvivors==n){
                        ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset-1,numSurvivors+=2);
                        dstOffset+=numSurvivors;
                      }else{
                        arr[dstOffset]=before;
                        pullSurvivorsDown(arr,srcOffset,++dstOffset,survivorWord);
                        arr[dstOffset+=numSurvivors]=after;
                        ++dstOffset;
                      }
                      break outer;
                    }
                  }
                }else{
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
      implements OmniStack.OfRef<E>,Cloneable,RandomAccess
  {
    private static final long serialVersionUID=1L;
    public UncheckedStack(Collection<? extends E> that){
      super(that);
    }
    public UncheckedStack(OmniCollection.OfRef<? extends E> that){
      super(that);
    }
    public UncheckedStack(){
      super();
    }
    public UncheckedStack(int initialCapacity){
      super(initialCapacity);
    }
    UncheckedStack(int size,Object[] arr){
      super(size,arr);
    }
    @Override public Object clone(){
      final Object[] copy;
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(this.arr,0,copy=new Object[size],0,size);
      }else{
        copy=OmniArray.OfRef.DEFAULT_ARR;
      }
      return new UncheckedStack<E>(size,copy);
    }
    @Override void uncheckedToString(int size,StringBuilder builder){
      OmniArray.OfRef.descendingToString(this.arr,0,size-1,builder);
    }
    @Override public int search(boolean val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedsearch(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedsearch(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(long val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedsearch(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(float val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedsearch(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(double val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedsearch(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Object val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedsearchNonNull(this.arr,size,val);
            }
            return OmniArray.OfRef.uncheckedsearchNull(this.arr,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(byte val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedsearch(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(char val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedsearch(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(short val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedsearch(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Boolean val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedsearch(this.arr,size,OmniPred.OfRef.getEqualsPred((boolean)(val)));
            }
            return OmniArray.OfRef.uncheckedsearchNull(this.arr,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Byte val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedsearch(this.arr,size,OmniPred.OfRef.getEqualsPred((byte)(val)));
            }
            return OmniArray.OfRef.uncheckedsearchNull(this.arr,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Character val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedsearch(this.arr,size,OmniPred.OfRef.getEqualsPred((char)(val)));
            }
            return OmniArray.OfRef.uncheckedsearchNull(this.arr,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Short val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedsearch(this.arr,size,OmniPred.OfRef.getEqualsPred((short)(val)));
            }
            return OmniArray.OfRef.uncheckedsearchNull(this.arr,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Integer val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedsearch(this.arr,size,OmniPred.OfRef.getEqualsPred((int)(val)));
            }
            return OmniArray.OfRef.uncheckedsearchNull(this.arr,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Long val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedsearch(this.arr,size,OmniPred.OfRef.getEqualsPred((long)(val)));
            }
            return OmniArray.OfRef.uncheckedsearchNull(this.arr,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Float val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedsearch(this.arr,size,OmniPred.OfRef.getEqualsPred((float)(val)));
            }
            return OmniArray.OfRef.uncheckedsearchNull(this.arr,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(Double val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedsearch(this.arr,size,OmniPred.OfRef.getEqualsPred((double)(val)));
            }
            return OmniArray.OfRef.uncheckedsearchNull(this.arr,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override boolean uncheckedremoveValNonNull(int size,Object nonNull){
      {
        final var arr=this.arr;
        for(int index=--size;;--index){
          if(nonNull.equals(arr[index])){
            OmniArray.OfRef.removeIndexAndPullDown(arr,index,size);
            this.size=size;
            return true;
          }else if(index==0){
            return false;
          }
        }
      }
    }
    @Override boolean uncheckedremoveValNull(int size
    ){
      final var arr=this.arr;
      for(int index=--size;;--index){
        if(
        arr[index]==null
        )
        {
          OmniArray.OfRef.removeIndexAndPullDown(arr,index,size);
          this.size=size;
          return true;
        }else if(index==0){
          return false;
        }
      }
    }
    @Override boolean uncheckedremoveVal (int size
    ,Predicate<Object> pred
    ){
      final var arr=this.arr;
      for(int index=--size;;--index){
        if(
        pred.test(arr[index])
        )
        {
          OmniArray.OfRef.removeIndexAndPullDown(arr,index,size);
          this.size=size;
          return true;
        }else if(index==0){
          return false;
        }
      }
    }
    @Override void uncheckedForEach(int size,Consumer<? super E> action){
      {
        OmniArray.OfRef.descendingForEach(this.arr,0,size-1,action);
      }
    }
    private static class Itr<E>
      implements OmniIterator.OfRef<E>
    {
      transient final UncheckedStack<E> parent;
      transient int cursor;
      private Itr(Itr<E> itr){
        this.parent=itr.parent;
        this.cursor=itr.cursor;
      }
      private Itr(UncheckedStack<E> parent){
        this.parent=parent;
        this.cursor=parent.size;
      }
      @Override public Object clone(){
        return new Itr<E>(this);
      }
      @Override public boolean hasNext(){
        return this.cursor>0;
      }
      @SuppressWarnings("unchecked")
      @Override public E next(){
        return (E)parent.arr[--cursor];
      }
      @Override public void remove(){
        final UncheckedStack<E> root;
        OmniArray.OfRef.removeIndexAndPullDown((root=this.parent).arr,this.cursor,--root.size);
      }
      @Override public void forEachRemaining(Consumer<? super E> action){
        final int cursor;
        if((cursor=this.cursor)>0){
          OmniArray.OfRef.descendingForEach(parent.arr,0,cursor-1,action);
          this.cursor=0;
        }
      }
    }
    @Override public OmniIterator.OfRef<E> iterator(){
      return new Itr<E>(this);
    }
    @Override void uncheckedCopyInto(Object[] dst,int length){
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @SuppressWarnings("unchecked")
    @Override public E poll(){
      int size;
      if((size=this.size)!=0){
        final var ret=(E)(arr[--size]);
        arr[size]=null;
        this.size=size;
        return ret;
      }
      return null;
    }
    @SuppressWarnings("unchecked")
    @Override public E peek(){
      final int size;
      if((size=this.size)!=0){
        return (E)(arr[size-1]);
      }
      return null;
    }
  }
  public
    static class UncheckedList<E>
      extends RefArrSeq<E>
      implements OmniList.OfRef<E>,Cloneable,RandomAccess
  {
    private static final long serialVersionUID=1L;
    public UncheckedList(Collection<? extends E> that){
      super(that);
    }
    public UncheckedList(OmniCollection.OfRef<? extends E> that){
      super(that);
    }
    public UncheckedList(){
      super();
    }
    public UncheckedList(int initialCapacity){
      super(initialCapacity);
    }
    UncheckedList(int size,Object[] arr){
      super(size,arr);
    }
    boolean isEqualTo(ListIterator<?> itr,int thisOffset,int thisBound){
      if(itr.hasNext()){
        for(final var arr=this.arr;Objects.equals(arr[thisOffset],itr.next());){
          if(!itr.hasNext()){
            return thisOffset+1==thisBound;
          }
          if(++thisOffset>=thisBound){
            break;
          }
        }
      }
      return false;
    }
    private boolean isEqualToHelper(int thisOffset,int thisBound,Object[] thatArr,int thatOffset){
      for(final var thisArr=this.arr;Objects.equals(thisArr[thisOffset],thatArr[thatOffset]);++thatOffset){
        if(++thisOffset==thisBound){
          return true;
        }  
      }
      return false;
    }
    boolean isEqualTo(int rootOffset,int size,OmniList.OfBoolean list){
      if(list instanceof AbstractBooleanArrSeq){
        final AbstractBooleanArrSeq abstractBooleanArrSeq;
        if(size!=(abstractBooleanArrSeq=(AbstractBooleanArrSeq)list).size){
          return false;
        }
        if(abstractBooleanArrSeq instanceof PackedBooleanArrSeq.UncheckedList){
          return SequenceEqualityUtil.isEqualTo(rootOffset,rootOffset+size,this.arr,0,((PackedBooleanArrSeq.UncheckedList)abstractBooleanArrSeq).words);
        }else{
          return SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,((BooleanArrSeq.UncheckedList)abstractBooleanArrSeq).arr,0);
        }
      }else if(list instanceof PackedBooleanArrSeq.UncheckedSubList){
        final PackedBooleanArrSeq.UncheckedSubList subList;
        return size==(subList=(PackedBooleanArrSeq.UncheckedSubList)list).size && SequenceEqualityUtil.isEqualTo(rootOffset,rootOffset+size,this.arr,subList.rootOffset,subList.root.words);
      }else if(list instanceof PackedBooleanArrSeq.CheckedSubList){
        final PackedBooleanArrSeq.CheckedSubList subList;
        final PackedBooleanArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount((subList=(PackedBooleanArrSeq.CheckedSubList)list).modCount,(thatRoot=subList.root).modCount);
        return size==subList.size && SequenceEqualityUtil.isEqualTo(rootOffset,rootOffset+size,this.arr,subList.rootOffset,thatRoot.words);
      }else if(list instanceof BooleanArrSeq.UncheckedSubList){
        final BooleanArrSeq.UncheckedSubList subList;
        return size==(subList=(BooleanArrSeq.UncheckedSubList)list).size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,subList.root.arr,subList.rootOffset);
      }else if(list instanceof BooleanArrSeq.CheckedSubList){
        final BooleanArrSeq.CheckedSubList subList;
        final BooleanArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount((subList=(BooleanArrSeq.CheckedSubList)list).modCount,(thatRoot=subList.root).modCount);
        return subList.size==size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,thatRoot.arr,subList.rootOffset);
      }else{
        //must be BooleanDblLnkSeq
        final BooleanDblLnkSeq dls;
        if((dls=(BooleanDblLnkSeq)list) instanceof BooleanDblLnkSeq.CheckedSubList){
          final BooleanDblLnkSeq.CheckedSubList subList;
          CheckedCollection.checkModCount((subList=(BooleanDblLnkSeq.CheckedSubList)dls).modCount,subList.root.modCount);
        }
        return size==dls.size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,dls.head);
      }
    }
    boolean isEqualTo(int rootOffset,int size,OmniList.OfByte list){
      if(list instanceof ByteArrSeq.UncheckedList){
        final ByteArrSeq.UncheckedList that;
        return size==(that=(ByteArrSeq.UncheckedList)list).size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,that.arr,0);
      }else if(list instanceof ByteArrSeq.UncheckedSubList){
        final ByteArrSeq.UncheckedSubList subList;
        return size==(subList=(ByteArrSeq.UncheckedSubList)list).size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,subList.root.arr,subList.rootOffset);
      }else if(list instanceof ByteArrSeq.CheckedSubList){
        final ByteArrSeq.CheckedSubList subList;
        final ByteArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount((subList=(ByteArrSeq.CheckedSubList)list).modCount,(thatRoot=subList.root).modCount);
        return subList.size==size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,thatRoot.arr,subList.rootOffset);
      }else{
        //must be ByteDblLnkSeq
        final ByteDblLnkSeq dls;
        if((dls=(ByteDblLnkSeq)list) instanceof ByteDblLnkSeq.CheckedSubList){
          final ByteDblLnkSeq.CheckedSubList subList;
          CheckedCollection.checkModCount((subList=(ByteDblLnkSeq.CheckedSubList)dls).modCount,subList.root.modCount);
        }
        return size==dls.size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,dls.head);
      }
    }
    boolean isEqualTo(int rootOffset,int size,OmniList.OfChar list){
      if(list instanceof CharArrSeq.UncheckedList){
        final CharArrSeq.UncheckedList that;
        return size==(that=(CharArrSeq.UncheckedList)list).size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,that.arr,0);
      }else if(list instanceof CharArrSeq.UncheckedSubList){
        final CharArrSeq.UncheckedSubList subList;
        return size==(subList=(CharArrSeq.UncheckedSubList)list).size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,subList.root.arr,subList.rootOffset);
      }else if(list instanceof CharArrSeq.CheckedSubList){
        final CharArrSeq.CheckedSubList subList;
        final CharArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount((subList=(CharArrSeq.CheckedSubList)list).modCount,(thatRoot=subList.root).modCount);
        return subList.size==size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,thatRoot.arr,subList.rootOffset);
      }else{
        //must be CharDblLnkSeq
        final CharDblLnkSeq dls;
        if((dls=(CharDblLnkSeq)list) instanceof CharDblLnkSeq.CheckedSubList){
          final CharDblLnkSeq.CheckedSubList subList;
          CheckedCollection.checkModCount((subList=(CharDblLnkSeq.CheckedSubList)dls).modCount,subList.root.modCount);
        }
        return size==dls.size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,dls.head);
      }
    }
    boolean isEqualTo(int rootOffset,int size,OmniList.OfShort list){
      if(list instanceof ShortArrSeq.UncheckedList){
        final ShortArrSeq.UncheckedList that;
        return size==(that=(ShortArrSeq.UncheckedList)list).size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,that.arr,0);
      }else if(list instanceof ShortArrSeq.UncheckedSubList){
        final ShortArrSeq.UncheckedSubList subList;
        return size==(subList=(ShortArrSeq.UncheckedSubList)list).size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,subList.root.arr,subList.rootOffset);
      }else if(list instanceof ShortArrSeq.CheckedSubList){
        final ShortArrSeq.CheckedSubList subList;
        final ShortArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount((subList=(ShortArrSeq.CheckedSubList)list).modCount,(thatRoot=subList.root).modCount);
        return subList.size==size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,thatRoot.arr,subList.rootOffset);
      }else{
        //must be ShortDblLnkSeq
        final ShortDblLnkSeq dls;
        if((dls=(ShortDblLnkSeq)list) instanceof ShortDblLnkSeq.CheckedSubList){
          final ShortDblLnkSeq.CheckedSubList subList;
          CheckedCollection.checkModCount((subList=(ShortDblLnkSeq.CheckedSubList)dls).modCount,subList.root.modCount);
        }
        return size==dls.size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,dls.head);
      }
    }
    boolean isEqualTo(int rootOffset,int size,OmniList.OfInt list){
      if(list instanceof IntArrSeq.UncheckedList){
        final IntArrSeq.UncheckedList that;
        return size==(that=(IntArrSeq.UncheckedList)list).size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,that.arr,0);
      }else if(list instanceof IntArrSeq.UncheckedSubList){
        final IntArrSeq.UncheckedSubList subList;
        return size==(subList=(IntArrSeq.UncheckedSubList)list).size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,subList.root.arr,subList.rootOffset);
      }else if(list instanceof IntArrSeq.CheckedSubList){
        final IntArrSeq.CheckedSubList subList;
        final IntArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount((subList=(IntArrSeq.CheckedSubList)list).modCount,(thatRoot=subList.root).modCount);
        return subList.size==size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,thatRoot.arr,subList.rootOffset);
      }else{
        //must be IntDblLnkSeq
        final IntDblLnkSeq dls;
        if((dls=(IntDblLnkSeq)list) instanceof IntDblLnkSeq.CheckedSubList){
          final IntDblLnkSeq.CheckedSubList subList;
          CheckedCollection.checkModCount((subList=(IntDblLnkSeq.CheckedSubList)dls).modCount,subList.root.modCount);
        }
        return size==dls.size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,dls.head);
      }
    }
    boolean isEqualTo(int rootOffset,int size,OmniList.OfLong list){
      if(list instanceof LongArrSeq.UncheckedList){
        final LongArrSeq.UncheckedList that;
        return size==(that=(LongArrSeq.UncheckedList)list).size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,that.arr,0);
      }else if(list instanceof LongArrSeq.UncheckedSubList){
        final LongArrSeq.UncheckedSubList subList;
        return size==(subList=(LongArrSeq.UncheckedSubList)list).size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,subList.root.arr,subList.rootOffset);
      }else if(list instanceof LongArrSeq.CheckedSubList){
        final LongArrSeq.CheckedSubList subList;
        final LongArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount((subList=(LongArrSeq.CheckedSubList)list).modCount,(thatRoot=subList.root).modCount);
        return subList.size==size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,thatRoot.arr,subList.rootOffset);
      }else{
        //must be LongDblLnkSeq
        final LongDblLnkSeq dls;
        if((dls=(LongDblLnkSeq)list) instanceof LongDblLnkSeq.CheckedSubList){
          final LongDblLnkSeq.CheckedSubList subList;
          CheckedCollection.checkModCount((subList=(LongDblLnkSeq.CheckedSubList)dls).modCount,subList.root.modCount);
        }
        return size==dls.size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,dls.head);
      }
    }
    boolean isEqualTo(int rootOffset,int size,OmniList.OfFloat list){
      if(list instanceof FloatArrSeq.UncheckedList){
        final FloatArrSeq.UncheckedList that;
        return size==(that=(FloatArrSeq.UncheckedList)list).size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,that.arr,0);
      }else if(list instanceof FloatArrSeq.UncheckedSubList){
        final FloatArrSeq.UncheckedSubList subList;
        return size==(subList=(FloatArrSeq.UncheckedSubList)list).size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,subList.root.arr,subList.rootOffset);
      }else if(list instanceof FloatArrSeq.CheckedSubList){
        final FloatArrSeq.CheckedSubList subList;
        final FloatArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount((subList=(FloatArrSeq.CheckedSubList)list).modCount,(thatRoot=subList.root).modCount);
        return subList.size==size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,thatRoot.arr,subList.rootOffset);
      }else{
        //must be FloatDblLnkSeq
        final FloatDblLnkSeq dls;
        if((dls=(FloatDblLnkSeq)list) instanceof FloatDblLnkSeq.CheckedSubList){
          final FloatDblLnkSeq.CheckedSubList subList;
          CheckedCollection.checkModCount((subList=(FloatDblLnkSeq.CheckedSubList)dls).modCount,subList.root.modCount);
        }
        return size==dls.size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,dls.head);
      }
    }
    boolean isEqualTo(int rootOffset,int size,OmniList.OfDouble list){
      if(list instanceof DoubleArrSeq.UncheckedList){
        final DoubleArrSeq.UncheckedList that;
        return size==(that=(DoubleArrSeq.UncheckedList)list).size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,that.arr,0);
      }else if(list instanceof DoubleArrSeq.UncheckedSubList){
        final DoubleArrSeq.UncheckedSubList subList;
        return size==(subList=(DoubleArrSeq.UncheckedSubList)list).size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,subList.root.arr,subList.rootOffset);
      }else if(list instanceof DoubleArrSeq.CheckedSubList){
        final DoubleArrSeq.CheckedSubList subList;
        final DoubleArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount((subList=(DoubleArrSeq.CheckedSubList)list).modCount,(thatRoot=subList.root).modCount);
        return subList.size==size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,thatRoot.arr,subList.rootOffset);
      }else{
        //must be DoubleDblLnkSeq
        final DoubleDblLnkSeq dls;
        if((dls=(DoubleDblLnkSeq)list) instanceof DoubleDblLnkSeq.CheckedSubList){
          final DoubleDblLnkSeq.CheckedSubList subList;
          CheckedCollection.checkModCount((subList=(DoubleDblLnkSeq.CheckedSubList)dls).modCount,subList.root.modCount);
        }
        return size==dls.size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,dls.head);
      }
    }
    private boolean isEqualTo(int size,OmniList.OfRef<?> list){
      if(list instanceof RefArrSeq.UncheckedList){
        final RefArrSeq.UncheckedList<?> uncheckedList;
        if((uncheckedList=(RefArrSeq.UncheckedList<?>)list) instanceof RefArrSeq.CheckedList){
          final RefArrSeq.CheckedList<?> that;
          final int thatModCount=(that=(RefArrSeq.CheckedList<?>)uncheckedList).modCount;
          try{
            return size==that.size && this.isEqualToHelper(0,size,that.arr,0);
          }finally{
            CheckedCollection.checkModCount(thatModCount,that.modCount);
          }
        }else{
          return size==uncheckedList.size && this.isEqualToHelper(0,size,uncheckedList.arr,0);
        }
      }else if(list instanceof RefDblLnkSeq){
        final RefDblLnkSeq<?> dls;
        if((dls=(RefDblLnkSeq<?>)list) instanceof RefDblLnkSeq.CheckedList){
          final RefDblLnkSeq.CheckedList<?> that;
          final int modCount=(that=(RefDblLnkSeq.CheckedList<?>)dls).modCount;
          try{
            return size==that.size && SequenceEqualityUtil.isEqualTo(this.arr,0,size,that.head);
          }finally{
            CheckedCollection.checkModCount(modCount,that.modCount);
          }
        }else if(dls instanceof RefDblLnkSeq.CheckedSubList){
          final RefDblLnkSeq.CheckedSubList<?> subList;
          final int thatModCount=(subList=(RefDblLnkSeq.CheckedSubList<?>)dls).modCount;
          try{
            return size==subList.size && SequenceEqualityUtil.isEqualTo(this.arr,0,size,subList.head);
          }finally{
            CheckedCollection.checkModCount(thatModCount,subList.root.modCount);
          }
        }else{
          return size==dls.size && SequenceEqualityUtil.isEqualTo(this.arr,0,size,dls.head);
        }
      }else if(list instanceof RefArrSeq.UncheckedSubList){
        final RefArrSeq.UncheckedSubList<?> subList;
        final RefArrSeq.UncheckedList<?> thatRoot;
        return size==(subList=(RefArrSeq.UncheckedSubList<?>)list).size && ((thatRoot=subList.root)==this || this.isEqualToHelper(0,size,thatRoot.arr,subList.rootOffset));
      }else{
        //must be RefArrSeq.CheckedSubList
        final RefArrSeq.CheckedSubList<?> subList;
        final RefArrSeq.CheckedList<?> thatRoot=(subList=(RefArrSeq.CheckedSubList<?>)list).root;
        final int thatModCount=subList.modCount;
        try{
          return subList.size==size && this.isEqualToHelper(0,size,thatRoot.arr,subList.rootOffset);
        }finally{
          CheckedCollection.checkModCount(thatModCount,thatRoot.modCount);
        }
      }
    }
    private boolean isEqualTo(int rootOffset,int size,OmniList.OfRef<?> list){
      if(list instanceof RefArrSeq.UncheckedList){
        final RefArrSeq.UncheckedList<?> uncheckedList;
        if((uncheckedList=(RefArrSeq.UncheckedList<?>)list) instanceof RefArrSeq.CheckedList){
          final RefArrSeq.CheckedList<?> that;
          final int modCount=(that=(RefArrSeq.CheckedList<?>)uncheckedList).modCount;
          try{
            return size==that.size && this.isEqualToHelper(rootOffset,rootOffset+size,that.arr,0);
          }finally{
            CheckedCollection.checkModCount(modCount,that.modCount);
          }
        }else{
          return size==uncheckedList.size && (uncheckedList==this || (this.isEqualToHelper(rootOffset,rootOffset+size,uncheckedList.arr,0)));
        }
      }else if(list instanceof RefArrSeq.UncheckedSubList){
        final RefArrSeq.UncheckedSubList<?> subList;
        final RefArrSeq.UncheckedList<?> thatRoot=(subList=(RefArrSeq.UncheckedSubList<?>)list).root;
        final int thatOffset;
        return size==subList.size && (((thatOffset=subList.rootOffset)==rootOffset &&thatRoot==this) || this.isEqualToHelper(rootOffset,rootOffset+size,thatRoot.arr,thatOffset));
      }else if(list instanceof RefArrSeq.CheckedSubList){
        final RefArrSeq.CheckedSubList<?> subList;
        final RefArrSeq.CheckedList<?> thatRoot=(subList=(RefArrSeq.CheckedSubList<?>)list).root;
        final int modCount=subList.modCount;
        try{
          return size==subList.size && this.isEqualToHelper(rootOffset,rootOffset+size,thatRoot.arr,subList.rootOffset);
        }finally{
          CheckedCollection.checkModCount(modCount,thatRoot.modCount);
        }
      }else{
        //must be RefDblLnkSeq
        final RefDblLnkSeq<?> dls;
        if((dls=(RefDblLnkSeq<?>)list) instanceof RefDblLnkSeq.CheckedList){
          final RefDblLnkSeq.CheckedList<?> thatRoot;
          final int modCount=(thatRoot=(RefDblLnkSeq.CheckedList<?>)dls).modCount;
          try{
            return size==thatRoot.size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,thatRoot.head);
          }finally{
            CheckedCollection.checkModCount(modCount,thatRoot.modCount);
          }
        }else if(dls instanceof RefDblLnkSeq.CheckedSubList){
          final RefDblLnkSeq.CheckedSubList<?> subList;
          final RefDblLnkSeq.CheckedList<?> thatRoot=(subList=(RefDblLnkSeq.CheckedSubList<?>)dls).root;
          final int modCount=subList.modCount;
          try{
            return subList.size==size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,subList.head);
          }finally{
            CheckedCollection.checkModCount(modCount,thatRoot.modCount);
          }
        }else{
          return size==dls.size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,dls.head);
        }
      }
    }
    @Override public boolean equals(Object val){
      if(val==this){
        return true;
      }
      if(val instanceof List){
        final int size;
        if((size=this.size)==0){
          return ((List<?>)val).isEmpty();
        }
        final List<?> list;
        if((list=(List<?>)val) instanceof AbstractOmniCollection){
          if(list instanceof OmniList.OfRef){
            return this.isEqualTo(size,(OmniList.OfRef<?>)list);
          }else if(list instanceof OmniList.OfInt){
            return this.isEqualTo(0,size,(OmniList.OfInt)list);
          }else if(list instanceof OmniList.OfFloat){
            return this.isEqualTo(0,size,(OmniList.OfFloat)list);
          }else if(list instanceof OmniList.OfLong){
            return this.isEqualTo(0,size,(OmniList.OfLong)list);
          }else if(list instanceof OmniList.OfDouble){
            return this.isEqualTo(0,size,(OmniList.OfDouble)list);
          }else if(list instanceof OmniList.OfByte){
            return this.isEqualTo(0,size,(OmniList.OfByte)list);
          }else if(list instanceof OmniList.OfChar){
            return this.isEqualTo(0,size,(OmniList.OfChar)list);
          }else if(list instanceof OmniList.OfShort){
            return this.isEqualTo(0,size,(OmniList.OfShort)list);
          }else{
            return this.isEqualTo(0,size,(OmniList.OfBoolean)list);
          }
        }else{
          return this.isEqualTo(list.listIterator(),0,size);
        }
      }
      return false;
    }
    @Override public Object clone(){
      final Object[] copy;
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(this.arr,0,copy=new Object[size],0,size);
      }else{
        copy=OmniArray.OfRef.DEFAULT_ARR;
      }
      return new UncheckedList<E>(size,copy);
    }
    @Override void uncheckedToString(int size,StringBuilder builder){
      OmniArray.OfRef.ascendingToString(this.arr,0,size-1,builder);
    }
    @Override public int hashCode(){
      final int size;
      if((size=this.size)!=0){
        return OmniArray.OfRef.ascendingSeqHashCode(this.arr,0,size-1);
      }
      return 1;
    }
    @Override public int indexOf(boolean val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedindexOf(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(int val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedindexOf(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(long val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedindexOf(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(float val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedindexOf(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(double val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedindexOf(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Object val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedindexOfNonNull(this.arr,size,val);
            }
            return OmniArray.OfRef.uncheckedindexOfNull(this.arr,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(byte val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedindexOf(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(char val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedindexOf(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(short val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedindexOf(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Boolean val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedindexOf(this.arr,size,OmniPred.OfRef.getEqualsPred((boolean)(val)));
            }
            return OmniArray.OfRef.uncheckedindexOfNull(this.arr,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Byte val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedindexOf(this.arr,size,OmniPred.OfRef.getEqualsPred((byte)(val)));
            }
            return OmniArray.OfRef.uncheckedindexOfNull(this.arr,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Character val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedindexOf(this.arr,size,OmniPred.OfRef.getEqualsPred((char)(val)));
            }
            return OmniArray.OfRef.uncheckedindexOfNull(this.arr,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Short val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedindexOf(this.arr,size,OmniPred.OfRef.getEqualsPred((short)(val)));
            }
            return OmniArray.OfRef.uncheckedindexOfNull(this.arr,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Integer val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedindexOf(this.arr,size,OmniPred.OfRef.getEqualsPred((int)(val)));
            }
            return OmniArray.OfRef.uncheckedindexOfNull(this.arr,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Long val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedindexOf(this.arr,size,OmniPred.OfRef.getEqualsPred((long)(val)));
            }
            return OmniArray.OfRef.uncheckedindexOfNull(this.arr,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Float val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedindexOf(this.arr,size,OmniPred.OfRef.getEqualsPred((float)(val)));
            }
            return OmniArray.OfRef.uncheckedindexOfNull(this.arr,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Double val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedindexOf(this.arr,size,OmniPred.OfRef.getEqualsPred((double)(val)));
            }
            return OmniArray.OfRef.uncheckedindexOfNull(this.arr,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(boolean val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedlastIndexOf(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(int val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedlastIndexOf(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(long val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedlastIndexOf(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(float val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedlastIndexOf(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(double val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedlastIndexOf(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Object val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedlastIndexOfNonNull(this.arr,size,val);
            }
            return OmniArray.OfRef.uncheckedlastIndexOfNull(this.arr,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(byte val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedlastIndexOf(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(char val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedlastIndexOf(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(short val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedlastIndexOf(this.arr,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Boolean val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedlastIndexOf(this.arr,size,OmniPred.OfRef.getEqualsPred((boolean)(val)));
            }
            return OmniArray.OfRef.uncheckedlastIndexOfNull(this.arr,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Byte val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedlastIndexOf(this.arr,size,OmniPred.OfRef.getEqualsPred((byte)(val)));
            }
            return OmniArray.OfRef.uncheckedlastIndexOfNull(this.arr,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Character val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedlastIndexOf(this.arr,size,OmniPred.OfRef.getEqualsPred((char)(val)));
            }
            return OmniArray.OfRef.uncheckedlastIndexOfNull(this.arr,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Short val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedlastIndexOf(this.arr,size,OmniPred.OfRef.getEqualsPred((short)(val)));
            }
            return OmniArray.OfRef.uncheckedlastIndexOfNull(this.arr,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Integer val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedlastIndexOf(this.arr,size,OmniPred.OfRef.getEqualsPred((int)(val)));
            }
            return OmniArray.OfRef.uncheckedlastIndexOfNull(this.arr,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Long val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedlastIndexOf(this.arr,size,OmniPred.OfRef.getEqualsPred((long)(val)));
            }
            return OmniArray.OfRef.uncheckedlastIndexOfNull(this.arr,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Float val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedlastIndexOf(this.arr,size,OmniPred.OfRef.getEqualsPred((float)(val)));
            }
            return OmniArray.OfRef.uncheckedlastIndexOfNull(this.arr,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Double val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedlastIndexOf(this.arr,size,OmniPred.OfRef.getEqualsPred((double)(val)));
            }
            return OmniArray.OfRef.uncheckedlastIndexOfNull(this.arr,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override boolean uncheckedremoveValNonNull(int size,Object nonNull){
      {
        final var arr=this.arr;
        for(int index=0;;){
          if(nonNull.equals(arr[index])){
            OmniArray.OfRef.removeIndexAndPullDown(arr,index,--size);
            this.size=size;
            return true;
          }else if(++index==size)
          {
            return false;
          }
        }
      }
    }
    @Override boolean uncheckedremoveValNull(int size
    ){
      final var arr=this.arr;
      for(int index=0;;){
        if(
        arr[index]==null
        )
        {
          OmniArray.OfRef.removeIndexAndPullDown(arr,index,--size);
          this.size=size;
          return true;
        }else if(++index==size){
          return false;
        }
      }
    }
    @Override boolean uncheckedremoveVal (int size
    ,Predicate<Object> pred
    ){
      final var arr=this.arr;
      for(int index=0;;){
        if(
        pred.test(arr[index])
        )
        {
          OmniArray.OfRef.removeIndexAndPullDown(arr,index,--size);
          this.size=size;
          return true;
        }else if(++index==size){
          return false;
        }
      }
    }
    @Override void uncheckedForEach(int size,Consumer<? super E> action){
      {
        OmniArray.OfRef.ascendingForEach(this.arr,0,size-1,action);
      }
    }
    private static class Itr<E>
      implements OmniIterator.OfRef<E>
    {
      transient final UncheckedList<E> parent;
      transient int cursor;
      private Itr(Itr<E> itr){
        this.parent=itr.parent;
        this.cursor=itr.cursor;
      }
      private Itr(UncheckedList<E> parent){
        this.parent=parent;
        this.cursor=0;
      }
      private Itr(UncheckedList<E> parent,int cursor){
        this.parent=parent;
        this.cursor=cursor;
      }
      @Override public Object clone(){
        return new Itr<E>(this);
      }
      @Override public boolean hasNext(){
        return this.cursor<parent.size;
      }
      @SuppressWarnings("unchecked")
      @Override public E next(){
        return (E)parent.arr[cursor++];
      }
      @Override public void remove(){
        final UncheckedList<E> root;
        OmniArray.OfRef.removeIndexAndPullDown((root=this.parent).arr,--this.cursor,--root.size);
      }
      @Override public void forEachRemaining(Consumer<? super E> action){
        final int cursor,bound;
        final UncheckedList<E> parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size)){
          OmniArray.OfRef.ascendingForEach(parent.arr,cursor,bound-1,action);
          this.cursor=bound;
        }
      }
    }
    @Override public OmniIterator.OfRef<E> iterator(){
      return new Itr<E>(this);
    }
    private static class ListItr<E> extends Itr<E> implements OmniListIterator.OfRef<E>{
      transient int lastRet;
      private ListItr(ListItr<E> itr){
        super(itr);
        this.lastRet=itr.lastRet;
      }
      private ListItr(UncheckedList<E> parent){
        super(parent);
      }
      private ListItr(UncheckedList<E> parent,int cursor){
        super(parent,cursor);
      }
      @Override public Object clone(){
        return new ListItr<E>(this);
      }
      @Override public boolean hasPrevious(){
        return this.cursor>0;
      }
      @Override public int nextIndex(){
        return this.cursor;
      }
      @Override public int previousIndex(){
        return this.cursor-1;
      }
      @SuppressWarnings("unchecked")
      @Override public E next(){
        int lastRet;
        this.lastRet=lastRet=this.cursor++;
        return (E)parent.arr[lastRet];
      }
      @Override public void remove(){
        final UncheckedList<E> root;
        final int lastRet;
        OmniArray.OfRef.removeIndexAndPullDown((root=this.parent).arr,lastRet=this.lastRet,--root.size);
        this.cursor=lastRet;
      }
      @Override public void forEachRemaining(Consumer<? super E> action){
        int cursor;
        final int bound;
        final UncheckedList<E> parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size)){
          OmniArray.OfRef.ascendingForEach(parent.arr,cursor,cursor=bound-1,action);
          this.lastRet=cursor;
          this.cursor=bound;
        }
      }
      @SuppressWarnings("unchecked")
      @Override public E previous(){
        final int lastRet;
        this.lastRet=lastRet=--this.cursor;
        return (E)parent.arr[lastRet];
      }
      @Override public void set(E val){
        parent.arr[this.lastRet]=val;
      }
      @Override public void add(E val){
        final UncheckedList<E> root;
        final int rootSize;
        if((rootSize=(root=this.parent).size)!=0)
        {
          root.uncheckedInsert(this.cursor++,rootSize,val);
        }else{
          root.uncheckedInit(val);
          ++this.cursor;
        }
      }
    }
    @Override public OmniListIterator.OfRef<E> listIterator(){
      return new ListItr<E>(this);
    }
    @Override public OmniListIterator.OfRef<E> listIterator(int index){
      return new ListItr<E>(this,index);
    }
      void uncheckedInsert(int index,int size,E val){
        final int tailDist;
        if((tailDist=size-index)==0){
          super.uncheckedAppend(size,val);
        }else{
          Object[] arr;
          if((arr=this.arr).length==size){
            final Object[] tmp;
            ArrCopy.semicheckedCopy(arr,0,tmp=new Object[OmniArray.growBy50Pct(size)],0,index);
            ArrCopy.uncheckedCopy(arr,index,tmp,index+1,tailDist);
            this.arr=arr=tmp;
          }else{
            ArrCopy.uncheckedCopy(arr,index,arr,index+1,tailDist);
          }
          arr[index]=val;
          this.size=size+1;
        }
      }
    @Override public void add(int index,E val){
      final int size;
      if((size=this.size)!=0){
        this
        .uncheckedInsert(index,size,val);
      }else{
        super.uncheckedInit(val);
      }
    }
    @Override void uncheckedCopyInto(Object[] dst,int length){
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override public void put(int index,E val){
      this.arr[index]=val;
    }
    @SuppressWarnings("unchecked")
    @Override public E get(int index){
      return (E)this.arr[index];
    }
    @SuppressWarnings("unchecked")
    @Override public E set(int index,E val){
      final Object[] arr;
      final var ret=(E)(arr=this.arr)[index];
      arr[index]=val;
      return ret;
    }
    @SuppressWarnings("unchecked")
    @Override public E remove(int index){
      final Object[] arr;
      final var ret=(E)(arr=this.arr)[index];
      OmniArray.OfRef.removeIndexAndPullDown(arr,index,--size);
      return ret;
    }
    @Override public void replaceAll(UnaryOperator<E> operator){
      final int size;
      if((size=this.size)!=0){
        OmniArray.OfRef.uncheckedReplaceAll(this.arr,0,size,operator);
      }
    }
    @Override
    public void sort(Comparator<? super E> sorter)
    {
      final int size;
      if((size=this.size)>1){
        if(sorter==null){
          RefSortUtil.uncheckedStableAscendingSort(this.arr,0,size);
        }else{
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
      if((size=this.size)>1){
        if(sorter==null){
          RefSortUtil.uncheckedUnstableAscendingSort(this.arr,0,size);
        }else{
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
    @Override public OmniList.OfRef<E> subList(int fromIndex,int toIndex){
      return new UncheckedSubList<E>(this,fromIndex,toIndex-fromIndex);
    }
  }
    static class UncheckedSubList<E>
      extends AbstractOmniCollection<E>
      implements OmniList.OfRef<E>,Cloneable,RandomAccess
  {
    private static final long serialVersionUID=1L;
    transient final int rootOffset;
    transient final UncheckedList<E> root;
    transient final UncheckedSubList<E> parent;
    private UncheckedSubList(UncheckedList<E> root,int rootOffset,int size){
      super(size);
      this.root=root;
      this.parent=null;
      this.rootOffset=rootOffset;
    }
    private UncheckedSubList(UncheckedSubList<E> parent,int rootOffset,int size){
      super(size);
      this.root=parent.root;
      this.parent=parent;
      this.rootOffset=rootOffset;
    }
    private static class SerializableSubList<E> implements Serializable{
      private static final long serialVersionUID=1L;
      private transient Object[] arr;
      private transient int size;
      private transient final int rootOffset;
      private SerializableSubList(Object[] arr,int size,int rootOffset
      ){
        this.arr=arr;
        this.size=size;
        this.rootOffset=rootOffset;
      }
      private Object readResolve(){
        return new UncheckedList<E>(size,arr);
      }
      private void readObject(ObjectInputStream ois) throws IOException
        ,ClassNotFoundException
      {
        int size;
        this.size=size=ois.readInt();
        if(size!=0){
          Object[] arr;
          OmniArray.OfRef.readArray(arr=new Object[size],0,size-1,ois);
          this.arr=arr;
        }else{
          this.arr=OmniArray.OfRef.DEFAULT_ARR;
        }
      }
      private void writeObject(ObjectOutputStream oos) throws IOException{
        {
          int size;
          oos.writeInt(size=this.size);
          if(size!=0){
            final int rootOffset;
            OmniArray.OfRef.writeArray(arr,rootOffset=this.rootOffset,rootOffset+size-1,oos);
          }
        }
      }
    }
    private Object writeReplace(){
      return new SerializableSubList<E>(root.arr,this.size,this.rootOffset);
    }
    @Override public boolean equals(Object val){
      if(val==this){
        return true;
      }
      if(val instanceof List){
         final int size;
         if((size=this.size)==0){
           return ((List<?>)val).isEmpty();
         }
         final List<?> list;
         if((list=(List<?>)val) instanceof AbstractOmniCollection){
            if(list instanceof OmniList.OfRef){
              return root.isEqualTo(this.rootOffset,size,(OmniList.OfRef<?>)list);
            }else if(list instanceof OmniList.OfInt){
              return root.isEqualTo(this.rootOffset,size,(OmniList.OfInt)list);
            }else if(list instanceof OmniList.OfFloat){
              return root.isEqualTo(this.rootOffset,size,(OmniList.OfFloat)list);
            }else if(list instanceof OmniList.OfLong){
              return root.isEqualTo(this.rootOffset,size,(OmniList.OfLong)list);
            }else if(list instanceof OmniList.OfDouble){
              return root.isEqualTo(this.rootOffset,size,(OmniList.OfDouble)list);
            }else if(list instanceof OmniList.OfByte){
              return root.isEqualTo(this.rootOffset,size,(OmniList.OfByte)list);
            }else if(list instanceof OmniList.OfChar){
              return root.isEqualTo(this.rootOffset,size,(OmniList.OfChar)list);
            }else if(list instanceof OmniList.OfShort){
              return root.isEqualTo(this.rootOffset,size,(OmniList.OfShort)list);
            }else{
              return root.isEqualTo(this.rootOffset,size,(OmniList.OfBoolean)list);
            }
         }else{
            final int rootOffset;
            return root.isEqualTo(list.listIterator(),rootOffset=this.rootOffset,size+rootOffset);
         }
      }
      return false;
    }
    @Override public Object clone(){
      final Object[] copy;
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(root.arr,rootOffset,copy=new Object[size],0,size);
      }else{
        copy=OmniArray.OfRef.DEFAULT_ARR;
      }
      return new UncheckedList<E>(size,copy);
    }
    @Override public String toString(){
      {
        final int size;
        if((size=this.size)!=0){
          final StringBuilder builder;
          final int rootOffset;
          OmniArray.OfRef.ascendingToString(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,builder=new StringBuilder("["));
          return builder.append(']').toString();
        }
        return "[]";
      }
    }
  @Override public int hashCode(){
    {
      final int size;
      if((size=this.size)!=0){
        final int rootOffset;
        return OmniArray.OfRef.ascendingSeqHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
      }
      return 1;
    }
  }
    @Override public void clear(){
      final int size;
      if((size=this.size)!=0){
        for(var curr=parent;curr!=null;curr.size-=size,curr=curr.parent){}
        final UncheckedList<E> root;
        (root=this.root).size=OmniArray.OfRef.removeRangeAndPullDown(root.arr,this.rootOffset+size,root.size,size);
        this.size=0;
      }
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(int val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(long val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(float val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(double val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean remove(Object val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return this.uncheckedremoveValNonNull(size,val);
            }
            return this.uncheckedremoveValNull(size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(byte val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(char val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(short val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(Boolean val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((boolean)(val)));
            }
            return this.uncheckedremoveValNull(size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(Byte val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((byte)(val)));
            }
            return this.uncheckedremoveValNull(size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(Character val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((char)(val)));
            }
            return this.uncheckedremoveValNull(size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(Short val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((short)(val)));
            }
            return this.uncheckedremoveValNull(size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(Integer val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((int)(val)));
            }
            return this.uncheckedremoveValNull(size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(Long val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((long)(val)));
            }
            return this.uncheckedremoveValNull(size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(Float val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((float)(val)));
            }
            return this.uncheckedremoveValNull(size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(Double val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((double)(val)));
            }
            return this.uncheckedremoveValNull(size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(boolean val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int rootOffset;
            return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(int val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int rootOffset;
            return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(long val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int rootOffset;
            return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(float val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int rootOffset;
            return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(double val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int rootOffset;
            return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Object val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              final int rootOffset;
              return OmniArray.OfRef.uncheckedcontainsNonNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,val);
            }
            final int rootOffset;
            return OmniArray.OfRef.uncheckedcontainsNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(byte val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int rootOffset;
            return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(char val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int rootOffset;
            return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(short val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int rootOffset;
            return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Boolean val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              final int rootOffset;
              return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred((boolean)(val)));
            }
            final int rootOffset;
            return OmniArray.OfRef.uncheckedcontainsNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Byte val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              final int rootOffset;
              return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred((byte)(val)));
            }
            final int rootOffset;
            return OmniArray.OfRef.uncheckedcontainsNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Character val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              final int rootOffset;
              return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred((char)(val)));
            }
            final int rootOffset;
            return OmniArray.OfRef.uncheckedcontainsNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Short val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              final int rootOffset;
              return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred((short)(val)));
            }
            final int rootOffset;
            return OmniArray.OfRef.uncheckedcontainsNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Integer val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              final int rootOffset;
              return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred((int)(val)));
            }
            final int rootOffset;
            return OmniArray.OfRef.uncheckedcontainsNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Long val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              final int rootOffset;
              return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred((long)(val)));
            }
            final int rootOffset;
            return OmniArray.OfRef.uncheckedcontainsNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Float val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              final int rootOffset;
              return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred((float)(val)));
            }
            final int rootOffset;
            return OmniArray.OfRef.uncheckedcontainsNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(Double val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              final int rootOffset;
              return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred((double)(val)));
            }
            final int rootOffset;
            return OmniArray.OfRef.uncheckedcontainsNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public int indexOf(boolean val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(int val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(long val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(float val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(double val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Object val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedindexOfNonNull(root.arr,this.rootOffset,size,val);
            }
            return OmniArray.OfRef.uncheckedindexOfNull(root.arr,this.rootOffset,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(byte val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(char val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(short val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Boolean val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((boolean)(val)));
            }
            return OmniArray.OfRef.uncheckedindexOfNull(root.arr,this.rootOffset,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Byte val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((byte)(val)));
            }
            return OmniArray.OfRef.uncheckedindexOfNull(root.arr,this.rootOffset,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Character val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((char)(val)));
            }
            return OmniArray.OfRef.uncheckedindexOfNull(root.arr,this.rootOffset,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Short val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((short)(val)));
            }
            return OmniArray.OfRef.uncheckedindexOfNull(root.arr,this.rootOffset,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Integer val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((int)(val)));
            }
            return OmniArray.OfRef.uncheckedindexOfNull(root.arr,this.rootOffset,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Long val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((long)(val)));
            }
            return OmniArray.OfRef.uncheckedindexOfNull(root.arr,this.rootOffset,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Float val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((float)(val)));
            }
            return OmniArray.OfRef.uncheckedindexOfNull(root.arr,this.rootOffset,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(Double val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((double)(val)));
            }
            return OmniArray.OfRef.uncheckedindexOfNull(root.arr,this.rootOffset,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(boolean val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(int val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(long val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(float val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(double val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Object val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedlastIndexOfNonNull(root.arr,this.rootOffset,size,val);
            }
            return OmniArray.OfRef.uncheckedlastIndexOfNull(root.arr,this.rootOffset,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(byte val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(char val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(short val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Boolean val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((boolean)(val)));
            }
            return OmniArray.OfRef.uncheckedlastIndexOfNull(root.arr,this.rootOffset,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Byte val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((byte)(val)));
            }
            return OmniArray.OfRef.uncheckedlastIndexOfNull(root.arr,this.rootOffset,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Character val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((char)(val)));
            }
            return OmniArray.OfRef.uncheckedlastIndexOfNull(root.arr,this.rootOffset,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Short val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((short)(val)));
            }
            return OmniArray.OfRef.uncheckedlastIndexOfNull(root.arr,this.rootOffset,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Integer val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((int)(val)));
            }
            return OmniArray.OfRef.uncheckedlastIndexOfNull(root.arr,this.rootOffset,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Long val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((long)(val)));
            }
            return OmniArray.OfRef.uncheckedlastIndexOfNull(root.arr,this.rootOffset,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Float val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((float)(val)));
            }
            return OmniArray.OfRef.uncheckedlastIndexOfNull(root.arr,this.rootOffset,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Double val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((double)(val)));
            }
            return OmniArray.OfRef.uncheckedlastIndexOfNull(root.arr,this.rootOffset,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    private boolean uncheckedremoveValNonNull(int size,Object nonNull){
      final UncheckedList<E> root;
      final var arr=(root=this.root).arr;
      {
        for(int index=this.rootOffset,bound=index+(--size);;++index){
          if(nonNull.equals(arr[index])){
            for(var curr=parent;curr!=null;--curr.size,curr=curr.parent){}
            OmniArray.OfRef.removeIndexAndPullDown(arr,index,--root.size);
            this.size=size;
            return true;
          }else if(index==bound){
            return false;
          }
        }
      }
    }
    private boolean uncheckedremoveValNull(int size
    ){
      final UncheckedList<E> root;
      final var arr=(root=this.root).arr;
      for(int index=this.rootOffset,bound=index+(--size);;++index){
        if(
        arr[index]==null
        )
        {
          for(var curr=parent;curr!=null;--curr.size,curr=curr.parent){}
          OmniArray.OfRef.removeIndexAndPullDown(arr,index,--root.size);
          this.size=size;
          return true;
        }else if(index==bound){
          return false;
        }
      }
    }
    private boolean uncheckedremoveVal (int size
    ,Predicate<Object> pred
    ){
      final UncheckedList<E> root;
      final var arr=(root=this.root).arr;
      for(int index=this.rootOffset,bound=index+(--size);;++index){
        if(
        pred.test(arr[index])
        )
        {
          for(var curr=parent;curr!=null;--curr.size,curr=curr.parent){}
          OmniArray.OfRef.removeIndexAndPullDown(arr,index,--root.size);
          this.size=size;
          return true;
        }else if(index==bound){
          return false;
        }
      }
    }
    @Override public void forEach(Consumer<? super E> action){
      {
        final int size;
        if((size=this.size)!=0){
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
      private Itr(Itr<E> itr){
        this.parent=itr.parent;
        this.cursor=itr.cursor;
      }
      private Itr(UncheckedSubList<E> parent){
        this.parent=parent;
        this.cursor=parent.rootOffset;
      }
      private Itr(UncheckedSubList<E> parent,int cursor){
        this.parent=parent;
        this.cursor=cursor;
      }
      @Override public Object clone(){
        return new Itr<E>(this);
      }
      @Override public boolean hasNext(){
        final UncheckedSubList<E> parent;
        return this.cursor<(parent=this.parent).rootOffset+parent.size;
      }
      @SuppressWarnings("unchecked")
      @Override public E next(){
        return (E)parent.root.arr[cursor++];
      }
      @Override public void remove(){
        UncheckedSubList<E> parent;
        final UncheckedList<E> root;
        OmniArray.OfRef.removeIndexAndPullDown((root=(parent=this.parent).root).arr,--this.cursor,--root.size);
        do{
          --parent.size;
        }while((parent=parent.parent)!=null);
      }
      @Override public void forEachRemaining(Consumer<? super E> action){
        final int cursor,bound;
        final UncheckedSubList<E> parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size)){
          OmniArray.OfRef.ascendingForEach(parent.root.arr,cursor,bound-1,action);
          this.cursor=bound;
        }
      }
    }
    @Override public OmniIterator.OfRef<E> iterator(){
      return new Itr<E>(this);
    }
    private static class ListItr<E> extends Itr<E> implements OmniListIterator.OfRef<E>{
      transient int lastRet;
      private ListItr(ListItr<E> itr){
        super(itr);
        this.lastRet=itr.lastRet;
      }
      private ListItr(UncheckedSubList<E> parent){
        super(parent);
      }
      private ListItr(UncheckedSubList<E> parent,int cursor){
        super(parent,cursor);
      }
      @Override public Object clone(){
        return new ListItr<E>(this);
      }
      @Override public boolean hasPrevious(){
        return this.cursor>parent.rootOffset;
      }
      @Override public int nextIndex(){
        return this.cursor-parent.rootOffset;
      }
      @Override public int previousIndex(){
        return this.cursor-parent.rootOffset-1;
      }
      @SuppressWarnings("unchecked")
      @Override public E next(){
        int lastRet;
        this.lastRet=lastRet=this.cursor++;
        return (E)parent.root.arr[lastRet];
      }
      @Override public void remove(){
        UncheckedSubList<E> parent;
        final UncheckedList<E> root;
        final int lastRet;
        OmniArray.OfRef.removeIndexAndPullDown((root=(parent=this.parent).root).arr,lastRet=this.lastRet,--root.size);
        this.cursor=lastRet;
        do{
          --parent.size;
        }while((parent=parent.parent)!=null);
      }
      @Override public void forEachRemaining(Consumer<? super E> action){
        int cursor;
        final int bound;
        final UncheckedSubList<E> parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size)){
          OmniArray.OfRef.ascendingForEach(parent.root.arr,cursor,cursor=bound-1,action);
          this.lastRet=cursor;
          this.cursor=bound;
        }
      }
      @SuppressWarnings("unchecked")
      @Override public E previous(){
        final int lastRet;
        this.lastRet=lastRet=--this.cursor;
        return (E)parent.root.arr[lastRet];
      }
      @Override public void set(E val){
        parent.root.arr[this.lastRet]=val;
      }
      @Override public void add(E val){
        final UncheckedList<E> root;
        final int rootSize;
        UncheckedSubList<E> parent;
        if((rootSize=(root=(parent=this.parent).root).size)!=0)
        {
          root.uncheckedInsert(this.cursor++,rootSize,val);
        }else{
          root.uncheckedInit(val);
          ++this.cursor;
        }
        do{
          ++parent.size;
        }while((parent=parent.parent)!=null);
      }
    }
    @Override public OmniListIterator.OfRef<E> listIterator(){
      return new ListItr<E>(this);
    }
    @Override public OmniListIterator.OfRef<E> listIterator(int index){
      return new ListItr<E>(this,index+this.rootOffset);
    }
    @Override public boolean add(E val){
      for(var curr=parent;curr!=null;++curr.size,curr=curr.parent){}
      final UncheckedList<E> root;
      final int rootSize;
      if((rootSize=(root=this.root).size)!=0){
        root.uncheckedInsert(this.rootOffset+(this.size++),rootSize,val);
      }else{
        root.uncheckedInit(val);
        ++this.size;
      }
      return true;
    }
    @Override public void add(int index,E val){
      for(var curr=parent;curr!=null;++curr.size,curr=curr.parent){}
      ++this.size;
      final UncheckedList<E> root;
      final int rootSize;
      if((rootSize=(root=this.root).size)!=0){
        root.uncheckedInsert(this.rootOffset+index,rootSize,val);
      }else{
        root.uncheckedInit(val);
      }
    }
    @Override  public <T> T[] toArray(T[] arr){
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,arr=OmniArray.uncheckedArrResize(size,arr),0,size);
      }else if(arr.length!=0){
        arr[0]=null;
      }
      return arr;
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      final int size;
      final T[] dst;
      {
        dst=arrConstructor.apply(size=this.size);
      }
      if(size!=0){
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst,0,size);
      }
      return dst;
    }
    @Override public Object[] toArray(){
      final int size;
      if((size=this.size)!=0){
        final Object[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new Object[size],0,size);
        return dst;
      }
      return OmniArray.OfRef.DEFAULT_ARR;
    }
    @Override public void put(int index,E val){
      root.arr[index+this.rootOffset]=val;
    }
    @SuppressWarnings("unchecked")
    @Override public E get(int index){
      return (E)root.arr[index+this.rootOffset];
    }
    @SuppressWarnings("unchecked")
    @Override public E set(int index,E val){
      final Object[] arr;
      final var ret=(E)(arr=root.arr)[index+=this.rootOffset];
      arr[index]=val;
      return ret;
    }
    @SuppressWarnings("unchecked")
    @Override public E remove(int index){
      final Object[] arr;
      for(var curr=parent;curr!=null;--curr.size,curr=curr.parent){}
      final UncheckedList<E> root;
      final var ret=(E)(arr=(root=this.root).arr)[index+=this.rootOffset];
      OmniArray.OfRef.removeIndexAndPullDown(arr,index,--root.size);
      this.size=size-1;
      return ret;
    }
    @Override public boolean removeIf(Predicate<? super E> filter){
      final int size;
      if((size=this.size)!=0){
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
    @Override public void replaceAll(UnaryOperator<E> operator){
      final int size;
      if((size=this.size)!=0){
        final int rootOffset;
        OmniArray.OfRef.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);  
      }
    }
    @Override
    public void sort(Comparator<? super E> sorter)
    {
      final int size;
      if((size=this.size)>1){
        final int rootOffset;
        if(sorter==null){
          RefSortUtil.uncheckedStableAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }else{
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
      if((size=this.size)>1){
        final int rootOffset;
        if(sorter==null){
          RefSortUtil.uncheckedUnstableAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }else{
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
    @Override public OmniList.OfRef<E> subList(int fromIndex,int toIndex){
      return new UncheckedSubList<E>(this,this.rootOffset+fromIndex,toIndex-fromIndex);
    }
  }
  public
    static class CheckedStack<E>
      extends UncheckedStack<E>
  {
    private static final long serialVersionUID=1L;
    transient int modCount;
    public CheckedStack(Collection<? extends E> that){
      super(that);
    }
    public CheckedStack(OmniCollection.OfRef<? extends E> that){
      super(that);
    }
    public CheckedStack(){
      super();
    }
    public CheckedStack(int initialCapacity){
      super(initialCapacity);
    }
    CheckedStack(int size,Object[] arr){
      super(size,arr);
    }
    private class ModCountChecker extends CheckedCollection.AbstractModCountChecker{
      ModCountChecker(int modCount){
        super(modCount);
      }
      @Override protected int getActualModCount(){
        return CheckedStack.this.modCount;
      }
    }
    @Override public void writeExternal(ObjectOutput out) throws IOException{
      int modCount=this.modCount;
      try{
        super.writeExternal(out);
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override public Object clone(){
      final Object[] copy;
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(this.arr,0,copy=new Object[size],0,size);
      }else{
        copy=OmniArray.OfRef.DEFAULT_ARR;
      }
      return new CheckedStack<E>(size,copy);
    }
    @Override void uncheckedToString(int size,StringBuilder builder){
      final int modCount=this.modCount;
      try{
        super.uncheckedToString(size,builder);
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override public void clear(){
      final int size;
      if((size=this.size)!=0){
        ++this.modCount;
        this.size=0;
        OmniArray.OfRef.nullifyRange(this.arr,size-1,0);
      }
    }
    @Override public boolean contains(Object val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              final int modCount=this.modCount;
              try{
                return OmniArray.OfRef.uncheckedcontainsNonNull(this.arr,0,size-1,val);
              }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
              }
            }
            return OmniArray.OfRef.uncheckedcontainsNull(this.arr,0,size-1);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public int search(Object val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              final int modCount=this.modCount;
              try{
                return OmniArray.OfRef.uncheckedsearchNonNull(this.arr,size,val);
              }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
              }
            }
            return OmniArray.OfRef.uncheckedsearchNull(this.arr,size);  
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override boolean uncheckedremoveValNonNull(int size,Object nonNull){
      int modCount=this.modCount;
      try
      {
        final var arr=this.arr;
        for(int index=--size;;--index){
          if(nonNull.equals(arr[index])){
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.modCount=modCount+1;
            OmniArray.OfRef.removeIndexAndPullDown(arr,index,size);
            this.size=size;
            return true;
          }else if(index==0){
            CheckedCollection.checkModCount(modCount,this.modCount);
            return false;
          }
        }
      }
      catch(final ConcurrentModificationException e){
        throw e;
      }catch(final RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
    }
    @Override boolean uncheckedremoveValNull(int size
    ){
      final var arr=this.arr;
      for(int index=--size;;--index){
        if(
        arr[index]==null
        )
        {
          ++this.modCount;
          OmniArray.OfRef.removeIndexAndPullDown(arr,index,size);
          this.size=size;
          return true;
        }else if(index==0){
          return false;
        }
      }
    }
    @Override boolean uncheckedremoveVal (int size
    ,Predicate<Object> pred
    ){
      final var arr=this.arr;
      for(int index=--size;;--index){
        if(
        pred.test(arr[index])
        )
        {
          ++this.modCount;
          OmniArray.OfRef.removeIndexAndPullDown(arr,index,size);
          this.size=size;
          return true;
        }else if(index==0){
          return false;
        }
      }
    }
    @Override void uncheckedForEach(int size,Consumer<? super E> action){
      int modCount=this.modCount;
      try
      {
        OmniArray.OfRef.descendingForEach(this.arr,0,size-1,action);
      }
      finally{
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
      private Itr(Itr<E> itr){
        this.parent=itr.parent;
        this.cursor=itr.cursor;
        this.lastRet=itr.lastRet;
        this.modCount=itr.modCount;
      }
      private Itr(CheckedStack<E> parent){
        this.parent=parent;
        this.cursor=parent.size;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      @Override public Object clone(){
        return new Itr<E>(this);
      }
      @Override public boolean hasNext(){
        return this.cursor>0;
      }
      @SuppressWarnings("unchecked")
      @Override public E next(){
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
      @Override public void remove(){
        final int lastRet;
        if((lastRet=this.lastRet)!=-1){
          int modCount;
          final CheckedStack<E> root;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.parent).modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          OmniArray.OfRef.removeIndexAndPullDown(root.arr,lastRet,--root.size);
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(Consumer<? super E> action){
        final int cursor;
        if((cursor=this.cursor)>0){
          final int modCount=this.modCount;
          final var parent=this.parent;
          try{
            OmniArray.OfRef.descendingForEach(parent.arr,0,cursor-1,action);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount,cursor,this.cursor);
          }
          this.cursor=0;
          this.lastRet=0;
        }
      }
    }
    @Override public OmniIterator.OfRef<E> iterator(){
      return new Itr<E>(this);
    }
    @Override public void push(E val){
      ++this.modCount;
      super.push(val);
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      return super.toArray(arrSize->{
        final int modCount=this.modCount;
        try{
          return arrConstructor.apply(arrSize);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      });
    }
    @SuppressWarnings("unchecked")
    @Override public E pop(){
      int size;
      if((size=this.size)!=0){
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
    @Override public E poll(){
      int size;
      if((size=this.size)!=0){
        ++this.modCount;
        final var ret=(E)(arr[--size]);
        arr[size]=null;
        this.size=size;
        return ret;
      }
      return null;
    }
    @Override boolean uncheckedRemoveIf(int size,Predicate<? super E> filter){
      final int modCount=this.modCount;
      try{
        final int numRemoved;
        final Object[] arr;
        if((numRemoved=uncheckedRemoveIfImpl(arr=this.arr
          ,0,size,filter,new ModCountChecker(modCount)))
          !=0
          ){
          this.modCount=modCount+1;
          OmniArray.OfRef.nullifyRange(arr,size-1,size-=numRemoved);
          this.size=size;
          return true;
        }
      }catch(ConcurrentModificationException e){
        throw e;
      }catch(RuntimeException e){
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
    private static final long serialVersionUID=1L;
    transient int modCount;
    public CheckedList(Collection<? extends E> that){
      super(that);
    }
    public CheckedList(OmniCollection.OfRef<? extends E> that){
      super(that);
    }
    public CheckedList(){
      super();
    }
    public CheckedList(int initialCapacity){
      super(initialCapacity);
    }
    CheckedList(int size,Object[] arr){
      super(size,arr);
    }
    private class ModCountChecker extends CheckedCollection.AbstractModCountChecker{
      ModCountChecker(int modCount){
        super(modCount);
      }
      @Override protected int getActualModCount(){
        return CheckedList.this.modCount;
      }
    }
    @Override public void writeExternal(ObjectOutput out) throws IOException{
      int modCount=this.modCount;
      try{
        super.writeExternal(out);
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    private boolean isEqualTo(int size,OmniList.OfRef<?> list){
      final int thisModCount=this.modCount;
      try{
        if(list instanceof RefArrSeq.UncheckedList){
          final RefArrSeq.UncheckedList<?> uncheckedList;
          if((uncheckedList=(RefArrSeq.UncheckedList<?>)list) instanceof RefArrSeq.CheckedList){
            final RefArrSeq.CheckedList<?> that;
            final int thatModCount=(that=(RefArrSeq.CheckedList<?>)uncheckedList).modCount;
            try{
              return size==that.size && super.isEqualToHelper(0,size,that.arr,0);
            }finally{
              CheckedCollection.checkModCount(thatModCount,that.modCount);
            }
          }else{
            return size==uncheckedList.size && super.isEqualToHelper(0,size,uncheckedList.arr,0);
          }
        }else if(list instanceof RefDblLnkSeq){
          final RefDblLnkSeq<?> dls;
          if((dls=(RefDblLnkSeq<?>)list) instanceof RefDblLnkSeq.CheckedList){
            final RefDblLnkSeq.CheckedList<?> that;
            final int thatModCount=(that=(RefDblLnkSeq.CheckedList<?>)dls).modCount;
            try{
              return size==that.size && SequenceEqualityUtil.isEqualTo(this.arr,0,size,that.head);
            }finally{
              CheckedCollection.checkModCount(thatModCount,that.modCount);
            }
          }else if(dls instanceof RefDblLnkSeq.CheckedSubList){
            final RefDblLnkSeq.CheckedSubList<?> that;
            final int thatModCount=(that=(RefDblLnkSeq.CheckedSubList<?>)dls).modCount;
            try{
              return size==that.size && SequenceEqualityUtil.isEqualTo(this.arr,0,size,that.head);
            }finally{
              CheckedCollection.checkModCount(thatModCount,that.root.modCount);
            }
          }else{
            return size==dls.size && SequenceEqualityUtil.isEqualTo(this.arr,0,size,((RefDblLnkSeq<?>)dls).head);
          }
        }else if(list instanceof RefArrSeq.CheckedSubList){
          final RefArrSeq.CheckedSubList<?> subList;
          final var thatRoot=(subList=(RefArrSeq.CheckedSubList<?>)list).root;
          final int thatModCount=subList.modCount;
          try{
            return size==subList.size && (thatRoot==this||super.isEqualToHelper(0,size,thatRoot.arr,subList.rootOffset));
          }finally{
            CheckedCollection.checkModCount(thatModCount,thatRoot.modCount);
          }
        }else{
          //must be RefArrSeq.UncheckedSubList
          final RefArrSeq.UncheckedSubList<?> subList;
          return size==(subList=(RefArrSeq.UncheckedSubList<?>)list).size && super.isEqualToHelper(0,size,subList.root.arr,subList.rootOffset);
        }
      }finally{
        CheckedCollection.checkModCount(thisModCount,this.modCount);
      }
    }
    private boolean isEqualTo(int rootOffset,int size,OmniList.OfRef<?> list){
      if(list instanceof RefArrSeq.UncheckedList){
        final RefArrSeq.UncheckedList<?> uncheckedList;
        if((uncheckedList=(RefArrSeq.UncheckedList<?>)list) instanceof RefArrSeq.CheckedList){
          final RefArrSeq.CheckedList<?> that;
          final int thatModCount=(that=(RefArrSeq.CheckedList<?>)uncheckedList).modCount;
          try{
            return size==that.size && (this==that || super.isEqualToHelper(rootOffset,rootOffset+size,that.arr,0));
          }finally{
            CheckedCollection.checkModCount(thatModCount,that.modCount);
          }
        }else{
          return size==uncheckedList.size && super.isEqualToHelper(rootOffset,rootOffset+size,uncheckedList.arr,0);
        }
      }else if(list instanceof RefArrSeq.CheckedSubList){
        final RefArrSeq.CheckedSubList<?> subList;
        final RefArrSeq.CheckedList<?> thatRoot=(subList=(RefArrSeq.CheckedSubList<?>)list).root;
        final int thatModCount=subList.modCount;
        try{
          final int thatOffset;
          return subList.size==size && (((thatOffset=subList.rootOffset)==rootOffset && thatRoot==this) || super.isEqualToHelper(rootOffset,rootOffset+size,thatRoot.arr,thatOffset));
        }finally{
          CheckedCollection.checkModCount(thatModCount,thatRoot.modCount);
        }
      }else if(list instanceof RefArrSeq.UncheckedSubList){
        final RefArrSeq.UncheckedSubList<?> subList;
        return size== (subList=(RefArrSeq.UncheckedSubList<?>)list).size && super.isEqualToHelper(rootOffset,rootOffset+size,subList.root.arr,subList.rootOffset);
      }else{
        //must be RefDblLnkSeq
        final RefDblLnkSeq<?> dls;
        if((dls=(RefDblLnkSeq<?>)list) instanceof RefDblLnkSeq.CheckedList){
          final RefDblLnkSeq.CheckedList<?> thatRoot;
          final int modCount=(thatRoot=(RefDblLnkSeq.CheckedList<?>)dls).modCount;
          try{
            return size==thatRoot.size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,thatRoot.head);
          }finally{
            CheckedCollection.checkModCount(modCount,thatRoot.modCount);
          }
        }else if(dls instanceof RefDblLnkSeq.CheckedSubList){
          final RefDblLnkSeq.CheckedSubList<?> subList;
          final RefDblLnkSeq.CheckedList<?> thatRoot=(subList=(RefDblLnkSeq.CheckedSubList<?>)dls).root;
          final int modCount=subList.modCount;
          try{
            return size==subList.size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,subList.head);
          }finally{
            CheckedCollection.checkModCount(modCount,thatRoot.modCount);
          }
        }else{
          return size==dls.size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,dls.head);
        }
      }
    }
    @Override public boolean equals(Object val){
      if(val==this){
        return true;
      }
      if(val instanceof List){
         final int size;
         if((size=this.size)==0){
           return ((List<?>)val).isEmpty();
         }
         final List<?> list;
         if((list=(List<?>)val) instanceof AbstractOmniCollection){
           if(list instanceof OmniList.OfRef){
             return this.isEqualTo(size,(OmniList.OfRef<?>)list);
           }else if(list instanceof OmniList.OfInt){
             return super.isEqualTo(0,size,(OmniList.OfInt)list);
           }else if(list instanceof OmniList.OfFloat){
             return super.isEqualTo(0,size,(OmniList.OfFloat)list);
           }else if(list instanceof OmniList.OfLong){
             return super.isEqualTo(0,size,(OmniList.OfLong)list);
           }else if(list instanceof OmniList.OfDouble){
             return super.isEqualTo(0,size,(OmniList.OfDouble)list);
           }else if(list instanceof OmniList.OfByte){
             return super.isEqualTo(0,size,(OmniList.OfByte)list);
           }else if(list instanceof OmniList.OfChar){
             return super.isEqualTo(0,size,(OmniList.OfChar)list);
           }else if(list instanceof OmniList.OfShort){
             return super.isEqualTo(0,size,(OmniList.OfShort)list);
           }else{
             return super.isEqualTo(0,size,(OmniList.OfBoolean)list);
           }
         }else{
           final int modCount=this.modCount;
           try{
             return super.isEqualTo(list.listIterator(),0,size);
           }finally{
             CheckedCollection.checkModCount(modCount,this.modCount);
           }
         }
      }
      return false;
    }
    @Override public Object clone(){
      final Object[] copy;
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(this.arr,0,copy=new Object[size],0,size);
      }else{
        copy=OmniArray.OfRef.DEFAULT_ARR;
      }
      return new CheckedList<E>(size,copy);
    }
    @Override void uncheckedToString(int size,StringBuilder builder){
      final int modCount=this.modCount;
      try{
        super.uncheckedToString(size,builder);
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
  @Override public int hashCode(){
    final int size;
    if((size=this.size)!=0){
      final int modCount=this.modCount;
      try{
        return OmniArray.OfRef.ascendingSeqHashCode(this.arr,0,size-1);
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    return 1;
  }
    @Override public void clear(){
      final int size;
      if((size=this.size)!=0){
        ++this.modCount;
        this.size=0;
        OmniArray.OfRef.nullifyRange(this.arr,size-1,0);
      }
    }
    @Override public boolean contains(Object val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              final int modCount=this.modCount;
              try{
                return OmniArray.OfRef.uncheckedcontainsNonNull(this.arr,0,size-1,val);
              }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
              }
            }
            return OmniArray.OfRef.uncheckedcontainsNull(this.arr,0,size-1);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public int indexOf(Object val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              final int modCount=this.modCount;
              try{
                return OmniArray.OfRef.uncheckedindexOfNonNull(this.arr,size,val);
              }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
              }
            }
            return OmniArray.OfRef.uncheckedindexOfNull(this.arr,size);  
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Object val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              final int modCount=this.modCount;
              try{
                return OmniArray.OfRef.uncheckedlastIndexOfNonNull(this.arr,size,val);
              }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
              }
            }
            return OmniArray.OfRef.uncheckedlastIndexOfNull(this.arr,size);  
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override boolean uncheckedremoveValNonNull(int size,Object nonNull){
      int modCount=this.modCount;
      try
      {
        final var arr=this.arr;
        for(int index=0;;){
          if(nonNull.equals(arr[index])){
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.modCount=modCount+1;
            OmniArray.OfRef.removeIndexAndPullDown(arr,index,--size);
            this.size=size;
            return true;
          }else if(++index==size)
          {
            CheckedCollection.checkModCount(modCount,this.modCount);
            return false;
          }
        }
      }
      catch(final ConcurrentModificationException e){
        throw e;
      }catch(final RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
    }
    @Override boolean uncheckedremoveValNull(int size
    ){
      final var arr=this.arr;
      for(int index=0;;){
        if(
        arr[index]==null
        )
        {
          ++this.modCount;
          OmniArray.OfRef.removeIndexAndPullDown(arr,index,--size);
          this.size=size;
          return true;
        }else if(++index==size){
          return false;
        }
      }
    }
    @Override boolean uncheckedremoveVal (int size
    ,Predicate<Object> pred
    ){
      final var arr=this.arr;
      for(int index=0;;){
        if(
        pred.test(arr[index])
        )
        {
          ++this.modCount;
          OmniArray.OfRef.removeIndexAndPullDown(arr,index,--size);
          this.size=size;
          return true;
        }else if(++index==size){
          return false;
        }
      }
    }
    @Override void uncheckedForEach(int size,Consumer<? super E> action){
      int modCount=this.modCount;
      try
      {
        OmniArray.OfRef.ascendingForEach(this.arr,0,size-1,action);
      }
      finally{
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
      private Itr(Itr<E> itr){
        this.parent=itr.parent;
        this.cursor=itr.cursor;
        this.lastRet=itr.lastRet;
        this.modCount=itr.modCount;
      }
      private Itr(CheckedList<E> parent){
        this.parent=parent;
        this.cursor=0;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      private Itr(CheckedList<E> parent,int cursor){
        this.parent=parent;
        this.cursor=cursor;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      @Override public Object clone(){
        return new Itr<E>(this);
      }
      @Override public boolean hasNext(){
        return this.cursor<parent.size;
      }
      @SuppressWarnings("unchecked")
      @Override public E next(){
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
      @Override public void remove(){
        final int lastRet;
        if((lastRet=this.lastRet)!=-1){
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
      @Override public void forEachRemaining(Consumer<? super E> action){
        final int cursor;
        final int bound;
        final CheckedList<E> parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size)){
          final int modCount=this.modCount;
          final int lastRet;
          try{
            OmniArray.OfRef.ascendingForEach(parent.arr,cursor,lastRet=bound-1,action);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount,cursor,this.cursor);
          }
          this.cursor=bound;
          this.lastRet=lastRet;
        }
      }
    }
    @Override public OmniIterator.OfRef<E> iterator(){
      return new Itr<E>(this);
    }
    private static class ListItr<E> extends Itr<E> implements OmniListIterator.OfRef<E>{
      private ListItr(ListItr<E> itr){
        super(itr);
      }
      private ListItr(CheckedList<E> parent){
        super(parent);
      }
      private ListItr(CheckedList<E> parent,int cursor){
        super(parent,cursor);
      }
      @Override public Object clone(){
        return new ListItr<E>(this);
      }
      @Override public boolean hasPrevious(){
        return this.cursor>0;
      }
      @Override public int nextIndex(){
        return this.cursor;
      }
      @Override public int previousIndex(){
        return this.cursor-1;
      }
      @SuppressWarnings("unchecked")
      @Override public E previous(){
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
      @Override public void set(E val){
        final int lastRet;
        if((lastRet=this.lastRet)!=-1){
          final CheckedList<E> root;
          CheckedCollection.checkModCount(modCount,(root=this.parent).modCount);
          root.arr[lastRet]=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void add(E val){
        int modCount;
        final CheckedList<E> root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.parent).modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        this.lastRet=-1;
        final int rootSize;
        if((rootSize=root.size)!=0)
        {
          root.uncheckedInsert(this.cursor++,rootSize,val);
        }else{
          root.uncheckedInit(val);
          ++this.cursor;
        }
      }
    }
    @Override public OmniListIterator.OfRef<E> listIterator(){
      return new ListItr<E>(this);
    }
    @Override public OmniListIterator.OfRef<E> listIterator(int index){
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,this.size);
      return new ListItr<E>(this,index);
    }
    @Override public void push(E val){
      ++this.modCount;
      super.push(val);
    }
    @Override public void add(int index,E val){
      final int size;
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,size=this.size);
      ++this.modCount;
      if(size!=0){
        super
        .uncheckedInsert(index,size,val);
      }else{
        super.uncheckedInit(val);
      }
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      return super.toArray(arrSize->{
        final int modCount=this.modCount;
        try{
          return arrConstructor.apply(arrSize);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      });
    }
    @Override public void put(int index,E val){
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      this.arr[index]=val;
    }
    @SuppressWarnings("unchecked")
    @Override public E get(int index){
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      return (E)this.arr[index];
    }
    @SuppressWarnings("unchecked")
    @Override public E set(int index,E val){
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      final Object[] arr;
      final var ret=(E)(arr=this.arr)[index];
      arr[index]=val;
      return ret;
    }
    @SuppressWarnings("unchecked")
    @Override public E remove(int index){
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
    @Override boolean uncheckedRemoveIf(int size,Predicate<? super E> filter){
      final int modCount=this.modCount;
      try{
        final int numRemoved;
        final Object[] arr;
        if((numRemoved=uncheckedRemoveIfImpl(arr=this.arr
          ,0,size,filter,new ModCountChecker(modCount)))
          !=0
          ){
          this.modCount=modCount+1;
          OmniArray.OfRef.nullifyRange(arr,size-1,size-=numRemoved);
          this.size=size;
          return true;
        }
      }catch(ConcurrentModificationException e){
        throw e;
      }catch(RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,this.modCount,e);
      }
      CheckedCollection.checkModCount(modCount,this.modCount);
      return false;
    }
    @Override public void replaceAll(UnaryOperator<E> operator){
      final int size;
      if((size=this.size)!=0){
        int modCount=this.modCount;
        try{
          OmniArray.OfRef.uncheckedReplaceAll(this.arr,0,size,operator);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override
    public void sort(Comparator<? super E> sorter)
    {
      final int size;
      if((size=this.size)>1){
        final int modCount=this.modCount;
        try{
          if(sorter==null){
            RefSortUtil.uncheckedStableAscendingSort(this.arr,0,size);
          }else{
            RefSortUtil.uncheckedStableSort(this.arr,0,size,sorter);
          }
        }catch(ArrayIndexOutOfBoundsException e){
          throw new IllegalArgumentException("Comparison method violates its general contract!",e);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override
    public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)>1){
        final int modCount=this.modCount;
        try{
          RefSortUtil.uncheckedStableAscendingSort(this.arr,0,size);
        }catch(ArrayIndexOutOfBoundsException e){
          throw new IllegalArgumentException("Comparison method violates its general contract!",e);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override
    public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1){
        final int modCount=this.modCount;
        try{
          RefSortUtil.uncheckedStableDescendingSort(this.arr,0,size);
        }catch(ArrayIndexOutOfBoundsException e){
          throw new IllegalArgumentException("Comparison method violates its general contract!",e);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override
    public void unstableSort(Comparator<? super E> sorter)
    {
      final int size;
      if((size=this.size)>1){
        final int modCount=this.modCount;
        try{
          if(sorter==null){
            RefSortUtil.uncheckedUnstableAscendingSort(this.arr,0,size);
          }else{
            RefSortUtil.uncheckedUnstableSort(this.arr,0,size,sorter);
          }
        }catch(ArrayIndexOutOfBoundsException e){
          throw new IllegalArgumentException("Comparison method violates its general contract!",e);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override
    public void unstableAscendingSort()
    {
      final int size;
      if((size=this.size)>1){
        final int modCount=this.modCount;
        try{
          RefSortUtil.uncheckedUnstableAscendingSort(this.arr,0,size);
        }catch(ArrayIndexOutOfBoundsException e){
          throw new IllegalArgumentException("Comparison method violates its general contract!",e);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override
    public void unstableDescendingSort()
    {
      final int size;
      if((size=this.size)>1){
        final int modCount=this.modCount;
        try{
          RefSortUtil.uncheckedUnstableDescendingSort(this.arr,0,size);
        }catch(ArrayIndexOutOfBoundsException e){
          throw new IllegalArgumentException("Comparison method violates its general contract!",e);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override public OmniList.OfRef<E> subList(int fromIndex,int toIndex){
      return new CheckedSubList<E>(this,fromIndex,CheckedCollection.checkSubListRange(fromIndex,toIndex,this.size));
    }
  }
    static class CheckedSubList<E>
      extends AbstractOmniCollection<E>
      implements OmniList.OfRef<E>,Cloneable,RandomAccess
  {
    private static final long serialVersionUID=1L;
    transient int modCount;
    transient final int rootOffset;
    transient final CheckedList<E> root;
    transient final CheckedSubList<E> parent;
    private CheckedSubList(CheckedList<E> root,int rootOffset,int size){
      super(size);
      this.root=root;
      this.parent=null;
      this.rootOffset=rootOffset;
      this.modCount=root.modCount;
    }
    private CheckedSubList(CheckedSubList<E> parent,int rootOffset,int size){
      super(size);
      this.root=parent.root;
      this.parent=parent;
      this.rootOffset=rootOffset;
      this.modCount=parent.modCount;
    }
    private static class SerializableSubList<E> implements Serializable{
      private static final long serialVersionUID=1L;
      private transient Object[] arr;
      private transient int size;
      private transient final int rootOffset;
      private transient final CheckedList<E>.ModCountChecker modCountChecker;
      private SerializableSubList(Object[] arr,int size,int rootOffset
        ,CheckedList<E>.ModCountChecker modCountChecker
      ){
        this.arr=arr;
        this.size=size;
        this.rootOffset=rootOffset;
        this.modCountChecker=modCountChecker;
      }
      private Object readResolve(){
        return new CheckedList<E>(size,arr);
      }
      private void readObject(ObjectInputStream ois) throws IOException
        ,ClassNotFoundException
      {
        int size;
        this.size=size=ois.readInt();
        if(size!=0){
          Object[] arr;
          OmniArray.OfRef.readArray(arr=new Object[size],0,size-1,ois);
          this.arr=arr;
        }else{
          this.arr=OmniArray.OfRef.DEFAULT_ARR;
        }
      }
      private void writeObject(ObjectOutputStream oos) throws IOException{
        try
        {
          int size;
          oos.writeInt(size=this.size);
          if(size!=0){
            final int rootOffset;
            OmniArray.OfRef.writeArray(arr,rootOffset=this.rootOffset,rootOffset+size-1,oos);
          }
        }
        finally{
          modCountChecker.checkModCount();
        }
      }
    }
    private Object writeReplace(){
      final CheckedList<E> root;
      return new SerializableSubList<E>((root=this.root).arr,this.size,this.rootOffset,root.new ModCountChecker(this.modCount));
    }
    @Override public boolean equals(Object val){
      if(val==this){
        return true;
      }
      if(val instanceof List){
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          final int size;
          if((size=this.size)==0){
            return ((List<?>)val).isEmpty();
          }
          final List<?> list;
          if((list=(List<?>)val) instanceof AbstractOmniCollection){
            final int rootOffset=this.rootOffset;
            if(list instanceof OmniList.OfRef){
              return root.isEqualTo(rootOffset,size,(OmniList.OfRef<?>)list);
            }else if(list instanceof OmniList.OfInt){
              return root.isEqualTo(rootOffset,size,(OmniList.OfInt)list);
            }else if(list instanceof OmniList.OfFloat){
              return root.isEqualTo(rootOffset,size,(OmniList.OfFloat)list);
            }else if(list instanceof OmniList.OfLong){
              return root.isEqualTo(rootOffset,size,(OmniList.OfLong)list);
            }else if(list instanceof OmniList.OfDouble){
              return root.isEqualTo(rootOffset,size,(OmniList.OfDouble)list);
            }else if(list instanceof OmniList.OfByte){
              return root.isEqualTo(rootOffset,size,(OmniList.OfByte)list);
            }else if(list instanceof OmniList.OfChar){
              return root.isEqualTo(rootOffset,size,(OmniList.OfChar)list);
            }else if(list instanceof OmniList.OfShort){
              return root.isEqualTo(rootOffset,size,(OmniList.OfShort)list);
            }else{
              return root.isEqualTo(rootOffset,size,(OmniList.OfBoolean)list);
            }
          }else{
            final int rootOffset;
            return root.isEqualTo(list.listIterator(),rootOffset=this.rootOffset,rootOffset+size);
          }
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      return false;
    }
    @Override public Object clone(){
      final CheckedList<E> root;
      CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
      final Object[] copy;
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(root.arr,rootOffset,copy=new Object[size],0,size);
      }else{
        copy=OmniArray.OfRef.DEFAULT_ARR;
      }
      return new CheckedList<E>(size,copy);
    }
    @Override public String toString(){
      final int modCount=this.modCount;
      final var root=this.root;
      try
      {
        final int size;
        if((size=this.size)!=0){
          final StringBuilder builder;
          final int rootOffset;
          OmniArray.OfRef.ascendingToString(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,builder=new StringBuilder("["));
          return builder.append(']').toString();
        }
        return "[]";
      }
      finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
  @Override public int hashCode(){
    final int modCount=this.modCount;
    final var root=this.root;
    try
    {
      final int size;
      if((size=this.size)!=0){
        final int rootOffset;
        return OmniArray.OfRef.ascendingSeqHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
      }
      return 1;
    }
    finally{
      CheckedCollection.checkModCount(modCount,root.modCount);
    }
  }
    @Override public int size(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return this.size;
    }
    @Override public boolean isEmpty(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return this.size==0;
    }
    @Override public void clear(){
      final CheckedList<E> root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0){
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr.size-=size,curr=curr.parent){}
        root.size=OmniArray.OfRef.removeRangeAndPullDown(root.arr,this.rootOffset+size,root.size,size);
        this.size=0;
      }
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(int val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(long val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(float val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(double val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean remove(Object val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return this.uncheckedremoveValNonNull(size,val);
            }
            return this.uncheckedremoveValNull(size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(byte val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(char val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(short val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(Boolean val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((boolean)(val)));
            }
            return this.uncheckedremoveValNull(size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(Byte val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((byte)(val)));
            }
            return this.uncheckedremoveValNull(size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(Character val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((char)(val)));
            }
            return this.uncheckedremoveValNull(size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(Short val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((short)(val)));
            }
            return this.uncheckedremoveValNull(size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(Integer val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((int)(val)));
            }
            return this.uncheckedremoveValNull(size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(Long val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((long)(val)));
            }
            return this.uncheckedremoveValNull(size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(Float val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((float)(val)));
            }
            return this.uncheckedremoveValNull(size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(Double val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return this.uncheckedremoveVal(size,OmniPred.OfRef.getEqualsPred((double)(val)));
            }
            return this.uncheckedremoveValNull(size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean contains(boolean val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              final int rootOffset;
              return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return false;
    }
    @Override public boolean contains(int val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              final int rootOffset;
              return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return false;
    }
    @Override public boolean contains(long val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              final int rootOffset;
              return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return false;
    }
    @Override public boolean contains(float val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              final int rootOffset;
              return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return false;
    }
    @Override public boolean contains(double val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              final int rootOffset;
              return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return false;
    }
    @Override public boolean contains(Object val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              final int rootOffset;
              return OmniArray.OfRef.uncheckedcontainsNonNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,val);
            }
            final int rootOffset;
            return OmniArray.OfRef.uncheckedcontainsNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return false;
    }
    @Override public boolean contains(byte val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              final int rootOffset;
              return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return false;
    }
    @Override public boolean contains(char val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              final int rootOffset;
              return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return false;
    }
    @Override public boolean contains(short val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              final int rootOffset;
              return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return false;
    }
    @Override public boolean contains(Boolean val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
                final int rootOffset;
                return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred((boolean)(val)));
            }
            final int rootOffset;
            return OmniArray.OfRef.uncheckedcontainsNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return false;
    }
    @Override public boolean contains(Byte val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
                final int rootOffset;
                return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred((byte)(val)));
            }
            final int rootOffset;
            return OmniArray.OfRef.uncheckedcontainsNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return false;
    }
    @Override public boolean contains(Character val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
                final int rootOffset;
                return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred((char)(val)));
            }
            final int rootOffset;
            return OmniArray.OfRef.uncheckedcontainsNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return false;
    }
    @Override public boolean contains(Short val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
                final int rootOffset;
                return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred((short)(val)));
            }
            final int rootOffset;
            return OmniArray.OfRef.uncheckedcontainsNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return false;
    }
    @Override public boolean contains(Integer val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
                final int rootOffset;
                return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred((int)(val)));
            }
            final int rootOffset;
            return OmniArray.OfRef.uncheckedcontainsNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return false;
    }
    @Override public boolean contains(Long val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
                final int rootOffset;
                return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred((long)(val)));
            }
            final int rootOffset;
            return OmniArray.OfRef.uncheckedcontainsNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return false;
    }
    @Override public boolean contains(Float val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
                final int rootOffset;
                return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred((float)(val)));
            }
            final int rootOffset;
            return OmniArray.OfRef.uncheckedcontainsNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return false;
    }
    @Override public boolean contains(Double val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
                final int rootOffset;
                return OmniArray.OfRef.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,OmniPred.OfRef.getEqualsPred((double)(val)));
            }
            final int rootOffset;
            return OmniArray.OfRef.uncheckedcontainsNull(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return false;
    }
    @Override public int indexOf(boolean val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int indexOf(int val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int indexOf(long val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int indexOf(float val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int indexOf(double val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int indexOf(Object val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedindexOfNonNull(root.arr,this.rootOffset,size,val);
            }
            return OmniArray.OfRef.uncheckedindexOfNull(root.arr,this.rootOffset,size);
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int indexOf(byte val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int indexOf(char val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int indexOf(short val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int indexOf(Boolean val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
                return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((boolean)(val)));
            }
            return OmniArray.OfRef.uncheckedindexOfNull(root.arr,this.rootOffset,size);
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int indexOf(Byte val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
                return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((byte)(val)));
            }
            return OmniArray.OfRef.uncheckedindexOfNull(root.arr,this.rootOffset,size);
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int indexOf(Character val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
                return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((char)(val)));
            }
            return OmniArray.OfRef.uncheckedindexOfNull(root.arr,this.rootOffset,size);
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int indexOf(Short val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
                return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((short)(val)));
            }
            return OmniArray.OfRef.uncheckedindexOfNull(root.arr,this.rootOffset,size);
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int indexOf(Integer val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
                return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((int)(val)));
            }
            return OmniArray.OfRef.uncheckedindexOfNull(root.arr,this.rootOffset,size);
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int indexOf(Long val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
                return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((long)(val)));
            }
            return OmniArray.OfRef.uncheckedindexOfNull(root.arr,this.rootOffset,size);
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int indexOf(Float val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
                return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((float)(val)));
            }
            return OmniArray.OfRef.uncheckedindexOfNull(root.arr,this.rootOffset,size);
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int indexOf(Double val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
                return OmniArray.OfRef.uncheckedindexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((double)(val)));
            }
            return OmniArray.OfRef.uncheckedindexOfNull(root.arr,this.rootOffset,size);
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(boolean val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(int val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(long val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(float val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(double val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Object val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
              return OmniArray.OfRef.uncheckedlastIndexOfNonNull(root.arr,this.rootOffset,size,val);
            }
            return OmniArray.OfRef.uncheckedlastIndexOfNull(root.arr,this.rootOffset,size);
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(byte val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(char val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(short val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Boolean val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
                return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((boolean)(val)));
            }
            return OmniArray.OfRef.uncheckedlastIndexOfNull(root.arr,this.rootOffset,size);
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Byte val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
                return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((byte)(val)));
            }
            return OmniArray.OfRef.uncheckedlastIndexOfNull(root.arr,this.rootOffset,size);
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Character val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
                return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((char)(val)));
            }
            return OmniArray.OfRef.uncheckedlastIndexOfNull(root.arr,this.rootOffset,size);
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Short val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
                return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((short)(val)));
            }
            return OmniArray.OfRef.uncheckedlastIndexOfNull(root.arr,this.rootOffset,size);
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Integer val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
                return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((int)(val)));
            }
            return OmniArray.OfRef.uncheckedlastIndexOfNull(root.arr,this.rootOffset,size);
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Long val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
                return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((long)(val)));
            }
            return OmniArray.OfRef.uncheckedlastIndexOfNull(root.arr,this.rootOffset,size);
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Float val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
                return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((float)(val)));
            }
            return OmniArray.OfRef.uncheckedlastIndexOfNull(root.arr,this.rootOffset,size);
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(Double val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=null){
                return OmniArray.OfRef.uncheckedlastIndexOf(root.arr,this.rootOffset,size,OmniPred.OfRef.getEqualsPred((double)(val)));
            }
            return OmniArray.OfRef.uncheckedlastIndexOfNull(root.arr,this.rootOffset,size);
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    private boolean uncheckedremoveValNonNull(int size,Object nonNull){
      int modCount=this.modCount;
      final CheckedList<E> root;
      final var arr=(root=this.root).arr;
      try
      {
        for(int index=this.rootOffset,bound=index+(--size);;++index){
          if(nonNull.equals(arr[index])){
            CheckedCollection.checkModCount(modCount,root.modCount);
            root.modCount=++modCount;
            this.modCount=modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,--curr.size,curr=curr.parent){}
            OmniArray.OfRef.removeIndexAndPullDown(arr,index,--root.size);
            this.size=size;
            return true;
          }else if(index==bound){
            CheckedCollection.checkModCount(modCount,root.modCount);
            return false;
          }
        }
      }
      catch(final ConcurrentModificationException e){
        throw e;
      }catch(final RuntimeException e){
        throw CheckedCollection.checkModCount(modCount,root.modCount,e);
      }
    }
    private boolean uncheckedremoveValNull(int size
    ){
      int modCount;
      final CheckedList<E> root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      final var arr=root.arr;
      for(int index=this.rootOffset,bound=index+(--size);;++index){
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
        }else if(index==bound){
          return false;
        }
      }
    }
    private boolean uncheckedremoveVal (int size
    ,Predicate<Object> pred
    ){
      int modCount;
      final CheckedList<E> root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      final var arr=root.arr;
      for(int index=this.rootOffset,bound=index+(--size);;++index){
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
        }else if(index==bound){
          return false;
        }
      }
    }
    @Override public void forEach(Consumer<? super E> action){
      int modCount=this.modCount;
      final var root=this.root;
      try
      {
        final int size;
        if((size=this.size)!=0){
          final int rootOffset;
          OmniArray.OfRef.ascendingForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,action);
        }
      }
      finally{
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
      private Itr(Itr<E> itr){
        this.parent=itr.parent;
        this.cursor=itr.cursor;
        this.lastRet=itr.lastRet;
        this.modCount=itr.modCount;
      }
      private Itr(CheckedSubList<E> parent){
        this.parent=parent;
        this.cursor=parent.rootOffset;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      private Itr(CheckedSubList<E> parent,int cursor){
        this.parent=parent;
        this.cursor=cursor;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      @Override public Object clone(){
        return new Itr<E>(this);
      }
      @Override public boolean hasNext(){
        final CheckedSubList<E> parent;
        return this.cursor<(parent=this.parent).rootOffset+parent.size;
      }
      @SuppressWarnings("unchecked")
      @Override public E next(){
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
      @Override public void remove(){
        final int lastRet;
        if((lastRet=this.lastRet)!=-1){
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
      @Override public void forEachRemaining(Consumer<? super E> action){
        final int cursor;
        final int bound;
        final CheckedSubList<E> parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size)){
          final int modCount=this.modCount;
          final var root=parent.root;
          final int lastRet;
          try{
            OmniArray.OfRef.ascendingForEach(root.arr,cursor,lastRet=bound-1,action);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount,cursor,this.cursor);
          }
          this.cursor=bound;
          this.lastRet=lastRet;
        }
      }
    }
    @Override public OmniIterator.OfRef<E> iterator(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new Itr<E>(this);
    }
    private static class ListItr<E> extends Itr<E> implements OmniListIterator.OfRef<E>{
      private ListItr(ListItr<E> itr){
        super(itr);
      }
      private ListItr(CheckedSubList<E> parent){
        super(parent);
      }
      private ListItr(CheckedSubList<E> parent,int cursor){
        super(parent,cursor);
      }
      @Override public Object clone(){
        return new ListItr<E>(this);
      }
      @Override public boolean hasPrevious(){
        return this.cursor>parent.rootOffset;
      }
      @Override public int nextIndex(){
        return this.cursor-parent.rootOffset;
      }
      @Override public int previousIndex(){
        return this.cursor-parent.rootOffset-1;
      }
      @SuppressWarnings("unchecked")
      @Override public E previous(){
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
      @Override public void set(E val){
        final int lastRet;
        if((lastRet=this.lastRet)!=-1){
          final CheckedList<E> root;
          CheckedCollection.checkModCount(modCount,(root=this.parent.root).modCount);
          root.arr[lastRet]=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void add(E val){
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
          root.uncheckedInsert(this.cursor++,rootSize,val);
        }else{
          root.uncheckedInit(val);
          ++this.cursor;
        }
      }
    }
    @Override public OmniListIterator.OfRef<E> listIterator(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new ListItr<E>(this);
    }
    @Override public OmniListIterator.OfRef<E> listIterator(int index){
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,this.size);
      return new ListItr<E>(this,index+this.rootOffset);
    }
    @Override public boolean add(E val){
      final CheckedList<E> root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,++curr.size,curr=curr.parent){}
      if((modCount=root.size)!=0){
        root.uncheckedInsert(this.rootOffset+(this.size++),modCount,val);
      }else{
        root.uncheckedInit(val);
        ++this.size;
      }
      return true;
    }
    @Override public void add(int index,E val){
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
        root.uncheckedInsert(this.rootOffset+index,modCount,val);
      }else{
        root.uncheckedInit(val);
      }
    }
    @Override  public <T> T[] toArray(T[] arr){
      final CheckedList<E> root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,arr=OmniArray.uncheckedArrResize(size,arr),0,size);
      }else if(arr.length!=0){
        arr[0]=null;
      }
      return arr;
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      final int size;
      final T[] dst;
      final CheckedList<E> root;
      int modCount=this.modCount;
      try
      {
        dst=arrConstructor.apply(size=this.size);
      }
      finally{
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      }
      if(size!=0){
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst,0,size);
      }
      return dst;
    }
    @Override public Object[] toArray(){
      final CheckedList<E> root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0){
        final Object[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new Object[size],0,size);
        return dst;
      }
      return OmniArray.OfRef.DEFAULT_ARR;
    }
    @Override public void put(int index,E val){
      final CheckedList<E> root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      root.arr[index+this.rootOffset]=val;
    }
    @SuppressWarnings("unchecked")
    @Override public E get(int index){
      final CheckedList<E> root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      return (E)root.arr[index+this.rootOffset];
    }
    @SuppressWarnings("unchecked")
    @Override public E set(int index,E val){
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
    @Override public E remove(int index){
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
    @Override public boolean removeIf(Predicate<? super E> filter){
      int modCount=this.modCount;
      final var root=this.root;
      final int size;
      if((size=this.size)!=0){
        try
        {
          final Object[] arr;
          final int numRemoved;
          int rootOffset;
          if((numRemoved=uncheckedRemoveIfImpl(arr=root.arr,rootOffset=this.rootOffset,rootOffset+=size,filter,root.new ModCountChecker(modCount)))!=0){
            root.modCount=++modCount;
            this.modCount=modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,curr.size-=numRemoved,curr=curr.parent){}
            root.size=OmniArray.OfRef.removeRangeAndPullDown(arr,rootOffset,root.size,numRemoved);
            this.size=size-numRemoved;
            return true;
          }
        }
        catch(ConcurrentModificationException e){
          throw e;
        }catch(RuntimeException e){
          throw CheckedCollection.checkModCount(modCount,root.modCount,e);
        }
      }
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public void replaceAll(UnaryOperator<E> operator){
      final int size;
      if((size=this.size)==0){
        CheckedCollection.checkModCount(modCount,root.modCount);
        return;
      }
      int modCount=this.modCount;
      final var root=this.root;
      try{
        final int rootOffset;
        OmniArray.OfRef.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);  
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
      }
    }
    @Override
    public void sort(Comparator<? super E> sorter)
    {
      final int size;
      if((size=this.size)<2){
        CheckedCollection.checkModCount(modCount,root.modCount);
        return;
      }
      int modCount=this.modCount;
      final var root=this.root;
      try{
        final int rootOffset=this.rootOffset;
        if(sorter==null){
          RefSortUtil.uncheckedStableAscendingSort(root.arr,rootOffset,rootOffset+size);
        }else{
          RefSortUtil.uncheckedStableSort(root.arr,rootOffset,rootOffset+size,sorter);
        }
      }catch(ArrayIndexOutOfBoundsException e){
        throw new IllegalArgumentException("Comparison method violates its general contract!",e);
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
      }
    }
    @Override
    public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)<2){
        CheckedCollection.checkModCount(modCount,root.modCount);
        return;
      }
      int modCount=this.modCount;
      final var root=this.root;
      try{
        final int rootOffset;
        RefSortUtil.uncheckedStableAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }catch(ArrayIndexOutOfBoundsException e){
        throw new IllegalArgumentException("Comparison method violates its general contract!",e);
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}  
      }
    }
    @Override
    public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)<2){
        CheckedCollection.checkModCount(modCount,root.modCount);
        return;
      }
      int modCount=this.modCount;
      final var root=this.root;
      try{
        final int rootOffset;
        RefSortUtil.uncheckedStableDescendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }catch(ArrayIndexOutOfBoundsException e){
        throw new IllegalArgumentException("Comparison method violates its general contract!",e);
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}  
      }
    }
    @Override
    public void unstableSort(Comparator<? super E> sorter)
    {
      final int size;
      if((size=this.size)<2){
        CheckedCollection.checkModCount(modCount,root.modCount);
        return;
      }
      int modCount=this.modCount;
      final var root=this.root;
      try{
        final int rootOffset=this.rootOffset;
        if(sorter==null){
          RefSortUtil.uncheckedUnstableAscendingSort(root.arr,rootOffset,rootOffset+size);
        }else{
          RefSortUtil.uncheckedUnstableSort(root.arr,rootOffset,rootOffset+size,sorter);
        }
      }catch(ArrayIndexOutOfBoundsException e){
        throw new IllegalArgumentException("Comparison method violates its general contract!",e);
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
      }
    }
    @Override
    public void unstableAscendingSort()
    {
      final int size;
      if((size=this.size)<2){
        CheckedCollection.checkModCount(modCount,root.modCount);
        return;
      }
      int modCount=this.modCount;
      final var root=this.root;
      try{
        final int rootOffset;
        RefSortUtil.uncheckedUnstableAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }catch(ArrayIndexOutOfBoundsException e){
        throw new IllegalArgumentException("Comparison method violates its general contract!",e);
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}  
      }
    }
    @Override
    public void unstableDescendingSort()
    {
      final int size;
      if((size=this.size)<2){
        CheckedCollection.checkModCount(modCount,root.modCount);
        return;
      }
      int modCount=this.modCount;
      final var root=this.root;
      try{
        final int rootOffset;
        RefSortUtil.uncheckedUnstableDescendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }catch(ArrayIndexOutOfBoundsException e){
        throw new IllegalArgumentException("Comparison method violates its general contract!",e);
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}  
      }
    }
    @Override public OmniList.OfRef<E> subList(int fromIndex,int toIndex){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new CheckedSubList<E>(this,this.rootOffset+fromIndex,CheckedCollection.checkSubListRange(fromIndex,toIndex,this.size));
    }
  }
}
