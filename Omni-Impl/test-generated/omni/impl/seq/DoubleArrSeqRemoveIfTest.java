package omni.impl.seq;
import java.util.function.Predicate;
import org.junit.jupiter.api.Assertions;
import java.util.ConcurrentModificationException;
import omni.util.TypeConversionUtil;
import omni.util.OmniArray;
import java.util.Random;
import org.junit.jupiter.api.Test;
import omni.impl.DoubleMonitoredPredicate;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.function.DoublePredicate;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import omni.api.OmniList;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class DoubleArrSeqRemoveIfTest
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
        Class<?> clazz=new DoubleArrSeq.CheckedList().subList(0,0).getClass();
        (CheckedSubListmodCount=clazz.getDeclaredField("modCount")).setAccessible(true);
        (CheckedSubListparent=clazz.getDeclaredField("parent")).setAccessible(true);
        modifiersField.setInt(CheckedSubListparent,CheckedSubListparent.getModifiers()&~Modifier.FINAL);
        (CheckedSubListroot=clazz.getDeclaredField("root")).setAccessible(true);
        modifiersField.setInt(CheckedSubListroot,CheckedSubListroot.getModifiers()&~Modifier.FINAL);
        (CheckedSubListsize=clazz.getDeclaredField("size")).setAccessible(true);
      }
      {
        Class<?> clazz=new DoubleArrSeq.UncheckedList().subList(0,0).getClass();
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
  private static DoubleArrSeq.CheckedList getRoot(Object seq)
  {
    try
    {
      return (DoubleArrSeq.CheckedList)CheckedSubListroot.get(seq);
    }
    catch(Exception e)
    {
      throw new RuntimeException(e);
    }
  }
  private static OmniList.OfDouble getParent(Object seq)
  {
    try
    {
      return (OmniList.OfDouble)CheckedSubListparent.get(seq);
    }
    catch(Exception e)
    {
      throw new RuntimeException(e);
    }
  }
  private static DoubleArrSeq.UncheckedList assertUncheckedSubListIntegrity(int expectedModCount,int expectedParentSize,Object obj)
  {
    if(!(obj instanceof DoubleArrSeq.UncheckedList))
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
    return (DoubleArrSeq.UncheckedList)obj;
  }
  private static DoubleArrSeq.CheckedList assertCheckedSubListIntegrity(int expectedModCount,int expectedParentSize,Object obj)
  {
    if(!(obj instanceof DoubleArrSeq.CheckedList))
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
    DoubleArrSeq.CheckedList root=(DoubleArrSeq.CheckedList)obj;
    Assertions.assertEquals(expectedModCount,root.modCount);
    return root;
  }
  static DoubleArrSeq.UncheckedStack buildArrSeqStackUnchecked(int seqSize)
  {
    double[] arr;
    if(seqSize==0)
    {
      arr=OmniArray.OfDouble.DEFAULT_ARR;
    }
    else
    {
      arr=new double[seqSize];
      for(int i=0;i<seqSize;++i)
      {
        arr[i]=TypeConversionUtil.convertTodouble(i);
      }
    }
    return new DoubleArrSeq.UncheckedStack(seqSize,arr);
  }
  @Test
  public void testArrSeqStackUncheckedremoveIfDoublePredicate()
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqStackUncheckedremoveIfDoublePredicateHelper(buildArrSeqStackUnchecked(seqSize),new DoubleMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqStackUncheckedremoveIfDoublePredicateHelper(buildArrSeqStackUnchecked(seqSize),new DoubleMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqStackUncheckedremoveIfDoublePredicateHelper(buildArrSeqStackUnchecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqStackUncheckedremoveIfDoublePredicateHelper(buildArrSeqStackUnchecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqStackUncheckedremoveIfDoublePredicateHelper(buildArrSeqStackUnchecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqStackUncheckedremoveIfDoublePredicateHelper(buildArrSeqStackUnchecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqStackUncheckedremoveIfDoublePredicateHelper(buildArrSeqStackUnchecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqStackUncheckedremoveIfDoublePredicateHelper(buildArrSeqStackUnchecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqStackUncheckedremoveIfDoublePredicateHelper(buildArrSeqStackUnchecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqStackUncheckedremoveIfDoublePredicateHelper(buildArrSeqStackUnchecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqStackUncheckedremoveIfDoublePredicateHelper(buildArrSeqStackUnchecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqStackUncheckedremoveIfDoublePredicateHelper(DoubleArrSeq.UncheckedStack seq,DoubleMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(DoubleArrSeq.UncheckedStack)seq.clone();
    int expectedSize;
    if(expectedException==null)
    {
      int seqSize=seq.size();
      if(numExpectedToBeRemoved<0)
      {
        var result=seq.removeIf((DoublePredicate)pred);
        int numRemoved=pred.removedVals.size();
        Assertions.assertEquals(numRemoved!=0,result);
        expectedSize=seqSize-numRemoved;
      }
      else if(numExpectedToBeRemoved==0)
      {
        Assertions.assertFalse(seq.removeIf((DoublePredicate)pred));
        Assertions.assertTrue(pred.removedVals.isEmpty());
        expectedSize=seqSize;
      }
      else
      {
        Assertions.assertTrue(seq.removeIf((DoublePredicate)pred));
        expectedSize=seqSize-numExpectedToBeRemoved;
      }
      Assertions.assertEquals(expectedSize,seq.size());
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((DoublePredicate)pred));
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
      var v=cloneItr.nextDouble();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextDouble(),v);
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
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new DoubleMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new DoubleMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqStackUncheckedremoveIfPredicateHelper(DoubleArrSeq.UncheckedStack seq,DoubleMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(DoubleArrSeq.UncheckedStack)seq.clone();
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
      var v=cloneItr.nextDouble();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextDouble(),v);
    }
  }
  static DoubleArrSeq.CheckedStack buildArrSeqStackChecked(int seqSize)
  {
    double[] arr;
    if(seqSize==0)
    {
      arr=OmniArray.OfDouble.DEFAULT_ARR;
    }
    else
    {
      arr=new double[seqSize];
      for(int i=0;i<seqSize;++i)
      {
        arr[i]=TypeConversionUtil.convertTodouble(i);
      }
    }
    return new DoubleArrSeq.CheckedStack(seqSize,arr);
  }
  @Test
  public void testArrSeqStackCheckedremoveIfDoublePredicate()
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqStackCheckedremoveIfDoublePredicateHelper(buildArrSeqStackChecked(seqSize),new DoubleMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqStackCheckedremoveIfDoublePredicateHelper(buildArrSeqStackChecked(seqSize),new DoubleMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqStackCheckedremoveIfDoublePredicateHelper(buildArrSeqStackChecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqStackCheckedremoveIfDoublePredicateHelper(buildArrSeqStackChecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqStackCheckedremoveIfDoublePredicateHelper(buildArrSeqStackChecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqStackCheckedremoveIfDoublePredicateHelper(buildArrSeqStackChecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqStackCheckedremoveIfDoublePredicateHelper(buildArrSeqStackChecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqStackCheckedremoveIfDoublePredicateHelper(buildArrSeqStackChecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqStackCheckedremoveIfDoublePredicateHelper(buildArrSeqStackChecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqStackCheckedremoveIfDoublePredicateHelper(buildArrSeqStackChecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqStackCheckedremoveIfDoublePredicateHelper(buildArrSeqStackChecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        testArrSeqStackCheckedremoveIfDoublePredicateHelper(buildArrSeqStackChecked(seqSize),new DoubleMonitoredPredicate.Throwing(rand,seqSize),0,seqSize==0?null:IndexOutOfBoundsException.class);
        {
          var seq=buildArrSeqStackChecked(seqSize);
          testArrSeqStackCheckedremoveIfDoublePredicateHelper(seq,new DoubleMonitoredPredicate.Modding(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqStackChecked(seqSize);
          testArrSeqStackCheckedremoveIfDoublePredicateHelper(seq,new DoubleMonitoredPredicate.ModdingAndThrowing(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqStackCheckedremoveIfDoublePredicateHelper(DoubleArrSeq.CheckedStack seq,DoubleMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(DoubleArrSeq.CheckedStack)seq.clone();
    int expectedSize;
    if(expectedException==null)
    {
      int seqSize=seq.size();
      if(numExpectedToBeRemoved<0)
      {
        var result=seq.removeIf((DoublePredicate)pred);
        int numRemoved=pred.removedVals.size();
        Assertions.assertEquals(numRemoved!=0,result);
        expectedSize=seqSize-numRemoved;
      }
      else if(numExpectedToBeRemoved==0)
      {
        Assertions.assertFalse(seq.removeIf((DoublePredicate)pred));
        Assertions.assertTrue(pred.removedVals.isEmpty());
        expectedSize=seqSize;
      }
      else
      {
        Assertions.assertTrue(seq.removeIf((DoublePredicate)pred));
        expectedSize=seqSize-numExpectedToBeRemoved;
      }
      Assertions.assertEquals(expectedSize,seq.size());
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((DoublePredicate)pred));
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
      var v=cloneItr.nextDouble();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextDouble(),v);
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
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new DoubleMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new DoubleMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new DoubleMonitoredPredicate.Throwing(rand,seqSize),0,seqSize==0?null:IndexOutOfBoundsException.class);
        {
          var seq=buildArrSeqStackChecked(seqSize);
          testArrSeqStackCheckedremoveIfPredicateHelper(seq,new DoubleMonitoredPredicate.Modding(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqStackChecked(seqSize);
          testArrSeqStackCheckedremoveIfPredicateHelper(seq,new DoubleMonitoredPredicate.ModdingAndThrowing(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqStackCheckedremoveIfPredicateHelper(DoubleArrSeq.CheckedStack seq,DoubleMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(DoubleArrSeq.CheckedStack)seq.clone();
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
      var v=cloneItr.nextDouble();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextDouble(),v);
    }
  }
  static DoubleArrSeq.UncheckedList buildArrSeqListUnchecked(int seqSize)
  {
    double[] arr;
    if(seqSize==0)
    {
      arr=OmniArray.OfDouble.DEFAULT_ARR;
    }
    else
    {
      arr=new double[seqSize];
      for(int i=0;i<seqSize;++i)
      {
        arr[i]=TypeConversionUtil.convertTodouble(i);
      }
    }
    return new DoubleArrSeq.UncheckedList(seqSize,arr);
  }
  @Test
  public void testArrSeqListUncheckedremoveIfDoublePredicate()
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqListUncheckedremoveIfDoublePredicateHelper(buildArrSeqListUnchecked(seqSize),new DoubleMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqListUncheckedremoveIfDoublePredicateHelper(buildArrSeqListUnchecked(seqSize),new DoubleMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqListUncheckedremoveIfDoublePredicateHelper(buildArrSeqListUnchecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqListUncheckedremoveIfDoublePredicateHelper(buildArrSeqListUnchecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqListUncheckedremoveIfDoublePredicateHelper(buildArrSeqListUnchecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqListUncheckedremoveIfDoublePredicateHelper(buildArrSeqListUnchecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqListUncheckedremoveIfDoublePredicateHelper(buildArrSeqListUnchecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqListUncheckedremoveIfDoublePredicateHelper(buildArrSeqListUnchecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqListUncheckedremoveIfDoublePredicateHelper(buildArrSeqListUnchecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqListUncheckedremoveIfDoublePredicateHelper(buildArrSeqListUnchecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqListUncheckedremoveIfDoublePredicateHelper(buildArrSeqListUnchecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqListUncheckedremoveIfDoublePredicateHelper(DoubleArrSeq.UncheckedList seq,DoubleMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(DoubleArrSeq.UncheckedList)seq.clone();
    int expectedSize;
    if(expectedException==null)
    {
      int seqSize=seq.size();
      if(numExpectedToBeRemoved<0)
      {
        var result=seq.removeIf((DoublePredicate)pred);
        int numRemoved=pred.removedVals.size();
        Assertions.assertEquals(numRemoved!=0,result);
        expectedSize=seqSize-numRemoved;
      }
      else if(numExpectedToBeRemoved==0)
      {
        Assertions.assertFalse(seq.removeIf((DoublePredicate)pred));
        Assertions.assertTrue(pred.removedVals.isEmpty());
        expectedSize=seqSize;
      }
      else
      {
        Assertions.assertTrue(seq.removeIf((DoublePredicate)pred));
        expectedSize=seqSize-numExpectedToBeRemoved;
      }
      Assertions.assertEquals(expectedSize,seq.size());
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((DoublePredicate)pred));
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
      var v=cloneItr.nextDouble();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextDouble(),v);
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
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new DoubleMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new DoubleMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqListUncheckedremoveIfPredicateHelper(DoubleArrSeq.UncheckedList seq,DoubleMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(DoubleArrSeq.UncheckedList)seq.clone();
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
      var v=cloneItr.nextDouble();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextDouble(),v);
    }
  }
  static DoubleArrSeq.CheckedList buildArrSeqListChecked(int seqSize)
  {
    double[] arr;
    if(seqSize==0)
    {
      arr=OmniArray.OfDouble.DEFAULT_ARR;
    }
    else
    {
      arr=new double[seqSize];
      for(int i=0;i<seqSize;++i)
      {
        arr[i]=TypeConversionUtil.convertTodouble(i);
      }
    }
    return new DoubleArrSeq.CheckedList(seqSize,arr);
  }
  @Test
  public void testArrSeqListCheckedremoveIfDoublePredicate()
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqListCheckedremoveIfDoublePredicateHelper(buildArrSeqListChecked(seqSize),new DoubleMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqListCheckedremoveIfDoublePredicateHelper(buildArrSeqListChecked(seqSize),new DoubleMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqListCheckedremoveIfDoublePredicateHelper(buildArrSeqListChecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqListCheckedremoveIfDoublePredicateHelper(buildArrSeqListChecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqListCheckedremoveIfDoublePredicateHelper(buildArrSeqListChecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqListCheckedremoveIfDoublePredicateHelper(buildArrSeqListChecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqListCheckedremoveIfDoublePredicateHelper(buildArrSeqListChecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqListCheckedremoveIfDoublePredicateHelper(buildArrSeqListChecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqListCheckedremoveIfDoublePredicateHelper(buildArrSeqListChecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqListCheckedremoveIfDoublePredicateHelper(buildArrSeqListChecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqListCheckedremoveIfDoublePredicateHelper(buildArrSeqListChecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        testArrSeqListCheckedremoveIfDoublePredicateHelper(buildArrSeqListChecked(seqSize),new DoubleMonitoredPredicate.Throwing(rand,seqSize),0,seqSize==0?null:IndexOutOfBoundsException.class);
        {
          var seq=buildArrSeqListChecked(seqSize);
          testArrSeqListCheckedremoveIfDoublePredicateHelper(seq,new DoubleMonitoredPredicate.Modding(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqListChecked(seqSize);
          testArrSeqListCheckedremoveIfDoublePredicateHelper(seq,new DoubleMonitoredPredicate.ModdingAndThrowing(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqListCheckedremoveIfDoublePredicateHelper(DoubleArrSeq.CheckedList seq,DoubleMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(DoubleArrSeq.CheckedList)seq.clone();
    int expectedSize;
    if(expectedException==null)
    {
      int seqSize=seq.size();
      if(numExpectedToBeRemoved<0)
      {
        var result=seq.removeIf((DoublePredicate)pred);
        int numRemoved=pred.removedVals.size();
        Assertions.assertEquals(numRemoved!=0,result);
        expectedSize=seqSize-numRemoved;
      }
      else if(numExpectedToBeRemoved==0)
      {
        Assertions.assertFalse(seq.removeIf((DoublePredicate)pred));
        Assertions.assertTrue(pred.removedVals.isEmpty());
        expectedSize=seqSize;
      }
      else
      {
        Assertions.assertTrue(seq.removeIf((DoublePredicate)pred));
        expectedSize=seqSize-numExpectedToBeRemoved;
      }
      Assertions.assertEquals(expectedSize,seq.size());
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((DoublePredicate)pred));
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
      var v=cloneItr.nextDouble();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextDouble(),v);
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
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new DoubleMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new DoubleMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new DoubleMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new DoubleMonitoredPredicate.Throwing(rand,seqSize),0,seqSize==0?null:IndexOutOfBoundsException.class);
        {
          var seq=buildArrSeqListChecked(seqSize);
          testArrSeqListCheckedremoveIfPredicateHelper(seq,new DoubleMonitoredPredicate.Modding(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqListChecked(seqSize);
          testArrSeqListCheckedremoveIfPredicateHelper(seq,new DoubleMonitoredPredicate.ModdingAndThrowing(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqListCheckedremoveIfPredicateHelper(DoubleArrSeq.CheckedList seq,DoubleMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(DoubleArrSeq.CheckedList)seq.clone();
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
      var v=cloneItr.nextDouble();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextDouble(),v);
    }
  }
  static OmniList.OfDouble buildArrSeqSubListUnchecked(int seqSize,int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc)
  {
    double[] arr;
    int rootSize=rootPreAlloc+parentPreAlloc+seqSize+parentPostAlloc+rootPostAlloc;
    if(rootSize==0)
    {
      arr=OmniArray.OfDouble.DEFAULT_ARR;
    }
    else
    {
      arr=new double[rootSize];
      int dstOffset=0;
      for(int i=0;i<rootPreAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTodouble(i-rootPreAlloc-parentPreAlloc);
      }
      for(int i=0;i<parentPreAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTodouble(i-parentPreAlloc);
      }
      for(int i=0;i<seqSize;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTodouble(i);
      }
      for(int i=0;i<parentPostAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTodouble(seqSize+i);
      }
      for(int i=0;i<rootPostAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTodouble(seqSize+parentPostAlloc+i);
      }
    }
    return new DoubleArrSeq.CheckedList(rootSize,arr).subList(rootPreAlloc,parentPreAlloc+seqSize+parentPostAlloc).subList(parentPreAlloc,parentPreAlloc+seqSize);
  }
  @ParameterizedTest(name="DoubleArrSeq.UncheckedSubList rootPreAlloc={0},parentPreAlloc={1},parentPostAlloc={2},rootPostAlloc={3}")
  @MethodSource("subListAllocationArgumentProvider")
  public void testArrSeqSubListUncheckedremoveIfDoublePredicate(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc)
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqSubListUncheckedremoveIfDoublePredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqSubListUncheckedremoveIfDoublePredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqSubListUncheckedremoveIfDoublePredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqSubListUncheckedremoveIfDoublePredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqSubListUncheckedremoveIfDoublePredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqSubListUncheckedremoveIfDoublePredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqSubListUncheckedremoveIfDoublePredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqSubListUncheckedremoveIfDoublePredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqSubListUncheckedremoveIfDoublePredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqSubListUncheckedremoveIfDoublePredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqSubListUncheckedremoveIfDoublePredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqSubListUncheckedremoveIfDoublePredicateHelper(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc,OmniList.OfDouble seq,DoubleMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(DoubleArrSeq.UncheckedList)seq.clone();
    int expectedSize;
    DoubleArrSeq.UncheckedList root;
    int seqSize=seq.size();
    if(expectedException==null)
    {
      if(numExpectedToBeRemoved<0)
      {
        var result=seq.removeIf((DoublePredicate)pred);
        int numRemoved=pred.removedVals.size();
        Assertions.assertEquals(numRemoved!=0,result);
        expectedSize=seqSize-numRemoved;
        root=assertUncheckedSubListIntegrity(numRemoved==0?0:1,parentPreAlloc+parentPostAlloc+expectedSize,seq);
      }
      else if(numExpectedToBeRemoved==0)
      {
        Assertions.assertFalse(seq.removeIf((DoublePredicate)pred));
        Assertions.assertTrue(pred.removedVals.isEmpty());
        expectedSize=seqSize;
        root=assertUncheckedSubListIntegrity(0,parentPreAlloc+parentPostAlloc+expectedSize,seq);
      }
      else
      {
        Assertions.assertTrue(seq.removeIf((DoublePredicate)pred));
        expectedSize=seqSize-numExpectedToBeRemoved;
        root=assertUncheckedSubListIntegrity(1,parentPreAlloc+parentPostAlloc+expectedSize,seq);
      }
      Assertions.assertEquals(expectedSize,seq.size());
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((DoublePredicate)pred));
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
      var v=cloneItr.nextDouble();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextDouble(),v);
    }
    int dstOffset=0;
    for(int i=0;i<rootPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTodouble(i-rootPreAlloc-parentPreAlloc));
    }
    for(int i=0;i<parentPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTodouble(i-parentPreAlloc));
    }
    dstOffset+=expectedSize;
    for(int i=0;i<parentPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTodouble(seqSize+i));
    }
    for(int i=0;i<rootPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTodouble(seqSize+parentPostAlloc+i));
    }
  }
  @ParameterizedTest(name="DoubleArrSeq.UncheckedSubList rootPreAlloc={0},parentPreAlloc={1},parentPostAlloc={2},rootPostAlloc={3}")
  @MethodSource("subListAllocationArgumentProvider")
  public void testArrSeqSubListUncheckedremoveIfPredicate(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc)
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqSubListUncheckedremoveIfPredicateHelper(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc,OmniList.OfDouble seq,DoubleMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(DoubleArrSeq.UncheckedList)seq.clone();
    int expectedSize;
    DoubleArrSeq.UncheckedList root;
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
      var v=cloneItr.nextDouble();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextDouble(),v);
    }
    int dstOffset=0;
    for(int i=0;i<rootPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTodouble(i-rootPreAlloc-parentPreAlloc));
    }
    for(int i=0;i<parentPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTodouble(i-parentPreAlloc));
    }
    dstOffset+=expectedSize;
    for(int i=0;i<parentPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTodouble(seqSize+i));
    }
    for(int i=0;i<rootPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTodouble(seqSize+parentPostAlloc+i));
    }
  }
  static OmniList.OfDouble buildArrSeqSubListChecked(int seqSize,int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc)
  {
    double[] arr;
    int rootSize=rootPreAlloc+parentPreAlloc+seqSize+parentPostAlloc+rootPostAlloc;
    if(rootSize==0)
    {
      arr=OmniArray.OfDouble.DEFAULT_ARR;
    }
    else
    {
      arr=new double[rootSize];
      int dstOffset=0;
      for(int i=0;i<rootPreAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTodouble(i-rootPreAlloc-parentPreAlloc);
      }
      for(int i=0;i<parentPreAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTodouble(i-parentPreAlloc);
      }
      for(int i=0;i<seqSize;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTodouble(i);
      }
      for(int i=0;i<parentPostAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTodouble(seqSize+i);
      }
      for(int i=0;i<rootPostAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTodouble(seqSize+parentPostAlloc+i);
      }
    }
    return new DoubleArrSeq.CheckedList(rootSize,arr).subList(rootPreAlloc,parentPreAlloc+seqSize+parentPostAlloc).subList(parentPreAlloc,parentPreAlloc+seqSize);
  }
  @ParameterizedTest(name="DoubleArrSeq.CheckedSubList rootPreAlloc={0},parentPreAlloc={1},parentPostAlloc={2},rootPostAlloc={3}")
  @MethodSource("subListAllocationArgumentProvider")
  public void testArrSeqSubListCheckedremoveIfDoublePredicate(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc)
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqSubListCheckedremoveIfDoublePredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqSubListCheckedremoveIfDoublePredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqSubListCheckedremoveIfDoublePredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqSubListCheckedremoveIfDoublePredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqSubListCheckedremoveIfDoublePredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqSubListCheckedremoveIfDoublePredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqSubListCheckedremoveIfDoublePredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqSubListCheckedremoveIfDoublePredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqSubListCheckedremoveIfDoublePredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqSubListCheckedremoveIfDoublePredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqSubListCheckedremoveIfDoublePredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        testArrSeqSubListCheckedremoveIfDoublePredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.Throwing(rand,seqSize),0,seqSize==0?null:IndexOutOfBoundsException.class);
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfDoublePredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new DoubleMonitoredPredicate.Modding(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfDoublePredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new DoubleMonitoredPredicate.ModdingAndThrowing(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfDoublePredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new DoubleMonitoredPredicate.Modding(rand,seqSize,getParent(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfDoublePredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new DoubleMonitoredPredicate.ModdingAndThrowing(rand,seqSize,getParent(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfDoublePredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new DoubleMonitoredPredicate.Modding(rand,seqSize,getRoot(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfDoublePredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new DoubleMonitoredPredicate.ModdingAndThrowing(rand,seqSize,getRoot(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqSubListCheckedremoveIfDoublePredicateHelper(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc,OmniList.OfDouble seq,DoubleMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(DoubleArrSeq.CheckedList)seq.clone();
    int expectedSize;
    DoubleArrSeq.CheckedList root;
    int seqSize=seq.size();
    if(expectedException==null)
    {
      if(numExpectedToBeRemoved<0)
      {
        var result=seq.removeIf((DoublePredicate)pred);
        int numRemoved=pred.removedVals.size();
        Assertions.assertEquals(numRemoved!=0,result);
        expectedSize=seqSize-numRemoved;
        root=assertCheckedSubListIntegrity(numRemoved==0?0:1,parentPreAlloc+parentPostAlloc+expectedSize,seq);
      }
      else if(numExpectedToBeRemoved==0)
      {
        Assertions.assertFalse(seq.removeIf((DoublePredicate)pred));
        Assertions.assertTrue(pred.removedVals.isEmpty());
        expectedSize=seqSize;
        root=assertCheckedSubListIntegrity(0,parentPreAlloc+parentPostAlloc+expectedSize,seq);
      }
      else
      {
        Assertions.assertTrue(seq.removeIf((DoublePredicate)pred));
        expectedSize=seqSize-numExpectedToBeRemoved;
        root=assertCheckedSubListIntegrity(1,parentPreAlloc+parentPostAlloc+expectedSize,seq);
      }
      Assertions.assertEquals(expectedSize,seq.size());
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((DoublePredicate)pred));
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
      var v=cloneItr.nextDouble();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextDouble(),v);
    }
    int dstOffset=0;
    for(int i=0;i<rootPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTodouble(i-rootPreAlloc-parentPreAlloc));
    }
    for(int i=0;i<parentPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTodouble(i-parentPreAlloc));
    }
    dstOffset+=expectedSize;
    for(int i=0;i<parentPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTodouble(seqSize+i));
    }
    for(int i=0;i<rootPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTodouble(seqSize+parentPostAlloc+i));
    }
  }
  @ParameterizedTest(name="DoubleArrSeq.CheckedSubList rootPreAlloc={0},parentPreAlloc={1},parentPostAlloc={2},rootPostAlloc={3}")
  @MethodSource("subListAllocationArgumentProvider")
  public void testArrSeqSubListCheckedremoveIfPredicate(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc)
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new DoubleMonitoredPredicate.Throwing(rand,seqSize),0,seqSize==0?null:IndexOutOfBoundsException.class);
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new DoubleMonitoredPredicate.Modding(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new DoubleMonitoredPredicate.ModdingAndThrowing(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new DoubleMonitoredPredicate.Modding(rand,seqSize,getParent(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new DoubleMonitoredPredicate.ModdingAndThrowing(rand,seqSize,getParent(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new DoubleMonitoredPredicate.Modding(rand,seqSize,getRoot(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new DoubleMonitoredPredicate.ModdingAndThrowing(rand,seqSize,getRoot(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqSubListCheckedremoveIfPredicateHelper(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc,OmniList.OfDouble seq,DoubleMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(DoubleArrSeq.CheckedList)seq.clone();
    int expectedSize;
    DoubleArrSeq.CheckedList root;
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
      var v=cloneItr.nextDouble();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextDouble(),v);
    }
    int dstOffset=0;
    for(int i=0;i<rootPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTodouble(i-rootPreAlloc-parentPreAlloc));
    }
    for(int i=0;i<parentPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTodouble(i-parentPreAlloc));
    }
    dstOffset+=expectedSize;
    for(int i=0;i<parentPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTodouble(seqSize+i));
    }
    for(int i=0;i<rootPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTodouble(seqSize+parentPostAlloc+i));
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
