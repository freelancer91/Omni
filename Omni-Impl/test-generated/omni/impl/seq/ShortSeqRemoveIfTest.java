package omni.impl.seq;
import java.util.function.Predicate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.impl.seq.ShortArrSeq.UncheckedList;
import omni.impl.seq.ShortArrSeq.CheckedList;
import omni.impl.seq.ShortArrSeq.UncheckedStack;
import omni.impl.seq.ShortArrSeq.CheckedStack;
import java.util.ConcurrentModificationException;
import omni.util.TypeConversionUtil;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.function.ShortPredicate;
public class ShortSeqRemoveIfTest
{
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedStack()
  {
    ShortPredicate filter=(val)->true;
    var seq=new UncheckedStack();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Short>)filter::test));
  }
  @Test
  public void testRemoveIfReturnFalseArrSeqUncheckedStack()
  {
    ShortPredicate filter=(val)->false;
    var seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertToshort(i);
      seq.add(val);
    }
    {
      Assertions.assertFalse(seq.removeIf(filter));
      Assertions.assertFalse(seq.removeIf((Predicate<Short>)filter::test));
    }
  }
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedList()
  {
    ShortPredicate filter=(val)->true;
    var seq=new UncheckedList();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Short>)filter::test));
  }
  @Test
  public void testRemoveIfReturnFalseArrSeqUncheckedList()
  {
    ShortPredicate filter=(val)->false;
    var seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertToshort(i);
      seq.add(val);
    }
    {
      Assertions.assertFalse(seq.removeIf(filter));
      Assertions.assertFalse(seq.removeIf((Predicate<Short>)filter::test));
    }
  }
  @Test
  public void testEmptyRemoveIfArrSeqCheckedStack()
  {
    ShortPredicate filter=(val)->true;
    var seq=new CheckedStack();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Short>)filter::test));
  }
  @Test
  public void testRemoveIfModCheckArrSeqCheckedStack()
  {
    var seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertToshort(i);
      seq.add(val);
    }
    {
    }
    {
      var seqClone=(OmniStack.OfShort)seq.clone();
      ShortPredicate removingPredicateReturnFalse=(val)->
      {
        seqClone.pop();
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(removingPredicateReturnFalse));
    }
    {
      var seqClone=(OmniStack.OfShort)seq.clone();
      ShortPredicate removingPredicateReturnFalse=(val)->
      {
        seqClone.pop();
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Short>)removingPredicateReturnFalse::test));
    }
    {
      var seqClone=(OmniStack.OfShort)seq.clone();
      ShortPredicate removingPredicateReturnTrue=(val)->
      {
        seqClone.pop();
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(removingPredicateReturnTrue));
    }
    {
      var seqClone=(OmniStack.OfShort)seq.clone();
      ShortPredicate removingPredicateReturnTrue=(val)->
      {
        seqClone.pop();
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Short>)removingPredicateReturnTrue::test));
    }
    {
      var seqClone=(OmniStack.OfShort)seq.clone();
      ShortPredicate addingPredicateReturnFalse=(val)->
      {
        seqClone.add(Short.MIN_VALUE);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(addingPredicateReturnFalse));
    }
    {
      var seqClone=(OmniStack.OfShort)seq.clone();
      ShortPredicate addingPredicateReturnFalse=(val)->
      {
        seqClone.add(Short.MIN_VALUE);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Short>)addingPredicateReturnFalse::test));
    }
    {
      var seqClone=(OmniStack.OfShort)seq.clone();
      ShortPredicate addingPredicateReturnTrue=(val)->
      {
        seqClone.add(Short.MIN_VALUE);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(addingPredicateReturnTrue));
    }
    {
      var seqClone=(OmniStack.OfShort)seq.clone();
      ShortPredicate addingPredicateReturnTrue=(val)->
      {
        seqClone.add(Short.MIN_VALUE);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Short>)addingPredicateReturnTrue::test));
    }
  }
  @Test
  public void testRemoveIfReturnFalseArrSeqCheckedStack()
  {
    ShortPredicate filter=(val)->false;
    var seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertToshort(i);
      seq.add(val);
    }
    {
      Assertions.assertFalse(seq.removeIf(filter));
      Assertions.assertFalse(seq.removeIf((Predicate<Short>)filter::test));
    }
  }
  @Test
  public void testEmptyRemoveIfArrSeqCheckedList()
  {
    ShortPredicate filter=(val)->true;
    var seq=new CheckedList();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Short>)filter::test));
  }
  @Test
  public void testRemoveIfModCheckArrSeqCheckedList()
  {
    var seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertToshort(i);
      seq.add(val);
    }
    {
    }
    {
      var seqClone=(OmniList.OfShort)seq.clone();
      ShortPredicate removingPredicateReturnFalse=(val)->
      {
        seqClone.remove(0);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(removingPredicateReturnFalse));
    }
    {
      var seqClone=(OmniList.OfShort)seq.clone();
      ShortPredicate removingPredicateReturnFalse=(val)->
      {
        seqClone.remove(0);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Short>)removingPredicateReturnFalse::test));
    }
    {
      var seqClone=(OmniList.OfShort)seq.clone();
      ShortPredicate removingPredicateReturnTrue=(val)->
      {
        seqClone.remove(0);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(removingPredicateReturnTrue));
    }
    {
      var seqClone=(OmniList.OfShort)seq.clone();
      ShortPredicate removingPredicateReturnTrue=(val)->
      {
        seqClone.remove(0);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Short>)removingPredicateReturnTrue::test));
    }
    {
      var seqClone=(OmniList.OfShort)seq.clone();
      ShortPredicate addingPredicateReturnFalse=(val)->
      {
        seqClone.add(Short.MIN_VALUE);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(addingPredicateReturnFalse));
    }
    {
      var seqClone=(OmniList.OfShort)seq.clone();
      ShortPredicate addingPredicateReturnFalse=(val)->
      {
        seqClone.add(Short.MIN_VALUE);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Short>)addingPredicateReturnFalse::test));
    }
    {
      var seqClone=(OmniList.OfShort)seq.clone();
      ShortPredicate addingPredicateReturnTrue=(val)->
      {
        seqClone.add(Short.MIN_VALUE);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(addingPredicateReturnTrue));
    }
    {
      var seqClone=(OmniList.OfShort)seq.clone();
      ShortPredicate addingPredicateReturnTrue=(val)->
      {
        seqClone.add(Short.MIN_VALUE);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Short>)addingPredicateReturnTrue::test));
    }
  }
  @Test
  public void testRemoveIfReturnFalseArrSeqCheckedList()
  {
    ShortPredicate filter=(val)->false;
    var seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertToshort(i);
      seq.add(val);
    }
    {
      Assertions.assertFalse(seq.removeIf(filter));
      Assertions.assertFalse(seq.removeIf((Predicate<Short>)filter::test));
    }
  }
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedSubList()
  {
    ShortPredicate filter=(val)->true;
    var root=new UncheckedList();
    var seq=root.subList(0,0);
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Short>)filter::test));
  }
  @Test
  public void testRemoveIfReturnFalseArrSeqUncheckedSubList()
  {
    ShortPredicate filter=(val)->false;
    var root=new UncheckedList();
    var seq=root.subList(0,0);
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertToshort(i);
      seq.add(val);
    }
    {
      Assertions.assertFalse(seq.removeIf(filter));
      Assertions.assertFalse(seq.removeIf((Predicate<Short>)filter::test));
    }
  }
  @Test
  public void testEmptyRemoveIfArrSeqCheckedSubList()
  {
    ShortPredicate filter=(val)->true;
    var root=new CheckedList();
    var seq=root.subList(0,0);
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Short>)filter::test));
  }
  @Test
  public void testRemoveIfModCheckArrSeqCheckedSubList()
  {
    var root=new CheckedList();
    var seq=root.subList(0,0);
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertToshort(i);
      seq.add(val);
    }
    {
    }
    {
      var seqClone=(OmniList.OfShort)seq.clone();
      ShortPredicate removingPredicateReturnFalse=(val)->
      {
        seqClone.remove(0);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(removingPredicateReturnFalse));
    }
    {
      var seqClone=(OmniList.OfShort)seq.clone();
      ShortPredicate removingPredicateReturnFalse=(val)->
      {
        seqClone.remove(0);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Short>)removingPredicateReturnFalse::test));
    }
    {
      var seqClone=(OmniList.OfShort)seq.clone();
      ShortPredicate removingPredicateReturnTrue=(val)->
      {
        seqClone.remove(0);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(removingPredicateReturnTrue));
    }
    {
      var seqClone=(OmniList.OfShort)seq.clone();
      ShortPredicate removingPredicateReturnTrue=(val)->
      {
        seqClone.remove(0);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Short>)removingPredicateReturnTrue::test));
    }
    {
      var seqClone=(OmniList.OfShort)seq.clone();
      ShortPredicate addingPredicateReturnFalse=(val)->
      {
        seqClone.add(Short.MIN_VALUE);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(addingPredicateReturnFalse));
    }
    {
      var seqClone=(OmniList.OfShort)seq.clone();
      ShortPredicate addingPredicateReturnFalse=(val)->
      {
        seqClone.add(Short.MIN_VALUE);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Short>)addingPredicateReturnFalse::test));
    }
    {
      var seqClone=(OmniList.OfShort)seq.clone();
      ShortPredicate addingPredicateReturnTrue=(val)->
      {
        seqClone.add(Short.MIN_VALUE);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(addingPredicateReturnTrue));
    }
    {
      var seqClone=(OmniList.OfShort)seq.clone();
      ShortPredicate addingPredicateReturnTrue=(val)->
      {
        seqClone.add(Short.MIN_VALUE);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Short>)addingPredicateReturnTrue::test));
    }
  }
  @Test
  public void testRemoveIfReturnFalseArrSeqCheckedSubList()
  {
    ShortPredicate filter=(val)->false;
    var root=new CheckedList();
    var seq=root.subList(0,0);
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertToshort(i);
      seq.add(val);
    }
    {
      Assertions.assertFalse(seq.removeIf(filter));
      Assertions.assertFalse(seq.removeIf((Predicate<Short>)filter::test));
    }
  }
}
