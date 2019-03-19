package omni.impl.seq;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import omni.api.OmniCollection;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class FloatSeqHashCodeTest
{
  @Test
  public void testEmptyHashCodeArrSeqUncheckedStack()
  {
    var expected=new ArrayList().hashCode();
    Assertions.assertEquals(expected,new FloatArrSeq.UncheckedStack().hashCode());
  }
  @Test
  public void testHashCodeUncheckedStack()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqUncheckedStack(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfFloat)seqPair[1];
    Assertions.assertEquals(arrayList.hashCode(),seq.hashCode());
  }
  private static Object[] createAscendingSequencePairArrSeqUncheckedStack(int length)
  {
    var seq=new FloatArrSeq.UncheckedStack(length);
    var arrayList=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      float val=TypeConversionUtil.convertTofloat(i);
      seq.add(val);
      float counterVal=TypeConversionUtil.convertTofloat(length-1-i);
      arrayList.add(counterVal);
    }
    return new Object[]{arrayList,seq};
  }
  @Test
  public void testEmptyHashCodeArrSeqUncheckedList()
  {
    var expected=new ArrayList().hashCode();
    Assertions.assertEquals(expected,new FloatArrSeq.UncheckedList().hashCode());
  }
  @Test
  public void testHashCodeUncheckedList()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqUncheckedList(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfFloat)seqPair[1];
    Assertions.assertEquals(arrayList.hashCode(),seq.hashCode());
  }
  private static Object[] createAscendingSequencePairArrSeqUncheckedList(int length)
  {
    var seq=new FloatArrSeq.UncheckedList(length);
    var arrayList=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      float val=TypeConversionUtil.convertTofloat(i);
      seq.add(val);
      arrayList.add(val);
    }
    return new Object[]{arrayList,seq};
  }
  @Test
  public void testEmptyHashCodeArrSeqCheckedStack()
  {
    var expected=new ArrayList().hashCode();
    Assertions.assertEquals(expected,new FloatArrSeq.CheckedStack().hashCode());
  }
  @Test
  public void testHashCodeCheckedStack()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqCheckedStack(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfFloat)seqPair[1];
    Assertions.assertEquals(arrayList.hashCode(),seq.hashCode());
  }
  private static Object[] createAscendingSequencePairArrSeqCheckedStack(int length)
  {
    var seq=new FloatArrSeq.CheckedStack(length);
    var arrayList=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      float val=TypeConversionUtil.convertTofloat(i);
      seq.add(val);
      float counterVal=TypeConversionUtil.convertTofloat(length-1-i);
      arrayList.add(counterVal);
    }
    return new Object[]{arrayList,seq};
  }
  @Test
  public void testEmptyHashCodeArrSeqCheckedList()
  {
    var expected=new ArrayList().hashCode();
    Assertions.assertEquals(expected,new FloatArrSeq.CheckedList().hashCode());
  }
  @Test
  public void testHashCodeCheckedList()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqCheckedList(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfFloat)seqPair[1];
    Assertions.assertEquals(arrayList.hashCode(),seq.hashCode());
  }
  private static Object[] createAscendingSequencePairArrSeqCheckedList(int length)
  {
    var seq=new FloatArrSeq.CheckedList(length);
    var arrayList=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      float val=TypeConversionUtil.convertTofloat(i);
      seq.add(val);
      arrayList.add(val);
    }
    return new Object[]{arrayList,seq};
  }
  @Test
  public void testEmptyHashCodeArrSeqUncheckedSubList()
  {
    var expected=new ArrayList().hashCode();
    Assertions.assertEquals(expected,new FloatArrSeq.UncheckedList().subList(0,0).hashCode());
  }
  @Test
  public void testHashCodeUncheckedSubList()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqUncheckedSubList(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfFloat)seqPair[1];
    Assertions.assertEquals(arrayList.hashCode(),seq.hashCode());
  }
  private static Object[] createAscendingSequencePairArrSeqUncheckedSubList(int length)
  {
    var root=new FloatArrSeq.UncheckedList(length);
    var seq=root.subList(0,0);
    var arrayList=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      float val=TypeConversionUtil.convertTofloat(i);
      seq.add(val);
      arrayList.add(val);
    }
    return new Object[]{arrayList,seq};
  }
  @Test
  public void testEmptyHashCodeArrSeqCheckedSubList()
  {
    var expected=new ArrayList().hashCode();
    var root=new FloatArrSeq.CheckedList();
    var subList=root.subList(0,0);
    Assertions.assertEquals(expected,subList.hashCode());
    root.add(Float.NaN);
    Assertions.assertThrows(ConcurrentModificationException.class,()->subList.hashCode());
  }
  @Test
  public void testHashCodeCheckedSubList()
  {
    int length=100;
    var seqPair=createAscendingSequencePairArrSeqCheckedSubList(length);
    var arrayList=(ArrayList)seqPair[0];
    var seq=(OmniCollection.OfFloat)seqPair[1];
    Assertions.assertEquals(arrayList.hashCode(),seq.hashCode());
  }
  private static Object[] createAscendingSequencePairArrSeqCheckedSubList(int length)
  {
    var root=new FloatArrSeq.CheckedList(length);
    var seq=root.subList(0,0);
    var arrayList=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      float val=TypeConversionUtil.convertTofloat(i);
      seq.add(val);
      arrayList.add(val);
    }
    return new Object[]{arrayList,seq};
  }
}
