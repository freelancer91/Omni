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
  //private static final int[] lengths=new int[]{34};
  //private static final int[] lengths=new int[]{2,3,5,8,13,21};
  private static final int[] lengths=new int[]{2, 3, 5, 8, 13, 21, 34, 55, 100, 1000, 10000, 100000, 1000000,Integer.MAX_VALUE-8};
  private static final long[] randSeeds=new long[]{666L,0xC0FFEEL,999L};
  private static void isSortedAscending(Integer[] arr,Integer[] copy)
  {
    SortUtil.sortObjectTimSort.uncheckedsort(arr,0,arr.length);
    //Thread stockSorterThread=new Thread(()->
    //{
    Arrays.sort(copy,0,copy.length);
    //});
    //stockSorterThread.start();
    //try
    //{
    //  stockSorterThread.join();
    //}
    //catch(InterruptedException e)
    //{
    //  throw new AssertionError(e);
    //}
    JunitUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSortedAscending(String[] arr,String[] copy)
  {
    SortUtil.sortObjectTimSort.uncheckedsort(arr,0,arr.length);
    //Thread stockSorterThread=new Thread(()->
    //{
    Arrays.sort(copy,0,copy.length);
    //});
    //stockSorterThread.start();
    //try
    //{
    //  stockSorterThread.join();
    //}
    //catch(InterruptedException e)
    //{
    //  throw new AssertionError(e);
    //}
    JunitUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSortedAscending(boolean[] arr,boolean[] copy)
  {
    SortUtil.uncheckedsort(arr,0,arr.length-1);
    //Thread stockSorterThread=new Thread(()->
    //{
    Boolean[] boxedCopy=new Boolean[copy.length];
    ArrCopy.uncheckedCopy(copy,0,boxedCopy,0,copy.length);
    Arrays.sort(boxedCopy,0,copy.length);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    //});
    //stockSorterThread.start();
    //try
    //{
    //  stockSorterThread.join();
    //}
    //catch(InterruptedException e)
    //{
    //  throw new AssertionError(e);
    //}
    JunitUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSortedAscending(byte[] arr,byte[] copy)
  {
    SortUtil.uncheckedsort(arr,0,arr.length-1);
    //Thread stockSorterThread=new Thread(()->
    //{
    Arrays.sort(copy,0,copy.length);
    //});
    //stockSorterThread.start();
    //try
    //{
    //  stockSorterThread.join();
    //}
    //catch(InterruptedException e)
    //{
    //  throw new AssertionError(e);
    //}
    JunitUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSortedAscending(char[] arr,char[] copy)
  {
    SortUtil.uncheckedsort(arr,0,arr.length-1);
    //Thread stockSorterThread=new Thread(()->
    //{
    Arrays.sort(copy,0,copy.length);
    //});
    //stockSorterThread.start();
    //try
    //{
    //  stockSorterThread.join();
    //}
    //catch(InterruptedException e)
    //{
    //  throw new AssertionError(e);
    //}
    JunitUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSortedAscending(short[] arr,short[] copy)
  {
    SortUtil.uncheckedsort(arr,0,arr.length-1);
    //Thread stockSorterThread=new Thread(()->
    //{
    Arrays.sort(copy,0,copy.length);
    //});
    //stockSorterThread.start();
    //try
    //{
    //  stockSorterThread.join();
    //}
    //catch(InterruptedException e)
    //{
    //  throw new AssertionError(e);
    //}
    JunitUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSortedAscending(int[] arr,int[] copy)
  {
    SortUtil.uncheckedsort(arr,0,arr.length-1);
    //Thread stockSorterThread=new Thread(()->
    //{
    Arrays.sort(copy,0,copy.length);
    //});
    //stockSorterThread.start();
    //try
    //{
    //  stockSorterThread.join();
    //}
    //catch(InterruptedException e)
    //{
    //  throw new AssertionError(e);
    //}
    JunitUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSortedAscending(long[] arr,long[] copy)
  {
    SortUtil.uncheckedsort(arr,0,arr.length-1);
    //Thread stockSorterThread=new Thread(()->
    //{
    Arrays.sort(copy,0,copy.length);
    //});
    //stockSorterThread.start();
    //try
    //{
    //  stockSorterThread.join();
    //}
    //catch(InterruptedException e)
    //{
    //  throw new AssertionError(e);
    //}
    JunitUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSortedAscending(float[] arr,float[] copy)
  {
    SortUtil.uncheckedsort(arr,0,arr.length-1);
    //Thread stockSorterThread=new Thread(()->
    //{
    Arrays.sort(copy,0,copy.length);
    //});
    //stockSorterThread.start();
    //try
    //{
    //  stockSorterThread.join();
    //}
    //catch(InterruptedException e)
    //{
    //  throw new AssertionError(e);
    //}
    JunitUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSortedAscending(double[] arr,double[] copy)
  {
    SortUtil.uncheckedsort(arr,0,arr.length-1);
    //Thread stockSorterThread=new Thread(()->
    //{
    Arrays.sort(copy,0,copy.length);
    //});
    //stockSorterThread.start();
    //try
    //{
    //  stockSorterThread.join();
    //}
    //catch(InterruptedException e)
    //{
    //  throw new AssertionError(e);
    //}
    JunitUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSortedDescending(Integer[] arr,Integer[] copy)
  {
    SortUtil.reverseSortObjectTimSort.uncheckedsort(arr,0,arr.length);
    //Thread stockSorterThread=new Thread(()->
    //{
    Arrays.sort(copy,0,copy.length);
    OmniArray.OfRef.reverseRange(copy,0,copy.length-1);
    //});
    //stockSorterThread.start();
    //try
    //{
    //  stockSorterThread.join();
    //}
    //catch(InterruptedException e)
    //{
    //  throw new AssertionError(e);
    //}
    JunitUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSortedDescending(String[] arr,String[] copy)
  {
    SortUtil.reverseSortObjectTimSort.uncheckedsort(arr,0,arr.length);
    //Thread stockSorterThread=new Thread(()->
    //{
    Arrays.sort(copy,0,copy.length);
    OmniArray.OfRef.reverseRange(copy,0,copy.length-1);
    //});
    //stockSorterThread.start();
    //try
    //{
    //  stockSorterThread.join();
    //}
    //catch(InterruptedException e)
    //{
    //  throw new AssertionError(e);
    //}
    JunitUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSortedDescending(boolean[] arr,boolean[] copy)
  {
    SortUtil.uncheckedreverseSort(arr,0,arr.length-1);
    //Thread stockSorterThread=new Thread(()->
    //{
    Boolean[] boxedCopy=new Boolean[copy.length];
    ArrCopy.uncheckedCopy(copy,0,boxedCopy,0,copy.length);
    Arrays.sort(boxedCopy,0,copy.length);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    OmniArray.OfBoolean.reverseRange(copy,0,copy.length-1);
    //});
    //stockSorterThread.start();
    //try
    //{
    //  stockSorterThread.join();
    //}
    //catch(InterruptedException e)
    //{
    //  throw new AssertionError(e);
    //}
    JunitUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSortedDescending(byte[] arr,byte[] copy)
  {
    SortUtil.uncheckedreverseSort(arr,0,arr.length-1);
    //Thread stockSorterThread=new Thread(()->
    //{
    Arrays.sort(copy,0,copy.length);
    OmniArray.OfByte.reverseRange(copy,0,copy.length-1);
    //});
    //stockSorterThread.start();
    //try
    //{
    //  stockSorterThread.join();
    //}
    //catch(InterruptedException e)
    //{
    //  throw new AssertionError(e);
    //}
    JunitUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSortedDescending(char[] arr,char[] copy)
  {
    SortUtil.uncheckedreverseSort(arr,0,arr.length-1);
    //Thread stockSorterThread=new Thread(()->
    //{
    Arrays.sort(copy,0,copy.length);
    OmniArray.OfChar.reverseRange(copy,0,copy.length-1);
    //});
    //stockSorterThread.start();
    //try
    //{
    //  stockSorterThread.join();
    //}
    //catch(InterruptedException e)
    //{
    //  throw new AssertionError(e);
    //}
    JunitUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSortedDescending(short[] arr,short[] copy)
  {
    SortUtil.uncheckedreverseSort(arr,0,arr.length-1);
    //Thread stockSorterThread=new Thread(()->
    //{
    Arrays.sort(copy,0,copy.length);
    OmniArray.OfShort.reverseRange(copy,0,copy.length-1);
    //});
    //stockSorterThread.start();
    //try
    //{
    //  stockSorterThread.join();
    //}
    //catch(InterruptedException e)
    //{
    //  throw new AssertionError(e);
    //}
    JunitUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSortedDescending(int[] arr,int[] copy)
  {
    SortUtil.uncheckedreverseSort(arr,0,arr.length-1);
    //Thread stockSorterThread=new Thread(()->
    //{
    Arrays.sort(copy,0,copy.length);
    OmniArray.OfInt.reverseRange(copy,0,copy.length-1);
    //});
    //stockSorterThread.start();
    //try
    //{
    //  stockSorterThread.join();
    //}
    //catch(InterruptedException e)
    //{
    //  throw new AssertionError(e);
    //}
    JunitUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSortedDescending(long[] arr,long[] copy)
  {
    SortUtil.uncheckedreverseSort(arr,0,arr.length-1);
    //Thread stockSorterThread=new Thread(()->
    //{
    Arrays.sort(copy,0,copy.length);
    OmniArray.OfLong.reverseRange(copy,0,copy.length-1);
    //});
    //stockSorterThread.start();
    //try
    //{
    //  stockSorterThread.join();
    //}
    //catch(InterruptedException e)
    //{
    //  throw new AssertionError(e);
    //}
    JunitUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSortedDescending(float[] arr,float[] copy)
  {
    SortUtil.uncheckedreverseSort(arr,0,arr.length-1);
    //Thread stockSorterThread=new Thread(()->
    //{
    Arrays.sort(copy,0,copy.length);
    OmniArray.OfFloat.reverseRange(copy,0,copy.length-1);
    //});
    //stockSorterThread.start();
    //try
    //{
    //  stockSorterThread.join();
    //}
    //catch(InterruptedException e)
    //{
    //  throw new AssertionError(e);
    //}
    JunitUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSortedDescending(double[] arr,double[] copy)
  {
    SortUtil.uncheckedreverseSort(arr,0,arr.length-1);
    //Thread stockSorterThread=new Thread(()->
    //{
    Arrays.sort(copy,0,copy.length);
    OmniArray.OfDouble.reverseRange(copy,0,copy.length-1);
    //});
    //stockSorterThread.start();
    //try
    //{
    //  stockSorterThread.join();
    //}
    //catch(InterruptedException e)
    //{
    //  throw new AssertionError(e);
    //}
    JunitUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,copy.length);
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
    JunitUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,copy.length);
  }
  private static void isSorted(String[] arr,String[] copy,Comparator<? super String> sorter)
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
    JunitUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,copy.length);
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
    JunitUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,copy.length);
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
    JunitUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,copy.length);
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
    JunitUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,copy.length);
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
    JunitUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,copy.length);
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
    JunitUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,copy.length);
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
    JunitUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,copy.length);
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
    JunitUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,copy.length);
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
    JunitUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,copy.length);
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
/*
  private static void testbooleanAscendingcomparatorSortHelper(JunitUtil.booleanArrayBuilder builder,int arrLength,long randSeed)
  {
    boolean[] golden=new boolean[arrLength];
    boolean[] test=new boolean[arrLength];
    Random rand=new Random(randSeed);
    var sorter=JunitUtil.getAscendingbooleanComparator();
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
  @Test
  public void testbooleanAscendingcomparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbooleanAscendingcomparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.booleanArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbooleanAscendingcomparatorSortHelper(JunitUtil.booleanArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testbooleanAscendingcomparatorSortHelper(JunitUtil.booleanArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testbooleanAscendingcomparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbooleanAscendingcomparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.booleanArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbooleanAscendingcomparatorSortHelper(JunitUtil.booleanArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testbooleanAscendingcomparatorSortHelper(JunitUtil.booleanArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testbooleanAscendingcomparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbooleanAscendingcomparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.booleanArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbooleanAscendingcomparatorSortHelper(JunitUtil.booleanArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testbooleanAscendingcomparatorSortHelper(JunitUtil.booleanArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testbooleanAscendingcomparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbooleanAscendingcomparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.booleanArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbooleanAscendingcomparatorSortHelper(JunitUtil.booleanArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testbooleanAscendingcomparatorSortHelper(JunitUtil.booleanArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  private static void testbooleanDescendingcomparatorSortHelper(JunitUtil.booleanArrayBuilder builder,int arrLength,long randSeed)
  {
    boolean[] golden=new boolean[arrLength];
    boolean[] test=new boolean[arrLength];
    Random rand=new Random(randSeed);
    var sorter=JunitUtil.getDescendingbooleanComparator();
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
  @Test
  public void testbooleanDescendingcomparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbooleanDescendingcomparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.booleanArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbooleanDescendingcomparatorSortHelper(JunitUtil.booleanArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testbooleanDescendingcomparatorSortHelper(JunitUtil.booleanArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testbooleanDescendingcomparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbooleanDescendingcomparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.booleanArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbooleanDescendingcomparatorSortHelper(JunitUtil.booleanArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testbooleanDescendingcomparatorSortHelper(JunitUtil.booleanArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testbooleanDescendingcomparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbooleanDescendingcomparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.booleanArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbooleanDescendingcomparatorSortHelper(JunitUtil.booleanArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testbooleanDescendingcomparatorSortHelper(JunitUtil.booleanArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testbooleanDescendingcomparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbooleanDescendingcomparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.booleanArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbooleanDescendingcomparatorSortHelper(JunitUtil.booleanArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testbooleanDescendingcomparatorSortHelper(JunitUtil.booleanArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  private static void testbooleanUnsortedcomparatorSortHelper(JunitUtil.booleanArrayBuilder builder,int arrLength,long randSeed)
  {
    boolean[] golden=new boolean[arrLength];
    boolean[] test=new boolean[arrLength];
    Random rand=new Random(randSeed);
    var sorter=JunitUtil.getUnsortedbooleanComparator();
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
  @Test
  public void testbooleanUnsortedcomparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbooleanUnsortedcomparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.booleanArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbooleanUnsortedcomparatorSortHelper(JunitUtil.booleanArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testbooleanUnsortedcomparatorSortHelper(JunitUtil.booleanArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testbooleanUnsortedcomparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbooleanUnsortedcomparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.booleanArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbooleanUnsortedcomparatorSortHelper(JunitUtil.booleanArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testbooleanUnsortedcomparatorSortHelper(JunitUtil.booleanArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testbooleanUnsortedcomparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbooleanUnsortedcomparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.booleanArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbooleanUnsortedcomparatorSortHelper(JunitUtil.booleanArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testbooleanUnsortedcomparatorSortHelper(JunitUtil.booleanArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testbooleanUnsortedcomparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbooleanUnsortedcomparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.booleanArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbooleanUnsortedcomparatorSortHelper(JunitUtil.booleanArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testbooleanUnsortedcomparatorSortHelper(JunitUtil.booleanArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
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
  @Test
  public void testbooleanAscendingnonComparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbooleanAscendingnonComparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.booleanArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbooleanAscendingnonComparatorSortHelper(JunitUtil.booleanArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testbooleanAscendingnonComparatorSortHelper(JunitUtil.booleanArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testbooleanAscendingnonComparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbooleanAscendingnonComparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.booleanArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbooleanAscendingnonComparatorSortHelper(JunitUtil.booleanArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testbooleanAscendingnonComparatorSortHelper(JunitUtil.booleanArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testbooleanAscendingnonComparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbooleanAscendingnonComparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.booleanArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbooleanAscendingnonComparatorSortHelper(JunitUtil.booleanArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testbooleanAscendingnonComparatorSortHelper(JunitUtil.booleanArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testbooleanAscendingnonComparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbooleanAscendingnonComparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.booleanArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbooleanAscendingnonComparatorSortHelper(JunitUtil.booleanArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testbooleanAscendingnonComparatorSortHelper(JunitUtil.booleanArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
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
  @Test
  public void testbooleanDescendingnonComparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbooleanDescendingnonComparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.booleanArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbooleanDescendingnonComparatorSortHelper(JunitUtil.booleanArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testbooleanDescendingnonComparatorSortHelper(JunitUtil.booleanArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testbooleanDescendingnonComparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbooleanDescendingnonComparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.booleanArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbooleanDescendingnonComparatorSortHelper(JunitUtil.booleanArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testbooleanDescendingnonComparatorSortHelper(JunitUtil.booleanArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testbooleanDescendingnonComparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbooleanDescendingnonComparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.booleanArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbooleanDescendingnonComparatorSortHelper(JunitUtil.booleanArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testbooleanDescendingnonComparatorSortHelper(JunitUtil.booleanArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testbooleanDescendingnonComparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbooleanDescendingnonComparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.booleanArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbooleanDescendingnonComparatorSortHelper(JunitUtil.booleanArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testbooleanDescendingnonComparatorSortHelper(JunitUtil.booleanArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
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
  @Test
  public void testbyteAscendingnonComparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteAscendingnonComparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteAscendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testbyteAscendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteAscendingnonComparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteAscendingnonComparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteAscendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testbyteAscendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteAscendingnonComparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteAscendingnonComparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteAscendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testbyteAscendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteAscendingnonComparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteAscendingnonComparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteAscendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testbyteAscendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteAscendingnonComparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteAscendingnonComparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteAscendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testbyteAscendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteAscendingnonComparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteAscendingnonComparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteAscendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testbyteAscendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteAscendingnonComparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteAscendingnonComparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteAscendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testbyteAscendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteAscendingnonComparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteAscendingnonComparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteAscendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testbyteAscendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteAscendingnonComparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteAscendingnonComparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteAscendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testbyteAscendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteAscendingnonComparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteAscendingnonComparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testbyteAscendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteAscendingnonComparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteAscendingnonComparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteAscendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testbyteAscendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteAscendingnonComparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteAscendingnonComparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteAscendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testbyteAscendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteAscendingnonComparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteAscendingnonComparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteAscendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testbyteAscendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteAscendingnonComparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteAscendingnonComparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteAscendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testbyteAscendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteAscendingnonComparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteAscendingnonComparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteAscendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testbyteAscendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
  private static void testbyteDescendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder builder,int arrLength,long randSeed)
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
        isSortedDescending(test,golden);
      }
    }
  }
  @Test
  public void testbyteDescendingnonComparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteDescendingnonComparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteDescendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testbyteDescendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteDescendingnonComparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteDescendingnonComparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteDescendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testbyteDescendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteDescendingnonComparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteDescendingnonComparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteDescendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testbyteDescendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteDescendingnonComparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteDescendingnonComparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteDescendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testbyteDescendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteDescendingnonComparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteDescendingnonComparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteDescendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testbyteDescendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteDescendingnonComparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteDescendingnonComparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteDescendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testbyteDescendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteDescendingnonComparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteDescendingnonComparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteDescendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testbyteDescendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteDescendingnonComparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteDescendingnonComparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteDescendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testbyteDescendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteDescendingnonComparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteDescendingnonComparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteDescendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testbyteDescendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteDescendingnonComparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteDescendingnonComparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Duplicated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteDescendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.Duplicated,arrLength,randSeed);
       });
     }
     else
     {
       testbyteDescendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteDescendingnonComparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteDescendingnonComparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteDescendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testbyteDescendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteDescendingnonComparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteDescendingnonComparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteDescendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testbyteDescendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteDescendingnonComparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteDescendingnonComparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteDescendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testbyteDescendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteDescendingnonComparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteDescendingnonComparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteDescendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testbyteDescendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteDescendingnonComparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteDescendingnonComparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteDescendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testbyteDescendingnonComparatorSortHelper(JunitUtil.byteArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
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
  @Test
  public void testcharAscendingnonComparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharAscendingnonComparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharAscendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testcharAscendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testcharAscendingnonComparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharAscendingnonComparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharAscendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testcharAscendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testcharAscendingnonComparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharAscendingnonComparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharAscendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testcharAscendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testcharAscendingnonComparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharAscendingnonComparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharAscendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testcharAscendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testcharAscendingnonComparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharAscendingnonComparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharAscendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testcharAscendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testcharAscendingnonComparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharAscendingnonComparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharAscendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testcharAscendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testcharAscendingnonComparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharAscendingnonComparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharAscendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testcharAscendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testcharAscendingnonComparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharAscendingnonComparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharAscendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testcharAscendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testcharAscendingnonComparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharAscendingnonComparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharAscendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testcharAscendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testcharAscendingnonComparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharAscendingnonComparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testcharAscendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testcharAscendingnonComparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharAscendingnonComparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharAscendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testcharAscendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testcharAscendingnonComparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharAscendingnonComparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharAscendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testcharAscendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testcharAscendingnonComparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharAscendingnonComparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharAscendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testcharAscendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testcharAscendingnonComparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharAscendingnonComparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharAscendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testcharAscendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testcharAscendingnonComparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharAscendingnonComparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharAscendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testcharAscendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
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
  @Test
  public void testcharDescendingnonComparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharDescendingnonComparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharDescendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testcharDescendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testcharDescendingnonComparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharDescendingnonComparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharDescendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testcharDescendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testcharDescendingnonComparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharDescendingnonComparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharDescendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testcharDescendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testcharDescendingnonComparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharDescendingnonComparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharDescendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testcharDescendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testcharDescendingnonComparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharDescendingnonComparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharDescendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testcharDescendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testcharDescendingnonComparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharDescendingnonComparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharDescendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testcharDescendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testcharDescendingnonComparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharDescendingnonComparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharDescendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testcharDescendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testcharDescendingnonComparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharDescendingnonComparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharDescendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testcharDescendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testcharDescendingnonComparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharDescendingnonComparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharDescendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testcharDescendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testcharDescendingnonComparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharDescendingnonComparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testcharDescendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testcharDescendingnonComparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharDescendingnonComparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharDescendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testcharDescendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testcharDescendingnonComparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharDescendingnonComparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharDescendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testcharDescendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testcharDescendingnonComparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharDescendingnonComparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharDescendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testcharDescendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testcharDescendingnonComparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharDescendingnonComparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharDescendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testcharDescendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testcharDescendingnonComparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharDescendingnonComparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharDescendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testcharDescendingnonComparatorSortHelper(JunitUtil.charArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
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
  @Test
  public void testshortAscendingnonComparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortAscendingnonComparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortAscendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testshortAscendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testshortAscendingnonComparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortAscendingnonComparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortAscendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testshortAscendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testshortAscendingnonComparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortAscendingnonComparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortAscendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testshortAscendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testshortAscendingnonComparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortAscendingnonComparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortAscendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testshortAscendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testshortAscendingnonComparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortAscendingnonComparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortAscendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testshortAscendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testshortAscendingnonComparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortAscendingnonComparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortAscendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testshortAscendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testshortAscendingnonComparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortAscendingnonComparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortAscendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testshortAscendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testshortAscendingnonComparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortAscendingnonComparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortAscendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testshortAscendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testshortAscendingnonComparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortAscendingnonComparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortAscendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testshortAscendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testshortAscendingnonComparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortAscendingnonComparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testshortAscendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testshortAscendingnonComparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortAscendingnonComparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortAscendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testshortAscendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testshortAscendingnonComparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortAscendingnonComparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortAscendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testshortAscendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testshortAscendingnonComparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortAscendingnonComparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortAscendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testshortAscendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testshortAscendingnonComparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortAscendingnonComparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortAscendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testshortAscendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testshortAscendingnonComparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortAscendingnonComparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortAscendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testshortAscendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
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
  @Test
  public void testshortDescendingnonComparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortDescendingnonComparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortDescendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testshortDescendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testshortDescendingnonComparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortDescendingnonComparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortDescendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testshortDescendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testshortDescendingnonComparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortDescendingnonComparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortDescendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testshortDescendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testshortDescendingnonComparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortDescendingnonComparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortDescendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testshortDescendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testshortDescendingnonComparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortDescendingnonComparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortDescendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testshortDescendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testshortDescendingnonComparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortDescendingnonComparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortDescendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testshortDescendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testshortDescendingnonComparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortDescendingnonComparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortDescendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testshortDescendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testshortDescendingnonComparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortDescendingnonComparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortDescendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testshortDescendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testshortDescendingnonComparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortDescendingnonComparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortDescendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testshortDescendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testshortDescendingnonComparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortDescendingnonComparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testshortDescendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testshortDescendingnonComparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortDescendingnonComparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortDescendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testshortDescendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testshortDescendingnonComparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortDescendingnonComparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortDescendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testshortDescendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testshortDescendingnonComparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortDescendingnonComparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortDescendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testshortDescendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testshortDescendingnonComparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortDescendingnonComparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortDescendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testshortDescendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testshortDescendingnonComparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortDescendingnonComparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortDescendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testshortDescendingnonComparatorSortHelper(JunitUtil.shortArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
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
  @Test
  public void testintAscendingnonComparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintAscendingnonComparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintAscendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testintAscendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testintAscendingnonComparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintAscendingnonComparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintAscendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testintAscendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testintAscendingnonComparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintAscendingnonComparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintAscendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testintAscendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testintAscendingnonComparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintAscendingnonComparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintAscendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testintAscendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testintAscendingnonComparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintAscendingnonComparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintAscendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testintAscendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testintAscendingnonComparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintAscendingnonComparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintAscendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testintAscendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testintAscendingnonComparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintAscendingnonComparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintAscendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testintAscendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testintAscendingnonComparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintAscendingnonComparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintAscendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testintAscendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testintAscendingnonComparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintAscendingnonComparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintAscendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testintAscendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testintAscendingnonComparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintAscendingnonComparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testintAscendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testintAscendingnonComparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintAscendingnonComparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintAscendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testintAscendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testintAscendingnonComparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintAscendingnonComparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintAscendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testintAscendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testintAscendingnonComparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintAscendingnonComparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintAscendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testintAscendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testintAscendingnonComparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintAscendingnonComparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintAscendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testintAscendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testintAscendingnonComparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintAscendingnonComparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintAscendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testintAscendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
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
  @Test
  public void testintDescendingnonComparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintDescendingnonComparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintDescendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testintDescendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testintDescendingnonComparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintDescendingnonComparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintDescendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testintDescendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testintDescendingnonComparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintDescendingnonComparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintDescendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testintDescendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testintDescendingnonComparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintDescendingnonComparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintDescendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testintDescendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testintDescendingnonComparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintDescendingnonComparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintDescendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testintDescendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testintDescendingnonComparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintDescendingnonComparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintDescendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testintDescendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testintDescendingnonComparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintDescendingnonComparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintDescendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testintDescendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testintDescendingnonComparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintDescendingnonComparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintDescendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testintDescendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testintDescendingnonComparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintDescendingnonComparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintDescendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testintDescendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testintDescendingnonComparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintDescendingnonComparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testintDescendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testintDescendingnonComparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintDescendingnonComparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintDescendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testintDescendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testintDescendingnonComparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintDescendingnonComparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintDescendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testintDescendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testintDescendingnonComparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintDescendingnonComparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintDescendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testintDescendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testintDescendingnonComparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintDescendingnonComparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintDescendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testintDescendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testintDescendingnonComparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintDescendingnonComparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintDescendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testintDescendingnonComparatorSortHelper(JunitUtil.intArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
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
  @Test
  public void testlongAscendingnonComparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongAscendingnonComparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongAscendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testlongAscendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testlongAscendingnonComparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongAscendingnonComparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongAscendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testlongAscendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testlongAscendingnonComparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongAscendingnonComparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongAscendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testlongAscendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testlongAscendingnonComparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongAscendingnonComparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongAscendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testlongAscendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testlongAscendingnonComparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongAscendingnonComparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongAscendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testlongAscendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testlongAscendingnonComparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongAscendingnonComparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongAscendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testlongAscendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testlongAscendingnonComparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongAscendingnonComparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongAscendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testlongAscendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testlongAscendingnonComparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongAscendingnonComparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongAscendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testlongAscendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testlongAscendingnonComparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongAscendingnonComparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongAscendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testlongAscendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testlongAscendingnonComparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongAscendingnonComparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testlongAscendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testlongAscendingnonComparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongAscendingnonComparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongAscendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testlongAscendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testlongAscendingnonComparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongAscendingnonComparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongAscendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testlongAscendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testlongAscendingnonComparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongAscendingnonComparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongAscendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testlongAscendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testlongAscendingnonComparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongAscendingnonComparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongAscendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testlongAscendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testlongAscendingnonComparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongAscendingnonComparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongAscendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testlongAscendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
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
  @Test
  public void testlongDescendingnonComparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongDescendingnonComparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongDescendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testlongDescendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testlongDescendingnonComparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongDescendingnonComparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongDescendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testlongDescendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testlongDescendingnonComparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongDescendingnonComparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongDescendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testlongDescendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testlongDescendingnonComparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongDescendingnonComparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongDescendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testlongDescendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testlongDescendingnonComparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongDescendingnonComparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongDescendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testlongDescendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testlongDescendingnonComparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongDescendingnonComparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongDescendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testlongDescendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testlongDescendingnonComparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongDescendingnonComparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongDescendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testlongDescendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testlongDescendingnonComparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongDescendingnonComparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongDescendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testlongDescendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testlongDescendingnonComparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongDescendingnonComparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongDescendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testlongDescendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testlongDescendingnonComparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongDescendingnonComparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testlongDescendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testlongDescendingnonComparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongDescendingnonComparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongDescendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testlongDescendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testlongDescendingnonComparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongDescendingnonComparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongDescendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testlongDescendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testlongDescendingnonComparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongDescendingnonComparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongDescendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testlongDescendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testlongDescendingnonComparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongDescendingnonComparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongDescendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testlongDescendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testlongDescendingnonComparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongDescendingnonComparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongDescendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testlongDescendingnonComparatorSortHelper(JunitUtil.longArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
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
  @Test
  public void testfloatAscendingnonComparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatAscendingnonComparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatAscendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testfloatAscendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatAscendingnonComparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatAscendingnonComparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatAscendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testfloatAscendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatAscendingnonComparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatAscendingnonComparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatAscendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testfloatAscendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatAscendingnonComparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatAscendingnonComparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatAscendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testfloatAscendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatAscendingnonComparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatAscendingnonComparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatAscendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testfloatAscendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatAscendingnonComparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatAscendingnonComparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatAscendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testfloatAscendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatAscendingnonComparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatAscendingnonComparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatAscendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testfloatAscendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatAscendingnonComparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatAscendingnonComparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatAscendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testfloatAscendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatAscendingnonComparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatAscendingnonComparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatAscendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testfloatAscendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatAscendingnonComparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatAscendingnonComparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testfloatAscendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatAscendingnonComparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatAscendingnonComparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatAscendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testfloatAscendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatAscendingnonComparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatAscendingnonComparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatAscendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testfloatAscendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatAscendingnonComparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatAscendingnonComparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatAscendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testfloatAscendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatAscendingnonComparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatAscendingnonComparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatAscendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testfloatAscendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatAscendingnonComparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatAscendingnonComparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatAscendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testfloatAscendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatAscendingnonComparatorSortWithNaNsAndZerosArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatAscendingnonComparatorSortWithNaNsAndZerosArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.WithNaNsAndZeros.isRandomized())
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
     else
     {
       testfloatAscendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.WithNaNsAndZeros,arrLength,0);
     }
    }
  }
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
  @Test
  public void testfloatDescendingnonComparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatDescendingnonComparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatDescendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testfloatDescendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatDescendingnonComparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatDescendingnonComparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatDescendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testfloatDescendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatDescendingnonComparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatDescendingnonComparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatDescendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testfloatDescendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatDescendingnonComparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatDescendingnonComparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatDescendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testfloatDescendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatDescendingnonComparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatDescendingnonComparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatDescendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testfloatDescendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatDescendingnonComparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatDescendingnonComparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatDescendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testfloatDescendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatDescendingnonComparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatDescendingnonComparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatDescendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testfloatDescendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatDescendingnonComparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatDescendingnonComparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatDescendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testfloatDescendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatDescendingnonComparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatDescendingnonComparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatDescendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testfloatDescendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatDescendingnonComparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatDescendingnonComparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testfloatDescendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatDescendingnonComparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatDescendingnonComparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatDescendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testfloatDescendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatDescendingnonComparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatDescendingnonComparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatDescendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testfloatDescendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatDescendingnonComparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatDescendingnonComparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatDescendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testfloatDescendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatDescendingnonComparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatDescendingnonComparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatDescendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testfloatDescendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatDescendingnonComparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatDescendingnonComparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatDescendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testfloatDescendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatDescendingnonComparatorSortWithNaNsAndZerosArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatDescendingnonComparatorSortWithNaNsAndZerosArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.WithNaNsAndZeros.isRandomized())
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
     else
     {
       testfloatDescendingnonComparatorSortHelper(JunitUtil.floatArrayBuilder.WithNaNsAndZeros,arrLength,0);
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
  @Test
  public void testdoubleAscendingnonComparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleAscendingnonComparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleAscendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleAscendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleAscendingnonComparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleAscendingnonComparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleAscendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleAscendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleAscendingnonComparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleAscendingnonComparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleAscendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleAscendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleAscendingnonComparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleAscendingnonComparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleAscendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleAscendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleAscendingnonComparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleAscendingnonComparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleAscendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleAscendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleAscendingnonComparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleAscendingnonComparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleAscendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleAscendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleAscendingnonComparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleAscendingnonComparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleAscendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleAscendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleAscendingnonComparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleAscendingnonComparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleAscendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleAscendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleAscendingnonComparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleAscendingnonComparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleAscendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleAscendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleAscendingnonComparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleAscendingnonComparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testdoubleAscendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleAscendingnonComparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleAscendingnonComparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleAscendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleAscendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleAscendingnonComparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleAscendingnonComparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleAscendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleAscendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleAscendingnonComparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleAscendingnonComparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleAscendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleAscendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleAscendingnonComparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleAscendingnonComparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleAscendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleAscendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleAscendingnonComparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleAscendingnonComparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleAscendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleAscendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleAscendingnonComparatorSortWithNaNsAndZerosArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleAscendingnonComparatorSortWithNaNsAndZerosArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.WithNaNsAndZeros.isRandomized())
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
     else
     {
       testdoubleAscendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.WithNaNsAndZeros,arrLength,0);
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
  @Test
  public void testdoubleDescendingnonComparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleDescendingnonComparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleDescendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleDescendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleDescendingnonComparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleDescendingnonComparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleDescendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleDescendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleDescendingnonComparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleDescendingnonComparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleDescendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleDescendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleDescendingnonComparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleDescendingnonComparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleDescendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleDescendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleDescendingnonComparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleDescendingnonComparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleDescendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleDescendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleDescendingnonComparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleDescendingnonComparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleDescendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleDescendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleDescendingnonComparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleDescendingnonComparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleDescendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleDescendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleDescendingnonComparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleDescendingnonComparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleDescendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleDescendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleDescendingnonComparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleDescendingnonComparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleDescendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleDescendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleDescendingnonComparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleDescendingnonComparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testdoubleDescendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleDescendingnonComparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleDescendingnonComparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleDescendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleDescendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleDescendingnonComparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleDescendingnonComparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleDescendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleDescendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleDescendingnonComparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleDescendingnonComparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleDescendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleDescendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleDescendingnonComparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleDescendingnonComparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleDescendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleDescendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleDescendingnonComparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleDescendingnonComparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleDescendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleDescendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleDescendingnonComparatorSortWithNaNsAndZerosArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleDescendingnonComparatorSortWithNaNsAndZerosArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.WithNaNsAndZeros.isRandomized())
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
     else
     {
       testdoubleDescendingnonComparatorSortHelper(JunitUtil.doubleArrayBuilder.WithNaNsAndZeros,arrLength,0);
     }
    }
  }
*/
  private static void testbyteUnsortedcomparatorSortHelper(JunitUtil.byteArrayBuilder builder,int arrLength,long randSeed)
  {
    byte[] golden=new byte[arrLength];
    byte[] test=new byte[arrLength];
    Random rand=new Random(randSeed);
    var sorter=JunitUtil.getUnsortedbyteComparator();
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
  @Test
  public void testbyteUnsortedcomparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteUnsortedcomparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteUnsortedcomparatorSortHelper(JunitUtil.byteArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testbyteUnsortedcomparatorSortHelper(JunitUtil.byteArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteUnsortedcomparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteUnsortedcomparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteUnsortedcomparatorSortHelper(JunitUtil.byteArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testbyteUnsortedcomparatorSortHelper(JunitUtil.byteArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteUnsortedcomparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteUnsortedcomparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteUnsortedcomparatorSortHelper(JunitUtil.byteArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testbyteUnsortedcomparatorSortHelper(JunitUtil.byteArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteUnsortedcomparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteUnsortedcomparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteUnsortedcomparatorSortHelper(JunitUtil.byteArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testbyteUnsortedcomparatorSortHelper(JunitUtil.byteArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteUnsortedcomparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteUnsortedcomparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteUnsortedcomparatorSortHelper(JunitUtil.byteArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testbyteUnsortedcomparatorSortHelper(JunitUtil.byteArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteUnsortedcomparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteUnsortedcomparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteUnsortedcomparatorSortHelper(JunitUtil.byteArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testbyteUnsortedcomparatorSortHelper(JunitUtil.byteArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteUnsortedcomparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteUnsortedcomparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteUnsortedcomparatorSortHelper(JunitUtil.byteArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testbyteUnsortedcomparatorSortHelper(JunitUtil.byteArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteUnsortedcomparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteUnsortedcomparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteUnsortedcomparatorSortHelper(JunitUtil.byteArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testbyteUnsortedcomparatorSortHelper(JunitUtil.byteArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteUnsortedcomparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteUnsortedcomparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteUnsortedcomparatorSortHelper(JunitUtil.byteArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testbyteUnsortedcomparatorSortHelper(JunitUtil.byteArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteUnsortedcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteUnsortedcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testbyteUnsortedcomparatorSortHelper(JunitUtil.byteArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteUnsortedcomparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteUnsortedcomparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteUnsortedcomparatorSortHelper(JunitUtil.byteArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testbyteUnsortedcomparatorSortHelper(JunitUtil.byteArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteUnsortedcomparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteUnsortedcomparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteUnsortedcomparatorSortHelper(JunitUtil.byteArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testbyteUnsortedcomparatorSortHelper(JunitUtil.byteArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteUnsortedcomparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteUnsortedcomparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteUnsortedcomparatorSortHelper(JunitUtil.byteArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testbyteUnsortedcomparatorSortHelper(JunitUtil.byteArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteUnsortedcomparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteUnsortedcomparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteUnsortedcomparatorSortHelper(JunitUtil.byteArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testbyteUnsortedcomparatorSortHelper(JunitUtil.byteArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteUnsortedcomparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteUnsortedcomparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteUnsortedcomparatorSortHelper(JunitUtil.byteArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testbyteUnsortedcomparatorSortHelper(JunitUtil.byteArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
  private static void testbyteAscendingcomparatorSortHelper(JunitUtil.byteArrayBuilder builder,int arrLength,long randSeed)
  {
    byte[] golden=new byte[arrLength];
    byte[] test=new byte[arrLength];
    Random rand=new Random(randSeed);
    var sorter=JunitUtil.getAscendingbyteComparator();
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
  @Test
  public void testbyteAscendingcomparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteAscendingcomparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteAscendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testbyteAscendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteAscendingcomparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteAscendingcomparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteAscendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testbyteAscendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteAscendingcomparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteAscendingcomparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteAscendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testbyteAscendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteAscendingcomparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteAscendingcomparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteAscendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testbyteAscendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteAscendingcomparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteAscendingcomparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteAscendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testbyteAscendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteAscendingcomparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteAscendingcomparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteAscendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testbyteAscendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteAscendingcomparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteAscendingcomparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteAscendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testbyteAscendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteAscendingcomparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteAscendingcomparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteAscendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testbyteAscendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteAscendingcomparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteAscendingcomparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteAscendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testbyteAscendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteAscendingcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteAscendingcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testbyteAscendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteAscendingcomparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteAscendingcomparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteAscendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testbyteAscendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteAscendingcomparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteAscendingcomparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteAscendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testbyteAscendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteAscendingcomparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteAscendingcomparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteAscendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testbyteAscendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteAscendingcomparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteAscendingcomparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteAscendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testbyteAscendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteAscendingcomparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteAscendingcomparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteAscendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testbyteAscendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
  private static void testbyteDescendingcomparatorSortHelper(JunitUtil.byteArrayBuilder builder,int arrLength,long randSeed)
  {
    byte[] golden=new byte[arrLength];
    byte[] test=new byte[arrLength];
    Random rand=new Random(randSeed);
    var sorter=JunitUtil.getDescendingbyteComparator();
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
  @Test
  public void testbyteDescendingcomparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteDescendingcomparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteDescendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testbyteDescendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteDescendingcomparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteDescendingcomparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteDescendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testbyteDescendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteDescendingcomparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteDescendingcomparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteDescendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testbyteDescendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteDescendingcomparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteDescendingcomparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteDescendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testbyteDescendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteDescendingcomparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteDescendingcomparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteDescendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testbyteDescendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteDescendingcomparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteDescendingcomparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteDescendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testbyteDescendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteDescendingcomparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteDescendingcomparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteDescendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testbyteDescendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteDescendingcomparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteDescendingcomparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteDescendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testbyteDescendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteDescendingcomparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteDescendingcomparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteDescendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testbyteDescendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteDescendingcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteDescendingcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testbyteDescendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteDescendingcomparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteDescendingcomparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteDescendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testbyteDescendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteDescendingcomparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteDescendingcomparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteDescendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testbyteDescendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteDescendingcomparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteDescendingcomparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteDescendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testbyteDescendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteDescendingcomparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteDescendingcomparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteDescendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testbyteDescendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testbyteDescendingcomparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testbyteDescendingcomparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.byteArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testbyteDescendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testbyteDescendingcomparatorSortHelper(JunitUtil.byteArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
  /*
  private static void testcharUnsortedcomparatorSortHelper(JunitUtil.charArrayBuilder builder,int arrLength,long randSeed)
  {
    char[] golden=new char[arrLength];
    char[] test=new char[arrLength];
    Random rand=new Random(randSeed);
    var sorter=JunitUtil.getUnsortedcharComparator();
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
  @Test
  public void testcharUnsortedcomparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharUnsortedcomparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharUnsortedcomparatorSortHelper(JunitUtil.charArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testcharUnsortedcomparatorSortHelper(JunitUtil.charArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testcharUnsortedcomparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharUnsortedcomparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharUnsortedcomparatorSortHelper(JunitUtil.charArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testcharUnsortedcomparatorSortHelper(JunitUtil.charArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testcharUnsortedcomparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharUnsortedcomparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharUnsortedcomparatorSortHelper(JunitUtil.charArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testcharUnsortedcomparatorSortHelper(JunitUtil.charArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testcharUnsortedcomparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharUnsortedcomparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharUnsortedcomparatorSortHelper(JunitUtil.charArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testcharUnsortedcomparatorSortHelper(JunitUtil.charArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testcharUnsortedcomparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharUnsortedcomparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharUnsortedcomparatorSortHelper(JunitUtil.charArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testcharUnsortedcomparatorSortHelper(JunitUtil.charArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testcharUnsortedcomparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharUnsortedcomparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharUnsortedcomparatorSortHelper(JunitUtil.charArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testcharUnsortedcomparatorSortHelper(JunitUtil.charArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testcharUnsortedcomparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharUnsortedcomparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharUnsortedcomparatorSortHelper(JunitUtil.charArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testcharUnsortedcomparatorSortHelper(JunitUtil.charArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testcharUnsortedcomparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharUnsortedcomparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharUnsortedcomparatorSortHelper(JunitUtil.charArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testcharUnsortedcomparatorSortHelper(JunitUtil.charArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testcharUnsortedcomparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharUnsortedcomparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharUnsortedcomparatorSortHelper(JunitUtil.charArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testcharUnsortedcomparatorSortHelper(JunitUtil.charArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testcharUnsortedcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharUnsortedcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testcharUnsortedcomparatorSortHelper(JunitUtil.charArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testcharUnsortedcomparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharUnsortedcomparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharUnsortedcomparatorSortHelper(JunitUtil.charArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testcharUnsortedcomparatorSortHelper(JunitUtil.charArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testcharUnsortedcomparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharUnsortedcomparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharUnsortedcomparatorSortHelper(JunitUtil.charArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testcharUnsortedcomparatorSortHelper(JunitUtil.charArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testcharUnsortedcomparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharUnsortedcomparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharUnsortedcomparatorSortHelper(JunitUtil.charArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testcharUnsortedcomparatorSortHelper(JunitUtil.charArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testcharUnsortedcomparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharUnsortedcomparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharUnsortedcomparatorSortHelper(JunitUtil.charArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testcharUnsortedcomparatorSortHelper(JunitUtil.charArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testcharUnsortedcomparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharUnsortedcomparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharUnsortedcomparatorSortHelper(JunitUtil.charArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testcharUnsortedcomparatorSortHelper(JunitUtil.charArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
  private static void testcharAscendingcomparatorSortHelper(JunitUtil.charArrayBuilder builder,int arrLength,long randSeed)
  {
    char[] golden=new char[arrLength];
    char[] test=new char[arrLength];
    Random rand=new Random(randSeed);
    var sorter=JunitUtil.getAscendingcharComparator();
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
  @Test
  public void testcharAscendingcomparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharAscendingcomparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharAscendingcomparatorSortHelper(JunitUtil.charArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testcharAscendingcomparatorSortHelper(JunitUtil.charArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testcharAscendingcomparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharAscendingcomparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharAscendingcomparatorSortHelper(JunitUtil.charArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testcharAscendingcomparatorSortHelper(JunitUtil.charArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testcharAscendingcomparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharAscendingcomparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharAscendingcomparatorSortHelper(JunitUtil.charArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testcharAscendingcomparatorSortHelper(JunitUtil.charArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testcharAscendingcomparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharAscendingcomparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharAscendingcomparatorSortHelper(JunitUtil.charArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testcharAscendingcomparatorSortHelper(JunitUtil.charArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testcharAscendingcomparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharAscendingcomparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharAscendingcomparatorSortHelper(JunitUtil.charArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testcharAscendingcomparatorSortHelper(JunitUtil.charArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testcharAscendingcomparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharAscendingcomparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharAscendingcomparatorSortHelper(JunitUtil.charArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testcharAscendingcomparatorSortHelper(JunitUtil.charArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testcharAscendingcomparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharAscendingcomparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharAscendingcomparatorSortHelper(JunitUtil.charArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testcharAscendingcomparatorSortHelper(JunitUtil.charArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testcharAscendingcomparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharAscendingcomparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharAscendingcomparatorSortHelper(JunitUtil.charArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testcharAscendingcomparatorSortHelper(JunitUtil.charArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testcharAscendingcomparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharAscendingcomparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharAscendingcomparatorSortHelper(JunitUtil.charArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testcharAscendingcomparatorSortHelper(JunitUtil.charArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testcharAscendingcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharAscendingcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testcharAscendingcomparatorSortHelper(JunitUtil.charArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testcharAscendingcomparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharAscendingcomparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharAscendingcomparatorSortHelper(JunitUtil.charArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testcharAscendingcomparatorSortHelper(JunitUtil.charArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testcharAscendingcomparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharAscendingcomparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharAscendingcomparatorSortHelper(JunitUtil.charArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testcharAscendingcomparatorSortHelper(JunitUtil.charArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testcharAscendingcomparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharAscendingcomparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharAscendingcomparatorSortHelper(JunitUtil.charArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testcharAscendingcomparatorSortHelper(JunitUtil.charArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testcharAscendingcomparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharAscendingcomparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharAscendingcomparatorSortHelper(JunitUtil.charArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testcharAscendingcomparatorSortHelper(JunitUtil.charArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testcharAscendingcomparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharAscendingcomparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharAscendingcomparatorSortHelper(JunitUtil.charArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testcharAscendingcomparatorSortHelper(JunitUtil.charArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
  private static void testcharDescendingcomparatorSortHelper(JunitUtil.charArrayBuilder builder,int arrLength,long randSeed)
  {
    char[] golden=new char[arrLength];
    char[] test=new char[arrLength];
    Random rand=new Random(randSeed);
    var sorter=JunitUtil.getDescendingcharComparator();
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
  @Test
  public void testcharDescendingcomparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharDescendingcomparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharDescendingcomparatorSortHelper(JunitUtil.charArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testcharDescendingcomparatorSortHelper(JunitUtil.charArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testcharDescendingcomparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharDescendingcomparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharDescendingcomparatorSortHelper(JunitUtil.charArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testcharDescendingcomparatorSortHelper(JunitUtil.charArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testcharDescendingcomparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharDescendingcomparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharDescendingcomparatorSortHelper(JunitUtil.charArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testcharDescendingcomparatorSortHelper(JunitUtil.charArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testcharDescendingcomparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharDescendingcomparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharDescendingcomparatorSortHelper(JunitUtil.charArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testcharDescendingcomparatorSortHelper(JunitUtil.charArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testcharDescendingcomparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharDescendingcomparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharDescendingcomparatorSortHelper(JunitUtil.charArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testcharDescendingcomparatorSortHelper(JunitUtil.charArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testcharDescendingcomparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharDescendingcomparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharDescendingcomparatorSortHelper(JunitUtil.charArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testcharDescendingcomparatorSortHelper(JunitUtil.charArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testcharDescendingcomparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharDescendingcomparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharDescendingcomparatorSortHelper(JunitUtil.charArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testcharDescendingcomparatorSortHelper(JunitUtil.charArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testcharDescendingcomparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharDescendingcomparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharDescendingcomparatorSortHelper(JunitUtil.charArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testcharDescendingcomparatorSortHelper(JunitUtil.charArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testcharDescendingcomparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharDescendingcomparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharDescendingcomparatorSortHelper(JunitUtil.charArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testcharDescendingcomparatorSortHelper(JunitUtil.charArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testcharDescendingcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharDescendingcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testcharDescendingcomparatorSortHelper(JunitUtil.charArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testcharDescendingcomparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharDescendingcomparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharDescendingcomparatorSortHelper(JunitUtil.charArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testcharDescendingcomparatorSortHelper(JunitUtil.charArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testcharDescendingcomparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharDescendingcomparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharDescendingcomparatorSortHelper(JunitUtil.charArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testcharDescendingcomparatorSortHelper(JunitUtil.charArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testcharDescendingcomparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharDescendingcomparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharDescendingcomparatorSortHelper(JunitUtil.charArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testcharDescendingcomparatorSortHelper(JunitUtil.charArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testcharDescendingcomparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharDescendingcomparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharDescendingcomparatorSortHelper(JunitUtil.charArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testcharDescendingcomparatorSortHelper(JunitUtil.charArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testcharDescendingcomparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testcharDescendingcomparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.charArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testcharDescendingcomparatorSortHelper(JunitUtil.charArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testcharDescendingcomparatorSortHelper(JunitUtil.charArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
  private static void testshortUnsortedcomparatorSortHelper(JunitUtil.shortArrayBuilder builder,int arrLength,long randSeed)
  {
    short[] golden=new short[arrLength];
    short[] test=new short[arrLength];
    Random rand=new Random(randSeed);
    var sorter=JunitUtil.getUnsortedshortComparator();
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
  @Test
  public void testshortUnsortedcomparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortUnsortedcomparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortUnsortedcomparatorSortHelper(JunitUtil.shortArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testshortUnsortedcomparatorSortHelper(JunitUtil.shortArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testshortUnsortedcomparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortUnsortedcomparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortUnsortedcomparatorSortHelper(JunitUtil.shortArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testshortUnsortedcomparatorSortHelper(JunitUtil.shortArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testshortUnsortedcomparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortUnsortedcomparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortUnsortedcomparatorSortHelper(JunitUtil.shortArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testshortUnsortedcomparatorSortHelper(JunitUtil.shortArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testshortUnsortedcomparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortUnsortedcomparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortUnsortedcomparatorSortHelper(JunitUtil.shortArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testshortUnsortedcomparatorSortHelper(JunitUtil.shortArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testshortUnsortedcomparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortUnsortedcomparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortUnsortedcomparatorSortHelper(JunitUtil.shortArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testshortUnsortedcomparatorSortHelper(JunitUtil.shortArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testshortUnsortedcomparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortUnsortedcomparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortUnsortedcomparatorSortHelper(JunitUtil.shortArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testshortUnsortedcomparatorSortHelper(JunitUtil.shortArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testshortUnsortedcomparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortUnsortedcomparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortUnsortedcomparatorSortHelper(JunitUtil.shortArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testshortUnsortedcomparatorSortHelper(JunitUtil.shortArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testshortUnsortedcomparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortUnsortedcomparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortUnsortedcomparatorSortHelper(JunitUtil.shortArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testshortUnsortedcomparatorSortHelper(JunitUtil.shortArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testshortUnsortedcomparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortUnsortedcomparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortUnsortedcomparatorSortHelper(JunitUtil.shortArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testshortUnsortedcomparatorSortHelper(JunitUtil.shortArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testshortUnsortedcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortUnsortedcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testshortUnsortedcomparatorSortHelper(JunitUtil.shortArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testshortUnsortedcomparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortUnsortedcomparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortUnsortedcomparatorSortHelper(JunitUtil.shortArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testshortUnsortedcomparatorSortHelper(JunitUtil.shortArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testshortUnsortedcomparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortUnsortedcomparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortUnsortedcomparatorSortHelper(JunitUtil.shortArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testshortUnsortedcomparatorSortHelper(JunitUtil.shortArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testshortUnsortedcomparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortUnsortedcomparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortUnsortedcomparatorSortHelper(JunitUtil.shortArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testshortUnsortedcomparatorSortHelper(JunitUtil.shortArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testshortUnsortedcomparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortUnsortedcomparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortUnsortedcomparatorSortHelper(JunitUtil.shortArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testshortUnsortedcomparatorSortHelper(JunitUtil.shortArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testshortUnsortedcomparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortUnsortedcomparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortUnsortedcomparatorSortHelper(JunitUtil.shortArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testshortUnsortedcomparatorSortHelper(JunitUtil.shortArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
  private static void testshortAscendingcomparatorSortHelper(JunitUtil.shortArrayBuilder builder,int arrLength,long randSeed)
  {
    short[] golden=new short[arrLength];
    short[] test=new short[arrLength];
    Random rand=new Random(randSeed);
    var sorter=JunitUtil.getAscendingshortComparator();
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
  @Test
  public void testshortAscendingcomparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortAscendingcomparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortAscendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testshortAscendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testshortAscendingcomparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortAscendingcomparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortAscendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testshortAscendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testshortAscendingcomparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortAscendingcomparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortAscendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testshortAscendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testshortAscendingcomparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortAscendingcomparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortAscendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testshortAscendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testshortAscendingcomparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortAscendingcomparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortAscendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testshortAscendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testshortAscendingcomparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortAscendingcomparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortAscendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testshortAscendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testshortAscendingcomparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortAscendingcomparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortAscendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testshortAscendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testshortAscendingcomparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortAscendingcomparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortAscendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testshortAscendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testshortAscendingcomparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortAscendingcomparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortAscendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testshortAscendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testshortAscendingcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortAscendingcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testshortAscendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testshortAscendingcomparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortAscendingcomparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortAscendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testshortAscendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testshortAscendingcomparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortAscendingcomparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortAscendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testshortAscendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testshortAscendingcomparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortAscendingcomparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortAscendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testshortAscendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testshortAscendingcomparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortAscendingcomparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortAscendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testshortAscendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testshortAscendingcomparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortAscendingcomparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortAscendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testshortAscendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
  private static void testshortDescendingcomparatorSortHelper(JunitUtil.shortArrayBuilder builder,int arrLength,long randSeed)
  {
    short[] golden=new short[arrLength];
    short[] test=new short[arrLength];
    Random rand=new Random(randSeed);
    var sorter=JunitUtil.getDescendingshortComparator();
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
  @Test
  public void testshortDescendingcomparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortDescendingcomparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortDescendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testshortDescendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testshortDescendingcomparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortDescendingcomparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortDescendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testshortDescendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testshortDescendingcomparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortDescendingcomparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortDescendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testshortDescendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testshortDescendingcomparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortDescendingcomparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortDescendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testshortDescendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testshortDescendingcomparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortDescendingcomparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortDescendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testshortDescendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testshortDescendingcomparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortDescendingcomparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortDescendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testshortDescendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testshortDescendingcomparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortDescendingcomparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortDescendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testshortDescendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testshortDescendingcomparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortDescendingcomparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortDescendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testshortDescendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testshortDescendingcomparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortDescendingcomparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortDescendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testshortDescendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testshortDescendingcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortDescendingcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testshortDescendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testshortDescendingcomparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortDescendingcomparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortDescendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testshortDescendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testshortDescendingcomparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortDescendingcomparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortDescendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testshortDescendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testshortDescendingcomparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortDescendingcomparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortDescendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testshortDescendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testshortDescendingcomparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortDescendingcomparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortDescendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testshortDescendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testshortDescendingcomparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testshortDescendingcomparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.shortArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testshortDescendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testshortDescendingcomparatorSortHelper(JunitUtil.shortArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
  private static void testintUnsortedcomparatorSortHelper(JunitUtil.intArrayBuilder builder,int arrLength,long randSeed)
  {
    int[] golden=new int[arrLength];
    int[] test=new int[arrLength];
    Random rand=new Random(randSeed);
    var sorter=JunitUtil.getUnsortedintComparator();
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
  @Test
  public void testintUnsortedcomparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintUnsortedcomparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintUnsortedcomparatorSortHelper(JunitUtil.intArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testintUnsortedcomparatorSortHelper(JunitUtil.intArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testintUnsortedcomparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintUnsortedcomparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintUnsortedcomparatorSortHelper(JunitUtil.intArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testintUnsortedcomparatorSortHelper(JunitUtil.intArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testintUnsortedcomparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintUnsortedcomparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintUnsortedcomparatorSortHelper(JunitUtil.intArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testintUnsortedcomparatorSortHelper(JunitUtil.intArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testintUnsortedcomparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintUnsortedcomparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintUnsortedcomparatorSortHelper(JunitUtil.intArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testintUnsortedcomparatorSortHelper(JunitUtil.intArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testintUnsortedcomparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintUnsortedcomparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintUnsortedcomparatorSortHelper(JunitUtil.intArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testintUnsortedcomparatorSortHelper(JunitUtil.intArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testintUnsortedcomparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintUnsortedcomparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintUnsortedcomparatorSortHelper(JunitUtil.intArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testintUnsortedcomparatorSortHelper(JunitUtil.intArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testintUnsortedcomparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintUnsortedcomparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintUnsortedcomparatorSortHelper(JunitUtil.intArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testintUnsortedcomparatorSortHelper(JunitUtil.intArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testintUnsortedcomparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintUnsortedcomparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintUnsortedcomparatorSortHelper(JunitUtil.intArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testintUnsortedcomparatorSortHelper(JunitUtil.intArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testintUnsortedcomparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintUnsortedcomparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintUnsortedcomparatorSortHelper(JunitUtil.intArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testintUnsortedcomparatorSortHelper(JunitUtil.intArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testintUnsortedcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintUnsortedcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testintUnsortedcomparatorSortHelper(JunitUtil.intArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testintUnsortedcomparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintUnsortedcomparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintUnsortedcomparatorSortHelper(JunitUtil.intArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testintUnsortedcomparatorSortHelper(JunitUtil.intArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testintUnsortedcomparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintUnsortedcomparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintUnsortedcomparatorSortHelper(JunitUtil.intArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testintUnsortedcomparatorSortHelper(JunitUtil.intArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testintUnsortedcomparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintUnsortedcomparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintUnsortedcomparatorSortHelper(JunitUtil.intArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testintUnsortedcomparatorSortHelper(JunitUtil.intArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testintUnsortedcomparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintUnsortedcomparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintUnsortedcomparatorSortHelper(JunitUtil.intArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testintUnsortedcomparatorSortHelper(JunitUtil.intArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testintUnsortedcomparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintUnsortedcomparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintUnsortedcomparatorSortHelper(JunitUtil.intArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testintUnsortedcomparatorSortHelper(JunitUtil.intArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
  private static void testintAscendingcomparatorSortHelper(JunitUtil.intArrayBuilder builder,int arrLength,long randSeed)
  {
    int[] golden=new int[arrLength];
    int[] test=new int[arrLength];
    Random rand=new Random(randSeed);
    var sorter=JunitUtil.getAscendingintComparator();
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
  @Test
  public void testintAscendingcomparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintAscendingcomparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintAscendingcomparatorSortHelper(JunitUtil.intArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testintAscendingcomparatorSortHelper(JunitUtil.intArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testintAscendingcomparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintAscendingcomparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintAscendingcomparatorSortHelper(JunitUtil.intArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testintAscendingcomparatorSortHelper(JunitUtil.intArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testintAscendingcomparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintAscendingcomparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintAscendingcomparatorSortHelper(JunitUtil.intArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testintAscendingcomparatorSortHelper(JunitUtil.intArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testintAscendingcomparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintAscendingcomparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintAscendingcomparatorSortHelper(JunitUtil.intArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testintAscendingcomparatorSortHelper(JunitUtil.intArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testintAscendingcomparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintAscendingcomparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintAscendingcomparatorSortHelper(JunitUtil.intArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testintAscendingcomparatorSortHelper(JunitUtil.intArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testintAscendingcomparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintAscendingcomparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintAscendingcomparatorSortHelper(JunitUtil.intArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testintAscendingcomparatorSortHelper(JunitUtil.intArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testintAscendingcomparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintAscendingcomparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintAscendingcomparatorSortHelper(JunitUtil.intArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testintAscendingcomparatorSortHelper(JunitUtil.intArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testintAscendingcomparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintAscendingcomparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintAscendingcomparatorSortHelper(JunitUtil.intArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testintAscendingcomparatorSortHelper(JunitUtil.intArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testintAscendingcomparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintAscendingcomparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintAscendingcomparatorSortHelper(JunitUtil.intArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testintAscendingcomparatorSortHelper(JunitUtil.intArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testintAscendingcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintAscendingcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testintAscendingcomparatorSortHelper(JunitUtil.intArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testintAscendingcomparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintAscendingcomparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintAscendingcomparatorSortHelper(JunitUtil.intArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testintAscendingcomparatorSortHelper(JunitUtil.intArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testintAscendingcomparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintAscendingcomparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintAscendingcomparatorSortHelper(JunitUtil.intArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testintAscendingcomparatorSortHelper(JunitUtil.intArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testintAscendingcomparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintAscendingcomparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintAscendingcomparatorSortHelper(JunitUtil.intArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testintAscendingcomparatorSortHelper(JunitUtil.intArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testintAscendingcomparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintAscendingcomparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintAscendingcomparatorSortHelper(JunitUtil.intArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testintAscendingcomparatorSortHelper(JunitUtil.intArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testintAscendingcomparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintAscendingcomparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintAscendingcomparatorSortHelper(JunitUtil.intArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testintAscendingcomparatorSortHelper(JunitUtil.intArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
  private static void testintDescendingcomparatorSortHelper(JunitUtil.intArrayBuilder builder,int arrLength,long randSeed)
  {
    int[] golden=new int[arrLength];
    int[] test=new int[arrLength];
    Random rand=new Random(randSeed);
    var sorter=JunitUtil.getDescendingintComparator();
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
  @Test
  public void testintDescendingcomparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintDescendingcomparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintDescendingcomparatorSortHelper(JunitUtil.intArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testintDescendingcomparatorSortHelper(JunitUtil.intArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testintDescendingcomparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintDescendingcomparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintDescendingcomparatorSortHelper(JunitUtil.intArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testintDescendingcomparatorSortHelper(JunitUtil.intArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testintDescendingcomparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintDescendingcomparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintDescendingcomparatorSortHelper(JunitUtil.intArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testintDescendingcomparatorSortHelper(JunitUtil.intArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testintDescendingcomparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintDescendingcomparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintDescendingcomparatorSortHelper(JunitUtil.intArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testintDescendingcomparatorSortHelper(JunitUtil.intArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testintDescendingcomparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintDescendingcomparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintDescendingcomparatorSortHelper(JunitUtil.intArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testintDescendingcomparatorSortHelper(JunitUtil.intArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testintDescendingcomparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintDescendingcomparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintDescendingcomparatorSortHelper(JunitUtil.intArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testintDescendingcomparatorSortHelper(JunitUtil.intArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testintDescendingcomparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintDescendingcomparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintDescendingcomparatorSortHelper(JunitUtil.intArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testintDescendingcomparatorSortHelper(JunitUtil.intArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testintDescendingcomparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintDescendingcomparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintDescendingcomparatorSortHelper(JunitUtil.intArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testintDescendingcomparatorSortHelper(JunitUtil.intArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testintDescendingcomparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintDescendingcomparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintDescendingcomparatorSortHelper(JunitUtil.intArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testintDescendingcomparatorSortHelper(JunitUtil.intArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testintDescendingcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintDescendingcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testintDescendingcomparatorSortHelper(JunitUtil.intArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testintDescendingcomparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintDescendingcomparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintDescendingcomparatorSortHelper(JunitUtil.intArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testintDescendingcomparatorSortHelper(JunitUtil.intArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testintDescendingcomparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintDescendingcomparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintDescendingcomparatorSortHelper(JunitUtil.intArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testintDescendingcomparatorSortHelper(JunitUtil.intArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testintDescendingcomparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintDescendingcomparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintDescendingcomparatorSortHelper(JunitUtil.intArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testintDescendingcomparatorSortHelper(JunitUtil.intArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testintDescendingcomparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintDescendingcomparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintDescendingcomparatorSortHelper(JunitUtil.intArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testintDescendingcomparatorSortHelper(JunitUtil.intArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testintDescendingcomparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testintDescendingcomparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.intArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testintDescendingcomparatorSortHelper(JunitUtil.intArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testintDescendingcomparatorSortHelper(JunitUtil.intArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
  private static void testlongUnsortedcomparatorSortHelper(JunitUtil.longArrayBuilder builder,int arrLength,long randSeed)
  {
    long[] golden=new long[arrLength];
    long[] test=new long[arrLength];
    Random rand=new Random(randSeed);
    var sorter=JunitUtil.getUnsortedlongComparator();
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
  @Test
  public void testlongUnsortedcomparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongUnsortedcomparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongUnsortedcomparatorSortHelper(JunitUtil.longArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testlongUnsortedcomparatorSortHelper(JunitUtil.longArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testlongUnsortedcomparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongUnsortedcomparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongUnsortedcomparatorSortHelper(JunitUtil.longArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testlongUnsortedcomparatorSortHelper(JunitUtil.longArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testlongUnsortedcomparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongUnsortedcomparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongUnsortedcomparatorSortHelper(JunitUtil.longArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testlongUnsortedcomparatorSortHelper(JunitUtil.longArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testlongUnsortedcomparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongUnsortedcomparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongUnsortedcomparatorSortHelper(JunitUtil.longArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testlongUnsortedcomparatorSortHelper(JunitUtil.longArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testlongUnsortedcomparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongUnsortedcomparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongUnsortedcomparatorSortHelper(JunitUtil.longArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testlongUnsortedcomparatorSortHelper(JunitUtil.longArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testlongUnsortedcomparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongUnsortedcomparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongUnsortedcomparatorSortHelper(JunitUtil.longArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testlongUnsortedcomparatorSortHelper(JunitUtil.longArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testlongUnsortedcomparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongUnsortedcomparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongUnsortedcomparatorSortHelper(JunitUtil.longArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testlongUnsortedcomparatorSortHelper(JunitUtil.longArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testlongUnsortedcomparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongUnsortedcomparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongUnsortedcomparatorSortHelper(JunitUtil.longArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testlongUnsortedcomparatorSortHelper(JunitUtil.longArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testlongUnsortedcomparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongUnsortedcomparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongUnsortedcomparatorSortHelper(JunitUtil.longArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testlongUnsortedcomparatorSortHelper(JunitUtil.longArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testlongUnsortedcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongUnsortedcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testlongUnsortedcomparatorSortHelper(JunitUtil.longArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testlongUnsortedcomparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongUnsortedcomparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongUnsortedcomparatorSortHelper(JunitUtil.longArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testlongUnsortedcomparatorSortHelper(JunitUtil.longArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testlongUnsortedcomparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongUnsortedcomparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongUnsortedcomparatorSortHelper(JunitUtil.longArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testlongUnsortedcomparatorSortHelper(JunitUtil.longArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testlongUnsortedcomparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongUnsortedcomparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongUnsortedcomparatorSortHelper(JunitUtil.longArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testlongUnsortedcomparatorSortHelper(JunitUtil.longArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testlongUnsortedcomparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongUnsortedcomparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongUnsortedcomparatorSortHelper(JunitUtil.longArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testlongUnsortedcomparatorSortHelper(JunitUtil.longArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testlongUnsortedcomparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongUnsortedcomparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongUnsortedcomparatorSortHelper(JunitUtil.longArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testlongUnsortedcomparatorSortHelper(JunitUtil.longArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
  private static void testlongAscendingcomparatorSortHelper(JunitUtil.longArrayBuilder builder,int arrLength,long randSeed)
  {
    long[] golden=new long[arrLength];
    long[] test=new long[arrLength];
    Random rand=new Random(randSeed);
    var sorter=JunitUtil.getAscendinglongComparator();
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
  @Test
  public void testlongAscendingcomparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongAscendingcomparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongAscendingcomparatorSortHelper(JunitUtil.longArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testlongAscendingcomparatorSortHelper(JunitUtil.longArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testlongAscendingcomparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongAscendingcomparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongAscendingcomparatorSortHelper(JunitUtil.longArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testlongAscendingcomparatorSortHelper(JunitUtil.longArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testlongAscendingcomparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongAscendingcomparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongAscendingcomparatorSortHelper(JunitUtil.longArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testlongAscendingcomparatorSortHelper(JunitUtil.longArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testlongAscendingcomparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongAscendingcomparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongAscendingcomparatorSortHelper(JunitUtil.longArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testlongAscendingcomparatorSortHelper(JunitUtil.longArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testlongAscendingcomparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongAscendingcomparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongAscendingcomparatorSortHelper(JunitUtil.longArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testlongAscendingcomparatorSortHelper(JunitUtil.longArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testlongAscendingcomparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongAscendingcomparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongAscendingcomparatorSortHelper(JunitUtil.longArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testlongAscendingcomparatorSortHelper(JunitUtil.longArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testlongAscendingcomparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongAscendingcomparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongAscendingcomparatorSortHelper(JunitUtil.longArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testlongAscendingcomparatorSortHelper(JunitUtil.longArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testlongAscendingcomparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongAscendingcomparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongAscendingcomparatorSortHelper(JunitUtil.longArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testlongAscendingcomparatorSortHelper(JunitUtil.longArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testlongAscendingcomparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongAscendingcomparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongAscendingcomparatorSortHelper(JunitUtil.longArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testlongAscendingcomparatorSortHelper(JunitUtil.longArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testlongAscendingcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongAscendingcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testlongAscendingcomparatorSortHelper(JunitUtil.longArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testlongAscendingcomparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongAscendingcomparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongAscendingcomparatorSortHelper(JunitUtil.longArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testlongAscendingcomparatorSortHelper(JunitUtil.longArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testlongAscendingcomparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongAscendingcomparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongAscendingcomparatorSortHelper(JunitUtil.longArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testlongAscendingcomparatorSortHelper(JunitUtil.longArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testlongAscendingcomparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongAscendingcomparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongAscendingcomparatorSortHelper(JunitUtil.longArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testlongAscendingcomparatorSortHelper(JunitUtil.longArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testlongAscendingcomparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongAscendingcomparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongAscendingcomparatorSortHelper(JunitUtil.longArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testlongAscendingcomparatorSortHelper(JunitUtil.longArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testlongAscendingcomparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongAscendingcomparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongAscendingcomparatorSortHelper(JunitUtil.longArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testlongAscendingcomparatorSortHelper(JunitUtil.longArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
  private static void testlongDescendingcomparatorSortHelper(JunitUtil.longArrayBuilder builder,int arrLength,long randSeed)
  {
    long[] golden=new long[arrLength];
    long[] test=new long[arrLength];
    Random rand=new Random(randSeed);
    var sorter=JunitUtil.getDescendinglongComparator();
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
  @Test
  public void testlongDescendingcomparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongDescendingcomparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongDescendingcomparatorSortHelper(JunitUtil.longArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testlongDescendingcomparatorSortHelper(JunitUtil.longArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testlongDescendingcomparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongDescendingcomparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongDescendingcomparatorSortHelper(JunitUtil.longArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testlongDescendingcomparatorSortHelper(JunitUtil.longArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testlongDescendingcomparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongDescendingcomparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongDescendingcomparatorSortHelper(JunitUtil.longArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testlongDescendingcomparatorSortHelper(JunitUtil.longArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testlongDescendingcomparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongDescendingcomparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongDescendingcomparatorSortHelper(JunitUtil.longArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testlongDescendingcomparatorSortHelper(JunitUtil.longArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testlongDescendingcomparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongDescendingcomparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongDescendingcomparatorSortHelper(JunitUtil.longArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testlongDescendingcomparatorSortHelper(JunitUtil.longArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testlongDescendingcomparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongDescendingcomparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongDescendingcomparatorSortHelper(JunitUtil.longArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testlongDescendingcomparatorSortHelper(JunitUtil.longArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testlongDescendingcomparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongDescendingcomparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongDescendingcomparatorSortHelper(JunitUtil.longArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testlongDescendingcomparatorSortHelper(JunitUtil.longArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testlongDescendingcomparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongDescendingcomparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongDescendingcomparatorSortHelper(JunitUtil.longArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testlongDescendingcomparatorSortHelper(JunitUtil.longArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testlongDescendingcomparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongDescendingcomparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongDescendingcomparatorSortHelper(JunitUtil.longArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testlongDescendingcomparatorSortHelper(JunitUtil.longArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testlongDescendingcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongDescendingcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testlongDescendingcomparatorSortHelper(JunitUtil.longArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testlongDescendingcomparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongDescendingcomparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongDescendingcomparatorSortHelper(JunitUtil.longArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testlongDescendingcomparatorSortHelper(JunitUtil.longArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testlongDescendingcomparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongDescendingcomparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongDescendingcomparatorSortHelper(JunitUtil.longArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testlongDescendingcomparatorSortHelper(JunitUtil.longArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testlongDescendingcomparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongDescendingcomparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongDescendingcomparatorSortHelper(JunitUtil.longArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testlongDescendingcomparatorSortHelper(JunitUtil.longArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testlongDescendingcomparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongDescendingcomparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongDescendingcomparatorSortHelper(JunitUtil.longArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testlongDescendingcomparatorSortHelper(JunitUtil.longArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testlongDescendingcomparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testlongDescendingcomparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.longArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testlongDescendingcomparatorSortHelper(JunitUtil.longArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testlongDescendingcomparatorSortHelper(JunitUtil.longArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
  private static void testfloatUnsortedcomparatorSortHelper(JunitUtil.floatArrayBuilder builder,int arrLength,long randSeed)
  {
    float[] golden=new float[arrLength];
    float[] test=new float[arrLength];
    Random rand=new Random(randSeed);
    var sorter=JunitUtil.getUnsortedfloatComparator();
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
  @Test
  public void testfloatUnsortedcomparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatUnsortedcomparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatUnsortedcomparatorSortHelper(JunitUtil.floatArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testfloatUnsortedcomparatorSortHelper(JunitUtil.floatArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatUnsortedcomparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatUnsortedcomparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatUnsortedcomparatorSortHelper(JunitUtil.floatArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testfloatUnsortedcomparatorSortHelper(JunitUtil.floatArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatUnsortedcomparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatUnsortedcomparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatUnsortedcomparatorSortHelper(JunitUtil.floatArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testfloatUnsortedcomparatorSortHelper(JunitUtil.floatArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatUnsortedcomparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatUnsortedcomparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatUnsortedcomparatorSortHelper(JunitUtil.floatArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testfloatUnsortedcomparatorSortHelper(JunitUtil.floatArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatUnsortedcomparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatUnsortedcomparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatUnsortedcomparatorSortHelper(JunitUtil.floatArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testfloatUnsortedcomparatorSortHelper(JunitUtil.floatArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatUnsortedcomparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatUnsortedcomparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatUnsortedcomparatorSortHelper(JunitUtil.floatArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testfloatUnsortedcomparatorSortHelper(JunitUtil.floatArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatUnsortedcomparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatUnsortedcomparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatUnsortedcomparatorSortHelper(JunitUtil.floatArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testfloatUnsortedcomparatorSortHelper(JunitUtil.floatArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatUnsortedcomparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatUnsortedcomparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatUnsortedcomparatorSortHelper(JunitUtil.floatArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testfloatUnsortedcomparatorSortHelper(JunitUtil.floatArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatUnsortedcomparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatUnsortedcomparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatUnsortedcomparatorSortHelper(JunitUtil.floatArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testfloatUnsortedcomparatorSortHelper(JunitUtil.floatArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatUnsortedcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatUnsortedcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testfloatUnsortedcomparatorSortHelper(JunitUtil.floatArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatUnsortedcomparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatUnsortedcomparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatUnsortedcomparatorSortHelper(JunitUtil.floatArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testfloatUnsortedcomparatorSortHelper(JunitUtil.floatArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatUnsortedcomparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatUnsortedcomparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatUnsortedcomparatorSortHelper(JunitUtil.floatArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testfloatUnsortedcomparatorSortHelper(JunitUtil.floatArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatUnsortedcomparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatUnsortedcomparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatUnsortedcomparatorSortHelper(JunitUtil.floatArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testfloatUnsortedcomparatorSortHelper(JunitUtil.floatArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatUnsortedcomparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatUnsortedcomparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatUnsortedcomparatorSortHelper(JunitUtil.floatArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testfloatUnsortedcomparatorSortHelper(JunitUtil.floatArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatUnsortedcomparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatUnsortedcomparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatUnsortedcomparatorSortHelper(JunitUtil.floatArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testfloatUnsortedcomparatorSortHelper(JunitUtil.floatArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatUnsortedcomparatorSortWithNaNsAndZerosArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatUnsortedcomparatorSortWithNaNsAndZerosArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.WithNaNsAndZeros.isRandomized())
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
     else
     {
       testfloatUnsortedcomparatorSortHelper(JunitUtil.floatArrayBuilder.WithNaNsAndZeros,arrLength,0);
     }
    }
  }
  private static void testfloatAscendingcomparatorSortHelper(JunitUtil.floatArrayBuilder builder,int arrLength,long randSeed)
  {
    float[] golden=new float[arrLength];
    float[] test=new float[arrLength];
    Random rand=new Random(randSeed);
    var sorter=JunitUtil.getAscendingfloatComparator();
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
  @Test
  public void testfloatAscendingcomparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatAscendingcomparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatAscendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testfloatAscendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatAscendingcomparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatAscendingcomparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatAscendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testfloatAscendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatAscendingcomparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatAscendingcomparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatAscendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testfloatAscendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatAscendingcomparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatAscendingcomparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatAscendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testfloatAscendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatAscendingcomparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatAscendingcomparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatAscendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testfloatAscendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatAscendingcomparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatAscendingcomparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatAscendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testfloatAscendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatAscendingcomparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatAscendingcomparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatAscendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testfloatAscendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatAscendingcomparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatAscendingcomparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatAscendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testfloatAscendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatAscendingcomparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatAscendingcomparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatAscendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testfloatAscendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatAscendingcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatAscendingcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testfloatAscendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatAscendingcomparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatAscendingcomparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatAscendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testfloatAscendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatAscendingcomparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatAscendingcomparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatAscendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testfloatAscendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatAscendingcomparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatAscendingcomparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatAscendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testfloatAscendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatAscendingcomparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatAscendingcomparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatAscendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testfloatAscendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatAscendingcomparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatAscendingcomparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatAscendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testfloatAscendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatAscendingcomparatorSortWithNaNsAndZerosArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatAscendingcomparatorSortWithNaNsAndZerosArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.WithNaNsAndZeros.isRandomized())
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
     else
     {
       testfloatAscendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.WithNaNsAndZeros,arrLength,0);
     }
    }
  }
  private static void testfloatDescendingcomparatorSortHelper(JunitUtil.floatArrayBuilder builder,int arrLength,long randSeed)
  {
    float[] golden=new float[arrLength];
    float[] test=new float[arrLength];
    Random rand=new Random(randSeed);
    var sorter=JunitUtil.getDescendingfloatComparator();
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
  @Test
  public void testfloatDescendingcomparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatDescendingcomparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatDescendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testfloatDescendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatDescendingcomparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatDescendingcomparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatDescendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testfloatDescendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatDescendingcomparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatDescendingcomparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatDescendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testfloatDescendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatDescendingcomparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatDescendingcomparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatDescendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testfloatDescendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatDescendingcomparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatDescendingcomparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatDescendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testfloatDescendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatDescendingcomparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatDescendingcomparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatDescendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testfloatDescendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatDescendingcomparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatDescendingcomparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatDescendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testfloatDescendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatDescendingcomparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatDescendingcomparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatDescendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testfloatDescendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatDescendingcomparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatDescendingcomparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatDescendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testfloatDescendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatDescendingcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatDescendingcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testfloatDescendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatDescendingcomparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatDescendingcomparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatDescendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testfloatDescendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatDescendingcomparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatDescendingcomparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatDescendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testfloatDescendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatDescendingcomparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatDescendingcomparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatDescendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testfloatDescendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatDescendingcomparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatDescendingcomparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatDescendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testfloatDescendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatDescendingcomparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatDescendingcomparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testfloatDescendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testfloatDescendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
  @Test
  public void testfloatDescendingcomparatorSortWithNaNsAndZerosArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testfloatDescendingcomparatorSortWithNaNsAndZerosArrayBuilder length = "+arrLength);
     if(JunitUtil.floatArrayBuilder.WithNaNsAndZeros.isRandomized())
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
     else
     {
       testfloatDescendingcomparatorSortHelper(JunitUtil.floatArrayBuilder.WithNaNsAndZeros,arrLength,0);
     }
    }
  }
  private static void testdoubleUnsortedcomparatorSortHelper(JunitUtil.doubleArrayBuilder builder,int arrLength,long randSeed)
  {
    double[] golden=new double[arrLength];
    double[] test=new double[arrLength];
    Random rand=new Random(randSeed);
    var sorter=JunitUtil.getUnsorteddoubleComparator();
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
  @Test
  public void testdoubleUnsortedcomparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleUnsortedcomparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleUnsortedcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleUnsortedcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleUnsortedcomparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleUnsortedcomparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleUnsortedcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleUnsortedcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleUnsortedcomparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleUnsortedcomparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleUnsortedcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleUnsortedcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleUnsortedcomparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleUnsortedcomparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleUnsortedcomparatorSortHelper(JunitUtil.doubleArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleUnsortedcomparatorSortHelper(JunitUtil.doubleArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleUnsortedcomparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleUnsortedcomparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleUnsortedcomparatorSortHelper(JunitUtil.doubleArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleUnsortedcomparatorSortHelper(JunitUtil.doubleArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleUnsortedcomparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleUnsortedcomparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleUnsortedcomparatorSortHelper(JunitUtil.doubleArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleUnsortedcomparatorSortHelper(JunitUtil.doubleArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleUnsortedcomparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleUnsortedcomparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleUnsortedcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleUnsortedcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleUnsortedcomparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleUnsortedcomparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleUnsortedcomparatorSortHelper(JunitUtil.doubleArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleUnsortedcomparatorSortHelper(JunitUtil.doubleArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleUnsortedcomparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleUnsortedcomparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleUnsortedcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleUnsortedcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleUnsortedcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleUnsortedcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testdoubleUnsortedcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleUnsortedcomparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleUnsortedcomparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleUnsortedcomparatorSortHelper(JunitUtil.doubleArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleUnsortedcomparatorSortHelper(JunitUtil.doubleArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleUnsortedcomparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleUnsortedcomparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleUnsortedcomparatorSortHelper(JunitUtil.doubleArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleUnsortedcomparatorSortHelper(JunitUtil.doubleArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleUnsortedcomparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleUnsortedcomparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleUnsortedcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleUnsortedcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleUnsortedcomparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleUnsortedcomparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleUnsortedcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleUnsortedcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleUnsortedcomparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleUnsortedcomparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleUnsortedcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleUnsortedcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleUnsortedcomparatorSortWithNaNsAndZerosArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleUnsortedcomparatorSortWithNaNsAndZerosArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.WithNaNsAndZeros.isRandomized())
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
     else
     {
       testdoubleUnsortedcomparatorSortHelper(JunitUtil.doubleArrayBuilder.WithNaNsAndZeros,arrLength,0);
     }
    }
  }
  private static void testdoubleAscendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder builder,int arrLength,long randSeed)
  {
    double[] golden=new double[arrLength];
    double[] test=new double[arrLength];
    Random rand=new Random(randSeed);
    var sorter=JunitUtil.getAscendingdoubleComparator();
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
  @Test
  public void testdoubleAscendingcomparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleAscendingcomparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleAscendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleAscendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleAscendingcomparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleAscendingcomparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleAscendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleAscendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleAscendingcomparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleAscendingcomparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleAscendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleAscendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleAscendingcomparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleAscendingcomparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleAscendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleAscendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleAscendingcomparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleAscendingcomparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleAscendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleAscendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleAscendingcomparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleAscendingcomparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleAscendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleAscendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleAscendingcomparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleAscendingcomparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleAscendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleAscendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleAscendingcomparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleAscendingcomparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleAscendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleAscendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleAscendingcomparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleAscendingcomparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleAscendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleAscendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleAscendingcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleAscendingcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testdoubleAscendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleAscendingcomparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleAscendingcomparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleAscendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleAscendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleAscendingcomparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleAscendingcomparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleAscendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleAscendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleAscendingcomparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleAscendingcomparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleAscendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleAscendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleAscendingcomparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleAscendingcomparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleAscendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleAscendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleAscendingcomparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleAscendingcomparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleAscendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleAscendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleAscendingcomparatorSortWithNaNsAndZerosArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleAscendingcomparatorSortWithNaNsAndZerosArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.WithNaNsAndZeros.isRandomized())
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
     else
     {
       testdoubleAscendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.WithNaNsAndZeros,arrLength,0);
     }
    }
  }
  private static void testdoubleDescendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder builder,int arrLength,long randSeed)
  {
    double[] golden=new double[arrLength];
    double[] test=new double[arrLength];
    Random rand=new Random(randSeed);
    var sorter=JunitUtil.getDescendingdoubleComparator();
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
  @Test
  public void testdoubleDescendingcomparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleDescendingcomparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleDescendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleDescendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleDescendingcomparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleDescendingcomparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleDescendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleDescendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleDescendingcomparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleDescendingcomparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleDescendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleDescendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleDescendingcomparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleDescendingcomparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleDescendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleDescendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleDescendingcomparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleDescendingcomparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleDescendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleDescendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleDescendingcomparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleDescendingcomparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleDescendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleDescendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleDescendingcomparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleDescendingcomparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleDescendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleDescendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleDescendingcomparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleDescendingcomparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleDescendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleDescendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleDescendingcomparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleDescendingcomparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleDescendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleDescendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleDescendingcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleDescendingcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testdoubleDescendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleDescendingcomparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleDescendingcomparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleDescendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleDescendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleDescendingcomparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleDescendingcomparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleDescendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleDescendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleDescendingcomparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleDescendingcomparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleDescendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleDescendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleDescendingcomparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleDescendingcomparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleDescendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleDescendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleDescendingcomparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleDescendingcomparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testdoubleDescendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testdoubleDescendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
  @Test
  public void testdoubleDescendingcomparatorSortWithNaNsAndZerosArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testdoubleDescendingcomparatorSortWithNaNsAndZerosArrayBuilder length = "+arrLength);
     if(JunitUtil.doubleArrayBuilder.WithNaNsAndZeros.isRandomized())
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
     else
     {
       testdoubleDescendingcomparatorSortHelper(JunitUtil.doubleArrayBuilder.WithNaNsAndZeros,arrLength,0);
     }
    }
  }
  private static void testIntegerUnsortedcomparatorSortHelper(JunitUtil.IntegerArrayBuilder builder,int arrLength,long randSeed)
  {
    Integer[] golden=new Integer[arrLength];
    Integer[] test=new Integer[arrLength];
    Random rand=new Random(randSeed);
    var sorter=JunitUtil.getUnsortedIntegerComparator();
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
  @Test
  public void testIntegerUnsortedcomparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerUnsortedcomparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerUnsortedcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerUnsortedcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerUnsortedcomparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerUnsortedcomparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerUnsortedcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerUnsortedcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerUnsortedcomparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerUnsortedcomparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerUnsortedcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerUnsortedcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerUnsortedcomparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerUnsortedcomparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerUnsortedcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerUnsortedcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerUnsortedcomparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerUnsortedcomparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerUnsortedcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerUnsortedcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerUnsortedcomparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerUnsortedcomparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerUnsortedcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerUnsortedcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerUnsortedcomparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerUnsortedcomparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerUnsortedcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerUnsortedcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerUnsortedcomparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerUnsortedcomparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerUnsortedcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerUnsortedcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerUnsortedcomparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerUnsortedcomparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerUnsortedcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerUnsortedcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerUnsortedcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerUnsortedcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testIntegerUnsortedcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerUnsortedcomparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerUnsortedcomparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerUnsortedcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerUnsortedcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerUnsortedcomparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerUnsortedcomparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerUnsortedcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerUnsortedcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerUnsortedcomparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerUnsortedcomparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerUnsortedcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerUnsortedcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerUnsortedcomparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerUnsortedcomparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerUnsortedcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerUnsortedcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerUnsortedcomparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerUnsortedcomparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerUnsortedcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerUnsortedcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
  private static void testIntegerAscendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder builder,int arrLength,long randSeed)
  {
    Integer[] golden=new Integer[arrLength];
    Integer[] test=new Integer[arrLength];
    Random rand=new Random(randSeed);
    var sorter=JunitUtil.getAscendingIntegerComparator();
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
  @Test
  public void testIntegerAscendingcomparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerAscendingcomparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerAscendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerAscendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerAscendingcomparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerAscendingcomparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerAscendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerAscendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerAscendingcomparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerAscendingcomparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerAscendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerAscendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerAscendingcomparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerAscendingcomparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerAscendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerAscendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerAscendingcomparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerAscendingcomparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerAscendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerAscendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerAscendingcomparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerAscendingcomparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerAscendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerAscendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerAscendingcomparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerAscendingcomparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerAscendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerAscendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerAscendingcomparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerAscendingcomparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerAscendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerAscendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerAscendingcomparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerAscendingcomparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerAscendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerAscendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerAscendingcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerAscendingcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testIntegerAscendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerAscendingcomparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerAscendingcomparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerAscendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerAscendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerAscendingcomparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerAscendingcomparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerAscendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerAscendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerAscendingcomparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerAscendingcomparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerAscendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerAscendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerAscendingcomparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerAscendingcomparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerAscendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerAscendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerAscendingcomparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerAscendingcomparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerAscendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerAscendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
  private static void testIntegerDescendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder builder,int arrLength,long randSeed)
  {
    Integer[] golden=new Integer[arrLength];
    Integer[] test=new Integer[arrLength];
    Random rand=new Random(randSeed);
    var sorter=JunitUtil.getDescendingIntegerComparator();
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
  @Test
  public void testIntegerDescendingcomparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerDescendingcomparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerDescendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerDescendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerDescendingcomparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerDescendingcomparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerDescendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerDescendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerDescendingcomparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerDescendingcomparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerDescendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerDescendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerDescendingcomparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerDescendingcomparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerDescendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerDescendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerDescendingcomparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerDescendingcomparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerDescendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerDescendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerDescendingcomparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerDescendingcomparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerDescendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerDescendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerDescendingcomparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerDescendingcomparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerDescendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerDescendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerDescendingcomparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerDescendingcomparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerDescendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerDescendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerDescendingcomparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerDescendingcomparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerDescendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerDescendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerDescendingcomparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerDescendingcomparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testIntegerDescendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerDescendingcomparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerDescendingcomparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerDescendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerDescendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerDescendingcomparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerDescendingcomparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerDescendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerDescendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerDescendingcomparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerDescendingcomparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerDescendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerDescendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerDescendingcomparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerDescendingcomparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerDescendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerDescendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerDescendingcomparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerDescendingcomparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerDescendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerDescendingcomparatorSortHelper(JunitUtil.IntegerArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
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
  @Test
  public void testIntegerAscendingnonComparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerAscendingnonComparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerAscendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerAscendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerAscendingnonComparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerAscendingnonComparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerAscendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerAscendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerAscendingnonComparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerAscendingnonComparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerAscendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerAscendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerAscendingnonComparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerAscendingnonComparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerAscendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerAscendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerAscendingnonComparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerAscendingnonComparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerAscendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerAscendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerAscendingnonComparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerAscendingnonComparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerAscendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerAscendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerAscendingnonComparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerAscendingnonComparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerAscendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerAscendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerAscendingnonComparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerAscendingnonComparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerAscendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerAscendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerAscendingnonComparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerAscendingnonComparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerAscendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerAscendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerAscendingnonComparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerAscendingnonComparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testIntegerAscendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerAscendingnonComparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerAscendingnonComparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerAscendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerAscendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerAscendingnonComparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerAscendingnonComparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerAscendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerAscendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerAscendingnonComparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerAscendingnonComparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerAscendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerAscendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerAscendingnonComparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerAscendingnonComparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerAscendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerAscendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerAscendingnonComparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerAscendingnonComparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerAscendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerAscendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
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
  @Test
  public void testIntegerDescendingnonComparatorSortRandomizedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerDescendingnonComparatorSortRandomizedArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Randomized.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerDescendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.Randomized,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerDescendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.Randomized,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerDescendingnonComparatorSortAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerDescendingnonComparatorSortAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Ascending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerDescendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.Ascending,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerDescendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.Ascending,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerDescendingnonComparatorSortDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerDescendingnonComparatorSortDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Descending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerDescendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.Descending,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerDescendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.Descending,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerDescendingnonComparatorSortAllEqualsArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerDescendingnonComparatorSortAllEqualsArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.AllEquals.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerDescendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.AllEquals,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerDescendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.AllEquals,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerDescendingnonComparatorSortMergeAscendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerDescendingnonComparatorSortMergeAscendingArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.MergeAscending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerDescendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.MergeAscending,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerDescendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.MergeAscending,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerDescendingnonComparatorSortMergeDescendingArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerDescendingnonComparatorSortMergeDescendingArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.MergeDescending.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerDescendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.MergeDescending,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerDescendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.MergeDescending,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerDescendingnonComparatorSortSawArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerDescendingnonComparatorSortSawArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Saw.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerDescendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.Saw,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerDescendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.Saw,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerDescendingnonComparatorSortSortedRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerDescendingnonComparatorSortSortedRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.SortedRepeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerDescendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.SortedRepeated,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerDescendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.SortedRepeated,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerDescendingnonComparatorSortRepeatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerDescendingnonComparatorSortRepeatedArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Repeated.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerDescendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.Repeated,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerDescendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.Repeated,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerDescendingnonComparatorSortDuplicatedArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerDescendingnonComparatorSortDuplicatedArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Duplicated.isRandomized())
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
     else
     {
       testIntegerDescendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.Duplicated,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerDescendingnonComparatorSortSortedOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerDescendingnonComparatorSortSortedOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.SortedOrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerDescendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.SortedOrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerDescendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.SortedOrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerDescendingnonComparatorSortOrganPipesArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerDescendingnonComparatorSortOrganPipesArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.OrganPipes.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerDescendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.OrganPipes,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerDescendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.OrganPipes,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerDescendingnonComparatorSortStaggerArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerDescendingnonComparatorSortStaggerArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Stagger.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerDescendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.Stagger,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerDescendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.Stagger,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerDescendingnonComparatorSortPlateauArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerDescendingnonComparatorSortPlateauArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Plateau.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerDescendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.Plateau,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerDescendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.Plateau,arrLength,0);
     }
    }
  }
  @Test
  public void testIntegerDescendingnonComparatorSortShuffleArrayBuilder()
  {
    for(int i=0;i<lengths.length;++i)
    {
      final int arrLength=lengths[i];
      System.out.println("testIntegerDescendingnonComparatorSortShuffleArrayBuilder length = "+arrLength);
     if(JunitUtil.IntegerArrayBuilder.Shuffle.isRandomized())
     {
        var stream=LongStream.of(randSeeds);
       if(false)
       {
         stream=stream.parallel();
       }
       stream.forEach(randSeed->
       {
         testIntegerDescendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.Shuffle,arrLength,randSeed);
       });
     }
     else
     {
       testIntegerDescendingnonComparatorSortHelper(JunitUtil.IntegerArrayBuilder.Shuffle,arrLength,0);
     }
    }
  }
  */
}
