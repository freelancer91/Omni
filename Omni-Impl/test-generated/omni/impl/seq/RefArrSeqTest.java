package omni.impl.seq;
//import omni.impl.seq.RefArrSeq.UncheckedList;
//import omni.impl.seq.RefArrSeq.CheckedList;
//import omni.impl.seq.RefArrSeq.UncheckedStack;
//import omni.impl.seq.RefArrSeq.CheckedStack;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.function.IntFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.ConcurrentModificationException;
import omni.util.OmniArray;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class RefArrSeqTest{
  @Test
  public void testUncheckedStackconstructor_void_initialCapacityDEFAULT(){
    var seq=new RefArrSeq.UncheckedStack();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
  }
  @Test
  public void testUncheckedStackconstructor_intObject_initialCapacityNULL(){
    var seq=new RefArrSeq.UncheckedStack(0,null);
    Assertions.assertEquals(0,seq.size);
    Assertions.assertNull(seq.arr);
  }
  @Test
  public void testUncheckedStackconstructor_int_initialCapacity50(){
    var seq=new RefArrSeq.UncheckedStack(50);
    Assertions.assertEquals(0,seq.size);
    switch(50){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(50,seq.arr.length);
      for(int i=0;i<50;++i){
        Assertions.assertNull(seq.arr[i]);
      }
    }
  }
  @Test
  public void testUncheckedStackconstructor_int_initialCapacity0(){
    var seq=new RefArrSeq.UncheckedStack(0);
    Assertions.assertEquals(0,seq.size);
    switch(0){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(0,seq.arr.length);
      for(int i=0;i<0;++i){
        Assertions.assertNull(seq.arr[i]);
      }
    }
  }
  @Test
  public void testUncheckedStackconstructor_int_initialCapacity10(){
    var seq=new RefArrSeq.UncheckedStack(10);
    Assertions.assertEquals(0,seq.size);
    switch(10){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(10,seq.arr.length);
      for(int i=0;i<10;++i){
        Assertions.assertNull(seq.arr[i]);
      }
    }
  }
  @Test
  public void testUncheckedStackClone_initialCapacityDEFAULT_seqIsEmpty()
  {
    var seq=new RefArrSeq.UncheckedStack();
    var clone=(RefArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfRef.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedStackClone_initialCapacityDEFAULT_seqIsNotEmpty()
  {
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var clone=(RefArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),clone.arr[i]);
      Assertions.assertSame(seq.arr[i],clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackClone_initialCapacityNULL_seqIsEmpty()
  {
    var seq=new RefArrSeq.UncheckedStack(0,null);
    var clone=(RefArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfRef.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedStackClone_initialCapacityNULL_seqIsNotEmpty()
  {
    var seq=new RefArrSeq.UncheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var clone=(RefArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),clone.arr[i]);
      Assertions.assertSame(seq.arr[i],clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackClone_initialCapacity50_seqIsEmpty()
  {
    var seq=new RefArrSeq.UncheckedStack(50);
    var clone=(RefArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfRef.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedStackClone_initialCapacity50_seqIsNotEmpty()
  {
    var seq=new RefArrSeq.UncheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var clone=(RefArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),clone.arr[i]);
      Assertions.assertSame(seq.arr[i],clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackClone_initialCapacity0_seqIsEmpty()
  {
    var seq=new RefArrSeq.UncheckedStack(0);
    var clone=(RefArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfRef.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedStackClone_initialCapacity0_seqIsNotEmpty()
  {
    var seq=new RefArrSeq.UncheckedStack(0);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var clone=(RefArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),clone.arr[i]);
      Assertions.assertSame(seq.arr[i],clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackClone_initialCapacity10_seqIsEmpty()
  {
    var seq=new RefArrSeq.UncheckedStack(10);
    var clone=(RefArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfRef.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedStackClone_initialCapacity10_seqIsNotEmpty()
  {
    var seq=new RefArrSeq.UncheckedStack(10);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var clone=(RefArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),clone.arr[i]);
      Assertions.assertSame(seq.arr[i],clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedStacktoArray_ObjectArray_zeroLengthArrayAndSequenceIsEmpty(){
    var seq=new RefArrSeq.UncheckedStack();
    Integer[] paramArr=new Integer[0];
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
  }
  @Test
  public void testUncheckedStacktoArray_ObjectArray_zeroLengthArrayAndSequenceNotEmpty(){
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Integer[] paramArr=new Integer[0];
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertNotSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertSame(itr.next(),result[i]);
    }
  }
  @Test
  public void testUncheckedStacktoArray_ObjectArray_nonzeroLengthArrayAndSequenceIsEmpty(){
    var seq=new RefArrSeq.UncheckedStack();
    Integer[] paramArr=new Integer[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToObject(paramArr.length);
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(5,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    Assertions.assertNull(result[0]);
    for(int i=1;i<result.length;++i){
      Assertions.assertEquals((Object)TypeConversionUtil.convertToObject(paramArr.length),result[i]);
    }
  }
  @Test
  public void testUncheckedStacktoArray_ObjectArray_overSizedArray(){
    var seq=new RefArrSeq.UncheckedStack();
    Integer[] paramArr=new Integer[10];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToObject(paramArr.length);
    }
    for(int i=0;i<5;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(5,seq.size());
    Assertions.assertEquals(10,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<5;++i){
      Assertions.assertSame(itr.next(),result[i]);
    }
    Assertions.assertNull(result[5]);
    for(int i=6;i<result.length;++i){
      Assertions.assertEquals((Object)TypeConversionUtil.convertToObject(paramArr.length),result[i]);
    }
  }
  @Test
  public void testUncheckedStacktoArray_ObjectArray_undersizedArray(){
    var seq=new RefArrSeq.UncheckedStack();
    Integer[] paramArr=new Integer[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToObject(10);
    }
    for(int i=0;i<10;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(10,seq.size());
    Assertions.assertEquals(10,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertNotSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<10;++i){
      Assertions.assertSame(itr.next(),result[i]);
    }
  }
  @Test
  public void testUncheckedStacktoArray_ObjectArray_exactSizeArray(){
    var seq=new RefArrSeq.UncheckedStack();
    Integer[] paramArr=new Integer[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToObject(5);
    }
    for(int i=0;i<5;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(5,seq.size());
    Assertions.assertEquals(5,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<5;++i){
      Assertions.assertSame(itr.next(),result[i]);
    }
  }
  @Test
  public void testUncheckedStacktoArray_IntFunction_seqIsEmpty_nonMod(){
    var seq=new RefArrSeq.UncheckedStack();
    IntFunction<Integer[]> arrConstructor=Integer[]::new;
    var result=seq.toArray(arrConstructor);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertEquals(0,result.length);
  }
  @Test
  public void testUncheckedStacktoArray_IntFunction_seqIsNotEmpty_nonMod(){
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    IntFunction<Integer[]> arrConstructor=Integer[]::new;
    var result=seq.toArray(arrConstructor);
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertSame(itr.next(),result[i]);
    }
  }
  @Test
  public void testUncheckedStackclear_void_seqIsEmpty(){
    var seq=new RefArrSeq.UncheckedStack();
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testUncheckedStackclear_void_seqIsNotEmpty(){
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
    for(int i=0;i<100;++i){
      Assertions.assertNull(seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackisEmpty_void_seqIsEmpty(){
    var seq=new RefArrSeq.UncheckedStack();
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testUncheckedStackisEmpty_void_seqIsNotEmpty(){
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertFalse(seq.isEmpty());
  }
  @Test
  public void testUncheckedStackisEmpty_void_seqIsBeingCleared(){
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;){
      Assertions.assertFalse(seq.isEmpty());
      itr.next();
      itr.remove();
    }
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testUncheckedStacksize_void_seqIsEmpty(){
    var seq=new RefArrSeq.UncheckedStack();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
  }
  @Test
  public void testUncheckedStacksize_void_seqIsNotEmpty(){
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
  }
  @Test
  public void testUncheckedStacksize_void_seqIsBeingCleared(){
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;){
      itr.next();
      itr.remove();
      Assertions.assertEquals(i,seq.size());
    }
  }
  @Test
  public void testUncheckedStackforEach_Consumer_SeqIsEmpty_NoMod(){
    var seq=new RefArrSeq.UncheckedStack();
    var consumer=new RefMonitoredConsumer();
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testUncheckedStackforEach_Consumer_SeqIsNotEmpty_NoMod(){
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var consumer=new RefMonitoredConsumer();
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
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqIsEmpty_RetainSecondPredicate()
  {
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqIsEmpty_RetainSecondAndLastPredicate()
  {
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveFirstAndThirdPredicate()
  {
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndThirdPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveFirstPredicate()
  {
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveFirstAndSecondToLastPredicate()
  {
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize3_RemoveAll(){
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveAllPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize3_RemoveNone(){
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveNonePredicate();
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize3_RetainSecond(){
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(1,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize3_RetainSecondAndLast(){
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(2,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize3_RemoveFirstAndThird(){
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndThirdPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3-2,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize3_RemoveFirst(){
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3-1,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize3_RemoveFirstAndSecondToLast(){
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3-2,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize50_RemoveAll(){
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveAllPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize50_RemoveNone(){
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveNonePredicate();
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize50_RetainSecond(){
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(1,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize50_RetainSecondAndLast(){
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(2,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize50_RemoveFirstAndThird(){
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndThirdPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50-2,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize50_RemoveFirst(){
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50-1,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize50_RemoveFirstAndSecondToLast(){
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50-2,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize100_RemoveAll(){
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveAllPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize100_RemoveNone(){
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveNonePredicate();
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize100_RetainSecond(){
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(1,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize100_RetainSecondAndLast(){
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(2,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize100_RemoveFirstAndThird(){
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndThirdPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100-2,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize100_RemoveFirst(){
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100-1,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqSize100_RemoveFirstAndSecondToLast(){
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100-2,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveAllPredicate()
  {
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveAllPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveNonePredicate()
  {
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveNonePredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedStackadd_Object_initialCapacityDEFAULT(){
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_Object_initialCapacityNULL(){
    var seq=new RefArrSeq.UncheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_Object_initialCapacity50(){
    var seq=new RefArrSeq.UncheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStacktoArray_void_seqIsEmpty(){
    var seq=new RefArrSeq.UncheckedStack();
    Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.toArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStacktoArray_void_seqIsNotEmpty(){
    var seq=new RefArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var result=seq.toArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertSame(itr.next(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testUncheckedListconstructor_void_initialCapacityDEFAULT(){
    var seq=new RefArrSeq.UncheckedList();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
  }
  @Test
  public void testUncheckedListconstructor_intObject_initialCapacityNULL(){
    var seq=new RefArrSeq.UncheckedList(0,null);
    Assertions.assertEquals(0,seq.size);
    Assertions.assertNull(seq.arr);
  }
  @Test
  public void testUncheckedListconstructor_int_initialCapacity50(){
    var seq=new RefArrSeq.UncheckedList(50);
    Assertions.assertEquals(0,seq.size);
    switch(50){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(50,seq.arr.length);
      for(int i=0;i<50;++i){
        Assertions.assertNull(seq.arr[i]);
      }
    }
  }
  @Test
  public void testUncheckedListconstructor_int_initialCapacity0(){
    var seq=new RefArrSeq.UncheckedList(0);
    Assertions.assertEquals(0,seq.size);
    switch(0){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(0,seq.arr.length);
      for(int i=0;i<0;++i){
        Assertions.assertNull(seq.arr[i]);
      }
    }
  }
  @Test
  public void testUncheckedListconstructor_int_initialCapacity10(){
    var seq=new RefArrSeq.UncheckedList(10);
    Assertions.assertEquals(0,seq.size);
    switch(10){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(10,seq.arr.length);
      for(int i=0;i<10;++i){
        Assertions.assertNull(seq.arr[i]);
      }
    }
  }
  @Test
  public void testUncheckedListClone_initialCapacityDEFAULT_seqIsEmpty()
  {
    var seq=new RefArrSeq.UncheckedList();
    var clone=(RefArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfRef.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedListClone_initialCapacityDEFAULT_seqIsNotEmpty()
  {
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var clone=(RefArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),clone.arr[i]);
      Assertions.assertSame(seq.arr[i],clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedListClone_initialCapacityNULL_seqIsEmpty()
  {
    var seq=new RefArrSeq.UncheckedList(0,null);
    var clone=(RefArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfRef.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedListClone_initialCapacityNULL_seqIsNotEmpty()
  {
    var seq=new RefArrSeq.UncheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var clone=(RefArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),clone.arr[i]);
      Assertions.assertSame(seq.arr[i],clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedListClone_initialCapacity50_seqIsEmpty()
  {
    var seq=new RefArrSeq.UncheckedList(50);
    var clone=(RefArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfRef.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedListClone_initialCapacity50_seqIsNotEmpty()
  {
    var seq=new RefArrSeq.UncheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var clone=(RefArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),clone.arr[i]);
      Assertions.assertSame(seq.arr[i],clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedListClone_initialCapacity0_seqIsEmpty()
  {
    var seq=new RefArrSeq.UncheckedList(0);
    var clone=(RefArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfRef.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedListClone_initialCapacity0_seqIsNotEmpty()
  {
    var seq=new RefArrSeq.UncheckedList(0);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var clone=(RefArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),clone.arr[i]);
      Assertions.assertSame(seq.arr[i],clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedListClone_initialCapacity10_seqIsEmpty()
  {
    var seq=new RefArrSeq.UncheckedList(10);
    var clone=(RefArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfRef.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedListClone_initialCapacity10_seqIsNotEmpty()
  {
    var seq=new RefArrSeq.UncheckedList(10);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var clone=(RefArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),clone.arr[i]);
      Assertions.assertSame(seq.arr[i],clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedListtoArray_ObjectArray_zeroLengthArrayAndSequenceIsEmpty(){
    var seq=new RefArrSeq.UncheckedList();
    Integer[] paramArr=new Integer[0];
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
  }
  @Test
  public void testUncheckedListtoArray_ObjectArray_zeroLengthArrayAndSequenceNotEmpty(){
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Integer[] paramArr=new Integer[0];
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertNotSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertSame(itr.next(),result[i]);
    }
  }
  @Test
  public void testUncheckedListtoArray_ObjectArray_nonzeroLengthArrayAndSequenceIsEmpty(){
    var seq=new RefArrSeq.UncheckedList();
    Integer[] paramArr=new Integer[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToObject(paramArr.length);
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(5,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    Assertions.assertNull(result[0]);
    for(int i=1;i<result.length;++i){
      Assertions.assertEquals((Object)TypeConversionUtil.convertToObject(paramArr.length),result[i]);
    }
  }
  @Test
  public void testUncheckedListtoArray_ObjectArray_overSizedArray(){
    var seq=new RefArrSeq.UncheckedList();
    Integer[] paramArr=new Integer[10];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToObject(paramArr.length);
    }
    for(int i=0;i<5;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(5,seq.size());
    Assertions.assertEquals(10,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<5;++i){
      Assertions.assertSame(itr.next(),result[i]);
    }
    Assertions.assertNull(result[5]);
    for(int i=6;i<result.length;++i){
      Assertions.assertEquals((Object)TypeConversionUtil.convertToObject(paramArr.length),result[i]);
    }
  }
  @Test
  public void testUncheckedListtoArray_ObjectArray_undersizedArray(){
    var seq=new RefArrSeq.UncheckedList();
    Integer[] paramArr=new Integer[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToObject(10);
    }
    for(int i=0;i<10;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(10,seq.size());
    Assertions.assertEquals(10,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertNotSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<10;++i){
      Assertions.assertSame(itr.next(),result[i]);
    }
  }
  @Test
  public void testUncheckedListtoArray_ObjectArray_exactSizeArray(){
    var seq=new RefArrSeq.UncheckedList();
    Integer[] paramArr=new Integer[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToObject(5);
    }
    for(int i=0;i<5;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(5,seq.size());
    Assertions.assertEquals(5,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<5;++i){
      Assertions.assertSame(itr.next(),result[i]);
    }
  }
  @Test
  public void testUncheckedListtoArray_IntFunction_seqIsEmpty_nonMod(){
    var seq=new RefArrSeq.UncheckedList();
    IntFunction<Integer[]> arrConstructor=Integer[]::new;
    var result=seq.toArray(arrConstructor);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertEquals(0,result.length);
  }
  @Test
  public void testUncheckedListtoArray_IntFunction_seqIsNotEmpty_nonMod(){
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    IntFunction<Integer[]> arrConstructor=Integer[]::new;
    var result=seq.toArray(arrConstructor);
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertSame(itr.next(),result[i]);
    }
  }
  @Test
  public void testUncheckedListclear_void_seqIsEmpty(){
    var seq=new RefArrSeq.UncheckedList();
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testUncheckedListclear_void_seqIsNotEmpty(){
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
    for(int i=0;i<100;++i){
      Assertions.assertNull(seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListisEmpty_void_seqIsEmpty(){
    var seq=new RefArrSeq.UncheckedList();
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testUncheckedListisEmpty_void_seqIsNotEmpty(){
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertFalse(seq.isEmpty());
  }
  @Test
  public void testUncheckedListisEmpty_void_seqIsBeingCleared(){
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;){
      Assertions.assertFalse(seq.isEmpty());
      itr.next();
      itr.remove();
    }
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testUncheckedListsize_void_seqIsEmpty(){
    var seq=new RefArrSeq.UncheckedList();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
  }
  @Test
  public void testUncheckedListsize_void_seqIsNotEmpty(){
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
  }
  @Test
  public void testUncheckedListsize_void_seqIsBeingCleared(){
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;){
      itr.next();
      itr.remove();
      Assertions.assertEquals(i,seq.size());
    }
  }
  @Test
  public void testUncheckedListforEach_Consumer_SeqIsEmpty_NoMod(){
    var seq=new RefArrSeq.UncheckedList();
    var consumer=new RefMonitoredConsumer();
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testUncheckedListforEach_Consumer_SeqIsNotEmpty_NoMod(){
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var consumer=new RefMonitoredConsumer();
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
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqIsEmpty_RetainSecondPredicate()
  {
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqIsEmpty_RetainSecondAndLastPredicate()
  {
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqIsEmpty_RemoveFirstAndThirdPredicate()
  {
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndThirdPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqIsEmpty_RemoveFirstPredicate()
  {
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqIsEmpty_RemoveFirstAndSecondToLastPredicate()
  {
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize3_RemoveAll(){
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveAllPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize3_RemoveNone(){
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveNonePredicate();
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize3_RetainSecond(){
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(1,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize3_RetainSecondAndLast(){
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(2,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize3_RemoveFirstAndThird(){
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndThirdPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3-2,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize3_RemoveFirst(){
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3-1,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize3_RemoveFirstAndSecondToLast(){
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3-2,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize50_RemoveAll(){
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveAllPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize50_RemoveNone(){
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveNonePredicate();
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize50_RetainSecond(){
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(1,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize50_RetainSecondAndLast(){
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(2,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize50_RemoveFirstAndThird(){
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndThirdPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50-2,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize50_RemoveFirst(){
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50-1,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize50_RemoveFirstAndSecondToLast(){
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50-2,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize100_RemoveAll(){
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveAllPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize100_RemoveNone(){
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveNonePredicate();
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize100_RetainSecond(){
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(1,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize100_RetainSecondAndLast(){
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(2,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize100_RemoveFirstAndThird(){
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndThirdPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100-2,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize100_RemoveFirst(){
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100-1,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqSize100_RemoveFirstAndSecondToLast(){
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100-2,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqIsEmpty_RemoveAllPredicate()
  {
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveAllPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedListremoveIf_Predicate_SeqIsEmpty_RemoveNonePredicate()
  {
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveNonePredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
  }
  @Test
  public void testUncheckedListadd_Object_initialCapacityDEFAULT(){
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_Object_initialCapacityNULL(){
    var seq=new RefArrSeq.UncheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_Object_initialCapacity50(){
    var seq=new RefArrSeq.UncheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListtoArray_void_seqIsEmpty(){
    var seq=new RefArrSeq.UncheckedList();
    Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.toArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedListtoArray_void_seqIsNotEmpty(){
    var seq=new RefArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var result=seq.toArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertSame(itr.next(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testCheckedStackconstructor_void_initialCapacityDEFAULT(){
    var seq=new RefArrSeq.CheckedStack();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackconstructor_intObject_initialCapacityNULL(){
    var seq=new RefArrSeq.CheckedStack(0,null);
    Assertions.assertEquals(0,seq.size);
    Assertions.assertNull(seq.arr);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackconstructor_int_initialCapacity50(){
    var seq=new RefArrSeq.CheckedStack(50);
    Assertions.assertEquals(0,seq.size);
    switch(50){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(50,seq.arr.length);
      for(int i=0;i<50;++i){
        Assertions.assertNull(seq.arr[i]);
      }
    }
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackconstructor_int_initialCapacity0(){
    var seq=new RefArrSeq.CheckedStack(0);
    Assertions.assertEquals(0,seq.size);
    switch(0){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(0,seq.arr.length);
      for(int i=0;i<0;++i){
        Assertions.assertNull(seq.arr[i]);
      }
    }
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackconstructor_int_initialCapacity10(){
    var seq=new RefArrSeq.CheckedStack(10);
    Assertions.assertEquals(0,seq.size);
    switch(10){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(10,seq.arr.length);
      for(int i=0;i<10;++i){
        Assertions.assertNull(seq.arr[i]);
      }
    }
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackClone_initialCapacityDEFAULT_seqIsEmpty()
  {
    var seq=new RefArrSeq.CheckedStack();
    var clone=(RefArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfRef.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackClone_initialCapacityDEFAULT_seqIsNotEmpty()
  {
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var clone=(RefArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),clone.arr[i]);
      Assertions.assertSame(seq.arr[i],clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedStackClone_initialCapacityNULL_seqIsEmpty()
  {
    var seq=new RefArrSeq.CheckedStack(0,null);
    var clone=(RefArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfRef.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackClone_initialCapacityNULL_seqIsNotEmpty()
  {
    var seq=new RefArrSeq.CheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var clone=(RefArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),clone.arr[i]);
      Assertions.assertSame(seq.arr[i],clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedStackClone_initialCapacity50_seqIsEmpty()
  {
    var seq=new RefArrSeq.CheckedStack(50);
    var clone=(RefArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfRef.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackClone_initialCapacity50_seqIsNotEmpty()
  {
    var seq=new RefArrSeq.CheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var clone=(RefArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),clone.arr[i]);
      Assertions.assertSame(seq.arr[i],clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedStackClone_initialCapacity0_seqIsEmpty()
  {
    var seq=new RefArrSeq.CheckedStack(0);
    var clone=(RefArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfRef.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackClone_initialCapacity0_seqIsNotEmpty()
  {
    var seq=new RefArrSeq.CheckedStack(0);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var clone=(RefArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),clone.arr[i]);
      Assertions.assertSame(seq.arr[i],clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedStackClone_initialCapacity10_seqIsEmpty()
  {
    var seq=new RefArrSeq.CheckedStack(10);
    var clone=(RefArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfRef.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackClone_initialCapacity10_seqIsNotEmpty()
  {
    var seq=new RefArrSeq.CheckedStack(10);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var clone=(RefArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),clone.arr[i]);
      Assertions.assertSame(seq.arr[i],clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedStacktoArray_ObjectArray_zeroLengthArrayAndSequenceIsEmpty(){
    var seq=new RefArrSeq.CheckedStack();
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
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
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
    for(int i=0;i<100;++i){
      Assertions.assertSame(itr.next(),result[i]);
    }
  }
  @Test
  public void testCheckedStacktoArray_ObjectArray_nonzeroLengthArrayAndSequenceIsEmpty(){
    var seq=new RefArrSeq.CheckedStack();
    Integer[] paramArr=new Integer[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToObject(paramArr.length);
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertEquals(5,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    Assertions.assertNull(result[0]);
    for(int i=1;i<result.length;++i){
      Assertions.assertEquals((Object)TypeConversionUtil.convertToObject(paramArr.length),result[i]);
    }
  }
  @Test
  public void testCheckedStacktoArray_ObjectArray_overSizedArray(){
    var seq=new RefArrSeq.CheckedStack();
    Integer[] paramArr=new Integer[10];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToObject(paramArr.length);
    }
    for(int i=0;i<5;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(5,seq.size());
    Assertions.assertEquals(5,seq.modCount);
    Assertions.assertEquals(10,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<5;++i){
      Assertions.assertSame(itr.next(),result[i]);
    }
    Assertions.assertNull(result[5]);
    for(int i=6;i<result.length;++i){
      Assertions.assertEquals((Object)TypeConversionUtil.convertToObject(paramArr.length),result[i]);
    }
  }
  @Test
  public void testCheckedStacktoArray_ObjectArray_undersizedArray(){
    var seq=new RefArrSeq.CheckedStack();
    Integer[] paramArr=new Integer[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToObject(10);
    }
    for(int i=0;i<10;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(10,seq.size());
    Assertions.assertEquals(10,seq.modCount);
    Assertions.assertEquals(10,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertNotSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<10;++i){
      Assertions.assertSame(itr.next(),result[i]);
    }
  }
  @Test
  public void testCheckedStacktoArray_ObjectArray_exactSizeArray(){
    var seq=new RefArrSeq.CheckedStack();
    Integer[] paramArr=new Integer[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToObject(5);
    }
    for(int i=0;i<5;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(5,seq.size());
    Assertions.assertEquals(5,seq.modCount);
    Assertions.assertEquals(5,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<5;++i){
      Assertions.assertSame(itr.next(),result[i]);
    }
  }
  @Test
  public void testCheckedStacktoArray_IntFunction_seqIsEmpty_nonMod(){
    var seq=new RefArrSeq.CheckedStack();
    IntFunction<Integer[]> arrConstructor=Integer[]::new;
    var result=seq.toArray(arrConstructor);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertEquals(0,result.length);
  }
  @Test
  public void testCheckedStacktoArray_IntFunction_seqIsNotEmpty_nonMod(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    IntFunction<Integer[]> arrConstructor=Integer[]::new;
    var result=seq.toArray(arrConstructor);
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertSame(itr.next(),result[i]);
    }
  }
  @Test
  public void testCheckedStacktoArray_IntFunction_seqIsEmpty_moddingArrayConstructor(){
    var seq=new RefArrSeq.CheckedStack();
    IntFunction<Integer[]> arrConstructor=(int arrSize)->{
      seq.add(TypeConversionUtil.convertToObject(arrSize));
      return new Integer[arrSize];
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(1,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertToObject(0),seq.arr[0]);
    Assertions.assertEquals(1,seq.modCount);
  }
  @Test
  public void testCheckedStacktoArray_IntFunction_seqIsNotEmpty_moddingArrayConstructor(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    IntFunction<Integer[]> arrConstructor=(int arrSize)->{
      seq.add(TypeConversionUtil.convertToObject(arrSize));
      return new Integer[arrSize];
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(101,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertToObject(100),seq.arr[100]);
    Assertions.assertEquals(101,seq.modCount);
  }
  @Test
  public void testCheckedStacktoArray_IntFunction_seqIsEmpty_throwingArrayConstructor(){
    var seq=new RefArrSeq.CheckedStack();
    IntFunction<Integer[]> arrConstructor=(int arrSize)->{
      throw new IndexOutOfBoundsException();
    };
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStacktoArray_IntFunction_seqIsNotEmpty_throwingArrayConstructor(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
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
    var seq=new RefArrSeq.CheckedStack();
    IntFunction<Integer[]> arrConstructor=(int arrSize)->{
      seq.add(TypeConversionUtil.convertToObject(arrSize));
      throw new IndexOutOfBoundsException();
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(1,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertToObject(0),seq.arr[0]);
    Assertions.assertEquals(1,seq.modCount);
  }
  @Test
  public void testCheckedStacktoArray_IntFunction_seqIsNotEmpty_throwingAndModdingArrConstructor(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    IntFunction<Integer[]> arrConstructor=(int arrSize)->{
      seq.add(TypeConversionUtil.convertToObject(arrSize));
      throw new IndexOutOfBoundsException();
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(101,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertToObject(100),seq.arr[100]);
    Assertions.assertEquals(101,seq.modCount);
  }
  @Test
  public void testCheckedStackclear_void_seqIsEmpty(){
    var seq=new RefArrSeq.CheckedStack();
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackclear_void_seqIsNotEmpty(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
    for(int i=0;i<100;++i){
      Assertions.assertNull(seq.arr[i]);
    }
    Assertions.assertEquals(101,seq.modCount);
  }
  @Test
  public void testCheckedStackisEmpty_void_seqIsEmpty(){
    var seq=new RefArrSeq.CheckedStack();
    Assertions.assertTrue(seq.isEmpty());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackisEmpty_void_seqIsNotEmpty(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertFalse(seq.isEmpty());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedStackisEmpty_void_seqIsBeingCleared(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;){
      Assertions.assertFalse(seq.isEmpty());
      itr.next();
      itr.remove();
      Assertions.assertEquals(100+(100-i),seq.modCount);
    }
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testCheckedStacksize_void_seqIsEmpty(){
    var seq=new RefArrSeq.CheckedStack();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStacksize_void_seqIsNotEmpty(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedStacksize_void_seqIsBeingCleared(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;){
      itr.next();
      itr.remove();
      Assertions.assertEquals(i,seq.size());
      Assertions.assertEquals(100+(100-i),seq.modCount);
    }
  }
  @Test
  public void testCheckedStackforEach_Consumer_SeqIsEmpty_NoMod(){
    var seq=new RefArrSeq.CheckedStack();
    var consumer=new RefMonitoredConsumer();
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedStackforEach_Consumer_SeqIsNotEmpty_NoMod(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var consumer=new RefMonitoredConsumer();
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
  public void testCheckedStackforEach_Consumer_SeqIsEmpty_ModdingConsumer(){
    var seq=new RefArrSeq.CheckedStack();
    var consumer=new RefMonitoredConsumer.ModifyingArrSeqCheckedStackConsumer(seq);
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedStackforEach_Consumer_SeqIsNotEmpty_ModdingConsumer(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var consumer=new RefMonitoredConsumer.ModifyingArrSeqCheckedStackConsumer(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.forEach((Consumer)consumer));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(300,seq.modCount);
    Assertions.assertEquals(100,consumer.size());
    var seqIterator=seq.iterator();
    var consumerIterator=consumer.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertSame(consumerIterator.next(),seqIterator.next());
    }
  }
  @Test
  public void testCheckedStackforEach_Consumer_SeqIsEmpty_ThrowingConsumer(){
    var seq=new RefArrSeq.CheckedStack();
    var consumer=new RefMonitoredConsumer.Throwing();
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedStackforEach_Consumer_SeqIsNotEmpty_ThrowingConsumer(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var consumer=new RefMonitoredConsumer.Throwing();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.forEach((Consumer)consumer));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(1,consumer.size());
    var seqIterator=seq.iterator();
    var consumerIterator=consumer.iterator();
    for(int i=0;i<1;++i){
      Assertions.assertSame(consumerIterator.next(),seqIterator.next());
    }
  }
  @Test
  public void testCheckedStackforEach_Consumer_SeqIsEmpty_ThrowingAndModdingConsumer(){
    var seq=new RefArrSeq.CheckedStack();
    var consumer=new RefMonitoredConsumer.ModifyingArrSeqCheckedStackAndThrowingConsumer(seq);
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedStackforEach_Consumer_SeqIsNotEmpty_ThrowingAndModdingConsumer(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var consumer=new RefMonitoredConsumer.ModifyingArrSeqCheckedStackAndThrowingConsumer(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.forEach((Consumer)consumer));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(102,seq.modCount);
    Assertions.assertEquals(1,consumer.size());
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_RetainSecondArrSeqCheckedStackModifyingPredicate()
  {
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_RetainSecondAndLastArrSeqCheckedStackModifyingPredicate()
  {
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondAndLastArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveFirstAndThirdArrSeqCheckedStackModifyingPredicate()
  {
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndThirdArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveFirstArrSeqCheckedStackModifyingPredicate()
  {
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveFirstAndSecondToLastArrSeqCheckedStackModifyingPredicate()
  {
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndSecondToLastArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  public void testCheckedStackremoveIf_Predicate_SeqSize3_ThrowAndMod(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.ModifyingArrSeqCheckedStackAndThrowingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3+1,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(1,filter.callCount);
    Assertions.assertEquals(3+1,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize3_Throwing(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.ThrowingPredicate();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(1,filter.callCount);
    Assertions.assertEquals(3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize3_RemoveAllMod(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveAllArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize3_RemoveNoneMod(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveNoneArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize3_RetainSecondMod(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize3_RetainSecondAndLastMod(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondAndLastArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize3_RemoveFirstAndThirdMod(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndThirdArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize3_RemoveFirstMod(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize3_RemoveFirstAndSecondToLastMod(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndSecondToLastArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  public void testCheckedStackremoveIf_Predicate_SeqSize50_ThrowAndMod(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.ModifyingArrSeqCheckedStackAndThrowingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50+1,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(1,filter.callCount);
    Assertions.assertEquals(50+1,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize50_Throwing(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.ThrowingPredicate();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(1,filter.callCount);
    Assertions.assertEquals(50,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize50_RemoveAllMod(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveAllArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize50_RemoveNoneMod(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveNoneArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize50_RetainSecondMod(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize50_RetainSecondAndLastMod(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondAndLastArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize50_RemoveFirstAndThirdMod(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndThirdArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize50_RemoveFirstMod(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize50_RemoveFirstAndSecondToLastMod(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndSecondToLastArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  public void testCheckedStackremoveIf_Predicate_SeqSize100_ThrowAndMod(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.ModifyingArrSeqCheckedStackAndThrowingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100+1,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(1,filter.callCount);
    Assertions.assertEquals(100+1,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize100_Throwing(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.ThrowingPredicate();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(1,filter.callCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize100_RemoveAllMod(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveAllArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize100_RemoveNoneMod(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveNoneArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize100_RetainSecondMod(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize100_RetainSecondAndLastMod(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondAndLastArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize100_RemoveFirstAndThirdMod(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndThirdArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize100_RemoveFirstMod(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize100_RemoveFirstAndSecondToLastMod(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndSecondToLastArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_RetainSecondPredicate()
  {
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_RetainSecondAndLastPredicate()
  {
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveFirstAndThirdPredicate()
  {
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndThirdPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveFirstPredicate()
  {
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveFirstAndSecondToLastPredicate()
  {
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize3_RemoveAll(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveAllPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize3_RemoveNone(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveNonePredicate();
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize3_RetainSecond(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(1,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize3_RetainSecondAndLast(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(2,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize3_RemoveFirstAndThird(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndThirdPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3-2,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize3_RemoveFirst(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3-1,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize3_RemoveFirstAndSecondToLast(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3-2,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize50_RemoveAll(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveAllPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize50_RemoveNone(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveNonePredicate();
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize50_RetainSecond(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(1,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize50_RetainSecondAndLast(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(2,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize50_RemoveFirstAndThird(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndThirdPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50-2,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize50_RemoveFirst(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50-1,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize50_RemoveFirstAndSecondToLast(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50-2,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize100_RemoveAll(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveAllPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize100_RemoveNone(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveNonePredicate();
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize100_RetainSecond(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(1,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize100_RetainSecondAndLast(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(2,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize100_RemoveFirstAndThird(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndThirdPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100-2,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize100_RemoveFirst(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100-1,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqSize100_RemoveFirstAndSecondToLast(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100-2,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveAllPredicate()
  {
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveAllPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveNonePredicate()
  {
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveNonePredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveAllArrSeqCheckedStackModifyingPredicate()
  {
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveAllArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_RemoveNoneArrSeqCheckedStackModifyingPredicate()
  {
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveNoneArrSeqCheckedStackModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_ThrowingPredicate()
  {
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.ThrowingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackremoveIf_Predicate_SeqIsEmpty_ModifyingArrSeqCheckedStackAndThrowingPredicate()
  {
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.ModifyingArrSeqCheckedStackAndThrowingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackadd_Object_initialCapacityDEFAULT(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_Object_initialCapacityNULL(){
    var seq=new RefArrSeq.CheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_Object_initialCapacity50(){
    var seq=new RefArrSeq.CheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStacktoArray_void_seqIsEmpty(){
    var seq=new RefArrSeq.CheckedStack();
    Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.toArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStacktoArray_void_seqIsNotEmpty(){
    var seq=new RefArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var result=seq.toArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertSame(itr.next(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
  @Test
  public void testCheckedListconstructor_void_initialCapacityDEFAULT(){
    var seq=new RefArrSeq.CheckedList();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListconstructor_intObject_initialCapacityNULL(){
    var seq=new RefArrSeq.CheckedList(0,null);
    Assertions.assertEquals(0,seq.size);
    Assertions.assertNull(seq.arr);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListconstructor_int_initialCapacity50(){
    var seq=new RefArrSeq.CheckedList(50);
    Assertions.assertEquals(0,seq.size);
    switch(50){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(50,seq.arr.length);
      for(int i=0;i<50;++i){
        Assertions.assertNull(seq.arr[i]);
      }
    }
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListconstructor_int_initialCapacity0(){
    var seq=new RefArrSeq.CheckedList(0);
    Assertions.assertEquals(0,seq.size);
    switch(0){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(0,seq.arr.length);
      for(int i=0;i<0;++i){
        Assertions.assertNull(seq.arr[i]);
      }
    }
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListconstructor_int_initialCapacity10(){
    var seq=new RefArrSeq.CheckedList(10);
    Assertions.assertEquals(0,seq.size);
    switch(10){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(10,seq.arr.length);
      for(int i=0;i<10;++i){
        Assertions.assertNull(seq.arr[i]);
      }
    }
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListClone_initialCapacityDEFAULT_seqIsEmpty()
  {
    var seq=new RefArrSeq.CheckedList();
    var clone=(RefArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfRef.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListClone_initialCapacityDEFAULT_seqIsNotEmpty()
  {
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var clone=(RefArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),clone.arr[i]);
      Assertions.assertSame(seq.arr[i],clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListClone_initialCapacityNULL_seqIsEmpty()
  {
    var seq=new RefArrSeq.CheckedList(0,null);
    var clone=(RefArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfRef.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListClone_initialCapacityNULL_seqIsNotEmpty()
  {
    var seq=new RefArrSeq.CheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var clone=(RefArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),clone.arr[i]);
      Assertions.assertSame(seq.arr[i],clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListClone_initialCapacity50_seqIsEmpty()
  {
    var seq=new RefArrSeq.CheckedList(50);
    var clone=(RefArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfRef.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListClone_initialCapacity50_seqIsNotEmpty()
  {
    var seq=new RefArrSeq.CheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var clone=(RefArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),clone.arr[i]);
      Assertions.assertSame(seq.arr[i],clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListClone_initialCapacity0_seqIsEmpty()
  {
    var seq=new RefArrSeq.CheckedList(0);
    var clone=(RefArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfRef.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListClone_initialCapacity0_seqIsNotEmpty()
  {
    var seq=new RefArrSeq.CheckedList(0);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var clone=(RefArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),clone.arr[i]);
      Assertions.assertSame(seq.arr[i],clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListClone_initialCapacity10_seqIsEmpty()
  {
    var seq=new RefArrSeq.CheckedList(10);
    var clone=(RefArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfRef.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListClone_initialCapacity10_seqIsNotEmpty()
  {
    var seq=new RefArrSeq.CheckedList(10);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var clone=(RefArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),clone.arr[i]);
      Assertions.assertSame(seq.arr[i],clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListtoArray_ObjectArray_zeroLengthArrayAndSequenceIsEmpty(){
    var seq=new RefArrSeq.CheckedList();
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
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i){
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
    for(int i=0;i<100;++i){
      Assertions.assertSame(itr.next(),result[i]);
    }
  }
  @Test
  public void testCheckedListtoArray_ObjectArray_nonzeroLengthArrayAndSequenceIsEmpty(){
    var seq=new RefArrSeq.CheckedList();
    Integer[] paramArr=new Integer[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToObject(paramArr.length);
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertEquals(5,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    Assertions.assertNull(result[0]);
    for(int i=1;i<result.length;++i){
      Assertions.assertEquals((Object)TypeConversionUtil.convertToObject(paramArr.length),result[i]);
    }
  }
  @Test
  public void testCheckedListtoArray_ObjectArray_overSizedArray(){
    var seq=new RefArrSeq.CheckedList();
    Integer[] paramArr=new Integer[10];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToObject(paramArr.length);
    }
    for(int i=0;i<5;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(5,seq.size());
    Assertions.assertEquals(5,seq.modCount);
    Assertions.assertEquals(10,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<5;++i){
      Assertions.assertSame(itr.next(),result[i]);
    }
    Assertions.assertNull(result[5]);
    for(int i=6;i<result.length;++i){
      Assertions.assertEquals((Object)TypeConversionUtil.convertToObject(paramArr.length),result[i]);
    }
  }
  @Test
  public void testCheckedListtoArray_ObjectArray_undersizedArray(){
    var seq=new RefArrSeq.CheckedList();
    Integer[] paramArr=new Integer[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToObject(10);
    }
    for(int i=0;i<10;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(10,seq.size());
    Assertions.assertEquals(10,seq.modCount);
    Assertions.assertEquals(10,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertNotSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<10;++i){
      Assertions.assertSame(itr.next(),result[i]);
    }
  }
  @Test
  public void testCheckedListtoArray_ObjectArray_exactSizeArray(){
    var seq=new RefArrSeq.CheckedList();
    Integer[] paramArr=new Integer[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertToObject(5);
    }
    for(int i=0;i<5;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(5,seq.size());
    Assertions.assertEquals(5,seq.modCount);
    Assertions.assertEquals(5,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    var itr=seq.iterator();
    for(int i=0;i<5;++i){
      Assertions.assertSame(itr.next(),result[i]);
    }
  }
  @Test
  public void testCheckedListtoArray_IntFunction_seqIsEmpty_nonMod(){
    var seq=new RefArrSeq.CheckedList();
    IntFunction<Integer[]> arrConstructor=Integer[]::new;
    var result=seq.toArray(arrConstructor);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertEquals(0,result.length);
  }
  @Test
  public void testCheckedListtoArray_IntFunction_seqIsNotEmpty_nonMod(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    IntFunction<Integer[]> arrConstructor=Integer[]::new;
    var result=seq.toArray(arrConstructor);
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertSame(itr.next(),result[i]);
    }
  }
  @Test
  public void testCheckedListtoArray_IntFunction_seqIsEmpty_moddingArrayConstructor(){
    var seq=new RefArrSeq.CheckedList();
    IntFunction<Integer[]> arrConstructor=(int arrSize)->{
      seq.add(TypeConversionUtil.convertToObject(arrSize));
      return new Integer[arrSize];
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(1,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertToObject(0),seq.arr[0]);
    Assertions.assertEquals(1,seq.modCount);
  }
  @Test
  public void testCheckedListtoArray_IntFunction_seqIsNotEmpty_moddingArrayConstructor(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    IntFunction<Integer[]> arrConstructor=(int arrSize)->{
      seq.add(TypeConversionUtil.convertToObject(arrSize));
      return new Integer[arrSize];
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(101,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertToObject(100),seq.arr[100]);
    Assertions.assertEquals(101,seq.modCount);
  }
  @Test
  public void testCheckedListtoArray_IntFunction_seqIsEmpty_throwingArrayConstructor(){
    var seq=new RefArrSeq.CheckedList();
    IntFunction<Integer[]> arrConstructor=(int arrSize)->{
      throw new IndexOutOfBoundsException();
    };
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListtoArray_IntFunction_seqIsNotEmpty_throwingArrayConstructor(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
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
    var seq=new RefArrSeq.CheckedList();
    IntFunction<Integer[]> arrConstructor=(int arrSize)->{
      seq.add(TypeConversionUtil.convertToObject(arrSize));
      throw new IndexOutOfBoundsException();
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(1,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertToObject(0),seq.arr[0]);
    Assertions.assertEquals(1,seq.modCount);
  }
  @Test
  public void testCheckedListtoArray_IntFunction_seqIsNotEmpty_throwingAndModdingArrConstructor(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    IntFunction<Integer[]> arrConstructor=(int arrSize)->{
      seq.add(TypeConversionUtil.convertToObject(arrSize));
      throw new IndexOutOfBoundsException();
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(101,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertToObject(100),seq.arr[100]);
    Assertions.assertEquals(101,seq.modCount);
  }
  @Test
  public void testCheckedListclear_void_seqIsEmpty(){
    var seq=new RefArrSeq.CheckedList();
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListclear_void_seqIsNotEmpty(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
    for(int i=0;i<100;++i){
      Assertions.assertNull(seq.arr[i]);
    }
    Assertions.assertEquals(101,seq.modCount);
  }
  @Test
  public void testCheckedListisEmpty_void_seqIsEmpty(){
    var seq=new RefArrSeq.CheckedList();
    Assertions.assertTrue(seq.isEmpty());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListisEmpty_void_seqIsNotEmpty(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertFalse(seq.isEmpty());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListisEmpty_void_seqIsBeingCleared(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;){
      Assertions.assertFalse(seq.isEmpty());
      itr.next();
      itr.remove();
      Assertions.assertEquals(100+(100-i),seq.modCount);
    }
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testCheckedListsize_void_seqIsEmpty(){
    var seq=new RefArrSeq.CheckedList();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListsize_void_seqIsNotEmpty(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListsize_void_seqIsBeingCleared(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;){
      itr.next();
      itr.remove();
      Assertions.assertEquals(i,seq.size());
      Assertions.assertEquals(100+(100-i),seq.modCount);
    }
  }
  @Test
  public void testCheckedListforEach_Consumer_SeqIsEmpty_NoMod(){
    var seq=new RefArrSeq.CheckedList();
    var consumer=new RefMonitoredConsumer();
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedListforEach_Consumer_SeqIsNotEmpty_NoMod(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var consumer=new RefMonitoredConsumer();
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
  public void testCheckedListforEach_Consumer_SeqIsEmpty_ModdingConsumer(){
    var seq=new RefArrSeq.CheckedList();
    var consumer=new RefMonitoredConsumer.ModifyingArrSeqCheckedListConsumer(seq);
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedListforEach_Consumer_SeqIsNotEmpty_ModdingConsumer(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var consumer=new RefMonitoredConsumer.ModifyingArrSeqCheckedListConsumer(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.forEach((Consumer)consumer));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(300,seq.modCount);
    Assertions.assertEquals(100,consumer.size());
    var seqIterator=seq.iterator();
    var consumerIterator=consumer.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertSame(consumerIterator.next(),seqIterator.next());
    }
  }
  @Test
  public void testCheckedListforEach_Consumer_SeqIsEmpty_ThrowingConsumer(){
    var seq=new RefArrSeq.CheckedList();
    var consumer=new RefMonitoredConsumer.Throwing();
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedListforEach_Consumer_SeqIsNotEmpty_ThrowingConsumer(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var consumer=new RefMonitoredConsumer.Throwing();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.forEach((Consumer)consumer));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(1,consumer.size());
    var seqIterator=seq.iterator();
    var consumerIterator=consumer.iterator();
    for(int i=0;i<1;++i){
      Assertions.assertSame(consumerIterator.next(),seqIterator.next());
    }
  }
  @Test
  public void testCheckedListforEach_Consumer_SeqIsEmpty_ThrowingAndModdingConsumer(){
    var seq=new RefArrSeq.CheckedList();
    var consumer=new RefMonitoredConsumer.ModifyingArrSeqCheckedListAndThrowingConsumer(seq);
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedListforEach_Consumer_SeqIsNotEmpty_ThrowingAndModdingConsumer(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var consumer=new RefMonitoredConsumer.ModifyingArrSeqCheckedListAndThrowingConsumer(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.forEach((Consumer)consumer));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(102,seq.modCount);
    Assertions.assertEquals(1,consumer.size());
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_RetainSecondArrSeqCheckedListModifyingPredicate()
  {
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_RetainSecondAndLastArrSeqCheckedListModifyingPredicate()
  {
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondAndLastArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_RemoveFirstAndThirdArrSeqCheckedListModifyingPredicate()
  {
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndThirdArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_RemoveFirstArrSeqCheckedListModifyingPredicate()
  {
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_RemoveFirstAndSecondToLastArrSeqCheckedListModifyingPredicate()
  {
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndSecondToLastArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  public void testCheckedListremoveIf_Predicate_SeqSize3_ThrowAndMod(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.ModifyingArrSeqCheckedListAndThrowingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3+1,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(1,filter.callCount);
    Assertions.assertEquals(3+1,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize3_Throwing(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.ThrowingPredicate();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(1,filter.callCount);
    Assertions.assertEquals(3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize3_RemoveAllMod(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveAllArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize3_RemoveNoneMod(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveNoneArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize3_RetainSecondMod(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize3_RetainSecondAndLastMod(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondAndLastArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize3_RemoveFirstAndThirdMod(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndThirdArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize3_RemoveFirstMod(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize3_RemoveFirstAndSecondToLastMod(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndSecondToLastArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
    Assertions.assertEquals(3*3,seq.modCount);
  }
  public void testCheckedListremoveIf_Predicate_SeqSize50_ThrowAndMod(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.ModifyingArrSeqCheckedListAndThrowingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50+1,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(1,filter.callCount);
    Assertions.assertEquals(50+1,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize50_Throwing(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.ThrowingPredicate();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(1,filter.callCount);
    Assertions.assertEquals(50,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize50_RemoveAllMod(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveAllArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize50_RemoveNoneMod(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveNoneArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize50_RetainSecondMod(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize50_RetainSecondAndLastMod(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondAndLastArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize50_RemoveFirstAndThirdMod(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndThirdArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize50_RemoveFirstMod(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize50_RemoveFirstAndSecondToLastMod(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndSecondToLastArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
    Assertions.assertEquals(50*3,seq.modCount);
  }
  public void testCheckedListremoveIf_Predicate_SeqSize100_ThrowAndMod(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.ModifyingArrSeqCheckedListAndThrowingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100+1,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(1,filter.callCount);
    Assertions.assertEquals(100+1,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize100_Throwing(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.ThrowingPredicate();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(1,filter.callCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize100_RemoveAllMod(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveAllArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize100_RemoveNoneMod(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveNoneArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize100_RetainSecondMod(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize100_RetainSecondAndLastMod(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondAndLastArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize100_RemoveFirstAndThirdMod(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndThirdArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize100_RemoveFirstMod(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize100_RemoveFirstAndSecondToLastMod(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndSecondToLastArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
    Assertions.assertEquals(100*3,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_RetainSecondPredicate()
  {
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_RetainSecondAndLastPredicate()
  {
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_RemoveFirstAndThirdPredicate()
  {
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndThirdPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_RemoveFirstPredicate()
  {
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_RemoveFirstAndSecondToLastPredicate()
  {
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize3_RemoveAll(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveAllPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize3_RemoveNone(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveNonePredicate();
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize3_RetainSecond(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(1,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize3_RetainSecondAndLast(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(2,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize3_RemoveFirstAndThird(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndThirdPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3-2,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize3_RemoveFirst(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3-1,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize3_RemoveFirstAndSecondToLast(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<3;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(3-2,seq.size());
    filter.verifyArray(seq.arr,0,3);
    Assertions.assertEquals(3,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize50_RemoveAll(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveAllPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize50_RemoveNone(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveNonePredicate();
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize50_RetainSecond(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(1,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize50_RetainSecondAndLast(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(2,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize50_RemoveFirstAndThird(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndThirdPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50-2,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize50_RemoveFirst(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50-1,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize50_RemoveFirstAndSecondToLast(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<50;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(50-2,seq.size());
    filter.verifyArray(seq.arr,0,50);
    Assertions.assertEquals(50,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize100_RemoveAll(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveAllPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize100_RemoveNone(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveNonePredicate();
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize100_RetainSecond(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(1,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize100_RetainSecondAndLast(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RetainSecondAndLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(2,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize100_RemoveFirstAndThird(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndThirdPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100-2,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize100_RemoveFirst(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstPredicate();
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100-1,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqSize100_RemoveFirstAndSecondToLast(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveFirstAndSecondToLastPredicate(seq.size);
    Assertions.assertTrue(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(100-2,seq.size());
    filter.verifyArray(seq.arr,0,100);
    Assertions.assertEquals(100,filter.callCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_RemoveAllPredicate()
  {
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveAllPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_RemoveNonePredicate()
  {
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveNonePredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_RemoveAllArrSeqCheckedListModifyingPredicate()
  {
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveAllArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_RemoveNoneArrSeqCheckedListModifyingPredicate()
  {
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.RemoveNoneArrSeqCheckedListModifyingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_ThrowingPredicate()
  {
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.ThrowingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIf_Predicate_SeqIsEmpty_ModifyingArrSeqCheckedListAndThrowingPredicate()
  {
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<0;++i){
      seq.add(TypeConversionUtil.convertToObject(i));
    }
    var filter=new RefMonitoredPredicate2.ModifyingArrSeqCheckedListAndThrowingPredicate(seq);
    Assertions.assertFalse(seq.removeIf((Predicate)filter));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,filter.callCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_Object_initialCapacityDEFAULT(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_Object_initialCapacityNULL(){
    var seq=new RefArrSeq.CheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_Object_initialCapacity50(){
    var seq=new RefArrSeq.CheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListtoArray_void_seqIsEmpty(){
    var seq=new RefArrSeq.CheckedList();
    Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.toArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListtoArray_void_seqIsNotEmpty(){
    var seq=new RefArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToObject(i)));
    }
    var result=seq.toArray();
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    Assertions.assertEquals(100,result.length);
    var itr=seq.iterator();
    for(int i=0;i<100;++i){
      Assertions.assertSame(itr.next(),result[i]);
    }
    Assertions.assertNotSame(seq.arr,result);
  }
}
