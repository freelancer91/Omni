package omni.impl.seq;
import omni.impl.CheckedCollectionTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class RefArrSeqToStringTest
{
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
        Assertions.assertEquals(expected,checkedList.toString());
        Assertions.assertEquals(expected,checkedList.subList(0,length).toString());
      }
      {
        var uncheckedList=new RefArrSeq.UncheckedList(length,arr);
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
      Assertions.assertEquals(expected,new RefArrSeq.CheckedStack(length,arr).toString());
      Assertions.assertEquals(expected,new RefArrSeq.UncheckedStack(length,arr).toString());
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