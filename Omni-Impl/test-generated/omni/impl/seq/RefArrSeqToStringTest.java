package omni.impl.seq;
import omni.util.EqualityUtil;
import omni.util.TypeConversionUtil;
import omni.impl.CheckedCollectionTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import omni.impl.seq.RefArrSeq.UncheckedStack;
import omni.impl.seq.RefArrSeq.CheckedStack;
import omni.impl.seq.RefArrSeq.UncheckedList;
import omni.impl.seq.RefArrSeq.CheckedList;
import java.util.ConcurrentModificationException;
import omni.api.OmniCollection;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class RefArrSeqToStringTest
{
  private static final Object MIN_LENGTH_STRING_VAL=new Object(){@Override public String toString(){return "";}};
  private static final int MIN_TOSTRING_LENGTH=String.valueOf(MIN_LENGTH_STRING_VAL).length();
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
    var seq=new UncheckedStack(length+1);
    for(int i=0;i<length;++i)
    {
      seq.add(MIN_LENGTH_STRING_VAL);
    }
    Assertions.assertDoesNotThrow(()->seq.toString());
    seq.add(MIN_LENGTH_STRING_VAL);
    Assertions.assertThrows(OutOfMemoryError.class,()->seq.toString());
  }
  private static Object[] createAscendingSequencePairUncheckedStack(int length)
  {
    OmniCollection.OfRef seq=new UncheckedStack(length);
    ArrayList arrayList=new ArrayList(length);
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
    Object[] seqPair=createAscendingSequencePairUncheckedStack(length);
    ArrayList arrayList=(ArrayList)seqPair[0];
    OmniCollection.OfRef seq=(OmniCollection.OfRef)seqPair[1];
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
    var seq=new UncheckedList(length+1);
    for(int i=0;i<length;++i)
    {
      seq.add(MIN_LENGTH_STRING_VAL);
    }
    Assertions.assertDoesNotThrow(()->seq.toString());
    seq.add(MIN_LENGTH_STRING_VAL);
    Assertions.assertThrows(OutOfMemoryError.class,()->seq.toString());
  }
  private static Object[] createAscendingSequencePairUncheckedList(int length)
  {
    OmniCollection.OfRef seq=new UncheckedList(length);
    ArrayList arrayList=new ArrayList(length);
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
    Object[] seqPair=createAscendingSequencePairUncheckedList(length);
    ArrayList arrayList=(ArrayList)seqPair[0];
    OmniCollection.OfRef seq=(OmniCollection.OfRef)seqPair[1];
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
    var seq=new CheckedStack(length+1);
    for(int i=0;i<length;++i)
    {
      seq.add(MIN_LENGTH_STRING_VAL);
    }
    Assertions.assertDoesNotThrow(()->seq.toString());
    seq.add(MIN_LENGTH_STRING_VAL);
    Assertions.assertThrows(OutOfMemoryError.class,()->seq.toString());
  }
  private static Object[] createAscendingSequencePairCheckedStack(int length)
  {
    OmniCollection.OfRef seq=new CheckedStack(length);
    ArrayList arrayList=new ArrayList(length);
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
    Object[] seqPair=createAscendingSequencePairCheckedStack(length);
    ArrayList arrayList=(ArrayList)seqPair[0];
    OmniCollection.OfRef seq=(OmniCollection.OfRef)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testModificationCheckCheckedStack()
  {
    CheckedStack seq=new CheckedStack();
    Object seqModifyingObject=CheckedCollectionTest.createCollectionModifyingObject(seq);
    for(int i=0;i<10;++i)
    {
      seq.add(seqModifyingObject);
    }
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toString());
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
    var seq=new CheckedList(length+1);
    for(int i=0;i<length;++i)
    {
      seq.add(MIN_LENGTH_STRING_VAL);
    }
    Assertions.assertDoesNotThrow(()->seq.toString());
    seq.add(MIN_LENGTH_STRING_VAL);
    Assertions.assertThrows(OutOfMemoryError.class,()->seq.toString());
  }
  private static Object[] createAscendingSequencePairCheckedList(int length)
  {
    OmniCollection.OfRef seq=new CheckedList(length);
    ArrayList arrayList=new ArrayList(length);
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
    Object[] seqPair=createAscendingSequencePairCheckedList(length);
    ArrayList arrayList=(ArrayList)seqPair[0];
    OmniCollection.OfRef seq=(OmniCollection.OfRef)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testModificationCheckCheckedList()
  {
    CheckedList seq=new CheckedList();
    Object seqModifyingObject=CheckedCollectionTest.createCollectionModifyingObject(seq);
    for(int i=0;i<10;++i)
    {
      seq.add(seqModifyingObject);
    }
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toString());
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
    var root=new UncheckedList(length+1);
    for(int i=0;i<length;++i)
    {
      root.add(MIN_LENGTH_STRING_VAL);
    }
    var seq=root.subList(0,root.size());
    Assertions.assertDoesNotThrow(()->seq.toString());
    seq.add(MIN_LENGTH_STRING_VAL);
    Assertions.assertThrows(OutOfMemoryError.class,()->seq.toString());
  }
  private static Object[] createAscendingSequencePairUncheckedSubList(int length)
  {
    UncheckedList root=new UncheckedList(length);
    OmniCollection.OfRef seq=root.subList(0,0);
    ArrayList arrayList=new ArrayList(length);
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
    Object[] seqPair=createAscendingSequencePairUncheckedSubList(length);
    ArrayList arrayList=(ArrayList)seqPair[0];
    OmniCollection.OfRef seq=(OmniCollection.OfRef)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testEmptyToStringCheckedSubList()
  {
    String expected=new ArrayList().toString();
    CheckedList root=new CheckedList();
    var subList=root.subList(0,0);
    Assertions.assertEquals(expected,subList.toString());
    root.add(null);
    Assertions.assertThrows(ConcurrentModificationException.class,()->subList.toString());
  }
  @Test
  public void testOOMToStringCheckedSubList()
  {
    int length=Integer.MAX_VALUE/(MIN_TOSTRING_LENGTH+2)-1;
    var root=new CheckedList(length+1);
    for(int i=0;i<length;++i)
    {
      root.add(MIN_LENGTH_STRING_VAL);
    }
    var seq=root.subList(0,root.size());
    Assertions.assertDoesNotThrow(()->seq.toString());
    seq.add(MIN_LENGTH_STRING_VAL);
    Assertions.assertThrows(OutOfMemoryError.class,()->seq.toString());
  }
  private static Object[] createAscendingSequencePairCheckedSubList(int length)
  {
    CheckedList root=new CheckedList(length);
    OmniCollection.OfRef seq=root.subList(0,0);
    ArrayList arrayList=new ArrayList(length);
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
    Object[] seqPair=createAscendingSequencePairCheckedSubList(length);
    ArrayList arrayList=(ArrayList)seqPair[0];
    OmniCollection.OfRef seq=(OmniCollection.OfRef)seqPair[1];
    EqualityUtil.parallelAssertStringsAreEqual(arrayList.toString(),seq.toString());
  }
  @Test
  public void testModificationCheckCheckedSubList()
  {
    {
      CheckedList root=new CheckedList();
      var subList=root.subList(0,0);
      Object rootModifyingObject=CheckedCollectionTest.createCollectionModifyingObject(root);
      for(int i=0;i<10;++i)
      {
        subList.add(rootModifyingObject);
      }
      Assertions.assertThrows(ConcurrentModificationException.class,()->subList.toString());
    }
    {
      CheckedList root=new CheckedList();
      var subList=root.subList(0,0);
      Object subListModifyingObject=CheckedCollectionTest.createCollectionModifyingObject(subList);
      for(int i=0;i<10;++i)
      {
        subList.add(subListModifyingObject);
      }
      Assertions.assertThrows(ConcurrentModificationException.class,()->subList.toString());
    }
  }
}
