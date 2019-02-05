package omni.util;
import org.junit.Assert;
import org.junit.Test;
import org.junit.BeforeClass;
import java.util.Random;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;
//TODO uncomment when the module bug is fixed
//import java.util.Comparator;
import java.util.function.IntBinaryOperator;
import omni.function.BooleanComparator;
import omni.function.ByteComparator;
import omni.function.CharComparator;
import omni.function.ShortComparator;
import omni.function.LongComparator;
import omni.function.FloatComparator;
import omni.function.DoubleComparator;
import java.util.function.Supplier;
import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.DoubleSupplier;
import omni.function.ByteSupplier;
import omni.function.CharSupplier;
import omni.function.ShortSupplier;
import omni.function.FloatSupplier;
public class SortUtilTest
{
  private static final int[] lengths=new int[]{2, 3, 5, 8, 13, 21, 34, 55, 100, 1000, 10000, 100000, 1000000};
  private static final long[] randSeeds=new long[]{666L,0xC0FFEEL,999L};
  /*//TODO uncomment when they fix the module bug in eclipse
  private static Comparator<String> getAscendingStringComparator()
  {
    return (val1,val2)->
    {
      return
        (
      val1.compareTo(val2)
        );
    };
  }
  private static BooleanComparator getAscendingbooleanComparator()
  {
    return (val1,val2)->
    {
      return
        (
      Boolean.compare(val1,val2)
        );
    };
  }
  private static ByteComparator getAscendingbyteComparator()
  {
    return (val1,val2)->
    {
      return
        (
      Byte.compare(val1,val2)
        );
    };
  }
  private static CharComparator getAscendingcharComparator()
  {
    return (val1,val2)->
    {
      return
        (
      Character.compare(val1,val2)
        );
    };
  }
  private static ShortComparator getAscendingshortComparator()
  {
    return (val1,val2)->
    {
      return
        (
      Short.compare(val1,val2)
        );
    };
  }
  private static IntBinaryOperator getAscendingintComparator()
  {
    return (val1,val2)->
    {
      return
        (
      Integer.compare(val1,val2)
        );
    };
  }
  private static LongComparator getAscendinglongComparator()
  {
    return (val1,val2)->
    {
      return
        (
      Long.compare(val1,val2)
        );
    };
  }
  private static FloatComparator getAscendingfloatComparator()
  {
    return (val1,val2)->
    {
      return
        (
      Float.compare(val1,val2)
        );
    };
  }
  private static DoubleComparator getAscendingdoubleComparator()
  {
    return (val1,val2)->
    {
      return
        (
      Double.compare(val1,val2)
        );
    };
  }
  private static Comparator<String> getUnsortedStringComparator()
  {
    return (val1,val2)->
    {
      return
        (
        0
        );
    };
  }
  private static BooleanComparator getUnsortedbooleanComparator()
  {
    return (val1,val2)->
    {
      return
        (
        0
        );
    };
  }
  private static ByteComparator getUnsortedbyteComparator()
  {
    return (val1,val2)->
    {
      return
        (
        0
        );
    };
  }
  private static CharComparator getUnsortedcharComparator()
  {
    return (val1,val2)->
    {
      return
        (
        0
        );
    };
  }
  private static ShortComparator getUnsortedshortComparator()
  {
    return (val1,val2)->
    {
      return
        (
        0
        );
    };
  }
  private static IntBinaryOperator getUnsortedintComparator()
  {
    return (val1,val2)->
    {
      return
        (
        0
        );
    };
  }
  private static LongComparator getUnsortedlongComparator()
  {
    return (val1,val2)->
    {
      return
        (
        0
        );
    };
  }
  private static FloatComparator getUnsortedfloatComparator()
  {
    return (val1,val2)->
    {
      return
        (
        0
        );
    };
  }
  private static DoubleComparator getUnsorteddoubleComparator()
  {
    return (val1,val2)->
    {
      return
        (
        0
        );
    };
  }
  private static Comparator<String> getDescendingStringComparator()
  {
    return (val1,val2)->
    {
      return
        -
        (
      val1.compareTo(val2)
        );
    };
  }
  private static BooleanComparator getDescendingbooleanComparator()
  {
    return (val1,val2)->
    {
      return
        -
        (
      Boolean.compare(val1,val2)
        );
    };
  }
  private static ByteComparator getDescendingbyteComparator()
  {
    return (val1,val2)->
    {
      return
        -
        (
      Byte.compare(val1,val2)
        );
    };
  }
  private static CharComparator getDescendingcharComparator()
  {
    return (val1,val2)->
    {
      return
        -
        (
      Character.compare(val1,val2)
        );
    };
  }
  private static ShortComparator getDescendingshortComparator()
  {
    return (val1,val2)->
    {
      return
        -
        (
      Short.compare(val1,val2)
        );
    };
  }
  private static IntBinaryOperator getDescendingintComparator()
  {
    return (val1,val2)->
    {
      return
        -
        (
      Integer.compare(val1,val2)
        );
    };
  }
  private static LongComparator getDescendinglongComparator()
  {
    return (val1,val2)->
    {
      return
        -
        (
      Long.compare(val1,val2)
        );
    };
  }
  private static FloatComparator getDescendingfloatComparator()
  {
    return (val1,val2)->
    {
      return
        -
        (
      Float.compare(val1,val2)
        );
    };
  }
  private static DoubleComparator getDescendingdoubleComparator()
  {
    return (val1,val2)->
    {
      return
        -
        (
      Double.compare(val1,val2)
        );
    };
  }
  */
    /*//TODO uncomment when they fix the module bug in eclipse
    private static void isSortedsort(String[] arr,String[] copy) throws InterruptedException
    {
      Thread stockSorterThread=new Thread(()->
      {
      Arrays.parallelSort(copy,0,copy.length);
      });
      stockSorterThread.start();
      SortUtil.uncheckedsort(arr,0,arr.length-1);
      stockSorterThread.join();
      IntStream.range(0,copy.length)
      .parallel()
      .forEach(index->
      {
        Assert.assertTrue(Objects.equals(arr[index],copy[index]));
      });
    }
    */
    private static void isSortedsort(boolean[] arr,boolean[] copy) throws InterruptedException
    {
      Thread stockSorterThread=new Thread(()->
      {
      Boolean[] boxedCopy=new Boolean[copy.length];
      ArrCopy.uncheckedCopy(copy,0,boxedCopy,0,copy.length);
      Arrays.parallelSort(boxedCopy,0,copy.length);
      ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
      });
      stockSorterThread.start();
      SortUtil.uncheckedsort(arr,0,arr.length-1);
      stockSorterThread.join();
      IntStream.range(0,copy.length)
      .parallel()
      .forEach(index->
      {
        Assert.assertTrue(arr[index]==copy[index]);
      });
    }
    private static void isSortedsort(byte[] arr,byte[] copy) throws InterruptedException
    {
      Thread stockSorterThread=new Thread(()->
      {
      Arrays.parallelSort(copy,0,copy.length);
      });
      stockSorterThread.start();
      SortUtil.uncheckedsort(arr,0,arr.length-1);
      stockSorterThread.join();
      IntStream.range(0,copy.length)
      .parallel()
      .forEach(index->
      {
        Assert.assertTrue(arr[index]==copy[index]);
      });
    }
    private static void isSortedsort(char[] arr,char[] copy) throws InterruptedException
    {
      Thread stockSorterThread=new Thread(()->
      {
      Arrays.parallelSort(copy,0,copy.length);
      });
      stockSorterThread.start();
      SortUtil.uncheckedsort(arr,0,arr.length-1);
      stockSorterThread.join();
      IntStream.range(0,copy.length)
      .parallel()
      .forEach(index->
      {
        Assert.assertTrue(arr[index]==copy[index]);
      });
    }
    private static void isSortedsort(short[] arr,short[] copy) throws InterruptedException
    {
      Thread stockSorterThread=new Thread(()->
      {
      Arrays.parallelSort(copy,0,copy.length);
      });
      stockSorterThread.start();
      SortUtil.uncheckedsort(arr,0,arr.length-1);
      stockSorterThread.join();
      IntStream.range(0,copy.length)
      .parallel()
      .forEach(index->
      {
        Assert.assertTrue(arr[index]==copy[index]);
      });
    }
    private static void isSortedsort(int[] arr,int[] copy) throws InterruptedException
    {
      Thread stockSorterThread=new Thread(()->
      {
      Arrays.parallelSort(copy,0,copy.length);
      });
      stockSorterThread.start();
      SortUtil.uncheckedsort(arr,0,arr.length-1);
      stockSorterThread.join();
      IntStream.range(0,copy.length)
      .parallel()
      .forEach(index->
      {
        Assert.assertTrue(Objects.equals(arr[index],copy[index]));
      });
    }
    private static void isSortedsort(long[] arr,long[] copy) throws InterruptedException
    {
      Thread stockSorterThread=new Thread(()->
      {
      Arrays.parallelSort(copy,0,copy.length);
      });
      stockSorterThread.start();
      SortUtil.uncheckedsort(arr,0,arr.length-1);
      stockSorterThread.join();
      IntStream.range(0,copy.length)
      .parallel()
      .forEach(index->
      {
        Assert.assertTrue(Objects.equals(arr[index],copy[index]));
      });
    }
    private static void isSortedsort(float[] arr,float[] copy) throws InterruptedException
    {
      Thread stockSorterThread=new Thread(()->
      {
      Arrays.parallelSort(copy,0,copy.length);
      });
      stockSorterThread.start();
      SortUtil.uncheckedsort(arr,0,arr.length-1);
      stockSorterThread.join();
      IntStream.range(0,copy.length)
      .parallel()
      .forEach(index->
      {
        Assert.assertTrue(TypeUtil.floatEquals(arr[index],copy[index]));
      });
    }
    private static void isSortedsort(double[] arr,double[] copy) throws InterruptedException
    {
      Thread stockSorterThread=new Thread(()->
      {
      Arrays.parallelSort(copy,0,copy.length);
      });
      stockSorterThread.start();
      SortUtil.uncheckedsort(arr,0,arr.length-1);
      stockSorterThread.join();
      IntStream.range(0,copy.length)
      .parallel()
      .forEach(index->
      {
        Assert.assertTrue(TypeUtil.doubleEquals(arr[index],copy[index]));
      });
    }
    /*//TODO uncomment when they fix the module bug in eclipse
    private static void isSortedreverseSort(String[] arr,String[] copy) throws InterruptedException
    {
      Thread stockSorterThread=new Thread(()->
      {
      Arrays.parallelSort(copy,0,copy.length);
      OmniArray.OfRef.reverseRange(copy,0,copy.length-1);
      });
      stockSorterThread.start();
      SortUtil.uncheckedreverseSort(arr,0,arr.length-1);
      stockSorterThread.join();
      IntStream.range(0,copy.length)
      .parallel()
      .forEach(index->
      {
        Assert.assertTrue(Objects.equals(arr[index],copy[index]));
      });
    }
    */
    private static void isSortedreverseSort(boolean[] arr,boolean[] copy) throws InterruptedException
    {
      Thread stockSorterThread=new Thread(()->
      {
      Boolean[] boxedCopy=new Boolean[copy.length];
      ArrCopy.uncheckedCopy(copy,0,boxedCopy,0,copy.length);
      Arrays.parallelSort(boxedCopy,0,copy.length);
      ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
      OmniArray.OfBoolean.reverseRange(copy,0,copy.length-1);
      });
      stockSorterThread.start();
      SortUtil.uncheckedreverseSort(arr,0,arr.length-1);
      stockSorterThread.join();
      IntStream.range(0,copy.length)
      .parallel()
      .forEach(index->
      {
        Assert.assertTrue(arr[index]==copy[index]);
      });
    }
    private static void isSortedreverseSort(byte[] arr,byte[] copy) throws InterruptedException
    {
      Thread stockSorterThread=new Thread(()->
      {
      Arrays.parallelSort(copy,0,copy.length);
      OmniArray.OfByte.reverseRange(copy,0,copy.length-1);
      });
      stockSorterThread.start();
      SortUtil.uncheckedreverseSort(arr,0,arr.length-1);
      stockSorterThread.join();
      IntStream.range(0,copy.length)
      .parallel()
      .forEach(index->
      {
        Assert.assertTrue(arr[index]==copy[index]);
      });
    }
    private static void isSortedreverseSort(char[] arr,char[] copy) throws InterruptedException
    {
      Thread stockSorterThread=new Thread(()->
      {
      Arrays.parallelSort(copy,0,copy.length);
      OmniArray.OfChar.reverseRange(copy,0,copy.length-1);
      });
      stockSorterThread.start();
      SortUtil.uncheckedreverseSort(arr,0,arr.length-1);
      stockSorterThread.join();
      IntStream.range(0,copy.length)
      .parallel()
      .forEach(index->
      {
        Assert.assertTrue(arr[index]==copy[index]);
      });
    }
    private static void isSortedreverseSort(short[] arr,short[] copy) throws InterruptedException
    {
      Thread stockSorterThread=new Thread(()->
      {
      Arrays.parallelSort(copy,0,copy.length);
      OmniArray.OfShort.reverseRange(copy,0,copy.length-1);
      });
      stockSorterThread.start();
      SortUtil.uncheckedreverseSort(arr,0,arr.length-1);
      stockSorterThread.join();
      IntStream.range(0,copy.length)
      .parallel()
      .forEach(index->
      {
        Assert.assertTrue(arr[index]==copy[index]);
      });
    }
    private static void isSortedreverseSort(int[] arr,int[] copy) throws InterruptedException
    {
      Thread stockSorterThread=new Thread(()->
      {
      Arrays.parallelSort(copy,0,copy.length);
      OmniArray.OfInt.reverseRange(copy,0,copy.length-1);
      });
      stockSorterThread.start();
      SortUtil.uncheckedreverseSort(arr,0,arr.length-1);
      stockSorterThread.join();
      IntStream.range(0,copy.length)
      .parallel()
      .forEach(index->
      {
        Assert.assertTrue(Objects.equals(arr[index],copy[index]));
      });
    }
    private static void isSortedreverseSort(long[] arr,long[] copy) throws InterruptedException
    {
      Thread stockSorterThread=new Thread(()->
      {
      Arrays.parallelSort(copy,0,copy.length);
      OmniArray.OfLong.reverseRange(copy,0,copy.length-1);
      });
      stockSorterThread.start();
      SortUtil.uncheckedreverseSort(arr,0,arr.length-1);
      stockSorterThread.join();
      IntStream.range(0,copy.length)
      .parallel()
      .forEach(index->
      {
        Assert.assertTrue(Objects.equals(arr[index],copy[index]));
      });
    }
    private static void isSortedreverseSort(float[] arr,float[] copy) throws InterruptedException
    {
      Thread stockSorterThread=new Thread(()->
      {
      Arrays.parallelSort(copy,0,copy.length);
      OmniArray.OfFloat.reverseRange(copy,0,copy.length-1);
      });
      stockSorterThread.start();
      SortUtil.uncheckedreverseSort(arr,0,arr.length-1);
      stockSorterThread.join();
      IntStream.range(0,copy.length)
      .parallel()
      .forEach(index->
      {
        Assert.assertTrue(TypeUtil.floatEquals(arr[index],copy[index]));
      });
    }
    private static void isSortedreverseSort(double[] arr,double[] copy) throws InterruptedException
    {
      Thread stockSorterThread=new Thread(()->
      {
      Arrays.parallelSort(copy,0,copy.length);
      OmniArray.OfDouble.reverseRange(copy,0,copy.length-1);
      });
      stockSorterThread.start();
      SortUtil.uncheckedreverseSort(arr,0,arr.length-1);
      stockSorterThread.join();
      IntStream.range(0,copy.length)
      .parallel()
      .forEach(index->
      {
        Assert.assertTrue(TypeUtil.doubleEquals(arr[index],copy[index]));
      });
    }
  /*//TODO uncomment when they fix the module bug in eclipse
  private static void isSorted(String[] arr,String[] copy,Comparator<? super String> sorter) throws InterruptedException
  {
    Thread stockSorterThread=new Thread(()->
    {
    Arrays.parallelSort(copy,0,copy.length,sorter);
    });
    stockSorterThread.start();
    SortUtil.uncheckedcomparatorSort(arr,0,arr.length-1,sorter);
    stockSorterThread.join();
    IntStream.range(0,copy.length).parallel().forEach(index->
    {
      Assert.assertTrue(Objects.equals(arr[index],copy[index]));
    });
  }
  private static void isSorted(boolean[] arr,boolean[] copy,BooleanComparator sorter) throws InterruptedException
  {
    Thread stockSorterThread=new Thread(()->
    {
    Boolean[] boxedCopy=new Boolean[copy.length];
    ArrCopy.uncheckedCopy(copy,0,boxedCopy,0,copy.length);
    Arrays.parallelSort(boxedCopy,0,copy.length,sorter::compare);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    });
    stockSorterThread.start();
    SortUtil.uncheckedcomparatorSort(arr,0,arr.length-1,sorter);
    stockSorterThread.join();
    IntStream.range(0,copy.length).parallel().forEach(index->
    {
      Assert.assertTrue(arr[index]==copy[index]);
    });
  }
  private static void isSorted(byte[] arr,byte[] copy,ByteComparator sorter) throws InterruptedException
  {
    Thread stockSorterThread=new Thread(()->
    {
    Byte[] boxedCopy=new Byte[copy.length];
    ArrCopy.uncheckedCopy(copy,0,boxedCopy,0,copy.length);
    Arrays.parallelSort(boxedCopy,0,copy.length,sorter::compare);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    });
    stockSorterThread.start();
    SortUtil.uncheckedcomparatorSort(arr,0,arr.length-1,sorter);
    stockSorterThread.join();
    IntStream.range(0,copy.length).parallel().forEach(index->
    {
      Assert.assertTrue(arr[index]==copy[index]);
    });
  }
  private static void isSorted(char[] arr,char[] copy,CharComparator sorter) throws InterruptedException
  {
    Thread stockSorterThread=new Thread(()->
    {
    Character[] boxedCopy=new Character[copy.length];
    ArrCopy.uncheckedCopy(copy,0,boxedCopy,0,copy.length);
    Arrays.parallelSort(boxedCopy,0,copy.length,sorter::compare);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    });
    stockSorterThread.start();
    SortUtil.uncheckedcomparatorSort(arr,0,arr.length-1,sorter);
    stockSorterThread.join();
    IntStream.range(0,copy.length).parallel().forEach(index->
    {
      Assert.assertTrue(arr[index]==copy[index]);
    });
  }
  private static void isSorted(short[] arr,short[] copy,ShortComparator sorter) throws InterruptedException
  {
    Thread stockSorterThread=new Thread(()->
    {
    Short[] boxedCopy=new Short[copy.length];
    ArrCopy.uncheckedCopy(copy,0,boxedCopy,0,copy.length);
    Arrays.parallelSort(boxedCopy,0,copy.length,sorter::compare);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    });
    stockSorterThread.start();
    SortUtil.uncheckedcomparatorSort(arr,0,arr.length-1,sorter);
    stockSorterThread.join();
    IntStream.range(0,copy.length).parallel().forEach(index->
    {
      Assert.assertTrue(arr[index]==copy[index]);
    });
  }
  private static void isSorted(int[] arr,int[] copy,IntBinaryOperator sorter) throws InterruptedException
  {
    Thread stockSorterThread=new Thread(()->
    {
    Integer[] boxedCopy=new Integer[copy.length];
    ArrCopy.uncheckedCopy(copy,0,boxedCopy,0,copy.length);
    Arrays.parallelSort(boxedCopy,0,copy.length,sorter::applyAsInt);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    });
    stockSorterThread.start();
    SortUtil.uncheckedcomparatorSort(arr,0,arr.length-1,sorter);
    stockSorterThread.join();
    IntStream.range(0,copy.length).parallel().forEach(index->
    {
      Assert.assertTrue(Objects.equals(arr[index],copy[index]));
    });
  }
  private static void isSorted(long[] arr,long[] copy,LongComparator sorter) throws InterruptedException
  {
    Thread stockSorterThread=new Thread(()->
    {
    Long[] boxedCopy=new Long[copy.length];
    ArrCopy.uncheckedCopy(copy,0,boxedCopy,0,copy.length);
    Arrays.parallelSort(boxedCopy,0,copy.length,sorter::compare);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    });
    stockSorterThread.start();
    SortUtil.uncheckedcomparatorSort(arr,0,arr.length-1,sorter);
    stockSorterThread.join();
    IntStream.range(0,copy.length).parallel().forEach(index->
    {
      Assert.assertTrue(Objects.equals(arr[index],copy[index]));
    });
  }
  private static void isSorted(float[] arr,float[] copy,FloatComparator sorter) throws InterruptedException
  {
    Thread stockSorterThread=new Thread(()->
    {
    Float[] boxedCopy=new Float[copy.length];
    ArrCopy.uncheckedCopy(copy,0,boxedCopy,0,copy.length);
    Arrays.parallelSort(boxedCopy,0,copy.length,sorter::compare);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    });
    stockSorterThread.start();
    SortUtil.uncheckedcomparatorSort(arr,0,arr.length-1,sorter);
    stockSorterThread.join();
    IntStream.range(0,copy.length).parallel().forEach(index->
    {
      Assert.assertTrue(TypeUtil.floatEquals(arr[index],copy[index]));
    });
  }
  private static void isSorted(double[] arr,double[] copy,DoubleComparator sorter) throws InterruptedException
  {
    Thread stockSorterThread=new Thread(()->
    {
    Double[] boxedCopy=new Double[copy.length];
    ArrCopy.uncheckedCopy(copy,0,boxedCopy,0,copy.length);
    Arrays.parallelSort(boxedCopy,0,copy.length,sorter::compare);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    });
    stockSorterThread.start();
    SortUtil.uncheckedcomparatorSort(arr,0,arr.length-1,sorter);
    stockSorterThread.join();
    IntStream.range(0,copy.length).parallel().forEach(index->
    {
      Assert.assertTrue(TypeUtil.doubleEquals(arr[index],copy[index]));
    });
  }
  */
  private static boolean getRandboolean(Random rand)
  {
    return rand.nextBoolean();
  }
  private static byte getRandbyte(Random rand)
  {
    return (byte)rand.nextLong();
  }
  private static char getRandchar(Random rand)
  {
    return (char)rand.nextLong();
  }
  private static short getRandshort(Random rand)
  {
    return (short)rand.nextLong();
  }
  private static int getRandint(Random rand)
  {
    return (int)rand.nextLong();
  }
  private static long getRandlong(Random rand)
  {
    return (long)rand.nextLong();
  }
  private static float getRandfloat(Random rand)
  {
    return rand.nextFloat();
  }
  private static double getRanddouble(Random rand)
  {
    return rand.nextDouble();
  }
  /*//TODO uncomment when the eclipse module bug is fixed
  private static String getRandString(Random rand)
  {
    return Long.toString(rand.nextLong());
  }
  */
  private static boolean convertToboolean(int val)
  {
    return val!=0;
  }
  private static byte convertTobyte(int val)
  {
    return (byte)val;
  }
  private static char convertTochar(int val)
  {
    return (char)val;
  }
  private static short convertToshort(int val)
  {
    return (short)val;
  }
  private static int convertToint(int val)
  {
    return (int)val;
  }
  private static long convertTolong(int val)
  {
    return (long)val;
  }
  private static float convertTofloat(int val)
  {
    return (float)val;
  }
  private static double convertTodouble(int val)
  {
    return (double)val;
  }
  /*//TODO uncomment when the eclipse module bug is fixed
  private static String convertToString(int val)
  {
    return Integer.toString(val);
  }
  */
  private static enum ArrayBuilderboolean
  {
    RANDOM
    {
      void build(boolean[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=getRandboolean(rand);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return getMaxRepsboolean(arrLength);
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    },
    ASCENDING
    {
      void build(boolean[] arr,int m,Random rand)
      {
        int pivotPoint=(arr.length/4)+rand.nextInt(arr.length/2);
        for(int i=0;i<pivotPoint;++i)
        {
          arr[i]=false;
        }
        for(int i=pivotPoint;i<arr.length;++i)
        {
          arr[i]=true;
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return getMaxRepsboolean(arrLength);
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    },
    DESCENDING
    {
      void build(boolean[] arr,int m,Random rand)
      {
        int pivotPoint=(arr.length/4)+rand.nextInt(arr.length/2);
        for(int i=0;i<pivotPoint;++i)
        {
          arr[i]=true;
        }
        for(int i=pivotPoint;i<arr.length;++i)
        {
          arr[i]=false;
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return getMaxRepsboolean(arrLength);
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    },
    ALL_EQUAL
    {
      void build(boolean[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertToboolean(m);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 0;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ;
    abstract void build(boolean[] arr,int m,Random rand); 
    abstract int getNumRepetitions(int arrLength);
    abstract int getMLo();
    abstract int getMHi(int arrLength);
    abstract int incrementM(int m);
  }
  private static enum ArrayBuilderbyte
  {
    RANDOM
    {
      void build(byte[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=getRandbyte(rand);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return getMaxRepsbyte(arrLength);
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    },
    ASCENDING
    {
      void build(byte[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTobyte(m+i);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    },
    DESCENDING
    {
      void build(byte[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTobyte(arr.length-m-i);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    },
    ALL_EQUAL
    {
      void build(byte[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTobyte(m);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 0;
      }
      int getMHi(int arrLength)
      {
        return 0;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,MERGE_ASCENDING
    {
      void build(byte[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int v=1;
        int i=0;
        for(int k=0;k<m;++k)
        {
          v=1;
          for(int p=0;p<period;++p)
          {
            arr[i++]=convertTobyte(v++);
          }
        }
        for(int j=1;j<arr.length-1;j++)
        {
          arr[j]=convertTobyte(v++);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 65;
      }
      int getMHi(int arrLength)
      {
        return 69;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,MERGE_DESCENDING
    {
      void build(byte[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int v=-1;
        int i=0;
        for(int k=0;k<m;++k)
        {
          v=-1;
          for(int p=0;p<period;++p)
          {
            arr[i++]=convertTobyte(v--);
          }
        }
        for(int j=1;j<arr.length-1;j++)
        {
          arr[j]=convertTobyte(v--);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 65;
      }
      int getMHi(int arrLength)
      {
        return 69;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,SAW
    {
      void build(byte[] arr,int m,Random rand)
      {
        int incCount=1;
        int decCount=arr.length;
        int i=0;
        int period=m--;
        for(;;)
        {
          for(int k=1;k<=period;k++)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTobyte(incCount++);
          }
          period+=m;
          for(int k=1;k<=period;k++)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTobyte(decCount--);
          }
          period+=m;
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SORTED_REPEATED
    {
      void build(byte[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int i=0;
        int k=0;
        for(;;)
        {
          for(int t=1;t<=period;++t)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTobyte(k);
          }
          if(i>=arr.length)
          {
            return;
          }
          ++k;
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return Math.min(arrLength,7);
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,REPEATED
    {
      void build(byte[] arr, int m, Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTobyte(i%m);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,DUPLICATED
    {
      void build(byte[] arr, int m, Random rand)
      {
        for (int i = 0; i < arr.length; i++)
        {
          arr[i] = convertTobyte(rand.nextInt(m));
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return getMaxRepsbyte(arrLength);
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SORTED_ORGAN_PIPES
    {
      void build(byte[] arr,int m,Random rand)
      {
        int i=0;
        int k=m;
        for(;;)
        {
          for(int t=1;t<=m;++t)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTobyte(k);
          }
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return Math.min(arrLength,7);
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,ORGAN_PIPES
    {
      void build(byte[] arr, int m, Random rand)
      {
        int middle = arr.length / (m + 1);
        for (int i = 0; i < middle; i++) {
            arr[i] = convertTobyte(i);
        }
        for (int i = middle; i < arr.length; i++) {
            arr[i] = convertTobyte(arr.length - i - 1);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,STAGGER
    {
      void build(byte[] arr, int m, Random rand) {
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertTobyte((i * m + i) % arr.length);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,PLATEAU
    {
      void build(byte[] arr, int m, Random rand) {
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertTobyte(Math.min(i, m));
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SHUFFLE
    {
      void build(byte[] arr, int m, Random rand) {
        int x = 0, y = 0;
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertTobyte(rand.nextBoolean() ? (x += 2) : (y += 2));
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return getMaxRepsbyte(arrLength);
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ;
    abstract void build(byte[] arr,int m,Random rand); 
    abstract int getNumRepetitions(int arrLength);
    abstract int getMLo();
    abstract int getMHi(int arrLength);
    abstract int incrementM(int m);
  }
  private static enum ArrayBuilderchar
  {
    RANDOM
    {
      void build(char[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=getRandchar(rand);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return getMaxRepschar(arrLength);
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    },
    ASCENDING
    {
      void build(char[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTochar(m+i);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    },
    DESCENDING
    {
      void build(char[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTochar(arr.length-m-i);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    },
    ALL_EQUAL
    {
      void build(char[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTochar(m);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 0;
      }
      int getMHi(int arrLength)
      {
        return 0;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,MERGE_ASCENDING
    {
      void build(char[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int v=1;
        int i=0;
        for(int k=0;k<m;++k)
        {
          v=1;
          for(int p=0;p<period;++p)
          {
            arr[i++]=convertTochar(v++);
          }
        }
        for(int j=1;j<arr.length-1;j++)
        {
          arr[j]=convertTochar(v++);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 65;
      }
      int getMHi(int arrLength)
      {
        return 69;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,MERGE_DESCENDING
    {
      void build(char[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int v=-1;
        int i=0;
        for(int k=0;k<m;++k)
        {
          v=-1;
          for(int p=0;p<period;++p)
          {
            arr[i++]=convertTochar(v--);
          }
        }
        for(int j=1;j<arr.length-1;j++)
        {
          arr[j]=convertTochar(v--);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 65;
      }
      int getMHi(int arrLength)
      {
        return 69;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,SAW
    {
      void build(char[] arr,int m,Random rand)
      {
        int incCount=1;
        int decCount=arr.length;
        int i=0;
        int period=m--;
        for(;;)
        {
          for(int k=1;k<=period;k++)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTochar(incCount++);
          }
          period+=m;
          for(int k=1;k<=period;k++)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTochar(decCount--);
          }
          period+=m;
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SORTED_REPEATED
    {
      void build(char[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int i=0;
        int k=0;
        for(;;)
        {
          for(int t=1;t<=period;++t)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTochar(k);
          }
          if(i>=arr.length)
          {
            return;
          }
          ++k;
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return Math.min(arrLength,7);
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,REPEATED
    {
      void build(char[] arr, int m, Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTochar(i%m);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,DUPLICATED
    {
      void build(char[] arr, int m, Random rand)
      {
        for (int i = 0; i < arr.length; i++)
        {
          arr[i] = convertTochar(rand.nextInt(m));
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return getMaxRepschar(arrLength);
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SORTED_ORGAN_PIPES
    {
      void build(char[] arr,int m,Random rand)
      {
        int i=0;
        int k=m;
        for(;;)
        {
          for(int t=1;t<=m;++t)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTochar(k);
          }
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return Math.min(arrLength,7);
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,ORGAN_PIPES
    {
      void build(char[] arr, int m, Random rand)
      {
        int middle = arr.length / (m + 1);
        for (int i = 0; i < middle; i++) {
            arr[i] = convertTochar(i);
        }
        for (int i = middle; i < arr.length; i++) {
            arr[i] = convertTochar(arr.length - i - 1);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,STAGGER
    {
      void build(char[] arr, int m, Random rand) {
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertTochar((i * m + i) % arr.length);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,PLATEAU
    {
      void build(char[] arr, int m, Random rand) {
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertTochar(Math.min(i, m));
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SHUFFLE
    {
      void build(char[] arr, int m, Random rand) {
        int x = 0, y = 0;
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertTochar(rand.nextBoolean() ? (x += 2) : (y += 2));
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return getMaxRepschar(arrLength);
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ;
    abstract void build(char[] arr,int m,Random rand); 
    abstract int getNumRepetitions(int arrLength);
    abstract int getMLo();
    abstract int getMHi(int arrLength);
    abstract int incrementM(int m);
  }
  private static enum ArrayBuildershort
  {
    RANDOM
    {
      void build(short[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=getRandshort(rand);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return getMaxRepsshort(arrLength);
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    },
    ASCENDING
    {
      void build(short[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertToshort(m+i);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    },
    DESCENDING
    {
      void build(short[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertToshort(arr.length-m-i);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    },
    ALL_EQUAL
    {
      void build(short[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertToshort(m);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 0;
      }
      int getMHi(int arrLength)
      {
        return 0;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,MERGE_ASCENDING
    {
      void build(short[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int v=1;
        int i=0;
        for(int k=0;k<m;++k)
        {
          v=1;
          for(int p=0;p<period;++p)
          {
            arr[i++]=convertToshort(v++);
          }
        }
        for(int j=1;j<arr.length-1;j++)
        {
          arr[j]=convertToshort(v++);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 65;
      }
      int getMHi(int arrLength)
      {
        return 69;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,MERGE_DESCENDING
    {
      void build(short[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int v=-1;
        int i=0;
        for(int k=0;k<m;++k)
        {
          v=-1;
          for(int p=0;p<period;++p)
          {
            arr[i++]=convertToshort(v--);
          }
        }
        for(int j=1;j<arr.length-1;j++)
        {
          arr[j]=convertToshort(v--);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 65;
      }
      int getMHi(int arrLength)
      {
        return 69;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,SAW
    {
      void build(short[] arr,int m,Random rand)
      {
        int incCount=1;
        int decCount=arr.length;
        int i=0;
        int period=m--;
        for(;;)
        {
          for(int k=1;k<=period;k++)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertToshort(incCount++);
          }
          period+=m;
          for(int k=1;k<=period;k++)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertToshort(decCount--);
          }
          period+=m;
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SORTED_REPEATED
    {
      void build(short[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int i=0;
        int k=0;
        for(;;)
        {
          for(int t=1;t<=period;++t)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertToshort(k);
          }
          if(i>=arr.length)
          {
            return;
          }
          ++k;
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return Math.min(arrLength,7);
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,REPEATED
    {
      void build(short[] arr, int m, Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertToshort(i%m);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,DUPLICATED
    {
      void build(short[] arr, int m, Random rand)
      {
        for (int i = 0; i < arr.length; i++)
        {
          arr[i] = convertToshort(rand.nextInt(m));
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return getMaxRepsshort(arrLength);
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SORTED_ORGAN_PIPES
    {
      void build(short[] arr,int m,Random rand)
      {
        int i=0;
        int k=m;
        for(;;)
        {
          for(int t=1;t<=m;++t)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertToshort(k);
          }
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return Math.min(arrLength,7);
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,ORGAN_PIPES
    {
      void build(short[] arr, int m, Random rand)
      {
        int middle = arr.length / (m + 1);
        for (int i = 0; i < middle; i++) {
            arr[i] = convertToshort(i);
        }
        for (int i = middle; i < arr.length; i++) {
            arr[i] = convertToshort(arr.length - i - 1);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,STAGGER
    {
      void build(short[] arr, int m, Random rand) {
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertToshort((i * m + i) % arr.length);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,PLATEAU
    {
      void build(short[] arr, int m, Random rand) {
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertToshort(Math.min(i, m));
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SHUFFLE
    {
      void build(short[] arr, int m, Random rand) {
        int x = 0, y = 0;
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertToshort(rand.nextBoolean() ? (x += 2) : (y += 2));
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return getMaxRepsshort(arrLength);
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ;
    abstract void build(short[] arr,int m,Random rand); 
    abstract int getNumRepetitions(int arrLength);
    abstract int getMLo();
    abstract int getMHi(int arrLength);
    abstract int incrementM(int m);
  }
  private static enum ArrayBuilderint
  {
    RANDOM
    {
      void build(int[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=getRandint(rand);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return getMaxRepsint(arrLength);
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    },
    ASCENDING
    {
      void build(int[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertToint(m+i);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    },
    DESCENDING
    {
      void build(int[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertToint(arr.length-m-i);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    },
    ALL_EQUAL
    {
      void build(int[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertToint(m);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 0;
      }
      int getMHi(int arrLength)
      {
        return 0;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,MERGE_ASCENDING
    {
      void build(int[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int v=1;
        int i=0;
        for(int k=0;k<m;++k)
        {
          v=1;
          for(int p=0;p<period;++p)
          {
            arr[i++]=convertToint(v++);
          }
        }
        for(int j=1;j<arr.length-1;j++)
        {
          arr[j]=convertToint(v++);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 65;
      }
      int getMHi(int arrLength)
      {
        return 69;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,MERGE_DESCENDING
    {
      void build(int[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int v=-1;
        int i=0;
        for(int k=0;k<m;++k)
        {
          v=-1;
          for(int p=0;p<period;++p)
          {
            arr[i++]=convertToint(v--);
          }
        }
        for(int j=1;j<arr.length-1;j++)
        {
          arr[j]=convertToint(v--);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 65;
      }
      int getMHi(int arrLength)
      {
        return 69;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,SAW
    {
      void build(int[] arr,int m,Random rand)
      {
        int incCount=1;
        int decCount=arr.length;
        int i=0;
        int period=m--;
        for(;;)
        {
          for(int k=1;k<=period;k++)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertToint(incCount++);
          }
          period+=m;
          for(int k=1;k<=period;k++)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertToint(decCount--);
          }
          period+=m;
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SORTED_REPEATED
    {
      void build(int[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int i=0;
        int k=0;
        for(;;)
        {
          for(int t=1;t<=period;++t)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertToint(k);
          }
          if(i>=arr.length)
          {
            return;
          }
          ++k;
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return Math.min(arrLength,7);
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,REPEATED
    {
      void build(int[] arr, int m, Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertToint(i%m);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,DUPLICATED
    {
      void build(int[] arr, int m, Random rand)
      {
        for (int i = 0; i < arr.length; i++)
        {
          arr[i] = convertToint(rand.nextInt(m));
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return getMaxRepsint(arrLength);
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SORTED_ORGAN_PIPES
    {
      void build(int[] arr,int m,Random rand)
      {
        int i=0;
        int k=m;
        for(;;)
        {
          for(int t=1;t<=m;++t)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertToint(k);
          }
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return Math.min(arrLength,7);
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,ORGAN_PIPES
    {
      void build(int[] arr, int m, Random rand)
      {
        int middle = arr.length / (m + 1);
        for (int i = 0; i < middle; i++) {
            arr[i] = convertToint(i);
        }
        for (int i = middle; i < arr.length; i++) {
            arr[i] = convertToint(arr.length - i - 1);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,STAGGER
    {
      void build(int[] arr, int m, Random rand) {
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertToint((i * m + i) % arr.length);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,PLATEAU
    {
      void build(int[] arr, int m, Random rand) {
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertToint(Math.min(i, m));
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SHUFFLE
    {
      void build(int[] arr, int m, Random rand) {
        int x = 0, y = 0;
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertToint(rand.nextBoolean() ? (x += 2) : (y += 2));
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return getMaxRepsint(arrLength);
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ;
    abstract void build(int[] arr,int m,Random rand); 
    abstract int getNumRepetitions(int arrLength);
    abstract int getMLo();
    abstract int getMHi(int arrLength);
    abstract int incrementM(int m);
  }
  private static enum ArrayBuilderlong
  {
    RANDOM
    {
      void build(long[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=getRandlong(rand);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return getMaxRepslong(arrLength);
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    },
    ASCENDING
    {
      void build(long[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTolong(m+i);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    },
    DESCENDING
    {
      void build(long[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTolong(arr.length-m-i);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    },
    ALL_EQUAL
    {
      void build(long[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTolong(m);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 0;
      }
      int getMHi(int arrLength)
      {
        return 0;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,MERGE_ASCENDING
    {
      void build(long[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int v=1;
        int i=0;
        for(int k=0;k<m;++k)
        {
          v=1;
          for(int p=0;p<period;++p)
          {
            arr[i++]=convertTolong(v++);
          }
        }
        for(int j=1;j<arr.length-1;j++)
        {
          arr[j]=convertTolong(v++);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 65;
      }
      int getMHi(int arrLength)
      {
        return 69;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,MERGE_DESCENDING
    {
      void build(long[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int v=-1;
        int i=0;
        for(int k=0;k<m;++k)
        {
          v=-1;
          for(int p=0;p<period;++p)
          {
            arr[i++]=convertTolong(v--);
          }
        }
        for(int j=1;j<arr.length-1;j++)
        {
          arr[j]=convertTolong(v--);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 65;
      }
      int getMHi(int arrLength)
      {
        return 69;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,SAW
    {
      void build(long[] arr,int m,Random rand)
      {
        int incCount=1;
        int decCount=arr.length;
        int i=0;
        int period=m--;
        for(;;)
        {
          for(int k=1;k<=period;k++)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTolong(incCount++);
          }
          period+=m;
          for(int k=1;k<=period;k++)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTolong(decCount--);
          }
          period+=m;
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SORTED_REPEATED
    {
      void build(long[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int i=0;
        int k=0;
        for(;;)
        {
          for(int t=1;t<=period;++t)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTolong(k);
          }
          if(i>=arr.length)
          {
            return;
          }
          ++k;
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return Math.min(arrLength,7);
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,REPEATED
    {
      void build(long[] arr, int m, Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTolong(i%m);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,DUPLICATED
    {
      void build(long[] arr, int m, Random rand)
      {
        for (int i = 0; i < arr.length; i++)
        {
          arr[i] = convertTolong(rand.nextInt(m));
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return getMaxRepslong(arrLength);
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SORTED_ORGAN_PIPES
    {
      void build(long[] arr,int m,Random rand)
      {
        int i=0;
        int k=m;
        for(;;)
        {
          for(int t=1;t<=m;++t)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTolong(k);
          }
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return Math.min(arrLength,7);
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,ORGAN_PIPES
    {
      void build(long[] arr, int m, Random rand)
      {
        int middle = arr.length / (m + 1);
        for (int i = 0; i < middle; i++) {
            arr[i] = convertTolong(i);
        }
        for (int i = middle; i < arr.length; i++) {
            arr[i] = convertTolong(arr.length - i - 1);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,STAGGER
    {
      void build(long[] arr, int m, Random rand) {
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertTolong((i * m + i) % arr.length);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,PLATEAU
    {
      void build(long[] arr, int m, Random rand) {
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertTolong(Math.min(i, m));
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SHUFFLE
    {
      void build(long[] arr, int m, Random rand) {
        int x = 0, y = 0;
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertTolong(rand.nextBoolean() ? (x += 2) : (y += 2));
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return getMaxRepslong(arrLength);
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ;
    abstract void build(long[] arr,int m,Random rand); 
    abstract int getNumRepetitions(int arrLength);
    abstract int getMLo();
    abstract int getMHi(int arrLength);
    abstract int incrementM(int m);
  }
  private static enum ArrayBuilderfloat
  {
    RANDOM
    {
      void build(float[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=getRandfloat(rand);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return getMaxRepsfloat(arrLength);
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    },
    ASCENDING
    {
      void build(float[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTofloat(m+i);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    },
    DESCENDING
    {
      void build(float[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTofloat(arr.length-m-i);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    },
    ALL_EQUAL
    {
      void build(float[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTofloat(m);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 0;
      }
      int getMHi(int arrLength)
      {
        return 0;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,MERGE_ASCENDING
    {
      void build(float[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int v=1;
        int i=0;
        for(int k=0;k<m;++k)
        {
          v=1;
          for(int p=0;p<period;++p)
          {
            arr[i++]=convertTofloat(v++);
          }
        }
        for(int j=1;j<arr.length-1;j++)
        {
          arr[j]=convertTofloat(v++);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 65;
      }
      int getMHi(int arrLength)
      {
        return 69;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,MERGE_DESCENDING
    {
      void build(float[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int v=-1;
        int i=0;
        for(int k=0;k<m;++k)
        {
          v=-1;
          for(int p=0;p<period;++p)
          {
            arr[i++]=convertTofloat(v--);
          }
        }
        for(int j=1;j<arr.length-1;j++)
        {
          arr[j]=convertTofloat(v--);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 65;
      }
      int getMHi(int arrLength)
      {
        return 69;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,SAW
    {
      void build(float[] arr,int m,Random rand)
      {
        int incCount=1;
        int decCount=arr.length;
        int i=0;
        int period=m--;
        for(;;)
        {
          for(int k=1;k<=period;k++)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTofloat(incCount++);
          }
          period+=m;
          for(int k=1;k<=period;k++)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTofloat(decCount--);
          }
          period+=m;
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SORTED_REPEATED
    {
      void build(float[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int i=0;
        int k=0;
        for(;;)
        {
          for(int t=1;t<=period;++t)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTofloat(k);
          }
          if(i>=arr.length)
          {
            return;
          }
          ++k;
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return Math.min(arrLength,7);
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,REPEATED
    {
      void build(float[] arr, int m, Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTofloat(i%m);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,DUPLICATED
    {
      void build(float[] arr, int m, Random rand)
      {
        for (int i = 0; i < arr.length; i++)
        {
          arr[i] = convertTofloat(rand.nextInt(m));
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return getMaxRepsfloat(arrLength);
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SORTED_ORGAN_PIPES
    {
      void build(float[] arr,int m,Random rand)
      {
        int i=0;
        int k=m;
        for(;;)
        {
          for(int t=1;t<=m;++t)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTofloat(k);
          }
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return Math.min(arrLength,7);
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,ORGAN_PIPES
    {
      void build(float[] arr, int m, Random rand)
      {
        int middle = arr.length / (m + 1);
        for (int i = 0; i < middle; i++) {
            arr[i] = convertTofloat(i);
        }
        for (int i = middle; i < arr.length; i++) {
            arr[i] = convertTofloat(arr.length - i - 1);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,STAGGER
    {
      void build(float[] arr, int m, Random rand) {
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertTofloat((i * m + i) % arr.length);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,PLATEAU
    {
      void build(float[] arr, int m, Random rand) {
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertTofloat(Math.min(i, m));
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SHUFFLE
    {
      void build(float[] arr, int m, Random rand) {
        int x = 0, y = 0;
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertTofloat(rand.nextBoolean() ? (x += 2) : (y += 2));
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return getMaxRepsfloat(arrLength);
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ;
    abstract void build(float[] arr,int m,Random rand); 
    abstract int getNumRepetitions(int arrLength);
    abstract int getMLo();
    abstract int getMHi(int arrLength);
    abstract int incrementM(int m);
  }
  private static enum ArrayBuilderdouble
  {
    RANDOM
    {
      void build(double[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=getRanddouble(rand);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return getMaxRepsdouble(arrLength);
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    },
    ASCENDING
    {
      void build(double[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTodouble(m+i);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    },
    DESCENDING
    {
      void build(double[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTodouble(arr.length-m-i);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    },
    ALL_EQUAL
    {
      void build(double[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTodouble(m);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 0;
      }
      int getMHi(int arrLength)
      {
        return 0;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,MERGE_ASCENDING
    {
      void build(double[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int v=1;
        int i=0;
        for(int k=0;k<m;++k)
        {
          v=1;
          for(int p=0;p<period;++p)
          {
            arr[i++]=convertTodouble(v++);
          }
        }
        for(int j=1;j<arr.length-1;j++)
        {
          arr[j]=convertTodouble(v++);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 65;
      }
      int getMHi(int arrLength)
      {
        return 69;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,MERGE_DESCENDING
    {
      void build(double[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int v=-1;
        int i=0;
        for(int k=0;k<m;++k)
        {
          v=-1;
          for(int p=0;p<period;++p)
          {
            arr[i++]=convertTodouble(v--);
          }
        }
        for(int j=1;j<arr.length-1;j++)
        {
          arr[j]=convertTodouble(v--);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 65;
      }
      int getMHi(int arrLength)
      {
        return 69;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,SAW
    {
      void build(double[] arr,int m,Random rand)
      {
        int incCount=1;
        int decCount=arr.length;
        int i=0;
        int period=m--;
        for(;;)
        {
          for(int k=1;k<=period;k++)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTodouble(incCount++);
          }
          period+=m;
          for(int k=1;k<=period;k++)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTodouble(decCount--);
          }
          period+=m;
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SORTED_REPEATED
    {
      void build(double[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int i=0;
        int k=0;
        for(;;)
        {
          for(int t=1;t<=period;++t)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTodouble(k);
          }
          if(i>=arr.length)
          {
            return;
          }
          ++k;
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return Math.min(arrLength,7);
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,REPEATED
    {
      void build(double[] arr, int m, Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTodouble(i%m);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,DUPLICATED
    {
      void build(double[] arr, int m, Random rand)
      {
        for (int i = 0; i < arr.length; i++)
        {
          arr[i] = convertTodouble(rand.nextInt(m));
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return getMaxRepsdouble(arrLength);
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SORTED_ORGAN_PIPES
    {
      void build(double[] arr,int m,Random rand)
      {
        int i=0;
        int k=m;
        for(;;)
        {
          for(int t=1;t<=m;++t)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTodouble(k);
          }
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return Math.min(arrLength,7);
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,ORGAN_PIPES
    {
      void build(double[] arr, int m, Random rand)
      {
        int middle = arr.length / (m + 1);
        for (int i = 0; i < middle; i++) {
            arr[i] = convertTodouble(i);
        }
        for (int i = middle; i < arr.length; i++) {
            arr[i] = convertTodouble(arr.length - i - 1);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,STAGGER
    {
      void build(double[] arr, int m, Random rand) {
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertTodouble((i * m + i) % arr.length);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,PLATEAU
    {
      void build(double[] arr, int m, Random rand) {
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertTodouble(Math.min(i, m));
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SHUFFLE
    {
      void build(double[] arr, int m, Random rand) {
        int x = 0, y = 0;
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertTodouble(rand.nextBoolean() ? (x += 2) : (y += 2));
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return getMaxRepsdouble(arrLength);
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ;
    abstract void build(double[] arr,int m,Random rand); 
    abstract int getNumRepetitions(int arrLength);
    abstract int getMLo();
    abstract int getMHi(int arrLength);
    abstract int incrementM(int m);
  }
  /*//TODO uncomment when the eclipse module bug is fixed
  private static enum ArrayBuilderString
  {
    RANDOM
    {
      void build(String[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=getRandString(rand);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return getMaxRepsString(arrLength);
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    },
    ASCENDING
    {
      void build(String[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertToString(m+i);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    },
    DESCENDING
    {
      void build(String[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertToString(arr.length-m-i);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    },
    ALL_EQUAL
    {
      void build(String[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertToString(m);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 0;
      }
      int getMHi(int arrLength)
      {
        return 0;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,MERGE_ASCENDING
    {
      void build(String[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int v=1;
        int i=0;
        for(int k=0;k<m;++k)
        {
          v=1;
          for(int p=0;p<period;++p)
          {
            arr[i++]=convertToString(v++);
          }
        }
        for(int j=1;j<arr.length-1;j++)
        {
          arr[j]=convertToString(v++);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 65;
      }
      int getMHi(int arrLength)
      {
        return 69;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,MERGE_DESCENDING
    {
      void build(String[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int v=-1;
        int i=0;
        for(int k=0;k<m;++k)
        {
          v=-1;
          for(int p=0;p<period;++p)
          {
            arr[i++]=convertToString(v--);
          }
        }
        for(int j=1;j<arr.length-1;j++)
        {
          arr[j]=convertToString(v--);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 65;
      }
      int getMHi(int arrLength)
      {
        return 69;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,SAW
    {
      void build(String[] arr,int m,Random rand)
      {
        int incCount=1;
        int decCount=arr.length;
        int i=0;
        int period=m--;
        for(;;)
        {
          for(int k=1;k<=period;k++)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertToString(incCount++);
          }
          period+=m;
          for(int k=1;k<=period;k++)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertToString(decCount--);
          }
          period+=m;
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SORTED_REPEATED
    {
      void build(String[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int i=0;
        int k=0;
        for(;;)
        {
          for(int t=1;t<=period;++t)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertToString(k);
          }
          if(i>=arr.length)
          {
            return;
          }
          ++k;
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return Math.min(arrLength,7);
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,REPEATED
    {
      void build(String[] arr, int m, Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertToString(i%m);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,DUPLICATED
    {
      void build(String[] arr, int m, Random rand)
      {
        for (int i = 0; i < arr.length; i++)
        {
          arr[i] = convertToString(rand.nextInt(m));
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return getMaxRepsString(arrLength);
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SORTED_ORGAN_PIPES
    {
      void build(String[] arr,int m,Random rand)
      {
        int i=0;
        int k=m;
        for(;;)
        {
          for(int t=1;t<=m;++t)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertToString(k);
          }
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return Math.min(arrLength,7);
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,ORGAN_PIPES
    {
      void build(String[] arr, int m, Random rand)
      {
        int middle = arr.length / (m + 1);
        for (int i = 0; i < middle; i++) {
            arr[i] = convertToString(i);
        }
        for (int i = middle; i < arr.length; i++) {
            arr[i] = convertToString(arr.length - i - 1);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,STAGGER
    {
      void build(String[] arr, int m, Random rand) {
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertToString((i * m + i) % arr.length);
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,PLATEAU
    {
      void build(String[] arr, int m, Random rand) {
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertToString(Math.min(i, m));
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SHUFFLE
    {
      void build(String[] arr, int m, Random rand) {
        int x = 0, y = 0;
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertToString(rand.nextBoolean() ? (x += 2) : (y += 2));
        }
      }
      int getNumRepetitions(int arrLength)
      {
        return getMaxRepsString(arrLength);
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ;
    abstract void build(String[] arr,int m,Random rand); 
    abstract int getNumRepetitions(int arrLength);
    abstract int getMLo();
    abstract int getMHi(int arrLength);
    abstract int incrementM(int m);
  }
  */
  private static int getMaxRepsboolean(int arrLength)
  {
        return 1;
  }
  private static int getMaxRepsbyte(int arrLength)
  {
        return 1;
  }
  private static int getMaxRepschar(int arrLength)
  {
        if(arrLength>3201 || arrLength<47)
        {
          return 1;
        }
        return 7;
  }
  private static int getMaxRepsshort(int arrLength)
  {
        if(arrLength>3201 || arrLength<47)
        {
          return 1;
        }
        return 7;
  }
  private static int getMaxRepsint(int arrLength)
  {
        return 1;
  }
  private static int getMaxRepslong(int arrLength)
  {
        return 1;
  }
  private static int getMaxRepsfloat(int arrLength)
  {
        return 1;
  }
  private static int getMaxRepsdouble(int arrLength)
  {
        return 1;
  }
  private static int getMaxRepsString(int arrLength)
  {
        return 1;
  }
  private static void testuncheckedsortbooleanHelper(long randSeed,int arrLength) throws InterruptedException
  {
    boolean[] golden=new boolean[arrLength];
    boolean[] test=new boolean[arrLength];
    {
      Random rand=new Random(randSeed);
      for(ArrayBuilderboolean builder:ArrayBuilderboolean.values())
      {
        for(int m=builder.getMLo(),mHi=builder.getMHi(arrLength);m<=mHi;m=builder.incrementM(m))
        {
          System.out.println("sortType = sort; arrType = boolean; randSeed= "+randSeed+"; length= "+arrLength+"; builder type "+builder.name()+"; m = "+m);
          for(int i=0,numReps=builder.getNumRepetitions(arrLength);i<numReps;++i)
          {
            builder.build(golden,m,rand);
            ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
            isSortedsort(test,golden);
          }
        }
      }
    }
  }
  @Test
  public void testuncheckedsortboolean() throws InterruptedException
  {
    testuncheckedsortbooleanHelper(0,30);
  }
  private static void testuncheckedreverseSortbooleanHelper(long randSeed,int arrLength) throws InterruptedException
  {
    boolean[] golden=new boolean[arrLength];
    boolean[] test=new boolean[arrLength];
    {
      Random rand=new Random(randSeed);
      for(ArrayBuilderboolean builder:ArrayBuilderboolean.values())
      {
        for(int m=builder.getMLo(),mHi=builder.getMHi(arrLength);m<=mHi;m=builder.incrementM(m))
        {
          System.out.println("sortType = reverseSort; arrType = boolean; randSeed= "+randSeed+"; length= "+arrLength+"; builder type "+builder.name()+"; m = "+m);
          for(int i=0,numReps=builder.getNumRepetitions(arrLength);i<numReps;++i)
          {
            builder.build(golden,m,rand);
            ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
            isSortedreverseSort(test,golden);
          }
        }
      }
    }
  }
  @Test
  public void testuncheckedreverseSortboolean() throws InterruptedException
  {
    testuncheckedreverseSortbooleanHelper(0,30);
  }
  private static void testuncheckedsortbyteHelper(long randSeed,int arrLength) throws InterruptedException
  {
    byte[] golden=new byte[arrLength];
    byte[] test=new byte[arrLength];
    {
      Random rand=new Random(randSeed);
      for(ArrayBuilderbyte builder:ArrayBuilderbyte.values())
      {
        for(int m=builder.getMLo(),mHi=builder.getMHi(arrLength);m<=mHi;m=builder.incrementM(m))
        {
          System.out.println("sortType = sort; arrType = byte; randSeed= "+randSeed+"; length= "+arrLength+"; builder type "+builder.name()+"; m = "+m);
          for(int i=0,numReps=builder.getNumRepetitions(arrLength);i<numReps;++i)
          {
            builder.build(golden,m,rand);
            ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
            isSortedsort(test,golden);
          }
        }
      }
    }
  }
  @Test
  public void testuncheckedsortbyte() throws InterruptedException
  {
    testuncheckedsortbyteHelper(0,30);
    testuncheckedsortbyteHelper(0,31);
  }
  private static void testuncheckedreverseSortbyteHelper(long randSeed,int arrLength) throws InterruptedException
  {
    byte[] golden=new byte[arrLength];
    byte[] test=new byte[arrLength];
    {
      Random rand=new Random(randSeed);
      for(ArrayBuilderbyte builder:ArrayBuilderbyte.values())
      {
        for(int m=builder.getMLo(),mHi=builder.getMHi(arrLength);m<=mHi;m=builder.incrementM(m))
        {
          System.out.println("sortType = reverseSort; arrType = byte; randSeed= "+randSeed+"; length= "+arrLength+"; builder type "+builder.name()+"; m = "+m);
          for(int i=0,numReps=builder.getNumRepetitions(arrLength);i<numReps;++i)
          {
            builder.build(golden,m,rand);
            ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
            isSortedreverseSort(test,golden);
          }
        }
      }
    }
  }
  @Test
  public void testuncheckedreverseSortbyte() throws InterruptedException
  {
    testuncheckedreverseSortbyteHelper(0,30);
    testuncheckedreverseSortbyteHelper(0,31);
  }
  private static void testuncheckedsortcharHelper(long randSeed,int arrLength) throws InterruptedException
  {
    char[] golden=new char[arrLength];
    char[] test=new char[arrLength];
    {
      Random rand=new Random(randSeed);
      for(ArrayBuilderchar builder:ArrayBuilderchar.values())
      {
        for(int m=builder.getMLo(),mHi=builder.getMHi(arrLength);m<=mHi;m=builder.incrementM(m))
        {
          System.out.println("sortType = sort; arrType = char; randSeed= "+randSeed+"; length= "+arrLength+"; builder type "+builder.name()+"; m = "+m);
          for(int i=0,numReps=builder.getNumRepetitions(arrLength);i<numReps;++i)
          {
            builder.build(golden,m,rand);
            ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
            isSortedsort(test,golden);
          }
        }
      }
    }
  }
  @Test
  public void testuncheckedsortchar() throws InterruptedException
  {
    for(int i=0;i<lengths.length;++i)
    {
      int arrLength=lengths[i];
      for(int j=0;j<randSeeds.length;++j)
      {
        long randSeed=randSeeds[j];
        testuncheckedsortcharHelper(randSeed,arrLength);
      }
    }
  }
  private static void testuncheckedreverseSortcharHelper(long randSeed,int arrLength) throws InterruptedException
  {
    char[] golden=new char[arrLength];
    char[] test=new char[arrLength];
    {
      Random rand=new Random(randSeed);
      for(ArrayBuilderchar builder:ArrayBuilderchar.values())
      {
        for(int m=builder.getMLo(),mHi=builder.getMHi(arrLength);m<=mHi;m=builder.incrementM(m))
        {
          System.out.println("sortType = reverseSort; arrType = char; randSeed= "+randSeed+"; length= "+arrLength+"; builder type "+builder.name()+"; m = "+m);
          for(int i=0,numReps=builder.getNumRepetitions(arrLength);i<numReps;++i)
          {
            builder.build(golden,m,rand);
            ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
            isSortedreverseSort(test,golden);
          }
        }
      }
    }
  }
  @Test
  public void testuncheckedreverseSortchar() throws InterruptedException
  {
    for(int i=0;i<lengths.length;++i)
    {
      int arrLength=lengths[i];
      for(int j=0;j<randSeeds.length;++j)
      {
        long randSeed=randSeeds[j];
        testuncheckedreverseSortcharHelper(randSeed,arrLength);
      }
    }
  }
  private static void testuncheckedsortshortHelper(long randSeed,int arrLength) throws InterruptedException
  {
    short[] golden=new short[arrLength];
    short[] test=new short[arrLength];
    {
      Random rand=new Random(randSeed);
      for(ArrayBuildershort builder:ArrayBuildershort.values())
      {
        for(int m=builder.getMLo(),mHi=builder.getMHi(arrLength);m<=mHi;m=builder.incrementM(m))
        {
          System.out.println("sortType = sort; arrType = short; randSeed= "+randSeed+"; length= "+arrLength+"; builder type "+builder.name()+"; m = "+m);
          for(int i=0,numReps=builder.getNumRepetitions(arrLength);i<numReps;++i)
          {
            builder.build(golden,m,rand);
            ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
            isSortedsort(test,golden);
          }
        }
      }
    }
  }
  @Test
  public void testuncheckedsortshort() throws InterruptedException
  {
    for(int i=0;i<lengths.length;++i)
    {
      int arrLength=lengths[i];
      for(int j=0;j<randSeeds.length;++j)
      {
        long randSeed=randSeeds[j];
        testuncheckedsortshortHelper(randSeed,arrLength);
      }
    }
  }
  private static void testuncheckedreverseSortshortHelper(long randSeed,int arrLength) throws InterruptedException
  {
    short[] golden=new short[arrLength];
    short[] test=new short[arrLength];
    {
      Random rand=new Random(randSeed);
      for(ArrayBuildershort builder:ArrayBuildershort.values())
      {
        for(int m=builder.getMLo(),mHi=builder.getMHi(arrLength);m<=mHi;m=builder.incrementM(m))
        {
          System.out.println("sortType = reverseSort; arrType = short; randSeed= "+randSeed+"; length= "+arrLength+"; builder type "+builder.name()+"; m = "+m);
          for(int i=0,numReps=builder.getNumRepetitions(arrLength);i<numReps;++i)
          {
            builder.build(golden,m,rand);
            ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
            isSortedreverseSort(test,golden);
          }
        }
      }
    }
  }
  @Test
  public void testuncheckedreverseSortshort() throws InterruptedException
  {
    for(int i=0;i<lengths.length;++i)
    {
      int arrLength=lengths[i];
      for(int j=0;j<randSeeds.length;++j)
      {
        long randSeed=randSeeds[j];
        testuncheckedreverseSortshortHelper(randSeed,arrLength);
      }
    }
  }
  private static void testuncheckedsortintHelper(long randSeed,int arrLength) throws InterruptedException
  {
    int[] golden=new int[arrLength];
    int[] test=new int[arrLength];
    {
      Random rand=new Random(randSeed);
      for(ArrayBuilderint builder:ArrayBuilderint.values())
      {
        for(int m=builder.getMLo(),mHi=builder.getMHi(arrLength);m<=mHi;m=builder.incrementM(m))
        {
          System.out.println("sortType = sort; arrType = int; randSeed= "+randSeed+"; length= "+arrLength+"; builder type "+builder.name()+"; m = "+m);
          for(int i=0,numReps=builder.getNumRepetitions(arrLength);i<numReps;++i)
          {
            builder.build(golden,m,rand);
            ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
            isSortedsort(test,golden);
          }
        }
      }
    }
  }
  @Test
  public void testuncheckedsortint() throws InterruptedException
  {
    for(int i=0;i<lengths.length;++i)
    {
      int arrLength=lengths[i];
      for(int j=0;j<randSeeds.length;++j)
      {
        long randSeed=randSeeds[j];
        testuncheckedsortintHelper(randSeed,arrLength);
      }
    }
  }
  private static void testuncheckedreverseSortintHelper(long randSeed,int arrLength) throws InterruptedException
  {
    int[] golden=new int[arrLength];
    int[] test=new int[arrLength];
    {
      Random rand=new Random(randSeed);
      for(ArrayBuilderint builder:ArrayBuilderint.values())
      {
        for(int m=builder.getMLo(),mHi=builder.getMHi(arrLength);m<=mHi;m=builder.incrementM(m))
        {
          System.out.println("sortType = reverseSort; arrType = int; randSeed= "+randSeed+"; length= "+arrLength+"; builder type "+builder.name()+"; m = "+m);
          for(int i=0,numReps=builder.getNumRepetitions(arrLength);i<numReps;++i)
          {
            builder.build(golden,m,rand);
            ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
            isSortedreverseSort(test,golden);
          }
        }
      }
    }
  }
  @Test
  public void testuncheckedreverseSortint() throws InterruptedException
  {
    for(int i=0;i<lengths.length;++i)
    {
      int arrLength=lengths[i];
      for(int j=0;j<randSeeds.length;++j)
      {
        long randSeed=randSeeds[j];
        testuncheckedreverseSortintHelper(randSeed,arrLength);
      }
    }
  }
  private static void testuncheckedsortlongHelper(long randSeed,int arrLength) throws InterruptedException
  {
    long[] golden=new long[arrLength];
    long[] test=new long[arrLength];
    {
      Random rand=new Random(randSeed);
      for(ArrayBuilderlong builder:ArrayBuilderlong.values())
      {
        for(int m=builder.getMLo(),mHi=builder.getMHi(arrLength);m<=mHi;m=builder.incrementM(m))
        {
          System.out.println("sortType = sort; arrType = long; randSeed= "+randSeed+"; length= "+arrLength+"; builder type "+builder.name()+"; m = "+m);
          for(int i=0,numReps=builder.getNumRepetitions(arrLength);i<numReps;++i)
          {
            builder.build(golden,m,rand);
            ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
            isSortedsort(test,golden);
          }
        }
      }
    }
  }
  @Test
  public void testuncheckedsortlong() throws InterruptedException
  {
    for(int i=0;i<lengths.length;++i)
    {
      int arrLength=lengths[i];
      for(int j=0;j<randSeeds.length;++j)
      {
        long randSeed=randSeeds[j];
        testuncheckedsortlongHelper(randSeed,arrLength);
      }
    }
  }
  private static void testuncheckedreverseSortlongHelper(long randSeed,int arrLength) throws InterruptedException
  {
    long[] golden=new long[arrLength];
    long[] test=new long[arrLength];
    {
      Random rand=new Random(randSeed);
      for(ArrayBuilderlong builder:ArrayBuilderlong.values())
      {
        for(int m=builder.getMLo(),mHi=builder.getMHi(arrLength);m<=mHi;m=builder.incrementM(m))
        {
          System.out.println("sortType = reverseSort; arrType = long; randSeed= "+randSeed+"; length= "+arrLength+"; builder type "+builder.name()+"; m = "+m);
          for(int i=0,numReps=builder.getNumRepetitions(arrLength);i<numReps;++i)
          {
            builder.build(golden,m,rand);
            ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
            isSortedreverseSort(test,golden);
          }
        }
      }
    }
  }
  @Test
  public void testuncheckedreverseSortlong() throws InterruptedException
  {
    for(int i=0;i<lengths.length;++i)
    {
      int arrLength=lengths[i];
      for(int j=0;j<randSeeds.length;++j)
      {
        long randSeed=randSeeds[j];
        testuncheckedreverseSortlongHelper(randSeed,arrLength);
      }
    }
  }
  private static void testuncheckedsortfloatHelper(long randSeed,int arrLength) throws InterruptedException
  {
    float[] golden=new float[arrLength];
    float[] test=new float[arrLength];
    {
      Random rand=new Random(randSeed);
      for(floatBuilder builder:floatBuilder.values())
      {
        for(int a=0;a<10;++a)
        {
          for(int g=0;g<=10;++g)
          {
            for(int z=0;z<=10;++z)
            {
              for(int n=0;n<=10;++n)
              {
                for(int p=0;p<=10;++p)
                {
                  if(a+g+z+n+p==arrLength)
                  {
                    System.out.println("sortType = sort; arrType = float; randSeed= "+randSeed+"; length= "+arrLength+"; builder type "+builder.name()+"; a ="+a+"; g="+g+"; z="+z+"; n="+n+"; p="+p);
                    builder.build(golden,a,g,z,n,p,rand);
                    ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
                    isSortedsort(test,golden);
                  }
                }
              }
            }
          }
        }
      }
    }
    {
      Random rand=new Random(randSeed);
      for(ArrayBuilderfloat builder:ArrayBuilderfloat.values())
      {
        for(int m=builder.getMLo(),mHi=builder.getMHi(arrLength);m<=mHi;m=builder.incrementM(m))
        {
          System.out.println("sortType = sort; arrType = float; randSeed= "+randSeed+"; length= "+arrLength+"; builder type "+builder.name()+"; m = "+m);
          for(int i=0,numReps=builder.getNumRepetitions(arrLength);i<numReps;++i)
          {
            builder.build(golden,m,rand);
            ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
            isSortedsort(test,golden);
          }
        }
      }
    }
  }
  @Test
  public void testuncheckedsortfloat() throws InterruptedException
  {
    for(int i=0;i<lengths.length;++i)
    {
      int arrLength=lengths[i];
      for(int j=0;j<randSeeds.length;++j)
      {
        long randSeed=randSeeds[j];
        testuncheckedsortfloatHelper(randSeed,arrLength);
      }
    }
  }
  private static void testuncheckedreverseSortfloatHelper(long randSeed,int arrLength) throws InterruptedException
  {
    float[] golden=new float[arrLength];
    float[] test=new float[arrLength];
    {
      Random rand=new Random(randSeed);
      for(floatBuilder builder:floatBuilder.values())
      {
        for(int a=0;a<10;++a)
        {
          for(int g=0;g<=10;++g)
          {
            for(int z=0;z<=10;++z)
            {
              for(int n=0;n<=10;++n)
              {
                for(int p=0;p<=10;++p)
                {
                  if(a+g+z+n+p==arrLength)
                  {
                    System.out.println("sortType = reverseSort; arrType = float; randSeed= "+randSeed+"; length= "+arrLength+"; builder type "+builder.name()+"; a ="+a+"; g="+g+"; z="+z+"; n="+n+"; p="+p);
                    builder.build(golden,a,g,z,n,p,rand);
                    ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
                    isSortedreverseSort(test,golden);
                  }
                }
              }
            }
          }
        }
      }
    }
    {
      Random rand=new Random(randSeed);
      for(ArrayBuilderfloat builder:ArrayBuilderfloat.values())
      {
        for(int m=builder.getMLo(),mHi=builder.getMHi(arrLength);m<=mHi;m=builder.incrementM(m))
        {
          System.out.println("sortType = reverseSort; arrType = float; randSeed= "+randSeed+"; length= "+arrLength+"; builder type "+builder.name()+"; m = "+m);
          for(int i=0,numReps=builder.getNumRepetitions(arrLength);i<numReps;++i)
          {
            builder.build(golden,m,rand);
            ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
            isSortedreverseSort(test,golden);
          }
        }
      }
    }
  }
  @Test
  public void testuncheckedreverseSortfloat() throws InterruptedException
  {
    for(int i=0;i<lengths.length;++i)
    {
      int arrLength=lengths[i];
      for(int j=0;j<randSeeds.length;++j)
      {
        long randSeed=randSeeds[j];
        testuncheckedreverseSortfloatHelper(randSeed,arrLength);
      }
    }
  }
  private static void testuncheckedsortdoubleHelper(long randSeed,int arrLength) throws InterruptedException
  {
    double[] golden=new double[arrLength];
    double[] test=new double[arrLength];
    {
      Random rand=new Random(randSeed);
      for(doubleBuilder builder:doubleBuilder.values())
      {
        for(int a=0;a<10;++a)
        {
          for(int g=0;g<=10;++g)
          {
            for(int z=0;z<=10;++z)
            {
              for(int n=0;n<=10;++n)
              {
                for(int p=0;p<=10;++p)
                {
                  if(a+g+z+n+p==arrLength)
                  {
                    System.out.println("sortType = sort; arrType = double; randSeed= "+randSeed+"; length= "+arrLength+"; builder type "+builder.name()+"; a ="+a+"; g="+g+"; z="+z+"; n="+n+"; p="+p);
                    builder.build(golden,a,g,z,n,p,rand);
                    ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
                    isSortedsort(test,golden);
                  }
                }
              }
            }
          }
        }
      }
    }
    {
      Random rand=new Random(randSeed);
      for(ArrayBuilderdouble builder:ArrayBuilderdouble.values())
      {
        for(int m=builder.getMLo(),mHi=builder.getMHi(arrLength);m<=mHi;m=builder.incrementM(m))
        {
          System.out.println("sortType = sort; arrType = double; randSeed= "+randSeed+"; length= "+arrLength+"; builder type "+builder.name()+"; m = "+m);
          for(int i=0,numReps=builder.getNumRepetitions(arrLength);i<numReps;++i)
          {
            builder.build(golden,m,rand);
            ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
            isSortedsort(test,golden);
          }
        }
      }
    }
  }
  @Test
  public void testuncheckedsortdouble() throws InterruptedException
  {
    for(int i=0;i<lengths.length;++i)
    {
      int arrLength=lengths[i];
      for(int j=0;j<randSeeds.length;++j)
      {
        long randSeed=randSeeds[j];
        testuncheckedsortdoubleHelper(randSeed,arrLength);
      }
    }
  }
  private static void testuncheckedreverseSortdoubleHelper(long randSeed,int arrLength) throws InterruptedException
  {
    double[] golden=new double[arrLength];
    double[] test=new double[arrLength];
    {
      Random rand=new Random(randSeed);
      for(doubleBuilder builder:doubleBuilder.values())
      {
        for(int a=0;a<10;++a)
        {
          for(int g=0;g<=10;++g)
          {
            for(int z=0;z<=10;++z)
            {
              for(int n=0;n<=10;++n)
              {
                for(int p=0;p<=10;++p)
                {
                  if(a+g+z+n+p==arrLength)
                  {
                    System.out.println("sortType = reverseSort; arrType = double; randSeed= "+randSeed+"; length= "+arrLength+"; builder type "+builder.name()+"; a ="+a+"; g="+g+"; z="+z+"; n="+n+"; p="+p);
                    builder.build(golden,a,g,z,n,p,rand);
                    ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
                    isSortedreverseSort(test,golden);
                  }
                }
              }
            }
          }
        }
      }
    }
    {
      Random rand=new Random(randSeed);
      for(ArrayBuilderdouble builder:ArrayBuilderdouble.values())
      {
        for(int m=builder.getMLo(),mHi=builder.getMHi(arrLength);m<=mHi;m=builder.incrementM(m))
        {
          System.out.println("sortType = reverseSort; arrType = double; randSeed= "+randSeed+"; length= "+arrLength+"; builder type "+builder.name()+"; m = "+m);
          for(int i=0,numReps=builder.getNumRepetitions(arrLength);i<numReps;++i)
          {
            builder.build(golden,m,rand);
            ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
            isSortedreverseSort(test,golden);
          }
        }
      }
    }
  }
  @Test
  public void testuncheckedreverseSortdouble() throws InterruptedException
  {
    for(int i=0;i<lengths.length;++i)
    {
      int arrLength=lengths[i];
      for(int j=0;j<randSeeds.length;++j)
      {
        long randSeed=randSeeds[j];
        testuncheckedreverseSortdoubleHelper(randSeed,arrLength);
      }
    }
  }
  private static enum floatBuilder
  {
    SIMPLE
    {
      void build(float[] arr,int a,int g,int z,int n,int p,Random rand)
      {
        int fromIndex,toIndex;
        float negVal=-getRandfloat(rand);
        float posVal=getRandfloat(rand);
        for(fromIndex=0,toIndex=n;fromIndex<toIndex;++fromIndex)
        {
          arr[fromIndex]=negVal;
        }
        for(toIndex=fromIndex+g;fromIndex<toIndex;++fromIndex)
        {
          arr[fromIndex]=-0.0f;
        }
        for(toIndex=fromIndex+z;fromIndex<toIndex;++fromIndex)
        {
          arr[fromIndex]=0.0f;
        }
        for(toIndex=fromIndex+p;fromIndex<toIndex;++fromIndex)
        {
          arr[fromIndex]=posVal;
        }
        for(toIndex=fromIndex+a;fromIndex<toIndex;++fromIndex)
        {
          arr[fromIndex]=Float.NaN;
        }
      }
    };
    abstract void build(float[] arr,int a,int g,int z,int n,int p,Random rand);
  }
  private static enum doubleBuilder
  {
    SIMPLE
    {
      void build(double[] arr,int a,int g,int z,int n,int p,Random rand)
      {
        int fromIndex,toIndex;
        double negVal=-getRanddouble(rand);
        double posVal=getRanddouble(rand);
        for(fromIndex=0,toIndex=n;fromIndex<toIndex;++fromIndex)
        {
          arr[fromIndex]=negVal;
        }
        for(toIndex=fromIndex+g;fromIndex<toIndex;++fromIndex)
        {
          arr[fromIndex]=-0.0d;
        }
        for(toIndex=fromIndex+z;fromIndex<toIndex;++fromIndex)
        {
          arr[fromIndex]=0.0d;
        }
        for(toIndex=fromIndex+p;fromIndex<toIndex;++fromIndex)
        {
          arr[fromIndex]=posVal;
        }
        for(toIndex=fromIndex+a;fromIndex<toIndex;++fromIndex)
        {
          arr[fromIndex]=Double.NaN;
        }
      }
    };
    abstract void build(double[] arr,int a,int g,int z,int n,int p,Random rand);
  }
}
