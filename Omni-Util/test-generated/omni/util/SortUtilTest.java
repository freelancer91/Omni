package omni.util;
import org.junit.jupiter.api.Test;
import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
public class SortUtilTest
{
  private static final int[] lengths=new int[]{2, 3, 5, 8, 13, 21, 34, 55, 100, 1000, 10000, 100000, 1000000};
  private static final long[] randSeeds=new long[]{666L,0xC0FFEEL,999L};
  @Test
  public void testbooleanAscendingcomparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerboolean  arrs=new RandomArrayContainerboolean();
    System.out.println("Building arrays for testbooleanAscendingcomparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.booleanArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.booleanArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.booleanArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingbooleanComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      boolean[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new boolean[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      }
      {
        Boolean[] boxedCopy=new Boolean[arrLength];
        ArrCopy.uncheckedCopy(copy,0,boxedCopy=new Boolean[arrLength],0,arrLength);
        Arrays.sort(boxedCopy,0,arrLength);
        ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbooleanAscendingcomparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerboolean  arrs=new RandomArrayContainerboolean();
    System.out.println("Building arrays for testbooleanAscendingcomparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.booleanArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.booleanArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.booleanArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingbooleanComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      boolean[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new boolean[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      }
      {
        Boolean[] boxedCopy=new Boolean[arrLength];
        ArrCopy.uncheckedCopy(copy,0,boxedCopy=new Boolean[arrLength],0,arrLength);
        Arrays.sort(boxedCopy,0,arrLength);
        ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbooleanAscendingcomparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerboolean  arrs=new RandomArrayContainerboolean();
    System.out.println("Building arrays for testbooleanAscendingcomparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.booleanArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.booleanArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.booleanArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingbooleanComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      boolean[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new boolean[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      }
      {
        Boolean[] boxedCopy=new Boolean[arrLength];
        ArrCopy.uncheckedCopy(copy,0,boxedCopy=new Boolean[arrLength],0,arrLength);
        Arrays.sort(boxedCopy,0,arrLength);
        ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbooleanAscendingcomparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerboolean  arrs=new RandomArrayContainerboolean();
    System.out.println("Building arrays for testbooleanAscendingcomparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.booleanArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.booleanArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.booleanArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingbooleanComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      boolean[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new boolean[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      }
      {
        Boolean[] boxedCopy=new Boolean[arrLength];
        ArrCopy.uncheckedCopy(copy,0,boxedCopy=new Boolean[arrLength],0,arrLength);
        Arrays.sort(boxedCopy,0,arrLength);
        ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbooleanDescendingcomparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerboolean  arrs=new RandomArrayContainerboolean();
    System.out.println("Building arrays for testbooleanDescendingcomparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.booleanArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.booleanArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.booleanArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingbooleanComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      boolean[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new boolean[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      }
      {
        Boolean[] boxedCopy=new Boolean[arrLength];
        ArrCopy.uncheckedCopy(copy,0,boxedCopy=new Boolean[arrLength],0,arrLength);
        Arrays.sort(boxedCopy,0,arrLength);
        ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,arrLength);
        OmniArray.OfBoolean.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbooleanDescendingcomparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerboolean  arrs=new RandomArrayContainerboolean();
    System.out.println("Building arrays for testbooleanDescendingcomparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.booleanArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.booleanArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.booleanArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingbooleanComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      boolean[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new boolean[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      }
      {
        Boolean[] boxedCopy=new Boolean[arrLength];
        ArrCopy.uncheckedCopy(copy,0,boxedCopy=new Boolean[arrLength],0,arrLength);
        Arrays.sort(boxedCopy,0,arrLength);
        ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,arrLength);
        OmniArray.OfBoolean.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbooleanDescendingcomparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerboolean  arrs=new RandomArrayContainerboolean();
    System.out.println("Building arrays for testbooleanDescendingcomparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.booleanArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.booleanArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.booleanArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingbooleanComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      boolean[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new boolean[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      }
      {
        Boolean[] boxedCopy=new Boolean[arrLength];
        ArrCopy.uncheckedCopy(copy,0,boxedCopy=new Boolean[arrLength],0,arrLength);
        Arrays.sort(boxedCopy,0,arrLength);
        ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,arrLength);
        OmniArray.OfBoolean.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbooleanDescendingcomparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerboolean  arrs=new RandomArrayContainerboolean();
    System.out.println("Building arrays for testbooleanDescendingcomparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.booleanArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.booleanArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.booleanArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingbooleanComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      boolean[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new boolean[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      }
      {
        Boolean[] boxedCopy=new Boolean[arrLength];
        ArrCopy.uncheckedCopy(copy,0,boxedCopy=new Boolean[arrLength],0,arrLength);
        Arrays.sort(boxedCopy,0,arrLength);
        ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,arrLength);
        OmniArray.OfBoolean.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbooleanUnsortedcomparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerboolean  arrs=new RandomArrayContainerboolean();
    System.out.println("Building arrays for testbooleanUnsortedcomparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.booleanArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.booleanArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.booleanArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedbooleanComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      boolean[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new boolean[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbooleanUnsortedcomparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerboolean  arrs=new RandomArrayContainerboolean();
    System.out.println("Building arrays for testbooleanUnsortedcomparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.booleanArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.booleanArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.booleanArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedbooleanComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      boolean[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new boolean[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbooleanUnsortedcomparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerboolean  arrs=new RandomArrayContainerboolean();
    System.out.println("Building arrays for testbooleanUnsortedcomparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.booleanArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.booleanArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.booleanArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedbooleanComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      boolean[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new boolean[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbooleanUnsortedcomparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerboolean  arrs=new RandomArrayContainerboolean();
    System.out.println("Building arrays for testbooleanUnsortedcomparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.booleanArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.booleanArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.booleanArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedbooleanComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      boolean[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new boolean[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedcomparatorSort(arr,0,arrLength-1,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbooleanAscendingnonComparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerboolean  arrs=new RandomArrayContainerboolean();
    System.out.println("Building arrays for testbooleanAscendingnonComparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.booleanArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.booleanArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.booleanArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      boolean[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new boolean[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Boolean[] boxedCopy=new Boolean[arrLength];
        ArrCopy.uncheckedCopy(copy,0,boxedCopy=new Boolean[arrLength],0,arrLength);
        Arrays.sort(boxedCopy,0,arrLength);
        ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbooleanAscendingnonComparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerboolean  arrs=new RandomArrayContainerboolean();
    System.out.println("Building arrays for testbooleanAscendingnonComparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.booleanArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.booleanArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.booleanArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      boolean[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new boolean[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Boolean[] boxedCopy=new Boolean[arrLength];
        ArrCopy.uncheckedCopy(copy,0,boxedCopy=new Boolean[arrLength],0,arrLength);
        Arrays.sort(boxedCopy,0,arrLength);
        ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbooleanAscendingnonComparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerboolean  arrs=new RandomArrayContainerboolean();
    System.out.println("Building arrays for testbooleanAscendingnonComparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.booleanArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.booleanArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.booleanArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      boolean[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new boolean[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Boolean[] boxedCopy=new Boolean[arrLength];
        ArrCopy.uncheckedCopy(copy,0,boxedCopy=new Boolean[arrLength],0,arrLength);
        Arrays.sort(boxedCopy,0,arrLength);
        ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbooleanAscendingnonComparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerboolean  arrs=new RandomArrayContainerboolean();
    System.out.println("Building arrays for testbooleanAscendingnonComparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.booleanArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.booleanArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.booleanArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      boolean[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new boolean[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Boolean[] boxedCopy=new Boolean[arrLength];
        ArrCopy.uncheckedCopy(copy,0,boxedCopy=new Boolean[arrLength],0,arrLength);
        Arrays.sort(boxedCopy,0,arrLength);
        ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbooleanDescendingnonComparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerboolean  arrs=new RandomArrayContainerboolean();
    System.out.println("Building arrays for testbooleanDescendingnonComparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.booleanArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.booleanArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.booleanArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      boolean[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new boolean[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Boolean[] boxedCopy=new Boolean[arrLength];
        ArrCopy.uncheckedCopy(copy,0,boxedCopy=new Boolean[arrLength],0,arrLength);
        Arrays.sort(boxedCopy,0,arrLength);
        ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,arrLength);
        OmniArray.OfBoolean.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbooleanDescendingnonComparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerboolean  arrs=new RandomArrayContainerboolean();
    System.out.println("Building arrays for testbooleanDescendingnonComparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.booleanArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.booleanArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.booleanArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      boolean[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new boolean[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Boolean[] boxedCopy=new Boolean[arrLength];
        ArrCopy.uncheckedCopy(copy,0,boxedCopy=new Boolean[arrLength],0,arrLength);
        Arrays.sort(boxedCopy,0,arrLength);
        ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,arrLength);
        OmniArray.OfBoolean.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbooleanDescendingnonComparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerboolean  arrs=new RandomArrayContainerboolean();
    System.out.println("Building arrays for testbooleanDescendingnonComparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.booleanArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.booleanArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.booleanArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      boolean[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new boolean[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Boolean[] boxedCopy=new Boolean[arrLength];
        ArrCopy.uncheckedCopy(copy,0,boxedCopy=new Boolean[arrLength],0,arrLength);
        Arrays.sort(boxedCopy,0,arrLength);
        ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,arrLength);
        OmniArray.OfBoolean.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbooleanDescendingnonComparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerboolean  arrs=new RandomArrayContainerboolean();
    System.out.println("Building arrays for testbooleanDescendingnonComparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.booleanArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.booleanArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.booleanArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      boolean[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new boolean[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Boolean[] boxedCopy=new Boolean[arrLength];
        ArrCopy.uncheckedCopy(copy,0,boxedCopy=new Boolean[arrLength],0,arrLength);
        Arrays.sort(boxedCopy,0,arrLength);
        ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,arrLength);
        OmniArray.OfBoolean.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteAscendingnonComparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteAscendingnonComparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteAscendingnonComparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteAscendingnonComparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteAscendingnonComparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteAscendingnonComparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteAscendingnonComparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteAscendingnonComparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteAscendingnonComparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteAscendingnonComparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteAscendingnonComparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteAscendingnonComparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteAscendingnonComparatorSortSawArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteAscendingnonComparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteAscendingnonComparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteAscendingnonComparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteAscendingnonComparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteAscendingnonComparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteAscendingnonComparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteAscendingnonComparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteAscendingnonComparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteAscendingnonComparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteAscendingnonComparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteAscendingnonComparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteAscendingnonComparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteAscendingnonComparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteAscendingnonComparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteAscendingnonComparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteAscendingnonComparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteAscendingnonComparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteDescendingnonComparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteDescendingnonComparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfByte.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteDescendingnonComparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteDescendingnonComparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfByte.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteDescendingnonComparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteDescendingnonComparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfByte.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteDescendingnonComparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteDescendingnonComparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfByte.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteDescendingnonComparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteDescendingnonComparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfByte.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteDescendingnonComparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteDescendingnonComparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfByte.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteDescendingnonComparatorSortSawArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteDescendingnonComparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfByte.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteDescendingnonComparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteDescendingnonComparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfByte.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteDescendingnonComparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteDescendingnonComparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfByte.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteDescendingnonComparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteDescendingnonComparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfByte.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteDescendingnonComparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteDescendingnonComparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfByte.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteDescendingnonComparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteDescendingnonComparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfByte.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteDescendingnonComparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteDescendingnonComparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfByte.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteDescendingnonComparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteDescendingnonComparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfByte.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteDescendingnonComparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteDescendingnonComparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfByte.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharAscendingnonComparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharAscendingnonComparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharAscendingnonComparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharAscendingnonComparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharAscendingnonComparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharAscendingnonComparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharAscendingnonComparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharAscendingnonComparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharAscendingnonComparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharAscendingnonComparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharAscendingnonComparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharAscendingnonComparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharAscendingnonComparatorSortSawArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharAscendingnonComparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharAscendingnonComparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharAscendingnonComparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharAscendingnonComparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharAscendingnonComparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharAscendingnonComparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharAscendingnonComparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharAscendingnonComparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharAscendingnonComparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharAscendingnonComparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharAscendingnonComparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharAscendingnonComparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharAscendingnonComparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharAscendingnonComparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharAscendingnonComparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharAscendingnonComparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharAscendingnonComparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharDescendingnonComparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharDescendingnonComparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfChar.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharDescendingnonComparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharDescendingnonComparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfChar.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharDescendingnonComparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharDescendingnonComparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfChar.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharDescendingnonComparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharDescendingnonComparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfChar.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharDescendingnonComparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharDescendingnonComparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfChar.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharDescendingnonComparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharDescendingnonComparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfChar.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharDescendingnonComparatorSortSawArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharDescendingnonComparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfChar.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharDescendingnonComparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharDescendingnonComparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfChar.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharDescendingnonComparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharDescendingnonComparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfChar.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharDescendingnonComparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharDescendingnonComparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfChar.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharDescendingnonComparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharDescendingnonComparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfChar.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharDescendingnonComparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharDescendingnonComparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfChar.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharDescendingnonComparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharDescendingnonComparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfChar.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharDescendingnonComparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharDescendingnonComparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfChar.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharDescendingnonComparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharDescendingnonComparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfChar.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortAscendingnonComparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortAscendingnonComparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortAscendingnonComparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortAscendingnonComparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortAscendingnonComparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortAscendingnonComparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortAscendingnonComparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortAscendingnonComparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortAscendingnonComparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortAscendingnonComparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortAscendingnonComparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortAscendingnonComparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortAscendingnonComparatorSortSawArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortAscendingnonComparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortAscendingnonComparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortAscendingnonComparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortAscendingnonComparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortAscendingnonComparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortAscendingnonComparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortAscendingnonComparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortAscendingnonComparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortAscendingnonComparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortAscendingnonComparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortAscendingnonComparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortAscendingnonComparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortAscendingnonComparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortAscendingnonComparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortAscendingnonComparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortAscendingnonComparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortAscendingnonComparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortDescendingnonComparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortDescendingnonComparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfShort.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortDescendingnonComparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortDescendingnonComparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfShort.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortDescendingnonComparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortDescendingnonComparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfShort.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortDescendingnonComparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortDescendingnonComparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfShort.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortDescendingnonComparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortDescendingnonComparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfShort.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortDescendingnonComparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortDescendingnonComparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfShort.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortDescendingnonComparatorSortSawArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortDescendingnonComparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfShort.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortDescendingnonComparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortDescendingnonComparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfShort.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortDescendingnonComparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortDescendingnonComparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfShort.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortDescendingnonComparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortDescendingnonComparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfShort.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortDescendingnonComparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortDescendingnonComparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfShort.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortDescendingnonComparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortDescendingnonComparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfShort.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortDescendingnonComparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortDescendingnonComparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfShort.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortDescendingnonComparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortDescendingnonComparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfShort.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortDescendingnonComparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortDescendingnonComparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfShort.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintAscendingnonComparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintAscendingnonComparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintAscendingnonComparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintAscendingnonComparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintAscendingnonComparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintAscendingnonComparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintAscendingnonComparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintAscendingnonComparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintAscendingnonComparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintAscendingnonComparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintAscendingnonComparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintAscendingnonComparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintAscendingnonComparatorSortSawArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintAscendingnonComparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintAscendingnonComparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintAscendingnonComparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintAscendingnonComparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintAscendingnonComparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintAscendingnonComparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintAscendingnonComparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintAscendingnonComparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintAscendingnonComparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintAscendingnonComparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintAscendingnonComparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintAscendingnonComparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintAscendingnonComparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintAscendingnonComparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintAscendingnonComparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintAscendingnonComparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintAscendingnonComparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintDescendingnonComparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintDescendingnonComparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfInt.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintDescendingnonComparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintDescendingnonComparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfInt.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintDescendingnonComparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintDescendingnonComparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfInt.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintDescendingnonComparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintDescendingnonComparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfInt.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintDescendingnonComparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintDescendingnonComparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfInt.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintDescendingnonComparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintDescendingnonComparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfInt.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintDescendingnonComparatorSortSawArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintDescendingnonComparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfInt.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintDescendingnonComparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintDescendingnonComparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfInt.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintDescendingnonComparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintDescendingnonComparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfInt.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintDescendingnonComparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintDescendingnonComparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfInt.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintDescendingnonComparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintDescendingnonComparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfInt.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintDescendingnonComparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintDescendingnonComparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfInt.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintDescendingnonComparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintDescendingnonComparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfInt.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintDescendingnonComparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintDescendingnonComparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfInt.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintDescendingnonComparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintDescendingnonComparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfInt.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongAscendingnonComparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongAscendingnonComparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongAscendingnonComparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongAscendingnonComparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongAscendingnonComparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongAscendingnonComparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongAscendingnonComparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongAscendingnonComparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongAscendingnonComparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongAscendingnonComparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongAscendingnonComparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongAscendingnonComparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongAscendingnonComparatorSortSawArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongAscendingnonComparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongAscendingnonComparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongAscendingnonComparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongAscendingnonComparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongAscendingnonComparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongAscendingnonComparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongAscendingnonComparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongAscendingnonComparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongAscendingnonComparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongAscendingnonComparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongAscendingnonComparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongAscendingnonComparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongAscendingnonComparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongAscendingnonComparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongAscendingnonComparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongAscendingnonComparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongAscendingnonComparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongDescendingnonComparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongDescendingnonComparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfLong.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongDescendingnonComparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongDescendingnonComparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfLong.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongDescendingnonComparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongDescendingnonComparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfLong.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongDescendingnonComparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongDescendingnonComparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfLong.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongDescendingnonComparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongDescendingnonComparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfLong.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongDescendingnonComparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongDescendingnonComparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfLong.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongDescendingnonComparatorSortSawArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongDescendingnonComparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfLong.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongDescendingnonComparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongDescendingnonComparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfLong.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongDescendingnonComparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongDescendingnonComparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfLong.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongDescendingnonComparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongDescendingnonComparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfLong.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongDescendingnonComparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongDescendingnonComparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfLong.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongDescendingnonComparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongDescendingnonComparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfLong.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongDescendingnonComparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongDescendingnonComparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfLong.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongDescendingnonComparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongDescendingnonComparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfLong.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongDescendingnonComparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongDescendingnonComparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfLong.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatAscendingnonComparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatAscendingnonComparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatAscendingnonComparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatAscendingnonComparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatAscendingnonComparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatAscendingnonComparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatAscendingnonComparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatAscendingnonComparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatAscendingnonComparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatAscendingnonComparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatAscendingnonComparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatAscendingnonComparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatAscendingnonComparatorSortSawArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatAscendingnonComparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatAscendingnonComparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatAscendingnonComparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatAscendingnonComparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatAscendingnonComparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatAscendingnonComparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatAscendingnonComparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatAscendingnonComparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatAscendingnonComparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatAscendingnonComparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatAscendingnonComparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatAscendingnonComparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatAscendingnonComparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatAscendingnonComparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatAscendingnonComparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatAscendingnonComparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatAscendingnonComparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatAscendingnonComparatorSortWithNaNsAndZerosArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatAscendingnonComparatorSortWithNaNsAndZerosArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.WithNaNsAndZeros.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.WithNaNsAndZeros);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.WithNaNsAndZeros);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatDescendingnonComparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatDescendingnonComparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfFloat.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatDescendingnonComparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatDescendingnonComparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfFloat.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatDescendingnonComparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatDescendingnonComparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfFloat.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatDescendingnonComparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatDescendingnonComparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfFloat.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatDescendingnonComparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatDescendingnonComparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfFloat.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatDescendingnonComparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatDescendingnonComparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfFloat.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatDescendingnonComparatorSortSawArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatDescendingnonComparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfFloat.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatDescendingnonComparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatDescendingnonComparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfFloat.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatDescendingnonComparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatDescendingnonComparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfFloat.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatDescendingnonComparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatDescendingnonComparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfFloat.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatDescendingnonComparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatDescendingnonComparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfFloat.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatDescendingnonComparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatDescendingnonComparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfFloat.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatDescendingnonComparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatDescendingnonComparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfFloat.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatDescendingnonComparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatDescendingnonComparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfFloat.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatDescendingnonComparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatDescendingnonComparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfFloat.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatDescendingnonComparatorSortWithNaNsAndZerosArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatDescendingnonComparatorSortWithNaNsAndZerosArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.WithNaNsAndZeros.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.WithNaNsAndZeros);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.WithNaNsAndZeros);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfFloat.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleAscendingnonComparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleAscendingnonComparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleAscendingnonComparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleAscendingnonComparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleAscendingnonComparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleAscendingnonComparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleAscendingnonComparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleAscendingnonComparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleAscendingnonComparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleAscendingnonComparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleAscendingnonComparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleAscendingnonComparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleAscendingnonComparatorSortSawArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleAscendingnonComparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleAscendingnonComparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleAscendingnonComparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleAscendingnonComparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleAscendingnonComparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleAscendingnonComparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleAscendingnonComparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleAscendingnonComparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleAscendingnonComparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleAscendingnonComparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleAscendingnonComparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleAscendingnonComparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleAscendingnonComparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleAscendingnonComparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleAscendingnonComparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleAscendingnonComparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleAscendingnonComparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleAscendingnonComparatorSortWithNaNsAndZerosArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleAscendingnonComparatorSortWithNaNsAndZerosArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.WithNaNsAndZeros.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.WithNaNsAndZeros);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.WithNaNsAndZeros);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedsort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleDescendingnonComparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleDescendingnonComparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfDouble.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleDescendingnonComparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleDescendingnonComparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfDouble.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleDescendingnonComparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleDescendingnonComparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfDouble.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleDescendingnonComparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleDescendingnonComparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfDouble.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleDescendingnonComparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleDescendingnonComparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfDouble.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleDescendingnonComparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleDescendingnonComparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfDouble.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleDescendingnonComparatorSortSawArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleDescendingnonComparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfDouble.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleDescendingnonComparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleDescendingnonComparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfDouble.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleDescendingnonComparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleDescendingnonComparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfDouble.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleDescendingnonComparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleDescendingnonComparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfDouble.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleDescendingnonComparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleDescendingnonComparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfDouble.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleDescendingnonComparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleDescendingnonComparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfDouble.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleDescendingnonComparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleDescendingnonComparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfDouble.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleDescendingnonComparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleDescendingnonComparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfDouble.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleDescendingnonComparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleDescendingnonComparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfDouble.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleDescendingnonComparatorSortWithNaNsAndZerosArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleDescendingnonComparatorSortWithNaNsAndZerosArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.WithNaNsAndZeros.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.WithNaNsAndZeros);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.WithNaNsAndZeros);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.uncheckedreverseSort(arr,0,arrLength-1);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfDouble.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteUnsortedcomparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteUnsortedcomparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteUnsortedcomparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteUnsortedcomparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteUnsortedcomparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteUnsortedcomparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteUnsortedcomparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteUnsortedcomparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteUnsortedcomparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteUnsortedcomparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteUnsortedcomparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteUnsortedcomparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteUnsortedcomparatorSortSawArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteUnsortedcomparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteUnsortedcomparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteUnsortedcomparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteUnsortedcomparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteUnsortedcomparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteUnsortedcomparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteUnsortedcomparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteUnsortedcomparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteUnsortedcomparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteUnsortedcomparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteUnsortedcomparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteUnsortedcomparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteUnsortedcomparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteUnsortedcomparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteUnsortedcomparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteUnsortedcomparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteUnsortedcomparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteAscendingcomparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteAscendingcomparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteAscendingcomparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteAscendingcomparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteAscendingcomparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteAscendingcomparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteAscendingcomparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteAscendingcomparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteAscendingcomparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteAscendingcomparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteAscendingcomparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteAscendingcomparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteAscendingcomparatorSortSawArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteAscendingcomparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteAscendingcomparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteAscendingcomparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteAscendingcomparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteAscendingcomparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteAscendingcomparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteAscendingcomparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteAscendingcomparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteAscendingcomparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteAscendingcomparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteAscendingcomparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteAscendingcomparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteAscendingcomparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteAscendingcomparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteAscendingcomparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteAscendingcomparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteAscendingcomparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteDescendingcomparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteDescendingcomparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfByte.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteDescendingcomparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteDescendingcomparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfByte.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteDescendingcomparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteDescendingcomparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfByte.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteDescendingcomparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteDescendingcomparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfByte.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteDescendingcomparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteDescendingcomparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfByte.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteDescendingcomparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteDescendingcomparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfByte.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteDescendingcomparatorSortSawArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteDescendingcomparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfByte.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteDescendingcomparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteDescendingcomparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfByte.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteDescendingcomparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteDescendingcomparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfByte.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteDescendingcomparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteDescendingcomparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfByte.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteDescendingcomparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteDescendingcomparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfByte.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteDescendingcomparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteDescendingcomparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfByte.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteDescendingcomparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteDescendingcomparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfByte.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteDescendingcomparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteDescendingcomparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfByte.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testbyteDescendingcomparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainerbyte  arrs=new RandomArrayContainerbyte();
    System.out.println("Building arrays for testbyteDescendingcomparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.byteArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.byteArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.byteArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingbyteComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      byte[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new byte[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortbyteTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfByte.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharUnsortedcomparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharUnsortedcomparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharUnsortedcomparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharUnsortedcomparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharUnsortedcomparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharUnsortedcomparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharUnsortedcomparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharUnsortedcomparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharUnsortedcomparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharUnsortedcomparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharUnsortedcomparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharUnsortedcomparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharUnsortedcomparatorSortSawArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharUnsortedcomparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharUnsortedcomparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharUnsortedcomparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharUnsortedcomparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharUnsortedcomparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharUnsortedcomparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharUnsortedcomparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharUnsortedcomparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharUnsortedcomparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharUnsortedcomparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharUnsortedcomparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharUnsortedcomparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharUnsortedcomparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharUnsortedcomparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharUnsortedcomparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharUnsortedcomparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharUnsortedcomparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharAscendingcomparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharAscendingcomparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharAscendingcomparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharAscendingcomparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharAscendingcomparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharAscendingcomparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharAscendingcomparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharAscendingcomparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharAscendingcomparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharAscendingcomparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharAscendingcomparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharAscendingcomparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharAscendingcomparatorSortSawArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharAscendingcomparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharAscendingcomparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharAscendingcomparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharAscendingcomparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharAscendingcomparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharAscendingcomparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharAscendingcomparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharAscendingcomparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharAscendingcomparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharAscendingcomparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharAscendingcomparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharAscendingcomparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharAscendingcomparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharAscendingcomparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharAscendingcomparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharAscendingcomparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharAscendingcomparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharDescendingcomparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharDescendingcomparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfChar.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharDescendingcomparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharDescendingcomparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfChar.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharDescendingcomparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharDescendingcomparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfChar.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharDescendingcomparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharDescendingcomparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfChar.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharDescendingcomparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharDescendingcomparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfChar.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharDescendingcomparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharDescendingcomparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfChar.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharDescendingcomparatorSortSawArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharDescendingcomparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfChar.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharDescendingcomparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharDescendingcomparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfChar.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharDescendingcomparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharDescendingcomparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfChar.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharDescendingcomparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharDescendingcomparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfChar.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharDescendingcomparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharDescendingcomparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfChar.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharDescendingcomparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharDescendingcomparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfChar.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharDescendingcomparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharDescendingcomparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfChar.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharDescendingcomparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharDescendingcomparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfChar.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testcharDescendingcomparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainerchar  arrs=new RandomArrayContainerchar();
    System.out.println("Building arrays for testcharDescendingcomparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.charArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.charArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.charArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingcharComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      char[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new char[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortcharTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfChar.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortUnsortedcomparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortUnsortedcomparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortUnsortedcomparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortUnsortedcomparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortUnsortedcomparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortUnsortedcomparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortUnsortedcomparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortUnsortedcomparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortUnsortedcomparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortUnsortedcomparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortUnsortedcomparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortUnsortedcomparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortUnsortedcomparatorSortSawArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortUnsortedcomparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortUnsortedcomparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortUnsortedcomparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortUnsortedcomparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortUnsortedcomparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortUnsortedcomparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortUnsortedcomparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortUnsortedcomparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortUnsortedcomparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortUnsortedcomparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortUnsortedcomparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortUnsortedcomparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortUnsortedcomparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortUnsortedcomparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortUnsortedcomparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortUnsortedcomparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortUnsortedcomparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortAscendingcomparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortAscendingcomparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortAscendingcomparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortAscendingcomparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortAscendingcomparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortAscendingcomparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortAscendingcomparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortAscendingcomparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortAscendingcomparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortAscendingcomparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortAscendingcomparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortAscendingcomparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortAscendingcomparatorSortSawArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortAscendingcomparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortAscendingcomparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortAscendingcomparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortAscendingcomparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortAscendingcomparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortAscendingcomparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortAscendingcomparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortAscendingcomparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortAscendingcomparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortAscendingcomparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortAscendingcomparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortAscendingcomparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortAscendingcomparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortAscendingcomparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortAscendingcomparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortAscendingcomparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortAscendingcomparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortDescendingcomparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortDescendingcomparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfShort.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortDescendingcomparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortDescendingcomparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfShort.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortDescendingcomparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortDescendingcomparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfShort.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortDescendingcomparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortDescendingcomparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfShort.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortDescendingcomparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortDescendingcomparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfShort.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortDescendingcomparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortDescendingcomparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfShort.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortDescendingcomparatorSortSawArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortDescendingcomparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfShort.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortDescendingcomparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortDescendingcomparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfShort.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortDescendingcomparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortDescendingcomparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfShort.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortDescendingcomparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortDescendingcomparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfShort.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortDescendingcomparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortDescendingcomparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfShort.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortDescendingcomparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortDescendingcomparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfShort.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortDescendingcomparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortDescendingcomparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfShort.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortDescendingcomparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortDescendingcomparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfShort.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testshortDescendingcomparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainershort  arrs=new RandomArrayContainershort();
    System.out.println("Building arrays for testshortDescendingcomparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.shortArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.shortArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.shortArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingshortComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      short[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new short[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortshortTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfShort.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintUnsortedcomparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintUnsortedcomparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintUnsortedcomparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintUnsortedcomparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintUnsortedcomparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintUnsortedcomparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintUnsortedcomparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintUnsortedcomparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintUnsortedcomparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintUnsortedcomparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintUnsortedcomparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintUnsortedcomparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintUnsortedcomparatorSortSawArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintUnsortedcomparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintUnsortedcomparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintUnsortedcomparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintUnsortedcomparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintUnsortedcomparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintUnsortedcomparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintUnsortedcomparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintUnsortedcomparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintUnsortedcomparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintUnsortedcomparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintUnsortedcomparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintUnsortedcomparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintUnsortedcomparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintUnsortedcomparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintUnsortedcomparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintUnsortedcomparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintUnsortedcomparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintAscendingcomparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintAscendingcomparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintAscendingcomparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintAscendingcomparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintAscendingcomparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintAscendingcomparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintAscendingcomparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintAscendingcomparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintAscendingcomparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintAscendingcomparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintAscendingcomparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintAscendingcomparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintAscendingcomparatorSortSawArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintAscendingcomparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintAscendingcomparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintAscendingcomparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintAscendingcomparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintAscendingcomparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintAscendingcomparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintAscendingcomparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintAscendingcomparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintAscendingcomparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintAscendingcomparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintAscendingcomparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintAscendingcomparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintAscendingcomparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintAscendingcomparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintAscendingcomparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintAscendingcomparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintAscendingcomparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintDescendingcomparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintDescendingcomparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfInt.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintDescendingcomparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintDescendingcomparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfInt.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintDescendingcomparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintDescendingcomparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfInt.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintDescendingcomparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintDescendingcomparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfInt.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintDescendingcomparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintDescendingcomparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfInt.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintDescendingcomparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintDescendingcomparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfInt.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintDescendingcomparatorSortSawArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintDescendingcomparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfInt.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintDescendingcomparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintDescendingcomparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfInt.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintDescendingcomparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintDescendingcomparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfInt.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintDescendingcomparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintDescendingcomparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfInt.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintDescendingcomparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintDescendingcomparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfInt.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintDescendingcomparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintDescendingcomparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfInt.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintDescendingcomparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintDescendingcomparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfInt.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintDescendingcomparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintDescendingcomparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfInt.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testintDescendingcomparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainerint  arrs=new RandomArrayContainerint();
    System.out.println("Building arrays for testintDescendingcomparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.intArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.intArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.intArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingintComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      int[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new int[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortintTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfInt.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongUnsortedcomparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongUnsortedcomparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedlongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongUnsortedcomparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongUnsortedcomparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedlongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongUnsortedcomparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongUnsortedcomparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedlongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongUnsortedcomparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongUnsortedcomparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedlongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongUnsortedcomparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongUnsortedcomparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedlongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongUnsortedcomparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongUnsortedcomparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedlongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongUnsortedcomparatorSortSawArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongUnsortedcomparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedlongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongUnsortedcomparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongUnsortedcomparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedlongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongUnsortedcomparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongUnsortedcomparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedlongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongUnsortedcomparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongUnsortedcomparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedlongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongUnsortedcomparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongUnsortedcomparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedlongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongUnsortedcomparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongUnsortedcomparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedlongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongUnsortedcomparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongUnsortedcomparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedlongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongUnsortedcomparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongUnsortedcomparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedlongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongUnsortedcomparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongUnsortedcomparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedlongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongAscendingcomparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongAscendingcomparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendinglongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongAscendingcomparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongAscendingcomparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendinglongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongAscendingcomparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongAscendingcomparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendinglongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongAscendingcomparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongAscendingcomparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendinglongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongAscendingcomparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongAscendingcomparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendinglongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongAscendingcomparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongAscendingcomparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendinglongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongAscendingcomparatorSortSawArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongAscendingcomparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendinglongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongAscendingcomparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongAscendingcomparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendinglongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongAscendingcomparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongAscendingcomparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendinglongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongAscendingcomparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongAscendingcomparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendinglongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongAscendingcomparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongAscendingcomparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendinglongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongAscendingcomparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongAscendingcomparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendinglongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongAscendingcomparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongAscendingcomparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendinglongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongAscendingcomparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongAscendingcomparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendinglongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongAscendingcomparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongAscendingcomparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendinglongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongDescendingcomparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongDescendingcomparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendinglongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfLong.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongDescendingcomparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongDescendingcomparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendinglongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfLong.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongDescendingcomparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongDescendingcomparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendinglongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfLong.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongDescendingcomparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongDescendingcomparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendinglongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfLong.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongDescendingcomparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongDescendingcomparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendinglongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfLong.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongDescendingcomparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongDescendingcomparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendinglongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfLong.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongDescendingcomparatorSortSawArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongDescendingcomparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendinglongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfLong.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongDescendingcomparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongDescendingcomparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendinglongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfLong.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongDescendingcomparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongDescendingcomparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendinglongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfLong.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongDescendingcomparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongDescendingcomparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendinglongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfLong.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongDescendingcomparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongDescendingcomparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendinglongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfLong.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongDescendingcomparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongDescendingcomparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendinglongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfLong.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongDescendingcomparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongDescendingcomparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendinglongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfLong.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongDescendingcomparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongDescendingcomparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendinglongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfLong.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testlongDescendingcomparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainerlong  arrs=new RandomArrayContainerlong();
    System.out.println("Building arrays for testlongDescendingcomparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.longArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.longArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.longArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendinglongComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      long[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new long[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortlongTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfLong.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatUnsortedcomparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatUnsortedcomparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatUnsortedcomparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatUnsortedcomparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatUnsortedcomparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatUnsortedcomparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatUnsortedcomparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatUnsortedcomparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatUnsortedcomparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatUnsortedcomparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatUnsortedcomparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatUnsortedcomparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatUnsortedcomparatorSortSawArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatUnsortedcomparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatUnsortedcomparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatUnsortedcomparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatUnsortedcomparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatUnsortedcomparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatUnsortedcomparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatUnsortedcomparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatUnsortedcomparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatUnsortedcomparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatUnsortedcomparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatUnsortedcomparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatUnsortedcomparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatUnsortedcomparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatUnsortedcomparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatUnsortedcomparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatUnsortedcomparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatUnsortedcomparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatUnsortedcomparatorSortWithNaNsAndZerosArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatUnsortedcomparatorSortWithNaNsAndZerosArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.WithNaNsAndZeros.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.WithNaNsAndZeros);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.WithNaNsAndZeros);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatAscendingcomparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatAscendingcomparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatAscendingcomparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatAscendingcomparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatAscendingcomparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatAscendingcomparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatAscendingcomparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatAscendingcomparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatAscendingcomparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatAscendingcomparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatAscendingcomparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatAscendingcomparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatAscendingcomparatorSortSawArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatAscendingcomparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatAscendingcomparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatAscendingcomparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatAscendingcomparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatAscendingcomparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatAscendingcomparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatAscendingcomparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatAscendingcomparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatAscendingcomparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatAscendingcomparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatAscendingcomparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatAscendingcomparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatAscendingcomparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatAscendingcomparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatAscendingcomparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatAscendingcomparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatAscendingcomparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatAscendingcomparatorSortWithNaNsAndZerosArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatAscendingcomparatorSortWithNaNsAndZerosArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.WithNaNsAndZeros.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.WithNaNsAndZeros);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.WithNaNsAndZeros);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatDescendingcomparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatDescendingcomparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfFloat.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatDescendingcomparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatDescendingcomparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfFloat.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatDescendingcomparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatDescendingcomparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfFloat.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatDescendingcomparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatDescendingcomparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfFloat.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatDescendingcomparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatDescendingcomparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfFloat.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatDescendingcomparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatDescendingcomparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfFloat.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatDescendingcomparatorSortSawArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatDescendingcomparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfFloat.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatDescendingcomparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatDescendingcomparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfFloat.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatDescendingcomparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatDescendingcomparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfFloat.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatDescendingcomparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatDescendingcomparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfFloat.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatDescendingcomparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatDescendingcomparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfFloat.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatDescendingcomparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatDescendingcomparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfFloat.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatDescendingcomparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatDescendingcomparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfFloat.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatDescendingcomparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatDescendingcomparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfFloat.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatDescendingcomparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatDescendingcomparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfFloat.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testfloatDescendingcomparatorSortWithNaNsAndZerosArrayBuilder()
  {
    RandomArrayContainerfloat  arrs=new RandomArrayContainerfloat();
    System.out.println("Building arrays for testfloatDescendingcomparatorSortWithNaNsAndZerosArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.floatArrayBuilder.WithNaNsAndZeros.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.floatArrayBuilder.WithNaNsAndZeros);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.floatArrayBuilder.WithNaNsAndZeros);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingfloatComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      float[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new float[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortfloatTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfFloat.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleUnsortedcomparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleUnsortedcomparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsorteddoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleUnsortedcomparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleUnsortedcomparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsorteddoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleUnsortedcomparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleUnsortedcomparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsorteddoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleUnsortedcomparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleUnsortedcomparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsorteddoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleUnsortedcomparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleUnsortedcomparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsorteddoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleUnsortedcomparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleUnsortedcomparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsorteddoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleUnsortedcomparatorSortSawArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleUnsortedcomparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsorteddoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleUnsortedcomparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleUnsortedcomparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsorteddoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleUnsortedcomparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleUnsortedcomparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsorteddoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleUnsortedcomparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleUnsortedcomparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsorteddoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleUnsortedcomparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleUnsortedcomparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsorteddoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleUnsortedcomparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleUnsortedcomparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsorteddoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleUnsortedcomparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleUnsortedcomparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsorteddoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleUnsortedcomparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleUnsortedcomparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsorteddoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleUnsortedcomparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleUnsortedcomparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsorteddoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleUnsortedcomparatorSortWithNaNsAndZerosArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleUnsortedcomparatorSortWithNaNsAndZerosArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.WithNaNsAndZeros.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.WithNaNsAndZeros);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.WithNaNsAndZeros);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsorteddoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleAscendingcomparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleAscendingcomparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingdoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleAscendingcomparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleAscendingcomparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingdoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleAscendingcomparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleAscendingcomparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingdoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleAscendingcomparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleAscendingcomparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingdoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleAscendingcomparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleAscendingcomparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingdoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleAscendingcomparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleAscendingcomparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingdoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleAscendingcomparatorSortSawArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleAscendingcomparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingdoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleAscendingcomparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleAscendingcomparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingdoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleAscendingcomparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleAscendingcomparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingdoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleAscendingcomparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleAscendingcomparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingdoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleAscendingcomparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleAscendingcomparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingdoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleAscendingcomparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleAscendingcomparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingdoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleAscendingcomparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleAscendingcomparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingdoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleAscendingcomparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleAscendingcomparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingdoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleAscendingcomparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleAscendingcomparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingdoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleAscendingcomparatorSortWithNaNsAndZerosArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleAscendingcomparatorSortWithNaNsAndZerosArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.WithNaNsAndZeros.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.WithNaNsAndZeros);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.WithNaNsAndZeros);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingdoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleDescendingcomparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleDescendingcomparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingdoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfDouble.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleDescendingcomparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleDescendingcomparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingdoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfDouble.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleDescendingcomparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleDescendingcomparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingdoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfDouble.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleDescendingcomparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleDescendingcomparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingdoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfDouble.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleDescendingcomparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleDescendingcomparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingdoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfDouble.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleDescendingcomparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleDescendingcomparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingdoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfDouble.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleDescendingcomparatorSortSawArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleDescendingcomparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingdoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfDouble.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleDescendingcomparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleDescendingcomparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingdoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfDouble.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleDescendingcomparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleDescendingcomparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingdoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfDouble.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleDescendingcomparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleDescendingcomparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingdoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfDouble.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleDescendingcomparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleDescendingcomparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingdoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfDouble.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleDescendingcomparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleDescendingcomparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingdoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfDouble.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleDescendingcomparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleDescendingcomparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingdoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfDouble.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleDescendingcomparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleDescendingcomparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingdoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfDouble.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleDescendingcomparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleDescendingcomparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingdoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfDouble.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testdoubleDescendingcomparatorSortWithNaNsAndZerosArrayBuilder()
  {
    RandomArrayContainerdouble  arrs=new RandomArrayContainerdouble();
    System.out.println("Building arrays for testdoubleDescendingcomparatorSortWithNaNsAndZerosArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.doubleArrayBuilder.WithNaNsAndZeros.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.doubleArrayBuilder.WithNaNsAndZeros);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.doubleArrayBuilder.WithNaNsAndZeros);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingdoubleComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      double[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new double[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortdoubleTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfDouble.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerUnsortedcomparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerUnsortedcomparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerUnsortedcomparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerUnsortedcomparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerUnsortedcomparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerUnsortedcomparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerUnsortedcomparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerUnsortedcomparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerUnsortedcomparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerUnsortedcomparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerUnsortedcomparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerUnsortedcomparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerUnsortedcomparatorSortSawArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerUnsortedcomparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerUnsortedcomparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerUnsortedcomparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerUnsortedcomparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerUnsortedcomparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerUnsortedcomparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerUnsortedcomparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerUnsortedcomparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerUnsortedcomparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerUnsortedcomparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerUnsortedcomparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerUnsortedcomparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerUnsortedcomparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerUnsortedcomparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerUnsortedcomparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerUnsortedcomparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerUnsortedcomparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getUnsortedIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerAscendingcomparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerAscendingcomparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerAscendingcomparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerAscendingcomparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerAscendingcomparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerAscendingcomparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerAscendingcomparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerAscendingcomparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerAscendingcomparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerAscendingcomparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerAscendingcomparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerAscendingcomparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerAscendingcomparatorSortSawArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerAscendingcomparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerAscendingcomparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerAscendingcomparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerAscendingcomparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerAscendingcomparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerAscendingcomparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerAscendingcomparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerAscendingcomparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerAscendingcomparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerAscendingcomparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerAscendingcomparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerAscendingcomparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerAscendingcomparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerAscendingcomparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerAscendingcomparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerAscendingcomparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerAscendingcomparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getAscendingIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerDescendingcomparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerDescendingcomparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfRef.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerDescendingcomparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerDescendingcomparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfRef.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerDescendingcomparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerDescendingcomparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfRef.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerDescendingcomparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerDescendingcomparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfRef.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerDescendingcomparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerDescendingcomparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfRef.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerDescendingcomparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerDescendingcomparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfRef.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerDescendingcomparatorSortSawArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerDescendingcomparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfRef.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerDescendingcomparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerDescendingcomparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfRef.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerDescendingcomparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerDescendingcomparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfRef.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerDescendingcomparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerDescendingcomparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfRef.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerDescendingcomparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerDescendingcomparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfRef.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerDescendingcomparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerDescendingcomparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfRef.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerDescendingcomparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerDescendingcomparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfRef.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerDescendingcomparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerDescendingcomparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfRef.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerDescendingcomparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerDescendingcomparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    var sorter=JunitUtil.getDescendingIntegerComparator();
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.comparatorSortObjectTimSort.uncheckedsort(arr,0,arrLength,sorter);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfRef.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerAscendingnonComparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerAscendingnonComparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.sortObjectTimSort.uncheckedsort(arr,0,arrLength);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerAscendingnonComparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerAscendingnonComparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.sortObjectTimSort.uncheckedsort(arr,0,arrLength);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerAscendingnonComparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerAscendingnonComparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.sortObjectTimSort.uncheckedsort(arr,0,arrLength);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerAscendingnonComparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerAscendingnonComparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.sortObjectTimSort.uncheckedsort(arr,0,arrLength);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerAscendingnonComparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerAscendingnonComparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.sortObjectTimSort.uncheckedsort(arr,0,arrLength);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerAscendingnonComparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerAscendingnonComparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.sortObjectTimSort.uncheckedsort(arr,0,arrLength);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerAscendingnonComparatorSortSawArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerAscendingnonComparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.sortObjectTimSort.uncheckedsort(arr,0,arrLength);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerAscendingnonComparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerAscendingnonComparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.sortObjectTimSort.uncheckedsort(arr,0,arrLength);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerAscendingnonComparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerAscendingnonComparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.sortObjectTimSort.uncheckedsort(arr,0,arrLength);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerAscendingnonComparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerAscendingnonComparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.sortObjectTimSort.uncheckedsort(arr,0,arrLength);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerAscendingnonComparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerAscendingnonComparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.sortObjectTimSort.uncheckedsort(arr,0,arrLength);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerAscendingnonComparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerAscendingnonComparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.sortObjectTimSort.uncheckedsort(arr,0,arrLength);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerAscendingnonComparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerAscendingnonComparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.sortObjectTimSort.uncheckedsort(arr,0,arrLength);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerAscendingnonComparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerAscendingnonComparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.sortObjectTimSort.uncheckedsort(arr,0,arrLength);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerAscendingnonComparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerAscendingnonComparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.sortObjectTimSort.uncheckedsort(arr,0,arrLength);
      }
      {
        Arrays.sort(copy,0,arrLength);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerDescendingnonComparatorSortRandomizedArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerDescendingnonComparatorSortRandomizedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Randomized.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Randomized);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Randomized);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.reverseSortObjectTimSort.uncheckedsort(arr,0,arrLength);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfRef.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerDescendingnonComparatorSortAscendingArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerDescendingnonComparatorSortAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Ascending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Ascending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Ascending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.reverseSortObjectTimSort.uncheckedsort(arr,0,arrLength);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfRef.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerDescendingnonComparatorSortDescendingArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerDescendingnonComparatorSortDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Descending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Descending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Descending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.reverseSortObjectTimSort.uncheckedsort(arr,0,arrLength);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfRef.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerDescendingnonComparatorSortAllEqualsArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerDescendingnonComparatorSortAllEqualsArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.AllEquals.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.AllEquals);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.AllEquals);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.reverseSortObjectTimSort.uncheckedsort(arr,0,arrLength);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfRef.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerDescendingnonComparatorSortMergeAscendingArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerDescendingnonComparatorSortMergeAscendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.MergeAscending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.MergeAscending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.MergeAscending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.reverseSortObjectTimSort.uncheckedsort(arr,0,arrLength);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfRef.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerDescendingnonComparatorSortMergeDescendingArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerDescendingnonComparatorSortMergeDescendingArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.MergeDescending.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.MergeDescending);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.MergeDescending);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.reverseSortObjectTimSort.uncheckedsort(arr,0,arrLength);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfRef.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerDescendingnonComparatorSortSawArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerDescendingnonComparatorSortSawArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Saw.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Saw);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Saw);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.reverseSortObjectTimSort.uncheckedsort(arr,0,arrLength);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfRef.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerDescendingnonComparatorSortSortedRepeatedArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerDescendingnonComparatorSortSortedRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.SortedRepeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.SortedRepeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.SortedRepeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.reverseSortObjectTimSort.uncheckedsort(arr,0,arrLength);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfRef.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerDescendingnonComparatorSortRepeatedArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerDescendingnonComparatorSortRepeatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Repeated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Repeated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Repeated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.reverseSortObjectTimSort.uncheckedsort(arr,0,arrLength);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfRef.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerDescendingnonComparatorSortDuplicatedArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerDescendingnonComparatorSortDuplicatedArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Duplicated.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Duplicated);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Duplicated);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.reverseSortObjectTimSort.uncheckedsort(arr,0,arrLength);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfRef.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerDescendingnonComparatorSortSortedOrganPipesArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerDescendingnonComparatorSortSortedOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.SortedOrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.SortedOrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.SortedOrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.reverseSortObjectTimSort.uncheckedsort(arr,0,arrLength);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfRef.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerDescendingnonComparatorSortOrganPipesArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerDescendingnonComparatorSortOrganPipesArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.OrganPipes.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.OrganPipes);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.OrganPipes);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.reverseSortObjectTimSort.uncheckedsort(arr,0,arrLength);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfRef.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerDescendingnonComparatorSortStaggerArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerDescendingnonComparatorSortStaggerArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Stagger.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Stagger);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Stagger);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.reverseSortObjectTimSort.uncheckedsort(arr,0,arrLength);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfRef.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerDescendingnonComparatorSortPlateauArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerDescendingnonComparatorSortPlateauArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Plateau.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Plateau);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Plateau);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.reverseSortObjectTimSort.uncheckedsort(arr,0,arrLength);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfRef.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  @Test
  public void testIntegerDescendingnonComparatorSortShuffleArrayBuilder()
  {
    RandomArrayContainerInteger  arrs=new RandomArrayContainerInteger();
    System.out.println("Building arrays for testIntegerDescendingnonComparatorSortShuffleArrayBuilder");
    IntStream.of(lengths).parallel().forEach(arrLength->
    {
      if(JunitUtil.IntegerArrayBuilder.Shuffle.isRandomized())
      {
        LongStream.of(randSeeds).parallel().forEach(randSeed->
        {
          arrs.addArrays(randSeed,arrLength,JunitUtil.IntegerArrayBuilder.Shuffle);
        });
      }
      else
      {
        arrs.addArrays(0,arrLength,JunitUtil.IntegerArrayBuilder.Shuffle);
      }
    });
    System.out.println("Built "+arrs.size()+" arrays");
    arrs.stream().parallel().forEach(arr->
    {
      int arrLength;
      Integer[] copy;
      ArrCopy.uncheckedCopy(arr,0,copy=new Integer[arrLength=arr.length],0,arrLength);
      {
        SortUtil.reverseSortObjectTimSort.uncheckedsort(arr,0,arrLength);
      }
      {
        Arrays.sort(copy,0,arrLength);
        OmniArray.OfRef.reverseRange(copy,0,arrLength-1);
      }
      JunitUtil.uncheckedassertarraysAreEqual(arr,0,copy,0,arrLength);
    });
  }
  private static class RandomArrayContainerboolean extends ArrayList<boolean[]>
  {
    private static final long serialVersionUID=1L;
    private RandomArrayContainerboolean()
    {
      super(13);
    }
    private void addArrays(long randSeed,int arrLength,JunitUtil.booleanArrayBuilder builder)
    {
      Random rand=new Random(randSeed);
      for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
      {
        for(int i=0;i<numReps;++i)
        {
          boolean[] arr=new boolean[arrLength];
          builder.build(arr,rand,m);
          synchronized(this)
          {
            super.add(arr);
          }
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
    if(builder.isRandomized())
    {
      return 10;
    }
    return 1;
    /*
    return 1;
    */
  }
  private static int incrementM(JunitUtil.booleanArrayBuilder builder,int m)
  {
    switch(builder)
    {
      default:
        return m+1;
    }
  }
  private static class RandomArrayContainerbyte extends ArrayList<byte[]>
  {
    private static final long serialVersionUID=1L;
    private RandomArrayContainerbyte()
    {
      super(13);
    }
    private void addArrays(long randSeed,int arrLength,JunitUtil.byteArrayBuilder builder)
    {
      Random rand=new Random(randSeed);
      for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
      {
        for(int i=0;i<numReps;++i)
        {
          byte[] arr=new byte[arrLength];
          builder.build(arr,rand,m);
          synchronized(this)
          {
            super.add(arr);
          }
        }
      }
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
    if(builder.isRandomized())
    {
      return 10;
    }
    return 1;
    /*
    return 1;
    */
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
  private static class RandomArrayContainerchar extends ArrayList<char[]>
  {
    private static final long serialVersionUID=1L;
    private RandomArrayContainerchar()
    {
      super(13);
    }
    private void addArrays(long randSeed,int arrLength,JunitUtil.charArrayBuilder builder)
    {
      Random rand=new Random(randSeed);
      for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
      {
        for(int i=0;i<numReps;++i)
        {
          char[] arr=new char[arrLength];
          builder.build(arr,rand,m);
          synchronized(this)
          {
            super.add(arr);
          }
        }
      }
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
      return 10;
    }
    return 1;
    /*
    if(builder.isRandomized())
    {
      if(arrLength>3201 || arrLength<47)
        {
          return 1;
        }
        return 7;
    }
    return 1;
    */
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
  private static class RandomArrayContainershort extends ArrayList<short[]>
  {
    private static final long serialVersionUID=1L;
    private RandomArrayContainershort()
    {
      super(13);
    }
    private void addArrays(long randSeed,int arrLength,JunitUtil.shortArrayBuilder builder)
    {
      Random rand=new Random(randSeed);
      for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
      {
        for(int i=0;i<numReps;++i)
        {
          short[] arr=new short[arrLength];
          builder.build(arr,rand,m);
          synchronized(this)
          {
            super.add(arr);
          }
        }
      }
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
      return 10;
    }
    return 1;
    /*
    if(builder.isRandomized())
    {
      if(arrLength>3201 || arrLength<47)
        {
          return 1;
        }
        return 7;
    }
    return 1;
    */
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
  private static class RandomArrayContainerint extends ArrayList<int[]>
  {
    private static final long serialVersionUID=1L;
    private RandomArrayContainerint()
    {
      super(13);
    }
    private void addArrays(long randSeed,int arrLength,JunitUtil.intArrayBuilder builder)
    {
      Random rand=new Random(randSeed);
      for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
      {
        for(int i=0;i<numReps;++i)
        {
          int[] arr=new int[arrLength];
          builder.build(arr,rand,m);
          synchronized(this)
          {
            super.add(arr);
          }
        }
      }
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
    if(builder.isRandomized())
    {
      return 10;
    }
    return 1;
    /*
    return 1;
    */
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
  private static class RandomArrayContainerlong extends ArrayList<long[]>
  {
    private static final long serialVersionUID=1L;
    private RandomArrayContainerlong()
    {
      super(13);
    }
    private void addArrays(long randSeed,int arrLength,JunitUtil.longArrayBuilder builder)
    {
      Random rand=new Random(randSeed);
      for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
      {
        for(int i=0;i<numReps;++i)
        {
          long[] arr=new long[arrLength];
          builder.build(arr,rand,m);
          synchronized(this)
          {
            super.add(arr);
          }
        }
      }
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
    if(builder.isRandomized())
    {
      return 10;
    }
    return 1;
    /*
    return 1;
    */
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
  private static class RandomArrayContainerfloat extends ArrayList<float[]>
  {
    private static final long serialVersionUID=1L;
    private RandomArrayContainerfloat()
    {
      super(13);
    }
    private void addArrays(long randSeed,int arrLength,JunitUtil.floatArrayBuilder builder)
    {
      Random rand=new Random(randSeed);
      for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
      {
        for(int i=0;i<numReps;++i)
        {
          float[] arr=new float[arrLength];
          builder.build(arr,rand,m);
          synchronized(this)
          {
            super.add(arr);
          }
        }
      }
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
    if(builder.isRandomized())
    {
      return 10;
    }
    return 1;
    /*
    if(builder==JunitUtil.floatArrayBuilder.WithNaNsAndZeros)
    {
      return 2;
    }
    return 1;
    */
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
  private static class RandomArrayContainerdouble extends ArrayList<double[]>
  {
    private static final long serialVersionUID=1L;
    private RandomArrayContainerdouble()
    {
      super(13);
    }
    private void addArrays(long randSeed,int arrLength,JunitUtil.doubleArrayBuilder builder)
    {
      Random rand=new Random(randSeed);
      for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
      {
        for(int i=0;i<numReps;++i)
        {
          double[] arr=new double[arrLength];
          builder.build(arr,rand,m);
          synchronized(this)
          {
            super.add(arr);
          }
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
    if(builder.isRandomized())
    {
      return 10;
    }
    return 1;
    /*
    if(builder==JunitUtil.doubleArrayBuilder.WithNaNsAndZeros)
    {
      return 2;
    }
    return 1;
    */
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
  private static class RandomArrayContainerInteger extends ArrayList<Integer[]>
  {
    private static final long serialVersionUID=1L;
    private RandomArrayContainerInteger()
    {
      super(13);
    }
    private void addArrays(long randSeed,int arrLength,JunitUtil.IntegerArrayBuilder builder)
    {
      Random rand=new Random(randSeed);
      for(int m=getMLo(builder),mHi=getMHi(builder,arrLength),numReps=getNumReps(builder,arrLength);m<=mHi;m=incrementM(builder,m))
      {
        for(int i=0;i<numReps;++i)
        {
          Integer[] arr=new Integer[arrLength];
          builder.build(arr,rand,m);
          synchronized(this)
          {
            super.add(arr);
          }
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
    if(builder.isRandomized())
    {
      return 10;
    }
    return 1;
    /*
    return 1;
    */
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
}
