package omni.impl.set;

import omni.api.OmniNavigableSet;
import omni.util.TypeUtil;

abstract class AbstractBooleanSet implements OmniNavigableSet.OfBoolean{
  abstract boolean containsTrue();
  abstract boolean containsFalse();
  abstract boolean removeTrue();
  abstract boolean removeFalse();
  abstract boolean addTrue();
  abstract boolean addFalse();
  @Override public boolean contains(Object val){
    containsTrue:for(;;) {
      containsFalse:for(;;) {
        if(val instanceof Boolean) {
          if((boolean)val) {
            break containsTrue;
          }
          break containsFalse;
        }else if(val instanceof Integer || val instanceof Byte || val instanceof Short) {
          switch(((Number)val).intValue()) {
          case 0:
            break containsFalse;
          case 1:
            break containsTrue;
          default:
          }
        }else if(val instanceof Long) {
          final long l;
          if((l=(long)val)==0L) {
            break containsFalse;
          }else if(l==1L) {
            break containsTrue;
          }
        }else if(val instanceof Float) {
          switch(Float.floatToRawIntBits((float)val)) {
          case 0:
          case Integer.MIN_VALUE:
            break containsFalse;
          case TypeUtil.FLT_TRUE_BITS:
            break containsTrue;
          }
        }else if(val instanceof Double) {
          final long bits;
          if((bits=Double.doubleToRawLongBits((double)val))==0L || bits==Long.MIN_VALUE) {
            break containsFalse;
          }else if(bits==TypeUtil.DBL_TRUE_BITS) {
            break containsTrue;
          }
        }else if(val instanceof Character) {
          switch(((Character)val).charValue()) {
          case 0:
            break containsFalse;
          case 1:
            break containsTrue;
          default:
          }
        }
        return false;
      }
      return containsFalse();
    }
    return containsTrue();
  }
  @Override public boolean contains(boolean val){
    if(val) {
      return containsTrue();
    }else {
      return containsFalse();
    }
  }
  @Override public boolean contains(int val){
    switch(val) {
    case 0:
      return containsFalse();
    case 1:
      return containsTrue();
    default:
      return false;
    }
  }
  @Override public boolean contains(long val){
    if(val==0L) {
      return containsFalse();
    }
    return val==1L && containsTrue();
  }
  @Override public boolean contains(float val){
    switch(Float.floatToRawIntBits(val)) {
    case 0:
    case Integer.MIN_VALUE:
      return containsFalse();
    case TypeUtil.FLT_TRUE_BITS:
      return containsTrue();
    default:
      return false;
    }
  }
  @Override public boolean contains(double val){
    final long bits;
    if((bits=Double.doubleToRawLongBits(val))==0L || bits==Long.MIN_VALUE) {
      return containsFalse();
    }
    return bits==TypeUtil.DBL_TRUE_BITS && containsTrue();
  }
  @Override public boolean remove(Object val){
    removeTrue:for(;;) {
      removeFalse:for(;;) {
        if(val instanceof Boolean) {
          if((boolean)val) {
            break removeTrue;
          }
          break removeFalse;
        }else if(val instanceof Integer || val instanceof Byte || val instanceof Short) {
          switch(((Number)val).intValue()) {
          case 0:
            break removeFalse;
          case 1:
            break removeTrue;
          default:
          }
        }else if(val instanceof Long) {
          final long l;
          if((l=(long)val)==0L) {
            break removeFalse;
          }else if(l==1L) {
            break removeTrue;
          }
        }else if(val instanceof Float) {
          switch(Float.floatToRawIntBits((float)val)) {
          case 0:
          case Integer.MIN_VALUE:
            break removeFalse;
          case TypeUtil.FLT_TRUE_BITS:
            break removeTrue;
          }
        }else if(val instanceof Double) {
          final long bits;
          if((bits=Double.doubleToRawLongBits((double)val))==0L || bits==Long.MIN_VALUE) {
            break removeFalse;
          }else if(bits==TypeUtil.DBL_TRUE_BITS) {
            break removeTrue;
          }
        }else if(val instanceof Character) {
          switch(((Character)val).charValue()) {
          case 0:
            break removeFalse;
          case 1:
            break removeTrue;
          default:
          }
        }
        return false;
      }
      return removeFalse();
    }
    return removeTrue();
  }
  @Override public boolean removeVal(boolean val){
    if(val) {
      return removeTrue();
    }
    return removeFalse();
  }
  @Override public boolean removeVal(int val){
    switch(val) {
    case 0:
      return removeFalse();
    case 1:
      return removeTrue();
    default:
      return false;
    }
  }
  @Override public boolean removeVal(long val){
    if(val==0L) {
      return removeFalse();
    }
    return val==1L && removeTrue();
  }
  @Override public boolean removeVal(float val){
    switch(Float.floatToRawIntBits(val)) {
    case 0:
    case Integer.MIN_VALUE:
      return removeFalse();
    case TypeUtil.FLT_TRUE_BITS:
      return removeTrue();
    default:
      return false;
    }
  }
  @Override public boolean removeVal(double val){
    final long bits;
    if((bits=Double.doubleToRawLongBits(val))==0L || bits==Long.MIN_VALUE) {
      return removeFalse();
    }
    return bits==TypeUtil.DBL_TRUE_BITS && removeTrue();
  }
  @Override public boolean add(Boolean val){
    if(val) {
      return addTrue();
    }else {
      return addFalse();
    }
  }
  @Override public boolean add(boolean val){
    if(val) {
      return addTrue();
    }else {
      return addFalse();
    }
  }
}
