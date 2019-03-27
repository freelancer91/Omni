package omni.impl.seq;
import java.util.function.Predicate;
import org.junit.jupiter.api.Assertions;
import java.util.ConcurrentModificationException;
import omni.util.TypeConversionUtil;
import omni.util.OmniArray;
import java.util.Random;
import org.junit.jupiter.api.Test;
import omni.impl.CharMonitoredPredicate;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import omni.function.CharPredicate;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import omni.api.OmniList;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class CharArrSeqRemoveIfTest
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
        Class<?> clazz=new CharArrSeq.CheckedList().subList(0,0).getClass();
        (CheckedSubListmodCount=clazz.getDeclaredField("modCount")).setAccessible(true);
        (CheckedSubListparent=clazz.getDeclaredField("parent")).setAccessible(true);
        modifiersField.setInt(CheckedSubListparent,CheckedSubListparent.getModifiers()&~Modifier.FINAL);
        (CheckedSubListroot=clazz.getDeclaredField("root")).setAccessible(true);
        modifiersField.setInt(CheckedSubListroot,CheckedSubListroot.getModifiers()&~Modifier.FINAL);
        (CheckedSubListsize=clazz.getDeclaredField("size")).setAccessible(true);
      }
      {
        Class<?> clazz=new CharArrSeq.UncheckedList().subList(0,0).getClass();
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
  private static CharArrSeq.CheckedList getRoot(Object seq)
  {
    try
    {
      return (CharArrSeq.CheckedList)CheckedSubListroot.get(seq);
    }
    catch(Exception e)
    {
      throw new RuntimeException(e);
    }
  }
  private static OmniList.OfChar getParent(Object seq)
  {
    try
    {
      return (OmniList.OfChar)CheckedSubListparent.get(seq);
    }
    catch(Exception e)
    {
      throw new RuntimeException(e);
    }
  }
  private static CharArrSeq.UncheckedList assertUncheckedSubListIntegrity(int expectedModCount,int expectedParentSize,Object obj)
  {
    if(!(obj instanceof CharArrSeq.UncheckedList))
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
    return (CharArrSeq.UncheckedList)obj;
  }
  private static CharArrSeq.CheckedList assertCheckedSubListIntegrity(int expectedModCount,int expectedParentSize,Object obj)
  {
    if(!(obj instanceof CharArrSeq.CheckedList))
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
    CharArrSeq.CheckedList root=(CharArrSeq.CheckedList)obj;
    Assertions.assertEquals(expectedModCount,root.modCount);
    return root;
  }
  static CharArrSeq.UncheckedStack buildArrSeqStackUnchecked(int seqSize)
  {
    char[] arr;
    if(seqSize==0)
    {
      arr=OmniArray.OfChar.DEFAULT_ARR;
    }
    else
    {
      arr=new char[seqSize];
      for(int i=0;i<seqSize;++i)
      {
        arr[i]=TypeConversionUtil.convertTochar(i);
      }
    }
    return new CharArrSeq.UncheckedStack(seqSize,arr);
  }
  @Test
  public void testArrSeqStackUncheckedremoveIfCharPredicate()
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqStackUncheckedremoveIfCharPredicateHelper(buildArrSeqStackUnchecked(seqSize),new CharMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqStackUncheckedremoveIfCharPredicateHelper(buildArrSeqStackUnchecked(seqSize),new CharMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqStackUncheckedremoveIfCharPredicateHelper(buildArrSeqStackUnchecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqStackUncheckedremoveIfCharPredicateHelper(buildArrSeqStackUnchecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqStackUncheckedremoveIfCharPredicateHelper(buildArrSeqStackUnchecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqStackUncheckedremoveIfCharPredicateHelper(buildArrSeqStackUnchecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqStackUncheckedremoveIfCharPredicateHelper(buildArrSeqStackUnchecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqStackUncheckedremoveIfCharPredicateHelper(buildArrSeqStackUnchecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqStackUncheckedremoveIfCharPredicateHelper(buildArrSeqStackUnchecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqStackUncheckedremoveIfCharPredicateHelper(buildArrSeqStackUnchecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqStackUncheckedremoveIfCharPredicateHelper(buildArrSeqStackUnchecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqStackUncheckedremoveIfCharPredicateHelper(CharArrSeq.UncheckedStack seq,CharMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(CharArrSeq.UncheckedStack)seq.clone();
    int expectedSize;
    if(expectedException==null)
    {
      int seqSize=seq.size();
      if(numExpectedToBeRemoved<0)
      {
        var result=seq.removeIf((CharPredicate)pred);
        int numRemoved=pred.removedVals.size();
        Assertions.assertEquals(numRemoved!=0,result);
        expectedSize=seqSize-numRemoved;
      }
      else if(numExpectedToBeRemoved==0)
      {
        Assertions.assertFalse(seq.removeIf((CharPredicate)pred));
        Assertions.assertTrue(pred.removedVals.isEmpty());
        expectedSize=seqSize;
      }
      else
      {
        Assertions.assertTrue(seq.removeIf((CharPredicate)pred));
        expectedSize=seqSize-numExpectedToBeRemoved;
      }
      Assertions.assertEquals(expectedSize,seq.size());
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((CharPredicate)pred));
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
      var v=cloneItr.nextChar();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextChar(),v);
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
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new CharMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new CharMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqStackUncheckedremoveIfPredicateHelper(buildArrSeqStackUnchecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqStackUncheckedremoveIfPredicateHelper(CharArrSeq.UncheckedStack seq,CharMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(CharArrSeq.UncheckedStack)seq.clone();
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
      var v=cloneItr.nextChar();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextChar(),v);
    }
  }
  static CharArrSeq.CheckedStack buildArrSeqStackChecked(int seqSize)
  {
    char[] arr;
    if(seqSize==0)
    {
      arr=OmniArray.OfChar.DEFAULT_ARR;
    }
    else
    {
      arr=new char[seqSize];
      for(int i=0;i<seqSize;++i)
      {
        arr[i]=TypeConversionUtil.convertTochar(i);
      }
    }
    return new CharArrSeq.CheckedStack(seqSize,arr);
  }
  @Test
  public void testArrSeqStackCheckedremoveIfCharPredicate()
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqStackCheckedremoveIfCharPredicateHelper(buildArrSeqStackChecked(seqSize),new CharMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqStackCheckedremoveIfCharPredicateHelper(buildArrSeqStackChecked(seqSize),new CharMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqStackCheckedremoveIfCharPredicateHelper(buildArrSeqStackChecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqStackCheckedremoveIfCharPredicateHelper(buildArrSeqStackChecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqStackCheckedremoveIfCharPredicateHelper(buildArrSeqStackChecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqStackCheckedremoveIfCharPredicateHelper(buildArrSeqStackChecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqStackCheckedremoveIfCharPredicateHelper(buildArrSeqStackChecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqStackCheckedremoveIfCharPredicateHelper(buildArrSeqStackChecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqStackCheckedremoveIfCharPredicateHelper(buildArrSeqStackChecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqStackCheckedremoveIfCharPredicateHelper(buildArrSeqStackChecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqStackCheckedremoveIfCharPredicateHelper(buildArrSeqStackChecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        testArrSeqStackCheckedremoveIfCharPredicateHelper(buildArrSeqStackChecked(seqSize),new CharMonitoredPredicate.Throwing(rand,seqSize),0,seqSize==0?null:IndexOutOfBoundsException.class);
        {
          var seq=buildArrSeqStackChecked(seqSize);
          testArrSeqStackCheckedremoveIfCharPredicateHelper(seq,new CharMonitoredPredicate.Modding(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqStackChecked(seqSize);
          testArrSeqStackCheckedremoveIfCharPredicateHelper(seq,new CharMonitoredPredicate.ModdingAndThrowing(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqStackCheckedremoveIfCharPredicateHelper(CharArrSeq.CheckedStack seq,CharMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(CharArrSeq.CheckedStack)seq.clone();
    int expectedSize;
    if(expectedException==null)
    {
      int seqSize=seq.size();
      if(numExpectedToBeRemoved<0)
      {
        var result=seq.removeIf((CharPredicate)pred);
        int numRemoved=pred.removedVals.size();
        Assertions.assertEquals(numRemoved!=0,result);
        expectedSize=seqSize-numRemoved;
      }
      else if(numExpectedToBeRemoved==0)
      {
        Assertions.assertFalse(seq.removeIf((CharPredicate)pred));
        Assertions.assertTrue(pred.removedVals.isEmpty());
        expectedSize=seqSize;
      }
      else
      {
        Assertions.assertTrue(seq.removeIf((CharPredicate)pred));
        expectedSize=seqSize-numExpectedToBeRemoved;
      }
      Assertions.assertEquals(expectedSize,seq.size());
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((CharPredicate)pred));
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
      var v=cloneItr.nextChar();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextChar(),v);
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
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new CharMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new CharMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        testArrSeqStackCheckedremoveIfPredicateHelper(buildArrSeqStackChecked(seqSize),new CharMonitoredPredicate.Throwing(rand,seqSize),0,seqSize==0?null:IndexOutOfBoundsException.class);
        {
          var seq=buildArrSeqStackChecked(seqSize);
          testArrSeqStackCheckedremoveIfPredicateHelper(seq,new CharMonitoredPredicate.Modding(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqStackChecked(seqSize);
          testArrSeqStackCheckedremoveIfPredicateHelper(seq,new CharMonitoredPredicate.ModdingAndThrowing(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqStackCheckedremoveIfPredicateHelper(CharArrSeq.CheckedStack seq,CharMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(CharArrSeq.CheckedStack)seq.clone();
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
      var v=cloneItr.nextChar();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextChar(),v);
    }
  }
  static CharArrSeq.UncheckedList buildArrSeqListUnchecked(int seqSize)
  {
    char[] arr;
    if(seqSize==0)
    {
      arr=OmniArray.OfChar.DEFAULT_ARR;
    }
    else
    {
      arr=new char[seqSize];
      for(int i=0;i<seqSize;++i)
      {
        arr[i]=TypeConversionUtil.convertTochar(i);
      }
    }
    return new CharArrSeq.UncheckedList(seqSize,arr);
  }
  @Test
  public void testArrSeqListUncheckedremoveIfCharPredicate()
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqListUncheckedremoveIfCharPredicateHelper(buildArrSeqListUnchecked(seqSize),new CharMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqListUncheckedremoveIfCharPredicateHelper(buildArrSeqListUnchecked(seqSize),new CharMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqListUncheckedremoveIfCharPredicateHelper(buildArrSeqListUnchecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqListUncheckedremoveIfCharPredicateHelper(buildArrSeqListUnchecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqListUncheckedremoveIfCharPredicateHelper(buildArrSeqListUnchecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqListUncheckedremoveIfCharPredicateHelper(buildArrSeqListUnchecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqListUncheckedremoveIfCharPredicateHelper(buildArrSeqListUnchecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqListUncheckedremoveIfCharPredicateHelper(buildArrSeqListUnchecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqListUncheckedremoveIfCharPredicateHelper(buildArrSeqListUnchecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqListUncheckedremoveIfCharPredicateHelper(buildArrSeqListUnchecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqListUncheckedremoveIfCharPredicateHelper(buildArrSeqListUnchecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqListUncheckedremoveIfCharPredicateHelper(CharArrSeq.UncheckedList seq,CharMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(CharArrSeq.UncheckedList)seq.clone();
    int expectedSize;
    if(expectedException==null)
    {
      int seqSize=seq.size();
      if(numExpectedToBeRemoved<0)
      {
        var result=seq.removeIf((CharPredicate)pred);
        int numRemoved=pred.removedVals.size();
        Assertions.assertEquals(numRemoved!=0,result);
        expectedSize=seqSize-numRemoved;
      }
      else if(numExpectedToBeRemoved==0)
      {
        Assertions.assertFalse(seq.removeIf((CharPredicate)pred));
        Assertions.assertTrue(pred.removedVals.isEmpty());
        expectedSize=seqSize;
      }
      else
      {
        Assertions.assertTrue(seq.removeIf((CharPredicate)pred));
        expectedSize=seqSize-numExpectedToBeRemoved;
      }
      Assertions.assertEquals(expectedSize,seq.size());
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((CharPredicate)pred));
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
      var v=cloneItr.nextChar();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextChar(),v);
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
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new CharMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new CharMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqListUncheckedremoveIfPredicateHelper(buildArrSeqListUnchecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqListUncheckedremoveIfPredicateHelper(CharArrSeq.UncheckedList seq,CharMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(CharArrSeq.UncheckedList)seq.clone();
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
      var v=cloneItr.nextChar();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextChar(),v);
    }
  }
  static CharArrSeq.CheckedList buildArrSeqListChecked(int seqSize)
  {
    char[] arr;
    if(seqSize==0)
    {
      arr=OmniArray.OfChar.DEFAULT_ARR;
    }
    else
    {
      arr=new char[seqSize];
      for(int i=0;i<seqSize;++i)
      {
        arr[i]=TypeConversionUtil.convertTochar(i);
      }
    }
    return new CharArrSeq.CheckedList(seqSize,arr);
  }
  @Test
  public void testArrSeqListCheckedremoveIfCharPredicate()
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqListCheckedremoveIfCharPredicateHelper(buildArrSeqListChecked(seqSize),new CharMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqListCheckedremoveIfCharPredicateHelper(buildArrSeqListChecked(seqSize),new CharMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqListCheckedremoveIfCharPredicateHelper(buildArrSeqListChecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqListCheckedremoveIfCharPredicateHelper(buildArrSeqListChecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqListCheckedremoveIfCharPredicateHelper(buildArrSeqListChecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqListCheckedremoveIfCharPredicateHelper(buildArrSeqListChecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqListCheckedremoveIfCharPredicateHelper(buildArrSeqListChecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqListCheckedremoveIfCharPredicateHelper(buildArrSeqListChecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqListCheckedremoveIfCharPredicateHelper(buildArrSeqListChecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqListCheckedremoveIfCharPredicateHelper(buildArrSeqListChecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqListCheckedremoveIfCharPredicateHelper(buildArrSeqListChecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        testArrSeqListCheckedremoveIfCharPredicateHelper(buildArrSeqListChecked(seqSize),new CharMonitoredPredicate.Throwing(rand,seqSize),0,seqSize==0?null:IndexOutOfBoundsException.class);
        {
          var seq=buildArrSeqListChecked(seqSize);
          testArrSeqListCheckedremoveIfCharPredicateHelper(seq,new CharMonitoredPredicate.Modding(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqListChecked(seqSize);
          testArrSeqListCheckedremoveIfCharPredicateHelper(seq,new CharMonitoredPredicate.ModdingAndThrowing(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqListCheckedremoveIfCharPredicateHelper(CharArrSeq.CheckedList seq,CharMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(CharArrSeq.CheckedList)seq.clone();
    int expectedSize;
    if(expectedException==null)
    {
      int seqSize=seq.size();
      if(numExpectedToBeRemoved<0)
      {
        var result=seq.removeIf((CharPredicate)pred);
        int numRemoved=pred.removedVals.size();
        Assertions.assertEquals(numRemoved!=0,result);
        expectedSize=seqSize-numRemoved;
      }
      else if(numExpectedToBeRemoved==0)
      {
        Assertions.assertFalse(seq.removeIf((CharPredicate)pred));
        Assertions.assertTrue(pred.removedVals.isEmpty());
        expectedSize=seqSize;
      }
      else
      {
        Assertions.assertTrue(seq.removeIf((CharPredicate)pred));
        expectedSize=seqSize-numExpectedToBeRemoved;
      }
      Assertions.assertEquals(expectedSize,seq.size());
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((CharPredicate)pred));
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
      var v=cloneItr.nextChar();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextChar(),v);
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
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new CharMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new CharMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new CharMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        testArrSeqListCheckedremoveIfPredicateHelper(buildArrSeqListChecked(seqSize),new CharMonitoredPredicate.Throwing(rand,seqSize),0,seqSize==0?null:IndexOutOfBoundsException.class);
        {
          var seq=buildArrSeqListChecked(seqSize);
          testArrSeqListCheckedremoveIfPredicateHelper(seq,new CharMonitoredPredicate.Modding(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqListChecked(seqSize);
          testArrSeqListCheckedremoveIfPredicateHelper(seq,new CharMonitoredPredicate.ModdingAndThrowing(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqListCheckedremoveIfPredicateHelper(CharArrSeq.CheckedList seq,CharMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(CharArrSeq.CheckedList)seq.clone();
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
      var v=cloneItr.nextChar();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextChar(),v);
    }
  }
  static OmniList.OfChar buildArrSeqSubListUnchecked(int seqSize,int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc)
  {
    char[] arr;
    int rootSize=rootPreAlloc+parentPreAlloc+seqSize+parentPostAlloc+rootPostAlloc;
    if(rootSize==0)
    {
      arr=OmniArray.OfChar.DEFAULT_ARR;
    }
    else
    {
      arr=new char[rootSize];
      int dstOffset=0;
      for(int i=0;i<rootPreAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTochar(i-rootPreAlloc-parentPreAlloc);
      }
      for(int i=0;i<parentPreAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTochar(i-parentPreAlloc);
      }
      for(int i=0;i<seqSize;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTochar(i);
      }
      for(int i=0;i<parentPostAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTochar(seqSize+i);
      }
      for(int i=0;i<rootPostAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTochar(seqSize+parentPostAlloc+i);
      }
    }
    return new CharArrSeq.CheckedList(rootSize,arr).subList(rootPreAlloc,parentPreAlloc+seqSize+parentPostAlloc).subList(parentPreAlloc,parentPreAlloc+seqSize);
  }
  @ParameterizedTest(name="CharArrSeq.UncheckedSubList rootPreAlloc={0},parentPreAlloc={1},parentPostAlloc={2},rootPostAlloc={3}")
  @MethodSource("subListAllocationArgumentProvider")
  public void testArrSeqSubListUncheckedremoveIfCharPredicate(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc)
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqSubListUncheckedremoveIfCharPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqSubListUncheckedremoveIfCharPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqSubListUncheckedremoveIfCharPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqSubListUncheckedremoveIfCharPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqSubListUncheckedremoveIfCharPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqSubListUncheckedremoveIfCharPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqSubListUncheckedremoveIfCharPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqSubListUncheckedremoveIfCharPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqSubListUncheckedremoveIfCharPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqSubListUncheckedremoveIfCharPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqSubListUncheckedremoveIfCharPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqSubListUncheckedremoveIfCharPredicateHelper(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc,OmniList.OfChar seq,CharMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(CharArrSeq.UncheckedList)seq.clone();
    int expectedSize;
    CharArrSeq.UncheckedList root;
    int seqSize=seq.size();
    if(expectedException==null)
    {
      if(numExpectedToBeRemoved<0)
      {
        var result=seq.removeIf((CharPredicate)pred);
        int numRemoved=pred.removedVals.size();
        Assertions.assertEquals(numRemoved!=0,result);
        expectedSize=seqSize-numRemoved;
        root=assertUncheckedSubListIntegrity(numRemoved==0?0:1,parentPreAlloc+parentPostAlloc+expectedSize,seq);
      }
      else if(numExpectedToBeRemoved==0)
      {
        Assertions.assertFalse(seq.removeIf((CharPredicate)pred));
        Assertions.assertTrue(pred.removedVals.isEmpty());
        expectedSize=seqSize;
        root=assertUncheckedSubListIntegrity(0,parentPreAlloc+parentPostAlloc+expectedSize,seq);
      }
      else
      {
        Assertions.assertTrue(seq.removeIf((CharPredicate)pred));
        expectedSize=seqSize-numExpectedToBeRemoved;
        root=assertUncheckedSubListIntegrity(1,parentPreAlloc+parentPostAlloc+expectedSize,seq);
      }
      Assertions.assertEquals(expectedSize,seq.size());
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((CharPredicate)pred));
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
      var v=cloneItr.nextChar();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextChar(),v);
    }
    int dstOffset=0;
    for(int i=0;i<rootPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTochar(i-rootPreAlloc-parentPreAlloc));
    }
    for(int i=0;i<parentPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTochar(i-parentPreAlloc));
    }
    dstOffset+=expectedSize;
    for(int i=0;i<parentPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTochar(seqSize+i));
    }
    for(int i=0;i<rootPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTochar(seqSize+parentPostAlloc+i));
    }
  }
  @ParameterizedTest(name="CharArrSeq.UncheckedSubList rootPreAlloc={0},parentPreAlloc={1},parentPostAlloc={2},rootPostAlloc={3}")
  @MethodSource("subListAllocationArgumentProvider")
  public void testArrSeqSubListUncheckedremoveIfPredicate(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc)
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqSubListUncheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListUnchecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqSubListUncheckedremoveIfPredicateHelper(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc,OmniList.OfChar seq,CharMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(CharArrSeq.UncheckedList)seq.clone();
    int expectedSize;
    CharArrSeq.UncheckedList root;
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
      var v=cloneItr.nextChar();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextChar(),v);
    }
    int dstOffset=0;
    for(int i=0;i<rootPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTochar(i-rootPreAlloc-parentPreAlloc));
    }
    for(int i=0;i<parentPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTochar(i-parentPreAlloc));
    }
    dstOffset+=expectedSize;
    for(int i=0;i<parentPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTochar(seqSize+i));
    }
    for(int i=0;i<rootPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTochar(seqSize+parentPostAlloc+i));
    }
  }
  static OmniList.OfChar buildArrSeqSubListChecked(int seqSize,int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc)
  {
    char[] arr;
    int rootSize=rootPreAlloc+parentPreAlloc+seqSize+parentPostAlloc+rootPostAlloc;
    if(rootSize==0)
    {
      arr=OmniArray.OfChar.DEFAULT_ARR;
    }
    else
    {
      arr=new char[rootSize];
      int dstOffset=0;
      for(int i=0;i<rootPreAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTochar(i-rootPreAlloc-parentPreAlloc);
      }
      for(int i=0;i<parentPreAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTochar(i-parentPreAlloc);
      }
      for(int i=0;i<seqSize;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTochar(i);
      }
      for(int i=0;i<parentPostAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTochar(seqSize+i);
      }
      for(int i=0;i<rootPostAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTochar(seqSize+parentPostAlloc+i);
      }
    }
    return new CharArrSeq.CheckedList(rootSize,arr).subList(rootPreAlloc,parentPreAlloc+seqSize+parentPostAlloc).subList(parentPreAlloc,parentPreAlloc+seqSize);
  }
  @ParameterizedTest(name="CharArrSeq.CheckedSubList rootPreAlloc={0},parentPreAlloc={1},parentPostAlloc={2},rootPostAlloc={3}")
  @MethodSource("subListAllocationArgumentProvider")
  public void testArrSeqSubListCheckedremoveIfCharPredicate(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc)
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqSubListCheckedremoveIfCharPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqSubListCheckedremoveIfCharPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqSubListCheckedremoveIfCharPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqSubListCheckedremoveIfCharPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqSubListCheckedremoveIfCharPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqSubListCheckedremoveIfCharPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqSubListCheckedremoveIfCharPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqSubListCheckedremoveIfCharPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqSubListCheckedremoveIfCharPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqSubListCheckedremoveIfCharPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqSubListCheckedremoveIfCharPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        testArrSeqSubListCheckedremoveIfCharPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.Throwing(rand,seqSize),0,seqSize==0?null:IndexOutOfBoundsException.class);
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfCharPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new CharMonitoredPredicate.Modding(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfCharPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new CharMonitoredPredicate.ModdingAndThrowing(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfCharPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new CharMonitoredPredicate.Modding(rand,seqSize,getParent(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfCharPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new CharMonitoredPredicate.ModdingAndThrowing(rand,seqSize,getParent(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfCharPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new CharMonitoredPredicate.Modding(rand,seqSize,getRoot(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfCharPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new CharMonitoredPredicate.ModdingAndThrowing(rand,seqSize,getRoot(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqSubListCheckedremoveIfCharPredicateHelper(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc,OmniList.OfChar seq,CharMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(CharArrSeq.CheckedList)seq.clone();
    int expectedSize;
    CharArrSeq.CheckedList root;
    int seqSize=seq.size();
    if(expectedException==null)
    {
      if(numExpectedToBeRemoved<0)
      {
        var result=seq.removeIf((CharPredicate)pred);
        int numRemoved=pred.removedVals.size();
        Assertions.assertEquals(numRemoved!=0,result);
        expectedSize=seqSize-numRemoved;
        root=assertCheckedSubListIntegrity(numRemoved==0?0:1,parentPreAlloc+parentPostAlloc+expectedSize,seq);
      }
      else if(numExpectedToBeRemoved==0)
      {
        Assertions.assertFalse(seq.removeIf((CharPredicate)pred));
        Assertions.assertTrue(pred.removedVals.isEmpty());
        expectedSize=seqSize;
        root=assertCheckedSubListIntegrity(0,parentPreAlloc+parentPostAlloc+expectedSize,seq);
      }
      else
      {
        Assertions.assertTrue(seq.removeIf((CharPredicate)pred));
        expectedSize=seqSize-numExpectedToBeRemoved;
        root=assertCheckedSubListIntegrity(1,parentPreAlloc+parentPostAlloc+expectedSize,seq);
      }
      Assertions.assertEquals(expectedSize,seq.size());
    }
    else
    {
      Assertions.assertThrows(expectedException,()->seq.removeIf((CharPredicate)pred));
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
      var v=cloneItr.nextChar();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextChar(),v);
    }
    int dstOffset=0;
    for(int i=0;i<rootPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTochar(i-rootPreAlloc-parentPreAlloc));
    }
    for(int i=0;i<parentPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTochar(i-parentPreAlloc));
    }
    dstOffset+=expectedSize;
    for(int i=0;i<parentPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTochar(seqSize+i));
    }
    for(int i=0;i<rootPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTochar(seqSize+parentPostAlloc+i));
    }
  }
  @ParameterizedTest(name="CharArrSeq.CheckedSubList rootPreAlloc={0},parentPreAlloc={1},parentPostAlloc={2},rootPostAlloc={3}")
  @MethodSource("subListAllocationArgumentProvider")
  public void testArrSeqSubListCheckedremoveIfPredicate(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc)
  {
    Random rand=new Random(0);
    outer:for(int seqSize=0;seqSize<=100;seqSize+=10)
    {
      for(int i=0;i<100;++i)
      {
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.RemoveAll(),seqSize,null);          
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.RemoveNone(),0,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.NonThrowing(rand,0.01),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.NonThrowing(rand,0.05),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.NonThrowing(rand,0.10),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.NonThrowing(rand,0.25),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.NonThrowing(rand,0.50),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.NonThrowing(rand,0.75),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.NonThrowing(rand,0.90),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.NonThrowing(rand,0.95),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.NonThrowing(rand,0.99),-1,null);
        testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc),new CharMonitoredPredicate.Throwing(rand,seqSize),0,seqSize==0?null:IndexOutOfBoundsException.class);
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new CharMonitoredPredicate.Modding(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new CharMonitoredPredicate.ModdingAndThrowing(rand,seqSize,seq),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new CharMonitoredPredicate.Modding(rand,seqSize,getParent(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new CharMonitoredPredicate.ModdingAndThrowing(rand,seqSize,getParent(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new CharMonitoredPredicate.Modding(rand,seqSize,getRoot(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        {
          var seq=buildArrSeqSubListChecked(seqSize,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
          testArrSeqSubListCheckedremoveIfPredicateHelper(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,seq,new CharMonitoredPredicate.ModdingAndThrowing(rand,seqSize,getRoot(seq)),0,seqSize==0?null:ConcurrentModificationException.class);
        }
        if(seqSize==0)
        {
          continue outer;
        }
      }
    }
  }
  private static void testArrSeqSubListCheckedremoveIfPredicateHelper(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc,OmniList.OfChar seq,CharMonitoredPredicate pred,int numExpectedToBeRemoved,Class<? extends Throwable> expectedException)
  {
    var clone=(CharArrSeq.CheckedList)seq.clone();
    int expectedSize;
    CharArrSeq.CheckedList root;
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
      var v=cloneItr.nextChar();
      if(expectedException==null && pred.removedVals.contains(v))
      {
        continue;
      }
      Assertions.assertTrue(seqItr.hasNext());
      Assertions.assertEquals(seqItr.nextChar(),v);
    }
    int dstOffset=0;
    for(int i=0;i<rootPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTochar(i-rootPreAlloc-parentPreAlloc));
    }
    for(int i=0;i<parentPreAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTochar(i-parentPreAlloc));
    }
    dstOffset+=expectedSize;
    for(int i=0;i<parentPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTochar(seqSize+i));
    }
    for(int i=0;i<rootPostAlloc;++i,++dstOffset)
    {
      Assertions.assertEquals(root.arr[dstOffset],TypeConversionUtil.convertTochar(seqSize+parentPostAlloc+i));
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
