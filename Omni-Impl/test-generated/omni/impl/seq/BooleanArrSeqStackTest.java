package omni.impl.seq;
import omni.impl.seq.BooleanArrSeq.UncheckedStack;
import omni.impl.seq.BooleanArrSeq.CheckedStack;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;
public class BooleanArrSeqStackTest
{
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
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
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
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
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
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
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
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
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
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
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
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedStackpopBoolean_void_seqIsNotEmpty(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(100-i-1),seq.popBoolean());
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedStackpollBoolean_void_seqIsEmpty(){
    UncheckedStack seq=new UncheckedStack();
    Assertions.assertEquals(false,seq.pollBoolean());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStackpeekBoolean_void_seqIsEmpty(){
    UncheckedStack seq=new UncheckedStack();
    Assertions.assertEquals(false,seq.peekBoolean());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStackpollBoolean_void_seqIsNotEmpty(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(100-i-1),seq.pollBoolean());
      Assertions.assertEquals(100-i-1,seq.size());
    }
    Assertions.assertEquals(false,seq.pollBoolean());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStackpeekBoolean_void_seqIsNotEmpty(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.peekBoolean());
      Assertions.assertEquals(i+1,seq.size());
    }
    for(int i=0;i<100;++i)
    {
      var peeked=seq.peekBoolean();
      var polled=seq.pollBoolean();
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(100-i-1),peeked);
      Assertions.assertEquals(polled,peeked);
      Assertions.assertEquals(100-i-1,seq.size());
    }
    Assertions.assertEquals(false,seq.peekBoolean());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStackpop_void_seqIsNotEmpty(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToBoolean(100-i-1),seq.pop());
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
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToBoolean(100-i-1),seq.poll());
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
      seq.push(TypeConversionUtil.convertToboolean(i));
      Assertions.assertEquals(TypeConversionUtil.convertToBoolean(i),seq.peek());
      Assertions.assertEquals(i+1,seq.size());
    }
    for(int i=0;i<100;++i)
    {
      var peeked=seq.peek();
      var polled=seq.poll();
      Assertions.assertEquals(TypeConversionUtil.convertToBoolean(100-i-1),peeked);
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
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTodoubleboolean(100-i-1),seq.popDouble());
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
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTodoubleboolean(100-i-1),seq.pollDouble());
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
      seq.push(TypeConversionUtil.convertToboolean(i));
      Assertions.assertEquals(TypeConversionUtil.convertTodoubleboolean(i),seq.peekDouble());
      Assertions.assertEquals(i+1,seq.size());
    }
    for(int i=0;i<100;++i)
    {
      var peeked=seq.peekDouble();
      var polled=seq.pollDouble();
      Assertions.assertEquals(TypeConversionUtil.convertTodoubleboolean(100-i-1),peeked);
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
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(100-i-1),seq.popFloat());
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
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(100-i-1),seq.pollFloat());
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
      seq.push(TypeConversionUtil.convertToboolean(i));
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(i),seq.peekFloat());
      Assertions.assertEquals(i+1,seq.size());
    }
    for(int i=0;i<100;++i)
    {
      var peeked=seq.peekFloat();
      var polled=seq.pollFloat();
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(100-i-1),peeked);
      Assertions.assertEquals(polled,peeked);
      Assertions.assertEquals(100-i-1,seq.size());
    }
    Assertions.assertEquals(Float.NaN,seq.peekFloat());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStackpopLong_void_seqIsNotEmpty(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(100-i-1),seq.popLong());
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
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(100-i-1),seq.pollLong());
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
      seq.push(TypeConversionUtil.convertToboolean(i));
      Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(i),seq.peekLong());
      Assertions.assertEquals(i+1,seq.size());
    }
    for(int i=0;i<100;++i)
    {
      var peeked=seq.peekLong();
      var polled=seq.pollLong();
      Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(100-i-1),peeked);
      Assertions.assertEquals(polled,peeked);
      Assertions.assertEquals(100-i-1,seq.size());
    }
    Assertions.assertEquals(Long.MIN_VALUE,seq.peekLong());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStackpopInt_void_seqIsNotEmpty(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(100-i-1),seq.popInt());
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedStackpollInt_void_seqIsEmpty(){
    UncheckedStack seq=new UncheckedStack();
    Assertions.assertEquals(Integer.MIN_VALUE,seq.pollInt());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStackpeekInt_void_seqIsEmpty(){
    UncheckedStack seq=new UncheckedStack();
    Assertions.assertEquals(Integer.MIN_VALUE,seq.peekInt());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStackpollInt_void_seqIsNotEmpty(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(100-i-1),seq.pollInt());
      Assertions.assertEquals(100-i-1,seq.size());
    }
    Assertions.assertEquals(Integer.MIN_VALUE,seq.pollInt());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStackpeekInt_void_seqIsNotEmpty(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.peekInt());
      Assertions.assertEquals(i+1,seq.size());
    }
    for(int i=0;i<100;++i)
    {
      var peeked=seq.peekInt();
      var polled=seq.pollInt();
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(100-i-1),peeked);
      Assertions.assertEquals(polled,peeked);
      Assertions.assertEquals(100-i-1,seq.size());
    }
    Assertions.assertEquals(Integer.MIN_VALUE,seq.peekInt());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStackpopShort_void_seqIsNotEmpty(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(100-i-1),seq.popShort());
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedStackpollShort_void_seqIsEmpty(){
    UncheckedStack seq=new UncheckedStack();
    Assertions.assertEquals(Short.MIN_VALUE,seq.pollShort());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStackpeekShort_void_seqIsEmpty(){
    UncheckedStack seq=new UncheckedStack();
    Assertions.assertEquals(Short.MIN_VALUE,seq.peekShort());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStackpollShort_void_seqIsNotEmpty(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(100-i-1),seq.pollShort());
      Assertions.assertEquals(100-i-1,seq.size());
    }
    Assertions.assertEquals(Short.MIN_VALUE,seq.pollShort());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStackpeekShort_void_seqIsNotEmpty(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(i),seq.peekShort());
      Assertions.assertEquals(i+1,seq.size());
    }
    for(int i=0;i<100;++i)
    {
      var peeked=seq.peekShort();
      var polled=seq.pollShort();
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(100-i-1),peeked);
      Assertions.assertEquals(polled,peeked);
      Assertions.assertEquals(100-i-1,seq.size());
    }
    Assertions.assertEquals(Short.MIN_VALUE,seq.peekShort());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStackpopChar_void_seqIsNotEmpty(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(100-i-1),seq.popChar());
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedStackpollChar_void_seqIsEmpty(){
    UncheckedStack seq=new UncheckedStack();
    Assertions.assertEquals(Character.MIN_VALUE,seq.pollChar());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStackpeekChar_void_seqIsEmpty(){
    UncheckedStack seq=new UncheckedStack();
    Assertions.assertEquals(Character.MIN_VALUE,seq.peekChar());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStackpollChar_void_seqIsNotEmpty(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(100-i-1),seq.pollChar());
      Assertions.assertEquals(100-i-1,seq.size());
    }
    Assertions.assertEquals(Character.MIN_VALUE,seq.pollChar());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStackpeekChar_void_seqIsNotEmpty(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.peekChar());
      Assertions.assertEquals(i+1,seq.size());
    }
    for(int i=0;i<100;++i)
    {
      var peeked=seq.peekChar();
      var polled=seq.pollChar();
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(100-i-1),peeked);
      Assertions.assertEquals(polled,peeked);
      Assertions.assertEquals(100-i-1,seq.size());
    }
    Assertions.assertEquals(Character.MIN_VALUE,seq.peekChar());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStackpopByte_void_seqIsNotEmpty(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(100-i-1),seq.popByte());
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedStackpollByte_void_seqIsEmpty(){
    UncheckedStack seq=new UncheckedStack();
    Assertions.assertEquals(Byte.MIN_VALUE,seq.pollByte());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStackpeekByte_void_seqIsEmpty(){
    UncheckedStack seq=new UncheckedStack();
    Assertions.assertEquals(Byte.MIN_VALUE,seq.peekByte());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStackpollByte_void_seqIsNotEmpty(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(100-i-1),seq.pollByte());
      Assertions.assertEquals(100-i-1,seq.size());
    }
    Assertions.assertEquals(Byte.MIN_VALUE,seq.pollByte());
    Assertions.assertEquals(0,seq.size());
  }
  @Test
  public void testUncheckedStackpeekByte_void_seqIsNotEmpty(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(i),seq.peekByte());
      Assertions.assertEquals(i+1,seq.size());
    }
    for(int i=0;i<100;++i)
    {
      var peeked=seq.peekByte();
      var polled=seq.pollByte();
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(100-i-1),peeked);
      Assertions.assertEquals(polled,peeked);
      Assertions.assertEquals(100-i-1,seq.size());
    }
    Assertions.assertEquals(Byte.MIN_VALUE,seq.peekByte());
    Assertions.assertEquals(0,seq.size());
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
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
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
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
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
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
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
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
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
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
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
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedStackpopBoolean_void_seqIsEmpty(){
    CheckedStack seq=new CheckedStack();
    Assertions.assertThrows(NoSuchElementException.class,()->seq.popBoolean());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackpopBoolean_void_seqIsNotEmpty(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(100-i-1),seq.popBoolean());
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
  }
  @Test
  public void testCheckedStackpollBoolean_void_seqIsEmpty(){
    CheckedStack seq=new CheckedStack();
    Assertions.assertEquals(false,seq.pollBoolean());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackpeekBoolean_void_seqIsEmpty(){
    CheckedStack seq=new CheckedStack();
    Assertions.assertEquals(false,seq.peekBoolean());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackpollBoolean_void_seqIsNotEmpty(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(100-i-1),seq.pollBoolean());
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
    Assertions.assertEquals(false,seq.pollBoolean());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(200,seq.modCount);
  }
  @Test
  public void testCheckedStackpeekBoolean_void_seqIsNotEmpty(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(i),seq.peekBoolean());
      Assertions.assertEquals(i+1,seq.size());
      Assertions.assertEquals(i+1,seq.modCount);
    }
    for(int i=0;i<100;++i)
    {
      var peeked=seq.peekBoolean();
      var polled=seq.pollBoolean();
      Assertions.assertEquals(TypeConversionUtil.convertToboolean(100-i-1),peeked);
      Assertions.assertEquals(polled,peeked);
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
    Assertions.assertEquals(false,seq.peekBoolean());
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
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToBoolean(100-i-1),seq.pop());
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
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToBoolean(100-i-1),seq.poll());
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
      seq.push(TypeConversionUtil.convertToboolean(i));
      Assertions.assertEquals(TypeConversionUtil.convertToBoolean(i),seq.peek());
      Assertions.assertEquals(i+1,seq.size());
      Assertions.assertEquals(i+1,seq.modCount);
    }
    for(int i=0;i<100;++i)
    {
      var peeked=seq.peek();
      var polled=seq.poll();
      Assertions.assertEquals(TypeConversionUtil.convertToBoolean(100-i-1),peeked);
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
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTodoubleboolean(100-i-1),seq.popDouble());
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
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTodoubleboolean(100-i-1),seq.pollDouble());
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
      seq.push(TypeConversionUtil.convertToboolean(i));
      Assertions.assertEquals(TypeConversionUtil.convertTodoubleboolean(i),seq.peekDouble());
      Assertions.assertEquals(i+1,seq.size());
      Assertions.assertEquals(i+1,seq.modCount);
    }
    for(int i=0;i<100;++i)
    {
      var peeked=seq.peekDouble();
      var polled=seq.pollDouble();
      Assertions.assertEquals(TypeConversionUtil.convertTodoubleboolean(100-i-1),peeked);
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
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(100-i-1),seq.popFloat());
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
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(100-i-1),seq.pollFloat());
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
      seq.push(TypeConversionUtil.convertToboolean(i));
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(i),seq.peekFloat());
      Assertions.assertEquals(i+1,seq.size());
      Assertions.assertEquals(i+1,seq.modCount);
    }
    for(int i=0;i<100;++i)
    {
      var peeked=seq.peekFloat();
      var polled=seq.pollFloat();
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(100-i-1),peeked);
      Assertions.assertEquals(polled,peeked);
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
    Assertions.assertEquals(Float.NaN,seq.peekFloat());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(200,seq.modCount);
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
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(100-i-1),seq.popLong());
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
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(100-i-1),seq.pollLong());
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
      seq.push(TypeConversionUtil.convertToboolean(i));
      Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(i),seq.peekLong());
      Assertions.assertEquals(i+1,seq.size());
      Assertions.assertEquals(i+1,seq.modCount);
    }
    for(int i=0;i<100;++i)
    {
      var peeked=seq.peekLong();
      var polled=seq.pollLong();
      Assertions.assertEquals(TypeConversionUtil.convertTolongboolean(100-i-1),peeked);
      Assertions.assertEquals(polled,peeked);
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
    Assertions.assertEquals(Long.MIN_VALUE,seq.peekLong());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(200,seq.modCount);
  }
  @Test
  public void testCheckedStackpopInt_void_seqIsEmpty(){
    CheckedStack seq=new CheckedStack();
    Assertions.assertThrows(NoSuchElementException.class,()->seq.popInt());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackpopInt_void_seqIsNotEmpty(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(100-i-1),seq.popInt());
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
  }
  @Test
  public void testCheckedStackpollInt_void_seqIsEmpty(){
    CheckedStack seq=new CheckedStack();
    Assertions.assertEquals(Integer.MIN_VALUE,seq.pollInt());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackpeekInt_void_seqIsEmpty(){
    CheckedStack seq=new CheckedStack();
    Assertions.assertEquals(Integer.MIN_VALUE,seq.peekInt());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackpollInt_void_seqIsNotEmpty(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(100-i-1),seq.pollInt());
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
    Assertions.assertEquals(Integer.MIN_VALUE,seq.pollInt());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(200,seq.modCount);
  }
  @Test
  public void testCheckedStackpeekInt_void_seqIsNotEmpty(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(i),seq.peekInt());
      Assertions.assertEquals(i+1,seq.size());
      Assertions.assertEquals(i+1,seq.modCount);
    }
    for(int i=0;i<100;++i)
    {
      var peeked=seq.peekInt();
      var polled=seq.pollInt();
      Assertions.assertEquals(TypeConversionUtil.convertTointboolean(100-i-1),peeked);
      Assertions.assertEquals(polled,peeked);
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
    Assertions.assertEquals(Integer.MIN_VALUE,seq.peekInt());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(200,seq.modCount);
  }
  @Test
  public void testCheckedStackpopShort_void_seqIsEmpty(){
    CheckedStack seq=new CheckedStack();
    Assertions.assertThrows(NoSuchElementException.class,()->seq.popShort());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackpopShort_void_seqIsNotEmpty(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(100-i-1),seq.popShort());
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
  }
  @Test
  public void testCheckedStackpollShort_void_seqIsEmpty(){
    CheckedStack seq=new CheckedStack();
    Assertions.assertEquals(Short.MIN_VALUE,seq.pollShort());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackpeekShort_void_seqIsEmpty(){
    CheckedStack seq=new CheckedStack();
    Assertions.assertEquals(Short.MIN_VALUE,seq.peekShort());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackpollShort_void_seqIsNotEmpty(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(100-i-1),seq.pollShort());
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
    Assertions.assertEquals(Short.MIN_VALUE,seq.pollShort());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(200,seq.modCount);
  }
  @Test
  public void testCheckedStackpeekShort_void_seqIsNotEmpty(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(i),seq.peekShort());
      Assertions.assertEquals(i+1,seq.size());
      Assertions.assertEquals(i+1,seq.modCount);
    }
    for(int i=0;i<100;++i)
    {
      var peeked=seq.peekShort();
      var polled=seq.pollShort();
      Assertions.assertEquals(TypeConversionUtil.convertToshortboolean(100-i-1),peeked);
      Assertions.assertEquals(polled,peeked);
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
    Assertions.assertEquals(Short.MIN_VALUE,seq.peekShort());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(200,seq.modCount);
  }
  @Test
  public void testCheckedStackpopChar_void_seqIsEmpty(){
    CheckedStack seq=new CheckedStack();
    Assertions.assertThrows(NoSuchElementException.class,()->seq.popChar());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackpopChar_void_seqIsNotEmpty(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(100-i-1),seq.popChar());
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
  }
  @Test
  public void testCheckedStackpollChar_void_seqIsEmpty(){
    CheckedStack seq=new CheckedStack();
    Assertions.assertEquals(Character.MIN_VALUE,seq.pollChar());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackpeekChar_void_seqIsEmpty(){
    CheckedStack seq=new CheckedStack();
    Assertions.assertEquals(Character.MIN_VALUE,seq.peekChar());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackpollChar_void_seqIsNotEmpty(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(100-i-1),seq.pollChar());
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
    Assertions.assertEquals(Character.MIN_VALUE,seq.pollChar());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(200,seq.modCount);
  }
  @Test
  public void testCheckedStackpeekChar_void_seqIsNotEmpty(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.peekChar());
      Assertions.assertEquals(i+1,seq.size());
      Assertions.assertEquals(i+1,seq.modCount);
    }
    for(int i=0;i<100;++i)
    {
      var peeked=seq.peekChar();
      var polled=seq.pollChar();
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(100-i-1),peeked);
      Assertions.assertEquals(polled,peeked);
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
    Assertions.assertEquals(Character.MIN_VALUE,seq.peekChar());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(200,seq.modCount);
  }
  @Test
  public void testCheckedStackpopByte_void_seqIsEmpty(){
    CheckedStack seq=new CheckedStack();
    Assertions.assertThrows(NoSuchElementException.class,()->seq.popByte());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackpopByte_void_seqIsNotEmpty(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(100-i-1),seq.popByte());
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
  }
  @Test
  public void testCheckedStackpollByte_void_seqIsEmpty(){
    CheckedStack seq=new CheckedStack();
    Assertions.assertEquals(Byte.MIN_VALUE,seq.pollByte());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackpeekByte_void_seqIsEmpty(){
    CheckedStack seq=new CheckedStack();
    Assertions.assertEquals(Byte.MIN_VALUE,seq.peekByte());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedStackpollByte_void_seqIsNotEmpty(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(100-i-1),seq.pollByte());
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
    Assertions.assertEquals(Byte.MIN_VALUE,seq.pollByte());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(200,seq.modCount);
  }
  @Test
  public void testCheckedStackpeekByte_void_seqIsNotEmpty(){
    CheckedStack seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToboolean(i));
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(i),seq.peekByte());
      Assertions.assertEquals(i+1,seq.size());
      Assertions.assertEquals(i+1,seq.modCount);
    }
    for(int i=0;i<100;++i)
    {
      var peeked=seq.peekByte();
      var polled=seq.pollByte();
      Assertions.assertEquals(TypeConversionUtil.convertTobyteboolean(100-i-1),peeked);
      Assertions.assertEquals(polled,peeked);
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
    Assertions.assertEquals(Byte.MIN_VALUE,seq.peekByte());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(200,seq.modCount);
  }
}
