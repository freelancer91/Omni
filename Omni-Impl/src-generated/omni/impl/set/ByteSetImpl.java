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
import omni.util.TypeUtil;
public abstract class ByteSetImpl extends AbstractByteSet.ComparatorlessImpl implements Cloneable{
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
  private boolean uncheckedremoveByte(int val){
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
  @Override public boolean removeVal(byte val){
    return uncheckedremoveByte(val);
  }
  private boolean uncheckedremoveChar(int val){
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
  @Override public boolean removeVal(char val){
    return uncheckedremoveChar(val);
  }
  @Override public boolean removeVal(double val){
    final int v;
    return (v=(byte)val)==val && uncheckedremoveByte(v);
  }
  @Override public boolean removeVal(float val){
    final int v;
    return (v=(byte)val)==val && uncheckedremoveByte(v);
  }
  @Override public boolean removeVal(long val){
    final int v;
    return (v=(byte)val)==val && uncheckedremoveByte(v);
  }
  private boolean uncheckedremoveInt(int val){
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
  @Override public boolean removeVal(int val){
    return uncheckedremoveInt(val);
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
    {
      int v;
      if(val>Byte.MIN_VALUE && (v=((ByteSetImpl)this).getThisOrLowerInRange(val-1))!=-129){
        return (Byte)(byte)(v);
      }
    }
    return null;
  }
  @Override public Byte floor(byte val){
    {
      int v;
      if((v=((ByteSetImpl)this).getThisOrLowerInRange(val))!=-129){
        return (Byte)(byte)(v);
      }
    }
    return null;
  }
  @Override public Byte higher(byte val){
    int v;
    if(val<Byte.MAX_VALUE && (v=((ByteSetImpl)this).getThisOrHigherInRange(val+1))!=128){
      return (Byte)(byte)(v);
    }
    return null;
  }
  @Override public Byte ceiling(byte val){
    int v;
    if((v=((ByteSetImpl)this).getThisOrHigherInRange(val))!=128){
      return (Byte)(byte)(v);
    }    
    return null;
  }
  @Override public byte lowerByte(byte val){
    {
      int v;
      if(val>Byte.MIN_VALUE && (v=((ByteSetImpl)this).getThisOrLowerInRange(val-1))!=-129){
        return (byte)(v);
      }
    }
    return Byte.MIN_VALUE;
  }
  @Override public byte byteFloor(byte val){
    {
      int v;
      if((v=((ByteSetImpl)this).getThisOrLowerInRange(val))!=-129){
        return (byte)(v);
      }
    }
    return Byte.MIN_VALUE;
  }
  @Override public byte higherByte(byte val){
    int v;
    if(val<Byte.MAX_VALUE && (v=((ByteSetImpl)this).getThisOrHigherInRange(val+1))!=128){
      return (byte)(v);
    }
    return Byte.MIN_VALUE;
  }
  @Override public byte byteCeiling(byte val){
    int v;
    if((v=((ByteSetImpl)this).getThisOrHigherInRange(val))!=128){
      return (byte)(v);
    }    
    return Byte.MIN_VALUE;
  }
  @Override public short lowerShort(short val){
    {
      int v;
      if(val>Byte.MIN_VALUE && (v=((ByteSetImpl)this).getThisOrLowerInRange(val>Byte.MAX_VALUE?Byte.MAX_VALUE:val-1))!=-129){
        return (short)(v);
      }
    }
    return Short.MIN_VALUE;
  }
  @Override public short shortFloor(short val){
    {
      int v;
      if(val>=Byte.MIN_VALUE && (v=((ByteSetImpl)this).getThisOrLowerInRange(val>=Byte.MAX_VALUE?Byte.MAX_VALUE:val))!=-129){
        return (short)(v);
      }
    }
    return Short.MIN_VALUE;
  }
  @Override public short higherShort(short val){
    int v;
    if(val<Byte.MAX_VALUE && (v=((ByteSetImpl)this).getThisOrHigherInRange(val<Byte.MIN_VALUE?Byte.MIN_VALUE:val+1))!=128){
      return (short)(v);
    }
    return Short.MIN_VALUE;
  }
  @Override public short shortCeiling(short val){
    int v;
    if(val<=Byte.MAX_VALUE && (v=((ByteSetImpl)this).getThisOrHigherInRange(val<=Byte.MIN_VALUE?Byte.MIN_VALUE:val))!=128){
      return (short)(v);
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
    {
      if(val>Byte.MIN_VALUE && (val=((ByteSetImpl)this).getThisOrLowerInRange(val>Byte.MAX_VALUE?Byte.MAX_VALUE:val-1))!=-129){
        return (int)(val);
      }
    }
    return Integer.MIN_VALUE;
  }
  @Override public int intFloor(int val){
    {
      if(val>=Byte.MIN_VALUE && (val=((ByteSetImpl)this).getThisOrLowerInRange(val>=Byte.MAX_VALUE?Byte.MAX_VALUE:val))!=-129){
        return (int)(val);
      }
    }
    return Integer.MIN_VALUE;
  }
  @Override public int higherInt(int val){
    if(val<Byte.MAX_VALUE && (val=((ByteSetImpl)this).getThisOrHigherInRange(val<Byte.MIN_VALUE?Byte.MIN_VALUE:val+1))!=128){
      return (int)(val);
    }
    return Integer.MIN_VALUE;
  }
  @Override public int intCeiling(int val){
    if(val<=Byte.MAX_VALUE && (val=((ByteSetImpl)this).getThisOrHigherInRange(val<=Byte.MIN_VALUE?Byte.MIN_VALUE:val))!=128){
      return (int)(val);
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
  @Override public long lowerLong(long val){
    {
      int v;
      if(val>Byte.MIN_VALUE && (v=((ByteSetImpl)this).getThisOrLowerInRange(val>Byte.MAX_VALUE?Byte.MAX_VALUE:((int)val)-1))!=-129){
        return (long)(v);
      }
    }
    return Long.MIN_VALUE;
  }
  @Override public long longFloor(long val){
    {
      int v;
      if(val>=Byte.MIN_VALUE && (v=((ByteSetImpl)this).getThisOrLowerInRange(val>=Byte.MAX_VALUE?Byte.MAX_VALUE:(int)val))!=-129){    
        return (long)(v);
      }
    }
    return Long.MIN_VALUE;
  }
  @Override public long higherLong(long val){
    int v;
    if(val<Byte.MAX_VALUE && (v=((ByteSetImpl)this).getThisOrHigherInRange(val<Byte.MIN_VALUE?Byte.MIN_VALUE:((int)val)+1))!=128){
      return (long)(v);
    }
    return Long.MIN_VALUE;
  }
  @Override public long longCeiling(long val){
    int v;
    if(val<=Byte.MAX_VALUE && (v=((ByteSetImpl)this).getThisOrHigherInRange(val<=Byte.MIN_VALUE?Byte.MIN_VALUE:(int)val))!=128){      
      return (long)(v);
    }    
    return Long.MIN_VALUE;
  }
  @Override public float lowerFloat(float val){
    for(;;)
    {
      int v;
      if(val!=val || val>Byte.MAX_VALUE){
        v=Byte.MAX_VALUE;
      }else if(val>Byte.MIN_VALUE){
        v=TypeUtil.lowerInt(val);
      }else{
        break;
      }
      if((v=((ByteSetImpl)this).getThisOrLowerInRange(v))!=-129){
        return (float)(v);
      }
      break;
    }
    return Float.NaN;
  }
  @Override public float floatFloor(float val){
    for(;;)
    {
      int v;
      if(val!=val || val>=Byte.MAX_VALUE){
        v=Byte.MAX_VALUE;
      }else if(val>=Byte.MIN_VALUE){
        v=TypeUtil.intFloor(val);
      }else{
        break;
      }
      if((v=((ByteSetImpl)this).getThisOrLowerInRange(v))!=-129){
        return (float)(v);
      }
      break;
    }
    return Float.NaN;
  }
  @Override public float higherFloat(float val){
    int v;
    if(val<Byte.MAX_VALUE && (v=((ByteSetImpl)this).getThisOrHigherInRange(val<Byte.MIN_VALUE?Byte.MIN_VALUE:TypeUtil.higherInt(val)))!=128){
      return (float)(v);
    }
    return Float.NaN;
  }
  @Override public float floatCeiling(float val){
    int v;
    if(val<=Byte.MAX_VALUE && (v=((ByteSetImpl)this).getThisOrHigherInRange(val<=Byte.MIN_VALUE?Byte.MIN_VALUE:TypeUtil.intCeiling(val)))!=128){      
      return (float)(v);
    }    
    return Float.NaN;
  }
  @Override public double lowerDouble(double val){
    for(;;)
    {
      int v;
      if(val!=val || val>Byte.MAX_VALUE){
        v=Byte.MAX_VALUE;
      }else if(val>Byte.MIN_VALUE){
        v=TypeUtil.lowerInt(val);
      }else{
        break;
      }
      if((v=((ByteSetImpl)this).getThisOrLowerInRange(v))!=-129){
        return (double)(v);
      }
      break;
    }
    return Double.NaN;
  }
  @Override public double doubleFloor(double val){
    for(;;)
    {
      int v;
      if(val!=val || val>=Byte.MAX_VALUE){
        v=Byte.MAX_VALUE;
      }else if(val>=Byte.MIN_VALUE){
        v=TypeUtil.intFloor(val);
      }else{
        break;
      }
      if((v=((ByteSetImpl)this).getThisOrLowerInRange(v))!=-129){
        return (double)(v);
      }
      break;
    }
    return Double.NaN;
  }
  @Override public double higherDouble(double val){
    int v;
    if(val<Byte.MAX_VALUE && (v=((ByteSetImpl)this).getThisOrHigherInRange(val<Byte.MIN_VALUE?Byte.MIN_VALUE:TypeUtil.higherInt(val)))!=128){
      return (double)(v);
    }
    return Double.NaN;
  }
  @Override public double doubleCeiling(double val){
    int v;
    if(val<=Byte.MAX_VALUE && (v=((ByteSetImpl)this).getThisOrHigherInRange(val<=Byte.MIN_VALUE?Byte.MIN_VALUE:TypeUtil.intCeiling(val)))!=128){      
      return (double)(v);
    }    
    return Double.NaN;
  }
  private int uncheckedHashCode(int numLeft){
    int sum=0;
    goToEnd:for(;;){
      int offset=127;
      for(long word=this.word3;;){
        if((word&(1L<<offset))!=0){
          sum+=offset;
          if(--numLeft==0){
            break goToEnd;
          }
        }
        if(--offset==63){
          break;
        }
      }
      for(long word=this.word2;;){
        if((word&(1L<<offset))!=0){
          sum+=offset;
          if(--numLeft==0){
            break goToEnd;
          }
        }
        if(--offset==-1){
          break;
        }
      }
      for(long word=this.word1;;){
        if((word&(1L<<offset))!=0){
          sum+=offset;
          if(--numLeft==0){
            break goToEnd;
          }
        }
        if(--offset==-65){
          break;
        }
      }
      for(long word=this.word0;;--offset){
        if((word&(1L<<offset))!=0){
          sum+=offset;
          if(--numLeft==0){
            break goToEnd;
          }
        }
      } 
    }
    return sum;
  }
  private int uncheckedHashCode(int exclusiveHi,int numLeft){
    int sum=0;
    goToEnd:switch((--exclusiveHi)>>6){
      case 1:
        for(long word=this.word3;;){
          if((word&(1L<<exclusiveHi))!=0){
            sum+=exclusiveHi;
            if(--numLeft==0){
              break goToEnd;
            }
          }
          if(--exclusiveHi==63){
            break;
          }
        }
      case 0:
        for(long word=this.word2;;){
          if((word&(1L<<exclusiveHi))!=0){
            sum+=exclusiveHi;
            if(--numLeft==0){
              break goToEnd;
            }
          }
          if(--exclusiveHi==-1){
            break;
          }
        }
      case -1:
        for(long word=this.word1;;){
          if((word&(1L<<exclusiveHi))!=0){
            sum+=exclusiveHi;
            if(--numLeft==0){
              break goToEnd;
            }
          }
          if(--exclusiveHi==-65){
            break;
          }
        }
      default:
        for(long word=this.word0;;--exclusiveHi){
          if((word&(1L<<exclusiveHi))!=0){
            sum+=exclusiveHi;
            if(--numLeft==0){
              break goToEnd;
            }
          }
        }
    }
    return sum;
  }
  private String uncheckedAscendingToString(int numLeft){
    int bufferOffset;
    byte[] buffer;
    (buffer=new byte[6*numLeft])[bufferOffset=0]='[';
    goToEnd:for(;;){
      int offset=-128;
      for(long word=this.word0;;){
        if((word&(1L<<offset))!=0){
          bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
          if(--numLeft==0){
            break goToEnd;
          }
          buffer[bufferOffset]=',';
          buffer[++bufferOffset]=' ';
        }
        if(++offset==-64){
          break;
        }
      }
      for(long word=this.word1;;){
        if((word&(1L<<offset))!=0){
          bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
          if(--numLeft==0){
            break goToEnd;
          }
          buffer[bufferOffset]=',';
          buffer[++bufferOffset]=' ';
        }
        if(++offset==0){
          break;
        }
      }
      for(long word=this.word2;;){
        if((word&(1L<<offset))!=0){
          bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
          if(--numLeft==0){
            break goToEnd;
          }
          buffer[bufferOffset]=',';
          buffer[++bufferOffset]=' ';
        }
        if(++offset==64){
          break;
        }
      }
      for(long word=this.word3;;++offset){
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
    buffer[++bufferOffset]=']';
    return new String(buffer,0,bufferOffset+1,ToStringUtil.IOS8859CharSet);
  }
  private String uncheckedDescendingToString(int numLeft){
    int bufferOffset;
    byte[] buffer;
    (buffer=new byte[6*numLeft])[bufferOffset=0]='[';
    goToEnd:for(;;){
      int offset=127;
      for(long word=this.word3;;){
        if((word&(1L<<offset))!=0){
          bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
          if(--numLeft==0){
            break goToEnd;
          }
          buffer[bufferOffset]=',';
          buffer[++bufferOffset]=' ';
        }
        if(--offset==63){
          break;
        }
      }
      for(long word=this.word2;;){
        if((word&(1L<<offset))!=0){
          bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
          if(--numLeft==0){
            break goToEnd;
          }
          buffer[bufferOffset]=',';
          buffer[++bufferOffset]=' ';
        }
        if(--offset==-1){
          break;
        }
      }
      for(long word=this.word1;;){
        if((word&(1L<<offset))!=0){
          bufferOffset=ToStringUtil.getStringShort(offset,buffer,++bufferOffset);
          if(--numLeft==0){
            break goToEnd;
          }
          buffer[bufferOffset]=',';
          buffer[++bufferOffset]=' ';
        }
        if(--offset==-65){
          break;
        }
      }
      for(long word=this.word0;;--offset){
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
    buffer[++bufferOffset]=']';
    return new String(buffer,0,bufferOffset+1,ToStringUtil.IOS8859CharSet);
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
          break;
        }
      }
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
      for(long word=this.word0;;--offset){
        if((word&(1L<<offset))!=0){
          action.accept((byte)offset);
          if(--numLeft==0){
            return;
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
          break;
        }
      }
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
      for(long word=this.word3;;++offset){
        if((word&(1L<<offset))!=0){
          action.accept((byte)offset);
          if(--numLeft==0){
            return;
          }
        }
      }
    }
    private String uncheckedAscendingToString(int inclusiveLo,int numLeft){
      int bufferOffset;
      byte[] buffer;
      (buffer=new byte[6*numLeft])[bufferOffset=0]='[';
      goToEnd:switch(inclusiveLo>>6){
        case -2:
          for(long word=this.word0;;){
            if((word&(1L<<inclusiveLo))!=0){
              bufferOffset=ToStringUtil.getStringShort(inclusiveLo,buffer,++bufferOffset);
              if(--numLeft==0){
                break goToEnd;
              }
            }
            if(++inclusiveLo==-64){
              break;
            }
          }  
        case -1:
          for(long word=this.word1;;){
            if((word&(1L<<inclusiveLo))!=0){
              bufferOffset=ToStringUtil.getStringShort(inclusiveLo,buffer,++bufferOffset);
              if(--numLeft==0){
                break goToEnd;
              }
            }
            if(++inclusiveLo==0){
              break;
            }
          }  
        case 0:
          for(long word=this.word2;;){
            if((word&(1L<<inclusiveLo))!=0){
              bufferOffset=ToStringUtil.getStringShort(inclusiveLo,buffer,++bufferOffset);
              if(--numLeft==0){
                break goToEnd;
              }
            }
            if(++inclusiveLo==64){
              break;
            }
          }  
        default:
          for(long word=this.word3;;++inclusiveLo){
            if((word&(1L<<inclusiveLo))!=0){
              bufferOffset=ToStringUtil.getStringShort(inclusiveLo,buffer,++bufferOffset);
              if(--numLeft==0){
                break goToEnd;
              }
            }
          }
      }
      buffer[++bufferOffset]=']';
      return new String(buffer,0,bufferOffset+1,ToStringUtil.IOS8859CharSet);
    }
    private void uncheckedAscendingForEach(int inclusiveLo,int numLeft,ByteConsumer action){
      goToEnd:switch(inclusiveLo>>6){
        case -2:
          for(long word=this.word0;;){
            if((word&(1L<<inclusiveLo))!=0){
              action.accept((byte)inclusiveLo);
              if(--numLeft==0){
                break goToEnd;
              }
            }
            if(++inclusiveLo==-64){
              break;
            }
          }  
        case -1:
          for(long word=this.word1;;){
            if((word&(1L<<inclusiveLo))!=0){
              action.accept((byte)inclusiveLo);
              if(--numLeft==0){
                break goToEnd;
              }
            }
            if(++inclusiveLo==0){
              break;
            }
          }
        case 0:
          for(long word=this.word2;;){
            if((word&(1L<<inclusiveLo))!=0){
              action.accept((byte)inclusiveLo);
              if(--numLeft==0){
                break goToEnd;
              }
            }
            if(++inclusiveLo==64){
              break;
            }
          }
        default:
          for(long word=this.word3;;++inclusiveLo){
            if((word&(1L<<inclusiveLo))!=0){
              action.accept((byte)inclusiveLo);
              if(--numLeft==0){
                break goToEnd;
              }
            }
          }
      }
    }
    private String uncheckedDescendingToString(int exclusiveHi,int numLeft){
      int bufferOffset;
      byte[] buffer;
      (buffer=new byte[6*numLeft])[bufferOffset=0]='[';
      goToEnd:switch((--exclusiveHi)>>6){
        case 1:
          for(long word=this.word3;;){
            if((word&(1L<<exclusiveHi))!=0){
              bufferOffset=ToStringUtil.getStringShort(exclusiveHi,buffer,++bufferOffset);
              if(--numLeft==0){
                break goToEnd;
              }
            }
            if(--exclusiveHi==63){
              break;
            }
          }  
        case 0:
          for(long word=this.word2;;){
            if((word&(1L<<exclusiveHi))!=0){
              bufferOffset=ToStringUtil.getStringShort(exclusiveHi,buffer,++bufferOffset);
              if(--numLeft==0){
                break goToEnd;
              }
            }
            if(--exclusiveHi==-1){
              break;
            }
          }  
        case -1:
          for(long word=this.word1;;){
            if((word&(1L<<exclusiveHi))!=0){
              bufferOffset=ToStringUtil.getStringShort(exclusiveHi,buffer,++bufferOffset);
              if(--numLeft==0){
                break goToEnd;
              }
            }
            if(--exclusiveHi==65){
              break;
            }
          }  
        default:
          for(long word=this.word0;;--exclusiveHi){
            if((word&(1L<<exclusiveHi))!=0){
              bufferOffset=ToStringUtil.getStringShort(exclusiveHi,buffer,++bufferOffset);
              if(--numLeft==0){
                break goToEnd;
              }
            }
          }
      }
      buffer[++bufferOffset]=']';
      return new String(buffer,0,bufferOffset+1,ToStringUtil.IOS8859CharSet);
    }
    private void uncheckedDescendingForEach(int exclusiveHi,int numLeft,ByteConsumer action){
      goToEnd:switch((--exclusiveHi)>>6){
        case 1:
          for(long word=this.word0;;){
            if((word&(1L<<exclusiveHi))!=0){
              action.accept((byte)exclusiveHi);
              if(--numLeft==0){
                break goToEnd;
              }
            }
            if(--exclusiveHi==63){
              break;
            }
          }  
        case 0:
          for(long word=this.word1;;){
            if((word&(1L<<exclusiveHi))!=0){
              action.accept((byte)exclusiveHi);
              if(--numLeft==0){
                break goToEnd;
              }
            }
            if(--exclusiveHi==-1){
              break;
            }
          }
        case -1:
          for(long word=this.word2;;){
            if((word&(1L<<exclusiveHi))!=0){
              action.accept((byte)exclusiveHi);
              if(--numLeft==0){
                break goToEnd;
              }
            }
            if(--exclusiveHi==-65){
              break;
            }
          }
        default:
          for(long word=this.word3;;--exclusiveHi){
            if((word&(1L<<exclusiveHi))!=0){
              action.accept((byte)exclusiveHi);
              if(--numLeft==0){
                break goToEnd;
              }
            }
          }
      }
    }
    private void uncheckedAscendingToArray(int numLeft,byte[] dst){
      int val=Byte.MAX_VALUE;
      for(long word=this.word3;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(byte)(val);
          if(numLeft==0){
            return;
          }
        }
        if(--val==63){
          break;
        }
      }
      for(long word=this.word2;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(byte)(val);
          if(numLeft==0){
            return;
          }
        }
        if(--val==-1){
          break;
        }
      }
      for(long word=this.word1;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(byte)(val);
          if(numLeft==0){
            return;
          }
        }
        if(--val==-65){
          break;
        }
      }
      for(long word=this.word0;;--val){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(byte)(val);
          if(numLeft==0){
            return;
          }
        }
      }
    }
    private void uncheckedDescendingToArray(int numLeft,byte[] dst){
      int val=Byte.MIN_VALUE;
      for(long word=this.word0;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(byte)(val);
          if(numLeft==0){
            return;
          }
        }
        if(++val==-64){
          break;
        }
      }
      for(long word=this.word1;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(byte)(val);
          if(numLeft==0){
            return;
          }
        }
        if(++val==0){
          break;
        }
      }
      for(long word=this.word2;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(byte)(val);
          if(numLeft==0){
            return;
          }
        }
        if(++val==64){
          break;
        }
      }
      for(long word=this.word3;;++val){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(byte)(val);
          if(numLeft==0){
            return;
          }
        }
      }
    }
    private void uncheckedAscendingToArray(int exclusiveHi,int numLeft,byte[] dst){
      goToEnd:switch((--exclusiveHi)>>6){
        case 1:
          for(long word=this.word3;;){
            if((word&(1L<<exclusiveHi))!=0){
              dst[--numLeft]=(byte)(exclusiveHi);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(--exclusiveHi==63){
              break;
            }
          }
        case 0:
          for(long word=this.word2;;){
            if((word&(1L<<exclusiveHi))!=0){
              dst[--numLeft]=(byte)(exclusiveHi);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(--exclusiveHi==-1){
              break;
            }
          }
        case -1:
          for(long word=this.word1;;){
            if((word&(1L<<exclusiveHi))!=0){
              dst[--numLeft]=(byte)(exclusiveHi);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(--exclusiveHi==-65){
              break;
            }
          }
        default:
          for(long word=this.word0;;--exclusiveHi){
            if((word&(1L<<exclusiveHi))!=0){
              dst[--numLeft]=(byte)(exclusiveHi);
              if(numLeft==0){
                break goToEnd;
              }
            }
          }
      }
    }
    private void uncheckedDescendingToArray(int inclusiveLo,int numLeft,byte[] dst){
      goToEnd:switch(inclusiveLo>>6){
        case -2:
          for(long word=this.word0;;){
            if((word&(1L<<inclusiveLo))!=0){
              dst[--numLeft]=(byte)(inclusiveLo);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(++inclusiveLo==-64){
              break;
            }
          }
        case -1:
          for(long word=this.word1;;){
            if((word&(1L<<inclusiveLo))!=0){
              dst[--numLeft]=(byte)(inclusiveLo);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(++inclusiveLo==0){
              break;
            }
          }
        case 0:
          for(long word=this.word2;;){
            if((word&(1L<<inclusiveLo))!=0){
              dst[--numLeft]=(byte)(inclusiveLo);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(++inclusiveLo==64){
              break;
            }
          }
        default:
          for(long word=this.word3;;++inclusiveLo){
            if((word&(1L<<inclusiveLo))!=0){
              dst[--numLeft]=(byte)(inclusiveLo);
              if(numLeft==0){
                break goToEnd;
              }
            }
          }
      }
    }
    private void uncheckedAscendingToArray(int numLeft,Byte[] dst){
      int val=Byte.MAX_VALUE;
      for(long word=this.word3;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(Byte)(byte)(val);
          if(numLeft==0){
            return;
          }
        }
        if(--val==63){
          break;
        }
      }
      for(long word=this.word2;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(Byte)(byte)(val);
          if(numLeft==0){
            return;
          }
        }
        if(--val==-1){
          break;
        }
      }
      for(long word=this.word1;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(Byte)(byte)(val);
          if(numLeft==0){
            return;
          }
        }
        if(--val==-65){
          break;
        }
      }
      for(long word=this.word0;;--val){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(Byte)(byte)(val);
          if(numLeft==0){
            return;
          }
        }
      }
    }
    private void uncheckedDescendingToArray(int numLeft,Byte[] dst){
      int val=Byte.MIN_VALUE;
      for(long word=this.word0;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(Byte)(byte)(val);
          if(numLeft==0){
            return;
          }
        }
        if(++val==-64){
          break;
        }
      }
      for(long word=this.word1;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(Byte)(byte)(val);
          if(numLeft==0){
            return;
          }
        }
        if(++val==0){
          break;
        }
      }
      for(long word=this.word2;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(Byte)(byte)(val);
          if(numLeft==0){
            return;
          }
        }
        if(++val==64){
          break;
        }
      }
      for(long word=this.word3;;++val){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(Byte)(byte)(val);
          if(numLeft==0){
            return;
          }
        }
      }
    }
    private void uncheckedAscendingToArray(int exclusiveHi,int numLeft,Byte[] dst){
      goToEnd:switch((--exclusiveHi)>>6){
        case 1:
          for(long word=this.word3;;){
            if((word&(1L<<exclusiveHi))!=0){
              dst[--numLeft]=(Byte)(byte)(exclusiveHi);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(--exclusiveHi==63){
              break;
            }
          }
        case 0:
          for(long word=this.word2;;){
            if((word&(1L<<exclusiveHi))!=0){
              dst[--numLeft]=(Byte)(byte)(exclusiveHi);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(--exclusiveHi==-1){
              break;
            }
          }
        case -1:
          for(long word=this.word1;;){
            if((word&(1L<<exclusiveHi))!=0){
              dst[--numLeft]=(Byte)(byte)(exclusiveHi);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(--exclusiveHi==-65){
              break;
            }
          }
        default:
          for(long word=this.word0;;--exclusiveHi){
            if((word&(1L<<exclusiveHi))!=0){
              dst[--numLeft]=(Byte)(byte)(exclusiveHi);
              if(numLeft==0){
                break goToEnd;
              }
            }
          }
      }
    }
    private void uncheckedDescendingToArray(int inclusiveLo,int numLeft,Byte[] dst){
      goToEnd:switch(inclusiveLo>>6){
        case -2:
          for(long word=this.word0;;){
            if((word&(1L<<inclusiveLo))!=0){
              dst[--numLeft]=(Byte)(byte)(inclusiveLo);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(++inclusiveLo==-64){
              break;
            }
          }
        case -1:
          for(long word=this.word1;;){
            if((word&(1L<<inclusiveLo))!=0){
              dst[--numLeft]=(Byte)(byte)(inclusiveLo);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(++inclusiveLo==0){
              break;
            }
          }
        case 0:
          for(long word=this.word2;;){
            if((word&(1L<<inclusiveLo))!=0){
              dst[--numLeft]=(Byte)(byte)(inclusiveLo);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(++inclusiveLo==64){
              break;
            }
          }
        default:
          for(long word=this.word3;;++inclusiveLo){
            if((word&(1L<<inclusiveLo))!=0){
              dst[--numLeft]=(Byte)(byte)(inclusiveLo);
              if(numLeft==0){
                break goToEnd;
              }
            }
          }
      }
    }
    private void uncheckedAscendingToArray(int numLeft,Object[] dst){
      int val=Byte.MAX_VALUE;
      for(long word=this.word3;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(Byte)(byte)(val);
          if(numLeft==0){
            return;
          }
        }
        if(--val==63){
          break;
        }
      }
      for(long word=this.word2;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(Byte)(byte)(val);
          if(numLeft==0){
            return;
          }
        }
        if(--val==-1){
          break;
        }
      }
      for(long word=this.word1;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(Byte)(byte)(val);
          if(numLeft==0){
            return;
          }
        }
        if(--val==-65){
          break;
        }
      }
      for(long word=this.word0;;--val){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(Byte)(byte)(val);
          if(numLeft==0){
            return;
          }
        }
      }
    }
    private void uncheckedDescendingToArray(int numLeft,Object[] dst){
      int val=Byte.MIN_VALUE;
      for(long word=this.word0;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(Byte)(byte)(val);
          if(numLeft==0){
            return;
          }
        }
        if(++val==-64){
          break;
        }
      }
      for(long word=this.word1;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(Byte)(byte)(val);
          if(numLeft==0){
            return;
          }
        }
        if(++val==0){
          break;
        }
      }
      for(long word=this.word2;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(Byte)(byte)(val);
          if(numLeft==0){
            return;
          }
        }
        if(++val==64){
          break;
        }
      }
      for(long word=this.word3;;++val){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(Byte)(byte)(val);
          if(numLeft==0){
            return;
          }
        }
      }
    }
    private void uncheckedAscendingToArray(int exclusiveHi,int numLeft,Object[] dst){
      goToEnd:switch((--exclusiveHi)>>6){
        case 1:
          for(long word=this.word3;;){
            if((word&(1L<<exclusiveHi))!=0){
              dst[--numLeft]=(Byte)(byte)(exclusiveHi);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(--exclusiveHi==63){
              break;
            }
          }
        case 0:
          for(long word=this.word2;;){
            if((word&(1L<<exclusiveHi))!=0){
              dst[--numLeft]=(Byte)(byte)(exclusiveHi);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(--exclusiveHi==-1){
              break;
            }
          }
        case -1:
          for(long word=this.word1;;){
            if((word&(1L<<exclusiveHi))!=0){
              dst[--numLeft]=(Byte)(byte)(exclusiveHi);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(--exclusiveHi==-65){
              break;
            }
          }
        default:
          for(long word=this.word0;;--exclusiveHi){
            if((word&(1L<<exclusiveHi))!=0){
              dst[--numLeft]=(Byte)(byte)(exclusiveHi);
              if(numLeft==0){
                break goToEnd;
              }
            }
          }
      }
    }
    private void uncheckedDescendingToArray(int inclusiveLo,int numLeft,Object[] dst){
      goToEnd:switch(inclusiveLo>>6){
        case -2:
          for(long word=this.word0;;){
            if((word&(1L<<inclusiveLo))!=0){
              dst[--numLeft]=(Byte)(byte)(inclusiveLo);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(++inclusiveLo==-64){
              break;
            }
          }
        case -1:
          for(long word=this.word1;;){
            if((word&(1L<<inclusiveLo))!=0){
              dst[--numLeft]=(Byte)(byte)(inclusiveLo);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(++inclusiveLo==0){
              break;
            }
          }
        case 0:
          for(long word=this.word2;;){
            if((word&(1L<<inclusiveLo))!=0){
              dst[--numLeft]=(Byte)(byte)(inclusiveLo);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(++inclusiveLo==64){
              break;
            }
          }
        default:
          for(long word=this.word3;;++inclusiveLo){
            if((word&(1L<<inclusiveLo))!=0){
              dst[--numLeft]=(Byte)(byte)(inclusiveLo);
              if(numLeft==0){
                break goToEnd;
              }
            }
          }
      }
    }
    private void uncheckedAscendingToArray(int numLeft,short[] dst){
      int val=Byte.MAX_VALUE;
      for(long word=this.word3;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(short)(val);
          if(numLeft==0){
            return;
          }
        }
        if(--val==63){
          break;
        }
      }
      for(long word=this.word2;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(short)(val);
          if(numLeft==0){
            return;
          }
        }
        if(--val==-1){
          break;
        }
      }
      for(long word=this.word1;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(short)(val);
          if(numLeft==0){
            return;
          }
        }
        if(--val==-65){
          break;
        }
      }
      for(long word=this.word0;;--val){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(short)(val);
          if(numLeft==0){
            return;
          }
        }
      }
    }
    private void uncheckedDescendingToArray(int numLeft,short[] dst){
      int val=Byte.MIN_VALUE;
      for(long word=this.word0;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(short)(val);
          if(numLeft==0){
            return;
          }
        }
        if(++val==-64){
          break;
        }
      }
      for(long word=this.word1;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(short)(val);
          if(numLeft==0){
            return;
          }
        }
        if(++val==0){
          break;
        }
      }
      for(long word=this.word2;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(short)(val);
          if(numLeft==0){
            return;
          }
        }
        if(++val==64){
          break;
        }
      }
      for(long word=this.word3;;++val){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(short)(val);
          if(numLeft==0){
            return;
          }
        }
      }
    }
    private void uncheckedAscendingToArray(int exclusiveHi,int numLeft,short[] dst){
      goToEnd:switch((--exclusiveHi)>>6){
        case 1:
          for(long word=this.word3;;){
            if((word&(1L<<exclusiveHi))!=0){
              dst[--numLeft]=(short)(exclusiveHi);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(--exclusiveHi==63){
              break;
            }
          }
        case 0:
          for(long word=this.word2;;){
            if((word&(1L<<exclusiveHi))!=0){
              dst[--numLeft]=(short)(exclusiveHi);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(--exclusiveHi==-1){
              break;
            }
          }
        case -1:
          for(long word=this.word1;;){
            if((word&(1L<<exclusiveHi))!=0){
              dst[--numLeft]=(short)(exclusiveHi);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(--exclusiveHi==-65){
              break;
            }
          }
        default:
          for(long word=this.word0;;--exclusiveHi){
            if((word&(1L<<exclusiveHi))!=0){
              dst[--numLeft]=(short)(exclusiveHi);
              if(numLeft==0){
                break goToEnd;
              }
            }
          }
      }
    }
    private void uncheckedDescendingToArray(int inclusiveLo,int numLeft,short[] dst){
      goToEnd:switch(inclusiveLo>>6){
        case -2:
          for(long word=this.word0;;){
            if((word&(1L<<inclusiveLo))!=0){
              dst[--numLeft]=(short)(inclusiveLo);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(++inclusiveLo==-64){
              break;
            }
          }
        case -1:
          for(long word=this.word1;;){
            if((word&(1L<<inclusiveLo))!=0){
              dst[--numLeft]=(short)(inclusiveLo);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(++inclusiveLo==0){
              break;
            }
          }
        case 0:
          for(long word=this.word2;;){
            if((word&(1L<<inclusiveLo))!=0){
              dst[--numLeft]=(short)(inclusiveLo);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(++inclusiveLo==64){
              break;
            }
          }
        default:
          for(long word=this.word3;;++inclusiveLo){
            if((word&(1L<<inclusiveLo))!=0){
              dst[--numLeft]=(short)(inclusiveLo);
              if(numLeft==0){
                break goToEnd;
              }
            }
          }
      }
    }
    private void uncheckedAscendingToArray(int numLeft,int[] dst){
      int val=Byte.MAX_VALUE;
      for(long word=this.word3;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(int)(val);
          if(numLeft==0){
            return;
          }
        }
        if(--val==63){
          break;
        }
      }
      for(long word=this.word2;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(int)(val);
          if(numLeft==0){
            return;
          }
        }
        if(--val==-1){
          break;
        }
      }
      for(long word=this.word1;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(int)(val);
          if(numLeft==0){
            return;
          }
        }
        if(--val==-65){
          break;
        }
      }
      for(long word=this.word0;;--val){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(int)(val);
          if(numLeft==0){
            return;
          }
        }
      }
    }
    private void uncheckedDescendingToArray(int numLeft,int[] dst){
      int val=Byte.MIN_VALUE;
      for(long word=this.word0;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(int)(val);
          if(numLeft==0){
            return;
          }
        }
        if(++val==-64){
          break;
        }
      }
      for(long word=this.word1;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(int)(val);
          if(numLeft==0){
            return;
          }
        }
        if(++val==0){
          break;
        }
      }
      for(long word=this.word2;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(int)(val);
          if(numLeft==0){
            return;
          }
        }
        if(++val==64){
          break;
        }
      }
      for(long word=this.word3;;++val){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(int)(val);
          if(numLeft==0){
            return;
          }
        }
      }
    }
    private void uncheckedAscendingToArray(int exclusiveHi,int numLeft,int[] dst){
      goToEnd:switch((--exclusiveHi)>>6){
        case 1:
          for(long word=this.word3;;){
            if((word&(1L<<exclusiveHi))!=0){
              dst[--numLeft]=(int)(exclusiveHi);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(--exclusiveHi==63){
              break;
            }
          }
        case 0:
          for(long word=this.word2;;){
            if((word&(1L<<exclusiveHi))!=0){
              dst[--numLeft]=(int)(exclusiveHi);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(--exclusiveHi==-1){
              break;
            }
          }
        case -1:
          for(long word=this.word1;;){
            if((word&(1L<<exclusiveHi))!=0){
              dst[--numLeft]=(int)(exclusiveHi);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(--exclusiveHi==-65){
              break;
            }
          }
        default:
          for(long word=this.word0;;--exclusiveHi){
            if((word&(1L<<exclusiveHi))!=0){
              dst[--numLeft]=(int)(exclusiveHi);
              if(numLeft==0){
                break goToEnd;
              }
            }
          }
      }
    }
    private void uncheckedDescendingToArray(int inclusiveLo,int numLeft,int[] dst){
      goToEnd:switch(inclusiveLo>>6){
        case -2:
          for(long word=this.word0;;){
            if((word&(1L<<inclusiveLo))!=0){
              dst[--numLeft]=(int)(inclusiveLo);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(++inclusiveLo==-64){
              break;
            }
          }
        case -1:
          for(long word=this.word1;;){
            if((word&(1L<<inclusiveLo))!=0){
              dst[--numLeft]=(int)(inclusiveLo);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(++inclusiveLo==0){
              break;
            }
          }
        case 0:
          for(long word=this.word2;;){
            if((word&(1L<<inclusiveLo))!=0){
              dst[--numLeft]=(int)(inclusiveLo);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(++inclusiveLo==64){
              break;
            }
          }
        default:
          for(long word=this.word3;;++inclusiveLo){
            if((word&(1L<<inclusiveLo))!=0){
              dst[--numLeft]=(int)(inclusiveLo);
              if(numLeft==0){
                break goToEnd;
              }
            }
          }
      }
    }
    private void uncheckedAscendingToArray(int numLeft,long[] dst){
      long val=Byte.MAX_VALUE;
      for(long word=this.word3;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(long)(val);
          if(numLeft==0){
            return;
          }
        }
        if(--val==63){
          break;
        }
      }
      for(long word=this.word2;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(long)(val);
          if(numLeft==0){
            return;
          }
        }
        if(--val==-1){
          break;
        }
      }
      for(long word=this.word1;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(long)(val);
          if(numLeft==0){
            return;
          }
        }
        if(--val==-65){
          break;
        }
      }
      for(long word=this.word0;;--val){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(long)(val);
          if(numLeft==0){
            return;
          }
        }
      }
    }
    private void uncheckedDescendingToArray(int numLeft,long[] dst){
      long val=Byte.MIN_VALUE;
      for(long word=this.word0;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(long)(val);
          if(numLeft==0){
            return;
          }
        }
        if(++val==-64){
          break;
        }
      }
      for(long word=this.word1;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(long)(val);
          if(numLeft==0){
            return;
          }
        }
        if(++val==0){
          break;
        }
      }
      for(long word=this.word2;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(long)(val);
          if(numLeft==0){
            return;
          }
        }
        if(++val==64){
          break;
        }
      }
      for(long word=this.word3;;++val){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(long)(val);
          if(numLeft==0){
            return;
          }
        }
      }
    }
    private void uncheckedAscendingToArray(int exclusiveHi,int numLeft,long[] dst){
      goToEnd:switch((--exclusiveHi)>>6){
        case 1:
          for(long word=this.word3;;){
            if((word&(1L<<exclusiveHi))!=0){
              dst[--numLeft]=(long)(exclusiveHi);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(--exclusiveHi==63){
              break;
            }
          }
        case 0:
          for(long word=this.word2;;){
            if((word&(1L<<exclusiveHi))!=0){
              dst[--numLeft]=(long)(exclusiveHi);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(--exclusiveHi==-1){
              break;
            }
          }
        case -1:
          for(long word=this.word1;;){
            if((word&(1L<<exclusiveHi))!=0){
              dst[--numLeft]=(long)(exclusiveHi);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(--exclusiveHi==-65){
              break;
            }
          }
        default:
          for(long word=this.word0;;--exclusiveHi){
            if((word&(1L<<exclusiveHi))!=0){
              dst[--numLeft]=(long)(exclusiveHi);
              if(numLeft==0){
                break goToEnd;
              }
            }
          }
      }
    }
    private void uncheckedDescendingToArray(int inclusiveLo,int numLeft,long[] dst){
      goToEnd:switch(inclusiveLo>>6){
        case -2:
          for(long word=this.word0;;){
            if((word&(1L<<inclusiveLo))!=0){
              dst[--numLeft]=(long)(inclusiveLo);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(++inclusiveLo==-64){
              break;
            }
          }
        case -1:
          for(long word=this.word1;;){
            if((word&(1L<<inclusiveLo))!=0){
              dst[--numLeft]=(long)(inclusiveLo);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(++inclusiveLo==0){
              break;
            }
          }
        case 0:
          for(long word=this.word2;;){
            if((word&(1L<<inclusiveLo))!=0){
              dst[--numLeft]=(long)(inclusiveLo);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(++inclusiveLo==64){
              break;
            }
          }
        default:
          for(long word=this.word3;;++inclusiveLo){
            if((word&(1L<<inclusiveLo))!=0){
              dst[--numLeft]=(long)(inclusiveLo);
              if(numLeft==0){
                break goToEnd;
              }
            }
          }
      }
    }
    private void uncheckedAscendingToArray(int numLeft,float[] dst){
      int val=Byte.MAX_VALUE;
      for(long word=this.word3;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(float)(val);
          if(numLeft==0){
            return;
          }
        }
        if(--val==63){
          break;
        }
      }
      for(long word=this.word2;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(float)(val);
          if(numLeft==0){
            return;
          }
        }
        if(--val==-1){
          break;
        }
      }
      for(long word=this.word1;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(float)(val);
          if(numLeft==0){
            return;
          }
        }
        if(--val==-65){
          break;
        }
      }
      for(long word=this.word0;;--val){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(float)(val);
          if(numLeft==0){
            return;
          }
        }
      }
    }
    private void uncheckedDescendingToArray(int numLeft,float[] dst){
      int val=Byte.MIN_VALUE;
      for(long word=this.word0;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(float)(val);
          if(numLeft==0){
            return;
          }
        }
        if(++val==-64){
          break;
        }
      }
      for(long word=this.word1;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(float)(val);
          if(numLeft==0){
            return;
          }
        }
        if(++val==0){
          break;
        }
      }
      for(long word=this.word2;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(float)(val);
          if(numLeft==0){
            return;
          }
        }
        if(++val==64){
          break;
        }
      }
      for(long word=this.word3;;++val){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(float)(val);
          if(numLeft==0){
            return;
          }
        }
      }
    }
    private void uncheckedAscendingToArray(int exclusiveHi,int numLeft,float[] dst){
      goToEnd:switch((--exclusiveHi)>>6){
        case 1:
          for(long word=this.word3;;){
            if((word&(1L<<exclusiveHi))!=0){
              dst[--numLeft]=(float)(exclusiveHi);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(--exclusiveHi==63){
              break;
            }
          }
        case 0:
          for(long word=this.word2;;){
            if((word&(1L<<exclusiveHi))!=0){
              dst[--numLeft]=(float)(exclusiveHi);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(--exclusiveHi==-1){
              break;
            }
          }
        case -1:
          for(long word=this.word1;;){
            if((word&(1L<<exclusiveHi))!=0){
              dst[--numLeft]=(float)(exclusiveHi);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(--exclusiveHi==-65){
              break;
            }
          }
        default:
          for(long word=this.word0;;--exclusiveHi){
            if((word&(1L<<exclusiveHi))!=0){
              dst[--numLeft]=(float)(exclusiveHi);
              if(numLeft==0){
                break goToEnd;
              }
            }
          }
      }
    }
    private void uncheckedDescendingToArray(int inclusiveLo,int numLeft,float[] dst){
      goToEnd:switch(inclusiveLo>>6){
        case -2:
          for(long word=this.word0;;){
            if((word&(1L<<inclusiveLo))!=0){
              dst[--numLeft]=(float)(inclusiveLo);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(++inclusiveLo==-64){
              break;
            }
          }
        case -1:
          for(long word=this.word1;;){
            if((word&(1L<<inclusiveLo))!=0){
              dst[--numLeft]=(float)(inclusiveLo);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(++inclusiveLo==0){
              break;
            }
          }
        case 0:
          for(long word=this.word2;;){
            if((word&(1L<<inclusiveLo))!=0){
              dst[--numLeft]=(float)(inclusiveLo);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(++inclusiveLo==64){
              break;
            }
          }
        default:
          for(long word=this.word3;;++inclusiveLo){
            if((word&(1L<<inclusiveLo))!=0){
              dst[--numLeft]=(float)(inclusiveLo);
              if(numLeft==0){
                break goToEnd;
              }
            }
          }
      }
    }
    private void uncheckedAscendingToArray(int numLeft,double[] dst){
      int val=Byte.MAX_VALUE;
      for(long word=this.word3;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(double)(val);
          if(numLeft==0){
            return;
          }
        }
        if(--val==63){
          break;
        }
      }
      for(long word=this.word2;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(double)(val);
          if(numLeft==0){
            return;
          }
        }
        if(--val==-1){
          break;
        }
      }
      for(long word=this.word1;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(double)(val);
          if(numLeft==0){
            return;
          }
        }
        if(--val==-65){
          break;
        }
      }
      for(long word=this.word0;;--val){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(double)(val);
          if(numLeft==0){
            return;
          }
        }
      }
    }
    private void uncheckedDescendingToArray(int numLeft,double[] dst){
      int val=Byte.MIN_VALUE;
      for(long word=this.word0;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(double)(val);
          if(numLeft==0){
            return;
          }
        }
        if(++val==-64){
          break;
        }
      }
      for(long word=this.word1;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(double)(val);
          if(numLeft==0){
            return;
          }
        }
        if(++val==0){
          break;
        }
      }
      for(long word=this.word2;;){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(double)(val);
          if(numLeft==0){
            return;
          }
        }
        if(++val==64){
          break;
        }
      }
      for(long word=this.word3;;++val){
        if((word&(1L<<val))!=0){
          dst[--numLeft]=(double)(val);
          if(numLeft==0){
            return;
          }
        }
      }
    }
    private void uncheckedAscendingToArray(int exclusiveHi,int numLeft,double[] dst){
      goToEnd:switch((--exclusiveHi)>>6){
        case 1:
          for(long word=this.word3;;){
            if((word&(1L<<exclusiveHi))!=0){
              dst[--numLeft]=(double)(exclusiveHi);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(--exclusiveHi==63){
              break;
            }
          }
        case 0:
          for(long word=this.word2;;){
            if((word&(1L<<exclusiveHi))!=0){
              dst[--numLeft]=(double)(exclusiveHi);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(--exclusiveHi==-1){
              break;
            }
          }
        case -1:
          for(long word=this.word1;;){
            if((word&(1L<<exclusiveHi))!=0){
              dst[--numLeft]=(double)(exclusiveHi);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(--exclusiveHi==-65){
              break;
            }
          }
        default:
          for(long word=this.word0;;--exclusiveHi){
            if((word&(1L<<exclusiveHi))!=0){
              dst[--numLeft]=(double)(exclusiveHi);
              if(numLeft==0){
                break goToEnd;
              }
            }
          }
      }
    }
    private void uncheckedDescendingToArray(int inclusiveLo,int numLeft,double[] dst){
      goToEnd:switch(inclusiveLo>>6){
        case -2:
          for(long word=this.word0;;){
            if((word&(1L<<inclusiveLo))!=0){
              dst[--numLeft]=(double)(inclusiveLo);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(++inclusiveLo==-64){
              break;
            }
          }
        case -1:
          for(long word=this.word1;;){
            if((word&(1L<<inclusiveLo))!=0){
              dst[--numLeft]=(double)(inclusiveLo);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(++inclusiveLo==0){
              break;
            }
          }
        case 0:
          for(long word=this.word2;;){
            if((word&(1L<<inclusiveLo))!=0){
              dst[--numLeft]=(double)(inclusiveLo);
              if(numLeft==0){
                break goToEnd;
              }
            }
            if(++inclusiveLo==64){
              break;
            }
          }
        default:
          for(long word=this.word3;;++inclusiveLo){
            if((word&(1L<<inclusiveLo))!=0){
              dst[--numLeft]=(double)(inclusiveLo);
              if(numLeft==0){
                break goToEnd;
              }
            }
          }
      }
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
      int numLeft;
      if((numLeft=this.modCountAndSize&0x1ff)!=0){
        return ((ByteSetImpl)this).uncheckedHashCode(numLeft);
      }
      return 0;
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
      long word2=this.word2;
      for(int offset=0;;){
        long marker;
        if((word2&(marker=1L<<offset))!=0){
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
        if(++offset==64){
          long word3=this.word3;
          for(;;){
            if((word3&(marker=1L<<offset))!=0){
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
            if(++offset==128){
              long word1=this.word1;
              for(offset=-1;;){
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
          this.word3=word3;
          break;
        }
      }
      this.word2=word2;
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
            if(!super.uncheckedremoveByte((byte)val)){
              break;
            }
          }else if(val instanceof Integer || val instanceof Short){
            if(!super.uncheckedremoveInt(((Number)val).intValue())){
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
            if(!super.uncheckedremoveChar((char)val)){
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
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0 && super.uncheckedremoveByte(val)){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(char val){
      final int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0 && super.uncheckedremoveChar(val)){
        this.modCountAndSize=modCountAndSize+((1<<9)-1);
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(int val){
      final int modCountAndSize;
      if(((modCountAndSize=this.modCountAndSize)&0x1ff)!=0 && super.uncheckedremoveInt(val)){
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
        return super.lower(val);
      }
      return null;
    }
    @Override public Byte floor(byte val){
      if((modCountAndSize&0x1ff)!=0){
        return super.floor(val);
      }
      return null;
    }
    @Override public Byte higher(byte val){
      if((modCountAndSize&0xff)!=0){
        return super.higher(val);
      }
      return null;
    }
    @Override public Byte ceiling(byte val){
      if((modCountAndSize&0x1ff)!=0){
        return super.ceiling(val);
      }  
      return null;
    }
    @Override public byte lowerByte(byte val){
      if((modCountAndSize&0x1ff)!=0){
        return super.lowerByte(val);
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte byteFloor(byte val){
      if((modCountAndSize&0x1ff)!=0){
        return super.byteFloor(val);
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte higherByte(byte val){
      if((modCountAndSize&0xff)!=0){
        return super.higherByte(val);
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte byteCeiling(byte val){
      if((modCountAndSize&0x1ff)!=0){
        return super.byteCeiling(val);
      }  
      return Byte.MIN_VALUE;
    }
    @Override public short lowerShort(short val){
      if((modCountAndSize&0x1ff)!=0){
        return super.lowerShort(val);
      }
      return Short.MIN_VALUE;
    }
    @Override public short shortFloor(short val){
      if((modCountAndSize&0x1ff)!=0){
        return super.shortFloor(val);
      }
      return Short.MIN_VALUE;
    }
    @Override public short higherShort(short val){
      if((modCountAndSize&0xff)!=0){
        return super.higherShort(val);
      }
      return Short.MIN_VALUE;
    }
    @Override public short shortCeiling(short val){
      if((modCountAndSize&0x1ff)!=0){
        return super.shortCeiling(val);
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
        return super.lowerInt(val);
      }
      return Integer.MIN_VALUE;
    }
    @Override public int intFloor(int val){
      if((modCountAndSize&0x1ff)!=0){
        return super.intFloor(val);
      }
      return Integer.MIN_VALUE;
    }
    @Override public int higherInt(int val){
      if((modCountAndSize&0xff)!=0){
        return super.higherInt(val);
      }
      return Integer.MIN_VALUE;
    }
    @Override public int intCeiling(int val){
      if((modCountAndSize&0x1ff)!=0){
        return super.intCeiling(val);
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
        return super.lowerLong(val);
      }
      return Long.MIN_VALUE;
    }
    @Override public long longFloor(long val){
      if((modCountAndSize&0x1ff)!=0){
        return super.longFloor(val);
      }
      return Long.MIN_VALUE;
    }
    @Override public long higherLong(long val){
      if((modCountAndSize&0xff)!=0){
        return super.higherLong(val);
      }
      return Long.MIN_VALUE;
    }
    @Override public long longCeiling(long val){
      if((modCountAndSize&0x1ff)!=0){
        return super.longCeiling(val);
      }  
      return Long.MIN_VALUE;
    }
    @Override public float lowerFloat(float val){
      if((modCountAndSize&0x1ff)!=0){
        return super.lowerFloat(val);
      }
      return Float.NaN;
    }
    @Override public float floatFloor(float val){
      if((modCountAndSize&0x1ff)!=0){
        return super.floatFloor(val);
      }
      return Float.NaN;
    }
    @Override public float higherFloat(float val){
      if((modCountAndSize&0xff)!=0){
        return super.higherFloat(val);
      }
      return Float.NaN;
    }
    @Override public float floatCeiling(float val){
      if((modCountAndSize&0x1ff)!=0){
        return super.floatCeiling(val);
      }  
      return Float.NaN;
    }
    @Override public double lowerDouble(double val){
      if((modCountAndSize&0x1ff)!=0){
        return super.lowerDouble(val);
      }
      return Double.NaN;
    }
    @Override public double doubleFloor(double val){
      if((modCountAndSize&0x1ff)!=0){
        return super.doubleFloor(val);
      }
      return Double.NaN;
    }
    @Override public double higherDouble(double val){
      if((modCountAndSize&0xff)!=0){
        return super.higherDouble(val);
      }
      return Double.NaN;
    }
    @Override public double doubleCeiling(double val){
      if((modCountAndSize&0x1ff)!=0){
        return super.doubleCeiling(val);
      }  
      return Double.NaN;
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
          return ((ByteSetImpl)this).uncheckedAscendingToString(numLeft);
        }
        return "[]";
      }
      @Override public void forEach(ByteConsumer action){
        final int modCountAndSize;
        int numLeft;
        if((numLeft=(modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
          try{
            ((ByteSetImpl)this).uncheckedAscendingForEach(numLeft,action);
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
            ((ByteSetImpl)this).uncheckedAscendingForEach(numLeft,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCountAndSize,this.modCountAndSize);
          }
        }
      }
      @SuppressWarnings("unchecked")
      @Override public <T> T[] toArray(T[] dst){
        int numLeft;
        if((numLeft=this.modCountAndSize&0x1ff)!=0){
          ((ByteSetImpl)this).uncheckedAscendingToArray(numLeft,dst=OmniArray.uncheckedArrResize(numLeft,dst));
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
          ((ByteSetImpl)this).uncheckedAscendingToArray(numLeft,dst);
        }
        return dst;
      }
      @Override public Byte[] toArray(){
        int numLeft;
        if((numLeft=this.modCountAndSize&0x1ff)!=0){
          final Byte[] dst;
          ((ByteSetImpl)this).uncheckedAscendingToArray(numLeft,dst=new Byte[numLeft]);
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_BOXED_ARR;
      }
      @Override public byte[] toByteArray(){
        int numLeft;
        if((numLeft=this.modCountAndSize&0x1ff)!=0){
          final byte[] dst;
          ((ByteSetImpl)this).uncheckedAscendingToArray(numLeft,dst=new byte[numLeft]);
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_ARR;
      }
      @Override public short[] toShortArray(){
        int numLeft;
        if((numLeft=this.modCountAndSize&0x1ff)!=0){
          final short[] dst;
          ((ByteSetImpl)this).uncheckedAscendingToArray(numLeft,dst=new short[numLeft]);
          return dst;
        }
        return OmniArray.OfShort.DEFAULT_ARR;
      }
      @Override public int[] toIntArray(){
        int numLeft;
        if((numLeft=this.modCountAndSize&0x1ff)!=0){
          final int[] dst;
          ((ByteSetImpl)this).uncheckedAscendingToArray(numLeft,dst=new int[numLeft]);
          return dst;
        }
        return OmniArray.OfInt.DEFAULT_ARR;
      }
      @Override public long[] toLongArray(){
        int numLeft;
        if((numLeft=this.modCountAndSize&0x1ff)!=0){
          final long[] dst;
          ((ByteSetImpl)this).uncheckedAscendingToArray(numLeft,dst=new long[numLeft]);
          return dst;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
      }
      @Override public float[] toFloatArray(){
        int numLeft;
        if((numLeft=this.modCountAndSize&0x1ff)!=0){
          final float[] dst;
          ((ByteSetImpl)this).uncheckedAscendingToArray(numLeft,dst=new float[numLeft]);
          return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
      }
      @Override public double[] toDoubleArray(){
        int numLeft;
        if((numLeft=this.modCountAndSize&0x1ff)!=0){
          final double[] dst;
          ((ByteSetImpl)this).uncheckedAscendingToArray(numLeft,dst=new double[numLeft]);
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
      @Override public String toString(){
        int numLeft;
        if((numLeft=this.modCountAndSize&0x1ff)!=0){
          return ((ByteSetImpl)this).uncheckedDescendingToString(numLeft);
        }
        return "[]";
      }
      @Override public void forEach(ByteConsumer action){
        final int modCountAndSize;
        int numLeft;
        if((numLeft=(modCountAndSize=this.modCountAndSize)&0x1ff)!=0){
          try{
            ((ByteSetImpl)this).uncheckedDescendingForEach(numLeft,action);
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
            ((ByteSetImpl)this).uncheckedDescendingForEach(numLeft,action::accept);
          }finally{
            CheckedCollection.checkModCount(modCountAndSize,this.modCountAndSize);
          }
        }
      }
      @SuppressWarnings("unchecked")
      @Override public <T> T[] toArray(T[] dst){
        int numLeft;
        if((numLeft=this.modCountAndSize&0x1ff)!=0){
          ((ByteSetImpl)this).uncheckedDescendingToArray(numLeft,dst=OmniArray.uncheckedArrResize(numLeft,dst));
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
          ((ByteSetImpl)this).uncheckedDescendingToArray(numLeft,dst);
        }
        return dst;
      }
      @Override public Byte[] toArray(){
        int numLeft;
        if((numLeft=this.modCountAndSize&0x1ff)!=0){
          final Byte[] dst;
          ((ByteSetImpl)this).uncheckedDescendingToArray(numLeft,dst=new Byte[numLeft]);
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_BOXED_ARR;
      }
      @Override public byte[] toByteArray(){
        int numLeft;
        if((numLeft=this.modCountAndSize&0x1ff)!=0){
          final byte[] dst;
          ((ByteSetImpl)this).uncheckedDescendingToArray(numLeft,dst=new byte[numLeft]);
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_ARR;
      }
      @Override public short[] toShortArray(){
        int numLeft;
        if((numLeft=this.modCountAndSize&0x1ff)!=0){
          final short[] dst;
          ((ByteSetImpl)this).uncheckedDescendingToArray(numLeft,dst=new short[numLeft]);
          return dst;
        }
        return OmniArray.OfShort.DEFAULT_ARR;
      }
      @Override public int[] toIntArray(){
        int numLeft;
        if((numLeft=this.modCountAndSize&0x1ff)!=0){
          final int[] dst;
          ((ByteSetImpl)this).uncheckedDescendingToArray(numLeft,dst=new int[numLeft]);
          return dst;
        }
        return OmniArray.OfInt.DEFAULT_ARR;
      }
      @Override public long[] toLongArray(){
        int numLeft;
        if((numLeft=this.modCountAndSize&0x1ff)!=0){
          final long[] dst;
          ((ByteSetImpl)this).uncheckedDescendingToArray(numLeft,dst=new long[numLeft]);
          return dst;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
      }
      @Override public float[] toFloatArray(){
        int numLeft;
        if((numLeft=this.modCountAndSize&0x1ff)!=0){
          final float[] dst;
          ((ByteSetImpl)this).uncheckedDescendingToArray(numLeft,dst=new float[numLeft]);
          return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
      }
      @Override public double[] toDoubleArray(){
        int numLeft;
        if((numLeft=this.modCountAndSize&0x1ff)!=0){
          final double[] dst;
          ((ByteSetImpl)this).uncheckedDescendingToArray(numLeft,dst=new double[numLeft]);
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
                      for(;;++offset){
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
                      for(;;++offset){
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
                      for(;;++offset){
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
                      for(;;++offset){
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
                      for(;;++offset){
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
                      for(;;++offset){
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
                      for(;;++offset){
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
                      for(;;++offset){
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
                      for(;;++offset){
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
  /*
  //TODO consider using these two methods
  private int getBodySetLo(int inclusiveLo,int exclusiveHi){
    foundElementAtEnd:for(;;){
      int tail0s;
      foundElementInMiddle:for(;;){
        switch((exclusiveHi-1)>>6){
          case 1:
            switch(inclusiveLo>>6){
              case -2:
                if((tail0s=Long.numberOfTrailingZeros(this.word0>>>inclusiveLo))!=64){
                  break foundElementInMiddle;
                }
                inclusiveLo=-64;
              case -1:
                if((tail0s=Long.numberOfTrailingZeros(this.word1>>>inclusiveLo))!=64){
                  break foundElementInMiddle;
                }
                inclusiveLo=0;
              case 0:
                if((tail0s=Long.numberOfTrailingZeros(this.word2>>>inclusiveLo))!=64){
                  break foundElementInMiddle;
                }
                inclusiveLo=64;
              case 1:
                if((inclusiveLo+=Long.numberOfTrailingZeros(this.word3>>>inclusiveLo))<exclusiveHi){
                  break foundElementAtEnd;
                }
              default:
            }
            break;
          case 0:
            switch(inclusiveLo>>6){
              case -2:
                if((tail0s=Long.numberOfTrailingZeros(this.word0>>>inclusiveLo))!=64){
                  break foundElementInMiddle;
                }
                inclusiveLo=-64;
              case -1:
                if((tail0s=Long.numberOfTrailingZeros(this.word1>>>inclusiveLo))!=64){
                  break foundElementInMiddle;
                }
                inclusiveLo=0;
              case 0:
                if((inclusiveLo+=Long.numberOfTrailingZeros(this.word2>>>inclusiveLo))<exclusiveHi){
                  break foundElementAtEnd;
                }
              default:
            }
            break;
          case -1:
            switch(inclusiveLo>>6){
              case -2:
                if((tail0s=Long.numberOfTrailingZeros(this.word0>>>inclusiveLo))!=64){
                  break foundElementInMiddle;
                }
                inclusiveLo=-64;
              case -1:
                if((inclusiveLo+=Long.numberOfTrailingZeros(this.word1>>>inclusiveLo))<exclusiveHi){
                  break foundElementAtEnd;
                }
              default:
            }
            break;
          default:
            if(inclusiveLo>>6==-2 && (inclusiveLo+=Long.numberOfTrailingZeros(this.word0>>>inclusiveLo))<exclusiveHi){
              break foundElementAtEnd;
            }
        }
        return 128;
      }
      return inclusiveLo+tail0s;
    }
    return inclusiveLo;
  }
  private int getBodySetHi(int inclusiveLo,int inclusiveHi){
    foundElementAtEnd:for(;;){
      int lead0s;
      foundElementInMiddle:for(;;){
        switch(inclusiveLo>>6){
          case -2:
            switch(inclusiveHi>>6){
              case 1:
                if((lead0s=Long.numberOfLeadingZeros(this.word3<<(-inclusiveHi-1)))!=64){
                  break foundElementInMiddle;
                }
                inclusiveHi=63;
              case 0:
                if((lead0s=Long.numberOfLeadingZeros(this.word2<<(-inclusiveHi-1)))!=64){
                  break foundElementInMiddle;
                }
                inclusiveHi=-1;
              case -1:
                if((lead0s=Long.numberOfLeadingZeros(this.word1<<(-inclusiveHi-1)))!=64){
                  break foundElementInMiddle;
                }
                inclusiveHi=-65;
              case -2:
                if((inclusiveHi-=Long.numberOfLeadingZeros(this.word0<<(-inclusiveHi-1)))>=inclusiveLo){
                  break foundElementAtEnd;
                }
              default:
            }
            break;
          case -1:
            switch(inclusiveHi>>6){
              case 1:
                if((lead0s=Long.numberOfLeadingZeros(this.word3<<(-inclusiveHi-1)))!=64){
                  break foundElementInMiddle;
                }
                inclusiveHi=63;
              case 0:
                if((lead0s=Long.numberOfLeadingZeros(this.word2<<(-inclusiveHi-1)))!=64){
                  break foundElementInMiddle;
                }
                inclusiveHi=-1;
              case -1:
                if((inclusiveHi-=Long.numberOfLeadingZeros(this.word1<<(-inclusiveHi-1)))>=inclusiveLo){
                  break foundElementAtEnd;
                }
              default:
            }
            break;
          case 0:
            switch(inclusiveHi>>6){
              case 1:
                if((lead0s=Long.numberOfLeadingZeros(this.word3<<(-inclusiveHi-1)))!=64){
                  break foundElementInMiddle;
                }
                inclusiveHi=63;
              case 0:
                if((inclusiveHi-=Long.numberOfLeadingZeros(this.word2<<(-inclusiveHi-1)))>=inclusiveLo){
                  break foundElementAtEnd;
                }
              default:
            }
            break;
          default:
            if(inclusiveHi>>6==1 && (inclusiveHi-=Long.numberOfLeadingZeros(this.word3<<(-inclusiveHi-1)))>=inclusiveLo){
               break foundElementAtEnd;
            }
        }
        return -129;
      }
      return inclusiveHi-lead0s;
    }
    return inclusiveHi;
  }
  */
  private int getThisOrLowerInRange(int inclusiveHi){
    int lead0s;
    foundElement:for(;;){
      switch(inclusiveHi>>6){
        case 1:// [64,127]
          if((lead0s=Long.numberOfLeadingZeros(this.word3<<(-inclusiveHi-1)))!=64){
            break foundElement;
          }
          inclusiveHi=63;
        case 0:// [0,63]
          if((lead0s=Long.numberOfLeadingZeros(this.word2<<(-inclusiveHi-1)))!=64){
            break foundElement;
          }
          inclusiveHi=-1;
        case -1:// [-64,-1]
          if((lead0s=Long.numberOfLeadingZeros(this.word1<<(-inclusiveHi-1)))!=64){
            break foundElement;
          }
          inclusiveHi=-65;
        default:// [-128,-65]
          if((lead0s=Long.numberOfLeadingZeros(this.word0<<(-inclusiveHi-1)))!=64){
            break foundElement;
          }
      }
      return -129;
    }
    return inclusiveHi-lead0s;
  }
  private int removeThisOrLowerInRange(int inclusiveHi){
    foundElement:for(;;){
      switch(inclusiveHi>>6){
        case 1:// [64,127]
          int lead0s;
          long word;
          if((lead0s=Long.numberOfLeadingZeros((word=this.word3)<<(-inclusiveHi-1)))!=64){
            this.word3=word&(~(1L<<(inclusiveHi-=lead0s)));
            break foundElement;
          }
          inclusiveHi=63;
        case 0:// [0,63]
          if((lead0s=Long.numberOfLeadingZeros((word=this.word2)<<(-inclusiveHi-1)))!=64){
            this.word2=word&(~(1L<<(inclusiveHi-=lead0s)));
            break foundElement;
          }
          inclusiveHi=-1;
        case -1:// [-64,-1]
          if((lead0s=Long.numberOfLeadingZeros((word=this.word1)<<(-inclusiveHi-1)))!=64){
            this.word1=word&(~(1L<<(inclusiveHi-=lead0s)));
            break foundElement;
          }
          inclusiveHi=-65;
        default:// [-128,-65]
          if((lead0s=Long.numberOfLeadingZeros((word=this.word0)<<(-inclusiveHi-1)))!=64){
            this.word0=word&(~(1L<<(inclusiveHi-=lead0s)));
            break foundElement;
          }
      }
      return Integer.MIN_VALUE;
    }
    return inclusiveHi;
  }
  private int getThisOrLower(int inclusiveHi){
    int lead0s;
    foundElement:for(;;){
      switch(inclusiveHi>>6){
        case 1:// [64,127]
          if((lead0s=Long.numberOfLeadingZeros(this.word3<<(-inclusiveHi-1)))!=64){
            break foundElement;
          }
          inclusiveHi=63;
        case 0:// [0,63]
          if((lead0s=Long.numberOfLeadingZeros(this.word2<<(-inclusiveHi-1)))!=64){
            break foundElement;
          }
          inclusiveHi=-1;
        case -1:// [-64,-1]
          if((lead0s=Long.numberOfLeadingZeros(this.word1<<(-inclusiveHi-1)))!=64){
            break foundElement;
          }
          inclusiveHi=-65;
        case -2:// [-128,-65]
          if((lead0s=Long.numberOfLeadingZeros(this.word0<<(-inclusiveHi-1)))!=64){
            break foundElement;
          }
        default:// < 128
          return -129;
      }
    }
    return inclusiveHi-lead0s;
  }
  private int getThisOrHigher(int inclusiveLo){
    int tail0s;
    foundElement:for(;;){
      switch(inclusiveLo>>6){
        case -2:// [-128,-65]
          if((tail0s=Long.numberOfTrailingZeros(this.word0>>>inclusiveLo))!=64){
            break foundElement;
          }
          inclusiveLo=-64;
        case -1:// [-64,-1]
          if((tail0s=Long.numberOfTrailingZeros(this.word1>>>inclusiveLo))!=64){
            break foundElement;
          }
          inclusiveLo=0;
        case 0:// [0,63]
          if((tail0s=Long.numberOfTrailingZeros(this.word2>>>inclusiveLo))!=64){
            break foundElement;
          }
          inclusiveLo=64;
        case 1:// [64,127]
          if((tail0s=Long.numberOfTrailingZeros(this.word3>>>inclusiveLo))!=64){
            break foundElement;
          }
        default:// > 127
          return 128;
      }
    }
    return inclusiveLo+tail0s;
  }
  private int getThisOrHigherInRange(int inclusiveLo){
    int tail0s;
    foundElement:for(;;){
      switch(inclusiveLo>>6){
        case -2:// [-128,-65]
          if((tail0s=Long.numberOfTrailingZeros(this.word0>>>inclusiveLo))!=64){
            break foundElement;
          }
          inclusiveLo=-64;
        case -1:// [-64,-1]
          if((tail0s=Long.numberOfTrailingZeros(this.word1>>>inclusiveLo))!=64){
            break foundElement;
          }
          inclusiveLo=0;
        case 0:// [0,63]
          if((tail0s=Long.numberOfTrailingZeros(this.word2>>>inclusiveLo))!=64){
            break foundElement;
          }
          inclusiveLo=64;
        default:// [64,127]
          if((tail0s=Long.numberOfTrailingZeros(this.word3>>>inclusiveLo))!=64){
            break foundElement;
          }
      }
      return 128;
    }
    return inclusiveLo+tail0s;
  }
  private int removeThisOrHigherInRange(int inclusiveLo){
    foundElement:for(;;){
      switch(inclusiveLo>>6){
        case -2:// [-128,-65]
          int tail0s;
          long word;
          if((tail0s=Long.numberOfTrailingZeros((word=this.word0)>>>inclusiveLo))!=64){
            this.word0=word&(~(1L<<(inclusiveLo+=tail0s)));
            break foundElement;
          }
          inclusiveLo=-64;
        case -1:// [-64,-1]
          if((tail0s=Long.numberOfTrailingZeros((word=this.word1)>>>inclusiveLo))!=64){
            this.word1=word&(~(1L<<(inclusiveLo+=tail0s)));
            break foundElement;
          }
          inclusiveLo=0;
        case 0:// [0,63]
          if((tail0s=Long.numberOfTrailingZeros((word=this.word2)>>>inclusiveLo))!=64){
            this.word2=word&(~(1L<<(inclusiveLo+=tail0s)));
            break foundElement;
          }
          inclusiveLo=64;
        default:// [64,127]
          if((tail0s=Long.numberOfTrailingZeros((word=this.word3)>>>inclusiveLo))!=64){
            this.word3=word&(~(1L<<(inclusiveLo+=tail0s)));
            break foundElement;
          }
      }
      return Integer.MIN_VALUE;
    }
    return inclusiveLo;
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
      this.currIndex=((ByteSetImpl)root).getThisOrHigher((ret=this.currIndex)+1);
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
      this.currIndex=((ByteSetImpl)root).getThisOrLower((ret=this.currIndex)-1);
      return (byte)ret;
    }
  }
  private static class CheckedAscendingItr extends AbstractByteItr{
    transient final ByteSetImpl.Checked root;
    transient int currIndexAndNumLeft;
    transient int modCountAndLastRet;
    private CheckedAscendingItr(ByteSetImpl.Checked root){
      this.root=root;
      int modCountAndSize;
      this.modCountAndLastRet=(modCountAndSize=root.modCountAndSize)|0x1ff;
      int currIndex;
      if((currIndex=Long.numberOfTrailingZeros(root.word0))==64){
        if((currIndex+=Long.numberOfTrailingZeros(root.word1))==128){
          if((currIndex+=Long.numberOfTrailingZeros(root.word2))==192){
            currIndex+=Long.numberOfTrailingZeros(root.word3);
          }
        }
      }
      this.currIndexAndNumLeft=(currIndex<<9)|(modCountAndSize&(0x1ff));
    }
    private CheckedAscendingItr(CheckedAscendingItr that){
      this.root=that.root;
      this.currIndexAndNumLeft=that.currIndexAndNumLeft;
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
    private void uncheckedForEachRemaining(int numLeft,int currIndex,ByteConsumer action){
      int modCountAndLastRet=this.modCountAndLastRet;
      final var root=this.root;
      int lastRet;
      try{
        action.accept((byte)(lastRet=currIndex));
        if(--numLeft!=0){
          goToEnd:switch((++currIndex)>>6){
            case -2:
              for(long word=root.word0;;){
                if((word&(1L<<currIndex))!=0){
                  action.accept((byte)(lastRet=currIndex));
                  if(--numLeft==0){
                    break goToEnd;
                  }
                }
                if(++currIndex==-64){
                  break;
                }
              }
            case -1:
              for(long word=root.word1;;){
                if((word&(1L<<currIndex))!=0){
                  action.accept((byte)(lastRet=currIndex));
                  if(--numLeft==0){
                    break goToEnd;
                  }
                }
                if(++currIndex==0){
                  break;
                }
              }
            case 0:
              for(long word=root.word2;;){
                if((word&(1L<<currIndex))!=0){
                  action.accept((byte)(lastRet=currIndex));
                  if(--numLeft==0){
                    break goToEnd;
                  }
                }
                if(++currIndex==64){
                  break;
                }
              }
            case 1:
              for(long word=root.word3;;++currIndex){
                if((word&(1L<<currIndex))!=0){
                  action.accept((byte)(lastRet=currIndex));
                  if(--numLeft==0){
                    break goToEnd;
                  }
                }
              }
            default:
          }
        }
      }finally{
        CheckedCollection.checkModCount(modCountAndLastRet&=(~0x1ff),root.modCountAndSize&(~0x1ff));
      }
      this.modCountAndLastRet=modCountAndLastRet|(0xff&lastRet);
      this.currIndexAndNumLeft=1<<16;
    }
    @Override public void forEachRemaining(ByteConsumer action){
      final int currIndexAndNumLeft;
      if((currIndexAndNumLeft=this.currIndexAndNumLeft)!=1<<16){
        uncheckedForEachRemaining(currIndexAndNumLeft&0x1ff,currIndexAndNumLeft>>9,action);
      }
    }
    @Override public void forEachRemaining(Consumer<? super Byte> action){
      final int currIndexAndNumLeft;
      if((currIndexAndNumLeft=this.currIndexAndNumLeft)!=1<<16){
        uncheckedForEachRemaining(currIndexAndNumLeft&0x1ff,currIndexAndNumLeft>>9,action::accept);
      }
    }
    @Override public boolean hasNext(){
      return this.currIndexAndNumLeft!=1<<16;
    }
    @Override public byte nextByte(){
      final ByteSetImpl.Checked root;
      final int modCountAndLastRet;
      CheckedCollection.checkModCount((modCountAndLastRet=this.modCountAndLastRet)&(~0x1ff),(root=this.root).modCountAndSize&(~0x1ff));
      int currIndexAndNumLeft;
      int numLeft;
      if((numLeft=(currIndexAndNumLeft=this.currIndexAndNumLeft)&0x1ff)!=0){
        final int ret;
        this.currIndexAndNumLeft=(((ByteSetImpl)root).getThisOrHigher((ret=currIndexAndNumLeft>>9)+1)<<9)|(numLeft-1);
        this.modCountAndLastRet=(modCountAndLastRet&(~0x1ff))+(ret&0xff);
        return (byte)ret;
      }
      throw new NoSuchElementException();
    }
  }
  private static class CheckedDescendingItr extends AbstractByteItr{
    transient final ByteSetImpl.Checked root;
    transient int currIndexAndNumLeft;
    transient int modCountAndLastRet;
    private CheckedDescendingItr(ByteSetImpl.Checked root){
      this.root=root;
      int modCountAndSize;
      this.modCountAndLastRet=(modCountAndSize=root.modCountAndSize)|0x1ff;
      int currIndex;
      if((currIndex=Long.numberOfLeadingZeros(root.word3))==64){
        if((currIndex+=Long.numberOfLeadingZeros(root.word2))==128){
          if((currIndex+=Long.numberOfLeadingZeros(root.word1))==192){
            currIndex+=Long.numberOfLeadingZeros(root.word0);
          }
        }
      }
      this.currIndexAndNumLeft=((Byte.MAX_VALUE-currIndex)<<9)|(modCountAndSize&(0x1ff));
    }
    private CheckedDescendingItr(CheckedDescendingItr that){
      this.root=that.root;
      this.currIndexAndNumLeft=that.currIndexAndNumLeft;
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
    private void uncheckedForEachRemaining(int numLeft,int currIndex,ByteConsumer action){
      int modCountAndLastRet=this.modCountAndLastRet;
      final var root=this.root;
      int lastRet;
      try{
        action.accept((byte)(lastRet=currIndex));
        if(--numLeft!=0){
          goToEnd:switch((--currIndex)>>6){
            case 1:
              for(long word=root.word3;;){
                if((word&(1L<<currIndex))!=0){
                  action.accept((byte)(lastRet=currIndex));
                  if(--numLeft==0){
                    break goToEnd;
                  }
                }
                if(--currIndex==63){
                  break;
                }
              }
            case 0:
              for(long word=root.word2;;){
                if((word&(1L<<currIndex))!=0){
                  action.accept((byte)(lastRet=currIndex));
                  if(--numLeft==0){
                    break goToEnd;
                  }
                }
                if(--currIndex==-1){
                  break;
                }
              }
            case -1:
              for(long word=root.word1;;){
                if((word&(1L<<currIndex))!=0){
                  action.accept((byte)(lastRet=currIndex));
                  if(--numLeft==0){
                    break goToEnd;
                  }
                }
                if(--currIndex==-65){
                  break;
                }
              }
            case -2:
              for(long word=root.word0;;--currIndex){
                if((word&(1L<<currIndex))!=0){
                  action.accept((byte)(lastRet=currIndex));
                  if(--numLeft==0){
                    break goToEnd;
                  }
                }
              }
            default:
          }
        }
      }finally{
        CheckedCollection.checkModCount(modCountAndLastRet&=(~0x1ff),root.modCountAndSize&(~0x1ff));
      }
      this.modCountAndLastRet=modCountAndLastRet|(0xff&lastRet);
      this.currIndexAndNumLeft=-129<<9;
    }
    @Override public void forEachRemaining(ByteConsumer action){
      final int currIndexAndNumLeft;
      if((currIndexAndNumLeft=this.currIndexAndNumLeft)!=(-129)<<9){
        uncheckedForEachRemaining(currIndexAndNumLeft&0x1ff,currIndexAndNumLeft>>9,action);
      }
    }
    @Override public void forEachRemaining(Consumer<? super Byte> action){
      final int currIndexAndNumLeft;
      if((currIndexAndNumLeft=this.currIndexAndNumLeft)!=(-129<<9)){
        uncheckedForEachRemaining(currIndexAndNumLeft&0x1ff,currIndexAndNumLeft>>9,action::accept);
      }
    }
    @Override public boolean hasNext(){
      return this.currIndexAndNumLeft!=(-129<<9);
    }
    @Override public byte nextByte(){
      final ByteSetImpl.Checked root;
      final int modCountAndLastRet;
      CheckedCollection.checkModCount((modCountAndLastRet=this.modCountAndLastRet)&(~0x1ff),(root=this.root).modCountAndSize&(~0x1ff));
      int currIndexAndNumLeft;
      int numLeft;
      if((numLeft=(currIndexAndNumLeft=this.currIndexAndNumLeft)&0x1ff)!=0){
        final int ret;
        this.currIndexAndNumLeft=(((ByteSetImpl)root).getThisOrLower((ret=currIndexAndNumLeft>>9)-1)<<9)|(numLeft-1);
        this.modCountAndLastRet=(modCountAndLastRet&(~0x1ff))+(ret&0xff);
        return (byte)ret;
      }
      throw new NoSuchElementException();
    }
  }
  private static abstract class AbstractCheckedFullView extends AbstractByteSet.ComparatorlessImpl{
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
    @Override public OmniNavigableSet.OfByte descendingSet(){
      return root;
    }
    @Override public int firstInt(){
      return root.lastInt();
    }
    @Override public int lastInt(){
      return root.firstInt();
    }
    private static class Ascending extends AbstractCheckedFullView implements Cloneable,Serializable{
      private static final long serialVersionUID=1L;
      private Ascending(ByteSetImpl.Checked.Descending root){
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
          return ((ByteSetImpl)root).uncheckedAscendingToString(numLeft);
        }
        return "[]";
      }
      @Override public void forEach(ByteConsumer action){
        final int modCountAndSize;
        int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(modCountAndSize=(root=this.root).modCountAndSize)&0x1ff)!=0){
          try{
            ((ByteSetImpl)root).uncheckedAscendingForEach(numLeft,action);
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
            ((ByteSetImpl)root).uncheckedAscendingForEach(numLeft,action::accept);
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
          ((ByteSetImpl)root).uncheckedAscendingToArray(numLeft,dst=OmniArray.uncheckedArrResize(numLeft,dst));
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
          ((ByteSetImpl)root).uncheckedAscendingToArray(numLeft,dst);
        }
        return dst;
      }
      @Override public Byte[] toArray(){
        int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(root=this.root).modCountAndSize&0x1ff)!=0){   
          final Byte[] dst;
          ((ByteSetImpl)root).uncheckedAscendingToArray(numLeft,dst=new Byte[numLeft]);
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_BOXED_ARR;
      }
      @Override public byte[] toByteArray(){
        int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(root=this.root).modCountAndSize&0x1ff)!=0){   
          final byte[] dst;
          ((ByteSetImpl)root).uncheckedAscendingToArray(numLeft,dst=new byte[numLeft]);
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_ARR;
      }
      @Override public short[] toShortArray(){
        int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(root=this.root).modCountAndSize&0x1ff)!=0){   
          final short[] dst;
          ((ByteSetImpl)root).uncheckedAscendingToArray(numLeft,dst=new short[numLeft]);
          return dst;
        }
        return OmniArray.OfShort.DEFAULT_ARR;
      }
      @Override public int[] toIntArray(){
        int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(root=this.root).modCountAndSize&0x1ff)!=0){   
          final int[] dst;
          ((ByteSetImpl)root).uncheckedAscendingToArray(numLeft,dst=new int[numLeft]);
          return dst;
        }
        return OmniArray.OfInt.DEFAULT_ARR;
      }
      @Override public long[] toLongArray(){
        int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(root=this.root).modCountAndSize&0x1ff)!=0){   
          final long[] dst;
          ((ByteSetImpl)root).uncheckedAscendingToArray(numLeft,dst=new long[numLeft]);
          return dst;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
      }
      @Override public float[] toFloatArray(){
        int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(root=this.root).modCountAndSize&0x1ff)!=0){   
          final float[] dst;
          ((ByteSetImpl)root).uncheckedAscendingToArray(numLeft,dst=new float[numLeft]);
          return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
      }
      @Override public double[] toDoubleArray(){
        int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(root=this.root).modCountAndSize&0x1ff)!=0){   
          final double[] dst;
          ((ByteSetImpl)root).uncheckedAscendingToArray(numLeft,dst=new double[numLeft]);
          return dst;
        }
        return OmniArray.OfDouble.DEFAULT_ARR;
      }
    }
    private static class Descending extends AbstractCheckedFullView implements Cloneable,Serializable{
      private static final long serialVersionUID=1L;
      private Descending(ByteSetImpl.Checked.Ascending root){
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
          return ((ByteSetImpl)root).uncheckedDescendingToString(numLeft);
        }
        return "[]";
      }
      @Override public void forEach(ByteConsumer action){
        final int modCountAndSize;
        int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(modCountAndSize=(root=this.root).modCountAndSize)&0x1ff)!=0){
          try{
            ((ByteSetImpl)root).uncheckedDescendingForEach(numLeft,action);
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
            ((ByteSetImpl)root).uncheckedDescendingForEach(numLeft,action::accept);
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
          ((ByteSetImpl)root).uncheckedDescendingToArray(numLeft,dst=OmniArray.uncheckedArrResize(numLeft,dst));
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
          ((ByteSetImpl)root).uncheckedDescendingToArray(numLeft,dst);
        }
        return dst;
      }
      @Override public Byte[] toArray(){
        int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(root=this.root).modCountAndSize&0x1ff)!=0){   
          final Byte[] dst;
          ((ByteSetImpl)root).uncheckedDescendingToArray(numLeft,dst=new Byte[numLeft]);
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_BOXED_ARR;
      }
      @Override public byte[] toByteArray(){
        int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(root=this.root).modCountAndSize&0x1ff)!=0){   
          final byte[] dst;
          ((ByteSetImpl)root).uncheckedDescendingToArray(numLeft,dst=new byte[numLeft]);
          return dst;
        }
        return OmniArray.OfByte.DEFAULT_ARR;
      }
      @Override public short[] toShortArray(){
        int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(root=this.root).modCountAndSize&0x1ff)!=0){   
          final short[] dst;
          ((ByteSetImpl)root).uncheckedDescendingToArray(numLeft,dst=new short[numLeft]);
          return dst;
        }
        return OmniArray.OfShort.DEFAULT_ARR;
      }
      @Override public int[] toIntArray(){
        int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(root=this.root).modCountAndSize&0x1ff)!=0){   
          final int[] dst;
          ((ByteSetImpl)root).uncheckedDescendingToArray(numLeft,dst=new int[numLeft]);
          return dst;
        }
        return OmniArray.OfInt.DEFAULT_ARR;
      }
      @Override public long[] toLongArray(){
        int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(root=this.root).modCountAndSize&0x1ff)!=0){   
          final long[] dst;
          ((ByteSetImpl)root).uncheckedDescendingToArray(numLeft,dst=new long[numLeft]);
          return dst;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
      }
      @Override public float[] toFloatArray(){
        int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(root=this.root).modCountAndSize&0x1ff)!=0){   
          final float[] dst;
          ((ByteSetImpl)root).uncheckedDescendingToArray(numLeft,dst=new float[numLeft]);
          return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
      }
      @Override public double[] toDoubleArray(){
        int numLeft;
        final ByteSetImpl.Checked root;
        if((numLeft=(root=this.root).modCountAndSize&0x1ff)!=0){   
          final double[] dst;
          ((ByteSetImpl)root).uncheckedDescendingToArray(numLeft,dst=new double[numLeft]);
          return dst;
        }
        return OmniArray.OfDouble.DEFAULT_ARR;
      }
    }
  }
  private static abstract class AbstractUncheckedFullView extends AbstractByteSet.ComparatorlessImpl{
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
    @Override public OmniNavigableSet.OfByte descendingSet(){
      return root;
    }
    @Override public int firstInt(){
      return root.lastInt();
    }
    @Override public int lastInt(){
      return root.firstInt();
    }
    private static class Ascending extends AbstractUncheckedFullView implements Cloneable,Serializable{
      private static final long serialVersionUID=1L;
      private Ascending(ByteSetImpl.Unchecked.Descending root){
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
      private Descending(ByteSetImpl.Unchecked.Ascending root){
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
                      for(;;++offset){
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
                      for(;;++offset){
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
                      for(;;++offset){
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
                      for(;;++offset){
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
                      for(;;++offset){
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
                      for(;;++offset){
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
                      for(;;++offset){
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
                      for(;;++offset){
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
                      for(;;++offset){
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
    goToEnd:switch((boundInfo&=0xff)>>6){
      case 2:
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
      case 3:
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
  private static abstract class UncheckedSubSet extends AbstractByteSet.ComparatorlessImpl{
    transient final ByteSetImpl.Unchecked root;
    transient final UncheckedSubSet parent;
    transient final int boundInfo;
    transient int size;
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
    abstract void forEachImpl(int numLeft,ByteConsumer action);
    abstract String toStringImpl(int numLeft);
    abstract void toArrayImpl(int numLeft,Byte[] dst);
    abstract void toArrayImpl(int numLeft,Object[] dst);
    abstract void toArrayImpl(int numLeft,byte[] dst);
    abstract void toArrayImpl(int numLeft,short[] dst);
    abstract void toArrayImpl(int numLeft,int[] dst);
    abstract void toArrayImpl(int numLeft,long[] dst);
    abstract void toArrayImpl(int numLeft,float[] dst);
    abstract void toArrayImpl(int numLeft,double[] dst);
    @Override public Byte[] toArray(){
      final int size;
      if((size=this.size)!=0){
        final Byte[] dst;
        toArrayImpl(size,dst=new Byte[size]);
        return dst;
      }
      return OmniArray.OfByte.DEFAULT_BOXED_ARR;
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      final int size;
      final T[] dst=arrConstructor.apply(size=this.size);
      if(size!=0){
        toArrayImpl(size,dst);
      }
      return dst;
    }
    @Override public <T> T[] toArray(T[] dst){
      final int size;
      if((size=this.size)!=0){
        toArrayImpl(size,dst=OmniArray.uncheckedArrResize(size,dst));
      }else if(dst.length!=0){
        dst[0]=null;
      }
      return dst;
    }
    @Override public byte[] toByteArray(){
      final int size;
      if((size=this.size)!=0){
        final byte[] dst;
        toArrayImpl(size,dst=new byte[size]);
        return dst;
      }
      return OmniArray.OfByte.DEFAULT_ARR;
    }
    @Override public short[] toShortArray(){
      final int size;
      if((size=this.size)!=0){
        final short[] dst;
        toArrayImpl(size,dst=new short[size]);
        return dst;
      }
      return OmniArray.OfShort.DEFAULT_ARR;
    }
    @Override public int[] toIntArray(){
      final int size;
      if((size=this.size)!=0){
        final int[] dst;
        toArrayImpl(size,dst=new int[size]);
        return dst;
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
    @Override public long[] toLongArray(){
      final int size;
      if((size=this.size)!=0){
        final long[] dst;
        toArrayImpl(size,dst=new long[size]);
        return dst;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override public float[] toFloatArray(){
      final int size;
      if((size=this.size)!=0){
        final float[] dst;
        toArrayImpl(size,dst=new float[size]);
        return dst;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override public double[] toDoubleArray(){
      final int size;
      if((size=this.size)!=0){
        final double[] dst;
        toArrayImpl(size,dst=new double[size]);
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override public String toString(){
      int size;
      if((size=this.size)!=0){
        return toStringImpl(size);
      }
      return "[]";
    }
    abstract int hashCodeImpl(int numLeft);
    @Override public int hashCode(){
      int size;
      if((size=this.size)!=0){
        return hashCodeImpl(size);
      }
      return 0;
    }
    @Override public void forEach(ByteConsumer action){
      final int numLeft;
      if((numLeft=this.size)!=0){
        forEachImpl(numLeft,action);
      }
    }
    @Override public void forEach(Consumer<? super Byte> action){
      final int numLeft;
      if((numLeft=this.size)!=0){
        forEachImpl(numLeft,action::accept);
      }
    }
    @Override public boolean removeVal(boolean val){
      final ByteSetImpl.Unchecked root;
      final int mask;
      final int size;
      long word;
      if((size=this.size)!=0 && isInRange(mask=val?0b10:0b01) && (word=(root=this.root).word2)!=(word&=(~mask))){
        root.word2=word;
        bubbleUpDecrementSize();
        this.size=size-1;
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(byte val){
      final int size;
      if((size=this.size)!=0 && isInRange(val) && ((ByteSetImpl)root).uncheckedremoveByte(val)){
        bubbleUpDecrementSize();
        this.size=size-1;
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(char val){
      final int size;
      if((size=this.size)!=0 && isInRange(val) && ((ByteSetImpl)root).uncheckedremoveChar(val)){
        bubbleUpDecrementSize();
        this.size=size-1;
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(int val){
      final int size;
      if((size=this.size)!=0 && isInRange(val) && ((ByteSetImpl)root).uncheckedremoveInt(val)){
        bubbleUpDecrementSize();
        this.size=size-1;
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(long val){
      final int size;
      final int v;
      if((size=this.size)!=0 && (v=(byte)val)==val && isInRange(v) && ((ByteSetImpl)root).uncheckedremoveByte(v)){
        bubbleUpDecrementSize();
        this.size=size-1;
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(float val){
      final int size;
      final int v;
      if((size=this.size)!=0 && (v=(byte)val)==val && isInRange(v) && ((ByteSetImpl)root).uncheckedremoveByte(v)){
        bubbleUpDecrementSize();
        this.size=size-1;
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(double val){
      final int size;
      final int v;
      if((size=this.size)!=0 && (v=(byte)val)==val && isInRange(v) && ((ByteSetImpl)root).uncheckedremoveByte(v)){
        bubbleUpDecrementSize();
        this.size=size-1;
        return true;
      }
      return false;
    }
    @Override public boolean remove(Object val){
      final int size;
      if((size=this.size)!=0){
        for(;;){
          final int v;
          if(val instanceof Byte){
            if(!isInRange(v=(byte)val) || !((ByteSetImpl)root).uncheckedremoveByte(v)){
              break;
            }
          }else if(val instanceof Integer || val instanceof Short){
            if(!isInRange(v=((Number)val).intValue()) || !((ByteSetImpl)root).uncheckedremoveInt(v)){
              break;
            }
          }else if(val instanceof Long){
            final long l;
            if((l=(long)val)!=(v=(byte)l) || !isInRange(v) || !((ByteSetImpl)root).uncheckedremoveByte(v)){
              break;
            }
          }else if(val instanceof Float){
            final float f;
            if((f=(float)val)!=(v=(byte)f) || !isInRange(v) || !((ByteSetImpl)root).uncheckedremoveByte(v)){
              break;
            }
          }else if(val instanceof Double){
            final double d;
            if((d=(double)val)!=(v=(byte)d) || !isInRange(v) || !((ByteSetImpl)root).uncheckedremoveByte(v)){
              break;
            }
          }else if(val instanceof Character){
            if(!isInRange(v=(char)val) || !((ByteSetImpl)root).uncheckedremoveChar(v)){
              break;
            }
          }else if(val instanceof Boolean){
            final ByteSetImpl root;
            long word;
            if(!isInRange(v=((boolean)val)?0b10:0b01) || (word=(root=this.root).word2)==(word&=(~v))){
              break;
            }
            root.word2=word;
          }else{
            break;
          }
          bubbleUpDecrementSize();
          this.size=size-1;
          return true;
        }
      }
      return false;
    }
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
    private void bubbleUpDecrementSize(){
      for(var parent=this.parent;parent!=null;--parent.size,parent=parent.parent){}
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
      @Override int hashCodeImpl(int numLeft){
        return ((ByteSetImpl)root).uncheckedHashCode(this.boundInfo,numLeft);
      }
      private static class Ascending extends HeadSet{
        private Ascending(ByteSetImpl.Unchecked root,int boundInfo,int size){
          super(root,boundInfo,size);
        }
        private Ascending(Ascending parent,int boundInfo,int size){
          super(parent,boundInfo,size);
        }
        @Override void forEachImpl(int numLeft,ByteConsumer action){
          ((ByteSetImpl)root).uncheckedAscendingForEach(numLeft,action);
        }
        @Override String toStringImpl(int numLeft){
          return ((ByteSetImpl)root).uncheckedAscendingToString(numLeft);
        }
        @Override void toArrayImpl(int numLeft,Byte[] dst){
          ((ByteSetImpl)root).uncheckedAscendingToArray(this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,Object[] dst){
          ((ByteSetImpl)root).uncheckedAscendingToArray(this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,byte[] dst){
          ((ByteSetImpl)root).uncheckedAscendingToArray(this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,short[] dst){
          ((ByteSetImpl)root).uncheckedAscendingToArray(this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,int[] dst){
          ((ByteSetImpl)root).uncheckedAscendingToArray(this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,long[] dst){
          ((ByteSetImpl)root).uncheckedAscendingToArray(this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,float[] dst){
          ((ByteSetImpl)root).uncheckedAscendingToArray(this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,double[] dst){
          ((ByteSetImpl)root).uncheckedAscendingToArray(this.boundInfo,numLeft,dst);
        }
      }
      private static class Descending extends HeadSet{
        private Descending(ByteSetImpl.Unchecked root,int boundInfo,int size){
          super(root,boundInfo,size);
        }
        private Descending(Descending parent,int boundInfo,int size){
          super(parent,boundInfo,size);
        }
        @Override void forEachImpl(int numLeft,ByteConsumer action){
          ((ByteSetImpl)root).uncheckedDescendingForEach(this.boundInfo,numLeft,action);
        }
        @Override String toStringImpl(int numLeft){
          return ((ByteSetImpl)root).uncheckedDescendingToString(this.boundInfo,numLeft);
        }
        @Override void toArrayImpl(int numLeft,Byte[] dst){
          ((ByteSetImpl)root).uncheckedDescendingToArray(numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,Object[] dst){
          ((ByteSetImpl)root).uncheckedDescendingToArray(numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,byte[] dst){
          ((ByteSetImpl)root).uncheckedDescendingToArray(numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,short[] dst){
          ((ByteSetImpl)root).uncheckedDescendingToArray(numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,int[] dst){
          ((ByteSetImpl)root).uncheckedDescendingToArray(numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,long[] dst){
          ((ByteSetImpl)root).uncheckedDescendingToArray(numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,float[] dst){
          ((ByteSetImpl)root).uncheckedDescendingToArray(numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,double[] dst){
          ((ByteSetImpl)root).uncheckedDescendingToArray(numLeft,dst);
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
      @Override int hashCodeImpl(int numLeft){
        return ((ByteSetImpl)root).uncheckedHashCode(numLeft);
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
        @Override void forEachImpl(int numLeft,ByteConsumer action){
          ((ByteSetImpl)root).uncheckedAscendingForEach(this.boundInfo,numLeft,action);
        }
        @Override String toStringImpl(int numLeft){
          return ((ByteSetImpl)root).uncheckedAscendingToString(this.boundInfo,numLeft);
        }
        @Override void toArrayImpl(int numLeft,Byte[] dst){
          ((ByteSetImpl)root).uncheckedAscendingToArray(numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,Object[] dst){
          ((ByteSetImpl)root).uncheckedAscendingToArray(numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,byte[] dst){
          ((ByteSetImpl)root).uncheckedAscendingToArray(numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,short[] dst){
          ((ByteSetImpl)root).uncheckedAscendingToArray(numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,int[] dst){
          ((ByteSetImpl)root).uncheckedAscendingToArray(numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,long[] dst){
          ((ByteSetImpl)root).uncheckedAscendingToArray(numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,float[] dst){
          ((ByteSetImpl)root).uncheckedAscendingToArray(numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,double[] dst){
          ((ByteSetImpl)root).uncheckedAscendingToArray(numLeft,dst);
        }
      }
      private static class Descending extends TailSet{
        private Descending(ByteSetImpl.Unchecked root,int boundInfo,int size){
          super(root,boundInfo,size);
        }
        private Descending(Descending parent,int boundInfo,int size){
          super(parent,boundInfo,size);
        }
        @Override void forEachImpl(int numLeft,ByteConsumer action){
          ((ByteSetImpl)root).uncheckedDescendingForEach(numLeft,action);
        }
        @Override String toStringImpl(int numLeft){
          return ((ByteSetImpl)root).uncheckedDescendingToString(numLeft);
        }
        @Override void toArrayImpl(int numLeft,Byte[] dst){
          ((ByteSetImpl)root).uncheckedDescendingToArray(this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,Object[] dst){
          ((ByteSetImpl)root).uncheckedDescendingToArray(this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,byte[] dst){
          ((ByteSetImpl)root).uncheckedDescendingToArray(this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,short[] dst){
          ((ByteSetImpl)root).uncheckedDescendingToArray(this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,int[] dst){
          ((ByteSetImpl)root).uncheckedDescendingToArray(this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,long[] dst){
          ((ByteSetImpl)root).uncheckedDescendingToArray(this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,float[] dst){
          ((ByteSetImpl)root).uncheckedDescendingToArray(this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,double[] dst){
          ((ByteSetImpl)root).uncheckedDescendingToArray(this.boundInfo,numLeft,dst);
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
      @Override int hashCodeImpl(int numLeft){
        return ((ByteSetImpl)root).uncheckedHashCode(this.boundInfo>>8,numLeft);
      }
      private static class Ascending extends BodySet{
        private Ascending(ByteSetImpl.Unchecked root,int boundInfo,int size){
          super(root,boundInfo,size);
        }
        private Ascending(UncheckedSubSet parent,int boundInfo,int size){
          super(parent,boundInfo,size);
        }
        @Override void forEachImpl(int numLeft,ByteConsumer action){
          ((ByteSetImpl)root).uncheckedAscendingForEach((byte)this.boundInfo,numLeft,action);
        }
        @Override String toStringImpl(int numLeft){
          return ((ByteSetImpl)root).uncheckedAscendingToString((byte)this.boundInfo,numLeft);
        }
        @Override void toArrayImpl(int numLeft,Byte[] dst){
          ((ByteSetImpl)root).uncheckedAscendingToArray(this.boundInfo>>8,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,Object[] dst){
          ((ByteSetImpl)root).uncheckedAscendingToArray(this.boundInfo>>8,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,byte[] dst){
          ((ByteSetImpl)root).uncheckedAscendingToArray(this.boundInfo>>8,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,short[] dst){
          ((ByteSetImpl)root).uncheckedAscendingToArray(this.boundInfo>>8,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,int[] dst){
          ((ByteSetImpl)root).uncheckedAscendingToArray(this.boundInfo>>8,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,long[] dst){
          ((ByteSetImpl)root).uncheckedAscendingToArray(this.boundInfo>>8,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,float[] dst){
          ((ByteSetImpl)root).uncheckedAscendingToArray(this.boundInfo>>8,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,double[] dst){
          ((ByteSetImpl)root).uncheckedAscendingToArray(this.boundInfo>>8,numLeft,dst);
        }
      }
      private static class Descending extends BodySet{
        private Descending(ByteSetImpl.Unchecked root,int boundInfo,int size){
          super(root,boundInfo,size);
        }
        private Descending(UncheckedSubSet parent,int boundInfo,int size){
          super(parent,boundInfo,size);
        }
        @Override void forEachImpl(int numLeft,ByteConsumer action){
          ((ByteSetImpl)root).uncheckedDescendingForEach(this.boundInfo>>8,numLeft,action);
        }
        @Override String toStringImpl(int numLeft){
          return ((ByteSetImpl)root).uncheckedDescendingToString(this.boundInfo>>8,numLeft);
        }
        @Override void toArrayImpl(int numLeft,Byte[] dst){
          ((ByteSetImpl)root).uncheckedDescendingToArray((byte)this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,Object[] dst){
          ((ByteSetImpl)root).uncheckedDescendingToArray((byte)this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,byte[] dst){
          ((ByteSetImpl)root).uncheckedDescendingToArray((byte)this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,short[] dst){
          ((ByteSetImpl)root).uncheckedDescendingToArray((byte)this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,int[] dst){
          ((ByteSetImpl)root).uncheckedDescendingToArray((byte)this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,long[] dst){
          ((ByteSetImpl)root).uncheckedDescendingToArray((byte)this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,float[] dst){
          ((ByteSetImpl)root).uncheckedDescendingToArray((byte)this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,double[] dst){
          ((ByteSetImpl)root).uncheckedDescendingToArray((byte)this.boundInfo,numLeft,dst);
        }
      }
    }
  }
  private static abstract class CheckedSubSet extends AbstractByteSet.ComparatorlessImpl{
    transient final ByteSetImpl.Checked root;
    transient final CheckedSubSet parent;
    transient final int boundInfo;
    transient int modCountAndSize;
    //TODO equals
    abstract boolean isInRange(int val);
    abstract void toArrayImpl(int numLeft,ByteSetImpl root,Byte[] dst);
    abstract void toArrayImpl(int numLeft,ByteSetImpl root,Object[] dst);
    abstract void toArrayImpl(int numLeft,ByteSetImpl root,byte[] dst);
    abstract void toArrayImpl(int numLeft,ByteSetImpl root,short[] dst);
    abstract void toArrayImpl(int numLeft,ByteSetImpl root,int[] dst);
    abstract void toArrayImpl(int numLeft,ByteSetImpl root,long[] dst);
    abstract void toArrayImpl(int numLeft,ByteSetImpl root,float[] dst);
    abstract void toArrayImpl(int numLeft,ByteSetImpl root,double[] dst);
    @Override public Byte[] toArray(){
      final ByteSetImpl.Checked root;
      int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,(root=this.root).modCountAndSize>>>9);
      if((modCountAndSize&=0x1ff)!=0){
        final Byte[] dst;
        toArrayImpl(modCountAndSize,root,dst=new Byte[modCountAndSize]);
        return dst;
      }
      return OmniArray.OfByte.DEFAULT_BOXED_ARR;
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      final int modCountAndSize=this.modCountAndSize;
      final T[] dst;
      final ByteSetImpl.Checked root;
      int size;
      try{
        dst=arrConstructor.apply(size=modCountAndSize&0x1ff);
      }finally{
        CheckedCollection.checkModCount(modCountAndSize>>>9,(root=this.root).modCountAndSize>>>9);
      }
      if(size!=0){
        toArrayImpl(size,root,dst);
      }
      return dst;
    }
    @Override public <T> T[] toArray(T[] dst){
       final ByteSetImpl.Checked root;
      int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,(root=this.root).modCountAndSize>>>9);
      if((modCountAndSize&=0x1ff)!=0){
        toArrayImpl(modCountAndSize,root,dst=OmniArray.uncheckedArrResize(modCountAndSize,dst));
      }else if(dst.length!=0){
        dst[0]=null;
      }
      return dst;
    }
    @Override public byte[] toByteArray(){
      final ByteSetImpl.Checked root;
      int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,(root=this.root).modCountAndSize>>>9);
      if((modCountAndSize&=0x1ff)!=0){
        final byte[] dst;
        toArrayImpl(modCountAndSize,root,dst=new byte[modCountAndSize]);
        return dst;
      }
      return OmniArray.OfByte.DEFAULT_ARR;
    }
    @Override public short[] toShortArray(){
      final ByteSetImpl.Checked root;
      int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,(root=this.root).modCountAndSize>>>9);
      if((modCountAndSize&=0x1ff)!=0){
        final short[] dst;
        toArrayImpl(modCountAndSize,root,dst=new short[modCountAndSize]);
        return dst;
      }
      return OmniArray.OfShort.DEFAULT_ARR;
    }
    @Override public int[] toIntArray(){
      final ByteSetImpl.Checked root;
      int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,(root=this.root).modCountAndSize>>>9);
      if((modCountAndSize&=0x1ff)!=0){
        final int[] dst;
        toArrayImpl(modCountAndSize,root,dst=new int[modCountAndSize]);
        return dst;
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
    @Override public long[] toLongArray(){
      final ByteSetImpl.Checked root;
      int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,(root=this.root).modCountAndSize>>>9);
      if((modCountAndSize&=0x1ff)!=0){
        final long[] dst;
        toArrayImpl(modCountAndSize,root,dst=new long[modCountAndSize]);
        return dst;
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override public float[] toFloatArray(){
      final ByteSetImpl.Checked root;
      int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,(root=this.root).modCountAndSize>>>9);
      if((modCountAndSize&=0x1ff)!=0){
        final float[] dst;
        toArrayImpl(modCountAndSize,root,dst=new float[modCountAndSize]);
        return dst;
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override public double[] toDoubleArray(){
      final ByteSetImpl.Checked root;
      int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,(root=this.root).modCountAndSize>>>9);
      if((modCountAndSize&=0x1ff)!=0){
        final double[] dst;
        toArrayImpl(modCountAndSize,root,dst=new double[modCountAndSize]);
        return dst;
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    abstract String toStringImpl(int numLeft,ByteSetImpl root);
    abstract int hashCodeImpl(int numLeft,ByteSetImpl root);
    @Override public int hashCode(){
      final ByteSetImpl.Checked root;
      int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,(root=this.root).modCountAndSize>>>9);
      if((modCountAndSize&=0x1ff)!=0){
        return hashCodeImpl(modCountAndSize,root);
      }
      return 0;
    }
    @Override public String toString(){
      final ByteSetImpl.Checked root;
      int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,(root=this.root).modCountAndSize>>>9);
      if((modCountAndSize&=0x1ff)!=0){
        return toStringImpl(modCountAndSize,root);
      }
      return "[]";
    }
    @Override public boolean contains(boolean val){
      final ByteSetImpl.Checked root;
      final int mask;
      final int modCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)>>>9,(root=this.root).modCountAndSize>>>9);
      return (modCountAndSize&0x1ff)!=0 && isInRange(mask=val?0b10:0b01) && (root.word2&mask)!=0;
    }
    @Override public boolean removeVal(boolean val){
      final ByteSetImpl.Checked root;
      final int mask;
      final int modCountAndSize;
      int rootModCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)&(~0x1ff),(rootModCountAndSize=(root=this.root).modCountAndSize)&(~0x1ff));
      long word;
      if((modCountAndSize&0x1ff)!=0 && isInRange(mask=val?0b10:0b01) && (word=root.word2)!=(word&=(~mask))){
        root.word2=word;
        root.modCountAndSize=rootModCountAndSize+(rootModCountAndSize=(1<<9)-1);
        bubbleUpModifyModCountAndSize(rootModCountAndSize);
        this.modCountAndSize=modCountAndSize+rootModCountAndSize;
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(byte val){
      final ByteSetImpl.Checked root;
      final int modCountAndSize;
      int rootModCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)&(~0x1ff),(rootModCountAndSize=(root=this.root).modCountAndSize)&(~0x1ff));
      if((modCountAndSize&0x1ff)!=0 && isInRange(val) && ((ByteSetImpl)root).uncheckedremoveByte(val)){
        root.modCountAndSize=rootModCountAndSize+(rootModCountAndSize=(1<<9)-1);
        bubbleUpModifyModCountAndSize(rootModCountAndSize);
        this.modCountAndSize=modCountAndSize+rootModCountAndSize;
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(char val){
      final ByteSetImpl.Checked root;
      final int modCountAndSize;
      int rootModCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)&(~0x1ff),(rootModCountAndSize=(root=this.root).modCountAndSize)&(~0x1ff));
      if((modCountAndSize&0x1ff)!=0 && isInRange(val) && ((ByteSetImpl)root).uncheckedremoveChar(val)){
        root.modCountAndSize=rootModCountAndSize+(rootModCountAndSize=(1<<9)-1);
        bubbleUpModifyModCountAndSize(rootModCountAndSize);
        this.modCountAndSize=modCountAndSize+rootModCountAndSize;
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(int val){
      final ByteSetImpl.Checked root;
      final int modCountAndSize;
      int rootModCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)&(~0x1ff),(rootModCountAndSize=(root=this.root).modCountAndSize)&(~0x1ff));
      if((modCountAndSize&0x1ff)!=0 && isInRange(val) && ((ByteSetImpl)root).uncheckedremoveInt(val)){
        root.modCountAndSize=rootModCountAndSize+(rootModCountAndSize=(1<<9)-1);
        bubbleUpModifyModCountAndSize(rootModCountAndSize);
        this.modCountAndSize=modCountAndSize+rootModCountAndSize;
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(long val){
      final ByteSetImpl.Checked root;
      final int modCountAndSize;
      int rootModCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)&(~0x1ff),(rootModCountAndSize=(root=this.root).modCountAndSize)&(~0x1ff));
      final int v;
      if((modCountAndSize&0x1ff)!=0 && (v=(byte)val)==val && isInRange(v) && ((ByteSetImpl)root).uncheckedremoveByte(v)){
        root.modCountAndSize=rootModCountAndSize+(rootModCountAndSize=(1<<9)-1);
        bubbleUpModifyModCountAndSize(rootModCountAndSize);
        this.modCountAndSize=modCountAndSize+rootModCountAndSize;
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(float val){
      final ByteSetImpl.Checked root;
      final int modCountAndSize;
      int rootModCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)&(~0x1ff),(rootModCountAndSize=(root=this.root).modCountAndSize)&(~0x1ff));
      final int v;
      if((modCountAndSize&0x1ff)!=0 && (v=(byte)val)==val && isInRange(v) && ((ByteSetImpl)root).uncheckedremoveByte(v)){
        root.modCountAndSize=rootModCountAndSize+(rootModCountAndSize=(1<<9)-1);
        bubbleUpModifyModCountAndSize(rootModCountAndSize);
        this.modCountAndSize=modCountAndSize+rootModCountAndSize;
        return true;
      }
      return false;
    }
    @Override public boolean removeVal(double val){
      final ByteSetImpl.Checked root;
      final int modCountAndSize;
      int rootModCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)&(~0x1ff),(rootModCountAndSize=(root=this.root).modCountAndSize)&(~0x1ff));
      final int v;
      if((modCountAndSize&0x1ff)!=0 && (v=(byte)val)==val && isInRange(v) && ((ByteSetImpl)root).uncheckedremoveByte(v)){
        root.modCountAndSize=rootModCountAndSize+(rootModCountAndSize=(1<<9)-1);
        bubbleUpModifyModCountAndSize(rootModCountAndSize);
        this.modCountAndSize=modCountAndSize+rootModCountAndSize;
        return true;
      }
      return false;
    }
    @Override public boolean remove(Object val){
      final ByteSetImpl.Checked root;
      final int modCountAndSize;
      int rootModCountAndSize;
      CheckedCollection.checkModCount((modCountAndSize=this.modCountAndSize)&(~0x1ff),(rootModCountAndSize=(root=this.root).modCountAndSize)&(~0x1ff));
      final int v;
      if((modCountAndSize&0x1ff)!=0){
        for(;;){
          if(val instanceof Byte){
            if(!isInRange(v=(byte)val) || !((ByteSetImpl)root).uncheckedremoveByte(v)){
              break;
            }
          }else if(val instanceof Integer || val instanceof Short){
            if(!isInRange(v=((Number)val).intValue()) || !((ByteSetImpl)root).uncheckedremoveInt(v)){
              break;
            }
          }else if(val instanceof Long){
            final long l;
            if((l=(long)val)!=(v=(byte)l) || !isInRange(v) || !((ByteSetImpl)root).uncheckedremoveByte(v)){
              break;
            }
          }else if(val instanceof Float){
            final float f;
            if((f=(float)val)!=(v=(byte)f) || !isInRange(v) || !((ByteSetImpl)root).uncheckedremoveByte(v)){
              break;
            }
          }else if(val instanceof Double){
            final double d;
            if((d=(double)val)!=(v=(byte)d) || !isInRange(v) || !((ByteSetImpl)root).uncheckedremoveByte(v)){
              break;
            }
          }else if(val instanceof Character){
            if(!isInRange(v=(char)val) || !((ByteSetImpl)root).uncheckedremoveChar(v)){
              break;
            }
          }else if(val instanceof Boolean){
            long word;
            if(!isInRange(v=((boolean)val)?0b10:0b01) || (word=root.word2)==(word&=(~v))){
              break;
            }
            root.word2=word;
          }else{
            break;
          }
          root.modCountAndSize=rootModCountAndSize+(rootModCountAndSize=(1<<9)-1);
          bubbleUpModifyModCountAndSize(rootModCountAndSize);
          this.modCountAndSize=modCountAndSize+rootModCountAndSize;
          return true;
        }
      }
      return false;
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
    abstract void forEachImpl(int numLeft,ByteSetImpl root,ByteConsumer action);
    @Override public void forEach(ByteConsumer action){
      final int modCountAndSize=this.modCountAndSize;
      final var root=this.root;
      try{
        final int numLeft;
        if((numLeft=modCountAndSize&0x1ff)!=0){
          forEachImpl(numLeft,root,action);
        }
      }finally{
        CheckedCollection.checkModCount(modCountAndSize&(~0x1ff),root.modCountAndSize&(~0x1ff));
      }
    }
    @Override public void forEach(Consumer<? super Byte> action){
      final int modCountAndSize=this.modCountAndSize;
      final var root=this.root;
      try{
        final int numLeft;
        if((numLeft=modCountAndSize&0x1ff)!=0){
          forEachImpl(numLeft,root,action::accept);
        }
      }finally{
        CheckedCollection.checkModCount(modCountAndSize&(~0x1ff),root.modCountAndSize&(~0x1ff));
      }
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
      @Override int hashCodeImpl(int numLeft,ByteSetImpl root){
        return root.uncheckedHashCode(this.boundInfo,numLeft);
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
        @Override void forEachImpl(int numLeft,ByteSetImpl root,ByteConsumer action){
          root.uncheckedAscendingForEach(numLeft,action);
        }
        @Override String toStringImpl(int numLeft,ByteSetImpl root){
          return root.uncheckedAscendingToString(numLeft);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,Byte[] dst){
          root.uncheckedAscendingToArray(this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,Object[] dst){
          root.uncheckedAscendingToArray(this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,byte[] dst){
          root.uncheckedAscendingToArray(this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,short[] dst){
          root.uncheckedAscendingToArray(this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,int[] dst){
          root.uncheckedAscendingToArray(this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,long[] dst){
          root.uncheckedAscendingToArray(this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,float[] dst){
          root.uncheckedAscendingToArray(this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,double[] dst){
          root.uncheckedAscendingToArray(this.boundInfo,numLeft,dst);
        }
      }
      private static class Descending extends HeadSet{
        private Descending(ByteSetImpl.Checked root,int boundInfo,int modCountAndSize){
          super(root,boundInfo,modCountAndSize);
        }
        private Descending(Descending parent,int boundInfo,int modCountAndSize){
          super(parent,boundInfo,modCountAndSize);
        }
        @Override void forEachImpl(int numLeft,ByteSetImpl root,ByteConsumer action){
          root.uncheckedDescendingForEach(this.boundInfo,numLeft,action);
        }
        @Override String toStringImpl(int numLeft,ByteSetImpl root){
          return root.uncheckedDescendingToString(this.boundInfo,numLeft);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,Byte[] dst){
          root.uncheckedDescendingToArray(numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,Object[] dst){
          root.uncheckedDescendingToArray(numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,byte[] dst){
          root.uncheckedDescendingToArray(numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,short[] dst){
          root.uncheckedDescendingToArray(numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,int[] dst){
          root.uncheckedDescendingToArray(numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,long[] dst){
          root.uncheckedDescendingToArray(numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,float[] dst){
          root.uncheckedDescendingToArray(numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,double[] dst){
          root.uncheckedDescendingToArray(numLeft,dst);
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
      @Override int hashCodeImpl(int numLeft,ByteSetImpl root){
        return root.uncheckedHashCode(numLeft);
      }
      private static class Ascending extends TailSet{
        private Ascending(ByteSetImpl.Checked root,int boundInfo,int modCountAndSize){
          super(root,boundInfo,modCountAndSize);
        }
        private Ascending(Ascending parent,int boundInfo,int modCountAndSize){
          super(parent,boundInfo,modCountAndSize);
        }
        @Override void forEachImpl(int numLeft,ByteSetImpl root,ByteConsumer action){
          root.uncheckedAscendingForEach(this.boundInfo,numLeft,action);
        }
        @Override String toStringImpl(int numLeft,ByteSetImpl root){
          return root.uncheckedAscendingToString(this.boundInfo,numLeft);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,Byte[] dst){
          root.uncheckedAscendingToArray(numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,Object[] dst){
          root.uncheckedAscendingToArray(numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,byte[] dst){
          root.uncheckedAscendingToArray(numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,short[] dst){
          root.uncheckedAscendingToArray(numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,int[] dst){
          root.uncheckedAscendingToArray(numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,long[] dst){
          root.uncheckedAscendingToArray(numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,float[] dst){
          root.uncheckedAscendingToArray(numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,double[] dst){
          root.uncheckedAscendingToArray(numLeft,dst);
        }
      }
      private static class Descending extends TailSet{
        private Descending(ByteSetImpl.Checked root,int boundInfo,int modCountAndSize){
          super(root,boundInfo,modCountAndSize);
        }
        private Descending(Descending parent,int boundInfo,int modCountAndSize){
          super(parent,boundInfo,modCountAndSize);
        }
        @Override void forEachImpl(int numLeft,ByteSetImpl root,ByteConsumer action){
          root.uncheckedDescendingForEach(numLeft,action);
        }
        @Override String toStringImpl(int numLeft,ByteSetImpl root){
          return root.uncheckedDescendingToString(numLeft);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,Byte[] dst){
          root.uncheckedDescendingToArray(this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,Object[] dst){
          root.uncheckedDescendingToArray(this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,byte[] dst){
          root.uncheckedDescendingToArray(this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,short[] dst){
          root.uncheckedDescendingToArray(this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,int[] dst){
          root.uncheckedDescendingToArray(this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,long[] dst){
          root.uncheckedDescendingToArray(this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,float[] dst){
          root.uncheckedDescendingToArray(this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,double[] dst){
          root.uncheckedDescendingToArray(this.boundInfo,numLeft,dst);
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
      @Override int hashCodeImpl(int numLeft,ByteSetImpl root){
        return root.uncheckedHashCode(this.boundInfo>>8,numLeft);
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
        @Override void forEachImpl(int numLeft,ByteSetImpl root,ByteConsumer action){
          root.uncheckedAscendingForEach((byte)boundInfo,numLeft,action);
        }
        @Override String toStringImpl(int numLeft,ByteSetImpl root){
          return root.uncheckedAscendingToString((byte)boundInfo,numLeft);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,Byte[] dst){
          root.uncheckedAscendingToArray(this.boundInfo>>8,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,Object[] dst){
          root.uncheckedAscendingToArray(this.boundInfo>>8,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,byte[] dst){
          root.uncheckedAscendingToArray(this.boundInfo>>8,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,short[] dst){
          root.uncheckedAscendingToArray(this.boundInfo>>8,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,int[] dst){
          root.uncheckedAscendingToArray(this.boundInfo>>8,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,long[] dst){
          root.uncheckedAscendingToArray(this.boundInfo>>8,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,float[] dst){
          root.uncheckedAscendingToArray(this.boundInfo>>8,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,double[] dst){
          root.uncheckedAscendingToArray(this.boundInfo>>8,numLeft,dst);
        }
      }
      private static class Descending extends BodySet{
        private Descending(ByteSetImpl.Checked root,int boundInfo,int modCountAndSize){
          super(root,boundInfo,modCountAndSize);
        }
        private Descending(CheckedSubSet parent,int boundInfo,int modCountAndSize){
          super(parent,boundInfo,modCountAndSize);
        }
        @Override void forEachImpl(int numLeft,ByteSetImpl root,ByteConsumer action){
          root.uncheckedDescendingForEach(boundInfo>>8,numLeft,action);
        }
        @Override String toStringImpl(int numLeft,ByteSetImpl root){
          return root.uncheckedDescendingToString(boundInfo>>8,numLeft);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,Byte[] dst){
          root.uncheckedDescendingToArray((byte)this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,Object[] dst){
          root.uncheckedDescendingToArray((byte)this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,byte[] dst){
          root.uncheckedDescendingToArray((byte)this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,short[] dst){
          root.uncheckedDescendingToArray((byte)this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,int[] dst){
          root.uncheckedDescendingToArray((byte)this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,long[] dst){
          root.uncheckedDescendingToArray((byte)this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,float[] dst){
          root.uncheckedDescendingToArray((byte)this.boundInfo,numLeft,dst);
        }
        @Override void toArrayImpl(int numLeft,ByteSetImpl root,double[] dst){
          root.uncheckedDescendingToArray((byte)this.boundInfo,numLeft,dst);
        }
      }
    }
  }
}
