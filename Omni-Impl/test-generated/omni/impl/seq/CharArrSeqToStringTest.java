package omni.impl.seq;
import omni.util.EqualityUtil;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class CharArrSeqToStringTest
{
  private static final char MIN_LENGTH_STRING_VAL=0;
  private static final int MIN_TOSTRING_LENGTH=String.valueOf(MIN_LENGTH_STRING_VAL).length();
  private static void testArrSeqToString(int length)
  {
    final var arr=new char[length];
    var arrayList=new ArrayList(length);
    var val=(char)0;
    var boxedVal=Character.valueOf(val);
    for(int i=0;i<length;++i)
    {
      arr[i]=val;
      arrayList.add(boxedVal);
    }
    {
      var expected=arrayList.toString();
      {
        var checkedList=new CharArrSeq.CheckedList(length,arr);
        EqualityUtil.parallelAssertStringsAreEqual(expected,checkedList.toString());
        EqualityUtil.parallelAssertStringsAreEqual(expected,checkedList.subList(0,length).toString());
      }
      {
        var uncheckedList=new CharArrSeq.UncheckedList(length,arr);
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
      EqualityUtil.parallelAssertStringsAreEqual(expected,new CharArrSeq.CheckedStack(length,arr).toString());
      EqualityUtil.parallelAssertStringsAreEqual(expected,new CharArrSeq.UncheckedStack(length,arr).toString());
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
      var root=new CharArrSeq.CheckedList();
      Assertions.assertEquals(expected,root.toString());
      var subList=root.subList(0,0);
      Assertions.assertEquals(expected,subList.toString());
      root.add(Character.MIN_VALUE);
      Assertions.assertThrows(ConcurrentModificationException.class,()->subList.toString());
    }
    {
      var root=new CharArrSeq.UncheckedList();
      Assertions.assertEquals(expected,root.toString());
      Assertions.assertEquals(expected,root.subList(0,0).toString());
    }
    Assertions.assertEquals(expected,new CharArrSeq.CheckedStack().toString());
    Assertions.assertEquals(expected,new CharArrSeq.UncheckedStack().toString());
  }
  //@Test
  public void testOOMArrSeqToString()
  {
    int length=Integer.MAX_VALUE/(MIN_TOSTRING_LENGTH+2)-1;
    final char[] arr=new char[length+1];
    {
      var root=new CharArrSeq.CheckedList(length,arr);
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
      var root=new CharArrSeq.UncheckedList(length,arr);
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
      var root=new CharArrSeq.CheckedStack(length,arr);
      Assertions.assertDoesNotThrow(()->root.toString());
      ++root.size;
      Assertions.assertThrows(OutOfMemoryError.class,()->root.toString());
    }
    {
      var root=new CharArrSeq.UncheckedStack(length,arr);
      Assertions.assertDoesNotThrow(()->root.toString());
      ++root.size;
      Assertions.assertThrows(OutOfMemoryError.class,()->root.toString());
    }
  }
}
