package omni.impl.seq;
import java.util.function.Predicate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.impl.seq.LongArrSeq.UncheckedList;
import omni.impl.seq.LongArrSeq.CheckedList;
import omni.impl.seq.LongArrSeq.UncheckedStack;
import omni.impl.seq.LongArrSeq.CheckedStack;
import java.util.ConcurrentModificationException;
import omni.util.TypeConversionUtil;
import omni.api.OmniList;
import omni.api.OmniStack;
import java.util.function.LongPredicate;
public class LongSeqRemoveIfTest
{
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedStack()
  {
    LongPredicate filter=(val)->true;
    var seq=new UncheckedStack();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Long>)filter::test));
  }
  @Test
  public void testRemoveIfReturnFalseArrSeqUncheckedStack()
  {
    LongPredicate filter=(val)->false;
    var seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTolong(i);
      seq.add(val);
    }
    {
      Assertions.assertFalse(seq.removeIf(filter));
      Assertions.assertFalse(seq.removeIf((Predicate<Long>)filter::test));
    }
  }
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedList()
  {
    LongPredicate filter=(val)->true;
    var seq=new UncheckedList();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Long>)filter::test));
  }
  @Test
  public void testRemoveIfReturnFalseArrSeqUncheckedList()
  {
    LongPredicate filter=(val)->false;
    var seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTolong(i);
      seq.add(val);
    }
    {
      Assertions.assertFalse(seq.removeIf(filter));
      Assertions.assertFalse(seq.removeIf((Predicate<Long>)filter::test));
    }
  }
  @Test
  public void testEmptyRemoveIfArrSeqCheckedStack()
  {
    LongPredicate filter=(val)->true;
    var seq=new CheckedStack();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Long>)filter::test));
  }
  @Test
  public void testRemoveIfModCheckArrSeqCheckedStack()
  {
    var seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTolong(i);
      seq.add(val);
    }
    {
    }
    {
      var seqClone=(OmniStack.OfLong)seq.clone();
      LongPredicate removingPredicateReturnFalse=(val)->
      {
        seqClone.pop();
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(removingPredicateReturnFalse));
    }
    {
      var seqClone=(OmniStack.OfLong)seq.clone();
      LongPredicate removingPredicateReturnFalse=(val)->
      {
        seqClone.pop();
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Long>)removingPredicateReturnFalse::test));
    }
    {
      var seqClone=(OmniStack.OfLong)seq.clone();
      LongPredicate removingPredicateReturnTrue=(val)->
      {
        seqClone.pop();
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(removingPredicateReturnTrue));
    }
    {
      var seqClone=(OmniStack.OfLong)seq.clone();
      LongPredicate removingPredicateReturnTrue=(val)->
      {
        seqClone.pop();
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Long>)removingPredicateReturnTrue::test));
    }
    {
      var seqClone=(OmniStack.OfLong)seq.clone();
      LongPredicate addingPredicateReturnFalse=(val)->
      {
        seqClone.add(Long.MIN_VALUE);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(addingPredicateReturnFalse));
    }
    {
      var seqClone=(OmniStack.OfLong)seq.clone();
      LongPredicate addingPredicateReturnFalse=(val)->
      {
        seqClone.add(Long.MIN_VALUE);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Long>)addingPredicateReturnFalse::test));
    }
    {
      var seqClone=(OmniStack.OfLong)seq.clone();
      LongPredicate addingPredicateReturnTrue=(val)->
      {
        seqClone.add(Long.MIN_VALUE);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(addingPredicateReturnTrue));
    }
    {
      var seqClone=(OmniStack.OfLong)seq.clone();
      LongPredicate addingPredicateReturnTrue=(val)->
      {
        seqClone.add(Long.MIN_VALUE);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Long>)addingPredicateReturnTrue::test));
    }
  }
  @Test
  public void testRemoveIfReturnFalseArrSeqCheckedStack()
  {
    LongPredicate filter=(val)->false;
    var seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTolong(i);
      seq.add(val);
    }
    {
      Assertions.assertFalse(seq.removeIf(filter));
      Assertions.assertFalse(seq.removeIf((Predicate<Long>)filter::test));
    }
  }
  @Test
  public void testEmptyRemoveIfArrSeqCheckedList()
  {
    LongPredicate filter=(val)->true;
    var seq=new CheckedList();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Long>)filter::test));
  }
  @Test
  public void testRemoveIfModCheckArrSeqCheckedList()
  {
    var seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTolong(i);
      seq.add(val);
    }
    {
    }
    {
      var seqClone=(OmniList.OfLong)seq.clone();
      LongPredicate removingPredicateReturnFalse=(val)->
      {
        seqClone.remove(0);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(removingPredicateReturnFalse));
    }
    {
      var seqClone=(OmniList.OfLong)seq.clone();
      LongPredicate removingPredicateReturnFalse=(val)->
      {
        seqClone.remove(0);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Long>)removingPredicateReturnFalse::test));
    }
    {
      var seqClone=(OmniList.OfLong)seq.clone();
      LongPredicate removingPredicateReturnTrue=(val)->
      {
        seqClone.remove(0);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(removingPredicateReturnTrue));
    }
    {
      var seqClone=(OmniList.OfLong)seq.clone();
      LongPredicate removingPredicateReturnTrue=(val)->
      {
        seqClone.remove(0);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Long>)removingPredicateReturnTrue::test));
    }
    {
      var seqClone=(OmniList.OfLong)seq.clone();
      LongPredicate addingPredicateReturnFalse=(val)->
      {
        seqClone.add(Long.MIN_VALUE);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(addingPredicateReturnFalse));
    }
    {
      var seqClone=(OmniList.OfLong)seq.clone();
      LongPredicate addingPredicateReturnFalse=(val)->
      {
        seqClone.add(Long.MIN_VALUE);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Long>)addingPredicateReturnFalse::test));
    }
    {
      var seqClone=(OmniList.OfLong)seq.clone();
      LongPredicate addingPredicateReturnTrue=(val)->
      {
        seqClone.add(Long.MIN_VALUE);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(addingPredicateReturnTrue));
    }
    {
      var seqClone=(OmniList.OfLong)seq.clone();
      LongPredicate addingPredicateReturnTrue=(val)->
      {
        seqClone.add(Long.MIN_VALUE);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Long>)addingPredicateReturnTrue::test));
    }
  }
  @Test
  public void testRemoveIfReturnFalseArrSeqCheckedList()
  {
    LongPredicate filter=(val)->false;
    var seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTolong(i);
      seq.add(val);
    }
    {
      Assertions.assertFalse(seq.removeIf(filter));
      Assertions.assertFalse(seq.removeIf((Predicate<Long>)filter::test));
    }
  }
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedSubList()
  {
    LongPredicate filter=(val)->true;
    var root=new UncheckedList();
    var seq=root.subList(0,0);
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Long>)filter::test));
  }
  @Test
  public void testRemoveIfReturnFalseArrSeqUncheckedSubList()
  {
    LongPredicate filter=(val)->false;
    var root=new UncheckedList();
    var seq=root.subList(0,0);
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTolong(i);
      seq.add(val);
    }
    {
      Assertions.assertFalse(seq.removeIf(filter));
      Assertions.assertFalse(seq.removeIf((Predicate<Long>)filter::test));
    }
  }
  @Test
  public void testEmptyRemoveIfArrSeqCheckedSubList()
  {
    LongPredicate filter=(val)->true;
    var root=new CheckedList();
    var seq=root.subList(0,0);
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Long>)filter::test));
  }
  @Test
  public void testRemoveIfModCheckArrSeqCheckedSubList()
  {
    var root=new CheckedList();
    var seq=root.subList(0,0);
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTolong(i);
      seq.add(val);
    }
    {
    }
    {
      var seqClone=(OmniList.OfLong)seq.clone();
      LongPredicate removingPredicateReturnFalse=(val)->
      {
        seqClone.remove(0);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(removingPredicateReturnFalse));
    }
    {
      var seqClone=(OmniList.OfLong)seq.clone();
      LongPredicate removingPredicateReturnFalse=(val)->
      {
        seqClone.remove(0);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Long>)removingPredicateReturnFalse::test));
    }
    {
      var seqClone=(OmniList.OfLong)seq.clone();
      LongPredicate removingPredicateReturnTrue=(val)->
      {
        seqClone.remove(0);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(removingPredicateReturnTrue));
    }
    {
      var seqClone=(OmniList.OfLong)seq.clone();
      LongPredicate removingPredicateReturnTrue=(val)->
      {
        seqClone.remove(0);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Long>)removingPredicateReturnTrue::test));
    }
    {
      var seqClone=(OmniList.OfLong)seq.clone();
      LongPredicate addingPredicateReturnFalse=(val)->
      {
        seqClone.add(Long.MIN_VALUE);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(addingPredicateReturnFalse));
    }
    {
      var seqClone=(OmniList.OfLong)seq.clone();
      LongPredicate addingPredicateReturnFalse=(val)->
      {
        seqClone.add(Long.MIN_VALUE);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Long>)addingPredicateReturnFalse::test));
    }
    {
      var seqClone=(OmniList.OfLong)seq.clone();
      LongPredicate addingPredicateReturnTrue=(val)->
      {
        seqClone.add(Long.MIN_VALUE);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(addingPredicateReturnTrue));
    }
    {
      var seqClone=(OmniList.OfLong)seq.clone();
      LongPredicate addingPredicateReturnTrue=(val)->
      {
        seqClone.add(Long.MIN_VALUE);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Long>)addingPredicateReturnTrue::test));
    }
  }
  @Test
  public void testRemoveIfReturnFalseArrSeqCheckedSubList()
  {
    LongPredicate filter=(val)->false;
    var root=new CheckedList();
    var seq=root.subList(0,0);
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTolong(i);
      seq.add(val);
    }
    {
      Assertions.assertFalse(seq.removeIf(filter));
      Assertions.assertFalse(seq.removeIf((Predicate<Long>)filter::test));
    }
  }
}
