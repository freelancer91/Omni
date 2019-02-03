package omni.util;
import java.util.Random;
import java.util.function.Supplier;
import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.DoubleSupplier;
import omni.function.ByteSupplier;
import omni.function.CharSupplier;
import omni.function.ShortSupplier;
import omni.function.FloatSupplier;
import java.util.Arrays;
public interface JunitUtil
{
  public static boolean[] getAscendingbooleanArray(Random rand,int length)
  {
    return getAscendingbooleanArray(
      rand::nextBoolean
    ,length);
  }
  public static boolean[] getAscendingbooleanArray(BooleanSupplier supplier,int length)
  {
    boolean[] arr=new boolean[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(boolean)supplier.getAsBoolean();
    }
    Boolean[] boxedCopy=new Boolean[length];
    ArrCopy.uncheckedCopy(arr,0,boxedCopy,0,length);
    Arrays.sort(boxedCopy,0,length);
    ArrCopy.uncheckedCopy(boxedCopy,0,arr,0,length);
    return arr;
  }
  public static byte[] getAscendingbyteArray(Random rand,int length)
  {
    return getAscendingbyteArray(
      ()->(byte)
      rand.nextLong()
    ,length);
  }
  public static byte[] getAscendingbyteArray(ByteSupplier supplier,int length)
  {
    byte[] arr=new byte[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(byte)supplier.getAsByte();
    }
    Arrays.sort(arr,0,length);
    return arr;
  }
  public static char[] getAscendingcharArray(Random rand,int length)
  {
    return getAscendingcharArray(
      ()->(char)
      rand.nextLong()
    ,length);
  }
  public static char[] getAscendingcharArray(CharSupplier supplier,int length)
  {
    char[] arr=new char[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(char)supplier.getAsChar();
    }
    Arrays.sort(arr,0,length);
    return arr;
  }
  public static short[] getAscendingshortArray(Random rand,int length)
  {
    return getAscendingshortArray(
      ()->(short)
      rand.nextLong()
    ,length);
  }
  public static short[] getAscendingshortArray(ShortSupplier supplier,int length)
  {
    short[] arr=new short[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(short)supplier.getAsShort();
    }
    Arrays.sort(arr,0,length);
    return arr;
  }
  public static int[] getAscendingintArray(Random rand,int length)
  {
    return getAscendingintArray(
      ()->(int)
      rand.nextLong()
    ,length);
  }
  public static int[] getAscendingintArray(IntSupplier supplier,int length)
  {
    int[] arr=new int[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(int)supplier.getAsInt();
    }
    Arrays.sort(arr,0,length);
    return arr;
  }
  public static long[] getAscendinglongArray(Random rand,int length)
  {
    return getAscendinglongArray(
      ()->(long)
      rand.nextLong()
    ,length);
  }
  public static long[] getAscendinglongArray(LongSupplier supplier,int length)
  {
    long[] arr=new long[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(long)supplier.getAsLong();
    }
    Arrays.sort(arr,0,length);
    return arr;
  }
  public static float[] getAscendingfloatArray(Random rand,int length)
  {
    return getAscendingfloatArray(
      rand::nextFloat
    ,length);
  }
  public static float[] getAscendingfloatArray(FloatSupplier supplier,int length)
  {
    float[] arr=new float[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(float)supplier.getAsFloat();
    }
    Arrays.sort(arr,0,length);
    return arr;
  }
  public static double[] getAscendingdoubleArray(Random rand,int length)
  {
    return getAscendingdoubleArray(
      rand::nextDouble
    ,length);
  }
  public static double[] getAscendingdoubleArray(DoubleSupplier supplier,int length)
  {
    double[] arr=new double[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(double)supplier.getAsDouble();
    }
    Arrays.sort(arr,0,length);
    return arr;
  }
  public static String[] getAscendingStringArray(Random rand,int length)
  {
    return getAscendingStringArray(
      ()->Long.toString(rand.nextLong())
    ,length);
  }
  public static String[] getAscendingStringArray(Supplier<? extends String> supplier,int length)
  {
    String[] arr=new String[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(String)supplier.get();
    }
    Arrays.sort(arr,0,length);
    return arr;
  }
  public static boolean[] getDescendingbooleanArray(Random rand,int length)
  {
    return getDescendingbooleanArray(
      rand::nextBoolean
    ,length);
  }
  public static boolean[] getDescendingbooleanArray(BooleanSupplier supplier,int length)
  {
    boolean[] arr=new boolean[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(boolean)supplier.getAsBoolean();
    }
    Boolean[] boxedCopy=new Boolean[length];
    ArrCopy.uncheckedCopy(arr,0,boxedCopy,0,length);
    Arrays.sort(boxedCopy,0,length);
    ArrCopy.uncheckedCopy(boxedCopy,0,arr,0,length);
    OmniArray.OfBoolean.reverseRange(arr,0,length-1);
    return arr;
  }
  public static byte[] getDescendingbyteArray(Random rand,int length)
  {
    return getDescendingbyteArray(
      ()->(byte)
      rand.nextLong()
    ,length);
  }
  public static byte[] getDescendingbyteArray(ByteSupplier supplier,int length)
  {
    byte[] arr=new byte[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(byte)supplier.getAsByte();
    }
    Arrays.sort(arr,0,length);
    OmniArray.OfByte.reverseRange(arr,0,length-1);
    return arr;
  }
  public static char[] getDescendingcharArray(Random rand,int length)
  {
    return getDescendingcharArray(
      ()->(char)
      rand.nextLong()
    ,length);
  }
  public static char[] getDescendingcharArray(CharSupplier supplier,int length)
  {
    char[] arr=new char[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(char)supplier.getAsChar();
    }
    Arrays.sort(arr,0,length);
    OmniArray.OfChar.reverseRange(arr,0,length-1);
    return arr;
  }
  public static short[] getDescendingshortArray(Random rand,int length)
  {
    return getDescendingshortArray(
      ()->(short)
      rand.nextLong()
    ,length);
  }
  public static short[] getDescendingshortArray(ShortSupplier supplier,int length)
  {
    short[] arr=new short[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(short)supplier.getAsShort();
    }
    Arrays.sort(arr,0,length);
    OmniArray.OfShort.reverseRange(arr,0,length-1);
    return arr;
  }
  public static int[] getDescendingintArray(Random rand,int length)
  {
    return getDescendingintArray(
      ()->(int)
      rand.nextLong()
    ,length);
  }
  public static int[] getDescendingintArray(IntSupplier supplier,int length)
  {
    int[] arr=new int[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(int)supplier.getAsInt();
    }
    Arrays.sort(arr,0,length);
    OmniArray.OfInt.reverseRange(arr,0,length-1);
    return arr;
  }
  public static long[] getDescendinglongArray(Random rand,int length)
  {
    return getDescendinglongArray(
      ()->(long)
      rand.nextLong()
    ,length);
  }
  public static long[] getDescendinglongArray(LongSupplier supplier,int length)
  {
    long[] arr=new long[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(long)supplier.getAsLong();
    }
    Arrays.sort(arr,0,length);
    OmniArray.OfLong.reverseRange(arr,0,length-1);
    return arr;
  }
  public static float[] getDescendingfloatArray(Random rand,int length)
  {
    return getDescendingfloatArray(
      rand::nextFloat
    ,length);
  }
  public static float[] getDescendingfloatArray(FloatSupplier supplier,int length)
  {
    float[] arr=new float[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(float)supplier.getAsFloat();
    }
    Arrays.sort(arr,0,length);
    OmniArray.OfFloat.reverseRange(arr,0,length-1);
    return arr;
  }
  public static double[] getDescendingdoubleArray(Random rand,int length)
  {
    return getDescendingdoubleArray(
      rand::nextDouble
    ,length);
  }
  public static double[] getDescendingdoubleArray(DoubleSupplier supplier,int length)
  {
    double[] arr=new double[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(double)supplier.getAsDouble();
    }
    Arrays.sort(arr,0,length);
    OmniArray.OfDouble.reverseRange(arr,0,length-1);
    return arr;
  }
  public static String[] getDescendingStringArray(Random rand,int length)
  {
    return getDescendingStringArray(
      ()->Long.toString(rand.nextLong())
    ,length);
  }
  public static String[] getDescendingStringArray(Supplier<? extends String> supplier,int length)
  {
    String[] arr=new String[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(String)supplier.get();
    }
    Arrays.sort(arr,0,length);
    OmniArray.OfRef.reverseRange(arr,0,length-1);
    return arr;
  }
  public static boolean[] getUnsortedbooleanArray(Random rand,int length)
  {
    return getUnsortedbooleanArray(
      rand::nextBoolean
    ,length);
  }
  public static boolean[] getUnsortedbooleanArray(BooleanSupplier supplier,int length)
  {
    boolean[] arr=new boolean[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(boolean)supplier.getAsBoolean();
    }
    return arr;
  }
  public static byte[] getUnsortedbyteArray(Random rand,int length)
  {
    return getUnsortedbyteArray(
      ()->(byte)
      rand.nextLong()
    ,length);
  }
  public static byte[] getUnsortedbyteArray(ByteSupplier supplier,int length)
  {
    byte[] arr=new byte[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(byte)supplier.getAsByte();
    }
    return arr;
  }
  public static char[] getUnsortedcharArray(Random rand,int length)
  {
    return getUnsortedcharArray(
      ()->(char)
      rand.nextLong()
    ,length);
  }
  public static char[] getUnsortedcharArray(CharSupplier supplier,int length)
  {
    char[] arr=new char[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(char)supplier.getAsChar();
    }
    return arr;
  }
  public static short[] getUnsortedshortArray(Random rand,int length)
  {
    return getUnsortedshortArray(
      ()->(short)
      rand.nextLong()
    ,length);
  }
  public static short[] getUnsortedshortArray(ShortSupplier supplier,int length)
  {
    short[] arr=new short[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(short)supplier.getAsShort();
    }
    return arr;
  }
  public static int[] getUnsortedintArray(Random rand,int length)
  {
    return getUnsortedintArray(
      ()->(int)
      rand.nextLong()
    ,length);
  }
  public static int[] getUnsortedintArray(IntSupplier supplier,int length)
  {
    int[] arr=new int[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(int)supplier.getAsInt();
    }
    return arr;
  }
  public static long[] getUnsortedlongArray(Random rand,int length)
  {
    return getUnsortedlongArray(
      ()->(long)
      rand.nextLong()
    ,length);
  }
  public static long[] getUnsortedlongArray(LongSupplier supplier,int length)
  {
    long[] arr=new long[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(long)supplier.getAsLong();
    }
    return arr;
  }
  public static float[] getUnsortedfloatArray(Random rand,int length)
  {
    return getUnsortedfloatArray(
      rand::nextFloat
    ,length);
  }
  public static float[] getUnsortedfloatArray(FloatSupplier supplier,int length)
  {
    float[] arr=new float[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(float)supplier.getAsFloat();
    }
    return arr;
  }
  public static double[] getUnsorteddoubleArray(Random rand,int length)
  {
    return getUnsorteddoubleArray(
      rand::nextDouble
    ,length);
  }
  public static double[] getUnsorteddoubleArray(DoubleSupplier supplier,int length)
  {
    double[] arr=new double[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(double)supplier.getAsDouble();
    }
    return arr;
  }
  public static String[] getUnsortedStringArray(Random rand,int length)
  {
    return getUnsortedStringArray(
      ()->Long.toString(rand.nextLong())
    ,length);
  }
  public static String[] getUnsortedStringArray(Supplier<? extends String> supplier,int length)
  {
    String[] arr=new String[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(String)supplier.get();
    }
    return arr;
  }
  public static Boolean[] getAscendingBooleanArray(Random rand,int length)
  {
    return getAscendingBooleanArray(
      rand::nextBoolean
    ,length);
  }
  public static Boolean[] getAscendingBooleanArray(BooleanSupplier supplier,int length)
  {
    Boolean[] arr=new Boolean[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Boolean)supplier.getAsBoolean();
    }
    Arrays.sort(arr,0,length);
    return arr;
  }
  public static Byte[] getAscendingByteArray(Random rand,int length)
  {
    return getAscendingByteArray(
      ()->(Byte)
      (byte)
      rand.nextLong()
    ,length);
  }
  public static Byte[] getAscendingByteArray(ByteSupplier supplier,int length)
  {
    Byte[] arr=new Byte[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Byte)supplier.getAsByte();
    }
    Arrays.sort(arr,0,length);
    return arr;
  }
  public static Character[] getAscendingCharacterArray(Random rand,int length)
  {
    return getAscendingCharacterArray(
      ()->(Character)
      (char)
      rand.nextLong()
    ,length);
  }
  public static Character[] getAscendingCharacterArray(CharSupplier supplier,int length)
  {
    Character[] arr=new Character[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Character)supplier.getAsChar();
    }
    Arrays.sort(arr,0,length);
    return arr;
  }
  public static Short[] getAscendingShortArray(Random rand,int length)
  {
    return getAscendingShortArray(
      ()->(Short)
      (short)
      rand.nextLong()
    ,length);
  }
  public static Short[] getAscendingShortArray(ShortSupplier supplier,int length)
  {
    Short[] arr=new Short[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Short)supplier.getAsShort();
    }
    Arrays.sort(arr,0,length);
    return arr;
  }
  public static Integer[] getAscendingIntegerArray(Random rand,int length)
  {
    return getAscendingIntegerArray(
      ()->(Integer)
      (int)
      rand.nextLong()
    ,length);
  }
  public static Integer[] getAscendingIntegerArray(IntSupplier supplier,int length)
  {
    Integer[] arr=new Integer[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Integer)supplier.getAsInt();
    }
    Arrays.sort(arr,0,length);
    return arr;
  }
  public static Long[] getAscendingLongArray(Random rand,int length)
  {
    return getAscendingLongArray(
      ()->(Long)
      rand.nextLong()
    ,length);
  }
  public static Long[] getAscendingLongArray(LongSupplier supplier,int length)
  {
    Long[] arr=new Long[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Long)supplier.getAsLong();
    }
    Arrays.sort(arr,0,length);
    return arr;
  }
  public static Float[] getAscendingFloatArray(Random rand,int length)
  {
    return getAscendingFloatArray(
      rand::nextFloat
    ,length);
  }
  public static Float[] getAscendingFloatArray(FloatSupplier supplier,int length)
  {
    Float[] arr=new Float[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Float)supplier.getAsFloat();
    }
    Arrays.sort(arr,0,length);
    return arr;
  }
  public static Double[] getAscendingDoubleArray(Random rand,int length)
  {
    return getAscendingDoubleArray(
      rand::nextDouble
    ,length);
  }
  public static Double[] getAscendingDoubleArray(DoubleSupplier supplier,int length)
  {
    Double[] arr=new Double[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Double)supplier.getAsDouble();
    }
    Arrays.sort(arr,0,length);
    return arr;
  }
  public static Boolean[] getDescendingBooleanArray(Random rand,int length)
  {
    return getDescendingBooleanArray(
      rand::nextBoolean
    ,length);
  }
  public static Boolean[] getDescendingBooleanArray(BooleanSupplier supplier,int length)
  {
    Boolean[] arr=new Boolean[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Boolean)supplier.getAsBoolean();
    }
    Arrays.sort(arr,0,length);
    OmniArray.OfRef.reverseRange(arr,0,length-1);
    return arr;
  }
  public static Byte[] getDescendingByteArray(Random rand,int length)
  {
    return getDescendingByteArray(
      ()->(Byte)
      (byte)
      rand.nextLong()
    ,length);
  }
  public static Byte[] getDescendingByteArray(ByteSupplier supplier,int length)
  {
    Byte[] arr=new Byte[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Byte)supplier.getAsByte();
    }
    Arrays.sort(arr,0,length);
    OmniArray.OfRef.reverseRange(arr,0,length-1);
    return arr;
  }
  public static Character[] getDescendingCharacterArray(Random rand,int length)
  {
    return getDescendingCharacterArray(
      ()->(Character)
      (char)
      rand.nextLong()
    ,length);
  }
  public static Character[] getDescendingCharacterArray(CharSupplier supplier,int length)
  {
    Character[] arr=new Character[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Character)supplier.getAsChar();
    }
    Arrays.sort(arr,0,length);
    OmniArray.OfRef.reverseRange(arr,0,length-1);
    return arr;
  }
  public static Short[] getDescendingShortArray(Random rand,int length)
  {
    return getDescendingShortArray(
      ()->(Short)
      (short)
      rand.nextLong()
    ,length);
  }
  public static Short[] getDescendingShortArray(ShortSupplier supplier,int length)
  {
    Short[] arr=new Short[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Short)supplier.getAsShort();
    }
    Arrays.sort(arr,0,length);
    OmniArray.OfRef.reverseRange(arr,0,length-1);
    return arr;
  }
  public static Integer[] getDescendingIntegerArray(Random rand,int length)
  {
    return getDescendingIntegerArray(
      ()->(Integer)
      (int)
      rand.nextLong()
    ,length);
  }
  public static Integer[] getDescendingIntegerArray(IntSupplier supplier,int length)
  {
    Integer[] arr=new Integer[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Integer)supplier.getAsInt();
    }
    Arrays.sort(arr,0,length);
    OmniArray.OfRef.reverseRange(arr,0,length-1);
    return arr;
  }
  public static Long[] getDescendingLongArray(Random rand,int length)
  {
    return getDescendingLongArray(
      ()->(Long)
      rand.nextLong()
    ,length);
  }
  public static Long[] getDescendingLongArray(LongSupplier supplier,int length)
  {
    Long[] arr=new Long[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Long)supplier.getAsLong();
    }
    Arrays.sort(arr,0,length);
    OmniArray.OfRef.reverseRange(arr,0,length-1);
    return arr;
  }
  public static Float[] getDescendingFloatArray(Random rand,int length)
  {
    return getDescendingFloatArray(
      rand::nextFloat
    ,length);
  }
  public static Float[] getDescendingFloatArray(FloatSupplier supplier,int length)
  {
    Float[] arr=new Float[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Float)supplier.getAsFloat();
    }
    Arrays.sort(arr,0,length);
    OmniArray.OfRef.reverseRange(arr,0,length-1);
    return arr;
  }
  public static Double[] getDescendingDoubleArray(Random rand,int length)
  {
    return getDescendingDoubleArray(
      rand::nextDouble
    ,length);
  }
  public static Double[] getDescendingDoubleArray(DoubleSupplier supplier,int length)
  {
    Double[] arr=new Double[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Double)supplier.getAsDouble();
    }
    Arrays.sort(arr,0,length);
    OmniArray.OfRef.reverseRange(arr,0,length-1);
    return arr;
  }
  public static Boolean[] getUnsortedBooleanArray(Random rand,int length)
  {
    return getUnsortedBooleanArray(
      rand::nextBoolean
    ,length);
  }
  public static Boolean[] getUnsortedBooleanArray(BooleanSupplier supplier,int length)
  {
    Boolean[] arr=new Boolean[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Boolean)supplier.getAsBoolean();
    }
    return arr;
  }
  public static Byte[] getUnsortedByteArray(Random rand,int length)
  {
    return getUnsortedByteArray(
      ()->(Byte)
      (byte)
      rand.nextLong()
    ,length);
  }
  public static Byte[] getUnsortedByteArray(ByteSupplier supplier,int length)
  {
    Byte[] arr=new Byte[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Byte)supplier.getAsByte();
    }
    return arr;
  }
  public static Character[] getUnsortedCharacterArray(Random rand,int length)
  {
    return getUnsortedCharacterArray(
      ()->(Character)
      (char)
      rand.nextLong()
    ,length);
  }
  public static Character[] getUnsortedCharacterArray(CharSupplier supplier,int length)
  {
    Character[] arr=new Character[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Character)supplier.getAsChar();
    }
    return arr;
  }
  public static Short[] getUnsortedShortArray(Random rand,int length)
  {
    return getUnsortedShortArray(
      ()->(Short)
      (short)
      rand.nextLong()
    ,length);
  }
  public static Short[] getUnsortedShortArray(ShortSupplier supplier,int length)
  {
    Short[] arr=new Short[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Short)supplier.getAsShort();
    }
    return arr;
  }
  public static Integer[] getUnsortedIntegerArray(Random rand,int length)
  {
    return getUnsortedIntegerArray(
      ()->(Integer)
      (int)
      rand.nextLong()
    ,length);
  }
  public static Integer[] getUnsortedIntegerArray(IntSupplier supplier,int length)
  {
    Integer[] arr=new Integer[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Integer)supplier.getAsInt();
    }
    return arr;
  }
  public static Long[] getUnsortedLongArray(Random rand,int length)
  {
    return getUnsortedLongArray(
      ()->(Long)
      rand.nextLong()
    ,length);
  }
  public static Long[] getUnsortedLongArray(LongSupplier supplier,int length)
  {
    Long[] arr=new Long[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Long)supplier.getAsLong();
    }
    return arr;
  }
  public static Float[] getUnsortedFloatArray(Random rand,int length)
  {
    return getUnsortedFloatArray(
      rand::nextFloat
    ,length);
  }
  public static Float[] getUnsortedFloatArray(FloatSupplier supplier,int length)
  {
    Float[] arr=new Float[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Float)supplier.getAsFloat();
    }
    return arr;
  }
  public static Double[] getUnsortedDoubleArray(Random rand,int length)
  {
    return getUnsortedDoubleArray(
      rand::nextDouble
    ,length);
  }
  public static Double[] getUnsortedDoubleArray(DoubleSupplier supplier,int length)
  {
    Double[] arr=new Double[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Double)supplier.getAsDouble();
    }
    return arr;
  }
}
