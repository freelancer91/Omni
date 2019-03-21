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
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
public class LongArrSeqTest
{
//TODO place sanity checks for checked sequence modification behavior
  @Test
  public void testToArrayUncheckedStack()
  {
    var seq=new UncheckedStack();
    var longArr=seq.toLongArray();
    Assertions.assertTrue(longArr==OmniArray.OfLong.DEFAULT_ARR);
    var LongArr=seq.toArray();
    Assertions.assertTrue(LongArr==OmniArray.OfLong.DEFAULT_BOXED_ARR);
    var doubleArr=seq.toDoubleArray();
    Assertions.assertTrue(doubleArr==OmniArray.OfDouble.DEFAULT_ARR);
    var floatArr=seq.toFloatArray();
    Assertions.assertTrue(floatArr==OmniArray.OfFloat.DEFAULT_ARR);
    for(int i=0;i<10;++i)
    {
      seq.add(TypeConversionUtil.convertTolong(i));
    }
    longArr=seq.toLongArray();
    Assertions.assertEquals(longArr.length,seq.size());
    var seqItr=seq.iterator();
    for(int i=0;i<seq.size();++i)
    {
      Assertions.assertEquals(seqItr.nextLong(),longArr[i]);
    }
    LongArr=seq.toArray();
    Assertions.assertEquals(LongArr.length,seq.size());
    seqItr=seq.iterator();
    for(int i=0;i<seq.size();++i)
    {
      Assertions.assertEquals(seqItr.next(),LongArr[i]);
    }
    doubleArr=seq.toDoubleArray();
    Assertions.assertEquals(doubleArr.length,seq.size());
    seqItr=seq.iterator();
    for(int i=0;i<seq.size();++i)
    {
      Assertions.assertEquals(seqItr.nextDouble(),doubleArr[i]);
    }
    floatArr=seq.toFloatArray();
    Assertions.assertEquals(floatArr.length,seq.size());
    seqItr=seq.iterator();
    for(int i=0;i<seq.size();++i)
    {
      Assertions.assertEquals(seqItr.nextFloat(),floatArr[i]);
    }
  }
  @Test
  public void testClearUncheckedStack()
  {
    var seq=new UncheckedStack();
    seq.clear();
    Assertions.assertEquals(0,seq.size());
    for(int i=0;i<100;++i)
    {
      seq.add(TypeConversionUtil.convertTolong(i));
    }
    Assertions.assertEquals(100,seq.size());
    seq.clear();
    Assertions.assertEquals(0,seq.size());
    Assertions.assertTrue(seq.arr.length>=100);
  }
  @Test
  public void testSizeUncheckedStack()
  {
    var seq=new UncheckedStack();
    int i=0;
    for(;i<100;++i)
    {
      Assertions.assertEquals(i,seq.size());
      seq.add(Long.MIN_VALUE);
    }
    Assertions.assertEquals(i,seq.size());
    var itr=seq.iterator();
    for(;;)
    {
      itr.next();
      itr.remove();
      Assertions.assertEquals(--i,seq.size());
      if(i==0)
      {
        break;
      }
    }
  }
  @Test
  public void testIsEmptyUncheckedStack()
  {
    var seq=new UncheckedStack();
    Assertions.assertTrue(seq.isEmpty());
    int i=0;
    for(;i<100;++i)
    {
      seq.add(Long.MIN_VALUE);
      Assertions.assertFalse(seq.isEmpty());
    }
    var itr=seq.iterator();
    for(;;)
    {
      itr.next();
      itr.remove();
      if(--i==0)
      {
        Assertions.assertTrue(seq.isEmpty());
        break;
      }
      Assertions.assertFalse(seq.isEmpty());
    }
  }
  @Test
  public void testToArrayIntFunctionParamUncheckedStack()
  {
    var seq=new UncheckedStack();
    Object[] result=seq.toArray(Object[]::new);
    Assertions.assertEquals(result.length,0);
    for(int i=0;i<10;++i)
    {
      seq.add(TypeConversionUtil.convertTolong(i));
    }
    result=seq.toArray(Object[]::new);
    Assertions.assertEquals(result.length,seq.size());
    var itr=seq.iterator();
    for(int i=0;i<10;++i)
    {
      Assertions.assertEquals(itr.next(),result[i]);
    }
  }
  @Test
  public void testForEachUncheckedStack()
  {
    {
      var seq=new UncheckedStack();
      ArrayList<Object> arrayList=new ArrayList<>();
      seq.forEach((LongConsumer)arrayList::add);
      Assertions.assertTrue(arrayList.isEmpty());
      for(int i=0;i<10;++i)
      {
        seq.add(TypeConversionUtil.convertTolong(i));
      }
      seq.forEach((LongConsumer)arrayList::add);
      Assertions.assertEquals(arrayList.size(),10);
      var seqItr=seq.iterator();
      for(var v:arrayList)
      {
        Assertions.assertEquals(v,seqItr.next());
      }
      seq.clear();
      arrayList.clear();
      seq.forEach((Consumer<Long>)arrayList::add);
      Assertions.assertTrue(arrayList.isEmpty());
      for(int i=0;i<10;++i)
      {
        seq.add(TypeConversionUtil.convertTolong(i));
      }
      seq.forEach((Consumer<Long>)arrayList::add);
      Assertions.assertEquals(arrayList.size(),10);
      seqItr=seq.iterator();
      for(var v:arrayList)
      {
        Assertions.assertEquals(v,seqItr.next());
      }
    }
  }
  @Test
  public void testToArrayArrayParamUncheckedStack()
  {
    var seq=new UncheckedStack();
    //Test empty exact capacity
    Object[] param=new Object[0];
    Object[] result=seq.toArray(param);
    Assertions.assertTrue(param==result);
    param=new Object[10];
    for(int i=0;i<10;++i)
    {
      param[i]=Integer.valueOf(1);
    }
    result=seq.toArray(param);
    Assertions.assertTrue(param==result);
    Assertions.assertNull(result[0]);
    for(int i=1;i<10;++i)
    {
      Assertions.assertEquals(result[i],Integer.valueOf(1));
    }
    param=new Object[0];
    seq.add(TypeConversionUtil.convertTolong(0));
    result=seq.toArray(param);
    Assertions.assertTrue(param!=result);
    Assertions.assertEquals(result.length,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertToLong(0),result[0]);
    param=new Object[10];
    for(int i=0;i<10;++i)
    {
      param[i]=Integer.valueOf(1);
    }
    result=seq.toArray(param);
    Assertions.assertTrue(param==result);
    Assertions.assertEquals(TypeConversionUtil.convertToLong(0),result[0]);
    Assertions.assertNull(result[1]);
    for(int i=2;i<10;++i)
    {
      Assertions.assertEquals(result[i],Integer.valueOf(1));
    }
    for(int i=1;i<10;++i)
    {
      seq.add(TypeConversionUtil.convertTolong(i));
    }
    result=seq.toArray(param);
    Assertions.assertTrue(param==result);
    var itr=seq.iterator();
    for(int i=0;i<10;++i)
    {
      Assertions.assertEquals(itr.next(),result[i]);
    }
  }
  @Test
  public void testAddUncheckedStack()
  {
    {
      var seq=new UncheckedStack();
      for(int i=0;i<100;++i)
      {
        Assertions.assertTrue(seq.add(TypeConversionUtil.convertTolong(i)));
      }
      Assertions.assertEquals(seq.size(),100);
      var itr=seq.iterator();
      for(int i=100;--i>=0;)
      {
        Assertions.assertEquals(itr.nextLong(),TypeConversionUtil.convertTolong(i));
      }
      Assertions.assertFalse(itr.hasNext());
    }
    {
      var seq=new UncheckedStack(0,null);
      for(int i=0;i<100;++i)
      {
        Assertions.assertTrue(seq.add(TypeConversionUtil.convertTolong(i)));
      }
      Assertions.assertEquals(seq.size(),100);
      var itr=seq.iterator();
      for(int i=100;--i>=0;)
      {
        Assertions.assertEquals(itr.nextLong(),TypeConversionUtil.convertTolong(i));
      }
      Assertions.assertFalse(itr.hasNext());
    }
     {
      var seq=new UncheckedStack(50);
      for(int i=0;i<100;++i)
      {
        Assertions.assertTrue(seq.add(TypeConversionUtil.convertTolong(i)));
      }
      Assertions.assertEquals(seq.size(),100);
      var itr=seq.iterator();
      for(int i=100;--i>=0;)
      {
        Assertions.assertEquals(itr.nextLong(),TypeConversionUtil.convertTolong(i));
      }
      Assertions.assertFalse(itr.hasNext());
    }
  }
  @Test
  public void testPushUncheckedStack()
  {
    {
      var seq=new UncheckedStack();
      for(int i=0;i<100;++i)
      {
        seq.push(TypeConversionUtil.convertTolong(i));
      }
      Assertions.assertEquals(100,seq.size());
      var itr=seq.iterator();
      for(int i=100;--i>=0;)
      {
        Assertions.assertEquals(TypeConversionUtil.convertTolong(i),itr.nextLong());
      }
      Assertions.assertFalse(itr.hasNext());
    }
    {
      var seq=new UncheckedStack(0,null);
      for(int i=0;i<100;++i)
      {
        seq.push(TypeConversionUtil.convertTolong(i));
      }
      Assertions.assertEquals(100,seq.size());
      var itr=seq.iterator();
      for(int i=100;--i>=0;)
      {
        Assertions.assertEquals(TypeConversionUtil.convertTolong(i),itr.nextLong());
      }
      Assertions.assertFalse(itr.hasNext());
    }
     {
      var seq=new UncheckedStack(50);
      for(int i=0;i<100;++i)
      {
        seq.push(TypeConversionUtil.convertTolong(i));
      }
      Assertions.assertEquals(100,seq.size());
      var itr=seq.iterator();
      for(int i=100;--i>=0;)
      {
        Assertions.assertEquals(TypeConversionUtil.convertTolong(i),itr.nextLong());
      }
      Assertions.assertFalse(itr.hasNext());
    }
  }
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
  public void testToArrayUncheckedList()
  {
    var seq=new UncheckedList();
    var longArr=seq.toLongArray();
    Assertions.assertTrue(longArr==OmniArray.OfLong.DEFAULT_ARR);
    var LongArr=seq.toArray();
    Assertions.assertTrue(LongArr==OmniArray.OfLong.DEFAULT_BOXED_ARR);
    var doubleArr=seq.toDoubleArray();
    Assertions.assertTrue(doubleArr==OmniArray.OfDouble.DEFAULT_ARR);
    var floatArr=seq.toFloatArray();
    Assertions.assertTrue(floatArr==OmniArray.OfFloat.DEFAULT_ARR);
    for(int i=0;i<10;++i)
    {
      seq.add(TypeConversionUtil.convertTolong(i));
    }
    longArr=seq.toLongArray();
    Assertions.assertEquals(longArr.length,seq.size());
    var seqItr=seq.iterator();
    for(int i=0;i<seq.size();++i)
    {
      Assertions.assertEquals(seqItr.nextLong(),longArr[i]);
    }
    LongArr=seq.toArray();
    Assertions.assertEquals(LongArr.length,seq.size());
    seqItr=seq.iterator();
    for(int i=0;i<seq.size();++i)
    {
      Assertions.assertEquals(seqItr.next(),LongArr[i]);
    }
    doubleArr=seq.toDoubleArray();
    Assertions.assertEquals(doubleArr.length,seq.size());
    seqItr=seq.iterator();
    for(int i=0;i<seq.size();++i)
    {
      Assertions.assertEquals(seqItr.nextDouble(),doubleArr[i]);
    }
    floatArr=seq.toFloatArray();
    Assertions.assertEquals(floatArr.length,seq.size());
    seqItr=seq.iterator();
    for(int i=0;i<seq.size();++i)
    {
      Assertions.assertEquals(seqItr.nextFloat(),floatArr[i]);
    }
  }
  @Test
  public void testClearUncheckedList()
  {
    var seq=new UncheckedList();
    seq.clear();
    Assertions.assertEquals(0,seq.size());
    for(int i=0;i<100;++i)
    {
      seq.add(TypeConversionUtil.convertTolong(i));
    }
    Assertions.assertEquals(100,seq.size());
    seq.clear();
    Assertions.assertEquals(0,seq.size());
    Assertions.assertTrue(seq.arr.length>=100);
  }
  @Test
  public void testSizeUncheckedList()
  {
    var seq=new UncheckedList();
    int i=0;
    for(;i<100;++i)
    {
      Assertions.assertEquals(i,seq.size());
      seq.add(Long.MIN_VALUE);
    }
    Assertions.assertEquals(i,seq.size());
    var itr=seq.iterator();
    for(;;)
    {
      itr.next();
      itr.remove();
      Assertions.assertEquals(--i,seq.size());
      if(i==0)
      {
        break;
      }
    }
  }
  @Test
  public void testIsEmptyUncheckedList()
  {
    var seq=new UncheckedList();
    Assertions.assertTrue(seq.isEmpty());
    int i=0;
    for(;i<100;++i)
    {
      seq.add(Long.MIN_VALUE);
      Assertions.assertFalse(seq.isEmpty());
    }
    var itr=seq.iterator();
    for(;;)
    {
      itr.next();
      itr.remove();
      if(--i==0)
      {
        Assertions.assertTrue(seq.isEmpty());
        break;
      }
      Assertions.assertFalse(seq.isEmpty());
    }
  }
  @Test
  public void testToArrayIntFunctionParamUncheckedList()
  {
    var seq=new UncheckedList();
    Object[] result=seq.toArray(Object[]::new);
    Assertions.assertEquals(result.length,0);
    for(int i=0;i<10;++i)
    {
      seq.add(TypeConversionUtil.convertTolong(i));
    }
    result=seq.toArray(Object[]::new);
    Assertions.assertEquals(result.length,seq.size());
    var itr=seq.iterator();
    for(int i=0;i<10;++i)
    {
      Assertions.assertEquals(itr.next(),result[i]);
    }
  }
  @Test
  public void testForEachUncheckedList()
  {
    {
      var seq=new UncheckedList();
      ArrayList<Object> arrayList=new ArrayList<>();
      seq.forEach((LongConsumer)arrayList::add);
      Assertions.assertTrue(arrayList.isEmpty());
      for(int i=0;i<10;++i)
      {
        seq.add(TypeConversionUtil.convertTolong(i));
      }
      seq.forEach((LongConsumer)arrayList::add);
      Assertions.assertEquals(arrayList.size(),10);
      var seqItr=seq.iterator();
      for(var v:arrayList)
      {
        Assertions.assertEquals(v,seqItr.next());
      }
      seq.clear();
      arrayList.clear();
      seq.forEach((Consumer<Long>)arrayList::add);
      Assertions.assertTrue(arrayList.isEmpty());
      for(int i=0;i<10;++i)
      {
        seq.add(TypeConversionUtil.convertTolong(i));
      }
      seq.forEach((Consumer<Long>)arrayList::add);
      Assertions.assertEquals(arrayList.size(),10);
      seqItr=seq.iterator();
      for(var v:arrayList)
      {
        Assertions.assertEquals(v,seqItr.next());
      }
    }
  }
  @Test
  public void testToArrayArrayParamUncheckedList()
  {
    var seq=new UncheckedList();
    //Test empty exact capacity
    Object[] param=new Object[0];
    Object[] result=seq.toArray(param);
    Assertions.assertTrue(param==result);
    param=new Object[10];
    for(int i=0;i<10;++i)
    {
      param[i]=Integer.valueOf(1);
    }
    result=seq.toArray(param);
    Assertions.assertTrue(param==result);
    Assertions.assertNull(result[0]);
    for(int i=1;i<10;++i)
    {
      Assertions.assertEquals(result[i],Integer.valueOf(1));
    }
    param=new Object[0];
    seq.add(TypeConversionUtil.convertTolong(0));
    result=seq.toArray(param);
    Assertions.assertTrue(param!=result);
    Assertions.assertEquals(result.length,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertToLong(0),result[0]);
    param=new Object[10];
    for(int i=0;i<10;++i)
    {
      param[i]=Integer.valueOf(1);
    }
    result=seq.toArray(param);
    Assertions.assertTrue(param==result);
    Assertions.assertEquals(TypeConversionUtil.convertToLong(0),result[0]);
    Assertions.assertNull(result[1]);
    for(int i=2;i<10;++i)
    {
      Assertions.assertEquals(result[i],Integer.valueOf(1));
    }
    for(int i=1;i<10;++i)
    {
      seq.add(TypeConversionUtil.convertTolong(i));
    }
    result=seq.toArray(param);
    Assertions.assertTrue(param==result);
    var itr=seq.iterator();
    for(int i=0;i<10;++i)
    {
      Assertions.assertEquals(itr.next(),result[i]);
    }
  }
  @Test
  public void testAddUncheckedList()
  {
    {
      var seq=new UncheckedList();
      for(int i=0;i<100;++i)
      {
        Assertions.assertTrue(seq.add(TypeConversionUtil.convertTolong(i)));
      }
      Assertions.assertEquals(seq.size(),100);
      var itr=seq.iterator();
      for(int i=0;i<100;++i)
      {
        Assertions.assertEquals(itr.nextLong(),TypeConversionUtil.convertTolong(i));
      }
      Assertions.assertFalse(itr.hasNext());
    }
    {
      var seq=new UncheckedList(0,null);
      for(int i=0;i<100;++i)
      {
        Assertions.assertTrue(seq.add(TypeConversionUtil.convertTolong(i)));
      }
      Assertions.assertEquals(seq.size(),100);
      var itr=seq.iterator();
      for(int i=0;i<100;++i)
      {
        Assertions.assertEquals(itr.nextLong(),TypeConversionUtil.convertTolong(i));
      }
      Assertions.assertFalse(itr.hasNext());
    }
     {
      var seq=new UncheckedList(50);
      for(int i=0;i<100;++i)
      {
        Assertions.assertTrue(seq.add(TypeConversionUtil.convertTolong(i)));
      }
      Assertions.assertEquals(seq.size(),100);
      var itr=seq.iterator();
      for(int i=0;i<100;++i)
      {
        Assertions.assertEquals(itr.nextLong(),TypeConversionUtil.convertTolong(i));
      }
      Assertions.assertFalse(itr.hasNext());
    }
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
  public void testToArrayCheckedStack()
  {
    var seq=new CheckedStack();
    var longArr=seq.toLongArray();
    Assertions.assertTrue(longArr==OmniArray.OfLong.DEFAULT_ARR);
    var LongArr=seq.toArray();
    Assertions.assertTrue(LongArr==OmniArray.OfLong.DEFAULT_BOXED_ARR);
    var doubleArr=seq.toDoubleArray();
    Assertions.assertTrue(doubleArr==OmniArray.OfDouble.DEFAULT_ARR);
    var floatArr=seq.toFloatArray();
    Assertions.assertTrue(floatArr==OmniArray.OfFloat.DEFAULT_ARR);
    for(int i=0;i<10;++i)
    {
      seq.add(TypeConversionUtil.convertTolong(i));
    }
    longArr=seq.toLongArray();
    Assertions.assertEquals(longArr.length,seq.size());
    var seqItr=seq.iterator();
    for(int i=0;i<seq.size();++i)
    {
      Assertions.assertEquals(seqItr.nextLong(),longArr[i]);
    }
    LongArr=seq.toArray();
    Assertions.assertEquals(LongArr.length,seq.size());
    seqItr=seq.iterator();
    for(int i=0;i<seq.size();++i)
    {
      Assertions.assertEquals(seqItr.next(),LongArr[i]);
    }
    doubleArr=seq.toDoubleArray();
    Assertions.assertEquals(doubleArr.length,seq.size());
    seqItr=seq.iterator();
    for(int i=0;i<seq.size();++i)
    {
      Assertions.assertEquals(seqItr.nextDouble(),doubleArr[i]);
    }
    floatArr=seq.toFloatArray();
    Assertions.assertEquals(floatArr.length,seq.size());
    seqItr=seq.iterator();
    for(int i=0;i<seq.size();++i)
    {
      Assertions.assertEquals(seqItr.nextFloat(),floatArr[i]);
    }
  }
  @Test
  public void testClearCheckedStack()
  {
    var seq=new CheckedStack();
    seq.clear();
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertEquals(0,seq.size());
    for(int i=0;i<100;++i)
    {
      seq.add(TypeConversionUtil.convertTolong(i));
    }
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    seq.clear();
    Assertions.assertEquals(0,seq.size());
    Assertions.assertNotEquals(100,seq.modCount);
    Assertions.assertTrue(seq.arr.length>=100);
  }
  @Test
  public void testSizeCheckedStack()
  {
    var seq=new CheckedStack();
    int i=0;
    for(;i<100;++i)
    {
      Assertions.assertEquals(i,seq.size());
      seq.add(Long.MIN_VALUE);
    }
    Assertions.assertEquals(i,seq.size());
    var itr=seq.iterator();
    for(;;)
    {
      itr.next();
      itr.remove();
      Assertions.assertEquals(--i,seq.size());
      if(i==0)
      {
        break;
      }
    }
  }
  @Test
  public void testIsEmptyCheckedStack()
  {
    var seq=new CheckedStack();
    Assertions.assertTrue(seq.isEmpty());
    int i=0;
    for(;i<100;++i)
    {
      seq.add(Long.MIN_VALUE);
      Assertions.assertFalse(seq.isEmpty());
    }
    var itr=seq.iterator();
    for(;;)
    {
      itr.next();
      itr.remove();
      if(--i==0)
      {
        Assertions.assertTrue(seq.isEmpty());
        break;
      }
      Assertions.assertFalse(seq.isEmpty());
    }
  }
  @Test
  public void testToArrayIntFunctionParamCheckedStack()
  {
    var seq=new CheckedStack();
    Object[] result=seq.toArray(Object[]::new);
    Assertions.assertEquals(result.length,0);
    for(int i=0;i<10;++i)
    {
      seq.add(TypeConversionUtil.convertTolong(i));
    }
    result=seq.toArray(Object[]::new);
    Assertions.assertEquals(result.length,seq.size());
    var itr=seq.iterator();
    for(int i=0;i<10;++i)
    {
      Assertions.assertEquals(itr.next(),result[i]);
    }
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray((arrSize)->
    {
      seq.add(TypeConversionUtil.convertTolong(arrSize));
      return new Object[arrSize];
    }));
  }
  @Test
  public void testForEachCheckedStack()
  {
    {
      var seq=new CheckedStack();
      ArrayList<Object> arrayList=new ArrayList<>();
      seq.forEach((LongConsumer)arrayList::add);
      Assertions.assertTrue(arrayList.isEmpty());
      for(int i=0;i<10;++i)
      {
        seq.add(TypeConversionUtil.convertTolong(i));
      }
      seq.forEach((LongConsumer)arrayList::add);
      Assertions.assertEquals(arrayList.size(),10);
      var seqItr=seq.iterator();
      for(var v:arrayList)
      {
        Assertions.assertEquals(v,seqItr.next());
      }
      seq.clear();
      arrayList.clear();
      seq.forEach((Consumer<Long>)arrayList::add);
      Assertions.assertTrue(arrayList.isEmpty());
      for(int i=0;i<10;++i)
      {
        seq.add(TypeConversionUtil.convertTolong(i));
      }
      seq.forEach((Consumer<Long>)arrayList::add);
      Assertions.assertEquals(arrayList.size(),10);
      seqItr=seq.iterator();
      for(var v:arrayList)
      {
        Assertions.assertEquals(v,seqItr.next());
      }
    }
    {
      var seq=new CheckedStack();
      seq.forEach((LongConsumer)((val)->seq.add(val)));
      Assertions.assertTrue(seq.isEmpty());
      for(int i=0;i<10;++i)
      {
        seq.add(TypeConversionUtil.convertTolong(i));
      }
      Assertions.assertThrows(ConcurrentModificationException.class,()->
      {
        seq.forEach((LongConsumer)((val)->seq.add(val)));
      });
    }
    {
      var seq=new CheckedStack();
      seq.forEach((Consumer<Long>)((val)->seq.add(val)));
      Assertions.assertTrue(seq.isEmpty());
      for(int i=0;i<10;++i)
      {
        seq.add(TypeConversionUtil.convertTolong(i));
      }
      Assertions.assertThrows(ConcurrentModificationException.class,()->
      {
        seq.forEach((Consumer<Long>)((val)->seq.add(val)));
      });
    }
  }
  @Test
  public void testToArrayArrayParamCheckedStack()
  {
    var seq=new CheckedStack();
    //Test empty exact capacity
    Object[] param=new Object[0];
    Object[] result=seq.toArray(param);
    Assertions.assertTrue(param==result);
    param=new Object[10];
    for(int i=0;i<10;++i)
    {
      param[i]=Integer.valueOf(1);
    }
    result=seq.toArray(param);
    Assertions.assertTrue(param==result);
    Assertions.assertNull(result[0]);
    for(int i=1;i<10;++i)
    {
      Assertions.assertEquals(result[i],Integer.valueOf(1));
    }
    param=new Object[0];
    seq.add(TypeConversionUtil.convertTolong(0));
    result=seq.toArray(param);
    Assertions.assertTrue(param!=result);
    Assertions.assertEquals(result.length,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertToLong(0),result[0]);
    param=new Object[10];
    for(int i=0;i<10;++i)
    {
      param[i]=Integer.valueOf(1);
    }
    result=seq.toArray(param);
    Assertions.assertTrue(param==result);
    Assertions.assertEquals(TypeConversionUtil.convertToLong(0),result[0]);
    Assertions.assertNull(result[1]);
    for(int i=2;i<10;++i)
    {
      Assertions.assertEquals(result[i],Integer.valueOf(1));
    }
    for(int i=1;i<10;++i)
    {
      seq.add(TypeConversionUtil.convertTolong(i));
    }
    result=seq.toArray(param);
    Assertions.assertTrue(param==result);
    var itr=seq.iterator();
    for(int i=0;i<10;++i)
    {
      Assertions.assertEquals(itr.next(),result[i]);
    }
  }
  @Test
  public void testAddCheckedStack()
  {
    {
      var seq=new CheckedStack();
      for(int i=0;i<100;++i)
      {
        Assertions.assertTrue(seq.add(TypeConversionUtil.convertTolong(i)));
      }
      Assertions.assertEquals(seq.size(),100);
      Assertions.assertEquals(100,seq.modCount);
      var itr=seq.iterator();
      for(int i=100;--i>=0;)
      {
        Assertions.assertEquals(itr.nextLong(),TypeConversionUtil.convertTolong(i));
      }
      Assertions.assertFalse(itr.hasNext());
    }
    {
      var seq=new CheckedStack(0,null);
      for(int i=0;i<100;++i)
      {
        Assertions.assertTrue(seq.add(TypeConversionUtil.convertTolong(i)));
      }
      Assertions.assertEquals(seq.size(),100);
      Assertions.assertEquals(100,seq.modCount);
      var itr=seq.iterator();
      for(int i=100;--i>=0;)
      {
        Assertions.assertEquals(itr.nextLong(),TypeConversionUtil.convertTolong(i));
      }
      Assertions.assertFalse(itr.hasNext());
    }
     {
      var seq=new CheckedStack(50);
      for(int i=0;i<100;++i)
      {
        Assertions.assertTrue(seq.add(TypeConversionUtil.convertTolong(i)));
      }
      Assertions.assertEquals(seq.size(),100);
      Assertions.assertEquals(100,seq.modCount);
      var itr=seq.iterator();
      for(int i=100;--i>=0;)
      {
        Assertions.assertEquals(itr.nextLong(),TypeConversionUtil.convertTolong(i));
      }
      Assertions.assertFalse(itr.hasNext());
    }
  }
  @Test
  public void testPushCheckedStack()
  {
    {
      var seq=new CheckedStack();
      for(int i=0;i<100;++i)
      {
        seq.push(TypeConversionUtil.convertTolong(i));
      }
      Assertions.assertEquals(100,seq.modCount);
      Assertions.assertEquals(100,seq.size());
      var itr=seq.iterator();
      for(int i=100;--i>=0;)
      {
        Assertions.assertEquals(TypeConversionUtil.convertTolong(i),itr.nextLong());
      }
      Assertions.assertFalse(itr.hasNext());
    }
    {
      var seq=new CheckedStack(0,null);
      for(int i=0;i<100;++i)
      {
        seq.push(TypeConversionUtil.convertTolong(i));
      }
      Assertions.assertEquals(100,seq.modCount);
      Assertions.assertEquals(100,seq.size());
      var itr=seq.iterator();
      for(int i=100;--i>=0;)
      {
        Assertions.assertEquals(TypeConversionUtil.convertTolong(i),itr.nextLong());
      }
      Assertions.assertFalse(itr.hasNext());
    }
     {
      var seq=new CheckedStack(50);
      for(int i=0;i<100;++i)
      {
        seq.push(TypeConversionUtil.convertTolong(i));
      }
      Assertions.assertEquals(100,seq.modCount);
      Assertions.assertEquals(100,seq.size());
      var itr=seq.iterator();
      for(int i=100;--i>=0;)
      {
        Assertions.assertEquals(TypeConversionUtil.convertTolong(i),itr.nextLong());
      }
      Assertions.assertFalse(itr.hasNext());
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
  public void testToArrayCheckedList()
  {
    var seq=new CheckedList();
    var longArr=seq.toLongArray();
    Assertions.assertTrue(longArr==OmniArray.OfLong.DEFAULT_ARR);
    var LongArr=seq.toArray();
    Assertions.assertTrue(LongArr==OmniArray.OfLong.DEFAULT_BOXED_ARR);
    var doubleArr=seq.toDoubleArray();
    Assertions.assertTrue(doubleArr==OmniArray.OfDouble.DEFAULT_ARR);
    var floatArr=seq.toFloatArray();
    Assertions.assertTrue(floatArr==OmniArray.OfFloat.DEFAULT_ARR);
    for(int i=0;i<10;++i)
    {
      seq.add(TypeConversionUtil.convertTolong(i));
    }
    longArr=seq.toLongArray();
    Assertions.assertEquals(longArr.length,seq.size());
    var seqItr=seq.iterator();
    for(int i=0;i<seq.size();++i)
    {
      Assertions.assertEquals(seqItr.nextLong(),longArr[i]);
    }
    LongArr=seq.toArray();
    Assertions.assertEquals(LongArr.length,seq.size());
    seqItr=seq.iterator();
    for(int i=0;i<seq.size();++i)
    {
      Assertions.assertEquals(seqItr.next(),LongArr[i]);
    }
    doubleArr=seq.toDoubleArray();
    Assertions.assertEquals(doubleArr.length,seq.size());
    seqItr=seq.iterator();
    for(int i=0;i<seq.size();++i)
    {
      Assertions.assertEquals(seqItr.nextDouble(),doubleArr[i]);
    }
    floatArr=seq.toFloatArray();
    Assertions.assertEquals(floatArr.length,seq.size());
    seqItr=seq.iterator();
    for(int i=0;i<seq.size();++i)
    {
      Assertions.assertEquals(seqItr.nextFloat(),floatArr[i]);
    }
  }
  @Test
  public void testClearCheckedList()
  {
    var seq=new CheckedList();
    seq.clear();
    Assertions.assertEquals(0,seq.modCount);
    Assertions.assertEquals(0,seq.size());
    for(int i=0;i<100;++i)
    {
      seq.add(TypeConversionUtil.convertTolong(i));
    }
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    seq.clear();
    Assertions.assertEquals(0,seq.size());
    Assertions.assertNotEquals(100,seq.modCount);
    Assertions.assertTrue(seq.arr.length>=100);
  }
  @Test
  public void testSizeCheckedList()
  {
    var seq=new CheckedList();
    int i=0;
    for(;i<100;++i)
    {
      Assertions.assertEquals(i,seq.size());
      seq.add(Long.MIN_VALUE);
    }
    Assertions.assertEquals(i,seq.size());
    var itr=seq.iterator();
    for(;;)
    {
      itr.next();
      itr.remove();
      Assertions.assertEquals(--i,seq.size());
      if(i==0)
      {
        break;
      }
    }
  }
  @Test
  public void testIsEmptyCheckedList()
  {
    var seq=new CheckedList();
    Assertions.assertTrue(seq.isEmpty());
    int i=0;
    for(;i<100;++i)
    {
      seq.add(Long.MIN_VALUE);
      Assertions.assertFalse(seq.isEmpty());
    }
    var itr=seq.iterator();
    for(;;)
    {
      itr.next();
      itr.remove();
      if(--i==0)
      {
        Assertions.assertTrue(seq.isEmpty());
        break;
      }
      Assertions.assertFalse(seq.isEmpty());
    }
  }
  @Test
  public void testToArrayIntFunctionParamCheckedList()
  {
    var seq=new CheckedList();
    Object[] result=seq.toArray(Object[]::new);
    Assertions.assertEquals(result.length,0);
    for(int i=0;i<10;++i)
    {
      seq.add(TypeConversionUtil.convertTolong(i));
    }
    result=seq.toArray(Object[]::new);
    Assertions.assertEquals(result.length,seq.size());
    var itr=seq.iterator();
    for(int i=0;i<10;++i)
    {
      Assertions.assertEquals(itr.next(),result[i]);
    }
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray((arrSize)->
    {
      seq.add(TypeConversionUtil.convertTolong(arrSize));
      return new Object[arrSize];
    }));
  }
  @Test
  public void testForEachCheckedList()
  {
    {
      var seq=new CheckedList();
      ArrayList<Object> arrayList=new ArrayList<>();
      seq.forEach((LongConsumer)arrayList::add);
      Assertions.assertTrue(arrayList.isEmpty());
      for(int i=0;i<10;++i)
      {
        seq.add(TypeConversionUtil.convertTolong(i));
      }
      seq.forEach((LongConsumer)arrayList::add);
      Assertions.assertEquals(arrayList.size(),10);
      var seqItr=seq.iterator();
      for(var v:arrayList)
      {
        Assertions.assertEquals(v,seqItr.next());
      }
      seq.clear();
      arrayList.clear();
      seq.forEach((Consumer<Long>)arrayList::add);
      Assertions.assertTrue(arrayList.isEmpty());
      for(int i=0;i<10;++i)
      {
        seq.add(TypeConversionUtil.convertTolong(i));
      }
      seq.forEach((Consumer<Long>)arrayList::add);
      Assertions.assertEquals(arrayList.size(),10);
      seqItr=seq.iterator();
      for(var v:arrayList)
      {
        Assertions.assertEquals(v,seqItr.next());
      }
    }
    {
      var seq=new CheckedList();
      seq.forEach((LongConsumer)((val)->seq.add(val)));
      Assertions.assertTrue(seq.isEmpty());
      for(int i=0;i<10;++i)
      {
        seq.add(TypeConversionUtil.convertTolong(i));
      }
      Assertions.assertThrows(ConcurrentModificationException.class,()->
      {
        seq.forEach((LongConsumer)((val)->seq.add(val)));
      });
    }
    {
      var seq=new CheckedList();
      seq.forEach((Consumer<Long>)((val)->seq.add(val)));
      Assertions.assertTrue(seq.isEmpty());
      for(int i=0;i<10;++i)
      {
        seq.add(TypeConversionUtil.convertTolong(i));
      }
      Assertions.assertThrows(ConcurrentModificationException.class,()->
      {
        seq.forEach((Consumer<Long>)((val)->seq.add(val)));
      });
    }
  }
  @Test
  public void testToArrayArrayParamCheckedList()
  {
    var seq=new CheckedList();
    //Test empty exact capacity
    Object[] param=new Object[0];
    Object[] result=seq.toArray(param);
    Assertions.assertTrue(param==result);
    param=new Object[10];
    for(int i=0;i<10;++i)
    {
      param[i]=Integer.valueOf(1);
    }
    result=seq.toArray(param);
    Assertions.assertTrue(param==result);
    Assertions.assertNull(result[0]);
    for(int i=1;i<10;++i)
    {
      Assertions.assertEquals(result[i],Integer.valueOf(1));
    }
    param=new Object[0];
    seq.add(TypeConversionUtil.convertTolong(0));
    result=seq.toArray(param);
    Assertions.assertTrue(param!=result);
    Assertions.assertEquals(result.length,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertToLong(0),result[0]);
    param=new Object[10];
    for(int i=0;i<10;++i)
    {
      param[i]=Integer.valueOf(1);
    }
    result=seq.toArray(param);
    Assertions.assertTrue(param==result);
    Assertions.assertEquals(TypeConversionUtil.convertToLong(0),result[0]);
    Assertions.assertNull(result[1]);
    for(int i=2;i<10;++i)
    {
      Assertions.assertEquals(result[i],Integer.valueOf(1));
    }
    for(int i=1;i<10;++i)
    {
      seq.add(TypeConversionUtil.convertTolong(i));
    }
    result=seq.toArray(param);
    Assertions.assertTrue(param==result);
    var itr=seq.iterator();
    for(int i=0;i<10;++i)
    {
      Assertions.assertEquals(itr.next(),result[i]);
    }
  }
  @Test
  public void testAddCheckedList()
  {
    {
      var seq=new CheckedList();
      for(int i=0;i<100;++i)
      {
        Assertions.assertTrue(seq.add(TypeConversionUtil.convertTolong(i)));
      }
      Assertions.assertEquals(seq.size(),100);
      Assertions.assertEquals(100,seq.modCount);
      var itr=seq.iterator();
      for(int i=0;i<100;++i)
      {
        Assertions.assertEquals(itr.nextLong(),TypeConversionUtil.convertTolong(i));
      }
      Assertions.assertFalse(itr.hasNext());
    }
    {
      var seq=new CheckedList(0,null);
      for(int i=0;i<100;++i)
      {
        Assertions.assertTrue(seq.add(TypeConversionUtil.convertTolong(i)));
      }
      Assertions.assertEquals(seq.size(),100);
      Assertions.assertEquals(100,seq.modCount);
      var itr=seq.iterator();
      for(int i=0;i<100;++i)
      {
        Assertions.assertEquals(itr.nextLong(),TypeConversionUtil.convertTolong(i));
      }
      Assertions.assertFalse(itr.hasNext());
    }
     {
      var seq=new CheckedList(50);
      for(int i=0;i<100;++i)
      {
        Assertions.assertTrue(seq.add(TypeConversionUtil.convertTolong(i)));
      }
      Assertions.assertEquals(seq.size(),100);
      Assertions.assertEquals(100,seq.modCount);
      var itr=seq.iterator();
      for(int i=0;i<100;++i)
      {
        Assertions.assertEquals(itr.nextLong(),TypeConversionUtil.convertTolong(i));
      }
      Assertions.assertFalse(itr.hasNext());
    }
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
  public void testToArrayUncheckedSubList()
  {
    var root=new UncheckedList();
    var subList=root.subList(0,0);
    var seq=subList.subList(0,0);
    var longArr=seq.toLongArray();
    Assertions.assertTrue(longArr==OmniArray.OfLong.DEFAULT_ARR);
    var LongArr=seq.toArray();
    Assertions.assertTrue(LongArr==OmniArray.OfLong.DEFAULT_BOXED_ARR);
    var doubleArr=seq.toDoubleArray();
    Assertions.assertTrue(doubleArr==OmniArray.OfDouble.DEFAULT_ARR);
    var floatArr=seq.toFloatArray();
    Assertions.assertTrue(floatArr==OmniArray.OfFloat.DEFAULT_ARR);
    for(int i=0;i<10;++i)
    {
      seq.add(TypeConversionUtil.convertTolong(i));
    }
    longArr=seq.toLongArray();
    Assertions.assertEquals(longArr.length,seq.size());
    var seqItr=seq.iterator();
    for(int i=0;i<seq.size();++i)
    {
      Assertions.assertEquals(seqItr.nextLong(),longArr[i]);
    }
    LongArr=seq.toArray();
    Assertions.assertEquals(LongArr.length,seq.size());
    seqItr=seq.iterator();
    for(int i=0;i<seq.size();++i)
    {
      Assertions.assertEquals(seqItr.next(),LongArr[i]);
    }
    doubleArr=seq.toDoubleArray();
    Assertions.assertEquals(doubleArr.length,seq.size());
    seqItr=seq.iterator();
    for(int i=0;i<seq.size();++i)
    {
      Assertions.assertEquals(seqItr.nextDouble(),doubleArr[i]);
    }
    floatArr=seq.toFloatArray();
    Assertions.assertEquals(floatArr.length,seq.size());
    seqItr=seq.iterator();
    for(int i=0;i<seq.size();++i)
    {
      Assertions.assertEquals(seqItr.nextFloat(),floatArr[i]);
    }
  }
  @Test
  public void testClearUncheckedSubList()
  {
    var root=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      root.add(TypeConversionUtil.convertTolong(i));
    }
    var emptySubList=root.subList(50,50);
    emptySubList.clear();
    Assertions.assertEquals(0,emptySubList.size());
    Assertions.assertEquals(100,root.size());
    var nonEmptySubList=root.subList(10,90);
    var nonEmptySubSubList=nonEmptySubList.subList(15,65);
    nonEmptySubSubList.clear();
    Assertions.assertEquals(0,nonEmptySubSubList.size());
    Assertions.assertEquals(50,root.size());
    Assertions.assertEquals(30,nonEmptySubList.size());
    for(int i=0;i<25;++i)
    {
      Assertions.assertEquals(root.getLong(i),TypeConversionUtil.convertTolong(i));
    }
    for(int i=25;i<50;++i)
    {
      Assertions.assertEquals(root.getLong(i),TypeConversionUtil.convertTolong(i+50));
    }
    nonEmptySubList.clear();
    Assertions.assertEquals(20,root.size());
    Assertions.assertEquals(0,nonEmptySubList.size());
    for(int i=0;i<10;++i)
    {
      Assertions.assertEquals(root.getLong(i),TypeConversionUtil.convertTolong(i));
    }
    for(int i=10;i<20;++i)
    {
      Assertions.assertEquals(root.getLong(i),TypeConversionUtil.convertTolong(i+80));
    }
    Assertions.assertTrue(root.arr.length>=100);
  }
  @Test
  public void testSizeUncheckedSubList()
  {
    var root=new UncheckedList();
    var subList=root.subList(0,0);
    var seq=subList.subList(0,0);
    int i=0;
    for(;i<100;++i)
    {
      Assertions.assertEquals(i,seq.size());
      seq.add(Long.MIN_VALUE);
    }
    Assertions.assertEquals(i,seq.size());
    var itr=seq.iterator();
    for(;;)
    {
      itr.next();
      itr.remove();
      Assertions.assertEquals(--i,seq.size());
      if(i==0)
      {
        break;
      }
    }
  }
  @Test
  public void testIsEmptyUncheckedSubList()
  {
    var root=new UncheckedList();
    var subList=root.subList(0,0);
    var seq=subList.subList(0,0);
    Assertions.assertTrue(seq.isEmpty());
    int i=0;
    for(;i<100;++i)
    {
      seq.add(Long.MIN_VALUE);
      Assertions.assertFalse(seq.isEmpty());
    }
    var itr=seq.iterator();
    for(;;)
    {
      itr.next();
      itr.remove();
      if(--i==0)
      {
        Assertions.assertTrue(seq.isEmpty());
        break;
      }
      Assertions.assertFalse(seq.isEmpty());
    }
  }
  @Test
  public void testToArrayIntFunctionParamUncheckedSubList()
  {
    var root=new UncheckedList();
    var subList=root.subList(0,0);
    var seq=subList.subList(0,0);
    Object[] result=seq.toArray(Object[]::new);
    Assertions.assertEquals(result.length,0);
    for(int i=0;i<10;++i)
    {
      seq.add(TypeConversionUtil.convertTolong(i));
    }
    result=seq.toArray(Object[]::new);
    Assertions.assertEquals(result.length,seq.size());
    var itr=seq.iterator();
    for(int i=0;i<10;++i)
    {
      Assertions.assertEquals(itr.next(),result[i]);
    }
  }
  @Test
  public void testForEachUncheckedSubList()
  {
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      ArrayList<Object> arrayList=new ArrayList<>();
      seq.forEach((LongConsumer)arrayList::add);
      Assertions.assertTrue(arrayList.isEmpty());
      for(int i=0;i<10;++i)
      {
        seq.add(TypeConversionUtil.convertTolong(i));
      }
      seq.forEach((LongConsumer)arrayList::add);
      Assertions.assertEquals(arrayList.size(),10);
      var seqItr=seq.iterator();
      for(var v:arrayList)
      {
        Assertions.assertEquals(v,seqItr.next());
      }
      seq.clear();
      arrayList.clear();
      seq.forEach((Consumer<Long>)arrayList::add);
      Assertions.assertTrue(arrayList.isEmpty());
      for(int i=0;i<10;++i)
      {
        seq.add(TypeConversionUtil.convertTolong(i));
      }
      seq.forEach((Consumer<Long>)arrayList::add);
      Assertions.assertEquals(arrayList.size(),10);
      seqItr=seq.iterator();
      for(var v:arrayList)
      {
        Assertions.assertEquals(v,seqItr.next());
      }
    }
  }
  @Test
  public void testToArrayArrayParamUncheckedSubList()
  {
    var root=new UncheckedList();
    var subList=root.subList(0,0);
    var seq=subList.subList(0,0);
    //Test empty exact capacity
    Object[] param=new Object[0];
    Object[] result=seq.toArray(param);
    Assertions.assertTrue(param==result);
    param=new Object[10];
    for(int i=0;i<10;++i)
    {
      param[i]=Integer.valueOf(1);
    }
    result=seq.toArray(param);
    Assertions.assertTrue(param==result);
    Assertions.assertNull(result[0]);
    for(int i=1;i<10;++i)
    {
      Assertions.assertEquals(result[i],Integer.valueOf(1));
    }
    param=new Object[0];
    seq.add(TypeConversionUtil.convertTolong(0));
    result=seq.toArray(param);
    Assertions.assertTrue(param!=result);
    Assertions.assertEquals(result.length,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertToLong(0),result[0]);
    param=new Object[10];
    for(int i=0;i<10;++i)
    {
      param[i]=Integer.valueOf(1);
    }
    result=seq.toArray(param);
    Assertions.assertTrue(param==result);
    Assertions.assertEquals(TypeConversionUtil.convertToLong(0),result[0]);
    Assertions.assertNull(result[1]);
    for(int i=2;i<10;++i)
    {
      Assertions.assertEquals(result[i],Integer.valueOf(1));
    }
    for(int i=1;i<10;++i)
    {
      seq.add(TypeConversionUtil.convertTolong(i));
    }
    result=seq.toArray(param);
    Assertions.assertTrue(param==result);
    var itr=seq.iterator();
    for(int i=0;i<10;++i)
    {
      Assertions.assertEquals(itr.next(),result[i]);
    }
  }
  @Test
  public void testAddUncheckedSubList()
  {
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      for(int i=0;i<100;++i)
      {
        Assertions.assertTrue(seq.add(TypeConversionUtil.convertTolong(i)));
      }
      Assertions.assertEquals(seq.size(),100);
      Assertions.assertEquals(subList.size(),100);
      Assertions.assertEquals(root.size(),100);
      var itr=seq.iterator();
      for(int i=0;i<100;++i)
      {
        Assertions.assertEquals(itr.nextLong(),TypeConversionUtil.convertTolong(i));
      }
      Assertions.assertFalse(itr.hasNext());
    }
    {
      var root=new UncheckedList(0,null);
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      for(int i=0;i<100;++i)
      {
        Assertions.assertTrue(seq.add(TypeConversionUtil.convertTolong(i)));
      }
      Assertions.assertEquals(seq.size(),100);
      Assertions.assertEquals(subList.size(),100);
      Assertions.assertEquals(root.size(),100);
      var itr=seq.iterator();
      for(int i=0;i<100;++i)
      {
        Assertions.assertEquals(itr.nextLong(),TypeConversionUtil.convertTolong(i));
      }
      Assertions.assertFalse(itr.hasNext());
    }
     {
      var root=new UncheckedList(50);
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      for(int i=0;i<100;++i)
      {
        Assertions.assertTrue(seq.add(TypeConversionUtil.convertTolong(i)));
      }
      Assertions.assertEquals(seq.size(),100);
      Assertions.assertEquals(subList.size(),100);
      Assertions.assertEquals(root.size(),100);
      var itr=seq.iterator();
      for(int i=0;i<100;++i)
      {
        Assertions.assertEquals(itr.nextLong(),TypeConversionUtil.convertTolong(i));
      }
      Assertions.assertFalse(itr.hasNext());
    }
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
  public void testToArrayCheckedSubList()
  {
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      subList.add(Long.MIN_VALUE);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toLongArray());
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray());
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toDoubleArray());
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toFloatArray());
    }
    var root=new CheckedList();
    var subList=root.subList(0,0);
    var seq=subList.subList(0,0);
    var longArr=seq.toLongArray();
    Assertions.assertTrue(longArr==OmniArray.OfLong.DEFAULT_ARR);
    var LongArr=seq.toArray();
    Assertions.assertTrue(LongArr==OmniArray.OfLong.DEFAULT_BOXED_ARR);
    var doubleArr=seq.toDoubleArray();
    Assertions.assertTrue(doubleArr==OmniArray.OfDouble.DEFAULT_ARR);
    var floatArr=seq.toFloatArray();
    Assertions.assertTrue(floatArr==OmniArray.OfFloat.DEFAULT_ARR);
    for(int i=0;i<10;++i)
    {
      seq.add(TypeConversionUtil.convertTolong(i));
    }
    longArr=seq.toLongArray();
    Assertions.assertEquals(longArr.length,seq.size());
    var seqItr=seq.iterator();
    for(int i=0;i<seq.size();++i)
    {
      Assertions.assertEquals(seqItr.nextLong(),longArr[i]);
    }
    LongArr=seq.toArray();
    Assertions.assertEquals(LongArr.length,seq.size());
    seqItr=seq.iterator();
    for(int i=0;i<seq.size();++i)
    {
      Assertions.assertEquals(seqItr.next(),LongArr[i]);
    }
    doubleArr=seq.toDoubleArray();
    Assertions.assertEquals(doubleArr.length,seq.size());
    seqItr=seq.iterator();
    for(int i=0;i<seq.size();++i)
    {
      Assertions.assertEquals(seqItr.nextDouble(),doubleArr[i]);
    }
    floatArr=seq.toFloatArray();
    Assertions.assertEquals(floatArr.length,seq.size());
    seqItr=seq.iterator();
    for(int i=0;i<seq.size();++i)
    {
      Assertions.assertEquals(seqItr.nextFloat(),floatArr[i]);
    }
  }
  @Test
  public void testClearCheckedSubList()
  {
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      subList.add(Long.MIN_VALUE);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.clear());
    }
    var root=new CheckedList();
    for(int i=0;i<100;++i)
    {
      root.add(TypeConversionUtil.convertTolong(i));
    }
    var emptySubList=root.subList(50,50);
    int modCount=root.modCount;
    emptySubList.clear();
    Assertions.assertEquals(0,emptySubList.size());
    Assertions.assertEquals(100,root.size());
    Assertions.assertEquals(modCount,root.modCount);
    var nonEmptySubList=root.subList(10,90);
    var nonEmptySubSubList=nonEmptySubList.subList(15,65);
    nonEmptySubSubList.clear();
    Assertions.assertEquals(0,nonEmptySubSubList.size());
    Assertions.assertEquals(50,root.size());
    Assertions.assertEquals(30,nonEmptySubList.size());
    Assertions.assertNotEquals(modCount,root.modCount);
    for(int i=0;i<25;++i)
    {
      Assertions.assertEquals(root.getLong(i),TypeConversionUtil.convertTolong(i));
    }
    for(int i=25;i<50;++i)
    {
      Assertions.assertEquals(root.getLong(i),TypeConversionUtil.convertTolong(i+50));
    }
    modCount=root.modCount;
    nonEmptySubList.clear();
    Assertions.assertNotEquals(modCount,root.modCount);
    modCount=root.modCount;
    Assertions.assertThrows(ConcurrentModificationException.class,()->nonEmptySubSubList.clear());
    Assertions.assertEquals(modCount,root.modCount);
    Assertions.assertEquals(20,root.size());
    Assertions.assertEquals(0,nonEmptySubList.size());
    for(int i=0;i<10;++i)
    {
      Assertions.assertEquals(root.getLong(i),TypeConversionUtil.convertTolong(i));
    }
    for(int i=10;i<20;++i)
    {
      Assertions.assertEquals(root.getLong(i),TypeConversionUtil.convertTolong(i+80));
    }
    Assertions.assertTrue(root.arr.length>=100);
    root.add(Long.MIN_VALUE);
    modCount=root.modCount;
    Assertions.assertThrows(ConcurrentModificationException.class,()->nonEmptySubList.clear());
    Assertions.assertEquals(modCount,root.modCount);
  }
  @Test
  public void testSizeCheckedSubList()
  {
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      subList.add(Long.MIN_VALUE);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.size());
    }
    var root=new CheckedList();
    var subList=root.subList(0,0);
    var seq=subList.subList(0,0);
    int i=0;
    for(;i<100;++i)
    {
      Assertions.assertEquals(i,seq.size());
      seq.add(Long.MIN_VALUE);
    }
    Assertions.assertEquals(i,seq.size());
    var itr=seq.iterator();
    for(;;)
    {
      itr.next();
      itr.remove();
      Assertions.assertEquals(--i,seq.size());
      if(i==0)
      {
        break;
      }
    }
    root.add(Long.MIN_VALUE);
    Assertions.assertThrows(ConcurrentModificationException.class,()->
    {
      seq.size();
    });
  }
  @Test
  public void testIsEmptyCheckedSubList()
  {
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      subList.add(Long.MIN_VALUE);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.isEmpty());
    }
    var root=new CheckedList();
    var subList=root.subList(0,0);
    var seq=subList.subList(0,0);
    Assertions.assertTrue(seq.isEmpty());
    int i=0;
    for(;i<100;++i)
    {
      seq.add(Long.MIN_VALUE);
      Assertions.assertFalse(seq.isEmpty());
    }
    var itr=seq.iterator();
    for(;;)
    {
      itr.next();
      itr.remove();
      if(--i==0)
      {
        Assertions.assertTrue(seq.isEmpty());
        break;
      }
      Assertions.assertFalse(seq.isEmpty());
    }
    root.add(Long.MIN_VALUE);
    Assertions.assertThrows(ConcurrentModificationException.class,()->
    {
      seq.isEmpty();
    });
  }
  @Test
  public void testToArrayIntFunctionParamCheckedSubList()
  {
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      subList.add(Long.MIN_VALUE);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(Object[]::new));
    }
    var root=new CheckedList();
    var subList=root.subList(0,0);
    var seq=subList.subList(0,0);
    Object[] result=seq.toArray(Object[]::new);
    Assertions.assertEquals(result.length,0);
    for(int i=0;i<10;++i)
    {
      seq.add(TypeConversionUtil.convertTolong(i));
    }
    result=seq.toArray(Object[]::new);
    Assertions.assertEquals(result.length,seq.size());
    var itr=seq.iterator();
    for(int i=0;i<10;++i)
    {
      Assertions.assertEquals(itr.next(),result[i]);
    }
    Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray((arrSize)->
    {
      seq.add(TypeConversionUtil.convertTolong(arrSize));
      return new Object[arrSize];
    }));
  }
  @Test
  public void testForEachCheckedSubList()
  {
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      subList.add(Long.MIN_VALUE);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.forEach((Consumer<Long>)((v)->{return;})));
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.forEach((LongConsumer)((v)->{return;})));
    }
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      ArrayList<Object> arrayList=new ArrayList<>();
      seq.forEach((LongConsumer)arrayList::add);
      Assertions.assertTrue(arrayList.isEmpty());
      for(int i=0;i<10;++i)
      {
        seq.add(TypeConversionUtil.convertTolong(i));
      }
      seq.forEach((LongConsumer)arrayList::add);
      Assertions.assertEquals(arrayList.size(),10);
      var seqItr=seq.iterator();
      for(var v:arrayList)
      {
        Assertions.assertEquals(v,seqItr.next());
      }
      seq.clear();
      arrayList.clear();
      seq.forEach((Consumer<Long>)arrayList::add);
      Assertions.assertTrue(arrayList.isEmpty());
      for(int i=0;i<10;++i)
      {
        seq.add(TypeConversionUtil.convertTolong(i));
      }
      seq.forEach((Consumer<Long>)arrayList::add);
      Assertions.assertEquals(arrayList.size(),10);
      seqItr=seq.iterator();
      for(var v:arrayList)
      {
        Assertions.assertEquals(v,seqItr.next());
      }
    }
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.forEach((LongConsumer)((val)->seq.add(val)));
      Assertions.assertTrue(seq.isEmpty());
      for(int i=0;i<10;++i)
      {
        seq.add(TypeConversionUtil.convertTolong(i));
      }
      Assertions.assertThrows(ConcurrentModificationException.class,()->
      {
        seq.forEach((LongConsumer)((val)->seq.add(val)));
      });
    }
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.forEach((Consumer<Long>)((val)->seq.add(val)));
      Assertions.assertTrue(seq.isEmpty());
      for(int i=0;i<10;++i)
      {
        seq.add(TypeConversionUtil.convertTolong(i));
      }
      Assertions.assertThrows(ConcurrentModificationException.class,()->
      {
        seq.forEach((Consumer<Long>)((val)->seq.add(val)));
      });
    }
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      subList.add(TypeConversionUtil.convertTolong(1));
      Assertions.assertThrows(ConcurrentModificationException.class,()->
      {
        seq.forEach((LongConsumer)((v)->{return;}));
      });
    }
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      subList.add(TypeConversionUtil.convertTolong(1));
      Assertions.assertThrows(ConcurrentModificationException.class,()->
      {
        seq.forEach((Consumer<Long>)((v)->{return;}));
      });
    }
  }
  @Test
  public void testToArrayArrayParamCheckedSubList()
  {
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      subList.add(Long.MIN_VALUE);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.toArray(new Object[0]));
    }
    var root=new CheckedList();
    var subList=root.subList(0,0);
    var seq=subList.subList(0,0);
    //Test empty exact capacity
    Object[] param=new Object[0];
    Object[] result=seq.toArray(param);
    Assertions.assertTrue(param==result);
    param=new Object[10];
    for(int i=0;i<10;++i)
    {
      param[i]=Integer.valueOf(1);
    }
    result=seq.toArray(param);
    Assertions.assertTrue(param==result);
    Assertions.assertNull(result[0]);
    for(int i=1;i<10;++i)
    {
      Assertions.assertEquals(result[i],Integer.valueOf(1));
    }
    param=new Object[0];
    seq.add(TypeConversionUtil.convertTolong(0));
    result=seq.toArray(param);
    Assertions.assertTrue(param!=result);
    Assertions.assertEquals(result.length,seq.size());
    Assertions.assertEquals(TypeConversionUtil.convertToLong(0),result[0]);
    param=new Object[10];
    for(int i=0;i<10;++i)
    {
      param[i]=Integer.valueOf(1);
    }
    result=seq.toArray(param);
    Assertions.assertTrue(param==result);
    Assertions.assertEquals(TypeConversionUtil.convertToLong(0),result[0]);
    Assertions.assertNull(result[1]);
    for(int i=2;i<10;++i)
    {
      Assertions.assertEquals(result[i],Integer.valueOf(1));
    }
    for(int i=1;i<10;++i)
    {
      seq.add(TypeConversionUtil.convertTolong(i));
    }
    result=seq.toArray(param);
    Assertions.assertTrue(param==result);
    var itr=seq.iterator();
    for(int i=0;i<10;++i)
    {
      Assertions.assertEquals(itr.next(),result[i]);
    }
  }
  @Test
  public void testAddCheckedSubList()
  {
    {
        {
          var root=new CheckedList();
          var subList=root.subList(0,0);
          var seq=subList.subList(0,0);
          subList.add(Long.MIN_VALUE);
          Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(Long.MIN_VALUE));
        }
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      for(int i=0;i<100;++i)
      {
        Assertions.assertTrue(seq.add(TypeConversionUtil.convertTolong(i)));
      }
      Assertions.assertEquals(seq.size(),100);
      Assertions.assertEquals(subList.size(),100);
      Assertions.assertEquals(root.size(),100);
      Assertions.assertEquals(100,root.modCount);
      var itr=seq.iterator();
      for(int i=0;i<100;++i)
      {
        Assertions.assertEquals(itr.nextLong(),TypeConversionUtil.convertTolong(i));
      }
      Assertions.assertFalse(itr.hasNext());
    }
    {
        {
          var root=new CheckedList(0,null);
          var subList=root.subList(0,0);
          var seq=subList.subList(0,0);
          subList.add(Long.MIN_VALUE);
          Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(Long.MIN_VALUE));
        }
      var root=new CheckedList(0,null);
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      for(int i=0;i<100;++i)
      {
        Assertions.assertTrue(seq.add(TypeConversionUtil.convertTolong(i)));
      }
      Assertions.assertEquals(seq.size(),100);
      Assertions.assertEquals(subList.size(),100);
      Assertions.assertEquals(root.size(),100);
      Assertions.assertEquals(100,root.modCount);
      var itr=seq.iterator();
      for(int i=0;i<100;++i)
      {
        Assertions.assertEquals(itr.nextLong(),TypeConversionUtil.convertTolong(i));
      }
      Assertions.assertFalse(itr.hasNext());
    }
     {
        {
          var root=new CheckedList(50);
          var subList=root.subList(0,0);
          var seq=subList.subList(0,0);
          subList.add(Long.MIN_VALUE);
          Assertions.assertThrows(ConcurrentModificationException.class,()->seq.add(Long.MIN_VALUE));
        }
      var root=new CheckedList(50);
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      for(int i=0;i<100;++i)
      {
        Assertions.assertTrue(seq.add(TypeConversionUtil.convertTolong(i)));
      }
      Assertions.assertEquals(seq.size(),100);
      Assertions.assertEquals(subList.size(),100);
      Assertions.assertEquals(root.size(),100);
      Assertions.assertEquals(100,root.modCount);
      var itr=seq.iterator();
      for(int i=0;i<100;++i)
      {
        Assertions.assertEquals(itr.nextLong(),TypeConversionUtil.convertTolong(i));
      }
      Assertions.assertFalse(itr.hasNext());
    }
  }
  @Test
  public void testCloneCheckedSubList()
  {
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      subList.add(Long.MIN_VALUE);
      Assertions.assertThrows(ConcurrentModificationException.class,()->seq.clone());
    }
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
