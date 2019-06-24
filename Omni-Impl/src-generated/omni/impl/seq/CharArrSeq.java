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
import omni.impl.AbstractCharItr;
import java.io.Externalizable;
import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectInput;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.RandomAccess;
public abstract class CharArrSeq extends 
AbstractSeq<Character>
 implements OmniCollection.OfChar,Externalizable,RandomAccess{
  //TODO refactor the template and/or optimize code generation to make sure that the code generation doesn't take forever
  private static final long serialVersionUID=1L;
  transient char[] arr; 
  private CharArrSeq(){
    super();
    this.arr=OmniArray.OfChar.DEFAULT_ARR;
  }
  private CharArrSeq(int initialCapacity){
    super();
    switch(initialCapacity){ 
    default:
      this.arr=new char[initialCapacity];
      return;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      this.arr=OmniArray.OfChar.DEFAULT_ARR;
    case 0:
    }
  }
  private CharArrSeq(int size,char[] arr){
    super(size);
    this.arr=arr;
  }
  @Override public void writeExternal(ObjectOutput out) throws IOException{
    int size;
    out.writeInt(size=this.size);
    if(size!=0)
    {
      OmniArray.OfChar.writeArray(arr,0,size-1,out);
    }
  }
  @Override public void readExternal(ObjectInput in) throws IOException
  {
    int size;
    this.size=size=in.readInt();
    if(size!=0){
      char[] arr;
      OmniArray.OfChar.readArray(arr=new char[size],0,size-1,in);
      this.arr=arr;
    }
  }
  static  long markSurvivors(char[] arr,int srcOffset,int srcBound,CharPredicate filter){
    for(long word=0L,marker=1L;;marker<<=1){
      if(!filter.test((char)arr[srcOffset])){
        word|=marker;
      }
      if(++srcOffset==srcBound){
        return word;
      }
    }
  }
  static  int markSurvivors(char[] arr,int srcOffset,int srcBound,CharPredicate filter,long[] survivorSet){
    for(int numSurvivors=0,wordOffset=0;;){
      long word=0L,marker=1L;
      do{
        if(!filter.test((char)arr[srcOffset])){
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
  static void pullSurvivorsDown(char[] arr,int srcOffset,int dstOffset,long word){
    int numTail0s=Long.numberOfTrailingZeros(word);
    do{
      ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset+=numTail0s,numTail0s=Long.numberOfTrailingZeros(~(word>>>=numTail0s)));
      srcOffset+=numTail0s;
      dstOffset+=numTail0s;
    }while((numTail0s=Long.numberOfTrailingZeros(word>>>=numTail0s))!=64);
  }
  static void pullSurvivorsDown(char[] arr,int srcOffset,int dstOffset,int dstBound,long[] survivorSet){
    for(int wordOffset=0;;){
      long word=survivorSet[wordOffset];
      for(int s=srcOffset;;){
        int numTail0s;
        if((numTail0s=Long.numberOfTrailingZeros(word))==64){
          break;
        }
        ArrCopy.uncheckedSelfCopy(arr,dstOffset,s+=numTail0s,numTail0s=Long.numberOfTrailingZeros(~(word>>>=numTail0s)));
        dstOffset+=numTail0s;
        if(numTail0s==64){
          break;
        }else if(dstOffset>=dstBound){
          return;
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
  @Override public int hashCode(){
    final int size;
    if((size=this.size)!=0){
      return uncheckedHashCode(size);
    }
    return 1;
  }
  @Override public String toString(){
    int size;
    if((size=this.size)!=0){
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
  @Override public boolean contains(boolean val){
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfChar.uncheckedcontains(this.arr,0,size-1,TypeUtil.castToChar(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(int val){
    if(val==(char)val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfChar.uncheckedcontains(this.arr,0,size-1,(val));
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
          final char v;
          if((v=(char)val)==val){
            return OmniArray.OfChar.uncheckedcontains(this.arr,0,size-1,v);
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
          final char v;
          if(val==(v=(char)val))
          {
            return OmniArray.OfChar.uncheckedcontains(this.arr,0,size-1,v);
          }
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
          final char v;
          if(val==(v=(char)val))
          {
            return OmniArray.OfChar.uncheckedcontains(this.arr,0,size-1,v);
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
            final int i;
            if(val instanceof Character){
              i=(char)val;
            }else if(val instanceof Integer){
              if((i=(int)val)!=(char)i){
                break returnFalse;
              }
            }else if(val instanceof Byte||val instanceof Short){
              if((i=((Number)val).shortValue())<0){
                break returnFalse;
              }
            }else if(val instanceof Long){
              final long l;
              if((l=(long)val)!=(i=(char)l)){
                break returnFalse;
              }
            }else if(val instanceof Float){
              final float f;
              if((f=(float)val)!=(i=(char)f)){
                break returnFalse;
              }
            }else if(val instanceof Double){
              final double d;
              if((d=(double)val)!=(i=(char)d)){
                break returnFalse;
              }
            }else if(val instanceof Boolean){
              i=TypeUtil.castToByte((boolean)val);
            }else{
              break returnFalse;
            }
            return OmniArray.OfChar.uncheckedcontains(this.arr,0,size-1,i);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(byte val){
    if(val>=0)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfChar.uncheckedcontains(this.arr,0,size-1,(val));
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
          return OmniArray.OfChar.uncheckedcontains(this.arr,0,size-1,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean contains(short val){
    if(val>=0)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return OmniArray.OfChar.uncheckedcontains(this.arr,0,size-1,(val));
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
          return this.uncheckedremoveVal(size,TypeUtil.castToChar(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(int val){
    if(val==(char)val)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,(val));
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
          final char v;
          if((v=(char)val)==val){
            return this.uncheckedremoveVal(size,v);
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
          final char v;
          if(val==(v=(char)val))
          {
            return this.uncheckedremoveVal(size,v);
          }
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
          final char v;
          if(val==(v=(char)val))
          {
            return this.uncheckedremoveVal(size,v);
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
            final int i;
            if(val instanceof Character){
              i=(char)val;
            }else if(val instanceof Integer){
              if((i=(int)val)!=(char)i){
                break returnFalse;
              }
            }else if(val instanceof Byte||val instanceof Short){
              if((i=((Number)val).shortValue())<0){
                break returnFalse;
              }
            }else if(val instanceof Long){
              final long l;
              if((l=(long)val)!=(i=(char)l)){
                break returnFalse;
              }
            }else if(val instanceof Float){
              final float f;
              if((f=(float)val)!=(i=(char)f)){
                break returnFalse;
              }
            }else if(val instanceof Double){
              final double d;
              if((d=(double)val)!=(i=(char)d)){
                break returnFalse;
              }
            }else if(val instanceof Boolean){
              i=TypeUtil.castToByte((boolean)val);
            }else{
              break returnFalse;
            }
            return this.uncheckedremoveVal(size,i);
          }
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(byte val){
    if(val>=0)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,(val));
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
          return this.uncheckedremoveVal(size,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  @Override public boolean removeVal(short val){
    if(val>=0)
    {
      {
        final int size;
        if((size=this.size)!=0)
        {
          return this.uncheckedremoveVal(size,(val));
        } //end size check
      } //end checked sublist try modcount
    }//end val check
    return false;
  }
  abstract boolean uncheckedremoveVal(int size,int val);
  abstract void uncheckedForEach(int size,CharConsumer action);
  @Override public void forEach(CharConsumer action){
    final int size;
    if((size=this.size)!=0){
      uncheckedForEach(size,action);
    }
  }
  @Override public void forEach(Consumer<? super Character> action){
    final int size;
    if((size=this.size)!=0){
      uncheckedForEach(size,action::accept);
    }
  }
  private void uncheckedAppend(int size,char val){
    char[] arr;
    if((arr=this.arr).length==size){
      ArrCopy.uncheckedCopy(arr,0,arr=new char[OmniArray.growBy50Pct(size)],0,size);
      this.arr=arr;
    }
    arr[size]=val;
    this.size=size+1;
  }
  private void uncheckedInit(char val){
    char[] arr;
    if((arr=this.arr)==null){
      this.arr=new char[]{val};
    }else{
      if(arr==OmniArray.OfChar.DEFAULT_ARR){
        this.arr=arr=new char[OmniArray.DEFAULT_ARR_SEQ_CAP];
      }
      arr[0]=val;
    }
    this.size=1;
  }
  public void push(char val){
    final int size;
    if((size=this.size)!=0){
      uncheckedAppend(size,val);
    }else{
      uncheckedInit(val);
    }
  }
  @Override public boolean add(char val){
    push(val);
    return true;
  }
  @Override public boolean add(Character val){
    push((char)val);
    return true;
  }
  @Override public boolean add(boolean val){
    push((char)TypeUtil.castToChar(val));
    return true;
  }
  abstract void uncheckedCopyInto(char[] dst,int length);
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
  @Override public char[] toCharArray(){
    final int size;
    if((size=this.size)!=0){
      final char[] dst;
      uncheckedCopyInto(dst=new char[size],size);
      return dst;
    }
    return OmniArray.OfChar.DEFAULT_ARR;
  }
  abstract void uncheckedCopyInto(Object[] dst,int length);
  abstract void uncheckedCopyInto(Character[] dst,int length);
  @Override public Character[] toArray(){
    final int size;
    if((size=this.size)!=0){
      final Character[] dst;
      uncheckedCopyInto(dst=new Character[size],size);
      return dst;
    }
    return OmniArray.OfChar.DEFAULT_BOXED_ARR;
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
  abstract void uncheckedCopyInto(float[] dst,int length);
  @Override public float[] toFloatArray(){
    final int size;
    if((size=this.size)!=0){
      final float[] dst;
      uncheckedCopyInto(dst=new float[size],size);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  abstract void uncheckedCopyInto(long[] dst,int length);
  @Override public long[] toLongArray(){
    final int size;
    if((size=this.size)!=0){
      final long[] dst;
      uncheckedCopyInto(dst=new long[size],size);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  abstract void uncheckedCopyInto(int[] dst,int length);
  @Override public int[] toIntArray(){
    final int size;
    if((size=this.size)!=0){
      final int[] dst;
      uncheckedCopyInto(dst=new int[size],size);
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  boolean uncheckedRemoveIf(int size,CharPredicate filter){
    if(size!=(size-=uncheckedRemoveIfImpl(this.arr,0,size,filter))){
      this.size=size;
      return true;
    }
    return false;
  }
  @Override public boolean removeIf(CharPredicate filter){
    final int size;
    return (size=this.size)!=0 && uncheckedRemoveIf(size,filter);
  }
  @Override public boolean removeIf(Predicate<? super Character> filter){
    final int size;
    return (size=this.size)!=0 && uncheckedRemoveIf(size,filter::test);
  }
  private static  int pullSurvivorsDown(char[] arr,int srcOffset,int srcBound,int dstOffset,CharPredicate filter){
    while(++srcOffset!=srcBound){
      final char v;
      if(!filter.test((char)(v=arr[srcOffset])))
      {
        arr[dstOffset++]=v;
      }
    }
    return srcBound-dstOffset;
  }
  private static  int uncheckedRemoveIfImpl(char[] arr,int srcOffset,int srcBound,CharPredicate filter){
    do{
      if(filter.test((char)arr[srcOffset])){
        return pullSurvivorsDown(arr,srcOffset,srcBound,srcOffset,filter);
      }
    }while(++srcOffset!=srcBound);
    return 0;
  }
  private static  int uncheckedRemoveIfImpl(char[] arr,int srcOffset,int srcBound,CharPredicate filter,CheckedCollection.AbstractModCountChecker modCountChecker){
    do{
      if(filter.test((char)arr[srcOffset])){
        int dstOffset=srcOffset;
        outer:for(;;){
          if(++srcOffset==srcBound){
            modCountChecker.checkModCount();
            break outer;
          }
          char before;
          if(!filter.test((char)(before=arr[srcOffset]))){
            for(int i=srcBound-1;;--i){
              if(i==srcOffset){
                modCountChecker.checkModCount();
                arr[dstOffset++]=before;
                break outer;
              }
              char after;
              if(!filter.test((char)(after=arr[i]))){
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
      extends CharArrSeq
      implements OmniStack.OfChar,Cloneable,RandomAccess
  {
    private static final long serialVersionUID=1L;
    public UncheckedStack(){
      super();
    }
    public UncheckedStack(int initialCapacity){
      super(initialCapacity);
    }
    UncheckedStack(int size,char[] arr){
      super(size,arr);
    }
    @Override public boolean equals(Object val){
      //TODO implements equals method for UncheckedStack
      return false;
    }
    @Override public Object clone(){
      final char[] copy;
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(this.arr,0,copy=new char[size],0,size);
      }else{
        copy=OmniArray.OfChar.DEFAULT_ARR;
      }
      return new UncheckedStack(size,copy);
    }
    void uncheckedToString(int size,char[] buffer){
      OmniArray.OfChar.descendingToString(this.arr,0,size-1,buffer,1);
    }
  @Override int uncheckedHashCode(int size){
    return OmniArray.OfChar.descendingSeqHashCode(this.arr,0,size-1);
  }
    @Override public int search(boolean val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfChar.uncheckedsearch(this.arr,size,TypeUtil.castToChar(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(int val){
      if(val==(char)val)
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfChar.uncheckedsearch(this.arr,size,(val));
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
            final char v;
            if((v=(char)val)==val){
              return OmniArray.OfChar.uncheckedsearch(this.arr,size,v);
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
            final char v;
            if(val==(v=(char)val))
            {
              return OmniArray.OfChar.uncheckedsearch(this.arr,size,v);
            }
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
            final char v;
            if(val==(v=(char)val))
            {
              return OmniArray.OfChar.uncheckedsearch(this.arr,size,v);
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
              final int i;
              if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Integer){
                if((i=(int)val)!=(char)i){
                  break returnFalse;
                }
              }else if(val instanceof Byte||val instanceof Short){
                if((i=((Number)val).shortValue())<0){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(char)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(char)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(char)d)){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return OmniArray.OfChar.uncheckedsearch(this.arr,size,i);
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
            return OmniArray.OfChar.uncheckedsearch(this.arr,size,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int search(short val){
      if(val>=0)
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfChar.uncheckedsearch(this.arr,size,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override boolean uncheckedremoveVal (int size
    ,int val
    ){
      final var arr=this.arr;
      for(int index=--size;;--index){
        if(
        val==arr[index]
        )
        {
          OmniArray.OfChar.removeIndexAndPullDown(arr,index,size);
          this.size=size;
          return true;
        }else if(index==0){
          return false;
        }
      }
    }
    @Override void uncheckedForEach(int size,CharConsumer action){
      {
        OmniArray.OfChar.descendingForEach(this.arr,0,size-1,action);
      }
    }
    private static class Itr
      extends AbstractCharItr
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
      private Itr(UncheckedStack parent,int cursor){
        this.parent=parent;
        this.cursor=cursor;
      }
      @Override public Object clone(){
        return new Itr(this);
      }
      @Override public boolean hasNext(){
        return this.cursor>0;
      }
      @Override public char nextChar(){
        return (char)parent.arr[--cursor];
      }
      @Override public void remove(){
        final UncheckedStack root;
        OmniArray.OfChar.removeIndexAndPullDown((root=this.parent).arr,this.cursor,--root.size);
      }
      @Override public void forEachRemaining(CharConsumer action){
        final int cursor;
        if((cursor=this.cursor)>0){
          OmniArray.OfChar.descendingForEach(parent.arr,0,cursor-1,action);
          this.cursor=0;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Character> action){
        final int cursor;
        if((cursor=this.cursor)>0){
          OmniArray.OfChar.descendingForEach(parent.arr,0,cursor-1,action::accept);
          this.cursor=0;
        }
      }
    }
    @Override public OmniIterator.OfChar iterator(){
      return new Itr(this);
    }
    @Override public void push(Character val){
      push((char)val);
    }
    @Override void uncheckedCopyInto(char[] dst,int length){
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override void uncheckedCopyInto(Object[] dst,int length){
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override void uncheckedCopyInto(Character[] dst,int length){
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override void uncheckedCopyInto(double[] dst,int length){
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override void uncheckedCopyInto(float[] dst,int length){
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override void uncheckedCopyInto(long[] dst,int length){
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override void uncheckedCopyInto(int[] dst,int length){
      ArrCopy.uncheckedReverseCopy(this.arr,0,dst,0,length);
    }
    @Override public Character pop(){
      return popChar();
    }
    @Override public char popChar(){
      return (char)arr[--this.size];
    }
    @Override public char pollChar(){
      int size;
      if((size=this.size)!=0){
        final var ret=(char)(arr[--size]);
        this.size=size;
        return ret;
      }
      return Character.MIN_VALUE;
    }
    @Override public char peekChar(){
      final int size;
      if((size=this.size)!=0){
        return (char)(arr[size-1]);
      }
      return Character.MIN_VALUE;
    }
    @Override public Character poll(){
      int size;
      if((size=this.size)!=0){
        final var ret=(Character)(arr[--size]);
        this.size=size;
        return ret;
      }
      return null;
    }
    @Override public Character peek(){
      final int size;
      if((size=this.size)!=0){
        return (Character)(arr[size-1]);
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
    @Override public long pollLong(){
      int size;
      if((size=this.size)!=0){
        final var ret=(long)(arr[--size]);
        this.size=size;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override public long peekLong(){
      final int size;
      if((size=this.size)!=0){
        return (long)(arr[size-1]);
      }
      return Long.MIN_VALUE;
    }
    @Override public int pollInt(){
      int size;
      if((size=this.size)!=0){
        final var ret=(int)(arr[--size]);
        this.size=size;
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override public int peekInt(){
      final int size;
      if((size=this.size)!=0){
        return (int)(arr[size-1]);
      }
      return Integer.MIN_VALUE;
    }
  }
  public
    static class UncheckedList
      extends CharArrSeq
      implements CharListDefault,Cloneable,RandomAccess
  {
    private static final long serialVersionUID=1L;
    public UncheckedList(){
      super();
    }
    public UncheckedList(int initialCapacity){
      super(initialCapacity);
    }
    UncheckedList(int size,char[] arr){
      super(size,arr);
    }
    @Override public boolean equals(Object val){
      //TODO implements equals method for UncheckedList
      return false;
    }
    @Override public Object clone(){
      final char[] copy;
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(this.arr,0,copy=new char[size],0,size);
      }else{
        copy=OmniArray.OfChar.DEFAULT_ARR;
      }
      return new UncheckedList(size,copy);
    }
    void uncheckedToString(int size,char[] buffer){
      OmniArray.OfChar.ascendingToString(this.arr,0,size-1,buffer,1);
    }
  @Override int uncheckedHashCode(int size){
    return OmniArray.OfChar.ascendingSeqHashCode(this.arr,0,size-1);
  }
    @Override public int indexOf(boolean val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfChar.uncheckedindexOf(this.arr,size,TypeUtil.castToChar(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(int val){
      if(val==(char)val)
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfChar.uncheckedindexOf(this.arr,size,(val));
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
            final char v;
            if((v=(char)val)==val){
              return OmniArray.OfChar.uncheckedindexOf(this.arr,size,v);
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
            final char v;
            if(val==(v=(char)val))
            {
              return OmniArray.OfChar.uncheckedindexOf(this.arr,size,v);
            }
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
            final char v;
            if(val==(v=(char)val))
            {
              return OmniArray.OfChar.uncheckedindexOf(this.arr,size,v);
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
              final int i;
              if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Integer){
                if((i=(int)val)!=(char)i){
                  break returnFalse;
                }
              }else if(val instanceof Byte||val instanceof Short){
                if((i=((Number)val).shortValue())<0){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(char)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(char)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(char)d)){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return OmniArray.OfChar.uncheckedindexOf(this.arr,size,i);
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
            return OmniArray.OfChar.uncheckedindexOf(this.arr,size,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(short val){
      if(val>=0)
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfChar.uncheckedindexOf(this.arr,size,(val));
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
            return OmniArray.OfChar.uncheckedlastIndexOf(this.arr,size,TypeUtil.castToChar(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(int val){
      if(val==(char)val)
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfChar.uncheckedlastIndexOf(this.arr,size,(val));
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
            final char v;
            if((v=(char)val)==val){
              return OmniArray.OfChar.uncheckedlastIndexOf(this.arr,size,v);
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
            final char v;
            if(val==(v=(char)val))
            {
              return OmniArray.OfChar.uncheckedlastIndexOf(this.arr,size,v);
            }
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
            final char v;
            if(val==(v=(char)val))
            {
              return OmniArray.OfChar.uncheckedlastIndexOf(this.arr,size,v);
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
              final int i;
              if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Integer){
                if((i=(int)val)!=(char)i){
                  break returnFalse;
                }
              }else if(val instanceof Byte||val instanceof Short){
                if((i=((Number)val).shortValue())<0){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(char)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(char)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(char)d)){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return OmniArray.OfChar.uncheckedlastIndexOf(this.arr,size,i);
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
            return OmniArray.OfChar.uncheckedlastIndexOf(this.arr,size,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(short val){
      if(val>=0)
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfChar.uncheckedlastIndexOf(this.arr,size,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override boolean uncheckedremoveVal (int size
    ,int val
    ){
      final var arr=this.arr;
      for(int index=0;;){
        if(
        val==arr[index]
        )
        {
          OmniArray.OfChar.removeIndexAndPullDown(arr,index,--size);
          this.size=size;
          return true;
        }else if(++index==size){
          return false;
        }
      }
    }
    @Override void uncheckedForEach(int size,CharConsumer action){
      {
        OmniArray.OfChar.ascendingForEach(this.arr,0,size-1,action);
      }
    }
    private static class Itr
      extends AbstractCharItr
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
      @Override public char nextChar(){
        return (char)parent.arr[cursor++];
      }
      @Override public void remove(){
        final UncheckedList root;
        OmniArray.OfChar.removeIndexAndPullDown((root=this.parent).arr,--this.cursor,--root.size);
      }
      @Override public void forEachRemaining(CharConsumer action){
        final int cursor,bound;
        final UncheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size)){
          OmniArray.OfChar.ascendingForEach(parent.arr,cursor,bound-1,action);
          this.cursor=bound;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Character> action){
        final int cursor,bound;
        final UncheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size)){
          OmniArray.OfChar.ascendingForEach(parent.arr,cursor,bound-1,action::accept);
          this.cursor=bound;
        }
      }
    }
    @Override public OmniIterator.OfChar iterator(){
      return new Itr(this);
    }
    private static class ListItr extends Itr implements OmniListIterator.OfChar{
      transient int lastRet;
      private ListItr(ListItr itr){
        super(itr);
        this.lastRet=itr.lastRet;
      }
      private ListItr(UncheckedList parent){
        super(parent);
        this.lastRet=-1;
      }
      private ListItr(UncheckedList parent,int cursor){
        super(parent,cursor);
        this.lastRet=-1;
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
      @Override public char nextChar(){
        int lastRet;
        this.lastRet=lastRet=this.cursor++;
        return (char)parent.arr[lastRet];
      }
      @Override public void remove(){
        final UncheckedList root;
        final int lastRet;
        OmniArray.OfChar.removeIndexAndPullDown((root=this.parent).arr,lastRet=this.lastRet,--root.size);
        this.cursor=lastRet;
      }
      @Override public void forEachRemaining(CharConsumer action){
        int cursor;
        final int bound;
        final UncheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size)){
          OmniArray.OfChar.ascendingForEach(parent.arr,cursor,cursor=bound-1,action);
          this.lastRet=cursor;
          this.cursor=bound;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Character> action){
        final int cursor,bound;
        final UncheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size)){
          OmniArray.OfChar.ascendingForEach(parent.arr,cursor,bound-1,action::accept);
          this.cursor=bound;
        }
      }
      @Override public char previousChar(){
        final int lastRet;
        this.lastRet=lastRet=--this.cursor;
        return (char)parent.arr[lastRet];
      }
      @Override public void set(char val){
        parent.arr[this.lastRet]=val;
      }
      @Override public void add(char val){
        final UncheckedList root;
        final int rootSize;
        if((rootSize=(root=this.parent).size)!=0)
        {
          ((UncheckedList)root).uncheckedInsert(this.cursor++,rootSize,val);
        }else{
          ((CharArrSeq)root).uncheckedInit(val);
          ++this.cursor;
        }
      }
    }
    @Override public OmniListIterator.OfChar listIterator(){
      return new ListItr(this);
    }
    @Override public OmniListIterator.OfChar listIterator(int index){
      return new ListItr(this,index);
    }
      private void uncheckedInsert(int index,int size,char val){
        final int tailDist;
        if((tailDist=size-index)==0){
          super.uncheckedAppend(size,val);
        }else{
          char[] arr;
          if((arr=this.arr).length==size){
            final char[] tmp;
            ArrCopy.semicheckedCopy(arr,0,tmp=new char[OmniArray.growBy50Pct(size)],0,index);
            ArrCopy.uncheckedCopy(arr,index,tmp,index+1,tailDist);
            this.arr=arr=tmp;
          }else{
            ArrCopy.uncheckedCopy(arr,index,arr,index+1,tailDist);
          }
          arr[index]=val;
          this.size=size+1;
        }
      }
    @Override public void add(int index,char val){
      final int size;
      if((size=this.size)!=0){
        ((UncheckedList)this).uncheckedInsert(index,size,val);
      }else{
        ((CharArrSeq)this).uncheckedInit(val);
      }
    }
    @Override void uncheckedCopyInto(char[] dst,int length){
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override void uncheckedCopyInto(Object[] dst,int length){
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override void uncheckedCopyInto(Character[] dst,int length){
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override void uncheckedCopyInto(double[] dst,int length){
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override void uncheckedCopyInto(float[] dst,int length){
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override void uncheckedCopyInto(long[] dst,int length){
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override void uncheckedCopyInto(int[] dst,int length){
      ArrCopy.uncheckedCopy(this.arr,0,dst,0,length);
    }
    @Override public void put(int index,char val){
      this.arr[index]=val;
    }
    @Override public char getChar(int index){
      return (char)this.arr[index];
    }
    @Override public char set(int index,char val){
      final char[] arr;
      final var ret=(char)(arr=this.arr)[index];
      arr[index]=val;
      return ret;
    }
    @Override public char removeCharAt(int index){
      final char[] arr;
      final var ret=(char)(arr=this.arr)[index];
      OmniArray.OfChar.removeIndexAndPullDown(arr,index,--size);
      return ret;
    }
    @Override public void replaceAll(CharUnaryOperator operator){
      final int size;
      if((size=this.size)!=0){
        OmniArray.OfChar.uncheckedReplaceAll(this.arr,0,size,operator);
      }
    }
    @Override
    public void sort(CharComparator sorter)
    {
      final int size;
      if((size=this.size)>1){
        if(sorter==null){
          CharSortUtil.uncheckedAscendingSort(this.arr,0,size);
        }else{
          CharSortUtil.uncheckedStableSort(this.arr,0,size,sorter);
        }
      }
    }
    @Override
    public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)>1){
        CharSortUtil.uncheckedAscendingSort(this.arr,0,size);
      }
    }
    @Override
    public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1){
        CharSortUtil.uncheckedDescendingSort(this.arr,0,size);
      }
    }
    @Override public void replaceAll(UnaryOperator<Character> operator){
      final int size;
      if((size=this.size)!=0){
        OmniArray.OfChar.uncheckedReplaceAll(this.arr,0,size,operator::apply);
      }
    }
    @Override
    public void sort(Comparator<? super Character> sorter)
    {
      final int size;
      if((size=this.size)>1){
        if(sorter==null){
          CharSortUtil.uncheckedAscendingSort(this.arr,0,size);
        }else{
          CharSortUtil.uncheckedStableSort(this.arr,0,size,sorter::compare);
        }
      }
    }
    @Override
    public void unstableSort(CharComparator sorter)
    {
      final int size;
      if((size=this.size)>1){
        if(sorter==null){
          CharSortUtil.uncheckedAscendingSort(this.arr,0,size);
        }else{
          CharSortUtil.uncheckedUnstableSort(this.arr,0,size,sorter);
        }
      }
    }
    @Override public OmniList.OfChar subList(int fromIndex,int toIndex){
      return new UncheckedSubList(this,fromIndex,toIndex-fromIndex);
    }
  }
  public
    static class UncheckedSubList
      extends AbstractSeq<Character>
      implements CharSubListDefault,Cloneable,RandomAccess
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
    private static class SerializableSubList implements Serializable{
      private static final long serialVersionUID=1L;
      private transient char[] arr;
      private transient int size;
      private transient final int rootOffset;
      private SerializableSubList(char[] arr,int size,int rootOffset
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
          char[] arr;
          OmniArray.OfChar.readArray(arr=new char[size],0,size-1,ois);
          this.arr=arr;
        }
      }
      private void writeObject(ObjectOutputStream oos) throws IOException{
        {
          int size;
          oos.writeInt(size=this.size);
          if(size!=0){
            final int rootOffset;
            OmniArray.OfChar.writeArray(arr,rootOffset=this.rootOffset,rootOffset+size-1,oos);
          }
        }
      }
    }
    private Object writeReplace(){
      return new SerializableSubList(root.arr,this.size,this.rootOffset);
    }
    @Override public boolean equals(Object val){
      //TODO implements equals method for UncheckedSubList
      return false;
    }
    @Override public Object clone(){
      final char[] copy;
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(root.arr,rootOffset,copy=new char[size],0,size);
      }else{
        copy=OmniArray.OfChar.DEFAULT_ARR;
      }
      return new UncheckedList(size,copy);
    }
    @Override public String toString(){
      int size;
      if((size=this.size)!=0){
          final int rootOffset;
          final char[] buffer;
          OmniArray.OfChar.ascendingToString(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,buffer=new char[size*=3],1);
          buffer[0]='[';
          buffer[size-1]=']';
          return new String(buffer,0,size);
      }
      return "[]";
    }
  @Override public int hashCode(){
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return OmniArray.OfChar.ascendingSeqHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
    }
    return 1;
  }
    @Override public void clear(){
      final int size;
      if((size=this.size)!=0){
        for(var curr=parent;curr!=null;curr.size-=size,curr=curr.parent){}
        final UncheckedList root;
        (root=this.root).size=OmniArray.OfChar.removeRangeAndPullDown(root.arr,this.rootOffset+size,root.size,size);
        this.size=0;
      }
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedremoveVal(size,TypeUtil.castToChar(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(int val){
      if(val==(char)val)
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedremoveVal(size,(val));
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
            final char v;
            if((v=(char)val)==val){
              return this.uncheckedremoveVal(size,v);
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
            final char v;
            if(val==(v=(char)val))
            {
              return this.uncheckedremoveVal(size,v);
            }
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
            final char v;
            if(val==(v=(char)val))
            {
              return this.uncheckedremoveVal(size,v);
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
              final int i;
              if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Integer){
                if((i=(int)val)!=(char)i){
                  break returnFalse;
                }
              }else if(val instanceof Byte||val instanceof Short){
                if((i=((Number)val).shortValue())<0){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(char)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(char)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(char)d)){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return this.uncheckedremoveVal(size,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(byte val){
      if(val>=0)
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedremoveVal(size,(val));
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
            return this.uncheckedremoveVal(size,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean removeVal(short val){
      if(val>=0)
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedremoveVal(size,(val));
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
            return OmniArray.OfChar.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,TypeUtil.castToChar(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(int val){
      if(val==(char)val)
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int rootOffset;
            return OmniArray.OfChar.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(val));
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
            final char v;
            if((v=(char)val)==val){
              final int rootOffset;
              return OmniArray.OfChar.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,v);
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
            final char v;
            if(val==(v=(char)val))
            {
              final int rootOffset;
              return OmniArray.OfChar.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,v);
            }
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
            final char v;
            if(val==(v=(char)val))
            {
              final int rootOffset;
              return OmniArray.OfChar.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,v);
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
              final int i;
              if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Integer){
                if((i=(int)val)!=(char)i){
                  break returnFalse;
                }
              }else if(val instanceof Byte||val instanceof Short){
                if((i=((Number)val).shortValue())<0){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(char)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(char)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(char)d)){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              final int rootOffset;
              return OmniArray.OfChar.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(byte val){
      if(val>=0)
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int rootOffset;
            return OmniArray.OfChar.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(val));
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
            return OmniArray.OfChar.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return false;
    }
    @Override public boolean contains(short val){
      if(val>=0)
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            final int rootOffset;
            return OmniArray.OfChar.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(val));
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
            return OmniArray.OfChar.uncheckedindexOf(root.arr,this.rootOffset,size,TypeUtil.castToChar(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(int val){
      if(val==(char)val)
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfChar.uncheckedindexOf(root.arr,this.rootOffset,size,(val));
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
            final char v;
            if((v=(char)val)==val){
              return OmniArray.OfChar.uncheckedindexOf(root.arr,this.rootOffset,size,v);
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
            final char v;
            if(val==(v=(char)val))
            {
              return OmniArray.OfChar.uncheckedindexOf(root.arr,this.rootOffset,size,v);
            }
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
            final char v;
            if(val==(v=(char)val))
            {
              return OmniArray.OfChar.uncheckedindexOf(root.arr,this.rootOffset,size,v);
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
              final int i;
              if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Integer){
                if((i=(int)val)!=(char)i){
                  break returnFalse;
                }
              }else if(val instanceof Byte||val instanceof Short){
                if((i=((Number)val).shortValue())<0){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(char)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(char)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(char)d)){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return OmniArray.OfChar.uncheckedindexOf(root.arr,this.rootOffset,size,i);
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
            return OmniArray.OfChar.uncheckedindexOf(root.arr,this.rootOffset,size,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int indexOf(short val){
      if(val>=0)
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfChar.uncheckedindexOf(root.arr,this.rootOffset,size,(val));
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
            return OmniArray.OfChar.uncheckedlastIndexOf(root.arr,this.rootOffset,size,TypeUtil.castToChar(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(int val){
      if(val==(char)val)
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfChar.uncheckedlastIndexOf(root.arr,this.rootOffset,size,(val));
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
            final char v;
            if((v=(char)val)==val){
              return OmniArray.OfChar.uncheckedlastIndexOf(root.arr,this.rootOffset,size,v);
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
            final char v;
            if(val==(v=(char)val))
            {
              return OmniArray.OfChar.uncheckedlastIndexOf(root.arr,this.rootOffset,size,v);
            }
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
            final char v;
            if(val==(v=(char)val))
            {
              return OmniArray.OfChar.uncheckedlastIndexOf(root.arr,this.rootOffset,size,v);
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
              final int i;
              if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Integer){
                if((i=(int)val)!=(char)i){
                  break returnFalse;
                }
              }else if(val instanceof Byte||val instanceof Short){
                if((i=((Number)val).shortValue())<0){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(char)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(char)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(char)d)){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return OmniArray.OfChar.uncheckedlastIndexOf(root.arr,this.rootOffset,size,i);
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
            return OmniArray.OfChar.uncheckedlastIndexOf(root.arr,this.rootOffset,size,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(short val){
      if(val>=0)
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return OmniArray.OfChar.uncheckedlastIndexOf(root.arr,this.rootOffset,size,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      return -1;
    }
    private boolean uncheckedremoveVal (int size
    ,int val
    ){
      final UncheckedList root;
      final var arr=(root=this.root).arr;
      for(int index=this.rootOffset,bound=index+(--size);;++index){
        if(
        val==arr[index]
        )
        {
          for(var curr=parent;curr!=null;--curr.size,curr=curr.parent){}
          OmniArray.OfChar.removeIndexAndPullDown(arr,index,--root.size);
          this.size=size;
          return true;
        }else if(index==bound){
          return false;
        }
      }
    }
    @Override public void forEach(CharConsumer action){
      {
        final int size;
        if((size=this.size)!=0){
          final int rootOffset;
          OmniArray.OfChar.ascendingForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,action);
        }
      }
    }
    @Override public void forEach(Consumer<? super Character> action){
      {
        final int size;
        if((size=this.size)!=0){
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
      @Override public char nextChar(){
        return (char)parent.root.arr[cursor++];
      }
      @Override public void remove(){
        UncheckedSubList parent;
        final UncheckedList root;
        OmniArray.OfChar.removeIndexAndPullDown((root=(parent=this.parent).root).arr,--this.cursor,--root.size);
        do{
          --parent.size;
        }while((parent=parent.parent)!=null);
      }
      @Override public void forEachRemaining(CharConsumer action){
        final int cursor,bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size)){
          OmniArray.OfChar.ascendingForEach(parent.root.arr,cursor,bound-1,action);
          this.cursor=bound;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Character> action){
        final int cursor,bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size)){
          OmniArray.OfChar.ascendingForEach(parent.root.arr,cursor,bound-1,action::accept);
          this.cursor=bound;
        }
      }
    }
    @Override public OmniIterator.OfChar iterator(){
      return new Itr(this);
    }
    private static class ListItr extends Itr implements OmniListIterator.OfChar{
      transient int lastRet;
      private ListItr(ListItr itr){
        super(itr);
        this.lastRet=itr.lastRet;
      }
      private ListItr(UncheckedSubList parent){
        super(parent);
        this.lastRet=-1;
      }
      private ListItr(UncheckedSubList parent,int cursor){
        super(parent,cursor);
        this.lastRet=-1;
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
      @Override public char nextChar(){
        int lastRet;
        this.lastRet=lastRet=this.cursor++;
        return (char)parent.root.arr[lastRet];
      }
      @Override public void remove(){
        UncheckedSubList parent;
        final UncheckedList root;
        final int lastRet;
        OmniArray.OfChar.removeIndexAndPullDown((root=(parent=this.parent).root).arr,lastRet=this.lastRet,--root.size);
        this.cursor=lastRet;
        do{
          --parent.size;
        }while((parent=parent.parent)!=null);
      }
      @Override public void forEachRemaining(CharConsumer action){
        int cursor;
        final int bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size)){
          OmniArray.OfChar.ascendingForEach(parent.root.arr,cursor,cursor=bound-1,action);
          this.lastRet=cursor;
          this.cursor=bound;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Character> action){
        final int cursor,bound;
        final UncheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size)){
          OmniArray.OfChar.ascendingForEach(parent.root.arr,cursor,bound-1,action::accept);
          this.cursor=bound;
        }
      }
      @Override public char previousChar(){
        final int lastRet;
        this.lastRet=lastRet=--this.cursor;
        return (char)parent.root.arr[lastRet];
      }
      @Override public void set(char val){
        parent.root.arr[this.lastRet]=val;
      }
      @Override public void add(char val){
        final UncheckedList root;
        final int rootSize;
        UncheckedSubList parent;
        if((rootSize=(root=(parent=this.parent).root).size)!=0)
        {
          ((UncheckedList)root).uncheckedInsert(this.cursor++,rootSize,val);
        }else{
          ((CharArrSeq)root).uncheckedInit(val);
          ++this.cursor;
        }
        do{
          ++parent.size;
        }while((parent=parent.parent)!=null);
      }
    }
    @Override public OmniListIterator.OfChar listIterator(){
      return new ListItr(this);
    }
    @Override public OmniListIterator.OfChar listIterator(int index){
      return new ListItr(this,index+this.rootOffset);
    }
    @Override public boolean add(char val){
      for(var curr=parent;curr!=null;++curr.size,curr=curr.parent){}
      final UncheckedList root;
      final int rootSize;
      if((rootSize=(root=this.root).size)!=0){
        ((UncheckedList)root).uncheckedInsert(this.rootOffset+(this.size++),rootSize,val);
      }else{
        ((CharArrSeq)root).uncheckedInit(val);
        ++this.size;
      }
      return true;
    }
    @Override public void add(int index,char val){
      for(var curr=parent;curr!=null;++curr.size,curr=curr.parent){}
      ++this.size;
      final UncheckedList root;
      final int rootSize;
      if((rootSize=(root=this.root).size)!=0){
        ((UncheckedList)root).uncheckedInsert(this.rootOffset+index,rootSize,val);
      }else{
        ((CharArrSeq)root).uncheckedInit(val);
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
    @Override public char[] toCharArray(){
      final int size;
      if((size=this.size)!=0){
        final char[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new char[size],0,size);
        return dst;
      }
      return OmniArray.OfChar.DEFAULT_ARR;
    }
    @Override public Character[] toArray(){
      final int size;
      if((size=this.size)!=0){
        final Character[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new Character[size],0,size);
        return dst;
      }
      return OmniArray.OfChar.DEFAULT_BOXED_ARR;
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
    @Override public float[] toFloatArray(){
      final int size;
      if((size=this.size)!=0){
        final float[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new float[size],0,size);
        return dst;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override public long[] toLongArray(){
      final int size;
      if((size=this.size)!=0){
        final long[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new long[size],0,size);
        return dst;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override public int[] toIntArray(){
      final int size;
      if((size=this.size)!=0){
        final int[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new int[size],0,size);
        return dst;
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
    @Override public void put(int index,char val){
      root.arr[index+this.rootOffset]=val;
    }
    @Override public char getChar(int index){
      return (char)root.arr[index+this.rootOffset];
    }
    @Override public char set(int index,char val){
      final char[] arr;
      final var ret=(char)(arr=root.arr)[index+=this.rootOffset];
      arr[index]=val;
      return ret;
    }
    @Override public char removeCharAt(int index){
      final char[] arr;
      for(var curr=parent;curr!=null;--curr.size,curr=curr.parent){}
      final UncheckedList root;
      final var ret=(char)(arr=(root=this.root).arr)[index+=this.rootOffset];
      OmniArray.OfChar.removeIndexAndPullDown(arr,index,--root.size);
      this.size=size-1;
      return ret;
    }
    @Override public boolean removeIf(CharPredicate filter){
      final int size;
      if((size=this.size)!=0){
        {
          final char[] arr;
          final int numRemoved;
          int rootOffset;
          final UncheckedList root;
          if((numRemoved=uncheckedRemoveIfImpl(arr=(root=this.root).arr,rootOffset=this.rootOffset,rootOffset+=size,filter))!=0){
            for(var curr=parent;curr!=null;curr.size-=numRemoved,curr=curr.parent){}
            root.size=OmniArray.OfChar.removeRangeAndPullDown(arr,rootOffset,root.size,numRemoved);
            this.size=size-numRemoved;
            return true;
          }
        }
      }
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Character> filter){
      final int size;
      if((size=this.size)!=0){
        {
          final char[] arr;
          final int numRemoved;
          int rootOffset;
          final UncheckedList root;
          if((numRemoved=uncheckedRemoveIfImpl(arr=(root=this.root).arr,rootOffset=this.rootOffset,rootOffset+=size,filter::test))!=0){
            for(var curr=parent;curr!=null;curr.size-=numRemoved,curr=curr.parent){}
            root.size=OmniArray.OfChar.removeRangeAndPullDown(arr,rootOffset,root.size,numRemoved);
            this.size=size-numRemoved;
            return true;
          }
        }
      }
      return false;
    }
    @Override public void replaceAll(CharUnaryOperator operator){
      final int size;
      if((size=this.size)!=0){
        final int rootOffset;
        OmniArray.OfChar.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);  
      }
    }
    @Override
    public void sort(CharComparator sorter)
    {
      final int size;
      if((size=this.size)>1){
        final int rootOffset;
        if(sorter==null){
          CharSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }else{
          CharSortUtil.uncheckedStableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter);
        }
      }
    }
    @Override
    public void stableAscendingSort()
    {
      final int size;
      if((size=this.size)>1){
        final int rootOffset;
        CharSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }
    }
    @Override
    public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1){
        final int rootOffset;
        CharSortUtil.uncheckedDescendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }
    }
    @Override public void replaceAll(UnaryOperator<Character> operator){
      final int size;
      if((size=this.size)!=0){
        final int rootOffset;
        OmniArray.OfChar.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator::apply);  
      }
    }
    @Override
    public void sort(Comparator<? super Character> sorter)
    {
      final int size;
      if((size=this.size)>1){
        final int rootOffset;
        if(sorter==null){
          CharSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }else{
          CharSortUtil.uncheckedStableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter::compare);
        }
      }
    }
    @Override
    public void unstableSort(CharComparator sorter)
    {
      final int size;
      if((size=this.size)>1){
        final int rootOffset;
        if(sorter==null){
          CharSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }else{
          CharSortUtil.uncheckedUnstableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter);
        }
      }
    }
    @Override public OmniList.OfChar subList(int fromIndex,int toIndex){
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
    CheckedStack(int size,char[] arr){
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
    @Override public boolean equals(Object val){
      //TODO implements equals method for CheckedStack
      return false;
    }
    @Override public Object clone(){
      final char[] copy;
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(this.arr,0,copy=new char[size],0,size);
      }else{
        copy=OmniArray.OfChar.DEFAULT_ARR;
      }
      return new CheckedStack(size,copy);
    }
    @Override public void clear(){
      if(this.size!=0){
        ++this.modCount;
        this.size=0;
      }
    }
    @Override boolean uncheckedremoveVal (int size
    ,int val
    ){
      final var arr=this.arr;
      for(int index=--size;;--index){
        if(
        val==arr[index]
        )
        {
          ++this.modCount;
          OmniArray.OfChar.removeIndexAndPullDown(arr,index,size);
          this.size=size;
          return true;
        }else if(index==0){
          return false;
        }
      }
    }
    @Override void uncheckedForEach(int size,CharConsumer action){
      int modCount=this.modCount;
      try
      {
        OmniArray.OfChar.descendingForEach(this.arr,0,size-1,action);
      }
      finally{
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
      private Itr(CheckedStack parent,int cursor){
        this.parent=parent;
        this.cursor=cursor;
        this.modCount=parent.modCount;
        this.lastRet=-1;
      }
      @Override public Object clone(){
        return new Itr(this);
      }
      @Override public boolean hasNext(){
        return this.cursor>0;
      }
      @Override public char nextChar(){
        final CheckedStack root;
        CheckedCollection.checkModCount(modCount,(root=this.parent).modCount);
        int cursor;
        if((cursor=this.cursor)>0)
        {  
          this.lastRet=--cursor;
          this.cursor=cursor;
          return (char)root.arr[cursor];
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
          OmniArray.OfChar.removeIndexAndPullDown(root.arr,lastRet,--root.size);
          this.cursor=lastRet;
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(CharConsumer action){
        final int cursor;
        if((cursor=this.cursor)>0){
          final int modCount=this.modCount;
          final var parent=this.parent;
          try{
            OmniArray.OfChar.descendingForEach(parent.arr,0,cursor-1,action);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount);
          }
          this.cursor=0;
          this.lastRet=0;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Character> action){
        final int cursor;
        if((cursor=this.cursor)>0){
          final int modCount=this.modCount;
          final var parent=this.parent;
          try{
            OmniArray.OfChar.descendingForEach(parent.arr,0,cursor-1,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount);
          }
          this.cursor=0;
          this.lastRet=0;
        }
      }
    }
    @Override public OmniIterator.OfChar iterator(){
      return new Itr(this);
    }
    @Override public void push(char val){
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
    @Override public char popChar(){
      int size;
      if((size=this.size)!=0){
        ++this.modCount;
        final var ret=(char)arr[--size];
        this.size=size;
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override public char pollChar(){
      int size;
      if((size=this.size)!=0){
        ++this.modCount;
        final var ret=(char)(arr[--size]);
        this.size=size;
        return ret;
      }
      return Character.MIN_VALUE;
    }
    @Override public Character poll(){
      int size;
      if((size=this.size)!=0){
        ++this.modCount;
        final var ret=(Character)(arr[--size]);
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
    @Override public long pollLong(){
      int size;
      if((size=this.size)!=0){
        ++this.modCount;
        final var ret=(long)(arr[--size]);
        this.size=size;
        return ret;
      }
      return Long.MIN_VALUE;
    }
    @Override public int pollInt(){
      int size;
      if((size=this.size)!=0){
        ++this.modCount;
        final var ret=(int)(arr[--size]);
        this.size=size;
        return ret;
      }
      return Integer.MIN_VALUE;
    }
    @Override boolean uncheckedRemoveIf(int size,CharPredicate filter){
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
    CheckedList(int size,char[] arr){
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
    @Override public boolean equals(Object val){
      //TODO implements equals method for CheckedList
      return false;
    }
    @Override public Object clone(){
      final char[] copy;
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(this.arr,0,copy=new char[size],0,size);
      }else{
        copy=OmniArray.OfChar.DEFAULT_ARR;
      }
      return new CheckedList(size,copy);
    }
    @Override public void clear(){
      if(this.size!=0){
        ++this.modCount;
        this.size=0;
      }
    }
    @Override boolean uncheckedremoveVal (int size
    ,int val
    ){
      final var arr=this.arr;
      for(int index=0;;){
        if(
        val==arr[index]
        )
        {
          ++this.modCount;
          OmniArray.OfChar.removeIndexAndPullDown(arr,index,--size);
          this.size=size;
          return true;
        }else if(++index==size){
          return false;
        }
      }
    }
    @Override void uncheckedForEach(int size,CharConsumer action){
      int modCount=this.modCount;
      try
      {
        OmniArray.OfChar.ascendingForEach(this.arr,0,size-1,action);
      }
      finally{
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
      @Override public char nextChar(){
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
      @Override public void remove(){
        final int lastRet;
        if((lastRet=this.lastRet)!=-1){
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
      @Override public void forEachRemaining(CharConsumer action){
        int cursor;
        final int bound;
        final CheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size)){
          final int modCount=this.modCount;
          try{
            OmniArray.OfChar.ascendingForEach(parent.arr,cursor,cursor=bound-1,action);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount);
          }
          this.cursor=bound;
          this.lastRet=cursor;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Character> action){
        int cursor;
        final int bound;
        final CheckedList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).size)){
          final int modCount=this.modCount;
          try{
            OmniArray.OfChar.ascendingForEach(parent.arr,cursor,cursor=bound-1,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCount,parent.modCount);
          }
          this.cursor=bound;
          this.lastRet=cursor;
        }
      }
    }
    @Override public OmniIterator.OfChar iterator(){
      return new Itr(this);
    }
    private static class ListItr extends Itr implements OmniListIterator.OfChar{
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
      @Override public char previousChar(){
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
      @Override public void set(char val){
        final int lastRet;
        if((lastRet=this.lastRet)!=-1){
          final CheckedList root;
          CheckedCollection.checkModCount(modCount,(root=this.parent).modCount);
          root.arr[lastRet]=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void add(char val){
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
          ((CharArrSeq)root).uncheckedInit(val);
          ++this.cursor;
        }
      }
    }
    @Override public OmniListIterator.OfChar listIterator(){
      return new ListItr(this);
    }
    @Override public OmniListIterator.OfChar listIterator(int index){
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,this.size);
      return new ListItr(this,index);
    }
    @Override public void push(char val){
      ++this.modCount;
      super.push(val);
    }
    @Override public void add(int index,char val){
      final int size;
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,size=this.size);
      ++this.modCount;
      if(size!=0){
        ((UncheckedList)this).uncheckedInsert(index,size,val);
      }else{
        ((CharArrSeq)this).uncheckedInit(val);
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
    @Override public void put(int index,char val){
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      this.arr[index]=val;
    }
    @Override public char getChar(int index){
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      return (char)this.arr[index];
    }
    @Override public char set(int index,char val){
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      final char[] arr;
      final var ret=(char)(arr=this.arr)[index];
      arr[index]=val;
      return ret;
    }
    @Override public char removeCharAt(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      final char[] arr;
      ++this.modCount;
      final var ret=(char)(arr=this.arr)[index];
      OmniArray.OfChar.removeIndexAndPullDown(arr,index,--size);
      this.size=size;
      return ret;
    }
    @Override boolean uncheckedRemoveIf(int size,CharPredicate filter){
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
    @Override public void replaceAll(CharUnaryOperator operator){
      final int size;
      if((size=this.size)!=0){
        int modCount=this.modCount;
        try{
          OmniArray.OfChar.uncheckedReplaceAll(this.arr,0,size,operator);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override
    public void sort(CharComparator sorter)
    {
      final int size;
      if((size=this.size)>1){
        if(sorter==null){
          CharSortUtil.uncheckedAscendingSort(this.arr,0,size);
          ++this.modCount;
        }else{
          final int modCount=this.modCount;
          try{
            CharSortUtil.uncheckedStableSort(this.arr,0,size,sorter);
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
        CharSortUtil.uncheckedAscendingSort(this.arr,0,size);
        this.modCount=modCount+1;
      }
    }
    @Override
    public void stableDescendingSort()
    {
      final int size;
      if((size=this.size)>1){
        CharSortUtil.uncheckedDescendingSort(this.arr,0,size);
        this.modCount=modCount+1;
      }
    }
    @Override public void replaceAll(UnaryOperator<Character> operator){
      final int size;
      if((size=this.size)!=0){
        int modCount=this.modCount;
        try{
          OmniArray.OfChar.uncheckedReplaceAll(this.arr,0,size,operator::apply);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
          this.modCount=modCount+1;
        }
      }
    }
    @Override
    public void sort(Comparator<? super Character> sorter)
    {
      final int size;
      if((size=this.size)>1){
        if(sorter==null){
          CharSortUtil.uncheckedAscendingSort(this.arr,0,size);
          ++this.modCount;
        }else{
          final int modCount=this.modCount;
          try{
            CharSortUtil.uncheckedStableSort(this.arr,0,size,sorter::compare);
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
    public void unstableSort(CharComparator sorter)
    {
      final int size;
      if((size=this.size)>1){
        if(sorter==null){
          CharSortUtil.uncheckedAscendingSort(this.arr,0,size);
          ++this.modCount;
        }else{
          final int modCount=this.modCount;
          try{
            CharSortUtil.uncheckedUnstableSort(this.arr,0,size,sorter);
          }catch(ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException("Comparison method violates its general contract!",e);
          }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.modCount=modCount+1;
          }
        }
      }
    }
    @Override public OmniList.OfChar subList(int fromIndex,int toIndex){
      return new CheckedSubList(this,fromIndex,CheckedCollection.checkSubListRange(fromIndex,toIndex,this.size));
    }
  }
  private
    static class CheckedSubList
      extends AbstractSeq<Character>
      implements CharSubListDefault,Cloneable,RandomAccess
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
    private static class SerializableSubList implements Serializable{
      private static final long serialVersionUID=1L;
      private transient char[] arr;
      private transient int size;
      private transient final int rootOffset;
      private transient final CheckedList.ModCountChecker modCountChecker;
      private SerializableSubList(char[] arr,int size,int rootOffset
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
          char[] arr;
          OmniArray.OfChar.readArray(arr=new char[size],0,size-1,ois);
          this.arr=arr;
        }
      }
      private void writeObject(ObjectOutputStream oos) throws IOException{
        try
        {
          int size;
          oos.writeInt(size=this.size);
          if(size!=0){
            final int rootOffset;
            OmniArray.OfChar.writeArray(arr,rootOffset=this.rootOffset,rootOffset+size-1,oos);
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
      //TODO implements equals method for CheckedSubList
      return false;
    }
    @Override public Object clone(){
      final CheckedList root;
      CheckedCollection.checkModCount(this.modCount,(root=this.root).modCount);
      final char[] copy;
      final int size;
      if((size=this.size)!=0){
        ArrCopy.uncheckedCopy(root.arr,rootOffset,copy=new char[size],0,size);
      }else{
        copy=OmniArray.OfChar.DEFAULT_ARR;
      }
      return new CheckedList(size,copy);
    }
    @Override public String toString(){
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      int size;
      if((size=this.size)!=0){
          final int rootOffset;
          final char[] buffer;
          OmniArray.OfChar.ascendingToString(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,buffer=new char[size*=3],1);
          buffer[0]='[';
          buffer[size-1]=']';
          return new String(buffer,0,size);
      }
      return "[]";
    }
  @Override public int hashCode(){
    final CheckedList root;
    CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
    final int size;
    if((size=this.size)!=0){
      final int rootOffset;
      return OmniArray.OfChar.ascendingSeqHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size-1);
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
        root.size=OmniArray.OfChar.removeRangeAndPullDown(root.arr,this.rootOffset+size,root.size,size);
        this.size=0;
      }
    }
    @Override public boolean removeVal(boolean val){
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedremoveVal(size,TypeUtil.castToChar(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(int val){
      if(val==(char)val)
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedremoveVal(size,(val));
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
            final char v;
            if((v=(char)val)==val){
              return this.uncheckedremoveVal(size,v);
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
            final char v;
            if(val==(v=(char)val))
            {
              return this.uncheckedremoveVal(size,v);
            }
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
            final char v;
            if(val==(v=(char)val))
            {
              return this.uncheckedremoveVal(size,v);
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
              final int i;
              if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Integer){
                if((i=(int)val)!=(char)i){
                  break returnFalse;
                }
              }else if(val instanceof Byte||val instanceof Short){
                if((i=((Number)val).shortValue())<0){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(char)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(char)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(char)d)){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
              return this.uncheckedremoveVal(size,i);
            }
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(byte val){
      if(val>=0)
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedremoveVal(size,(val));
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
            return this.uncheckedremoveVal(size,(val));
          } //end size check
        } //end checked sublist try modcount
      }//end val check
      CheckedCollection.checkModCount(modCount,root.modCount);
      return false;
    }
    @Override public boolean removeVal(short val){
      if(val>=0)
      {
        {
          final int size;
          if((size=this.size)!=0)
          {
            return this.uncheckedremoveVal(size,(val));
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
              return OmniArray.OfChar.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,TypeUtil.castToChar(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return false;
    }
    @Override public boolean contains(int val){
      if(val==(char)val)
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              final int rootOffset;
              return OmniArray.OfChar.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(val));
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
            final char v;
            if((v=(char)val)==val){
                final int rootOffset;
                return OmniArray.OfChar.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,v);
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
            final char v;
            if(val==(v=(char)val))
            {
                final int rootOffset;
                return OmniArray.OfChar.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,v);
            }
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
            final char v;
            if(val==(v=(char)val))
            {
                final int rootOffset;
                return OmniArray.OfChar.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,v);
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
              final int i;
              if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Integer){
                if((i=(int)val)!=(char)i){
                  break returnFalse;
                }
              }else if(val instanceof Byte||val instanceof Short){
                if((i=((Number)val).shortValue())<0){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(char)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(char)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(char)d)){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
                final int rootOffset;
                return OmniArray.OfChar.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,i);
            }
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return false;
    }
    @Override public boolean contains(byte val){
      if(val>=0)
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              final int rootOffset;
              return OmniArray.OfChar.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(val));
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
              return OmniArray.OfChar.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return false;
    }
    @Override public boolean contains(short val){
      if(val>=0)
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              final int rootOffset;
              return OmniArray.OfChar.uncheckedcontains(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,(val));
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
              return OmniArray.OfChar.uncheckedindexOf(root.arr,this.rootOffset,size,TypeUtil.castToChar(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int indexOf(int val){
      if(val==(char)val)
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              return OmniArray.OfChar.uncheckedindexOf(root.arr,this.rootOffset,size,(val));
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
            final char v;
            if((v=(char)val)==val){
                return OmniArray.OfChar.uncheckedindexOf(root.arr,this.rootOffset,size,v);
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
            final char v;
            if(val==(v=(char)val))
            {
                return OmniArray.OfChar.uncheckedindexOf(root.arr,this.rootOffset,size,v);
            }
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
            final char v;
            if(val==(v=(char)val))
            {
                return OmniArray.OfChar.uncheckedindexOf(root.arr,this.rootOffset,size,v);
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
              final int i;
              if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Integer){
                if((i=(int)val)!=(char)i){
                  break returnFalse;
                }
              }else if(val instanceof Byte||val instanceof Short){
                if((i=((Number)val).shortValue())<0){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(char)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(char)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(char)d)){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
                return OmniArray.OfChar.uncheckedindexOf(root.arr,this.rootOffset,size,i);
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
              return OmniArray.OfChar.uncheckedindexOf(root.arr,this.rootOffset,size,(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int indexOf(short val){
      if(val>=0)
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              return OmniArray.OfChar.uncheckedindexOf(root.arr,this.rootOffset,size,(val));
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
              return OmniArray.OfChar.uncheckedlastIndexOf(root.arr,this.rootOffset,size,TypeUtil.castToChar(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(int val){
      if(val==(char)val)
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              return OmniArray.OfChar.uncheckedlastIndexOf(root.arr,this.rootOffset,size,(val));
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
            final char v;
            if((v=(char)val)==val){
                return OmniArray.OfChar.uncheckedlastIndexOf(root.arr,this.rootOffset,size,v);
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
            final char v;
            if(val==(v=(char)val))
            {
                return OmniArray.OfChar.uncheckedlastIndexOf(root.arr,this.rootOffset,size,v);
            }
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
            final char v;
            if(val==(v=(char)val))
            {
                return OmniArray.OfChar.uncheckedlastIndexOf(root.arr,this.rootOffset,size,v);
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
              final int i;
              if(val instanceof Character){
                i=(char)val;
              }else if(val instanceof Integer){
                if((i=(int)val)!=(char)i){
                  break returnFalse;
                }
              }else if(val instanceof Byte||val instanceof Short){
                if((i=((Number)val).shortValue())<0){
                  break returnFalse;
                }
              }else if(val instanceof Long){
                final long l;
                if((l=(long)val)!=(i=(char)l)){
                  break returnFalse;
                }
              }else if(val instanceof Float){
                final float f;
                if((f=(float)val)!=(i=(char)f)){
                  break returnFalse;
                }
              }else if(val instanceof Double){
                final double d;
                if((d=(double)val)!=(i=(char)d)){
                  break returnFalse;
                }
              }else if(val instanceof Boolean){
                i=TypeUtil.castToByte((boolean)val);
              }else{
                break returnFalse;
              }
                return OmniArray.OfChar.uncheckedlastIndexOf(root.arr,this.rootOffset,size,i);
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
              return OmniArray.OfChar.uncheckedlastIndexOf(root.arr,this.rootOffset,size,(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    @Override public int lastIndexOf(short val){
      if(val>=0)
      {
        final var root=this.root;
        final int modCount=this.modCount;
        try
        {
          final int size;
          if((size=this.size)!=0)
          {
              return OmniArray.OfChar.uncheckedlastIndexOf(root.arr,this.rootOffset,size,(val));
          } //end size check
        } //end checked sublist try modcount
        finally{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }//end val check
      return -1;
    }
    private boolean uncheckedremoveVal (int size
    ,int val
    ){
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      final var arr=root.arr;
      for(int index=this.rootOffset,bound=index+(--size);;++index){
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
        }else if(index==bound){
          return false;
        }
      }
    }
    @Override public void forEach(CharConsumer action){
      int modCount=this.modCount;
      final var root=this.root;
      try
      {
        final int size;
        if((size=this.size)!=0){
          final int rootOffset;
          OmniArray.OfChar.ascendingForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,action);
        }
      }
      finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
      }
    }
    @Override public void forEach(Consumer<? super Character> action){
      int modCount=this.modCount;
      final var root=this.root;
      try
      {
        final int size;
        if((size=this.size)!=0){
          final int rootOffset;
          OmniArray.OfChar.ascendingForEach(root.arr,rootOffset=this.rootOffset,rootOffset+size-1,action::accept);
        }
      }
      finally{
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
      @Override public char nextChar(){
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
          OmniArray.OfChar.removeIndexAndPullDown(root.arr,lastRet,--root.size);
          this.cursor=lastRet;
          this.lastRet=-1;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void forEachRemaining(CharConsumer action){
        int cursor;
        final int bound;
        final CheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size)){
          final int modCount=this.modCount;
          final var root=parent.root;
          try{
            OmniArray.OfChar.ascendingForEach(root.arr,cursor,cursor=bound-1,action);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
          this.cursor=bound;
          this.lastRet=cursor;
        }
      }
      @Override public void forEachRemaining(Consumer<? super Character> action){
        int cursor;
        final int bound;
        final CheckedSubList parent;
        if((cursor=this.cursor)<(bound=(parent=this.parent).rootOffset+parent.size)){
          final int modCount=this.modCount;
          final var root=parent.root;
          try{
            OmniArray.OfChar.ascendingForEach(root.arr,cursor,cursor=bound-1,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCount,root.modCount);
          }
          this.cursor=bound;
          this.lastRet=cursor;
        }
      }
    }
    @Override public OmniIterator.OfChar iterator(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new Itr(this);
    }
    private static class ListItr extends Itr implements OmniListIterator.OfChar{
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
      @Override public char previousChar(){
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
      @Override public void set(char val){
        final int lastRet;
        if((lastRet=this.lastRet)!=-1){
          final CheckedList root;
          CheckedCollection.checkModCount(modCount,(root=this.parent.root).modCount);
          root.arr[lastRet]=val;
          return;
        }
        throw new IllegalStateException();
      }
      @Override public void add(char val){
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
          ((CharArrSeq)root).uncheckedInit(val);
          ++this.cursor;
        }
      }
    }
    @Override public OmniListIterator.OfChar listIterator(){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new ListItr(this);
    }
    @Override public OmniListIterator.OfChar listIterator(int index){
      CheckedCollection.checkModCount(modCount,root.modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkWriteHi(index,this.size);
      return new ListItr(this,index+this.rootOffset);
    }
    @Override public boolean add(char val){
      final CheckedList root;
      int modCount;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,++curr.size,curr=curr.parent){}
      if((modCount=root.size)!=0){
        ((UncheckedList)root).uncheckedInsert(this.rootOffset+(this.size++),modCount,val);
      }else{
        ((CharArrSeq)root).uncheckedInit(val);
        ++this.size;
      }
      return true;
    }
    @Override public void add(int index,char val){
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
        ((CharArrSeq)root).uncheckedInit(val);
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
    @Override public char[] toCharArray(){
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0){
        final char[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new char[size],0,size);
        return dst;
      }
      return OmniArray.OfChar.DEFAULT_ARR;
    }
    @Override public Character[] toArray(){
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0){
        final Character[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new Character[size],0,size);
        return dst;
      }
      return OmniArray.OfChar.DEFAULT_BOXED_ARR;
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
    @Override public long[] toLongArray(){
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0){
        final long[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new long[size],0,size);
        return dst;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override public int[] toIntArray(){
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      final int size;
      if((size=this.size)!=0){
        final int[] dst;
        ArrCopy.uncheckedCopy(root.arr,this.rootOffset,dst=new int[size],0,size);
        return dst;
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
    @Override public void put(int index,char val){
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      root.arr[index+this.rootOffset]=val;
    }
    @Override public char getChar(int index){
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      return (char)root.arr[index+this.rootOffset];
    }
    @Override public char set(int index,char val){
      final CheckedList root;
      CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,this.size);
      final char[] arr;
      final var ret=(char)(arr=root.arr)[index+=this.rootOffset];
      arr[index]=val;
      return ret;
    }
    @Override public char removeCharAt(int index){
      int modCount;
      final CheckedList root;
      CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      final char[] arr;
      root.modCount=++modCount;
      this.modCount=modCount;
      for(var curr=parent;curr!=null;curr.modCount=modCount,--curr.size,curr=curr.parent){}
      final var ret=(char)(arr=root.arr)[index+=this.rootOffset];
      OmniArray.OfChar.removeIndexAndPullDown(arr,index,--root.size);
      this.size=size-1;
      return ret;
    }
    @Override public boolean removeIf(CharPredicate filter){
      int modCount=this.modCount;
      final var root=this.root;
      final int size;
      if((size=this.size)!=0){
        try
        {
          final char[] arr;
          final int numRemoved;
          int rootOffset;
          if((numRemoved=uncheckedRemoveIfImpl(arr=root.arr,rootOffset=this.rootOffset,rootOffset+=size,filter,root.new ModCountChecker(modCount)))!=0){
            root.modCount=++modCount;
            this.modCount=modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,curr.size-=numRemoved,curr=curr.parent){}
            root.size=OmniArray.OfChar.removeRangeAndPullDown(arr,rootOffset,root.size,numRemoved);
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
    @Override public boolean removeIf(Predicate<? super Character> filter){
      int modCount=this.modCount;
      final var root=this.root;
      final int size;
      if((size=this.size)!=0){
        try
        {
          final char[] arr;
          final int numRemoved;
          int rootOffset;
          if((numRemoved=uncheckedRemoveIfImpl(arr=root.arr,rootOffset=this.rootOffset,rootOffset+=size,filter::test,root.new ModCountChecker(modCount)))!=0){
            root.modCount=++modCount;
            this.modCount=modCount;
            for(var curr=parent;curr!=null;curr.modCount=modCount,curr.size-=numRemoved,curr=curr.parent){}
            root.size=OmniArray.OfChar.removeRangeAndPullDown(arr,rootOffset,root.size,numRemoved);
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
    @Override public void replaceAll(CharUnaryOperator operator){
      final int size;
      if((size=this.size)==0){
        CheckedCollection.checkModCount(modCount,root.modCount);
        return;
      }
      int modCount=this.modCount;
      final var root=this.root;
      try{
        final int rootOffset;
        OmniArray.OfChar.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator);  
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
      }
    }
    @Override
    public void sort(CharComparator sorter)
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
        CharSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
      }else{
        try{
          final int rootOffset;
          CharSortUtil.uncheckedStableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter);
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
        CharSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
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
        CharSortUtil.uncheckedDescendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}  
      }
    }
    @Override public void replaceAll(UnaryOperator<Character> operator){
      final int size;
      if((size=this.size)==0){
        CheckedCollection.checkModCount(modCount,root.modCount);
        return;
      }
      int modCount=this.modCount;
      final var root=this.root;
      try{
        final int rootOffset;
        OmniArray.OfChar.uncheckedReplaceAll(root.arr,rootOffset=this.rootOffset,rootOffset+size,operator::apply);  
      }finally{
        CheckedCollection.checkModCount(modCount,root.modCount);
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
      }
    }
    @Override
    public void sort(Comparator<? super Character> sorter)
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
        CharSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
      }else{
        try{
          final int rootOffset;
          CharSortUtil.uncheckedStableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter::compare);
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
    public void unstableSort(CharComparator sorter)
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
        CharSortUtil.uncheckedAscendingSort(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        root.modCount=++modCount;
        this.modCount=modCount;
        for(var curr=parent;curr!=null;curr.modCount=modCount,curr=curr.parent){}
      }else{
        try{
          final int rootOffset;
          CharSortUtil.uncheckedUnstableSort(root.arr,rootOffset=this.rootOffset,rootOffset+size,sorter);
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
    @Override public OmniList.OfChar subList(int fromIndex,int toIndex){
      CheckedCollection.checkModCount(modCount,root.modCount);
      return new CheckedSubList(this,this.rootOffset+fromIndex,CheckedCollection.checkSubListRange(fromIndex,toIndex,this.size));
    }
  }
}
