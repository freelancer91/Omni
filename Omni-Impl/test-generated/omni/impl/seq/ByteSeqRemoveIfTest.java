package omni.impl.seq;
import java.util.function.Predicate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.impl.seq.ByteArrSeq.UncheckedList;
import omni.impl.seq.ByteArrSeq.CheckedList;
import omni.impl.seq.ByteArrSeq.UncheckedStack;
import omni.impl.seq.ByteArrSeq.CheckedStack;
import java.util.ConcurrentModificationException;
import omni.util.TypeConversionUtil;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.function.BytePredicate;
public class ByteSeqRemoveIfTest
{
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedStack()
  {
    BytePredicate filter=(val)->true;
    var seq=new UncheckedStack();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Byte>)filter::test));
  }
  @Test
  public void testRemoveIfReturnFalseArrSeqUncheckedStack()
  {
    BytePredicate filter=(val)->false;
    var seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTobyte(i);
      seq.add(val);
    }
    {
      Assertions.assertFalse(seq.removeIf(filter));
      Assertions.assertFalse(seq.removeIf((Predicate<Byte>)filter::test));
    }
  }
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedList()
  {
    BytePredicate filter=(val)->true;
    var seq=new UncheckedList();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Byte>)filter::test));
  }
  @Test
  public void testRemoveIfReturnFalseArrSeqUncheckedList()
  {
    BytePredicate filter=(val)->false;
    var seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTobyte(i);
      seq.add(val);
    }
    {
      Assertions.assertFalse(seq.removeIf(filter));
      Assertions.assertFalse(seq.removeIf((Predicate<Byte>)filter::test));
    }
  }
  @Test
  public void testEmptyRemoveIfArrSeqCheckedStack()
  {
    BytePredicate filter=(val)->true;
    var seq=new CheckedStack();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Byte>)filter::test));
  }
  @Test
  public void testRemoveIfModCheckArrSeqCheckedStack()
  {
    var seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTobyte(i);
      seq.add(val);
    }
    {
    }
    {
      var seqClone=(OmniStack.OfByte)seq.clone();
      BytePredicate removingPredicateReturnFalse=(val)->
      {
        seqClone.pop();
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(removingPredicateReturnFalse));
    }
    {
      var seqClone=(OmniStack.OfByte)seq.clone();
      BytePredicate removingPredicateReturnFalse=(val)->
      {
        seqClone.pop();
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Byte>)removingPredicateReturnFalse::test));
    }
    {
      var seqClone=(OmniStack.OfByte)seq.clone();
      BytePredicate removingPredicateReturnTrue=(val)->
      {
        seqClone.pop();
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(removingPredicateReturnTrue));
    }
    {
      var seqClone=(OmniStack.OfByte)seq.clone();
      BytePredicate removingPredicateReturnTrue=(val)->
      {
        seqClone.pop();
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Byte>)removingPredicateReturnTrue::test));
    }
    {
      var seqClone=(OmniStack.OfByte)seq.clone();
      BytePredicate addingPredicateReturnFalse=(val)->
      {
        seqClone.add(Byte.MIN_VALUE);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(addingPredicateReturnFalse));
    }
    {
      var seqClone=(OmniStack.OfByte)seq.clone();
      BytePredicate addingPredicateReturnFalse=(val)->
      {
        seqClone.add(Byte.MIN_VALUE);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Byte>)addingPredicateReturnFalse::test));
    }
    {
      var seqClone=(OmniStack.OfByte)seq.clone();
      BytePredicate addingPredicateReturnTrue=(val)->
      {
        seqClone.add(Byte.MIN_VALUE);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(addingPredicateReturnTrue));
    }
    {
      var seqClone=(OmniStack.OfByte)seq.clone();
      BytePredicate addingPredicateReturnTrue=(val)->
      {
        seqClone.add(Byte.MIN_VALUE);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Byte>)addingPredicateReturnTrue::test));
    }
  }
  @Test
  public void testRemoveIfReturnFalseArrSeqCheckedStack()
  {
    BytePredicate filter=(val)->false;
    var seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTobyte(i);
      seq.add(val);
    }
    {
      Assertions.assertFalse(seq.removeIf(filter));
      Assertions.assertFalse(seq.removeIf((Predicate<Byte>)filter::test));
    }
  }
  @Test
  public void testEmptyRemoveIfArrSeqCheckedList()
  {
    BytePredicate filter=(val)->true;
    var seq=new CheckedList();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Byte>)filter::test));
  }
  @Test
  public void testRemoveIfModCheckArrSeqCheckedList()
  {
    var seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTobyte(i);
      seq.add(val);
    }
    {
    }
    {
      var seqClone=(OmniList.OfByte)seq.clone();
      BytePredicate removingPredicateReturnFalse=(val)->
      {
        seqClone.remove(0);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(removingPredicateReturnFalse));
    }
    {
      var seqClone=(OmniList.OfByte)seq.clone();
      BytePredicate removingPredicateReturnFalse=(val)->
      {
        seqClone.remove(0);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Byte>)removingPredicateReturnFalse::test));
    }
    {
      var seqClone=(OmniList.OfByte)seq.clone();
      BytePredicate removingPredicateReturnTrue=(val)->
      {
        seqClone.remove(0);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(removingPredicateReturnTrue));
    }
    {
      var seqClone=(OmniList.OfByte)seq.clone();
      BytePredicate removingPredicateReturnTrue=(val)->
      {
        seqClone.remove(0);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Byte>)removingPredicateReturnTrue::test));
    }
    {
      var seqClone=(OmniList.OfByte)seq.clone();
      BytePredicate addingPredicateReturnFalse=(val)->
      {
        seqClone.add(Byte.MIN_VALUE);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(addingPredicateReturnFalse));
    }
    {
      var seqClone=(OmniList.OfByte)seq.clone();
      BytePredicate addingPredicateReturnFalse=(val)->
      {
        seqClone.add(Byte.MIN_VALUE);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Byte>)addingPredicateReturnFalse::test));
    }
    {
      var seqClone=(OmniList.OfByte)seq.clone();
      BytePredicate addingPredicateReturnTrue=(val)->
      {
        seqClone.add(Byte.MIN_VALUE);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(addingPredicateReturnTrue));
    }
    {
      var seqClone=(OmniList.OfByte)seq.clone();
      BytePredicate addingPredicateReturnTrue=(val)->
      {
        seqClone.add(Byte.MIN_VALUE);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Byte>)addingPredicateReturnTrue::test));
    }
  }
  @Test
  public void testRemoveIfReturnFalseArrSeqCheckedList()
  {
    BytePredicate filter=(val)->false;
    var seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTobyte(i);
      seq.add(val);
    }
    {
      Assertions.assertFalse(seq.removeIf(filter));
      Assertions.assertFalse(seq.removeIf((Predicate<Byte>)filter::test));
    }
  }
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedSubList()
  {
    BytePredicate filter=(val)->true;
    var root=new UncheckedList();
    var seq=root.subList(0,0);
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Byte>)filter::test));
  }
  @Test
  public void testRemoveIfReturnFalseArrSeqUncheckedSubList()
  {
    BytePredicate filter=(val)->false;
    var root=new UncheckedList();
    var seq=root.subList(0,0);
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTobyte(i);
      seq.add(val);
    }
    {
      Assertions.assertFalse(seq.removeIf(filter));
      Assertions.assertFalse(seq.removeIf((Predicate<Byte>)filter::test));
    }
  }
  @Test
  public void testEmptyRemoveIfArrSeqCheckedSubList()
  {
    BytePredicate filter=(val)->true;
    var root=new CheckedList();
    var seq=root.subList(0,0);
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Byte>)filter::test));
  }
  @Test
  public void testRemoveIfModCheckArrSeqCheckedSubList()
  {
    var root=new CheckedList();
    var seq=root.subList(0,0);
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTobyte(i);
      seq.add(val);
    }
    {
    }
    {
      var seqClone=(OmniList.OfByte)seq.clone();
      BytePredicate removingPredicateReturnFalse=(val)->
      {
        seqClone.remove(0);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(removingPredicateReturnFalse));
    }
    {
      var seqClone=(OmniList.OfByte)seq.clone();
      BytePredicate removingPredicateReturnFalse=(val)->
      {
        seqClone.remove(0);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Byte>)removingPredicateReturnFalse::test));
    }
    {
      var seqClone=(OmniList.OfByte)seq.clone();
      BytePredicate removingPredicateReturnTrue=(val)->
      {
        seqClone.remove(0);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(removingPredicateReturnTrue));
    }
    {
      var seqClone=(OmniList.OfByte)seq.clone();
      BytePredicate removingPredicateReturnTrue=(val)->
      {
        seqClone.remove(0);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Byte>)removingPredicateReturnTrue::test));
    }
    {
      var seqClone=(OmniList.OfByte)seq.clone();
      BytePredicate addingPredicateReturnFalse=(val)->
      {
        seqClone.add(Byte.MIN_VALUE);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(addingPredicateReturnFalse));
    }
    {
      var seqClone=(OmniList.OfByte)seq.clone();
      BytePredicate addingPredicateReturnFalse=(val)->
      {
        seqClone.add(Byte.MIN_VALUE);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Byte>)addingPredicateReturnFalse::test));
    }
    {
      var seqClone=(OmniList.OfByte)seq.clone();
      BytePredicate addingPredicateReturnTrue=(val)->
      {
        seqClone.add(Byte.MIN_VALUE);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(addingPredicateReturnTrue));
    }
    {
      var seqClone=(OmniList.OfByte)seq.clone();
      BytePredicate addingPredicateReturnTrue=(val)->
      {
        seqClone.add(Byte.MIN_VALUE);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Byte>)addingPredicateReturnTrue::test));
    }
  }
  @Test
  public void testRemoveIfReturnFalseArrSeqCheckedSubList()
  {
    BytePredicate filter=(val)->false;
    var root=new CheckedList();
    var seq=root.subList(0,0);
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertTobyte(i);
      seq.add(val);
    }
    {
      Assertions.assertFalse(seq.removeIf(filter));
      Assertions.assertFalse(seq.removeIf((Predicate<Byte>)filter::test));
    }
  }
}
