package omni.impl.set;
import omni.api.OmniNavigableSet;
import java.io.Serializable;
import java.io.Externalizable;
import java.util.Collection;
import omni.api.OmniCollection;
import omni.function.BooleanComparator;
import omni.function.BooleanConsumer;
import java.util.function.Consumer;
import omni.function.BooleanPredicate;
import java.util.function.Predicate;
import java.io.ObjectOutput;
import java.io.ObjectInput;
import java.io.IOException;
import java.util.function.IntFunction;
import omni.util.OmniArray;
import omni.api.OmniIterator;
import java.util.Set;
import omni.api.OmniSet;
import java.util.NoSuchElementException;
import omni.impl.AbstractBooleanItr;
import java.util.ConcurrentModificationException;
import omni.impl.CheckedCollection;
public class BooleanSetImpl extends AbstractBooleanSet implements Externalizable,Cloneable{
  transient int state;
  @Override public OmniNavigableSet.OfBoolean descendingSet(){
    return new DescendingView(this);
  }
  private static final long serialVersionUID=1L;
  public BooleanSetImpl(){
    super();
  }
  public BooleanSetImpl(BooleanSetImpl that){
    this.state=that.state;
  }
  public BooleanSetImpl(OmniCollection.OfBoolean that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  public BooleanSetImpl(OmniCollection.OfRef<? extends Boolean> that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  public BooleanSetImpl(Collection<? extends Boolean> that){
    super();
    //TODO optimize
    this.addAll(that);
  }
  BooleanSetImpl(int state){
    super();
    this.state=state;
  }
  @Override public Object clone(){
    return new BooleanSetImpl(this.state);
  }
  @Override public BooleanComparator comparator(){
    return Boolean::compare;
  }
  @Override public String toString(){
    switch(this.state){
      case 0b00:
        return "[]";
      case 0b01:
        return "[false]";
      case 0b10:
        return "[true]";
      default:
        return "[false, true]";
    }
  }
  @Override public Boolean ceiling(boolean val){
    switch(this.state){
      case 0b11:
        return val;
      case 0b10:
        return Boolean.TRUE;
      default:
    }
    return null;
  }
  @Override public Boolean floor(boolean val){
    switch(this.state){
      case 0b11:
        return val;
      case 0b01:
        return Boolean.FALSE;
      case 0b10:
        if(val){
          return Boolean.TRUE;
        }
      case 0b00:
    }
    return null;
  }
  @Override public Boolean lower(boolean val){
    if(val && (this.state&0b01)!=0){
      return Boolean.FALSE;
    }
    return null;
  }
  @Override public Boolean higher(boolean val){
    if(!val && (this.state&0b10)!=0){
      return Boolean.TRUE;
    }
    return null;
  }
  @Override public Boolean pollFirst(){
    switch(this.state){
      default:
        return null;
      case 0b10:
        this.state=0b00;
        return Boolean.TRUE;
      case 0b11:
        this.state=0b10;
        break;
      case 0b01:
        this.state=0b00;
    }
    return Boolean.FALSE;  
  }
  @Override public Boolean pollLast(){
    switch(this.state){
      case 0b01:
        this.state=0b00;
        return Boolean.FALSE;
      default:
        return null;
      case 0b10:
        this.state=0b00;
        break;
      case 0b11:
        this.state=0b01;
    }
    return Boolean.TRUE;
  }
  @Override public boolean booleanCeiling(boolean val){
    switch(this.state){
      case 0b11:
        return val;
      case 0b10:
        return true;
      default:
    }
    return false;
  }
  @Override public boolean booleanFloor(boolean val){
    return val && (this.state&0b10)!=0;
  }
  @Override public boolean lowerBoolean(boolean val){
    return false;
  }
  @Override public boolean higherBoolean(boolean val){
    return !val && (this.state&0b10)!=0;
  }
  @Override public boolean pollFirstBoolean(){
    switch(this.state){
      case 0b10:
        this.state=0b00;
        return true;
      case 0b11:
        this.state=0b10;
        break;
      case 0b01:
        this.state=0b00;
      default:
    }
    return false;
  }
  @Override public boolean pollLastBoolean(){
    switch(this.state){
      case 0b01:
        this.state=0b00;
      default:
        return false;
      case 0b10:
        this.state=0b00;
        break;
      case 0b11:
        this.state=0b01;
    }
    return true;
  }
  @Override public byte byteCeiling(byte val){
    switch(this.state){
    case 0b11:
      switch(Integer.signum(val-1)){
      case -1:
        return 0;
      case 0:
        return 1;
      default:
      }
      break;
    case 0b10:
      if(val<=1){
        return 1;
      }
      break;
    case 0b01:
      if(val<=0){
        return 0;
      }
    default:
    }
    return Byte.MIN_VALUE;
  }
  @Override public byte byteFloor(byte val){
    switch(this.state){
    case 0b11:
      switch(Integer.signum(val)){ 
      case 1:
        return 1;
      case 0:
        return 0;
      default:
      }
      break;
    case 0b01:
      if(val>=0){
        return 0;
      }
      break;
    case 0b10:
      if(val>=1){
        return 1;
      }
    default:
    }
    return Byte.MIN_VALUE;
  }
  @Override public byte lowerByte(byte val){
    switch(this.state){
    case 0b11:
      switch(Integer.signum(val-1)){    
      case 1:
        return 1;
      case 0:
        return 0;
      default:
      }
      break;
    case 0b01:
      if(val>0){
        return 0;
      }
      break;
    case 0b10:
      if(val>1){
        return 1;
      }
    default:
    }    
    return Byte.MIN_VALUE;
  }
  @Override public byte higherByte(byte val){
    switch(this.state){
    case 0b11:
      switch(Integer.signum(val)){
      case -1:
        return 0;
      case 0:
        return 1;
      default:
      }
      break;
    case 0b10:
      if(val<1){
        return 1;
      }
      break;
    case 0b01:
      if(val<0){
        return 0;
      }
    default:
    }
    return Byte.MIN_VALUE;
  }
  @Override public byte pollFirstByte(){
    switch(this.state){
      default:
        return Byte.MIN_VALUE;
      case 0b10:
        this.state=0b00;
        return 1;
      case 0b11:
        this.state=0b10;
        break;
      case 0b01:
        this.state=0b00;
    }
    return 0;  
  }
  @Override public byte pollLastByte(){
    switch(this.state){
      case 0b01:
        this.state=0b00;
        return 0;
      default:
        return Byte.MIN_VALUE;
      case 0b10:
        this.state=0b00;
        break;
      case 0b11:
        this.state=0b01;
    }
    return 1;
  }
  @Override public char charCeiling(char val){
    switch(this.state){
     case 0b11:
       if(val==1){
         return 1;
       }
       break;
     case 0b10:
       if(val<=1){
         return 1;
       }
     default:
    }
    return Character.MIN_VALUE;
  }
  @Override public char charFloor(char val){
    if(val>0 && (this.state&0b10)!=0){
      return 1;
    }
    return Character.MIN_VALUE;
  }
  @Override public char lowerChar(char val){
    if(val>1 && (this.state&0b10)!=0){
      return 1;
    }
    return Character.MIN_VALUE;
  }
  @Override public char higherChar(char val){
    if(val==0 && (this.state&0b10)!=0){
      return 1;
    }
    return Character.MIN_VALUE;
  }
  @Override public char pollFirstChar(){
    switch(this.state){
      case 0b10:
        this.state=0b00;
        return 1;
      case 0b11:
        this.state=0b10;
        break;
      case 0b01:
        this.state=0b00;
      default:
    }
    return Character.MIN_VALUE;
  }
  @Override public char pollLastChar(){
    switch(this.state){
      case 0b01:
        this.state=0b00;
      default:
        return Character.MIN_VALUE;
      case 0b10:
        this.state=0b00;
        break;
      case 0b11:
        this.state=0b01;
    }
    return 1;
  }
  @Override public short shortCeiling(short val){
    switch(this.state){
    case 0b11:
      switch(Integer.signum(val-1)){
      case -1:
        return 0;
      case 0:
        return 1;
      default:
      }
      break;
    case 0b10:
      if(val<=1){
        return 1;
      }
      break;
    case 0b01:
      if(val<=0){
        return 0;
      }
    default:
    }
    return Short.MIN_VALUE;
  }
  @Override public short shortFloor(short val){
    switch(this.state){
    case 0b11:
      switch(Integer.signum(val)){ 
      case 1:
        return 1;
      case 0:
        return 0;
      default:
      }
      break;
    case 0b01:
      if(val>=0){
        return 0;
      }
      break;
    case 0b10:
      if(val>=1){
        return 1;
      }
    default:
    }
    return Short.MIN_VALUE;
  }
  @Override public short lowerShort(short val){
    switch(this.state){
    case 0b11:
      switch(Integer.signum(val-1)){    
      case 1:
        return 1;
      case 0:
        return 0;
      default:
      }
      break;
    case 0b01:
      if(val>0){
        return 0;
      }
      break;
    case 0b10:
      if(val>1){
        return 1;
      }
    default:
    }    
    return Short.MIN_VALUE;
  }
  @Override public short higherShort(short val){
    switch(this.state){
    case 0b11:
      switch(Integer.signum(val)){
      case -1:
        return 0;
      case 0:
        return 1;
      default:
      }
      break;
    case 0b10:
      if(val<1){
        return 1;
      }
      break;
    case 0b01:
      if(val<0){
        return 0;
      }
    default:
    }
    return Short.MIN_VALUE;
  }
  @Override public short pollFirstShort(){
    switch(this.state){
      default:
        return Short.MIN_VALUE;
      case 0b10:
        this.state=0b00;
        return 1;
      case 0b11:
        this.state=0b10;
        break;
      case 0b01:
        this.state=0b00;
    }
    return 0;  
  }
  @Override public short pollLastShort(){
    switch(this.state){
      case 0b01:
        this.state=0b00;
        return 0;
      default:
        return Short.MIN_VALUE;
      case 0b10:
        this.state=0b00;
        break;
      case 0b11:
        this.state=0b01;
    }
    return 1;
  }
  @Override public int intCeiling(int val){
    switch(this.state){
    case 0b11:
      switch(Integer.signum(val-1)){
      case -1:
        return 0;
      case 0:
        return 1;
      default:
      }
      break;
    case 0b10:
      if(val<=1){
        return 1;
      }
      break;
    case 0b01:
      if(val<=0){
        return 0;
      }
    default:
    }
    return Integer.MIN_VALUE;
  }
  @Override public int intFloor(int val){
    switch(this.state){
    case 0b11:
      switch(Integer.signum(val)){ 
      case 1:
        return 1;
      case 0:
        return 0;
      default:
      }
      break;
    case 0b01:
      if(val>=0){
        return 0;
      }
      break;
    case 0b10:
      if(val>=1){
        return 1;
      }
    default:
    }
    return Integer.MIN_VALUE;
  }
  @Override public int lowerInt(int val){
    switch(this.state){
    case 0b11:
      switch(Integer.signum(val-1)){    
      case 1:
        return 1;
      case 0:
        return 0;
      default:
      }
      break;
    case 0b01:
      if(val>0){
        return 0;
      }
      break;
    case 0b10:
      if(val>1){
        return 1;
      }
    default:
    }    
    return Integer.MIN_VALUE;
  }
  @Override public int higherInt(int val){
    switch(this.state){
    case 0b11:
      switch(Integer.signum(val)){
      case -1:
        return 0;
      case 0:
        return 1;
      default:
      }
      break;
    case 0b10:
      if(val<1){
        return 1;
      }
      break;
    case 0b01:
      if(val<0){
        return 0;
      }
    default:
    }
    return Integer.MIN_VALUE;
  }
  @Override public int pollFirstInt(){
    switch(this.state){
      default:
        return Integer.MIN_VALUE;
      case 0b10:
        this.state=0b00;
        return 1;
      case 0b11:
        this.state=0b10;
        break;
      case 0b01:
        this.state=0b00;
    }
    return 0;  
  }
  @Override public int pollLastInt(){
    switch(this.state){
      case 0b01:
        this.state=0b00;
        return 0;
      default:
        return Integer.MIN_VALUE;
      case 0b10:
        this.state=0b00;
        break;
      case 0b11:
        this.state=0b01;
    }
    return 1;
  }
  @Override public long longCeiling(long val){
    switch(this.state){
    case 0b11:
      switch(Long.signum(val-1L)){
      case -1:
        return 0L;
      case 0:
        return 1L;
      default:
      }
      break;
    case 0b10:
      if(val<=1L){
        return 1L;
      }
      break;
    case 0b01:
      if(val<=0L){
        return 0L;
      }
    default:
    }
    return Long.MIN_VALUE;
  }
  @Override public long longFloor(long val){
    switch(this.state){
    case 0b11:
      switch(Long.signum(val)){
      case 1:
        return 1L;
      case 0:
        return 0L;
      default:
      }
      break;
    case 0b01:
      if(val>=0L){
        return 0L;
      }
      break;
    case 0b10:
      if(val>=1L){
        return 1L;
      }
    default:
    }
    return Long.MIN_VALUE;
  }
  @Override public long lowerLong(long val){
    switch(this.state){
    case 0b11:
      switch(Long.signum(val-1L)){
      case 1:
        return 1L;
      case 0:
        return 0L;
      default:
      }
      break;
    case 0b01:
      if(val>0L){
        return 0L;
      }
      break;
    case 0b10:
      if(val>1L){
        return 1L;
      }
    default:
    }    
    return Long.MIN_VALUE;
  }
  @Override public long higherLong(long val){
    switch(this.state){
    case 0b11:
      switch(Long.signum(val)){
      case -1:
        return 0L;
      case 0:
        return 1L;
      default:
      }
      break;
    case 0b10:
      if(val<1L){
        return 1L;
      }
      break;
    case 0b01:
      if(val<0L){
        return 0L;
      }
    default:
    }
    return Long.MIN_VALUE;
  }
  @Override public long pollFirstLong(){
    switch(this.state){
      default:
        return Long.MIN_VALUE;
      case 0b10:
        this.state=0b00;
        return 1L;
      case 0b11:
        this.state=0b10;
        break;
      case 0b01:
        this.state=0b00;
    }
    return 0L;  
  }
  @Override public long pollLastLong(){
    switch(this.state){
      case 0b01:
        this.state=0b00;
        return 0L;
      default:
        return Long.MIN_VALUE;
      case 0b10:
        this.state=0b00;
        break;
      case 0b11:
        this.state=0b01;
    }
    return 1L;
  }
  @Override public float floatCeiling(float val){
    switch(this.state){
    case 0b11:
      if(val<=0F){
        return 0F;
      }
    case 0b10:
      if(val<=1F){
        return 1F;
      }
      break;
    case 0b01:
      if(val<=0F){
        return 0F;
      }
      break;
    default:
    }
    return Float.NaN;
  }
  @Override public float floatFloor(float val){
    switch(this.state){
    case 0b11:
      if(val>=1F){
        return 1F;
      }
    case 0b01:
      if(val>=0F){
        return 0F;
      }
      break;
    case 0b10:
      if(val>=1F){
        return 1F;
      }
    default:
    }
    return Float.NaN;
  }
  @Override public float lowerFloat(float val){
    switch(this.state){
    case 0b11:
      if(val>1F){
        return 1F;
      }
    case 0b01:
      if(val>0F){
        return 0F;
      }
      break;
    case 0b10:
      if(val>1F){
        return 1F;
      }
    default:
    }
    return Float.NaN;
  }
  @Override public float higherFloat(float val){
    switch(this.state){
    case 0b11:
      if(val<0F){
        return 0F;
      }
    case 0b10:
      if(val<1F){
        return 1F;
      }
      break;
    case 0b01:
      if(val<0F){
        return 0F;
      }
    default:
    }
    return Float.NaN;
  }
  @Override public float pollFirstFloat(){
    switch(this.state){
      default:
        return Float.NaN;
      case 0b10:
        this.state=0b00;
        return 1F;
      case 0b11:
        this.state=0b10;
        break;
      case 0b01:
        this.state=0b00;
    }
    return 0F;  
  }
  @Override public float pollLastFloat(){
    switch(this.state){
      case 0b01:
        this.state=0b00;
        return 0F;
      default:
        return Float.NaN;
      case 0b10:
        this.state=0b00;
        break;
      case 0b11:
        this.state=0b01;
    }
    return 1F;
  }
  @Override public double doubleCeiling(double val){
    switch(this.state){
    case 0b11:
      if(val<=0D){
        return 0D;
      }
    case 0b10:
      if(val<=1D){
        return 1D;
      }
      break;
    case 0b01:
      if(val<=0D){
        return 0D;
      }
      break;
    default:
    }
    return Double.NaN;
  }
  @Override public double doubleFloor(double val){
    switch(this.state){
    case 0b11:
      if(val>=1D){
        return 1D;
      }
    case 0b01:
      if(val>=0D){
        return 0D;
      }
      break;
    case 0b10:
      if(val>=1D){
        return 1D;
      }
    default:
    }
    return Double.NaN;
  }
  @Override public double lowerDouble(double val){
    switch(this.state){
    case 0b11:
      if(val>1D){
        return 1D;
      }
    case 0b01:
      if(val>0D){
        return 0D;
      }
      break;
    case 0b10:
      if(val>1D){
        return 1D;
      }
    default:
    }
    return Double.NaN;
  }
  @Override public double higherDouble(double val){
    switch(this.state){
    case 0b11:
      if(val<0D){
        return 0D;
      }
    case 0b10:
      if(val<1D){
        return 1D;
      }
      break;
    case 0b01:
      if(val<0D){
        return 0D;
      }
    default:
    }
    return Double.NaN;
  }
  @Override public double pollFirstDouble(){
    switch(this.state){
      default:
        return Double.NaN;
      case 0b10:
        this.state=0b00;
        return 1D;
      case 0b11:
        this.state=0b10;
        break;
      case 0b01:
        this.state=0b00;
    }
    return 0D;  
  }
  @Override public double pollLastDouble(){
    switch(this.state){
      case 0b01:
        this.state=0b00;
        return 0D;
      default:
        return Double.NaN;
      case 0b10:
        this.state=0b00;
        break;
      case 0b11:
        this.state=0b01;
    }
    return 1D;
  }
  @Override public boolean equals(Object val){
  if(val==this){
      return true;
    }
    if(val instanceof Set){
      switch(this.state){
        case 0b00:
          return ((Set<?>)val).isEmpty();
        case 0b01:
          return equalsFalseState((Set<?>)val);
        case 0b10:
          return equalsTrueState((Set<?>)val);
        default:
          return equalsFullState((Set<?>)val);
      }
    }
    return false;
  }
  @Override public int size(){
    switch(this.state){
      case 0b00:
        return 0;
      case 0b01:
      case 0b10:
        return 1;
      default:
        return 2;
    }
  }
  @Override public void clear(){
    this.state=0b00;
  }
  @Override public int hashCode(){
    switch(this.state){
      case 0b00:
        return 0;
      case 0b01:
        return 1237;
      case 0b10:
        return 1231;
      default:
        return 1231+1237;
    }
  }
  @Override public boolean isEmpty(){
    return this.state==0b00;
  }
  @Override public void forEach(BooleanConsumer action){
    switch(this.state){
      case 0b11:
        action.accept(false);
      case 0b10:
        action.accept(true);
        break;
      case 0b01:
        action.accept(false);
      default:
    }
  }
  @Override public void forEach(Consumer<? super Boolean> action){
    switch(this.state){
      case 0b11:
        action.accept(Boolean.FALSE);
      case 0b10:
        action.accept(Boolean.TRUE);
        break;
      case 0b01:
        action.accept(Boolean.FALSE);
      default:
    }
  }
  @Override public boolean removeIf(BooleanPredicate filter){
    switch(this.state){
      case 0b11:
        if(filter.test(false)){
          if(filter.test(true)){
            break;
          }else{
            this.state=0b10;
            return true;
          }
        }else if(filter.test(true)){
          this.state=0b01;
          return true;
        }
        return false;
      case 0b10:
        if(filter.test(true)){
          break;
        }
        return false;
      case 0b01:
        if(filter.test(false)){
          break;
        }
      default:
        return false;
    }
    this.state=0b00;
    return true;
  }
  @Override public boolean removeIf(Predicate<? super Boolean> filter){
    switch(this.state){
      case 0b11:
        if(filter.test(Boolean.FALSE)){
          if(filter.test(Boolean.TRUE)){
            break;
          }else{
            this.state=0b10;
            return true;
          }
        }else if(filter.test(Boolean.TRUE)){
          this.state=0b01;
          return true;
        }
        return false;
      case 0b10:
        if(filter.test(Boolean.TRUE)){
          break;
        }
        return false;
      case 0b01:
        if(filter.test(Boolean.FALSE)){
          break;
        }
      default:
        return false;
    }
    this.state=0b00;
    return true;
  }
  @Override boolean addTrue(){
    final int state;
    if(((state=this.state)&0b10)==0){
      this.state=state+0b10;
      return true;
    }
    return false;
  }
  @Override boolean containsTrue(){
    return (this.state&0b10)!=0;
  }
  @Override boolean removeTrue(){
    final int state;
    if(((state=this.state)&0b10)!=0){
      this.state=state-0b10;
      return true;
    }
    return false;
  }
  @Override boolean addFalse(){
    final int state;
    if(((state=this.state)&0b01)==0){
      this.state=state+0b01;
      return true;
    }
    return false;
  }
  @Override boolean containsFalse(){
    return (this.state&0b01)!=0;
  }
  @Override boolean removeFalse(){
    final int state;
    if(((state=this.state)&0b01)!=0){
      this.state=state-0b01;
      return true;
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  @Override public <T> T[] toArray(T[] dst){
    switch(this.state){
    case 0b11:
      (dst=OmniArray.uncheckedArrResize(2,dst))[0]=(T)Boolean.FALSE;
      dst[1]=(T)Boolean.TRUE;
      break;
    case 0b10:
      (dst=OmniArray.uncheckedArrResize(1,dst))[0]=(T)Boolean.TRUE;
      break;
    case 0b01:
      (dst=OmniArray.uncheckedArrResize(1,dst))[0]=(T)Boolean.FALSE;
      break;
    default:
      if(dst.length!=0){
        dst[0]=null;
      }
    }
    return dst;
  }
  @SuppressWarnings("unchecked")
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final T[] dst;
    switch(this.state){
    case 0b11:
      (dst=arrConstructor.apply(2))[0]=(T)Boolean.FALSE;
      dst[1]=(T)Boolean.TRUE;
      break;
    case 0b10:
      (dst=arrConstructor.apply(1))[0]=(T)Boolean.TRUE;
      break;
    case 0b01:
      (dst=arrConstructor.apply(1))[0]=(T)Boolean.FALSE;
      break;
    default:
      return arrConstructor.apply(0);
    }
    return dst;
  }
  @Override public Boolean[] toArray(){
    switch(this.state){
      case 0b00:
        return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
      case 0b01:
        return new Boolean[]{Boolean.FALSE};
      case 0b10:
        return new Boolean[]{Boolean.TRUE};
      default:
        return new Boolean[]{Boolean.FALSE,Boolean.TRUE};
    }
  }
  @Override public boolean[] toBooleanArray(){
    switch(this.state){
      case 0b00:
        return OmniArray.OfBoolean.DEFAULT_ARR;
      case 0b01:
        return new boolean[]{false};
      case 0b10:
        return new boolean[]{true};
      default:
        return new boolean[]{false,true};
    }
  }
  @Override public byte[] toByteArray(){
    switch(this.state){
      case 0b00:
        return OmniArray.OfByte.DEFAULT_ARR;
      case 0b01:
        return new byte[]{(byte)0};
      case 0b10:
        return new byte[]{(byte)1};
      default:
        return new byte[]{(byte)0,(byte)1};
    }
  }
  @Override public char[] toCharArray(){
    switch(this.state){
      case 0b00:
        return OmniArray.OfChar.DEFAULT_ARR;
      case 0b01:
        return new char[]{(char)0};
      case 0b10:
        return new char[]{(char)1};
      default:
        return new char[]{(char)0,(char)1};
    }
  }
  @Override public short[] toShortArray(){
    switch(this.state){
      case 0b00:
        return OmniArray.OfShort.DEFAULT_ARR;
      case 0b01:
        return new short[]{(short)0};
      case 0b10:
        return new short[]{(short)1};
      default:
        return new short[]{(short)0,(short)1};
    }
  }
  @Override public int[] toIntArray(){
    switch(this.state){
      case 0b00:
        return OmniArray.OfInt.DEFAULT_ARR;
      case 0b01:
        return new int[]{0};
      case 0b10:
        return new int[]{1};
      default:
        return new int[]{0,1};
    }
  }
  @Override public long[] toLongArray(){
    switch(this.state){
      case 0b00:
        return OmniArray.OfLong.DEFAULT_ARR;
      case 0b01:
        return new long[]{0L};
      case 0b10:
        return new long[]{1L};
      default:
        return new long[]{0L,1L};
    }
  }
  @Override public float[] toFloatArray(){
    switch(this.state){
      case 0b00:
        return OmniArray.OfFloat.DEFAULT_ARR;
      case 0b01:
        return new float[]{0F};
      case 0b10:
        return new float[]{1F};
      default:
        return new float[]{0F,1F};
    }
  }
  @Override public double[] toDoubleArray(){
    switch(this.state){
      case 0b00:
        return OmniArray.OfDouble.DEFAULT_ARR;
      case 0b01:
        return new double[]{0D};
      case 0b10:
        return new double[]{1D};
      default:
        return new double[]{0D,1D};
    }
  }
  @Override public OmniIterator.OfBoolean iterator(){
    final int state;
    if((state=this.state)==0b00){
      return EMPTY_ITR;
    }
    return new UncheckedAscendingFullItr(this,state);
  }
  @Override public OmniIterator.OfBoolean descendingIterator(){
    final int state;
    if((state=this.state)==0b00){
      return EMPTY_ITR;
    }
    return new UncheckedDescendingFullItr(this,state);
  }
  @Override public boolean firstBoolean(){
    if(this.state==0b10){
      return true;
    }
    return false;
  }
  @Override public boolean lastBoolean(){
    if(this.state==0b01){
      return false;
    }
    return true;
  }
  @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
    if(fromElement){
      return new UncheckedTrueView(this);
    }
    return this;
  }
  @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
    if(fromElement==inclusive){
      return new UncheckedTrueView(this);
    }else if(fromElement){
      return AbstractBooleanSet.UNCHECKED_EMPTY_ASCENDING;
    }
    return this;
  }
  @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement){
    if(toElement){
      return this;
    }
    return new UncheckedFalseView(this);
  }
  @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
    if(toElement ^ inclusive){
      return new UncheckedFalseView(this);
    }else if(toElement){
      return this;
    }else{
      return AbstractBooleanSet.UNCHECKED_EMPTY_ASCENDING;
    } 
  }
  @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
    if(fromElement){
      if(toElement){
        return AbstractBooleanSet.UNCHECKED_EMPTY_ASCENDING;
      }else{
        return new UncheckedTrueView(this);
      }
    }else{
      if(toElement){
        return new UncheckedFalseView(this);
      }else{
        return this;
      }
    } 
  }
  @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
    if(fromElement==fromInclusive){
      if(toElement && toInclusive){
        return new UncheckedTrueView(this);
      }
    }else if(toElement^toInclusive){
      if(fromInclusive){
        return new UncheckedFalseView(this);
      }
    }else if(toElement && !fromElement){
      return this;
    }
    return AbstractBooleanSet.UNCHECKED_EMPTY_ASCENDING;
  }
  public static class Checked extends BooleanSetImpl{
    private static final long serialVersionUID=1L;
    public Checked(){
      super();
    }
    public Checked(BooleanSetImpl that){
      super(that);
    }
    public Checked(OmniCollection.OfBoolean that){
      super(that);
    }
    public Checked(OmniCollection.OfRef<? extends Boolean> that){
      super(that);
    }
    public Checked(Collection<? extends Boolean> that){
      super(that);
    }
    Checked(int state){
      super(state);
    }
    @Override public Object clone(){
      return new Checked(this.state);
    }
    @Override public OmniNavigableSet.OfBoolean descendingSet(){
      return new DescendingView.Checked(this);
    }
    @Override public void forEach(BooleanConsumer action){
      final int state;
      switch(state=this.state){ 
        default:
          return;
        case 0b11:
          try{
            action.accept(false);
            action.accept(true);
          }finally{
            CheckedCollection.checkModCount(state,this.state);
          }
          break;
        case 0b01:
          try{
            action.accept(false);
          }finally{
            CheckedCollection.checkModCount(state,this.state);
          }
          break;
        case 0b10:
          try{
            action.accept(true);
          }finally{
            CheckedCollection.checkModCount(state,this.state);
          }
      }
    }
    @Override public void forEach(Consumer<? super Boolean> action){
      final int state;
      switch(state=this.state){ 
        default:
          return;
        case 0b11:
          try{
            action.accept(Boolean.FALSE);
            action.accept(Boolean.TRUE);
          }finally{
            CheckedCollection.checkModCount(state,this.state);
          }
          break;
        case 0b01:
          try{
            action.accept(Boolean.FALSE);
          }finally{
            CheckedCollection.checkModCount(state,this.state);
          }
          break;
        case 0b10:
          try{
            action.accept(Boolean.TRUE);
          }finally{
            CheckedCollection.checkModCount(state,this.state);
          }
      }
    }
    @Override public boolean removeIf(BooleanPredicate filter){
      final int state;
      switch(state=this.state){
        default:
          return false;
        case 0b11:
          final int newState;
          try{
            if(filter.test(false)){
              if(filter.test(true)){
                newState=0b00;
              }else{
                newState=0b10;
              }
            }else{
              if(filter.test(true)){
                newState=0b01;
              }else{
                return false;
              }
            }
          }finally{
            CheckedCollection.checkModCount(state,this.state);
          }
          this.state=newState;
          break;
        case 0b01:
          try{
            if(!filter.test(false)){
              return false;
            }
          }finally{
            CheckedCollection.checkModCount(state,this.state);
          }
          this.state=0b00;
          break;
        case 0b10:
          try{
            if(!filter.test(true)){
              return false;
            }
          }finally{
            CheckedCollection.checkModCount(state,this.state);
          }
          this.state=0b00;
      }
      return true;
    }
    @Override public boolean removeIf(Predicate<? super Boolean> filter){
      final int state;
      switch(state=this.state){
        default:
          return false;
        case 0b11:
          final int newState;
          try{
            if(filter.test(Boolean.FALSE)){
              if(filter.test(Boolean.TRUE)){
                newState=0b00;
              }else{
                newState=0b10;
              }
            }else{
              if(filter.test(Boolean.TRUE)){
                newState=0b01;
              }else{
                return false;
              }
            }
          }finally{
            CheckedCollection.checkModCount(state,this.state);
          }
          this.state=newState;
          break;
        case 0b01:
          try{
            if(!filter.test(Boolean.FALSE)){
              return false;
            }
          }finally{
            CheckedCollection.checkModCount(state,this.state);
          }
          this.state=0b00;
          break;
        case 0b10:
          try{
            if(!filter.test(Boolean.TRUE)){
              return false;
            }
          }finally{
            CheckedCollection.checkModCount(state,this.state);
          }
          this.state=0b00;
      }
      return true;
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      final int state=this.state;
      final T[] dst;
      try{
	      switch(state){
	        case 0b00:
	          return arrConstructor.apply(0);
	        case 0b01:
	          (dst=arrConstructor.apply(1))[0]=(T)Boolean.FALSE;
	          break;
	        case 0b10:
	          (dst=arrConstructor.apply(1))[0]=(T)Boolean.TRUE;
	          break;
	        default:
	          (dst=arrConstructor.apply(2))[0]=(T)Boolean.FALSE;
	          dst[1]=(T)Boolean.TRUE;
	      }
      }finally{
        CheckedCollection.checkModCount(state,this.state);
      }
      return dst;
    }
    @Override public void writeExternal(ObjectOutput oos) throws IOException{
      final int expectedState=this.state;
      try{
        super.writeExternal(oos);
      }finally{
        CheckedCollection.checkModCount(expectedState,this.state);
      }
    }
    @Override public OmniIterator.OfBoolean iterator(){
      final int state;
      if((state=this.state)==0b00){
        return EMPTY_ITR;
      }
      return new CheckedAscendingFullItr(this,state);
    }
    @Override public OmniIterator.OfBoolean descendingIterator(){
      final int state;
      if((state=this.state)==0b00){
        return EMPTY_ITR;
      }
      return new CheckedDescendingFullItr(this,state);
    }
    @Override public boolean firstBoolean(){
      switch(this.state){
        case 0b00:
          throw new NoSuchElementException();
        case 0b10:
          return true;
        default:
          return false;
      }
    }
    @Override public boolean lastBoolean(){
      switch(this.state){
        case 0b00:
          throw new NoSuchElementException();
        case 0b01:
          return false;
        default:
          return true;
      }
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
      if(fromElement){
        return new UncheckedTrueView.Checked(this);
      }
      return this;
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
      if(fromElement==inclusive){
        return new UncheckedTrueView.Checked(this);
      }else if(fromElement){
        return AbstractBooleanSet.CHECKED_EMPTY_ASCENDING_TAIL;
      }
      return this;
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement){
      if(toElement){
        return this;
      }
      return new UncheckedFalseView.Checked(this);
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
      if(toElement ^ inclusive){
        return new UncheckedFalseView.Checked(this);
      }else if(toElement){
        return this;
      }else{
        return AbstractBooleanSet.CHECKED_EMPTY_ASCENDING_HEAD;
      }
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
      if(fromElement){
        if(toElement){
          return AbstractBooleanSet.CHECKED_EMPTY_ASCENDING_MIDDLE;
        }else{
          return new UncheckedTrueView.Checked(this);
        }
      }else{
        if(toElement){
          return new UncheckedFalseView.Checked(this);
        }else{
          return this;
        }
      }
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
      if(fromElement==fromInclusive){
        if(toElement^toInclusive){
          return AbstractBooleanSet.CHECKED_EMPTY_ASCENDING_MIDDLE;
        }else if(toElement){
          return new UncheckedTrueView.Checked(this);
        }
      }else if(toElement^toInclusive){
        if(fromInclusive){
          return new UncheckedFalseView.Checked(this);
        }
      }else{
        if(toElement){
          if(fromElement){
            return AbstractBooleanSet.CHECKED_EMPTY_ASCENDING_TAIL;
          }else{
            return this;
          }
        }else if(fromElement){
          return AbstractBooleanSet.CHECKED_EMPTY_ASCENDING_HEAD;
        }
      }
      throw new IllegalArgumentException("out of bounds");
    }
  }
  public static class Descending extends BooleanSetImpl{
    private static final long serialVersionUID=1L;
    public Descending(){
      super();
    }
    public Descending(BooleanSetImpl that){
      super(that);
    }
    public Descending(OmniCollection.OfBoolean that){
      super(that);
    }
    public Descending(OmniCollection.OfRef<? extends Boolean> that){
      super(that);
    }
    public Descending(Collection<? extends Boolean> that){
      super(that);
    }
    Descending(int state){
      super(state);
    }
    @Override public Object clone(){
      return new Descending(this.state);
    }
    @Override public BooleanComparator comparator(){
      return BooleanComparator::descendingCompare;
    }
    @Override public String toString(){
      switch(this.state){
        case 0b00:
          return "[]";
        case 0b01:
          return "[false]";
        case 0b10:
          return "[true]";
        default:
          return "[true, false]";
      }
    }
    @Override public void forEach(BooleanConsumer action){
      switch(this.state){
        case 0b11:
          action.accept(true);
        case 0b01:
          action.accept(false);
          break;
        case 0b10:
          action.accept(true);
        default:
      }
    }
    @Override public void forEach(Consumer<? super Boolean> action){
      switch(this.state){
        case 0b11:
          action.accept(Boolean.TRUE);
        case 0b01:
          action.accept(Boolean.FALSE);
          break;
        case 0b10:
          action.accept(Boolean.TRUE);
        default:
      }
    }
    @SuppressWarnings("unchecked")
    @Override public <T> T[] toArray(T[] dst){
      switch(this.state){
      case 0b11:
        (dst=OmniArray.uncheckedArrResize(2,dst))[0]=(T)Boolean.TRUE;
        dst[1]=(T)Boolean.FALSE;
        break;
      case 0b10:
        (dst=OmniArray.uncheckedArrResize(1,dst))[0]=(T)Boolean.TRUE;
        break;
      case 0b01:
        (dst=OmniArray.uncheckedArrResize(1,dst))[0]=(T)Boolean.FALSE;
        break;
      default:
        if(dst.length!=0){
          dst[0]=null;
        }
      }
      return dst;
    }
    @SuppressWarnings("unchecked")
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      final T[] dst;
      switch(this.state){
      case 0b11:
        (dst=arrConstructor.apply(2))[0]=(T)Boolean.TRUE;
        dst[1]=(T)Boolean.FALSE;
        break;
      case 0b10:
        (dst=arrConstructor.apply(1))[0]=(T)Boolean.TRUE;
        break;
      case 0b01:
        (dst=arrConstructor.apply(1))[0]=(T)Boolean.FALSE;
        break;
      default:
        return arrConstructor.apply(0);
      }
      return dst;
    }
    @Override public Boolean[] toArray(){
      switch(this.state){
        case 0b00:
          return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
        case 0b01:
          return new Boolean[]{Boolean.FALSE};
        case 0b10:
          return new Boolean[]{Boolean.TRUE};
        default:
          return new Boolean[]{Boolean.TRUE,Boolean.FALSE};
      }
    }
    @Override public boolean[] toBooleanArray(){
      switch(this.state){
        case 0b00:
          return OmniArray.OfBoolean.DEFAULT_ARR;
        case 0b01:
          return new boolean[]{false};
        case 0b10:
          return new boolean[]{true};
        default:
          return new boolean[]{true,false};
      }
    }
    @Override public byte[] toByteArray(){
      switch(this.state){
        case 0b00:
          return OmniArray.OfByte.DEFAULT_ARR;
        case 0b01:
          return new byte[]{(byte)0};
        case 0b10:
          return new byte[]{(byte)1};
        default:
          return new byte[]{(byte)1,(byte)0};
      }
    }
    @Override public char[] toCharArray(){
      switch(this.state){
        case 0b00:
          return OmniArray.OfChar.DEFAULT_ARR;
        case 0b01:
          return new char[]{(char)0};
        case 0b10:
          return new char[]{(char)1};
        default:
          return new char[]{(char)1,(char)0};
      }
    }
    @Override public short[] toShortArray(){
      switch(this.state){
        case 0b00:
          return OmniArray.OfShort.DEFAULT_ARR;
        case 0b01:
          return new short[]{(short)0};
        case 0b10:
          return new short[]{(short)1};
        default:
          return new short[]{(short)1,(short)0};
      }
    }
    @Override public int[] toIntArray(){
      switch(this.state){
        case 0b00:
          return OmniArray.OfInt.DEFAULT_ARR;
        case 0b01:
          return new int[]{0};
        case 0b10:
          return new int[]{1};
        default:
          return new int[]{1,0};
      }
    }
    @Override public long[] toLongArray(){
      switch(this.state){
        case 0b00:
          return OmniArray.OfLong.DEFAULT_ARR;
        case 0b01:
          return new long[]{0L};
        case 0b10:
          return new long[]{1L};
        default:
          return new long[]{1L,0L};
      }
    }
    @Override public float[] toFloatArray(){
      switch(this.state){
        case 0b00:
          return OmniArray.OfFloat.DEFAULT_ARR;
        case 0b01:
          return new float[]{0F};
        case 0b10:
          return new float[]{1F};
        default:
          return new float[]{1F,0F};
      }
    }
    @Override public double[] toDoubleArray(){
      switch(this.state){
        case 0b00:
          return OmniArray.OfDouble.DEFAULT_ARR;
        case 0b01:
          return new double[]{0D};
        case 0b10:
          return new double[]{1D};
        default:
          return new double[]{1D,0D};
      }
    }
    @Override public Boolean ceiling(boolean val){
      return super.floor(val); 
    }
    @Override public Boolean floor(boolean val){
      return super.ceiling(val); 
    }
    @Override public Boolean lower(boolean val){
      return super.higher(val);
    }
    @Override public Boolean higher(boolean val){
      return super.lower(val);
    }
    @Override public Boolean pollFirst(){
      return super.pollLast();
    }
    @Override public Boolean pollLast(){
      return super.pollFirst();
    }
    @Override public boolean booleanCeiling(boolean val){
      return super.booleanFloor(val);
    }
    @Override public boolean booleanFloor(boolean val){
      return super.booleanCeiling(val);
    }
    @Override public boolean lowerBoolean(boolean val){
      return super.higherBoolean(val);
    }
    @Override public boolean higherBoolean(boolean val){
      return super.lowerBoolean(val);
    }
    @Override public boolean pollFirstBoolean(){
      return super.pollLastBoolean();
    }
    @Override public boolean pollLastBoolean(){
      return super.pollFirstBoolean();
    }
    @Override public byte byteCeiling(byte val){
      return super.byteFloor(val);
    }
    @Override public byte byteFloor(byte val){
      return super.byteCeiling(val);
    }
    @Override public byte lowerByte(byte val){
      return super.higherByte(val);
    }
    @Override public byte higherByte(byte val){
      return super.lowerByte(val);
    }
    @Override public byte pollFirstByte(){
      return super.pollLastByte();
    }
    @Override public byte pollLastByte(){
      return super.pollFirstByte();
    }
    @Override public char charCeiling(char val){
      return super.charFloor(val);
    }
    @Override public char charFloor(char val){
      return super.charCeiling(val);
    }
    @Override public char lowerChar(char val){
      return super.higherChar(val);
    }
    @Override public char higherChar(char val){
      return super.lowerChar(val);
    }
    @Override public char pollFirstChar(){
      return super.pollLastChar();
    }
    @Override public char pollLastChar(){
      return super.pollFirstChar();
    }
    @Override public short shortCeiling(short val){
      return super.shortFloor(val);
    }
    @Override public short shortFloor(short val){
      return super.shortCeiling(val);
    }
    @Override public short lowerShort(short val){
      return super.higherShort(val);
    }
    @Override public short higherShort(short val){
      return super.lowerShort(val);
    }
    @Override public short pollFirstShort(){
      return super.pollLastShort();
    }
    @Override public short pollLastShort(){
      return super.pollFirstShort();
    }
    @Override public int intCeiling(int val){
      return super.intFloor(val);
    }
    @Override public int intFloor(int val){
      return super.intCeiling(val);
    }
    @Override public int lowerInt(int val){
      return super.higherInt(val);
    }
    @Override public int higherInt(int val){
      return super.lowerInt(val);
    }
    @Override public int pollFirstInt(){
      return super.pollLastInt();
    }
    @Override public int pollLastInt(){
      return super.pollFirstInt();
    }
    @Override public long longCeiling(long val){
      return super.longFloor(val);
    }
    @Override public long longFloor(long val){
      return super.longCeiling(val);
    }
    @Override public long lowerLong(long val){
      return super.higherLong(val);
    }
    @Override public long higherLong(long val){
      return super.lowerLong(val);
    }
    @Override public long pollFirstLong(){
      return super.pollLastLong();
    }
    @Override public long pollLastLong(){
      return super.pollFirstLong();
    }
    @Override public float floatCeiling(float val){
      return super.floatFloor(val);
    }
    @Override public float floatFloor(float val){
      return super.floatCeiling(val);
    }
    @Override public float lowerFloat(float val){
      return super.higherFloat(val);
    }
    @Override public float higherFloat(float val){
      return super.lowerFloat(val);
    }
    @Override public float pollFirstFloat(){
      return super.pollLastFloat();
    }
    @Override public float pollLastFloat(){
      return super.pollFirstFloat();
    }
    @Override public double doubleCeiling(double val){
      return super.doubleFloor(val);
    }
    @Override public double doubleFloor(double val){
      return super.doubleCeiling(val);
    }
    @Override public double lowerDouble(double val){
      return super.higherDouble(val);
    }
    @Override public double higherDouble(double val){
      return super.lowerDouble(val);
    }
    @Override public double pollFirstDouble(){
      return super.pollLastDouble();
    }
    @Override public double pollLastDouble(){
      return super.pollFirstDouble();
    }
    @Override public OmniIterator.OfBoolean iterator(){
      final int state;
      if((state=this.state)==0b00){
        return EMPTY_ITR;
      }
      return new UncheckedDescendingFullItr(this,state);
    }
    @Override public OmniIterator.OfBoolean descendingIterator(){
      final int state;
      if((state=this.state)==0b00){
        return EMPTY_ITR;
      }
      return new UncheckedAscendingFullItr(this,state);
    }
    @Override public boolean firstBoolean(){
      if(this.state==0b01){
        return false;
      }
      return true;
    }
    @Override public boolean lastBoolean(){
      if(this.state==0b10){
        return true;
      }
      return false;
    }
    @Override public OmniNavigableSet.OfBoolean descendingSet(){
      return new AscendingView(this);
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
      if(fromElement){
        return this;
      }
      return new UncheckedFalseView.Descending(this);
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
      if(fromElement^inclusive){
        return new UncheckedFalseView.Descending(this);
      }else if(fromElement){
        return this;
      }
      return AbstractBooleanSet.UNCHECKED_EMPTY_DESCENDING;
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement){
      if(toElement){
        return new UncheckedTrueView.Descending(this);
      }
      return this;
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
      if(toElement==inclusive){
        return new UncheckedTrueView.Descending(this);
      }else if(toElement){
        return AbstractBooleanSet.UNCHECKED_EMPTY_DESCENDING;
      }else{
        return this;
      }  
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
      if(fromElement){
        if(toElement){
          return new UncheckedTrueView.Descending(this);
        }else{
          return this;
        }
      }else{
        if(toElement){
          return AbstractBooleanSet.UNCHECKED_EMPTY_DESCENDING;
        }else{
          return new UncheckedFalseView.Descending(this);
        }
      }  
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
      if(fromElement^fromInclusive){
        if(toInclusive && !toElement){
          return new UncheckedFalseView.Descending(this);
        }
      }else if(fromElement){
        if(toElement==toInclusive){
          return new UncheckedTrueView.Descending(this);
        }else if(toInclusive){
          return this;
        }
      }
      return AbstractBooleanSet.UNCHECKED_EMPTY_DESCENDING;
    }
    public static class Checked extends Descending{
      private static final long serialVersionUID=1L;
      public Checked(){
        super();
      }
      public Checked(BooleanSetImpl that){
        super(that);
      }
      public Checked(OmniCollection.OfBoolean that){
        super(that);
      }
      public Checked(OmniCollection.OfRef<? extends Boolean> that){
        super(that);
      }
      public Checked(Collection<? extends Boolean> that){
        super(that);
      }
      Checked(int state){
        super(state);
      }
      @Override public Object clone(){
        return new Checked(this.state);
      }
      @Override public OmniIterator.OfBoolean iterator(){
        final int state;
        if((state=this.state)==0b00){
          return EMPTY_ITR;
        }
        return new CheckedDescendingFullItr(this,state);
      }
      @Override public void forEach(BooleanConsumer action){
        final int state;
        switch(state=this.state){ 
          default:
            return;
          case 0b11:
            try{
              action.accept(true);
              action.accept(false);
            }finally{
              CheckedCollection.checkModCount(state,this.state);
            }
            break;
          case 0b01:
            try{
              action.accept(false);
            }finally{
              CheckedCollection.checkModCount(state,this.state);
            }
            break;
          case 0b10:
            try{
              action.accept(true);
            }finally{
              CheckedCollection.checkModCount(state,this.state);
            }
        }
      }
      @Override public void forEach(Consumer<? super Boolean> action){
        final int state;
        switch(state=this.state){ 
          default:
            return;
          case 0b11:
            try{
              action.accept(Boolean.TRUE);
              action.accept(Boolean.FALSE);
            }finally{
              CheckedCollection.checkModCount(state,this.state);
            }
            break;
          case 0b01:
            try{
              action.accept(Boolean.FALSE);
            }finally{
              CheckedCollection.checkModCount(state,this.state);
            }
            break;
          case 0b10:
            try{
              action.accept(Boolean.TRUE);
            }finally{
              CheckedCollection.checkModCount(state,this.state);
            }
        }
      }
      @Override public boolean removeIf(BooleanPredicate filter){
        final int state;
        switch(state=this.state){
          default:
            return false;
          case 0b11:
            final int newState;
            try{
              if(filter.test(false)){
                if(filter.test(true)){
                  newState=0b00;
                }else{
                  newState=0b10;
                }
              }else{
                if(filter.test(true)){
                  newState=0b01;
                }else{
                  return false;
                }
              }
            }finally{
              CheckedCollection.checkModCount(state,this.state);
            }
            this.state=newState;
            break;
          case 0b01:
            try{
              if(!filter.test(false)){
                return false;
              }
            }finally{
              CheckedCollection.checkModCount(state,this.state);
            }
            this.state=0b00;
            break;
          case 0b10:
            try{
              if(!filter.test(true)){
                return false;
              }
            }finally{
              CheckedCollection.checkModCount(state,this.state);
            }
            this.state=0b00;
        }
        return true;
      }
      @Override public boolean removeIf(Predicate<? super Boolean> filter){
        final int state;
        switch(state=this.state){
          default:
            return false;
          case 0b11:
            final int newState;
            try{
              if(filter.test(Boolean.FALSE)){
                if(filter.test(Boolean.TRUE)){
                  newState=0b00;
                }else{
                  newState=0b10;
                }
              }else{
                if(filter.test(Boolean.TRUE)){
                  newState=0b01;
                }else{
                  return false;
                }
              }
            }finally{
              CheckedCollection.checkModCount(state,this.state);
            }
            this.state=newState;
            break;
          case 0b01:
            try{
              if(!filter.test(Boolean.FALSE)){
                return false;
              }
            }finally{
              CheckedCollection.checkModCount(state,this.state);
            }
            this.state=0b00;
            break;
          case 0b10:
            try{
              if(!filter.test(Boolean.TRUE)){
                return false;
              }
            }finally{
              CheckedCollection.checkModCount(state,this.state);
            }
            this.state=0b00;
        }
        return true;
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
	      final int state=this.state;
	      final T[] dst;
	      try{
		      switch(state){
		        case 0b00:
		          return arrConstructor.apply(0);
		        case 0b01:
		          (dst=arrConstructor.apply(1))[0]=(T)Boolean.FALSE;
		          break;
		        case 0b10:
		          (dst=arrConstructor.apply(1))[0]=(T)Boolean.TRUE;
		          break;
		        default:
		          (dst=arrConstructor.apply(2))[0]=(T)Boolean.TRUE;
		          dst[1]=(T)Boolean.FALSE;
		      }
	      }finally{
	        CheckedCollection.checkModCount(state,this.state);
	      }
	      return dst;
	    }
      @Override public void writeExternal(ObjectOutput oos) throws IOException{
        final int expectedState=this.state;
        try{
          super.writeExternal(oos);
        }finally{
          CheckedCollection.checkModCount(expectedState,this.state);
        }
      }
      @Override public OmniNavigableSet.OfBoolean descendingSet(){
        return new AscendingView.Checked(this);
      }
      @Override public OmniIterator.OfBoolean descendingIterator(){
        final int state;
        if((state=this.state)==0b00){
          return EMPTY_ITR;
        }
        return new CheckedAscendingFullItr(this,state);
      }
      @Override public boolean firstBoolean(){
        switch(this.state){
          case 0b00:
            throw new NoSuchElementException();
          case 0b01:
            return false;
          default:
            return true;
        }
      }
      @Override public boolean lastBoolean(){
        switch(this.state){
          case 0b00:
            throw new NoSuchElementException();
          case 0b10:
            return true;
          default:
            return false;
        }
      }
      @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
        if(fromElement){
          return this;
        }
        return new UncheckedFalseView.Checked.Descending(this);
      }
      @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
        if(fromElement^inclusive){
          return new UncheckedFalseView.Checked.Descending(this);
        }else if(fromElement){
          return this;
        }
        return AbstractBooleanSet.CHECKED_EMPTY_DESCENDING_TAIL;
      }
      @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement){
        if(toElement){
          return new UncheckedTrueView.Checked.Descending(this);
        }
        return this;
      }
      @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
        if(toElement==inclusive){
          return new UncheckedTrueView.Checked.Descending(this);
        }else if(toElement){
          return AbstractBooleanSet.CHECKED_EMPTY_DESCENDING_HEAD;
        }else{
          return this;
        }
      }
      @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
        if(fromElement){
          if(toElement){
            return new UncheckedTrueView.Checked.Descending(this);
          }else{
            return this;
          }
        }else{
          if(toElement){
            return AbstractBooleanSet.CHECKED_EMPTY_DESCENDING_MIDDLE;
          }else{
            return new UncheckedFalseView.Checked.Descending(this);
          }
        }
      }
      @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
        if(fromElement^fromInclusive){
          if(toElement==toInclusive){
            return AbstractBooleanSet.CHECKED_EMPTY_DESCENDING_MIDDLE;
          }else if(toInclusive){
            return new UncheckedFalseView.Checked.Descending(this);
          }
        }else if(toElement==toInclusive){
          if(fromElement){
            return new UncheckedTrueView.Checked.Descending(this);
          }
        }else if(fromInclusive){
          if(toElement){
            return AbstractBooleanSet.CHECKED_EMPTY_DESCENDING_HEAD;
          }else{
            return this;
          }
        }else if(toInclusive){
          return AbstractBooleanSet.CHECKED_EMPTY_DESCENDING_TAIL;
        }
        throw new IllegalArgumentException("out of bounds");  
      }
    }
  }
  @Override public void writeExternal(ObjectOutput oos) throws IOException{
    oos.writeByte(this.state);
  }
  @Override public void readExternal(ObjectInput ois) throws IOException{
    this.state=ois.readUnsignedByte();
  }
  private static boolean equalsFullState(Set<?> val){
    if(val.size()==2){
      if(val instanceof OmniSet){
        if(val instanceof AbstractBooleanSet){
          return true;
        }else if(val instanceof OmniSet.OfRef){
          final OmniSet.OfRef<?> that;
          return (that=(OmniSet.OfRef<?>)val).contains(false) && that.contains(true);
        }
      }else{
        return val.contains(Boolean.FALSE) && val.contains(Boolean.TRUE);
      }
    }
    return false;
  }
  private static boolean equalsTrueState(Set<?> val){
    if(val.size()==1){
      if(val instanceof OmniSet){
        if(val instanceof AbstractBooleanSet){
          return ((AbstractBooleanSet)val).containsTrue();
        }else if(val instanceof OmniSet.OfRef){
          return ((OmniSet.OfRef<?>)val).contains(true);
        }
      }else{
        return val.contains(Boolean.TRUE);
      }
    }
    return false;
  }
  private static boolean equalsFalseState(Set<?> val){
    if(val.size()==1){
      if(val instanceof OmniSet){
        if(val instanceof AbstractBooleanSet){
          return ((AbstractBooleanSet)val).containsFalse();
        }else if(val instanceof OmniSet.OfRef){
          return ((OmniSet.OfRef<?>)val).contains(false);
        }
      }else{
        return val.contains(Boolean.FALSE);
      }
    }
    return false;
  }
  private static abstract class AbstractFullView extends AbstractBooleanSet implements Serializable,Cloneable{
    private static final long serialVersionUID=1L;
    transient final BooleanSetImpl root;
    private AbstractFullView(BooleanSetImpl root){
      this.root=root;
    }
    @Override boolean addTrue(){
      final int state;
      final BooleanSetImpl root;
      if(((state=(root=this.root).state)&0b10)==0){
        root.state=state+0b10;
        return true;
      }
      return false;
    }
    @Override boolean containsTrue(){
      return (root.state&0b10)!=0;
    }
    @Override boolean removeTrue(){
      final int state;
      final BooleanSetImpl root;
      if(((state=(root=this.root).state)&0b10)!=0){
        root.state=state-0b10;
        return true;
      }
      return false;
    }
    @Override boolean addFalse(){
      final int state;
      final BooleanSetImpl root;
      if(((state=(root=this.root).state)&0b01)==0){
        root.state=state+0b01;
        return true;
      }
      return false;
    }
    @Override boolean containsFalse(){
      return (root.state&0b01)!=0;
    }
    @Override boolean removeFalse(){
      final int state;
      final BooleanSetImpl root;
      if(((state=(root=this.root).state)&0b01)!=0){
        root.state=state-0b01;
        return true;
      }
      return false;
    }
    @Override public boolean removeIf(BooleanPredicate filter){
      final BooleanSetImpl root;
      switch((root=this.root).state){
        case 0b11:
          if(filter.test(false)){
            if(filter.test(true)){
              break;
            }else{
              root.state=0b10;
              return true;
            }
          }else if(filter.test(true)){
            root.state=0b01;
            return true;
          }
          return false;
        case 0b10:
          if(filter.test(true)){
            break;
          }
          return false;
        case 0b01:
          if(filter.test(false)){
            break;
          }
        default:
          return false;
      }
      root.state=0b00;
      return true;
    }
    @Override public boolean removeIf(Predicate<? super Boolean> filter){
      final BooleanSetImpl root;
      switch((root=this.root).state){
        case 0b11:
          if(filter.test(Boolean.FALSE)){
            if(filter.test(Boolean.TRUE)){
              break;
            }else{
              root.state=0b10;
              return true;
            }
          }else if(filter.test(Boolean.TRUE)){
            root.state=0b01;
            return true;
          }
          return false;
        case 0b10:
          if(filter.test(Boolean.TRUE)){
            break;
          }
          return false;
        case 0b01:
          if(filter.test(Boolean.FALSE)){
            break;
          }
        default:
          return false;
      }
      root.state=0b00;
      return true;
    }
    @Override public int size(){
      switch(root.state){
        case 0b00:
          return 0;
        case 0b01:
        case 0b10:
          return 1;
        default:
          return 2;
      }
    }
    @Override public void clear(){
      root.state=0b00;
    }
    @Override public int hashCode(){
      switch(root.state){
        case 0b00:
          return 0;
        case 0b01:
          return 1237;
        case 0b10:
          return 1231;
        default:
          return 1231+1237;
      }
    }
    @Override public boolean isEmpty(){
      return root.state==0b00;
    }
    @Override public boolean equals(Object val){
    if(val==this||val==root){
        return true;
      }
      if(val instanceof Set){
        switch(root.state){
          case 0b00:
            return ((Set<?>)val).isEmpty();
          case 0b01:
            return equalsFalseState((Set<?>)val);
          case 0b10:
            return equalsTrueState((Set<?>)val);
          default:
            return equalsFullState((Set<?>)val);
        }
      }
      return false;
    }
  }
  private static class DescendingView extends AbstractFullView{
    private static final long serialVersionUID=1L;
    private DescendingView(BooleanSetImpl root){
      super(root);
    }
    @Override public Object clone(){
      return new Descending(root.state);
    }
    private Object writeReplace(){
      return new Descending(root.state);
    }
    @Override public OmniNavigableSet.OfBoolean descendingSet(){
      return root;
    }
    @Override public BooleanComparator comparator(){
      return BooleanComparator::descendingCompare;
    }
    @SuppressWarnings("unchecked")
    @Override public <T> T[] toArray(T[] dst){
      switch(root.state){
      case 0b11:
        (dst=OmniArray.uncheckedArrResize(2,dst))[0]=(T)Boolean.TRUE;
        dst[1]=(T)Boolean.FALSE;
        break;
      case 0b10:
        (dst=OmniArray.uncheckedArrResize(1,dst))[0]=(T)Boolean.TRUE;
        break;
      case 0b01:
        (dst=OmniArray.uncheckedArrResize(1,dst))[0]=(T)Boolean.FALSE;
        break;
      default:
        if(dst.length!=0){
          dst[0]=null;
        }
      }
      return dst;
    }
    @SuppressWarnings("unchecked")
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      final T[] dst;
      switch(root.state){
      case 0b11:
        (dst=arrConstructor.apply(2))[0]=(T)Boolean.TRUE;
        dst[1]=(T)Boolean.FALSE;
        break;
      case 0b10:
        (dst=arrConstructor.apply(1))[0]=(T)Boolean.TRUE;
        break;
      case 0b01:
        (dst=arrConstructor.apply(1))[0]=(T)Boolean.FALSE;
        break;
      default:
        return arrConstructor.apply(0);
      }
      return dst;
    }
    @Override public Boolean[] toArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
        case 0b01:
          return new Boolean[]{Boolean.FALSE};
        case 0b10:
          return new Boolean[]{Boolean.TRUE};
        default:
          return new Boolean[]{Boolean.TRUE,Boolean.FALSE};
      }
    }
    @Override public boolean[] toBooleanArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfBoolean.DEFAULT_ARR;
        case 0b01:
          return new boolean[]{false};
        case 0b10:
          return new boolean[]{true};
        default:
          return new boolean[]{true,false};
      }
    }
    @Override public byte[] toByteArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfByte.DEFAULT_ARR;
        case 0b01:
          return new byte[]{(byte)0};
        case 0b10:
          return new byte[]{(byte)1};
        default:
          return new byte[]{(byte)1,(byte)0};
      }
    }
    @Override public char[] toCharArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfChar.DEFAULT_ARR;
        case 0b01:
          return new char[]{(char)0};
        case 0b10:
          return new char[]{(char)1};
        default:
          return new char[]{(char)1,(char)0};
      }
    }
    @Override public short[] toShortArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfShort.DEFAULT_ARR;
        case 0b01:
          return new short[]{(short)0};
        case 0b10:
          return new short[]{(short)1};
        default:
          return new short[]{(short)1,(short)0};
      }
    }
    @Override public int[] toIntArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfInt.DEFAULT_ARR;
        case 0b01:
          return new int[]{0};
        case 0b10:
          return new int[]{1};
        default:
          return new int[]{1,0};
      }
    }
    @Override public long[] toLongArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfLong.DEFAULT_ARR;
        case 0b01:
          return new long[]{0L};
        case 0b10:
          return new long[]{1L};
        default:
          return new long[]{1L,0L};
      }
    }
    @Override public float[] toFloatArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfFloat.DEFAULT_ARR;
        case 0b01:
          return new float[]{0F};
        case 0b10:
          return new float[]{1F};
        default:
          return new float[]{1F,0F};
      }
    }
    @Override public double[] toDoubleArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfDouble.DEFAULT_ARR;
        case 0b01:
          return new double[]{0D};
        case 0b10:
          return new double[]{1D};
        default:
          return new double[]{1D,0D};
      }
    }
    @Override public void forEach(BooleanConsumer action){
      switch(root.state){
        case 0b11:
          action.accept(true);
        case 0b01:
          action.accept(false);
          break;
        case 0b10:
          action.accept(true);
        default:
      }
    }
    @Override public void forEach(Consumer<? super Boolean> action){
      switch(root.state){
        case 0b11:
          action.accept(Boolean.TRUE);
        case 0b01:
          action.accept(Boolean.FALSE);
          break;
        case 0b10:
          action.accept(Boolean.TRUE);
        default:
      }
    }
    @Override public boolean firstBoolean(){
      if(root.state==0b01){
        return false;
      }
      return true;
    }
    @Override public boolean lastBoolean(){
      if(root.state==0b10){
        return true;
      }
      return false;
    }
    @Override public String toString(){
      switch(root.state){
        case 0b00:
          return "[]";
        case 0b01:
          return "[false]";
        case 0b10:
          return "[true]";
        default:
          return "[true, false]";
      }
    }
    @Override public Boolean ceiling(boolean val){
      return root.floor(val); 
    }
    @Override public Boolean floor(boolean val){
      return root.ceiling(val); 
    }
    @Override public Boolean lower(boolean val){
      return root.higher(val);
    }
    @Override public Boolean higher(boolean val){
      return root.lower(val);
    }
    @Override public Boolean pollFirst(){
      return root.pollLast();
    }
    @Override public Boolean pollLast(){
      return root.pollFirst();
    }
    @Override public boolean booleanCeiling(boolean val){
      return root.booleanFloor(val);
    }
    @Override public boolean booleanFloor(boolean val){
      return root.booleanCeiling(val);
    }
    @Override public boolean lowerBoolean(boolean val){
      return root.higherBoolean(val);
    }
    @Override public boolean higherBoolean(boolean val){
      return root.lowerBoolean(val);
    }
    @Override public boolean pollFirstBoolean(){
      return root.pollLastBoolean();
    }
    @Override public boolean pollLastBoolean(){
      return root.pollFirstBoolean();
    }
    @Override public byte byteCeiling(byte val){
      return root.byteFloor(val);
    }
    @Override public byte byteFloor(byte val){
      return root.byteCeiling(val);
    }
    @Override public byte lowerByte(byte val){
      return root.higherByte(val);
    }
    @Override public byte higherByte(byte val){
      return root.lowerByte(val);
    }
    @Override public byte pollFirstByte(){
      return root.pollLastByte();
    }
    @Override public byte pollLastByte(){
      return root.pollFirstByte();
    }
    @Override public char charCeiling(char val){
      return root.charFloor(val);
    }
    @Override public char charFloor(char val){
      return root.charCeiling(val);
    }
    @Override public char lowerChar(char val){
      return root.higherChar(val);
    }
    @Override public char higherChar(char val){
      return root.lowerChar(val);
    }
    @Override public char pollFirstChar(){
      return root.pollLastChar();
    }
    @Override public char pollLastChar(){
      return root.pollFirstChar();
    }
    @Override public short shortCeiling(short val){
      return root.shortFloor(val);
    }
    @Override public short shortFloor(short val){
      return root.shortCeiling(val);
    }
    @Override public short lowerShort(short val){
      return root.higherShort(val);
    }
    @Override public short higherShort(short val){
      return root.lowerShort(val);
    }
    @Override public short pollFirstShort(){
      return root.pollLastShort();
    }
    @Override public short pollLastShort(){
      return root.pollFirstShort();
    }
    @Override public int intCeiling(int val){
      return root.intFloor(val);
    }
    @Override public int intFloor(int val){
      return root.intCeiling(val);
    }
    @Override public int lowerInt(int val){
      return root.higherInt(val);
    }
    @Override public int higherInt(int val){
      return root.lowerInt(val);
    }
    @Override public int pollFirstInt(){
      return root.pollLastInt();
    }
    @Override public int pollLastInt(){
      return root.pollFirstInt();
    }
    @Override public long longCeiling(long val){
      return root.longFloor(val);
    }
    @Override public long longFloor(long val){
      return root.longCeiling(val);
    }
    @Override public long lowerLong(long val){
      return root.higherLong(val);
    }
    @Override public long higherLong(long val){
      return root.lowerLong(val);
    }
    @Override public long pollFirstLong(){
      return root.pollLastLong();
    }
    @Override public long pollLastLong(){
      return root.pollFirstLong();
    }
    @Override public float floatCeiling(float val){
      return root.floatFloor(val);
    }
    @Override public float floatFloor(float val){
      return root.floatCeiling(val);
    }
    @Override public float lowerFloat(float val){
      return root.higherFloat(val);
    }
    @Override public float higherFloat(float val){
      return root.lowerFloat(val);
    }
    @Override public float pollFirstFloat(){
      return root.pollLastFloat();
    }
    @Override public float pollLastFloat(){
      return root.pollFirstFloat();
    }
    @Override public double doubleCeiling(double val){
      return root.doubleFloor(val);
    }
    @Override public double doubleFloor(double val){
      return root.doubleCeiling(val);
    }
    @Override public double lowerDouble(double val){
      return root.higherDouble(val);
    }
    @Override public double higherDouble(double val){
      return root.lowerDouble(val);
    }
    @Override public double pollFirstDouble(){
      return root.pollLastDouble();
    }
    @Override public double pollLastDouble(){
      return root.pollFirstDouble();
    }
    @Override public OmniIterator.OfBoolean iterator(){
      final BooleanSetImpl root;
      final int rootState;
      if((rootState=(root=this.root).state)==0b00){
        return EMPTY_ITR;
      }
      return new UncheckedDescendingFullItr(root,rootState);
    }
    @Override public OmniIterator.OfBoolean descendingIterator(){
      final BooleanSetImpl root;
      final int rootState;
      if((rootState=(root=this.root).state)==0b00){
        return EMPTY_ITR;
      }
      return new UncheckedAscendingFullItr(root,rootState);
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
      if(fromElement){
        return this;
      }
      return new UncheckedFalseView.Descending(root);
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
      if(fromElement^inclusive){
        return new UncheckedFalseView.Descending(root);
      }else if(fromElement){
        return this;
      }
      return AbstractBooleanSet.UNCHECKED_EMPTY_DESCENDING;
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement){
      if(toElement){
        return new UncheckedTrueView.Descending(root);
      }
      return this;
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
      if(toElement==inclusive){
        return new UncheckedTrueView.Descending(root);
      }else if(toElement){
        return AbstractBooleanSet.UNCHECKED_EMPTY_DESCENDING;
      }else{
        return this;
      }  
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
      if(fromElement){
        if(toElement){
          return new UncheckedTrueView.Descending(root);
        }else{
          return this;
        }
      }else{
        if(toElement){
          return AbstractBooleanSet.UNCHECKED_EMPTY_DESCENDING;
        }else{
          return new UncheckedFalseView.Descending(root);
        }
      }  
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
      if(fromElement^fromInclusive){
        if(toInclusive && !toElement){
          return new UncheckedFalseView.Descending(root);
        }
      }else if(fromElement){
        if(toElement==toInclusive){
          return new UncheckedTrueView.Descending(root);
        }else if(toInclusive){
          return this;
        }
      }
      return AbstractBooleanSet.UNCHECKED_EMPTY_DESCENDING;
    }
    private static class Checked extends DescendingView{
      private static final long serialVersionUID=1L;
      private Checked(BooleanSetImpl root){
        super(root);
      }
      @Override public OmniNavigableSet.OfBoolean descendingSet(){
        return root;
      }
      @Override public void forEach(BooleanConsumer action){
        final int state;
        final BooleanSetImpl root;
        switch(state=(root=this.root).state){
          default:
            return;
          case 0b11:
            try{
              action.accept(true);
              action.accept(false);
            }finally{
              CheckedCollection.checkModCount(state,root.state);
            }
            break;
          case 0b01:
            try{
              action.accept(false);
            }finally{
              CheckedCollection.checkModCount(state,root.state);
            }
            break;
          case 0b10:
            try{
              action.accept(true);
            }finally{
              CheckedCollection.checkModCount(state,root.state);
            }
        }
      }
      @Override public void forEach(Consumer<? super Boolean> action){
        final int state;
        final BooleanSetImpl root;
        switch(state=(root=this.root).state){
          default:
            return;
          case 0b11:
            try{
              action.accept(Boolean.TRUE);
              action.accept(Boolean.FALSE);
            }finally{
              CheckedCollection.checkModCount(state,root.state);
            }
            break;
          case 0b01:
            try{
              action.accept(Boolean.FALSE);
            }finally{
              CheckedCollection.checkModCount(state,root.state);
            }
            break;
          case 0b10:
            try{
              action.accept(Boolean.TRUE);
            }finally{
              CheckedCollection.checkModCount(state,root.state);
            }
        }
      }
      @Override public boolean removeIf(BooleanPredicate filter){
        final int state;
        final BooleanSetImpl root;
        switch(state=(root=this.root).state){
          default:
            return false;
          case 0b11:
            final int newState;
            try{
              if(filter.test(false)){
                if(filter.test(true)){
                  newState=0b00;
                }else{
                  newState=0b10;
                }
              }else{
                if(filter.test(true)){
                  newState=0b01;
                }else{
                  return false;
                }
              }
            }finally{
              CheckedCollection.checkModCount(state,root.state);
            }
            root.state=newState;
            break;
          case 0b01:
            try{
              if(!filter.test(false)){
                return false;
              }
            }finally{
              CheckedCollection.checkModCount(state,root.state);
            }
            root.state=0b00;
            break;
          case 0b10:
            try{
              if(!filter.test(true)){
                return false;
              }
            }finally{
              CheckedCollection.checkModCount(state,root.state);
            }
            root.state=0b00;
        }
        return true;
      }
      @Override public boolean removeIf(Predicate<? super Boolean> filter){
        final int state;
        final BooleanSetImpl root;
        switch(state=(root=this.root).state){
          default:
            return false;
          case 0b11:
            final int newState;
            try{
              if(filter.test(Boolean.FALSE)){
                if(filter.test(Boolean.TRUE)){
                  newState=0b00;
                }else{
                  newState=0b10;
                }
              }else{
                if(filter.test(Boolean.TRUE)){
                  newState=0b01;
                }else{
                  return false;
                }
              }
            }finally{
              CheckedCollection.checkModCount(state,root.state);
            }
            root.state=newState;
            break;
          case 0b01:
            try{
              if(!filter.test(Boolean.FALSE)){
                return false;
              }
            }finally{
              CheckedCollection.checkModCount(state,root.state);
            }
            root.state=0b00;
            break;
          case 0b10:
            try{
              if(!filter.test(Boolean.TRUE)){
                return false;
              }
            }finally{
              CheckedCollection.checkModCount(state,root.state);
            }
            root.state=0b00;
        }
        return true;
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
         final BooleanSetImpl root;
	      final int state=(root=this.root).state;
	      final T[] dst;
	      try{
		      switch(state){
		        case 0b00:
		          return arrConstructor.apply(0);
		        case 0b01:
		          (dst=arrConstructor.apply(1))[0]=(T)Boolean.FALSE;
		          break;
		        case 0b10:
		          (dst=arrConstructor.apply(1))[0]=(T)Boolean.TRUE;
		          break;
		        default:
		          (dst=arrConstructor.apply(2))[0]=(T)Boolean.TRUE;
		          dst[1]=(T)Boolean.FALSE;
		      }
	      }finally{
	        CheckedCollection.checkModCount(state,root.state);
	      }
	      return dst;
	    }
      @Override public Object clone(){
        return new Descending.Checked(root.state);
      }
      private Object writeReplace(){
        return new Descending.Checked(root.state);
      }
      @Override public boolean firstBoolean(){
        switch(root.state){
          case 0b00:
            throw new NoSuchElementException();
          case 0b01:
            return false;
          default:
            return true;
        }
      }
      @Override public boolean lastBoolean(){
        switch(root.state){
          case 0b00:
            throw new NoSuchElementException();
          case 0b10:
            return true;
          default:
            return false;
        }
      }
      @Override public OmniIterator.OfBoolean iterator(){
        final BooleanSetImpl root;
        final int rootState;
        if((rootState=(root=this.root).state)==0b00){
          return EMPTY_ITR;
        }
        return new CheckedDescendingFullItr(root,rootState);
      }
      @Override public OmniIterator.OfBoolean descendingIterator(){
        final BooleanSetImpl root;
        final int rootState;
        if((rootState=(root=this.root).state)==0b00){
          return EMPTY_ITR;
        }
        return new CheckedAscendingFullItr(root,rootState);
      }
      @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
        if(fromElement){
          return this;
        }
        return new UncheckedFalseView.Checked.Descending(root);
      }
      @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
        if(fromElement^inclusive){
          return new UncheckedFalseView.Checked.Descending(root);
        }else if(fromElement){
          return this;
        }
        return AbstractBooleanSet.CHECKED_EMPTY_DESCENDING_TAIL;
      }
      @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement){
        if(toElement){
          return new UncheckedTrueView.Checked.Descending(root);
        }
        return this;
      }
      @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
        if(toElement==inclusive){
          return new UncheckedTrueView.Checked.Descending(root);
        }else if(toElement){
          return AbstractBooleanSet.CHECKED_EMPTY_DESCENDING_HEAD;
        }else{
          return this;
        }
      }
      @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
        if(fromElement){
          if(toElement){
            return new UncheckedTrueView.Checked.Descending(root);
          }else{
            return this;
          }
        }else{
          if(toElement){
            return AbstractBooleanSet.CHECKED_EMPTY_DESCENDING_MIDDLE;
          }else{
            return new UncheckedFalseView.Checked.Descending(root);
          }
        }
      }
      @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
        if(fromElement^fromInclusive){
          if(toElement==toInclusive){
            return AbstractBooleanSet.CHECKED_EMPTY_DESCENDING_MIDDLE;
          }else if(toInclusive){
            return new UncheckedFalseView.Checked.Descending(root);
          }
        }else if(toElement==toInclusive){
          if(fromElement){
            return new UncheckedTrueView.Checked.Descending(root);
          }
        }else if(fromInclusive){
          if(toElement){
            return AbstractBooleanSet.CHECKED_EMPTY_DESCENDING_HEAD;
          }else{
            return this;
          }
        }else if(toInclusive){
          return AbstractBooleanSet.CHECKED_EMPTY_DESCENDING_TAIL;
        }
        throw new IllegalArgumentException("out of bounds");  
      }
    }
  }
  private static class AscendingView extends AbstractFullView{
    private static final long serialVersionUID=1L;
    private AscendingView(BooleanSetImpl root){
      super(root);
    }
    @Override public Object clone(){
      return new BooleanSetImpl(root.state);
    }
    private Object writeReplace(){
      return new BooleanSetImpl(root.state);
    }
    @Override public BooleanComparator comparator(){
      return Boolean::compare;
    }
    @SuppressWarnings("unchecked")
    @Override public <T> T[] toArray(T[] dst){
      switch(root.state){
      case 0b11:
        (dst=OmniArray.uncheckedArrResize(2,dst))[0]=(T)Boolean.FALSE;
        dst[1]=(T)Boolean.TRUE;
        break;
      case 0b10:
        (dst=OmniArray.uncheckedArrResize(1,dst))[0]=(T)Boolean.TRUE;
        break;
      case 0b01:
        (dst=OmniArray.uncheckedArrResize(1,dst))[0]=(T)Boolean.FALSE;
        break;
      default:
        if(dst.length!=0){
          dst[0]=null;
        }
      }
      return dst;
    }
    @SuppressWarnings("unchecked")
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      final T[] dst;
      switch(root.state){
      case 0b11:
        (dst=arrConstructor.apply(2))[0]=(T)Boolean.FALSE;
        dst[1]=(T)Boolean.TRUE;
        break;
      case 0b10:
        (dst=arrConstructor.apply(1))[0]=(T)Boolean.TRUE;
        break;
      case 0b01:
        (dst=arrConstructor.apply(1))[0]=(T)Boolean.FALSE;
        break;
      default:
        return arrConstructor.apply(0);
      }
      return dst;
    }
    @Override public Boolean[] toArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
        case 0b01:
          return new Boolean[]{Boolean.FALSE};
        case 0b10:
          return new Boolean[]{Boolean.TRUE};
        default:
          return new Boolean[]{Boolean.FALSE,Boolean.TRUE};
      }
    }
    @Override public boolean[] toBooleanArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfBoolean.DEFAULT_ARR;
        case 0b01:
          return new boolean[]{false};
        case 0b10:
          return new boolean[]{true};
        default:
          return new boolean[]{false,true};
      }
    }
    @Override public byte[] toByteArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfByte.DEFAULT_ARR;
        case 0b01:
          return new byte[]{(byte)0};
        case 0b10:
          return new byte[]{(byte)1};
        default:
          return new byte[]{(byte)0,(byte)1};
      }
    }
    @Override public char[] toCharArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfChar.DEFAULT_ARR;
        case 0b01:
          return new char[]{(char)0};
        case 0b10:
          return new char[]{(char)1};
        default:
          return new char[]{(char)0,(char)1};
      }
    }
    @Override public short[] toShortArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfShort.DEFAULT_ARR;
        case 0b01:
          return new short[]{(short)0};
        case 0b10:
          return new short[]{(short)1};
        default:
          return new short[]{(short)0,(short)1};
      }
    }
    @Override public int[] toIntArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfInt.DEFAULT_ARR;
        case 0b01:
          return new int[]{0};
        case 0b10:
          return new int[]{1};
        default:
          return new int[]{0,1};
      }
    }
    @Override public long[] toLongArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfLong.DEFAULT_ARR;
        case 0b01:
          return new long[]{0L};
        case 0b10:
          return new long[]{1L};
        default:
          return new long[]{0L,1L};
      }
    }
    @Override public float[] toFloatArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfFloat.DEFAULT_ARR;
        case 0b01:
          return new float[]{0F};
        case 0b10:
          return new float[]{1F};
        default:
          return new float[]{0F,1F};
      }
    }
    @Override public double[] toDoubleArray(){
      switch(root.state){
        case 0b00:
          return OmniArray.OfDouble.DEFAULT_ARR;
        case 0b01:
          return new double[]{0D};
        case 0b10:
          return new double[]{1D};
        default:
          return new double[]{0D,1D};
      }
    }
    @Override public void forEach(BooleanConsumer action){
      switch(root.state){
        case 0b11:
          action.accept(false);
        case 0b10:
          action.accept(true);
          break;
        case 0b01:
          action.accept(false);
        default:
      }
    }
    @Override public void forEach(Consumer<? super Boolean> action){
      switch(root.state){
        case 0b11:
          action.accept(Boolean.FALSE);
        case 0b10:
          action.accept(Boolean.TRUE);
          break;
        case 0b01:
          action.accept(Boolean.FALSE);
        default:
      }
    }
    @Override public boolean firstBoolean(){
      if(root.state==0b10){
        return true;
      }
      return false;
    }
    @Override public boolean lastBoolean(){
      if(root.state==0b01){
        return false;
      }
      return true;
    }
    @Override public Boolean ceiling(boolean val){
      return root.floor(val); 
    }
    @Override public Boolean floor(boolean val){
      return root.ceiling(val); 
    }
    @Override public Boolean lower(boolean val){
      return root.higher(val);
    }
    @Override public Boolean higher(boolean val){
      return root.lower(val);
    }
    @Override public Boolean pollFirst(){
      return root.pollLast();
    }
    @Override public Boolean pollLast(){
      return root.pollFirst();
    }
    @Override public boolean booleanCeiling(boolean val){
      return root.booleanFloor(val);
    }
    @Override public boolean booleanFloor(boolean val){
      return root.booleanCeiling(val);
    }
    @Override public boolean lowerBoolean(boolean val){
      return root.higherBoolean(val);
    }
    @Override public boolean higherBoolean(boolean val){
      return root.lowerBoolean(val);
    }
    @Override public boolean pollFirstBoolean(){
      return root.pollLastBoolean();
    }
    @Override public boolean pollLastBoolean(){
      return root.pollFirstBoolean();
    }
    @Override public byte byteCeiling(byte val){
      return root.byteFloor(val);
    }
    @Override public byte byteFloor(byte val){
      return root.byteCeiling(val);
    }
    @Override public byte lowerByte(byte val){
      return root.higherByte(val);
    }
    @Override public byte higherByte(byte val){
      return root.lowerByte(val);
    }
    @Override public byte pollFirstByte(){
      return root.pollLastByte();
    }
    @Override public byte pollLastByte(){
      return root.pollFirstByte();
    }
    @Override public char charCeiling(char val){
      return root.charFloor(val);
    }
    @Override public char charFloor(char val){
      return root.charCeiling(val);
    }
    @Override public char lowerChar(char val){
      return root.higherChar(val);
    }
    @Override public char higherChar(char val){
      return root.lowerChar(val);
    }
    @Override public char pollFirstChar(){
      return root.pollLastChar();
    }
    @Override public char pollLastChar(){
      return root.pollFirstChar();
    }
    @Override public short shortCeiling(short val){
      return root.shortFloor(val);
    }
    @Override public short shortFloor(short val){
      return root.shortCeiling(val);
    }
    @Override public short lowerShort(short val){
      return root.higherShort(val);
    }
    @Override public short higherShort(short val){
      return root.lowerShort(val);
    }
    @Override public short pollFirstShort(){
      return root.pollLastShort();
    }
    @Override public short pollLastShort(){
      return root.pollFirstShort();
    }
    @Override public int intCeiling(int val){
      return root.intFloor(val);
    }
    @Override public int intFloor(int val){
      return root.intCeiling(val);
    }
    @Override public int lowerInt(int val){
      return root.higherInt(val);
    }
    @Override public int higherInt(int val){
      return root.lowerInt(val);
    }
    @Override public int pollFirstInt(){
      return root.pollLastInt();
    }
    @Override public int pollLastInt(){
      return root.pollFirstInt();
    }
    @Override public long longCeiling(long val){
      return root.longFloor(val);
    }
    @Override public long longFloor(long val){
      return root.longCeiling(val);
    }
    @Override public long lowerLong(long val){
      return root.higherLong(val);
    }
    @Override public long higherLong(long val){
      return root.lowerLong(val);
    }
    @Override public long pollFirstLong(){
      return root.pollLastLong();
    }
    @Override public long pollLastLong(){
      return root.pollFirstLong();
    }
    @Override public float floatCeiling(float val){
      return root.floatFloor(val);
    }
    @Override public float floatFloor(float val){
      return root.floatCeiling(val);
    }
    @Override public float lowerFloat(float val){
      return root.higherFloat(val);
    }
    @Override public float higherFloat(float val){
      return root.lowerFloat(val);
    }
    @Override public float pollFirstFloat(){
      return root.pollLastFloat();
    }
    @Override public float pollLastFloat(){
      return root.pollFirstFloat();
    }
    @Override public double doubleCeiling(double val){
      return root.doubleFloor(val);
    }
    @Override public double doubleFloor(double val){
      return root.doubleCeiling(val);
    }
    @Override public double lowerDouble(double val){
      return root.higherDouble(val);
    }
    @Override public double higherDouble(double val){
      return root.lowerDouble(val);
    }
    @Override public double pollFirstDouble(){
      return root.pollLastDouble();
    }
    @Override public double pollLastDouble(){
      return root.pollFirstDouble();
    }
    @Override public String toString(){
      switch(root.state){
        case 0b00:
          return "[]";
        case 0b01:
          return "[false]";
        case 0b10:
          return "[true]";
        default:
          return "[false, true]";
      }
    }
    @Override public OmniIterator.OfBoolean iterator(){
      final BooleanSetImpl root;
      final int rootState;
      if((rootState=(root=this.root).state)==0b00){
        return EMPTY_ITR;
      }
      return new UncheckedAscendingFullItr(root,rootState);
    }
    @Override public OmniIterator.OfBoolean descendingIterator(){
      final BooleanSetImpl root;
      final int rootState;
      if((rootState=(root=this.root).state)==0b00){
        return EMPTY_ITR;
      }
      return new UncheckedDescendingFullItr(root,rootState);
    }
    @Override public OmniNavigableSet.OfBoolean descendingSet(){
      return root;
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
      if(fromElement){
        return new UncheckedTrueView(root);
      }
      return this;
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
      if(fromElement==inclusive){
        return new UncheckedTrueView(root);
      }else if(fromElement){
        return AbstractBooleanSet.UNCHECKED_EMPTY_ASCENDING;
      }
      return this;
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement){
      if(toElement){
        return this;
      }
      return new UncheckedFalseView(root);
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
      if(toElement ^ inclusive){
        return new UncheckedFalseView(root);
      }else if(toElement){
        return this;
      }else{
        return AbstractBooleanSet.UNCHECKED_EMPTY_ASCENDING;
      } 
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
      if(fromElement){
        if(toElement){
          return AbstractBooleanSet.UNCHECKED_EMPTY_ASCENDING;
        }else{
          return new UncheckedTrueView(root);
        }
      }else{
        if(toElement){
          return new UncheckedFalseView(root);
        }else{
          return this;
        }
      } 
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
      if(fromElement==fromInclusive){
        if(toElement && toInclusive){
          return new UncheckedTrueView(root);
        }
      }else if(toElement^toInclusive){
        if(fromInclusive){
          return new UncheckedFalseView(root);
        }
      }else if(toElement && !fromElement){
        return this;
      }
      return AbstractBooleanSet.UNCHECKED_EMPTY_ASCENDING;
    }
    private static class Checked extends AscendingView{
      private static final long serialVersionUID=1L;
      private Checked(BooleanSetImpl root){
        super(root);
      }
      @Override public void forEach(BooleanConsumer action){
        final int state;
        final BooleanSetImpl root;
        switch(state=(root=this.root).state){
          default:
            return;
          case 0b11:
            try{
              action.accept(false);
              action.accept(true);
            }finally{
              CheckedCollection.checkModCount(state,root.state);
            }
            break;
          case 0b01:
            try{
              action.accept(false);
            }finally{
              CheckedCollection.checkModCount(state,root.state);
            }
            break;
          case 0b10:
            try{
              action.accept(true);
            }finally{
              CheckedCollection.checkModCount(state,root.state);
            }
        }
      }
      @Override public void forEach(Consumer<? super Boolean> action){
        final int state;
        final BooleanSetImpl root;
        switch(state=(root=this.root).state){
          default:
            return;
          case 0b11:
            try{
              action.accept(Boolean.FALSE);
              action.accept(Boolean.TRUE);
            }finally{
              CheckedCollection.checkModCount(state,root.state);
            }
            break;
          case 0b01:
            try{
              action.accept(Boolean.FALSE);
            }finally{
              CheckedCollection.checkModCount(state,root.state);
            }
            break;
          case 0b10:
            try{
              action.accept(Boolean.TRUE);
            }finally{
              CheckedCollection.checkModCount(state,root.state);
            }
        }
      }
      @Override public boolean removeIf(BooleanPredicate filter){
        final int state;
        final BooleanSetImpl root;
        switch(state=(root=this.root).state){
          default:
            return false;
          case 0b11:
            final int newState;
            try{
              if(filter.test(false)){
                if(filter.test(true)){
                  newState=0b00;
                }else{
                  newState=0b10;
                }
              }else{
                if(filter.test(true)){
                  newState=0b01;
                }else{
                  return false;
                }
              }
            }finally{
              CheckedCollection.checkModCount(state,root.state);
            }
            root.state=newState;
            break;
          case 0b01:
            try{
              if(!filter.test(false)){
                return false;
              }
            }finally{
              CheckedCollection.checkModCount(state,root.state);
            }
            root.state=0b00;
            break;
          case 0b10:
            try{
              if(!filter.test(true)){
                return false;
              }
            }finally{
              CheckedCollection.checkModCount(state,root.state);
            }
            root.state=0b00;
        }
        return true;
      }
      @Override public boolean removeIf(Predicate<? super Boolean> filter){
        final int state;
        final BooleanSetImpl root;
        switch(state=(root=this.root).state){
          default:
            return false;
          case 0b11:
            final int newState;
            try{
              if(filter.test(Boolean.FALSE)){
                if(filter.test(Boolean.TRUE)){
                  newState=0b00;
                }else{
                  newState=0b10;
                }
              }else{
                if(filter.test(Boolean.TRUE)){
                  newState=0b01;
                }else{
                  return false;
                }
              }
            }finally{
              CheckedCollection.checkModCount(state,root.state);
            }
            root.state=newState;
            break;
          case 0b01:
            try{
              if(!filter.test(Boolean.FALSE)){
                return false;
              }
            }finally{
              CheckedCollection.checkModCount(state,root.state);
            }
            root.state=0b00;
            break;
          case 0b10:
            try{
              if(!filter.test(Boolean.TRUE)){
                return false;
              }
            }finally{
              CheckedCollection.checkModCount(state,root.state);
            }
            root.state=0b00;
        }
        return true;
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
         final BooleanSetImpl root;
	      final int state=(root=this.root).state;
	      final T[] dst;
	      try{
		      switch(state){
		        case 0b00:
		          return arrConstructor.apply(0);
		        case 0b01:
		          (dst=arrConstructor.apply(1))[0]=(T)Boolean.FALSE;
		          break;
		        case 0b10:
		          (dst=arrConstructor.apply(1))[0]=(T)Boolean.TRUE;
		          break;
		        default:
		          (dst=arrConstructor.apply(2))[0]=(T)Boolean.FALSE;
		          dst[1]=(T)Boolean.TRUE;
		      }
	      }finally{
	        CheckedCollection.checkModCount(state,root.state);
	      }
	      return dst;
	    }
      @Override public OmniNavigableSet.OfBoolean descendingSet(){
        return root;
      }
      @Override public Object clone(){
        return new BooleanSetImpl.Checked(root.state);
      }
      private Object writeReplace(){
        return new BooleanSetImpl.Checked(root.state);
      }
      @Override public boolean firstBoolean(){
        switch(root.state){
          case 0b00:
            throw new NoSuchElementException();
          case 0b10:
            return true;
          default:
            return false;
        }
      }
      @Override public boolean lastBoolean(){
        switch(root.state){
          case 0b00:
            throw new NoSuchElementException();
          case 0b01:
            return false;
          default:
            return true;
        }
      }
      @Override public OmniIterator.OfBoolean iterator(){
        final BooleanSetImpl root;
        final int rootState;
        if((rootState=(root=this.root).state)==0b00){
          return EMPTY_ITR;
        }
        return new CheckedAscendingFullItr(root,rootState);
      }
      @Override public OmniIterator.OfBoolean descendingIterator(){
        final BooleanSetImpl root;
        final int rootState;
        if((rootState=(root=this.root).state)==0b00){
          return EMPTY_ITR;
        }
        return new CheckedDescendingFullItr(root,rootState);
      }
      @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
        if(fromElement){
          return new UncheckedTrueView.Checked(root);
        }
        return this;
      }
      @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
        if(fromElement==inclusive){
          return new UncheckedTrueView.Checked(root);
        }else if(fromElement){
          return AbstractBooleanSet.CHECKED_EMPTY_ASCENDING_TAIL;
        }
        return this;
      }
      @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement){
        if(toElement){
          return this;
        }
        return new UncheckedFalseView.Checked(root);
      }
      @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
        if(toElement ^ inclusive){
          return new UncheckedFalseView.Checked(root);
        }else if(toElement){
          return this;
        }else{
          return AbstractBooleanSet.CHECKED_EMPTY_ASCENDING_HEAD;
        }
      }
      @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
        if(fromElement){
          if(toElement){
            return AbstractBooleanSet.CHECKED_EMPTY_ASCENDING_MIDDLE;
          }else{
            return new UncheckedTrueView.Checked(root);
          }
        }else{
          if(toElement){
            return new UncheckedFalseView.Checked(root);
          }else{
            return this;
          }
        }
      }
      @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
        if(fromElement==fromInclusive){
          if(toElement^toInclusive){
            return AbstractBooleanSet.CHECKED_EMPTY_ASCENDING_MIDDLE;
          }else if(toElement){
            return new UncheckedTrueView.Checked(root);
          }
        }else if(toElement^toInclusive){
          if(fromInclusive){
            return new UncheckedFalseView.Checked(root);
          }
        }else{
          if(toElement){
            if(fromElement){
              return AbstractBooleanSet.CHECKED_EMPTY_ASCENDING_TAIL;
            }else{
              return this;
            }
          }else if(fromElement){
            return AbstractBooleanSet.CHECKED_EMPTY_ASCENDING_HEAD;
          }
        }
        throw new IllegalArgumentException("out of bounds");
      }
    }
  }
  private static abstract class AbstractSingleView extends AbstractBooleanSet{
    transient final BooleanSetImpl root;
    private AbstractSingleView(BooleanSetImpl root){
      this.root=root;
    }
    @Override public BooleanComparator comparator(){
      return Boolean::compare;
    }
  }
  private static class UncheckedTrueView extends AbstractSingleView{
    private UncheckedTrueView(BooleanSetImpl root){
      super(root);
    }
    @Override boolean addTrue(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&(10))==0){
        root.state=state+10;
        return true;
      }
      return false;
    }
    @Override boolean containsTrue(){
      return (root.state&0b10)!=0;
    }
    @Override boolean removeTrue(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&(10))!=0){
        root.state=state-10;
        return true;
      }
      return false;
    }
    @Override boolean addFalse(){
      throw new IllegalArgumentException("out of bounds");
    }
    @Override boolean containsFalse(){
      return false;
    }
    @Override boolean removeFalse(){
      return false;
    }
    @Override public boolean removeIf(BooleanPredicate filter){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10)!=0 && filter.test(true)){
        root.state=state-0b10;
        return true;
      }
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Boolean> filter){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10)!=0 && filter.test(Boolean.TRUE)){
        root.state=state-0b10;
        return true;
      }
      return false;
    }
    @Override public int size(){
      return root.state>>>1;
    }
    @Override public void clear(){
      root.state&=0b01;
    }
    @Override public int hashCode(){
      if((root.state&0b10)==0){
        return 0;
      }
      return 1231;
    }
    @Override public boolean isEmpty(){
      return (root.state&0b10)==0;
    }
    @Override public boolean equals(Object val){
    if(val==this){
        return true;
      }
      if(val instanceof Set){
        if(((root.state)&0b01)==0){
          return ((Set<?>)val).isEmpty();
        }
        return equalsTrueState((Set<?>)val);
      }
      return false;
    }
    @SuppressWarnings("unchecked")
    @Override public <T> T[] toArray(T[] dst){
      if((root.state&0b10)==0){
        if(dst.length!=0){
          dst[0]=null;
        }
      }else{
        (dst=OmniArray.uncheckedArrResize(1,dst))[0]=(T)Boolean.TRUE;
      }
      return dst;
    }
    @SuppressWarnings("unchecked")
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      if((root.state&0b10)==0){
        return arrConstructor.apply(0);
      }else{
        final T[] dst;
        (dst=arrConstructor.apply(1))[0]=(T)Boolean.TRUE;
        return dst;
      }
    }
    @Override public Boolean[] toArray(){
      if((root.state&0b10)!=0){
        return new Boolean[]{Boolean.TRUE};
      }
      return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
    }
    @Override public boolean[] toBooleanArray(){
      if((root.state&0b10)!=0){
        return new boolean[]{true};
      }
      return OmniArray.OfBoolean.DEFAULT_ARR;
    }
    @Override public byte[] toByteArray(){
      if((root.state&0b10)!=0){
        return new byte[]{(byte)1};
      }
      return OmniArray.OfByte.DEFAULT_ARR;
    }
    @Override public char[] toCharArray(){
      if((root.state&0b10)!=0){
        return new char[]{(char)1};
      }
      return OmniArray.OfChar.DEFAULT_ARR;
    }
    @Override public short[] toShortArray(){
      if((root.state&0b10)!=0){
        return new short[]{(short)1};
      }
      return OmniArray.OfShort.DEFAULT_ARR;
    }
    @Override public int[] toIntArray(){
      if((root.state&0b10)!=0){
        return new int[]{1};
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
    @Override public long[] toLongArray(){
      if((root.state&0b10)!=0){
        return new long[]{1L};
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override public float[] toFloatArray(){
      if((root.state&0b10)!=0){
        return new float[]{1F};
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override public double[] toDoubleArray(){
      if((root.state&0b10)!=0){
        return new double[]{1D};
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override public void forEach(BooleanConsumer action){
      if((root.state&0b10)!=0){
        action.accept(true);
      }
    }
    @Override public void forEach(Consumer<? super Boolean> action){
      if((root.state&0b10)!=0){
        action.accept(Boolean.TRUE);
      }
    }
    @Override public boolean firstBoolean(){
      return true;
    }
    @Override public boolean lastBoolean(){
      return true;
    }
    @Override public String toString(){
      if((root.state&0b10)!=0){
        return "[true]";
      }
      return "[]";
    }
    @Override public Boolean ceiling(boolean val){
      if((root.state&0b10)!=0){
        return Boolean.TRUE;
      }
      return null;
    }
    @Override public Boolean floor(boolean val){
      if(val && (root.state&0b10)!=0){
        return Boolean.TRUE;
      }
      return null;
    }
    @Override public Boolean lower(boolean val){
      return null;
    }
    @Override public Boolean higher(boolean val){
      if(!val && (root.state&0b10)!=0){
        return Boolean.TRUE;
      }
      return null;
    }
    @Override public Boolean pollFirst(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10)!=0){
        root.state=state-0b10;
        return Boolean.TRUE;
      }
      return null;
    }
    @Override public Boolean pollLast(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10)!=0){
        root.state=state-0b10;
        return Boolean.TRUE;
      }
      return null;
    }
    @Override public boolean booleanCeiling(boolean val){
      return (root.state&0b10)!=0;
    }
    @Override public boolean booleanFloor(boolean val){
      return val && (root.state&0b10)!=0;
    }
    @Override public boolean lowerBoolean(boolean val){
      return false;
    }
    @Override public boolean higherBoolean(boolean val){
      return !val && (root.state&0b10)!=0;
    }
    @Override public boolean pollFirstBoolean(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10)!=0){
        root.state=state-0b10;
        return true;
      }
      return false;
    }
    @Override public boolean pollLastBoolean(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10)!=0){
        root.state=state-0b10;
        return true;
      }
      return false;
    }
    @Override public byte byteCeiling(byte val){
      if(val<=1 && (root.state&0b10)!=0){
        return 1;
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte byteFloor(byte val){
       if(val>=1 && (root.state&0b10)!=0){
        return 1;
      }
      return Byte.MIN_VALUE; 
    }
    @Override public byte lowerByte(byte val){
      if(val>1 && (root.state&0b10)!=0){
        return 1;
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte higherByte(byte val){
      if(val<1 && (root.state&0b10)!=0){
        return 1;
      } 
      return Byte.MIN_VALUE;
    }
    @Override public byte pollFirstByte(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10)!=0){
        root.state=state-0b10;
        return 1;
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte pollLastByte(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10)!=0){
        root.state=state-0b10;
        return 1;
      }
      return Byte.MIN_VALUE;
    }
    @Override public char charCeiling(char val){
      if(val<=1 && (root.state&0b10)!=0){
        return 1;
      }
      return Character.MIN_VALUE;
    }
    @Override public char charFloor(char val){
       if(val>=1 && (root.state&0b10)!=0){
        return 1;
      }
      return Character.MIN_VALUE; 
    }
    @Override public char lowerChar(char val){
      if(val>1 && (root.state&0b10)!=0){
        return 1;
      }
      return Character.MIN_VALUE;
    }
    @Override public char higherChar(char val){
      if(val==0 && (root.state&0b10)!=0){
        return 1;
      }  
      return Character.MIN_VALUE;
    }
    @Override public char pollFirstChar(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10)!=0){
        root.state=state-0b10;
        return 1;
      }
      return Character.MIN_VALUE;
    }
    @Override public char pollLastChar(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10)!=0){
        root.state=state-0b10;
        return 1;
      }
      return Character.MIN_VALUE;
    }
    @Override public short shortCeiling(short val){
      if(val<=1 && (root.state&0b10)!=0){
        return 1;
      }
      return Short.MIN_VALUE;
    }
    @Override public short shortFloor(short val){
       if(val>=1 && (root.state&0b10)!=0){
        return 1;
      }
      return Short.MIN_VALUE; 
    }
    @Override public short lowerShort(short val){
      if(val>1 && (root.state&0b10)!=0){
        return 1;
      }
      return Short.MIN_VALUE;
    }
    @Override public short higherShort(short val){
      if(val<1 && (root.state&0b10)!=0){
        return 1;
      } 
      return Short.MIN_VALUE;
    }
    @Override public short pollFirstShort(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10)!=0){
        root.state=state-0b10;
        return 1;
      }
      return Short.MIN_VALUE;
    }
    @Override public short pollLastShort(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10)!=0){
        root.state=state-0b10;
        return 1;
      }
      return Short.MIN_VALUE;
    }
    @Override public int intCeiling(int val){
      if(val<=1 && (root.state&0b10)!=0){
        return 1;
      }
      return Integer.MIN_VALUE;
    }
    @Override public int intFloor(int val){
       if(val>=1 && (root.state&0b10)!=0){
        return 1;
      }
      return Integer.MIN_VALUE; 
    }
    @Override public int lowerInt(int val){
      if(val>1 && (root.state&0b10)!=0){
        return 1;
      }
      return Integer.MIN_VALUE;
    }
    @Override public int higherInt(int val){
      if(val<1 && (root.state&0b10)!=0){
        return 1;
      } 
      return Integer.MIN_VALUE;
    }
    @Override public int pollFirstInt(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10)!=0){
        root.state=state-0b10;
        return 1;
      }
      return Integer.MIN_VALUE;
    }
    @Override public int pollLastInt(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10)!=0){
        root.state=state-0b10;
        return 1;
      }
      return Integer.MIN_VALUE;
    }
    @Override public long longCeiling(long val){
      if(val<=1L && (root.state&0b10)!=0){
        return 1L;
      }
      return Long.MIN_VALUE;
    }
    @Override public long longFloor(long val){
       if(val>=1L && (root.state&0b10)!=0){
        return 1L;
      }
      return Long.MIN_VALUE; 
    }
    @Override public long lowerLong(long val){
      if(val>1L && (root.state&0b10)!=0){
        return 1L;
      }
      return Long.MIN_VALUE;
    }
    @Override public long higherLong(long val){
      if(val<1L && (root.state&0b10)!=0){
        return 1L;
      } 
      return Long.MIN_VALUE;
    }
    @Override public long pollFirstLong(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10)!=0){
        root.state=state-0b10;
        return 1L;
      }
      return Long.MIN_VALUE;
    }
    @Override public long pollLastLong(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10)!=0){
        root.state=state-0b10;
        return 1L;
      }
      return Long.MIN_VALUE;
    }
    @Override public float floatCeiling(float val){
      if(val<=1F && (root.state&0b10)!=0){
        return 1F;
      }
      return Float.NaN;
    }
    @Override public float floatFloor(float val){
       if(val>=1F && (root.state&0b10)!=0){
        return 1F;
      }
      return Float.NaN; 
    }
    @Override public float lowerFloat(float val){
      if(val>1F && (root.state&0b10)!=0){
        return 1F;
      }
      return Float.NaN;
    }
    @Override public float higherFloat(float val){
      if(val<1F && (root.state&0b10)!=0){
        return 1F;
      } 
      return Float.NaN;
    }
    @Override public float pollFirstFloat(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10)!=0){
        root.state=state-0b10;
        return 1F;
      }
      return Float.NaN;
    }
    @Override public float pollLastFloat(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10)!=0){
        root.state=state-0b10;
        return 1F;
      }
      return Float.NaN;
    }
    @Override public double doubleCeiling(double val){
      if(val<=1D && (root.state&0b10)!=0){
        return 1D;
      }
      return Double.NaN;
    }
    @Override public double doubleFloor(double val){
       if(val>=1D && (root.state&0b10)!=0){
        return 1D;
      }
      return Double.NaN; 
    }
    @Override public double lowerDouble(double val){
      if(val>1D && (root.state&0b10)!=0){
        return 1D;
      }
      return Double.NaN;
    }
    @Override public double higherDouble(double val){
      if(val<1D && (root.state&0b10)!=0){
        return 1D;
      } 
      return Double.NaN;
    }
    @Override public double pollFirstDouble(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10)!=0){
        root.state=state-0b10;
        return 1D;
      }
      return Double.NaN;
    }
    @Override public double pollLastDouble(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b10)!=0){
        root.state=state-0b10;
        return 1D;
      }
      return Double.NaN;
    }
    @Override public OmniIterator.OfBoolean iterator(){
      final BooleanSetImpl root;
      if(((root=this.root).state)==0b00){
        return EMPTY_ITR;
      }
      return new UncheckedTrueItr(root,0b1);
    }
    @Override public OmniIterator.OfBoolean descendingIterator(){
      final BooleanSetImpl root;
      if(((root=this.root).state)==0b00){
        return EMPTY_ITR;
      }
      return new UncheckedTrueItr(root,0b1);
    }
    @Override public OmniNavigableSet.OfBoolean descendingSet(){
      return new UncheckedTrueView.Descending(root);
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
      return this;
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
      if(fromElement==inclusive){
        return this;
      }
      return AbstractBooleanSet.UNCHECKED_EMPTY_ASCENDING;
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement){
      if(toElement){
        return this;
      }
      return AbstractBooleanSet.UNCHECKED_EMPTY_ASCENDING;
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
      if(toElement && inclusive){
        return this;
      }
      return AbstractBooleanSet.UNCHECKED_EMPTY_ASCENDING;
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
      if(fromElement && toElement){
        return this;
      }
      return AbstractBooleanSet.UNCHECKED_EMPTY_ASCENDING;
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
      if(toElement && toInclusive && fromElement==fromInclusive){
        return this;
      }
      return AbstractBooleanSet.UNCHECKED_EMPTY_ASCENDING;
    }
    private static class Descending extends UncheckedTrueView{
      private Descending(BooleanSetImpl root){
        super(root);
      }
      @Override public BooleanComparator comparator(){
        return BooleanComparator::descendingCompare;
      }
      @Override public OmniNavigableSet.OfBoolean descendingSet(){
        return new UncheckedTrueView(root);
      }
      @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
        if(fromElement){
          return this;
        }
        return AbstractBooleanSet.UNCHECKED_EMPTY_DESCENDING;
      }
      @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
        if(fromElement^inclusive){
          return AbstractBooleanSet.UNCHECKED_EMPTY_DESCENDING;
        }
        return this;
      }
      @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement){
        return this;
      }
      @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
        if(toElement==inclusive){
          return this;
        }
        return AbstractBooleanSet.UNCHECKED_EMPTY_DESCENDING;
      }
      @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
        if(fromElement && toElement){
          return this;
        } 
        return AbstractBooleanSet.UNCHECKED_EMPTY_DESCENDING;
      }
      @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
        if(fromElement && fromInclusive && toElement==toInclusive){
          return this;
        }
        return AbstractBooleanSet.UNCHECKED_EMPTY_DESCENDING;
      }
    }
    private static class Checked extends UncheckedTrueView{
      private Checked(BooleanSetImpl root){
        super(root);
      }
      @Override public void forEach(BooleanConsumer action){
        final BooleanSetImpl root;
        final int state;
        if(((state=(root=this.root).state)&0b10)!=0){
          try{
            action.accept(true);
          }finally{
             CheckedCollection.checkModCount(state,root.state);
          }
        }
      }
      @Override public void forEach(Consumer<? super Boolean> action){
        final BooleanSetImpl root;
        final int state;
        if(((state=(root=this.root).state)&0b10)!=0){
          try{
            action.accept(Boolean.TRUE);
          }finally{
             CheckedCollection.checkModCount(state,root.state);
          }
        }
      }
      @Override public boolean removeIf(BooleanPredicate filter){
        final BooleanSetImpl root;
        final int state;
        if(((state=(root=this.root).state)&0b10)!=0){
          try{
            if(!filter.test(true)){
              return false;
            }
          }finally{
            CheckedCollection.checkModCount(state,root.state);
          }
          root.state=state&0b01;
          return true;
        }
        return false;
      }
      @Override public boolean removeIf(Predicate<? super Boolean> filter){
        final BooleanSetImpl root;
        final int state;
        if(((state=(root=this.root).state)&0b10)!=0){
          try{
            if(!filter.test(Boolean.TRUE)){
              return false;
            }
          }finally{
            CheckedCollection.checkModCount(state,root.state);
          }
          root.state=state&0b01;
          return true;
        }
        return false;
      }
      @Override public boolean firstBoolean(){
        if((root.state&0b10)==0){
          throw new NoSuchElementException();
        }
        return true;
      }
      @Override public boolean lastBoolean(){
        if((root.state&0b10)==0){
          throw new NoSuchElementException();
        }
        return true;
      }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        final BooleanSetImpl root;
        final int state;
        final T[] dst;
        final int size=(state=(root=this.root).state)>>>1;
        try{
          dst=arrConstructor.apply(size);
        }finally{
          CheckedCollection.checkModCount(state,root.state);
        }
        if(size!=0){
          dst[0]=(T)Boolean.TRUE;
        }
        return dst;
      }
      @Override public OmniIterator.OfBoolean iterator(){
        final BooleanSetImpl root;
        if(((root=this.root).state)==0b00){
          return EMPTY_ITR;
        }
        return new CheckedTrueItr(root,0b10);
      }
      @Override public OmniIterator.OfBoolean descendingIterator(){
        final BooleanSetImpl root;
        if(((root=this.root).state)==0b00){
          return EMPTY_ITR;
        }
        return new CheckedTrueItr(root,0b10);
      }
      @Override public OmniNavigableSet.OfBoolean descendingSet(){
        return new UncheckedTrueView.Checked.Descending(root);
      }
      @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
        if(fromElement){
          return this;
        }
        throw new IllegalArgumentException("out of bounds");
      }
      @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
        if(fromElement==inclusive){
          return this;
        }else if(fromElement){
          return AbstractBooleanSet.CHECKED_EMPTY_ASCENDING_TAIL;
        }
        throw new IllegalArgumentException("out of bounds");
      }
      @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement){
        if(toElement){
          return this;
        }
        return AbstractBooleanSet.CHECKED_EMPTY_ASCENDING_MIDDLE;
      }
      @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
        if(toElement^inclusive){
          return AbstractBooleanSet.CHECKED_EMPTY_ASCENDING_MIDDLE;
        }else if(toElement){
          return this;
        }
        throw new IllegalArgumentException("out of bounds");
      }
      @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
        if(fromElement){
          if(toElement){
            return this;
          }else{
            return AbstractBooleanSet.CHECKED_EMPTY_ASCENDING_MIDDLE;
          }
        }
        throw new IllegalArgumentException("out of bounds");
      }
      @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
        if(fromElement==fromInclusive){
          if(toElement^toInclusive){
            return AbstractBooleanSet.CHECKED_EMPTY_ASCENDING_MIDDLE;
          }else if(toElement){
            return this;
          }
        }else if(fromElement && toElement && toInclusive){
          return AbstractBooleanSet.CHECKED_EMPTY_ASCENDING_TAIL;
        }
        throw new IllegalArgumentException("out of bounds");
      }
      private static class Descending extends Checked{
        private Descending(BooleanSetImpl root){
          super(root);
        }
        @Override public BooleanComparator comparator(){
          return BooleanComparator::descendingCompare;
        }
        @Override public OmniNavigableSet.OfBoolean descendingSet(){
          return new UncheckedTrueView.Checked(root);
        }
        @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
          if(fromElement){
            return this;
          }
          return AbstractBooleanSet.CHECKED_EMPTY_DESCENDING_MIDDLE;
        }
        @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
          if(fromElement^inclusive){
            return AbstractBooleanSet.CHECKED_EMPTY_DESCENDING_MIDDLE;
          }else if(fromElement){
            return this;
          }
          throw new IllegalArgumentException("out of bounds");
        }
        @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement){
          if(toElement){
            return this;
          }
          throw new IllegalArgumentException("out of bounds");
        }
        @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
          if(toElement==inclusive){
            return this;
          }else if(toElement){
            return AbstractBooleanSet.CHECKED_EMPTY_DESCENDING_HEAD;
          }
          throw new IllegalArgumentException("out of bounds");
        }
        @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
          if(toElement){
            if(fromElement){
              return this;
            }else{
              return AbstractBooleanSet.CHECKED_EMPTY_DESCENDING_MIDDLE;
            }
          }
          throw new IllegalArgumentException("out of bounds"); 
        }
        @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
          if(toElement==toInclusive){
            if(fromElement^fromInclusive){
              return AbstractBooleanSet.CHECKED_EMPTY_DESCENDING_MIDDLE;
            }else if(fromElement){
              return this;
            }
          }else if(toElement && fromElement && fromInclusive){
            return AbstractBooleanSet.CHECKED_EMPTY_DESCENDING_HEAD;
          }
          throw new IllegalArgumentException("out of bounds");
        }
      }
    }
  }
  private static class UncheckedFalseView extends AbstractSingleView{
    private UncheckedFalseView(BooleanSetImpl root){
      super(root);
    }
    @Override boolean addTrue(){
      throw new IllegalArgumentException("out of bounds");
    }
    @Override boolean containsTrue(){
      return false;
    }
    @Override boolean removeTrue(){
      return false;
    }
    @Override boolean addFalse(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&(01))==0){
        root.state=state+01;
        return true;
      }
      return false;
    }
    @Override boolean containsFalse(){
      return (root.state&0b01)!=0;
    }
    @Override boolean removeFalse(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&(01))!=0){
        root.state=state-01;
        return true;
      }
      return false;
    }
    @Override public boolean removeIf(BooleanPredicate filter){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b01)!=0 && filter.test(false)){
        root.state=state-0b01;
        return true;
      }
      return false;
    }
    @Override public boolean removeIf(Predicate<? super Boolean> filter){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b01)!=0 && filter.test(Boolean.FALSE)){
        root.state=state-0b01;
        return true;
      }
      return false;
    }
    @Override public int size(){
      return root.state&0b01;
    }
    @Override public void clear(){
      root.state&=0b10;
    }
    @Override public int hashCode(){
      if((root.state&0b01)==0){
        return 0;
      }
      return 1237;
    }
    @Override public boolean isEmpty(){
      return (root.state&0b01)==0;
    }
    @Override public boolean equals(Object val){
    if(val==this){
        return true;
      }
      if(val instanceof Set){
        if(((root.state)&0b01)==0){
          return ((Set<?>)val).isEmpty();
        }
        return equalsFalseState((Set<?>)val);
      }
      return false;
    }
    @SuppressWarnings("unchecked")
    @Override public <T> T[] toArray(T[] dst){
      if((root.state&0b01)==0){
        if(dst.length!=0){
          dst[0]=null;
        }
      }else{
        (dst=OmniArray.uncheckedArrResize(1,dst))[0]=(T)Boolean.FALSE;
      }
      return dst;
    }
    @SuppressWarnings("unchecked")
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      if((root.state&0b01)==0){
        return arrConstructor.apply(0);
      }else{
        final T[] dst;
        (dst=arrConstructor.apply(1))[0]=(T)Boolean.FALSE;
        return dst;
      }
    }
    @Override public Boolean[] toArray(){
      if((root.state&0b01)!=0){
        return new Boolean[]{Boolean.FALSE};
      }
      return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
    }
    @Override public boolean[] toBooleanArray(){
      if((root.state&0b01)!=0){
        return new boolean[]{false};
      }
      return OmniArray.OfBoolean.DEFAULT_ARR;
    }
    @Override public byte[] toByteArray(){
      if((root.state&0b01)!=0){
        return new byte[]{(byte)0};
      }
      return OmniArray.OfByte.DEFAULT_ARR;
    }
    @Override public char[] toCharArray(){
      if((root.state&0b01)!=0){
        return new char[]{(char)0};
      }
      return OmniArray.OfChar.DEFAULT_ARR;
    }
    @Override public short[] toShortArray(){
      if((root.state&0b01)!=0){
        return new short[]{(short)0};
      }
      return OmniArray.OfShort.DEFAULT_ARR;
    }
    @Override public int[] toIntArray(){
      if((root.state&0b01)!=0){
        return new int[]{0};
      }
      return OmniArray.OfInt.DEFAULT_ARR;
    }
    @Override public long[] toLongArray(){
      if((root.state&0b01)!=0){
        return new long[]{0L};
      }
      return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override public float[] toFloatArray(){
      if((root.state&0b01)!=0){
        return new float[]{0F};
      }
      return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override public double[] toDoubleArray(){
      if((root.state&0b01)!=0){
        return new double[]{0D};
      }
      return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override public void forEach(BooleanConsumer action){
      if((root.state&0b01)!=0){
        action.accept(false);
      }
    }
    @Override public void forEach(Consumer<? super Boolean> action){
      if((root.state&0b01)!=0){
        action.accept(Boolean.FALSE);
      }
    }
    @Override public boolean firstBoolean(){
      return false;
    }
    @Override public boolean lastBoolean(){
      return false;
    }
    @Override public String toString(){
      if((root.state&0b01)!=0){
        return "[false]";
      }
      return "[]";
    }
    @Override public Boolean ceiling(boolean val){
      if(!val && (root.state&0b01)!=0){
        return Boolean.FALSE;
      }
      return null;
    }
    @Override public Boolean floor(boolean val){
      if((root.state&0b01)!=0){
        return Boolean.FALSE;
      }
      return null;  
    }
    @Override public Boolean lower(boolean val){
      if(val && (root.state&0b01)!=0){
        return Boolean.FALSE;
      }
      return null;
    }
    @Override public Boolean higher(boolean val){
      return null;
    }
    @Override public Boolean pollFirst(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b01)!=0){
        root.state=state-0b01;
        return Boolean.FALSE;
      }
      return null;
    }
    @Override public Boolean pollLast(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b01)!=0){
        root.state=state-0b01;
        return Boolean.FALSE;
      }
      return null;
    }
    @Override public boolean booleanCeiling(boolean val){
      return false;
    }
    @Override public boolean booleanFloor(boolean val){
      return false;
    }
    @Override public boolean lowerBoolean(boolean val){
      return false;
    }
    @Override public boolean higherBoolean(boolean val){
      return false;
    }
    @Override public boolean pollFirstBoolean(){
      root.state&=0b10;
      return false;
    }
    @Override public boolean pollLastBoolean(){
      root.state&=0b10;
      return false;
    }
    @Override public byte byteCeiling(byte val){
      if(val<=0 && (root.state&0b01)!=0){
        return 0;
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte byteFloor(byte val){
      if(val>=0 && (root.state&0b01)!=0){
        return 0;
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte lowerByte(byte val){
      if(val>0 && (root.state&0b01)!=0){
        return 0;
      }  
      return Byte.MIN_VALUE;
    }
    @Override public byte higherByte(byte val){
      if(val<0 && (root.state&0b01)!=0){
        return 0;
      } 
      return Byte.MIN_VALUE;
    }
    @Override public byte pollFirstByte(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b01)!=0){
        root.state=state-0b01;
        return 0;
      }
      return Byte.MIN_VALUE;
    }
    @Override public byte pollLastByte(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b01)!=0){
        root.state=state-0b01;
        return 0;
      }
      return Byte.MIN_VALUE;
    }
    @Override public char charCeiling(char val){
      return Character.MIN_VALUE;
    }
    @Override public char charFloor(char val){
      return Character.MIN_VALUE;
    }
    @Override public char lowerChar(char val){
      return Character.MIN_VALUE;
    }
    @Override public char higherChar(char val){
      return Character.MIN_VALUE;
    }
    @Override public char pollFirstChar(){
      root.state&=0b10;
      return Character.MIN_VALUE;
    }
    @Override public char pollLastChar(){
      root.state&=0b10;
      return Character.MIN_VALUE;
    }
    @Override public short shortCeiling(short val){
      if(val<=0 && (root.state&0b01)!=0){
        return 0;
      }
      return Short.MIN_VALUE;
    }
    @Override public short shortFloor(short val){
      if(val>=0 && (root.state&0b01)!=0){
        return 0;
      }
      return Short.MIN_VALUE;
    }
    @Override public short lowerShort(short val){
      if(val>0 && (root.state&0b01)!=0){
        return 0;
      }  
      return Short.MIN_VALUE;
    }
    @Override public short higherShort(short val){
      if(val<0 && (root.state&0b01)!=0){
        return 0;
      } 
      return Short.MIN_VALUE;
    }
    @Override public short pollFirstShort(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b01)!=0){
        root.state=state-0b01;
        return 0;
      }
      return Short.MIN_VALUE;
    }
    @Override public short pollLastShort(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b01)!=0){
        root.state=state-0b01;
        return 0;
      }
      return Short.MIN_VALUE;
    }
    @Override public int intCeiling(int val){
      if(val<=0 && (root.state&0b01)!=0){
        return 0;
      }
      return Integer.MIN_VALUE;
    }
    @Override public int intFloor(int val){
      if(val>=0 && (root.state&0b01)!=0){
        return 0;
      }
      return Integer.MIN_VALUE;
    }
    @Override public int lowerInt(int val){
      if(val>0 && (root.state&0b01)!=0){
        return 0;
      }  
      return Integer.MIN_VALUE;
    }
    @Override public int higherInt(int val){
      if(val<0 && (root.state&0b01)!=0){
        return 0;
      } 
      return Integer.MIN_VALUE;
    }
    @Override public int pollFirstInt(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b01)!=0){
        root.state=state-0b01;
        return 0;
      }
      return Integer.MIN_VALUE;
    }
    @Override public int pollLastInt(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b01)!=0){
        root.state=state-0b01;
        return 0;
      }
      return Integer.MIN_VALUE;
    }
    @Override public long longCeiling(long val){
      if(val<=0L && (root.state&0b01)!=0){
        return 0L;
      }
      return Long.MIN_VALUE;
    }
    @Override public long longFloor(long val){
      if(val>=0L && (root.state&0b01)!=0){
        return 0L;
      }
      return Long.MIN_VALUE;
    }
    @Override public long lowerLong(long val){
      if(val>0L && (root.state&0b01)!=0){
        return 0L;
      }  
      return Long.MIN_VALUE;
    }
    @Override public long higherLong(long val){
      if(val<0L && (root.state&0b01)!=0){
        return 0L;
      } 
      return Long.MIN_VALUE;
    }
    @Override public long pollFirstLong(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b01)!=0){
        root.state=state-0b01;
        return 0L;
      }
      return Long.MIN_VALUE;
    }
    @Override public long pollLastLong(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b01)!=0){
        root.state=state-0b01;
        return 0L;
      }
      return Long.MIN_VALUE;
    }
    @Override public float floatCeiling(float val){
      if(val<=0F && (root.state&0b01)!=0){
        return 0F;
      }
      return Float.NaN;
    }
    @Override public float floatFloor(float val){
      if(val>=0F && (root.state&0b01)!=0){
        return 0F;
      }
      return Float.NaN;
    }
    @Override public float lowerFloat(float val){
      if(val>0F && (root.state&0b01)!=0){
        return 0F;
      }  
      return Float.NaN;
    }
    @Override public float higherFloat(float val){
      if(val<0F && (root.state&0b01)!=0){
        return 0F;
      } 
      return Float.NaN;
    }
    @Override public float pollFirstFloat(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b01)!=0){
        root.state=state-0b01;
        return 0F;
      }
      return Float.NaN;
    }
    @Override public float pollLastFloat(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b01)!=0){
        root.state=state-0b01;
        return 0F;
      }
      return Float.NaN;
    }
    @Override public double doubleCeiling(double val){
      if(val<=0D && (root.state&0b01)!=0){
        return 0D;
      }
      return Double.NaN;
    }
    @Override public double doubleFloor(double val){
      if(val>=0D && (root.state&0b01)!=0){
        return 0D;
      }
      return Double.NaN;
    }
    @Override public double lowerDouble(double val){
      if(val>0D && (root.state&0b01)!=0){
        return 0D;
      }  
      return Double.NaN;
    }
    @Override public double higherDouble(double val){
      if(val<0D && (root.state&0b01)!=0){
        return 0D;
      } 
      return Double.NaN;
    }
    @Override public double pollFirstDouble(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b01)!=0){
        root.state=state-0b01;
        return 0D;
      }
      return Double.NaN;
    }
    @Override public double pollLastDouble(){
      final BooleanSetImpl root;
      final int state;
      if(((state=(root=this.root).state)&0b01)!=0){
        root.state=state-0b01;
        return 0D;
      }
      return Double.NaN;
    }
    @Override public OmniIterator.OfBoolean iterator(){
      final BooleanSetImpl root;
      if(((root=this.root).state)==0b00){
        return EMPTY_ITR;
      }
      return new UncheckedFalseItr(root,0b1);
    }
    @Override public OmniIterator.OfBoolean descendingIterator(){
      final BooleanSetImpl root;
      if(((root=this.root).state)==0b00){
        return EMPTY_ITR;
      }
      return new UncheckedFalseItr(root,0b1);
    }
    @Override public OmniNavigableSet.OfBoolean descendingSet(){
      return new UncheckedFalseView.Descending(root);
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
      if(fromElement){
        return AbstractBooleanSet.UNCHECKED_EMPTY_ASCENDING;
      }
      return this;
    }
    @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
      if(fromElement==inclusive){
        return AbstractBooleanSet.UNCHECKED_EMPTY_ASCENDING;
      }
      return this;
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement){
      return this;
    }
    @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
      if(toElement^inclusive){
        return this;
      }  
      return AbstractBooleanSet.UNCHECKED_EMPTY_ASCENDING;
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
      if(!toElement && !fromElement){
        return this;
      }
      return AbstractBooleanSet.UNCHECKED_EMPTY_ASCENDING;
    }
    @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
      if(fromInclusive && !fromElement && toElement^toInclusive){
        return this;
      }
      return AbstractBooleanSet.UNCHECKED_EMPTY_ASCENDING;
    }
    private static class Descending extends UncheckedFalseView{
      private Descending(BooleanSetImpl root){
        super(root);
      }
      @Override public OmniNavigableSet.OfBoolean descendingSet(){
        return new UncheckedFalseView(root);
      }
      @Override public BooleanComparator comparator(){
        return BooleanComparator::descendingCompare;
      }
      @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
        return this;
      }
      @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
        if(fromElement^inclusive){
          return this;
        }
        return AbstractBooleanSet.UNCHECKED_EMPTY_DESCENDING;
      }
      @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement){
        if(toElement){
          return AbstractBooleanSet.UNCHECKED_EMPTY_DESCENDING;
        }
        return this;
      }
      @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
        if(toElement==inclusive){
          return AbstractBooleanSet.UNCHECKED_EMPTY_DESCENDING;
        }  
        return this;
      }
      @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
        if(!fromElement && !toElement){
          return this;
        }
        return AbstractBooleanSet.UNCHECKED_EMPTY_DESCENDING;
      }
      @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
        if(toInclusive && !toElement && fromElement^fromInclusive){
          return this;
        }
        return AbstractBooleanSet.UNCHECKED_EMPTY_DESCENDING;    
      }
    }
    private static class Checked extends UncheckedFalseView{
      private Checked(BooleanSetImpl root){
        super(root);
      }
      @Override public void forEach(BooleanConsumer action){
        final BooleanSetImpl root;
        final int state;
        if(((state=(root=this.root).state)&0b01)!=0){
          try{
            action.accept(false);
          }finally{
             CheckedCollection.checkModCount(state,root.state);
          }
        }
      }
      @Override public void forEach(Consumer<? super Boolean> action){
        final BooleanSetImpl root;
        final int state;
        if(((state=(root=this.root).state)&0b01)!=0){
          try{
            action.accept(Boolean.FALSE);
          }finally{
             CheckedCollection.checkModCount(state,root.state);
          }
        }
      }
      @Override public boolean removeIf(BooleanPredicate filter){
        final BooleanSetImpl root;
        final int state;
        if(((state=(root=this.root).state)&0b01)!=0){
        	try{
            if(!filter.test(false)){
              return false;
            }
          }finally{
            CheckedCollection.checkModCount(state,root.state);
          }
          root.state=state&0b10;
          return true;
        }
        return false;
      }
      @Override public boolean removeIf(Predicate<? super Boolean> filter){
        final BooleanSetImpl root;
        final int state;
        if(((state=(root=this.root).state)&0b01)!=0){
        	try{
            if(!filter.test(Boolean.FALSE)){
              return false;
            }
          }finally{
            CheckedCollection.checkModCount(state,root.state);
          }
          root.state=state&0b10;
          return true;
        }
        return false;
      }
      @Override public boolean firstBoolean(){
        if((root.state&0b01)==0){
          throw new NoSuchElementException();
        }
        return false;
      }
      @Override public boolean lastBoolean(){
        if((root.state&0b01)==0){
          throw new NoSuchElementException();
        }
        return false;
      }
      @Override public OmniNavigableSet.OfBoolean descendingSet(){
          return new UncheckedFalseView.Checked.Descending(root);
        }
      @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        final BooleanSetImpl root;
        final int state;
        final T[] dst;
        final int size=(state=(root=this.root).state)&0b01;
        try{
          dst=arrConstructor.apply(size);
        }finally{
          CheckedCollection.checkModCount(state,root.state);
        }
        if(size!=0){
          dst[0]=(T)Boolean.FALSE;
        }
        return dst;
      }
      @Override public OmniIterator.OfBoolean iterator(){
        final BooleanSetImpl root;
        if(((root=this.root).state)==0b00){
          return EMPTY_ITR;
        }
        return new CheckedFalseItr(root,0b10);
      }
      @Override public OmniIterator.OfBoolean descendingIterator(){
        final BooleanSetImpl root;
        if(((root=this.root).state)==0b00){
          return EMPTY_ITR;
        }
        return new CheckedFalseItr(root,0b10);
      }
      @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
        if(fromElement){
          return AbstractBooleanSet.CHECKED_EMPTY_ASCENDING_MIDDLE;
        }
        return this;
      }
      @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
        if(fromElement==inclusive){
          return AbstractBooleanSet.CHECKED_EMPTY_ASCENDING_MIDDLE;
        }else if(inclusive){
          return this;
        }
        throw new IllegalArgumentException("out of bounds");
      }
      @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement){
        if(!toElement){
          return this;
        }
        throw new IllegalArgumentException("out of bounds");
      }
      @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
        if(toElement^inclusive){
          return this;
        }else if(!toElement){
          return AbstractBooleanSet.CHECKED_EMPTY_ASCENDING_HEAD;
        }
        throw new IllegalArgumentException("out of bounds");
      }
      @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
        if(!toElement){
          if(fromElement){
            return AbstractBooleanSet.CHECKED_EMPTY_ASCENDING_MIDDLE;
          }else{
            return this;
          }
        }
        throw new IllegalArgumentException("out of bounds"); 
      }
      @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
        if(toElement ^ toInclusive){
          if(fromElement==fromInclusive){
            return AbstractBooleanSet.CHECKED_EMPTY_ASCENDING_MIDDLE;
          }else if(fromInclusive){
            return this;
          }
        }else if(!toInclusive && fromInclusive && !fromElement){
          return AbstractBooleanSet.CHECKED_EMPTY_ASCENDING_HEAD;
        }
        throw new IllegalArgumentException("out of bounds");
      }
      private static class Descending extends Checked{
        private Descending(BooleanSetImpl root){
          super(root);
        }
        @Override public BooleanComparator comparator(){
          return BooleanComparator::descendingCompare;
        }
        @Override public OmniNavigableSet.OfBoolean descendingSet(){
          return new UncheckedFalseView.Checked(root);
        }
        @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
          if(!fromElement){
            return this;
          }
          throw new IllegalArgumentException("out of bounds");
        }
        @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
          if(fromElement^inclusive){
            return this;
          }else if(!fromElement){
            return AbstractBooleanSet.CHECKED_EMPTY_DESCENDING_TAIL;
          }
          throw new IllegalArgumentException("out of bounds");
        }
        @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement){
          if(toElement){
            return AbstractBooleanSet.CHECKED_EMPTY_DESCENDING_MIDDLE;
          }
          return this;
        }
        @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
          if(toElement==inclusive){
            return AbstractBooleanSet.CHECKED_EMPTY_DESCENDING_MIDDLE;
          }else if(inclusive){
            return this;
          }
          throw new IllegalArgumentException("out of bounds");
        }
        @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
          if(!fromElement){
            if(toElement){
              return AbstractBooleanSet.CHECKED_EMPTY_DESCENDING_MIDDLE;
            }else{
              return this;
            }
          }
          throw new IllegalArgumentException("out of bounds"); 
        }
        @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
          if(fromElement^fromInclusive){
            if(toElement==toInclusive){
              return AbstractBooleanSet.CHECKED_EMPTY_DESCENDING_MIDDLE;
            }else if(toInclusive){
              return this;
            }
          }else if(!fromElement && toInclusive && !toElement){
            return AbstractBooleanSet.CHECKED_EMPTY_DESCENDING_TAIL;
          }
          throw new IllegalArgumentException("out of bounds");
        }
      }
    }
  }
  private static class UncheckedAscendingFullItr extends AbstractBooleanItr{
    // valid itrStates
    // state | next        | forEachRemaining | expected root state | remove
    // 0b00  | undefined   | do nothing       | any                 | if root contains true, remove true, else remove false
    // 0b01  | false->0b00 | false            | 0b01                | undefined
    // 0b10  | true ->0b00 | true             | 0b11                | remove false
    // 0b11  | false->0b10 | false,true       | 0b11                | undefined
    transient final BooleanSetImpl root;
    transient int itrState;
    private UncheckedAscendingFullItr(BooleanSetImpl root,int itrState){
      this.root=root;
      this.itrState=itrState;
    }
    @Override public Object clone(){
      return new UncheckedAscendingFullItr(root,itrState);
    }
    @Override public boolean hasNext(){
      return this.itrState!=0;
    }
    @Override public boolean nextBoolean(){
      switch(this.itrState){
        case 0b11:
          this.itrState=0b10;
          break;
        case 0b10:
          this.itrState=0b00;
          return true;
        default:
          this.itrState=0b00;
      } 
      return false;
    }
    @Override public void remove(){
      if(itrState==0){
        final BooleanSetImpl root;
        final int rootState;
        (root=this.root).state=(rootState=root.state)-((rootState&0b10)==0?0b01:0b10);
      }else{
        root.state=0b10;
      }
    }
    @Override public void forEachRemaining(BooleanConsumer action){
      switch(itrState){
        default:
          return;
        case 0b11:
          action.accept(false);
        case 0b10:
          action.accept(true);
          break;
        case 0b01:
          action.accept(false);
      }
      this.itrState=0b00;
    }
    @Override public void forEachRemaining(Consumer<? super Boolean> action){
      switch(itrState){
        default:
          return;
        case 0b11:
          action.accept(Boolean.FALSE);
        case 0b10:
          action.accept(Boolean.TRUE);
          break;
        case 0b01:
          action.accept(Boolean.FALSE);
      }
      this.itrState=0b00;
    }
  }
  private static class CheckedAscendingFullItr extends UncheckedAscendingFullItr{
    // valid itrStates
    // state | next         | forEachRemaining | expected root state  | remove
    // 0b000 | throw NSE    | do nothing       | 0b00 OR 0b01         | throw ISE
    // 0b001 | false->0b100 | false            | 0b01                 | throw ISE
    // 0b010 | true ->0b100 | true             | 0b10                 | throw ISE
    // 0b011 | false->0b110 | false,true       | 0b11                 | throw ISE
    // 0b100 | throw NSE    | do nothing       | 0b01 OR 0b10 OR 0b11 | if root contains true, remove true, else remove false
    // 0b110 | true ->0b100 | true             | 0b11                 | remove false
    transient int expectedRootState;
    private CheckedAscendingFullItr(BooleanSetImpl root,int itrState){
      super(root,itrState);
      this.expectedRootState=itrState;
    }
    @Override public Object clone(){
      return new CheckedAscendingFullItr(root,itrState);
    }
    @Override public boolean hasNext(){
      return (this.itrState&0b11)!=0;
    }
    @Override public boolean nextBoolean(){
      CheckedCollection.checkModCount(expectedRootState,this.root.state);
      switch(itrState&0b11){
        default:
          throw new NoSuchElementException();
        case 0b01:
          this.itrState=0b100;
          return false;
        case 0b10:
          this.itrState=0b100;
          return true;
        case 0b11:
          this.itrState=0b110;
          return false; 
      }
    }
    @Override public void remove(){
        final int itrState;
        if(((itrState=this.itrState)&0b100)!=0){
          final BooleanSetImpl root;
          int rootState;
          CheckedCollection.checkModCount(rootState=expectedRootState,(root=this.root).state);
          if(itrState==0b100){
            if(rootState==0b11){
              root.state=0b01;
              this.expectedRootState=0b01;
            }else{
              root.state=0b00;
              this.expectedRootState=0b00;
            }
            this.itrState=0b000;
          }else{
            this.itrState=0b010;
            root.state=rootState&=0b10;
            this.expectedRootState=rootState;
          }
          return;
        }
        throw new IllegalStateException();
    }
    @Override public void forEachRemaining(BooleanConsumer action){
      final int itrState=this.itrState;
      try{
    	  switch(itrState&0b11){
    	    default:
    	      return;
            case 0b11:
              action.accept(false);
            case 0b10:
              action.accept(true);
              break;
            case 0b01:
              action.accept(false);
    	  }
    	  CheckedCollection.checkModCount(expectedRootState,root.state,itrState,this.itrState);
      }catch(ConcurrentModificationException e){
        throw e;
      }catch(RuntimeException e){
        throw CheckedCollection.checkModCount(expectedRootState,root.state,itrState,this.itrState,e);
      }
      this.itrState=0b100;
    }
    @Override public void forEachRemaining(Consumer<? super Boolean> action){
      final int itrState=this.itrState;
      try{
    	  switch(itrState&0b11){
    	    default:
    	      return;
            case 0b11:
              action.accept(Boolean.FALSE);
            case 0b10:
              action.accept(Boolean.TRUE);
              break;
            case 0b01:
              action.accept(Boolean.FALSE);
    	  }
    	  CheckedCollection.checkModCount(expectedRootState,root.state,itrState,this.itrState);
      }catch(ConcurrentModificationException e){
        throw e;
      }catch(RuntimeException e){
        throw CheckedCollection.checkModCount(expectedRootState,root.state,itrState,this.itrState,e);
      }
      this.itrState=0b100;
    }
  }
  private static class UncheckedDescendingFullItr extends UncheckedAscendingFullItr{
    // valid itrStates
    // state | next        | forEachRemaining | expected root state | remove
    // 0b00  | undefined   | do nothing       | any                 | if root contains false, remove false, else remove true
    // 0b01  | false->0b00 | false            | 0b11                | remove true
    // 0b10  | true ->0b00 | true             | 0b10                | undefined
    // 0b11  | true ->0b01 | true,false       | 0b11                | undefined
    private UncheckedDescendingFullItr(BooleanSetImpl root,int itrState){
      super(root,itrState);
    }
    @Override public Object clone(){
      return new UncheckedDescendingFullItr(root,itrState);
    }
    @Override public boolean nextBoolean(){
      switch(this.itrState){
        case 0b11:
          this.itrState=0b01;
          break;
        case 0b01:
          this.itrState=0b00;
          return false;
        default:
          this.itrState=0b00;
          break;
      } 
      return true;
    }
    @Override public void remove(){
      if(itrState==0){
        final BooleanSetImpl root;
        final int rootState;
        (root=this.root).state=(rootState=root.state)-((rootState&0b01)==0?0b10:0b01);
      }else{
        root.state=0b01;
      }
    }
    @Override public void forEachRemaining(BooleanConsumer action){
      switch(itrState){
        default:
          return;
        case 0b11:
          action.accept(true);
        case 0b01:
          action.accept(false);
          break;
        case 0b10:
          action.accept(true);
      }
      this.itrState=0b00;
    }
    @Override public void forEachRemaining(Consumer<? super Boolean> action){
      switch(itrState){
        default:
          return;
        case 0b11:
          action.accept(Boolean.TRUE);
        case 0b01:
          action.accept(Boolean.FALSE);
          break;
        case 0b10:
          action.accept(Boolean.TRUE);
      }
      this.itrState=0b00;
    }
  }
  private static class CheckedDescendingFullItr extends CheckedAscendingFullItr{
    // valid itrStates
    // state | next         | forEachRemaining | expected root state  | remove
    // 0b000 | throw NSE    | do nothing       | 0b00 OR 0b10         | throw ISE
    // 0b001 | false->0b100 | false            | 0b01                 | throw ISE
    // 0b010 | true ->0b100 | true             | 0b10                 | throw ISE
    // 0b011 | true ->0b101 | true,false       | 0b11                 | throw ISE
    // 0b100 | throw NSE    | do nothing       | 0b01 OR 0b10 OR 0b11 | if root contains false, remove false, else remove true
    // 0b101 | false->0b100 | false            | 0b11                 | remove true
    private CheckedDescendingFullItr(BooleanSetImpl root,int itrState){
      super(root,itrState);
    }
    @Override public Object clone(){
      return new CheckedDescendingFullItr(root,itrState);
    }
    @Override public boolean nextBoolean(){
      CheckedCollection.checkModCount(expectedRootState,this.root.state);
      switch(itrState&0b11){
        default:
          throw new NoSuchElementException();
        case 0b01:
          this.itrState=0b100;
          return false;
        case 0b10:
          this.itrState=0b100;
          return true;
        case 0b11:
          this.itrState=0b101;
          return true;
      }
    }
    @Override public void remove(){
        final int itrState;
        if(((itrState=this.itrState)&0b100)!=0){
          final BooleanSetImpl root;
          int rootState;
          CheckedCollection.checkModCount(rootState=expectedRootState,(root=this.root).state);
          if(itrState==0b100){
            if(rootState==0b11){
              root.state=0b10;
              this.expectedRootState=0b10;
            }else{
              root.state=0b00;
              this.expectedRootState=0b00;
            }
            this.itrState=0b000;
          }else{
            this.itrState=0b001;
            root.state=rootState&=0b01;
            this.expectedRootState=rootState;
          }
          return;
        }
        throw new IllegalStateException();
    }
    @Override public void forEachRemaining(BooleanConsumer action){
      final int itrState=this.itrState;
      try{
    	  switch(itrState&0b11){
    	    default:
    	      return;
            case 0b11:
              action.accept(true);
            case 0b01:
              action.accept(false);
              break;
            case 0b10:
              action.accept(true);
    	  }
    	  CheckedCollection.checkModCount(expectedRootState,root.state,itrState,this.itrState);
      }catch(ConcurrentModificationException e){
        throw e;
      }catch(RuntimeException e){
        throw CheckedCollection.checkModCount(expectedRootState,root.state,itrState,this.itrState,e);
      }
      this.itrState=0b100;
    }
    @Override public void forEachRemaining(Consumer<? super Boolean> action){
      final int itrState=this.itrState;
      try{
    	  switch(itrState&0b11){
    	    default:
    	      return;
            case 0b11:
              action.accept(Boolean.TRUE);
            case 0b01:
              action.accept(Boolean.FALSE);
              break;
            case 0b10:
              action.accept(Boolean.TRUE);
    	  }
    	  CheckedCollection.checkModCount(expectedRootState,root.state,itrState,this.itrState);
      }catch(ConcurrentModificationException e){
        throw e;
      }catch(RuntimeException e){
        throw CheckedCollection.checkModCount(expectedRootState,root.state,itrState,this.itrState,e);
      }
      this.itrState=0b100;
    }
  }
  private static class UncheckedTrueItr extends UncheckedAscendingFullItr{
    // valid itrStates
    // state | previously returned | next return | nextBoolean post-state | remove      | forEachRemaining | expected root state
    // 0b0   | true                | undefined   | undefined              | remove true | do nothing       | 0b10 OR 0b01 OR 0b11 OR 0b00
    // 0b1   | undefined           | true        | 0b0                    | undefined   | true             | 0b10 OR 0b11
    private UncheckedTrueItr(BooleanSetImpl root,int itrState){
      super(root,itrState);
    }
    @Override public Object clone(){
      return new UncheckedTrueItr(root,itrState);
    }
    @Override public boolean nextBoolean(){
      itrState=0b0;
      return true;
    }
    @Override public void remove(){
      root.state&=0b01;
    }
    @Override public void forEachRemaining(BooleanConsumer action){
      if(itrState==0b1){
        action.accept(true);
        itrState=0b0;
      }
    }
    @Override public void forEachRemaining(Consumer<? super Boolean> action){
      if(itrState==0b1){
        action.accept(Boolean.TRUE);
        itrState=0b0;
      }
    }
  }
  private static class UncheckedFalseItr extends UncheckedTrueItr{
    // valid itrStates
    // state | previously returned | next return | nextBoolean post-state | remove      | forEachRemaining | expected root state
    // 0b0   | false               | undefined   | undefined              | remove false| do nothing       | 0b10 OR 0b01 OR 0b11 OR 0b00
    // 0b1   | undefined           | false       | 0b0                    | undefined   | false            | 0b01 OR 0b11
    private UncheckedFalseItr(BooleanSetImpl root,int itrState){
      super(root,itrState);
    }
    @Override public Object clone(){
      return new UncheckedFalseItr(root,itrState);
    }
    @Override public boolean nextBoolean(){
      itrState=0b0;
      return false;
    }
    @Override public void remove(){
      root.state&=0b10;
    }
    @Override public void forEachRemaining(BooleanConsumer action){
      if(itrState==0b1){
        action.accept(false);
        itrState=0b0;
      }
    }
    @Override public void forEachRemaining(Consumer<? super Boolean> action){
      if(itrState==0b1){
        action.accept(Boolean.FALSE);
        itrState=0b0;
      }
    }
  }
  private static class CheckedTrueItr extends UncheckedAscendingFullItr{
    // valid itrStates
    // state | previously returned | next return | nextBoolean post-state | remove      | remove post-state | forEachRemaining | forEachRemaining post-state | expected root state
    // 0b00  | undefined           | throw NSE   | 0b00                   | throw ISE   | 0b00              | do nothing       | 0b00                        | 0b00 OR 0b01
    // 0b01  | true                | throw NSE   | 0b01                   | remove true | 0b00              | do nothing       | 0b01                        | 0b10 OR 0b11
    // 0b10  | undefined           | return true | 0b01                   | throw ISE   | 0b10              | true             | 0b01                        | 0b10 OR 0b11
    private CheckedTrueItr(BooleanSetImpl root,int itrState){
      super(root,itrState);
    }
    @Override public Object clone(){
      return new CheckedTrueItr(root,itrState);
    }
    @Override public boolean hasNext(){
      return itrState==0b10;
    }
    @Override public boolean nextBoolean(){
      switch((itrState<<1)|(root.state>>>1)){
      case 0b000:
      case 0b011:
        throw new NoSuchElementException();
      default:
        throw new ConcurrentModificationException();
      case 0b101:
      }
      itrState=0b01;
      return true;
    }
    @Override public void remove(){
      final BooleanSetImpl root;
      final int rootState;
      switch((itrState<<1)|((rootState=(root=this.root).state)>>>1)){
      default:
        throw new IllegalStateException();
      case 0b010:
        throw new ConcurrentModificationException();
      case 0b011:
        root.state=rootState-0b10;
        itrState=0b00;
      }
    }
    @Override public void forEachRemaining(BooleanConsumer action){
      switch((itrState<<1)|(root.state>>>1)){
      default:
        throw new ConcurrentModificationException();
      case 0b101:
        action.accept(true);
        itrState=0b01;
      case 0b000:
      case 0b011:
      }
    }
    @Override public void forEachRemaining(Consumer<? super Boolean> action){
      switch((itrState<<1)|(root.state>>>1)){
      default:
        throw new ConcurrentModificationException();
      case 0b101:
        action.accept(Boolean.TRUE);
        itrState=0b01;
      case 0b000:
      case 0b011:
      }
    }
  }
  private static class CheckedFalseItr extends CheckedTrueItr{
    // valid itrStates
    // state | previously returned | next return  | nextBoolean post-state | remove       | remove post-state | forEachRemaining | forEachRemaining post-state | expected root state
    // 0b00  | undefined           | throw NSE    | 0b00                   | throw ISE    | 0b00              | do nothing       | 0b00                        | 0b00 OR 0b10
    // 0b01  | false               | throw NSE    | 0b01                   | remove false | 0b00              | do nothing       | 0b01                        | 0b01 OR 0b11
    // 0b10  | undefined           | return false | 0b01                   | throw ISE    | 0b10              | false            | 0b01                        | 0b01 OR 0b11
    private CheckedFalseItr(BooleanSetImpl root,int itrState){
      super(root,itrState);
    }
    @Override public Object clone(){
      return new CheckedFalseItr(root,itrState);
    }
    @Override public boolean nextBoolean(){
      switch((itrState<<1)|(root.state&0b01)){
      case 0b000:
      case 0b011:
        throw new NoSuchElementException();
      default:
        throw new ConcurrentModificationException();
      case 0b101:
      }
      itrState=0b01;
      return false;
    }
    @Override public void remove(){
      final BooleanSetImpl root;
      final int rootState;
      switch((itrState<<1)|((rootState=(root=this.root).state)&1)){
      default:
        throw new IllegalStateException();
      case 0b010:
        throw new ConcurrentModificationException();
      case 0b011:
        root.state=rootState-0b01;
        itrState=0b00;
      }
    }
    @Override public void forEachRemaining(BooleanConsumer action){
      switch((itrState<<1)|(root.state&0b01)){
      default:
        throw new ConcurrentModificationException();
      case 0b101:
        action.accept(false);
        itrState=0b01;
      case 0b000:
      case 0b011:
      }
    }
    @Override public void forEachRemaining(Consumer<? super Boolean> action){
      switch((itrState<<1)|(root.state&0b01)){
      default:
        throw new ConcurrentModificationException();
      case 0b101:
        action.accept(Boolean.FALSE);
        itrState=0b01;
      case 0b000:
      case 0b011:
      }
    }
  }
}
