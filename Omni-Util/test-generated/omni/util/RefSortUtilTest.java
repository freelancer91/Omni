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
public class RefSortUtilTest
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
    final IntegerTestDataBuilder builder;
    final Integer[] customArr;
    final int[] stockArr;
    private TestData(long randSeed,int m,IntegerTestDataBuilder builder,int arrLength,Random rand)
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
    private static void initializeTestData(IntegerTestDataBuilder builder,long randSeed,int arrLength,ArrayList<TestData> testDatas)
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
  public void testIsStableUnsortedComparatorSort()
  {
    Random rand=new Random(0);
    StableVal[] customArr=new StableVal[4000];
    for(int i=0;i<customArr.length;)
    {
      int key=rand.nextInt();
      customArr[i++]=new StableVal(key,1);
      customArr[i++]=new StableVal(key,2);
      customArr[i++]=new StableVal(key,3);
      customArr[i++]=new StableVal(key,4);
    }
    RefSortUtil.uncheckedStableSort(customArr,0,customArr.length,(StableVal v1,StableVal v2)->0);
    for(int i=0,bound=customArr.length/4;i<bound;)
    {
      int key1=customArr[i].key;
      int val1=customArr[i++].val;
      int key2=customArr[i].key;
      int val2=customArr[i++].val;
      int key3=customArr[i].key;
      int val3=customArr[i++].val;
      int key4=customArr[i].key;
      int val4=customArr[i++].val;
      Assertions.assertEquals(key1,key2);
      Assertions.assertEquals(key2,key3);
      Assertions.assertEquals(key3,key4);
      Assertions.assertTrue(val1<val2);
      Assertions.assertTrue(val2<val3);
      Assertions.assertTrue(val3<val4);
    }
  }
  @Test
  public void testIsStableAscendingComparatorSort()
  {
    Random rand=new Random(0);
    StableVal[] customArr=new StableVal[4000];
    for(int i=0;i<customArr.length;)
    {
      int key=rand.nextInt();
      customArr[i++]=new StableVal(key,1);
      customArr[i++]=new StableVal(key,2);
      customArr[i++]=new StableVal(key,3);
      customArr[i++]=new StableVal(key,4);
    }
    RefSortUtil.uncheckedStableSort(customArr,0,customArr.length,(StableVal v1,StableVal v2)->v1.compareTo(v2));
    for(int i=0,bound=customArr.length/4;i<bound;)
    {
      int key1=customArr[i].key;
      int val1=customArr[i++].val;
      int key2=customArr[i].key;
      int val2=customArr[i++].val;
      int key3=customArr[i].key;
      int val3=customArr[i++].val;
      int key4=customArr[i].key;
      int val4=customArr[i++].val;
      Assertions.assertEquals(key1,key2);
      Assertions.assertEquals(key2,key3);
      Assertions.assertEquals(key3,key4);
      Assertions.assertTrue(val1<val2);
      Assertions.assertTrue(val2<val3);
      Assertions.assertTrue(val3<val4);
    }
  }
  @Test
  public void testIsStableDescendingComparatorSort()
  {
    Random rand=new Random(0);
    StableVal[] customArr=new StableVal[4000];
    for(int i=0;i<customArr.length;)
    {
      int key=rand.nextInt();
      customArr[i++]=new StableVal(key,1);
      customArr[i++]=new StableVal(key,2);
      customArr[i++]=new StableVal(key,3);
      customArr[i++]=new StableVal(key,4);
    }
    RefSortUtil.uncheckedStableSort(customArr,0,customArr.length,(StableVal v1,StableVal v2)->v2.compareTo(v1));
    for(int i=0,bound=customArr.length/4;i<bound;)
    {
      int key1=customArr[i].key;
      int val1=customArr[i++].val;
      int key2=customArr[i].key;
      int val2=customArr[i++].val;
      int key3=customArr[i].key;
      int val3=customArr[i++].val;
      int key4=customArr[i].key;
      int val4=customArr[i++].val;
      Assertions.assertEquals(key1,key2);
      Assertions.assertEquals(key2,key3);
      Assertions.assertEquals(key3,key4);
      Assertions.assertTrue(val1<val2);
      Assertions.assertTrue(val2<val3);
      Assertions.assertTrue(val3<val4);
    }
  }
  @Test
  public void testIsStableAscendingSort()
  {
    Random rand=new Random(0);
    StableVal[] customArr=new StableVal[4000];
    for(int i=0;i<customArr.length;)
    {
      int key=rand.nextInt();
      customArr[i++]=new StableVal(key,1);
      customArr[i++]=new StableVal(key,2);
      customArr[i++]=new StableVal(key,3);
      customArr[i++]=new StableVal(key,4);
    }
    RefSortUtil.uncheckedStableAscendingSort(customArr,0,customArr.length);
    for(int i=0,bound=customArr.length/4;i<bound;)
    {
      int key1=customArr[i].key;
      int val1=customArr[i++].val;
      int key2=customArr[i].key;
      int val2=customArr[i++].val;
      int key3=customArr[i].key;
      int val3=customArr[i++].val;
      int key4=customArr[i].key;
      int val4=customArr[i++].val;
      Assertions.assertEquals(key1,key2);
      Assertions.assertEquals(key2,key3);
      Assertions.assertEquals(key3,key4);
      Assertions.assertTrue(val1<val2);
      Assertions.assertTrue(val2<val3);
      Assertions.assertTrue(val3<val4);
    }
  }
  @Test
  public void testIsStableDescendingSort()
  {
    Random rand=new Random(0);
    StableVal[] customArr=new StableVal[4000];
    for(int i=0;i<customArr.length;)
    {
      int key=rand.nextInt();
      customArr[i++]=new StableVal(key,1);
      customArr[i++]=new StableVal(key,2);
      customArr[i++]=new StableVal(key,3);
      customArr[i++]=new StableVal(key,4);
    }
    RefSortUtil.uncheckedStableDescendingSort(customArr,0,customArr.length);
    for(int i=0,bound=customArr.length/4;i<bound;)
    {
      int key1=customArr[i].key;
      int val1=customArr[i++].val;
      int key2=customArr[i].key;
      int val2=customArr[i++].val;
      int key3=customArr[i].key;
      int val3=customArr[i++].val;
      int key4=customArr[i].key;
      int val4=customArr[i++].val;
      Assertions.assertEquals(key1,key2);
      Assertions.assertEquals(key2,key3);
      Assertions.assertEquals(key3,key4);
      Assertions.assertTrue(val1<val2);
      Assertions.assertTrue(val2<val3);
      Assertions.assertTrue(val3<val4);
    }
  }
  @Test
  @Order(10*0+1)
  public void initializeArraysForAllEquals()
  {
    System.out.println("Initializing arrays for arrType=Object; builder=AllEquals");
    var lengthStream=Arrays.stream(lengths);
    if(PARALLEL)
    {
      lengthStream=lengthStream.parallel();
    }
    lengthStream.forEach(arrLength->
    {
      if(IntegerTestDataBuilder.AllEquals.isRandomized())
      {
        var randStream=Arrays.stream(randSeeds);
        if(PARALLEL)
        {
          randStream=randStream.parallel();
        }
        randStream.forEach(randSeed->
        {
          TestData.initializeTestData(IntegerTestDataBuilder.AllEquals,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(IntegerTestDataBuilder.AllEquals,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(10*0+2)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Unsorted);
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
  @Order(10*0+3)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Descending);
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
  @Order(10*0+4)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,IntegerComparators.Descending);
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
  @Order(10*0+5)
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
      RefSortUtil.uncheckedStableDescendingSort(customArr,0,arrLength);
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
  @Order(10*0+6)
  public void testUncheckedUnstableDescendingSortWithAllEqualsArray()
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
      RefSortUtil.uncheckedUnstableDescendingSort(customArr,0,arrLength);
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
  @Order(10*0+7)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Ascending);
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
  @Order(10*0+8)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,IntegerComparators.Ascending);
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
  @Order(10*0+9)
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
      RefSortUtil.uncheckedStableAscendingSort(customArr,0,arrLength);
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
  @Order(10*0+10)
  public void testUncheckedUnstableAscendingSortWithAllEqualsArray()
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
      RefSortUtil.uncheckedUnstableAscendingSort(customArr,0,arrLength);
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
  @Order(10*1+1)
  public void initializeArraysForMergeAscending()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=Object; builder=MergeAscending");
    var lengthStream=Arrays.stream(lengths);
    if(PARALLEL)
    {
      lengthStream=lengthStream.parallel();
    }
    lengthStream.forEach(arrLength->
    {
      if(IntegerTestDataBuilder.MergeAscending.isRandomized())
      {
        var randStream=Arrays.stream(randSeeds);
        if(PARALLEL)
        {
          randStream=randStream.parallel();
        }
        randStream.forEach(randSeed->
        {
          TestData.initializeTestData(IntegerTestDataBuilder.MergeAscending,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(IntegerTestDataBuilder.MergeAscending,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(10*1+2)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Unsorted);
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
  @Order(10*1+3)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Ascending);
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
  @Order(10*1+4)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,IntegerComparators.Ascending);
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
  @Order(10*1+5)
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
      RefSortUtil.uncheckedStableAscendingSort(customArr,0,arrLength);
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
  @Order(10*1+6)
  public void testUncheckedUnstableAscendingSortWithMergeAscendingArray()
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
      RefSortUtil.uncheckedUnstableAscendingSort(customArr,0,arrLength);
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
  @Order(10*1+7)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Descending);
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
  @Order(10*1+8)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,IntegerComparators.Descending);
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
  @Order(10*1+9)
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
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedStableDescendingSort(customArr,0,arrLength);
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
  @Order(10*1+10)
  public void testUncheckedUnstableDescendingSortWithMergeAscendingArray()
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
      RefSortUtil.uncheckedUnstableDescendingSort(customArr,0,arrLength);
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
  @Order(10*2+1)
  public void initializeArraysForMergeDescending()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=Object; builder=MergeDescending");
    var lengthStream=Arrays.stream(lengths);
    if(PARALLEL)
    {
      lengthStream=lengthStream.parallel();
    }
    lengthStream.forEach(arrLength->
    {
      if(IntegerTestDataBuilder.MergeDescending.isRandomized())
      {
        var randStream=Arrays.stream(randSeeds);
        if(PARALLEL)
        {
          randStream=randStream.parallel();
        }
        randStream.forEach(randSeed->
        {
          TestData.initializeTestData(IntegerTestDataBuilder.MergeDescending,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(IntegerTestDataBuilder.MergeDescending,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(10*2+2)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Unsorted);
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
  @Order(10*2+3)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Ascending);
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
  @Order(10*2+4)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,IntegerComparators.Ascending);
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
  @Order(10*2+5)
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
      RefSortUtil.uncheckedStableAscendingSort(customArr,0,arrLength);
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
  @Order(10*2+6)
  public void testUncheckedUnstableAscendingSortWithMergeDescendingArray()
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
      RefSortUtil.uncheckedUnstableAscendingSort(customArr,0,arrLength);
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
  @Order(10*2+7)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Descending);
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
  @Order(10*2+8)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,IntegerComparators.Descending);
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
  @Order(10*2+9)
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
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedStableDescendingSort(customArr,0,arrLength);
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
  @Order(10*2+10)
  public void testUncheckedUnstableDescendingSortWithMergeDescendingArray()
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
      RefSortUtil.uncheckedUnstableDescendingSort(customArr,0,arrLength);
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
  @Order(10*3+1)
  public void initializeArraysForSortedRepeated()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=Object; builder=SortedRepeated");
    var lengthStream=Arrays.stream(lengths);
    if(PARALLEL)
    {
      lengthStream=lengthStream.parallel();
    }
    lengthStream.forEach(arrLength->
    {
      if(IntegerTestDataBuilder.SortedRepeated.isRandomized())
      {
        var randStream=Arrays.stream(randSeeds);
        if(PARALLEL)
        {
          randStream=randStream.parallel();
        }
        randStream.forEach(randSeed->
        {
          TestData.initializeTestData(IntegerTestDataBuilder.SortedRepeated,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(IntegerTestDataBuilder.SortedRepeated,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(10*3+2)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Unsorted);
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
  @Order(10*3+3)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Ascending);
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
  @Order(10*3+4)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,IntegerComparators.Ascending);
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
  @Order(10*3+5)
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
      RefSortUtil.uncheckedStableAscendingSort(customArr,0,arrLength);
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
  @Order(10*3+6)
  public void testUncheckedUnstableAscendingSortWithSortedRepeatedArray()
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
      RefSortUtil.uncheckedUnstableAscendingSort(customArr,0,arrLength);
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
  @Order(10*3+7)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Descending);
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
  @Order(10*3+8)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,IntegerComparators.Descending);
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
  @Order(10*3+9)
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
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedStableDescendingSort(customArr,0,arrLength);
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
  @Order(10*3+10)
  public void testUncheckedUnstableDescendingSortWithSortedRepeatedArray()
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
      RefSortUtil.uncheckedUnstableDescendingSort(customArr,0,arrLength);
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
  @Order(10*4+1)
  public void initializeArraysForSortedOrganPipes()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=Object; builder=SortedOrganPipes");
    var lengthStream=Arrays.stream(lengths);
    if(PARALLEL)
    {
      lengthStream=lengthStream.parallel();
    }
    lengthStream.forEach(arrLength->
    {
      if(IntegerTestDataBuilder.SortedOrganPipes.isRandomized())
      {
        var randStream=Arrays.stream(randSeeds);
        if(PARALLEL)
        {
          randStream=randStream.parallel();
        }
        randStream.forEach(randSeed->
        {
          TestData.initializeTestData(IntegerTestDataBuilder.SortedOrganPipes,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(IntegerTestDataBuilder.SortedOrganPipes,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(10*4+2)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Unsorted);
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
  @Order(10*4+3)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Ascending);
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
  @Order(10*4+4)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,IntegerComparators.Ascending);
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
  @Order(10*4+5)
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
      RefSortUtil.uncheckedStableAscendingSort(customArr,0,arrLength);
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
  @Order(10*4+6)
  public void testUncheckedUnstableAscendingSortWithSortedOrganPipesArray()
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
      RefSortUtil.uncheckedUnstableAscendingSort(customArr,0,arrLength);
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
  @Order(10*4+7)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Descending);
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
  @Order(10*4+8)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,IntegerComparators.Descending);
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
  @Order(10*4+9)
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
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedStableDescendingSort(customArr,0,arrLength);
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
  @Order(10*4+10)
  public void testUncheckedUnstableDescendingSortWithSortedOrganPipesArray()
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
      RefSortUtil.uncheckedUnstableDescendingSort(customArr,0,arrLength);
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
  @Order(10*5+1)
  public void initializeArraysForAscending()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=Object; builder=Ascending");
    var lengthStream=Arrays.stream(lengths);
    if(PARALLEL)
    {
      lengthStream=lengthStream.parallel();
    }
    lengthStream.forEach(arrLength->
    {
      if(IntegerTestDataBuilder.Ascending.isRandomized())
      {
        var randStream=Arrays.stream(randSeeds);
        if(PARALLEL)
        {
          randStream=randStream.parallel();
        }
        randStream.forEach(randSeed->
        {
          TestData.initializeTestData(IntegerTestDataBuilder.Ascending,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(IntegerTestDataBuilder.Ascending,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(10*5+2)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Unsorted);
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
  @Order(10*5+3)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Ascending);
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
  @Order(10*5+4)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,IntegerComparators.Ascending);
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
  @Order(10*5+5)
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
      RefSortUtil.uncheckedStableAscendingSort(customArr,0,arrLength);
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
  @Order(10*5+6)
  public void testUncheckedUnstableAscendingSortWithAscendingArray()
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
      RefSortUtil.uncheckedUnstableAscendingSort(customArr,0,arrLength);
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
  @Order(10*5+7)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Descending);
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
  @Order(10*5+8)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,IntegerComparators.Descending);
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
  @Order(10*5+9)
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
      RefSortUtil.uncheckedStableDescendingSort(customArr,0,arrLength);
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
  @Order(10*5+10)
  public void testUncheckedUnstableDescendingSortWithAscendingArray()
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
      RefSortUtil.uncheckedUnstableDescendingSort(customArr,0,arrLength);
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
  @Order(10*6+1)
  public void initializeArraysForDescending()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=Object; builder=Descending");
    var lengthStream=Arrays.stream(lengths);
    if(PARALLEL)
    {
      lengthStream=lengthStream.parallel();
    }
    lengthStream.forEach(arrLength->
    {
      if(IntegerTestDataBuilder.Descending.isRandomized())
      {
        var randStream=Arrays.stream(randSeeds);
        if(PARALLEL)
        {
          randStream=randStream.parallel();
        }
        randStream.forEach(randSeed->
        {
          TestData.initializeTestData(IntegerTestDataBuilder.Descending,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(IntegerTestDataBuilder.Descending,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(10*6+2)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Unsorted);
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
  @Order(10*6+3)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Descending);
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
  @Order(10*6+4)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,IntegerComparators.Descending);
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
  @Order(10*6+5)
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
      RefSortUtil.uncheckedStableDescendingSort(customArr,0,arrLength);
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
  @Order(10*6+6)
  public void testUncheckedUnstableDescendingSortWithDescendingArray()
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
      RefSortUtil.uncheckedUnstableDescendingSort(customArr,0,arrLength);
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
  @Order(10*6+7)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Ascending);
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
  @Order(10*6+8)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,IntegerComparators.Ascending);
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
  @Order(10*6+9)
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
      RefSortUtil.uncheckedStableAscendingSort(customArr,0,arrLength);
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
  @Order(10*6+10)
  public void testUncheckedUnstableAscendingSortWithDescendingArray()
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
      RefSortUtil.uncheckedUnstableAscendingSort(customArr,0,arrLength);
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
  @Order(10*7+1)
  public void initializeArraysForSaw()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=Object; builder=Saw");
    var lengthStream=Arrays.stream(lengths);
    if(PARALLEL)
    {
      lengthStream=lengthStream.parallel();
    }
    lengthStream.forEach(arrLength->
    {
      if(IntegerTestDataBuilder.Saw.isRandomized())
      {
        var randStream=Arrays.stream(randSeeds);
        if(PARALLEL)
        {
          randStream=randStream.parallel();
        }
        randStream.forEach(randSeed->
        {
          TestData.initializeTestData(IntegerTestDataBuilder.Saw,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(IntegerTestDataBuilder.Saw,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(10*7+2)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Unsorted);
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
  @Order(10*7+3)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Ascending);
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
  @Order(10*7+4)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,IntegerComparators.Ascending);
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
  @Order(10*7+5)
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
      RefSortUtil.uncheckedStableAscendingSort(customArr,0,arrLength);
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
  @Order(10*7+6)
  public void testUncheckedUnstableAscendingSortWithSawArray()
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
      RefSortUtil.uncheckedUnstableAscendingSort(customArr,0,arrLength);
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
  @Order(10*7+7)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Descending);
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
  @Order(10*7+8)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,IntegerComparators.Descending);
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
  @Order(10*7+9)
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
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedStableDescendingSort(customArr,0,arrLength);
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
  @Order(10*7+10)
  public void testUncheckedUnstableDescendingSortWithSawArray()
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
      RefSortUtil.uncheckedUnstableDescendingSort(customArr,0,arrLength);
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
  @Order(10*8+1)
  public void initializeArraysForRepeated()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=Object; builder=Repeated");
    var lengthStream=Arrays.stream(lengths);
    if(PARALLEL)
    {
      lengthStream=lengthStream.parallel();
    }
    lengthStream.forEach(arrLength->
    {
      if(IntegerTestDataBuilder.Repeated.isRandomized())
      {
        var randStream=Arrays.stream(randSeeds);
        if(PARALLEL)
        {
          randStream=randStream.parallel();
        }
        randStream.forEach(randSeed->
        {
          TestData.initializeTestData(IntegerTestDataBuilder.Repeated,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(IntegerTestDataBuilder.Repeated,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(10*8+2)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Unsorted);
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
  @Order(10*8+3)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Ascending);
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
  @Order(10*8+4)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,IntegerComparators.Ascending);
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
  @Order(10*8+5)
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
      RefSortUtil.uncheckedStableAscendingSort(customArr,0,arrLength);
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
  @Order(10*8+6)
  public void testUncheckedUnstableAscendingSortWithRepeatedArray()
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
      RefSortUtil.uncheckedUnstableAscendingSort(customArr,0,arrLength);
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
  @Order(10*8+7)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Descending);
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
  @Order(10*8+8)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,IntegerComparators.Descending);
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
  @Order(10*8+9)
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
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedStableDescendingSort(customArr,0,arrLength);
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
  @Order(10*8+10)
  public void testUncheckedUnstableDescendingSortWithRepeatedArray()
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
      RefSortUtil.uncheckedUnstableDescendingSort(customArr,0,arrLength);
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
  @Order(10*9+1)
  public void initializeArraysForOrganPipes()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=Object; builder=OrganPipes");
    var lengthStream=Arrays.stream(lengths);
    if(PARALLEL)
    {
      lengthStream=lengthStream.parallel();
    }
    lengthStream.forEach(arrLength->
    {
      if(IntegerTestDataBuilder.OrganPipes.isRandomized())
      {
        var randStream=Arrays.stream(randSeeds);
        if(PARALLEL)
        {
          randStream=randStream.parallel();
        }
        randStream.forEach(randSeed->
        {
          TestData.initializeTestData(IntegerTestDataBuilder.OrganPipes,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(IntegerTestDataBuilder.OrganPipes,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(10*9+2)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Unsorted);
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
  @Order(10*9+3)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Ascending);
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
  @Order(10*9+4)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,IntegerComparators.Ascending);
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
  @Order(10*9+5)
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
      RefSortUtil.uncheckedStableAscendingSort(customArr,0,arrLength);
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
  @Order(10*9+6)
  public void testUncheckedUnstableAscendingSortWithOrganPipesArray()
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
      RefSortUtil.uncheckedUnstableAscendingSort(customArr,0,arrLength);
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
  @Order(10*9+7)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Descending);
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
  @Order(10*9+8)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,IntegerComparators.Descending);
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
  @Order(10*9+9)
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
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedStableDescendingSort(customArr,0,arrLength);
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
  @Order(10*9+10)
  public void testUncheckedUnstableDescendingSortWithOrganPipesArray()
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
      RefSortUtil.uncheckedUnstableDescendingSort(customArr,0,arrLength);
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
  @Order(10*10+1)
  public void initializeArraysForStagger()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=Object; builder=Stagger");
    var lengthStream=Arrays.stream(lengths);
    if(PARALLEL)
    {
      lengthStream=lengthStream.parallel();
    }
    lengthStream.forEach(arrLength->
    {
      if(IntegerTestDataBuilder.Stagger.isRandomized())
      {
        var randStream=Arrays.stream(randSeeds);
        if(PARALLEL)
        {
          randStream=randStream.parallel();
        }
        randStream.forEach(randSeed->
        {
          TestData.initializeTestData(IntegerTestDataBuilder.Stagger,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(IntegerTestDataBuilder.Stagger,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(10*10+2)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Unsorted);
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
  @Order(10*10+3)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Ascending);
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
  @Order(10*10+4)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,IntegerComparators.Ascending);
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
  @Order(10*10+5)
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
      RefSortUtil.uncheckedStableAscendingSort(customArr,0,arrLength);
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
  @Order(10*10+6)
  public void testUncheckedUnstableAscendingSortWithStaggerArray()
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
      RefSortUtil.uncheckedUnstableAscendingSort(customArr,0,arrLength);
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
  @Order(10*10+7)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Descending);
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
  @Order(10*10+8)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,IntegerComparators.Descending);
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
  @Order(10*10+9)
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
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedStableDescendingSort(customArr,0,arrLength);
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
  @Order(10*10+10)
  public void testUncheckedUnstableDescendingSortWithStaggerArray()
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
      RefSortUtil.uncheckedUnstableDescendingSort(customArr,0,arrLength);
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
  @Order(10*11+1)
  public void initializeArraysForPlateau()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=Object; builder=Plateau");
    var lengthStream=Arrays.stream(lengths);
    if(PARALLEL)
    {
      lengthStream=lengthStream.parallel();
    }
    lengthStream.forEach(arrLength->
    {
      if(IntegerTestDataBuilder.Plateau.isRandomized())
      {
        var randStream=Arrays.stream(randSeeds);
        if(PARALLEL)
        {
          randStream=randStream.parallel();
        }
        randStream.forEach(randSeed->
        {
          TestData.initializeTestData(IntegerTestDataBuilder.Plateau,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(IntegerTestDataBuilder.Plateau,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(10*11+2)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Unsorted);
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
  @Order(10*11+3)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Ascending);
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
  @Order(10*11+4)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,IntegerComparators.Ascending);
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
  @Order(10*11+5)
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
      RefSortUtil.uncheckedStableAscendingSort(customArr,0,arrLength);
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
  @Order(10*11+6)
  public void testUncheckedUnstableAscendingSortWithPlateauArray()
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
      RefSortUtil.uncheckedUnstableAscendingSort(customArr,0,arrLength);
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
  @Order(10*11+7)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Descending);
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
  @Order(10*11+8)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,IntegerComparators.Descending);
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
  @Order(10*11+9)
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
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedStableDescendingSort(customArr,0,arrLength);
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
  @Order(10*11+10)
  public void testUncheckedUnstableDescendingSortWithPlateauArray()
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
      RefSortUtil.uncheckedUnstableDescendingSort(customArr,0,arrLength);
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
  @Order(10*12+1)
  public void initializeArraysForShuffle()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=Object; builder=Shuffle");
    var lengthStream=Arrays.stream(lengths);
    if(PARALLEL)
    {
      lengthStream=lengthStream.parallel();
    }
    lengthStream.forEach(arrLength->
    {
      if(IntegerTestDataBuilder.Shuffle.isRandomized())
      {
        var randStream=Arrays.stream(randSeeds);
        if(PARALLEL)
        {
          randStream=randStream.parallel();
        }
        randStream.forEach(randSeed->
        {
          TestData.initializeTestData(IntegerTestDataBuilder.Shuffle,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(IntegerTestDataBuilder.Shuffle,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(10*12+2)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Unsorted);
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
  @Order(10*12+3)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Ascending);
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
  @Order(10*12+4)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,IntegerComparators.Ascending);
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
  @Order(10*12+5)
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
      RefSortUtil.uncheckedStableAscendingSort(customArr,0,arrLength);
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
  @Order(10*12+6)
  public void testUncheckedUnstableAscendingSortWithShuffleArray()
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
      RefSortUtil.uncheckedUnstableAscendingSort(customArr,0,arrLength);
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
  @Order(10*12+7)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Descending);
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
  @Order(10*12+8)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,IntegerComparators.Descending);
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
  @Order(10*12+9)
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
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedStableDescendingSort(customArr,0,arrLength);
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
  @Order(10*12+10)
  public void testUncheckedUnstableDescendingSortWithShuffleArray()
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
      RefSortUtil.uncheckedUnstableDescendingSort(customArr,0,arrLength);
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
  @Order(10*13+1)
  public void initializeArraysForRandomized()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=Object; builder=Randomized");
    var lengthStream=Arrays.stream(lengths);
    if(PARALLEL)
    {
      lengthStream=lengthStream.parallel();
    }
    lengthStream.forEach(arrLength->
    {
      if(IntegerTestDataBuilder.Randomized.isRandomized())
      {
        var randStream=Arrays.stream(randSeeds);
        if(PARALLEL)
        {
          randStream=randStream.parallel();
        }
        randStream.forEach(randSeed->
        {
          TestData.initializeTestData(IntegerTestDataBuilder.Randomized,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(IntegerTestDataBuilder.Randomized,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(10*13+2)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Unsorted);
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
  @Order(10*13+3)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Ascending);
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
  @Order(10*13+4)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,IntegerComparators.Ascending);
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
  @Order(10*13+5)
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
      RefSortUtil.uncheckedStableAscendingSort(customArr,0,arrLength);
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
  @Order(10*13+6)
  public void testUncheckedUnstableAscendingSortWithRandomizedArray()
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
      RefSortUtil.uncheckedUnstableAscendingSort(customArr,0,arrLength);
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
  @Order(10*13+7)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Descending);
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
  @Order(10*13+8)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,IntegerComparators.Descending);
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
  @Order(10*13+9)
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
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedStableDescendingSort(customArr,0,arrLength);
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
  @Order(10*13+10)
  public void testUncheckedUnstableDescendingSortWithRandomizedArray()
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
      RefSortUtil.uncheckedUnstableDescendingSort(customArr,0,arrLength);
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
  @Order(10*14+1)
  public void initializeArraysForDuplicated()
  {
    stopTimer();
    TEST_DATA.clear();
    System.out.println("Initializing arrays for arrType=Object; builder=Duplicated");
    var lengthStream=Arrays.stream(lengths);
    if(PARALLEL)
    {
      lengthStream=lengthStream.parallel();
    }
    lengthStream.forEach(arrLength->
    {
      if(IntegerTestDataBuilder.Duplicated.isRandomized())
      {
        var randStream=Arrays.stream(randSeeds);
        if(PARALLEL)
        {
          randStream=randStream.parallel();
        }
        randStream.forEach(randSeed->
        {
          TestData.initializeTestData(IntegerTestDataBuilder.Duplicated,randSeed,arrLength,TEST_DATA);
        });
      }
      else
      {
        TestData.initializeTestData(IntegerTestDataBuilder.Duplicated,0,arrLength,TEST_DATA);
      }
    });
    System.out.println("Initialized "+TEST_DATA.size()+" arrays");
    TIMER=System.currentTimeMillis();
  }
  @Test
  @Order(10*14+2)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Unsorted);
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
  @Order(10*14+3)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Ascending);
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
  @Order(10*14+4)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,IntegerComparators.Ascending);
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
  @Order(10*14+5)
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
      RefSortUtil.uncheckedStableAscendingSort(customArr,0,arrLength);
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
  @Order(10*14+6)
  public void testUncheckedUnstableAscendingSortWithDuplicatedArray()
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
      RefSortUtil.uncheckedUnstableAscendingSort(customArr,0,arrLength);
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
  @Order(10*14+7)
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
      RefSortUtil.uncheckedStableSort(customArr,0,arrLength,IntegerComparators.Descending);
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
  @Order(10*14+8)
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
      RefSortUtil.uncheckedUnstableSort(customArr,0,arrLength,IntegerComparators.Descending);
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
  @Order(10*14+9)
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
      var customArr=testData.copyCustomArr();
      int arrLength=stockArr.length;
      //make no alterations to the stock array
      RefSortUtil.uncheckedStableDescendingSort(customArr,0,arrLength);
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
  @Order(10*14+10)
  public void testUncheckedUnstableDescendingSortWithDuplicatedArray()
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
      RefSortUtil.uncheckedUnstableDescendingSort(customArr,0,arrLength);
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
  private static class StableVal implements Comparable<StableVal>
  {
    private int key;
    private int val;
    private StableVal(int key,int val)
    {
      this.key=key;
      this.val=val;
    }
    @Override
    public int compareTo(StableVal that)
    {
      int thisKey,thatKey;
      if((thisKey=this.key)<(thatKey=that.key))
      {
        return -1;
      }
      if(thisKey>thatKey)
      {
        return 1;
      }
      return 0;
    }
    @Override
    public boolean equals(Object that)
    {
      return that==this;
    }
    @Override
    public String toString()
    {
      return "("+key+", "+val+")";
    }
  }
}
