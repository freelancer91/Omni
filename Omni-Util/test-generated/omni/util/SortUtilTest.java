package omni.util;
import org.junit.Assert;
import org.junit.Test;
import java.util.Random;
import java.util.Arrays;
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
public class SortUtilTest
{
  //TODO uncomment when the module bug is fixed
  //MACRO GetComparator<Ascending,String,Comparator<String>>(NULL)
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
  //TODO uncomment when the module bug is fixed
  //MACRO GetComparator<Unsorted,String,Comparator<String>>(NULL)
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
  //TODO uncomment when the module bug is fixed
  //MACRO GetComparator<Descending,String,Comparator<String>>(NULL)
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
  //TODO uncomment when the module bug is fixed
  //MACRO TestComparatorSortHelper<String,Ascending>()
  private static void testComparatorSortHelperAscendingboolean(Random rand,int length)
  {
    boolean[] arr=ArrCopyTest.getbooleanArr(rand,length);
    boolean[] copy=new boolean[length];
    Boolean[] boxedCopy=new Boolean[length];
    ArrCopy.uncheckedCopy(arr,0,boxedCopy,0,length);
    Arrays.sort(boxedCopy,0,length);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,length);
    var sorter=getAscendingbooleanComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void testComparatorSortHelperAscendingbyte(Random rand,int length)
  {
    byte[] arr=ArrCopyTest.getbyteArr(rand,length);
    byte[] copy=new byte[length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,length);
    Arrays.sort(copy,0,length);
    var sorter=getAscendingbyteComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void testComparatorSortHelperAscendingchar(Random rand,int length)
  {
    char[] arr=ArrCopyTest.getcharArr(rand,length);
    char[] copy=new char[length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,length);
    Arrays.sort(copy,0,length);
    var sorter=getAscendingcharComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void testComparatorSortHelperAscendingshort(Random rand,int length)
  {
    short[] arr=ArrCopyTest.getshortArr(rand,length);
    short[] copy=new short[length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,length);
    Arrays.sort(copy,0,length);
    var sorter=getAscendingshortComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void testComparatorSortHelperAscendingint(Random rand,int length)
  {
    int[] arr=ArrCopyTest.getintArr(rand,length);
    int[] copy=new int[length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,length);
    Arrays.sort(copy,0,length);
    var sorter=getAscendingintComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void testComparatorSortHelperAscendinglong(Random rand,int length)
  {
    long[] arr=ArrCopyTest.getlongArr(rand,length);
    long[] copy=new long[length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,length);
    Arrays.sort(copy,0,length);
    var sorter=getAscendinglongComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void testComparatorSortHelperAscendingfloat(Random rand,int length)
  {
    float[] arr=ArrCopyTest.getfloatArr(rand,length);
    float[] copy=new float[length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,length);
    Arrays.sort(copy,0,length);
    var sorter=getAscendingfloatComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(TypeUtil.floatEquals(arr[i],copy[i]));
    }
  }
  private static void testComparatorSortHelperAscendingdouble(Random rand,int length)
  {
    double[] arr=ArrCopyTest.getdoubleArr(rand,length);
    double[] copy=new double[length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,length);
    Arrays.sort(copy,0,length);
    var sorter=getAscendingdoubleComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(TypeUtil.doubleEquals(arr[i],copy[i]));
    }
  }
  //TODO uncomment when the module bug is fixed
  //MACRO TestComparatorSortHelper<String,Unsorted>()
  private static void testComparatorSortHelperUnsortedboolean(Random rand,int length)
  {
    boolean[] arr=ArrCopyTest.getbooleanArr(rand,length);
    boolean[] copy=new boolean[length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,length);
    var sorter=getUnsortedbooleanComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void testComparatorSortHelperUnsortedbyte(Random rand,int length)
  {
    byte[] arr=ArrCopyTest.getbyteArr(rand,length);
    byte[] copy=new byte[length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,length);
    var sorter=getUnsortedbyteComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void testComparatorSortHelperUnsortedchar(Random rand,int length)
  {
    char[] arr=ArrCopyTest.getcharArr(rand,length);
    char[] copy=new char[length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,length);
    var sorter=getUnsortedcharComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void testComparatorSortHelperUnsortedshort(Random rand,int length)
  {
    short[] arr=ArrCopyTest.getshortArr(rand,length);
    short[] copy=new short[length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,length);
    var sorter=getUnsortedshortComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void testComparatorSortHelperUnsortedint(Random rand,int length)
  {
    int[] arr=ArrCopyTest.getintArr(rand,length);
    int[] copy=new int[length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,length);
    var sorter=getUnsortedintComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void testComparatorSortHelperUnsortedlong(Random rand,int length)
  {
    long[] arr=ArrCopyTest.getlongArr(rand,length);
    long[] copy=new long[length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,length);
    var sorter=getUnsortedlongComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void testComparatorSortHelperUnsortedfloat(Random rand,int length)
  {
    float[] arr=ArrCopyTest.getfloatArr(rand,length);
    float[] copy=new float[length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,length);
    var sorter=getUnsortedfloatComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(TypeUtil.floatEquals(arr[i],copy[i]));
    }
  }
  private static void testComparatorSortHelperUnsorteddouble(Random rand,int length)
  {
    double[] arr=ArrCopyTest.getdoubleArr(rand,length);
    double[] copy=new double[length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,length);
    var sorter=getUnsorteddoubleComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(TypeUtil.doubleEquals(arr[i],copy[i]));
    }
  }
  //TODO uncomment when the module bug is fixed
  //MACRO TestComparatorSortHelper<String,Descending>()
  private static void testComparatorSortHelperDescendingboolean(Random rand,int length)
  {
    boolean[] arr=ArrCopyTest.getbooleanArr(rand,length);
    boolean[] copy=new boolean[length];
    Boolean[] boxedCopy=new Boolean[length];
    ArrCopy.uncheckedCopy(arr,0,boxedCopy,0,length);
    Arrays.sort(boxedCopy,0,length);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,length);
    reverseRange(copy,0,length-1);
    var sorter=getDescendingbooleanComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void testComparatorSortHelperDescendingbyte(Random rand,int length)
  {
    byte[] arr=ArrCopyTest.getbyteArr(rand,length);
    byte[] copy=new byte[length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,length);
    Arrays.sort(copy,0,length);
    reverseRange(copy,0,length-1);
    var sorter=getDescendingbyteComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void testComparatorSortHelperDescendingchar(Random rand,int length)
  {
    char[] arr=ArrCopyTest.getcharArr(rand,length);
    char[] copy=new char[length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,length);
    Arrays.sort(copy,0,length);
    reverseRange(copy,0,length-1);
    var sorter=getDescendingcharComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void testComparatorSortHelperDescendingshort(Random rand,int length)
  {
    short[] arr=ArrCopyTest.getshortArr(rand,length);
    short[] copy=new short[length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,length);
    Arrays.sort(copy,0,length);
    reverseRange(copy,0,length-1);
    var sorter=getDescendingshortComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void testComparatorSortHelperDescendingint(Random rand,int length)
  {
    int[] arr=ArrCopyTest.getintArr(rand,length);
    int[] copy=new int[length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,length);
    Arrays.sort(copy,0,length);
    reverseRange(copy,0,length-1);
    var sorter=getDescendingintComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void testComparatorSortHelperDescendinglong(Random rand,int length)
  {
    long[] arr=ArrCopyTest.getlongArr(rand,length);
    long[] copy=new long[length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,length);
    Arrays.sort(copy,0,length);
    reverseRange(copy,0,length-1);
    var sorter=getDescendinglongComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void testComparatorSortHelperDescendingfloat(Random rand,int length)
  {
    float[] arr=ArrCopyTest.getfloatArr(rand,length);
    float[] copy=new float[length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,length);
    Arrays.sort(copy,0,length);
    reverseRange(copy,0,length-1);
    var sorter=getDescendingfloatComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(TypeUtil.floatEquals(arr[i],copy[i]));
    }
  }
  private static void testComparatorSortHelperDescendingdouble(Random rand,int length)
  {
    double[] arr=ArrCopyTest.getdoubleArr(rand,length);
    double[] copy=new double[length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,length);
    Arrays.sort(copy,0,length);
    reverseRange(copy,0,length-1);
    var sorter=getDescendingdoubleComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(TypeUtil.doubleEquals(arr[i],copy[i]));
    }
  }
    //TODO uncomment when the module bug is fixed
  //MACRO TestComparatorSortHelperAlreadySorted<String,Ascending>()
  private static void testComparatorSortHelperAlreadySortedAscendingboolean(Random rand,int length)
  {
    boolean[] arr=getSortedbooleanAscendingArray(rand,length);
    boolean[] copy=new boolean[length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,length);
    var sorter=getAscendingbooleanComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void testComparatorSortHelperAlreadySortedAscendingbyte(Random rand,int length)
  {
    byte[] arr=getSortedbyteAscendingArray(rand,length);
    byte[] copy=new byte[length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,length);
    var sorter=getAscendingbyteComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void testComparatorSortHelperAlreadySortedAscendingchar(Random rand,int length)
  {
    char[] arr=getSortedcharAscendingArray(rand,length);
    char[] copy=new char[length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,length);
    var sorter=getAscendingcharComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void testComparatorSortHelperAlreadySortedAscendingshort(Random rand,int length)
  {
    short[] arr=getSortedshortAscendingArray(rand,length);
    short[] copy=new short[length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,length);
    var sorter=getAscendingshortComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void testComparatorSortHelperAlreadySortedAscendingint(Random rand,int length)
  {
    int[] arr=getSortedintAscendingArray(rand,length);
    int[] copy=new int[length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,length);
    var sorter=getAscendingintComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void testComparatorSortHelperAlreadySortedAscendinglong(Random rand,int length)
  {
    long[] arr=getSortedlongAscendingArray(rand,length);
    long[] copy=new long[length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,length);
    var sorter=getAscendinglongComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void testComparatorSortHelperAlreadySortedAscendingfloat(Random rand,int length)
  {
    float[] arr=getSortedfloatAscendingArray(rand,length);
    float[] copy=new float[length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,length);
    var sorter=getAscendingfloatComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(TypeUtil.floatEquals(arr[i],copy[i]));
    }
  }
  private static void testComparatorSortHelperAlreadySortedAscendingdouble(Random rand,int length)
  {
    double[] arr=getSorteddoubleAscendingArray(rand,length);
    double[] copy=new double[length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,length);
    var sorter=getAscendingdoubleComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(TypeUtil.doubleEquals(arr[i],copy[i]));
    }
  }
  //TODO uncomment when the module bug is fixed
  //MACRO TestComparatorSortHelperAlreadySorted<String,Descending>()
  private static void testComparatorSortHelperAlreadySortedDescendingboolean(Random rand,int length)
  {
    boolean[] arr=getSortedbooleanDescendingArray(rand,length);
    boolean[] copy=new boolean[length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,length);
    var sorter=getDescendingbooleanComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void testComparatorSortHelperAlreadySortedDescendingbyte(Random rand,int length)
  {
    byte[] arr=getSortedbyteDescendingArray(rand,length);
    byte[] copy=new byte[length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,length);
    var sorter=getDescendingbyteComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void testComparatorSortHelperAlreadySortedDescendingchar(Random rand,int length)
  {
    char[] arr=getSortedcharDescendingArray(rand,length);
    char[] copy=new char[length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,length);
    var sorter=getDescendingcharComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void testComparatorSortHelperAlreadySortedDescendingshort(Random rand,int length)
  {
    short[] arr=getSortedshortDescendingArray(rand,length);
    short[] copy=new short[length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,length);
    var sorter=getDescendingshortComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void testComparatorSortHelperAlreadySortedDescendingint(Random rand,int length)
  {
    int[] arr=getSortedintDescendingArray(rand,length);
    int[] copy=new int[length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,length);
    var sorter=getDescendingintComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void testComparatorSortHelperAlreadySortedDescendinglong(Random rand,int length)
  {
    long[] arr=getSortedlongDescendingArray(rand,length);
    long[] copy=new long[length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,length);
    var sorter=getDescendinglongComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void testComparatorSortHelperAlreadySortedDescendingfloat(Random rand,int length)
  {
    float[] arr=getSortedfloatDescendingArray(rand,length);
    float[] copy=new float[length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,length);
    var sorter=getDescendingfloatComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(TypeUtil.floatEquals(arr[i],copy[i]));
    }
  }
  private static void testComparatorSortHelperAlreadySortedDescendingdouble(Random rand,int length)
  {
    double[] arr=getSorteddoubleDescendingArray(rand,length);
    double[] copy=new double[length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,length);
    var sorter=getDescendingdoubleComparator();
    SortUtil.uncheckedcomparatorSort(arr,0,length-1,sorter);
    for(int i=0;i<length;++i)
    {
      Assert.assertTrue(TypeUtil.doubleEquals(arr[i],copy[i]));
    }
  }
  @Test
  public void testComparatorSortboolean()
  {
    Random rand=new Random(0);
    int length=1000;
    for(int i=0;i<100;++i)
    {
      testComparatorSortHelperAscendingboolean(rand,length);
      testComparatorSortHelperUnsortedboolean(rand,length);
      testComparatorSortHelperDescendingboolean(rand,length);
      testComparatorSortHelperAlreadySortedAscendingboolean(rand,length);
      testComparatorSortHelperAlreadySortedDescendingboolean(rand,length);
    }
    boolean[] homogenousArr=new boolean[1000];
    SortUtil.uncheckedcomparatorSort(homogenousArr,0,999,getAscendingbooleanComparator());
    for(int i=0;i<1000;++i)
    {
      Assert.assertFalse(homogenousArr[i]);
    }
  }
  @Test
  public void testComparatorSortbyte()
  {
    Random rand=new Random(0);
    int length=1000;
    for(int i=0;i<100;++i)
    {
      testComparatorSortHelperAscendingbyte(rand,length);
      //testComparatorSortHelperUnsortedbyte(rand,length);
      testComparatorSortHelperDescendingbyte(rand,length);
      testComparatorSortHelperAlreadySortedAscendingbyte(rand,length);
      testComparatorSortHelperAlreadySortedDescendingbyte(rand,length);
    }
    length=29;
    for(int i=0;i<100;++i)
    {
      testComparatorSortHelperAscendingbyte(rand,length);
      //testComparatorSortHelperUnsortedbyte(rand,length);
      testComparatorSortHelperDescendingbyte(rand,length);
      testComparatorSortHelperAlreadySortedAscendingbyte(rand,length);
      testComparatorSortHelperAlreadySortedDescendingbyte(rand,length);
    }
  }
  private static void reverseRange(Object[] arr,int begin,int end)
  {
    do
    {
      var tmp=arr[begin];
      arr[begin]=arr[end];
      arr[end]=tmp;
    }
    while(++begin<--end);
  }
  private static void reverseRange(boolean[] arr,int begin,int end)
  {
    do
    {
      var tmp=arr[begin];
      arr[begin]=arr[end];
      arr[end]=tmp;
    }
    while(++begin<--end);
  }
  private static void reverseRange(byte[] arr,int begin,int end)
  {
    do
    {
      var tmp=arr[begin];
      arr[begin]=arr[end];
      arr[end]=tmp;
    }
    while(++begin<--end);
  }
  private static void reverseRange(char[] arr,int begin,int end)
  {
    do
    {
      var tmp=arr[begin];
      arr[begin]=arr[end];
      arr[end]=tmp;
    }
    while(++begin<--end);
  }
  private static void reverseRange(short[] arr,int begin,int end)
  {
    do
    {
      var tmp=arr[begin];
      arr[begin]=arr[end];
      arr[end]=tmp;
    }
    while(++begin<--end);
  }
  private static void reverseRange(int[] arr,int begin,int end)
  {
    do
    {
      var tmp=arr[begin];
      arr[begin]=arr[end];
      arr[end]=tmp;
    }
    while(++begin<--end);
  }
  private static void reverseRange(long[] arr,int begin,int end)
  {
    do
    {
      var tmp=arr[begin];
      arr[begin]=arr[end];
      arr[end]=tmp;
    }
    while(++begin<--end);
  }
  private static void reverseRange(float[] arr,int begin,int end)
  {
    do
    {
      var tmp=arr[begin];
      arr[begin]=arr[end];
      arr[end]=tmp;
    }
    while(++begin<--end);
  }
  private static void reverseRange(double[] arr,int begin,int end)
  {
    do
    {
      var tmp=arr[begin];
      arr[begin]=arr[end];
      arr[end]=tmp;
    }
    while(++begin<--end);
  }
  //TODO uncomment when the module bug is fixed
  //MACRO GetSortedArray<String,Ascending>()
  private static boolean[] getSortedbooleanAscendingArray(Random rand,int length)
  {
    boolean[] arr=ArrCopyTest.getbooleanArr(rand,length);
    Boolean[] boxedCopy=new Boolean[length];
    ArrCopy.uncheckedCopy(arr,0,boxedCopy,0,length);
    Arrays.sort(boxedCopy,0,length);
    ArrCopy.uncheckedCopy(boxedCopy,0,arr,0,length);
    return arr;
  }
  private static byte[] getSortedbyteAscendingArray(Random rand,int length)
  {
    byte[] arr=ArrCopyTest.getbyteArr(rand,length);
    Arrays.sort(arr,0,length);
    return arr;
  }
  private static char[] getSortedcharAscendingArray(Random rand,int length)
  {
    char[] arr=ArrCopyTest.getcharArr(rand,length);
    Arrays.sort(arr,0,length);
    return arr;
  }
  private static short[] getSortedshortAscendingArray(Random rand,int length)
  {
    short[] arr=ArrCopyTest.getshortArr(rand,length);
    Arrays.sort(arr,0,length);
    return arr;
  }
  private static int[] getSortedintAscendingArray(Random rand,int length)
  {
    int[] arr=ArrCopyTest.getintArr(rand,length);
    Arrays.sort(arr,0,length);
    return arr;
  }
  private static long[] getSortedlongAscendingArray(Random rand,int length)
  {
    long[] arr=ArrCopyTest.getlongArr(rand,length);
    Arrays.sort(arr,0,length);
    return arr;
  }
  private static float[] getSortedfloatAscendingArray(Random rand,int length)
  {
    float[] arr=ArrCopyTest.getfloatArr(rand,length);
    Arrays.sort(arr,0,length);
    return arr;
  }
  private static double[] getSorteddoubleAscendingArray(Random rand,int length)
  {
    double[] arr=ArrCopyTest.getdoubleArr(rand,length);
    Arrays.sort(arr,0,length);
    return arr;
  }
  //TODO uncomment when the module bug is fixed
  //MACRO GetSortedArray<String,Descending>()
  private static boolean[] getSortedbooleanDescendingArray(Random rand,int length)
  {
    boolean[] arr=ArrCopyTest.getbooleanArr(rand,length);
    Boolean[] boxedCopy=new Boolean[length];
    ArrCopy.uncheckedCopy(arr,0,boxedCopy,0,length);
    Arrays.sort(boxedCopy,0,length);
    ArrCopy.uncheckedCopy(boxedCopy,0,arr,0,length);
    reverseRange(arr,0,length-1);
    return arr;
  }
  private static byte[] getSortedbyteDescendingArray(Random rand,int length)
  {
    byte[] arr=ArrCopyTest.getbyteArr(rand,length);
    Arrays.sort(arr,0,length);
    reverseRange(arr,0,length-1);
    return arr;
  }
  private static char[] getSortedcharDescendingArray(Random rand,int length)
  {
    char[] arr=ArrCopyTest.getcharArr(rand,length);
    Arrays.sort(arr,0,length);
    reverseRange(arr,0,length-1);
    return arr;
  }
  private static short[] getSortedshortDescendingArray(Random rand,int length)
  {
    short[] arr=ArrCopyTest.getshortArr(rand,length);
    Arrays.sort(arr,0,length);
    reverseRange(arr,0,length-1);
    return arr;
  }
  private static int[] getSortedintDescendingArray(Random rand,int length)
  {
    int[] arr=ArrCopyTest.getintArr(rand,length);
    Arrays.sort(arr,0,length);
    reverseRange(arr,0,length-1);
    return arr;
  }
  private static long[] getSortedlongDescendingArray(Random rand,int length)
  {
    long[] arr=ArrCopyTest.getlongArr(rand,length);
    Arrays.sort(arr,0,length);
    reverseRange(arr,0,length-1);
    return arr;
  }
  private static float[] getSortedfloatDescendingArray(Random rand,int length)
  {
    float[] arr=ArrCopyTest.getfloatArr(rand,length);
    Arrays.sort(arr,0,length);
    reverseRange(arr,0,length-1);
    return arr;
  }
  private static double[] getSorteddoubleDescendingArray(Random rand,int length)
  {
    double[] arr=ArrCopyTest.getdoubleArr(rand,length);
    Arrays.sort(arr,0,length);
    reverseRange(arr,0,length-1);
    return arr;
  }
}
