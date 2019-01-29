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
  //TODO uncomment when they fix the module bug in eclipse
  //MACRO GetComparator<Ascending,String>(String,Comparator<String>)
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
  //TODO uncomment when they fix the module bug in eclipse
  //MACRO GetComparator<Unsorted,String>(String,Comparator<String>)
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
  //TODO uncomment when they fix the module bug in eclipse
  //MACRO GetComparator<Descending,String>(String,Comparator<String>)
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
  @Test
  public void testComparatorSortboolean()
  {
    /*
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
    */
  }
  @Test
  public void testSortBoolean()
  {
    //TODO
  }
  @Test
  public void testComparatorSortbyte()
  {
    /*
    Implementation is broken
    Random rand=new Random(0);
    int length=1000;
    for(int i=0;i<100;++i)
    {
      testcomparatorSortHelperAscendingbyte(rand,length);
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
    */
  }
}
