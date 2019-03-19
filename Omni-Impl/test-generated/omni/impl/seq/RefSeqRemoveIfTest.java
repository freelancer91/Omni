package omni.impl.seq;
import java.util.function.Predicate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.impl.seq.RefArrSeq.UncheckedList;
import omni.impl.seq.RefArrSeq.CheckedList;
import omni.impl.seq.RefArrSeq.UncheckedStack;
import omni.impl.seq.RefArrSeq.CheckedStack;
import java.util.ConcurrentModificationException;
import omni.util.TypeConversionUtil;
import omni.api.OmniList;
import omni.api.OmniStack;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class RefSeqRemoveIfTest
{
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedStack()
  {
    Predicate filter=(val)->true;
    var seq=new UncheckedStack();
    Assertions.assertFalse(seq.removeIf(filter));
  }
  @Test
  public void testRemoveIfReturnFalseArrSeqUncheckedStack()
  {
    Predicate filter=(val)->false;
    var seq=new UncheckedStack();
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertToInteger(i);
      seq.add(val);
    }
    {
      Assertions.assertFalse(seq.removeIf(filter));
    }
  }
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedList()
  {
    Predicate filter=(val)->true;
    var seq=new UncheckedList();
    Assertions.assertFalse(seq.removeIf(filter));
  }
  @Test
  public void testRemoveIfReturnFalseArrSeqUncheckedList()
  {
    Predicate filter=(val)->false;
    var seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertToInteger(i);
      seq.add(val);
    }
    {
      Assertions.assertFalse(seq.removeIf(filter));
    }
  }
  @Test
  public void testEmptyRemoveIfArrSeqCheckedStack()
  {
    Predicate filter=(val)->true;
    var seq=new CheckedStack();
    Assertions.assertFalse(seq.removeIf(filter));
  }
  @Test
  public void testRemoveIfModCheckArrSeqCheckedStack()
  {
    var seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertToInteger(i);
      seq.add(val);
    }
    {
    }
    {
      var seqClone=(OmniStack.OfRef)seq.clone();
      Predicate removingPredicateReturnFalse=(val)->
      {
        seqClone.pop();
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(removingPredicateReturnFalse));
    }
    {
      var seqClone=(OmniStack.OfRef)seq.clone();
      Predicate removingPredicateReturnTrue=(val)->
      {
        seqClone.pop();
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(removingPredicateReturnTrue));
    }
    {
      var seqClone=(OmniStack.OfRef)seq.clone();
      Predicate addingPredicateReturnFalse=(val)->
      {
        seqClone.add(null);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(addingPredicateReturnFalse));
    }
    {
      var seqClone=(OmniStack.OfRef)seq.clone();
      Predicate addingPredicateReturnTrue=(val)->
      {
        seqClone.add(null);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(addingPredicateReturnTrue));
    }
  }
  @Test
  public void testRemoveIfReturnFalseArrSeqCheckedStack()
  {
    Predicate filter=(val)->false;
    var seq=new CheckedStack();
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertToInteger(i);
      seq.add(val);
    }
    {
      Assertions.assertFalse(seq.removeIf(filter));
    }
  }
  @Test
  public void testEmptyRemoveIfArrSeqCheckedList()
  {
    Predicate filter=(val)->true;
    var seq=new CheckedList();
    Assertions.assertFalse(seq.removeIf(filter));
  }
  @Test
  public void testRemoveIfModCheckArrSeqCheckedList()
  {
    var seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertToInteger(i);
      seq.add(val);
    }
    {
    }
    {
      var seqClone=(OmniList.OfRef)seq.clone();
      Predicate removingPredicateReturnFalse=(val)->
      {
        seqClone.remove(0);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(removingPredicateReturnFalse));
    }
    {
      var seqClone=(OmniList.OfRef)seq.clone();
      Predicate removingPredicateReturnTrue=(val)->
      {
        seqClone.remove(0);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(removingPredicateReturnTrue));
    }
    {
      var seqClone=(OmniList.OfRef)seq.clone();
      Predicate addingPredicateReturnFalse=(val)->
      {
        seqClone.add(null);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(addingPredicateReturnFalse));
    }
    {
      var seqClone=(OmniList.OfRef)seq.clone();
      Predicate addingPredicateReturnTrue=(val)->
      {
        seqClone.add(null);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(addingPredicateReturnTrue));
    }
  }
  @Test
  public void testRemoveIfReturnFalseArrSeqCheckedList()
  {
    Predicate filter=(val)->false;
    var seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertToInteger(i);
      seq.add(val);
    }
    {
      Assertions.assertFalse(seq.removeIf(filter));
    }
  }
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedSubList()
  {
    Predicate filter=(val)->true;
    var root=new UncheckedList();
    var seq=root.subList(0,0);
    Assertions.assertFalse(seq.removeIf(filter));
  }
  @Test
  public void testRemoveIfReturnFalseArrSeqUncheckedSubList()
  {
    Predicate filter=(val)->false;
    var root=new UncheckedList();
    var seq=root.subList(0,0);
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertToInteger(i);
      seq.add(val);
    }
    {
      Assertions.assertFalse(seq.removeIf(filter));
    }
  }
  @Test
  public void testEmptyRemoveIfArrSeqCheckedSubList()
  {
    Predicate filter=(val)->true;
    var root=new CheckedList();
    var seq=root.subList(0,0);
    Assertions.assertFalse(seq.removeIf(filter));
  }
  @Test
  public void testRemoveIfModCheckArrSeqCheckedSubList()
  {
    var root=new CheckedList();
    var seq=root.subList(0,0);
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertToInteger(i);
      seq.add(val);
    }
    {
    }
    {
      var seqClone=(OmniList.OfRef)seq.clone();
      Predicate removingPredicateReturnFalse=(val)->
      {
        seqClone.remove(0);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(removingPredicateReturnFalse));
    }
    {
      var seqClone=(OmniList.OfRef)seq.clone();
      Predicate removingPredicateReturnTrue=(val)->
      {
        seqClone.remove(0);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(removingPredicateReturnTrue));
    }
    {
      var seqClone=(OmniList.OfRef)seq.clone();
      Predicate addingPredicateReturnFalse=(val)->
      {
        seqClone.add(null);
        return false;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(addingPredicateReturnFalse));
    }
    {
      var seqClone=(OmniList.OfRef)seq.clone();
      Predicate addingPredicateReturnTrue=(val)->
      {
        seqClone.add(null);
        return true;
      };
      Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(addingPredicateReturnTrue));
    }
  }
  @Test
  public void testRemoveIfReturnFalseArrSeqCheckedSubList()
  {
    Predicate filter=(val)->false;
    var root=new CheckedList();
    var seq=root.subList(0,0);
    for(int i=0;i<100;++i)
    {
      var val=TypeConversionUtil.convertToInteger(i);
      seq.add(val);
    }
    {
      Assertions.assertFalse(seq.removeIf(filter));
    }
  }
}
