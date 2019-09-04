package omni.impl.seq;
import java.util.List;
import omni.api.OmniCollection;
import omni.util.OmniArray;
import omni.api.OmniList;
import omni.api.OmniStack;
import java.util.function.Predicate;
import java.util.function.Consumer;
import java.util.Comparator;
import omni.util.ArrCopy;
import omni.util.DoubleSortUtil;
import omni.impl.CheckedCollection;
import java.util.NoSuchElementException;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import java.util.ListIterator;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;
import omni.impl.RefDblLnkNode;
import omni.impl.DoubleDblLnkNode;
import omni.util.TypeUtil;
import java.util.ConcurrentModificationException;
import java.util.function.DoubleUnaryOperator;
import omni.function.DoubleComparator;
import java.util.function.DoublePredicate;
import java.util.function.DoubleConsumer;
import omni.impl.AbstractDoubleItr;
import java.io.Externalizable;
import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectInput;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.RandomAccess;
public abstract class DoubleArrSeq extends 
AbstractSeq<Double>
 implements OmniCollection.OfDouble,Externalizable,RandomAccess{
  //TODO refactor the template and/or optimize code generation to make sure that the code generation doesn't take forever
  private static final long serialVersionUID=1L;
  transient double[] arr; 
  private DoubleArrSeq(){
    super();
    this.arr=OmniArray.OfDouble.DEFAULT_ARR;
  }
  private DoubleArrSeq(int initialCapacity){
    super();
    switch(initialCapacity){ 
    default:
      this.arr=new double[initialCapacity];
      return;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfDouble.DEFAULT_ARR;
    case 0:
    }
  }
  private DoubleArrSeq(int size,double[] arr){
    super(size);
    this.arr=arr;
  }
  @Override public void writeExternal(ObjectOutput out) throws IOException{
    int size;
    out.writeInt(size=this.size);
    if(size!=0)
    {
      OmniArray.OfDouble.writeArray(arr,0,size-1,out);
    }
  }
  @Override public void readExternal(ObjectInput in) throws IOException
  {
    int size;
    this.size=size=in.readInt();
    if(size!=0){
      double[] arr;
      OmniArray.OfDouble.readArray(arr=new double[size],0,size-1,in);
      this.arr=arr;
    }else{
      this.arr=OmniArray.OfDouble.DEFAULT_ARR;
    }
  }
  public double popDouble(){
    return (double)arr[--this.size];
  }
  static  long markSurvivors(double[] arr,int srcOffset,int srcBound,DoublePredicate filter){
    for(long word=0L,marker=1L;;marker<<=1){
      if(!filter.test((double)arr[srcOffset])){
        word|=marker;
      }
      if(++srcOffset==srcBound){
        return word;
      }
    }
  }
  static  int markSurvivors(double[] arr,int srcOffset,int srcBound,DoublePredicate filter,long[] survivorSet){
    for(int numSurvivors=0,wordOffset=0;;){
      long word=0L,marker=1L;
      do{
        if(!filter.test((double)arr[srcOffset])){
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
  static void pullSurvivorsDown(double[] arr,int srcOffset,int dstOffset,long word){
    int numTail0s=Long.numberOfTrailingZeros(word);
    do{
      ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset+=numTail0s,numTail0s=Long.numberOfTrailingZeros(~(word>>>=numTail0s)));
      srcOffset+=numTail0s;
      dstOffset+=numTail0s;
    }while((numTail0s=Long.numberOfTrailingZeros(word>>>=numTail0s))!=64);
  }
  static void pullSurvivorsDown(double[] arr,int srcOffset,int dstOffset,int dstBound,long[] survivorSet){
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
    this.size=0;
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
          if(val){
            return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,TypeUtil.DBL_TRUE_BITS);
          }
          return OmniArray.OfDouble.uncheckedcontains0(this.arr,0,size-1);
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
          if(val!=0){
            {
              return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,Double.doubleToRawLongBits(val));
            }
          }else{
            return OmniArray.OfDouble.uncheckedcontains0(this.arr,0,size-1);
          }
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
          if(val!=0){
            if(TypeUtil.checkCastToDouble(val)){
              return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,Double.doubleToRawLongBits(val));
            }
          }else{
            return OmniArray.OfDouble.uncheckedcontains0(this.arr,0,size-1);
          }
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
          if(val==val){
            return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,Double.doubleToRawLongBits(val));
          }
          return OmniArray.OfDouble.uncheckedcontainsNaN(this.arr,0,size-1);
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
          if(val==val){
            return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,Double.doubleToRawLongBits(val));
          }
          return OmniArray.OfDouble.uncheckedcontainsNaN(this.arr,0,size-1);
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
          //TODO a pattern-matching switch statement would be great here
          returnFalse:for(;;){
            if(val instanceof Double){
              final double d;
              if((d=(double)val)==d){
                 return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,Double.doubleToRawLongBits(d));
              }
              return OmniArray.OfDouble.uncheckedcontainsNaN(this.arr,0,size-1);
            }else if(val instanceof Float){
              final float f;
              if((f=(float)val)==f){
                 return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,Double.doubleToRawLongBits(f));
              }
              return OmniArray.OfDouble.uncheckedcontainsNaN(this.arr,0,size-1);
            }else if(val instanceof Integer|| val instanceof Short||val instanceof Byte){
              final int i;
              if((i=((Number)val).intValue())!=0){
                return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,Double.doubleToRawLongBits(i));
              }
              return OmniArray.OfDouble.uncheckedcontains0(this.arr,0,size-1);
            }else if(val instanceof Long){
              final long l;
              if((l=(long)val)!=0){
                if(!TypeUtil.checkCastToDouble(l)){
                  break returnFalse;
                }
                return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,Double.doubleToRawLongBits(l));
              }
              return OmniArray.OfDouble.uncheckedcontains0(this.arr,0,size-1);
            }else if(val instanceof Character){
              final int i;
              if((i=(char)val)!=0){
                return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,Double.doubleToRawLongBits(i));
              }
              return OmniArray.OfDouble.uncheckedcontains0(this.arr,0,size-1);
            }else if(val instanceof Boolean){
              if((boolean)val){
                return OmniArray.OfDouble.uncheckedcontainsBits(this.arr,0,size-1,TypeUtil.DBL_TRUE_BITS);
              }
              return OmniArray.OfDouble.uncheckedcontains0(this.arr,0,size-1);
            }else{
              break returnFalse;
            }
          }
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
          if(val){
            return this.uncheckedremoveValBits(size,TypeUtil.DBL_TRUE_BITS);
          }
          return this.uncheckedremoveVal0(size);
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
          if(val!=0){
            {
              return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
            }
          }else{
            return this.uncheckedremoveVal0(size);
          }
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
          if(val!=0){
            if(TypeUtil.checkCastToDouble(val)){
              return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
            }
          }else{
            return this.uncheckedremoveVal0(size);
          }
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
          if(val==val){
            return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
          }
          return this.uncheckedremoveValNaN(size);
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
          if(val==val){
            return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
          }
          return this.uncheckedremoveValNaN(size);
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
          //TODO a pattern-matching switch statement would be great here
          returnFalse:for(;;){
            if(val instanceof Double){
              final double d;
              if((d=(double)val)==d){
                 return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(d));
              }
              return this.uncheckedremoveValNaN(size);
            }else if(val instanceof Float){
              final float f;
              if((f=(float)val)==f){
                 return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(f));
              }
              return this.uncheckedremoveValNaN(size);
            }else if(val instanceof Integer|| val instanceof Short||val instanceof Byte){
              final int i;
              if((i=((Number)val).intValue())!=0){
                return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(i));
              }
              return this.uncheckedremoveVal0(size);
            }else if(val instanceof Long){
              final long l;
              if((l=(long)val)!=0){
                if(!TypeUtil.checkCastToDouble(l)){
                  break returnFalse;
                }
                return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(l));
              }
              return this.uncheckedremoveVal0(size);
            }else if(val instanceof Character){
              final int i;
              if((i=(char)val)!=0){
                return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(i));
              }
              return this.uncheckedremoveVal0(size);
            }else if(val instanceof Boolean){
              if((boolean)val){
                return this.uncheckedremoveValBits(size,TypeUtil.DBL_TRUE_BITS);
              }
              return this.uncheckedremoveVal0(size);
            }else{
              break returnFalse;
            }
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  abstract boolean uncheckedremoveValBits(int size,long bits);
  abstract boolean uncheckedremoveVal0(int size);
  abstract boolean uncheckedremoveValNaN(int size);
  abstract void uncheckedForEach(int size,DoubleConsumer action);
  @Override public void forEach(DoubleConsumer action){
    final int size;
    if((size=this.size)!=0){
      uncheckedForEach(size,action);
    }
  }
  @Override public void forEach(Consumer<? super Double> action){
    final int size;
    if((size=this.size)!=0){
      uncheckedForEach(size,action::accept);
    }
  }
  public void push(double val){
    final int size;
    if((size=this.size)!=0){
      uncheckedAppend(size,val);
    }else{
      uncheckedInit(val);
    }
  }
  @Override public boolean add(double val){
    push(val);
    return true;
  }
  @Override public boolean add(Double val){
    push((double)val);
    return true;
  }
  @Override public boolean add(boolean val){
    push((double)TypeUtil.castToDouble(val));
    return true;
  }
  @Override public boolean add(int val){
    push((double)val);
    return true;
  }
  @Override public boolean add(long val){
    push((double)val);
    return true;
  }
  @Override public boolean add(float val){
    push((double)val);
    return true;
  }
  abstract void uncheckedCopyInto(double[] dst,int length);
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
  @Override public double[] toDoubleArray(){
    final int size;
    if((size=this.size)!=0){
      final double[] dst;
      uncheckedCopyInto(dst=new double[size],size);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  abstract void uncheckedCopyInto(Object[] dst,int length);
  abstract void uncheckedCopyInto(Double[] dst,int length);
  @Override public Double[] toArray(){
    final int size;
    if((size=this.size)!=0){
      final Double[] dst;
      uncheckedCopyInto(dst=new Double[size],size);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_BOXED_ARR;
  }
  boolean uncheckedRemoveIf(int size,DoublePredicate filter){
    if(size!=(size-=uncheckedRemoveIfImpl(this.arr,0,size,filter))){
      this.size=size;
      return true;
    }
    return false;
  }
  @Override public boolean removeIf(DoublePredicate filter){
    final int size;
    return (size=this.size)!=0 && uncheckedRemoveIf(size,filter);
  }
  @Override public boolean removeIf(Predicate<? super Double> filter){
    final int size;
    return (size=this.size)!=0 && uncheckedRemoveIf(size,filter::test);
  }
  private
  void uncheckedAppend(int size,double val){
    double[] arr;
    if((arr=this.arr).length==size){
      ArrCopy.uncheckedCopy(arr,0,arr=new double[OmniArray.growBy50Pct(size)],0,size);
      this.arr=arr;
    }
    arr[size]=val;
    this.size=size+1;
  }
  private
  void uncheckedInit(double val){
    double[] arr;
    if((arr=this.arr)==null){
      this.arr=new double[]{val};
    }else{
      if(arr==OmniArray.OfDouble.DEFAULT_ARR){
        this.arr=arr=new double[OmniArray.DEFAULT_ARR_SEQ_CAP];
      }
      arr[0]=val;
    }
    this.size=1;
  }
  private static  int pullSurvivorsDown(double[] arr,int srcOffset,int srcBound,int dstOffset,DoublePredicate filter){
    while(++srcOffset!=srcBound){
      final double v;
      if(!filter.test((double)(v=arr[srcOffset])))
      {
        arr[dstOffset++]=v;
      }
    }
    return srcBound-dstOffset;
  }
  private static  int uncheckedRemoveIfImpl(double[] arr,int srcOffset,int srcBound,DoublePredicate filter){
    do{
      if(filter.test((double)arr[srcOffset])){
        return pullSurvivorsDown(arr,srcOffset,srcBound,srcOffset,filter);
      }
    }while(++srcOffset!=srcBound);
    return 0;
  }
  private static  int uncheckedRemoveIfImpl(double[] arr,int srcOffset,int srcBound,DoublePredicate filter,CheckedCollection.AbstractModCountChecker modCountChecker){
    do{
      if(filter.test((double)arr[srcOffset])){
        int dstOffset=srcOffset;
        outer:for(;;){
          if(++srcOffset==srcBound){
            modCountChecker.checkModCount();
            break outer;
          }
          double before;
          if(!filter.test((double)(before=arr[srcOffset]))){
            for(int i=srcBound-1;;--i){
              if(i==srcOffset){
                modCountChecker.checkModCount();
                arr[dstOffset++]=before;
                break outer;
              }
              double after;
              if(!filter.test((double)(after=arr[i]))){
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
    static class UncheckedStack
      extends DoubleArrSeq
      implements OmniStack.OfDouble,Cloneable,RandomAccess
  {
    private static final long serialVersionUID=1L;
    public UncheckedStack(){
      super();
    }
    public UncheckedStack(int initialCapacity){
      super(initialCapacity);
    }
    UncheckedStack(int size,double[] arr){
      super(size,arr);
    }
    @Override public Object clone(){
      final double[] copy;
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(this.arr,0,copy=new double[size],0,size);
      }else{
        copy=OmniArray.OfDouble.DEFAULT_ARR;
      }
      return new UncheckedStack(size,copy);
    }
    @Override void uncheckedToString(int size,StringBuilder builder){
      OmniArray.OfDouble.descendingToString(this.arr,0,size-1,builder);
    }
    @Override public int search(boolean val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val){
              return OmniArray.OfDouble.uncheckedsearchBits(this.arr,size,TypeUtil.DBL_TRUE_BITS);
            }
            return OmniArray.OfDouble.uncheckedsearch0(this.arr,size);
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
            if(val!=0){
              {
                return OmniArray.OfDouble.uncheckedsearchBits(this.arr,size,Double.doubleToRawLongBits(val));
              }
            }else{
              return OmniArray.OfDouble.uncheckedsearch0(this.arr,size);
            }
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
            if(val!=0){
              if(TypeUtil.checkCastToDouble(val)){
                return OmniArray.OfDouble.uncheckedsearchBits(this.arr,size,Double.doubleToRawLongBits(val));
              }
            }else{
              return OmniArray.OfDouble.uncheckedsearch0(this.arr,size);
            }
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
            if(val==val){
              return OmniArray.OfDouble.uncheckedsearchBits(this.arr,size,Double.doubleToRawLongBits(val));
            }
            return OmniArray.OfDouble.uncheckedsearchNaN(this.arr,size);
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
            if(val==val){
              return OmniArray.OfDouble.uncheckedsearchBits(this.arr,size,Double.doubleToRawLongBits(val));
            }
            return OmniArray.OfDouble.uncheckedsearchNaN(this.arr,size);
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
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              if(val instanceof Double){
                final double d;
                if((d=(double)val)==d){
                   return OmniArray.OfDouble.uncheckedsearchBits(this.arr,size,Double.doubleToRawLongBits(d));
                }
                return OmniArray.OfDouble.uncheckedsearchNaN(this.arr,size);
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return OmniArray.OfDouble.uncheckedsearchBits(this.arr,size,Double.doubleToRawLongBits(f));
                }
                return OmniArray.OfDouble.uncheckedsearchNaN(this.arr,size);
              }else if(val instanceof Integer|| val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).intValue())!=0){
                  return OmniArray.OfDouble.uncheckedsearchBits(this.arr,size,Double.doubleToRawLongBits(i));
                }
                return OmniArray.OfDouble.uncheckedsearch0(this.arr,size);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToDouble(l)){
                    break returnFalse;
                  }
                  return OmniArray.OfDouble.uncheckedsearchBits(this.arr,size,Double.doubleToRawLongBits(l));
                }
                return OmniArray.OfDouble.uncheckedsearch0(this.arr,size);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return OmniArray.OfDouble.uncheckedsearchBits(this.arr,size,Double.doubleToRawLongBits(i));
                }
                return OmniArray.OfDouble.uncheckedsearch0(this.arr,size);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return OmniArray.OfDouble.uncheckedsearchBits(this.arr,size,TypeUtil.DBL_TRUE_BITS);
                }
                return OmniArray.OfDouble.uncheckedsearch0(this.arr,size);
              }else{
                break returnFalse;
              }
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override boolean uncheckedremoveValBits(int size
    ,long bits
    ){
      final var arr=this.arr;
      for(int index=--size;;--index){
        if(
        bits==Double.doubleToRawLongBits(arr[index])
        )
        {
          OmniArray.OfDouble.removeIndexAndPullDown(arr,index,size);
          this.size=size;
          return true;
        }else if(index==0){
          return false;
        }
      }
    }
    @Override boolean uncheckedremoveValNaN(int size
    ){
      final var arr=this.arr;
      for(int index=--size;;--index){
        if(
        Double.isNaN(arr[index])
        )
        {
          OmniArray.OfDouble.removeIndexAndPullDown(arr,index,size);
          this.size=size;
          return true;
        }else if(index==0){
          return false;
        }
      }
    }
    @Override boolean uncheckedremoveVal0(int size
    ){
      final var arr=this.arr;
      for(int index=--size;;--index){
        if(
        arr[index]==0
        )
        {
          OmniArray.OfDouble.removeIndexAndPullDown(arr,index,size);
          this.size=size;
          return true;
        }else if(index==0){
          return false;
        }
      }
    }
    @Override void uncheckedForEach(int size,DoubleConsumer action){
      {
        OmniArray.OfDouble.descendingForEach(this.arr,0,size-1,action);
      }
    }
    private static class Itr
      extends AbstractDoubleItr
    {
      transient final UncheckedStack parent;
      transient int cursor;
      private Itr(Itr itr){
        this.parent=itr.parent;
        this.cursor=itr.cursor;
      }
      private Itr(UncheckedStack parent){
        this.parent=parent;
        this.cursor=parent.size;
      }
      @Override public Object clone(){
        return new Itr(this);
      }
      @Override public boolean hasNext(){
        return this.cursor>0;
      }
      @Override public double nextDouble(){
        return (double)parent.arr[--cursor];
      }
      @Override public void remove(){
        final UncheckedStack root;
        OmniArray.OfDouble.removeIndexAndPullDown((root=this.parent).arr,this.cursor,--root.size);
      }
      @Override public void forEachRemaining(DoubleConsumer action){
        final int cursor;
        if((cursor=this.cursor)>0){
          OmniArray.OfDouble.descendingForEach(parent.arr,0,cursor-1,action);
          this.cursor=0;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Double> action){
        final int cursor;
        if((cursor=this.cursor)>0){
          OmniArray.OfDouble.descendingForEach(parent.arr,0,cursor-1,action::accept);
          this.cursor=0;
        }
      }
    }
    @Override public OmniIterator.OfDouble iterator(){
      return new Itr(this);
    }
    @Override public void push(Double val){
      push((double)val);
    }
    @Override void uncheckedCopyInto(double[] dst,int length){
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override void uncheckedCopyInto(Object[] dst,int length){
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override void uncheckedCopyInto(Double[] dst,int length){
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override public Double pop(){
      return popDouble();
    }
    @Override public double pollDouble(){
      int size;
      if((size=this.size)!=0){
        final var ret=(double)(arr[--size]);
        this.size=size;
        return ret;
      }
      return Double.NaN;
    }
    @Override public double peekDouble(){
      final int size;
      if((size=this.size)!=0){
        return (double)(arr[size-1]);
      }
      return Double.NaN;
    }
    @Override public Double poll(){
      int size;
      if((size=this.size)!=0){
        final var ret=(Double)(arr[--size]);
        this.size=size;
        return ret;
      }
      return null;
    }
    @Override public Double peek(){
      final int size;
      if((size=this.size)!=0){
        return (Double)(arr[size-1]);
      }
      return null;
    }
  }
  public
    static class UncheckedList
      extends DoubleArrSeq
      implements DoubleListDefault,Cloneable,RandomAccess
  {
    private static final long serialVersionUID=1L;
    public UncheckedList(){
      super();
    }
    public UncheckedList(int initialCapacity){
      super(initialCapacity);
    }
    UncheckedList(int size,double[] arr){
      super(size,arr);
    }
    private boolean isEqualTo(ListIterator<?> itr,int thisOffset){
      if(itr.hasNext()){
        Object val;
        if((val=itr.next()) instanceof Double){
          for(final var arr=this.arr;TypeUtil.doubleEquals((double)val,arr[thisOffset]);++thisOffset){
            if(!itr.hasNext()){
              return true;
            }
            if(!((val=itr.next()) instanceof Double)){
              break;
            }
          }
        }
      }
      return false;
    }
    private boolean isEqualToHelper(int thisOffset,int thisBound,double[] thatArr,int thatOffset){
      for(final var thisArr=this.arr;TypeUtil.doubleEquals(thisArr[thisOffset],thatArr[thatOffset]);++thatOffset){
        if(++thisOffset==thisBound){
          return true;
        }  
      }
      return false;
    }
    private boolean isEqualTo(int rootOffset,int bound,AbstractSeq<?> list){
      if(list instanceof DoubleArrSeq.UncheckedList){
        return this.isEqualToHelper(rootOffset,bound,((DoubleArrSeq.UncheckedList)list).arr,0);
      }else if(list instanceof DoubleArrSeq.UncheckedSubList){
        final DoubleArrSeq.UncheckedSubList subList;
        final int thatOffset=(subList=(DoubleArrSeq.UncheckedSubList)list).rootOffset;
        final DoubleArrSeq.UncheckedList thatRoot;
        return ((thatRoot=subList.root)==this && thatOffset==rootOffset) || this.isEqualToHelper(rootOffset,bound,thatRoot.arr,thatOffset);
      }else if(list instanceof DoubleArrSeq.CheckedSubList){
        final DoubleArrSeq.CheckedSubList subList;
        final DoubleArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount(((subList=(DoubleArrSeq.CheckedSubList)list)).modCount,(thatRoot=subList.root).modCount);
        return this.isEqualToHelper(rootOffset,bound,thatRoot.arr,subList.rootOffset);
      }else if(list instanceof DoubleDblLnkSeq){
        final DoubleDblLnkNode head;
        if(list instanceof DoubleDblLnkSeq.CheckedSubList){
          final DoubleDblLnkSeq.CheckedSubList subList;
          CheckedCollection.checkModCount((subList=(DoubleDblLnkSeq.CheckedSubList)list).modCount,subList.root.modCount);
          head=subList.head;
        }else{
          head=((DoubleDblLnkSeq)list).head;
        }
        return SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,bound,head);
      }else if(list instanceof RefArrSeq.UncheckedList){
        return SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,bound,((RefArrSeq.UncheckedList<?>)list).arr,0);
      }else if(list instanceof RefArrSeq.UncheckedSubList){
        final RefArrSeq.UncheckedSubList<?> subList;
        return SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,bound,(subList=(RefArrSeq.UncheckedSubList<?>)list).root.arr,subList.rootOffset);
      }else if(list instanceof RefArrSeq.CheckedSubList){
        final RefArrSeq.CheckedSubList<?> subList;
        final RefArrSeq.CheckedList<?> thatRoot;
        CheckedCollection.checkModCount((subList=(RefArrSeq.CheckedSubList<?>)list).modCount,(thatRoot=subList.root).modCount);
        return SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,bound,thatRoot.arr,subList.rootOffset);
      }else{
        //must be RefDblLnkSeq
        final RefDblLnkNode<?> head;
        if(list instanceof RefDblLnkSeq.CheckedSubList){
          final RefDblLnkSeq.CheckedSubList<?> subList;
          CheckedCollection.checkModCount((subList=(RefDblLnkSeq.CheckedSubList<?>)list).modCount,subList.root.modCount);
          head=subList.head;
        }else{
          head=((RefDblLnkSeq<?>)list).head;
        }
        return SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,bound,head);
      }
    }
    private boolean isEqualTo(int size,AbstractSeq<?> list){
      if(list instanceof DoubleArrSeq.UncheckedList){
        return this.isEqualToHelper(0,size,((DoubleArrSeq.UncheckedList)list).arr,0);
      }else if(list instanceof DoubleDblLnkSeq){
        final DoubleDblLnkNode head;
        if(list instanceof DoubleDblLnkSeq.CheckedSubList){
          final DoubleDblLnkSeq.CheckedSubList subList;
          CheckedCollection.checkModCount((subList=(DoubleDblLnkSeq.CheckedSubList)list).modCount,subList.root.modCount);
          head=subList.head;
        }else{
          head=((DoubleDblLnkSeq)list).head;
        }
        return SequenceEqualityUtil.isEqualTo(this.arr,0,size,head);
      }else if(list instanceof RefArrSeq.UncheckedList){
        return SequenceEqualityUtil.isEqualTo(this.arr,0,size,((RefArrSeq.UncheckedList<?>)list).arr,0);
      }else if(list instanceof RefDblLnkSeq){
        final RefDblLnkNode<?> head;
        if(list instanceof RefDblLnkSeq.CheckedSubList){
          final RefDblLnkSeq.CheckedSubList<?> subList;
          CheckedCollection.checkModCount((subList=(RefDblLnkSeq.CheckedSubList<?>)list).modCount,subList.root.modCount);
          head=subList.head;
        }else{
          head=((RefDblLnkSeq<?>)list).head;
        }
        return SequenceEqualityUtil.isEqualTo(this.arr,0,size,head);
      }else if(list instanceof DoubleArrSeq.UncheckedSubList){
        final DoubleArrSeq.UncheckedSubList subList;
        final DoubleArrSeq.UncheckedList thatRoot;
        return (thatRoot=(subList=(DoubleArrSeq.UncheckedSubList)list).root)==this || this.isEqualToHelper(0,size,thatRoot.arr,subList.rootOffset);
      }else if(list instanceof DoubleArrSeq.CheckedSubList){
        final DoubleArrSeq.CheckedSubList subList;
        final DoubleArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount((subList=(DoubleArrSeq.CheckedSubList)list).modCount,(thatRoot=subList.root).modCount);
        return this.isEqualToHelper(0,size,thatRoot.arr,subList.rootOffset);
      }else if(list instanceof RefArrSeq.UncheckedSubList){
        final RefArrSeq.UncheckedSubList<?> subList;
        return SequenceEqualityUtil.isEqualTo(this.arr,0,size,(subList=(RefArrSeq.UncheckedSubList<?>)list).root.arr,subList.rootOffset);
      }else{
        //must be RefArrSeq.CheckedSubList
        final RefArrSeq.CheckedSubList<?> subList;
        final RefArrSeq.CheckedList<?> thatRoot;
        CheckedCollection.checkModCount((subList=(RefArrSeq.CheckedSubList<?>)list).modCount,(thatRoot=subList.root).modCount);
        return SequenceEqualityUtil.isEqualTo(this.arr,0,size,thatRoot.arr,subList.rootOffset);
      }
    }
    @Override public boolean equals(Object val){
      if(val==this){
        return true;
      }
      if(val instanceof List){
        final List<?> list;
        if((list=(List<?>)val) instanceof AbstractSeq){
          final AbstractSeq<?> abstractSeq;
          if((abstractSeq=(AbstractSeq<?>)list).size==size){
            return this.isEqualTo(size,abstractSeq);
          }
        }else{
          return this.size==list.size() && this.isEqualTo(list.listIterator(),0);
        }
      }
      return false;
    }
    @Override public Object clone(){
      final double[] copy;
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(this.arr,0,copy=new double[size],0,size);
      }else{
        copy=OmniArray.OfDouble.DEFAULT_ARR;
      }
      return new UncheckedList(size,copy);
    }
    @Override void uncheckedToString(int size,StringBuilder builder){
      OmniArray.OfDouble.ascendingToString(this.arr,0,size-1,builder);
    }
    @Override public int hashCode(){
      final int size;
      if((size=this.size)!=0){
        return OmniArray.OfDouble.ascendingSeqHashCode(this.arr,0,size-1);
      }
      return 1;
    }
    @Override public int indexOf(boolean val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val){
              return OmniArray.OfDouble.uncheckedindexOfBits(this.arr,size,TypeUtil.DBL_TRUE_BITS);
            }
            return OmniArray.OfDouble.uncheckedindexOf0(this.arr,size);
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
            if(val!=0){
              {
                return OmniArray.OfDouble.uncheckedindexOfBits(this.arr,size,Double.doubleToRawLongBits(val));
              }
            }else{
              return OmniArray.OfDouble.uncheckedindexOf0(this.arr,size);
            }
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
            if(val!=0){
              if(TypeUtil.checkCastToDouble(val)){
                return OmniArray.OfDouble.uncheckedindexOfBits(this.arr,size,Double.doubleToRawLongBits(val));
              }
            }else{
              return OmniArray.OfDouble.uncheckedindexOf0(this.arr,size);
            }
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
            if(val==val){
              return OmniArray.OfDouble.uncheckedindexOfBits(this.arr,size,Double.doubleToRawLongBits(val));
            }
            return OmniArray.OfDouble.uncheckedindexOfNaN(this.arr,size);
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
            if(val==val){
              return OmniArray.OfDouble.uncheckedindexOfBits(this.arr,size,Double.doubleToRawLongBits(val));
            }
            return OmniArray.OfDouble.uncheckedindexOfNaN(this.arr,size);
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
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              if(val instanceof Double){
                final double d;
                if((d=(double)val)==d){
                   return OmniArray.OfDouble.uncheckedindexOfBits(this.arr,size,Double.doubleToRawLongBits(d));
                }
                return OmniArray.OfDouble.uncheckedindexOfNaN(this.arr,size);
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return OmniArray.OfDouble.uncheckedindexOfBits(this.arr,size,Double.doubleToRawLongBits(f));
                }
                return OmniArray.OfDouble.uncheckedindexOfNaN(this.arr,size);
              }else if(val instanceof Integer|| val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).intValue())!=0){
                  return OmniArray.OfDouble.uncheckedindexOfBits(this.arr,size,Double.doubleToRawLongBits(i));
                }
                return OmniArray.OfDouble.uncheckedindexOf0(this.arr,size);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToDouble(l)){
                    break returnFalse;
                  }
                  return OmniArray.OfDouble.uncheckedindexOfBits(this.arr,size,Double.doubleToRawLongBits(l));
                }
                return OmniArray.OfDouble.uncheckedindexOf0(this.arr,size);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return OmniArray.OfDouble.uncheckedindexOfBits(this.arr,size,Double.doubleToRawLongBits(i));
                }
                return OmniArray.OfDouble.uncheckedindexOf0(this.arr,size);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return OmniArray.OfDouble.uncheckedindexOfBits(this.arr,size,TypeUtil.DBL_TRUE_BITS);
                }
                return OmniArray.OfDouble.uncheckedindexOf0(this.arr,size);
              }else{
                break returnFalse;
              }
            }
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
            if(val){
              return OmniArray.OfDouble.uncheckedlastIndexOfBits(this.arr,size,TypeUtil.DBL_TRUE_BITS);
            }
            return OmniArray.OfDouble.uncheckedlastIndexOf0(this.arr,size);
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
            if(val!=0){
              {
                return OmniArray.OfDouble.uncheckedlastIndexOfBits(this.arr,size,Double.doubleToRawLongBits(val));
              }
            }else{
              return OmniArray.OfDouble.uncheckedlastIndexOf0(this.arr,size);
            }
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
            if(val!=0){
              if(TypeUtil.checkCastToDouble(val)){
                return OmniArray.OfDouble.uncheckedlastIndexOfBits(this.arr,size,Double.doubleToRawLongBits(val));
              }
            }else{
              return OmniArray.OfDouble.uncheckedlastIndexOf0(this.arr,size);
            }
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
            if(val==val){
              return OmniArray.OfDouble.uncheckedlastIndexOfBits(this.arr,size,Double.doubleToRawLongBits(val));
            }
            return OmniArray.OfDouble.uncheckedlastIndexOfNaN(this.arr,size);
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
            if(val==val){
              return OmniArray.OfDouble.uncheckedlastIndexOfBits(this.arr,size,Double.doubleToRawLongBits(val));
            }
            return OmniArray.OfDouble.uncheckedlastIndexOfNaN(this.arr,size);
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
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              if(val instanceof Double){
                final double d;
                if((d=(double)val)==d){
                   return OmniArray.OfDouble.uncheckedlastIndexOfBits(this.arr,size,Double.doubleToRawLongBits(d));
                }
                return OmniArray.OfDouble.uncheckedlastIndexOfNaN(this.arr,size);
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return OmniArray.OfDouble.uncheckedlastIndexOfBits(this.arr,size,Double.doubleToRawLongBits(f));
                }
                return OmniArray.OfDouble.uncheckedlastIndexOfNaN(this.arr,size);
              }else if(val instanceof Integer|| val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).intValue())!=0){
                  return OmniArray.OfDouble.uncheckedlastIndexOfBits(this.arr,size,Double.doubleToRawLongBits(i));
                }
                return OmniArray.OfDouble.uncheckedlastIndexOf0(this.arr,size);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToDouble(l)){
                    break returnFalse;
                  }
                  return OmniArray.OfDouble.uncheckedlastIndexOfBits(this.arr,size,Double.doubleToRawLongBits(l));
                }
                return OmniArray.OfDouble.uncheckedlastIndexOf0(this.arr,size);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return OmniArray.OfDouble.uncheckedlastIndexOfBits(this.arr,size,Double.doubleToRawLongBits(i));
                }
                return OmniArray.OfDouble.uncheckedlastIndexOf0(this.arr,size);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return OmniArray.OfDouble.uncheckedlastIndexOfBits(this.arr,size,TypeUtil.DBL_TRUE_BITS);
                }
                return OmniArray.OfDouble.uncheckedlastIndexOf0(this.arr,size);
              }else{
                break returnFalse;
              }
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override boolean uncheckedremoveValBits(int size
    ,long bits
    ){
      final var arr=this.arr;
      for(int index=0;;){
        if(
        bits==Double.doubleToRawLongBits(arr[index])
        )
        {
          OmniArray.OfDouble.removeIndexAndPullDown(arr,index,--size);
          this.size=size;
          return true;
        }else if(++index==size){
          return false;
        }
      }
    }
    @Override boolean uncheckedremoveValNaN(int size
    ){
      final var arr=this.arr;
      for(int index=0;;){
        if(
        Double.isNaN(arr[index])
        )
        {
          OmniArray.OfDouble.removeIndexAndPullDown(arr,index,--size);
          this.size=size;
          return true;
        }else if(++index==size){
          return false;
        }
      }
    }
    @Override boolean uncheckedremoveVal0(int size
    ){
      final var arr=this.arr;
      for(int index=0;;){
        if(
        arr[index]==0
        )
        {
          OmniArray.OfDouble.removeIndexAndPullDown(arr,index,--size);
          this.size=size;
          return true;
        }else if(++index==size){
          return false;
        }
      }
    }
    @Override void uncheckedForEach(int size,DoubleConsumer action){
      {
        OmniArray.OfDouble.ascendingForEach(this.arr,0,size-1,action);
      }
    }
    private static class Itr
      extends AbstractDoubleItr
    {
      transient final UncheckedList parent;
      transient int cursor;
      private Itr(Itr itr){
        this.parent=itr.parent;
        this.cursor=itr.cursor;
      }
      private Itr(UncheckedList parent){
        this.parent=parent;
        this.cursor=0;
      }
      private Itr(UncheckedList parent,int cursor){
        this.parent=parent;
        this.cursor=cursor;
      }
      @Override public Object clone(){
        return new Itr(this);
      }
      @Override public boolean hasNext(){
        return this.cursor<parent.size;
      }
      @Override public double nextDouble(){
        return (double)parent.arr[cursor++];
      }
      @Override public void remove(){
        final UncheckedList root;
        OmniArray.OfDouble.removeIndexAndPullDown((root=this.parent).arr,--this.cursor,--root.size);
      }
      @Override public void forEachRemaining(DoubleConsumer action){
        final int cursor,bound;
        final UncheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size)){
          OmniArray.OfDouble.ascendingForEach(parent.arr,cursor,bound-1,action);
          this.cursor=bound;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Double> action){
        final int cursor,bound;
        final UncheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size)){
          OmniArray.OfDouble.ascendingForEach(parent.arr,cursor,bound-1,action::accept);
          this.cursor=bound;
        }
      }
    }
    @Override public OmniIterator.OfDouble iterator(){
      return new Itr(this);
    }
    private static class ListItr extends Itr implements OmniListIterator.OfDouble{
      transient int lastRet;
      private ListItr(ListItr itr){
        super(itr);
        this.lastRet=itr.lastRet;
      }
      private ListItr(UncheckedList parent){
        super(parent);
      }
      private ListItr(UncheckedList parent,int cursor){
        super(parent,cursor);
      }
      @Override public Object clone(){
        return new ListItr(this);
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
      @Override public double nextDouble(){
        int lastRet;
        this.lastRet=lastRet=this.cursor++;
        return (double)parent.arr[lastRet];
      }
      @Override public void remove(){
        final UncheckedList root;
        final int lastRet;
        OmniArray.OfDouble.removeIndexAndPullDown((root=this.parent).arr,lastRet=this.lastRet,--root.size);
        this.cursor=lastRet;
      }
      @Override public void forEachRemaining(DoubleConsumer action){
        int cursor;
        final int bound;
        final UncheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size)){
          OmniArray.OfDouble.ascendingForEach(parent.arr,cursor,cursor=bound-1,action);
          this.lastRet=cursor;
          this.cursor=bound;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Double> action){
        int cursor;
        final int bound;
        final UncheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size)){
          OmniArray.OfDouble.ascendingForEach(parent.arr,cursor,cursor=bound-1,action::accept);
          this.lastRet=cursor;
          this.cursor=bound;
        }
      }
      @Override public double previousDouble(){
        final int lastRet;
        this.lastRet=lastRet=--this.cursor;
        return (double)parent.arr[lastRet];
      }
      @Override public void set(double val){
        parent.arr[this.lastRet]=val;
      }
      @Override public void add(double val){
        final UncheckedList root;
        final int rootSize;
        if((rootSize=(root=this.parent).size)!=0)
        {
          ((UncheckedList)root).uncheckedInsert(this.cursor++,rootSize,val);
        }else{
          ((DoubleArrSeq)root).uncheckedInit(val);
          ++this.cursor;
        }
      }
    }
    @Override public OmniListIterator.OfDouble listIterator(){
      return new ListItr(this);
    }
    @Override public OmniListIterator.OfDouble listIterator(int index){
      return new ListItr(this,index);
    }
      private void uncheckedInsert(int index,int size,double val){
        final int tailDist;
        if((tailDist=size-index)==0){
          super.uncheckedAppend(size,val);
        }else{
          double[] arr;
          if((arr=this.arr).length==size){
            final double[] tmp;
            ArrCopy.semicheckedCopy(arr,0,tmp=new double[OmniArray.growBy50Pct(size)],0,index);
            ArrCopy.uncheckedCopy(arr,index,tmp,index+1,tailDist);
            this.arr=arr=tmp;
          }else{
            ArrCopy.uncheckedCopy(arr,index,arr,index+1,tailDist);
          }
          arr[index]=val;
          this.size=size+1;
        }
      }
    @Override public void add(int index,double val){
      final int size;
      if((size=this.size)!=0){
        ((UncheckedList)this).uncheckedInsert(index,size,val);
      }else{
        ((DoubleArrSeq)this).uncheckedInit(val);
      }
    }
    @Override void uncheckedCopyInto(double[] dst,int length){
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override void uncheckedCopyInto(Object[] dst,int length){
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override void uncheckedCopyInto(Double[] dst,int length){
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override public void put(int index,double val){
      this.arr[index]=val;
    }
    @Override public double getDouble(int index){
      return (double)this.arr[index];
    }
    @Override public double set(int index,double val){
      final double[] arr;
      final var ret=(double)(arr=this.arr)[index];
      arr[index]=val;
      return ret;
    }
    @Override public double removeDoubleAt(int index){
      final double[] arr;
      final var ret=(double)(arr=this.arr)[index];
      OmniArray.OfDouble.removeIndexAndPullDown(arr,index,--size);
      return ret;
    }
    @Override public void replaceAll(DoubleUnaryOperator operator){
      final int size;
      if((size=this.size)!=0){
        OmniArray.OfDouble.uncheckedReplaceAll(this.arr,0,size,operator);
      }
    }
    @Override
    public void sort(DoubleComparator sorter)
    {
      final int size;
      if((size=this.size)>1){
        if(sorter==null){
          DoubleSortUtil.uncheckedAscendingSort(this.arr,0,size);
        }else{
          DoubleSortUtil.uncheckedStableSort(this.arr,0,size,sorter);
        }
      }
    }
    @Override
    public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)>1){
        DoubleSortUtil.uncheckedAscendingSort(this.arr,0,size);
      }
    }
    @Override
    public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1){
        DoubleSortUtil.uncheckedDescendingSort(this.arr,0,size);
      }
    }
    @Override public void replaceAll(UnaryOperator<Double> operator){
      final int size;
      if((size=this.size)!=0){
        OmniArray.OfDouble.uncheckedReplaceAll(this.arr,0,size,operator::apply);
      }
    }
    @Override
    public void sort(Comparator<? super Double> sorter)
    {
      final int size;
      if((size=this.size)>1){
        if(sorter==null){
          DoubleSortUtil.uncheckedAscendingSort(this.arr,0,size);
        }else{
          DoubleSortUtil.uncheckedStableSort(this.arr,0,size,sorter::compare);
        }
      }
    }
    @Override
    public void unstableSort(DoubleComparator sorter)
    {
      final int size;
      if((size=this.size)>1){
        if(sorter==null){
          DoubleSortUtil.uncheckedAscendingSort(this.arr,0,size);
        }else{
          DoubleSortUtil.uncheckedUnstableSort(this.arr,0,size,sorter);
        }
      }
    }
    @Override public OmniList.OfDouble subList(int fromIndex,int toIndex){
      return new UncheckedSubList(this,fromIndex,toIndex-fromIndex);
    }
  }
    static class UncheckedSubList
      extends AbstractSeq<Double>
      implements DoubleSubListDefault,Cloneable,RandomAccess
  {
    private static final long serialVersionUID=1L;
    transient final int rootOffset;
    transient final UncheckedList root;
    transient final UncheckedSubList parent;
    private UncheckedSubList(UncheckedList root,int rootOffset,int size){
      super(size);
      this.root=root;
      this.parent=null;
      this.rootOffset=rootOffset;
    }
    private UncheckedSubList(UncheckedSubList parent,int rootOffset,int size){
      super(size);
      this.root=parent.root;
      this.parent=parent;
      this.rootOffset=rootOffset;
    }
    @Override public boolean add(Double val){
      return add((double)val);
    }
    private static class SerializableSubList implements Serializable{
      private static final long serialVersionUID=1L;
      private transient double[] arr;
      private transient int size;
      private transient final int rootOffset;
      private SerializableSubList(double[] arr,int size,int rootOffset
      ){
        this.arr=arr;
        this.size=size;
        this.rootOffset=rootOffset;
      }
      private Object readResolve(){
        return new UncheckedList(size,arr);
      }
      private void readObject(ObjectInputStream ois) throws IOException
      {
        int size;
        this.size=size=ois.readInt();
        if(size!=0){
          double[] arr;
          OmniArray.OfDouble.readArray(arr=new double[size],0,size-1,ois);
          this.arr=arr;
        }else{
          this.arr=OmniArray.OfDouble.DEFAULT_ARR;
        }
      }
      private void writeObject(ObjectOutputStream oos) throws IOException{
        {
          int size;
          oos.writeInt(size=this.size);
          if(size!=0){
            final int rootOffset;
            OmniArray.OfDouble.writeArray(arr,rootOffset=this.rootOffset,rootOffset+size-1,oos);
          }
        }
      }
    }
    private Object writeReplace(){
      return new SerializableSubList(root.arr,this.size,this.rootOffset);
    }
    @Override public boolean equals(Object val){
      if(val==this){
        return true;
      }
      if(val instanceof List){
         final List<?> list;
         if((list=(List<?>)val) instanceof AbstractSeq){
            final AbstractSeq<?> abstractSeq;
            if((abstractSeq=(AbstractSeq<?>)list).size==size){
              final int rootOffset;
              return root.isEqualTo(rootOffset=this.rootOffset,rootOffset+size,abstractSeq);
            }
         }else{
            return this.size==list.size() && root.isEqualTo(list.listIterator(),this.rootOffset);
         }
      }
      return false;
    }
    @Override public Object clone(){
      final double[] copy;
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(root.arr,rootOffset,copy=new double[size],0,size);
      }else{
        copy=OmniArray.OfDouble.DEFAULT_ARR;
      }
      return new UncheckedList(size,copy);
    }
    @Override public String toString(){
      int size;
      if((size=this.size)!=0){
          final int rootOffset;
          final StringBuilder builder;
          OmniArray.OfDouble.ascendingToString(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,builder=new StringBuilder("["));
          return builder.append(']').toString();
      }
      return "[]";
    }
  @Override public int hashCode(){
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return OmniArray.OfDouble.ascendingSeqHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
    }
    return 1;
  }
    @Override public void clear(){
      final int size;
      if((size=this.size)!=0){
        for(var curr=parent;curr!=null;curr.size-=size,curr=curr.parent){}
        final UncheckedList root;
        (root=this.root).size=OmniArray.OfDouble.removeRangeAndPullDown(root.arr,this.rootOffset+size,root.size,size);
        this.size=0;
      }
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val){
              return this.uncheckedremoveValBits(size,TypeUtil.DBL_TRUE_BITS);
            }
            return this.uncheckedremoveVal0(size);
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
            if(val!=0){
              {
                return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
              }
            }else{
              return this.uncheckedremoveVal0(size);
            }
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
            if(val!=0){
              if(TypeUtil.checkCastToDouble(val)){
                return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
              }
            }else{
              return this.uncheckedremoveVal0(size);
            }
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
            if(val==val){
              return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
            }
            return this.uncheckedremoveValNaN(size);
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
            if(val==val){
              return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
            }
            return this.uncheckedremoveValNaN(size);
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
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              if(val instanceof Double){
                final double d;
                if((d=(double)val)==d){
                   return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(d));
                }
                return this.uncheckedremoveValNaN(size);
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(f));
                }
                return this.uncheckedremoveValNaN(size);
              }else if(val instanceof Integer|| val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).intValue())!=0){
                  return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(i));
                }
                return this.uncheckedremoveVal0(size);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToDouble(l)){
                    break returnFalse;
                  }
                  return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(l));
                }
                return this.uncheckedremoveVal0(size);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(i));
                }
                return this.uncheckedremoveVal0(size);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return this.uncheckedremoveValBits(size,TypeUtil.DBL_TRUE_BITS);
                }
                return this.uncheckedremoveVal0(size);
              }else{
                break returnFalse;
              }
            }
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
            if(val){
              final int rootOffset;
              return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,TypeUtil.DBL_TRUE_BITS);
            }
            final int rootOffset;
            return OmniArray.OfDouble.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
            if(val!=0){
              {
                final int rootOffset;
                return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Double.doubleToRawLongBits(val));
              }
            }else{
              final int rootOffset;
              return OmniArray.OfDouble.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
            }
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
            if(val!=0){
              if(TypeUtil.checkCastToDouble(val)){
                final int rootOffset;
                return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Double.doubleToRawLongBits(val));
              }
            }else{
              final int rootOffset;
              return OmniArray.OfDouble.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
            }
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
            if(val==val){
              final int rootOffset;
              return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Double.doubleToRawLongBits(val));
            }
            final int rootOffset;
            return OmniArray.OfDouble.uncheckedcontainsNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
            if(val==val){
              final int rootOffset;
              return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Double.doubleToRawLongBits(val));
            }
            final int rootOffset;
            return OmniArray.OfDouble.uncheckedcontainsNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              if(val instanceof Double){
                final double d;
                if((d=(double)val)==d){
                   final int rootOffset;
                   return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Double.doubleToRawLongBits(d));
                }
                final int rootOffset;
                return OmniArray.OfDouble.uncheckedcontainsNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   final int rootOffset;
                   return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Double.doubleToRawLongBits(f));
                }
                final int rootOffset;
                return OmniArray.OfDouble.uncheckedcontainsNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
              }else if(val instanceof Integer|| val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).intValue())!=0){
                  final int rootOffset;
                  return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Double.doubleToRawLongBits(i));
                }
                final int rootOffset;
                return OmniArray.OfDouble.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToDouble(l)){
                    break returnFalse;
                  }
                  final int rootOffset;
                  return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Double.doubleToRawLongBits(l));
                }
                final int rootOffset;
                return OmniArray.OfDouble.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  final int rootOffset;
                  return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Double.doubleToRawLongBits(i));
                }
                final int rootOffset;
                return OmniArray.OfDouble.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  final int rootOffset;
                  return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,TypeUtil.DBL_TRUE_BITS);
                }
                final int rootOffset;
                return OmniArray.OfDouble.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
              }else{
                break returnFalse;
              }
            }
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
            if(val){
              return OmniArray.OfDouble.uncheckedindexOfBits(root.arr,this.rootOffset,size,TypeUtil.DBL_TRUE_BITS);
            }
            return OmniArray.OfDouble.uncheckedindexOf0(root.arr,this.rootOffset,size);
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
            if(val!=0){
              {
                return OmniArray.OfDouble.uncheckedindexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(val));
              }
            }else{
              return OmniArray.OfDouble.uncheckedindexOf0(root.arr,this.rootOffset,size);
            }
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
            if(val!=0){
              if(TypeUtil.checkCastToDouble(val)){
                return OmniArray.OfDouble.uncheckedindexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(val));
              }
            }else{
              return OmniArray.OfDouble.uncheckedindexOf0(root.arr,this.rootOffset,size);
            }
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
            if(val==val){
              return OmniArray.OfDouble.uncheckedindexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(val));
            }
            return OmniArray.OfDouble.uncheckedindexOfNaN(root.arr,this.rootOffset,size);
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
            if(val==val){
              return OmniArray.OfDouble.uncheckedindexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(val));
            }
            return OmniArray.OfDouble.uncheckedindexOfNaN(root.arr,this.rootOffset,size);
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
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              if(val instanceof Double){
                final double d;
                if((d=(double)val)==d){
                   return OmniArray.OfDouble.uncheckedindexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(d));
                }
                return OmniArray.OfDouble.uncheckedindexOfNaN(root.arr,this.rootOffset,size);
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return OmniArray.OfDouble.uncheckedindexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(f));
                }
                return OmniArray.OfDouble.uncheckedindexOfNaN(root.arr,this.rootOffset,size);
              }else if(val instanceof Integer|| val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).intValue())!=0){
                  return OmniArray.OfDouble.uncheckedindexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(i));
                }
                return OmniArray.OfDouble.uncheckedindexOf0(root.arr,this.rootOffset,size);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToDouble(l)){
                    break returnFalse;
                  }
                  return OmniArray.OfDouble.uncheckedindexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(l));
                }
                return OmniArray.OfDouble.uncheckedindexOf0(root.arr,this.rootOffset,size);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return OmniArray.OfDouble.uncheckedindexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(i));
                }
                return OmniArray.OfDouble.uncheckedindexOf0(root.arr,this.rootOffset,size);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return OmniArray.OfDouble.uncheckedindexOfBits(root.arr,this.rootOffset,size,TypeUtil.DBL_TRUE_BITS);
                }
                return OmniArray.OfDouble.uncheckedindexOf0(root.arr,this.rootOffset,size);
              }else{
                break returnFalse;
              }
            }
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
            if(val){
              return OmniArray.OfDouble.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,TypeUtil.DBL_TRUE_BITS);
            }
            return OmniArray.OfDouble.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
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
            if(val!=0){
              {
                return OmniArray.OfDouble.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(val));
              }
            }else{
              return OmniArray.OfDouble.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
            }
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
            if(val!=0){
              if(TypeUtil.checkCastToDouble(val)){
                return OmniArray.OfDouble.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(val));
              }
            }else{
              return OmniArray.OfDouble.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
            }
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
            if(val==val){
              return OmniArray.OfDouble.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(val));
            }
            return OmniArray.OfDouble.uncheckedlastIndexOfNaN(root.arr,this.rootOffset,size);
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
            if(val==val){
              return OmniArray.OfDouble.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(val));
            }
            return OmniArray.OfDouble.uncheckedlastIndexOfNaN(root.arr,this.rootOffset,size);
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
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              if(val instanceof Double){
                final double d;
                if((d=(double)val)==d){
                   return OmniArray.OfDouble.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(d));
                }
                return OmniArray.OfDouble.uncheckedlastIndexOfNaN(root.arr,this.rootOffset,size);
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return OmniArray.OfDouble.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(f));
                }
                return OmniArray.OfDouble.uncheckedlastIndexOfNaN(root.arr,this.rootOffset,size);
              }else if(val instanceof Integer|| val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).intValue())!=0){
                  return OmniArray.OfDouble.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(i));
                }
                return OmniArray.OfDouble.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToDouble(l)){
                    break returnFalse;
                  }
                  return OmniArray.OfDouble.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(l));
                }
                return OmniArray.OfDouble.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return OmniArray.OfDouble.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(i));
                }
                return OmniArray.OfDouble.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return OmniArray.OfDouble.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,TypeUtil.DBL_TRUE_BITS);
                }
                return OmniArray.OfDouble.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
              }else{
                break returnFalse;
              }
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    private boolean uncheckedremoveValBits(int size
    ,long bits
    ){
      final UncheckedList root;
      final var arr=(root=this.root).arr;
      for(int index=this.rootOffset,bound=index+(--size);;++index){
        if(
        bits==Double.doubleToRawLongBits(arr[index])
        )
        {
          for(var curr=parent;curr!=null;--curr.size,curr=curr.parent){}
          OmniArray.OfDouble.removeIndexAndPullDown(arr,index,--root.size);
          this.size=size;
          return true;
        }else if(index==bound){
          return false;
        }
      }
    }
    private boolean uncheckedremoveValNaN(int size
    ){
      final UncheckedList root;
      final var arr=(root=this.root).arr;
      for(int index=this.rootOffset,bound=index+(--size);;++index){
        if(
        Double.isNaN(arr[index])
        )
        {
          for(var curr=parent;curr!=null;--curr.size,curr=curr.parent){}
          OmniArray.OfDouble.removeIndexAndPullDown(arr,index,--root.size);
          this.size=size;
          return true;
        }else if(index==bound){
          return false;
        }
      }
    }
    private boolean uncheckedremoveVal0(int size
    ){
      final UncheckedList root;
      final var arr=(root=this.root).arr;
      for(int index=this.rootOffset,bound=index+(--size);;++index){
        if(
        arr[index]==0
        )
        {
          for(var curr=parent;curr!=null;--curr.size,curr=curr.parent){}
          OmniArray.OfDouble.removeIndexAndPullDown(arr,index,--root.size);
          this.size=size;
          return true;
        }else if(index==bound){
          return false;
        }
      }
    }
    @Override public void forEach(DoubleConsumer action){
      {
        final int size;
        if((size=this.size)!=0){
          final int rootOffset;
          OmniArray.OfDouble.ascendingForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,action);
        }
      }
    }
    @Override public void forEach(Consumer<? super Double> action){
      {
        final int size;
        if((size=this.size)!=0){
          final int rootOffset;
          OmniArray.OfDouble.ascendingForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,action::accept);
        }
      }
    }
    private static class Itr
      extends AbstractDoubleItr
    {
      transient final UncheckedSubList parent;
      transient int cursor;
      private Itr(Itr itr){
        this.parent=itr.parent;
        this.cursor=itr.cursor;
      }
      private Itr(UncheckedSubList parent){
        this.parent=parent;
        this.cursor=parent.rootOffset;
      }
      private Itr(UncheckedSubList parent,int cursor){
        this.parent=parent;
        this.cursor=cursor;
      }
      @Override public Object clone(){
        return new Itr(this);
      }
      @Override public boolean hasNext(){
        final UncheckedSubList parent;
        return this.cursor<(parent=this.parent).rootOffset+parent.size;
      }
      @Override public double nextDouble(){
        return (double)parent.root.arr[cursor++];
      }
      @Override public void remove(){
        UncheckedSubList parent;
        final UncheckedList root;
        OmniArray.OfDouble.removeIndexAndPullDown((root=(parent=this.parent).root).arr,--this.cursor,--root.size);
        do{
          --parent.size;
        }while((parent=parent.parent)!=null);
      }
      @Override public void forEachRemaining(DoubleConsumer action){
        final int cursor,bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size)){
          OmniArray.OfDouble.ascendingForEach(parent.root.arr,cursor,bound-1,action);
          this.cursor=bound;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Double> action){
        final int cursor,bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size)){
          OmniArray.OfDouble.ascendingForEach(parent.root.arr,cursor,bound-1,action::accept);
          this.cursor=bound;
        }
      }
    }
    @Override public OmniIterator.OfDouble iterator(){
      return new Itr(this);
    }
    private static class ListItr extends Itr implements OmniListIterator.OfDouble{
      transient int lastRet;
      private ListItr(ListItr itr){
        super(itr);
        this.lastRet=itr.lastRet;
      }
      private ListItr(UncheckedSubList parent){
        super(parent);
      }
      private ListItr(UncheckedSubList parent,int cursor){
        super(parent,cursor);
      }
      @Override public Object clone(){
        return new ListItr(this);
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
      @Override public double nextDouble(){
        int lastRet;
        this.lastRet=lastRet=this.cursor++;
        return (double)parent.root.arr[lastRet];
      }
      @Override public void remove(){
        UncheckedSubList parent;
        final UncheckedList root;
        final int lastRet;
        OmniArray.OfDouble.removeIndexAndPullDown((root=(parent=this.parent).root).arr,lastRet=this.lastRet,--root.size);
        this.cursor=lastRet;
        do{
          --parent.size;
        }while((parent=parent.parent)!=null);
      }
      @Override public void forEachRemaining(DoubleConsumer action){
        int cursor;
        final int bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size)){
          OmniArray.OfDouble.ascendingForEach(parent.root.arr,cursor,cursor=bound-1,action);
          this.lastRet=cursor;
          this.cursor=bound;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Double> action){
        int cursor;
        final int bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size)){
          OmniArray.OfDouble.ascendingForEach(parent.root.arr,cursor,cursor=bound-1,action::accept);
          this.lastRet=cursor;
          this.cursor=bound;
        }
      }
      @Override public double previousDouble(){
        final int lastRet;
        this.lastRet=lastRet=--this.cursor;
        return (double)parent.root.arr[lastRet];
      }
      @Override public void set(double val){
        parent.root.arr[this.lastRet]=val;
      }
      @Override public void add(double val){
        final UncheckedList root;
        final int rootSize;
        UncheckedSubList parent;
        if((rootSize=(root=(parent=this.parent).root).size)!=0)
        {
          ((UncheckedList)root).uncheckedInsert(this.cursor++,rootSize,val);
        }else{
          ((DoubleArrSeq)root).uncheckedInit(val);
          ++this.cursor;
        }
        do{
          ++parent.size;
        }while((parent=parent.parent)!=null);
      }
    }
    @Override public OmniListIterator.OfDouble listIterator(){
      return new ListItr(this);
    }
    @Override public OmniListIterator.OfDouble listIterator(int index){
      return new ListItr(this,index+this.rootOffset);
    }
    @Override public boolean add(double val){
      for(var curr=parent;curr!=null;++curr.size,curr=curr.parent){}
      final UncheckedList root;
      final int rootSize;
      if((rootSize=(root=this.root).size)!=0){
        ((UncheckedList)root).uncheckedInsert(this.rootOffset+(this.size++),rootSize,val);
      }else{
        ((DoubleArrSeq)root).uncheckedInit(val);
        ++this.size;
      }
      return true;
    }
    @Override public void add(int index,double val){
      for(var curr=parent;curr!=null;++curr.size,curr=curr.parent){}
      ++this.size;
      final UncheckedList root;
      final int rootSize;
      if((rootSize=(root=this.root).size)!=0){
        ((UncheckedList)root).uncheckedInsert(this.rootOffset+index,rootSize,val);
      }else{
        ((DoubleArrSeq)root).uncheckedInit(val);
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
    @Override public double[] toDoubleArray(){
      final int size;
      if((size=this.size)!=0){
        final double[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new double[size],0,size);
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override public Double[] toArray(){
      final int size;
      if((size=this.size)!=0){
        final Double[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new Double[size],0,size);
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_BOXED_ARR;
    }
    @Override public void put(int index,double val){
      root.arr[index+this.rootOffset]=val;
    }
    @Override public double getDouble(int index){
      return (double)root.arr[index+this.rootOffset];
    }
    @Override public double set(int index,double val){
      final double[] arr;
      final var ret=(double)(arr=root.arr)[index+=this.rootOffset];
      arr[index]=val;
      return ret;
    }
    @Override public double removeDoubleAt(int index){
      final double[] arr;
      for(var curr=parent;curr!=null;--curr.size,curr=curr.parent){}
      final UncheckedList root;
      final var ret=(double)(arr=(root=this.root).arr)[index+=this.rootOffset];
      OmniArray.OfDouble.removeIndexAndPullDown(arr,index,--root.size);
      this.size=size-1;
      return ret;
    }
    @Override public boolean removeIf(DoublePredicate filter){
      final int size;
      if((size=this.size)!=0){
        {
          final double[] arr;
          final int numRemoved;
          int rootOffset;
          final UncheckedList root;
          if((numRemoved=uncheckedRemoveIfImpl(arr=(root=this.root).arr,rootOffset=this.rootOffset,rootOffset+=size,filter))!=0){
            for(var curr=parent;curr!=null;curr.size-=numRemoved,curr=curr.parent){}
            root.size=OmniArray.OfDouble.removeRangeAndPullDown(arr,rootOffset,root.size,numRemoved);
            this.size=size-numRemoved;
            return true;
          }
        }
      }
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Double> filter){
      final int size;
      if((size=this.size)!=0){
        {
          final double[] arr;
          final int numRemoved;
          int rootOffset;
          final UncheckedList root;
          if((numRemoved=uncheckedRemoveIfImpl(arr=(root=this.root).arr,rootOffset=this.rootOffset,rootOffset+=size,filter::test))!=0){
            for(var curr=parent;curr!=null;curr.size-=numRemoved,curr=curr.parent){}
            root.size=OmniArray.OfDouble.removeRangeAndPullDown(arr,rootOffset,root.size,numRemoved);
            this.size=size-numRemoved;
            return true;
          }
        }
      }
      return false;
    }
    @Override public void replaceAll(DoubleUnaryOperator operator){
      final int size;
      if((size=this.size)!=0){
        final int rootOffset;
        OmniArray.OfDouble.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);  
      }
    }
    @Override
    public void sort(DoubleComparator sorter)
    {
      final int size;
      if((size=this.size)>1){
        final int rootOffset;
        if(sorter==null){
          DoubleSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }else{
          DoubleSortUtil.uncheckedStableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter);
        }
      }
    }
    @Override
    public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)>1){
        final int rootOffset;
        DoubleSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }
    }
    @Override
    public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1){
        final int rootOffset;
        DoubleSortUtil.uncheckedDescendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }
    }
    @Override public void replaceAll(UnaryOperator<Double> operator){
      final int size;
      if((size=this.size)!=0){
        final int rootOffset;
        OmniArray.OfDouble.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator::apply);  
      }
    }
    @Override
    public void sort(Comparator<? super Double> sorter)
    {
      final int size;
      if((size=this.size)>1){
        final int rootOffset;
        if(sorter==null){
          DoubleSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }else{
          DoubleSortUtil.uncheckedStableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter::compare);
        }
      }
    }
    @Override
    public void unstableSort(DoubleComparator sorter)
    {
      final int size;
      if((size=this.size)>1){
        final int rootOffset;
        if(sorter==null){
          DoubleSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }else{
          DoubleSortUtil.uncheckedUnstableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter);
        }
      }
    }
    @Override public OmniList.OfDouble subList(int fromIndex,int toIndex){
      return new UncheckedSubList(this,this.rootOffset+fromIndex,toIndex-fromIndex);
    }
  }
  public
    static class CheckedStack
      extends UncheckedStack
  {
    private static final long serialVersionUID=1L;
    transient int modCount;
    public CheckedStack(){
      super();
    }
    public CheckedStack(int initialCapacity){
      super(initialCapacity);
    }
    CheckedStack(int size,double[] arr){
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
      final double[] copy;
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(this.arr,0,copy=new double[size],0,size);
      }else{
        copy=OmniArray.OfDouble.DEFAULT_ARR;
      }
      return new CheckedStack(size,copy);
    }
    @Override public void clear(){
      if(this.size!=0){
        ++this.modCount;
        this.size=0;
      }
    }
    @Override boolean uncheckedremoveValBits(int size
    ,long bits
    ){
      final var arr=this.arr;
      for(int index=--size;;--index){
        if(
        bits==Double.doubleToRawLongBits(arr[index])
        )
        {
          ++this.modCount;
          OmniArray.OfDouble.removeIndexAndPullDown(arr,index,size);
          this.size=size;
          return true;
        }else if(index==0){
          return false;
        }
      }
    }
    @Override boolean uncheckedremoveValNaN(int size
    ){
      final var arr=this.arr;
      for(int index=--size;;--index){
        if(
        Double.isNaN(arr[index])
        )
        {
          ++this.modCount;
          OmniArray.OfDouble.removeIndexAndPullDown(arr,index,size);
          this.size=size;
          return true;
        }else if(index==0){
          return false;
        }
      }
    }
    @Override boolean uncheckedremoveVal0(int size
    ){
      final var arr=this.arr;
      for(int index=--size;;--index){
        if(
        arr[index]==0
        )
        {
          ++this.modCount;
          OmniArray.OfDouble.removeIndexAndPullDown(arr,index,size);
          this.size=size;
          return true;
        }else if(index==0){
          return false;
        }
      }
    }
    @Override void uncheckedForEach(int size,DoubleConsumer action){
      int modCount=this.modCount;
      try
      {
        OmniArray.OfDouble.descendingForEach(this.arr,0,size-1,action);
      }
      finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    private static class Itr
      extends AbstractDoubleItr
    {
      transient final CheckedStack parent;
      transient int cursor;
      transient int lastRet;
      transient int modCount;
      private Itr(Itr itr){
        this.parent=itr.parent;
        this.cursor=itr.cursor;
        this.lastRet=itr.lastRet;
        this.modCount=itr.modCount;
      }
      private Itr(CheckedStack parent){
        this.parent=parent;
        this.cursor=parent.size;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      @Override public Object clone(){
        return new Itr(this);
      }
      @Override public boolean hasNext(){
        return this.cursor>0;
      }
      @Override public double nextDouble(){
        final CheckedStack root;
        CheckedCollection.checkModCount(modCount,(root=this.parent).modCount);
        int cursor;
        if((cursor=this.cursor)>0)
        {  
          this.lastRet=--cursor;
          this.cursor=cursor;
          return (double)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override public void remove(){
        final int lastRet;
        if((lastRet=this.lastRet)!=-1){
          int modCount;
          final CheckedStack root;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.parent).modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          OmniArray.OfDouble.removeIndexAndPullDown(root.arr,lastRet,--root.size);
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(DoubleConsumer action){
        final int cursor;
        if((cursor=this.cursor)>0){
          final int modCount=this.modCount;
          final var parent=this.parent;
          try{
            OmniArray.OfDouble.descendingForEach(parent.arr,0,cursor-1,action);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount,cursor,this.cursor);
          }
          this.cursor=0;
          this.lastRet=0;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Double> action){
        final int cursor;
        if((cursor=this.cursor)>0){
          final int modCount=this.modCount;
          final var parent=this.parent;
          try{
            OmniArray.OfDouble.descendingForEach(parent.arr,0,cursor-1,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount,cursor,this.cursor);
          }
          this.cursor=0;
          this.lastRet=0;
        }
      }
    }
    @Override public OmniIterator.OfDouble iterator(){
      return new Itr(this);
    }
    @Override public void push(double val){
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
    @Override public double popDouble(){
      int size;
      if((size=this.size)!=0){
        ++this.modCount;
        final var ret=(double)arr[--size];
        this.size=size;
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override public double pollDouble(){
      int size;
      if((size=this.size)!=0){
        ++this.modCount;
        final var ret=(double)(arr[--size]);
        this.size=size;
        return ret;
      }
      return Double.NaN;
    }
    @Override public Double poll(){
      int size;
      if((size=this.size)!=0){
        ++this.modCount;
        final var ret=(Double)(arr[--size]);
        this.size=size;
        return ret;
      }
      return null;
    }
    @Override boolean uncheckedRemoveIf(int size,DoublePredicate filter){
      final int modCount=this.modCount;
      try{
        if(size!=(size-=uncheckedRemoveIfImpl(this.arr
          ,0,size,filter,new ModCountChecker(modCount)))
          ){
          this.modCount=modCount+1;
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
    static class CheckedList
      extends UncheckedList
  {
    private static final long serialVersionUID=1L;
    transient int modCount;
    public CheckedList(){
      super();
    }
    public CheckedList(int initialCapacity){
      super(initialCapacity);
    }
    CheckedList(int size,double[] arr){
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
    private boolean isEqualTo(int rootOffset,int bound,AbstractSeq<?> list){
      if(list instanceof DoubleArrSeq.UncheckedList){
        return super.isEqualToHelper(rootOffset,bound,((DoubleArrSeq.UncheckedList)list).arr,0);
      }else if(list instanceof DoubleArrSeq.CheckedSubList){
        final DoubleArrSeq.CheckedSubList subList;
        final DoubleArrSeq.CheckedList thatRoot;
        if((thatRoot=(subList=(DoubleArrSeq.CheckedSubList)list).root)==this){
          return true;
        }
        CheckedCollection.checkModCount(subList.modCount,thatRoot.modCount);
        return super.isEqualToHelper(rootOffset,bound,thatRoot.arr,subList.rootOffset);
      }else if(list instanceof DoubleArrSeq.UncheckedSubList){
        final DoubleArrSeq.UncheckedSubList subList;
        return super.isEqualToHelper(rootOffset,bound,(subList=(DoubleArrSeq.UncheckedSubList)list).root.arr,subList.rootOffset);
      }else if(list instanceof DoubleDblLnkSeq){
        final DoubleDblLnkNode head;
        if(list instanceof DoubleDblLnkSeq.CheckedSubList){
          final DoubleDblLnkSeq.CheckedSubList subList;
          CheckedCollection.checkModCount((subList=(DoubleDblLnkSeq.CheckedSubList)list).modCount,subList.root.modCount);
          head=subList.head;
        }else{
          head=((DoubleDblLnkSeq)list).head;
        }
        return SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,bound,head);
      }else if(list instanceof RefArrSeq.UncheckedList){
        return SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,bound,((RefArrSeq.UncheckedList<?>)list).arr,0);
      }else if(list instanceof RefArrSeq.CheckedSubList){
        final RefArrSeq.CheckedSubList<?> subList;
        final RefArrSeq.CheckedList<?> thatRoot;
        CheckedCollection.checkModCount((subList=(RefArrSeq.CheckedSubList<?>)list).modCount,(thatRoot=subList.root).modCount);
        return SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,bound,thatRoot.arr,subList.rootOffset);
      }else if(list instanceof RefArrSeq.UncheckedSubList){
        final RefArrSeq.UncheckedSubList<?> subList;
        return SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,bound,(subList=(RefArrSeq.UncheckedSubList<?>)list).root.arr,subList.rootOffset);
      }else{
        //must be RefDblLnkSeq
        final RefDblLnkNode<?> head;
        if(list instanceof RefDblLnkSeq.CheckedSubList){
          final RefDblLnkSeq.CheckedSubList<?> subList;
          CheckedCollection.checkModCount((subList=(RefDblLnkSeq.CheckedSubList<?>)list).modCount,subList.root.modCount);
          head=subList.head;
        }else{
          head=((RefDblLnkSeq<?>)list).head;
        }
        return SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,bound,head);
      }
    }
    private boolean isEqualTo(int size,AbstractSeq<?> list){
      if(list instanceof DoubleArrSeq.UncheckedList){
        return super.isEqualToHelper(0,size,((DoubleArrSeq.UncheckedList)list).arr,0);
      }else if(list instanceof DoubleDblLnkSeq){
        final DoubleDblLnkNode head;
        if(list instanceof DoubleDblLnkSeq.CheckedSubList){
          final DoubleDblLnkSeq.CheckedSubList subList;
          CheckedCollection.checkModCount((subList=(DoubleDblLnkSeq.CheckedSubList)list).modCount,subList.root.modCount);
          head=subList.head;
        }else{
          head=((DoubleDblLnkSeq)list).head;
        }
        return SequenceEqualityUtil.isEqualTo(this.arr,0,size,head);
      }else if(list instanceof RefArrSeq.UncheckedList){
        return SequenceEqualityUtil.isEqualTo(this.arr,0,size,((RefArrSeq.UncheckedList<?>)list).arr,0);
      }else if(list instanceof RefDblLnkSeq){
        final RefDblLnkNode<?> head;
        if(list instanceof RefDblLnkSeq.CheckedSubList){
          final RefDblLnkSeq.CheckedSubList<?> subList;
          CheckedCollection.checkModCount((subList=(RefDblLnkSeq.CheckedSubList<?>)list).modCount,subList.root.modCount);
          head=subList.head;
        }else{
          head=((RefDblLnkSeq<?>)list).head;
        }
        return SequenceEqualityUtil.isEqualTo(this.arr,0,size,head);
      }else if(list instanceof DoubleArrSeq.CheckedSubList){
        final DoubleArrSeq.CheckedSubList subList;
        final DoubleArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount((subList=(DoubleArrSeq.CheckedSubList)list).modCount,(thatRoot=subList.root).modCount);
        return thatRoot==this || super.isEqualToHelper(0,size,thatRoot.arr,subList.rootOffset);
      }else if(list instanceof DoubleArrSeq.UncheckedSubList){
        final DoubleArrSeq.UncheckedSubList subList;
        return super.isEqualToHelper(0,size,(subList=(DoubleArrSeq.UncheckedSubList)list).root.arr,subList.rootOffset);
      }else if(list instanceof RefArrSeq.CheckedSubList){
        final RefArrSeq.CheckedSubList<?> subList;
        final RefArrSeq.CheckedList<?> thatRoot;
        CheckedCollection.checkModCount((subList=(RefArrSeq.CheckedSubList<?>)list).modCount,(thatRoot=subList.root).modCount);
        return SequenceEqualityUtil.isEqualTo(this.arr,0,size,thatRoot.arr,subList.rootOffset);
      }else{
        //must be RefArrSeq.UncheckedSubList
        final RefArrSeq.UncheckedSubList<?> subList;
        return SequenceEqualityUtil.isEqualTo(this.arr,0,size,(subList=(RefArrSeq.UncheckedSubList<?>)list).root.arr,subList.rootOffset);
      }
    }
    @Override public boolean equals(Object val){
      if(val==this){
        return true;
      }
      if(val instanceof List){
         final List<?> list;
         if((list=(List<?>)val) instanceof AbstractSeq){
           final AbstractSeq<?> abstractSeq;
           if((abstractSeq=(AbstractSeq<?>)list).size==size){
             return this.isEqualTo(size,abstractSeq);
           }
         }else{
           final int modCount=this.modCount;
           try{
             return this.size==list.size() && super.isEqualTo(list.listIterator(),0);
           }finally{
             CheckedCollection.checkModCount(modCount,this.modCount);
           }
         }
      }
      return false;
    }
    @Override public Object clone(){
      final double[] copy;
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(this.arr,0,copy=new double[size],0,size);
      }else{
        copy=OmniArray.OfDouble.DEFAULT_ARR;
      }
      return new CheckedList(size,copy);
    }
    @Override public void clear(){
      if(this.size!=0){
        ++this.modCount;
        this.size=0;
      }
    }
    @Override boolean uncheckedremoveValBits(int size
    ,long bits
    ){
      final var arr=this.arr;
      for(int index=0;;){
        if(
        bits==Double.doubleToRawLongBits(arr[index])
        )
        {
          ++this.modCount;
          OmniArray.OfDouble.removeIndexAndPullDown(arr,index,--size);
          this.size=size;
          return true;
        }else if(++index==size){
          return false;
        }
      }
    }
    @Override boolean uncheckedremoveValNaN(int size
    ){
      final var arr=this.arr;
      for(int index=0;;){
        if(
        Double.isNaN(arr[index])
        )
        {
          ++this.modCount;
          OmniArray.OfDouble.removeIndexAndPullDown(arr,index,--size);
          this.size=size;
          return true;
        }else if(++index==size){
          return false;
        }
      }
    }
    @Override boolean uncheckedremoveVal0(int size
    ){
      final var arr=this.arr;
      for(int index=0;;){
        if(
        arr[index]==0
        )
        {
          ++this.modCount;
          OmniArray.OfDouble.removeIndexAndPullDown(arr,index,--size);
          this.size=size;
          return true;
        }else if(++index==size){
          return false;
        }
      }
    }
    @Override void uncheckedForEach(int size,DoubleConsumer action){
      int modCount=this.modCount;
      try
      {
        OmniArray.OfDouble.ascendingForEach(this.arr,0,size-1,action);
      }
      finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    private static class Itr
      extends AbstractDoubleItr
    {
      transient final CheckedList parent;
      transient int cursor;
      transient int lastRet;
      transient int modCount;
      private Itr(Itr itr){
        this.parent=itr.parent;
        this.cursor=itr.cursor;
        this.lastRet=itr.lastRet;
        this.modCount=itr.modCount;
      }
      private Itr(CheckedList parent){
        this.parent=parent;
        this.cursor=0;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      private Itr(CheckedList parent,int cursor){
        this.parent=parent;
        this.cursor=cursor;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      @Override public Object clone(){
        return new Itr(this);
      }
      @Override public boolean hasNext(){
        return this.cursor<parent.size;
      }
      @Override public double nextDouble(){
        final CheckedList root;
        CheckedCollection.checkModCount(modCount,(root=this.parent).modCount);
        final int cursor;
        if((cursor=this.cursor)<root.size)
        {  
          this.lastRet=cursor;
          this.cursor=cursor+1;
          return (double)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override public void remove(){
        final int lastRet;
        if((lastRet=this.lastRet)!=-1){
          int modCount;
          final CheckedList root;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.parent).modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          OmniArray.OfDouble.removeIndexAndPullDown(root.arr,lastRet,--root.size);
          this.cursor=lastRet;
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(DoubleConsumer action){
        final int cursor;
        final int bound;
        final CheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size)){
          final int modCount=this.modCount;
          final int lastRet;
          try{
            OmniArray.OfDouble.ascendingForEach(parent.arr,cursor,lastRet=bound-1,action);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount,cursor,this.cursor);
          }
          this.cursor=bound;
          this.lastRet=lastRet;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Double> action){
        final int cursor;
        final int bound;
        final CheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size)){
          final int modCount=this.modCount;
          final int lastRet;
          try{
            OmniArray.OfDouble.ascendingForEach(parent.arr,cursor,lastRet=bound-1,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount,cursor,this.cursor);
          }
          this.cursor=bound;
          this.lastRet=lastRet;
        }
      }
    }
    @Override public OmniIterator.OfDouble iterator(){
      return new Itr(this);
    }
    private static class ListItr extends Itr implements OmniListIterator.OfDouble{
      private ListItr(ListItr itr){
        super(itr);
      }
      private ListItr(CheckedList parent){
        super(parent);
      }
      private ListItr(CheckedList parent,int cursor){
        super(parent,cursor);
      }
      @Override public Object clone(){
        return new ListItr(this);
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
      @Override public double previousDouble(){
        final CheckedList root;
        int cursor;
        CheckedCollection.checkModCount(modCount,(root=this.parent).modCount);
        if((cursor=this.cursor)>0)
        {
          this.lastRet=--cursor;
          this.cursor=cursor;
          return (double)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override public void set(double val){
        final int lastRet;
        if((lastRet=this.lastRet)!=-1){
          final CheckedList root;
          CheckedCollection.checkModCount(modCount,(root=this.parent).modCount);
          root.arr[lastRet]=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void add(double val){
        int modCount;
        final CheckedList root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.parent).modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        this.lastRet=-1;
        final int rootSize;
        if((rootSize=root.size)!=0)
        {
          ((UncheckedList)root).uncheckedInsert(this.cursor++,rootSize,val);
        }else{
          ((DoubleArrSeq)root).uncheckedInit(val);
          ++this.cursor;
        }
      }
    }
    @Override public OmniListIterator.OfDouble listIterator(){
      return new ListItr(this);
    }
    @Override public OmniListIterator.OfDouble listIterator(int index){
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,this.size);
      return new ListItr(this,index);
    }
    @Override public void push(double val){
      ++this.modCount;
      super.push(val);
    }
    @Override public void add(int index,double val){
      final int size;
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,size=this.size);
      ++this.modCount;
      if(size!=0){
        ((UncheckedList)this).uncheckedInsert(index,size,val);
      }else{
        ((DoubleArrSeq)this).uncheckedInit(val);
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
    @Override public void put(int index,double val){
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      this.arr[index]=val;
    }
    @Override public double getDouble(int index){
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      return (double)this.arr[index];
    }
    @Override public double set(int index,double val){
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      final double[] arr;
      final var ret=(double)(arr=this.arr)[index];
      arr[index]=val;
      return ret;
    }
    @Override public double removeDoubleAt(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      final double[] arr;
      ++this.modCount;
      final var ret=(double)(arr=this.arr)[index];
      OmniArray.OfDouble.removeIndexAndPullDown(arr,index,--size);
      this.size=size;
      return ret;
    }
    @Override boolean uncheckedRemoveIf(int size,DoublePredicate filter){
      final int modCount=this.modCount;
      try{
        if(size!=(size-=uncheckedRemoveIfImpl(this.arr
          ,0,size,filter,new ModCountChecker(modCount)))
          ){
          this.modCount=modCount+1;
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
    @Override public void replaceAll(DoubleUnaryOperator operator){
      final int size;
      if((size=this.size)!=0){
        int modCount=this.modCount;
        try{
          OmniArray.OfDouble.uncheckedReplaceAll(this.arr,0,size,operator);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override
    public void sort(DoubleComparator sorter)
    {
      final int size;
      if((size=this.size)>1){
        if(sorter==null){
          DoubleSortUtil.uncheckedAscendingSort(this.arr,0,size);
          ++this.modCount;
        }else{
          final int modCount=this.modCount;
          try{
            DoubleSortUtil.uncheckedStableSort(this.arr,0,size,sorter);
          }catch(ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException("Comparison method violates its general contract!",e);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.modCount=modCount+1;
          }
        }
      }
    }
    @Override
    public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)>1){
        DoubleSortUtil.uncheckedAscendingSort(this.arr,0,size);
        this.modCount=modCount+1;
      }
    }
    @Override
    public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1){
        DoubleSortUtil.uncheckedDescendingSort(this.arr,0,size);
        this.modCount=modCount+1;
      }
    }
    @Override public void replaceAll(UnaryOperator<Double> operator){
      final int size;
      if((size=this.size)!=0){
        int modCount=this.modCount;
        try{
          OmniArray.OfDouble.uncheckedReplaceAll(this.arr,0,size,operator::apply);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override
    public void sort(Comparator<? super Double> sorter)
    {
      final int size;
      if((size=this.size)>1){
        if(sorter==null){
          DoubleSortUtil.uncheckedAscendingSort(this.arr,0,size);
          ++this.modCount;
        }else{
          final int modCount=this.modCount;
          try{
            DoubleSortUtil.uncheckedStableSort(this.arr,0,size,sorter::compare);
          }catch(ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException("Comparison method violates its general contract!",e);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.modCount=modCount+1;
          }
        }
      }
    }
    @Override
    public void unstableSort(DoubleComparator sorter)
    {
      final int size;
      if((size=this.size)>1){
        if(sorter==null){
          DoubleSortUtil.uncheckedAscendingSort(this.arr,0,size);
          ++this.modCount;
        }else{
          final int modCount=this.modCount;
          try{
            DoubleSortUtil.uncheckedUnstableSort(this.arr,0,size,sorter);
          }catch(ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException("Comparison method violates its general contract!",e);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.modCount=modCount+1;
          }
        }
      }
    }
    @Override public OmniList.OfDouble subList(int fromIndex,int toIndex){
      return new CheckedSubList(this,fromIndex,CheckedCollection.checkSubListRange(fromIndex,toIndex,this.size));
    }
  }
    static class CheckedSubList
      extends AbstractSeq<Double>
      implements DoubleSubListDefault,Cloneable,RandomAccess
  {
    private static final long serialVersionUID=1L;
    transient int modCount;
    transient final int rootOffset;
    transient final CheckedList root;
    transient final CheckedSubList parent;
    private CheckedSubList(CheckedList root,int rootOffset,int size){
      super(size);
      this.root=root;
      this.parent=null;
      this.rootOffset=rootOffset;
      this.modCount=root.modCount;
    }
    private CheckedSubList(CheckedSubList parent,int rootOffset,int size){
      super(size);
      this.root=parent.root;
      this.parent=parent;
      this.rootOffset=rootOffset;
      this.modCount=parent.modCount;
    }
    @Override public boolean add(Double val){
      return add((double)val);
    }
    private static class SerializableSubList implements Serializable{
      private static final long serialVersionUID=1L;
      private transient double[] arr;
      private transient int size;
      private transient final int rootOffset;
      private transient final CheckedList.ModCountChecker modCountChecker;
      private SerializableSubList(double[] arr,int size,int rootOffset
        ,CheckedList.ModCountChecker modCountChecker
      ){
        this.arr=arr;
        this.size=size;
        this.rootOffset=rootOffset;
        this.modCountChecker=modCountChecker;
      }
      private Object readResolve(){
        return new CheckedList(size,arr);
      }
      private void readObject(ObjectInputStream ois) throws IOException
      {
        int size;
        this.size=size=ois.readInt();
        if(size!=0){
          double[] arr;
          OmniArray.OfDouble.readArray(arr=new double[size],0,size-1,ois);
          this.arr=arr;
        }else{
          this.arr=OmniArray.OfDouble.DEFAULT_ARR;
        }
      }
      private void writeObject(ObjectOutputStream oos) throws IOException{
        try
        {
          int size;
          oos.writeInt(size=this.size);
          if(size!=0){
            final int rootOffset;
            OmniArray.OfDouble.writeArray(arr,rootOffset=this.rootOffset,rootOffset+size-1,oos);
          }
        }
        finally{
          modCountChecker.checkModCount();
        }
      }
    }
    private Object writeReplace(){
      final CheckedList root;
      return new SerializableSubList((root=this.root).arr,this.size,this.rootOffset,root.new ModCountChecker(this.modCount));
    }
    @Override public boolean equals(Object val){
      if(val==this){
        return true;
      }
      if(val instanceof List){
        final int modCount=this.modCount;
        final var root=this.root;
        try{
          final List<?> list;
          if((list=(List<?>)val) instanceof AbstractSeq){
            final AbstractSeq<?> abstractSeq;
            if((abstractSeq=(AbstractSeq<?>)list).size==size){
              final int rootOffset;
              return root.isEqualTo(rootOffset=this.rootOffset,rootOffset+size,abstractSeq);
            }
          }else{
            return this.size==list.size() && ((UncheckedList)root).isEqualTo(list.listIterator(),rootOffset);
          }
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      return false;
    }
    @Override public Object clone(){
      final CheckedList root;
      CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
      final double[] copy;
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(root.arr,rootOffset,copy=new double[size],0,size);
      }else{
        copy=OmniArray.OfDouble.DEFAULT_ARR;
      }
      return new CheckedList(size,copy);
    }
    @Override public String toString(){
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      int size;
      if((size=this.size)!=0){
          final int rootOffset;
          final StringBuilder builder;
          OmniArray.OfDouble.ascendingToString(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,builder=new StringBuilder("["));
          return builder.append(']').toString();
      }
      return "[]";
    }
  @Override public int hashCode(){
    final CheckedList root;
    CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return OmniArray.OfDouble.ascendingSeqHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
    }
    return 1;
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
      final CheckedList root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0){
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr.size-=size,curr=curr.parent){}
        root.size=OmniArray.OfDouble.removeRangeAndPullDown(root.arr,this.rootOffset+size,root.size,size);
        this.size=0;
      }
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val){
              return this.uncheckedremoveValBits(size,TypeUtil.DBL_TRUE_BITS);
            }
            return this.uncheckedremoveVal0(size);
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
            if(val!=0){
              {
                return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
              }
            }else{
              return this.uncheckedremoveVal0(size);
            }
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
            if(val!=0){
              if(TypeUtil.checkCastToDouble(val)){
                return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
              }
            }else{
              return this.uncheckedremoveVal0(size);
            }
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
            if(val==val){
              return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
            }
            return this.uncheckedremoveValNaN(size);
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
            if(val==val){
              return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(val));
            }
            return this.uncheckedremoveValNaN(size);
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
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              if(val instanceof Double){
                final double d;
                if((d=(double)val)==d){
                   return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(d));
                }
                return this.uncheckedremoveValNaN(size);
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(f));
                }
                return this.uncheckedremoveValNaN(size);
              }else if(val instanceof Integer|| val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).intValue())!=0){
                  return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(i));
                }
                return this.uncheckedremoveVal0(size);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToDouble(l)){
                    break returnFalse;
                  }
                  return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(l));
                }
                return this.uncheckedremoveVal0(size);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return this.uncheckedremoveValBits(size,Double.doubleToRawLongBits(i));
                }
                return this.uncheckedremoveVal0(size);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return this.uncheckedremoveValBits(size,TypeUtil.DBL_TRUE_BITS);
                }
                return this.uncheckedremoveVal0(size);
              }else{
                break returnFalse;
              }
            }
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
            if(val){
              final int rootOffset;
              return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,TypeUtil.DBL_TRUE_BITS);
            }
            final int rootOffset;
            return OmniArray.OfDouble.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
            if(val!=0){
              {
                final int rootOffset;
                return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Double.doubleToRawLongBits(val));
              }
            }else{
              final int rootOffset;
              return OmniArray.OfDouble.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
            }
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
            if(val!=0){
              if(TypeUtil.checkCastToDouble(val)){
                final int rootOffset;
                return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Double.doubleToRawLongBits(val));
              }
            }else{
              final int rootOffset;
              return OmniArray.OfDouble.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
            }
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
            if(val==val){
              final int rootOffset;
              return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Double.doubleToRawLongBits(val));
            }
            final int rootOffset;
            return OmniArray.OfDouble.uncheckedcontainsNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
            if(val==val){
              final int rootOffset;
              return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Double.doubleToRawLongBits(val));
            }
            final int rootOffset;
            return OmniArray.OfDouble.uncheckedcontainsNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              if(val instanceof Double){
                final double d;
                if((d=(double)val)==d){
                   final int rootOffset;
                   return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Double.doubleToRawLongBits(d));
                }
                final int rootOffset;
                return OmniArray.OfDouble.uncheckedcontainsNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   final int rootOffset;
                   return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Double.doubleToRawLongBits(f));
                }
                final int rootOffset;
                return OmniArray.OfDouble.uncheckedcontainsNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
              }else if(val instanceof Integer|| val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).intValue())!=0){
                  final int rootOffset;
                  return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Double.doubleToRawLongBits(i));
                }
                final int rootOffset;
                return OmniArray.OfDouble.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToDouble(l)){
                    break returnFalse;
                  }
                  final int rootOffset;
                  return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Double.doubleToRawLongBits(l));
                }
                final int rootOffset;
                return OmniArray.OfDouble.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  final int rootOffset;
                  return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Double.doubleToRawLongBits(i));
                }
                final int rootOffset;
                return OmniArray.OfDouble.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  final int rootOffset;
                  return OmniArray.OfDouble.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,TypeUtil.DBL_TRUE_BITS);
                }
                final int rootOffset;
                return OmniArray.OfDouble.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
              }else{
                break returnFalse;
              }
            }
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
            if(val){
              return OmniArray.OfDouble.uncheckedindexOfBits(root.arr,this.rootOffset,size,TypeUtil.DBL_TRUE_BITS);
            }
            return OmniArray.OfDouble.uncheckedindexOf0(root.arr,this.rootOffset,size);
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
            if(val!=0){
              {
                return OmniArray.OfDouble.uncheckedindexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(val));
              }
            }else{
              return OmniArray.OfDouble.uncheckedindexOf0(root.arr,this.rootOffset,size);
            }
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
            if(val!=0){
              if(TypeUtil.checkCastToDouble(val)){
                return OmniArray.OfDouble.uncheckedindexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(val));
              }
            }else{
              return OmniArray.OfDouble.uncheckedindexOf0(root.arr,this.rootOffset,size);
            }
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
            if(val==val){
              return OmniArray.OfDouble.uncheckedindexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(val));
            }
            return OmniArray.OfDouble.uncheckedindexOfNaN(root.arr,this.rootOffset,size);
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
            if(val==val){
              return OmniArray.OfDouble.uncheckedindexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(val));
            }
            return OmniArray.OfDouble.uncheckedindexOfNaN(root.arr,this.rootOffset,size);
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
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              if(val instanceof Double){
                final double d;
                if((d=(double)val)==d){
                   return OmniArray.OfDouble.uncheckedindexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(d));
                }
                return OmniArray.OfDouble.uncheckedindexOfNaN(root.arr,this.rootOffset,size);
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return OmniArray.OfDouble.uncheckedindexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(f));
                }
                return OmniArray.OfDouble.uncheckedindexOfNaN(root.arr,this.rootOffset,size);
              }else if(val instanceof Integer|| val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).intValue())!=0){
                  return OmniArray.OfDouble.uncheckedindexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(i));
                }
                return OmniArray.OfDouble.uncheckedindexOf0(root.arr,this.rootOffset,size);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToDouble(l)){
                    break returnFalse;
                  }
                  return OmniArray.OfDouble.uncheckedindexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(l));
                }
                return OmniArray.OfDouble.uncheckedindexOf0(root.arr,this.rootOffset,size);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return OmniArray.OfDouble.uncheckedindexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(i));
                }
                return OmniArray.OfDouble.uncheckedindexOf0(root.arr,this.rootOffset,size);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return OmniArray.OfDouble.uncheckedindexOfBits(root.arr,this.rootOffset,size,TypeUtil.DBL_TRUE_BITS);
                }
                return OmniArray.OfDouble.uncheckedindexOf0(root.arr,this.rootOffset,size);
              }else{
                break returnFalse;
              }
            }
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
            if(val){
              return OmniArray.OfDouble.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,TypeUtil.DBL_TRUE_BITS);
            }
            return OmniArray.OfDouble.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
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
            if(val!=0){
              {
                return OmniArray.OfDouble.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(val));
              }
            }else{
              return OmniArray.OfDouble.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
            }
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
            if(val!=0){
              if(TypeUtil.checkCastToDouble(val)){
                return OmniArray.OfDouble.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(val));
              }
            }else{
              return OmniArray.OfDouble.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
            }
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
            if(val==val){
              return OmniArray.OfDouble.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(val));
            }
            return OmniArray.OfDouble.uncheckedlastIndexOfNaN(root.arr,this.rootOffset,size);
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
            if(val==val){
              return OmniArray.OfDouble.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(val));
            }
            return OmniArray.OfDouble.uncheckedlastIndexOfNaN(root.arr,this.rootOffset,size);
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
            //TODO a pattern-matching switch statement would be great here
            returnFalse:for(;;){
              if(val instanceof Double){
                final double d;
                if((d=(double)val)==d){
                   return OmniArray.OfDouble.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(d));
                }
                return OmniArray.OfDouble.uncheckedlastIndexOfNaN(root.arr,this.rootOffset,size);
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return OmniArray.OfDouble.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(f));
                }
                return OmniArray.OfDouble.uncheckedlastIndexOfNaN(root.arr,this.rootOffset,size);
              }else if(val instanceof Integer|| val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).intValue())!=0){
                  return OmniArray.OfDouble.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(i));
                }
                return OmniArray.OfDouble.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToDouble(l)){
                    break returnFalse;
                  }
                  return OmniArray.OfDouble.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(l));
                }
                return OmniArray.OfDouble.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return OmniArray.OfDouble.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Double.doubleToRawLongBits(i));
                }
                return OmniArray.OfDouble.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return OmniArray.OfDouble.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,TypeUtil.DBL_TRUE_BITS);
                }
                return OmniArray.OfDouble.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
              }else{
                break returnFalse;
              }
            }
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    private boolean uncheckedremoveValBits(int size
    ,long bits
    ){
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      final var arr=root.arr;
      for(int index=this.rootOffset,bound=index+(--size);;++index){
        if(
        bits==Double.doubleToRawLongBits(arr[index])
        )
        {
          root.modCount=++modCount;
          this.modCount=modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,--curr.size,curr=curr.parent){}
          OmniArray.OfDouble.removeIndexAndPullDown(arr,index,--root.size);
          this.size=size;
          return true;
        }else if(index==bound){
          return false;
        }
      }
    }
    private boolean uncheckedremoveValNaN(int size
    ){
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      final var arr=root.arr;
      for(int index=this.rootOffset,bound=index+(--size);;++index){
        if(
        Double.isNaN(arr[index])
        )
        {
          root.modCount=++modCount;
          this.modCount=modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,--curr.size,curr=curr.parent){}
          OmniArray.OfDouble.removeIndexAndPullDown(arr,index,--root.size);
          this.size=size;
          return true;
        }else if(index==bound){
          return false;
        }
      }
    }
    private boolean uncheckedremoveVal0(int size
    ){
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      final var arr=root.arr;
      for(int index=this.rootOffset,bound=index+(--size);;++index){
        if(
        arr[index]==0
        )
        {
          root.modCount=++modCount;
          this.modCount=modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,--curr.size,curr=curr.parent){}
          OmniArray.OfDouble.removeIndexAndPullDown(arr,index,--root.size);
          this.size=size;
          return true;
        }else if(index==bound){
          return false;
        }
      }
    }
    @Override public void forEach(DoubleConsumer action){
      int modCount=this.modCount;
      final var root=this.root;
      try
      {
        final int size;
        if((size=this.size)!=0){
          final int rootOffset;
          OmniArray.OfDouble.ascendingForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,action);
        }
      }
      finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void forEach(Consumer<? super Double> action){
      int modCount=this.modCount;
      final var root=this.root;
      try
      {
        final int size;
        if((size=this.size)!=0){
          final int rootOffset;
          OmniArray.OfDouble.ascendingForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,action::accept);
        }
      }
      finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    private static class Itr
      extends AbstractDoubleItr
    {
      transient final CheckedSubList parent;
      transient int cursor;
      transient int lastRet;
      transient int modCount;
      private Itr(Itr itr){
        this.parent=itr.parent;
        this.cursor=itr.cursor;
        this.lastRet=itr.lastRet;
        this.modCount=itr.modCount;
      }
      private Itr(CheckedSubList parent){
        this.parent=parent;
        this.cursor=parent.rootOffset;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      private Itr(CheckedSubList parent,int cursor){
        this.parent=parent;
        this.cursor=cursor;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      @Override public Object clone(){
        return new Itr(this);
      }
      @Override public boolean hasNext(){
        final CheckedSubList parent;
        return this.cursor<(parent=this.parent).rootOffset+parent.size;
      }
      @Override public double nextDouble(){
        final CheckedList root;
        final CheckedSubList parent;
        CheckedCollection.checkModCount(modCount,(root=(parent=this.parent).root).modCount);
        final int cursor;
        if((cursor=this.cursor)<(parent.rootOffset+parent.size))
        {  
          this.lastRet=cursor;
          this.cursor=cursor+1;
          return (double)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override public void remove(){
        final int lastRet;
        if((lastRet=this.lastRet)!=-1){
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
          OmniArray.OfDouble.removeIndexAndPullDown(root.arr,lastRet,--root.size);
          this.cursor=lastRet;
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(DoubleConsumer action){
        final int cursor;
        final int bound;
        final CheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size)){
          final int modCount=this.modCount;
          final var root=parent.root;
          final int lastRet;
          try{
            OmniArray.OfDouble.ascendingForEach(root.arr,cursor,lastRet=bound-1,action);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount,cursor,this.cursor);
          }
          this.cursor=bound;
          this.lastRet=lastRet;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Double> action){
        final int cursor;
        final int bound;
        final CheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size)){
          final int modCount=this.modCount;
          final var root=parent.root;
          final int lastRet;
          try{
            OmniArray.OfDouble.ascendingForEach(root.arr,cursor,lastRet=bound-1,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount,cursor,this.cursor);
          }
          this.cursor=bound;
          this.lastRet=lastRet;
        }
      }
    }
    @Override public OmniIterator.OfDouble iterator(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new Itr(this);
    }
    private static class ListItr extends Itr implements OmniListIterator.OfDouble{
      private ListItr(ListItr itr){
        super(itr);
      }
      private ListItr(CheckedSubList parent){
        super(parent);
      }
      private ListItr(CheckedSubList parent,int cursor){
        super(parent,cursor);
      }
      @Override public Object clone(){
        return new ListItr(this);
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
      @Override public double previousDouble(){
        final CheckedList root;
        int cursor;
        final CheckedSubList parent;
        CheckedCollection.checkModCount(modCount,(root=(parent=this.parent).root).modCount);
        if((cursor=this.cursor)>parent.rootOffset)
        {
          this.lastRet=--cursor;
          this.cursor=cursor;
          return (double)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override public void set(double val){
        final int lastRet;
        if((lastRet=this.lastRet)!=-1){
          final CheckedList root;
          CheckedCollection.checkModCount(modCount,(root=this.parent.root).modCount);
          root.arr[lastRet]=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void add(double val){
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
          ((UncheckedList)root).uncheckedInsert(this.cursor++,rootSize,val);
        }else{
          ((DoubleArrSeq)root).uncheckedInit(val);
          ++this.cursor;
        }
      }
    }
    @Override public OmniListIterator.OfDouble listIterator(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new ListItr(this);
    }
    @Override public OmniListIterator.OfDouble listIterator(int index){
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,this.size);
      return new ListItr(this,index+this.rootOffset);
    }
    @Override public boolean add(double val){
      final CheckedList root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,++curr.size,curr=curr.parent){}
      if((modCount=root.size)!=0){
        ((UncheckedList)root).uncheckedInsert(this.rootOffset+(this.size++),modCount,val);
      }else{
        ((DoubleArrSeq)root).uncheckedInit(val);
        ++this.size;
      }
      return true;
    }
    @Override public void add(int index,double val){
      final CheckedList root;
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
        ((UncheckedList)root).uncheckedInsert(this.rootOffset+index,modCount,val);
      }else{
        ((DoubleArrSeq)root).uncheckedInit(val);
      }
    }
    @Override  public <T> T[] toArray(T[] arr){
      final CheckedList root;
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
      final CheckedList root;
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
    @Override public double[] toDoubleArray(){
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0){
        final double[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new double[size],0,size);
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override public Double[] toArray(){
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0){
        final Double[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new Double[size],0,size);
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_BOXED_ARR;
    }
    @Override public void put(int index,double val){
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      root.arr[index+this.rootOffset]=val;
    }
    @Override public double getDouble(int index){
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      return (double)root.arr[index+this.rootOffset];
    }
    @Override public double set(int index,double val){
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      final double[] arr;
      final var ret=(double)(arr=root.arr)[index+=this.rootOffset];
      arr[index]=val;
      return ret;
    }
    @Override public double removeDoubleAt(int index){
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      final double[] arr;
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,--curr.size,curr=curr.parent){}
      final var ret=(double)(arr=root.arr)[index+=this.rootOffset];
      OmniArray.OfDouble.removeIndexAndPullDown(arr,index,--root.size);
      this.size=size-1;
      return ret;
    }
    @Override public boolean removeIf(DoublePredicate filter){
      int modCount=this.modCount;
      final var root=this.root;
      final int size;
      if((size=this.size)!=0){
        try
        {
          final double[] arr;
          final int numRemoved;
          int rootOffset;
          if((numRemoved=uncheckedRemoveIfImpl(arr=root.arr,rootOffset=this.rootOffset,rootOffset+=size,filter,root.new ModCountChecker(modCount)))!=0){
            root.modCount=++modCount;
            this.modCount=modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,curr.size-=numRemoved,curr=curr.parent){}
            root.size=OmniArray.OfDouble.removeRangeAndPullDown(arr,rootOffset,root.size,numRemoved);
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
    @Override public boolean removeIf(Predicate<? super Double> filter){
      int modCount=this.modCount;
      final var root=this.root;
      final int size;
      if((size=this.size)!=0){
        try
        {
          final double[] arr;
          final int numRemoved;
          int rootOffset;
          if((numRemoved=uncheckedRemoveIfImpl(arr=root.arr,rootOffset=this.rootOffset,rootOffset+=size,filter::test,root.new ModCountChecker(modCount)))!=0){
            root.modCount=++modCount;
            this.modCount=modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,curr.size-=numRemoved,curr=curr.parent){}
            root.size=OmniArray.OfDouble.removeRangeAndPullDown(arr,rootOffset,root.size,numRemoved);
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
    @Override public void replaceAll(DoubleUnaryOperator operator){
      final int size;
      if((size=this.size)==0){
        CheckedCollection.checkModCount(modCount,root.modCount);
        return;
      }
      int modCount=this.modCount;
      final var root=this.root;
      try{
        final int rootOffset;
        OmniArray.OfDouble.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);  
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
      }
    }
    @Override
    public void sort(DoubleComparator sorter)
    {
      final int size;
      if((size=this.size)<2){
        CheckedCollection.checkModCount(modCount,root.modCount);
        return;
      }
      final var root=this.root;
      int modCount=this.modCount;
      if(sorter==null){
        CheckedCollection.checkModCount(modCount,root.modCount);
        final int rootOffset;
        DoubleSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
      }else{
        try{
          final int rootOffset;
          DoubleSortUtil.uncheckedStableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter);
        }catch(ArrayIndexOutOfBoundsException e){
          throw new IllegalArgumentException("Comparison method violates its general contract!",e);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        }
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
        DoubleSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
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
        DoubleSortUtil.uncheckedDescendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}  
      }
    }
    @Override public void replaceAll(UnaryOperator<Double> operator){
      final int size;
      if((size=this.size)==0){
        CheckedCollection.checkModCount(modCount,root.modCount);
        return;
      }
      int modCount=this.modCount;
      final var root=this.root;
      try{
        final int rootOffset;
        OmniArray.OfDouble.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator::apply);  
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
      }
    }
    @Override
    public void sort(Comparator<? super Double> sorter)
    {
      final int size;
      if((size=this.size)<2){
        CheckedCollection.checkModCount(modCount,root.modCount);
        return;
      }
      final var root=this.root;
      int modCount=this.modCount;
      if(sorter==null){
        CheckedCollection.checkModCount(modCount,root.modCount);
        final int rootOffset;
        DoubleSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
      }else{
        try{
          final int rootOffset;
          DoubleSortUtil.uncheckedStableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter::compare);
        }catch(ArrayIndexOutOfBoundsException e){
          throw new IllegalArgumentException("Comparison method violates its general contract!",e);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        }
      }
    }
    @Override
    public void unstableSort(DoubleComparator sorter)
    {
      final int size;
      if((size=this.size)<2){
        CheckedCollection.checkModCount(modCount,root.modCount);
        return;
      }
      final var root=this.root;
      int modCount=this.modCount;
      if(sorter==null){
        CheckedCollection.checkModCount(modCount,root.modCount);
        final int rootOffset;
        DoubleSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
      }else{
        try{
          final int rootOffset;
          DoubleSortUtil.uncheckedUnstableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter);
        }catch(ArrayIndexOutOfBoundsException e){
          throw new IllegalArgumentException("Comparison method violates its general contract!",e);
        }finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
        }
      }
    }
    @Override public OmniList.OfDouble subList(int fromIndex,int toIndex){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new CheckedSubList(this,this.rootOffset+fromIndex,CheckedCollection.checkSubListRange(fromIndex,toIndex,this.size));
    }
  }
}
