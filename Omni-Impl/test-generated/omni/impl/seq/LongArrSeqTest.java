package omni.impl.seq;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.util.TypeConversionUtil;
import omni.util.EqualityUtil;
import omni.util.OmniArray;
import omni.impl.seq.LongArrSeq.UncheckedList;
import omni.impl.seq.LongArrSeq.CheckedList;
import omni.impl.seq.LongArrSeq.UncheckedStack;
import omni.impl.seq.LongArrSeq.CheckedStack;
import java.util.ConcurrentModificationException;
public class LongArrSeqTest
{
  @Test
  public void testCloneUncheckedStack()
  {
    var seq=new UncheckedStack();
    Object clonedObject=seq.clone();
    Assertions.assertTrue(clonedObject instanceof UncheckedStack);
    var clonedSeq=(UncheckedStack)clonedObject;
    Assertions.assertTrue(clonedSeq.arr==seq.arr);
    Assertions.assertEquals(clonedSeq.size(),seq.size());
    Assertions.assertTrue(seq!=clonedSeq);
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTolong(i);
      seq.push(val);
    }
    Assertions.assertEquals(seq.size(),100);
    clonedObject=seq.clone();
    Assertions.assertTrue(clonedObject instanceof UncheckedStack);
    clonedSeq=(UncheckedStack)clonedObject;
    Assertions.assertTrue(clonedSeq.arr!=seq.arr);
    Assertions.assertEquals(seq.size(),clonedSeq.size());
    EqualityUtil.uncheckedparallelassertarraysAreEqual(seq.arr,0,clonedSeq.arr,0,seq.size());
  }
  @Test
  public void testConstructorsUncheckedStack()
  {
    var seq=new UncheckedStack();
    Assertions.assertEquals(seq.size(),0);
    Assertions.assertTrue(seq.isEmpty());
    Assertions.assertTrue(seq.arr==OmniArray.OfLong.DEFAULT_ARR);
    seq=new UncheckedStack(0);
    Assertions.assertEquals(seq.size(),0);
    Assertions.assertTrue(seq.isEmpty());
    Assertions.assertTrue(seq.arr==null);
    seq=new UncheckedStack(OmniArray.DEFAULT_ARR_SEQ_CAP);
    Assertions.assertEquals(seq.size(),0);
    Assertions.assertTrue(seq.isEmpty());
    Assertions.assertTrue(seq.arr==OmniArray.OfLong.DEFAULT_ARR);
    for(int i=1;i<OmniArray.DEFAULT_ARR_SEQ_CAP;++i)
    {
      seq=new UncheckedStack(i);
      Assertions.assertEquals(seq.size(),0);
      Assertions.assertTrue(seq.isEmpty());
      Assertions.assertEquals(seq.arr.length,i);
    }
  }
    @Test
    public void testListAddLongUncheckedList()
    {
      var seq=new UncheckedList();
      for(int i=100;--i>=0;)
      {
        //test add at beginning
        seq.add(0,(Long)TypeConversionUtil.convertTolong(i));
      }
      for(int i=0;i<100;++i)
      {
        Assertions.assertEquals((TypeConversionUtil.convertTolong(i)),seq.getLong(i));
      }
      int currSize=seq.size();
      Assertions.assertEquals(100,currSize);
      for(int i=200;i<300;++i)
      {
        //add at end
        seq.add(seq.size(),(Long)TypeConversionUtil.convertTolong(i));
      }
      for(int i=100;i<200;++i)
      {
        Assertions.assertEquals((TypeConversionUtil.convertTolong(i+100)),seq.getLong(i));
      }
      currSize=seq.size();
      Assertions.assertEquals(200,currSize);
      for(int i=200;--i>=100;)
      {
        //add in middle
        seq.add(100,(Long)TypeConversionUtil.convertTolong(i));
      }
      for(int i=100;i<200;++i)
      {
        Assertions.assertEquals((TypeConversionUtil.convertTolong(i)),seq.getLong(i));
      }
      currSize=seq.size();
      Assertions.assertEquals(300,currSize);
    }
  @Test
  public void testCloneUncheckedList()
  {
    var seq=new UncheckedList();
    Object clonedObject=seq.clone();
    Assertions.assertTrue(clonedObject instanceof UncheckedList);
    var clonedSeq=(UncheckedList)clonedObject;
    Assertions.assertTrue(clonedSeq.arr==seq.arr);
    Assertions.assertEquals(clonedSeq.size(),seq.size());
    Assertions.assertTrue(seq!=clonedSeq);
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTolong(i);
      seq.push(val);
    }
    Assertions.assertEquals(seq.size(),100);
    clonedObject=seq.clone();
    Assertions.assertTrue(clonedObject instanceof UncheckedList);
    clonedSeq=(UncheckedList)clonedObject;
    Assertions.assertTrue(clonedSeq.arr!=seq.arr);
    Assertions.assertEquals(seq.size(),clonedSeq.size());
    EqualityUtil.uncheckedparallelassertarraysAreEqual(seq.arr,0,clonedSeq.arr,0,seq.size());
  }
  @Test
  public void testConstructorsUncheckedList()
  {
    var seq=new UncheckedList();
    Assertions.assertEquals(seq.size(),0);
    Assertions.assertTrue(seq.isEmpty());
    Assertions.assertTrue(seq.arr==OmniArray.OfLong.DEFAULT_ARR);
    seq=new UncheckedList(0);
    Assertions.assertEquals(seq.size(),0);
    Assertions.assertTrue(seq.isEmpty());
    Assertions.assertTrue(seq.arr==null);
    seq=new UncheckedList(OmniArray.DEFAULT_ARR_SEQ_CAP);
    Assertions.assertEquals(seq.size(),0);
    Assertions.assertTrue(seq.isEmpty());
    Assertions.assertTrue(seq.arr==OmniArray.OfLong.DEFAULT_ARR);
    for(int i=1;i<OmniArray.DEFAULT_ARR_SEQ_CAP;++i)
    {
      seq=new UncheckedList(i);
      Assertions.assertEquals(seq.size(),0);
      Assertions.assertTrue(seq.isEmpty());
      Assertions.assertEquals(seq.arr.length,i);
    }
  }
  @Test
  public void testCloneCheckedStack()
  {
    var seq=new CheckedStack();
    Object clonedObject=seq.clone();
    Assertions.assertTrue(clonedObject instanceof CheckedStack);
    var clonedSeq=(CheckedStack)clonedObject;
    Assertions.assertTrue(clonedSeq.arr==seq.arr);
    Assertions.assertEquals(clonedSeq.size(),seq.size());
    Assertions.assertTrue(seq!=clonedSeq);
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTolong(i);
      seq.push(val);
    }
    Assertions.assertEquals(seq.size(),100);
    clonedObject=seq.clone();
    Assertions.assertTrue(clonedObject instanceof CheckedStack);
    clonedSeq=(CheckedStack)clonedObject;
    Assertions.assertTrue(clonedSeq.arr!=seq.arr);
    Assertions.assertEquals(seq.size(),clonedSeq.size());
    EqualityUtil.uncheckedparallelassertarraysAreEqual(seq.arr,0,clonedSeq.arr,0,seq.size());
  }
  @Test
  public void testConstructorsCheckedStack()
  {
    var seq=new CheckedStack();
    Assertions.assertEquals(seq.size(),0);
    Assertions.assertTrue(seq.isEmpty());
    Assertions.assertTrue(seq.arr==OmniArray.OfLong.DEFAULT_ARR);
    seq=new CheckedStack(0);
    Assertions.assertEquals(seq.size(),0);
    Assertions.assertTrue(seq.isEmpty());
    Assertions.assertTrue(seq.arr==null);
    seq=new CheckedStack(OmniArray.DEFAULT_ARR_SEQ_CAP);
    Assertions.assertEquals(seq.size(),0);
    Assertions.assertTrue(seq.isEmpty());
    Assertions.assertTrue(seq.arr==OmniArray.OfLong.DEFAULT_ARR);
    for(int i=1;i<OmniArray.DEFAULT_ARR_SEQ_CAP;++i)
    {
      seq=new CheckedStack(i);
      Assertions.assertEquals(seq.size(),0);
      Assertions.assertTrue(seq.isEmpty());
      Assertions.assertEquals(seq.arr.length,i);
    }
  }
    @Test
    public void testListAddLongCheckedList()
    {
      var seq=new CheckedList();
      for(int i=100;--i>=0;)
      {
        //test add at beginning
        seq.add(0,(Long)TypeConversionUtil.convertTolong(i));
      }
      for(int i=0;i<100;++i)
      {
        Assertions.assertEquals((TypeConversionUtil.convertTolong(i)),seq.getLong(i));
      }
      int currSize=seq.size();
      Assertions.assertEquals(100,currSize);
      int modCount=seq.modCount;
      Assertions.assertEquals(modCount,100);
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTolong(0)));
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size()+1,TypeConversionUtil.convertTolong(0)));
      Assertions.assertEquals(modCount,seq.modCount);
      Assertions.assertEquals(currSize,seq.size());
      for(int i=200;i<300;++i)
      {
        //add at end
        seq.add(seq.size(),(Long)TypeConversionUtil.convertTolong(i));
      }
      for(int i=100;i<200;++i)
      {
        Assertions.assertEquals((TypeConversionUtil.convertTolong(i+100)),seq.getLong(i));
      }
      currSize=seq.size();
      Assertions.assertEquals(200,currSize);
      modCount=seq.modCount;
      Assertions.assertEquals(modCount,200);
      for(int i=200;--i>=100;)
      {
        //add in middle
        seq.add(100,(Long)TypeConversionUtil.convertTolong(i));
      }
      for(int i=100;i<200;++i)
      {
        Assertions.assertEquals((TypeConversionUtil.convertTolong(i)),seq.getLong(i));
      }
      currSize=seq.size();
      Assertions.assertEquals(300,currSize);
      modCount=seq.modCount;
      Assertions.assertEquals(modCount,300);
    }
  @Test
  public void testCloneCheckedList()
  {
    var seq=new CheckedList();
    Object clonedObject=seq.clone();
    Assertions.assertTrue(clonedObject instanceof CheckedList);
    var clonedSeq=(CheckedList)clonedObject;
    Assertions.assertTrue(clonedSeq.arr==seq.arr);
    Assertions.assertEquals(clonedSeq.size(),seq.size());
    Assertions.assertTrue(seq!=clonedSeq);
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTolong(i);
      seq.push(val);
    }
    Assertions.assertEquals(seq.size(),100);
    clonedObject=seq.clone();
    Assertions.assertTrue(clonedObject instanceof CheckedList);
    clonedSeq=(CheckedList)clonedObject;
    Assertions.assertTrue(clonedSeq.arr!=seq.arr);
    Assertions.assertEquals(seq.size(),clonedSeq.size());
    EqualityUtil.uncheckedparallelassertarraysAreEqual(seq.arr,0,clonedSeq.arr,0,seq.size());
  }
  @Test
  public void testConstructorsCheckedList()
  {
    var seq=new CheckedList();
    Assertions.assertEquals(seq.size(),0);
    Assertions.assertTrue(seq.isEmpty());
    Assertions.assertTrue(seq.arr==OmniArray.OfLong.DEFAULT_ARR);
    seq=new CheckedList(0);
    Assertions.assertEquals(seq.size(),0);
    Assertions.assertTrue(seq.isEmpty());
    Assertions.assertTrue(seq.arr==null);
    seq=new CheckedList(OmniArray.DEFAULT_ARR_SEQ_CAP);
    Assertions.assertEquals(seq.size(),0);
    Assertions.assertTrue(seq.isEmpty());
    Assertions.assertTrue(seq.arr==OmniArray.OfLong.DEFAULT_ARR);
    for(int i=1;i<OmniArray.DEFAULT_ARR_SEQ_CAP;++i)
    {
      seq=new CheckedList(i);
      Assertions.assertEquals(seq.size(),0);
      Assertions.assertTrue(seq.isEmpty());
      Assertions.assertEquals(seq.arr.length,i);
    }
  }
    @Test
    public void testListAddLongUncheckedSubList()
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      for(int i=100;--i>=0;)
      {
        //test add at beginning
        seq.add(0,(Long)TypeConversionUtil.convertTolong(i));
      }
      for(int i=0;i<100;++i)
      {
        Assertions.assertEquals((TypeConversionUtil.convertTolong(i)),seq.getLong(i));
      }
      int currSize=seq.size();
      Assertions.assertEquals(subList.size(),currSize);
      Assertions.assertEquals(root.size(),currSize);
      Assertions.assertEquals(100,currSize);
      for(int i=200;i<300;++i)
      {
        //add at end
        seq.add(seq.size(),(Long)TypeConversionUtil.convertTolong(i));
      }
      for(int i=100;i<200;++i)
      {
        Assertions.assertEquals((TypeConversionUtil.convertTolong(i+100)),seq.getLong(i));
      }
      currSize=seq.size();
      Assertions.assertEquals(subList.size(),currSize);
      Assertions.assertEquals(root.size(),currSize);
      Assertions.assertEquals(200,currSize);
      for(int i=200;--i>=100;)
      {
        //add in middle
        seq.add(100,(Long)TypeConversionUtil.convertTolong(i));
      }
      for(int i=100;i<200;++i)
      {
        Assertions.assertEquals((TypeConversionUtil.convertTolong(i)),seq.getLong(i));
      }
      currSize=seq.size();
      Assertions.assertEquals(subList.size(),currSize);
      Assertions.assertEquals(root.size(),currSize);
      Assertions.assertEquals(300,currSize);
    }
  @Test
  public void testCloneUncheckedSubList()
  {
    var seq=new UncheckedList();
    {
      var subList=seq.subList(0,0);
      var clonedObject=subList.clone();
      Assertions.assertTrue(clonedObject instanceof UncheckedList);
      var clonedSubSeq=(UncheckedList)clonedObject;
      Assertions.assertTrue(clonedSubSeq.arr==seq.arr);
      Assertions.assertEquals(clonedSubSeq.size(),subList.size());
      Assertions.assertTrue(subList!=clonedSubSeq);
    }
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTolong(i);
      seq.push(val);
    }
    {
      var subList=seq.subList(25,75);
      Assertions.assertEquals(subList.size(),75-25);
      var clonedObject=subList.clone();
      Assertions.assertTrue(clonedObject instanceof UncheckedList);
      var clonedSeq=(UncheckedList)clonedObject;
      Assertions.assertTrue(clonedSeq.arr!=seq.arr);
      Assertions.assertEquals(subList.size(),clonedSeq.size());
      EqualityUtil.uncheckedparallelassertarraysAreEqual(seq.arr,25,clonedSeq.arr,0,subList.size());
    }
  }
  @Test
  public void testConstructorsUncheckedSubList()
  {
    var root=new UncheckedList();
    {
      var subList=root.subList(0,0);
      Assertions.assertEquals(subList.size(),0);
      Assertions.assertTrue(subList.isEmpty());
      var subsubList=subList.subList(0,0);
      Assertions.assertEquals(subsubList.size(),0);
      Assertions.assertTrue(subsubList.isEmpty());
    }
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTolong(i);
      root.add(val);
    }
    {
      var subList=root.subList(25,75);
      Assertions.assertEquals(subList.size(),75-25);
      for(int i=25;i<75;++i)
      {
        var val=TypeConversionUtil.convertTolong(i);
        Assertions.assertEquals(val,subList.getLong(i-25));
      }
      var subsubList=subList.subList(10,30);
      Assertions.assertEquals(subsubList.size(),30-10);
      Assertions.assertFalse(subsubList.isEmpty());
      for(int i=10;i<30;++i)
      {
        var val=TypeConversionUtil.convertTolong(i+25);
        Assertions.assertEquals(val,subsubList.getLong(i-10));
      }
    }
  }
    @Test
    public void testListAddLongCheckedSubList()
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      for(int i=100;--i>=0;)
      {
        //test add at beginning
        seq.add(0,(Long)TypeConversionUtil.convertTolong(i));
      }
      for(int i=0;i<100;++i)
      {
        Assertions.assertEquals((TypeConversionUtil.convertTolong(i)),seq.getLong(i));
      }
      int currSize=seq.size();
      Assertions.assertEquals(subList.size(),currSize);
      Assertions.assertEquals(root.size(),currSize);
      Assertions.assertEquals(100,currSize);
      int modCount=root.modCount;
      Assertions.assertEquals(modCount,100);
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTolong(0)));
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size()+1,TypeConversionUtil.convertTolong(0)));
      Assertions.assertEquals(modCount,root.modCount);
      Assertions.assertEquals(currSize,seq.size());
      for(int i=200;i<300;++i)
      {
        //add at end
        seq.add(seq.size(),(Long)TypeConversionUtil.convertTolong(i));
      }
      for(int i=100;i<200;++i)
      {
        Assertions.assertEquals((TypeConversionUtil.convertTolong(i+100)),seq.getLong(i));
      }
      currSize=seq.size();
      Assertions.assertEquals(subList.size(),currSize);
      Assertions.assertEquals(root.size(),currSize);
      Assertions.assertEquals(200,currSize);
      modCount=root.modCount;
      Assertions.assertEquals(modCount,200);
      for(int i=200;--i>=100;)
      {
        //add in middle
        seq.add(100,(Long)TypeConversionUtil.convertTolong(i));
      }
      for(int i=100;i<200;++i)
      {
        Assertions.assertEquals((TypeConversionUtil.convertTolong(i)),seq.getLong(i));
      }
      currSize=seq.size();
      Assertions.assertEquals(subList.size(),currSize);
      Assertions.assertEquals(root.size(),currSize);
      Assertions.assertEquals(300,currSize);
      modCount=root.modCount;
      Assertions.assertEquals(modCount,300);
    }
  @Test
  public void testCloneCheckedSubList()
  {
    var seq=new CheckedList();
    {
      var subList=seq.subList(0,0);
      var clonedObject=subList.clone();
      Assertions.assertTrue(clonedObject instanceof CheckedList);
      var clonedSubSeq=(CheckedList)clonedObject;
      Assertions.assertTrue(clonedSubSeq.arr==seq.arr);
      Assertions.assertEquals(clonedSubSeq.size(),subList.size());
      Assertions.assertTrue(subList!=clonedSubSeq);
    }
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTolong(i);
      seq.push(val);
    }
    {
      var subList=seq.subList(25,75);
      Assertions.assertEquals(subList.size(),75-25);
      var clonedObject=subList.clone();
      Assertions.assertTrue(clonedObject instanceof CheckedList);
      var clonedSeq=(CheckedList)clonedObject;
      Assertions.assertTrue(clonedSeq.arr!=seq.arr);
      Assertions.assertEquals(subList.size(),clonedSeq.size());
      EqualityUtil.uncheckedparallelassertarraysAreEqual(seq.arr,25,clonedSeq.arr,0,subList.size());
      seq.add(Long.MIN_VALUE);
      Assertions.assertThrows(ConcurrentModificationException.class,()->subList.clone());
    }
  }
  @Test
  public void testConstructorsCheckedSubList()
  {
    var root=new CheckedList();
    {
      var subList=root.subList(0,0);
      Assertions.assertEquals(subList.size(),0);
      Assertions.assertTrue(subList.isEmpty());
      var subsubList=subList.subList(0,0);
      Assertions.assertEquals(subsubList.size(),0);
      Assertions.assertTrue(subsubList.isEmpty());
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->subList.subList(-1,0));
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->subList.subList(0,1));
    }
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTolong(i);
      root.add(val);
    }
    {
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->root.subList(-1,75));
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->root.subList(25,101));
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->root.subList(75,25));
      var subList=root.subList(25,75);
      Assertions.assertEquals(subList.size(),75-25);
      Assertions.assertFalse(subList.isEmpty());
      for(int i=25;i<75;++i)
      {
        var val=TypeConversionUtil.convertTolong(i);
        Assertions.assertEquals(val,subList.getLong(i-25));
      }
      {
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->subList.subList(-1,30));
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->subList.subList(0,51));
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->subList.subList(30,10));
        var subsubList=subList.subList(10,30);
        Assertions.assertEquals(subsubList.size(),30-10);
        Assertions.assertFalse(subsubList.isEmpty());
        for(int i=10;i<30;++i)
        {
          var val=TypeConversionUtil.convertTolong(i+25);
          Assertions.assertEquals(val,subsubList.getLong(i-10));
        }
      }
      root.add(Long.MIN_VALUE);
      Assertions.assertThrows(ConcurrentModificationException.class,()->subList.subList(10,30));
    }
  }
}
