package omni.impl.seq;
import omni.impl.seq.LongArrSeq.UncheckedStack;
import omni.impl.seq.LongArrSeq.CheckedStack;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;
public class LongArrSeqStackTest
{
  @Test
  public void testUncheckedStackpush_long_initialCapacityDEFAULT(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTolong(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_long_initialCapacityNULL(){
    UncheckedStack seq=new UncheckedStack(0,null);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTolong(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_long_initialCapacity50(){
    UncheckedStack seq=new UncheckedStack(50);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTolong(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_Long_initialCapacityDEFAULT(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToLong(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_Long_initialCapacityNULL(){
    UncheckedStack seq=new UncheckedStack(0,null);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToLong(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_Long_initialCapacity50(){
    UncheckedStack seq=new UncheckedStack(50);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToLong(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_boolean_initialCapacityDEFAULT(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_boolean_initialCapacityNULL(){
    UncheckedStack seq=new UncheckedStack(0,null);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_boolean_initialCapacity50(){
    UncheckedStack seq=new UncheckedStack(50);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_Boolean_initialCapacityDEFAULT(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToBoolean(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_Boolean_initialCapacityNULL(){
    UncheckedStack seq=new UncheckedStack(0,null);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToBoolean(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_Boolean_initialCapacity50(){
    UncheckedStack seq=new UncheckedStack(50);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToBoolean(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_byte_initialCapacityDEFAULT(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTobyte(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_byte_initialCapacityNULL(){
    UncheckedStack seq=new UncheckedStack(0,null);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTobyte(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_byte_initialCapacity50(){
    UncheckedStack seq=new UncheckedStack(50);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTobyte(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_Byte_initialCapacityDEFAULT(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToByte(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_Byte_initialCapacityNULL(){
    UncheckedStack seq=new UncheckedStack(0,null);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToByte(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_Byte_initialCapacity50(){
    UncheckedStack seq=new UncheckedStack(50);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToByte(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_char_initialCapacityDEFAULT(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_char_initialCapacityNULL(){
    UncheckedStack seq=new UncheckedStack(0,null);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_char_initialCapacity50(){
    UncheckedStack seq=new UncheckedStack(50);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_Character_initialCapacityDEFAULT(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToCharacter(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_Character_initialCapacityNULL(){
    UncheckedStack seq=new UncheckedStack(0,null);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToCharacter(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_Character_initialCapacity50(){
    UncheckedStack seq=new UncheckedStack(50);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToCharacter(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_short_initialCapacityDEFAULT(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_short_initialCapacityNULL(){
    UncheckedStack seq=new UncheckedStack(0,null);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_short_initialCapacity50(){
    UncheckedStack seq=new UncheckedStack(50);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_Short_initialCapacityDEFAULT(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToShort(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_Short_initialCapacityNULL(){
    UncheckedStack seq=new UncheckedStack(0,null);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToShort(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_Short_initialCapacity50(){
    UncheckedStack seq=new UncheckedStack(50);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToShort(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_int_initialCapacityDEFAULT(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToint(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_int_initialCapacityNULL(){
    UncheckedStack seq=new UncheckedStack(0,null);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToint(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_int_initialCapacity50(){
    UncheckedStack seq=new UncheckedStack(50);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToint(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_Integer_initialCapacityDEFAULT(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToInteger(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_Integer_initialCapacityNULL(){
    UncheckedStack seq=new UncheckedStack(0,null);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToInteger(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpush_Integer_initialCapacity50(){
    UncheckedStack seq=new UncheckedStack(50);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToInteger(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpopLong_void_seqIsNotEmpty(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTolong(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTolong(100-i-1),seq.popLong());
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedStackpollLong_void_seqIsEmpty(){
    UncheckedStack seq=new UncheckedStack();
    Assertions.assertEquals(Long.MIN_VALUE,seq.pollLong());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStackpeekLong_void_seqIsEmpty(){
    UncheckedStack seq=new UncheckedStack();
    Assertions.assertEquals(Long.MIN_VALUE,seq.peekLong());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStackpollLong_void_seqIsNotEmpty(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTolong(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTolong(100-i-1),seq.pollLong());
      Assertions.assertEquals(100-i-1,seq.size());
    }
    Assertions.assertEquals(Long.MIN_VALUE,seq.pollLong());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStackpeekLong_void_seqIsNotEmpty(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTolong(i));
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.peekLong());
      Assertions.assertEquals(i+1,seq.size());
    }
    for(int i=0;i<100;++i)
    {
      var peeked=seq.peekLong();
      var polled=seq.pollLong();
      Assertions.assertEquals(TypeConversionUtil.convertTolong(100-i-1),peeked);
      Assertions.assertEquals(polled,peeked);
      Assertions.assertEquals(100-i-1,seq.size());
    }
    Assertions.assertEquals(Long.MIN_VALUE,seq.peekLong());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStackpop_void_seqIsNotEmpty(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTolong(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToLong(100-i-1),seq.pop());
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedStackpoll_void_seqIsEmpty(){
    UncheckedStack seq=new UncheckedStack();
    Assertions.assertEquals(null,seq.poll());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStackpeek_void_seqIsEmpty(){
    UncheckedStack seq=new UncheckedStack();
    Assertions.assertEquals(null,seq.peek());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStackpoll_void_seqIsNotEmpty(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTolong(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToLong(100-i-1),seq.poll());
      Assertions.assertEquals(100-i-1,seq.size());
    }
    Assertions.assertEquals(null,seq.poll());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStackpeek_void_seqIsNotEmpty(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTolong(i));
      Assertions.assertEquals(TypeConversionUtil.convertToLong(i),seq.peek());
      Assertions.assertEquals(i+1,seq.size());
    }
    for(int i=0;i<100;++i)
    {
      var peeked=seq.peek();
      var polled=seq.poll();
      Assertions.assertEquals(TypeConversionUtil.convertToLong(100-i-1),peeked);
      Assertions.assertEquals(polled,peeked);
      Assertions.assertEquals(100-i-1,seq.size());
    }
    Assertions.assertEquals(null,seq.peek());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStackpopDouble_void_seqIsNotEmpty(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTolong(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(100-i-1),seq.popDouble());
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedStackpollDouble_void_seqIsEmpty(){
    UncheckedStack seq=new UncheckedStack();
    Assertions.assertEquals(Double.NaN,seq.pollDouble());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStackpeekDouble_void_seqIsEmpty(){
    UncheckedStack seq=new UncheckedStack();
    Assertions.assertEquals(Double.NaN,seq.peekDouble());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStackpollDouble_void_seqIsNotEmpty(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTolong(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(100-i-1),seq.pollDouble());
      Assertions.assertEquals(100-i-1,seq.size());
    }
    Assertions.assertEquals(Double.NaN,seq.pollDouble());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStackpeekDouble_void_seqIsNotEmpty(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTolong(i));
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(i),seq.peekDouble());
      Assertions.assertEquals(i+1,seq.size());
    }
    for(int i=0;i<100;++i)
    {
      var peeked=seq.peekDouble();
      var polled=seq.pollDouble();
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(100-i-1),peeked);
      Assertions.assertEquals(polled,peeked);
      Assertions.assertEquals(100-i-1,seq.size());
    }
    Assertions.assertEquals(Double.NaN,seq.peekDouble());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStackpopFloat_void_seqIsNotEmpty(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTolong(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(100-i-1),seq.popFloat());
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedStackpollFloat_void_seqIsEmpty(){
    UncheckedStack seq=new UncheckedStack();
    Assertions.assertEquals(Float.NaN,seq.pollFloat());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStackpeekFloat_void_seqIsEmpty(){
    UncheckedStack seq=new UncheckedStack();
    Assertions.assertEquals(Float.NaN,seq.peekFloat());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStackpollFloat_void_seqIsNotEmpty(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTolong(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(100-i-1),seq.pollFloat());
      Assertions.assertEquals(100-i-1,seq.size());
    }
    Assertions.assertEquals(Float.NaN,seq.pollFloat());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStackpeekFloat_void_seqIsNotEmpty(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTolong(i));
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.peekFloat());
      Assertions.assertEquals(i+1,seq.size());
    }
    for(int i=0;i<100;++i)
    {
      var peeked=seq.peekFloat();
      var polled=seq.pollFloat();
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(100-i-1),peeked);
      Assertions.assertEquals(polled,peeked);
      Assertions.assertEquals(100-i-1,seq.size());
    }
    Assertions.assertEquals(Float.NaN,seq.peekFloat());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testCheckedStackpush_long_initialCapacityDEFAULT(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTolong(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_long_initialCapacityNULL(){
    CheckedStack seq=new CheckedStack(0,null);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTolong(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_long_initialCapacity50(){
    CheckedStack seq=new CheckedStack(50);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTolong(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_Long_initialCapacityDEFAULT(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToLong(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_Long_initialCapacityNULL(){
    CheckedStack seq=new CheckedStack(0,null);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToLong(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_Long_initialCapacity50(){
    CheckedStack seq=new CheckedStack(50);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToLong(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_boolean_initialCapacityDEFAULT(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_boolean_initialCapacityNULL(){
    CheckedStack seq=new CheckedStack(0,null);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_boolean_initialCapacity50(){
    CheckedStack seq=new CheckedStack(50);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_Boolean_initialCapacityDEFAULT(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToBoolean(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_Boolean_initialCapacityNULL(){
    CheckedStack seq=new CheckedStack(0,null);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToBoolean(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_Boolean_initialCapacity50(){
    CheckedStack seq=new CheckedStack(50);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToBoolean(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_byte_initialCapacityDEFAULT(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTobyte(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_byte_initialCapacityNULL(){
    CheckedStack seq=new CheckedStack(0,null);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTobyte(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_byte_initialCapacity50(){
    CheckedStack seq=new CheckedStack(50);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTobyte(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_Byte_initialCapacityDEFAULT(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToByte(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_Byte_initialCapacityNULL(){
    CheckedStack seq=new CheckedStack(0,null);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToByte(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_Byte_initialCapacity50(){
    CheckedStack seq=new CheckedStack(50);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToByte(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_char_initialCapacityDEFAULT(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_char_initialCapacityNULL(){
    CheckedStack seq=new CheckedStack(0,null);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_char_initialCapacity50(){
    CheckedStack seq=new CheckedStack(50);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_Character_initialCapacityDEFAULT(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToCharacter(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_Character_initialCapacityNULL(){
    CheckedStack seq=new CheckedStack(0,null);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToCharacter(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_Character_initialCapacity50(){
    CheckedStack seq=new CheckedStack(50);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToCharacter(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_short_initialCapacityDEFAULT(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_short_initialCapacityNULL(){
    CheckedStack seq=new CheckedStack(0,null);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_short_initialCapacity50(){
    CheckedStack seq=new CheckedStack(50);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_Short_initialCapacityDEFAULT(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToShort(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_Short_initialCapacityNULL(){
    CheckedStack seq=new CheckedStack(0,null);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToShort(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_Short_initialCapacity50(){
    CheckedStack seq=new CheckedStack(50);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToShort(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_int_initialCapacityDEFAULT(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToint(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_int_initialCapacityNULL(){
    CheckedStack seq=new CheckedStack(0,null);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToint(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_int_initialCapacity50(){
    CheckedStack seq=new CheckedStack(50);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToint(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_Integer_initialCapacityDEFAULT(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToInteger(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_Integer_initialCapacityNULL(){
    CheckedStack seq=new CheckedStack(0,null);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToInteger(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpush_Integer_initialCapacity50(){
    CheckedStack seq=new CheckedStack(50);
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToInteger(i));
    }
    Assertions.assertEquals(100,seq.size);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seq.size;++i){
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpopLong_void_seqIsEmpty(){
    CheckedStack seq=new CheckedStack();
    Assertions.assertThrows(NoSuchElementException.class,()->seq.popLong());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackpopLong_void_seqIsNotEmpty(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTolong(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTolong(100-i-1),seq.popLong());
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
  }
  @Test
  public void testCheckedStackpollLong_void_seqIsEmpty(){
    CheckedStack seq=new CheckedStack();
    Assertions.assertEquals(Long.MIN_VALUE,seq.pollLong());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackpeekLong_void_seqIsEmpty(){
    CheckedStack seq=new CheckedStack();
    Assertions.assertEquals(Long.MIN_VALUE,seq.peekLong());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackpollLong_void_seqIsNotEmpty(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTolong(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTolong(100-i-1),seq.pollLong());
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
    Assertions.assertEquals(Long.MIN_VALUE,seq.pollLong());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(200,seq.modCount);
  }
  @Test
  public void testCheckedStackpeekLong_void_seqIsNotEmpty(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTolong(i));
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.peekLong());
      Assertions.assertEquals(i+1,seq.size());
      Assertions.assertEquals(i+1,seq.modCount);
    }
    for(int i=0;i<100;++i)
    {
      var peeked=seq.peekLong();
      var polled=seq.pollLong();
      Assertions.assertEquals(TypeConversionUtil.convertTolong(100-i-1),peeked);
      Assertions.assertEquals(polled,peeked);
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
    Assertions.assertEquals(Long.MIN_VALUE,seq.peekLong());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(200,seq.modCount);
  }
  @Test
  public void testCheckedStackpop_void_seqIsEmpty(){
    CheckedStack seq=new CheckedStack();
    Assertions.assertThrows(NoSuchElementException.class,()->seq.pop());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackpop_void_seqIsNotEmpty(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTolong(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToLong(100-i-1),seq.pop());
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
  }
  @Test
  public void testCheckedStackpoll_void_seqIsEmpty(){
    CheckedStack seq=new CheckedStack();
    Assertions.assertEquals(null,seq.poll());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackpeek_void_seqIsEmpty(){
    CheckedStack seq=new CheckedStack();
    Assertions.assertEquals(null,seq.peek());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackpoll_void_seqIsNotEmpty(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTolong(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToLong(100-i-1),seq.poll());
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
    Assertions.assertEquals(null,seq.poll());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(200,seq.modCount);
  }
  @Test
  public void testCheckedStackpeek_void_seqIsNotEmpty(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTolong(i));
      Assertions.assertEquals(TypeConversionUtil.convertToLong(i),seq.peek());
      Assertions.assertEquals(i+1,seq.size());
      Assertions.assertEquals(i+1,seq.modCount);
    }
    for(int i=0;i<100;++i)
    {
      var peeked=seq.peek();
      var polled=seq.poll();
      Assertions.assertEquals(TypeConversionUtil.convertToLong(100-i-1),peeked);
      Assertions.assertEquals(polled,peeked);
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
    Assertions.assertEquals(null,seq.peek());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(200,seq.modCount);
  }
  @Test
  public void testCheckedStackpopDouble_void_seqIsEmpty(){
    CheckedStack seq=new CheckedStack();
    Assertions.assertThrows(NoSuchElementException.class,()->seq.popDouble());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackpopDouble_void_seqIsNotEmpty(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTolong(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(100-i-1),seq.popDouble());
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
  }
  @Test
  public void testCheckedStackpollDouble_void_seqIsEmpty(){
    CheckedStack seq=new CheckedStack();
    Assertions.assertEquals(Double.NaN,seq.pollDouble());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackpeekDouble_void_seqIsEmpty(){
    CheckedStack seq=new CheckedStack();
    Assertions.assertEquals(Double.NaN,seq.peekDouble());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackpollDouble_void_seqIsNotEmpty(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTolong(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(100-i-1),seq.pollDouble());
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
    Assertions.assertEquals(Double.NaN,seq.pollDouble());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(200,seq.modCount);
  }
  @Test
  public void testCheckedStackpeekDouble_void_seqIsNotEmpty(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTolong(i));
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(i),seq.peekDouble());
      Assertions.assertEquals(i+1,seq.size());
      Assertions.assertEquals(i+1,seq.modCount);
    }
    for(int i=0;i<100;++i)
    {
      var peeked=seq.peekDouble();
      var polled=seq.pollDouble();
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(100-i-1),peeked);
      Assertions.assertEquals(polled,peeked);
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
    Assertions.assertEquals(Double.NaN,seq.peekDouble());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(200,seq.modCount);
  }
  @Test
  public void testCheckedStackpopFloat_void_seqIsEmpty(){
    CheckedStack seq=new CheckedStack();
    Assertions.assertThrows(NoSuchElementException.class,()->seq.popFloat());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackpopFloat_void_seqIsNotEmpty(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTolong(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(100-i-1),seq.popFloat());
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
  }
  @Test
  public void testCheckedStackpollFloat_void_seqIsEmpty(){
    CheckedStack seq=new CheckedStack();
    Assertions.assertEquals(Float.NaN,seq.pollFloat());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackpeekFloat_void_seqIsEmpty(){
    CheckedStack seq=new CheckedStack();
    Assertions.assertEquals(Float.NaN,seq.peekFloat());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackpollFloat_void_seqIsNotEmpty(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTolong(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(100-i-1),seq.pollFloat());
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
    Assertions.assertEquals(Float.NaN,seq.pollFloat());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(200,seq.modCount);
  }
  @Test
  public void testCheckedStackpeekFloat_void_seqIsNotEmpty(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertTolong(i));
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.peekFloat());
      Assertions.assertEquals(i+1,seq.size());
      Assertions.assertEquals(i+1,seq.modCount);
    }
    for(int i=0;i<100;++i)
    {
      var peeked=seq.peekFloat();
      var polled=seq.pollFloat();
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(100-i-1),peeked);
      Assertions.assertEquals(polled,peeked);
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
    Assertions.assertEquals(Float.NaN,seq.peekFloat());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(200,seq.modCount);
  }
}
