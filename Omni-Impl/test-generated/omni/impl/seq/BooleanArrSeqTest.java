package omni.impl.seq;
//import omni.impl.seq.BooleanArrSeq.UncheckedList;
//import omni.impl.seq.BooleanArrSeq.CheckedList;
//import omni.impl.seq.BooleanArrSeq.UncheckedStack;
//import omni.impl.seq.BooleanArrSeq.CheckedStack;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.function.IntFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import omni.function.BooleanConsumer;
import omni.function.BooleanPredicate;
import java.util.ConcurrentModificationException;
import omni.util.OmniArray;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class BooleanArrSeqTest{
  @Test
  public void testUncheckedStackconstructor_void_initialCapacityDEFAULT(){
    var seq=new BooleanArrSeq.UncheckedStack();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertSame(OmniArray.OfBoolean.DEFAULT_ARR,seq.arr);
  }
  @Test
  public void testUncheckedStackconstructor_intboolean_initialCapacityNULL(){
    var seq=new BooleanArrSeq.UncheckedStack(0,null);
    Assertions.assertEquals(0,seq.size);
    Assertions.assertNull(seq.arr);
  }
  @Test
  public void testUncheckedStackconstructor_int_initialCapacity50(){
    var seq=new BooleanArrSeq.UncheckedStack(50);
    Assertions.assertEquals(0,seq.size);
    switch(50){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfBoolean.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(50,seq.arr.length);
    }
  }
  @Test
  public void testUncheckedStackconstructor_int_initialCapacity0(){
    var seq=new BooleanArrSeq.UncheckedStack(0);
    Assertions.assertEquals(0,seq.size);
    switch(0){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfBoolean.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(0,seq.arr.length);
    }
  }
  @Test
  public void testUncheckedStackconstructor_int_initialCapacity10(){
    var seq=new BooleanArrSeq.UncheckedStack(10);
    Assertions.assertEquals(0,seq.size);
    switch(10){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfBoolean.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(10,seq.arr.length);
    }
  }
  @Test
  public void testUncheckedStackClone_initialCapacityDEFAULT_seqIsEmpty()
  {
    var seq=new BooleanArrSeq.UncheckedStack();
    var clone=(BooleanArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfBoolean.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedStackClone_initialCapacityDEFAULT_seqIsNotEmpty()
  {
    var seq=new BooleanArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var clone=(BooleanArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackClone_initialCapacityNULL_seqIsEmpty()
  {
    var seq=new BooleanArrSeq.UncheckedStack(0,null);
    var clone=(BooleanArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfBoolean.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedStackClone_initialCapacityNULL_seqIsNotEmpty()
  {
    var seq=new BooleanArrSeq.UncheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var clone=(BooleanArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackClone_initialCapacity50_seqIsEmpty()
  {
    var seq=new BooleanArrSeq.UncheckedStack(50);
    var clone=(BooleanArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfBoolean.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedStackClone_initialCapacity50_seqIsNotEmpty()
  {
    var seq=new BooleanArrSeq.UncheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var clone=(BooleanArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackClone_initialCapacity0_seqIsEmpty()
  {
    var seq=new BooleanArrSeq.UncheckedStack(0);
    var clone=(BooleanArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfBoolean.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedStackClone_initialCapacity0_seqIsNotEmpty()
  {
    var seq=new BooleanArrSeq.UncheckedStack(0);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var clone=(BooleanArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackClone_initialCapacity10_seqIsEmpty()
  {
    var seq=new BooleanArrSeq.UncheckedStack(10);
    var clone=(BooleanArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfBoolean.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedStackClone_initialCapacity10_seqIsNotEmpty()
  {
    var seq=new BooleanArrSeq.UncheckedStack(10);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var clone=(BooleanArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedStacktoArray_ObjectArray_zeroLengthArrayAndSequenceIsEmpty(){
    var seq=new BooleanArrSeq.UncheckedStack();
    Boolean[] paramArr=new Boolean[0];
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
  }
  @Test
  public void testUncheckedStacktoArray_ObjectArray_zeroLengthArrayAndSequenceNotEmpty(){
    var seq=new BooleanArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Boolean[] paramArr=new Boolean[0];
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertNotSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.next(),result[i]);
    }
  }
  @Test
  public void testUncheckedStacktoArray_ObjectArray_nonzeroLengthArrayAndSequenceIsEmpty(){
    var seq=new BooleanArrSeq.UncheckedStack();
    Boolean[] paramArr=new Boolean[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToboolean(paramArr.length);
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(5,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    Assertions.assertNull(result[0]);
    for(int i=1;i<result.length;++i){
      Assertions.assertEquals((Object)TypeConversionUtil.convertToboolean(paramArr.length),result[i]);
    }
  }
  @Test
  public void testUncheckedStacktoArray_ObjectArray_overSizedArray(){
    var seq=new BooleanArrSeq.UncheckedStack();
    Boolean[] paramArr=new Boolean[10];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToboolean(paramArr.length);
    }
    for(int i=0;i<5;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(5,seq.size());
    Assertions.assertEquals(10,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<5;++i){
      Assertions.assertEquals(itr.next(),result[i]);
    }
    Assertions.assertNull(result[5]);
    for(int i=6;i<result.length;++i){
      Assertions.assertEquals((Object)TypeConversionUtil.convertToboolean(paramArr.length),result[i]);
    }
  }
  @Test
  public void testUncheckedStacktoArray_ObjectArray_undersizedArray(){
    var seq=new BooleanArrSeq.UncheckedStack();
    Boolean[] paramArr=new Boolean[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToboolean(10);
    }
    for(int i=0;i<10;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(10,seq.size());
    Assertions.assertEquals(10,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertNotSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<10;++i){
      Assertions.assertEquals(itr.next(),result[i]);
    }
  }
  @Test
  public void testUncheckedStacktoArray_ObjectArray_exactSizeArray(){
    var seq=new BooleanArrSeq.UncheckedStack();
    Boolean[] paramArr=new Boolean[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToboolean(5);
    }
    for(int i=0;i<5;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(5,seq.size());
    Assertions.assertEquals(5,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<5;++i){
      Assertions.assertEquals(itr.next(),result[i]);
    }
  }
  @Test
  public void testUncheckedStacktoArray_IntFunction_seqIsEmpty_nonMod(){
    var seq=new BooleanArrSeq.UncheckedStack();
    IntFunction<Boolean[]> arrConstructor=Boolean[]::new;
    var result=seq.toArray(arrConstructor);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertEquals(0,result.length);
  }
  @Test
  public void testUncheckedStacktoArray_IntFunction_seqIsNotEmpty_nonMod(){
    var seq=new BooleanArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    IntFunction<Boolean[]> arrConstructor=Boolean[]::new;
    var result=seq.toArray(arrConstructor);
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.next(),result[i]);
    }
  }
  @Test
  public void testUncheckedStackclear_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.UncheckedStack();
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testUncheckedStackclear_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
    for(int i=0;i<100;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackisEmpty_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.UncheckedStack();
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testUncheckedStackisEmpty_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertFalse(seq.isEmpty());
  }
  @Test
  public void testUncheckedStackisEmpty_void_seqIsBeingCleared(){
    var seq=new BooleanArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;){
      Assertions.assertFalse(seq.isEmpty());
      itr.nextBoolean();
      itr.remove();
    }
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testUncheckedStacksize_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.UncheckedStack();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
  }
  @Test
  public void testUncheckedStacksize_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
  }
  @Test
  public void testUncheckedStacksize_void_seqIsBeingCleared(){
    var seq=new BooleanArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;){
      itr.nextBoolean();
      itr.remove();
      Assertions.assertEquals(i,seq.size());
    }
  }
  @Test
  public void testUncheckedStackforEach_Consumer_SeqIsEmpty_NoMod(){
    var seq=new BooleanArrSeq.UncheckedStack();
    var consumer=new BooleanMonitoredConsumer();
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testUncheckedStackforEach_Consumer_SeqIsNotEmpty_NoMod(){
    var seq=new BooleanArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var consumer=new BooleanMonitoredConsumer();
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,consumer.size());
    var seqIterator=seq.iterator();
    var consumerIterator=consumer.iterator();
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(consumerIterator.next(),seqIterator.next());
    }
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveTruePredicate()
  {
    var seq=new BooleanArrSeq.UncheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveTruePredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveFalsePredicate()
  {
    var seq=new BooleanArrSeq.UncheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveFalsePredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_FirstValtrue_RemoveTrue(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.UncheckedStack();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveTruePredicate();
      Assertions.assertEquals(trueCount!=0,seq.removeIf((Predicate)filter));
      Assertions.assertEquals(falseCount,seq.size());
      for(int i=0;i<falseCount;++i){
        Assertions.assertFalse(seq.arr[i]);
      }
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
    }
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_FirstValfalse_RemoveTrue(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.UncheckedStack();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveTruePredicate();
      Assertions.assertEquals(trueCount!=0,seq.removeIf((Predicate)filter));
      Assertions.assertEquals(falseCount,seq.size());
      for(int i=0;i<falseCount;++i){
        Assertions.assertFalse(seq.arr[i]);
      }
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
    }
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_FirstValtrue_RemoveFalse(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.UncheckedStack();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveFalsePredicate();
      Assertions.assertEquals(falseCount!=0,seq.removeIf((Predicate)filter));
      Assertions.assertEquals(trueCount,seq.size());
      for(int i=0;i<trueCount;++i){
        Assertions.assertTrue(seq.arr[i]);
      }
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
    }
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_FirstValfalse_RemoveFalse(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.UncheckedStack();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveFalsePredicate();
      Assertions.assertEquals(falseCount!=0,seq.removeIf((Predicate)filter));
      Assertions.assertEquals(trueCount,seq.size());
      for(int i=0;i<trueCount;++i){
        Assertions.assertTrue(seq.arr[i]);
      }
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
    }
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_FirstValtrue_RemoveAll(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.UncheckedStack();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveAllPredicate();
      Assertions.assertTrue(seq.removeIf((Predicate)filter));
      Assertions.assertEquals(0,seq.size());
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
    }
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_FirstValfalse_RemoveAll(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.UncheckedStack();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveAllPredicate();
      Assertions.assertTrue(seq.removeIf((Predicate)filter));
      Assertions.assertEquals(0,seq.size());
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
    }
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_FirstValtrue_RemoveNone(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.UncheckedStack();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveNonePredicate();
      Assertions.assertFalse(seq.removeIf((Predicate)filter));
      Assertions.assertEquals(10,seq.size());
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      currVal=true;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
    }
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_FirstValfalse_RemoveNone(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.UncheckedStack();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveNonePredicate();
      Assertions.assertFalse(seq.removeIf((Predicate)filter));
      Assertions.assertEquals(10,seq.size());
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      currVal=false;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
    }
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveAllPredicate()
  {
    var seq=new BooleanArrSeq.UncheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveAllPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveNonePredicate()
  {
    var seq=new BooleanArrSeq.UncheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveNonePredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedStackforEach_BooleanConsumer_SeqIsEmpty_NoMod(){
    var seq=new BooleanArrSeq.UncheckedStack();
    var consumer=new BooleanMonitoredConsumer();
    seq.forEach((BooleanConsumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testUncheckedStackforEach_BooleanConsumer_SeqIsNotEmpty_NoMod(){
    var seq=new BooleanArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var consumer=new BooleanMonitoredConsumer();
    seq.forEach((BooleanConsumer)consumer);
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,consumer.size());
    var seqIterator=seq.iterator();
    var consumerIterator=consumer.iterator();
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(consumerIterator.next(),seqIterator.next());
    }
  }
  @Test
  public void testUncheckedStackremoveIf_BooleanPredicate_SeqIsEmpty_RemoveTruePredicate()
  {
    var seq=new BooleanArrSeq.UncheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveTruePredicate(seq);
    Assertions.assertFalse(seq.removeIf((BooleanPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_BooleanPredicate_SeqIsEmpty_RemoveFalsePredicate()
  {
    var seq=new BooleanArrSeq.UncheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveFalsePredicate(seq);
    Assertions.assertFalse(seq.removeIf((BooleanPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_BooleanPredicate_FirstValtrue_RemoveTrue(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.UncheckedStack();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveTruePredicate();
      Assertions.assertEquals(trueCount!=0,seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(falseCount,seq.size());
      for(int i=0;i<falseCount;++i){
        Assertions.assertFalse(seq.arr[i]);
      }
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
    }
  }
  @Test
  public void testUncheckedStackremoveIf_BooleanPredicate_FirstValfalse_RemoveTrue(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.UncheckedStack();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveTruePredicate();
      Assertions.assertEquals(trueCount!=0,seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(falseCount,seq.size());
      for(int i=0;i<falseCount;++i){
        Assertions.assertFalse(seq.arr[i]);
      }
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
    }
  }
  @Test
  public void testUncheckedStackremoveIf_BooleanPredicate_FirstValtrue_RemoveFalse(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.UncheckedStack();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveFalsePredicate();
      Assertions.assertEquals(falseCount!=0,seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(trueCount,seq.size());
      for(int i=0;i<trueCount;++i){
        Assertions.assertTrue(seq.arr[i]);
      }
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
    }
  }
  @Test
  public void testUncheckedStackremoveIf_BooleanPredicate_FirstValfalse_RemoveFalse(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.UncheckedStack();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveFalsePredicate();
      Assertions.assertEquals(falseCount!=0,seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(trueCount,seq.size());
      for(int i=0;i<trueCount;++i){
        Assertions.assertTrue(seq.arr[i]);
      }
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
    }
  }
  @Test
  public void testUncheckedStackremoveIf_BooleanPredicate_FirstValtrue_RemoveAll(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.UncheckedStack();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveAllPredicate();
      Assertions.assertTrue(seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(0,seq.size());
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
    }
  }
  @Test
  public void testUncheckedStackremoveIf_BooleanPredicate_FirstValfalse_RemoveAll(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.UncheckedStack();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveAllPredicate();
      Assertions.assertTrue(seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(0,seq.size());
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
    }
  }
  @Test
  public void testUncheckedStackremoveIf_BooleanPredicate_FirstValtrue_RemoveNone(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.UncheckedStack();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveNonePredicate();
      Assertions.assertFalse(seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(10,seq.size());
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      currVal=true;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
    }
  }
  @Test
  public void testUncheckedStackremoveIf_BooleanPredicate_FirstValfalse_RemoveNone(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.UncheckedStack();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveNonePredicate();
      Assertions.assertFalse(seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(10,seq.size());
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      currVal=false;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
    }
  }
  @Test
  public void testUncheckedStackremoveIf_BooleanPredicate_SeqIsEmpty_RemoveAllPredicate()
  {
    var seq=new BooleanArrSeq.UncheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveAllPredicate(seq);
    Assertions.assertFalse(seq.removeIf((BooleanPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_BooleanPredicate_SeqIsEmpty_RemoveNonePredicate()
  {
    var seq=new BooleanArrSeq.UncheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveNonePredicate(seq);
    Assertions.assertFalse(seq.removeIf((BooleanPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedStackadd_boolean_initialCapacityDEFAULT(){
    var seq=new BooleanArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_boolean_initialCapacityNULL(){
    var seq=new BooleanArrSeq.UncheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_boolean_initialCapacity50(){
    var seq=new BooleanArrSeq.UncheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_Boolean_initialCapacityDEFAULT(){
    var seq=new BooleanArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_Boolean_initialCapacityNULL(){
    var seq=new BooleanArrSeq.UncheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_Boolean_initialCapacity50(){
    var seq=new BooleanArrSeq.UncheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStacktoBooleanArray_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.UncheckedStack();
    Assertions.assertSame(OmniArray.OfBoolean.DEFAULT_ARR,seq.toBooleanArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStacktoBooleanArray_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toBooleanArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.nextBoolean(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testUncheckedStacktoArray_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.UncheckedStack();
    Assertions.assertSame(OmniArray.OfBoolean.DEFAULT_BOXED_ARR,seq.toArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStacktoArray_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.next(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testUncheckedStacktoDoubleArray_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.UncheckedStack();
    Assertions.assertSame(OmniArray.OfDouble.DEFAULT_ARR,seq.toDoubleArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStacktoDoubleArray_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toDoubleArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.nextDouble(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testUncheckedStacktoFloatArray_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.UncheckedStack();
    Assertions.assertSame(OmniArray.OfFloat.DEFAULT_ARR,seq.toFloatArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStacktoFloatArray_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toFloatArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.nextFloat(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testUncheckedStacktoLongArray_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.UncheckedStack();
    Assertions.assertSame(OmniArray.OfLong.DEFAULT_ARR,seq.toLongArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStacktoLongArray_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toLongArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.nextLong(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testUncheckedStacktoIntArray_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.UncheckedStack();
    Assertions.assertSame(OmniArray.OfInt.DEFAULT_ARR,seq.toIntArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStacktoIntArray_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toIntArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.nextInt(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testUncheckedStacktoShortArray_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.UncheckedStack();
    Assertions.assertSame(OmniArray.OfShort.DEFAULT_ARR,seq.toShortArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStacktoShortArray_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toShortArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.nextShort(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testUncheckedStacktoCharArray_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.UncheckedStack();
    Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,seq.toCharArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStacktoCharArray_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toCharArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.nextChar(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testUncheckedStacktoByteArray_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.UncheckedStack();
    Assertions.assertSame(OmniArray.OfByte.DEFAULT_ARR,seq.toByteArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStacktoByteArray_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toByteArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.nextByte(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testUncheckedListconstructor_void_initialCapacityDEFAULT(){
    var seq=new BooleanArrSeq.UncheckedList();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertSame(OmniArray.OfBoolean.DEFAULT_ARR,seq.arr);
  }
  @Test
  public void testUncheckedListconstructor_intboolean_initialCapacityNULL(){
    var seq=new BooleanArrSeq.UncheckedList(0,null);
    Assertions.assertEquals(0,seq.size);
    Assertions.assertNull(seq.arr);
  }
  @Test
  public void testUncheckedListconstructor_int_initialCapacity50(){
    var seq=new BooleanArrSeq.UncheckedList(50);
    Assertions.assertEquals(0,seq.size);
    switch(50){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfBoolean.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(50,seq.arr.length);
    }
  }
  @Test
  public void testUncheckedListconstructor_int_initialCapacity0(){
    var seq=new BooleanArrSeq.UncheckedList(0);
    Assertions.assertEquals(0,seq.size);
    switch(0){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfBoolean.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(0,seq.arr.length);
    }
  }
  @Test
  public void testUncheckedListconstructor_int_initialCapacity10(){
    var seq=new BooleanArrSeq.UncheckedList(10);
    Assertions.assertEquals(0,seq.size);
    switch(10){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfBoolean.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(10,seq.arr.length);
    }
  }
  @Test
  public void testUncheckedListClone_initialCapacityDEFAULT_seqIsEmpty()
  {
    var seq=new BooleanArrSeq.UncheckedList();
    var clone=(BooleanArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfBoolean.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedListClone_initialCapacityDEFAULT_seqIsNotEmpty()
  {
    var seq=new BooleanArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var clone=(BooleanArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedListClone_initialCapacityNULL_seqIsEmpty()
  {
    var seq=new BooleanArrSeq.UncheckedList(0,null);
    var clone=(BooleanArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfBoolean.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedListClone_initialCapacityNULL_seqIsNotEmpty()
  {
    var seq=new BooleanArrSeq.UncheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var clone=(BooleanArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedListClone_initialCapacity50_seqIsEmpty()
  {
    var seq=new BooleanArrSeq.UncheckedList(50);
    var clone=(BooleanArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfBoolean.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedListClone_initialCapacity50_seqIsNotEmpty()
  {
    var seq=new BooleanArrSeq.UncheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var clone=(BooleanArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedListClone_initialCapacity0_seqIsEmpty()
  {
    var seq=new BooleanArrSeq.UncheckedList(0);
    var clone=(BooleanArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfBoolean.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedListClone_initialCapacity0_seqIsNotEmpty()
  {
    var seq=new BooleanArrSeq.UncheckedList(0);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var clone=(BooleanArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedListClone_initialCapacity10_seqIsEmpty()
  {
    var seq=new BooleanArrSeq.UncheckedList(10);
    var clone=(BooleanArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfBoolean.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedListClone_initialCapacity10_seqIsNotEmpty()
  {
    var seq=new BooleanArrSeq.UncheckedList(10);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var clone=(BooleanArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedListtoArray_ObjectArray_zeroLengthArrayAndSequenceIsEmpty(){
    var seq=new BooleanArrSeq.UncheckedList();
    Boolean[] paramArr=new Boolean[0];
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
  }
  @Test
  public void testUncheckedListtoArray_ObjectArray_zeroLengthArrayAndSequenceNotEmpty(){
    var seq=new BooleanArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Boolean[] paramArr=new Boolean[0];
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertNotSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.next(),result[i]);
    }
  }
  @Test
  public void testUncheckedListtoArray_ObjectArray_nonzeroLengthArrayAndSequenceIsEmpty(){
    var seq=new BooleanArrSeq.UncheckedList();
    Boolean[] paramArr=new Boolean[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToboolean(paramArr.length);
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(5,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    Assertions.assertNull(result[0]);
    for(int i=1;i<result.length;++i){
      Assertions.assertEquals((Object)TypeConversionUtil.convertToboolean(paramArr.length),result[i]);
    }
  }
  @Test
  public void testUncheckedListtoArray_ObjectArray_overSizedArray(){
    var seq=new BooleanArrSeq.UncheckedList();
    Boolean[] paramArr=new Boolean[10];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToboolean(paramArr.length);
    }
    for(int i=0;i<5;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(5,seq.size());
    Assertions.assertEquals(10,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<5;++i){
      Assertions.assertEquals(itr.next(),result[i]);
    }
    Assertions.assertNull(result[5]);
    for(int i=6;i<result.length;++i){
      Assertions.assertEquals((Object)TypeConversionUtil.convertToboolean(paramArr.length),result[i]);
    }
  }
  @Test
  public void testUncheckedListtoArray_ObjectArray_undersizedArray(){
    var seq=new BooleanArrSeq.UncheckedList();
    Boolean[] paramArr=new Boolean[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToboolean(10);
    }
    for(int i=0;i<10;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(10,seq.size());
    Assertions.assertEquals(10,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertNotSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<10;++i){
      Assertions.assertEquals(itr.next(),result[i]);
    }
  }
  @Test
  public void testUncheckedListtoArray_ObjectArray_exactSizeArray(){
    var seq=new BooleanArrSeq.UncheckedList();
    Boolean[] paramArr=new Boolean[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToboolean(5);
    }
    for(int i=0;i<5;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(5,seq.size());
    Assertions.assertEquals(5,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<5;++i){
      Assertions.assertEquals(itr.next(),result[i]);
    }
  }
  @Test
  public void testUncheckedListtoArray_IntFunction_seqIsEmpty_nonMod(){
    var seq=new BooleanArrSeq.UncheckedList();
    IntFunction<Boolean[]> arrConstructor=Boolean[]::new;
    var result=seq.toArray(arrConstructor);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertEquals(0,result.length);
  }
  @Test
  public void testUncheckedListtoArray_IntFunction_seqIsNotEmpty_nonMod(){
    var seq=new BooleanArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    IntFunction<Boolean[]> arrConstructor=Boolean[]::new;
    var result=seq.toArray(arrConstructor);
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.next(),result[i]);
    }
  }
  @Test
  public void testUncheckedListclear_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.UncheckedList();
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testUncheckedListclear_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
    for(int i=0;i<100;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListisEmpty_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.UncheckedList();
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testUncheckedListisEmpty_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertFalse(seq.isEmpty());
  }
  @Test
  public void testUncheckedListisEmpty_void_seqIsBeingCleared(){
    var seq=new BooleanArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;){
      Assertions.assertFalse(seq.isEmpty());
      itr.nextBoolean();
      itr.remove();
    }
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testUncheckedListsize_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.UncheckedList();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
  }
  @Test
  public void testUncheckedListsize_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
  }
  @Test
  public void testUncheckedListsize_void_seqIsBeingCleared(){
    var seq=new BooleanArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;){
      itr.nextBoolean();
      itr.remove();
      Assertions.assertEquals(i,seq.size());
    }
  }
  @Test
  public void testUncheckedListforEach_Consumer_SeqIsEmpty_NoMod(){
    var seq=new BooleanArrSeq.UncheckedList();
    var consumer=new BooleanMonitoredConsumer();
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testUncheckedListforEach_Consumer_SeqIsNotEmpty_NoMod(){
    var seq=new BooleanArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var consumer=new BooleanMonitoredConsumer();
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,consumer.size());
    var seqIterator=seq.iterator();
    var consumerIterator=consumer.iterator();
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(consumerIterator.next(),seqIterator.next());
    }
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqIsEmpty_RemoveTruePredicate()
  {
    var seq=new BooleanArrSeq.UncheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveTruePredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqIsEmpty_RemoveFalsePredicate()
  {
    var seq=new BooleanArrSeq.UncheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveFalsePredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_FirstValtrue_RemoveTrue(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.UncheckedList();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveTruePredicate();
      Assertions.assertEquals(trueCount!=0,seq.removeIf((Predicate)filter));
      Assertions.assertEquals(falseCount,seq.size());
      for(int i=0;i<falseCount;++i){
        Assertions.assertFalse(seq.arr[i]);
      }
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
    }
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_FirstValfalse_RemoveTrue(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.UncheckedList();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveTruePredicate();
      Assertions.assertEquals(trueCount!=0,seq.removeIf((Predicate)filter));
      Assertions.assertEquals(falseCount,seq.size());
      for(int i=0;i<falseCount;++i){
        Assertions.assertFalse(seq.arr[i]);
      }
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
    }
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_FirstValtrue_RemoveFalse(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.UncheckedList();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveFalsePredicate();
      Assertions.assertEquals(falseCount!=0,seq.removeIf((Predicate)filter));
      Assertions.assertEquals(trueCount,seq.size());
      for(int i=0;i<trueCount;++i){
        Assertions.assertTrue(seq.arr[i]);
      }
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
    }
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_FirstValfalse_RemoveFalse(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.UncheckedList();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveFalsePredicate();
      Assertions.assertEquals(falseCount!=0,seq.removeIf((Predicate)filter));
      Assertions.assertEquals(trueCount,seq.size());
      for(int i=0;i<trueCount;++i){
        Assertions.assertTrue(seq.arr[i]);
      }
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
    }
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_FirstValtrue_RemoveAll(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.UncheckedList();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveAllPredicate();
      Assertions.assertTrue(seq.removeIf((Predicate)filter));
      Assertions.assertEquals(0,seq.size());
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
    }
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_FirstValfalse_RemoveAll(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.UncheckedList();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveAllPredicate();
      Assertions.assertTrue(seq.removeIf((Predicate)filter));
      Assertions.assertEquals(0,seq.size());
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
    }
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_FirstValtrue_RemoveNone(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.UncheckedList();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveNonePredicate();
      Assertions.assertFalse(seq.removeIf((Predicate)filter));
      Assertions.assertEquals(10,seq.size());
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      currVal=true;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
    }
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_FirstValfalse_RemoveNone(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.UncheckedList();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveNonePredicate();
      Assertions.assertFalse(seq.removeIf((Predicate)filter));
      Assertions.assertEquals(10,seq.size());
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      currVal=false;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
    }
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqIsEmpty_RemoveAllPredicate()
  {
    var seq=new BooleanArrSeq.UncheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveAllPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqIsEmpty_RemoveNonePredicate()
  {
    var seq=new BooleanArrSeq.UncheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveNonePredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedListforEach_BooleanConsumer_SeqIsEmpty_NoMod(){
    var seq=new BooleanArrSeq.UncheckedList();
    var consumer=new BooleanMonitoredConsumer();
    seq.forEach((BooleanConsumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testUncheckedListforEach_BooleanConsumer_SeqIsNotEmpty_NoMod(){
    var seq=new BooleanArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var consumer=new BooleanMonitoredConsumer();
    seq.forEach((BooleanConsumer)consumer);
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,consumer.size());
    var seqIterator=seq.iterator();
    var consumerIterator=consumer.iterator();
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(consumerIterator.next(),seqIterator.next());
    }
  }
  @Test
  public void testUncheckedListremoveIf_BooleanPredicate_SeqIsEmpty_RemoveTruePredicate()
  {
    var seq=new BooleanArrSeq.UncheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveTruePredicate(seq);
    Assertions.assertFalse(seq.removeIf((BooleanPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_BooleanPredicate_SeqIsEmpty_RemoveFalsePredicate()
  {
    var seq=new BooleanArrSeq.UncheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveFalsePredicate(seq);
    Assertions.assertFalse(seq.removeIf((BooleanPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_BooleanPredicate_FirstValtrue_RemoveTrue(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.UncheckedList();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveTruePredicate();
      Assertions.assertEquals(trueCount!=0,seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(falseCount,seq.size());
      for(int i=0;i<falseCount;++i){
        Assertions.assertFalse(seq.arr[i]);
      }
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
    }
  }
  @Test
  public void testUncheckedListremoveIf_BooleanPredicate_FirstValfalse_RemoveTrue(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.UncheckedList();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveTruePredicate();
      Assertions.assertEquals(trueCount!=0,seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(falseCount,seq.size());
      for(int i=0;i<falseCount;++i){
        Assertions.assertFalse(seq.arr[i]);
      }
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
    }
  }
  @Test
  public void testUncheckedListremoveIf_BooleanPredicate_FirstValtrue_RemoveFalse(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.UncheckedList();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveFalsePredicate();
      Assertions.assertEquals(falseCount!=0,seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(trueCount,seq.size());
      for(int i=0;i<trueCount;++i){
        Assertions.assertTrue(seq.arr[i]);
      }
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
    }
  }
  @Test
  public void testUncheckedListremoveIf_BooleanPredicate_FirstValfalse_RemoveFalse(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.UncheckedList();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveFalsePredicate();
      Assertions.assertEquals(falseCount!=0,seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(trueCount,seq.size());
      for(int i=0;i<trueCount;++i){
        Assertions.assertTrue(seq.arr[i]);
      }
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
    }
  }
  @Test
  public void testUncheckedListremoveIf_BooleanPredicate_FirstValtrue_RemoveAll(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.UncheckedList();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveAllPredicate();
      Assertions.assertTrue(seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(0,seq.size());
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
    }
  }
  @Test
  public void testUncheckedListremoveIf_BooleanPredicate_FirstValfalse_RemoveAll(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.UncheckedList();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveAllPredicate();
      Assertions.assertTrue(seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(0,seq.size());
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
    }
  }
  @Test
  public void testUncheckedListremoveIf_BooleanPredicate_FirstValtrue_RemoveNone(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.UncheckedList();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveNonePredicate();
      Assertions.assertFalse(seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(10,seq.size());
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      currVal=true;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
    }
  }
  @Test
  public void testUncheckedListremoveIf_BooleanPredicate_FirstValfalse_RemoveNone(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.UncheckedList();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveNonePredicate();
      Assertions.assertFalse(seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(10,seq.size());
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      currVal=false;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
    }
  }
  @Test
  public void testUncheckedListremoveIf_BooleanPredicate_SeqIsEmpty_RemoveAllPredicate()
  {
    var seq=new BooleanArrSeq.UncheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveAllPredicate(seq);
    Assertions.assertFalse(seq.removeIf((BooleanPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_BooleanPredicate_SeqIsEmpty_RemoveNonePredicate()
  {
    var seq=new BooleanArrSeq.UncheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveNonePredicate(seq);
    Assertions.assertFalse(seq.removeIf((BooleanPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedListadd_boolean_initialCapacityDEFAULT(){
    var seq=new BooleanArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_boolean_initialCapacityNULL(){
    var seq=new BooleanArrSeq.UncheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_boolean_initialCapacity50(){
    var seq=new BooleanArrSeq.UncheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_Boolean_initialCapacityDEFAULT(){
    var seq=new BooleanArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_Boolean_initialCapacityNULL(){
    var seq=new BooleanArrSeq.UncheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_Boolean_initialCapacity50(){
    var seq=new BooleanArrSeq.UncheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListtoBooleanArray_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.UncheckedList();
    Assertions.assertSame(OmniArray.OfBoolean.DEFAULT_ARR,seq.toBooleanArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedListtoBooleanArray_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toBooleanArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.nextBoolean(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testUncheckedListtoArray_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.UncheckedList();
    Assertions.assertSame(OmniArray.OfBoolean.DEFAULT_BOXED_ARR,seq.toArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedListtoArray_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.next(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testUncheckedListtoDoubleArray_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.UncheckedList();
    Assertions.assertSame(OmniArray.OfDouble.DEFAULT_ARR,seq.toDoubleArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedListtoDoubleArray_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toDoubleArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.nextDouble(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testUncheckedListtoFloatArray_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.UncheckedList();
    Assertions.assertSame(OmniArray.OfFloat.DEFAULT_ARR,seq.toFloatArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedListtoFloatArray_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toFloatArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.nextFloat(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testUncheckedListtoLongArray_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.UncheckedList();
    Assertions.assertSame(OmniArray.OfLong.DEFAULT_ARR,seq.toLongArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedListtoLongArray_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toLongArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.nextLong(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testUncheckedListtoIntArray_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.UncheckedList();
    Assertions.assertSame(OmniArray.OfInt.DEFAULT_ARR,seq.toIntArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedListtoIntArray_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toIntArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.nextInt(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testUncheckedListtoShortArray_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.UncheckedList();
    Assertions.assertSame(OmniArray.OfShort.DEFAULT_ARR,seq.toShortArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedListtoShortArray_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toShortArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.nextShort(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testUncheckedListtoCharArray_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.UncheckedList();
    Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,seq.toCharArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedListtoCharArray_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toCharArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.nextChar(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testUncheckedListtoByteArray_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.UncheckedList();
    Assertions.assertSame(OmniArray.OfByte.DEFAULT_ARR,seq.toByteArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedListtoByteArray_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toByteArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.nextByte(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testCheckedStackconstructor_void_initialCapacityDEFAULT(){
    var seq=new BooleanArrSeq.CheckedStack();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertSame(OmniArray.OfBoolean.DEFAULT_ARR,seq.arr);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackconstructor_intboolean_initialCapacityNULL(){
    var seq=new BooleanArrSeq.CheckedStack(0,null);
    Assertions.assertEquals(0,seq.size);
    Assertions.assertNull(seq.arr);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackconstructor_int_initialCapacity50(){
    var seq=new BooleanArrSeq.CheckedStack(50);
    Assertions.assertEquals(0,seq.size);
    switch(50){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfBoolean.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(50,seq.arr.length);
    }
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackconstructor_int_initialCapacity0(){
    var seq=new BooleanArrSeq.CheckedStack(0);
    Assertions.assertEquals(0,seq.size);
    switch(0){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfBoolean.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(0,seq.arr.length);
    }
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackconstructor_int_initialCapacity10(){
    var seq=new BooleanArrSeq.CheckedStack(10);
    Assertions.assertEquals(0,seq.size);
    switch(10){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfBoolean.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(10,seq.arr.length);
    }
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackClone_initialCapacityDEFAULT_seqIsEmpty()
  {
    var seq=new BooleanArrSeq.CheckedStack();
    var clone=(BooleanArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfBoolean.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackClone_initialCapacityDEFAULT_seqIsNotEmpty()
  {
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var clone=(BooleanArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedStackClone_initialCapacityNULL_seqIsEmpty()
  {
    var seq=new BooleanArrSeq.CheckedStack(0,null);
    var clone=(BooleanArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfBoolean.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackClone_initialCapacityNULL_seqIsNotEmpty()
  {
    var seq=new BooleanArrSeq.CheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var clone=(BooleanArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedStackClone_initialCapacity50_seqIsEmpty()
  {
    var seq=new BooleanArrSeq.CheckedStack(50);
    var clone=(BooleanArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfBoolean.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackClone_initialCapacity50_seqIsNotEmpty()
  {
    var seq=new BooleanArrSeq.CheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var clone=(BooleanArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedStackClone_initialCapacity0_seqIsEmpty()
  {
    var seq=new BooleanArrSeq.CheckedStack(0);
    var clone=(BooleanArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfBoolean.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackClone_initialCapacity0_seqIsNotEmpty()
  {
    var seq=new BooleanArrSeq.CheckedStack(0);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var clone=(BooleanArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedStackClone_initialCapacity10_seqIsEmpty()
  {
    var seq=new BooleanArrSeq.CheckedStack(10);
    var clone=(BooleanArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfBoolean.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackClone_initialCapacity10_seqIsNotEmpty()
  {
    var seq=new BooleanArrSeq.CheckedStack(10);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var clone=(BooleanArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedStacktoArray_ObjectArray_zeroLengthArrayAndSequenceIsEmpty(){
    var seq=new BooleanArrSeq.CheckedStack();
    Boolean[] paramArr=new Boolean[0];
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertEquals(0,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
  }
  @Test
  public void testCheckedStacktoArray_ObjectArray_zeroLengthArrayAndSequenceNotEmpty(){
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Boolean[] paramArr=new Boolean[0];
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(100,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertNotSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.next(),result[i]);
    }
  }
  @Test
  public void testCheckedStacktoArray_ObjectArray_nonzeroLengthArrayAndSequenceIsEmpty(){
    var seq=new BooleanArrSeq.CheckedStack();
    Boolean[] paramArr=new Boolean[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToboolean(paramArr.length);
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertEquals(5,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    Assertions.assertNull(result[0]);
    for(int i=1;i<result.length;++i){
      Assertions.assertEquals((Object)TypeConversionUtil.convertToboolean(paramArr.length),result[i]);
    }
  }
  @Test
  public void testCheckedStacktoArray_ObjectArray_overSizedArray(){
    var seq=new BooleanArrSeq.CheckedStack();
    Boolean[] paramArr=new Boolean[10];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToboolean(paramArr.length);
    }
    for(int i=0;i<5;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(5,seq.size());
    Assertions.assertEquals(5,seq.modCount);
    Assertions.assertEquals(10,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<5;++i){
      Assertions.assertEquals(itr.next(),result[i]);
    }
    Assertions.assertNull(result[5]);
    for(int i=6;i<result.length;++i){
      Assertions.assertEquals((Object)TypeConversionUtil.convertToboolean(paramArr.length),result[i]);
    }
  }
  @Test
  public void testCheckedStacktoArray_ObjectArray_undersizedArray(){
    var seq=new BooleanArrSeq.CheckedStack();
    Boolean[] paramArr=new Boolean[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToboolean(10);
    }
    for(int i=0;i<10;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(10,seq.size());
    Assertions.assertEquals(10,seq.modCount);
    Assertions.assertEquals(10,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertNotSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<10;++i){
      Assertions.assertEquals(itr.next(),result[i]);
    }
  }
  @Test
  public void testCheckedStacktoArray_ObjectArray_exactSizeArray(){
    var seq=new BooleanArrSeq.CheckedStack();
    Boolean[] paramArr=new Boolean[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToboolean(5);
    }
    for(int i=0;i<5;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(5,seq.size());
    Assertions.assertEquals(5,seq.modCount);
    Assertions.assertEquals(5,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<5;++i){
      Assertions.assertEquals(itr.next(),result[i]);
    }
  }
  @Test
  public void testCheckedStacktoArray_IntFunction_seqIsEmpty_nonMod(){
    var seq=new BooleanArrSeq.CheckedStack();
    IntFunction<Boolean[]> arrConstructor=Boolean[]::new;
    var result=seq.toArray(arrConstructor);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertEquals(0,result.length);
  }
  @Test
  public void testCheckedStacktoArray_IntFunction_seqIsNotEmpty_nonMod(){
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    IntFunction<Boolean[]> arrConstructor=Boolean[]::new;
    var result=seq.toArray(arrConstructor);
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.next(),result[i]);
    }
  }
  @Test
  public void testCheckedStacktoArray_IntFunction_seqIsEmpty_moddingArrayConstructor(){
    var seq=new BooleanArrSeq.CheckedStack();
    IntFunction<Boolean[]> arrConstructor=(int arrSize)->{
      seq.add(TypeConversionUtil.convertToboolean(arrSize));
      return new Boolean[arrSize];
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(1,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertToboolean(0),seq.arr[0]);
    Assertions.assertEquals(1,seq.modCount);
  }
  @Test
  public void testCheckedStacktoArray_IntFunction_seqIsNotEmpty_moddingArrayConstructor(){
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    IntFunction<Boolean[]> arrConstructor=(int arrSize)->{
      seq.add(TypeConversionUtil.convertToboolean(arrSize));
      return new Boolean[arrSize];
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(101,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertToboolean(100),seq.arr[100]);
    Assertions.assertEquals(101,seq.modCount);
  }
  @Test
  public void testCheckedStacktoArray_IntFunction_seqIsEmpty_throwingArrayConstructor(){
    var seq=new BooleanArrSeq.CheckedStack();
    IntFunction<Boolean[]> arrConstructor=(int arrSize)->{
      throw new IndexOutOfBoundsException();
    };
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStacktoArray_IntFunction_seqIsNotEmpty_throwingArrayConstructor(){
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    IntFunction<Boolean[]> arrConstructor=(int arrSize)->{
      throw new IndexOutOfBoundsException();
    };
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedStacktoArray_IntFunction_seqIsEmpty_throwingAndModdingArrConstructor(){
    var seq=new BooleanArrSeq.CheckedStack();
    IntFunction<Boolean[]> arrConstructor=(int arrSize)->{
      seq.add(TypeConversionUtil.convertToboolean(arrSize));
      throw new IndexOutOfBoundsException();
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(1,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertToboolean(0),seq.arr[0]);
    Assertions.assertEquals(1,seq.modCount);
  }
  @Test
  public void testCheckedStacktoArray_IntFunction_seqIsNotEmpty_throwingAndModdingArrConstructor(){
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    IntFunction<Boolean[]> arrConstructor=(int arrSize)->{
      seq.add(TypeConversionUtil.convertToboolean(arrSize));
      throw new IndexOutOfBoundsException();
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(101,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertToboolean(100),seq.arr[100]);
    Assertions.assertEquals(101,seq.modCount);
  }
  @Test
  public void testCheckedStackclear_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.CheckedStack();
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackclear_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
    for(int i=0;i<100;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
    }
    Assertions.assertEquals(101,seq.modCount);
  }
  @Test
  public void testCheckedStackisEmpty_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.CheckedStack();
    Assertions.assertTrue(seq.isEmpty());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackisEmpty_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertFalse(seq.isEmpty());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedStackisEmpty_void_seqIsBeingCleared(){
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;){
      Assertions.assertFalse(seq.isEmpty());
      itr.nextBoolean();
      itr.remove();
      Assertions.assertEquals(100+(100-i),seq.modCount);
    }
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testCheckedStacksize_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.CheckedStack();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStacksize_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedStacksize_void_seqIsBeingCleared(){
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;){
      itr.nextBoolean();
      itr.remove();
      Assertions.assertEquals(i,seq.size());
      Assertions.assertEquals(100+(100-i),seq.modCount);
    }
  }
  @Test
  public void testCheckedStackforEach_Consumer_SeqIsEmpty_NoMod(){
    var seq=new BooleanArrSeq.CheckedStack();
    var consumer=new BooleanMonitoredConsumer();
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedStackforEach_Consumer_SeqIsNotEmpty_NoMod(){
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var consumer=new BooleanMonitoredConsumer();
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(100,consumer.size());
    var seqIterator=seq.iterator();
    var consumerIterator=consumer.iterator();
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(consumerIterator.next(),seqIterator.next());
    }
  }
  @Test
  public void testCheckedStackforEach_Consumer_SeqIsEmpty_ModdingConsumer(){
    var seq=new BooleanArrSeq.CheckedStack();
    var consumer=new BooleanMonitoredConsumer.ModifyingArrSeqCheckedStackConsumer(seq);
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedStackforEach_Consumer_SeqIsNotEmpty_ModdingConsumer(){
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var consumer=new BooleanMonitoredConsumer.ModifyingArrSeqCheckedStackConsumer(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.forEach((Consumer)consumer));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(300,seq.modCount);
    Assertions.assertEquals(100,consumer.size());
    var seqIterator=seq.iterator();
    var consumerIterator=consumer.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(consumerIterator.next(),seqIterator.next());
    }
  }
  @Test
  public void testCheckedStackforEach_Consumer_SeqIsEmpty_ThrowingConsumer(){
    var seq=new BooleanArrSeq.CheckedStack();
    var consumer=new BooleanMonitoredConsumer.Throwing();
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedStackforEach_Consumer_SeqIsNotEmpty_ThrowingConsumer(){
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var consumer=new BooleanMonitoredConsumer.Throwing();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.forEach((Consumer)consumer));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(1,consumer.size());
    var seqIterator=seq.iterator();
    var consumerIterator=consumer.iterator();
    for(int i=0;i<1;++i){
      Assertions.assertEquals(consumerIterator.next(),seqIterator.next());
    }
  }
  @Test
  public void testCheckedStackforEach_Consumer_SeqIsEmpty_ThrowingAndModdingConsumer(){
    var seq=new BooleanArrSeq.CheckedStack();
    var consumer=new BooleanMonitoredConsumer.ModifyingArrSeqCheckedStackAndThrowingConsumer(seq);
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedStackforEach_Consumer_SeqIsNotEmpty_ThrowingAndModdingConsumer(){
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var consumer=new BooleanMonitoredConsumer.ModifyingArrSeqCheckedStackAndThrowingConsumer(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.forEach((Consumer)consumer));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(102,seq.modCount);
    Assertions.assertEquals(1,consumer.size());
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveTruePredicate()
  {
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveTruePredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveFalsePredicate()
  {
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveFalsePredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_FirstValtrue_RemoveTrue(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveTruePredicate();
      Assertions.assertEquals(trueCount!=0,seq.removeIf((Predicate)filter));
      Assertions.assertEquals(falseCount,seq.size());
      for(int i=0;i<falseCount;++i){
        Assertions.assertFalse(seq.arr[i]);
      }
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      Assertions.assertEquals(trueCount==0?10:11,seq.modCount);
    }
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_FirstValfalse_RemoveTrue(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveTruePredicate();
      Assertions.assertEquals(trueCount!=0,seq.removeIf((Predicate)filter));
      Assertions.assertEquals(falseCount,seq.size());
      for(int i=0;i<falseCount;++i){
        Assertions.assertFalse(seq.arr[i]);
      }
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      Assertions.assertEquals(trueCount==0?10:11,seq.modCount);
    }
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_FirstValtrue_RemoveFalse(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveFalsePredicate();
      Assertions.assertEquals(falseCount!=0,seq.removeIf((Predicate)filter));
      Assertions.assertEquals(trueCount,seq.size());
      for(int i=0;i<trueCount;++i){
        Assertions.assertTrue(seq.arr[i]);
      }
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      Assertions.assertEquals(falseCount==0?10:11,seq.modCount);
    }
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_FirstValfalse_RemoveFalse(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveFalsePredicate();
      Assertions.assertEquals(falseCount!=0,seq.removeIf((Predicate)filter));
      Assertions.assertEquals(trueCount,seq.size());
      for(int i=0;i<trueCount;++i){
        Assertions.assertTrue(seq.arr[i]);
      }
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      Assertions.assertEquals(falseCount==0?10:11,seq.modCount);
    }
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_FirstValtrue_RemoveAll(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveAllPredicate();
      Assertions.assertTrue(seq.removeIf((Predicate)filter));
      Assertions.assertEquals(0,seq.size());
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      Assertions.assertEquals(11,seq.modCount);
    }
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_FirstValfalse_RemoveAll(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveAllPredicate();
      Assertions.assertTrue(seq.removeIf((Predicate)filter));
      Assertions.assertEquals(0,seq.size());
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      Assertions.assertEquals(11,seq.modCount);
    }
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_FirstValtrue_RemoveNone(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveNonePredicate();
      Assertions.assertFalse(seq.removeIf((Predicate)filter));
      Assertions.assertEquals(10,seq.size());
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      currVal=true;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      Assertions.assertEquals(10,seq.modCount);
    }
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_FirstValfalse_RemoveNone(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveNonePredicate();
      Assertions.assertFalse(seq.removeIf((Predicate)filter));
      Assertions.assertEquals(10,seq.size());
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      currVal=false;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      Assertions.assertEquals(10,seq.modCount);
    }
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_FirstValtrue_RemoveTrueArrSeqCheckedStackModifyingPredicate(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveTrueArrSeqCheckedStackModifyingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=true;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      if(falseCount!=10 && trueCount!=10){
        Assertions.assertEquals(10+4,seq.modCount);
        Assertions.assertEquals(2,filter.callCount);
      }else{
        Assertions.assertEquals(10+2,seq.modCount);
        Assertions.assertEquals(1,filter.callCount);
      }
    }
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_FirstValtrue_RemoveFalseArrSeqCheckedStackModifyingPredicate(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveFalseArrSeqCheckedStackModifyingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=true;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      if(falseCount!=10 && trueCount!=10){
        Assertions.assertEquals(10+4,seq.modCount);
        Assertions.assertEquals(2,filter.callCount);
      }else{
        Assertions.assertEquals(10+2,seq.modCount);
        Assertions.assertEquals(1,filter.callCount);
      }
    }
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_FirstValfalse_RemoveTrueArrSeqCheckedStackModifyingPredicate(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveTrueArrSeqCheckedStackModifyingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=false;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      if(falseCount!=10 && trueCount!=10){
        Assertions.assertEquals(10+4,seq.modCount);
        Assertions.assertEquals(2,filter.callCount);
      }else{
        Assertions.assertEquals(10+2,seq.modCount);
        Assertions.assertEquals(1,filter.callCount);
      }
    }
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_FirstValfalse_RemoveFalseArrSeqCheckedStackModifyingPredicate(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveFalseArrSeqCheckedStackModifyingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=false;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      if(falseCount!=10 && trueCount!=10){
        Assertions.assertEquals(10+4,seq.modCount);
        Assertions.assertEquals(2,filter.callCount);
      }else{
        Assertions.assertEquals(10+2,seq.modCount);
        Assertions.assertEquals(1,filter.callCount);
      }
    }
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_FirstValtrue_RemoveNoneArrSeqCheckedStackModifyingPredicate(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveNoneArrSeqCheckedStackModifyingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=true;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      if(falseCount!=10 && trueCount!=10){
        Assertions.assertEquals(10+4,seq.modCount);
        Assertions.assertEquals(2,filter.callCount);
      }else{
        Assertions.assertEquals(10+2,seq.modCount);
        Assertions.assertEquals(1,filter.callCount);
      }
    }
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_FirstValtrue_RemoveAllArrSeqCheckedStackModifyingPredicate(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveAllArrSeqCheckedStackModifyingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=true;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      if(falseCount!=10 && trueCount!=10){
        Assertions.assertEquals(10+4,seq.modCount);
        Assertions.assertEquals(2,filter.callCount);
      }else{
        Assertions.assertEquals(10+2,seq.modCount);
        Assertions.assertEquals(1,filter.callCount);
      }
    }
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_FirstValfalse_RemoveNoneArrSeqCheckedStackModifyingPredicate(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveNoneArrSeqCheckedStackModifyingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=false;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      if(falseCount!=10 && trueCount!=10){
        Assertions.assertEquals(10+4,seq.modCount);
        Assertions.assertEquals(2,filter.callCount);
      }else{
        Assertions.assertEquals(10+2,seq.modCount);
        Assertions.assertEquals(1,filter.callCount);
      }
    }
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_FirstValfalse_RemoveAllArrSeqCheckedStackModifyingPredicate(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveAllArrSeqCheckedStackModifyingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=false;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      if(falseCount!=10 && trueCount!=10){
        Assertions.assertEquals(10+4,seq.modCount);
        Assertions.assertEquals(2,filter.callCount);
      }else{
        Assertions.assertEquals(10+2,seq.modCount);
        Assertions.assertEquals(1,filter.callCount);
      }
    }
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_FirstValtrue_Throwing(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.ThrowingPredicate();
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIf((Predicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=true;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      Assertions.assertEquals(10,seq.modCount);
      Assertions.assertEquals(1,filter.callCount);
    }
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_FirstValfalse_Throwing(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.ThrowingPredicate();
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIf((Predicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=false;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      Assertions.assertEquals(10,seq.modCount);
      Assertions.assertEquals(1,filter.callCount);
    }
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_FirstValfalse_ThrowAndMod(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.ModifyingArrSeqCheckedStackAndThrowingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
      Assertions.assertEquals(11,seq.size());
      currVal=false;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      Assertions.assertEquals(false,seq.arr[10]);
      Assertions.assertEquals(10+1,seq.modCount);
      Assertions.assertEquals(1,filter.callCount);
    }
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_FirstValtrue_ThrowAndMod(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.ModifyingArrSeqCheckedStackAndThrowingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
      Assertions.assertEquals(11,seq.size());
      currVal=true;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      Assertions.assertEquals(true,seq.arr[10]);
      Assertions.assertEquals(10+1,seq.modCount);
      Assertions.assertEquals(1,filter.callCount);
    }
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveTrueArrSeqCheckedStackModifyingPredicate()
  {
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveTrueArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveFalseArrSeqCheckedStackModifyingPredicate()
  {
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveFalseArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveAllPredicate()
  {
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveAllPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveNonePredicate()
  {
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveNonePredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveAllArrSeqCheckedStackModifyingPredicate()
  {
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveAllArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveNoneArrSeqCheckedStackModifyingPredicate()
  {
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveNoneArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_ThrowingPredicate()
  {
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.ThrowingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_ModifyingArrSeqCheckedStackAndThrowingPredicate()
  {
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.ModifyingArrSeqCheckedStackAndThrowingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackforEach_BooleanConsumer_SeqIsEmpty_NoMod(){
    var seq=new BooleanArrSeq.CheckedStack();
    var consumer=new BooleanMonitoredConsumer();
    seq.forEach((BooleanConsumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedStackforEach_BooleanConsumer_SeqIsNotEmpty_NoMod(){
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var consumer=new BooleanMonitoredConsumer();
    seq.forEach((BooleanConsumer)consumer);
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(100,consumer.size());
    var seqIterator=seq.iterator();
    var consumerIterator=consumer.iterator();
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(consumerIterator.next(),seqIterator.next());
    }
  }
  @Test
  public void testCheckedStackforEach_BooleanConsumer_SeqIsEmpty_ModdingConsumer(){
    var seq=new BooleanArrSeq.CheckedStack();
    var consumer=new BooleanMonitoredConsumer.ModifyingArrSeqCheckedStackConsumer(seq);
    seq.forEach((BooleanConsumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedStackforEach_BooleanConsumer_SeqIsNotEmpty_ModdingConsumer(){
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var consumer=new BooleanMonitoredConsumer.ModifyingArrSeqCheckedStackConsumer(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.forEach((BooleanConsumer)consumer));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(300,seq.modCount);
    Assertions.assertEquals(100,consumer.size());
    var seqIterator=seq.iterator();
    var consumerIterator=consumer.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(consumerIterator.next(),seqIterator.next());
    }
  }
  @Test
  public void testCheckedStackforEach_BooleanConsumer_SeqIsEmpty_ThrowingConsumer(){
    var seq=new BooleanArrSeq.CheckedStack();
    var consumer=new BooleanMonitoredConsumer.Throwing();
    seq.forEach((BooleanConsumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedStackforEach_BooleanConsumer_SeqIsNotEmpty_ThrowingConsumer(){
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var consumer=new BooleanMonitoredConsumer.Throwing();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.forEach((BooleanConsumer)consumer));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(1,consumer.size());
    var seqIterator=seq.iterator();
    var consumerIterator=consumer.iterator();
    for(int i=0;i<1;++i){
      Assertions.assertEquals(consumerIterator.next(),seqIterator.next());
    }
  }
  @Test
  public void testCheckedStackforEach_BooleanConsumer_SeqIsEmpty_ThrowingAndModdingConsumer(){
    var seq=new BooleanArrSeq.CheckedStack();
    var consumer=new BooleanMonitoredConsumer.ModifyingArrSeqCheckedStackAndThrowingConsumer(seq);
    seq.forEach((BooleanConsumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedStackforEach_BooleanConsumer_SeqIsNotEmpty_ThrowingAndModdingConsumer(){
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var consumer=new BooleanMonitoredConsumer.ModifyingArrSeqCheckedStackAndThrowingConsumer(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.forEach((BooleanConsumer)consumer));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(102,seq.modCount);
    Assertions.assertEquals(1,consumer.size());
  }
  @Test
  public void testCheckedStackremoveIf_BooleanPredicate_SeqIsEmpty_RemoveTruePredicate()
  {
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveTruePredicate(seq);
    Assertions.assertFalse(seq.removeIf((BooleanPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_BooleanPredicate_SeqIsEmpty_RemoveFalsePredicate()
  {
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveFalsePredicate(seq);
    Assertions.assertFalse(seq.removeIf((BooleanPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_BooleanPredicate_FirstValtrue_RemoveTrue(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveTruePredicate();
      Assertions.assertEquals(trueCount!=0,seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(falseCount,seq.size());
      for(int i=0;i<falseCount;++i){
        Assertions.assertFalse(seq.arr[i]);
      }
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      Assertions.assertEquals(trueCount==0?10:11,seq.modCount);
    }
  }
  @Test
  public void testCheckedStackremoveIf_BooleanPredicate_FirstValfalse_RemoveTrue(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveTruePredicate();
      Assertions.assertEquals(trueCount!=0,seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(falseCount,seq.size());
      for(int i=0;i<falseCount;++i){
        Assertions.assertFalse(seq.arr[i]);
      }
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      Assertions.assertEquals(trueCount==0?10:11,seq.modCount);
    }
  }
  @Test
  public void testCheckedStackremoveIf_BooleanPredicate_FirstValtrue_RemoveFalse(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveFalsePredicate();
      Assertions.assertEquals(falseCount!=0,seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(trueCount,seq.size());
      for(int i=0;i<trueCount;++i){
        Assertions.assertTrue(seq.arr[i]);
      }
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      Assertions.assertEquals(falseCount==0?10:11,seq.modCount);
    }
  }
  @Test
  public void testCheckedStackremoveIf_BooleanPredicate_FirstValfalse_RemoveFalse(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveFalsePredicate();
      Assertions.assertEquals(falseCount!=0,seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(trueCount,seq.size());
      for(int i=0;i<trueCount;++i){
        Assertions.assertTrue(seq.arr[i]);
      }
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      Assertions.assertEquals(falseCount==0?10:11,seq.modCount);
    }
  }
  @Test
  public void testCheckedStackremoveIf_BooleanPredicate_FirstValtrue_RemoveAll(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveAllPredicate();
      Assertions.assertTrue(seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(0,seq.size());
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      Assertions.assertEquals(11,seq.modCount);
    }
  }
  @Test
  public void testCheckedStackremoveIf_BooleanPredicate_FirstValfalse_RemoveAll(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveAllPredicate();
      Assertions.assertTrue(seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(0,seq.size());
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      Assertions.assertEquals(11,seq.modCount);
    }
  }
  @Test
  public void testCheckedStackremoveIf_BooleanPredicate_FirstValtrue_RemoveNone(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveNonePredicate();
      Assertions.assertFalse(seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(10,seq.size());
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      currVal=true;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      Assertions.assertEquals(10,seq.modCount);
    }
  }
  @Test
  public void testCheckedStackremoveIf_BooleanPredicate_FirstValfalse_RemoveNone(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveNonePredicate();
      Assertions.assertFalse(seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(10,seq.size());
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      currVal=false;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      Assertions.assertEquals(10,seq.modCount);
    }
  }
  @Test
  public void testCheckedStackremoveIf_BooleanPredicate_FirstValtrue_RemoveTrueArrSeqCheckedStackModifyingPredicate(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveTrueArrSeqCheckedStackModifyingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=true;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      if(falseCount!=10 && trueCount!=10){
        Assertions.assertEquals(10+4,seq.modCount);
        Assertions.assertEquals(2,filter.callCount);
      }else{
        Assertions.assertEquals(10+2,seq.modCount);
        Assertions.assertEquals(1,filter.callCount);
      }
    }
  }
  @Test
  public void testCheckedStackremoveIf_BooleanPredicate_FirstValtrue_RemoveFalseArrSeqCheckedStackModifyingPredicate(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveFalseArrSeqCheckedStackModifyingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=true;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      if(falseCount!=10 && trueCount!=10){
        Assertions.assertEquals(10+4,seq.modCount);
        Assertions.assertEquals(2,filter.callCount);
      }else{
        Assertions.assertEquals(10+2,seq.modCount);
        Assertions.assertEquals(1,filter.callCount);
      }
    }
  }
  @Test
  public void testCheckedStackremoveIf_BooleanPredicate_FirstValfalse_RemoveTrueArrSeqCheckedStackModifyingPredicate(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveTrueArrSeqCheckedStackModifyingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=false;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      if(falseCount!=10 && trueCount!=10){
        Assertions.assertEquals(10+4,seq.modCount);
        Assertions.assertEquals(2,filter.callCount);
      }else{
        Assertions.assertEquals(10+2,seq.modCount);
        Assertions.assertEquals(1,filter.callCount);
      }
    }
  }
  @Test
  public void testCheckedStackremoveIf_BooleanPredicate_FirstValfalse_RemoveFalseArrSeqCheckedStackModifyingPredicate(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveFalseArrSeqCheckedStackModifyingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=false;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      if(falseCount!=10 && trueCount!=10){
        Assertions.assertEquals(10+4,seq.modCount);
        Assertions.assertEquals(2,filter.callCount);
      }else{
        Assertions.assertEquals(10+2,seq.modCount);
        Assertions.assertEquals(1,filter.callCount);
      }
    }
  }
  @Test
  public void testCheckedStackremoveIf_BooleanPredicate_FirstValtrue_RemoveNoneArrSeqCheckedStackModifyingPredicate(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveNoneArrSeqCheckedStackModifyingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=true;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      if(falseCount!=10 && trueCount!=10){
        Assertions.assertEquals(10+4,seq.modCount);
        Assertions.assertEquals(2,filter.callCount);
      }else{
        Assertions.assertEquals(10+2,seq.modCount);
        Assertions.assertEquals(1,filter.callCount);
      }
    }
  }
  @Test
  public void testCheckedStackremoveIf_BooleanPredicate_FirstValtrue_RemoveAllArrSeqCheckedStackModifyingPredicate(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveAllArrSeqCheckedStackModifyingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=true;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      if(falseCount!=10 && trueCount!=10){
        Assertions.assertEquals(10+4,seq.modCount);
        Assertions.assertEquals(2,filter.callCount);
      }else{
        Assertions.assertEquals(10+2,seq.modCount);
        Assertions.assertEquals(1,filter.callCount);
      }
    }
  }
  @Test
  public void testCheckedStackremoveIf_BooleanPredicate_FirstValfalse_RemoveNoneArrSeqCheckedStackModifyingPredicate(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveNoneArrSeqCheckedStackModifyingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=false;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      if(falseCount!=10 && trueCount!=10){
        Assertions.assertEquals(10+4,seq.modCount);
        Assertions.assertEquals(2,filter.callCount);
      }else{
        Assertions.assertEquals(10+2,seq.modCount);
        Assertions.assertEquals(1,filter.callCount);
      }
    }
  }
  @Test
  public void testCheckedStackremoveIf_BooleanPredicate_FirstValfalse_RemoveAllArrSeqCheckedStackModifyingPredicate(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveAllArrSeqCheckedStackModifyingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=false;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      if(falseCount!=10 && trueCount!=10){
        Assertions.assertEquals(10+4,seq.modCount);
        Assertions.assertEquals(2,filter.callCount);
      }else{
        Assertions.assertEquals(10+2,seq.modCount);
        Assertions.assertEquals(1,filter.callCount);
      }
    }
  }
  @Test
  public void testCheckedStackremoveIf_BooleanPredicate_FirstValtrue_Throwing(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.ThrowingPredicate();
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=true;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      Assertions.assertEquals(10,seq.modCount);
      Assertions.assertEquals(1,filter.callCount);
    }
  }
  @Test
  public void testCheckedStackremoveIf_BooleanPredicate_FirstValfalse_Throwing(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.ThrowingPredicate();
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=false;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      Assertions.assertEquals(10,seq.modCount);
      Assertions.assertEquals(1,filter.callCount);
    }
  }
  @Test
  public void testCheckedStackremoveIf_BooleanPredicate_FirstValfalse_ThrowAndMod(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.ModifyingArrSeqCheckedStackAndThrowingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(11,seq.size());
      currVal=false;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      Assertions.assertEquals(false,seq.arr[10]);
      Assertions.assertEquals(10+1,seq.modCount);
      Assertions.assertEquals(1,filter.callCount);
    }
  }
  @Test
  public void testCheckedStackremoveIf_BooleanPredicate_FirstValtrue_ThrowAndMod(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedStack();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.ModifyingArrSeqCheckedStackAndThrowingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(11,seq.size());
      currVal=true;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      Assertions.assertEquals(true,seq.arr[10]);
      Assertions.assertEquals(10+1,seq.modCount);
      Assertions.assertEquals(1,filter.callCount);
    }
  }
  @Test
  public void testCheckedStackremoveIf_BooleanPredicate_SeqIsEmpty_RemoveTrueArrSeqCheckedStackModifyingPredicate()
  {
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveTrueArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((BooleanPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_BooleanPredicate_SeqIsEmpty_RemoveFalseArrSeqCheckedStackModifyingPredicate()
  {
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveFalseArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((BooleanPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_BooleanPredicate_SeqIsEmpty_RemoveAllPredicate()
  {
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveAllPredicate(seq);
    Assertions.assertFalse(seq.removeIf((BooleanPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_BooleanPredicate_SeqIsEmpty_RemoveNonePredicate()
  {
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveNonePredicate(seq);
    Assertions.assertFalse(seq.removeIf((BooleanPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_BooleanPredicate_SeqIsEmpty_RemoveAllArrSeqCheckedStackModifyingPredicate()
  {
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveAllArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((BooleanPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_BooleanPredicate_SeqIsEmpty_RemoveNoneArrSeqCheckedStackModifyingPredicate()
  {
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveNoneArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((BooleanPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_BooleanPredicate_SeqIsEmpty_ThrowingPredicate()
  {
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.ThrowingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((BooleanPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_BooleanPredicate_SeqIsEmpty_ModifyingArrSeqCheckedStackAndThrowingPredicate()
  {
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.ModifyingArrSeqCheckedStackAndThrowingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((BooleanPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackadd_boolean_initialCapacityDEFAULT(){
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_boolean_initialCapacityNULL(){
    var seq=new BooleanArrSeq.CheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_boolean_initialCapacity50(){
    var seq=new BooleanArrSeq.CheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_Boolean_initialCapacityDEFAULT(){
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_Boolean_initialCapacityNULL(){
    var seq=new BooleanArrSeq.CheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_Boolean_initialCapacity50(){
    var seq=new BooleanArrSeq.CheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStacktoBooleanArray_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.CheckedStack();
    Assertions.assertSame(OmniArray.OfBoolean.DEFAULT_ARR,seq.toBooleanArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStacktoBooleanArray_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toBooleanArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.nextBoolean(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testCheckedStacktoArray_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.CheckedStack();
    Assertions.assertSame(OmniArray.OfBoolean.DEFAULT_BOXED_ARR,seq.toArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStacktoArray_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.next(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testCheckedStacktoDoubleArray_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.CheckedStack();
    Assertions.assertSame(OmniArray.OfDouble.DEFAULT_ARR,seq.toDoubleArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStacktoDoubleArray_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toDoubleArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.nextDouble(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testCheckedStacktoFloatArray_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.CheckedStack();
    Assertions.assertSame(OmniArray.OfFloat.DEFAULT_ARR,seq.toFloatArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStacktoFloatArray_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toFloatArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.nextFloat(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testCheckedStacktoLongArray_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.CheckedStack();
    Assertions.assertSame(OmniArray.OfLong.DEFAULT_ARR,seq.toLongArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStacktoLongArray_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toLongArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.nextLong(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testCheckedStacktoIntArray_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.CheckedStack();
    Assertions.assertSame(OmniArray.OfInt.DEFAULT_ARR,seq.toIntArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStacktoIntArray_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toIntArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.nextInt(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testCheckedStacktoShortArray_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.CheckedStack();
    Assertions.assertSame(OmniArray.OfShort.DEFAULT_ARR,seq.toShortArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStacktoShortArray_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toShortArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.nextShort(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testCheckedStacktoCharArray_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.CheckedStack();
    Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,seq.toCharArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStacktoCharArray_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toCharArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.nextChar(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testCheckedStacktoByteArray_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.CheckedStack();
    Assertions.assertSame(OmniArray.OfByte.DEFAULT_ARR,seq.toByteArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStacktoByteArray_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toByteArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.nextByte(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testCheckedListconstructor_void_initialCapacityDEFAULT(){
    var seq=new BooleanArrSeq.CheckedList();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertSame(OmniArray.OfBoolean.DEFAULT_ARR,seq.arr);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListconstructor_intboolean_initialCapacityNULL(){
    var seq=new BooleanArrSeq.CheckedList(0,null);
    Assertions.assertEquals(0,seq.size);
    Assertions.assertNull(seq.arr);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListconstructor_int_initialCapacity50(){
    var seq=new BooleanArrSeq.CheckedList(50);
    Assertions.assertEquals(0,seq.size);
    switch(50){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfBoolean.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(50,seq.arr.length);
    }
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListconstructor_int_initialCapacity0(){
    var seq=new BooleanArrSeq.CheckedList(0);
    Assertions.assertEquals(0,seq.size);
    switch(0){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfBoolean.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(0,seq.arr.length);
    }
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListconstructor_int_initialCapacity10(){
    var seq=new BooleanArrSeq.CheckedList(10);
    Assertions.assertEquals(0,seq.size);
    switch(10){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfBoolean.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(10,seq.arr.length);
    }
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListClone_initialCapacityDEFAULT_seqIsEmpty()
  {
    var seq=new BooleanArrSeq.CheckedList();
    var clone=(BooleanArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfBoolean.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListClone_initialCapacityDEFAULT_seqIsNotEmpty()
  {
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var clone=(BooleanArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListClone_initialCapacityNULL_seqIsEmpty()
  {
    var seq=new BooleanArrSeq.CheckedList(0,null);
    var clone=(BooleanArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfBoolean.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListClone_initialCapacityNULL_seqIsNotEmpty()
  {
    var seq=new BooleanArrSeq.CheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var clone=(BooleanArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListClone_initialCapacity50_seqIsEmpty()
  {
    var seq=new BooleanArrSeq.CheckedList(50);
    var clone=(BooleanArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfBoolean.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListClone_initialCapacity50_seqIsNotEmpty()
  {
    var seq=new BooleanArrSeq.CheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var clone=(BooleanArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListClone_initialCapacity0_seqIsEmpty()
  {
    var seq=new BooleanArrSeq.CheckedList(0);
    var clone=(BooleanArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfBoolean.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListClone_initialCapacity0_seqIsNotEmpty()
  {
    var seq=new BooleanArrSeq.CheckedList(0);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var clone=(BooleanArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListClone_initialCapacity10_seqIsEmpty()
  {
    var seq=new BooleanArrSeq.CheckedList(10);
    var clone=(BooleanArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfBoolean.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListClone_initialCapacity10_seqIsNotEmpty()
  {
    var seq=new BooleanArrSeq.CheckedList(10);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var clone=(BooleanArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListtoArray_ObjectArray_zeroLengthArrayAndSequenceIsEmpty(){
    var seq=new BooleanArrSeq.CheckedList();
    Boolean[] paramArr=new Boolean[0];
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertEquals(0,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
  }
  @Test
  public void testCheckedListtoArray_ObjectArray_zeroLengthArrayAndSequenceNotEmpty(){
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Boolean[] paramArr=new Boolean[0];
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(100,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertNotSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.next(),result[i]);
    }
  }
  @Test
  public void testCheckedListtoArray_ObjectArray_nonzeroLengthArrayAndSequenceIsEmpty(){
    var seq=new BooleanArrSeq.CheckedList();
    Boolean[] paramArr=new Boolean[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToboolean(paramArr.length);
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertEquals(5,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    Assertions.assertNull(result[0]);
    for(int i=1;i<result.length;++i){
      Assertions.assertEquals((Object)TypeConversionUtil.convertToboolean(paramArr.length),result[i]);
    }
  }
  @Test
  public void testCheckedListtoArray_ObjectArray_overSizedArray(){
    var seq=new BooleanArrSeq.CheckedList();
    Boolean[] paramArr=new Boolean[10];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToboolean(paramArr.length);
    }
    for(int i=0;i<5;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(5,seq.size());
    Assertions.assertEquals(5,seq.modCount);
    Assertions.assertEquals(10,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<5;++i){
      Assertions.assertEquals(itr.next(),result[i]);
    }
    Assertions.assertNull(result[5]);
    for(int i=6;i<result.length;++i){
      Assertions.assertEquals((Object)TypeConversionUtil.convertToboolean(paramArr.length),result[i]);
    }
  }
  @Test
  public void testCheckedListtoArray_ObjectArray_undersizedArray(){
    var seq=new BooleanArrSeq.CheckedList();
    Boolean[] paramArr=new Boolean[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToboolean(10);
    }
    for(int i=0;i<10;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(10,seq.size());
    Assertions.assertEquals(10,seq.modCount);
    Assertions.assertEquals(10,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertNotSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<10;++i){
      Assertions.assertEquals(itr.next(),result[i]);
    }
  }
  @Test
  public void testCheckedListtoArray_ObjectArray_exactSizeArray(){
    var seq=new BooleanArrSeq.CheckedList();
    Boolean[] paramArr=new Boolean[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToboolean(5);
    }
    for(int i=0;i<5;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(5,seq.size());
    Assertions.assertEquals(5,seq.modCount);
    Assertions.assertEquals(5,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<5;++i){
      Assertions.assertEquals(itr.next(),result[i]);
    }
  }
  @Test
  public void testCheckedListtoArray_IntFunction_seqIsEmpty_nonMod(){
    var seq=new BooleanArrSeq.CheckedList();
    IntFunction<Boolean[]> arrConstructor=Boolean[]::new;
    var result=seq.toArray(arrConstructor);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertEquals(0,result.length);
  }
  @Test
  public void testCheckedListtoArray_IntFunction_seqIsNotEmpty_nonMod(){
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    IntFunction<Boolean[]> arrConstructor=Boolean[]::new;
    var result=seq.toArray(arrConstructor);
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.next(),result[i]);
    }
  }
  @Test
  public void testCheckedListtoArray_IntFunction_seqIsEmpty_moddingArrayConstructor(){
    var seq=new BooleanArrSeq.CheckedList();
    IntFunction<Boolean[]> arrConstructor=(int arrSize)->{
      seq.add(TypeConversionUtil.convertToboolean(arrSize));
      return new Boolean[arrSize];
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(1,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertToboolean(0),seq.arr[0]);
    Assertions.assertEquals(1,seq.modCount);
  }
  @Test
  public void testCheckedListtoArray_IntFunction_seqIsNotEmpty_moddingArrayConstructor(){
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    IntFunction<Boolean[]> arrConstructor=(int arrSize)->{
      seq.add(TypeConversionUtil.convertToboolean(arrSize));
      return new Boolean[arrSize];
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(101,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertToboolean(100),seq.arr[100]);
    Assertions.assertEquals(101,seq.modCount);
  }
  @Test
  public void testCheckedListtoArray_IntFunction_seqIsEmpty_throwingArrayConstructor(){
    var seq=new BooleanArrSeq.CheckedList();
    IntFunction<Boolean[]> arrConstructor=(int arrSize)->{
      throw new IndexOutOfBoundsException();
    };
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListtoArray_IntFunction_seqIsNotEmpty_throwingArrayConstructor(){
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    IntFunction<Boolean[]> arrConstructor=(int arrSize)->{
      throw new IndexOutOfBoundsException();
    };
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListtoArray_IntFunction_seqIsEmpty_throwingAndModdingArrConstructor(){
    var seq=new BooleanArrSeq.CheckedList();
    IntFunction<Boolean[]> arrConstructor=(int arrSize)->{
      seq.add(TypeConversionUtil.convertToboolean(arrSize));
      throw new IndexOutOfBoundsException();
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(1,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertToboolean(0),seq.arr[0]);
    Assertions.assertEquals(1,seq.modCount);
  }
  @Test
  public void testCheckedListtoArray_IntFunction_seqIsNotEmpty_throwingAndModdingArrConstructor(){
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    IntFunction<Boolean[]> arrConstructor=(int arrSize)->{
      seq.add(TypeConversionUtil.convertToboolean(arrSize));
      throw new IndexOutOfBoundsException();
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(101,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertToboolean(100),seq.arr[100]);
    Assertions.assertEquals(101,seq.modCount);
  }
  @Test
  public void testCheckedListclear_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.CheckedList();
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListclear_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
    for(int i=0;i<100;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
    }
    Assertions.assertEquals(101,seq.modCount);
  }
  @Test
  public void testCheckedListisEmpty_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.CheckedList();
    Assertions.assertTrue(seq.isEmpty());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListisEmpty_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertFalse(seq.isEmpty());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListisEmpty_void_seqIsBeingCleared(){
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;){
      Assertions.assertFalse(seq.isEmpty());
      itr.nextBoolean();
      itr.remove();
      Assertions.assertEquals(100+(100-i),seq.modCount);
    }
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testCheckedListsize_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.CheckedList();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListsize_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListsize_void_seqIsBeingCleared(){
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;){
      itr.nextBoolean();
      itr.remove();
      Assertions.assertEquals(i,seq.size());
      Assertions.assertEquals(100+(100-i),seq.modCount);
    }
  }
  @Test
  public void testCheckedListforEach_Consumer_SeqIsEmpty_NoMod(){
    var seq=new BooleanArrSeq.CheckedList();
    var consumer=new BooleanMonitoredConsumer();
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedListforEach_Consumer_SeqIsNotEmpty_NoMod(){
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var consumer=new BooleanMonitoredConsumer();
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(100,consumer.size());
    var seqIterator=seq.iterator();
    var consumerIterator=consumer.iterator();
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(consumerIterator.next(),seqIterator.next());
    }
  }
  @Test
  public void testCheckedListforEach_Consumer_SeqIsEmpty_ModdingConsumer(){
    var seq=new BooleanArrSeq.CheckedList();
    var consumer=new BooleanMonitoredConsumer.ModifyingArrSeqCheckedListConsumer(seq);
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedListforEach_Consumer_SeqIsNotEmpty_ModdingConsumer(){
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var consumer=new BooleanMonitoredConsumer.ModifyingArrSeqCheckedListConsumer(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.forEach((Consumer)consumer));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(300,seq.modCount);
    Assertions.assertEquals(100,consumer.size());
    var seqIterator=seq.iterator();
    var consumerIterator=consumer.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(consumerIterator.next(),seqIterator.next());
    }
  }
  @Test
  public void testCheckedListforEach_Consumer_SeqIsEmpty_ThrowingConsumer(){
    var seq=new BooleanArrSeq.CheckedList();
    var consumer=new BooleanMonitoredConsumer.Throwing();
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedListforEach_Consumer_SeqIsNotEmpty_ThrowingConsumer(){
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var consumer=new BooleanMonitoredConsumer.Throwing();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.forEach((Consumer)consumer));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(1,consumer.size());
    var seqIterator=seq.iterator();
    var consumerIterator=consumer.iterator();
    for(int i=0;i<1;++i){
      Assertions.assertEquals(consumerIterator.next(),seqIterator.next());
    }
  }
  @Test
  public void testCheckedListforEach_Consumer_SeqIsEmpty_ThrowingAndModdingConsumer(){
    var seq=new BooleanArrSeq.CheckedList();
    var consumer=new BooleanMonitoredConsumer.ModifyingArrSeqCheckedListAndThrowingConsumer(seq);
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedListforEach_Consumer_SeqIsNotEmpty_ThrowingAndModdingConsumer(){
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var consumer=new BooleanMonitoredConsumer.ModifyingArrSeqCheckedListAndThrowingConsumer(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.forEach((Consumer)consumer));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(102,seq.modCount);
    Assertions.assertEquals(1,consumer.size());
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_RemoveTruePredicate()
  {
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveTruePredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_RemoveFalsePredicate()
  {
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveFalsePredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_FirstValtrue_RemoveTrue(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveTruePredicate();
      Assertions.assertEquals(trueCount!=0,seq.removeIf((Predicate)filter));
      Assertions.assertEquals(falseCount,seq.size());
      for(int i=0;i<falseCount;++i){
        Assertions.assertFalse(seq.arr[i]);
      }
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      Assertions.assertEquals(trueCount==0?10:11,seq.modCount);
    }
  }
  @Test
  public void testCheckedListremoveIf_Predicate_FirstValfalse_RemoveTrue(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveTruePredicate();
      Assertions.assertEquals(trueCount!=0,seq.removeIf((Predicate)filter));
      Assertions.assertEquals(falseCount,seq.size());
      for(int i=0;i<falseCount;++i){
        Assertions.assertFalse(seq.arr[i]);
      }
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      Assertions.assertEquals(trueCount==0?10:11,seq.modCount);
    }
  }
  @Test
  public void testCheckedListremoveIf_Predicate_FirstValtrue_RemoveFalse(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveFalsePredicate();
      Assertions.assertEquals(falseCount!=0,seq.removeIf((Predicate)filter));
      Assertions.assertEquals(trueCount,seq.size());
      for(int i=0;i<trueCount;++i){
        Assertions.assertTrue(seq.arr[i]);
      }
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      Assertions.assertEquals(falseCount==0?10:11,seq.modCount);
    }
  }
  @Test
  public void testCheckedListremoveIf_Predicate_FirstValfalse_RemoveFalse(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveFalsePredicate();
      Assertions.assertEquals(falseCount!=0,seq.removeIf((Predicate)filter));
      Assertions.assertEquals(trueCount,seq.size());
      for(int i=0;i<trueCount;++i){
        Assertions.assertTrue(seq.arr[i]);
      }
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      Assertions.assertEquals(falseCount==0?10:11,seq.modCount);
    }
  }
  @Test
  public void testCheckedListremoveIf_Predicate_FirstValtrue_RemoveAll(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveAllPredicate();
      Assertions.assertTrue(seq.removeIf((Predicate)filter));
      Assertions.assertEquals(0,seq.size());
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      Assertions.assertEquals(11,seq.modCount);
    }
  }
  @Test
  public void testCheckedListremoveIf_Predicate_FirstValfalse_RemoveAll(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveAllPredicate();
      Assertions.assertTrue(seq.removeIf((Predicate)filter));
      Assertions.assertEquals(0,seq.size());
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      Assertions.assertEquals(11,seq.modCount);
    }
  }
  @Test
  public void testCheckedListremoveIf_Predicate_FirstValtrue_RemoveNone(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveNonePredicate();
      Assertions.assertFalse(seq.removeIf((Predicate)filter));
      Assertions.assertEquals(10,seq.size());
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      currVal=true;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      Assertions.assertEquals(10,seq.modCount);
    }
  }
  @Test
  public void testCheckedListremoveIf_Predicate_FirstValfalse_RemoveNone(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveNonePredicate();
      Assertions.assertFalse(seq.removeIf((Predicate)filter));
      Assertions.assertEquals(10,seq.size());
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      currVal=false;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      Assertions.assertEquals(10,seq.modCount);
    }
  }
  @Test
  public void testCheckedListremoveIf_Predicate_FirstValtrue_RemoveTrueArrSeqCheckedListModifyingPredicate(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveTrueArrSeqCheckedListModifyingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=true;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      if(falseCount!=10 && trueCount!=10){
        Assertions.assertEquals(10+4,seq.modCount);
        Assertions.assertEquals(2,filter.callCount);
      }else{
        Assertions.assertEquals(10+2,seq.modCount);
        Assertions.assertEquals(1,filter.callCount);
      }
    }
  }
  @Test
  public void testCheckedListremoveIf_Predicate_FirstValtrue_RemoveFalseArrSeqCheckedListModifyingPredicate(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveFalseArrSeqCheckedListModifyingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=true;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      if(falseCount!=10 && trueCount!=10){
        Assertions.assertEquals(10+4,seq.modCount);
        Assertions.assertEquals(2,filter.callCount);
      }else{
        Assertions.assertEquals(10+2,seq.modCount);
        Assertions.assertEquals(1,filter.callCount);
      }
    }
  }
  @Test
  public void testCheckedListremoveIf_Predicate_FirstValfalse_RemoveTrueArrSeqCheckedListModifyingPredicate(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveTrueArrSeqCheckedListModifyingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=false;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      if(falseCount!=10 && trueCount!=10){
        Assertions.assertEquals(10+4,seq.modCount);
        Assertions.assertEquals(2,filter.callCount);
      }else{
        Assertions.assertEquals(10+2,seq.modCount);
        Assertions.assertEquals(1,filter.callCount);
      }
    }
  }
  @Test
  public void testCheckedListremoveIf_Predicate_FirstValfalse_RemoveFalseArrSeqCheckedListModifyingPredicate(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveFalseArrSeqCheckedListModifyingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=false;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      if(falseCount!=10 && trueCount!=10){
        Assertions.assertEquals(10+4,seq.modCount);
        Assertions.assertEquals(2,filter.callCount);
      }else{
        Assertions.assertEquals(10+2,seq.modCount);
        Assertions.assertEquals(1,filter.callCount);
      }
    }
  }
  @Test
  public void testCheckedListremoveIf_Predicate_FirstValtrue_RemoveNoneArrSeqCheckedListModifyingPredicate(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveNoneArrSeqCheckedListModifyingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=true;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      if(falseCount!=10 && trueCount!=10){
        Assertions.assertEquals(10+4,seq.modCount);
        Assertions.assertEquals(2,filter.callCount);
      }else{
        Assertions.assertEquals(10+2,seq.modCount);
        Assertions.assertEquals(1,filter.callCount);
      }
    }
  }
  @Test
  public void testCheckedListremoveIf_Predicate_FirstValtrue_RemoveAllArrSeqCheckedListModifyingPredicate(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveAllArrSeqCheckedListModifyingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=true;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      if(falseCount!=10 && trueCount!=10){
        Assertions.assertEquals(10+4,seq.modCount);
        Assertions.assertEquals(2,filter.callCount);
      }else{
        Assertions.assertEquals(10+2,seq.modCount);
        Assertions.assertEquals(1,filter.callCount);
      }
    }
  }
  @Test
  public void testCheckedListremoveIf_Predicate_FirstValfalse_RemoveNoneArrSeqCheckedListModifyingPredicate(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveNoneArrSeqCheckedListModifyingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=false;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      if(falseCount!=10 && trueCount!=10){
        Assertions.assertEquals(10+4,seq.modCount);
        Assertions.assertEquals(2,filter.callCount);
      }else{
        Assertions.assertEquals(10+2,seq.modCount);
        Assertions.assertEquals(1,filter.callCount);
      }
    }
  }
  @Test
  public void testCheckedListremoveIf_Predicate_FirstValfalse_RemoveAllArrSeqCheckedListModifyingPredicate(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveAllArrSeqCheckedListModifyingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=false;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      if(falseCount!=10 && trueCount!=10){
        Assertions.assertEquals(10+4,seq.modCount);
        Assertions.assertEquals(2,filter.callCount);
      }else{
        Assertions.assertEquals(10+2,seq.modCount);
        Assertions.assertEquals(1,filter.callCount);
      }
    }
  }
  @Test
  public void testCheckedListremoveIf_Predicate_FirstValtrue_Throwing(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.ThrowingPredicate();
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIf((Predicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=true;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      Assertions.assertEquals(10,seq.modCount);
      Assertions.assertEquals(1,filter.callCount);
    }
  }
  @Test
  public void testCheckedListremoveIf_Predicate_FirstValfalse_Throwing(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.ThrowingPredicate();
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIf((Predicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=false;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      Assertions.assertEquals(10,seq.modCount);
      Assertions.assertEquals(1,filter.callCount);
    }
  }
  @Test
  public void testCheckedListremoveIf_Predicate_FirstValfalse_ThrowAndMod(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.ModifyingArrSeqCheckedListAndThrowingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
      Assertions.assertEquals(11,seq.size());
      currVal=false;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      Assertions.assertEquals(false,seq.arr[10]);
      Assertions.assertEquals(10+1,seq.modCount);
      Assertions.assertEquals(1,filter.callCount);
    }
  }
  @Test
  public void testCheckedListremoveIf_Predicate_FirstValtrue_ThrowAndMod(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.ModifyingArrSeqCheckedListAndThrowingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
      Assertions.assertEquals(11,seq.size());
      currVal=true;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      Assertions.assertEquals(true,seq.arr[10]);
      Assertions.assertEquals(10+1,seq.modCount);
      Assertions.assertEquals(1,filter.callCount);
    }
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_RemoveTrueArrSeqCheckedListModifyingPredicate()
  {
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveTrueArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_RemoveFalseArrSeqCheckedListModifyingPredicate()
  {
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveFalseArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_RemoveAllPredicate()
  {
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveAllPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_RemoveNonePredicate()
  {
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveNonePredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_RemoveAllArrSeqCheckedListModifyingPredicate()
  {
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveAllArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_RemoveNoneArrSeqCheckedListModifyingPredicate()
  {
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveNoneArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_ThrowingPredicate()
  {
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.ThrowingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_ModifyingArrSeqCheckedListAndThrowingPredicate()
  {
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.ModifyingArrSeqCheckedListAndThrowingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListforEach_BooleanConsumer_SeqIsEmpty_NoMod(){
    var seq=new BooleanArrSeq.CheckedList();
    var consumer=new BooleanMonitoredConsumer();
    seq.forEach((BooleanConsumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedListforEach_BooleanConsumer_SeqIsNotEmpty_NoMod(){
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var consumer=new BooleanMonitoredConsumer();
    seq.forEach((BooleanConsumer)consumer);
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(100,consumer.size());
    var seqIterator=seq.iterator();
    var consumerIterator=consumer.iterator();
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(consumerIterator.next(),seqIterator.next());
    }
  }
  @Test
  public void testCheckedListforEach_BooleanConsumer_SeqIsEmpty_ModdingConsumer(){
    var seq=new BooleanArrSeq.CheckedList();
    var consumer=new BooleanMonitoredConsumer.ModifyingArrSeqCheckedListConsumer(seq);
    seq.forEach((BooleanConsumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedListforEach_BooleanConsumer_SeqIsNotEmpty_ModdingConsumer(){
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var consumer=new BooleanMonitoredConsumer.ModifyingArrSeqCheckedListConsumer(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.forEach((BooleanConsumer)consumer));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(300,seq.modCount);
    Assertions.assertEquals(100,consumer.size());
    var seqIterator=seq.iterator();
    var consumerIterator=consumer.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(consumerIterator.next(),seqIterator.next());
    }
  }
  @Test
  public void testCheckedListforEach_BooleanConsumer_SeqIsEmpty_ThrowingConsumer(){
    var seq=new BooleanArrSeq.CheckedList();
    var consumer=new BooleanMonitoredConsumer.Throwing();
    seq.forEach((BooleanConsumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedListforEach_BooleanConsumer_SeqIsNotEmpty_ThrowingConsumer(){
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var consumer=new BooleanMonitoredConsumer.Throwing();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.forEach((BooleanConsumer)consumer));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(1,consumer.size());
    var seqIterator=seq.iterator();
    var consumerIterator=consumer.iterator();
    for(int i=0;i<1;++i){
      Assertions.assertEquals(consumerIterator.next(),seqIterator.next());
    }
  }
  @Test
  public void testCheckedListforEach_BooleanConsumer_SeqIsEmpty_ThrowingAndModdingConsumer(){
    var seq=new BooleanArrSeq.CheckedList();
    var consumer=new BooleanMonitoredConsumer.ModifyingArrSeqCheckedListAndThrowingConsumer(seq);
    seq.forEach((BooleanConsumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedListforEach_BooleanConsumer_SeqIsNotEmpty_ThrowingAndModdingConsumer(){
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var consumer=new BooleanMonitoredConsumer.ModifyingArrSeqCheckedListAndThrowingConsumer(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.forEach((BooleanConsumer)consumer));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(102,seq.modCount);
    Assertions.assertEquals(1,consumer.size());
  }
  @Test
  public void testCheckedListremoveIf_BooleanPredicate_SeqIsEmpty_RemoveTruePredicate()
  {
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveTruePredicate(seq);
    Assertions.assertFalse(seq.removeIf((BooleanPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_BooleanPredicate_SeqIsEmpty_RemoveFalsePredicate()
  {
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveFalsePredicate(seq);
    Assertions.assertFalse(seq.removeIf((BooleanPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_BooleanPredicate_FirstValtrue_RemoveTrue(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveTruePredicate();
      Assertions.assertEquals(trueCount!=0,seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(falseCount,seq.size());
      for(int i=0;i<falseCount;++i){
        Assertions.assertFalse(seq.arr[i]);
      }
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      Assertions.assertEquals(trueCount==0?10:11,seq.modCount);
    }
  }
  @Test
  public void testCheckedListremoveIf_BooleanPredicate_FirstValfalse_RemoveTrue(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveTruePredicate();
      Assertions.assertEquals(trueCount!=0,seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(falseCount,seq.size());
      for(int i=0;i<falseCount;++i){
        Assertions.assertFalse(seq.arr[i]);
      }
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      Assertions.assertEquals(trueCount==0?10:11,seq.modCount);
    }
  }
  @Test
  public void testCheckedListremoveIf_BooleanPredicate_FirstValtrue_RemoveFalse(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveFalsePredicate();
      Assertions.assertEquals(falseCount!=0,seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(trueCount,seq.size());
      for(int i=0;i<trueCount;++i){
        Assertions.assertTrue(seq.arr[i]);
      }
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      Assertions.assertEquals(falseCount==0?10:11,seq.modCount);
    }
  }
  @Test
  public void testCheckedListremoveIf_BooleanPredicate_FirstValfalse_RemoveFalse(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveFalsePredicate();
      Assertions.assertEquals(falseCount!=0,seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(trueCount,seq.size());
      for(int i=0;i<trueCount;++i){
        Assertions.assertTrue(seq.arr[i]);
      }
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      Assertions.assertEquals(falseCount==0?10:11,seq.modCount);
    }
  }
  @Test
  public void testCheckedListremoveIf_BooleanPredicate_FirstValtrue_RemoveAll(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveAllPredicate();
      Assertions.assertTrue(seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(0,seq.size());
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      Assertions.assertEquals(11,seq.modCount);
    }
  }
  @Test
  public void testCheckedListremoveIf_BooleanPredicate_FirstValfalse_RemoveAll(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveAllPredicate();
      Assertions.assertTrue(seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(0,seq.size());
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      Assertions.assertEquals(11,seq.modCount);
    }
  }
  @Test
  public void testCheckedListremoveIf_BooleanPredicate_FirstValtrue_RemoveNone(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveNonePredicate();
      Assertions.assertFalse(seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(10,seq.size());
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      currVal=true;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      Assertions.assertEquals(10,seq.modCount);
    }
  }
  @Test
  public void testCheckedListremoveIf_BooleanPredicate_FirstValfalse_RemoveNone(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveNonePredicate();
      Assertions.assertFalse(seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(10,seq.size());
      Assertions.assertEquals(falseCount==0||trueCount==0?1:2,filter.callCount);
      currVal=false;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      Assertions.assertEquals(10,seq.modCount);
    }
  }
  @Test
  public void testCheckedListremoveIf_BooleanPredicate_FirstValtrue_RemoveTrueArrSeqCheckedListModifyingPredicate(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveTrueArrSeqCheckedListModifyingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=true;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      if(falseCount!=10 && trueCount!=10){
        Assertions.assertEquals(10+4,seq.modCount);
        Assertions.assertEquals(2,filter.callCount);
      }else{
        Assertions.assertEquals(10+2,seq.modCount);
        Assertions.assertEquals(1,filter.callCount);
      }
    }
  }
  @Test
  public void testCheckedListremoveIf_BooleanPredicate_FirstValtrue_RemoveFalseArrSeqCheckedListModifyingPredicate(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveFalseArrSeqCheckedListModifyingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=true;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      if(falseCount!=10 && trueCount!=10){
        Assertions.assertEquals(10+4,seq.modCount);
        Assertions.assertEquals(2,filter.callCount);
      }else{
        Assertions.assertEquals(10+2,seq.modCount);
        Assertions.assertEquals(1,filter.callCount);
      }
    }
  }
  @Test
  public void testCheckedListremoveIf_BooleanPredicate_FirstValfalse_RemoveTrueArrSeqCheckedListModifyingPredicate(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveTrueArrSeqCheckedListModifyingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=false;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      if(falseCount!=10 && trueCount!=10){
        Assertions.assertEquals(10+4,seq.modCount);
        Assertions.assertEquals(2,filter.callCount);
      }else{
        Assertions.assertEquals(10+2,seq.modCount);
        Assertions.assertEquals(1,filter.callCount);
      }
    }
  }
  @Test
  public void testCheckedListremoveIf_BooleanPredicate_FirstValfalse_RemoveFalseArrSeqCheckedListModifyingPredicate(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveFalseArrSeqCheckedListModifyingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=false;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      if(falseCount!=10 && trueCount!=10){
        Assertions.assertEquals(10+4,seq.modCount);
        Assertions.assertEquals(2,filter.callCount);
      }else{
        Assertions.assertEquals(10+2,seq.modCount);
        Assertions.assertEquals(1,filter.callCount);
      }
    }
  }
  @Test
  public void testCheckedListremoveIf_BooleanPredicate_FirstValtrue_RemoveNoneArrSeqCheckedListModifyingPredicate(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveNoneArrSeqCheckedListModifyingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=true;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      if(falseCount!=10 && trueCount!=10){
        Assertions.assertEquals(10+4,seq.modCount);
        Assertions.assertEquals(2,filter.callCount);
      }else{
        Assertions.assertEquals(10+2,seq.modCount);
        Assertions.assertEquals(1,filter.callCount);
      }
    }
  }
  @Test
  public void testCheckedListremoveIf_BooleanPredicate_FirstValtrue_RemoveAllArrSeqCheckedListModifyingPredicate(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveAllArrSeqCheckedListModifyingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=true;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      if(falseCount!=10 && trueCount!=10){
        Assertions.assertEquals(10+4,seq.modCount);
        Assertions.assertEquals(2,filter.callCount);
      }else{
        Assertions.assertEquals(10+2,seq.modCount);
        Assertions.assertEquals(1,filter.callCount);
      }
    }
  }
  @Test
  public void testCheckedListremoveIf_BooleanPredicate_FirstValfalse_RemoveNoneArrSeqCheckedListModifyingPredicate(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveNoneArrSeqCheckedListModifyingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=false;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      if(falseCount!=10 && trueCount!=10){
        Assertions.assertEquals(10+4,seq.modCount);
        Assertions.assertEquals(2,filter.callCount);
      }else{
        Assertions.assertEquals(10+2,seq.modCount);
        Assertions.assertEquals(1,filter.callCount);
      }
    }
  }
  @Test
  public void testCheckedListremoveIf_BooleanPredicate_FirstValfalse_RemoveAllArrSeqCheckedListModifyingPredicate(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.RemoveAllArrSeqCheckedListModifyingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=false;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      if(falseCount!=10 && trueCount!=10){
        Assertions.assertEquals(10+4,seq.modCount);
        Assertions.assertEquals(2,filter.callCount);
      }else{
        Assertions.assertEquals(10+2,seq.modCount);
        Assertions.assertEquals(1,filter.callCount);
      }
    }
  }
  @Test
  public void testCheckedListremoveIf_BooleanPredicate_FirstValtrue_Throwing(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.ThrowingPredicate();
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=true;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      Assertions.assertEquals(10,seq.modCount);
      Assertions.assertEquals(1,filter.callCount);
    }
  }
  @Test
  public void testCheckedListremoveIf_BooleanPredicate_FirstValfalse_Throwing(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.ThrowingPredicate();
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(10,seq.size());
      currVal=false;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      Assertions.assertEquals(10,seq.modCount);
      Assertions.assertEquals(1,filter.callCount);
    }
  }
  @Test
  public void testCheckedListremoveIf_BooleanPredicate_FirstValfalse_ThrowAndMod(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=false;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.ModifyingArrSeqCheckedListAndThrowingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(11,seq.size());
      currVal=false;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      Assertions.assertEquals(false,seq.arr[10]);
      Assertions.assertEquals(10+1,seq.modCount);
      Assertions.assertEquals(1,filter.callCount);
    }
  }
  @Test
  public void testCheckedListremoveIf_BooleanPredicate_FirstValtrue_ThrowAndMod(){
    for(int period=1;period<=10;++period){
      var seq=new BooleanArrSeq.CheckedList();
      boolean currVal=true;
      int trueCount=0;
      int falseCount=0;
      for(int i=0;;){
        seq.add(currVal);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      var filter=new BooleanMonitoredPredicate2.ModifyingArrSeqCheckedListAndThrowingPredicate(seq);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((BooleanPredicate)filter));
      Assertions.assertEquals(11,seq.size());
      currVal=true;
      trueCount=0;
      falseCount=0;
      for(int i=0;;){
        Assertions.assertEquals(currVal,seq.arr[i]);
        if(currVal){
          ++trueCount;
        }else{
          ++falseCount;
        }
        if((trueCount+falseCount)==10){
          break;
        }
        if(((++i)%period)==0){
          currVal=!currVal;
        }
      }
      Assertions.assertEquals(true,seq.arr[10]);
      Assertions.assertEquals(10+1,seq.modCount);
      Assertions.assertEquals(1,filter.callCount);
    }
  }
  @Test
  public void testCheckedListremoveIf_BooleanPredicate_SeqIsEmpty_RemoveTrueArrSeqCheckedListModifyingPredicate()
  {
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveTrueArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((BooleanPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_BooleanPredicate_SeqIsEmpty_RemoveFalseArrSeqCheckedListModifyingPredicate()
  {
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveFalseArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((BooleanPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_BooleanPredicate_SeqIsEmpty_RemoveAllPredicate()
  {
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveAllPredicate(seq);
    Assertions.assertFalse(seq.removeIf((BooleanPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_BooleanPredicate_SeqIsEmpty_RemoveNonePredicate()
  {
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveNonePredicate(seq);
    Assertions.assertFalse(seq.removeIf((BooleanPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_BooleanPredicate_SeqIsEmpty_RemoveAllArrSeqCheckedListModifyingPredicate()
  {
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveAllArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((BooleanPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_BooleanPredicate_SeqIsEmpty_RemoveNoneArrSeqCheckedListModifyingPredicate()
  {
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.RemoveNoneArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((BooleanPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_BooleanPredicate_SeqIsEmpty_ThrowingPredicate()
  {
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.ThrowingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((BooleanPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_BooleanPredicate_SeqIsEmpty_ModifyingArrSeqCheckedListAndThrowingPredicate()
  {
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToboolean(i));
    }
    var filter=new BooleanMonitoredPredicate2.ModifyingArrSeqCheckedListAndThrowingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((BooleanPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_boolean_initialCapacityDEFAULT(){
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_boolean_initialCapacityNULL(){
    var seq=new BooleanArrSeq.CheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_boolean_initialCapacity50(){
    var seq=new BooleanArrSeq.CheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_Boolean_initialCapacityDEFAULT(){
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_Boolean_initialCapacityNULL(){
    var seq=new BooleanArrSeq.CheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_Boolean_initialCapacity50(){
    var seq=new BooleanArrSeq.CheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListtoBooleanArray_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.CheckedList();
    Assertions.assertSame(OmniArray.OfBoolean.DEFAULT_ARR,seq.toBooleanArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListtoBooleanArray_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toBooleanArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.nextBoolean(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testCheckedListtoArray_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.CheckedList();
    Assertions.assertSame(OmniArray.OfBoolean.DEFAULT_BOXED_ARR,seq.toArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListtoArray_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.next(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testCheckedListtoDoubleArray_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.CheckedList();
    Assertions.assertSame(OmniArray.OfDouble.DEFAULT_ARR,seq.toDoubleArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListtoDoubleArray_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toDoubleArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.nextDouble(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testCheckedListtoFloatArray_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.CheckedList();
    Assertions.assertSame(OmniArray.OfFloat.DEFAULT_ARR,seq.toFloatArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListtoFloatArray_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toFloatArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.nextFloat(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testCheckedListtoLongArray_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.CheckedList();
    Assertions.assertSame(OmniArray.OfLong.DEFAULT_ARR,seq.toLongArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListtoLongArray_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toLongArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.nextLong(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testCheckedListtoIntArray_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.CheckedList();
    Assertions.assertSame(OmniArray.OfInt.DEFAULT_ARR,seq.toIntArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListtoIntArray_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toIntArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.nextInt(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testCheckedListtoShortArray_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.CheckedList();
    Assertions.assertSame(OmniArray.OfShort.DEFAULT_ARR,seq.toShortArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListtoShortArray_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toShortArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.nextShort(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testCheckedListtoCharArray_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.CheckedList();
    Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,seq.toCharArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListtoCharArray_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toCharArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.nextChar(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testCheckedListtoByteArray_void_seqIsEmpty(){
    var seq=new BooleanArrSeq.CheckedList();
    Assertions.assertSame(OmniArray.OfByte.DEFAULT_ARR,seq.toByteArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListtoByteArray_void_seqIsNotEmpty(){
    var seq=new BooleanArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    var result=seq.toByteArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertEquals(itr.nextByte(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
}
