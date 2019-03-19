package omni.impl.seq;
import omni.util.EqualityUtil;
import omni.util.TypeConversionUtil;
import omni.impl.CheckedCollectionTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import omni.api.OmniCollection;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class RefSeqToStringTest
{
  private static final Object MIN_LENGTH_STRING_VAL=new Object(){@Override public String toString(){return "";}};
  private static final int MIN_TOSTRING_LENGTH=String.valueOf(MIN_LENGTH_STRING_VAL).length();
  @Test
  public void testEmptyToStringArrSeqUncheckedStack()
  {
    var expected=new ArrayList().toString();
    Assertions.assertEquals(expected,new RefArrSeq.UncheckedStack().toString());
  }
  @Test
  public void testOOMToStringArrSeqUncheckedStack()
  {
    int length=Integer.MAX_VALUE/(MIN_TOSTRING_LENGTH+2)-1;
    var seq=new RefArrSeq.UncheckedStack(length+1);
    for(int i=0;i<length;++i)
    {
      seq.add(MIN_LENGTH_STRING_VAL);
    }
    Assertions.assertDoesNotThrow(()->seq.toString());
    seq.add(MIN_LENGTH_STRING_VAL);
    Assertions.assertThrows(OutOfMemoryError.class,()->seq.toString());
  }
  private static Object[] createAscendingSequencePairArrSeqUncheckedStack(int length)
  {
    var seq=new RefArrSeq.UncheckedStack(length);
    var arrayList=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      var val=TypeConversionUtil.convertToInteger(i);
      seq.add(val);
      var counterVal=TypeConversionUtil.convertToInteger(length-1-i);
      arrayList.add(counterVal);
    }
    return new Object[]{arrayList,seq};
  }
  @Test
  public void testSmallToStringUncheckedStack()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqUncheckedStack(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfRef)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testEmptyToStringArrSeqUncheckedList()
  {
    var expected=new ArrayList().toString();
    Assertions.assertEquals(expected,new RefArrSeq.UncheckedList().toString());
  }
  @Test
  public void testOOMToStringArrSeqUncheckedList()
  {
    int length=Integer.MAX_VALUE/(MIN_TOSTRING_LENGTH+2)-1;
    var seq=new RefArrSeq.UncheckedList(length+1);
    for(int i=0;i<length;++i)
    {
      seq.add(MIN_LENGTH_STRING_VAL);
    }
    Assertions.assertDoesNotThrow(()->seq.toString());
    seq.add(MIN_LENGTH_STRING_VAL);
    Assertions.assertThrows(OutOfMemoryError.class,()->seq.toString());
  }
  private static Object[] createAscendingSequencePairArrSeqUncheckedList(int length)
  {
    var seq=new RefArrSeq.UncheckedList(length);
    var arrayList=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      var val=TypeConversionUtil.convertToInteger(i);
      seq.add(val);
      arrayList.add(val);
    }
    return new Object[]{arrayList,seq};
  }
  @Test
  public void testSmallToStringUncheckedList()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqUncheckedList(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfRef)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testEmptyToStringArrSeqCheckedStack()
  {
    var expected=new ArrayList().toString();
    Assertions.assertEquals(expected,new RefArrSeq.CheckedStack().toString());
  }
  @Test
  public void testOOMToStringArrSeqCheckedStack()
  {
    int length=Integer.MAX_VALUE/(MIN_TOSTRING_LENGTH+2)-1;
    var seq=new RefArrSeq.CheckedStack(length+1);
    for(int i=0;i<length;++i)
    {
      seq.add(MIN_LENGTH_STRING_VAL);
    }
    Assertions.assertDoesNotThrow(()->seq.toString());
    seq.add(MIN_LENGTH_STRING_VAL);
    Assertions.assertThrows(OutOfMemoryError.class,()->seq.toString());
  }
  private static Object[] createAscendingSequencePairArrSeqCheckedStack(int length)
  {
    var seq=new RefArrSeq.CheckedStack(length);
    var arrayList=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      var val=TypeConversionUtil.convertToInteger(i);
      seq.add(val);
      var counterVal=TypeConversionUtil.convertToInteger(length-1-i);
      arrayList.add(counterVal);
    }
    return new Object[]{arrayList,seq};
  }
  @Test
  public void testSmallToStringCheckedStack()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqCheckedStack(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfRef)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testModificationCheckCheckedStack()
  {
    var seq=new RefArrSeq.CheckedStack();
    var seqModifyingObject=CheckedCollectionTest.createCollectionModifyingObject(seq);
    for(int i=0;i<10;++i)
    {
      seq.add(seqModifyingObject);
    }
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toString());
  }
  @Test
  public void testEmptyToStringArrSeqCheckedList()
  {
    var expected=new ArrayList().toString();
    Assertions.assertEquals(expected,new RefArrSeq.CheckedList().toString());
  }
  @Test
  public void testOOMToStringArrSeqCheckedList()
  {
    int length=Integer.MAX_VALUE/(MIN_TOSTRING_LENGTH+2)-1;
    var seq=new RefArrSeq.CheckedList(length+1);
    for(int i=0;i<length;++i)
    {
      seq.add(MIN_LENGTH_STRING_VAL);
    }
    Assertions.assertDoesNotThrow(()->seq.toString());
    seq.add(MIN_LENGTH_STRING_VAL);
    Assertions.assertThrows(OutOfMemoryError.class,()->seq.toString());
  }
  private static Object[] createAscendingSequencePairArrSeqCheckedList(int length)
  {
    var seq=new RefArrSeq.CheckedList(length);
    var arrayList=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      var val=TypeConversionUtil.convertToInteger(i);
      seq.add(val);
      arrayList.add(val);
    }
    return new Object[]{arrayList,seq};
  }
  @Test
  public void testSmallToStringCheckedList()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqCheckedList(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfRef)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testModificationCheckCheckedList()
  {
    var seq=new RefArrSeq.CheckedList();
    var seqModifyingObject=CheckedCollectionTest.createCollectionModifyingObject(seq);
    for(int i=0;i<10;++i)
    {
      seq.add(seqModifyingObject);
    }
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toString());
  }
  @Test
  public void testEmptyToStringArrSeqUncheckedSubList()
  {
    var expected=new ArrayList().toString();
    Assertions.assertEquals(expected,new RefArrSeq.UncheckedList().subList(0,0).toString());
  }
  @Test
  public void testOOMToStringArrSeqUncheckedSubList()
  {
    int length=Integer.MAX_VALUE/(MIN_TOSTRING_LENGTH+2)-1;
    var root=new RefArrSeq.UncheckedList(length+1);
    for(int i=0;i<length;++i)
    {
      root.add(MIN_LENGTH_STRING_VAL);
    }
    var seq=root.subList(0,root.size());
    Assertions.assertDoesNotThrow(()->seq.toString());
    seq.add(MIN_LENGTH_STRING_VAL);
    Assertions.assertThrows(OutOfMemoryError.class,()->seq.toString());
  }
  private static Object[] createAscendingSequencePairArrSeqUncheckedSubList(int length)
  {
    var root=new RefArrSeq.UncheckedList(length);
    var seq=root.subList(0,0);
    var arrayList=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      var val=TypeConversionUtil.convertToInteger(i);
      seq.add(val);
      arrayList.add(val);
    }
    return new Object[]{arrayList,seq};
  }
  @Test
  public void testSmallToStringUncheckedSubList()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqUncheckedSubList(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfRef)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testEmptyToStringArrSeqCheckedSubList()
  {
    var expected=new ArrayList().toString();
    var root=new RefArrSeq.CheckedList();
    var subList=root.subList(0,0);
    Assertions.assertEquals(expected,subList.toString());
    root.add(null);
    Assertions.assertThrows(ConcurrentModificationException.class,()->subList.toString());
  }
  @Test
  public void testOOMToStringArrSeqCheckedSubList()
  {
    int length=Integer.MAX_VALUE/(MIN_TOSTRING_LENGTH+2)-1;
    var root=new RefArrSeq.CheckedList(length+1);
    for(int i=0;i<length;++i)
    {
      root.add(MIN_LENGTH_STRING_VAL);
    }
    var seq=root.subList(0,root.size());
    Assertions.assertDoesNotThrow(()->seq.toString());
    seq.add(MIN_LENGTH_STRING_VAL);
    Assertions.assertThrows(OutOfMemoryError.class,()->seq.toString());
  }
  private static Object[] createAscendingSequencePairArrSeqCheckedSubList(int length)
  {
    var root=new RefArrSeq.CheckedList(length);
    var seq=root.subList(0,0);
    var arrayList=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      var val=TypeConversionUtil.convertToInteger(i);
      seq.add(val);
      arrayList.add(val);
    }
    return new Object[]{arrayList,seq};
  }
  @Test
  public void testSmallToStringCheckedSubList()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqCheckedSubList(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfRef)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testModificationCheckCheckedSubList()
  {
    {
      var root=new RefArrSeq.CheckedList();
      var subList=root.subList(0,0);
      var rootModifyingObject=CheckedCollectionTest.createCollectionModifyingObject(root);
      for(int i=0;i<10;++i)
      {
        subList.add(rootModifyingObject);
      }
      Assertions.assertThrows(ConcurrentModificationException.class,()->subList.toString());
    }
    {
      var root=new RefArrSeq.CheckedList();
      var subList=root.subList(0,0);
      var subListModifyingObject=CheckedCollectionTest.createCollectionModifyingObject(subList);
      for(int i=0;i<10;++i)
      {
        subList.add(subListModifyingObject);
      }
      Assertions.assertThrows(ConcurrentModificationException.class,()->subList.toString());
    }
  }
}
