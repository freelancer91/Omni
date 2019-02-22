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
import omni.function.BooleanComparator;
@TestMethodOrder(OrderAnnotation.class)
public class BooleanSortUtilTest
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
    final JunitUtil.booleanArrayBuilder builder;
    final boolean[] customArr;
    final Boolean[] stockArr;
    private TestData(long randSeed,int m,JunitUtil.booleanArrayBuilder builder,int arrLength,Random rand)
    {
      this.randSeed=randSeed;
      this.m=m;
      this.builder=builder;
      this.customArr=new boolean[arrLength];
      this.stockArr=new Boolean[arrLength];
      builder.buildUnchecked(customArr,0,arrLength,rand,m);
      ArrCopy.uncheckedCopy(customArr,0,stockArr,0,arrLength);
    }
    private boolean[] copyCustomArr()
    {
      boolean[] copy=new boolean[customArr.length];
      ArrCopy.uncheckedCopy(customArr,0,copy,0,customArr.length);
      return copy;
    }
    private void reverseStockArr()
    {
      OmniArray.OfRef.reverseRange(stockArr,0,stockArr.length-1);
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
      return "TestData{builder="+builder+"; arrayType=boolean; arrLength="+customArr.length+"; m="+m+"; randSeed="+randSeed+"}";
    }
    private static void initializeTestData(JunitUtil.booleanArrayBuilder builder,long randSeed,int arrLength,ArrayList<TestData> testDatas)
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
    private static int getMHi(int arrLength,JunitUtil.booleanArrayBuilder builder)
    {
      switch(builder)
      {
      default:
        return 1;
      }
    }
    private static int getNumReps(int arrLength,JunitUtil.booleanArrayBuilder builder)
    {
      switch(builder)
      {
      case Randomized:
        return 10;
      default:
        return 1;
      }
    }
    private static int incrementM(int m,JunitUtil.booleanArrayBuilder builder)
    {
      switch(builder)
      {
      default:
        return m+1;
      }
    }
  }
  private static final BooleanComparator Unsorted=(val1,val2)->
  {
    return 0;
  };
  private static final BooleanComparator Ascending=(val1,val2)->
  {
    return Boolean.compare(val1,val2);
  };
  private static final BooleanComparator Descending=(val1,val2)->
  {
    return Boolean.compare(val2,val1);
  };
  @Test
  @Order(6*0+1)
  public void initializeArraysForAllEquals()
  {
    System.out.println("Initializing arrays for arrType=boolean; builder=AllEquals");
    Arrays.stream(lengths)
    .parallel()
    .forEach(arrLength->
    {
      if(JunitUtil.booleanArrayBuilder.AllEquals.isRandomized())
      {
        Arrays.stream(randSeeds)
        .parallel()
        .forEach(randSeed->
        {
          TestData.initializeTestData(JunitUtil.booleanArrayBuilder.AllEquals,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(JunitUtil.booleanArrayBuilder.AllEquals,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(6*0+2)
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
      BooleanSortUtil.uncheckedSort(customArr,0,arrLength,Unsorted);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(6*0+3)
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
      BooleanSortUtil.uncheckedSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(6*0+4)
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
      BooleanSortUtil.uncheckedDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(6*0+5)
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
      BooleanSortUtil.uncheckedSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(6*0+6)
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
      BooleanSortUtil.uncheckedAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(6*1+1)
  public void initializeArraysForAscending()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=boolean; builder=Ascending");
    Arrays.stream(lengths)
    .parallel()
    .forEach(arrLength->
    {
      if(JunitUtil.booleanArrayBuilder.Ascending.isRandomized())
      {
        Arrays.stream(randSeeds)
        .parallel()
        .forEach(randSeed->
        {
          TestData.initializeTestData(JunitUtil.booleanArrayBuilder.Ascending,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(JunitUtil.booleanArrayBuilder.Ascending,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(6*1+2)
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
      BooleanSortUtil.uncheckedSort(customArr,0,arrLength,Unsorted);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(6*1+3)
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
      BooleanSortUtil.uncheckedSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(6*1+4)
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
      BooleanSortUtil.uncheckedAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(6*1+5)
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
      BooleanSortUtil.uncheckedSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(6*1+6)
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
      BooleanSortUtil.uncheckedDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(6*2+1)
  public void initializeArraysForDescending()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=boolean; builder=Descending");
    Arrays.stream(lengths)
    .parallel()
    .forEach(arrLength->
    {
      if(JunitUtil.booleanArrayBuilder.Descending.isRandomized())
      {
        Arrays.stream(randSeeds)
        .parallel()
        .forEach(randSeed->
        {
          TestData.initializeTestData(JunitUtil.booleanArrayBuilder.Descending,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(JunitUtil.booleanArrayBuilder.Descending,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(6*2+2)
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
      BooleanSortUtil.uncheckedSort(customArr,0,arrLength,Unsorted);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(6*2+3)
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
      BooleanSortUtil.uncheckedSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(6*2+4)
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
      BooleanSortUtil.uncheckedDescendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(6*2+5)
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
      BooleanSortUtil.uncheckedSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(6*2+6)
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
      BooleanSortUtil.uncheckedAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(6*3+1)
  public void initializeArraysForRandomized()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=boolean; builder=Randomized");
    Arrays.stream(lengths)
    .parallel()
    .forEach(arrLength->
    {
      if(JunitUtil.booleanArrayBuilder.Randomized.isRandomized())
      {
        Arrays.stream(randSeeds)
        .parallel()
        .forEach(randSeed->
        {
          TestData.initializeTestData(JunitUtil.booleanArrayBuilder.Randomized,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(JunitUtil.booleanArrayBuilder.Randomized,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(6*3+2)
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
      BooleanSortUtil.uncheckedSort(customArr,0,arrLength,Unsorted);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(6*3+3)
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
      BooleanSortUtil.uncheckedSort(customArr,0,arrLength,Ascending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(6*3+4)
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
      BooleanSortUtil.uncheckedAscendingSort(customArr,0,arrLength);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(6*3+5)
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
      BooleanSortUtil.uncheckedSort(customArr,0,arrLength,Descending);
      JunitUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
    });
  }
  @Test
  @Order(6*3+6)
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
      BooleanSortUtil.uncheckedDescendingSort(customArr,0,arrLength);
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
        Boolean[] copy=new Boolean[arrLength];
        ArrCopy.uncheckedCopy(arr,0,copy,0,arrLength);
        //test stable unsorted comparator sort
        BooleanSortUtil.uncheckedSort(arr,0,arrLength,Unsorted);
        JunitUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,arrLength);  
        Arrays.sort(copy,0,arrLength);
        boolean[] copy1;
        //test stable ascending comparator sort  
        copy1=new boolean[arrLength];
        ArrCopy.uncheckedCopy(arr,0,copy1,0,arrLength);
        BooleanSortUtil.uncheckedSort(copy1,0,arrLength,Ascending);
        JunitUtil.uncheckedparallelassertarraysAreEqual(copy1,0,copy,0,arrLength);  
        //test stable ascending non-comparator sort
        copy1=new boolean[arrLength];
        ArrCopy.uncheckedCopy(arr,0,copy1,0,arrLength);
        BooleanSortUtil.uncheckedAscendingSort(copy1,0,arrLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(copy1,0,copy,0,arrLength);  
        OmniArray.OfRef.reverseRange(copy,0,arrLength-1);
        //test stable descending comparator sort
        copy1=new boolean[arrLength];
        ArrCopy.uncheckedCopy(arr,0,copy1,0,arrLength);
        BooleanSortUtil.uncheckedSort(copy1,0,arrLength,Descending);
        JunitUtil.uncheckedparallelassertarraysAreEqual(copy1,0,copy,0,arrLength);  
        //test stable descending non-comparator sort
        BooleanSortUtil.uncheckedDescendingSort(arr,0,arrLength);
        JunitUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,arrLength);  
      });
    }
*/
}
