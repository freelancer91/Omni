package omni.impl.seq;
import java.util.function.Predicate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.impl.seq.BooleanArrSeq.UncheckedList;
import omni.impl.seq.BooleanArrSeq.CheckedList;
import omni.impl.seq.BooleanArrSeq.UncheckedStack;
import omni.impl.seq.BooleanArrSeq.CheckedStack;
import java.util.ConcurrentModificationException;
import omni.impl.CheckedCollectionTest;
import omni.util.booleanPredicates;
import omni.function.BooleanPredicate;
public class BooleanArrSeqRemoveIfTest
{
//TODO place sanity checks for checked sequence modification behavior
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedStack()
  {
    BooleanPredicate filter=booleanPredicates.MarkAll.getPred(null,0);
    var seq=new UncheckedStack();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Boolean>)filter::test));
  }
  @Test
  public void testRemoveIfUncheckedStack()
  {
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new UncheckedStack(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        BooleanPredicate pred=val->true;
        //remove all
        boolean expectedResult=true;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new UncheckedStack(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Predicate<Boolean> pred=val->true;
        //remove all
        boolean expectedResult=true;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new UncheckedStack(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        BooleanPredicate pred=val->false;
        //remove none
        boolean expectedResult=false;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertEquals(10,seq.size());
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new UncheckedStack(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Predicate<Boolean> pred=val->false;
        //remove none
        boolean expectedResult=false;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertEquals(10,seq.size());
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new UncheckedStack(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        BooleanPredicate pred=val->val;
        //remove true
        boolean expectedResult=seq.contains(true);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(true));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new UncheckedStack(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Predicate<Boolean> pred=val->val;
        //remove true
        boolean expectedResult=seq.contains(true);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(true));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new UncheckedStack(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        BooleanPredicate pred=val->!val;
        //remove false
        boolean expectedResult=seq.contains(false);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(false));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new UncheckedStack(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Predicate<Boolean> pred=val->!val;
        //remove false
        boolean expectedResult=seq.contains(false);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(false));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new UncheckedStack(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        BooleanPredicate pred=val->true;
        //remove all
        boolean expectedResult=true;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new UncheckedStack(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Predicate<Boolean> pred=val->true;
        //remove all
        boolean expectedResult=true;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new UncheckedStack(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        BooleanPredicate pred=val->false;
        //remove none
        boolean expectedResult=false;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertEquals(10,seq.size());
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new UncheckedStack(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Predicate<Boolean> pred=val->false;
        //remove none
        boolean expectedResult=false;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertEquals(10,seq.size());
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new UncheckedStack(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        BooleanPredicate pred=val->val;
        //remove true
        boolean expectedResult=seq.contains(true);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(true));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new UncheckedStack(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Predicate<Boolean> pred=val->val;
        //remove true
        boolean expectedResult=seq.contains(true);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(true));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new UncheckedStack(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        BooleanPredicate pred=val->!val;
        //remove false
        boolean expectedResult=seq.contains(false);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(false));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new UncheckedStack(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Predicate<Boolean> pred=val->!val;
        //remove false
        boolean expectedResult=seq.contains(false);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(false));
      }
    }
  }
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedList()
  {
    BooleanPredicate filter=booleanPredicates.MarkAll.getPred(null,0);
    var seq=new UncheckedList();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Boolean>)filter::test));
  }
  @Test
  public void testRemoveIfUncheckedList()
  {
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new UncheckedList(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        BooleanPredicate pred=val->true;
        //remove all
        boolean expectedResult=true;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new UncheckedList(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Predicate<Boolean> pred=val->true;
        //remove all
        boolean expectedResult=true;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new UncheckedList(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        BooleanPredicate pred=val->false;
        //remove none
        boolean expectedResult=false;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertEquals(10,seq.size());
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new UncheckedList(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Predicate<Boolean> pred=val->false;
        //remove none
        boolean expectedResult=false;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertEquals(10,seq.size());
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new UncheckedList(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        BooleanPredicate pred=val->val;
        //remove true
        boolean expectedResult=seq.contains(true);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(true));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new UncheckedList(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Predicate<Boolean> pred=val->val;
        //remove true
        boolean expectedResult=seq.contains(true);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(true));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new UncheckedList(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        BooleanPredicate pred=val->!val;
        //remove false
        boolean expectedResult=seq.contains(false);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(false));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new UncheckedList(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Predicate<Boolean> pred=val->!val;
        //remove false
        boolean expectedResult=seq.contains(false);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(false));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new UncheckedList(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        BooleanPredicate pred=val->true;
        //remove all
        boolean expectedResult=true;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new UncheckedList(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Predicate<Boolean> pred=val->true;
        //remove all
        boolean expectedResult=true;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new UncheckedList(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        BooleanPredicate pred=val->false;
        //remove none
        boolean expectedResult=false;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertEquals(10,seq.size());
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new UncheckedList(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Predicate<Boolean> pred=val->false;
        //remove none
        boolean expectedResult=false;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertEquals(10,seq.size());
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new UncheckedList(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        BooleanPredicate pred=val->val;
        //remove true
        boolean expectedResult=seq.contains(true);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(true));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new UncheckedList(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Predicate<Boolean> pred=val->val;
        //remove true
        boolean expectedResult=seq.contains(true);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(true));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new UncheckedList(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        BooleanPredicate pred=val->!val;
        //remove false
        boolean expectedResult=seq.contains(false);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(false));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new UncheckedList(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Predicate<Boolean> pred=val->!val;
        //remove false
        boolean expectedResult=seq.contains(false);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(false));
      }
    }
  }
  @Test
  public void testEmptyRemoveIfArrSeqCheckedStack()
  {
    BooleanPredicate filter=booleanPredicates.MarkAll.getPred(null,0);
    var seq=new CheckedStack();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Boolean>)filter::test));
  }
  @Test
  public void testRemoveIfCheckedStack()
  {
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        BooleanPredicate pred=val->true;
        //remove all
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popBoolean();
          seq.push(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Predicate<Boolean> pred=val->true;
        //remove all
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popBoolean();
          seq.push(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        BooleanPredicate pred=val->false;
        //remove none
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popBoolean();
          seq.push(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Predicate<Boolean> pred=val->false;
        //remove none
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popBoolean();
          seq.push(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        BooleanPredicate pred=val->val;
        //remove true
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popBoolean();
          seq.push(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Predicate<Boolean> pred=val->val;
        //remove true
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popBoolean();
          seq.push(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        BooleanPredicate pred=val->!val;
        //remove false
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popBoolean();
          seq.push(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Predicate<Boolean> pred=val->!val;
        //remove false
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popBoolean();
          seq.push(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        BooleanPredicate pred=val->true;
        //remove all
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popBoolean();
          seq.push(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Predicate<Boolean> pred=val->true;
        //remove all
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popBoolean();
          seq.push(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        BooleanPredicate pred=val->false;
        //remove none
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popBoolean();
          seq.push(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Predicate<Boolean> pred=val->false;
        //remove none
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popBoolean();
          seq.push(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        BooleanPredicate pred=val->val;
        //remove true
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popBoolean();
          seq.push(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Predicate<Boolean> pred=val->val;
        //remove true
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popBoolean();
          seq.push(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        BooleanPredicate pred=val->!val;
        //remove false
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popBoolean();
          seq.push(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Predicate<Boolean> pred=val->!val;
        //remove false
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.popBoolean();
          seq.push(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((BooleanPredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<Boolean>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((BooleanPredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<Boolean>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((BooleanPredicate)(v)->
          {
            var tmp=seq.popBoolean();
            seq.push(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<Boolean>)(v)->
          {
            var tmp=seq.popBoolean();
            seq.push(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((BooleanPredicate)(v)->
          {
            var tmp=seq.popBoolean();
            seq.push(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<Boolean>)(v)->
          {
            var tmp=seq.popBoolean();
            seq.push(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        BooleanPredicate pred=val->true;
        //remove all
        boolean expectedResult=true;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(seq.modCount!=0);
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Predicate<Boolean> pred=val->true;
        //remove all
        boolean expectedResult=true;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(seq.modCount!=0);
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        BooleanPredicate pred=val->false;
        //remove none
        boolean expectedResult=false;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertEquals(10,seq.size());
        Assertions.assertEquals(0,seq.modCount);
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Predicate<Boolean> pred=val->false;
        //remove none
        boolean expectedResult=false;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertEquals(10,seq.size());
        Assertions.assertEquals(0,seq.modCount);
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        BooleanPredicate pred=val->val;
        //remove true
        boolean expectedResult=seq.contains(true);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(true));
        if(expectedResult)
        {
          Assertions.assertTrue(seq.modCount!=0);
        }
        else
        {
          Assertions.assertEquals(0,seq.modCount);
        }
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Predicate<Boolean> pred=val->val;
        //remove true
        boolean expectedResult=seq.contains(true);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(true));
        if(expectedResult)
        {
          Assertions.assertTrue(seq.modCount!=0);
        }
        else
        {
          Assertions.assertEquals(0,seq.modCount);
        }
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        BooleanPredicate pred=val->!val;
        //remove false
        boolean expectedResult=seq.contains(false);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(false));
        if(expectedResult)
        {
          Assertions.assertTrue(seq.modCount!=0);
        }
        else
        {
          Assertions.assertEquals(0,seq.modCount);
        }
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Predicate<Boolean> pred=val->!val;
        //remove false
        boolean expectedResult=seq.contains(false);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(false));
        if(expectedResult)
        {
          Assertions.assertTrue(seq.modCount!=0);
        }
        else
        {
          Assertions.assertEquals(0,seq.modCount);
        }
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        BooleanPredicate pred=val->true;
        //remove all
        boolean expectedResult=true;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(seq.modCount!=0);
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Predicate<Boolean> pred=val->true;
        //remove all
        boolean expectedResult=true;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(seq.modCount!=0);
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        BooleanPredicate pred=val->false;
        //remove none
        boolean expectedResult=false;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertEquals(10,seq.size());
        Assertions.assertEquals(0,seq.modCount);
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Predicate<Boolean> pred=val->false;
        //remove none
        boolean expectedResult=false;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertEquals(10,seq.size());
        Assertions.assertEquals(0,seq.modCount);
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        BooleanPredicate pred=val->val;
        //remove true
        boolean expectedResult=seq.contains(true);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(true));
        if(expectedResult)
        {
          Assertions.assertTrue(seq.modCount!=0);
        }
        else
        {
          Assertions.assertEquals(0,seq.modCount);
        }
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Predicate<Boolean> pred=val->val;
        //remove true
        boolean expectedResult=seq.contains(true);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(true));
        if(expectedResult)
        {
          Assertions.assertTrue(seq.modCount!=0);
        }
        else
        {
          Assertions.assertEquals(0,seq.modCount);
        }
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        BooleanPredicate pred=val->!val;
        //remove false
        boolean expectedResult=seq.contains(false);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(false));
        if(expectedResult)
        {
          Assertions.assertTrue(seq.modCount!=0);
        }
        else
        {
          Assertions.assertEquals(0,seq.modCount);
        }
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedStack(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Predicate<Boolean> pred=val->!val;
        //remove false
        boolean expectedResult=seq.contains(false);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(false));
        if(expectedResult)
        {
          Assertions.assertTrue(seq.modCount!=0);
        }
        else
        {
          Assertions.assertEquals(0,seq.modCount);
        }
      }
    }
  }
  /*
  @Test
  public void testRemoveIfModCheckArrSeqCheckedStack()
  {
    var seq=new CheckedStack();
    for(int i=0;i<1000;++i)
    {
      var val=TypeConversionUtil.convertToboolean(i);
      seq.add(val);
    }
    Random rand=new Random(0);
    for(var predicateGenerator:booleanPredicates.values())
    {
      for(int m=predicateGenerator.getMLo(),mHi=predicateGenerator.getMHi(),repsBound=predicateGenerator.getNumReps();m<=mHi;m=predicateGenerator.incrementM(m))
      {
        for(int reps=0;reps<repsBound;++reps)
        {
          {
            var seqClone=(OmniStack.OfBoolean)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              if(!seqClone.isEmpty())
              {
                seqClone.pop();
              }
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(pred));
          }
          {
            var seqClone=(OmniStack.OfBoolean)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(false);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(pred));
          }
          {
            var seqClone=(OmniStack.OfBoolean)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              if(!seqClone.isEmpty())
              {
                seqClone.pop();
              }
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Boolean>)pred::test));
          }
          {
            var seqClone=(OmniStack.OfBoolean)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(false);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Boolean>)pred::test));
          }
        }
      }
    }
  }
  */
  @Test
  public void testEmptyRemoveIfArrSeqCheckedList()
  {
    BooleanPredicate filter=booleanPredicates.MarkAll.getPred(null,0);
    var seq=new CheckedList();
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Boolean>)filter::test));
  }
  @Test
  public void testRemoveIfCheckedList()
  {
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        BooleanPredicate pred=val->true;
        //remove all
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeBooleanAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Predicate<Boolean> pred=val->true;
        //remove all
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeBooleanAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        BooleanPredicate pred=val->false;
        //remove none
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeBooleanAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Predicate<Boolean> pred=val->false;
        //remove none
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeBooleanAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        BooleanPredicate pred=val->val;
        //remove true
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeBooleanAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Predicate<Boolean> pred=val->val;
        //remove true
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeBooleanAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        BooleanPredicate pred=val->!val;
        //remove false
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeBooleanAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Predicate<Boolean> pred=val->!val;
        //remove false
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeBooleanAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        BooleanPredicate pred=val->true;
        //remove all
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeBooleanAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Predicate<Boolean> pred=val->true;
        //remove all
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeBooleanAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        BooleanPredicate pred=val->false;
        //remove none
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeBooleanAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Predicate<Boolean> pred=val->false;
        //remove none
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeBooleanAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        BooleanPredicate pred=val->val;
        //remove true
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeBooleanAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Predicate<Boolean> pred=val->val;
        //remove true
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeBooleanAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        BooleanPredicate pred=val->!val;
        //remove false
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeBooleanAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Predicate<Boolean> pred=val->!val;
        //remove false
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeBooleanAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((BooleanPredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<Boolean>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((BooleanPredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<Boolean>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((BooleanPredicate)(v)->
          {
            var tmp=seq.removeBooleanAt(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<Boolean>)(v)->
          {
            var tmp=seq.removeBooleanAt(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((BooleanPredicate)(v)->
          {
            var tmp=seq.removeBooleanAt(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<Boolean>)(v)->
          {
            var tmp=seq.removeBooleanAt(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        BooleanPredicate pred=val->true;
        //remove all
        boolean expectedResult=true;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(seq.modCount!=0);
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Predicate<Boolean> pred=val->true;
        //remove all
        boolean expectedResult=true;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(seq.modCount!=0);
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        BooleanPredicate pred=val->false;
        //remove none
        boolean expectedResult=false;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertEquals(10,seq.size());
        Assertions.assertEquals(0,seq.modCount);
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Predicate<Boolean> pred=val->false;
        //remove none
        boolean expectedResult=false;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertEquals(10,seq.size());
        Assertions.assertEquals(0,seq.modCount);
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        BooleanPredicate pred=val->val;
        //remove true
        boolean expectedResult=seq.contains(true);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(true));
        if(expectedResult)
        {
          Assertions.assertTrue(seq.modCount!=0);
        }
        else
        {
          Assertions.assertEquals(0,seq.modCount);
        }
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Predicate<Boolean> pred=val->val;
        //remove true
        boolean expectedResult=seq.contains(true);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(true));
        if(expectedResult)
        {
          Assertions.assertTrue(seq.modCount!=0);
        }
        else
        {
          Assertions.assertEquals(0,seq.modCount);
        }
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        BooleanPredicate pred=val->!val;
        //remove false
        boolean expectedResult=seq.contains(false);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(false));
        if(expectedResult)
        {
          Assertions.assertTrue(seq.modCount!=0);
        }
        else
        {
          Assertions.assertEquals(0,seq.modCount);
        }
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Predicate<Boolean> pred=val->!val;
        //remove false
        boolean expectedResult=seq.contains(false);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(false));
        if(expectedResult)
        {
          Assertions.assertTrue(seq.modCount!=0);
        }
        else
        {
          Assertions.assertEquals(0,seq.modCount);
        }
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        BooleanPredicate pred=val->true;
        //remove all
        boolean expectedResult=true;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(seq.modCount!=0);
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Predicate<Boolean> pred=val->true;
        //remove all
        boolean expectedResult=true;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(seq.modCount!=0);
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        BooleanPredicate pred=val->false;
        //remove none
        boolean expectedResult=false;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertEquals(10,seq.size());
        Assertions.assertEquals(0,seq.modCount);
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Predicate<Boolean> pred=val->false;
        //remove none
        boolean expectedResult=false;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertEquals(10,seq.size());
        Assertions.assertEquals(0,seq.modCount);
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        BooleanPredicate pred=val->val;
        //remove true
        boolean expectedResult=seq.contains(true);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(true));
        if(expectedResult)
        {
          Assertions.assertTrue(seq.modCount!=0);
        }
        else
        {
          Assertions.assertEquals(0,seq.modCount);
        }
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Predicate<Boolean> pred=val->val;
        //remove true
        boolean expectedResult=seq.contains(true);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(true));
        if(expectedResult)
        {
          Assertions.assertTrue(seq.modCount!=0);
        }
        else
        {
          Assertions.assertEquals(0,seq.modCount);
        }
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        BooleanPredicate pred=val->!val;
        //remove false
        boolean expectedResult=seq.contains(false);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(false));
        if(expectedResult)
        {
          Assertions.assertTrue(seq.modCount!=0);
        }
        else
        {
          Assertions.assertEquals(0,seq.modCount);
        }
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var seq=new CheckedList(10,arr);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Predicate<Boolean> pred=val->!val;
        //remove false
        boolean expectedResult=seq.contains(false);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(false));
        if(expectedResult)
        {
          Assertions.assertTrue(seq.modCount!=0);
        }
        else
        {
          Assertions.assertEquals(0,seq.modCount);
        }
      }
    }
  }
  /*
  @Test
  public void testRemoveIfModCheckArrSeqCheckedList()
  {
    var seq=new CheckedList();
    for(int i=0;i<1000;++i)
    {
      var val=TypeConversionUtil.convertToboolean(i);
      seq.add(val);
    }
    Random rand=new Random(0);
    for(var predicateGenerator:booleanPredicates.values())
    {
      for(int m=predicateGenerator.getMLo(),mHi=predicateGenerator.getMHi(),repsBound=predicateGenerator.getNumReps();m<=mHi;m=predicateGenerator.incrementM(m))
      {
        for(int reps=0;reps<repsBound;++reps)
        {
          {
            var seqClone=(OmniList.OfBoolean)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              if(!seqClone.isEmpty())
              {
                seqClone.remove(0);
              }
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(pred));
          }
          {
            var seqClone=(OmniList.OfBoolean)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(false);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(pred));
          }
          {
            var seqClone=(OmniList.OfBoolean)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              if(!seqClone.isEmpty())
              {
                seqClone.remove(0);
              }
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Boolean>)pred::test));
          }
          {
            var seqClone=(OmniList.OfBoolean)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(false);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Boolean>)pred::test));
          }
        }
      }
    }
  }
  */
  @Test
  public void testEmptyRemoveIfArrSeqUncheckedSubList()
  {
    BooleanPredicate filter=booleanPredicates.MarkAll.getPred(null,0);
    var root=new UncheckedList();
    var seq=root.subList(0,0);
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Boolean>)filter::test));
  }
  @Test
  public void testRemoveIfUncheckedSubList()
  {
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new UncheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        BooleanPredicate pred=val->true;
        //remove all
        boolean expectedResult=true;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new UncheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Predicate<Boolean> pred=val->true;
        //remove all
        boolean expectedResult=true;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new UncheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        BooleanPredicate pred=val->false;
        //remove none
        boolean expectedResult=false;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertEquals(10,seq.size());
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new UncheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Predicate<Boolean> pred=val->false;
        //remove none
        boolean expectedResult=false;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertEquals(10,seq.size());
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new UncheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        BooleanPredicate pred=val->val;
        //remove true
        boolean expectedResult=seq.contains(true);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(true));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new UncheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Predicate<Boolean> pred=val->val;
        //remove true
        boolean expectedResult=seq.contains(true);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(true));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new UncheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        BooleanPredicate pred=val->!val;
        //remove false
        boolean expectedResult=seq.contains(false);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(false));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new UncheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Predicate<Boolean> pred=val->!val;
        //remove false
        boolean expectedResult=seq.contains(false);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(false));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new UncheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        BooleanPredicate pred=val->true;
        //remove all
        boolean expectedResult=true;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new UncheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Predicate<Boolean> pred=val->true;
        //remove all
        boolean expectedResult=true;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new UncheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        BooleanPredicate pred=val->false;
        //remove none
        boolean expectedResult=false;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertEquals(10,seq.size());
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new UncheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Predicate<Boolean> pred=val->false;
        //remove none
        boolean expectedResult=false;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertEquals(10,seq.size());
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new UncheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        BooleanPredicate pred=val->val;
        //remove true
        boolean expectedResult=seq.contains(true);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(true));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new UncheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Predicate<Boolean> pred=val->val;
        //remove true
        boolean expectedResult=seq.contains(true);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(true));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new UncheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        BooleanPredicate pred=val->!val;
        //remove false
        boolean expectedResult=seq.contains(false);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(false));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new UncheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Predicate<Boolean> pred=val->!val;
        //remove false
        boolean expectedResult=seq.contains(false);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(false));
      }
    }
  }
  @Test
  public void testEmptyRemoveIfArrSeqCheckedSubList()
  {
    BooleanPredicate filter=booleanPredicates.MarkAll.getPred(null,0);
    var root=new CheckedList();
    var seq=root.subList(0,0);
    Assertions.assertFalse(seq.removeIf(filter));
    Assertions.assertFalse(seq.removeIf((Predicate<Boolean>)filter::test));
  }
  @Test
  public void testRemoveIfCheckedSubList()
  {
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        BooleanPredicate pred=val->true;
        //remove all
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeBooleanAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Predicate<Boolean> pred=val->true;
        //remove all
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeBooleanAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        BooleanPredicate pred=val->false;
        //remove none
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeBooleanAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Predicate<Boolean> pred=val->false;
        //remove none
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeBooleanAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        BooleanPredicate pred=val->val;
        //remove true
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeBooleanAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Predicate<Boolean> pred=val->val;
        //remove true
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeBooleanAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        BooleanPredicate pred=val->!val;
        //remove false
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeBooleanAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Predicate<Boolean> pred=val->!val;
        //remove false
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeBooleanAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        BooleanPredicate pred=val->true;
        //remove all
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeBooleanAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Predicate<Boolean> pred=val->true;
        //remove all
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeBooleanAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        BooleanPredicate pred=val->false;
        //remove none
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeBooleanAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Predicate<Boolean> pred=val->false;
        //remove none
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeBooleanAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        BooleanPredicate pred=val->val;
        //remove true
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeBooleanAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Predicate<Boolean> pred=val->val;
        //remove true
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeBooleanAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        BooleanPredicate pred=val->!val;
        //remove false
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeBooleanAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Predicate<Boolean> pred=val->!val;
        //remove false
        Assertions.assertThrows(ConcurrentModificationException.class,()->seq.removeIf(CheckedCollectionTest.getModifyingPred(pred,()->
        {
          var tmp=seq.removeBooleanAt(seq.size()-1);
          seq.add(tmp);
        })));
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((BooleanPredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<Boolean>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((BooleanPredicate)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->
        {
          seq.removeIf((Predicate<Boolean>)(v)->
          {
            throw new IndexOutOfBoundsException();
          });
        });
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((BooleanPredicate)(v)->
          {
            var tmp=seq.removeBooleanAt(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<Boolean>)(v)->
          {
            var tmp=seq.removeBooleanAt(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((BooleanPredicate)(v)->
          {
            var tmp=seq.removeBooleanAt(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Assertions.assertThrows(ConcurrentModificationException.class,()->
        {
          seq.removeIf((Predicate<Boolean>)(v)->
          {
            var tmp=seq.removeBooleanAt(seq.size()-1);
            seq.add(tmp);
            throw new IndexOutOfBoundsException();
          });
        });
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        BooleanPredicate pred=val->true;
        //remove all
        boolean expectedResult=true;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(root.modCount!=0);
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Predicate<Boolean> pred=val->true;
        //remove all
        boolean expectedResult=true;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(root.modCount!=0);
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        BooleanPredicate pred=val->false;
        //remove none
        boolean expectedResult=false;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertEquals(10,seq.size());
        Assertions.assertEquals(0,root.modCount);
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Predicate<Boolean> pred=val->false;
        //remove none
        boolean expectedResult=false;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertEquals(10,seq.size());
        Assertions.assertEquals(0,root.modCount);
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        BooleanPredicate pred=val->val;
        //remove true
        boolean expectedResult=seq.contains(true);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(true));
        if(expectedResult)
        {
          Assertions.assertTrue(root.modCount!=0);
        }
        else
        {
          Assertions.assertEquals(0,root.modCount);
        }
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Predicate<Boolean> pred=val->val;
        //remove true
        boolean expectedResult=seq.contains(true);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(true));
        if(expectedResult)
        {
          Assertions.assertTrue(root.modCount!=0);
        }
        else
        {
          Assertions.assertEquals(0,root.modCount);
        }
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        BooleanPredicate pred=val->!val;
        //remove false
        boolean expectedResult=seq.contains(false);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(false));
        if(expectedResult)
        {
          Assertions.assertTrue(root.modCount!=0);
        }
        else
        {
          Assertions.assertEquals(0,root.modCount);
        }
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=false;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin false
        Predicate<Boolean> pred=val->!val;
        //remove false
        boolean expectedResult=seq.contains(false);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(false));
        if(expectedResult)
        {
          Assertions.assertTrue(root.modCount!=0);
        }
        else
        {
          Assertions.assertEquals(0,root.modCount);
        }
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        BooleanPredicate pred=val->true;
        //remove all
        boolean expectedResult=true;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(root.modCount!=0);
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Predicate<Boolean> pred=val->true;
        //remove all
        boolean expectedResult=true;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertTrue(seq.isEmpty());
        Assertions.assertTrue(root.modCount!=0);
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        BooleanPredicate pred=val->false;
        //remove none
        boolean expectedResult=false;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertEquals(10,seq.size());
        Assertions.assertEquals(0,root.modCount);
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Predicate<Boolean> pred=val->false;
        //remove none
        boolean expectedResult=false;
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertEquals(10,seq.size());
        Assertions.assertEquals(0,root.modCount);
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        BooleanPredicate pred=val->val;
        //remove true
        boolean expectedResult=seq.contains(true);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(true));
        if(expectedResult)
        {
          Assertions.assertTrue(root.modCount!=0);
        }
        else
        {
          Assertions.assertEquals(0,root.modCount);
        }
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Predicate<Boolean> pred=val->val;
        //remove true
        boolean expectedResult=seq.contains(true);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(true));
        if(expectedResult)
        {
          Assertions.assertTrue(root.modCount!=0);
        }
        else
        {
          Assertions.assertEquals(0,root.modCount);
        }
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        BooleanPredicate pred=val->!val;
        //remove false
        boolean expectedResult=seq.contains(false);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(false));
        if(expectedResult)
        {
          Assertions.assertTrue(root.modCount!=0);
        }
        else
        {
          Assertions.assertEquals(0,root.modCount);
        }
      }
    }
    {
      for(int period=1;period<=10;++period)
      {
        var arr=new boolean[10];
        var root=new CheckedList(10,arr);
        var subList=root.subList(0,10);
        var seq=subList.subList(0,10);
        boolean currVal=true;
        for(int i=0;i<10;++i)
        {
          arr[i]=currVal;
          if(((++i)%period)==0)
          {
            currVal=!currVal;
          }
        }
        //alternating begin true
        Predicate<Boolean> pred=val->!val;
        //remove false
        boolean expectedResult=seq.contains(false);
        Assertions.assertEquals(expectedResult,seq.removeIf(pred));
        Assertions.assertFalse(seq.contains(false));
        if(expectedResult)
        {
          Assertions.assertTrue(root.modCount!=0);
        }
        else
        {
          Assertions.assertEquals(0,root.modCount);
        }
      }
    }
  }
  /*
  @Test
  public void testRemoveIfModCheckArrSeqCheckedSubList()
  {
    var root=new CheckedList();
    var seq=root.subList(0,0);
    for(int i=0;i<1000;++i)
    {
      var val=TypeConversionUtil.convertToboolean(i);
      seq.add(val);
    }
    Random rand=new Random(0);
    for(var predicateGenerator:booleanPredicates.values())
    {
      for(int m=predicateGenerator.getMLo(),mHi=predicateGenerator.getMHi(),repsBound=predicateGenerator.getNumReps();m<=mHi;m=predicateGenerator.incrementM(m))
      {
        for(int reps=0;reps<repsBound;++reps)
        {
          {
            var seqClone=(OmniList.OfBoolean)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              if(!seqClone.isEmpty())
              {
                seqClone.remove(0);
              }
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(pred));
          }
          {
            var seqClone=(OmniList.OfBoolean)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(false);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf(pred));
          }
          {
            var seqClone=(OmniList.OfBoolean)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              if(!seqClone.isEmpty())
              {
                seqClone.remove(0);
              }
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Boolean>)pred::test));
          }
          {
            var seqClone=(OmniList.OfBoolean)seq.clone();
            var pred=CheckedCollectionTest.getModifyingPred(predicateGenerator.getPred(rand,m),()->
            {
              seqClone.add(false);
            });
            Assertions.assertThrows(ConcurrentModificationException.class,()->seqClone.removeIf((Predicate<Boolean>)pred::test));
          }
        }
      }
    }
  }
  */
}
