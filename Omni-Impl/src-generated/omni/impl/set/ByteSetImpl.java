package omni.impl.set;
import omni.api.OmniSet;
import java.io.Externalizable;
import java.io.Serializable;
import omni.impl.CheckedCollection;
import omni.function.BytePredicate;
import java.util.function.Predicate;
import omni.function.ByteConsumer;
import java.util.function.Consumer;
import omni.api.OmniIterator;
import java.util.function.IntFunction;
import java.util.Set;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.IOException;
import java.util.Collection;
import omni.api.OmniCollection;
import omni.util.ToStringUtil;
import omni.util.OmniArray;
public class ByteSetImpl extends AbstractByteSet implements Externalizable,Cloneable{
  //TODO equals methods
  transient long word0;
  transient long word1;
  transient long word2;
  transient long word3;
  private static final long serialVersionUID=1L;
  public ByteSetImpl(){
    super();
  }
  public ByteSetImpl(ByteSetImpl that){
    super();
    word0=that.word0;
    word1=that.word1;
    word2=that.word2;
    word3=that.word3;
  }
  public ByteSetImpl(Collection<? extends Byte> that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  public ByteSetImpl(OmniCollection.OfRef<? extends Byte> that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  public ByteSetImpl(OmniCollection.ByteOutput<?> that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  public ByteSetImpl(OmniCollection.OfByte that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  public ByteSetImpl(OmniCollection.OfBoolean that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  @Override public Object clone(){
    return new ByteSetImpl(this);
  }
  ByteSetImpl(long word0,long word1,long word2,long word3){
    super();
    this.word0=word0;
    this.word1=word1;
    this.word2=word2;
    this.word3=word3;
  }
  @Override public boolean contains(boolean val){
    return (this.word2&(val?0b10:0b01))!=0;
  }
  @Override public boolean contains(byte val){
    final long word;
    switch(val>>6){
      case -2:
        word=this.word0;
        break;
      case -1:
        word=this.word1;
        break;
      case 0:
        word=this.word2;
        break;
      default:
        word=this.word3;
    }
    return (word&(1L<<val))!=0;
  }
  @Override public boolean contains(char val){
    final long word;
    switch(val>>6){
      case 0:
        word=this.word2;
        break;
      case 1:
        word=this.word3;
        break;
      default:
        return false;
    }
    return (word&(1L<<val))!=0;
  }
  @Override public boolean contains(int val){
    final long word;
    switch(val>>6){
      case -2:
        word=this.word0;
        break;
      case -1:
        word=this.word1;
        break;
      case 0:
        word=this.word2;
        break;
      case 1:
        word=this.word3;
        break;
      default:
        return false;
    }
    return (word&(1L<<val))!=0;
  }
  @Override public boolean contains(long val){
    final byte v;
    return (v=(byte)val)==val && contains(v);
  }
  @Override public boolean contains(float val){
    final byte v;
    return (v=(byte)val)==val && contains(v);
  }
  @Override public boolean contains(double val){
    final byte v;
    return (v=(byte)val)==val && contains(v);
  }
  @Override public boolean contains(Object val){
    for(;;){
      final int v;
      if(val instanceof Byte){
        return this.contains((byte)val);
      }else if(val instanceof Integer || val instanceof Short){
        v=((Number)val).intValue();
      }else if(val instanceof Long){
        final long l;
        if((l=(long)val)!=(v=(int)l)){
          break;
        }
      }else if(val instanceof Float){
        final float f;
        if((f=(float)val)!=(v=(int)f)){
          break;
        }
      }else if(val instanceof Double){
        final double d;
        if((d=(double)val)!=(v=(int)d)){
          break;
        }
      }else if(val instanceof Character){
        return this.contains((char)val);
      }else if(val instanceof Boolean){
        return this.contains((boolean)val);
      }else{
        break;
      }
      return contains(v);
    }
    return false;
  }
  @Override public boolean removeVal(boolean val){
    long word;
    if((word=this.word2)!=(word&=(val?~0b10:~0b01))){
      this.word2=word;
      return true;
    }
    return false;
  }
  @Override public boolean removeVal(byte val){
    switch(val>>6){
      case -2:
        long word;
        if((word=this.word0)!=(word&=(1L<<val))){
          this.word0=word;
          return true;
        }
        break;
      case -1:
        if((word=this.word1)!=(word&=(1L<<val))){
          this.word1=word;
          return true;
        }
        break;
      case 0:
        if((word=this.word2)!=(word&=(1L<<val))){
          this.word2=word;
          return true;
        }
        break;
      default:
        if((word=this.word3)!=(word&=(1L<<val))){
          this.word3=word;
          return true;
        }
    }
    return false;
  }
  @Override public boolean removeVal(char val){
    switch(val>>6){
      case 0:
        long word;
        if((word=this.word2)!=(word&=(1L<<val))){
          this.word2=word;
          return true;
        }
        break;
      case 1:
        if((word=this.word3)!=(word&=(1L<<val))){
          this.word3=word;
          return true;
        }
      default:
    }
    return false;
  }
  @Override public boolean removeVal(int val){
    switch(val>>6){
      case -2:
        long word;
        if((word=this.word0)!=(word&=(1L<<val))){
          this.word0=word;
          return true;
        }
        break;
      case -1:
        if((word=this.word1)!=(word&=(1L<<val))){
          this.word1=word;
          return true;
        }
        break;
      case 0:
        if((word=this.word2)!=(word&=(1L<<val))){
          this.word2=word;
          return true;
        }
        break;
      case 1:
        if((word=this.word3)!=(word&=(1L<<val))){
          this.word3=word;
          return true;
        }
      default:
    }
    return false;
  }
  @Override public boolean removeVal(long val){
    final int v;
    return (v=(int)val)==val && removeVal(v);
  }
  @Override public boolean removeVal(float val){
    final int v;
    return (v=(int)val)==val && removeVal(v);
  }
  @Override public boolean removeVal(double val){
    final int v;
    return (v=(int)val)==val && removeVal(v);
  }
  @Override public boolean remove(Object val){
    final int v;
    if(val instanceof Byte){
      return this.removeVal((byte)val);
    }else if(val instanceof Integer || val instanceof Short){
      return this.removeVal(((Number)val).intValue());
    }else if(val instanceof Long){
      final long l;
      return (l=(long)val)==(v=(int)l) && this.removeVal(v);
    }else if(val instanceof Float){
      final float f;
      return (f=(float)val)==(v=(int)f) && this.removeVal(v);
    }else if(val instanceof Double){
      final double d;
      return (d=(double)val)==(v=(int)d) && this.removeVal(v);
    }else if(val instanceof Character){
      return this.removeVal((char)val);
    }else if(val instanceof Boolean){
      return this.removeVal((boolean)val);
    }
    return false;
  }
  @Override public boolean add(boolean val){
    long word;
    if((word=this.word2)!=(word|=(val?0b10:0b01))){
      this.word2=word;
      return true;
    }
    return false;
  }
  @Override public boolean add(byte val){
    switch(val>>6){
      case -2:
        long word;
        if((word=this.word0)!=(word|=(1L<<val))){
          this.word0=word;
          return true;
        }
        break;
      case -1:
        if((word=this.word1)!=(word|=(1L<<val))){
          this.word1=word;
          return true;
        }
        break;
      case 0:
        if((word=this.word2)!=(word|=(1L<<val))){
          this.word2=word;
          return true;
        }
        break;
      default:
        if((word=this.word3)!=(word|=(1L<<val))){
          this.word3=word;
          return true;
        }
    }
    return false;
  }
  @Override public int hashCode(){
    return ByteSetImpl.hashCodeForWord(word0,Byte.MIN_VALUE,ByteSetImpl.hashCodeForWord(word1,-64,ByteSetImpl.hashCodeForWord(word2,0,ByteSetImpl.hashCodeForWord(word2,64,0))));  
  }
  @Override public void clear(){
    this.word0=0;
    this.word1=0;
    this.word2=0;
    this.word3=0; 
  }
  @Override public boolean isEmpty(){
    return word0==0 && word1==0 && word2==0 && word3==0;
  }
  @Override public int size(){
    return SetCommonImpl.size(word0,word1,word2,word3);
  }
  @Override public boolean removeIf(BytePredicate filter){
    long word;
    return (word=this.word0) != (this.word0=processWordRemoveIf(word,Byte.MIN_VALUE,filter))
      | (word=this.word1) != (this.word1=processWordRemoveIf(word,-64,filter))
      | (word=this.word2) != (this.word2=processWordRemoveIf(word,0,filter))
      | (word=this.word3) != (this.word3=processWordRemoveIf(word,64,filter));      
  }
  @Override public boolean removeIf(Predicate<? super Byte> filter){
    return removeIf((BytePredicate)filter::test);
  }
  @Override public String toString(){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){  
      final byte[] buffer;
      (buffer=new byte[size*6])[0]='[';
      if((size=wordToStringAscending(word0,Byte.MIN_VALUE,-64,buffer,size<<11))>=1<<11){
        if((size=wordToStringAscending(word1,-64,0,buffer,size))>=1<<11){
          if((size=wordToStringAscending(word2,0,64,buffer,size))>=1<<11){
            size=finalWordToStringAscending(word3,64,buffer,size);
          }
        }
      }
      buffer[++size]=']';
      return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
    }
    return "[]";
  }
  @Override public void forEach(ByteConsumer action){
    forEachWordAscending(this.word0,Byte.MIN_VALUE,action);
    forEachWordAscending(this.word1,-64,action);
    forEachWordAscending(this.word2,0,action);
    forEachWordAscending(this.word3,64,action);
  }
  @Override public void forEach(Consumer<? super Byte> action){
    this.forEach((ByteConsumer)action::accept);
  }
  @Override public OmniIterator.OfByte iterator(){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  @Override public Byte[] toArray(){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){  
      final Byte[] dst;
      if((size=wordToArrayAscending(word0,Byte.MIN_VALUE,-64,dst=new Byte[size],size<<11))>=1<<11){
        if((size=wordToArrayAscending(word1,-64,0,dst,size))>=1<<11){
          if((size=wordToArrayAscending(word2,0,64,dst,size))>=1<<11){
            size=finalWordToArrayAscending(word3,64,dst,size);
          }
        }
      }
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_BOXED_ARR;
  }
  @Override public byte[] toByteArray(){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){  
      final byte[] dst;
      if((size=wordToArrayAscending(word0,Byte.MIN_VALUE,-64,dst=new byte[size],size<<11))>=1<<11){
        if((size=wordToArrayAscending(word1,-64,0,dst,size))>=1<<11){
          if((size=wordToArrayAscending(word2,0,64,dst,size))>=1<<11){
            size=finalWordToArrayAscending(word3,64,dst,size);
          }
        }
      }
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_ARR;
  }
  @Override public short[] toShortArray(){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){  
      final short[] dst;
      if((size=wordToArrayAscending(word0,Byte.MIN_VALUE,-64,dst=new short[size],size<<11))>=1<<11){
        if((size=wordToArrayAscending(word1,-64,0,dst,size))>=1<<11){
          if((size=wordToArrayAscending(word2,0,64,dst,size))>=1<<11){
            size=finalWordToArrayAscending(word3,64,dst,size);
          }
        }
      }
      return dst;
    }
    return OmniArray.OfShort.DEFAULT_ARR;
  }
  @Override public int[] toIntArray(){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){  
      final int[] dst;
      if((size=wordToArrayAscending(word0,Byte.MIN_VALUE,-64,dst=new int[size],size<<11))>=1<<11){
        if((size=wordToArrayAscending(word1,-64,0,dst,size))>=1<<11){
          if((size=wordToArrayAscending(word2,0,64,dst,size))>=1<<11){
            size=finalWordToArrayAscending(word3,64,dst,size);
          }
        }
      }
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  @Override public long[] toLongArray(){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){  
      final long[] dst;
      if((size=wordToArrayAscending(word0,Byte.MIN_VALUE,-64,dst=new long[size],size<<11))>=1<<11){
        if((size=wordToArrayAscending(word1,-64,0,dst,size))>=1<<11){
          if((size=wordToArrayAscending(word2,0,64,dst,size))>=1<<11){
            size=finalWordToArrayAscending(word3,64,dst,size);
          }
        }
      }
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  @Override public float[] toFloatArray(){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){  
      final float[] dst;
      if((size=wordToArrayAscending(word0,Byte.MIN_VALUE,-64,dst=new float[size],size<<11))>=1<<11){
        if((size=wordToArrayAscending(word1,-64,0,dst,size))>=1<<11){
          if((size=wordToArrayAscending(word2,0,64,dst,size))>=1<<11){
            size=finalWordToArrayAscending(word3,64,dst,size);
          }
        }
      }
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override public double[] toDoubleArray(){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){  
      final double[] dst;
      if((size=wordToArrayAscending(word0,Byte.MIN_VALUE,-64,dst=new double[size],size<<11))>=1<<11){
        if((size=wordToArrayAscending(word1,-64,0,dst,size))>=1<<11){
          if((size=wordToArrayAscending(word2,0,64,dst,size))>=1<<11){
            size=finalWordToArrayAscending(word3,64,dst,size);
          }
        }
      }
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  @Override public <T> T[] toArray(T[] dst){
    int size;
    final long word0,word1,word2,word3;
    if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){  
      if((size=wordToArrayAscending(word0,Byte.MIN_VALUE,-64,dst=OmniArray.uncheckedArrResize(size,dst),size<<11))>=1<<11){
        if((size=wordToArrayAscending(word1,-64,0,dst,size))>=1<<11){
          if((size=wordToArrayAscending(word2,0,64,dst,size))>=1<<11){
            size=finalWordToArrayAscending(word3,64,dst,size);
          }
        }
      }
    }else if(dst.length!=0){
      dst[0]=null;
    }
    return dst;
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    int size;
    final long word0,word1,word2,word3;
    final T[] dst=arrConstructor.apply(size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3));
    if(size!=0){
      if((size=wordToArrayAscending(word0,Byte.MIN_VALUE,-64,dst,size<<11))>=1<<11){
        if((size=wordToArrayAscending(word1,-64,0,dst,size))>=1<<11){
          if((size=wordToArrayAscending(word2,0,64,dst,size))>=1<<11){
            size=finalWordToArrayAscending(word3,64,dst,size);
          }
        }
      }
    }
    return dst;
  }
  @Override public void writeExternal(ObjectOutput output) throws IOException{
    output.writeLong(word0);
    output.writeLong(word1);
    output.writeLong(word2);
    output.writeLong(word3);
  }
  @Override public void readExternal(ObjectInput input) throws IOException{
    word0=input.readLong();
    word1=input.readLong();
    word2=input.readLong();
    word3=input.readLong();
  }
  private static int hashCodeForWord(long word,int offset,int sum){
    for(;;){
      int tail0s;
      if((tail0s=Long.numberOfTrailingZeros(word))==64){
        return sum;
      }
      offset+=tail0s;
      sum+=offset;
      word>>>=(1+tail0s);
      ++offset;
    }
  }
  private int removeIfHelper(BytePredicate filter,int exclusiveTo){
    long word;
    int numRemoved;
    goToWord0:for(;;){
      goToWord1:for(;;){
        goToWord2:switch(exclusiveTo>>6){
        case -2:
          return Long.bitCount(((word=this.word0)^(this.word0=processSubSetWordRemoveIf(word,Byte.MIN_VALUE,exclusiveTo,filter)))&(-1L>>>-exclusiveTo));
        case -1:
          numRemoved=Long.bitCount(((word=this.word1)^(this.word1=processSubSetWordRemoveIf(word,-64,exclusiveTo,filter)))&(-1L>>>-exclusiveTo));
          break goToWord0;
        case 0:
          numRemoved=Long.bitCount(((word=this.word2)^(this.word2=processSubSetWordRemoveIf(word,0,exclusiveTo,filter)))&(-1L>>>-exclusiveTo));
          break goToWord1;
        default:
          numRemoved=Long.bitCount(((word=this.word3)^(this.word3=processSubSetWordRemoveIf(word,64,exclusiveTo,filter)))&(-1L>>>-exclusiveTo));
          break goToWord2;
        }
        numRemoved+=Long.bitCount((word=this.word2)^(this.word2=processWordRemoveIf(word,0,filter)));
        break;
      }
      numRemoved+=Long.bitCount((word=this.word1)^(this.word1=processWordRemoveIf(word,-64,filter)));
      break;
    }
    return numRemoved+Long.bitCount((word=this.word0)^(this.word0=processWordRemoveIf(word,Byte.MIN_VALUE,filter)));
  }
  private int removeIfHelper(int inclusiveFrom,BytePredicate filter){
    int numRemoved;
    long word;
    goToWord3:for(;;){
      goToWord2:for(;;){
        goToWord1:switch(inclusiveFrom>>6){
        case 1:
          return Long.bitCount(((word=this.word3)^(this.word3=processWordRemoveIf(word,inclusiveFrom,filter)))&(-1L<<inclusiveFrom));
        case 0:
          numRemoved=Long.bitCount(((word=this.word2)^(this.word2=processWordRemoveIf(word,inclusiveFrom,filter)))&(-1L<<inclusiveFrom));
          break goToWord3;
        case -1:
          numRemoved=Long.bitCount(((word=this.word1)^(this.word1=processWordRemoveIf(word,inclusiveFrom,filter)))&(-1L<<inclusiveFrom));
          break goToWord2;
        default:
          numRemoved=Long.bitCount(((word=this.word0)^(this.word0=processWordRemoveIf(word,inclusiveFrom,filter)))&(-1L<<inclusiveFrom));
          break goToWord1;
        }
        numRemoved+=Long.bitCount((word=this.word1)^(this.word1=processWordRemoveIf(word,-64,filter)));
        break;
      }
      numRemoved+=Long.bitCount((word=this.word2)^(this.word2=processWordRemoveIf(word,0,filter)));
      break;
    }
    return numRemoved+Long.bitCount((word=this.word3)^(this.word3=processWordRemoveIf(word,64,filter)));
  }
  private void forEachAscendingHelper(ByteConsumer action,int numLeft){
    if((numLeft=forEachWordAscending(word0,Byte.MIN_VALUE,action,numLeft))!=0){
      if((numLeft=forEachWordAscending(word1,-64,action,numLeft))!=0){
        if((numLeft=forEachWordAscending(word2,0,action,numLeft))!=0){
          forEachWordAscending(word3,64,action,numLeft);
        }
      }
    }
  }
  private void forEachDescendingHelper(ByteConsumer action,int numLeft){
    if((numLeft=forEachWordDescending(word3,128,action,numLeft))!=0){
      if((numLeft=forEachWordDescending(word2,64,action,numLeft))!=0){
        if((numLeft=forEachWordDescending(word1,0,action,numLeft))!=0){
          forEachWordDescending(word0,-64,action,numLeft);
        }
      }
    }
  }
  private void forEachDescendingHelper(ByteConsumer action,int numLeft,int exclusiveTo){
    switch(exclusiveTo>>6){
      case 1:
        if((numLeft=forEachWordAscending(word3,exclusiveTo,action,numLeft))==0){
          break;
        }
        exclusiveTo=64;
      case 0:
        if((numLeft=forEachWordAscending(word2,exclusiveTo,action,numLeft))==0){
          break;
        }
        exclusiveTo=0;
      case -1:
        if((numLeft=forEachWordAscending(word1,exclusiveTo,action,numLeft))==0){
          break;
        }
        exclusiveTo=-64;
      default:
        forEachWordAscending(word0,exclusiveTo,action,numLeft);
    }
  }
  private void forEachAscendingHelper(int inclusiveFrom,ByteConsumer action,int numLeft){
    switch(inclusiveFrom>>6){
      case -2:
        if((numLeft=forEachWordAscending(word0,inclusiveFrom,action,numLeft))==0){
          break;
        }
        inclusiveFrom=-64;
      case -1:
        if((numLeft=forEachWordAscending(word1,inclusiveFrom,action,numLeft))==0){
          break;
        }
        inclusiveFrom=0;
      case 0:
        if((numLeft=forEachWordAscending(word2,inclusiveFrom,action,numLeft))==0){
          break;
        }
        inclusiveFrom=64;
      default:
        forEachWordAscending(word3,inclusiveFrom,action,numLeft);
    }
  }
  private static int forEachWordDescending(long word,int valBound,ByteConsumer action,int numLeft){
    for(long marker=1L<<(--valBound);;--valBound){
      if((word&marker)!=0){
        action.accept((byte)valBound);
        if(--numLeft==0){
          break;
        }
      }
      if((marker>>>=1)==0){
        break;
      }
    }
    return numLeft;
  }
  private static void forEachWordAscending(long word,int valOffset,ByteConsumer action){
    for(long marker=1L;;++valOffset){
      if((word&marker)!=0){
        action.accept((byte)valOffset);
      }
      if((marker<<=1)==0){
        break;
      }
    }
  }
  private static void forEachWordDescending(long word,int inclusiveValBound,ByteConsumer action){
    for(long marker=Long.MIN_VALUE;;--inclusiveValBound){
      if((word&marker)!=0){
        action.accept((byte)inclusiveValBound);
      }
      if((marker>>>=1)==0){
        break;
      }
    }
  }
  private static int forEachWordAscending(long word,int valOffset,ByteConsumer action,int numLeft){
    for(long marker=1L<<valOffset;;++valOffset){
      if((word&marker)!=0){
        action.accept((byte)valOffset);
        if(--numLeft==0){
          break;
        }
      }
      if((marker<<=1)==0){
        break;
      }
    }
    return numLeft;
  }
  private static int wordToArrayAscending(long word,int valOffset,int valBound,Object[] dst,int magicWord){
    int numLeft=magicWord>>>9;
    for(magicWord&=(-1>>>-9);;){
      if(((1L<<valOffset)&word)!=0){
        dst[magicWord++]=(Byte)(byte)(valOffset);
        if(--numLeft==0){
          return magicWord;
        }
      }
      if(++valOffset==valBound){
        return magicWord|(numLeft<<9);
      }
    }
  }
  private static int finalWordToArrayAscending(long word,int valOffset,Object[] dst,int magicWord){
    for(int dstOffset=magicWord&(-1>>>-9);;){
      if(((1L<<valOffset)&word)!=0){
        dst[dstOffset++]=(Byte)(byte)(valOffset);
        if((magicWord-=(1<<9))<(1<<9)){
          return magicWord;
        }
      }
      ++valOffset;
    }
  }
  private static int wordToArrayDescending(long word,int valOffset,int valBound,Object[] dst,int magicWord){
    int numLeft=magicWord>>>9;
    for(magicWord&=(-1>>>-9);;){
      if(((1L<<(--valBound))&word)!=0){
        dst[magicWord++]=(Byte)(byte)(valBound);
        if(--numLeft==0){
          return magicWord;
        }
      }
      if(valOffset==valBound){
        return magicWord|(numLeft<<9);
      }
    }
  }
  private static int finalWordToArrayDescending(long word,int valBound,Object[] dst,int magicWord){
    for(int dstOffset=magicWord&(-1>>>-9);;){
      if(((1L<<(--valBound))&word)!=0){
        dst[dstOffset++]=(Byte)(byte)(valBound);
        if((magicWord-=(1<<9))<(1<<9)){
          return magicWord;
        }
      }
    }
  }
  private static int wordToArrayAscending(long word,int valOffset,int valBound,byte[] dst,int magicWord){
    int numLeft=magicWord>>>9;
    for(magicWord&=(-1>>>-9);;){
      if(((1L<<valOffset)&word)!=0){
        dst[magicWord++]=(byte)(valOffset);
        if(--numLeft==0){
          return magicWord;
        }
      }
      if(++valOffset==valBound){
        return magicWord|(numLeft<<9);
      }
    }
  }
  private static int finalWordToArrayAscending(long word,int valOffset,byte[] dst,int magicWord){
    for(int dstOffset=magicWord&(-1>>>-9);;){
      if(((1L<<valOffset)&word)!=0){
        dst[dstOffset++]=(byte)(valOffset);
        if((magicWord-=(1<<9))<(1<<9)){
          return magicWord;
        }
      }
      ++valOffset;
    }
  }
  private static int wordToArrayDescending(long word,int valOffset,int valBound,byte[] dst,int magicWord){
    int numLeft=magicWord>>>9;
    for(magicWord&=(-1>>>-9);;){
      if(((1L<<(--valBound))&word)!=0){
        dst[magicWord++]=(byte)(valBound);
        if(--numLeft==0){
          return magicWord;
        }
      }
      if(valOffset==valBound){
        return magicWord|(numLeft<<9);
      }
    }
  }
  private static int finalWordToArrayDescending(long word,int valBound,byte[] dst,int magicWord){
    for(int dstOffset=magicWord&(-1>>>-9);;){
      if(((1L<<(--valBound))&word)!=0){
        dst[dstOffset++]=(byte)(valBound);
        if((magicWord-=(1<<9))<(1<<9)){
          return magicWord;
        }
      }
    }
  }
  private static int wordToArrayAscending(long word,int valOffset,int valBound,Byte[] dst,int magicWord){
    int numLeft=magicWord>>>9;
    for(magicWord&=(-1>>>-9);;){
      if(((1L<<valOffset)&word)!=0){
        dst[magicWord++]=(Byte)(byte)(valOffset);
        if(--numLeft==0){
          return magicWord;
        }
      }
      if(++valOffset==valBound){
        return magicWord|(numLeft<<9);
      }
    }
  }
  private static int finalWordToArrayAscending(long word,int valOffset,Byte[] dst,int magicWord){
    for(int dstOffset=magicWord&(-1>>>-9);;){
      if(((1L<<valOffset)&word)!=0){
        dst[dstOffset++]=(Byte)(byte)(valOffset);
        if((magicWord-=(1<<9))<(1<<9)){
          return magicWord;
        }
      }
      ++valOffset;
    }
  }
  private static int wordToArrayDescending(long word,int valOffset,int valBound,Byte[] dst,int magicWord){
    int numLeft=magicWord>>>9;
    for(magicWord&=(-1>>>-9);;){
      if(((1L<<(--valBound))&word)!=0){
        dst[magicWord++]=(Byte)(byte)(valBound);
        if(--numLeft==0){
          return magicWord;
        }
      }
      if(valOffset==valBound){
        return magicWord|(numLeft<<9);
      }
    }
  }
  private static int finalWordToArrayDescending(long word,int valBound,Byte[] dst,int magicWord){
    for(int dstOffset=magicWord&(-1>>>-9);;){
      if(((1L<<(--valBound))&word)!=0){
        dst[dstOffset++]=(Byte)(byte)(valBound);
        if((magicWord-=(1<<9))<(1<<9)){
          return magicWord;
        }
      }
    }
  }
  private static int wordToArrayAscending(long word,int valOffset,int valBound,short[] dst,int magicWord){
    int numLeft=magicWord>>>9;
    for(magicWord&=(-1>>>-9);;){
      if(((1L<<valOffset)&word)!=0){
        dst[magicWord++]=(short)(valOffset);
        if(--numLeft==0){
          return magicWord;
        }
      }
      if(++valOffset==valBound){
        return magicWord|(numLeft<<9);
      }
    }
  }
  private static int finalWordToArrayAscending(long word,int valOffset,short[] dst,int magicWord){
    for(int dstOffset=magicWord&(-1>>>-9);;){
      if(((1L<<valOffset)&word)!=0){
        dst[dstOffset++]=(short)(valOffset);
        if((magicWord-=(1<<9))<(1<<9)){
          return magicWord;
        }
      }
      ++valOffset;
    }
  }
  private static int wordToArrayDescending(long word,int valOffset,int valBound,short[] dst,int magicWord){
    int numLeft=magicWord>>>9;
    for(magicWord&=(-1>>>-9);;){
      if(((1L<<(--valBound))&word)!=0){
        dst[magicWord++]=(short)(valBound);
        if(--numLeft==0){
          return magicWord;
        }
      }
      if(valOffset==valBound){
        return magicWord|(numLeft<<9);
      }
    }
  }
  private static int finalWordToArrayDescending(long word,int valBound,short[] dst,int magicWord){
    for(int dstOffset=magicWord&(-1>>>-9);;){
      if(((1L<<(--valBound))&word)!=0){
        dst[dstOffset++]=(short)(valBound);
        if((magicWord-=(1<<9))<(1<<9)){
          return magicWord;
        }
      }
    }
  }
  private static int wordToArrayAscending(long word,int valOffset,int valBound,int[] dst,int magicWord){
    int numLeft=magicWord>>>9;
    for(magicWord&=(-1>>>-9);;){
      if(((1L<<valOffset)&word)!=0){
        dst[magicWord++]=(valOffset);
        if(--numLeft==0){
          return magicWord;
        }
      }
      if(++valOffset==valBound){
        return magicWord|(numLeft<<9);
      }
    }
  }
  private static int finalWordToArrayAscending(long word,int valOffset,int[] dst,int magicWord){
    for(int dstOffset=magicWord&(-1>>>-9);;){
      if(((1L<<valOffset)&word)!=0){
        dst[dstOffset++]=(valOffset);
        if((magicWord-=(1<<9))<(1<<9)){
          return magicWord;
        }
      }
      ++valOffset;
    }
  }
  private static int wordToArrayDescending(long word,int valOffset,int valBound,int[] dst,int magicWord){
    int numLeft=magicWord>>>9;
    for(magicWord&=(-1>>>-9);;){
      if(((1L<<(--valBound))&word)!=0){
        dst[magicWord++]=(valBound);
        if(--numLeft==0){
          return magicWord;
        }
      }
      if(valOffset==valBound){
        return magicWord|(numLeft<<9);
      }
    }
  }
  private static int finalWordToArrayDescending(long word,int valBound,int[] dst,int magicWord){
    for(int dstOffset=magicWord&(-1>>>-9);;){
      if(((1L<<(--valBound))&word)!=0){
        dst[dstOffset++]=(valBound);
        if((magicWord-=(1<<9))<(1<<9)){
          return magicWord;
        }
      }
    }
  }
  private static int wordToArrayAscending(long word,long valOffset,long valBound,long[] dst,int magicWord){
    int numLeft=magicWord>>>9;
    for(magicWord&=(-1>>>-9);;){
      if(((1L<<valOffset)&word)!=0){
        dst[magicWord++]=(valOffset);
        if(--numLeft==0){
          return magicWord;
        }
      }
      if(++valOffset==valBound){
        return magicWord|(numLeft<<9);
      }
    }
  }
  private static int finalWordToArrayAscending(long word,long valOffset,long[] dst,int magicWord){
    for(int dstOffset=magicWord&(-1>>>-9);;){
      if(((1L<<valOffset)&word)!=0){
        dst[dstOffset++]=(valOffset);
        if((magicWord-=(1<<9))<(1<<9)){
          return magicWord;
        }
      }
      ++valOffset;
    }
  }
  private static int wordToArrayDescending(long word,long valOffset,long valBound,long[] dst,int magicWord){
    int numLeft=magicWord>>>9;
    for(magicWord&=(-1>>>-9);;){
      if(((1L<<(--valBound))&word)!=0){
        dst[magicWord++]=(valBound);
        if(--numLeft==0){
          return magicWord;
        }
      }
      if(valOffset==valBound){
        return magicWord|(numLeft<<9);
      }
    }
  }
  private static int finalWordToArrayDescending(long word,long valBound,long[] dst,int magicWord){
    for(int dstOffset=magicWord&(-1>>>-9);;){
      if(((1L<<(--valBound))&word)!=0){
        dst[dstOffset++]=(valBound);
        if((magicWord-=(1<<9))<(1<<9)){
          return magicWord;
        }
      }
    }
  }
  private static int wordToArrayAscending(long word,int valOffset,int valBound,float[] dst,int magicWord){
    int numLeft=magicWord>>>9;
    for(magicWord&=(-1>>>-9);;){
      if(((1L<<valOffset)&word)!=0){
        dst[magicWord++]=(float)(valOffset);
        if(--numLeft==0){
          return magicWord;
        }
      }
      if(++valOffset==valBound){
        return magicWord|(numLeft<<9);
      }
    }
  }
  private static int finalWordToArrayAscending(long word,int valOffset,float[] dst,int magicWord){
    for(int dstOffset=magicWord&(-1>>>-9);;){
      if(((1L<<valOffset)&word)!=0){
        dst[dstOffset++]=(float)(valOffset);
        if((magicWord-=(1<<9))<(1<<9)){
          return magicWord;
        }
      }
      ++valOffset;
    }
  }
  private static int wordToArrayDescending(long word,int valOffset,int valBound,float[] dst,int magicWord){
    int numLeft=magicWord>>>9;
    for(magicWord&=(-1>>>-9);;){
      if(((1L<<(--valBound))&word)!=0){
        dst[magicWord++]=(float)(valBound);
        if(--numLeft==0){
          return magicWord;
        }
      }
      if(valOffset==valBound){
        return magicWord|(numLeft<<9);
      }
    }
  }
  private static int finalWordToArrayDescending(long word,int valBound,float[] dst,int magicWord){
    for(int dstOffset=magicWord&(-1>>>-9);;){
      if(((1L<<(--valBound))&word)!=0){
        dst[dstOffset++]=(float)(valBound);
        if((magicWord-=(1<<9))<(1<<9)){
          return magicWord;
        }
      }
    }
  }
  private static int wordToArrayAscending(long word,int valOffset,int valBound,double[] dst,int magicWord){
    int numLeft=magicWord>>>9;
    for(magicWord&=(-1>>>-9);;){
      if(((1L<<valOffset)&word)!=0){
        dst[magicWord++]=(double)(valOffset);
        if(--numLeft==0){
          return magicWord;
        }
      }
      if(++valOffset==valBound){
        return magicWord|(numLeft<<9);
      }
    }
  }
  private static int finalWordToArrayAscending(long word,int valOffset,double[] dst,int magicWord){
    for(int dstOffset=magicWord&(-1>>>-9);;){
      if(((1L<<valOffset)&word)!=0){
        dst[dstOffset++]=(double)(valOffset);
        if((magicWord-=(1<<9))<(1<<9)){
          return magicWord;
        }
      }
      ++valOffset;
    }
  }
  private static int wordToArrayDescending(long word,int valOffset,int valBound,double[] dst,int magicWord){
    int numLeft=magicWord>>>9;
    for(magicWord&=(-1>>>-9);;){
      if(((1L<<(--valBound))&word)!=0){
        dst[magicWord++]=(double)(valBound);
        if(--numLeft==0){
          return magicWord;
        }
      }
      if(valOffset==valBound){
        return magicWord|(numLeft<<9);
      }
    }
  }
  private static int finalWordToArrayDescending(long word,int valBound,double[] dst,int magicWord){
    for(int dstOffset=magicWord&(-1>>>-9);;){
      if(((1L<<(--valBound))&word)!=0){
        dst[dstOffset++]=(double)(valBound);
        if((magicWord-=(1<<9))<(1<<9)){
          return magicWord;
        }
      }
    }
  }
  private static int wordToStringAscending(long word,int valOffset,int valBound,byte[] buffer,int magicWord){
    int size=magicWord>>>11;
    for(magicWord&=(-1>>>-11);;){
      if(((1L<<valOffset)&word)!=0){
        magicWord=(ToStringUtil.getStringShort(valOffset,buffer,++magicWord));
        if(--size==0){
          return magicWord;
        }
      }
      if(++valOffset==valBound){
        return magicWord|(size<<11);
      }
    }
  }
  private static int finalWordToStringAscending(long word,int valOffset,byte[] buffer,int magicWord){
    for(int bufferOffset=magicWord&(-1>>>-11);;){
      if(((1L<<valOffset)&word)!=0){
        bufferOffset=(ToStringUtil.getStringShort(valOffset,buffer,++bufferOffset));
        if((magicWord-=(1<<11))<(1<<11)){
          return magicWord;
        }
      }
      ++valOffset;
    }
  }
  private static int wordToStringDescending(long word,int valOffset,int valBound,byte[] buffer,int magicWord){
    int size=magicWord>>>11;
    for(magicWord&=(-1>>>-11);;){
      if(((1L<<(--valBound))&word)!=0){
        magicWord=(ToStringUtil.getStringShort(valBound,buffer,++magicWord));
        if(--size==0){
          return magicWord;
        }
      }
      if(valOffset==valBound){
        return magicWord|(size<<11);
      }
    }
  }
  private static int finalWordToStringDescending(long word,int valBound,byte[] buffer,int magicWord){
    for(int bufferOffset=magicWord&(-1>>>-11);;){
      if(((1L<<(--valBound))&word)!=0){
        bufferOffset=(ToStringUtil.getStringShort(valBound,buffer,++bufferOffset));
        if((magicWord-=(1<<11))<(1<<11)){
          return magicWord;
        }
      }
    }
  }
  private int removeIfHelper(int inclusiveFrom,BytePredicate filter,int exclusiveTo){
    switch(inclusiveFrom>>6){
      case -2:
        long word;
        int numRemoved;
        goToWord0:for(;;){
          goToWord1:for(;;){
            goToWord2:switch(exclusiveTo>>6){
              case -2:
                return Long.bitCount(((word=this.word0)^(this.word0=processSubSetWordRemoveIf(word,inclusiveFrom,exclusiveTo,filter))&(-1L<<inclusiveFrom)&(-1L>>>-exclusiveTo)));
              case -1:
                numRemoved=Long.bitCount(((word=this.word1)^(this.word1=processSubSetWordRemoveIf(word,-64,exclusiveTo,filter))&(-1L>>>-exclusiveTo)));
                break goToWord0;
              case 0:
                numRemoved=Long.bitCount(((word=this.word2)^(this.word2=processSubSetWordRemoveIf(word,0,exclusiveTo,filter))&(-1L>>>-exclusiveTo)));
                break goToWord1;
              default:
                numRemoved=Long.bitCount(((word=this.word3)^(this.word3=processSubSetWordRemoveIf(word,64,exclusiveTo,filter))&(-1L>>>-exclusiveTo)));
                break goToWord2;
            }
            numRemoved+=Long.bitCount((word=this.word2)^(this.word2=processWordRemoveIf(word,0,filter)));
            break;
          }
          numRemoved+=Long.bitCount((word=this.word1)^(this.word1=processWordRemoveIf(word,-64,filter)));
          break;
        }
        return numRemoved+Long.bitCount(((word=this.word0)^(this.word0=processWordRemoveIf(word,inclusiveFrom,filter))&(1L<<inclusiveFrom)));
      case -1:
        goToWord1:for(;;){
          goToWord2:switch(exclusiveTo>>6){
            case -1:
              return Long.bitCount(((word=this.word1)^(this.word1=processSubSetWordRemoveIf(word,inclusiveFrom,exclusiveTo,filter))&(-1L<<inclusiveFrom)&(-1L>>>-exclusiveTo)));
            case 0:
              numRemoved=Long.bitCount(((word=this.word2)^(this.word2=processSubSetWordRemoveIf(word,0,exclusiveTo,filter))&(-1L>>>-exclusiveTo)));
              break goToWord1;
            default:
              numRemoved=Long.bitCount(((word=this.word3)^(this.word3=processSubSetWordRemoveIf(word,64,exclusiveTo,filter))&(-1L>>>-exclusiveTo)));
              break goToWord2;
          }
          numRemoved+=Long.bitCount((word=this.word2)^(this.word2=processWordRemoveIf(word,0,filter)));
          break;
        }
        return numRemoved+Long.bitCount(((word=this.word1)^(this.word1=processWordRemoveIf(word,inclusiveFrom,filter))&(1L<<inclusiveFrom)));
      case 0:
        if((exclusiveTo>>6)==0){
          return Long.bitCount(((word=this.word2)^(this.word2=processSubSetWordRemoveIf(word,inclusiveFrom,exclusiveTo,filter))&(-1L<<inclusiveFrom)&(-1L>>>-exclusiveTo)));
        }else{
          return Long.bitCount(((word=this.word2)^(this.word2=processWordRemoveIf(word,inclusiveFrom,filter))&(1L<<inclusiveFrom)))
            + Long.bitCount(((word=this.word3)^(this.word3=processSubSetWordRemoveIf(word,64,exclusiveTo,filter))&(-1L>>>-exclusiveTo)));
        }
      default:
        return Long.bitCount(((word=this.word3)^(this.word3=processSubSetWordRemoveIf(word,inclusiveFrom,exclusiveTo,filter))&(-1L<<inclusiveFrom)&(-1L>>>-exclusiveTo)));
    }
  }
  private static long processWordRemoveIf(long word,int valOffset,BytePredicate filter){
    long marker=1L<<valOffset;
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
  private static long processSubSetWordRemoveIf(long word,int valOffset,int valBound,BytePredicate filter){
    long marker=1L<<valOffset;
    for(;;){
      if((word & marker) != 0){
        if(filter.test((byte)valOffset)){
          word&=~marker;
        }
      }
      if(++valOffset==valBound){
        return word;
      }
      marker<<=1;
    }
  }
  public static class Checked extends ByteSetImpl{
    transient int modCountAndSize;
    private static final long serialVersionUID=1L;
    public Checked(){
      super();
    }
    public Checked(ByteSetImpl that){
      super(that);
      modCountAndSize=SetCommonImpl.size(word0,word1,word2,word3);
    }
    public Checked(Collection<? extends Byte> that){
      super(that);
    }
    public Checked(OmniCollection.OfRef<? extends Byte> that){
      super(that);
    }
    public Checked(OmniCollection.ByteOutput<?> that){
      super(that);
    }
    public Checked(OmniCollection.OfByte that){
      super(that);
    }
    public Checked(OmniCollection.OfBoolean that){
      super(that);
    }
    @Override public Object clone(){
      return new Checked(this);
    }
    public Checked(ByteSetImpl.Checked that){
      super(that);
      modCountAndSize=that.modCountAndSize&0x1ff;
    }
    Checked(int size,long word0,long word1,long word2,long word3){
      super(word0,word1,word2,word3);
      this.modCountAndSize=size;
    }
    @Override public boolean add(boolean val){
      if(super.add(val)){
        this.modCountAndSize+=((1<<9)+1);
        return true;
      }
      return false;
    }
    @Override public boolean add(byte val){
      if(super.add(val)){
        this.modCountAndSize+=((1<<9)+1);
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(boolean val){
      final int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0 && super.removeVal(val)){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(byte val){
      final int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0 && super.removeVal(val)){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(char val){
      final int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0 && super.removeVal(val)){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(int val){
      final int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0 && super.removeVal(val)){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(long val){
      final int modCountAndSize,v;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0 && (v=(int)val)==val && super.removeVal(v)){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(float val){
      final int modCountAndSize,v;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0 && (v=(int)val)==val && super.removeVal(v)){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(double val){
      final int modCountAndSize,v;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0 && (v=(int)val)==val && super.removeVal(v)){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean remove(Object val){
      final int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        for(;;){
          final int v;
          if(val instanceof Byte){
            if(!super.removeVal((byte)val)){
              break;
            }
          }else if(val instanceof Integer || val instanceof Short){
            if(!super.removeVal(((Number)val).intValue())){
              break;
            }
          }else if(val instanceof Long){
            final long l;
            if((l=(long)val)!=(v=(int)l) || !super.removeVal(v)){
              break;
            }
          }else if(val instanceof Float){
            final float f;
            if((f=(float)val)!=(v=(int)f) || !super.removeVal(v)){
              break;
            }
          }else if(val instanceof Double){
            final double d;
            if((d=(double)val)!=(v=(int)d) || !super.removeVal(v)){
              break;
            }
          }else if(val instanceof Character){
            if(!super.removeVal((char)val)){
              break;
            }
          }else if(val instanceof Boolean){
            if(!super.removeVal((boolean)val)){
              break;
            }
          }else{
            break;
          }
          this.modCountAndSize=modCountAndSize+((1<<9)-1);
          return true;
        }
      }
      return false;
    }
    @Override public boolean contains(Object val){
      if((this.modCountAndSize&0x1ff)!=0){
        for(;;){
          final int v;
          if(val instanceof Byte){
            return super.contains((byte)val);
          }else if(val instanceof Integer || val instanceof Short){
            v=((Number)val).intValue();
          }else if(val instanceof Long){
            final long l;
            if((l=(long)val)!=(v=(int)l)){
              break;
            }     
          }else if(val instanceof Float){
            final float f;
            if((f=(float)val)!=(v=(int)f)){
              break;
            }      
          }else if(val instanceof Double){
            final double d;
            if((d=(double)val)!=(v=(int)d)){
              break;
            }
          }else if(val instanceof Character){
            return super.contains((char)val);
          }else if(val instanceof Boolean){
            return super.contains((boolean)val);
          }else{
            break;
          }
          return super.contains(v);
        }
      }
      return false;
    }
    @Override public int hashCode(){
      int size;
      if((size=this.modCountAndSize&0x1ff)!=0){
        size=ByteSetImpl.Checked.hashCodeForWord(word0,Byte.MIN_VALUE,size);
        if((size&0x1ff)!=0){
          size=ByteSetImpl.Checked.hashCodeForWord(word1,-64,size);
          if((size&0x1ff)!=0){
            size=ByteSetImpl.Checked.hashCodeForWord(word2,0,size);
            int numLeft;
            if((numLeft=(size&0x1ff))!=0){
              long word=word3;
              size>>=9;
              int offset=64;
              for(;;){
                int tail0s;
                size+=(offset+=(tail0s=Long.numberOfTrailingZeros(word)));
                if(--numLeft==0){
                  return size;
                }
                word>>>=(++tail0s);
                ++offset;
              }
            }
          }
        }
        return size>>9;
      }
      return 0;
    }
    @Override public void clear(){
      final int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        this.word0=0;
        this.word1=0;
        this.word2=0;
        this.word3=0;
        this.modCountAndSize=(modCountAndSize+(1<<9))&0xfffffe00;
      }
    }
    @Override public boolean isEmpty(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      //return (this.modCountAndSize&0x1FF)==0;
    }
    @Override public int size(){
      //TODO
      throw new omni.util.NotYetImplementedException();
      //return (this.modCountAndSize&0x1FF);
    }
    @Override public boolean removeIf(BytePredicate filter){
      final int modCountAndSize;
      return (((modCountAndSize=this.modCountAndSize)&0x1ff)!=0) && this.removeIfHelper(filter,modCountAndSize);
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      final int modCountAndSize;
      return (((modCountAndSize=this.modCountAndSize)&0x1ff)!=0) && this.removeIfHelper(filter::test,modCountAndSize);
    }
    @Override public String toString(){
      int size;
      if((size=(this.modCountAndSize&0x1ff))!=0){
        final byte[] buffer;
        (buffer=new byte[size*6])[0]='[';
        if((size=wordToStringAscending(word0,Byte.MIN_VALUE,-64,buffer,size<<11))>=1<<11){
          if((size=wordToStringAscending(word1,-64,0,buffer,size))>=1<<11){
            if((size=wordToStringAscending(word2,0,64,buffer,size))>=1<<11){
              size=finalWordToStringAscending(word3,64,buffer,size);
            }
          }
        }
        buffer[++size]=']';
        return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
      }
      return "[]";
    }
    @Override public void forEach(ByteConsumer action){
      final int modCountAndSize;
      final int numLeft;
      if((numLeft=(modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        try{
          ((ByteSetImpl)this).forEachAscendingHelper(action,numLeft);
        }finally{
          CheckedCollection.checkModCount(modCountAndSize,this.modCountAndSize);
        }
      }
    }
    @Override public void forEach(Consumer<? super Byte> action){
      final int modCountAndSize;
      final int numLeft;
      if((numLeft=(modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        try{
          ((ByteSetImpl)this).forEachAscendingHelper(action::accept,numLeft);
        }finally{
          CheckedCollection.checkModCount(modCountAndSize,this.modCountAndSize);
        }
      }
    }
    @Override public OmniIterator.OfByte iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Byte[] toArray(){
      int size;
      if((size=(this.modCountAndSize&0x1ff))!=0){
        final Byte[] dst;
        if((size=wordToArrayAscending(word0,Byte.MIN_VALUE,-64,dst=new Byte[size],size<<11))>=1<<11){
          if((size=wordToArrayAscending(word1,-64,0,dst,size))>=1<<11){
            if((size=wordToArrayAscending(word2,0,64,dst,size))>=1<<11){
              size=finalWordToArrayAscending(word3,64,dst,size);
            }
          }
        }
        return dst;
      }
      return OmniArray.OfByte.DEFAULT_BOXED_ARR;
    }
    @Override public byte[] toByteArray(){
      int size;
      if((size=(this.modCountAndSize&0x1ff))!=0){
        final byte[] dst;
        if((size=wordToArrayAscending(word0,Byte.MIN_VALUE,-64,dst=new byte[size],size<<11))>=1<<11){
          if((size=wordToArrayAscending(word1,-64,0,dst,size))>=1<<11){
            if((size=wordToArrayAscending(word2,0,64,dst,size))>=1<<11){
              size=finalWordToArrayAscending(word3,64,dst,size);
            }
          }
        }
        return dst;
      }
      return OmniArray.OfByte.DEFAULT_ARR;
    }
    @Override public short[] toShortArray(){
      int size;
      if((size=(this.modCountAndSize&0x1ff))!=0){
        final short[] dst;
        if((size=wordToArrayAscending(word0,Byte.MIN_VALUE,-64,dst=new short[size],size<<11))>=1<<11){
          if((size=wordToArrayAscending(word1,-64,0,dst,size))>=1<<11){
            if((size=wordToArrayAscending(word2,0,64,dst,size))>=1<<11){
              size=finalWordToArrayAscending(word3,64,dst,size);
            }
          }
        }
        return dst;
      }
      return OmniArray.OfShort.DEFAULT_ARR;
    }
    @Override public int[] toIntArray(){
      int size;
      if((size=(this.modCountAndSize&0x1ff))!=0){
        final int[] dst;
        if((size=wordToArrayAscending(word0,Byte.MIN_VALUE,-64,dst=new int[size],size<<11))>=1<<11){
          if((size=wordToArrayAscending(word1,-64,0,dst,size))>=1<<11){
            if((size=wordToArrayAscending(word2,0,64,dst,size))>=1<<11){
              size=finalWordToArrayAscending(word3,64,dst,size);
            }
          }
        }
        return dst;
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
    @Override public long[] toLongArray(){
      int size;
      if((size=(this.modCountAndSize&0x1ff))!=0){
        final long[] dst;
        if((size=wordToArrayAscending(word0,Byte.MIN_VALUE,-64,dst=new long[size],size<<11))>=1<<11){
          if((size=wordToArrayAscending(word1,-64,0,dst,size))>=1<<11){
            if((size=wordToArrayAscending(word2,0,64,dst,size))>=1<<11){
              size=finalWordToArrayAscending(word3,64,dst,size);
            }
          }
        }
        return dst;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override public float[] toFloatArray(){
      int size;
      if((size=(this.modCountAndSize&0x1ff))!=0){
        final float[] dst;
        if((size=wordToArrayAscending(word0,Byte.MIN_VALUE,-64,dst=new float[size],size<<11))>=1<<11){
          if((size=wordToArrayAscending(word1,-64,0,dst,size))>=1<<11){
            if((size=wordToArrayAscending(word2,0,64,dst,size))>=1<<11){
              size=finalWordToArrayAscending(word3,64,dst,size);
            }
          }
        }
        return dst;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override public double[] toDoubleArray(){
      int size;
      if((size=(this.modCountAndSize&0x1ff))!=0){
        final double[] dst;
        if((size=wordToArrayAscending(word0,Byte.MIN_VALUE,-64,dst=new double[size],size<<11))>=1<<11){
          if((size=wordToArrayAscending(word1,-64,0,dst,size))>=1<<11){
            if((size=wordToArrayAscending(word2,0,64,dst,size))>=1<<11){
              size=finalWordToArrayAscending(word3,64,dst,size);
            }
          }
        }
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override public <T> T[] toArray(T[] dst){
      int size;
      if((size=(this.modCountAndSize&0x1ff))!=0){
        if((size=wordToArrayAscending(word0,Byte.MIN_VALUE,-64,dst=OmniArray.uncheckedArrResize(size,dst),size<<11))>=1<<11){
          if((size=wordToArrayAscending(word1,-64,0,dst,size))>=1<<11){
            if((size=wordToArrayAscending(word2,0,64,dst,size))>=1<<11){
              size=finalWordToArrayAscending(word3,64,dst,size);
            }
          }
        }
      }else if(dst.length!=0){
        dst[0]=null;
      }
      return dst;
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      int size;
      final T[] dst;
      final int modCountAndSize=this.modCountAndSize;
      try{
        dst=arrConstructor.apply(size=(modCountAndSize&0x1ff));
      }finally{
        CheckedCollection.checkModCount(modCountAndSize,this.modCountAndSize);
      }
      if(size!=0){
        if((size=wordToArrayAscending(this.word0,Byte.MIN_VALUE,-64,dst,size<<11))>=1<<11){
          if((size=wordToArrayAscending(this.word1,-64,0,dst,size))>=1<<11){
            if((size=wordToArrayAscending(this.word2,0,64,dst,size))>=1<<11){
              size=finalWordToArrayAscending(this.word3,64,dst,size);
            }
          }
        }
      }
      return dst;
    }
    @Override public void writeExternal(ObjectOutput output) throws IOException{
      final int modCountAndSize=this.modCountAndSize;
      try{
        output.writeShort(modCountAndSize);
        int numLeft;
        if((numLeft=modCountAndSize&0x1ff)!=0){
          //only write as many words as we need to
          //start by writing the positive number since those will probably be more common
          long word;
          output.writeLong(word=this.word2);
          if((numLeft-=Long.bitCount(word))!=0){
            output.writeLong(word=this.word3);
            if((numLeft-=Long.bitCount(word))!=0){
              output.writeLong(word=this.word1);
              if((numLeft-=Long.bitCount(word))!=0){
                output.writeLong(this.word0);
              }
            }
          }
        }
      }finally{
        CheckedCollection.checkModCount(modCountAndSize,this.modCountAndSize);
      }
    }
    @Override public void readExternal(ObjectInput input) throws IOException{
      int numLeft;
      this.modCountAndSize=numLeft=input.readUnsignedShort();
      if((numLeft&=0x1ff)!=0){
        long word;
        word2=word=input.readLong();
        if((numLeft-=Long.bitCount(word))!=0){
          word3=word=input.readLong();
          if((numLeft-=Long.bitCount(word))!=0){
            word1=word=input.readLong();
            if((numLeft-=Long.bitCount(word))!=0){
              word0=input.readLong();
            }
          }
        }
      }
    }
    private boolean removeIfHelper(BytePredicate filter,int modCountAndSize){
      long word,newWord0,newWord1,newWord2,newWord3;
      int numRemoved;
      try {
        numRemoved=Long.bitCount((word=this.word0)^(newWord0=processWordRemoveIf(word,Byte.MIN_VALUE,filter)))
          +Long.bitCount((word=this.word1)^(newWord1=processWordRemoveIf(word,-64,filter)))
          +Long.bitCount((word=this.word2)^(newWord2=processWordRemoveIf(word,0,filter)))
          +Long.bitCount((word=this.word3)^(newWord3=processWordRemoveIf(word,64,filter)));
      }finally {
        CheckedCollection.checkModCount(modCountAndSize,this.modCountAndSize);
      }
      if(numRemoved!=0) {
        this.word0=newWord0;
        this.word1=newWord1;
        this.word2=newWord2;
        this.word3=newWord3;
        this.modCountAndSize=modCountAndSize-numRemoved+(1<<9);
        return true;
      }
      return false;
    }
    private int removeIfHelper(BytePredicate filter,int exclusiveTo,CheckedCollection.AbstractModCountChecker modCountChecker){
      long newWord0=this.word0,newWord1=this.word1,newWord2=this.word2,newWord3=this.word3;
      int numRemoved;
      try{
        goToEnd:for(;;){
          goToWord0:for(;;){
            goToWord1:for(;;){
              switch(exclusiveTo>>6){
              case -2:
                numRemoved=Long.bitCount(((newWord0)^(newWord0=processSubSetWordRemoveIf(newWord0,Byte.MIN_VALUE,exclusiveTo,filter)))&(-1L>>>-exclusiveTo));
                break goToEnd;
              case -1:
                numRemoved=Long.bitCount(((newWord1)^(newWord1=processSubSetWordRemoveIf(newWord1,-64,exclusiveTo,filter)))&(-1L>>>-exclusiveTo));
                break goToWord0;
              case 0:
                numRemoved=Long.bitCount(((newWord2)^(newWord2=processSubSetWordRemoveIf(newWord2,0,exclusiveTo,filter)))&(-1L>>>-exclusiveTo));
                break goToWord1;
              default:
                numRemoved=Long.bitCount(((newWord3)^(newWord3=processSubSetWordRemoveIf(newWord3,64,exclusiveTo,filter)))&(-1L>>>-exclusiveTo));
              }
              numRemoved+=Long.bitCount(newWord2^(newWord2=processWordRemoveIf(newWord2,0,filter)));
              break;
            }
            numRemoved+=Long.bitCount(newWord1^(newWord1=processWordRemoveIf(newWord1,-64,filter)));
            break;
          }
          numRemoved+=Long.bitCount(newWord0^(newWord0=processWordRemoveIf(newWord0,Byte.MIN_VALUE,filter)));
          break;
        }
      }finally{
        modCountChecker.checkModCount();
      }
      if(numRemoved!=0){
        this.word0=newWord0;
        this.word1=newWord1;
        this.word2=newWord2;
        this.word3=newWord3;
      }
      return numRemoved;
    }
    private int removeIfHelper(int inclusiveFrom,BytePredicate filter,CheckedCollection.AbstractModCountChecker modCountChecker){
      long newWord0=this.word0,newWord1=this.word1,newWord2=this.word2,newWord3=this.word3;
      int numRemoved;
      try{
        goToEnd:for(;;){
          goToWord3:for(;;){
            goToWord2:for(;;){
              switch(inclusiveFrom>>6){
              case 1:
                numRemoved=Long.bitCount(((newWord3)^(newWord3=processWordRemoveIf(newWord3,inclusiveFrom,filter)))&(-1L<<inclusiveFrom));
                break goToEnd;
              case 0:
                numRemoved=Long.bitCount(((newWord2)^(newWord2=processWordRemoveIf(newWord2,inclusiveFrom,filter)))&(-1L<<inclusiveFrom));
                break goToWord3;
              case -1:
                numRemoved=Long.bitCount(((newWord1)^(newWord1=processWordRemoveIf(newWord1,inclusiveFrom,filter)))&(-1L<<inclusiveFrom));
                break goToWord2;
              default:
                numRemoved=Long.bitCount(((newWord0)^(newWord0=processWordRemoveIf(newWord0,inclusiveFrom,filter)))&(-1L<<inclusiveFrom));
              }
              numRemoved+=Long.bitCount(newWord1^(newWord1=processWordRemoveIf(newWord1,-64,filter)));
              break;
            }
            numRemoved+=Long.bitCount(newWord2^(newWord2=processWordRemoveIf(newWord2,0,filter)));
            break;
          }
          numRemoved+=Long.bitCount(newWord3^(newWord3=processWordRemoveIf(newWord3,64,filter)));
          break;
        }
      }finally{
        modCountChecker.checkModCount();
      }
      if(numRemoved!=0){
        this.word0=newWord0;
        this.word1=newWord1;
        this.word2=newWord2;
        this.word3=newWord3;
      }
      return numRemoved;
    }
    private int removeIfHelper(int inclusiveFrom,BytePredicate filter,int exclusiveTo,CheckedCollection.AbstractModCountChecker modCountChecker){
      long newWord0=this.word0,newWord1=this.word1,newWord2=this.word2,newWord3=this.word3;
      int numRemoved;
      try{
        goToEnd:switch(inclusiveFrom>>6){
          case -2:
            goToWord0:for(;;){
              goToWord1:for(;;){
                goToWord2:switch(exclusiveTo>>6){
                  case -2:
                    numRemoved=Long.bitCount((newWord0^(newWord0=processSubSetWordRemoveIf(newWord0,inclusiveFrom,exclusiveTo,filter))&(-1L<<inclusiveFrom)&(-1L>>>-exclusiveTo)));
                    break goToEnd;
                  case -1:
                    numRemoved=Long.bitCount((newWord1^(newWord1=processSubSetWordRemoveIf(newWord1,-64,exclusiveTo,filter))&(-1L>>>-exclusiveTo)));
                    break goToWord0;
                  case 0:
                    numRemoved=Long.bitCount((newWord2^(newWord2=processSubSetWordRemoveIf(newWord2,0,exclusiveTo,filter))&(-1L>>>-exclusiveTo)));
                    break goToWord1;
                  default:
                    numRemoved=Long.bitCount((newWord3^(newWord3=processSubSetWordRemoveIf(newWord3,64,exclusiveTo,filter))&(-1L>>>-exclusiveTo)));
                    break goToWord2;
                }
                numRemoved+=Long.bitCount(newWord2^(newWord2=processWordRemoveIf(newWord2,0,filter)));
                break;
              }
              numRemoved+=Long.bitCount(newWord1^(newWord1=processWordRemoveIf(newWord1,-64,filter)));
              break;
            }
            numRemoved+=Long.bitCount((newWord0^(newWord0=processWordRemoveIf(newWord0,inclusiveFrom,filter))&(1L<<inclusiveFrom)));
            break;
          case -1:
            goToWord1:for(;;){
              goToWord2:switch(exclusiveTo>>6){
                case -1:
                  numRemoved=Long.bitCount((newWord1^(newWord1=processSubSetWordRemoveIf(newWord1,inclusiveFrom,exclusiveTo,filter))&(-1L<<inclusiveFrom)&(-1L>>>-exclusiveTo)));
                  break goToEnd;
                case 0:
                  numRemoved=Long.bitCount((newWord2^(newWord2=processSubSetWordRemoveIf(newWord2,0,exclusiveTo,filter))&(-1L>>>-exclusiveTo)));
                  break goToWord1;
                default:
                  numRemoved=Long.bitCount((newWord3^(newWord3=processSubSetWordRemoveIf(newWord3,64,exclusiveTo,filter))&(-1L>>>-exclusiveTo)));
                  break goToWord2;
              }
              numRemoved+=Long.bitCount(newWord2^(newWord2=processWordRemoveIf(newWord2,0,filter)));
              break;
            }
            numRemoved+=Long.bitCount((newWord1^(newWord1=processWordRemoveIf(newWord1,inclusiveFrom,filter))&(1L<<inclusiveFrom)));
            break;
          case 0:
            if((exclusiveTo>>6)==0){
              numRemoved=Long.bitCount((newWord2^(newWord2=processSubSetWordRemoveIf(newWord2,inclusiveFrom,exclusiveTo,filter))&(-1L<<inclusiveFrom)&(-1L>>>-exclusiveTo)));
            }else{
              numRemoved=Long.bitCount((newWord2^(newWord2=processWordRemoveIf(newWord2,inclusiveFrom,filter))&(1L<<inclusiveFrom)))
                + Long.bitCount((newWord3^(newWord3=processSubSetWordRemoveIf(newWord3,64,exclusiveTo,filter))&(-1L>>>-exclusiveTo)));
            }
            break;
          default:
            numRemoved=Long.bitCount((newWord3^(newWord3=processSubSetWordRemoveIf(newWord3,inclusiveFrom,exclusiveTo,filter))&(-1L<<inclusiveFrom)&(-1L>>>-exclusiveTo)));
        }
      }finally{
        modCountChecker.checkModCount();
      }
      if(numRemoved!=0){
        this.word0=newWord0;
        this.word1=newWord1;
        this.word2=newWord2;
        this.word3=newWord3;
      }
      return numRemoved;
    }
    private static int hashCodeForWord(long word,int offset,int sumAndNumLeft){
      int tail0s;
      if((tail0s=Long.numberOfTrailingZeros(word))==64){
        return sumAndNumLeft;
      }
      int numLeft=sumAndNumLeft&0x1ff;
      sumAndNumLeft&=0xfffffe00;
      for(;;){
        sumAndNumLeft+=((offset+=tail0s)<<9);
        if(--numLeft==0){
          return sumAndNumLeft;
        }
        if((tail0s=Long.numberOfTrailingZeros(word>>>=(++tail0s)))==64){
          return sumAndNumLeft|numLeft;
        }
        ++offset;
      }
    }
    public static class Descending extends Checked{
      private static final long serialVersionUID=1L;
      public Descending(){
        super();
      }
      public Descending(ByteSetImpl that){
        super(that);
      }
      public Descending(Collection<? extends Byte> that){
        super(that);
      }
      public Descending(OmniCollection.OfRef<? extends Byte> that){
        super(that);
      }
      public Descending(OmniCollection.ByteOutput<?> that){
        super(that);
      }
      public Descending(OmniCollection.OfByte that){
        super(that);
      }
      public Descending(OmniCollection.OfBoolean that){
        super(that);
      }
      @Override public Object clone(){
        return new Descending(this);
      }
      public Descending(ByteSetImpl.Checked that){
        super(that);
      }
      Descending(int size,long word0,long word1,long word2,long word3){
        super(size,word0,word1,word2,word3);
      }
      @Override public String toString(){
        int size;
        if((size=(this.modCountAndSize&0x1ff))!=0){
          final byte[] buffer;
          (buffer=new byte[size*6])[0]='[';
          if((size=wordToStringDescending(word3,64,128,buffer,size<<11))>=1<<11){
            if((size=wordToStringDescending(word2,0,64,buffer,size))>=1<<11){
              if((size=wordToStringDescending(word1,-64,0,buffer,size))>=1<<11){
                size=finalWordToStringDescending(word0,-64,buffer,size);
              }
            }
          }
          buffer[++size]=']';
          return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
        }
        return "[]";
      }
      @Override public void forEach(ByteConsumer action){
        final int modCountAndSize;
        final int numLeft;
        if((numLeft=(modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
          try{
            ((ByteSetImpl)this).forEachDescendingHelper(action,numLeft);
          }finally{
            CheckedCollection.checkModCount(modCountAndSize,this.modCountAndSize);
          }
        }
      }
      @Override public void forEach(Consumer<? super Byte> action){
        final int modCountAndSize;
        final int numLeft;
        if((numLeft=(modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
          try{
            ((ByteSetImpl)this).forEachDescendingHelper(action::accept,numLeft);
          }finally{
            CheckedCollection.checkModCount(modCountAndSize,this.modCountAndSize);
          }
        }
      }
      @Override public OmniIterator.OfByte iterator(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public Byte[] toArray(){
        int size;
        if((size=(this.modCountAndSize&0x1ff))!=0){
          final Byte[] dst;
          if((size=wordToArrayDescending(word3,64,128,dst=new Byte[size],size<<11))>=1<<11){
            if((size=wordToArrayDescending(word2,0,64,dst,size))>=1<<11){
              if((size=wordToArrayDescending(word1,-64,0,dst,size))>=1<<11){
                size=finalWordToArrayDescending(word0,-64,dst,size);
              }
            }
          }
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_BOXED_ARR;
      }
      @Override public byte[] toByteArray(){
        int size;
        if((size=(this.modCountAndSize&0x1ff))!=0){
          final byte[] dst;
          if((size=wordToArrayDescending(word3,64,128,dst=new byte[size],size<<11))>=1<<11){
            if((size=wordToArrayDescending(word2,0,64,dst,size))>=1<<11){
              if((size=wordToArrayDescending(word1,-64,0,dst,size))>=1<<11){
                size=finalWordToArrayDescending(word0,-64,dst,size);
              }
            }
          }
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_ARR;
      }
      @Override public short[] toShortArray(){
        int size;
        if((size=(this.modCountAndSize&0x1ff))!=0){
          final short[] dst;
          if((size=wordToArrayDescending(word3,64,128,dst=new short[size],size<<11))>=1<<11){
            if((size=wordToArrayDescending(word2,0,64,dst,size))>=1<<11){
              if((size=wordToArrayDescending(word1,-64,0,dst,size))>=1<<11){
                size=finalWordToArrayDescending(word0,-64,dst,size);
              }
            }
          }
          return dst;
        }
        return OmniArray.OfShort.DEFAULT_ARR;
      }
      @Override public int[] toIntArray(){
        int size;
        if((size=(this.modCountAndSize&0x1ff))!=0){
          final int[] dst;
          if((size=wordToArrayDescending(word3,64,128,dst=new int[size],size<<11))>=1<<11){
            if((size=wordToArrayDescending(word2,0,64,dst,size))>=1<<11){
              if((size=wordToArrayDescending(word1,-64,0,dst,size))>=1<<11){
                size=finalWordToArrayDescending(word0,-64,dst,size);
              }
            }
          }
          return dst;
        }
        return OmniArray.OfInt.DEFAULT_ARR;
      }
      @Override public long[] toLongArray(){
        int size;
        if((size=(this.modCountAndSize&0x1ff))!=0){
          final long[] dst;
          if((size=wordToArrayDescending(word3,64,128,dst=new long[size],size<<11))>=1<<11){
            if((size=wordToArrayDescending(word2,0,64,dst,size))>=1<<11){
              if((size=wordToArrayDescending(word1,-64,0,dst,size))>=1<<11){
                size=finalWordToArrayDescending(word0,-64,dst,size);
              }
            }
          }
          return dst;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
      }
      @Override public float[] toFloatArray(){
        int size;
        if((size=(this.modCountAndSize&0x1ff))!=0){
          final float[] dst;
          if((size=wordToArrayDescending(word3,64,128,dst=new float[size],size<<11))>=1<<11){
            if((size=wordToArrayDescending(word2,0,64,dst,size))>=1<<11){
              if((size=wordToArrayDescending(word1,-64,0,dst,size))>=1<<11){
                size=finalWordToArrayDescending(word0,-64,dst,size);
              }
            }
          }
          return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
      }
      @Override public double[] toDoubleArray(){
        int size;
        if((size=(this.modCountAndSize&0x1ff))!=0){
          final double[] dst;
          if((size=wordToArrayDescending(word3,64,128,dst=new double[size],size<<11))>=1<<11){
            if((size=wordToArrayDescending(word2,0,64,dst,size))>=1<<11){
              if((size=wordToArrayDescending(word1,-64,0,dst,size))>=1<<11){
                size=finalWordToArrayDescending(word0,-64,dst,size);
              }
            }
          }
          return dst;
        }
        return OmniArray.OfDouble.DEFAULT_ARR;
      }
      @Override public <T> T[] toArray(T[] dst){
        int size;
        if((size=(this.modCountAndSize&0x1ff))!=0){
          if((size=wordToArrayDescending(word3,64,128,dst=OmniArray.uncheckedArrResize(size,dst),size<<11))>=1<<11){
            if((size=wordToArrayDescending(word2,0,64,dst,size))>=1<<11){
              if((size=wordToArrayDescending(word1,-64,0,dst,size))>=1<<11){
                size=finalWordToArrayDescending(word0,-64,dst,size);
              }
            }
          }
        }else if(dst.length!=0){
          dst[0]=null;
        }
        return dst;
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        int size;
        final T[] dst;
        final int modCountAndSize=this.modCountAndSize;
        try{
          dst=arrConstructor.apply(size=(modCountAndSize&0x1ff));
        }finally{
          CheckedCollection.checkModCount(modCountAndSize,this.modCountAndSize);
        }
        if(size!=0){
          if((size=wordToArrayDescending(this.word3,64,128,dst,size<<11))>=1<<11){
            if((size=wordToArrayDescending(this.word2,0,64,dst,size))>=1<<11){
              if((size=wordToArrayDescending(this.word1,-64,0,dst,size))>=1<<11){
                size=finalWordToArrayDescending(this.word0,-64,dst,size);
              }
            }
          }
        }
        return dst;
      }
    }
  }
  public static class Descending extends ByteSetImpl{
    private static final long serialVersionUID=1L;
    public Descending(){
      super();
    }
    public Descending(ByteSetImpl that){
      super(that);
    }
    public Descending(Collection<? extends Byte> that){
      super(that);
    }
    public Descending(OmniCollection.OfRef<? extends Byte> that){
      super(that);
    }
    public Descending(OmniCollection.ByteOutput<?> that){
      super(that);
    }
    public Descending(OmniCollection.OfByte that){
      super(that);
    }
    public Descending(OmniCollection.OfBoolean that){
      super(that);
    }
    @Override public Object clone(){
      return new Descending(this);
    }
    Descending(long word0,long word1,long word2,long word3){
      super(word0,word1,word2,word3);
    }
    @Override public String toString(){
      int size;
      final long word0,word1,word2,word3;
      if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){  
        final byte[] buffer;
        (buffer=new byte[size*6])[0]='[';
        if((size=wordToStringDescending(word3,64,128,buffer,size<<11))>=1<<11){
          if((size=wordToStringDescending(word2,0,64,buffer,size))>=1<<11){
            if((size=wordToStringDescending(word1,-64,0,buffer,size))>=1<<11){
              size=finalWordToStringDescending(word0,-64,buffer,size);
            }
          }
        }
        buffer[++size]=']';
        return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
      }
      return "[]";
    }
    @Override public void forEach(ByteConsumer action){
      forEachWordDescending(this.word3,128,action);
      forEachWordDescending(this.word2,64,action);
      forEachWordDescending(this.word1,0,action);
      forEachWordDescending(this.word0,-64,action);
    }
    @Override public void forEach(Consumer<? super Byte> action){
      this.forEach((ByteConsumer)action::accept);
    }
    @Override public OmniIterator.OfByte iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Byte[] toArray(){
      int size;
      final long word0,word1,word2,word3;
      if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){  
        final Byte[] dst;
        if((size=wordToArrayDescending(word3,64,128,dst=new Byte[size],size<<11))>=1<<11){
          if((size=wordToArrayDescending(word2,0,64,dst,size))>=1<<11){
            if((size=wordToArrayDescending(word1,-64,0,dst,size))>=1<<11){
              size=finalWordToArrayDescending(word0,-64,dst,size);
            }
          }
        }
        return dst;
      }
      return OmniArray.OfByte.DEFAULT_BOXED_ARR;
    }
    @Override public byte[] toByteArray(){
      int size;
      final long word0,word1,word2,word3;
      if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){  
        final byte[] dst;
        if((size=wordToArrayDescending(word3,64,128,dst=new byte[size],size<<11))>=1<<11){
          if((size=wordToArrayDescending(word2,0,64,dst,size))>=1<<11){
            if((size=wordToArrayDescending(word1,-64,0,dst,size))>=1<<11){
              size=finalWordToArrayDescending(word0,-64,dst,size);
            }
          }
        }
        return dst;
      }
      return OmniArray.OfByte.DEFAULT_ARR;
    }
    @Override public short[] toShortArray(){
      int size;
      final long word0,word1,word2,word3;
      if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){  
        final short[] dst;
        if((size=wordToArrayDescending(word3,64,128,dst=new short[size],size<<11))>=1<<11){
          if((size=wordToArrayDescending(word2,0,64,dst,size))>=1<<11){
            if((size=wordToArrayDescending(word1,-64,0,dst,size))>=1<<11){
              size=finalWordToArrayDescending(word0,-64,dst,size);
            }
          }
        }
        return dst;
      }
      return OmniArray.OfShort.DEFAULT_ARR;
    }
    @Override public int[] toIntArray(){
      int size;
      final long word0,word1,word2,word3;
      if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){  
        final int[] dst;
        if((size=wordToArrayDescending(word3,64,128,dst=new int[size],size<<11))>=1<<11){
          if((size=wordToArrayDescending(word2,0,64,dst,size))>=1<<11){
            if((size=wordToArrayDescending(word1,-64,0,dst,size))>=1<<11){
              size=finalWordToArrayDescending(word0,-64,dst,size);
            }
          }
        }
        return dst;
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
    @Override public long[] toLongArray(){
      int size;
      final long word0,word1,word2,word3;
      if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){  
        final long[] dst;
        if((size=wordToArrayDescending(word3,64,128,dst=new long[size],size<<11))>=1<<11){
          if((size=wordToArrayDescending(word2,0,64,dst,size))>=1<<11){
            if((size=wordToArrayDescending(word1,-64,0,dst,size))>=1<<11){
              size=finalWordToArrayDescending(word0,-64,dst,size);
            }
          }
        }
        return dst;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override public float[] toFloatArray(){
      int size;
      final long word0,word1,word2,word3;
      if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){  
        final float[] dst;
        if((size=wordToArrayDescending(word3,64,128,dst=new float[size],size<<11))>=1<<11){
          if((size=wordToArrayDescending(word2,0,64,dst,size))>=1<<11){
            if((size=wordToArrayDescending(word1,-64,0,dst,size))>=1<<11){
              size=finalWordToArrayDescending(word0,-64,dst,size);
            }
          }
        }
        return dst;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override public double[] toDoubleArray(){
      int size;
      final long word0,word1,word2,word3;
      if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){  
        final double[] dst;
        if((size=wordToArrayDescending(word3,64,128,dst=new double[size],size<<11))>=1<<11){
          if((size=wordToArrayDescending(word2,0,64,dst,size))>=1<<11){
            if((size=wordToArrayDescending(word1,-64,0,dst,size))>=1<<11){
              size=finalWordToArrayDescending(word0,-64,dst,size);
            }
          }
        }
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override public <T> T[] toArray(T[] dst){
      int size;
      final long word0,word1,word2,word3;
      if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){  
        if((size=wordToArrayDescending(word3,64,128,dst=OmniArray.uncheckedArrResize(size,dst),size<<11))>=1<<11){
          if((size=wordToArrayDescending(word2,0,64,dst,size))>=1<<11){
            if((size=wordToArrayDescending(word1,-64,0,dst,size))>=1<<11){
              size=finalWordToArrayDescending(word0,-64,dst,size);
            }
          }
        }
      }else if(dst.length!=0){
        dst[0]=null;
      }
      return dst;
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      int size;
      final long word0,word1,word2,word3;
      final T[] dst=arrConstructor.apply(size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3));
      if(size!=0){
        if((size=wordToArrayDescending(word3,64,128,dst,size<<11))>=1<<11){
          if((size=wordToArrayDescending(word2,0,64,dst,size))>=1<<11){
            if((size=wordToArrayDescending(word1,-64,0,dst,size))>=1<<11){
              size=finalWordToArrayDescending(word0,-64,dst,size);
            }
          }
        }
      }
      return dst;
    }
  }
  private static class UncheckedFullView extends AbstractByteSet implements Cloneable,Serializable{
    private static final long serialVersionUID=1L;
    transient final ByteSetImpl root;
    private UncheckedFullView(ByteSetImpl root){
      this.root=root;
    }
    @Override public boolean add(boolean val){
      return root.add(val);
    }
    @Override public boolean add(byte val){
      return root.add(val);
    }
    @Override public boolean contains(boolean val){
      return root.contains(val);
    }
    @Override public boolean contains(byte val){
      return root.contains(val);
    }
    @Override public boolean contains(char val){
      return root.contains(val);
    }
    @Override public boolean contains(int val){
      return root.contains(val);
    }
    @Override public boolean contains(long val){
      return root.contains(val);
    }
    @Override public boolean contains(float val){
      return root.contains(val);
    }
    @Override public boolean contains(double val){
      return root.contains(val);
    }
    @Override public boolean contains(Object val){
      return root.contains(val);
    }
    @Override public boolean removeVal(boolean val){
      return root.removeVal(val);
    }
    @Override public boolean removeVal(byte val){
      return root.removeVal(val);
    }
    @Override public boolean removeVal(char val){
      return root.removeVal(val);
    }
    @Override public boolean removeVal(int val){
      return root.removeVal(val);
    }
    @Override public boolean removeVal(long val){
      return root.removeVal(val);
    }
    @Override public boolean removeVal(float val){
      return root.removeVal(val);
    }
    @Override public boolean removeVal(double val){
      return root.removeVal(val);
    }
    @Override public boolean remove(Object val){
      return root.remove(val);
    }
    @Override public int hashCode(){
      return root.hashCode();
    }
    @Override public void clear(){
      final ByteSetImpl root;
      (root=this.root).word0=0;
      root.word1=0;
      root.word2=0;
      root.word3=0;  
    }
    @Override public boolean isEmpty(){
      final ByteSetImpl root;
      return (root=this.root).word0==0 && root.word1==0 && root.word2==0 && root.word3==0;
    }
    @Override public int size(){
      final ByteSetImpl root;
      return SetCommonImpl.size((root=this.root).word0,root.word1,root.word2,root.word3);
    }
    @Override public boolean removeIf(BytePredicate filter){
      return root.removeIf((BytePredicate)filter);
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      return root.removeIf((BytePredicate)filter::test);
    }
    @Override public String toString(){
      int size;
      final long word0,word1,word2,word3;
      final ByteSetImpl root;
      if((size=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3))!=0){      
        final byte[] buffer;
        (buffer=new byte[size*6])[0]='[';
        if((size=wordToStringAscending(word0,Byte.MIN_VALUE,-64,buffer,size<<11))>=1<<11){
          if((size=wordToStringAscending(word1,-64,0,buffer,size))>=1<<11){
            if((size=wordToStringAscending(word2,0,64,buffer,size))>=1<<11){
              size=finalWordToStringAscending(word3,64,buffer,size);
            }
          }
        }
        buffer[++size]=']';
        return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
      }
      return "[]";
    }
    @Override public void forEach(ByteConsumer action){
      final ByteSetImpl root;
      forEachWordAscending((root=this.root).word0,Byte.MIN_VALUE,action);
      forEachWordAscending(root.word1,-64,action);
      forEachWordAscending(root.word2,0,action);
      forEachWordAscending(root.word3,64,action);
    }
    @Override public void forEach(Consumer<? super Byte> action){
      this.forEach((ByteConsumer)action::accept);
    }
    @Override public OmniIterator.OfByte iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Byte[] toArray(){
      int size;
      final long word0,word1,word2,word3;
      final ByteSetImpl root;
      if((size=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3))!=0){      
        final Byte[] dst;
        if((size=wordToArrayAscending(word0,Byte.MIN_VALUE,-64,dst=new Byte[size],size<<11))>=1<<11){
          if((size=wordToArrayAscending(word1,-64,0,dst,size))>=1<<11){
            if((size=wordToArrayAscending(word2,0,64,dst,size))>=1<<11){
              size=finalWordToArrayAscending(word3,64,dst,size);
            }
          }
        }
        return dst;
      }
      return OmniArray.OfByte.DEFAULT_BOXED_ARR;
    }
    @Override public byte[] toByteArray(){
      int size;
      final long word0,word1,word2,word3;
      final ByteSetImpl root;
      if((size=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3))!=0){      
        final byte[] dst;
        if((size=wordToArrayAscending(word0,Byte.MIN_VALUE,-64,dst=new byte[size],size<<11))>=1<<11){
          if((size=wordToArrayAscending(word1,-64,0,dst,size))>=1<<11){
            if((size=wordToArrayAscending(word2,0,64,dst,size))>=1<<11){
              size=finalWordToArrayAscending(word3,64,dst,size);
            }
          }
        }
        return dst;
      }
      return OmniArray.OfByte.DEFAULT_ARR;
    }
    @Override public short[] toShortArray(){
      int size;
      final long word0,word1,word2,word3;
      final ByteSetImpl root;
      if((size=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3))!=0){      
        final short[] dst;
        if((size=wordToArrayAscending(word0,Byte.MIN_VALUE,-64,dst=new short[size],size<<11))>=1<<11){
          if((size=wordToArrayAscending(word1,-64,0,dst,size))>=1<<11){
            if((size=wordToArrayAscending(word2,0,64,dst,size))>=1<<11){
              size=finalWordToArrayAscending(word3,64,dst,size);
            }
          }
        }
        return dst;
      }
      return OmniArray.OfShort.DEFAULT_ARR;
    }
    @Override public int[] toIntArray(){
      int size;
      final long word0,word1,word2,word3;
      final ByteSetImpl root;
      if((size=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3))!=0){      
        final int[] dst;
        if((size=wordToArrayAscending(word0,Byte.MIN_VALUE,-64,dst=new int[size],size<<11))>=1<<11){
          if((size=wordToArrayAscending(word1,-64,0,dst,size))>=1<<11){
            if((size=wordToArrayAscending(word2,0,64,dst,size))>=1<<11){
              size=finalWordToArrayAscending(word3,64,dst,size);
            }
          }
        }
        return dst;
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
    @Override public long[] toLongArray(){
      int size;
      final long word0,word1,word2,word3;
      final ByteSetImpl root;
      if((size=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3))!=0){      
        final long[] dst;
        if((size=wordToArrayAscending(word0,Byte.MIN_VALUE,-64,dst=new long[size],size<<11))>=1<<11){
          if((size=wordToArrayAscending(word1,-64,0,dst,size))>=1<<11){
            if((size=wordToArrayAscending(word2,0,64,dst,size))>=1<<11){
              size=finalWordToArrayAscending(word3,64,dst,size);
            }
          }
        }
        return dst;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override public float[] toFloatArray(){
      int size;
      final long word0,word1,word2,word3;
      final ByteSetImpl root;
      if((size=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3))!=0){      
        final float[] dst;
        if((size=wordToArrayAscending(word0,Byte.MIN_VALUE,-64,dst=new float[size],size<<11))>=1<<11){
          if((size=wordToArrayAscending(word1,-64,0,dst,size))>=1<<11){
            if((size=wordToArrayAscending(word2,0,64,dst,size))>=1<<11){
              size=finalWordToArrayAscending(word3,64,dst,size);
            }
          }
        }
        return dst;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override public double[] toDoubleArray(){
      int size;
      final long word0,word1,word2,word3;
      final ByteSetImpl root;
      if((size=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3))!=0){      
        final double[] dst;
        if((size=wordToArrayAscending(word0,Byte.MIN_VALUE,-64,dst=new double[size],size<<11))>=1<<11){
          if((size=wordToArrayAscending(word1,-64,0,dst,size))>=1<<11){
            if((size=wordToArrayAscending(word2,0,64,dst,size))>=1<<11){
              size=finalWordToArrayAscending(word3,64,dst,size);
            }
          }
        }
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override public <T> T[] toArray(T[] dst){
      int size;
      final long word0,word1,word2,word3;
      final ByteSetImpl root;
      if((size=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3))!=0){      
        if((size=wordToArrayAscending(word0,Byte.MIN_VALUE,-64,dst=OmniArray.uncheckedArrResize(size,dst),size<<11))>=1<<11){
          if((size=wordToArrayAscending(word1,-64,0,dst,size))>=1<<11){
            if((size=wordToArrayAscending(word2,0,64,dst,size))>=1<<11){
              size=finalWordToArrayAscending(word3,64,dst,size);
            }
          }
        }
      }else if(dst.length!=0){
        dst[0]=null;
      }
      return dst;
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      int size;
      final long word0,word1,word2,word3;
      final ByteSetImpl root;
      final T[] dst=arrConstructor.apply(size=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3));
      if(size!=0){
        if((size=wordToArrayAscending(word0,Byte.MIN_VALUE,-64,dst,size<<11))>=1<<11){
          if((size=wordToArrayAscending(word1,-64,0,dst,size))>=1<<11){
            if((size=wordToArrayAscending(word2,0,64,dst,size))>=1<<11){
              size=finalWordToArrayAscending(word3,64,dst,size);
            }
          }
        }
      }
      return dst;
    }
    private Object writeReplace(){
      return new ByteSetImpl(root);
    }
    private static class Descending extends UncheckedFullView{
      private Descending(ByteSetImpl root){
        super(root);
      }
      private Object writeReplace(){
        return new ByteSetImpl.Descending(root);
      }
      @Override public String toString(){
        int size;
        final long word0,word1,word2,word3;
        final ByteSetImpl root;
        if((size=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3))!=0){      
          final byte[] buffer;
          (buffer=new byte[size*6])[0]='[';
          if((size=wordToStringDescending(word3,64,128,buffer,size<<11))>=1<<11){
            if((size=wordToStringDescending(word2,0,64,buffer,size))>=1<<11){
              if((size=wordToStringDescending(word1,-64,0,buffer,size))>=1<<11){
                size=finalWordToStringDescending(word0,-64,buffer,size);
              }
            }
          }
          buffer[++size]=']';
          return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
        }
        return "[]";
      }
      @Override public void forEach(ByteConsumer action){
        final ByteSetImpl root;
        forEachWordDescending((root=this.root).word3,128,action);
        forEachWordDescending(root.word2,64,action);
        forEachWordDescending(root.word1,0,action);
        forEachWordDescending(root.word0,-64,action);
      }
      @Override public void forEach(Consumer<? super Byte> action){
        this.forEach((ByteConsumer)action::accept);
      }
      @Override public OmniIterator.OfByte iterator(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public Byte[] toArray(){
        int size;
        final long word0,word1,word2,word3;
        final ByteSetImpl root;
        if((size=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3))!=0){      
          final Byte[] dst;
          if((size=wordToArrayDescending(word3,64,128,dst=new Byte[size],size<<11))>=1<<11){
            if((size=wordToArrayDescending(word2,0,64,dst,size))>=1<<11){
              if((size=wordToArrayDescending(word1,-64,0,dst,size))>=1<<11){
                size=finalWordToArrayDescending(word0,-64,dst,size);
              }
            }
          }
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_BOXED_ARR;
      }
      @Override public byte[] toByteArray(){
        int size;
        final long word0,word1,word2,word3;
        final ByteSetImpl root;
        if((size=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3))!=0){      
          final byte[] dst;
          if((size=wordToArrayDescending(word3,64,128,dst=new byte[size],size<<11))>=1<<11){
            if((size=wordToArrayDescending(word2,0,64,dst,size))>=1<<11){
              if((size=wordToArrayDescending(word1,-64,0,dst,size))>=1<<11){
                size=finalWordToArrayDescending(word0,-64,dst,size);
              }
            }
          }
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_ARR;
      }
      @Override public short[] toShortArray(){
        int size;
        final long word0,word1,word2,word3;
        final ByteSetImpl root;
        if((size=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3))!=0){      
          final short[] dst;
          if((size=wordToArrayDescending(word3,64,128,dst=new short[size],size<<11))>=1<<11){
            if((size=wordToArrayDescending(word2,0,64,dst,size))>=1<<11){
              if((size=wordToArrayDescending(word1,-64,0,dst,size))>=1<<11){
                size=finalWordToArrayDescending(word0,-64,dst,size);
              }
            }
          }
          return dst;
        }
        return OmniArray.OfShort.DEFAULT_ARR;
      }
      @Override public int[] toIntArray(){
        int size;
        final long word0,word1,word2,word3;
        final ByteSetImpl root;
        if((size=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3))!=0){      
          final int[] dst;
          if((size=wordToArrayDescending(word3,64,128,dst=new int[size],size<<11))>=1<<11){
            if((size=wordToArrayDescending(word2,0,64,dst,size))>=1<<11){
              if((size=wordToArrayDescending(word1,-64,0,dst,size))>=1<<11){
                size=finalWordToArrayDescending(word0,-64,dst,size);
              }
            }
          }
          return dst;
        }
        return OmniArray.OfInt.DEFAULT_ARR;
      }
      @Override public long[] toLongArray(){
        int size;
        final long word0,word1,word2,word3;
        final ByteSetImpl root;
        if((size=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3))!=0){      
          final long[] dst;
          if((size=wordToArrayDescending(word3,64,128,dst=new long[size],size<<11))>=1<<11){
            if((size=wordToArrayDescending(word2,0,64,dst,size))>=1<<11){
              if((size=wordToArrayDescending(word1,-64,0,dst,size))>=1<<11){
                size=finalWordToArrayDescending(word0,-64,dst,size);
              }
            }
          }
          return dst;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
      }
      @Override public float[] toFloatArray(){
        int size;
        final long word0,word1,word2,word3;
        final ByteSetImpl root;
        if((size=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3))!=0){      
          final float[] dst;
          if((size=wordToArrayDescending(word3,64,128,dst=new float[size],size<<11))>=1<<11){
            if((size=wordToArrayDescending(word2,0,64,dst,size))>=1<<11){
              if((size=wordToArrayDescending(word1,-64,0,dst,size))>=1<<11){
                size=finalWordToArrayDescending(word0,-64,dst,size);
              }
            }
          }
          return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
      }
      @Override public double[] toDoubleArray(){
        int size;
        final long word0,word1,word2,word3;
        final ByteSetImpl root;
        if((size=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3))!=0){      
          final double[] dst;
          if((size=wordToArrayDescending(word3,64,128,dst=new double[size],size<<11))>=1<<11){
            if((size=wordToArrayDescending(word2,0,64,dst,size))>=1<<11){
              if((size=wordToArrayDescending(word1,-64,0,dst,size))>=1<<11){
                size=finalWordToArrayDescending(word0,-64,dst,size);
              }
            }
          }
          return dst;
        }
        return OmniArray.OfDouble.DEFAULT_ARR;
      }
      @Override public <T> T[] toArray(T[] dst){
        int size;
        final long word0,word1,word2,word3;
        final ByteSetImpl root;
        if((size=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3))!=0){      
          if((size=wordToArrayDescending(word3,64,128,dst=OmniArray.uncheckedArrResize(size,dst),size<<11))>=1<<11){
            if((size=wordToArrayDescending(word2,0,64,dst,size))>=1<<11){
              if((size=wordToArrayDescending(word1,-64,0,dst,size))>=1<<11){
                size=finalWordToArrayDescending(word0,-64,dst,size);
              }
            }
          }
        }else if(dst.length!=0){
          dst[0]=null;
        }
        return dst;
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        int size;
        final long word0,word1,word2,word3;
        final ByteSetImpl root;
        final T[] dst=arrConstructor.apply(size=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3));
        if(size!=0){
          if((size=wordToArrayDescending(word3,64,128,dst,size<<11))>=1<<11){
            if((size=wordToArrayDescending(word2,0,64,dst,size))>=1<<11){
              if((size=wordToArrayDescending(word1,-64,0,dst,size))>=1<<11){
                size=finalWordToArrayDescending(word0,-64,dst,size);
              }
            }
          }
        }
        return dst;
      }
    }
  }
  private static class CheckedFullView extends AbstractByteSet implements Cloneable,Serializable{
    private static final long serialVersionUID=1L;
    transient final ByteSetImpl.Checked root;
    private CheckedFullView(ByteSetImpl.Checked root){
      this.root=root;
    }
    @Override public boolean add(boolean val){
      return root.add(val);
    }
    @Override public boolean add(byte val){
      return root.add(val);
    }
    @Override public boolean contains(boolean val){
      return root.contains(val);
    }
    @Override public boolean contains(byte val){
      return root.contains(val);
    }
    @Override public boolean contains(char val){
      return root.contains(val);
    }
    @Override public boolean contains(int val){
      return root.contains(val);
    }
    @Override public boolean contains(long val){
      return root.contains(val);
    }
    @Override public boolean contains(float val){
      return root.contains(val);
    }
    @Override public boolean contains(double val){
      return root.contains(val);
    }
    @Override public boolean contains(Object val){
      return root.contains(val);
    }
    @Override public boolean removeVal(boolean val){
      return root.removeVal(val);
    }
    @Override public boolean removeVal(byte val){
      return root.removeVal(val);
    }
    @Override public boolean removeVal(char val){
      return root.removeVal(val);
    }
    @Override public boolean removeVal(int val){
      return root.removeVal(val);
    }
    @Override public boolean removeVal(long val){
      return root.removeVal(val);
    }
    @Override public boolean removeVal(float val){
      return root.removeVal(val);
    }
    @Override public boolean removeVal(double val){
      return root.removeVal(val);
    }
    @Override public boolean remove(Object val){
      return root.remove(val);
    }
    @Override public int hashCode(){
      return root.hashCode();
    }
    @Override public void clear(){
      final int modCountAndSize;
      final ByteSetImpl.Checked root;
      if(((modCountAndSize=(root=this.root).modCountAndSize)&0x1ff)!=0){  
        root.word0=0;
        root.word1=0;
        root.word2=0;
        root.word3=0;
        root.modCountAndSize=(modCountAndSize+(1<<9))&0xfffffe00;
      }
    }
    @Override public boolean isEmpty(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      //return (root.modCountAndSize&0x1FF)==0;
    }
    @Override public int size(){
      //TODO
      throw new omni.util.NotYetImplementedException();
      //return (root.modCountAndSize&0x1FF);
    }
    @Override public boolean removeIf(BytePredicate filter){
      return root.removeIf((BytePredicate)filter);
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      return root.removeIf((BytePredicate)filter::test);
    }
    @Override public String toString(){
      int size;
      final ByteSetImpl.Checked root;
      if((size=((root=this.root).modCountAndSize&0x1ff))!=0){
        final byte[] buffer;
        (buffer=new byte[size*6])[0]='[';
        if((size=wordToStringAscending(root.word0,Byte.MIN_VALUE,-64,buffer,size<<11))>=1<<11){
          if((size=wordToStringAscending(root.word1,-64,0,buffer,size))>=1<<11){
            if((size=wordToStringAscending(root.word2,0,64,buffer,size))>=1<<11){
              size=finalWordToStringAscending(root.word3,64,buffer,size);
            }
          }
        }
        buffer[++size]=']';
        return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
      }
      return "[]";
    }
    @Override public void forEach(ByteConsumer action){
      final int modCountAndSize;
      final int numLeft;
      final ByteSetImpl.Checked root;
      if((numLeft=(modCountAndSize=(root=this.root).modCountAndSize)&0x1ff)!=0){
        try{
          ((ByteSetImpl)root).forEachAscendingHelper(action,numLeft);
        }finally{
          CheckedCollection.checkModCount(modCountAndSize,root.modCountAndSize);
        }
      }
    }
    @Override public void forEach(Consumer<? super Byte> action){
      final int modCountAndSize;
      final int numLeft;
      final ByteSetImpl.Checked root;
      if((numLeft=(modCountAndSize=(root=this.root).modCountAndSize)&0x1ff)!=0){
        try{
          ((ByteSetImpl)root).forEachAscendingHelper(action::accept,numLeft);
        }finally{
          CheckedCollection.checkModCount(modCountAndSize,root.modCountAndSize);
        }
      }
    }
    @Override public OmniIterator.OfByte iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Byte[] toArray(){
      int size;
      final ByteSetImpl.Checked root;
      if((size=((root=this.root).modCountAndSize&0x1ff))!=0){
        final Byte[] dst;
        if((size=wordToArrayAscending(root.word0,Byte.MIN_VALUE,-64,dst=new Byte[size],size<<11))>=1<<11){
          if((size=wordToArrayAscending(root.word1,-64,0,dst,size))>=1<<11){
            if((size=wordToArrayAscending(root.word2,0,64,dst,size))>=1<<11){
              size=finalWordToArrayAscending(root.word3,64,dst,size);
            }
          }
        }
        return dst;
      }
      return OmniArray.OfByte.DEFAULT_BOXED_ARR;
    }
    @Override public byte[] toByteArray(){
      int size;
      final ByteSetImpl.Checked root;
      if((size=((root=this.root).modCountAndSize&0x1ff))!=0){
        final byte[] dst;
        if((size=wordToArrayAscending(root.word0,Byte.MIN_VALUE,-64,dst=new byte[size],size<<11))>=1<<11){
          if((size=wordToArrayAscending(root.word1,-64,0,dst,size))>=1<<11){
            if((size=wordToArrayAscending(root.word2,0,64,dst,size))>=1<<11){
              size=finalWordToArrayAscending(root.word3,64,dst,size);
            }
          }
        }
        return dst;
      }
      return OmniArray.OfByte.DEFAULT_ARR;
    }
    @Override public short[] toShortArray(){
      int size;
      final ByteSetImpl.Checked root;
      if((size=((root=this.root).modCountAndSize&0x1ff))!=0){
        final short[] dst;
        if((size=wordToArrayAscending(root.word0,Byte.MIN_VALUE,-64,dst=new short[size],size<<11))>=1<<11){
          if((size=wordToArrayAscending(root.word1,-64,0,dst,size))>=1<<11){
            if((size=wordToArrayAscending(root.word2,0,64,dst,size))>=1<<11){
              size=finalWordToArrayAscending(root.word3,64,dst,size);
            }
          }
        }
        return dst;
      }
      return OmniArray.OfShort.DEFAULT_ARR;
    }
    @Override public int[] toIntArray(){
      int size;
      final ByteSetImpl.Checked root;
      if((size=((root=this.root).modCountAndSize&0x1ff))!=0){
        final int[] dst;
        if((size=wordToArrayAscending(root.word0,Byte.MIN_VALUE,-64,dst=new int[size],size<<11))>=1<<11){
          if((size=wordToArrayAscending(root.word1,-64,0,dst,size))>=1<<11){
            if((size=wordToArrayAscending(root.word2,0,64,dst,size))>=1<<11){
              size=finalWordToArrayAscending(root.word3,64,dst,size);
            }
          }
        }
        return dst;
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
    @Override public long[] toLongArray(){
      int size;
      final ByteSetImpl.Checked root;
      if((size=((root=this.root).modCountAndSize&0x1ff))!=0){
        final long[] dst;
        if((size=wordToArrayAscending(root.word0,Byte.MIN_VALUE,-64,dst=new long[size],size<<11))>=1<<11){
          if((size=wordToArrayAscending(root.word1,-64,0,dst,size))>=1<<11){
            if((size=wordToArrayAscending(root.word2,0,64,dst,size))>=1<<11){
              size=finalWordToArrayAscending(root.word3,64,dst,size);
            }
          }
        }
        return dst;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override public float[] toFloatArray(){
      int size;
      final ByteSetImpl.Checked root;
      if((size=((root=this.root).modCountAndSize&0x1ff))!=0){
        final float[] dst;
        if((size=wordToArrayAscending(root.word0,Byte.MIN_VALUE,-64,dst=new float[size],size<<11))>=1<<11){
          if((size=wordToArrayAscending(root.word1,-64,0,dst,size))>=1<<11){
            if((size=wordToArrayAscending(root.word2,0,64,dst,size))>=1<<11){
              size=finalWordToArrayAscending(root.word3,64,dst,size);
            }
          }
        }
        return dst;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override public double[] toDoubleArray(){
      int size;
      final ByteSetImpl.Checked root;
      if((size=((root=this.root).modCountAndSize&0x1ff))!=0){
        final double[] dst;
        if((size=wordToArrayAscending(root.word0,Byte.MIN_VALUE,-64,dst=new double[size],size<<11))>=1<<11){
          if((size=wordToArrayAscending(root.word1,-64,0,dst,size))>=1<<11){
            if((size=wordToArrayAscending(root.word2,0,64,dst,size))>=1<<11){
              size=finalWordToArrayAscending(root.word3,64,dst,size);
            }
          }
        }
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override public <T> T[] toArray(T[] dst){
      int size;
      final ByteSetImpl.Checked root;
      if((size=((root=this.root).modCountAndSize&0x1ff))!=0){
        if((size=wordToArrayAscending(root.word0,Byte.MIN_VALUE,-64,dst=OmniArray.uncheckedArrResize(size,dst),size<<11))>=1<<11){
          if((size=wordToArrayAscending(root.word1,-64,0,dst,size))>=1<<11){
            if((size=wordToArrayAscending(root.word2,0,64,dst,size))>=1<<11){
              size=finalWordToArrayAscending(root.word3,64,dst,size);
            }
          }
        }
      }else if(dst.length!=0){
        dst[0]=null;
      }
      return dst;
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      int size;
      final T[] dst;
      final ByteSetImpl.Checked root;
      final int modCountAndSize=(root=this.root).modCountAndSize;
      try{
        dst=arrConstructor.apply(size=(modCountAndSize&0x1ff));
      }finally{
        CheckedCollection.checkModCount(modCountAndSize,root.modCountAndSize);
      }
      if(size!=0){
        if((size=wordToArrayAscending(root.word0,Byte.MIN_VALUE,-64,dst,size<<11))>=1<<11){
          if((size=wordToArrayAscending(root.word1,-64,0,dst,size))>=1<<11){
            if((size=wordToArrayAscending(root.word2,0,64,dst,size))>=1<<11){
              size=finalWordToArrayAscending(root.word3,64,dst,size);
            }
          }
        }
      }
      return dst;
    }
    private Object writeReplace(){
      return new ByteSetImpl.Checked(root);
    }
    private static class Descending extends CheckedFullView{
      private Descending(final ByteSetImpl.Checked root){
        super(root);
      }
      private Object writeReplace(){
        return new ByteSetImpl.Checked.Descending(root);
      }
      @Override public String toString(){
        int size;
        final ByteSetImpl.Checked root;
        if((size=((root=this.root).modCountAndSize&0x1ff))!=0){
          final byte[] buffer;
          (buffer=new byte[size*6])[0]='[';
          if((size=wordToStringDescending(root.word3,64,128,buffer,size<<11))>=1<<11){
            if((size=wordToStringDescending(root.word2,0,64,buffer,size))>=1<<11){
              if((size=wordToStringDescending(root.word1,-64,0,buffer,size))>=1<<11){
                size=finalWordToStringDescending(root.word0,-64,buffer,size);
              }
            }
          }
          buffer[++size]=']';
          return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
        }
        return "[]";
      }
      @Override public void forEach(ByteConsumer action){
        final int modCountAndSize;
        final int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(modCountAndSize=(root=this.root).modCountAndSize)&0x1ff)!=0){
          try{
            ((ByteSetImpl)root).forEachDescendingHelper(action,numLeft);
          }finally{
            CheckedCollection.checkModCount(modCountAndSize,root.modCountAndSize);
          }
        }
      }
      @Override public void forEach(Consumer<? super Byte> action){
        final int modCountAndSize;
        final int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(modCountAndSize=(root=this.root).modCountAndSize)&0x1ff)!=0){
          try{
            ((ByteSetImpl)root).forEachDescendingHelper(action::accept,numLeft);
          }finally{
            CheckedCollection.checkModCount(modCountAndSize,root.modCountAndSize);
          }
        }
      }
      @Override public OmniIterator.OfByte iterator(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public Byte[] toArray(){
        int size;
        final ByteSetImpl.Checked root;
        if((size=((root=this.root).modCountAndSize&0x1ff))!=0){
          final Byte[] dst;
          if((size=wordToArrayDescending(root.word3,64,128,dst=new Byte[size],size<<11))>=1<<11){
            if((size=wordToArrayDescending(root.word2,0,64,dst,size))>=1<<11){
              if((size=wordToArrayDescending(root.word1,-64,0,dst,size))>=1<<11){
                size=finalWordToArrayDescending(root.word0,-64,dst,size);
              }
            }
          }
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_BOXED_ARR;
      }
      @Override public byte[] toByteArray(){
        int size;
        final ByteSetImpl.Checked root;
        if((size=((root=this.root).modCountAndSize&0x1ff))!=0){
          final byte[] dst;
          if((size=wordToArrayDescending(root.word3,64,128,dst=new byte[size],size<<11))>=1<<11){
            if((size=wordToArrayDescending(root.word2,0,64,dst,size))>=1<<11){
              if((size=wordToArrayDescending(root.word1,-64,0,dst,size))>=1<<11){
                size=finalWordToArrayDescending(root.word0,-64,dst,size);
              }
            }
          }
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_ARR;
      }
      @Override public short[] toShortArray(){
        int size;
        final ByteSetImpl.Checked root;
        if((size=((root=this.root).modCountAndSize&0x1ff))!=0){
          final short[] dst;
          if((size=wordToArrayDescending(root.word3,64,128,dst=new short[size],size<<11))>=1<<11){
            if((size=wordToArrayDescending(root.word2,0,64,dst,size))>=1<<11){
              if((size=wordToArrayDescending(root.word1,-64,0,dst,size))>=1<<11){
                size=finalWordToArrayDescending(root.word0,-64,dst,size);
              }
            }
          }
          return dst;
        }
        return OmniArray.OfShort.DEFAULT_ARR;
      }
      @Override public int[] toIntArray(){
        int size;
        final ByteSetImpl.Checked root;
        if((size=((root=this.root).modCountAndSize&0x1ff))!=0){
          final int[] dst;
          if((size=wordToArrayDescending(root.word3,64,128,dst=new int[size],size<<11))>=1<<11){
            if((size=wordToArrayDescending(root.word2,0,64,dst,size))>=1<<11){
              if((size=wordToArrayDescending(root.word1,-64,0,dst,size))>=1<<11){
                size=finalWordToArrayDescending(root.word0,-64,dst,size);
              }
            }
          }
          return dst;
        }
        return OmniArray.OfInt.DEFAULT_ARR;
      }
      @Override public long[] toLongArray(){
        int size;
        final ByteSetImpl.Checked root;
        if((size=((root=this.root).modCountAndSize&0x1ff))!=0){
          final long[] dst;
          if((size=wordToArrayDescending(root.word3,64,128,dst=new long[size],size<<11))>=1<<11){
            if((size=wordToArrayDescending(root.word2,0,64,dst,size))>=1<<11){
              if((size=wordToArrayDescending(root.word1,-64,0,dst,size))>=1<<11){
                size=finalWordToArrayDescending(root.word0,-64,dst,size);
              }
            }
          }
          return dst;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
      }
      @Override public float[] toFloatArray(){
        int size;
        final ByteSetImpl.Checked root;
        if((size=((root=this.root).modCountAndSize&0x1ff))!=0){
          final float[] dst;
          if((size=wordToArrayDescending(root.word3,64,128,dst=new float[size],size<<11))>=1<<11){
            if((size=wordToArrayDescending(root.word2,0,64,dst,size))>=1<<11){
              if((size=wordToArrayDescending(root.word1,-64,0,dst,size))>=1<<11){
                size=finalWordToArrayDescending(root.word0,-64,dst,size);
              }
            }
          }
          return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
      }
      @Override public double[] toDoubleArray(){
        int size;
        final ByteSetImpl.Checked root;
        if((size=((root=this.root).modCountAndSize&0x1ff))!=0){
          final double[] dst;
          if((size=wordToArrayDescending(root.word3,64,128,dst=new double[size],size<<11))>=1<<11){
            if((size=wordToArrayDescending(root.word2,0,64,dst,size))>=1<<11){
              if((size=wordToArrayDescending(root.word1,-64,0,dst,size))>=1<<11){
                size=finalWordToArrayDescending(root.word0,-64,dst,size);
              }
            }
          }
          return dst;
        }
        return OmniArray.OfDouble.DEFAULT_ARR;
      }
      @Override public <T> T[] toArray(T[] dst){
        int size;
        final ByteSetImpl.Checked root;
        if((size=((root=this.root).modCountAndSize&0x1ff))!=0){
          if((size=wordToArrayDescending(root.word3,64,128,dst=OmniArray.uncheckedArrResize(size,dst),size<<11))>=1<<11){
            if((size=wordToArrayDescending(root.word2,0,64,dst,size))>=1<<11){
              if((size=wordToArrayDescending(root.word1,-64,0,dst,size))>=1<<11){
                size=finalWordToArrayDescending(root.word0,-64,dst,size);
              }
            }
          }
        }else if(dst.length!=0){
          dst[0]=null;
        }
        return dst;
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        int size;
        final T[] dst;
        final ByteSetImpl.Checked root;
        final int modCountAndSize=(root=this.root).modCountAndSize;
        try{
          dst=arrConstructor.apply(size=(modCountAndSize&0x1ff));
        }finally{
          CheckedCollection.checkModCount(modCountAndSize,root.modCountAndSize);
        }
        if(size!=0){
          if((size=wordToArrayDescending(root.word3,64,128,dst,size<<11))>=1<<11){
            if((size=wordToArrayDescending(root.word2,0,64,dst,size))>=1<<11){
              if((size=wordToArrayDescending(root.word1,-64,0,dst,size))>=1<<11){
                size=finalWordToArrayDescending(root.word0,-64,dst,size);
              }
            }
          }
        }
        return dst;
      }
    }
  }
  private abstract static class AbstractUncheckedSubSet extends AbstractByteSet{
    transient final ByteSetImpl root;
    transient final AbstractUncheckedSubSet parent;
    transient final int boundInfo;
    transient int size;
    private AbstractUncheckedSubSet(ByteSetImpl root,int boundInfo,int size){
      this.root=root;
      this.parent=null;
      this.boundInfo=boundInfo;
      this.size=size;
    }
    private AbstractUncheckedSubSet(AbstractUncheckedSubSet parent,int boundInfo,int size){
      this.root=parent.root;
      this.parent=parent;
      this.boundInfo=boundInfo;
      this.size=size;
    }
  }
  private abstract static class AbstractCheckedSubSet extends AbstractByteSet{
    transient final ByteSetImpl.Checked root;
    transient final AbstractCheckedSubSet parent;
    transient final int boundInfo;
    transient int modCountAndSize;
    private AbstractCheckedSubSet(final ByteSetImpl.Checked root,int boundInfo,int modCountAndSize){
      this.root=root;
      this.parent=null;
      this.boundInfo=boundInfo;
      this.modCountAndSize=modCountAndSize;
    }
    private AbstractCheckedSubSet(final AbstractCheckedSubSet parent,int boundInfo,int modCountAndSize){
      this.root=parent.root;
      this.parent=parent;
      this.boundInfo=boundInfo;
      this.modCountAndSize=modCountAndSize;
    }
  }
  private static class UncheckedHeadView extends AbstractUncheckedSubSet{
    private UncheckedHeadView(ByteSetImpl root,int boundInfo,int size){
      super(root,boundInfo,size);
    }
    private UncheckedHeadView(AbstractUncheckedSubSet parent,int boundInfo,int size){
      super(parent,boundInfo,size);
    }
    @Override public boolean add(boolean val){
      return root.add(val);
    }
    @Override public boolean add(byte val){
      return root.add(val);
    }
    @Override public boolean contains(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(Object val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean remove(Object val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int hashCode(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void clear(){
      final int size;
      if((size=this.size)!=0){
        this.size=0;
        for(var parent=this.parent;parent!=null;parent=parent.parent){
          parent.size-=size;
        }
        final ByteSetImpl root=this.root;
        goToEnd:for(;;){
          goToWord0:for(;;){
            goToWord1:for(;;){
              final int boundInfo;
              switch((boundInfo=this.boundInfo)>>6){
              case -2:
                root.word0&=(-1L<<boundInfo);
                break goToEnd;
              case -1:
                root.word1&=(-1L<<boundInfo);
                break goToWord0;
              case 0:
                root.word2&=(-1L<<boundInfo);
                break goToWord1;
              default:
                root.word3&=(-1L<<boundInfo);
              }
              root.word2=0;
              break;
            }
            root.word1=0;
            break;
          }
          root.word0=0;
          break;
        }
      }
    }
    @Override public boolean isEmpty(){
      return this.size==0;
    }
    @Override public int size(){
      return this.size;
    }
    @Override public boolean removeIf(BytePredicate filter){
      final int size;
      if((size=this.size)!=0){
        final int numRemoved;
        if((numRemoved=root.removeIfHelper(filter,this.boundInfo))!=0){
          this.size=size-numRemoved;
          for(var parent=this.parent;parent!=null;parent=parent.parent){
            parent.size-=numRemoved;
          }
          return true;
        }
      }
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      final int size;
      if((size=this.size)!=0){
        final int numRemoved;
        if((numRemoved=root.removeIfHelper(filter::test,this.boundInfo))!=0){
          this.size=size-numRemoved;
          for(var parent=this.parent;parent!=null;parent=parent.parent){
            parent.size-=numRemoved;
          }
          return true;
        }
      }
      return false;
    }
    @Override public String toString(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void forEach(ByteConsumer action){
      final int size;
      if((size=this.size)!=0){
        root.forEachAscendingHelper(action,size);
      }
    }
    @Override public void forEach(Consumer<? super Byte> action){
      final int size;
      if((size=this.size)!=0){
        root.forEachAscendingHelper(action::accept,size);
      }
    }
    @Override public OmniIterator.OfByte iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Byte[] toArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte[] toByteArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short[] toShortArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int[] toIntArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long[] toLongArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float[] toFloatArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double[] toDoubleArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public <T> T[] toArray(T[] dst){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    private static class Descending extends UncheckedHeadView{
      private Descending(ByteSetImpl root,int boundInfo,int size){
        super(root,boundInfo,size);
      }
      private Descending(AbstractUncheckedSubSet parent,int boundInfo,int size){
        super(parent,boundInfo,size);
      }
      @Override public String toString(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public void forEach(ByteConsumer action){
        final int size;
        if((size=this.size)!=0){
          root.forEachDescendingHelper(action,size,this.boundInfo);
        }
      }
      @Override public void forEach(Consumer<? super Byte> action){
        final int size;
        if((size=this.size)!=0){
          root.forEachDescendingHelper(action::accept,size,this.boundInfo);
        }
      }
      @Override public OmniIterator.OfByte iterator(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public Byte[] toArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public byte[] toByteArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public short[] toShortArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public int[] toIntArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public long[] toLongArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public float[] toFloatArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public double[] toDoubleArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public <T> T[] toArray(T[] dst){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
    }
  }
  private static class CheckedHeadView extends AbstractCheckedSubSet{
    private CheckedHeadView(final ByteSetImpl.Checked root,int boundInfo,int modCountAndSize){
      super(root,boundInfo,modCountAndSize);
    }
    private CheckedHeadView(final AbstractCheckedSubSet parent,int boundInfo,int modCountAndSize){
      super(parent,boundInfo,modCountAndSize);
    }
    @Override public boolean add(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean add(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(byte val){
      if(val<this.boundInfo){
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount(modCountAndSize>>>9,(root=this.root).modCountAndSize>>>9);
        return root.contains(val);
      }
      return false;
    }
    @Override public boolean contains(char val){
      if(val<this.boundInfo){
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount(modCountAndSize>>>9,(root=this.root).modCountAndSize>>>9);
        return root.contains(val);
      }
      return false;
    }
    @Override public boolean contains(int val){
      if(val<this.boundInfo){
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount(modCountAndSize>>>9,(root=this.root).modCountAndSize>>>9);
        return root.contains(val);
      }
      return false;
    }
    @Override public boolean contains(long val){
      if(val<this.boundInfo){
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount(modCountAndSize>>>9,(root=this.root).modCountAndSize>>>9);
        return root.contains(val);
      }
      return false;
    }
    @Override public boolean contains(float val){
      if(val<this.boundInfo){
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount(modCountAndSize>>>9,(root=this.root).modCountAndSize>>>9);
        return root.contains(val);
      }
      return false;
    }
    @Override public boolean contains(double val){
      if(val<this.boundInfo){
        final ByteSetImpl.Checked root;
        CheckedCollection.checkModCount(modCountAndSize>>>9,(root=this.root).modCountAndSize>>>9);
        return root.contains(val);
      }
      return false;
    }
    @Override public boolean contains(Object val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean remove(Object val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int hashCode(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void clear(){
      final ByteSetImpl.Checked root;
      int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)&0xfffffe00,((root=this.root).modCountAndSize)&0xfffffe00);
      if(((modCountAndSize&=0x1ff))!=0){
        root.modCountAndSize+=(modCountAndSize=(1<<9)-modCountAndSize);
        AbstractCheckedSubSet curr=this;
        do{
          curr.modCountAndSize+=modCountAndSize;
        }while((curr=curr.parent)!=null);
        goToEnd:for(;;){
          goToWord0:for(;;){
            goToWord1:for(;;){
              final int boundInfo;
              switch((boundInfo=this.boundInfo)>>6){
              case -2:
                root.word0&=(-1L<<boundInfo);
                break goToEnd;
              case -1:
                root.word1&=(-1L<<boundInfo);
                break goToWord0;
              case 0:
                root.word2&=(-1L<<boundInfo);
                break goToWord1;
              default:
                root.word3&=(-1L<<boundInfo);
              }
              root.word2=0;
              break;
            }
            root.word1=0;
            break;
          }
          root.word0=0;
          break;
        }
      }
    }
    @Override public boolean isEmpty(){
      final int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,root.modCountAndSize>>>9);
      return (modCountAndSize&0x1FF)==0;
    }
    @Override public int size(){
      final int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,root.modCountAndSize>>>9);
      return (modCountAndSize&0x1FF);
    }
    @Override public boolean removeIf(BytePredicate filter){
      final int modCountAndSize;
      final ByteSetImpl.Checked root=this.root;
      if((modCountAndSize=this.modCountAndSize&0x1ff)!=0){
        int numRemoved;
        if((numRemoved=root.removeIfHelper(filter,this.boundInfo,new CheckedCollection.AbstractModCountChecker(modCountAndSize>>>9){
          @Override protected int getActualModCount(){
            return root.modCountAndSize>>>9;
          }
        }))!=0){
          root.modCountAndSize+=(numRemoved=(1<<9)-numRemoved);
          AbstractCheckedSubSet curr=this;
          do{
            curr.modCountAndSize+=numRemoved;
          }while((curr=curr.parent)!=null);
          return true;
        }
        return false;
      }
      CheckedCollection.checkModCount(modCountAndSize>>>9,root.modCountAndSize>>>9);
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      final int modCountAndSize;
      final ByteSetImpl.Checked root=this.root;
      if((modCountAndSize=this.modCountAndSize&0x1ff)!=0){
        int numRemoved;
        if((numRemoved=root.removeIfHelper(filter::test,this.boundInfo,new CheckedCollection.AbstractModCountChecker(modCountAndSize>>>9){
          @Override protected int getActualModCount(){
            return root.modCountAndSize>>>9;
          }
        }))!=0){
          root.modCountAndSize+=(numRemoved=(1<<9)-numRemoved);
          AbstractCheckedSubSet curr=this;
          do{
            curr.modCountAndSize+=numRemoved;
          }while((curr=curr.parent)!=null);
          return true;
        }
        return false;
      }
      CheckedCollection.checkModCount(modCountAndSize>>>9,root.modCountAndSize>>>9);
      return false;
    }
    @Override public String toString(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void forEach(ByteConsumer action){
      final ByteSetImpl.Checked root;
      final int modCountAndSize=(root=this.root).modCountAndSize;
      try{
        final int numLeft;
        if((numLeft=modCountAndSize&0x1ff)!=0){
          ((ByteSetImpl)root).forEachAscendingHelper(action,numLeft);
        }
      }finally{
        CheckedCollection.checkModCount(modCountAndSize,root.modCountAndSize);
      }
    }
    @Override public void forEach(Consumer<? super Byte> action){
      final ByteSetImpl.Checked root;
      final int modCountAndSize=(root=this.root).modCountAndSize;
      try{
        final int numLeft;
        if((numLeft=modCountAndSize&0x1ff)!=0){
          ((ByteSetImpl)root).forEachAscendingHelper(action::accept,numLeft);
        }
      }finally{
        CheckedCollection.checkModCount(modCountAndSize,root.modCountAndSize);
      }
    }
    @Override public OmniIterator.OfByte iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Byte[] toArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte[] toByteArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short[] toShortArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int[] toIntArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long[] toLongArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float[] toFloatArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double[] toDoubleArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public <T> T[] toArray(T[] dst){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    private static class Descending extends CheckedHeadView{
      private Descending(final ByteSetImpl.Checked root,int boundInfo,int modCountAndSize){
        super(root,boundInfo,modCountAndSize);
      }
      private Descending(final AbstractCheckedSubSet parent,int boundInfo,int modCountAndSize){
        super(parent,boundInfo,modCountAndSize);
      }
      @Override public String toString(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public void forEach(ByteConsumer action){
        final ByteSetImpl.Checked root;
        final int modCountAndSize=(root=this.root).modCountAndSize;
        try{
          final int numLeft;
          if((numLeft=modCountAndSize&0x1ff)!=0){
            ((ByteSetImpl)root).forEachDescendingHelper(action,numLeft,this.boundInfo);
          }
        }finally{
          CheckedCollection.checkModCount(modCountAndSize,root.modCountAndSize);
        }
      }
      @Override public void forEach(Consumer<? super Byte> action){
        final ByteSetImpl.Checked root;
        final int modCountAndSize=(root=this.root).modCountAndSize;
        try{
          final int numLeft;
          if((numLeft=modCountAndSize&0x1ff)!=0){
            ((ByteSetImpl)root).forEachDescendingHelper(action::accept,numLeft,this.boundInfo);
          }
        }finally{
          CheckedCollection.checkModCount(modCountAndSize,root.modCountAndSize);
        }
      }
      @Override public OmniIterator.OfByte iterator(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public Byte[] toArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public byte[] toByteArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public short[] toShortArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public int[] toIntArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public long[] toLongArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public float[] toFloatArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public double[] toDoubleArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public <T> T[] toArray(T[] dst){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
    }
  }
  private static class UncheckedTailView extends AbstractUncheckedSubSet{
    private UncheckedTailView(ByteSetImpl root,int boundInfo,int size){
      super(root,boundInfo,size);
    }
    private UncheckedTailView(AbstractUncheckedSubSet parent,int boundInfo,int size){
      super(parent,boundInfo,size);
    }
    @Override public boolean add(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean add(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(Object val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean remove(Object val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int hashCode(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void clear(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean isEmpty(){
      return this.size==0;
    }
    @Override public int size(){
      return this.size;
    }
    @Override public boolean removeIf(BytePredicate filter){
      final int size;
      if((size=this.size)!=0){
        final int numRemoved;
        if((numRemoved=root.removeIfHelper(this.boundInfo,filter))!=0){
          this.size=size-numRemoved;
          for(var parent=this.parent;parent!=null;parent=parent.parent){
            parent.size-=numRemoved;
          }
          return true;
        }
      }
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      final int size;
      if((size=this.size)!=0){
        final int numRemoved;
        if((numRemoved=root.removeIfHelper(this.boundInfo,filter::test))!=0){
          this.size=size-numRemoved;
          for(var parent=this.parent;parent!=null;parent=parent.parent){
            parent.size-=numRemoved;
          }
          return true;
        }
      }
      return false;
    }
    @Override public String toString(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void forEach(ByteConsumer action){
      final int size;
      if((size=this.size)!=0){
        root.forEachAscendingHelper(this.boundInfo,action,size);
      }
    }
    @Override public void forEach(Consumer<? super Byte> action){
      final int size;
      if((size=this.size)!=0){
        root.forEachAscendingHelper(this.boundInfo,action::accept,size);
      }
    }
    @Override public OmniIterator.OfByte iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Byte[] toArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte[] toByteArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short[] toShortArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int[] toIntArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long[] toLongArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float[] toFloatArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double[] toDoubleArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public <T> T[] toArray(T[] dst){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    private static class Descending extends UncheckedTailView{
      private Descending(ByteSetImpl root,int boundInfo,int size){
        super(root,boundInfo,size);
      }
      private Descending(AbstractUncheckedSubSet parent,int boundInfo,int size){
        super(parent,boundInfo,size);
      }
      @Override public String toString(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public void forEach(ByteConsumer action){
        final int size;
        if((size=this.size)!=0){
          root.forEachDescendingHelper(action,size);
        }
      }
      @Override public void forEach(Consumer<? super Byte> action){
        final int size;
        if((size=this.size)!=0){
          root.forEachDescendingHelper(action::accept,size);
        }
      }
      @Override public OmniIterator.OfByte iterator(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public Byte[] toArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public byte[] toByteArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public short[] toShortArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public int[] toIntArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public long[] toLongArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public float[] toFloatArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public double[] toDoubleArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public <T> T[] toArray(T[] dst){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
    }
  }
  private static class CheckedTailView extends AbstractCheckedSubSet{
    private CheckedTailView(final ByteSetImpl.Checked root,int boundInfo,int modCountAndSize){
      super(root,boundInfo,modCountAndSize);
    }
    private CheckedTailView(final AbstractCheckedSubSet parent,int boundInfo,int modCountAndSize){
      super(parent,boundInfo,modCountAndSize);
    }
    @Override public boolean add(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean add(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(Object val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean remove(Object val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int hashCode(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void clear(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean isEmpty(){
      final int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,root.modCountAndSize>>>9);
      return (modCountAndSize&0x1FF)==0;
    }
    @Override public int size(){
      final int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,root.modCountAndSize>>>9);
      return (modCountAndSize&0x1FF);
    }
    @Override public boolean removeIf(BytePredicate filter){
      final int modCountAndSize;
      final ByteSetImpl.Checked root=this.root;
      if((modCountAndSize=this.modCountAndSize&0x1ff)!=0){
        int numRemoved;
        if((numRemoved=root.removeIfHelper(this.boundInfo,filter,new CheckedCollection.AbstractModCountChecker(modCountAndSize>>>9){
          @Override protected int getActualModCount(){
            return root.modCountAndSize>>>9;
          }
        }))!=0){
          root.modCountAndSize+=(numRemoved=(1<<9)-numRemoved);
          AbstractCheckedSubSet curr=this;
          do{
            curr.modCountAndSize+=numRemoved;
          }while((curr=curr.parent)!=null);
          return true;
        }
        return false;
      }
      CheckedCollection.checkModCount(modCountAndSize>>>9,root.modCountAndSize>>>9);
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      final int modCountAndSize;
      final ByteSetImpl.Checked root=this.root;
      if((modCountAndSize=this.modCountAndSize&0x1ff)!=0){
        int numRemoved;
        if((numRemoved=root.removeIfHelper(this.boundInfo,filter::test,new CheckedCollection.AbstractModCountChecker(modCountAndSize>>>9){
          @Override protected int getActualModCount(){
            return root.modCountAndSize>>>9;
          }
        }))!=0){
          root.modCountAndSize+=(numRemoved=(1<<9)-numRemoved);
          AbstractCheckedSubSet curr=this;
          do{
            curr.modCountAndSize+=numRemoved;
          }while((curr=curr.parent)!=null);
          return true;
        }
        return false;
      }
      CheckedCollection.checkModCount(modCountAndSize>>>9,root.modCountAndSize>>>9);
      return false;
    }
    @Override public String toString(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void forEach(ByteConsumer action){
      final ByteSetImpl.Checked root;
      final int modCountAndSize=(root=this.root).modCountAndSize;
      try{
        final int numLeft;
        if((numLeft=modCountAndSize&0x1ff)!=0){
          ((ByteSetImpl)root).forEachAscendingHelper(this.boundInfo,action,numLeft);
        }
      }finally{
        CheckedCollection.checkModCount(modCountAndSize,root.modCountAndSize);
      }
    }
    @Override public void forEach(Consumer<? super Byte> action){
      final ByteSetImpl.Checked root;
      final int modCountAndSize=(root=this.root).modCountAndSize;
      try{
        final int numLeft;
        if((numLeft=modCountAndSize&0x1ff)!=0){
          ((ByteSetImpl)root).forEachAscendingHelper(this.boundInfo,action::accept,numLeft);
        }
      }finally{
        CheckedCollection.checkModCount(modCountAndSize,root.modCountAndSize);
      }
    }
    @Override public OmniIterator.OfByte iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Byte[] toArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte[] toByteArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short[] toShortArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int[] toIntArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long[] toLongArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float[] toFloatArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double[] toDoubleArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public <T> T[] toArray(T[] dst){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    private static class Descending extends CheckedTailView{
      private Descending(final ByteSetImpl.Checked root,int boundInfo,int modCountAndSize){
        super(root,boundInfo,modCountAndSize);
      }
      private Descending(final AbstractCheckedSubSet parent,int boundInfo,int modCountAndSize){
        super(parent,boundInfo,modCountAndSize);
      }
      @Override public String toString(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public void forEach(ByteConsumer action){
        final ByteSetImpl.Checked root;
        final int modCountAndSize=(root=this.root).modCountAndSize;
        try{
          final int numLeft;
          if((numLeft=modCountAndSize&0x1ff)!=0){
            ((ByteSetImpl)root).forEachDescendingHelper(action,numLeft);
          }
        }finally{
          CheckedCollection.checkModCount(modCountAndSize,root.modCountAndSize);
        }
      }
      @Override public void forEach(Consumer<? super Byte> action){
        final ByteSetImpl.Checked root;
        final int modCountAndSize=(root=this.root).modCountAndSize;
        try{
          final int numLeft;
          if((numLeft=modCountAndSize&0x1ff)!=0){
            ((ByteSetImpl)root).forEachDescendingHelper(action::accept,numLeft);
          }
        }finally{
          CheckedCollection.checkModCount(modCountAndSize,root.modCountAndSize);
        }
      }
      @Override public OmniIterator.OfByte iterator(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public Byte[] toArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public byte[] toByteArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public short[] toShortArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public int[] toIntArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public long[] toLongArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public float[] toFloatArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public double[] toDoubleArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public <T> T[] toArray(T[] dst){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
    }
  }
  private static class UncheckedBodyView extends AbstractUncheckedSubSet{
    private UncheckedBodyView(ByteSetImpl root,int boundInfo,int size){
      super(root,boundInfo,size);
    }
    private UncheckedBodyView(AbstractUncheckedSubSet parent,int boundInfo,int size){
      super(parent,boundInfo,size);
    }
    @Override public boolean add(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean add(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(Object val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean remove(Object val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int hashCode(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void clear(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean isEmpty(){
      return this.size==0;
    }
    @Override public int size(){
      return this.size;
    }
    @Override public boolean removeIf(BytePredicate filter){
      final int size;
      if((size=this.size)!=0){
        int numRemoved;
        if((numRemoved=root.removeIfHelper((byte)(0xff&(numRemoved=this.boundInfo)),filter,(numRemoved>>8)))!=0){
          this.size=size-numRemoved;
          for(var parent=this.parent;parent!=null;parent=parent.parent){
            parent.size-=numRemoved;
          }
          return true;
        }
      }
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      final int size;
      if((size=this.size)!=0){
        int numRemoved;
        if((numRemoved=root.removeIfHelper((byte)(0xff&(numRemoved=this.boundInfo)),filter::test,(numRemoved>>8)))!=0){
          this.size=size-numRemoved;
          for(var parent=this.parent;parent!=null;parent=parent.parent){
            parent.size-=numRemoved;
          }
          return true;
        }
      }
      return false;
    }
    @Override public String toString(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void forEach(ByteConsumer action){
      final int size;
      if((size=this.size)!=0){
          root.forEachAscendingHelper((byte)(0xff&this.boundInfo),action,size);
      }
    }
    @Override public void forEach(Consumer<? super Byte> action){
      final int size;
      if((size=this.size)!=0){
          root.forEachAscendingHelper((byte)(0xff&this.boundInfo),action::accept,size);
      }
    }
    @Override public OmniIterator.OfByte iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Byte[] toArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte[] toByteArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short[] toShortArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int[] toIntArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long[] toLongArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float[] toFloatArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double[] toDoubleArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public <T> T[] toArray(T[] dst){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    private static class Descending extends UncheckedBodyView{
      private Descending(ByteSetImpl root,int boundInfo,int size){
        super(root,boundInfo,size);
      }
      private Descending(AbstractUncheckedSubSet parent,int boundInfo,int size){
        super(parent,boundInfo,size);
      }
      @Override public String toString(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public void forEach(ByteConsumer action){
        final int size;
        if((size=this.size)!=0){
            root.forEachDescendingHelper(action,size,this.boundInfo>>8);
        }
      }
      @Override public void forEach(Consumer<? super Byte> action){
        final int size;
        if((size=this.size)!=0){
            root.forEachDescendingHelper(action::accept,size,this.boundInfo>>8);
        }
      }
      @Override public OmniIterator.OfByte iterator(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public Byte[] toArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public byte[] toByteArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public short[] toShortArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public int[] toIntArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public long[] toLongArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public float[] toFloatArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public double[] toDoubleArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public <T> T[] toArray(T[] dst){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
    }
  }
  private static class CheckedBodyView extends AbstractCheckedSubSet{
    private CheckedBodyView(final ByteSetImpl.Checked root,int boundInfo,int modCountAndSize){
      super(root,boundInfo,modCountAndSize);
    }
    private CheckedBodyView(final AbstractCheckedSubSet parent,int boundInfo,int modCountAndSize){
      super(parent,boundInfo,modCountAndSize);
    }
    @Override public boolean add(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean add(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean contains(Object val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(boolean val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(byte val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(char val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(int val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(long val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(float val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean removeVal(double val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean remove(Object val){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int hashCode(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void clear(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public boolean isEmpty(){
      final int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,root.modCountAndSize>>>9);
      return (modCountAndSize&0x1FF)==0;
    }
    @Override public int size(){
      final int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,root.modCountAndSize>>>9);
      return (modCountAndSize&0x1FF);
    }
    @Override public boolean removeIf(BytePredicate filter){
      final int modCountAndSize;
      final ByteSetImpl.Checked root=this.root;
      if((modCountAndSize=this.modCountAndSize&0x1ff)!=0){
        int numRemoved;
        if((numRemoved=root.removeIfHelper((byte)(0xff&(numRemoved=this.boundInfo)),filter,(numRemoved>>8),new CheckedCollection.AbstractModCountChecker(modCountAndSize>>>9){
          @Override protected int getActualModCount(){
            return root.modCountAndSize>>>9;
          }
        }))!=0){
          root.modCountAndSize+=(numRemoved=(1<<9)-numRemoved);
          AbstractCheckedSubSet curr=this;
          do{
            curr.modCountAndSize+=numRemoved;
          }while((curr=curr.parent)!=null);
          return true;
        }
        return false;
      }
      CheckedCollection.checkModCount(modCountAndSize>>>9,root.modCountAndSize>>>9);
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      final int modCountAndSize;
      final ByteSetImpl.Checked root=this.root;
      if((modCountAndSize=this.modCountAndSize&0x1ff)!=0){
        int numRemoved;
        if((numRemoved=root.removeIfHelper((byte)(0xff&(numRemoved=this.boundInfo)),filter::test,(numRemoved>>8),new CheckedCollection.AbstractModCountChecker(modCountAndSize>>>9){
          @Override protected int getActualModCount(){
            return root.modCountAndSize>>>9;
          }
        }))!=0){
          root.modCountAndSize+=(numRemoved=(1<<9)-numRemoved);
          AbstractCheckedSubSet curr=this;
          do{
            curr.modCountAndSize+=numRemoved;
          }while((curr=curr.parent)!=null);
          return true;
        }
        return false;
      }
      CheckedCollection.checkModCount(modCountAndSize>>>9,root.modCountAndSize>>>9);
      return false;
    }
    @Override public String toString(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public void forEach(ByteConsumer action){
      final ByteSetImpl.Checked root;
      final int modCountAndSize=(root=this.root).modCountAndSize;
      try{
        final int numLeft;
        if((numLeft=modCountAndSize&0x1ff)!=0){
          ((ByteSetImpl)root).forEachAscendingHelper((byte)(0xff&this.boundInfo),action,numLeft);
        }
      }finally{
        CheckedCollection.checkModCount(modCountAndSize,root.modCountAndSize);
      }
    }
    @Override public void forEach(Consumer<? super Byte> action){
      final ByteSetImpl.Checked root;
      final int modCountAndSize=(root=this.root).modCountAndSize;
      try{
        final int numLeft;
        if((numLeft=modCountAndSize&0x1ff)!=0){
          ((ByteSetImpl)root).forEachAscendingHelper((byte)(0xff&this.boundInfo),action::accept,numLeft);
        }
      }finally{
        CheckedCollection.checkModCount(modCountAndSize,root.modCountAndSize);
      }
    }
    @Override public OmniIterator.OfByte iterator(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public Byte[] toArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public byte[] toByteArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public short[] toShortArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public int[] toIntArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public long[] toLongArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public float[] toFloatArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public double[] toDoubleArray(){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public <T> T[] toArray(T[] dst){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      //TODO
      throw new omni.util.NotYetImplementedException();
    }
    private static class Descending extends CheckedBodyView{
      private Descending(final ByteSetImpl.Checked root,int boundInfo,int modCountAndSize){
        super(root,boundInfo,modCountAndSize);
      }
      private Descending(final AbstractCheckedSubSet parent,int boundInfo,int modCountAndSize){
        super(parent,boundInfo,modCountAndSize);
      }
      @Override public String toString(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public void forEach(ByteConsumer action){
        final ByteSetImpl.Checked root;
        final int modCountAndSize=(root=this.root).modCountAndSize;
        try{
          final int numLeft;
          if((numLeft=modCountAndSize&0x1ff)!=0){
            ((ByteSetImpl)root).forEachDescendingHelper(action,numLeft,this.boundInfo>>8);
          }
        }finally{
          CheckedCollection.checkModCount(modCountAndSize,root.modCountAndSize);
        }
      }
      @Override public void forEach(Consumer<? super Byte> action){
        final ByteSetImpl.Checked root;
        final int modCountAndSize=(root=this.root).modCountAndSize;
        try{
          final int numLeft;
          if((numLeft=modCountAndSize&0x1ff)!=0){
            ((ByteSetImpl)root).forEachDescendingHelper(action::accept,numLeft,this.boundInfo>>8);
          }
        }finally{
          CheckedCollection.checkModCount(modCountAndSize,root.modCountAndSize);
        }
      }
      @Override public OmniIterator.OfByte iterator(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public Byte[] toArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public byte[] toByteArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public short[] toShortArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public int[] toIntArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public long[] toLongArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public float[] toFloatArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public double[] toDoubleArray(){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public <T> T[] toArray(T[] dst){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        //TODO
        throw new omni.util.NotYetImplementedException();
      }
    }
  }
}
