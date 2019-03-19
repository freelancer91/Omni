package omni.impl.seq;
import omni.util.EqualityUtil;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import omni.api.OmniCollection;
import omni.util.OmniArray;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class IntSeqToStringTest
{
  private static final int MIN_LENGTH_STRING_VAL=0;
  private static final int MIN_TOSTRING_LENGTH=String.valueOf(MIN_LENGTH_STRING_VAL).length();
  private static final int MAX_TOSTRING_LENGTH=11;
  @Test
  public void testEmptyToStringArrSeqUncheckedStack()
  {
    var expected=new ArrayList().toString();
    Assertions.assertEquals(expected,new IntArrSeq.UncheckedStack().toString());
  }
  @Test
  public void testOOMToStringArrSeqUncheckedStack()
  {
    int length=Integer.MAX_VALUE/(MIN_TOSTRING_LENGTH+2)-1;
    var seq=new IntArrSeq.UncheckedStack(length,new int[length+1]);
    Assertions.assertDoesNotThrow(()->seq.toString());
    seq.add(MIN_LENGTH_STRING_VAL);
    Assertions.assertThrows(OutOfMemoryError.class,()->seq.toString());
  }
  private static Object[] createAscendingSequencePairArrSeqUncheckedStack(int length)
  {
    var seq=new IntArrSeq.UncheckedStack(length);
    var arrayList=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      int val=TypeConversionUtil.convertToint(i);
      seq.add(val);
      int counterVal=TypeConversionUtil.convertToint(length-1-i);
      arrayList.add(counterVal);
    }
    return new Object[]{arrayList,seq};
  }
  @Test
  public void testLargeToStringUncheckedStack()
  {
    int length=(OmniArray.MAX_ARR_SIZE/(MAX_TOSTRING_LENGTH+2))+1;
    var seqPair=createAscendingSequencePairArrSeqUncheckedStack(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfInt)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testSmallToStringUncheckedStack()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqUncheckedStack(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfInt)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testEmptyToStringArrSeqUncheckedList()
  {
    var expected=new ArrayList().toString();
    Assertions.assertEquals(expected,new IntArrSeq.UncheckedList().toString());
  }
  @Test
  public void testOOMToStringArrSeqUncheckedList()
  {
    int length=Integer.MAX_VALUE/(MIN_TOSTRING_LENGTH+2)-1;
    var seq=new IntArrSeq.UncheckedList(length,new int[length+1]);
    Assertions.assertDoesNotThrow(()->seq.toString());
    seq.add(MIN_LENGTH_STRING_VAL);
    Assertions.assertThrows(OutOfMemoryError.class,()->seq.toString());
  }
  private static Object[] createAscendingSequencePairArrSeqUncheckedList(int length)
  {
    var seq=new IntArrSeq.UncheckedList(length);
    var arrayList=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      int val=TypeConversionUtil.convertToint(i);
      seq.add(val);
      arrayList.add(val);
    }
    return new Object[]{arrayList,seq};
  }
  @Test
  public void testLargeToStringUncheckedList()
  {
    int length=(OmniArray.MAX_ARR_SIZE/(MAX_TOSTRING_LENGTH+2))+1;
    var seqPair=createAscendingSequencePairArrSeqUncheckedList(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfInt)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testSmallToStringUncheckedList()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqUncheckedList(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfInt)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testEmptyToStringArrSeqCheckedStack()
  {
    var expected=new ArrayList().toString();
    Assertions.assertEquals(expected,new IntArrSeq.CheckedStack().toString());
  }
  @Test
  public void testOOMToStringArrSeqCheckedStack()
  {
    int length=Integer.MAX_VALUE/(MIN_TOSTRING_LENGTH+2)-1;
    var seq=new IntArrSeq.CheckedStack(length,new int[length+1]);
    Assertions.assertDoesNotThrow(()->seq.toString());
    seq.add(MIN_LENGTH_STRING_VAL);
    Assertions.assertThrows(OutOfMemoryError.class,()->seq.toString());
  }
  private static Object[] createAscendingSequencePairArrSeqCheckedStack(int length)
  {
    var seq=new IntArrSeq.CheckedStack(length);
    var arrayList=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      int val=TypeConversionUtil.convertToint(i);
      seq.add(val);
      int counterVal=TypeConversionUtil.convertToint(length-1-i);
      arrayList.add(counterVal);
    }
    return new Object[]{arrayList,seq};
  }
  @Test
  public void testLargeToStringCheckedStack()
  {
    int length=(OmniArray.MAX_ARR_SIZE/(MAX_TOSTRING_LENGTH+2))+1;
    var seqPair=createAscendingSequencePairArrSeqCheckedStack(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfInt)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testSmallToStringCheckedStack()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqCheckedStack(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfInt)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testEmptyToStringArrSeqCheckedList()
  {
    var expected=new ArrayList().toString();
    Assertions.assertEquals(expected,new IntArrSeq.CheckedList().toString());
  }
  @Test
  public void testOOMToStringArrSeqCheckedList()
  {
    int length=Integer.MAX_VALUE/(MIN_TOSTRING_LENGTH+2)-1;
    var seq=new IntArrSeq.CheckedList(length,new int[length+1]);
    Assertions.assertDoesNotThrow(()->seq.toString());
    seq.add(MIN_LENGTH_STRING_VAL);
    Assertions.assertThrows(OutOfMemoryError.class,()->seq.toString());
  }
  private static Object[] createAscendingSequencePairArrSeqCheckedList(int length)
  {
    var seq=new IntArrSeq.CheckedList(length);
    var arrayList=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      int val=TypeConversionUtil.convertToint(i);
      seq.add(val);
      arrayList.add(val);
    }
    return new Object[]{arrayList,seq};
  }
  @Test
  public void testLargeToStringCheckedList()
  {
    int length=(OmniArray.MAX_ARR_SIZE/(MAX_TOSTRING_LENGTH+2))+1;
    var seqPair=createAscendingSequencePairArrSeqCheckedList(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfInt)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testSmallToStringCheckedList()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqCheckedList(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfInt)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testEmptyToStringArrSeqUncheckedSubList()
  {
    var expected=new ArrayList().toString();
    Assertions.assertEquals(expected,new IntArrSeq.UncheckedList().subList(0,0).toString());
  }
  @Test
  public void testOOMToStringArrSeqUncheckedSubList()
  {
    int length=Integer.MAX_VALUE/(MIN_TOSTRING_LENGTH+2)-1;
    var root=new IntArrSeq.UncheckedList(length,new int[length+1]);
    var seq=root.subList(0,length);
    Assertions.assertDoesNotThrow(()->seq.toString());
    seq.add(MIN_LENGTH_STRING_VAL);
    Assertions.assertThrows(OutOfMemoryError.class,()->seq.toString());
  }
  private static Object[] createAscendingSequencePairArrSeqUncheckedSubList(int length)
  {
    var root=new IntArrSeq.UncheckedList(length);
    var seq=root.subList(0,0);
    var arrayList=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      int val=TypeConversionUtil.convertToint(i);
      seq.add(val);
      arrayList.add(val);
    }
    return new Object[]{arrayList,seq};
  }
  @Test
  public void testLargeToStringUncheckedSubList()
  {
    int length=(OmniArray.MAX_ARR_SIZE/(MAX_TOSTRING_LENGTH+2))+1;
    var seqPair=createAscendingSequencePairArrSeqUncheckedSubList(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfInt)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testSmallToStringUncheckedSubList()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqUncheckedSubList(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfInt)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testEmptyToStringArrSeqCheckedSubList()
  {
    var expected=new ArrayList().toString();
    var root=new IntArrSeq.CheckedList();
    var subList=root.subList(0,0);
    Assertions.assertEquals(expected,subList.toString());
    root.add(Integer.MIN_VALUE);
    Assertions.assertThrows(ConcurrentModificationException.class,()->subList.toString());
  }
  @Test
  public void testOOMToStringArrSeqCheckedSubList()
  {
    int length=Integer.MAX_VALUE/(MIN_TOSTRING_LENGTH+2)-1;
    var root=new IntArrSeq.CheckedList(length,new int[length+1]);
    var seq=root.subList(0,length);
    Assertions.assertDoesNotThrow(()->seq.toString());
    seq.add(MIN_LENGTH_STRING_VAL);
    Assertions.assertThrows(OutOfMemoryError.class,()->seq.toString());
  }
  private static Object[] createAscendingSequencePairArrSeqCheckedSubList(int length)
  {
    var root=new IntArrSeq.CheckedList(length);
    var seq=root.subList(0,0);
    var arrayList=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      int val=TypeConversionUtil.convertToint(i);
      seq.add(val);
      arrayList.add(val);
    }
    return new Object[]{arrayList,seq};
  }
  @Test
  public void testLargeToStringCheckedSubList()
  {
    int length=(OmniArray.MAX_ARR_SIZE/(MAX_TOSTRING_LENGTH+2))+1;
    var seqPair=createAscendingSequencePairArrSeqCheckedSubList(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfInt)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testSmallToStringCheckedSubList()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqCheckedSubList(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfInt)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
}
