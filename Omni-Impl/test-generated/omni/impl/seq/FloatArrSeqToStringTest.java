package omni.impl.seq;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import omni.util.OmniArray;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class FloatArrSeqToStringTest
{
  private static final int MAX_TOSTRING_LENGTH=15;
  private static void testArrSeqToString(int length)
  {
    final var arr=new float[length];
    var arrayList=new ArrayList(length);
    var val=(float)0;
    var boxedVal=Float.valueOf(val);
    for(int i=0;i<length;++i)
    {
      arr[i]=val;
      arrayList.add(boxedVal);
    }
    {
      var expected=arrayList.toString();
      {
        var checkedList=new FloatArrSeq.CheckedList(length,arr);
        Assertions.assertEquals(expected,checkedList.toString());
        Assertions.assertEquals(expected,checkedList.subList(0,length).toString());
      }
      {
        var uncheckedList=new FloatArrSeq.UncheckedList(length,arr);
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
      Assertions.assertEquals(expected,new FloatArrSeq.CheckedStack(length,arr).toString());
      Assertions.assertEquals(expected,new FloatArrSeq.UncheckedStack(length,arr).toString());
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
      var root=new FloatArrSeq.CheckedList();
      Assertions.assertEquals(expected,root.toString());
      var subList=root.subList(0,0);
      Assertions.assertEquals(expected,subList.toString());
      root.add(Float.NaN);
      Assertions.assertThrows(ConcurrentModificationException.class,()->subList.toString());
    }
    {
      var root=new FloatArrSeq.UncheckedList();
      Assertions.assertEquals(expected,root.toString());
      Assertions.assertEquals(expected,root.subList(0,0).toString());
    }
    Assertions.assertEquals(expected,new FloatArrSeq.CheckedStack().toString());
    Assertions.assertEquals(expected,new FloatArrSeq.UncheckedStack().toString());
  }
  //TODO make this faster or place is switch to disable it as desired
  //@Test
  public void testLargeArrSeqToString()
  {
    testArrSeqToString((OmniArray.MAX_ARR_SIZE/(MAX_TOSTRING_LENGTH+2))+1);
  }
}
