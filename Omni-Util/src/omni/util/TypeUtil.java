package omni.util;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
import omni.function.FloatConsumer;
import omni.function.FloatPredicate;
public interface TypeUtil{
  /** Integral values greater than this might lose magnitude when cast to {@code float}. */
  int MAX_SAFE_INT=(1<<24)-1;
  /** Integral values less than this might lose magnitude when cast to {@code float}. */
  int MIN_SAFE_INT=-MAX_SAFE_INT;
  /** Integral values greater than this might lose magnitude when cast to {@code double}. */
  long MAX_SAFE_LONG=(1L<<53)-1;
  /** Integral values less than this might lose magnitude when cast to {@code double}. */
  long MIN_SAFE_LONG=-MAX_SAFE_LONG;
  int FLT_TRUE_BITS=0x3f800000;
  long DBL_TRUE_BITS=0x3ff0000000000000L;
  int FLT_NAN_BITS=0x7fc00000;
  long DBL_NAN_BITS=0x7ff8000000000000L;
  static byte castToByte(boolean val){
    if(val){ return 1; }
    return 0;
  }
  static char castToChar(boolean val){
    if(val){ return 1; }
    return 0;
  }
  static double castToDouble(boolean val){
    if(val){ return 1; }
    return 0;
  }
  static float castToFloat(boolean val){
    if(val){ return 1; }
    return 0;
  }
  static long castToLong(boolean val){
    if(val){ return 1; }
    return 0;
  }
  //// /**
  //// * Integral values greater than this might lose magnitude when cast to
  //// * {@code float}.
  //// */
  //// public static final int MAX_SAFE_INT=(1<<24)-1;
  //// /**
  //// * Integral values less than this might lose magnitude when cast to
  //// * {@code float}.
  //// */
  //// public static final int MIN_SAFE_INT=-MAX_SAFE_INT;
  //// /**
  //// * Integral values greater than this might lose magnitude when cast to
  //// * {@code double}.
  //// */
  //// public static final long MAX_SAFE_LONG=(1L<<53)-1;
  //// /**
  //// * Integral values less than this might lose magnitude when cast to
  //// * {@code double}.
  //// */
  //// public static final long MIN_SAFE_LONG=-MAX_SAFE_LONG;
  //// public static final int FLT_TRUE_BITS=0x3f800000;
  //// public static final long DBL_TRUE_BITS=0x3ff0000000000000L;
  //// public static byte castToByte(boolean val){
  //// if(val){ return 1; }
  //// return 0;
  //// }
  //// public static char castToChar(boolean val){
  //// if(val){ return 1; }
  //// return 0;
  //// }
  //// public static double castToDouble(boolean val){
  //// if(val){ return 1; }
  //// return 0;
  //// }
  //// public static float castToFloat(boolean val){
  //// if(val){ return 1; }
  //// return 0;
  //// }
  //// public static long castToLong(boolean val){
  //// if(val){ return 1; }
  //// return 0;
  //// }
  // public static boolean doubleEquals(double val1,double val2){
  // if(val1==val1){ return
  //// Double.doubleToRawLongBits(val1)==Double.doubleToRawLongBits(val2); }
  // return val2!=val2;
  // }
  // public static boolean doubleEquals(double val1,float val2){
  // if(val2==val2){ return
  //// Double.doubleToRawLongBits(val1)==Double.doubleToRawLongBits(val2); }
  // return val1!=val1;
  // }
  // @Deprecated
  // public static boolean doubleEquals(double val1,int val2){
  // return val1==val2;
  // }
  // public static boolean doubleEquals(double val1,long val2){
  // return OmniArray.checkCastToDouble(val2)&&val1==val2;
  // }
  // @Deprecated
  // public static boolean doubleEquals(float val1,double val2){
  // return doubleEquals(val2,val1);
  // }
  // @Deprecated
  // public static boolean floatEquals(char val1,float val2){
  // return val1==val2;
  // }
  // @Deprecated
  // public static boolean floatEquals(float val1,char val2){
  // return val1==val2;
  // }
  // public static boolean floatEquals(float val1,float val2){
  // if(val1==val1){ return
  //// Float.floatToRawIntBits(val1)==Float.floatToRawIntBits(val2); }
  // return val2!=val2;
  // }
  // @Deprecated
  // public static boolean floatEquals(float val1,int val2){
  // return (double)val1==(double)val2;
  // }
  // public static boolean floatEquals(float val1,long val2){
  // if(val2<=OmniArray.MAX_SAFE_LONG&&val2>=OmniArray.MIN_SAFE_LONG){
  // if(OmniArray.checkCastToFloat(val2)){ return val1==val2; }
  // }else
  //// if(Long.numberOfLeadingZeros(val2)+Long.numberOfTrailingZeros(val2)<11){
  //// return false; }
  // return (double)val1==(double)val2;
  // }
  // @Deprecated
  // public static boolean floatEquals(float val1,short val2){
  // return val1==val2;
  // }
  // @Deprecated
  // public static boolean floatEquals(int val1,float val2){
  // return (double)val1==(double)val2;
  // }
  // @Deprecated
  // public static boolean floatEquals(long val1,float val2){
  // return floatEquals(val2,val1);
  // }
  // @Deprecated
  // public static boolean floatEquals(short val1,float val2){
  // return val1==val2;
  // }
  //// public static Predicate<Object> getRefEqualsDblBitsPred(long dblBits){
  //// return v->TypeUtil.refEqualsDblBits(v,dblBits);
  //// }
  //// public static Predicate<Object> getRefEqualsFltBitsPred(int fltBits){
  //// return v->TypeUtil.refEqualsFltBits(v,fltBits);
  //// }
  //// public static Predicate<Object> getRefEqualsPred(boolean val){
  //// if(val){ return TypeUtil::refEqualsTrue; }
  //// return TypeUtil::refEqualsFalse;
  //// }
  //// public static Predicate<Object> getRefEqualsPred(Boolean val){
  //// if(val!=null){ return getRefEqualsPred(val.booleanValue()); }
  //// return Objects::isNull;
  //// }
  //// public static Predicate<Object> getRefEqualsPred(byte val){
  //// return v->TypeUtil.refEquals(v,val);
  //// }
  //// public static Predicate<Object> getRefEqualsPred(Byte val){
  //// if(val!=null){ return getRefEqualsPred(val.byteValue()); }
  //// return Objects::isNull;
  //// }
  //// public static Predicate<Object> getRefEqualsPred(char val){
  //// return v->TypeUtil.refEquals(v,val);
  //// }
  //// public static Predicate<Object> getRefEqualsPred(Character val){
  //// if(val!=null){ return getRefEqualsPred(val.charValue()); }
  //// return Objects::isNull;
  //// }
  //// public static Predicate<Object> getRefEqualsPred(double val){
  //// if(val==val){ return
  //// getRefEqualsDblBitsPred(Double.doubleToRawLongBits(val)); }
  //// return TypeUtil::refEqualsDblNaN;
  //// }
  //// public static Predicate<Object> getRefEqualsPred(Double val){
  //// if(val!=null){ return getRefEqualsPred(val.doubleValue()); }
  //// return Objects::isNull;
  //// }
  //// public static Predicate<Object> getRefEqualsPred(float val){
  //// if(val==val){ return getRefEqualsFltBitsPred(Float.floatToRawIntBits(val));
  //// }
  //// return TypeUtil::refEqualsFltNaN;
  //// }
  //// public static Predicate<Object> getRefEqualsPred(Float val){
  //// if(val!=null){ return getRefEqualsPred(val.floatValue()); }
  //// return Objects::isNull;
  //// }
  //// public static Predicate<Object> getRefEqualsPred(int val){
  //// return v->TypeUtil.refEquals(v,val);
  //// }
  //// public static Predicate<Object> getRefEqualsPred(Integer val){
  //// if(val!=null){ return getRefEqualsPred(val.intValue()); }
  //// return Objects::isNull;
  //// }
  //// public static Predicate<Object> getRefEqualsPred(long val){
  //// return v->TypeUtil.refEquals(v,val);
  //// }
  //// public static Predicate<Object> getRefEqualsPred(Long val){
  //// if(val!=null){ return getRefEqualsPred(val.longValue()); }
  //// return Objects::isNull;
  //// }
  //// public static Predicate<Object> getRefEqualsPred(Object val){
  //// if(val!=null){ return val::equals; }
  //// return Objects::isNull;
  //// }
  //// public static Predicate<Object> getRefEqualsPred(short val){
  //// return v->TypeUtil.refEquals(v,val);
  //// }
  //// public static Predicate<Object> getRefEqualsPred(Short val){
  //// if(val!=null){ return getRefEqualsPred(val.shortValue()); }
  //// return Objects::isNull;
  //// }
  // public static boolean refEquals(Object val1,boolean val2){
  // return val1 instanceof Boolean&&val2==(boolean)val1;
  // }
  // public static boolean refEquals(Object val1,Boolean val2){
  // return val1==val2||val1 instanceof Boolean&&val2==(boolean)val1;
  // }
  // public static boolean refEquals(Object val1,byte val2){
  // return val1 instanceof Byte&&val2==(byte)val1;
  // }
  // public static boolean refEquals(Object val1,Byte val2){
  // return val1==val2||val1 instanceof Byte&&val2==(byte)val1;
  // }
  // public static boolean refEquals(Object val1,char val2){
  // return val1 instanceof Character&&val2==(char)val1;
  // }
  // public static boolean refEquals(Object val1,Character val2){
  // return val1==val2||val1 instanceof Character&&val2==(char)val1;
  // }
  // public static boolean refEquals(Object val1,double val2){
  // return val1 instanceof Double&&doubleEquals((double)val1,val2);
  // }
  // public static boolean refEquals(Object val1,Double val2){
  // return val1==val2||val1 instanceof Double&&doubleEquals((double)val1,val2);
  // }
  // public static boolean refEquals(Object val1,float val2){
  // return val1 instanceof Float&&floatEquals((float)val1,val2);
  // }
  // public static boolean refEquals(Object val1,Float val2){
  // return val1==val2||val1 instanceof Float&&floatEquals((float)val1,val2);
  // }
  // public static boolean refEquals(Object val1,int val2){
  // return val1 instanceof Integer&&val2==(int)val1;
  // }
  // public static boolean refEquals(Object val1,Integer val2){
  // return val1==val2||val1 instanceof Integer&&val2==(int)val1;
  // }
  // public static boolean refEquals(Object val1,long val2){
  // return val1 instanceof Long&&val2==(long)val1;
  // }
  // public static boolean refEquals(Object val1,Long val2){
  // return val1==val2||val1 instanceof Long&&val2==(long)val1;
  // }
  // public static boolean refEquals(Object val1,short val2){
  // return val1 instanceof Short&&val2==(short)val1;
  // }
  // public static boolean refEquals(Object val1,Short val2){
  // return val1==val2||val1 instanceof Short&&val2==(short)val1;
  // }
  // public static boolean refEqualsDblBits(Object val1,long dblBits){
  // return val1 instanceof
  //// Double&&dblBits==Double.doubleToRawLongBits((double)val1);
  // }
  // public static boolean refEqualsDblNaN(Object val){
  // double v;
  // return val instanceof Double&&(v=(double)val)!=v;
  // }
  // public static boolean refEqualsFalse(Object val){
  // return val instanceof Boolean&&!(boolean)val;
  // }
  // public static boolean refEqualsFltBits(Object val1,int fltBits){
  // return val1 instanceof Float&&fltBits==Float.floatToRawIntBits((float)val1);
  // }
  // public static boolean refEqualsFltNaN(Object val){
  // float v;
  // return val instanceof Float&&(v=(float)val)!=v;
  // }
  // public static boolean refEqualsRawByte(Object val1,int val2){
  // return val1 instanceof Byte&&val2==(byte)val1;
  // }
  // public static boolean refEqualsRawChar(Object val1,int val2){
  // return val1 instanceof Character&&val2==(char)val1;
  // }
  // public static boolean refEqualsRawShrt(Object val1,int val2){
  // return val1 instanceof Short&&val2==(short)val1;
  // }
  //// // public static Predicate<Object> getRefEqualsPred(byte val){
  ////// return v->TypeUtil.refEquals(v,val);
  ////// }
  ////// public static Predicate<Object> getRefEqualsPred(byte val){
  ////// return v->TypeUtil.refEquals(v,val);
  ////// }
  // public static boolean refEqualsTrue(Object val){
  // return val instanceof Boolean&&(boolean)val;
  // }
  //// /**
  //// * @Deprecated This method is deprecated to discourage a developer from
  //// * accidentally calling {@link #checkCastToDouble(long)} on a
  //// * non-{@code long} value. It is useless to call it on an
  //// * {@code int} value as it will always return {@code true}.
  //// * @param val The value to check for casting
  //// * @return {@code true}
  //// */
  //// @Deprecated
  //// static boolean checkCastToDouble(int val){
  //// return true;
  //// }
  //// /**
  //// * Check to see if a {@code long} value will lose magnitude if it is cast to
  //// * {@code double}.
  //// * <p>
  //// * A {@code long} can be cast to a {@code double} value without losing
  //// precision
  //// * if the following expression evaluates to {@code true}.
  //// * <p>
  //// *
  //// * <pre>
  //// * return val <= {@link #MAX_SAFE_LONG} && val >= {@link #MIN_SAFE_LONG} ||
  //// {@link Long#numberOfLeadingZeros(long)
  //// Long.numberOfLeadingZeros(val)} + {@link Long#numberOfTrailingZeros(long)
  //// Long.numberOfTrailingZeros(val)} >= 11;
  //// * </pre>
  //// *
  //// * @param val The value to check for casting
  //// * @return {@code true} if the value can be cast to {@code double} without
  //// * losing magnitude.
  //// */
  //// static boolean checkCastToDouble(long val){
  //// return val<=MAX_SAFE_LONG&&val>=MIN_SAFE_LONG
  //// ||Long.numberOfLeadingZeros(val)+Long.numberOfTrailingZeros(val)>=11;
  //// }
  //// /**
  //// * @Deprecated This method is deprecated to discourage a developer from
  //// * accidentally calling {@link #checkCastToFloat(int)} on a
  //// * non-{@code int} value. It is useless to call it on an
  //// * {@code char} or {@code short} value as it will always return
  //// * {@code true}.
  //// * @param val The value to check for casting
  //// * @return {@code true}
  //// */
  //// @Deprecated
  //// static boolean checkCastToFloat(char val){
  //// return true;
  //// }
  //// /**
  //// * Check to see if a {@code int} value will lose magnitude if it is cast to
  //// * {@code float}.
  //// * <p>
  //// * A {@code long} can be cast to a {@code float} value without losing
  //// precision
  //// * if the following expression evaluates to {@code true}.
  //// * <p>
  //// *
  //// * <pre>
  //// * return val <= {@link #MAX_SAFE_INT} && val >= {@link #MIN_SAFE_INT} ||
  //// {@link Integer#numberOfLeadingZeros(int)
  //// Integer.numberOfLeadingZeros(val)} + {@link
  //// Integer#numberOfTrailingZeros(int)
  //// Integer.numberOfTrailingZeros(val)} >= 8;
  //// * </pre>
  //// *
  //// * @param val The value to check for casting
  //// * @return {@code true} if the value can be cast to {@code float} without
  //// losing
  //// * magnitude.
  //// */
  //// static boolean checkCastToFloat(int val){
  //// return val<=MAX_SAFE_INT&&val>=MIN_SAFE_INT
  //// ||Integer.numberOfLeadingZeros(val)+Integer.numberOfTrailingZeros(val)>=8;
  //// }
  //// /**
  //// * Check to see if a {@code long} value will lose magnitude if it is cast to
  //// * {@code float}.
  //// * <p>
  //// * A {@code long} can be cast to a {@code float} value without losing
  //// precision
  //// * if the following expression evaluates to {@code true}.
  //// * <p>
  //// *
  //// * <pre>
  //// * return val <= {@link #MAX_SAFE_INT} && val >= {@link #MIN_SAFE_INT} ||
  //// {@link Long#numberOfLeadingZeros(long)
  //// Long.numberOfLeadingZeros(val)} + {@link Long#numberOfTrailingZeros(long)
  //// Long.numberOfTrailingZeros(val)} >= 40;
  //// * </pre>
  //// *
  //// * @param val The value to check for casting
  //// * @return {@code true} if the value can be cast to {@code double} without
  //// * losing magnitude.
  //// */
  //// static boolean checkCastToFloat(long val){
  //// return
  //// val<=MAX_SAFE_INT&&val>=MIN_SAFE_INT||Long.numberOfLeadingZeros(val)+Long.numberOfTrailingZeros(val)>=40;
  //// }
  //// /**
  //// * @Deprecated This method is deprecated to discourage a developer from
  //// * accidentally calling {@link #checkCastToFloat(int)} on a
  //// * non-{@code int} value. It is useless to call it on an
  //// * {@code char} or {@code short} value as it will always return
  //// * {@code true}.
  //// * @param val The value to check for casting
  //// * @return {@code true}
  //// */
  //// @Deprecated
  //// static boolean checkCastToFloat(short val){
  //// return true;
  //// }
  //// @Deprecated
  //// static boolean doubleEquals(int val1,double val2){
  //// return val1==val2;
  //// }
  //// @Deprecated
  //// static boolean doubleEquals(long val1,double val2){
  //// return doubleEquals(val2,val1);
  //// }
  static boolean checkCastToDouble(long val){
    return val<=MAX_SAFE_LONG&&val>=MIN_SAFE_LONG||Long.numberOfLeadingZeros(val)+Long.numberOfTrailingZeros(val)>=11;
  }
  static boolean checkCastToFloat(int val){
    return val<=MAX_SAFE_INT&&val>=MIN_SAFE_INT
        ||Integer.numberOfLeadingZeros(val)+Integer.numberOfTrailingZeros(val)>=8;
  }
  static boolean checkCastToFloat(long val){
    return val<=MAX_SAFE_INT&&val>=MIN_SAFE_INT||Long.numberOfLeadingZeros(val)+Long.numberOfTrailingZeros(val)>=40;
  }
  static boolean dblBitsEquals0(long dblBits){
    return dblBits==0;
  }
  static boolean dblBitsEqualsNaN(long dblBits){
    return dblBits==0x7ff8000000000000L;
  }
  static boolean doubleEquals(double val1,double val2){
    if(val1==val1){ return Double.doubleToRawLongBits(val1)==Double.doubleToRawLongBits(val2); }
    return val2!=val2;
  }
  static boolean doubleEquals(double val1,float val2){
    if(val2==val2){ return Double.doubleToRawLongBits(val1)==Double.doubleToRawLongBits(val2); }
    return val1!=val1;
  }
  @Deprecated static boolean doubleEquals(double val1,int val2){
    return val1==val2;
  }
  static boolean doubleEquals(double val1,long val2){
    return checkCastToDouble(val2)&&val1==val2;
  }
  @Deprecated static boolean doubleEquals(float val1,double val2){
    return doubleEquals(val2,val1);
  }
  static LongConsumer doubleToLongBitsConsumer(DoubleConsumer action){
    return dblBits->action.accept(Double.longBitsToDouble(dblBits));
  }
  static LongPredicate doubleToLongBitsPredicate(DoublePredicate predicate){
    return dblBits->predicate.test(Double.longBitsToDouble(dblBits));
  }
  static boolean equalsDbl0(double val){
    return val==0;
  }
  static boolean equalsFalse(boolean val){
    return !val;
  }
  static boolean equalsFlt0(float val){
    return val==0;
  }
  static boolean equalsTrue(boolean val){
    return val;
  }
  @Deprecated static boolean floatEquals(char val1,float val2){
    return val1==val2;
  }
  @Deprecated static boolean floatEquals(float val1,char val2){
    return val1==val2;
  }
  static boolean floatEquals(float val1,float val2){
    if(val1==val1){ return Float.floatToRawIntBits(val1)==Float.floatToRawIntBits(val2); }
    return val2!=val2;
  }
  @Deprecated static boolean floatEquals(float val1,int val2){
    return (double)val1==(double)val2;
  }
  static boolean floatEquals(float val1,long val2){
    if(val2<=MAX_SAFE_LONG&&val2>=MIN_SAFE_LONG){
      if(checkCastToFloat(val2)){ return val1==val2; }
    }else if(Long.numberOfLeadingZeros(val2)+Long.numberOfTrailingZeros(val2)<11){ return false; }
    return (double)val1==(double)val2;
  }
  @Deprecated static boolean floatEquals(float val1,short val2){
    return val1==val2;
  }
  @Deprecated static boolean floatEquals(int val1,float val2){
    return (double)val1==(double)val2;
  }
  @Deprecated static boolean floatEquals(long val1,float val2){
    return floatEquals(val2,val1);
  }
  @Deprecated static boolean floatEquals(short val1,float val2){
    return val1==val2;
  }
  static IntConsumer floatToIntBitsConsumer(FloatConsumer action){
    return fltBits->action.accept(Float.intBitsToFloat(fltBits));
  }
  static IntPredicate floatToIntBitsPredicate(FloatPredicate predicate){
    return fltBits->predicate.test(Float.intBitsToFloat(fltBits));
  }
  static boolean fltBitsEquals0(int fltBits){
    return fltBits==0;
  }
  static boolean fltBitsEqualsNaN(int fltBits){
    return fltBits==0x7fc00000;
  }
  static FloatPredicate intBitsToFltPredicate(IntPredicate predicate){
    return fltVal->predicate.test(Float.floatToRawIntBits(fltVal));
  }
  static FloatConsumer longBitsToDoubleConsumer(IntConsumer action){
    return fltVal->action.accept(Float.floatToRawIntBits(fltVal));
  }
  static DoubleConsumer longBitsToDoubleConsumer(LongConsumer action){
    return dblVal->action.accept(Double.doubleToRawLongBits(dblVal));
  }
  static DoublePredicate longBitsToDoublePredicate(LongPredicate predicate){
    return dblVal->predicate.test(Double.doubleToRawLongBits(dblVal));
  }
  static boolean refEquals(Object val1,boolean val2){
    return val1 instanceof Boolean&&val2==(boolean)val1;
  }
  static boolean refEquals(Object val1,Boolean val2){
    return val1==val2||val1 instanceof Boolean&&val2==(boolean)val1;
  }
  static boolean refEquals(Object val1,byte val2){
    return val1 instanceof Byte&&val2==(byte)val1;
  }
  static boolean refEquals(Object val1,Byte val2){
    return val1==val2||val1 instanceof Byte&&val2==(byte)val1;
  }
  static boolean refEquals(Object val1,char val2){
    return val1 instanceof Character&&val2==(char)val1;
  }
  static boolean refEquals(Object val1,Character val2){
    return val1==val2||val1 instanceof Character&&val2==(char)val1;
  }
  static boolean refEquals(Object val1,double val2){
    return val1 instanceof Double&&doubleEquals((double)val1,val2);
  }
  static boolean refEquals(Object val1,Double val2){
    return val1==val2||val1 instanceof Double&&doubleEquals((double)val1,val2);
  }
  static boolean refEquals(Object val1,float val2){
    return val1 instanceof Float&&floatEquals((float)val1,val2);
  }
  static boolean refEquals(Object val1,Float val2){
    return val1==val2||val1 instanceof Float&&floatEquals((float)val1,val2);
  }
  static boolean refEquals(Object val1,int val2){
    return val1 instanceof Integer&&val2==(int)val1;
  }
  static boolean refEquals(Object val1,Integer val2){
    return val1==val2||val1 instanceof Integer&&val2==(int)val1;
  }
  static boolean refEquals(Object val1,long val2){
    return val1 instanceof Long&&val2==(long)val1;
  }
  static boolean refEquals(Object val1,Long val2){
    return val1==val2||val1 instanceof Long&&val2==(long)val1;
  }
  static boolean refEquals(Object val1,short val2){
    return val1 instanceof Short&&val2==(short)val1;
  }
  static boolean refEquals(Object val1,Short val2){
    return val1==val2||val1 instanceof Short&&val2==(short)val1;
  }
  static boolean refEqualsDblBits(Object val1,long dblBits){
    return val1 instanceof Double&&dblBits==Double.doubleToRawLongBits((double)val1);
  }
  static boolean refEqualsDblNaN(Object val){
    double v;
    return val instanceof Double&&(v=(double)val)!=v;
  }
  static boolean refEqualsFalse(Object val){
    return val instanceof Boolean&&!(boolean)val;
  }
  static boolean refEqualsFltBits(Object val1,int fltBits){
    return val1 instanceof Float&&fltBits==Float.floatToRawIntBits((float)val1);
  }
  static boolean refEqualsFltNaN(Object val){
    float v;
    return val instanceof Float&&(v=(float)val)!=v;
  }
  static boolean refEqualsRawByte(Object val1,int val2){
    return val1 instanceof Byte&&val2==(byte)val1;
  }
  static boolean refEqualsRawChar(Object val1,int val2){
    return val1 instanceof Character&&val2==(char)val1;
  }
  static boolean refEqualsRawShrt(Object val1,int val2){
    return val1 instanceof Short&&val2==(short)val1;
  }
  static boolean refEqualsTrue(Object val){
    return val instanceof Boolean&&(boolean)val;
  }
}
