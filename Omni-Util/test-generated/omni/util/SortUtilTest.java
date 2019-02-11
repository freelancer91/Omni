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
  private static final int[] lengths=new int[]{34};
  //private static final int[] lengths=new int[]{2,3,5,8,13,21};
  //private static final int[] lengths=new int[]{2, 3, 5, 8, 13, 21, 34, 55, 100, 1000, 10000, 100000, 1000000};
  private static final long[] randSeeds=new long[]{666L,0xC0FFEEL,999L};
  private static Comparator<Integer> getAscendingIntegerComparator()
  {
    return (val1,val2)->
    {
      return
        (
      Integer.compare(val1,val2)
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
  private static Comparator<Integer> getUnsortedIntegerComparator()
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
  private static Comparator<Integer> getDescendingIntegerComparator()
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
  private static void isSortedAscending(Integer[] arr,Integer[] copy)
  {
    Thread stockSorterThread=new Thread(()->
    {
    Arrays.sort(copy,0,copy.length);
    });
    stockSorterThread.start();
    SortUtil.sortObjectTimSort.uncheckedsort(arr,0,arr.length);
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
  private static void isSortedAscending(boolean[] arr,boolean[] copy)
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
  private static void isSortedAscending(byte[] arr,byte[] copy)
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
  private static void isSortedAscending(char[] arr,char[] copy)
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
  private static void isSortedAscending(short[] arr,short[] copy)
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
  private static void isSortedAscending(int[] arr,int[] copy)
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
  private static void isSortedAscending(long[] arr,long[] copy)
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
  private static void isSortedAscending(float[] arr,float[] copy)
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
  private static void isSortedAscending(double[] arr,double[] copy)
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
  private static void isSortedDescending(Integer[] arr,Integer[] copy)
  {
    Thread stockSorterThread=new Thread(()->
    {
    Arrays.sort(copy,0,copy.length);
    OmniArray.OfRef.reverseRange(copy,0,copy.length-1);
    });
    stockSorterThread.start();
    SortUtil.reverseSortObjectTimSort.uncheckedsort(arr,0,arr.length);
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
  private static void isSortedDescending(boolean[] arr,boolean[] copy)
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
  private static void isSortedDescending(byte[] arr,byte[] copy)
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
  private static void isSortedDescending(char[] arr,char[] copy)
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
  private static void isSortedDescending(short[] arr,short[] copy)
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
  private static void isSortedDescending(int[] arr,int[] copy)
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
  private static void isSortedDescending(long[] arr,long[] copy)
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
  private static void isSortedDescending(float[] arr,float[] copy)
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
  private static void isSortedDescending(double[] arr,double[] copy)
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
  private static void isSorted(Integer[] arr,Integer[] copy,Comparator<? super Integer> sorter)
  {
    Thread stockSorterThread=new Thread(()->
    {
    Arrays.sort(copy,0,copy.length,sorter);
    });
    stockSorterThread.start();
    SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arr.length,sorter);
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
    Arrays.sort(boxedCopy,0,copy.length,sorter::compare);
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
    Arrays.sort(boxedCopy,0,copy.length,sorter::compare);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    });
    stockSorterThread.start();
    SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arr.length,sorter);
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
    Arrays.sort(boxedCopy,0,copy.length,sorter::compare);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    });
    stockSorterThread.start();
    SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arr.length,sorter);
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
    Arrays.sort(boxedCopy,0,copy.length,sorter::compare);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    });
    stockSorterThread.start();
    SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arr.length,sorter);
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
    Arrays.sort(boxedCopy,0,copy.length,sorter::applyAsInt);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    });
    stockSorterThread.start();
    SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arr.length,sorter);
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
    Arrays.sort(boxedCopy,0,copy.length,sorter::compare);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    });
    stockSorterThread.start();
    SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arr.length,sorter);
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
    Arrays.sort(boxedCopy,0,copy.length,sorter::compare);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    });
    stockSorterThread.start();
    SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arr.length,sorter);
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
    Arrays.sort(boxedCopy,0,copy.length,sorter::compare);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    });
    stockSorterThread.start();
    SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arr.length,sorter);
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
  private static int getMLo(JunitUtil.IntegerArrayBuilder builder)
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
  private static int getMHi(JunitUtil.IntegerArrayBuilder builder,int arrLength)
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
  private static int getNumReps(JunitUtil.IntegerArrayBuilder builder,int arrLength)
  {
    return 1;
  }
  private static int incrementM(JunitUtil.IntegerArrayBuilder builder,int m)
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
  //#MACRO TestMethods<boolean,comparatorSort,Ascending>()
  //#MACRO TestMethods<boolean,comparatorSort,Descending>()
/*
  private static void testbooleanAscendingnonComparatorSortHelper(JunitUtil.booleanArrayBuilder builder,int arrLength,long randSeed)
  {
    boolean[] golden=new boolean[arrLength];
    boolean[] test=new boolean[arrLength];
    Random rand=new Random(randSeed);
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSortedAscending(test,golden);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  private static void testbyteAscendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder builder,int arrLength,long randSeed)
  {
    byte[] golden=new byte[arrLength];
    byte[] test=new byte[arrLength];
    Random rand=new Random(randSeed);
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSortedAscending(test,golden);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testbyteAscendingnonComparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteAscendingnonComparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Duplicated.isRandomized())
     {
         testbyteAscendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteAscendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
  private static void testcharAscendingnonComparatorSortHelper(JunitUtil.charArrayBuilder builder,int arrLength,long randSeed)
  {
    char[] golden=new char[arrLength];
    char[] test=new char[arrLength];
    Random rand=new Random(randSeed);
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSortedAscending(test,golden);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testcharAscendingnonComparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharAscendingnonComparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Duplicated.isRandomized())
     {
         testcharAscendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharAscendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
  private static void testshortAscendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder builder,int arrLength,long randSeed)
  {
    short[] golden=new short[arrLength];
    short[] test=new short[arrLength];
    Random rand=new Random(randSeed);
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSortedAscending(test,golden);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testshortAscendingnonComparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortAscendingnonComparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Duplicated.isRandomized())
     {
         testshortAscendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortAscendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
  private static void testintAscendingnonComparatorSortHelper(JunitUtil.intArrayBuilder builder,int arrLength,long randSeed)
  {
    int[] golden=new int[arrLength];
    int[] test=new int[arrLength];
    Random rand=new Random(randSeed);
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSortedAscending(test,golden);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testintAscendingnonComparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintAscendingnonComparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Duplicated.isRandomized())
     {
         testintAscendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintAscendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
  private static void testlongAscendingnonComparatorSortHelper(JunitUtil.longArrayBuilder builder,int arrLength,long randSeed)
  {
    long[] golden=new long[arrLength];
    long[] test=new long[arrLength];
    Random rand=new Random(randSeed);
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSortedAscending(test,golden);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testlongAscendingnonComparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongAscendingnonComparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Duplicated.isRandomized())
     {
         testlongAscendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongAscendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
  private static void testfloatAscendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder builder,int arrLength,long randSeed)
  {
    float[] golden=new float[arrLength];
    float[] test=new float[arrLength];
    Random rand=new Random(randSeed);
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSortedAscending(test,golden);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testfloatAscendingnonComparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatAscendingnonComparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Duplicated.isRandomized())
     {
         testfloatAscendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatAscendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
  @Test
  public void testfloatAscendingnonComparatorSortWithNaNsAndZerosArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatAscendingnonComparatorSortWithNaNsAndZerosArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.WithNaNsAndZeros.isRandomized())
     {
         testfloatAscendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.WithNaNsAndZeros,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatAscendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.WithNaNsAndZeros,arrLength,randSeed);
       });
     }
    }
  }
  private static void testdoubleAscendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder builder,int arrLength,long randSeed)
  {
    double[] golden=new double[arrLength];
    double[] test=new double[arrLength];
    Random rand=new Random(randSeed);
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSortedAscending(test,golden);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testdoubleAscendingnonComparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleAscendingnonComparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Duplicated.isRandomized())
     {
         testdoubleAscendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleAscendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
  @Test
  public void testdoubleAscendingnonComparatorSortWithNaNsAndZerosArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleAscendingnonComparatorSortWithNaNsAndZerosArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.WithNaNsAndZeros.isRandomized())
     {
         testdoubleAscendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.WithNaNsAndZeros,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleAscendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.WithNaNsAndZeros,arrLength,randSeed);
       });
     }
    }
  }
*/
/*
  private static void testbooleanDescendingnonComparatorSortHelper(JunitUtil.booleanArrayBuilder builder,int arrLength,long randSeed)
  {
    boolean[] golden=new boolean[arrLength];
    boolean[] test=new boolean[arrLength];
    Random rand=new Random(randSeed);
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSortedDescending(test,golden);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  private static void testcharDescendingnonComparatorSortHelper(JunitUtil.charArrayBuilder builder,int arrLength,long randSeed)
  {
    char[] golden=new char[arrLength];
    char[] test=new char[arrLength];
    Random rand=new Random(randSeed);
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSortedDescending(test,golden);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testcharDescendingnonComparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharDescendingnonComparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Duplicated.isRandomized())
     {
         testcharDescendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharDescendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
  private static void testshortDescendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder builder,int arrLength,long randSeed)
  {
    short[] golden=new short[arrLength];
    short[] test=new short[arrLength];
    Random rand=new Random(randSeed);
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSortedDescending(test,golden);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testshortDescendingnonComparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortDescendingnonComparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Duplicated.isRandomized())
     {
         testshortDescendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortDescendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
  private static void testintDescendingnonComparatorSortHelper(JunitUtil.intArrayBuilder builder,int arrLength,long randSeed)
  {
    int[] golden=new int[arrLength];
    int[] test=new int[arrLength];
    Random rand=new Random(randSeed);
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSortedDescending(test,golden);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testintDescendingnonComparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintDescendingnonComparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Duplicated.isRandomized())
     {
         testintDescendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintDescendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
  private static void testlongDescendingnonComparatorSortHelper(JunitUtil.longArrayBuilder builder,int arrLength,long randSeed)
  {
    long[] golden=new long[arrLength];
    long[] test=new long[arrLength];
    Random rand=new Random(randSeed);
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSortedDescending(test,golden);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testlongDescendingnonComparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongDescendingnonComparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Duplicated.isRandomized())
     {
         testlongDescendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongDescendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
  private static void testfloatDescendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder builder,int arrLength,long randSeed)
  {
    float[] golden=new float[arrLength];
    float[] test=new float[arrLength];
    Random rand=new Random(randSeed);
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSortedDescending(test,golden);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testfloatDescendingnonComparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatDescendingnonComparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Duplicated.isRandomized())
     {
         testfloatDescendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatDescendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
  @Test
  public void testfloatDescendingnonComparatorSortWithNaNsAndZerosArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatDescendingnonComparatorSortWithNaNsAndZerosArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.WithNaNsAndZeros.isRandomized())
     {
         testfloatDescendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.WithNaNsAndZeros,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatDescendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.WithNaNsAndZeros,arrLength,randSeed);
       });
     }
    }
  }
  private static void testdoubleDescendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder builder,int arrLength,long randSeed)
  {
    double[] golden=new double[arrLength];
    double[] test=new double[arrLength];
    Random rand=new Random(randSeed);
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSortedDescending(test,golden);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testdoubleDescendingnonComparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleDescendingnonComparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Duplicated.isRandomized())
     {
         testdoubleDescendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleDescendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
  @Test
  public void testdoubleDescendingnonComparatorSortWithNaNsAndZerosArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleDescendingnonComparatorSortWithNaNsAndZerosArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.WithNaNsAndZeros.isRandomized())
     {
         testdoubleDescendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.WithNaNsAndZeros,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleDescendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.WithNaNsAndZeros,arrLength,randSeed);
       });
     }
    }
  }
*/
/*
  private static void testbooleanUnsortedcomparatorSortHelper(JunitUtil.booleanArrayBuilder builder,int arrLength,long randSeed)
  {
    boolean[] golden=new boolean[arrLength];
    boolean[] test=new boolean[arrLength];
    Random rand=new Random(randSeed);
    var sorter=getUnsortedbooleanComparator();
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSorted(test,golden,sorter);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  private static void testbyteUnsortedcomparatorSortHelper(JunitUtil.byteArrayBuilder builder,int arrLength,long randSeed)
  {
    byte[] golden=new byte[arrLength];
    byte[] test=new byte[arrLength];
    Random rand=new Random(randSeed);
    var sorter=getUnsortedbyteComparator();
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSorted(test,golden,sorter);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testbyteUnsortedcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteUnsortedcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Duplicated.isRandomized())
     {
         testbyteUnsortedcomparatorSortHelper(JunitUtil.byteArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteUnsortedcomparatorSortHelper(JunitUtil.byteArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
  private static void testcharUnsortedcomparatorSortHelper(JunitUtil.charArrayBuilder builder,int arrLength,long randSeed)
  {
    char[] golden=new char[arrLength];
    char[] test=new char[arrLength];
    Random rand=new Random(randSeed);
    var sorter=getUnsortedcharComparator();
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSorted(test,golden,sorter);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testcharUnsortedcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharUnsortedcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Duplicated.isRandomized())
     {
         testcharUnsortedcomparatorSortHelper(JunitUtil.charArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharUnsortedcomparatorSortHelper(JunitUtil.charArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
  private static void testshortUnsortedcomparatorSortHelper(JunitUtil.shortArrayBuilder builder,int arrLength,long randSeed)
  {
    short[] golden=new short[arrLength];
    short[] test=new short[arrLength];
    Random rand=new Random(randSeed);
    var sorter=getUnsortedshortComparator();
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSorted(test,golden,sorter);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testshortUnsortedcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortUnsortedcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Duplicated.isRandomized())
     {
         testshortUnsortedcomparatorSortHelper(JunitUtil.shortArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortUnsortedcomparatorSortHelper(JunitUtil.shortArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
  private static void testintUnsortedcomparatorSortHelper(JunitUtil.intArrayBuilder builder,int arrLength,long randSeed)
  {
    int[] golden=new int[arrLength];
    int[] test=new int[arrLength];
    Random rand=new Random(randSeed);
    var sorter=getUnsortedintComparator();
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSorted(test,golden,sorter);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testintUnsortedcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintUnsortedcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Duplicated.isRandomized())
     {
         testintUnsortedcomparatorSortHelper(JunitUtil.intArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintUnsortedcomparatorSortHelper(JunitUtil.intArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
  private static void testlongUnsortedcomparatorSortHelper(JunitUtil.longArrayBuilder builder,int arrLength,long randSeed)
  {
    long[] golden=new long[arrLength];
    long[] test=new long[arrLength];
    Random rand=new Random(randSeed);
    var sorter=getUnsortedlongComparator();
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSorted(test,golden,sorter);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testlongUnsortedcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongUnsortedcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Duplicated.isRandomized())
     {
         testlongUnsortedcomparatorSortHelper(JunitUtil.longArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongUnsortedcomparatorSortHelper(JunitUtil.longArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
  private static void testfloatUnsortedcomparatorSortHelper(JunitUtil.floatArrayBuilder builder,int arrLength,long randSeed)
  {
    float[] golden=new float[arrLength];
    float[] test=new float[arrLength];
    Random rand=new Random(randSeed);
    var sorter=getUnsortedfloatComparator();
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSorted(test,golden,sorter);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testfloatUnsortedcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatUnsortedcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Duplicated.isRandomized())
     {
         testfloatUnsortedcomparatorSortHelper(JunitUtil.floatArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatUnsortedcomparatorSortHelper(JunitUtil.floatArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
  @Test
  public void testfloatUnsortedcomparatorSortWithNaNsAndZerosArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatUnsortedcomparatorSortWithNaNsAndZerosArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.WithNaNsAndZeros.isRandomized())
     {
         testfloatUnsortedcomparatorSortHelper(JunitUtil.floatArrayBuilder.WithNaNsAndZeros,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatUnsortedcomparatorSortHelper(JunitUtil.floatArrayBuilder.WithNaNsAndZeros,arrLength,randSeed);
       });
     }
    }
  }
  private static void testdoubleUnsortedcomparatorSortHelper(JunitUtil.doubleArrayBuilder builder,int arrLength,long randSeed)
  {
    double[] golden=new double[arrLength];
    double[] test=new double[arrLength];
    Random rand=new Random(randSeed);
    var sorter=getUnsorteddoubleComparator();
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSorted(test,golden,sorter);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testdoubleUnsortedcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleUnsortedcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Duplicated.isRandomized())
     {
         testdoubleUnsortedcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleUnsortedcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
  @Test
  public void testdoubleUnsortedcomparatorSortWithNaNsAndZerosArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleUnsortedcomparatorSortWithNaNsAndZerosArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.WithNaNsAndZeros.isRandomized())
     {
         testdoubleUnsortedcomparatorSortHelper(JunitUtil.doubleArrayBuilder.WithNaNsAndZeros,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleUnsortedcomparatorSortHelper(JunitUtil.doubleArrayBuilder.WithNaNsAndZeros,arrLength,randSeed);
       });
     }
    }
  }
  private static void testIntegerUnsortedcomparatorSortHelper(JunitUtil.IntegerArrayBuilder builder,int arrLength,long randSeed)
  {
    Integer[] golden=new Integer[arrLength];
    Integer[] test=new Integer[arrLength];
    Random rand=new Random(randSeed);
    var sorter=getUnsortedIntegerComparator();
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSorted(test,golden,sorter);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testIntegerUnsortedcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerUnsortedcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Duplicated.isRandomized())
     {
         testIntegerUnsortedcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerUnsortedcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
*/
  private static void testbyteAscendingcomparatorSortHelper(JunitUtil.byteArrayBuilder builder,int arrLength,long randSeed)
  {
    byte[] golden=new byte[arrLength];
    byte[] test=new byte[arrLength];
    Random rand=new Random(randSeed);
    var sorter=getAscendingbyteComparator();
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSorted(test,golden,sorter);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testbyteAscendingcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteAscendingcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Duplicated.isRandomized())
     {
         testbyteAscendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteAscendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
/*
  private static void testcharAscendingcomparatorSortHelper(JunitUtil.charArrayBuilder builder,int arrLength,long randSeed)
  {
    char[] golden=new char[arrLength];
    char[] test=new char[arrLength];
    Random rand=new Random(randSeed);
    var sorter=getAscendingcharComparator();
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSorted(test,golden,sorter);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testcharAscendingcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharAscendingcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Duplicated.isRandomized())
     {
         testcharAscendingcomparatorSortHelper(JunitUtil.charArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharAscendingcomparatorSortHelper(JunitUtil.charArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
  private static void testshortAscendingcomparatorSortHelper(JunitUtil.shortArrayBuilder builder,int arrLength,long randSeed)
  {
    short[] golden=new short[arrLength];
    short[] test=new short[arrLength];
    Random rand=new Random(randSeed);
    var sorter=getAscendingshortComparator();
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSorted(test,golden,sorter);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testshortAscendingcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortAscendingcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Duplicated.isRandomized())
     {
         testshortAscendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortAscendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
  private static void testintAscendingcomparatorSortHelper(JunitUtil.intArrayBuilder builder,int arrLength,long randSeed)
  {
    int[] golden=new int[arrLength];
    int[] test=new int[arrLength];
    Random rand=new Random(randSeed);
    var sorter=getAscendingintComparator();
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSorted(test,golden,sorter);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testintAscendingcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintAscendingcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Duplicated.isRandomized())
     {
         testintAscendingcomparatorSortHelper(JunitUtil.intArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintAscendingcomparatorSortHelper(JunitUtil.intArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
  private static void testlongAscendingcomparatorSortHelper(JunitUtil.longArrayBuilder builder,int arrLength,long randSeed)
  {
    long[] golden=new long[arrLength];
    long[] test=new long[arrLength];
    Random rand=new Random(randSeed);
    var sorter=getAscendinglongComparator();
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSorted(test,golden,sorter);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testlongAscendingcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongAscendingcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Duplicated.isRandomized())
     {
         testlongAscendingcomparatorSortHelper(JunitUtil.longArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongAscendingcomparatorSortHelper(JunitUtil.longArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
  private static void testfloatAscendingcomparatorSortHelper(JunitUtil.floatArrayBuilder builder,int arrLength,long randSeed)
  {
    float[] golden=new float[arrLength];
    float[] test=new float[arrLength];
    Random rand=new Random(randSeed);
    var sorter=getAscendingfloatComparator();
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSorted(test,golden,sorter);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testfloatAscendingcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatAscendingcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Duplicated.isRandomized())
     {
         testfloatAscendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatAscendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
  @Test
  public void testfloatAscendingcomparatorSortWithNaNsAndZerosArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatAscendingcomparatorSortWithNaNsAndZerosArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.WithNaNsAndZeros.isRandomized())
     {
         testfloatAscendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.WithNaNsAndZeros,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatAscendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.WithNaNsAndZeros,arrLength,randSeed);
       });
     }
    }
  }
  private static void testdoubleAscendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder builder,int arrLength,long randSeed)
  {
    double[] golden=new double[arrLength];
    double[] test=new double[arrLength];
    Random rand=new Random(randSeed);
    var sorter=getAscendingdoubleComparator();
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSorted(test,golden,sorter);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testdoubleAscendingcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleAscendingcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Duplicated.isRandomized())
     {
         testdoubleAscendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleAscendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
  @Test
  public void testdoubleAscendingcomparatorSortWithNaNsAndZerosArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleAscendingcomparatorSortWithNaNsAndZerosArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.WithNaNsAndZeros.isRandomized())
     {
         testdoubleAscendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.WithNaNsAndZeros,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleAscendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.WithNaNsAndZeros,arrLength,randSeed);
       });
     }
    }
  }
  private static void testIntegerAscendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder builder,int arrLength,long randSeed)
  {
    Integer[] golden=new Integer[arrLength];
    Integer[] test=new Integer[arrLength];
    Random rand=new Random(randSeed);
    var sorter=getAscendingIntegerComparator();
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSorted(test,golden,sorter);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testIntegerAscendingcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerAscendingcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Duplicated.isRandomized())
     {
         testIntegerAscendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerAscendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
*/
/*
  private static void testbyteDescendingcomparatorSortHelper(JunitUtil.byteArrayBuilder builder,int arrLength,long randSeed)
  {
    byte[] golden=new byte[arrLength];
    byte[] test=new byte[arrLength];
    Random rand=new Random(randSeed);
    var sorter=getDescendingbyteComparator();
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSorted(test,golden,sorter);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testbyteDescendingcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteDescendingcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Duplicated.isRandomized())
     {
         testbyteDescendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteDescendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
  private static void testcharDescendingcomparatorSortHelper(JunitUtil.charArrayBuilder builder,int arrLength,long randSeed)
  {
    char[] golden=new char[arrLength];
    char[] test=new char[arrLength];
    Random rand=new Random(randSeed);
    var sorter=getDescendingcharComparator();
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSorted(test,golden,sorter);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testcharDescendingcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharDescendingcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Duplicated.isRandomized())
     {
         testcharDescendingcomparatorSortHelper(JunitUtil.charArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharDescendingcomparatorSortHelper(JunitUtil.charArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
  private static void testshortDescendingcomparatorSortHelper(JunitUtil.shortArrayBuilder builder,int arrLength,long randSeed)
  {
    short[] golden=new short[arrLength];
    short[] test=new short[arrLength];
    Random rand=new Random(randSeed);
    var sorter=getDescendingshortComparator();
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSorted(test,golden,sorter);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testshortDescendingcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortDescendingcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Duplicated.isRandomized())
     {
         testshortDescendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortDescendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
  private static void testintDescendingcomparatorSortHelper(JunitUtil.intArrayBuilder builder,int arrLength,long randSeed)
  {
    int[] golden=new int[arrLength];
    int[] test=new int[arrLength];
    Random rand=new Random(randSeed);
    var sorter=getDescendingintComparator();
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSorted(test,golden,sorter);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testintDescendingcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintDescendingcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Duplicated.isRandomized())
     {
         testintDescendingcomparatorSortHelper(JunitUtil.intArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintDescendingcomparatorSortHelper(JunitUtil.intArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
  private static void testlongDescendingcomparatorSortHelper(JunitUtil.longArrayBuilder builder,int arrLength,long randSeed)
  {
    long[] golden=new long[arrLength];
    long[] test=new long[arrLength];
    Random rand=new Random(randSeed);
    var sorter=getDescendinglongComparator();
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSorted(test,golden,sorter);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testlongDescendingcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongDescendingcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Duplicated.isRandomized())
     {
         testlongDescendingcomparatorSortHelper(JunitUtil.longArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongDescendingcomparatorSortHelper(JunitUtil.longArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
  private static void testfloatDescendingcomparatorSortHelper(JunitUtil.floatArrayBuilder builder,int arrLength,long randSeed)
  {
    float[] golden=new float[arrLength];
    float[] test=new float[arrLength];
    Random rand=new Random(randSeed);
    var sorter=getDescendingfloatComparator();
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSorted(test,golden,sorter);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testfloatDescendingcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatDescendingcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Duplicated.isRandomized())
     {
         testfloatDescendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatDescendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
  @Test
  public void testfloatDescendingcomparatorSortWithNaNsAndZerosArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatDescendingcomparatorSortWithNaNsAndZerosArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.WithNaNsAndZeros.isRandomized())
     {
         testfloatDescendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.WithNaNsAndZeros,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatDescendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.WithNaNsAndZeros,arrLength,randSeed);
       });
     }
    }
  }
  private static void testdoubleDescendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder builder,int arrLength,long randSeed)
  {
    double[] golden=new double[arrLength];
    double[] test=new double[arrLength];
    Random rand=new Random(randSeed);
    var sorter=getDescendingdoubleComparator();
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSorted(test,golden,sorter);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testdoubleDescendingcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleDescendingcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Duplicated.isRandomized())
     {
         testdoubleDescendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleDescendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
  @Test
  public void testdoubleDescendingcomparatorSortWithNaNsAndZerosArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleDescendingcomparatorSortWithNaNsAndZerosArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.WithNaNsAndZeros.isRandomized())
     {
         testdoubleDescendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.WithNaNsAndZeros,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleDescendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.WithNaNsAndZeros,arrLength,randSeed);
       });
     }
    }
  }
  private static void testIntegerDescendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder builder,int arrLength,long randSeed)
  {
    Integer[] golden=new Integer[arrLength];
    Integer[] test=new Integer[arrLength];
    Random rand=new Random(randSeed);
    var sorter=getDescendingIntegerComparator();
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSorted(test,golden,sorter);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testIntegerDescendingcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerDescendingcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Duplicated.isRandomized())
     {
         testIntegerDescendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerDescendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
*/
/*
  private static void testIntegerAscendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder builder,int arrLength,long randSeed)
  {
    Integer[] golden=new Integer[arrLength];
    Integer[] test=new Integer[arrLength];
    Random rand=new Random(randSeed);
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSortedAscending(test,golden);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testIntegerAscendingnonComparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerAscendingnonComparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Duplicated.isRandomized())
     {
         testIntegerAscendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerAscendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
  private static void testIntegerDescendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder builder,int arrLength,long randSeed)
  {
    Integer[] golden=new Integer[arrLength];
    Integer[] test=new Integer[arrLength];
    Random rand=new Random(randSeed);
    for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
    {
      for(int i=0;i<numReps;++i)
      {
        builder.build(golden,rand,m);
        ArrCopy.uncheckedCopy(golden,0,test,0,arrLength);
        isSortedDescending(test,golden);
      }
    }
  }
  //#MACRO TestMethod<Randomized>()
  //#MACRO TestMethod<Ascending>()
  //#MACRO TestMethod<Descending>()
  //#MACRO TestMethod<AllEquals>()
  //#MACRO TestMethod<MergeAscending>()
  //#MACRO TestMethod<MergeDescending>()
  //#MACRO TestMethod<Saw>()
  //#MACRO TestMethod<SortedRepeated>()
  //#MACRO TestMethod<Repeated>()
  @Test
  public void testIntegerDescendingnonComparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerDescendingnonComparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Duplicated.isRandomized())
     {
         testIntegerDescendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.Duplicated,arrLength,0);
     }
     else
     {
       var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerDescendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
    }
  }
  //#MACRO TestMethod<SortedOrganPipes>()
  //#MACRO TestMethod<OrganPipes>()
  //#MACRO TestMethod<Stagger>()
  //#MACRO TestMethod<Plateau>()
  //#MACRO TestMethod<Shuffle>()
*/
}
