package omni.impl.seq;
import omni.util.TypeConversionUtil;
import omni.impl.CheckedCollectionTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import omni.api.OmniCollection;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class RefArrSeqHashCodeTest
{
  @Test
  public void testEmptyHashCodeArrSeqUncheckedStack()
  {
    var expected=new ArrayList().hashCode();
    Assertions.assertEquals(expected,new RefArrSeq.UncheckedStack().hashCode());
  }
  @Test
  public void testHashCodeUncheckedStack()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqUncheckedStack(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfRef)seqPair[1];
    Assertions.assertEquals(arrayList.hashCode(),seq.hashCode());
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
  public void testEmptyHashCodeArrSeqUncheckedList()
  {
    var expected=new ArrayList().hashCode();
    Assertions.assertEquals(expected,new RefArrSeq.UncheckedList().hashCode());
  }
  @Test
  public void testHashCodeUncheckedList()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqUncheckedList(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfRef)seqPair[1];
    Assertions.assertEquals(arrayList.hashCode(),seq.hashCode());
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
  public void testEmptyHashCodeArrSeqCheckedStack()
  {
    var expected=new ArrayList().hashCode();
    Assertions.assertEquals(expected,new RefArrSeq.CheckedStack().hashCode());
  }
  @Test
  public void testHashCodeCheckedStack()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqCheckedStack(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfRef)seqPair[1];
    Assertions.assertEquals(arrayList.hashCode(),seq.hashCode());
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
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.hashCode());
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
  public void testEmptyHashCodeArrSeqCheckedList()
  {
    var expected=new ArrayList().hashCode();
    Assertions.assertEquals(expected,new RefArrSeq.CheckedList().hashCode());
  }
  @Test
  public void testHashCodeCheckedList()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqCheckedList(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfRef)seqPair[1];
    Assertions.assertEquals(arrayList.hashCode(),seq.hashCode());
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
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.hashCode());
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
  public void testEmptyHashCodeArrSeqUncheckedSubList()
  {
    var expected=new ArrayList().hashCode();
    Assertions.assertEquals(expected,new RefArrSeq.UncheckedList().subList(0,0).hashCode());
  }
  @Test
  public void testHashCodeUncheckedSubList()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqUncheckedSubList(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfRef)seqPair[1];
    Assertions.assertEquals(arrayList.hashCode(),seq.hashCode());
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
  public void testEmptyHashCodeArrSeqCheckedSubList()
  {
    var expected=new ArrayList().hashCode();
    var root=new RefArrSeq.CheckedList();
    var subList=root.subList(0,0);
    Assertions.assertEquals(expected,subList.hashCode());
    root.add(null);
    Assertions.assertThrows(ConcurrentModificationException.class,()->subList.hashCode());
  }
  @Test
  public void testHashCodeCheckedSubList()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqCheckedSubList(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfRef)seqPair[1];
    Assertions.assertEquals(arrayList.hashCode(),seq.hashCode());
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
      Assertions.assertThrows(ConcurrentModificationException.class,()->subList.hashCode());
    }
    {
      var root=new RefArrSeq.CheckedList();
      var subList=root.subList(0,0);
      var subListModifyingObject=CheckedCollectionTest.createCollectionModifyingObject(subList);
      for(int i=0;i<10;++i)
      {
        subList.add(subListModifyingObject);
      }
      Assertions.assertThrows(ConcurrentModificationException.class,()->subList.hashCode());
    }
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
}
