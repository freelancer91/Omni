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
      arr[i]=supplier.getAsBoolean();
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
      ()->(byte)rand.nextLong()
    ,length);
  }
  public static byte[] getAscendingbyteArray(ByteSupplier supplier,int length)
  {
    byte[] arr=new byte[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=supplier.getAsByte();
    }
    Arrays.sort(arr,0,length);
    return arr;
  }
  public static char[] getAscendingcharArray(Random rand,int length)
  {
    return getAscendingcharArray(
      ()->(char)rand.nextLong()
    ,length);
  }
  public static char[] getAscendingcharArray(CharSupplier supplier,int length)
  {
    char[] arr=new char[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=supplier.getAsChar();
    }
    Arrays.sort(arr,0,length);
    return arr;
  }
  public static short[] getAscendingshortArray(Random rand,int length)
  {
    return getAscendingshortArray(
      ()->(short)rand.nextLong()
    ,length);
  }
  public static short[] getAscendingshortArray(ShortSupplier supplier,int length)
  {
    short[] arr=new short[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=supplier.getAsShort();
    }
    Arrays.sort(arr,0,length);
    return arr;
  }
  public static int[] getAscendingintArray(Random rand,int length)
  {
    return getAscendingintArray(
      ()->(int)rand.nextLong()
    ,length);
  }
  public static int[] getAscendingintArray(IntSupplier supplier,int length)
  {
    int[] arr=new int[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=supplier.getAsInt();
    }
    Arrays.sort(arr,0,length);
    return arr;
  }
  public static long[] getAscendinglongArray(Random rand,int length)
  {
    return getAscendinglongArray(
      ()->(long)rand.nextLong()
    ,length);
  }
  public static long[] getAscendinglongArray(LongSupplier supplier,int length)
  {
    long[] arr=new long[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=supplier.getAsLong();
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
      arr[i]=supplier.getAsFloat();
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
      arr[i]=supplier.getAsDouble();
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
      arr[i]=supplier.get();
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
      arr[i]=supplier.getAsBoolean();
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
      ()->(byte)rand.nextLong()
    ,length);
  }
  public static byte[] getDescendingbyteArray(ByteSupplier supplier,int length)
  {
    byte[] arr=new byte[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=supplier.getAsByte();
    }
    Arrays.sort(arr,0,length);
    OmniArray.OfByte.reverseRange(arr,0,length-1);
    return arr;
  }
  public static char[] getDescendingcharArray(Random rand,int length)
  {
    return getDescendingcharArray(
      ()->(char)rand.nextLong()
    ,length);
  }
  public static char[] getDescendingcharArray(CharSupplier supplier,int length)
  {
    char[] arr=new char[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=supplier.getAsChar();
    }
    Arrays.sort(arr,0,length);
    OmniArray.OfChar.reverseRange(arr,0,length-1);
    return arr;
  }
  public static short[] getDescendingshortArray(Random rand,int length)
  {
    return getDescendingshortArray(
      ()->(short)rand.nextLong()
    ,length);
  }
  public static short[] getDescendingshortArray(ShortSupplier supplier,int length)
  {
    short[] arr=new short[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=supplier.getAsShort();
    }
    Arrays.sort(arr,0,length);
    OmniArray.OfShort.reverseRange(arr,0,length-1);
    return arr;
  }
  public static int[] getDescendingintArray(Random rand,int length)
  {
    return getDescendingintArray(
      ()->(int)rand.nextLong()
    ,length);
  }
  public static int[] getDescendingintArray(IntSupplier supplier,int length)
  {
    int[] arr=new int[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=supplier.getAsInt();
    }
    Arrays.sort(arr,0,length);
    OmniArray.OfInt.reverseRange(arr,0,length-1);
    return arr;
  }
  public static long[] getDescendinglongArray(Random rand,int length)
  {
    return getDescendinglongArray(
      ()->(long)rand.nextLong()
    ,length);
  }
  public static long[] getDescendinglongArray(LongSupplier supplier,int length)
  {
    long[] arr=new long[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=supplier.getAsLong();
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
      arr[i]=supplier.getAsFloat();
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
      arr[i]=supplier.getAsDouble();
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
      arr[i]=supplier.get();
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
      arr[i]=supplier.getAsBoolean();
    }
    return arr;
  }
  public static byte[] getUnsortedbyteArray(Random rand,int length)
  {
    return getUnsortedbyteArray(
      ()->(byte)rand.nextLong()
    ,length);
  }
  public static byte[] getUnsortedbyteArray(ByteSupplier supplier,int length)
  {
    byte[] arr=new byte[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=supplier.getAsByte();
    }
    return arr;
  }
  public static char[] getUnsortedcharArray(Random rand,int length)
  {
    return getUnsortedcharArray(
      ()->(char)rand.nextLong()
    ,length);
  }
  public static char[] getUnsortedcharArray(CharSupplier supplier,int length)
  {
    char[] arr=new char[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=supplier.getAsChar();
    }
    return arr;
  }
  public static short[] getUnsortedshortArray(Random rand,int length)
  {
    return getUnsortedshortArray(
      ()->(short)rand.nextLong()
    ,length);
  }
  public static short[] getUnsortedshortArray(ShortSupplier supplier,int length)
  {
    short[] arr=new short[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=supplier.getAsShort();
    }
    return arr;
  }
  public static int[] getUnsortedintArray(Random rand,int length)
  {
    return getUnsortedintArray(
      ()->(int)rand.nextLong()
    ,length);
  }
  public static int[] getUnsortedintArray(IntSupplier supplier,int length)
  {
    int[] arr=new int[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=supplier.getAsInt();
    }
    return arr;
  }
  public static long[] getUnsortedlongArray(Random rand,int length)
  {
    return getUnsortedlongArray(
      ()->(long)rand.nextLong()
    ,length);
  }
  public static long[] getUnsortedlongArray(LongSupplier supplier,int length)
  {
    long[] arr=new long[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=supplier.getAsLong();
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
      arr[i]=supplier.getAsFloat();
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
      arr[i]=supplier.getAsDouble();
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
      arr[i]=supplier.get();
    }
    return arr;
  }
}
