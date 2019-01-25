package omni.util;
import java.util.Objects;
import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import omni.function.BooleanPredicate;
import omni.function.BytePredicate;
import omni.function.CharPredicate;
import omni.function.FloatPredicate;
import omni.function.ShortPredicate;
public interface OmniPred{
  interface OfBoolean{
    static BooleanPredicate getEqualsPred(boolean val){
      if(val){ return TypeUtil::equalsTrue; }
      return TypeUtil::equalsFalse;
    }
  }
  interface OfByte{
    static BytePredicate getEqualsPred(int val){
      return v->v==val;
    }
  }
  interface OfChar{
    static CharPredicate getEqualsPred(int val){
      return v->v==val;
    }
  }
  interface OfDouble{
    static DoublePredicate equalsDblBits(long dblBits){
      return v->Double.doubleToRawLongBits(v)==dblBits;
    }
    static DoublePredicate getEqualsPred(boolean val){
      if(val){ return equalsDblBits(TypeUtil.DBL_TRUE_BITS); }
      return TypeUtil::equalsDbl0;
    }
    static DoublePredicate getEqualsPred(double val){
      if(val==val){ return equalsDblBits(Double.doubleToRawLongBits(val)); }
      return Double::isNaN;
    }
    static DoublePredicate getEqualsPred(float val){
      if(val==val){ return equalsDblBits(Double.doubleToRawLongBits(val)); }
      return Double::isNaN;
    }
    static DoublePredicate getEqualsPred(int val){
      if(val!=0){ return equalsDblBits(Double.doubleToRawLongBits(val)); }
      return TypeUtil::equalsDbl0;
    }
  }
  interface OfFloat{
    static FloatPredicate equalsFltBits(int fltBits){
      return v->Float.floatToRawIntBits(v)==fltBits;
    }
    static FloatPredicate getEqualsPred(boolean val){
      if(val){ return equalsFltBits(TypeUtil.FLT_TRUE_BITS); }
      return TypeUtil::equalsFlt0;
    }
    static FloatPredicate getEqualsPred(float val){
      if(val==val){ return equalsFltBits(Float.floatToRawIntBits(val)); }
      return Float::isNaN;
    }
    static FloatPredicate getEqualsRawPred(int val){
      if(val!=0){ return equalsFltBits(Float.floatToRawIntBits(val)); }
      return TypeUtil::equalsFlt0;
    }
  }
  interface OfInt{
    static IntPredicate getEqualsPred(int val){
      return v->v==val;
    }
  }
  interface OfLong{
    static LongPredicate getEqualsPred(long val){
      return v->v==val;
    }
  }
  interface OfRef{
    static <E> Predicate<E> getEqualsDblBitsPred(long dblBits){
      return v->TypeUtil.refEqualsDblBits(v,dblBits);
    }
    static <E> Predicate<E> getEqualsFltBitsPred(int fltBits){
      return v->TypeUtil.refEqualsFltBits(v,fltBits);
    }
    static <E> Predicate<E> getEqualsPred(boolean val){
      if(val){ return TypeUtil::refEqualsTrue; }
      return TypeUtil::refEqualsFalse;
    }
    static <E> Predicate<E> getEqualsPred(Boolean val){
      if(val!=null){ return getEqualsPred((boolean)val); }
      return Objects::isNull;
    }
    static <E> Predicate<E> getEqualsPred(byte val){
      return v->TypeUtil.refEquals(v,val);
    }
    static <E> Predicate<E> getEqualsPred(Byte val){
      if(val!=null){ return getEqualsPred((byte)val); }
      return Objects::isNull;
    }
    static <E> Predicate<E> getEqualsPred(char val){
      return v->TypeUtil.refEquals(v,val);
    }
    static <E> Predicate<E> getEqualsPred(Character val){
      if(val!=null){ return getEqualsPred((char)val); }
      return Objects::isNull;
    }
    static <E> Predicate<E> getEqualsPred(double val){
      if(val==val){ return getEqualsDblBitsPred(Double.doubleToRawLongBits(val)); }
      return TypeUtil::refEqualsDblNaN;
    }
    static <E> Predicate<E> getEqualsPred(Double val){
      if(val!=null){ return getEqualsPred((double)val); }
      return Objects::isNull;
    }
    static <E> Predicate<E> getEqualsPred(float val){
      if(val==val){ return getEqualsFltBitsPred(Float.floatToRawIntBits(val)); }
      return TypeUtil::refEqualsFltNaN;
    }
    static <E> Predicate<E> getEqualsPred(Float val){
      if(val!=null){ return getEqualsPred((float)val); }
      return Objects::isNull;
    }
    static <E> Predicate<E> getEqualsPred(int val){
      return v->TypeUtil.refEquals(v,val);
    }
    static <E> Predicate<E> getEqualsPred(Integer val){
      if(val!=null){ return getEqualsPred((int)val); }
      return Objects::isNull;
    }
    static <E> Predicate<E> getEqualsPred(long val){
      return v->TypeUtil.refEquals(v,val);
    }
    static <E> Predicate<E> getEqualsPred(Long val){
      if(val!=null){ return getEqualsPred((long)val); }
      return Objects::isNull;
    }
    static <E> Predicate<E> getEqualsPred(Object val){
      if(val!=null){ return val::equals; }
      return Objects::isNull;
    }
    static <E> Predicate<E> getEqualsPred(short val){
      return v->TypeUtil.refEquals(v,val);
    }
    static <E> Predicate<E> getEqualsPred(Short val){
      if(val!=null){ return getEqualsPred((short)val); }
      return Objects::isNull;
    }
  }
  interface OfShort{
    static ShortPredicate getEqualsPred(int val){
      return v->v==val;
    }
  }
}
