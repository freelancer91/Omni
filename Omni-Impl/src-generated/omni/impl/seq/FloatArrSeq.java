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
import omni.util.FloatSortUtil;
import omni.impl.CheckedCollection;
import java.util.NoSuchElementException;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import omni.impl.AbstractOmniCollection;
import java.util.ListIterator;
import java.util.function.IntFunction;
import java.util.function.UnaryOperator;
import omni.util.TypeUtil;
import java.util.ConcurrentModificationException;
import omni.function.FloatUnaryOperator;
import omni.function.FloatComparator;
import omni.function.FloatPredicate;
import omni.function.FloatConsumer;
import omni.util.ToStringUtil;
import omni.impl.AbstractFloatItr;
import java.io.Externalizable;
import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectInput;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.RandomAccess;
public abstract class FloatArrSeq extends 
AbstractOmniCollection<Float>
 implements OmniCollection.OfFloat,Externalizable,RandomAccess{
  //TODO refactor the template and/or optimize code generation to make sure that the code generation doesn't take forever
  private static final long serialVersionUID=1L;
  transient float[] arr; 
  private FloatArrSeq(){
    super();
    this.arr=OmniArray.OfFloat.DEFAULT_ARR;
  }
  private FloatArrSeq(Collection<? extends Float> that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private FloatArrSeq(OmniCollection.OfRef<? extends Float> that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private FloatArrSeq(OmniCollection.OfBoolean that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private FloatArrSeq(OmniCollection.FloatOutput<?> that){
    super();
    //TODO optimize;
    this.addAll(that);
  }
  private FloatArrSeq(OmniCollection.OfByte that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private FloatArrSeq(OmniCollection.OfShort that){
    super();
    //TODO optimize
    this.addAll(that);
  }  
  private FloatArrSeq(OmniCollection.OfInt that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private FloatArrSeq(OmniCollection.OfLong that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private FloatArrSeq(OmniCollection.OfFloat that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private FloatArrSeq(OmniCollection.OfChar that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private FloatArrSeq(int initialCapacity){
    super();
    switch(initialCapacity){ 
    default:
      this.arr=new float[initialCapacity];
      return;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfFloat.DEFAULT_ARR;
    case 0:
    }
  }
  private FloatArrSeq(int size,float[] arr){
    super(size);
    this.arr=arr;
  }
  @Override public void writeExternal(ObjectOutput out) throws IOException{
    int size;
    out.writeInt(size=this.size);
    if(size!=0)
    {
      OmniArray.OfFloat.writeArray(arr,0,size-1,out);
    }
  }
  @Override public void readExternal(ObjectInput in) throws IOException
  {
    int size;
    this.size=size=in.readInt();
    if(size!=0){
      float[] arr;
      OmniArray.OfFloat.readArray(arr=new float[size],0,size-1,in);
      this.arr=arr;
    }else{
      this.arr=OmniArray.OfFloat.DEFAULT_ARR;
    }
  }
  public float popFloat(){
    return (float)arr[--this.size];
  }
  static  long markSurvivors(float[] arr,int srcOffset,int srcBound,FloatPredicate filter){
    for(long word=0L,marker=1L;;marker<<=1){
      if(!filter.test((float)arr[srcOffset])){
        word|=marker;
      }
      if(++srcOffset==srcBound){
        return word;
      }
    }
  }
  static  int markSurvivors(float[] arr,int srcOffset,int srcBound,FloatPredicate filter,long[] survivorSet){
    for(int numSurvivors=0,wordOffset=0;;){
      long word=0L,marker=1L;
      do{
        if(!filter.test((float)arr[srcOffset])){
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
  static void pullSurvivorsDown(float[] arr,int srcOffset,int dstOffset,long word){
    int numTail0s=Long.numberOfTrailingZeros(word);
    do{
      ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset+=numTail0s,numTail0s=Long.numberOfTrailingZeros(~(word>>>=numTail0s)));
      srcOffset+=numTail0s;
      dstOffset+=numTail0s;
    }while((numTail0s=Long.numberOfTrailingZeros(word>>>=numTail0s))!=64);
  }
  static void pullSurvivorsDown(float[] arr,int srcOffset,int dstOffset,int dstBound,long[] survivorSet){
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
      final byte[] buffer;
      if(size<=(OmniArray.MAX_ARR_SIZE/17)){(buffer=new byte[size*17])
        [size=uncheckedToString(size,buffer)]=(byte)']';
        buffer[0]=(byte)'[';
        return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
      }else{
        final ToStringUtil.OmniStringBuilderByte builder;
        uncheckedToString(size,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
        builder.uncheckedAppendChar((byte)']');
        (buffer=builder.buffer)[0]=(byte)'[';
        return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
      }
    }
    return "[]";
  }
  abstract int uncheckedToString(int size,byte[] buffer);
  abstract void uncheckedToString(int size,ToStringUtil.OmniStringBuilderByte builder);
  @Override public boolean contains(boolean val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val){
            return OmniArray.OfFloat.uncheckedcontainsBits(this.arr,0,size-1,TypeUtil.FLT_TRUE_BITS);
          }
          return OmniArray.OfFloat.uncheckedcontains0(this.arr,0,size-1);
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
            if(TypeUtil.checkCastToFloat(val))
            {
              return OmniArray.OfFloat.uncheckedcontainsBits(this.arr,0,size-1,Float.floatToRawIntBits(val));
            }
          }else{
            return OmniArray.OfFloat.uncheckedcontains0(this.arr,0,size-1);
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
            if(TypeUtil.checkCastToFloat(val)){
              return OmniArray.OfFloat.uncheckedcontainsBits(this.arr,0,size-1,Float.floatToRawIntBits(val));
            }
          }else{
            return OmniArray.OfFloat.uncheckedcontains0(this.arr,0,size-1);
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
            return OmniArray.OfFloat.uncheckedcontainsBits(this.arr,0,size-1,Float.floatToRawIntBits(val));
          }
          return OmniArray.OfFloat.uncheckedcontainsNaN(this.arr,0,size-1);
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
          final float v;
          if(val==(v=(float)val)){
            return OmniArray.OfFloat.uncheckedcontainsBits(this.arr,0,size-1,Float.floatToRawIntBits(v));
          }else if(v!=v){
            return OmniArray.OfFloat.uncheckedcontainsNaN(this.arr,0,size-1);
          }
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
            if(val instanceof Float){
              final float f;
              if((f=(float)val)==f){
                 return OmniArray.OfFloat.uncheckedcontainsBits(this.arr,0,size-1,Float.floatToRawIntBits(f));
              }
              return OmniArray.OfFloat.uncheckedcontainsNaN(this.arr,0,size-1);
            }else if(val instanceof Double){
              final double d;
              final float f;
              if((d=(double)val)==(f=(float)d)){
                return OmniArray.OfFloat.uncheckedcontainsBits(this.arr,0,size-1,Float.floatToRawIntBits(f));
              }else if(f!=f){
                return OmniArray.OfFloat.uncheckedcontainsNaN(this.arr,0,size-1);
              }else{
                break returnFalse;
              }
            }else if(val instanceof Integer){
              final int i;
              if((i=(int)val)!=0){
                if(!TypeUtil.checkCastToFloat(i)){
                  break returnFalse;
                }
                return OmniArray.OfFloat.uncheckedcontainsBits(this.arr,0,size-1,Float.floatToRawIntBits(i));
              }
              return OmniArray.OfFloat.uncheckedcontains0(this.arr,0,size-1);
            }else if(val instanceof Long){
              final long l;
              if((l=(long)val)!=0){
                if(!TypeUtil.checkCastToFloat(l)){
                  break returnFalse;
                }
                return OmniArray.OfFloat.uncheckedcontainsBits(this.arr,0,size-1,Float.floatToRawIntBits(l));
              }
              return OmniArray.OfFloat.uncheckedcontains0(this.arr,0,size-1);
            }else if(val instanceof Short||val instanceof Byte){
              final int i;
              if((i=((Number)val).shortValue())!=0){
                return OmniArray.OfFloat.uncheckedcontainsBits(this.arr,0,size-1,Float.floatToRawIntBits(i));
              }
              return OmniArray.OfFloat.uncheckedcontains0(this.arr,0,size-1);
            }else if(val instanceof Character){
              final int i;
              if((i=(char)val)!=0){
                return OmniArray.OfFloat.uncheckedcontainsBits(this.arr,0,size-1,Float.floatToRawIntBits(i));
              }
              return OmniArray.OfFloat.uncheckedcontains0(this.arr,0,size-1);
            }else if(val instanceof Boolean){
              if((boolean)val){
                return OmniArray.OfFloat.uncheckedcontainsBits(this.arr,0,size-1,TypeUtil.FLT_TRUE_BITS);
              }
              return OmniArray.OfFloat.uncheckedcontains0(this.arr,0,size-1);
            }else{
              break returnFalse;
            }
          }
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
          if(val!=0){
            return OmniArray.OfFloat.uncheckedcontainsBits(this.arr,0,size-1,Float.floatToRawIntBits(val));
          }
          return OmniArray.OfFloat.uncheckedcontains0(this.arr,0,size-1);
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
          if(val!=0){
            return OmniArray.OfFloat.uncheckedcontainsBits(this.arr,0,size-1,Float.floatToRawIntBits(val));
          }
          return OmniArray.OfFloat.uncheckedcontains0(this.arr,0,size-1);
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
            return this.uncheckedremoveValBits(size,TypeUtil.FLT_TRUE_BITS);
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
            if(TypeUtil.checkCastToFloat(val))
            {
              return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
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
            if(TypeUtil.checkCastToFloat(val)){
              return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
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
            return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
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
          final float v;
          if(val==(v=(float)val)){
            return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(v));
          }else if(v!=v){
            return this.uncheckedremoveValNaN(size);
          }
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
            if(val instanceof Float){
              final float f;
              if((f=(float)val)==f){
                 return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(f));
              }
              return this.uncheckedremoveValNaN(size);
            }else if(val instanceof Double){
              final double d;
              final float f;
              if((d=(double)val)==(f=(float)d)){
                return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(f));
              }else if(f!=f){
                return this.uncheckedremoveValNaN(size);
              }else{
                break returnFalse;
              }
            }else if(val instanceof Integer){
              final int i;
              if((i=(int)val)!=0){
                if(!TypeUtil.checkCastToFloat(i)){
                  break returnFalse;
                }
                return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(i));
              }
              return this.uncheckedremoveVal0(size);
            }else if(val instanceof Long){
              final long l;
              if((l=(long)val)!=0){
                if(!TypeUtil.checkCastToFloat(l)){
                  break returnFalse;
                }
                return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(l));
              }
              return this.uncheckedremoveVal0(size);
            }else if(val instanceof Short||val instanceof Byte){
              final int i;
              if((i=((Number)val).shortValue())!=0){
                return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(i));
              }
              return this.uncheckedremoveVal0(size);
            }else if(val instanceof Character){
              final int i;
              if((i=(char)val)!=0){
                return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(i));
              }
              return this.uncheckedremoveVal0(size);
            }else if(val instanceof Boolean){
              if((boolean)val){
                return this.uncheckedremoveValBits(size,TypeUtil.FLT_TRUE_BITS);
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
  @Override public boolean removeVal(char val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          if(val!=0){
            return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
          }
          return this.uncheckedremoveVal0(size);
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
          if(val!=0){
            return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
          }
          return this.uncheckedremoveVal0(size);
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  abstract boolean uncheckedremoveValBits(int size,int bits);
  abstract boolean uncheckedremoveVal0(int size);
  abstract boolean uncheckedremoveValNaN(int size);
  abstract void uncheckedForEach(int size,FloatConsumer action);
  @Override public void forEach(FloatConsumer action){
    final int size;
    if((size=this.size)!=0){
      uncheckedForEach(size,action);
    }
  }
  @Override public void forEach(Consumer<? super Float> action){
    final int size;
    if((size=this.size)!=0){
      uncheckedForEach(size,action::accept);
    }
  }
  public void push(float val){
    final int size;
    if((size=this.size)!=0){
      uncheckedAppend(size,val);
    }else{
      uncheckedInit(val);
    }
  }
  @Override public boolean add(float val){
    push(val);
    return true;
  }
  @Override public boolean add(Float val){
    push((float)val);
    return true;
  }
  @Override public boolean add(boolean val){
    push((float)TypeUtil.castToFloat(val));
    return true;
  }
  @Override public boolean add(int val){
    push((float)val);
    return true;
  }
  @Override public boolean add(char val){
    push((float)val);
    return true;
  }
  @Override public boolean add(short val){
    push((float)val);
    return true;
  }  
  @Override public boolean add(long val){
    push((float)val);
    return true;
  }
  abstract void uncheckedCopyInto(float[] dst,int length);
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
  @Override public float[] toFloatArray(){
    final int size;
    if((size=this.size)!=0){
      final float[] dst;
      uncheckedCopyInto(dst=new float[size],size);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  abstract void uncheckedCopyInto(Object[] dst,int length);
  abstract void uncheckedCopyInto(Float[] dst,int length);
  @Override public Float[] toArray(){
    final int size;
    if((size=this.size)!=0){
      final Float[] dst;
      uncheckedCopyInto(dst=new Float[size],size);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_BOXED_ARR;
  }
  abstract void uncheckedCopyInto(double[] dst,int length);
  @Override public double[] toDoubleArray(){
    final int size;
    if((size=this.size)!=0){
      final double[] dst;
      uncheckedCopyInto(dst=new double[size],size);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  boolean uncheckedRemoveIf(int size,FloatPredicate filter){
    if(size!=(size-=uncheckedRemoveIfImpl(this.arr,0,size,filter))){
      this.size=size;
      return true;
    }
    return false;
  }
  @Override public boolean removeIf(FloatPredicate filter){
    final int size;
    return (size=this.size)!=0 && uncheckedRemoveIf(size,filter);
  }
  @Override public boolean removeIf(Predicate<? super Float> filter){
    final int size;
    return (size=this.size)!=0 && uncheckedRemoveIf(size,filter::test);
  }
  private
  void uncheckedAppend(int size,float val){
    float[] arr;
    if((arr=this.arr).length==size){
      ArrCopy.uncheckedCopy(arr,0,arr=new float[OmniArray.growBy50Pct(size)],0,size);
      this.arr=arr;
    }
    arr[size]=val;
    this.size=size+1;
  }
  void uncheckedInit(float val){
    float[] arr;
    if((arr=this.arr)==null){
      this.arr=new float[]{val};
    }else{
      if(arr==OmniArray.OfFloat.DEFAULT_ARR){
        this.arr=arr=new float[OmniArray.DEFAULT_ARR_SEQ_CAP];
      }
      arr[0]=val;
    }
    this.size=1;
  }
  private static  int pullSurvivorsDown(float[] arr,int srcOffset,int srcBound,int dstOffset,FloatPredicate filter){
    while(++srcOffset!=srcBound){
      final float v;
      if(!filter.test((float)(v=arr[srcOffset])))
      {
        arr[dstOffset++]=v;
      }
    }
    return srcBound-dstOffset;
  }
  private static  int uncheckedRemoveIfImpl(float[] arr,int srcOffset,int srcBound,FloatPredicate filter){
    do{
      if(filter.test((float)arr[srcOffset])){
        return pullSurvivorsDown(arr,srcOffset,srcBound,srcOffset,filter);
      }
    }while(++srcOffset!=srcBound);
    return 0;
  }
  private static  int uncheckedRemoveIfImpl(float[] arr,int srcOffset,int srcBound,FloatPredicate filter,CheckedCollection.AbstractModCountChecker modCountChecker){
    do{
      if(filter.test((float)arr[srcOffset])){
        int dstOffset=srcOffset;
        outer:for(;;){
          if(++srcOffset==srcBound){
            modCountChecker.checkModCount();
            break outer;
          }
          float before;
          if(!filter.test((float)(before=arr[srcOffset]))){
            for(int i=srcBound-1;;--i){
              if(i==srcOffset){
                modCountChecker.checkModCount();
                arr[dstOffset++]=before;
                break outer;
              }
              float after;
              if(!filter.test((float)(after=arr[i]))){
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
      extends FloatArrSeq
      implements OmniStack.OfFloat,Cloneable,RandomAccess
  {
    private static final long serialVersionUID=1L;
    public UncheckedStack(Collection<? extends Float> that){
      super(that);
    }
    public UncheckedStack(OmniCollection.OfRef<? extends Float> that){
      super(that);
    }
    public UncheckedStack(OmniCollection.OfBoolean that){
      super(that);
    }
    public UncheckedStack(OmniCollection.FloatOutput<?> that){
      super(that);
    }
    public UncheckedStack(OmniCollection.OfByte that){
      super(that);
    }
    public UncheckedStack(OmniCollection.OfShort that){
      super(that);
    }  
    public UncheckedStack(OmniCollection.OfInt that){
      super(that);
    }
    public UncheckedStack(OmniCollection.OfLong that){
      super(that);
    }
    public UncheckedStack(OmniCollection.OfFloat that){
      super(that);
    }
    public UncheckedStack(OmniCollection.OfChar that){
      super(that);
    }
    public UncheckedStack(){
      super();
    }
    public UncheckedStack(int initialCapacity){
      super(initialCapacity);
    }
    UncheckedStack(int size,float[] arr){
      super(size,arr);
    }
    @Override public Object clone(){
      final float[] copy;
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(this.arr,0,copy=new float[size],0,size);
      }else{
        copy=OmniArray.OfFloat.DEFAULT_ARR;
      }
      return new UncheckedStack(size,copy);
    }
    int uncheckedToString(int size,byte[] buffer){
      return OmniArray.OfFloat.descendingToString(this.arr,0,size-1,buffer,1);
    }
    void uncheckedToString(int size,ToStringUtil.OmniStringBuilderByte builder){
      OmniArray.OfFloat.descendingToString(this.arr,0,size-1,builder);
    }
    @Override public int search(boolean val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val){
              return OmniArray.OfFloat.uncheckedsearchBits(this.arr,size,TypeUtil.FLT_TRUE_BITS);
            }
            return OmniArray.OfFloat.uncheckedsearch0(this.arr,size);
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
              if(TypeUtil.checkCastToFloat(val))
              {
                return OmniArray.OfFloat.uncheckedsearchBits(this.arr,size,Float.floatToRawIntBits(val));
              }
            }else{
              return OmniArray.OfFloat.uncheckedsearch0(this.arr,size);
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
              if(TypeUtil.checkCastToFloat(val)){
                return OmniArray.OfFloat.uncheckedsearchBits(this.arr,size,Float.floatToRawIntBits(val));
              }
            }else{
              return OmniArray.OfFloat.uncheckedsearch0(this.arr,size);
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
              return OmniArray.OfFloat.uncheckedsearchBits(this.arr,size,Float.floatToRawIntBits(val));
            }
            return OmniArray.OfFloat.uncheckedsearchNaN(this.arr,size);
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
            final float v;
            if(val==(v=(float)val)){
              return OmniArray.OfFloat.uncheckedsearchBits(this.arr,size,Float.floatToRawIntBits(v));
            }else if(v!=v){
              return OmniArray.OfFloat.uncheckedsearchNaN(this.arr,size);
            }
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
              if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return OmniArray.OfFloat.uncheckedsearchBits(this.arr,size,Float.floatToRawIntBits(f));
                }
                return OmniArray.OfFloat.uncheckedsearchNaN(this.arr,size);
              }else if(val instanceof Double){
                final double d;
                final float f;
                if((d=(double)val)==(f=(float)d)){
                  return OmniArray.OfFloat.uncheckedsearchBits(this.arr,size,Float.floatToRawIntBits(f));
                }else if(f!=f){
                  return OmniArray.OfFloat.uncheckedsearchNaN(this.arr,size);
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Integer){
                final int i;
                if((i=(int)val)!=0){
                  if(!TypeUtil.checkCastToFloat(i)){
                    break returnFalse;
                  }
                  return OmniArray.OfFloat.uncheckedsearchBits(this.arr,size,Float.floatToRawIntBits(i));
                }
                return OmniArray.OfFloat.uncheckedsearch0(this.arr,size);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToFloat(l)){
                    break returnFalse;
                  }
                  return OmniArray.OfFloat.uncheckedsearchBits(this.arr,size,Float.floatToRawIntBits(l));
                }
                return OmniArray.OfFloat.uncheckedsearch0(this.arr,size);
              }else if(val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).shortValue())!=0){
                  return OmniArray.OfFloat.uncheckedsearchBits(this.arr,size,Float.floatToRawIntBits(i));
                }
                return OmniArray.OfFloat.uncheckedsearch0(this.arr,size);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return OmniArray.OfFloat.uncheckedsearchBits(this.arr,size,Float.floatToRawIntBits(i));
                }
                return OmniArray.OfFloat.uncheckedsearch0(this.arr,size);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return OmniArray.OfFloat.uncheckedsearchBits(this.arr,size,TypeUtil.FLT_TRUE_BITS);
                }
                return OmniArray.OfFloat.uncheckedsearch0(this.arr,size);
              }else{
                break returnFalse;
              }
            }
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
            if(val!=0){
              return OmniArray.OfFloat.uncheckedsearchBits(this.arr,size,Float.floatToRawIntBits(val));
            }
            return OmniArray.OfFloat.uncheckedsearch0(this.arr,size);
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
            if(val!=0){
              return OmniArray.OfFloat.uncheckedsearchBits(this.arr,size,Float.floatToRawIntBits(val));
            }
            return OmniArray.OfFloat.uncheckedsearch0(this.arr,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override boolean uncheckedremoveValBits(int size
    ,int bits
    ){
      final var arr=this.arr;
      for(int index=--size;;--index){
        if(
        bits==Float.floatToRawIntBits(arr[index])
        )
        {
          OmniArray.OfFloat.removeIndexAndPullDown(arr,index,size);
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
        Float.isNaN(arr[index])
        )
        {
          OmniArray.OfFloat.removeIndexAndPullDown(arr,index,size);
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
          OmniArray.OfFloat.removeIndexAndPullDown(arr,index,size);
          this.size=size;
          return true;
        }else if(index==0){
          return false;
        }
      }
    }
    @Override void uncheckedForEach(int size,FloatConsumer action){
      {
        OmniArray.OfFloat.descendingForEach(this.arr,0,size-1,action);
      }
    }
    private static class Itr
      extends AbstractFloatItr
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
      @Override public float nextFloat(){
        return (float)parent.arr[--cursor];
      }
      @Override public void remove(){
        final UncheckedStack root;
        OmniArray.OfFloat.removeIndexAndPullDown((root=this.parent).arr,this.cursor,--root.size);
      }
      @Override public void forEachRemaining(FloatConsumer action){
        final int cursor;
        if((cursor=this.cursor)>0){
          OmniArray.OfFloat.descendingForEach(parent.arr,0,cursor-1,action);
          this.cursor=0;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Float> action){
        final int cursor;
        if((cursor=this.cursor)>0){
          OmniArray.OfFloat.descendingForEach(parent.arr,0,cursor-1,action::accept);
          this.cursor=0;
        }
      }
    }
    @Override public OmniIterator.OfFloat iterator(){
      return new Itr(this);
    }
    @Override public void push(Float val){
      push((float)val);
    }
    @Override void uncheckedCopyInto(float[] dst,int length){
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override void uncheckedCopyInto(Object[] dst,int length){
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override void uncheckedCopyInto(Float[] dst,int length){
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override void uncheckedCopyInto(double[] dst,int length){
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override public Float pop(){
      return popFloat();
    }
    @Override public float pollFloat(){
      int size;
      if((size=this.size)!=0){
        final var ret=(float)(arr[--size]);
        this.size=size;
        return ret;
      }
      return Float.NaN;
    }
    @Override public float peekFloat(){
      final int size;
      if((size=this.size)!=0){
        return (float)(arr[size-1]);
      }
      return Float.NaN;
    }
    @Override public Float poll(){
      int size;
      if((size=this.size)!=0){
        final var ret=(Float)(arr[--size]);
        this.size=size;
        return ret;
      }
      return null;
    }
    @Override public Float peek(){
      final int size;
      if((size=this.size)!=0){
        return (Float)(arr[size-1]);
      }
      return null;
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
  }
  public
    static class UncheckedList
      extends FloatArrSeq
      implements FloatListDefault,Cloneable,RandomAccess
  {
    private static final long serialVersionUID=1L;
    public UncheckedList(Collection<? extends Float> that){
      super(that);
    }
    public UncheckedList(OmniCollection.OfRef<? extends Float> that){
      super(that);
    }
    public UncheckedList(OmniCollection.OfBoolean that){
      super(that);
    }
    public UncheckedList(OmniCollection.FloatOutput<?> that){
      super(that);
    }
    public UncheckedList(OmniCollection.OfByte that){
      super(that);
    }
    public UncheckedList(OmniCollection.OfShort that){
      super(that);
    }  
    public UncheckedList(OmniCollection.OfInt that){
      super(that);
    }
    public UncheckedList(OmniCollection.OfLong that){
      super(that);
    }
    public UncheckedList(OmniCollection.OfFloat that){
      super(that);
    }
    public UncheckedList(OmniCollection.OfChar that){
      super(that);
    }
    public UncheckedList(){
      super();
    }
    public UncheckedList(int initialCapacity){
      super(initialCapacity);
    }
    UncheckedList(int size,float[] arr){
      super(size,arr);
    }
    boolean isEqualTo(ListIterator<?> itr,int thisOffset,int thisBound){
      Object val;
      if(itr.hasNext() && (val=itr.next()) instanceof Float){
        for(final var arr=this.arr;TypeUtil.floatEquals((float)val,arr[thisOffset]);){
          if(!itr.hasNext()){
            return thisOffset+1==thisBound;
          }
          if(++thisOffset>=thisBound || !((val=itr.next()) instanceof Float)){
            break;
          }
        }
      }
      return false;
    }
    private boolean isEqualToHelper(int thisOffset,int thisBound,float[] thatArr,int thatOffset){
      for(final var thisArr=this.arr;TypeUtil.floatEquals(thisArr[thisOffset],thatArr[thatOffset]);++thatOffset){
        if(++thisOffset==thisBound){
          return true;
        }  
      }
      return false;
    }
    private boolean isEqualTo(int rootOffset,int size,OmniList.OfRef<?> list){
      if(list instanceof RefArrSeq.UncheckedList){
        final RefArrSeq.UncheckedList<?> that;
        return size==(that=(RefArrSeq.UncheckedList<?>)list).size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,that.arr,0);
      }else if(list instanceof RefArrSeq.UncheckedSubList){
        final RefArrSeq.UncheckedSubList<?> subList;
        return size==(subList=(RefArrSeq.UncheckedSubList<?>)list).size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,subList.root.arr,subList.rootOffset);
      }else if(list instanceof RefArrSeq.CheckedSubList){
        final RefArrSeq.CheckedSubList<?> subList;
        final RefArrSeq.CheckedList<?> thatRoot;
        CheckedCollection.checkModCount((subList=(RefArrSeq.CheckedSubList<?>)list).modCount,(thatRoot=subList.root).modCount);
        return size==subList.size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,thatRoot.arr,subList.rootOffset);
      }else{
        //must be RefDblLnkSeq
        final RefDblLnkSeq<?> dls;
        if((dls=(RefDblLnkSeq<?>)list) instanceof RefDblLnkSeq.CheckedSubList){
          final RefDblLnkSeq.CheckedSubList<?> subList;
          CheckedCollection.checkModCount((subList=(RefDblLnkSeq.CheckedSubList<?>)dls).modCount,subList.root.modCount);
        }
        return size==dls.size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,dls.head);
      }
    }
    private boolean isEqualTo(int rootOffset,int size,OmniList.OfFloat list){
      if(list instanceof FloatArrSeq.UncheckedList){
        final FloatArrSeq.UncheckedList uncheckedList;
        return (uncheckedList=(FloatArrSeq.UncheckedList)list).size==size && ((uncheckedList==this) || this.isEqualToHelper(rootOffset,rootOffset+size,uncheckedList.arr,0));
      }else if(list instanceof FloatArrSeq.UncheckedSubList){
        final FloatArrSeq.UncheckedSubList subList;
        final int thatOffset=(subList=(FloatArrSeq.UncheckedSubList)list).rootOffset;
        final FloatArrSeq.UncheckedList thatRoot;
        return size==subList.size && (((thatRoot=subList.root)==this && thatOffset==rootOffset) || this.isEqualToHelper(rootOffset,rootOffset+size,thatRoot.arr,thatOffset));
      }else if(list instanceof FloatArrSeq.CheckedSubList){
        final FloatArrSeq.CheckedSubList subList;
        final FloatArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount(((subList=(FloatArrSeq.CheckedSubList)list)).modCount,(thatRoot=subList.root).modCount);
        return subList.size==size && this.isEqualToHelper(rootOffset,rootOffset+size,thatRoot.arr,subList.rootOffset);
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
    private boolean isEqualTo(int size,OmniList.OfRef<?> list){
      if(list instanceof RefArrSeq.UncheckedList){
        final RefArrSeq.UncheckedList<?> that;
        return size==(that=(RefArrSeq.UncheckedList<?>)list).size && SequenceEqualityUtil.isEqualTo(this.arr,0,size,that.arr,0);
      }else if(list instanceof RefDblLnkSeq){
        final RefDblLnkSeq<?> dls;
        if((dls=(RefDblLnkSeq<?>)list) instanceof RefDblLnkSeq.CheckedSubList){
          final RefDblLnkSeq.CheckedSubList<?> subList;
          CheckedCollection.checkModCount((subList=(RefDblLnkSeq.CheckedSubList<?>)dls).modCount,subList.root.modCount);
        }
        return dls.size==size && SequenceEqualityUtil.isEqualTo(this.arr,0,size,dls.head);
      }else if(list instanceof RefArrSeq.UncheckedSubList){
        final RefArrSeq.UncheckedSubList<?> subList;
        return size==(subList=(RefArrSeq.UncheckedSubList<?>)list).size && SequenceEqualityUtil.isEqualTo(this.arr,0,size,subList.root.arr,subList.rootOffset);
      }else{
        //must be RefArrSeq.CheckedSubList
        final RefArrSeq.CheckedSubList<?> subList;
        final RefArrSeq.CheckedList<?> thatRoot;
        CheckedCollection.checkModCount((subList=(RefArrSeq.CheckedSubList<?>)list).modCount,(thatRoot=subList.root).modCount);
        return size==subList.size && SequenceEqualityUtil.isEqualTo(this.arr,0,size,thatRoot.arr,subList.rootOffset);
      }
    }
    private boolean isEqualTo(int size,OmniList.OfFloat list){
      if(list instanceof FloatArrSeq.UncheckedList){
        final FloatArrSeq.UncheckedList that;
        return size==(that=(FloatArrSeq.UncheckedList)list).size && this.isEqualToHelper(0,size,that.arr,0);
      }else if(list instanceof FloatDblLnkSeq){
        final FloatDblLnkSeq dls;
        if((dls=(FloatDblLnkSeq)list) instanceof FloatDblLnkSeq.CheckedSubList){
          final FloatDblLnkSeq.CheckedSubList subList;
          CheckedCollection.checkModCount((subList=(FloatDblLnkSeq.CheckedSubList)dls).modCount,subList.root.modCount);
        }
        return size==dls.size && SequenceEqualityUtil.isEqualTo(this.arr,0,size,dls.head);
      }else if(list instanceof FloatArrSeq.UncheckedSubList){
        final FloatArrSeq.UncheckedSubList subList;
        final FloatArrSeq.UncheckedList thatRoot;
        return (subList=(FloatArrSeq.UncheckedSubList)list).size==size && ((thatRoot=subList.root)==this || this.isEqualToHelper(0,size,thatRoot.arr,subList.rootOffset));
      }else{
        //must be FloatArrSeq.CheckedSubList
        final FloatArrSeq.CheckedSubList subList;
        final FloatArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount((subList=(FloatArrSeq.CheckedSubList)list).modCount,(thatRoot=subList.root).modCount);
        return size==subList.size && this.isEqualToHelper(0,size,thatRoot.arr,subList.rootOffset);
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
          if(list instanceof OmniList.OfFloat){
            return this.isEqualTo(size,(OmniList.OfFloat)list);
          }else if(list instanceof OmniList.OfRef){
            return this.isEqualTo(size,(OmniList.OfRef<?>)list);
          }
        }else{
          return this.isEqualTo(list.listIterator(),0,size);
        }
      }
      return false;
    }
    @Override public Object clone(){
      final float[] copy;
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(this.arr,0,copy=new float[size],0,size);
      }else{
        copy=OmniArray.OfFloat.DEFAULT_ARR;
      }
      return new UncheckedList(size,copy);
    }
    int uncheckedToString(int size,byte[] buffer){
      return OmniArray.OfFloat.ascendingToString(this.arr,0,size-1,buffer,1);
    }
    void uncheckedToString(int size,ToStringUtil.OmniStringBuilderByte builder){
      OmniArray.OfFloat.ascendingToString(this.arr,0,size-1,builder);
    }
    @Override public int hashCode(){
      final int size;
      if((size=this.size)!=0){
        return OmniArray.OfFloat.ascendingSeqHashCode(this.arr,0,size-1);
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
              return OmniArray.OfFloat.uncheckedindexOfBits(this.arr,size,TypeUtil.FLT_TRUE_BITS);
            }
            return OmniArray.OfFloat.uncheckedindexOf0(this.arr,size);
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
              if(TypeUtil.checkCastToFloat(val))
              {
                return OmniArray.OfFloat.uncheckedindexOfBits(this.arr,size,Float.floatToRawIntBits(val));
              }
            }else{
              return OmniArray.OfFloat.uncheckedindexOf0(this.arr,size);
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
              if(TypeUtil.checkCastToFloat(val)){
                return OmniArray.OfFloat.uncheckedindexOfBits(this.arr,size,Float.floatToRawIntBits(val));
              }
            }else{
              return OmniArray.OfFloat.uncheckedindexOf0(this.arr,size);
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
              return OmniArray.OfFloat.uncheckedindexOfBits(this.arr,size,Float.floatToRawIntBits(val));
            }
            return OmniArray.OfFloat.uncheckedindexOfNaN(this.arr,size);
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
            final float v;
            if(val==(v=(float)val)){
              return OmniArray.OfFloat.uncheckedindexOfBits(this.arr,size,Float.floatToRawIntBits(v));
            }else if(v!=v){
              return OmniArray.OfFloat.uncheckedindexOfNaN(this.arr,size);
            }
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
              if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return OmniArray.OfFloat.uncheckedindexOfBits(this.arr,size,Float.floatToRawIntBits(f));
                }
                return OmniArray.OfFloat.uncheckedindexOfNaN(this.arr,size);
              }else if(val instanceof Double){
                final double d;
                final float f;
                if((d=(double)val)==(f=(float)d)){
                  return OmniArray.OfFloat.uncheckedindexOfBits(this.arr,size,Float.floatToRawIntBits(f));
                }else if(f!=f){
                  return OmniArray.OfFloat.uncheckedindexOfNaN(this.arr,size);
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Integer){
                final int i;
                if((i=(int)val)!=0){
                  if(!TypeUtil.checkCastToFloat(i)){
                    break returnFalse;
                  }
                  return OmniArray.OfFloat.uncheckedindexOfBits(this.arr,size,Float.floatToRawIntBits(i));
                }
                return OmniArray.OfFloat.uncheckedindexOf0(this.arr,size);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToFloat(l)){
                    break returnFalse;
                  }
                  return OmniArray.OfFloat.uncheckedindexOfBits(this.arr,size,Float.floatToRawIntBits(l));
                }
                return OmniArray.OfFloat.uncheckedindexOf0(this.arr,size);
              }else if(val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).shortValue())!=0){
                  return OmniArray.OfFloat.uncheckedindexOfBits(this.arr,size,Float.floatToRawIntBits(i));
                }
                return OmniArray.OfFloat.uncheckedindexOf0(this.arr,size);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return OmniArray.OfFloat.uncheckedindexOfBits(this.arr,size,Float.floatToRawIntBits(i));
                }
                return OmniArray.OfFloat.uncheckedindexOf0(this.arr,size);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return OmniArray.OfFloat.uncheckedindexOfBits(this.arr,size,TypeUtil.FLT_TRUE_BITS);
                }
                return OmniArray.OfFloat.uncheckedindexOf0(this.arr,size);
              }else{
                break returnFalse;
              }
            }
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
            if(val!=0){
              return OmniArray.OfFloat.uncheckedindexOfBits(this.arr,size,Float.floatToRawIntBits(val));
            }
            return OmniArray.OfFloat.uncheckedindexOf0(this.arr,size);
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
            if(val!=0){
              return OmniArray.OfFloat.uncheckedindexOfBits(this.arr,size,Float.floatToRawIntBits(val));
            }
            return OmniArray.OfFloat.uncheckedindexOf0(this.arr,size);
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
              return OmniArray.OfFloat.uncheckedlastIndexOfBits(this.arr,size,TypeUtil.FLT_TRUE_BITS);
            }
            return OmniArray.OfFloat.uncheckedlastIndexOf0(this.arr,size);
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
              if(TypeUtil.checkCastToFloat(val))
              {
                return OmniArray.OfFloat.uncheckedlastIndexOfBits(this.arr,size,Float.floatToRawIntBits(val));
              }
            }else{
              return OmniArray.OfFloat.uncheckedlastIndexOf0(this.arr,size);
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
              if(TypeUtil.checkCastToFloat(val)){
                return OmniArray.OfFloat.uncheckedlastIndexOfBits(this.arr,size,Float.floatToRawIntBits(val));
              }
            }else{
              return OmniArray.OfFloat.uncheckedlastIndexOf0(this.arr,size);
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
              return OmniArray.OfFloat.uncheckedlastIndexOfBits(this.arr,size,Float.floatToRawIntBits(val));
            }
            return OmniArray.OfFloat.uncheckedlastIndexOfNaN(this.arr,size);
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
            final float v;
            if(val==(v=(float)val)){
              return OmniArray.OfFloat.uncheckedlastIndexOfBits(this.arr,size,Float.floatToRawIntBits(v));
            }else if(v!=v){
              return OmniArray.OfFloat.uncheckedlastIndexOfNaN(this.arr,size);
            }
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
              if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return OmniArray.OfFloat.uncheckedlastIndexOfBits(this.arr,size,Float.floatToRawIntBits(f));
                }
                return OmniArray.OfFloat.uncheckedlastIndexOfNaN(this.arr,size);
              }else if(val instanceof Double){
                final double d;
                final float f;
                if((d=(double)val)==(f=(float)d)){
                  return OmniArray.OfFloat.uncheckedlastIndexOfBits(this.arr,size,Float.floatToRawIntBits(f));
                }else if(f!=f){
                  return OmniArray.OfFloat.uncheckedlastIndexOfNaN(this.arr,size);
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Integer){
                final int i;
                if((i=(int)val)!=0){
                  if(!TypeUtil.checkCastToFloat(i)){
                    break returnFalse;
                  }
                  return OmniArray.OfFloat.uncheckedlastIndexOfBits(this.arr,size,Float.floatToRawIntBits(i));
                }
                return OmniArray.OfFloat.uncheckedlastIndexOf0(this.arr,size);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToFloat(l)){
                    break returnFalse;
                  }
                  return OmniArray.OfFloat.uncheckedlastIndexOfBits(this.arr,size,Float.floatToRawIntBits(l));
                }
                return OmniArray.OfFloat.uncheckedlastIndexOf0(this.arr,size);
              }else if(val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).shortValue())!=0){
                  return OmniArray.OfFloat.uncheckedlastIndexOfBits(this.arr,size,Float.floatToRawIntBits(i));
                }
                return OmniArray.OfFloat.uncheckedlastIndexOf0(this.arr,size);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return OmniArray.OfFloat.uncheckedlastIndexOfBits(this.arr,size,Float.floatToRawIntBits(i));
                }
                return OmniArray.OfFloat.uncheckedlastIndexOf0(this.arr,size);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return OmniArray.OfFloat.uncheckedlastIndexOfBits(this.arr,size,TypeUtil.FLT_TRUE_BITS);
                }
                return OmniArray.OfFloat.uncheckedlastIndexOf0(this.arr,size);
              }else{
                break returnFalse;
              }
            }
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
            if(val!=0){
              return OmniArray.OfFloat.uncheckedlastIndexOfBits(this.arr,size,Float.floatToRawIntBits(val));
            }
            return OmniArray.OfFloat.uncheckedlastIndexOf0(this.arr,size);
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
            if(val!=0){
              return OmniArray.OfFloat.uncheckedlastIndexOfBits(this.arr,size,Float.floatToRawIntBits(val));
            }
            return OmniArray.OfFloat.uncheckedlastIndexOf0(this.arr,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override boolean uncheckedremoveValBits(int size
    ,int bits
    ){
      final var arr=this.arr;
      for(int index=0;;){
        if(
        bits==Float.floatToRawIntBits(arr[index])
        )
        {
          OmniArray.OfFloat.removeIndexAndPullDown(arr,index,--size);
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
        Float.isNaN(arr[index])
        )
        {
          OmniArray.OfFloat.removeIndexAndPullDown(arr,index,--size);
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
          OmniArray.OfFloat.removeIndexAndPullDown(arr,index,--size);
          this.size=size;
          return true;
        }else if(++index==size){
          return false;
        }
      }
    }
    @Override void uncheckedForEach(int size,FloatConsumer action){
      {
        OmniArray.OfFloat.ascendingForEach(this.arr,0,size-1,action);
      }
    }
    private static class Itr
      extends AbstractFloatItr
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
      @Override public float nextFloat(){
        return (float)parent.arr[cursor++];
      }
      @Override public void remove(){
        final UncheckedList root;
        OmniArray.OfFloat.removeIndexAndPullDown((root=this.parent).arr,--this.cursor,--root.size);
      }
      @Override public void forEachRemaining(FloatConsumer action){
        final int cursor,bound;
        final UncheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size)){
          OmniArray.OfFloat.ascendingForEach(parent.arr,cursor,bound-1,action);
          this.cursor=bound;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Float> action){
        final int cursor,bound;
        final UncheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size)){
          OmniArray.OfFloat.ascendingForEach(parent.arr,cursor,bound-1,action::accept);
          this.cursor=bound;
        }
      }
    }
    @Override public OmniIterator.OfFloat iterator(){
      return new Itr(this);
    }
    private static class ListItr extends Itr implements OmniListIterator.OfFloat{
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
      @Override public float nextFloat(){
        int lastRet;
        this.lastRet=lastRet=this.cursor++;
        return (float)parent.arr[lastRet];
      }
      @Override public void remove(){
        final UncheckedList root;
        final int lastRet;
        OmniArray.OfFloat.removeIndexAndPullDown((root=this.parent).arr,lastRet=this.lastRet,--root.size);
        this.cursor=lastRet;
      }
      @Override public void forEachRemaining(FloatConsumer action){
        int cursor;
        final int bound;
        final UncheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size)){
          OmniArray.OfFloat.ascendingForEach(parent.arr,cursor,cursor=bound-1,action);
          this.lastRet=cursor;
          this.cursor=bound;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Float> action){
        int cursor;
        final int bound;
        final UncheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size)){
          OmniArray.OfFloat.ascendingForEach(parent.arr,cursor,cursor=bound-1,action::accept);
          this.lastRet=cursor;
          this.cursor=bound;
        }
      }
      @Override public float previousFloat(){
        final int lastRet;
        this.lastRet=lastRet=--this.cursor;
        return (float)parent.arr[lastRet];
      }
      @Override public void set(float val){
        parent.arr[this.lastRet]=val;
      }
      @Override public void add(float val){
        final UncheckedList root;
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
    @Override public OmniListIterator.OfFloat listIterator(){
      return new ListItr(this);
    }
    @Override public OmniListIterator.OfFloat listIterator(int index){
      return new ListItr(this,index);
    }
      void uncheckedInsert(int index,int size,float val){
        final int tailDist;
        if((tailDist=size-index)==0){
          super.uncheckedAppend(size,val);
        }else{
          float[] arr;
          if((arr=this.arr).length==size){
            final float[] tmp;
            ArrCopy.semicheckedCopy(arr,0,tmp=new float[OmniArray.growBy50Pct(size)],0,index);
            ArrCopy.uncheckedCopy(arr,index,tmp,index+1,tailDist);
            this.arr=arr=tmp;
          }else{
            ArrCopy.uncheckedCopy(arr,index,arr,index+1,tailDist);
          }
          arr[index]=val;
          this.size=size+1;
        }
      }
    @Override public void add(int index,float val){
      final int size;
      if((size=this.size)!=0){
        this
        .uncheckedInsert(index,size,val);
      }else{
        super.uncheckedInit(val);
      }
    }
    @Override void uncheckedCopyInto(float[] dst,int length){
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override void uncheckedCopyInto(Object[] dst,int length){
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override void uncheckedCopyInto(Float[] dst,int length){
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override void uncheckedCopyInto(double[] dst,int length){
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override public void put(int index,float val){
      this.arr[index]=val;
    }
    @Override public float getFloat(int index){
      return (float)this.arr[index];
    }
    @Override public float set(int index,float val){
      final float[] arr;
      final var ret=(float)(arr=this.arr)[index];
      arr[index]=val;
      return ret;
    }
    @Override public float removeFloatAt(int index){
      final float[] arr;
      final var ret=(float)(arr=this.arr)[index];
      OmniArray.OfFloat.removeIndexAndPullDown(arr,index,--size);
      return ret;
    }
    @Override public void replaceAll(FloatUnaryOperator operator){
      final int size;
      if((size=this.size)!=0){
        OmniArray.OfFloat.uncheckedReplaceAll(this.arr,0,size,operator);
      }
    }
    @Override
    public void sort(FloatComparator sorter)
    {
      final int size;
      if((size=this.size)>1){
        if(sorter==null){
          FloatSortUtil.uncheckedAscendingSort(this.arr,0,size);
        }else{
          FloatSortUtil.uncheckedStableSort(this.arr,0,size,sorter);
        }
      }
    }
    @Override
    public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)>1){
        FloatSortUtil.uncheckedAscendingSort(this.arr,0,size);
      }
    }
    @Override
    public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1){
        FloatSortUtil.uncheckedDescendingSort(this.arr,0,size);
      }
    }
    @Override public void replaceAll(UnaryOperator<Float> operator){
      final int size;
      if((size=this.size)!=0){
        OmniArray.OfFloat.uncheckedReplaceAll(this.arr,0,size,operator::apply);
      }
    }
    @Override
    public void sort(Comparator<? super Float> sorter)
    {
      final int size;
      if((size=this.size)>1){
        if(sorter==null){
          FloatSortUtil.uncheckedAscendingSort(this.arr,0,size);
        }else{
          FloatSortUtil.uncheckedStableSort(this.arr,0,size,sorter::compare);
        }
      }
    }
    @Override
    public void unstableSort(FloatComparator sorter)
    {
      final int size;
      if((size=this.size)>1){
        if(sorter==null){
          FloatSortUtil.uncheckedAscendingSort(this.arr,0,size);
        }else{
          FloatSortUtil.uncheckedUnstableSort(this.arr,0,size,sorter);
        }
      }
    }
    @Override public OmniList.OfFloat subList(int fromIndex,int toIndex){
      return new UncheckedSubList(this,fromIndex,toIndex-fromIndex);
    }
  }
    static class UncheckedSubList
      extends AbstractOmniCollection<Float>
      implements FloatSubListDefault,Cloneable,RandomAccess
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
    @Override public boolean add(Float val){
      return add((float)val);
    }
    private static class SerializableSubList implements Serializable{
      private static final long serialVersionUID=1L;
      private transient float[] arr;
      private transient int size;
      private transient final int rootOffset;
      private SerializableSubList(float[] arr,int size,int rootOffset
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
          float[] arr;
          OmniArray.OfFloat.readArray(arr=new float[size],0,size-1,ois);
          this.arr=arr;
        }else{
          this.arr=OmniArray.OfFloat.DEFAULT_ARR;
        }
      }
      private void writeObject(ObjectOutputStream oos) throws IOException{
        {
          int size;
          oos.writeInt(size=this.size);
          if(size!=0){
            final int rootOffset;
            OmniArray.OfFloat.writeArray(arr,rootOffset=this.rootOffset,rootOffset+size-1,oos);
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
         final int size;
         if((size=this.size)==0){
           return ((List<?>)val).isEmpty();
         }
         final List<?> list;
         if((list=(List<?>)val) instanceof AbstractOmniCollection){
            if(list instanceof OmniList.OfFloat){
              return root.isEqualTo(this.rootOffset,size,(OmniList.OfFloat)list);
            }else if(list instanceof OmniList.OfRef){
              return root.isEqualTo(this.rootOffset,size,(OmniList.OfRef<?>)list);
            }
         }else{
            final int rootOffset;
            return root.isEqualTo(list.listIterator(),rootOffset=this.rootOffset,size+rootOffset);
         }
      }
      return false;
    }
    @Override public Object clone(){
      final float[] copy;
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(root.arr,rootOffset,copy=new float[size],0,size);
      }else{
        copy=OmniArray.OfFloat.DEFAULT_ARR;
      }
      return new UncheckedList(size,copy);
    }
    @Override public String toString(){
      int size;
      if((size=this.size)!=0){
          final int rootOffset;
          final byte[] buffer;
          if(size<=(OmniArray.MAX_ARR_SIZE/17)){(buffer=new byte[size*17])
            [size=OmniArray.OfFloat.ascendingToString(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,buffer,1)]=(byte)']';
            buffer[0]=(byte)'[';
            return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
          }else{
            final ToStringUtil.OmniStringBuilderByte builder;
            OmniArray.OfFloat.ascendingToString(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
            builder.uncheckedAppendChar((byte)']');
            (buffer=builder.buffer)[0]=(byte)'[';
            return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
          }
      }
      return "[]";
    }
  @Override public int hashCode(){
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return OmniArray.OfFloat.ascendingSeqHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
    }
    return 1;
  }
    @Override public void clear(){
      final int size;
      if((size=this.size)!=0){
        for(var curr=parent;curr!=null;curr.size-=size,curr=curr.parent){}
        final UncheckedList root;
        (root=this.root).size=OmniArray.OfFloat.removeRangeAndPullDown(root.arr,this.rootOffset+size,root.size,size);
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
              return this.uncheckedremoveValBits(size,TypeUtil.FLT_TRUE_BITS);
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
              if(TypeUtil.checkCastToFloat(val))
              {
                return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
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
              if(TypeUtil.checkCastToFloat(val)){
                return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
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
              return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
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
            final float v;
            if(val==(v=(float)val)){
              return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(v));
            }else if(v!=v){
              return this.uncheckedremoveValNaN(size);
            }
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
              if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(f));
                }
                return this.uncheckedremoveValNaN(size);
              }else if(val instanceof Double){
                final double d;
                final float f;
                if((d=(double)val)==(f=(float)d)){
                  return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(f));
                }else if(f!=f){
                  return this.uncheckedremoveValNaN(size);
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Integer){
                final int i;
                if((i=(int)val)!=0){
                  if(!TypeUtil.checkCastToFloat(i)){
                    break returnFalse;
                  }
                  return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(i));
                }
                return this.uncheckedremoveVal0(size);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToFloat(l)){
                    break returnFalse;
                  }
                  return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(l));
                }
                return this.uncheckedremoveVal0(size);
              }else if(val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).shortValue())!=0){
                  return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(i));
                }
                return this.uncheckedremoveVal0(size);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(i));
                }
                return this.uncheckedremoveVal0(size);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return this.uncheckedremoveValBits(size,TypeUtil.FLT_TRUE_BITS);
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
    @Override public boolean removeVal(char val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=0){
              return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
            }
            return this.uncheckedremoveVal0(size);
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
            if(val!=0){
              return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
            }
            return this.uncheckedremoveVal0(size);
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
              return OmniArray.OfFloat.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,TypeUtil.FLT_TRUE_BITS);
            }
            final int rootOffset;
            return OmniArray.OfFloat.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
              if(TypeUtil.checkCastToFloat(val))
              {
                final int rootOffset;
                return OmniArray.OfFloat.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Float.floatToRawIntBits(val));
              }
            }else{
              final int rootOffset;
              return OmniArray.OfFloat.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
              if(TypeUtil.checkCastToFloat(val)){
                final int rootOffset;
                return OmniArray.OfFloat.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Float.floatToRawIntBits(val));
              }
            }else{
              final int rootOffset;
              return OmniArray.OfFloat.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
              return OmniArray.OfFloat.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Float.floatToRawIntBits(val));
            }
            final int rootOffset;
            return OmniArray.OfFloat.uncheckedcontainsNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
            final float v;
            if(val==(v=(float)val)){
              final int rootOffset;
              return OmniArray.OfFloat.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Float.floatToRawIntBits(v));
            }else if(v!=v){
              final int rootOffset;
              return OmniArray.OfFloat.uncheckedcontainsNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
            }
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
              if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   final int rootOffset;
                   return OmniArray.OfFloat.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Float.floatToRawIntBits(f));
                }
                final int rootOffset;
                return OmniArray.OfFloat.uncheckedcontainsNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
              }else if(val instanceof Double){
                final double d;
                final float f;
                if((d=(double)val)==(f=(float)d)){
                  final int rootOffset;
                  return OmniArray.OfFloat.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Float.floatToRawIntBits(f));
                }else if(f!=f){
                  final int rootOffset;
                  return OmniArray.OfFloat.uncheckedcontainsNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Integer){
                final int i;
                if((i=(int)val)!=0){
                  if(!TypeUtil.checkCastToFloat(i)){
                    break returnFalse;
                  }
                  final int rootOffset;
                  return OmniArray.OfFloat.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Float.floatToRawIntBits(i));
                }
                final int rootOffset;
                return OmniArray.OfFloat.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToFloat(l)){
                    break returnFalse;
                  }
                  final int rootOffset;
                  return OmniArray.OfFloat.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Float.floatToRawIntBits(l));
                }
                final int rootOffset;
                return OmniArray.OfFloat.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
              }else if(val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).shortValue())!=0){
                  final int rootOffset;
                  return OmniArray.OfFloat.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Float.floatToRawIntBits(i));
                }
                final int rootOffset;
                return OmniArray.OfFloat.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  final int rootOffset;
                  return OmniArray.OfFloat.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Float.floatToRawIntBits(i));
                }
                final int rootOffset;
                return OmniArray.OfFloat.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  final int rootOffset;
                  return OmniArray.OfFloat.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,TypeUtil.FLT_TRUE_BITS);
                }
                final int rootOffset;
                return OmniArray.OfFloat.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
              }else{
                break returnFalse;
              }
            }
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
            if(val!=0){
              final int rootOffset;
              return OmniArray.OfFloat.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Float.floatToRawIntBits(val));
            }
            final int rootOffset;
            return OmniArray.OfFloat.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
            if(val!=0){
              final int rootOffset;
              return OmniArray.OfFloat.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Float.floatToRawIntBits(val));
            }
            final int rootOffset;
            return OmniArray.OfFloat.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
              return OmniArray.OfFloat.uncheckedindexOfBits(root.arr,this.rootOffset,size,TypeUtil.FLT_TRUE_BITS);
            }
            return OmniArray.OfFloat.uncheckedindexOf0(root.arr,this.rootOffset,size);
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
              if(TypeUtil.checkCastToFloat(val))
              {
                return OmniArray.OfFloat.uncheckedindexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(val));
              }
            }else{
              return OmniArray.OfFloat.uncheckedindexOf0(root.arr,this.rootOffset,size);
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
              if(TypeUtil.checkCastToFloat(val)){
                return OmniArray.OfFloat.uncheckedindexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(val));
              }
            }else{
              return OmniArray.OfFloat.uncheckedindexOf0(root.arr,this.rootOffset,size);
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
              return OmniArray.OfFloat.uncheckedindexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(val));
            }
            return OmniArray.OfFloat.uncheckedindexOfNaN(root.arr,this.rootOffset,size);
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
            final float v;
            if(val==(v=(float)val)){
              return OmniArray.OfFloat.uncheckedindexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(v));
            }else if(v!=v){
              return OmniArray.OfFloat.uncheckedindexOfNaN(root.arr,this.rootOffset,size);
            }
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
              if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return OmniArray.OfFloat.uncheckedindexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(f));
                }
                return OmniArray.OfFloat.uncheckedindexOfNaN(root.arr,this.rootOffset,size);
              }else if(val instanceof Double){
                final double d;
                final float f;
                if((d=(double)val)==(f=(float)d)){
                  return OmniArray.OfFloat.uncheckedindexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(f));
                }else if(f!=f){
                  return OmniArray.OfFloat.uncheckedindexOfNaN(root.arr,this.rootOffset,size);
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Integer){
                final int i;
                if((i=(int)val)!=0){
                  if(!TypeUtil.checkCastToFloat(i)){
                    break returnFalse;
                  }
                  return OmniArray.OfFloat.uncheckedindexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(i));
                }
                return OmniArray.OfFloat.uncheckedindexOf0(root.arr,this.rootOffset,size);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToFloat(l)){
                    break returnFalse;
                  }
                  return OmniArray.OfFloat.uncheckedindexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(l));
                }
                return OmniArray.OfFloat.uncheckedindexOf0(root.arr,this.rootOffset,size);
              }else if(val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).shortValue())!=0){
                  return OmniArray.OfFloat.uncheckedindexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(i));
                }
                return OmniArray.OfFloat.uncheckedindexOf0(root.arr,this.rootOffset,size);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return OmniArray.OfFloat.uncheckedindexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(i));
                }
                return OmniArray.OfFloat.uncheckedindexOf0(root.arr,this.rootOffset,size);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return OmniArray.OfFloat.uncheckedindexOfBits(root.arr,this.rootOffset,size,TypeUtil.FLT_TRUE_BITS);
                }
                return OmniArray.OfFloat.uncheckedindexOf0(root.arr,this.rootOffset,size);
              }else{
                break returnFalse;
              }
            }
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
            if(val!=0){
              return OmniArray.OfFloat.uncheckedindexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(val));
            }
            return OmniArray.OfFloat.uncheckedindexOf0(root.arr,this.rootOffset,size);
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
            if(val!=0){
              return OmniArray.OfFloat.uncheckedindexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(val));
            }
            return OmniArray.OfFloat.uncheckedindexOf0(root.arr,this.rootOffset,size);
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
              return OmniArray.OfFloat.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,TypeUtil.FLT_TRUE_BITS);
            }
            return OmniArray.OfFloat.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
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
              if(TypeUtil.checkCastToFloat(val))
              {
                return OmniArray.OfFloat.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(val));
              }
            }else{
              return OmniArray.OfFloat.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
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
              if(TypeUtil.checkCastToFloat(val)){
                return OmniArray.OfFloat.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(val));
              }
            }else{
              return OmniArray.OfFloat.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
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
              return OmniArray.OfFloat.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(val));
            }
            return OmniArray.OfFloat.uncheckedlastIndexOfNaN(root.arr,this.rootOffset,size);
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
            final float v;
            if(val==(v=(float)val)){
              return OmniArray.OfFloat.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(v));
            }else if(v!=v){
              return OmniArray.OfFloat.uncheckedlastIndexOfNaN(root.arr,this.rootOffset,size);
            }
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
              if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return OmniArray.OfFloat.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(f));
                }
                return OmniArray.OfFloat.uncheckedlastIndexOfNaN(root.arr,this.rootOffset,size);
              }else if(val instanceof Double){
                final double d;
                final float f;
                if((d=(double)val)==(f=(float)d)){
                  return OmniArray.OfFloat.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(f));
                }else if(f!=f){
                  return OmniArray.OfFloat.uncheckedlastIndexOfNaN(root.arr,this.rootOffset,size);
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Integer){
                final int i;
                if((i=(int)val)!=0){
                  if(!TypeUtil.checkCastToFloat(i)){
                    break returnFalse;
                  }
                  return OmniArray.OfFloat.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(i));
                }
                return OmniArray.OfFloat.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToFloat(l)){
                    break returnFalse;
                  }
                  return OmniArray.OfFloat.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(l));
                }
                return OmniArray.OfFloat.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
              }else if(val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).shortValue())!=0){
                  return OmniArray.OfFloat.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(i));
                }
                return OmniArray.OfFloat.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return OmniArray.OfFloat.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(i));
                }
                return OmniArray.OfFloat.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return OmniArray.OfFloat.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,TypeUtil.FLT_TRUE_BITS);
                }
                return OmniArray.OfFloat.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
              }else{
                break returnFalse;
              }
            }
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
            if(val!=0){
              return OmniArray.OfFloat.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(val));
            }
            return OmniArray.OfFloat.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
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
            if(val!=0){
              return OmniArray.OfFloat.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(val));
            }
            return OmniArray.OfFloat.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    private boolean uncheckedremoveValBits(int size
    ,int bits
    ){
      final UncheckedList root;
      final var arr=(root=this.root).arr;
      for(int index=this.rootOffset,bound=index+(--size);;++index){
        if(
        bits==Float.floatToRawIntBits(arr[index])
        )
        {
          for(var curr=parent;curr!=null;--curr.size,curr=curr.parent){}
          OmniArray.OfFloat.removeIndexAndPullDown(arr,index,--root.size);
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
        Float.isNaN(arr[index])
        )
        {
          for(var curr=parent;curr!=null;--curr.size,curr=curr.parent){}
          OmniArray.OfFloat.removeIndexAndPullDown(arr,index,--root.size);
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
          OmniArray.OfFloat.removeIndexAndPullDown(arr,index,--root.size);
          this.size=size;
          return true;
        }else if(index==bound){
          return false;
        }
      }
    }
    @Override public void forEach(FloatConsumer action){
      {
        final int size;
        if((size=this.size)!=0){
          final int rootOffset;
          OmniArray.OfFloat.ascendingForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,action);
        }
      }
    }
    @Override public void forEach(Consumer<? super Float> action){
      {
        final int size;
        if((size=this.size)!=0){
          final int rootOffset;
          OmniArray.OfFloat.ascendingForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,action::accept);
        }
      }
    }
    private static class Itr
      extends AbstractFloatItr
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
      @Override public float nextFloat(){
        return (float)parent.root.arr[cursor++];
      }
      @Override public void remove(){
        UncheckedSubList parent;
        final UncheckedList root;
        OmniArray.OfFloat.removeIndexAndPullDown((root=(parent=this.parent).root).arr,--this.cursor,--root.size);
        do{
          --parent.size;
        }while((parent=parent.parent)!=null);
      }
      @Override public void forEachRemaining(FloatConsumer action){
        final int cursor,bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size)){
          OmniArray.OfFloat.ascendingForEach(parent.root.arr,cursor,bound-1,action);
          this.cursor=bound;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Float> action){
        final int cursor,bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size)){
          OmniArray.OfFloat.ascendingForEach(parent.root.arr,cursor,bound-1,action::accept);
          this.cursor=bound;
        }
      }
    }
    @Override public OmniIterator.OfFloat iterator(){
      return new Itr(this);
    }
    private static class ListItr extends Itr implements OmniListIterator.OfFloat{
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
      @Override public float nextFloat(){
        int lastRet;
        this.lastRet=lastRet=this.cursor++;
        return (float)parent.root.arr[lastRet];
      }
      @Override public void remove(){
        UncheckedSubList parent;
        final UncheckedList root;
        final int lastRet;
        OmniArray.OfFloat.removeIndexAndPullDown((root=(parent=this.parent).root).arr,lastRet=this.lastRet,--root.size);
        this.cursor=lastRet;
        do{
          --parent.size;
        }while((parent=parent.parent)!=null);
      }
      @Override public void forEachRemaining(FloatConsumer action){
        int cursor;
        final int bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size)){
          OmniArray.OfFloat.ascendingForEach(parent.root.arr,cursor,cursor=bound-1,action);
          this.lastRet=cursor;
          this.cursor=bound;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Float> action){
        int cursor;
        final int bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size)){
          OmniArray.OfFloat.ascendingForEach(parent.root.arr,cursor,cursor=bound-1,action::accept);
          this.lastRet=cursor;
          this.cursor=bound;
        }
      }
      @Override public float previousFloat(){
        final int lastRet;
        this.lastRet=lastRet=--this.cursor;
        return (float)parent.root.arr[lastRet];
      }
      @Override public void set(float val){
        parent.root.arr[this.lastRet]=val;
      }
      @Override public void add(float val){
        final UncheckedList root;
        final int rootSize;
        UncheckedSubList parent;
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
    @Override public OmniListIterator.OfFloat listIterator(){
      return new ListItr(this);
    }
    @Override public OmniListIterator.OfFloat listIterator(int index){
      return new ListItr(this,index+this.rootOffset);
    }
    @Override public boolean add(float val){
      for(var curr=parent;curr!=null;++curr.size,curr=curr.parent){}
      final UncheckedList root;
      final int rootSize;
      if((rootSize=(root=this.root).size)!=0){
        root.uncheckedInsert(this.rootOffset+(this.size++),rootSize,val);
      }else{
        root.uncheckedInit(val);
        ++this.size;
      }
      return true;
    }
    @Override public void add(int index,float val){
      for(var curr=parent;curr!=null;++curr.size,curr=curr.parent){}
      ++this.size;
      final UncheckedList root;
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
    @Override public float[] toFloatArray(){
      final int size;
      if((size=this.size)!=0){
        final float[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new float[size],0,size);
        return dst;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override public Float[] toArray(){
      final int size;
      if((size=this.size)!=0){
        final Float[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new Float[size],0,size);
        return dst;
      }
      return OmniArray.OfFloat.DEFAULT_BOXED_ARR;
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
    @Override public void put(int index,float val){
      root.arr[index+this.rootOffset]=val;
    }
    @Override public float getFloat(int index){
      return (float)root.arr[index+this.rootOffset];
    }
    @Override public float set(int index,float val){
      final float[] arr;
      final var ret=(float)(arr=root.arr)[index+=this.rootOffset];
      arr[index]=val;
      return ret;
    }
    @Override public float removeFloatAt(int index){
      final float[] arr;
      for(var curr=parent;curr!=null;--curr.size,curr=curr.parent){}
      final UncheckedList root;
      final var ret=(float)(arr=(root=this.root).arr)[index+=this.rootOffset];
      OmniArray.OfFloat.removeIndexAndPullDown(arr,index,--root.size);
      this.size=size-1;
      return ret;
    }
    @Override public boolean removeIf(FloatPredicate filter){
      final int size;
      if((size=this.size)!=0){
        {
          final float[] arr;
          final int numRemoved;
          int rootOffset;
          final UncheckedList root;
          if((numRemoved=uncheckedRemoveIfImpl(arr=(root=this.root).arr,rootOffset=this.rootOffset,rootOffset+=size,filter))!=0){
            for(var curr=parent;curr!=null;curr.size-=numRemoved,curr=curr.parent){}
            root.size=OmniArray.OfFloat.removeRangeAndPullDown(arr,rootOffset,root.size,numRemoved);
            this.size=size-numRemoved;
            return true;
          }
        }
      }
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Float> filter){
      final int size;
      if((size=this.size)!=0){
        {
          final float[] arr;
          final int numRemoved;
          int rootOffset;
          final UncheckedList root;
          if((numRemoved=uncheckedRemoveIfImpl(arr=(root=this.root).arr,rootOffset=this.rootOffset,rootOffset+=size,filter::test))!=0){
            for(var curr=parent;curr!=null;curr.size-=numRemoved,curr=curr.parent){}
            root.size=OmniArray.OfFloat.removeRangeAndPullDown(arr,rootOffset,root.size,numRemoved);
            this.size=size-numRemoved;
            return true;
          }
        }
      }
      return false;
    }
    @Override public void replaceAll(FloatUnaryOperator operator){
      final int size;
      if((size=this.size)!=0){
        final int rootOffset;
        OmniArray.OfFloat.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);  
      }
    }
    @Override
    public void sort(FloatComparator sorter)
    {
      final int size;
      if((size=this.size)>1){
        final int rootOffset;
        if(sorter==null){
          FloatSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }else{
          FloatSortUtil.uncheckedStableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter);
        }
      }
    }
    @Override
    public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)>1){
        final int rootOffset;
        FloatSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }
    }
    @Override
    public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1){
        final int rootOffset;
        FloatSortUtil.uncheckedDescendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }
    }
    @Override public void replaceAll(UnaryOperator<Float> operator){
      final int size;
      if((size=this.size)!=0){
        final int rootOffset;
        OmniArray.OfFloat.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator::apply);  
      }
    }
    @Override
    public void sort(Comparator<? super Float> sorter)
    {
      final int size;
      if((size=this.size)>1){
        final int rootOffset;
        if(sorter==null){
          FloatSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }else{
          FloatSortUtil.uncheckedStableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter::compare);
        }
      }
    }
    @Override
    public void unstableSort(FloatComparator sorter)
    {
      final int size;
      if((size=this.size)>1){
        final int rootOffset;
        if(sorter==null){
          FloatSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }else{
          FloatSortUtil.uncheckedUnstableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter);
        }
      }
    }
    @Override public OmniList.OfFloat subList(int fromIndex,int toIndex){
      return new UncheckedSubList(this,this.rootOffset+fromIndex,toIndex-fromIndex);
    }
  }
  public
    static class CheckedStack
      extends UncheckedStack
  {
    private static final long serialVersionUID=1L;
    transient int modCount;
    public CheckedStack(Collection<? extends Float> that){
      super(that);
    }
    public CheckedStack(OmniCollection.OfRef<? extends Float> that){
      super(that);
    }
    public CheckedStack(OmniCollection.OfBoolean that){
      super(that);
    }
    public CheckedStack(OmniCollection.FloatOutput<?> that){
      super(that);
    }
    public CheckedStack(OmniCollection.OfByte that){
      super(that);
    }
    public CheckedStack(OmniCollection.OfShort that){
      super(that);
    }  
    public CheckedStack(OmniCollection.OfInt that){
      super(that);
    }
    public CheckedStack(OmniCollection.OfLong that){
      super(that);
    }
    public CheckedStack(OmniCollection.OfFloat that){
      super(that);
    }
    public CheckedStack(OmniCollection.OfChar that){
      super(that);
    }
    public CheckedStack(){
      super();
    }
    public CheckedStack(int initialCapacity){
      super(initialCapacity);
    }
    CheckedStack(int size,float[] arr){
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
      final float[] copy;
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(this.arr,0,copy=new float[size],0,size);
      }else{
        copy=OmniArray.OfFloat.DEFAULT_ARR;
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
    ,int bits
    ){
      final var arr=this.arr;
      for(int index=--size;;--index){
        if(
        bits==Float.floatToRawIntBits(arr[index])
        )
        {
          ++this.modCount;
          OmniArray.OfFloat.removeIndexAndPullDown(arr,index,size);
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
        Float.isNaN(arr[index])
        )
        {
          ++this.modCount;
          OmniArray.OfFloat.removeIndexAndPullDown(arr,index,size);
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
          OmniArray.OfFloat.removeIndexAndPullDown(arr,index,size);
          this.size=size;
          return true;
        }else if(index==0){
          return false;
        }
      }
    }
    @Override void uncheckedForEach(int size,FloatConsumer action){
      int modCount=this.modCount;
      try
      {
        OmniArray.OfFloat.descendingForEach(this.arr,0,size-1,action);
      }
      finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    private static class Itr
      extends AbstractFloatItr
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
      @Override public float nextFloat(){
        final CheckedStack root;
        CheckedCollection.checkModCount(modCount,(root=this.parent).modCount);
        int cursor;
        if((cursor=this.cursor)>0)
        {  
          this.lastRet=--cursor;
          this.cursor=cursor;
          return (float)root.arr[cursor];
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
          OmniArray.OfFloat.removeIndexAndPullDown(root.arr,lastRet,--root.size);
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(FloatConsumer action){
        final int cursor;
        if((cursor=this.cursor)>0){
          final int modCount=this.modCount;
          final var parent=this.parent;
          try{
            OmniArray.OfFloat.descendingForEach(parent.arr,0,cursor-1,action);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount,cursor,this.cursor);
          }
          this.cursor=0;
          this.lastRet=0;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Float> action){
        final int cursor;
        if((cursor=this.cursor)>0){
          final int modCount=this.modCount;
          final var parent=this.parent;
          try{
            OmniArray.OfFloat.descendingForEach(parent.arr,0,cursor-1,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount,cursor,this.cursor);
          }
          this.cursor=0;
          this.lastRet=0;
        }
      }
    }
    @Override public OmniIterator.OfFloat iterator(){
      return new Itr(this);
    }
    @Override public void push(float val){
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
    @Override public float popFloat(){
      int size;
      if((size=this.size)!=0){
        ++this.modCount;
        final var ret=(float)arr[--size];
        this.size=size;
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override public float pollFloat(){
      int size;
      if((size=this.size)!=0){
        ++this.modCount;
        final var ret=(float)(arr[--size]);
        this.size=size;
        return ret;
      }
      return Float.NaN;
    }
    @Override public Float poll(){
      int size;
      if((size=this.size)!=0){
        ++this.modCount;
        final var ret=(Float)(arr[--size]);
        this.size=size;
        return ret;
      }
      return null;
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
    @Override boolean uncheckedRemoveIf(int size,FloatPredicate filter){
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
    public CheckedList(Collection<? extends Float> that){
      super(that);
    }
    public CheckedList(OmniCollection.OfRef<? extends Float> that){
      super(that);
    }
    public CheckedList(OmniCollection.OfBoolean that){
      super(that);
    }
    public CheckedList(OmniCollection.FloatOutput<?> that){
      super(that);
    }
    public CheckedList(OmniCollection.OfByte that){
      super(that);
    }
    public CheckedList(OmniCollection.OfShort that){
      super(that);
    }  
    public CheckedList(OmniCollection.OfInt that){
      super(that);
    }
    public CheckedList(OmniCollection.OfLong that){
      super(that);
    }
    public CheckedList(OmniCollection.OfFloat that){
      super(that);
    }
    public CheckedList(OmniCollection.OfChar that){
      super(that);
    }
    public CheckedList(){
      super();
    }
    public CheckedList(int initialCapacity){
      super(initialCapacity);
    }
    CheckedList(int size,float[] arr){
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
    private boolean isEqualTo(int rootOffset,int size,OmniList.OfRef<?> list){
      if(list instanceof RefArrSeq.UncheckedList){
        final RefArrSeq.UncheckedList<?> that;
        return size==(that=(RefArrSeq.UncheckedList<?>)list).size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,that.arr,0);
      }else if(list instanceof RefArrSeq.CheckedSubList){
        final RefArrSeq.CheckedSubList<?> subList;
        final RefArrSeq.CheckedList<?> thatRoot;
        CheckedCollection.checkModCount((subList=(RefArrSeq.CheckedSubList<?>)list).modCount,(thatRoot=subList.root).modCount);
        return size==subList.size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,thatRoot.arr,subList.rootOffset);
      }else if(list instanceof RefArrSeq.UncheckedSubList){
        final RefArrSeq.UncheckedSubList<?> subList;
        return size==(subList=(RefArrSeq.UncheckedSubList<?>)list).size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,subList.root.arr,subList.rootOffset);
      }else{
        //must be RefDblLnkSeq
        final RefDblLnkSeq<?> dls;
        if((dls=(RefDblLnkSeq<?>)list) instanceof RefDblLnkSeq.CheckedSubList){
          final RefDblLnkSeq.CheckedSubList<?> subList;
          CheckedCollection.checkModCount((subList=(RefDblLnkSeq.CheckedSubList<?>)dls).modCount,subList.root.modCount);
        }
        return size==dls.size && SequenceEqualityUtil.isEqualTo(this.arr,rootOffset,rootOffset+size,dls.head);
      }
    }
    private boolean isEqualTo(int rootOffset,int size,OmniList.OfFloat list){
      if(list instanceof FloatArrSeq.UncheckedList){
        final FloatArrSeq.UncheckedList that;
        return size==(that=(FloatArrSeq.UncheckedList)list).size && ((that==this)||(super.isEqualToHelper(rootOffset,rootOffset+size,that.arr,0)));
      }else if(list instanceof FloatArrSeq.CheckedSubList){
        final FloatArrSeq.CheckedSubList subList;
        final FloatArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount((subList=(FloatArrSeq.CheckedSubList)list).modCount,(thatRoot=subList.root).modCount);
        final int thatOffset;
        return size==subList.size && (((thatOffset=subList.rootOffset)==rootOffset&&thatRoot==this)|| super.isEqualToHelper(rootOffset,rootOffset+size,thatRoot.arr,thatOffset));
      }else if(list instanceof FloatArrSeq.UncheckedSubList){
        final FloatArrSeq.UncheckedSubList subList;
        return size==(subList=(FloatArrSeq.UncheckedSubList)list).size && super.isEqualToHelper(rootOffset,rootOffset+size,subList.root.arr,subList.rootOffset);
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
    private boolean isEqualTo(int size,OmniList.OfRef<?> list){
      if(list instanceof RefArrSeq.UncheckedList){
        final RefArrSeq.UncheckedList<?> that;
        return size==(that=(RefArrSeq.UncheckedList<?>)list).size && SequenceEqualityUtil.isEqualTo(this.arr,0,size,that.arr,0);
      }else if(list instanceof RefDblLnkSeq){
        final RefDblLnkSeq<?> dls;
        if((dls=(RefDblLnkSeq<?>)list) instanceof RefDblLnkSeq.CheckedSubList){
          final RefDblLnkSeq.CheckedSubList<?> subList;
          CheckedCollection.checkModCount((subList=(RefDblLnkSeq.CheckedSubList<?>)dls).modCount,subList.root.modCount);
        }
        return size==dls.size && SequenceEqualityUtil.isEqualTo(this.arr,0,size,dls.head);
      }else if(list instanceof RefArrSeq.CheckedSubList){
        final RefArrSeq.CheckedSubList<?> subList;
        final RefArrSeq.CheckedList<?> thatRoot;
        CheckedCollection.checkModCount((subList=(RefArrSeq.CheckedSubList<?>)list).modCount,(thatRoot=subList.root).modCount);
        return size==subList.size && SequenceEqualityUtil.isEqualTo(this.arr,0,size,thatRoot.arr,subList.rootOffset);
      }else{
        //must be RefArrSeq.UncheckedSubList
        final RefArrSeq.UncheckedSubList<?> subList;
        return size==(subList=(RefArrSeq.UncheckedSubList<?>)list).size && SequenceEqualityUtil.isEqualTo(this.arr,0,size,subList.root.arr,subList.rootOffset);
      }
    }
    private boolean isEqualTo(int size,OmniList.OfFloat list){
      if(list instanceof FloatArrSeq.UncheckedList){
        final FloatArrSeq.UncheckedList that;
        return size==(that=(FloatArrSeq.UncheckedList)list).size && super.isEqualToHelper(0,size,that.arr,0);
      }else if(list instanceof FloatDblLnkSeq){
        final FloatDblLnkSeq dls;
        if((dls=(FloatDblLnkSeq)list) instanceof FloatDblLnkSeq.CheckedSubList){
          final FloatDblLnkSeq.CheckedSubList subList;
          CheckedCollection.checkModCount((subList=(FloatDblLnkSeq.CheckedSubList)dls).modCount,subList.root.modCount);
        }
        return size==dls.size && SequenceEqualityUtil.isEqualTo(this.arr,0,size,dls.head);
      }else if(list instanceof FloatArrSeq.CheckedSubList){
        final FloatArrSeq.CheckedSubList subList;
        final FloatArrSeq.CheckedList thatRoot;
        CheckedCollection.checkModCount((subList=(FloatArrSeq.CheckedSubList)list).modCount,(thatRoot=subList.root).modCount);
        return size==subList.size && (thatRoot==this || super.isEqualToHelper(0,size,thatRoot.arr,subList.rootOffset));
      }else{
        //must be FloatArrSeq.UncheckedSubList
        final FloatArrSeq.UncheckedSubList subList;
        return size==(subList=(FloatArrSeq.UncheckedSubList)list).size && super.isEqualToHelper(0,size,subList.root.arr,subList.rootOffset);
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
           if(list instanceof OmniList.OfFloat){
             return this.isEqualTo(size,(OmniList.OfFloat)list);
           }else if(list instanceof OmniList.OfRef){
             return this.isEqualTo(size,(OmniList.OfRef<?>)list);
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
      final float[] copy;
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(this.arr,0,copy=new float[size],0,size);
      }else{
        copy=OmniArray.OfFloat.DEFAULT_ARR;
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
    ,int bits
    ){
      final var arr=this.arr;
      for(int index=0;;){
        if(
        bits==Float.floatToRawIntBits(arr[index])
        )
        {
          ++this.modCount;
          OmniArray.OfFloat.removeIndexAndPullDown(arr,index,--size);
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
        Float.isNaN(arr[index])
        )
        {
          ++this.modCount;
          OmniArray.OfFloat.removeIndexAndPullDown(arr,index,--size);
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
          OmniArray.OfFloat.removeIndexAndPullDown(arr,index,--size);
          this.size=size;
          return true;
        }else if(++index==size){
          return false;
        }
      }
    }
    @Override void uncheckedForEach(int size,FloatConsumer action){
      int modCount=this.modCount;
      try
      {
        OmniArray.OfFloat.ascendingForEach(this.arr,0,size-1,action);
      }
      finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    private static class Itr
      extends AbstractFloatItr
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
      @Override public float nextFloat(){
        final CheckedList root;
        CheckedCollection.checkModCount(modCount,(root=this.parent).modCount);
        final int cursor;
        if((cursor=this.cursor)<root.size)
        {  
          this.lastRet=cursor;
          this.cursor=cursor+1;
          return (float)root.arr[cursor];
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
          OmniArray.OfFloat.removeIndexAndPullDown(root.arr,lastRet,--root.size);
          this.cursor=lastRet;
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(FloatConsumer action){
        final int cursor;
        final int bound;
        final CheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size)){
          final int modCount=this.modCount;
          final int lastRet;
          try{
            OmniArray.OfFloat.ascendingForEach(parent.arr,cursor,lastRet=bound-1,action);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount,cursor,this.cursor);
          }
          this.cursor=bound;
          this.lastRet=lastRet;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Float> action){
        final int cursor;
        final int bound;
        final CheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size)){
          final int modCount=this.modCount;
          final int lastRet;
          try{
            OmniArray.OfFloat.ascendingForEach(parent.arr,cursor,lastRet=bound-1,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount,cursor,this.cursor);
          }
          this.cursor=bound;
          this.lastRet=lastRet;
        }
      }
    }
    @Override public OmniIterator.OfFloat iterator(){
      return new Itr(this);
    }
    private static class ListItr extends Itr implements OmniListIterator.OfFloat{
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
      @Override public float previousFloat(){
        final CheckedList root;
        int cursor;
        CheckedCollection.checkModCount(modCount,(root=this.parent).modCount);
        if((cursor=this.cursor)>0)
        {
          this.lastRet=--cursor;
          this.cursor=cursor;
          return (float)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override public void set(float val){
        final int lastRet;
        if((lastRet=this.lastRet)!=-1){
          final CheckedList root;
          CheckedCollection.checkModCount(modCount,(root=this.parent).modCount);
          root.arr[lastRet]=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void add(float val){
        int modCount;
        final CheckedList root;
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
    @Override public OmniListIterator.OfFloat listIterator(){
      return new ListItr(this);
    }
    @Override public OmniListIterator.OfFloat listIterator(int index){
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,this.size);
      return new ListItr(this,index);
    }
    @Override public void push(float val){
      ++this.modCount;
      super.push(val);
    }
    @Override public void add(int index,float val){
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
    @Override public void put(int index,float val){
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      this.arr[index]=val;
    }
    @Override public float getFloat(int index){
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      return (float)this.arr[index];
    }
    @Override public float set(int index,float val){
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      final float[] arr;
      final var ret=(float)(arr=this.arr)[index];
      arr[index]=val;
      return ret;
    }
    @Override public float removeFloatAt(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      final float[] arr;
      ++this.modCount;
      final var ret=(float)(arr=this.arr)[index];
      OmniArray.OfFloat.removeIndexAndPullDown(arr,index,--size);
      this.size=size;
      return ret;
    }
    @Override boolean uncheckedRemoveIf(int size,FloatPredicate filter){
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
    @Override public void replaceAll(FloatUnaryOperator operator){
      final int size;
      if((size=this.size)!=0){
        int modCount=this.modCount;
        try{
          OmniArray.OfFloat.uncheckedReplaceAll(this.arr,0,size,operator);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override
    public void sort(FloatComparator sorter)
    {
      final int size;
      if((size=this.size)>1){
        if(sorter==null){
          FloatSortUtil.uncheckedAscendingSort(this.arr,0,size);
          ++this.modCount;
        }else{
          final int modCount=this.modCount;
          try{
            FloatSortUtil.uncheckedStableSort(this.arr,0,size,sorter);
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
        FloatSortUtil.uncheckedAscendingSort(this.arr,0,size);
        this.modCount=modCount+1;
      }
    }
    @Override
    public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1){
        FloatSortUtil.uncheckedDescendingSort(this.arr,0,size);
        this.modCount=modCount+1;
      }
    }
    @Override public void replaceAll(UnaryOperator<Float> operator){
      final int size;
      if((size=this.size)!=0){
        int modCount=this.modCount;
        try{
          OmniArray.OfFloat.uncheckedReplaceAll(this.arr,0,size,operator::apply);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override
    public void sort(Comparator<? super Float> sorter)
    {
      final int size;
      if((size=this.size)>1){
        if(sorter==null){
          FloatSortUtil.uncheckedAscendingSort(this.arr,0,size);
          ++this.modCount;
        }else{
          final int modCount=this.modCount;
          try{
            FloatSortUtil.uncheckedStableSort(this.arr,0,size,sorter::compare);
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
    public void unstableSort(FloatComparator sorter)
    {
      final int size;
      if((size=this.size)>1){
        if(sorter==null){
          FloatSortUtil.uncheckedAscendingSort(this.arr,0,size);
          ++this.modCount;
        }else{
          final int modCount=this.modCount;
          try{
            FloatSortUtil.uncheckedUnstableSort(this.arr,0,size,sorter);
          }catch(ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException("Comparison method violates its general contract!",e);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.modCount=modCount+1;
          }
        }
      }
    }
    @Override public OmniList.OfFloat subList(int fromIndex,int toIndex){
      return new CheckedSubList(this,fromIndex,CheckedCollection.checkSubListRange(fromIndex,toIndex,this.size));
    }
  }
    static class CheckedSubList
      extends AbstractOmniCollection<Float>
      implements FloatSubListDefault,Cloneable,RandomAccess
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
    @Override public boolean add(Float val){
      return add((float)val);
    }
    private static class SerializableSubList implements Serializable{
      private static final long serialVersionUID=1L;
      private transient float[] arr;
      private transient int size;
      private transient final int rootOffset;
      private transient final CheckedList.ModCountChecker modCountChecker;
      private SerializableSubList(float[] arr,int size,int rootOffset
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
          float[] arr;
          OmniArray.OfFloat.readArray(arr=new float[size],0,size-1,ois);
          this.arr=arr;
        }else{
          this.arr=OmniArray.OfFloat.DEFAULT_ARR;
        }
      }
      private void writeObject(ObjectOutputStream oos) throws IOException{
        try
        {
          int size;
          oos.writeInt(size=this.size);
          if(size!=0){
            final int rootOffset;
            OmniArray.OfFloat.writeArray(arr,rootOffset=this.rootOffset,rootOffset+size-1,oos);
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
          final int size;
          if((size=this.size)==0){
            return ((List<?>)val).isEmpty();
          }
          final List<?> list;
          if((list=(List<?>)val) instanceof AbstractOmniCollection){
            if(list instanceof OmniList.OfFloat){
              return root.isEqualTo(this.rootOffset,size,(OmniList.OfFloat)list);
            }else if(list instanceof OmniList.OfRef){
              return root.isEqualTo(this.rootOffset,size,(OmniList.OfRef<?>)list);
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
      final CheckedList root;
      CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
      final float[] copy;
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(root.arr,rootOffset,copy=new float[size],0,size);
      }else{
        copy=OmniArray.OfFloat.DEFAULT_ARR;
      }
      return new CheckedList(size,copy);
    }
    @Override public String toString(){
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      int size;
      if((size=this.size)!=0){
          final int rootOffset;
          final byte[] buffer;
          if(size<=(OmniArray.MAX_ARR_SIZE/17)){(buffer=new byte[size*17])
            [size=OmniArray.OfFloat.ascendingToString(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,buffer,1)]=(byte)']';
            buffer[0]=(byte)'[';
            return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
          }else{
            final ToStringUtil.OmniStringBuilderByte builder;
            OmniArray.OfFloat.ascendingToString(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]));
            builder.uncheckedAppendChar((byte)']');
            (buffer=builder.buffer)[0]=(byte)'[';
            return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
          }
      }
      return "[]";
    }
  @Override public int hashCode(){
    final CheckedList root;
    CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return OmniArray.OfFloat.ascendingSeqHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
        root.size=OmniArray.OfFloat.removeRangeAndPullDown(root.arr,this.rootOffset+size,root.size,size);
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
              return this.uncheckedremoveValBits(size,TypeUtil.FLT_TRUE_BITS);
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
              if(TypeUtil.checkCastToFloat(val))
              {
                return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
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
              if(TypeUtil.checkCastToFloat(val)){
                return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
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
              return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
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
            final float v;
            if(val==(v=(float)val)){
              return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(v));
            }else if(v!=v){
              return this.uncheckedremoveValNaN(size);
            }
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
              if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(f));
                }
                return this.uncheckedremoveValNaN(size);
              }else if(val instanceof Double){
                final double d;
                final float f;
                if((d=(double)val)==(f=(float)d)){
                  return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(f));
                }else if(f!=f){
                  return this.uncheckedremoveValNaN(size);
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Integer){
                final int i;
                if((i=(int)val)!=0){
                  if(!TypeUtil.checkCastToFloat(i)){
                    break returnFalse;
                  }
                  return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(i));
                }
                return this.uncheckedremoveVal0(size);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToFloat(l)){
                    break returnFalse;
                  }
                  return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(l));
                }
                return this.uncheckedremoveVal0(size);
              }else if(val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).shortValue())!=0){
                  return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(i));
                }
                return this.uncheckedremoveVal0(size);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(i));
                }
                return this.uncheckedremoveVal0(size);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return this.uncheckedremoveValBits(size,TypeUtil.FLT_TRUE_BITS);
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
    @Override public boolean removeVal(char val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=0){
              return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
            }
            return this.uncheckedremoveVal0(size);
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
            if(val!=0){
              return this.uncheckedremoveValBits(size,Float.floatToRawIntBits(val));
            }
            return this.uncheckedremoveVal0(size);
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
              return OmniArray.OfFloat.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,TypeUtil.FLT_TRUE_BITS);
            }
            final int rootOffset;
            return OmniArray.OfFloat.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
              if(TypeUtil.checkCastToFloat(val))
              {
                final int rootOffset;
                return OmniArray.OfFloat.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Float.floatToRawIntBits(val));
              }
            }else{
              final int rootOffset;
              return OmniArray.OfFloat.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
              if(TypeUtil.checkCastToFloat(val)){
                final int rootOffset;
                return OmniArray.OfFloat.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Float.floatToRawIntBits(val));
              }
            }else{
              final int rootOffset;
              return OmniArray.OfFloat.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
              return OmniArray.OfFloat.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Float.floatToRawIntBits(val));
            }
            final int rootOffset;
            return OmniArray.OfFloat.uncheckedcontainsNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
            final float v;
            if(val==(v=(float)val)){
              final int rootOffset;
              return OmniArray.OfFloat.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Float.floatToRawIntBits(v));
            }else if(v!=v){
              final int rootOffset;
              return OmniArray.OfFloat.uncheckedcontainsNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
            }
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
              if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   final int rootOffset;
                   return OmniArray.OfFloat.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Float.floatToRawIntBits(f));
                }
                final int rootOffset;
                return OmniArray.OfFloat.uncheckedcontainsNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
              }else if(val instanceof Double){
                final double d;
                final float f;
                if((d=(double)val)==(f=(float)d)){
                  final int rootOffset;
                  return OmniArray.OfFloat.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Float.floatToRawIntBits(f));
                }else if(f!=f){
                  final int rootOffset;
                  return OmniArray.OfFloat.uncheckedcontainsNaN(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Integer){
                final int i;
                if((i=(int)val)!=0){
                  if(!TypeUtil.checkCastToFloat(i)){
                    break returnFalse;
                  }
                  final int rootOffset;
                  return OmniArray.OfFloat.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Float.floatToRawIntBits(i));
                }
                final int rootOffset;
                return OmniArray.OfFloat.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToFloat(l)){
                    break returnFalse;
                  }
                  final int rootOffset;
                  return OmniArray.OfFloat.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Float.floatToRawIntBits(l));
                }
                final int rootOffset;
                return OmniArray.OfFloat.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
              }else if(val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).shortValue())!=0){
                  final int rootOffset;
                  return OmniArray.OfFloat.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Float.floatToRawIntBits(i));
                }
                final int rootOffset;
                return OmniArray.OfFloat.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  final int rootOffset;
                  return OmniArray.OfFloat.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Float.floatToRawIntBits(i));
                }
                final int rootOffset;
                return OmniArray.OfFloat.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  final int rootOffset;
                  return OmniArray.OfFloat.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,TypeUtil.FLT_TRUE_BITS);
                }
                final int rootOffset;
                return OmniArray.OfFloat.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
    @Override public boolean contains(char val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=0){
              final int rootOffset;
              return OmniArray.OfFloat.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Float.floatToRawIntBits(val));
            }
            final int rootOffset;
            return OmniArray.OfFloat.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
            if(val!=0){
              final int rootOffset;
              return OmniArray.OfFloat.uncheckedcontainsBits(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,Float.floatToRawIntBits(val));
            }
            final int rootOffset;
            return OmniArray.OfFloat.uncheckedcontains0(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
              return OmniArray.OfFloat.uncheckedindexOfBits(root.arr,this.rootOffset,size,TypeUtil.FLT_TRUE_BITS);
            }
            return OmniArray.OfFloat.uncheckedindexOf0(root.arr,this.rootOffset,size);
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
              if(TypeUtil.checkCastToFloat(val))
              {
                return OmniArray.OfFloat.uncheckedindexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(val));
              }
            }else{
              return OmniArray.OfFloat.uncheckedindexOf0(root.arr,this.rootOffset,size);
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
              if(TypeUtil.checkCastToFloat(val)){
                return OmniArray.OfFloat.uncheckedindexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(val));
              }
            }else{
              return OmniArray.OfFloat.uncheckedindexOf0(root.arr,this.rootOffset,size);
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
              return OmniArray.OfFloat.uncheckedindexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(val));
            }
            return OmniArray.OfFloat.uncheckedindexOfNaN(root.arr,this.rootOffset,size);
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
            final float v;
            if(val==(v=(float)val)){
              return OmniArray.OfFloat.uncheckedindexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(v));
            }else if(v!=v){
              return OmniArray.OfFloat.uncheckedindexOfNaN(root.arr,this.rootOffset,size);
            }
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
              if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return OmniArray.OfFloat.uncheckedindexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(f));
                }
                return OmniArray.OfFloat.uncheckedindexOfNaN(root.arr,this.rootOffset,size);
              }else if(val instanceof Double){
                final double d;
                final float f;
                if((d=(double)val)==(f=(float)d)){
                  return OmniArray.OfFloat.uncheckedindexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(f));
                }else if(f!=f){
                  return OmniArray.OfFloat.uncheckedindexOfNaN(root.arr,this.rootOffset,size);
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Integer){
                final int i;
                if((i=(int)val)!=0){
                  if(!TypeUtil.checkCastToFloat(i)){
                    break returnFalse;
                  }
                  return OmniArray.OfFloat.uncheckedindexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(i));
                }
                return OmniArray.OfFloat.uncheckedindexOf0(root.arr,this.rootOffset,size);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToFloat(l)){
                    break returnFalse;
                  }
                  return OmniArray.OfFloat.uncheckedindexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(l));
                }
                return OmniArray.OfFloat.uncheckedindexOf0(root.arr,this.rootOffset,size);
              }else if(val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).shortValue())!=0){
                  return OmniArray.OfFloat.uncheckedindexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(i));
                }
                return OmniArray.OfFloat.uncheckedindexOf0(root.arr,this.rootOffset,size);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return OmniArray.OfFloat.uncheckedindexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(i));
                }
                return OmniArray.OfFloat.uncheckedindexOf0(root.arr,this.rootOffset,size);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return OmniArray.OfFloat.uncheckedindexOfBits(root.arr,this.rootOffset,size,TypeUtil.FLT_TRUE_BITS);
                }
                return OmniArray.OfFloat.uncheckedindexOf0(root.arr,this.rootOffset,size);
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
    @Override public int indexOf(char val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=0){
              return OmniArray.OfFloat.uncheckedindexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(val));
            }
            return OmniArray.OfFloat.uncheckedindexOf0(root.arr,this.rootOffset,size);
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
            if(val!=0){
              return OmniArray.OfFloat.uncheckedindexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(val));
            }
            return OmniArray.OfFloat.uncheckedindexOf0(root.arr,this.rootOffset,size);
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
              return OmniArray.OfFloat.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,TypeUtil.FLT_TRUE_BITS);
            }
            return OmniArray.OfFloat.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
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
              if(TypeUtil.checkCastToFloat(val))
              {
                return OmniArray.OfFloat.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(val));
              }
            }else{
              return OmniArray.OfFloat.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
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
              if(TypeUtil.checkCastToFloat(val)){
                return OmniArray.OfFloat.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(val));
              }
            }else{
              return OmniArray.OfFloat.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
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
              return OmniArray.OfFloat.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(val));
            }
            return OmniArray.OfFloat.uncheckedlastIndexOfNaN(root.arr,this.rootOffset,size);
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
            final float v;
            if(val==(v=(float)val)){
              return OmniArray.OfFloat.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(v));
            }else if(v!=v){
              return OmniArray.OfFloat.uncheckedlastIndexOfNaN(root.arr,this.rootOffset,size);
            }
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
              if(val instanceof Float){
                final float f;
                if((f=(float)val)==f){
                   return OmniArray.OfFloat.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(f));
                }
                return OmniArray.OfFloat.uncheckedlastIndexOfNaN(root.arr,this.rootOffset,size);
              }else if(val instanceof Double){
                final double d;
                final float f;
                if((d=(double)val)==(f=(float)d)){
                  return OmniArray.OfFloat.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(f));
                }else if(f!=f){
                  return OmniArray.OfFloat.uncheckedlastIndexOfNaN(root.arr,this.rootOffset,size);
                }else{
                  break returnFalse;
                }
              }else if(val instanceof Integer){
                final int i;
                if((i=(int)val)!=0){
                  if(!TypeUtil.checkCastToFloat(i)){
                    break returnFalse;
                  }
                  return OmniArray.OfFloat.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(i));
                }
                return OmniArray.OfFloat.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=0){
                  if(!TypeUtil.checkCastToFloat(l)){
                    break returnFalse;
                  }
                  return OmniArray.OfFloat.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(l));
                }
                return OmniArray.OfFloat.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
              }else if(val instanceof Short||val instanceof Byte){
                final int i;
                if((i=((Number)val).shortValue())!=0){
                  return OmniArray.OfFloat.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(i));
                }
                return OmniArray.OfFloat.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
              }else if(val instanceof Character){
                final int i;
                if((i=(char)val)!=0){
                  return OmniArray.OfFloat.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(i));
                }
                return OmniArray.OfFloat.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
              }else if(val instanceof Boolean){
                if((boolean)val){
                  return OmniArray.OfFloat.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,TypeUtil.FLT_TRUE_BITS);
                }
                return OmniArray.OfFloat.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
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
    @Override public int lastIndexOf(char val){
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
            if(val!=0){
              return OmniArray.OfFloat.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(val));
            }
            return OmniArray.OfFloat.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
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
            if(val!=0){
              return OmniArray.OfFloat.uncheckedlastIndexOfBits(root.arr,this.rootOffset,size,Float.floatToRawIntBits(val));
            }
            return OmniArray.OfFloat.uncheckedlastIndexOf0(root.arr,this.rootOffset,size);
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    private boolean uncheckedremoveValBits(int size
    ,int bits
    ){
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      final var arr=root.arr;
      for(int index=this.rootOffset,bound=index+(--size);;++index){
        if(
        bits==Float.floatToRawIntBits(arr[index])
        )
        {
          root.modCount=++modCount;
          this.modCount=modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,--curr.size,curr=curr.parent){}
          OmniArray.OfFloat.removeIndexAndPullDown(arr,index,--root.size);
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
        Float.isNaN(arr[index])
        )
        {
          root.modCount=++modCount;
          this.modCount=modCount;
          for(var curr=parent;curr!=null;curr.modCount=modCount,--curr.size,curr=curr.parent){}
          OmniArray.OfFloat.removeIndexAndPullDown(arr,index,--root.size);
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
          OmniArray.OfFloat.removeIndexAndPullDown(arr,index,--root.size);
          this.size=size;
          return true;
        }else if(index==bound){
          return false;
        }
      }
    }
    @Override public void forEach(FloatConsumer action){
      int modCount=this.modCount;
      final var root=this.root;
      try
      {
        final int size;
        if((size=this.size)!=0){
          final int rootOffset;
          OmniArray.OfFloat.ascendingForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,action);
        }
      }
      finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void forEach(Consumer<? super Float> action){
      int modCount=this.modCount;
      final var root=this.root;
      try
      {
        final int size;
        if((size=this.size)!=0){
          final int rootOffset;
          OmniArray.OfFloat.ascendingForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,action::accept);
        }
      }
      finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    private static class Itr
      extends AbstractFloatItr
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
      @Override public float nextFloat(){
        final CheckedList root;
        final CheckedSubList parent;
        CheckedCollection.checkModCount(modCount,(root=(parent=this.parent).root).modCount);
        final int cursor;
        if((cursor=this.cursor)<(parent.rootOffset+parent.size))
        {  
          this.lastRet=cursor;
          this.cursor=cursor+1;
          return (float)root.arr[cursor];
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
          OmniArray.OfFloat.removeIndexAndPullDown(root.arr,lastRet,--root.size);
          this.cursor=lastRet;
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(FloatConsumer action){
        final int cursor;
        final int bound;
        final CheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size)){
          final int modCount=this.modCount;
          final var root=parent.root;
          final int lastRet;
          try{
            OmniArray.OfFloat.ascendingForEach(root.arr,cursor,lastRet=bound-1,action);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount,cursor,this.cursor);
          }
          this.cursor=bound;
          this.lastRet=lastRet;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Float> action){
        final int cursor;
        final int bound;
        final CheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size)){
          final int modCount=this.modCount;
          final var root=parent.root;
          final int lastRet;
          try{
            OmniArray.OfFloat.ascendingForEach(root.arr,cursor,lastRet=bound-1,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount,cursor,this.cursor);
          }
          this.cursor=bound;
          this.lastRet=lastRet;
        }
      }
    }
    @Override public OmniIterator.OfFloat iterator(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new Itr(this);
    }
    private static class ListItr extends Itr implements OmniListIterator.OfFloat{
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
      @Override public float previousFloat(){
        final CheckedList root;
        int cursor;
        final CheckedSubList parent;
        CheckedCollection.checkModCount(modCount,(root=(parent=this.parent).root).modCount);
        if((cursor=this.cursor)>parent.rootOffset)
        {
          this.lastRet=--cursor;
          this.cursor=cursor;
          return (float)root.arr[cursor];
        }
        throw new NoSuchElementException();
      }
      @Override public void set(float val){
        final int lastRet;
        if((lastRet=this.lastRet)!=-1){
          final CheckedList root;
          CheckedCollection.checkModCount(modCount,(root=this.parent.root).modCount);
          root.arr[lastRet]=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void add(float val){
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
          root.uncheckedInsert(this.cursor++,rootSize,val);
        }else{
          root.uncheckedInit(val);
          ++this.cursor;
        }
      }
    }
    @Override public OmniListIterator.OfFloat listIterator(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new ListItr(this);
    }
    @Override public OmniListIterator.OfFloat listIterator(int index){
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,this.size);
      return new ListItr(this,index+this.rootOffset);
    }
    @Override public boolean add(float val){
      final CheckedList root;
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
    @Override public void add(int index,float val){
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
        root.uncheckedInsert(this.rootOffset+index,modCount,val);
      }else{
        root.uncheckedInit(val);
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
    @Override public float[] toFloatArray(){
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0){
        final float[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new float[size],0,size);
        return dst;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override public Float[] toArray(){
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0){
        final Float[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new Float[size],0,size);
        return dst;
      }
      return OmniArray.OfFloat.DEFAULT_BOXED_ARR;
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
    @Override public void put(int index,float val){
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      root.arr[index+this.rootOffset]=val;
    }
    @Override public float getFloat(int index){
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      return (float)root.arr[index+this.rootOffset];
    }
    @Override public float set(int index,float val){
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      final float[] arr;
      final var ret=(float)(arr=root.arr)[index+=this.rootOffset];
      arr[index]=val;
      return ret;
    }
    @Override public float removeFloatAt(int index){
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      final float[] arr;
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,--curr.size,curr=curr.parent){}
      final var ret=(float)(arr=root.arr)[index+=this.rootOffset];
      OmniArray.OfFloat.removeIndexAndPullDown(arr,index,--root.size);
      this.size=size-1;
      return ret;
    }
    @Override public boolean removeIf(FloatPredicate filter){
      int modCount=this.modCount;
      final var root=this.root;
      final int size;
      if((size=this.size)!=0){
        try
        {
          final float[] arr;
          final int numRemoved;
          int rootOffset;
          if((numRemoved=uncheckedRemoveIfImpl(arr=root.arr,rootOffset=this.rootOffset,rootOffset+=size,filter,root.new ModCountChecker(modCount)))!=0){
            root.modCount=++modCount;
            this.modCount=modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,curr.size-=numRemoved,curr=curr.parent){}
            root.size=OmniArray.OfFloat.removeRangeAndPullDown(arr,rootOffset,root.size,numRemoved);
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
    @Override public boolean removeIf(Predicate<? super Float> filter){
      int modCount=this.modCount;
      final var root=this.root;
      final int size;
      if((size=this.size)!=0){
        try
        {
          final float[] arr;
          final int numRemoved;
          int rootOffset;
          if((numRemoved=uncheckedRemoveIfImpl(arr=root.arr,rootOffset=this.rootOffset,rootOffset+=size,filter::test,root.new ModCountChecker(modCount)))!=0){
            root.modCount=++modCount;
            this.modCount=modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,curr.size-=numRemoved,curr=curr.parent){}
            root.size=OmniArray.OfFloat.removeRangeAndPullDown(arr,rootOffset,root.size,numRemoved);
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
    @Override public void replaceAll(FloatUnaryOperator operator){
      final int size;
      if((size=this.size)==0){
        CheckedCollection.checkModCount(modCount,root.modCount);
        return;
      }
      int modCount=this.modCount;
      final var root=this.root;
      try{
        final int rootOffset;
        OmniArray.OfFloat.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);  
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
      }
    }
    @Override
    public void sort(FloatComparator sorter)
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
        FloatSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
      }else{
        try{
          final int rootOffset;
          FloatSortUtil.uncheckedStableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter);
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
        FloatSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
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
        FloatSortUtil.uncheckedDescendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}  
      }
    }
    @Override public void replaceAll(UnaryOperator<Float> operator){
      final int size;
      if((size=this.size)==0){
        CheckedCollection.checkModCount(modCount,root.modCount);
        return;
      }
      int modCount=this.modCount;
      final var root=this.root;
      try{
        final int rootOffset;
        OmniArray.OfFloat.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator::apply);  
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
      }
    }
    @Override
    public void sort(Comparator<? super Float> sorter)
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
        FloatSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
      }else{
        try{
          final int rootOffset;
          FloatSortUtil.uncheckedStableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter::compare);
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
    public void unstableSort(FloatComparator sorter)
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
        FloatSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
      }else{
        try{
          final int rootOffset;
          FloatSortUtil.uncheckedUnstableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter);
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
    @Override public OmniList.OfFloat subList(int fromIndex,int toIndex){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new CheckedSubList(this,this.rootOffset+fromIndex,CheckedCollection.checkSubListRange(fromIndex,toIndex,this.size));
    }
  }
}
