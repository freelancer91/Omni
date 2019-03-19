package omni.impl.seq;
import java.util.function.Predicate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.impl.seq.DoubleArrSeq.UncheckedList;
import omni.impl.seq.DoubleArrSeq.CheckedList;
import omni.impl.seq.DoubleArrSeq.UncheckedStack;
import omni.impl.seq.DoubleArrSeq.CheckedStack;
import java.util.ConcurrentModificationException;
import omni.util.TypeConversionUtil;
import omni.api.OmniList;
import omni.api.OmniStack;
import java.util.function.DoublePredicate;
public class DoubleSeqRemoveIfTest
{
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedStack()
  {
    DoublePredicate filter=(val)->true;
    var seq=new UncheckedStack();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Double>)filter::test));
  }
  @Test
  public void testRemoveIfReturnFalseArrSeqUncheckedStack()
  {
    DoublePredicate filter=(val)->false;
    var seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTodouble(i);
      seq.add(val);
    }
    {
      Assertions.assertFalse(seq.removeIf(filter));
      Assertions.assertFalse(seq.removeIf((Predicate<Double>)filter::test));
    }
  }
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedList()
  {
    DoublePredicate filter=(val)->true;
    var seq=new UncheckedList();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Double>)filter::test));
  }
  @Test
  public void testRemoveIfReturnFalseArrSeqUncheckedList()
  {
    DoublePredicate filter=(val)->false;
    var seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTodouble(i);
      seq.add(val);
    }
    {
      Assertions.assertFalse(seq.removeIf(filter));
      Assertions.assertFalse(seq.removeIf((Predicate<Double>)filter::test));
    }
  }
  @Test
  public void testEmptyRemoveIfArrSeqCheckedStack()
  {
    DoublePredicate filter=(val)->true;
    var seq=new CheckedStack();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Double>)filter::test));
  }
  @Test
  public void testRemoveIfModCheckArrSeqCheckedStack()
  {
    var seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTodouble(i);
      seq.add(val);
    }
    {
    }
    {
      var seqClone=(OmniStack.OfDouble)seq.clone();
      DoublePredicate removingPredicateReturnFalse=(val)->
      {
        seqClone.pop();
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(removingPredicateReturnFalse));
    }
    {
      var seqClone=(OmniStack.OfDouble)seq.clone();
      DoublePredicate removingPredicateReturnFalse=(val)->
      {
        seqClone.pop();
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Double>)removingPredicateReturnFalse::test));
    }
    {
      var seqClone=(OmniStack.OfDouble)seq.clone();
      DoublePredicate removingPredicateReturnTrue=(val)->
      {
        seqClone.pop();
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(removingPredicateReturnTrue));
    }
    {
      var seqClone=(OmniStack.OfDouble)seq.clone();
      DoublePredicate removingPredicateReturnTrue=(val)->
      {
        seqClone.pop();
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Double>)removingPredicateReturnTrue::test));
    }
    {
      var seqClone=(OmniStack.OfDouble)seq.clone();
      DoublePredicate addingPredicateReturnFalse=(val)->
      {
        seqClone.add(Double.NaN);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(addingPredicateReturnFalse));
    }
    {
      var seqClone=(OmniStack.OfDouble)seq.clone();
      DoublePredicate addingPredicateReturnFalse=(val)->
      {
        seqClone.add(Double.NaN);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Double>)addingPredicateReturnFalse::test));
    }
    {
      var seqClone=(OmniStack.OfDouble)seq.clone();
      DoublePredicate addingPredicateReturnTrue=(val)->
      {
        seqClone.add(Double.NaN);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(addingPredicateReturnTrue));
    }
    {
      var seqClone=(OmniStack.OfDouble)seq.clone();
      DoublePredicate addingPredicateReturnTrue=(val)->
      {
        seqClone.add(Double.NaN);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Double>)addingPredicateReturnTrue::test));
    }
  }
  @Test
  public void testRemoveIfReturnFalseArrSeqCheckedStack()
  {
    DoublePredicate filter=(val)->false;
    var seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTodouble(i);
      seq.add(val);
    }
    {
      Assertions.assertFalse(seq.removeIf(filter));
      Assertions.assertFalse(seq.removeIf((Predicate<Double>)filter::test));
    }
  }
  @Test
  public void testEmptyRemoveIfArrSeqCheckedList()
  {
    DoublePredicate filter=(val)->true;
    var seq=new CheckedList();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Double>)filter::test));
  }
  @Test
  public void testRemoveIfModCheckArrSeqCheckedList()
  {
    var seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTodouble(i);
      seq.add(val);
    }
    {
    }
    {
      var seqClone=(OmniList.OfDouble)seq.clone();
      DoublePredicate removingPredicateReturnFalse=(val)->
      {
        seqClone.remove(0);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(removingPredicateReturnFalse));
    }
    {
      var seqClone=(OmniList.OfDouble)seq.clone();
      DoublePredicate removingPredicateReturnFalse=(val)->
      {
        seqClone.remove(0);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Double>)removingPredicateReturnFalse::test));
    }
    {
      var seqClone=(OmniList.OfDouble)seq.clone();
      DoublePredicate removingPredicateReturnTrue=(val)->
      {
        seqClone.remove(0);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(removingPredicateReturnTrue));
    }
    {
      var seqClone=(OmniList.OfDouble)seq.clone();
      DoublePredicate removingPredicateReturnTrue=(val)->
      {
        seqClone.remove(0);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Double>)removingPredicateReturnTrue::test));
    }
    {
      var seqClone=(OmniList.OfDouble)seq.clone();
      DoublePredicate addingPredicateReturnFalse=(val)->
      {
        seqClone.add(Double.NaN);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(addingPredicateReturnFalse));
    }
    {
      var seqClone=(OmniList.OfDouble)seq.clone();
      DoublePredicate addingPredicateReturnFalse=(val)->
      {
        seqClone.add(Double.NaN);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Double>)addingPredicateReturnFalse::test));
    }
    {
      var seqClone=(OmniList.OfDouble)seq.clone();
      DoublePredicate addingPredicateReturnTrue=(val)->
      {
        seqClone.add(Double.NaN);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(addingPredicateReturnTrue));
    }
    {
      var seqClone=(OmniList.OfDouble)seq.clone();
      DoublePredicate addingPredicateReturnTrue=(val)->
      {
        seqClone.add(Double.NaN);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Double>)addingPredicateReturnTrue::test));
    }
  }
  @Test
  public void testRemoveIfReturnFalseArrSeqCheckedList()
  {
    DoublePredicate filter=(val)->false;
    var seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTodouble(i);
      seq.add(val);
    }
    {
      Assertions.assertFalse(seq.removeIf(filter));
      Assertions.assertFalse(seq.removeIf((Predicate<Double>)filter::test));
    }
  }
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedSubList()
  {
    DoublePredicate filter=(val)->true;
    var root=new UncheckedList();
    var seq=root.subList(0,0);
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Double>)filter::test));
  }
  @Test
  public void testRemoveIfReturnFalseArrSeqUncheckedSubList()
  {
    DoublePredicate filter=(val)->false;
    var root=new UncheckedList();
    var seq=root.subList(0,0);
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTodouble(i);
      seq.add(val);
    }
    {
      Assertions.assertFalse(seq.removeIf(filter));
      Assertions.assertFalse(seq.removeIf((Predicate<Double>)filter::test));
    }
  }
  @Test
  public void testEmptyRemoveIfArrSeqCheckedSubList()
  {
    DoublePredicate filter=(val)->true;
    var root=new CheckedList();
    var seq=root.subList(0,0);
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Double>)filter::test));
  }
  @Test
  public void testRemoveIfModCheckArrSeqCheckedSubList()
  {
    var root=new CheckedList();
    var seq=root.subList(0,0);
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTodouble(i);
      seq.add(val);
    }
    {
    }
    {
      var seqClone=(OmniList.OfDouble)seq.clone();
      DoublePredicate removingPredicateReturnFalse=(val)->
      {
        seqClone.remove(0);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(removingPredicateReturnFalse));
    }
    {
      var seqClone=(OmniList.OfDouble)seq.clone();
      DoublePredicate removingPredicateReturnFalse=(val)->
      {
        seqClone.remove(0);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Double>)removingPredicateReturnFalse::test));
    }
    {
      var seqClone=(OmniList.OfDouble)seq.clone();
      DoublePredicate removingPredicateReturnTrue=(val)->
      {
        seqClone.remove(0);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(removingPredicateReturnTrue));
    }
    {
      var seqClone=(OmniList.OfDouble)seq.clone();
      DoublePredicate removingPredicateReturnTrue=(val)->
      {
        seqClone.remove(0);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Double>)removingPredicateReturnTrue::test));
    }
    {
      var seqClone=(OmniList.OfDouble)seq.clone();
      DoublePredicate addingPredicateReturnFalse=(val)->
      {
        seqClone.add(Double.NaN);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(addingPredicateReturnFalse));
    }
    {
      var seqClone=(OmniList.OfDouble)seq.clone();
      DoublePredicate addingPredicateReturnFalse=(val)->
      {
        seqClone.add(Double.NaN);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Double>)addingPredicateReturnFalse::test));
    }
    {
      var seqClone=(OmniList.OfDouble)seq.clone();
      DoublePredicate addingPredicateReturnTrue=(val)->
      {
        seqClone.add(Double.NaN);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(addingPredicateReturnTrue));
    }
    {
      var seqClone=(OmniList.OfDouble)seq.clone();
      DoublePredicate addingPredicateReturnTrue=(val)->
      {
        seqClone.add(Double.NaN);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Double>)addingPredicateReturnTrue::test));
    }
  }
  @Test
  public void testRemoveIfReturnFalseArrSeqCheckedSubList()
  {
    DoublePredicate filter=(val)->false;
    var root=new CheckedList();
    var seq=root.subList(0,0);
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTodouble(i);
      seq.add(val);
    }
    {
      Assertions.assertFalse(seq.removeIf(filter));
      Assertions.assertFalse(seq.removeIf((Predicate<Double>)filter::test));
    }
  }
}
