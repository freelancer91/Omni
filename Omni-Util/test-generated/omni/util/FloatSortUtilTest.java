package omni.util;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Assertions;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;
@TestMethodOrder(OrderAnnotation.class)
public class FloatSortUtilTest
{
  private static final boolean LONGRUN=true;
  private static final boolean PARALLEL=true;
  private static final int[] lengths;
  private static final long[] randSeeds;
  private static final ArrayList<TestData> TEST_DATA=new ArrayList<>();
  static
  {
   if(LONGRUN)
    {
      lengths=new int[]{2, 3, 5, 8, 13, 21, 34, 46, 47, 55, 100, 286, 287, 1000, 10000, 100000, 1000000};
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
    final floatTestDataBuilder builder;
    final float[] customArr;
    final float[] stockArr;
    private TestData(long randSeed,int m,floatTestDataBuilder builder,int arrLength,Random rand)
    {
      this.randSeed=randSeed;
      this.m=m;
      this.builder=builder;
      this.customArr=new float[arrLength];
      this.stockArr=new float[arrLength];
      builder.buildUnchecked(customArr,0,arrLength,rand,m);
      ArrCopy.uncheckedCopy(customArr,0,stockArr,0,arrLength);
    }
    private float[] copyCustomArr()
    {
      float[] copy=new float[customArr.length];
      ArrCopy.uncheckedCopy(customArr,0,copy,0,customArr.length);
      return copy;
    }
    private void reverseStockArr()
    {
      OmniArray.OfFloat.reverseRange(stockArr,0,stockArr.length-1);
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
      return "TestData{builder="+builder+"; arrayType=float; arrLength="+customArr.length+"; m="+m+"; randSeed="+randSeed+"}";
    }
    private static void initializeTestData(floatTestDataBuilder builder,long randSeed,int arrLength,ArrayList<TestData> testDatas)
    {
      Random rand=new Random(randSeed);
      for(int m=builder.getMLo(),mHi=builder.getMHi(arrLength),numReps=builder.getNumSortReps(arrLength);m<=mHi;m=builder.incrementM(m))
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
  }
  @Test
  public void testIsStableUnstableComparatorSort()
  {
    Random rand=new Random(0);
    float[] customArr=new float[4000];
    floatTestDataBuilder.Randomized.buildUnchecked(customArr,0,customArr.length,rand,0);
    float[] copy=new float[customArr.length];
    ArrCopy.uncheckedCopy(customArr,0,copy,0,customArr.length);
    FloatSortUtil.uncheckedStableSort(customArr,0,customArr.length,floatComparators.Unstable);
    int oddIndex;
    //find the first odd index
    for(oddIndex=0;oddIndex<customArr.length;++oddIndex)
    {
      if((Float.floatToRawIntBits(customArr[oddIndex])&0b1)!=0)
      {
        break;
      }
    }
    int evenIndex=0;
    for(int i=0;i<copy.length;++i)
    {
      if((Float.floatToRawIntBits(copy[i])&0b1)!=0)
      {
        //is odd
        Assertions.assertTrue(EqualityUtil.isEqual(copy[i],customArr[oddIndex++]));
      }
      else
      {
        //is even
        Assertions.assertTrue(EqualityUtil.isEqual(copy[i],customArr[evenIndex++]));
      }
    }
  }
  @Test
  @Order(8*0+1)
  public void initializeArraysForAllEquals()
  {
    System.out.println("Initializing arrays for arrType=float; builder=AllEquals");
    var lengthStream=Arrays.stream(lengths);
    if(PARALLEL)
    {
      lengthStream=lengthStream.parallel();
    }
    lengthStream.forEach(arrLength->
    {
      if(floatTestDataBuilder.AllEquals.isRandomized())
      {
        var randStream=Arrays.stream(randSeeds);
        if(PARALLEL)
        {
          randStream=randStream.parallel();
        }
        randStream.forEach(randSeed->
        {
          TestData.initializeTestData(floatTestDataBuilder.AllEquals,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(floatTestDataBuilder.AllEquals,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(8*0+2)
  public void testUncheckedStableUnsortedComparatorSortWithAllEqualsArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Unsorted);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*0+3)
  public void testUncheckedStableDescendingComparatorSortWithAllEqualsArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Descending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*0+4)
  public void testUncheckedUnstableDescendingComparatorSortWithAllEqualsArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedUnstableSort(customArr,0,arrLength,floatComparators.Descending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*0+5)
  public void testUncheckedStableDescendingSortWithAllEqualsArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedDescendingSort(customArr,0,arrLength);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*0+6)
  public void testUncheckedStableAscendingComparatorSortWithAllEqualsArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Ascending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*0+7)
  public void testUncheckedUnstableAscendingComparatorSortWithAllEqualsArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedUnstableSort(customArr,0,arrLength,floatComparators.Ascending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*0+8)
  public void testUncheckedStableAscendingSortWithAllEqualsArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedAscendingSort(customArr,0,arrLength);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*1+1)
  public void initializeArraysForMergeAscending()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=float; builder=MergeAscending");
    var lengthStream=Arrays.stream(lengths);
    if(PARALLEL)
    {
      lengthStream=lengthStream.parallel();
    }
    lengthStream.forEach(arrLength->
    {
      if(floatTestDataBuilder.MergeAscending.isRandomized())
      {
        var randStream=Arrays.stream(randSeeds);
        if(PARALLEL)
        {
          randStream=randStream.parallel();
        }
        randStream.forEach(randSeed->
        {
          TestData.initializeTestData(floatTestDataBuilder.MergeAscending,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(floatTestDataBuilder.MergeAscending,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(8*1+2)
  public void testUncheckedStableUnsortedComparatorSortWithMergeAscendingArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Unsorted);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*1+3)
  public void testUncheckedStableAscendingComparatorSortWithMergeAscendingArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //sort the stock array
      Arrays.sort(stockArr,0,arrLength);
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Ascending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*1+4)
  public void testUncheckedUnstableAscendingComparatorSortWithMergeAscendingArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedUnstableSort(customArr,0,arrLength,floatComparators.Ascending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*1+5)
  public void testUncheckedStableAscendingSortWithMergeAscendingArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedAscendingSort(customArr,0,arrLength);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*1+6)
  public void testUncheckedStableDescendingComparatorSortWithMergeAscendingArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //reverse the stock array
      testData.reverseStockArr();
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Descending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*1+7)
  public void testUncheckedUnstableDescendingComparatorSortWithMergeAscendingArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedUnstableSort(customArr,0,arrLength,floatComparators.Descending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*1+8)
  public void testUncheckedStableDescendingSortWithMergeAscendingArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedDescendingSort(customArr,0,arrLength);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*2+1)
  public void initializeArraysForMergeDescending()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=float; builder=MergeDescending");
    var lengthStream=Arrays.stream(lengths);
    if(PARALLEL)
    {
      lengthStream=lengthStream.parallel();
    }
    lengthStream.forEach(arrLength->
    {
      if(floatTestDataBuilder.MergeDescending.isRandomized())
      {
        var randStream=Arrays.stream(randSeeds);
        if(PARALLEL)
        {
          randStream=randStream.parallel();
        }
        randStream.forEach(randSeed->
        {
          TestData.initializeTestData(floatTestDataBuilder.MergeDescending,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(floatTestDataBuilder.MergeDescending,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(8*2+2)
  public void testUncheckedStableUnsortedComparatorSortWithMergeDescendingArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Unsorted);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*2+3)
  public void testUncheckedStableAscendingComparatorSortWithMergeDescendingArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //sort the stock array
      Arrays.sort(stockArr,0,arrLength);
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Ascending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*2+4)
  public void testUncheckedUnstableAscendingComparatorSortWithMergeDescendingArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedUnstableSort(customArr,0,arrLength,floatComparators.Ascending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*2+5)
  public void testUncheckedStableAscendingSortWithMergeDescendingArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedAscendingSort(customArr,0,arrLength);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*2+6)
  public void testUncheckedStableDescendingComparatorSortWithMergeDescendingArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //reverse the stock array
      testData.reverseStockArr();
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Descending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*2+7)
  public void testUncheckedUnstableDescendingComparatorSortWithMergeDescendingArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedUnstableSort(customArr,0,arrLength,floatComparators.Descending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*2+8)
  public void testUncheckedStableDescendingSortWithMergeDescendingArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedDescendingSort(customArr,0,arrLength);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*3+1)
  public void initializeArraysForSortedRepeated()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=float; builder=SortedRepeated");
    var lengthStream=Arrays.stream(lengths);
    if(PARALLEL)
    {
      lengthStream=lengthStream.parallel();
    }
    lengthStream.forEach(arrLength->
    {
      if(floatTestDataBuilder.SortedRepeated.isRandomized())
      {
        var randStream=Arrays.stream(randSeeds);
        if(PARALLEL)
        {
          randStream=randStream.parallel();
        }
        randStream.forEach(randSeed->
        {
          TestData.initializeTestData(floatTestDataBuilder.SortedRepeated,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(floatTestDataBuilder.SortedRepeated,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(8*3+2)
  public void testUncheckedStableUnsortedComparatorSortWithSortedRepeatedArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Unsorted);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*3+3)
  public void testUncheckedStableAscendingComparatorSortWithSortedRepeatedArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //sort the stock array
      Arrays.sort(stockArr,0,arrLength);
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Ascending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*3+4)
  public void testUncheckedUnstableAscendingComparatorSortWithSortedRepeatedArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedUnstableSort(customArr,0,arrLength,floatComparators.Ascending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*3+5)
  public void testUncheckedStableAscendingSortWithSortedRepeatedArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedAscendingSort(customArr,0,arrLength);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*3+6)
  public void testUncheckedStableDescendingComparatorSortWithSortedRepeatedArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //reverse the stock array
      testData.reverseStockArr();
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Descending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*3+7)
  public void testUncheckedUnstableDescendingComparatorSortWithSortedRepeatedArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedUnstableSort(customArr,0,arrLength,floatComparators.Descending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*3+8)
  public void testUncheckedStableDescendingSortWithSortedRepeatedArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedDescendingSort(customArr,0,arrLength);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*4+1)
  public void initializeArraysForSortedOrganPipes()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=float; builder=SortedOrganPipes");
    var lengthStream=Arrays.stream(lengths);
    if(PARALLEL)
    {
      lengthStream=lengthStream.parallel();
    }
    lengthStream.forEach(arrLength->
    {
      if(floatTestDataBuilder.SortedOrganPipes.isRandomized())
      {
        var randStream=Arrays.stream(randSeeds);
        if(PARALLEL)
        {
          randStream=randStream.parallel();
        }
        randStream.forEach(randSeed->
        {
          TestData.initializeTestData(floatTestDataBuilder.SortedOrganPipes,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(floatTestDataBuilder.SortedOrganPipes,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(8*4+2)
  public void testUncheckedStableUnsortedComparatorSortWithSortedOrganPipesArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Unsorted);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*4+3)
  public void testUncheckedStableAscendingComparatorSortWithSortedOrganPipesArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //sort the stock array
      Arrays.sort(stockArr,0,arrLength);
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Ascending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*4+4)
  public void testUncheckedUnstableAscendingComparatorSortWithSortedOrganPipesArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedUnstableSort(customArr,0,arrLength,floatComparators.Ascending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*4+5)
  public void testUncheckedStableAscendingSortWithSortedOrganPipesArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedAscendingSort(customArr,0,arrLength);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*4+6)
  public void testUncheckedStableDescendingComparatorSortWithSortedOrganPipesArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //reverse the stock array
      testData.reverseStockArr();
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Descending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*4+7)
  public void testUncheckedUnstableDescendingComparatorSortWithSortedOrganPipesArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedUnstableSort(customArr,0,arrLength,floatComparators.Descending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*4+8)
  public void testUncheckedStableDescendingSortWithSortedOrganPipesArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedDescendingSort(customArr,0,arrLength);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*5+1)
  public void initializeArraysForAscending()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=float; builder=Ascending");
    var lengthStream=Arrays.stream(lengths);
    if(PARALLEL)
    {
      lengthStream=lengthStream.parallel();
    }
    lengthStream.forEach(arrLength->
    {
      if(floatTestDataBuilder.Ascending.isRandomized())
      {
        var randStream=Arrays.stream(randSeeds);
        if(PARALLEL)
        {
          randStream=randStream.parallel();
        }
        randStream.forEach(randSeed->
        {
          TestData.initializeTestData(floatTestDataBuilder.Ascending,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(floatTestDataBuilder.Ascending,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(8*5+2)
  public void testUncheckedStableUnsortedComparatorSortWithAscendingArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Unsorted);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*5+3)
  public void testUncheckedStableAscendingComparatorSortWithAscendingArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Ascending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*5+4)
  public void testUncheckedUnstableAscendingComparatorSortWithAscendingArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedUnstableSort(customArr,0,arrLength,floatComparators.Ascending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*5+5)
  public void testUncheckedStableAscendingSortWithAscendingArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedAscendingSort(customArr,0,arrLength);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*5+6)
  public void testUncheckedStableDescendingComparatorSortWithAscendingArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //reverse the stock array
      testData.reverseStockArr();
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Descending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*5+7)
  public void testUncheckedUnstableDescendingComparatorSortWithAscendingArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedUnstableSort(customArr,0,arrLength,floatComparators.Descending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*5+8)
  public void testUncheckedStableDescendingSortWithAscendingArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedDescendingSort(customArr,0,arrLength);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*6+1)
  public void initializeArraysForDescending()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=float; builder=Descending");
    var lengthStream=Arrays.stream(lengths);
    if(PARALLEL)
    {
      lengthStream=lengthStream.parallel();
    }
    lengthStream.forEach(arrLength->
    {
      if(floatTestDataBuilder.Descending.isRandomized())
      {
        var randStream=Arrays.stream(randSeeds);
        if(PARALLEL)
        {
          randStream=randStream.parallel();
        }
        randStream.forEach(randSeed->
        {
          TestData.initializeTestData(floatTestDataBuilder.Descending,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(floatTestDataBuilder.Descending,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(8*6+2)
  public void testUncheckedStableUnsortedComparatorSortWithDescendingArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Unsorted);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*6+3)
  public void testUncheckedStableDescendingComparatorSortWithDescendingArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Descending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*6+4)
  public void testUncheckedUnstableDescendingComparatorSortWithDescendingArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedUnstableSort(customArr,0,arrLength,floatComparators.Descending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*6+5)
  public void testUncheckedStableDescendingSortWithDescendingArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedDescendingSort(customArr,0,arrLength);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*6+6)
  public void testUncheckedStableAscendingComparatorSortWithDescendingArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //reverse the stock array
      testData.reverseStockArr();
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Ascending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*6+7)
  public void testUncheckedUnstableAscendingComparatorSortWithDescendingArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedUnstableSort(customArr,0,arrLength,floatComparators.Ascending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*6+8)
  public void testUncheckedStableAscendingSortWithDescendingArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedAscendingSort(customArr,0,arrLength);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*7+1)
  public void initializeArraysForSaw()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=float; builder=Saw");
    var lengthStream=Arrays.stream(lengths);
    if(PARALLEL)
    {
      lengthStream=lengthStream.parallel();
    }
    lengthStream.forEach(arrLength->
    {
      if(floatTestDataBuilder.Saw.isRandomized())
      {
        var randStream=Arrays.stream(randSeeds);
        if(PARALLEL)
        {
          randStream=randStream.parallel();
        }
        randStream.forEach(randSeed->
        {
          TestData.initializeTestData(floatTestDataBuilder.Saw,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(floatTestDataBuilder.Saw,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(8*7+2)
  public void testUncheckedStableUnsortedComparatorSortWithSawArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Unsorted);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*7+3)
  public void testUncheckedStableAscendingComparatorSortWithSawArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //sort the stock array
      Arrays.sort(stockArr,0,arrLength);
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Ascending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*7+4)
  public void testUncheckedUnstableAscendingComparatorSortWithSawArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedUnstableSort(customArr,0,arrLength,floatComparators.Ascending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*7+5)
  public void testUncheckedStableAscendingSortWithSawArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedAscendingSort(customArr,0,arrLength);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*7+6)
  public void testUncheckedStableDescendingComparatorSortWithSawArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //reverse the stock array
      testData.reverseStockArr();
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Descending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*7+7)
  public void testUncheckedUnstableDescendingComparatorSortWithSawArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedUnstableSort(customArr,0,arrLength,floatComparators.Descending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*7+8)
  public void testUncheckedStableDescendingSortWithSawArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedDescendingSort(customArr,0,arrLength);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*8+1)
  public void initializeArraysForRepeated()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=float; builder=Repeated");
    var lengthStream=Arrays.stream(lengths);
    if(PARALLEL)
    {
      lengthStream=lengthStream.parallel();
    }
    lengthStream.forEach(arrLength->
    {
      if(floatTestDataBuilder.Repeated.isRandomized())
      {
        var randStream=Arrays.stream(randSeeds);
        if(PARALLEL)
        {
          randStream=randStream.parallel();
        }
        randStream.forEach(randSeed->
        {
          TestData.initializeTestData(floatTestDataBuilder.Repeated,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(floatTestDataBuilder.Repeated,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(8*8+2)
  public void testUncheckedStableUnsortedComparatorSortWithRepeatedArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Unsorted);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*8+3)
  public void testUncheckedStableAscendingComparatorSortWithRepeatedArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //sort the stock array
      Arrays.sort(stockArr,0,arrLength);
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Ascending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*8+4)
  public void testUncheckedUnstableAscendingComparatorSortWithRepeatedArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedUnstableSort(customArr,0,arrLength,floatComparators.Ascending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*8+5)
  public void testUncheckedStableAscendingSortWithRepeatedArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedAscendingSort(customArr,0,arrLength);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*8+6)
  public void testUncheckedStableDescendingComparatorSortWithRepeatedArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //reverse the stock array
      testData.reverseStockArr();
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Descending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*8+7)
  public void testUncheckedUnstableDescendingComparatorSortWithRepeatedArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedUnstableSort(customArr,0,arrLength,floatComparators.Descending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*8+8)
  public void testUncheckedStableDescendingSortWithRepeatedArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedDescendingSort(customArr,0,arrLength);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*9+1)
  public void initializeArraysForOrganPipes()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=float; builder=OrganPipes");
    var lengthStream=Arrays.stream(lengths);
    if(PARALLEL)
    {
      lengthStream=lengthStream.parallel();
    }
    lengthStream.forEach(arrLength->
    {
      if(floatTestDataBuilder.OrganPipes.isRandomized())
      {
        var randStream=Arrays.stream(randSeeds);
        if(PARALLEL)
        {
          randStream=randStream.parallel();
        }
        randStream.forEach(randSeed->
        {
          TestData.initializeTestData(floatTestDataBuilder.OrganPipes,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(floatTestDataBuilder.OrganPipes,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(8*9+2)
  public void testUncheckedStableUnsortedComparatorSortWithOrganPipesArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Unsorted);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*9+3)
  public void testUncheckedStableAscendingComparatorSortWithOrganPipesArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //sort the stock array
      Arrays.sort(stockArr,0,arrLength);
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Ascending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*9+4)
  public void testUncheckedUnstableAscendingComparatorSortWithOrganPipesArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedUnstableSort(customArr,0,arrLength,floatComparators.Ascending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*9+5)
  public void testUncheckedStableAscendingSortWithOrganPipesArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedAscendingSort(customArr,0,arrLength);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*9+6)
  public void testUncheckedStableDescendingComparatorSortWithOrganPipesArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //reverse the stock array
      testData.reverseStockArr();
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Descending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*9+7)
  public void testUncheckedUnstableDescendingComparatorSortWithOrganPipesArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedUnstableSort(customArr,0,arrLength,floatComparators.Descending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*9+8)
  public void testUncheckedStableDescendingSortWithOrganPipesArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedDescendingSort(customArr,0,arrLength);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*10+1)
  public void initializeArraysForStagger()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=float; builder=Stagger");
    var lengthStream=Arrays.stream(lengths);
    if(PARALLEL)
    {
      lengthStream=lengthStream.parallel();
    }
    lengthStream.forEach(arrLength->
    {
      if(floatTestDataBuilder.Stagger.isRandomized())
      {
        var randStream=Arrays.stream(randSeeds);
        if(PARALLEL)
        {
          randStream=randStream.parallel();
        }
        randStream.forEach(randSeed->
        {
          TestData.initializeTestData(floatTestDataBuilder.Stagger,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(floatTestDataBuilder.Stagger,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(8*10+2)
  public void testUncheckedStableUnsortedComparatorSortWithStaggerArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Unsorted);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*10+3)
  public void testUncheckedStableAscendingComparatorSortWithStaggerArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //sort the stock array
      Arrays.sort(stockArr,0,arrLength);
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Ascending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*10+4)
  public void testUncheckedUnstableAscendingComparatorSortWithStaggerArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedUnstableSort(customArr,0,arrLength,floatComparators.Ascending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*10+5)
  public void testUncheckedStableAscendingSortWithStaggerArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedAscendingSort(customArr,0,arrLength);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*10+6)
  public void testUncheckedStableDescendingComparatorSortWithStaggerArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //reverse the stock array
      testData.reverseStockArr();
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Descending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*10+7)
  public void testUncheckedUnstableDescendingComparatorSortWithStaggerArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedUnstableSort(customArr,0,arrLength,floatComparators.Descending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*10+8)
  public void testUncheckedStableDescendingSortWithStaggerArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedDescendingSort(customArr,0,arrLength);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*11+1)
  public void initializeArraysForPlateau()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=float; builder=Plateau");
    var lengthStream=Arrays.stream(lengths);
    if(PARALLEL)
    {
      lengthStream=lengthStream.parallel();
    }
    lengthStream.forEach(arrLength->
    {
      if(floatTestDataBuilder.Plateau.isRandomized())
      {
        var randStream=Arrays.stream(randSeeds);
        if(PARALLEL)
        {
          randStream=randStream.parallel();
        }
        randStream.forEach(randSeed->
        {
          TestData.initializeTestData(floatTestDataBuilder.Plateau,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(floatTestDataBuilder.Plateau,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(8*11+2)
  public void testUncheckedStableUnsortedComparatorSortWithPlateauArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Unsorted);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*11+3)
  public void testUncheckedStableAscendingComparatorSortWithPlateauArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //sort the stock array
      Arrays.sort(stockArr,0,arrLength);
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Ascending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*11+4)
  public void testUncheckedUnstableAscendingComparatorSortWithPlateauArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedUnstableSort(customArr,0,arrLength,floatComparators.Ascending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*11+5)
  public void testUncheckedStableAscendingSortWithPlateauArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedAscendingSort(customArr,0,arrLength);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*11+6)
  public void testUncheckedStableDescendingComparatorSortWithPlateauArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //reverse the stock array
      testData.reverseStockArr();
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Descending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*11+7)
  public void testUncheckedUnstableDescendingComparatorSortWithPlateauArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedUnstableSort(customArr,0,arrLength,floatComparators.Descending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*11+8)
  public void testUncheckedStableDescendingSortWithPlateauArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedDescendingSort(customArr,0,arrLength);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*12+1)
  public void initializeArraysForWithNaNsAndZeros()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=float; builder=WithNaNsAndZeros");
    var lengthStream=Arrays.stream(lengths);
    if(PARALLEL)
    {
      lengthStream=lengthStream.parallel();
    }
    lengthStream.forEach(arrLength->
    {
      if(floatTestDataBuilder.WithNaNsAndZeros.isRandomized())
      {
        var randStream=Arrays.stream(randSeeds);
        if(PARALLEL)
        {
          randStream=randStream.parallel();
        }
        randStream.forEach(randSeed->
        {
          TestData.initializeTestData(floatTestDataBuilder.WithNaNsAndZeros,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(floatTestDataBuilder.WithNaNsAndZeros,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(8*12+2)
  public void testUncheckedStableUnsortedComparatorSortWithWithNaNsAndZerosArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Unsorted);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*12+3)
  public void testUncheckedStableAscendingComparatorSortWithWithNaNsAndZerosArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //sort the stock array
      Arrays.sort(stockArr,0,arrLength);
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Ascending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*12+4)
  public void testUncheckedUnstableAscendingComparatorSortWithWithNaNsAndZerosArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedUnstableSort(customArr,0,arrLength,floatComparators.Ascending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*12+5)
  public void testUncheckedStableAscendingSortWithWithNaNsAndZerosArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedAscendingSort(customArr,0,arrLength);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*12+6)
  public void testUncheckedStableDescendingComparatorSortWithWithNaNsAndZerosArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //reverse the stock array
      testData.reverseStockArr();
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Descending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*12+7)
  public void testUncheckedUnstableDescendingComparatorSortWithWithNaNsAndZerosArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedUnstableSort(customArr,0,arrLength,floatComparators.Descending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*12+8)
  public void testUncheckedStableDescendingSortWithWithNaNsAndZerosArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedDescendingSort(customArr,0,arrLength);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*13+1)
  public void initializeArraysForShuffle()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=float; builder=Shuffle");
    var lengthStream=Arrays.stream(lengths);
    if(PARALLEL)
    {
      lengthStream=lengthStream.parallel();
    }
    lengthStream.forEach(arrLength->
    {
      if(floatTestDataBuilder.Shuffle.isRandomized())
      {
        var randStream=Arrays.stream(randSeeds);
        if(PARALLEL)
        {
          randStream=randStream.parallel();
        }
        randStream.forEach(randSeed->
        {
          TestData.initializeTestData(floatTestDataBuilder.Shuffle,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(floatTestDataBuilder.Shuffle,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(8*13+2)
  public void testUncheckedStableUnsortedComparatorSortWithShuffleArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Unsorted);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*13+3)
  public void testUncheckedStableAscendingComparatorSortWithShuffleArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //sort the stock array
      Arrays.sort(stockArr,0,arrLength);
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Ascending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*13+4)
  public void testUncheckedUnstableAscendingComparatorSortWithShuffleArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedUnstableSort(customArr,0,arrLength,floatComparators.Ascending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*13+5)
  public void testUncheckedStableAscendingSortWithShuffleArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedAscendingSort(customArr,0,arrLength);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*13+6)
  public void testUncheckedStableDescendingComparatorSortWithShuffleArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //reverse the stock array
      testData.reverseStockArr();
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Descending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*13+7)
  public void testUncheckedUnstableDescendingComparatorSortWithShuffleArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedUnstableSort(customArr,0,arrLength,floatComparators.Descending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*13+8)
  public void testUncheckedStableDescendingSortWithShuffleArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedDescendingSort(customArr,0,arrLength);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*14+1)
  public void initializeArraysForRandomized()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=float; builder=Randomized");
    var lengthStream=Arrays.stream(lengths);
    if(PARALLEL)
    {
      lengthStream=lengthStream.parallel();
    }
    lengthStream.forEach(arrLength->
    {
      if(floatTestDataBuilder.Randomized.isRandomized())
      {
        var randStream=Arrays.stream(randSeeds);
        if(PARALLEL)
        {
          randStream=randStream.parallel();
        }
        randStream.forEach(randSeed->
        {
          TestData.initializeTestData(floatTestDataBuilder.Randomized,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(floatTestDataBuilder.Randomized,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(8*14+2)
  public void testUncheckedStableUnsortedComparatorSortWithRandomizedArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Unsorted);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*14+3)
  public void testUncheckedStableAscendingComparatorSortWithRandomizedArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //sort the stock array
      Arrays.sort(stockArr,0,arrLength);
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Ascending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*14+4)
  public void testUncheckedUnstableAscendingComparatorSortWithRandomizedArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedUnstableSort(customArr,0,arrLength,floatComparators.Ascending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*14+5)
  public void testUncheckedStableAscendingSortWithRandomizedArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedAscendingSort(customArr,0,arrLength);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*14+6)
  public void testUncheckedStableDescendingComparatorSortWithRandomizedArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //reverse the stock array
      testData.reverseStockArr();
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Descending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*14+7)
  public void testUncheckedUnstableDescendingComparatorSortWithRandomizedArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedUnstableSort(customArr,0,arrLength,floatComparators.Descending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*14+8)
  public void testUncheckedStableDescendingSortWithRandomizedArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedDescendingSort(customArr,0,arrLength);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*15+1)
  public void initializeArraysForDuplicated()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=float; builder=Duplicated");
    var lengthStream=Arrays.stream(lengths);
    if(PARALLEL)
    {
      lengthStream=lengthStream.parallel();
    }
    lengthStream.forEach(arrLength->
    {
      if(floatTestDataBuilder.Duplicated.isRandomized())
      {
        var randStream=Arrays.stream(randSeeds);
        if(PARALLEL)
        {
          randStream=randStream.parallel();
        }
        randStream.forEach(randSeed->
        {
          TestData.initializeTestData(floatTestDataBuilder.Duplicated,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(floatTestDataBuilder.Duplicated,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(8*15+2)
  public void testUncheckedStableUnsortedComparatorSortWithDuplicatedArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Unsorted);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*15+3)
  public void testUncheckedStableAscendingComparatorSortWithDuplicatedArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //sort the stock array
      Arrays.sort(stockArr,0,arrLength);
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Ascending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*15+4)
  public void testUncheckedUnstableAscendingComparatorSortWithDuplicatedArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedUnstableSort(customArr,0,arrLength,floatComparators.Ascending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*15+5)
  public void testUncheckedStableAscendingSortWithDuplicatedArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedAscendingSort(customArr,0,arrLength);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*15+6)
  public void testUncheckedStableDescendingComparatorSortWithDuplicatedArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //reverse the stock array
      testData.reverseStockArr();
      FloatSortUtil.uncheckedStableSort(customArr,0,arrLength,floatComparators.Descending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*15+7)
  public void testUncheckedUnstableDescendingComparatorSortWithDuplicatedArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedUnstableSort(customArr,0,arrLength,floatComparators.Descending);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
    });
  }
  @Test
  @Order(8*15+8)
  public void testUncheckedStableDescendingSortWithDuplicatedArray()
  {
    var testDataStream=TEST_DATA.stream();
    if(PARALLEL)
    {
      testDataStream=testDataStream.parallel();
    }
    testDataStream.forEach(testData->
    {
      var stockArr=testData.stockArr;
      var customArr=testData.customArr;
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      FloatSortUtil.uncheckedDescendingSort(customArr,0,arrLength);
      if(PARALLEL)
      {
        EqualityUtil.uncheckedparallelassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
      else
      {
        EqualityUtil.uncheckedassertarraysAreEqual(customArr,0,stockArr,0,arrLength);  
      }
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
}
