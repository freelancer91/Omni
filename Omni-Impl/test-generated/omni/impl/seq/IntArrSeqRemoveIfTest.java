package omni.impl.seq;
import java.util.function.Predicate;
import org.junit.jupiter.api.Assertions;
import java.util.ConcurrentModificationException;
import omni.util.TypeConversionUtil;
import omni.util.OmniArray;
import java.util.Random;
import org.junit.jupiter.api.Test;
import omni.impl.IntMonitoredPredicate;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.function.IntPredicate;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import omni.api.OmniList;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class IntArrSeqRemoveIfTest
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
        Class<?> clazz=new IntArrSeq.CheckedList().subList(0,0).getClass();
        (CheckedSubListmodCount=clazz.getDeclaredField("modCount")).setAccessible(true);
        (CheckedSubListparent=clazz.getDeclaredField("parent")).setAccessible(true);
        modifiersField.setInt(CheckedSubListparent,CheckedSubListparent.getModifiers()&~Modifier.FINAL);
        (CheckedSubListroot=clazz.getDeclaredField("root")).setAccessible(true);
        modifiersField.setInt(CheckedSubListroot,CheckedSubListroot.getModifiers()&~Modifier.FINAL);
        (CheckedSubListsize=clazz.getDeclaredField("size")).setAccessible(true);
      }
      {
        Class<?> clazz=new IntArrSeq.UncheckedList().subList(0,0).getClass();
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
  private static IntArrSeq.CheckedList getRoot(Object seq)
  {
    try
    {
      return (IntArrSeq.CheckedList)CheckedSubListroot.get(seq);
    }
    catch(Exception e)
    {
      throw new RuntimeException(e);
    }
  }
  private static OmniList.OfInt getParent(Object seq)
  {
    try
    {
      return (OmniList.OfInt)CheckedSubListparent.get(seq);
    }
    catch(Exception e)
    {
      throw new RuntimeException(e);
    }
  }
  private static IntArrSeq.UncheckedList assertUncheckedSubListIntegrity(int expectedModCount,int expectedParentSize,Object obj)
  {
    if(!(obj instanceof IntArrSeq.UncheckedList))
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
    return (IntArrSeq.UncheckedList)obj;
  }
  private static IntArrSeq.CheckedList assertCheckedSubListIntegrity(int expectedModCount,int expectedParentSize,Object obj)
  {
    if(!(obj instanceof IntArrSeq.CheckedList))
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
    IntArrSeq.CheckedList root=(IntArrSeq.CheckedList)obj;
    Assertions.assertEquals(expectedModCount,root.modCount);
    return root;
  }
  static IntArrSeq.UncheckedStack buildArrSeqStackUnchecked(int seqSize)
  {
    int[] arr;
    if(seqSize==0)
    {
      arr=OmniArray.OfInt.DEFAULT_ARR;
    }
    else
    {
      arr=new int[seqSize];
      for(int i=0;i<seqSize;++i)
      {
        arr[i]=TypeConversionUtil.convertToint(i);
      }
    }
    return new IntArrSeq.UncheckedStack(seqSize,arr);
  }
  @Test
  public void testArrSeqStackUncheckedremoveIfIntPredicate()
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqStackUncheckedremoveIfIntPredicateHelper(buildArrSeqStackUnchecked(seqSize),new IntMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqStackUncheckedremoveIfIntPredicateHelper(buildArrSeqStackUnchecked(seqSize),new IntMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqStackUncheckedremoveIfIntPredicateHelper(buildArrSeqStackUnchecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqStackUncheckedremoveIfIntPredicateHelper(buildArrSeqStackUnchecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqStackUncheckedremoveIfIntPredicateHelper(buildArrSeqStackUnchecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqStackUncheckedremoveIfIntPredicateHelper(buildArrSeqStackUnchecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqStackUncheckedremoveIfIntPredicateHelper(buildArrSeqStackUnchecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqStackUncheckedremoveIfIntPredicateHelper(buildArrSeqStackUnchecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqStackUncheckedremoveIfIntPredicateHelper(buildArrSeqStackUnchecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqStackUncheckedremoveIfIntPredicateHelper(buildArrSeqStackUnchecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqStackUncheckedremoveIfIntPredicateHelper(buildArrSeqStackUnchecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqStackUncheckedremoveIfIntPredicateHelper(IntArrSeq.UncheckedStack seq,IntMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(IntArrSeq.UncheckedStack)seq.clone();
    int expectedSize;
    if(expectedException==null)
    {
      int seqSize=seq.size();
      if(numExpectedToBeRemoved<0)
      {
        var result=seq.removeIf((IntPredicate)pred);
        int numRemoved=pred.removedVals.size();
        Assertions.assertEquals(numRemoved!=0,result);
        expectedSize=seqSize-numRemoved;
      }
      else if(numExpectedToBeRemoved==0)
      {
        Assertions.assertFalse(seq.removeIf((IntPredicate)pred));
        Assertions.assertTrue(pred.removedVals.isEmpty());
        expectedSize=seqSize;
      }
      else
      {
        Assertions.assertTrue(seq.removeIf((IntPredicate)pred));
        expectedSize=seqSize-numExpectedToBeRemoved;
      }
      Assertions.assertEquals(expectedSize,seq.size());
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((IntPredicate)pred));
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
      var v=cloneItr.nextInt();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextInt(),v);
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
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new IntMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new IntMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqStackUncheckedremoveIfPredicateHelper(IntArrSeq.UncheckedStack seq,IntMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(IntArrSeq.UncheckedStack)seq.clone();
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
      var v=cloneItr.nextInt();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextInt(),v);
    }
  }
  static IntArrSeq.CheckedStack buildArrSeqStackChecked(int seqSize)
  {
    int[] arr;
    if(seqSize==0)
    {
      arr=OmniArray.OfInt.DEFAULT_ARR;
    }
    else
    {
      arr=new int[seqSize];
      for(int i=0;i<seqSize;++i)
      {
        arr[i]=TypeConversionUtil.convertToint(i);
      }
    }
    return new IntArrSeq.CheckedStack(seqSize,arr);
  }
  @Test
  public void testArrSeqStackCheckedremoveIfIntPredicate()
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqStackCheckedremoveIfIntPredicateHelper(buildArrSeqStackChecked(seqSize),new IntMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqStackCheckedremoveIfIntPredicateHelper(buildArrSeqStackChecked(seqSize),new IntMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqStackCheckedremoveIfIntPredicateHelper(buildArrSeqStackChecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqStackCheckedremoveIfIntPredicateHelper(buildArrSeqStackChecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqStackCheckedremoveIfIntPredicateHelper(buildArrSeqStackChecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqStackCheckedremoveIfIntPredicateHelper(buildArrSeqStackChecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqStackCheckedremoveIfIntPredicateHelper(buildArrSeqStackChecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqStackCheckedremoveIfIntPredicateHelper(buildArrSeqStackChecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqStackCheckedremoveIfIntPredicateHelper(buildArrSeqStackChecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqStackCheckedremoveIfIntPredicateHelper(buildArrSeqStackChecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqStackCheckedremoveIfIntPredicateHelper(buildArrSeqStackChecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        testArrSeqStackCheckedremoveIfIntPredicateHelper(buildArrSeqStackChecked(seqSize),new IntMonitoredPredicate.Throwing(rand,seqSize),0,seqSize==0?null:IndexOutOfBoundsException.class);
        {
          var seq=buildArrSeqStackChecked(seqSize);
          testArrSeqStackCheckedremoveIfIntPredicateHelper(seq,new IntMonitoredPredicate.Modding(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqStackChecked(seqSize);
          testArrSeqStackCheckedremoveIfIntPredicateHelper(seq,new IntMonitoredPredicate.ModdingAndThrowing(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqStackCheckedremoveIfIntPredicateHelper(IntArrSeq.CheckedStack seq,IntMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(IntArrSeq.CheckedStack)seq.clone();
    int expectedSize;
    if(expectedException==null)
    {
      int seqSize=seq.size();
      if(numExpectedToBeRemoved<0)
      {
        var result=seq.removeIf((IntPredicate)pred);
        int numRemoved=pred.removedVals.size();
        Assertions.assertEquals(numRemoved!=0,result);
        expectedSize=seqSize-numRemoved;
      }
      else if(numExpectedToBeRemoved==0)
      {
        Assertions.assertFalse(seq.removeIf((IntPredicate)pred));
        Assertions.assertTrue(pred.removedVals.isEmpty());
        expectedSize=seqSize;
      }
      else
      {
        Assertions.assertTrue(seq.removeIf((IntPredicate)pred));
        expectedSize=seqSize-numExpectedToBeRemoved;
      }
      Assertions.assertEquals(expectedSize,seq.size());
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((IntPredicate)pred));
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
      var v=cloneItr.nextInt();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextInt(),v);
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
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new IntMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new IntMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new IntMonitoredPredicate.Throwing(rand,seqSize),0,seqSize==0?null:IndexOutOfBoundsException.class);
        {
          var seq=buildArrSeqStackChecked(seqSize);
          testArrSeqStackCheckedremoveIfPredicateHelper(seq,new IntMonitoredPredicate.Modding(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqStackChecked(seqSize);
          testArrSeqStackCheckedremoveIfPredicateHelper(seq,new IntMonitoredPredicate.ModdingAndThrowing(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqStackCheckedremoveIfPredicateHelper(IntArrSeq.CheckedStack seq,IntMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(IntArrSeq.CheckedStack)seq.clone();
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
      var v=cloneItr.nextInt();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextInt(),v);
    }
  }
  static IntArrSeq.UncheckedList buildArrSeqListUnchecked(int seqSize)
  {
    int[] arr;
    if(seqSize==0)
    {
      arr=OmniArray.OfInt.DEFAULT_ARR;
    }
    else
    {
      arr=new int[seqSize];
      for(int i=0;i<seqSize;++i)
      {
        arr[i]=TypeConversionUtil.convertToint(i);
      }
    }
    return new IntArrSeq.UncheckedList(seqSize,arr);
  }
  @Test
  public void testArrSeqListUncheckedremoveIfIntPredicate()
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqListUncheckedremoveIfIntPredicateHelper(buildArrSeqListUnchecked(seqSize),new IntMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqListUncheckedremoveIfIntPredicateHelper(buildArrSeqListUnchecked(seqSize),new IntMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqListUncheckedremoveIfIntPredicateHelper(buildArrSeqListUnchecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqListUncheckedremoveIfIntPredicateHelper(buildArrSeqListUnchecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqListUncheckedremoveIfIntPredicateHelper(buildArrSeqListUnchecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqListUncheckedremoveIfIntPredicateHelper(buildArrSeqListUnchecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqListUncheckedremoveIfIntPredicateHelper(buildArrSeqListUnchecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqListUncheckedremoveIfIntPredicateHelper(buildArrSeqListUnchecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqListUncheckedremoveIfIntPredicateHelper(buildArrSeqListUnchecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqListUncheckedremoveIfIntPredicateHelper(buildArrSeqListUnchecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqListUncheckedremoveIfIntPredicateHelper(buildArrSeqListUnchecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqListUncheckedremoveIfIntPredicateHelper(IntArrSeq.UncheckedList seq,IntMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(IntArrSeq.UncheckedList)seq.clone();
    int expectedSize;
    if(expectedException==null)
    {
      int seqSize=seq.size();
      if(numExpectedToBeRemoved<0)
      {
        var result=seq.removeIf((IntPredicate)pred);
        int numRemoved=pred.removedVals.size();
        Assertions.assertEquals(numRemoved!=0,result);
        expectedSize=seqSize-numRemoved;
      }
      else if(numExpectedToBeRemoved==0)
      {
        Assertions.assertFalse(seq.removeIf((IntPredicate)pred));
        Assertions.assertTrue(pred.removedVals.isEmpty());
        expectedSize=seqSize;
      }
      else
      {
        Assertions.assertTrue(seq.removeIf((IntPredicate)pred));
        expectedSize=seqSize-numExpectedToBeRemoved;
      }
      Assertions.assertEquals(expectedSize,seq.size());
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((IntPredicate)pred));
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
      var v=cloneItr.nextInt();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextInt(),v);
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
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new IntMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new IntMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqListUncheckedremoveIfPredicateHelper(IntArrSeq.UncheckedList seq,IntMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(IntArrSeq.UncheckedList)seq.clone();
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
      var v=cloneItr.nextInt();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextInt(),v);
    }
  }
  static IntArrSeq.CheckedList buildArrSeqListChecked(int seqSize)
  {
    int[] arr;
    if(seqSize==0)
    {
      arr=OmniArray.OfInt.DEFAULT_ARR;
    }
    else
    {
      arr=new int[seqSize];
      for(int i=0;i<seqSize;++i)
      {
        arr[i]=TypeConversionUtil.convertToint(i);
      }
    }
    return new IntArrSeq.CheckedList(seqSize,arr);
  }
  @Test
  public void testArrSeqListCheckedremoveIfIntPredicate()
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqListCheckedremoveIfIntPredicateHelper(buildArrSeqListChecked(seqSize),new IntMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqListCheckedremoveIfIntPredicateHelper(buildArrSeqListChecked(seqSize),new IntMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqListCheckedremoveIfIntPredicateHelper(buildArrSeqListChecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqListCheckedremoveIfIntPredicateHelper(buildArrSeqListChecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqListCheckedremoveIfIntPredicateHelper(buildArrSeqListChecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqListCheckedremoveIfIntPredicateHelper(buildArrSeqListChecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqListCheckedremoveIfIntPredicateHelper(buildArrSeqListChecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqListCheckedremoveIfIntPredicateHelper(buildArrSeqListChecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqListCheckedremoveIfIntPredicateHelper(buildArrSeqListChecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqListCheckedremoveIfIntPredicateHelper(buildArrSeqListChecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqListCheckedremoveIfIntPredicateHelper(buildArrSeqListChecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        testArrSeqListCheckedremoveIfIntPredicateHelper(buildArrSeqListChecked(seqSize),new IntMonitoredPredicate.Throwing(rand,seqSize),0,seqSize==0?null:IndexOutOfBoundsException.class);
        {
          var seq=buildArrSeqListChecked(seqSize);
          testArrSeqListCheckedremoveIfIntPredicateHelper(seq,new IntMonitoredPredicate.Modding(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqListChecked(seqSize);
          testArrSeqListCheckedremoveIfIntPredicateHelper(seq,new IntMonitoredPredicate.ModdingAndThrowing(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqListCheckedremoveIfIntPredicateHelper(IntArrSeq.CheckedList seq,IntMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(IntArrSeq.CheckedList)seq.clone();
    int expectedSize;
    if(expectedException==null)
    {
      int seqSize=seq.size();
      if(numExpectedToBeRemoved<0)
      {
        var result=seq.removeIf((IntPredicate)pred);
        int numRemoved=pred.removedVals.size();
        Assertions.assertEquals(numRemoved!=0,result);
        expectedSize=seqSize-numRemoved;
      }
      else if(numExpectedToBeRemoved==0)
      {
        Assertions.assertFalse(seq.removeIf((IntPredicate)pred));
        Assertions.assertTrue(pred.removedVals.isEmpty());
        expectedSize=seqSize;
      }
      else
      {
        Assertions.assertTrue(seq.removeIf((IntPredicate)pred));
        expectedSize=seqSize-numExpectedToBeRemoved;
      }
      Assertions.assertEquals(expectedSize,seq.size());
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((IntPredicate)pred));
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
      var v=cloneItr.nextInt();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextInt(),v);
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
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new IntMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new IntMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new IntMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new IntMonitoredPredicate.Throwing(rand,seqSize),0,seqSize==0?null:IndexOutOfBoundsException.class);
        {
          var seq=buildArrSeqListChecked(seqSize);
          testArrSeqListCheckedremoveIfPredicateHelper(seq,new IntMonitoredPredicate.Modding(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqListChecked(seqSize);
          testArrSeqListCheckedremoveIfPredicateHelper(seq,new IntMonitoredPredicate.ModdingAndThrowing(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqListCheckedremoveIfPredicateHelper(IntArrSeq.CheckedList seq,IntMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(IntArrSeq.CheckedList)seq.clone();
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
      var v=cloneItr.nextInt();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextInt(),v);
    }
  }
  static OmniList.OfInt buildArrSeqSubListUnchecked(int seqSize,int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc)
  {
    int[] arr;
    int rootSize=rootPreAlloc+parentPreAlloc+seqSize+parentPostAlloc+rootPostAlloc;
    if(rootSize==0)
    {
      arr=OmniArray.OfInt.DEFAULT_ARR;
    }
    else
    {
      arr=new int[rootSize];
      int dstOffset=0;
      for(int i=0;i<rootPreAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertToint(i-rootPreAlloc-parentPreAlloc);
      }
      for(int i=0;i<parentPreAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertToint(i-parentPreAlloc);
      }
      for(int i=0;i<seqSize;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertToint(i);
      }
      for(int i=0;i<parentPostAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertToint(seqSize+i);
      }
      for(int i=0;i<rootPostAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertToint(seqSize+parentPostAlloc+i);
      }
    }
    return new IntArrSeq.CheckedList(rootSize,arr).subList(rootPreAlloc,parentPreAlloc+seqSize+parentPostAlloc).subList(parentPreAlloc,parentPreAlloc+seqSize);
  }
  @ParameterizedTest(name="IntArrSeq.UncheckedSubList rootPreAlloc={0},parentPreAlloc={1},parentPostAlloc={2},rootPostAlloc={3}")
  @MethodSource("subListAllocationArgumentProvider")
  public void testArrSeqSubListUncheckedremoveIfIntPredicate(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc)
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqSubListUncheckedremoveIfIntPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqSubListUncheckedremoveIfIntPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqSubListUncheckedremoveIfIntPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqSubListUncheckedremoveIfIntPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqSubListUncheckedremoveIfIntPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqSubListUncheckedremoveIfIntPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqSubListUncheckedremoveIfIntPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqSubListUncheckedremoveIfIntPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqSubListUncheckedremoveIfIntPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqSubListUncheckedremoveIfIntPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqSubListUncheckedremoveIfIntPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqSubListUncheckedremoveIfIntPredicateHelper(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc,OmniList.OfInt seq,IntMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(IntArrSeq.UncheckedList)seq.clone();
    int expectedSize;
    IntArrSeq.UncheckedList root;
    int seqSize=seq.size();
    if(expectedException==null)
    {
      if(numExpectedToBeRemoved<0)
      {
        var result=seq.removeIf((IntPredicate)pred);
        int numRemoved=pred.removedVals.size();
        Assertions.assertEquals(numRemoved!=0,result);
        expectedSize=seqSize-numRemoved;
        root=assertUncheckedSubListIntegrity(numRemoved==0?0:1,parentPreAlloc+parentPostAlloc+expectedSize,seq);
      }
      else if(numExpectedToBeRemoved==0)
      {
        Assertions.assertFalse(seq.removeIf((IntPredicate)pred));
        Assertions.assertTrue(pred.removedVals.isEmpty());
        expectedSize=seqSize;
        root=assertUncheckedSubListIntegrity(0,parentPreAlloc+parentPostAlloc+expectedSize,seq);
      }
      else
      {
        Assertions.assertTrue(seq.removeIf((IntPredicate)pred));
        expectedSize=seqSize-numExpectedToBeRemoved;
        root=assertUncheckedSubListIntegrity(1,parentPreAlloc+parentPostAlloc+expectedSize,seq);
      }
      Assertions.assertEquals(expectedSize,seq.size());
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((IntPredicate)pred));
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
      var v=cloneItr.nextInt();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextInt(),v);
    }
    int dstOffset=0;
    for(int i=0;i<rootPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertToint(i-rootPreAlloc-parentPreAlloc));
    }
    for(int i=0;i<parentPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertToint(i-parentPreAlloc));
    }
    dstOffset+=expectedSize;
    for(int i=0;i<parentPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertToint(seqSize+i));
    }
    for(int i=0;i<rootPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertToint(seqSize+parentPostAlloc+i));
    }
  }
  @ParameterizedTest(name="IntArrSeq.UncheckedSubList rootPreAlloc={0},parentPreAlloc={1},parentPostAlloc={2},rootPostAlloc={3}")
  @MethodSource("subListAllocationArgumentProvider")
  public void testArrSeqSubListUncheckedremoveIfPredicate(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc)
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqSubListUncheckedremoveIfPredicateHelper(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc,OmniList.OfInt seq,IntMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(IntArrSeq.UncheckedList)seq.clone();
    int expectedSize;
    IntArrSeq.UncheckedList root;
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
      var v=cloneItr.nextInt();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextInt(),v);
    }
    int dstOffset=0;
    for(int i=0;i<rootPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertToint(i-rootPreAlloc-parentPreAlloc));
    }
    for(int i=0;i<parentPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertToint(i-parentPreAlloc));
    }
    dstOffset+=expectedSize;
    for(int i=0;i<parentPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertToint(seqSize+i));
    }
    for(int i=0;i<rootPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertToint(seqSize+parentPostAlloc+i));
    }
  }
  static OmniList.OfInt buildArrSeqSubListChecked(int seqSize,int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc)
  {
    int[] arr;
    int rootSize=rootPreAlloc+parentPreAlloc+seqSize+parentPostAlloc+rootPostAlloc;
    if(rootSize==0)
    {
      arr=OmniArray.OfInt.DEFAULT_ARR;
    }
    else
    {
      arr=new int[rootSize];
      int dstOffset=0;
      for(int i=0;i<rootPreAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertToint(i-rootPreAlloc-parentPreAlloc);
      }
      for(int i=0;i<parentPreAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertToint(i-parentPreAlloc);
      }
      for(int i=0;i<seqSize;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertToint(i);
      }
      for(int i=0;i<parentPostAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertToint(seqSize+i);
      }
      for(int i=0;i<rootPostAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertToint(seqSize+parentPostAlloc+i);
      }
    }
    return new IntArrSeq.CheckedList(rootSize,arr).subList(rootPreAlloc,parentPreAlloc+seqSize+parentPostAlloc).subList(parentPreAlloc,parentPreAlloc+seqSize);
  }
  @ParameterizedTest(name="IntArrSeq.CheckedSubList rootPreAlloc={0},parentPreAlloc={1},parentPostAlloc={2},rootPostAlloc={3}")
  @MethodSource("subListAllocationArgumentProvider")
  public void testArrSeqSubListCheckedremoveIfIntPredicate(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc)
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqSubListCheckedremoveIfIntPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqSubListCheckedremoveIfIntPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqSubListCheckedremoveIfIntPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqSubListCheckedremoveIfIntPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqSubListCheckedremoveIfIntPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqSubListCheckedremoveIfIntPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqSubListCheckedremoveIfIntPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqSubListCheckedremoveIfIntPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqSubListCheckedremoveIfIntPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqSubListCheckedremoveIfIntPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqSubListCheckedremoveIfIntPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        testArrSeqSubListCheckedremoveIfIntPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.Throwing(rand,seqSize),0,seqSize==0?null:IndexOutOfBoundsException.class);
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfIntPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new IntMonitoredPredicate.Modding(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfIntPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new IntMonitoredPredicate.ModdingAndThrowing(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfIntPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new IntMonitoredPredicate.Modding(rand,seqSize,getParent(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfIntPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new IntMonitoredPredicate.ModdingAndThrowing(rand,seqSize,getParent(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfIntPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new IntMonitoredPredicate.Modding(rand,seqSize,getRoot(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfIntPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new IntMonitoredPredicate.ModdingAndThrowing(rand,seqSize,getRoot(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqSubListCheckedremoveIfIntPredicateHelper(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc,OmniList.OfInt seq,IntMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(IntArrSeq.CheckedList)seq.clone();
    int expectedSize;
    IntArrSeq.CheckedList root;
    int seqSize=seq.size();
    if(expectedException==null)
    {
      if(numExpectedToBeRemoved<0)
      {
        var result=seq.removeIf((IntPredicate)pred);
        int numRemoved=pred.removedVals.size();
        Assertions.assertEquals(numRemoved!=0,result);
        expectedSize=seqSize-numRemoved;
        root=assertCheckedSubListIntegrity(numRemoved==0?0:1,parentPreAlloc+parentPostAlloc+expectedSize,seq);
      }
      else if(numExpectedToBeRemoved==0)
      {
        Assertions.assertFalse(seq.removeIf((IntPredicate)pred));
        Assertions.assertTrue(pred.removedVals.isEmpty());
        expectedSize=seqSize;
        root=assertCheckedSubListIntegrity(0,parentPreAlloc+parentPostAlloc+expectedSize,seq);
      }
      else
      {
        Assertions.assertTrue(seq.removeIf((IntPredicate)pred));
        expectedSize=seqSize-numExpectedToBeRemoved;
        root=assertCheckedSubListIntegrity(1,parentPreAlloc+parentPostAlloc+expectedSize,seq);
      }
      Assertions.assertEquals(expectedSize,seq.size());
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((IntPredicate)pred));
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
      var v=cloneItr.nextInt();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextInt(),v);
    }
    int dstOffset=0;
    for(int i=0;i<rootPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertToint(i-rootPreAlloc-parentPreAlloc));
    }
    for(int i=0;i<parentPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertToint(i-parentPreAlloc));
    }
    dstOffset+=expectedSize;
    for(int i=0;i<parentPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertToint(seqSize+i));
    }
    for(int i=0;i<rootPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertToint(seqSize+parentPostAlloc+i));
    }
  }
  @ParameterizedTest(name="IntArrSeq.CheckedSubList rootPreAlloc={0},parentPreAlloc={1},parentPostAlloc={2},rootPostAlloc={3}")
  @MethodSource("subListAllocationArgumentProvider")
  public void testArrSeqSubListCheckedremoveIfPredicate(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc)
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new IntMonitoredPredicate.Throwing(rand,seqSize),0,seqSize==0?null:IndexOutOfBoundsException.class);
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new IntMonitoredPredicate.Modding(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new IntMonitoredPredicate.ModdingAndThrowing(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new IntMonitoredPredicate.Modding(rand,seqSize,getParent(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new IntMonitoredPredicate.ModdingAndThrowing(rand,seqSize,getParent(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new IntMonitoredPredicate.Modding(rand,seqSize,getRoot(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new IntMonitoredPredicate.ModdingAndThrowing(rand,seqSize,getRoot(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqSubListCheckedremoveIfPredicateHelper(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc,OmniList.OfInt seq,IntMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(IntArrSeq.CheckedList)seq.clone();
    int expectedSize;
    IntArrSeq.CheckedList root;
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
      var v=cloneItr.nextInt();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextInt(),v);
    }
    int dstOffset=0;
    for(int i=0;i<rootPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertToint(i-rootPreAlloc-parentPreAlloc));
    }
    for(int i=0;i<parentPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertToint(i-parentPreAlloc));
    }
    dstOffset+=expectedSize;
    for(int i=0;i<parentPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertToint(seqSize+i));
    }
    for(int i=0;i<rootPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertToint(seqSize+parentPostAlloc+i));
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
