package omni.util;
public interface HashUtils{
  static int DBL_NAN_HASH=(int)(TypeUtil.DBL_NAN_BITS^TypeUtil.DBL_NAN_BITS>>>32);
  static int hashDouble(double val){
    if(val==val){ return Long.hashCode(Double.doubleToRawLongBits(val)); }
    return DBL_NAN_HASH;
  }
  static int hashFloat(float val){
    if(val==val){ return Float.floatToRawIntBits(val); }
    return TypeUtil.FLT_NAN_BITS;
  }
}
