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
}
