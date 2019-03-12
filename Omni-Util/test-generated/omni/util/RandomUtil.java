package omni.util;
import java.util.Random;
public interface RandomUtil
{
  static int randomIntBetween(int minIncl,int maxIncl,Random rand)
  {
    return minIncl+rand.nextInt(maxIncl-minIncl+1);
  }
  public static boolean getRandomboolean(Random rand)
  {
    return rand.nextBoolean();
  }
  public static byte getRandombyte(Random rand)
  {
    return TypeConversionUtil.convertTobyte(rand.nextInt());
  }
  public static char getRandomchar(Random rand)
  {
    return TypeConversionUtil.convertTochar(rand.nextInt());
  }
  public static short getRandomshort(Random rand)
  {
    return TypeConversionUtil.convertToshort(rand.nextInt());
  }
  public static int getRandomint(Random rand)
  {
    return TypeConversionUtil.convertToint(rand.nextInt());
  }
  public static long getRandomlong(Random rand)
  {
    return (long)rand.nextLong();
  }
  public static float getRandomfloat(Random rand)
  {
    return rand.nextFloat();
  }
  public static double getRandomdouble(Random rand)
  {
    return rand.nextDouble();
  }
  //Ignore deprecation because we need would like to be able to use identity equality on the newly created boxed types
  @SuppressWarnings("deprecation")
  public static Boolean getRandomBoolean(Random rand)
  {
    return new Boolean(rand.nextBoolean());
  }
  public static Byte getRandomByte(Random rand)
  {
    return TypeConversionUtil.convertToByte(rand.nextInt());
  }
  public static Character getRandomCharacter(Random rand)
  {
    return TypeConversionUtil.convertToCharacter(rand.nextInt());
  }
  public static Short getRandomShort(Random rand)
  {
    return TypeConversionUtil.convertToShort(rand.nextInt());
  }
  public static Integer getRandomInteger(Random rand)
  {
    return TypeConversionUtil.convertToInteger(rand.nextInt());
  }
  //Ignore deprecation because we need would like to be able to use identity equality on the newly created boxed types
  @SuppressWarnings("deprecation")
  public static Long getRandomLong(Random rand)
  {
    return new Long(rand.nextLong());
  }
  //Ignore deprecation because we need would like to be able to use identity equality on the newly created boxed types
  @SuppressWarnings("deprecation")
  public static Float getRandomFloat(Random rand)
  {
    return new Float(rand.nextFloat());
  }
  //Ignore deprecation because we need would like to be able to use identity equality on the newly created boxed types
  @SuppressWarnings("deprecation")
  public static Double getRandomDouble(Random rand)
  {
    return new Double(rand.nextDouble());
  }
  public static String getRandomString(Random rand)
  {
    return TypeConversionUtil.convertToString(rand.nextInt());
  }
}
