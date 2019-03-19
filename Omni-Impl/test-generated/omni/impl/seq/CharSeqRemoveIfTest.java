package omni.impl.seq;
import java.util.function.Predicate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.impl.seq.CharArrSeq.UncheckedList;
import omni.impl.seq.CharArrSeq.CheckedList;
import omni.impl.seq.CharArrSeq.UncheckedStack;
import omni.impl.seq.CharArrSeq.CheckedStack;
import java.util.ConcurrentModificationException;
import omni.util.TypeConversionUtil;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.function.CharPredicate;
public class CharSeqRemoveIfTest
{
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedStack()
  {
    CharPredicate filter=(val)->true;
    var seq=new UncheckedStack();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Character>)filter::test));
  }
  @Test
  public void testRemoveIfReturnFalseArrSeqUncheckedStack()
  {
    CharPredicate filter=(val)->false;
    var seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTochar(i);
      seq.add(val);
    }
    {
      Assertions.assertFalse(seq.removeIf(filter));
      Assertions.assertFalse(seq.removeIf((Predicate<Character>)filter::test));
    }
  }
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedList()
  {
    CharPredicate filter=(val)->true;
    var seq=new UncheckedList();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Character>)filter::test));
  }
  @Test
  public void testRemoveIfReturnFalseArrSeqUncheckedList()
  {
    CharPredicate filter=(val)->false;
    var seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTochar(i);
      seq.add(val);
    }
    {
      Assertions.assertFalse(seq.removeIf(filter));
      Assertions.assertFalse(seq.removeIf((Predicate<Character>)filter::test));
    }
  }
  @Test
  public void testEmptyRemoveIfArrSeqCheckedStack()
  {
    CharPredicate filter=(val)->true;
    var seq=new CheckedStack();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Character>)filter::test));
  }
  @Test
  public void testRemoveIfModCheckArrSeqCheckedStack()
  {
    var seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTochar(i);
      seq.add(val);
    }
    {
    }
    {
      var seqClone=(OmniStack.OfChar)seq.clone();
      CharPredicate removingPredicateReturnFalse=(val)->
      {
        seqClone.pop();
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(removingPredicateReturnFalse));
    }
    {
      var seqClone=(OmniStack.OfChar)seq.clone();
      CharPredicate removingPredicateReturnFalse=(val)->
      {
        seqClone.pop();
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Character>)removingPredicateReturnFalse::test));
    }
    {
      var seqClone=(OmniStack.OfChar)seq.clone();
      CharPredicate removingPredicateReturnTrue=(val)->
      {
        seqClone.pop();
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(removingPredicateReturnTrue));
    }
    {
      var seqClone=(OmniStack.OfChar)seq.clone();
      CharPredicate removingPredicateReturnTrue=(val)->
      {
        seqClone.pop();
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Character>)removingPredicateReturnTrue::test));
    }
    {
      var seqClone=(OmniStack.OfChar)seq.clone();
      CharPredicate addingPredicateReturnFalse=(val)->
      {
        seqClone.add(Character.MIN_VALUE);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(addingPredicateReturnFalse));
    }
    {
      var seqClone=(OmniStack.OfChar)seq.clone();
      CharPredicate addingPredicateReturnFalse=(val)->
      {
        seqClone.add(Character.MIN_VALUE);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Character>)addingPredicateReturnFalse::test));
    }
    {
      var seqClone=(OmniStack.OfChar)seq.clone();
      CharPredicate addingPredicateReturnTrue=(val)->
      {
        seqClone.add(Character.MIN_VALUE);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(addingPredicateReturnTrue));
    }
    {
      var seqClone=(OmniStack.OfChar)seq.clone();
      CharPredicate addingPredicateReturnTrue=(val)->
      {
        seqClone.add(Character.MIN_VALUE);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Character>)addingPredicateReturnTrue::test));
    }
  }
  @Test
  public void testRemoveIfReturnFalseArrSeqCheckedStack()
  {
    CharPredicate filter=(val)->false;
    var seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTochar(i);
      seq.add(val);
    }
    {
      Assertions.assertFalse(seq.removeIf(filter));
      Assertions.assertFalse(seq.removeIf((Predicate<Character>)filter::test));
    }
  }
  @Test
  public void testEmptyRemoveIfArrSeqCheckedList()
  {
    CharPredicate filter=(val)->true;
    var seq=new CheckedList();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Character>)filter::test));
  }
  @Test
  public void testRemoveIfModCheckArrSeqCheckedList()
  {
    var seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTochar(i);
      seq.add(val);
    }
    {
    }
    {
      var seqClone=(OmniList.OfChar)seq.clone();
      CharPredicate removingPredicateReturnFalse=(val)->
      {
        seqClone.remove(0);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(removingPredicateReturnFalse));
    }
    {
      var seqClone=(OmniList.OfChar)seq.clone();
      CharPredicate removingPredicateReturnFalse=(val)->
      {
        seqClone.remove(0);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Character>)removingPredicateReturnFalse::test));
    }
    {
      var seqClone=(OmniList.OfChar)seq.clone();
      CharPredicate removingPredicateReturnTrue=(val)->
      {
        seqClone.remove(0);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(removingPredicateReturnTrue));
    }
    {
      var seqClone=(OmniList.OfChar)seq.clone();
      CharPredicate removingPredicateReturnTrue=(val)->
      {
        seqClone.remove(0);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Character>)removingPredicateReturnTrue::test));
    }
    {
      var seqClone=(OmniList.OfChar)seq.clone();
      CharPredicate addingPredicateReturnFalse=(val)->
      {
        seqClone.add(Character.MIN_VALUE);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(addingPredicateReturnFalse));
    }
    {
      var seqClone=(OmniList.OfChar)seq.clone();
      CharPredicate addingPredicateReturnFalse=(val)->
      {
        seqClone.add(Character.MIN_VALUE);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Character>)addingPredicateReturnFalse::test));
    }
    {
      var seqClone=(OmniList.OfChar)seq.clone();
      CharPredicate addingPredicateReturnTrue=(val)->
      {
        seqClone.add(Character.MIN_VALUE);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(addingPredicateReturnTrue));
    }
    {
      var seqClone=(OmniList.OfChar)seq.clone();
      CharPredicate addingPredicateReturnTrue=(val)->
      {
        seqClone.add(Character.MIN_VALUE);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Character>)addingPredicateReturnTrue::test));
    }
  }
  @Test
  public void testRemoveIfReturnFalseArrSeqCheckedList()
  {
    CharPredicate filter=(val)->false;
    var seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTochar(i);
      seq.add(val);
    }
    {
      Assertions.assertFalse(seq.removeIf(filter));
      Assertions.assertFalse(seq.removeIf((Predicate<Character>)filter::test));
    }
  }
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedSubList()
  {
    CharPredicate filter=(val)->true;
    var root=new UncheckedList();
    var seq=root.subList(0,0);
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Character>)filter::test));
  }
  @Test
  public void testRemoveIfReturnFalseArrSeqUncheckedSubList()
  {
    CharPredicate filter=(val)->false;
    var root=new UncheckedList();
    var seq=root.subList(0,0);
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTochar(i);
      seq.add(val);
    }
    {
      Assertions.assertFalse(seq.removeIf(filter));
      Assertions.assertFalse(seq.removeIf((Predicate<Character>)filter::test));
    }
  }
  @Test
  public void testEmptyRemoveIfArrSeqCheckedSubList()
  {
    CharPredicate filter=(val)->true;
    var root=new CheckedList();
    var seq=root.subList(0,0);
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Character>)filter::test));
  }
  @Test
  public void testRemoveIfModCheckArrSeqCheckedSubList()
  {
    var root=new CheckedList();
    var seq=root.subList(0,0);
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTochar(i);
      seq.add(val);
    }
    {
    }
    {
      var seqClone=(OmniList.OfChar)seq.clone();
      CharPredicate removingPredicateReturnFalse=(val)->
      {
        seqClone.remove(0);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(removingPredicateReturnFalse));
    }
    {
      var seqClone=(OmniList.OfChar)seq.clone();
      CharPredicate removingPredicateReturnFalse=(val)->
      {
        seqClone.remove(0);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Character>)removingPredicateReturnFalse::test));
    }
    {
      var seqClone=(OmniList.OfChar)seq.clone();
      CharPredicate removingPredicateReturnTrue=(val)->
      {
        seqClone.remove(0);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(removingPredicateReturnTrue));
    }
    {
      var seqClone=(OmniList.OfChar)seq.clone();
      CharPredicate removingPredicateReturnTrue=(val)->
      {
        seqClone.remove(0);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Character>)removingPredicateReturnTrue::test));
    }
    {
      var seqClone=(OmniList.OfChar)seq.clone();
      CharPredicate addingPredicateReturnFalse=(val)->
      {
        seqClone.add(Character.MIN_VALUE);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(addingPredicateReturnFalse));
    }
    {
      var seqClone=(OmniList.OfChar)seq.clone();
      CharPredicate addingPredicateReturnFalse=(val)->
      {
        seqClone.add(Character.MIN_VALUE);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Character>)addingPredicateReturnFalse::test));
    }
    {
      var seqClone=(OmniList.OfChar)seq.clone();
      CharPredicate addingPredicateReturnTrue=(val)->
      {
        seqClone.add(Character.MIN_VALUE);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(addingPredicateReturnTrue));
    }
    {
      var seqClone=(OmniList.OfChar)seq.clone();
      CharPredicate addingPredicateReturnTrue=(val)->
      {
        seqClone.add(Character.MIN_VALUE);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Character>)addingPredicateReturnTrue::test));
    }
  }
  @Test
  public void testRemoveIfReturnFalseArrSeqCheckedSubList()
  {
    CharPredicate filter=(val)->false;
    var root=new CheckedList();
    var seq=root.subList(0,0);
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTochar(i);
      seq.add(val);
    }
    {
      Assertions.assertFalse(seq.removeIf(filter));
      Assertions.assertFalse(seq.removeIf((Predicate<Character>)filter::test));
    }
  }
}
