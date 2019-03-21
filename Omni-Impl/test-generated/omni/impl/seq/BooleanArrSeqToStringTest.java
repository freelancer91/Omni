package omni.impl.seq;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import omni.util.OmniArray;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class BooleanArrSeqToStringTest
{
  private static final boolean MIN_LENGTH_STRING_VAL=true;
  private static final int MIN_TOSTRING_LENGTH=String.valueOf(MIN_LENGTH_STRING_VAL).length();
  private static final int MAX_TOSTRING_LENGTH=5;
  private static void testArrSeqToString(int length)
  {
    final var arr=new boolean[length];
    var arrayList=new ArrayList(length);
    var val=true;
    var boxedVal=Boolean.TRUE;
    for(int i=0;i<length;++i)
    {
      arr[i]=val;
      arrayList.add(boxedVal);
    }
    {
      var expected=arrayList.toString();
      {
        var checkedList=new BooleanArrSeq.CheckedList(length,arr);
        Assertions.assertEquals(expected,checkedList.toString());
        Assertions.assertEquals(expected,checkedList.subList(0,length).toString());
      }
      {
        var uncheckedList=new BooleanArrSeq.UncheckedList(length,arr);
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
      Assertions.assertEquals(expected,new BooleanArrSeq.CheckedStack(length,arr).toString());
      Assertions.assertEquals(expected,new BooleanArrSeq.UncheckedStack(length,arr).toString());
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
      var root=new BooleanArrSeq.CheckedList();
      Assertions.assertEquals(expected,root.toString());
      var subList=root.subList(0,0);
      Assertions.assertEquals(expected,subList.toString());
      root.add(false);
      Assertions.assertThrows(ConcurrentModificationException.class,()->subList.toString());
    }
    {
      var root=new BooleanArrSeq.UncheckedList();
      Assertions.assertEquals(expected,root.toString());
      Assertions.assertEquals(expected,root.subList(0,0).toString());
    }
    Assertions.assertEquals(expected,new BooleanArrSeq.CheckedStack().toString());
    Assertions.assertEquals(expected,new BooleanArrSeq.UncheckedStack().toString());
  }
  //@Test
  public void testOOMArrSeqToString()
  {
    int length=Integer.MAX_VALUE/(MIN_TOSTRING_LENGTH+2)-1;
    final boolean[] arr=new boolean[length+1];
    for(int i=0;i<length;++i)
    {
      arr[i]=MIN_LENGTH_STRING_VAL;
    }
    {
      var root=new BooleanArrSeq.CheckedList(length,arr);
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
      var root=new BooleanArrSeq.UncheckedList(length,arr);
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
      var root=new BooleanArrSeq.CheckedStack(length,arr);
      Assertions.assertDoesNotThrow(()->root.toString());
      ++root.size;
      Assertions.assertThrows(OutOfMemoryError.class,()->root.toString());
    }
    {
      var root=new BooleanArrSeq.UncheckedStack(length,arr);
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
