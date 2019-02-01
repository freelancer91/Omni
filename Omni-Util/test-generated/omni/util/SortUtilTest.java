package omni.util;
import org.junit.Assert;
import org.junit.Test;
import java.util.Random;
import java.util.Arrays;
import java.util.Objects;
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
  */
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
  /*//TODO uncomment when they fix the module bug in eclipse
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
  */
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
  /*//TODO uncomment when they fix the module bug in eclipse
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
  */
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
  /*//TODO uncomment when they fix the module bug in eclipse
  private static void isSortedAscending(String[] arr,String[] copy)
  {
    Arrays.sort(copy,0,copy.length);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(Objects.equals(arr[i],copy[i]));
    }
  }
  */
  private static void isSortedAscending(boolean[] arr,boolean[] copy)
  {
    Boolean[] boxedCopy=new Boolean[copy.length];
    ArrCopy.uncheckedCopy(copy,0,boxedCopy,0,copy.length);
    Arrays.sort(boxedCopy,0,copy.length);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void isSortedAscending(byte[] arr,byte[] copy)
  {
    Arrays.sort(copy,0,copy.length);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void isSortedAscending(char[] arr,char[] copy)
  {
    Arrays.sort(copy,0,copy.length);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void isSortedAscending(short[] arr,short[] copy)
  {
    Arrays.sort(copy,0,copy.length);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void isSortedAscending(int[] arr,int[] copy)
  {
    Arrays.sort(copy,0,copy.length);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(Objects.equals(arr[i],copy[i]));
    }
  }
  private static void isSortedAscending(long[] arr,long[] copy)
  {
    Arrays.sort(copy,0,copy.length);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(Objects.equals(arr[i],copy[i]));
    }
  }
  private static void isSortedAscending(float[] arr,float[] copy)
  {
    Arrays.sort(copy,0,copy.length);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(TypeUtil.floatEquals(arr[i],copy[i]));
    }
  }
  private static void isSortedAscending(double[] arr,double[] copy)
  {
    Arrays.sort(copy,0,copy.length);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(TypeUtil.doubleEquals(arr[i],copy[i]));
    }
  }
  /*//TODO uncomment when they fix the module bug in eclipse
  private static void isSortedDescending(String[] arr,String[] copy)
  {
    Arrays.sort(copy,0,copy.length);
    OmniArray.OfRef.reverseRange(copy,0,copy.length-1);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(Objects.equals(arr[i],copy[i]));
    }
  }
  */
  private static void isSortedDescending(boolean[] arr,boolean[] copy)
  {
    Boolean[] boxedCopy=new Boolean[copy.length];
    ArrCopy.uncheckedCopy(copy,0,boxedCopy,0,copy.length);
    Arrays.sort(boxedCopy,0,copy.length);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    OmniArray.OfBoolean.reverseRange(copy,0,copy.length-1);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void isSortedDescending(byte[] arr,byte[] copy)
  {
    Arrays.sort(copy,0,copy.length);
    OmniArray.OfByte.reverseRange(copy,0,copy.length-1);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void isSortedDescending(char[] arr,char[] copy)
  {
    Arrays.sort(copy,0,copy.length);
    OmniArray.OfChar.reverseRange(copy,0,copy.length-1);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void isSortedDescending(short[] arr,short[] copy)
  {
    Arrays.sort(copy,0,copy.length);
    OmniArray.OfShort.reverseRange(copy,0,copy.length-1);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void isSortedDescending(int[] arr,int[] copy)
  {
    Arrays.sort(copy,0,copy.length);
    OmniArray.OfInt.reverseRange(copy,0,copy.length-1);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(Objects.equals(arr[i],copy[i]));
    }
  }
  private static void isSortedDescending(long[] arr,long[] copy)
  {
    Arrays.sort(copy,0,copy.length);
    OmniArray.OfLong.reverseRange(copy,0,copy.length-1);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(Objects.equals(arr[i],copy[i]));
    }
  }
  private static void isSortedDescending(float[] arr,float[] copy)
  {
    Arrays.sort(copy,0,copy.length);
    OmniArray.OfFloat.reverseRange(copy,0,copy.length-1);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(TypeUtil.floatEquals(arr[i],copy[i]));
    }
  }
  private static void isSortedDescending(double[] arr,double[] copy)
  {
    Arrays.sort(copy,0,copy.length);
    OmniArray.OfDouble.reverseRange(copy,0,copy.length-1);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(TypeUtil.doubleEquals(arr[i],copy[i]));
    }
  }
  /*//TODO uncomment when they fix the module bug in eclipse
  private static void isSorted(String[] arr,String[] copy,Comparator<? super String> sorter)
  {
    Arrays.sort(copy,0,copy.length,sorter);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(Objects.equals(arr[i],copy[i]));
    }
  }
  */
  private static void isSorted(boolean[] arr,boolean[] copy,BooleanComparator sorter)
  {
    Boolean[] boxedCopy=new Boolean[copy.length];
    ArrCopy.uncheckedCopy(copy,0,boxedCopy,0,copy.length);
    Arrays.sort(boxedCopy,0,copy.length,sorter::compare);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void isSorted(byte[] arr,byte[] copy,ByteComparator sorter)
  {
    Byte[] boxedCopy=new Byte[copy.length];
    ArrCopy.uncheckedCopy(copy,0,boxedCopy,0,copy.length);
    Arrays.sort(boxedCopy,0,copy.length,sorter::compare);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void isSorted(char[] arr,char[] copy,CharComparator sorter)
  {
    Character[] boxedCopy=new Character[copy.length];
    ArrCopy.uncheckedCopy(copy,0,boxedCopy,0,copy.length);
    Arrays.sort(boxedCopy,0,copy.length,sorter::compare);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void isSorted(short[] arr,short[] copy,ShortComparator sorter)
  {
    Short[] boxedCopy=new Short[copy.length];
    ArrCopy.uncheckedCopy(copy,0,boxedCopy,0,copy.length);
    Arrays.sort(boxedCopy,0,copy.length,sorter::compare);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void isSorted(int[] arr,int[] copy,IntBinaryOperator sorter)
  {
    Integer[] boxedCopy=new Integer[copy.length];
    ArrCopy.uncheckedCopy(copy,0,boxedCopy,0,copy.length);
    Arrays.sort(boxedCopy,0,copy.length,sorter::applyAsInt);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(Objects.equals(arr[i],copy[i]));
    }
  }
  private static void isSorted(long[] arr,long[] copy,LongComparator sorter)
  {
    Long[] boxedCopy=new Long[copy.length];
    ArrCopy.uncheckedCopy(copy,0,boxedCopy,0,copy.length);
    Arrays.sort(boxedCopy,0,copy.length,sorter::compare);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(Objects.equals(arr[i],copy[i]));
    }
  }
  private static void isSorted(float[] arr,float[] copy,FloatComparator sorter)
  {
    Float[] boxedCopy=new Float[copy.length];
    ArrCopy.uncheckedCopy(copy,0,boxedCopy,0,copy.length);
    Arrays.sort(boxedCopy,0,copy.length,sorter::compare);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(TypeUtil.floatEquals(arr[i],copy[i]));
    }
  }
  private static void isSorted(double[] arr,double[] copy,DoubleComparator sorter)
  {
    Double[] boxedCopy=new Double[copy.length];
    ArrCopy.uncheckedCopy(copy,0,boxedCopy,0,copy.length);
    Arrays.sort(boxedCopy,0,copy.length,sorter::compare);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(TypeUtil.doubleEquals(arr[i],copy[i]));
    }
  }
  private static void testWithoutComparatorAscendingbooleanHelper(BooleanSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      boolean[] arr;
      if(presorted)
      {
        arr=JunitUtil.getAscendingbooleanArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedbooleanArray(supplier,arrLength);
      }
      boolean[] copy=new boolean[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      SortUtil.uncheckedsort(arr,0,arrLength-1);
      isSortedAscending(arr,copy);
    }
  }
  private static void testWithoutComparatorAscendingbyteHelper(ByteSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      byte[] arr;
      if(presorted)
      {
        arr=JunitUtil.getAscendingbyteArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedbyteArray(supplier,arrLength);
      }
      byte[] copy=new byte[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      SortUtil.uncheckedsort(arr,0,arrLength-1);
      isSortedAscending(arr,copy);
    }
  }
  private static void testWithoutComparatorAscendingcharHelper(CharSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      char[] arr;
      if(presorted)
      {
        arr=JunitUtil.getAscendingcharArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedcharArray(supplier,arrLength);
      }
      char[] copy=new char[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      SortUtil.uncheckedsort(arr,0,arrLength-1);
      isSortedAscending(arr,copy);
    }
  }
  private static void testWithoutComparatorAscendingshortHelper(ShortSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      short[] arr;
      if(presorted)
      {
        arr=JunitUtil.getAscendingshortArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedshortArray(supplier,arrLength);
      }
      short[] copy=new short[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      SortUtil.uncheckedsort(arr,0,arrLength-1);
      isSortedAscending(arr,copy);
    }
  }
  private static void testWithoutComparatorAscendingintHelper(IntSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      int[] arr;
      if(presorted)
      {
        arr=JunitUtil.getAscendingintArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedintArray(supplier,arrLength);
      }
      int[] copy=new int[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      SortUtil.uncheckedsort(arr,0,arrLength-1);
      isSortedAscending(arr,copy);
    }
  }
  private static void testWithoutComparatorAscendinglongHelper(LongSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      long[] arr;
      if(presorted)
      {
        arr=JunitUtil.getAscendinglongArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedlongArray(supplier,arrLength);
      }
      long[] copy=new long[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      SortUtil.uncheckedsort(arr,0,arrLength-1);
      isSortedAscending(arr,copy);
    }
  }
  private static void testWithoutComparatorAscendingfloatHelper(FloatSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      float[] arr;
      if(presorted)
      {
        arr=JunitUtil.getAscendingfloatArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedfloatArray(supplier,arrLength);
      }
      float[] copy=new float[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      SortUtil.uncheckedsort(arr,0,arrLength-1);
      isSortedAscending(arr,copy);
    }
  }
  private static void testWithoutComparatorAscendingdoubleHelper(DoubleSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      double[] arr;
      if(presorted)
      {
        arr=JunitUtil.getAscendingdoubleArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsorteddoubleArray(supplier,arrLength);
      }
      double[] copy=new double[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      SortUtil.uncheckedsort(arr,0,arrLength-1);
      isSortedAscending(arr,copy);
    }
  }
  private static void testWithoutComparatorDescendingbooleanHelper(BooleanSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      boolean[] arr;
      if(presorted)
      {
        arr=JunitUtil.getDescendingbooleanArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedbooleanArray(supplier,arrLength);
      }
      boolean[] copy=new boolean[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      isSortedDescending(arr,copy);
    }
  }
  private static void testWithoutComparatorDescendingbyteHelper(ByteSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      byte[] arr;
      if(presorted)
      {
        arr=JunitUtil.getDescendingbyteArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedbyteArray(supplier,arrLength);
      }
      byte[] copy=new byte[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      isSortedDescending(arr,copy);
    }
  }
  private static void testWithoutComparatorDescendingcharHelper(CharSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      char[] arr;
      if(presorted)
      {
        arr=JunitUtil.getDescendingcharArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedcharArray(supplier,arrLength);
      }
      char[] copy=new char[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      isSortedDescending(arr,copy);
    }
  }
  private static void testWithoutComparatorDescendingshortHelper(ShortSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      short[] arr;
      if(presorted)
      {
        arr=JunitUtil.getDescendingshortArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedshortArray(supplier,arrLength);
      }
      short[] copy=new short[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      isSortedDescending(arr,copy);
    }
  }
  private static void testWithoutComparatorDescendingintHelper(IntSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      int[] arr;
      if(presorted)
      {
        arr=JunitUtil.getDescendingintArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedintArray(supplier,arrLength);
      }
      int[] copy=new int[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      isSortedDescending(arr,copy);
    }
  }
  private static void testWithoutComparatorDescendinglongHelper(LongSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      long[] arr;
      if(presorted)
      {
        arr=JunitUtil.getDescendinglongArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedlongArray(supplier,arrLength);
      }
      long[] copy=new long[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      isSortedDescending(arr,copy);
    }
  }
  private static void testWithoutComparatorDescendingfloatHelper(FloatSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      float[] arr;
      if(presorted)
      {
        arr=JunitUtil.getDescendingfloatArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedfloatArray(supplier,arrLength);
      }
      float[] copy=new float[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      isSortedDescending(arr,copy);
    }
  }
  private static void testWithoutComparatorDescendingdoubleHelper(DoubleSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      double[] arr;
      if(presorted)
      {
        arr=JunitUtil.getDescendingdoubleArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsorteddoubleArray(supplier,arrLength);
      }
      double[] copy=new double[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      isSortedDescending(arr,copy);
    }
  }
  private static void testWithComparatorAscendingbooleanHelper(BooleanSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      boolean[] arr;
      if(presorted)
      {
        arr=JunitUtil.getAscendingbooleanArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedbooleanArray(supplier,arrLength);
      }
      boolean[] copy=new boolean[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      var sorter=getAscendingbooleanComparator();
      SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      isSorted(arr,copy,sorter);
    }
  }
  private static void testWithComparatorAscendingbyteHelper(ByteSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      byte[] arr;
      if(presorted)
      {
        arr=JunitUtil.getAscendingbyteArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedbyteArray(supplier,arrLength);
      }
      byte[] copy=new byte[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      var sorter=getAscendingbyteComparator();
      SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      isSorted(arr,copy,sorter);
    }
  }
  private static void testWithComparatorAscendingcharHelper(CharSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      char[] arr;
      if(presorted)
      {
        arr=JunitUtil.getAscendingcharArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedcharArray(supplier,arrLength);
      }
      char[] copy=new char[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      var sorter=getAscendingcharComparator();
      SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      isSorted(arr,copy,sorter);
    }
  }
  private static void testWithComparatorAscendingshortHelper(ShortSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      short[] arr;
      if(presorted)
      {
        arr=JunitUtil.getAscendingshortArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedshortArray(supplier,arrLength);
      }
      short[] copy=new short[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      var sorter=getAscendingshortComparator();
      SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      isSorted(arr,copy,sorter);
    }
  }
  private static void testWithComparatorAscendingintHelper(IntSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      int[] arr;
      if(presorted)
      {
        arr=JunitUtil.getAscendingintArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedintArray(supplier,arrLength);
      }
      int[] copy=new int[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      var sorter=getAscendingintComparator();
      SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      isSorted(arr,copy,sorter);
    }
  }
  private static void testWithComparatorAscendinglongHelper(LongSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      long[] arr;
      if(presorted)
      {
        arr=JunitUtil.getAscendinglongArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedlongArray(supplier,arrLength);
      }
      long[] copy=new long[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      var sorter=getAscendinglongComparator();
      SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      isSorted(arr,copy,sorter);
    }
  }
  private static void testWithComparatorAscendingfloatHelper(FloatSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      float[] arr;
      if(presorted)
      {
        arr=JunitUtil.getAscendingfloatArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedfloatArray(supplier,arrLength);
      }
      float[] copy=new float[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      var sorter=getAscendingfloatComparator();
      SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      isSorted(arr,copy,sorter);
    }
  }
  private static void testWithComparatorAscendingdoubleHelper(DoubleSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      double[] arr;
      if(presorted)
      {
        arr=JunitUtil.getAscendingdoubleArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsorteddoubleArray(supplier,arrLength);
      }
      double[] copy=new double[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      var sorter=getAscendingdoubleComparator();
      SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      isSorted(arr,copy,sorter);
    }
  }
  private static void testWithComparatorDescendingbooleanHelper(BooleanSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      boolean[] arr;
      if(presorted)
      {
        arr=JunitUtil.getDescendingbooleanArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedbooleanArray(supplier,arrLength);
      }
      boolean[] copy=new boolean[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      var sorter=getDescendingbooleanComparator();
      SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      isSorted(arr,copy,sorter);
    }
  }
  private static void testWithComparatorDescendingbyteHelper(ByteSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      byte[] arr;
      if(presorted)
      {
        arr=JunitUtil.getDescendingbyteArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedbyteArray(supplier,arrLength);
      }
      byte[] copy=new byte[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      var sorter=getDescendingbyteComparator();
      SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      isSorted(arr,copy,sorter);
    }
  }
  private static void testWithComparatorDescendingcharHelper(CharSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      char[] arr;
      if(presorted)
      {
        arr=JunitUtil.getDescendingcharArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedcharArray(supplier,arrLength);
      }
      char[] copy=new char[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      var sorter=getDescendingcharComparator();
      SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      isSorted(arr,copy,sorter);
    }
  }
  private static void testWithComparatorDescendingshortHelper(ShortSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      short[] arr;
      if(presorted)
      {
        arr=JunitUtil.getDescendingshortArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedshortArray(supplier,arrLength);
      }
      short[] copy=new short[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      var sorter=getDescendingshortComparator();
      SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      isSorted(arr,copy,sorter);
    }
  }
  private static void testWithComparatorDescendingintHelper(IntSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      int[] arr;
      if(presorted)
      {
        arr=JunitUtil.getDescendingintArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedintArray(supplier,arrLength);
      }
      int[] copy=new int[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      var sorter=getDescendingintComparator();
      SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      isSorted(arr,copy,sorter);
    }
  }
  private static void testWithComparatorDescendinglongHelper(LongSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      long[] arr;
      if(presorted)
      {
        arr=JunitUtil.getDescendinglongArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedlongArray(supplier,arrLength);
      }
      long[] copy=new long[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      var sorter=getDescendinglongComparator();
      SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      isSorted(arr,copy,sorter);
    }
  }
  private static void testWithComparatorDescendingfloatHelper(FloatSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      float[] arr;
      if(presorted)
      {
        arr=JunitUtil.getDescendingfloatArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedfloatArray(supplier,arrLength);
      }
      float[] copy=new float[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      var sorter=getDescendingfloatComparator();
      SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      isSorted(arr,copy,sorter);
    }
  }
  private static void testWithComparatorDescendingdoubleHelper(DoubleSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      double[] arr;
      if(presorted)
      {
        arr=JunitUtil.getDescendingdoubleArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsorteddoubleArray(supplier,arrLength);
      }
      double[] copy=new double[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      var sorter=getDescendingdoubleComparator();
      SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      isSorted(arr,copy,sorter);
    }
  }
  private static void testWithComparatorUnsortedbooleanHelper(BooleanSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      boolean[] arr;
      if(presorted)
      {
        arr=JunitUtil.getUnsortedbooleanArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedbooleanArray(supplier,arrLength);
      }
      boolean[] copy=new boolean[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      var sorter=getUnsortedbooleanComparator();
      SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      isSorted(arr,copy,sorter);
    }
  }
  private static void testWithComparatorUnsortedbyteHelper(ByteSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      byte[] arr;
      if(presorted)
      {
        arr=JunitUtil.getUnsortedbyteArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedbyteArray(supplier,arrLength);
      }
      byte[] copy=new byte[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      var sorter=getUnsortedbyteComparator();
      SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      isSorted(arr,copy,sorter);
    }
  }
  private static void testWithComparatorUnsortedcharHelper(CharSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      char[] arr;
      if(presorted)
      {
        arr=JunitUtil.getUnsortedcharArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedcharArray(supplier,arrLength);
      }
      char[] copy=new char[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      var sorter=getUnsortedcharComparator();
      SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      isSorted(arr,copy,sorter);
    }
  }
  private static void testWithComparatorUnsortedshortHelper(ShortSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      short[] arr;
      if(presorted)
      {
        arr=JunitUtil.getUnsortedshortArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedshortArray(supplier,arrLength);
      }
      short[] copy=new short[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      var sorter=getUnsortedshortComparator();
      SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      isSorted(arr,copy,sorter);
    }
  }
  private static void testWithComparatorUnsortedintHelper(IntSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      int[] arr;
      if(presorted)
      {
        arr=JunitUtil.getUnsortedintArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedintArray(supplier,arrLength);
      }
      int[] copy=new int[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      var sorter=getUnsortedintComparator();
      SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      isSorted(arr,copy,sorter);
    }
  }
  private static void testWithComparatorUnsortedlongHelper(LongSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      long[] arr;
      if(presorted)
      {
        arr=JunitUtil.getUnsortedlongArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedlongArray(supplier,arrLength);
      }
      long[] copy=new long[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      var sorter=getUnsortedlongComparator();
      SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      isSorted(arr,copy,sorter);
    }
  }
  private static void testWithComparatorUnsortedfloatHelper(FloatSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      float[] arr;
      if(presorted)
      {
        arr=JunitUtil.getUnsortedfloatArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedfloatArray(supplier,arrLength);
      }
      float[] copy=new float[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      var sorter=getUnsortedfloatComparator();
      SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      isSorted(arr,copy,sorter);
    }
  }
  private static void testWithComparatorUnsorteddoubleHelper(DoubleSupplier supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      double[] arr;
      if(presorted)
      {
        arr=JunitUtil.getUnsorteddoubleArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsorteddoubleArray(supplier,arrLength);
      }
      double[] copy=new double[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      var sorter=getUnsorteddoubleComparator();
      SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      isSorted(arr,copy,sorter);
    }
  }
  /*//TODO uncomment when they fix the eclipse module bug
  private static void testWithoutComparatorAscendingStringHelper(Supplier<String> supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      String[] arr;
      if(presorted)
      {
        arr=JunitUtil.getAscendingStringArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedStringArray(supplier,arrLength);
      }
      String[] copy=new String[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      SortUtil.uncheckedsort(arr,0,arrLength-1);
      isSortedAscending(arr,copy);
    }
  }
  private static void testWithoutComparatorDescendingStringHelper(Supplier<String> supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      String[] arr;
      if(presorted)
      {
        arr=JunitUtil.getDescendingStringArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedStringArray(supplier,arrLength);
      }
      String[] copy=new String[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      isSortedDescending(arr,copy);
    }
  }
  private static void testWithComparatorAscendingStringHelper(Supplier<String> supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      String[] arr;
      if(presorted)
      {
        arr=JunitUtil.getAscendingStringArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedStringArray(supplier,arrLength);
      }
      String[] copy=new String[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      var sorter=getAscendingStringComparator();
      SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      isSorted(arr,copy,sorter);
    }
  }
  private static void testWithComparatorDescendingStringHelper(Supplier<String> supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      String[] arr;
      if(presorted)
      {
        arr=JunitUtil.getDescendingStringArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedStringArray(supplier,arrLength);
      }
      String[] copy=new String[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      var sorter=getDescendingStringComparator();
      SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      isSorted(arr,copy,sorter);
    }
  }
  private static void testWithComparatorUnsortedStringHelper(Supplier<String> supplier,int numiterations,int arrLength,boolean presorted)
  {
    for(int i=0;i<numiterations;++i)
    {
      String[] arr;
      if(presorted)
      {
        arr=JunitUtil.getUnsortedStringArray(supplier,arrLength);
      }
      else
      {
        arr=JunitUtil.getUnsortedStringArray(supplier,arrLength);
      }
      String[] copy=new String[arrLength];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
      var sorter=getUnsortedStringComparator();
      SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      isSorted(arr,copy,sorter);
    }
  }
  */
  @Test
  public void testUncheckedSortboolean()
  {
    var rand=new Random(0);
    testWithoutComparatorAscendingbooleanHelper(rand::nextBoolean,100,100,false);
    testWithoutComparatorAscendingbooleanHelper(rand::nextBoolean,1,100,true);
    testWithoutComparatorAscendingbooleanHelper(()->false,1,100,false);
    testWithoutComparatorAscendingbooleanHelper(()->true,1,100,false);
  }
  @Test
  public void testUncheckedReverseSortboolean()
  {
    var rand=new Random(0);
    testWithoutComparatorDescendingbooleanHelper(rand::nextBoolean,100,100,false);
    testWithoutComparatorDescendingbooleanHelper(rand::nextBoolean,1,100,true);
    testWithoutComparatorDescendingbooleanHelper(()->false,1,100,false);
    testWithoutComparatorDescendingbooleanHelper(()->true,1,100,false);
  }
  /*//TODO uncomment when the module bug is fixed
  @Test
  public void testUncheckedComparatorSortboolean()
  {
    Random rand=new Random(0);
    BooleanSupplier falseSupplier=()->false;
    BooleanSupplier trueSupplier=()->true;
    testWithComparatorAscendingbooleanHelper(rand::nextBoolean,100,100,false);
    testWithComparatorAscendingbooleanHelper(rand::nextBoolean,1,100,true);
    testWithComparatorAscendingbooleanHelper(falseSupplier,1,100,false);
    testWithComparatorAscendingbooleanHelper(trueSupplier,1,100,false);
    testWithComparatorDescendingbooleanHelper(rand::nextBoolean,100,100,false);
    testWithComparatorDescendingbooleanHelper(rand::nextBoolean,1,100,true);
    testWithComparatorDescendingbooleanHelper(falseSupplier,1,100,false);
    testWithComparatorDescendingbooleanHelper(trueSupplier,1,100,false);
    testWithComparatorUnsortedbooleanHelper(rand::nextBoolean,100,100,false);
    testWithComparatorUnsortedbooleanHelper(falseSupplier,1,100,false);
    testWithComparatorUnsortedbooleanHelper(trueSupplier,1,100,false);
  }
  */
  @Test
  public void testUncheckedSortbyte()
  {
    var rand=new Random(0);
    ByteSupplier supplier=()->(byte)rand.nextInt();
    testWithoutComparatorAscendingbyteHelper(supplier,1000,29,false);
    testWithoutComparatorAscendingbyteHelper(supplier,1,29,true);
    testWithoutComparatorAscendingbyteHelper(supplier,1000,1000,false);
    testWithoutComparatorAscendingbyteHelper(supplier,1,1000,true);
  }
  @Test
  public void testUncheckedReverseSortbyte()
  {
    Random rand=new Random(0);
    ByteSupplier supplier=()->(byte)rand.nextInt();
    testWithoutComparatorDescendingbyteHelper(supplier,1000,29,false);
    testWithoutComparatorDescendingbyteHelper(supplier,1,29,true);
    testWithoutComparatorDescendingbyteHelper(supplier,1000,1000,false);
    testWithoutComparatorDescendingbyteHelper(supplier,1,1000,true);
  }
  @Test
  public void testUncheckedsortchar()
  {
    var rand=new Random(0);
    CharSupplier supplier=()->(char)rand.nextInt();
    //testWithoutComparatorAscendingcharHelper(supplier,1000,3201,false);
    //testWithoutComparatorAscendingcharHelper(supplier,1,3201,true);
    testWithoutComparatorAscendingcharHelper(supplier,1000,3202,false);
    testWithoutComparatorAscendingcharHelper(supplier,1,3202,true);
  }
  @Test
  public void testUncheckedReverseSortchar()
  {
    Random rand=new Random(0);
    CharSupplier supplier=()->(char)rand.nextInt();
    //testWithoutComparatorDescendingcharHelper(supplier,1000,3201,false);
    //testWithoutComparatorDescendingcharHelper(supplier,1,3201,true);
    testWithoutComparatorDescendingcharHelper(supplier,1000,3202,false);
    testWithoutComparatorDescendingcharHelper(supplier,1,3202,true);
  }
  @Test
  public void testUncheckedsortshort()
  {
    var rand=new Random(0);
    ShortSupplier supplier=()->(short)rand.nextInt();
    //testWithoutComparatorAscendingshortHelper(supplier,1000,3201,false);
    //testWithoutComparatorAscendingshortHelper(supplier,1,3201,true);
    testWithoutComparatorAscendingshortHelper(supplier,1000,3202,false);
    testWithoutComparatorAscendingshortHelper(supplier,1,3202,true);
  }
  @Test
  public void testUncheckedReverseSortshort()
  {
    Random rand=new Random(0);
    ShortSupplier supplier=()->(short)rand.nextInt();
    //testWithoutComparatorDescendingshortHelper(supplier,1000,3201,false);
    //testWithoutComparatorDescendingshortHelper(supplier,1,3201,true);
    testWithoutComparatorDescendingshortHelper(supplier,1000,3202,false);
    testWithoutComparatorDescendingshortHelper(supplier,1,3202,true);
  }
}
