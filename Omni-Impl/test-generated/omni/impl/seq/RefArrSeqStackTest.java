package omni.impl.seq;
import omni.impl.seq.RefArrSeq.UncheckedStack;
import omni.impl.seq.RefArrSeq.CheckedStack;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class RefArrSeqStackTest
{
  @Test
  public void testUncheckedStackpop_void_seqIsNotEmpty(){
    UncheckedStack seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      seq.push(TypeConversionUtil.convertToObject(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(100-i-1),seq.pop());
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
      seq.push(TypeConversionUtil.convertToObject(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(100-i-1),seq.poll());
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
      seq.push(TypeConversionUtil.convertToObject(i));
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.peek());
      Assertions.assertEquals(i+1,seq.size());
    }
    for(int i=0;i<100;++i)
    {
      var peeked=seq.peek();
      var polled=seq.poll();
      Assertions.assertEquals(TypeConversionUtil.convertToObject(100-i-1),peeked);
      Assertions.assertSame(polled,peeked);
      Assertions.assertEquals(100-i-1,seq.size());
    }
    Assertions.assertEquals(null,seq.peek());
    Assertions.assertEquals(0,seq.size());
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
      seq.push(TypeConversionUtil.convertToObject(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(100-i-1),seq.pop());
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
      seq.push(TypeConversionUtil.convertToObject(i));
    }
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(100-i-1),seq.poll());
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
      seq.push(TypeConversionUtil.convertToObject(i));
      Assertions.assertEquals(TypeConversionUtil.convertToObject(i),seq.peek());
      Assertions.assertEquals(i+1,seq.size());
      Assertions.assertEquals(i+1,seq.modCount);
    }
    for(int i=0;i<100;++i)
    {
      var peeked=seq.peek();
      var polled=seq.poll();
      Assertions.assertEquals(TypeConversionUtil.convertToObject(100-i-1),peeked);
      Assertions.assertSame(polled,peeked);
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
    Assertions.assertEquals(null,seq.peek());
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(200,seq.modCount);
  }
}
