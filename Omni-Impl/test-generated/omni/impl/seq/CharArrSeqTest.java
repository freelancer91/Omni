package omni.impl.seq;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.function.IntFunction;
import java.util.function.Consumer;
import omni.function.CharConsumer;
import java.util.ConcurrentModificationException;
import omni.util.OmniArray;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class CharArrSeqTest{
  @Test
  public void testUncheckedStackconstructor_void_initialCapacityDEFAULT(){
    var seq=new CharArrSeq.UncheckedStack();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,seq.arr);
  }
  @Test
  public void testUncheckedStackconstructor_intchar_initialCapacityNULL(){
    var seq=new CharArrSeq.UncheckedStack(0,null);
    Assertions.assertEquals(0,seq.size);
    Assertions.assertNull(seq.arr);
  }
  @Test
  public void testUncheckedStackconstructor_int_initialCapacity50(){
    var seq=new CharArrSeq.UncheckedStack(50);
    Assertions.assertEquals(0,seq.size);
    switch(50){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(50,seq.arr.length);
    }
  }
  @Test
  public void testUncheckedStackconstructor_int_initialCapacity0(){
    var seq=new CharArrSeq.UncheckedStack(0);
    Assertions.assertEquals(0,seq.size);
    switch(0){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(0,seq.arr.length);
    }
  }
  @Test
  public void testUncheckedStackconstructor_int_initialCapacity10(){
    var seq=new CharArrSeq.UncheckedStack(10);
    Assertions.assertEquals(0,seq.size);
    switch(10){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(10,seq.arr.length);
    }
  }
  @Test
  public void testUncheckedStackClone_initialCapacityDEFAULT_seqIsEmpty()
  {
    var seq=new CharArrSeq.UncheckedStack();
    var clone=(CharArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfChar.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedStackClone_initialCapacityDEFAULT_seqIsNotEmpty()
  {
    var seq=new CharArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var clone=(CharArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackClone_initialCapacityNULL_seqIsEmpty()
  {
    var seq=new CharArrSeq.UncheckedStack(0,null);
    var clone=(CharArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfChar.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedStackClone_initialCapacityNULL_seqIsNotEmpty()
  {
    var seq=new CharArrSeq.UncheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var clone=(CharArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackClone_initialCapacity50_seqIsEmpty()
  {
    var seq=new CharArrSeq.UncheckedStack(50);
    var clone=(CharArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfChar.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedStackClone_initialCapacity50_seqIsNotEmpty()
  {
    var seq=new CharArrSeq.UncheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var clone=(CharArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackClone_initialCapacity0_seqIsEmpty()
  {
    var seq=new CharArrSeq.UncheckedStack(0);
    var clone=(CharArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfChar.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedStackClone_initialCapacity0_seqIsNotEmpty()
  {
    var seq=new CharArrSeq.UncheckedStack(0);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var clone=(CharArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackClone_initialCapacity10_seqIsEmpty()
  {
    var seq=new CharArrSeq.UncheckedStack(10);
    var clone=(CharArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfChar.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedStackClone_initialCapacity10_seqIsNotEmpty()
  {
    var seq=new CharArrSeq.UncheckedStack(10);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var clone=(CharArrSeq.UncheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedStacktoArray_ObjectArray_zeroLengthArrayAndSequenceIsEmpty(){
    var seq=new CharArrSeq.UncheckedStack();
    Character[] paramArr=new Character[0];
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
  }
  @Test
  public void testUncheckedStacktoArray_ObjectArray_zeroLengthArrayAndSequenceNotEmpty(){
    var seq=new CharArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    Character[] paramArr=new Character[0];
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
    var seq=new CharArrSeq.UncheckedStack();
    Character[] paramArr=new Character[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertTochar(paramArr.length);
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(5,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    Assertions.assertNull(result[0]);
    for(int i=1;i<result.length;++i){
      Assertions.assertEquals((Object)TypeConversionUtil.convertTochar(paramArr.length),result[i]);
    }
  }
  @Test
  public void testUncheckedStacktoArray_ObjectArray_overSizedArray(){
    var seq=new CharArrSeq.UncheckedStack();
    Character[] paramArr=new Character[10];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertTochar(paramArr.length);
    }
    for(int i=0;i<5;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
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
      Assertions.assertEquals((Object)TypeConversionUtil.convertTochar(paramArr.length),result[i]);
    }
  }
  @Test
  public void testUncheckedStacktoArray_ObjectArray_undersizedArray(){
    var seq=new CharArrSeq.UncheckedStack();
    Character[] paramArr=new Character[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertTochar(10);
    }
    for(int i=0;i<10;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
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
    var seq=new CharArrSeq.UncheckedStack();
    Character[] paramArr=new Character[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertTochar(5);
    }
    for(int i=0;i<5;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
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
    var seq=new CharArrSeq.UncheckedStack();
    IntFunction<Character[]> arrConstructor=Character[]::new;
    var result=seq.toArray(arrConstructor);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertEquals(0,result.length);
  }
  @Test
  public void testUncheckedStacktoArray_IntFunction_seqIsNotEmpty_nonMod(){
    var seq=new CharArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    IntFunction<Character[]> arrConstructor=Character[]::new;
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
    var seq=new CharArrSeq.UncheckedStack();
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testUncheckedStackclear_void_seqIsNotEmpty(){
    var seq=new CharArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
    for(int i=0;i<100;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackisEmpty_void_seqIsEmpty(){
    var seq=new CharArrSeq.UncheckedStack();
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testUncheckedStackisEmpty_void_seqIsNotEmpty(){
    var seq=new CharArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    Assertions.assertFalse(seq.isEmpty());
  }
  @Test
  public void testUncheckedStackisEmpty_void_seqIsBeingCleared(){
    var seq=new CharArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;){
      Assertions.assertFalse(seq.isEmpty());
      itr.nextChar();
      itr.remove();
    }
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testUncheckedStacksize_void_seqIsEmpty(){
    var seq=new CharArrSeq.UncheckedStack();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
  }
  @Test
  public void testUncheckedStacksize_void_seqIsNotEmpty(){
    var seq=new CharArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
  }
  @Test
  public void testUncheckedStacksize_void_seqIsBeingCleared(){
    var seq=new CharArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;){
      itr.nextChar();
      itr.remove();
      Assertions.assertEquals(i,seq.size());
    }
  }
  @Test
  public void testUncheckedStackforEach_Consumer_SeqIsEmpty_NoMod(){
    var seq=new CharArrSeq.UncheckedStack();
    var consumer=new CharMonitoredConsumer();
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testUncheckedStackforEach_Consumer_SeqIsNotEmpty_NoMod(){
    var seq=new CharArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var consumer=new CharMonitoredConsumer();
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
  public void testUncheckedStackforEach_CharConsumer_SeqIsEmpty_NoMod(){
    var seq=new CharArrSeq.UncheckedStack();
    var consumer=new CharMonitoredConsumer();
    seq.forEach((CharConsumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testUncheckedStackforEach_CharConsumer_SeqIsNotEmpty_NoMod(){
    var seq=new CharArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var consumer=new CharMonitoredConsumer();
    seq.forEach((CharConsumer)consumer);
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
  public void testUncheckedStackadd_char_initialCapacityDEFAULT(){
    var seq=new CharArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_char_initialCapacityNULL(){
    var seq=new CharArrSeq.UncheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_char_initialCapacity50(){
    var seq=new CharArrSeq.UncheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_Character_initialCapacityDEFAULT(){
    var seq=new CharArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToCharacter(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_Character_initialCapacityNULL(){
    var seq=new CharArrSeq.UncheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToCharacter(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_Character_initialCapacity50(){
    var seq=new CharArrSeq.UncheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToCharacter(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_boolean_initialCapacityDEFAULT(){
    var seq=new CharArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_boolean_initialCapacityNULL(){
    var seq=new CharArrSeq.UncheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_boolean_initialCapacity50(){
    var seq=new CharArrSeq.UncheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_Boolean_initialCapacityDEFAULT(){
    var seq=new CharArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_Boolean_initialCapacityNULL(){
    var seq=new CharArrSeq.UncheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackadd_Boolean_initialCapacity50(){
    var seq=new CharArrSeq.UncheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStacktoCharArray_void_seqIsEmpty(){
    var seq=new CharArrSeq.UncheckedStack();
    Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,seq.toCharArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStacktoCharArray_void_seqIsNotEmpty(){
    var seq=new CharArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
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
  public void testUncheckedStacktoArray_void_seqIsEmpty(){
    var seq=new CharArrSeq.UncheckedStack();
    Assertions.assertSame(OmniArray.OfChar.DEFAULT_BOXED_ARR,seq.toArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStacktoArray_void_seqIsNotEmpty(){
    var seq=new CharArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
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
    var seq=new CharArrSeq.UncheckedStack();
    Assertions.assertSame(OmniArray.OfDouble.DEFAULT_ARR,seq.toDoubleArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStacktoDoubleArray_void_seqIsNotEmpty(){
    var seq=new CharArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
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
    var seq=new CharArrSeq.UncheckedStack();
    Assertions.assertSame(OmniArray.OfFloat.DEFAULT_ARR,seq.toFloatArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStacktoFloatArray_void_seqIsNotEmpty(){
    var seq=new CharArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
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
    var seq=new CharArrSeq.UncheckedStack();
    Assertions.assertSame(OmniArray.OfLong.DEFAULT_ARR,seq.toLongArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStacktoLongArray_void_seqIsNotEmpty(){
    var seq=new CharArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
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
    var seq=new CharArrSeq.UncheckedStack();
    Assertions.assertSame(OmniArray.OfInt.DEFAULT_ARR,seq.toIntArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStacktoIntArray_void_seqIsNotEmpty(){
    var seq=new CharArrSeq.UncheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
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
  public void testUncheckedListconstructor_void_initialCapacityDEFAULT(){
    var seq=new CharArrSeq.UncheckedList();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,seq.arr);
  }
  @Test
  public void testUncheckedListconstructor_intchar_initialCapacityNULL(){
    var seq=new CharArrSeq.UncheckedList(0,null);
    Assertions.assertEquals(0,seq.size);
    Assertions.assertNull(seq.arr);
  }
  @Test
  public void testUncheckedListconstructor_int_initialCapacity50(){
    var seq=new CharArrSeq.UncheckedList(50);
    Assertions.assertEquals(0,seq.size);
    switch(50){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(50,seq.arr.length);
    }
  }
  @Test
  public void testUncheckedListconstructor_int_initialCapacity0(){
    var seq=new CharArrSeq.UncheckedList(0);
    Assertions.assertEquals(0,seq.size);
    switch(0){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(0,seq.arr.length);
    }
  }
  @Test
  public void testUncheckedListconstructor_int_initialCapacity10(){
    var seq=new CharArrSeq.UncheckedList(10);
    Assertions.assertEquals(0,seq.size);
    switch(10){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(10,seq.arr.length);
    }
  }
  @Test
  public void testUncheckedListClone_initialCapacityDEFAULT_seqIsEmpty()
  {
    var seq=new CharArrSeq.UncheckedList();
    var clone=(CharArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfChar.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedListClone_initialCapacityDEFAULT_seqIsNotEmpty()
  {
    var seq=new CharArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var clone=(CharArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedListClone_initialCapacityNULL_seqIsEmpty()
  {
    var seq=new CharArrSeq.UncheckedList(0,null);
    var clone=(CharArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfChar.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedListClone_initialCapacityNULL_seqIsNotEmpty()
  {
    var seq=new CharArrSeq.UncheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var clone=(CharArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedListClone_initialCapacity50_seqIsEmpty()
  {
    var seq=new CharArrSeq.UncheckedList(50);
    var clone=(CharArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfChar.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedListClone_initialCapacity50_seqIsNotEmpty()
  {
    var seq=new CharArrSeq.UncheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var clone=(CharArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedListClone_initialCapacity0_seqIsEmpty()
  {
    var seq=new CharArrSeq.UncheckedList(0);
    var clone=(CharArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfChar.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedListClone_initialCapacity0_seqIsNotEmpty()
  {
    var seq=new CharArrSeq.UncheckedList(0);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var clone=(CharArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedListClone_initialCapacity10_seqIsEmpty()
  {
    var seq=new CharArrSeq.UncheckedList(10);
    var clone=(CharArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfChar.DEFAULT_ARR);
  }
  @Test
  public void testUncheckedListClone_initialCapacity10_seqIsNotEmpty()
  {
    var seq=new CharArrSeq.UncheckedList(10);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var clone=(CharArrSeq.UncheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),clone.arr[i]);
    }
  }
  @Test
  public void testUncheckedListtoArray_ObjectArray_zeroLengthArrayAndSequenceIsEmpty(){
    var seq=new CharArrSeq.UncheckedList();
    Character[] paramArr=new Character[0];
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
  }
  @Test
  public void testUncheckedListtoArray_ObjectArray_zeroLengthArrayAndSequenceNotEmpty(){
    var seq=new CharArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    Character[] paramArr=new Character[0];
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
    var seq=new CharArrSeq.UncheckedList();
    Character[] paramArr=new Character[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertTochar(paramArr.length);
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(5,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    Assertions.assertNull(result[0]);
    for(int i=1;i<result.length;++i){
      Assertions.assertEquals((Object)TypeConversionUtil.convertTochar(paramArr.length),result[i]);
    }
  }
  @Test
  public void testUncheckedListtoArray_ObjectArray_overSizedArray(){
    var seq=new CharArrSeq.UncheckedList();
    Character[] paramArr=new Character[10];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertTochar(paramArr.length);
    }
    for(int i=0;i<5;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
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
      Assertions.assertEquals((Object)TypeConversionUtil.convertTochar(paramArr.length),result[i]);
    }
  }
  @Test
  public void testUncheckedListtoArray_ObjectArray_undersizedArray(){
    var seq=new CharArrSeq.UncheckedList();
    Character[] paramArr=new Character[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertTochar(10);
    }
    for(int i=0;i<10;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
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
    var seq=new CharArrSeq.UncheckedList();
    Character[] paramArr=new Character[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertTochar(5);
    }
    for(int i=0;i<5;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
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
    var seq=new CharArrSeq.UncheckedList();
    IntFunction<Character[]> arrConstructor=Character[]::new;
    var result=seq.toArray(arrConstructor);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertEquals(0,result.length);
  }
  @Test
  public void testUncheckedListtoArray_IntFunction_seqIsNotEmpty_nonMod(){
    var seq=new CharArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    IntFunction<Character[]> arrConstructor=Character[]::new;
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
    var seq=new CharArrSeq.UncheckedList();
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testUncheckedListclear_void_seqIsNotEmpty(){
    var seq=new CharArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
    for(int i=0;i<100;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListisEmpty_void_seqIsEmpty(){
    var seq=new CharArrSeq.UncheckedList();
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testUncheckedListisEmpty_void_seqIsNotEmpty(){
    var seq=new CharArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    Assertions.assertFalse(seq.isEmpty());
  }
  @Test
  public void testUncheckedListisEmpty_void_seqIsBeingCleared(){
    var seq=new CharArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;){
      Assertions.assertFalse(seq.isEmpty());
      itr.nextChar();
      itr.remove();
    }
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testUncheckedListsize_void_seqIsEmpty(){
    var seq=new CharArrSeq.UncheckedList();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
  }
  @Test
  public void testUncheckedListsize_void_seqIsNotEmpty(){
    var seq=new CharArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
  }
  @Test
  public void testUncheckedListsize_void_seqIsBeingCleared(){
    var seq=new CharArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;){
      itr.nextChar();
      itr.remove();
      Assertions.assertEquals(i,seq.size());
    }
  }
  @Test
  public void testUncheckedListforEach_Consumer_SeqIsEmpty_NoMod(){
    var seq=new CharArrSeq.UncheckedList();
    var consumer=new CharMonitoredConsumer();
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testUncheckedListforEach_Consumer_SeqIsNotEmpty_NoMod(){
    var seq=new CharArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var consumer=new CharMonitoredConsumer();
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
  public void testUncheckedListforEach_CharConsumer_SeqIsEmpty_NoMod(){
    var seq=new CharArrSeq.UncheckedList();
    var consumer=new CharMonitoredConsumer();
    seq.forEach((CharConsumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testUncheckedListforEach_CharConsumer_SeqIsNotEmpty_NoMod(){
    var seq=new CharArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var consumer=new CharMonitoredConsumer();
    seq.forEach((CharConsumer)consumer);
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
  public void testUncheckedListadd_char_initialCapacityDEFAULT(){
    var seq=new CharArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_char_initialCapacityNULL(){
    var seq=new CharArrSeq.UncheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_char_initialCapacity50(){
    var seq=new CharArrSeq.UncheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_Character_initialCapacityDEFAULT(){
    var seq=new CharArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToCharacter(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_Character_initialCapacityNULL(){
    var seq=new CharArrSeq.UncheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToCharacter(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_Character_initialCapacity50(){
    var seq=new CharArrSeq.UncheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToCharacter(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_boolean_initialCapacityDEFAULT(){
    var seq=new CharArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_boolean_initialCapacityNULL(){
    var seq=new CharArrSeq.UncheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_boolean_initialCapacity50(){
    var seq=new CharArrSeq.UncheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_Boolean_initialCapacityDEFAULT(){
    var seq=new CharArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_Boolean_initialCapacityNULL(){
    var seq=new CharArrSeq.UncheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_Boolean_initialCapacity50(){
    var seq=new CharArrSeq.UncheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListtoCharArray_void_seqIsEmpty(){
    var seq=new CharArrSeq.UncheckedList();
    Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,seq.toCharArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedListtoCharArray_void_seqIsNotEmpty(){
    var seq=new CharArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
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
  public void testUncheckedListtoArray_void_seqIsEmpty(){
    var seq=new CharArrSeq.UncheckedList();
    Assertions.assertSame(OmniArray.OfChar.DEFAULT_BOXED_ARR,seq.toArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedListtoArray_void_seqIsNotEmpty(){
    var seq=new CharArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
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
    var seq=new CharArrSeq.UncheckedList();
    Assertions.assertSame(OmniArray.OfDouble.DEFAULT_ARR,seq.toDoubleArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedListtoDoubleArray_void_seqIsNotEmpty(){
    var seq=new CharArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
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
    var seq=new CharArrSeq.UncheckedList();
    Assertions.assertSame(OmniArray.OfFloat.DEFAULT_ARR,seq.toFloatArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedListtoFloatArray_void_seqIsNotEmpty(){
    var seq=new CharArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
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
    var seq=new CharArrSeq.UncheckedList();
    Assertions.assertSame(OmniArray.OfLong.DEFAULT_ARR,seq.toLongArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedListtoLongArray_void_seqIsNotEmpty(){
    var seq=new CharArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
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
    var seq=new CharArrSeq.UncheckedList();
    Assertions.assertSame(OmniArray.OfInt.DEFAULT_ARR,seq.toIntArray());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedListtoIntArray_void_seqIsNotEmpty(){
    var seq=new CharArrSeq.UncheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
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
  public void testCheckedStackconstructor_void_initialCapacityDEFAULT(){
    var seq=new CharArrSeq.CheckedStack();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,seq.arr);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackconstructor_intchar_initialCapacityNULL(){
    var seq=new CharArrSeq.CheckedStack(0,null);
    Assertions.assertEquals(0,seq.size);
    Assertions.assertNull(seq.arr);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackconstructor_int_initialCapacity50(){
    var seq=new CharArrSeq.CheckedStack(50);
    Assertions.assertEquals(0,seq.size);
    switch(50){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(50,seq.arr.length);
    }
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackconstructor_int_initialCapacity0(){
    var seq=new CharArrSeq.CheckedStack(0);
    Assertions.assertEquals(0,seq.size);
    switch(0){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(0,seq.arr.length);
    }
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackconstructor_int_initialCapacity10(){
    var seq=new CharArrSeq.CheckedStack(10);
    Assertions.assertEquals(0,seq.size);
    switch(10){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,seq.arr);
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
    var seq=new CharArrSeq.CheckedStack();
    var clone=(CharArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfChar.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackClone_initialCapacityDEFAULT_seqIsNotEmpty()
  {
    var seq=new CharArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var clone=(CharArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedStackClone_initialCapacityNULL_seqIsEmpty()
  {
    var seq=new CharArrSeq.CheckedStack(0,null);
    var clone=(CharArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfChar.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackClone_initialCapacityNULL_seqIsNotEmpty()
  {
    var seq=new CharArrSeq.CheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var clone=(CharArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedStackClone_initialCapacity50_seqIsEmpty()
  {
    var seq=new CharArrSeq.CheckedStack(50);
    var clone=(CharArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfChar.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackClone_initialCapacity50_seqIsNotEmpty()
  {
    var seq=new CharArrSeq.CheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var clone=(CharArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedStackClone_initialCapacity0_seqIsEmpty()
  {
    var seq=new CharArrSeq.CheckedStack(0);
    var clone=(CharArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfChar.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackClone_initialCapacity0_seqIsNotEmpty()
  {
    var seq=new CharArrSeq.CheckedStack(0);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var clone=(CharArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedStackClone_initialCapacity10_seqIsEmpty()
  {
    var seq=new CharArrSeq.CheckedStack(10);
    var clone=(CharArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfChar.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackClone_initialCapacity10_seqIsNotEmpty()
  {
    var seq=new CharArrSeq.CheckedStack(10);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var clone=(CharArrSeq.CheckedStack)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedStacktoArray_ObjectArray_zeroLengthArrayAndSequenceIsEmpty(){
    var seq=new CharArrSeq.CheckedStack();
    Character[] paramArr=new Character[0];
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertEquals(0,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
  }
  @Test
  public void testCheckedStacktoArray_ObjectArray_zeroLengthArrayAndSequenceNotEmpty(){
    var seq=new CharArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    Character[] paramArr=new Character[0];
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
    var seq=new CharArrSeq.CheckedStack();
    Character[] paramArr=new Character[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertTochar(paramArr.length);
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertEquals(5,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    Assertions.assertNull(result[0]);
    for(int i=1;i<result.length;++i){
      Assertions.assertEquals((Object)TypeConversionUtil.convertTochar(paramArr.length),result[i]);
    }
  }
  @Test
  public void testCheckedStacktoArray_ObjectArray_overSizedArray(){
    var seq=new CharArrSeq.CheckedStack();
    Character[] paramArr=new Character[10];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertTochar(paramArr.length);
    }
    for(int i=0;i<5;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
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
      Assertions.assertEquals((Object)TypeConversionUtil.convertTochar(paramArr.length),result[i]);
    }
  }
  @Test
  public void testCheckedStacktoArray_ObjectArray_undersizedArray(){
    var seq=new CharArrSeq.CheckedStack();
    Character[] paramArr=new Character[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertTochar(10);
    }
    for(int i=0;i<10;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
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
    var seq=new CharArrSeq.CheckedStack();
    Character[] paramArr=new Character[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertTochar(5);
    }
    for(int i=0;i<5;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
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
    var seq=new CharArrSeq.CheckedStack();
    IntFunction<Character[]> arrConstructor=Character[]::new;
    var result=seq.toArray(arrConstructor);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertEquals(0,result.length);
  }
  @Test
  public void testCheckedStacktoArray_IntFunction_seqIsNotEmpty_nonMod(){
    var seq=new CharArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    IntFunction<Character[]> arrConstructor=Character[]::new;
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
    var seq=new CharArrSeq.CheckedStack();
    IntFunction<Character[]> arrConstructor=(int arrSize)->{
      seq.add(TypeConversionUtil.convertTochar(arrSize));
      return new Character[arrSize];
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(1,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertTochar(0),seq.arr[0]);
    Assertions.assertEquals(1,seq.modCount);
  }
  @Test
  public void testCheckedStacktoArray_IntFunction_seqIsNotEmpty_moddingArrayConstructor(){
    var seq=new CharArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    IntFunction<Character[]> arrConstructor=(int arrSize)->{
      seq.add(TypeConversionUtil.convertTochar(arrSize));
      return new Character[arrSize];
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(101,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertTochar(100),seq.arr[100]);
    Assertions.assertEquals(101,seq.modCount);
  }
  @Test
  public void testCheckedStacktoArray_IntFunction_seqIsEmpty_throwingArrayConstructor(){
    var seq=new CharArrSeq.CheckedStack();
    IntFunction<Character[]> arrConstructor=(int arrSize)->{
      throw new IndexOutOfBoundsException();
    };
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStacktoArray_IntFunction_seqIsNotEmpty_throwingArrayConstructor(){
    var seq=new CharArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    IntFunction<Character[]> arrConstructor=(int arrSize)->{
      throw new IndexOutOfBoundsException();
    };
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedStacktoArray_IntFunction_seqIsEmpty_throwingAndModdingArrConstructor(){
    var seq=new CharArrSeq.CheckedStack();
    IntFunction<Character[]> arrConstructor=(int arrSize)->{
      seq.add(TypeConversionUtil.convertTochar(arrSize));
      throw new IndexOutOfBoundsException();
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(1,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertTochar(0),seq.arr[0]);
    Assertions.assertEquals(1,seq.modCount);
  }
  @Test
  public void testCheckedStacktoArray_IntFunction_seqIsNotEmpty_throwingAndModdingArrConstructor(){
    var seq=new CharArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    IntFunction<Character[]> arrConstructor=(int arrSize)->{
      seq.add(TypeConversionUtil.convertTochar(arrSize));
      throw new IndexOutOfBoundsException();
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(101,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertTochar(100),seq.arr[100]);
    Assertions.assertEquals(101,seq.modCount);
  }
  @Test
  public void testCheckedStackclear_void_seqIsEmpty(){
    var seq=new CharArrSeq.CheckedStack();
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackclear_void_seqIsNotEmpty(){
    var seq=new CharArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
    for(int i=0;i<100;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
    Assertions.assertEquals(101,seq.modCount);
  }
  @Test
  public void testCheckedStackisEmpty_void_seqIsEmpty(){
    var seq=new CharArrSeq.CheckedStack();
    Assertions.assertTrue(seq.isEmpty());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackisEmpty_void_seqIsNotEmpty(){
    var seq=new CharArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    Assertions.assertFalse(seq.isEmpty());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedStackisEmpty_void_seqIsBeingCleared(){
    var seq=new CharArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;){
      Assertions.assertFalse(seq.isEmpty());
      itr.nextChar();
      itr.remove();
      Assertions.assertEquals(100+(100-i),seq.modCount);
    }
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testCheckedStacksize_void_seqIsEmpty(){
    var seq=new CharArrSeq.CheckedStack();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStacksize_void_seqIsNotEmpty(){
    var seq=new CharArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedStacksize_void_seqIsBeingCleared(){
    var seq=new CharArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;){
      itr.nextChar();
      itr.remove();
      Assertions.assertEquals(i,seq.size());
      Assertions.assertEquals(100+(100-i),seq.modCount);
    }
  }
  @Test
  public void testCheckedStackforEach_Consumer_SeqIsEmpty_NoMod(){
    var seq=new CharArrSeq.CheckedStack();
    var consumer=new CharMonitoredConsumer();
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedStackforEach_Consumer_SeqIsNotEmpty_NoMod(){
    var seq=new CharArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var consumer=new CharMonitoredConsumer();
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
    var seq=new CharArrSeq.CheckedStack();
    var consumer=new CharMonitoredConsumer.ModifyingArrSeqCheckedStackConsumer(seq);
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedStackforEach_Consumer_SeqIsNotEmpty_ModdingConsumer(){
    var seq=new CharArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var consumer=new CharMonitoredConsumer.ModifyingArrSeqCheckedStackConsumer(seq);
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
    var seq=new CharArrSeq.CheckedStack();
    var consumer=new CharMonitoredConsumer.Throwing();
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedStackforEach_Consumer_SeqIsNotEmpty_ThrowingConsumer(){
    var seq=new CharArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var consumer=new CharMonitoredConsumer.Throwing();
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
    var seq=new CharArrSeq.CheckedStack();
    var consumer=new CharMonitoredConsumer.ModifyingArrSeqCheckedStackAndThrowingConsumer(seq);
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedStackforEach_Consumer_SeqIsNotEmpty_ThrowingAndModdingConsumer(){
    var seq=new CharArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var consumer=new CharMonitoredConsumer.ModifyingArrSeqCheckedStackAndThrowingConsumer(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.forEach((Consumer)consumer));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(102,seq.modCount);
    Assertions.assertEquals(1,consumer.size());
  }
  @Test
  public void testCheckedStackforEach_CharConsumer_SeqIsEmpty_NoMod(){
    var seq=new CharArrSeq.CheckedStack();
    var consumer=new CharMonitoredConsumer();
    seq.forEach((CharConsumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedStackforEach_CharConsumer_SeqIsNotEmpty_NoMod(){
    var seq=new CharArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var consumer=new CharMonitoredConsumer();
    seq.forEach((CharConsumer)consumer);
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
  public void testCheckedStackforEach_CharConsumer_SeqIsEmpty_ModdingConsumer(){
    var seq=new CharArrSeq.CheckedStack();
    var consumer=new CharMonitoredConsumer.ModifyingArrSeqCheckedStackConsumer(seq);
    seq.forEach((CharConsumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedStackforEach_CharConsumer_SeqIsNotEmpty_ModdingConsumer(){
    var seq=new CharArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var consumer=new CharMonitoredConsumer.ModifyingArrSeqCheckedStackConsumer(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.forEach((CharConsumer)consumer));
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
  public void testCheckedStackforEach_CharConsumer_SeqIsEmpty_ThrowingConsumer(){
    var seq=new CharArrSeq.CheckedStack();
    var consumer=new CharMonitoredConsumer.Throwing();
    seq.forEach((CharConsumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedStackforEach_CharConsumer_SeqIsNotEmpty_ThrowingConsumer(){
    var seq=new CharArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var consumer=new CharMonitoredConsumer.Throwing();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.forEach((CharConsumer)consumer));
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
  public void testCheckedStackforEach_CharConsumer_SeqIsEmpty_ThrowingAndModdingConsumer(){
    var seq=new CharArrSeq.CheckedStack();
    var consumer=new CharMonitoredConsumer.ModifyingArrSeqCheckedStackAndThrowingConsumer(seq);
    seq.forEach((CharConsumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedStackforEach_CharConsumer_SeqIsNotEmpty_ThrowingAndModdingConsumer(){
    var seq=new CharArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var consumer=new CharMonitoredConsumer.ModifyingArrSeqCheckedStackAndThrowingConsumer(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.forEach((CharConsumer)consumer));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(102,seq.modCount);
    Assertions.assertEquals(1,consumer.size());
  }
  @Test
  public void testCheckedStackadd_char_initialCapacityDEFAULT(){
    var seq=new CharArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_char_initialCapacityNULL(){
    var seq=new CharArrSeq.CheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_char_initialCapacity50(){
    var seq=new CharArrSeq.CheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_Character_initialCapacityDEFAULT(){
    var seq=new CharArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToCharacter(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_Character_initialCapacityNULL(){
    var seq=new CharArrSeq.CheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToCharacter(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_Character_initialCapacity50(){
    var seq=new CharArrSeq.CheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToCharacter(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_boolean_initialCapacityDEFAULT(){
    var seq=new CharArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_boolean_initialCapacityNULL(){
    var seq=new CharArrSeq.CheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_boolean_initialCapacity50(){
    var seq=new CharArrSeq.CheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_Boolean_initialCapacityDEFAULT(){
    var seq=new CharArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_Boolean_initialCapacityNULL(){
    var seq=new CharArrSeq.CheckedStack(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackadd_Boolean_initialCapacity50(){
    var seq=new CharArrSeq.CheckedStack(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStacktoCharArray_void_seqIsEmpty(){
    var seq=new CharArrSeq.CheckedStack();
    Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,seq.toCharArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStacktoCharArray_void_seqIsNotEmpty(){
    var seq=new CharArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
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
  public void testCheckedStacktoArray_void_seqIsEmpty(){
    var seq=new CharArrSeq.CheckedStack();
    Assertions.assertSame(OmniArray.OfChar.DEFAULT_BOXED_ARR,seq.toArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStacktoArray_void_seqIsNotEmpty(){
    var seq=new CharArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
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
    var seq=new CharArrSeq.CheckedStack();
    Assertions.assertSame(OmniArray.OfDouble.DEFAULT_ARR,seq.toDoubleArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStacktoDoubleArray_void_seqIsNotEmpty(){
    var seq=new CharArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
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
    var seq=new CharArrSeq.CheckedStack();
    Assertions.assertSame(OmniArray.OfFloat.DEFAULT_ARR,seq.toFloatArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStacktoFloatArray_void_seqIsNotEmpty(){
    var seq=new CharArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
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
    var seq=new CharArrSeq.CheckedStack();
    Assertions.assertSame(OmniArray.OfLong.DEFAULT_ARR,seq.toLongArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStacktoLongArray_void_seqIsNotEmpty(){
    var seq=new CharArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
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
    var seq=new CharArrSeq.CheckedStack();
    Assertions.assertSame(OmniArray.OfInt.DEFAULT_ARR,seq.toIntArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStacktoIntArray_void_seqIsNotEmpty(){
    var seq=new CharArrSeq.CheckedStack();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
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
  public void testCheckedListconstructor_void_initialCapacityDEFAULT(){
    var seq=new CharArrSeq.CheckedList();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,seq.arr);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListconstructor_intchar_initialCapacityNULL(){
    var seq=new CharArrSeq.CheckedList(0,null);
    Assertions.assertEquals(0,seq.size);
    Assertions.assertNull(seq.arr);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListconstructor_int_initialCapacity50(){
    var seq=new CharArrSeq.CheckedList(50);
    Assertions.assertEquals(0,seq.size);
    switch(50){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(50,seq.arr.length);
    }
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListconstructor_int_initialCapacity0(){
    var seq=new CharArrSeq.CheckedList(0);
    Assertions.assertEquals(0,seq.size);
    switch(0){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,seq.arr);
      break;
    default:
      Assertions.assertNotNull(seq.arr);
      Assertions.assertEquals(0,seq.arr.length);
    }
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListconstructor_int_initialCapacity10(){
    var seq=new CharArrSeq.CheckedList(10);
    Assertions.assertEquals(0,seq.size);
    switch(10){
    case 0:
      Assertions.assertNull(seq.arr);
      break;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,seq.arr);
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
    var seq=new CharArrSeq.CheckedList();
    var clone=(CharArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfChar.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListClone_initialCapacityDEFAULT_seqIsNotEmpty()
  {
    var seq=new CharArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var clone=(CharArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListClone_initialCapacityNULL_seqIsEmpty()
  {
    var seq=new CharArrSeq.CheckedList(0,null);
    var clone=(CharArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfChar.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListClone_initialCapacityNULL_seqIsNotEmpty()
  {
    var seq=new CharArrSeq.CheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var clone=(CharArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListClone_initialCapacity50_seqIsEmpty()
  {
    var seq=new CharArrSeq.CheckedList(50);
    var clone=(CharArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfChar.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListClone_initialCapacity50_seqIsNotEmpty()
  {
    var seq=new CharArrSeq.CheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var clone=(CharArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListClone_initialCapacity0_seqIsEmpty()
  {
    var seq=new CharArrSeq.CheckedList(0);
    var clone=(CharArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfChar.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListClone_initialCapacity0_seqIsNotEmpty()
  {
    var seq=new CharArrSeq.CheckedList(0);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var clone=(CharArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListClone_initialCapacity10_seqIsEmpty()
  {
    var seq=new CharArrSeq.CheckedList(10);
    var clone=(CharArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(0,clone.size());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertSame(clone.arr,OmniArray.OfChar.DEFAULT_ARR);
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListClone_initialCapacity10_seqIsNotEmpty()
  {
    var seq=new CharArrSeq.CheckedList(10);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var clone=(CharArrSeq.CheckedList)seq.clone();
    Assertions.assertEquals(100,clone.size());
    Assertions.assertEquals(100,seq.size());
    Assertions.assertNotSame(seq.arr,clone.arr);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),clone.arr[i]);
    }
    Assertions.assertEquals(0,clone.modCount);
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListtoArray_ObjectArray_zeroLengthArrayAndSequenceIsEmpty(){
    var seq=new CharArrSeq.CheckedList();
    Character[] paramArr=new Character[0];
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertEquals(0,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
  }
  @Test
  public void testCheckedListtoArray_ObjectArray_zeroLengthArrayAndSequenceNotEmpty(){
    var seq=new CharArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    Character[] paramArr=new Character[0];
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
    var seq=new CharArrSeq.CheckedList();
    Character[] paramArr=new Character[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertTochar(paramArr.length);
    }
    var result=seq.toArray(paramArr);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertEquals(5,result.length);
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertSame(paramArr,result);
    Assertions.assertNull(result[0]);
    for(int i=1;i<result.length;++i){
      Assertions.assertEquals((Object)TypeConversionUtil.convertTochar(paramArr.length),result[i]);
    }
  }
  @Test
  public void testCheckedListtoArray_ObjectArray_overSizedArray(){
    var seq=new CharArrSeq.CheckedList();
    Character[] paramArr=new Character[10];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertTochar(paramArr.length);
    }
    for(int i=0;i<5;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
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
      Assertions.assertEquals((Object)TypeConversionUtil.convertTochar(paramArr.length),result[i]);
    }
  }
  @Test
  public void testCheckedListtoArray_ObjectArray_undersizedArray(){
    var seq=new CharArrSeq.CheckedList();
    Character[] paramArr=new Character[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertTochar(10);
    }
    for(int i=0;i<10;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
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
    var seq=new CharArrSeq.CheckedList();
    Character[] paramArr=new Character[5];
    for(int i=0;i<paramArr.length;++i){
      paramArr[i]=TypeConversionUtil.convertTochar(5);
    }
    for(int i=0;i<5;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
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
    var seq=new CharArrSeq.CheckedList();
    IntFunction<Character[]> arrConstructor=Character[]::new;
    var result=seq.toArray(arrConstructor);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertNotSame(seq.arr,result);
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertEquals(0,result.length);
  }
  @Test
  public void testCheckedListtoArray_IntFunction_seqIsNotEmpty_nonMod(){
    var seq=new CharArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    IntFunction<Character[]> arrConstructor=Character[]::new;
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
    var seq=new CharArrSeq.CheckedList();
    IntFunction<Character[]> arrConstructor=(int arrSize)->{
      seq.add(TypeConversionUtil.convertTochar(arrSize));
      return new Character[arrSize];
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(1,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertTochar(0),seq.arr[0]);
    Assertions.assertEquals(1,seq.modCount);
  }
  @Test
  public void testCheckedListtoArray_IntFunction_seqIsNotEmpty_moddingArrayConstructor(){
    var seq=new CharArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    IntFunction<Character[]> arrConstructor=(int arrSize)->{
      seq.add(TypeConversionUtil.convertTochar(arrSize));
      return new Character[arrSize];
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(101,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertTochar(100),seq.arr[100]);
    Assertions.assertEquals(101,seq.modCount);
  }
  @Test
  public void testCheckedListtoArray_IntFunction_seqIsEmpty_throwingArrayConstructor(){
    var seq=new CharArrSeq.CheckedList();
    IntFunction<Character[]> arrConstructor=(int arrSize)->{
      throw new IndexOutOfBoundsException();
    };
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListtoArray_IntFunction_seqIsNotEmpty_throwingArrayConstructor(){
    var seq=new CharArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    IntFunction<Character[]> arrConstructor=(int arrSize)->{
      throw new IndexOutOfBoundsException();
    };
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListtoArray_IntFunction_seqIsEmpty_throwingAndModdingArrConstructor(){
    var seq=new CharArrSeq.CheckedList();
    IntFunction<Character[]> arrConstructor=(int arrSize)->{
      seq.add(TypeConversionUtil.convertTochar(arrSize));
      throw new IndexOutOfBoundsException();
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(1,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertTochar(0),seq.arr[0]);
    Assertions.assertEquals(1,seq.modCount);
  }
  @Test
  public void testCheckedListtoArray_IntFunction_seqIsNotEmpty_throwingAndModdingArrConstructor(){
    var seq=new CharArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    IntFunction<Character[]> arrConstructor=(int arrSize)->{
      seq.add(TypeConversionUtil.convertTochar(arrSize));
      throw new IndexOutOfBoundsException();
    };
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(arrConstructor));
    Assertions.assertEquals(101,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertTochar(100),seq.arr[100]);
    Assertions.assertEquals(101,seq.modCount);
  }
  @Test
  public void testCheckedListclear_void_seqIsEmpty(){
    var seq=new CharArrSeq.CheckedList();
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListclear_void_seqIsNotEmpty(){
    var seq=new CharArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    seq.clear();
    Assertions.assertTrue(seq.isEmpty());
    for(int i=0;i<100;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
    Assertions.assertEquals(101,seq.modCount);
  }
  @Test
  public void testCheckedListisEmpty_void_seqIsEmpty(){
    var seq=new CharArrSeq.CheckedList();
    Assertions.assertTrue(seq.isEmpty());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListisEmpty_void_seqIsNotEmpty(){
    var seq=new CharArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    Assertions.assertFalse(seq.isEmpty());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListisEmpty_void_seqIsBeingCleared(){
    var seq=new CharArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;){
      Assertions.assertFalse(seq.isEmpty());
      itr.nextChar();
      itr.remove();
      Assertions.assertEquals(100+(100-i),seq.modCount);
    }
    Assertions.assertTrue(seq.isEmpty());
  }
  @Test
  public void testCheckedListsize_void_seqIsEmpty(){
    var seq=new CharArrSeq.CheckedList();
    Assertions.assertEquals(0,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListsize_void_seqIsNotEmpty(){
    var seq=new CharArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertEquals(seq.size,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListsize_void_seqIsBeingCleared(){
    var seq=new CharArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var itr=seq.iterator();
    for(int i=100;--i>=0;){
      itr.nextChar();
      itr.remove();
      Assertions.assertEquals(i,seq.size());
      Assertions.assertEquals(100+(100-i),seq.modCount);
    }
  }
  @Test
  public void testCheckedListforEach_Consumer_SeqIsEmpty_NoMod(){
    var seq=new CharArrSeq.CheckedList();
    var consumer=new CharMonitoredConsumer();
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedListforEach_Consumer_SeqIsNotEmpty_NoMod(){
    var seq=new CharArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var consumer=new CharMonitoredConsumer();
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
    var seq=new CharArrSeq.CheckedList();
    var consumer=new CharMonitoredConsumer.ModifyingArrSeqCheckedListConsumer(seq);
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedListforEach_Consumer_SeqIsNotEmpty_ModdingConsumer(){
    var seq=new CharArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var consumer=new CharMonitoredConsumer.ModifyingArrSeqCheckedListConsumer(seq);
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
    var seq=new CharArrSeq.CheckedList();
    var consumer=new CharMonitoredConsumer.Throwing();
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedListforEach_Consumer_SeqIsNotEmpty_ThrowingConsumer(){
    var seq=new CharArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var consumer=new CharMonitoredConsumer.Throwing();
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
    var seq=new CharArrSeq.CheckedList();
    var consumer=new CharMonitoredConsumer.ModifyingArrSeqCheckedListAndThrowingConsumer(seq);
    seq.forEach((Consumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedListforEach_Consumer_SeqIsNotEmpty_ThrowingAndModdingConsumer(){
    var seq=new CharArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var consumer=new CharMonitoredConsumer.ModifyingArrSeqCheckedListAndThrowingConsumer(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.forEach((Consumer)consumer));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(102,seq.modCount);
    Assertions.assertEquals(1,consumer.size());
  }
  @Test
  public void testCheckedListforEach_CharConsumer_SeqIsEmpty_NoMod(){
    var seq=new CharArrSeq.CheckedList();
    var consumer=new CharMonitoredConsumer();
    seq.forEach((CharConsumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedListforEach_CharConsumer_SeqIsNotEmpty_NoMod(){
    var seq=new CharArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var consumer=new CharMonitoredConsumer();
    seq.forEach((CharConsumer)consumer);
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
  public void testCheckedListforEach_CharConsumer_SeqIsEmpty_ModdingConsumer(){
    var seq=new CharArrSeq.CheckedList();
    var consumer=new CharMonitoredConsumer.ModifyingArrSeqCheckedListConsumer(seq);
    seq.forEach((CharConsumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedListforEach_CharConsumer_SeqIsNotEmpty_ModdingConsumer(){
    var seq=new CharArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var consumer=new CharMonitoredConsumer.ModifyingArrSeqCheckedListConsumer(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.forEach((CharConsumer)consumer));
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
  public void testCheckedListforEach_CharConsumer_SeqIsEmpty_ThrowingConsumer(){
    var seq=new CharArrSeq.CheckedList();
    var consumer=new CharMonitoredConsumer.Throwing();
    seq.forEach((CharConsumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedListforEach_CharConsumer_SeqIsNotEmpty_ThrowingConsumer(){
    var seq=new CharArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var consumer=new CharMonitoredConsumer.Throwing();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.forEach((CharConsumer)consumer));
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
  public void testCheckedListforEach_CharConsumer_SeqIsEmpty_ThrowingAndModdingConsumer(){
    var seq=new CharArrSeq.CheckedList();
    var consumer=new CharMonitoredConsumer.ModifyingArrSeqCheckedListAndThrowingConsumer(seq);
    seq.forEach((CharConsumer)consumer);
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertTrue(consumer.isEmpty());
  }
  @Test
  public void testCheckedListforEach_CharConsumer_SeqIsNotEmpty_ThrowingAndModdingConsumer(){
    var seq=new CharArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    var consumer=new CharMonitoredConsumer.ModifyingArrSeqCheckedListAndThrowingConsumer(seq);
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.forEach((CharConsumer)consumer));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(102,seq.modCount);
    Assertions.assertEquals(1,consumer.size());
  }
  @Test
  public void testCheckedListadd_char_initialCapacityDEFAULT(){
    var seq=new CharArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_char_initialCapacityNULL(){
    var seq=new CharArrSeq.CheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_char_initialCapacity50(){
    var seq=new CharArrSeq.CheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_Character_initialCapacityDEFAULT(){
    var seq=new CharArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToCharacter(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_Character_initialCapacityNULL(){
    var seq=new CharArrSeq.CheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToCharacter(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_Character_initialCapacity50(){
    var seq=new CharArrSeq.CheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToCharacter(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_boolean_initialCapacityDEFAULT(){
    var seq=new CharArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_boolean_initialCapacityNULL(){
    var seq=new CharArrSeq.CheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_boolean_initialCapacity50(){
    var seq=new CharArrSeq.CheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToboolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_Boolean_initialCapacityDEFAULT(){
    var seq=new CharArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_Boolean_initialCapacityNULL(){
    var seq=new CharArrSeq.CheckedList(0,null);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_Boolean_initialCapacity50(){
    var seq=new CharArrSeq.CheckedList(50);
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertToBoolean(i)));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListtoCharArray_void_seqIsEmpty(){
    var seq=new CharArrSeq.CheckedList();
    Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,seq.toCharArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListtoCharArray_void_seqIsNotEmpty(){
    var seq=new CharArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
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
  public void testCheckedListtoArray_void_seqIsEmpty(){
    var seq=new CharArrSeq.CheckedList();
    Assertions.assertSame(OmniArray.OfChar.DEFAULT_BOXED_ARR,seq.toArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListtoArray_void_seqIsNotEmpty(){
    var seq=new CharArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
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
    var seq=new CharArrSeq.CheckedList();
    Assertions.assertSame(OmniArray.OfDouble.DEFAULT_ARR,seq.toDoubleArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListtoDoubleArray_void_seqIsNotEmpty(){
    var seq=new CharArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
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
    var seq=new CharArrSeq.CheckedList();
    Assertions.assertSame(OmniArray.OfFloat.DEFAULT_ARR,seq.toFloatArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListtoFloatArray_void_seqIsNotEmpty(){
    var seq=new CharArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
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
    var seq=new CharArrSeq.CheckedList();
    Assertions.assertSame(OmniArray.OfLong.DEFAULT_ARR,seq.toLongArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListtoLongArray_void_seqIsNotEmpty(){
    var seq=new CharArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
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
    var seq=new CharArrSeq.CheckedList();
    Assertions.assertSame(OmniArray.OfInt.DEFAULT_ARR,seq.toIntArray());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListtoIntArray_void_seqIsNotEmpty(){
    var seq=new CharArrSeq.CheckedList();
    for(int i=0;i<100;++i){
      Assertions.assertTrue(seq.add(TypeConversionUtil.convertTochar(i)));
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
  public void testCheckedSubListsize(){
    var root=new CharArrSeq.CheckedList();
    var parent=root.subList(0,0);
    var seq=parent.subList(0,0);
    Assertions.assertEquals(0,seq.size());
    for(int i=0;i<100;++i)
    {
      seq.add(TypeConversionUtil.convertTochar(i));
      Assertions.assertEquals(i+1,seq.size());
    }
    parent.add(TypeConversionUtil.convertTochar(0));
    Assertions.assertThrows(ConcurrentModificationException.class,seq::size);
    root.add(TypeConversionUtil.convertTochar(0));
    Assertions.assertThrows(ConcurrentModificationException.class,parent::size);
  }
  @Test
  public void testCheckedSubListisEmpty(){
    var root=new CharArrSeq.CheckedList();
    var parent=root.subList(0,0);
    var seq=parent.subList(0,0);
    Assertions.assertTrue(seq.isEmpty());
    for(int i=0;i<100;++i)
    {
      seq.add(TypeConversionUtil.convertTochar(i));
      Assertions.assertFalse(seq.isEmpty());
    }
    var seqItr=seq.iterator();
    for(int i=0;i<100;++i)
    {
      Assertions.assertFalse(seq.isEmpty());
      seqItr.next();
      seqItr.remove();
    }
    Assertions.assertTrue(seq.isEmpty());
    parent.add(TypeConversionUtil.convertTochar(0));
    Assertions.assertThrows(ConcurrentModificationException.class,seq::isEmpty);
    root.add(TypeConversionUtil.convertTochar(0));
    Assertions.assertThrows(ConcurrentModificationException.class,parent::isEmpty);
  }
  @Test
  public void testUncheckedSubListsize(){
    var root=new CharArrSeq.UncheckedList();
    var parent=root.subList(0,0);
    var seq=parent.subList(0,0);
    Assertions.assertEquals(0,seq.size());
    for(int i=0;i<100;++i)
    {
      seq.add(TypeConversionUtil.convertTochar(i));
      Assertions.assertEquals(i+1,seq.size());
    }
  }
  @Test
  public void testUncheckedSubListisEmpty(){
    var root=new CharArrSeq.UncheckedList();
    var parent=root.subList(0,0);
    var seq=parent.subList(0,0);
    Assertions.assertTrue(seq.isEmpty());
    for(int i=0;i<100;++i)
    {
      seq.add(TypeConversionUtil.convertTochar(i));
      Assertions.assertFalse(seq.isEmpty());
    }
    var seqItr=seq.iterator();
    for(int i=0;i<100;++i)
    {
      Assertions.assertFalse(seq.isEmpty());
      seqItr.next();
      seqItr.remove();
    }
    Assertions.assertTrue(seq.isEmpty());
  }
}
