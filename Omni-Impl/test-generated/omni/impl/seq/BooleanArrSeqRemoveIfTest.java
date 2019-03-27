package omni.impl.seq;
import java.util.function.Predicate;
import org.junit.jupiter.api.Assertions;
import java.util.ConcurrentModificationException;
import omni.util.OmniArray;
import java.util.Random;
import org.junit.jupiter.api.Test;
import omni.impl.BooleanMonitoredPredicate;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import omni.function.BooleanPredicate;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import omni.api.OmniList;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class BooleanArrSeqRemoveIfTest
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
        Class<?> clazz=new BooleanArrSeq.CheckedList().subList(0,0).getClass();
        (CheckedSubListmodCount=clazz.getDeclaredField("modCount")).setAccessible(true);
        (CheckedSubListparent=clazz.getDeclaredField("parent")).setAccessible(true);
        modifiersField.setInt(CheckedSubListparent,CheckedSubListparent.getModifiers()&~Modifier.FINAL);
        (CheckedSubListroot=clazz.getDeclaredField("root")).setAccessible(true);
        modifiersField.setInt(CheckedSubListroot,CheckedSubListroot.getModifiers()&~Modifier.FINAL);
        (CheckedSubListsize=clazz.getDeclaredField("size")).setAccessible(true);
      }
      {
        Class<?> clazz=new BooleanArrSeq.UncheckedList().subList(0,0).getClass();
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
  private static BooleanArrSeq.CheckedList getRoot(Object seq)
  {
    try
    {
      return (BooleanArrSeq.CheckedList)CheckedSubListroot.get(seq);
    }
    catch(Exception e)
    {
      throw new RuntimeException(e);
    }
  }
  private static OmniList.OfBoolean getParent(Object seq)
  {
    try
    {
      return (OmniList.OfBoolean)CheckedSubListparent.get(seq);
    }
    catch(Exception e)
    {
      throw new RuntimeException(e);
    }
  }
  private static BooleanArrSeq.UncheckedList assertUncheckedSubListIntegrity(int expectedModCount,int expectedParentSize,Object obj)
  {
    if(!(obj instanceof BooleanArrSeq.UncheckedList))
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
    return (BooleanArrSeq.UncheckedList)obj;
  }
  private static BooleanArrSeq.CheckedList assertCheckedSubListIntegrity(int expectedModCount,int expectedParentSize,Object obj)
  {
    if(!(obj instanceof BooleanArrSeq.CheckedList))
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
    BooleanArrSeq.CheckedList root=(BooleanArrSeq.CheckedList)obj;
    Assertions.assertEquals(expectedModCount,root.modCount);
    return root;
  }
  static BooleanArrSeq.UncheckedStack buildArrSeqStackUnchecked(int seqSize,boolean initVal,int period)
  {
    boolean[] arr;
    if(seqSize==0)
    {
      arr=OmniArray.OfBoolean.DEFAULT_ARR;
    }
    else
    {
      arr=new boolean[seqSize];
      for(int i=0;i<seqSize;)
      {
        arr[i]=initVal;
        if(++i==seqSize)
        {
          break;
        }
        if(i%period==0)
        {
          initVal=!initVal;
        }
      }
    }
    return new BooleanArrSeq.UncheckedStack(seqSize,arr);
  }
  @Test
  public void testArrSeqStackUncheckedremoveIfBooleanPredicate()
  {
    Random rand=new Random(0);
    for(int seqSize=0;seqSize<=10;++seqSize)
    {
       for(int period=1,inc=Math.max(1,seqSize/10);period<=seqSize;period+=inc)
       {
         testArrSeqStackUncheckedremoveIfBooleanPredicateHelper(buildArrSeqStackUnchecked(seqSize,true,period),new BooleanMonitoredPredicate.RemoveAll(),null);
         testArrSeqStackUncheckedremoveIfBooleanPredicateHelper(buildArrSeqStackUnchecked(seqSize,false,period),new BooleanMonitoredPredicate.RemoveAll(),null);
         testArrSeqStackUncheckedremoveIfBooleanPredicateHelper(buildArrSeqStackUnchecked(seqSize,true,period),new BooleanMonitoredPredicate.RemoveNone(),null);
         testArrSeqStackUncheckedremoveIfBooleanPredicateHelper(buildArrSeqStackUnchecked(seqSize,false,period),new BooleanMonitoredPredicate.RemoveNone(),null);
         testArrSeqStackUncheckedremoveIfBooleanPredicateHelper(buildArrSeqStackUnchecked(seqSize,true,period),new BooleanMonitoredPredicate.NonThrowing(rand,0.5),null);
         testArrSeqStackUncheckedremoveIfBooleanPredicateHelper(buildArrSeqStackUnchecked(seqSize,false,period),new BooleanMonitoredPredicate.NonThrowing(rand,0.5),null);
      }
    }
  }
  private static void testArrSeqStackUncheckedremoveIfBooleanPredicateHelper(BooleanArrSeq.UncheckedStack seq,BooleanMonitoredPredicate pred,Class<? extends Throwable> expectedException)
  {
    var clone=(BooleanArrSeq.UncheckedStack)seq.clone();
    if(expectedException==null)
    {
      int seqSize=seq.size();
      int trueCount=0;
      for(var v:clone)
      {
        if(v)
        {
          ++trueCount;
        }
      }
      var result=seq.removeIf((BooleanPredicate)pred);
      Assertions.assertEquals(!pred.removedVals.isEmpty(),result);
      boolean removedValsContainsTrue=pred.removedVals.contains(true);
      boolean removedValsContainsFalse=pred.removedVals.contains(false);
      Assertions.assertEquals(removedValsContainsTrue?removedValsContainsFalse?0:seqSize-trueCount:removedValsContainsFalse?trueCount:seqSize,seq.size());
      if(removedValsContainsTrue)
      {
        Assertions.assertFalse(seq.contains(true));
      }
      if(removedValsContainsFalse)
      {
        Assertions.assertFalse(seq.contains(false));
      }
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((BooleanPredicate)pred));
      if(ConcurrentModificationException.class.equals(expectedException))
      {
        Assertions.assertEquals(seq.size(),clone.size());
        for(var val:clone)
        {
          Assertions.assertTrue(seq.contains(val));
        }
        return;
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
        var v=cloneItr.nextBoolean();
        if(expectedException==null && pred.removedVals.contains(v))
        {
          continue;
        }
        Assertions.assertTrue(seqItr.hasNext());
        Assertions.assertEquals(seqItr.nextBoolean(),v);
      }
    }
  }
  @Test
  public void testArrSeqStackUncheckedremoveIfPredicate()
  {
    Random rand=new Random(0);
    for(int seqSize=0;seqSize<=10;++seqSize)
    {
       for(int period=1,inc=Math.max(1,seqSize/10);period<=seqSize;period+=inc)
       {
         testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize,true,period),new BooleanMonitoredPredicate.RemoveAll(),null);
         testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize,false,period),new BooleanMonitoredPredicate.RemoveAll(),null);
         testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize,true,period),new BooleanMonitoredPredicate.RemoveNone(),null);
         testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize,false,period),new BooleanMonitoredPredicate.RemoveNone(),null);
         testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize,true,period),new BooleanMonitoredPredicate.NonThrowing(rand,0.5),null);
         testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize,false,period),new BooleanMonitoredPredicate.NonThrowing(rand,0.5),null);
      }
    }
  }
  private static void testArrSeqStackUncheckedremoveIfPredicateHelper(BooleanArrSeq.UncheckedStack seq,BooleanMonitoredPredicate pred,Class<? extends Throwable> expectedException)
  {
    var clone=(BooleanArrSeq.UncheckedStack)seq.clone();
    if(expectedException==null)
    {
      int seqSize=seq.size();
      int trueCount=0;
      for(var v:clone)
      {
        if(v)
        {
          ++trueCount;
        }
      }
      var result=seq.removeIf((Predicate)pred);
      Assertions.assertEquals(!pred.removedVals.isEmpty(),result);
      boolean removedValsContainsTrue=pred.removedVals.contains(true);
      boolean removedValsContainsFalse=pred.removedVals.contains(false);
      Assertions.assertEquals(removedValsContainsTrue?removedValsContainsFalse?0:seqSize-trueCount:removedValsContainsFalse?trueCount:seqSize,seq.size());
      if(removedValsContainsTrue)
      {
        Assertions.assertFalse(seq.contains(true));
      }
      if(removedValsContainsFalse)
      {
        Assertions.assertFalse(seq.contains(false));
      }
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
      var cloneItr=clone.iterator();
      var seqItr=seq.iterator();
      for(;;)
      {
        if(!cloneItr.hasNext())
        {
          Assertions.assertFalse(seqItr.hasNext());
          return;
        }
        var v=cloneItr.nextBoolean();
        if(expectedException==null && pred.removedVals.contains(v))
        {
          continue;
        }
        Assertions.assertTrue(seqItr.hasNext());
        Assertions.assertEquals(seqItr.nextBoolean(),v);
      }
    }
  }
  static BooleanArrSeq.CheckedStack buildArrSeqStackChecked(int seqSize,boolean initVal,int period)
  {
    boolean[] arr;
    if(seqSize==0)
    {
      arr=OmniArray.OfBoolean.DEFAULT_ARR;
    }
    else
    {
      arr=new boolean[seqSize];
      for(int i=0;i<seqSize;)
      {
        arr[i]=initVal;
        if(++i==seqSize)
        {
          break;
        }
        if(i%period==0)
        {
          initVal=!initVal;
        }
      }
    }
    return new BooleanArrSeq.CheckedStack(seqSize,arr);
  }
  @Test
  public void testArrSeqStackCheckedremoveIfBooleanPredicate()
  {
    Random rand=new Random(0);
    for(int seqSize=0;seqSize<=10;++seqSize)
    {
       for(int period=1,inc=Math.max(1,seqSize/10);period<=seqSize;period+=inc)
       {
         testArrSeqStackCheckedremoveIfBooleanPredicateHelper(buildArrSeqStackChecked(seqSize,true,period),new BooleanMonitoredPredicate.RemoveAll(),null);
         testArrSeqStackCheckedremoveIfBooleanPredicateHelper(buildArrSeqStackChecked(seqSize,false,period),new BooleanMonitoredPredicate.RemoveAll(),null);
         testArrSeqStackCheckedremoveIfBooleanPredicateHelper(buildArrSeqStackChecked(seqSize,true,period),new BooleanMonitoredPredicate.RemoveNone(),null);
         testArrSeqStackCheckedremoveIfBooleanPredicateHelper(buildArrSeqStackChecked(seqSize,false,period),new BooleanMonitoredPredicate.RemoveNone(),null);
         testArrSeqStackCheckedremoveIfBooleanPredicateHelper(buildArrSeqStackChecked(seqSize,true,period),new BooleanMonitoredPredicate.NonThrowing(rand,0.5),null);
         testArrSeqStackCheckedremoveIfBooleanPredicateHelper(buildArrSeqStackChecked(seqSize,false,period),new BooleanMonitoredPredicate.NonThrowing(rand,0.5),null);
         {
           var seq=buildArrSeqStackChecked(seqSize,true,period);
           testArrSeqStackCheckedremoveIfBooleanPredicateHelper(seq,new BooleanMonitoredPredicate.Throwing(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0),seqSize==0?null:IndexOutOfBoundsException.class);
         }
         {
           var seq=buildArrSeqStackChecked(seqSize,false,period);
           testArrSeqStackCheckedremoveIfBooleanPredicateHelper(seq,new BooleanMonitoredPredicate.Throwing(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0),seqSize==0?null:IndexOutOfBoundsException.class);
         }
         {
           var seq=buildArrSeqStackChecked(seqSize,true,period);
           testArrSeqStackCheckedremoveIfBooleanPredicateHelper(seq,new BooleanMonitoredPredicate.Modding(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,seq),seqSize==0?null:ConcurrentModificationException.class);
         }
         {
           var seq=buildArrSeqStackChecked(seqSize,false,period);
           testArrSeqStackCheckedremoveIfBooleanPredicateHelper(seq,new BooleanMonitoredPredicate.Modding(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,seq),seqSize==0?null:ConcurrentModificationException.class);
         }
         {
           var seq=buildArrSeqStackChecked(seqSize,true,period);
           testArrSeqStackCheckedremoveIfBooleanPredicateHelper(seq,new BooleanMonitoredPredicate.ModdingAndThrowing(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,seq),seqSize==0?null:ConcurrentModificationException.class);
         }
         {
           var seq=buildArrSeqStackChecked(seqSize,false,period);
           testArrSeqStackCheckedremoveIfBooleanPredicateHelper(seq,new BooleanMonitoredPredicate.ModdingAndThrowing(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,seq),seqSize==0?null:ConcurrentModificationException.class);
         }
      }
    }
  }
  private static void testArrSeqStackCheckedremoveIfBooleanPredicateHelper(BooleanArrSeq.CheckedStack seq,BooleanMonitoredPredicate pred,Class<? extends Throwable> expectedException)
  {
    var clone=(BooleanArrSeq.CheckedStack)seq.clone();
    if(expectedException==null)
    {
      int seqSize=seq.size();
      int trueCount=0;
      for(var v:clone)
      {
        if(v)
        {
          ++trueCount;
        }
      }
      var result=seq.removeIf((BooleanPredicate)pred);
      Assertions.assertEquals(!pred.removedVals.isEmpty(),result);
      Assertions.assertEquals(result?1:0,seq.modCount);
      boolean removedValsContainsTrue=pred.removedVals.contains(true);
      boolean removedValsContainsFalse=pred.removedVals.contains(false);
      Assertions.assertEquals(removedValsContainsTrue?removedValsContainsFalse?0:seqSize-trueCount:removedValsContainsFalse?trueCount:seqSize,seq.size());
      if(removedValsContainsTrue)
      {
        Assertions.assertFalse(seq.contains(true));
      }
      if(removedValsContainsFalse)
      {
        Assertions.assertFalse(seq.contains(false));
      }
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((BooleanPredicate)pred));
      if(ConcurrentModificationException.class.equals(expectedException))
      {
        Assertions.assertEquals(seq.size(),clone.size());
        for(var val:clone)
        {
          Assertions.assertTrue(seq.contains(val));
        }
        return;
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
        var v=cloneItr.nextBoolean();
        if(expectedException==null && pred.removedVals.contains(v))
        {
          continue;
        }
        Assertions.assertTrue(seqItr.hasNext());
        Assertions.assertEquals(seqItr.nextBoolean(),v);
      }
    }
  }
  @Test
  public void testArrSeqStackCheckedremoveIfPredicate()
  {
    Random rand=new Random(0);
    for(int seqSize=0;seqSize<=10;++seqSize)
    {
       for(int period=1,inc=Math.max(1,seqSize/10);period<=seqSize;period+=inc)
       {
         testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize,true,period),new BooleanMonitoredPredicate.RemoveAll(),null);
         testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize,false,period),new BooleanMonitoredPredicate.RemoveAll(),null);
         testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize,true,period),new BooleanMonitoredPredicate.RemoveNone(),null);
         testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize,false,period),new BooleanMonitoredPredicate.RemoveNone(),null);
         testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize,true,period),new BooleanMonitoredPredicate.NonThrowing(rand,0.5),null);
         testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize,false,period),new BooleanMonitoredPredicate.NonThrowing(rand,0.5),null);
         {
           var seq=buildArrSeqStackChecked(seqSize,true,period);
           testArrSeqStackCheckedremoveIfPredicateHelper(seq,new BooleanMonitoredPredicate.Throwing(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0),seqSize==0?null:IndexOutOfBoundsException.class);
         }
         {
           var seq=buildArrSeqStackChecked(seqSize,false,period);
           testArrSeqStackCheckedremoveIfPredicateHelper(seq,new BooleanMonitoredPredicate.Throwing(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0),seqSize==0?null:IndexOutOfBoundsException.class);
         }
         {
           var seq=buildArrSeqStackChecked(seqSize,true,period);
           testArrSeqStackCheckedremoveIfPredicateHelper(seq,new BooleanMonitoredPredicate.Modding(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,seq),seqSize==0?null:ConcurrentModificationException.class);
         }
         {
           var seq=buildArrSeqStackChecked(seqSize,false,period);
           testArrSeqStackCheckedremoveIfPredicateHelper(seq,new BooleanMonitoredPredicate.Modding(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,seq),seqSize==0?null:ConcurrentModificationException.class);
         }
         {
           var seq=buildArrSeqStackChecked(seqSize,true,period);
           testArrSeqStackCheckedremoveIfPredicateHelper(seq,new BooleanMonitoredPredicate.ModdingAndThrowing(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,seq),seqSize==0?null:ConcurrentModificationException.class);
         }
         {
           var seq=buildArrSeqStackChecked(seqSize,false,period);
           testArrSeqStackCheckedremoveIfPredicateHelper(seq,new BooleanMonitoredPredicate.ModdingAndThrowing(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,seq),seqSize==0?null:ConcurrentModificationException.class);
         }
      }
    }
  }
  private static void testArrSeqStackCheckedremoveIfPredicateHelper(BooleanArrSeq.CheckedStack seq,BooleanMonitoredPredicate pred,Class<? extends Throwable> expectedException)
  {
    var clone=(BooleanArrSeq.CheckedStack)seq.clone();
    if(expectedException==null)
    {
      int seqSize=seq.size();
      int trueCount=0;
      for(var v:clone)
      {
        if(v)
        {
          ++trueCount;
        }
      }
      var result=seq.removeIf((Predicate)pred);
      Assertions.assertEquals(!pred.removedVals.isEmpty(),result);
      Assertions.assertEquals(result?1:0,seq.modCount);
      boolean removedValsContainsTrue=pred.removedVals.contains(true);
      boolean removedValsContainsFalse=pred.removedVals.contains(false);
      Assertions.assertEquals(removedValsContainsTrue?removedValsContainsFalse?0:seqSize-trueCount:removedValsContainsFalse?trueCount:seqSize,seq.size());
      if(removedValsContainsTrue)
      {
        Assertions.assertFalse(seq.contains(true));
      }
      if(removedValsContainsFalse)
      {
        Assertions.assertFalse(seq.contains(false));
      }
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
      var cloneItr=clone.iterator();
      var seqItr=seq.iterator();
      for(;;)
      {
        if(!cloneItr.hasNext())
        {
          Assertions.assertFalse(seqItr.hasNext());
          return;
        }
        var v=cloneItr.nextBoolean();
        if(expectedException==null && pred.removedVals.contains(v))
        {
          continue;
        }
        Assertions.assertTrue(seqItr.hasNext());
        Assertions.assertEquals(seqItr.nextBoolean(),v);
      }
    }
  }
  static BooleanArrSeq.UncheckedList buildArrSeqListUnchecked(int seqSize,boolean initVal,int period)
  {
    boolean[] arr;
    if(seqSize==0)
    {
      arr=OmniArray.OfBoolean.DEFAULT_ARR;
    }
    else
    {
      arr=new boolean[seqSize];
      for(int i=0;i<seqSize;)
      {
        arr[i]=initVal;
        if(++i==seqSize)
        {
          break;
        }
        if(i%period==0)
        {
          initVal=!initVal;
        }
      }
    }
    return new BooleanArrSeq.UncheckedList(seqSize,arr);
  }
  @Test
  public void testArrSeqListUncheckedremoveIfBooleanPredicate()
  {
    Random rand=new Random(0);
    for(int seqSize=0;seqSize<=10;++seqSize)
    {
       for(int period=1,inc=Math.max(1,seqSize/10);period<=seqSize;period+=inc)
       {
         testArrSeqListUncheckedremoveIfBooleanPredicateHelper(buildArrSeqListUnchecked(seqSize,true,period),new BooleanMonitoredPredicate.RemoveAll(),null);
         testArrSeqListUncheckedremoveIfBooleanPredicateHelper(buildArrSeqListUnchecked(seqSize,false,period),new BooleanMonitoredPredicate.RemoveAll(),null);
         testArrSeqListUncheckedremoveIfBooleanPredicateHelper(buildArrSeqListUnchecked(seqSize,true,period),new BooleanMonitoredPredicate.RemoveNone(),null);
         testArrSeqListUncheckedremoveIfBooleanPredicateHelper(buildArrSeqListUnchecked(seqSize,false,period),new BooleanMonitoredPredicate.RemoveNone(),null);
         testArrSeqListUncheckedremoveIfBooleanPredicateHelper(buildArrSeqListUnchecked(seqSize,true,period),new BooleanMonitoredPredicate.NonThrowing(rand,0.5),null);
         testArrSeqListUncheckedremoveIfBooleanPredicateHelper(buildArrSeqListUnchecked(seqSize,false,period),new BooleanMonitoredPredicate.NonThrowing(rand,0.5),null);
      }
    }
  }
  private static void testArrSeqListUncheckedremoveIfBooleanPredicateHelper(BooleanArrSeq.UncheckedList seq,BooleanMonitoredPredicate pred,Class<? extends Throwable> expectedException)
  {
    var clone=(BooleanArrSeq.UncheckedList)seq.clone();
    if(expectedException==null)
    {
      int seqSize=seq.size();
      int trueCount=0;
      for(var v:clone)
      {
        if(v)
        {
          ++trueCount;
        }
      }
      var result=seq.removeIf((BooleanPredicate)pred);
      Assertions.assertEquals(!pred.removedVals.isEmpty(),result);
      boolean removedValsContainsTrue=pred.removedVals.contains(true);
      boolean removedValsContainsFalse=pred.removedVals.contains(false);
      Assertions.assertEquals(removedValsContainsTrue?removedValsContainsFalse?0:seqSize-trueCount:removedValsContainsFalse?trueCount:seqSize,seq.size());
      if(removedValsContainsTrue)
      {
        Assertions.assertFalse(seq.contains(true));
      }
      if(removedValsContainsFalse)
      {
        Assertions.assertFalse(seq.contains(false));
      }
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((BooleanPredicate)pred));
      if(ConcurrentModificationException.class.equals(expectedException))
      {
        Assertions.assertEquals(seq.size(),clone.size());
        for(var val:clone)
        {
          Assertions.assertTrue(seq.contains(val));
        }
        return;
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
        var v=cloneItr.nextBoolean();
        if(expectedException==null && pred.removedVals.contains(v))
        {
          continue;
        }
        Assertions.assertTrue(seqItr.hasNext());
        Assertions.assertEquals(seqItr.nextBoolean(),v);
      }
    }
  }
  @Test
  public void testArrSeqListUncheckedremoveIfPredicate()
  {
    Random rand=new Random(0);
    for(int seqSize=0;seqSize<=10;++seqSize)
    {
       for(int period=1,inc=Math.max(1,seqSize/10);period<=seqSize;period+=inc)
       {
         testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize,true,period),new BooleanMonitoredPredicate.RemoveAll(),null);
         testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize,false,period),new BooleanMonitoredPredicate.RemoveAll(),null);
         testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize,true,period),new BooleanMonitoredPredicate.RemoveNone(),null);
         testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize,false,period),new BooleanMonitoredPredicate.RemoveNone(),null);
         testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize,true,period),new BooleanMonitoredPredicate.NonThrowing(rand,0.5),null);
         testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize,false,period),new BooleanMonitoredPredicate.NonThrowing(rand,0.5),null);
      }
    }
  }
  private static void testArrSeqListUncheckedremoveIfPredicateHelper(BooleanArrSeq.UncheckedList seq,BooleanMonitoredPredicate pred,Class<? extends Throwable> expectedException)
  {
    var clone=(BooleanArrSeq.UncheckedList)seq.clone();
    if(expectedException==null)
    {
      int seqSize=seq.size();
      int trueCount=0;
      for(var v:clone)
      {
        if(v)
        {
          ++trueCount;
        }
      }
      var result=seq.removeIf((Predicate)pred);
      Assertions.assertEquals(!pred.removedVals.isEmpty(),result);
      boolean removedValsContainsTrue=pred.removedVals.contains(true);
      boolean removedValsContainsFalse=pred.removedVals.contains(false);
      Assertions.assertEquals(removedValsContainsTrue?removedValsContainsFalse?0:seqSize-trueCount:removedValsContainsFalse?trueCount:seqSize,seq.size());
      if(removedValsContainsTrue)
      {
        Assertions.assertFalse(seq.contains(true));
      }
      if(removedValsContainsFalse)
      {
        Assertions.assertFalse(seq.contains(false));
      }
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
      var cloneItr=clone.iterator();
      var seqItr=seq.iterator();
      for(;;)
      {
        if(!cloneItr.hasNext())
        {
          Assertions.assertFalse(seqItr.hasNext());
          return;
        }
        var v=cloneItr.nextBoolean();
        if(expectedException==null && pred.removedVals.contains(v))
        {
          continue;
        }
        Assertions.assertTrue(seqItr.hasNext());
        Assertions.assertEquals(seqItr.nextBoolean(),v);
      }
    }
  }
  static BooleanArrSeq.CheckedList buildArrSeqListChecked(int seqSize,boolean initVal,int period)
  {
    boolean[] arr;
    if(seqSize==0)
    {
      arr=OmniArray.OfBoolean.DEFAULT_ARR;
    }
    else
    {
      arr=new boolean[seqSize];
      for(int i=0;i<seqSize;)
      {
        arr[i]=initVal;
        if(++i==seqSize)
        {
          break;
        }
        if(i%period==0)
        {
          initVal=!initVal;
        }
      }
    }
    return new BooleanArrSeq.CheckedList(seqSize,arr);
  }
  @Test
  public void testArrSeqListCheckedremoveIfBooleanPredicate()
  {
    Random rand=new Random(0);
    for(int seqSize=0;seqSize<=10;++seqSize)
    {
       for(int period=1,inc=Math.max(1,seqSize/10);period<=seqSize;period+=inc)
       {
         testArrSeqListCheckedremoveIfBooleanPredicateHelper(buildArrSeqListChecked(seqSize,true,period),new BooleanMonitoredPredicate.RemoveAll(),null);
         testArrSeqListCheckedremoveIfBooleanPredicateHelper(buildArrSeqListChecked(seqSize,false,period),new BooleanMonitoredPredicate.RemoveAll(),null);
         testArrSeqListCheckedremoveIfBooleanPredicateHelper(buildArrSeqListChecked(seqSize,true,period),new BooleanMonitoredPredicate.RemoveNone(),null);
         testArrSeqListCheckedremoveIfBooleanPredicateHelper(buildArrSeqListChecked(seqSize,false,period),new BooleanMonitoredPredicate.RemoveNone(),null);
         testArrSeqListCheckedremoveIfBooleanPredicateHelper(buildArrSeqListChecked(seqSize,true,period),new BooleanMonitoredPredicate.NonThrowing(rand,0.5),null);
         testArrSeqListCheckedremoveIfBooleanPredicateHelper(buildArrSeqListChecked(seqSize,false,period),new BooleanMonitoredPredicate.NonThrowing(rand,0.5),null);
         {
           var seq=buildArrSeqListChecked(seqSize,true,period);
           testArrSeqListCheckedremoveIfBooleanPredicateHelper(seq,new BooleanMonitoredPredicate.Throwing(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0),seqSize==0?null:IndexOutOfBoundsException.class);
         }
         {
           var seq=buildArrSeqListChecked(seqSize,false,period);
           testArrSeqListCheckedremoveIfBooleanPredicateHelper(seq,new BooleanMonitoredPredicate.Throwing(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0),seqSize==0?null:IndexOutOfBoundsException.class);
         }
         {
           var seq=buildArrSeqListChecked(seqSize,true,period);
           testArrSeqListCheckedremoveIfBooleanPredicateHelper(seq,new BooleanMonitoredPredicate.Modding(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,seq),seqSize==0?null:ConcurrentModificationException.class);
         }
         {
           var seq=buildArrSeqListChecked(seqSize,false,period);
           testArrSeqListCheckedremoveIfBooleanPredicateHelper(seq,new BooleanMonitoredPredicate.Modding(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,seq),seqSize==0?null:ConcurrentModificationException.class);
         }
         {
           var seq=buildArrSeqListChecked(seqSize,true,period);
           testArrSeqListCheckedremoveIfBooleanPredicateHelper(seq,new BooleanMonitoredPredicate.ModdingAndThrowing(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,seq),seqSize==0?null:ConcurrentModificationException.class);
         }
         {
           var seq=buildArrSeqListChecked(seqSize,false,period);
           testArrSeqListCheckedremoveIfBooleanPredicateHelper(seq,new BooleanMonitoredPredicate.ModdingAndThrowing(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,seq),seqSize==0?null:ConcurrentModificationException.class);
         }
      }
    }
  }
  private static void testArrSeqListCheckedremoveIfBooleanPredicateHelper(BooleanArrSeq.CheckedList seq,BooleanMonitoredPredicate pred,Class<? extends Throwable> expectedException)
  {
    var clone=(BooleanArrSeq.CheckedList)seq.clone();
    if(expectedException==null)
    {
      int seqSize=seq.size();
      int trueCount=0;
      for(var v:clone)
      {
        if(v)
        {
          ++trueCount;
        }
      }
      var result=seq.removeIf((BooleanPredicate)pred);
      Assertions.assertEquals(!pred.removedVals.isEmpty(),result);
      Assertions.assertEquals(result?1:0,seq.modCount);
      boolean removedValsContainsTrue=pred.removedVals.contains(true);
      boolean removedValsContainsFalse=pred.removedVals.contains(false);
      Assertions.assertEquals(removedValsContainsTrue?removedValsContainsFalse?0:seqSize-trueCount:removedValsContainsFalse?trueCount:seqSize,seq.size());
      if(removedValsContainsTrue)
      {
        Assertions.assertFalse(seq.contains(true));
      }
      if(removedValsContainsFalse)
      {
        Assertions.assertFalse(seq.contains(false));
      }
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((BooleanPredicate)pred));
      if(ConcurrentModificationException.class.equals(expectedException))
      {
        Assertions.assertEquals(seq.size(),clone.size());
        for(var val:clone)
        {
          Assertions.assertTrue(seq.contains(val));
        }
        return;
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
        var v=cloneItr.nextBoolean();
        if(expectedException==null && pred.removedVals.contains(v))
        {
          continue;
        }
        Assertions.assertTrue(seqItr.hasNext());
        Assertions.assertEquals(seqItr.nextBoolean(),v);
      }
    }
  }
  @Test
  public void testArrSeqListCheckedremoveIfPredicate()
  {
    Random rand=new Random(0);
    for(int seqSize=0;seqSize<=10;++seqSize)
    {
       for(int period=1,inc=Math.max(1,seqSize/10);period<=seqSize;period+=inc)
       {
         testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize,true,period),new BooleanMonitoredPredicate.RemoveAll(),null);
         testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize,false,period),new BooleanMonitoredPredicate.RemoveAll(),null);
         testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize,true,period),new BooleanMonitoredPredicate.RemoveNone(),null);
         testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize,false,period),new BooleanMonitoredPredicate.RemoveNone(),null);
         testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize,true,period),new BooleanMonitoredPredicate.NonThrowing(rand,0.5),null);
         testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize,false,period),new BooleanMonitoredPredicate.NonThrowing(rand,0.5),null);
         {
           var seq=buildArrSeqListChecked(seqSize,true,period);
           testArrSeqListCheckedremoveIfPredicateHelper(seq,new BooleanMonitoredPredicate.Throwing(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0),seqSize==0?null:IndexOutOfBoundsException.class);
         }
         {
           var seq=buildArrSeqListChecked(seqSize,false,period);
           testArrSeqListCheckedremoveIfPredicateHelper(seq,new BooleanMonitoredPredicate.Throwing(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0),seqSize==0?null:IndexOutOfBoundsException.class);
         }
         {
           var seq=buildArrSeqListChecked(seqSize,true,period);
           testArrSeqListCheckedremoveIfPredicateHelper(seq,new BooleanMonitoredPredicate.Modding(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,seq),seqSize==0?null:ConcurrentModificationException.class);
         }
         {
           var seq=buildArrSeqListChecked(seqSize,false,period);
           testArrSeqListCheckedremoveIfPredicateHelper(seq,new BooleanMonitoredPredicate.Modding(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,seq),seqSize==0?null:ConcurrentModificationException.class);
         }
         {
           var seq=buildArrSeqListChecked(seqSize,true,period);
           testArrSeqListCheckedremoveIfPredicateHelper(seq,new BooleanMonitoredPredicate.ModdingAndThrowing(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,seq),seqSize==0?null:ConcurrentModificationException.class);
         }
         {
           var seq=buildArrSeqListChecked(seqSize,false,period);
           testArrSeqListCheckedremoveIfPredicateHelper(seq,new BooleanMonitoredPredicate.ModdingAndThrowing(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,seq),seqSize==0?null:ConcurrentModificationException.class);
         }
      }
    }
  }
  private static void testArrSeqListCheckedremoveIfPredicateHelper(BooleanArrSeq.CheckedList seq,BooleanMonitoredPredicate pred,Class<? extends Throwable> expectedException)
  {
    var clone=(BooleanArrSeq.CheckedList)seq.clone();
    if(expectedException==null)
    {
      int seqSize=seq.size();
      int trueCount=0;
      for(var v:clone)
      {
        if(v)
        {
          ++trueCount;
        }
      }
      var result=seq.removeIf((Predicate)pred);
      Assertions.assertEquals(!pred.removedVals.isEmpty(),result);
      Assertions.assertEquals(result?1:0,seq.modCount);
      boolean removedValsContainsTrue=pred.removedVals.contains(true);
      boolean removedValsContainsFalse=pred.removedVals.contains(false);
      Assertions.assertEquals(removedValsContainsTrue?removedValsContainsFalse?0:seqSize-trueCount:removedValsContainsFalse?trueCount:seqSize,seq.size());
      if(removedValsContainsTrue)
      {
        Assertions.assertFalse(seq.contains(true));
      }
      if(removedValsContainsFalse)
      {
        Assertions.assertFalse(seq.contains(false));
      }
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
      var cloneItr=clone.iterator();
      var seqItr=seq.iterator();
      for(;;)
      {
        if(!cloneItr.hasNext())
        {
          Assertions.assertFalse(seqItr.hasNext());
          return;
        }
        var v=cloneItr.nextBoolean();
        if(expectedException==null && pred.removedVals.contains(v))
        {
          continue;
        }
        Assertions.assertTrue(seqItr.hasNext());
        Assertions.assertEquals(seqItr.nextBoolean(),v);
      }
    }
  }
  static OmniList.OfBoolean buildArrSeqSubListUnchecked(int seqSize,boolean initVal,int period,int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc)
  {
    boolean[] arr;
    int rootSize=rootPreAlloc+parentPreAlloc+seqSize+parentPostAlloc+rootPostAlloc;
    if(rootSize==0)
    {
      arr=OmniArray.OfBoolean.DEFAULT_ARR;
    }
    else
    {
      arr=new boolean[rootSize];
      for(int i=0;i<rootPreAlloc+parentPreAlloc;++i)
      {
        arr[i]=!initVal;
      }
      for(int i=rootPreAlloc+parentPreAlloc+seqSize;i<rootSize;++i)
      {
        arr[i]=!initVal;
      }
      for(int i=rootPreAlloc+parentPreAlloc;;)
      {
        arr[i]=initVal;
        if(++i==rootPreAlloc+parentPreAlloc+seqSize)
        {
          break;
        }
        if(i%period==0)
        {
          initVal=!initVal;
        }
      }
    }
    return new BooleanArrSeq.CheckedList(rootSize,arr).subList(rootPreAlloc,parentPreAlloc+seqSize+parentPostAlloc).subList(parentPreAlloc,parentPreAlloc+seqSize);
  }
  @ParameterizedTest(name="BooleanArrSeq.UncheckedSubList rootPreAlloc={0},parentPreAlloc={1},parentPostAlloc={2},rootPostAlloc={3}")
  @MethodSource("subListAllocationArgumentProvider")
  public void testArrSeqSubListUncheckedremoveIfBooleanPredicate(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc)
  {
    Random rand=new Random(0);
    for(int seqSize=0;seqSize<=10;++seqSize)
    {
       for(int period=1,inc=Math.max(1,seqSize/10);period<=seqSize;period+=inc)
       {
         testArrSeqSubListUncheckedremoveIfBooleanPredicateHelper(true,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,true,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new BooleanMonitoredPredicate.RemoveAll(),null);
         testArrSeqSubListUncheckedremoveIfBooleanPredicateHelper(false,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,false,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new BooleanMonitoredPredicate.RemoveAll(),null);
         testArrSeqSubListUncheckedremoveIfBooleanPredicateHelper(true,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,true,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new BooleanMonitoredPredicate.RemoveNone(),null);
         testArrSeqSubListUncheckedremoveIfBooleanPredicateHelper(false,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,false,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new BooleanMonitoredPredicate.RemoveNone(),null);
         testArrSeqSubListUncheckedremoveIfBooleanPredicateHelper(true,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,true,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new BooleanMonitoredPredicate.NonThrowing(rand,0.5),null);
         testArrSeqSubListUncheckedremoveIfBooleanPredicateHelper(false,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,false,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new BooleanMonitoredPredicate.NonThrowing(rand,0.5),null);
      }
    }
  }
  private static void testArrSeqSubListUncheckedremoveIfBooleanPredicateHelper(boolean initVal,int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc,OmniList.OfBoolean seq,BooleanMonitoredPredicate pred,Class<? extends Throwable> expectedException)
  {
    var clone=(BooleanArrSeq.UncheckedList)seq.clone();
    int expectedSize;
    BooleanArrSeq.UncheckedList root;
    if(expectedException==null)
    {
      int seqSize=seq.size();
      int trueCount=0;
      for(var v:clone)
      {
        if(v)
        {
          ++trueCount;
        }
      }
      var result=seq.removeIf((BooleanPredicate)pred);
      Assertions.assertEquals(!pred.removedVals.isEmpty(),result);
      boolean removedValsContainsTrue=pred.removedVals.contains(true);
      boolean removedValsContainsFalse=pred.removedVals.contains(false);
      expectedSize=removedValsContainsTrue?removedValsContainsFalse?0:seqSize-trueCount:removedValsContainsFalse?trueCount:seqSize;
      Assertions.assertEquals(expectedSize,seq.size());
      if(removedValsContainsTrue)
      {
        Assertions.assertFalse(seq.contains(true));
      }
      if(removedValsContainsFalse)
      {
        Assertions.assertFalse(seq.contains(false));
      }
      root=assertUncheckedSubListIntegrity(result?1:0,parentPreAlloc+parentPostAlloc+expectedSize,seq);
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((BooleanPredicate)pred));
      if(ConcurrentModificationException.class.equals(expectedException))
      {
        return;
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
        var v=cloneItr.nextBoolean();
        if(expectedException==null && pred.removedVals.contains(v))
        {
          continue;
        }
        Assertions.assertTrue(seqItr.hasNext());
        Assertions.assertEquals(seqItr.nextBoolean(),v);
      }
      expectedSize=seq.size();
      root=assertUncheckedSubListIntegrity(0,parentPreAlloc+parentPostAlloc+expectedSize,seq);
    }
    Assertions.assertEquals(parentPreAlloc+parentPostAlloc+expectedSize+rootPreAlloc+rootPostAlloc,root.size);
    for(int i=0,bound=rootPreAlloc+parentPreAlloc;i<bound;++i)
    {
      Assertions.assertEquals(!initVal,root.arr[i]);
    }
    for(int i=rootPreAlloc+parentPreAlloc+expectedSize,bound=parentPreAlloc+parentPostAlloc+expectedSize+rootPreAlloc+rootPostAlloc;i<bound;++i)
    {
      Assertions.assertEquals(!initVal,root.arr[i]);
    }
  }
  @ParameterizedTest(name="BooleanArrSeq.UncheckedSubList rootPreAlloc={0},parentPreAlloc={1},parentPostAlloc={2},rootPostAlloc={3}")
  @MethodSource("subListAllocationArgumentProvider")
  public void testArrSeqSubListUncheckedremoveIfPredicate(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc)
  {
    Random rand=new Random(0);
    for(int seqSize=0;seqSize<=10;++seqSize)
    {
       for(int period=1,inc=Math.max(1,seqSize/10);period<=seqSize;period+=inc)
       {
         testArrSeqSubListUncheckedremoveIfPredicateHelper(true,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,true,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new BooleanMonitoredPredicate.RemoveAll(),null);
         testArrSeqSubListUncheckedremoveIfPredicateHelper(false,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,false,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new BooleanMonitoredPredicate.RemoveAll(),null);
         testArrSeqSubListUncheckedremoveIfPredicateHelper(true,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,true,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new BooleanMonitoredPredicate.RemoveNone(),null);
         testArrSeqSubListUncheckedremoveIfPredicateHelper(false,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,false,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new BooleanMonitoredPredicate.RemoveNone(),null);
         testArrSeqSubListUncheckedremoveIfPredicateHelper(true,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,true,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new BooleanMonitoredPredicate.NonThrowing(rand,0.5),null);
         testArrSeqSubListUncheckedremoveIfPredicateHelper(false,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,false,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new BooleanMonitoredPredicate.NonThrowing(rand,0.5),null);
      }
    }
  }
  private static void testArrSeqSubListUncheckedremoveIfPredicateHelper(boolean initVal,int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc,OmniList.OfBoolean seq,BooleanMonitoredPredicate pred,Class<? extends Throwable> expectedException)
  {
    var clone=(BooleanArrSeq.UncheckedList)seq.clone();
    int expectedSize;
    BooleanArrSeq.UncheckedList root;
    if(expectedException==null)
    {
      int seqSize=seq.size();
      int trueCount=0;
      for(var v:clone)
      {
        if(v)
        {
          ++trueCount;
        }
      }
      var result=seq.removeIf((Predicate)pred);
      Assertions.assertEquals(!pred.removedVals.isEmpty(),result);
      boolean removedValsContainsTrue=pred.removedVals.contains(true);
      boolean removedValsContainsFalse=pred.removedVals.contains(false);
      expectedSize=removedValsContainsTrue?removedValsContainsFalse?0:seqSize-trueCount:removedValsContainsFalse?trueCount:seqSize;
      Assertions.assertEquals(expectedSize,seq.size());
      if(removedValsContainsTrue)
      {
        Assertions.assertFalse(seq.contains(true));
      }
      if(removedValsContainsFalse)
      {
        Assertions.assertFalse(seq.contains(false));
      }
      root=assertUncheckedSubListIntegrity(result?1:0,parentPreAlloc+parentPostAlloc+expectedSize,seq);
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((Predicate)pred));
      if(ConcurrentModificationException.class.equals(expectedException))
      {
        return;
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
        var v=cloneItr.nextBoolean();
        if(expectedException==null && pred.removedVals.contains(v))
        {
          continue;
        }
        Assertions.assertTrue(seqItr.hasNext());
        Assertions.assertEquals(seqItr.nextBoolean(),v);
      }
      expectedSize=seq.size();
      root=assertUncheckedSubListIntegrity(0,parentPreAlloc+parentPostAlloc+expectedSize,seq);
    }
    Assertions.assertEquals(parentPreAlloc+parentPostAlloc+expectedSize+rootPreAlloc+rootPostAlloc,root.size);
    for(int i=0,bound=rootPreAlloc+parentPreAlloc;i<bound;++i)
    {
      Assertions.assertEquals(!initVal,root.arr[i]);
    }
    for(int i=rootPreAlloc+parentPreAlloc+expectedSize,bound=parentPreAlloc+parentPostAlloc+expectedSize+rootPreAlloc+rootPostAlloc;i<bound;++i)
    {
      Assertions.assertEquals(!initVal,root.arr[i]);
    }
  }
  static OmniList.OfBoolean buildArrSeqSubListChecked(int seqSize,boolean initVal,int period,int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc)
  {
    boolean[] arr;
    int rootSize=rootPreAlloc+parentPreAlloc+seqSize+parentPostAlloc+rootPostAlloc;
    if(rootSize==0)
    {
      arr=OmniArray.OfBoolean.DEFAULT_ARR;
    }
    else
    {
      arr=new boolean[rootSize];
      for(int i=0;i<rootPreAlloc+parentPreAlloc;++i)
      {
        arr[i]=!initVal;
      }
      for(int i=rootPreAlloc+parentPreAlloc+seqSize;i<rootSize;++i)
      {
        arr[i]=!initVal;
      }
      for(int i=rootPreAlloc+parentPreAlloc;;)
      {
        arr[i]=initVal;
        if(++i==rootPreAlloc+parentPreAlloc+seqSize)
        {
          break;
        }
        if(i%period==0)
        {
          initVal=!initVal;
        }
      }
    }
    return new BooleanArrSeq.CheckedList(rootSize,arr).subList(rootPreAlloc,parentPreAlloc+seqSize+parentPostAlloc).subList(parentPreAlloc,parentPreAlloc+seqSize);
  }
  @ParameterizedTest(name="BooleanArrSeq.CheckedSubList rootPreAlloc={0},parentPreAlloc={1},parentPostAlloc={2},rootPostAlloc={3}")
  @MethodSource("subListAllocationArgumentProvider")
  public void testArrSeqSubListCheckedremoveIfBooleanPredicate(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc)
  {
    Random rand=new Random(0);
    for(int seqSize=0;seqSize<=10;++seqSize)
    {
       for(int period=1,inc=Math.max(1,seqSize/10);period<=seqSize;period+=inc)
       {
         testArrSeqSubListCheckedremoveIfBooleanPredicateHelper(true,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,true,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new BooleanMonitoredPredicate.RemoveAll(),null);
         testArrSeqSubListCheckedremoveIfBooleanPredicateHelper(false,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,false,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new BooleanMonitoredPredicate.RemoveAll(),null);
         testArrSeqSubListCheckedremoveIfBooleanPredicateHelper(true,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,true,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new BooleanMonitoredPredicate.RemoveNone(),null);
         testArrSeqSubListCheckedremoveIfBooleanPredicateHelper(false,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,false,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new BooleanMonitoredPredicate.RemoveNone(),null);
         testArrSeqSubListCheckedremoveIfBooleanPredicateHelper(true,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,true,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new BooleanMonitoredPredicate.NonThrowing(rand,0.5),null);
         testArrSeqSubListCheckedremoveIfBooleanPredicateHelper(false,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,false,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new BooleanMonitoredPredicate.NonThrowing(rand,0.5),null);
         {
           var seq=buildArrSeqSubListChecked(seqSize,true,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
           testArrSeqSubListCheckedremoveIfBooleanPredicateHelper(true,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new BooleanMonitoredPredicate.Throwing(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0),seqSize==0?null:IndexOutOfBoundsException.class);
         }
         {
           var seq=buildArrSeqSubListChecked(seqSize,false,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
           testArrSeqSubListCheckedremoveIfBooleanPredicateHelper(false,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new BooleanMonitoredPredicate.Throwing(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0),seqSize==0?null:IndexOutOfBoundsException.class);
         }
         {
           var seq=buildArrSeqSubListChecked(seqSize,true,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
           testArrSeqSubListCheckedremoveIfBooleanPredicateHelper(true,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new BooleanMonitoredPredicate.Modding(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,seq),seqSize==0?null:ConcurrentModificationException.class);
         }
         {
           var seq=buildArrSeqSubListChecked(seqSize,false,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
           testArrSeqSubListCheckedremoveIfBooleanPredicateHelper(false,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new BooleanMonitoredPredicate.Modding(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,seq),seqSize==0?null:ConcurrentModificationException.class);
         }
         {
           var seq=buildArrSeqSubListChecked(seqSize,true,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
           testArrSeqSubListCheckedremoveIfBooleanPredicateHelper(true,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new BooleanMonitoredPredicate.ModdingAndThrowing(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,seq),seqSize==0?null:ConcurrentModificationException.class);
         }
         {
           var seq=buildArrSeqSubListChecked(seqSize,false,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
           testArrSeqSubListCheckedremoveIfBooleanPredicateHelper(false,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new BooleanMonitoredPredicate.ModdingAndThrowing(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,seq),seqSize==0?null:ConcurrentModificationException.class);
         }
         {
           var seq=buildArrSeqSubListChecked(seqSize,true,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
           testArrSeqSubListCheckedremoveIfBooleanPredicateHelper(true,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new BooleanMonitoredPredicate.Modding(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,getParent(seq)),seqSize==0?null:ConcurrentModificationException.class);
         }
         {
           var seq=buildArrSeqSubListChecked(seqSize,false,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
           testArrSeqSubListCheckedremoveIfBooleanPredicateHelper(false,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new BooleanMonitoredPredicate.Modding(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,getParent(seq)),seqSize==0?null:ConcurrentModificationException.class);
         }
         {
           var seq=buildArrSeqSubListChecked(seqSize,true,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
           testArrSeqSubListCheckedremoveIfBooleanPredicateHelper(true,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new BooleanMonitoredPredicate.ModdingAndThrowing(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,getParent(seq)),seqSize==0?null:ConcurrentModificationException.class);
         }
         {
           var seq=buildArrSeqSubListChecked(seqSize,false,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
           testArrSeqSubListCheckedremoveIfBooleanPredicateHelper(false,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new BooleanMonitoredPredicate.ModdingAndThrowing(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,getParent(seq)),seqSize==0?null:ConcurrentModificationException.class);
         }
         {
           var seq=buildArrSeqSubListChecked(seqSize,true,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
           testArrSeqSubListCheckedremoveIfBooleanPredicateHelper(true,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new BooleanMonitoredPredicate.Modding(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,getRoot(seq)),seqSize==0?null:ConcurrentModificationException.class);
         }
         {
           var seq=buildArrSeqSubListChecked(seqSize,false,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
           testArrSeqSubListCheckedremoveIfBooleanPredicateHelper(false,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new BooleanMonitoredPredicate.Modding(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,getRoot(seq)),seqSize==0?null:ConcurrentModificationException.class);
         }
         {
           var seq=buildArrSeqSubListChecked(seqSize,true,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
           testArrSeqSubListCheckedremoveIfBooleanPredicateHelper(true,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new BooleanMonitoredPredicate.ModdingAndThrowing(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,getRoot(seq)),seqSize==0?null:ConcurrentModificationException.class);
         }
         {
           var seq=buildArrSeqSubListChecked(seqSize,false,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
           testArrSeqSubListCheckedremoveIfBooleanPredicateHelper(false,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new BooleanMonitoredPredicate.ModdingAndThrowing(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,getRoot(seq)),seqSize==0?null:ConcurrentModificationException.class);
         }
      }
    }
  }
  private static void testArrSeqSubListCheckedremoveIfBooleanPredicateHelper(boolean initVal,int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc,OmniList.OfBoolean seq,BooleanMonitoredPredicate pred,Class<? extends Throwable> expectedException)
  {
    var clone=(BooleanArrSeq.CheckedList)seq.clone();
    int expectedSize;
    BooleanArrSeq.CheckedList root;
    if(expectedException==null)
    {
      int seqSize=seq.size();
      int trueCount=0;
      for(var v:clone)
      {
        if(v)
        {
          ++trueCount;
        }
      }
      var result=seq.removeIf((BooleanPredicate)pred);
      Assertions.assertEquals(!pred.removedVals.isEmpty(),result);
      boolean removedValsContainsTrue=pred.removedVals.contains(true);
      boolean removedValsContainsFalse=pred.removedVals.contains(false);
      expectedSize=removedValsContainsTrue?removedValsContainsFalse?0:seqSize-trueCount:removedValsContainsFalse?trueCount:seqSize;
      Assertions.assertEquals(expectedSize,seq.size());
      if(removedValsContainsTrue)
      {
        Assertions.assertFalse(seq.contains(true));
      }
      if(removedValsContainsFalse)
      {
        Assertions.assertFalse(seq.contains(false));
      }
      root=assertCheckedSubListIntegrity(result?1:0,parentPreAlloc+parentPostAlloc+expectedSize,seq);
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((BooleanPredicate)pred));
      if(ConcurrentModificationException.class.equals(expectedException))
      {
        return;
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
        var v=cloneItr.nextBoolean();
        if(expectedException==null && pred.removedVals.contains(v))
        {
          continue;
        }
        Assertions.assertTrue(seqItr.hasNext());
        Assertions.assertEquals(seqItr.nextBoolean(),v);
      }
      expectedSize=seq.size();
      root=assertCheckedSubListIntegrity(0,parentPreAlloc+parentPostAlloc+expectedSize,seq);
    }
    Assertions.assertEquals(parentPreAlloc+parentPostAlloc+expectedSize+rootPreAlloc+rootPostAlloc,root.size);
    for(int i=0,bound=rootPreAlloc+parentPreAlloc;i<bound;++i)
    {
      Assertions.assertEquals(!initVal,root.arr[i]);
    }
    for(int i=rootPreAlloc+parentPreAlloc+expectedSize,bound=parentPreAlloc+parentPostAlloc+expectedSize+rootPreAlloc+rootPostAlloc;i<bound;++i)
    {
      Assertions.assertEquals(!initVal,root.arr[i]);
    }
  }
  @ParameterizedTest(name="BooleanArrSeq.CheckedSubList rootPreAlloc={0},parentPreAlloc={1},parentPostAlloc={2},rootPostAlloc={3}")
  @MethodSource("subListAllocationArgumentProvider")
  public void testArrSeqSubListCheckedremoveIfPredicate(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc)
  {
    Random rand=new Random(0);
    for(int seqSize=0;seqSize<=10;++seqSize)
    {
       for(int period=1,inc=Math.max(1,seqSize/10);period<=seqSize;period+=inc)
       {
         testArrSeqSubListCheckedremoveIfPredicateHelper(true,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,true,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new BooleanMonitoredPredicate.RemoveAll(),null);
         testArrSeqSubListCheckedremoveIfPredicateHelper(false,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,false,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new BooleanMonitoredPredicate.RemoveAll(),null);
         testArrSeqSubListCheckedremoveIfPredicateHelper(true,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,true,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new BooleanMonitoredPredicate.RemoveNone(),null);
         testArrSeqSubListCheckedremoveIfPredicateHelper(false,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,false,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new BooleanMonitoredPredicate.RemoveNone(),null);
         testArrSeqSubListCheckedremoveIfPredicateHelper(true,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,true,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new BooleanMonitoredPredicate.NonThrowing(rand,0.5),null);
         testArrSeqSubListCheckedremoveIfPredicateHelper(false,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,false,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new BooleanMonitoredPredicate.NonThrowing(rand,0.5),null);
         {
           var seq=buildArrSeqSubListChecked(seqSize,true,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
           testArrSeqSubListCheckedremoveIfPredicateHelper(true,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new BooleanMonitoredPredicate.Throwing(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0),seqSize==0?null:IndexOutOfBoundsException.class);
         }
         {
           var seq=buildArrSeqSubListChecked(seqSize,false,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
           testArrSeqSubListCheckedremoveIfPredicateHelper(false,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new BooleanMonitoredPredicate.Throwing(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0),seqSize==0?null:IndexOutOfBoundsException.class);
         }
         {
           var seq=buildArrSeqSubListChecked(seqSize,true,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
           testArrSeqSubListCheckedremoveIfPredicateHelper(true,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new BooleanMonitoredPredicate.Modding(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,seq),seqSize==0?null:ConcurrentModificationException.class);
         }
         {
           var seq=buildArrSeqSubListChecked(seqSize,false,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
           testArrSeqSubListCheckedremoveIfPredicateHelper(false,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new BooleanMonitoredPredicate.Modding(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,seq),seqSize==0?null:ConcurrentModificationException.class);
         }
         {
           var seq=buildArrSeqSubListChecked(seqSize,true,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
           testArrSeqSubListCheckedremoveIfPredicateHelper(true,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new BooleanMonitoredPredicate.ModdingAndThrowing(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,seq),seqSize==0?null:ConcurrentModificationException.class);
         }
         {
           var seq=buildArrSeqSubListChecked(seqSize,false,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
           testArrSeqSubListCheckedremoveIfPredicateHelper(false,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new BooleanMonitoredPredicate.ModdingAndThrowing(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,seq),seqSize==0?null:ConcurrentModificationException.class);
         }
         {
           var seq=buildArrSeqSubListChecked(seqSize,true,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
           testArrSeqSubListCheckedremoveIfPredicateHelper(true,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new BooleanMonitoredPredicate.Modding(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,getParent(seq)),seqSize==0?null:ConcurrentModificationException.class);
         }
         {
           var seq=buildArrSeqSubListChecked(seqSize,false,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
           testArrSeqSubListCheckedremoveIfPredicateHelper(false,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new BooleanMonitoredPredicate.Modding(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,getParent(seq)),seqSize==0?null:ConcurrentModificationException.class);
         }
         {
           var seq=buildArrSeqSubListChecked(seqSize,true,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
           testArrSeqSubListCheckedremoveIfPredicateHelper(true,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new BooleanMonitoredPredicate.ModdingAndThrowing(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,getParent(seq)),seqSize==0?null:ConcurrentModificationException.class);
         }
         {
           var seq=buildArrSeqSubListChecked(seqSize,false,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
           testArrSeqSubListCheckedremoveIfPredicateHelper(false,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new BooleanMonitoredPredicate.ModdingAndThrowing(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,getParent(seq)),seqSize==0?null:ConcurrentModificationException.class);
         }
         {
           var seq=buildArrSeqSubListChecked(seqSize,true,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
           testArrSeqSubListCheckedremoveIfPredicateHelper(true,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new BooleanMonitoredPredicate.Modding(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,getRoot(seq)),seqSize==0?null:ConcurrentModificationException.class);
         }
         {
           var seq=buildArrSeqSubListChecked(seqSize,false,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
           testArrSeqSubListCheckedremoveIfPredicateHelper(false,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new BooleanMonitoredPredicate.Modding(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,getRoot(seq)),seqSize==0?null:ConcurrentModificationException.class);
         }
         {
           var seq=buildArrSeqSubListChecked(seqSize,true,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
           testArrSeqSubListCheckedremoveIfPredicateHelper(true,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new BooleanMonitoredPredicate.ModdingAndThrowing(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,getRoot(seq)),seqSize==0?null:ConcurrentModificationException.class);
         }
         {
           var seq=buildArrSeqSubListChecked(seqSize,false,period,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
           testArrSeqSubListCheckedremoveIfPredicateHelper(false,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new BooleanMonitoredPredicate.ModdingAndThrowing(rand,seq.contains(true)?seq.contains(false)?2:1:seq.contains(false)?1:0,getRoot(seq)),seqSize==0?null:ConcurrentModificationException.class);
         }
      }
    }
  }
  private static void testArrSeqSubListCheckedremoveIfPredicateHelper(boolean initVal,int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc,OmniList.OfBoolean seq,BooleanMonitoredPredicate pred,Class<? extends Throwable> expectedException)
  {
    var clone=(BooleanArrSeq.CheckedList)seq.clone();
    int expectedSize;
    BooleanArrSeq.CheckedList root;
    if(expectedException==null)
    {
      int seqSize=seq.size();
      int trueCount=0;
      for(var v:clone)
      {
        if(v)
        {
          ++trueCount;
        }
      }
      var result=seq.removeIf((Predicate)pred);
      Assertions.assertEquals(!pred.removedVals.isEmpty(),result);
      boolean removedValsContainsTrue=pred.removedVals.contains(true);
      boolean removedValsContainsFalse=pred.removedVals.contains(false);
      expectedSize=removedValsContainsTrue?removedValsContainsFalse?0:seqSize-trueCount:removedValsContainsFalse?trueCount:seqSize;
      Assertions.assertEquals(expectedSize,seq.size());
      if(removedValsContainsTrue)
      {
        Assertions.assertFalse(seq.contains(true));
      }
      if(removedValsContainsFalse)
      {
        Assertions.assertFalse(seq.contains(false));
      }
      root=assertCheckedSubListIntegrity(result?1:0,parentPreAlloc+parentPostAlloc+expectedSize,seq);
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((Predicate)pred));
      if(ConcurrentModificationException.class.equals(expectedException))
      {
        return;
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
        var v=cloneItr.nextBoolean();
        if(expectedException==null && pred.removedVals.contains(v))
        {
          continue;
        }
        Assertions.assertTrue(seqItr.hasNext());
        Assertions.assertEquals(seqItr.nextBoolean(),v);
      }
      expectedSize=seq.size();
      root=assertCheckedSubListIntegrity(0,parentPreAlloc+parentPostAlloc+expectedSize,seq);
    }
    Assertions.assertEquals(parentPreAlloc+parentPostAlloc+expectedSize+rootPreAlloc+rootPostAlloc,root.size);
    for(int i=0,bound=rootPreAlloc+parentPreAlloc;i<bound;++i)
    {
      Assertions.assertEquals(!initVal,root.arr[i]);
    }
    for(int i=rootPreAlloc+parentPreAlloc+expectedSize,bound=parentPreAlloc+parentPostAlloc+expectedSize+rootPreAlloc+rootPostAlloc;i<bound;++i)
    {
      Assertions.assertEquals(!initVal,root.arr[i]);
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
