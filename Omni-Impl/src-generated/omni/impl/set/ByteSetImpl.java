package omni.impl.set;
import java.io.Externalizable;
import java.io.ObjectOutput;
import java.io.ObjectInput;
import java.io.IOException;
import java.util.Collection;
import omni.api.OmniCollection;
import omni.function.ByteConsumer;
import omni.function.BytePredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import omni.api.OmniIterator;
import omni.api.OmniNavigableSet;
import omni.util.ToStringUtil;
import omni.impl.AbstractByteItr;
import omni.impl.CheckedCollection;
import omni.util.TypeUtil;
import omni.util.OmniArray;
import java.util.function.IntFunction;
import omni.function.ByteComparator;
import java.util.NoSuchElementException;
public abstract class ByteSetImpl extends AbstractByteSet.ComparatorlessImpl{
  transient long word0;
  transient long word1;
  transient long word2;
  transient long word3;
  private ByteSetImpl(){
    super();
  }
  private ByteSetImpl(long word0,long word1,long word2,long word3){
    super();
    this.word0=word0;
    this.word1=word1;
    this.word2=word2;
    this.word3=word3;
  }
  private ByteSetImpl(ByteSetImpl that){
    this(that.word0,that.word1,that.word2,that.word3);
  }
  private ByteSetImpl(Collection<? extends Byte> that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private ByteSetImpl(OmniCollection.OfRef<? extends Byte> that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private ByteSetImpl(OmniCollection.OfBoolean that){
    super();
    //TODO optimize;
    this.addAll(that);
  }
  private ByteSetImpl(OmniCollection.OfByte that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  private ByteSetImpl(OmniCollection.ByteOutput<?> that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  //TODO equals
  private static void consumeWordAscending(long word,int inclLo,int exclHi,ByteConsumer action){
    do{
      if((word&(1L<<inclLo))!=0){
        action.accept((byte)inclLo);
      }
    }while(++inclLo!=exclHi);
  }
  private void forEachAscendingHelper(int numLeft,int offset,ByteConsumer action){
    switch(offset>>6){
      case -2:
        for(long word=this.word0;;){
          if((word&(1L<<offset))!=0){
            action.accept((byte)offset);
            if(--numLeft==0){
              return;
            }
          }
          if(++offset==-64){
            break;
          }
        }
      case -1:
        for(long word=this.word1;;){
          if((word&(1L<<offset))!=0){
            action.accept((byte)offset);
            if(--numLeft==0){
              return;
            }
          }
          if(++offset==0){
            break;
          }
        }
      case 0:
        for(long word=this.word2;;){
          if((word&(1L<<offset))!=0){
            action.accept((byte)offset);
            if(--numLeft==0){
              return;
            }
          }
          if(++offset==64){
            break;
          }
        }
      default:
        for(long word=this.word3;;++offset){
          if((word&(1L<<offset))!=0){
            action.accept((byte)offset);
            if(--numLeft==0){
              return;
            }
          }
        }
    }
  }
  private void forEachDescendingHelper(int numLeft,int offset,ByteConsumer action){
    switch(offset>>6){
      case 1:
        for(long word=this.word3;;){
          if((word&(1L<<offset))!=0){
            action.accept((byte)offset);
            if(--numLeft==0){
              return;
            }
          }
          if(--offset==63){
            break;
          }
        }
      case 0:
        for(long word=this.word2;;){
          if((word&(1L<<offset))!=0){
            action.accept((byte)offset);
            if(--numLeft==0){
              return;
            }
          }
          if(--offset==-1){
            break;
          }
        }
      case -1:
        for(long word=this.word1;;){
          if((word&(1L<<offset))!=0){
            action.accept((byte)offset);
            if(--numLeft==0){
              return;
            }
          }
          if(--offset==-65){
            break;
          }
        }
      default:
        for(long word=this.word0;;--offset){
          if((word&(1L<<offset))!=0){
            action.accept((byte)offset);
            if(--numLeft==0){
              return;
            }
          }
        }
    }
  }
  @Override public boolean contains(boolean val){
    return (word2&(val?0b10:0b01))!=0;
  }
  @Override public boolean contains(byte val){
    switch(val>>6){
      case -2:
        return
          ((word0)&(1L<<(val)))!=0
          ;
      case -1:
        return
          ((word1)&(1L<<(val)))!=0
          ;
      case 0:
        return
          ((word2)&(1L<<(val)))!=0
          ;
      default:
        return
          ((word3)&(1L<<(val)))!=0
          ;
    }
  }
  @Override public boolean contains(char val){
    switch(val>>6){
      case 0:
        return
          ((word2)&(1L<<(val)))!=0
          ;
      case 1:
        return
          ((word3)&(1L<<(val)))!=0
          ;
      default:
        return false;
    }
  }
  @Override public boolean contains(int val){
    switch(val>>6){
      case -2:
        return
          ((word0)&(1L<<(val)))!=0
          ;
      case -1:
        return
          ((word1)&(1L<<(val)))!=0
          ;
      case 0:
        return
          ((word2)&(1L<<(val)))!=0
          ;
      case 1:
        return
          ((word3)&(1L<<(val)))!=0
          ;
      default:
        return false; 
    }
  }
  @Override public boolean removeVal(boolean val){
    long word;
    if((word=this.word2)!=(word&=(val?0b01:0b10))){
      this.word2=word;
      return true;
    }
    return false;
  }
  @Override public boolean removeVal(byte val){
    returnTrue:for(;;){
      returnFalse:switch(val>>6){
      case -2:
        long word;
        if((word=this.word0)!=(word&=(~(1L<<(val))))){
          this.word0=word;
          break returnTrue;
        }
        break returnFalse;
      case -1:
        if((word=this.word1)!=(word&=(~(1L<<(val))))){
          this.word1=word;
          break returnTrue;
        }
        break returnFalse;
      case 0:
        if((word=this.word2)!=(word&=(~(1L<<(val))))){
          this.word2=word;
          break returnTrue;
        }
        break returnFalse;
      default:
        if((word=this.word3)!=(word&=(~(1L<<(val))))){
          this.word3=word;
          break returnTrue;
        }
        break returnFalse;
      }
      return false;
    }
    return true;
  }
  @Override public boolean removeVal(char val){
    returnTrue:for(;;){
      returnFalse:switch(val>>6){
      case 0:
        long word;
        if((word=this.word2)!=(word&=(~(1L<<(val))))){
          this.word2=word;
          break returnTrue;
        }
        break returnFalse;
      case 1:
        if((word=this.word3)!=(word&=(~(1L<<(val))))){
          this.word3=word;
          break returnTrue;
        }
        break returnFalse;
      default:
      }
      return false;
    }
    return true;
  }
  @Override public boolean removeVal(int val){
    returnTrue:for(;;){
      returnFalse:switch(val>>6){
      case -2:
        long word;
        if((word=this.word0)!=(word&=(~(1L<<(val))))){
          this.word0=word;
          break returnTrue;
        }
        break returnFalse;
      case -1:
        if((word=this.word1)!=(word&=(~(1L<<(val))))){
          this.word1=word;
          break returnTrue;
        }
        break returnFalse;
      case 0:
        if((word=this.word2)!=(word&=(~(1L<<(val))))){
          this.word2=word;
          break returnTrue;
        }
        break returnFalse;
      case 1:
        if((word=this.word3)!=(word&=(~(1L<<(val))))){
          this.word3=word;
          break returnTrue;
        }
        break returnFalse;
      default:
      }
      return false;
    }
    return true;
  }
  private static long removeIfWord(long word,int inclLo,int exclHi,BytePredicate filter){
    do{
      long mask;
      if((word&(mask=1L<<inclLo))!=0){
        if(filter.test((byte)inclLo)){
          word&=(~mask);
        }
      }
    }while(++inclLo!=exclHi);
    return word;
  }
  @Override public boolean removeIf(BytePredicate filter){
    long word;
    return (word=this.word0)!=(this.word0=removeIfWord(word,-128,-64,filter)) |
           (word=this.word1)!=(this.word1=removeIfWord(word,-64,0,filter)) |
           (word=this.word2)!=(this.word2=removeIfWord(word,0,64,filter)) |
           (word=this.word3)!=(this.word3=removeIfWord(word,64,128,filter));
  }
  @Override public void forEach(ByteConsumer action){
    consumeWordAscending(word0,-128,-64,action);
    consumeWordAscending(word1,-64,0,action);
    consumeWordAscending(word2,0,64,action);
    consumeWordAscending(word3,64,128,action);
  }
  @Override public void forEach(Consumer<? super Byte> action){
    forEach((ByteConsumer)action::accept);
  }
  @Override public byte higherByte(byte val){
    final int v;
    if((v=getThisOrHigher(val+1))!=128){
      return (byte)v;
    }
    return Byte.MIN_VALUE;
  }
  @Override public short higherShort(short val){
    final int v;
    if((v=getThisOrHigher(Math.max(val+1,Byte.MIN_VALUE)))!=128){
      return (short)v;
    }
    return Short.MIN_VALUE;
  }
  @Override public int higherInt(int val){
    final int v;
    if((v=getThisOrHigher(Math.max(val+1,Byte.MIN_VALUE)))!=128){
      return v;
    }
    return Integer.MIN_VALUE;
  }
  @Override public long higherLong(long val){
    final int v;
    if(val<Byte.MAX_VALUE && (v=getThisOrHigher(Math.max(((int)val)+1,Byte.MIN_VALUE)))!=128){
      return v;
    }
    return Long.MIN_VALUE;
  }
  @Override public float higherFloat(float val){
    final int v;
    if(val<Byte.MAX_VALUE && (v=getThisOrHigher(Math.max(TypeUtil.higherInt(val),Byte.MIN_VALUE)))!=128){
      return v;
    }
    return Float.NaN;
  }
  @Override public double higherDouble(double val){
    final int v;
    if(val<Byte.MAX_VALUE && (v=getThisOrHigher(Math.max(TypeUtil.higherInt(val),Byte.MIN_VALUE)))!=128){
      return v;
    }
    return Double.NaN;
  }
  @Override public Byte higher(byte val){
    final int v;
    if((v=getThisOrHigher(val+1))!=128){
      return (byte)v;
    }
    return null;
  }
  @Override public byte byteCeiling(byte val){
    final int v;
    if((v=getThisOrHigher(val))!=128){
      return (byte)v;
    }
    return Byte.MIN_VALUE;
  }
  @Override public short shortCeiling(short val){
    final int v;
    if((v=getThisOrHigher(Math.max(val,Byte.MIN_VALUE)))!=128){
      return (short)v;
    }
    return Short.MIN_VALUE;
  }
  @Override public int intCeiling(int val){
    final int v;
    if((v=getThisOrHigher(Math.max(val,Byte.MIN_VALUE)))!=128){
      return v;
    }
    return Integer.MIN_VALUE;
  }
  @Override public long longCeiling(long val){
    final int v;
    if(val<=Byte.MAX_VALUE && (v=getThisOrHigher(Math.max(((int)val),Byte.MIN_VALUE)))!=128){
      return v;
    }
    return Long.MIN_VALUE;
  }
  @Override public float floatCeiling(float val){
    final int v;
    if(val<=Byte.MAX_VALUE && (v=getThisOrHigher(Math.max(TypeUtil.intCeiling(val),Byte.MIN_VALUE)))!=128){
      return v;
    }
    return Float.NaN;
  }
  @Override public double doubleCeiling(double val){
    final int v;
    if(val<=Byte.MAX_VALUE && (v=getThisOrHigher(Math.max(TypeUtil.intCeiling(val),Byte.MIN_VALUE)))!=128){
      return v;
    }
    return Double.NaN;
  }
  @Override public Byte ceiling(byte val){
    final int v;
    if((v=getThisOrHigher(val))!=128){
      return (byte)v;
    }
    return null;
  }
  @Override public byte lowerByte(byte val){
    final int v;
    if((v=getThisOrHigher(val-1))!=-129){
      return (byte)v;
    }
    return Byte.MIN_VALUE;
  }
  @Override public short lowerShort(short val){
    final int v;
    if((v=getThisOrLower(Math.min(val-1,Byte.MAX_VALUE)))!=-129){
      return (short)v;
    }
    return Short.MIN_VALUE;
  }
  @Override public int lowerInt(int val){
    final int v;
    if((v=getThisOrLower(Math.min(val-1,Byte.MAX_VALUE)))!=-129){
      return v;
    }
    return Integer.MIN_VALUE;
  }
  @Override public long lowerLong(long val){
    final int v;
    if(val>Byte.MIN_VALUE && (v=getThisOrLower(Math.min(((int)val)-1,Byte.MAX_VALUE)))!=-129){
      return v;
    }
    return Long.MIN_VALUE;
  }
  @Override public float lowerFloat(float val){
    for(;;){
      int v;
      if(val!=val){
        v=Byte.MAX_VALUE;
      }else{
        if(val<=Byte.MIN_VALUE){
          break;
        }
        v=Math.min(Byte.MAX_VALUE,TypeUtil.lowerInt(val));
      }
      if((v=getThisOrLower(v))!=-129){
        return v;
      }
      break;
    }
    return Float.NaN;
  }
  @Override public double lowerDouble(double val){
    for(;;){
      int v;
      if(val!=val){
        v=Byte.MAX_VALUE;
      }else{
        if(val<=Byte.MIN_VALUE){
          break;
        }
        v=Math.min(Byte.MAX_VALUE,TypeUtil.lowerInt(val));
      }
      if((v=getThisOrLower(v))!=-129){
        return v;
      }
      break;
    }
    return Double.NaN;
  }
  @Override public Byte lower(byte val){
    final int v;
    if((v=getThisOrLower(val-1))!=-129){
      return (byte)v;
    }
    return null;
  }
  @Override public byte byteFloor(byte val){
    final int v;
    if((v=getThisOrHigher(val))!=-129){
      return (byte)v;
    }
    return Byte.MIN_VALUE;
  }
  @Override public short shortFloor(short val){
    final int v;
    if((v=getThisOrLower(Math.min(val,Byte.MAX_VALUE)))!=-129){
      return (short)v;
    }
    return Short.MIN_VALUE;
  }
  @Override public int intFloor(int val){
    final int v;
    if((v=getThisOrLower(Math.min(val,Byte.MAX_VALUE)))!=-129){
      return v;
    }
    return Integer.MIN_VALUE;
  }
  @Override public long longFloor(long val){
    final int v;
    if(val>=Byte.MIN_VALUE && (v=getThisOrLower(Math.min(((int)val),Byte.MAX_VALUE)))!=-129){
      return v;
    }
    return Long.MIN_VALUE;
  }
  @Override public float floatFloor(float val){
    for(;;){
      int v;
      if(val!=val){
        v=Byte.MAX_VALUE;
      }else{
        if(val<=Byte.MIN_VALUE){
          break;
        }
        v=Math.min(Byte.MAX_VALUE,TypeUtil.intFloor(val));
      }
      if((v=getThisOrLower(v))!=-129){
        return v;
      }
      break;
    }
    return Float.NaN;
  }
  @Override public double doubleFloor(double val){
    for(;;){
      int v;
      if(val!=val){
        v=Byte.MAX_VALUE;
      }else{
        if(val<=Byte.MIN_VALUE){
          break;
        }
        v=Math.min(Byte.MAX_VALUE,TypeUtil.intFloor(val));
      }
      if((v=getThisOrLower(v))!=-129){
        return v;
      }
      break;
    }
    return Double.NaN;
  }
  @Override public Byte floor(byte val){
    final int v;
    if((v=getThisOrLower(val))!=-129){
      return (byte)v;
    }
    return null;
  }
  private int getThisOrLower(int offset){
    int lead0s;
    switch(offset>>6){
      case 1:
        if((lead0s=Long.numberOfLeadingZeros(word3<<(-offset-1)))!=64){
          break;
        }
        offset=63;
      case 0:
        if((lead0s=Long.numberOfLeadingZeros(word2<<(-offset-1)))!=64){
          break;
        }
        offset=-1;
      case -1:
        if((lead0s=Long.numberOfLeadingZeros(word1<<(-offset-1)))!=64){
          break;
        }
        offset=-65;
      case -2:
        if((lead0s=Long.numberOfLeadingZeros(word0<<(-offset-1)))!=64){
          break;
        }
      default:
        return -129;
    }
    return offset-lead0s;
  }
  private int getThisOrHigher(int offset){
    int tail0s;
    switch(offset>>6){
      case -2:
        if((tail0s=Long.numberOfTrailingZeros(word0>>>offset))!=64){
          break;
        }
        offset=-64;
      case -1:
        if((tail0s=Long.numberOfTrailingZeros(word1>>>offset))!=64){
          break;
        }
        offset=0;
      case 0:
        if((tail0s=Long.numberOfTrailingZeros(word2>>>offset))!=64){
          break;
        }
        offset=64;
      case 1:
        if((tail0s=Long.numberOfTrailingZeros(word3>>>offset))!=64){
          break;
        }
      default:
        return 128;
    }
    return offset+tail0s;
  }
  public static abstract class Unchecked extends ByteSetImpl implements Externalizable{
    private static final long serialVersionUID=1L;
    private Unchecked(){
      super();
    }
    private Unchecked(long word0,long word1,long word2,long word3){
      super(word0,word1,word2,word3);
    }
    private Unchecked(ByteSetImpl that){
      super(that.word0,that.word1,that.word2,that.word3);
    }
    private Unchecked(Collection<? extends Byte> that){
      super(that);
    }
    private Unchecked(OmniCollection.OfRef<? extends Byte> that){
      super(that);
    }
    private Unchecked(OmniCollection.OfBoolean that){
      super(that);
    }
    private Unchecked(OmniCollection.OfByte that){
      super(that);
    }
    private Unchecked(OmniCollection.ByteOutput<?> that){
      super(that);
    }
    @Override public int firstInt(){
      int v;
      for(;;){
        if((v=Long.numberOfTrailingZeros(word0))!=64){
          break;
        }
        if((v+=Long.numberOfTrailingZeros(word1))!=128){
          break;
        }
        if((v+=Long.numberOfTrailingZeros(word2))!=192){
          break;
        }
        v+=Long.numberOfTrailingZeros(word3);
        break;
      }
      return v-128;
    }
    @Override public int pollFirstInt(){
      int v;
      for(;;){
        long word;
        if((v=Long.numberOfTrailingZeros(word=word0))!=64){
          word0=word&(~(1L<<v));
          break;
        }
        if((v+=Long.numberOfTrailingZeros(word=word1))!=128){
          word1=word&(~(1L<<v));
          break;
        }
        if((v+=Long.numberOfTrailingZeros(word=word2))!=192){
          word2=word&(~(1L<<v));
          break;
        }
        if((v+=Long.numberOfTrailingZeros(word=word3))!=256){
          word3=word&(~(1L<<v));
          break;
        }
        return Integer.MIN_VALUE;
      }
      return v-128;
    }
    @Override public int lastInt(){
      int v;
      for(;;){
        if((v=Long.numberOfLeadingZeros(word3))!=64){
          break;
        }
        if((v+=Long.numberOfLeadingZeros(word2))!=128){
          break;
        }
        if((v+=Long.numberOfLeadingZeros(word1))!=192){
          break;
        }
        v+=Long.numberOfLeadingZeros(word0);
        break;
      }
      return 127-v;
    }
    @Override public int pollLastInt(){
      int v;
      for(;;){
        long word;
        if((v=Long.numberOfLeadingZeros(word=word3))!=64){
          word3=word&~(1L<<(-v-1));
          break;
        }
        if((v+=Long.numberOfLeadingZeros(word=word2))!=128){
          word2=word&~(1L<<(-v-1));
          break;
        }
        if((v+=Long.numberOfLeadingZeros(word=word1))!=192){
          word1=word&~(1L<<(-v-1));
          break;
        }
        if((v+=Long.numberOfLeadingZeros(word=word0))!=256){
          word0=word&~(1L<<(-v-1));
          break;
        }
        return Integer.MIN_VALUE;
      }
      return 127-v;
    }
    @Override public void clear(){
      word0=0;
      word1=0;
      word2=0;
      word3=0;
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      return super.removeIf((BytePredicate)filter::test);
    }
    private static int wordHashCode(long word,int inclLo,int inclHi){
      int hash=0;
      do{
        if((word&(1L<<inclLo))!=0){
          hash+=inclLo;
        }
      }while(++inclLo!=inclHi);
      return hash;
    }
    @Override public int hashCode(){
      return wordHashCode(word0,-128,-64) +
             wordHashCode(word1,-64,0) +
             wordHashCode(word2,0,64) +
             wordHashCode(word3,64,128);
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
      returnTrue:for(;;){
        returnFalse:switch(val>>6){
      case -2:
        long word;
        if((word=this.word0)!=(word|=(1L<<(val)))){
          this.word0=word;
          break returnTrue;
        }
        break returnFalse;
      case -1:
        if((word=this.word1)!=(word|=(1L<<(val)))){
          this.word1=word;
          break returnTrue;
        }
        break returnFalse;
      case 0:
        if((word=this.word2)!=(word|=(1L<<(val)))){
          this.word2=word;
          break returnTrue;
        }
        break returnFalse;
      default:
        if((word=this.word3)!=(word|=(1L<<(val)))){
          this.word3=word;
          break returnTrue;
        }
        break returnFalse;
        }
        return false;
      }
      return true;
    }
    @Override public boolean contains(long val){
      final int v;
      return (v=(int)val)==val && super.contains(v);
    }
    @Override public boolean removeVal(long val){
      final int v;
      return (v=(int)val)==val && super.removeVal(v);
    }
    @Override public boolean contains(float val){
      final int v;
      return (v=(int)val)==val && super.contains(v);
    }
    @Override public boolean removeVal(float val){
      final int v;
      return (v=(int)val)==val && super.removeVal(v);
    }
    @Override public boolean contains(double val){
      final int v;
      return (v=(int)val)==val && super.contains(v);
    }
    @Override public boolean removeVal(double val){
      final int v;
      return (v=(int)val)==val && super.removeVal(v);
    }
    @Override public boolean contains(Object val){
      final int v;
      if(val instanceof Byte){
        return super.contains((byte)val);
      }else if(val instanceof Integer || val instanceof Short){
        v=((Number)val).intValue();
      }else if(val instanceof Long){
        final long l;
        if((l=(long)val)!=(v=(int)l)){
          return false;
        }
      }else if(val instanceof Float){
        final float f;
        if((f=(float)val)!=(v=(int)f)){
          return false;
        }
      }else if(val instanceof Double){
        final double d;
        if((d=(double)val)!=(v=(int)d)){
          return false;
        }
      }else if(val instanceof Character){
        return super.contains((char)val);
      }else if(val instanceof Boolean){
        return super.contains((boolean)val);
      }else{
        return false;
      }
      return super.contains(v);
    }
    @Override public boolean remove(Object val){
      final int v;
      if(val instanceof Byte){
        return super.removeVal((byte)val);
      }else if(val instanceof Integer || val instanceof Short){
        v=((Number)val).intValue();
      }else if(val instanceof Long){
        final long l;
        if((l=(long)val)!=(v=(int)l)){
          return false;
        }
      }else if(val instanceof Float){
        final float f;
        if((f=(float)val)!=(v=(int)f)){
          return false;
        }
      }else if(val instanceof Double){
        final double d;
        if((d=(double)val)!=(v=(int)d)){
          return false;
        }
      }else if(val instanceof Character){
        return super.removeVal((char)val);
      }else if(val instanceof Boolean){
        return super.removeVal((boolean)val);
      }else{
        return false;
      }
      return super.removeVal(v);
    }
    @Override public int size(){
      return SetCommonImpl.size(word0,word1,word2,word3);
    }
    @Override public boolean isEmpty(){
      return word0==0 && word1==0 && word2==0 && word3==0;
    }
    @Override public void writeExternal(ObjectOutput oos) throws IOException{
      oos.writeLong(word0);
      oos.writeLong(word1);
      oos.writeLong(word2);
      oos.writeLong(word3);
    }
    @Override public void readExternal(ObjectInput ois) throws IOException{
      word0=ois.readLong();
      word1=ois.readLong();
      word2=ois.readLong();
      word3=ois.readLong();
    }
    @Override public OmniIterator.OfByte iterator(){
      int offset;
      final int numLeft;
      goToNonEmptyItr:for(;;){
        long word;
        if((offset=Long.numberOfTrailingZeros(word=word0))!=64){
            numLeft=SetCommonImpl.size(word,word1,word2,word3);
            break goToNonEmptyItr;
        }
        if((offset+=Long.numberOfTrailingZeros(word=word1))!=128){
            numLeft=Long.bitCount(word)+Long.bitCount(word2)+Long.bitCount(word3);
            break goToNonEmptyItr;
        }
        if((offset+=Long.numberOfTrailingZeros(word=word2))!=192){
            numLeft=Long.bitCount(word)+Long.bitCount(word3);
            break goToNonEmptyItr;
        }
        if((offset+=Long.numberOfTrailingZeros(word=word3))!=256){
            numLeft=Long.bitCount(word);
            break goToNonEmptyItr;
        }
        return EmptyView.EMPTY_ITR;
      }
      return new UncheckedAscendingItr(this,offset-128,numLeft);
    }
    @Override public OmniIterator.OfByte descendingIterator(){
      int offset;
      final int numLeft;
      goToNonEmptyItr:for(;;){
        long word;
        if((offset=Long.numberOfLeadingZeros(word=word3))!=64){
            numLeft=SetCommonImpl.size(word0,word1,word2,word);
            break goToNonEmptyItr;
        }
        if((offset+=Long.numberOfLeadingZeros(word=word2))!=128){
            numLeft=Long.bitCount(word0)+Long.bitCount(word1)+Long.bitCount(word);
            break goToNonEmptyItr;
        }
        if((offset+=Long.numberOfLeadingZeros(word=word1))!=192){
            numLeft=Long.bitCount(word0)+Long.bitCount(word);
            break goToNonEmptyItr;
        }
        if((offset+=Long.numberOfLeadingZeros(word=word0))!=256){
            numLeft=Long.bitCount(word);
            break goToNonEmptyItr;
        }
        return EmptyView.EMPTY_ITR;
      }
      return new UncheckedDescendingItr(this,127-offset,numLeft);
    }
    public static class Ascending extends Unchecked implements Cloneable{
      private static final long serialVersionUID=1L;
      public Ascending(){
        super();
      }
      Ascending(long word0,long word1,long word2,long word3){
        super(word0,word1,word2,word3);
      }
      public Ascending(ByteSetImpl that){
        super(that);
      }
      public Ascending(Collection<? extends Byte> that){
        super(that);
      }
      public Ascending(OmniCollection.OfRef<? extends Byte> that){
        super(that);
      }
      public Ascending(OmniCollection.OfBoolean that){
        super(that);
      }
      public Ascending(OmniCollection.OfByte that){
        super(that);
      }
      public Ascending(OmniCollection.ByteOutput<?> that){
        super(that);
      }
      @Override public Object clone(){
        return new Ascending(this);
      }
      @Override public String toString(){
        final long word0,word1,word2,word3;
        int size;
        if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
          final byte[] buffer;
          int bufferOffset;
          (buffer=new byte[size*6])[bufferOffset=0]='[';
          done:for(int valOffset=Byte.MIN_VALUE;;){
            do{
              if((word0&(1L<<valOffset))!=0){
                bufferOffset=ToStringUtil.getStringShort(valOffset,buffer,++bufferOffset);
                if(--size==0){
                  break done;
                }
                buffer[bufferOffset]=',';
                buffer[++bufferOffset]=' ';
              }
            }while(++valOffset!=-64);
            do{
              if((word1&(1L<<valOffset))!=0){
                bufferOffset=ToStringUtil.getStringShort(valOffset,buffer,++bufferOffset);
                if(--size==0){
                  break done;
                }
                buffer[bufferOffset]=',';
                buffer[++bufferOffset]=' ';
              }
            }while(++valOffset!=0);
            do{
              if((word2&(1L<<valOffset))!=0){
                bufferOffset=ToStringUtil.getStringShort(valOffset,buffer,++bufferOffset);
                if(--size==0){
                  break done;
                }
                buffer[bufferOffset]=',';
                buffer[++bufferOffset]=' ';
              }
            }while(++valOffset!=64);
            for(;;++valOffset){
              if((word3&(1L<<valOffset))!=0){
                bufferOffset=ToStringUtil.getStringShort(valOffset,buffer,++bufferOffset);
                if(--size==0){
                  break done;
                }
                buffer[bufferOffset]=',';
                buffer[++bufferOffset]=' ';
              }
           }
          }
          buffer[bufferOffset]=']';
          return new String(buffer,0,bufferOffset+1,ToStringUtil.IOS8859CharSet);
        }
        return "[]";
      }
      @Override public OmniNavigableSet.OfByte descendingSet(){
        //TODO
        return null;
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
        //TODO
        return null;
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,boolean fromInclusive,byte toElement,boolean toInclusive){
        //TODO
        return null;
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement){
        //TODO
        return null;
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
        //TODO
        return null;
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
        //TODO
        return null;
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement,boolean inclusive){
        //TODO
        return null;
      }
      @SuppressWarnings("unchecked")
      @Override public <T> T[] toArray(T[] dst){
        int size;
        final long word0,word1,word2,word3;
        if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
          dst=OmniArray.uncheckedArrResize(size,dst);
          done:for(int offset=Byte.MAX_VALUE;;){
            do{
              if((word3&(1L<<offset))!=0){
                dst[--size]=(T)(Byte)(byte)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(--offset!=63);
            do{
              if((word2&(1L<<offset))!=0){
                dst[--size]=(T)(Byte)(byte)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(--offset!=-1);
            do{
              if((word1&(1L<<offset))!=0){
                dst[--size]=(T)(Byte)(byte)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(--offset!=-65);
            for(;;--offset){
              if((word0&(1L<<offset))!=0){
                dst[--size]=(T)(Byte)(byte)(offset);
                if(size==0){
                  break done;
                }
              }
            }
          }
        }else if(dst.length!=0){
          dst[0]=null;
        }
        return dst;
      }
      @SuppressWarnings("unchecked")
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        int size;
        final long word0,word1,word2,word3;
        final T[] dst=arrConstructor.apply(size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3));
        if(size!=0){
          done:for(int offset=Byte.MAX_VALUE;;){
            do{
              if((word3&(1L<<offset))!=0){
                dst[--size]=(T)(Byte)(byte)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(--offset!=63);
            do{
              if((word2&(1L<<offset))!=0){
                dst[--size]=(T)(Byte)(byte)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(--offset!=-1);
            do{
              if((word1&(1L<<offset))!=0){
                dst[--size]=(T)(Byte)(byte)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(--offset!=-65);
            for(;;--offset){
              if((word0&(1L<<offset))!=0){
                dst[--size]=(T)(Byte)(byte)(offset);
                if(size==0){
                  break done;
                }
              }
            }
          }
        }
        return dst;
      }
      @Override public Byte[] toArray(){
        int size;
        long word0,word1,word2,word3;
        if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
          final Byte[] dst=new Byte[size];
          done:for(int offset=Byte.MAX_VALUE;;){
            do{
              if((word3&(1L<<offset))!=0){
                dst[--size]=(Byte)(byte)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(--offset!=63);
            do{
              if((word2&(1L<<offset))!=0){
                dst[--size]=(Byte)(byte)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(--offset!=-1);
            do{
              if((word1&(1L<<offset))!=0){
                dst[--size]=(Byte)(byte)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(--offset!=-65);
            for(;;--offset){
              if((word0&(1L<<offset))!=0){
                dst[--size]=(Byte)(byte)(offset);
                if(size==0){
                  break done;
                }
              }
            }
          }
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_BOXED_ARR;
      }
      @Override public byte[] toByteArray(){
        int size;
        long word0,word1,word2,word3;
        if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
          final byte[] dst=new byte[size];
          done:for(int offset=Byte.MAX_VALUE;;){
            do{
              if((word3&(1L<<offset))!=0){
                dst[--size]=(byte)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(--offset!=63);
            do{
              if((word2&(1L<<offset))!=0){
                dst[--size]=(byte)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(--offset!=-1);
            do{
              if((word1&(1L<<offset))!=0){
                dst[--size]=(byte)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(--offset!=-65);
            for(;;--offset){
              if((word0&(1L<<offset))!=0){
                dst[--size]=(byte)(offset);
                if(size==0){
                  break done;
                }
              }
            }
          }
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_ARR;
      }
      @Override public short[] toShortArray(){
        int size;
        long word0,word1,word2,word3;
        if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
          final short[] dst=new short[size];
          done:for(int offset=Byte.MAX_VALUE;;){
            do{
              if((word3&(1L<<offset))!=0){
                dst[--size]=(short)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(--offset!=63);
            do{
              if((word2&(1L<<offset))!=0){
                dst[--size]=(short)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(--offset!=-1);
            do{
              if((word1&(1L<<offset))!=0){
                dst[--size]=(short)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(--offset!=-65);
            for(;;--offset){
              if((word0&(1L<<offset))!=0){
                dst[--size]=(short)(offset);
                if(size==0){
                  break done;
                }
              }
            }
          }
          return dst;
        }
        return OmniArray.OfShort.DEFAULT_ARR;
      }
      @Override public int[] toIntArray(){
        int size;
        long word0,word1,word2,word3;
        if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
          final int[] dst=new int[size];
          done:for(int offset=Byte.MAX_VALUE;;){
            do{
              if((word3&(1L<<offset))!=0){
                dst[--size]=(int)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(--offset!=63);
            do{
              if((word2&(1L<<offset))!=0){
                dst[--size]=(int)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(--offset!=-1);
            do{
              if((word1&(1L<<offset))!=0){
                dst[--size]=(int)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(--offset!=-65);
            for(;;--offset){
              if((word0&(1L<<offset))!=0){
                dst[--size]=(int)(offset);
                if(size==0){
                  break done;
                }
              }
            }
          }
          return dst;
        }
        return OmniArray.OfInt.DEFAULT_ARR;
      }
      @Override public long[] toLongArray(){
        int size;
        long word0,word1,word2,word3;
        if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
          final long[] dst=new long[size];
          done:for(long offset=Byte.MAX_VALUE;;){
            do{
              if((word3&(1L<<offset))!=0){
                dst[--size]=(long)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(--offset!=63);
            do{
              if((word2&(1L<<offset))!=0){
                dst[--size]=(long)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(--offset!=-1);
            do{
              if((word1&(1L<<offset))!=0){
                dst[--size]=(long)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(--offset!=-65);
            for(;;--offset){
              if((word0&(1L<<offset))!=0){
                dst[--size]=(long)(offset);
                if(size==0){
                  break done;
                }
              }
            }
          }
          return dst;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
      }
      @Override public float[] toFloatArray(){
        int size;
        long word0,word1,word2,word3;
        if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
          final float[] dst=new float[size];
          done:for(int offset=Byte.MAX_VALUE;;){
            do{
              if((word3&(1L<<offset))!=0){
                dst[--size]=(float)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(--offset!=63);
            do{
              if((word2&(1L<<offset))!=0){
                dst[--size]=(float)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(--offset!=-1);
            do{
              if((word1&(1L<<offset))!=0){
                dst[--size]=(float)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(--offset!=-65);
            for(;;--offset){
              if((word0&(1L<<offset))!=0){
                dst[--size]=(float)(offset);
                if(size==0){
                  break done;
                }
              }
            }
          }
          return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
      }
      @Override public double[] toDoubleArray(){
        int size;
        long word0,word1,word2,word3;
        if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
          final double[] dst=new double[size];
          done:for(int offset=Byte.MAX_VALUE;;){
            do{
              if((word3&(1L<<offset))!=0){
                dst[--size]=(double)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(--offset!=63);
            do{
              if((word2&(1L<<offset))!=0){
                dst[--size]=(double)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(--offset!=-1);
            do{
              if((word1&(1L<<offset))!=0){
                dst[--size]=(double)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(--offset!=-65);
            for(;;--offset){
              if((word0&(1L<<offset))!=0){
                dst[--size]=(double)(offset);
                if(size==0){
                  break done;
                }
              }
            }
          }
          return dst;
        }
        return OmniArray.OfDouble.DEFAULT_ARR;
      }
    }
    public static class Descending extends Unchecked implements Cloneable{
      private static final long serialVersionUID=1L;
      public Descending(){
        super();
      }
      Descending(long word0,long word1,long word2,long word3){
        super(word0,word1,word2,word3);
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
      public Descending(OmniCollection.OfBoolean that){
        super(that);
      }
      public Descending(OmniCollection.OfByte that){
        super(that);
      }
      public Descending(OmniCollection.ByteOutput<?> that){
        super(that);
      }
      @Override public int firstInt(){
        return super.lastInt();
      }
      @Override public int lastInt(){
        return super.firstInt();
      }
      @Override public int pollFirstInt(){
        return super.pollLastInt();
      }
      @Override public int pollLastInt(){
        return super.pollFirstInt();
      }
      @Override public ByteComparator comparator(){
        return ByteComparator::descendingCompare;
      }
      @Override public byte higherByte(byte val){
        return super.lowerByte(val);
      }
      @Override public byte lowerByte(byte val){
        return super.higherByte(val);
      }
      @Override public byte byteCeiling(byte val){
        return super.byteFloor(val);
      }
      @Override public byte byteFloor(byte val){
        return super.byteCeiling(val);
      }
      @Override public short higherShort(short val){
        return super.lowerShort(val);
      }
      @Override public short lowerShort(short val){
        return super.higherShort(val);
      }
      @Override public short shortCeiling(short val){
        return super.shortFloor(val);
      }
      @Override public short shortFloor(short val){
        return super.shortCeiling(val);
      }
      @Override public int higherInt(int val){
        return super.lowerInt(val);
      }
      @Override public int lowerInt(int val){
        return super.higherInt(val);
      }
      @Override public int intCeiling(int val){
        return super.intFloor(val);
      }
      @Override public int intFloor(int val){
        return super.intCeiling(val);
      }
      @Override public long higherLong(long val){
        return super.lowerLong(val);
      }
      @Override public long lowerLong(long val){
        return super.higherLong(val);
      }
      @Override public long longCeiling(long val){
        return super.longFloor(val);
      }
      @Override public long longFloor(long val){
        return super.longCeiling(val);
      }
      @Override public float higherFloat(float val){
        return super.lowerFloat(val);
      }
      @Override public float lowerFloat(float val){
        return super.higherFloat(val);
      }
      @Override public float floatCeiling(float val){
        return super.floatFloor(val);
      }
      @Override public float floatFloor(float val){
        return super.floatCeiling(val);
      }
      @Override public double higherDouble(double val){
        return super.lowerDouble(val);
      }
      @Override public double lowerDouble(double val){
        return super.higherDouble(val);
      }
      @Override public double doubleCeiling(double val){
        return super.doubleFloor(val);
      }
      @Override public double doubleFloor(double val){
        return super.doubleCeiling(val);
      }
      @Override public Byte higher(byte val){
        return super.lower(val);
      }
      @Override public Byte lower(byte val){
        return super.higher(val);
      }
      @Override public Byte ceiling(byte val){
        return super.floor(val);
      }
      @Override public Byte floor(byte val){
        return super.ceiling(val);
      }
      @SuppressWarnings("unchecked")
      @Override public <T> T[] toArray(T[] dst){
        int size;
        final long word0,word1,word2,word3;
        if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
          dst=OmniArray.uncheckedArrResize(size,dst);
          done:for(int offset=Byte.MIN_VALUE;;){
            do{
              if((word0&(1L<<offset))!=0){
                dst[--size]=(T)(Byte)(byte)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(++offset!=-64);
            do{
              if((word1&(1L<<offset))!=0){
                dst[--size]=(T)(Byte)(byte)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(++offset!=0);
            do{
              if((word2&(1L<<offset))!=0){
                dst[--size]=(T)(Byte)(byte)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(++offset!=64);
            for(;;++offset){
              if((word3&(1L<<offset))!=0){
                dst[--size]=(T)(Byte)(byte)(offset);
                if(size==0){
                  break done;
                }
              }
            }
          }
        }else if(dst.length!=0){
          dst[0]=null;
        }
        return dst;
      }
      @SuppressWarnings("unchecked")
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        int size;
        final long word0,word1,word2,word3;
        final T[] dst=arrConstructor.apply(size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3));
        if(size!=0){
          done:for(int offset=Byte.MIN_VALUE;;){
            do{
              if((word0&(1L<<offset))!=0){
                dst[--size]=(T)(Byte)(byte)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(++offset!=-64);
            do{
              if((word1&(1L<<offset))!=0){
                dst[--size]=(T)(Byte)(byte)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(++offset!=0);
            do{
              if((word2&(1L<<offset))!=0){
                dst[--size]=(T)(Byte)(byte)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(++offset!=64);
            for(;;++offset){
              if((word3&(1L<<offset))!=0){
                dst[--size]=(T)(Byte)(byte)(offset);
                if(size==0){
                  break done;
                }
              }
            }
          }
        }
        return dst;
      }
      @Override public Byte[] toArray(){
        int size;
        long word0,word1,word2,word3;
        if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
          final Byte[] dst=new Byte[size];
          done:for(int offset=Byte.MIN_VALUE;;){
            do{
              if((word0&(1L<<offset))!=0){
                dst[--size]=(Byte)(byte)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(++offset!=-64);
            do{
              if((word1&(1L<<offset))!=0){
                dst[--size]=(Byte)(byte)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(++offset!=0);
            do{
              if((word2&(1L<<offset))!=0){
                dst[--size]=(Byte)(byte)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(++offset!=64);
            for(;;++offset){
              if((word3&(1L<<offset))!=0){
                dst[--size]=(Byte)(byte)(offset);
                if(size==0){
                  break done;
                }
              }
            }
          }
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_BOXED_ARR;
      }
      @Override public byte[] toByteArray(){
        int size;
        long word0,word1,word2,word3;
        if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
          final byte[] dst=new byte[size];
          done:for(int offset=Byte.MIN_VALUE;;){
            do{
              if((word0&(1L<<offset))!=0){
                dst[--size]=(byte)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(++offset!=-64);
            do{
              if((word1&(1L<<offset))!=0){
                dst[--size]=(byte)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(++offset!=0);
            do{
              if((word2&(1L<<offset))!=0){
                dst[--size]=(byte)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(++offset!=64);
            for(;;++offset){
              if((word3&(1L<<offset))!=0){
                dst[--size]=(byte)(offset);
                if(size==0){
                  break done;
                }
              }
            }
          }
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_ARR;
      }
      @Override public short[] toShortArray(){
        int size;
        long word0,word1,word2,word3;
        if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
          final short[] dst=new short[size];
          done:for(int offset=Byte.MIN_VALUE;;){
            do{
              if((word0&(1L<<offset))!=0){
                dst[--size]=(short)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(++offset!=-64);
            do{
              if((word1&(1L<<offset))!=0){
                dst[--size]=(short)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(++offset!=0);
            do{
              if((word2&(1L<<offset))!=0){
                dst[--size]=(short)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(++offset!=64);
            for(;;++offset){
              if((word3&(1L<<offset))!=0){
                dst[--size]=(short)(offset);
                if(size==0){
                  break done;
                }
              }
            }
          }
          return dst;
        }
        return OmniArray.OfShort.DEFAULT_ARR;
      }
      @Override public int[] toIntArray(){
        int size;
        long word0,word1,word2,word3;
        if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
          final int[] dst=new int[size];
          done:for(int offset=Byte.MIN_VALUE;;){
            do{
              if((word0&(1L<<offset))!=0){
                dst[--size]=(int)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(++offset!=-64);
            do{
              if((word1&(1L<<offset))!=0){
                dst[--size]=(int)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(++offset!=0);
            do{
              if((word2&(1L<<offset))!=0){
                dst[--size]=(int)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(++offset!=64);
            for(;;++offset){
              if((word3&(1L<<offset))!=0){
                dst[--size]=(int)(offset);
                if(size==0){
                  break done;
                }
              }
            }
          }
          return dst;
        }
        return OmniArray.OfInt.DEFAULT_ARR;
      }
      @Override public long[] toLongArray(){
        int size;
        long word0,word1,word2,word3;
        if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
          final long[] dst=new long[size];
          done:for(long offset=Byte.MIN_VALUE;;){
            do{
              if((word0&(1L<<offset))!=0){
                dst[--size]=(long)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(++offset!=-64);
            do{
              if((word1&(1L<<offset))!=0){
                dst[--size]=(long)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(++offset!=0);
            do{
              if((word2&(1L<<offset))!=0){
                dst[--size]=(long)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(++offset!=64);
            for(;;++offset){
              if((word3&(1L<<offset))!=0){
                dst[--size]=(long)(offset);
                if(size==0){
                  break done;
                }
              }
            }
          }
          return dst;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
      }
      @Override public float[] toFloatArray(){
        int size;
        long word0,word1,word2,word3;
        if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
          final float[] dst=new float[size];
          done:for(int offset=Byte.MIN_VALUE;;){
            do{
              if((word0&(1L<<offset))!=0){
                dst[--size]=(float)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(++offset!=-64);
            do{
              if((word1&(1L<<offset))!=0){
                dst[--size]=(float)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(++offset!=0);
            do{
              if((word2&(1L<<offset))!=0){
                dst[--size]=(float)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(++offset!=64);
            for(;;++offset){
              if((word3&(1L<<offset))!=0){
                dst[--size]=(float)(offset);
                if(size==0){
                  break done;
                }
              }
            }
          }
          return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
      }
      @Override public double[] toDoubleArray(){
        int size;
        long word0,word1,word2,word3;
        if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
          final double[] dst=new double[size];
          done:for(int offset=Byte.MIN_VALUE;;){
            do{
              if((word0&(1L<<offset))!=0){
                dst[--size]=(double)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(++offset!=-64);
            do{
              if((word1&(1L<<offset))!=0){
                dst[--size]=(double)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(++offset!=0);
            do{
              if((word2&(1L<<offset))!=0){
                dst[--size]=(double)(offset);
                if(size==0){
                  break done;
                }
              }
            }while(++offset!=64);
            for(;;++offset){
              if((word3&(1L<<offset))!=0){
                dst[--size]=(double)(offset);
                if(size==0){
                  break done;
                }
              }
            }
          }
          return dst;
        }
        return OmniArray.OfDouble.DEFAULT_ARR;
      }
      @Override public void forEach(ByteConsumer action){
        consumeWordDescending(word3,127,63,action);
        consumeWordDescending(word2,63,-1,action);
        consumeWordDescending(word1,-1,-65,action);
        consumeWordDescending(word0,-65,-129,action);
      }
      private static void consumeWordDescending(long word,int inclHi,int exclLo,ByteConsumer action){
        do{
          if((word&(1L<<inclHi))!=0){
            action.accept((byte)inclHi);
          }
        }while(--inclHi!=exclLo);
      }
      @Override public Object clone(){
        return new Descending(this);
      }
      @Override public String toString(){
        final long word0,word1,word2,word3;
        int size;
        if((size=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
          final byte[] buffer;
          int bufferOffset;
          (buffer=new byte[size*6])[bufferOffset=0]='[';
          done:for(int valOffset=Byte.MAX_VALUE;;){
            do{
              if((word3&(1L<<valOffset))!=0){
                bufferOffset=ToStringUtil.getStringShort(valOffset,buffer,++bufferOffset);
                if(--size==0){
                  break done;
                }
                buffer[bufferOffset]=',';
                buffer[++bufferOffset]=' ';
              }
            }while(--valOffset!=63);
            do{
              if((word2&(1L<<valOffset))!=0){
                bufferOffset=ToStringUtil.getStringShort(valOffset,buffer,++bufferOffset);
                if(--size==0){
                  break done;
                }
                buffer[bufferOffset]=',';
                buffer[++bufferOffset]=' ';
              }
            }while(--valOffset!=-1);
            do{
              if((word1&(1L<<valOffset))!=0){
                bufferOffset=ToStringUtil.getStringShort(valOffset,buffer,++bufferOffset);
                if(--size==0){
                  break done;
                }
                buffer[bufferOffset]=',';
                buffer[++bufferOffset]=' ';
              }
            }while(--valOffset!=-65);
            for(;;--valOffset){
              if((word0&(1L<<valOffset))!=0){
                bufferOffset=ToStringUtil.getStringShort(valOffset,buffer,++bufferOffset);
                if(--size==0){
                  break done;
                }
                buffer[bufferOffset]=',';
                buffer[++bufferOffset]=' ';
              }
           }
          }
          buffer[bufferOffset]=']';
          return new String(buffer,0,bufferOffset+1,ToStringUtil.IOS8859CharSet);
        }
        return "[]";
      }
      @Override public OmniIterator.OfByte iterator(){
        return super.descendingIterator();
      }
      @Override public OmniIterator.OfByte descendingIterator(){
        return super.iterator();
      }
      @Override public OmniNavigableSet.OfByte descendingSet(){
        //TODO
        return null;
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
        //TODO
        return null;
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,boolean fromInclusive,byte toElement,boolean toInclusive){
        //TODO
        return null;
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement){
        //TODO
        return null;
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
        //TODO
        return null;
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
        //TODO
        return null;
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement,boolean inclusive){
        //TODO
        return null;
      }
    }
  }
  public static abstract class Checked extends Unchecked{
    private static final long serialVersionUID=1L;
    transient int modCountAndSize;
    private Checked(){
      super();
    }
    private Checked(int size,long word0,long word1,long word2,long word3){
      super(word0,word1,word2,word3);
      this.modCountAndSize=size;
    }
    private Checked(ByteSetImpl that){
      super(that);
      this.modCountAndSize=SetCommonImpl.size(word0,word1,word2,word3);
    }
    private Checked(ByteSetImpl.Checked that){
      super(that);
      this.modCountAndSize=(that.modCountAndSize&0x1ff);
    }
    private Checked(Collection<? extends Byte> that){
      super(that);
    }
    private Checked(OmniCollection.OfRef<? extends Byte> that){
      super(that);
    }
    private Checked(OmniCollection.OfBoolean that){
      super(that);
    }
    private Checked(OmniCollection.OfByte that){
      super(that);
    }
    private Checked(OmniCollection.ByteOutput<?> that){
      super(that);
    }
    @Override public int firstInt(){
      if((modCountAndSize&0x1ff)!=0){
        return super.firstInt();
      }
      throw new NoSuchElementException();
    }
    @Override public int lastInt(){
      if((modCountAndSize&0x1ff)!=0){
        return super.lastInt();
      }
      throw new NoSuchElementException();
    }
    @Override public int pollFirstInt(){
      final int modCountAndSize,v;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0 && (v=super.pollFirstInt())!=Integer.MIN_VALUE){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return v;
      }
      return Integer.MIN_VALUE;
    }
    @Override public int pollLastInt(){
      final int modCountAndSize,v;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0 && (v=super.pollLastInt())!=Integer.MIN_VALUE){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return v;
      }
      return Integer.MIN_VALUE;
    }
    @Override public byte higherByte(byte val){
      if((modCountAndSize&0x1ff)!=0){
        return super.higherByte(val);
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte lowerByte(byte val){
      if((modCountAndSize&0x1ff)!=0){
        return super.lowerByte(val);
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte byteCeiling(byte val){
      if((modCountAndSize&0x1ff)!=0){
        return super.byteCeiling(val);
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte byteFloor(byte val){
      if((modCountAndSize&0x1ff)!=0){
        return super.byteFloor(val);
      }
      return Byte.MIN_VALUE;
    }
    @Override public short higherShort(short val){
      if((modCountAndSize&0x1ff)!=0){
        return super.higherShort(val);
      }
      return Short.MIN_VALUE;
    }
    @Override public short lowerShort(short val){
      if((modCountAndSize&0x1ff)!=0){
        return super.lowerShort(val);
      }
      return Short.MIN_VALUE;
    }
    @Override public short shortCeiling(short val){
      if((modCountAndSize&0x1ff)!=0){
        return super.shortCeiling(val);
      }
      return Short.MIN_VALUE;
    }
    @Override public short shortFloor(short val){
      if((modCountAndSize&0x1ff)!=0){
        return super.shortFloor(val);
      }
      return Short.MIN_VALUE;
    }
    @Override public int higherInt(int val){
      if((modCountAndSize&0x1ff)!=0){
        return super.higherInt(val);
      }
      return Integer.MIN_VALUE;
    }
    @Override public int lowerInt(int val){
      if((modCountAndSize&0x1ff)!=0){
        return super.lowerInt(val);
      }
      return Integer.MIN_VALUE;
    }
    @Override public int intCeiling(int val){
      if((modCountAndSize&0x1ff)!=0){
        return super.intCeiling(val);
      }
      return Integer.MIN_VALUE;
    }
    @Override public int intFloor(int val){
      if((modCountAndSize&0x1ff)!=0){
        return super.intFloor(val);
      }
      return Integer.MIN_VALUE;
    }
    @Override public long higherLong(long val){
      if((modCountAndSize&0x1ff)!=0){
        return super.higherLong(val);
      }
      return Long.MIN_VALUE;
    }
    @Override public long lowerLong(long val){
      if((modCountAndSize&0x1ff)!=0){
        return super.lowerLong(val);
      }
      return Long.MIN_VALUE;
    }
    @Override public long longCeiling(long val){
      if((modCountAndSize&0x1ff)!=0){
        return super.longCeiling(val);
      }
      return Long.MIN_VALUE;
    }
    @Override public long longFloor(long val){
      if((modCountAndSize&0x1ff)!=0){
        return super.longFloor(val);
      }
      return Long.MIN_VALUE;
    }
    @Override public float higherFloat(float val){
      if((modCountAndSize&0x1ff)!=0){
        return super.higherFloat(val);
      }
      return Float.NaN;
    }
    @Override public float lowerFloat(float val){
      if((modCountAndSize&0x1ff)!=0){
        return super.lowerFloat(val);
      }
      return Float.NaN;
    }
    @Override public float floatCeiling(float val){
      if((modCountAndSize&0x1ff)!=0){
        return super.floatCeiling(val);
      }
      return Float.NaN;
    }
    @Override public float floatFloor(float val){
      if((modCountAndSize&0x1ff)!=0){
        return super.floatFloor(val);
      }
      return Float.NaN;
    }
    @Override public double higherDouble(double val){
      if((modCountAndSize&0x1ff)!=0){
        return super.higherDouble(val);
      }
      return Double.NaN;
    }
    @Override public double lowerDouble(double val){
      if((modCountAndSize&0x1ff)!=0){
        return super.lowerDouble(val);
      }
      return Double.NaN;
    }
    @Override public double doubleCeiling(double val){
      if((modCountAndSize&0x1ff)!=0){
        return super.doubleCeiling(val);
      }
      return Double.NaN;
    }
    @Override public double doubleFloor(double val){
      if((modCountAndSize&0x1ff)!=0){
        return super.doubleFloor(val);
      }
      return Double.NaN;
    }
    @Override public Byte higher(byte val){
      if((modCountAndSize&0x1ff)!=0){
        return super.higher(val);
      }
      return null;
    }
    @Override public Byte lower(byte val){
      if((modCountAndSize&0x1ff)!=0){
        return super.lower(val);
      }
      return null;
    }
    @Override public Byte ceiling(byte val){
      if((modCountAndSize&0x1ff)!=0){
        return super.ceiling(val);
      }
      return null;
    }
    @Override public Byte floor(byte val){
      if((modCountAndSize&0x1ff)!=0){
        return super.floor(val);
      }
      return null;
    }
    @Override public int hashCode(){
      int size;
      if((size=this.modCountAndSize&0x1ff)!=0){
        int hash=0;
        done:for(int offset=0;;){
          for(long word=this.word2;;){
            if((word&(1L<<offset))!=0){
              hash+=offset;
              if(--size==0){
                break done;
              }
            }
            if(++offset==64){
              break;
            }
          }
          for(long word=this.word3;;){
            if((word&(1L<<offset))!=0){
              hash+=offset;
              if(--size==0){
                break done;
              }
            }
            if(++offset==128){
              break;
            }
          }
          offset=-1;
          for(long word=this.word1;;){
            if((word&(1L<<offset))!=0){
              hash+=offset;
              if(--size==0){
                break done;
              }
            }
            if(--offset==-65){
              break;
            }
          }
          for(long word=this.word0;;--offset){
            if((word&(1L<<offset))!=0){
              hash+=offset;
              if(--size==0){
                break done;
              }
            }
          }
        }
        return hash;
      }
      return 0;
    }
    private boolean uncheckedRemoveIf(int numLeft,int modCountAndSize,BytePredicate filter){
      long word2=this.word2;
      int numRemoved=0;
      setWord2:for(int offset=0;;){
        long mask;
        if((word2&(mask=1L<<offset))!=0){
          if(filter.test((byte)offset)){
            word2&=~(mask);
            ++numRemoved;
          }
          if(--numLeft==0){
            CheckedCollection.checkModCount(modCountAndSize,this.modCountAndSize);
            if(numRemoved!=0){
              break setWord2;
            }
            return false;
          }
        }
      if(++offset==64){
        long word3=this.word3;
        setWord3:for(;;){
          if((word3&(mask=1L<<offset))!=0){
            if(filter.test((byte)offset)){
              word3&=~(mask);
              ++numRemoved;
            }
            if(--numLeft==0){
              CheckedCollection.checkModCount(modCountAndSize,this.modCountAndSize);
              if(numRemoved!=0){
                break setWord3;
              }
              return false;
            }
          }
          if(++offset==128){
            long word1=this.word1;
            setWord1:for(offset=-1;;){
              if((word1&(mask=1L<<offset))!=0){
                if(filter.test((byte)offset)){
                  word1&=~(mask);
                  ++numRemoved;
                }
                if(--numLeft==0){
                  CheckedCollection.checkModCount(modCountAndSize,this.modCountAndSize);
                  if(numRemoved!=0){
                    break setWord1;
                  }
                  return false;
                }
              }
              if(--offset==-65){
                long word0=this.word0;
                setWord0:for(;;--offset){
                  if((word0&(mask=1L<<offset))!=0){
                    if(filter.test((byte)offset)){
                      word0&=~(mask);
                      ++numRemoved;
                    }
                    if(--numLeft==0){
                      CheckedCollection.checkModCount(modCountAndSize,this.modCountAndSize);
                      if(numRemoved!=0){
                        break setWord0;
                      }
                      return false;
                    }
                  }
                }
                this.word0=word0;
                break setWord1;
              }
            }
            this.word1=word1;
            break setWord3;
          }
        }
        this.word3=word3;
        break setWord2;
        }
      }
      this.word2=word2;
      this.modCountAndSize=modCountAndSize+(1<<9)-numRemoved;
      return true;
    }
    @Override public boolean removeIf(BytePredicate filter){
      final int modCountAndSize;
      final int numLeft;
      return (numLeft=(modCountAndSize=this.modCountAndSize)&0x1ff)!=0 && uncheckedRemoveIf(numLeft,modCountAndSize,filter);
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      final int modCountAndSize;
      final int numLeft;
      return (numLeft=(modCountAndSize=this.modCountAndSize)&0x1ff)!=0 && uncheckedRemoveIf(numLeft,modCountAndSize,filter::test);
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
    @Override public int size(){
      return modCountAndSize&0x1ff;
    }
    @Override public boolean isEmpty(){
      return (modCountAndSize&0x1ff)==0;
    }
    @Override public void clear(){
      final int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        this.modCountAndSize=(modCountAndSize&(~0x1ff))+(1<<9);
        super.clear();
      }
    }
    abstract int uncheckedToString(int size,byte[] buffer);
    @Override public String toString(){
      int size;
      if((size=this.modCountAndSize&0x1ff)!=0){
        final byte[] buffer;
        (buffer=new byte[size*6])[0]='[';
        buffer[size=this.uncheckedToString(size,buffer)]=']';
        return new String(buffer,0,size+1,ToStringUtil.IOS8859CharSet);
      }
      return "[]";
    }
    abstract void uncheckedForEach(int size,ByteConsumer action);
    @Override public void forEach(Consumer<? super Byte> action){
      final int modCountAndSize;
      final int size;
      if((size=(modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        try{
          this.uncheckedForEach(size,action::accept);
        }finally{
          CheckedCollection.checkModCount(modCountAndSize,this.modCountAndSize);
        }
      }
    }
    @Override public void forEach(ByteConsumer action){
      final int modCountAndSize;
      final int size;
      if((size=(modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        try{
          this.uncheckedForEach(size,action);
        }finally{
          CheckedCollection.checkModCount(modCountAndSize,this.modCountAndSize);
        }
      }
    }
    @Override public boolean contains(boolean val){
      return (modCountAndSize&0x1ff)!=0 && super.contains(val);
    }
    @Override public boolean contains(byte val){
      return (modCountAndSize&0x1ff)!=0 && super.contains(val);  
    }
    @Override public boolean contains(char val){
      return (modCountAndSize&0x1ff)!=0 && super.contains(val);
    }
    @Override public boolean contains(int val){
      return (modCountAndSize&0x1ff)!=0 && super.contains(val);
    }
    @Override public boolean contains(long val){
      return (modCountAndSize&0x1ff)!=0 && super.contains(val);
    }
    @Override public boolean contains(float val){
      return (modCountAndSize&0x1ff)!=0 && super.contains(val);
    }
    @Override public boolean contains(double val){
      return (modCountAndSize&0x1ff)!=0 && super.contains(val);
    }
    @Override public boolean contains(Object val){
      return (modCountAndSize&0x1ff)!=0 && super.contains(val);
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
      final int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0 && super.removeVal(val)){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(float val){
      final int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0 && super.removeVal(val)){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(double val){
      final int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0 && super.removeVal(val)){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean remove(Object val){
      final int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0 && super.remove(val)){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public void writeExternal(ObjectOutput oos) throws IOException{
      final int modCountAndSize=this.modCountAndSize;
      try{
        int size;
        oos.writeShort(size=modCountAndSize&0x1ff);
        if(size!=0){
          long word;
          oos.writeLong(word=word2);
          if((size-=Long.bitCount(word))!=0){
            oos.writeLong(word=word3);
            if((size-=Long.bitCount(word))!=0){
              oos.writeLong(word=word1);
              if((size-Long.bitCount(word))!=0){
                oos.writeLong(word0);
              }
	          }
          }
        }
      }finally{
        CheckedCollection.checkModCount(modCountAndSize,this.modCountAndSize);
      }
    }
    @Override public void readExternal(ObjectInput ois) throws IOException{
      int size;
      this.modCountAndSize=size=ois.readUnsignedShort();
      if(size!=0){
        long word;
        word2=word=ois.readLong();
        if((size-=Long.bitCount(word))!=0){
          word3=word=ois.readLong();
          if((size-=Long.bitCount(word))!=0){
            word1=word=ois.readLong();
            if((size-Long.bitCount(word))!=0){
              word0=ois.readLong();
            }
          }
        }
      }
    }
    @Override public OmniIterator.OfByte iterator(){
      final int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        int offset;
        if((offset=Long.numberOfTrailingZeros(word0))==64){
          if((offset+=Long.numberOfTrailingZeros(word1))==128){
            if((offset+=Long.numberOfTrailingZeros(word2))==192){
              offset+=Long.numberOfTrailingZeros(word3);
            }
          }
        }
        return new CheckedAscendingItr(this,modCountAndSize,128<<8|((offset-128)&0xff)); 
      }
      return EmptyView.EMPTY_ITR;
    }
    @Override public OmniIterator.OfByte descendingIterator(){
      final int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        int offset;
        if((offset=Long.numberOfLeadingZeros(word3))==64){
          if((offset+=Long.numberOfLeadingZeros(word2))==128){
            if((offset+=Long.numberOfLeadingZeros(word1))==192){
              offset+=Long.numberOfLeadingZeros(word0);
            }
          }
        }
        return new CheckedDescendingItr(this,modCountAndSize,128<<8|((127-offset)&0xff));
      }
      return EmptyView.EMPTY_ITR;
    }
    @Override public byte[] toByteArray(){
      final int size;
      if((size=this.modCountAndSize&0x1ff)!=0){
        final byte[] dst;
        uncheckedToArray(size,dst=new byte[size]);
        return dst;
      }
      return OmniArray.OfByte.DEFAULT_ARR;
    }
    @Override public short[] toShortArray(){
      final int size;
      if((size=this.modCountAndSize&0x1ff)!=0){
        final short[] dst;
        uncheckedToArray(size,dst=new short[size]);
        return dst;
      }
      return OmniArray.OfShort.DEFAULT_ARR;
    }
    @Override public int[] toIntArray(){
      final int size;
      if((size=this.modCountAndSize&0x1ff)!=0){
        final int[] dst;
        uncheckedToArray(size,dst=new int[size]);
        return dst;
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
    @Override public long[] toLongArray(){
      final int size;
      if((size=this.modCountAndSize&0x1ff)!=0){
        final long[] dst;
        uncheckedToArray(size,dst=new long[size]);
        return dst;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override public float[] toFloatArray(){
      final int size;
      if((size=this.modCountAndSize&0x1ff)!=0){
        final float[] dst;
        uncheckedToArray(size,dst=new float[size]);
        return dst;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override public double[] toDoubleArray(){
      final int size;
      if((size=this.modCountAndSize&0x1ff)!=0){
        final double[] dst;
        uncheckedToArray(size,dst=new double[size]);
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override public Byte[] toArray(){
      final int size;
      if((size=this.modCountAndSize&0x1ff)!=0){
        final Byte[] dst;
        uncheckedToArray(size,dst=new Byte[size]);
        return dst;
      }
      return OmniArray.OfByte.DEFAULT_BOXED_ARR;
    }
    @Override public <T> T[] toArray(T[] dst){
      final int size;
      if((size=this.modCountAndSize&0x1ff)!=0){
        uncheckedToArray(size,dst=OmniArray.uncheckedArrResize(size,dst));
      }else if(dst.length!=0){
        dst[0]=null;
      }
      return dst;
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      final int modCountAndSize=this.modCountAndSize;
      final int size;
      final T[] dst;
      try{
        dst=arrConstructor.apply(size=modCountAndSize&0x1ff);
      }finally{
        CheckedCollection.checkModCount(modCountAndSize,this.modCountAndSize);
      }
      if(size!=0){
        uncheckedToArray(size,dst);
      }
      return dst;
    }
    abstract void uncheckedToArray(int size,Object[] dst);
    abstract void uncheckedToArray(int size,Byte[] dst);
    abstract void uncheckedToArray(int size,byte[] dst);
    abstract void uncheckedToArray(int size,short[] dst);
    abstract void uncheckedToArray(int size,int[] dst);
    abstract void uncheckedToArray(int size,long[] dst);
    abstract void uncheckedToArray(int size,float[] dst);
    abstract void uncheckedToArray(int size,double[] dst);
    public static class Ascending extends Checked implements Cloneable{
      private static final long serialVersionUID=1L;
      public Ascending(){
        super();
      }
      Ascending(int size,long word0,long word1,long word2,long word3){
        super(size,word0,word1,word2,word3);
      }
      public Ascending(ByteSetImpl that){
        super(that);
      }
      public Ascending(ByteSetImpl.Checked that){
        super(that);
      }
      public Ascending(Collection<? extends Byte> that){
        super(that);
      }
      public Ascending(OmniCollection.OfRef<? extends Byte> that){
        super(that);
      }
      public Ascending(OmniCollection.OfBoolean that){
        super(that);
      }
      public Ascending(OmniCollection.OfByte that){
        super(that);
      }
      public Ascending(OmniCollection.ByteOutput<?> that){
        super(that);
      }
      @Override void uncheckedToArray(int size,Object[] dst){
        int offset=Byte.MAX_VALUE;
        long word=this.word3;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              return;
            }
          }
        }while(--offset!=63);
        word=this.word2;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              return;
            }
          }
        }while(--offset!=-1);
        word=this.word1;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              return;
            }
          }
        }while(--offset!=-65);
        for(word=this.word0;;--offset){
          if((word&(1L<<offset))!=0){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              return;
            }
          }
        }
      }
      @Override void uncheckedToArray(int size,Byte[] dst){
        int offset=Byte.MAX_VALUE;
        long word=this.word3;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              return;
            }
          }
        }while(--offset!=63);
        word=this.word2;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              return;
            }
          }
        }while(--offset!=-1);
        word=this.word1;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              return;
            }
          }
        }while(--offset!=-65);
        for(word=this.word0;;--offset){
          if((word&(1L<<offset))!=0){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              return;
            }
          }
        }
      }
      @Override void uncheckedToArray(int size,byte[] dst){
        int offset=Byte.MAX_VALUE;
        long word=this.word3;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(byte)(offset);
            if(size==0){
              return;
            }
          }
        }while(--offset!=63);
        word=this.word2;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(byte)(offset);
            if(size==0){
              return;
            }
          }
        }while(--offset!=-1);
        word=this.word1;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(byte)(offset);
            if(size==0){
              return;
            }
          }
        }while(--offset!=-65);
        for(word=this.word0;;--offset){
          if((word&(1L<<offset))!=0){
            dst[--size]=(byte)(offset);
            if(size==0){
              return;
            }
          }
        }
      }
      @Override void uncheckedToArray(int size,short[] dst){
        int offset=Byte.MAX_VALUE;
        long word=this.word3;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(short)(offset);
            if(size==0){
              return;
            }
          }
        }while(--offset!=63);
        word=this.word2;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(short)(offset);
            if(size==0){
              return;
            }
          }
        }while(--offset!=-1);
        word=this.word1;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(short)(offset);
            if(size==0){
              return;
            }
          }
        }while(--offset!=-65);
        for(word=this.word0;;--offset){
          if((word&(1L<<offset))!=0){
            dst[--size]=(short)(offset);
            if(size==0){
              return;
            }
          }
        }
      }
      @Override void uncheckedToArray(int size,int[] dst){
        int offset=Byte.MAX_VALUE;
        long word=this.word3;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(int)(offset);
            if(size==0){
              return;
            }
          }
        }while(--offset!=63);
        word=this.word2;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(int)(offset);
            if(size==0){
              return;
            }
          }
        }while(--offset!=-1);
        word=this.word1;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(int)(offset);
            if(size==0){
              return;
            }
          }
        }while(--offset!=-65);
        for(word=this.word0;;--offset){
          if((word&(1L<<offset))!=0){
            dst[--size]=(int)(offset);
            if(size==0){
              return;
            }
          }
        }
      }
      @Override void uncheckedToArray(int size,long[] dst){
        long offset=Byte.MAX_VALUE;
        long word=this.word3;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(long)(offset);
            if(size==0){
              return;
            }
          }
        }while(--offset!=63);
        word=this.word2;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(long)(offset);
            if(size==0){
              return;
            }
          }
        }while(--offset!=-1);
        word=this.word1;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(long)(offset);
            if(size==0){
              return;
            }
          }
        }while(--offset!=-65);
        for(word=this.word0;;--offset){
          if((word&(1L<<offset))!=0){
            dst[--size]=(long)(offset);
            if(size==0){
              return;
            }
          }
        }
      }
      @Override void uncheckedToArray(int size,float[] dst){
        int offset=Byte.MAX_VALUE;
        long word=this.word3;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(float)(offset);
            if(size==0){
              return;
            }
          }
        }while(--offset!=63);
        word=this.word2;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(float)(offset);
            if(size==0){
              return;
            }
          }
        }while(--offset!=-1);
        word=this.word1;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(float)(offset);
            if(size==0){
              return;
            }
          }
        }while(--offset!=-65);
        for(word=this.word0;;--offset){
          if((word&(1L<<offset))!=0){
            dst[--size]=(float)(offset);
            if(size==0){
              return;
            }
          }
        }
      }
      @Override void uncheckedToArray(int size,double[] dst){
        int offset=Byte.MAX_VALUE;
        long word=this.word3;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(double)(offset);
            if(size==0){
              return;
            }
          }
        }while(--offset!=63);
        word=this.word2;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(double)(offset);
            if(size==0){
              return;
            }
          }
        }while(--offset!=-1);
        word=this.word1;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(double)(offset);
            if(size==0){
              return;
            }
          }
        }while(--offset!=-65);
        for(word=this.word0;;--offset){
          if((word&(1L<<offset))!=0){
            dst[--size]=(double)(offset);
            if(size==0){
              return;
            }
          }
        }
      }
      @Override public Object clone(){
        return new Ascending(this);
      }
      @Override public OmniNavigableSet.OfByte descendingSet(){
        //TODO
        return null;
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
        //TODO
        return null;
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,boolean fromInclusive,byte toElement,boolean toInclusive){
        //TODO
        return null;
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement){
        //TODO
        return null;
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
        //TODO
        return null;
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
        //TODO
        return null;
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement,boolean inclusive){
        //TODO
        return null;
      }
      @Override void uncheckedForEach(int size,ByteConsumer action){
        int valOffset=Byte.MIN_VALUE;
        for(long word=this.word0;;){
          if((word&(1L<<valOffset))!=0){
            action.accept((byte)valOffset);
            if(--size==0){
              return;
            }
          }
          if(++valOffset==-64){
            break;
          }
        }
        for(long word=this.word1;;){
          if((word&(1L<<valOffset))!=0){
            action.accept((byte)valOffset);
            if(--size==0){
              return;
            }
          }
          if(++valOffset==0){
            break;
          }
        }
        for(long word=this.word2;;){
          if((word&(1L<<valOffset))!=0){
            action.accept((byte)valOffset);
            if(--size==0){
              return;
            }
          }
          if(++valOffset==64){
            break;
          }
        }
        for(long word=this.word3;;++valOffset){
          if((word&(1L<<valOffset))!=0){
            action.accept((byte)valOffset);
            if(--size==0){
              return;
            }
          }
        }
      }
      @Override int uncheckedToString(int size,byte[] buffer){
        int bufferOffset=0;
        done:for(int valOffset=Byte.MIN_VALUE;;){
          long word=this.word0;
          do{
            if((word&(1L<<valOffset))!=0){
              bufferOffset=ToStringUtil.getStringShort(valOffset,buffer,++bufferOffset);
              if(--size==0){
                break done;
              }
              buffer[bufferOffset]=',';
              buffer[++bufferOffset]=' ';
            }
          }while(++valOffset!=-64);
          word=this.word1;
          do{
            if((word&(1L<<valOffset))!=0){
              bufferOffset=ToStringUtil.getStringShort(valOffset,buffer,++bufferOffset);
              if(--size==0){
                break done;
              }
              buffer[bufferOffset]=',';
              buffer[++bufferOffset]=' ';
            }
          }while(++valOffset!=0);
          word=this.word2;
          do{
            if((word&(1L<<valOffset))!=0){
              bufferOffset=ToStringUtil.getStringShort(valOffset,buffer,++bufferOffset);
              if(--size==0){
                break done;
              }
              buffer[bufferOffset]=',';
              buffer[++bufferOffset]=' ';
            }
          }while(++valOffset!=64);
          for(word=this.word3;;++valOffset){
            if((word&(1L<<valOffset))!=0){
              bufferOffset=ToStringUtil.getStringShort(valOffset,buffer,++bufferOffset);
              if(--size==0){
                break done;
              }
              buffer[bufferOffset]=',';
              buffer[++bufferOffset]=' ';
            }
          }
        }
        return bufferOffset;
      }
    }
    public static class Descending extends Checked implements Cloneable{
      private static final long serialVersionUID=1L;
      public Descending(){
        super();
      }
      Descending(int size,long word0,long word1,long word2,long word3){
        super(size,word0,word1,word2,word3);
      }
      public Descending(ByteSetImpl that){
        super(that);
      }
      public Descending(ByteSetImpl.Checked that){
        super(that);
      }
      public Descending(Collection<? extends Byte> that){
        super(that);
      }
      public Descending(OmniCollection.OfRef<? extends Byte> that){
        super(that);
      }
      public Descending(OmniCollection.OfBoolean that){
        super(that);
      }
      public Descending(OmniCollection.OfByte that){
        super(that);
      }
      public Descending(OmniCollection.ByteOutput<?> that){
        super(that);
      }
      @Override public ByteComparator comparator(){
        return ByteComparator::descendingCompare;
      }
      @Override public int firstInt(){
        return super.lastInt();
      }
      @Override public int lastInt(){
        return super.firstInt();
      }
      @Override public int pollFirstInt(){
        return super.pollLastInt();
      }
      @Override public int pollLastInt(){
        return super.pollFirstInt();
      }
      @Override void uncheckedToArray(int size,Object[] dst){
        int offset=Byte.MIN_VALUE;
        long word=this.word0;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              return;
            }
          }
        }while(++offset!=-64);
        word=this.word1;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              return;
            }
          }
        }while(++offset!=0);
        word=this.word2;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              return;
            }
          }
        }while(++offset!=64);
        for(word=this.word3;;++offset){
          if((word&(1L<<offset))!=0){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              return;
            }
          }
        }
      }
      @Override void uncheckedToArray(int size,Byte[] dst){
        int offset=Byte.MIN_VALUE;
        long word=this.word0;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              return;
            }
          }
        }while(++offset!=-64);
        word=this.word1;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              return;
            }
          }
        }while(++offset!=0);
        word=this.word2;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              return;
            }
          }
        }while(++offset!=64);
        for(word=this.word3;;++offset){
          if((word&(1L<<offset))!=0){
            dst[--size]=(Byte)(byte)(offset);
            if(size==0){
              return;
            }
          }
        }
      }
      @Override void uncheckedToArray(int size,byte[] dst){
        int offset=Byte.MIN_VALUE;
        long word=this.word0;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(byte)(offset);
            if(size==0){
              return;
            }
          }
        }while(++offset!=-64);
        word=this.word1;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(byte)(offset);
            if(size==0){
              return;
            }
          }
        }while(++offset!=0);
        word=this.word2;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(byte)(offset);
            if(size==0){
              return;
            }
          }
        }while(++offset!=64);
        for(word=this.word3;;++offset){
          if((word&(1L<<offset))!=0){
            dst[--size]=(byte)(offset);
            if(size==0){
              return;
            }
          }
        }
      }
      @Override void uncheckedToArray(int size,short[] dst){
        int offset=Byte.MIN_VALUE;
        long word=this.word0;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(short)(offset);
            if(size==0){
              return;
            }
          }
        }while(++offset!=-64);
        word=this.word1;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(short)(offset);
            if(size==0){
              return;
            }
          }
        }while(++offset!=0);
        word=this.word2;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(short)(offset);
            if(size==0){
              return;
            }
          }
        }while(++offset!=64);
        for(word=this.word3;;++offset){
          if((word&(1L<<offset))!=0){
            dst[--size]=(short)(offset);
            if(size==0){
              return;
            }
          }
        }
      }
      @Override void uncheckedToArray(int size,int[] dst){
        int offset=Byte.MIN_VALUE;
        long word=this.word0;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(int)(offset);
            if(size==0){
              return;
            }
          }
        }while(++offset!=-64);
        word=this.word1;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(int)(offset);
            if(size==0){
              return;
            }
          }
        }while(++offset!=0);
        word=this.word2;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(int)(offset);
            if(size==0){
              return;
            }
          }
        }while(++offset!=64);
        for(word=this.word3;;++offset){
          if((word&(1L<<offset))!=0){
            dst[--size]=(int)(offset);
            if(size==0){
              return;
            }
          }
        }
      }
      @Override void uncheckedToArray(int size,long[] dst){
        long offset=Byte.MIN_VALUE;
        long word=this.word0;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(long)(offset);
            if(size==0){
              return;
            }
          }
        }while(++offset!=-64);
        word=this.word1;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(long)(offset);
            if(size==0){
              return;
            }
          }
        }while(++offset!=0);
        word=this.word2;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(long)(offset);
            if(size==0){
              return;
            }
          }
        }while(++offset!=64);
        for(word=this.word3;;++offset){
          if((word&(1L<<offset))!=0){
            dst[--size]=(long)(offset);
            if(size==0){
              return;
            }
          }
        }
      }
      @Override void uncheckedToArray(int size,float[] dst){
        int offset=Byte.MIN_VALUE;
        long word=this.word0;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(float)(offset);
            if(size==0){
              return;
            }
          }
        }while(++offset!=-64);
        word=this.word1;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(float)(offset);
            if(size==0){
              return;
            }
          }
        }while(++offset!=0);
        word=this.word2;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(float)(offset);
            if(size==0){
              return;
            }
          }
        }while(++offset!=64);
        for(word=this.word3;;++offset){
          if((word&(1L<<offset))!=0){
            dst[--size]=(float)(offset);
            if(size==0){
              return;
            }
          }
        }
      }
      @Override void uncheckedToArray(int size,double[] dst){
        int offset=Byte.MIN_VALUE;
        long word=this.word0;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(double)(offset);
            if(size==0){
              return;
            }
          }
        }while(++offset!=-64);
        word=this.word1;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(double)(offset);
            if(size==0){
              return;
            }
          }
        }while(++offset!=0);
        word=this.word2;
        do{
          if((word&(1L<<offset))!=0){
            dst[--size]=(double)(offset);
            if(size==0){
              return;
            }
          }
        }while(++offset!=64);
        for(word=this.word3;;++offset){
          if((word&(1L<<offset))!=0){
            dst[--size]=(double)(offset);
            if(size==0){
              return;
            }
          }
        }
      }
      @Override public byte higherByte(byte val){
        return super.lowerByte(val);
      }
      @Override public byte lowerByte(byte val){
        return super.higherByte(val);
      }
      @Override public byte byteCeiling(byte val){
        return super.byteFloor(val);
      }
      @Override public byte byteFloor(byte val){
        return super.byteCeiling(val);
      }
      @Override public short higherShort(short val){
        return super.lowerShort(val);
      }
      @Override public short lowerShort(short val){
        return super.higherShort(val);
      }
      @Override public short shortCeiling(short val){
        return super.shortFloor(val);
      }
      @Override public short shortFloor(short val){
        return super.shortCeiling(val);
      }
      @Override public int higherInt(int val){
        return super.lowerInt(val);
      }
      @Override public int lowerInt(int val){
        return super.higherInt(val);
      }
      @Override public int intCeiling(int val){
        return super.intFloor(val);
      }
      @Override public int intFloor(int val){
        return super.intCeiling(val);
      }
      @Override public long higherLong(long val){
        return super.lowerLong(val);
      }
      @Override public long lowerLong(long val){
        return super.higherLong(val);
      }
      @Override public long longCeiling(long val){
        return super.longFloor(val);
      }
      @Override public long longFloor(long val){
        return super.longCeiling(val);
      }
      @Override public float higherFloat(float val){
        return super.lowerFloat(val);
      }
      @Override public float lowerFloat(float val){
        return super.higherFloat(val);
      }
      @Override public float floatCeiling(float val){
        return super.floatFloor(val);
      }
      @Override public float floatFloor(float val){
        return super.floatCeiling(val);
      }
      @Override public double higherDouble(double val){
        return super.lowerDouble(val);
      }
      @Override public double lowerDouble(double val){
        return super.higherDouble(val);
      }
      @Override public double doubleCeiling(double val){
        return super.doubleFloor(val);
      }
      @Override public double doubleFloor(double val){
        return super.doubleCeiling(val);
      }
      @Override public Byte higher(byte val){
        return super.lower(val);
      }
      @Override public Byte lower(byte val){
        return super.higher(val);
      }
      @Override public Byte ceiling(byte val){
        return super.floor(val);
      }
      @Override public Byte floor(byte val){
        return super.ceiling(val);
      }
      @Override public Object clone(){
        return new Descending(this);
      }
      @Override public OmniIterator.OfByte iterator(){
        return super.descendingIterator();
      }
      @Override public OmniIterator.OfByte descendingIterator(){
        return super.iterator();
      }
      @Override public OmniNavigableSet.OfByte descendingSet(){
        //TODO
        return null;
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
        //TODO
        return null;
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,boolean fromInclusive,byte toElement,boolean toInclusive){
        //TODO
        return null;
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement){
        //TODO
        return null;
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
        //TODO
        return null;
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
        //TODO
        return null;
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement,boolean inclusive){
        //TODO
        return null;
      }
      @Override void uncheckedForEach(int size,ByteConsumer action){
        int valOffset=Byte.MAX_VALUE;
        for(long word=this.word3;;){
          if((word&(1L<<valOffset))!=0){
            action.accept((byte)valOffset);
            if(--size==0){
              return;
            }
          }
          if(--valOffset==63){
            break;
          }
        }
        for(long word=this.word2;;){
          if((word&(1L<<valOffset))!=0){
            action.accept((byte)valOffset);
            if(--size==0){
              return;
            }
          }
          if(--valOffset==-1){
            break;
          }
        }
        for(long word=this.word1;;){
          if((word&(1L<<valOffset))!=0){
            action.accept((byte)valOffset);
            if(--size==0){
              return;
            }
          }
          if(--valOffset==-65){
            break;
          }
        }
        for(long word=this.word0;;--valOffset){
          if((word&(1L<<valOffset))!=0){
            action.accept((byte)valOffset);
            if(--size==0){
              return;
            }
          }
        }
      }
      @Override int uncheckedToString(int size,byte[] buffer){
        int bufferOffset=0;
        done:for(int valOffset=Byte.MAX_VALUE;;){
          long word=this.word3;
          do{
            if((word&(1L<<valOffset))!=0){
              bufferOffset=ToStringUtil.getStringShort(valOffset,buffer,++bufferOffset);
              if(--size==0){
                break done;
              }
              buffer[bufferOffset]=',';
              buffer[++bufferOffset]=' ';
            }
          }while(--valOffset!=63);
          word=this.word2;
          do{
            if((word&(1L<<valOffset))!=0){
              bufferOffset=ToStringUtil.getStringShort(valOffset,buffer,++bufferOffset);
              if(--size==0){
                break done;
              }
              buffer[bufferOffset]=',';
              buffer[++bufferOffset]=' ';
            }
          }while(--valOffset!=-1);
          word=this.word1;
          do{
            if((word&(1L<<valOffset))!=0){
              bufferOffset=ToStringUtil.getStringShort(valOffset,buffer,++bufferOffset);
              if(--size==0){
                break done;
              }
              buffer[bufferOffset]=',';
              buffer[++bufferOffset]=' ';
            }
          }while(--valOffset!=-65);
          for(word=this.word0;;--valOffset){
            if((word&(1L<<valOffset))!=0){
              bufferOffset=ToStringUtil.getStringShort(valOffset,buffer,++bufferOffset);
              if(--size==0){
                break done;
              }
              buffer[bufferOffset]=',';
              buffer[++bufferOffset]=' ';
            }
          }
        }
        return bufferOffset;
      }
    }
  }
  private static class UncheckedDescendingItr extends UncheckedAscendingItr{
    private UncheckedDescendingItr(ByteSetImpl root,int offset,int numLeft){
      super(root,offset,numLeft);
    }
    @Override public Object clone(){
      return new UncheckedDescendingItr(root,offset,numLeft);
    }
    @Override public byte nextByte(){
      final int ret;
      this.offset=root.getThisOrLower((ret=this.offset)-1);
      --numLeft;
      return (byte)ret;
    }
    @Override public void forEachRemaining(ByteConsumer action){
      final int numLeft;
      if((numLeft=this.numLeft)!=0){
        root.forEachDescendingHelper(numLeft,this.offset,action);
        this.numLeft=0;
        this.offset=-129;
      }
    }
    @Override public void forEachRemaining(Consumer<? super Byte> action){
      final int numLeft;
      if((numLeft=this.numLeft)!=0){
        root.forEachDescendingHelper(numLeft,this.offset,action::accept);
        this.numLeft=0;
        this.offset=-129;
      }
    }
    @Override public void remove(){
      int offset;
      final var root=this.root;
      switch(((offset=this.offset+1))>>6){
        case -2:
          int tail0s;
          long word;
          if((tail0s=Long.numberOfTrailingZeros((word=root.word0)&(-1L<<(offset))))!=64){
            root.word0=word&(~(1L<<tail0s));
            return;
          }
          offset=0;
        case -1:
          if((tail0s=Long.numberOfTrailingZeros((word=root.word1)&(-1L<<(offset))))!=64){
            root.word1=word&(~(1L<<tail0s));
            return;
          }
          offset=0;
        case 0:
          if((tail0s=Long.numberOfTrailingZeros((word=root.word2)&(-1L<<(offset))))!=64){
            root.word2=word&(~(1L<<tail0s));
            return;
          }
          offset=0;
        default:
          root.word3=(word=root.word3)&(~(1L<<(Long.numberOfTrailingZeros(word&(-1L<<(offset))))));
      }
    }
  }
  private static class CheckedDescendingItr extends CheckedAscendingItr{
    private CheckedDescendingItr(ByteSetImpl.Checked root,int modCountAndNumLeft,int lastRetAndOffset){
      super(root,modCountAndNumLeft,lastRetAndOffset);
    }
    @Override public Object clone(){
      return new CheckedDescendingItr(root,modCountAndNumLeft,lastRetAndOffset);
    }
    @Override public byte nextByte(){
      final ByteSetImpl.Checked root;
      final int modCountAndNumLeft;
      CheckedCollection.checkModCount((modCountAndNumLeft=this.modCountAndNumLeft)>>>9,((root=this.root).modCountAndSize)>>>9);
      if((modCountAndNumLeft&0x1ff)!=0){
        this.modCountAndNumLeft=modCountAndNumLeft-1;
        final byte ret;
        this.lastRetAndOffset=((ret=(byte)(this.lastRetAndOffset&0xff))<<8)|(0xff&((ByteSetImpl)root).getThisOrLower(ret-1));
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override void uncheckedForEachRemaining(int modCountAndNumLeft,int numLeft,ByteConsumer action){
      final int lastRetAndOffset;
      int offset=(byte)((lastRetAndOffset=this.lastRetAndOffset)&0xff);
      final var root=this.root;
      int lastRet=0;
      try{
        done:switch(offset>>6){
          case 1:
            for(long word=root.word3;;){
              if((word&(1L<<offset))!=0){
                action.accept((byte)(lastRet=offset));
                if(--numLeft==0){
                  break done;
                }
              }
              if(--offset==63){
                break;
              }
            }
          case 0:
            for(long word=root.word2;;){
              if((word&(1L<<offset))!=0){
                action.accept((byte)(lastRet=offset));
                if(--numLeft==0){
                  break done;
                }
              }
              if(--offset==-1){
                break;
              }
            }
          case -1:
            for(long word=root.word1;;){
              if((word&(1L<<offset))!=0){
                action.accept((byte)(lastRet=offset));
                if(--numLeft==0){
                  break done;
                }
              }
              if(--offset==-65){
                break;
              }
            }
          default:
            for(long word=root.word0;;--offset){
              if((word&(1L<<offset))!=0){
                action.accept((byte)(lastRet=offset));
                if(--numLeft==0){
                  break done;
                }
              }
            }
        }
      }finally{
        CheckedCollection.checkModCount(modCountAndNumLeft>>>9,root.modCountAndSize>>>9,lastRetAndOffset,this.lastRetAndOffset);
      }
      this.lastRetAndOffset=lastRet<<8;
    }
  }
  private static class CheckedAscendingItr extends AbstractByteItr{
    transient final ByteSetImpl.Checked root;
    transient int modCountAndNumLeft;
    transient int lastRetAndOffset;
    private CheckedAscendingItr(ByteSetImpl.Checked root,int modCountAndNumLeft,int lastRetAndOffset){
      this.root=root;
      this.modCountAndNumLeft=modCountAndNumLeft;
      this.lastRetAndOffset=lastRetAndOffset;
    }
    @Override public Object clone(){
      return new CheckedAscendingItr(root,modCountAndNumLeft,lastRetAndOffset);
    }
    @Override public boolean hasNext(){
      return (this.modCountAndNumLeft&0x1ff)>0;
    }
    @Override public byte nextByte(){
      final ByteSetImpl.Checked root;
      final int modCountAndNumLeft;
      CheckedCollection.checkModCount((modCountAndNumLeft=this.modCountAndNumLeft)>>>9,((root=this.root).modCountAndSize)>>>9);
      if((modCountAndNumLeft&0x1ff)!=0){
        this.modCountAndNumLeft=modCountAndNumLeft-1;
        final byte ret;
        this.lastRetAndOffset=((ret=(byte)(this.lastRetAndOffset&0xff))<<8)|(0xff&((ByteSetImpl)root).getThisOrHigher(ret+1));
        return ret;
      }
      throw new NoSuchElementException();
    }
    @Override public void remove(){
      final int lastRet;
      final int lastRetAndOffset;
      if((lastRet=(lastRetAndOffset=this.lastRetAndOffset)>>8)!=128){
        final ByteSetImpl.Checked root;
        final int modCountAndNumLeft;
        final int modCountAndSize;
        CheckedCollection.checkModCount((modCountAndNumLeft=this.modCountAndNumLeft)>>>9,(modCountAndSize=(root=this.root).modCountAndSize)>>>9);
        root.modCountAndSize=modCountAndSize+((1<<9)-1);
        this.modCountAndNumLeft=modCountAndNumLeft+(1<<9);
        switch(lastRet>>6){
          case -2:
            root.word0&=(~(1L<<lastRet));
            break;
          case -1:
            root.word1&=(~(1L<<lastRet));
            break;
          case 0:
            root.word2&=(~(1L<<lastRet));
            break;
          default:
            root.word3&=(~(1L<<lastRet));
        }
        this.lastRetAndOffset=(128<<8)|(lastRetAndOffset&0xff);
        return;
      }
      throw new IllegalStateException();
    }
    void uncheckedForEachRemaining(int modCountAndNumLeft,int numLeft,ByteConsumer action){
      final int lastRetAndOffset;
      int offset=(byte)((lastRetAndOffset=this.lastRetAndOffset)&0xff);
      final var root=this.root;
      int lastRet=0;
      try{
        done:switch(offset>>6){
          case -2:
            for(long word=root.word0;;){
              if((word&(1L<<offset))!=0){
                action.accept((byte)(lastRet=offset));
                if(--numLeft==0){
                  break done;
                }
              }
              if(++offset==-64){
                break;
              }
            }
          case -1:
            for(long word=root.word1;;){
              if((word&(1L<<offset))!=0){
                action.accept((byte)(lastRet=offset));
                if(--numLeft==0){
                  break done;
                }
              }
              if(++offset==0){
                break;
              }
            }
          case 0:
            for(long word=root.word2;;){
              if((word&(1L<<offset))!=0){
                action.accept((byte)(lastRet=offset));
                if(--numLeft==0){
                  break done;
                }
              }
              if(++offset==64){
                break;
              }
            }
          default:
            for(long word=root.word3;;++offset){
              if((word&(1L<<offset))!=0){
                action.accept((byte)(lastRet=offset));
                if(--numLeft==0){
                  break done;
                }
              }
            }
        }
      }finally{
        CheckedCollection.checkModCount(modCountAndNumLeft>>>9,root.modCountAndSize>>>9,lastRetAndOffset,this.lastRetAndOffset);
      }
      this.lastRetAndOffset=lastRet<<8;
    }
    @Override public void forEachRemaining(ByteConsumer action){
      final int modCountAndNumLeft;
      final int numLeft;
      if((numLeft=(modCountAndNumLeft=this.modCountAndNumLeft)&0x1ff)!=0){
        uncheckedForEachRemaining(modCountAndNumLeft,numLeft,action);
      }
    }
    @Override public void forEachRemaining(Consumer<? super Byte> action){
      final int modCountAndNumLeft;
      final int numLeft;
      if((numLeft=(modCountAndNumLeft=this.modCountAndNumLeft)&0x1ff)!=0){
        uncheckedForEachRemaining(modCountAndNumLeft,numLeft,action::accept);
      }
    }
  }
  private static class UncheckedAscendingItr extends AbstractByteItr{
    transient final ByteSetImpl root;
    transient int offset;
    transient int numLeft;
    private UncheckedAscendingItr(ByteSetImpl root,int offset,int numLeft){
      this.root=root;
      this.offset=offset;
      this.numLeft=numLeft;
    }
    @Override public Object clone(){
      return new UncheckedAscendingItr(root,offset,numLeft);
    }
    @Override public boolean hasNext(){
      return this.numLeft>0;
    }
    @Override public byte nextByte(){
      final int ret;
      this.offset=root.getThisOrHigher((ret=this.offset)+1);
      --numLeft;
      return (byte)ret;
    }
    @Override public void forEachRemaining(ByteConsumer action){
      final int numLeft;
      if((numLeft=this.numLeft)!=0){
        root.forEachAscendingHelper(numLeft,this.offset,action);
        this.numLeft=0;
        this.offset=128;
      }
    }
    @Override public void forEachRemaining(Consumer<? super Byte> action){
      final int numLeft;
      if((numLeft=this.numLeft)!=0){
        root.forEachAscendingHelper(numLeft,this.offset,action::accept);
        this.numLeft=0;
        this.offset=128;
      }
    }
    @Override public void remove(){
      int offset;
      final var root=this.root;
      switch(((offset=this.offset)-1)>>6){
        case 1:
          int lead0s;
          long word;
          if((lead0s=Long.numberOfLeadingZeros((word=root.word3)&(-1L>>>(-offset))))!=64){
            root.word3=word&(~(Long.MIN_VALUE>>>lead0s));
            return;
          }
          offset=0;
        case 0:
          if((lead0s=Long.numberOfLeadingZeros((word=root.word2)&(-1L>>>(-offset))))!=64){
            root.word2=word&(~(Long.MIN_VALUE>>>lead0s));
            return;
          }
          offset=0;
        case -1:
          if((lead0s=Long.numberOfLeadingZeros((word=root.word1)&(-1L>>>(-offset))))!=64){
            root.word1=word&(~(Long.MIN_VALUE>>>lead0s));
            return;
          }
          offset=0;
        default:
          root.word0=(word=root.word0)&(~(Long.MIN_VALUE>>>(Long.numberOfLeadingZeros(word&(-1L>>>(-offset))))));
      }
    }
  }
}
