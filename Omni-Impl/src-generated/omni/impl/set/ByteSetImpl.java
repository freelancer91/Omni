package omni.impl.set;
import omni.impl.AbstractByteItr;
import omni.function.ByteConsumer;
import omni.function.BytePredicate;
import omni.impl.CheckedCollection;
import java.util.function.Consumer;
import java.util.function.Predicate;
import omni.api.OmniIterator;
import omni.util.OmniArray;
import java.util.function.IntFunction;
import java.util.NoSuchElementException;
import omni.util.ToStringUtil;
import omni.api.OmniCollection;
import java.util.Collection;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import omni.function.ByteComparator;
import omni.api.OmniNavigableSet;
public abstract class ByteSetImpl extends AbstractByteSet implements Cloneable{
  transient long word0;
  transient long word1;
  transient long word2;
  transient long word3;
  //TODO equals
  private ByteSetImpl(long word0,long word1,long word2,long word3){
    super();
    this.word0=word0;
    this.word1=word1;
    this.word2=word2;
    this.word3=word3;
  }
  private ByteSetImpl(ByteSetImpl.Checked that){
    super();
    if((that.modCountAndSize&0x1ff)!=0){
      this.word0=that.word0;
      this.word1=that.word1;
      this.word2=that.word2;
      this.word3=that.word3;
    }
  }
  private ByteSetImpl(ByteSetImpl.Unchecked that){
    super();
    this.word0=that.word0;
    this.word1=that.word1;
    this.word2=that.word2;
    this.word3=that.word3;
  }
  private ByteSetImpl(){
    super();
  }
  private boolean uncheckedAdd(byte val){
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
  @Override public boolean add(byte val){
    return uncheckedAdd(val);
  }
  @Override public boolean add(boolean val){
    long word;
    if((word=this.word2)!=(word|=(val?0b10:0b01))){
      this.word2=word;
      return true;
    }
    return false;
  }
  @Override public boolean contains(boolean val){
    return (this.word2&(val?0b10:0b01))!=0;
  }
  @Override public boolean contains(byte val){
    switch(val>>6){
    case -2:
      return (this.word0&(1L<<val))!=0;
    case -1:
      return (this.word1&(1L<<val))!=0;
    case 0:
      return (this.word2&(1L<<val))!=0;
    default:
      return (this.word3&(1L<<val))!=0;
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
  @Override public boolean contains(long val){
    final int v;
    if((v=(byte)val)==val){
      switch(v>>6){
      case -2:
        return (this.word0&(1L<<v))!=0;
      case -1:
        return (this.word1&(1L<<v))!=0;
      case 0:
        return (this.word2&(1L<<v))!=0;
      default:
        return (this.word3&(1L<<v))!=0;
      }
    }
    return false;
  }
  @Override public boolean contains(float val){
    final int v;
    if((v=(byte)val)==val){
      switch(v>>6){
      case -2:
        return (this.word0&(1L<<v))!=0;
      case -1:
        return (this.word1&(1L<<v))!=0;
      case 0:
        return (this.word2&(1L<<v))!=0;
      default:
        return (this.word3&(1L<<v))!=0;
      }
    }
    return false;
  }
  @Override public boolean contains(double val){
    final int v;
    if((v=(byte)val)==val){
      switch(v>>6){
      case -2:
        return (this.word0&(1L<<v))!=0;
      case -1:
        return (this.word1&(1L<<v))!=0;
      case 0:
        return (this.word2&(1L<<v))!=0;
      default:
        return (this.word3&(1L<<v))!=0;
      }
    }
    return false;
  }
  @Override public boolean contains(int val){
    switch(val>>6){
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
  @Override public boolean removeVal(boolean val){
    long word;
    if((word=this.word2)!=(word&=(val?(~0b10):(~0b01)))){
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
  @Override public boolean removeVal(double val){
    final int v;
    if((v=(byte)val)==val){
      switch(v>>6){
      case -2:
        long word;
        if((word=this.word0)!=(word&=(1L<<v))){
          this.word0=word;
          return true;
        }
        break;
      case -1:
        if((word=this.word1)!=(word&=(1L<<v))){
          this.word1=word;
          return true;
        }
        break;
      case 0:
        if((word=this.word2)!=(word&=(1L<<v))){
          this.word2=word;
          return true;
        }
        break;
      default:
        if((word=this.word3)!=(word&=(1L<<v))){
          this.word3=word;
          return true;
        }
      }
    }
    return false;
  }
  @Override public boolean removeVal(float val){
    final int v;
    if((v=(byte)val)==val){
      switch(v>>6){
      case -2:
        long word;
        if((word=this.word0)!=(word&=(1L<<v))){
          this.word0=word;
          return true;
        }
        break;
      case -1:
        if((word=this.word1)!=(word&=(1L<<v))){
          this.word1=word;
          return true;
        }
        break;
      case 0:
        if((word=this.word2)!=(word&=(1L<<v))){
          this.word2=word;
          return true;
        }
        break;
      default:
        if((word=this.word3)!=(word&=(1L<<v))){
          this.word3=word;
          return true;
        }
      }
    }
    return false;
  }
  @Override public boolean removeVal(long val){
    final int v;
    if((v=(byte)val)==val){
      switch(v>>6){
      case -2:
        long word;
        if((word=this.word0)!=(word&=(1L<<v))){
          this.word0=word;
          return true;
        }
        break;
      case -1:
        if((word=this.word1)!=(word&=(1L<<v))){
          this.word1=word;
          return true;
        }
        break;
      case 0:
        if((word=this.word2)!=(word&=(1L<<v))){
          this.word2=word;
          return true;
        }
        break;
      default:
        if((word=this.word3)!=(word&=(1L<<v))){
          this.word3=word;
          return true;
        }
      }
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
  private int uncheckedRemoveFirst(){
    int tail0s;
    long word;
    if((tail0s=Long.numberOfTrailingZeros(word=this.word0))!=64){
      this.word0=word&(~(1L<<tail0s));
      return (Byte.MIN_VALUE+tail0s);
    }
    if((tail0s=Long.numberOfTrailingZeros(word=this.word1))!=64){
      this.word1=word&(~(1L<<tail0s));
      return (tail0s-64);
    }
    if((tail0s=Long.numberOfTrailingZeros(word=this.word2))!=64){
      this.word2=word&(~(1L<<tail0s));
      return tail0s;
    }
    this.word3=(word=this.word3)&(~(1L<<(tail0s=Long.numberOfTrailingZeros(word))));
    return (tail0s+64);
  }
  private int uncheckedRemoveLast(){
    int lead0s;
    long word;
    if((lead0s=Long.numberOfLeadingZeros(word=this.word3))!=64){
      this.word3=word&(~(Long.MIN_VALUE>>>lead0s));
      return (Byte.MAX_VALUE-lead0s);
    }
    if((lead0s=Long.numberOfLeadingZeros(word=this.word2))!=64){
      this.word2=word&(~(Long.MIN_VALUE>>>lead0s));
       return (63-lead0s);
    }
    if((lead0s=Long.numberOfLeadingZeros(word=this.word1))!=64){
      this.word1=word&(~(Long.MIN_VALUE>>>lead0s));
      return (-1-lead0s);
    }
    this.word0=(word=this.word0)&(~(Long.MIN_VALUE>>>(lead0s=Long.numberOfLeadingZeros(word))));
    return (-65-lead0s);
  }
  @Override public Byte lower(byte val){
    if(val>Byte.MIN_VALUE){
      int v;
      switch((v=((int)val)-1)>>6){
        case 1:
          int lead0s;
          if((lead0s=Long.numberOfLeadingZeros(this.word3<<-v))!=64){
            return (Byte)(byte)(127-lead0s);
          }
          v=0;
        case 0:
          if((lead0s=Long.numberOfLeadingZeros(this.word2<<-v))!=64){
            return (Byte)(byte)(63-lead0s);
          }
          v=0;
        case -1:
          if((lead0s=Long.numberOfLeadingZeros(this.word1<<-v))!=64){
            return (Byte)(byte)(-1-lead0s);
          }
          v=0;
        case -2:
          if((lead0s=Long.numberOfLeadingZeros(this.word0<<-v))!=64){
            return (Byte)(byte)(-65-lead0s);
          }
        default:
      }
    }  
    return null;
  }
  @Override public Byte floor(byte val){
    {
      switch(val>>6){
        case 1:
          int lead0s;
          if((lead0s=Long.numberOfLeadingZeros(this.word3<<-val))!=64){
            return (Byte)(byte)(127-lead0s);
          }
          val=0;
        case 0:
          if((lead0s=Long.numberOfLeadingZeros(this.word2<<-val))!=64){
            return (Byte)(byte)(63-lead0s);
          }
          val=0;
        case -1:
          if((lead0s=Long.numberOfLeadingZeros(this.word1<<-val))!=64){
            return (Byte)(byte)(-1-lead0s);
          }
          val=0;
        default:
          if((lead0s=Long.numberOfLeadingZeros(this.word0<<-val))!=64){
            return (Byte)(byte)(-65-lead0s);
          }
      }
    }  
    return null;
  }
  @Override public Byte higher(byte val){
    {
      int v;
      switch((v=((int)val)+1)>>6){
        case -2:
          int tail0s;
          if((tail0s=Long.numberOfTrailingZeros(this.word0>>>v))!=64){
            return (Byte)(byte)(tail0s-128);
          }
          v=0;
        case -1:
          if((tail0s=Long.numberOfTrailingZeros(this.word1>>>v))!=64){
            return (Byte)(byte)(tail0s-64);
          }
          v=0;
        case 0:
          if((tail0s=Long.numberOfTrailingZeros(this.word2>>>v))!=64){
            return (Byte)(byte)(tail0s);
          }
          v=0;
        case 1:
          if((tail0s=Long.numberOfTrailingZeros(this.word3>>>v))!=64){
            return (Byte)(byte)(tail0s+64);
          }
        default:
      }
    }
    return null;
  }
  @Override public Byte ceiling(byte val){
    {
      switch(val>>6){
        case -2:
          int tail0s;
          if((tail0s=Long.numberOfTrailingZeros(this.word0>>>val))!=64){
            return (Byte)(byte)(tail0s-128);
          }
          val=0;
        case -1:
          if((tail0s=Long.numberOfTrailingZeros(this.word1>>>val))!=64){
            return (Byte)(byte)(tail0s-64);
          }
          val=0;
        case 0:
          if((tail0s=Long.numberOfTrailingZeros(this.word2>>>val))!=64){
            return (Byte)(byte)(tail0s);
          }
          val=0;
        default:
          if((tail0s=Long.numberOfTrailingZeros(this.word3>>>val))!=64){
            return (Byte)(byte)(tail0s+64);
          }
      }
    }
    return null;
  }
  @Override public Byte pollFirst(){
    long word;
    int tail0s;
    if((tail0s=Long.numberOfTrailingZeros(word=this.word0))!=64){
      this.word0=word&(~(1L<<tail0s));
      return (Byte)(byte)(Byte.MIN_VALUE+tail0s);
    }
    if((tail0s=Long.numberOfTrailingZeros(word=this.word1))!=64){
      this.word1=word&(~(1L<<tail0s));
      return (Byte)(byte)(tail0s-64);
    }
    if((tail0s=Long.numberOfTrailingZeros(word=this.word2))!=64){
      this.word2=word&(~(1L<<tail0s));
      return (Byte)(byte)(tail0s);
    }
    if((tail0s=Long.numberOfTrailingZeros(word=this.word3))!=64){
      this.word3=word&(~(1L<<tail0s));
      return (Byte)(byte)(tail0s+64);
    }
    return null;
  }
  @Override public Byte pollLast(){
    long word;
    int lead0s;
    if((lead0s=Long.numberOfLeadingZeros(word=this.word3))!=64){
      this.word3=word&(~(Long.MIN_VALUE>>>lead0s));
      return (Byte)(byte)(Byte.MAX_VALUE-lead0s);
    }
    if((lead0s=Long.numberOfLeadingZeros(word=this.word2))!=64){
      this.word2=word&(~(Long.MIN_VALUE>>>lead0s));
      return (Byte)(byte)(63-lead0s);
    }
    if((lead0s=Long.numberOfLeadingZeros(word=this.word1))!=64){
      this.word1=word&(~(Long.MIN_VALUE>>>lead0s));
      return (Byte)(byte)(-1-lead0s);
    }
    if((lead0s=Long.numberOfLeadingZeros(word=this.word0))!=64){
      this.word0=word&(~(Long.MIN_VALUE>>>lead0s));
      return (Byte)(byte)(-65-lead0s); 
    }
    return null;
  }
  @Override public byte firstByte(){
    return (byte)(firstInt());
  }
  @Override public byte lastByte(){
    return (byte)(lastInt());
  }
  @Override public byte lowerByte(byte val){
    if(val>Byte.MIN_VALUE){
      int v;
      switch((v=((int)val)-1)>>6){
        case 1:
          int lead0s;
          if((lead0s=Long.numberOfLeadingZeros(this.word3<<-v))!=64){
            return (byte)(127-lead0s);
          }
          v=0;
        case 0:
          if((lead0s=Long.numberOfLeadingZeros(this.word2<<-v))!=64){
            return (byte)(63-lead0s);
          }
          v=0;
        case -1:
          if((lead0s=Long.numberOfLeadingZeros(this.word1<<-v))!=64){
            return (byte)(-1-lead0s);
          }
          v=0;
        case -2:
          if((lead0s=Long.numberOfLeadingZeros(this.word0<<-v))!=64){
            return (byte)(-65-lead0s);
          }
        default:
      }
    }  
    return Byte.MIN_VALUE;
  }
  @Override public byte byteFloor(byte val){
    {
      switch(val>>6){
        case 1:
          int lead0s;
          if((lead0s=Long.numberOfLeadingZeros(this.word3<<-val))!=64){
            return (byte)(127-lead0s);
          }
          val=0;
        case 0:
          if((lead0s=Long.numberOfLeadingZeros(this.word2<<-val))!=64){
            return (byte)(63-lead0s);
          }
          val=0;
        case -1:
          if((lead0s=Long.numberOfLeadingZeros(this.word1<<-val))!=64){
            return (byte)(-1-lead0s);
          }
          val=0;
        default:
          if((lead0s=Long.numberOfLeadingZeros(this.word0<<-val))!=64){
            return (byte)(-65-lead0s);
          }
      }
    }  
    return Byte.MIN_VALUE;
  }
  @Override public byte higherByte(byte val){
    {
      int v;
      switch((v=((int)val)+1)>>6){
        case -2:
          int tail0s;
          if((tail0s=Long.numberOfTrailingZeros(this.word0>>>v))!=64){
            return (byte)(tail0s-128);
          }
          v=0;
        case -1:
          if((tail0s=Long.numberOfTrailingZeros(this.word1>>>v))!=64){
            return (byte)(tail0s-64);
          }
          v=0;
        case 0:
          if((tail0s=Long.numberOfTrailingZeros(this.word2>>>v))!=64){
            return (byte)(tail0s);
          }
          v=0;
        case 1:
          if((tail0s=Long.numberOfTrailingZeros(this.word3>>>v))!=64){
            return (byte)(tail0s+64);
          }
        default:
      }
    }
    return Byte.MIN_VALUE;
  }
  @Override public byte byteCeiling(byte val){
    {
      switch(val>>6){
        case -2:
          int tail0s;
          if((tail0s=Long.numberOfTrailingZeros(this.word0>>>val))!=64){
            return (byte)(tail0s-128);
          }
          val=0;
        case -1:
          if((tail0s=Long.numberOfTrailingZeros(this.word1>>>val))!=64){
            return (byte)(tail0s-64);
          }
          val=0;
        case 0:
          if((tail0s=Long.numberOfTrailingZeros(this.word2>>>val))!=64){
            return (byte)(tail0s);
          }
          val=0;
        default:
          if((tail0s=Long.numberOfTrailingZeros(this.word3>>>val))!=64){
            return (byte)(tail0s+64);
          }
      }
    }
    return Byte.MIN_VALUE;
  }
  @Override public byte pollFirstByte(){
    long word;
    int tail0s;
    if((tail0s=Long.numberOfTrailingZeros(word=this.word0))!=64){
      this.word0=word&(~(1L<<tail0s));
      return (byte)(Byte.MIN_VALUE+tail0s);
    }
    if((tail0s=Long.numberOfTrailingZeros(word=this.word1))!=64){
      this.word1=word&(~(1L<<tail0s));
      return (byte)(tail0s-64);
    }
    if((tail0s=Long.numberOfTrailingZeros(word=this.word2))!=64){
      this.word2=word&(~(1L<<tail0s));
      return (byte)(tail0s);
    }
    if((tail0s=Long.numberOfTrailingZeros(word=this.word3))!=64){
      this.word3=word&(~(1L<<tail0s));
      return (byte)(tail0s+64);
    }
    return Byte.MIN_VALUE;
  }
  @Override public byte pollLastByte(){
    long word;
    int lead0s;
    if((lead0s=Long.numberOfLeadingZeros(word=this.word3))!=64){
      this.word3=word&(~(Long.MIN_VALUE>>>lead0s));
      return (byte)(Byte.MAX_VALUE-lead0s);
    }
    if((lead0s=Long.numberOfLeadingZeros(word=this.word2))!=64){
      this.word2=word&(~(Long.MIN_VALUE>>>lead0s));
      return (byte)(63-lead0s);
    }
    if((lead0s=Long.numberOfLeadingZeros(word=this.word1))!=64){
      this.word1=word&(~(Long.MIN_VALUE>>>lead0s));
      return (byte)(-1-lead0s);
    }
    if((lead0s=Long.numberOfLeadingZeros(word=this.word0))!=64){
      this.word0=word&(~(Long.MIN_VALUE>>>lead0s));
      return (byte)(-65-lead0s); 
    }
    return Byte.MIN_VALUE;
  }
  @Override public short firstShort(){
    return (short)(firstInt());
  }
  @Override public short lastShort(){
    return (short)(lastInt());
  }
  @Override public short lowerShort(short val){
    if(val>Byte.MIN_VALUE){
      int v;
      switch((v=((int)val)-1)>>6){
        default:
          v=127;
        case 1:
          int lead0s;
          if((lead0s=Long.numberOfLeadingZeros(this.word3<<-v))!=64){
            return (short)(127-lead0s);
          }
          v=0;
        case 0:
          if((lead0s=Long.numberOfLeadingZeros(this.word2<<-v))!=64){
            return (short)(63-lead0s);
          }
          v=0;
        case -1:
          if((lead0s=Long.numberOfLeadingZeros(this.word1<<-v))!=64){
            return (short)(-1-lead0s);
          }
          v=0;
        case -2:
          if((lead0s=Long.numberOfLeadingZeros(this.word0<<-v))!=64){
            return (short)(-65-lead0s);
          }
      }
    }  
    return Short.MIN_VALUE;
  }
  @Override public short shortFloor(short val){
    if(val>=Byte.MIN_VALUE)
    {
      switch(val>>6){
        default:
          val=127;
        case 1:
          int lead0s;
          if((lead0s=Long.numberOfLeadingZeros(this.word3<<-val))!=64){
            return (short)(127-lead0s);
          }
          val=0;
        case 0:
          if((lead0s=Long.numberOfLeadingZeros(this.word2<<-val))!=64){
            return (short)(63-lead0s);
          }
          val=0;
        case -1:
          if((lead0s=Long.numberOfLeadingZeros(this.word1<<-val))!=64){
            return (short)(-1-lead0s);
          }
          val=0;
        case -2:
          if((lead0s=Long.numberOfLeadingZeros(this.word0<<-val))!=64){
            return (short)(-65-lead0s);
          }
      }
    }  
    return Short.MIN_VALUE;
  }
  @Override public short higherShort(short val){
    if(val<Byte.MAX_VALUE)
    {
      int v;
      switch((v=((int)val)+1)>>6){
        default:
          v=-128;
        case -2:
          int tail0s;
          if((tail0s=Long.numberOfTrailingZeros(this.word0>>>v))!=64){
            return (short)(tail0s-128);
          }
          v=0;
        case -1:
          if((tail0s=Long.numberOfTrailingZeros(this.word1>>>v))!=64){
            return (short)(tail0s-64);
          }
          v=0;
        case 0:
          if((tail0s=Long.numberOfTrailingZeros(this.word2>>>v))!=64){
            return (short)(tail0s);
          }
          v=0;
        case 1:
          if((tail0s=Long.numberOfTrailingZeros(this.word3>>>v))!=64){
            return (short)(tail0s+64);
          }
      }
    }
    return Short.MIN_VALUE;
  }
  @Override public short shortCeiling(short val){
    if(val<=Byte.MAX_VALUE)
    {
      switch(val>>6){
        default:
          val=-128;
        case -2:
          int tail0s;
          if((tail0s=Long.numberOfTrailingZeros(this.word0>>>val))!=64){
            return (short)(tail0s-128);
          }
          val=0;
        case -1:
          if((tail0s=Long.numberOfTrailingZeros(this.word1>>>val))!=64){
            return (short)(tail0s-64);
          }
          val=0;
        case 0:
          if((tail0s=Long.numberOfTrailingZeros(this.word2>>>val))!=64){
            return (short)(tail0s);
          }
          val=0;
        case 1:
          if((tail0s=Long.numberOfTrailingZeros(this.word3>>>val))!=64){
            return (short)(tail0s+64);
          }
      }
    }
    return Short.MIN_VALUE;
  }
  @Override public short pollFirstShort(){
    long word;
    int tail0s;
    if((tail0s=Long.numberOfTrailingZeros(word=this.word0))!=64){
      this.word0=word&(~(1L<<tail0s));
      return (short)(Byte.MIN_VALUE+tail0s);
    }
    if((tail0s=Long.numberOfTrailingZeros(word=this.word1))!=64){
      this.word1=word&(~(1L<<tail0s));
      return (short)(tail0s-64);
    }
    if((tail0s=Long.numberOfTrailingZeros(word=this.word2))!=64){
      this.word2=word&(~(1L<<tail0s));
      return (short)(tail0s);
    }
    if((tail0s=Long.numberOfTrailingZeros(word=this.word3))!=64){
      this.word3=word&(~(1L<<tail0s));
      return (short)(tail0s+64);
    }
    return Short.MIN_VALUE;
  }
  @Override public short pollLastShort(){
    long word;
    int lead0s;
    if((lead0s=Long.numberOfLeadingZeros(word=this.word3))!=64){
      this.word3=word&(~(Long.MIN_VALUE>>>lead0s));
      return (short)(Byte.MAX_VALUE-lead0s);
    }
    if((lead0s=Long.numberOfLeadingZeros(word=this.word2))!=64){
      this.word2=word&(~(Long.MIN_VALUE>>>lead0s));
      return (short)(63-lead0s);
    }
    if((lead0s=Long.numberOfLeadingZeros(word=this.word1))!=64){
      this.word1=word&(~(Long.MIN_VALUE>>>lead0s));
      return (short)(-1-lead0s);
    }
    if((lead0s=Long.numberOfLeadingZeros(word=this.word0))!=64){
      this.word0=word&(~(Long.MIN_VALUE>>>lead0s));
      return (short)(-65-lead0s); 
    }
    return Short.MIN_VALUE;
  }
  @Override public int firstInt(){
    int tail0s;
    if((tail0s=Long.numberOfTrailingZeros(word0))!=64){
      return (Byte.MIN_VALUE+tail0s);
    }
    if((tail0s=Long.numberOfTrailingZeros(word1))!=64){
      return (tail0s-64);
    }
    if((tail0s=Long.numberOfTrailingZeros(word2))!=64){
      return tail0s;
    }
    return (Long.numberOfTrailingZeros(word3)+64);  
  }
  @Override public int lastInt(){
    int lead0s;
    if((lead0s=Long.numberOfLeadingZeros(word3))!=64){
      return (Byte.MAX_VALUE-lead0s);
    }
    if((lead0s=Long.numberOfLeadingZeros(word2))!=64){
      return (63-lead0s);
    }
    if((lead0s=Long.numberOfLeadingZeros(word1))!=64){
      return (-1-lead0s);
    }
    return (-65-Long.numberOfLeadingZeros(word0));  
  }
  @Override public int lowerInt(int val){
    if(val>Byte.MIN_VALUE){
      switch((--val)>>6){
        default:
          val=127;
        case 1:
          int lead0s;
          if((lead0s=Long.numberOfLeadingZeros(this.word3<<-val))!=64){
            return (int)(127-lead0s);
          }
          val=0;
        case 0:
          if((lead0s=Long.numberOfLeadingZeros(this.word2<<-val))!=64){
            return (int)(63-lead0s);
          }
          val=0;
        case -1:
          if((lead0s=Long.numberOfLeadingZeros(this.word1<<-val))!=64){
            return (int)(-1-lead0s);
          }
          val=0;
        case -2:
          if((lead0s=Long.numberOfLeadingZeros(this.word0<<-val))!=64){
            return (int)(-65-lead0s);
          }
      }
    }  
    return Integer.MIN_VALUE;
  }
  @Override public int intFloor(int val){
    if(val>=Byte.MIN_VALUE)
    {
      switch(val>>6){
        default:
          val=127;
        case 1:
          int lead0s;
          if((lead0s=Long.numberOfLeadingZeros(this.word3<<-val))!=64){
            return (int)(127-lead0s);
          }
          val=0;
        case 0:
          if((lead0s=Long.numberOfLeadingZeros(this.word2<<-val))!=64){
            return (int)(63-lead0s);
          }
          val=0;
        case -1:
          if((lead0s=Long.numberOfLeadingZeros(this.word1<<-val))!=64){
            return (int)(-1-lead0s);
          }
          val=0;
        case -2:
          if((lead0s=Long.numberOfLeadingZeros(this.word0<<-val))!=64){
            return (int)(-65-lead0s);
          }
      }
    }  
    return Integer.MIN_VALUE;
  }
  @Override public int higherInt(int val){
    if(val<Byte.MAX_VALUE)
    {
      switch((++val)>>6){
        default:
          val=-128;
        case -2:
          int tail0s;
          if((tail0s=Long.numberOfTrailingZeros(this.word0>>>val))!=64){
            return (int)(tail0s-128);
          }
          val=0;
        case -1:
          if((tail0s=Long.numberOfTrailingZeros(this.word1>>>val))!=64){
            return (int)(tail0s-64);
          }
          val=0;
        case 0:
          if((tail0s=Long.numberOfTrailingZeros(this.word2>>>val))!=64){
            return (int)(tail0s);
          }
          val=0;
        case 1:
          if((tail0s=Long.numberOfTrailingZeros(this.word3>>>val))!=64){
            return (int)(tail0s+64);
          }
      }
    }
    return Integer.MIN_VALUE;
  }
  @Override public int intCeiling(int val){
    if(val<=Byte.MAX_VALUE)
    {
      switch(val>>6){
        default:
          val=-128;
        case -2:
          int tail0s;
          if((tail0s=Long.numberOfTrailingZeros(this.word0>>>val))!=64){
            return (int)(tail0s-128);
          }
          val=0;
        case -1:
          if((tail0s=Long.numberOfTrailingZeros(this.word1>>>val))!=64){
            return (int)(tail0s-64);
          }
          val=0;
        case 0:
          if((tail0s=Long.numberOfTrailingZeros(this.word2>>>val))!=64){
            return (int)(tail0s);
          }
          val=0;
        case 1:
          if((tail0s=Long.numberOfTrailingZeros(this.word3>>>val))!=64){
            return (int)(tail0s+64);
          }
      }
    }
    return Integer.MIN_VALUE;
  }
  @Override public int pollFirstInt(){
    long word;
    int tail0s;
    if((tail0s=Long.numberOfTrailingZeros(word=this.word0))!=64){
      this.word0=word&(~(1L<<tail0s));
      return (int)(Byte.MIN_VALUE+tail0s);
    }
    if((tail0s=Long.numberOfTrailingZeros(word=this.word1))!=64){
      this.word1=word&(~(1L<<tail0s));
      return (int)(tail0s-64);
    }
    if((tail0s=Long.numberOfTrailingZeros(word=this.word2))!=64){
      this.word2=word&(~(1L<<tail0s));
      return (int)(tail0s);
    }
    if((tail0s=Long.numberOfTrailingZeros(word=this.word3))!=64){
      this.word3=word&(~(1L<<tail0s));
      return (int)(tail0s+64);
    }
    return Integer.MIN_VALUE;
  }
  @Override public int pollLastInt(){
    long word;
    int lead0s;
    if((lead0s=Long.numberOfLeadingZeros(word=this.word3))!=64){
      this.word3=word&(~(Long.MIN_VALUE>>>lead0s));
      return (int)(Byte.MAX_VALUE-lead0s);
    }
    if((lead0s=Long.numberOfLeadingZeros(word=this.word2))!=64){
      this.word2=word&(~(Long.MIN_VALUE>>>lead0s));
      return (int)(63-lead0s);
    }
    if((lead0s=Long.numberOfLeadingZeros(word=this.word1))!=64){
      this.word1=word&(~(Long.MIN_VALUE>>>lead0s));
      return (int)(-1-lead0s);
    }
    if((lead0s=Long.numberOfLeadingZeros(word=this.word0))!=64){
      this.word0=word&(~(Long.MIN_VALUE>>>lead0s));
      return (int)(-65-lead0s); 
    }
    return Integer.MIN_VALUE;
  }
  @Override public long firstLong(){
    return (long)(firstInt());
  }
  @Override public long lastLong(){
    return (long)(lastInt());
  }
  @Override public long lowerLong(long val){
    if(val>Byte.MIN_VALUE){
      int v;
      switch((v=((int)val)-1)>>6){
        default:
          v=127;
        case 1:
          int lead0s;
          if((lead0s=Long.numberOfLeadingZeros(this.word3<<-v))!=64){
            return (long)(127-lead0s);
          }
          v=0;
        case 0:
          if((lead0s=Long.numberOfLeadingZeros(this.word2<<-v))!=64){
            return (long)(63-lead0s);
          }
          v=0;
        case -1:
          if((lead0s=Long.numberOfLeadingZeros(this.word1<<-v))!=64){
            return (long)(-1-lead0s);
          }
          v=0;
        case -2:
          if((lead0s=Long.numberOfLeadingZeros(this.word0<<-v))!=64){
            return (long)(-65-lead0s);
          }
      }
    }  
    return Long.MIN_VALUE;
  }
  @Override public long longFloor(long val){
    if(val>=Byte.MIN_VALUE)
    {
      int v;
      switch((v=(int)val)>>6){
        default:
          v=127;
        case 1:
          int lead0s;
          if((lead0s=Long.numberOfLeadingZeros(this.word3<<-v))!=64){
            return (long)(127-lead0s);
          }
          v=0;
        case 0:
          if((lead0s=Long.numberOfLeadingZeros(this.word2<<-v))!=64){
            return (long)(63-lead0s);
          }
          v=0;
        case -1:
          if((lead0s=Long.numberOfLeadingZeros(this.word1<<-v))!=64){
            return (long)(-1-lead0s);
          }
          v=0;
        case -2:
          if((lead0s=Long.numberOfLeadingZeros(this.word0<<-v))!=64){
            return (long)(-65-lead0s);
          }
      }
    }  
    return Long.MIN_VALUE;
  }
  @Override public long higherLong(long val){
    if(val<Byte.MAX_VALUE)
    {
      int v;
      switch((v=((int)val)+1)>>6){
        default:
          v=-128;
        case -2:
          int tail0s;
          if((tail0s=Long.numberOfTrailingZeros(this.word0>>>v))!=64){
            return (long)(tail0s-128);
          }
          v=0;
        case -1:
          if((tail0s=Long.numberOfTrailingZeros(this.word1>>>v))!=64){
            return (long)(tail0s-64);
          }
          v=0;
        case 0:
          if((tail0s=Long.numberOfTrailingZeros(this.word2>>>v))!=64){
            return (long)(tail0s);
          }
          v=0;
        case 1:
          if((tail0s=Long.numberOfTrailingZeros(this.word3>>>v))!=64){
            return (long)(tail0s+64);
          }
      }
    }
    return Long.MIN_VALUE;
  }
  @Override public long longCeiling(long val){
    if(val<=Byte.MAX_VALUE)
    {
      int v;
      switch((v=(int)val)>>6){
        default:
          v=-128;
        case -2:
          int tail0s;
          if((tail0s=Long.numberOfTrailingZeros(this.word0>>>v))!=64){
            return (long)(tail0s-128);
          }
          v=0;
        case -1:
          if((tail0s=Long.numberOfTrailingZeros(this.word1>>>v))!=64){
            return (long)(tail0s-64);
          }
          v=0;
        case 0:
          if((tail0s=Long.numberOfTrailingZeros(this.word2>>>v))!=64){
            return (long)(tail0s);
          }
          v=0;
        case 1:
          if((tail0s=Long.numberOfTrailingZeros(this.word3>>>v))!=64){
            return (long)(tail0s+64);
          }
      }
    }
    return Long.MIN_VALUE;
  }
  @Override public long pollFirstLong(){
    long word;
    int tail0s;
    if((tail0s=Long.numberOfTrailingZeros(word=this.word0))!=64){
      this.word0=word&(~(1L<<tail0s));
      return (long)(Byte.MIN_VALUE+tail0s);
    }
    if((tail0s=Long.numberOfTrailingZeros(word=this.word1))!=64){
      this.word1=word&(~(1L<<tail0s));
      return (long)(tail0s-64);
    }
    if((tail0s=Long.numberOfTrailingZeros(word=this.word2))!=64){
      this.word2=word&(~(1L<<tail0s));
      return (long)(tail0s);
    }
    if((tail0s=Long.numberOfTrailingZeros(word=this.word3))!=64){
      this.word3=word&(~(1L<<tail0s));
      return (long)(tail0s+64);
    }
    return Long.MIN_VALUE;
  }
  @Override public long pollLastLong(){
    long word;
    int lead0s;
    if((lead0s=Long.numberOfLeadingZeros(word=this.word3))!=64){
      this.word3=word&(~(Long.MIN_VALUE>>>lead0s));
      return (long)(Byte.MAX_VALUE-lead0s);
    }
    if((lead0s=Long.numberOfLeadingZeros(word=this.word2))!=64){
      this.word2=word&(~(Long.MIN_VALUE>>>lead0s));
      return (long)(63-lead0s);
    }
    if((lead0s=Long.numberOfLeadingZeros(word=this.word1))!=64){
      this.word1=word&(~(Long.MIN_VALUE>>>lead0s));
      return (long)(-1-lead0s);
    }
    if((lead0s=Long.numberOfLeadingZeros(word=this.word0))!=64){
      this.word0=word&(~(Long.MIN_VALUE>>>lead0s));
      return (long)(-65-lead0s); 
    }
    return Long.MIN_VALUE;
  }
  @Override public float firstFloat(){
    return (float)(firstInt());
  }
  @Override public float lastFloat(){
    return (float)(lastInt());
  }
  @Override public float lowerFloat(float val){
    for(;;){
      int v;
      if(val!=val){
        v=Byte.MAX_VALUE;
      }else if(val>Byte.MIN_VALUE){
        if((v=(int)val)>=val){
          --v;
        }
      }else{
        break;
      }
      switch(v>>6){
        default:
          v=127;
        case 1:
          int lead0s;
          if((lead0s=Long.numberOfLeadingZeros(this.word3<<-v))!=64){
            return (float)(127-lead0s);
          }
          v=0;
        case 0:
          if((lead0s=Long.numberOfLeadingZeros(this.word2<<-v))!=64){
            return (float)(63-lead0s);
          }
          v=0;
        case -1:
          if((lead0s=Long.numberOfLeadingZeros(this.word1<<-v))!=64){
            return (float)(-1-lead0s);
          }
          v=0;
        case -2:
          if((lead0s=Long.numberOfLeadingZeros(this.word0<<-v))!=64){
            return (float)(-65-lead0s);
          }
      }
      break;
    }
    return Float.NaN;
  }
  @Override public float floatFloor(float val){
    for(;;){
      int v;
      if(val!=val){
        v=Byte.MAX_VALUE;
      }else if(val>=Byte.MIN_VALUE){
        if((v=(int)val)>val){
          --v;
        }
      }else{
        break;
      }
      switch(v>>6){
        default:
          v=127;
        case 1:
          int lead0s;
          if((lead0s=Long.numberOfLeadingZeros(this.word3<<-v))!=64){
            return (float)(127-lead0s);
          }
          v=0;
        case 0:
          if((lead0s=Long.numberOfLeadingZeros(this.word2<<-v))!=64){
            return (float)(63-lead0s);
          }
          v=0;
        case -1:
          if((lead0s=Long.numberOfLeadingZeros(this.word1<<-v))!=64){
            return (float)(-1-lead0s);
          }
          v=0;
        case -2:
          if((lead0s=Long.numberOfLeadingZeros(this.word0<<-v))!=64){
            return (float)(-65-lead0s);
          }
      }
      break;
    }
    return Float.NaN;
  }
  @Override public float higherFloat(float val){
    if(val==val && val<Byte.MAX_VALUE){
      int v;
      if((v=(int)val)<=val){
        ++v;
      }
      switch(v>>6){
        default:
          v=-128;
        case -2:
          int tail0s;
          if((tail0s=Long.numberOfTrailingZeros(this.word0>>>v))!=64){
            return (float)(tail0s-128);
          }
          v=0;
        case -1:
          if((tail0s=Long.numberOfTrailingZeros(this.word1>>>v))!=64){
            return (float)(tail0s-64);
          }
          v=0;
        case 0:
          if((tail0s=Long.numberOfTrailingZeros(this.word2>>>v))!=64){
            return (float)(tail0s);
          }
          v=0;
        case 1:
          if((tail0s=Long.numberOfTrailingZeros(this.word3>>>v))!=64){
            return (float)(tail0s+64);
          }
      }
    }
    return Float.NaN;
  }
  @Override public float floatCeiling(float val){
    if(val==val && val<=Byte.MAX_VALUE){
      int v;
      if((v=(int)val)<val){
        ++v;
      }
      switch(v>>6){
        default:
          v=-128;
        case -2:
          int tail0s;
          if((tail0s=Long.numberOfTrailingZeros(this.word0>>>v))!=64){
            return (float)(tail0s-128);
          }
          v=0;
        case -1:
          if((tail0s=Long.numberOfTrailingZeros(this.word1>>>v))!=64){
            return (float)(tail0s-64);
          }
          v=0;
        case 0:
          if((tail0s=Long.numberOfTrailingZeros(this.word2>>>v))!=64){
            return (float)(tail0s);
          }
          v=0;
        case 1:
          if((tail0s=Long.numberOfTrailingZeros(this.word3>>>v))!=64){
            return (float)(tail0s+64);
          }
      }
    }
    return Float.NaN;
  }
  @Override public float pollFirstFloat(){
    long word;
    int tail0s;
    if((tail0s=Long.numberOfTrailingZeros(word=this.word0))!=64){
      this.word0=word&(~(1L<<tail0s));
      return (float)(Byte.MIN_VALUE+tail0s);
    }
    if((tail0s=Long.numberOfTrailingZeros(word=this.word1))!=64){
      this.word1=word&(~(1L<<tail0s));
      return (float)(tail0s-64);
    }
    if((tail0s=Long.numberOfTrailingZeros(word=this.word2))!=64){
      this.word2=word&(~(1L<<tail0s));
      return (float)(tail0s);
    }
    if((tail0s=Long.numberOfTrailingZeros(word=this.word3))!=64){
      this.word3=word&(~(1L<<tail0s));
      return (float)(tail0s+64);
    }
    return Float.NaN;
  }
  @Override public float pollLastFloat(){
    long word;
    int lead0s;
    if((lead0s=Long.numberOfLeadingZeros(word=this.word3))!=64){
      this.word3=word&(~(Long.MIN_VALUE>>>lead0s));
      return (float)(Byte.MAX_VALUE-lead0s);
    }
    if((lead0s=Long.numberOfLeadingZeros(word=this.word2))!=64){
      this.word2=word&(~(Long.MIN_VALUE>>>lead0s));
      return (float)(63-lead0s);
    }
    if((lead0s=Long.numberOfLeadingZeros(word=this.word1))!=64){
      this.word1=word&(~(Long.MIN_VALUE>>>lead0s));
      return (float)(-1-lead0s);
    }
    if((lead0s=Long.numberOfLeadingZeros(word=this.word0))!=64){
      this.word0=word&(~(Long.MIN_VALUE>>>lead0s));
      return (float)(-65-lead0s); 
    }
    return Float.NaN;
  }
  @Override public double firstDouble(){
    return (double)(firstInt());
  }
  @Override public double lastDouble(){
    return (double)(lastInt());
  }
  @Override public double lowerDouble(double val){
    for(;;){
      int v;
      if(val!=val){
        v=Byte.MAX_VALUE;
      }else if(val>Byte.MIN_VALUE){
        if((v=(int)val)>=val){
          --v;
        }
      }else{
        break;
      }
      switch(v>>6){
        default:
          v=127;
        case 1:
          int lead0s;
          if((lead0s=Long.numberOfLeadingZeros(this.word3<<-v))!=64){
            return (double)(127-lead0s);
          }
          v=0;
        case 0:
          if((lead0s=Long.numberOfLeadingZeros(this.word2<<-v))!=64){
            return (double)(63-lead0s);
          }
          v=0;
        case -1:
          if((lead0s=Long.numberOfLeadingZeros(this.word1<<-v))!=64){
            return (double)(-1-lead0s);
          }
          v=0;
        case -2:
          if((lead0s=Long.numberOfLeadingZeros(this.word0<<-v))!=64){
            return (double)(-65-lead0s);
          }
      }
      break;
    }
    return Double.NaN;
  }
  @Override public double doubleFloor(double val){
    for(;;){
      int v;
      if(val!=val){
        v=Byte.MAX_VALUE;
      }else if(val>=Byte.MIN_VALUE){
        if((v=(int)val)>val){
          --v;
        }
      }else{
        break;
      }
      switch(v>>6){
        default:
          v=127;
        case 1:
          int lead0s;
          if((lead0s=Long.numberOfLeadingZeros(this.word3<<-v))!=64){
            return (double)(127-lead0s);
          }
          v=0;
        case 0:
          if((lead0s=Long.numberOfLeadingZeros(this.word2<<-v))!=64){
            return (double)(63-lead0s);
          }
          v=0;
        case -1:
          if((lead0s=Long.numberOfLeadingZeros(this.word1<<-v))!=64){
            return (double)(-1-lead0s);
          }
          v=0;
        case -2:
          if((lead0s=Long.numberOfLeadingZeros(this.word0<<-v))!=64){
            return (double)(-65-lead0s);
          }
      }
      break;
    }
    return Double.NaN;
  }
  @Override public double higherDouble(double val){
    if(val==val && val<Byte.MAX_VALUE){
      int v;
      if((v=(int)val)<=val){
        ++v;
      }
      switch(v>>6){
        default:
          v=-128;
        case -2:
          int tail0s;
          if((tail0s=Long.numberOfTrailingZeros(this.word0>>>v))!=64){
            return (double)(tail0s-128);
          }
          v=0;
        case -1:
          if((tail0s=Long.numberOfTrailingZeros(this.word1>>>v))!=64){
            return (double)(tail0s-64);
          }
          v=0;
        case 0:
          if((tail0s=Long.numberOfTrailingZeros(this.word2>>>v))!=64){
            return (double)(tail0s);
          }
          v=0;
        case 1:
          if((tail0s=Long.numberOfTrailingZeros(this.word3>>>v))!=64){
            return (double)(tail0s+64);
          }
      }
    }
    return Double.NaN;
  }
  @Override public double doubleCeiling(double val){
    if(val==val && val<=Byte.MAX_VALUE){
      int v;
      if((v=(int)val)<val){
        ++v;
      }
      switch(v>>6){
        default:
          v=-128;
        case -2:
          int tail0s;
          if((tail0s=Long.numberOfTrailingZeros(this.word0>>>v))!=64){
            return (double)(tail0s-128);
          }
          v=0;
        case -1:
          if((tail0s=Long.numberOfTrailingZeros(this.word1>>>v))!=64){
            return (double)(tail0s-64);
          }
          v=0;
        case 0:
          if((tail0s=Long.numberOfTrailingZeros(this.word2>>>v))!=64){
            return (double)(tail0s);
          }
          v=0;
        case 1:
          if((tail0s=Long.numberOfTrailingZeros(this.word3>>>v))!=64){
            return (double)(tail0s+64);
          }
      }
    }
    return Double.NaN;
  }
  @Override public double pollFirstDouble(){
    long word;
    int tail0s;
    if((tail0s=Long.numberOfTrailingZeros(word=this.word0))!=64){
      this.word0=word&(~(1L<<tail0s));
      return (double)(Byte.MIN_VALUE+tail0s);
    }
    if((tail0s=Long.numberOfTrailingZeros(word=this.word1))!=64){
      this.word1=word&(~(1L<<tail0s));
      return (double)(tail0s-64);
    }
    if((tail0s=Long.numberOfTrailingZeros(word=this.word2))!=64){
      this.word2=word&(~(1L<<tail0s));
      return (double)(tail0s);
    }
    if((tail0s=Long.numberOfTrailingZeros(word=this.word3))!=64){
      this.word3=word&(~(1L<<tail0s));
      return (double)(tail0s+64);
    }
    return Double.NaN;
  }
  @Override public double pollLastDouble(){
    long word;
    int lead0s;
    if((lead0s=Long.numberOfLeadingZeros(word=this.word3))!=64){
      this.word3=word&(~(Long.MIN_VALUE>>>lead0s));
      return (double)(Byte.MAX_VALUE-lead0s);
    }
    if((lead0s=Long.numberOfLeadingZeros(word=this.word2))!=64){
      this.word2=word&(~(Long.MIN_VALUE>>>lead0s));
      return (double)(63-lead0s);
    }
    if((lead0s=Long.numberOfLeadingZeros(word=this.word1))!=64){
      this.word1=word&(~(Long.MIN_VALUE>>>lead0s));
      return (double)(-1-lead0s);
    }
    if((lead0s=Long.numberOfLeadingZeros(word=this.word0))!=64){
      this.word0=word&(~(Long.MIN_VALUE>>>lead0s));
      return (double)(-65-lead0s); 
    }
    return Double.NaN;
  }
  @Override public ByteComparator comparator(){
    return Byte::compare;
  }
  private int countHeadSetElements(int exclToElement){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  private int countTailSetElements(int inclFromElement){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  private int countSubSetElements(int inclFromElement,int exclToElement){
    //TODO
    throw new omni.util.NotYetImplementedException();
  }
  public static abstract class Checked extends ByteSetImpl implements Serializable{
    private static final long serialVersionUID=1L;
    transient int modCountAndSize;
    public Checked(){
      super();
    }
    public Checked(ByteSetImpl.Checked that){
      super(that);
      this.modCountAndSize=that.modCountAndSize;
    }
    public Checked(ByteSetImpl.Unchecked that){
      super(that);
      this.modCountAndSize=SetCommonImpl.size(word0,word1,word2,word3);
    }
    public Checked(OmniCollection.OfBoolean that){
      super();
      //TODO optimize
      this.addAll(that);
    }
    public Checked(OmniCollection.OfByte that){
      super();
      //TODO optimize
      this.addAll(that);
    }
    public Checked(OmniCollection.ByteOutput<?> that){
      super();
      //TODO optimize
      this.addAll(that);
    }
    public Checked(OmniCollection.OfRef<? extends Byte> that){
      super();
      //TODO optimize
      this.addAll(that);
    }
    public Checked(Collection<? extends Byte> that){
      super();
      //TODO optimize
      this.addAll(that);
    }
    private void readObject(ObjectInputStream ois) throws IOException{
      int size;
      this.modCountAndSize=size=ois.readUnsignedShort();
      if(size!=0){
        long word;
        this.word2=word=ois.readLong();
        if((size-=Long.bitCount(word))!=0){
          this.word3=word=ois.readLong();
          if((size-=Long.bitCount(word))!=0){
            this.word1=word=ois.readLong();
            if((size-=Long.bitCount(word))!=0){
              this.word0=ois.readLong();
            }
          }
        }
      }
    }
    private void writeObject(ObjectOutputStream oos) throws IOException{
      int modCountAndSize=this.modCountAndSize;
      try{
        int size;
        oos.writeShort(size=modCountAndSize&0x1ff);
        if(size!=0){
          long word;
          oos.writeLong(word=this.word2);
          if((size-=Long.bitCount(word))!=0){
            oos.writeLong(word=this.word3);
            if((size-=Long.bitCount(word))!=0){
              oos.writeLong(word=this.word1);
              if((size-=Long.bitCount(word))!=0){
                oos.writeLong(this.word0);
              }
            }
          }
        }
      }finally{
        CheckedCollection.checkModCount(modCountAndSize,this.modCountAndSize);
      }
    }
    @Override public int hashCode(){
      int numLeft,sum=0;
      if((numLeft=this.modCountAndSize&0x1ff)!=0){
        int offset=Byte.MAX_VALUE;
        goToEnd:for(long word=this.word3;;){
          if((word&(1L<<offset))!=0){
            sum+=offset;
            if(--numLeft==0){
              break goToEnd;
            }
          }
          if(--offset==63){
            for(word=this.word2;;){
              if((word&(1L<<offset))!=0){
                sum+=offset;
                if(--numLeft==0){
                  break goToEnd;
                }
              }
              if(--offset==-1){
                for(word=this.word1;;){
                  if((word&(1L<<offset))!=0){
                    sum+=offset;
                    if(--numLeft==0){
                      break goToEnd;
                    }
                  }
                  if(--offset==-65){
                    for(word=this.word0;;--offset){
                      if((word&(1L<<offset))!=0){
                        sum+=offset;
                        if(--numLeft==0){
                          break goToEnd;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
      return sum;
    }
    @Override public boolean add(byte val){
      if(super.uncheckedAdd(val)){
        this.modCountAndSize+=((1<<9)+1);
        return true;
      }
      return false;
    }
    @Override public boolean add(boolean val){
      if(super.add(val)){
        this.modCountAndSize+=((1<<9)+1);
        return true;
      }
      return false;
    }
    private int uncheckedremoveIf(int modCount,int size,BytePredicate filter){
      int numRemoved=0;
      long word3=this.word3;
      for(int offset=Byte.MAX_VALUE;;){
        long marker;
        if((word3&(marker=1L<<offset))!=0){
          if(filter.test((byte)offset)){
            word3&=(~marker);
            ++numRemoved;
          }
          if(--size==0){
            CheckedCollection.checkModCount(modCount,(modCount=this.modCountAndSize)&(~0x1ff));
            if(numRemoved==0){
              return 0;
            }
            break;
          }
        }
        if(--offset==63){
          long word2=this.word2;
          for(;;){
            if((word2&(marker=1L<<offset))!=0){
              if(filter.test((byte)offset)){
                word2&=(~marker);
                ++numRemoved;
              }
              if(--size==0){
                CheckedCollection.checkModCount(modCount,(modCount=this.modCountAndSize)&(~0x1ff));
                if(numRemoved==0){
                  return 0;
                }
                break;
              }
            }
            if(--offset==-1){
              long word1=this.word1;
              for(;;){
                if((word1&(marker=1L<<offset))!=0){
                  if(filter.test((byte)offset)){
                    word1&=(~marker);
                    ++numRemoved;
                  }
                  if(--size==0){
                    CheckedCollection.checkModCount(modCount,(modCount=this.modCountAndSize)&(~0x1ff));
                    if(numRemoved==0){
                      return 0;
                    }
                    break;
                  }
                }
                if(--offset==-65){
                  long word0=this.word0;
                  for(;;--offset){
                    if((word0&(marker=1L<<offset))!=0){
                      if(filter.test((byte)offset)){
                        word0&=(~marker);
                        ++numRemoved;
                      }
                      if(--size==0){
                        CheckedCollection.checkModCount(modCount,(modCount=this.modCountAndSize)&(~0x1ff));
                        if(numRemoved==0){
                          return 0;
                        }
                        break;
                      }
                    }
                  }
                  this.word0=word0;
                  break;
                }
              }
              this.word1=word1;
              break;
            }
          }
          this.word2=word2;
          break;
        }
      }
      this.word3=word3;
      this.modCountAndSize=modCount+(modCount=(1<<9)-numRemoved);
      return modCount;
    }
    @Override public boolean removeIf(BytePredicate filter){
      final int modCountAndSize;
      return ((modCountAndSize=this.modCountAndSize)&0x1ff)!=0 && uncheckedremoveIf(modCountAndSize&(~0x1ff),modCountAndSize&0x1ff,filter)!=0;
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      final int modCountAndSize;
      return ((modCountAndSize=this.modCountAndSize)&0x1ff)!=0 && uncheckedremoveIf(modCountAndSize&(~0x1ff),modCountAndSize&0x1ff,filter::test)!=0;
    }
    @Override public void clear(){
      final int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        this.modCountAndSize=(modCountAndSize&(~0x1ff))+(1<<9);
        this.word0=0;
        this.word1=0;
        this.word2=0;
        this.word3=0;
      }
    }
    @Override public boolean isEmpty(){
      return (this.modCountAndSize&0x1ff)==0;
    }
    @Override public int size(){
      return this.modCountAndSize&0x1ff;
    }
    @Override public boolean contains(Object val){
      if((this.modCountAndSize&0x1ff)!=0){
        if(val instanceof Byte){
          return super.contains((byte)val);
        }else if(val instanceof Integer || val instanceof Short){
          return super.contains(((Number)val).intValue());
        }else if(val instanceof Long){
          return super.contains((long)val);
        }else if(val instanceof Float){
          return super.contains((float)val);
        }else if(val instanceof Double){
          return super.contains((double)val);
        }else if(val instanceof Character){
          return super.contains((char)val);
        }else if(val instanceof Boolean){
          return super.contains((boolean)val);
        }
      }
      return false;
    }
    @Override public boolean remove(Object val){
      final int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        for(;;){
          if(val instanceof Byte){
            if(!super.removeVal((byte)val)){
              break;
            }
          }else if(val instanceof Integer || val instanceof Short){
            if(!super.removeVal(((Number)val).intValue())){
              break;
            }
          }else if(val instanceof Long){
            if(!super.removeVal((long)val)){
              break;
            }
          }else if(val instanceof Float){
            if(!super.removeVal((float)val)){
              break;
            }
          }else if(val instanceof Double){
            if(!super.removeVal((double)val)){
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
    @Override public Byte lower(byte val){
      if((modCountAndSize&0x1ff)!=0){
        int v;
        switch((v=((int)val)+1)>>6){
          case 1:
            int lead0s;
            if((lead0s=Long.numberOfLeadingZeros(this.word3<<-v))!=64){
              return (Byte)(byte)(127-lead0s);
            }
            v=0;
          case 0:
            if((lead0s=Long.numberOfLeadingZeros(this.word2<<-v))!=64){
              return (Byte)(byte)(63-lead0s);
            }
            v=0;
          case -1:
            if((lead0s=Long.numberOfLeadingZeros(this.word1<<-v))!=64){
              return (Byte)(byte)(-1-lead0s);
            }
            v=0;
          case -2:
            if((lead0s=Long.numberOfLeadingZeros(this.word0<<-v))!=64){
              return (Byte)(byte)(-65-lead0s);
            }
          default:
        }
      }
      return null;
    }
    @Override public Byte floor(byte val){
      if((modCountAndSize&0x1ff)!=0){
        switch(val>>6){
          case 1:
            int lead0s;
            if((lead0s=Long.numberOfLeadingZeros(this.word3<<-val))!=64){
              return (Byte)(byte)(127-lead0s);
            }
            val=0;
          case 0:
            if((lead0s=Long.numberOfLeadingZeros(this.word2<<-val))!=64){
              return (Byte)(byte)(63-lead0s);
            }
            val=0;
          case -1:
            if((lead0s=Long.numberOfLeadingZeros(this.word1<<-val))!=64){
              return (Byte)(byte)(-1-lead0s);
            }
            val=0;
          default:
            if((lead0s=Long.numberOfLeadingZeros(this.word0<<-val))!=64){
              return (Byte)(byte)(-65-lead0s);
            }
        }
      }
      return null;
    }
    @Override public Byte higher(byte val){
      if((modCountAndSize&0x1ff)!=0){
        int v;
        switch((v=((int)val)+1)>>6){
          case -2:
            int tail0s;
            if((tail0s=Long.numberOfTrailingZeros(this.word0>>>v))!=64){
              return (Byte)(byte)(tail0s-128);
            }
            v=0;
          case -1:
            if((tail0s=Long.numberOfTrailingZeros(this.word1>>>v))!=64){
              return (Byte)(byte)(tail0s-64);
            }
            v=0;
          case 0:
            if((tail0s=Long.numberOfTrailingZeros(this.word2>>>v))!=64){
              return (Byte)(byte)(tail0s);
            }
            v=0;
          case 1:
            if((tail0s=Long.numberOfTrailingZeros(this.word3>>>v))!=64){
              return (Byte)(byte)(tail0s+64);
            }
          default:
        }
      }
      return null;
    }
    @Override public Byte ceiling(byte val){
      if((modCountAndSize&0x1ff)!=0){
        switch(val>>6){
          case -2:
            int tail0s;
            if((tail0s=Long.numberOfTrailingZeros(this.word0>>>val))!=64){
              return (Byte)(byte)(tail0s-128);
            }
            val=0;
          case -1:
            if((tail0s=Long.numberOfTrailingZeros(this.word1>>>val))!=64){
              return (Byte)(byte)(tail0s-64);
            }
            val=0;
          case 0:
            if((tail0s=Long.numberOfTrailingZeros(this.word2>>>val))!=64){
              return (Byte)(byte)(tail0s);
            }
            val=0;
          default:
            if((tail0s=Long.numberOfTrailingZeros(this.word3>>>val))!=64){
              return (Byte)(byte)(tail0s+64);
            }
        }
      }
      return null;
    }
    @Override public Byte pollFirst(){
      int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return (Byte)(byte)(super.uncheckedRemoveFirst());
      }
      return null;
    }
    @Override public Byte pollLast(){
      int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return (Byte)(byte)(super.uncheckedRemoveLast());
      }
      return null;
    }
    @Override public byte lowerByte(byte val){
      if((modCountAndSize&0x1ff)!=0){
        int v;
        switch((v=((int)val)+1)>>6){
          case 1:
            int lead0s;
            if((lead0s=Long.numberOfLeadingZeros(this.word3<<-v))!=64){
              return (byte)(127-lead0s);
            }
            v=0;
          case 0:
            if((lead0s=Long.numberOfLeadingZeros(this.word2<<-v))!=64){
              return (byte)(63-lead0s);
            }
            v=0;
          case -1:
            if((lead0s=Long.numberOfLeadingZeros(this.word1<<-v))!=64){
              return (byte)(-1-lead0s);
            }
            v=0;
          case -2:
            if((lead0s=Long.numberOfLeadingZeros(this.word0<<-v))!=64){
              return (byte)(-65-lead0s);
            }
          default:
        }
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte byteFloor(byte val){
      if((modCountAndSize&0x1ff)!=0){
        switch(val>>6){
          case 1:
            int lead0s;
            if((lead0s=Long.numberOfLeadingZeros(this.word3<<-val))!=64){
              return (byte)(127-lead0s);
            }
            val=0;
          case 0:
            if((lead0s=Long.numberOfLeadingZeros(this.word2<<-val))!=64){
              return (byte)(63-lead0s);
            }
            val=0;
          case -1:
            if((lead0s=Long.numberOfLeadingZeros(this.word1<<-val))!=64){
              return (byte)(-1-lead0s);
            }
            val=0;
          default:
            if((lead0s=Long.numberOfLeadingZeros(this.word0<<-val))!=64){
              return (byte)(-65-lead0s);
            }
        }
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte higherByte(byte val){
      if((modCountAndSize&0x1ff)!=0){
        int v;
        switch((v=((int)val)+1)>>6){
          case -2:
            int tail0s;
            if((tail0s=Long.numberOfTrailingZeros(this.word0>>>v))!=64){
              return (byte)(tail0s-128);
            }
            v=0;
          case -1:
            if((tail0s=Long.numberOfTrailingZeros(this.word1>>>v))!=64){
              return (byte)(tail0s-64);
            }
            v=0;
          case 0:
            if((tail0s=Long.numberOfTrailingZeros(this.word2>>>v))!=64){
              return (byte)(tail0s);
            }
            v=0;
          case 1:
            if((tail0s=Long.numberOfTrailingZeros(this.word3>>>v))!=64){
              return (byte)(tail0s+64);
            }
          default:
        }
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte byteCeiling(byte val){
      if((modCountAndSize&0x1ff)!=0){
        switch(val>>6){
          case -2:
            int tail0s;
            if((tail0s=Long.numberOfTrailingZeros(this.word0>>>val))!=64){
              return (byte)(tail0s-128);
            }
            val=0;
          case -1:
            if((tail0s=Long.numberOfTrailingZeros(this.word1>>>val))!=64){
              return (byte)(tail0s-64);
            }
            val=0;
          case 0:
            if((tail0s=Long.numberOfTrailingZeros(this.word2>>>val))!=64){
              return (byte)(tail0s);
            }
            val=0;
          default:
            if((tail0s=Long.numberOfTrailingZeros(this.word3>>>val))!=64){
              return (byte)(tail0s+64);
            }
        }
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte pollFirstByte(){
      int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return (byte)(super.uncheckedRemoveFirst());
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte pollLastByte(){
      int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return (byte)(super.uncheckedRemoveLast());
      }
      return Byte.MIN_VALUE;
    }
    @Override public short lowerShort(short val){
      if((modCountAndSize&0x1ff)!=0){
        if(val>Byte.MAX_VALUE){
          return (short)(super.lastInt());
        }
        int v;
        switch((v=((int)val)+1)>>6){
          case 1:
            int lead0s;
            if((lead0s=Long.numberOfLeadingZeros(this.word3<<-v))!=64){
              return (short)(127-lead0s);
            }
            v=0;
          case 0:
            if((lead0s=Long.numberOfLeadingZeros(this.word2<<-v))!=64){
              return (short)(63-lead0s);
            }
            v=0;
          case -1:
            if((lead0s=Long.numberOfLeadingZeros(this.word1<<-v))!=64){
              return (short)(-1-lead0s);
            }
            v=0;
          case -2:
            if((lead0s=Long.numberOfLeadingZeros(this.word0<<-v))!=64){
              return (short)(-65-lead0s);
            }
          default:
        }
      }
      return Short.MIN_VALUE;
    }
    @Override public short shortFloor(short val){
      if((modCountAndSize&0x1ff)!=0){
        if(val>Byte.MAX_VALUE){
          return (short)(super.lastInt());
        }
        switch(val>>6){
          case 1:
            int lead0s;
            if((lead0s=Long.numberOfLeadingZeros(this.word3<<-val))!=64){
              return (short)(127-lead0s);
            }
            val=0;
          case 0:
            if((lead0s=Long.numberOfLeadingZeros(this.word2<<-val))!=64){
              return (short)(63-lead0s);
            }
            val=0;
          case -1:
            if((lead0s=Long.numberOfLeadingZeros(this.word1<<-val))!=64){
              return (short)(-1-lead0s);
            }
            val=0;
          case -2:
            if((lead0s=Long.numberOfLeadingZeros(this.word0<<-val))!=64){
              return (short)(-65-lead0s);
            }
          default:
        }
      }
      return Short.MIN_VALUE;
    }
    @Override public short higherShort(short val){
      if((modCountAndSize&0x1ff)!=0){
        if(val<Byte.MIN_VALUE){
          return (short)(super.firstInt());
        }
        int v;
        switch((v=((int)val)+1)>>6){
          case -2:
            int tail0s;
            if((tail0s=Long.numberOfTrailingZeros(this.word0>>>v))!=64){
              return (short)(tail0s-128);
            }
            v=0;
          case -1:
            if((tail0s=Long.numberOfTrailingZeros(this.word1>>>v))!=64){
              return (short)(tail0s-64);
            }
            v=0;
          case 0:
            if((tail0s=Long.numberOfTrailingZeros(this.word2>>>v))!=64){
              return (short)(tail0s);
            }
            v=0;
          case 1:
            if((tail0s=Long.numberOfTrailingZeros(this.word3>>>v))!=64){
              return (short)(tail0s+64);
            }
          default:
        }
      }
      return Short.MIN_VALUE;
    }
    @Override public short shortCeiling(short val){
      if((modCountAndSize&0x1ff)!=0){
        if(val<Byte.MIN_VALUE){
          return (short)(super.firstInt());
        }
        switch(val>>6){
          case -2:
            int tail0s;
            if((tail0s=Long.numberOfTrailingZeros(this.word0>>>val))!=64){
              return (short)(tail0s-128);
            }
            val=0;
          case -1:
            if((tail0s=Long.numberOfTrailingZeros(this.word1>>>val))!=64){
              return (short)(tail0s-64);
            }
            val=0;
          case 0:
            if((tail0s=Long.numberOfTrailingZeros(this.word2>>>val))!=64){
              return (short)(tail0s);
            }
            val=0;
          case 1:
            if((tail0s=Long.numberOfTrailingZeros(this.word3>>>val))!=64){
              return (short)(tail0s+64);
            }
          default:
        }
      }
      return Short.MIN_VALUE;
    }
    @Override public short pollFirstShort(){
      int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return (short)(super.uncheckedRemoveFirst());
      }
      return Short.MIN_VALUE;
    }
    @Override public short pollLastShort(){
      int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return (short)(super.uncheckedRemoveLast());
      }
      return Short.MIN_VALUE;
    }
    @Override public int firstInt(){
      if((modCountAndSize&0x1ff)==0){
        throw new NoSuchElementException();
      }
      return super.firstInt();
    }
    @Override public int lastInt(){
      if((modCountAndSize&0x1ff)==0){
        throw new NoSuchElementException();
      }
      return super.lastInt();  
    }
    @Override public int lowerInt(int val){
      if((modCountAndSize&0x1ff)!=0){
        if(val>Byte.MAX_VALUE){
          return (int)(super.lastInt());
        }
        switch((--val)>>6){
          case 1:
            int lead0s;
            if((lead0s=Long.numberOfLeadingZeros(this.word3<<-val))!=64){
              return (int)(127-lead0s);
            }
            val=0;
          case 0:
            if((lead0s=Long.numberOfLeadingZeros(this.word2<<-val))!=64){
              return (int)(63-lead0s);
            }
            val=0;
          case -1:
            if((lead0s=Long.numberOfLeadingZeros(this.word1<<-val))!=64){
              return (int)(-1-lead0s);
            }
            val=0;
          case -2:
            if((lead0s=Long.numberOfLeadingZeros(this.word0<<-val))!=64){
              return (int)(-65-lead0s);
            }
          default:
        }
      }
      return Integer.MIN_VALUE;
    }
    @Override public int intFloor(int val){
      if((modCountAndSize&0x1ff)!=0){
        if(val>Byte.MAX_VALUE){
          return (int)(super.lastInt());
        }
        switch(val>>6){
          case 1:
            int lead0s;
            if((lead0s=Long.numberOfLeadingZeros(this.word3<<-val))!=64){
              return (int)(127-lead0s);
            }
            val=0;
          case 0:
            if((lead0s=Long.numberOfLeadingZeros(this.word2<<-val))!=64){
              return (int)(63-lead0s);
            }
            val=0;
          case -1:
            if((lead0s=Long.numberOfLeadingZeros(this.word1<<-val))!=64){
              return (int)(-1-lead0s);
            }
            val=0;
          case -2:
            if((lead0s=Long.numberOfLeadingZeros(this.word0<<-val))!=64){
              return (int)(-65-lead0s);
            }
          default:
        }
      }
      return Integer.MIN_VALUE;
    }
    @Override public int higherInt(int val){
      if((modCountAndSize&0x1ff)!=0){
        if(val<Byte.MIN_VALUE){
          return (int)(super.firstInt());
        }
        switch((++val)>>6){
          case -2:
            int tail0s;
            if((tail0s=Long.numberOfTrailingZeros(this.word0>>>val))!=64){
              return (int)(tail0s-128);
            }
            val=0;
          case -1:
            if((tail0s=Long.numberOfTrailingZeros(this.word1>>>val))!=64){
              return (int)(tail0s-64);
            }
            val=0;
          case 0:
            if((tail0s=Long.numberOfTrailingZeros(this.word2>>>val))!=64){
              return (int)(tail0s);
            }
            val=0;
          case 1:
            if((tail0s=Long.numberOfTrailingZeros(this.word3>>>val))!=64){
              return (int)(tail0s+64);
            }
          default:
        }
      }
      return Integer.MIN_VALUE;
    }
    @Override public int intCeiling(int val){
      if((modCountAndSize&0x1ff)!=0){
        if(val<Byte.MIN_VALUE){
          return (int)(super.firstInt());
        }
        switch(val>>6){
          case -2:
            int tail0s;
            if((tail0s=Long.numberOfTrailingZeros(this.word0>>>val))!=64){
              return (int)(tail0s-128);
            }
            val=0;
          case -1:
            if((tail0s=Long.numberOfTrailingZeros(this.word1>>>val))!=64){
              return (int)(tail0s-64);
            }
            val=0;
          case 0:
            if((tail0s=Long.numberOfTrailingZeros(this.word2>>>val))!=64){
              return (int)(tail0s);
            }
            val=0;
          case 1:
            if((tail0s=Long.numberOfTrailingZeros(this.word3>>>val))!=64){
              return (int)(tail0s+64);
            }
          default:
        }
      }
      return Integer.MIN_VALUE;
    }
    @Override public int pollFirstInt(){
      int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return (int)(super.uncheckedRemoveFirst());
      }
      return Integer.MIN_VALUE;
    }
    @Override public int pollLastInt(){
      int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return (int)(super.uncheckedRemoveLast());
      }
      return Integer.MIN_VALUE;
    }
    @Override public long lowerLong(long val){
      if((modCountAndSize&0x1ff)!=0){
        if(val>Byte.MAX_VALUE){
          return (long)(super.lastInt());
        }
        int v;
        switch((v=((int)val)+1)>>6){
          case 1:
            int lead0s;
            if((lead0s=Long.numberOfLeadingZeros(this.word3<<-v))!=64){
              return (long)(127-lead0s);
            }
            v=0;
          case 0:
            if((lead0s=Long.numberOfLeadingZeros(this.word2<<-v))!=64){
              return (long)(63-lead0s);
            }
            v=0;
          case -1:
            if((lead0s=Long.numberOfLeadingZeros(this.word1<<-v))!=64){
              return (long)(-1-lead0s);
            }
            v=0;
          case -2:
            if((lead0s=Long.numberOfLeadingZeros(this.word0<<-v))!=64){
              return (long)(-65-lead0s);
            }
          default:
        }
      }
      return Long.MIN_VALUE;
    }
    @Override public long longFloor(long val){
      if((modCountAndSize&0x1ff)!=0){
        if(val>Byte.MAX_VALUE){
          return (long)(super.lastInt());
        }
        int v;
        switch((v=(int)val)>>6){
          case 1:
            int lead0s;
            if((lead0s=Long.numberOfLeadingZeros(this.word3<<-v))!=64){
              return (long)(127-lead0s);
            }
            v=0;
          case 0:
            if((lead0s=Long.numberOfLeadingZeros(this.word2<<-v))!=64){
              return (long)(63-lead0s);
            }
            v=0;
          case -1:
            if((lead0s=Long.numberOfLeadingZeros(this.word1<<-v))!=64){
              return (long)(-1-lead0s);
            }
            v=0;
          case -2:
            if((lead0s=Long.numberOfLeadingZeros(this.word0<<-v))!=64){
              return (long)(-65-lead0s);
            }
          default:
        }
      }
      return Long.MIN_VALUE;
    }
    @Override public long higherLong(long val){
      if((modCountAndSize&0x1ff)!=0){
        if(val<Byte.MIN_VALUE){
          return (long)(super.firstInt());
        }
        int v;
        switch((v=((int)val)+1)>>6){
          case -2:
            int tail0s;
            if((tail0s=Long.numberOfTrailingZeros(this.word0>>>v))!=64){
              return (long)(tail0s-128);
            }
            v=0;
          case -1:
            if((tail0s=Long.numberOfTrailingZeros(this.word1>>>v))!=64){
              return (long)(tail0s-64);
            }
            v=0;
          case 0:
            if((tail0s=Long.numberOfTrailingZeros(this.word2>>>v))!=64){
              return (long)(tail0s);
            }
            v=0;
          case 1:
            if((tail0s=Long.numberOfTrailingZeros(this.word3>>>v))!=64){
              return (long)(tail0s+64);
            }
          default:
        }
      }
      return Long.MIN_VALUE;
    }
    @Override public long longCeiling(long val){
      if((modCountAndSize&0x1ff)!=0){
        if(val<Byte.MIN_VALUE){
          return (long)(super.firstInt());
        }
        int v;
        switch((v=(int)val)>>6){
          case -2:
            int tail0s;
            if((tail0s=Long.numberOfTrailingZeros(this.word0>>>v))!=64){
              return (long)(tail0s-128);
            }
            v=0;
          case -1:
            if((tail0s=Long.numberOfTrailingZeros(this.word1>>>v))!=64){
              return (long)(tail0s-64);
            }
            v=0;
          case 0:
            if((tail0s=Long.numberOfTrailingZeros(this.word2>>>v))!=64){
              return (long)(tail0s);
            }
            v=0;
          case 1:
            if((tail0s=Long.numberOfTrailingZeros(this.word3>>>v))!=64){
              return (long)(tail0s+64);
            }
          default:
        }
      }
      return Long.MIN_VALUE;
    }
    @Override public long pollFirstLong(){
      int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return (long)(super.uncheckedRemoveFirst());
      }
      return Long.MIN_VALUE;
    }
    @Override public long pollLastLong(){
      int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return (long)(super.uncheckedRemoveLast());
      }
      return Long.MIN_VALUE;
    }
    @Override public float lowerFloat(float val){
      if((modCountAndSize&0x1ff)!=0){
        if(val!=val || val>Byte.MAX_VALUE){
          return (float)(super.lastInt());
        }
        int v;
        if((v=(int)val)>=val){
          --v;
        }
        switch(v>>6){
          case 1:
            int lead0s;
            if((lead0s=Long.numberOfLeadingZeros(this.word3<<-v))!=64){
              return (float)(127-lead0s);
            }
            v=0;
          case 0:
            if((lead0s=Long.numberOfLeadingZeros(this.word2<<-v))!=64){
              return (float)(63-lead0s);
            }
            v=0;
          case -1:
            if((lead0s=Long.numberOfLeadingZeros(this.word1<<-v))!=64){
              return (float)(-1-lead0s);
            }
            v=0;
          case -2:
            if((lead0s=Long.numberOfLeadingZeros(this.word0<<-v))!=64){
              return (float)(-65-lead0s);
            }
          default:
        }
      }
      return Float.NaN;
    }
    @Override public float floatFloor(float val){
      if((modCountAndSize&0x1ff)!=0){
        if(val!=val || val>=Byte.MAX_VALUE){
          return (float)(super.lastInt());
        }
        int v;
        if((v=(int)val)>val){
          --v;
        }
        switch(v>>6){
          case 1:
            int lead0s;
            if((lead0s=Long.numberOfLeadingZeros(this.word3<<-v))!=64){
              return (float)(127-lead0s);
            }
            v=0;
          case 0:
            if((lead0s=Long.numberOfLeadingZeros(this.word2<<-v))!=64){
              return (float)(63-lead0s);
            }
            v=0;
          case -1:
            if((lead0s=Long.numberOfLeadingZeros(this.word1<<-v))!=64){
              return (float)(-1-lead0s);
            }
            v=0;
          case -2:
            if((lead0s=Long.numberOfLeadingZeros(this.word0<<-v))!=64){
              return (float)(-65-lead0s);
            }
          default:
        }
      }
      return Float.NaN;
    }
    @Override public float higherFloat(float val){
      if(val==val && (modCountAndSize&0x1ff)!=0){
        if(val<Byte.MIN_VALUE){
          return (float)(super.firstInt());
        }
        int v;
        if((v=(int)val)<=val){
          ++v;
        }
        switch(v>>6){
          case -2:
            int tail0s;
            if((tail0s=Long.numberOfTrailingZeros(this.word0>>>v))!=64){
              return (float)(tail0s-128);
            }
            v=0;
          case -1:
            if((tail0s=Long.numberOfTrailingZeros(this.word1>>>v))!=64){
              return (float)(tail0s-64);
            }
            v=0;
          case 0:
            if((tail0s=Long.numberOfTrailingZeros(this.word2>>>v))!=64){
              return (float)(tail0s);
            }
            v=0;
          case 1:
            if((tail0s=Long.numberOfTrailingZeros(this.word3>>>v))!=64){
              return (float)(tail0s+64);
            }
          default:
        }
      }
      return Float.NaN;
    }
    @Override public float floatCeiling(float val){
      if(val==val && (modCountAndSize&0x1ff)!=0){
        if(val<Byte.MIN_VALUE){
          return (float)(super.firstInt());
        }
        int v;
        if((v=(int)val)<val){
          ++v;
        }
        switch(v>>6){
          case -2:
            int tail0s;
            if((tail0s=Long.numberOfTrailingZeros(this.word0>>>v))!=64){
              return (float)(tail0s-128);
            }
            v=0;
          case -1:
            if((tail0s=Long.numberOfTrailingZeros(this.word1>>>v))!=64){
              return (float)(tail0s-64);
            }
            v=0;
          case 0:
            if((tail0s=Long.numberOfTrailingZeros(this.word2>>>v))!=64){
              return (float)(tail0s);
            }
            v=0;
          case 1:
            if((tail0s=Long.numberOfTrailingZeros(this.word3>>>v))!=64){
              return (float)(tail0s+64);
            }
          default:
        }
      }
      return Float.NaN;
    }
    @Override public float pollFirstFloat(){
      int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return (float)(super.uncheckedRemoveFirst());
      }
      return Float.NaN;
    }
    @Override public float pollLastFloat(){
      int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return (float)(super.uncheckedRemoveLast());
      }
      return Float.NaN;
    }
    @Override public double lowerDouble(double val){
      if((modCountAndSize&0x1ff)!=0){
        if(val!=val || val>Byte.MAX_VALUE){
          return (double)(super.lastInt());
        }
        int v;
        if((v=(int)val)>=val){
          --v;
        }
        switch(v>>6){
          case 1:
            int lead0s;
            if((lead0s=Long.numberOfLeadingZeros(this.word3<<-v))!=64){
              return (double)(127-lead0s);
            }
            v=0;
          case 0:
            if((lead0s=Long.numberOfLeadingZeros(this.word2<<-v))!=64){
              return (double)(63-lead0s);
            }
            v=0;
          case -1:
            if((lead0s=Long.numberOfLeadingZeros(this.word1<<-v))!=64){
              return (double)(-1-lead0s);
            }
            v=0;
          case -2:
            if((lead0s=Long.numberOfLeadingZeros(this.word0<<-v))!=64){
              return (double)(-65-lead0s);
            }
          default:
        }
      }
      return Double.NaN;
    }
    @Override public double doubleFloor(double val){
      if((modCountAndSize&0x1ff)!=0){
        if(val!=val || val>=Byte.MAX_VALUE){
          return (double)(super.lastInt());
        }
        int v;
        if((v=(int)val)>val){
          --v;
        }
        switch(v>>6){
          case 1:
            int lead0s;
            if((lead0s=Long.numberOfLeadingZeros(this.word3<<-v))!=64){
              return (double)(127-lead0s);
            }
            v=0;
          case 0:
            if((lead0s=Long.numberOfLeadingZeros(this.word2<<-v))!=64){
              return (double)(63-lead0s);
            }
            v=0;
          case -1:
            if((lead0s=Long.numberOfLeadingZeros(this.word1<<-v))!=64){
              return (double)(-1-lead0s);
            }
            v=0;
          case -2:
            if((lead0s=Long.numberOfLeadingZeros(this.word0<<-v))!=64){
              return (double)(-65-lead0s);
            }
          default:
        }
      }
      return Double.NaN;
    }
    @Override public double higherDouble(double val){
      if(val==val && (modCountAndSize&0x1ff)!=0){
        if(val<Byte.MIN_VALUE){
          return (double)(super.firstInt());
        }
        int v;
        if((v=(int)val)<=val){
          ++v;
        }
        switch(v>>6){
          case -2:
            int tail0s;
            if((tail0s=Long.numberOfTrailingZeros(this.word0>>>v))!=64){
              return (double)(tail0s-128);
            }
            v=0;
          case -1:
            if((tail0s=Long.numberOfTrailingZeros(this.word1>>>v))!=64){
              return (double)(tail0s-64);
            }
            v=0;
          case 0:
            if((tail0s=Long.numberOfTrailingZeros(this.word2>>>v))!=64){
              return (double)(tail0s);
            }
            v=0;
          case 1:
            if((tail0s=Long.numberOfTrailingZeros(this.word3>>>v))!=64){
              return (double)(tail0s+64);
            }
          default:
        }
      }
      return Double.NaN;
    }
    @Override public double doubleCeiling(double val){
      if(val==val && (modCountAndSize&0x1ff)!=0){
        if(val<Byte.MIN_VALUE){
          return (double)(super.firstInt());
        }
        int v;
        if((v=(int)val)<val){
          ++v;
        }
        switch(v>>6){
          case -2:
            int tail0s;
            if((tail0s=Long.numberOfTrailingZeros(this.word0>>>v))!=64){
              return (double)(tail0s-128);
            }
            v=0;
          case -1:
            if((tail0s=Long.numberOfTrailingZeros(this.word1>>>v))!=64){
              return (double)(tail0s-64);
            }
            v=0;
          case 0:
            if((tail0s=Long.numberOfTrailingZeros(this.word2>>>v))!=64){
              return (double)(tail0s);
            }
            v=0;
          case 1:
            if((tail0s=Long.numberOfTrailingZeros(this.word3>>>v))!=64){
              return (double)(tail0s+64);
            }
          default:
        }
      }
      return Double.NaN;
    }
    @Override public double pollFirstDouble(){
      int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return (double)(super.uncheckedRemoveFirst());
      }
      return Double.NaN;
    }
    @Override public double pollLastDouble(){
      int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return (double)(super.uncheckedRemoveLast());
      }
      return Double.NaN;
    }
    private void uncheckedDescendingForEach(int numLeft,ByteConsumer action){
      int offset=127;
      for(long word=this.word3;;){
        if((word&(1L<<offset))!=0){
          action.accept((byte)offset);
          if(--numLeft==0){
            return;
          }
        }
        if(--offset==63){
          for(word=this.word2;;){
            if((word&(1L<<offset))!=0){
              action.accept((byte)offset);
              if(--numLeft==0){
                return;
              }
            }
            if(--offset==-1){
              for(word=this.word1;;){
                if((word&(1L<<offset))!=0){
                  action.accept((byte)offset);
                  if(--numLeft==0){
                    return;
                  }
                }
                if(--offset==-65){
                  for(word=this.word0;;--offset){
                    if((word&(1L<<offset))!=0){
                      action.accept((byte)offset);
                      if(--numLeft==0){
                        return;
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    private void uncheckedAscendingForEach(int numLeft,ByteConsumer action){
      int offset=-128;
      for(long word=this.word0;;){
        if((word&(1L<<offset))!=0){
          action.accept((byte)offset);
          if(--numLeft==0){
            return;
          }
        }
        if(++offset==-64){
          for(word=this.word1;;){
            if((word&(1L<<offset))!=0){
              action.accept((byte)offset);
              if(--numLeft==0){
                return;
              }
            }
            if(++offset==0){
              for(word=this.word2;;){
                if((word&(1L<<offset))!=0){
                  action.accept((byte)offset);
                  if(--numLeft==0){
                    return;
                  }
                }
                if(++offset==64){
                  for(word=this.word3;;++offset){
                    if((word&(1L<<offset))!=0){
                      action.accept((byte)offset);
                      if(--numLeft==0){
                        return;
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    public static class Ascending extends Checked{
      public Ascending(){
        super();
      }
      public Ascending(ByteSetImpl.Checked that){
        super(that);
      }
      public Ascending(ByteSetImpl.Unchecked that){
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
      public Ascending(OmniCollection.OfRef<? extends Byte> that){
        super(that);
      }
      public Ascending(Collection<? extends Byte> that){
        super(that);
      }
      @Override public Object clone(){
        return new Ascending(this);
      }
      @Override public OmniIterator.OfByte iterator(){
        return new CheckedAscendingItr(this);
      }
      @Override public OmniIterator.OfByte descendingIterator(){
        return new CheckedDescendingItr(this);
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement){
        if(toElement==Byte.MAX_VALUE){
          return this;
        }
        final int t;
        return new CheckedSubSet.HeadSet.Ascending(this,t=toElement+1,(this.modCountAndSize&(~0x1ff))|((ByteSetImpl)this).countHeadSetElements(t));
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
        if(fromElement==Byte.MIN_VALUE){
          return this;
        }
        return new CheckedSubSet.TailSet.Ascending(this,fromElement,(this.modCountAndSize&(~0x1ff))|((ByteSetImpl)this).countTailSetElements(fromElement));
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
        final int t;
        switch(Integer.signum((t=toElement+1)-fromElement)){
        case 1:
          if(t==128){
            if(fromElement==-128){
              return this;
            }else{
              return new CheckedSubSet.TailSet.Ascending(this,fromElement,(this.modCountAndSize&(~0x1ff))|((ByteSetImpl)this).countTailSetElements(fromElement));
            }
          }else{
            if(fromElement==-128){
              return new CheckedSubSet.HeadSet.Ascending(this,t,(this.modCountAndSize&(~0x1ff))|((ByteSetImpl)this).countHeadSetElements(t));
            }else{
              return new CheckedSubSet.BodySet.Ascending(this,(t<<8)|(fromElement&0xff),(this.modCountAndSize&(~0x1ff))|((ByteSetImpl)this).countSubSetElements(fromElement,t));
            }
          }
        case 0:
          return new AbstractByteSet.EmptyView.Checked.Ascending(t);
        default:
          throw new IllegalArgumentException("out of bounds");
        }
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
        if(inclusive){
          return headSet(toElement);
        }
        if(toElement==Byte.MIN_VALUE){
          return new AbstractByteSet.EmptyView.Checked.Ascending(Byte.MIN_VALUE);
        }
        return new CheckedSubSet.HeadSet.Ascending(this,toElement,(this.modCountAndSize&(~0x1ff))|((ByteSetImpl)this).countHeadSetElements(toElement));
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement,boolean inclusive){
        if(inclusive){
          return tailSet(fromElement);
        }
        if(fromElement==Byte.MAX_VALUE){
           return new AbstractByteSet.EmptyView.Checked.Ascending(Byte.MAX_VALUE+1);
        }
        final int f;
        return new CheckedSubSet.TailSet.Ascending(this,f=fromElement+1,(this.modCountAndSize&(~0x1ff))|((ByteSetImpl)this).countTailSetElements(f));
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,boolean fromInclusive,byte toElement,boolean toInclusive){
        final int f,t;
        switch(Integer.signum((t=(toInclusive?toElement+1:toElement))-(f=(fromInclusive?fromElement:fromElement+1)))){
        case 1:
          if(t==128){
            if(f==-128){
              return this;
            }else{
              return new CheckedSubSet.TailSet.Ascending(this,f,(this.modCountAndSize&(~0x1ff))|((ByteSetImpl)this).countTailSetElements(f));
            }
          }else{
            if(f==-128){
              return new CheckedSubSet.HeadSet.Ascending(this,t,(this.modCountAndSize&(~0x1ff))|((ByteSetImpl)this).countHeadSetElements(t));
            }else{
              return new CheckedSubSet.BodySet.Ascending(this,(t<<8)|(f&0xff),(this.modCountAndSize&(~0x1ff))|((ByteSetImpl)this).countSubSetElements(f,t));
            }
          }
        case 0:
          return new AbstractByteSet.EmptyView.Checked.Ascending(t);
        default:
          throw new IllegalArgumentException("out of bounds");
        }
      }
      @Override public OmniNavigableSet.OfByte descendingSet(){
        return new AbstractCheckedFullView.Descending(this);
      }
      @Override public String toString(){
        int numLeft;
        if((numLeft=this.modCountAndSize&0x1ff)!=0){
          final byte[] buffer;
          int bufferOffset;
          (buffer=new byte[6*numLeft])[bufferOffset=0]='[';
          int offset=Byte.MIN_VALUE;
          goToEnd:for(long word=this.word0;;){
            if((word&(1L<<offset))!=0){
              bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
              if(--numLeft==0){
                 break goToEnd;
              }
              buffer[bufferOffset]=',';
              buffer[++bufferOffset]=' ';
            }
            if(++offset==-64){
              for(word=this.word1;;){
                if((word&(1L<<offset))!=0){
                  bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
                  if(--numLeft==0){
                    break goToEnd;
                  }
                  buffer[bufferOffset]=',';
                  buffer[++bufferOffset]=' ';
                }
                if(++offset==0){
                  for(word=this.word2;;){
                    if((word&(1L<<offset))!=0){
                      bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
                      if(--numLeft==0){
                        break goToEnd;
                      }
                      buffer[bufferOffset]=',';
                      buffer[++bufferOffset]=' ';
                    }
                    if(++offset==64){
                      for(word=this.word3;;++offset){
                        if((word&(1L<<offset))!=0){
                          bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
                          if(--numLeft==0){
                            break goToEnd;
                          }
                          buffer[bufferOffset]=',';
                          buffer[++bufferOffset]=' ';
                        }
                      }
                    }
                  }
                }
              }
            }
          }
          buffer[++bufferOffset]=']';
          return new String(buffer,0,bufferOffset+1,ToStringUtil.IOS8859CharSet);
        }
        return "[]";
      }
      @Override public void forEach(ByteConsumer action){
        final int modCountAndSize;
        int numLeft;
        if((numLeft=(modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
          try{
            super.uncheckedAscendingForEach(numLeft,action);
          }finally{
            CheckedCollection.checkModCount(modCountAndSize,this.modCountAndSize);
          }
        }
      }
      @Override public void forEach(Consumer<? super Byte> action){
        final int modCountAndSize;
        int numLeft;
        if((numLeft=(modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
          try{
            super.uncheckedAscendingForEach(numLeft,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCountAndSize,this.modCountAndSize);
          }
        }
      }
      @SuppressWarnings("unchecked")
      @Override public <T> T[] toArray(T[] dst){
        int numLeft;
        if((numLeft=this.modCountAndSize&0x1ff)!=0){
          dst=OmniArray.uncheckedArrResize(numLeft,dst);
          int offset=Byte.MAX_VALUE;
          outer:for(long word=this.word3;;){
            if((word&(1L<<offset))!=0){
              dst[--numLeft]=(T)(Byte)(byte)(offset);
              if(numLeft==0){
                break outer;
              }
            }
            if(--offset==63){
              for(word=this.word2;;){
                if((word&(1L<<offset))!=0){
                  dst[--numLeft]=(T)(Byte)(byte)(offset);
                  if(numLeft==0){
                    break outer;
                  }
                }
                if(--offset==-1){
                  for(word=this.word1;;){
                    if((word&(1L<<offset))!=0){
                      dst[--numLeft]=(T)(Byte)(byte)(offset);
                      if(numLeft==0){
                        break outer;
                      }
                    }
                    if(--offset==-65){
                      for(word=this.word0;;--offset){
                        if((word&(1L<<offset))!=0){
                          dst[--numLeft]=(T)(Byte)(byte)(offset);
                          if(numLeft==0){
                            break outer;
                          }
                        }
                      }
                    }
                  }
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
        int numLeft;
        final T[] dst;
        final int modCountAndSize=this.modCountAndSize;
        try{
          dst=arrConstructor.apply((numLeft=modCountAndSize&0x1ff));
        }finally{
          CheckedCollection.checkModCount(modCountAndSize,this.modCountAndSize);
        }
        if(numLeft!=0){
          int offset=Byte.MAX_VALUE;
          outer:for(long word=this.word3;;){
            if((word&(1L<<offset))!=0){
              dst[--numLeft]=(T)(Byte)(byte)(offset);
              if(numLeft==0){
                break outer;
              }
            }
            if(--offset==63){
              for(word=this.word2;;){
                if((word&(1L<<offset))!=0){
                  dst[--numLeft]=(T)(Byte)(byte)(offset);
                  if(numLeft==0){
                    break outer;
                  }
                }
                if(--offset==-1){
                  for(word=this.word1;;){
                    if((word&(1L<<offset))!=0){
                      dst[--numLeft]=(T)(Byte)(byte)(offset);
                      if(numLeft==0){
                        break outer;
                      }
                    }
                    if(--offset==-65){
                      for(word=this.word0;;--offset){
                        if((word&(1L<<offset))!=0){
                          dst[--numLeft]=(T)(Byte)(byte)(offset);
                          if(numLeft==0){
                            break outer;
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
        return dst;
      }
      @Override public Byte[] toArray(){
        int numLeft;
        if((numLeft=this.modCountAndSize&0x1ff)!=0){
          final Byte[] dst=new Byte[numLeft];
        int offset=Byte.MAX_VALUE;
        outer:for(long word=this.word3;;){
          if((word&(1L<<offset))!=0){
            dst[--numLeft]=(Byte)(byte)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(--offset==63){
            for(word=this.word2;;){
              if((word&(1L<<offset))!=0){
                dst[--numLeft]=(Byte)(byte)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(--offset==-1){
                for(word=this.word1;;){
                  if((word&(1L<<offset))!=0){
                    dst[--numLeft]=(Byte)(byte)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(--offset==-65){
                    for(word=this.word0;;--offset){
                      if((word&(1L<<offset))!=0){
                        dst[--numLeft]=(Byte)(byte)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_BOXED_ARR;
      }
      @Override public byte[] toByteArray(){
        int numLeft;
        if((numLeft=this.modCountAndSize&0x1ff)!=0){
          final byte[] dst=new byte[numLeft];
        int offset=Byte.MAX_VALUE;
        outer:for(long word=this.word3;;){
          if((word&(1L<<offset))!=0){
            dst[--numLeft]=(byte)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(--offset==63){
            for(word=this.word2;;){
              if((word&(1L<<offset))!=0){
                dst[--numLeft]=(byte)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(--offset==-1){
                for(word=this.word1;;){
                  if((word&(1L<<offset))!=0){
                    dst[--numLeft]=(byte)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(--offset==-65){
                    for(word=this.word0;;--offset){
                      if((word&(1L<<offset))!=0){
                        dst[--numLeft]=(byte)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_ARR;
      }
      @Override public short[] toShortArray(){
        int numLeft;
        if((numLeft=this.modCountAndSize&0x1ff)!=0){
          final short[] dst=new short[numLeft];
        int offset=Byte.MAX_VALUE;
        outer:for(long word=this.word3;;){
          if((word&(1L<<offset))!=0){
            dst[--numLeft]=(short)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(--offset==63){
            for(word=this.word2;;){
              if((word&(1L<<offset))!=0){
                dst[--numLeft]=(short)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(--offset==-1){
                for(word=this.word1;;){
                  if((word&(1L<<offset))!=0){
                    dst[--numLeft]=(short)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(--offset==-65){
                    for(word=this.word0;;--offset){
                      if((word&(1L<<offset))!=0){
                        dst[--numLeft]=(short)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfShort.DEFAULT_ARR;
      }
      @Override public int[] toIntArray(){
        int numLeft;
        if((numLeft=this.modCountAndSize&0x1ff)!=0){
          final int[] dst=new int[numLeft];
        int offset=Byte.MAX_VALUE;
        outer:for(long word=this.word3;;){
          if((word&(1L<<offset))!=0){
            dst[--numLeft]=(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(--offset==63){
            for(word=this.word2;;){
              if((word&(1L<<offset))!=0){
                dst[--numLeft]=(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(--offset==-1){
                for(word=this.word1;;){
                  if((word&(1L<<offset))!=0){
                    dst[--numLeft]=(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(--offset==-65){
                    for(word=this.word0;;--offset){
                      if((word&(1L<<offset))!=0){
                        dst[--numLeft]=(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfInt.DEFAULT_ARR;
      }
      @Override public long[] toLongArray(){
        int numLeft;
        if((numLeft=this.modCountAndSize&0x1ff)!=0){
          final long[] dst=new long[numLeft];
        long offset=Byte.MAX_VALUE;
        outer:for(long word=this.word3;;){
          if((word&(1L<<offset))!=0){
            dst[--numLeft]=(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(--offset==63){
            for(word=this.word2;;){
              if((word&(1L<<offset))!=0){
                dst[--numLeft]=(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(--offset==-1){
                for(word=this.word1;;){
                  if((word&(1L<<offset))!=0){
                    dst[--numLeft]=(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(--offset==-65){
                    for(word=this.word0;;--offset){
                      if((word&(1L<<offset))!=0){
                        dst[--numLeft]=(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
      }
      @Override public float[] toFloatArray(){
        int numLeft;
        if((numLeft=this.modCountAndSize&0x1ff)!=0){
          final float[] dst=new float[numLeft];
        int offset=Byte.MAX_VALUE;
        outer:for(long word=this.word3;;){
          if((word&(1L<<offset))!=0){
            dst[--numLeft]=(float)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(--offset==63){
            for(word=this.word2;;){
              if((word&(1L<<offset))!=0){
                dst[--numLeft]=(float)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(--offset==-1){
                for(word=this.word1;;){
                  if((word&(1L<<offset))!=0){
                    dst[--numLeft]=(float)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(--offset==-65){
                    for(word=this.word0;;--offset){
                      if((word&(1L<<offset))!=0){
                        dst[--numLeft]=(float)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
      }
      @Override public double[] toDoubleArray(){
        int numLeft;
        if((numLeft=this.modCountAndSize&0x1ff)!=0){
          final double[] dst=new double[numLeft];
        int offset=Byte.MAX_VALUE;
        outer:for(long word=this.word3;;){
          if((word&(1L<<offset))!=0){
            dst[--numLeft]=(double)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(--offset==63){
            for(word=this.word2;;){
              if((word&(1L<<offset))!=0){
                dst[--numLeft]=(double)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(--offset==-1){
                for(word=this.word1;;){
                  if((word&(1L<<offset))!=0){
                    dst[--numLeft]=(double)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(--offset==-65){
                    for(word=this.word0;;--offset){
                      if((word&(1L<<offset))!=0){
                        dst[--numLeft]=(double)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfDouble.DEFAULT_ARR;
      }
    }
    public static class Descending extends Checked{
      public Descending(){
        super();
      }
      public Descending(ByteSetImpl.Checked that){
        super(that);
      }
      public Descending(ByteSetImpl.Unchecked that){
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
      public Descending(OmniCollection.OfRef<? extends Byte> that){
        super(that);
      }
      public Descending(Collection<? extends Byte> that){
        super(that);
      }
      @Override public Object clone(){
        return new Descending(this);
      }
      @Override public OmniIterator.OfByte iterator(){
        return new CheckedDescendingItr(this);
      }
      @Override public OmniIterator.OfByte descendingIterator(){
        return new CheckedAscendingItr(this);
      }
      @Override public ByteComparator comparator(){
        return ByteComparator::descendingCompare;
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement){
        if(toElement==Byte.MIN_VALUE){
          return this;
        }
        return new CheckedSubSet.TailSet.Descending(this,toElement,(this.modCountAndSize&(~0x1ff))|((ByteSetImpl)this).countTailSetElements(toElement));
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
        if(fromElement==Byte.MAX_VALUE){
          return this;
        }
        final int f;
        return new CheckedSubSet.HeadSet.Descending(this,f=fromElement+1,(this.modCountAndSize&(~0x1ff))|((ByteSetImpl)this).countHeadSetElements(f));
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
        final int f;
        switch(Integer.signum((f=fromElement+1)-toElement)){
        case 1:
          if(f==128){
            if(toElement==-128){
              return this;
            }else{
              return new CheckedSubSet.TailSet.Descending(this,toElement,(this.modCountAndSize&(~0x1ff))|((ByteSetImpl)this).countTailSetElements(toElement));
            }
          }else{
            if(toElement==-128){
              return new CheckedSubSet.HeadSet.Descending(this,f,(this.modCountAndSize&(~0x1ff))|((ByteSetImpl)this).countHeadSetElements(f));
            }else{
              return new CheckedSubSet.BodySet.Descending(this,(f<<8)|(toElement&0xff),(this.modCountAndSize&(~0x1ff))|((ByteSetImpl)this).countSubSetElements(toElement,f));
            }
          }
        case 0:
          return new AbstractByteSet.EmptyView.Checked.Descending(f);
        default:
          throw new IllegalArgumentException("out of bounds");
        }
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
        if(inclusive){
          return headSet(toElement);
        }
        if(toElement==Byte.MAX_VALUE){
          return new AbstractByteSet.EmptyView.Checked.Descending(128);
        }
        final int t;
        return new CheckedSubSet.TailSet.Descending(this,t=toElement+1,(this.modCountAndSize&(~0x1ff))|((ByteSetImpl)this).countTailSetElements(t));
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement,boolean inclusive){
        if(inclusive){
          return tailSet(fromElement);
        }
        if(fromElement==Byte.MIN_VALUE){
          return new AbstractByteSet.EmptyView.Checked.Descending(Byte.MIN_VALUE);
        }
        return new CheckedSubSet.HeadSet.Descending(this,fromElement,(this.modCountAndSize&(~0x1ff))|((ByteSetImpl)this).countHeadSetElements(fromElement));
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,boolean fromInclusive,byte toElement,boolean toInclusive){
        final int f,t;
        switch(Integer.signum((f=(fromInclusive?fromElement+1:fromElement))-(t=(toInclusive?toElement:toElement+1)))){
        case 1:
          if(t==-128){
            if(f==128){
              return this;
            }else{
              return new CheckedSubSet.HeadSet.Descending(this,f,(this.modCountAndSize&(~0x1ff))|((ByteSetImpl)this).countHeadSetElements(f));
            }
          }else{
            if(f==128){
              return new CheckedSubSet.TailSet.Descending(this,t,(this.modCountAndSize&(~0x1ff))|((ByteSetImpl)this).countTailSetElements(t));
            }else{
              return new CheckedSubSet.BodySet.Descending(this,(f<<8)|(t&0xff),(this.modCountAndSize&(~0x1ff))|((ByteSetImpl)this).countSubSetElements(t,f));
            }
          }
        case 0:
          return new AbstractByteSet.EmptyView.Checked.Descending(f);
        default:
          throw new IllegalArgumentException("out of bounds");
        }
      }
      @Override public OmniNavigableSet.OfByte descendingSet(){
        return new AbstractCheckedFullView.Ascending(this);
      }
      @Override public Byte lower(byte val){
        return super.higher(val);
      }
      @Override public Byte floor(byte val){
        return super.ceiling(val);
      }
      @Override public Byte higher(byte val){
        return super.lower(val);
      }
      @Override public Byte ceiling(byte val){
        return super.floor(val);
      }
      @Override public Byte pollFirst(){
        return super.pollLast();
      }
      @Override public Byte pollLast(){
        return super.pollFirst();
      }
      @Override public byte lowerByte(byte val){
        return super.higherByte(val);
      }
      @Override public byte byteFloor(byte val){
        return super.byteCeiling(val);
      }
      @Override public byte higherByte(byte val){
        return super.lowerByte(val);
      }
      @Override public byte byteCeiling(byte val){
        return super.byteFloor(val);
      }
      @Override public byte pollFirstByte(){
        return super.pollLastByte();
      }
      @Override public byte pollLastByte(){
        return super.pollFirstByte();
      }
      @Override public short lowerShort(short val){
        return super.higherShort(val);
      }
      @Override public short shortFloor(short val){
        return super.shortCeiling(val);
      }
      @Override public short higherShort(short val){
        return super.lowerShort(val);
      }
      @Override public short shortCeiling(short val){
        return super.shortFloor(val);
      }
      @Override public short pollFirstShort(){
        return super.pollLastShort();
      }
      @Override public short pollLastShort(){
        return super.pollFirstShort();
      }
      @Override public int firstInt(){
        return super.lastInt();
      }
      @Override public int lastInt(){
        return super.firstInt();
      }
      @Override public int lowerInt(int val){
        return super.higherInt(val);
      }
      @Override public int intFloor(int val){
        return super.intCeiling(val);
      }
      @Override public int higherInt(int val){
        return super.lowerInt(val);
      }
      @Override public int intCeiling(int val){
        return super.intFloor(val);
      }
      @Override public int pollFirstInt(){
        return super.pollLastInt();
      }
      @Override public int pollLastInt(){
        return super.pollFirstInt();
      }
      @Override public long lowerLong(long val){
        return super.higherLong(val);
      }
      @Override public long longFloor(long val){
        return super.longCeiling(val);
      }
      @Override public long higherLong(long val){
        return super.lowerLong(val);
      }
      @Override public long longCeiling(long val){
        return super.longFloor(val);
      }
      @Override public long pollFirstLong(){
        return super.pollLastLong();
      }
      @Override public long pollLastLong(){
        return super.pollFirstLong();
      }
      @Override public float lowerFloat(float val){
        return super.higherFloat(val);
      }
      @Override public float floatFloor(float val){
        return super.floatCeiling(val);
      }
      @Override public float higherFloat(float val){
        return super.lowerFloat(val);
      }
      @Override public float floatCeiling(float val){
        return super.floatFloor(val);
      }
      @Override public float pollFirstFloat(){
        return super.pollLastFloat();
      }
      @Override public float pollLastFloat(){
        return super.pollFirstFloat();
      }
      @Override public double lowerDouble(double val){
        return super.higherDouble(val);
      }
      @Override public double doubleFloor(double val){
        return super.doubleCeiling(val);
      }
      @Override public double higherDouble(double val){
        return super.lowerDouble(val);
      }
      @Override public double doubleCeiling(double val){
        return super.doubleFloor(val);
      }
      @Override public double pollFirstDouble(){
        return super.pollLastDouble();
      }
      @Override public double pollLastDouble(){
        return super.pollFirstDouble();
      }
      @Override public String toString(){
        int numLeft;
        if((numLeft=this.modCountAndSize&0x1ff)!=0){
          final byte[] buffer;
          int bufferOffset;
          (buffer=new byte[6*numLeft])[bufferOffset=0]='[';
          int offset=Byte.MAX_VALUE;
          goToEnd:for(long word=this.word3;;){
            if((word&(1L<<offset))!=0){
              bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
              if(--numLeft==0){
                 break goToEnd;
              }
              buffer[bufferOffset]=',';
              buffer[++bufferOffset]=' ';
            }
            if(--offset==63){
              for(word=this.word2;;){
                if((word&(1L<<offset))!=0){
                  bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
                  if(--numLeft==0){
                    break goToEnd;
                  }
                  buffer[bufferOffset]=',';
                  buffer[++bufferOffset]=' ';
                }
                if(--offset==-1){
                  for(word=this.word1;;){
                    if((word&(1L<<offset))!=0){
                      bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
                      if(--numLeft==0){
                        break goToEnd;
                      }
                      buffer[bufferOffset]=',';
                      buffer[++bufferOffset]=' ';
                    }
                    if(--offset==-65){
                      for(word=this.word0;;--offset){
                        if((word&(1L<<offset))!=0){
                          bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
                          if(--numLeft==0){
                            break goToEnd;
                          }
                          buffer[bufferOffset]=',';
                          buffer[++bufferOffset]=' ';
                        }
                      }
                    }
                  }
                }
              }
            }
          }
          buffer[++bufferOffset]=']';
          return new String(buffer,0,bufferOffset+1,ToStringUtil.IOS8859CharSet);
        }
        return "[]";
      }
      @Override public void forEach(ByteConsumer action){
        final int modCountAndSize;
        int numLeft;
        if((numLeft=(modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
          try{
            super.uncheckedDescendingForEach(numLeft,action);
          }finally{
            CheckedCollection.checkModCount(modCountAndSize,this.modCountAndSize);
          }
        }
      }
      @Override public void forEach(Consumer<? super Byte> action){
        final int modCountAndSize;
        int numLeft;
        if((numLeft=(modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
          try{
            super.uncheckedDescendingForEach(numLeft,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCountAndSize,this.modCountAndSize);
          }
        }
      }
      @SuppressWarnings("unchecked")
      @Override public <T> T[] toArray(T[] dst){
        int numLeft;
        if((numLeft=this.modCountAndSize&0x1ff)!=0){
          dst=OmniArray.uncheckedArrResize(numLeft,dst);
          int offset=Byte.MIN_VALUE;
          outer:for(long word=this.word0;;){
            if((word&(1L<<offset))!=0){
              dst[--numLeft]=(T)(Byte)(byte)(offset);
              if(numLeft==0){
                break outer;
              }
            }
            if(++offset==-64){
              for(word=this.word1;;){
                if((word&(1L<<offset))!=0){
                  dst[--numLeft]=(T)(Byte)(byte)(offset);
                  if(numLeft==0){
                    break outer;
                  }
                }
                if(++offset==0){
                  for(word=this.word2;;){
                    if((word&(1L<<offset))!=0){
                      dst[--numLeft]=(T)(Byte)(byte)(offset);
                      if(numLeft==0){
                        break outer;
                      }
                    }
                    if(++offset==64){
                      for(word=this.word3;;++offset){
                        if((word&(1L<<offset))!=0){
                          dst[--numLeft]=(T)(Byte)(byte)(offset);
                          if(numLeft==0){
                            break outer;
                          }
                        }
                      }
                    }
                  }
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
        int numLeft;
        final T[] dst;
        final int modCountAndSize=this.modCountAndSize;
        try{
          dst=arrConstructor.apply((numLeft=modCountAndSize&0x1ff));
        }finally{
          CheckedCollection.checkModCount(modCountAndSize,this.modCountAndSize);
        }
        if(numLeft!=0){
          int offset=Byte.MIN_VALUE;
          outer:for(long word=this.word0;;){
            if((word&(1L<<offset))!=0){
              dst[--numLeft]=(T)(Byte)(byte)(offset);
              if(numLeft==0){
                break outer;
              }
            }
            if(++offset==-64){
              for(word=this.word1;;){
                if((word&(1L<<offset))!=0){
                  dst[--numLeft]=(T)(Byte)(byte)(offset);
                  if(numLeft==0){
                    break outer;
                  }
                }
                if(++offset==0){
                  for(word=this.word2;;){
                    if((word&(1L<<offset))!=0){
                      dst[--numLeft]=(T)(Byte)(byte)(offset);
                      if(numLeft==0){
                        break outer;
                      }
                    }
                    if(++offset==64){
                      for(word=this.word3;;++offset){
                        if((word&(1L<<offset))!=0){
                          dst[--numLeft]=(T)(Byte)(byte)(offset);
                          if(numLeft==0){
                            break outer;
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
        return dst;
      }
      @Override public Byte[] toArray(){
        int numLeft;
        if((numLeft=this.modCountAndSize&0x1ff)!=0){
          final Byte[] dst=new Byte[numLeft];
        int offset=Byte.MIN_VALUE;
        outer:for(long word=this.word0;;){
          if((word&(1L<<offset))!=0){
            dst[--numLeft]=(Byte)(byte)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(++offset==-64){
            for(word=this.word1;;){
              if((word&(1L<<offset))!=0){
                dst[--numLeft]=(Byte)(byte)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(++offset==0){
                for(word=this.word2;;){
                  if((word&(1L<<offset))!=0){
                    dst[--numLeft]=(Byte)(byte)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(++offset==64){
                    for(word=this.word3;;++offset){
                      if((word&(1L<<offset))!=0){
                        dst[--numLeft]=(Byte)(byte)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_BOXED_ARR;
      }
      @Override public byte[] toByteArray(){
        int numLeft;
        if((numLeft=this.modCountAndSize&0x1ff)!=0){
          final byte[] dst=new byte[numLeft];
        int offset=Byte.MIN_VALUE;
        outer:for(long word=this.word0;;){
          if((word&(1L<<offset))!=0){
            dst[--numLeft]=(byte)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(++offset==-64){
            for(word=this.word1;;){
              if((word&(1L<<offset))!=0){
                dst[--numLeft]=(byte)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(++offset==0){
                for(word=this.word2;;){
                  if((word&(1L<<offset))!=0){
                    dst[--numLeft]=(byte)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(++offset==64){
                    for(word=this.word3;;++offset){
                      if((word&(1L<<offset))!=0){
                        dst[--numLeft]=(byte)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_ARR;
      }
      @Override public short[] toShortArray(){
        int numLeft;
        if((numLeft=this.modCountAndSize&0x1ff)!=0){
          final short[] dst=new short[numLeft];
        int offset=Byte.MIN_VALUE;
        outer:for(long word=this.word0;;){
          if((word&(1L<<offset))!=0){
            dst[--numLeft]=(short)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(++offset==-64){
            for(word=this.word1;;){
              if((word&(1L<<offset))!=0){
                dst[--numLeft]=(short)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(++offset==0){
                for(word=this.word2;;){
                  if((word&(1L<<offset))!=0){
                    dst[--numLeft]=(short)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(++offset==64){
                    for(word=this.word3;;++offset){
                      if((word&(1L<<offset))!=0){
                        dst[--numLeft]=(short)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfShort.DEFAULT_ARR;
      }
      @Override public int[] toIntArray(){
        int numLeft;
        if((numLeft=this.modCountAndSize&0x1ff)!=0){
          final int[] dst=new int[numLeft];
        int offset=Byte.MIN_VALUE;
        outer:for(long word=this.word0;;){
          if((word&(1L<<offset))!=0){
            dst[--numLeft]=(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(++offset==-64){
            for(word=this.word1;;){
              if((word&(1L<<offset))!=0){
                dst[--numLeft]=(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(++offset==0){
                for(word=this.word2;;){
                  if((word&(1L<<offset))!=0){
                    dst[--numLeft]=(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(++offset==64){
                    for(word=this.word3;;++offset){
                      if((word&(1L<<offset))!=0){
                        dst[--numLeft]=(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfInt.DEFAULT_ARR;
      }
      @Override public long[] toLongArray(){
        int numLeft;
        if((numLeft=this.modCountAndSize&0x1ff)!=0){
          final long[] dst=new long[numLeft];
        long offset=Byte.MIN_VALUE;
        outer:for(long word=this.word0;;){
          if((word&(1L<<offset))!=0){
            dst[--numLeft]=(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(++offset==-64){
            for(word=this.word1;;){
              if((word&(1L<<offset))!=0){
                dst[--numLeft]=(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(++offset==0){
                for(word=this.word2;;){
                  if((word&(1L<<offset))!=0){
                    dst[--numLeft]=(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(++offset==64){
                    for(word=this.word3;;++offset){
                      if((word&(1L<<offset))!=0){
                        dst[--numLeft]=(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
      }
      @Override public float[] toFloatArray(){
        int numLeft;
        if((numLeft=this.modCountAndSize&0x1ff)!=0){
          final float[] dst=new float[numLeft];
        int offset=Byte.MIN_VALUE;
        outer:for(long word=this.word0;;){
          if((word&(1L<<offset))!=0){
            dst[--numLeft]=(float)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(++offset==-64){
            for(word=this.word1;;){
              if((word&(1L<<offset))!=0){
                dst[--numLeft]=(float)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(++offset==0){
                for(word=this.word2;;){
                  if((word&(1L<<offset))!=0){
                    dst[--numLeft]=(float)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(++offset==64){
                    for(word=this.word3;;++offset){
                      if((word&(1L<<offset))!=0){
                        dst[--numLeft]=(float)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
      }
      @Override public double[] toDoubleArray(){
        int numLeft;
        if((numLeft=this.modCountAndSize&0x1ff)!=0){
          final double[] dst=new double[numLeft];
        int offset=Byte.MIN_VALUE;
        outer:for(long word=this.word0;;){
          if((word&(1L<<offset))!=0){
            dst[--numLeft]=(double)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(++offset==-64){
            for(word=this.word1;;){
              if((word&(1L<<offset))!=0){
                dst[--numLeft]=(double)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(++offset==0){
                for(word=this.word2;;){
                  if((word&(1L<<offset))!=0){
                    dst[--numLeft]=(double)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(++offset==64){
                    for(word=this.word3;;++offset){
                      if((word&(1L<<offset))!=0){
                        dst[--numLeft]=(double)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfDouble.DEFAULT_ARR;
      }
    }
  }
  public abstract static class Unchecked extends ByteSetImpl implements Serializable{
    public static class Ascending extends Unchecked{
      private static final long serialVersionUID=1L;
      public Ascending(ByteSetImpl.Checked that){
        super(that);
      }
      public Ascending(ByteSetImpl.Unchecked that){
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
      public Ascending(OmniCollection.OfRef<? extends Byte> that){
        super(that);
      }
      public Ascending(Collection<? extends Byte> that){
        super(that);
      }
      @Override public Object clone(){
        return new Ascending(this);
      }
      @Override public OmniIterator.OfByte iterator(){
        return new UncheckedAscendingItr(this);
      }
      @Override public OmniIterator.OfByte descendingIterator(){
        return new UncheckedDescendingItr(this);
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement){
        if(toElement==Byte.MAX_VALUE){
          return this;
        }
        final int t;
        return new UncheckedSubSet.HeadSet.Ascending(this,t=toElement+1,((ByteSetImpl)this).countHeadSetElements(t));
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
        if(fromElement==Byte.MIN_VALUE){
          return this;
        }
        return new UncheckedSubSet.TailSet.Ascending(this,fromElement,((ByteSetImpl)this).countTailSetElements(fromElement));
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
        if(toElement>=fromElement){
          if(toElement==127){
            if(fromElement==-128){
              return this;
            }else{
              return new UncheckedSubSet.TailSet.Ascending(this,fromElement,((ByteSetImpl)this).countTailSetElements(fromElement));
            }
          }else{
            final int t=toElement+1;
            if(fromElement==-128){
              return new UncheckedSubSet.HeadSet.Ascending(this,t,((ByteSetImpl)this).countHeadSetElements(t));
            }else{
              return new UncheckedSubSet.BodySet.Ascending(this,(t<<8)|(fromElement&0xff),((ByteSetImpl)this).countSubSetElements(fromElement,t));
            }
          }
        }
        return AbstractByteSet.EmptyView.ASCENDING;
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
        if(inclusive){
          return headSet(toElement);
        }
        if(toElement==Byte.MIN_VALUE){
          return AbstractByteSet.EmptyView.ASCENDING;
        }
        return new UncheckedSubSet.HeadSet.Ascending(this,toElement,((ByteSetImpl)this).countHeadSetElements(toElement));
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement,boolean inclusive){
        if(inclusive){
          return tailSet(fromElement);
        }
        if(fromElement==Byte.MAX_VALUE){
           return AbstractByteSet.EmptyView.ASCENDING;
        }
        final int f;
        return new UncheckedSubSet.TailSet.Ascending(this,f=fromElement+1,((ByteSetImpl)this).countTailSetElements(f));
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,boolean fromInclusive,byte toElement,boolean toInclusive){
        final int f,t;
        if((t=(toInclusive?toElement+1:toElement))>(f=(fromInclusive?fromElement:fromElement+1))){
          if(t==128){
            if(f==-128){
              return this;
            }else{
              return new UncheckedSubSet.TailSet.Ascending(this,f,((ByteSetImpl)this).countTailSetElements(f));
            }
          }else{
            if(f==-128){
              return new UncheckedSubSet.HeadSet.Ascending(this,t,((ByteSetImpl)this).countHeadSetElements(t));
            }else{
              return new UncheckedSubSet.BodySet.Ascending(this,(t<<8)|(f&0xff),((ByteSetImpl)this).countSubSetElements(f,t));
            }
          }
        }
        return AbstractByteSet.EmptyView.ASCENDING;
      }
      @Override public OmniNavigableSet.OfByte descendingSet(){
        return new AbstractUncheckedFullView.Descending(this);
      }
      @Override public String toString(){
        int numLeft;
        final long word0,word1,word2,word3;
        if((numLeft=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
          final byte[] buffer;
          int bufferOffset;
          (buffer=new byte[6*numLeft])[bufferOffset=0]='[';
          goToEnd:for(int offset=Byte.MIN_VALUE;;){
            if((word0&(1L<<offset))!=0){
              bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
              if(--numLeft==0){
                 break goToEnd;
              }
              buffer[bufferOffset]=',';
              buffer[++bufferOffset]=' ';
            }
            if(++offset==-64){
              for(;;){
                if((word1&(1L<<offset))!=0){
                  bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
                  if(--numLeft==0){
                    break goToEnd;
                  }
                  buffer[bufferOffset]=',';
                  buffer[++bufferOffset]=' ';
                }
                if(++offset==0){
                  for(;;){
                    if((word2&(1L<<offset))!=0){
                      bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
                      if(--numLeft==0){
                        break goToEnd;
                      }
                      buffer[bufferOffset]=',';
                      buffer[++bufferOffset]=' ';
                    }
                    if(++offset==64){
                      for(;;++offset){
                        if((word3&(1L<<offset))!=0){
                          bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
                          if(--numLeft==0){
                            break goToEnd;
                          }
                          buffer[bufferOffset]=',';
                          buffer[++bufferOffset]=' ';
                        }
                      }
                    }
                  }
                }
              }
            }
          }
          buffer[++bufferOffset]=']';
          return new String(buffer,0,bufferOffset+1,ToStringUtil.IOS8859CharSet);
        }
        return "[]";
      }
      @Override public void forEach(ByteConsumer action){
        Unchecked.Ascending.uncheckedWordForEach(this.word0,-128,-64,action);
        Unchecked.Ascending.uncheckedWordForEach(this.word1,-64,0,action);
        Unchecked.Ascending.uncheckedWordForEach(this.word2,0,64,action);
        Unchecked.Ascending.uncheckedWordForEach(this.word3,64,128,action);
      }
      private static void uncheckedWordForEach(long word,int offset,int bound,ByteConsumer action){
        do{
          if((word&(1L<<offset))!=0){
            action.accept((byte)offset);
          } 
        }while(++offset!=bound);
      }
      @SuppressWarnings("unchecked")
      @Override public <T> T[] toArray(T[] dst){
        int numLeft;
        final long word0,word1,word2,word3;
        if((numLeft=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
          dst=OmniArray.uncheckedArrResize(numLeft,dst);
          int offset=Byte.MAX_VALUE;
          outer:for(;;){
            if((word3&(1L<<offset))!=0){
              dst[--numLeft]=(T)(Byte)(byte)(offset);
              if(numLeft==0){
                break outer;
              }
            }
            if(--offset==63){
              for(;;){
                if((word2&(1L<<offset))!=0){
                  dst[--numLeft]=(T)(Byte)(byte)(offset);
                  if(numLeft==0){
                    break outer;
                  }
                }
                if(--offset==-1){
                  for(;;){
                    if((word1&(1L<<offset))!=0){
                      dst[--numLeft]=(T)(Byte)(byte)(offset);
                      if(numLeft==0){
                        break outer;
                      }
                    }
                    if(--offset==-65){
                      for(;;--offset){
                        if((word0&(1L<<offset))!=0){
                          dst[--numLeft]=(T)(Byte)(byte)(offset);
                          if(numLeft==0){
                            break outer;
                          }
                        }
                      }
                    }
                  }
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
        int numLeft;
        final long word0,word1,word2,word3;
        final T[] dst=arrConstructor.apply(numLeft=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3));
        if(numLeft!=0){
          int offset=Byte.MAX_VALUE;
          outer:for(;;){
            if((word3&(1L<<offset))!=0){
              dst[--numLeft]=(T)(Byte)(byte)(offset);
              if(numLeft==0){
                break outer;
              }
            }
            if(--offset==63){
              for(;;){
                if((word2&(1L<<offset))!=0){
                  dst[--numLeft]=(T)(Byte)(byte)(offset);
                  if(numLeft==0){
                    break outer;
                  }
                }
                if(--offset==-1){
                  for(;;){
                    if((word1&(1L<<offset))!=0){
                      dst[--numLeft]=(T)(Byte)(byte)(offset);
                      if(numLeft==0){
                        break outer;
                      }
                    }
                    if(--offset==-65){
                      for(;;--offset){
                        if((word0&(1L<<offset))!=0){
                          dst[--numLeft]=(T)(Byte)(byte)(offset);
                          if(numLeft==0){
                            break outer;
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
        return dst;
      }
      @Override public Byte[] toArray(){
        int numLeft;
        final long word0,word1,word2,word3;
        if((numLeft=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
          final Byte[] dst=new Byte[numLeft];
        int offset=Byte.MAX_VALUE;
        outer:for(;;){
          if((word3&(1L<<offset))!=0){
            dst[--numLeft]=(Byte)(byte)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(--offset==63){
            for(;;){
              if((word2&(1L<<offset))!=0){
                dst[--numLeft]=(Byte)(byte)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(--offset==-1){
                for(;;){
                  if((word1&(1L<<offset))!=0){
                    dst[--numLeft]=(Byte)(byte)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(--offset==-65){
                    for(;;--offset){
                      if((word0&(1L<<offset))!=0){
                        dst[--numLeft]=(Byte)(byte)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_BOXED_ARR;
      }
      @Override public byte[] toByteArray(){
        int numLeft;
        final long word0,word1,word2,word3;
        if((numLeft=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
          final byte[] dst=new byte[numLeft];
        int offset=Byte.MAX_VALUE;
        outer:for(;;){
          if((word3&(1L<<offset))!=0){
            dst[--numLeft]=(byte)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(--offset==63){
            for(;;){
              if((word2&(1L<<offset))!=0){
                dst[--numLeft]=(byte)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(--offset==-1){
                for(;;){
                  if((word1&(1L<<offset))!=0){
                    dst[--numLeft]=(byte)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(--offset==-65){
                    for(;;--offset){
                      if((word0&(1L<<offset))!=0){
                        dst[--numLeft]=(byte)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_ARR;
      }
      @Override public short[] toShortArray(){
        int numLeft;
        final long word0,word1,word2,word3;
        if((numLeft=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
          final short[] dst=new short[numLeft];
        int offset=Byte.MAX_VALUE;
        outer:for(;;){
          if((word3&(1L<<offset))!=0){
            dst[--numLeft]=(short)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(--offset==63){
            for(;;){
              if((word2&(1L<<offset))!=0){
                dst[--numLeft]=(short)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(--offset==-1){
                for(;;){
                  if((word1&(1L<<offset))!=0){
                    dst[--numLeft]=(short)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(--offset==-65){
                    for(;;--offset){
                      if((word0&(1L<<offset))!=0){
                        dst[--numLeft]=(short)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfShort.DEFAULT_ARR;
      }
      @Override public int[] toIntArray(){
        int numLeft;
        final long word0,word1,word2,word3;
        if((numLeft=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
          final int[] dst=new int[numLeft];
        int offset=Byte.MAX_VALUE;
        outer:for(;;){
          if((word3&(1L<<offset))!=0){
            dst[--numLeft]=(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(--offset==63){
            for(;;){
              if((word2&(1L<<offset))!=0){
                dst[--numLeft]=(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(--offset==-1){
                for(;;){
                  if((word1&(1L<<offset))!=0){
                    dst[--numLeft]=(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(--offset==-65){
                    for(;;--offset){
                      if((word0&(1L<<offset))!=0){
                        dst[--numLeft]=(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfInt.DEFAULT_ARR;
      }
      @Override public long[] toLongArray(){
        int numLeft;
        final long word0,word1,word2,word3;
        if((numLeft=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
          final long[] dst=new long[numLeft];
        long offset=Byte.MAX_VALUE;
        outer:for(;;){
          if((word3&(1L<<offset))!=0){
            dst[--numLeft]=(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(--offset==63){
            for(;;){
              if((word2&(1L<<offset))!=0){
                dst[--numLeft]=(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(--offset==-1){
                for(;;){
                  if((word1&(1L<<offset))!=0){
                    dst[--numLeft]=(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(--offset==-65){
                    for(;;--offset){
                      if((word0&(1L<<offset))!=0){
                        dst[--numLeft]=(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
      }
      @Override public float[] toFloatArray(){
        int numLeft;
        final long word0,word1,word2,word3;
        if((numLeft=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
          final float[] dst=new float[numLeft];
        int offset=Byte.MAX_VALUE;
        outer:for(;;){
          if((word3&(1L<<offset))!=0){
            dst[--numLeft]=(float)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(--offset==63){
            for(;;){
              if((word2&(1L<<offset))!=0){
                dst[--numLeft]=(float)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(--offset==-1){
                for(;;){
                  if((word1&(1L<<offset))!=0){
                    dst[--numLeft]=(float)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(--offset==-65){
                    for(;;--offset){
                      if((word0&(1L<<offset))!=0){
                        dst[--numLeft]=(float)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
      }
      @Override public double[] toDoubleArray(){
        int numLeft;
        final long word0,word1,word2,word3;
        if((numLeft=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
          final double[] dst=new double[numLeft];
        int offset=Byte.MAX_VALUE;
        outer:for(;;){
          if((word3&(1L<<offset))!=0){
            dst[--numLeft]=(double)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(--offset==63){
            for(;;){
              if((word2&(1L<<offset))!=0){
                dst[--numLeft]=(double)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(--offset==-1){
                for(;;){
                  if((word1&(1L<<offset))!=0){
                    dst[--numLeft]=(double)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(--offset==-65){
                    for(;;--offset){
                      if((word0&(1L<<offset))!=0){
                        dst[--numLeft]=(double)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfDouble.DEFAULT_ARR;
      }
    }
    public static class Descending extends Unchecked{
      private static final long serialVersionUID=1L;
      public Descending(ByteSetImpl.Checked that){
        super(that);
      }
      public Descending(ByteSetImpl.Unchecked that){
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
      public Descending(OmniCollection.OfRef<? extends Byte> that){
        super(that);
      }
      public Descending(Collection<? extends Byte> that){
        super(that);
      }
      @Override public Object clone(){
        return new Descending(this);
      }
      @Override public OmniIterator.OfByte iterator(){
        return new UncheckedDescendingItr(this);
      }
      @Override public OmniIterator.OfByte descendingIterator(){
        return new UncheckedAscendingItr(this);
      }
      @Override public ByteComparator comparator(){
        return ByteComparator::descendingCompare;
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement){
        if(toElement==Byte.MIN_VALUE){
          return this;
        }
        return new UncheckedSubSet.TailSet.Descending(this,toElement,((ByteSetImpl)this).countTailSetElements(toElement));
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
        if(fromElement==Byte.MAX_VALUE){
          return this;
        }
        final int f;
        return new UncheckedSubSet.HeadSet.Descending(this,f=fromElement+1,((ByteSetImpl)this).countHeadSetElements(f));
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
        if(fromElement>=toElement){
          if(fromElement==127){
            if(toElement==-128){
              return this;
            }else{
              return new UncheckedSubSet.TailSet.Descending(this,toElement,((ByteSetImpl)this).countTailSetElements(toElement));
            }
          }else{
            final int f=fromElement+1;
            if(toElement==-128){
              return new UncheckedSubSet.HeadSet.Descending(this,f,((ByteSetImpl)this).countHeadSetElements(f));
            }else{
              return new UncheckedSubSet.BodySet.Descending(this,(f<<8)|(toElement&0xff),((ByteSetImpl)this).countSubSetElements(toElement,f));
            }
          }
        }
        return AbstractByteSet.EmptyView.DESCENDING;
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
        if(inclusive){
          return headSet(toElement);
        }
        if(toElement==Byte.MAX_VALUE){
          return AbstractByteSet.EmptyView.DESCENDING;
        }
        final int t;
        return new UncheckedSubSet.TailSet.Descending(this,t=toElement+1,((ByteSetImpl)this).countTailSetElements(t));
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement,boolean inclusive){
        if(inclusive){
          return tailSet(fromElement);
        }
        if(fromElement==Byte.MIN_VALUE){
          return AbstractByteSet.EmptyView.DESCENDING;
        }
        return new UncheckedSubSet.HeadSet.Descending(this,fromElement,((ByteSetImpl)this).countHeadSetElements(fromElement));
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,boolean fromInclusive,byte toElement,boolean toInclusive){
        final int f,t;
        if((f=(fromInclusive?fromElement+1:fromElement))>(t=(toInclusive?toElement:toElement+1))){
          if(t==-128){
            if(f==128){
              return this;
            }else{
              return new UncheckedSubSet.HeadSet.Descending(this,f,((ByteSetImpl)this).countHeadSetElements(f));
            }
          }else{
            if(f==128){
              return new UncheckedSubSet.TailSet.Descending(this,t,((ByteSetImpl)this).countTailSetElements(t));
            }else{
              return new UncheckedSubSet.BodySet.Descending(this,(f<<8)|(t&0xff),((ByteSetImpl)this).countSubSetElements(t,f));
            }
          }
        }
        return AbstractByteSet.EmptyView.DESCENDING;
      }
      @Override public OmniNavigableSet.OfByte descendingSet(){
        return new AbstractUncheckedFullView.Ascending(this);
      }
      @Override public Byte lower(byte val){
        return super.higher(val);
      }
      @Override public Byte floor(byte val){
        return super.ceiling(val);
      }
      @Override public Byte higher(byte val){
        return super.lower(val);
      }
      @Override public Byte ceiling(byte val){
        return super.floor(val);
      }
      @Override public Byte pollFirst(){
        return super.pollLast();
      }
      @Override public Byte pollLast(){
        return super.pollFirst();
      }
      @Override public byte lowerByte(byte val){
        return super.higherByte(val);
      }
      @Override public byte byteFloor(byte val){
        return super.byteCeiling(val);
      }
      @Override public byte higherByte(byte val){
        return super.lowerByte(val);
      }
      @Override public byte byteCeiling(byte val){
        return super.byteFloor(val);
      }
      @Override public byte pollFirstByte(){
        return super.pollLastByte();
      }
      @Override public byte pollLastByte(){
        return super.pollFirstByte();
      }
      @Override public short lowerShort(short val){
        return super.higherShort(val);
      }
      @Override public short shortFloor(short val){
        return super.shortCeiling(val);
      }
      @Override public short higherShort(short val){
        return super.lowerShort(val);
      }
      @Override public short shortCeiling(short val){
        return super.shortFloor(val);
      }
      @Override public short pollFirstShort(){
        return super.pollLastShort();
      }
      @Override public short pollLastShort(){
        return super.pollFirstShort();
      }
      @Override public int firstInt(){
        return super.lastInt();
      }
      @Override public int lastInt(){
        return super.firstInt();
      }
      @Override public int lowerInt(int val){
        return super.higherInt(val);
      }
      @Override public int intFloor(int val){
        return super.intCeiling(val);
      }
      @Override public int higherInt(int val){
        return super.lowerInt(val);
      }
      @Override public int intCeiling(int val){
        return super.intFloor(val);
      }
      @Override public int pollFirstInt(){
        return super.pollLastInt();
      }
      @Override public int pollLastInt(){
        return super.pollFirstInt();
      }
      @Override public long lowerLong(long val){
        return super.higherLong(val);
      }
      @Override public long longFloor(long val){
        return super.longCeiling(val);
      }
      @Override public long higherLong(long val){
        return super.lowerLong(val);
      }
      @Override public long longCeiling(long val){
        return super.longFloor(val);
      }
      @Override public long pollFirstLong(){
        return super.pollLastLong();
      }
      @Override public long pollLastLong(){
        return super.pollFirstLong();
      }
      @Override public float lowerFloat(float val){
        return super.higherFloat(val);
      }
      @Override public float floatFloor(float val){
        return super.floatCeiling(val);
      }
      @Override public float higherFloat(float val){
        return super.lowerFloat(val);
      }
      @Override public float floatCeiling(float val){
        return super.floatFloor(val);
      }
      @Override public float pollFirstFloat(){
        return super.pollLastFloat();
      }
      @Override public float pollLastFloat(){
        return super.pollFirstFloat();
      }
      @Override public double lowerDouble(double val){
        return super.higherDouble(val);
      }
      @Override public double doubleFloor(double val){
        return super.doubleCeiling(val);
      }
      @Override public double higherDouble(double val){
        return super.lowerDouble(val);
      }
      @Override public double doubleCeiling(double val){
        return super.doubleFloor(val);
      }
      @Override public double pollFirstDouble(){
        return super.pollLastDouble();
      }
      @Override public double pollLastDouble(){
        return super.pollFirstDouble();
      }
      @Override public String toString(){
        int numLeft;
        final long word0,word1,word2,word3;
        if((numLeft=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
          final byte[] buffer;
          int bufferOffset;
          (buffer=new byte[6*numLeft])[bufferOffset=0]='[';
          goToEnd:for(int offset=Byte.MAX_VALUE;;){
            if((word3&(1L<<offset))!=0){
              bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
              if(--numLeft==0){
                 break goToEnd;
              }
              buffer[bufferOffset]=',';
              buffer[++bufferOffset]=' ';
            }
            if(--offset==63){
              for(;;){
                if((word2&(1L<<offset))!=0){
                  bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
                  if(--numLeft==0){
                    break goToEnd;
                  }
                  buffer[bufferOffset]=',';
                  buffer[++bufferOffset]=' ';
                }
                if(--offset==-1){
                  for(;;){
                    if((word1&(1L<<offset))!=0){
                      bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
                      if(--numLeft==0){
                        break goToEnd;
                      }
                      buffer[bufferOffset]=',';
                      buffer[++bufferOffset]=' ';
                    }
                    if(--offset==-65){
                      for(;;--offset){
                        if((word0&(1L<<offset))!=0){
                          bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
                          if(--numLeft==0){
                            break goToEnd;
                          }
                          buffer[bufferOffset]=',';
                          buffer[++bufferOffset]=' ';
                        }
                      }
                    }
                  }
                }
              }
            }
          }
          buffer[++bufferOffset]=']';
          return new String(buffer,0,bufferOffset+1,ToStringUtil.IOS8859CharSet);
        }
        return "[]";
      }
      @Override public void forEach(ByteConsumer action){
        Unchecked.Descending.uncheckedWordForEach(this.word3,127,63,action);
        Unchecked.Descending.uncheckedWordForEach(this.word2,63,-1,action);
        Unchecked.Descending.uncheckedWordForEach(this.word1,-1,-65,action);
        Unchecked.Descending.uncheckedWordForEach(this.word0,-65,-129,action);
      }
      private static void uncheckedWordForEach(long word,int offset,int bound,ByteConsumer action){
        do{
          if((word&(1L<<offset))!=0){
            action.accept((byte)offset);
          } 
        }while(--offset!=bound);
      }
      @SuppressWarnings("unchecked")
      @Override public <T> T[] toArray(T[] dst){
        int numLeft;
        final long word0,word1,word2,word3;
        if((numLeft=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
          dst=OmniArray.uncheckedArrResize(numLeft,dst);
          int offset=Byte.MIN_VALUE;
          outer:for(;;){
            if((word0&(1L<<offset))!=0){
              dst[--numLeft]=(T)(Byte)(byte)(offset);
              if(numLeft==0){
                break outer;
              }
            }
            if(++offset==-64){
              for(;;){
                if((word1&(1L<<offset))!=0){
                  dst[--numLeft]=(T)(Byte)(byte)(offset);
                  if(numLeft==0){
                    break outer;
                  }
                }
                if(++offset==0){
                  for(;;){
                    if((word2&(1L<<offset))!=0){
                      dst[--numLeft]=(T)(Byte)(byte)(offset);
                      if(numLeft==0){
                        break outer;
                      }
                    }
                    if(++offset==64){
                      for(;;--offset){
                        if((word3&(1L<<offset))!=0){
                          dst[--numLeft]=(T)(Byte)(byte)(offset);
                          if(numLeft==0){
                            break outer;
                          }
                        }
                      }
                    }
                  }
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
        int numLeft;
        final long word0,word1,word2,word3;
        final T[] dst=arrConstructor.apply(numLeft=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3));
        if(numLeft!=0){
          int offset=Byte.MIN_VALUE;
          outer:for(;;){
            if((word0&(1L<<offset))!=0){
              dst[--numLeft]=(T)(Byte)(byte)(offset);
              if(numLeft==0){
                break outer;
              }
            }
            if(++offset==-64){
              for(;;){
                if((word1&(1L<<offset))!=0){
                  dst[--numLeft]=(T)(Byte)(byte)(offset);
                  if(numLeft==0){
                    break outer;
                  }
                }
                if(++offset==0){
                  for(;;){
                    if((word2&(1L<<offset))!=0){
                      dst[--numLeft]=(T)(Byte)(byte)(offset);
                      if(numLeft==0){
                        break outer;
                      }
                    }
                    if(++offset==64){
                      for(;;--offset){
                        if((word3&(1L<<offset))!=0){
                          dst[--numLeft]=(T)(Byte)(byte)(offset);
                          if(numLeft==0){
                            break outer;
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
        return dst;
      }
      @Override public Byte[] toArray(){
        int numLeft;
        final long word0,word1,word2,word3;
        if((numLeft=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
          final Byte[] dst=new Byte[numLeft];
        int offset=Byte.MIN_VALUE;
        outer:for(;;){
          if((word0&(1L<<offset))!=0){
            dst[--numLeft]=(Byte)(byte)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(++offset==-64){
            for(;;){
              if((word1&(1L<<offset))!=0){
                dst[--numLeft]=(Byte)(byte)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(++offset==0){
                for(;;){
                  if((word2&(1L<<offset))!=0){
                    dst[--numLeft]=(Byte)(byte)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(++offset==64){
                    for(;;--offset){
                      if((word3&(1L<<offset))!=0){
                        dst[--numLeft]=(Byte)(byte)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_BOXED_ARR;
      }
      @Override public byte[] toByteArray(){
        int numLeft;
        final long word0,word1,word2,word3;
        if((numLeft=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
          final byte[] dst=new byte[numLeft];
        int offset=Byte.MIN_VALUE;
        outer:for(;;){
          if((word0&(1L<<offset))!=0){
            dst[--numLeft]=(byte)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(++offset==-64){
            for(;;){
              if((word1&(1L<<offset))!=0){
                dst[--numLeft]=(byte)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(++offset==0){
                for(;;){
                  if((word2&(1L<<offset))!=0){
                    dst[--numLeft]=(byte)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(++offset==64){
                    for(;;--offset){
                      if((word3&(1L<<offset))!=0){
                        dst[--numLeft]=(byte)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_ARR;
      }
      @Override public short[] toShortArray(){
        int numLeft;
        final long word0,word1,word2,word3;
        if((numLeft=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
          final short[] dst=new short[numLeft];
        int offset=Byte.MIN_VALUE;
        outer:for(;;){
          if((word0&(1L<<offset))!=0){
            dst[--numLeft]=(short)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(++offset==-64){
            for(;;){
              if((word1&(1L<<offset))!=0){
                dst[--numLeft]=(short)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(++offset==0){
                for(;;){
                  if((word2&(1L<<offset))!=0){
                    dst[--numLeft]=(short)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(++offset==64){
                    for(;;--offset){
                      if((word3&(1L<<offset))!=0){
                        dst[--numLeft]=(short)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfShort.DEFAULT_ARR;
      }
      @Override public int[] toIntArray(){
        int numLeft;
        final long word0,word1,word2,word3;
        if((numLeft=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
          final int[] dst=new int[numLeft];
        int offset=Byte.MIN_VALUE;
        outer:for(;;){
          if((word0&(1L<<offset))!=0){
            dst[--numLeft]=(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(++offset==-64){
            for(;;){
              if((word1&(1L<<offset))!=0){
                dst[--numLeft]=(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(++offset==0){
                for(;;){
                  if((word2&(1L<<offset))!=0){
                    dst[--numLeft]=(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(++offset==64){
                    for(;;--offset){
                      if((word3&(1L<<offset))!=0){
                        dst[--numLeft]=(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfInt.DEFAULT_ARR;
      }
      @Override public long[] toLongArray(){
        int numLeft;
        final long word0,word1,word2,word3;
        if((numLeft=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
          final long[] dst=new long[numLeft];
        long offset=Byte.MIN_VALUE;
        outer:for(;;){
          if((word0&(1L<<offset))!=0){
            dst[--numLeft]=(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(++offset==-64){
            for(;;){
              if((word1&(1L<<offset))!=0){
                dst[--numLeft]=(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(++offset==0){
                for(;;){
                  if((word2&(1L<<offset))!=0){
                    dst[--numLeft]=(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(++offset==64){
                    for(;;--offset){
                      if((word3&(1L<<offset))!=0){
                        dst[--numLeft]=(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
      }
      @Override public float[] toFloatArray(){
        int numLeft;
        final long word0,word1,word2,word3;
        if((numLeft=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
          final float[] dst=new float[numLeft];
        int offset=Byte.MIN_VALUE;
        outer:for(;;){
          if((word0&(1L<<offset))!=0){
            dst[--numLeft]=(float)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(++offset==-64){
            for(;;){
              if((word1&(1L<<offset))!=0){
                dst[--numLeft]=(float)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(++offset==0){
                for(;;){
                  if((word2&(1L<<offset))!=0){
                    dst[--numLeft]=(float)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(++offset==64){
                    for(;;--offset){
                      if((word3&(1L<<offset))!=0){
                        dst[--numLeft]=(float)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
      }
      @Override public double[] toDoubleArray(){
        int numLeft;
        final long word0,word1,word2,word3;
        if((numLeft=SetCommonImpl.size(word0=this.word0,word1=this.word1,word2=this.word2,word3=this.word3))!=0){
          final double[] dst=new double[numLeft];
        int offset=Byte.MIN_VALUE;
        outer:for(;;){
          if((word0&(1L<<offset))!=0){
            dst[--numLeft]=(double)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(++offset==-64){
            for(;;){
              if((word1&(1L<<offset))!=0){
                dst[--numLeft]=(double)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(++offset==0){
                for(;;){
                  if((word2&(1L<<offset))!=0){
                    dst[--numLeft]=(double)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(++offset==64){
                    for(;;--offset){
                      if((word3&(1L<<offset))!=0){
                        dst[--numLeft]=(double)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfDouble.DEFAULT_ARR;
      }
    }
    private static final long serialVersionUID=1L;
    private Unchecked(ByteSetImpl.Checked that){
      super(that);
    }
    private Unchecked(ByteSetImpl.Unchecked that){
      super(that);
    }
    private Unchecked(OmniCollection.OfBoolean that){
      super();
      //TODO optimize
      this.addAll(that);
    }
    private Unchecked(OmniCollection.OfByte that){
      super();
      //TODO optimize
      this.addAll(that);
    }
    private Unchecked(OmniCollection.ByteOutput<?> that){
      super();
      //TODO optimize
      this.addAll(that);
    }
    private Unchecked(OmniCollection.OfRef<? extends Byte> that){
      super();
      //TODO optimize
      this.addAll(that);
    }
    private Unchecked(Collection<? extends Byte> that){
      super();
      //TODO optimize
      this.addAll(that);
    }
    private void uncheckedForEachAscending(int offset,ByteConsumer action){
      switch(offset>>6){
        case -2:
          Ascending.uncheckedWordForEach(this.word0,offset,offset=-64,action);
        case -1:
          Ascending.uncheckedWordForEach(this.word1,offset,offset=0,action);
        case 0:
          Ascending.uncheckedWordForEach(this.word2,offset,offset=64,action);
        default:
          Ascending.uncheckedWordForEach(this.word3,offset,128,action);
      }
    }
    private void uncheckedForEachDescending(int offset,ByteConsumer action){
      switch(offset>>6){
        case 1:
          Descending.uncheckedWordForEach(this.word3,offset,offset=63,action);
        case 0:
          Descending.uncheckedWordForEach(this.word2,offset,offset=-1,action);
        case -1:
          Descending.uncheckedWordForEach(this.word1,offset,offset=-65,action);
        default:
          Descending.uncheckedWordForEach(this.word0,offset,-129,action);
      }
    }
    private void readObject(ObjectInputStream ois) throws IOException{
      this.word0=ois.readLong();
      this.word1=ois.readLong();
      this.word2=ois.readLong();
      this.word3=ois.readLong();
    }
    private void writeObject(ObjectOutputStream oos) throws IOException{
      oos.writeLong(this.word0);
      oos.writeLong(this.word1);
      oos.writeLong(this.word2);
      oos.writeLong(this.word3);
    }
    private static int hashCodeForWord(long word,int offset,int bound){
      for(int sum=0;;){
        if((word&(1L<<offset))!=0){
          sum+=offset;
        }
        if(++offset==bound){
          return sum;
        }
      }
    }
    @Override public int hashCode(){
      return hashCodeForWord(word0,Byte.MIN_VALUE,-64)
        + hashCodeForWord(word1,-64,0)
        + hashCodeForWord(word2,0,64)
        + hashCodeForWord(word3,64,128);
    }
    @Override public void forEach(Consumer<? super Byte> action){
      forEach((ByteConsumer)action::accept);
    }
    @Override public boolean removeIf(BytePredicate filter){
      boolean changed=false;
      int offset=Byte.MIN_VALUE;
      for(long word=this.word0,mask=1L;;++offset){
        if((word&mask)!=0 && filter.test((byte)offset)){
          changed=true;
          word&=(~mask);
        }
        if((mask<<=1)==0L){
          for(this.word0=word,word=this.word1,mask=1L;;++offset){
            if((word&mask)!=0 && filter.test((byte)offset)){
              changed=true;
              word&=(~mask);
            }
            if((mask<<=1)==0L){
              for(this.word1=word,word=this.word2,mask=1L;;++offset){
                if((word&mask)!=0 && filter.test((byte)offset)){
                  changed=true;
                  word&=(~mask);
                }
                if((mask<<=1)==0L){
                  for(this.word2=word,word=this.word3,mask=1L;;++offset){
                    if((word&mask)!=0 && filter.test((byte)offset)){
                      changed=true;
                      word&=(~mask);
                    }
                    if((mask<<=1)==0L){
                      this.word3=word;
                      return changed;
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      return this.removeIf((BytePredicate)filter::test);
    }
    @Override public void clear(){
      this.word0=0;
      this.word1=0;
      this.word2=0;
      this.word3=0;
    }
    @Override public boolean isEmpty(){
      return this.word0==0 && this.word1==0 && this.word2==0 && this.word3==0;
    }
    @Override public int size(){
      return SetCommonImpl.size(word0,word1,word2,word3);
    }
    @Override public boolean contains(Object val){
      if(val instanceof Byte){
        return super.contains((byte)val);
      }else if(val instanceof Integer || val instanceof Short){
        return super.contains(((Number)val).intValue());
      }else if(val instanceof Long){
        return super.contains((long)val);
      }else if(val instanceof Float){
        return super.contains((float)val);
      }else if(val instanceof Double){
        return super.contains((double)val);
      }else if(val instanceof Character){
        return super.contains((char)val);
      }else if(val instanceof Boolean){
        return super.contains((boolean)val);
      }
      return false;
    }
    @Override public boolean remove(Object val){
      if(val instanceof Byte){
        return super.removeVal((byte)val);
      }else if(val instanceof Integer || val instanceof Short){
        return super.removeVal(((Number)val).intValue());
      }else if(val instanceof Long){
        return super.removeVal((long)val);
      }else if(val instanceof Float){
        return super.removeVal((float)val);
      }else if(val instanceof Double){
        return super.removeVal((double)val);
      }else if(val instanceof Character){
        return super.removeVal((char)val);
      }else if(val instanceof Boolean){
        return super.removeVal((boolean)val);
      }
      return false;
    }
    private int headSetRemoveIfImpl(int inclusiveTo,int size,BytePredicate filter){
      int numRemoved=0;
      goToWord0:for(;;){
        goToWord1:for(;;){
          switch(inclusiveTo>>6){
            case -2:
              break goToWord0;
            case -1:
              break goToWord1;
            case 0:
              break;
            default:
              for(long word=this.word3;;){
                final long marker;
                if((word&(marker=1L<<inclusiveTo))!=0){
                  if(filter.test((byte)inclusiveTo)){
                    ++numRemoved;
                    word&=(~marker);
                  }
                  if(--size==0){
                    this.word3=word;
                    return numRemoved;
                  }
                }
                if(--inclusiveTo==63){
                  this.word3=word;
                  break;
                }
              }
          }
          for(long word=this.word2;;){
            final long marker;
            if((word&(marker=1L<<inclusiveTo))!=0){
              if(filter.test((byte)inclusiveTo)){
                ++numRemoved;
                word&=(~marker);
              }
              if(--size==0){
                this.word2=word;
                return numRemoved;
              }
            }
            if(--inclusiveTo==-1){
              this.word2=word;
              break goToWord1;
            }
          }
        }
        for(long word=this.word1;;){
          final long marker;
          if((word&(marker=1L<<inclusiveTo))!=0){
            if(filter.test((byte)inclusiveTo)){
              ++numRemoved;
              word&=(~marker);
            }
            if(--size==0){
              this.word1=word;
              return numRemoved;
            }
          }
          if(--inclusiveTo==-65){
            this.word1=word;
            break goToWord0;
          }
        }
      }
      for(long word=this.word0;;--inclusiveTo){
        final long marker;
        if((word&(marker=1L<<inclusiveTo))!=0){
          if(filter.test((byte)inclusiveTo)){
            ++numRemoved;
            word&=(~marker);
          }
          if(--size==0){
            this.word1=word;
            return numRemoved;
          }
        }
      }
    }
  }
  private static class UncheckedAscendingItr extends AbstractByteItr{
    transient final ByteSetImpl.Unchecked root;
    transient int currIndex;
    private UncheckedAscendingItr(ByteSetImpl.Unchecked root){
      this.root=root;
      int currIndex;
      if((currIndex=Long.numberOfTrailingZeros(root.word0))==64){
        if((currIndex+=Long.numberOfTrailingZeros(root.word1))==128){
          if((currIndex+=Long.numberOfTrailingZeros(root.word2))==192){
            currIndex+=Long.numberOfTrailingZeros(root.word3);
          }
        }
      }
      this.currIndex=currIndex-128;
    }
    private UncheckedAscendingItr(UncheckedAscendingItr that){
      this.root=that.root;
      this.currIndex=that.currIndex;
    }
    @Override public Object clone(){
      return new UncheckedAscendingItr(this);
    }
    @Override public void remove(){
      final var root=this.root;
      int currIndex;
      switch(((currIndex=this.currIndex)-1)>>6){
        case 1:
          int lead0s;
          long word;
          if((lead0s=Long.numberOfLeadingZeros((word=root.word3)<<(-currIndex)))!=64){
            root.word3=word&(~(1L<<(currIndex-lead0s)));
            break;
          }
          currIndex=0;
        case 0:
          if((lead0s=Long.numberOfLeadingZeros((word=root.word2)<<(-currIndex)))!=64){
            root.word2=word&(~(1L<<(currIndex-lead0s)));
            break;
          }
          currIndex=0;
        case -1:
          if((lead0s=Long.numberOfLeadingZeros((word=root.word1)<<(-currIndex)))!=64){
            root.word1=word&(~(1L<<(currIndex-lead0s)));
            break;
          }
          currIndex=0;
        default:
          root.word0=(word=root.word0)&(~(1L<<(currIndex-Long.numberOfLeadingZeros(word<<(-currIndex)))));
      }
    }
    @Override public void forEachRemaining(ByteConsumer action){
      final int currIndex;
      if((currIndex=this.currIndex)!=128){
        root.uncheckedForEachAscending(currIndex,action);
        this.currIndex=128;
      }
    }
    @Override public void forEachRemaining(Consumer<? super Byte> action){
      final int currIndex;
      if((currIndex=this.currIndex)!=128){
        root.uncheckedForEachAscending(currIndex,action::accept);
        this.currIndex=128;
      }
    }
    @Override public boolean hasNext(){
      return this.currIndex!=128;
    }
    @Override public byte nextByte(){
      final int ret;
      int currIndex;
      final var root=this.root;
      switch((currIndex=ret=this.currIndex)>>6){
        case -2:
          int tail0s;
          if((tail0s=Long.numberOfTrailingZeros(root.word0>>>currIndex))!=64){
            this.currIndex=currIndex+tail0s;
            break;
          }
          currIndex=-64;
        case -1:
          if((tail0s=Long.numberOfTrailingZeros(root.word1>>>currIndex))!=64){
            this.currIndex=currIndex+tail0s;
            break;
          }
          currIndex=0;
        case 0:
          if((tail0s=Long.numberOfTrailingZeros(root.word2>>>currIndex))!=64){
            this.currIndex=currIndex+tail0s;
            break;
          }
          currIndex=64;
        default:
          if((tail0s=Long.numberOfTrailingZeros(root.word3>>>currIndex))!=64){
            this.currIndex=currIndex+tail0s;
            break;
          }
          this.currIndex=128;
      }
      return (byte)ret;
    }
  }
  private static class UncheckedDescendingItr extends AbstractByteItr{
    transient final ByteSetImpl.Unchecked root;
    transient int currIndex;
    private UncheckedDescendingItr(ByteSetImpl.Unchecked root){
      this.root=root;
      int currIndex;
      if((currIndex=Long.numberOfLeadingZeros(root.word3))==64){
        if((currIndex+=Long.numberOfLeadingZeros(root.word2))==128){
          if((currIndex+=Long.numberOfLeadingZeros(root.word1))==192){
            currIndex+=Long.numberOfLeadingZeros(root.word0);
          }
        }
      }
      this.currIndex=Byte.MAX_VALUE-currIndex;
    }
    private UncheckedDescendingItr(UncheckedDescendingItr that){
      this.root=that.root;
      this.currIndex=that.currIndex;
    }
    @Override public Object clone(){
      return new UncheckedDescendingItr(this);
    }
    @Override public void remove(){
      final var root=this.root;
      int currIndex;
      switch(((currIndex=this.currIndex+1))>>6){
      case -2:
        int tail0s;
        long word;
        if((tail0s=Long.numberOfTrailingZeros((word=root.word0)>>>(currIndex)))!=64){
          root.word0=word&(~(1L<<(currIndex+tail0s)));
          break;
        }
        currIndex=0;
      case -1:
        if((tail0s=Long.numberOfTrailingZeros((word=root.word1)>>>(currIndex)))!=64){
          root.word1=word&(~(1L<<(currIndex+tail0s)));
          break;
        }
        currIndex=0;
      case 0:
        if((tail0s=Long.numberOfTrailingZeros((word=root.word2)>>>(currIndex)))!=64){
          root.word2=word&(~(1L<<(currIndex+tail0s)));
          break;
        }
        currIndex=0;
      default:
        root.word3=(word=root.word3)&(~(1L<<(currIndex+Long.numberOfTrailingZeros(word>>>(currIndex)))));
      }
    }
    @Override public void forEachRemaining(ByteConsumer action){
      final int currIndex;
      if((currIndex=this.currIndex)!=-129){
        root.uncheckedForEachDescending(currIndex,action);
        this.currIndex=-129;
      }
    }
    @Override public void forEachRemaining(Consumer<? super Byte> action){
      final int currIndex;
      if((currIndex=this.currIndex)!=-129){
        root.uncheckedForEachDescending(currIndex,action::accept);
        this.currIndex=-129;
      }
    }
    @Override public boolean hasNext(){
      return this.currIndex!=-129;
    }
    @Override public byte nextByte(){
      final int ret;
      int currIndex;
      final var root=this.root;
      switch((currIndex=ret=this.currIndex)>>6){
        case -2:
          int lead0s;
          if((lead0s=Long.numberOfLeadingZeros(root.word3<<-currIndex))!=64){
            this.currIndex=currIndex-lead0s;
            break;
          }
          currIndex=63;
        case -1:
          if((lead0s=Long.numberOfLeadingZeros(root.word2<<-currIndex))!=64){
            this.currIndex=currIndex-lead0s;
            break;
          }
          currIndex=-1;
        case 0:
          if((lead0s=Long.numberOfLeadingZeros(root.word1<<-currIndex))!=64){
            this.currIndex=currIndex-lead0s;
            break;
          }
          currIndex=-65;
        default:
          if((lead0s=Long.numberOfLeadingZeros(root.word0<<-currIndex))!=64){
            this.currIndex=currIndex-lead0s;
            break;
          }
          this.currIndex=-129;
      }
      return (byte)ret;
    }
  }
  private static class CheckedAscendingItr extends AbstractByteItr{
    transient final ByteSetImpl.Checked root;
    transient int currIndex;
    transient int modCountAndLastRet;
    private CheckedAscendingItr(ByteSetImpl.Checked root){
      this.root=root;
      int currIndex;
      if((currIndex=Long.numberOfTrailingZeros(root.word0))==64){
        if((currIndex+=Long.numberOfTrailingZeros(root.word1))==128){
          if((currIndex+=Long.numberOfTrailingZeros(root.word2))==192){
            currIndex+=Long.numberOfTrailingZeros(root.word3);
          }
        }
      }
      this.currIndex=currIndex-128;
      this.modCountAndLastRet=root.modCountAndSize|0x1ff;
    }
    private CheckedAscendingItr(CheckedAscendingItr that){
      this.root=that.root;
      this.currIndex=that.currIndex;
      this.modCountAndLastRet=that.modCountAndLastRet;
    }
    @Override public Object clone(){
      return new CheckedAscendingItr(this);
    }
    @Override public void remove(){
      int lastRet;
      int modCountAndLastRet;
      if((lastRet=(modCountAndLastRet=this.modCountAndLastRet)&0x1ff)==0x1ff){
        throw new IllegalStateException();
      }
      final ByteSetImpl.Checked root;
      final int modCountAndSize;
      CheckedCollection.checkModCount(modCountAndLastRet&(~0x1ff),(modCountAndSize=(root=this.root).modCountAndSize)&(~0x1ff));
      switch((lastRet=(byte)lastRet)>>6){
        case -2:
          root.word0 &=(~(1L<<lastRet));
          break;
        case -1:
          root.word1 &=(~(1L<<lastRet));
          break;
        case 0:
          root.word2 &=(~(1L<<lastRet));
          break;
        default:
          root.word3 &=(~(1L<<lastRet));
      }
      root.modCountAndSize=modCountAndSize+((1<<9)-1);
      this.modCountAndLastRet=(modCountAndLastRet+(1<<9))|0x1ff;
    }
    private void uncheckedForEachRemaining(int currIndex,ByteConsumer action){
      int modCountAndLastRet=this.modCountAndLastRet;
      final var root=this.root;
      int lastRet;
      try{
        action.accept((byte)(lastRet=currIndex));
        switch((++currIndex)>>6){
          case -2:
            for(long word=root.word0;;){
              if((word&(1L<<currIndex))!=0){
                action.accept((byte)(lastRet=currIndex));
              }
              if(++currIndex==-64){
                break;
              }
            }
          case -1:
            for(long word=root.word1;;){
              if((word&(1L<<currIndex))!=0){
                action.accept((byte)(lastRet=currIndex));
              }
              if(++currIndex==0){
                break;
              }
            }
          case 0:
            for(long word=root.word2;;){
              if((word&(1L<<currIndex))!=0){
                action.accept((byte)(lastRet=currIndex));
              }
              if(++currIndex==64){
                break;
              }
            }
          case 1:
            for(long word=root.word3;;){
              if((word&(1L<<currIndex))!=0){
                action.accept((byte)(lastRet=currIndex));
              }
              if(++currIndex==128){
                break;
              }
            }
          default:
        }
      }finally{
        CheckedCollection.checkModCount(modCountAndLastRet&=(~0x1ff),root.modCountAndSize&(~0x1ff));
      }
      this.modCountAndLastRet=modCountAndLastRet|(0xff&lastRet);
      this.currIndex=128;
    }
    @Override public void forEachRemaining(ByteConsumer action){
      final int currIndex;
      if((currIndex=this.currIndex)!=128){
        uncheckedForEachRemaining(currIndex,action);
      }
    }
    @Override public void forEachRemaining(Consumer<? super Byte> action){
      final int currIndex;
      if((currIndex=this.currIndex)!=128){
        uncheckedForEachRemaining(currIndex,action::accept);
      }
    }
    @Override public boolean hasNext(){
      return this.currIndex!=128;
    }
    @Override public byte nextByte(){
      final ByteSetImpl.Checked root;
      final int modCountAndLastRet;
      CheckedCollection.checkModCount((modCountAndLastRet=this.modCountAndLastRet)&(~0x1ff),(root=this.root).modCountAndSize&(~0x1ff));
      final int ret;
      int currIndex;
      switch((currIndex=ret=this.currIndex)>>6){
        case -2:
          int tail0s;
          if((tail0s=Long.numberOfTrailingZeros(root.word0>>>currIndex))!=64){
            this.currIndex=currIndex+tail0s;
            break;
          }
          currIndex=-64;
        case -1:
          if((tail0s=Long.numberOfTrailingZeros(root.word1>>>currIndex))!=64){
            this.currIndex=currIndex+tail0s;
            break;
          }
          currIndex=0;
        case 0:
          if((tail0s=Long.numberOfTrailingZeros(root.word2>>>currIndex))!=64){
            this.currIndex=currIndex+tail0s;
            break;
          }
          currIndex=64;
        case 1:
          if((tail0s=Long.numberOfTrailingZeros(root.word3>>>currIndex))!=64){
            this.currIndex=currIndex+tail0s;
            break;
          }
          this.currIndex=128;
          break;
        default:
          throw new NoSuchElementException();
      }
      this.modCountAndLastRet=(modCountAndLastRet&(~0x1ff))+(ret&0xff);
      return (byte)ret;
    }
  }
  private static class CheckedDescendingItr extends AbstractByteItr{
    transient final ByteSetImpl.Checked root;
    transient int currIndex;
    transient int modCountAndLastRet;
    private CheckedDescendingItr(ByteSetImpl.Checked root){
      this.root=root;
      int currIndex;
      if((currIndex=Long.numberOfLeadingZeros(root.word3))==64){
        if((currIndex+=Long.numberOfLeadingZeros(root.word2))==128){
          if((currIndex+=Long.numberOfLeadingZeros(root.word1))==192){
            currIndex+=Long.numberOfLeadingZeros(root.word0);
          }
        }
      }
      this.currIndex=Byte.MAX_VALUE-currIndex;
      this.modCountAndLastRet=root.modCountAndSize|0x1ff;
    }
    private CheckedDescendingItr(CheckedDescendingItr that){
      this.root=that.root;
      this.currIndex=that.currIndex;
      this.modCountAndLastRet=that.modCountAndLastRet;
    }
    @Override public Object clone(){
      return new CheckedDescendingItr(this);
    }
    @Override public void remove(){
      int lastRet;
      int modCountAndLastRet;
      if((lastRet=(modCountAndLastRet=this.modCountAndLastRet)&0x1ff)==0x1ff){
        throw new IllegalStateException();
      }
      final ByteSetImpl.Checked root;
      final int modCountAndSize;
      CheckedCollection.checkModCount(modCountAndLastRet&(~0x1ff),(modCountAndSize=(root=this.root).modCountAndSize)&(~0x1ff));
      switch((lastRet=(byte)lastRet)>>6){
        case 1:
          root.word3 &=(~(1L<<lastRet));
          break;
        case 0:
          root.word2 &=(~(1L<<lastRet));
          break;
        case -1:
          root.word1 &=(~(1L<<lastRet));
          break;
        default:
          root.word0 &=(~(1L<<lastRet));
      }
      root.modCountAndSize=modCountAndSize+((1<<9)-1);
      this.modCountAndLastRet=(modCountAndLastRet+(1<<9))|0x1ff;
    }
    private void uncheckedForEachRemaining(int currIndex,ByteConsumer action){
      int modCountAndLastRet=this.modCountAndLastRet;
      final var root=this.root;
      int lastRet;
      try{
        action.accept((byte)(lastRet=currIndex));
        switch((--currIndex)>>6){
          case 1:
            for(long word=root.word3;;){
              if((word&(1L<<currIndex))!=0){
                action.accept((byte)(lastRet=currIndex));
              }
              if(--currIndex==63){
                break;
              }
            }
          case 0:
            for(long word=root.word2;;){
              if((word&(1L<<currIndex))!=0){
                action.accept((byte)(lastRet=currIndex));
              }
              if(--currIndex==-1){
                break;
              }
            }
          case -1:
            for(long word=root.word0;;){
              if((word&(1L<<currIndex))!=0){
                action.accept((byte)(lastRet=currIndex));
              }
              if(--currIndex==-65){
                break;
              }
            }
          case -2:
            for(long word=root.word0;;){
              if((word&(1L<<currIndex))!=0){
                action.accept((byte)(lastRet=currIndex));
              }
              if(--currIndex==-129){
                break;
              }
            }
          default:
        }
      }finally{
        CheckedCollection.checkModCount(modCountAndLastRet&=(~0x1ff),root.modCountAndSize&(~0x1ff));
      }
      this.modCountAndLastRet=modCountAndLastRet|(0xff&lastRet);
      this.currIndex=-129;
    }
    @Override public void forEachRemaining(ByteConsumer action){
      final int currIndex;
      if((currIndex=this.currIndex)!=-129){
        uncheckedForEachRemaining(currIndex,action);
      }
    }
    @Override public void forEachRemaining(Consumer<? super Byte> action){
      final int currIndex;
      if((currIndex=this.currIndex)!=-129){
        uncheckedForEachRemaining(currIndex,action::accept);
      }
    }
    @Override public boolean hasNext(){
      return this.currIndex!=-129;
    }
    @Override public byte nextByte(){
      final ByteSetImpl.Checked root;
      final int modCountAndLastRet;
      CheckedCollection.checkModCount((modCountAndLastRet=this.modCountAndLastRet)&(~0x1ff),(root=this.root).modCountAndSize&(~0x1ff));
      final int ret;
      int currIndex;
      switch((currIndex=ret=this.currIndex)>>6){
        case 1:
          int lead0s;
          if((lead0s=Long.numberOfLeadingZeros(root.word3<<-currIndex))!=64){
            this.currIndex=currIndex-lead0s;
            break;
          }
          currIndex=63;
        case 0:
          if((lead0s=Long.numberOfLeadingZeros(root.word2<<-currIndex))!=64){
            this.currIndex=currIndex-lead0s;
            break;
          }
          currIndex=-1;
        case -1:
          if((lead0s=Long.numberOfLeadingZeros(root.word1<<-currIndex))!=64){
            this.currIndex=currIndex-lead0s;
            break;
          }
          currIndex=-64;
        case -2:
          if((lead0s=Long.numberOfLeadingZeros(root.word0<<-currIndex))!=64){
            this.currIndex=currIndex-lead0s;
            break;
          }
          this.currIndex=-129;
          break;
        default:
          throw new NoSuchElementException();
      }
      this.modCountAndLastRet=(modCountAndLastRet&(~0x1ff))+(ret&0xff);
      return (byte)ret;
    }
  }
  private static abstract class AbstractCheckedFullView extends AbstractByteSet{
    transient final ByteSetImpl.Checked root;
    private AbstractCheckedFullView(ByteSetImpl.Checked root){
      this.root=root;
    }
    @Override public int size(){
      return root.size();
    }
    @Override public boolean isEmpty(){
      return root.isEmpty();
    }
    @Override public void clear(){
      root.clear();
    }
    @Override public boolean add(boolean val){
      return root.add(val);
    }
    @Override public boolean add(byte val){
      return root.add(val);
    }
    @Override public int hashCode(){
      return root.hashCode();
    }
    @Override public boolean removeIf(BytePredicate filter){
      return root.removeIf(filter);
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      return root.removeIf((BytePredicate)filter::test);
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
    @Override public Byte lower(byte val){
      return root.higher(val);
    }
    @Override public Byte floor(byte val){
      return root.ceiling(val);
    }
    @Override public Byte higher(byte val){
      return root.lower(val);
    }
    @Override public Byte ceiling(byte val){
      return root.floor(val);
    }
    @Override public Byte pollFirst(){
      return root.pollLast();
    }
    @Override public Byte pollLast(){
      return root.pollFirst();
    }
    @Override public byte lowerByte(byte val){
      return root.higherByte(val);
    }
    @Override public byte byteFloor(byte val){
      return root.byteCeiling(val);
    }
    @Override public byte higherByte(byte val){
      return root.lowerByte(val);
    }
    @Override public byte byteCeiling(byte val){
      return root.byteFloor(val);
    }
    @Override public byte pollFirstByte(){
      return root.pollLastByte();
    }
    @Override public byte pollLastByte(){
      return root.pollFirstByte();
    }
    @Override public short lowerShort(short val){
      return root.higherShort(val);
    }
    @Override public short shortFloor(short val){
      return root.shortCeiling(val);
    }
    @Override public short higherShort(short val){
      return root.lowerShort(val);
    }
    @Override public short shortCeiling(short val){
      return root.shortFloor(val);
    }
    @Override public short pollFirstShort(){
      return root.pollLastShort();
    }
    @Override public short pollLastShort(){
      return root.pollFirstShort();
    }
    @Override public int lowerInt(int val){
      return root.higherInt(val);
    }
    @Override public int intFloor(int val){
      return root.intCeiling(val);
    }
    @Override public int higherInt(int val){
      return root.lowerInt(val);
    }
    @Override public int intCeiling(int val){
      return root.intFloor(val);
    }
    @Override public int pollFirstInt(){
      return root.pollLastInt();
    }
    @Override public int pollLastInt(){
      return root.pollFirstInt();
    }
    @Override public long lowerLong(long val){
      return root.higherLong(val);
    }
    @Override public long longFloor(long val){
      return root.longCeiling(val);
    }
    @Override public long higherLong(long val){
      return root.lowerLong(val);
    }
    @Override public long longCeiling(long val){
      return root.longFloor(val);
    }
    @Override public long pollFirstLong(){
      return root.pollLastLong();
    }
    @Override public long pollLastLong(){
      return root.pollFirstLong();
    }
    @Override public float lowerFloat(float val){
      return root.higherFloat(val);
    }
    @Override public float floatFloor(float val){
      return root.floatCeiling(val);
    }
    @Override public float higherFloat(float val){
      return root.lowerFloat(val);
    }
    @Override public float floatCeiling(float val){
      return root.floatFloor(val);
    }
    @Override public float pollFirstFloat(){
      return root.pollLastFloat();
    }
    @Override public float pollLastFloat(){
      return root.pollFirstFloat();
    }
    @Override public double lowerDouble(double val){
      return root.higherDouble(val);
    }
    @Override public double doubleFloor(double val){
      return root.doubleCeiling(val);
    }
    @Override public double higherDouble(double val){
      return root.lowerDouble(val);
    }
    @Override public double doubleCeiling(double val){
      return root.doubleFloor(val);
    }
    @Override public double pollFirstDouble(){
      return root.pollLastDouble();
    }
    @Override public double pollLastDouble(){
      return root.pollFirstDouble();
    }
    @Override public OmniNavigableSet.OfByte descendingSet(){
      return root;
    }
    @Override public byte firstByte(){
      return (byte)root.lastInt();
    }
    @Override public short firstShort(){
      return (short)root.lastInt();
    }
    @Override public long firstLong(){
      return (long)root.lastInt();
    }
    @Override public float firstFloat(){
      return (float)root.lastInt();
    }
    @Override public double firstDouble(){
      return (double)root.lastInt();
    }
    @Override public byte lastByte(){
      return (byte)root.firstInt();
    }
    @Override public short lastShort(){
      return (short)root.firstInt();
    }
    @Override public long lastLong(){
      return (long)root.firstInt();
    }
    @Override public float lastFloat(){
      return (float)root.firstInt();
    }
    @Override public double lastDouble(){
      return (double)root.firstInt();
    }
    @Override public int firstInt(){
      return root.lastInt();
    }
    @Override public int lastInt(){
      return root.firstInt();
    }
    private static class Ascending extends AbstractCheckedFullView implements Cloneable,Serializable{
      private static final long serialVersionUID=1L;
      private Ascending(ByteSetImpl.Checked root){
        super(root);
      }
      private Object writeReplace(){
        return new ByteSetImpl.Checked.Ascending(root);
      }
      @Override public Object clone(){
        return new ByteSetImpl.Checked.Ascending(root);
      }
      @Override public OmniIterator.OfByte iterator(){
        return new CheckedAscendingItr(root);
      }
      @Override public OmniIterator.OfByte descendingIterator(){
        return new CheckedDescendingItr(root);
      }
      @Override public ByteComparator comparator(){
        return Byte::compare;
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement){
        if(toElement==Byte.MAX_VALUE){
          return this;
        }else{
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
        if(fromElement==Byte.MIN_VALUE){
          return this;
        }else{
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
        if(fromElement==Byte.MIN_VALUE){
          return headSet(toElement);
        }else{
          if(toElement==Byte.MAX_VALUE){
            return tailSet(fromElement);
          }else{
            //TODO
            throw new omni.util.NotYetImplementedException();
          }
        }
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
        if(inclusive){
          return headSet(toElement);
        }else{
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement,boolean inclusive){
        if(inclusive){
          return tailSet(fromElement);
        }else{
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,boolean fromInclusive,byte toElement,boolean toInclusive){
        if(fromInclusive){
          if(toInclusive){
            return subSet(fromElement,toElement);
          }else{
            //TODO
            throw new omni.util.NotYetImplementedException();
          }
        }else{
          if(toInclusive){
            //TODO
            throw new omni.util.NotYetImplementedException();
          }else{
            //TODO
            throw new omni.util.NotYetImplementedException();
          }
        }
      }
      @Override public String toString(){
        int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(root=this.root).modCountAndSize&0x1ff)!=0){
          final byte[] buffer;
          int bufferOffset;
          (buffer=new byte[6*numLeft])[bufferOffset=0]='[';
          int offset=Byte.MIN_VALUE;
          goToEnd:for(long word=root.word0;;){
            if((word&(1L<<offset))!=0){
              bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
              if(--numLeft==0){
                 break goToEnd;
              }
              buffer[bufferOffset]=',';
              buffer[++bufferOffset]=' ';
            }
            if(++offset==-64){
              for(word=root.word1;;){
                if((word&(1L<<offset))!=0){
                  bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
                  if(--numLeft==0){
                    break goToEnd;
                  }
                  buffer[bufferOffset]=',';
                  buffer[++bufferOffset]=' ';
                }
                if(++offset==0){
                  for(word=root.word2;;){
                    if((word&(1L<<offset))!=0){
                      bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
                      if(--numLeft==0){
                        break goToEnd;
                      }
                      buffer[bufferOffset]=',';
                      buffer[++bufferOffset]=' ';
                    }
                    if(++offset==64){
                      for(word=root.word3;;++offset){
                        if((word&(1L<<offset))!=0){
                          bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
                          if(--numLeft==0){
                            break goToEnd;
                          }
                          buffer[bufferOffset]=',';
                          buffer[++bufferOffset]=' ';
                        }
                      }
                    }
                  }
                }
              }
            }
          }
          buffer[++bufferOffset]=']';
          return new String(buffer,0,bufferOffset+1,ToStringUtil.IOS8859CharSet);
        }
        return "[]";
      }
      @Override public void forEach(ByteConsumer action){
        final int modCountAndSize;
        int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(modCountAndSize=(root=this.root).modCountAndSize)&0x1ff)!=0){
          try{
            root.uncheckedAscendingForEach(numLeft,action);
          }finally{
            CheckedCollection.checkModCount(modCountAndSize,root.modCountAndSize);
          }
        }
      }
      @Override public void forEach(Consumer<? super Byte> action){
        final int modCountAndSize;
        int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(modCountAndSize=(root=this.root).modCountAndSize)&0x1ff)!=0){
          try{
            root.uncheckedAscendingForEach(numLeft,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCountAndSize,root.modCountAndSize);
          }
        }
      }
      @SuppressWarnings("unchecked")
      @Override public <T> T[] toArray(T[] dst){
        int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(root=this.root).modCountAndSize&0x1ff)!=0){   
          dst=OmniArray.uncheckedArrResize(numLeft,dst);
          int offset=Byte.MAX_VALUE;
          outer:for(long word=root.word3;;){
            if((word&(1L<<offset))!=0){
              dst[--numLeft]=(T)(Byte)(byte)(offset);
              if(numLeft==0){
                break outer;
              }
            }
            if(--offset==63){
              for(word=root.word2;;){
                if((word&(1L<<offset))!=0){
                  dst[--numLeft]=(T)(Byte)(byte)(offset);
                  if(numLeft==0){
                    break outer;
                  }
                }
                if(--offset==-1){
                  for(word=root.word1;;){
                    if((word&(1L<<offset))!=0){
                      dst[--numLeft]=(T)(Byte)(byte)(offset);
                      if(numLeft==0){
                        break outer;
                      }
                    }
                    if(--offset==-65){
                      for(word=root.word0;;--offset){
                        if((word&(1L<<offset))!=0){
                          dst[--numLeft]=(T)(Byte)(byte)(offset);
                          if(numLeft==0){
                            break outer;
                          }
                        }
                      }
                    }
                  }
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
        int numLeft;
        final T[] dst;
        final ByteSetImpl.Checked root;
        final int modCountAndSize=(root=this.root).modCountAndSize;
        try{
          dst=arrConstructor.apply((numLeft=modCountAndSize&0x1ff));
        }finally{
          CheckedCollection.checkModCount(modCountAndSize,root.modCountAndSize);
        }
        if(numLeft!=0){
          int offset=Byte.MAX_VALUE;
          outer:for(long word=root.word3;;){
            if((word&(1L<<offset))!=0){
              dst[--numLeft]=(T)(Byte)(byte)(offset);
              if(numLeft==0){
                break outer;
              }
            }
            if(--offset==63){
              for(word=root.word2;;){
                if((word&(1L<<offset))!=0){
                  dst[--numLeft]=(T)(Byte)(byte)(offset);
                  if(numLeft==0){
                    break outer;
                  }
                }
                if(--offset==-1){
                  for(word=root.word1;;){
                    if((word&(1L<<offset))!=0){
                      dst[--numLeft]=(T)(Byte)(byte)(offset);
                      if(numLeft==0){
                        break outer;
                      }
                    }
                    if(--offset==-65){
                      for(word=root.word0;;--offset){
                        if((word&(1L<<offset))!=0){
                          dst[--numLeft]=(T)(Byte)(byte)(offset);
                          if(numLeft==0){
                            break outer;
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
        return dst;
      }
      @Override public Byte[] toArray(){
        int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(root=this.root).modCountAndSize&0x1ff)!=0){   
          final Byte[] dst=new Byte[numLeft];
        int offset=Byte.MAX_VALUE;
        outer:for(long word=root.word3;;){
          if((word&(1L<<offset))!=0){
            dst[--numLeft]=(Byte)(byte)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(--offset==63){
            for(word=root.word2;;){
              if((word&(1L<<offset))!=0){
                dst[--numLeft]=(Byte)(byte)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(--offset==-1){
                for(word=root.word1;;){
                  if((word&(1L<<offset))!=0){
                    dst[--numLeft]=(Byte)(byte)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(--offset==-65){
                    for(word=root.word0;;--offset){
                      if((word&(1L<<offset))!=0){
                        dst[--numLeft]=(Byte)(byte)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_BOXED_ARR;
      }
      @Override public byte[] toByteArray(){
        int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(root=this.root).modCountAndSize&0x1ff)!=0){   
          final byte[] dst=new byte[numLeft];
        int offset=Byte.MAX_VALUE;
        outer:for(long word=root.word3;;){
          if((word&(1L<<offset))!=0){
            dst[--numLeft]=(byte)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(--offset==63){
            for(word=root.word2;;){
              if((word&(1L<<offset))!=0){
                dst[--numLeft]=(byte)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(--offset==-1){
                for(word=root.word1;;){
                  if((word&(1L<<offset))!=0){
                    dst[--numLeft]=(byte)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(--offset==-65){
                    for(word=root.word0;;--offset){
                      if((word&(1L<<offset))!=0){
                        dst[--numLeft]=(byte)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_ARR;
      }
      @Override public short[] toShortArray(){
        int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(root=this.root).modCountAndSize&0x1ff)!=0){   
          final short[] dst=new short[numLeft];
        int offset=Byte.MAX_VALUE;
        outer:for(long word=root.word3;;){
          if((word&(1L<<offset))!=0){
            dst[--numLeft]=(short)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(--offset==63){
            for(word=root.word2;;){
              if((word&(1L<<offset))!=0){
                dst[--numLeft]=(short)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(--offset==-1){
                for(word=root.word1;;){
                  if((word&(1L<<offset))!=0){
                    dst[--numLeft]=(short)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(--offset==-65){
                    for(word=root.word0;;--offset){
                      if((word&(1L<<offset))!=0){
                        dst[--numLeft]=(short)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfShort.DEFAULT_ARR;
      }
      @Override public int[] toIntArray(){
        int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(root=this.root).modCountAndSize&0x1ff)!=0){   
          final int[] dst=new int[numLeft];
        int offset=Byte.MAX_VALUE;
        outer:for(long word=root.word3;;){
          if((word&(1L<<offset))!=0){
            dst[--numLeft]=(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(--offset==63){
            for(word=root.word2;;){
              if((word&(1L<<offset))!=0){
                dst[--numLeft]=(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(--offset==-1){
                for(word=root.word1;;){
                  if((word&(1L<<offset))!=0){
                    dst[--numLeft]=(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(--offset==-65){
                    for(word=root.word0;;--offset){
                      if((word&(1L<<offset))!=0){
                        dst[--numLeft]=(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfInt.DEFAULT_ARR;
      }
      @Override public long[] toLongArray(){
        int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(root=this.root).modCountAndSize&0x1ff)!=0){   
          final long[] dst=new long[numLeft];
        long offset=Byte.MAX_VALUE;
        outer:for(long word=root.word3;;){
          if((word&(1L<<offset))!=0){
            dst[--numLeft]=(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(--offset==63){
            for(word=root.word2;;){
              if((word&(1L<<offset))!=0){
                dst[--numLeft]=(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(--offset==-1){
                for(word=root.word1;;){
                  if((word&(1L<<offset))!=0){
                    dst[--numLeft]=(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(--offset==-65){
                    for(word=root.word0;;--offset){
                      if((word&(1L<<offset))!=0){
                        dst[--numLeft]=(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
      }
      @Override public float[] toFloatArray(){
        int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(root=this.root).modCountAndSize&0x1ff)!=0){   
          final float[] dst=new float[numLeft];
        int offset=Byte.MAX_VALUE;
        outer:for(long word=root.word3;;){
          if((word&(1L<<offset))!=0){
            dst[--numLeft]=(float)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(--offset==63){
            for(word=root.word2;;){
              if((word&(1L<<offset))!=0){
                dst[--numLeft]=(float)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(--offset==-1){
                for(word=root.word1;;){
                  if((word&(1L<<offset))!=0){
                    dst[--numLeft]=(float)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(--offset==-65){
                    for(word=root.word0;;--offset){
                      if((word&(1L<<offset))!=0){
                        dst[--numLeft]=(float)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
      }
      @Override public double[] toDoubleArray(){
        int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(root=this.root).modCountAndSize&0x1ff)!=0){   
          final double[] dst=new double[numLeft];
        int offset=Byte.MAX_VALUE;
        outer:for(long word=root.word3;;){
          if((word&(1L<<offset))!=0){
            dst[--numLeft]=(double)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(--offset==63){
            for(word=root.word2;;){
              if((word&(1L<<offset))!=0){
                dst[--numLeft]=(double)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(--offset==-1){
                for(word=root.word1;;){
                  if((word&(1L<<offset))!=0){
                    dst[--numLeft]=(double)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(--offset==-65){
                    for(word=root.word0;;--offset){
                      if((word&(1L<<offset))!=0){
                        dst[--numLeft]=(double)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfDouble.DEFAULT_ARR;
      }
    }
    private static class Descending extends AbstractCheckedFullView implements Cloneable,Serializable{
      private static final long serialVersionUID=1L;
      private Descending(ByteSetImpl.Checked root){
        super(root);
      }
      private Object writeReplace(){
        return new ByteSetImpl.Checked.Descending(root);
      }
      @Override public Object clone(){
        return new ByteSetImpl.Checked.Descending(root);
      }
      @Override public OmniIterator.OfByte iterator(){
        return new CheckedDescendingItr(root);
      }
      @Override public OmniIterator.OfByte descendingIterator(){
        return new CheckedAscendingItr(root);
      }
      @Override public ByteComparator comparator(){
        return ByteComparator::descendingCompare;
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement){
        if(toElement==Byte.MIN_VALUE){
          return this;
        }else{
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
        if(fromElement==Byte.MAX_VALUE){
          return this;
        }else{
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
        if(fromElement==Byte.MAX_VALUE){
          return headSet(toElement);
        }else{
          if(toElement==Byte.MIN_VALUE){
            return tailSet(toElement);
          }else{
            //TODO
            throw new omni.util.NotYetImplementedException();
          }
        }
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
        if(inclusive){
          return headSet(toElement);
        }else{
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement,boolean inclusive){
        if(inclusive){
          return tailSet(fromElement);
        }else{
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,boolean fromInclusive,byte toElement,boolean toInclusive){
        if(fromInclusive){
          if(toInclusive){
            return subSet(fromElement,toElement);
          }else{
            //TODO
            throw new omni.util.NotYetImplementedException();
          }
        }else{
          if(toInclusive){
            //TODO
            throw new omni.util.NotYetImplementedException();
          }else{
            //TODO
            throw new omni.util.NotYetImplementedException();
          }
        }
      }
      @Override public String toString(){
        int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(root=this.root).modCountAndSize&0x1ff)!=0){
          final byte[] buffer;
          int bufferOffset;
          (buffer=new byte[6*numLeft])[bufferOffset=0]='[';
          int offset=Byte.MAX_VALUE;
          goToEnd:for(long word=root.word3;;){
            if((word&(1L<<offset))!=0){
              bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
              if(--numLeft==0){
                 break goToEnd;
              }
              buffer[bufferOffset]=',';
              buffer[++bufferOffset]=' ';
            }
            if(--offset==63){
              for(word=root.word2;;){
                if((word&(1L<<offset))!=0){
                  bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
                  if(--numLeft==0){
                    break goToEnd;
                  }
                  buffer[bufferOffset]=',';
                  buffer[++bufferOffset]=' ';
                }
                if(--offset==-1){
                  for(word=root.word1;;){
                    if((word&(1L<<offset))!=0){
                      bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
                      if(--numLeft==0){
                        break goToEnd;
                      }
                      buffer[bufferOffset]=',';
                      buffer[++bufferOffset]=' ';
                    }
                    if(--offset==-65){
                      for(word=root.word0;;--offset){
                        if((word&(1L<<offset))!=0){
                          bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
                          if(--numLeft==0){
                            break goToEnd;
                          }
                          buffer[bufferOffset]=',';
                          buffer[++bufferOffset]=' ';
                        }
                      }
                    }
                  }
                }
              }
            }
          }
          buffer[++bufferOffset]=']';
          return new String(buffer,0,bufferOffset+1,ToStringUtil.IOS8859CharSet);
        }
        return "[]";
      }
      @Override public void forEach(ByteConsumer action){
        final int modCountAndSize;
        int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(modCountAndSize=(root=this.root).modCountAndSize)&0x1ff)!=0){
          try{
            root.uncheckedDescendingForEach(numLeft,action);
          }finally{
            CheckedCollection.checkModCount(modCountAndSize,root.modCountAndSize);
          }
        }
      }
      @Override public void forEach(Consumer<? super Byte> action){
        final int modCountAndSize;
        int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(modCountAndSize=(root=this.root).modCountAndSize)&0x1ff)!=0){
          try{
            root.uncheckedDescendingForEach(numLeft,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCountAndSize,root.modCountAndSize);
          }
        }
      }
      @SuppressWarnings("unchecked")
      @Override public <T> T[] toArray(T[] dst){
        int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(root=this.root).modCountAndSize&0x1ff)!=0){   
          dst=OmniArray.uncheckedArrResize(numLeft,dst);
          int offset=Byte.MIN_VALUE;
          outer:for(long word=root.word0;;){
            if((word&(1L<<offset))!=0){
              dst[--numLeft]=(T)(Byte)(byte)(offset);
              if(numLeft==0){
                break outer;
              }
            }
            if(++offset==-64){
              for(word=root.word1;;){
                if((word&(1L<<offset))!=0){
                  dst[--numLeft]=(T)(Byte)(byte)(offset);
                  if(numLeft==0){
                    break outer;
                  }
                }
                if(++offset==0){
                  for(word=root.word2;;){
                    if((word&(1L<<offset))!=0){
                      dst[--numLeft]=(T)(Byte)(byte)(offset);
                      if(numLeft==0){
                        break outer;
                      }
                    }
                    if(++offset==64){
                      for(word=root.word3;;++offset){
                        if((word&(1L<<offset))!=0){
                          dst[--numLeft]=(T)(Byte)(byte)(offset);
                          if(numLeft==0){
                            break outer;
                          }
                        }
                      }
                    }
                  }
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
        int numLeft;
        final T[] dst;
        final ByteSetImpl.Checked root;
        final int modCountAndSize=(root=this.root).modCountAndSize;
        try{
          dst=arrConstructor.apply((numLeft=modCountAndSize&0x1ff));
        }finally{
          CheckedCollection.checkModCount(modCountAndSize,root.modCountAndSize);
        }
        if(numLeft!=0){
          int offset=Byte.MIN_VALUE;
          outer:for(long word=root.word0;;){
            if((word&(1L<<offset))!=0){
              dst[--numLeft]=(T)(Byte)(byte)(offset);
              if(numLeft==0){
                break outer;
              }
            }
            if(++offset==-64){
              for(word=root.word1;;){
                if((word&(1L<<offset))!=0){
                  dst[--numLeft]=(T)(Byte)(byte)(offset);
                  if(numLeft==0){
                    break outer;
                  }
                }
                if(++offset==0){
                  for(word=root.word2;;){
                    if((word&(1L<<offset))!=0){
                      dst[--numLeft]=(T)(Byte)(byte)(offset);
                      if(numLeft==0){
                        break outer;
                      }
                    }
                    if(++offset==64){
                      for(word=root.word3;;++offset){
                        if((word&(1L<<offset))!=0){
                          dst[--numLeft]=(T)(Byte)(byte)(offset);
                          if(numLeft==0){
                            break outer;
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
        return dst;
      }
      @Override public Byte[] toArray(){
        int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(root=this.root).modCountAndSize&0x1ff)!=0){   
          final Byte[] dst=new Byte[numLeft];
        int offset=Byte.MIN_VALUE;
        outer:for(long word=root.word0;;){
          if((word&(1L<<offset))!=0){
            dst[--numLeft]=(Byte)(byte)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(++offset==-64){
            for(word=root.word1;;){
              if((word&(1L<<offset))!=0){
                dst[--numLeft]=(Byte)(byte)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(++offset==0){
                for(word=root.word2;;){
                  if((word&(1L<<offset))!=0){
                    dst[--numLeft]=(Byte)(byte)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(++offset==64){
                    for(word=root.word3;;++offset){
                      if((word&(1L<<offset))!=0){
                        dst[--numLeft]=(Byte)(byte)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_BOXED_ARR;
      }
      @Override public byte[] toByteArray(){
        int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(root=this.root).modCountAndSize&0x1ff)!=0){   
          final byte[] dst=new byte[numLeft];
        int offset=Byte.MIN_VALUE;
        outer:for(long word=root.word0;;){
          if((word&(1L<<offset))!=0){
            dst[--numLeft]=(byte)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(++offset==-64){
            for(word=root.word1;;){
              if((word&(1L<<offset))!=0){
                dst[--numLeft]=(byte)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(++offset==0){
                for(word=root.word2;;){
                  if((word&(1L<<offset))!=0){
                    dst[--numLeft]=(byte)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(++offset==64){
                    for(word=root.word3;;++offset){
                      if((word&(1L<<offset))!=0){
                        dst[--numLeft]=(byte)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_ARR;
      }
      @Override public short[] toShortArray(){
        int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(root=this.root).modCountAndSize&0x1ff)!=0){   
          final short[] dst=new short[numLeft];
        int offset=Byte.MIN_VALUE;
        outer:for(long word=root.word0;;){
          if((word&(1L<<offset))!=0){
            dst[--numLeft]=(short)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(++offset==-64){
            for(word=root.word1;;){
              if((word&(1L<<offset))!=0){
                dst[--numLeft]=(short)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(++offset==0){
                for(word=root.word2;;){
                  if((word&(1L<<offset))!=0){
                    dst[--numLeft]=(short)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(++offset==64){
                    for(word=root.word3;;++offset){
                      if((word&(1L<<offset))!=0){
                        dst[--numLeft]=(short)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfShort.DEFAULT_ARR;
      }
      @Override public int[] toIntArray(){
        int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(root=this.root).modCountAndSize&0x1ff)!=0){   
          final int[] dst=new int[numLeft];
        int offset=Byte.MIN_VALUE;
        outer:for(long word=root.word0;;){
          if((word&(1L<<offset))!=0){
            dst[--numLeft]=(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(++offset==-64){
            for(word=root.word1;;){
              if((word&(1L<<offset))!=0){
                dst[--numLeft]=(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(++offset==0){
                for(word=root.word2;;){
                  if((word&(1L<<offset))!=0){
                    dst[--numLeft]=(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(++offset==64){
                    for(word=root.word3;;++offset){
                      if((word&(1L<<offset))!=0){
                        dst[--numLeft]=(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfInt.DEFAULT_ARR;
      }
      @Override public long[] toLongArray(){
        int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(root=this.root).modCountAndSize&0x1ff)!=0){   
          final long[] dst=new long[numLeft];
        long offset=Byte.MIN_VALUE;
        outer:for(long word=root.word0;;){
          if((word&(1L<<offset))!=0){
            dst[--numLeft]=(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(++offset==-64){
            for(word=root.word1;;){
              if((word&(1L<<offset))!=0){
                dst[--numLeft]=(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(++offset==0){
                for(word=root.word2;;){
                  if((word&(1L<<offset))!=0){
                    dst[--numLeft]=(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(++offset==64){
                    for(word=root.word3;;++offset){
                      if((word&(1L<<offset))!=0){
                        dst[--numLeft]=(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
      }
      @Override public float[] toFloatArray(){
        int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(root=this.root).modCountAndSize&0x1ff)!=0){   
          final float[] dst=new float[numLeft];
        int offset=Byte.MIN_VALUE;
        outer:for(long word=root.word0;;){
          if((word&(1L<<offset))!=0){
            dst[--numLeft]=(float)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(++offset==-64){
            for(word=root.word1;;){
              if((word&(1L<<offset))!=0){
                dst[--numLeft]=(float)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(++offset==0){
                for(word=root.word2;;){
                  if((word&(1L<<offset))!=0){
                    dst[--numLeft]=(float)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(++offset==64){
                    for(word=root.word3;;++offset){
                      if((word&(1L<<offset))!=0){
                        dst[--numLeft]=(float)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
      }
      @Override public double[] toDoubleArray(){
        int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(root=this.root).modCountAndSize&0x1ff)!=0){   
          final double[] dst=new double[numLeft];
        int offset=Byte.MIN_VALUE;
        outer:for(long word=root.word0;;){
          if((word&(1L<<offset))!=0){
            dst[--numLeft]=(double)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(++offset==-64){
            for(word=root.word1;;){
              if((word&(1L<<offset))!=0){
                dst[--numLeft]=(double)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(++offset==0){
                for(word=root.word2;;){
                  if((word&(1L<<offset))!=0){
                    dst[--numLeft]=(double)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(++offset==64){
                    for(word=root.word3;;++offset){
                      if((word&(1L<<offset))!=0){
                        dst[--numLeft]=(double)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfDouble.DEFAULT_ARR;
      }
    }
  }
  private static abstract class AbstractUncheckedFullView extends AbstractByteSet{
    transient final ByteSetImpl.Unchecked root;
    private AbstractUncheckedFullView(ByteSetImpl.Unchecked root){
      this.root=root;
    }
    @Override public int size(){
      return root.size();
    }
    @Override public boolean isEmpty(){
      return root.isEmpty();
    }
    @Override public void clear(){
      root.clear();
    }
    @Override public boolean add(boolean val){
      return root.add(val);
    }
    @Override public boolean add(byte val){
      return root.add(val);
    }
    @Override public int hashCode(){
      return root.hashCode();
    }
    @Override public boolean removeIf(BytePredicate filter){
      return root.removeIf(filter);
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      return root.removeIf((BytePredicate)filter::test);
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
    @Override public Byte lower(byte val){
      return root.higher(val);
    }
    @Override public Byte floor(byte val){
      return root.ceiling(val);
    }
    @Override public Byte higher(byte val){
      return root.lower(val);
    }
    @Override public Byte ceiling(byte val){
      return root.floor(val);
    }
    @Override public Byte pollFirst(){
      return root.pollLast();
    }
    @Override public Byte pollLast(){
      return root.pollFirst();
    }
    @Override public byte lowerByte(byte val){
      return root.higherByte(val);
    }
    @Override public byte byteFloor(byte val){
      return root.byteCeiling(val);
    }
    @Override public byte higherByte(byte val){
      return root.lowerByte(val);
    }
    @Override public byte byteCeiling(byte val){
      return root.byteFloor(val);
    }
    @Override public byte pollFirstByte(){
      return root.pollLastByte();
    }
    @Override public byte pollLastByte(){
      return root.pollFirstByte();
    }
    @Override public short lowerShort(short val){
      return root.higherShort(val);
    }
    @Override public short shortFloor(short val){
      return root.shortCeiling(val);
    }
    @Override public short higherShort(short val){
      return root.lowerShort(val);
    }
    @Override public short shortCeiling(short val){
      return root.shortFloor(val);
    }
    @Override public short pollFirstShort(){
      return root.pollLastShort();
    }
    @Override public short pollLastShort(){
      return root.pollFirstShort();
    }
    @Override public int lowerInt(int val){
      return root.higherInt(val);
    }
    @Override public int intFloor(int val){
      return root.intCeiling(val);
    }
    @Override public int higherInt(int val){
      return root.lowerInt(val);
    }
    @Override public int intCeiling(int val){
      return root.intFloor(val);
    }
    @Override public int pollFirstInt(){
      return root.pollLastInt();
    }
    @Override public int pollLastInt(){
      return root.pollFirstInt();
    }
    @Override public long lowerLong(long val){
      return root.higherLong(val);
    }
    @Override public long longFloor(long val){
      return root.longCeiling(val);
    }
    @Override public long higherLong(long val){
      return root.lowerLong(val);
    }
    @Override public long longCeiling(long val){
      return root.longFloor(val);
    }
    @Override public long pollFirstLong(){
      return root.pollLastLong();
    }
    @Override public long pollLastLong(){
      return root.pollFirstLong();
    }
    @Override public float lowerFloat(float val){
      return root.higherFloat(val);
    }
    @Override public float floatFloor(float val){
      return root.floatCeiling(val);
    }
    @Override public float higherFloat(float val){
      return root.lowerFloat(val);
    }
    @Override public float floatCeiling(float val){
      return root.floatFloor(val);
    }
    @Override public float pollFirstFloat(){
      return root.pollLastFloat();
    }
    @Override public float pollLastFloat(){
      return root.pollFirstFloat();
    }
    @Override public double lowerDouble(double val){
      return root.higherDouble(val);
    }
    @Override public double doubleFloor(double val){
      return root.doubleCeiling(val);
    }
    @Override public double higherDouble(double val){
      return root.lowerDouble(val);
    }
    @Override public double doubleCeiling(double val){
      return root.doubleFloor(val);
    }
    @Override public double pollFirstDouble(){
      return root.pollLastDouble();
    }
    @Override public double pollLastDouble(){
      return root.pollFirstDouble();
    }
    @Override public OmniNavigableSet.OfByte descendingSet(){
      return root;
    }
    @Override public byte firstByte(){
      return (byte)root.lastInt();
    }
    @Override public short firstShort(){
      return (short)root.lastInt();
    }
    @Override public long firstLong(){
      return (long)root.lastInt();
    }
    @Override public float firstFloat(){
      return (float)root.lastInt();
    }
    @Override public double firstDouble(){
      return (double)root.lastInt();
    }
    @Override public byte lastByte(){
      return (byte)root.firstInt();
    }
    @Override public short lastShort(){
      return (short)root.firstInt();
    }
    @Override public long lastLong(){
      return (long)root.firstInt();
    }
    @Override public float lastFloat(){
      return (float)root.firstInt();
    }
    @Override public double lastDouble(){
      return (double)root.firstInt();
    }
    @Override public int firstInt(){
      return root.lastInt();
    }
    @Override public int lastInt(){
      return root.firstInt();
    }
    private static class Ascending extends AbstractUncheckedFullView implements Cloneable,Serializable{
      private static final long serialVersionUID=1L;
      private Ascending(ByteSetImpl.Unchecked root){
        super(root);
      }
      private Object writeReplace(){
        return new ByteSetImpl.Unchecked.Ascending(root);
      }
      @Override public Object clone(){
        return new ByteSetImpl.Unchecked.Ascending(root);
      }
      @Override public OmniIterator.OfByte iterator(){
        return new UncheckedAscendingItr(root);
      }
      @Override public OmniIterator.OfByte descendingIterator(){
        return new UncheckedDescendingItr(root);
      }
      @Override public ByteComparator comparator(){
        return Byte::compare;
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement){
        if(toElement==Byte.MAX_VALUE){
          return this;
        }else{
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
        if(fromElement==Byte.MIN_VALUE){
          return this;
        }else{
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
        if(fromElement==Byte.MIN_VALUE){
          return headSet(toElement);
        }else{
          if(toElement==Byte.MAX_VALUE){
            return tailSet(fromElement);
          }else{
            //TODO
            throw new omni.util.NotYetImplementedException();
          }
        }
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
        if(inclusive){
          return headSet(toElement);
        }else{
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement,boolean inclusive){
        if(inclusive){
          return tailSet(fromElement);
        }else{
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,boolean fromInclusive,byte toElement,boolean toInclusive){
        if(fromInclusive){
          if(toInclusive){
            return subSet(fromElement,toElement);
          }else{
            //TODO
            throw new omni.util.NotYetImplementedException();
          }
        }else{
          if(toInclusive){
            //TODO
            throw new omni.util.NotYetImplementedException();
          }else{
            //TODO
            throw new omni.util.NotYetImplementedException();
          }
        }
      }
      @Override public String toString(){
        int numLeft;
        final long word0,word1,word2,word3;
        final ByteSetImpl.Unchecked root;
        if((numLeft=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3))!=0){
          final byte[] buffer;
          int bufferOffset;
          (buffer=new byte[6*numLeft])[bufferOffset=0]='[';
          goToEnd:for(int offset=Byte.MIN_VALUE;;){
            if((word0&(1L<<offset))!=0){
              bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
              if(--numLeft==0){
                 break goToEnd;
              }
              buffer[bufferOffset]=',';
              buffer[++bufferOffset]=' ';
            }
            if(++offset==-64){
              for(;;){
                if((word1&(1L<<offset))!=0){
                  bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
                  if(--numLeft==0){
                    break goToEnd;
                  }
                  buffer[bufferOffset]=',';
                  buffer[++bufferOffset]=' ';
                }
                if(++offset==0){
                  for(;;){
                    if((word2&(1L<<offset))!=0){
                      bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
                      if(--numLeft==0){
                        break goToEnd;
                      }
                      buffer[bufferOffset]=',';
                      buffer[++bufferOffset]=' ';
                    }
                    if(++offset==64){
                      for(;;++offset){
                        if((word3&(1L<<offset))!=0){
                          bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
                          if(--numLeft==0){
                            break goToEnd;
                          }
                          buffer[bufferOffset]=',';
                          buffer[++bufferOffset]=' ';
                        }
                      }
                    }
                  }
                }
              }
            }
          }
          buffer[++bufferOffset]=']';
          return new String(buffer,0,bufferOffset+1,ToStringUtil.IOS8859CharSet);
        }
        return "[]";
      }
      @Override public void forEach(ByteConsumer action){
        final ByteSetImpl.Unchecked root;
        Unchecked.Ascending.uncheckedWordForEach((root=this.root).word0,-128,-64,action);  
        Unchecked.Ascending.uncheckedWordForEach(root.word1,-64,0,action);
        Unchecked.Ascending.uncheckedWordForEach(root.word2,0,64,action);
        Unchecked.Ascending.uncheckedWordForEach(root.word3,64,128,action);
      }
      @Override public void forEach(Consumer<? super Byte> action){
        this.forEach((ByteConsumer)action::accept);
      }
      @SuppressWarnings("unchecked")
      @Override public <T> T[] toArray(T[] dst){
        int numLeft;
        final long word0,word1,word2,word3;
        final ByteSetImpl.Unchecked root;
        if((numLeft=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3))!=0){    
          dst=OmniArray.uncheckedArrResize(numLeft,dst);
          int offset=Byte.MAX_VALUE;
          outer:for(;;){
            if((word3&(1L<<offset))!=0){
              dst[--numLeft]=(T)(Byte)(byte)(offset);
              if(numLeft==0){
                break outer;
              }
            }
            if(--offset==63){
              for(;;){
                if((word2&(1L<<offset))!=0){
                  dst[--numLeft]=(T)(Byte)(byte)(offset);
                  if(numLeft==0){
                    break outer;
                  }
                }
                if(--offset==-1){
                  for(;;){
                    if((word1&(1L<<offset))!=0){
                      dst[--numLeft]=(T)(Byte)(byte)(offset);
                      if(numLeft==0){
                        break outer;
                      }
                    }
                    if(--offset==-65){
                      for(;;--offset){
                        if((word0&(1L<<offset))!=0){
                          dst[--numLeft]=(T)(Byte)(byte)(offset);
                          if(numLeft==0){
                            break outer;
                          }
                        }
                      }
                    }
                  }
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
        int numLeft;
        final long word0,word1,word2,word3;
        final ByteSetImpl.Unchecked root;
        final T[] dst=arrConstructor.apply(numLeft=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3));
        if(numLeft!=0){
          int offset=Byte.MAX_VALUE;
          outer:for(;;){
            if((word3&(1L<<offset))!=0){
              dst[--numLeft]=(T)(Byte)(byte)(offset);
              if(numLeft==0){
                break outer;
              }
            }
            if(--offset==63){
              for(;;){
                if((word2&(1L<<offset))!=0){
                  dst[--numLeft]=(T)(Byte)(byte)(offset);
                  if(numLeft==0){
                    break outer;
                  }
                }
                if(--offset==-1){
                  for(;;){
                    if((word1&(1L<<offset))!=0){
                      dst[--numLeft]=(T)(Byte)(byte)(offset);
                      if(numLeft==0){
                        break outer;
                      }
                    }
                    if(--offset==-65){
                      for(;;--offset){
                        if((word0&(1L<<offset))!=0){
                          dst[--numLeft]=(T)(Byte)(byte)(offset);
                          if(numLeft==0){
                            break outer;
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
        return dst;
      }
      @Override public Byte[] toArray(){
        int numLeft;
        final long word0,word1,word2,word3;
        final ByteSetImpl.Unchecked root;
        if((numLeft=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3))!=0){ 
          final Byte[] dst=new Byte[numLeft];
        int offset=Byte.MAX_VALUE;
        outer:for(;;){
          if((word3&(1L<<offset))!=0){
            dst[--numLeft]=(Byte)(byte)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(--offset==63){
            for(;;){
              if((word2&(1L<<offset))!=0){
                dst[--numLeft]=(Byte)(byte)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(--offset==-1){
                for(;;){
                  if((word1&(1L<<offset))!=0){
                    dst[--numLeft]=(Byte)(byte)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(--offset==-65){
                    for(;;--offset){
                      if((word0&(1L<<offset))!=0){
                        dst[--numLeft]=(Byte)(byte)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_BOXED_ARR;
      }
      @Override public byte[] toByteArray(){
        int numLeft;
        final long word0,word1,word2,word3;
        final ByteSetImpl.Unchecked root;
        if((numLeft=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3))!=0){ 
          final byte[] dst=new byte[numLeft];
        int offset=Byte.MAX_VALUE;
        outer:for(;;){
          if((word3&(1L<<offset))!=0){
            dst[--numLeft]=(byte)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(--offset==63){
            for(;;){
              if((word2&(1L<<offset))!=0){
                dst[--numLeft]=(byte)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(--offset==-1){
                for(;;){
                  if((word1&(1L<<offset))!=0){
                    dst[--numLeft]=(byte)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(--offset==-65){
                    for(;;--offset){
                      if((word0&(1L<<offset))!=0){
                        dst[--numLeft]=(byte)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_ARR;
      }
      @Override public short[] toShortArray(){
        int numLeft;
        final long word0,word1,word2,word3;
        final ByteSetImpl.Unchecked root;
        if((numLeft=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3))!=0){ 
          final short[] dst=new short[numLeft];
        int offset=Byte.MAX_VALUE;
        outer:for(;;){
          if((word3&(1L<<offset))!=0){
            dst[--numLeft]=(short)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(--offset==63){
            for(;;){
              if((word2&(1L<<offset))!=0){
                dst[--numLeft]=(short)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(--offset==-1){
                for(;;){
                  if((word1&(1L<<offset))!=0){
                    dst[--numLeft]=(short)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(--offset==-65){
                    for(;;--offset){
                      if((word0&(1L<<offset))!=0){
                        dst[--numLeft]=(short)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfShort.DEFAULT_ARR;
      }
      @Override public int[] toIntArray(){
        int numLeft;
        final long word0,word1,word2,word3;
        final ByteSetImpl.Unchecked root;
        if((numLeft=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3))!=0){ 
          final int[] dst=new int[numLeft];
        int offset=Byte.MAX_VALUE;
        outer:for(;;){
          if((word3&(1L<<offset))!=0){
            dst[--numLeft]=(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(--offset==63){
            for(;;){
              if((word2&(1L<<offset))!=0){
                dst[--numLeft]=(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(--offset==-1){
                for(;;){
                  if((word1&(1L<<offset))!=0){
                    dst[--numLeft]=(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(--offset==-65){
                    for(;;--offset){
                      if((word0&(1L<<offset))!=0){
                        dst[--numLeft]=(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfInt.DEFAULT_ARR;
      }
      @Override public long[] toLongArray(){
        int numLeft;
        final long word0,word1,word2,word3;
        final ByteSetImpl.Unchecked root;
        if((numLeft=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3))!=0){ 
          final long[] dst=new long[numLeft];
        long offset=Byte.MAX_VALUE;
        outer:for(;;){
          if((word3&(1L<<offset))!=0){
            dst[--numLeft]=(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(--offset==63){
            for(;;){
              if((word2&(1L<<offset))!=0){
                dst[--numLeft]=(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(--offset==-1){
                for(;;){
                  if((word1&(1L<<offset))!=0){
                    dst[--numLeft]=(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(--offset==-65){
                    for(;;--offset){
                      if((word0&(1L<<offset))!=0){
                        dst[--numLeft]=(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
      }
      @Override public float[] toFloatArray(){
        int numLeft;
        final long word0,word1,word2,word3;
        final ByteSetImpl.Unchecked root;
        if((numLeft=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3))!=0){ 
          final float[] dst=new float[numLeft];
        int offset=Byte.MAX_VALUE;
        outer:for(;;){
          if((word3&(1L<<offset))!=0){
            dst[--numLeft]=(float)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(--offset==63){
            for(;;){
              if((word2&(1L<<offset))!=0){
                dst[--numLeft]=(float)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(--offset==-1){
                for(;;){
                  if((word1&(1L<<offset))!=0){
                    dst[--numLeft]=(float)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(--offset==-65){
                    for(;;--offset){
                      if((word0&(1L<<offset))!=0){
                        dst[--numLeft]=(float)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
      }
      @Override public double[] toDoubleArray(){
        int numLeft;
        final long word0,word1,word2,word3;
        final ByteSetImpl.Unchecked root;
        if((numLeft=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3))!=0){ 
          final double[] dst=new double[numLeft];
        int offset=Byte.MAX_VALUE;
        outer:for(;;){
          if((word3&(1L<<offset))!=0){
            dst[--numLeft]=(double)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(--offset==63){
            for(;;){
              if((word2&(1L<<offset))!=0){
                dst[--numLeft]=(double)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(--offset==-1){
                for(;;){
                  if((word1&(1L<<offset))!=0){
                    dst[--numLeft]=(double)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(--offset==-65){
                    for(;;--offset){
                      if((word0&(1L<<offset))!=0){
                        dst[--numLeft]=(double)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfDouble.DEFAULT_ARR;
      }
    }
    private static class Descending extends AbstractUncheckedFullView implements Cloneable,Serializable{
      private static final long serialVersionUID=1L;
      private Descending(ByteSetImpl.Unchecked root){
        super(root);
      }
      private Object writeReplace(){
        return new ByteSetImpl.Unchecked.Descending(root);
      }
      @Override public Object clone(){
        return new ByteSetImpl.Unchecked.Descending(root);
      }
      @Override public OmniIterator.OfByte iterator(){
        return new UncheckedDescendingItr(root);
      }
      @Override public OmniIterator.OfByte descendingIterator(){
        return new UncheckedAscendingItr(root);
      }
      @Override public ByteComparator comparator(){
        return ByteComparator::descendingCompare;
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement){
        if(toElement==Byte.MIN_VALUE){
          return this;
        }else{
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement){
        if(fromElement==Byte.MAX_VALUE){
          return this;
        }else{
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,byte toElement){
        if(fromElement==Byte.MAX_VALUE){
          return headSet(toElement);
        }else{
          if(toElement==Byte.MIN_VALUE){
            return tailSet(toElement);
          }else{
            //TODO
            throw new omni.util.NotYetImplementedException();
          }
        }
      }
      @Override public OmniNavigableSet.OfByte headSet(byte toElement,boolean inclusive){
        if(inclusive){
          return headSet(toElement);
        }else{
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
      }
      @Override public OmniNavigableSet.OfByte tailSet(byte fromElement,boolean inclusive){
        if(inclusive){
          return tailSet(fromElement);
        }else{
          //TODO
          throw new omni.util.NotYetImplementedException();
        }
      }
      @Override public OmniNavigableSet.OfByte subSet(byte fromElement,boolean fromInclusive,byte toElement,boolean toInclusive){
        if(fromInclusive){
          if(toInclusive){
            return subSet(fromElement,toElement);
          }else{
            //TODO
            throw new omni.util.NotYetImplementedException();
          }
        }else{
          if(toInclusive){
            //TODO
            throw new omni.util.NotYetImplementedException();
          }else{
            //TODO
            throw new omni.util.NotYetImplementedException();
          }
        }
      }
      @Override public String toString(){
        int numLeft;
        final long word0,word1,word2,word3;
        final ByteSetImpl.Unchecked root;
        if((numLeft=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3))!=0){
          final byte[] buffer;
          int bufferOffset;
          (buffer=new byte[6*numLeft])[bufferOffset=0]='[';
          goToEnd:for(int offset=Byte.MAX_VALUE;;){
            if((word3&(1L<<offset))!=0){
              bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
              if(--numLeft==0){
                 break goToEnd;
              }
              buffer[bufferOffset]=',';
              buffer[++bufferOffset]=' ';
            }
            if(--offset==63){
              for(;;){
                if((word2&(1L<<offset))!=0){
                  bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
                  if(--numLeft==0){
                    break goToEnd;
                  }
                  buffer[bufferOffset]=',';
                  buffer[++bufferOffset]=' ';
                }
                if(--offset==-1){
                  for(;;){
                    if((word1&(1L<<offset))!=0){
                      bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
                      if(--numLeft==0){
                        break goToEnd;
                      }
                      buffer[bufferOffset]=',';
                      buffer[++bufferOffset]=' ';
                    }
                    if(--offset==-65){
                      for(;;--offset){
                        if((word0&(1L<<offset))!=0){
                          bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
                          if(--numLeft==0){
                            break goToEnd;
                          }
                          buffer[bufferOffset]=',';
                          buffer[++bufferOffset]=' ';
                        }
                      }
                    }
                  }
                }
              }
            }
          }
          buffer[++bufferOffset]=']';
          return new String(buffer,0,bufferOffset+1,ToStringUtil.IOS8859CharSet);
        }
        return "[]";
      }
      @Override public void forEach(ByteConsumer action){
        final ByteSetImpl.Unchecked root;
        Unchecked.Descending.uncheckedWordForEach((root=this.root).word3,127,63,action);  
        Unchecked.Descending.uncheckedWordForEach(root.word2,63,-1,action);
        Unchecked.Descending.uncheckedWordForEach(root.word1,-1,-65,action);
        Unchecked.Descending.uncheckedWordForEach(root.word0,-65,-129,action);
      }
      @Override public void forEach(Consumer<? super Byte> action){
        this.forEach((ByteConsumer)action::accept);
      }
      @SuppressWarnings("unchecked")
      @Override public <T> T[] toArray(T[] dst){
        int numLeft;
        final long word0,word1,word2,word3;
        final ByteSetImpl.Unchecked root;
        if((numLeft=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3))!=0){    
          dst=OmniArray.uncheckedArrResize(numLeft,dst);
          int offset=Byte.MIN_VALUE;
          outer:for(;;){
            if((word0&(1L<<offset))!=0){
              dst[--numLeft]=(T)(Byte)(byte)(offset);
              if(numLeft==0){
                break outer;
              }
            }
            if(++offset==-64){
              for(;;){
                if((word1&(1L<<offset))!=0){
                  dst[--numLeft]=(T)(Byte)(byte)(offset);
                  if(numLeft==0){
                    break outer;
                  }
                }
                if(++offset==0){
                  for(;;){
                    if((word2&(1L<<offset))!=0){
                      dst[--numLeft]=(T)(Byte)(byte)(offset);
                      if(numLeft==0){
                        break outer;
                      }
                    }
                    if(++offset==64){
                      for(;;--offset){
                        if((word3&(1L<<offset))!=0){
                          dst[--numLeft]=(T)(Byte)(byte)(offset);
                          if(numLeft==0){
                            break outer;
                          }
                        }
                      }
                    }
                  }
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
        int numLeft;
        final long word0,word1,word2,word3;
        final ByteSetImpl.Unchecked root;
        final T[] dst=arrConstructor.apply(numLeft=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3));
        if(numLeft!=0){
          int offset=Byte.MIN_VALUE;
          outer:for(;;){
            if((word0&(1L<<offset))!=0){
              dst[--numLeft]=(T)(Byte)(byte)(offset);
              if(numLeft==0){
                break outer;
              }
            }
            if(++offset==-64){
              for(;;){
                if((word1&(1L<<offset))!=0){
                  dst[--numLeft]=(T)(Byte)(byte)(offset);
                  if(numLeft==0){
                    break outer;
                  }
                }
                if(++offset==0){
                  for(;;){
                    if((word2&(1L<<offset))!=0){
                      dst[--numLeft]=(T)(Byte)(byte)(offset);
                      if(numLeft==0){
                        break outer;
                      }
                    }
                    if(++offset==64){
                      for(;;--offset){
                        if((word3&(1L<<offset))!=0){
                          dst[--numLeft]=(T)(Byte)(byte)(offset);
                          if(numLeft==0){
                            break outer;
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
        return dst;
      }
      @Override public Byte[] toArray(){
        int numLeft;
        final long word0,word1,word2,word3;
        final ByteSetImpl.Unchecked root;
        if((numLeft=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3))!=0){ 
          final Byte[] dst=new Byte[numLeft];
        int offset=Byte.MIN_VALUE;
        outer:for(;;){
          if((word0&(1L<<offset))!=0){
            dst[--numLeft]=(Byte)(byte)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(++offset==-64){
            for(;;){
              if((word1&(1L<<offset))!=0){
                dst[--numLeft]=(Byte)(byte)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(++offset==0){
                for(;;){
                  if((word2&(1L<<offset))!=0){
                    dst[--numLeft]=(Byte)(byte)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(++offset==64){
                    for(;;--offset){
                      if((word3&(1L<<offset))!=0){
                        dst[--numLeft]=(Byte)(byte)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_BOXED_ARR;
      }
      @Override public byte[] toByteArray(){
        int numLeft;
        final long word0,word1,word2,word3;
        final ByteSetImpl.Unchecked root;
        if((numLeft=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3))!=0){ 
          final byte[] dst=new byte[numLeft];
        int offset=Byte.MIN_VALUE;
        outer:for(;;){
          if((word0&(1L<<offset))!=0){
            dst[--numLeft]=(byte)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(++offset==-64){
            for(;;){
              if((word1&(1L<<offset))!=0){
                dst[--numLeft]=(byte)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(++offset==0){
                for(;;){
                  if((word2&(1L<<offset))!=0){
                    dst[--numLeft]=(byte)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(++offset==64){
                    for(;;--offset){
                      if((word3&(1L<<offset))!=0){
                        dst[--numLeft]=(byte)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_ARR;
      }
      @Override public short[] toShortArray(){
        int numLeft;
        final long word0,word1,word2,word3;
        final ByteSetImpl.Unchecked root;
        if((numLeft=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3))!=0){ 
          final short[] dst=new short[numLeft];
        int offset=Byte.MIN_VALUE;
        outer:for(;;){
          if((word0&(1L<<offset))!=0){
            dst[--numLeft]=(short)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(++offset==-64){
            for(;;){
              if((word1&(1L<<offset))!=0){
                dst[--numLeft]=(short)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(++offset==0){
                for(;;){
                  if((word2&(1L<<offset))!=0){
                    dst[--numLeft]=(short)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(++offset==64){
                    for(;;--offset){
                      if((word3&(1L<<offset))!=0){
                        dst[--numLeft]=(short)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfShort.DEFAULT_ARR;
      }
      @Override public int[] toIntArray(){
        int numLeft;
        final long word0,word1,word2,word3;
        final ByteSetImpl.Unchecked root;
        if((numLeft=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3))!=0){ 
          final int[] dst=new int[numLeft];
        int offset=Byte.MIN_VALUE;
        outer:for(;;){
          if((word0&(1L<<offset))!=0){
            dst[--numLeft]=(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(++offset==-64){
            for(;;){
              if((word1&(1L<<offset))!=0){
                dst[--numLeft]=(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(++offset==0){
                for(;;){
                  if((word2&(1L<<offset))!=0){
                    dst[--numLeft]=(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(++offset==64){
                    for(;;--offset){
                      if((word3&(1L<<offset))!=0){
                        dst[--numLeft]=(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfInt.DEFAULT_ARR;
      }
      @Override public long[] toLongArray(){
        int numLeft;
        final long word0,word1,word2,word3;
        final ByteSetImpl.Unchecked root;
        if((numLeft=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3))!=0){ 
          final long[] dst=new long[numLeft];
        long offset=Byte.MIN_VALUE;
        outer:for(;;){
          if((word0&(1L<<offset))!=0){
            dst[--numLeft]=(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(++offset==-64){
            for(;;){
              if((word1&(1L<<offset))!=0){
                dst[--numLeft]=(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(++offset==0){
                for(;;){
                  if((word2&(1L<<offset))!=0){
                    dst[--numLeft]=(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(++offset==64){
                    for(;;--offset){
                      if((word3&(1L<<offset))!=0){
                        dst[--numLeft]=(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
      }
      @Override public float[] toFloatArray(){
        int numLeft;
        final long word0,word1,word2,word3;
        final ByteSetImpl.Unchecked root;
        if((numLeft=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3))!=0){ 
          final float[] dst=new float[numLeft];
        int offset=Byte.MIN_VALUE;
        outer:for(;;){
          if((word0&(1L<<offset))!=0){
            dst[--numLeft]=(float)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(++offset==-64){
            for(;;){
              if((word1&(1L<<offset))!=0){
                dst[--numLeft]=(float)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(++offset==0){
                for(;;){
                  if((word2&(1L<<offset))!=0){
                    dst[--numLeft]=(float)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(++offset==64){
                    for(;;--offset){
                      if((word3&(1L<<offset))!=0){
                        dst[--numLeft]=(float)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
      }
      @Override public double[] toDoubleArray(){
        int numLeft;
        final long word0,word1,word2,word3;
        final ByteSetImpl.Unchecked root;
        if((numLeft=SetCommonImpl.size(word0=(root=this.root).word0,word1=root.word1,word2=root.word2,word3=root.word3))!=0){ 
          final double[] dst=new double[numLeft];
        int offset=Byte.MIN_VALUE;
        outer:for(;;){
          if((word0&(1L<<offset))!=0){
            dst[--numLeft]=(double)(offset);
            if(numLeft==0){
              break outer;
            }
          }
          if(++offset==-64){
            for(;;){
              if((word1&(1L<<offset))!=0){
                dst[--numLeft]=(double)(offset);
                if(numLeft==0){
                  break outer;
                }
              }
              if(++offset==0){
                for(;;){
                  if((word2&(1L<<offset))!=0){
                    dst[--numLeft]=(double)(offset);
                    if(numLeft==0){
                      break outer;
                    }
                  }
                  if(++offset==64){
                    for(;;--offset){
                      if((word3&(1L<<offset))!=0){
                        dst[--numLeft]=(double)(offset);
                        if(numLeft==0){
                          break outer;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
          return dst;
        }
        return OmniArray.OfDouble.DEFAULT_ARR;
      }
    }
  }
  private void clearHeadSet(int exclusiveTo){
    goToEnd:for(;;){
      clearWord0:for(;;){
        switch(exclusiveTo>>6){
        case -2:
          this.word0&=(-1L<<exclusiveTo);
          break goToEnd;
        case -1:
          this.word1&=(-1L<<exclusiveTo);
          break clearWord0;
        case 0:
          this.word2&=(-1L<<exclusiveTo);
          break;
        default:
          this.word3&=(-1L<<exclusiveTo);
          this.word2=0;
        }       
        this.word1=0;
        break;
      }
      this.word0=0;
      break;
    }
  }
  private void clearTailSet(int inclusiveFrom){
    goToEnd:for(;;){
      clearWord3:for(;;){
        switch(inclusiveFrom>>6){
        case 1:
          this.word3&=(-1L>>>(-inclusiveFrom));
          break goToEnd;
        case 0:
          this.word2&=(-1L>>>(-inclusiveFrom));
          break clearWord3;
        case -1:
          this.word1&=(-1L>>>(-inclusiveFrom));
          break;
        default:
          this.word0&=(-1L>>>(-inclusiveFrom));
          this.word1=0;
        }
        this.word2=0;
        break;
      }
      this.word3=0;
      break;
    }
  }
  private void clearBodySet(int boundInfo){
    final int exclusiveTo=(boundInfo>>8);
    goToEnd:switch((boundInfo=(byte)(boundInfo&0xff))>>6){
      case -2:
        clearWord0:for(;;){
          switch(exclusiveTo>>6){
            case -2:
              this.word0&=((-1L>>>(-boundInfo))|(-1L<<exclusiveTo));
              break goToEnd;
            case -1:
              this.word1&=(-1L<<exclusiveTo);
              break clearWord0;
            case 0:
              this.word2&=(-1L<<exclusiveTo);
              break;
            default:
              this.word3&=(-1L<<exclusiveTo);
              this.word2=0;
          }
          this.word1=0;
          break;
        }
        this.word0&=(-1L>>>(-boundInfo));
        break;
      case -1:
        switch(exclusiveTo>>6){
          case -1:
            this.word1&=((-1L>>>(-boundInfo))|(-1L<<exclusiveTo));
            break goToEnd;
          case 0:
            this.word2&=(-1L<<exclusiveTo);
            break;
          default:
            this.word3&=(-1L<<exclusiveTo);
            this.word2=0;
        }
        this.word1&=(-1L>>>(-boundInfo));
        break;
      case 0:
        if(exclusiveTo>>6==0){
          this.word2&=((-1L>>>(-boundInfo))|(-1L<<exclusiveTo));
        }else{
          this.word2&=(-1L>>>(-boundInfo));
          this.word3&=(-1L<<exclusiveTo);
        }
        break;
      default:
        this.word3&=((-1L>>>(-boundInfo))|(-1L<<exclusiveTo));
    }
  }
  private static abstract class UncheckedSubSet extends AbstractByteSet{
    transient final ByteSetImpl.Unchecked root;
    transient final UncheckedSubSet parent;
    transient final int boundInfo;
    transient int size;
    //TODO hashCode
    //TODO toString
    //TODO equals
    private UncheckedSubSet(ByteSetImpl.Unchecked root,int boundInfo,int size){
      this.root=root;
      this.parent=null;
      this.boundInfo=boundInfo;
      this.size=size;
    }
    private UncheckedSubSet(UncheckedSubSet parent,int boundInfo,int size){
      this.root=parent.root;
      this.parent=parent;
      this.boundInfo=boundInfo;
      this.size=size;
    }
    abstract boolean isInRange(int val);
    @Override public boolean contains(boolean val){
      final int mask;
      return size!=0 && isInRange(mask=val?0b10:0b01) && (root.word2&mask)!=0;
    }
    @Override public boolean contains(byte val){
      return size!=0 && isInRange(val) && root.contains(val);
    }
    @Override public boolean contains(char val){
      return size!=0 && isInRange(val) && root.contains(val);
    }
    @Override public boolean contains(int val){
      return size!=0 && isInRange(val) && root.contains(val);
    }
    @Override public boolean contains(long val){
      final int v;
      return size!=0 && (v=(int)val)==val && isInRange(v) && root.contains(v);
    }
    @Override public boolean contains(float val){
      final int v;
      return size!=0 && (v=(int)val)==val && isInRange(v) && root.contains(v);
    }
    @Override public boolean contains(double val){
      final int v;
      return size!=0 && (v=(int)val)==val && isInRange(v) && root.contains(v);
    }
    @Override public boolean contains(Object val){
      if(size!=0){
        for(;;){
          if(val instanceof Byte){
            final byte v;
            return isInRange(v=(byte)val) && root.contains(v);
          }
          final int v;
          if(val instanceof Integer || val instanceof Short){
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
            final char c;
            return isInRange(c=(char)val) && root.contains(c);
          }else if(val instanceof Boolean){
            return isInRange(v=((boolean)val)?0b10:0b01) && (root.word2&v)!=0;
          }else{
            break;
          }
          return isInRange(v) && root.contains(v);
        }
      }
      return false;
    }
    private void bubbleUpDecrementSize(int numToRemove){
      for(var parent=this.parent;parent!=null;parent.size-=numToRemove,parent=parent.parent){}
    }
    private void bubbleUpIncrementSize(){
      for(var parent=this.parent;parent!=null;++parent.size,parent=parent.parent){}
    }
    abstract int removeIfImpl(int size,BytePredicate filter);
    @Override public boolean removeIf(BytePredicate filter){
      final int size,numRemoved;
      if((size=this.size)!=0 && (numRemoved=this.removeIfImpl(size,filter))!=0){
        bubbleUpDecrementSize(numRemoved);
        this.size=size-numRemoved;
        return true;
      }
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      final int size,numRemoved;
      if((size=this.size)!=0 && (numRemoved=this.removeIfImpl(size,filter::test))!=0){
        bubbleUpDecrementSize(numRemoved);
        this.size=size-numRemoved;
        return true;
      }
      return false;
    }
    @Override public int size(){
      return this.size;
    }
    @Override public boolean isEmpty(){
      return this.size==0;
    }
    @Override public boolean add(byte val){
      if(((ByteSetImpl)root).uncheckedAdd(val)){
        ++this.size;
        bubbleUpIncrementSize();
        return true;
      }
      return false;
    }
    @Override public boolean add(boolean val){
      if(root.add(val)){
        ++this.size;
        bubbleUpIncrementSize();
        return true;
      }
      return false;
    }
    abstract void clearImpl();
    @Override public void clear(){
      final int size;
      if((size=this.size)!=0){
        bubbleUpDecrementSize(size);
        this.size=0;
        clearImpl();
      }
    }
    private static abstract class HeadSet extends UncheckedSubSet{
      private HeadSet(ByteSetImpl.Unchecked root,int boundInfo,int size){
        super(root,boundInfo,size);
      }
      private HeadSet(HeadSet parent,int boundInfo,int size){
        super(parent,boundInfo,size);
      }
      @Override int removeIfImpl(int size,BytePredicate filter){
        return root.headSetRemoveIfImpl(this.boundInfo-1,size,filter);
      }
      @Override boolean isInRange(int val){
        return val<this.boundInfo;
      }
      @Override void clearImpl(){
        ((ByteSetImpl)root).clearHeadSet(this.boundInfo);
      }
      private static class Ascending extends HeadSet{
        private Ascending(ByteSetImpl.Unchecked root,int boundInfo,int size){
          super(root,boundInfo,size);
        }
        private Ascending(Ascending parent,int boundInfo,int size){
          super(parent,boundInfo,size);
        }
      }
      private static class Descending extends HeadSet{
        private Descending(ByteSetImpl.Unchecked root,int boundInfo,int size){
          super(root,boundInfo,size);
        }
        private Descending(Descending parent,int boundInfo,int size){
          super(parent,boundInfo,size);
        }
      }
    }
    private static abstract class TailSet extends UncheckedSubSet{
      private TailSet(ByteSetImpl.Unchecked root,int boundInfo,int size){
        super(root,boundInfo,size);
      }
      private TailSet(TailSet parent,int boundInfo,int size){
        super(parent,boundInfo,size);
      }
      @Override boolean isInRange(int val){
        return val>=this.boundInfo;
      }
      @Override void clearImpl(){
        ((ByteSetImpl)root).clearTailSet(this.boundInfo);
      }
      @Override int removeIfImpl(int size,BytePredicate filter){
        int numRemoved=0;
        final ByteSetImpl root=this.root;
        int inclusiveFrom;
        goToWord3:for(;;){
          goToWord2:for(;;){
            switch((inclusiveFrom=this.boundInfo)>>6){
              case 1:
                break goToWord3;
              case 0:
                break goToWord2;
              case -1:
                break;
              default:
                for(long word=root.word0;;){
                  final long marker;
                  if((word&(marker=1L<<inclusiveFrom))!=0){
                    if(filter.test((byte)inclusiveFrom)){
                      ++numRemoved;
                      word&=(~marker);
                    }
                    if(--size==0){
                      root.word0=word;
                      return numRemoved;
                    }
                  }
                  if(++inclusiveFrom==-64){
                    root.word0=word;
                    break;
                  }
                }
            }
            for(long word=root.word1;;){
              final long marker;
              if((word&(marker=1L<<inclusiveFrom))!=0){
                if(filter.test((byte)inclusiveFrom)){
                  ++numRemoved;
                  word&=(~marker);
                }
                if(--size==0){
                  root.word1=word;
                  return numRemoved;
                }
              }
              if(++inclusiveFrom==0){
                root.word1=word;
                break goToWord2;
              }
            }
          }
          for(long word=root.word2;;){
            final long marker;
            if((word&(marker=1L<<inclusiveFrom))!=0){
              if(filter.test((byte)inclusiveFrom)){
                ++numRemoved;
                word&=(~marker);
              }
              if(--size==0){
                root.word2=word;
                return numRemoved;
              }
            }
            if(++inclusiveFrom==64){
              root.word2=word;
              break goToWord3;
            }
          }
        }
        for(long word=root.word3;;++inclusiveFrom){
          final long marker;
          if((word&(marker=1L<<inclusiveFrom))!=0){
            if(filter.test((byte)inclusiveFrom)){
              ++numRemoved;
              word&=(~marker);
            }
            if(--size==0){
              root.word3=word;
              return numRemoved;
            }
          }
        }
      }
      private static class Ascending extends TailSet{
        private Ascending(ByteSetImpl.Unchecked root,int boundInfo,int size){
          super(root,boundInfo,size);
        }
        private Ascending(Ascending parent,int boundInfo,int size){
          super(parent,boundInfo,size);
        }
      }
      private static class Descending extends TailSet{
        private Descending(ByteSetImpl.Unchecked root,int boundInfo,int size){
          super(root,boundInfo,size);
        }
        private Descending(Descending parent,int boundInfo,int size){
          super(parent,boundInfo,size);
        }
      }
    }
    private static abstract class BodySet extends UncheckedSubSet{
      private BodySet(ByteSetImpl.Unchecked root,int boundInfo,int size){
        super(root,boundInfo,size);
      }
      private BodySet(UncheckedSubSet parent,int boundInfo,int size){
        super(parent,boundInfo,size);
      }
      @Override boolean isInRange(int val){
        final int boundInfo;
        return val<((boundInfo=this.boundInfo)>>8) && val>=((byte)(boundInfo&0x1ff));
      }
      @Override void clearImpl(){
        ((ByteSetImpl)root).clearBodySet(this.boundInfo);
      }
      @Override int removeIfImpl(int size,BytePredicate filter){
        return root.headSetRemoveIfImpl((this.boundInfo>>8)-1,size,filter);
      }
      private static class Ascending extends BodySet{
        private Ascending(ByteSetImpl.Unchecked root,int boundInfo,int size){
          super(root,boundInfo,size);
        }
        private Ascending(UncheckedSubSet parent,int boundInfo,int size){
          super(parent,boundInfo,size);
        }
      }
      private static class Descending extends BodySet{
        private Descending(ByteSetImpl.Unchecked root,int boundInfo,int size){
          super(root,boundInfo,size);
        }
        private Descending(UncheckedSubSet parent,int boundInfo,int size){
          super(parent,boundInfo,size);
        }
      }
    }
  }
  private static abstract class CheckedSubSet extends AbstractByteSet{
    transient final ByteSetImpl.Checked root;
    transient final CheckedSubSet parent;
    transient final int boundInfo;
    transient int modCountAndSize;
    //TODO hashCode
    //TODO toString
    //TODO equals
    abstract boolean isInRange(int val);
    @Override public boolean contains(boolean val){
      final ByteSetImpl.Checked root;
      final int mask;
      final int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,(root=this.root).modCountAndSize>>>9);
      return (modCountAndSize&0x1ff)!=0 && isInRange(mask=val?0b10:0b01) && (root.word2&mask)!=0;
    }
    @Override public boolean contains(byte val){
      final ByteSetImpl.Checked root;
      final int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,(root=this.root).modCountAndSize>>>9);
      return (modCountAndSize&0x1ff)!=0 && isInRange(val) && root.contains(val);
    }
    @Override public boolean contains(char val){
      final ByteSetImpl.Checked root;
      final int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,(root=this.root).modCountAndSize>>>9);
      return (modCountAndSize&0x1ff)!=0 && isInRange(val) && root.contains(val);
    }
    @Override public boolean contains(int val){
      final ByteSetImpl.Checked root;
      final int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,(root=this.root).modCountAndSize>>>9);
      return (modCountAndSize&0x1ff)!=0 && isInRange(val) && root.contains(val);
    }
    @Override public boolean contains(long val){
      final ByteSetImpl.Checked root;
      final int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,(root=this.root).modCountAndSize>>>9);
      final int v;
      return (modCountAndSize&0x1ff)!=0 && (v=(int)val)==val && isInRange(v) && root.contains(v);
    }
    @Override public boolean contains(float val){
      final ByteSetImpl.Checked root;
      final int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,(root=this.root).modCountAndSize>>>9);
      final int v;
      return (modCountAndSize&0x1ff)!=0 && (v=(int)val)==val && isInRange(v) && root.contains(v);
    }
    @Override public boolean contains(double val){
      final ByteSetImpl.Checked root;
      final int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,(root=this.root).modCountAndSize>>>9);
      final int v;
      return (modCountAndSize&0x1ff)!=0 && (v=(int)val)==val && isInRange(v) && root.contains(v);
    }
    @Override public boolean contains(Object val){
      final ByteSetImpl.Checked root;
      final int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,(root=this.root).modCountAndSize>>>9);
      if((modCountAndSize&0x1ff)!=0){
        for(;;){
          if(val instanceof Byte){
            final byte v;
            return isInRange(v=(byte)val) && root.contains(v);
          }
          final int v;
          if(val instanceof Integer || val instanceof Short){
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
            final char c;
            return isInRange(c=(char)val) && root.contains(c);
          }else if(val instanceof Boolean){
            return isInRange(v=((boolean)val)?0b10:0b01) && (root.word2&v)!=0;
          }else{
            break;
          }
          return isInRange(v) && root.contains(v);
        }
      }
      return false;
    }
    @Override public boolean add(byte val){
      final ByteSetImpl.Checked root;
      int modCountAndSize;
      final int rootModCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,(rootModCountAndSize=(root=this.root).modCountAndSize)>>>9);
      if(isInRange(val)){
        if(((ByteSetImpl)root).uncheckedAdd(val)){
          this.modCountAndSize=modCountAndSize+(modCountAndSize=(1<<9)+1);
          root.modCountAndSize=rootModCountAndSize+modCountAndSize;
          bubbleUpModifyModCountAndSize(modCountAndSize);
          return true;
        }
        return false;
      }
      throw new IllegalArgumentException("out of bounds");
    }
    @Override public boolean add(boolean val){
      final ByteSetImpl.Checked root;
      int modCountAndSize;
      final int rootModCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,(rootModCountAndSize=(root=this.root).modCountAndSize)>>>9);
      final int mask;
      if(isInRange(mask=val?0b10:0b01)){
        long word;
        if((word=root.word2)!=(word|=mask)){
          root.word2=word;
          this.modCountAndSize=modCountAndSize+(modCountAndSize=(1<<9)+1);
          root.modCountAndSize=rootModCountAndSize+modCountAndSize;
          bubbleUpModifyModCountAndSize(modCountAndSize);
          return true;
        }
        return false;
      }
      throw new IllegalArgumentException("out of bounds");
    }
    abstract int removeIfImpl(int modCount,int size,ByteSetImpl.Checked root,BytePredicate filter);
    @Override public boolean removeIf(BytePredicate filter){
      final var root=this.root;
      final int modCountAndSize;
      int size;
      if((size=(modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        if((size=this.removeIfImpl(modCountAndSize&(~0x1ff),size,root,filter))!=0){
          this.modCountAndSize=modCountAndSize+size;
          bubbleUpModifyModCountAndSize(size);
          return true;
        }
      }else{
        CheckedCollection.checkModCount(modCountAndSize>>>9,root.modCountAndSize>>>9);
      }
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Byte> filter){
      final var root=this.root;
      final int modCountAndSize;
      int size;
      if((size=(modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
        if((size=this.removeIfImpl(modCountAndSize&(~0x1ff),size,root,filter::test))!=0){
          this.modCountAndSize=modCountAndSize+size;
          bubbleUpModifyModCountAndSize(size);
          return true;
        }
      }else{
        CheckedCollection.checkModCount(modCountAndSize>>>9,root.modCountAndSize>>>9);
      }
      return false;
    }
    @Override public int size(){
      final int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,root.modCountAndSize>>>9);
      return modCountAndSize&0x1ff;
    }
    @Override public boolean isEmpty(){
      final int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,root.modCountAndSize>>>9);
      return (modCountAndSize&0x1ff)==0;
    }
    abstract void clearImpl(ByteSetImpl root);
    @Override public void clear(){
      int modCountAndSize;
      final int rootModCountAndSize;
      final ByteSetImpl.Checked root;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,(rootModCountAndSize=(root=this.root).modCountAndSize)>>>9);
      final int size;
      if((size=modCountAndSize&0x1ff)!=0){
        this.modCountAndSize=modCountAndSize+(modCountAndSize=(1<<9)-size);
        root.modCountAndSize=rootModCountAndSize+modCountAndSize;
        bubbleUpModifyModCountAndSize(modCountAndSize);
        clearImpl(root);
      }
    }
    private void bubbleUpModifyModCountAndSize(int modCountAndSizeDiff){
      for(var parent=this.parent;parent!=null;parent.modCountAndSize+=modCountAndSizeDiff,parent=parent.parent){}
    }
    private CheckedSubSet(ByteSetImpl.Checked root,int boundInfo,int modCountAndSize){
      this.root=root;
      this.parent=null;
      this.boundInfo=boundInfo;
      this.modCountAndSize=modCountAndSize;
    }
    private CheckedSubSet(CheckedSubSet parent,int boundInfo,int modCountAndSize){
      this.root=parent.root;
      this.parent=parent;
      this.boundInfo=boundInfo;
      this.modCountAndSize=modCountAndSize;
    }
    private static abstract class HeadSet extends CheckedSubSet{
      private HeadSet(ByteSetImpl.Checked root,int boundInfo,int modCountAndSize){
        super(root,boundInfo,modCountAndSize);
      }
      private HeadSet(HeadSet parent,int boundInfo,int modCountAndSize){
        super(parent,boundInfo,modCountAndSize);
      }
      @Override void clearImpl(ByteSetImpl root){
        root.clearHeadSet(this.boundInfo);
      }
      @Override boolean isInRange(int val){
        return val<this.boundInfo;
      }
      @Override int removeIfImpl(int modCount,int size,ByteSetImpl.Checked root,BytePredicate filter){
        int numRemoved=0;
        final int rootModCountAndSize;
        long word0=root.word0;
        for(int offset=Byte.MIN_VALUE;;){
          long marker;
          if((word0&(marker=1L<<offset))!=0){
            if(filter.test((byte)offset)){
              ++numRemoved;
              word0&=(~marker);
            }
            if(--size==0){
              if(numRemoved==0){
                return 0;
              }
              CheckedCollection.checkModCount(modCount,(rootModCountAndSize=root.modCountAndSize)&(~0x1ff));
              break;
            }
          }
          if(++offset==-64){
            long word1=root.word1;
            for(;;){
              if((word1&(marker=1L<<offset))!=0){
                if(filter.test((byte)offset)){
                  ++numRemoved;
                  word1&=(~marker);
                }
                if(--size==0){
                  if(numRemoved==0){
                    return 0;
                  }
                  CheckedCollection.checkModCount(modCount,(rootModCountAndSize=root.modCountAndSize)&(~0x1ff));
                  break;
                }
              }
              if(++offset==0){
                long word2=root.word2;
                for(;;){
                  if((word2&(marker=1L<<offset))!=0){
                    if(filter.test((byte)offset)){
                      ++numRemoved;
                      word2&=(~marker);
                    }
                    if(--size==0){
                      if(numRemoved==0){
                        return 0;
                      }
                      CheckedCollection.checkModCount(modCount,(rootModCountAndSize=root.modCountAndSize)&(~0x1ff));
                      break;
                    }
                  }
                  if(++offset==64){
                    long word3=root.word3;
                    for(;;++offset){
                      if((word3&(marker=1L<<offset))!=0){
                        if(filter.test((byte)offset)){
                          ++numRemoved;
                          word3&=(~marker);
                        }
                        if(--size==0){
                          if(numRemoved==0){
                            return 0;
                          }
                          CheckedCollection.checkModCount(modCount,(rootModCountAndSize=root.modCountAndSize)&(~0x1ff));
                          break;
                        }
                      }
                    }
                    root.word3=word3;
                    break;
                  }
                }
                root.word2=word2;
                break;
              }
            }
            root.word1=word1;
            break;
          }
        }
        root.word0=word0;
        root.modCountAndSize=rootModCountAndSize+(modCount=(1<<9)-numRemoved);
        return modCount;
      }
      private static class Ascending extends HeadSet{
        private Ascending(ByteSetImpl.Checked root,int boundInfo,int modCountAndSize){
          super(root,boundInfo,modCountAndSize);
        }
        private Ascending(Ascending parent,int boundInfo,int modCountAndSize){
          super(parent,boundInfo,modCountAndSize);
        }
      }
      private static class Descending extends HeadSet{
        private Descending(ByteSetImpl.Checked root,int boundInfo,int modCountAndSize){
          super(root,boundInfo,modCountAndSize);
        }
        private Descending(Descending parent,int boundInfo,int modCountAndSize){
          super(parent,boundInfo,modCountAndSize);
        }
      }
    }
    private static abstract class TailSet extends CheckedSubSet{
      private TailSet(ByteSetImpl.Checked root,int boundInfo,int modCountAndSize){
        super(root,boundInfo,modCountAndSize);
      }
      private TailSet(TailSet parent,int boundInfo,int modCountAndSize){
        super(parent,boundInfo,modCountAndSize);
      }
      @Override void clearImpl(ByteSetImpl root){
        root.clearTailSet(this.boundInfo);
      }
      @Override boolean isInRange(int val){
        return val>=this.boundInfo;
      }
      @Override int removeIfImpl(int modCount,int size,ByteSetImpl.Checked root,BytePredicate filter){
        return root.uncheckedremoveIf(modCount,size,filter);
      }
      private static class Ascending extends TailSet{
        private Ascending(ByteSetImpl.Checked root,int boundInfo,int modCountAndSize){
          super(root,boundInfo,modCountAndSize);
        }
        private Ascending(Ascending parent,int boundInfo,int modCountAndSize){
          super(parent,boundInfo,modCountAndSize);
        }
      }
      private static class Descending extends TailSet{
        private Descending(ByteSetImpl.Checked root,int boundInfo,int modCountAndSize){
          super(root,boundInfo,modCountAndSize);
        }
        private Descending(Descending parent,int boundInfo,int modCountAndSize){
          super(parent,boundInfo,modCountAndSize);
        }
      }
    }
    private static abstract class BodySet extends CheckedSubSet{
      private BodySet(ByteSetImpl.Checked root,int boundInfo,int modCountAndSize){
        super(root,boundInfo,modCountAndSize);
      }
      private BodySet(CheckedSubSet parent,int boundInfo,int modCountAndSize){
        super(parent,boundInfo,modCountAndSize);
      }
      @Override boolean isInRange(int val){
        final int boundInfo;
        return val<((boundInfo=this.boundInfo)>>8) && val>=((byte)(boundInfo&0x1ff));
      }
      @Override int removeIfImpl(int modCount,int size,ByteSetImpl.Checked root,BytePredicate filter){
        int numRemoved=0;
        long word0=root.word0,word1=root.word1,word2=root.word2,word3=root.word3;
        int inclusiveTo;
        goToWord0:for(;;){
          goToWord1:for(;;){
            switch((inclusiveTo=(this.boundInfo>>8)-1)>>6){
              case -2:
                break goToWord0;
              case -1:
                break goToWord1;
              case 0:
                break;
              default:
                for(;;){
                  final long marker;
                  if((word3&(marker=1L<<inclusiveTo))!=0){
                    if(filter.test((byte)inclusiveTo)){
                      ++numRemoved;
                      word3&=(~marker);
                    }
                    if(--size==0){
                      final int rootModCountAndSize;
                      CheckedCollection.checkModCount(modCount,(rootModCountAndSize=root.modCountAndSize)&(~0x1ff));
                      if(numRemoved!=0){
                         root.modCountAndSize=rootModCountAndSize+(modCount=(1<<9)-numRemoved);
                         root.word3=word3;
                         return modCount;
                      }
                      return 0;
                    }
                  }
                  if(--inclusiveTo==63){
                    break;
                  }
                }
            }
            for(;;){
              final long marker;
              if((word2&(marker=1L<<inclusiveTo))!=0){
                if(filter.test((byte)inclusiveTo)){
                  ++numRemoved;
                  word2&=(~marker);
                }
                if(--size==0){
                  final int rootModCountAndSize;
                  CheckedCollection.checkModCount(modCount,(rootModCountAndSize=root.modCountAndSize)&(~0x1ff));
                  if(numRemoved!=0){
                     root.modCountAndSize=rootModCountAndSize+(modCount=(1<<9)-numRemoved);
                     root.word2=word2;
                     root.word3=word3;
                     return modCount;
                  }
                  return 0;
                }
              }
              if(--inclusiveTo==-1){
                break goToWord1;
              }
            }
          }
          for(;;){
            final long marker;
            if((word1&(marker=1L<<inclusiveTo))!=0){
              if(filter.test((byte)inclusiveTo)){
                ++numRemoved;
                word1&=(~marker);
              }
              if(--size==0){
                final int rootModCountAndSize;
                CheckedCollection.checkModCount(modCount,(rootModCountAndSize=root.modCountAndSize)&(~0x1ff));
                if(numRemoved!=0){
                   root.modCountAndSize=rootModCountAndSize+(modCount=(1<<9)-numRemoved);
                   root.word1=word1;
                   root.word2=word2;
                   root.word3=word3;
                   return modCount;
                }
                return 0;
              }
            }
            if(--inclusiveTo==-65){
              this.word1=word;
              break goToWord0;
            }
          }
        }
        for(;;--inclusiveTo){
          final long marker;
          if((word0&(marker=1L<<inclusiveTo))!=0){
            if(filter.test((byte)inclusiveTo)){
              ++numRemoved;
              word0&=(~marker);
            }
            if(--size==0){
              int rootModCountAndSize;
              CheckedCollection.checkModCount(modCount,(rootModCountAndSize=root.modCountAndSize)&(~0x1ff));
              if(numRemoved!=0){
                 root.modCountAndSize=rootModCountAndSize+(modCount=(1<<9)-numRemoved);
                 root.word0=word0;
                 root.word1=word1;
                 root.word2=word2;
                 root.word3=word3;
                 return modCount;
              }
              return 0;
            }
          }
        }
      }
      @Override void clearImpl(ByteSetImpl root){
        root.clearBodySet(this.boundInfo);
      }
      private static class Ascending extends BodySet{
        private Ascending(ByteSetImpl.Checked root,int boundInfo,int modCountAndSize){
          super(root,boundInfo,modCountAndSize);
        }
        private Ascending(CheckedSubSet parent,int boundInfo,int modCountAndSize){
          super(parent,boundInfo,modCountAndSize);
        }
      }
      private static class Descending extends BodySet{
        private Descending(ByteSetImpl.Checked root,int boundInfo,int modCountAndSize){
          super(root,boundInfo,modCountAndSize);
        }
        private Descending(CheckedSubSet parent,int boundInfo,int modCountAndSize){
          super(parent,boundInfo,modCountAndSize);
        }
      }
    }
  }
}
