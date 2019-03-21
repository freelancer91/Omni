package omni.impl.seq;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import omni.api.OmniCollection;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class ShortArrSeqHashCodeTest
{
//TODO place sanity checks for checked sequence modification behavior
  @Test
  public void testEmptyHashCodeArrSeqUncheckedStack()
  {
    var expected=new ArrayList().hashCode();
    Assertions.assertEquals(expected,new ShortArrSeq.UncheckedStack().hashCode());
  }
  @Test
  public void testHashCodeUncheckedStack()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqUncheckedStack(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfShort)seqPair[1];
    Assertions.assertEquals(arrayList.hashCode(),seq.hashCode());
  }
  private static Object[] createAscendingSequencePairArrSeqUncheckedStack(int length)
  {
    var seq=new ShortArrSeq.UncheckedStack(length);
    var arrayList=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      short val=TypeConversionUtil.convertToshort(i);
      seq.add(val);
      short counterVal=TypeConversionUtil.convertToshort(length-1-i);
      arrayList.add(counterVal);
    }
    return new Object[]{arrayList,seq};
  }
  @Test
  public void testEmptyHashCodeArrSeqUncheckedList()
  {
    var expected=new ArrayList().hashCode();
    Assertions.assertEquals(expected,new ShortArrSeq.UncheckedList().hashCode());
  }
  @Test
  public void testHashCodeUncheckedList()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqUncheckedList(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfShort)seqPair[1];
    Assertions.assertEquals(arrayList.hashCode(),seq.hashCode());
  }
  private static Object[] createAscendingSequencePairArrSeqUncheckedList(int length)
  {
    var seq=new ShortArrSeq.UncheckedList(length);
    var arrayList=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      short val=TypeConversionUtil.convertToshort(i);
      seq.add(val);
      arrayList.add(val);
    }
    return new Object[]{arrayList,seq};
  }
  @Test
  public void testEmptyHashCodeArrSeqCheckedStack()
  {
    var expected=new ArrayList().hashCode();
    Assertions.assertEquals(expected,new ShortArrSeq.CheckedStack().hashCode());
  }
  @Test
  public void testHashCodeCheckedStack()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqCheckedStack(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfShort)seqPair[1];
    Assertions.assertEquals(arrayList.hashCode(),seq.hashCode());
  }
  private static Object[] createAscendingSequencePairArrSeqCheckedStack(int length)
  {
    var seq=new ShortArrSeq.CheckedStack(length);
    var arrayList=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      short val=TypeConversionUtil.convertToshort(i);
      seq.add(val);
      short counterVal=TypeConversionUtil.convertToshort(length-1-i);
      arrayList.add(counterVal);
    }
    return new Object[]{arrayList,seq};
  }
  @Test
  public void testEmptyHashCodeArrSeqCheckedList()
  {
    var expected=new ArrayList().hashCode();
    Assertions.assertEquals(expected,new ShortArrSeq.CheckedList().hashCode());
  }
  @Test
  public void testHashCodeCheckedList()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqCheckedList(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfShort)seqPair[1];
    Assertions.assertEquals(arrayList.hashCode(),seq.hashCode());
  }
  private static Object[] createAscendingSequencePairArrSeqCheckedList(int length)
  {
    var seq=new ShortArrSeq.CheckedList(length);
    var arrayList=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      short val=TypeConversionUtil.convertToshort(i);
      seq.add(val);
      arrayList.add(val);
    }
    return new Object[]{arrayList,seq};
  }
  @Test
  public void testEmptyHashCodeArrSeqUncheckedSubList()
  {
    var expected=new ArrayList().hashCode();
    Assertions.assertEquals(expected,new ShortArrSeq.UncheckedList().subList(0,0).hashCode());
  }
  @Test
  public void testHashCodeUncheckedSubList()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqUncheckedSubList(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfShort)seqPair[1];
    Assertions.assertEquals(arrayList.hashCode(),seq.hashCode());
  }
  private static Object[] createAscendingSequencePairArrSeqUncheckedSubList(int length)
  {
    var root=new ShortArrSeq.UncheckedList(length);
    var seq=root.subList(0,0);
    var arrayList=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      short val=TypeConversionUtil.convertToshort(i);
      seq.add(val);
      arrayList.add(val);
    }
    return new Object[]{arrayList,seq};
  }
  @Test
  public void testEmptyHashCodeArrSeqCheckedSubList()
  {
    var expected=new ArrayList().hashCode();
    var root=new ShortArrSeq.CheckedList();
    var subList=root.subList(0,0);
    Assertions.assertEquals(expected,subList.hashCode());
    root.add(Short.MIN_VALUE);
    Assertions.assertThrows(ConcurrentModificationException.class,()->subList.hashCode());
  }
  @Test
  public void testHashCodeCheckedSubList()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqCheckedSubList(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfShort)seqPair[1];
    Assertions.assertEquals(arrayList.hashCode(),seq.hashCode());
  }
  private static Object[] createAscendingSequencePairArrSeqCheckedSubList(int length)
  {
    var root=new ShortArrSeq.CheckedList(length);
    var seq=root.subList(0,0);
    var arrayList=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      short val=TypeConversionUtil.convertToshort(i);
      seq.add(val);
      arrayList.add(val);
    }
    return new Object[]{arrayList,seq};
  }
}
