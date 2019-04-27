package omni.util;
public final class TypeConversionUtil
{
  private TypeConversionUtil()
  {
  }
  public static boolean convertToboolean(int val)
  {
    return (val&0b1)!=0;
  }
  public static byte convertTobyte(int val)
  {
    return (byte)val;
  }
  public static char convertTochar(int val)
  {
    return (char)val;
  }
  public static short convertToshort(int val)
  {
    return (short)val;
  }
  public static int convertToint(int val)
  {
    return (int)val;
  }
  public static long convertTolong(int val)
  {
    return (long)val;
  }
  public static float convertTofloat(int val)
  {
    return (float)val;
  }
  public static double convertTodouble(int val)
  {
    return (double)val;
  }
  //Ignore deprecation because we need would like to be able to use identity equality on the newly created boxed types
  @SuppressWarnings("deprecation")
  public static Boolean convertToBoolean(int val)
  {
    return new Boolean((val&0b1)!=0);
  }
  //Ignore deprecation because we need would like to be able to use identity equality on the newly created boxed types
  @SuppressWarnings("deprecation")
  public static Byte convertToByte(int val)
  {
    return new Byte((byte)val);
  }
  //Ignore deprecation because we need would like to be able to use identity equality on the newly created boxed types
  @SuppressWarnings("deprecation")
  public static Character convertToCharacter(int val)
  {
    return new Character((char)val);
  }
  //Ignore deprecation because we need would like to be able to use identity equality on the newly created boxed types
  @SuppressWarnings("deprecation")
  public static Short convertToShort(int val)
  {
    return new Short((short)val);
  }
  //Ignore deprecation because we need would like to be able to use identity equality on the newly created boxed types
  @SuppressWarnings("deprecation")
  public static Integer convertToInteger(int val)
  {
    return new Integer(val);
  }
  //Ignore deprecation because we need would like to be able to use identity equality on the newly created boxed types
  @SuppressWarnings("deprecation")
  public static Long convertToLong(int val)
  {
    return new Long(val);
  }
  //Ignore deprecation because we need would like to be able to use identity equality on the newly created boxed types
  @SuppressWarnings("deprecation")
  public static Float convertToFloat(int val)
  {
    return new Float(val);
  }
  //Ignore deprecation because we need would like to be able to use identity equality on the newly created boxed types
  @SuppressWarnings("deprecation")
  public static Double convertToDouble(int val)
  {
    return new Double(val);
  }
  public static String convertToString(int val)
  {
    return Integer.toString(val);
  }
  //Ignore deprecation because we need would like to be able to use identity equality on the newly created boxed types
  @SuppressWarnings("deprecation")
  public static Integer convertToObject(int val)
  {
    return new Integer(val);
  }
  public static double convertTodoubleboolean(int val)
  {
    return TypeUtil.castToDouble(convertToboolean(val));
  }
  public static float convertTofloatboolean(int val)
  {
    return TypeUtil.castToFloat(convertToboolean(val));
  }
  public static long convertTolongboolean(int val)
  {
    return TypeUtil.castToLong(convertToboolean(val));
  }
  public static int convertTointboolean(int val)
  {
    return TypeUtil.castToByte(convertToboolean(val));
  }
  public static short convertToshortboolean(int val)
  {
    return TypeUtil.castToByte(convertToboolean(val));
  }
  public static char convertTocharboolean(int val)
  {
    return TypeUtil.castToChar(convertToboolean(val));
  }
  public static byte convertTobyteboolean(int val)
  {
    return TypeUtil.castToByte(convertToboolean(val));
  }
  public static short convertToshortbyte(int val){
    return (byte)val;
  }
  public static long convertTolongbyte(int val){
    return (byte)val;
  }
  public static float convertTofloatbyte(int val){
    return (byte)val;
  }
  public static double convertTodoublebyte(int val){
    return (byte)val;
  }
  public static int convertTointbyte(int val){
    return (byte)val;
  }
  public static long convertTolongchar(int val){
    return (char)val;
  }
  public static int convertTointchar(int val){
    return (char)val;
  }
  public static float convertTofloatchar(int val){
    return (char)val;
  }
  public static double convertTodoublechar(int val){
    return (char)val;
  }
  public static long convertTolongshort(int val){
    return (short)val;
  }
  public static int convertTointshort(int val){
    return (short)val;
  }
  public static float convertTofloatshort(int val){
    return (short)val;
  }
  public static double convertTodoubleshort(int val){
    return (short)val;
  }
}
