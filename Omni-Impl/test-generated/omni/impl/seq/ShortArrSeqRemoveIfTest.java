package omni.impl.seq;
import java.util.function.Predicate;
import org.junit.jupiter.api.Assertions;
import java.util.ConcurrentModificationException;
import omni.util.TypeConversionUtil;
import omni.util.OmniArray;
import java.util.Random;
import org.junit.jupiter.api.Test;
import omni.impl.ShortMonitoredPredicate;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import omni.function.ShortPredicate;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import omni.api.OmniList;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class ShortArrSeqRemoveIfTest
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
        Class<?> clazz=new ShortArrSeq.CheckedList().subList(0,0).getClass();
        (CheckedSubListmodCount=clazz.getDeclaredField("modCount")).setAccessible(true);
        (CheckedSubListparent=clazz.getDeclaredField("parent")).setAccessible(true);
        modifiersField.setInt(CheckedSubListparent,CheckedSubListparent.getModifiers()&~Modifier.FINAL);
        (CheckedSubListroot=clazz.getDeclaredField("root")).setAccessible(true);
        modifiersField.setInt(CheckedSubListroot,CheckedSubListroot.getModifiers()&~Modifier.FINAL);
        (CheckedSubListsize=clazz.getDeclaredField("size")).setAccessible(true);
      }
      {
        Class<?> clazz=new ShortArrSeq.UncheckedList().subList(0,0).getClass();
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
  private static ShortArrSeq.CheckedList getRoot(Object seq)
  {
    try
    {
      return (ShortArrSeq.CheckedList)CheckedSubListroot.get(seq);
    }
    catch(Exception e)
    {
      throw new RuntimeException(e);
    }
  }
  private static OmniList.OfShort getParent(Object seq)
  {
    try
    {
      return (OmniList.OfShort)CheckedSubListparent.get(seq);
    }
    catch(Exception e)
    {
      throw new RuntimeException(e);
    }
  }
  private static ShortArrSeq.UncheckedList assertUncheckedSubListIntegrity(int expectedModCount,int expectedParentSize,Object obj)
  {
    if(!(obj instanceof ShortArrSeq.UncheckedList))
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
    return (ShortArrSeq.UncheckedList)obj;
  }
  private static ShortArrSeq.CheckedList assertCheckedSubListIntegrity(int expectedModCount,int expectedParentSize,Object obj)
  {
    if(!(obj instanceof ShortArrSeq.CheckedList))
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
    ShortArrSeq.CheckedList root=(ShortArrSeq.CheckedList)obj;
    Assertions.assertEquals(expectedModCount,root.modCount);
    return root;
  }
  static ShortArrSeq.UncheckedStack buildArrSeqStackUnchecked(int seqSize)
  {
    short[] arr;
    if(seqSize==0)
    {
      arr=OmniArray.OfShort.DEFAULT_ARR;
    }
    else
    {
      arr=new short[seqSize];
      for(int i=0;i<seqSize;++i)
      {
        arr[i]=TypeConversionUtil.convertToshort(i);
      }
    }
    return new ShortArrSeq.UncheckedStack(seqSize,arr);
  }
  @Test
  public void testArrSeqStackUncheckedremoveIfShortPredicate()
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqStackUncheckedremoveIfShortPredicateHelper(buildArrSeqStackUnchecked(seqSize),new ShortMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqStackUncheckedremoveIfShortPredicateHelper(buildArrSeqStackUnchecked(seqSize),new ShortMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqStackUncheckedremoveIfShortPredicateHelper(buildArrSeqStackUnchecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqStackUncheckedremoveIfShortPredicateHelper(buildArrSeqStackUnchecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqStackUncheckedremoveIfShortPredicateHelper(buildArrSeqStackUnchecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqStackUncheckedremoveIfShortPredicateHelper(buildArrSeqStackUnchecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqStackUncheckedremoveIfShortPredicateHelper(buildArrSeqStackUnchecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqStackUncheckedremoveIfShortPredicateHelper(buildArrSeqStackUnchecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqStackUncheckedremoveIfShortPredicateHelper(buildArrSeqStackUnchecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqStackUncheckedremoveIfShortPredicateHelper(buildArrSeqStackUnchecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqStackUncheckedremoveIfShortPredicateHelper(buildArrSeqStackUnchecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqStackUncheckedremoveIfShortPredicateHelper(ShortArrSeq.UncheckedStack seq,ShortMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(ShortArrSeq.UncheckedStack)seq.clone();
    int expectedSize;
    if(expectedException==null)
    {
      int seqSize=seq.size();
      if(numExpectedToBeRemoved<0)
      {
        var result=seq.removeIf((ShortPredicate)pred);
        int numRemoved=pred.removedVals.size();
        Assertions.assertEquals(numRemoved!=0,result);
        expectedSize=seqSize-numRemoved;
      }
      else if(numExpectedToBeRemoved==0)
      {
        Assertions.assertFalse(seq.removeIf((ShortPredicate)pred));
        Assertions.assertTrue(pred.removedVals.isEmpty());
        expectedSize=seqSize;
      }
      else
      {
        Assertions.assertTrue(seq.removeIf((ShortPredicate)pred));
        expectedSize=seqSize-numExpectedToBeRemoved;
      }
      Assertions.assertEquals(expectedSize,seq.size());
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((ShortPredicate)pred));
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
      var v=cloneItr.nextShort();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextShort(),v);
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
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new ShortMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new ShortMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqStackUncheckedremoveIfPredicateHelper(ShortArrSeq.UncheckedStack seq,ShortMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(ShortArrSeq.UncheckedStack)seq.clone();
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
      var v=cloneItr.nextShort();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextShort(),v);
    }
  }
  static ShortArrSeq.CheckedStack buildArrSeqStackChecked(int seqSize)
  {
    short[] arr;
    if(seqSize==0)
    {
      arr=OmniArray.OfShort.DEFAULT_ARR;
    }
    else
    {
      arr=new short[seqSize];
      for(int i=0;i<seqSize;++i)
      {
        arr[i]=TypeConversionUtil.convertToshort(i);
      }
    }
    return new ShortArrSeq.CheckedStack(seqSize,arr);
  }
  @Test
  public void testArrSeqStackCheckedremoveIfShortPredicate()
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqStackCheckedremoveIfShortPredicateHelper(buildArrSeqStackChecked(seqSize),new ShortMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqStackCheckedremoveIfShortPredicateHelper(buildArrSeqStackChecked(seqSize),new ShortMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqStackCheckedremoveIfShortPredicateHelper(buildArrSeqStackChecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqStackCheckedremoveIfShortPredicateHelper(buildArrSeqStackChecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqStackCheckedremoveIfShortPredicateHelper(buildArrSeqStackChecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqStackCheckedremoveIfShortPredicateHelper(buildArrSeqStackChecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqStackCheckedremoveIfShortPredicateHelper(buildArrSeqStackChecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqStackCheckedremoveIfShortPredicateHelper(buildArrSeqStackChecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqStackCheckedremoveIfShortPredicateHelper(buildArrSeqStackChecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqStackCheckedremoveIfShortPredicateHelper(buildArrSeqStackChecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqStackCheckedremoveIfShortPredicateHelper(buildArrSeqStackChecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        testArrSeqStackCheckedremoveIfShortPredicateHelper(buildArrSeqStackChecked(seqSize),new ShortMonitoredPredicate.Throwing(rand,seqSize),0,seqSize==0?null:IndexOutOfBoundsException.class);
        {
          var seq=buildArrSeqStackChecked(seqSize);
          testArrSeqStackCheckedremoveIfShortPredicateHelper(seq,new ShortMonitoredPredicate.Modding(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqStackChecked(seqSize);
          testArrSeqStackCheckedremoveIfShortPredicateHelper(seq,new ShortMonitoredPredicate.ModdingAndThrowing(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqStackCheckedremoveIfShortPredicateHelper(ShortArrSeq.CheckedStack seq,ShortMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(ShortArrSeq.CheckedStack)seq.clone();
    int expectedSize;
    if(expectedException==null)
    {
      int seqSize=seq.size();
      if(numExpectedToBeRemoved<0)
      {
        var result=seq.removeIf((ShortPredicate)pred);
        int numRemoved=pred.removedVals.size();
        Assertions.assertEquals(numRemoved!=0,result);
        expectedSize=seqSize-numRemoved;
      }
      else if(numExpectedToBeRemoved==0)
      {
        Assertions.assertFalse(seq.removeIf((ShortPredicate)pred));
        Assertions.assertTrue(pred.removedVals.isEmpty());
        expectedSize=seqSize;
      }
      else
      {
        Assertions.assertTrue(seq.removeIf((ShortPredicate)pred));
        expectedSize=seqSize-numExpectedToBeRemoved;
      }
      Assertions.assertEquals(expectedSize,seq.size());
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((ShortPredicate)pred));
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
      var v=cloneItr.nextShort();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextShort(),v);
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
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new ShortMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new ShortMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new ShortMonitoredPredicate.Throwing(rand,seqSize),0,seqSize==0?null:IndexOutOfBoundsException.class);
        {
          var seq=buildArrSeqStackChecked(seqSize);
          testArrSeqStackCheckedremoveIfPredicateHelper(seq,new ShortMonitoredPredicate.Modding(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqStackChecked(seqSize);
          testArrSeqStackCheckedremoveIfPredicateHelper(seq,new ShortMonitoredPredicate.ModdingAndThrowing(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqStackCheckedremoveIfPredicateHelper(ShortArrSeq.CheckedStack seq,ShortMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(ShortArrSeq.CheckedStack)seq.clone();
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
      var v=cloneItr.nextShort();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextShort(),v);
    }
  }
  static ShortArrSeq.UncheckedList buildArrSeqListUnchecked(int seqSize)
  {
    short[] arr;
    if(seqSize==0)
    {
      arr=OmniArray.OfShort.DEFAULT_ARR;
    }
    else
    {
      arr=new short[seqSize];
      for(int i=0;i<seqSize;++i)
      {
        arr[i]=TypeConversionUtil.convertToshort(i);
      }
    }
    return new ShortArrSeq.UncheckedList(seqSize,arr);
  }
  @Test
  public void testArrSeqListUncheckedremoveIfShortPredicate()
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqListUncheckedremoveIfShortPredicateHelper(buildArrSeqListUnchecked(seqSize),new ShortMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqListUncheckedremoveIfShortPredicateHelper(buildArrSeqListUnchecked(seqSize),new ShortMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqListUncheckedremoveIfShortPredicateHelper(buildArrSeqListUnchecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqListUncheckedremoveIfShortPredicateHelper(buildArrSeqListUnchecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqListUncheckedremoveIfShortPredicateHelper(buildArrSeqListUnchecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqListUncheckedremoveIfShortPredicateHelper(buildArrSeqListUnchecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqListUncheckedremoveIfShortPredicateHelper(buildArrSeqListUnchecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqListUncheckedremoveIfShortPredicateHelper(buildArrSeqListUnchecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqListUncheckedremoveIfShortPredicateHelper(buildArrSeqListUnchecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqListUncheckedremoveIfShortPredicateHelper(buildArrSeqListUnchecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqListUncheckedremoveIfShortPredicateHelper(buildArrSeqListUnchecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqListUncheckedremoveIfShortPredicateHelper(ShortArrSeq.UncheckedList seq,ShortMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(ShortArrSeq.UncheckedList)seq.clone();
    int expectedSize;
    if(expectedException==null)
    {
      int seqSize=seq.size();
      if(numExpectedToBeRemoved<0)
      {
        var result=seq.removeIf((ShortPredicate)pred);
        int numRemoved=pred.removedVals.size();
        Assertions.assertEquals(numRemoved!=0,result);
        expectedSize=seqSize-numRemoved;
      }
      else if(numExpectedToBeRemoved==0)
      {
        Assertions.assertFalse(seq.removeIf((ShortPredicate)pred));
        Assertions.assertTrue(pred.removedVals.isEmpty());
        expectedSize=seqSize;
      }
      else
      {
        Assertions.assertTrue(seq.removeIf((ShortPredicate)pred));
        expectedSize=seqSize-numExpectedToBeRemoved;
      }
      Assertions.assertEquals(expectedSize,seq.size());
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((ShortPredicate)pred));
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
      var v=cloneItr.nextShort();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextShort(),v);
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
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new ShortMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new ShortMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqListUncheckedremoveIfPredicateHelper(ShortArrSeq.UncheckedList seq,ShortMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(ShortArrSeq.UncheckedList)seq.clone();
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
      var v=cloneItr.nextShort();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextShort(),v);
    }
  }
  static ShortArrSeq.CheckedList buildArrSeqListChecked(int seqSize)
  {
    short[] arr;
    if(seqSize==0)
    {
      arr=OmniArray.OfShort.DEFAULT_ARR;
    }
    else
    {
      arr=new short[seqSize];
      for(int i=0;i<seqSize;++i)
      {
        arr[i]=TypeConversionUtil.convertToshort(i);
      }
    }
    return new ShortArrSeq.CheckedList(seqSize,arr);
  }
  @Test
  public void testArrSeqListCheckedremoveIfShortPredicate()
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqListCheckedremoveIfShortPredicateHelper(buildArrSeqListChecked(seqSize),new ShortMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqListCheckedremoveIfShortPredicateHelper(buildArrSeqListChecked(seqSize),new ShortMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqListCheckedremoveIfShortPredicateHelper(buildArrSeqListChecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqListCheckedremoveIfShortPredicateHelper(buildArrSeqListChecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqListCheckedremoveIfShortPredicateHelper(buildArrSeqListChecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqListCheckedremoveIfShortPredicateHelper(buildArrSeqListChecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqListCheckedremoveIfShortPredicateHelper(buildArrSeqListChecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqListCheckedremoveIfShortPredicateHelper(buildArrSeqListChecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqListCheckedremoveIfShortPredicateHelper(buildArrSeqListChecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqListCheckedremoveIfShortPredicateHelper(buildArrSeqListChecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqListCheckedremoveIfShortPredicateHelper(buildArrSeqListChecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        testArrSeqListCheckedremoveIfShortPredicateHelper(buildArrSeqListChecked(seqSize),new ShortMonitoredPredicate.Throwing(rand,seqSize),0,seqSize==0?null:IndexOutOfBoundsException.class);
        {
          var seq=buildArrSeqListChecked(seqSize);
          testArrSeqListCheckedremoveIfShortPredicateHelper(seq,new ShortMonitoredPredicate.Modding(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqListChecked(seqSize);
          testArrSeqListCheckedremoveIfShortPredicateHelper(seq,new ShortMonitoredPredicate.ModdingAndThrowing(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqListCheckedremoveIfShortPredicateHelper(ShortArrSeq.CheckedList seq,ShortMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(ShortArrSeq.CheckedList)seq.clone();
    int expectedSize;
    if(expectedException==null)
    {
      int seqSize=seq.size();
      if(numExpectedToBeRemoved<0)
      {
        var result=seq.removeIf((ShortPredicate)pred);
        int numRemoved=pred.removedVals.size();
        Assertions.assertEquals(numRemoved!=0,result);
        expectedSize=seqSize-numRemoved;
      }
      else if(numExpectedToBeRemoved==0)
      {
        Assertions.assertFalse(seq.removeIf((ShortPredicate)pred));
        Assertions.assertTrue(pred.removedVals.isEmpty());
        expectedSize=seqSize;
      }
      else
      {
        Assertions.assertTrue(seq.removeIf((ShortPredicate)pred));
        expectedSize=seqSize-numExpectedToBeRemoved;
      }
      Assertions.assertEquals(expectedSize,seq.size());
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((ShortPredicate)pred));
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
      var v=cloneItr.nextShort();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextShort(),v);
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
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new ShortMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new ShortMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new ShortMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new ShortMonitoredPredicate.Throwing(rand,seqSize),0,seqSize==0?null:IndexOutOfBoundsException.class);
        {
          var seq=buildArrSeqListChecked(seqSize);
          testArrSeqListCheckedremoveIfPredicateHelper(seq,new ShortMonitoredPredicate.Modding(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqListChecked(seqSize);
          testArrSeqListCheckedremoveIfPredicateHelper(seq,new ShortMonitoredPredicate.ModdingAndThrowing(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqListCheckedremoveIfPredicateHelper(ShortArrSeq.CheckedList seq,ShortMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(ShortArrSeq.CheckedList)seq.clone();
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
      var v=cloneItr.nextShort();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextShort(),v);
    }
  }
  static OmniList.OfShort buildArrSeqSubListUnchecked(int seqSize,int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc)
  {
    short[] arr;
    int rootSize=rootPreAlloc+parentPreAlloc+seqSize+parentPostAlloc+rootPostAlloc;
    if(rootSize==0)
    {
      arr=OmniArray.OfShort.DEFAULT_ARR;
    }
    else
    {
      arr=new short[rootSize];
      int dstOffset=0;
      for(int i=0;i<rootPreAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertToshort(i-rootPreAlloc-parentPreAlloc);
      }
      for(int i=0;i<parentPreAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertToshort(i-parentPreAlloc);
      }
      for(int i=0;i<seqSize;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertToshort(i);
      }
      for(int i=0;i<parentPostAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertToshort(seqSize+i);
      }
      for(int i=0;i<rootPostAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertToshort(seqSize+parentPostAlloc+i);
      }
    }
    return new ShortArrSeq.CheckedList(rootSize,arr).subList(rootPreAlloc,parentPreAlloc+seqSize+parentPostAlloc).subList(parentPreAlloc,parentPreAlloc+seqSize);
  }
  @ParameterizedTest(name="ShortArrSeq.UncheckedSubList rootPreAlloc={0},parentPreAlloc={1},parentPostAlloc={2},rootPostAlloc={3}")
  @MethodSource("subListAllocationArgumentProvider")
  public void testArrSeqSubListUncheckedremoveIfShortPredicate(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc)
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqSubListUncheckedremoveIfShortPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqSubListUncheckedremoveIfShortPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqSubListUncheckedremoveIfShortPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqSubListUncheckedremoveIfShortPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqSubListUncheckedremoveIfShortPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqSubListUncheckedremoveIfShortPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqSubListUncheckedremoveIfShortPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqSubListUncheckedremoveIfShortPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqSubListUncheckedremoveIfShortPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqSubListUncheckedremoveIfShortPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqSubListUncheckedremoveIfShortPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqSubListUncheckedremoveIfShortPredicateHelper(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc,OmniList.OfShort seq,ShortMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(ShortArrSeq.UncheckedList)seq.clone();
    int expectedSize;
    ShortArrSeq.UncheckedList root;
    int seqSize=seq.size();
    if(expectedException==null)
    {
      if(numExpectedToBeRemoved<0)
      {
        var result=seq.removeIf((ShortPredicate)pred);
        int numRemoved=pred.removedVals.size();
        Assertions.assertEquals(numRemoved!=0,result);
        expectedSize=seqSize-numRemoved;
        root=assertUncheckedSubListIntegrity(numRemoved==0?0:1,parentPreAlloc+parentPostAlloc+expectedSize,seq);
      }
      else if(numExpectedToBeRemoved==0)
      {
        Assertions.assertFalse(seq.removeIf((ShortPredicate)pred));
        Assertions.assertTrue(pred.removedVals.isEmpty());
        expectedSize=seqSize;
        root=assertUncheckedSubListIntegrity(0,parentPreAlloc+parentPostAlloc+expectedSize,seq);
      }
      else
      {
        Assertions.assertTrue(seq.removeIf((ShortPredicate)pred));
        expectedSize=seqSize-numExpectedToBeRemoved;
        root=assertUncheckedSubListIntegrity(1,parentPreAlloc+parentPostAlloc+expectedSize,seq);
      }
      Assertions.assertEquals(expectedSize,seq.size());
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((ShortPredicate)pred));
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
      var v=cloneItr.nextShort();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextShort(),v);
    }
    int dstOffset=0;
    for(int i=0;i<rootPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertToshort(i-rootPreAlloc-parentPreAlloc));
    }
    for(int i=0;i<parentPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertToshort(i-parentPreAlloc));
    }
    dstOffset+=expectedSize;
    for(int i=0;i<parentPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertToshort(seqSize+i));
    }
    for(int i=0;i<rootPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertToshort(seqSize+parentPostAlloc+i));
    }
  }
  @ParameterizedTest(name="ShortArrSeq.UncheckedSubList rootPreAlloc={0},parentPreAlloc={1},parentPostAlloc={2},rootPostAlloc={3}")
  @MethodSource("subListAllocationArgumentProvider")
  public void testArrSeqSubListUncheckedremoveIfPredicate(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc)
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqSubListUncheckedremoveIfPredicateHelper(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc,OmniList.OfShort seq,ShortMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(ShortArrSeq.UncheckedList)seq.clone();
    int expectedSize;
    ShortArrSeq.UncheckedList root;
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
      var v=cloneItr.nextShort();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextShort(),v);
    }
    int dstOffset=0;
    for(int i=0;i<rootPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertToshort(i-rootPreAlloc-parentPreAlloc));
    }
    for(int i=0;i<parentPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertToshort(i-parentPreAlloc));
    }
    dstOffset+=expectedSize;
    for(int i=0;i<parentPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertToshort(seqSize+i));
    }
    for(int i=0;i<rootPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertToshort(seqSize+parentPostAlloc+i));
    }
  }
  static OmniList.OfShort buildArrSeqSubListChecked(int seqSize,int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc)
  {
    short[] arr;
    int rootSize=rootPreAlloc+parentPreAlloc+seqSize+parentPostAlloc+rootPostAlloc;
    if(rootSize==0)
    {
      arr=OmniArray.OfShort.DEFAULT_ARR;
    }
    else
    {
      arr=new short[rootSize];
      int dstOffset=0;
      for(int i=0;i<rootPreAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertToshort(i-rootPreAlloc-parentPreAlloc);
      }
      for(int i=0;i<parentPreAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertToshort(i-parentPreAlloc);
      }
      for(int i=0;i<seqSize;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertToshort(i);
      }
      for(int i=0;i<parentPostAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertToshort(seqSize+i);
      }
      for(int i=0;i<rootPostAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertToshort(seqSize+parentPostAlloc+i);
      }
    }
    return new ShortArrSeq.CheckedList(rootSize,arr).subList(rootPreAlloc,parentPreAlloc+seqSize+parentPostAlloc).subList(parentPreAlloc,parentPreAlloc+seqSize);
  }
  @ParameterizedTest(name="ShortArrSeq.CheckedSubList rootPreAlloc={0},parentPreAlloc={1},parentPostAlloc={2},rootPostAlloc={3}")
  @MethodSource("subListAllocationArgumentProvider")
  public void testArrSeqSubListCheckedremoveIfShortPredicate(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc)
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqSubListCheckedremoveIfShortPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqSubListCheckedremoveIfShortPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqSubListCheckedremoveIfShortPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqSubListCheckedremoveIfShortPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqSubListCheckedremoveIfShortPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqSubListCheckedremoveIfShortPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqSubListCheckedremoveIfShortPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqSubListCheckedremoveIfShortPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqSubListCheckedremoveIfShortPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqSubListCheckedremoveIfShortPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqSubListCheckedremoveIfShortPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        testArrSeqSubListCheckedremoveIfShortPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.Throwing(rand,seqSize),0,seqSize==0?null:IndexOutOfBoundsException.class);
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfShortPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new ShortMonitoredPredicate.Modding(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfShortPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new ShortMonitoredPredicate.ModdingAndThrowing(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfShortPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new ShortMonitoredPredicate.Modding(rand,seqSize,getParent(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfShortPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new ShortMonitoredPredicate.ModdingAndThrowing(rand,seqSize,getParent(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfShortPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new ShortMonitoredPredicate.Modding(rand,seqSize,getRoot(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfShortPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new ShortMonitoredPredicate.ModdingAndThrowing(rand,seqSize,getRoot(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqSubListCheckedremoveIfShortPredicateHelper(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc,OmniList.OfShort seq,ShortMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(ShortArrSeq.CheckedList)seq.clone();
    int expectedSize;
    ShortArrSeq.CheckedList root;
    int seqSize=seq.size();
    if(expectedException==null)
    {
      if(numExpectedToBeRemoved<0)
      {
        var result=seq.removeIf((ShortPredicate)pred);
        int numRemoved=pred.removedVals.size();
        Assertions.assertEquals(numRemoved!=0,result);
        expectedSize=seqSize-numRemoved;
        root=assertCheckedSubListIntegrity(numRemoved==0?0:1,parentPreAlloc+parentPostAlloc+expectedSize,seq);
      }
      else if(numExpectedToBeRemoved==0)
      {
        Assertions.assertFalse(seq.removeIf((ShortPredicate)pred));
        Assertions.assertTrue(pred.removedVals.isEmpty());
        expectedSize=seqSize;
        root=assertCheckedSubListIntegrity(0,parentPreAlloc+parentPostAlloc+expectedSize,seq);
      }
      else
      {
        Assertions.assertTrue(seq.removeIf((ShortPredicate)pred));
        expectedSize=seqSize-numExpectedToBeRemoved;
        root=assertCheckedSubListIntegrity(1,parentPreAlloc+parentPostAlloc+expectedSize,seq);
      }
      Assertions.assertEquals(expectedSize,seq.size());
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((ShortPredicate)pred));
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
      var v=cloneItr.nextShort();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextShort(),v);
    }
    int dstOffset=0;
    for(int i=0;i<rootPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertToshort(i-rootPreAlloc-parentPreAlloc));
    }
    for(int i=0;i<parentPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertToshort(i-parentPreAlloc));
    }
    dstOffset+=expectedSize;
    for(int i=0;i<parentPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertToshort(seqSize+i));
    }
    for(int i=0;i<rootPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertToshort(seqSize+parentPostAlloc+i));
    }
  }
  @ParameterizedTest(name="ShortArrSeq.CheckedSubList rootPreAlloc={0},parentPreAlloc={1},parentPostAlloc={2},rootPostAlloc={3}")
  @MethodSource("subListAllocationArgumentProvider")
  public void testArrSeqSubListCheckedremoveIfPredicate(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc)
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new ShortMonitoredPredicate.Throwing(rand,seqSize),0,seqSize==0?null:IndexOutOfBoundsException.class);
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new ShortMonitoredPredicate.Modding(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new ShortMonitoredPredicate.ModdingAndThrowing(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new ShortMonitoredPredicate.Modding(rand,seqSize,getParent(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new ShortMonitoredPredicate.ModdingAndThrowing(rand,seqSize,getParent(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new ShortMonitoredPredicate.Modding(rand,seqSize,getRoot(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new ShortMonitoredPredicate.ModdingAndThrowing(rand,seqSize,getRoot(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqSubListCheckedremoveIfPredicateHelper(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc,OmniList.OfShort seq,ShortMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(ShortArrSeq.CheckedList)seq.clone();
    int expectedSize;
    ShortArrSeq.CheckedList root;
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
      var v=cloneItr.nextShort();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextShort(),v);
    }
    int dstOffset=0;
    for(int i=0;i<rootPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertToshort(i-rootPreAlloc-parentPreAlloc));
    }
    for(int i=0;i<parentPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertToshort(i-parentPreAlloc));
    }
    dstOffset+=expectedSize;
    for(int i=0;i<parentPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertToshort(seqSize+i));
    }
    for(int i=0;i<rootPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertToshort(seqSize+parentPostAlloc+i));
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
