package omni.impl.seq;
import omni.util.EqualityUtil;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class DoubleSeqToStringTest
{
  private static final double MIN_LENGTH_STRING_VAL=0;
  private static final int MIN_TOSTRING_LENGTH=String.valueOf(MIN_LENGTH_STRING_VAL).length();
  private static void testArrSeqToString(int length)
  {
    final var arr=new double[length];
    var arrayList=new ArrayList(length);
    for(int i=0;i<length;++i)
    {
      var val=TypeConversionUtil.convertTodouble(i);
      arr[i]=val;
      arrayList.add(val);
    }
    {
      var expected=arrayList.toString();
      {
        var checkedList=new DoubleArrSeq.CheckedList(length,arr);
        EqualityUtil.parallelAssertStringsAreEqual(expected,checkedList.toString());
        EqualityUtil.parallelAssertStringsAreEqual(expected,checkedList.subList(0,length).toString());
      }
      {
        var uncheckedList=new DoubleArrSeq.UncheckedList(length,arr);
        EqualityUtil.parallelAssertStringsAreEqual(expected,uncheckedList.toString());
        EqualityUtil.parallelAssertStringsAreEqual(expected,uncheckedList.subList(0,length).toString());
      }
    }
    {
      for(int l=0,r=length-1;l<r;++l,--r)
      {
        arrayList.set(r,arrayList.set(l,arrayList.get(r)));
      }
      var expected=arrayList.toString();
      arrayList=null;
      EqualityUtil.parallelAssertStringsAreEqual(expected,new DoubleArrSeq.CheckedStack(length,arr).toString());
      EqualityUtil.parallelAssertStringsAreEqual(expected,new DoubleArrSeq.UncheckedStack(length,arr).toString());
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
      var root=new DoubleArrSeq.CheckedList();
      Assertions.assertEquals(expected,root.toString());
      var subList=root.subList(0,0);
      Assertions.assertEquals(expected,subList.toString());
      root.add(Double.NaN);
      Assertions.assertThrows(ConcurrentModificationException.class,()->subList.toString());
    }
    {
      var root=new DoubleArrSeq.UncheckedList();
      Assertions.assertEquals(expected,root.toString());
      Assertions.assertEquals(expected,root.subList(0,0).toString());
    }
    Assertions.assertEquals(expected,new DoubleArrSeq.CheckedStack().toString());
    Assertions.assertEquals(expected,new DoubleArrSeq.UncheckedStack().toString());
  }
  //@Test
  public void testOOMArrSeqToString()
  {
    int length=Integer.MAX_VALUE/(MIN_TOSTRING_LENGTH+2)-1;
    final double[] arr=new double[length+1];
    {
      var root=new DoubleArrSeq.CheckedList(length,arr);
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
      var root=new DoubleArrSeq.UncheckedList(length,arr);
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
      var root=new DoubleArrSeq.CheckedStack(length,arr);
      Assertions.assertDoesNotThrow(()->root.toString());
      ++root.size;
      Assertions.assertThrows(OutOfMemoryError.class,()->root.toString());
    }
    {
      var root=new DoubleArrSeq.UncheckedStack(length,arr);
      Assertions.assertDoesNotThrow(()->root.toString());
      ++root.size;
      Assertions.assertThrows(OutOfMemoryError.class,()->root.toString());
    }
  }
}
