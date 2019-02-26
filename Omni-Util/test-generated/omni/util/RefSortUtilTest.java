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
import java.util.Comparator;
@TestMethodOrder(OrderAnnotation.class)
public class RefSortUtilTest
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
    final JunitUtil.IntegerArrayBuilder builder;
    final Integer[] customArr;
    final int[] stockArr;
    private TestData(long randSeed,int m,JunitUtil.IntegerArrayBuilder builder,int arrLength,Random rand)
    {
      this.randSeed=randSeed;
      this.m=m;
      this.builder=builder;
      this.customArr=new Integer[arrLength];
      this.stockArr=new int[arrLength];
      builder.buildUnchecked(customArr,0,arrLength,rand,m);
      ArrCopy.uncheckedCopy(customArr,0,stockArr,0,arrLength);
    }
    private Integer[] copyCustomArr()
    {
      Integer[] copy=new Integer[customArr.length];
      ArrCopy.uncheckedCopy(customArr,0,copy,0,customArr.length);
      return copy;
    }
    private void reverseStockArr()
    {
      OmniArray.OfInt.reverseRange(stockArr,0,stockArr.length-1);
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
      return "TestData{builder="+builder+"; arrayType=Object; arrLength="+customArr.length+"; m="+m+"; randSeed="+randSeed+"}";
    }
    private static void initializeTestData(JunitUtil.IntegerArrayBuilder builder,long randSeed,int arrLength,ArrayList<TestData> testDatas)
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
    private static int getMHi(int arrLength,JunitUtil.IntegerArrayBuilder builder)
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
    private static int getNumReps(int arrLength,JunitUtil.IntegerArrayBuilder builder)
    {
      switch(builder)
      {
      case Randomized:
        return 20;
      case Duplicated:
        return 1;
      case Shuffle:
        return 20;
      default:
        return 1;
      }
    }
    private static int incrementM(int m,JunitUtil.IntegerArrayBuilder builder)
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
  private static final Comparator<Integer> Unsorted=(val1,val2)->
  {
    return 0;
  };
  private static final Comparator<Integer> Ascending=(val1,val2)->
  {
    return val1.compareTo(val2);
  };
  private static final Comparator<Integer> Descending=(val1,val2)->
  {
    return val2.compareTo(val1);
  };
  @Test
  @Order(10*0+1)
  public void initializeArraysForAllEquals()
  {
    System.out.println("Initializing arrays for arrType=Object; builder=AllEquals");
    Arrays.stream(lengths)
    .parallel()
    .forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.AllEquals.isRandomized())
      {
        Arrays.stream(randSeeds)
        .parallel()
        .forEach(randSeed->
        {
          TestData.initializeTestData(JunitUtil.IntegerArrayBuilder.AllEquals,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(JunitUtil.IntegerArrayBuilder.AllEquals,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(10*0+2)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Unsorted);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*0+3)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*0+4)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*0+5)
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
      RefSortUtil.uncheckedStableDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*0+6)
  public void testUncheckedUnstableDescendingSortWithAllEqualsArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedUnstableDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*0+7)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*0+8)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*0+9)
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
      RefSortUtil.uncheckedStableAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*0+10)
  public void testUncheckedUnstableAscendingSortWithAllEqualsArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedUnstableAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*1+1)
  public void initializeArraysForMergeAscending()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=Object; builder=MergeAscending");
    Arrays.stream(lengths)
    .parallel()
    .forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.MergeAscending.isRandomized())
      {
        Arrays.stream(randSeeds)
        .parallel()
        .forEach(randSeed->
        {
          TestData.initializeTestData(JunitUtil.IntegerArrayBuilder.MergeAscending,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(JunitUtil.IntegerArrayBuilder.MergeAscending,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(10*1+2)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Unsorted);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*1+3)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*1+4)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*1+5)
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
      RefSortUtil.uncheckedStableAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*1+6)
  public void testUncheckedUnstableAscendingSortWithMergeAscendingArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedUnstableAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*1+7)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*1+8)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*1+9)
  public void testUncheckedStableDescendingSortWithMergeAscendingArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedStableDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*1+10)
  public void testUncheckedUnstableDescendingSortWithMergeAscendingArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedUnstableDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*2+1)
  public void initializeArraysForMergeDescending()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=Object; builder=MergeDescending");
    Arrays.stream(lengths)
    .parallel()
    .forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.MergeDescending.isRandomized())
      {
        Arrays.stream(randSeeds)
        .parallel()
        .forEach(randSeed->
        {
          TestData.initializeTestData(JunitUtil.IntegerArrayBuilder.MergeDescending,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(JunitUtil.IntegerArrayBuilder.MergeDescending,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(10*2+2)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Unsorted);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*2+3)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*2+4)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*2+5)
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
      RefSortUtil.uncheckedStableAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*2+6)
  public void testUncheckedUnstableAscendingSortWithMergeDescendingArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedUnstableAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*2+7)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*2+8)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*2+9)
  public void testUncheckedStableDescendingSortWithMergeDescendingArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedStableDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*2+10)
  public void testUncheckedUnstableDescendingSortWithMergeDescendingArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedUnstableDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*3+1)
  public void initializeArraysForSortedRepeated()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=Object; builder=SortedRepeated");
    Arrays.stream(lengths)
    .parallel()
    .forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.SortedRepeated.isRandomized())
      {
        Arrays.stream(randSeeds)
        .parallel()
        .forEach(randSeed->
        {
          TestData.initializeTestData(JunitUtil.IntegerArrayBuilder.SortedRepeated,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(JunitUtil.IntegerArrayBuilder.SortedRepeated,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(10*3+2)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Unsorted);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*3+3)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*3+4)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*3+5)
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
      RefSortUtil.uncheckedStableAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*3+6)
  public void testUncheckedUnstableAscendingSortWithSortedRepeatedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedUnstableAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*3+7)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*3+8)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*3+9)
  public void testUncheckedStableDescendingSortWithSortedRepeatedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedStableDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*3+10)
  public void testUncheckedUnstableDescendingSortWithSortedRepeatedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedUnstableDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*4+1)
  public void initializeArraysForSortedOrganPipes()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=Object; builder=SortedOrganPipes");
    Arrays.stream(lengths)
    .parallel()
    .forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.SortedOrganPipes.isRandomized())
      {
        Arrays.stream(randSeeds)
        .parallel()
        .forEach(randSeed->
        {
          TestData.initializeTestData(JunitUtil.IntegerArrayBuilder.SortedOrganPipes,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(JunitUtil.IntegerArrayBuilder.SortedOrganPipes,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(10*4+2)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Unsorted);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*4+3)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*4+4)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*4+5)
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
      RefSortUtil.uncheckedStableAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*4+6)
  public void testUncheckedUnstableAscendingSortWithSortedOrganPipesArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedUnstableAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*4+7)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*4+8)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*4+9)
  public void testUncheckedStableDescendingSortWithSortedOrganPipesArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedStableDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*4+10)
  public void testUncheckedUnstableDescendingSortWithSortedOrganPipesArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedUnstableDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*5+1)
  public void initializeArraysForAscending()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=Object; builder=Ascending");
    Arrays.stream(lengths)
    .parallel()
    .forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Ascending.isRandomized())
      {
        Arrays.stream(randSeeds)
        .parallel()
        .forEach(randSeed->
        {
          TestData.initializeTestData(JunitUtil.IntegerArrayBuilder.Ascending,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(JunitUtil.IntegerArrayBuilder.Ascending,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(10*5+2)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Unsorted);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*5+3)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*5+4)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*5+5)
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
      RefSortUtil.uncheckedStableAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*5+6)
  public void testUncheckedUnstableAscendingSortWithAscendingArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedUnstableAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*5+7)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*5+8)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*5+9)
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
      RefSortUtil.uncheckedStableDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*5+10)
  public void testUncheckedUnstableDescendingSortWithAscendingArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedUnstableDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*6+1)
  public void initializeArraysForDescending()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=Object; builder=Descending");
    Arrays.stream(lengths)
    .parallel()
    .forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Descending.isRandomized())
      {
        Arrays.stream(randSeeds)
        .parallel()
        .forEach(randSeed->
        {
          TestData.initializeTestData(JunitUtil.IntegerArrayBuilder.Descending,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(JunitUtil.IntegerArrayBuilder.Descending,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(10*6+2)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Unsorted);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*6+3)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*6+4)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*6+5)
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
      RefSortUtil.uncheckedStableDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*6+6)
  public void testUncheckedUnstableDescendingSortWithDescendingArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedUnstableDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*6+7)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*6+8)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*6+9)
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
      RefSortUtil.uncheckedStableAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*6+10)
  public void testUncheckedUnstableAscendingSortWithDescendingArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedUnstableAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*7+1)
  public void initializeArraysForSaw()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=Object; builder=Saw");
    Arrays.stream(lengths)
    .parallel()
    .forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Saw.isRandomized())
      {
        Arrays.stream(randSeeds)
        .parallel()
        .forEach(randSeed->
        {
          TestData.initializeTestData(JunitUtil.IntegerArrayBuilder.Saw,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(JunitUtil.IntegerArrayBuilder.Saw,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(10*7+2)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Unsorted);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*7+3)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*7+4)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*7+5)
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
      RefSortUtil.uncheckedStableAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*7+6)
  public void testUncheckedUnstableAscendingSortWithSawArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedUnstableAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*7+7)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*7+8)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*7+9)
  public void testUncheckedStableDescendingSortWithSawArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedStableDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*7+10)
  public void testUncheckedUnstableDescendingSortWithSawArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedUnstableDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*8+1)
  public void initializeArraysForRepeated()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=Object; builder=Repeated");
    Arrays.stream(lengths)
    .parallel()
    .forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Repeated.isRandomized())
      {
        Arrays.stream(randSeeds)
        .parallel()
        .forEach(randSeed->
        {
          TestData.initializeTestData(JunitUtil.IntegerArrayBuilder.Repeated,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(JunitUtil.IntegerArrayBuilder.Repeated,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(10*8+2)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Unsorted);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*8+3)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*8+4)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*8+5)
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
      RefSortUtil.uncheckedStableAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*8+6)
  public void testUncheckedUnstableAscendingSortWithRepeatedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedUnstableAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*8+7)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*8+8)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*8+9)
  public void testUncheckedStableDescendingSortWithRepeatedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedStableDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*8+10)
  public void testUncheckedUnstableDescendingSortWithRepeatedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedUnstableDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*9+1)
  public void initializeArraysForOrganPipes()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=Object; builder=OrganPipes");
    Arrays.stream(lengths)
    .parallel()
    .forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.OrganPipes.isRandomized())
      {
        Arrays.stream(randSeeds)
        .parallel()
        .forEach(randSeed->
        {
          TestData.initializeTestData(JunitUtil.IntegerArrayBuilder.OrganPipes,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(JunitUtil.IntegerArrayBuilder.OrganPipes,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(10*9+2)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Unsorted);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*9+3)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*9+4)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*9+5)
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
      RefSortUtil.uncheckedStableAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*9+6)
  public void testUncheckedUnstableAscendingSortWithOrganPipesArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedUnstableAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*9+7)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*9+8)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*9+9)
  public void testUncheckedStableDescendingSortWithOrganPipesArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedStableDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*9+10)
  public void testUncheckedUnstableDescendingSortWithOrganPipesArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedUnstableDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*10+1)
  public void initializeArraysForStagger()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=Object; builder=Stagger");
    Arrays.stream(lengths)
    .parallel()
    .forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Stagger.isRandomized())
      {
        Arrays.stream(randSeeds)
        .parallel()
        .forEach(randSeed->
        {
          TestData.initializeTestData(JunitUtil.IntegerArrayBuilder.Stagger,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(JunitUtil.IntegerArrayBuilder.Stagger,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(10*10+2)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Unsorted);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*10+3)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*10+4)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*10+5)
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
      RefSortUtil.uncheckedStableAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*10+6)
  public void testUncheckedUnstableAscendingSortWithStaggerArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedUnstableAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*10+7)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*10+8)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*10+9)
  public void testUncheckedStableDescendingSortWithStaggerArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedStableDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*10+10)
  public void testUncheckedUnstableDescendingSortWithStaggerArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedUnstableDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*11+1)
  public void initializeArraysForPlateau()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=Object; builder=Plateau");
    Arrays.stream(lengths)
    .parallel()
    .forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Plateau.isRandomized())
      {
        Arrays.stream(randSeeds)
        .parallel()
        .forEach(randSeed->
        {
          TestData.initializeTestData(JunitUtil.IntegerArrayBuilder.Plateau,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(JunitUtil.IntegerArrayBuilder.Plateau,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(10*11+2)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Unsorted);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*11+3)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*11+4)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*11+5)
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
      RefSortUtil.uncheckedStableAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*11+6)
  public void testUncheckedUnstableAscendingSortWithPlateauArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedUnstableAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*11+7)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*11+8)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*11+9)
  public void testUncheckedStableDescendingSortWithPlateauArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedStableDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*11+10)
  public void testUncheckedUnstableDescendingSortWithPlateauArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedUnstableDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*12+1)
  public void initializeArraysForShuffle()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=Object; builder=Shuffle");
    Arrays.stream(lengths)
    .parallel()
    .forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Shuffle.isRandomized())
      {
        Arrays.stream(randSeeds)
        .parallel()
        .forEach(randSeed->
        {
          TestData.initializeTestData(JunitUtil.IntegerArrayBuilder.Shuffle,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(JunitUtil.IntegerArrayBuilder.Shuffle,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(10*12+2)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Unsorted);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*12+3)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*12+4)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*12+5)
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
      RefSortUtil.uncheckedStableAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*12+6)
  public void testUncheckedUnstableAscendingSortWithShuffleArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedUnstableAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*12+7)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*12+8)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*12+9)
  public void testUncheckedStableDescendingSortWithShuffleArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedStableDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*12+10)
  public void testUncheckedUnstableDescendingSortWithShuffleArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedUnstableDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*13+1)
  public void initializeArraysForRandomized()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=Object; builder=Randomized");
    Arrays.stream(lengths)
    .parallel()
    .forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Randomized.isRandomized())
      {
        Arrays.stream(randSeeds)
        .parallel()
        .forEach(randSeed->
        {
          TestData.initializeTestData(JunitUtil.IntegerArrayBuilder.Randomized,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(JunitUtil.IntegerArrayBuilder.Randomized,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(10*13+2)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Unsorted);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*13+3)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*13+4)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*13+5)
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
      RefSortUtil.uncheckedStableAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*13+6)
  public void testUncheckedUnstableAscendingSortWithRandomizedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedUnstableAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*13+7)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*13+8)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*13+9)
  public void testUncheckedStableDescendingSortWithRandomizedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedStableDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*13+10)
  public void testUncheckedUnstableDescendingSortWithRandomizedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedUnstableDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*14+1)
  public void initializeArraysForDuplicated()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=Object; builder=Duplicated");
    Arrays.stream(lengths)
    .parallel()
    .forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Duplicated.isRandomized())
      {
        Arrays.stream(randSeeds)
        .parallel()
        .forEach(randSeed->
        {
          TestData.initializeTestData(JunitUtil.IntegerArrayBuilder.Duplicated,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(JunitUtil.IntegerArrayBuilder.Duplicated,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(10*14+2)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Unsorted);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*14+3)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*14+4)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*14+5)
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
      RefSortUtil.uncheckedStableAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*14+6)
  public void testUncheckedUnstableAscendingSortWithDuplicatedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedUnstableAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*14+7)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*14+8)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*14+9)
  public void testUncheckedStableDescendingSortWithDuplicatedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedStableDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(10*14+10)
  public void testUncheckedUnstableDescendingSortWithDuplicatedArray()
  {
    TEST_DATA.stream()
      .parallel()
      .forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedUnstableDescendingSort(customArr,0,arrLength);
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
        int[] copy=new int[arrLength];
        ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
        //test stable unsorted comparator sort
        RefSortUtil.uncheckedStableSort(arr,0,arrLength,Unsorted);
        JunitUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,arrLength);  
        Arrays.sort(copy,0,arrLength);
        Object[] copy1;
        //test unstable ascending comparator sort
        copy1=new Object[arrLength];
        ArrCopy.uncheckedCopy(arr,0,copy1,0,arrLength);
        RefSortUtil.uncheckedUnstableSort(copy1,0,arrLength,Ascending);
        JunitUtil.uncheckedparallelassertarraysAreEqual(copy1,0,copy,0,arrLength);  
        //test unstable ascending non-comparator sort
        copy1=new Object[arrLength];
        ArrCopy.uncheckedCopy(arr,0,copy1,0,arrLength);
        RefSortUtil.uncheckedUnstableAscendingSort(copy1,0,arrLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(copy1,0,copy,0,arrLength);  
        //test stable ascending comparator sort  
        copy1=new Object[arrLength];
        ArrCopy.uncheckedCopy(arr,0,copy1,0,arrLength);
        RefSortUtil.uncheckedStableSort(copy1,0,arrLength,Ascending);
        JunitUtil.uncheckedparallelassertarraysAreEqual(copy1,0,copy,0,arrLength);  
        //test stable ascending non-comparator sort
        copy1=new Object[arrLength];
        ArrCopy.uncheckedCopy(arr,0,copy1,0,arrLength);
        RefSortUtil.uncheckedStableAscendingSort(copy1,0,arrLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(copy1,0,copy,0,arrLength);  
        OmniArray.OfInt.reverseRange(copy,0,arrLength-1);
        //test unstable descending comparator sort
        copy1=new Object[arrLength];
        ArrCopy.uncheckedCopy(arr,0,copy1,0,arrLength);
        RefSortUtil.uncheckedUnstableSort(copy1,0,arrLength,Descending);
        JunitUtil.uncheckedparallelassertarraysAreEqual(copy1,0,copy,0,arrLength);  
        //test unstable descending non-comparator sort
        copy1=new Object[arrLength];
        ArrCopy.uncheckedCopy(arr,0,copy1,0,arrLength);
        RefSortUtil.uncheckedUnstableDescendingSort(copy1,0,arrLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(copy1,0,copy,0,arrLength);  
        //test stable descending comparator sort
        copy1=new Object[arrLength];
        ArrCopy.uncheckedCopy(arr,0,copy1,0,arrLength);
        RefSortUtil.uncheckedStableSort(copy1,0,arrLength,Descending);
        JunitUtil.uncheckedparallelassertarraysAreEqual(copy1,0,copy,0,arrLength);  
        //test stable descending non-comparator sort
        RefSortUtil.uncheckedStableDescendingSort(arr,0,arrLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,arrLength);  
      });
    }
*/
}
