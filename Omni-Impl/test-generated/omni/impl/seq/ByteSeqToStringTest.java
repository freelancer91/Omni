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
public class ByteSeqToStringTest
{
  private static final byte MIN_LENGTH_STRING_VAL=0;
  private static final int MIN_TOSTRING_LENGTH=String.valueOf(MIN_LENGTH_STRING_VAL).length();
  private static final int MAX_TOSTRING_LENGTH=4;
  @Test
  public void testEmptyToStringArrSeqUncheckedStack()
  {
    var expected=new ArrayList().toString();
    Assertions.assertEquals(expected,new ByteArrSeq.UncheckedStack().toString());
  }
  @Test
  public void testOOMToStringArrSeqUncheckedStack()
  {
    int length=Integer.MAX_VALUE/(MIN_TOSTRING_LENGTH+2)-1;
    var seq=new ByteArrSeq.UncheckedStack(length,new byte[length+1]);
    Assertions.assertDoesNotThrow(()->seq.toString());
    seq.add(MIN_LENGTH_STRING_VAL);
    Assertions.assertThrows(OutOfMemoryError.class,()->seq.toString());
  }
  private static Object[] createAscendingSequencePairArrSeqUncheckedStack(int length)
  {
    var seq=new ByteArrSeq.UncheckedStack(length);
    var arrayList=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      byte val=TypeConversionUtil.convertTobyte(i);
      seq.add(val);
      byte counterVal=TypeConversionUtil.convertTobyte(length-1-i);
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
    var seq=(OmniCollection.OfByte)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testSmallToStringUncheckedStack()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqUncheckedStack(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfByte)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testEmptyToStringArrSeqUncheckedList()
  {
    var expected=new ArrayList().toString();
    Assertions.assertEquals(expected,new ByteArrSeq.UncheckedList().toString());
  }
  @Test
  public void testOOMToStringArrSeqUncheckedList()
  {
    int length=Integer.MAX_VALUE/(MIN_TOSTRING_LENGTH+2)-1;
    var seq=new ByteArrSeq.UncheckedList(length,new byte[length+1]);
    Assertions.assertDoesNotThrow(()->seq.toString());
    seq.add(MIN_LENGTH_STRING_VAL);
    Assertions.assertThrows(OutOfMemoryError.class,()->seq.toString());
  }
  private static Object[] createAscendingSequencePairArrSeqUncheckedList(int length)
  {
    var seq=new ByteArrSeq.UncheckedList(length);
    var arrayList=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      byte val=TypeConversionUtil.convertTobyte(i);
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
    var seq=(OmniCollection.OfByte)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testSmallToStringUncheckedList()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqUncheckedList(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfByte)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testEmptyToStringArrSeqCheckedStack()
  {
    var expected=new ArrayList().toString();
    Assertions.assertEquals(expected,new ByteArrSeq.CheckedStack().toString());
  }
  @Test
  public void testOOMToStringArrSeqCheckedStack()
  {
    int length=Integer.MAX_VALUE/(MIN_TOSTRING_LENGTH+2)-1;
    var seq=new ByteArrSeq.CheckedStack(length,new byte[length+1]);
    Assertions.assertDoesNotThrow(()->seq.toString());
    seq.add(MIN_LENGTH_STRING_VAL);
    Assertions.assertThrows(OutOfMemoryError.class,()->seq.toString());
  }
  private static Object[] createAscendingSequencePairArrSeqCheckedStack(int length)
  {
    var seq=new ByteArrSeq.CheckedStack(length);
    var arrayList=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      byte val=TypeConversionUtil.convertTobyte(i);
      seq.add(val);
      byte counterVal=TypeConversionUtil.convertTobyte(length-1-i);
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
    var seq=(OmniCollection.OfByte)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testSmallToStringCheckedStack()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqCheckedStack(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfByte)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testEmptyToStringArrSeqCheckedList()
  {
    var expected=new ArrayList().toString();
    Assertions.assertEquals(expected,new ByteArrSeq.CheckedList().toString());
  }
  @Test
  public void testOOMToStringArrSeqCheckedList()
  {
    int length=Integer.MAX_VALUE/(MIN_TOSTRING_LENGTH+2)-1;
    var seq=new ByteArrSeq.CheckedList(length,new byte[length+1]);
    Assertions.assertDoesNotThrow(()->seq.toString());
    seq.add(MIN_LENGTH_STRING_VAL);
    Assertions.assertThrows(OutOfMemoryError.class,()->seq.toString());
  }
  private static Object[] createAscendingSequencePairArrSeqCheckedList(int length)
  {
    var seq=new ByteArrSeq.CheckedList(length);
    var arrayList=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      byte val=TypeConversionUtil.convertTobyte(i);
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
    var seq=(OmniCollection.OfByte)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testSmallToStringCheckedList()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqCheckedList(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfByte)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testEmptyToStringArrSeqUncheckedSubList()
  {
    var expected=new ArrayList().toString();
    Assertions.assertEquals(expected,new ByteArrSeq.UncheckedList().subList(0,0).toString());
  }
  @Test
  public void testOOMToStringArrSeqUncheckedSubList()
  {
    int length=Integer.MAX_VALUE/(MIN_TOSTRING_LENGTH+2)-1;
    var root=new ByteArrSeq.UncheckedList(length,new byte[length+1]);
    var seq=root.subList(0,length);
    Assertions.assertDoesNotThrow(()->seq.toString());
    seq.add(MIN_LENGTH_STRING_VAL);
    Assertions.assertThrows(OutOfMemoryError.class,()->seq.toString());
  }
  private static Object[] createAscendingSequencePairArrSeqUncheckedSubList(int length)
  {
    var root=new ByteArrSeq.UncheckedList(length);
    var seq=root.subList(0,0);
    var arrayList=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      byte val=TypeConversionUtil.convertTobyte(i);
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
    var seq=(OmniCollection.OfByte)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testSmallToStringUncheckedSubList()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqUncheckedSubList(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfByte)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testEmptyToStringArrSeqCheckedSubList()
  {
    var expected=new ArrayList().toString();
    var root=new ByteArrSeq.CheckedList();
    var subList=root.subList(0,0);
    Assertions.assertEquals(expected,subList.toString());
    root.add(Byte.MIN_VALUE);
    Assertions.assertThrows(ConcurrentModificationException.class,()->subList.toString());
  }
  @Test
  public void testOOMToStringArrSeqCheckedSubList()
  {
    int length=Integer.MAX_VALUE/(MIN_TOSTRING_LENGTH+2)-1;
    var root=new ByteArrSeq.CheckedList(length,new byte[length+1]);
    var seq=root.subList(0,length);
    Assertions.assertDoesNotThrow(()->seq.toString());
    seq.add(MIN_LENGTH_STRING_VAL);
    Assertions.assertThrows(OutOfMemoryError.class,()->seq.toString());
  }
  private static Object[] createAscendingSequencePairArrSeqCheckedSubList(int length)
  {
    var root=new ByteArrSeq.CheckedList(length);
    var seq=root.subList(0,0);
    var arrayList=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      byte val=TypeConversionUtil.convertTobyte(i);
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
    var seq=(OmniCollection.OfByte)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testSmallToStringCheckedSubList()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqCheckedSubList(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfByte)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
}
