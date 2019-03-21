package omni.impl.seq;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import omni.api.OmniCollection;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class DoubleArrSeqHashCodeTest
{
  @Test
  public void testEmptyHashCodeArrSeqUncheckedStack()
  {
    var expected=new ArrayList().hashCode();
    Assertions.assertEquals(expected,new DoubleArrSeq.UncheckedStack().hashCode());
  }
  @Test
  public void testHashCodeUncheckedStack()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqUncheckedStack(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfDouble)seqPair[1];
    Assertions.assertEquals(arrayList.hashCode(),seq.hashCode());
  }
  private static Object[] createAscendingSequencePairArrSeqUncheckedStack(int length)
  {
    var seq=new DoubleArrSeq.UncheckedStack(length);
    var arrayList=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      double val=TypeConversionUtil.convertTodouble(i);
      seq.add(val);
      double counterVal=TypeConversionUtil.convertTodouble(length-1-i);
      arrayList.add(counterVal);
    }
    return new Object[]{arrayList,seq};
  }
  @Test
  public void testEmptyHashCodeArrSeqUncheckedList()
  {
    var expected=new ArrayList().hashCode();
    Assertions.assertEquals(expected,new DoubleArrSeq.UncheckedList().hashCode());
  }
  @Test
  public void testHashCodeUncheckedList()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqUncheckedList(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfDouble)seqPair[1];
    Assertions.assertEquals(arrayList.hashCode(),seq.hashCode());
  }
  private static Object[] createAscendingSequencePairArrSeqUncheckedList(int length)
  {
    var seq=new DoubleArrSeq.UncheckedList(length);
    var arrayList=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      double val=TypeConversionUtil.convertTodouble(i);
      seq.add(val);
      arrayList.add(val);
    }
    return new Object[]{arrayList,seq};
  }
  @Test
  public void testEmptyHashCodeArrSeqCheckedStack()
  {
    var expected=new ArrayList().hashCode();
    Assertions.assertEquals(expected,new DoubleArrSeq.CheckedStack().hashCode());
  }
  @Test
  public void testHashCodeCheckedStack()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqCheckedStack(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfDouble)seqPair[1];
    Assertions.assertEquals(arrayList.hashCode(),seq.hashCode());
  }
  private static Object[] createAscendingSequencePairArrSeqCheckedStack(int length)
  {
    var seq=new DoubleArrSeq.CheckedStack(length);
    var arrayList=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      double val=TypeConversionUtil.convertTodouble(i);
      seq.add(val);
      double counterVal=TypeConversionUtil.convertTodouble(length-1-i);
      arrayList.add(counterVal);
    }
    return new Object[]{arrayList,seq};
  }
  @Test
  public void testEmptyHashCodeArrSeqCheckedList()
  {
    var expected=new ArrayList().hashCode();
    Assertions.assertEquals(expected,new DoubleArrSeq.CheckedList().hashCode());
  }
  @Test
  public void testHashCodeCheckedList()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqCheckedList(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfDouble)seqPair[1];
    Assertions.assertEquals(arrayList.hashCode(),seq.hashCode());
  }
  private static Object[] createAscendingSequencePairArrSeqCheckedList(int length)
  {
    var seq=new DoubleArrSeq.CheckedList(length);
    var arrayList=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      double val=TypeConversionUtil.convertTodouble(i);
      seq.add(val);
      arrayList.add(val);
    }
    return new Object[]{arrayList,seq};
  }
  @Test
  public void testEmptyHashCodeArrSeqUncheckedSubList()
  {
    var expected=new ArrayList().hashCode();
    Assertions.assertEquals(expected,new DoubleArrSeq.UncheckedList().subList(0,0).hashCode());
  }
  @Test
  public void testHashCodeUncheckedSubList()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqUncheckedSubList(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfDouble)seqPair[1];
    Assertions.assertEquals(arrayList.hashCode(),seq.hashCode());
  }
  private static Object[] createAscendingSequencePairArrSeqUncheckedSubList(int length)
  {
    var root=new DoubleArrSeq.UncheckedList(length);
    var seq=root.subList(0,0);
    var arrayList=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      double val=TypeConversionUtil.convertTodouble(i);
      seq.add(val);
      arrayList.add(val);
    }
    return new Object[]{arrayList,seq};
  }
  @Test
  public void testEmptyHashCodeArrSeqCheckedSubList()
  {
    var expected=new ArrayList().hashCode();
    var root=new DoubleArrSeq.CheckedList();
    var subList=root.subList(0,0);
    Assertions.assertEquals(expected,subList.hashCode());
    root.add(Double.NaN);
    Assertions.assertThrows(ConcurrentModificationException.class,()->subList.hashCode());
  }
  @Test
  public void testHashCodeCheckedSubList()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqCheckedSubList(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfDouble)seqPair[1];
    Assertions.assertEquals(arrayList.hashCode(),seq.hashCode());
  }
  private static Object[] createAscendingSequencePairArrSeqCheckedSubList(int length)
  {
    var root=new DoubleArrSeq.CheckedList(length);
    var seq=root.subList(0,0);
    var arrayList=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      double val=TypeConversionUtil.convertTodouble(i);
      seq.add(val);
      arrayList.add(val);
    }
    return new Object[]{arrayList,seq};
  }
}
