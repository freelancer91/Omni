package omni.util;
import java.util.Objects;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
import java.util.function.Predicate;

import omni.function.FloatConsumer;
import omni.function.FloatPredicate;
public interface TypeUtil{
    /** Integral values greater than this might lose magnitude when cast to {@code float}. */
    int MAX_SAFE_INT=1<<24;
    /** Integral values less than this might lose magnitude when cast to {@code float}. */
    int MIN_SAFE_INT=-MAX_SAFE_INT;
    /** Integral values greater than this might lose magnitude when cast to {@code double}. */
    long MAX_SAFE_LONG=1L<<53;
    /** Integral values less than this might lose magnitude when cast to {@code double}. */
    long MIN_SAFE_LONG=-MAX_SAFE_LONG;
    int FLT_TRUE_BITS=0x3f800000;
    long DBL_TRUE_BITS=0x3ff0000000000000L;
    int FLT_NAN_BITS=0x7fc00000;
    long DBL_NAN_BITS=0x7ff8000000000000L;
    
    static int intCeiling(double val) {
        int v;
        if((v=(int)val)<val){
          ++v;
        }
        return v;
        
    }
    static int intCeiling(float val) {
        int v;
        if((v=(int)val)<val){
          ++v;
        }
        return v;
    }
    
    static int intFloor(double val) {
        int v;
        if((v=(int)val)>val){
            --v;
          }
        return v;
        
    }
    static int intFloor(float val) {
        int v;
        if((v=(int)val)>val){
            --v;
          }
        return v;
    }
    
    static int higherInt(double val) {
        int v;
        if((v=(int)val)<=val){
          ++v;
        }
        return v;
        
    }
    static int higherInt(float val) {
        int v;
        if((v=(int)val)<=val){
          ++v;
        }
        return v;
    }
    
    static int lowerInt(double val) {
      int v;
      if((v=(int)val)>=val){
         --v;
      }
      return v;
        
    }
    static int lowerInt(float val) {
        int v;
        if((v=(int)val)>=val){
           --v;
        }
        return v;  
    }
    
    
    
    
    
    static long longCeiling(double val) {
        long v;
        if((v=(long)val)<val){
          ++v;
        }
        return v;
        
    }
    static long longCeiling(float val) {
        long v;
        if((v=(long)val)<val){
          ++v;
        }
        return v;
    }
    
    static long longFloor(double val) {
        long v;
        if((v=(long)val)>val){
            --v;
          }
        return v;
        
    }
    static long longFloor(float val) {
        long v;
        if((v=(long)val)>val){
            --v;
          }
        return v;
    }
    
    static long higherLong(double val) {
        long v;
        if((v=(long)val)<=val){
          ++v;
        }
        return v;
        
    }
    static long higherLong(float val) {
        long v;
        if((v=(long)val)<=val){
          ++v;
        }
        return v;
    }
    
    static long lowerLong(double val) {
        long v;
      if((v=(long)val)>=val){
         --v;
      }
      return v;
        
    }
    static long lowerLong(float val) {
        long v;
        if((v=(long)val)>=val){
           --v;
        }
        return v;  
    }
    
    
    
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
    static boolean checkCastToDouble(long val){
        if(val<=MAX_SAFE_LONG){
            if(val>=MIN_SAFE_LONG){
                return true;
            }
            val=-val;
        }
        return Long.numberOfLeadingZeros(val)+Long.numberOfTrailingZeros(-val)>=11;
    }
    static boolean checkCastToFloat(int val){
        if(val<=MAX_SAFE_INT){
            if(val>=MIN_SAFE_INT){
                return true;
            }
            val=-val;
        }
        return Integer.numberOfLeadingZeros(val)+Integer.numberOfTrailingZeros(val)>=8;
    }
    static boolean checkCastToFloat(long val){
        if(val<=MAX_SAFE_INT){
            if(val>=MIN_SAFE_INT){
                return true;
            }
            val=-val;
        }
        return Long.numberOfLeadingZeros(val)+Long.numberOfTrailingZeros(val)>=40;
    }
    static boolean dblBitsEquals0(long dblBits){
        return dblBits==0;
    }
    static boolean dblBitsEqualsNaN(long dblBits){
        return dblBits==0x7ff8000000000000L;
    }
    static boolean doubleEquals(double val1,double val2){
        if(val2==val2){
            return Double.doubleToRawLongBits(val1)==Double.doubleToRawLongBits(val2);
        }
        return val1!=val1;
    }
    static boolean doubleEquals(double val1,float val2){
        if(val2==val2){
            return Double.doubleToRawLongBits(val1)==Double.doubleToRawLongBits(val2);
        }
        return val1!=val1;
    }
    @Deprecated
    static boolean doubleEquals(double val1,int val2){
        return val1==val2;
    }
    static boolean doubleEquals(double val1,long val2){
        return checkCastToDouble(val2)&&val1==val2;
    }
    @Deprecated
    static boolean doubleEquals(float val1,double val2){
        return doubleEquals(val2,val1);
    }
    static LongConsumer doubleToLongBitsConsumer(DoubleConsumer action){
        return dblBits->action.accept(Double.longBitsToDouble(dblBits));
    }
    static LongPredicate doubleToLongBitsPredicate(DoublePredicate predicate){
        return dblBits->predicate.test(Double.longBitsToDouble(dblBits));
    }
    static boolean equalsTrue(double val) {
    	return Double.doubleToRawLongBits(val)==DBL_TRUE_BITS;
    }
    static boolean equalsTrue(float val) {
    	return Float.floatToRawIntBits(val)==FLT_TRUE_BITS;
    }
    static FloatPredicate floatEquals(float val) {
    	if(val==val) {
    		return floatEqualsBits(Float.floatToRawIntBits(val));
    	}
    	return Float::isNaN;
    }
    static DoublePredicate doubleEquals(float val) {
    	if(val==val) {
    		return doubleEqualsBits(Double.doubleToRawLongBits(val));
    	}
    	return Double::isNaN;
    }
    static DoublePredicate doubleEquals(double val) {
    	if(val==val) {
    		return doubleEqualsBits(Double.doubleToRawLongBits(val));
    	}
    	return Double::isNaN;
    }
    static FloatPredicate floatEquals(boolean val) {
    	if(val) {
    		return TypeUtil::equalsTrue;
    	}else {
    		return TypeUtil::equalsFlt0;
    	}
    }
    static DoublePredicate doubleEquals(boolean val) {
    	if(val) {
    		return TypeUtil::equalsTrue;
    	}else {
    		return TypeUtil::equalsDbl0;
    	}
    }
    static FloatPredicate floatEquals(int val) {
    	if(val==0) {
    		return TypeUtil::equalsFlt0;
    	}
    	final int bits=Float.floatToRawIntBits(val);
    	return v->Float.floatToRawIntBits(v)==bits;
    }
    static FloatPredicate floatEquals(long val) {
    	if(val==0) {
    		return TypeUtil::equalsFlt0;
    	}
    	final int bits=Float.floatToRawIntBits(val);
    	return v->Float.floatToRawIntBits(v)==bits;
    }
    static FloatPredicate floatEqualsBits(int bits) {
    	return v->Float.floatToRawIntBits(v)==bits;
    }
    static DoublePredicate doubleEqualsBits(long bits) {
    	return v->Double.doubleToRawLongBits(v)==bits;
    }
    static DoublePredicate doubleEquals(int val) {
    	if(val==0) {
    		return TypeUtil::equalsDbl0;
    	}
    	final long bits=Double.doubleToRawLongBits(val);
    	return v->Double.doubleToRawLongBits(v)==bits;
    }
    static DoublePredicate doubleEquals(long val) {
    	if(val==0) {
    		return TypeUtil::equalsDbl0;
    	}
    	final long bits=Double.doubleToRawLongBits(val);
    	return v->Double.doubleToRawLongBits(v)==bits;
    }
    static boolean equalsDbl0(double val) {
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
        if(val2==val2){
            return Float.floatToRawIntBits(val1)==Float.floatToRawIntBits(val2);
        }
        return val1!=val1;
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
    static FloatPredicate intBitsToFloatPredicate(IntPredicate predicate){
        return fltVal->predicate.test(Float.floatToRawIntBits(fltVal));
    }
    static FloatConsumer intBitsToFloatConsumer(IntConsumer action){
        return fltVal->action.accept(Float.floatToRawIntBits(fltVal));
    }
    static DoubleConsumer longBitsToDoubleConsumer(LongConsumer action){
        return dblVal->action.accept(Double.doubleToRawLongBits(dblVal));
    }
    static DoublePredicate longBitsToDoublePredicate(LongPredicate predicate){
        return dblVal->predicate.test(Double.doubleToRawLongBits(dblVal));
    }
    static Predicate<Object> refEquals(Object val){
    	if(val!=null) {
    		return val::equals;
    	}
    	return Objects::isNull;
    }
    static Predicate<Object> refEquals(boolean val){
    	return v->v instanceof Boolean && (boolean)v==val;
    }
    static Predicate<Object> refEquals(Boolean val){
    	if(val!=null) {
    		return refEquals((boolean)val);
    	}
    	return Objects::isNull;
    }
    static Predicate<Object> refEquals(byte val){
    	return v->v instanceof Byte && (byte)v==val;
    }
    static Predicate<Object> refEquals(Byte val){
    	if(val!=null) {
    		return refEquals((byte)val);
    	}
    	return Objects::isNull;
    }
    static Predicate<Object> refEquals(char val){
    	return v->v instanceof Character && (char)v==val;
    }
    static Predicate<Object> refEquals(Character val){
    	if(val!=null) {
    		return refEquals((char)val);
    	}
    	return Objects::isNull;
    }
    static Predicate<Object> refEquals(short val){
    	return v->v instanceof Short && (short)v==val;
    }
    static Predicate<Object> refEquals(Short val){
    	if(val!=null) {
    		return refEquals((short)val);
    	}
    	return Objects::isNull;
    }
    static Predicate<Object> refEquals(int val){
    	return v->v instanceof Integer && (int)v==val;
    }
    static Predicate<Object> refEquals(Integer val){
    	if(val!=null) {
    		return refEquals((int)val);
    	}
    	return Objects::isNull;
    }
    static Predicate<Object> refEquals(long val){
    	return v->v instanceof Long && (long)v==val;
    }
    static Predicate<Object> refEquals(Long val){
    	if(val!=null) {
    		return refEquals((long)val);
    	}
    	return Objects::isNull;
    }
    static Predicate<Object> refEquals(float val){
    	if(val==val) {
    		final int bits=Float.floatToRawIntBits(val);
    		return v->v instanceof Float && Float.floatToRawIntBits((float)v)==bits;
    	}
    	return v->v instanceof Float && Float.isNaN((float)v);
    }
    static Predicate<Object> refEquals(Float val){
    	if(val!=null) {
    		return refEquals((float)val);
    	}
    	return Objects::isNull;
    }
    static Predicate<Object> refEquals(double val){
    	if(val==val) {
    		final long bits=Double.doubleToRawLongBits(val);
    		return v->v instanceof Double && Double.doubleToRawLongBits((double)v)==bits;
    	}
    	return v->v instanceof Double && Double.isNaN((double)v);
    }
    static Predicate<Object> refEquals(Double val){
    	if(val!=null) {
    		return refEquals((double)val);
    	}
    	return Objects::isNull;
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
