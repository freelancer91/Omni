package omni.impl.seq;
import omni.util.EqualityUtil;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import omni.impl.seq.IntArrSeq.UncheckedStack;
import omni.impl.seq.IntArrSeq.CheckedStack;
import omni.impl.seq.IntArrSeq.UncheckedList;
import omni.impl.seq.IntArrSeq.CheckedList;
import java.util.ConcurrentModificationException;
import omni.api.OmniCollection;
import omni.util.OmniArray;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class IntArrSeqToStringTest
{
  private static final int MIN_LENGTH_STRING_VAL=0;
  private static final int MIN_TOSTRING_LENGTH=String.valueOf(MIN_LENGTH_STRING_VAL).length();
  private static final int MAX_TOSTRING_LENGTH=11;
  @Test
  public void testEmptyToStringUncheckedStack()
  {
    String expected=new ArrayList().toString();
    Assertions.assertEquals(expected,new UncheckedStack().toString());
  }
  @Test
  public void testOOMToStringUncheckedStack()
  {
    int length=Integer.MAX_VALUE/(MIN_TOSTRING_LENGTH+2)-1;
    var seq=new UncheckedStack(length,new int[length+1]);
    Assertions.assertDoesNotThrow(()->seq.toString());
    seq.add(MIN_LENGTH_STRING_VAL);
    Assertions.assertThrows(OutOfMemoryError.class,()->seq.toString());
  }
  private static Object[] createAscendingSequencePairUncheckedStack(int length)
  {
    OmniCollection.OfInt seq=new UncheckedStack(length);
    ArrayList arrayList=new ArrayList(length);
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
    Object[] seqPair=createAscendingSequencePairUncheckedStack(length);
    ArrayList arrayList=(ArrayList)seqPair[0];
    OmniCollection.OfInt seq=(OmniCollection.OfInt)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testSmallToStringUncheckedStack()
  {
    int length=100;
    Object[] seqPair=createAscendingSequencePairUncheckedStack(length);
    ArrayList arrayList=(ArrayList)seqPair[0];
    OmniCollection.OfInt seq=(OmniCollection.OfInt)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testEmptyToStringUncheckedList()
  {
    String expected=new ArrayList().toString();
    Assertions.assertEquals(expected,new UncheckedList().toString());
  }
  @Test
  public void testOOMToStringUncheckedList()
  {
    int length=Integer.MAX_VALUE/(MIN_TOSTRING_LENGTH+2)-1;
    var seq=new UncheckedList(length,new int[length+1]);
    Assertions.assertDoesNotThrow(()->seq.toString());
    seq.add(MIN_LENGTH_STRING_VAL);
    Assertions.assertThrows(OutOfMemoryError.class,()->seq.toString());
  }
  private static Object[] createAscendingSequencePairUncheckedList(int length)
  {
    OmniCollection.OfInt seq=new UncheckedList(length);
    ArrayList arrayList=new ArrayList(length);
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
    Object[] seqPair=createAscendingSequencePairUncheckedList(length);
    ArrayList arrayList=(ArrayList)seqPair[0];
    OmniCollection.OfInt seq=(OmniCollection.OfInt)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testSmallToStringUncheckedList()
  {
    int length=100;
    Object[] seqPair=createAscendingSequencePairUncheckedList(length);
    ArrayList arrayList=(ArrayList)seqPair[0];
    OmniCollection.OfInt seq=(OmniCollection.OfInt)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testEmptyToStringCheckedStack()
  {
    String expected=new ArrayList().toString();
    Assertions.assertEquals(expected,new CheckedStack().toString());
  }
  @Test
  public void testOOMToStringCheckedStack()
  {
    int length=Integer.MAX_VALUE/(MIN_TOSTRING_LENGTH+2)-1;
    var seq=new CheckedStack(length,new int[length+1]);
    Assertions.assertDoesNotThrow(()->seq.toString());
    seq.add(MIN_LENGTH_STRING_VAL);
    Assertions.assertThrows(OutOfMemoryError.class,()->seq.toString());
  }
  private static Object[] createAscendingSequencePairCheckedStack(int length)
  {
    OmniCollection.OfInt seq=new CheckedStack(length);
    ArrayList arrayList=new ArrayList(length);
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
    Object[] seqPair=createAscendingSequencePairCheckedStack(length);
    ArrayList arrayList=(ArrayList)seqPair[0];
    OmniCollection.OfInt seq=(OmniCollection.OfInt)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testSmallToStringCheckedStack()
  {
    int length=100;
    Object[] seqPair=createAscendingSequencePairCheckedStack(length);
    ArrayList arrayList=(ArrayList)seqPair[0];
    OmniCollection.OfInt seq=(OmniCollection.OfInt)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testEmptyToStringCheckedList()
  {
    String expected=new ArrayList().toString();
    Assertions.assertEquals(expected,new CheckedList().toString());
  }
  @Test
  public void testOOMToStringCheckedList()
  {
    int length=Integer.MAX_VALUE/(MIN_TOSTRING_LENGTH+2)-1;
    var seq=new CheckedList(length,new int[length+1]);
    Assertions.assertDoesNotThrow(()->seq.toString());
    seq.add(MIN_LENGTH_STRING_VAL);
    Assertions.assertThrows(OutOfMemoryError.class,()->seq.toString());
  }
  private static Object[] createAscendingSequencePairCheckedList(int length)
  {
    OmniCollection.OfInt seq=new CheckedList(length);
    ArrayList arrayList=new ArrayList(length);
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
    Object[] seqPair=createAscendingSequencePairCheckedList(length);
    ArrayList arrayList=(ArrayList)seqPair[0];
    OmniCollection.OfInt seq=(OmniCollection.OfInt)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testSmallToStringCheckedList()
  {
    int length=100;
    Object[] seqPair=createAscendingSequencePairCheckedList(length);
    ArrayList arrayList=(ArrayList)seqPair[0];
    OmniCollection.OfInt seq=(OmniCollection.OfInt)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testEmptyToStringUncheckedSubList()
  {
    String expected=new ArrayList().toString();
    Assertions.assertEquals(expected,new UncheckedList().subList(0,0).toString());
  }
  @Test
  public void testOOMToStringUncheckedSubList()
  {
    int length=Integer.MAX_VALUE/(MIN_TOSTRING_LENGTH+2)-1;
    var root=new UncheckedList(length,new int[length+1]);
    var seq=root.subList(0,length);
    Assertions.assertDoesNotThrow(()->seq.toString());
    seq.add(MIN_LENGTH_STRING_VAL);
    Assertions.assertThrows(OutOfMemoryError.class,()->seq.toString());
  }
  private static Object[] createAscendingSequencePairUncheckedSubList(int length)
  {
    UncheckedList root=new UncheckedList(length);
    OmniCollection.OfInt seq=root.subList(0,0);
    ArrayList arrayList=new ArrayList(length);
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
    Object[] seqPair=createAscendingSequencePairUncheckedSubList(length);
    ArrayList arrayList=(ArrayList)seqPair[0];
    OmniCollection.OfInt seq=(OmniCollection.OfInt)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testSmallToStringUncheckedSubList()
  {
    int length=100;
    Object[] seqPair=createAscendingSequencePairUncheckedSubList(length);
    ArrayList arrayList=(ArrayList)seqPair[0];
    OmniCollection.OfInt seq=(OmniCollection.OfInt)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testEmptyToStringCheckedSubList()
  {
    String expected=new ArrayList().toString();
    CheckedList root=new CheckedList();
    var subList=root.subList(0,0);
    Assertions.assertEquals(expected,subList.toString());
    root.add(Integer.MIN_VALUE);
    Assertions.assertThrows(ConcurrentModificationException.class,()->subList.toString());
  }
  @Test
  public void testOOMToStringCheckedSubList()
  {
    int length=Integer.MAX_VALUE/(MIN_TOSTRING_LENGTH+2)-1;
    var root=new CheckedList(length,new int[length+1]);
    var seq=root.subList(0,length);
    Assertions.assertDoesNotThrow(()->seq.toString());
    seq.add(MIN_LENGTH_STRING_VAL);
    Assertions.assertThrows(OutOfMemoryError.class,()->seq.toString());
  }
  private static Object[] createAscendingSequencePairCheckedSubList(int length)
  {
    CheckedList root=new CheckedList(length);
    OmniCollection.OfInt seq=root.subList(0,0);
    ArrayList arrayList=new ArrayList(length);
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
    Object[] seqPair=createAscendingSequencePairCheckedSubList(length);
    ArrayList arrayList=(ArrayList)seqPair[0];
    OmniCollection.OfInt seq=(OmniCollection.OfInt)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testSmallToStringCheckedSubList()
  {
    int length=100;
    Object[] seqPair=createAscendingSequencePairCheckedSubList(length);
    ArrayList arrayList=(ArrayList)seqPair[0];
    OmniCollection.OfInt seq=(OmniCollection.OfInt)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
}
