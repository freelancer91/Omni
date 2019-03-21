package omni.impl.seq;
import omni.util.EqualityUtil;
import omni.util.TypeConversionUtil;
import omni.impl.CheckedCollectionTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class RefArrSeqToStringTest
{
  private static final Object MIN_LENGTH_STRING_VAL=new Object(){@Override public String toString(){return "";}};
  private static final int MIN_TOSTRING_LENGTH=String.valueOf(MIN_LENGTH_STRING_VAL).length();
  private static void testArrSeqToString(int length)
  {
    final var arr=new Object[length];
    var arrayList=new ArrayList(length);
    var val=Integer.valueOf(0);
    var boxedVal=val;
    for(int i=0;i<length;++i)
    {
      arr[i]=val;
      arrayList.add(boxedVal);
    }
    {
      var expected=arrayList.toString();
      {
        var checkedList=new RefArrSeq.CheckedList(length,arr);
        EqualityUtil.parallelAssertStringsAreEqual(expected,checkedList.toString());
        EqualityUtil.parallelAssertStringsAreEqual(expected,checkedList.subList(0,length).toString());
      }
      {
        var uncheckedList=new RefArrSeq.UncheckedList(length,arr);
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
      EqualityUtil.parallelAssertStringsAreEqual(expected,new RefArrSeq.CheckedStack(length,arr).toString());
      EqualityUtil.parallelAssertStringsAreEqual(expected,new RefArrSeq.UncheckedStack(length,arr).toString());
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
      var root=new RefArrSeq.CheckedList();
      Assertions.assertEquals(expected,root.toString());
      var subList=root.subList(0,0);
      Assertions.assertEquals(expected,subList.toString());
      root.add(null);
      Assertions.assertThrows(ConcurrentModificationException.class,()->subList.toString());
    }
    {
      var root=new RefArrSeq.UncheckedList();
      Assertions.assertEquals(expected,root.toString());
      Assertions.assertEquals(expected,root.subList(0,0).toString());
    }
    Assertions.assertEquals(expected,new RefArrSeq.CheckedStack().toString());
    Assertions.assertEquals(expected,new RefArrSeq.UncheckedStack().toString());
  }
  //@Test
  public void testOOMArrSeqToString()
  {
    int length=Integer.MAX_VALUE/(MIN_TOSTRING_LENGTH+2)-1;
    final Object[] arr=new Object[length+1];
    for(int i=0;i<length;++i)
    {
      arr[i]=MIN_LENGTH_STRING_VAL;
    }
    {
      var root=new RefArrSeq.CheckedList(length,arr);
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
      var root=new RefArrSeq.UncheckedList(length,arr);
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
      var root=new RefArrSeq.CheckedStack(length,arr);
      Assertions.assertDoesNotThrow(()->root.toString());
      ++root.size;
      Assertions.assertThrows(OutOfMemoryError.class,()->root.toString());
    }
    {
      var root=new RefArrSeq.UncheckedStack(length,arr);
      Assertions.assertDoesNotThrow(()->root.toString());
      ++root.size;
      Assertions.assertThrows(OutOfMemoryError.class,()->root.toString());
    }
  }
  @Test
  public void testArrSeqModificationCheck()
  {
    {
      var root=new RefArrSeq.CheckedList();
      var subList=root.subList(0,0);
      var rootModifyingObject=CheckedCollectionTest.createCollectionModifyingObject(root);
      for(int i=0;i<10;++i)
      {
        subList.add(rootModifyingObject);
      }
      Assertions.assertThrows(ConcurrentModificationException.class,()->subList.toString());
    }
    {
      var root=new RefArrSeq.CheckedList();
      var subList=root.subList(0,0);
      var subListModifyingObject=CheckedCollectionTest.createCollectionModifyingObject(subList);
      for(int i=0;i<10;++i)
      {
        subList.add(subListModifyingObject);
      }
      Assertions.assertThrows(ConcurrentModificationException.class,()->subList.toString());
    }
    {
      var seq=new RefArrSeq.CheckedList();
      var seqModifyingObject=CheckedCollectionTest.createCollectionModifyingObject(seq);
      for(int i=0;i<10;++i)
      {
        seq.add(seqModifyingObject);
      }
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toString());
    }
    {
      var seq=new RefArrSeq.CheckedStack();
      var seqModifyingObject=CheckedCollectionTest.createCollectionModifyingObject(seq);
      for(int i=0;i<10;++i)
      {
        seq.add(seqModifyingObject);
      }
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toString());
    }
  }
}
