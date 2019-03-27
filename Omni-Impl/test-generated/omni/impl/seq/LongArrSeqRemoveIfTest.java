package omni.impl.seq;
import java.util.function.Predicate;
import org.junit.jupiter.api.Assertions;
import java.util.ConcurrentModificationException;
import omni.util.TypeConversionUtil;
import omni.util.OmniArray;
import java.util.Random;
import org.junit.jupiter.api.Test;
import omni.impl.LongMonitoredPredicate;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.function.LongPredicate;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import omni.api.OmniList;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class LongArrSeqRemoveIfTest
{
//TODO place sanity checks for checked sequence modification behavior
  private static final Field CheckedSubListmodCount;
  private static final Field CheckedSubListparent;
  private static final Field CheckedSubListroot;
  private static final Field CheckedSubListsize;
  private static final Field UncheckedSubListparent;
  private static final Field UncheckedSubListroot;
  private static final Field UncheckedSubListsize;
  static
  {
    try
    {
      Field modifiersField=Field.class.getDeclaredField("modifiers");
      modifiersField.setAccessible(true);
      {
        Class<?> clazz=new LongArrSeq.CheckedList().subList(0,0).getClass();
        (CheckedSubListmodCount=clazz.getDeclaredField("modCount")).setAccessible(true);
        (CheckedSubListparent=clazz.getDeclaredField("parent")).setAccessible(true);
        modifiersField.setInt(CheckedSubListparent,CheckedSubListparent.getModifiers()&~Modifier.FINAL);
        (CheckedSubListroot=clazz.getDeclaredField("root")).setAccessible(true);
        modifiersField.setInt(CheckedSubListroot,CheckedSubListroot.getModifiers()&~Modifier.FINAL);
        (CheckedSubListsize=clazz.getDeclaredField("size")).setAccessible(true);
      }
      {
        Class<?> clazz=new LongArrSeq.UncheckedList().subList(0,0).getClass();
        (UncheckedSubListparent=clazz.getDeclaredField("parent")).setAccessible(true);
        modifiersField.setInt(UncheckedSubListparent,UncheckedSubListparent.getModifiers()&~Modifier.FINAL);
        (UncheckedSubListroot=clazz.getDeclaredField("root")).setAccessible(true);
        modifiersField.setInt(UncheckedSubListroot,UncheckedSubListroot.getModifiers()&~Modifier.FINAL);
        (UncheckedSubListsize=clazz.getDeclaredField("size")).setAccessible(true);
      }
    }
    catch(Exception e)
    {
      throw new ExceptionInInitializerError(e);
    }
  }
  private static LongArrSeq.CheckedList getRoot(Object seq)
  {
    try
    {
      return (LongArrSeq.CheckedList)CheckedSubListroot.get(seq);
    }
    catch(Exception e)
    {
      throw new RuntimeException(e);
    }
  }
  private static OmniList.OfLong getParent(Object seq)
  {
    try
    {
      return (OmniList.OfLong)CheckedSubListparent.get(seq);
    }
    catch(Exception e)
    {
      throw new RuntimeException(e);
    }
  }
  private static LongArrSeq.UncheckedList assertUncheckedSubListIntegrity(int expectedModCount,int expectedParentSize,Object obj)
  {
    if(!(obj instanceof LongArrSeq.UncheckedList))
    {
      try
      {
        Object parent=UncheckedSubListparent.get(obj);
        if(parent!=null)
        {
           Assertions.assertEquals(expectedParentSize,UncheckedSubListsize.getInt(parent));
           obj=parent;
        }
        obj=UncheckedSubListroot.get(obj);
      }
      catch(Exception e)
      {
        throw new RuntimeException(e);
      }
    }
    return (LongArrSeq.UncheckedList)obj;
  }
  private static LongArrSeq.CheckedList assertCheckedSubListIntegrity(int expectedModCount,int expectedParentSize,Object obj)
  {
    if(!(obj instanceof LongArrSeq.CheckedList))
    {
      try
      {
        Assertions.assertEquals(expectedModCount,CheckedSubListmodCount.getInt(obj));
        Object parent=CheckedSubListparent.get(obj);
        if(parent!=null)
        {
           Assertions.assertEquals(expectedModCount,CheckedSubListmodCount.getInt(parent));
           Assertions.assertEquals(expectedParentSize,CheckedSubListsize.getInt(parent));
           obj=parent;
        }
        obj=CheckedSubListroot.get(obj);
      }
      catch(Exception e)
      {
        throw new RuntimeException(e);
      }
    }
    LongArrSeq.CheckedList root=(LongArrSeq.CheckedList)obj;
    Assertions.assertEquals(expectedModCount,root.modCount);
    return root;
  }
  static LongArrSeq.UncheckedStack buildArrSeqStackUnchecked(int seqSize)
  {
    long[] arr;
    if(seqSize==0)
    {
      arr=OmniArray.OfLong.DEFAULT_ARR;
    }
    else
    {
      arr=new long[seqSize];
      for(int i=0;i<seqSize;++i)
      {
        arr[i]=TypeConversionUtil.convertTolong(i);
      }
    }
    return new LongArrSeq.UncheckedStack(seqSize,arr);
  }
  @Test
  public void testArrSeqStackUncheckedremoveIfLongPredicate()
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqStackUncheckedremoveIfLongPredicateHelper(buildArrSeqStackUnchecked(seqSize),new LongMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqStackUncheckedremoveIfLongPredicateHelper(buildArrSeqStackUnchecked(seqSize),new LongMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqStackUncheckedremoveIfLongPredicateHelper(buildArrSeqStackUnchecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqStackUncheckedremoveIfLongPredicateHelper(buildArrSeqStackUnchecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqStackUncheckedremoveIfLongPredicateHelper(buildArrSeqStackUnchecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqStackUncheckedremoveIfLongPredicateHelper(buildArrSeqStackUnchecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqStackUncheckedremoveIfLongPredicateHelper(buildArrSeqStackUnchecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqStackUncheckedremoveIfLongPredicateHelper(buildArrSeqStackUnchecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqStackUncheckedremoveIfLongPredicateHelper(buildArrSeqStackUnchecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqStackUncheckedremoveIfLongPredicateHelper(buildArrSeqStackUnchecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqStackUncheckedremoveIfLongPredicateHelper(buildArrSeqStackUnchecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqStackUncheckedremoveIfLongPredicateHelper(LongArrSeq.UncheckedStack seq,LongMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(LongArrSeq.UncheckedStack)seq.clone();
    int expectedSize;
    if(expectedException==null)
    {
      int seqSize=seq.size();
      if(numExpectedToBeRemoved<0)
      {
        var result=seq.removeIf((LongPredicate)pred);
        int numRemoved=pred.removedVals.size();
        Assertions.assertEquals(numRemoved!=0,result);
        expectedSize=seqSize-numRemoved;
      }
      else if(numExpectedToBeRemoved==0)
      {
        Assertions.assertFalse(seq.removeIf((LongPredicate)pred));
        Assertions.assertTrue(pred.removedVals.isEmpty());
        expectedSize=seqSize;
      }
      else
      {
        Assertions.assertTrue(seq.removeIf((LongPredicate)pred));
        expectedSize=seqSize-numExpectedToBeRemoved;
      }
      Assertions.assertEquals(expectedSize,seq.size());
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((LongPredicate)pred));
      if(ConcurrentModificationException.class.equals(expectedException))
      {
        Assertions.assertEquals(seq.size(),clone.size());
        for(var val:clone)
        {
          Assertions.assertTrue(seq.contains(val));
        }
        return;
      }
    }
    var cloneItr=clone.iterator();
    var seqItr=seq.iterator();
    for(;;)
    {
      if(!cloneItr.hasNext())
      {
        Assertions.assertFalse(seqItr.hasNext());
        return;
      }
      var v=cloneItr.nextLong();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextLong(),v);
    }
  }
  @Test
  public void testArrSeqStackUncheckedremoveIfPredicate()
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new LongMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new LongMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqStackUncheckedremoveIfPredicateHelper(LongArrSeq.UncheckedStack seq,LongMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(LongArrSeq.UncheckedStack)seq.clone();
    int expectedSize;
    if(expectedException==null)
    {
      int seqSize=seq.size();
      if(numExpectedToBeRemoved<0)
      {
        var result=seq.removeIf((Predicate)pred);
        int numRemoved=pred.removedVals.size();
        Assertions.assertEquals(numRemoved!=0,result);
        expectedSize=seqSize-numRemoved;
      }
      else if(numExpectedToBeRemoved==0)
      {
        Assertions.assertFalse(seq.removeIf((Predicate)pred));
        Assertions.assertTrue(pred.removedVals.isEmpty());
        expectedSize=seqSize;
      }
      else
      {
        Assertions.assertTrue(seq.removeIf((Predicate)pred));
        expectedSize=seqSize-numExpectedToBeRemoved;
      }
      Assertions.assertEquals(expectedSize,seq.size());
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((Predicate)pred));
      if(ConcurrentModificationException.class.equals(expectedException))
      {
        Assertions.assertEquals(seq.size(),clone.size());
        for(var val:clone)
        {
          Assertions.assertTrue(seq.contains(val));
        }
        return;
      }
    }
    var cloneItr=clone.iterator();
    var seqItr=seq.iterator();
    for(;;)
    {
      if(!cloneItr.hasNext())
      {
        Assertions.assertFalse(seqItr.hasNext());
        return;
      }
      var v=cloneItr.nextLong();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextLong(),v);
    }
  }
  static LongArrSeq.CheckedStack buildArrSeqStackChecked(int seqSize)
  {
    long[] arr;
    if(seqSize==0)
    {
      arr=OmniArray.OfLong.DEFAULT_ARR;
    }
    else
    {
      arr=new long[seqSize];
      for(int i=0;i<seqSize;++i)
      {
        arr[i]=TypeConversionUtil.convertTolong(i);
      }
    }
    return new LongArrSeq.CheckedStack(seqSize,arr);
  }
  @Test
  public void testArrSeqStackCheckedremoveIfLongPredicate()
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqStackCheckedremoveIfLongPredicateHelper(buildArrSeqStackChecked(seqSize),new LongMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqStackCheckedremoveIfLongPredicateHelper(buildArrSeqStackChecked(seqSize),new LongMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqStackCheckedremoveIfLongPredicateHelper(buildArrSeqStackChecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqStackCheckedremoveIfLongPredicateHelper(buildArrSeqStackChecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqStackCheckedremoveIfLongPredicateHelper(buildArrSeqStackChecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqStackCheckedremoveIfLongPredicateHelper(buildArrSeqStackChecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqStackCheckedremoveIfLongPredicateHelper(buildArrSeqStackChecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqStackCheckedremoveIfLongPredicateHelper(buildArrSeqStackChecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqStackCheckedremoveIfLongPredicateHelper(buildArrSeqStackChecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqStackCheckedremoveIfLongPredicateHelper(buildArrSeqStackChecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqStackCheckedremoveIfLongPredicateHelper(buildArrSeqStackChecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        testArrSeqStackCheckedremoveIfLongPredicateHelper(buildArrSeqStackChecked(seqSize),new LongMonitoredPredicate.Throwing(rand,seqSize),0,seqSize==0?null:IndexOutOfBoundsException.class);
        {
          var seq=buildArrSeqStackChecked(seqSize);
          testArrSeqStackCheckedremoveIfLongPredicateHelper(seq,new LongMonitoredPredicate.Modding(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqStackChecked(seqSize);
          testArrSeqStackCheckedremoveIfLongPredicateHelper(seq,new LongMonitoredPredicate.ModdingAndThrowing(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqStackCheckedremoveIfLongPredicateHelper(LongArrSeq.CheckedStack seq,LongMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(LongArrSeq.CheckedStack)seq.clone();
    int expectedSize;
    if(expectedException==null)
    {
      int seqSize=seq.size();
      if(numExpectedToBeRemoved<0)
      {
        var result=seq.removeIf((LongPredicate)pred);
        int numRemoved=pred.removedVals.size();
        Assertions.assertEquals(numRemoved!=0,result);
        expectedSize=seqSize-numRemoved;
      }
      else if(numExpectedToBeRemoved==0)
      {
        Assertions.assertFalse(seq.removeIf((LongPredicate)pred));
        Assertions.assertTrue(pred.removedVals.isEmpty());
        expectedSize=seqSize;
      }
      else
      {
        Assertions.assertTrue(seq.removeIf((LongPredicate)pred));
        expectedSize=seqSize-numExpectedToBeRemoved;
      }
      Assertions.assertEquals(expectedSize,seq.size());
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((LongPredicate)pred));
      if(ConcurrentModificationException.class.equals(expectedException))
      {
        Assertions.assertEquals(seq.size(),clone.size());
        for(var val:clone)
        {
          Assertions.assertTrue(seq.contains(val));
        }
        return;
      }
    }
    var cloneItr=clone.iterator();
    var seqItr=seq.iterator();
    for(;;)
    {
      if(!cloneItr.hasNext())
      {
        Assertions.assertFalse(seqItr.hasNext());
        return;
      }
      var v=cloneItr.nextLong();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextLong(),v);
    }
  }
  @Test
  public void testArrSeqStackCheckedremoveIfPredicate()
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new LongMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new LongMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new LongMonitoredPredicate.Throwing(rand,seqSize),0,seqSize==0?null:IndexOutOfBoundsException.class);
        {
          var seq=buildArrSeqStackChecked(seqSize);
          testArrSeqStackCheckedremoveIfPredicateHelper(seq,new LongMonitoredPredicate.Modding(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqStackChecked(seqSize);
          testArrSeqStackCheckedremoveIfPredicateHelper(seq,new LongMonitoredPredicate.ModdingAndThrowing(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqStackCheckedremoveIfPredicateHelper(LongArrSeq.CheckedStack seq,LongMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(LongArrSeq.CheckedStack)seq.clone();
    int expectedSize;
    if(expectedException==null)
    {
      int seqSize=seq.size();
      if(numExpectedToBeRemoved<0)
      {
        var result=seq.removeIf((Predicate)pred);
        int numRemoved=pred.removedVals.size();
        Assertions.assertEquals(numRemoved!=0,result);
        expectedSize=seqSize-numRemoved;
      }
      else if(numExpectedToBeRemoved==0)
      {
        Assertions.assertFalse(seq.removeIf((Predicate)pred));
        Assertions.assertTrue(pred.removedVals.isEmpty());
        expectedSize=seqSize;
      }
      else
      {
        Assertions.assertTrue(seq.removeIf((Predicate)pred));
        expectedSize=seqSize-numExpectedToBeRemoved;
      }
      Assertions.assertEquals(expectedSize,seq.size());
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((Predicate)pred));
      if(ConcurrentModificationException.class.equals(expectedException))
      {
        Assertions.assertEquals(seq.size(),clone.size());
        for(var val:clone)
        {
          Assertions.assertTrue(seq.contains(val));
        }
        return;
      }
    }
    var cloneItr=clone.iterator();
    var seqItr=seq.iterator();
    for(;;)
    {
      if(!cloneItr.hasNext())
      {
        Assertions.assertFalse(seqItr.hasNext());
        return;
      }
      var v=cloneItr.nextLong();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextLong(),v);
    }
  }
  static LongArrSeq.UncheckedList buildArrSeqListUnchecked(int seqSize)
  {
    long[] arr;
    if(seqSize==0)
    {
      arr=OmniArray.OfLong.DEFAULT_ARR;
    }
    else
    {
      arr=new long[seqSize];
      for(int i=0;i<seqSize;++i)
      {
        arr[i]=TypeConversionUtil.convertTolong(i);
      }
    }
    return new LongArrSeq.UncheckedList(seqSize,arr);
  }
  @Test
  public void testArrSeqListUncheckedremoveIfLongPredicate()
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqListUncheckedremoveIfLongPredicateHelper(buildArrSeqListUnchecked(seqSize),new LongMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqListUncheckedremoveIfLongPredicateHelper(buildArrSeqListUnchecked(seqSize),new LongMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqListUncheckedremoveIfLongPredicateHelper(buildArrSeqListUnchecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqListUncheckedremoveIfLongPredicateHelper(buildArrSeqListUnchecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqListUncheckedremoveIfLongPredicateHelper(buildArrSeqListUnchecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqListUncheckedremoveIfLongPredicateHelper(buildArrSeqListUnchecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqListUncheckedremoveIfLongPredicateHelper(buildArrSeqListUnchecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqListUncheckedremoveIfLongPredicateHelper(buildArrSeqListUnchecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqListUncheckedremoveIfLongPredicateHelper(buildArrSeqListUnchecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqListUncheckedremoveIfLongPredicateHelper(buildArrSeqListUnchecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqListUncheckedremoveIfLongPredicateHelper(buildArrSeqListUnchecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqListUncheckedremoveIfLongPredicateHelper(LongArrSeq.UncheckedList seq,LongMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(LongArrSeq.UncheckedList)seq.clone();
    int expectedSize;
    if(expectedException==null)
    {
      int seqSize=seq.size();
      if(numExpectedToBeRemoved<0)
      {
        var result=seq.removeIf((LongPredicate)pred);
        int numRemoved=pred.removedVals.size();
        Assertions.assertEquals(numRemoved!=0,result);
        expectedSize=seqSize-numRemoved;
      }
      else if(numExpectedToBeRemoved==0)
      {
        Assertions.assertFalse(seq.removeIf((LongPredicate)pred));
        Assertions.assertTrue(pred.removedVals.isEmpty());
        expectedSize=seqSize;
      }
      else
      {
        Assertions.assertTrue(seq.removeIf((LongPredicate)pred));
        expectedSize=seqSize-numExpectedToBeRemoved;
      }
      Assertions.assertEquals(expectedSize,seq.size());
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((LongPredicate)pred));
      if(ConcurrentModificationException.class.equals(expectedException))
      {
        Assertions.assertEquals(seq.size(),clone.size());
        for(var val:clone)
        {
          Assertions.assertTrue(seq.contains(val));
        }
        return;
      }
    }
    var cloneItr=clone.iterator();
    var seqItr=seq.iterator();
    for(;;)
    {
      if(!cloneItr.hasNext())
      {
        Assertions.assertFalse(seqItr.hasNext());
        return;
      }
      var v=cloneItr.nextLong();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextLong(),v);
    }
  }
  @Test
  public void testArrSeqListUncheckedremoveIfPredicate()
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new LongMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new LongMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqListUncheckedremoveIfPredicateHelper(LongArrSeq.UncheckedList seq,LongMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(LongArrSeq.UncheckedList)seq.clone();
    int expectedSize;
    if(expectedException==null)
    {
      int seqSize=seq.size();
      if(numExpectedToBeRemoved<0)
      {
        var result=seq.removeIf((Predicate)pred);
        int numRemoved=pred.removedVals.size();
        Assertions.assertEquals(numRemoved!=0,result);
        expectedSize=seqSize-numRemoved;
      }
      else if(numExpectedToBeRemoved==0)
      {
        Assertions.assertFalse(seq.removeIf((Predicate)pred));
        Assertions.assertTrue(pred.removedVals.isEmpty());
        expectedSize=seqSize;
      }
      else
      {
        Assertions.assertTrue(seq.removeIf((Predicate)pred));
        expectedSize=seqSize-numExpectedToBeRemoved;
      }
      Assertions.assertEquals(expectedSize,seq.size());
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((Predicate)pred));
      if(ConcurrentModificationException.class.equals(expectedException))
      {
        Assertions.assertEquals(seq.size(),clone.size());
        for(var val:clone)
        {
          Assertions.assertTrue(seq.contains(val));
        }
        return;
      }
    }
    var cloneItr=clone.iterator();
    var seqItr=seq.iterator();
    for(;;)
    {
      if(!cloneItr.hasNext())
      {
        Assertions.assertFalse(seqItr.hasNext());
        return;
      }
      var v=cloneItr.nextLong();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextLong(),v);
    }
  }
  static LongArrSeq.CheckedList buildArrSeqListChecked(int seqSize)
  {
    long[] arr;
    if(seqSize==0)
    {
      arr=OmniArray.OfLong.DEFAULT_ARR;
    }
    else
    {
      arr=new long[seqSize];
      for(int i=0;i<seqSize;++i)
      {
        arr[i]=TypeConversionUtil.convertTolong(i);
      }
    }
    return new LongArrSeq.CheckedList(seqSize,arr);
  }
  @Test
  public void testArrSeqListCheckedremoveIfLongPredicate()
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqListCheckedremoveIfLongPredicateHelper(buildArrSeqListChecked(seqSize),new LongMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqListCheckedremoveIfLongPredicateHelper(buildArrSeqListChecked(seqSize),new LongMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqListCheckedremoveIfLongPredicateHelper(buildArrSeqListChecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqListCheckedremoveIfLongPredicateHelper(buildArrSeqListChecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqListCheckedremoveIfLongPredicateHelper(buildArrSeqListChecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqListCheckedremoveIfLongPredicateHelper(buildArrSeqListChecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqListCheckedremoveIfLongPredicateHelper(buildArrSeqListChecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqListCheckedremoveIfLongPredicateHelper(buildArrSeqListChecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqListCheckedremoveIfLongPredicateHelper(buildArrSeqListChecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqListCheckedremoveIfLongPredicateHelper(buildArrSeqListChecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqListCheckedremoveIfLongPredicateHelper(buildArrSeqListChecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        testArrSeqListCheckedremoveIfLongPredicateHelper(buildArrSeqListChecked(seqSize),new LongMonitoredPredicate.Throwing(rand,seqSize),0,seqSize==0?null:IndexOutOfBoundsException.class);
        {
          var seq=buildArrSeqListChecked(seqSize);
          testArrSeqListCheckedremoveIfLongPredicateHelper(seq,new LongMonitoredPredicate.Modding(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqListChecked(seqSize);
          testArrSeqListCheckedremoveIfLongPredicateHelper(seq,new LongMonitoredPredicate.ModdingAndThrowing(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqListCheckedremoveIfLongPredicateHelper(LongArrSeq.CheckedList seq,LongMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(LongArrSeq.CheckedList)seq.clone();
    int expectedSize;
    if(expectedException==null)
    {
      int seqSize=seq.size();
      if(numExpectedToBeRemoved<0)
      {
        var result=seq.removeIf((LongPredicate)pred);
        int numRemoved=pred.removedVals.size();
        Assertions.assertEquals(numRemoved!=0,result);
        expectedSize=seqSize-numRemoved;
      }
      else if(numExpectedToBeRemoved==0)
      {
        Assertions.assertFalse(seq.removeIf((LongPredicate)pred));
        Assertions.assertTrue(pred.removedVals.isEmpty());
        expectedSize=seqSize;
      }
      else
      {
        Assertions.assertTrue(seq.removeIf((LongPredicate)pred));
        expectedSize=seqSize-numExpectedToBeRemoved;
      }
      Assertions.assertEquals(expectedSize,seq.size());
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((LongPredicate)pred));
      if(ConcurrentModificationException.class.equals(expectedException))
      {
        Assertions.assertEquals(seq.size(),clone.size());
        for(var val:clone)
        {
          Assertions.assertTrue(seq.contains(val));
        }
        return;
      }
    }
    var cloneItr=clone.iterator();
    var seqItr=seq.iterator();
    for(;;)
    {
      if(!cloneItr.hasNext())
      {
        Assertions.assertFalse(seqItr.hasNext());
        return;
      }
      var v=cloneItr.nextLong();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextLong(),v);
    }
  }
  @Test
  public void testArrSeqListCheckedremoveIfPredicate()
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new LongMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new LongMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new LongMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new LongMonitoredPredicate.Throwing(rand,seqSize),0,seqSize==0?null:IndexOutOfBoundsException.class);
        {
          var seq=buildArrSeqListChecked(seqSize);
          testArrSeqListCheckedremoveIfPredicateHelper(seq,new LongMonitoredPredicate.Modding(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqListChecked(seqSize);
          testArrSeqListCheckedremoveIfPredicateHelper(seq,new LongMonitoredPredicate.ModdingAndThrowing(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqListCheckedremoveIfPredicateHelper(LongArrSeq.CheckedList seq,LongMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(LongArrSeq.CheckedList)seq.clone();
    int expectedSize;
    if(expectedException==null)
    {
      int seqSize=seq.size();
      if(numExpectedToBeRemoved<0)
      {
        var result=seq.removeIf((Predicate)pred);
        int numRemoved=pred.removedVals.size();
        Assertions.assertEquals(numRemoved!=0,result);
        expectedSize=seqSize-numRemoved;
      }
      else if(numExpectedToBeRemoved==0)
      {
        Assertions.assertFalse(seq.removeIf((Predicate)pred));
        Assertions.assertTrue(pred.removedVals.isEmpty());
        expectedSize=seqSize;
      }
      else
      {
        Assertions.assertTrue(seq.removeIf((Predicate)pred));
        expectedSize=seqSize-numExpectedToBeRemoved;
      }
      Assertions.assertEquals(expectedSize,seq.size());
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((Predicate)pred));
      if(ConcurrentModificationException.class.equals(expectedException))
      {
        Assertions.assertEquals(seq.size(),clone.size());
        for(var val:clone)
        {
          Assertions.assertTrue(seq.contains(val));
        }
        return;
      }
    }
    var cloneItr=clone.iterator();
    var seqItr=seq.iterator();
    for(;;)
    {
      if(!cloneItr.hasNext())
      {
        Assertions.assertFalse(seqItr.hasNext());
        return;
      }
      var v=cloneItr.nextLong();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextLong(),v);
    }
  }
  static OmniList.OfLong buildArrSeqSubListUnchecked(int seqSize,int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc)
  {
    long[] arr;
    int rootSize=rootPreAlloc+parentPreAlloc+seqSize+parentPostAlloc+rootPostAlloc;
    if(rootSize==0)
    {
      arr=OmniArray.OfLong.DEFAULT_ARR;
    }
    else
    {
      arr=new long[rootSize];
      int dstOffset=0;
      for(int i=0;i<rootPreAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTolong(i-rootPreAlloc-parentPreAlloc);
      }
      for(int i=0;i<parentPreAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTolong(i-parentPreAlloc);
      }
      for(int i=0;i<seqSize;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTolong(i);
      }
      for(int i=0;i<parentPostAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTolong(seqSize+i);
      }
      for(int i=0;i<rootPostAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTolong(seqSize+parentPostAlloc+i);
      }
    }
    return new LongArrSeq.CheckedList(rootSize,arr).subList(rootPreAlloc,parentPreAlloc+seqSize+parentPostAlloc).subList(parentPreAlloc,parentPreAlloc+seqSize);
  }
  @ParameterizedTest(name="LongArrSeq.UncheckedSubList rootPreAlloc={0},parentPreAlloc={1},parentPostAlloc={2},rootPostAlloc={3}")
  @MethodSource("subListAllocationArgumentProvider")
  public void testArrSeqSubListUncheckedremoveIfLongPredicate(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc)
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqSubListUncheckedremoveIfLongPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqSubListUncheckedremoveIfLongPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqSubListUncheckedremoveIfLongPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqSubListUncheckedremoveIfLongPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqSubListUncheckedremoveIfLongPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqSubListUncheckedremoveIfLongPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqSubListUncheckedremoveIfLongPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqSubListUncheckedremoveIfLongPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqSubListUncheckedremoveIfLongPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqSubListUncheckedremoveIfLongPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqSubListUncheckedremoveIfLongPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqSubListUncheckedremoveIfLongPredicateHelper(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc,OmniList.OfLong seq,LongMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(LongArrSeq.UncheckedList)seq.clone();
    int expectedSize;
    LongArrSeq.UncheckedList root;
    int seqSize=seq.size();
    if(expectedException==null)
    {
      if(numExpectedToBeRemoved<0)
      {
        var result=seq.removeIf((LongPredicate)pred);
        int numRemoved=pred.removedVals.size();
        Assertions.assertEquals(numRemoved!=0,result);
        expectedSize=seqSize-numRemoved;
        root=assertUncheckedSubListIntegrity(numRemoved==0?0:1,parentPreAlloc+parentPostAlloc+expectedSize,seq);
      }
      else if(numExpectedToBeRemoved==0)
      {
        Assertions.assertFalse(seq.removeIf((LongPredicate)pred));
        Assertions.assertTrue(pred.removedVals.isEmpty());
        expectedSize=seqSize;
        root=assertUncheckedSubListIntegrity(0,parentPreAlloc+parentPostAlloc+expectedSize,seq);
      }
      else
      {
        Assertions.assertTrue(seq.removeIf((LongPredicate)pred));
        expectedSize=seqSize-numExpectedToBeRemoved;
        root=assertUncheckedSubListIntegrity(1,parentPreAlloc+parentPostAlloc+expectedSize,seq);
      }
      Assertions.assertEquals(expectedSize,seq.size());
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((LongPredicate)pred));
      if(ConcurrentModificationException.class.equals(expectedException))
      {
        return;
      }
      expectedSize=seq.size();
      root=assertUncheckedSubListIntegrity(0,parentPreAlloc+parentPostAlloc+expectedSize,seq);
    }
    var cloneItr=clone.iterator();
    var seqItr=seq.iterator();
    for(;;)
    {
      if(!cloneItr.hasNext())
      {
        Assertions.assertFalse(seqItr.hasNext());
        break;
      }
      var v=cloneItr.nextLong();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextLong(),v);
    }
    int dstOffset=0;
    for(int i=0;i<rootPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTolong(i-rootPreAlloc-parentPreAlloc));
    }
    for(int i=0;i<parentPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTolong(i-parentPreAlloc));
    }
    dstOffset+=expectedSize;
    for(int i=0;i<parentPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTolong(seqSize+i));
    }
    for(int i=0;i<rootPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTolong(seqSize+parentPostAlloc+i));
    }
  }
  @ParameterizedTest(name="LongArrSeq.UncheckedSubList rootPreAlloc={0},parentPreAlloc={1},parentPostAlloc={2},rootPostAlloc={3}")
  @MethodSource("subListAllocationArgumentProvider")
  public void testArrSeqSubListUncheckedremoveIfPredicate(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc)
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqSubListUncheckedremoveIfPredicateHelper(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc,OmniList.OfLong seq,LongMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(LongArrSeq.UncheckedList)seq.clone();
    int expectedSize;
    LongArrSeq.UncheckedList root;
    int seqSize=seq.size();
    if(expectedException==null)
    {
      if(numExpectedToBeRemoved<0)
      {
        var result=seq.removeIf((Predicate)pred);
        int numRemoved=pred.removedVals.size();
        Assertions.assertEquals(numRemoved!=0,result);
        expectedSize=seqSize-numRemoved;
        root=assertUncheckedSubListIntegrity(numRemoved==0?0:1,parentPreAlloc+parentPostAlloc+expectedSize,seq);
      }
      else if(numExpectedToBeRemoved==0)
      {
        Assertions.assertFalse(seq.removeIf((Predicate)pred));
        Assertions.assertTrue(pred.removedVals.isEmpty());
        expectedSize=seqSize;
        root=assertUncheckedSubListIntegrity(0,parentPreAlloc+parentPostAlloc+expectedSize,seq);
      }
      else
      {
        Assertions.assertTrue(seq.removeIf((Predicate)pred));
        expectedSize=seqSize-numExpectedToBeRemoved;
        root=assertUncheckedSubListIntegrity(1,parentPreAlloc+parentPostAlloc+expectedSize,seq);
      }
      Assertions.assertEquals(expectedSize,seq.size());
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((Predicate)pred));
      if(ConcurrentModificationException.class.equals(expectedException))
      {
        return;
      }
      expectedSize=seq.size();
      root=assertUncheckedSubListIntegrity(0,parentPreAlloc+parentPostAlloc+expectedSize,seq);
    }
    var cloneItr=clone.iterator();
    var seqItr=seq.iterator();
    for(;;)
    {
      if(!cloneItr.hasNext())
      {
        Assertions.assertFalse(seqItr.hasNext());
        break;
      }
      var v=cloneItr.nextLong();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextLong(),v);
    }
    int dstOffset=0;
    for(int i=0;i<rootPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTolong(i-rootPreAlloc-parentPreAlloc));
    }
    for(int i=0;i<parentPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTolong(i-parentPreAlloc));
    }
    dstOffset+=expectedSize;
    for(int i=0;i<parentPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTolong(seqSize+i));
    }
    for(int i=0;i<rootPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTolong(seqSize+parentPostAlloc+i));
    }
  }
  static OmniList.OfLong buildArrSeqSubListChecked(int seqSize,int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc)
  {
    long[] arr;
    int rootSize=rootPreAlloc+parentPreAlloc+seqSize+parentPostAlloc+rootPostAlloc;
    if(rootSize==0)
    {
      arr=OmniArray.OfLong.DEFAULT_ARR;
    }
    else
    {
      arr=new long[rootSize];
      int dstOffset=0;
      for(int i=0;i<rootPreAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTolong(i-rootPreAlloc-parentPreAlloc);
      }
      for(int i=0;i<parentPreAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTolong(i-parentPreAlloc);
      }
      for(int i=0;i<seqSize;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTolong(i);
      }
      for(int i=0;i<parentPostAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTolong(seqSize+i);
      }
      for(int i=0;i<rootPostAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTolong(seqSize+parentPostAlloc+i);
      }
    }
    return new LongArrSeq.CheckedList(rootSize,arr).subList(rootPreAlloc,parentPreAlloc+seqSize+parentPostAlloc).subList(parentPreAlloc,parentPreAlloc+seqSize);
  }
  @ParameterizedTest(name="LongArrSeq.CheckedSubList rootPreAlloc={0},parentPreAlloc={1},parentPostAlloc={2},rootPostAlloc={3}")
  @MethodSource("subListAllocationArgumentProvider")
  public void testArrSeqSubListCheckedremoveIfLongPredicate(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc)
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqSubListCheckedremoveIfLongPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqSubListCheckedremoveIfLongPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqSubListCheckedremoveIfLongPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqSubListCheckedremoveIfLongPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqSubListCheckedremoveIfLongPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqSubListCheckedremoveIfLongPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqSubListCheckedremoveIfLongPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqSubListCheckedremoveIfLongPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqSubListCheckedremoveIfLongPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqSubListCheckedremoveIfLongPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqSubListCheckedremoveIfLongPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        testArrSeqSubListCheckedremoveIfLongPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.Throwing(rand,seqSize),0,seqSize==0?null:IndexOutOfBoundsException.class);
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfLongPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new LongMonitoredPredicate.Modding(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfLongPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new LongMonitoredPredicate.ModdingAndThrowing(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfLongPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new LongMonitoredPredicate.Modding(rand,seqSize,getParent(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfLongPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new LongMonitoredPredicate.ModdingAndThrowing(rand,seqSize,getParent(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfLongPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new LongMonitoredPredicate.Modding(rand,seqSize,getRoot(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfLongPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new LongMonitoredPredicate.ModdingAndThrowing(rand,seqSize,getRoot(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqSubListCheckedremoveIfLongPredicateHelper(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc,OmniList.OfLong seq,LongMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(LongArrSeq.CheckedList)seq.clone();
    int expectedSize;
    LongArrSeq.CheckedList root;
    int seqSize=seq.size();
    if(expectedException==null)
    {
      if(numExpectedToBeRemoved<0)
      {
        var result=seq.removeIf((LongPredicate)pred);
        int numRemoved=pred.removedVals.size();
        Assertions.assertEquals(numRemoved!=0,result);
        expectedSize=seqSize-numRemoved;
        root=assertCheckedSubListIntegrity(numRemoved==0?0:1,parentPreAlloc+parentPostAlloc+expectedSize,seq);
      }
      else if(numExpectedToBeRemoved==0)
      {
        Assertions.assertFalse(seq.removeIf((LongPredicate)pred));
        Assertions.assertTrue(pred.removedVals.isEmpty());
        expectedSize=seqSize;
        root=assertCheckedSubListIntegrity(0,parentPreAlloc+parentPostAlloc+expectedSize,seq);
      }
      else
      {
        Assertions.assertTrue(seq.removeIf((LongPredicate)pred));
        expectedSize=seqSize-numExpectedToBeRemoved;
        root=assertCheckedSubListIntegrity(1,parentPreAlloc+parentPostAlloc+expectedSize,seq);
      }
      Assertions.assertEquals(expectedSize,seq.size());
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((LongPredicate)pred));
      if(ConcurrentModificationException.class.equals(expectedException))
      {
        return;
      }
      expectedSize=seq.size();
      root=assertCheckedSubListIntegrity(0,parentPreAlloc+parentPostAlloc+expectedSize,seq);
    }
    var cloneItr=clone.iterator();
    var seqItr=seq.iterator();
    for(;;)
    {
      if(!cloneItr.hasNext())
      {
        Assertions.assertFalse(seqItr.hasNext());
        break;
      }
      var v=cloneItr.nextLong();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextLong(),v);
    }
    int dstOffset=0;
    for(int i=0;i<rootPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTolong(i-rootPreAlloc-parentPreAlloc));
    }
    for(int i=0;i<parentPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTolong(i-parentPreAlloc));
    }
    dstOffset+=expectedSize;
    for(int i=0;i<parentPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTolong(seqSize+i));
    }
    for(int i=0;i<rootPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTolong(seqSize+parentPostAlloc+i));
    }
  }
  @ParameterizedTest(name="LongArrSeq.CheckedSubList rootPreAlloc={0},parentPreAlloc={1},parentPostAlloc={2},rootPostAlloc={3}")
  @MethodSource("subListAllocationArgumentProvider")
  public void testArrSeqSubListCheckedremoveIfPredicate(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc)
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new LongMonitoredPredicate.Throwing(rand,seqSize),0,seqSize==0?null:IndexOutOfBoundsException.class);
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new LongMonitoredPredicate.Modding(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new LongMonitoredPredicate.ModdingAndThrowing(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new LongMonitoredPredicate.Modding(rand,seqSize,getParent(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new LongMonitoredPredicate.ModdingAndThrowing(rand,seqSize,getParent(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new LongMonitoredPredicate.Modding(rand,seqSize,getRoot(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new LongMonitoredPredicate.ModdingAndThrowing(rand,seqSize,getRoot(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqSubListCheckedremoveIfPredicateHelper(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc,OmniList.OfLong seq,LongMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(LongArrSeq.CheckedList)seq.clone();
    int expectedSize;
    LongArrSeq.CheckedList root;
    int seqSize=seq.size();
    if(expectedException==null)
    {
      if(numExpectedToBeRemoved<0)
      {
        var result=seq.removeIf((Predicate)pred);
        int numRemoved=pred.removedVals.size();
        Assertions.assertEquals(numRemoved!=0,result);
        expectedSize=seqSize-numRemoved;
        root=assertCheckedSubListIntegrity(numRemoved==0?0:1,parentPreAlloc+parentPostAlloc+expectedSize,seq);
      }
      else if(numExpectedToBeRemoved==0)
      {
        Assertions.assertFalse(seq.removeIf((Predicate)pred));
        Assertions.assertTrue(pred.removedVals.isEmpty());
        expectedSize=seqSize;
        root=assertCheckedSubListIntegrity(0,parentPreAlloc+parentPostAlloc+expectedSize,seq);
      }
      else
      {
        Assertions.assertTrue(seq.removeIf((Predicate)pred));
        expectedSize=seqSize-numExpectedToBeRemoved;
        root=assertCheckedSubListIntegrity(1,parentPreAlloc+parentPostAlloc+expectedSize,seq);
      }
      Assertions.assertEquals(expectedSize,seq.size());
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((Predicate)pred));
      if(ConcurrentModificationException.class.equals(expectedException))
      {
        return;
      }
      expectedSize=seq.size();
      root=assertCheckedSubListIntegrity(0,parentPreAlloc+parentPostAlloc+expectedSize,seq);
    }
    var cloneItr=clone.iterator();
    var seqItr=seq.iterator();
    for(;;)
    {
      if(!cloneItr.hasNext())
      {
        Assertions.assertFalse(seqItr.hasNext());
        break;
      }
      var v=cloneItr.nextLong();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextLong(),v);
    }
    int dstOffset=0;
    for(int i=0;i<rootPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTolong(i-rootPreAlloc-parentPreAlloc));
    }
    for(int i=0;i<parentPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTolong(i-parentPreAlloc));
    }
    dstOffset+=expectedSize;
    for(int i=0;i<parentPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTolong(seqSize+i));
    }
    for(int i=0;i<rootPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTolong(seqSize+parentPostAlloc+i));
    }
  }
  static Stream<Arguments> subListAllocationArgumentProvider()
  {
    Arguments[] args=new Arguments[16];
    args[0]=Arguments.of(0,0,0,0);
    args[1]=Arguments.of(0,0,0,5);
    args[2]=Arguments.of(0,0,5,0);
    args[3]=Arguments.of(0,0,5,5);
    args[4]=Arguments.of(0,5,0,0);
    args[5]=Arguments.of(0,5,0,5);
    args[6]=Arguments.of(0,5,5,0);
    args[7]=Arguments.of(0,5,5,5);
    args[8]=Arguments.of(5,0,0,0);
    args[9]=Arguments.of(5,0,0,5);
    args[10]=Arguments.of(5,0,5,0);
    args[11]=Arguments.of(5,0,5,5);
    args[12]=Arguments.of(5,5,0,0);
    args[13]=Arguments.of(5,5,0,5);
    args[14]=Arguments.of(5,5,5,0);
    args[15]=Arguments.of(5,5,5,5);
    return Stream.of(args);
  }
}
