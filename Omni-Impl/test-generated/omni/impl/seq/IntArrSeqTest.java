package omni.impl.seq;
//import omni.impl.seq.IntArrSeq.UncheckedList;
//import omni.impl.seq.IntArrSeq.CheckedList;
//import omni.impl.seq.IntArrSeq.UncheckedStack;
//import omni.impl.seq.IntArrSeq.CheckedStack;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.function.IntFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.ConcurrentModificationException;
import omni.util.OmniArray;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class IntArrSeqTest{
  @Test
  public void testUncheckedStackconstructor_void_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.UncheckedStack();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertSame(OmniArray.OfInt.DEFAULT_ARR,seq.arr);
  }
  @Test
  public void testUncheckedStackconstructor_intint_initialCapacityNULL(){
    var seq=new IntArrSeq.UncheckedStack(0,null);
    Assertions.assertEquals(0,seq.size);
    Assertions.assertNull(seq.arr);
  }
  @Test
  public void testUncheckedStackconstructor_int_initialCapacity50(){
    var seq=new IntArrSeq.UncheckedStack(50);
    Assertions.assertEquals(0,seq.size);
    switch(50){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfInt.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(50,seq.arr.length);
    }
  }
  @Test
  public void testUncheckedStackconstructor_int_initialCapacity0(){
    var seq=new IntArrSeq.UncheckedStack(0);
    Assertions.assertEquals(0,seq.size);
    switch(0){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfInt.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(0,seq.arr.length);
    }
  }
  @Test
  public void testUncheckedStackconstructor_int_initialCapacity10(){
    var seq=new IntArrSeq.UncheckedStack(10);
    Assertions.assertEquals(0,seq.size);
    switch(10){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfInt.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(10,seq.arr.length);
    }
  }
  @Test
  public void testUncheckedStackClone_initialCapacityDEFAULT_seqIsEmpty()
  {
    var seq=new IntArrSeq.UncheckedStack();
    var clone=(IntArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfInt.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedStackClone_initialCapacityDEFAULT_seqIsNotEmpty()
  {
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var clone=(IntArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackClone_initialCapacityNULL_seqIsEmpty()
  {
    var seq=new IntArrSeq.UncheckedStack(0,null);
    var clone=(IntArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfInt.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedStackClone_initialCapacityNULL_seqIsNotEmpty()
  {
    var seq=new IntArrSeq.UncheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var clone=(IntArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackClone_initialCapacity50_seqIsEmpty()
  {
    var seq=new IntArrSeq.UncheckedStack(50);
    var clone=(IntArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfInt.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedStackClone_initialCapacity50_seqIsNotEmpty()
  {
    var seq=new IntArrSeq.UncheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var clone=(IntArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackClone_initialCapacity0_seqIsEmpty()
  {
    var seq=new IntArrSeq.UncheckedStack(0);
    var clone=(IntArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfInt.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedStackClone_initialCapacity0_seqIsNotEmpty()
  {
    var seq=new IntArrSeq.UncheckedStack(0);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var clone=(IntArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackClone_initialCapacity10_seqIsEmpty()
  {
    var seq=new IntArrSeq.UncheckedStack(10);
    var clone=(IntArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfInt.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedStackClone_initialCapacity10_seqIsNotEmpty()
  {
    var seq=new IntArrSeq.UncheckedStack(10);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var clone=(IntArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedStacktoArray_ObjectArray_zeroLengthArrayAndSequenceIsEmpty(){
    var seq=new IntArrSeq.UncheckedStack();
    Integer[] paramArr=new Integer[0];
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
  }
  @Test
  public void testUncheckedStacktoArray_ObjectArray_zeroLengthArrayAndSequenceNotEmpty(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    Integer[] paramArr=new Integer[0];
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
    var seq=new IntArrSeq.UncheckedStack();
    Integer[] paramArr=new Integer[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToint(paramArr.length);
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(5,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    Assertions.assertNull(result[0]);
    for(int i=1;i<result.length;++i){
      Assertions.assertEquals((Object)TypeConversionUtil.convertToint(paramArr.length),result[i]);
    }
  }
  @Test
  public void testUncheckedStacktoArray_ObjectArray_overSizedArray(){
    var seq=new IntArrSeq.UncheckedStack();
    Integer[] paramArr=new Integer[10];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToint(paramArr.length);
    }
    for(int i=0;i<5;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
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
      Assertions.assertEquals((Object)TypeConversionUtil.convertToint(paramArr.length),result[i]);
    }
  }
  @Test
  public void testUncheckedStacktoArray_ObjectArray_undersizedArray(){
    var seq=new IntArrSeq.UncheckedStack();
    Integer[] paramArr=new Integer[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToint(10);
    }
    for(int i=0;i<10;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
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
    var seq=new IntArrSeq.UncheckedStack();
    Integer[] paramArr=new Integer[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToint(5);
    }
    for(int i=0;i<5;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
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
    var seq=new IntArrSeq.UncheckedStack();
    IntFunction<Integer[]> arrConstructor=Integer[]::new;
    var result=seq.toArray(arrConstructor);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertEquals(0,result.length);
  }
  @Test
  public void testUncheckedStacktoArray_IntFunction_seqIsNotEmpty_nonMod(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    IntFunction<Integer[]> arrConstructor=Integer[]::new;
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
    var seq=new IntArrSeq.UncheckedStack();
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testUncheckedStackclear_void_seqIsNotEmpty(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
    for(int i=0;i<100;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackisEmpty_void_seqIsEmpty(){
    var seq=new IntArrSeq.UncheckedStack();
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testUncheckedStackisEmpty_void_seqIsNotEmpty(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    Assertions.assertFalse(seq.isEmpty());
  }
  @Test
  public void testUncheckedStackisEmpty_void_seqIsBeingCleared(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;){
      Assertions.assertFalse(seq.isEmpty());
      itr.nextInt();
      itr.remove();
    }
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testUncheckedStacksize_void_seqIsEmpty(){
    var seq=new IntArrSeq.UncheckedStack();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
  }
  @Test
  public void testUncheckedStacksize_void_seqIsNotEmpty(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
  }
  @Test
  public void testUncheckedStacksize_void_seqIsBeingCleared(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;){
      itr.nextInt();
      itr.remove();
      Assertions.assertEquals(i,seq.size());
    }
  }
  @Test
  public void testUncheckedStackforEach_Consumer_SeqIsEmpty_NoMod(){
    var seq=new IntArrSeq.UncheckedStack();
    var consumer=new IntMonitoredConsumer();
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testUncheckedStackforEach_Consumer_SeqIsNotEmpty_NoMod(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var consumer=new IntMonitoredConsumer();
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
  public void testUncheckedStackremoveIf_Predicate_SeqIsEmpty_RetainSecondPredicate()
  {
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqIsEmpty_RetainSecondAndLastPredicate()
  {
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveFirstAndThirdPredicate()
  {
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveFirstPredicate()
  {
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveFirstAndSecondToLastPredicate()
  {
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize3_RemoveAll(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize3_RemoveNone(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNonePredicate();
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize3_RetainSecond(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(1,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize3_RetainSecondAndLast(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(2,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize3_RemoveFirstAndThird(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3-2,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize3_RemoveFirst(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3-1,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize3_RemoveFirstAndSecondToLast(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3-2,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize50_RemoveAll(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize50_RemoveNone(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNonePredicate();
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize50_RetainSecond(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(1,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize50_RetainSecondAndLast(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(2,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize50_RemoveFirstAndThird(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50-2,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize50_RemoveFirst(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50-1,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize50_RemoveFirstAndSecondToLast(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50-2,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize100_RemoveAll(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize100_RemoveNone(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNonePredicate();
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize100_RetainSecond(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(1,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize100_RetainSecondAndLast(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(2,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize100_RemoveFirstAndThird(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100-2,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize100_RemoveFirst(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100-1,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize100_RemoveFirstAndSecondToLast(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100-2,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveAllPredicate()
  {
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveNonePredicate()
  {
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNonePredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedStackforEach_IntConsumer_SeqIsEmpty_NoMod(){
    var seq=new IntArrSeq.UncheckedStack();
    var consumer=new IntMonitoredConsumer();
    seq.forEach((IntConsumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testUncheckedStackforEach_IntConsumer_SeqIsNotEmpty_NoMod(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var consumer=new IntMonitoredConsumer();
    seq.forEach((IntConsumer)consumer);
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
  public void testUncheckedStackremoveIf_IntPredicate_SeqIsEmpty_RetainSecondPredicate()
  {
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondPredicate(seq);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_IntPredicate_SeqIsEmpty_RetainSecondAndLastPredicate()
  {
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_IntPredicate_SeqIsEmpty_RemoveFirstAndThirdPredicate()
  {
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdPredicate(seq);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_IntPredicate_SeqIsEmpty_RemoveFirstPredicate()
  {
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstPredicate(seq);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_IntPredicate_SeqIsEmpty_RemoveFirstAndSecondToLastPredicate()
  {
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_IntPredicate_SeqSize3_RemoveAll(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_IntPredicate_SeqSize3_RemoveNone(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNonePredicate();
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_IntPredicate_SeqSize3_RetainSecond(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(1,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_IntPredicate_SeqSize3_RetainSecondAndLast(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(2,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_IntPredicate_SeqSize3_RemoveFirstAndThird(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(3-2,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_IntPredicate_SeqSize3_RemoveFirst(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(3-1,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_IntPredicate_SeqSize3_RemoveFirstAndSecondToLast(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(3-2,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_IntPredicate_SeqSize50_RemoveAll(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_IntPredicate_SeqSize50_RemoveNone(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNonePredicate();
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_IntPredicate_SeqSize50_RetainSecond(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(1,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_IntPredicate_SeqSize50_RetainSecondAndLast(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(2,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_IntPredicate_SeqSize50_RemoveFirstAndThird(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(50-2,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_IntPredicate_SeqSize50_RemoveFirst(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(50-1,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_IntPredicate_SeqSize50_RemoveFirstAndSecondToLast(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(50-2,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_IntPredicate_SeqSize100_RemoveAll(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_IntPredicate_SeqSize100_RemoveNone(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNonePredicate();
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_IntPredicate_SeqSize100_RetainSecond(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(1,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_IntPredicate_SeqSize100_RetainSecondAndLast(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(2,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_IntPredicate_SeqSize100_RemoveFirstAndThird(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(100-2,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_IntPredicate_SeqSize100_RemoveFirst(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(100-1,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_IntPredicate_SeqSize100_RemoveFirstAndSecondToLast(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(100-2,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_IntPredicate_SeqIsEmpty_RemoveAllPredicate()
  {
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllPredicate(seq);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_IntPredicate_SeqIsEmpty_RemoveNonePredicate()
  {
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNonePredicate(seq);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedStackadd_int_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_int_initialCapacityNULL(){
    var seq=new IntArrSeq.UncheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_int_initialCapacity50(){
    var seq=new IntArrSeq.UncheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_Integer_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToInteger(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_Integer_initialCapacityNULL(){
    var seq=new IntArrSeq.UncheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToInteger(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_Integer_initialCapacity50(){
    var seq=new IntArrSeq.UncheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToInteger(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_boolean_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_boolean_initialCapacityNULL(){
    var seq=new IntArrSeq.UncheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_boolean_initialCapacity50(){
    var seq=new IntArrSeq.UncheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_Boolean_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_Boolean_initialCapacityNULL(){
    var seq=new IntArrSeq.UncheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_Boolean_initialCapacity50(){
    var seq=new IntArrSeq.UncheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_byte_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTobyte(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_byte_initialCapacityNULL(){
    var seq=new IntArrSeq.UncheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTobyte(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_byte_initialCapacity50(){
    var seq=new IntArrSeq.UncheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTobyte(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_Byte_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToByte(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_Byte_initialCapacityNULL(){
    var seq=new IntArrSeq.UncheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToByte(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_Byte_initialCapacity50(){
    var seq=new IntArrSeq.UncheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToByte(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_char_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_char_initialCapacityNULL(){
    var seq=new IntArrSeq.UncheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_char_initialCapacity50(){
    var seq=new IntArrSeq.UncheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_Character_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToCharacter(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_Character_initialCapacityNULL(){
    var seq=new IntArrSeq.UncheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToCharacter(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_Character_initialCapacity50(){
    var seq=new IntArrSeq.UncheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToCharacter(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_short_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToshort(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_short_initialCapacityNULL(){
    var seq=new IntArrSeq.UncheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToshort(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_short_initialCapacity50(){
    var seq=new IntArrSeq.UncheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToshort(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_Short_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToShort(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_Short_initialCapacityNULL(){
    var seq=new IntArrSeq.UncheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToShort(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_Short_initialCapacity50(){
    var seq=new IntArrSeq.UncheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToShort(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStacktoIntArray_void_seqIsEmpty(){
    var seq=new IntArrSeq.UncheckedStack();
    Assertions.assertSame(OmniArray.OfInt.DEFAULT_ARR,seq.toIntArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStacktoIntArray_void_seqIsNotEmpty(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
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
  public void testUncheckedStacktoArray_void_seqIsEmpty(){
    var seq=new IntArrSeq.UncheckedStack();
    Assertions.assertSame(OmniArray.OfInt.DEFAULT_BOXED_ARR,seq.toArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStacktoArray_void_seqIsNotEmpty(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
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
    var seq=new IntArrSeq.UncheckedStack();
    Assertions.assertSame(OmniArray.OfDouble.DEFAULT_ARR,seq.toDoubleArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStacktoDoubleArray_void_seqIsNotEmpty(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
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
    var seq=new IntArrSeq.UncheckedStack();
    Assertions.assertSame(OmniArray.OfFloat.DEFAULT_ARR,seq.toFloatArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStacktoFloatArray_void_seqIsNotEmpty(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
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
    var seq=new IntArrSeq.UncheckedStack();
    Assertions.assertSame(OmniArray.OfLong.DEFAULT_ARR,seq.toLongArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStacktoLongArray_void_seqIsNotEmpty(){
    var seq=new IntArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
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
  public void testUncheckedListconstructor_void_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.UncheckedList();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertSame(OmniArray.OfInt.DEFAULT_ARR,seq.arr);
  }
  @Test
  public void testUncheckedListconstructor_intint_initialCapacityNULL(){
    var seq=new IntArrSeq.UncheckedList(0,null);
    Assertions.assertEquals(0,seq.size);
    Assertions.assertNull(seq.arr);
  }
  @Test
  public void testUncheckedListconstructor_int_initialCapacity50(){
    var seq=new IntArrSeq.UncheckedList(50);
    Assertions.assertEquals(0,seq.size);
    switch(50){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfInt.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(50,seq.arr.length);
    }
  }
  @Test
  public void testUncheckedListconstructor_int_initialCapacity0(){
    var seq=new IntArrSeq.UncheckedList(0);
    Assertions.assertEquals(0,seq.size);
    switch(0){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfInt.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(0,seq.arr.length);
    }
  }
  @Test
  public void testUncheckedListconstructor_int_initialCapacity10(){
    var seq=new IntArrSeq.UncheckedList(10);
    Assertions.assertEquals(0,seq.size);
    switch(10){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfInt.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(10,seq.arr.length);
    }
  }
  @Test
  public void testUncheckedListClone_initialCapacityDEFAULT_seqIsEmpty()
  {
    var seq=new IntArrSeq.UncheckedList();
    var clone=(IntArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfInt.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedListClone_initialCapacityDEFAULT_seqIsNotEmpty()
  {
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var clone=(IntArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedListClone_initialCapacityNULL_seqIsEmpty()
  {
    var seq=new IntArrSeq.UncheckedList(0,null);
    var clone=(IntArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfInt.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedListClone_initialCapacityNULL_seqIsNotEmpty()
  {
    var seq=new IntArrSeq.UncheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var clone=(IntArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedListClone_initialCapacity50_seqIsEmpty()
  {
    var seq=new IntArrSeq.UncheckedList(50);
    var clone=(IntArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfInt.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedListClone_initialCapacity50_seqIsNotEmpty()
  {
    var seq=new IntArrSeq.UncheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var clone=(IntArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedListClone_initialCapacity0_seqIsEmpty()
  {
    var seq=new IntArrSeq.UncheckedList(0);
    var clone=(IntArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfInt.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedListClone_initialCapacity0_seqIsNotEmpty()
  {
    var seq=new IntArrSeq.UncheckedList(0);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var clone=(IntArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedListClone_initialCapacity10_seqIsEmpty()
  {
    var seq=new IntArrSeq.UncheckedList(10);
    var clone=(IntArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfInt.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedListClone_initialCapacity10_seqIsNotEmpty()
  {
    var seq=new IntArrSeq.UncheckedList(10);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var clone=(IntArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedListtoArray_ObjectArray_zeroLengthArrayAndSequenceIsEmpty(){
    var seq=new IntArrSeq.UncheckedList();
    Integer[] paramArr=new Integer[0];
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
  }
  @Test
  public void testUncheckedListtoArray_ObjectArray_zeroLengthArrayAndSequenceNotEmpty(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    Integer[] paramArr=new Integer[0];
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
    var seq=new IntArrSeq.UncheckedList();
    Integer[] paramArr=new Integer[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToint(paramArr.length);
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(5,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    Assertions.assertNull(result[0]);
    for(int i=1;i<result.length;++i){
      Assertions.assertEquals((Object)TypeConversionUtil.convertToint(paramArr.length),result[i]);
    }
  }
  @Test
  public void testUncheckedListtoArray_ObjectArray_overSizedArray(){
    var seq=new IntArrSeq.UncheckedList();
    Integer[] paramArr=new Integer[10];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToint(paramArr.length);
    }
    for(int i=0;i<5;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
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
      Assertions.assertEquals((Object)TypeConversionUtil.convertToint(paramArr.length),result[i]);
    }
  }
  @Test
  public void testUncheckedListtoArray_ObjectArray_undersizedArray(){
    var seq=new IntArrSeq.UncheckedList();
    Integer[] paramArr=new Integer[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToint(10);
    }
    for(int i=0;i<10;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
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
    var seq=new IntArrSeq.UncheckedList();
    Integer[] paramArr=new Integer[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToint(5);
    }
    for(int i=0;i<5;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
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
    var seq=new IntArrSeq.UncheckedList();
    IntFunction<Integer[]> arrConstructor=Integer[]::new;
    var result=seq.toArray(arrConstructor);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertEquals(0,result.length);
  }
  @Test
  public void testUncheckedListtoArray_IntFunction_seqIsNotEmpty_nonMod(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    IntFunction<Integer[]> arrConstructor=Integer[]::new;
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
    var seq=new IntArrSeq.UncheckedList();
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testUncheckedListclear_void_seqIsNotEmpty(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
    for(int i=0;i<100;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListisEmpty_void_seqIsEmpty(){
    var seq=new IntArrSeq.UncheckedList();
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testUncheckedListisEmpty_void_seqIsNotEmpty(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    Assertions.assertFalse(seq.isEmpty());
  }
  @Test
  public void testUncheckedListisEmpty_void_seqIsBeingCleared(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;){
      Assertions.assertFalse(seq.isEmpty());
      itr.nextInt();
      itr.remove();
    }
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testUncheckedListsize_void_seqIsEmpty(){
    var seq=new IntArrSeq.UncheckedList();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
  }
  @Test
  public void testUncheckedListsize_void_seqIsNotEmpty(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
  }
  @Test
  public void testUncheckedListsize_void_seqIsBeingCleared(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;){
      itr.nextInt();
      itr.remove();
      Assertions.assertEquals(i,seq.size());
    }
  }
  @Test
  public void testUncheckedListforEach_Consumer_SeqIsEmpty_NoMod(){
    var seq=new IntArrSeq.UncheckedList();
    var consumer=new IntMonitoredConsumer();
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testUncheckedListforEach_Consumer_SeqIsNotEmpty_NoMod(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var consumer=new IntMonitoredConsumer();
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
  public void testUncheckedListremoveIf_Predicate_SeqIsEmpty_RetainSecondPredicate()
  {
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqIsEmpty_RetainSecondAndLastPredicate()
  {
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqIsEmpty_RemoveFirstAndThirdPredicate()
  {
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqIsEmpty_RemoveFirstPredicate()
  {
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqIsEmpty_RemoveFirstAndSecondToLastPredicate()
  {
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize3_RemoveAll(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize3_RemoveNone(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNonePredicate();
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize3_RetainSecond(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(1,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize3_RetainSecondAndLast(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(2,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize3_RemoveFirstAndThird(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3-2,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize3_RemoveFirst(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3-1,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize3_RemoveFirstAndSecondToLast(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3-2,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize50_RemoveAll(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize50_RemoveNone(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNonePredicate();
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize50_RetainSecond(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(1,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize50_RetainSecondAndLast(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(2,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize50_RemoveFirstAndThird(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50-2,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize50_RemoveFirst(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50-1,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize50_RemoveFirstAndSecondToLast(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50-2,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize100_RemoveAll(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize100_RemoveNone(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNonePredicate();
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize100_RetainSecond(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(1,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize100_RetainSecondAndLast(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(2,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize100_RemoveFirstAndThird(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100-2,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize100_RemoveFirst(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100-1,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize100_RemoveFirstAndSecondToLast(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100-2,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqIsEmpty_RemoveAllPredicate()
  {
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqIsEmpty_RemoveNonePredicate()
  {
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNonePredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedListforEach_IntConsumer_SeqIsEmpty_NoMod(){
    var seq=new IntArrSeq.UncheckedList();
    var consumer=new IntMonitoredConsumer();
    seq.forEach((IntConsumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testUncheckedListforEach_IntConsumer_SeqIsNotEmpty_NoMod(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var consumer=new IntMonitoredConsumer();
    seq.forEach((IntConsumer)consumer);
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
  public void testUncheckedListremoveIf_IntPredicate_SeqIsEmpty_RetainSecondPredicate()
  {
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondPredicate(seq);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_IntPredicate_SeqIsEmpty_RetainSecondAndLastPredicate()
  {
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_IntPredicate_SeqIsEmpty_RemoveFirstAndThirdPredicate()
  {
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdPredicate(seq);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_IntPredicate_SeqIsEmpty_RemoveFirstPredicate()
  {
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstPredicate(seq);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_IntPredicate_SeqIsEmpty_RemoveFirstAndSecondToLastPredicate()
  {
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_IntPredicate_SeqSize3_RemoveAll(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_IntPredicate_SeqSize3_RemoveNone(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNonePredicate();
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_IntPredicate_SeqSize3_RetainSecond(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(1,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_IntPredicate_SeqSize3_RetainSecondAndLast(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(2,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_IntPredicate_SeqSize3_RemoveFirstAndThird(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(3-2,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_IntPredicate_SeqSize3_RemoveFirst(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(3-1,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_IntPredicate_SeqSize3_RemoveFirstAndSecondToLast(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(3-2,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_IntPredicate_SeqSize50_RemoveAll(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_IntPredicate_SeqSize50_RemoveNone(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNonePredicate();
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_IntPredicate_SeqSize50_RetainSecond(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(1,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_IntPredicate_SeqSize50_RetainSecondAndLast(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(2,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_IntPredicate_SeqSize50_RemoveFirstAndThird(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(50-2,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_IntPredicate_SeqSize50_RemoveFirst(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(50-1,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_IntPredicate_SeqSize50_RemoveFirstAndSecondToLast(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(50-2,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_IntPredicate_SeqSize100_RemoveAll(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_IntPredicate_SeqSize100_RemoveNone(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNonePredicate();
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_IntPredicate_SeqSize100_RetainSecond(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(1,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_IntPredicate_SeqSize100_RetainSecondAndLast(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(2,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_IntPredicate_SeqSize100_RemoveFirstAndThird(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(100-2,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_IntPredicate_SeqSize100_RemoveFirst(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(100-1,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_IntPredicate_SeqSize100_RemoveFirstAndSecondToLast(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(100-2,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_IntPredicate_SeqIsEmpty_RemoveAllPredicate()
  {
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllPredicate(seq);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_IntPredicate_SeqIsEmpty_RemoveNonePredicate()
  {
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNonePredicate(seq);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedListadd_int_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_int_initialCapacityNULL(){
    var seq=new IntArrSeq.UncheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_int_initialCapacity50(){
    var seq=new IntArrSeq.UncheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_Integer_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToInteger(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_Integer_initialCapacityNULL(){
    var seq=new IntArrSeq.UncheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToInteger(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_Integer_initialCapacity50(){
    var seq=new IntArrSeq.UncheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToInteger(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_boolean_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_boolean_initialCapacityNULL(){
    var seq=new IntArrSeq.UncheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_boolean_initialCapacity50(){
    var seq=new IntArrSeq.UncheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_Boolean_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_Boolean_initialCapacityNULL(){
    var seq=new IntArrSeq.UncheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_Boolean_initialCapacity50(){
    var seq=new IntArrSeq.UncheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_byte_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTobyte(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_byte_initialCapacityNULL(){
    var seq=new IntArrSeq.UncheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTobyte(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_byte_initialCapacity50(){
    var seq=new IntArrSeq.UncheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTobyte(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_Byte_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToByte(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_Byte_initialCapacityNULL(){
    var seq=new IntArrSeq.UncheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToByte(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_Byte_initialCapacity50(){
    var seq=new IntArrSeq.UncheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToByte(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_char_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_char_initialCapacityNULL(){
    var seq=new IntArrSeq.UncheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_char_initialCapacity50(){
    var seq=new IntArrSeq.UncheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_Character_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToCharacter(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_Character_initialCapacityNULL(){
    var seq=new IntArrSeq.UncheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToCharacter(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_Character_initialCapacity50(){
    var seq=new IntArrSeq.UncheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToCharacter(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_short_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToshort(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_short_initialCapacityNULL(){
    var seq=new IntArrSeq.UncheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToshort(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_short_initialCapacity50(){
    var seq=new IntArrSeq.UncheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToshort(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_Short_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToShort(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_Short_initialCapacityNULL(){
    var seq=new IntArrSeq.UncheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToShort(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_Short_initialCapacity50(){
    var seq=new IntArrSeq.UncheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToShort(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListtoIntArray_void_seqIsEmpty(){
    var seq=new IntArrSeq.UncheckedList();
    Assertions.assertSame(OmniArray.OfInt.DEFAULT_ARR,seq.toIntArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedListtoIntArray_void_seqIsNotEmpty(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
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
  public void testUncheckedListtoArray_void_seqIsEmpty(){
    var seq=new IntArrSeq.UncheckedList();
    Assertions.assertSame(OmniArray.OfInt.DEFAULT_BOXED_ARR,seq.toArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedListtoArray_void_seqIsNotEmpty(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
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
    var seq=new IntArrSeq.UncheckedList();
    Assertions.assertSame(OmniArray.OfDouble.DEFAULT_ARR,seq.toDoubleArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedListtoDoubleArray_void_seqIsNotEmpty(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
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
    var seq=new IntArrSeq.UncheckedList();
    Assertions.assertSame(OmniArray.OfFloat.DEFAULT_ARR,seq.toFloatArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedListtoFloatArray_void_seqIsNotEmpty(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
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
    var seq=new IntArrSeq.UncheckedList();
    Assertions.assertSame(OmniArray.OfLong.DEFAULT_ARR,seq.toLongArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedListtoLongArray_void_seqIsNotEmpty(){
    var seq=new IntArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
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
  public void testCheckedStackconstructor_void_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.CheckedStack();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertSame(OmniArray.OfInt.DEFAULT_ARR,seq.arr);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackconstructor_intint_initialCapacityNULL(){
    var seq=new IntArrSeq.CheckedStack(0,null);
    Assertions.assertEquals(0,seq.size);
    Assertions.assertNull(seq.arr);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackconstructor_int_initialCapacity50(){
    var seq=new IntArrSeq.CheckedStack(50);
    Assertions.assertEquals(0,seq.size);
    switch(50){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfInt.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(50,seq.arr.length);
    }
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackconstructor_int_initialCapacity0(){
    var seq=new IntArrSeq.CheckedStack(0);
    Assertions.assertEquals(0,seq.size);
    switch(0){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfInt.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(0,seq.arr.length);
    }
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackconstructor_int_initialCapacity10(){
    var seq=new IntArrSeq.CheckedStack(10);
    Assertions.assertEquals(0,seq.size);
    switch(10){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfInt.DEFAULT_ARR,seq.arr);
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
    var seq=new IntArrSeq.CheckedStack();
    var clone=(IntArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfInt.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackClone_initialCapacityDEFAULT_seqIsNotEmpty()
  {
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var clone=(IntArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedStackClone_initialCapacityNULL_seqIsEmpty()
  {
    var seq=new IntArrSeq.CheckedStack(0,null);
    var clone=(IntArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfInt.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackClone_initialCapacityNULL_seqIsNotEmpty()
  {
    var seq=new IntArrSeq.CheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var clone=(IntArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedStackClone_initialCapacity50_seqIsEmpty()
  {
    var seq=new IntArrSeq.CheckedStack(50);
    var clone=(IntArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfInt.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackClone_initialCapacity50_seqIsNotEmpty()
  {
    var seq=new IntArrSeq.CheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var clone=(IntArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedStackClone_initialCapacity0_seqIsEmpty()
  {
    var seq=new IntArrSeq.CheckedStack(0);
    var clone=(IntArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfInt.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackClone_initialCapacity0_seqIsNotEmpty()
  {
    var seq=new IntArrSeq.CheckedStack(0);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var clone=(IntArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedStackClone_initialCapacity10_seqIsEmpty()
  {
    var seq=new IntArrSeq.CheckedStack(10);
    var clone=(IntArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfInt.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackClone_initialCapacity10_seqIsNotEmpty()
  {
    var seq=new IntArrSeq.CheckedStack(10);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var clone=(IntArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedStacktoArray_ObjectArray_zeroLengthArrayAndSequenceIsEmpty(){
    var seq=new IntArrSeq.CheckedStack();
    Integer[] paramArr=new Integer[0];
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertEquals(0,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
  }
  @Test
  public void testCheckedStacktoArray_ObjectArray_zeroLengthArrayAndSequenceNotEmpty(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    Integer[] paramArr=new Integer[0];
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
    var seq=new IntArrSeq.CheckedStack();
    Integer[] paramArr=new Integer[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToint(paramArr.length);
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertEquals(5,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    Assertions.assertNull(result[0]);
    for(int i=1;i<result.length;++i){
      Assertions.assertEquals((Object)TypeConversionUtil.convertToint(paramArr.length),result[i]);
    }
  }
  @Test
  public void testCheckedStacktoArray_ObjectArray_overSizedArray(){
    var seq=new IntArrSeq.CheckedStack();
    Integer[] paramArr=new Integer[10];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToint(paramArr.length);
    }
    for(int i=0;i<5;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
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
      Assertions.assertEquals((Object)TypeConversionUtil.convertToint(paramArr.length),result[i]);
    }
  }
  @Test
  public void testCheckedStacktoArray_ObjectArray_undersizedArray(){
    var seq=new IntArrSeq.CheckedStack();
    Integer[] paramArr=new Integer[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToint(10);
    }
    for(int i=0;i<10;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
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
    var seq=new IntArrSeq.CheckedStack();
    Integer[] paramArr=new Integer[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToint(5);
    }
    for(int i=0;i<5;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
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
    var seq=new IntArrSeq.CheckedStack();
    IntFunction<Integer[]> arrConstructor=Integer[]::new;
    var result=seq.toArray(arrConstructor);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertEquals(0,result.length);
  }
  @Test
  public void testCheckedStacktoArray_IntFunction_seqIsNotEmpty_nonMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    IntFunction<Integer[]> arrConstructor=Integer[]::new;
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
    var seq=new IntArrSeq.CheckedStack();
    IntFunction<Integer[]> arrConstructor=(int arrSize)->{
      seq.add(TypeConversionUtil.convertToint(arrSize));
      return new Integer[arrSize];
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(1,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertToint(0),seq.arr[0]);
    Assertions.assertEquals(1,seq.modCount);
  }
  @Test
  public void testCheckedStacktoArray_IntFunction_seqIsNotEmpty_moddingArrayConstructor(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    IntFunction<Integer[]> arrConstructor=(int arrSize)->{
      seq.add(TypeConversionUtil.convertToint(arrSize));
      return new Integer[arrSize];
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(101,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertToint(100),seq.arr[100]);
    Assertions.assertEquals(101,seq.modCount);
  }
  @Test
  public void testCheckedStacktoArray_IntFunction_seqIsEmpty_throwingArrayConstructor(){
    var seq=new IntArrSeq.CheckedStack();
    IntFunction<Integer[]> arrConstructor=(int arrSize)->{
      throw new IndexOutOfBoundsException();
    };
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStacktoArray_IntFunction_seqIsNotEmpty_throwingArrayConstructor(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    IntFunction<Integer[]> arrConstructor=(int arrSize)->{
      throw new IndexOutOfBoundsException();
    };
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedStacktoArray_IntFunction_seqIsEmpty_throwingAndModdingArrConstructor(){
    var seq=new IntArrSeq.CheckedStack();
    IntFunction<Integer[]> arrConstructor=(int arrSize)->{
      seq.add(TypeConversionUtil.convertToint(arrSize));
      throw new IndexOutOfBoundsException();
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(1,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertToint(0),seq.arr[0]);
    Assertions.assertEquals(1,seq.modCount);
  }
  @Test
  public void testCheckedStacktoArray_IntFunction_seqIsNotEmpty_throwingAndModdingArrConstructor(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    IntFunction<Integer[]> arrConstructor=(int arrSize)->{
      seq.add(TypeConversionUtil.convertToint(arrSize));
      throw new IndexOutOfBoundsException();
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(101,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertToint(100),seq.arr[100]);
    Assertions.assertEquals(101,seq.modCount);
  }
  @Test
  public void testCheckedStackclear_void_seqIsEmpty(){
    var seq=new IntArrSeq.CheckedStack();
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackclear_void_seqIsNotEmpty(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
    for(int i=0;i<100;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
    Assertions.assertEquals(101,seq.modCount);
  }
  @Test
  public void testCheckedStackisEmpty_void_seqIsEmpty(){
    var seq=new IntArrSeq.CheckedStack();
    Assertions.assertTrue(seq.isEmpty());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackisEmpty_void_seqIsNotEmpty(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    Assertions.assertFalse(seq.isEmpty());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedStackisEmpty_void_seqIsBeingCleared(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;){
      Assertions.assertFalse(seq.isEmpty());
      itr.nextInt();
      itr.remove();
      Assertions.assertEquals(100+(100-i),seq.modCount);
    }
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testCheckedStacksize_void_seqIsEmpty(){
    var seq=new IntArrSeq.CheckedStack();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStacksize_void_seqIsNotEmpty(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedStacksize_void_seqIsBeingCleared(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;){
      itr.nextInt();
      itr.remove();
      Assertions.assertEquals(i,seq.size());
      Assertions.assertEquals(100+(100-i),seq.modCount);
    }
  }
  @Test
  public void testCheckedStackforEach_Consumer_SeqIsEmpty_NoMod(){
    var seq=new IntArrSeq.CheckedStack();
    var consumer=new IntMonitoredConsumer();
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedStackforEach_Consumer_SeqIsNotEmpty_NoMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var consumer=new IntMonitoredConsumer();
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
    var seq=new IntArrSeq.CheckedStack();
    var consumer=new IntMonitoredConsumer.ModifyingArrSeqCheckedStackConsumer(seq);
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedStackforEach_Consumer_SeqIsNotEmpty_ModdingConsumer(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var consumer=new IntMonitoredConsumer.ModifyingArrSeqCheckedStackConsumer(seq);
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
    var seq=new IntArrSeq.CheckedStack();
    var consumer=new IntMonitoredConsumer.Throwing();
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedStackforEach_Consumer_SeqIsNotEmpty_ThrowingConsumer(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var consumer=new IntMonitoredConsumer.Throwing();
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
    var seq=new IntArrSeq.CheckedStack();
    var consumer=new IntMonitoredConsumer.ModifyingArrSeqCheckedStackAndThrowingConsumer(seq);
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedStackforEach_Consumer_SeqIsNotEmpty_ThrowingAndModdingConsumer(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var consumer=new IntMonitoredConsumer.ModifyingArrSeqCheckedStackAndThrowingConsumer(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.forEach((Consumer)consumer));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(102,seq.modCount);
    Assertions.assertEquals(1,consumer.size());
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_RetainSecondArrSeqCheckedStackModifyingPredicate()
  {
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_RetainSecondAndLastArrSeqCheckedStackModifyingPredicate()
  {
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveFirstAndThirdArrSeqCheckedStackModifyingPredicate()
  {
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveFirstArrSeqCheckedStackModifyingPredicate()
  {
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveFirstAndSecondToLastArrSeqCheckedStackModifyingPredicate()
  {
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  public void testCheckedStackremoveIf_Predicate_SeqSize3_ThrowAndMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.ModifyingArrSeqCheckedStackAndThrowingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3+1,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(1,filter.callCount);
    Assertions.assertEquals(3+1,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize3_Throwing(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.ThrowingPredicate();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(1,filter.callCount);
    Assertions.assertEquals(3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize3_RemoveAllMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize3_RemoveNoneMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNoneArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize3_RetainSecondMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize3_RetainSecondAndLastMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize3_RemoveFirstAndThirdMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize3_RemoveFirstMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize3_RemoveFirstAndSecondToLastMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  public void testCheckedStackremoveIf_Predicate_SeqSize50_ThrowAndMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.ModifyingArrSeqCheckedStackAndThrowingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50+1,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(1,filter.callCount);
    Assertions.assertEquals(50+1,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize50_Throwing(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.ThrowingPredicate();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(1,filter.callCount);
    Assertions.assertEquals(50,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize50_RemoveAllMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize50_RemoveNoneMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNoneArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize50_RetainSecondMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize50_RetainSecondAndLastMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize50_RemoveFirstAndThirdMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize50_RemoveFirstMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize50_RemoveFirstAndSecondToLastMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  public void testCheckedStackremoveIf_Predicate_SeqSize100_ThrowAndMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.ModifyingArrSeqCheckedStackAndThrowingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100+1,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(1,filter.callCount);
    Assertions.assertEquals(100+1,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize100_Throwing(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.ThrowingPredicate();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(1,filter.callCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize100_RemoveAllMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize100_RemoveNoneMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNoneArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize100_RetainSecondMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize100_RetainSecondAndLastMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize100_RemoveFirstAndThirdMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize100_RemoveFirstMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize100_RemoveFirstAndSecondToLastMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_RetainSecondPredicate()
  {
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_RetainSecondAndLastPredicate()
  {
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveFirstAndThirdPredicate()
  {
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveFirstPredicate()
  {
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveFirstAndSecondToLastPredicate()
  {
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize3_RemoveAll(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize3_RemoveNone(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNonePredicate();
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize3_RetainSecond(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(1,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize3_RetainSecondAndLast(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(2,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize3_RemoveFirstAndThird(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3-2,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize3_RemoveFirst(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3-1,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize3_RemoveFirstAndSecondToLast(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3-2,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize50_RemoveAll(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize50_RemoveNone(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNonePredicate();
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize50_RetainSecond(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(1,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize50_RetainSecondAndLast(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(2,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize50_RemoveFirstAndThird(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50-2,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize50_RemoveFirst(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50-1,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize50_RemoveFirstAndSecondToLast(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50-2,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize100_RemoveAll(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize100_RemoveNone(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNonePredicate();
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize100_RetainSecond(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(1,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize100_RetainSecondAndLast(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(2,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize100_RemoveFirstAndThird(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100-2,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize100_RemoveFirst(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100-1,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize100_RemoveFirstAndSecondToLast(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100-2,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveAllPredicate()
  {
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveNonePredicate()
  {
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNonePredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveAllArrSeqCheckedStackModifyingPredicate()
  {
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveNoneArrSeqCheckedStackModifyingPredicate()
  {
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNoneArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_ThrowingPredicate()
  {
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.ThrowingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_ModifyingArrSeqCheckedStackAndThrowingPredicate()
  {
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.ModifyingArrSeqCheckedStackAndThrowingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackforEach_IntConsumer_SeqIsEmpty_NoMod(){
    var seq=new IntArrSeq.CheckedStack();
    var consumer=new IntMonitoredConsumer();
    seq.forEach((IntConsumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedStackforEach_IntConsumer_SeqIsNotEmpty_NoMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var consumer=new IntMonitoredConsumer();
    seq.forEach((IntConsumer)consumer);
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
  public void testCheckedStackforEach_IntConsumer_SeqIsEmpty_ModdingConsumer(){
    var seq=new IntArrSeq.CheckedStack();
    var consumer=new IntMonitoredConsumer.ModifyingArrSeqCheckedStackConsumer(seq);
    seq.forEach((IntConsumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedStackforEach_IntConsumer_SeqIsNotEmpty_ModdingConsumer(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var consumer=new IntMonitoredConsumer.ModifyingArrSeqCheckedStackConsumer(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.forEach((IntConsumer)consumer));
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
  public void testCheckedStackforEach_IntConsumer_SeqIsEmpty_ThrowingConsumer(){
    var seq=new IntArrSeq.CheckedStack();
    var consumer=new IntMonitoredConsumer.Throwing();
    seq.forEach((IntConsumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedStackforEach_IntConsumer_SeqIsNotEmpty_ThrowingConsumer(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var consumer=new IntMonitoredConsumer.Throwing();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.forEach((IntConsumer)consumer));
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
  public void testCheckedStackforEach_IntConsumer_SeqIsEmpty_ThrowingAndModdingConsumer(){
    var seq=new IntArrSeq.CheckedStack();
    var consumer=new IntMonitoredConsumer.ModifyingArrSeqCheckedStackAndThrowingConsumer(seq);
    seq.forEach((IntConsumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedStackforEach_IntConsumer_SeqIsNotEmpty_ThrowingAndModdingConsumer(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var consumer=new IntMonitoredConsumer.ModifyingArrSeqCheckedStackAndThrowingConsumer(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.forEach((IntConsumer)consumer));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(102,seq.modCount);
    Assertions.assertEquals(1,consumer.size());
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqIsEmpty_RetainSecondArrSeqCheckedStackModifyingPredicate()
  {
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqIsEmpty_RetainSecondAndLastArrSeqCheckedStackModifyingPredicate()
  {
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqIsEmpty_RemoveFirstAndThirdArrSeqCheckedStackModifyingPredicate()
  {
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqIsEmpty_RemoveFirstArrSeqCheckedStackModifyingPredicate()
  {
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqIsEmpty_RemoveFirstAndSecondToLastArrSeqCheckedStackModifyingPredicate()
  {
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  public void testCheckedStackremoveIf_IntPredicate_SeqSize3_ThrowAndMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.ModifyingArrSeqCheckedStackAndThrowingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(3+1,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(1,filter.callCount);
    Assertions.assertEquals(3+1,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize3_Throwing(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.ThrowingPredicate();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(1,filter.callCount);
    Assertions.assertEquals(3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize3_RemoveAllMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize3_RemoveNoneMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNoneArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize3_RetainSecondMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize3_RetainSecondAndLastMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize3_RemoveFirstAndThirdMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize3_RemoveFirstMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize3_RemoveFirstAndSecondToLastMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  public void testCheckedStackremoveIf_IntPredicate_SeqSize50_ThrowAndMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.ModifyingArrSeqCheckedStackAndThrowingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(50+1,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(1,filter.callCount);
    Assertions.assertEquals(50+1,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize50_Throwing(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.ThrowingPredicate();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(1,filter.callCount);
    Assertions.assertEquals(50,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize50_RemoveAllMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize50_RemoveNoneMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNoneArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize50_RetainSecondMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize50_RetainSecondAndLastMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize50_RemoveFirstAndThirdMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize50_RemoveFirstMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize50_RemoveFirstAndSecondToLastMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  public void testCheckedStackremoveIf_IntPredicate_SeqSize100_ThrowAndMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.ModifyingArrSeqCheckedStackAndThrowingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(100+1,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(1,filter.callCount);
    Assertions.assertEquals(100+1,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize100_Throwing(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.ThrowingPredicate();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(1,filter.callCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize100_RemoveAllMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize100_RemoveNoneMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNoneArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize100_RetainSecondMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize100_RetainSecondAndLastMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize100_RemoveFirstAndThirdMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize100_RemoveFirstMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize100_RemoveFirstAndSecondToLastMod(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqIsEmpty_RetainSecondPredicate()
  {
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondPredicate(seq);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqIsEmpty_RetainSecondAndLastPredicate()
  {
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqIsEmpty_RemoveFirstAndThirdPredicate()
  {
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdPredicate(seq);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqIsEmpty_RemoveFirstPredicate()
  {
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstPredicate(seq);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqIsEmpty_RemoveFirstAndSecondToLastPredicate()
  {
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize3_RemoveAll(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize3_RemoveNone(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNonePredicate();
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize3_RetainSecond(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(1,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize3_RetainSecondAndLast(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(2,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize3_RemoveFirstAndThird(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(3-2,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize3_RemoveFirst(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(3-1,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize3_RemoveFirstAndSecondToLast(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(3-2,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize50_RemoveAll(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize50_RemoveNone(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNonePredicate();
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize50_RetainSecond(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(1,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize50_RetainSecondAndLast(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(2,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize50_RemoveFirstAndThird(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(50-2,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize50_RemoveFirst(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(50-1,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize50_RemoveFirstAndSecondToLast(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(50-2,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize100_RemoveAll(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize100_RemoveNone(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNonePredicate();
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize100_RetainSecond(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(1,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize100_RetainSecondAndLast(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(2,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize100_RemoveFirstAndThird(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(100-2,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize100_RemoveFirst(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(100-1,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqSize100_RemoveFirstAndSecondToLast(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(100-2,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqIsEmpty_RemoveAllPredicate()
  {
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllPredicate(seq);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqIsEmpty_RemoveNonePredicate()
  {
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNonePredicate(seq);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqIsEmpty_RemoveAllArrSeqCheckedStackModifyingPredicate()
  {
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqIsEmpty_RemoveNoneArrSeqCheckedStackModifyingPredicate()
  {
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNoneArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqIsEmpty_ThrowingPredicate()
  {
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.ThrowingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_IntPredicate_SeqIsEmpty_ModifyingArrSeqCheckedStackAndThrowingPredicate()
  {
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.ModifyingArrSeqCheckedStackAndThrowingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackadd_int_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_int_initialCapacityNULL(){
    var seq=new IntArrSeq.CheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_int_initialCapacity50(){
    var seq=new IntArrSeq.CheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_Integer_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToInteger(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_Integer_initialCapacityNULL(){
    var seq=new IntArrSeq.CheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToInteger(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_Integer_initialCapacity50(){
    var seq=new IntArrSeq.CheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToInteger(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_boolean_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_boolean_initialCapacityNULL(){
    var seq=new IntArrSeq.CheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_boolean_initialCapacity50(){
    var seq=new IntArrSeq.CheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_Boolean_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_Boolean_initialCapacityNULL(){
    var seq=new IntArrSeq.CheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_Boolean_initialCapacity50(){
    var seq=new IntArrSeq.CheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_byte_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTobyte(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_byte_initialCapacityNULL(){
    var seq=new IntArrSeq.CheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTobyte(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_byte_initialCapacity50(){
    var seq=new IntArrSeq.CheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTobyte(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_Byte_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToByte(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_Byte_initialCapacityNULL(){
    var seq=new IntArrSeq.CheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToByte(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_Byte_initialCapacity50(){
    var seq=new IntArrSeq.CheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToByte(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_char_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_char_initialCapacityNULL(){
    var seq=new IntArrSeq.CheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_char_initialCapacity50(){
    var seq=new IntArrSeq.CheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_Character_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToCharacter(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_Character_initialCapacityNULL(){
    var seq=new IntArrSeq.CheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToCharacter(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_Character_initialCapacity50(){
    var seq=new IntArrSeq.CheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToCharacter(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_short_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToshort(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_short_initialCapacityNULL(){
    var seq=new IntArrSeq.CheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToshort(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_short_initialCapacity50(){
    var seq=new IntArrSeq.CheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToshort(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_Short_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToShort(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_Short_initialCapacityNULL(){
    var seq=new IntArrSeq.CheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToShort(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_Short_initialCapacity50(){
    var seq=new IntArrSeq.CheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToShort(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStacktoIntArray_void_seqIsEmpty(){
    var seq=new IntArrSeq.CheckedStack();
    Assertions.assertSame(OmniArray.OfInt.DEFAULT_ARR,seq.toIntArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStacktoIntArray_void_seqIsNotEmpty(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
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
  public void testCheckedStacktoArray_void_seqIsEmpty(){
    var seq=new IntArrSeq.CheckedStack();
    Assertions.assertSame(OmniArray.OfInt.DEFAULT_BOXED_ARR,seq.toArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStacktoArray_void_seqIsNotEmpty(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
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
    var seq=new IntArrSeq.CheckedStack();
    Assertions.assertSame(OmniArray.OfDouble.DEFAULT_ARR,seq.toDoubleArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStacktoDoubleArray_void_seqIsNotEmpty(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
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
    var seq=new IntArrSeq.CheckedStack();
    Assertions.assertSame(OmniArray.OfFloat.DEFAULT_ARR,seq.toFloatArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStacktoFloatArray_void_seqIsNotEmpty(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
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
    var seq=new IntArrSeq.CheckedStack();
    Assertions.assertSame(OmniArray.OfLong.DEFAULT_ARR,seq.toLongArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStacktoLongArray_void_seqIsNotEmpty(){
    var seq=new IntArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
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
  public void testCheckedListconstructor_void_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.CheckedList();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertSame(OmniArray.OfInt.DEFAULT_ARR,seq.arr);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListconstructor_intint_initialCapacityNULL(){
    var seq=new IntArrSeq.CheckedList(0,null);
    Assertions.assertEquals(0,seq.size);
    Assertions.assertNull(seq.arr);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListconstructor_int_initialCapacity50(){
    var seq=new IntArrSeq.CheckedList(50);
    Assertions.assertEquals(0,seq.size);
    switch(50){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfInt.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(50,seq.arr.length);
    }
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListconstructor_int_initialCapacity0(){
    var seq=new IntArrSeq.CheckedList(0);
    Assertions.assertEquals(0,seq.size);
    switch(0){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfInt.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(0,seq.arr.length);
    }
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListconstructor_int_initialCapacity10(){
    var seq=new IntArrSeq.CheckedList(10);
    Assertions.assertEquals(0,seq.size);
    switch(10){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfInt.DEFAULT_ARR,seq.arr);
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
    var seq=new IntArrSeq.CheckedList();
    var clone=(IntArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfInt.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListClone_initialCapacityDEFAULT_seqIsNotEmpty()
  {
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var clone=(IntArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListClone_initialCapacityNULL_seqIsEmpty()
  {
    var seq=new IntArrSeq.CheckedList(0,null);
    var clone=(IntArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfInt.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListClone_initialCapacityNULL_seqIsNotEmpty()
  {
    var seq=new IntArrSeq.CheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var clone=(IntArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListClone_initialCapacity50_seqIsEmpty()
  {
    var seq=new IntArrSeq.CheckedList(50);
    var clone=(IntArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfInt.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListClone_initialCapacity50_seqIsNotEmpty()
  {
    var seq=new IntArrSeq.CheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var clone=(IntArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListClone_initialCapacity0_seqIsEmpty()
  {
    var seq=new IntArrSeq.CheckedList(0);
    var clone=(IntArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfInt.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListClone_initialCapacity0_seqIsNotEmpty()
  {
    var seq=new IntArrSeq.CheckedList(0);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var clone=(IntArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListClone_initialCapacity10_seqIsEmpty()
  {
    var seq=new IntArrSeq.CheckedList(10);
    var clone=(IntArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfInt.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListClone_initialCapacity10_seqIsNotEmpty()
  {
    var seq=new IntArrSeq.CheckedList(10);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var clone=(IntArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListtoArray_ObjectArray_zeroLengthArrayAndSequenceIsEmpty(){
    var seq=new IntArrSeq.CheckedList();
    Integer[] paramArr=new Integer[0];
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertEquals(0,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
  }
  @Test
  public void testCheckedListtoArray_ObjectArray_zeroLengthArrayAndSequenceNotEmpty(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    Integer[] paramArr=new Integer[0];
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
    var seq=new IntArrSeq.CheckedList();
    Integer[] paramArr=new Integer[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToint(paramArr.length);
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertEquals(5,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    Assertions.assertNull(result[0]);
    for(int i=1;i<result.length;++i){
      Assertions.assertEquals((Object)TypeConversionUtil.convertToint(paramArr.length),result[i]);
    }
  }
  @Test
  public void testCheckedListtoArray_ObjectArray_overSizedArray(){
    var seq=new IntArrSeq.CheckedList();
    Integer[] paramArr=new Integer[10];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToint(paramArr.length);
    }
    for(int i=0;i<5;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
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
      Assertions.assertEquals((Object)TypeConversionUtil.convertToint(paramArr.length),result[i]);
    }
  }
  @Test
  public void testCheckedListtoArray_ObjectArray_undersizedArray(){
    var seq=new IntArrSeq.CheckedList();
    Integer[] paramArr=new Integer[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToint(10);
    }
    for(int i=0;i<10;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
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
    var seq=new IntArrSeq.CheckedList();
    Integer[] paramArr=new Integer[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToint(5);
    }
    for(int i=0;i<5;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
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
    var seq=new IntArrSeq.CheckedList();
    IntFunction<Integer[]> arrConstructor=Integer[]::new;
    var result=seq.toArray(arrConstructor);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertEquals(0,result.length);
  }
  @Test
  public void testCheckedListtoArray_IntFunction_seqIsNotEmpty_nonMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    IntFunction<Integer[]> arrConstructor=Integer[]::new;
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
    var seq=new IntArrSeq.CheckedList();
    IntFunction<Integer[]> arrConstructor=(int arrSize)->{
      seq.add(TypeConversionUtil.convertToint(arrSize));
      return new Integer[arrSize];
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(1,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertToint(0),seq.arr[0]);
    Assertions.assertEquals(1,seq.modCount);
  }
  @Test
  public void testCheckedListtoArray_IntFunction_seqIsNotEmpty_moddingArrayConstructor(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    IntFunction<Integer[]> arrConstructor=(int arrSize)->{
      seq.add(TypeConversionUtil.convertToint(arrSize));
      return new Integer[arrSize];
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(101,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertToint(100),seq.arr[100]);
    Assertions.assertEquals(101,seq.modCount);
  }
  @Test
  public void testCheckedListtoArray_IntFunction_seqIsEmpty_throwingArrayConstructor(){
    var seq=new IntArrSeq.CheckedList();
    IntFunction<Integer[]> arrConstructor=(int arrSize)->{
      throw new IndexOutOfBoundsException();
    };
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListtoArray_IntFunction_seqIsNotEmpty_throwingArrayConstructor(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    IntFunction<Integer[]> arrConstructor=(int arrSize)->{
      throw new IndexOutOfBoundsException();
    };
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListtoArray_IntFunction_seqIsEmpty_throwingAndModdingArrConstructor(){
    var seq=new IntArrSeq.CheckedList();
    IntFunction<Integer[]> arrConstructor=(int arrSize)->{
      seq.add(TypeConversionUtil.convertToint(arrSize));
      throw new IndexOutOfBoundsException();
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(1,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertToint(0),seq.arr[0]);
    Assertions.assertEquals(1,seq.modCount);
  }
  @Test
  public void testCheckedListtoArray_IntFunction_seqIsNotEmpty_throwingAndModdingArrConstructor(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    IntFunction<Integer[]> arrConstructor=(int arrSize)->{
      seq.add(TypeConversionUtil.convertToint(arrSize));
      throw new IndexOutOfBoundsException();
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(101,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertToint(100),seq.arr[100]);
    Assertions.assertEquals(101,seq.modCount);
  }
  @Test
  public void testCheckedListclear_void_seqIsEmpty(){
    var seq=new IntArrSeq.CheckedList();
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListclear_void_seqIsNotEmpty(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
    for(int i=0;i<100;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
    Assertions.assertEquals(101,seq.modCount);
  }
  @Test
  public void testCheckedListisEmpty_void_seqIsEmpty(){
    var seq=new IntArrSeq.CheckedList();
    Assertions.assertTrue(seq.isEmpty());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListisEmpty_void_seqIsNotEmpty(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    Assertions.assertFalse(seq.isEmpty());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListisEmpty_void_seqIsBeingCleared(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;){
      Assertions.assertFalse(seq.isEmpty());
      itr.nextInt();
      itr.remove();
      Assertions.assertEquals(100+(100-i),seq.modCount);
    }
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testCheckedListsize_void_seqIsEmpty(){
    var seq=new IntArrSeq.CheckedList();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListsize_void_seqIsNotEmpty(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListsize_void_seqIsBeingCleared(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;){
      itr.nextInt();
      itr.remove();
      Assertions.assertEquals(i,seq.size());
      Assertions.assertEquals(100+(100-i),seq.modCount);
    }
  }
  @Test
  public void testCheckedListforEach_Consumer_SeqIsEmpty_NoMod(){
    var seq=new IntArrSeq.CheckedList();
    var consumer=new IntMonitoredConsumer();
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedListforEach_Consumer_SeqIsNotEmpty_NoMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var consumer=new IntMonitoredConsumer();
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
    var seq=new IntArrSeq.CheckedList();
    var consumer=new IntMonitoredConsumer.ModifyingArrSeqCheckedListConsumer(seq);
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedListforEach_Consumer_SeqIsNotEmpty_ModdingConsumer(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var consumer=new IntMonitoredConsumer.ModifyingArrSeqCheckedListConsumer(seq);
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
    var seq=new IntArrSeq.CheckedList();
    var consumer=new IntMonitoredConsumer.Throwing();
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedListforEach_Consumer_SeqIsNotEmpty_ThrowingConsumer(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var consumer=new IntMonitoredConsumer.Throwing();
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
    var seq=new IntArrSeq.CheckedList();
    var consumer=new IntMonitoredConsumer.ModifyingArrSeqCheckedListAndThrowingConsumer(seq);
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedListforEach_Consumer_SeqIsNotEmpty_ThrowingAndModdingConsumer(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var consumer=new IntMonitoredConsumer.ModifyingArrSeqCheckedListAndThrowingConsumer(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.forEach((Consumer)consumer));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(102,seq.modCount);
    Assertions.assertEquals(1,consumer.size());
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_RetainSecondArrSeqCheckedListModifyingPredicate()
  {
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_RetainSecondAndLastArrSeqCheckedListModifyingPredicate()
  {
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_RemoveFirstAndThirdArrSeqCheckedListModifyingPredicate()
  {
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_RemoveFirstArrSeqCheckedListModifyingPredicate()
  {
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_RemoveFirstAndSecondToLastArrSeqCheckedListModifyingPredicate()
  {
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  public void testCheckedListremoveIf_Predicate_SeqSize3_ThrowAndMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.ModifyingArrSeqCheckedListAndThrowingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3+1,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(1,filter.callCount);
    Assertions.assertEquals(3+1,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize3_Throwing(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.ThrowingPredicate();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(1,filter.callCount);
    Assertions.assertEquals(3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize3_RemoveAllMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize3_RemoveNoneMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNoneArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize3_RetainSecondMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize3_RetainSecondAndLastMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize3_RemoveFirstAndThirdMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize3_RemoveFirstMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize3_RemoveFirstAndSecondToLastMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  public void testCheckedListremoveIf_Predicate_SeqSize50_ThrowAndMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.ModifyingArrSeqCheckedListAndThrowingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50+1,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(1,filter.callCount);
    Assertions.assertEquals(50+1,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize50_Throwing(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.ThrowingPredicate();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(1,filter.callCount);
    Assertions.assertEquals(50,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize50_RemoveAllMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize50_RemoveNoneMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNoneArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize50_RetainSecondMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize50_RetainSecondAndLastMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize50_RemoveFirstAndThirdMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize50_RemoveFirstMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize50_RemoveFirstAndSecondToLastMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  public void testCheckedListremoveIf_Predicate_SeqSize100_ThrowAndMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.ModifyingArrSeqCheckedListAndThrowingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100+1,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(1,filter.callCount);
    Assertions.assertEquals(100+1,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize100_Throwing(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.ThrowingPredicate();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(1,filter.callCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize100_RemoveAllMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize100_RemoveNoneMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNoneArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize100_RetainSecondMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize100_RetainSecondAndLastMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize100_RemoveFirstAndThirdMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize100_RemoveFirstMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize100_RemoveFirstAndSecondToLastMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_RetainSecondPredicate()
  {
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_RetainSecondAndLastPredicate()
  {
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_RemoveFirstAndThirdPredicate()
  {
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_RemoveFirstPredicate()
  {
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_RemoveFirstAndSecondToLastPredicate()
  {
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize3_RemoveAll(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize3_RemoveNone(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNonePredicate();
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize3_RetainSecond(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(1,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize3_RetainSecondAndLast(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(2,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize3_RemoveFirstAndThird(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3-2,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize3_RemoveFirst(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3-1,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize3_RemoveFirstAndSecondToLast(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3-2,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize50_RemoveAll(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize50_RemoveNone(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNonePredicate();
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize50_RetainSecond(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(1,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize50_RetainSecondAndLast(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(2,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize50_RemoveFirstAndThird(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50-2,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize50_RemoveFirst(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50-1,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize50_RemoveFirstAndSecondToLast(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50-2,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize100_RemoveAll(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize100_RemoveNone(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNonePredicate();
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize100_RetainSecond(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(1,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize100_RetainSecondAndLast(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(2,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize100_RemoveFirstAndThird(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100-2,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize100_RemoveFirst(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100-1,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize100_RemoveFirstAndSecondToLast(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100-2,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_RemoveAllPredicate()
  {
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_RemoveNonePredicate()
  {
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNonePredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_RemoveAllArrSeqCheckedListModifyingPredicate()
  {
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_RemoveNoneArrSeqCheckedListModifyingPredicate()
  {
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNoneArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_ThrowingPredicate()
  {
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.ThrowingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_ModifyingArrSeqCheckedListAndThrowingPredicate()
  {
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.ModifyingArrSeqCheckedListAndThrowingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListforEach_IntConsumer_SeqIsEmpty_NoMod(){
    var seq=new IntArrSeq.CheckedList();
    var consumer=new IntMonitoredConsumer();
    seq.forEach((IntConsumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedListforEach_IntConsumer_SeqIsNotEmpty_NoMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var consumer=new IntMonitoredConsumer();
    seq.forEach((IntConsumer)consumer);
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
  public void testCheckedListforEach_IntConsumer_SeqIsEmpty_ModdingConsumer(){
    var seq=new IntArrSeq.CheckedList();
    var consumer=new IntMonitoredConsumer.ModifyingArrSeqCheckedListConsumer(seq);
    seq.forEach((IntConsumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedListforEach_IntConsumer_SeqIsNotEmpty_ModdingConsumer(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var consumer=new IntMonitoredConsumer.ModifyingArrSeqCheckedListConsumer(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.forEach((IntConsumer)consumer));
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
  public void testCheckedListforEach_IntConsumer_SeqIsEmpty_ThrowingConsumer(){
    var seq=new IntArrSeq.CheckedList();
    var consumer=new IntMonitoredConsumer.Throwing();
    seq.forEach((IntConsumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedListforEach_IntConsumer_SeqIsNotEmpty_ThrowingConsumer(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var consumer=new IntMonitoredConsumer.Throwing();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.forEach((IntConsumer)consumer));
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
  public void testCheckedListforEach_IntConsumer_SeqIsEmpty_ThrowingAndModdingConsumer(){
    var seq=new IntArrSeq.CheckedList();
    var consumer=new IntMonitoredConsumer.ModifyingArrSeqCheckedListAndThrowingConsumer(seq);
    seq.forEach((IntConsumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedListforEach_IntConsumer_SeqIsNotEmpty_ThrowingAndModdingConsumer(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    var consumer=new IntMonitoredConsumer.ModifyingArrSeqCheckedListAndThrowingConsumer(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.forEach((IntConsumer)consumer));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(102,seq.modCount);
    Assertions.assertEquals(1,consumer.size());
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqIsEmpty_RetainSecondArrSeqCheckedListModifyingPredicate()
  {
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqIsEmpty_RetainSecondAndLastArrSeqCheckedListModifyingPredicate()
  {
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqIsEmpty_RemoveFirstAndThirdArrSeqCheckedListModifyingPredicate()
  {
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqIsEmpty_RemoveFirstArrSeqCheckedListModifyingPredicate()
  {
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqIsEmpty_RemoveFirstAndSecondToLastArrSeqCheckedListModifyingPredicate()
  {
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  public void testCheckedListremoveIf_IntPredicate_SeqSize3_ThrowAndMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.ModifyingArrSeqCheckedListAndThrowingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(3+1,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(1,filter.callCount);
    Assertions.assertEquals(3+1,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize3_Throwing(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.ThrowingPredicate();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(1,filter.callCount);
    Assertions.assertEquals(3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize3_RemoveAllMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize3_RemoveNoneMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNoneArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize3_RetainSecondMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize3_RetainSecondAndLastMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize3_RemoveFirstAndThirdMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize3_RemoveFirstMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize3_RemoveFirstAndSecondToLastMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  public void testCheckedListremoveIf_IntPredicate_SeqSize50_ThrowAndMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.ModifyingArrSeqCheckedListAndThrowingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(50+1,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(1,filter.callCount);
    Assertions.assertEquals(50+1,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize50_Throwing(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.ThrowingPredicate();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(1,filter.callCount);
    Assertions.assertEquals(50,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize50_RemoveAllMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize50_RemoveNoneMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNoneArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize50_RetainSecondMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize50_RetainSecondAndLastMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize50_RemoveFirstAndThirdMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize50_RemoveFirstMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize50_RemoveFirstAndSecondToLastMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  public void testCheckedListremoveIf_IntPredicate_SeqSize100_ThrowAndMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.ModifyingArrSeqCheckedListAndThrowingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(100+1,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(1,filter.callCount);
    Assertions.assertEquals(100+1,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize100_Throwing(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.ThrowingPredicate();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(1,filter.callCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize100_RemoveAllMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize100_RemoveNoneMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNoneArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize100_RetainSecondMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize100_RetainSecondAndLastMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize100_RemoveFirstAndThirdMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize100_RemoveFirstMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize100_RemoveFirstAndSecondToLastMod(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqIsEmpty_RetainSecondPredicate()
  {
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondPredicate(seq);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqIsEmpty_RetainSecondAndLastPredicate()
  {
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqIsEmpty_RemoveFirstAndThirdPredicate()
  {
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdPredicate(seq);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqIsEmpty_RemoveFirstPredicate()
  {
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstPredicate(seq);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqIsEmpty_RemoveFirstAndSecondToLastPredicate()
  {
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize3_RemoveAll(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize3_RemoveNone(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNonePredicate();
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize3_RetainSecond(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(1,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize3_RetainSecondAndLast(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(2,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize3_RemoveFirstAndThird(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(3-2,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize3_RemoveFirst(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(3-1,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize3_RemoveFirstAndSecondToLast(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(3-2,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize50_RemoveAll(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize50_RemoveNone(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNonePredicate();
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize50_RetainSecond(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(1,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize50_RetainSecondAndLast(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(2,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize50_RemoveFirstAndThird(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(50-2,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize50_RemoveFirst(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(50-1,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize50_RemoveFirstAndSecondToLast(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(50-2,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize100_RemoveAll(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize100_RemoveNone(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNonePredicate();
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize100_RetainSecond(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(1,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize100_RetainSecondAndLast(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(2,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize100_RemoveFirstAndThird(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndThirdPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(100-2,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize100_RemoveFirst(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstPredicate();
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(100-1,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqSize100_RemoveFirstAndSecondToLast(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(100-2,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqIsEmpty_RemoveAllPredicate()
  {
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllPredicate(seq);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqIsEmpty_RemoveNonePredicate()
  {
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNonePredicate(seq);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqIsEmpty_RemoveAllArrSeqCheckedListModifyingPredicate()
  {
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveAllArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqIsEmpty_RemoveNoneArrSeqCheckedListModifyingPredicate()
  {
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.RemoveNoneArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqIsEmpty_ThrowingPredicate()
  {
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.ThrowingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_IntPredicate_SeqIsEmpty_ModifyingArrSeqCheckedListAndThrowingPredicate()
  {
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToint(i));
    }
    var filter=new IntMonitoredPredicate2.ModifyingArrSeqCheckedListAndThrowingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((IntPredicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_int_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_int_initialCapacityNULL(){
    var seq=new IntArrSeq.CheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_int_initialCapacity50(){
    var seq=new IntArrSeq.CheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_Integer_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToInteger(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_Integer_initialCapacityNULL(){
    var seq=new IntArrSeq.CheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToInteger(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_Integer_initialCapacity50(){
    var seq=new IntArrSeq.CheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToInteger(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_boolean_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_boolean_initialCapacityNULL(){
    var seq=new IntArrSeq.CheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_boolean_initialCapacity50(){
    var seq=new IntArrSeq.CheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_Boolean_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_Boolean_initialCapacityNULL(){
    var seq=new IntArrSeq.CheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_Boolean_initialCapacity50(){
    var seq=new IntArrSeq.CheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_byte_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTobyte(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_byte_initialCapacityNULL(){
    var seq=new IntArrSeq.CheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTobyte(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_byte_initialCapacity50(){
    var seq=new IntArrSeq.CheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTobyte(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_Byte_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToByte(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_Byte_initialCapacityNULL(){
    var seq=new IntArrSeq.CheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToByte(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_Byte_initialCapacity50(){
    var seq=new IntArrSeq.CheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToByte(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_char_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_char_initialCapacityNULL(){
    var seq=new IntArrSeq.CheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_char_initialCapacity50(){
    var seq=new IntArrSeq.CheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_Character_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToCharacter(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_Character_initialCapacityNULL(){
    var seq=new IntArrSeq.CheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToCharacter(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_Character_initialCapacity50(){
    var seq=new IntArrSeq.CheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToCharacter(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_short_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToshort(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_short_initialCapacityNULL(){
    var seq=new IntArrSeq.CheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToshort(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_short_initialCapacity50(){
    var seq=new IntArrSeq.CheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToshort(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_Short_initialCapacityDEFAULT(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToShort(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_Short_initialCapacityNULL(){
    var seq=new IntArrSeq.CheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToShort(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_Short_initialCapacity50(){
    var seq=new IntArrSeq.CheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToShort(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListtoIntArray_void_seqIsEmpty(){
    var seq=new IntArrSeq.CheckedList();
    Assertions.assertSame(OmniArray.OfInt.DEFAULT_ARR,seq.toIntArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListtoIntArray_void_seqIsNotEmpty(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
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
  public void testCheckedListtoArray_void_seqIsEmpty(){
    var seq=new IntArrSeq.CheckedList();
    Assertions.assertSame(OmniArray.OfInt.DEFAULT_BOXED_ARR,seq.toArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListtoArray_void_seqIsNotEmpty(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
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
    var seq=new IntArrSeq.CheckedList();
    Assertions.assertSame(OmniArray.OfDouble.DEFAULT_ARR,seq.toDoubleArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListtoDoubleArray_void_seqIsNotEmpty(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
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
    var seq=new IntArrSeq.CheckedList();
    Assertions.assertSame(OmniArray.OfFloat.DEFAULT_ARR,seq.toFloatArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListtoFloatArray_void_seqIsNotEmpty(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
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
    var seq=new IntArrSeq.CheckedList();
    Assertions.assertSame(OmniArray.OfLong.DEFAULT_ARR,seq.toLongArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListtoLongArray_void_seqIsNotEmpty(){
    var seq=new IntArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToint(i)));
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
}
