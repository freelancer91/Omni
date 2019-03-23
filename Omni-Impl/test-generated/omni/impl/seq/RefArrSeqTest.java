package omni.impl.seq;
import java.util.ArrayList;
import omni.impl.seq.RefArrSeq.UncheckedList;
import omni.impl.seq.RefArrSeq.CheckedList;
import omni.impl.seq.RefArrSeq.UncheckedStack;
import omni.impl.seq.RefArrSeq.CheckedStack;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.function.IntFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.ConcurrentModificationException;
import omni.util.EqualityUtil;
import omni.util.OmniArray;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class RefArrSeqTest
{
  @Test
  public void testUncheckedStackconstructor_void_initialCapacityDEFAULT()
  {
    UncheckedStack seq=new UncheckedStack();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
  }
  @Test
  public void testUncheckedStackconstructor_intObject_initialCapacityNULL()
  {
    UncheckedStack seq=new UncheckedStack(0,null);
    Assertions.assertEquals(0,seq.size);
    Assertions.assertNull(seq.arr);
  }
  @Test
  public void testUncheckedStackconstructor_int_initialCapacity50()
  {
    UncheckedStack seq=new UncheckedStack(50);
    Assertions.assertEquals(0,seq.size);
    switch(50)
    {
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(50,seq.arr.length);
      for(int i=0;i<50;++i)
      {
        Assertions.assertNull(seq.arr[i]);
      }
    }
  }
  @Test
  public void testUncheckedStackconstructor_int_initialCapacity0()
  {
    UncheckedStack seq=new UncheckedStack(0);
    Assertions.assertEquals(0,seq.size);
    switch(0)
    {
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(0,seq.arr.length);
      for(int i=0;i<0;++i)
      {
        Assertions.assertNull(seq.arr[i]);
      }
    }
  }
  @Test
  public void testUncheckedStackconstructor_int_initialCapacity10()
  {
    UncheckedStack seq=new UncheckedStack(10);
    Assertions.assertEquals(0,seq.size);
    switch(10)
    {
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(10,seq.arr.length);
      for(int i=0;i<10;++i)
      {
        Assertions.assertNull(seq.arr[i]);
      }
    }
  }
  @Test
  public void testUncheckedStacktoArray_ObjectArray_zeroLengthArrayAndSequenceIsEmpty()
  {
    UncheckedStack seq=new UncheckedStack();
    Integer[] paramArr=new Integer[0];
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
  }
  @Test
  public void testUncheckedStacktoArray_ObjectArray_zeroLengthArrayAndSequenceNotEmpty()
  {
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Integer[] paramArr=new Integer[0];
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertNotSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<100;++i)
    {
      Assertions.assertSame(itr.next(),result[i]);
    }
  }
  @Test
  public void testUncheckedStacktoArray_ObjectArray_nonzeroLengthArrayAndSequenceIsEmpty()
  {
    UncheckedStack seq=new UncheckedStack();
    Integer[] paramArr=new Integer[5];
    for(int i=0;i<paramArr.length;++i)
    {
      paramArr[i]=TypeConversionUtil.convertToObject(paramArr.length);
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(5,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    Assertions.assertNull(result[0]);
    for(int i=1;i<result.length;++i)
    {
      Assertions.assertEquals((Object)TypeConversionUtil.convertToObject(paramArr.length),result[i]);
    }
  }
  @Test
  public void testUncheckedStacktoArray_ObjectArray_overSizedArray()
  {
    UncheckedStack seq=new UncheckedStack();
    Integer[] paramArr=new Integer[10];
    for(int i=0;i<paramArr.length;++i)
    {
      paramArr[i]=TypeConversionUtil.convertToObject(paramArr.length);
    }
    for(int i=0;i<5;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(5,seq.size());
    Assertions.assertEquals(10,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<5;++i)
    {
      Assertions.assertSame(itr.next(),result[i]);
    }
    Assertions.assertNull(result[5]);
    for(int i=6;i<result.length;++i)
    {
      Assertions.assertEquals((Object)TypeConversionUtil.convertToObject(paramArr.length),result[i]);
    }
  }
  @Test
  public void testUncheckedStacktoArray_ObjectArray_undersizedArray()
  {
    UncheckedStack seq=new UncheckedStack();
    Integer[] paramArr=new Integer[5];
    for(int i=0;i<paramArr.length;++i)
    {
      paramArr[i]=TypeConversionUtil.convertToObject(10);
    }
    for(int i=0;i<10;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(10,seq.size());
    Assertions.assertEquals(10,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertNotSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<10;++i)
    {
      Assertions.assertSame(itr.next(),result[i]);
    }
  }
  @Test
  public void testUncheckedStacktoArray_ObjectArray_exactSizeArray()
  {
    UncheckedStack seq=new UncheckedStack();
    Integer[] paramArr=new Integer[5];
    for(int i=0;i<paramArr.length;++i)
    {
      paramArr[i]=TypeConversionUtil.convertToObject(5);
    }
    for(int i=0;i<5;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(5,seq.size());
    Assertions.assertEquals(5,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<5;++i)
    {
      Assertions.assertSame(itr.next(),result[i]);
    }
  }
  @Test
  public void testUncheckedStacktoArray_IntFunction_seqIsEmpty_nonMod()
  {
    UncheckedStack seq=new UncheckedStack();
    IntFunction<Integer[]> arrConstructor=Integer[]::new;
    var result=seq.toArray(arrConstructor);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertEquals(0,result.length);
  }
  @Test
  public void testUncheckedStacktoArray_IntFunction_seqIsNotEmpty_nonMod()
  {
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    IntFunction<Integer[]> arrConstructor=Integer[]::new;
    var result=seq.toArray(arrConstructor);
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i)
    {
      Assertions.assertSame(itr.next(),result[i]);
    }
  }
  @Test
  public void testUncheckedStackclear_void_seqIsEmpty()
  {
    UncheckedStack seq=new UncheckedStack();
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testUncheckedStackclear_void_seqIsNotEmpty()
  {
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
    for(int i=0;i<100;++i)
    {
      Assertions.assertNull(seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackisEmpty_void_seqIsEmpty()
  {
    UncheckedStack seq=new UncheckedStack();
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testUncheckedStackisEmpty_void_seqIsNotEmpty()
  {
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertFalse(seq.isEmpty());
  }
  @Test
  public void testUncheckedStackisEmpty_void_seqIsBeingCleared()
  {
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;)
    {
      Assertions.assertFalse(seq.isEmpty());
      itr.next();
      itr.remove();
    }
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testUncheckedStacksize_void_seqIsEmpty()
  {
    UncheckedStack seq=new UncheckedStack();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
  }
  @Test
  public void testUncheckedStacksize_void_seqIsNotEmpty()
  {
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
  }
  @Test
  public void testUncheckedStacksize_void_seqIsBeingCleared()
  {
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;)
    {
      itr.next();
      itr.remove();
      Assertions.assertEquals(i,seq.size());
    }
  }
  @Test
  public void testUncheckedStackforEach_Consumer_SeqIsEmpty_NoMod()
  {
    UncheckedStack seq=new UncheckedStack();
    MonitoredConsumer consumer=new MonitoredConsumer();
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testUncheckedStackforEach_Consumer_SeqIsNotEmpty_NoMod()
  {
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    MonitoredConsumer consumer=new MonitoredConsumer();
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,consumer.size());
    var seqIterator=seq.iterator();
    var consumerIterator=consumer.iterator();
    for(int i=0;i<100;++i)
    {
      Assertions.assertSame(consumerIterator.next(),seqIterator.next());
    }
  }
  //TODO TestRemoveIfMethods<DEFAULT>(,)
  //TODO TestRemoveIfMethods<NULL>(,)
  //TODO TestRemoveIfMethods<50>(,)
  @Test
  public void testUncheckedStackadd_Object_initialCapacityDEFAULT()
  {
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_Object_initialCapacityNULL()
  {
    UncheckedStack seq=new UncheckedStack(0,null);
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_Object_initialCapacity50()
  {
    UncheckedStack seq=new UncheckedStack(50);
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStacktoArray_void_seqIsEmpty()
  {
    UncheckedStack seq=new UncheckedStack();
    Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.toArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStacktoArray_void_seqIsNotEmpty()
  {
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var result=seq.toArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i)
    {
      Assertions.assertSame(itr.next(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testUncheckedListconstructor_void_initialCapacityDEFAULT()
  {
    UncheckedList seq=new UncheckedList();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
  }
  @Test
  public void testUncheckedListconstructor_intObject_initialCapacityNULL()
  {
    UncheckedList seq=new UncheckedList(0,null);
    Assertions.assertEquals(0,seq.size);
    Assertions.assertNull(seq.arr);
  }
  @Test
  public void testUncheckedListconstructor_int_initialCapacity50()
  {
    UncheckedList seq=new UncheckedList(50);
    Assertions.assertEquals(0,seq.size);
    switch(50)
    {
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(50,seq.arr.length);
      for(int i=0;i<50;++i)
      {
        Assertions.assertNull(seq.arr[i]);
      }
    }
  }
  @Test
  public void testUncheckedListconstructor_int_initialCapacity0()
  {
    UncheckedList seq=new UncheckedList(0);
    Assertions.assertEquals(0,seq.size);
    switch(0)
    {
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(0,seq.arr.length);
      for(int i=0;i<0;++i)
      {
        Assertions.assertNull(seq.arr[i]);
      }
    }
  }
  @Test
  public void testUncheckedListconstructor_int_initialCapacity10()
  {
    UncheckedList seq=new UncheckedList(10);
    Assertions.assertEquals(0,seq.size);
    switch(10)
    {
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(10,seq.arr.length);
      for(int i=0;i<10;++i)
      {
        Assertions.assertNull(seq.arr[i]);
      }
    }
  }
  @Test
  public void testUncheckedListtoArray_ObjectArray_zeroLengthArrayAndSequenceIsEmpty()
  {
    UncheckedList seq=new UncheckedList();
    Integer[] paramArr=new Integer[0];
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
  }
  @Test
  public void testUncheckedListtoArray_ObjectArray_zeroLengthArrayAndSequenceNotEmpty()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Integer[] paramArr=new Integer[0];
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertNotSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<100;++i)
    {
      Assertions.assertSame(itr.next(),result[i]);
    }
  }
  @Test
  public void testUncheckedListtoArray_ObjectArray_nonzeroLengthArrayAndSequenceIsEmpty()
  {
    UncheckedList seq=new UncheckedList();
    Integer[] paramArr=new Integer[5];
    for(int i=0;i<paramArr.length;++i)
    {
      paramArr[i]=TypeConversionUtil.convertToObject(paramArr.length);
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(5,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    Assertions.assertNull(result[0]);
    for(int i=1;i<result.length;++i)
    {
      Assertions.assertEquals((Object)TypeConversionUtil.convertToObject(paramArr.length),result[i]);
    }
  }
  @Test
  public void testUncheckedListtoArray_ObjectArray_overSizedArray()
  {
    UncheckedList seq=new UncheckedList();
    Integer[] paramArr=new Integer[10];
    for(int i=0;i<paramArr.length;++i)
    {
      paramArr[i]=TypeConversionUtil.convertToObject(paramArr.length);
    }
    for(int i=0;i<5;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(5,seq.size());
    Assertions.assertEquals(10,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<5;++i)
    {
      Assertions.assertSame(itr.next(),result[i]);
    }
    Assertions.assertNull(result[5]);
    for(int i=6;i<result.length;++i)
    {
      Assertions.assertEquals((Object)TypeConversionUtil.convertToObject(paramArr.length),result[i]);
    }
  }
  @Test
  public void testUncheckedListtoArray_ObjectArray_undersizedArray()
  {
    UncheckedList seq=new UncheckedList();
    Integer[] paramArr=new Integer[5];
    for(int i=0;i<paramArr.length;++i)
    {
      paramArr[i]=TypeConversionUtil.convertToObject(10);
    }
    for(int i=0;i<10;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(10,seq.size());
    Assertions.assertEquals(10,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertNotSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<10;++i)
    {
      Assertions.assertSame(itr.next(),result[i]);
    }
  }
  @Test
  public void testUncheckedListtoArray_ObjectArray_exactSizeArray()
  {
    UncheckedList seq=new UncheckedList();
    Integer[] paramArr=new Integer[5];
    for(int i=0;i<paramArr.length;++i)
    {
      paramArr[i]=TypeConversionUtil.convertToObject(5);
    }
    for(int i=0;i<5;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(5,seq.size());
    Assertions.assertEquals(5,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<5;++i)
    {
      Assertions.assertSame(itr.next(),result[i]);
    }
  }
  @Test
  public void testUncheckedListtoArray_IntFunction_seqIsEmpty_nonMod()
  {
    UncheckedList seq=new UncheckedList();
    IntFunction<Integer[]> arrConstructor=Integer[]::new;
    var result=seq.toArray(arrConstructor);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertEquals(0,result.length);
  }
  @Test
  public void testUncheckedListtoArray_IntFunction_seqIsNotEmpty_nonMod()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    IntFunction<Integer[]> arrConstructor=Integer[]::new;
    var result=seq.toArray(arrConstructor);
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i)
    {
      Assertions.assertSame(itr.next(),result[i]);
    }
  }
  @Test
  public void testUncheckedListclear_void_seqIsEmpty()
  {
    UncheckedList seq=new UncheckedList();
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testUncheckedListclear_void_seqIsNotEmpty()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
    for(int i=0;i<100;++i)
    {
      Assertions.assertNull(seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListisEmpty_void_seqIsEmpty()
  {
    UncheckedList seq=new UncheckedList();
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testUncheckedListisEmpty_void_seqIsNotEmpty()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertFalse(seq.isEmpty());
  }
  @Test
  public void testUncheckedListisEmpty_void_seqIsBeingCleared()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;)
    {
      Assertions.assertFalse(seq.isEmpty());
      itr.next();
      itr.remove();
    }
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testUncheckedListsize_void_seqIsEmpty()
  {
    UncheckedList seq=new UncheckedList();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
  }
  @Test
  public void testUncheckedListsize_void_seqIsNotEmpty()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
  }
  @Test
  public void testUncheckedListsize_void_seqIsBeingCleared()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;)
    {
      itr.next();
      itr.remove();
      Assertions.assertEquals(i,seq.size());
    }
  }
  @Test
  public void testUncheckedListforEach_Consumer_SeqIsEmpty_NoMod()
  {
    UncheckedList seq=new UncheckedList();
    MonitoredConsumer consumer=new MonitoredConsumer();
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testUncheckedListforEach_Consumer_SeqIsNotEmpty_NoMod()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    MonitoredConsumer consumer=new MonitoredConsumer();
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,consumer.size());
    var seqIterator=seq.iterator();
    var consumerIterator=consumer.iterator();
    for(int i=0;i<100;++i)
    {
      Assertions.assertSame(consumerIterator.next(),seqIterator.next());
    }
  }
  //TODO TestRemoveIfMethods<DEFAULT>(,)
  //TODO TestRemoveIfMethods<NULL>(,)
  //TODO TestRemoveIfMethods<50>(,)
  @Test
  public void testUncheckedListadd_Object_initialCapacityDEFAULT()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_Object_initialCapacityNULL()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_Object_initialCapacity50()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListtoArray_void_seqIsEmpty()
  {
    UncheckedList seq=new UncheckedList();
    Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.toArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedListtoArray_void_seqIsNotEmpty()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var result=seq.toArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i)
    {
      Assertions.assertSame(itr.next(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  static class ModifyingCheckedStackConsumer extends MonitoredConsumer
  {
    private static final long serialVersionUID=1L;
    CheckedStack seq;
    public ModifyingCheckedStackConsumer(CheckedStack seq)
    {
      this.seq=seq;
    }
    @Override public void accept(Object val)
    {
      seq.modCount+=2;
      super.accept((Object)val);
    }
  }
  static class ModifiyingCheckedStackAndThrowingConsumer extends ModifyingCheckedStackConsumer
  {
    private static final long serialVersionUID=1L;
    public ModifiyingCheckedStackAndThrowingConsumer(CheckedStack seq)
    {
      super(seq);
    }
    @Override public void accept(Object val)
    {
      super.accept((Object)val);
      throw new IndexOutOfBoundsException();
    }
  }
  static class ModifyingAndThrowingCheckedStackPredicate extends ThrowingPredicate
  {
      CheckedStack seq;
      public ModifyingAndThrowingCheckedStackPredicate(CheckedStack seq)
      {
        this.seq=seq;
      }
      @Override boolean testImpl(Object val)
      {
        seq.add(val);
        throw new IndexOutOfBoundsException();
      }
  }
  static class RemoveAllCheckedStackModifyingPredicate extends RemoveAllPredicate
  {
      CheckedStack seq;
      RemoveAllCheckedStackModifyingPredicate(CheckedStack seq)
      {
        this.seq=seq;
      }
      @Override
      public boolean test(Object val)
      {
        seq.modCount+=2;
        return super.test(val);
      }
  }
  static class RemoveNoneCheckedStackModifyingPredicate extends RemoveNonePredicate
  {
      CheckedStack seq;
      RemoveNoneCheckedStackModifyingPredicate(CheckedStack seq)
      {
        this.seq=seq;
      }
      @Override
      public boolean test(Object val)
      {
        seq.modCount+=2;
        return super.test(val);
      }
  }
    static class RetainSecondCheckedStackModifyingPredicate extends RetainSecondPredicate
    {
      CheckedStack seq;
      RetainSecondCheckedStackModifyingPredicate(CheckedStack seq)
      {
        this.seq=seq;
      }
      @Override
      public boolean test(Object val)
      {
        seq.modCount+=2;
        return super.test(val);
      }
    }
    static class RetainSecondAndLastCheckedStackModifyingPredicate extends RetainSecondAndLastPredicate
    {
      CheckedStack seq;
      RetainSecondAndLastCheckedStackModifyingPredicate(CheckedStack seq)
      {
        super(seq.size);
        this.seq=seq;
      }
      @Override
      public boolean test(Object val)
      {
        seq.modCount+=2;
        return super.test(val);
      }
    }
    static class RemoveFirstAndThirdCheckedStackModifyingPredicate extends RemoveFirstAndThirdPredicate
    {
      CheckedStack seq;
      RemoveFirstAndThirdCheckedStackModifyingPredicate(CheckedStack seq)
      {
        this.seq=seq;
      }
      @Override
      public boolean test(Object val)
      {
        seq.modCount+=2;
        return super.test(val);
      }
    }
    static class RemoveFirstCheckedStackModifyingPredicate  extends RemoveFirstPredicate
    {
      CheckedStack seq;
      RemoveFirstCheckedStackModifyingPredicate(CheckedStack seq)
      {
        this.seq=seq;
      }
      @Override
      public boolean test(Object val)
      {
        seq.modCount+=2;
        return super.test(val);
      }
    }
    static class RemoveFirstAndSecondToLastCheckedStackModifyingPredicate extends RemoveFirstAndSecondToLastPredicate
    {
      CheckedStack seq;
      RemoveFirstAndSecondToLastCheckedStackModifyingPredicate(CheckedStack seq)
      {
        super(seq.size);
        this.seq=seq;
      }
      @Override
      public boolean test(Object val)
      {
        seq.modCount+=2;
        return super.test(val);
      }
    }
  @Test
  public void testCheckedStackconstructor_void_initialCapacityDEFAULT()
  {
    CheckedStack seq=new CheckedStack();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackconstructor_intObject_initialCapacityNULL()
  {
    CheckedStack seq=new CheckedStack(0,null);
    Assertions.assertEquals(0,seq.size);
    Assertions.assertNull(seq.arr);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackconstructor_int_initialCapacity50()
  {
    CheckedStack seq=new CheckedStack(50);
    Assertions.assertEquals(0,seq.size);
    switch(50)
    {
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(50,seq.arr.length);
      for(int i=0;i<50;++i)
      {
        Assertions.assertNull(seq.arr[i]);
      }
    }
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackconstructor_int_initialCapacity0()
  {
    CheckedStack seq=new CheckedStack(0);
    Assertions.assertEquals(0,seq.size);
    switch(0)
    {
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(0,seq.arr.length);
      for(int i=0;i<0;++i)
      {
        Assertions.assertNull(seq.arr[i]);
      }
    }
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackconstructor_int_initialCapacity10()
  {
    CheckedStack seq=new CheckedStack(10);
    Assertions.assertEquals(0,seq.size);
    switch(10)
    {
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(10,seq.arr.length);
      for(int i=0;i<10;++i)
      {
        Assertions.assertNull(seq.arr[i]);
      }
    }
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStacktoArray_ObjectArray_zeroLengthArrayAndSequenceIsEmpty()
  {
    CheckedStack seq=new CheckedStack();
    Integer[] paramArr=new Integer[0];
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertEquals(0,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
  }
  @Test
  public void testCheckedStacktoArray_ObjectArray_zeroLengthArrayAndSequenceNotEmpty()
  {
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Integer[] paramArr=new Integer[0];
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(100,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertNotSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<100;++i)
    {
      Assertions.assertSame(itr.next(),result[i]);
    }
  }
  @Test
  public void testCheckedStacktoArray_ObjectArray_nonzeroLengthArrayAndSequenceIsEmpty()
  {
    CheckedStack seq=new CheckedStack();
    Integer[] paramArr=new Integer[5];
    for(int i=0;i<paramArr.length;++i)
    {
      paramArr[i]=TypeConversionUtil.convertToObject(paramArr.length);
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertEquals(5,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    Assertions.assertNull(result[0]);
    for(int i=1;i<result.length;++i)
    {
      Assertions.assertEquals((Object)TypeConversionUtil.convertToObject(paramArr.length),result[i]);
    }
  }
  @Test
  public void testCheckedStacktoArray_ObjectArray_overSizedArray()
  {
    CheckedStack seq=new CheckedStack();
    Integer[] paramArr=new Integer[10];
    for(int i=0;i<paramArr.length;++i)
    {
      paramArr[i]=TypeConversionUtil.convertToObject(paramArr.length);
    }
    for(int i=0;i<5;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(5,seq.size());
    Assertions.assertEquals(5,seq.modCount);
    Assertions.assertEquals(10,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<5;++i)
    {
      Assertions.assertSame(itr.next(),result[i]);
    }
    Assertions.assertNull(result[5]);
    for(int i=6;i<result.length;++i)
    {
      Assertions.assertEquals((Object)TypeConversionUtil.convertToObject(paramArr.length),result[i]);
    }
  }
  @Test
  public void testCheckedStacktoArray_ObjectArray_undersizedArray()
  {
    CheckedStack seq=new CheckedStack();
    Integer[] paramArr=new Integer[5];
    for(int i=0;i<paramArr.length;++i)
    {
      paramArr[i]=TypeConversionUtil.convertToObject(10);
    }
    for(int i=0;i<10;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(10,seq.size());
    Assertions.assertEquals(10,seq.modCount);
    Assertions.assertEquals(10,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertNotSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<10;++i)
    {
      Assertions.assertSame(itr.next(),result[i]);
    }
  }
  @Test
  public void testCheckedStacktoArray_ObjectArray_exactSizeArray()
  {
    CheckedStack seq=new CheckedStack();
    Integer[] paramArr=new Integer[5];
    for(int i=0;i<paramArr.length;++i)
    {
      paramArr[i]=TypeConversionUtil.convertToObject(5);
    }
    for(int i=0;i<5;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(5,seq.size());
    Assertions.assertEquals(5,seq.modCount);
    Assertions.assertEquals(5,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<5;++i)
    {
      Assertions.assertSame(itr.next(),result[i]);
    }
  }
  @Test
  public void testCheckedStacktoArray_IntFunction_seqIsEmpty_nonMod()
  {
    CheckedStack seq=new CheckedStack();
    IntFunction<Integer[]> arrConstructor=Integer[]::new;
    var result=seq.toArray(arrConstructor);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertEquals(0,result.length);
  }
  @Test
  public void testCheckedStacktoArray_IntFunction_seqIsNotEmpty_nonMod()
  {
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    IntFunction<Integer[]> arrConstructor=Integer[]::new;
    var result=seq.toArray(arrConstructor);
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i)
    {
      Assertions.assertSame(itr.next(),result[i]);
    }
  }
  @Test
  public void testCheckedStacktoArray_IntFunction_seqIsEmpty_moddingArrayConstructor()
  {
    CheckedStack seq=new CheckedStack();
    IntFunction<Integer[]> arrConstructor=(int arrSize)->
    {
      seq.add(TypeConversionUtil.convertToObject(arrSize));
      return new Integer[arrSize];
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(1,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertToObject(0),seq.arr[0]);
    Assertions.assertEquals(1,seq.modCount);
  }
  @Test
  public void testCheckedStacktoArray_IntFunction_seqIsNotEmpty_moddingArrayConstructor()
  {
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    IntFunction<Integer[]> arrConstructor=(int arrSize)->
    {
      seq.add(TypeConversionUtil.convertToObject(arrSize));
      return new Integer[arrSize];
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(101,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertToObject(100),seq.arr[100]);
    Assertions.assertEquals(101,seq.modCount);
  }
  @Test
  public void testCheckedStacktoArray_IntFunction_seqIsEmpty_throwingArrayConstructor()
  {
    CheckedStack seq=new CheckedStack();
    IntFunction<Integer[]> arrConstructor=(int arrSize)->
    {
      throw new IndexOutOfBoundsException();
    };
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStacktoArray_IntFunction_seqIsNotEmpty_throwingArrayConstructor()
  {
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    IntFunction<Integer[]> arrConstructor=(int arrSize)->
    {
      throw new IndexOutOfBoundsException();
    };
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedStacktoArray_IntFunction_seqIsEmpty_throwingAndModdingArrConstructor()
  {
    CheckedStack seq=new CheckedStack();
    IntFunction<Integer[]> arrConstructor=(int arrSize)->
    {
      seq.add(TypeConversionUtil.convertToObject(arrSize));
      throw new IndexOutOfBoundsException();
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(1,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertToObject(0),seq.arr[0]);
    Assertions.assertEquals(1,seq.modCount);
  }
  @Test
  public void testCheckedStacktoArray_IntFunction_seqIsNotEmpty_throwingAndModdingArrConstructor()
  {
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    IntFunction<Integer[]> arrConstructor=(int arrSize)->
    {
      seq.add(TypeConversionUtil.convertToObject(arrSize));
      throw new IndexOutOfBoundsException();
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(101,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertToObject(100),seq.arr[100]);
    Assertions.assertEquals(101,seq.modCount);
  }
  @Test
  public void testCheckedStackclear_void_seqIsEmpty()
  {
    CheckedStack seq=new CheckedStack();
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackclear_void_seqIsNotEmpty()
  {
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
    for(int i=0;i<100;++i)
    {
      Assertions.assertNull(seq.arr[i]);
    }
    Assertions.assertEquals(101,seq.modCount);
  }
  @Test
  public void testCheckedStackisEmpty_void_seqIsEmpty()
  {
    CheckedStack seq=new CheckedStack();
    Assertions.assertTrue(seq.isEmpty());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackisEmpty_void_seqIsNotEmpty()
  {
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertFalse(seq.isEmpty());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedStackisEmpty_void_seqIsBeingCleared()
  {
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;)
    {
      Assertions.assertFalse(seq.isEmpty());
      itr.next();
      itr.remove();
      Assertions.assertEquals(100+(100-i),seq.modCount);
    }
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testCheckedStacksize_void_seqIsEmpty()
  {
    CheckedStack seq=new CheckedStack();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStacksize_void_seqIsNotEmpty()
  {
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedStacksize_void_seqIsBeingCleared()
  {
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;)
    {
      itr.next();
      itr.remove();
      Assertions.assertEquals(i,seq.size());
      Assertions.assertEquals(100+(100-i),seq.modCount);
    }
  }
  @Test
  public void testCheckedStackforEach_Consumer_SeqIsEmpty_NoMod()
  {
    CheckedStack seq=new CheckedStack();
    MonitoredConsumer consumer=new MonitoredConsumer();
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedStackforEach_Consumer_SeqIsNotEmpty_NoMod()
  {
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    MonitoredConsumer consumer=new MonitoredConsumer();
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(100,consumer.size());
    var seqIterator=seq.iterator();
    var consumerIterator=consumer.iterator();
    for(int i=0;i<100;++i)
    {
      Assertions.assertSame(consumerIterator.next(),seqIterator.next());
    }
  }
  @Test
  public void testCheckedStackforEach_Consumer_SeqIsEmpty_ModdingConsumer()
  {
    CheckedStack seq=new CheckedStack();
    ModifyingCheckedStackConsumer consumer=new ModifyingCheckedStackConsumer(seq);
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedStackforEach_Consumer_SeqIsNotEmpty_ModdingConsumer()
  {
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    ModifyingCheckedStackConsumer consumer=new ModifyingCheckedStackConsumer(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.forEach((Consumer)consumer));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(300,seq.modCount);
    Assertions.assertEquals(100,consumer.size());
    var seqIterator=seq.iterator();
    var consumerIterator=consumer.iterator();
    for(int i=0;i<100;++i)
    {
      Assertions.assertSame(consumerIterator.next(),seqIterator.next());
    }
  }
  @Test
  public void testCheckedStackforEach_Consumer_SeqIsEmpty_ThrowingConsumer()
  {
    CheckedStack seq=new CheckedStack();
    ThrowingConsumer consumer=new ThrowingConsumer();
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedStackforEach_Consumer_SeqIsNotEmpty_ThrowingConsumer()
  {
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    ThrowingConsumer consumer=new ThrowingConsumer();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.forEach((Consumer)consumer));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(1,consumer.size());
    var seqIterator=seq.iterator();
    var consumerIterator=consumer.iterator();
    for(int i=0;i<1;++i)
    {
      Assertions.assertSame(consumerIterator.next(),seqIterator.next());
    }
  }
  @Test
  public void testCheckedStackforEach_Consumer_SeqIsEmpty_ThrowingAndModdingConsumer()
  {
    CheckedStack seq=new CheckedStack();
    ModifiyingCheckedStackAndThrowingConsumer consumer=new ModifiyingCheckedStackAndThrowingConsumer(seq);
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedStackforEach_Consumer_SeqIsNotEmpty_ThrowingAndModdingConsumer()
  {
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    ModifiyingCheckedStackAndThrowingConsumer consumer=new ModifiyingCheckedStackAndThrowingConsumer(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.forEach((Consumer)consumer));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(102,seq.modCount);
    Assertions.assertEquals(1,consumer.size());
  }
  //TODO TestRemoveIfMethods<DEFAULT>(,)
  //TODO TestRemoveIfMethods<NULL>(,)
  //TODO TestRemoveIfMethods<50>(,)
  @Test
  public void testCheckedStackadd_Object_initialCapacityDEFAULT()
  {
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_Object_initialCapacityNULL()
  {
    CheckedStack seq=new CheckedStack(0,null);
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_Object_initialCapacity50()
  {
    CheckedStack seq=new CheckedStack(50);
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStacktoArray_void_seqIsEmpty()
  {
    CheckedStack seq=new CheckedStack();
    Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.toArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStacktoArray_void_seqIsNotEmpty()
  {
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var result=seq.toArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i)
    {
      Assertions.assertSame(itr.next(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  static class ModifyingCheckedListConsumer extends MonitoredConsumer
  {
    private static final long serialVersionUID=1L;
    CheckedList seq;
    public ModifyingCheckedListConsumer(CheckedList seq)
    {
      this.seq=seq;
    }
    @Override public void accept(Object val)
    {
      seq.modCount+=2;
      super.accept((Object)val);
    }
  }
  static class ModifiyingCheckedListAndThrowingConsumer extends ModifyingCheckedListConsumer
  {
    private static final long serialVersionUID=1L;
    public ModifiyingCheckedListAndThrowingConsumer(CheckedList seq)
    {
      super(seq);
    }
    @Override public void accept(Object val)
    {
      super.accept((Object)val);
      throw new IndexOutOfBoundsException();
    }
  }
  static class ModifyingAndThrowingCheckedListPredicate extends ThrowingPredicate
  {
      CheckedList seq;
      public ModifyingAndThrowingCheckedListPredicate(CheckedList seq)
      {
        this.seq=seq;
      }
      @Override boolean testImpl(Object val)
      {
        seq.add(val);
        throw new IndexOutOfBoundsException();
      }
  }
  static class RemoveAllCheckedListModifyingPredicate extends RemoveAllPredicate
  {
      CheckedList seq;
      RemoveAllCheckedListModifyingPredicate(CheckedList seq)
      {
        this.seq=seq;
      }
      @Override
      public boolean test(Object val)
      {
        seq.modCount+=2;
        return super.test(val);
      }
  }
  static class RemoveNoneCheckedListModifyingPredicate extends RemoveNonePredicate
  {
      CheckedList seq;
      RemoveNoneCheckedListModifyingPredicate(CheckedList seq)
      {
        this.seq=seq;
      }
      @Override
      public boolean test(Object val)
      {
        seq.modCount+=2;
        return super.test(val);
      }
  }
    static class RetainSecondCheckedListModifyingPredicate extends RetainSecondPredicate
    {
      CheckedList seq;
      RetainSecondCheckedListModifyingPredicate(CheckedList seq)
      {
        this.seq=seq;
      }
      @Override
      public boolean test(Object val)
      {
        seq.modCount+=2;
        return super.test(val);
      }
    }
    static class RetainSecondAndLastCheckedListModifyingPredicate extends RetainSecondAndLastPredicate
    {
      CheckedList seq;
      RetainSecondAndLastCheckedListModifyingPredicate(CheckedList seq)
      {
        super(seq.size);
        this.seq=seq;
      }
      @Override
      public boolean test(Object val)
      {
        seq.modCount+=2;
        return super.test(val);
      }
    }
    static class RemoveFirstAndThirdCheckedListModifyingPredicate extends RemoveFirstAndThirdPredicate
    {
      CheckedList seq;
      RemoveFirstAndThirdCheckedListModifyingPredicate(CheckedList seq)
      {
        this.seq=seq;
      }
      @Override
      public boolean test(Object val)
      {
        seq.modCount+=2;
        return super.test(val);
      }
    }
    static class RemoveFirstCheckedListModifyingPredicate  extends RemoveFirstPredicate
    {
      CheckedList seq;
      RemoveFirstCheckedListModifyingPredicate(CheckedList seq)
      {
        this.seq=seq;
      }
      @Override
      public boolean test(Object val)
      {
        seq.modCount+=2;
        return super.test(val);
      }
    }
    static class RemoveFirstAndSecondToLastCheckedListModifyingPredicate extends RemoveFirstAndSecondToLastPredicate
    {
      CheckedList seq;
      RemoveFirstAndSecondToLastCheckedListModifyingPredicate(CheckedList seq)
      {
        super(seq.size);
        this.seq=seq;
      }
      @Override
      public boolean test(Object val)
      {
        seq.modCount+=2;
        return super.test(val);
      }
    }
  @Test
  public void testCheckedListconstructor_void_initialCapacityDEFAULT()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListconstructor_intObject_initialCapacityNULL()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertEquals(0,seq.size);
    Assertions.assertNull(seq.arr);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListconstructor_int_initialCapacity50()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertEquals(0,seq.size);
    switch(50)
    {
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(50,seq.arr.length);
      for(int i=0;i<50;++i)
      {
        Assertions.assertNull(seq.arr[i]);
      }
    }
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListconstructor_int_initialCapacity0()
  {
    CheckedList seq=new CheckedList(0);
    Assertions.assertEquals(0,seq.size);
    switch(0)
    {
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(0,seq.arr.length);
      for(int i=0;i<0;++i)
      {
        Assertions.assertNull(seq.arr[i]);
      }
    }
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListconstructor_int_initialCapacity10()
  {
    CheckedList seq=new CheckedList(10);
    Assertions.assertEquals(0,seq.size);
    switch(10)
    {
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(10,seq.arr.length);
      for(int i=0;i<10;++i)
      {
        Assertions.assertNull(seq.arr[i]);
      }
    }
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListtoArray_ObjectArray_zeroLengthArrayAndSequenceIsEmpty()
  {
    CheckedList seq=new CheckedList();
    Integer[] paramArr=new Integer[0];
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertEquals(0,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
  }
  @Test
  public void testCheckedListtoArray_ObjectArray_zeroLengthArrayAndSequenceNotEmpty()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Integer[] paramArr=new Integer[0];
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(100,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertNotSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<100;++i)
    {
      Assertions.assertSame(itr.next(),result[i]);
    }
  }
  @Test
  public void testCheckedListtoArray_ObjectArray_nonzeroLengthArrayAndSequenceIsEmpty()
  {
    CheckedList seq=new CheckedList();
    Integer[] paramArr=new Integer[5];
    for(int i=0;i<paramArr.length;++i)
    {
      paramArr[i]=TypeConversionUtil.convertToObject(paramArr.length);
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertEquals(5,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    Assertions.assertNull(result[0]);
    for(int i=1;i<result.length;++i)
    {
      Assertions.assertEquals((Object)TypeConversionUtil.convertToObject(paramArr.length),result[i]);
    }
  }
  @Test
  public void testCheckedListtoArray_ObjectArray_overSizedArray()
  {
    CheckedList seq=new CheckedList();
    Integer[] paramArr=new Integer[10];
    for(int i=0;i<paramArr.length;++i)
    {
      paramArr[i]=TypeConversionUtil.convertToObject(paramArr.length);
    }
    for(int i=0;i<5;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(5,seq.size());
    Assertions.assertEquals(5,seq.modCount);
    Assertions.assertEquals(10,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<5;++i)
    {
      Assertions.assertSame(itr.next(),result[i]);
    }
    Assertions.assertNull(result[5]);
    for(int i=6;i<result.length;++i)
    {
      Assertions.assertEquals((Object)TypeConversionUtil.convertToObject(paramArr.length),result[i]);
    }
  }
  @Test
  public void testCheckedListtoArray_ObjectArray_undersizedArray()
  {
    CheckedList seq=new CheckedList();
    Integer[] paramArr=new Integer[5];
    for(int i=0;i<paramArr.length;++i)
    {
      paramArr[i]=TypeConversionUtil.convertToObject(10);
    }
    for(int i=0;i<10;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(10,seq.size());
    Assertions.assertEquals(10,seq.modCount);
    Assertions.assertEquals(10,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertNotSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<10;++i)
    {
      Assertions.assertSame(itr.next(),result[i]);
    }
  }
  @Test
  public void testCheckedListtoArray_ObjectArray_exactSizeArray()
  {
    CheckedList seq=new CheckedList();
    Integer[] paramArr=new Integer[5];
    for(int i=0;i<paramArr.length;++i)
    {
      paramArr[i]=TypeConversionUtil.convertToObject(5);
    }
    for(int i=0;i<5;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(5,seq.size());
    Assertions.assertEquals(5,seq.modCount);
    Assertions.assertEquals(5,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<5;++i)
    {
      Assertions.assertSame(itr.next(),result[i]);
    }
  }
  @Test
  public void testCheckedListtoArray_IntFunction_seqIsEmpty_nonMod()
  {
    CheckedList seq=new CheckedList();
    IntFunction<Integer[]> arrConstructor=Integer[]::new;
    var result=seq.toArray(arrConstructor);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertEquals(0,result.length);
  }
  @Test
  public void testCheckedListtoArray_IntFunction_seqIsNotEmpty_nonMod()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    IntFunction<Integer[]> arrConstructor=Integer[]::new;
    var result=seq.toArray(arrConstructor);
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i)
    {
      Assertions.assertSame(itr.next(),result[i]);
    }
  }
  @Test
  public void testCheckedListtoArray_IntFunction_seqIsEmpty_moddingArrayConstructor()
  {
    CheckedList seq=new CheckedList();
    IntFunction<Integer[]> arrConstructor=(int arrSize)->
    {
      seq.add(TypeConversionUtil.convertToObject(arrSize));
      return new Integer[arrSize];
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(1,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertToObject(0),seq.arr[0]);
    Assertions.assertEquals(1,seq.modCount);
  }
  @Test
  public void testCheckedListtoArray_IntFunction_seqIsNotEmpty_moddingArrayConstructor()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    IntFunction<Integer[]> arrConstructor=(int arrSize)->
    {
      seq.add(TypeConversionUtil.convertToObject(arrSize));
      return new Integer[arrSize];
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(101,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertToObject(100),seq.arr[100]);
    Assertions.assertEquals(101,seq.modCount);
  }
  @Test
  public void testCheckedListtoArray_IntFunction_seqIsEmpty_throwingArrayConstructor()
  {
    CheckedList seq=new CheckedList();
    IntFunction<Integer[]> arrConstructor=(int arrSize)->
    {
      throw new IndexOutOfBoundsException();
    };
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListtoArray_IntFunction_seqIsNotEmpty_throwingArrayConstructor()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    IntFunction<Integer[]> arrConstructor=(int arrSize)->
    {
      throw new IndexOutOfBoundsException();
    };
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListtoArray_IntFunction_seqIsEmpty_throwingAndModdingArrConstructor()
  {
    CheckedList seq=new CheckedList();
    IntFunction<Integer[]> arrConstructor=(int arrSize)->
    {
      seq.add(TypeConversionUtil.convertToObject(arrSize));
      throw new IndexOutOfBoundsException();
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(1,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertToObject(0),seq.arr[0]);
    Assertions.assertEquals(1,seq.modCount);
  }
  @Test
  public void testCheckedListtoArray_IntFunction_seqIsNotEmpty_throwingAndModdingArrConstructor()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    IntFunction<Integer[]> arrConstructor=(int arrSize)->
    {
      seq.add(TypeConversionUtil.convertToObject(arrSize));
      throw new IndexOutOfBoundsException();
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(101,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertToObject(100),seq.arr[100]);
    Assertions.assertEquals(101,seq.modCount);
  }
  @Test
  public void testCheckedListclear_void_seqIsEmpty()
  {
    CheckedList seq=new CheckedList();
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListclear_void_seqIsNotEmpty()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
    for(int i=0;i<100;++i)
    {
      Assertions.assertNull(seq.arr[i]);
    }
    Assertions.assertEquals(101,seq.modCount);
  }
  @Test
  public void testCheckedListisEmpty_void_seqIsEmpty()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertTrue(seq.isEmpty());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListisEmpty_void_seqIsNotEmpty()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertFalse(seq.isEmpty());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListisEmpty_void_seqIsBeingCleared()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;)
    {
      Assertions.assertFalse(seq.isEmpty());
      itr.next();
      itr.remove();
      Assertions.assertEquals(100+(100-i),seq.modCount);
    }
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testCheckedListsize_void_seqIsEmpty()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListsize_void_seqIsNotEmpty()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListsize_void_seqIsBeingCleared()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;)
    {
      itr.next();
      itr.remove();
      Assertions.assertEquals(i,seq.size());
      Assertions.assertEquals(100+(100-i),seq.modCount);
    }
  }
  @Test
  public void testCheckedListforEach_Consumer_SeqIsEmpty_NoMod()
  {
    CheckedList seq=new CheckedList();
    MonitoredConsumer consumer=new MonitoredConsumer();
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedListforEach_Consumer_SeqIsNotEmpty_NoMod()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    MonitoredConsumer consumer=new MonitoredConsumer();
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(100,consumer.size());
    var seqIterator=seq.iterator();
    var consumerIterator=consumer.iterator();
    for(int i=0;i<100;++i)
    {
      Assertions.assertSame(consumerIterator.next(),seqIterator.next());
    }
  }
  @Test
  public void testCheckedListforEach_Consumer_SeqIsEmpty_ModdingConsumer()
  {
    CheckedList seq=new CheckedList();
    ModifyingCheckedListConsumer consumer=new ModifyingCheckedListConsumer(seq);
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedListforEach_Consumer_SeqIsNotEmpty_ModdingConsumer()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    ModifyingCheckedListConsumer consumer=new ModifyingCheckedListConsumer(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.forEach((Consumer)consumer));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(300,seq.modCount);
    Assertions.assertEquals(100,consumer.size());
    var seqIterator=seq.iterator();
    var consumerIterator=consumer.iterator();
    for(int i=0;i<100;++i)
    {
      Assertions.assertSame(consumerIterator.next(),seqIterator.next());
    }
  }
  @Test
  public void testCheckedListforEach_Consumer_SeqIsEmpty_ThrowingConsumer()
  {
    CheckedList seq=new CheckedList();
    ThrowingConsumer consumer=new ThrowingConsumer();
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedListforEach_Consumer_SeqIsNotEmpty_ThrowingConsumer()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    ThrowingConsumer consumer=new ThrowingConsumer();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.forEach((Consumer)consumer));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(1,consumer.size());
    var seqIterator=seq.iterator();
    var consumerIterator=consumer.iterator();
    for(int i=0;i<1;++i)
    {
      Assertions.assertSame(consumerIterator.next(),seqIterator.next());
    }
  }
  @Test
  public void testCheckedListforEach_Consumer_SeqIsEmpty_ThrowingAndModdingConsumer()
  {
    CheckedList seq=new CheckedList();
    ModifiyingCheckedListAndThrowingConsumer consumer=new ModifiyingCheckedListAndThrowingConsumer(seq);
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedListforEach_Consumer_SeqIsNotEmpty_ThrowingAndModdingConsumer()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    ModifiyingCheckedListAndThrowingConsumer consumer=new ModifiyingCheckedListAndThrowingConsumer(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.forEach((Consumer)consumer));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(102,seq.modCount);
    Assertions.assertEquals(1,consumer.size());
  }
  //TODO TestRemoveIfMethods<DEFAULT>(,)
  //TODO TestRemoveIfMethods<NULL>(,)
  //TODO TestRemoveIfMethods<50>(,)
  @Test
  public void testCheckedListadd_Object_initialCapacityDEFAULT()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_Object_initialCapacityNULL()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_Object_initialCapacity50()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListtoArray_void_seqIsEmpty()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.toArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListtoArray_void_seqIsNotEmpty()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var result=seq.toArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i)
    {
      Assertions.assertSame(itr.next(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  static class MonitoredConsumer extends ArrayList implements Consumer
  {
    private static final long serialVersionUID=1L;
    @Override public void accept(Object val)
    {
      super.add(val);
    }
  }
  static class ThrowingConsumer extends MonitoredConsumer
  {
    private static final long serialVersionUID=1L;
    @Override public void accept(Object val)
    {
      super.accept((Object)val);
      throw new IndexOutOfBoundsException();
    }
  }
  private static abstract class AbstractMonitoredPredicate implements Predicate
  {
    int callCounter;
    abstract boolean testImpl(Object val);
    @Override public boolean test(Object val)
    {
      ++callCounter;
      return testImpl((Object)val);
    }
    public AbstractMonitoredPredicate negate()
    {
      //don't care
      return null;
    }
  }
  static class RemoveAllPredicate extends AbstractMonitoredPredicate
  {
    boolean testImpl(Object val)
    {
      return true;
    }
  }
  static class RemoveNonePredicate extends AbstractMonitoredPredicate
  {
    boolean testImpl(Object val)
    {
      return false;
    }
  }
  static class ThrowingPredicate extends AbstractMonitoredPredicate
  {
    @Override boolean testImpl(Object val)
    {
      throw new IndexOutOfBoundsException();
    }
  }
  static class RetainSecondPredicate extends AbstractMonitoredPredicate
  {
    boolean testImpl(Object val)
    {
      return !EqualityUtil.isEqual(val,TypeConversionUtil.convertToObject(1));
    }
  }
  static class RetainSecondAndLastPredicate extends AbstractMonitoredPredicate
  {
    int seqLength;
    RetainSecondAndLastPredicate(int seqLength)
    {
      this.seqLength=seqLength;
    }
    boolean testImpl(Object val)
    {
      return !EqualityUtil.isEqual(val,TypeConversionUtil.convertToObject(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertToObject(seqLength-1));
    }
  }
  static class RemoveFirstAndThirdPredicate extends AbstractMonitoredPredicate
  {
    boolean testImpl(Object val)
    {
      return EqualityUtil.isEqual(val,TypeConversionUtil.convertToObject(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertToObject(2));
    }
  }
  static class RemoveFirstPredicate  extends AbstractMonitoredPredicate
  {
    boolean testImpl(Object val)
    {
      return EqualityUtil.isEqual(val,TypeConversionUtil.convertToObject(0));
    }
  }
  static class RemoveFirstAndSecondToLastPredicate extends AbstractMonitoredPredicate
  {
    int seqLength;
    RemoveFirstAndSecondToLastPredicate(int seqLength)
    {
      this.seqLength=seqLength;
    }
    boolean testImpl(Object val)
    {
      return EqualityUtil.isEqual(val,TypeConversionUtil.convertToObject(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertToObject(seqLength-2));
    }
  }
}
