package omni.util;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;
import omni.function.DoubleComparator;
@TestMethodOrder(OrderAnnotation.class)
public class DoubleSortUtilTest
{
  private static final boolean LONGRUN=true;
  private static final int[] lengths;
  private static final long[] randSeeds;
  private static final ArrayList<TestData> TEST_DATA=new ArrayList<>();
  static
  {
   if(LONGRUN)
    {
      lengths=new int[]{2, 3, 5, 8, 13, 21, 34, 55, 100, 1000, 10000, 100000, 1000000};
      randSeeds=new long[]{666L,0xC0FFEEL,999L};
    }
    else
    {
      lengths=new int[]{2, 3, 21, 55, 1000, 10000};
      randSeeds=new long[]{666};
    }
  }
  private static long TIMER;
  @BeforeAll
  public static void startTimer()
  {
    TIMER=System.currentTimeMillis();
  }
  private static class TestData
  {
    final long randSeed;
    final int m;
    final JunitUtil.doubleArrayBuilder builder;
    final double[] customArr;
    final double[] stockArr;
    private TestData(long randSeed,int m,JunitUtil.doubleArrayBuilder builder,int arrLength,Random rand)
    {
      this.randSeed=randSeed;
      this.m=m;
      this.builder=builder;
      this.customArr=new double[arrLength];
      this.stockArr=new double[arrLength];
      builder.buildUnchecked(customArr,0,arrLength,rand,m);
      ArrCopy.uncheckedCopy(customArr,0,stockArr,0,arrLength);
    }
    private double[] copyCustomArr()
    {
      double[] copy=new double[customArr.length];
      ArrCopy.uncheckedCopy(customArr,0,copy,0,customArr.length);
      return copy;
    }
    private void reverseStockArr()
    {
      OmniArray.OfDouble.reverseRange(stockArr,0,stockArr.length-1);
    }
    @Override
    public boolean equals(Object val)
    {
      TestData td;
      return val instanceof TestData && (td=(TestData)val).builder==this.builder && td.customArr==this.customArr;
    }
    @Override
    public int hashCode()
    {
      return builder.hashCode()*31+customArr.hashCode();
    }
    @Override
    public String toString()
    {
      return "TestData{builder="+builder+"; arrayType=double; arrLength="+customArr.length+"; m="+m+"; randSeed="+randSeed+"}";
    }
    private static void initializeTestData(JunitUtil.doubleArrayBuilder builder,long randSeed,int arrLength,ArrayList<TestData> testDatas)
    {
      Random rand=new Random(randSeed);
      for(int m=getMLo(builder),mHi=getMHi(arrLength,builder),numReps=getNumReps(arrLength,builder);m<=mHi;m=incrementM(m,builder))
      {
        for(int i=0;i<numReps;++i)
        {
          TestData td=new TestData(randSeed,m,builder,arrLength,rand);
          synchronized(testDatas)
          {
            testDatas.add(td);
          }
        }
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
    private static int getMHi(int arrLength,JunitUtil.doubleArrayBuilder builder)
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
        return (arrLength<<1)-1;
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
    private static int getNumReps(int arrLength,JunitUtil.doubleArrayBuilder builder)
    {
      switch(builder)
      {
      case Randomized:
        return 20;
      case Duplicated:
        return 1;
      case Shuffle:
        return 20;
      case WithNaNsAndZeros:
        return 2;
      default:
        return 1;
      }
    }
    private static int incrementM(int m,JunitUtil.doubleArrayBuilder builder)
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
        return m<<1;
      default:
        return m+1;
      }
    }
  }
  private static final DoubleComparator Unsorted=(val1,val2)->
  {
    return 0;
  };
  private static final DoubleComparator Ascending=(val1,val2)->
  {
    return Double.compare(val1,val2);
  };
  private static final DoubleComparator Descending=(val1,val2)->
  {
    return Double.compare(val2,val1);
  };
  @Test
  @Order(8*0+1)
  public void initializeArraysForAllEquals()
  {
    System.out.println("Initializing arrays for arrType=double; builder=AllEquals");
    Arrays.stream(lengths)
    .parallel()
    .forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.AllEquals.isRandomized())
      {
        Arrays.stream(randSeeds)
        .parallel()
        .forEach(randSeed->
        {
          TestData.initializeTestData(JunitUtil.doubleArrayBuilder.AllEquals,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(JunitUtil.doubleArrayBuilder.AllEquals,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(8*0+2)
  public void testUncheckedStableUnsortedComparatorSortWithAllEqualsArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Unsorted);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*0+3)
  public void testUncheckedStableDescendingComparatorSortWithAllEqualsArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*0+4)
  public void testUncheckedUnstableDescendingComparatorSortWithAllEqualsArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*0+5)
  public void testUncheckedStableDescendingSortWithAllEqualsArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*0+6)
  public void testUncheckedStableAscendingComparatorSortWithAllEqualsArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*0+7)
  public void testUncheckedUnstableAscendingComparatorSortWithAllEqualsArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*0+8)
  public void testUncheckedStableAscendingSortWithAllEqualsArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*1+1)
  public void initializeArraysForMergeAscending()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=double; builder=MergeAscending");
    Arrays.stream(lengths)
    .parallel()
    .forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.MergeAscending.isRandomized())
      {
        Arrays.stream(randSeeds)
        .parallel()
        .forEach(randSeed->
        {
          TestData.initializeTestData(JunitUtil.doubleArrayBuilder.MergeAscending,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(JunitUtil.doubleArrayBuilder.MergeAscending,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(8*1+2)
  public void testUncheckedStableUnsortedComparatorSortWithMergeAscendingArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Unsorted);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*1+3)
  public void testUncheckedStableAscendingComparatorSortWithMergeAscendingArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //sort the stock array
      Arrays.sort(stockArr,0,arrLength);
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*1+4)
  public void testUncheckedUnstableAscendingComparatorSortWithMergeAscendingArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*1+5)
  public void testUncheckedStableAscendingSortWithMergeAscendingArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*1+6)
  public void testUncheckedStableDescendingComparatorSortWithMergeAscendingArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //reverse the stock array
      testData.reverseStockArr();
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*1+7)
  public void testUncheckedUnstableDescendingComparatorSortWithMergeAscendingArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*1+8)
  public void testUncheckedStableDescendingSortWithMergeAscendingArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*2+1)
  public void initializeArraysForMergeDescending()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=double; builder=MergeDescending");
    Arrays.stream(lengths)
    .parallel()
    .forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.MergeDescending.isRandomized())
      {
        Arrays.stream(randSeeds)
        .parallel()
        .forEach(randSeed->
        {
          TestData.initializeTestData(JunitUtil.doubleArrayBuilder.MergeDescending,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(JunitUtil.doubleArrayBuilder.MergeDescending,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(8*2+2)
  public void testUncheckedStableUnsortedComparatorSortWithMergeDescendingArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Unsorted);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*2+3)
  public void testUncheckedStableAscendingComparatorSortWithMergeDescendingArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //sort the stock array
      Arrays.sort(stockArr,0,arrLength);
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*2+4)
  public void testUncheckedUnstableAscendingComparatorSortWithMergeDescendingArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*2+5)
  public void testUncheckedStableAscendingSortWithMergeDescendingArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*2+6)
  public void testUncheckedStableDescendingComparatorSortWithMergeDescendingArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //reverse the stock array
      testData.reverseStockArr();
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*2+7)
  public void testUncheckedUnstableDescendingComparatorSortWithMergeDescendingArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*2+8)
  public void testUncheckedStableDescendingSortWithMergeDescendingArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*3+1)
  public void initializeArraysForSortedRepeated()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=double; builder=SortedRepeated");
    Arrays.stream(lengths)
    .parallel()
    .forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.SortedRepeated.isRandomized())
      {
        Arrays.stream(randSeeds)
        .parallel()
        .forEach(randSeed->
        {
          TestData.initializeTestData(JunitUtil.doubleArrayBuilder.SortedRepeated,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(JunitUtil.doubleArrayBuilder.SortedRepeated,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(8*3+2)
  public void testUncheckedStableUnsortedComparatorSortWithSortedRepeatedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Unsorted);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*3+3)
  public void testUncheckedStableAscendingComparatorSortWithSortedRepeatedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //sort the stock array
      Arrays.sort(stockArr,0,arrLength);
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*3+4)
  public void testUncheckedUnstableAscendingComparatorSortWithSortedRepeatedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*3+5)
  public void testUncheckedStableAscendingSortWithSortedRepeatedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*3+6)
  public void testUncheckedStableDescendingComparatorSortWithSortedRepeatedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //reverse the stock array
      testData.reverseStockArr();
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*3+7)
  public void testUncheckedUnstableDescendingComparatorSortWithSortedRepeatedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*3+8)
  public void testUncheckedStableDescendingSortWithSortedRepeatedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*4+1)
  public void initializeArraysForSortedOrganPipes()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=double; builder=SortedOrganPipes");
    Arrays.stream(lengths)
    .parallel()
    .forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.SortedOrganPipes.isRandomized())
      {
        Arrays.stream(randSeeds)
        .parallel()
        .forEach(randSeed->
        {
          TestData.initializeTestData(JunitUtil.doubleArrayBuilder.SortedOrganPipes,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(JunitUtil.doubleArrayBuilder.SortedOrganPipes,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(8*4+2)
  public void testUncheckedStableUnsortedComparatorSortWithSortedOrganPipesArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Unsorted);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*4+3)
  public void testUncheckedStableAscendingComparatorSortWithSortedOrganPipesArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //sort the stock array
      Arrays.sort(stockArr,0,arrLength);
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*4+4)
  public void testUncheckedUnstableAscendingComparatorSortWithSortedOrganPipesArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*4+5)
  public void testUncheckedStableAscendingSortWithSortedOrganPipesArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*4+6)
  public void testUncheckedStableDescendingComparatorSortWithSortedOrganPipesArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //reverse the stock array
      testData.reverseStockArr();
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*4+7)
  public void testUncheckedUnstableDescendingComparatorSortWithSortedOrganPipesArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*4+8)
  public void testUncheckedStableDescendingSortWithSortedOrganPipesArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*5+1)
  public void initializeArraysForAscending()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=double; builder=Ascending");
    Arrays.stream(lengths)
    .parallel()
    .forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Ascending.isRandomized())
      {
        Arrays.stream(randSeeds)
        .parallel()
        .forEach(randSeed->
        {
          TestData.initializeTestData(JunitUtil.doubleArrayBuilder.Ascending,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(JunitUtil.doubleArrayBuilder.Ascending,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(8*5+2)
  public void testUncheckedStableUnsortedComparatorSortWithAscendingArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Unsorted);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*5+3)
  public void testUncheckedStableAscendingComparatorSortWithAscendingArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*5+4)
  public void testUncheckedUnstableAscendingComparatorSortWithAscendingArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*5+5)
  public void testUncheckedStableAscendingSortWithAscendingArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*5+6)
  public void testUncheckedStableDescendingComparatorSortWithAscendingArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //reverse the stock array
      testData.reverseStockArr();
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*5+7)
  public void testUncheckedUnstableDescendingComparatorSortWithAscendingArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*5+8)
  public void testUncheckedStableDescendingSortWithAscendingArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*6+1)
  public void initializeArraysForDescending()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=double; builder=Descending");
    Arrays.stream(lengths)
    .parallel()
    .forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Descending.isRandomized())
      {
        Arrays.stream(randSeeds)
        .parallel()
        .forEach(randSeed->
        {
          TestData.initializeTestData(JunitUtil.doubleArrayBuilder.Descending,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(JunitUtil.doubleArrayBuilder.Descending,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(8*6+2)
  public void testUncheckedStableUnsortedComparatorSortWithDescendingArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Unsorted);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*6+3)
  public void testUncheckedStableDescendingComparatorSortWithDescendingArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*6+4)
  public void testUncheckedUnstableDescendingComparatorSortWithDescendingArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*6+5)
  public void testUncheckedStableDescendingSortWithDescendingArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*6+6)
  public void testUncheckedStableAscendingComparatorSortWithDescendingArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //reverse the stock array
      testData.reverseStockArr();
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*6+7)
  public void testUncheckedUnstableAscendingComparatorSortWithDescendingArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*6+8)
  public void testUncheckedStableAscendingSortWithDescendingArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*7+1)
  public void initializeArraysForSaw()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=double; builder=Saw");
    Arrays.stream(lengths)
    .parallel()
    .forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Saw.isRandomized())
      {
        Arrays.stream(randSeeds)
        .parallel()
        .forEach(randSeed->
        {
          TestData.initializeTestData(JunitUtil.doubleArrayBuilder.Saw,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(JunitUtil.doubleArrayBuilder.Saw,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(8*7+2)
  public void testUncheckedStableUnsortedComparatorSortWithSawArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Unsorted);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*7+3)
  public void testUncheckedStableAscendingComparatorSortWithSawArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //sort the stock array
      Arrays.sort(stockArr,0,arrLength);
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*7+4)
  public void testUncheckedUnstableAscendingComparatorSortWithSawArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*7+5)
  public void testUncheckedStableAscendingSortWithSawArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*7+6)
  public void testUncheckedStableDescendingComparatorSortWithSawArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //reverse the stock array
      testData.reverseStockArr();
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*7+7)
  public void testUncheckedUnstableDescendingComparatorSortWithSawArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*7+8)
  public void testUncheckedStableDescendingSortWithSawArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*8+1)
  public void initializeArraysForRepeated()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=double; builder=Repeated");
    Arrays.stream(lengths)
    .parallel()
    .forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Repeated.isRandomized())
      {
        Arrays.stream(randSeeds)
        .parallel()
        .forEach(randSeed->
        {
          TestData.initializeTestData(JunitUtil.doubleArrayBuilder.Repeated,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(JunitUtil.doubleArrayBuilder.Repeated,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(8*8+2)
  public void testUncheckedStableUnsortedComparatorSortWithRepeatedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Unsorted);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*8+3)
  public void testUncheckedStableAscendingComparatorSortWithRepeatedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //sort the stock array
      Arrays.sort(stockArr,0,arrLength);
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*8+4)
  public void testUncheckedUnstableAscendingComparatorSortWithRepeatedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*8+5)
  public void testUncheckedStableAscendingSortWithRepeatedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*8+6)
  public void testUncheckedStableDescendingComparatorSortWithRepeatedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //reverse the stock array
      testData.reverseStockArr();
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*8+7)
  public void testUncheckedUnstableDescendingComparatorSortWithRepeatedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*8+8)
  public void testUncheckedStableDescendingSortWithRepeatedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*9+1)
  public void initializeArraysForOrganPipes()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=double; builder=OrganPipes");
    Arrays.stream(lengths)
    .parallel()
    .forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.OrganPipes.isRandomized())
      {
        Arrays.stream(randSeeds)
        .parallel()
        .forEach(randSeed->
        {
          TestData.initializeTestData(JunitUtil.doubleArrayBuilder.OrganPipes,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(JunitUtil.doubleArrayBuilder.OrganPipes,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(8*9+2)
  public void testUncheckedStableUnsortedComparatorSortWithOrganPipesArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Unsorted);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*9+3)
  public void testUncheckedStableAscendingComparatorSortWithOrganPipesArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //sort the stock array
      Arrays.sort(stockArr,0,arrLength);
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*9+4)
  public void testUncheckedUnstableAscendingComparatorSortWithOrganPipesArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*9+5)
  public void testUncheckedStableAscendingSortWithOrganPipesArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*9+6)
  public void testUncheckedStableDescendingComparatorSortWithOrganPipesArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //reverse the stock array
      testData.reverseStockArr();
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*9+7)
  public void testUncheckedUnstableDescendingComparatorSortWithOrganPipesArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*9+8)
  public void testUncheckedStableDescendingSortWithOrganPipesArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*10+1)
  public void initializeArraysForStagger()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=double; builder=Stagger");
    Arrays.stream(lengths)
    .parallel()
    .forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Stagger.isRandomized())
      {
        Arrays.stream(randSeeds)
        .parallel()
        .forEach(randSeed->
        {
          TestData.initializeTestData(JunitUtil.doubleArrayBuilder.Stagger,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(JunitUtil.doubleArrayBuilder.Stagger,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(8*10+2)
  public void testUncheckedStableUnsortedComparatorSortWithStaggerArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Unsorted);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*10+3)
  public void testUncheckedStableAscendingComparatorSortWithStaggerArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //sort the stock array
      Arrays.sort(stockArr,0,arrLength);
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*10+4)
  public void testUncheckedUnstableAscendingComparatorSortWithStaggerArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*10+5)
  public void testUncheckedStableAscendingSortWithStaggerArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*10+6)
  public void testUncheckedStableDescendingComparatorSortWithStaggerArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //reverse the stock array
      testData.reverseStockArr();
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*10+7)
  public void testUncheckedUnstableDescendingComparatorSortWithStaggerArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*10+8)
  public void testUncheckedStableDescendingSortWithStaggerArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*11+1)
  public void initializeArraysForPlateau()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=double; builder=Plateau");
    Arrays.stream(lengths)
    .parallel()
    .forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Plateau.isRandomized())
      {
        Arrays.stream(randSeeds)
        .parallel()
        .forEach(randSeed->
        {
          TestData.initializeTestData(JunitUtil.doubleArrayBuilder.Plateau,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(JunitUtil.doubleArrayBuilder.Plateau,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(8*11+2)
  public void testUncheckedStableUnsortedComparatorSortWithPlateauArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Unsorted);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*11+3)
  public void testUncheckedStableAscendingComparatorSortWithPlateauArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //sort the stock array
      Arrays.sort(stockArr,0,arrLength);
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*11+4)
  public void testUncheckedUnstableAscendingComparatorSortWithPlateauArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*11+5)
  public void testUncheckedStableAscendingSortWithPlateauArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*11+6)
  public void testUncheckedStableDescendingComparatorSortWithPlateauArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //reverse the stock array
      testData.reverseStockArr();
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*11+7)
  public void testUncheckedUnstableDescendingComparatorSortWithPlateauArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*11+8)
  public void testUncheckedStableDescendingSortWithPlateauArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*12+1)
  public void initializeArraysForWithNaNsAndZeros()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=double; builder=WithNaNsAndZeros");
    Arrays.stream(lengths)
    .parallel()
    .forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.WithNaNsAndZeros.isRandomized())
      {
        Arrays.stream(randSeeds)
        .parallel()
        .forEach(randSeed->
        {
          TestData.initializeTestData(JunitUtil.doubleArrayBuilder.WithNaNsAndZeros,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(JunitUtil.doubleArrayBuilder.WithNaNsAndZeros,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(8*12+2)
  public void testUncheckedStableUnsortedComparatorSortWithWithNaNsAndZerosArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Unsorted);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*12+3)
  public void testUncheckedStableAscendingComparatorSortWithWithNaNsAndZerosArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //sort the stock array
      Arrays.sort(stockArr,0,arrLength);
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*12+4)
  public void testUncheckedUnstableAscendingComparatorSortWithWithNaNsAndZerosArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*12+5)
  public void testUncheckedStableAscendingSortWithWithNaNsAndZerosArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*12+6)
  public void testUncheckedStableDescendingComparatorSortWithWithNaNsAndZerosArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //reverse the stock array
      testData.reverseStockArr();
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*12+7)
  public void testUncheckedUnstableDescendingComparatorSortWithWithNaNsAndZerosArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*12+8)
  public void testUncheckedStableDescendingSortWithWithNaNsAndZerosArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*13+1)
  public void initializeArraysForShuffle()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=double; builder=Shuffle");
    Arrays.stream(lengths)
    .parallel()
    .forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Shuffle.isRandomized())
      {
        Arrays.stream(randSeeds)
        .parallel()
        .forEach(randSeed->
        {
          TestData.initializeTestData(JunitUtil.doubleArrayBuilder.Shuffle,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(JunitUtil.doubleArrayBuilder.Shuffle,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(8*13+2)
  public void testUncheckedStableUnsortedComparatorSortWithShuffleArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Unsorted);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*13+3)
  public void testUncheckedStableAscendingComparatorSortWithShuffleArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //sort the stock array
      Arrays.sort(stockArr,0,arrLength);
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*13+4)
  public void testUncheckedUnstableAscendingComparatorSortWithShuffleArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*13+5)
  public void testUncheckedStableAscendingSortWithShuffleArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*13+6)
  public void testUncheckedStableDescendingComparatorSortWithShuffleArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //reverse the stock array
      testData.reverseStockArr();
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*13+7)
  public void testUncheckedUnstableDescendingComparatorSortWithShuffleArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*13+8)
  public void testUncheckedStableDescendingSortWithShuffleArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*14+1)
  public void initializeArraysForRandomized()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=double; builder=Randomized");
    Arrays.stream(lengths)
    .parallel()
    .forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Randomized.isRandomized())
      {
        Arrays.stream(randSeeds)
        .parallel()
        .forEach(randSeed->
        {
          TestData.initializeTestData(JunitUtil.doubleArrayBuilder.Randomized,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(JunitUtil.doubleArrayBuilder.Randomized,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(8*14+2)
  public void testUncheckedStableUnsortedComparatorSortWithRandomizedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Unsorted);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*14+3)
  public void testUncheckedStableAscendingComparatorSortWithRandomizedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //sort the stock array
      Arrays.sort(stockArr,0,arrLength);
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*14+4)
  public void testUncheckedUnstableAscendingComparatorSortWithRandomizedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*14+5)
  public void testUncheckedStableAscendingSortWithRandomizedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*14+6)
  public void testUncheckedStableDescendingComparatorSortWithRandomizedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //reverse the stock array
      testData.reverseStockArr();
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*14+7)
  public void testUncheckedUnstableDescendingComparatorSortWithRandomizedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*14+8)
  public void testUncheckedStableDescendingSortWithRandomizedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*15+1)
  public void initializeArraysForDuplicated()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=double; builder=Duplicated");
    Arrays.stream(lengths)
    .parallel()
    .forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Duplicated.isRandomized())
      {
        Arrays.stream(randSeeds)
        .parallel()
        .forEach(randSeed->
        {
          TestData.initializeTestData(JunitUtil.doubleArrayBuilder.Duplicated,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(JunitUtil.doubleArrayBuilder.Duplicated,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(8*15+2)
  public void testUncheckedStableUnsortedComparatorSortWithDuplicatedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Unsorted);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*15+3)
  public void testUncheckedStableAscendingComparatorSortWithDuplicatedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //sort the stock array
      Arrays.sort(stockArr,0,arrLength);
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*15+4)
  public void testUncheckedUnstableAscendingComparatorSortWithDuplicatedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*15+5)
  public void testUncheckedStableAscendingSortWithDuplicatedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*15+6)
  public void testUncheckedStableDescendingComparatorSortWithDuplicatedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //reverse the stock array
      testData.reverseStockArr();
      DoubleSortUtil.uncheckedStableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*15+7)
  public void testUncheckedUnstableDescendingComparatorSortWithDuplicatedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(8*15+8)
  public void testUncheckedStableDescendingSortWithDuplicatedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      DoubleSortUtil.uncheckedDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @AfterAll
  public static void stopTimer()
  {
    long dur=System.currentTimeMillis()-TIMER;
    StringBuilder builder=new StringBuilder(34).append("Test suite time: ");
    long hours=dur/(1000*60*60);
    long minutes=(dur-=(hours*1000*60*60))/(1000*60);
    long seconds=(dur-=(minutes*1000*60))/1000;
    dur=(dur-=(seconds*1000));
    minutesLoop: for(;;)
    {
      for(;;)
      {
        if(hours==0)
        {
          if(minutes==0)
          {
            break minutesLoop;
          }
          break;
        }
        builder.append(hours).append(':');
        if(minutes<10)
        {
          builder.append('0');
        }
        break;
      }
      builder.append(minutes).append(':');
      if(seconds<10)
      {
        builder.append('0');
      }
      break;
    }
    builder.append(seconds).append('.');
    if(dur<100)
    {
      builder.append('0');
      if(dur<10)
      {
        builder.append('0');
      }
    }
    builder.append(dur);
    System.out.println(builder);
  }
/*
    @Test
    public void testSortMethods()
    {
      final var arrays=initializeArrays();
      arrays.stream()
      .parallel()
      .forEach(arr->
      {
        int arrLength=arr.length;
        double[] copy=new double[arrLength];
        ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
        //test stable unsorted comparator sort
        DoubleSortUtil.uncheckedStableSort(arr,0,arrLength,Unsorted);
        JunitUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,arrLength);  
        Arrays.sort(copy,0,arrLength);
        double[] copy1;
        //test unstable ascending comparator sort
        copy1=new double[arrLength];
        ArrCopy.uncheckedCopy(arr,0,copy1,0,arrLength);
        DoubleSortUtil.uncheckedUnstableSort(copy1,0,arrLength,Ascending);
        JunitUtil.uncheckedparallelassertarraysAreEqual(copy1,0,copy,0,arrLength);  
        //test stable ascending comparator sort  
        copy1=new double[arrLength];
        ArrCopy.uncheckedCopy(arr,0,copy1,0,arrLength);
        DoubleSortUtil.uncheckedStableSort(copy1,0,arrLength,Ascending);
        JunitUtil.uncheckedparallelassertarraysAreEqual(copy1,0,copy,0,arrLength);  
        //test stable ascending non-comparator sort
        copy1=new double[arrLength];
        ArrCopy.uncheckedCopy(arr,0,copy1,0,arrLength);
        DoubleSortUtil.uncheckedAscendingSort(copy1,0,arrLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(copy1,0,copy,0,arrLength);  
        OmniArray.OfDouble.reverseRange(copy,0,arrLength-1);
        //test unstable descending comparator sort
        copy1=new double[arrLength];
        ArrCopy.uncheckedCopy(arr,0,copy1,0,arrLength);
        DoubleSortUtil.uncheckedUnstableSort(copy1,0,arrLength,Descending);
        JunitUtil.uncheckedparallelassertarraysAreEqual(copy1,0,copy,0,arrLength);  
        //test stable descending comparator sort
        copy1=new double[arrLength];
        ArrCopy.uncheckedCopy(arr,0,copy1,0,arrLength);
        DoubleSortUtil.uncheckedStableSort(copy1,0,arrLength,Descending);
        JunitUtil.uncheckedparallelassertarraysAreEqual(copy1,0,copy,0,arrLength);  
        //test stable descending non-comparator sort
        DoubleSortUtil.uncheckedDescendingSort(arr,0,arrLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,arrLength);  
      });
    }
*/
}
