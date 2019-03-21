package omni.impl.seq;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import omni.util.OmniArray;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class ByteArrSeqToStringTest
{
  private static final byte MIN_LENGTH_STRING_VAL=0;
  private static final int MIN_TOSTRING_LENGTH=String.valueOf(MIN_LENGTH_STRING_VAL).length();
  private static final int MAX_TOSTRING_LENGTH=4;
  private static void testArrSeqToString(int length)
  {
    final var arr=new byte[length];
    var arrayList=new ArrayList(length);
    var val=(byte)0;
    var boxedVal=Byte.valueOf(val);
    for(int i=0;i<length;++i)
    {
      arr[i]=val;
      arrayList.add(boxedVal);
    }
    {
      var expected=arrayList.toString();
      {
        var checkedList=new ByteArrSeq.CheckedList(length,arr);
        Assertions.assertEquals(expected,checkedList.toString());
        Assertions.assertEquals(expected,checkedList.subList(0,length).toString());
      }
      {
        var uncheckedList=new ByteArrSeq.UncheckedList(length,arr);
        Assertions.assertEquals(expected,uncheckedList.toString());
        Assertions.assertEquals(expected,uncheckedList.subList(0,length).toString());
      }
    }
    {
      for(int l=0,r=length-1;l<r;++l,--r)
      {
        arrayList.set(r,arrayList.set(l,arrayList.get(r)));
      }
      var expected=arrayList.toString();
      arrayList=null;
      Assertions.assertEquals(expected,new ByteArrSeq.CheckedStack(length,arr).toString());
      Assertions.assertEquals(expected,new ByteArrSeq.UncheckedStack(length,arr).toString());
    }
  }
  @Test
  public void testSmallArrSeqToString()
  {
    testArrSeqToString(100);
  }
  @Test
  public void testEmptyArrSeqToString()
  {
    var expected=new ArrayList().toString();
    {
      var root=new ByteArrSeq.CheckedList();
      Assertions.assertEquals(expected,root.toString());
      var subList=root.subList(0,0);
      Assertions.assertEquals(expected,subList.toString());
      root.add(Byte.MIN_VALUE);
      Assertions.assertThrows(ConcurrentModificationException.class,()->subList.toString());
    }
    {
      var root=new ByteArrSeq.UncheckedList();
      Assertions.assertEquals(expected,root.toString());
      Assertions.assertEquals(expected,root.subList(0,0).toString());
    }
    Assertions.assertEquals(expected,new ByteArrSeq.CheckedStack().toString());
    Assertions.assertEquals(expected,new ByteArrSeq.UncheckedStack().toString());
  }
  //@Test
  public void testOOMArrSeqToString()
  {
    int length=Integer.MAX_VALUE/(MIN_TOSTRING_LENGTH+2)-1;
    final byte[] arr=new byte[length+1];
    {
      var root=new ByteArrSeq.CheckedList(length,arr);
      Assertions.assertDoesNotThrow(()->root.toString());
      ++root.size;
      Assertions.assertThrows(OutOfMemoryError.class,()->root.toString());
      --root.size;
      var subList=root.subList(0,root.size);
      Assertions.assertDoesNotThrow(()->subList.toString());
      subList.add(MIN_LENGTH_STRING_VAL);
      Assertions.assertThrows(OutOfMemoryError.class,()->subList.toString());
    }
    {
      var root=new ByteArrSeq.UncheckedList(length,arr);
      Assertions.assertDoesNotThrow(()->root.toString());
      ++root.size;
      Assertions.assertThrows(OutOfMemoryError.class,()->root.toString());
      --root.size;
      var subList=root.subList(0,root.size);
      Assertions.assertDoesNotThrow(()->subList.toString());
      subList.add(MIN_LENGTH_STRING_VAL);
      Assertions.assertThrows(OutOfMemoryError.class,()->subList.toString());
    }
    {
      var root=new ByteArrSeq.CheckedStack(length,arr);
      Assertions.assertDoesNotThrow(()->root.toString());
      ++root.size;
      Assertions.assertThrows(OutOfMemoryError.class,()->root.toString());
    }
    {
      var root=new ByteArrSeq.UncheckedStack(length,arr);
      Assertions.assertDoesNotThrow(()->root.toString());
      ++root.size;
      Assertions.assertThrows(OutOfMemoryError.class,()->root.toString());
    }
  }
  //TODO make this faster or place is switch to disable it as desired
  //@Test
  public void testLargeArrSeqToString()
  {
    testArrSeqToString((OmniArray.MAX_ARR_SIZE/(MAX_TOSTRING_LENGTH+2))+1);
  }
}
