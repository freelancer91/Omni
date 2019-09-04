package omni.impl.set;
import java.util.Set;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import omni.api.OmniSet;
import omni.api.OmniIterator;
import omni.function.ByteConsumer;
import omni.function.BytePredicate;
import omni.impl.AbstractByteItr;
import omni.impl.CheckedCollection;
import omni.util.OmniArray;
import omni.util.ToStringUtil;
public class ByteSetImpl implements OmniSet.OfByte,Cloneable,Externalizable{
  private static final long serialVersionUID=1L;
  transient long word0;
  transient long word1;
  transient long word2;
  transient long word3;
  public ByteSetImpl(){
    super();
  }
  ByteSetImpl(long word0,long word1,long word2,long word3){
    this.word0=word0;
    this.word1=word1;
    this.word2=word2;
    this.word3=word3;
  }
  public ByteSetImpl(ByteSetImpl that){
    this.word0=that.word0;
    this.word1=that.word1;
    this.word2=that.word2;
    this.word3=that.word3;
  }
  @Override public Object clone(){
      return new ByteSetImpl(this);
  }
  private static long processWordToString(long word,int valOffset,int valBound,byte[] buffer,long magicWord){
    int bufferOffset=(int)(magicWord >>> 32);
    int numLeft=(int)magicWord;
    do{
      if((word & 1L << valOffset) != 0L){
        bufferOffset=ToStringUtil.getStringShort(valOffset,buffer,++bufferOffset);
        if(--numLeft == 0){
          break;
        }
        buffer[bufferOffset]=',';
        buffer[++bufferOffset]=' ';
      }
    }while(++valOffset != valBound);
    return numLeft | (long)bufferOffset << 32;
  }
  private static long processWordHashCode(long word,int valOffset,int valBound,long magicWord){
    int hash=(int)(magicWord >>> 32);
    int numLeft=(int)magicWord;
    do{
      if((word & 1L << valOffset) != 0L){
        hash+=valOffset;
        if(--numLeft == 0){
          break;
        }
      }
    }while(++valOffset != valBound);
    return numLeft | (long)hash << 32;
  }
  private static String toStringHelper(long word0,long word1,long word2,long word3,int size) {
    final byte[] buffer;
    (buffer=new byte[size * 6])[0]='[';
    long magicWord;
    if((int)(magicWord=processWordToString(word0,Byte.MIN_VALUE,-64,buffer,size)) != 0){
      if((int)(magicWord=processWordToString(word1,-64,0,buffer,magicWord)) != 0){
        if((int)(magicWord=processWordToString(word2,0,64,buffer,magicWord)) != 0){
          magicWord=processWordToString(word3,64,128,buffer,magicWord);
        }
      }
    }
    buffer[size=(int)(magicWord >>> 32)]=']';
    return new String(buffer,0,size + 1,ToStringUtil.IOS8859CharSet);
  }
  private static int hashCodeHelper(long word0,long word1,long word2,long word3,int size) {
    long magicWord;
    if((int)(magicWord=processWordHashCode(word0,Byte.MIN_VALUE,-64,size)) != 0){
      if((int)(magicWord=processWordHashCode(word1,-64,0,magicWord)) != 0){
        if((int)(magicWord=processWordHashCode(word2,0,64,magicWord)) != 0){
          magicWord=processWordHashCode(word3,64,128,magicWord);
        }
      }
    }
    return (int)(magicWord >>> 32);
  }
  @Override public String toString(){
    int size;
    long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3)) != 0){
      return toStringHelper(word0,word1,word2,word3,size);
    }
    return "[]";
  }
  @Override public int hashCode(){
    int size;
    long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3)) != 0){
      return hashCodeHelper(word0,word1,word2,word3,size);
    }
    return 0;
  }
  private boolean isEqualTo(ByteSetImpl thatSet){
    return this.word0==thatSet.word0 && this.word1==thatSet.word1 && this.word2==thatSet.word2 && this.word3==thatSet.word3;
  }
  @Override public boolean equals(Object val){
    if(val==this){
      return true;
    }
    if(val instanceof Set){
      if(val instanceof ByteSetImpl){
        return isEqualTo((ByteSetImpl)val);
      }else if(val instanceof RefOpenAddressHashSet){
        return SetCommonImpl.isEqualTo((RefOpenAddressHashSet<?>)val,this);
      }else{
        return SetCommonImpl.isEqualTo((Set<?>)val,this);
      }
    }
    return false;
  }
  @Override public void writeExternal(ObjectOutput out) throws IOException{
    out.writeLong(word0);
    out.writeLong(word1);
    out.writeLong(word2);
    out.writeLong(word3);
  }
  @Override public void readExternal(ObjectInput in) throws IOException{
    word0=in.readLong();
    word1=in.readLong();
    word2=in.readLong();
    word3=in.readLong();
  }
  @Override public void clear(){
    word0=0;
    word1=0;
    word2=0;
    word3=0;
  }
  private boolean uncheckedRemoveByte(int val){
    long word,mask=~(1L << val);
    switch(val >> 6){
    case -2:
      return (word=this.word0) != (this.word0=word & mask);
    case -1:
      return (word=this.word1) != (this.word1=word & mask);
    case 0:
      return (word=this.word2) != (this.word2=word & mask);
    default:
      return (word=this.word3) != (this.word3=word & mask);
    }
  }
  @Override public boolean contains(Object val){
    if(val instanceof Byte){
      return contains((byte)val);
    }else if(val instanceof Integer || val instanceof Short){
      return contains(((Number)val).intValue());
    }else if(val instanceof Long){
      return contains((long)val);
    }else if(val instanceof Float){
      return contains((float)val);
    }else if(val instanceof Double){
      return contains((double)val);
    }else if(val instanceof Character){
      return contains((char)val);
    }else if(val instanceof Boolean){
      return contains((boolean)val);
    }
    return false;
  }
  @Override public boolean contains(boolean val){
    return (this.word2&(val?2L:1L))!=0;
  }
  @Override public boolean contains(byte val){
    long mask=1L << val;
    switch(val >> 6){
    case -2:
      return (this.word0&(mask))!=0;
    case -1:
      return (this.word1&(mask))!=0;
    case 0:
      return (this.word2&(mask))!=0;
    default:
      return (this.word3&(mask))!=0;
    }
  }
  @Override public boolean contains(char val){
    switch(val>>6){
    case 0:
      return (this.word2&(1L<<val))!=0;
    case 1:
      return (this.word3&(1L<<val))!=0;
    default:
      return false;
    }
  }
  @Override public boolean contains(int val){
    switch(val >> 6){
    case -2:
      return (this.word0&(1L<<val))!=0;
    case -1:
      return (this.word1&(1L<<val))!=0;
    case 0:
      return (this.word2&(1L<<val))!=0;
    case 1:
      return (this.word3&(1L<<val))!=0;
    default:
      return false;
    }
  }
  @Override public boolean contains(long val){
      int v;
      return (v=(int)val) == val && contains(v);
  }
  @Override public boolean contains(float val){
      int v;
      return (v=(int)val) == val && contains(v);
  }
  @Override public boolean contains(double val){
      int v;
      return (v=(int)val) == val && contains(v);
  }
  @Override public boolean remove(Object val){
    for(;;) {
      int v;
      if(val instanceof Byte){
        return uncheckedRemoveByte((byte)val);
      }else if(val instanceof Integer || val instanceof Short){
        v=((Number)val).intValue();
      }else if(val instanceof Long){
        long l;
        if((v=(int)(l=(long)val)) != l) {
          break;
        }
      }else if(val instanceof Float){
        float f;
        if((v=(int)(f=(float)val)) != f) {
          break;
        }
      }else if(val instanceof Double){
        double d;
        if((v=(int)(d=(double)val)) != d) {
          break;
        }
      }else if(val instanceof Character){
        return uncheckedRemoveChar((char)val);
      }else if(val instanceof Boolean){
        return uncheckedRemoveBoolean((boolean)val);
      }else {
        break;
      }
      return uncheckedRemoveInt(v);
    }
    return false;
  }
  @Override public boolean removeVal(boolean val){
    return uncheckedRemoveBoolean(val);
  }
  @Override public boolean removeVal(byte val){
    return uncheckedRemoveByte(val);
  }
  @Override public boolean removeVal(char val){
    return uncheckedRemoveChar(val);
  }
  private boolean uncheckedRemoveBoolean(boolean val) {
    long word;
    return (word=this.word2) != (this.word2=word & (val?~2L:~1L));
  }
  private boolean uncheckedRemoveInt(int val){
    long word;
    switch(val >> 6){
    case -2:
      return (word=this.word0) != (this.word0=word & (~(1L<<val)));
    case -1:
      return (word=this.word1) != (this.word1=word & (~(1L<<val)));
    case 0:
      return (word=this.word2) != (this.word2=word & (~(1L<<val)));
    case 1:
      return (word=this.word3) != (this.word3=word & (~(1L<<val)));
    default:
      return false;
    }
  }
  private boolean uncheckedRemoveChar(int val) {
    long word;
    switch(val>>6){
    case 0:
      return (word=this.word2) != (this.word2=word & (~(1L<<val)));
    case 1:
      return (word=this.word3) != (this.word3=word & (~(1L<<val)));
    default:
      return false;
    }
  }
  @Override public boolean removeVal(int val){
    return uncheckedRemoveInt(val);
  }
  @Override public boolean removeVal(long val){
    int v;
    return (v=(int)val) == val && uncheckedRemoveInt(v);
  }
  @Override public boolean removeVal(float val){
    int v;
    return (v=(int)val) == val && uncheckedRemoveInt(v);
  }
  @Override public boolean removeVal(double val){
    int v;
    return (v=(int)val) == val && uncheckedRemoveInt(v);
  }
  @Override public boolean isEmpty(){
    return word0 == 0 && word1 == 0 && word2 == 0 && word3 == 0;
  }
  @Override public int size(){
    return SetCommonImpl.size(word0,word1,word2,word3);
  }
  private static int processWordCopyToArray(long word,int valOffset,int valBound,Object[] dst,int dstOffset){
    do{
      if((word & 1L << --valBound) != 0L){
        dst[--dstOffset]=(byte)(valBound);
        if(dstOffset == 0){
          break;
        }
      }
    }while(valBound != valOffset);
    return dstOffset;
  }
  private static void toArrayHelper(long word0,long word1,long word2,long word3,int size,Object[] arr) {
    if((size=processWordCopyToArray(word3,64,128,arr,size)) != 0){
      if((size=processWordCopyToArray(word2,0,64,arr,size)) != 0){
        if((size=processWordCopyToArray(word1,-64,0,arr,size)) != 0){
          for(int valBound=-65;;--valBound){
            if((word0 & 1L << valBound) != 0L){
              arr[--size]=(byte)(valBound);
              if(size == 0){
                  break;
              }
            }
          }
        }
      }
    }
  }
  private static int processWordCopyToArray(long word,int valOffset,int valBound,Byte[] dst,int dstOffset){
    do{
      if((word & 1L << --valBound) != 0L){
        dst[--dstOffset]=(byte)(valBound);
        if(dstOffset == 0){
          break;
        }
      }
    }while(valBound != valOffset);
    return dstOffset;
  }
  private static void toArrayHelper(long word0,long word1,long word2,long word3,int size,Byte[] arr) {
    if((size=processWordCopyToArray(word3,64,128,arr,size)) != 0){
      if((size=processWordCopyToArray(word2,0,64,arr,size)) != 0){
        if((size=processWordCopyToArray(word1,-64,0,arr,size)) != 0){
          for(int valBound=-65;;--valBound){
            if((word0 & 1L << valBound) != 0L){
              arr[--size]=(byte)(valBound);
              if(size == 0){
                  break;
              }
            }
          }
        }
      }
    }
  }
  @Override public Byte[] toArray(){
    long word0,word1,word2,word3;
    int size;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3)) != 0){
        Byte[] dst;
        toArrayHelper(word0,word1,word2,word3,size,dst=new Byte[size]);
        return dst;
    }
    return OmniArray.OfByte.DEFAULT_BOXED_ARR;
  }
  private static int processWordCopyToArray(long word,int valOffset,int valBound,byte[] dst,int dstOffset){
    do{
      if((word & 1L << --valBound) != 0L){
        dst[--dstOffset]=(byte)(valBound);
        if(dstOffset == 0){
          break;
        }
      }
    }while(valBound != valOffset);
    return dstOffset;
  }
  private static void toArrayHelper(long word0,long word1,long word2,long word3,int size,byte[] arr) {
    if((size=processWordCopyToArray(word3,64,128,arr,size)) != 0){
      if((size=processWordCopyToArray(word2,0,64,arr,size)) != 0){
        if((size=processWordCopyToArray(word1,-64,0,arr,size)) != 0){
          for(int valBound=-65;;--valBound){
            if((word0 & 1L << valBound) != 0L){
              arr[--size]=(byte)(valBound);
              if(size == 0){
                  break;
              }
            }
          }
        }
      }
    }
  }
  @Override public byte[] toByteArray(){
    long word0,word1,word2,word3;
    int size;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3)) != 0){
        byte[] dst;
        toArrayHelper(word0,word1,word2,word3,size,dst=new byte[size]);
        return dst;
    }
    return OmniArray.OfByte.DEFAULT_ARR;
  }
  private static int processWordCopyToArray(long word,int valOffset,int valBound,short[] dst,int dstOffset){
    do{
      if((word & 1L << --valBound) != 0L){
        dst[--dstOffset]=(short)(valBound);
        if(dstOffset == 0){
          break;
        }
      }
    }while(valBound != valOffset);
    return dstOffset;
  }
  private static void toArrayHelper(long word0,long word1,long word2,long word3,int size,short[] arr) {
    if((size=processWordCopyToArray(word3,64,128,arr,size)) != 0){
      if((size=processWordCopyToArray(word2,0,64,arr,size)) != 0){
        if((size=processWordCopyToArray(word1,-64,0,arr,size)) != 0){
          for(int valBound=-65;;--valBound){
            if((word0 & 1L << valBound) != 0L){
              arr[--size]=(short)(valBound);
              if(size == 0){
                  break;
              }
            }
          }
        }
      }
    }
  }
  @Override public short[] toShortArray(){
    long word0,word1,word2,word3;
    int size;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3)) != 0){
        short[] dst;
        toArrayHelper(word0,word1,word2,word3,size,dst=new short[size]);
        return dst;
    }
    return OmniArray.OfShort.DEFAULT_ARR;
  }
  private static int processWordCopyToArray(long word,int valOffset,int valBound,int[] dst,int dstOffset){
    do{
      if((word & 1L << --valBound) != 0L){
        dst[--dstOffset]=(valBound);
        if(dstOffset == 0){
          break;
        }
      }
    }while(valBound != valOffset);
    return dstOffset;
  }
  private static void toArrayHelper(long word0,long word1,long word2,long word3,int size,int[] arr) {
    if((size=processWordCopyToArray(word3,64,128,arr,size)) != 0){
      if((size=processWordCopyToArray(word2,0,64,arr,size)) != 0){
        if((size=processWordCopyToArray(word1,-64,0,arr,size)) != 0){
          for(int valBound=-65;;--valBound){
            if((word0 & 1L << valBound) != 0L){
              arr[--size]=(valBound);
              if(size == 0){
                  break;
              }
            }
          }
        }
      }
    }
  }
  @Override public int[] toIntArray(){
    long word0,word1,word2,word3;
    int size;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3)) != 0){
        int[] dst;
        toArrayHelper(word0,word1,word2,word3,size,dst=new int[size]);
        return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  private static int processWordCopyToArray(long word,int valOffset,int valBound,long[] dst,int dstOffset){
    do{
      if((word & 1L << --valBound) != 0L){
        dst[--dstOffset]=(valBound);
        if(dstOffset == 0){
          break;
        }
      }
    }while(valBound != valOffset);
    return dstOffset;
  }
  private static void toArrayHelper(long word0,long word1,long word2,long word3,int size,long[] arr) {
    if((size=processWordCopyToArray(word3,64,128,arr,size)) != 0){
      if((size=processWordCopyToArray(word2,0,64,arr,size)) != 0){
        if((size=processWordCopyToArray(word1,-64,0,arr,size)) != 0){
          for(int valBound=-65;;--valBound){
            if((word0 & 1L << valBound) != 0L){
              arr[--size]=(valBound);
              if(size == 0){
                  break;
              }
            }
          }
        }
      }
    }
  }
  @Override public long[] toLongArray(){
    long word0,word1,word2,word3;
    int size;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3)) != 0){
        long[] dst;
        toArrayHelper(word0,word1,word2,word3,size,dst=new long[size]);
        return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  private static int processWordCopyToArray(long word,int valOffset,int valBound,float[] dst,int dstOffset){
    do{
      if((word & 1L << --valBound) != 0L){
        dst[--dstOffset]=(valBound);
        if(dstOffset == 0){
          break;
        }
      }
    }while(valBound != valOffset);
    return dstOffset;
  }
  private static void toArrayHelper(long word0,long word1,long word2,long word3,int size,float[] arr) {
    if((size=processWordCopyToArray(word3,64,128,arr,size)) != 0){
      if((size=processWordCopyToArray(word2,0,64,arr,size)) != 0){
        if((size=processWordCopyToArray(word1,-64,0,arr,size)) != 0){
          for(int valBound=-65;;--valBound){
            if((word0 & 1L << valBound) != 0L){
              arr[--size]=(valBound);
              if(size == 0){
                  break;
              }
            }
          }
        }
      }
    }
  }
  @Override public float[] toFloatArray(){
    long word0,word1,word2,word3;
    int size;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3)) != 0){
        float[] dst;
        toArrayHelper(word0,word1,word2,word3,size,dst=new float[size]);
        return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  private static int processWordCopyToArray(long word,int valOffset,int valBound,double[] dst,int dstOffset){
    do{
      if((word & 1L << --valBound) != 0L){
        dst[--dstOffset]=(valBound);
        if(dstOffset == 0){
          break;
        }
      }
    }while(valBound != valOffset);
    return dstOffset;
  }
  private static void toArrayHelper(long word0,long word1,long word2,long word3,int size,double[] arr) {
    if((size=processWordCopyToArray(word3,64,128,arr,size)) != 0){
      if((size=processWordCopyToArray(word2,0,64,arr,size)) != 0){
        if((size=processWordCopyToArray(word1,-64,0,arr,size)) != 0){
          for(int valBound=-65;;--valBound){
            if((word0 & 1L << valBound) != 0L){
              arr[--size]=(valBound);
              if(size == 0){
                  break;
              }
            }
          }
        }
      }
    }
  }
  @Override public double[] toDoubleArray(){
    long word0,word1,word2,word3;
    int size;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3)) != 0){
        double[] dst;
        toArrayHelper(word0,word1,word2,word3,size,dst=new double[size]);
        return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    long word0,word1,word2,word3;
    int size;
    T[] arr=arrConstructor.apply(size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3));
    if(size != 0){
      toArrayHelper(word0,word1,word2,word3,size,arr);
    }
    return arr;
  }
  @Override public <T> T[] toArray(T[] dst){
    long word0,word1,word2,word3;
    int size;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3)) != 0){
      toArrayHelper(word0,word1,word2,word3,size,dst=OmniArray.uncheckedArrResize(size,dst));
    }else if(dst.length != 0){
      dst[0]=null;
    }
    return dst;
  }
  @Override public boolean add(boolean val){
    long word;
    return (word=this.word2) != (this.word2=word | (val?2L:1L));
  }
  @Override public boolean add(byte val){
    long word,mask=1L << val;
    switch(val >> 6){
    case -2:
      return (word=this.word0) != (this.word0=word | (mask));
    case -1:
      return (word=this.word1) != (this.word1=word | (mask));
    case 0:
      return (word=this.word2) != (this.word2=word | (mask));
    default:
      return (word=this.word3) != (this.word3=word | (mask));
    }
  }
  @Override public boolean add(Byte val){
    return add((byte)val);
  }
  private static int processWordForEach(long word,int valOffset,int valBound,ByteConsumer action,int numLeft){
    do{
      if((word & 1L << valOffset) != 0L){
        action.accept((byte)valOffset);
        if(--numLeft == 0){
          break;
        }
      }
    }while(++valOffset != valBound);
    return numLeft;
  }
  private static void forEachHelper(long word0,long word1,long word2,long word3,int size,ByteConsumer action) {
    if((size=processWordForEach(word0,Byte.MIN_VALUE,-64,action,size)) != 0){
      if((size=processWordForEach(word1,-64,0,action,size)) != 0){
        if((size=processWordForEach(word2,0,64,action,size)) != 0){
          for(int valOffset=64;;++valOffset){
            if((word3 & 1L << valOffset) != 0L){
              action.accept((byte)valOffset);
              if(--size == 0){
                break;
              }
            }
          }
        }
      }
    }
  }
  @Override public void forEach(ByteConsumer action){
    long word0,word1,word2,word3;
    int size;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3)) != 0){
      forEachHelper(word0,word1,word2,word3,size,action);
    }
  }
  @Override public void forEach(Consumer<? super Byte> action){
    long word0,word1,word2,word3;
    int size;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3)) != 0){
      forEachHelper(word0,word1,word2,word3,size,action::accept);
    }
  }
  private static long processWordRemoveIf(long word,int valOffset,BytePredicate filter){
    long marker=1L;
    for(;;){
      if((word & marker) != 0){
        if(filter.test((byte)valOffset)){
          word&=~marker;
        }
      }
      if((marker<<=1) == 0){
        return word;
      }
      ++valOffset;
    }
  }
  @Override public boolean removeIf(BytePredicate filter){
    long word;
    return (word=this.word0) != (this.word0=processWordRemoveIf(word,Byte.MIN_VALUE,filter))
      | (word=this.word1) != (this.word1=processWordRemoveIf(word,-64,filter))
      | (word=this.word2) != (this.word2=processWordRemoveIf(word,0,filter))
      | (word=this.word3) != (this.word3=processWordRemoveIf(word,64,filter));
  }
  private static long processWordRemoveIf(long word,int valOffset,Predicate<? super Byte> filter){
    long marker=1L;
    for(;;){
      if((word & marker) != 0){
        if(filter.test((byte)valOffset)){
          word&=~marker;
        }
      }
      if((marker<<=1) == 0){
        return word;
      }
      ++valOffset;
    }
  }
  @Override public boolean removeIf(Predicate<? super Byte> filter){
    long word;
    return (word=this.word0) != (this.word0=processWordRemoveIf(word,Byte.MIN_VALUE,filter))
      | (word=this.word1) != (this.word1=processWordRemoveIf(word,-64,filter))
      | (word=this.word2) != (this.word2=processWordRemoveIf(word,0,filter))
      | (word=this.word3) != (this.word3=processWordRemoveIf(word,64,filter));
  }
  private static class Itr extends AbstractByteItr{
    private final ByteSetImpl root;
    private int valOffset;
    private Itr(Itr itr){
      root=itr.root;
      valOffset=itr.valOffset;
    }
    private Itr(ByteSetImpl root){
        this.root=root;
        int tail0s;
        if((tail0s=Long.numberOfTrailingZeros(root.word0)) != 64){
            this.valOffset=tail0s - 128;
        }else if((tail0s=Long.numberOfTrailingZeros(root.word1)) != 64){
            this.valOffset=tail0s - 64;
        }else if((tail0s=Long.numberOfTrailingZeros(root.word2)) != 64){
            this.valOffset=tail0s;
        }else{
            this.valOffset=Long.numberOfTrailingZeros(root.word3) + 64;
        }
    }
    @Override public Object clone(){
      return new Itr(this);
    }
    @Override public boolean hasNext(){
        return this.valOffset != 128;
    }
    @Override public byte nextByte(){
        int valOffset;
        var ret=(byte)(valOffset=this.valOffset);
        var root=this.root;
        switch(++valOffset >> 6){
        case -2:
            int tail0s;
            if((tail0s=Long.numberOfTrailingZeros(root.word0 >>> valOffset)) != 64){
                this.valOffset=valOffset+tail0s;
                break;
            }
            valOffset=-64;
        case -1:
            if((tail0s=Long.numberOfTrailingZeros(root.word1 >>> valOffset)) != 64){
                this.valOffset=valOffset+tail0s;
                break;
            }
            valOffset=0;
        case 0:
            if((tail0s=Long.numberOfTrailingZeros(root.word2 >>> valOffset)) != 64){
                this.valOffset=valOffset+tail0s;
                break;
            }
            valOffset=64;
        case 1:
            if((tail0s=Long.numberOfTrailingZeros(root.word3 >>> valOffset)) != 64){
               this.valOffset=valOffset+tail0s;
               break;
            }
        default:
            this.valOffset=128;
        }
        return ret;
    }
    private static void forEachRemainingHelper(long word,int valOffset,ByteConsumer action){
      for(long marker=1L << valOffset;;++valOffset){
        if((word & marker) != 0){
          action.accept((byte)valOffset);
        }
        if((marker<<=1) == 0){
          break;
        }
      }
    }
    @Override public void forEachRemaining(ByteConsumer action){
      var root=this.root;
      int valOffset;
      switch((valOffset=this.valOffset) >> 6){
        case -2:
          forEachRemainingHelper(root.word0,valOffset,action);
          valOffset=-64;
        case -1:
          forEachRemainingHelper(root.word1,valOffset,action);
          valOffset=0;
        case 0:
          forEachRemainingHelper(root.word2,valOffset,action);
          valOffset=64;
        case 1:
          forEachRemainingHelper(root.word3,valOffset,action);
          this.valOffset=128;
        default:
      }
    }
    private static void forEachRemainingHelper(long word,int valOffset,Consumer<? super Byte> action){
      for(long marker=1L << valOffset;;++valOffset){
        if((word & marker) != 0){
          action.accept((byte)valOffset);
        }
        if((marker<<=1) == 0){
          break;
        }
      }
    }
    @Override public void forEachRemaining(Consumer<? super Byte> action){
      var root=this.root;
      int valOffset;
      switch((valOffset=this.valOffset) >> 6){
        case -2:
          forEachRemainingHelper(root.word0,valOffset,action);
          valOffset=-64;
        case -1:
          forEachRemainingHelper(root.word1,valOffset,action);
          valOffset=0;
        case 0:
          forEachRemainingHelper(root.word2,valOffset,action);
          valOffset=64;
        case 1:
          forEachRemainingHelper(root.word3,valOffset,action);
          this.valOffset=128;
        default:
      }
    }
    @Override public void remove(){
      final var root=this.root;
      long word;
      int valOffset;
      switch((valOffset=this.valOffset)-1>>6)
      {
        case 1:
          if((valOffset=Long.numberOfLeadingZeros((word=root.word3)&(-1L>>>-valOffset)))!=64){
            root.word3=word&~(Long.MIN_VALUE>>>valOffset);
            return;
          }
          valOffset=0;
        case 0:
          if((valOffset=Long.numberOfLeadingZeros((word=root.word2)&(-1L>>>-valOffset)))!=64){
            root.word2=word&~(Long.MIN_VALUE>>>valOffset);
            return;
          }
          valOffset=0;
        case -1:
          if((valOffset=Long.numberOfLeadingZeros((word=root.word1)&(-1L>>>-valOffset)))!=64){
            root.word1=word&~(Long.MIN_VALUE>>>valOffset);
            return;
          }
          valOffset=0;
        default:
          root.word0=(word=root.word0)&~(Long.MIN_VALUE>>>Long.numberOfLeadingZeros(word&(-1L>>>-valOffset)));
      }
    }
  }
  @Override public OmniIterator.OfByte iterator(){
      return new Itr(this);
  }
  public static class Checked extends ByteSetImpl{
    transient int modCount;
    transient int size;
    public Checked(){
      super();
    }
    Checked(long word0,long word1,long word2,long word3){
      super(word0,word1,word2,word3);
      this.size=SetCommonImpl.size(word0,word1,word2,word3);
    }
    Checked(long word0,long word1,long word2,long word3,int size){
      super(word0,word1,word2,word3);
      this.size=size;
    }
    Checked(Checked that){
      super(that.word0,that.word1,that.word2,that.word3);
      this.size=that.size;
    }
    @Override public Object clone() {
      return new Checked(this);
    }
    @Override public String toString() {
      int size;
      if((size=this.size)!=0) {
        return toStringHelper(word0,word1,word2,word3,size);
      }
      return "[]";
    }
    @Override public int hashCode() {
      int size;
      if((size=this.size)!=0) {
        return hashCodeHelper(word0,word1,word2,word3,size);
      }
      return 0;
    }
    @Override public void writeExternal(ObjectOutput out) throws IOException{
      int modCount=this.modCount;
      try {
        super.writeExternal(out);
      }finally {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override public boolean equals(Object val){
      if(val==this){
        return true;
      }
      if(val instanceof Set){
        if(val instanceof ByteSetImpl){
          return super.isEqualTo((ByteSetImpl)val);
        }else if(val instanceof RefOpenAddressHashSet){
          return SetCommonImpl.isEqualTo((RefOpenAddressHashSet<?>)val,this);
        }else{
          return SetCommonImpl.isEqualTo((Set<?>)val,this);
        }
      }
      return false;
    }
    @Override public void readExternal(ObjectInput in) throws IOException{
      this.size=SetCommonImpl.size(word0=in.readLong(),word1=in.readLong(),word2=in.readLong(),word3=in.readLong());
    }
    @Override public void clear(){
      if(this.size!=0) {
        this.word0=0;
        this.word1=0;
        this.word2=0;
        this.word3=0;
        this.size=0;
        ++this.modCount;
      }
    }
    @Override public boolean contains(Object val){
      return size!=0 && super.contains(val);
    }
    @Override public boolean remove(Object val){
      for(;;) {
        int size;
        if((size=this.size)!=0) {
          if(val instanceof Byte){
            if(!super.uncheckedRemoveByte((byte)val)) {
              break;
            }
          }else if(val instanceof Integer || val instanceof Short){
            if(!super.uncheckedRemoveInt(((Number)val).intValue())) {
              break;
            }
          }else if(val instanceof Long){
            if(!super.removeVal((long)val)) {
              break;
            }
          }else if(val instanceof Float){
            if(!super.removeVal((float)val)) {
              break;
            }
          }else if(val instanceof Double){
            if(!super.removeVal((double)val)) {
              break;
            }
          }else if(val instanceof Character){
            if(!super.uncheckedRemoveChar((char)val)) {
              break;
            }
          }else if(val instanceof Boolean){
            if(!super.uncheckedRemoveBoolean((boolean)val)) {
              break;
            }
          }else{
            break;
          }
          ++this.modCount;
          this.size=size-1;
          return true;
        }
        break;
      }
      return false;
    }
    @Override public boolean removeVal(boolean val){
      if(super.uncheckedRemoveBoolean(val)) {
        ++this.modCount;
        --this.size;
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(byte val){
      if(super.uncheckedRemoveByte(val)) {
        ++this.modCount;
        --this.size;
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(char val){
      if(super.uncheckedRemoveChar(val)) {
        ++this.modCount;
        --this.size;
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(int val){
      if(super.uncheckedRemoveInt(val)) {
        ++this.modCount;
        --this.size;
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(long val){
      if(super.removeVal(val)) {
        ++this.modCount;
        --this.size;
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(float val){
      if(super.removeVal(val)) {
        ++this.modCount;
        --this.size;
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(double val){
      if(super.removeVal(val)) {
        ++this.modCount;
        --this.size;
        return true;
      }
      return false;
    }
    @Override public boolean isEmpty(){
      return this.size==0;
    }
    @Override public int size(){
      return this.size;
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      int size;
      T[] arr;
      int modCount=this.modCount;
      try {
        arr=arrConstructor.apply(size=this.size);
      }finally {
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
      if(size != 0){
        toArrayHelper(word0,word1,word2,word3,size,arr);
      }
      return arr;
    }
    @Override public <T> T[] toArray(T[] dst){
      int size;
      if((size=this.size) != 0){
        toArrayHelper(word0,word1,word2,word3,size,dst=OmniArray.uncheckedArrResize(size,dst));
      }else if(dst.length != 0){
        dst[0]=null;
      }
      return dst;
    }
    @Override public boolean add(boolean val){
      if(super.add(val)) {
        ++this.modCount;
        ++this.size;
        return true;
      }
      return false;
    }
    @Override public boolean add(byte val){
      if(super.add(val)) {
        ++this.modCount;
        ++this.size;
        return true;
      }
      return false;
    }
    @Override public void forEach(ByteConsumer action){
      int size;
      if((size=this.size)!=0) {
        int modCount=this.modCount;
        try {
          forEachHelper(word0,word1,word2,word3,size,action);
        }finally {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public void forEach(Consumer<? super Byte> action){
      int size;
      if((size=this.size)!=0) {
        int modCount=this.modCount;
        try {
          forEachHelper(word0,word1,word2,word3,size,action::accept);
        }finally {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      }
    }
    @Override public boolean removeIf(BytePredicate filter){
      int size;
      if((size=this.size)!=0) {
        int modCount=this.modCount;
        long word,newWord0,newWord1,newWord2,newWord3;
        int numRemoved;
        try {
          numRemoved=Long.bitCount((word=this.word0)^(newWord0=processWordRemoveIf(word,Byte.MIN_VALUE,filter)))
            +Long.bitCount((word=this.word1)^(newWord1=processWordRemoveIf(word,-64,filter)))
            +Long.bitCount((word=this.word2)^(newWord2=processWordRemoveIf(word,0,filter)))
            +Long.bitCount((word=this.word3)^(newWord3=processWordRemoveIf(word,64,filter)));
        }finally {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        if(numRemoved!=0) {
          this.word0=newWord0;
          this.word1=newWord1;
          this.word2=newWord2;
          this.word3=newWord3;
          this.size=size-numRemoved;
          this.modCount=modCount+1;
          return true;
        }
      }
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      int size;
      if((size=this.size)!=0) {
        int modCount=this.modCount;
        long word,newWord0,newWord1,newWord2,newWord3;
        int numRemoved;
        try {
          numRemoved=Long.bitCount((word=this.word0)^(newWord0=processWordRemoveIf(word,Byte.MIN_VALUE,filter)))
            +Long.bitCount((word=this.word1)^(newWord1=processWordRemoveIf(word,-64,filter)))
            +Long.bitCount((word=this.word2)^(newWord2=processWordRemoveIf(word,0,filter)))
            +Long.bitCount((word=this.word3)^(newWord3=processWordRemoveIf(word,64,filter)));
        }finally {
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
        if(numRemoved!=0) {
          this.word0=newWord0;
          this.word1=newWord1;
          this.word2=newWord2;
          this.word3=newWord3;
          this.size=size-numRemoved;
          this.modCount=modCount+1;
          return true;
        }
      }
      return false;
    }
    @Override public OmniIterator.OfByte iterator(){
      return new Itr(this);
    }
    private static class Itr extends AbstractByteItr{
      private final Checked root;
      private int valOffset;
      private int modCount;
      private int lastRet;
      private Itr(Itr itr){
        this.root=itr.root;
        this.valOffset=itr.valOffset;
        this.modCount=itr.modCount;
        this.lastRet=itr.lastRet;
      }
      private Itr(Checked root){
        this.root=root;
        int tail0s;
        if((tail0s=Long.numberOfTrailingZeros(root.word0)) != 64){
          this.valOffset=tail0s - 128;
        }else if((tail0s=Long.numberOfTrailingZeros(root.word1)) != 64){
          this.valOffset=tail0s - 64;
        }else if((tail0s=Long.numberOfTrailingZeros(root.word2)) != 64){
          this.valOffset=tail0s;
        }else{
          this.valOffset=Long.numberOfTrailingZeros(root.word3) + 64;
        }
        this.lastRet=-129;
        this.modCount=root.modCount;
      }
      @Override public Object clone(){
        return new Itr(this);
      }
      @Override public boolean hasNext(){
        return this.valOffset != 128;
      }
      @Override public byte nextByte(){
        Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        int valOffset;
        if((valOffset=this.valOffset) != 128){
          this.lastRet=valOffset;
          var ret=(byte)(valOffset=this.valOffset);
          switch(++valOffset >> 6){
          case -2:
            int tail0s;
            if((tail0s=Long.numberOfTrailingZeros(root.word0 >>> valOffset)) != 64){
              this.valOffset=valOffset+tail0s;
              break;
            }
            valOffset=-64;
          case -1:
            if((tail0s=Long.numberOfTrailingZeros(root.word1 >>> valOffset)) != 64){
              this.valOffset=valOffset+tail0s;
              break;
            }
            valOffset=0;
          case 0:
            if((tail0s=Long.numberOfTrailingZeros(root.word2 >>> valOffset)) != 64){
              this.valOffset=valOffset+tail0s;
              break;
            }
            valOffset=64;
          case 1:
            if((tail0s=Long.numberOfTrailingZeros(root.word3 >>> valOffset))!=64){
              this.valOffset=valOffset+tail0s;
              break;
            }
          default:
            this.valOffset=128;
          }
          return ret;
        }
        throw new NoSuchElementException();
      }
      private static int forEachRemainingHelper(long word,int valOffset,int lastRet,ByteConsumer action){
        for(long marker=1L << valOffset;;++valOffset){
          if((word & marker) != 0){
            action.accept((byte)valOffset);
            lastRet=valOffset;
          }
          if((marker<<=1) == 0){
            return lastRet;
          }
        }
      }
      private void forEachRemainingHelper(final int expectedValOffset,ByteConsumer action){
          int modCount=this.modCount;
          final var root=this.root;
          int lastRet;
          int valOffset;
          try{
              switch((lastRet=valOffset=expectedValOffset) >> 6){
              case -2:
                  lastRet=forEachRemainingHelper(root.word0,valOffset,lastRet,action);
                  valOffset=-64;
              case -1:
                  lastRet=forEachRemainingHelper(root.word1,valOffset,lastRet,action);
                  valOffset=0;
              case 0:
                  lastRet=forEachRemainingHelper(root.word2,valOffset,lastRet,action);
                  valOffset=64;
              default:
                  lastRet=forEachRemainingHelper(root.word3,valOffset,lastRet,action);
              }
          }finally{
              CheckedCollection.checkModCount(modCount,root.modCount,expectedValOffset,this.valOffset);
          }
          this.valOffset=128;
          this.lastRet=lastRet;
      }
      @Override public void forEachRemaining(ByteConsumer action){
        int valOffset;
        if((valOffset=this.valOffset) != 128){
          forEachRemainingHelper(valOffset,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Byte> action){
        int valOffset;
        if((valOffset=this.valOffset) != 128){
          forEachRemainingHelper(valOffset,action::accept);
        }
      }
      @Override public void remove(){
        int lastRet;
        if((lastRet=this.lastRet) != -129){
          int modCount;
          Checked root;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
          root.modCount=++modCount;
          this.modCount=modCount;
          --root.size;
          long mask=~(1L << lastRet);
          switch(lastRet >> 6){
          case -2:
            root.word0&=mask;
            break;
          case -1:
            root.word1&=mask;
            break;
          case 0:
            root.word2&=mask;
            break;
          default:
            root.word3&=mask;
            break;
          }
          this.lastRet=-129;
          return;
        }
        throw new IllegalStateException();
      }
    }
    @Override public Byte[] toArray(){
      int size;
      if((size=this.size) != 0){
        Byte[] dst;
        toArrayHelper(word0,word1,word2,word3,size,dst=new Byte[size]);
        return dst;
      }
      return OmniArray.OfByte.DEFAULT_BOXED_ARR;
    }
    @Override public byte[] toByteArray(){
      int size;
      if((size=this.size) != 0){
        byte[] dst;
        toArrayHelper(word0,word1,word2,word3,size,dst=new byte[size]);
        return dst;
      }
      return OmniArray.OfByte.DEFAULT_ARR;
    }
    @Override public short[] toShortArray(){
      int size;
      if((size=this.size) != 0){
        short[] dst;
        toArrayHelper(word0,word1,word2,word3,size,dst=new short[size]);
        return dst;
      }
      return OmniArray.OfShort.DEFAULT_ARR;
    }
    @Override public int[] toIntArray(){
      int size;
      if((size=this.size) != 0){
        int[] dst;
        toArrayHelper(word0,word1,word2,word3,size,dst=new int[size]);
        return dst;
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
    @Override public long[] toLongArray(){
      int size;
      if((size=this.size) != 0){
        long[] dst;
        toArrayHelper(word0,word1,word2,word3,size,dst=new long[size]);
        return dst;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override public float[] toFloatArray(){
      int size;
      if((size=this.size) != 0){
        float[] dst;
        toArrayHelper(word0,word1,word2,word3,size,dst=new float[size]);
        return dst;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override public double[] toDoubleArray(){
      int size;
      if((size=this.size) != 0){
        double[] dst;
        toArrayHelper(word0,word1,word2,word3,size,dst=new double[size]);
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
  }
}
