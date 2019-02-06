package omni.util;
import org.junit.jupiter.api.Test;
import java.util.Random;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.LongStream;
import java.util.function.IntBinaryOperator;
import omni.function.BooleanComparator;
import omni.function.ByteComparator;
import omni.function.CharComparator;
import omni.function.ShortComparator;
import omni.function.LongComparator;
import omni.function.FloatComparator;
import omni.function.DoubleComparator;
public class SortUtilTest
{
  private static final int[] lengths=new int[]{2, 3, 5, 8, 13, 21, 34, 55, 100, 1000, 10000, 100000, 1000000};
  private static final long[] randSeeds=new long[]{666L,0xC0FFEEL,999L};
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
  private static void isSortedsort(String[] arr,String[] copy)
  {
    Thread stockSorterThread=new Thread(()->
    {
    Arrays.sort(copy,0,copy.length);
    });
    stockSorterThread.start();
    SortUtil.uncheckedsort(arr,0,arr.length-1);
    try
    {
      stockSorterThread.join();
    }
    catch(InterruptedException e)
    {
      throw new AssertionError(e);
    }
    JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSortedsort(boolean[] arr,boolean[] copy)
  {
    Thread stockSorterThread=new Thread(()->
    {
    Boolean[] boxedCopy=new Boolean[copy.length];
    ArrCopy.uncheckedCopy(copy,0,boxedCopy,0,copy.length);
    Arrays.sort(boxedCopy,0,copy.length);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    });
    stockSorterThread.start();
    SortUtil.uncheckedsort(arr,0,arr.length-1);
    try
    {
      stockSorterThread.join();
    }
    catch(InterruptedException e)
    {
      throw new AssertionError(e);
    }
    JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSortedsort(byte[] arr,byte[] copy)
  {
    Thread stockSorterThread=new Thread(()->
    {
    Arrays.sort(copy,0,copy.length);
    });
    stockSorterThread.start();
    SortUtil.uncheckedsort(arr,0,arr.length-1);
    try
    {
      stockSorterThread.join();
    }
    catch(InterruptedException e)
    {
      throw new AssertionError(e);
    }
    JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSortedsort(char[] arr,char[] copy)
  {
    Thread stockSorterThread=new Thread(()->
    {
    Arrays.sort(copy,0,copy.length);
    });
    stockSorterThread.start();
    SortUtil.uncheckedsort(arr,0,arr.length-1);
    try
    {
      stockSorterThread.join();
    }
    catch(InterruptedException e)
    {
      throw new AssertionError(e);
    }
    JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSortedsort(short[] arr,short[] copy)
  {
    Thread stockSorterThread=new Thread(()->
    {
    Arrays.sort(copy,0,copy.length);
    });
    stockSorterThread.start();
    SortUtil.uncheckedsort(arr,0,arr.length-1);
    try
    {
      stockSorterThread.join();
    }
    catch(InterruptedException e)
    {
      throw new AssertionError(e);
    }
    JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSortedsort(int[] arr,int[] copy)
  {
    Thread stockSorterThread=new Thread(()->
    {
    Arrays.sort(copy,0,copy.length);
    });
    stockSorterThread.start();
    SortUtil.uncheckedsort(arr,0,arr.length-1);
    try
    {
      stockSorterThread.join();
    }
    catch(InterruptedException e)
    {
      throw new AssertionError(e);
    }
    JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSortedsort(long[] arr,long[] copy)
  {
    Thread stockSorterThread=new Thread(()->
    {
    Arrays.sort(copy,0,copy.length);
    });
    stockSorterThread.start();
    SortUtil.uncheckedsort(arr,0,arr.length-1);
    try
    {
      stockSorterThread.join();
    }
    catch(InterruptedException e)
    {
      throw new AssertionError(e);
    }
    JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSortedsort(float[] arr,float[] copy)
  {
    Thread stockSorterThread=new Thread(()->
    {
    Arrays.sort(copy,0,copy.length);
    });
    stockSorterThread.start();
    SortUtil.uncheckedsort(arr,0,arr.length-1);
    try
    {
      stockSorterThread.join();
    }
    catch(InterruptedException e)
    {
      throw new AssertionError(e);
    }
    JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSortedsort(double[] arr,double[] copy)
  {
    Thread stockSorterThread=new Thread(()->
    {
    Arrays.sort(copy,0,copy.length);
    });
    stockSorterThread.start();
    SortUtil.uncheckedsort(arr,0,arr.length-1);
    try
    {
      stockSorterThread.join();
    }
    catch(InterruptedException e)
    {
      throw new AssertionError(e);
    }
    JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSortedreverseSort(String[] arr,String[] copy)
  {
    Thread stockSorterThread=new Thread(()->
    {
    Arrays.sort(copy,0,copy.length);
    OmniArray.OfRef.reverseRange(copy,0,copy.length-1);
    });
    stockSorterThread.start();
    SortUtil.uncheckedreverseSort(arr,0,arr.length-1);
    try
    {
      stockSorterThread.join();
    }
    catch(InterruptedException e)
    {
      throw new AssertionError(e);
    }
    JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSortedreverseSort(boolean[] arr,boolean[] copy)
  {
    Thread stockSorterThread=new Thread(()->
    {
    Boolean[] boxedCopy=new Boolean[copy.length];
    ArrCopy.uncheckedCopy(copy,0,boxedCopy,0,copy.length);
    Arrays.sort(boxedCopy,0,copy.length);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    OmniArray.OfBoolean.reverseRange(copy,0,copy.length-1);
    });
    stockSorterThread.start();
    SortUtil.uncheckedreverseSort(arr,0,arr.length-1);
    try
    {
      stockSorterThread.join();
    }
    catch(InterruptedException e)
    {
      throw new AssertionError(e);
    }
    JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSortedreverseSort(byte[] arr,byte[] copy)
  {
    Thread stockSorterThread=new Thread(()->
    {
    Arrays.sort(copy,0,copy.length);
    OmniArray.OfByte.reverseRange(copy,0,copy.length-1);
    });
    stockSorterThread.start();
    SortUtil.uncheckedreverseSort(arr,0,arr.length-1);
    try
    {
      stockSorterThread.join();
    }
    catch(InterruptedException e)
    {
      throw new AssertionError(e);
    }
    JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSortedreverseSort(char[] arr,char[] copy)
  {
    Thread stockSorterThread=new Thread(()->
    {
    Arrays.sort(copy,0,copy.length);
    OmniArray.OfChar.reverseRange(copy,0,copy.length-1);
    });
    stockSorterThread.start();
    SortUtil.uncheckedreverseSort(arr,0,arr.length-1);
    try
    {
      stockSorterThread.join();
    }
    catch(InterruptedException e)
    {
      throw new AssertionError(e);
    }
    JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSortedreverseSort(short[] arr,short[] copy)
  {
    Thread stockSorterThread=new Thread(()->
    {
    Arrays.sort(copy,0,copy.length);
    OmniArray.OfShort.reverseRange(copy,0,copy.length-1);
    });
    stockSorterThread.start();
    SortUtil.uncheckedreverseSort(arr,0,arr.length-1);
    try
    {
      stockSorterThread.join();
    }
    catch(InterruptedException e)
    {
      throw new AssertionError(e);
    }
    JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSortedreverseSort(int[] arr,int[] copy)
  {
    Thread stockSorterThread=new Thread(()->
    {
    Arrays.sort(copy,0,copy.length);
    OmniArray.OfInt.reverseRange(copy,0,copy.length-1);
    });
    stockSorterThread.start();
    SortUtil.uncheckedreverseSort(arr,0,arr.length-1);
    try
    {
      stockSorterThread.join();
    }
    catch(InterruptedException e)
    {
      throw new AssertionError(e);
    }
    JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSortedreverseSort(long[] arr,long[] copy)
  {
    Thread stockSorterThread=new Thread(()->
    {
    Arrays.sort(copy,0,copy.length);
    OmniArray.OfLong.reverseRange(copy,0,copy.length-1);
    });
    stockSorterThread.start();
    SortUtil.uncheckedreverseSort(arr,0,arr.length-1);
    try
    {
      stockSorterThread.join();
    }
    catch(InterruptedException e)
    {
      throw new AssertionError(e);
    }
    JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSortedreverseSort(float[] arr,float[] copy)
  {
    Thread stockSorterThread=new Thread(()->
    {
    Arrays.sort(copy,0,copy.length);
    OmniArray.OfFloat.reverseRange(copy,0,copy.length-1);
    });
    stockSorterThread.start();
    SortUtil.uncheckedreverseSort(arr,0,arr.length-1);
    try
    {
      stockSorterThread.join();
    }
    catch(InterruptedException e)
    {
      throw new AssertionError(e);
    }
    JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSortedreverseSort(double[] arr,double[] copy)
  {
    Thread stockSorterThread=new Thread(()->
    {
    Arrays.sort(copy,0,copy.length);
    OmniArray.OfDouble.reverseRange(copy,0,copy.length-1);
    });
    stockSorterThread.start();
    SortUtil.uncheckedreverseSort(arr,0,arr.length-1);
    try
    {
      stockSorterThread.join();
    }
    catch(InterruptedException e)
    {
      throw new AssertionError(e);
    }
    JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSorted(String[] arr,String[] copy,Comparator<? super String> sorter)
  {
    Thread stockSorterThread=new Thread(()->
    {
    Arrays.parallelSort(copy,0,copy.length,sorter);
    });
    stockSorterThread.start();
    SortUtil.uncheckedcomparatorSort(arr,0,arr.length-1,sorter);
    try
    {
      stockSorterThread.join();
    }
    catch(InterruptedException e)
    {
      throw new AssertionError(e);
    }
    JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSorted(boolean[] arr,boolean[] copy,BooleanComparator sorter)
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
    try
    {
      stockSorterThread.join();
    }
    catch(InterruptedException e)
    {
      throw new AssertionError(e);
    }
    JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSorted(byte[] arr,byte[] copy,ByteComparator sorter)
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
    try
    {
      stockSorterThread.join();
    }
    catch(InterruptedException e)
    {
      throw new AssertionError(e);
    }
    JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSorted(char[] arr,char[] copy,CharComparator sorter)
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
    try
    {
      stockSorterThread.join();
    }
    catch(InterruptedException e)
    {
      throw new AssertionError(e);
    }
    JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSorted(short[] arr,short[] copy,ShortComparator sorter)
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
    try
    {
      stockSorterThread.join();
    }
    catch(InterruptedException e)
    {
      throw new AssertionError(e);
    }
    JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSorted(int[] arr,int[] copy,IntBinaryOperator sorter)
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
    try
    {
      stockSorterThread.join();
    }
    catch(InterruptedException e)
    {
      throw new AssertionError(e);
    }
    JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSorted(long[] arr,long[] copy,LongComparator sorter)
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
    try
    {
      stockSorterThread.join();
    }
    catch(InterruptedException e)
    {
      throw new AssertionError(e);
    }
    JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSorted(float[] arr,float[] copy,FloatComparator sorter)
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
    try
    {
      stockSorterThread.join();
    }
    catch(InterruptedException e)
    {
      throw new AssertionError(e);
    }
    JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSorted(double[] arr,double[] copy,DoubleComparator sorter)
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
    try
    {
      stockSorterThread.join();
    }
    catch(InterruptedException e)
    {
      throw new AssertionError(e);
    }
    JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static int getMLo(JunitUtil.booleanArrayBuilder builder)
  {
    switch(builder)
    {
      case AllEquals:
        return 0;
      default:
        return 1;
    }
  }
  private static int getMHi(JunitUtil.booleanArrayBuilder builder,int arrLength)
  {
    switch(builder)
    {
      default:
        return 1;
    }
  }
  private static int getNumReps(JunitUtil.booleanArrayBuilder builder,int arrLength)
  {
    return 1;
  }
  private static int incrementM(JunitUtil.booleanArrayBuilder builder,int m)
  {
    switch(builder)
    {
      default:
        return m+1;
    }
  }
  private static int getMLo(JunitUtil.byteArrayBuilder builder)
  {
    switch(builder)
    {
      case AllEquals:
        return 0;
      case MergeAscending:
      case MergeDescending:
        return 65;
      default:
        return 1;
    }
  }
  private static int getMHi(JunitUtil.byteArrayBuilder builder,int arrLength)
  {
    switch(builder)
    {
      case Ascending:
      case Descending:
      case Saw:
      case Repeated:
      case Duplicated:
      case OrganPipes:
      case Stagger:
      case Plateau:
        return (2*arrLength)-1;
      case AllEquals:
        return 0;
      case MergeAscending:
      case MergeDescending:
        return 69;
      case SortedRepeated:
      case SortedOrganPipes:
        return Math.min(arrLength,7);
      default:
        return 1;
    }
  }
  private static int getNumReps(JunitUtil.byteArrayBuilder builder,int arrLength)
  {
    return 1;
  }
  private static int incrementM(JunitUtil.byteArrayBuilder builder,int m)
  {
    switch(builder)
    {
      case Ascending:
      case Descending:
      case Saw:
      case Repeated:
      case Duplicated:
      case OrganPipes:
      case Stagger:
      case Plateau:
        return m*2;
      default:
        return m+1;
    }
  }
  private static int getMLo(JunitUtil.charArrayBuilder builder)
  {
    switch(builder)
    {
      case AllEquals:
        return 0;
      case MergeAscending:
      case MergeDescending:
        return 65;
      default:
        return 1;
    }
  }
  private static int getMHi(JunitUtil.charArrayBuilder builder,int arrLength)
  {
    switch(builder)
    {
      case Ascending:
      case Descending:
      case Saw:
      case Repeated:
      case Duplicated:
      case OrganPipes:
      case Stagger:
      case Plateau:
        return (2*arrLength)-1;
      case AllEquals:
        return 0;
      case MergeAscending:
      case MergeDescending:
        return 69;
      case SortedRepeated:
      case SortedOrganPipes:
        return Math.min(arrLength,7);
      default:
        return 1;
    }
  }
  private static int getNumReps(JunitUtil.charArrayBuilder builder,int arrLength)
  {
    if(builder.isRandomized())
    {
      if(arrLength>3201 || arrLength<47)
        {
          return 1;
        }
        return 7;
    }
    return 1;
  }
  private static int incrementM(JunitUtil.charArrayBuilder builder,int m)
  {
    switch(builder)
    {
      case Ascending:
      case Descending:
      case Saw:
      case Repeated:
      case Duplicated:
      case OrganPipes:
      case Stagger:
      case Plateau:
        return m*2;
      default:
        return m+1;
    }
  }
  private static int getMLo(JunitUtil.shortArrayBuilder builder)
  {
    switch(builder)
    {
      case AllEquals:
        return 0;
      case MergeAscending:
      case MergeDescending:
        return 65;
      default:
        return 1;
    }
  }
  private static int getMHi(JunitUtil.shortArrayBuilder builder,int arrLength)
  {
    switch(builder)
    {
      case Ascending:
      case Descending:
      case Saw:
      case Repeated:
      case Duplicated:
      case OrganPipes:
      case Stagger:
      case Plateau:
        return (2*arrLength)-1;
      case AllEquals:
        return 0;
      case MergeAscending:
      case MergeDescending:
        return 69;
      case SortedRepeated:
      case SortedOrganPipes:
        return Math.min(arrLength,7);
      default:
        return 1;
    }
  }
  private static int getNumReps(JunitUtil.shortArrayBuilder builder,int arrLength)
  {
    if(builder.isRandomized())
    {
      if(arrLength>3201 || arrLength<47)
        {
          return 1;
        }
        return 7;
    }
    return 1;
  }
  private static int incrementM(JunitUtil.shortArrayBuilder builder,int m)
  {
    switch(builder)
    {
      case Ascending:
      case Descending:
      case Saw:
      case Repeated:
      case Duplicated:
      case OrganPipes:
      case Stagger:
      case Plateau:
        return m*2;
      default:
        return m+1;
    }
  }
  private static int getMLo(JunitUtil.intArrayBuilder builder)
  {
    switch(builder)
    {
      case AllEquals:
        return 0;
      case MergeAscending:
      case MergeDescending:
        return 65;
      default:
        return 1;
    }
  }
  private static int getMHi(JunitUtil.intArrayBuilder builder,int arrLength)
  {
    switch(builder)
    {
      case Ascending:
      case Descending:
      case Saw:
      case Repeated:
      case Duplicated:
      case OrganPipes:
      case Stagger:
      case Plateau:
        return (2*arrLength)-1;
      case AllEquals:
        return 0;
      case MergeAscending:
      case MergeDescending:
        return 69;
      case SortedRepeated:
      case SortedOrganPipes:
        return Math.min(arrLength,7);
      default:
        return 1;
    }
  }
  private static int getNumReps(JunitUtil.intArrayBuilder builder,int arrLength)
  {
    return 1;
  }
  private static int incrementM(JunitUtil.intArrayBuilder builder,int m)
  {
    switch(builder)
    {
      case Ascending:
      case Descending:
      case Saw:
      case Repeated:
      case Duplicated:
      case OrganPipes:
      case Stagger:
      case Plateau:
        return m*2;
      default:
        return m+1;
    }
  }
  private static int getMLo(JunitUtil.longArrayBuilder builder)
  {
    switch(builder)
    {
      case AllEquals:
        return 0;
      case MergeAscending:
      case MergeDescending:
        return 65;
      default:
        return 1;
    }
  }
  private static int getMHi(JunitUtil.longArrayBuilder builder,int arrLength)
  {
    switch(builder)
    {
      case Ascending:
      case Descending:
      case Saw:
      case Repeated:
      case Duplicated:
      case OrganPipes:
      case Stagger:
      case Plateau:
        return (2*arrLength)-1;
      case AllEquals:
        return 0;
      case MergeAscending:
      case MergeDescending:
        return 69;
      case SortedRepeated:
      case SortedOrganPipes:
        return Math.min(arrLength,7);
      default:
        return 1;
    }
  }
  private static int getNumReps(JunitUtil.longArrayBuilder builder,int arrLength)
  {
    return 1;
  }
  private static int incrementM(JunitUtil.longArrayBuilder builder,int m)
  {
    switch(builder)
    {
      case Ascending:
      case Descending:
      case Saw:
      case Repeated:
      case Duplicated:
      case OrganPipes:
      case Stagger:
      case Plateau:
        return m*2;
      default:
        return m+1;
    }
  }
  private static int getMLo(JunitUtil.floatArrayBuilder builder)
  {
    switch(builder)
    {
      case AllEquals:
        return 0;
      case MergeAscending:
      case MergeDescending:
        return 65;
      default:
        return 1;
    }
  }
  private static int getMHi(JunitUtil.floatArrayBuilder builder,int arrLength)
  {
    switch(builder)
    {
      case Ascending:
      case Descending:
      case Saw:
      case Repeated:
      case Duplicated:
      case OrganPipes:
      case Stagger:
      case Plateau:
        return (2*arrLength)-1;
      case AllEquals:
        return 0;
      case MergeAscending:
      case MergeDescending:
        return 69;
      case SortedRepeated:
      case SortedOrganPipes:
        return Math.min(arrLength,7);
      default:
        return 1;
    }
  }
  private static int getNumReps(JunitUtil.floatArrayBuilder builder,int arrLength)
  {
    if(builder==JunitUtil.floatArrayBuilder.WithNaNsAndZeros)
    {
      return 2;
    }
    return 1;
  }
  private static int incrementM(JunitUtil.floatArrayBuilder builder,int m)
  {
    switch(builder)
    {
      case Ascending:
      case Descending:
      case Saw:
      case Repeated:
      case Duplicated:
      case OrganPipes:
      case Stagger:
      case Plateau:
        return m*2;
      default:
        return m+1;
    }
  }
  private static int getMLo(JunitUtil.doubleArrayBuilder builder)
  {
    switch(builder)
    {
      case AllEquals:
        return 0;
      case MergeAscending:
      case MergeDescending:
        return 65;
      default:
        return 1;
    }
  }
  private static int getMHi(JunitUtil.doubleArrayBuilder builder,int arrLength)
  {
    switch(builder)
    {
      case Ascending:
      case Descending:
      case Saw:
      case Repeated:
      case Duplicated:
      case OrganPipes:
      case Stagger:
      case Plateau:
        return (2*arrLength)-1;
      case AllEquals:
        return 0;
      case MergeAscending:
      case MergeDescending:
        return 69;
      case SortedRepeated:
      case SortedOrganPipes:
        return Math.min(arrLength,7);
      default:
        return 1;
    }
  }
  private static int getNumReps(JunitUtil.doubleArrayBuilder builder,int arrLength)
  {
    if(builder==JunitUtil.doubleArrayBuilder.WithNaNsAndZeros)
    {
      return 2;
    }
    return 1;
  }
  private static int incrementM(JunitUtil.doubleArrayBuilder builder,int m)
  {
    switch(builder)
    {
      case Ascending:
      case Descending:
      case Saw:
      case Repeated:
      case Duplicated:
      case OrganPipes:
      case Stagger:
      case Plateau:
        return m*2;
      default:
        return m+1;
    }
  }
  private static int getMLo(JunitUtil.StringArrayBuilder builder)
  {
    switch(builder)
    {
      case AllEquals:
        return 0;
      case MergeAscending:
      case MergeDescending:
        return 65;
      default:
        return 1;
    }
  }
  private static int getMHi(JunitUtil.StringArrayBuilder builder,int arrLength)
  {
    switch(builder)
    {
      case Ascending:
      case Descending:
      case Saw:
      case Repeated:
      case Duplicated:
      case OrganPipes:
      case Stagger:
      case Plateau:
        return (2*arrLength)-1;
      case AllEquals:
        return 0;
      case MergeAscending:
      case MergeDescending:
        return 69;
      case SortedRepeated:
      case SortedOrganPipes:
        return Math.min(arrLength,7);
      default:
        return 1;
    }
  }
  private static int getNumReps(JunitUtil.StringArrayBuilder builder,int arrLength)
  {
    return 1;
  }
  private static int incrementM(JunitUtil.StringArrayBuilder builder,int m)
  {
    switch(builder)
    {
      case Ascending:
      case Descending:
      case Saw:
      case Repeated:
      case Duplicated:
      case OrganPipes:
      case Stagger:
      case Plateau:
        return m*2;
      default:
        return m+1;
    }
  }
  private static void testuncheckedsortbooleanHelper(long randSeed,int arrLength)
  {
    boolean[] golden=new boolean[arrLength];
    boolean[] test=new boolean[arrLength];
    Random rand=new Random(randSeed);
    for(JunitUtil.booleanArrayBuilder builder:JunitUtil.booleanArrayBuilder.values())
    {
      for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
      {
        //System.out.println("sortType = sort; arrType = boolean; randSeed= "+randSeed+"; length= "+arrLength+"; builder type "+builder+"; m = "+m);
        for(int i=0;i<numReps;++i)
        {
          builder.build(golden,rand,m);
          ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
          isSortedsort(test,golden);
        }
      }
    }
  }
  @Test
  public void testuncheckedsortboolean()
  {
    testuncheckedsortbooleanHelper(0,30);
  }
  private static void testuncheckedreverseSortbooleanHelper(long randSeed,int arrLength)
  {
    boolean[] golden=new boolean[arrLength];
    boolean[] test=new boolean[arrLength];
    Random rand=new Random(randSeed);
    for(JunitUtil.booleanArrayBuilder builder:JunitUtil.booleanArrayBuilder.values())
    {
      for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
      {
        //System.out.println("sortType = reverseSort; arrType = boolean; randSeed= "+randSeed+"; length= "+arrLength+"; builder type "+builder+"; m = "+m);
        for(int i=0;i<numReps;++i)
        {
          builder.build(golden,rand,m);
          ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
          isSortedreverseSort(test,golden);
        }
      }
    }
  }
  @Test
  public void testuncheckedreverseSortboolean()
  {
    testuncheckedreverseSortbooleanHelper(0,30);
  }
  private static void testuncheckedsortbyteHelper(long randSeed,int arrLength)
  {
    byte[] golden=new byte[arrLength];
    byte[] test=new byte[arrLength];
    Random rand=new Random(randSeed);
    for(JunitUtil.byteArrayBuilder builder:JunitUtil.byteArrayBuilder.values())
    {
      for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
      {
        //System.out.println("sortType = sort; arrType = byte; randSeed= "+randSeed+"; length= "+arrLength+"; builder type "+builder+"; m = "+m);
        for(int i=0;i<numReps;++i)
        {
          builder.build(golden,rand,m);
          ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
          isSortedsort(test,golden);
        }
      }
    }
  }
  @Test
  public void testuncheckedsortbyte()
  {
    testuncheckedsortbyteHelper(0,30);
    testuncheckedsortbyteHelper(0,31);
  }
  private static void testuncheckedreverseSortbyteHelper(long randSeed,int arrLength)
  {
    byte[] golden=new byte[arrLength];
    byte[] test=new byte[arrLength];
    Random rand=new Random(randSeed);
    for(JunitUtil.byteArrayBuilder builder:JunitUtil.byteArrayBuilder.values())
    {
      for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
      {
        //System.out.println("sortType = reverseSort; arrType = byte; randSeed= "+randSeed+"; length= "+arrLength+"; builder type "+builder+"; m = "+m);
        for(int i=0;i<numReps;++i)
        {
          builder.build(golden,rand,m);
          ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
          isSortedreverseSort(test,golden);
        }
      }
    }
  }
  @Test
  public void testuncheckedreverseSortbyte()
  {
    testuncheckedreverseSortbyteHelper(0,30);
    testuncheckedreverseSortbyteHelper(0,31);
  }
  private static void testuncheckedsortcharHelper(long randSeed,int arrLength)
  {
    char[] golden=new char[arrLength];
    char[] test=new char[arrLength];
    Random rand=new Random(randSeed);
    for(JunitUtil.charArrayBuilder builder:JunitUtil.charArrayBuilder.values())
    {
      for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
      {
        //System.out.println("sortType = sort; arrType = char; randSeed= "+randSeed+"; length= "+arrLength+"; builder type "+builder+"; m = "+m);
        for(int i=0;i<numReps;++i)
        {
          builder.build(golden,rand,m);
          ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
          isSortedsort(test,golden);
        }
      }
    }
  }
  @Test
  public void testuncheckedsortchar()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testuncheckedsortchar length = "+arrLength);
      var stream=LongStream.of(randSeeds);
      //if(true)
      {
        stream=stream.parallel();
      }
      stream.forEach(randSeed->
      {
        testuncheckedsortcharHelper(randSeed,arrLength);
      });
    }
  }
  private static void testuncheckedreverseSortcharHelper(long randSeed,int arrLength)
  {
    char[] golden=new char[arrLength];
    char[] test=new char[arrLength];
    Random rand=new Random(randSeed);
    for(JunitUtil.charArrayBuilder builder:JunitUtil.charArrayBuilder.values())
    {
      for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
      {
        //System.out.println("sortType = reverseSort; arrType = char; randSeed= "+randSeed+"; length= "+arrLength+"; builder type "+builder+"; m = "+m);
        for(int i=0;i<numReps;++i)
        {
          builder.build(golden,rand,m);
          ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
          isSortedreverseSort(test,golden);
        }
      }
    }
  }
  @Test
  public void testuncheckedreverseSortchar()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testuncheckedreverseSortchar length = "+arrLength);
      var stream=LongStream.of(randSeeds);
      //if(true)
      {
        stream=stream.parallel();
      }
      stream.forEach(randSeed->
      {
        testuncheckedreverseSortcharHelper(randSeed,arrLength);
      });
    }
  }
  private static void testuncheckedsortshortHelper(long randSeed,int arrLength)
  {
    short[] golden=new short[arrLength];
    short[] test=new short[arrLength];
    Random rand=new Random(randSeed);
    for(JunitUtil.shortArrayBuilder builder:JunitUtil.shortArrayBuilder.values())
    {
      for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
      {
        //System.out.println("sortType = sort; arrType = short; randSeed= "+randSeed+"; length= "+arrLength+"; builder type "+builder+"; m = "+m);
        for(int i=0;i<numReps;++i)
        {
          builder.build(golden,rand,m);
          ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
          isSortedsort(test,golden);
        }
      }
    }
  }
  @Test
  public void testuncheckedsortshort()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testuncheckedsortshort length = "+arrLength);
      var stream=LongStream.of(randSeeds);
      //if(true)
      {
        stream=stream.parallel();
      }
      stream.forEach(randSeed->
      {
        testuncheckedsortshortHelper(randSeed,arrLength);
      });
    }
  }
  private static void testuncheckedreverseSortshortHelper(long randSeed,int arrLength)
  {
    short[] golden=new short[arrLength];
    short[] test=new short[arrLength];
    Random rand=new Random(randSeed);
    for(JunitUtil.shortArrayBuilder builder:JunitUtil.shortArrayBuilder.values())
    {
      for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
      {
        //System.out.println("sortType = reverseSort; arrType = short; randSeed= "+randSeed+"; length= "+arrLength+"; builder type "+builder+"; m = "+m);
        for(int i=0;i<numReps;++i)
        {
          builder.build(golden,rand,m);
          ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
          isSortedreverseSort(test,golden);
        }
      }
    }
  }
  @Test
  public void testuncheckedreverseSortshort()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testuncheckedreverseSortshort length = "+arrLength);
      var stream=LongStream.of(randSeeds);
      //if(true)
      {
        stream=stream.parallel();
      }
      stream.forEach(randSeed->
      {
        testuncheckedreverseSortshortHelper(randSeed,arrLength);
      });
    }
  }
  private static void testuncheckedsortintHelper(long randSeed,int arrLength)
  {
    int[] golden=new int[arrLength];
    int[] test=new int[arrLength];
    Random rand=new Random(randSeed);
    for(JunitUtil.intArrayBuilder builder:JunitUtil.intArrayBuilder.values())
    {
      for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
      {
        //System.out.println("sortType = sort; arrType = int; randSeed= "+randSeed+"; length= "+arrLength+"; builder type "+builder+"; m = "+m);
        for(int i=0;i<numReps;++i)
        {
          builder.build(golden,rand,m);
          ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
          isSortedsort(test,golden);
        }
      }
    }
  }
  @Test
  public void testuncheckedsortint()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testuncheckedsortint length = "+arrLength);
      var stream=LongStream.of(randSeeds);
      //if(true)
      {
        stream=stream.parallel();
      }
      stream.forEach(randSeed->
      {
        testuncheckedsortintHelper(randSeed,arrLength);
      });
    }
  }
  private static void testuncheckedreverseSortintHelper(long randSeed,int arrLength)
  {
    int[] golden=new int[arrLength];
    int[] test=new int[arrLength];
    Random rand=new Random(randSeed);
    for(JunitUtil.intArrayBuilder builder:JunitUtil.intArrayBuilder.values())
    {
      for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
      {
        //System.out.println("sortType = reverseSort; arrType = int; randSeed= "+randSeed+"; length= "+arrLength+"; builder type "+builder+"; m = "+m);
        for(int i=0;i<numReps;++i)
        {
          builder.build(golden,rand,m);
          ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
          isSortedreverseSort(test,golden);
        }
      }
    }
  }
  @Test
  public void testuncheckedreverseSortint()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testuncheckedreverseSortint length = "+arrLength);
      var stream=LongStream.of(randSeeds);
      //if(true)
      {
        stream=stream.parallel();
      }
      stream.forEach(randSeed->
      {
        testuncheckedreverseSortintHelper(randSeed,arrLength);
      });
    }
  }
  private static void testuncheckedsortlongHelper(long randSeed,int arrLength)
  {
    long[] golden=new long[arrLength];
    long[] test=new long[arrLength];
    Random rand=new Random(randSeed);
    for(JunitUtil.longArrayBuilder builder:JunitUtil.longArrayBuilder.values())
    {
      for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
      {
        //System.out.println("sortType = sort; arrType = long; randSeed= "+randSeed+"; length= "+arrLength+"; builder type "+builder+"; m = "+m);
        for(int i=0;i<numReps;++i)
        {
          builder.build(golden,rand,m);
          ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
          isSortedsort(test,golden);
        }
      }
    }
  }
  @Test
  public void testuncheckedsortlong()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testuncheckedsortlong length = "+arrLength);
      var stream=LongStream.of(randSeeds);
      //if(true)
      {
        stream=stream.parallel();
      }
      stream.forEach(randSeed->
      {
        testuncheckedsortlongHelper(randSeed,arrLength);
      });
    }
  }
  private static void testuncheckedreverseSortlongHelper(long randSeed,int arrLength)
  {
    long[] golden=new long[arrLength];
    long[] test=new long[arrLength];
    Random rand=new Random(randSeed);
    for(JunitUtil.longArrayBuilder builder:JunitUtil.longArrayBuilder.values())
    {
      for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
      {
        //System.out.println("sortType = reverseSort; arrType = long; randSeed= "+randSeed+"; length= "+arrLength+"; builder type "+builder+"; m = "+m);
        for(int i=0;i<numReps;++i)
        {
          builder.build(golden,rand,m);
          ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
          isSortedreverseSort(test,golden);
        }
      }
    }
  }
  @Test
  public void testuncheckedreverseSortlong()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testuncheckedreverseSortlong length = "+arrLength);
      var stream=LongStream.of(randSeeds);
      //if(true)
      {
        stream=stream.parallel();
      }
      stream.forEach(randSeed->
      {
        testuncheckedreverseSortlongHelper(randSeed,arrLength);
      });
    }
  }
  private static void testuncheckedsortfloatHelper(long randSeed,int arrLength)
  {
    float[] golden=new float[arrLength];
    float[] test=new float[arrLength];
    Random rand=new Random(randSeed);
    for(JunitUtil.floatArrayBuilder builder:JunitUtil.floatArrayBuilder.values())
    {
      for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
      {
        //System.out.println("sortType = sort; arrType = float; randSeed= "+randSeed+"; length= "+arrLength+"; builder type "+builder+"; m = "+m);
        for(int i=0;i<numReps;++i)
        {
          builder.build(golden,rand,m);
          ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
          isSortedsort(test,golden);
        }
      }
    }
  }
  @Test
  public void testuncheckedsortfloat()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testuncheckedsortfloat length = "+arrLength);
      var stream=LongStream.of(randSeeds);
      //if(true)
      {
        stream=stream.parallel();
      }
      stream.forEach(randSeed->
      {
        testuncheckedsortfloatHelper(randSeed,arrLength);
      });
    }
  }
  private static void testuncheckedreverseSortfloatHelper(long randSeed,int arrLength)
  {
    float[] golden=new float[arrLength];
    float[] test=new float[arrLength];
    Random rand=new Random(randSeed);
    for(JunitUtil.floatArrayBuilder builder:JunitUtil.floatArrayBuilder.values())
    {
      for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
      {
        //System.out.println("sortType = reverseSort; arrType = float; randSeed= "+randSeed+"; length= "+arrLength+"; builder type "+builder+"; m = "+m);
        for(int i=0;i<numReps;++i)
        {
          builder.build(golden,rand,m);
          ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
          isSortedreverseSort(test,golden);
        }
      }
    }
  }
  @Test
  public void testuncheckedreverseSortfloat()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testuncheckedreverseSortfloat length = "+arrLength);
      var stream=LongStream.of(randSeeds);
      //if(true)
      {
        stream=stream.parallel();
      }
      stream.forEach(randSeed->
      {
        testuncheckedreverseSortfloatHelper(randSeed,arrLength);
      });
    }
  }
  private static void testuncheckedsortdoubleHelper(long randSeed,int arrLength)
  {
    double[] golden=new double[arrLength];
    double[] test=new double[arrLength];
    Random rand=new Random(randSeed);
    for(JunitUtil.doubleArrayBuilder builder:JunitUtil.doubleArrayBuilder.values())
    {
      for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
      {
        //System.out.println("sortType = sort; arrType = double; randSeed= "+randSeed+"; length= "+arrLength+"; builder type "+builder+"; m = "+m);
        for(int i=0;i<numReps;++i)
        {
          builder.build(golden,rand,m);
          ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
          isSortedsort(test,golden);
        }
      }
    }
  }
  @Test
  public void testuncheckedsortdouble()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testuncheckedsortdouble length = "+arrLength);
      var stream=LongStream.of(randSeeds);
      //if(true)
      {
        stream=stream.parallel();
      }
      stream.forEach(randSeed->
      {
        testuncheckedsortdoubleHelper(randSeed,arrLength);
      });
    }
  }
  private static void testuncheckedreverseSortdoubleHelper(long randSeed,int arrLength)
  {
    double[] golden=new double[arrLength];
    double[] test=new double[arrLength];
    Random rand=new Random(randSeed);
    for(JunitUtil.doubleArrayBuilder builder:JunitUtil.doubleArrayBuilder.values())
    {
      for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
      {
        //System.out.println("sortType = reverseSort; arrType = double; randSeed= "+randSeed+"; length= "+arrLength+"; builder type "+builder+"; m = "+m);
        for(int i=0;i<numReps;++i)
        {
          builder.build(golden,rand,m);
          ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
          isSortedreverseSort(test,golden);
        }
      }
    }
  }
  @Test
  public void testuncheckedreverseSortdouble()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testuncheckedreverseSortdouble length = "+arrLength);
      var stream=LongStream.of(randSeeds);
      //if(true)
      {
        stream=stream.parallel();
      }
      stream.forEach(randSeed->
      {
        testuncheckedreverseSortdoubleHelper(randSeed,arrLength);
      });
    }
  }
}
